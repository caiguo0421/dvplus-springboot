package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.vehicle.contract.model.VehicleInvoicesGroups;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractDetailGroups;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/3 17:15
 * @Description: 发票明细分组
 */
@Service
public class VehicleInvoicesGroupService implements Command<VehicleInvoicesGroups> {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleInvoicesGroupService.class);
    private static EntityRelation entityRelation;

    static {
        entityRelation = EntityRelation.newInstance(VehicleInvoicesGroupService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleInvoicesGroups> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleInvoicesGroupService.class, "beforeExecute");

        //合同号
        VehicleInvoicesGroups detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContractDetailGroups> masterProxy = entityProxy.getMaster();
        VehicleSaleContractDetailGroups master = masterProxy.getEntity();
        detail.setContractNo(master.getContractNo());

        ContractStopWatch.stop(watch);
    }

    @Override
    public void execute(EntityProxy<VehicleInvoicesGroups> entityProxy) {

    }
}
