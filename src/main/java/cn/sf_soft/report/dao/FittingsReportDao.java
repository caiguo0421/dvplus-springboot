package cn.sf_soft.report.dao;

import java.util.List;
import java.util.Map;

import cn.sf_soft.report.model.FittingsOutStockReport;
import cn.sf_soft.report.model.FittingsPutInReport;

/**
 * 获取配件统计
 */
public interface FittingsReportDao {
	//配件出库时间段报表
	public List<FittingsOutStockReport> getFittingsOutStockReport(final String dateTime,final String endTime,
			final String analyseType, final List<String> stationIds);
	//配件入库时间段报表
	public List<FittingsPutInReport> getFittingPutInReport(final String beginTime,final String endTime,
			final String analyseType, final List<String> stationIds);

    List<Map<String,Object>> getStockOutReport(List<String> stationIds, String unitName, String beginTime, String endTime);

	List<Map<String,Object>> getStockInReport(List<String> stationIds, String unitName, String beginTime, String endTime);

	List<Map<String,Object>> getFixedReport(List<String> stationIds, String unitName, String beginTime, String endTime, String dateType);

	List<Map<String,Object>> getProductionValueTrade(List<String> stationIds, String unitName, String beginTime, String endTime, String dateUnit);

}
