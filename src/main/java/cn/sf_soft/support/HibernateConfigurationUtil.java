package cn.sf_soft.support;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.sf_soft.common.ServiceException;
import org.hibernate.boot.Metadata;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 * Hibernate Configuration Util
 * @author chenbiao
 *
 */
public class HibernateConfigurationUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static Configuration configuration;

    private static Metadata metadata;

    //key为simpleClassName
    private static Map<String, PersistentClass> simpleClassNamePersistentClass = null;
    //key为jpaEntityName
    //private static Map<String, PersistentClass> jpaEntityNamePersistentClass = null;

    public static Configuration getConfiguration() {
        if (configuration == null) {
            // 取sessionFactory的时候要加上&
            LocalSessionFactoryBean factory = (LocalSessionFactoryBean) applicationContext
                    .getBean("&sessionFactory");
            configuration = factory.getConfiguration();
        }
        return configuration;
    }

    public static Metadata getMetadata(){
        if(metadata==null) {
            // 取sessionFactory的时候要加上&
            LocalSessionFactoryBean factory = (LocalSessionFactoryBean) applicationContext
                    .getBean("&sessionFactory");
            metadata = factory.getMetadataSources().getMetadataBuilder().build();
        }
        return metadata;
    }

    public static PersistentClass getPersistentClassBySimpleClassName(String simpleClassName) {
        PersistentClass persistentClass = simpleClassNamePersistentClass.get(simpleClassName);
        if(null == persistentClass){
            throw new ServiceException(String.format("未找到业务对象的实体类:%s", simpleClassName));
        }
        return persistentClass;
    }

   /* public static PersistentClass getPersistentClassByJpaEntityName(String jpaEntityName){
        return jpaEntityNamePersistentClass.convert(jpaEntityName);
    }*/


//    public static PersistentClass getPersistentClassByTable(String tableName){
//        Configuration configuration = getConfiguration();
//        Iterator<PersistentClass> it = configuration.getClassMappings();
//        while (it.hasNext()) {
//            PersistentClass persistentClass = it.next();
//            if(persistentClass.getTable().getName().equals(tableName)){
//                return persistentClass;
//            }
//        }
//        return null;
//    }

    public static PersistentClass getPersistentClassByTable(String tableName){
        Metadata metadata = getMetadata();
        Collection<PersistentClass> it = metadata.getEntityBindings();
        for (PersistentClass persistentClass:it) {
            if(persistentClass.getTable().getName().equals(tableName)){
                return persistentClass;
            }
        }

        return null;
    }


    public static <T> PersistentClass getPersistentClass(Class<T> clazz) {
        synchronized (HibernateConfigurationUtil.class) {
            PersistentClass pc = getMetadata().getEntityBinding(clazz.getName());
            if (pc == null) {
                configuration = configuration.addClass(clazz);
                pc = getMetadata().getEntityBinding(clazz.getName());
            }
            return pc;
        }
    }

    /**
     * 获得实体类对应的表名
     *
     * @param clazz
     *            实体类的Class对象
     * @return 表名
     */
    public static <T> String getTableName(Class<T> clazz) {
        return getPersistentClass(clazz).getTable().getName();
    }

    /**
     * 获得实体类对应表的主键字段名称
     *
     * @param clazz
     *            实体类的Class对象
     * @return 主键字段名称
     */
    public static <T> String getIdentifierColumnName(Class<T> clazz) {
        return getPersistentClass(clazz).getTable().getPrimaryKey()
                .getColumn(0).getName();
    }

    /**
     * 获取实体类对应的主键字段名称
     * @param clazz 实体类的Class对象
     * @return 主键字段名称
     */
    public static <T> String getIdentifierPropertyName(Class<?> clazz){
        return getPersistentClass(clazz).getIdentifierProperty().getName();
    }

    /**
     * 根据关联表名查找对应的属性
     * @param clazz 类
     * @param tableName 关联表名
     * @return 返回属性
     */
    public static <T> Property getPropertyByCollectionTable(Class<T> clazz, String tableName){
        PersistentClass persistentClass = getPersistentClass(clazz);
        Iterator<?> iterator = persistentClass.getPropertyIterator();
        while(iterator.hasNext()){
            Property p = (Property)iterator.next();
            if(p.getValue() instanceof org.hibernate.mapping.Bag){
                org.hibernate.mapping.Bag bag = (org.hibernate.mapping.Bag)p.getValue();
                if(null != bag.getCollectionTable()){
                    if(bag.getCollectionTable().getName().equalsIgnoreCase(tableName)){
                        return p;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取属性名,先从主键中查找，再从一般属性和集合属性中找
     * @param clazz 类
     * @param columnName 列名
     * @return 返回属性
     */
    public static <T> Property getProperty(Class<T> clazz, String columnName){
        String pkColumnName = getIdentifierColumnName(clazz);
        if(pkColumnName.equals(columnName)){
            PersistentClass persistentClass = getPersistentClass(clazz);
            Property identifierProperty = persistentClass.getIdentifierProperty();
            Iterator<?> identifierIterator = identifierProperty.getColumnIterator();
            while(identifierIterator.hasNext()){
                Column column = (Column) identifierIterator.next();
                if(column.getName().equalsIgnoreCase(columnName)){
                    return identifierProperty;
                }
            }
            return null;
        }
        PersistentClass persistentClass = getPersistentClass(clazz);
        Iterator<?> iterator = persistentClass.getPropertyIterator();
        while(iterator.hasNext()){
            Property p = (Property)iterator.next();
            Iterator<?> it = p.getColumnIterator();
            if(it.hasNext()){
                Column column = (Column) it.next();
                if(column.getName().equalsIgnoreCase(columnName)){
                    return p;
                }
            }else{
                if(p.getValue() instanceof org.hibernate.mapping.Bag){
                    org.hibernate.mapping.Bag bag = (org.hibernate.mapping.Bag)p.getValue();
                    if(null != bag.getCollectionTable()){
                        if(bag.getCollectionTable().getName().equalsIgnoreCase(columnName)){
                            return p;
                        }
                    }
                }
            }
        }
        return null;
    }


    /**
     * 获取子对象设置主对象的属性
     * @param parentClazz 主对象
     * @param childClazz 子对象
     * @return 返回子对象设置主对象的属性
     */
    @SuppressWarnings("rawtypes")
    public static <T> Property getReferencedProperty(Class<?> parentClazz, Class<?> childClazz){
        PersistentClass persistentClass = HibernateConfigurationUtil.getPersistentClass(childClazz);
        Iterator it = persistentClass.getDeclaredPropertyIterator();
        while(it.hasNext()){
            Property property = (Property)it.next();
            if(property.getType() instanceof org.hibernate.type.ManyToOneType){
                String referencedEntityName = ((org.hibernate.mapping.ManyToOne)property.getValue()).getReferencedEntityName();
                if(parentClazz.getName().equals(referencedEntityName)){
                    return property;
                }
            }
        }
        return null;
    }

//    @Override
//    public void setApplicationContext(ApplicationContext context) throws BeansException {
//        applicationContext = context;
//
//        //初始化-----
//        Iterator<PersistentClass> it = getConfiguration().getClassMappings();
//        simpleClassNamePersistentClass = new HashMap<String, PersistentClass>();
//        //jpaEntityNamePersistentClass = new HashMap<String, PersistentClass>();
//        while (it.hasNext()) {
//            PersistentClass persistentClass = it.next();
//            simpleClassNamePersistentClass.put(persistentClass.getMappedClass().getSimpleName(), persistentClass);
//            //jpaEntityNamePersistentClass.put(persistentClass.getJpaEntityName(), persistentClass);
//        }
//
//
//    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;

        Metadata metadata = getMetadata();
        Collection<PersistentClass> it = metadata.getEntityBindings();
        for (PersistentClass persistentClass:it) {
            simpleClassNamePersistentClass.put(persistentClass.getMappedClass().getSimpleName(), persistentClass);

        }


    }

}
