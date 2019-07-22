package cn.sf_soft.office.approval.service.impl;

import org.springframework.stereotype.Service;

import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceSalaries;

/**
 * @Description:工资审批处理类
 * @author ShiChunshan
 * @date 2015年4月17日 下午3:15:59
 */
@Service("financeSalairesManager")
public class AmFinanceSalaries extends BaseApproveProcess {

	@Override
	public ApproveDocuments getDocumentDetail(String documentNo) {
		return dao.get(FinanceSalaries.class, documentNo);
	}

	@Override
	protected String getApprovalPopedomId() {
		return null;
	}
	
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument,
			String comment) {
		// 添加单据分录
		/*
		 * FinanceSalaries document = (FinanceSalaries)
		 * getDocumentDetail(approveDocument .getDocumentNo());
		 * FinanceDocumentEntries financeDocumentEntries = new
		 * FinanceDocumentEntries();
		 * financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
		 * financeDocumentEntries.setStationId(approveDocument
		 * .getSubmitStationId());
		 * 
		 * financeDocumentEntries.setEntryProperty(0);
		 * financeDocumentEntries.setEntryType
		 * (DocumentEntries.ENTRIES_TYPE_NEED_APPLAY);//应请
		 * financeDocumentEntries.setDocumentType("工资-员工实发");
		 * financeDocumentEntries
		 * .setDocumentId(approveDocument.getDocumentNo());
		 * financeDocumentEntries
		 * .setDocumentNo(approveDocument.getDocumentNo());
		 * financeDocumentEntries
		 * .setSubDocumentNo(approveDocument.getDocumentNo());
		 * financeDocumentEntries.setObjectId(document.getObjectId());
		 * financeDocumentEntries.setObjectNo(document.getObjectNo());
		 * financeDocumentEntries.setObjectName(document.getObjectName());
		 * financeDocumentEntries.setSummary(document.getRemark());
		 * financeDocumentEntries.setUserId(document.getUserId());
		 * financeDocumentEntries.setUserName(document.getUserName());
		 * financeDocumentEntries.setUserNo(document.getUserNo());
		 * financeDocumentEntries.setDepartmentId(document.getDepartmentId());
		 * financeDocumentEntries
		 * .setDepartmentName(document.getDepartmentName());
		 * financeDocumentEntries.setDepartmentNo(document.getDepartmentNo());
		 * financeDocumentEntries.setAccountId(null);
		 * financeDocumentEntries.setAmountType
		 * (AmountType.OTHERS_PAYMENT);//其他支出 //
		 * financeDocumentEntries.setDocumentAmount
		 * (document.getWriteOffAmount());
		 * financeDocumentEntries.setDocumentTime(new Timestamp(new Date()
		 * .getTime()));
		 * financeDocumentEntries.setOffsetAmount(document.getWriteOffAmount());
		 * financeDocumentEntries .setOffsetTime(new Timestamp(new
		 * Date().getTime())); financeDocumentEntries.setLeftAmount(0.00);
		 * financeDocumentEntries.setWriteOffAmount(0.00);
		 * financeDocumentEntries.setInvoiceAmount(0.00);
		 * financeDocumentEntries.setPaidAmount(0.00);
		 * 
		 * 
		 * 
		 * 
		 * // 2013-12-10修改，备注->摘要
		 * financeDocumentEntries.setSummary(document.getRemark()); boolean
		 * success = financeDocumentEntriesDao
		 * .insertFinanceDocumentEntries(financeDocumentEntries); if (!success)
		 * { // return MessageResult.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL;
		 * throw new ServiceException("审批失败:插入单据分录出错"); }
		 */
		return super.onLastApproveLevel(approveDocument, comment);
	}

	
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	

}
