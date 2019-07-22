package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.Constant.DocumentEntries;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.finance.funds.model.FinanceSettlementsModes;
import cn.sf_soft.office.approval.dao.FinancePaymentRequestsDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceAccountWriteOffs;
import cn.sf_soft.office.approval.model.FinanceAccountWriteOffsDetails;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;

/**
 * 销账处理
 * 
 * @author king
 */
@Service("financeAccountWriteOffManager")
public class AmFinanceAccountWriteOffs extends BaseApproveProcess {
	// 审批权限Id
	protected String approvalPopedomId = "40233020";
	
	private static final NumberFormat nf = new DecimalFormat("##.##");
	
	@Resource
	private FinancePaymentRequestsDao financePaymentRequestsDao;
	@Resource
	private BaseDao baseDao;
	
	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
		FinanceAccountWriteOffs accountWriteOff = getDocumentDetail(approveDocument.getDocumentNo());
		for (FinanceAccountWriteOffsDetails detail : accountWriteOff.getChargeDetail()) {
			FinanceDocumentEntries documentEntry = financeDocumentEntriesDao.getDocumentEntriesByEntryId(detail
					.getEntryId());

			if (documentEntry == null) {
				throw new ServiceException("审批失败:找不到对应的单据");
			}

			List<Map<String, Object>> list = financePaymentRequestsDao
					.findFinancePaymentRequestsDetailsByEnterId(detail.getEntryId());

			double leftAmount = documentEntry.getLeftAmount();
			double mWriteOffAmount = detail.getWriteOffAmount();

			double leftReal = leftAmount;//可销账金额
			double paidReal = 0; // 已销金额
			if (list != null && list.size() > 0) {
				leftReal = leftReal - (double) list.get(0).get("amount");
				paidReal = (double) list.get(0).get("request_amount");
			}

			// 若为负数的业务单据，则销账金额也为负数
			if (leftReal < 0) {
				mWriteOffAmount = -mWriteOffAmount;
			}

			// if ((mWriteOffAmount * leftReal < -0.001 || mWriteOffAmount *
			// (mWriteOffAmount - leftReal) > 0.001)) {
			// throw new ServiceException("审批失败:销账金额大于可销金额");
			// }

			// 修改销账金额大于可销金额 的判断  20160906 caigx
			if (Math.abs(mWriteOffAmount - leftReal) > 0.001 && mWriteOffAmount - leftReal > 0) {
				// 请款金额大于应请金额
				logger.error(String.format("销账处理 %s,单据 %s：销账金额 %s,可销金额%s,已销金额%s", approveDocument.getDocumentNo(),
						detail.getDocumentNoEntry(), nf.format(mWriteOffAmount), nf.format(leftReal),
						nf.format(paidReal)));
				throw new ServiceException(String.format("审批失败：单据(%s)销账金额%s元大于可销金额%s元", detail.getDocumentNoEntry(),
						nf.format(mWriteOffAmount), nf.format(leftReal)));
			}

			if (ApproveStatus.LAST_APPROVE == approveStatus) {
				// 最后一级审批时，修改单据分录
				double writeOffAmount = documentEntry.getWriteOffAmount() + mWriteOffAmount;
				documentEntry.setWriteOffAmount(writeOffAmount);
				documentEntry.setLeftAmount(documentEntry.getDocumentAmount()
						- (documentEntry.getPaidAmount() + documentEntry.getOffsetAmount() + documentEntry
								.getWriteOffAmount()));
				documentEntry.setWriteOffTime(new Timestamp(new Date().getTime()));
				if (documentEntry.getAfterNo() == null || documentEntry.getAfterNo().length() < 1) {// 添加后续单号
					documentEntry.setAfterNo(detail.getDocumentNo());
				} else {
					documentEntry.setAfterNo(documentEntry.getAfterNo() + "," + detail.getDocumentNo());
				}
				if (!financeDocumentEntriesDao.updateFinanceDocumentEntries(documentEntry)) {
					// 更改业务单据失败
					// return
					// MessageResult.APPROVE_ERROR_DOCUMENT_ENTRY_UPDATE_FAIL;
					throw new ServiceException("审批失败:更改业务单据出错");
				}
			}

		}
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	@Override
	public FinanceAccountWriteOffs getDocumentDetail(String documentNo) {
		return dao.get(FinanceAccountWriteOffs.class, documentNo);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
		// 添加单据分录
		FinanceAccountWriteOffs document = getDocumentDetail(approveDocument.getDocumentNo());
		FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
		financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
		financeDocumentEntries.setStationId(approveDocument.getSubmitStationId());
		short entryType = DocumentEntries.ENTRIES_TYPE_SELL_INCOME;// 销收
		short amountType = DocumentEntries.ENTRIES_TYPE_ACTUALLY_PAID;// 实付
		if (document.getWriteOffType() == DocumentEntries.ENTRIES_TYPE_NEED_PAY) {
			entryType = DocumentEntries.ENTRIES_TYPE_SELL_PAY;// 销付
			amountType = DocumentEntries.ENTRIES_TYPE_ACTUALLY_INCOME;// 实收
		}
		financeDocumentEntries.setEntryProperty(DocumentEntries.ENTRIES_PROPERTY_INCLUDED_IN_CURRENT);// 计入往来
		financeDocumentEntries.setEntryType(entryType);
		financeDocumentEntries.setDocumentType("账款-" + document.getWriteOffTypeMean() + "销账");
		financeDocumentEntries.setAmountType(amountType);
		financeDocumentEntries.setDocumentId(approveDocument.getDocumentNo());
		financeDocumentEntries.setDocumentNo(approveDocument.getDocumentNo());
		financeDocumentEntries.setSubDocumentNo(approveDocument.getDocumentNo());
		financeDocumentEntries.setObjectId(document.getObjectId());
		financeDocumentEntries.setObjectNo(document.getObjectNo());
		financeDocumentEntries.setObjectName(document.getObjectName());
		financeDocumentEntries.setDocumentAmount(document.getWriteOffAmount());
		financeDocumentEntries.setDocumentTime(new Timestamp(new Date().getTime()));
		financeDocumentEntries.setOffsetAmount(document.getWriteOffAmount());
		financeDocumentEntries.setOffsetTime(new Timestamp(new Date().getTime()));
		financeDocumentEntries.setLeftAmount(0.00);
		financeDocumentEntries.setWriteOffAmount(0.00);
		financeDocumentEntries.setInvoiceAmount(0.00);
		financeDocumentEntries.setPaidAmount(0.00);

		financeDocumentEntries.setUserId(document.getUserId());
		financeDocumentEntries.setUserName(document.getUserName());
		financeDocumentEntries.setUserNo(document.getUserNo());
		financeDocumentEntries.setDepartmentId(document.getDepartmentId());
		financeDocumentEntries.setDepartmentName(document.getDepartmentName());
		financeDocumentEntries.setDepartmentNo(document.getDepartmentNo());

		// add by shichunshan 2016/02/15
		creditRepayment(document);
		// 2013-12-10修改，备注->摘要
		financeDocumentEntries.setSummary(document.getRemark());
		boolean success = financeDocumentEntriesDao.insertFinanceDocumentEntries(financeDocumentEntries);
		if (!success) {
			// return MessageResult.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL;
			throw new ServiceException("审批失败:插入单据分录出错");
		}
		if (!createVoucher(approveDocument.getDocumentNo())) {
			throw new ServiceException("审批失败:生成凭证模板出错");
		}
		return super.onLastApproveLevel(approveDocument, comment);
	}

	private boolean createVoucher(String documentNo) {
		// String sql = dao.getQueryStringByName("accountWriteOffVoucherDS", new
		// String[]{"documentNo"}, new String[]{"'" + documentNo + "'"});
		// return voucherAuto.generateVoucher(sql, "40233000", false,
		// HttpSessionStore.getSessionUser());
		String sql = dao.getQueryStringByName("accountWriteOffVoucherDS", null, null);
		return voucherAuto.generateVoucherByProc(sql, "40233000", false, HttpSessionStore.getSessionUser().getUserId(),
				documentNo);
	}

	// add by shichunshan 2016/02/04
	public void creditRepayment(FinanceAccountWriteOffs document) {
		Set<FinanceAccountWriteOffsDetails> details = document.getChargeDetail();
		if (details != null && details.size() > 0) {
			for (FinanceAccountWriteOffsDetails detail : details) {
				String str = String.format("SELECT entry_id , CASE WHEN used_credit * "
						+ "(left_amount - used_credit) > 0 THEN left_amount - used_credit ELSE 0 END AS "
						+ "usable_credit FROM finance_document_entries WHERE entry_id = '%s'", detail.getEntryId());
				List<Map<String, Object>> dvwRepay = dao.getMapBySQL(str, null);
				if (dvwRepay == null || dvwRepay.size() != 1) {
					return;
				}
				double repayAmount = Math.abs(detail.getWriteOffAmount())
						- Math.abs((double) dvwRepay.get(0).get("usable_credit"));
				if (repayAmount < 0.001) {
					continue;
				}
				String modeQueryStr = String.format("SELECT a.*, b.approve_time, c.entry_id "
						+ "FROM finance_settlements_modes a LEFT JOIN finance_settlements b"
						+ " ON a.settlement_no= b.settlement_no" + " LEFT JOIN finance_settlements_details c"
						+ " ON b.settlement_no= c.settlement_no" + " WHERE a.payment_mode IN (45, 50)"
						+ " AND a.settle_amount > a.repay_amount "
						+ " AND b.status = 40 AND b.origin_no IS NULL AND c.entry_id='%s'"
						+ " ORDER BY a.payment_mode DESC, b.approve_time", detail.getEntryId());
				List<Map<String, Object>> drvwMode = dao.getMapBySQL(modeQueryStr, null);
				if (drvwMode != null && drvwMode.size() > 0) {
					for (Map<String, Object> map : drvwMode) {
						double unpaidAmount = (double) map.get("settle_amount") - (double) map.get("repay_amount");
						FinanceSettlementsModes financeSettlementsModes = baseDao.get(FinanceSettlementsModes.class,
								map.get("fsm_id").toString());
						financeSettlementsModes.setRepayAmount(financeSettlementsModes.getRepayAmount()
								+ (repayAmount - unpaidAmount > 0.001 ? unpaidAmount : repayAmount));
						repayAmount -= unpaidAmount;
						if (repayAmount < 0.001) {
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * @Override public boolean checkDataChanged(String modifyTime,
	 *           ApproveDocuments approveDocument) { String documentNo =
	 *           approveDocument.getDocumentNo(); FinanceAccountWriteOffs
	 *           financeAccountWriteOffs = dao.get(
	 *           FinanceAccountWriteOffs.class, documentNo);
	 * 
	 *           Timestamp lastModifyTime =
	 *           financeAccountWriteOffs.getModifyTime(); if (lastModifyTime ==
	 *           null) { return false; } if (null != modifyTime &&
	 *           !"".equals(modifyTime)) {
	 * 
	 *           Timestamp timestamp = Timestamp.valueOf(modifyTime); return
	 *           compareTime(timestamp, lastModifyTime); } return true; }
	 **/

}
