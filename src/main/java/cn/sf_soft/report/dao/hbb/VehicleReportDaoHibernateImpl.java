package cn.sf_soft.report.dao.hbb;

import java.sql.SQLException;
import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.report.dao.VehicleReportDao;
import cn.sf_soft.report.model.VehicleMonthlyReport;
import cn.sf_soft.report.model.VehiclePeriodOfTimeReport;

@Repository("vehicleReportDao")
public class VehicleReportDaoHibernateImpl extends BaseDaoHibernateImpl implements VehicleReportDao {

	final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleReportDaoHibernateImpl.class);

	@SuppressWarnings("unchecked")
	// 销量统计(月度)
	public List<VehicleMonthlyReport> getMonthlySaleReportData(final String dateTime, final String analyseType,
			final List<String> stationIds, final String datetype, final String stateContent) {
		return (List<VehicleMonthlyReport>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				try {
					sql = getQueryStringByName("vehicleMonthlyQuery", new String[] { "analyseType", "analyseMode",
							"analyseResult" }, new String[] { analyseType, datetype, stateContent });
					logger.debug(String.format("销量统计(月度) sql:%s,stationIds:%s,dateTime：%s", sql, stationIds, dateTime));
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				return session.createSQLQuery(sql).addEntity(VehicleMonthlyReport.class)
						.setParameterList("stationIds", stationIds, new StringType()).setString("dateTime", dateTime)
						.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	// 时间段内的销售统计
	public List<VehiclePeriodOfTimeReport> getPeriodOfTimeSaleReportData(final String beginTime, final String endTime,
			final String analyseType, final List<String> stationIds, final String datetype) {
		return (List<VehiclePeriodOfTimeReport>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = "";
				try {
					sql = getQueryStringByName("periodOfTimeSaleReportData",
							new String[] { "analyseType", "datetype" }, new String[] { analyseType, datetype });

					logger.debug(String.format("销售统计（时间段） sql:%s,stationIds:%s,beginTime：%s,endTime:%s", sql,
							stationIds, beginTime, endTime));
				} catch (Exception e) {
					throw new HibernateException(e.getMessage());
				}
				return getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).addEntity(VehiclePeriodOfTimeReport.class)
						.setParameterList("stationIds", stationIds, new StringType()).setString("beginTime", beginTime)
						.setString("endTime", endTime).list();
			}
		});
	}

}
