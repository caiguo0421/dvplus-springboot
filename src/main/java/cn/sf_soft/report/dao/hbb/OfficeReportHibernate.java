package cn.sf_soft.report.dao.hbb;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.basedata.model.BaseOthers;
import cn.sf_soft.basedata.model.SysFlags;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.report.dao.OfficeReportDao;
import cn.sf_soft.report.model.OfficeAssetReport;
import cn.sf_soft.report.model.SuppliesPosition;
/**
 * 
 * @Title: 办公统计
 * @date 2013-11-19 下午04:15:37 
 * @author cw
 */
@Repository("officeReportDao")
public class OfficeReportHibernate extends 
BaseDaoHibernateImpl implements OfficeReportDao {
	/**
	 * 初始化出入库类型数据
	 */
@SuppressWarnings("unchecked")
	public List<SysFlags> getTypeInitData(String Type){
		List<SysFlags> list = findByNamedQueryAndNamedParam("getTypeInitData", new String[]{"type"}, new String[]{Type});
		return list;
	}
	/**
	 * 初始化存放位置数据
	 */
	@SuppressWarnings("unchecked")
	public List<BaseOthers> getPositionInitData(){
		List<BaseOthers> list = findByNamedQueryAndNamedParam("getPositionInitData", null,null);
		return list;
	}
	/**
	 * 初始化用品仓库数据
	 */
	@SuppressWarnings("unchecked")
	public List<SuppliesPosition> getSuppliesPositionInitData(List<String> stationIds){
		List<SuppliesPosition> list = findByNamedQueryAndNamedParam("getSuppliesPositionInitData",new String[]{"stationIds"}, new Object[]{stationIds});
		return list;
	}
	/**
	 * 资产出库查询（出库类型）
	 */
	@SuppressWarnings("unchecked")
	public List<OfficeAssetReport> getAssetOutReoprtData(final String beginTime,final String endTime,
			final List<String> stationIds,final String posType,final String depositPosition) {
	return (List<OfficeAssetReport>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				try {
					StringBuffer hql = new StringBuffer(" ");
					if(!"".equals(posType) && posType != null && !"null".equals(posType)) {
						hql.append(" AND pos_type = '"+posType+"'  ");
					}
					if(!"".equals(depositPosition) && depositPosition != null) {
						hql.append(" AND deposit_position LIKE '%"+depositPosition+"%'  ");
					}
					sql = getQueryStringByName("getAssetOutReoprtData", new String[]{"hql"}, new String[]{hql.toString()});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				return session.createSQLQuery(sql)
						.addEntity(OfficeAssetReport.class)
						.setParameterList("stationIds", stationIds, new StringType())
						.setString("beginTime", beginTime)
						.setString("endTime", endTime)
						.list();
			}
		});
	}
	
	/**
	 * 资产入库查询（出库类型）
	 */
	@SuppressWarnings("unchecked")
	public List<OfficeAssetReport> getAssetInReoprtData(final String beginTime,final String endTime,
			final List<String> stationIds,final String pis_type,final String depositPosition) {
	return (List<OfficeAssetReport>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				try {
					StringBuffer hql = new StringBuffer(" ");
					if(!"".equals(pis_type) && pis_type != null && !"null".equals(pis_type)) {
						hql.append(" AND pis_type = '"+pis_type+"'  ");
					}
					if(!"".equals(depositPosition) && depositPosition != null) {
						hql.append(" AND deposit_position LIKE '%"+depositPosition+"%'  ");
					}
					sql = getQueryStringByName("getAssetInReoprtData", new String[]{"hql"}, new String[]{hql.toString()});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				return session.createSQLQuery(sql)
						.addEntity(OfficeAssetReport.class)
						.setParameterList("stationIds", stationIds, new StringType())
						.setString("beginTime", beginTime)
						.setString("endTime", endTime)
						.list();
			}
		});
	}
	
	/**
	 * 用品入库查询（入库类型）
	 */
	@SuppressWarnings("unchecked")
	public List<OfficeAssetReport> getThingsInReoprtData(final String beginTime,final String endTime,
			final List<String> stationIds,final String pis_type,final String positionCode) {
	return (List<OfficeAssetReport>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				try {
					StringBuffer hql = new StringBuffer(" ");
					if(!"".equals(pis_type) && pis_type != null && !"null".equals(pis_type)) {
						hql.append(" AND pis_type = '"+pis_type+"'  ");
					}
					if(!"".equals(positionCode) && positionCode != null) {
						hql.append(" AND position_code = '"+positionCode+"'  ");
					}
					sql = getQueryStringByName("getThingsInReoprtData", new String[]{"hql"}, new String[]{hql.toString()});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				return session.createSQLQuery(sql)
						.addEntity(OfficeAssetReport.class)
						.setParameterList("stationIds", stationIds, new StringType())
						.setString("beginTime", beginTime)
						.setString("endTime", endTime)
						.list();
			}
		});
	}
	
	/**
	 * 用品出库查询（出库类型）
	 */
	@SuppressWarnings("unchecked")
	public List<OfficeAssetReport> getThingsOutReoprtData(final String beginTime,final String endTime,
			final List<String> stationIds,final String pos_type,final String positionCode) {
	return (List<OfficeAssetReport>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				String sql = null;
				try {
					StringBuffer hql = new StringBuffer(" ");
					if(!"".equals(pos_type) && pos_type != null && !"null".equals(pos_type)) {
						hql.append(" AND pos_type = '"+pos_type+"'  ");
					}
					if(!"".equals(positionCode) && positionCode != null) {
						hql.append(" AND position_code = '"+positionCode+"'  ");
					}
					sql = getQueryStringByName("getThingsOutReoprtData", new String[]{"hql"}, new String[]{hql.toString()});
				} catch (Exception e) {
					e.printStackTrace();
					throw new HibernateException(e.getMessage());
				}
				return session.createSQLQuery(sql)
						.addEntity(OfficeAssetReport.class)
						.setParameterList("stationIds", stationIds, new StringType())
						.setString("beginTime", beginTime)
						.setString("endTime", endTime)
						.list();
			}
		});
	}
}
