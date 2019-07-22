package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractPresent;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/3 17:18
 * @Description: 销售合同精品明细
 */
@Service
public class VehicleSaleContractPresentService implements Command<VehicleSaleContractPresent> {

    Logger logger = LoggerFactory.getLogger(VehicleSaleContractPresentService.class);

    private static EntityRelation entityRelation;

    static {
        entityRelation = EntityRelation.newInstance(VehicleSaleContractPresentService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleSaleContractPresent> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractPresentService.class, "beforeExecute");

        //设置冗余信息（合同号）
        VehicleSaleContractPresent detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContractDetail> masterProxy = entityProxy.getMaster();
        VehicleSaleContractDetail master = masterProxy.getEntity();
        detail.setContractNo(master.getContractNo());

        Operation operation = entityProxy.getOperation();
        if(operation == Operation.CREATE || operation == Operation.UPDATE){
            VehicleSaleContractPresent entity = entityProxy.getEntity();
            if(null == entity.getPlanQuantity()){
                entity.setPlanQuantity(BigDecimal.ZERO);
            }
            if(null == entity.getGetQuantity()){
                entity.setGetQuantity(BigDecimal.ZERO);
            }
        }
        ContractStopWatch.stop(watch);

    }

    @Override
    public void execute(EntityProxy<VehicleSaleContractPresent> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractPresentService.class, "execute");
        this.validate(entityProxy);
        ContractStopWatch.stop(watch);
    }

    /**
     * 校验精品
     * @param entityProxy
     */
    private void validate(EntityProxy<VehicleSaleContractPresent> entityProxy) {
        Operation op = entityProxy.getOperation();
        logger.debug("校验销售合同精品{}", entityProxy);
        if(op == Operation.CREATE || op == Operation.UPDATE){
            VehicleSaleContractPresent entity = entityProxy.getEntity();

            if(entity.getPlanQuantity().compareTo(BigDecimal.ZERO) <= 0){
                throw new ServiceException(String.format("编码为【%s】的精品计领数量必须大于零，请重新填写！", entity.getPartNo()));
            }
            if(entity.getPlanQuantity().compareTo(entity.getGetQuantity())< 0){
                throw new ServiceException(String.format("编码为【%s】的精品计领数量不能小于实领数量, 请重新填写！", entity.getPartNo()));
            }

        }
    }
}
