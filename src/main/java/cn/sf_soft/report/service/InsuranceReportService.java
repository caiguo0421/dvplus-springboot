package cn.sf_soft.report.service;

import java.util.List;

import cn.sf_soft.report.model.InsurancePurchaseReport;

/**
 * 保险统计
 * @author king
 * @create 2013-11-8上午11:26:43
 */
public interface InsuranceReportService {

	/**
	 * 保险购险统计(按供应商+保单类型)
	 * @param beginTime
	 * @param endTIme
	 * @param stationIds
	 * @return
	 */
	public List<InsurancePurchaseReport> getReportBySupplierAndInsuType(String beginTime, String endTime, List<String> stationIds);
	
	/**
	 * 保险购险统计(按保险类别+保单类型)
	 * @param beginTime
	 * @param endTIme
	 * @param stationIds
	 * @return
	 */
	public List<InsurancePurchaseReport> getReportByInsuCategoryAndType(String beginTime, String endTime, List<String> stationIds);
}
