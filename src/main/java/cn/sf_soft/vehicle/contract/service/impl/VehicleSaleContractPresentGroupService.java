package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractDetailGroups;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractPresentGroups;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/3 17:11
 * @Description: 销售合同精品分组
 */
@Service
public class VehicleSaleContractPresentGroupService implements Command<VehicleSaleContractPresentGroups> {
    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleSaleContractPresentGroupService.class);

    private static EntityRelation entityRelation;
    static {
        entityRelation = EntityRelation.newInstance(VehicleSaleContractPresentGroupService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleSaleContractPresentGroups> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractPresentGroupService.class, "beforeExecute");

        //合同号
        VehicleSaleContractPresentGroups detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContractDetailGroups> masterProxy = entityProxy.getMaster();
        VehicleSaleContractDetailGroups master = masterProxy.getEntity();
        detail.setContractNo(master.getContractNo());
        ContractStopWatch.stop(watch);
    }

    @Override
    public void execute(EntityProxy<VehicleSaleContractPresentGroups> entityProxy) {

    }
}
