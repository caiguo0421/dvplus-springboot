package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;


import org.springframework.stereotype.Service;

import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.Constant.DocumentEntries;
import cn.sf_soft.common.util.Constant.SysFlags;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.AssetsPurchasePlan;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;

/**
 * 资产采购计划业务处理
 * 
 * @author king
 * @create 2013-1-21上午10:44:04
 */
@Service("assetsPurchasePlanManager")
public class AmAssetsPurchasePlan extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "35101020";
	
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AmAssetsPurchasePlan.class);

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
	public AssetsPurchasePlan getDocumentDetail(String documentNo) {
		return dao.get(AssetsPurchasePlan.class, documentNo);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument,
			String comment) {
		AssetsPurchasePlan assetsPurchasePlan = getDocumentDetail(approveDocument
				.getDocumentNo());
		if (assetsPurchasePlan == null) {
			// return ApproveResultCode.APPROVE_ERROR_DOCUMENT_NOT_EXIST;
			return new ApproveResult(
					ApproveResultCode.APPROVE_ERROR_DOCUMENT_NOT_EXIST);
		}
		Short payType = assetsPurchasePlan.getPayType();
		if (payType != null) {
			if (payType == SysFlags.PAY_TYPE_ADVANCE_PAYMENT) {
				// 付款类型是预付款
				double totalPrice = assetsPurchasePlan.getTotPrice();
				if (totalPrice > 0.001) {
					// 总金额大于0时添加单据分录
					if (!insertDocEntry(assetsPurchasePlan, "资产-采购计划", // 资产-采购计划
							AmountType.PURCHASING_ADVANCE_PAYMENT, // 金额类型是采购预付款
							totalPrice)) {
						// return
						// ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL;
						return new ApproveResult(
								ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
					}
				}
			} else if (payType == SysFlags.PAY_TYPE_ACCOUNT_PAYABLE
					&& assetsPurchasePlan.getCtDeposit() > 0) {
				// 付款类型是应付款，且采购订金大于0时添加单据分录
				if (!insertDocEntry(assetsPurchasePlan, "资产-采购订金", // 资产-采购订金
						AmountType.PURCHASING_DEPOSIT, // 金额类型是采购订金
						assetsPurchasePlan.getCtDeposit())) {
					// return
					// ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL;
					return new ApproveResult(
							ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
				}
			}
		}

		return super.onLastApproveLevel(approveDocument, comment);
	}

	/**
	 * 插入一条单据分录
	 * 
	 * @param document
	 * @param documentType
	 * @param amountType
	 * @param amount
	 * @return
	 */
	private boolean insertDocEntry(AssetsPurchasePlan document,
			String documentType, short amountType, double amount) {
		FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
		financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
		logger.debug("entryId--->" + financeDocumentEntries.getEntryId());
		financeDocumentEntries.setStationId(document.getStationId());
		financeDocumentEntries.setEntryProperty(0);
		financeDocumentEntries
				.setEntryType(DocumentEntries.ENTRIES_TYPE_NEED_APPLAY);// 单据类型是应请
		financeDocumentEntries.setDocumentType(documentType);
		financeDocumentEntries.setDocumentId(document.getDocumentNo());
		financeDocumentEntries.setDocumentNo(document.getDocumentNo());
		financeDocumentEntries.setSubDocumentNo(document.getDocumentNo());
		financeDocumentEntries.setObjectId(document.getSupplierId());
		financeDocumentEntries.setObjectNo(document.getSupplierNo());
		financeDocumentEntries.setObjectName(document.getSupplierName());

		financeDocumentEntries.setAmountType(amountType);

		financeDocumentEntries.setLeftAmount(amount);
		financeDocumentEntries.setDocumentAmount(amount);
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
		financeDocumentEntries.setDepartmentName(document.getDepartmentName());
		financeDocumentEntries.setDepartmentNo(document.getDepartmentNo());
		return financeDocumentEntriesDao
				.insertFinanceDocumentEntries(financeDocumentEntries);
	}

	/**
	@Override
	public boolean checkDataChanged(String modifyTime,
			ApproveDocuments approveDocument) {
		String documentNo = approveDocument.getDocumentNo();
		AssetsPurchasePlan assetsPurchasePlan = dao.get(
				AssetsPurchasePlan.class, documentNo);
		// 未修改过
		Timestamp lastModifyTime = assetsPurchasePlan.getModifyTime();
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
