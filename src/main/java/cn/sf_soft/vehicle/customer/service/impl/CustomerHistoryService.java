package cn.sf_soft.vehicle.customer.service.impl;

import cn.sf_soft.common.BeanUtil;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.dao.DefaultTransformerAdapter;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.support.HibernateConfigurationUtil;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.model.BaseCustomerGroups;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjectHistory;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.customer.model.InterestedCustomers;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.Query;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/4 15:33
 * @Description: 客户历史记录服务
 */
@Service
public class CustomerHistoryService {

    private static String FIELDS_SQL =
            "SELECT field_name, field_name_meaning " +
                    "FROM base_related_object_fields " +
                    "WHERE table_name = 'base_related_objects'";

    private static Map<String, String> fields;
    private static Map<String, String> fieldNames;
    private static Map<String, List<String>> properties;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private SyncObjectNameService syncObjectNameService;

    public void WriteCustomerChangeHistory(Object object){
        if(null == object){
            return;
        }
        doCalcVals(object);//处理计算值


        Map<String, Object> oriObject = null;
        if(object instanceof BaseRelatedObjects || object instanceof InterestedCustomers){
            String objectId = null;
            try {
                objectId = FieldUtils.readDeclaredField(object, "objectId", true).toString();
            } catch (IllegalAccessException e) {
                throw new ServiceException(String.format("获取%s.%s的属性值时出错", object.getClass().getSimpleName(), "objectId"));
            }
            if(StringUtils.isEmpty(objectId)){
                return;
            }
            /*List<Object> list = baseDao.findByNewSession("from "+ object.getClass().getSimpleName() +" where objectId=?", objectId);
            if(null == list || list.isEmpty()){
                return;
            }
            oriObject = list.get(0);*/
            String sql = null;
            if(object instanceof BaseRelatedObjects) {
                sql = "select o.*, (select t.customer_group_name from base_customer_groups t where t.customer_group_id=o.customer_group_id) as customer_group_name from base_related_objects o where o.object_id='"+objectId+"'";
            } else if(object instanceof InterestedCustomers){
                sql = "select * from interested_customers where object_id='"+objectId+"'";
            }
            Query query = baseDao.getCurrentSession().createSQLQuery(sql);
            query.setResultTransformer(DefaultTransformerAdapter.INSTANCE);
            List list = query.list();
            if(null == list || list.isEmpty()){
                return;
            }
            oriObject = (Map<String, Object>)list.get(0);
        }else{
            throw new ServiceException(String.format("暂不支持该类型(%s)", object.getClass().getSimpleName()));
        }
        WriteCustomerChangeHistory(object, oriObject);
    }

    private void WriteCustomerChangeHistory(Object object, Map<String, Object> oriObject) {
        initFields();
        boolean syncObjectName = false;
        for(String propertyName : fields.keySet()){
            if(hasProperty(object, propertyName)){
                try {
                    String value = ObjectUtils.toString(BeanUtil.getValueOfProperty(object, propertyName), StringUtils.EMPTY);
                    String oriValue = ObjectUtils.toString(oriObject.get(propertyName), StringUtils.EMPTY);
                    if(!StringUtils.equals(value, oriValue)){
                        BaseRelatedObjectHistory history = new BaseRelatedObjectHistory();
                        history.setHistoryId(Tools.newGuid());
                        SysUsers user = HttpSessionStore.getSessionUser();
                        history.setStationId(user.getDefaulStationId());
                        history.setObjectId(ObjectUtils.toString(BeanUtil.getValueOfProperty(object, "objectId"), StringUtils.EMPTY));
                        history.setValueBefore(oriValue);
                        history.setValueAfter(value);
                        history.setModifyTime(TimestampUitls.getTime());
                        history.setModifier(user.getUserFullName());
                        history.setFieldName(fieldNames.get(propertyName));
                        history.setFieldNameMeaning(fields.get(propertyName));
                        baseDao.save(history);
                        if(!syncObjectName && "objectName".equals(propertyName)){
                            syncObjectName = true;
                        }
                    }
                }catch(Exception e){
                    throw new ServiceException(String.format("设置往来对象（%s）历史数据出错", fields.get(propertyName)), e);
                }
            }
        }
        if(syncObjectName){
            this.syncObjectNameService.sync(object);
        }
    }

    /**
     * 处理计算值
     * @author caigx
     * @param relatedObjects
     */
    private void doCalcVals(Object relatedObjects) {
        if (relatedObjects == null) {
            return;
        }
        if (!(relatedObjects instanceof BaseRelatedObjects)) {
            return;
        }
        //计算字段customerGroupName写入到relatedObjects中
        if (StringUtils.isBlank(((BaseRelatedObjects)relatedObjects).getCustomerGroupId())) {
            ((BaseRelatedObjects)relatedObjects).setCustomerGroupName(null);
        } else {
            BaseCustomerGroups groups = baseDao.get(BaseCustomerGroups.class, ((BaseRelatedObjects)relatedObjects).getCustomerGroupId());
            ((BaseRelatedObjects)relatedObjects).setCustomerGroupName(groups == null ? null : groups.getCustomerGroupName());
        }

    }

    private boolean hasProperty(Object object, String propertyName){
        if(null == properties){
            properties = new HashMap<String, List<String>>();
        }
        String key = object.getClass().getSimpleName();
        if(!properties.containsKey(key)){
            List<String> list = new ArrayList();
            PersistentClass persistentClass = HibernateConfigurationUtil.getPersistentClassBySimpleClassName(key);
            Iterator<Property> it = persistentClass.getPropertyIterator();
            while(it.hasNext()){
                Property property = it.next();
                list.add(property.getName());
            }
            properties.put(key, list);
        }
        return properties.get(key).contains(propertyName);
    }

    private void initFields(){
        if(null == fields){
            fields = new HashMap<String, String>();
            fieldNames = new HashMap<String, String>();
            List<List<Object>> fields = baseDao.findBySql(FIELDS_SQL,null);
            if(null != fields && !fields.isEmpty()){
                for(int i=0; i<fields.size(); i++){
                    String fieldName = fields.get(i).get(0).toString();
                    CustomerHistoryService.fields.put(Tools.underline2SmallCamel(fieldName), fields.get(i).get(1).toString());
                    CustomerHistoryService.fieldNames.put(Tools.underline2SmallCamel(fieldName), fieldName);
                }
            }
        }
    }



    /*public void WriteCustomerChangeHistory(BaseRelatedObjects object){
        if(null == object || StringUtils.isEmpty(object.getObjectId())){
            return;
        }
        doCalcVals(object);//处理计算值
        String objectId = object.getObjectId();
        List<BaseRelatedObjects> list = baseDao.findByNewSession("from BaseRelatedObjects where objectId=?", objectId);
        if(null == list || list.isEmpty()){
            return;
        }
        BaseRelatedObjects oriObject = list.get(0);
        WriteCustomerChangeHistory(object, oriObject);
    }

    private void WriteCustomerChangeHistory(BaseRelatedObjects object, BaseRelatedObjects oriObject) {
        initFields();
        for(String propertyName : fields.keySet()){
            if(hasProperty(propertyName)){
                try {
                    String value = ObjectUtils.toString(BeanUtil.getValueOfProperty(object, propertyName), StringUtils.EMPTY);
                    String oriValue = ObjectUtils.toString(BeanUtil.getValueOfProperty(oriObject, propertyName), StringUtils.EMPTY);
                    if(!StringUtils.equals(value, oriValue)){
                        BaseRelatedObjectHistory history = new BaseRelatedObjectHistory();
                        history.setHistoryId(Tools.newGuid());
                        SysUsers user = HttpSessionStore.getSessionUser();
                        history.setStationId(user.getDefaulStationId());
                        history.setObjectId(object.getObjectId());
                        history.setValueBefore(oriValue);
                        history.setValueAfter(value);
                        history.setModifyTime(TimestampUitls.getTime());
                        history.setModifier(user.getUserFullName());
                        history.setFieldName(fieldNames.get(propertyName));
                        history.setFieldNameMeaning(fields.get(propertyName));
                        baseDao.save(history);
                    }
                }catch(Exception e){
                    throw new ServiceException(String.format("设置往来对象（%s）历史数据出错", fields.get(propertyName)), e);
                }
            }
        }
    }

    *//**
     * 处理计算值
     * @author caigx
     * @param relatedObjects
     *//*
    private void doCalcVals(BaseRelatedObjects relatedObjects){
        if(relatedObjects==null){
            return;
        }
        //计算字段customerGroupName写入到relatedObjects中
        if (StringUtils.isBlank(relatedObjects.getCustomerGroupId())) {
            relatedObjects.setCustomerGroupName(null);
        } else {
            BaseCustomerGroups groups = baseDao.get(BaseCustomerGroups.class, relatedObjects.getCustomerGroupId());
            relatedObjects.setCustomerGroupName(groups == null ? null : groups.getCustomerGroupName());
        }
    }

    private boolean hasProperty(String propertyName){
        if(null == properties){
            properties = new ArrayList<String>();
            PersistentClass persistentClass = HibernateConfigurationUtil.getPersistentClassBySimpleClassName(BaseRelatedObjects.class.getSimpleName());
            Iterator<Property> it = persistentClass.getPropertyIterator();
            while(it.hasNext()){
                Property property = it.next();
                properties.add(property.getName());
            }
        }
        return properties.contains(propertyName);
    }

    private void initFields(){
        if(null == fields){
            fields = new HashMap<String, String>();
            fieldNames = new HashMap<String, String>();
            List<List<Object>> fields = baseDao.findBySql(FIELDS_SQL,null);
            if(null != fields && !fields.isEmpty()){
                for(int i=0; i<fields.size(); i++){
                    String fieldName = fields.get(i).get(0).toString();
                    CustomerHistoryService.fields.put(Tools.underline2SmallCamel(fieldName), fields.get(i).get(1).toString());
                    CustomerHistoryService.fieldNames.put(Tools.underline2SmallCamel(fieldName), fieldName);
                }
            }
        }
    }*/
}
