package cn.sf_soft.office.approval.dao;

import java.util.List;

import cn.sf_soft.office.approval.model.ApproveLevels;
import cn.sf_soft.office.approval.model.ApproveLevelsPoints;

/**
 * 审批级别DAO
 * @author king
 * @create 2012-12-12上午09:56:52
 */
public interface ApproveLevelDao  {

	/**
	 * 得到审批级别的详细信息
	 * @param approveLevel		要得到的审批的级别
	 * @param moduleId			审批模块的ID
	 * @param submitStationId	提交审批单据的站点ID
	 * @return
	 */
	public ApproveLevels getApproveLevel(int approveLevel,String moduleId, String submitStationId);
	
	/**
	 * 得到审批级别
	 * @param moduleId
	 * @param submitStationId
	 * @return
	 */
	public List<ApproveLevels> getApproveLevels(String moduleId, String submitStationId);
	
	/**
	 * 通过审批级别的ID得到该审批级别下的审批点
	 * @param levelId
	 * @return
	 */
	public List<ApproveLevelsPoints> getApproveLevelsPoints(String levelId);
}
