package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceFundsRequests;

/**
 * 资金调入申请
 * 
 * @author king
 * @create 2013-2-18上午10:29:10
 */
@Service("financefundsRequestsInManager")
public class AmFinanceFundsRequestsIn extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "40302020";
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	@Override
	public FinanceFundsRequests getDocumentDetail(String documentNo) {
		return dao.get(FinanceFundsRequests.class, documentNo);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument,
			String comment) {
		return super.onLastApproveLevel(approveDocument, comment);
	}

	/**
	@Override
	public boolean checkDataChanged(String modifyTime,
			ApproveDocuments approveDocument) {
		String documentNo = approveDocument.getDocumentNo();
		FinanceFundsRequests financeFundsRequests = dao.get(
				FinanceFundsRequests.class, documentNo);

		Timestamp lastModifyTime = financeFundsRequests.getModifyTime();
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
