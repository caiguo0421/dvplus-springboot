package cn.sf_soft.report.service;

import java.util.List;
import java.util.Map;

import cn.sf_soft.report.model.FinanaceGatheringReport;
import cn.sf_soft.report.model.FinanceReportOfPayMode;

/**
 * 
 * @Title:  收款&付款（账户）报表
 * @date 2013-10-30 下午03:18:52 
 * @author cw
 */
public interface FinanceReportService {
	
	/**
	 * 初始化收款类型数据
	 * @return
	 */
	public Map<String,String> getGatheringInitData();
	
	/**
	 * 获取收款统计数据
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @param financeType 收款方式
	 * @return
	 */
	public List<FinanaceGatheringReport> getGatheringReportData(final String beginTime,final String endTime,
			final List<String> stationIds,final String financeType);
	
	/**
	 * 初始化付款类型数据
	 * @return
	 */
	public Map<String,String> getPayMentInitData();
	/**
	 * 获取付款统计数据
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @param financeType
	 * @return
	 */
	public List<FinanaceGatheringReport> getPayMentReportData(final String beginTime,final String endTime,
			final List<String> stationIds,final String financeType);
	/**
	 * 收款统计（收款方式）
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @param financeType
	 * @return
	 */
	public List<FinanceReportOfPayMode> getGatheringReportForPayModeData(final String beginTime,final String endTime,
			final List<String> stationIds,final String financeType);
	/**
	 * 付款统计（付款方式）
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @param financeType
	 * @return
	 */
	public List<FinanceReportOfPayMode> getPayMentReportForPayModeData(final String beginTime,final String endTime,
			final List<String> stationIds,final String financeType);
}
