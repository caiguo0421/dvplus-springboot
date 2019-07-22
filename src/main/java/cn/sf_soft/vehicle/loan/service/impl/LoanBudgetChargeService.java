package cn.sf_soft.vehicle.loan.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.dao.FinanceDocumentEntriesDao;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleLoanBudget;
import cn.sf_soft.office.approval.model.VehicleLoanBudgetCharge;
import cn.sf_soft.office.approval.model.VehicleLoanBudgetDetails;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.vehicle.contract.model.ChargeCatalog;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/15 14:06
 * @Description:
 */
@Service
public class LoanBudgetChargeService implements Command<VehicleLoanBudgetCharge> {

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoanBudgetChargeService.class);

    private static EntityRelation entityRelation;

    static {
        entityRelation = EntityRelation.newInstance(LoanBudgetChargeService.class);
    }

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private FinanceDocumentEntriesDao financeDocumentEntriesDao;

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleLoanBudgetCharge> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        VehicleLoanBudgetCharge budgetCharge = entityProxy.getEntity();

        //ADM19010056
        if(Tools.toDouble(budgetCharge.getIncome())>0 && budgetCharge.getArDate()==null){
            budgetCharge.setArDate(new Timestamp(System.currentTimeMillis())); //应收日期为空时，默认为系统时间
        }
    }

    private void validateRecord(EntityProxy<VehicleLoanBudgetCharge> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        VehicleLoanBudgetCharge budgetCharge = entityProxy.getEntity();
        if (StringUtils.isEmpty(budgetCharge.getChargeGroupId())) {
            throw new ServiceException("费用明细中chargeGroupId不能为空");
        }

        if (budgetCharge.getMoneyType() == null) {
            throw new ServiceException("费用明细中款项类型不能为空");
        }
        if (budgetCharge.getIncome() == null) {
            budgetCharge.setIncome(0.00D);
        }

        if (budgetCharge.getExpenditure() == null) {
            budgetCharge.setExpenditure(0.00D);
        }

        if (budgetCharge.getIncome() < 0.00D) {
            throw new ServiceException("费用明细中收入不能小于0");
        }

        if (budgetCharge.getExpenditure() < 0.00D) {
            throw new ServiceException("费用明细中支出不能小于0");
        }

        if (budgetCharge.getIncome() == 0.00D && budgetCharge.getExpenditure() == 0.00D) {
            throw new ServiceException("费用明细中收入和支出不能同时为0");
        }

        if (budgetCharge.getLoanAmount() == null) {
            budgetCharge.setLoanAmount(0.00D);
        }

        if (budgetCharge.getLoanAmount() < 0.00D) {
            throw new ServiceException("费用明细中费用贷款不能小于0");
        }

        //20为保证金，40为保险押金
        if (Tools.toByte(budgetCharge.getMoneyType()) == 20 || Tools.toByte(budgetCharge.getMoneyType()) == 40) {
            if (StringUtils.isEmpty(budgetCharge.getObjectId())) {
                throw new ServiceException("款项类型为保证金或续保押金时，应付对象不能为空");
            }

            if (budgetCharge.getPaymentDate() == null) {
                throw new ServiceException("款项类型为保证金或续保押金时，应付日期不能为空");
            }
            if (Tools.toDouble(budgetCharge.getExpenditure()) == 0.00D) {
                throw new ServiceException("款项类型为保证金或续保押金时，支出不能为0");
            }
        }

        if (Tools.toDouble(budgetCharge.getExpenditure()) > 0 && budgetCharge.getPaymentDate() == null) {
            throw new ServiceException("有支出金额时，应付日期不能为空");
        }

        //ADM19010056  有收入金额时，应收日期不能为空
        if(Tools.toDouble(budgetCharge.getIncome())>0 && budgetCharge.getArDate()==null){
            throw new ServiceException("有收入金额时，应收日期不能为空");
        }

        if (Tools.toBigDecimal(budgetCharge.getIncome()).compareTo(Tools.toBigDecimal(budgetCharge.getLoanAmount())) < 0) {
            throw new ServiceException("收入不能小于费用贷款");
        }

        //如果支出为0，清除掉应付日期和应付对象
        if (Tools.toDouble(budgetCharge.getExpenditure()) == 0.00D) {
            budgetCharge.setPaymentDate(null);
            budgetCharge.setObjectId(null);
        }
    }

    @Override
    public void execute(EntityProxy<VehicleLoanBudgetCharge> entityProxy) {
        validateRecord(entityProxy);
        dealBillDocument(entityProxy);
    }

    private void dealBillDocument(EntityProxy<VehicleLoanBudgetCharge> entityProxy) {
        VehicleLoanBudgetCharge budgetCharge = entityProxy.getEntity();
        EntityProxy<VehicleLoanBudgetDetails> budgetDetailsEntityProxy = entityProxy.getMaster();
        EntityProxy<VehicleLoanBudget> budgetEntityProxy = entityProxy.getMaster().getMaster().getMaster();
        VehicleLoanBudget budget = budgetEntityProxy.getEntity();
        VehicleLoanBudgetDetails budgetDetails = budgetDetailsEntityProxy.getEntity();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String sContractNo = "";
        if (Tools.toShort(budget.getLoanMode()) == 20) {
            sContractNo = budget.getDocumentNo();
        } else {
            sContractNo = budget.getDocumentNo() + "," + budget.getSaleContractNo();
        }
        String sVehicleVin = "," + budgetDetails.getVehicleVin();

        short bytAmountType = 20;//应收款
        if (Tools.toShort(budgetCharge.getMoneyType(), (short) 10) == 60)//60为“预收款”类型
        {
            bytAmountType = 10;//预收款类型
        }

        //审批后追加的费用
        if (Tools.toByte(budgetCharge.getStatus()) == 20 && entityProxy.getOperation() == Operation.CREATE) {
            byte moneyType = Tools.toByte(budgetCharge.getMoneyType(), (byte) 10);
            int nEntryProperty = 19;
            double dIncome = Tools.toDouble(budgetCharge.getIncome());
            double dExpenditure = Tools.toDouble(budgetCharge.getExpenditure());
            double dLoanPrice = Tools.toDouble(budgetCharge.getLoanAmount());
            ChargeCatalog chargeCatalog = baseDao.get(ChargeCatalog.class, budgetCharge.getChargeId());
            //应收对象默认=购车客户 ADM19010008
            BaseRelatedObjects arObject = baseDao.get(BaseRelatedObjects.class, budget.getCustomerId());
            //如果有应收对象，默认给应收对象
            if(StringUtils.isNotBlank(budgetCharge.getArObjectId())){
                arObject = baseDao.get(BaseRelatedObjects.class, budgetCharge.getArObjectId());
            }

            if (dIncome - dLoanPrice <= 0.00D && dExpenditure <= 0.00D) {
                return;
            }

            if (dIncome - dLoanPrice > 0) {
                financeDocumentEntriesDao.insertEntryEx(budget.getStationId(), (short) 19, (short) 10, "消贷-" + chargeCatalog.getChargeName(), budgetCharge.getSelfId(), arObject.getObjectId(), arObject.getObjectNo(), arObject.getObjectName(), bytAmountType, dIncome - dLoanPrice, sContractNo + sVehicleVin, sContractNo, budgetCharge.getArDate());
            }

            if (dExpenditure > 0 && StringUtils.isNotEmpty(budgetCharge.getObjectId())) {
                BaseRelatedObjects relatedObjects = baseDao.get(BaseRelatedObjects.class, budgetCharge.getObjectId());
                financeDocumentEntriesDao.insertEntryEx(budget.getStationId(), nEntryProperty, (short) 65, "消贷-" + chargeCatalog.getChargeName(), budgetCharge.getSelfId() + "_1", budgetCharge.getObjectId(), relatedObjects.getObjectNo(), relatedObjects.getObjectName(), (short) 70, dExpenditure, sContractNo + "," + arObject.getObjectName() + sVehicleVin, sContractNo, budgetCharge.getPaymentDate());
            }
        } else if (Tools.toByte(budgetCharge.getStatus()) == 30) { //终止费用
            List<FinanceDocumentEntries> documentEntriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries where documentType LIKE '消贷-%' AND (documentId =? OR documentId=?)", budgetCharge.getSelfId(), budgetCharge.getSelfId() + "_1");
            if (documentEntriesList != null && documentEntriesList.size() > 0) {
                for (FinanceDocumentEntries documentEntries : documentEntriesList) {
                    if (StringUtils.isNotEmpty(documentEntries.getAfterNo())) {
                        throw new ServiceException(String.format("%s已被处理，不能终止", documentEntries.getDocumentType()));
                    }
                    baseDao.delete(documentEntries);
                }
            }
        }

    }


}
