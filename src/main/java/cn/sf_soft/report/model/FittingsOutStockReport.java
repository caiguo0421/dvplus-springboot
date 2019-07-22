package cn.sf_soft.report.model;

import cn.sf_soft.report.ichartjs.ColumnAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;

/**
 * 
 * @Title: 配件日报
 * @date 2013-8-2 上午09:59:32 
 * @author cw
 */
public class FittingsOutStockReport implements ColumnAble,TouchChartAble{
	private String id;
	private String stationName;	//站点名称
	private String analyseType;	//分析类型
	private Double profit;
	private Double cost;
	private Double posPrice;
	private Integer result;
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
	
	
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getPosPrice() {
		return posPrice;
	}
	public void setPosPrice(Double posPrice) {
		this.posPrice = posPrice;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String toString() {
		return "FittingsReport [stationName=" + stationName
				+ ", analyseType=" + analyseType + ", profit="
				+ profit + ", cost=" + cost+",posPrice="+posPrice+"]";
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
		Double result = profit == null ? 0:profit;
		return  Float.valueOf(result.toString());
	}
	public String getTouchChartName() {
		return analyseType == null ? "未定义": stationName+analyseType;
	}
	public float getTouchChartValue() {
		Double result = profit == null ? 0:profit;
		return  Float.valueOf(result.toString());
	}
	public float getTouchChartValue2() {
		return 0;
	}
}
