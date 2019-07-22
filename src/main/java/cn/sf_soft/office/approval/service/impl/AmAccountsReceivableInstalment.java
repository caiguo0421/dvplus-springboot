package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.AccountsReceivableInstalment;
import cn.sf_soft.office.approval.model.AccountsReceivableInstalmentDocument;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleLoanStandingBooks;

/**
 * 
 * @Title: 应收账款分期
 * @date 2013-8-20 上午10:46:02
 * @author cw
 */
@Service("accountsReceivableInstalment")
public class AmAccountsReceivableInstalment extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "40901020";
	
	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	@Override
	public AccountsReceivableInstalment getDocumentDetail(String documentNo) {
		return dao.get(AccountsReceivableInstalment.class, documentNo);
	}

	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		AccountsReceivableInstalment instalment = getDocumentDetail(approveDocument
				.getDocumentNo());
		Set<AccountsReceivableInstalmentDocument> indetalmentDocument = instalment
				.getInstalmentdocument();
		instalment.getChargeDetail();

		for (AccountsReceivableInstalmentDocument a : indetalmentDocument) {
			if (instalment.getInstalmentdocument() == null) {
				throw new ServiceException("审批失败,待分期的单据不存在");
			}
			if (instalment.getArPrincipalDue() > instalment.getArAmountOri()) {
				throw new ServiceException("审批失败,待分期应收款大于原应收单的应收金额");
			}
		}
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument,
			String comment) {
		AccountsReceivableInstalment instalment = getDocumentDetail(approveDocument
				.getDocumentNo());
		Set<VehicleLoanStandingBooks> vehicle = instalment.getChargeDetail();
		int count = 1;
		Set<AccountsReceivableInstalmentDocument> indetalmentDocument = instalment
				.getInstalmentdocument();
		int i = 0;
		double total = 0;
		Iterator<VehicleLoanStandingBooks> iterator = vehicle.iterator();

		while (iterator.hasNext()) {
			i++;
			VehicleLoanStandingBooks v = iterator.next();
			FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
			financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
			financeDocumentEntries.setStationId(approveDocument
					.getSubmitStationId());

			financeDocumentEntries
					.setEntryProperty(Constant.DocumentEntries.ENTRIES_PROPERTY_INSTALMENT);
			financeDocumentEntries
					.setEntryType(Constant.DocumentEntries.ENTRIES_TYPE_NEED_INCOME);
			financeDocumentEntries.setDocumentType("分期-应收账款");
			financeDocumentEntries.setAmountType(AmountType.ACCOUNT_RECEIVABLE);
			financeDocumentEntries.setDocumentId(v.getSlcBookId());
			financeDocumentEntries.setDocumentNo(approveDocument
					.getDocumentNo() + "_" + count + "期");
			count++;
			financeDocumentEntries.setSubDocumentNo(approveDocument
					.getDocumentNo());
			financeDocumentEntries.setObjectId(instalment.getObjectId());
			financeDocumentEntries.setObjectNo(instalment.getObjectNo());
			financeDocumentEntries.setObjectName(instalment.getObjectName());

			financeDocumentEntries.setLeftAmount(v.getArAmount());
			financeDocumentEntries.setDocumentAmount(v.getArAmount());

			if (i == vehicle.size()) {
				financeDocumentEntries.setLeftAmount(instalment.getArAmount()
						- total);
				financeDocumentEntries.setDocumentAmount(instalment
						.getArAmount() - total);
			}

			total += v.getArAmount();

			financeDocumentEntries.setDocumentTime(new Timestamp(new Date()
					.getTime()));
			financeDocumentEntries.setOffsetAmount(0.00);
			financeDocumentEntries.setWriteOffAmount(0.00);
			financeDocumentEntries.setInvoiceAmount(0.00);
			financeDocumentEntries.setPaidAmount(0.00);

			financeDocumentEntries.setUserId(instalment.getApproverId());
			financeDocumentEntries.setUserName(instalment.getApproverName());
			financeDocumentEntries.setUserNo(instalment.getApproverNo());
			financeDocumentEntries
					.setDepartmentId(instalment.getDepartmentId());
			financeDocumentEntries.setDepartmentName(instalment
					.getDepartmentName());
			financeDocumentEntries
					.setDepartmentNo(instalment.getDepartmentNo());
			// 2013-12-10 by 备注->摘要liujin
			financeDocumentEntries.setSummary(instalment.getRemark());
			boolean success = financeDocumentEntriesDao
					.insertFinanceDocumentEntries(financeDocumentEntries);
			if (!success) {
				return new ApproveResult(
						ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
			}
		}

		Set<AccountsReceivableInstalmentDocument> document = instalment
				.getInstalmentdocument();

		for (AccountsReceivableInstalmentDocument a : document) {
			// modify by shichunshan fix 应收账款分期的单据分录应收金额没有修改
			// FinanceDocumentEntries finance = financeDocumentEntriesDao
			// .getDocumentEntriesByDocumentNo(a.getReceivableNo());
			FinanceDocumentEntries finance = financeDocumentEntriesDao
					.getDocumentEntriesByEntryId(a.getDocumentId());
			if (null == finance) {
				throw new ServiceException("审批失败:待分期应收账款单据不存在");
			}
			finance.setAfterNo(finance.getAfterNo() + ","
					+ approveDocument.getDocumentNo());
			finance.setLeftAmount(finance.getLeftAmount() - a.getArAmount());
			finance.setOffsetAmount(finance.getOffsetAmount() + a.getArAmount());
			finance.setOffsetTime(new Timestamp(new Date().getTime()));
			financeDocumentEntriesDao.updateFinanceDocumentEntries(finance);
		}
		// add by shichunshan 增加生成机制凭证
		if (!createVoucher(approveDocument.getDocumentNo())) {
			throw new ServiceException("审批失败:生成凭证模板出错");
		}
		return super.onLastApproveLevel(approveDocument, comment);
	}

	/**
	 * @Description: 生成机制凭证
	 * @param documentNo
	 * @param @return
	 * @throws
	 */
	private boolean createVoucher(String documentNo) {

		// return voucherAuto.generateVoucher(sql, "35551000", false,
		// HttpSessionStore.getSessionUser());
		String sql = dao.getQueryStringByName("accountsReceivableInstalmentDS",
				null, null);
		return voucherAuto.generateVoucherByProc(sql, "40901000", false,
				HttpSessionStore.getSessionUser().getUserId(), documentNo);
	}

	/**
	@Override
	public boolean checkDataChanged(String modifyTime,
			ApproveDocuments approveDocument) {
		String documentNo = approveDocument.getDocumentNo();
		AccountsReceivableInstalment accountsReceivableInstalment = dao.get(
				AccountsReceivableInstalment.class, documentNo);
		Timestamp lastModifyTime = accountsReceivableInstalment.getModifyTime();
		// 未修改过
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
