package cn.sf_soft.vehicle.loan.model;

import java.sql.Timestamp;

import javax.persistence.*;

/**
 * 视图- 预算费用
 * @author caigx
 *
 */
@Entity
@Table(name = "vw_vehicle_loan_budget_charge")
public class VwVehicleLoanBudgetCharge implements java.io.Serializable {
	private static final long serialVersionUID = 5898819688957108073L;
	// Fields
	private String selfId;
	private String budgetDetailId;
	private String chargeId;
	//private Short direction;
	private String objectId;
	//应付日期
	private Timestamp paymentDate;
	private Short moneyType;
	//应收款金额
	private Double income;
	//应付款金额
	private Double expenditure;
	private Double loanAmount;
	private Double cost;
	//private Timestamp arapDate;
	//private Timestamp returnDate;
	//private Double amount;
	private Boolean isReimbursed;
	private String abortReason;
	private Short status;
	private String remark;
	private String creatorId;
	private Timestamp createTime;
	private Timestamp abortTime;
	private String abortPersonId;
	private String objectNo;
	private String objectName;
	//private String directionMeaning;
	private String moneyTypeMeaning;
	private String statusMeaning;
	private String chargeName;
	private Double arCharge;
	private String vehicleVin;
	private String saleContractDetailId;

	//应收对象ID ADM19010008
	private String arObjectId;

	// Constructors

	public VwVehicleLoanBudgetCharge() {
	}

	// Property accessors
	@Id
	@Column(name = "self_id", nullable = false, length = 40)
	public String getSelfId() {
		return this.selfId;
	}

	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}

	@Column(name = "budget_detail_id")
	public String getBudgetDetailId() {
		return this.budgetDetailId;
	}

	public void setBudgetDetailId(String budgetDetailId) {
		this.budgetDetailId = budgetDetailId;
	}

	@Column(name = "charge_id")
	public String getChargeId() {
		return this.chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	@Column(name = "object_id")
	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@Column(name = "money_type", nullable = false)
	public Short getMoneyType() {
		return this.moneyType;
	}

	public void setMoneyType(Short moneyType) {
		this.moneyType = moneyType;
	}


	@Column(name = "loan_amount", scale = 4)
	public Double getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Column(name = "cost", scale = 4)
	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	@Column(name = "is_reimbursed")
	public Boolean getIsReimbursed() {
		return this.isReimbursed;
	}

	public void setIsReimbursed(Boolean isReimbursed) {
		this.isReimbursed = isReimbursed;
	}

	@Column(name = "abort_reason")
	public String getAbortReason() {
		return this.abortReason;
	}

	public void setAbortReason(String abortReason) {
		this.abortReason = abortReason;
	}

	@Column(name = "status", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "creator_id")
	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "create_time", nullable = false, length = 23)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "abort_time", length = 23)
	public Timestamp getAbortTime() {
		return this.abortTime;
	}

	public void setAbortTime(Timestamp abortTime) {
		this.abortTime = abortTime;
	}

	@Column(name = "abort_person_id")
	public String getAbortPersonId() {
		return this.abortPersonId;
	}

	public void setAbortPersonId(String abortPersonId) {
		this.abortPersonId = abortPersonId;
	}

	@Column(name = "object_no", length = 40)
	public String getObjectNo() {
		return this.objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	@Column(name = "object_name")
	public String getObjectName() {
		return this.objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

//	@Column(name = "direction_meaning")
//	public String getDirectionMeaning() {
//		return this.directionMeaning;
//	}
//
//	public void setDirectionMeaning(String directionMeaning) {
//		this.directionMeaning = directionMeaning;
//	}

	@Column(name = "money_type_meaning")
	public String getMoneyTypeMeaning() {
		return this.moneyTypeMeaning;
	}

	public void setMoneyTypeMeaning(String moneyTypeMeaning) {
		this.moneyTypeMeaning = moneyTypeMeaning;
	}

	@Column(name = "status_meaning")
	public String getStatusMeaning() {
		return this.statusMeaning;
	}

	public void setStatusMeaning(String statusMeaning) {
		this.statusMeaning = statusMeaning;
	}

	@Column(name = "charge_name")
	public String getChargeName() {
		return this.chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	@Column(name = "ar_charge", scale = 4)
	public Double getArCharge() {
		return this.arCharge;
	}

	public void setArCharge(Double arCharge) {
		this.arCharge = arCharge;
	}

	@Column(name = "vehicle_vin")
	public String getVehicleVin() {
		return this.vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
	}

	@Column(name = "sale_contract_detail_id")
	public String getSaleContractDetailId() {
		return this.saleContractDetailId;
	}

	public void setSaleContractDetailId(String saleContractDetailId) {
		this.saleContractDetailId = saleContractDetailId;
	}

	@Column(name = "payment_date")
	public Timestamp getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(Double expenditure) {
		this.expenditure = expenditure;
	}

	@Basic
	@Column(name = "ar_object_id")
	public String getArObjectId() {
		return arObjectId;
	}

	public void setArObjectId(String arObjectId) {
		this.arObjectId = arObjectId;
	}
}