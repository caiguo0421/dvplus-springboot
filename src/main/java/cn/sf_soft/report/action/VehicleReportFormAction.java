package cn.sf_soft.report.action;

import cn.sf_soft.common.action.BaseReportAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom.ReportForm;
import cn.sf_soft.report.ichartjs.IchartJsHelper;
import cn.sf_soft.report.model.VehicleMonthlyReport;
import cn.sf_soft.report.model.VehiclePeriodOfTimeReport;
import cn.sf_soft.report.service.VehicleReportService;
import org.apache.struts2.ServletActionContext;

import java.util.List;
import java.util.Map;

/**
 * 车辆业务报表
 * 
 * @author king
 * @create 2013-7-10下午5:43:42
 */
public class VehicleReportFormAction extends BaseReportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4388879572342753041L;

	private String analyseType; // 分析类型
	private String stateType;// 统计方式(订购日期，交车日期)
	private String stateContent;//统计的内容（月度统计中，按数量还是按利润统计）
	
	private Map<String, String> analyseTypeMap;
	
	public void setAnalyseType(String analyseType) {
		this.analyseType = analyseType;
	}

	public Map<String, String> getAnalyseTypeMap() {
		return analyseTypeMap;
	}

	public void setAnalyseTypeMap(Map<String, String> analyseTypeMap) {
		this.analyseTypeMap = analyseTypeMap;
	}

	public void setStateType(String stateType) {
		this.stateType = stateType;
	}

	public void setStateContent(String stateContent) {
		this.stateContent = stateContent;
	}

	private String msg;
	private VehicleReportService vehicleReportService;

	public void setVehicleReportService(
			VehicleReportService vehicleReportService) {
		this.vehicleReportService = vehicleReportService;
	}
	/**
	 * 生成车辆销售统计报表
	 * @return
	 */
	@Access(needPopedom=ReportForm.VEHICLE_SALE)
	public String generateMonthlyReport() {
		if("订购日期".equals(stateType)){
			stateType = "sign_time";
		}else if("交车日期".equals(stateType)){
			stateType = "real_deliver_time";
		}else{
			ServletActionContext.getRequest().setAttribute("msg", "统计方式无效:"+stateType);
			return ERROR;
		}
		
		if (analyseTypeMap.get(analyseType) == null) {
			msg = "统计类型无效";
			ServletActionContext.getRequest().setAttribute("msg", msg);
			return ERROR;
		}
		
	//	try {this
			/*
			 * 判断有没有输入截止日期， 如果没有截止日期就查询月销售额度 如果有截止日期就查询时间段内的销售额度
			 */
			if (endTime == null || endTime.length() == 0) {
				// 根据用户的选择vehicle判断按交付时间查询或按订购时间查询
				
				String stateContentSql = "";
				if("数量".equals(stateContent)){
					stateContentSql = "ISNULL(SUM(vehicle_quantity), 0)";
				}else if("利润".equals(stateContent)){
					stateContentSql = "ISNULL(SUM(total_profit), 0)";
				}else{
					ServletActionContext.getRequest().setAttribute("msg", "统计内容无效:"+stateContent);
					return ERROR;
				}
				
				List<VehicleMonthlyReport> data = vehicleReportService.getMonthlySaleReportData(beginTime,analyseTypeMap.get(analyseType), stationIds,stateType, stateContentSql);
				setRespDataSource(data);
				ServletActionContext.getRequest().setAttribute("analyseType",analyseType);
				ServletActionContext.getRequest().setAttribute("msg", "msg");
				setTitle(beginTime.substring(0, beginTime.length() - 3) + "月销售统计");
				setColumnData(IchartJsHelper.toColumnJson(data)); 
				setTouchChartData(IchartJsHelper.toTouchChartJson(data));
				
				
			} else {
				// 根据用户的选择vehicle判断按交付时间查询或按订购时间查询
				/*String msg = TimeUtil.compareTimes(beginTime, endTime);
				if( msg != null){
					ServletActionContext.getRequest().setAttribute("msg", msg);
					return ERROR;
				}*/
				
				List<VehiclePeriodOfTimeReport> data = vehicleReportService
						.getPeriodOfTimeSaleReportData(beginTime, endTime,
								analyseTypeMap.get(analyseType), stationIds,
								stateType);
				setRespDataSource(data);
				ServletActionContext.getRequest().setAttribute("analyseType",analyseType);
				setTitle(beginTime +  "至" + endTime + "销售统计");
				setColumnData(IchartJsHelper.toColumnJson(data));
				setTouchChartData(IchartJsHelper.toTouchChartJson(data));
			}
		if(stateContent == null || stateContent.length() == 0){
			stateContent = "利润";
		}
		ServletActionContext.getRequest().setAttribute("stateContent", stateContent);
		return "MONTHLY_REPORT";

	}

	@Override
	public String toString() {
		return "VehicleReportFormAction [analyseType=" + analyseType
				+ ", stationIds=" + stationIds + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + "]";
	}

}
