package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Set;

/**
 * 日常费用报销明细
 * @author  caigx
 */

public class ExpenseReimbursementsDetails implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 2294130691617975246L;
	private String oerdId;
	private String documentNo;
	private String expenseId;
	private String expenseNo;
	private String expenseName;
	private Double expenseAmount;
	private Timestamp beginTime;
	private Timestamp endTime;
	private String summary;
	private String route;
	private Integer mileage;
	private Double quantity;
	private String remark;

	private String expenseRelation;// 费用关联
	private String expenseFullName; //费用全称
	
	//2013-12-13 修改为每个费用明细对应多个分摊部门  by liujin
	private Set<ExpenseReimbursementsApportionments> apportionment;

	// Constructors

	public ExpenseReimbursementsDetails() {
	}


	// Property accessors

	public String getOerdId() {
		return this.oerdId;
	}

	public void setOerdId(String oerdId) {
		this.oerdId = oerdId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getExpenseId() {
		return this.expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getExpenseNo() {
		return this.expenseNo;
	}

	public void setExpenseNo(String expenseNo) {
		this.expenseNo = expenseNo;
	}

	public String getExpenseName() {
		return this.expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public Double getExpenseAmount() {
		return this.expenseAmount;
	}

	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public Timestamp getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRoute() {
		return this.route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Integer getMileage() {
		return this.mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExpenseRelation() {
		return expenseRelation;
	}

	public void setExpenseRelation(String expenseRelation) {
		this.expenseRelation = expenseRelation;
	}

	public void setApportionment(
			Set<ExpenseReimbursementsApportionments> apportionment) {
		this.apportionment = apportionment;
	}

	public Set<ExpenseReimbursementsApportionments> getApportionment() {
		return apportionment;
	}

	public String getExpenseFullName() {
		return expenseFullName;
	}

	public void setExpenseFullName(String expenseFullName) {
		this.expenseFullName = expenseFullName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(apportionment != null){
			Iterator<ExpenseReimbursementsApportionments> it = apportionment.iterator();
			while(it.hasNext()){
				sb.append("\n\t\t" + it.next().toString());
			}
		}
		return "ExpenseReimbursementsDetails [oerdId=" + oerdId
				+ ", documentNo=" + documentNo + ", expenseId=" + expenseId
				+ ", expenseNo=" + expenseNo + ", expenseName=" + expenseName
				+ ", expenseAmount=" + expenseAmount + ", beginTime="
				+ beginTime + ", endTime=" + endTime + ", summary=" + summary
				+ ", route=" + route + ", mileage=" + mileage + ", quantity="
				+ quantity + ", remark=" + remark + ", expenseRelation="
				+ expenseRelation + "]" + sb.toString() + "\n\t======";
	}

}
