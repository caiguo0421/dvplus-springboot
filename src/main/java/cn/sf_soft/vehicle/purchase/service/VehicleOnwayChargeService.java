package cn.sf_soft.vehicle.purchase.service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.support.BaseService;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.vehicle.df.model.VehicleDfSapDelivery;
import cn.sf_soft.vehicle.df.model.VehicleOnwayCharge;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/2 11:00
 * @Description: 在途费用确认
 */
@Service
public class VehicleOnwayChargeService extends BaseService<VehicleOnwayCharge> {

    private static EntityRelation entityRelation;
    static {
        entityRelation = new EntityRelation(VehicleOnwayCharge.class, VehicleOnwayChargeService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleOnwayCharge> entityProxy) {
        if(entityProxy.getOperation() == Operation.CREATE){
            entityProxy.getEntity().setCostStatus(false);
        }
        if(entityProxy.getOperation() == Operation.CREATE){
            EntityProxy<VehicleDfSapDelivery> master = entityProxy.getMaster();
            entityProxy.getEntity().setVocdId(master.getEntity().getSelfId());
        }
    }

    @Override
    public void execute(EntityProxy<VehicleOnwayCharge> entityProxy) {

    }

    /**
     * 数据校验
     * @param entityProxy
     */
    private void validate(EntityProxy<VehicleOnwayCharge> entityProxy){
        VehicleOnwayCharge entity = entityProxy.getEntity();
        VehicleOnwayCharge oriEntity = entityProxy.getOriginalEntity();
        if(entityProxy.getOperation() == Operation.UPDATE){
            ConfirmStatus status = getStatus(entity);
            ConfirmStatus oriStatus = getStatus(oriEntity);
            boolean costStatus = null == entity.getCostStatus() ? false : entity.getCostStatus().booleanValue();
            boolean oriCostStatus = null == oriEntity.getCostStatus() ? false : oriEntity.getCostStatus().booleanValue();
            if(status == ConfirmStatus.UNCONFIRMED && status.compareTo(oriStatus) != 0){
                if(oriCostStatus){
                    throw new ServiceException(String.format("费用(%s)已报销，不允许反确认",entity.getChargeName()));
                }
            }
            if(costStatus != oriCostStatus){
                throw new ServiceException(String.format("费用(%s)禁止更改报销状态",entity.getChargeName()));
            }
        }else if(entityProxy.getOperation() == Operation.DELETE){
            ConfirmStatus oriStatus = getStatus(oriEntity);
            boolean oriCostStatus = null == oriEntity.getCostStatus() ? false : oriEntity.getCostStatus().booleanValue();
            if(oriStatus == ConfirmStatus.CONFIRMED){
                if(oriCostStatus){
                    throw new ServiceException(String.format("费用(%s)已报销，不允许删除",entity.getChargeName()));
                }
            }
        }

        if(entityProxy.getOperation() == Operation.CREATE || entityProxy.getOperation() == Operation.UPDATE) {
            //如果提货单没有在途确认，禁止确认费用
            ConfirmStatus status = ConfirmStatus.valueOf(entity.getStatus());
            if (status == ConfirmStatus.CONFIRMED) {
                this.valiateRequired(entityProxy);
                EntityProxy<VehicleDfSapDelivery> master = entityProxy.getMaster();
                VehicleDfSapDelivery delivery = master.getEntity();
                if (null == delivery.getComfirmStatus() || delivery.getComfirmStatus() == 0) {
                    throw new ServiceException("提货单未在途确认，禁止确认费用");
                }
            }
        }
    }

    private void valiateRequired(EntityProxy<VehicleOnwayCharge> entityProxy){
        VehicleOnwayCharge entity = entityProxy.getEntity();
        if(entityProxy.getOperation() == Operation.CREATE || entityProxy.getOperation() == Operation.UPDATE){
            if(StringUtils.isEmpty(entity.getChargeId())){
                throw new ServiceException("费用不能为空");
            }
            if(StringUtils.isEmpty(entity.getChargeName())){
                throw new ServiceException("费用名称不能为空");
            }
            if(null == entity.getChargePf()){
                throw new ServiceException(String.format("费用(%s)的预估费用不能为空", entity.getChargeName()));
            }
        }
    }

    private ConfirmStatus getStatus(VehicleOnwayCharge charge){
        Byte status = charge.getStatus();
        if(null == status){
            return ConfirmStatus.UNCONFIRMED;
        }
        ConfirmStatus confirmStatus = ConfirmStatus.valueOf(status);
        if(null == confirmStatus){
            throw new ServiceException(String.format("费用(%s)状态(%s)异常",charge.getChargeName(), status));
        }
        return confirmStatus;
    }
}
