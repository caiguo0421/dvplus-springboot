package cn.sf_soft.support;

import cn.sf_soft.common.BeanUtil;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.common.util.TimestampUitls;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.mapping.Bag;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Auther: chenbiao
 * @Date: 2018/6/30 09:23
 * @Description: 缺省转换器
 */
@Component
public class ParameterDefaultConvert implements ParameterConverter {

    protected static Logger logger = LoggerFactory.getLogger(ParameterDefaultConvert.class);

    @Autowired
    protected BaseDaoHibernateImpl baseDao;

    protected ThreadLocal<JsonObject> parameter = new ThreadLocal<JsonObject>();

    @Override
    public List<EntityProxy<?>> convert(JsonObject parameter, Command command) {

        if (null == parameter || parameter.isJsonNull()) {
            logger.debug("转换的参数为空");
            return null;
        } else {
            if (null == command) {
                logger.debug("必须提供Command服务");
                throw new ServiceException("必须提供Command服务");
            }
            this.parameter.set(parameter);
            try {
                return doConvert(command.getEntityRelation(), null, null);
            } finally {
                this.parameter.remove();
            }
        }
    }

    protected String getColumnName(PersistentClass persistentClass, String propertyName) {
        Property property = persistentClass.getProperty(propertyName);
        Iterator<?> iterator = property.getColumnIterator();
        if (iterator.hasNext()) {
            Column column = (Column) iterator.next();
            return column.getName();
        }
        return null;
    }

    protected List<EntityProxy<?>> doConvert(EntityRelation relation, String masterId, Object masterObject) {
        if (null == relation) {
            throw new ServiceException("服务未配置关联关系");
        }
        Class<?> entityClass = relation.getEntityClass();
        PersistentClass persistentClass = HibernateConfigurationUtil.getPersistentClassBySimpleClassName(entityClass.getSimpleName());
        if (null == persistentClass) {
            throw new ServiceException(String.format("未找到命名为%s的实体映射", entityClass.getSimpleName()));
        }
        //表名
        String tableName = persistentClass.getTable().getName();

        //主键列
        String primaryName = null;
        if (StringUtils.isEmpty(relation.getIdentifierPropertyName())) {
            primaryName = persistentClass.getTable().getPrimaryKey().getColumn(0).getName();
        } else {
            primaryName = getColumnName(persistentClass, relation.getIdentifierPropertyName());
        }

        List<EntityProxy<?>> entityProxies = new ArrayList<EntityProxy<?>>();
        if (null == masterId) {
            JsonArray array = this.findArray(tableName);
            if (null == array || array.isJsonNull()) {
                throw new ServiceException(String.format("未找到Key为%s的JSON参数", tableName));

            } else if (array.size() != 1) {
                throw new ServiceException(String.format("最顶层的JSON对象（%s）个数异常", tableName));
            }
            JsonObject jsonObject = array.get(0).getAsJsonObject();
            EntityProxyImpl<?> entityProxy = this.convertJsonObject(jsonObject, null, tableName, primaryName, persistentClass, relation);
            ;
            entityProxies.add(entityProxy);
        } else {
            //String associationColumnName = getColumnName(persistentClass, relation.getAssociationPropertyName());
            JsonArray array = this.findArray(relation, persistentClass, masterId, masterObject);

            /*String associationPropertyName = relation.getAssociationPropertyName();
            String hql = String.format("from %s where %s=?", relation.getEntityClass().getSimpleName(), associationPropertyName);
            logger.debug("hql:{}", hql);
            String identifierPropertyName = persistentClass.getIdentifierProperty().getName();
            List<?> objects = baseDao.findByHql(hql, new Object[]{masterId});*/

            List<?> objects = this.findSlaveObjects(relation, masterId, masterObject);
            String identifierPropertyName = persistentClass.getIdentifierProperty().getName();
            Map<String, Object> beans = new HashMap<String, Object>();
            if (null != objects && !objects.isEmpty()) {
                for (int i = 0; i < objects.size(); i++) {
                    Object bean = objects.get(i);
                    String id = this.getId(bean, identifierPropertyName);
                    beans.put(id, bean);
                }
            }
            //将JSON对象中存在的转换成EntityProxyImpl并添加到entityProxies中
            if (null != array && !array.isJsonNull()) {
                for (int i = 0; i < array.size(); i++) {
                    JsonObject jsonObject = array.get(i).getAsJsonObject();
                    String id = this.getId(jsonObject, relation, primaryName);
                    if (beans.containsKey(id)) {
                        Object bean = beans.get(id);
                        EntityProxyImpl<?> entityProxy = this.convertJsonObject(jsonObject, bean, tableName, primaryName, persistentClass, relation);
                        entityProxies.add(entityProxy);
                        beans.remove(id);
                    } else {
                        EntityProxyImpl<?> entityProxy = this.convertJsonObject(jsonObject, BeanUtils.instantiate(entityClass), tableName, primaryName, persistentClass, relation);
                        entityProxies.add(entityProxy);
                    }
                }
            }
            //将JSON对象中不存但在数据库中存在的对象添加到entityProxies中
            for (String key : beans.keySet()) {
                Object bean = beans.get(key);
                EntityProxyImpl<Object> entityProxy = new EntityProxyImpl<Object>(Operation.NONE, relation.getEntityClass());
                entityProxy.setEntity(bean);
                entityProxy.setOriginalEntity(bean, true);
                entityProxy.setService(EntityProxyUtil.getService(relation));
                entityProxy.setReadOnly(relation.isReadOnly());
                String identifierProperty = persistentClass.getIdentifierProperty().getName();
                String id = this.getId(bean, identifierProperty);
                Map<String, List<EntityProxy<?>>> map = this.convertSlave(relation, id, bean);
                entityProxy.setSlaves(map);
                entityProxies.add(entityProxy);
            }
        }
        return entityProxies;
    }

    protected List<?> findSlaveObjects(EntityRelation relation, String masterId, Object masterObject) {
        String associationPropertyName = relation.getAssociationPropertyName();
        String hql = String.format("from %s where %s=?", relation.getEntityClass().getSimpleName(), associationPropertyName);
        logger.debug("hql:{}", hql);
        List<?> objects = baseDao.findByHql(hql, new Object[]{masterId});
        return objects;
    }

    protected Object findObject(EntityRelation relation, String id, JsonObject jsonObject) {
        return baseDao.get(relation.getEntityClass(), id);
    }

    protected EntityProxyImpl<?> convertJsonObject(JsonObject jsonObject, Object object, String tableName, String primaryKey, PersistentClass persistentClass, EntityRelation relation) {
        Operation operation = this.getOperation(jsonObject);
        EntityProxyImpl<Object> entityProxy = new EntityProxyImpl<>(operation, relation.getEntityClass());
        entityProxy.setJsonObject(jsonObject);
        entityProxy.setService(EntityProxyUtil.getService(relation));
        entityProxy.setReadOnly(relation.isReadOnly());
        String id = getAsString(jsonObject, primaryKey);
        if (StringUtils.isBlank(id) || StringUtils.isEmpty(id)) {
            throw new ServiceException(String.format("%s的JSON对象提供唯一标识%s不能为空", tableName, primaryKey));
        }
        if (operation == Operation.CREATE) {
            Object obj = this.convertToObject(jsonObject, BeanUtils.instantiate(relation.getEntityClass()), persistentClass);
            Property property = persistentClass.getIdentifierProperty();
            String methodName = getSetterMethodName(property.getName());
            try {
                Method method = relation.getEntityClass().getMethod(methodName, property.getType().getReturnedClass());
                method.invoke(obj, id);
            } catch (Exception e) {
                throw new ServiceException(String.format("设置对象%s.%s：%s时出错", object.getClass().getSimpleName(), property.getValue(), id), e);
            }
            entityProxy.setEntity(obj);
            Map<String, List<EntityProxy<?>>> slaves = this.convertSlave(relation, id, obj);
            entityProxy.setSlaves(slaves);
        } else if (operation == Operation.UPDATE || operation == Operation.DELETE || operation == Operation.NONE) {
            if (!jsonObject.has(primaryKey)) {
                throw new ServiceException(String.format("%s的JSON对象未提供唯一标识%s", tableName, primaryKey));
            }
            if (null == object) {
                object = this.findObject(relation, id, jsonObject);
                if (null == object) {
                    throw new ServiceException(String.format("指定的对象%s（%s）记录未找到", relation.getEntityClass().getSimpleName(), id));
                }
            }
            entityProxy.setOriginalEntity(object, true);
            if (operation == Operation.UPDATE) {
                object = this.convertToObject(jsonObject, object, persistentClass);
                entityProxy.setEntity(object, relation.isReadOnly());
                //从hibernate缓存中移除改对象
                if (relation.isReadOnly()) {
                    baseDao.getCurrentSession().evict(object);
                }
            } else if (operation == Operation.DELETE || operation == Operation.NONE) {
                entityProxy.setEntity(object, relation.isReadOnly());
            }


            Map<String, List<EntityProxy<?>>> slaves = this.convertSlave(relation, id, object);
            entityProxy.setSlaves(slaves);
        } else {
            return null;
        }
        return entityProxy;
    }


    protected Object convertToObject(JsonObject jsonObject, Object object, PersistentClass persistentClass) {
        Iterator<Property> it = persistentClass.getPropertyIterator();
        while (it.hasNext()) {
            Property property = it.next();
            if(property.getValue() instanceof Bag){
                continue;
            }
            String propertyName = property.getName();
            Iterator iterator = property.getColumnIterator();
            if (iterator==null) {
                continue;
            }
            Object p = iterator.next();
            if (!(p instanceof Column)) continue;
            Column column = (Column) property.getColumnIterator().next();
            String columnName = column.getName();
            if (jsonObject.has(columnName)) {
                Object value = getAsString(jsonObject, columnName);
                String typeName = property.getType().getName();
                Object newValue = null;
                Class<?> type = null;
                try {
                    Map.Entry<Class<?>, Object> entry = this.convertByTypeName(typeName, value);
                    newValue = entry.getValue();
                    type = entry.getKey();
                } catch (Exception e) {
                    throw new ServiceException(String.format("解析对象%s.%s：%s时出错", object.getClass().getSimpleName(), propertyName, value), e);
                }
                String methodName = getSetterMethodName(propertyName);
                try {
                    //Method method = object.getClass().getMethod(methodName, property.getType().getReturnedClass()); 时间类型处理有问题
                    Method method = object.getClass().getMethod(methodName, type);
                    method.invoke(object, newValue);
                } catch (Exception e) {
                    throw new ServiceException(String.format("设置对象%s.%s：%s时出错", object.getClass().getSimpleName(), propertyName, value), e);
                }
            }
        }
        return object;
    }

    protected Map.Entry<Class<?>, Object> convertByTypeName(String typeName, Object val) {
        String sVal = null;
        Object oVal = null;
        if (null == val || (val instanceof String) && "null".equals(((String) val).toLowerCase())) {
            oVal = null;
        } else {
            oVal = val;
            sVal = val.toString().trim();
        }
        try {
            Object o = null;
            Class<?> type = null;
            if ("short".equals(typeName)) {
                type = Short.class;
                if (null != oVal && StringUtils.isNotEmpty(sVal)) {
                    o = Short.parseShort(sVal);
                }
            } else if ("integer".equals(typeName)) {
                type = Integer.class;
                if (null != oVal && StringUtils.isNotEmpty(sVal)) {
                    o = Integer.parseInt(sVal);
                }
            } else if ("long".equals(typeName)) {
                type = Long.class;
                if (null != oVal && StringUtils.isNotEmpty(sVal)) {
                    o = Long.parseLong(sVal);
                }
            } else if ("float".equals(typeName)) {
                type = Float.class;
                if (null != oVal && StringUtils.isNotEmpty(sVal)) {
                    o = Float.parseFloat(sVal);
                }
            } else if ("double".equals(typeName)) {
                type = Double.class;
                if (null != oVal && StringUtils.isNotEmpty(sVal)) {
                    o = Double.parseDouble(sVal);
                }
            } else if ("byte".equals(typeName)) {
                type = Byte.class;
                if (null != oVal && StringUtils.isNotEmpty(sVal)) {
                    o = Byte.valueOf(sVal);
                }
            } else if ("string".equals(typeName)) {
                type = String.class;
                if (null != oVal) {
                    o = sVal;
                }
            } else if ("boolean".equals(typeName)) {
                type = Boolean.class;
                if (null == oVal || StringUtils.isEmpty(sVal)) {
                    o = null; //如果没有值，则为null
                } else {
                    sVal = sVal.toLowerCase();
                    if (sVal.equals("y") || sVal.equals("yes") || sVal.equals("t") || sVal.equals("true") || sVal.equals("1")) {
                        o = true;
                    } else {
                        o = false;
                    }
                }
            } else if ("timestamp".equals(typeName)) {
                type = Timestamp.class;
                if (null == oVal || StringUtils.isEmpty(sVal)) {
                    o = null;
                } else {
                    if (sVal != null && sVal.length() == 10) {
                        o = new Timestamp(TimestampUitls.formatDate(sVal, "yyyy-MM-dd").getTime());
                    } else if (sVal != null && sVal.length() == 19) {
                        o = new Timestamp(TimestampUitls.formatDate(sVal, "yyyy-MM-dd HH:mm:ss").getTime());
                    } else {
                        o = new Timestamp(TimestampUitls.formatDate(sVal).getTime());
                    }
                }
            } else if ("date".equals(typeName)) {
                type = java.sql.Date.class;
                if (null == oVal || StringUtils.isEmpty(sVal)) {
                    o = null;
                } else {
                    o = TimestampUitls.formatDate2(sVal);
                }

            } else if ("big_decimal".equals(typeName)) {
                type = BigDecimal.class;
                if (null == oVal || StringUtils.isEmpty(sVal)) {
                    o = null;
                } else {
                    o = new BigDecimal(sVal);
                }
            } else {
                throw new ServiceException(String.format("遭遇不支持的数据类型：%s", typeName));
            }
            return new AbstractMap.SimpleEntry(type, o);
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(String.format(String.format("转换类型为%s值为%s时出错", typeName, val)), ex);
        }
    }

    /**
     * 转换从对象
     *
     * @param relation
     * @param masterId
     * @return
     */
    protected Map<String, List<EntityProxy<?>>> convertSlave(EntityRelation relation, String masterId, Object masterObject) {
        List<EntityRelation> slaves = relation.getSlaves();
        if (null != slaves && !slaves.isEmpty()) {
            Map<String, List<EntityProxy<?>>> slaveEntityProxies = new HashMap<String, List<EntityProxy<?>>>();
            for (EntityRelation slaveRelation : slaves) {
                if (null == slaveRelation.getService()) {
                    throw new ServiceException(String.format("对象%s未配置执行服务", slaveRelation.getEntityClass().getSimpleName()));
                }
                Object slaveCommand = ApplicationUtil.getBean(slaveRelation.getService());
                if (null == slaveCommand) {
                    throw new ServiceException(String.format("对象%s配置的执行服务%s未定义", slaveRelation.getEntityClass().getSimpleName(), slaveRelation.getService().getSimpleName()));

                } else if (!(slaveCommand instanceof Command)) {
                    throw new ServiceException(String.format("对象%s配置的执行服务%s未实现指定的接口", slaveRelation.getEntityClass().getSimpleName(), slaveRelation.getService().getSimpleName()));
                }
                List<EntityProxy<?>> entityProxies = this.doConvert(slaveRelation, masterId, masterObject);
                if (null != entityProxies && !entityProxies.isEmpty()) {
                    slaveEntityProxies.put(slaveRelation.getEntityClass().getSimpleName(), entityProxies);
                }
            }
            if (slaveEntityProxies.isEmpty()) {
                return null;
            }
            return slaveEntityProxies;
        }
        return null;
    }


    protected Operation getOperation(JsonObject object) {
        if (null != object && !object.isJsonNull()) {
            if (!object.has(OP_CODE)) {
                logger.error("缺少{}，{}", OP_CODE, object);
                throw new ServiceException(String.format("参数中缺少%s", OP_CODE));
            }
        }
        String opCode = object.get(OP_CODE).getAsString();
        if (StringUtils.isEmpty(opCode)) {
            logger.error("{}为空，{}", OP_CODE, object);
            throw new ServiceException(String.format("参数中的%s不能为空", OP_CODE));
        }
        Operation operation = Operation.valueOf(opCode.toUpperCase());
        if (null == operation) {
            throw new ServiceException(String.format("无效的%s（%s）", OP_CODE, opCode));
        }
        return operation;
    }

    protected JsonArray findArray(String tableName) {

        if (this.parameter.get().has(tableName)) {
            return this.parameter.get().get(tableName).getAsJsonArray();
        }
        return null;
    }

    protected JsonArray findArray(EntityRelation relation, PersistentClass persistentClass, String associationId, Object associationObject) {
        String tableName = persistentClass.getTable().getName();
        String associationColumnName = getColumnName(persistentClass, relation.getAssociationPropertyName());
        if (null == associationColumnName || null == associationId) {
            throw new ServiceException("指定的关联参数不能为空（" + tableName + "）");
        }

        if (this.parameter.get().has(tableName)) {
            JsonArray array = this.parameter.get().get(tableName).getAsJsonArray();
            if (null != array && !array.isJsonNull()) {
                JsonArray jsonArray = new JsonArray();
                for (int j = 0; j < array.size(); j++) {
                    JsonObject jsonObject = array.get(j).getAsJsonObject();
                    if (null != jsonObject && !jsonObject.isJsonNull() && jsonObject.has(associationColumnName)) {
                        String id = getAsString(jsonObject, associationColumnName);
                        if (StringUtils.equalsIgnoreCase(id, associationId)) {
                            jsonArray.add(jsonObject);
                        }

                    }
                }
                return jsonArray;
            }
        }

        return null;
    }

    protected String getId(Object object, String pkPropertyName) {
        if (null == object || StringUtils.isEmpty(pkPropertyName)) {
            throw new ServiceException("指定的参数不能为空");
        }
        String getterMethodName = getGetterMethodName(pkPropertyName);
        try {
            return MethodUtils.invokeMethod(object, getterMethodName, null).toString();
        } catch (Exception e) {
            throw new ServiceException(String.format("获取对象唯一标识%s.%s出错", object.getClass().getSimpleName(), pkPropertyName), e);
        }
    }

    protected String getId(JsonObject jsonObject, EntityRelation relation, String primaryName) {
        return getAsString(jsonObject, primaryName);
    }

    public static String getGetterMethodName(String propertyName) {
        return BeanUtil.getGetterMethod(propertyName);
    }

    public static String getSetterMethodName(String propertyName) {
        return BeanUtil.getSetterMethod(propertyName);
    }

    public static String getAsString(JsonObject object, String memberName) {
        JsonElement element = object.get(memberName);
        if (element == null || element.isJsonNull()) return null;
        return element.getAsString();
    }


    public static void main(String[] args){
        Object val = "";
        String typeName = "date";

        ParameterDefaultConvert convert = new ParameterDefaultConvert();

        System.out.println(convert.convertByTypeName(typeName,val).getKey()+"--->"+convert.convertByTypeName(typeName,val).getValue());
    }
}
