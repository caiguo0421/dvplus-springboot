package cn.sf_soft.finance.funds.dao.hbb;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.finance.funds.dao.FundsAccountsDao;
import cn.sf_soft.finance.funds.model.FinanceDocument;
import cn.sf_soft.finance.funds.model.FundsAccounts;
import cn.sf_soft.finance.funds.model.FundsAccountsSearchCriteria;
import cn.sf_soft.finance.funds.model.TotalCountOfFinance;
import cn.sf_soft.finance.funds.model.TotalCountOfFundsAccounts;

/**
 * 资金账户查询
 * @author liujin
 *
 */
@Repository("fundsAccountsDao")
public class FundsAccountsDaoHibernateImpl extends BaseDaoHibernateImpl implements FundsAccountsDao {

	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FundsAccountsDaoHibernateImpl.class);

	/**
	 * 获取资金查询信息
	 */
	@SuppressWarnings("unchecked")
	public List<FundsAccounts> getFundsAccountsData(final FundsAccountsSearchCriteria serchCriteria,final int pageNo,final int pageSize) {
	return (List<FundsAccounts>) getHibernateTemplate().execute(new HibernateCallback() {
			
			@SuppressWarnings("null")
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				String beginTime =  serchCriteria.getBeginTime();
				String endTime = serchCriteria.getEndTime();
				try {
					String station = " ";
					if(!"".equals(serchCriteria.getStationIds()) && serchCriteria.getStationIds() != null) {
						for(int i = 0;i<serchCriteria.getStationIds().size();i++){
							station += " OR station_id LIKE :stationId"+i+" ";
						}
					}
					//期初余额：在起始时间之前的余额，若起始时间为NULL则给定为2001-01-01
					if("".equals(serchCriteria.getBeginTime()) || serchCriteria.getBeginTime() == null){
						beginTime = "2001-01-01";
					}
					if("".equals(serchCriteria.getEndTime()) || serchCriteria.getEndTime() == null){
						Date dt = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
						endTime = sdf.format(dt);
					}
					sql = getQueryStringByName("getFundsAccounts", new String[]{"station","hql"}, new String[]{station,getSerchConditions(serchCriteria)});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				SQLQuery query = getQuery(session,sql,serchCriteria);
				 query.addEntity(FundsAccounts.class)
				.setString("beginTime", beginTime)
				.setString("endTime", endTime)
				.setString("userId", serchCriteria.getUserId())
				.setFirstResult((pageNo-1)*pageSize)
				.setMaxResults(pageSize);
				 if(!"".equals(serchCriteria.getStationIds()) && serchCriteria.getStationIds() != null) {
						for(int i = 0;i<serchCriteria.getStationIds().size();i++){
							query.setString("stationId"+i+"", "%"+serchCriteria.getStationIds().get(i)+"%");
						}
					}
				return query.list();
			}
		});
	}
	/**
	 * 获得资金查询总记录数
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getFundsAccountsCount(final FundsAccountsSearchCriteria serchCriteria) {
	return (List<Integer>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				String beginTime =  serchCriteria.getBeginTime();
				String endTime = serchCriteria.getEndTime();
				try {
					String station=" ";
					if(!"".equals(serchCriteria.getStationIds()) && serchCriteria.getStationIds() != null) {
						for(int i = 0;i<serchCriteria.getStationIds().size();i++){
							station += " OR station_id LIKE :stationId"+i+" ";
						}
					}
					if("".equals(serchCriteria.getBeginTime()) || serchCriteria.getBeginTime() == null){
						beginTime = "2001-01-01";
					}
					if("".equals(serchCriteria.getEndTime()) || serchCriteria.getEndTime() == null){
						Date dt = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
						endTime = sdf.format(dt);
					}
					sql = getQueryStringByName("getFundsAccountsCount", new String[]{"station","hql"}, new String[]{station,getSerchConditions(serchCriteria)});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				SQLQuery query = getQuery(session,sql,serchCriteria);
				query.setString("beginTime", beginTime)
				     .setString("endTime", endTime)
				     .setString("userId", serchCriteria.getUserId());
				 if(!"".equals(serchCriteria.getStationIds()) && serchCriteria.getStationIds() != null) {
						for(int i = 0;i<serchCriteria.getStationIds().size();i++){
							query.setString("stationId"+i+"", "%"+serchCriteria.getStationIds().get(i)+"%");
						}
					}
				return query.list();
				
			}
		});
	}
	/**
	 *  获得资金查询总计
	 */
	
	@SuppressWarnings("unchecked")
	public List<TotalCountOfFundsAccounts> getTotalCountOfFundsAccounts(final FundsAccountsSearchCriteria serchCriteria) {
	return (List<TotalCountOfFundsAccounts>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				String beginTime =  serchCriteria.getBeginTime();
				String endTime = serchCriteria.getEndTime();
				try {
					String station=" ";
					
					if(!"".equals(serchCriteria.getStationIds()) && serchCriteria.getStationIds() != null) {
						for(int i = 0;i<serchCriteria.getStationIds().size();i++){
							station += " OR station_id LIKE :stationId"+i+" ";
						}
					}
					if("".equals(serchCriteria.getBeginTime()) || serchCriteria.getBeginTime() == null){
						beginTime = "2001-01-01";
					}
					if("".equals(serchCriteria.getEndTime()) || serchCriteria.getEndTime() == null){
						Date dt = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
						endTime = sdf.format(dt);
					}
					sql = getQueryStringByName("getTotalCountOfFundsAccounts",
							new String[]{"station","hql"}, new String[]{station,getSerchConditions(serchCriteria)});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				SQLQuery query = getQuery(session,sql,serchCriteria);
				query.addEntity(TotalCountOfFundsAccounts.class)
				.setString("beginTime", beginTime)
				.setString("endTime", endTime)
				.setString("userId", serchCriteria.getUserId());
				if(!"".equals(serchCriteria.getStationIds()) && serchCriteria.getStationIds() != null) {
					for(int i = 0;i<serchCriteria.getStationIds().size();i++){
						query.setString("stationId"+i+"", "%"+serchCriteria.getStationIds().get(i)+"%");
					}
				}
				return query.list();
			}
		});
	}

	public String getSerchConditions(FundsAccountsSearchCriteria serchCriteria){
		StringBuffer conditions=new StringBuffer(" 1 = 1 ");
		if(!"".equals(serchCriteria.getAccountName()) && serchCriteria.getAccountName() != null) {
			conditions.append(" AND a.account_name LIKE :accountName  ");
		}
		if(!"".equals(serchCriteria.getAccountNo()) && serchCriteria.getAccountNo() != null) {
			conditions.append(" AND a.account_no LIKE :accountNo  " );
		}
		if(!"".equals(serchCriteria.getAccountTypeMeaning()) && serchCriteria.getAccountTypeMeaning() != null) {
			conditions.append(" AND b.meaning = :meaning  " );
		}
		String search = " 1 = 1 ";
		if(serchCriteria.getKeyword() != null && serchCriteria.getKeyword().length() > 0){
			String keyword = serchCriteria.getKeyword();
			search = " ( a.account_name LIKE '%"+ keyword +"%' " +
					" OR a.account_no LIKE '%" + keyword + "%'" +
					" OR a.bank_account_name LIKE '%" + keyword + "%' ) ";
		}

		return " AND (" + conditions.toString() + " AND " + search +")";
	}
	public SQLQuery getQuery(Session session,String sql,FundsAccountsSearchCriteria serchCriteria){
		SQLQuery query = session.createSQLQuery(sql);
		if(!"".equals(serchCriteria.getAccountName()) && serchCriteria.getAccountName() != null) {
			query.setString("accountName", "%"+serchCriteria.getAccountName()+"%");
		}
		if(!"".equals(serchCriteria.getAccountNo()) && serchCriteria.getAccountNo() != null) {
			query.setString("accountNo", "%"+serchCriteria.getAccountNo()+"%");
		}
		if(!"".equals(serchCriteria.getAccountTypeMeaning()) && serchCriteria.getAccountTypeMeaning() != null) {
			query.setString("meaning", serchCriteria.getAccountTypeMeaning());
		}
		return query;
	}
	/**
	 * 资金账户出入明细
	 */
	@SuppressWarnings("unchecked")
	public List<FinanceDocument> getFinanceDocumentEntries(final FundsAccountsSearchCriteria serchCriteria,final int pageNo,final int pageSize) {
	return (List<FinanceDocument>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				try {
					StringBuffer hql = new StringBuffer(" 1 = 1 ");
					if(!"".equals(serchCriteria.getAccountName()) && serchCriteria.getAccountName() != null) {
						hql.append(" AND  b.account_name LIKE :accountName  ");
					}
					if(!"".equals(serchCriteria.getAccountNo()) && serchCriteria.getAccountNo() != null) {
						hql.append(" AND b.account_no LIKE :accountNo  " );
					}
					if(!"".equals(serchCriteria.getAccountTypeMeaning()) && serchCriteria.getAccountTypeMeaning() != null) {
						hql.append(" AND e.meaning = :meaning  " );
					}
					if(!"".equals(serchCriteria.getBeginTime()) && serchCriteria.getBeginTime() != null){
						hql.append(" AND DATEDIFF(DAY, :beginTime, a.document_time) >= 0    " );
					}
					if(!"".equals(serchCriteria.getEndTime()) && serchCriteria.getEndTime() != null){
						hql.append(" AND DATEDIFF(DAY, :endTime, a.document_time) <= 0     " );
					}

					String search = " 1 = 1 ";
					if(serchCriteria.getKeyword() != null && serchCriteria.getKeyword().length() > 0){
						String keyword = serchCriteria.getKeyword();
						search = " (  document_type LIKE '%" +  keyword + "%' " +
								" OR document_no LIKE  '%" +  keyword + "%' " +
								" OR d.meaning LIKE  '%" +  keyword + "%' " +
								" OR object_name LIKE  '%" +  keyword + "%' ) ";
					}

					String hqlBuild = " AND (" + hql.toString() + " AND " + search + ")";
					sql = getQueryStringByName("getFinanceDocumentEntries", new String[]{"hql"}, new String[]{hqlBuild});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				SQLQuery query = getQuery(session,sql,serchCriteria);
				 query.addEntity(FinanceDocument.class)
				.setString("userId", serchCriteria.getUserId())
				.setString("accountId",serchCriteria.getAccountId())
				.setFirstResult((pageNo-1)*pageSize)
				.setMaxResults(pageSize);
				 
				 if(!"".equals(serchCriteria.getBeginTime()) && serchCriteria.getBeginTime() != null){
					 query.setString("beginTime", serchCriteria.getBeginTime());
					}
				 if(!"".equals(serchCriteria.getEndTime()) && serchCriteria.getEndTime() != null){
					 query.setString("endTime", serchCriteria.getEndTime());
					}
				 return query.list();
			}
		});
	}
	
	/**
	 *  资金账户出入明细总记录数
	 */
	
	@SuppressWarnings("unchecked")
	public List<Integer> getFinanceDocumentEntriesCount(final FundsAccountsSearchCriteria serchCriteria) {
	return (List<Integer>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				try {
					StringBuffer hql = new StringBuffer(" 1 = 1 ");
					if(!"".equals(serchCriteria.getAccountName()) && serchCriteria.getAccountName() != null) {
						hql.append(" AND  b.account_name LIKE :accountName  ");
					}
					if(!"".equals(serchCriteria.getAccountNo()) && serchCriteria.getAccountNo() != null) {
						hql.append(" AND b.account_no LIKE :accountNo  " );
					}
					if(!"".equals(serchCriteria.getAccountTypeMeaning()) && serchCriteria.getAccountTypeMeaning() != null) {
						hql.append(" AND e.meaning = :meaning  " );
					}
					if(!"".equals(serchCriteria.getBeginTime()) && serchCriteria.getBeginTime() != null){
						hql.append(" AND DATEDIFF(DAY, :beginTime, a.document_time) >= 0    " );
					}
					if(!"".equals(serchCriteria.getEndTime()) && serchCriteria.getEndTime() != null){
						hql.append(" AND DATEDIFF(DAY, :endTime, a.document_time) <= 0     " );
					}

					String search = " 1 = 1 ";
					if(serchCriteria.getKeyword() != null && serchCriteria.getKeyword().length() > 0){
						String keyword = serchCriteria.getKeyword();
						search = " (  document_type LIKE '%" +  keyword + "%' " +
								" OR document_no LIKE  '%" +  keyword + "%' " +
								" OR d.meaning LIKE  '%" +  keyword + "%' " +
								" OR object_name LIKE  '%" +  keyword + "%' ) ";
					}

					String hqlBuild = " AND (" + hql.toString() + " AND " + search + ")";
					sql = getQueryStringByName("getFinanceDocumentEntriesCount", new String[]{"hql"}, new String[]{hqlBuild});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				SQLQuery query = getQuery(session,sql,serchCriteria);
				query.setString("userId", serchCriteria.getUserId())
					 .setString("accountId",serchCriteria.getAccountId());
				if(!"".equals(serchCriteria.getBeginTime()) && serchCriteria.getBeginTime() != null){
					 query.setString("beginTime", serchCriteria.getBeginTime());
					}
				 if(!"".equals(serchCriteria.getEndTime()) && serchCriteria.getEndTime() != null){
					 query.setString("endTime", serchCriteria.getEndTime());
					}
				return query.list();
			}
		});
	}
	
	/**
	 *  出入明细总计
	 */
	
	@SuppressWarnings("unchecked")
	public List<TotalCountOfFinance> getTotalCountOfFinance(final FundsAccountsSearchCriteria serchCriteria) {
	return (List<TotalCountOfFinance>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				try {
					StringBuffer hql = new StringBuffer(" ");
					if(!"".equals(serchCriteria.getAccountName()) && serchCriteria.getAccountName() != null) {
						hql.append(" and  b.account_name LIKE :accountName  ");
					}
					if(!"".equals(serchCriteria.getAccountNo()) && serchCriteria.getAccountNo() != null) {
						hql.append(" AND b.account_no LIKE :accountNo  " );
					}
					if(!"".equals(serchCriteria.getAccountTypeMeaning()) && serchCriteria.getAccountTypeMeaning() != null) {
						hql.append(" AND e.meaning = :meaning  " );
					}
					if(!"".equals(serchCriteria.getBeginTime()) && serchCriteria.getBeginTime() != null){
						hql.append(" AND DATEDIFF(DAY, :beginTime, a.document_time) >= 0    " );
					}
					if(!"".equals(serchCriteria.getEndTime()) && serchCriteria.getEndTime() != null){
						hql.append(" AND DATEDIFF(DAY, :endTime, a.document_time) <= 0     " );
					}
					sql = getQueryStringByName("getTotalCountOfFinance", new String[]{"hql"}, new String[]{hql.toString()});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				SQLQuery query = getQuery(session,sql,serchCriteria);
				query.addEntity(TotalCountOfFinance.class)
					 .setString("userId", serchCriteria.getUserId())
					 .setString("accountId",serchCriteria.getAccountId());
				if(!"".equals(serchCriteria.getBeginTime()) && serchCriteria.getBeginTime() != null){
					 query.setString("beginTime", serchCriteria.getBeginTime());
					}
				 if(!"".equals(serchCriteria.getEndTime()) && serchCriteria.getEndTime() != null){
					 query.setString("endTime", serchCriteria.getEndTime());
					}
				return query.list();
			}
		});
	}
	
	/**
	 * 初始化查询条件
	 */
	@SuppressWarnings("unchecked")
	public List<String> getFundsAccountSearchCriteriaDate() {
			  return findByNamedQueryAndNamedParam("getfundsAccountType", null, null);
	}

	@Override
	public List<Map<String, Object>> getPayable(List<String> stationIds, String objectName) {
		String sql;
		String stationIdStr = " IS NOT NULL ";
		if(stationIds != null && stationIds.size() > 0){
			stationIdStr = " IN ('" + StringUtils.join(stationIds, "','") + "') ";
		}

		if(objectName == null) {
			sql = this.getQueryStringByName("getPayableByStationId", new String[]{"stationIds"}, new String[]{stationIdStr});
		}else{
			sql = this.getQueryStringByName("getPayableByStationIdAndObjectName",
					new String[]{"stationIds", "objectName"},
					new String[]{stationIdStr, "'"+objectName+"'"});
		}
		return getMapBySQL(sql, null);
	}

	@Override
	public List<Map<String, Object>> getReceivable(List<String> stationIds, String objectName) {
		String sql;
		String stationIdStr = " IS NOT NULL ";
		if(stationIds != null && stationIds.size() > 0){
			stationIdStr = " IN ('" + StringUtils.join(stationIds, "','") + "') ";
		}

		if(objectName == null) {
			sql = this.getQueryStringByName("getReceivableByStationId", new String[]{"stationIds"}, new String[]{stationIdStr});
		}else{
			sql = this.getQueryStringByName("getReceivableByStationIdAndObjectName",
					new String[]{"stationIds", "objectName"},
					new String[]{stationIdStr, "'"+objectName+"'"});
		}
		return getMapBySQL(sql, null);
	}

	@Override
	public List<Map<String, Object>> getPayableTrend(List<String> stationIds, String objectName, String businessType) {
		String sql;
		String stationIdStr = " IS NOT NULL ";
		if(stationIds != null && stationIds.size() > 0){
			stationIdStr = " IN ('" + StringUtils.join(stationIds, "','") + "') ";
		}

		if(objectName == null) {
			sql = this.getQueryStringByName("getPayableTrendByStationId",
					new String[]{"stationIds"},
					new String[]{stationIdStr});
		}else{
			sql = this.getQueryStringByName("getPayableTrendByStationIdAndObjectName",
					new String[]{"stationIds", "objectName"},
					new String[]{stationIdStr, "'"+objectName+"'"});
		}
		return getMapBySQL(sql, null);
	}


	@Override
	public List<Map<String, Object>> getReceivableTrend(List<String> stationIds, String objectName, String businessType) {
		String sql;
		String stationIdStr = " IS NOT NULL ";
		if(stationIds != null && stationIds.size() > 0){
			stationIdStr = " IN ('" + StringUtils.join(stationIds, "','") + "') ";
		}

		if(objectName == null) {
			sql = this.getQueryStringByName("getReceivableTrendByStationId",
					new String[]{"stationIds"},
					new String[]{stationIdStr});
		}else{
			sql = this.getQueryStringByName("getReceivableTrendByStationIdAndObjectName",
					new String[]{"stationIds", "objectName"},
					new String[]{stationIdStr, "'"+objectName+"'"});
		}
		return getMapBySQL(sql, null);
	}
}
