package cn.sf_soft.report.dao;

import java.util.List;

import cn.sf_soft.report.model.FinanaceGatheringReport;
import cn.sf_soft.report.model.FinanceReportOfPayMode;
import cn.sf_soft.report.model.FinanceType;

/**
 * 
 * @Title:  收款&付款（账户）报表
 * @date 2013-10-30 下午03:18:52 
 * @author cw
 */
public interface FinanceReportDao {

	/**
	 * 初始化收款类型数据
	 * @return
	 */
	public List<FinanceType> getGatheringInitData();
	/**
	 * 获取收款统计数据
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @param financeType
	 * @return
	 */
	public List<FinanaceGatheringReport> getGatheringReportData(final String beginTime,final String endTime,
			final List<String> stationIds,final String financeType);
	/**
	 * 初始化付款类型数据
	 * @return
	 */
	public List<FinanceType> getPayMentInitData();
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
