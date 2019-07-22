package cn.sf_soft.vehicle.purchase.service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.support.BaseService;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.vehicle.df.model.VehicleDfSapDelivery;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/28 16:26
 * @Description: 车辆采购跟踪
 */
@Service
public class VehicleDfSapDeliveryService extends BaseService<VehicleDfSapDelivery> {

    private static EntityRelation entityRelation;

    static {
        entityRelation = new EntityRelation(VehicleDfSapDelivery.class, VehicleDfSapDeliveryService.class);
        entityRelation.addSlave("vocdId", VehicleConversionDetailService.class);
        entityRelation.addSlave("vocdId", VehicleOnwayChargeService.class);
    }

    @Autowired
    private BaseDao baseDao;

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleDfSapDelivery> entityProxy) {
        if(entityProxy.getOperation() == Operation.CREATE){
            if(StringUtils.isEmpty(entityProxy.getEntity().getSelfId())){
                entityProxy.getEntity().setSelfId(Tools.newGuid());
            }
        }
        this.validate(entityProxy);
    }

    @Override
    public void execute(EntityProxy<VehicleDfSapDelivery> entityProxy) {
    }

    /**
     * 保存
     * @param parameter
     * @return
     */
    public Map<String, List<Object>> saveDelivery(JsonObject parameter){
        List<EntityProxy<?>> entityProxies = super.save(parameter);
        baseDao.flush();
        VehicleDfSapDelivery entity = (VehicleDfSapDelivery)entityProxies.get(0).getEntity();
        return convertReturnData(entity.getSelfId());
    }


    private Map<String, List<Object>> convertReturnData(String selfId) {
        Map<String, List<Object>> rtnData = new HashMap<>(2);
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("val", selfId);
        List<Object> data = baseDao.listInSql("SELECT * FROM vehicle_df_sap_delivery WHERE self_id=:val", parmMap).getData();
        rtnData.put("vehicle_df_sap_delivery", data);
        data = baseDao.listInSql("SELECT * FROM vehicle_conversion_detail WHERE vocd_id=:val", parmMap).getData();
        rtnData.put("vehicle_onway_comfirm_items", data);
        data = baseDao.listInSql("SELECT * FROM vehicle_onway_charge WHERE vocd_id=:val", parmMap).getData();
        rtnData.put("vehicle_onway_charge", data);
        return rtnData;
    }

    private void validate(EntityProxy<VehicleDfSapDelivery> entityProxy){
        if(entityProxy.getOperation() == Operation.CREATE || entityProxy.getOperation() == Operation.UPDATE){
            VehicleDfSapDelivery entity = entityProxy.getEntity();
            String underpanNo = entity.getUnderpanNo();
            String sapOrderNo = entity.getSapOrderNo();
            //sapOrderNo不为空说明是东风提货单
            if(StringUtils.isNotEmpty(sapOrderNo) && StringUtils.isEmpty(underpanNo)){
                throw new ServiceException("东风提货单的底盘号不能为空");
            }
            if(StringUtils.isNotEmpty(underpanNo)){
                List<List<Object>> list =
                        baseDao.findBySql("select 1 from vehicle_df_sap_delivery where self_id<>? and underpan_no=?",
                        entity.getSelfId(), underpanNo);
                if(null != list && !list.isEmpty()){
                    throw new ServiceException(String.format("底盘号(%s)不能为空", underpanNo));
                }
            }
        }else if(entityProxy.getOperation() == Operation.DELETE){
            throw new ServiceException("提货单禁止删除");
        }
    }


}
