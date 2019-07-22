package cn.sf_soft.vehicle.loan.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.VehicleLoanBudget;
import cn.sf_soft.office.approval.model.VehicleLoanBudgetDetails;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.support.*;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractDetailGroups;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetDetailsGroup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/15 11:17
 * @Description:
 */
@Service
public class LoanBudgetDetailsGroupService implements Command<VwVehicleLoanBudgetDetailsGroup> {

    private static EntityRelation entityRelation;

    static {
        entityRelation = EntityRelation.newInstance(LoanBudgetDetailsGroupService.class, true);
        entityRelation.addSlave("groupId", LoanBudgetChargeGroupService.class);
    }

    @Autowired
    private BaseDao baseDao;

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VwVehicleLoanBudgetDetailsGroup> entityProxy) {
        EntityProxy<VehicleLoanBudget> budgetEntityProxy = entityProxy.getMaster();
        VehicleLoanBudget budget = budgetEntityProxy.getEntity();
        VwVehicleLoanBudgetDetailsGroup budgetDetailsGroup = entityProxy.getEntity();
        budgetDetailsGroup.setDocumentNo(budget.getDocumentNo());

        handle(entityProxy);
    }

    //通过VwVehicleLoanBudgetDetailsGroup生成VehicleLoanBudgetDetails
    private void handle(EntityProxy<VwVehicleLoanBudgetDetailsGroup> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            throw new ServiceException("VwVehicleLoanBudgetDetailsGroup不支持DELETE操作");
        }

        EntityProxy<VehicleLoanBudget> budgetEntityProxy = entityProxy.getMaster();
        VehicleLoanBudget budget = budgetEntityProxy.getEntity();

        VwVehicleLoanBudgetDetailsGroup budgetDetailsGroup = entityProxy.getEntity();
        if (StringUtils.isEmpty(budgetDetailsGroup.getGroupId())) {
            throw new ServiceException("VwVehicleLoanBudgetDetailsGroup中group_id不能为空");
        }
        VehicleSaleContractDetailGroups detailGroups = baseDao.get(VehicleSaleContractDetailGroups.class, budgetDetailsGroup.getGroupId());
        if (detailGroups == null) {
            throw new ServiceException(String.format("groupId：%s，在VehicleSaleContractDetailGroups未查到任何记录", budgetDetailsGroup.getGroupId()));
        }

        List<VehicleSaleContractDetail> contractDetailList = (List<VehicleSaleContractDetail>) baseDao.findByHql("FROM VehicleSaleContractDetail Where groupId = ? ", budgetDetailsGroup.getGroupId());
        if (contractDetailList == null || contractDetailList.size() == 0) {
            throw new ServiceException(String.format("groupId：%s，没有车辆明细", budgetDetailsGroup.getGroupId()));
        }

        //同步挂靠公司 ADM19010015 20190123
        this.fillAffiliatedCompany(budgetDetailsGroup,detailGroups);

        for (VehicleSaleContractDetail contractDetail : contractDetailList) {
            List<VehicleLoanBudgetDetails> budgetDetailsList = (List<VehicleLoanBudgetDetails>) baseDao.findByHql("FROM VehicleLoanBudgetDetails where documentNo = ? and saleContractDetailId = ?", budget.getDocumentNo(), contractDetail.getContractDetailId());
            VehicleLoanBudgetDetails budgetDetails = null;
            EntityProxy proxy = null;
            if (budgetDetailsList == null || budgetDetailsList.size() == 0) {
                budgetDetails = new VehicleLoanBudgetDetails();
                budgetDetails.setSelfId(UUID.randomUUID().toString());
                budgetDetails.setDocumentNo(budget.getDocumentNo());
                budgetDetails.setStatus((short) 0);
                budgetDetails.setBulletinId(contractDetail.getVnoIdNew());
                budgetDetails.setBulletinNo(contractDetail.getVehicleVnoNew());
                budgetDetails.setRateType((short) 10);

                proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, budgetDetails, LoanBudgetDetailsService.class);
            } else {
                budgetDetails = budgetDetailsList.get(0);
                proxy = EntityProxyUtil.newEntityProxy(Operation.UPDATE, budgetDetails, LoanBudgetDetailsService.class);
            }

            budgetDetails.setSaleContractDetailId(contractDetail.getContractDetailId());
            budgetDetails.setVnoId(contractDetail.getVnoId());
            budgetDetails.setVehicleVin(contractDetail.getVehicleVin());
            budgetDetails.setVehiclePriceTotal(Tools.toDouble(budgetDetailsGroup.getVehiclePriceTotal()));
            budgetDetails.setLoanAmount(Tools.toDouble(budgetDetailsGroup.getLoanAmount()));
            budgetDetails.setRemark(budgetDetailsGroup.getRemark());
            budgetDetails.setAgentAmount(Tools.toDouble(budgetDetailsGroup.getAgentAmount()));
            budgetDetails.setInterestRate(budgetDetailsGroup.getInterestRate() == null ? null : budgetDetailsGroup.getInterestRate().doubleValue());
            budgetDetails.setPeriodNumber(budgetDetailsGroup.getPeriodNumber());
            budgetDetails.setMonthPay(Tools.toDouble(budgetDetailsGroup.getMonthPay()));
            //挂靠公司可以修改 ADM19010015 20190123
            budgetDetails.setAffiliatedCompanyId(budgetDetailsGroup.getAffiliatedCompanyId());

            budgetDetails.setInterest(Tools.toDouble(budgetDetailsGroup.getInterest()));

            EntityProxyUtil.addSlave(entityProxy, proxy);
        }
    }

    //同步挂靠公司 ADM19010015 20190123
    private void fillAffiliatedCompany(VwVehicleLoanBudgetDetailsGroup budgetDetailsGroup, VehicleSaleContractDetailGroups detailGroups) {
        if(budgetDetailsGroup==null || detailGroups==null) {
            return;
        }
        //挂靠公司没变，跳过
        if(StringUtils.equals(budgetDetailsGroup.getAffiliatedCompanyId(), detailGroups.getBelongToSupplierId())){
            return;
        }
        SysUsers user = HttpSessionStore.getSessionUser();
        BaseRelatedObjects baseRelatedObjects = baseDao.get(BaseRelatedObjects.class, budgetDetailsGroup.getAffiliatedCompanyId());
//        if(baseRelatedObjects == null){
//            throw new ServiceException(String.format("同步挂靠单位出错，通过挂靠公司id：%s未找到往来对象", budgetDetailsGroup.getAffiliatedCompanyId()));
//        }
        if(baseRelatedObjects!=null) {
            detailGroups.setBelongToSupplierId(baseRelatedObjects.getObjectId());
            detailGroups.setBelongToSupplierNo(baseRelatedObjects.getObjectNo());
            detailGroups.setBelongToSupplierName(baseRelatedObjects.getObjectName());

            List<VehicleSaleContractDetail> contractDetailList = (List<VehicleSaleContractDetail>) baseDao.findByHql("FROM VehicleSaleContractDetail Where groupId = ? ", budgetDetailsGroup.getGroupId());
            if (contractDetailList == null || contractDetailList.size() == 0) {
                throw new ServiceException(String.format("groupId：%s，没有车辆明细", budgetDetailsGroup.getGroupId()));
            }

            for (VehicleSaleContractDetail contractDetail : contractDetailList) {
                contractDetail.setBelongToSupplierId(baseRelatedObjects.getObjectId());
                contractDetail.setBelongToSupplierNo(baseRelatedObjects.getObjectNo());
                contractDetail.setBelongToSupplierName(baseRelatedObjects.getObjectName());
            }
            // 更新合同的ModifyTime，防止并发性问题
            VehicleSaleContracts contracts = baseDao.get(VehicleSaleContracts.class, detailGroups.getContractNo());
            if(contracts!=null){
                contracts.setModifier(user.getUserFullName());
                contracts.setModifyTime(new Timestamp(System.currentTimeMillis()));
            }
        }

    }

    @Override
    public void execute(EntityProxy<VwVehicleLoanBudgetDetailsGroup> entityProxy) {

    }
}
