package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.ToolsPurchasePlan;
import org.springframework.stereotype.Service;

/**
 * 
 * @Title: 工具采购计划审批模块
 * @date 2013-9-29 上午10:58:01
 * @author cw
 */
@Service("toolsPurchasePlan")
public class AmToolsPurchasePlan extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "35491020";
	
	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	@Override
	public ToolsPurchasePlan getDocumentDetail(String documentNo) {
		return dao.get(ToolsPurchasePlan.class, documentNo);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

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
		ToolsPurchasePlan toolsPurchasePlan = dao.get(ToolsPurchasePlan.class,
				documentNo);

		Timestamp lastModifyTime = toolsPurchasePlan.getModifyTime();
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
