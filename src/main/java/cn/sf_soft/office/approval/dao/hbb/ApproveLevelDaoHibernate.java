package cn.sf_soft.office.approval.dao.hbb;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.ApproveLevelDao;
import cn.sf_soft.office.approval.model.ApproveLevels;
import cn.sf_soft.office.approval.model.ApproveLevelsPoints;
/**
 * 审批级别DAO
 * @author king
 * @create 2012-12-12上午10:03:29
 */
@Repository("approveLevelDaoHibernate")
public class ApproveLevelDaoHibernate extends BaseDaoHibernateImpl implements ApproveLevelDao {

	@SuppressWarnings("unchecked")
	public ApproveLevels getApproveLevel(int approveLevel,
			String moduleId, String submitStationId) {
		List<ApproveLevels> list = (List<ApproveLevels>) getHibernateTemplate().find("from ApproveLevels a left join fetch a.approvelPoint where a.approveLevel=? and a.stationId=? and a.moduleId=?",new Object[]{approveLevel,submitStationId,moduleId});
		if(list != null && list.size() != 0)
			return list.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ApproveLevels> getApproveLevels(String moduleId,
			String submitStationId) {
		List<ApproveLevels> list = (List<ApproveLevels>) getHibernateTemplate().find("from ApproveLevels a where a.stationId=? and a.moduleId=?",new Object[]{submitStationId,moduleId});
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ApproveLevelsPoints> getApproveLevelsPoints(String levelId) {
		return (List<ApproveLevelsPoints>) getHibernateTemplate().find("select distinct p from ApproveLevelsPoints p left join fetch p.levelPointCondition where p.levelId=?", levelId);
	}

}
