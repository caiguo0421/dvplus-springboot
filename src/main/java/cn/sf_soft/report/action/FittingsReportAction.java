package cn.sf_soft.report.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.sf_soft.common.model.ResponseMessage;
import org.apache.struts2.ServletActionContext;
import cn.sf_soft.common.action.BaseReportAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.report.ichartjs.IchartJsHelper;
import cn.sf_soft.report.model.FittingsOutStockReport;
import cn.sf_soft.report.model.FittingsPutInReport;
import cn.sf_soft.report.service.FittingsReportService;
/**
 * 
 * @Title: 配件统计报表
 * @date 2013-8-2 上午10:39:23 
 * @author cw
 */
public class FittingsReportAction  extends BaseReportAction{
	private static final long serialVersionUID = 7852246438910447220L;
	 private FittingsReportService fittingReportService;
	 private String analyseType; // 分析配件出库统计类型
//	 private String analyseTypePutIn;//分析配件入库统计类型
	 private String queryName;//查询方法名称
	 private Map<String, String> analyseTypeMap;
	 private Map<String,String> analyseTypeMapPutIn;
	 private String unitName;
	 private String dateType;
	 private String dateUnit;

	public void setFittingReportService(FittingsReportService fittingReportService) {
		this.fittingReportService = fittingReportService;
	}

	public String getQueryName() {
		return queryName;
	}


	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}


//	public String getAnalyseType() {
//		return analyseType;
//	}
//
//
//	public void setAnalyseType(String analyseType) {
//		this.analyseType = analyseType;
//	}
	

//	public Map<String, String> getAnalyseTypeMap() {
//		return analyseTypeMap;
//	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setAnalyseTypeMap(Map<String, String> analyseTypeMap) {
		this.analyseTypeMap = analyseTypeMap;
	}
	
//	public Map<String, String> getAnalyseTypeMapPutIn() {
//		return analyseTypeMapPutIn;
//	}

	public void setAnalyseTypeMapPutIn(Map<String, String> analyseTypeMapPutIn) {
		this.analyseTypeMapPutIn = analyseTypeMapPutIn;
		
	}


	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public void setDateUnit(String dateUnit) {
		this.dateUnit = dateUnit;
	}

//	public String getAnalyseTypePutIn() {
//		return analyseTypePutIn;
//	}
//
//	public void setAnalyseTypePutIn(String analyseTypePutIn) {
//		this.analyseTypePutIn = analyseTypePutIn;
//	}
	public void setAnalyseType(String analyseType) {
		this.analyseType = analyseType;
	}

//	public void setAnalyseTypePutIn(String analyseTypePutIn) {
//		this.analyseTypePutIn = analyseTypePutIn;
//	}
	//配件出库统计
	@Access(needPopedom=AccessPopedom.ReportForm.FITTING_OUT_STOCK_REPORT)
	public String getFittingsOutStockReport(){
		/*msg = TimeUtil.compareTimes(beginTime, endTime);
		if( msg != null){
			ServletActionContext.getRequest().setAttribute("msg", msg);
			return ERROR;
		}*/
		/*ServletActionContext.getRequest().setAttribute("queryName",queryName);
		if("频次".equals(queryName)){
			queryName = "fittingsReportDataForFrequency";
		}else if("种类".equals(queryName)){
			queryName = "fittingsReportDataforSort";
		}
		else if("产值".equals(queryName)){
			queryName = "fittingsReportDataforValue";
		}
		else if("利润".equals(queryName)){
			queryName = "fittingsReportDataforGain";
		}else{
			ServletActionContext.getRequest().setAttribute("msg", "统计方式无效:"+queryName);
			return ERROR;
		}*/
		
		if (analyseTypeMap.get(analyseType) == null) {
			ServletActionContext.getRequest().setAttribute("msg", "统计类型无效");
			return ERROR;
		}
		
			List<FittingsOutStockReport> data=fittingReportService.getFittingsOutStockReport(beginTime,endTime, analyseTypeMap.get(analyseType), stationIds);
			setRespDataSource(data);
			ServletActionContext.getRequest().setAttribute("analyseType",analyseType);
			ServletActionContext.getRequest().setAttribute("legend","利润");
			setTitle(beginTime + "至" + endTime + "配件出库");
			setColumnData(IchartJsHelper.toColumnJson(data));
			setTouchChartData(IchartJsHelper.toTouchChartJson(data));
			ServletActionContext.getRequest().setAttribute("secern", "secern");
			
		return "FITTINGS_DAY_REPORT";
	}

	//配件入库时间段统计
	@Access(needPopedom=AccessPopedom.ReportForm.FITTING_IN_STOCK_REPORT)
	public String getFittingPutInReport(){
		/*msg = TimeUtil.compareTimes(beginTime, endTime);
		if( msg != null){
			ServletActionContext.getRequest().setAttribute("msg", msg);
			return ERROR;
		}*/
		/*ServletActionContext.getRequest().setAttribute("queryName",queryName);
		if("频次".equals(queryName)){
			queryName = "fittingsPutInReportForFrequency";
		}else if("种类".equals(queryName)){
			queryName = "fittingsPutInReportDataforSort";
		}
		else if("金额".equals(queryName)){
			queryName = "fittingsPutInReportDataforMoney";
		}else{
			ServletActionContext.getRequest().setAttribute("msg", "统计方式无效:"+queryName);
			return ERROR;
		}*/
		if (analyseTypeMapPutIn.get(analyseType) == null) {
			ServletActionContext.getRequest().setAttribute("msg", "统计类型无效");
			return ERROR;
		}
		List<FittingsPutInReport> list=fittingReportService.getFittingPutInReport(beginTime,endTime, analyseTypeMapPutIn.get(analyseType), stationIds);
		setRespDataSource(list);
		setTitle(beginTime + "至" + endTime + "配件入库");
		ServletActionContext.getRequest().setAttribute("analyseType",analyseType);
		ServletActionContext.getRequest().setAttribute("legend","入库金额");
		setColumnData(IchartJsHelper.toColumnJson(list));
		setTouchChartData(IchartJsHelper.toTouchChartJson(list));
			
		return "FITTINGS_PUTIN_REPORT";
	}

	@Access(needPopedom=AccessPopedom.ReportForm.FITTING_OUT_STOCK_REPORT)
	public String getStockOutReport(){
		setResponseData(fittingReportService.getStockOutReport(stationIds, unitName, beginTime, endTime));
		return SUCCESS;
	}

	@Access(needPopedom=AccessPopedom.ReportForm.FITTING_IN_STOCK_REPORT)
	public String getStockInReport(){
		setResponseData(fittingReportService.getStockInReport(stationIds, unitName, beginTime, endTime));
		return SUCCESS;
	}

	@Access(needPopedom=AccessPopedom.ReportForm.MAINTAIN_REPORT)
	public String getFixedReport(){
		if(!dateType.equals("in") && !dateType.equals("check")){
			ResponseMessage err = new ResponseMessage();
			err.setRet((short)1);
			err.setMsg("未知的统计类型");
			setResponseCommonData(err);
			return SUCCESS;
		}
		setResponseData(fittingReportService.getFixedReport(stationIds, unitName, beginTime, endTime, dateType));
		return SUCCESS;
	}

	@Access(needPopedom=AccessPopedom.ReportForm.MAINTAIN_TREND)
	public String getProductionValueTrade(){
		if(!dateUnit.equals("date") && !dateUnit.equals("month")){
			ResponseMessage err = new ResponseMessage();
			err.setRet((short)1);
			err.setMsg("未知的日期类型");
			setResponseCommonData(err);
			return SUCCESS;
		}
		setResponseData(fittingReportService.getProductionValueTrade(stationIds, unitName, beginTime, endTime, dateUnit));
		return SUCCESS;
	}

}
