package cn.sf_soft.office.approval.dao;

import java.util.List;

import cn.sf_soft.office.approval.model.ApproveConditions;

/**
 * 审批条件表DAO
 * @author king
 * @create 2012-12-12上午10:37:18
 */
public interface ApproveConditionDao {
	/**
	 * 得到审批的条件
	 * @param moduelId		   模块的ID
	 * @param conditionField 条件属性
	 * @return
	 */
	public ApproveConditions getApproveCondition(String moduleId, String conditionField);

	/**
	 * 根据审批条件sql查询
	 * @param sql
	 * @param documentNo
	 * @param conditionType
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	int checkConditionSql(String sql, String documentNo, String conditionType, Object minValue, Object maxValue);
}
