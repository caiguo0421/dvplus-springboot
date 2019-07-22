package cn.sf_soft.vehicle.purchase.service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractItemGroups;
import cn.sf_soft.vehicle.purchase.model.VehiclePurchaseOrder;
import cn.sf_soft.vehicle.purchase.model.VehiclePurchaseOrderConversion;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


/**
 * 车辆采购订单差异化改装
 *
 * @author caigx
 */
@Service
public class VehiclePurchaseOrderConversionService implements Command<VehiclePurchaseOrderConversion> {

    @Autowired
    private BaseDao baseDao;


    private static EntityRelation entityRelation;

    static {
        entityRelation = EntityRelation.newInstance(VehiclePurchaseOrderConversionService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }




    @Override
    public void beforeExecute(EntityProxy<VehiclePurchaseOrderConversion> entityProxy) {
        if (entityProxy.getOperation() == Operation.CREATE) {
            SysUsers user = HttpSessionStore.getSessionUser();
            //订单编码
            EntityProxy<VehiclePurchaseOrder> masterProxy = entityProxy.getMaster();
            VehiclePurchaseOrderConversion orderConversion = entityProxy.getEntity();
            orderConversion.setDocumentNo(masterProxy.getEntity().getDocumentNo());

            orderConversion.setCreator(user.getUserFullName());
            orderConversion.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        validateOrderConversion(entityProxy);

    }

    private void validateOrderConversion(EntityProxy<VehiclePurchaseOrderConversion> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            EntityProxy<VehiclePurchaseOrder> masterProxy = entityProxy.getMaster();
            VehiclePurchaseOrder purchaseOrder = masterProxy.getEntity();

            VehiclePurchaseOrderConversion oriOrderConversion = entityProxy.getOriginalEntity();
            if (purchaseOrder.getOrderType() == OrderType.CONTRACT.getCode()) {
                if (!StringUtils.isBlank(oriOrderConversion.getItemGroupId())) {
                    throw new ServiceException("不能删除销售合同已有的改装项目：" + oriOrderConversion.getItemName());
                }
            }
        } else {
            EntityProxy<VehiclePurchaseOrder> masterProxy = entityProxy.getMaster();
            VehiclePurchaseOrder purchaseOrder = masterProxy.getEntity();
            VehiclePurchaseOrderConversion orderConversion = entityProxy.getEntity();

            if (purchaseOrder.getOrderType() == OrderType.CONTRACT.getCode()) {
                if (StringUtils.isNotBlank(orderConversion.getItemGroupId())) {
                    VehicleSaleContractItemGroups itemGroup = baseDao.get(VehicleSaleContractItemGroups.class, orderConversion.getItemGroupId());
                    //销售合同的修改自动更新关联到的采购订单信息
                    orderConversion.setItemId(itemGroup.getItemId());
                    orderConversion.setItemNo(itemGroup.getItemNo());
                    orderConversion.setItemName(itemGroup.getItemName());
                    orderConversion.setSupplierId(itemGroup.getSupplierId());
                    orderConversion.setSupplierNo(itemGroup.getSupplierNo());
                    orderConversion.setSupplierName(itemGroup.getSupplierName());
                    orderConversion.setItemCost(itemGroup.getItemCost());
                }
            }
            //校验
            if (StringUtils.isBlank(orderConversion.getItemId()) || StringUtils.isBlank(orderConversion.getItemName())) {
                throw new ServiceException("改装项目为空");
            }

//            if (StringUtils.isBlank(orderConversion.getSupplierId())) {
//                throw new ServiceException("改装项目:" + orderConversion.getItemName() + "的供应商为空");
//            }

            if (Tools.toBigDecimal(orderConversion.getItemCost()).compareTo(BigDecimal.ZERO) <= 0) {
                throw new ServiceException("预估成本不能小于0");
            }
        }
    }

    @Override
    public void execute(EntityProxy<VehiclePurchaseOrderConversion> entityProxy) {

    }


}
