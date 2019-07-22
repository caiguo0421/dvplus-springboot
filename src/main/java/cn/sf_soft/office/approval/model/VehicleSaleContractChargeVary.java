package cn.sf_soft.office.approval.model;

/**
 * 费用变更表
 * @author caigx
 *
 */
public class VehicleSaleContractChargeVary implements java.io.Serializable {

	private static final long serialVersionUID = -4445174711531705914L;
	private String vehicleSaleContractChargeVaryId;
	private String detailVaryId;
	private String saleContractChargeId;
	private String contractDetailId;
	private String chargeId;
	private String chargeName;
	private Double chargePf;
	private Double chargeCost;
	private Short costStatus;
	private String chargeComment;
	private Short abortStatus;
	private String abortComment;
	private Double income;
	private Boolean paidByBill;
	private String remark;
	private String contractNo;
	private Double oriChargePf;
	private Double oriChargeCost;
	private Double oriIncome;
	private Boolean oriPaidByBill;

	// Constructors

	public VehicleSaleContractChargeVary() {
	}

	public String getVehicleSaleContractChargeVaryId() {
		return this.vehicleSaleContractChargeVaryId;
	}

	public void setVehicleSaleContractChargeVaryId(
			String vehicleSaleContractChargeVaryId) {
		this.vehicleSaleContractChargeVaryId = vehicleSaleContractChargeVaryId;
	}

	public String getDetailVaryId() {
		return this.detailVaryId;
	}

	public void setDetailVaryId(String detailVaryId) {
		this.detailVaryId = detailVaryId;
	}

	public String getSaleContractChargeId() {
		return this.saleContractChargeId;
	}

	public void setSaleContractChargeId(String saleContractChargeId) {
		this.saleContractChargeId = saleContractChargeId;
	}

	public String getContractDetailId() {
		return this.contractDetailId;
	}

	public void setContractDetailId(String contractDetailId) {
		this.contractDetailId = contractDetailId;
	}

	public String getChargeId() {
		return this.chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public String getChargeName() {
		return this.chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public Double getChargePf() {
		return this.chargePf;
	}

	public void setChargePf(Double chargePf) {
		this.chargePf = chargePf;
	}

	public Double getChargeCost() {
		return this.chargeCost;
	}

	public void setChargeCost(Double chargeCost) {
		this.chargeCost = chargeCost;
	}

	public Short getCostStatus() {
		return this.costStatus;
	}

	public void setCostStatus(Short costStatus) {
		this.costStatus = costStatus;
	}

	public String getChargeComment() {
		return this.chargeComment;
	}

	public void setChargeComment(String chargeComment) {
		this.chargeComment = chargeComment;
	}

	public Short getAbortStatus() {
		return this.abortStatus;
	}

	public void setAbortStatus(Short abortStatus) {
		this.abortStatus = abortStatus;
	}

	public String getAbortComment() {
		return this.abortComment;
	}

	public void setAbortComment(String abortComment) {
		this.abortComment = abortComment;
	}

	public Double getIncome() {
		return this.income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Boolean getPaidByBill() {
		return this.paidByBill;
	}

	public void setPaidByBill(Boolean paidByBill) {
		this.paidByBill = paidByBill;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Double getOriChargePf() {
		return this.oriChargePf;
	}

	public void setOriChargePf(Double oriChargePf) {
		this.oriChargePf = oriChargePf;
	}

	public Double getOriChargeCost() {
		return this.oriChargeCost;
	}

	public void setOriChargeCost(Double oriChargeCost) {
		this.oriChargeCost = oriChargeCost;
	}

	public Double getOriIncome() {
		return this.oriIncome;
	}

	public void setOriIncome(Double oriIncome) {
		this.oriIncome = oriIncome;
	}

	public Boolean getOriPaidByBill() {
		return this.oriPaidByBill;
	}

	public void setOriPaidByBill(Boolean oriPaidByBill) {
		this.oriPaidByBill = oriPaidByBill;
	}

}
