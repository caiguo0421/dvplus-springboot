package cn.sf_soft.report.service;

import java.util.List;

import cn.sf_soft.report.model.VehicleMonthlyReport;
import cn.sf_soft.report.model.VehiclePeriodOfTimeReport;


/*
 *车辆统计报表 
 */
public interface VehicleReportService {
	
	/**
	 * 获取月度车辆销售统计报表数据
	 * @param dateTime
	 * @param analyseType	统计类别，按销售员统计，车型等统计
	 * @param stationIds
	 * @param datetype		统计方式，按订购日期统计还是按交车日期统计
	 * @param stateContent	统计内容，按数量统计还是按利润统计
	 * @return
	 * @throws Exception
	 */
	public List<VehicleMonthlyReport> getMonthlySaleReportData(String dateTime, String analyseType, List<String> stationIds,String datetype, String statContent);
	
	public List<VehiclePeriodOfTimeReport> getPeriodOfTimeSaleReportData(String beginTime, String endTime, String analyseType,List<String> stationIds,String datetype);
	
}
