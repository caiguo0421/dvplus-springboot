package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.office.approval.dao.FinanceFundsRequestsDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceFundsRequests;

/**
 * 资金调出申请
 * 
 * @author king
 * @create 2013-2-18下午02:11:58
 */
@Service("financefundsRequestsOutManager")
public class AmFinanceFundsRequestOut extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "40302520";
	
	@Autowired
	@Qualifier("financeFundsRequestsDao")
	private FinanceFundsRequestsDao financeFundsRequestsDao;
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AmFinanceFundsRequestOut.class);

	public void setFinanceFundsRequestsDao(
			FinanceFundsRequestsDao financeFundsRequestsDao) {
		this.financeFundsRequestsDao = financeFundsRequestsDao;
	}

	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		FinanceFundsRequests financeFundsRequests = getDocumentDetail(approveDocument
				.getDocumentNo());
		// 得到资金调出申请单对应的资金调入申请单
		FinanceFundsRequests requestIn = financeFundsRequestsDao
				.getAgreedRequestIn(financeFundsRequests.getRequestNo());
		if (requestIn == null) {
			// 资金调入申请已作废
			return ApproveResultCode.APPROVE_ERROR_REQUEST_IN_DOCUMENT_INVALID;
		}
		if (requestIn.getAllocateStatus() != 0) {
			// 资金调入申请单已做调出申请
			return ApproveResultCode.APPROVE_ERROR_REQUEST_IN_HAS_REQUEST_OUT;
		}
		//fix 将最后一级审批后的数据回填代码由checkData转入onLastApproveLevel方法中，caigx -20160419
		/**if (approveStatus == ApproveStatus.LAST_APPROVE) {
			// 修改对应的资金调入申请表的allocate_status状态
			requestIn.setAllocateStatus((short) 1);
			financeFundsRequestsDao
					.updateFinanceFundsRequests(financeFundsRequests);
			financeFundsRequestsDao.updateFinanceFundsRequests(requestIn);

		}**/
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	@Override
	public FinanceFundsRequests getDocumentDetail(String documentNo) {
		return dao.get(FinanceFundsRequests.class, documentNo);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument,
			String comment) {
		
		//fix 将最后一级审批后的数据回填代码由checkData转入onLastApproveLevel方法中，caigx -20160419
		FinanceFundsRequests financeFundsRequests = getDocumentDetail(approveDocument.getDocumentNo());
		// 得到资金调出申请单对应的资金调入申请单
		FinanceFundsRequests requestIn = financeFundsRequestsDao.getAgreedRequestIn(financeFundsRequests.getRequestNo());
		// 修改对应的资金调入申请表的allocate_status状态
		requestIn.setAllocateStatus((short) 1);
		financeFundsRequestsDao.updateFinanceFundsRequests(financeFundsRequests);
		financeFundsRequestsDao.updateFinanceFundsRequests(requestIn);
		
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
