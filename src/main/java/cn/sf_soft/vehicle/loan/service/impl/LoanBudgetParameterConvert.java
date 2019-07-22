package cn.sf_soft.vehicle.loan.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.office.approval.model.VehicleLoanBudgetCharge;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.ParameterDefaultConvert;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetChargeGroup;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetDetailsGroup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.mapping.PersistentClass;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/31 10:00
 * @Description:
 */
@Component
public class LoanBudgetParameterConvert extends ParameterDefaultConvert {

    @Override
    protected List<?> findSlaveObjects(EntityRelation relation, String masterId, Object masterObject) {
        Class<?> clazz = relation.getEntityClass();
        if(clazz == VwVehicleLoanBudgetChargeGroup.class){
            VwVehicleLoanBudgetDetailsGroup group = (VwVehicleLoanBudgetDetailsGroup)masterObject;
            return baseDao.findByHql("from VwVehicleLoanBudgetChargeGroup where documentNo=? and groupId=?", group.getDocumentNo(), group.getGroupId());
        }
        return super.findSlaveObjects(relation, masterId, masterObject);
    }

    @Override
    protected Object findObject(EntityRelation relation, String id, JsonObject jsonObject) {
        Class<?> clazz = relation.getEntityClass();
        if(clazz == VwVehicleLoanBudgetChargeGroup.class){
            String chargeId = jsonObject.get("charge_id").getAsString();
            String groupId = jsonObject.get("group_id").getAsString();
            String documentNo = jsonObject.get("document_no").getAsString();
            String chargeGroupId = jsonObject.get("charge_group_id").getAsString();
            return this.baseDao.findByHql("from VwVehicleLoanBudgetChargeGroup where chargeId=? and groupId=? and documentNo=? and chargeGroupId=?", chargeId, groupId, documentNo, chargeGroupId);
        }else if(clazz == VwVehicleLoanBudgetDetailsGroup.class){
            String groupId = jsonObject.get("group_id").getAsString();
            String documentNo = jsonObject.get("document_no").getAsString();
            return this.baseDao.findByHql("from VwVehicleLoanBudgetDetailsGroup where groupId=? and documentNo=?", groupId, documentNo);
        }
        return super.findObject(relation, id, jsonObject);
    }

    @Override
    protected JsonArray findArray(EntityRelation relation, PersistentClass persistentClass, String associationId, Object associationObject) {
        String tableName = persistentClass.getTable().getName();
        String associationColumnName = getColumnName(persistentClass, relation.getAssociationPropertyName());
        if (null == associationColumnName || null == associationId) {
            throw new ServiceException("指定的关联参数不能为空（" + tableName + "）");
        }
        Class<?> clazz = relation.getEntityClass();
        if(clazz == VwVehicleLoanBudgetChargeGroup.class) {
            VwVehicleLoanBudgetDetailsGroup detailsGroup = (VwVehicleLoanBudgetDetailsGroup)associationObject;
            if (this.parameter.get().has(tableName)) {
                JsonArray array = this.parameter.get().get(tableName).getAsJsonArray();
                if (null != array && !array.isJsonNull()) {
                    JsonArray jsonArray = new JsonArray();
                    for (int j = 0; j < array.size(); j++) {
                        JsonObject jsonObject = array.get(j).getAsJsonObject();
                        if(null != jsonObject && !jsonObject.isJsonNull()){
                            String documentNo = jsonObject.get("document_no").getAsString();
                            String groupId = jsonObject.get("group_id").getAsString();
                            if(StringUtils.equals(documentNo, detailsGroup.getDocumentNo()) && StringUtils.equals(groupId, detailsGroup.getGroupId())){
                                jsonArray.add(jsonObject);
                            }
                        }
                    }
                    return jsonArray;
                }
            }
        }else{
            return super.findArray(relation, persistentClass, associationId, associationObject);
        }

        return null;
    }

    @Override
    protected String getId(Object object, String pkPropertyName) {
        if(object instanceof VwVehicleLoanBudgetChargeGroup){
            VwVehicleLoanBudgetChargeGroup vcGroup = (VwVehicleLoanBudgetChargeGroup)object;
            return vcGroup.getDocumentNo()+","+vcGroup.getGroupId()+","+vcGroup.getChargeId()+","+vcGroup.getChargeGroupId();
        }else if(object instanceof VwVehicleLoanBudgetDetailsGroup){
            VwVehicleLoanBudgetDetailsGroup group = (VwVehicleLoanBudgetDetailsGroup)object;
            return group.getDocumentNo()+","+group.getGroupId();
        }
        return super.getId(object, pkPropertyName);
    }

    @Override
    protected String getId(JsonObject jsonObject, EntityRelation relation, String primaryName) {
        Class<?> clazz = relation.getEntityClass();
        if(clazz == VwVehicleLoanBudgetChargeGroup.class){
            String chargeId = jsonObject.get("charge_id").getAsString();
            String groupId = jsonObject.get("group_id").getAsString();
            String documentNo = jsonObject.get("document_no").getAsString();
            String chargeGroupId = jsonObject.get("charge_group_id").getAsString();
            return documentNo+","+groupId+","+chargeId+","+chargeGroupId;
        }else if(clazz == VwVehicleLoanBudgetDetailsGroup.class){
            String groupId = jsonObject.get("group_id").getAsString();
            String documentNo = jsonObject.get("document_no").getAsString();
            return documentNo+","+groupId;
        }
        return super.getId(jsonObject, relation, primaryName);
    }
}
