package cn.sf_soft.report.dao.hbb;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.report.dao.MaintainReportDao;
import cn.sf_soft.report.model.MainTainDailyLineReport;
import cn.sf_soft.report.model.MaintainClaimDailyReport;
import cn.sf_soft.report.model.MaintainClaimsReport;
import cn.sf_soft.report.model.MaintainReport;
import cn.sf_soft.report.model.MaintainReportofTime;

/**
 * 
 * @Title: 车辆维修报表
 * @date 2013-7-24 上午09:37:21
 * @author cw
 */
@Repository("maintainReportDao")
public class MaintainReportDaoHibernateImpl extends BaseDaoHibernateImpl implements MaintainReportDao {
	@SuppressWarnings("unchecked")
	final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MaintainReportDaoHibernateImpl.class);

	/**
	 * 每日维修报表
	 */
	public List<MaintainReport> getMaintainDayReport(final String dateTime, final String analyseType,
			final List<String> stationIds) {
		return (List<MaintainReport>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				String sql = "";
				String time = "";
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					time = sdf.format(sdf.parse(dateTime)).substring(0, 7);
					time += "-01";
					sql = getQueryStringByName("maintainDayReport", new String[] { "analyseType" },
							new String[] { analyseType });
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.debug(String.format("维修报表(日) sql:%s,stationIds:%s,dateTime:%s,time:%s", sql, stationIds,
						dateTime, time));
				return session.createSQLQuery(sql).addEntity(MaintainReport.class)
						.setParameterList("stationIds", stationIds, new StringType()).setString("dateTime", dateTime)
						.setString("time", time).list();
			}
		});
	}

	@SuppressWarnings({ "unchecked" })
	public List<MainTainDailyLineReport> getMaintainDailyLineReportData(String beginTime, String endTime,
			List<String> stationIds, int analyseType) {
		String queryName = "";
		if (analyseType == 1) {
			queryName = "maintainDailyNumQuery";
		} else {
			queryName = "maintainDailyOutputQuery";
		}
		List<MainTainDailyLineReport> list = (List<MainTainDailyLineReport>) getHibernateTemplate()
				.findByNamedQueryAndNamedParam(queryName, new String[] { "stationIds", "beginTime", "endTime" },
						new Object[] { stationIds, beginTime, endTime });
		return list;
	}

	/**
	 * 时间段内的维修报表
	 */
	@SuppressWarnings("unchecked")
	public List<MaintainReportofTime> getMaintainforTimeReport(final String beginTime, final String endTime,
			final String analyseType, final List<String> stationIds) {
		return (List<MaintainReportofTime>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				String sql = "";
				try {
					sql = getQueryStringByName("maintainforTimeReport", new String[] { "analyseType" },
							new String[] { analyseType });
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.debug(String.format("维修报表(时间段) sql:%s,stationIds:%s,beginTime:%s,endTime:%s", sql, stationIds,
						beginTime, endTime));
				return session.createSQLQuery(sql).addEntity(MaintainReportofTime.class)
						.setParameterList("stationIds", stationIds, new StringType()).setString("beginTime", beginTime)
						.setString("endTime", endTime).list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<MaintainClaimDailyReport> getMaintainClaimDailyReportData(String dateTime, List<String> stationIds) {
		
		return (List<MaintainClaimDailyReport>) getHibernateTemplate().findByNamedQueryAndNamedParam(
				"claimDailyReport", new String[] { "stationIds", "dateTime" }, new Object[] { stationIds, dateTime });
	}

	/**
	 * 三包索赔类别报表
	 */
	@SuppressWarnings("unchecked")
	public List<MaintainClaimsReport> getMaintainClaimsReportData(String beginTime, String endTime,
			List<String> stationIds) {
		return (List<MaintainClaimsReport>) getHibernateTemplate().findByNamedQueryAndNamedParam(
				"maintainClaimsReport", new String[] { "stationIds", "beginTime", "endTime" },
				new Object[] { stationIds, beginTime, endTime });
	}
}
