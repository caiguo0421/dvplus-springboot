package cn.sf_soft.report.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.sf_soft.common.action.BaseReportAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom.ReportForm;
import cn.sf_soft.report.ichartjs.IchartJsHelper;
import cn.sf_soft.report.model.SaleCallBackReport;
import cn.sf_soft.report.model.VehicleMonthlyReport;
import cn.sf_soft.report.service.CustomerConnectionService;

/**
 * 
 * @Title: 客户关系报表
 * @date 2013-11-9 上午10:17:56 
 * @author cw
 */
public class CustomerConnectionAction extends BaseReportAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomerConnectionService service;
	public void setService(CustomerConnectionService service) {
		this.service = service;
	}
	
	@Access(needPopedom=ReportForm.SALE_CALL_BACK)
	public String getSaleCallBackData(){
		List<SaleCallBackReport> data = service.getSaleCallBackData(beginTime, endTime, stationIds);
		setRespDataSource(data);
		setColumnMutipleData(IchartJsHelper.toMutipleColumnJson(data, new String[]{"已回访", "投诉数量"}, new String[]{"#1f7e92", "#73B9E4"}));
		setTitle(beginTime + "至" + endTime + "销售回访");
		return "SALE_CALL_BACK";
	}
	@Access(needPopedom=ReportForm.SERVICE_CALL_BACK)
	public String  getServiceCallBackData(){
		List<SaleCallBackReport> data = service.getServiceCallBackData(beginTime, endTime, stationIds);
		setRespDataSource(data);
		setColumnMutipleData(IchartJsHelper.toMutipleColumnJson(data, new String[]{"已回访", "投诉数量"}, new String[]{"#1f7e92", "#73B9E4"}));
		setTitle(beginTime + "至" + endTime + "维修回访");
		return "SALE_CALL_BACK";
	}
}
