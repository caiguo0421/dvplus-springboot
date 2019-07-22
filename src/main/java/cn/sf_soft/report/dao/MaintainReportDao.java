package cn.sf_soft.report.dao;

import java.util.List;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.report.model.MainTainDailyLineReport;
import cn.sf_soft.report.model.MaintainClaimDailyReport;
import cn.sf_soft.report.model.MaintainClaimsReport;
import cn.sf_soft.report.model.MaintainReport;
import cn.sf_soft.report.model.MaintainReportofTime;

public interface MaintainReportDao extends BaseDao{
	/**
	 * 获取车辆维修统计
	 ** @param dateTime 统计时间
	 * @param analyseType 统计类型车型、品系、销售员等
	 * @param stationIds 统计站点
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
	 */
	public List<MainTainDailyLineReport> getMaintainDailyLineReportData(String beginTime, String endTime, List<String> stationIds, int analyseType);
	
	/**
	 * 获取维修索赔日报 报表数据
	 * @param dateTime		日期
	 * @param stationIds	站点范围
	 * @return
	 * @throws Exception
	 */
	public List<MaintainClaimDailyReport> getMaintainClaimDailyReportData(String dateTime, List<String> stationIds);

	public List<MaintainReportofTime> getMaintainforTimeReport(final String beginTime,
			final String endTime,final String analyseType, final List<String> stationIds);
	
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
