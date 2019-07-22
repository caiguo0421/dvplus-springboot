package cn.sf_soft.office.approval.dao;

import java.util.List;

import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.ApproveDocumentsPoints;

/**
 * interface is for event of approval history
 * @author minggo
 * @created 2012-12-13
 */
public interface ApproveDocumentPointsDao {
	/**
	 * insert a history of approval
	 * @param approveDocumentsPoints
	 * @return
	 */
	public boolean insertApproveDocumentPoint(ApproveDocumentsPoints approveDocumentsPoints);
	
	/**
	 * get the documents's approve history
	 * @param documentId
	 * @return
	 */
	public List<ApproveDocumentsPoints> getDocumentHistory(String documentId);
	
	
	/**
	 * 获取业务单据相应的‘审批中’状态的审批单据审批点。
	 * @param approveDocument
	 * @return
	 * @author caigx
	 * @date 2016-05-24
	 */
	public List<ApproveDocumentsPoints> getApproveDocumentsPoints(String documentId);
}
