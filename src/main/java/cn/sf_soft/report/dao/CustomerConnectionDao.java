package cn.sf_soft.report.dao;

import java.util.List;

import cn.sf_soft.report.model.SaleCallBackReport;

/**
 * 
 * @Title: 客户关系报表
 * @date 2013-11-9 上午10:13:22 
 * @author cw
 */
public interface CustomerConnectionDao {
/**
 * 销售回访(回访，未回访，投诉数量)
 * @param beginTime
 * @param endTime
 * @param stationIds
 * @return
 */
	public List<SaleCallBackReport> getSaleCallBackData(
			String beginTime, String endTime, List<String> stationIds
			);
	/**
	 * 维修回访(回访，未回访，投诉数量)
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @return
	 */
	public List<SaleCallBackReport> getServiceCallBackData(
			String beginTime, String endTime, List<String> stationIds
			);
}
