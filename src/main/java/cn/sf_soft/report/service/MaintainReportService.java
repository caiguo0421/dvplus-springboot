package cn.sf_soft.report.service;

import java.util.List;

import cn.sf_soft.report.model.MainTainDailyLineReport;
import cn.sf_soft.report.model.MaintainClaimDailyReport;
import cn.sf_soft.report.model.MaintainClaimsReport;
import cn.sf_soft.report.model.MaintainReport;
import cn.sf_soft.report.model.MaintainReportofTime;

public interface MaintainReportService {
	/**
	 * 
	 * @Title: 车辆维修报表
	 * @date 2013-7-24 上午09:30:21 
	 * @author cw
	 * @param dateTime		统计的时间
	 * @param analyseType	统计类型(车型、品系、销售员等)
	 * @param stationIds	统计站点ID集合
	 * @return
	 * @throws Exception
	 */
	public List<MaintainReport> getMaintainDayReport(final String dateTime,final String analyseType, final List<String> stationIds);
	
	/**
	 * 获取 日进场台次/日产值曲线 报表数据
	 * @param beginTime		开始日期
	 * @param endTime		结束日期
	 * @param stationIds	站点范围
	 * @param analyseType	统计类型, 1:日进场台次统计; 2:日产值统计
	 * @return
	 * @throws Exception
	 * @author liujin
	 */
	public List<MainTainDailyLineReport> getMaintainDailyLineReport(List<String> stationIds, String beginTime, String endTime, int analyseType);
	
	
	public List<MaintainReportofTime> getMaintainforTimeReport(final String begintime,
			final String endtime,final String analyseType, final List<String> stationIds);
	
	
	/**
	 * 获取维修索赔日报报表数据
	 * @param stationIds 站点范围
	 * @param dateTime	 日期
	 * @return
	 * @throws Exception
	 * @author liujin
	 */
	public List<MaintainClaimDailyReport> getMaintainClaimDailyReport(List<String> stationIds, String dateTime);
	
	/**
	 * 获取维修索赔三包报表数据
	 * @param beginTime  鉴定开始时间
	 * @param endTime  鉴定截止时间
	 * @param stationIds
	 * @return
	 * @throws Exception
	 */
	public List<MaintainClaimsReport> getMaintainClaimsReportData(String beginTime,String endTime,
			List<String> stationIds);
}
