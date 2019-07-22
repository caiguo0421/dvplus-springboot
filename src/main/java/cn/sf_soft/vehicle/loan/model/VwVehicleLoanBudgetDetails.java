package cn.sf_soft.vehicle.loan.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 视图-预算明细
 * @author caigx
 *
 */
@Entity
@Table(name = "vw_vehicle_loan_budget_details")
public class VwVehicleLoanBudgetDetails implements java.io.Serializable {
	private static final long serialVersionUID = 4901134633261879110L;

	// Fields
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
	private String vehicleBrand;
	private String vehicleStrain;
	private String vehicleName;
	private String vehicleSalesCode;
	private Double vehicleFirstPay;
	private Double chargeAmount;
	private Double chargeLoanAmount;
	private Double chargeFirstPay;
	private Double loanAmountTot;
	private Double firstPayTot;
	private Short loanType;
	private String loanTypeMeaning;
	private Double invoiceAmount;
	private String statusMeaning;
	private String approverNo;
	private String approver;
	private String affiliatedCompanyNo;
	private String affiliatedCompanyName;
	private Short rateType;

	// Constructors

	/** default constructor */
	public VwVehicleLoanBudgetDetails() {
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

	@Column(name = "document_no")
	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "sale_contract_detail_id")
	public String getSaleContractDetailId() {
		return this.saleContractDetailId;
	}

	public void setSaleContractDetailId(String saleContractDetailId) {
		this.saleContractDetailId = saleContractDetailId;
	}

	@Column(name = "vno_id")
	public String getVnoId() {
		return this.vnoId;
	}

	public void setVnoId(String vnoId) {
		this.vnoId = vnoId;
	}

	@Column(name = "vehicle_vin")
	public String getVehicleVin() {
		return this.vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
	}

	@Column(name = "vehicle_price_total", nullable = false, scale = 4)
	public Double getVehiclePriceTotal() {
		return this.vehiclePriceTotal;
	}

	public void setVehiclePriceTotal(Double vehiclePriceTotal) {
		this.vehiclePriceTotal = vehiclePriceTotal;
	}

	@Column(name = "loan_amount", nullable = false, scale = 4)
	public Double getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "mortgage_date", length = 23)
	public Timestamp getMortgageDate() {
		return this.mortgageDate;
	}

	public void setMortgageDate(Timestamp mortgageDate) {
		this.mortgageDate = mortgageDate;
	}

	@Column(name = "post_date", length = 23)
	public Timestamp getPostDate() {
		return this.postDate;
	}

	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}

	@Column(name = "grant_date", length = 23)
	public Timestamp getGrantDate() {
		return this.grantDate;
	}

	public void setGrantDate(Timestamp grantDate) {
		this.grantDate = grantDate;
	}

	@Column(name = "status", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "approver_id")
	public String getApproverId() {
		return this.approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	@Column(name = "approve_time", length = 23)
	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	@Column(name = "agent_amount")
	public Double getAgentAmount() {
		return this.agentAmount;
	}

	public void setAgentAmount(Double agentAmount) {
		this.agentAmount = agentAmount;
	}

	@Column(name = "superstructure_amount", scale = 4)
	public Double getSuperstructureAmount() {
		return this.superstructureAmount;
	}

	public void setSuperstructureAmount(Double superstructureAmount) {
		this.superstructureAmount = superstructureAmount;
	}

	@Column(name = "interest_rate", precision = 9, scale = 6)
	public Double getInterestRate() {
		return this.interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	@Column(name = "period_number")
	public Integer getPeriodNumber() {
		return this.periodNumber;
	}

	public void setPeriodNumber(Integer periodNumber) {
		this.periodNumber = periodNumber;
	}

	@Column(name = "month_pay", scale = 4)
	public Double getMonthPay() {
		return this.monthPay;
	}

	public void setMonthPay(Double monthPay) {
		this.monthPay = monthPay;
	}

	@Column(name = "loan_ratio", precision = 9)
	public Double getLoanRatio() {
		return this.loanRatio;
	}

	public void setLoanRatio(Double loanRatio) {
		this.loanRatio = loanRatio;
	}

	@Column(name = "payment_ratio", precision = 9)
	public Double getPaymentRatio() {
		return this.paymentRatio;
	}

	public void setPaymentRatio(Double paymentRatio) {
		this.paymentRatio = paymentRatio;
	}

	@Column(name = "affiliated_company_id", length = 40)
	public String getAffiliatedCompanyId() {
		return this.affiliatedCompanyId;
	}

	public void setAffiliatedCompanyId(String affiliatedCompanyId) {
		this.affiliatedCompanyId = affiliatedCompanyId;
	}

	@Column(name = "vehicle_brand")
	public String getVehicleBrand() {
		return this.vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	@Column(name = "vehicle_strain")
	public String getVehicleStrain() {
		return this.vehicleStrain;
	}

	public void setVehicleStrain(String vehicleStrain) {
		this.vehicleStrain = vehicleStrain;
	}

	@Column(name = "vehicle_name")
	public String getVehicleName() {
		return this.vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	@Column(name = "vehicle_sales_code")
	public String getVehicleSalesCode() {
		return this.vehicleSalesCode;
	}

	public void setVehicleSalesCode(String vehicleSalesCode) {
		this.vehicleSalesCode = vehicleSalesCode;
	}

	@Column(name = "vehicle_first_pay", scale = 4)
	public Double getVehicleFirstPay() {
		return this.vehicleFirstPay;
	}

	public void setVehicleFirstPay(Double vehicleFirstPay) {
		this.vehicleFirstPay = vehicleFirstPay;
	}

	@Column(name = "charge_amount", scale = 4)
	public Double getChargeAmount() {
		return this.chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	@Column(name = "charge_loan_amount", scale = 4)
	public Double getChargeLoanAmount() {
		return this.chargeLoanAmount;
	}

	public void setChargeLoanAmount(Double chargeLoanAmount) {
		this.chargeLoanAmount = chargeLoanAmount;
	}

	@Column(name = "charge_first_pay", scale = 4)
	public Double getChargeFirstPay() {
		return this.chargeFirstPay;
	}

	public void setChargeFirstPay(Double chargeFirstPay) {
		this.chargeFirstPay = chargeFirstPay;
	}

	@Column(name = "loan_amount_tot", scale = 4)
	public Double getLoanAmountTot() {
		return this.loanAmountTot;
	}

	public void setLoanAmountTot(Double loanAmountTot) {
		this.loanAmountTot = loanAmountTot;
	}

	@Column(name = "first_pay_tot", scale = 4)
	public Double getFirstPayTot() {
		return this.firstPayTot;
	}

	public void setFirstPayTot(Double firstPayTot) {
		this.firstPayTot = firstPayTot;
	}

	@Column(name = "loan_type")
	public Short getLoanType() {
		return this.loanType;
	}

	public void setLoanType(Short loanType) {
		this.loanType = loanType;
	}

	@Column(name = "loan_type_meaning")
	public String getLoanTypeMeaning() {
		return this.loanTypeMeaning;
	}

	public void setLoanTypeMeaning(String loanTypeMeaning) {
		this.loanTypeMeaning = loanTypeMeaning;
	}

	@Column(name = "invoice_amount")
	public Double getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	@Column(name = "status_meaning")
	public String getStatusMeaning() {
		return this.statusMeaning;
	}

	public void setStatusMeaning(String statusMeaning) {
		this.statusMeaning = statusMeaning;
	}

	@Column(name = "approver_no", length = 10)
	public String getApproverNo() {
		return this.approverNo;
	}

	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}

	@Column(name = "approver")
	public String getApprover() {
		return this.approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	@Column(name = "affiliated_company_no", length = 40)
	public String getAffiliatedCompanyNo() {
		return this.affiliatedCompanyNo;
	}

	public void setAffiliatedCompanyNo(String affiliatedCompanyNo) {
		this.affiliatedCompanyNo = affiliatedCompanyNo;
	}

	@Column(name = "affiliated_company_name")
	public String getAffiliatedCompanyName() {
		return this.affiliatedCompanyName;
	}

	public void setAffiliatedCompanyName(String affiliatedCompanyName) {
		this.affiliatedCompanyName = affiliatedCompanyName;
	}

	@Column(name = "rate_type")
	public Short getRateType() {
		return rateType;
	}

	public void setRateType(Short rateType) {
		this.rateType = rateType;
	}
}