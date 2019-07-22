package cn.sf_soft.vehicle.purchase.service;

import cn.jiguang.common.utils.StringUtils;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.vehicle.purchase.model.VehiclePurchaseContract;
import cn.sf_soft.vehicle.purchase.model.VehiclePurchaseContractDetail;
import cn.sf_soft.vehicle.purchase.model.VehiclePurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/28 10:53
 * @Description:
 */
@Service
public class VehiclePurchaseContractDetailService implements Command<VehiclePurchaseContractDetail> {

    @Autowired
    private BaseDao baseDao;

    private static EntityRelation entityRelation;
    static {
        entityRelation = EntityRelation.newInstance(VehiclePurchaseContractDetailService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehiclePurchaseContractDetail> entityProxy) {
        this.validate(entityProxy);
        //设置合同号
        if(Operation.CREATE == entityProxy.getOperation()){
            EntityProxy<VehiclePurchaseContract> masterProxy = entityProxy.getMaster();
            entityProxy.getEntity().setContractNo(masterProxy.getEntity().getContractNo());
        }
    }

    @Override
    public void execute(EntityProxy<VehiclePurchaseContractDetail> entityProxy) {
        this.declareQuantityHandle(entityProxy);
    }

    private void validate(EntityProxy<VehiclePurchaseContractDetail> entityProxy){
        if(entityProxy.getOperation() != Operation.DELETE){
            VehiclePurchaseContractDetail entity = entityProxy.getEntity();

            String orderId = entity.getOrderId();
            if(StringUtils.isEmpty(orderId)){
                throw new ServiceException("DMS采购订单不能为空");
            }
            VehiclePurchaseOrder order = baseDao.get(VehiclePurchaseOrder.class, orderId);
            if(null == order){
                throw new ServiceException(String.format("DMS采购订单(%s)不存在",orderId));
            }
            Short status = order.getStatus();
            if(status != Constant.DocumentStatus.AGREED){
                throw new ServiceException(String.format("DMS采购订单(%s)状态(%s)异常，DMS采购订单必须为已审", order.getDocumentNo(), status));
            }
            if (null == entity.getDeclareQuantity()){
                throw new ServiceException("申报数量不能为空");
            }
        }
    }

    /**
     * 校验申报数量，校验通过后设置订单中的申报数量
     * @param entityProxy
     */
    private void declareQuantityHandle(EntityProxy<VehiclePurchaseContractDetail> entityProxy){
        Operation operation = entityProxy.getOperation();
        if(operation != Operation.DELETE){
            VehiclePurchaseContractDetail entity = entityProxy.getEntity();
            String orderId = entity.getOrderId();
            if(StringUtils.isEmpty(orderId)){
                throw new ServiceException("DMS采购订单不能为空");
            }

            VehiclePurchaseOrder order = baseDao.get(VehiclePurchaseOrder.class, orderId);
            if(null == order){
                throw new ServiceException(String.format("未找到DMS采购订单(%s)", orderId));
            }

            int status = (int)order.getStatus();
            if(status != Constant.DocumentStatus.AGREED){
                throw new ServiceException(String.format("DMS采购订单(%s)状态(%s)异常", order, status));
            }
            //订单数量
            int vehicleQuantity = null == order.getVehicleQuantity() ? 0 : order.getVehicleQuantity();
            //申报数量
            int declareQuantity = null == order.getDeclareQuantity() ? 0 : order.getDeclareQuantity();
            //终止数量
            int abortQuantity = null == order.getAbortQuantity() ? 0 : order.getAbortQuantity();
            //可申报数量
            int quantity = vehicleQuantity - declareQuantity - abortQuantity;
            if(quantity < 0){
                throw new ServiceException(String.format("DMS采购订单(%s)可申报数量异常", order.getDocumentNo()));
            }
            int curQuantity = null == entity.getDeclareQuantity() ? 0 : entity.getDeclareQuantity();
            if(curQuantity > quantity){
                throw new ServiceException(String.format("DMS采购订单(%s)申报数量必须小于%s", order.getDocumentNo(), quantity));
            }
        }

    }
}
