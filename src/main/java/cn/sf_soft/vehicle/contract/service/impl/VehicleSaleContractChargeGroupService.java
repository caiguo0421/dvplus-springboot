package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractChargeGroups;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractDetailGroups;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/3 17:13
 * @Description: 销售合同费用分组
 */
@Service
public class VehicleSaleContractChargeGroupService implements Command<VehicleSaleContractChargeGroups> {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleSaleContractChargeGroupService.class);

    private static EntityRelation entityRelation;
    static {
        entityRelation = EntityRelation.newInstance(VehicleSaleContractChargeGroupService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }
    @Override
    public void beforeExecute(EntityProxy<VehicleSaleContractChargeGroups> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractChargeGroupService.class, "beforeExecute");
        //合同号
        VehicleSaleContractChargeGroups detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContractDetailGroups> masterProxy = entityProxy.getMaster();
        VehicleSaleContractDetailGroups master = masterProxy.getEntity();
        detail.setContractNo(master.getContractNo());

        if(entityProxy.getOperation() == Operation.CREATE){
            if(detail.getPaidByBill()==null){
                detail.setPaidByBill(false);
            }
        }
        ContractStopWatch.stop(watch);
    }

    @Override
    public void execute(EntityProxy<VehicleSaleContractChargeGroups> entityProxy) {

    }
}
