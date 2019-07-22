package cn.sf_soft.vehicle.loan.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.VehicleLoanBudget;
import cn.sf_soft.office.approval.model.VehicleLoanBudgetCharge;
import cn.sf_soft.office.approval.model.VehicleLoanBudgetDetails;
import cn.sf_soft.support.*;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetChargeGroup;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetDetailsGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/15 14:05
 * @Description:
 */
@Service
public class LoanBudgetChargeGroupService implements Command<VwVehicleLoanBudgetChargeGroup> {

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoanBudgetChargeGroupService.class);

    private static EntityRelation entityRelation;

    static {
        entityRelation = EntityRelation.newInstance(LoanBudgetChargeGroupService.class, true);
    }

    @Autowired
    private BaseDao baseDao;

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VwVehicleLoanBudgetChargeGroup> entityProxy) {
        EntityProxy<VwVehicleLoanBudgetDetailsGroup> detailsGroupEntityProxy = entityProxy.getMaster();
        EntityProxy<VehicleLoanBudget> budgetEntityProxy = detailsGroupEntityProxy.getMaster();
        VwVehicleLoanBudgetDetailsGroup detailsGroup = detailsGroupEntityProxy.getEntity();
        VwVehicleLoanBudgetChargeGroup chargeGroup = entityProxy.getEntity();
        chargeGroup.setDocumentNo(detailsGroup.getDocumentNo());

        if(entityProxy.getOperation() == Operation.CREATE){
            chargeGroup.setCreateTime(new Timestamp(System.currentTimeMillis()));//增加crete_time,后续填入到VehicleLoanBudgetCharge中
        }

        handle(entityProxy,budgetEntityProxy.getEntity());
    }

    //通过VwVehicleLoanBudgetChargeGroup生成VehicleLoanBudgetCharge
    private void handle(EntityProxy<VwVehicleLoanBudgetChargeGroup> entityProxy,VehicleLoanBudget budget) {
        SysUsers user = HttpSessionStore.getSessionUser();
        EntityProxy<VwVehicleLoanBudgetDetailsGroup> detailsGroupEntityProxy = entityProxy.getMaster();
        List<EntityProxy<VehicleLoanBudgetDetails>> budgetDetailsProxyList = detailsGroupEntityProxy.getSlaves(VehicleLoanBudgetDetails.class.getSimpleName());


        for (EntityProxy<VehicleLoanBudgetDetails> budgetDetailsProxy : budgetDetailsProxyList) {
            VehicleLoanBudgetDetails budgetDetails = budgetDetailsProxy.getEntity();
            VwVehicleLoanBudgetChargeGroup chargeGroup = entityProxy.getEntity();

            List<VehicleLoanBudgetCharge> budgetChargeList = (List<VehicleLoanBudgetCharge>) baseDao.findByHql("FROM VehicleLoanBudgetCharge where budgetDetailId = ? AND chargeId = ? AND chargeGroupId = ?", budgetDetails.getSelfId(), chargeGroup.getChargeId(),chargeGroup.getChargeGroupId());
            if (entityProxy.getOperation() == Operation.DELETE || budgetDetailsProxy.getOperation() == Operation.DELETE) {
                if (budgetChargeList == null || budgetChargeList.size() == 0) {
                    continue;
                }

                VehicleLoanBudgetCharge charge = budgetChargeList.get(0);
                EntityProxy proxy = EntityProxyUtil.newEntityProxy(Operation.DELETE, charge, LoanBudgetChargeService.class);
                EntityProxyUtil.addSlave(budgetDetailsProxy, proxy); //VehicleLoanBudgetCharge作为VehicleLoanBudgetDetails的slave
            } else if (entityProxy.getOperation() == Operation.CREATE || budgetDetailsProxy.getOperation() == Operation.CREATE) {
                VehicleLoanBudgetCharge charge = new VehicleLoanBudgetCharge();
                charge.setSelfId(UUID.randomUUID().toString());
                charge.setBudgetDetailId(budgetDetails.getSelfId());
                charge.setChargeId(chargeGroup.getChargeId());
                charge.setChargeGroupId(chargeGroup.getChargeGroupId());
                charge.setCreatorId(user.getUserId());
                charge.setCreateTime(chargeGroup.getCreateTime());

                //应收对象ID ADM19010008
                charge.setArObjectId(chargeGroup.getArObjectId());
                //应收日期 ADM19010056
                charge.setArDate(chargeGroup.getArDate());
                charge.setStatus(chargeGroup.getStatus());
                charge.setObjectId(chargeGroup.getObjectId());
                charge.setPaymentDate(chargeGroup.getPaymentDate());
                charge.setMoneyType(chargeGroup.getMoneyType());
                charge.setIncome(Tools.toDouble(chargeGroup.getIncome()));
                charge.setExpenditure(Tools.toDouble(chargeGroup.getExpenditure()));
                charge.setLoanAmount(Tools.toDouble(chargeGroup.getLoanAmount()));
                charge.setRemark(chargeGroup.getRemark());

                EntityProxy proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, charge, LoanBudgetChargeService.class);
                EntityProxyUtil.addSlave(budgetDetailsProxy, proxy); //VehicleLoanBudgetCharge作为VehicleLoanBudgetDetails的slave
            } else {
                EntityProxy proxy = null;
                VehicleLoanBudgetCharge charge = null;
                if (budgetChargeList == null || budgetChargeList.size() == 0) {
                    charge = new VehicleLoanBudgetCharge();
                    charge.setSelfId(UUID.randomUUID().toString());
                    charge.setBudgetDetailId(budgetDetails.getSelfId());
                    charge.setChargeId(chargeGroup.getChargeId());
                    charge.setChargeGroupId(chargeGroup.getChargeGroupId());
                    charge.setCreatorId(user.getUserId());

                    proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, charge, LoanBudgetChargeService.class);
                } else {
                    charge = budgetChargeList.get(0);

                    proxy = EntityProxyUtil.newEntityProxy(Operation.UPDATE, charge, LoanBudgetChargeService.class);
                }

                //应收对象ID ADM19010008
                charge.setArObjectId(chargeGroup.getArObjectId());
                charge.setCreateTime(chargeGroup.getCreateTime());
                charge.setStatus(chargeGroup.getStatus());
                charge.setObjectId(chargeGroup.getObjectId());
                charge.setPaymentDate(chargeGroup.getPaymentDate());
                charge.setMoneyType(chargeGroup.getMoneyType());
                charge.setIncome(Tools.toDouble(chargeGroup.getIncome()));
                charge.setExpenditure(Tools.toDouble(chargeGroup.getExpenditure()));
                charge.setLoanAmount(Tools.toDouble(chargeGroup.getLoanAmount()));
                charge.setRemark(chargeGroup.getRemark());

                EntityProxyUtil.addSlave(budgetDetailsProxy, proxy); //VehicleLoanBudgetCharge作为VehicleLoanBudgetDetails的slave
            }
        }
    }

    @Override
    public void execute(EntityProxy<VwVehicleLoanBudgetChargeGroup> entityProxy) {

    }
}
