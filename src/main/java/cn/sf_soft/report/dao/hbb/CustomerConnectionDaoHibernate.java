package cn.sf_soft.report.dao.hbb;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.report.dao.CustomerConnectionDao;
import cn.sf_soft.report.model.SaleCallBackReport;

/**
 * 
 * @Title: 客户关系报表
 * @date 2013-11-9 上午10:13:05 
 * @author cw
 */
@Repository("customerConnectionReportDao")
public class CustomerConnectionDaoHibernate extends 
BaseDaoHibernateImpl implements  CustomerConnectionDao{
	/**
	 * 销售回访
	 */
	@SuppressWarnings("unchecked")
	public List<SaleCallBackReport> getSaleCallBackData(
			String beginTime, String endTime, List<String> stationIds
			){
		List<SaleCallBackReport> list = (List<SaleCallBackReport>) getHibernateTemplate()
				.findByNamedQueryAndNamedParam("getSaleCallBackData", 
						new String[]{"stationIds", "beginTime", "endTime"}, 
						new Object[]{stationIds, beginTime, endTime});
		return list;
	}
	/**
	 * 维修回访
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SaleCallBackReport> getServiceCallBackData(
			String beginTime, String endTime, List<String> stationIds
			){
		List<SaleCallBackReport> list = (List<SaleCallBackReport>) getHibernateTemplate()
				.findByNamedQueryAndNamedParam("getServiceCallBackData", 
						new String[]{"stationIds", "beginTime", "endTime"}, 
						new Object[]{stationIds, beginTime, endTime});
		return list;
	}
}
