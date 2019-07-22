package cn.sf_soft.report.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import cn.sf_soft.common.action.BaseReportAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom.ReportForm;
import cn.sf_soft.report.ichartjs.IchartJsHelper;
import cn.sf_soft.report.model.FinanaceGatheringReport;
import cn.sf_soft.report.model.FinanceReportOfPayMode;
import cn.sf_soft.report.service.FinanceReportService;

/**
 * 
 * @Title: 收款&付款（账户）报表
 * @date 2013-10-30 下午04:33:53 
 * @author cw
 */
public class FinanceReportAction extends BaseReportAction{


	/**
 * 
 */
private static final long serialVersionUID = 6468195845109543265L;
	private FinanceReportService service;
	private String financeType;// 收&付款类型（预收款，预付款等）
	public void setFinanceType(String financeType) {
		this.financeType = financeType;
	}
	public void setService(FinanceReportService service) {
		this.service = service;
	}
	@Access(needPopedom=ReportForm.FINANCE_GATHERING)
	public String getGatheringInitData(){
		Map<String,String> map = service.getGatheringInitData();
		setResponseData(map);
		return SUCCESS;
	}
	/**
	 * 收款统计(账户)
	 * @return
	 */
	@Access(needPopedom=ReportForm.FINANCE_GATHERING)
	public String getGatheringReportData(){
		List<FinanaceGatheringReport> data = service.getGatheringReportData(beginTime, endTime, stationIds, financeType);
		setRespDataSource(data);
		setColumnData(IchartJsHelper.toColumnJson(data));
		setTitle(beginTime + "至" + endTime + "收款统计");
		setTouchChartData(IchartJsHelper.toTouchChartJson(data));
		ServletActionContext.getRequest().setAttribute("secern", "secern");
		return "FINANCE_GATHERING";
	}
	/**
	 * 付款统计初始化数据
	 * @return
	 */
	@Access(needPopedom=ReportForm.FINANCE_PAYMENT)
	public String getPayMentInitData(){
		Map<String,String> map = service.getPayMentInitData();
		setResponseData(map);
		return SUCCESS;
	}
	
	/**
	 * 付款统计(账户)
	 * @return
	 */
	@Access(needPopedom=ReportForm.FINANCE_PAYMENT)
	public String getPayMentReportData(){
		List<FinanaceGatheringReport> data = service.getPayMentReportData(beginTime, endTime, stationIds, financeType);
		setRespDataSource(data);
		setColumnData(IchartJsHelper.toColumnJson(data));
		setTitle(beginTime + "至" + endTime + "付款统计");
		setTouchChartData(IchartJsHelper.toTouchChartJson(data));
		ServletActionContext.getRequest().setAttribute("secern", "secern");
		ServletActionContext.getRequest().setAttribute("pay", "pay");
		return "FINANCE_GATHERING";
	}
	
	
	/**
	 * 收款统计(收款方式)
	 * @return
	 */
	@Access(needPopedom=ReportForm.FINANCE_GATHERING_PAYMODE)
	public String getGatheringReportForPayModeData(){
		List<FinanceReportOfPayMode> data = service.getGatheringReportForPayModeData(beginTime, endTime, stationIds, financeType);
		setRespDataSource(data);
		setColumnData(IchartJsHelper.toColumnJson(data));
		setTouchChartData(IchartJsHelper.toTouchChartJson(data));
		setTitle(beginTime + "至" + endTime + "收款统计");
		return "FINANCE_GATHERING";
	}
	
	
	/**
	 * 付款统计(付款方式)
	 * @return
	 */
	@Access(needPopedom=ReportForm.FINANCE_PAYMENT_PAYMODE)
	public String getPayMentReportForPayModeData(){
		List<FinanceReportOfPayMode> data = service.getPayMentReportForPayModeData(beginTime, endTime, stationIds, financeType);
		setRespDataSource(data);
		setColumnData(IchartJsHelper.toColumnJson(data));
		setTouchChartData(IchartJsHelper.toTouchChartJson(data));
		setTitle(beginTime + "至" + endTime + "付款统计");
		ServletActionContext.getRequest().setAttribute("pay", "pay");
		return "FINANCE_GATHERING";
	}
}
