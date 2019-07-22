package cn.sf_soft.office.approval.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.sf_soft.oa.notification.model.VwApproveDocumentsNotification;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;

/**
 * 审批单据DAO
 * 
 * @author king
 * @create 2012-12-8下午04:02:22
 */
public interface ApproveDocumentDao {

	/**
	 * 根据用户ID得到用户的待审事宜
	 * 
	 * @param userId
	 * @return
	 */
	public List<VwOfficeApproveDocuments> getPendingMatters(String userId, String documentNo, String submitUserName, String moduleId);

	/**
	 * 根据审批单据的Id得到审批单据的详细信息
	 * 
	 * @param documentId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ApproveDocuments getApproveDocumentsById(String documentId);

	/**
	 * 修改审批的单据
	 * 
	 * @param approveDocument
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean updateApproveDocument(ApproveDocuments approveDocument);

	/**
	 * 根据用户ID分页得到其审批过的单据（）
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<ApproveDocuments> getApprovedMatters(String userId, int pageNo, int pageSize);

	/**
	 * 分页加载已审单据
	 * 
	 * @param userId
	 * @param documentNo
	 * @param submitUserName
	 * @param beginTieme
	 * @param endTime
	 * @param pageNo
	 *            从0开始
	 * @param pageSize
	 * @return
	 */
	public List<ApproveDocuments<?>> findApprovedMattersByProperties(String userId, String documentNo,
			String submitUserName, Date beginTieme, Date endTime, int pageNo, int pageSize);

	/**
	 * 根据用户ID得到由其提交的，且正在审批中的单据
	 * 
	 * @param userId
	 * @return
	 */
	public List<VwOfficeApproveDocuments> getMyApprovingMatters(String userId, String documentNo, String submitUserName, String moduleId);

	/**
	 * @Description: 通过单号找审批单
	 * @param doucumentNo
	 *            单号
	 * @return ApproveDocuments<?> 返回类型
	 * @throws
	 */

	public ApproveDocuments getApproveDocumentsByNo(String doucumentNo);

	/**
	 * 分页加载已审单据-NEW
	 * 
	 * @param userId
	 * @param documentNo
	 * @param submitUserName
	 * @param beginTieme
	 * @param endTime
	 * @param pageNo
	 *            从0开始
	 * @param pageSize
	 * @author caigx
	 * @return
	 */
	List<VwOfficeApproveDocuments> findApprovedMatters(String userId, String documentNo, String submitUserName, String moduleId,
			Date beginTime, Date endTime, int pageNo, int pageSize);

	/**
	 * 分页获得我的已审事宜
	 * @param userId
	 * @return
	 * @author caigx
	 */
	List<VwOfficeApproveDocuments> getMyApprovedMatters(String userId, String documentNo, String submitUserName, String moduleId,
			Date beginTieme, Date endTime, int pageNo, int pageSize);

	/**
	 * 根据ModuleId找模块名称
	 * @param moduleId
	 * @return
	 */
	public String getModuleNameByModuleId(String moduleId);

	/**
	 * 获得所有的待审事宜，iOS推送需要
	 * @return
	 */
	List<VwApproveDocumentsNotification> getAllApprovingMatters();

	public List<Map<String,Object>> getApproveModuleList();
}
