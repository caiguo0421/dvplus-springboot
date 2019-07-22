package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractDetailGroups;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractInsuranceGroups;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractItemGroups;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/3 17:12
 * @Description: 外委预估改装分组
 */
@Service
public class VehicleSaleContractItemGroupService implements Command<VehicleSaleContractItemGroups> {
    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleSaleContractItemGroupService.class);

    private static EntityRelation entityRelation;
    static {
        entityRelation = EntityRelation.newInstance(VehicleSaleContractItemGroupService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleSaleContractItemGroups> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractItemGroupService.class, "beforeExecute");

        //合同号
        VehicleSaleContractItemGroups detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContractDetailGroups> masterProxy = entityProxy.getMaster();
        VehicleSaleContractDetailGroups master = masterProxy.getEntity();
        detail.setContractNo(master.getContractNo());

        ContractStopWatch.stop(watch);
    }

    @Override
    public void execute(EntityProxy<VehicleSaleContractItemGroups> entityProxy) {

    }
}
