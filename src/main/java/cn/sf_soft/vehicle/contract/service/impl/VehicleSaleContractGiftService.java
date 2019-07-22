package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractGifts;
import cn.sf_soft.office.approval.model.VehicleSaleContractItem;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import org.springframework.stereotype.Service;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/5 11:59
 * @Description: 销售合同厂家赠品服务
 */
@Service
public class VehicleSaleContractGiftService implements Command<VehicleSaleContractGifts> {

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleSaleContractGiftService.class);
    private static EntityRelation entityRelation;
    static {
        entityRelation = EntityRelation.newInstance(VehicleSaleContractGiftService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleSaleContractGifts> entityProxy) {
        //合同号
        VehicleSaleContractGifts detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContractDetail> masterProxy = entityProxy.getMaster();
        VehicleSaleContractDetail master = masterProxy.getEntity();
        detail.setContractNo(master.getContractNo());
        logger.debug(String.format("%s的beforeExecute完成，id：%s",entityProxy.getEntityClass().getSimpleName(),detail.getGiftId()));
    }

    @Override
    public void execute(EntityProxy<VehicleSaleContractGifts> entityProxy) {

    }
}
