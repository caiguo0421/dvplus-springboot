package cn.sf_soft.support;
import cn.sf_soft.common.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2018/6/30 10:04
 * @Description: 对象关系
 */
public class EntityRelation implements Cloneable{
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * 当前对象类型
     */
    private Class<?> entityClass;

    /**
     * 对象主键属性名
     */
    private String identifierPropertyName;

//    /**
//     * 主对象
//     */
//    EntityRelation master;

    /**
     * 与主对象的关联ID
     */
    private String associationPropertyName;

    /**
     * 对象的服务
     */
    private Class<?> service;

    private List<SlaveEntityRelation> slaveEntityRelations;

    private boolean readOnly = false;

    private EntityRelation(){}

    public EntityRelation(Class<?> entityClass, String identifierPropertyName, Class<?> service){
        this.entityClass = entityClass;
        this.identifierPropertyName = identifierPropertyName;
        this.service = service;
    }

    public EntityRelation(Class<?> entityClass, Class<?> service){
        this.entityClass = entityClass;
        this.service = service;
    }

    public EntityRelation(Class<?> entityClass, Class<?> service, boolean readOnly){
        this.entityClass = entityClass;
        this.service = service;
        this.readOnly = readOnly;
    }


    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getIdentifierPropertyName() {
        return identifierPropertyName;
    }

    public void setIdentifierPropertyName(String identifierPropertyName) {
        this.identifierPropertyName = identifierPropertyName;
    }


    public String getAssociationPropertyName() {
        return associationPropertyName;
    }

    protected void setAssociationPropertyName(String associationPropertyName){
        this.associationPropertyName = associationPropertyName;
    }


    public List<EntityRelation> getSlaves() {
        if(null == slaveEntityRelations || slaveEntityRelations.isEmpty()){
            return null;
        }else{
            List<EntityRelation> slaves = new ArrayList<EntityRelation>();
            for(SlaveEntityRelation slave : this.slaveEntityRelations){
                Class<?> entityClass = slave.service;
                try {
                    Object serv = ApplicationUtil.getBean(slave.service);
                    if (null != serv && (serv instanceof Command)) {

                        EntityRelation relation = (EntityRelation) ((Command) serv).getEntityRelation().clone();
                        relation.setAssociationPropertyName(slave.associationPropertyName);
                        slaves.add(relation);
                    }
                } catch (Exception e) {
                    throw new ServiceException(String.format("获取对象（%s）关联出错", entityClass.getSimpleName()),e);
                }
            }
            return slaves;
        }
    }

    public EntityRelation addSlave(String associationPropertyName, Class<? extends Command> slaveService){
        if(StringUtils.isEmpty(associationPropertyName)){
            throw new RuntimeException("必须提供关联属性名");
        }
        if(null == slaveService){
            throw new RuntimeException("必须提供服务");
        }
        if(null == slaveEntityRelations){
            slaveEntityRelations = new ArrayList<SlaveEntityRelation>();
        }
        slaveEntityRelations.add(new SlaveEntityRelation(associationPropertyName, slaveService));
        return this;
    }

    public Class<?> getService() {
        return service;
    }

    public void setService(Class<Command> service) {
        this.service = service;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public static EntityRelation newInstance(Class<? extends Command> clazz, boolean readOnly){
        Type[] genType = clazz.getGenericInterfaces();
        Class<?> entityClass = null;
        for(Type type : genType){
            if(((ParameterizedType) type).getRawType() == Command.class){
                Type[] params = ((ParameterizedType) type).getActualTypeArguments();
                entityClass = ((Class)params[0]);
            }
        }
        if(null != entityClass){
            return new EntityRelation(entityClass, clazz, readOnly);
        }
        throw new RuntimeException(String.format("服务%s配置有误", clazz));
    }

    public static EntityRelation newInstance(Class<? extends Command> clazz){
        return newInstance(clazz, false);
    }

    class SlaveEntityRelation {
        /**
         * 与主对象的关联ID
         */
        private String associationPropertyName;

        /**
         * 对象的服务
         */
        private Class<?> service;
        protected SlaveEntityRelation(){
        }
        protected SlaveEntityRelation(String associationPropertyName, Class<?> service){
            this.associationPropertyName = associationPropertyName;
            this.service = service;
        }

        protected String getAssociationPropertyName() {
            return associationPropertyName;
        }

        protected void setAssociationPropertyName(String associationPropertyName) {
            this.associationPropertyName = associationPropertyName;
        }

        protected Class<?> getService() {
            return service;
        }

        protected void setService(Class<?> service) {
            this.service = service;
        }
    }
}
