package cn.sf_soft.report.service;

import java.util.List;
import java.util.Map;

import cn.sf_soft.report.model.FittingsOutStockReport;
import cn.sf_soft.report.model.FittingsPutInReport;
/**
 * 
 * @Title: 配件统计报表
 * @param dateTime		统计的时间
 * @param analyseType	统计类型
 * @param stationIds	统计站点ID集合
 * @date 2013-8-2 上午10:36:06 
 * @author cw
 */
public interface FittingsReportService {
	//配件出库统计报表
	public List<FittingsOutStockReport> getFittingsOutStockReport(final String dateTime,final String endTime,
			final String analyseType, final List<String> stationIds);
	//配件入库时间段报表
	public List<FittingsPutInReport> getFittingPutInReport(final String beginTime,final String endTime,
			final String analyseType, final List<String> stationIds);

    List<Map<String, Object>> getStockOutReport(List<String> stationIds, String unitName, String beginTime, String endTime);

	List<Map<String, Object>>  getStockInReport(List<String> stationIds, String unitName, String beginTime, String endTime);

	List<Map<String, Object>>  getFixedReport(List<String> stationIds, String unitName, String beginTime, String endTime, String  dateType);

	List<Map<String, Object>>  getProductionValueTrade(List<String> stationIds, String unitName, String beginTime, String endTime, String dateUnit);
}
