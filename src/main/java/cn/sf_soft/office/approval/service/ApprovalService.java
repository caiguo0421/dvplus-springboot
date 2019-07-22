package cn.sf_soft.office.approval.service;

import java.util.List;
import java.util.Map;

import cn.sf_soft.common.util.Constant.OSType;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocumentSearchCriteria;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.ApproveDocumentsPoints;
import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;
import cn.sf_soft.user.model.SysUsers;

/**
 * 审批
 * 
 * @author king
 * @create 2013-9-27下午4:37:22
 */
public interface ApprovalService {

	/**
	 * 根据用户ID得到其待审事宜
	 * 
	 * @param userId
	 * @return
	 */
	public List<VwOfficeApproveDocuments> getPendingMatters(String userId, ApproveDocumentSearchCriteria searchCriteria);

	/**
	 * 审批单据
	 * 
	 * @param curUser
	 *            当前登录用户
	 * @param agree
	 *            是否同意单据
	 * @param documentId
	 *            单据ID
	 * @param comment
	 *            审批备注
	 * @param modifyTime
	 *            修改时间
	 */
//	public ResponseMessage<Object> approveDocument(SysUsers curUser,
//			boolean agree, String documentId, String comment, String modifyTime);
	
	/**
	 * @param documentNo
	 * @Title: approveDocumentByPC
	 * @Description: 手机端审批
	 * @param user
	 *            审批人
	 * @param agree
	 *            是否同意
	 * @param documentId
	 *            审批单id
	 * @param comment
	 *            审批意见
	 * @param modifyTime
	 *            修改时间
	 * @return ApproveResult 审批结果
	 * @throws
	 */
//	public ApproveResult approveDocumentByPC(SysUsers user, boolean agree,
//			String documentId, String comment, String modifyTime,
//			String documentNo);

	/**
	 * 审批单据
	 * 
	 * @param curUser
	 *            当前登录用户
	 * @param agree
	 *            是否同意单据
	 * @param documentId
	 *            单据ID
	 * @param comment
	 *            审批备注
	 */
	// public void approveDocument(SysUsers curUser, boolean agree, String
	// documentId, String comment,Timestamp modifyTime);
	/**
	 * 根据单据编号得到单据的明细及子单据的明细
	 * 
	 * @param documentNo
	 * @param moduleId
	 * @return
	 */
	public ApproveDocuments<?> getDocumentDetail(String documentNo,
			String moduleId);

	/**
	 * 根据单据ID得到单据的审批历史
	 * 
	 * @param documentId
	 * @param moduleId
	 * @return
	 */
	public List<ApproveDocumentsPoints> getDocumentHistory(String documentId,
			String moduleId);

	/**
	 * 根据查询条件和用户ID查询出用户已审单据
	 * 
	 * @param userId
	 * @param searchCriteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<VwOfficeApproveDocuments> getApprovedMattersByProperties(
			String userId, ApproveDocumentSearchCriteria searchCriteria,
			int pageNo, int pageSize);

	/**
	 * 根据用户ID得到该用户提交，等待审批完成的单据
	 * 
	 * @param userId
	 * @return
	 */
	public List<VwOfficeApproveDocuments> getMyApprovingMatters(String userId, ApproveDocumentSearchCriteria searchCriteria);

	
	
	/**
	 * 合并PC端和手机端的审批
	 * 
	 * @param user
	 *            审批用户
	 * @param agree
	 *            是否同意
	 * @param documentId
	 *            单据Id
	 * @param comment
	 *            审批意见
	 * @param modifyTime
	 *            修改时间，做版本校验
	 * @param doucumentNo
	 *            单据编号
	 * @param osType
	 *            os类型：PC和手机
	 * @author caigx
	 * @create 2016-04-20下午5:02:08
	 */
	public ApproveResult approveDocument(SysUsers user, boolean agree,
			String documentId, String comment, String modifyTime,
			String doucumentNo, OSType osType);

	public ApproveResult approveDocumentWithExtraData(SysUsers user, boolean agree,
										 String documentId, String comment, String modifyTime,
										 String doucumentNo, Map<String, Object> extraData, OSType osType);


	/**
	 * 根据条件查询我的已审事宜(分页)
	 * @param userId
	 * @return
	 * @author caigx
	 */
	List<VwOfficeApproveDocuments> getMyApprovedMatters(String userId, ApproveDocumentSearchCriteria searchCriteria,
			int pageNo, int pageSize);

	/**
	 * 审批单据的提交接口
	 * @param user
	 * @param documentNo
	 * @param moduleId
	 * @param agree
	 * @param comment
	 * @param modifyTime
	 * @param pc
	 * @return
	 */
	public ApproveResult submitRecord(SysUsers user, String documentNo, String moduleId, boolean agree, String comment,
			String modifyTime, OSType pc);

	/**
	 * 获取开启的审批类型
	 * @return
	 */
	public List<Map<String, Object>> getApproveModuleList();



	/**
	 * 撤销操作
	 * @param documentNo
	 * @param moduleId
	 * @param modifyTime
	 */
	public Object revokeRecord(String documentNo, String moduleId, String modifyTime);

	/**
	 * 作废操作
	 * @param documentNo
	 * @param moduleId
	 * @param modifyTime
	 */
	public Object forbidRecord(String documentNo, String moduleId, String modifyTime);
}
