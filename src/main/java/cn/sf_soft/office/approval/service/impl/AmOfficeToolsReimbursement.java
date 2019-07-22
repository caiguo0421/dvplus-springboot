package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.Constant.DocumentEntries;
import cn.sf_soft.common.util.Constant.DocumentStatus;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.OfficeToolsReimbursements;
import cn.sf_soft.office.approval.model.OfficeToolsReimbursementsDetails;
import cn.sf_soft.office.approval.model.ToolsRepair;

/**
 * 工具费用报销审批模块
 * 
 * @author king
 * @create 2013-6-25下午5:01:16
 */
@Service("officeToolsReimbursement")
public class AmOfficeToolsReimbursement extends BaseApproveProcess {
	@Override
	public OfficeToolsReimbursements getDocumentDetail(String documentNo) {
		return dao.get(OfficeToolsReimbursements.class, documentNo);
	}

	@Override
	protected String getApprovalPopedomId() {
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		OfficeToolsReimbursements document = getDocumentDetail(approveDocument
				.getDocumentNo());
		Set<OfficeToolsReimbursementsDetails> details = document
				.getChargeDetail();
		Set<String> objNos = new HashSet<String>();
		for (OfficeToolsReimbursementsDetails detail : details) {
			objNos.add(detail.getObjNo());
		}
		DetachedCriteria dc = DetachedCriteria.forClass(ToolsRepair.class);
		dc.add(Restrictions.in("documentNo", objNos));
		dc.add(Restrictions.eq("status", DocumentStatus.CONFIREMED));
		// 从数据库中查询出单据所对应的资产维修信息
		List<ToolsRepair> repairs = dao.findByCriteria(dc);
		boolean flag = false;
		for (OfficeToolsReimbursementsDetails detail : details) {
			for (ToolsRepair repair : repairs) {
				if (repair.getDocumentNo().equals(detail.getObjNo())) {
					double reimburseAmount = repair.getReimburseAmount() == null ? 0
							: repair.getReimburseAmount();
					if (reimburseAmount > 0) {
						// 费用已报销
						throw new ServiceException("审批失败,费用已报销");
					}
					if (approveStatus == ApproveStatus.LAST_APPROVE) {
						repair.setReimburseAmount(detail.getChargeAmount());
						dao.update(repair);
					}
					flag = true;
					break;
				}
			}
		}
		if (!flag) {
			// 找不到资产维修单或资产维修单已作废
			throw new ServiceException("审批失败,找不到工具维修单或工具维修单已作废");
		}
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument,
			String comment) {
		OfficeToolsReimbursements document = getDocumentDetail(approveDocument
				.getDocumentNo());

		FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
		financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
		financeDocumentEntries.setStationId(document.getStationId());
		financeDocumentEntries
				.setEntryProperty(DocumentEntries.ENTRIES_PROPERTY_EXPENSE_REIMBURSEMENT);
		financeDocumentEntries
				.setEntryType(DocumentEntries.ENTRIES_TYPE_NEED_PAY);
		financeDocumentEntries.setDocumentType("费用-工具报销");
		financeDocumentEntries.setDocumentId(document.getDocumentNo());
		financeDocumentEntries.setDocumentNo(document.getDocumentNo());
		financeDocumentEntries.setSubDocumentNo(document.getDocumentNo());
		financeDocumentEntries.setObjectId(document.getUserId());
		financeDocumentEntries.setObjectNo(document.getUserNo());
		financeDocumentEntries.setObjectName(document.getUserName());

		financeDocumentEntries.setAmountType(AmountType.OTHERS_PAYMENT);

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
		if (!financeDocumentEntriesDao
				.insertFinanceDocumentEntries(financeDocumentEntries)) {
			throw new ServiceException("审批失败:插入单据分录出错");
		}
		// 工具金额比较小，不用生成凭证
		/*
		 * if(!createVoucher(approveDocument.getDocumentNo())){ throw new
		 * ServiceException("审批失败:生成凭证模板出错"); }
		 */
		return super.onLastApproveLevel(approveDocument, comment);
	}

	private boolean createVoucher(String documentNo) {
		String sql = dao.getQueryStringByName("assetsReimbursementVoucherDS",
				new String[] { "documentNo" }, new String[] { "'" + documentNo
						+ "'" });
		return voucherAuto.generateVoucher(sql, "35554000", false,
				HttpSessionStore.getSessionUser());
	}

	/**
	@Override
	public boolean checkDataChanged(String modifyTime,
			ApproveDocuments approveDocument) {
		String documentNo = approveDocument.getDocumentNo();
		OfficeToolsReimbursements officeAssetsReimbursements = dao.get(
				OfficeToolsReimbursements.class, documentNo);
		Timestamp lastModifyTime = officeAssetsReimbursements.getModifyTime();
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
