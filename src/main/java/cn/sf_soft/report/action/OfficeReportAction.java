package cn.sf_soft.report.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.sf_soft.common.action.BaseReportAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom.ReportForm;
import cn.sf_soft.report.ichartjs.IchartJsHelper;
import cn.sf_soft.report.model.OfficeAssetReport;
import cn.sf_soft.report.service.OfficeReportService;

/**
 * 
 * @Title: 办公统计
 * @date 2013-11-19 下午04:31:22 
 * @author cw
 */
public class OfficeReportAction extends BaseReportAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7746445155029752801L;
	private OfficeReportService service;
	private String Type;//出库aos_type ，入库ais_type
	private String posType;//出入库类型
	private String depositPosition;//存放位置
	
	
	
	public void setPosType(String posType) {
		this.posType = posType;
	}
	
	

	public void setDepositPosition(String depositPosition) {
		this.depositPosition = depositPosition;
	}



	public void setType(String type) {
		Type = type;
	}
	public void setService(OfficeReportService service) {
		this.service = service;
	}
	
	
	
	
	/**
	 * 初始化出入库类型
	 * @return
	 */
	@Access(pass= true)
	public String getTypeInitData(){
		setResponseData(service.getTypeInitData(Type));
		return SUCCESS;
	}
	/**
	 * 初始化存放位置
	 * @return
	 */
	@Access(pass= true)
	public String getPositionInitData(){
		setResponseData(service.getPositionInitData());
		return SUCCESS;
	}
	/**
	 * 初始化用品仓库
	 * @return
	 */
	@Access(pass= true)
	public String getSuppliesPositionInitData(){
		setResponseData(service.getSuppliesPositionInitData(stationIds));
		return SUCCESS;
	}
	/**
	 * 资产出库统计
	 * @return
	 */
	@Access(needPopedom=ReportForm.OFFICE_ASSET_OUT_TPYE)
	public String getAssetOutReoprtData(){
		List<OfficeAssetReport>  list = service.getAssetOutReoprtData(beginTime, endTime, stationIds, posType, depositPosition);
		setRespDataSource(list);
		setColumnData(IchartJsHelper.toColumnJson(list));
		ServletActionContext.getRequest().setAttribute("legendName", "出库数量");
		setTitle(beginTime + "至" + endTime + "资产出库");
		setTouchChartData(IchartJsHelper.toTouchChartJson(list));
		return "OFFICE_REPORT";
	}
	/**
	 * 资产入库统计
	 * @return
	 */
	@Access(needPopedom=ReportForm.OFFICE_ASSET_IN_TPYE)
	public String getAssetInReoprtData(){
		List<OfficeAssetReport>  list = service.getAssetInReoprtData(beginTime, endTime, stationIds, posType, depositPosition);
		setRespDataSource(list);
		setColumnData(IchartJsHelper.toColumnJson(list));
		ServletActionContext.getRequest().setAttribute("msg", "msg");
		ServletActionContext.getRequest().setAttribute("legendName", "入库数量");
		setTitle(beginTime + "至" + endTime + "资产入库");
		setTouchChartData(IchartJsHelper.toTouchChartJson(list));
		return "OFFICE_REPORT";
	}
	
	/**
	 * 用品入库统计
	 * @return
	 */
	@Access(needPopedom=ReportForm.OFFICE_THINGS_IN_TPYE)
	public String getThingsInReoprtData(){
		List<OfficeAssetReport>  list = service.getThingsInReoprtData(beginTime, endTime, stationIds, posType, depositPosition);
		setRespDataSource(list);
		setColumnData(IchartJsHelper.toColumnJson(list));
		ServletActionContext.getRequest().setAttribute("msg", "msg");
		ServletActionContext.getRequest().setAttribute("legendName", "入库数量");
		setTitle(beginTime + "至" + endTime + "用品入库");
		setTouchChartData(IchartJsHelper.toTouchChartJson(list));
		return "OFFICE_REPORT";
	}
	/**
	 * 用品出库统计
	 * @return
	 */
	@Access(needPopedom=ReportForm.OFFICE_THINGS_IN_TPYE)
	public String getThingsOutReoprtData(){
		List<OfficeAssetReport>  list = service.getThingsOutReoprtData(beginTime, endTime, stationIds, posType, depositPosition);
		setRespDataSource(list);
		setColumnData(IchartJsHelper.toColumnJson(list));
		ServletActionContext.getRequest().setAttribute("legendName", "出库数量");
		setTitle(beginTime + "至" + endTime + "用品出库");
		setTouchChartData(IchartJsHelper.toTouchChartJson(list));
		return "OFFICE_REPORT";
	}
}
