package cn.sf_soft.support;

import java.util.*;

/**
 * Entity对比
 *
 * @author caigu
 */
public class CompareEntity {

    private String entityClassName;

    //id的值
    private Object id;

    private Operation operation;

    private List<CompareProperty> properties = new ArrayList<>();

    /**
     * 从对象
     */
    private Map<String, List<CompareEntity>> slaves;

    public CompareEntity(String entityClassName, Operation operation, Object id) {
        this.entityClassName = entityClassName;
        this.operation = operation;
        this.id = id;
    }

    public String getEntityClassName() {
        return entityClassName;
    }

    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public List<CompareProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<CompareProperty> properties) {
        this.properties = properties;
    }

    public void addCompareProperty(String propertyName, String columnName, Object val, Object oriVal) {
        properties.add(new CompareProperty(propertyName, columnName, val, oriVal));
    }

    public void addSlave(CompareEntity compareEntity) {
        String simpleName = compareEntity.getEntityClassName();
        if (null == slaves) {
            slaves = new HashMap<String, List<CompareEntity>>();
        }
        if (slaves.containsKey(simpleName)) {
            slaves.get(simpleName).add(compareEntity);
        } else {
            List<CompareEntity> slaveArray = new ArrayList<CompareEntity>();
            slaveArray.add(compareEntity);
            slaves.put(simpleName, slaveArray);
        }
    }

    public String[] getSlaveNames(){
        if(null != slaves){
            return this.slaves.keySet().toArray(new String[0]);
        }
        return null;
    }

    public List<CompareEntity> getSlaves(String slaveClassName){
        if(null != slaves && !slaves.isEmpty()){
            if(slaves.containsKey(slaveClassName) && null != slaves.get(slaveClassName))
                return Arrays.asList(slaves.get(slaveClassName).toArray(new CompareEntity[0]));
        }
        return null;
    }





    public class CompareProperty {
        private String propertyName;

        private String columnName;

        private Object val;

        private Object oriVal;

        public CompareProperty(String propertyName, String columnName, Object val, Object oriVal) {
            this.propertyName = propertyName;
            this.columnName = columnName;
            this.val = val;
            this.oriVal = oriVal;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public Object getVal() {
            return val;
        }

        public void setVal(Object val) {
            this.val = val;
        }

        public Object getOriVal() {
            return oriVal;
        }

        public void setOriVal(Object oriVal) {
            this.oriVal = oriVal;
        }
    }


    public interface Compare{
        boolean compareProperty(CompareEntity entity, CompareProperty property);
    }
}
