package cn.sf_soft.report.model;

import cn.sf_soft.report.ichartjs.ColumnAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;

public class FinanceReportOfPayMode implements ColumnAble,TouchChartAble {

	private String id;
	private String settleStationId;
	private String payMentMode;
	private Double settleAmount;
	private String stationName;
	private String payMentModeMeaning;
	
	
	public FinanceReportOfPayMode(){
		
	}
	public FinanceReportOfPayMode(String id, String settleStationId,
			String payMentMode, Double settleAmount, String stationName,
			String payMentModeMeaning) {
		super();
		this.id = id;
		this.settleStationId = settleStationId;
		this.payMentMode = payMentMode;
		this.settleAmount = settleAmount;
		this.stationName = stationName;
		this.payMentModeMeaning = payMentModeMeaning;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSettleStationId() {
		return settleStationId;
	}
	public void setSettleStationId(String settleStationId) {
		this.settleStationId = settleStationId;
	}
	public String getPayMentMode() {
		return payMentMode;
	}
	public void setPayMentMode(String payMentMode) {
		this.payMentMode = payMentMode;
	}
	public Double getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(Double settleAmount) {
		this.settleAmount = settleAmount;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getPayMentModeMeaning() {
		return payMentModeMeaning;
	}
	public void setPayMentModeMeaning(String payMentModeMeaning) {
		this.payMentModeMeaning = payMentModeMeaning;
	}
	
	public String toChartDataString(){
		return "{name:'"+id+"',value:"+settleAmount/10000+",color:'#1f7e92'}";
}
	public String getName() {
		return id;
	}
	public float getColumnValue() {
		return Float.valueOf(String.valueOf(settleAmount/10000));
	}
	public String getTouchChartName() {
		return id;
	}
	public float getTouchChartValue() {
		return Float.valueOf(String.valueOf(settleAmount/10000));
	}
	public float getTouchChartValue2() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
