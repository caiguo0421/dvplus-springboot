package cn.sf_soft.report.model;

import java.io.Serializable;

import cn.sf_soft.report.ichartjs.ColumnAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;

/**
 * 车辆销售时间段统计
 * @author king
 * @create 2013-7-12下午2:15:18
 */
public class VehiclePeriodOfTimeReport implements Serializable,ColumnAble,TouchChartAble{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1628456940743490962L;
	/**
	 * 
	 */
	private String id;
	private String stationName;	//站点名称
	private String analyseType;	//分析类型
	private Integer analyseResult;//统计结果
	private Float totalProfit;	//销售利润
	private Float totalProfitPercent;//销售利润率
	
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
	public Integer getAnalyseResult() {
		return analyseResult;
	}
	public void setAnalyseResult(Integer analyseResult) {
		this.analyseResult = analyseResult;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Float getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(Float totalProfit) {
		this.totalProfit = totalProfit;
	}
	public Float getTotalProfitPercent() {
		return totalProfitPercent;
	}
	public void setTotalProfitPercent(Float totalProfitPercent) {
		this.totalProfitPercent = totalProfitPercent;
	}
	
	public String toChartDataString(){
		if(analyseType == null || analyseType.length()==0)
			return "{name:'未定义',value:"+analyseResult+",color:'#1f7e92'}";
		else
			return "{name:'"+analyseType+"',value:"+analyseResult+",color:'#1f7e92'}";
	}
	
	@Override
	public String toString() {
		return "VehiclePeriodOfTimeReport [id=" + id + ", stationName="
				+ stationName + ", analyseType=" + analyseType
				+ ", analyseResult=" + analyseResult + ", totalProfit="
				+ totalProfit + ", totalProfitPercent=" + totalProfitPercent
				+ "]";
	}
	public String getName() {
		return analyseType == null ? "未定义": analyseType;
	}
	public float getColumnValue() {
		return analyseResult;
	}
	public String getTouchChartName() {
		return analyseType == null ? "未定义": analyseType;
	}
	public float getTouchChartValue() {
		return analyseResult;
	}
	public float getTouchChartValue2() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
