package cn.sf_soft.report.dao.hbb;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.report.dao.FinanceReportDao;
import cn.sf_soft.report.model.FinanaceGatheringReport;
import cn.sf_soft.report.model.FinanceReportOfPayMode;
import cn.sf_soft.report.model.FinanceType;

/**
 * 
 * @Title:  收款&付款（账户）报表
 * @date 2013-10-30 下午03:18:52 
 * @author cw
 */
@Repository("financeReportDao")
public class FinanceReportDaoHibernate extends 
			BaseDaoHibernateImpl implements FinanceReportDao {

		/**
		 * 初始化收款类型数据
		 */
	@SuppressWarnings("unchecked")
		public List<FinanceType> getGatheringInitData(){
			List<FinanceType> list = findByNamedQueryAndNamedParam("getGatheringInitData", null, null);
			return list;
		}
	
	/**
	 * 收款统计（账户）
	 */
	@SuppressWarnings("unchecked")
	public List<FinanaceGatheringReport> getGatheringReportData(final String beginTime,final String endTime,
			final List<String> stationIds,final String financeType) {
	return (List<FinanaceGatheringReport>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException
					{
				String sql = null;
				try {
					StringBuffer hql = new StringBuffer(" ");
					if(!"".equals(beginTime) && beginTime != null) {
						hql.append(" AND DATEDIFF(SECOND, '"+beginTime+"', a.approve_time) >= 0  ");
						
					}
					if(!"".equals(endTime) && endTime != null) {
						hql.append(" AND DATEDIFF(SECOND, '"+endTime+"', a.approve_time) <= 0  ");
					}
					if(!"".equals(financeType) && financeType != null && !"null".equals(financeType)) {
						hql.append(" AND a.settle_type = '"+financeType+"'  " );
					}
					sql = getQueryStringByName("getGatheringReportData", new String[]{"hql"}, new String[]{hql.toString()});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				return session.createSQLQuery(sql)
						.addEntity(FinanaceGatheringReport.class)
						.setParameterList("stationIds", stationIds, new StringType())
						.list();
			}
		});
	}
	/**
	 * 初始化付款类型数据
	 */
	@SuppressWarnings("unchecked")
	public List<FinanceType> getPayMentInitData(){
		List<FinanceType> list = findByNamedQueryAndNamedParam("getPaymentInitData", null, null);
		return list;
	}
	/**
	 * 付款统计（账户）
	 */
	@SuppressWarnings("unchecked")
	public List<FinanaceGatheringReport> getPayMentReportData(final String beginTime,final String endTime,
			final List<String> stationIds,final String financeType) {
	return (List<FinanaceGatheringReport>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				try {
					StringBuffer hql = new StringBuffer(" ");
					if(!"".equals(beginTime) && beginTime != null) {
						hql.append(" AND DATEDIFF(SECOND, '"+beginTime+"', a.approve_time) >= 0  ");
						
					}
					if(!"".equals(endTime) && endTime != null) {
						hql.append(" AND DATEDIFF(SECOND, '"+endTime+"', a.approve_time) <= 0  ");
					}
					if(!"".equals(financeType) && financeType != null && !"null".equals(financeType)) {
						hql.append(" AND a.settle_type = '"+financeType+"'  " );
					}
					sql = getQueryStringByName("getPayMentReportData", new String[]{"hql"}, new String[]{hql.toString()});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				return session.createSQLQuery(sql)
						.addEntity(FinanaceGatheringReport.class)
						.setParameterList("stationIds", stationIds, new StringType())
						.list();
			}
		});
	}
	
	/**
	 * 收款统计（收款方式）
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @param financeType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FinanceReportOfPayMode> getGatheringReportForPayModeData(final String beginTime,final String endTime,
			final List<String> stationIds,final String financeType) {
	return (List<FinanceReportOfPayMode>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				try {
					StringBuffer hql = new StringBuffer(" ");
					if(!"".equals(beginTime) && beginTime != null) {
						hql.append(" AND DATEDIFF(SECOND, '"+beginTime+"', a.approve_time) >= 0  ");
						
					}
					if(!"".equals(endTime) && endTime != null) {
						hql.append(" AND DATEDIFF(SECOND, '"+endTime+"', a.approve_time) <= 0  ");
					}
					if(!"".equals(financeType) && financeType != null && !"null".equals(financeType)) {
						hql.append(" AND a.settle_type = '"+financeType+"'  " );
					}
					sql = getQueryStringByName("getFinanceReportForGatheringModeData", new String[]{"hql"}, new String[]{hql.toString()});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				return session.createSQLQuery(sql)
						.addEntity(FinanceReportOfPayMode.class)
						.setParameterList("stationIds", stationIds, new StringType())
						.list();
			}
		});
	}
	
	/**
	 * 付款统计（付款方式）
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @param financeType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FinanceReportOfPayMode> getPayMentReportForPayModeData(final String beginTime,final String endTime,
			final List<String> stationIds,final String financeType) {
	return (List<FinanceReportOfPayMode>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				try {
					StringBuffer hql = new StringBuffer(" ");
					if(!"".equals(beginTime) && beginTime != null) {
						hql.append(" AND DATEDIFF(SECOND, '"+beginTime+"', a.approve_time) >= 0  ");
						
					}
					if(!"".equals(endTime) && endTime != null) {
						hql.append(" AND DATEDIFF(SECOND, '"+endTime+"', a.approve_time) <= 0  ");
					}
					if(!"".equals(financeType) && financeType != null && !"null".equals(financeType)) {
						hql.append(" AND a.settle_type = '"+financeType+"'  " );
					}
					sql = getQueryStringByName("getFinanceReportForPayMentModeData", new String[]{"hql"}, new String[]{hql.toString()});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				return session.createSQLQuery(sql)
						.addEntity(FinanceReportOfPayMode.class)
						.setParameterList("stationIds", stationIds, new StringType())
						.list();
			}
		});
	}
}
