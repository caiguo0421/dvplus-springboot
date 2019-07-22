package cn.sf_soft.report.model;

import java.io.Serializable;

import cn.sf_soft.report.ichartjs.ColumnAble;
import cn.sf_soft.report.ichartjs.LineAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;
	/**
	 * 
	 * @Title: 车辆维修报表
	 * @date 2013-7-24 上午09:30:21 
	 * @author cw
	 */
public class MaintainReport implements LineAble,ColumnAble,TouchChartAble{
	private static final long serialVersionUID = 1628456940743490962L;
	private String id;
	private String stationName;	//站点名称
	private String analyseType;	//分析类型
	private Integer dayCount;//每日台数
	private Float dayMoney;//每日产值
	private Integer monthCount;//当月累计台数
	private Float monthMoney;//当月累计产值
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
	
	
	
	public Integer getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(Integer monthCount) {
		this.monthCount = monthCount;
	}
	public Float getMonthMoney() {
		return monthMoney;
	}
	public void setMonthMoney(Float monthMoney) {
		this.monthMoney = monthMoney;
	}
	public String toString() {
		return "MaintainDayReport [stationName=" + stationName
				+ ", analyseType=" + analyseType + ", dayCount="
				+ dayCount + ", dayMoney=" + (float)(Math.round(dayMoney*100))/100
				+ ", monthCount=" + monthCount +",monthMoney="+monthMoney+"]";
	}
	
	public String toChartDataString(){
		if(analyseType == null || analyseType.length()==0)
			if(dayCount==null||dayCount.toString().length()==0)
				return "{name:'未定义',value:"+0+",color:'#1f7e92'}";
			else
			return "{name:'未定义',value:"+dayCount+",color:'#1f7e92'}";
		else
			if(dayCount == null || dayCount.toString().length() == 0)
				return "{name:'" + analyseType + "',value:"+0+",color:'#1f7e92'}";
			else
			return "{name:'"+analyseType+"',value:"+dayCount+",color:'#1f7e92'}";
	}
	public static String toChartDataStringline(String money){
			return "{name:'',value:["+money+"],color:'#D52B2B',line_width:2}";
		
	}
	
	
	public static void main(String args[]){
		double i = 51341.123;
		String s = String.valueOf(i);
		s = s.substring(0, s.indexOf("."));
		System.out.println(s.indexOf(".") + ";" + s.length());
		char a = s.charAt(0);
		double b = Math.pow(10, s.length()-1);
		System.out.println(b * (Integer.parseInt(a + "") + 1));
	}
	public String getName() {
		return analyseType == null ? "未定义": analyseType;
	}
	public float getColumnValue() {
		return dayCount == null ? 0:dayCount;
	}
	public float getLineValue(int line) {
		return dayMoney== null ? 0:dayMoney;
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

