package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 消贷费用预算-明细
 * 
 * @author caigx
 *
 */
public class VehicleLoanBudgetDetails implements java.io.Serializable {
	private static final long serialVersionUID = 434599904024463821L;

	// field
	private String selfId;
	private String documentNo;
	private String saleContractDetailId;
	private String vnoId;
	private String vehicleVin;
	private Double vehiclePriceTotal;
	private Double loanAmount;
	private String remark;
	private Timestamp mortgageDate;
	private Timestamp postDate;
	private Timestamp grantDate;
	private Short status;
	private String approverId;
	private Timestamp approveTime;
	private Double agentAmount;
	private Double superstructureAmount;
	private Double interestRate;
	private Integer periodNumber;
	private Double monthPay;
	private Double loanRatio;
	private Double paymentRatio;
	private String affiliatedCompanyId;
	
	//ADM17060020
	private String bulletinId;
	
	private String bulletinNo;
	private Double interest;
	private Short rateType;

	// Constructors

	public VehicleLoanBudgetDetails() {
	}

	// Property accessors

	public String getSelfId() {
		return this.selfId;
	}

	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getSaleContractDetailId() {
		return this.saleContractDetailId;
	}

	public void setSaleContractDetailId(String saleContractDetailId) {
		this.saleContractDetailId = saleContractDetailId;
	}

	public String getVnoId() {
		return this.vnoId;
	}

	public void setVnoId(String vnoId) {
		this.vnoId = vnoId;
	}

	public String getVehicleVin() {
		return this.vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
	}

	public Double getVehiclePriceTotal() {
		return this.vehiclePriceTotal;
	}

	public void setVehiclePriceTotal(Double vehiclePriceTotal) {
		this.vehiclePriceTotal = vehiclePriceTotal;
	}

	public Double getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getMortgageDate() {
		return this.mortgageDate;
	}

	public void setMortgageDate(Timestamp mortgageDate) {
		this.mortgageDate = mortgageDate;
	}

	public Timestamp getPostDate() {
		return this.postDate;
	}

	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}

	public Timestamp getGrantDate() {
		return this.grantDate;
	}

	public void setGrantDate(Timestamp grantDate) {
		this.grantDate = grantDate;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getApproverId() {
		return this.approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public Double getAgentAmount() {
		return this.agentAmount;
	}

	public void setAgentAmount(Double agentAmount) {
		this.agentAmount = agentAmount;
	}

	public Double getSuperstructureAmount() {
		return this.superstructureAmount;
	}

	public void setSuperstructureAmount(Double superstructureAmount) {
		this.superstructureAmount = superstructureAmount;
	}

	public Double getInterestRate() {
		return this.interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Integer getPeriodNumber() {
		return this.periodNumber;
	}

	public void setPeriodNumber(Integer periodNumber) {
		this.periodNumber = periodNumber;
	}

	public Double getMonthPay() {
		return this.monthPay;
	}

	public void setMonthPay(Double monthPay) {
		this.monthPay = monthPay;
	}

	public Double getLoanRatio() {
		return this.loanRatio;
	}

	public void setLoanRatio(Double loanRatio) {
		this.loanRatio = loanRatio;
	}

	public Double getPaymentRatio() {
		return this.paymentRatio;
	}

	public void setPaymentRatio(Double paymentRatio) {
		this.paymentRatio = paymentRatio;
	}

	public String getAffiliatedCompanyId() {
		return this.affiliatedCompanyId;
	}

	public void setAffiliatedCompanyId(String affiliatedCompanyId) {
		this.affiliatedCompanyId = affiliatedCompanyId;
	}

	public String getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(String bulletinId) {
		this.bulletinId = bulletinId;
	}

	public String getBulletinNo() {
		return bulletinNo;
	}

	public void setBulletinNo(String bulletinNo) {
		this.bulletinNo = bulletinNo;
	}

	public Short getRateType() {
		return rateType;
	}

	public void setRateType(Short rateType) {
		this.rateType = rateType;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}
}