package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractDetailGroups;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractInsuranceGroups;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/3 17:10
 * @Description: 销售合同保险分组服务
 */
@Service
public class VehicleSaleContractInsuranceGroupService implements Command<VehicleSaleContractInsuranceGroups> {

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleSaleContractInsuranceGroupService.class);

    private static EntityRelation entityRelation;
    static {
        entityRelation = EntityRelation.newInstance(VehicleSaleContractInsuranceGroupService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleSaleContractInsuranceGroups> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractInsuranceGroupService.class, "beforeExecute");
        //合同号
        VehicleSaleContractInsuranceGroups detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContractDetailGroups> masterProxy = entityProxy.getMaster();
        VehicleSaleContractDetailGroups master = masterProxy.getEntity();
        detail.setContractNo(master.getContractNo());

        ContractStopWatch.stop(watch);
    }

    @Override
    public void execute(EntityProxy<VehicleSaleContractInsuranceGroups> entityProxy) {

    }
}
