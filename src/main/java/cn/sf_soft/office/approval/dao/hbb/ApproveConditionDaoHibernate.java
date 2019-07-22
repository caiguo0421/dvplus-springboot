package cn.sf_soft.office.approval.dao.hbb;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.office.approval.dao.ApproveConditionDao;
import cn.sf_soft.office.approval.model.ApproveConditions;

/**
 * 审批条件
 * 
 * @author liujin
 *
 */
@Repository("approveConditionDaoHibernate")
public class ApproveConditionDaoHibernate extends BaseDaoHibernateImpl implements ApproveConditionDao {

	/**
	 * 得到审批条件
	 */
	@SuppressWarnings("unchecked")
	public ApproveConditions getApproveCondition(String moduelId, String conditionField) {
		List<ApproveConditions> list = (List<ApproveConditions>) getHibernateTemplate().find(
				"from ApproveConditions a where a.moduleId=? and conditionField=?",
				new Object[] { moduelId, conditionField });
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据审批条件sql查询
	 * 
	 * @param sql
	 * @param documentNo
	 * @param conditionType
	 * @param minValue
	 * @param maxValue
	 * @return
	 */
	@Override
	public int checkConditionSql(String sql, String documentNo, String conditionType, Object minValue, Object maxValue) {
		SQLQuery query = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setParameter("documentNo", documentNo);
		
		if (Constant.ApproveConditionType.SELECTION.equals(conditionType)) {
			query.setParameterList("minValue", (Object[]) minValue);
		} else {
			query.setParameter("minValue", minValue);
		}
		// 只有日期类型和数字类型有 maxValue
		if (Constant.ApproveConditionType.DATETIME.equals(conditionType)
				|| Constant.ApproveConditionType.DECIMAL.equals(conditionType)) {
			query.setParameter("maxValue", maxValue);
		}
		return (int)query.uniqueResult();
		
	}

}
