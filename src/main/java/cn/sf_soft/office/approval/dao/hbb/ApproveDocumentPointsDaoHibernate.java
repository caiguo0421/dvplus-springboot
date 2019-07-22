package cn.sf_soft.office.approval.dao.hbb;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.ApproveDocumentPointsDao;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.ApproveDocumentsPoints;

/**
 * it's directly to operating data of approval history
 * @author minggo
 * @created 2012-12-13
 */
@Repository("approveDocumentPointDao")
public class ApproveDocumentPointsDaoHibernate extends BaseDaoHibernateImpl implements ApproveDocumentPointsDao {
	
	/**
	 * insert approval history
	 */
	public boolean insertApproveDocumentPoint(final ApproveDocumentsPoints approveDocumentsPoints) {
		try {
			getHibernateTemplate().save(approveDocumentsPoints);
		} catch (DataAccessException e) {
//			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * get the approve history of document
	 */
	@SuppressWarnings("unchecked")
	public List<ApproveDocumentsPoints> getDocumentHistory(String documentId) {
		if(documentId != null && documentId.length() > 1)
			return (List<ApproveDocumentsPoints>) getHibernateTemplate().find("from ApproveDocumentsPoints a where a.documentId=? ORDER BY ISNULL(approveTime, GETDATE()),approveLevel",documentId);
		return null;
	}

	//获取业务单据相应的‘审批中’状态的审批单据审批点
	@SuppressWarnings("unchecked")
	@Override
	public List<ApproveDocumentsPoints> getApproveDocumentsPoints(String documentId) {
		String hql = "from  ApproveDocumentsPoints where status = 30 and  documentId = ? ORDER BY approveLevel";
		return (List<ApproveDocumentsPoints>) getHibernateTemplate().find(hql,documentId);
	}

}
