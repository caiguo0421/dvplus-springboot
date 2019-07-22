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
import cn.sf_soft.office.approval.model.OfficeInternalAudits;

/**
 * 内部审计业务
 * 
 * @author king
 * @create 2013-1-17下午05:21:46
 */
@Service("officeInternalAuditsManager")
public class AmOfficeInternalAudits extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "35651020";
	
	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	@Override
	public OfficeInternalAudits getDocumentDetail(String documentNo) {
		return dao.get(OfficeInternalAudits.class, documentNo);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument,
			String comment) {
		OfficeInternalAudits document = getDocumentDetail(approveDocument
				.getDocumentNo());
		double bearAmount = document.getBearAmount();
		if (bearAmount > 0) {
			FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
			financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
			financeDocumentEntries.setStationId(approveDocument
					.getSubmitStationId());

			financeDocumentEntries.setEntryProperty(19);
			financeDocumentEntries
					.setEntryType(Constant.DocumentEntries.ENTRIES_TYPE_NEED_SILVER);
			financeDocumentEntries.setDocumentType("审计-内部审计");
			financeDocumentEntries.setAmountType(AmountType.ACCOUNT_RECEIVABLE);

			financeDocumentEntries.setDocumentId(approveDocument
					.getDocumentNo());
			financeDocumentEntries.setDocumentNo(approveDocument
					.getDocumentNo());
			financeDocumentEntries.setSubDocumentNo(approveDocument
					.getDocumentNo());
			financeDocumentEntries.setObjectId(document.getBearerId());
			financeDocumentEntries.setObjectNo(document.getBearerNo());
			financeDocumentEntries.setObjectName(document.getBearerName());

			financeDocumentEntries.setLeftAmount(document.getBearAmount());
			financeDocumentEntries.setDocumentAmount(document.getBearAmount());
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
			// 2013-12-10 by 备注->摘要liujin
			financeDocumentEntries.setSummary(document.getRemark());
			if (!financeDocumentEntriesDao
					.insertFinanceDocumentEntries(financeDocumentEntries)) {
				// return
				// ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL;
				return new ApproveResult(
						ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
			}
		}

		return super.onLastApproveLevel(approveDocument, comment);
	}

	/**
	@Override
	public boolean checkDataChanged(String modifyTime,
			ApproveDocuments approveDocument) {
		String documentNo = approveDocument.getDocumentNo();
		OfficeInternalAudits officeInternalAudits = dao.get(
				OfficeInternalAudits.class, documentNo);

		Timestamp lastModifyTime = officeInternalAudits.getModifyTime();
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
