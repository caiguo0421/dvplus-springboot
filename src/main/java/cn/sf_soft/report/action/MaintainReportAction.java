package cn.sf_soft.report.action;

import java.util.List;
import java.util.Map;


import org.apache.struts2.ServletActionContext;

import cn.sf_soft.common.action.BaseReportAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.report.dao.hbb.MaintainReportDaoHibernateImpl;
import cn.sf_soft.report.ichartjs.IchartJsHelper;
import cn.sf_soft.report.model.MainTainDailyLineReport;
import cn.sf_soft.report.model.MaintainClaimDailyReport;
import cn.sf_soft.report.model.MaintainClaimsReport;
import cn.sf_soft.report.model.MaintainReport;
import cn.sf_soft.report.model.MaintainReportofTime;
import cn.sf_soft.report.service.MaintainReportService;

/**
 * 
 * @Title:车辆维修报表
 * @date 2013-7-24 上午09:33:02
 * @author cw
 */
public class MaintainReportAction extends BaseReportAction {

	final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MaintainReportAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 7852246438910447220L;
	private String analyseType; // 分析类型
	private MaintainReportService maintainReportService;
	private Map<String, String> analyseTypeMap;

	public void setAnalyseTypeMap(Map<String, String> analyseTypeMap) {
		this.analyseTypeMap = analyseTypeMap;
	}

	public void setAnalyseType(String analyseType) {
		this.analyseType = analyseType;
	}

	public void setMaintainReportService(MaintainReportService maintainReportService) {
		this.maintainReportService = maintainReportService;
	}

	/**
	 * 
	 * @param 每日维修报表
	 */

	@Access(needPopedom = AccessPopedom.ReportForm.MAINTAIN_REPORT)
	public String getMaintainReport() {
		if (analyseTypeMap.get(analyseType) == null) {
			ServletActionContext.getRequest().setAttribute("msg", "统计类别无效");
			return ERROR;
		}
		// StringBuilder columnData = new StringBuilder("[");//柱状图数据
		// StringBuilder daymoney = new StringBuilder();
		// double maxmoney = 0.0f;//折线图坐标轴的最大值
		// 如果endTime等于空则按输入的当天时间的统计 如果endTime等于空，则按开始时间到截止时间统计
		if (endTime == null || endTime.length() == 0) {
			List<MaintainReport> data = maintainReportService.getMaintainDayReport(beginTime,
					analyseTypeMap.get(analyseType), stationIds);

			setRespDataSource(data);
			ServletActionContext.getRequest().setAttribute("msg", "msg");
			setTitle(beginTime + "维修统计");
			setColumnData(IchartJsHelper.toColumnJson(data));
			setLineData(IchartJsHelper.toLineJson("", data));
			setTouchChartData(IchartJsHelper.toTouchChartJson(data));

		} else {
			// 有截止日期，时间段统计
			/*
			 * String msg = TimeUtil.compareTimes(beginTime, endTime); if( msg
			 * != null){ ServletActionContext.getRequest().setAttribute("msg",
			 * msg); return ERROR; }
			 */
			List<MaintainReportofTime> data = maintainReportService.getMaintainforTimeReport(beginTime, endTime,
					analyseTypeMap.get(analyseType), stationIds);
			setRespDataSource(data);
			setTitle(beginTime + "至" + endTime + "维修统计");
			setColumnData(IchartJsHelper.toColumnJson(data));
			setLineData(IchartJsHelper.toLineJson("", data));
			setTouchChartData(IchartJsHelper.toTouchChartJson(data));
		}
		ServletActionContext.getRequest().setAttribute("analyseType", analyseType);
		return "MaintainReport_Day";
	}

	/**
	 * 获取日产值/进场台次曲线 报表
	 * 
	 * @author liujin
	 * @return
	 */
	@Access(needPopedom = AccessPopedom.ReportForm.MAINTAIN_DAILY_LINE_REPORT)
	public String getDailyLineReport() {
		// int type = 0;
		// if("台次".equals(analyseType)){
		// type = 1;
		// }else if("产值".equals(analyseType)){
		// type = 2;
		// }else{
		// ServletActionContext.getRequest().setAttribute("msg", "统计类别无效");
		// return ERROR;
		// }
		try {
			/*
			 * String msg = TimeUtil.compareTimes(beginTime, endTime); if(msg !=
			 * null){ ServletActionContext.getRequest().setAttribute("msg",
			 * msg); return ERROR; }
			 */
			List<MainTainDailyLineReport> list = maintainReportService.getMaintainDailyLineReport(stationIds,
					beginTime, endTime, 1);
			setRespDataSource(list);
			setLineData(IchartJsHelper.toLineJson(list, new String[] { "台次", "产值" }, new String[] { "#1f7e92", "red" }));
			setTitle(beginTime + "至" + endTime + "日台次/产值曲线");
			ServletActionContext.getRequest().setAttribute("list", list);
			ServletActionContext.getRequest().setAttribute("analyseType", analyseType);
			setTouchChartData(IchartJsHelper.toTouchChartJson(list));

		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getRequest().setAttribute("msg", e.getMessage());
			return ERROR;
		}
		return "MaintainReport_DailyLine";
	}

	/**
	 * 维修索赔日报
	 * 
	 * @author liujin
	 * @return
	 */
	@Access(needPopedom = AccessPopedom.ReportForm.MAINTAIN_CLAIM_DAILY_REPORT)
	public String getClaimDailyReport() {

		List<MaintainClaimDailyReport> list = maintainReportService.getMaintainClaimDailyReport(stationIds, beginTime);
		setRespDataSource(list);
		setTitle(beginTime + "索赔报表(日)");
		setColumnData(IchartJsHelper.toColumnJson(list));
		setLineData(IchartJsHelper.toLineJson("", list));
		setTouchChartData(IchartJsHelper.toTouchChartJson(list));
		return "MaintainClaim_DailyReport";
	}

	/**
	 * 三包索赔类别
	 */
	@Access(needPopedom = AccessPopedom.ReportForm.MAINTAIN_SANBAO_CLAIM_REPORT)
	public String getMaintainClaimsReport() {

		List<MaintainClaimsReport> list = maintainReportService.getMaintainClaimsReportData(beginTime, endTime,
				stationIds);
		setRespDataSource(list);
		setTitle(beginTime + "至" + endTime + "三包索赔类别");
		setColumnData(IchartJsHelper.toColumnJson(list));
		setLineData(IchartJsHelper.toLineJson("", list));
		setTouchChartData(IchartJsHelper.toTouchChartJson(list));
		return "MaintainClaimsReport";
	}

	@Override
	public String toString() {
		return "MaintainReportAction [analyseType=" + analyseType + ", stationIds=" + stationIds + ", beginTime="
				+ beginTime + ", endTime=" + endTime + "]";
	}
}
