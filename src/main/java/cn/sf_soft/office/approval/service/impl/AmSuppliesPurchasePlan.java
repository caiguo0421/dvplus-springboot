package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.SuppliesPurchasePlan;

/**
 * 用品采购计划审批模块
 * 
 * @author king
 * @create 2013-6-24下午3:27:06
 */
@Service("suppliesPurchasePlanManager")
public class AmSuppliesPurchasePlan extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "35351020";
	
	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	@Override
	public SuppliesPurchasePlan getDocumentDetail(String documentNo) {
		return dao.get(SuppliesPurchasePlan.class, documentNo);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument,
			String comment) {
		// 不用做任何判断
		/*
		 * SuppliesPurchasePlan document = getDocumentDetail(approveDocument
		 * .getDocumentNo()); //add by shichunshan 新增单据分录 //上级站点采购 boolean
		 * isUnifiedPurchase = document.getIsUnifiedPurchase() == null ? false :
		 * document.getIsUnifiedPurchase(); //本级站点采购 boolean
		 * isUnifiedPurchaseDept = document.getIsUnifiedPurchaseDept() == null ?
		 * false : document.getIsUnifiedPurchaseDept(); if (!isUnifiedPurchase
		 * && !isUnifiedPurchaseDept) { //预付款 if (document.getPayType() == 0) {
		 * double totPrice = document.getTotPrice() == null ? 0 : document
		 * .getTotPrice(); if (totPrice > 0) {
		 */
		// 添加单据分录
		/*
		 * FinanceDocumentEntries financeDocumentEntries=new
		 * FinanceDocumentEntries();
		 * financeDocumentEntries.setAccountId(accountId);
		 * financeDocumentEntries.setAfterNo(afterNo);
		 * financeDocumentEntries.setAmountType(amountType);
		 * financeDocumentEntries.setArapTime(arapTime);
		 * financeDocumentEntries.setDepartmentId(departmentId);
		 * financeDocumentEntries.setDepartmentName(departmentName);
		 * financeDocumentEntries.setDepartmentNo(departmentNo);
		 * financeDocumentEntries.setDocumentAmount(documentAmount);
		 * financeDocumentEntries.setDocumentId(documentId);
		 * financeDocumentEntries.setDocumentNo(documentNo);
		 * financeDocumentEntries.setDocumentTime(documentTime);
		 * financeDocumentEntries.setDocumentType(documentType);
		 * financeDocumentEntries.setEntryId(entryId);
		 * financeDocumentEntries.setEntryProperty(entryProperty);
		 * financeDocumentEntries.setEntryType(entryType);
		 * financeDocumentEntries.setInvoiceAmount(invoiceAmount);
		 * financeDocumentEntries.setInvoiceTime(invoiceTime);
		 * financeDocumentEntries.setLeftAmount(leftAmount);
		 * financeDocumentEntries.setObjectId(objectId);
		 * financeDocumentEntries.setObjectName(objectName);
		 * financeDocumentEntries.setObjectNo(objectNo);
		 * financeDocumentEntries.setOffsetAmount(offsetAmount);
		 */
		// FinanceDocumentEntries financeDocumentEntries=new
		// FinanceDocumentEntries(null, document.getStationId(), 0, 65,
		// "用品-采购计划", document.getDocumentId(), document.getDocumentNo(),
		// document.getDocumentNo(), document.getSupplierId(),
		// document.getSupplierNo(), document.getSupplierName(), accountId,
		// AmountType.PURCHASING_ADVANCE_PAYMENT, leftAmount,
		// document.getTotPrice(), documentTime, arapTime, offsetAmount,
		// offsetTime, paidAmount, paidTime, writeOffAmount, writeOffTime,
		// invoiceAmount, invoiceTime, afterNo, userId, userNo, userName,
		// departmentId, departmentNo, departmentName)

		/*
		 * if (!insertDocEntries( document, 0,
		 * DocumentEntries.ENTRIES_TYPE_NEED_APPLAY,
		 * DocumentEntries.DOCUMENT_TYPE_SUPPLIES_PURCHASE_PLAN,
		 * document.getSupplierId(), document.getSupplierNo(),
		 * document.getSupplierName(), AmountType.PURCHASING_ADVANCE_PAYMENT,
		 * totPrice)) {
		 */
		/*
		 * return new
		 * ApproveResult(ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL
		 * ); } } } }
		 */
		return super.onLastApproveLevel(approveDocument, comment);
	}

	/**
	@Override
	public boolean checkDataChanged(String modifyTime,
			ApproveDocuments approveDocument) {
		String documentNo = approveDocument.getDocumentNo();
		SuppliesPurchasePlan suppliesPurchasePlan = dao.get(
				SuppliesPurchasePlan.class, documentNo);

		Timestamp lastModifyTime = suppliesPurchasePlan.getModifyTime();
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
