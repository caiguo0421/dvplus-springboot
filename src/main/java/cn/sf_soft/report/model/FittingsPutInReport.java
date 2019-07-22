package cn.sf_soft.report.model;

import cn.sf_soft.report.ichartjs.ColumnAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;

/**
 * 
 * @Title: 配件入库报表
 * @date 2013-8-5 上午10:46:38 
 * @author cw
 */
public class FittingsPutInReport implements ColumnAble,TouchChartAble{
	private String id;
	private String stationName;	//站点名称
	private String analyseType;	//分析类型
	private Integer analyseCount;//统计台次
	private Double analyseResult;//统计金额
	private Integer result;
	private Integer sort;
	
	
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getAnalyseType() {
		return analyseType;
	}
	public void setAnalyseType(String analyseType) {
		this.analyseType = analyseType;
	}
	
	public Integer getAnalyseCount() {
		return analyseCount;
	}
	public void setAnalyseCount(Integer analyseCount) {
		this.analyseCount = analyseCount;
	}
	public Double getAnalyseResult() {
		return analyseResult;
	}
	public void setAnalyseResult(Double analyseResult) {
		this.analyseResult = analyseResult;
	}
	
	public String toString() {
		return "FittingsPutInReport [stationName=" + stationName
				+ ", analyseType=" + analyseType + ", analyseResult="
				+ analyseResult + ", analyseCount=" + analyseCount+"]";
	}
	/**
	 * 将数据转成图表组件的数据格式
	 * @return
	 */
    public String toChartDataString(){
		
		if(analyseType == null || analyseType.length()==0)
			return "{name:'未定义',value:"+result+",color:'#1f7e92'}";
		else
			return "{name:'"+analyseType+"',value:"+result+",color:'#1f7e92'}";
	}
	public String getName() {
		return analyseType == null ? "未定义": analyseType;
	}
	public float getColumnValue() {
		return Float.valueOf(analyseResult.toString());
	}
	public String getTouchChartName() {
		return analyseType == null ? "未定义": stationName+analyseType;
	}
	public float getTouchChartValue() {
		return Float.valueOf(analyseResult.toString());
	}
	public float getTouchChartValue2() {
		// TODO Auto-generated method stub
		return 0;
	}
}
