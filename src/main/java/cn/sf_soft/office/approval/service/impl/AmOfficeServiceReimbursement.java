package cn.sf_soft.office.approval.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.Constant.SysFlags;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.office.approval.dao.OfficeServiceReimbursementDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.OfficeServiceReimbursements;
import cn.sf_soft.office.approval.model.OfficeServiceReimbursementsDetails;
import cn.sf_soft.office.approval.model.ServiceWorkListsCharge;

/**
 * 维修费用报销审批业务实现
 * 
 * @author king
 * @create 2013-1-16下午02:22:06
 */
@Service("officeServiceRiemburseManager")
public class AmOfficeServiceReimbursement extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "35553020";
	
	@Autowired
	@Qualifier("officeServiceReimbursementDao")
	private OfficeServiceReimbursementDao officeServiceReimbursementDao;

	public void setOfficeServiceReimbursementDao(
			OfficeServiceReimbursementDao officeServiceReimbursementDao) {
		this.officeServiceReimbursementDao = officeServiceReimbursementDao;
	}

	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		OfficeServiceReimbursements officeServiceReimbursements = getDocumentDetail(approveDocument
				.getDocumentNo());
		Set<OfficeServiceReimbursementsDetails> details = officeServiceReimbursements
				.getChargeDetail();
		for (OfficeServiceReimbursementsDetails d : details) {
			if (officeServiceReimbursementDao.getServiceWorkLists(d.getTaskNo()).getTaskStatus() > SysFlags.TASK_STATUS_FINSHED_ACCOUNT) {
				// 审批失败:维修工单未结算
				// return
				// MessageResult.APPROVE_ERROR_SERVICE_WORK_NOT_SETTLEMENT;
				throw new ServiceException("审批失败:维修工单未结算");
			}
			ServiceWorkListsCharge serviceWorkListsCharge = officeServiceReimbursementDao
					.getServiceWorkListCharges(d.getSwlcId());
			if (serviceWorkListsCharge == null) {
				// return MessageResult.APPROVE_ERROR_SERVICE_CHARGE_NOT_FOUND;
				throw new ServiceException("审批失败:对应维修工单费用信息找不到");
			}
			if (serviceWorkListsCharge.getSupplierId() != null
					&& serviceWorkListsCharge.getSupplierId().length() > 0) {
				// 费用已指定外委供应商
				// return MessageResult.APPROVE_ERROR_SERVICE_SUPPLIER_NOT_NULL;
				throw new ServiceException("审批失败:费用已指定外委供应商");
			}
			// 预估成本为0也可以审批 2019-04-11
			/*
			if (serviceWorkListsCharge.getChargePf() == 0) {
				// 预估成本为0
				// return MessageResult.APPROVE_ERROR_SERVICE_CHARGE_PF_IS_ZERO;
				throw new ServiceException("审批失败:预估成本为0");
			}*/
			if (BigDecimal.ZERO.compareTo(serviceWorkListsCharge.getChargeCost()) != 0) {
				// 费用已报销
				// return MessageResult.APPROVE_ERROR_SERVICE_CHARGE_REIMBURSED;
				throw new ServiceException("审批失败:费用已报销");
			}
			// 数据检查通过
			if (approveStatus == ApproveStatus.LAST_APPROVE) {
				// 最后一级审批时回填数据
				serviceWorkListsCharge.setChargeCost(new BigDecimal(d.getChargeAmount()));
				officeServiceReimbursementDao
						.updateOfficeServiceReimbursements(officeServiceReimbursements);
			}
		}
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	/**
	 * 得到维修费用报销单的详细信息
	 */
	@Override
	public OfficeServiceReimbursements getDocumentDetail(String documentNo) {

		return officeServiceReimbursementDao.getDocumentDetail(documentNo);
	}

	/**
	 * 最后一级审批时
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument,
			String comment) {

		OfficeServiceReimbursements document = officeServiceReimbursementDao
				.getDocumentDetail(approveDocument.getDocumentNo());

		// 添加单据分录
		FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
		financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
		financeDocumentEntries.setStationId(approveDocument
				.getSubmitStationId());

		financeDocumentEntries
				.setEntryProperty(Constant.DocumentEntries.ENTRIES_PROPERTY_EXPENSE_REIMBURSEMENT);
		financeDocumentEntries
				.setEntryType(Constant.DocumentEntries.ENTRIES_TYPE_NEED_PAY);
		financeDocumentEntries.setDocumentType("费用-维修报销");
		// modify by shichunshan
		financeDocumentEntries.setAmountType(AmountType.NEED_PAY_OTHERS);
		// financeDocumentEntries.setAmountType(AmountType.OTHERS_PAYMENT);

		financeDocumentEntries.setDocumentId(approveDocument.getDocumentNo());
		financeDocumentEntries.setDocumentNo(approveDocument.getDocumentNo());
		financeDocumentEntries
				.setSubDocumentNo(approveDocument.getDocumentNo());
		financeDocumentEntries.setObjectId(document.getUserId());
		financeDocumentEntries.setObjectNo(document.getUserNo());
		financeDocumentEntries.setObjectName(document.getUserName());

		financeDocumentEntries.setLeftAmount(document.getReimburseAmount());
		financeDocumentEntries.setDocumentAmount(document.getReimburseAmount());
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
		// 2013-12-10 by 备注->摘要liujin
		financeDocumentEntries.setSummary(document.getRemark());
		boolean success = financeDocumentEntriesDao
				.insertFinanceDocumentEntries(financeDocumentEntries);
		if (!success) {
			// return MessageResult.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL;
			throw new ServiceException("审批失败:插入单据分录失败");
		}
		if (!createVoucher(approveDocument.getDocumentNo())) {
			throw new ServiceException("审批失败:生成机制凭证出错");
		}
		if (!createOffsetVoucher(approveDocument.getDocumentNo())) {
			throw new ServiceException("审批失败:生成[冲暂估]机制凭证出错");
		}
		return super.onLastApproveLevel(approveDocument, comment);
	}

	private boolean createVoucher(String documentNo) {
		// String sql =
		// dao.getQueryStringByName("serviceReimbursementVoucherDS", new
		// String[]{"documentNo"}, new String[]{"'" + documentNo + "'"});
		// return voucherAuto.generateVoucher(sql, "35553000", false,
		// HttpSessionStore.getSessionUser());
		String sql = dao.getQueryStringByName("serviceReimbursementVoucherDS",
				null, null);
		return voucherAuto.generateVoucherByProc(sql, "35553000", false,
				HttpSessionStore.getSessionUser().getUserId(), documentNo);
	}

	private boolean createOffsetVoucher(String documentNo) {
		// String sql =
		// dao.getQueryStringByName("chargeReimbursementOffsetVoucherDS", new
		// String[]{"documentNo"}, new String[]{"'" + documentNo + "'"});
		// return voucherAuto.generateVoucher(sql, "35552000", false,
		// HttpSessionStore.getSessionUser());
		String sql = dao.getQueryStringByName(
				"serviceReimbursementOffsetVoucherDS", null, null);
		return voucherAuto.generateVoucherByProc(sql, "35553000", false,
				HttpSessionStore.getSessionUser().getUserId(), documentNo);
	}

	/**
	@Override
	public boolean checkDataChanged(String modifyTime,
			ApproveDocuments approveDocument) {
		String documentNo = approveDocument.getDocumentNo();
		OfficeServiceReimbursements officeServiceReimbursements = dao.get(
				OfficeServiceReimbursements.class, documentNo);

		Timestamp lastModifyTime = officeServiceReimbursements.getModifyTime();
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
