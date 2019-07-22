package cn.sf_soft.support;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * @Auther: chenbiao
 * @Date: 2018/6/30 09:46
 * @Description:
 */
public class EntityProxyImpl<T> implements EntityProxy<T>{
    /**
     * 对象操作
     */
    private Operation operation;

    /**
     * 对象实体类
     */
    private Class<?> entityClass;


    /**
     * 主对象
     */
    private EntityProxy<?> master;

    /**
     * 从对象
     */
    private Map<String, List<EntityProxy<?>>> slaves;

    /**
     * 转换前的json对象
     * @see ParameterConverter
     */
    private JsonObject jsonObject;

    /**
     * 更改后的对象
     */
    private T entity;

    /**
     * 更改前的对象
     */
    private T originalEntity;

    private Command<T> command;

    private boolean readOnly = false;

    private EntityProxyImpl(){}

    public EntityProxyImpl(Operation operation, Class<?> entityClass){
        this.operation = operation;
        this.entityClass = entityClass;
    }

    public Operation getOperation(){
        return this.operation;
    }

    public Class<?> getEntityClass(){
        return this.entityClass;
    }

    public void setMaster(EntityProxy<T> master){
        this.master = master;
    }

    protected void setSlaves(Map<String, List<EntityProxy<?>>> slaves){
        if(null != slaves){
            for(String key : slaves.keySet()){
                List<EntityProxy<?>> entityProxies = slaves.get(key);
                if(null != entityProxies && !entityProxies.isEmpty()){
                    for(EntityProxy<?> entityProxy : entityProxies){
                        ((EntityProxyImpl)entityProxy).setMaster(this);
                    }
                }
            }
        }
        this.slaves = slaves;
    }

    public EntityProxy<?> getMaster(){
        return this.master;
    }



    public List<EntityProxy<?>> getSlaves(String slaveClassName){
        if(null != slaves && !slaves.isEmpty()){
            if(slaves.containsKey(slaveClassName) && null != slaves.get(slaveClassName))
                return Arrays.asList(slaves.get(slaveClassName).toArray(new EntityProxy<?>[0]));
        }
        return null;
    }

    public void addSlave(EntityProxy entityProxy){
        String simpleName = entityProxy.getEntityClass().getSimpleName();
        if(null == slaves){
            slaves = new HashMap<String, List<EntityProxy<?>>>();
        }
        ((EntityProxyImpl)entityProxy).setMaster(this);
        if(slaves.containsKey(simpleName)){
            slaves.get(simpleName).add(entityProxy);
        }else{
            List<EntityProxy<?>> slaveArray = new ArrayList<EntityProxy<?>>();
            slaveArray.add(entityProxy);
            slaves.put(simpleName, slaveArray);
        }
    }

    public String[] getSlaveNames(){
        if(null != slaves){
            return this.slaves.keySet().toArray(new String[0]);
        }
        return null;
    }

    public boolean hasSlave(){
        return !(null == this.slaves || this.slaves.isEmpty());
    }

    public JsonObject getJsonObject(){
        return this.jsonObject;
    }

    public Command<T> getService() {
        return this.command;
    }

    protected void setService(Command<T> command){
        this.command = command;
    }

    protected void setJsonObject(JsonObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public T getEntity(){
        return this.entity;
    }

    protected void setEntity(T t){
        this.entity = t;
    }

    protected void setEntity(T t, boolean clone){
        if(null == t){
            this.entity = null;
        }else if(clone){
            if(t instanceof Cloneable){
                this.entity = ObjectUtils.clone(t);
            }else{
                Object target = BeanUtils.instantiate(t.getClass());
                BeanUtils.copyProperties(t, target);
                this.entity = (T)target;
            }
        }else{
            this.entity = t;
        }
    }

    public T getOriginalEntity(){
        return this.originalEntity;
    }

    protected void setOriginalEntity(T t, boolean clone){
        if(null == t){
            this.originalEntity = null;
        }else if(clone){
            if(t instanceof Cloneable){
                this.originalEntity = ObjectUtils.clone(t);
            }else{
                Object target = BeanUtils.instantiate(t.getClass());
                BeanUtils.copyProperties(t, target);
                this.originalEntity = (T)target;
            }
        }else{
            this.originalEntity = t;
        }
    }

    @Override
    public boolean isReadOnly() {
        return this.readOnly;
    }

    protected void setReadOnly(boolean readOnly){
        this.readOnly = readOnly;
    }

    public String toString(){
        StringBuffer buff = new StringBuffer();
        buff.append("op:");
        if(null != this.operation) {
            buff.append(this.operation.name());
        }
        buff.append(" ");

        buff.append("json:");
        if(null != this.jsonObject && !this.jsonObject.isJsonNull()){
            buff.append(this.jsonObject);
        }
        buff.append(" ");

        buff.append("entity:");
        if(null != this.entity){
            buff.append(this.entity);
        }
        buff.append(" ");

        buff.append("originalEntity:");
        if(null != this.originalEntity){
            buff.append(this.originalEntity);
        }
        return buff.toString();
    }
}
