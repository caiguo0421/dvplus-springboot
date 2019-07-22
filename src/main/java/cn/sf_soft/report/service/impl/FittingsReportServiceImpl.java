package cn.sf_soft.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.report.dao.FittingsReportDao;
import cn.sf_soft.report.model.FittingsOutStockReport;
import cn.sf_soft.report.model.FittingsPutInReport;
import cn.sf_soft.report.service.FittingsReportService;
@Service("fittingsReportService")
public class FittingsReportServiceImpl  implements FittingsReportService{
	//配件日报
	@Autowired
	@Qualifier("fittingsReportDao")
	private FittingsReportDao dao;
	
	
	public void setDao(FittingsReportDao dao) {
		this.dao = dao;
	}

	//配件出库统计报表
	public List<FittingsOutStockReport> getFittingsOutStockReport(String dateTime,final String endTime,
			String analyseType, List<String> stationIds){
	
		return dao.getFittingsOutStockReport(dateTime,endTime, analyseType, stationIds);
	}
	//配件入库时间段报表
	public List<FittingsPutInReport> getFittingPutInReport(final String beginTime,final String endTime,
			final String analyseType, final List<String> stationIds){
		return dao.getFittingPutInReport(beginTime, endTime, analyseType, stationIds);
	}

	@Override
	public List<Map<String, Object>> getStockOutReport(List<String> stationIds, String unitName, String beginTime, String endTime) {
		return dao.getStockOutReport(stationIds, unitName, beginTime, endTime);
	}

	@Override
	public List<Map<String, Object>> getStockInReport(List<String> stationIds, String unitName, String beginTime, String endTime) {
		return dao.getStockInReport(stationIds, unitName, beginTime, endTime);
	}

	@Override
	public List<Map<String, Object>> getFixedReport(List<String> stationIds, String unitName, String beginTime, String endTime, String dateType) {
		return dao.getFixedReport(stationIds, unitName, beginTime, endTime, dateType);
	}

	@Override
	public List<Map<String, Object>> getProductionValueTrade(List<String> stationIds, String unitName, String beginTime, String endTime, String dateUnit) {
		return dao.getProductionValueTrade(stationIds, unitName, beginTime, endTime, dateUnit);
	}
}
