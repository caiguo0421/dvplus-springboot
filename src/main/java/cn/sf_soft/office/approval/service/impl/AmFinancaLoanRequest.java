package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.FinanceLoanRequests;

/**
 * 员工借款审批
 * 
 * @author minggo
 * @created 2013-01-05
 */
@Service("financaLoanRequestManager")
public class AmFinancaLoanRequest extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "40102020";
	
	
	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	/**
	 * 获取员工借款单据详细信息
	 */
	@Override
	public FinanceLoanRequests getDocumentDetail(String documentNo) {
		return dao.get(FinanceLoanRequests.class, documentNo);
	}

	/**
	 * 审批的最后一步在分录表中添加数据
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument,
			String comment) {
		// 检查员工借款数据表和费用分录表中的记录
		FinanceLoanRequests document = getDocumentDetail(approveDocument
				.getDocumentNo());
		if (financeDocumentEntriesDao
				.getDocumentEntriesByDocumentNo(approveDocument.getDocumentNo()) == null
				&& document != null) {
			FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
			financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
			financeDocumentEntries.setStationId(approveDocument
					.getSubmitStationId());
			financeDocumentEntries
					.setEntryProperty(Constant.DocumentEntries.ENTRIES_PROPERTY_SETTLE_ACCOUNTS);
			financeDocumentEntries
					.setEntryType(Constant.DocumentEntries.ENTRIES_TYPE_NEED_PAY);
			financeDocumentEntries.setDocumentType("借款-员工借款");
			financeDocumentEntries.setDocumentId(approveDocument
					.getDocumentNo());
			financeDocumentEntries.setDocumentNo(approveDocument
					.getDocumentNo());
			financeDocumentEntries.setSubDocumentNo(approveDocument
					.getDocumentNo());
			financeDocumentEntries.setObjectId(document.getUserId());
			financeDocumentEntries.setObjectNo(document.getUserNo());
			financeDocumentEntries.setObjectName(document.getUserName());

			// 如果请款类型是“备用金”那么费用类型为“应付备用金”
			// 如果请款类型是“其他”那么费用类型为“其他应付”
			if (document.getRequestType() == Constant.LoanRequestType.LOAN_TYPE_PREPARED_PAYMENT) {
				financeDocumentEntries
						.setAmountType(AmountType.NEED_PAY_PAYMENT);
			} else if (document.getRequestType() == Constant.LoanRequestType.LOAN_TYPE_OTHERS) {
				financeDocumentEntries
						.setAmountType(AmountType.NEED_PAY_OTHERS);
			}
			financeDocumentEntries.setLeftAmount(document.getRequestAmount());
			financeDocumentEntries.setDocumentAmount(document
					.getRequestAmount());
			financeDocumentEntries.setDocumentTime(new Timestamp(new Date()
					.getTime()));
			financeDocumentEntries.setOffsetAmount(0.00);
			financeDocumentEntries.setWriteOffAmount(0.00);
			financeDocumentEntries.setInvoiceAmount(0.00);
			financeDocumentEntries.setPaidAmount(0.00);

			financeDocumentEntries.setUserId(document.getUserId());
			financeDocumentEntries.setUserName(document.getUserName());
			financeDocumentEntries.setUserNo(document.getUserNo());
			financeDocumentEntries.setDepartmentId(document.getDepartmentId());
			financeDocumentEntries.setDepartmentName(document
					.getDepartmentName());
			financeDocumentEntries.setDepartmentNo(document.getDepartmentNo());
			// 2013-12-10修改，备注->摘要
			financeDocumentEntries.setSummary(document.getRemark());
			if (!financeDocumentEntriesDao
					.insertFinanceDocumentEntries(financeDocumentEntries)) {

				return new ApproveResult(
						ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
			}

		}

		return super.onLastApproveLevel(approveDocument, comment);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		// 不需要检查数据
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	/**
	@Override
	public boolean checkDataChanged(String modifyTime,
			ApproveDocuments approveDocument) {
		String documentNo = approveDocument.getDocumentNo();
		FinanceLoanRequests financeLoanRequests = dao.get(
				FinanceLoanRequests.class, documentNo);

		Timestamp lastModifyTime = financeLoanRequests.getModifyTime();
		if (lastModifyTime == null) {
			return false;
		}
		if (null != modifyTime && !"".equals(modifyTime)) {

			Timestamp timestamp = Timestamp.valueOf(modifyTime);
			return compareTime(timestamp, lastModifyTime);
		}
		return true;
	}**/

}
