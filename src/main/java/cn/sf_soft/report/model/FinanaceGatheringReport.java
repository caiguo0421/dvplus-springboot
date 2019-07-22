package cn.sf_soft.report.model;


import cn.sf_soft.report.ichartjs.ColumnAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;

public class FinanaceGatheringReport implements ColumnAble,TouchChartAble{

	private String id;
	private String settleStationId;
	private String payMentMode;
	private String accountId;
	private String stationName;
	private Double settleAmount;
	private String payMentModeMeaning;
	private String accountNo;
	private String accountName;
	private String bankName;
	
	public FinanaceGatheringReport(){
		
	}
	
	
	
	public FinanaceGatheringReport(String id, String settleStationId,
			String payMentMode, String accountId, String stationName,
			Double settleAmount, String payMentModeMeaning,
			String accountNo, String accountName, String bankName) {
		super();
		this.id = id;
		this.settleStationId = settleStationId;
		this.payMentMode = payMentMode;
		this.accountId = accountId;
		this.stationName = stationName;
		this.settleAmount = settleAmount;
		this.payMentModeMeaning = payMentModeMeaning;
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.bankName = bankName;
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



	public String getAccountId() {
		return accountId;
	}



	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}



	public String getStationName() {
		return stationName;
	}



	public void setStationName(String stationName) {
		this.stationName = stationName;
	}



	public Double getSettleAmount() {
		return settleAmount;
	}



	public void setSettleAmount(Double settleAmount) {
		this.settleAmount = settleAmount;
	}



	public String getPayMentModeMeaning() {
		return payMentModeMeaning;
	}



	public void setPayMentModeMeaning(String payMentModeMeaning) {
		this.payMentModeMeaning = payMentModeMeaning;
	}



	public String getAccountNo() {
		return accountNo;
	}



	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}



	public String getAccountName() {
		return accountName;
	}



	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}



	public String getBankName() {
		return bankName;
	}



	public void setBankName(String bankName) {
		this.bankName = bankName;
	}



	public String toChartDataString(){
			return "{name:'"+accountName+"',value:"+settleAmount/10000+",color:'#1f7e92'}";
	}



	public String getName() {
		return accountName;
	}
	public float getColumnValue() {
		return Float.valueOf(String.valueOf(settleAmount/10000));
	}



	public String getTouchChartName() {
		return (accountName == null ? "未定义": accountName)+payMentModeMeaning;
	}



	public float getTouchChartValue() {
		return Float.valueOf(String.valueOf(settleAmount/10000));
	}



	public float getTouchChartValue2() {
		return 0;
	}
}
