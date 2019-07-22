package cn.sf_soft.office.approval.dao;

import cn.sf_soft.office.approval.model.ApproveAgent;

/**
 * 
 * @author minggo
 * @created 2012-12-13
 */
public interface ApproveAgentDao {
	/**
	 * get the valid Agent
	 * 
	 * @param principal
	 * @param moduleId
	 * @param stationId
	 * @return
	 */
	public ApproveAgent getAgent(short status, String originUserId,
			String moduleId, String stationId);

	// add by shichunshan fix 代理审批选择所有审批功能时不起作用
	public ApproveAgent getAgent(short status, String originUserId,
			String stationId);

	/**
	 * 查找模块对应的 审批代理
	 * @param status
	 * @param originUserId
	 * @param moduleId
	 * @param stationId
	 * @return
	 * @author caigx
	 */
	ApproveAgent getAgentByMoudleId(short status, String originUserId, String moduleId, String stationId);
}
