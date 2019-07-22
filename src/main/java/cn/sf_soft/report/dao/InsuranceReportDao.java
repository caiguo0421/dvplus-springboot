package cn.sf_soft.report.dao;

import java.util.List;

import cn.sf_soft.report.model.InsurancePurchaseReport;

/**
 * 保险统计
 * @author king
 * @create 2013-11-11上午10:05:52
 */
public interface InsuranceReportDao{

	/**
	 * 保险购险统计(按供应商+保单类型)
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @return
	 */
	public List<InsurancePurchaseReport> statBySupplierAndInsuType(String beginTime, String endTime, List<String> stationIds);
	
	/**
	 * 保险购险统计(按保险类别+保单类型)
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @return
	 */
	public List<InsurancePurchaseReport> statByInsuCategoryAndType(String beginTime, String endTime, List<String> stationIds);
}
