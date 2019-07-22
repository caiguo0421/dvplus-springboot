package cn.sf_soft.office.approval.model;

import java.io.Serializable;

/**
 * 费用报销模块审批中的一些附加信息，如相关的预算和已报销金额
 * @author king
 * @create 2013-3-14下午04:24:05
 */
public class ExpenseReimbursementAdditional implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4642743203069431419L;
	private String unitRelation;//部门关联
	private String unitName; //关联的部门名称
	private String expenseRelation;//费用的关联
	private String expenseName;//关联的费用名称
	private String stationId;//站点ID
	private String stationName;//站点名称
	private double totalReimbursedAmount;//已报总金额
	private Double budgetAmount;//预算值
	private Double alarm;//报警值
	
	private double curApply;//本次申报金额
	public String getUnitRelation() {
		return unitRelation;
	}
	public void setUnitRelation(String unitRelation) {
		this.unitRelation = unitRelation;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getExpenseRelation() {
		return expenseRelation;
	}
	public void setExpenseRelation(String expenseRelation) {
		this.expenseRelation = expenseRelation;
	}
	public String getExpenseName() {
		return expenseName;
	}
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public double getTotalReimbursedAmount() {
		return totalReimbursedAmount;
	}
	public void setTotalReimbursedAmount(double totalReimbursedAmount) {
		this.totalReimbursedAmount = totalReimbursedAmount;
	}
	public Double getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(Double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	public Double getAlarm() {
		return alarm;
	}
	public void setAlarm(Double alarm) {
		this.alarm = alarm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStationName() {
		return stationName;
	}
	public void setCurApply(double curApply) {
		this.curApply = curApply;
	}
	public double getCurApply() {
		return curApply;
	}
	@Override
	public String toString() {
		return "ExpenseReimbursementAdditional [alarm=" + alarm
				+ ", budgetAmount=" + budgetAmount + ", curApply=" + curApply
				+ ", expenseName=" + expenseName + ", expenseRelation="
				+ expenseRelation + ", stationId=" + stationId
				+ ", stationName=" + stationName + ", totalReimbursedAmount="
				+ totalReimbursedAmount + ", unitName=" + unitName
				+ ", unitRelation=" + unitRelation + "]";
	}
	
}
