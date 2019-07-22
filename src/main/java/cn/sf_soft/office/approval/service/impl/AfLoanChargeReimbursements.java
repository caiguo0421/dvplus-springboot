package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import cn.sf_soft.common.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.office.approval.dao.LoanChargeReimbursementsDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.OfficeLoanChargeReimbursements;
import cn.sf_soft.office.approval.model.OfficeLoanChargeReimbursementsDetails;
import cn.sf_soft.office.approval.model.VehicleLoanBudgetCharge;

/**
 * 消贷费用报销
 *
 * @author shichunshan
 * @date 2016.3.31
 */
@Service("afLoanChargeReimbursements")
@SuppressWarnings("rawtypes")
public class AfLoanChargeReimbursements extends BaseApproveProcess {
    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "35552220";

    @Autowired
    private LoanChargeReimbursementsDao loanChargeReimbursementsDao;

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @Override
    public OfficeLoanChargeReimbursements getDocumentDetail(String documentNo) {
        return dao.get(OfficeLoanChargeReimbursements.class, documentNo);
    }

    @Override
    public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
        OfficeLoanChargeReimbursements officeLoanChargeReimbursements = getDocumentDetail(approveDocument
                .getDocumentNo());
        Set<OfficeLoanChargeReimbursementsDetails> chargeDetail = officeLoanChargeReimbursements.getChargeDetail();
        if (chargeDetail == null || chargeDetail.size() == 0) {
            throw new ServiceException("审批失败:该费用报销单中没有费用明细");
        }
        for (OfficeLoanChargeReimbursementsDetails detail : chargeDetail) {
            logger.debug(detail.toString());
            List<Map<String, Object>> list = loanChargeReimbursementsDao.getVehicleLoanBudgetChargeById(detail
                    .getBudgetChargeId());
            if (list == null || list.size() == 0) {
                throw new ServiceException("审批失败:找不到消贷费用。");
            }
            Map<String, Object> vehicleLoanBudgetChargeMap = list.get(0);
            if (!String.valueOf(detail.getVehicleVin()).equals(
                    String.valueOf(vehicleLoanBudgetChargeMap.get("vehicle_vin")))
                    || !String.valueOf(detail.getVehicleSalesCode()).equals(
                    String.valueOf(vehicleLoanBudgetChargeMap.get("vehicle_sales_code")))
                    || !String.valueOf(detail.getVehicleName()).equals(
                    String.valueOf(vehicleLoanBudgetChargeMap.get("vehicle_name")))
                    || !String.valueOf(detail.getChargeName()).equals(
                    String.valueOf(vehicleLoanBudgetChargeMap.get("charge_name")))
                    || !String.valueOf(detail.getChargeId()).equals(
                    String.valueOf(vehicleLoanBudgetChargeMap.get("charge_id")))) {
                throw new ServiceException("审批失败:预算单中的费用信息与报销的费用信息不一致。");
            }

            if (vehicleLoanBudgetChargeMap.get("status") != null) {
                Byte status = (Byte) vehicleLoanBudgetChargeMap.get("status");
                if (status != 10 && status != 20) {
                    throw new ServiceException("审批失败:预算单中的费用["
                            + vehicleLoanBudgetChargeMap.get("charge_name").toString() + "]状态不正确。");
                }
            }

            if (vehicleLoanBudgetChargeMap.get("status_lcb") != null) {
                Byte statusLcb = (Byte) vehicleLoanBudgetChargeMap.get("status_lcb");
                if (statusLcb == 0 || statusLcb == 2 || statusLcb == 4) {
                    throw new ServiceException("审批失败:预算单[" + vehicleLoanBudgetChargeMap.get("document_no").toString()
                            + "]的状态不正确。");
                }
            }

            if (vehicleLoanBudgetChargeMap.get("is_reimbursed") != null) {
                boolean isReimbursed = (boolean) vehicleLoanBudgetChargeMap.get("is_reimbursed");
                if (isReimbursed) {
                    throw new ServiceException("审批失败:预算单中的费用已报销。");
                }
            }
        }

        return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        OfficeLoanChargeReimbursements document = getDocumentDetail(approveDocument.getDocumentNo());
        // 正常应付回填成本
        Set<OfficeLoanChargeReimbursementsDetails> chargeDetail = document.getChargeDetail();
        if (chargeDetail != null && chargeDetail.size() > 0) {
            for (OfficeLoanChargeReimbursementsDetails detail : chargeDetail) {
                VehicleLoanBudgetCharge vehicleLoanBudgetCharge = dao.get(VehicleLoanBudgetCharge.class,
                        detail.getBudgetChargeId());
                //10和50都要处理
                if (Tools.toByte(vehicleLoanBudgetCharge.getMoneyType()) == 10 || Tools.toByte(vehicleLoanBudgetCharge.getMoneyType()) == 50) {
                    vehicleLoanBudgetCharge.setCost(detail.getChargeAmount());
                    vehicleLoanBudgetCharge.setIsReimbursed(true);
                }
                dao.update(vehicleLoanBudgetCharge);
            }
        }
        if (financeDocumentEntriesDao.getDocumentEntriesByDocumentNo(approveDocument.getDocumentNo()) == null
                && document != null) {
            FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
            financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
            financeDocumentEntries.setStationId(approveDocument.getSubmitStationId());
            financeDocumentEntries.setEntryProperty(Constant.DocumentEntries.ENTRIES_PROPERTY_EXPENSE_REIMBURSEMENT);
            financeDocumentEntries.setEntryType(Constant.DocumentEntries.ENTRIES_TYPE_NEED_PAY);
            financeDocumentEntries.setDocumentType("费用-消贷费用报销");
            financeDocumentEntries.setDocumentId(approveDocument.getDocumentNo());// 单据分录document_id和document_no，应该都是报销的单号才对。
            financeDocumentEntries.setDocumentNo(approveDocument.getDocumentNo());
            financeDocumentEntries.setSubDocumentNo(approveDocument.getDocumentNo());
            financeDocumentEntries.setObjectId(document.getUserId());
            financeDocumentEntries.setObjectNo(document.getUserNo());
            financeDocumentEntries.setObjectName(document.getUserName());
            financeDocumentEntries.setSummary(document.getRemark());
            financeDocumentEntries.setUserId(document.getUserId());
            financeDocumentEntries.setUserName(document.getUserName());
            financeDocumentEntries.setUserNo(document.getUserNo());
            financeDocumentEntries.setDepartmentId(document.getDepartmentId());
            financeDocumentEntries.setDepartmentName(document.getDepartmentName());
            financeDocumentEntries.setDepartmentNo(document.getDepartmentNo());
            financeDocumentEntries.setAmountType(AmountType.NEED_PAY_OTHERS);
            double mReimburseAmount = document.getReimburseAmount();

            financeDocumentEntries.setLeftAmount(mReimburseAmount);
            financeDocumentEntries.setDocumentAmount(mReimburseAmount);

            financeDocumentEntries.setDocumentTime(new Timestamp(new Date().getTime()));
            financeDocumentEntries.setOffsetAmount(0.00);
            financeDocumentEntries.setWriteOffAmount(0.00);
            financeDocumentEntries.setInvoiceAmount(0.00);
            financeDocumentEntries.setPaidAmount(0.00);
            if (!financeDocumentEntriesDao.insertFinanceDocumentEntries(financeDocumentEntries)) {
                return new ApproveResult(ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
            }

        }
        if (!createVoucher(approveDocument.getDocumentNo())) {
            throw new ServiceException("审批失败:生成凭证模板出错");
        }
        return super.onLastApproveLevel(approveDocument, comment);
    }

    private boolean createVoucher(String documentNo) {
        String sql = dao.getQueryStringByName("afLoanChargeReimbursementsDS", null, null);
        return voucherAuto.generateVoucherByProc(sql, "355522", false, HttpSessionStore.getSessionUser().getUserId(),
                documentNo);
    }


    /**
     * @Override public boolean checkDataChanged(String modifyTime,
     *           ApproveDocuments approveDocument) { String documentNo =
     *           approveDocument.getDocumentNo(); OfficeLoanChargeReimbursements
     *           officeLoanChargeReimbursements = this
     *           .getDocumentDetail(documentNo); Timestamp lastModifyTime =
     *           officeLoanChargeReimbursements.getModifyTime(); if
     *           (lastModifyTime == null) { return false; } if (null !=
     *           modifyTime && !"".equals(modifyTime)) { Timestamp timestamp =
     *           Timestamp.valueOf(modifyTime); return compareTime(timestamp,
     *           lastModifyTime); } return true; }
     **/

}
