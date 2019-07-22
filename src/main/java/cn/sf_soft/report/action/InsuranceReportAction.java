package cn.sf_soft.report.action;

import java.util.List;

import cn.sf_soft.common.action.BaseReportAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.report.ichartjs.IchartJsHelper;
import cn.sf_soft.report.model.InsurancePurchaseReport;
import cn.sf_soft.report.service.InsuranceReportService;

/**
 * 保险统计Action
 * @author king
 * @create 2013-11-8上午10:48:15
 */
public class InsuranceReportAction extends BaseReportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4035744507667214973L;
	private InsuranceReportService reportService;
	
	public void setReportService(InsuranceReportService reportService) {
		this.reportService = reportService;
	}
	

	/**
	 * 按供应商+保单类型
	 * @return
	 */
	@Access(needPopedom=AccessPopedom.ReportForm.INSURANCE_PURCHASE_SUPPLIER_TYPE)
	public String getBySupplierAndInsuranceType(){
		List<InsurancePurchaseReport> list = reportService.getReportBySupplierAndInsuType(beginTime, endTime, stationIds);
		setRespDataSource(list);
		setColumnData(IchartJsHelper.toColumnJson(list));
		setColumnMutipleData(IchartJsHelper.toMutipleColumnJson(list, new String[]{"首保", "续保"}));
		setTouchChartData(IchartJsHelper.toTouchChartJson(list));
		setTitle(beginTime + "至" + endTime + "购险统计");
		return "SUPPLIER_AND_TYPE";
	}
	@Access(needPopedom=AccessPopedom.ReportForm.INSURANCE_PURCHASE_CATEGORY_TYPE)
	public String getReportByCategoryType(){
		List<InsurancePurchaseReport> list = reportService.getReportByInsuCategoryAndType(beginTime, endTime, stationIds);
		setRespDataSource(list);
		setColumnData(IchartJsHelper.toColumnJson(list));
		setColumnMutipleData(IchartJsHelper.toMutipleColumnJson(list, new String[]{"首保", "续保"}));
		setTouchChartData(IchartJsHelper.toTouchChartJson(list));
		setTitle(beginTime + "至" + endTime + "购险统计");
		setAttribute("msg");
		return "SUPPLIER_AND_TYPE";
	}

}
