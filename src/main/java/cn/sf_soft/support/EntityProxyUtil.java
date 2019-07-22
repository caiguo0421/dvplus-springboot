package cn.sf_soft.support;

import cn.sf_soft.common.BeanUtil;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.common.util.Tools;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.mapping.Bag;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/5 14:05
 * @Description:
 */
public class EntityProxyUtil {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EntityProxyUtil.class);

    public static BigDecimal sum(EntityProxy entityProxy, String slaveName, String propertyName) {
        List<EntityProxy<?>> slaves = entityProxy.getSlaves(slaveName);
        if (null == slaves || slaves.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal result = BigDecimal.ZERO;
        for (EntityProxy slaveEntityProxy : slaves) {
            if (slaveEntityProxy.getOperation() == Operation.DELETE) continue;
            Object object = slaveEntityProxy.getEntity();
            if (null == object) continue;
            try {
                Object obj = BeanUtil.getValueOfProperty(object, propertyName);
                if (null != obj) {
                    if (obj instanceof Number) {
                        BigDecimal value = Tools.toBigDecimal((Number) obj);
                        result = result.add(value);
                    } else {
                        throw new ServiceException(String.format("%s.%s的类型不是数字类型", object.getClass().getSimpleName(), propertyName));
                    }
                }

            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                throw new ServiceException(String.format("获取%s.%s的值时出错", object.getClass().getSimpleName(), propertyName));
            }
        }
        return result;
    }


    /**
     * 查询entityProxy的子孙
     *
     * @param entityProxy
     * @param slaveNamePath：路径，以“.”分割
     * @return
     */
    public static List<EntityProxy<?>> getDescendants(EntityProxy entityProxy, String slaveNamePath) {
        String[] slaveNames = StringUtils.split(slaveNamePath, ".");
        List<EntityProxy<?>> targetProxyList = new ArrayList<>();
        reGetDescendants(entityProxy, slaveNames, targetProxyList);
        return targetProxyList;
    }

    private static void reGetDescendants(EntityProxy masterProxy, String[] slaveNames, List<EntityProxy<?>> targetProxyList) {
        if (masterProxy == null) {
            return;
        }
        if (slaveNames == null || slaveNames.length == 0) {
            return;
        }
        String slaveName = slaveNames[0];

        List<EntityProxy<?>> slaves = masterProxy.getSlaves(slaveName);
        if (slaves != null && slaves.size() > 0) {
            if (slaveNames.length == 1) {
                targetProxyList.addAll(slaves);
            } else {
                for (EntityProxy<?> slave : slaves) {
                    reGetDescendants(slave, ArrayUtils.remove(slaveNames, 0), targetProxyList);
                }
            }
        }
    }


    public static EntityProxy newEntityProxy(Operation operation, Object object, Class<?> serviceClass, boolean readOnly) {
        if (null == operation) {
            throw new ServiceException("创建代理对象时对象操作不能为空");
        }
        if (null == object) {
            throw new ServiceException("创建代理对象时对象不能为空");
        }
        if (null == serviceClass) {
            throw new ServiceException("创建代理对象时对象服务不能为空");
        }
        EntityProxyImpl<Object> entityProxy = new EntityProxyImpl<Object>(operation, object.getClass());
        entityProxy.setService(getService(serviceClass));
        entityProxy.setEntity(object);
        entityProxy.setReadOnly(readOnly);
        if (operation != Operation.CREATE) {
            entityProxy.setOriginalEntity(object, true);
        }
        return entityProxy;
    }

    public static EntityProxy newEntityProxy(Operation operation, Object object, Class<?> serviceClass) {
        return newEntityProxy(operation, object, serviceClass, false);
    }

    public static void addSlave(EntityProxy master, EntityProxy slave) {
        if (null == master || null == slave) {
            throw new ServiceException("建立代理对象关系时，主从代理对象不能为空");
        }
        ((EntityProxyImpl) master).addSlave(slave);
    }

    public static Command getService(EntityRelation relation) {
        return getService(relation.getService());
    }

    public static Command getService(Class<?> clazz) {
        try {
            Object bean = ApplicationUtil.getBean(clazz);
            if ((bean instanceof Command)) {
                return (Command) bean;
            } else {
                throw new ServiceException(String.format("对象%s的服务未实现指定的接口", clazz.getSimpleName()));
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(String.format("获取%s的服务实例出错", clazz), e);
        }
    }


    //比较EntityProxy中的新旧值
    public static CompareEntity compareEntityProxy(EntityProxy entityProxy) {
        PersistentClass persistentClass = HibernateConfigurationUtil.getPersistentClassBySimpleClassName(entityProxy.getEntityClass().getSimpleName());
        //id的值
        Object idVal = null;
        if (entityProxy.getEntity() != null) {
            idVal = BeanUtil.getPropertyValue(entityProxy.getEntity(), persistentClass.getIdentifierProperty().getName());
        }
        Iterator<Property> it = persistentClass.getPropertyIterator();
        CompareEntity compareEntity = new CompareEntity(entityProxy.getEntityClass().getSimpleName(), entityProxy.getOperation(), idVal);

        if (entityProxy.getOperation() == Operation.UPDATE) {
            Object obj = entityProxy.getEntity();
            Object oriObj = entityProxy.getOriginalEntity();

            while (it.hasNext()) {
                Property property = it.next();
                if (property.getValue() instanceof Bag) {
                    continue;
                }
                String propertyName = property.getName();
                Iterator iterator = property.getColumnIterator();
                if (iterator ==null) {
                    continue;
                }
                Object p = iterator.next();
                if (!(p instanceof Column)) {
                    continue;
                }
                Column column = (Column) property.getColumnIterator().next();
                String columnName = column.getName();

                Object propertyVal = BeanUtil.getPropertyValue(obj, propertyName);
                Object oriPropertyVal = BeanUtil.getPropertyValue(oriObj, propertyName);
                if (!compareVal(propertyVal, oriPropertyVal, property.getType().getName())) {
                    compareEntity.addCompareProperty(propertyName, columnName, propertyVal, oriPropertyVal);
                }
            }
        }

        if (entityProxy.getSlaveNames() != null && entityProxy.getSlaveNames().length > 0) {
            for (String salveName : entityProxy.getSlaveNames()) {
                List<EntityProxy> salveEntityList = entityProxy.getSlaves(salveName);
                if (salveEntityList != null && salveEntityList.size() > 0) {
                    for (EntityProxy salveEntity : salveEntityList) {
                        compareEntity.addSlave(compareEntityProxy(salveEntity));
                    }
                }
            }
        }

        return compareEntity;
    }

    //是否变更过
    public static boolean compareEntityIsChanged(CompareEntity compareEntity, CompareEntity.Compare compareInterface) {
        boolean isChanged = false;
        if (compareEntity.getOperation() == Operation.CREATE || compareEntity.getOperation() == Operation.DELETE) {
            return true;
        }

        if (compareEntity.getOperation() == Operation.UPDATE) {
            if (compareEntity.getProperties() != null && compareEntity.getProperties().size() > 0) {
                for (CompareEntity.CompareProperty compareProperty : compareEntity.getProperties()) {
                    if (compareInterface == null) {
                        //如果compare接口为空，那么默认isChanged = true
                        isChanged = true;
                    } else {
                        isChanged = compareInterface.compareProperty(compareEntity, compareProperty);
                    }

                    if (isChanged) {
                        logger.debug(String.format("%s(id=%s)属性%s 发生变更，val:%s oriVal:%s", compareEntity.getEntityClassName(), compareEntity.getId(), compareProperty.getPropertyName(),
                                compareProperty.getVal(), compareProperty.getOriVal()));
                        return isChanged;
                    }
                }
            }
        }

        if (compareEntity.getSlaveNames() != null && compareEntity.getSlaveNames().length > 0) {
            for (String salveName : compareEntity.getSlaveNames()) {
                List<CompareEntity> salveCompareEntityList = compareEntity.getSlaves(salveName);
                if (salveCompareEntityList != null && salveCompareEntityList.size() > 0) {
                    for (CompareEntity salveCompareEntity : salveCompareEntityList) {
                        isChanged = compareEntityIsChanged(salveCompareEntity, compareInterface);

                        if (isChanged) {
                            return isChanged;
                        }
                    }
                }
            }
        }

        return isChanged;
    }


    private static boolean compareVal(Object val, Object oriVal, String typeName) {
        //如果都为空，认为相等
        if (null == val && null == oriVal) {
            return true;
        }

        if ("boolean".equals(typeName)) {
            //如果一个为空，一个不为空，认为不相等
            if (val == null || oriVal == null) {
                return false;
            }
            return ((Boolean) val).equals((Boolean) oriVal);
        } else if ("byte".equals(typeName)) {
            //如果一个为空，一个不为空，认为不相等
            if (val == null || oriVal == null) {
                return false;
            }
            return ((Byte) val).equals((Byte) oriVal);
        } else if ("short".equals(typeName)) {
            //如果一个为空，一个不为空，认为不相等
            if (val == null || oriVal == null) {
                return false;
            }
            return ((Short) val).equals((Short) oriVal);
        } else if ("integer".equals(typeName)) {
            //int 按值进行比较
            return Tools.toInt((Integer) val) == Tools.toInt((Integer) oriVal);
        } else if ("long".equals(typeName)) {
            return Tools.toLong((Long) val) == Tools.toLong((Long) oriVal);
        } else if ("float".equals(typeName) || "double".equals(typeName)) {
            //double 判断
            return Math.abs(Tools.toDouble((Number) val) - Tools.toDouble((Number) oriVal)) < 0.00005;
        } else if ("timestamp".equals(typeName)) {
            //如果一个为空，一个不为空，认为不相等
            if (val == null || oriVal == null) {
                return false;
            }
            //解决bug,小于500ms算相等
            return Math.abs(((Timestamp) val).getTime() - ((Timestamp) oriVal).getTime()) < 500;

//            return ((Timestamp) val).equals((Timestamp) oriVal);
        } else if ("date".equals(typeName)) {
            //如果一个为空，一个不为空，认为不相等
            if (val == null || oriVal == null) {
                return false;
            }
            return ((java.sql.Date) val).equals((java.sql.Date) oriVal);
        } else if ("big_decimal".equals(typeName)) {
            return (Tools.toBigDecimal((BigDecimal) val)).compareTo(Tools.toBigDecimal((BigDecimal) oriVal)) == 0;
        } else if ("string".equals(typeName)) {
            if (StringUtils.isEmpty((String) val) && StringUtils.isEmpty((String) oriVal)) {
                //String的认为NULL和""相等
                return true;
            }
            return StringUtils.equals((String) val, (String) oriVal);
        } else {
            throw new ServiceException(String.format("数据比较遇到不支持的数据类型：%s,原始值:%s,现在值:%s", typeName, oriVal, val));
        }
    }


    public static void main(String[] args) {
        Object val = null;
        Object oriVal = 0.00D;
//        Double l = 0.00D;
        Long l = 1L;
//        System.out.println((BigDecimal)l);

        System.out.println(val instanceof Double || oriVal instanceof Double);
    }


}
