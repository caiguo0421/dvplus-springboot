package cn.sf_soft.common;

import cn.sf_soft.support.HibernateConfigurationUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

public class BeanUtil {

    private static final byte[] get = "get".getBytes();

    private static final byte[] set = "set".getBytes();


    /**
     * 获取所有属性
     *
     * @param cls 源对象class
     * @return the Field object
     */
    public static Field[] getDeclaredFields(final Class<?> cls) {
        return cls.getDeclaredFields();
    }

    /**
     * 根据属性名获取属性对象
     *
     * @param cls       源对象class
     * @param fieldName 属性名
     * @return the Field object
     */
    public static Field getDeclaredField(final Class<?> cls, String fieldName) {
        return FieldUtils.getDeclaredField(cls, fieldName, true);
    }

    /**
     * 获取属性的值（通过属性的get方法获取，如果没有get方法返回null）
     *
     * @param bean         源对象
     * @param propertyName 属性名
     * @return
     */
    public static Object getPropertyValue(Object bean, String propertyName) {
        return getPropertyValue(bean, propertyName, (Object[]) null);
    }

    /**
     * 获取属性的值（通过属性的get方法获取，如果没有get方法返回null）
     *
     * @param bean         源对象
     * @param propertyName 属性名
     * @param arg          参数
     * @return
     */
    public static Object getPropertyValue(Object bean, String propertyName, Object... arg) {
        try {
            if (null == arg) {
                return MethodUtils.invokeMethod(bean, getGetterMethod(propertyName));
            } else {
                return MethodUtils.invokeMethod(bean, getGetterMethod(propertyName), arg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 执行对象中的方法
     *
     * @param bean       源对象
     * @param methodName 方法名称
     * @param args       参数
     * @return
     * @throws Exception
     */
    public static Object invokeMethod(Object bean, String methodName, Object... args) throws Exception {
        try {
            return MethodUtils.invokeMethod(bean, methodName, args);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据属性名获取get方法
     *
     * @param propertyName 属性名
     * @return get方法
     */
    public static String getGetterMethod(String propertyName) {
        byte[] items = propertyName.getBytes();
        if (items[0] >= 97 && items[0] <= 122) {
            items[0] = (byte) ((char) items[0] - 'a' + 'A');
        }
        byte[] returnValue = new byte[items.length + get.length];
        System.arraycopy(get, 0, returnValue, 0, get.length);
        System.arraycopy(items, 0, returnValue, get.length, items.length);
        return new String(returnValue);
    }

    /**
     * 根据属性名获取set方法
     *
     * @param propertyName 属性名
     * @return set方法
     */
    public static String getSetterMethod(String propertyName) {
        byte[] items = propertyName.getBytes();
        if (items[0] >= 97 && items[0] <= 122) {
            items[0] = (byte) ((char) items[0] - 'a' + 'A');
        }
        byte[] returnValue = new byte[items.length + set.length];
        System.arraycopy(set, 0, returnValue, 0, set.length);
        System.arraycopy(items, 0, returnValue, set.length, items.length);
        return new String(returnValue);
    }

    /**
     * 获取属性的类型字符串
     *
     * @param cls
     * @param propertyName
     * @return
     */
    public static String getPropertyTypeStr(Class<?> cls, String propertyName) {
        return getDeclaredField(cls, propertyName).getType().getName();
    }

    /**
     * 获取属性的类型字符串
     *
     * @param cls
     * @param propertyName
     * @return
     */
    public static String getPropertySimpleTypeStr(Class<?> cls, String propertyName) {
        return getDeclaredField(cls, propertyName).getType().getSimpleName();
    }

    /**
     * 获取属性的类型
     *
     * @param cls
     * @param propertyName
     * @return
     */
    public static Type getPropertyGenericType(Class<?> cls, String propertyName) {
        return getDeclaredField(cls, propertyName).getGenericType();
    }

    /**
     * 根据属性生成对应的无参getter方法获取返回参数类型
     *
     * @param clazz    源对象
     * @param property 对象属性名称
     * @return 对象对应的getter方法的返回参数类型
     */
    public static Type getReturnTypeByProperty(Class<?> clazz, String property) {
        return getReturnTypeByMethod(clazz, getGetterMethod(property));
    }

    /**
     * 根据方法获取返回参数类型
     *
     * @param clazz      源对象
     * @param methodName 方法名称
     * @return 方法的返回参数类型
     */
    public static Type getReturnTypeByMethod(Class<?> clazz, String methodName) {
        if (StringUtils.isEmpty(methodName)) return null;
        try {
            Method method = clazz.getMethod(methodName);
            return method.getGenericReturnType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getValueOfProperty(Object object, String propertyName) throws Exception {
        Exception ex = null;
        try {
            Field field = object.getClass().getDeclaredField(propertyName);
            if(null != field){
                field.setAccessible(true);
                return field.get(object);
            }else{
                ex = new Exception(String.format("对象%s未提供属性%s", object.getClass().getName(), propertyName));
                throw ex;
            }
        } catch (Exception e) {
            if(null != ex){
                throw ex;
            }
            throw new Exception(String.format("读取%s.%s属性值时失败", object.getClass().getName(), propertyName), e);
        }
    }

    public static void setValueOfProperty(Object object, String propertyName, Object value) throws Exception {
        Exception ex = null;
        try {
            Field field = object.getClass().getDeclaredField(propertyName);
            if (null != field) {
                field.setAccessible(true);
                field.set(object, value);
            } else {
                ex = new Exception(String.format("对象%s未提供属性%s", object.getClass().getName(), propertyName));
                throw ex;
            }
        } catch (Exception e) {
            if (null != ex) {
                throw ex;
            }
            throw new Exception(String.format("设置%s属性%s的值时失败：%s",
                    object.getClass().getName(),
                    propertyName,
                    null == value ? null : value.toString()
            ), e);
        }
    }

    public static Field[] getAllField(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<Field>();
        Field[] dFields = clazz.getDeclaredFields();
        if (null != dFields && dFields.length > 0) {
            fieldList.addAll(Arrays.asList(dFields));
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != Object.class) {
            Field[] superFields = getAllField(superClass);
            if (null != superFields && superFields.length > 0) {
                for(Field field:superFields){
                    if(!isOverride(fieldList, field)){
                        fieldList.add(field);
                    }
                }
            }
        }
        return fieldList.toArray(new Field[]{});
    }
    public static boolean isOverride(List<Field> fieldList,Field field){
        for(Field temp:fieldList){
            if(temp.getName().equals(field.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * 将hibernate实体对象属性转换成集合（key：列名；value：值）
     * @param object
     * @return
     */
    public static Map<String, Object> toNativeOfMap(Object object){
        if(null != object){
            PersistentClass persistentClass = HibernateConfigurationUtil.getPersistentClassBySimpleClassName(object.getClass().getSimpleName());
            if(null == persistentClass){
                throw new ServiceException(String.format("实体对象%s为映射", object.getClass().getSimpleName()));
            }
            Iterator<?> it = persistentClass.getPropertyIterator();
            Map<String, Object> map = new HashMap<String, Object>();
            AbstractMap.SimpleEntry<String, Object> entry = getColumnAndValue(object, persistentClass.getIdentifierProperty());
            map.put(entry.getKey(), entry.getValue());
            while(it.hasNext()){
                Object p = it.next();
                if(p instanceof Property){
                    Property property = (Property)p;
                    entry = getColumnAndValue(object, property);
                    map.put(entry.getKey(), entry.getValue());
                }
            }
            return map;
        }
        return null;
    }

    private static AbstractMap.SimpleEntry<String, Object> getColumnAndValue(Object object, Property property){
        Iterator<?> columnIt = property.getColumnIterator();
        String columnName = null;
        while(columnIt.hasNext()){
            Column column = (Column) columnIt.next();
            columnName = column.getName();
            break;
        }
        String name = property.getName();
        Object value = null;
        try {
            value = getValueOfProperty(object, name);
        }catch (Exception ex){
            throw new ServiceException(ex);
        }
        return new AbstractMap.SimpleEntry(columnName, value);
    }
}
