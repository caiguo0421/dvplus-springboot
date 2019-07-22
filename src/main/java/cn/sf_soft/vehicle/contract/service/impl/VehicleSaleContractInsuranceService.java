package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractInsurance;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/3 17:16
 * @Description: 销售合同保险明细
 */
@Service
public class VehicleSaleContractInsuranceService implements Command<VehicleSaleContractInsurance> {

    private static Logger logger = LoggerFactory.getLogger(VehicleSaleContractInsuranceService.class);

    private static String INSURANCE_EXISTS_SQL = "SELECT count(1)  FROM dbo.insurance a\n" + "LEFT JOIN dbo.vehicle_sale_contract_detail b ON a.contract_detail_id=b.contract_detail_id\n" + "LEFT JOIN dbo.vehicle_sale_contract_detail_groups c ON b.group_id=c.group_id\n" + "LEFT JOIN dbo.insurance_detail d ON a.insurance_no=d.insurance_no\n" + "where  sale_contract_insurance_id =?";

    @Autowired
    private BaseDao baseDao;

    private static EntityRelation entityRelation;

    static {
        entityRelation = EntityRelation.newInstance(VehicleSaleContractInsuranceService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleSaleContractInsurance> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractInsuranceService.class, "beforeExecute");

        //合同号
        VehicleSaleContractInsurance detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContractDetail> masterProxy = entityProxy.getMaster();
        VehicleSaleContractDetail master = masterProxy.getEntity();
        detail.setContractNo(master.getContractNo());

        ContractStopWatch.stop(watch);
    }

    @Override
    public void execute(EntityProxy<VehicleSaleContractInsurance> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractInsuranceService.class, "execute");

        this.validate(entityProxy);

        ContractStopWatch.stop(watch);
    }

    private void validate(EntityProxy<VehicleSaleContractInsurance> entityProxy) {
        Operation op = entityProxy.getOperation();
        logger.debug("校验销售合同精品{}", entityProxy);
        if (op == Operation.CREATE || op == Operation.UPDATE) {
            VehicleSaleContractInsurance entity = entityProxy.getEntity();
            if (null == entity.getCategoryIncome()) {
                entity.setCategoryIncome(BigDecimal.ZERO);
            }
            if (entity.getCategoryIncome().compareTo(BigDecimal.ZERO) < 0) {
                throw new ServiceException(String.format("险种【%s】金额不能小于零，请修改后再尝试保存", entity.getCategoryName()));
            }
            VehicleSaleContractInsurance oriEntity = entityProxy.getOriginalEntity();
            if (op == Operation.UPDATE) {
                double categoryIncome = null == oriEntity.getCategoryIncome() ? 0 : oriEntity.getCategoryIncome().doubleValue();

                Integer result = baseDao.findUniqueBySql(INSURANCE_EXISTS_SQL, entity.getSaleContractInsuranceId());
                if (null != result && result > 0) {
                    if (entity.getCategoryIncome().doubleValue() != categoryIncome) {
                        throw new ServiceException(String.format("险种【%s】已建立保险单，不能变更险种金额", entity.getCategoryName()));
                    }

                    if(!StringUtils.equals(entity.getCategoryId(),oriEntity.getCategoryId())){
                        throw new ServiceException(String.format("险种已建立保险单，不能将险种由【%s】变成【%s】", oriEntity.getCategoryName(),entity.getCategoryName()));
                    }
                }
            }else if(op==Operation.DELETE){
                Integer result = baseDao.findUniqueBySql(INSURANCE_EXISTS_SQL, oriEntity.getSaleContractInsuranceId());
                if (null != result && result > 0) {
                    throw new ServiceException(String.format("险种【%s】已建立保险单，不能删除", oriEntity.getCategoryName()));
                }

            }
        }
    }


}
