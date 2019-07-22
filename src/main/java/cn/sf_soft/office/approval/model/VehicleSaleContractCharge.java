package cn.sf_soft.office.approval.model;

import java.math.BigDecimal;

/**
 * 车辆销售合同-费用
 * 
 * @author caigx
 *
 */
public class VehicleSaleContractCharge implements java.io.Serializable {

	private static final long serialVersionUID = -5988029177165992260L;

	private String saleContractChargeId;
	private String contractDetailId;
	private String chargeId;
	private String chargeName;
	private BigDecimal chargePf = BigDecimal.ZERO;
	private BigDecimal chargeCost = BigDecimal.ZERO;
	private Short costStatus;
	private String chargeComment;
	private Short abortStatus;
	private BigDecimal income = BigDecimal.ZERO;
	private String remark;
	private Boolean paidByBill;
	private String contractNo;
	private String chargeGroupId;

	public VehicleSaleContractCharge() {
	}

	public VehicleSaleContractCharge(String saleContractChargeId) {
		this.saleContractChargeId = saleContractChargeId;
	}


	// Property accessors

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

	public BigDecimal getChargePf() {
		return this.chargePf;
	}

	public void setChargePf(BigDecimal chargePf) {
		this.chargePf = chargePf;
	}

	public BigDecimal getChargeCost() {
		return this.chargeCost;
	}

	public void setChargeCost(BigDecimal chargeCost) {
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

	public BigDecimal getIncome() {
		return this.income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getPaidByBill() {
		return paidByBill;
	}

	public void setPaidByBill(Boolean paidByBill) {
		this.paidByBill = paidByBill;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Override
	public String toString() {
		return "VehicleSaleContractCharge [saleContractChargeId=" + saleContractChargeId + ", contractDetailId="
				+ contractDetailId + ", chargeId=" + chargeId + ", chargeName=" + chargeName + ", chargePf=" + chargePf
				+ ", chargeCost=" + chargeCost + ", costStatus=" + costStatus + ", chargeComment=" + chargeComment
				+ ", abortStatus=" + abortStatus + ", income=" + income + ", remark=" + remark + ", paidByBill="
				+ paidByBill + "]";
	}

	public String getChargeGroupId() {
		return chargeGroupId;
	}

	public void setChargeGroupId(String chargeGroupId) {
		this.chargeGroupId = chargeGroupId;
	}
}
