package cn.sf_soft.office.postaudit.service;

import java.util.List;

import cn.sf_soft.office.postaudit.model.OfficeAuditEntries;
import cn.sf_soft.office.postaudit.model.PostAuditInitData;
import cn.sf_soft.office.postaudit.model.PostAuditSearchCriteria;
import cn.sf_soft.user.model.SysUsers;

/**
 * 事后审核
 * @author king
 * @create 2013-9-18下午4:48:50
 */
public interface PostAuditService {

	/**
	 * 根据条件分页查询事后审核<b>待审事项</b>
	 * @param pageNo
	 * @param pageSize
	 * @param searchCriteria
	 * @return
	 */
	public List<OfficeAuditEntries> getPendingAuditEntriesByProperties(int pageNo, int pageSize, PostAuditSearchCriteria searchCriteria);
	
	/**
	 * 根据条件动态组合查询出事后审核<b>已审事项</b>
	 * @param pageNo	
	 * @return
	 */
	public List<OfficeAuditEntries> getApprovedEntryByProperties(int pageNo, int pageSize, PostAuditSearchCriteria searchCriteria);

	/**
	 * 处理事后审核事项
	 * @param entryId
	 * @param handleResult 处理结果
	 * @param handleOpinion 处理意见
	 * @param approveOpion 审批意见
	 * @param curUser 当前登录用户
	 * @return 处理之后的{@link OfficeAuditEntries}
	 */
	public OfficeAuditEntries handleAuditEntries(String entryId, String handleResult, String handleOpinion, SysUsers curUser);

	/**
	 * 审批事后审核事项
	 * 
	 * @param entryId
	 * @param approveOpion 审批意见
	 * @param curUser 当前登录用户
	 * @return 处理之后的{@link OfficeAuditEntries}
	 */
	public OfficeAuditEntries approveAuditEntries(String entryId, String handleResult, String handleOpinion, String approveOpinion, SysUsers curUser);
	
	/**
	 * 得到初始化数据
	 */
	public PostAuditInitData getInitData();
}
