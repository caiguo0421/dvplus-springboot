package cn.sf_soft.report.model;

import java.io.Serializable;

import cn.sf_soft.report.ichartjs.ColumnAble;
import cn.sf_soft.report.ichartjs.LineAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;

public class MaintainReportofTime  implements Serializable,LineAble,ColumnAble,TouchChartAble{
	private static final long serialVersionUID = 1628456940743490962L;
	private String id;
	private String stationName;	//站点名称
	private String analyseType;	//分析类型
	private Integer dayCount;//台数
	private Float dayMoney;//产值
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
	
	public Integer getDayCount() {
		return dayCount;
	}
	public void setDayCount(Integer dayCount) {
		this.dayCount = dayCount;
	}
	public Float getDayMoney() {
		return dayMoney;
	}
	public void setDayMoney(Float dayMoney) {
		this.dayMoney = dayMoney;
	}
	public String toString() {
		return "MaintainDayReport [stationName=" + stationName
				+ ", analyseType=" + analyseType + ", timeofCount="
				+ dayCount + ", timeofMoney=" + (float)(Math.round(dayMoney*100))/100+"]";
	}
	
	public String toChartDataString(){
		if(analyseType == null || analyseType.length()==0)
			if(dayCount==null||dayCount.toString().length()==0)
				return "{name:'未定义',value:"+0+",color:'#1f7e92'}";
			else
			return "{name:'未定义',value:"+dayCount+",color:'#1f7e92'}";
		else
			if(dayCount==null||dayCount.toString().length()==0)
				return "{name:'"+analyseType+"',value:"+0+",color:'#1f7e92'}";
			else
			return "{name:'"+analyseType+"',value:"+dayCount+",color:'#1f7e92'}";
	}
	public static String toChartDataStringline(String money){
			return "{name:'',value:["+money+"],color:'#D52B2B'}";
		
	}
	public String getName() {
		return analyseType == null ? "未定义": analyseType;
	}
	public float getColumnValue() {
		return dayCount == null ? 0:dayCount;
	}
	public float getLineValue(int line) {
		return dayMoney == null ? 0:dayMoney;
	}
	public String getLabel() {
		return analyseType == null ? "未定义": analyseType;
	}
	/**
	 * Touch Chart
	 */
	public String getTouchChartName() {
		return analyseType == null ? "未定义": analyseType;
	}
	public float getTouchChartValue() {
		return dayCount == null ? 0:dayCount;
	}
	public float getTouchChartValue2() {
		return dayMoney== null ? 0:dayMoney;
	}
}
