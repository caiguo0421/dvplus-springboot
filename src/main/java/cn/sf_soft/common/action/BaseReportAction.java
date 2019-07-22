package cn.sf_soft.common.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 * 报表Action基类，继承自{@link BaseAction}
 * @author king
 * @create 2013-11-12上午10:21:00
 */
public class BaseReportAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4867008506102094840L;
	protected String beginTime;
	protected String endTime;
	protected String format;
	
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	protected void setRespDataSource(List<?> data){
		if("json".equals(format)){
			setResponseData(data);
		}else{
			ServletActionContext.getRequest().setAttribute("data", data);
		}
	}
	
	/**
	 * call {@link HttpServletRequest#setAttribute(String, Object)}, and attribute name is "columnData"
	 * @param columnJsonData
	 */
	protected void setColumnData(String columnJsonData){
		ServletActionContext.getRequest().setAttribute("columnData", columnJsonData);
	}
	/**
	 * @param touchChartData
	 */
	protected void setTouchChartData(String touchChartData){
		ServletActionContext.getRequest().setAttribute("touchChartData", touchChartData);
	}
	/**
	 * call {@link HttpServletRequest#setAttribute(String, Object)}, and attribute name is "columnMutipleData", and "labels"
	 * @param columnMutipleJsonData 长度为2的数组，分别为分别为JSON格式数据源、JSON格式Label
	 */
	protected void setColumnMutipleData(String[] columnMutipleAndLabelJsonData){
		ServletActionContext.getRequest().setAttribute("columnMutipleData", columnMutipleAndLabelJsonData[0]);
		ServletActionContext.getRequest().setAttribute("labels", columnMutipleAndLabelJsonData[1]);
	}
	
	/**
	 * call {@link HttpServletRequest#setAttribute(String, Object)}, and attribute name is "lineData"
	 * @param lineJsonData
	 */
	protected void setLineData(String[] lineJsonData){
		ServletActionContext.getRequest().setAttribute("lineData", lineJsonData[0]);
		ServletActionContext.getRequest().setAttribute("labels", lineJsonData[1]);
	}
	
	/**
	 * call {@link HttpServletRequest#setAttribute(String, Object)}, and attribute name is "title"
	 * @param title
	 */
	protected void setTitle(String title){
		ServletActionContext.getRequest().setAttribute("title", title);
	}
}
