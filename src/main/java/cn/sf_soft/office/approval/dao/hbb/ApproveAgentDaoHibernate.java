package cn.sf_soft.office.approval.dao.hbb;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.ApproveAgentDao;
import cn.sf_soft.office.approval.model.ApproveAgent;

/**
 * this HibernateDao is for ApproveAgent
 * 
 * @author minggo
 * @created 2012-12-13
 */
@Repository("approveAgentDaoHibernate")
public class ApproveAgentDaoHibernate extends BaseDaoHibernateImpl implements
		ApproveAgentDao {

	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApproveAgentDaoHibernate.class);
	
	/**
	 * find out the valid agent of approval
	 */
	@SuppressWarnings("unchecked")
	public ApproveAgent getAgent(short status, String originUserId,
			String moduleId, String stationId) {
		Timestamp now = new Timestamp(new Date().getTime());
		String hql = "from ApproveAgent a where a.originApprover=? and a.moduleId=? and a.stationId=? and a.status=? and a.beginTime<? and a.endTime>?";
		List<ApproveAgent> approveAgents = (List<ApproveAgent>) getHibernateTemplate()
				.find(hql,
						new Object[] { originUserId, moduleId, stationId,
								status, now, now });
		if (approveAgents != null && approveAgents.size() != 0)
			return approveAgents.get(0);
		return null;
	}

	// add by shichunshan fix 代理审批选择所有审批功能时不起作用
	@SuppressWarnings("unchecked")
	public ApproveAgent getAgent(short status, String originUserId,
			String stationId) {
		Timestamp now = new Timestamp(new Date().getTime());
		String hql = "from ApproveAgent a where a.originApprover=?  and a.stationId=? and a.status=? and datediff(dy,a.beginTime,?) <=0 and datediff(dy,a.endTime,?)>=0";
		List<ApproveAgent> approveAgents = (List<ApproveAgent>) getHibernateTemplate()
				.find(hql,
						new Object[] { originUserId, stationId, status, now,
								now });
		if (approveAgents != null && approveAgents.size() != 0)
			return approveAgents.get(0);
		return null;
	}
	
	
	//获得模块对应的审批代理
	@Override
	@SuppressWarnings("unchecked")
	public ApproveAgent getAgentByMoudleId(short status, String originUserId,
			String moduleId,String stationId){
		Timestamp now = new Timestamp(new Date().getTime());
		//moduleId='0000' 表示所有模块
		String hql = "from ApproveAgent a where a.originApprover=? and (a.moduleId='0000' OR a.moduleId LIKE ?) and a.stationId=? and a.status=? and datediff(dy,a.beginTime,?) >=0 and datediff(dy,a.endTime,?)<=0";
		// logger.debug(String.format("访问getAgentByMoudleId方法, hql:%s,status:%d,originUserId:%s,moduleId:%s,stationId:%s", hql,status,originUserId,moduleId,stationId));
		List<ApproveAgent> approveAgents = (List<ApproveAgent>) getHibernateTemplate()
				.find(hql,
						new Object[] { originUserId, '%'+moduleId+'%', stationId,
								status, now, now });
		if (approveAgents != null && approveAgents.size() != 0)
			return approveAgents.get(0);
		return null;
	}
}
