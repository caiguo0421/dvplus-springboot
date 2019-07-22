package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.AccountsArapTimeModify;
import cn.sf_soft.office.approval.model.AccountsArapTimeModifyDetail;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 
 * @Title: 收付日期调整
 * @date 2013-8-20 上午10:46:21
 * @author cw
 */
@Service("accountsArapTimeModify")
public class AmAccountsArapTimeModify extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "40902020";
	
	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ApproveDocuments getDocumentDetail(String documentNo) {
		return dao.get(AccountsArapTimeModify.class, documentNo);
	}

	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument,
			String comment) {
		AccountsArapTimeModify accountsArapTimeModify = (AccountsArapTimeModify) getDocumentDetail(approveDocument
				.getDocumentNo());
		Set<AccountsArapTimeModifyDetail> detail = accountsArapTimeModify
				.getChargeDetail();
		for (AccountsArapTimeModifyDetail a : detail) {
			FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
			FinanceDocumentEntries finance = financeDocumentEntriesDao
					.getDocumentEntriesByDocumentNo(a.getArapNo());
			finance.setDocumentTime(a.getDocumentTimeModify());
			finance.setArapTime(a.getArapTimeModify());
			financeDocumentEntriesDao.updateFinanceDocumentEntries(finance);
		}
		return super.onLastApproveLevel(approveDocument, comment);
	}

	/**
	@Override
	public boolean checkDataChanged(String modifyTime,
			ApproveDocuments approveDocument) {
		String documentNo = approveDocument.getDocumentNo();
		AccountsArapTimeModify accountsArapTimeModify = dao.get(
				AccountsArapTimeModify.class, documentNo);
		Timestamp lastModifyTime = accountsArapTimeModify.getModifyTime();
		// 若未修改过
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
