package cn.sf_soft.report.model;

import java.io.Serializable;

import cn.sf_soft.report.ichartjs.ColumnAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;

/**
 * 车辆销售月度统计
 * @author king
 * @create 2013-7-12下午2:15:18
 */
public class VehicleMonthlyReport implements ColumnAble,TouchChartAble{

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
	private Integer monthCompare;//环比
	private Integer yearCompare;	//同比
	
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

	public Integer getMonthCompare() {
		return monthCompare;
	}

	public void setMonthCompare(Integer monthCompare) {
		this.monthCompare = monthCompare;
	}

	public Integer getYearCompare() {
		return yearCompare;
	}

	public void setYearCompare(Integer yearCompare) {
		this.yearCompare = yearCompare;
	}
	@Override
	public String toString() {
		return "VehicleMonthlyReport [stationName=" + stationName
				+ ", analyseType=" + analyseType + ", analyseResult="
				+ analyseResult + ", monthCompare=" + monthCompare
				+ ", yearCompare=" + yearCompare + "]";
	}
	
	/**
	 * 将数据转成图表组件的数据格式
	 * @return
	 */
	public String toChartDataString(){
		
		if(analyseType == null || analyseType.length()==0)
			return "{name:'未定义',value:"+analyseResult+",color:'#1f7e92'}";
		else
			return "{name:'"+analyseType+"',value:"+analyseResult+",color:'#1f7e92'}";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
		return 0;
	}
	
}
