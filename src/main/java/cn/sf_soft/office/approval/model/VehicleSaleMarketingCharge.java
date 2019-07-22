package cn.sf_soft.office.approval.model;

/**
 * VehicleSaleMarketingCharge entity. @author MyEclipse Persistence Tools
 */

public class VehicleSaleMarketingCharge implements java.io.Serializable {

	// Fields

	private String selfId;
	private String documentNo;
	private String chargeName;
	private String chargeRemark;
	private Double chargePrice;
	private Double chargeCost;

	// Constructors

	/** default constructor */
	public VehicleSaleMarketingCharge() {
	}

	/** minimal constructor */
	public VehicleSaleMarketingCharge(String selfId, String documentNo,
			String chargeName, Double chargePrice) {
		this.selfId = selfId;
		this.documentNo = documentNo;
		this.chargeName = chargeName;
		this.chargePrice = chargePrice;
	}

	/** full constructor */
	public VehicleSaleMarketingCharge(String selfId, String documentNo,
			String chargeName, String chargeRemark, Double chargePrice) {
		this.selfId = selfId;
		this.documentNo = documentNo;
		this.chargeName = chargeName;
		this.chargeRemark = chargeRemark;
		this.chargePrice = chargePrice;
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

	public String getChargeName() {
		return this.chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public String getChargeRemark() {
		return this.chargeRemark;
	}

	public void setChargeRemark(String chargeRemark) {
		this.chargeRemark = chargeRemark;
	}

	public Double getChargePrice() {
		return this.chargePrice;
	}

	public void setChargePrice(Double chargePrice) {
		this.chargePrice = chargePrice;
	}

	public Double getChargeCost() {
		return chargeCost;
	}

	public void setChargeCost(Double chargeCost) {
		this.chargeCost = chargeCost;
	}
}
