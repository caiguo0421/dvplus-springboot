package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.FinanceDocumentEntriesDao;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.office.approval.dao.ExpenseReimbursementsDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.ExpenseReimbursements;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;

/**
 * 费用报销审批实现类
 *
 * @author minggo
 * @modify king
 * @created 2013-01-06
 */
@Service("expenseReimbursementsManager")
public class AmExpenseReimbursements extends BaseApproveProcess {
    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "35551020";

    @Autowired
    @Qualifier("expenseReimbursementsDao")
    private ExpenseReimbursementsDao expenseReimbursementsDao;
    @Autowired
    @Qualifier("financeDocumentEntriesDao")
    private FinanceDocumentEntriesDao financeDocumentEntriesDao;

    public void setExpenseReimbursementsDao(ExpenseReimbursementsDao expenseReimbursementsDao) {
        this.expenseReimbursementsDao = expenseReimbursementsDao;
    }

    @Override
    public void setFinanceDocumentEntriesDao(FinanceDocumentEntriesDao financeDocumentEntriesDao) {
        this.financeDocumentEntriesDao = financeDocumentEntriesDao;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
        return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @Override
    public ExpenseReimbursements getDocumentDetail(String documentNo) {
        return expenseReimbursementsDao.getDocumentDetail(documentNo, true);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        ExpenseReimbursements document = dao.load(ExpenseReimbursements.class, approveDocument.getDocumentNo());
        if (document != null && financeDocumentEntriesDao.getDocumentEntriesByDocumentNo(approveDocument.getDocumentNo()) == null) {
            FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
            financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
            financeDocumentEntries.setStationId(approveDocument.getSubmitStationId());

            financeDocumentEntries.setEntryProperty(Constant.DocumentEntries.ENTRIES_PROPERTY_EXPENSE_REIMBURSEMENT);
            financeDocumentEntries.setEntryType(Constant.DocumentEntries.ENTRIES_TYPE_NEED_PAY);
            financeDocumentEntries.setDocumentType("费用-一般报销");
            // modify by shichunshan
            financeDocumentEntries.setAmountType(AmountType.NEED_PAY_OTHERS);
            // financeDocumentEntries.setAmountType(AmountType.OTHERS_PAYMENT);

            financeDocumentEntries.setDocumentId(approveDocument.getDocumentNo());
            financeDocumentEntries.setDocumentNo(approveDocument.getDocumentNo());
            financeDocumentEntries.setSubDocumentNo(approveDocument.getDocumentNo());
            financeDocumentEntries.setObjectId(document.getUserId());
            financeDocumentEntries.setObjectNo(document.getUserNo());
            financeDocumentEntries.setObjectName(document.getUserName());

            financeDocumentEntries.setLeftAmount(document.getReimburseAmount());
            financeDocumentEntries.setDocumentAmount(document.getReimburseAmount());
            financeDocumentEntries.setDocumentTime(new Timestamp(new Date().getTime()));
            financeDocumentEntries.setOffsetAmount(0.00);
            financeDocumentEntries.setWriteOffAmount(0.00);
            financeDocumentEntries.setInvoiceAmount(0.00);
            financeDocumentEntries.setPaidAmount(0.00);

            financeDocumentEntries.setUserId(document.getUserId());
            financeDocumentEntries.setUserName(document.getUserName());
            financeDocumentEntries.setUserNo(document.getUserNo());
            financeDocumentEntries.setDepartmentId(document.getDepartmentId());
            financeDocumentEntries.setDepartmentName(document.getDepartmentName());
            financeDocumentEntries.setDepartmentNo(document.getDepartmentNo());
            // 2013-12-10 备注->摘要 by liujin
            financeDocumentEntries.setSummary(document.getRemark());
            if (!financeDocumentEntriesDao.insertFinanceDocumentEntries(financeDocumentEntries)) {
                throw new ServiceException("审批失败:插入单据分录出错");
            }

//            if (!createVoucher(approveDocument.getDocumentNo())) {
//                throw new ServiceException("审批失败:生成凭证模板出错");
//            }
        }
        return super.onLastApproveLevel(approveDocument, comment);
    }


//    private boolean createVoucher(String documentNo) {
//        String sql = dao.getQueryStringByName("expenseReimbursementVoucherDS", null, null);
//        return voucherAuto.generateVoucherByProc(sql, "35551000", false, HttpSessionStore.getSessionUser().getUserId(), documentNo);
//    }

    /**
     @Override public boolean checkDataChanged(String modifyTime,
     ApproveDocuments approveDocument) {
     String documentNo = approveDocument.getDocumentNo();
     ExpenseReimbursements expenseReimbursements = expenseReimbursementsDao
     .getDocumentDetail(documentNo, true);
     Timestamp lastModifyTime = expenseReimbursements.getModifyTime();
     if (lastModifyTime == null) {
     return false;
     }
     if (null != modifyTime && !"".equals(modifyTime)) {
     // Nov 30, 2015 2:52:40 PM
     Timestamp timestamp = Timestamp.valueOf(modifyTime);
     // Timestamp timestamp = new Timestamp(Timestamp.parse(modifyTime));
     return compareTime(timestamp, lastModifyTime);
     }
     return true;
     }**/

}
