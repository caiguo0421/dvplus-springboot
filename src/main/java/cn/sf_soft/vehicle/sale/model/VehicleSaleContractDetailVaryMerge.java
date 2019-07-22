package cn.sf_soft.vehicle.sale.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.sf_soft.office.approval.model.VwVehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VwVehicleSaleContractDetailVary;

@Entity
@Table(name = "vw_vehicle_sale_contract_detail_vary_merge")
public class VehicleSaleContractDetailVaryMerge implements Serializable{
	private static final long serialVersionUID = -5287139814361316087L;
	private String detailVaryId;
	private String documentNo;
	private String contractDetailId;
	private String contractNo;
	private String oriVehicleVno;
	private String vehicleVno;
	private Short abortStatus;
	private Boolean notApproved;
	private Boolean notModified;
	private Boolean isModified;
	private Boolean isNew;
	private Boolean isAborted;
	private Double oriVehiclePriceTotal;
	private Double oriVehiclePrice;
	private Double oriVehicleCost;
	// private Double oriVehicleCarriage;
	private Integer oriVehicleQuantity;
	private Double oriInsuranceIncome;
	private Double oriInsurancePf;
	private Double oriInsuranceCost;
	private Double oriPresentIncome;
	private Double oriPresentPf;
	private Double oriPresentCost;
	private Double oriConversionIncome;
	private Double oriConversionPf;
	private Double oriConversionCost;
	private Double oriModifiedFee;
	private Double oriChargeIncome;
	private Double oriChargePf;
	private Double oriChargeCost;
	private Double oriVehicleProfit;
	private Double oriMaintainFee;
	private Double oriDiscountAmount;
	private Double oriTaxationAmount;
	private Double oriProfitReturn;
	private Double oriOtherCost;
	private Double vehiclePriceTotal;
	private Double vehiclePrice;
	private Double vehicleCost;
	//private Double vehicleCarriage;
	private Integer vehicleQuantity;
	private Double insuranceIncome;
	private Double insurancePf;
	private Double insuranceCost;
	private Double presentIncome;
	private Double presentPf;
	private Double presentCost;
	private Double conversionIncome;
	private Double conversionPf;
	private Double conversionCost;
	private Double modifiedFee;
	private Double chargeIncome;
	private Double chargePf;
	private Double chargeCost;
	private Double vehicleProfit;
	private Double maintainFee;
	private Double discountAmount;
	private Double taxationAmount;
	private Double profitReturn;
	private Double otherCost;

	private VwVehicleSaleContractDetail vwVehicleSaleContractDetail;
	private VwVehicleSaleContractDetailVary vwVehicleSaleContractDetailVary;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contract_detail_id",referencedColumnName="contract_detail_id",insertable = false, updatable = false, nullable=true)
	public VwVehicleSaleContractDetail getVwVehicleSaleContractDetail() {
		return this.vwVehicleSaleContractDetail;
	}

	public void setVwVehicleSaleContractDetail(VwVehicleSaleContractDetail vwVehicleSaleContractDetail) {
		this.vwVehicleSaleContractDetail = vwVehicleSaleContractDetail;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "detail_vary_id",referencedColumnName="detail_vary_id",insertable = false, updatable = false, nullable=true)
	public VwVehicleSaleContractDetailVary getVwVehicleSaleContractDetailVary() {
		return this.vwVehicleSaleContractDetailVary;
	}

	public void setVwVehicleSaleContractDetailVary(VwVehicleSaleContractDetailVary vwVehicleSaleContractDetailVary) {
		this.vwVehicleSaleContractDetailVary = vwVehicleSaleContractDetailVary;
	}

	// Constructors
	public VehicleSaleContractDetailVaryMerge() {
	}

	// Property accessors

	@Column(name = "detail_vary_id", length = 40)
	public String getDetailVaryId() {
		return this.detailVaryId;
	}

	public void setDetailVaryId(String detailVaryId) {
		this.detailVaryId = detailVaryId;
	}

	@Column(name = "abort_status")
	public Short getAbortStatus() {
		return this.abortStatus;
	}

	public void setAbortStatus(Short abortStatus) {
		this.abortStatus = abortStatus;
	}

	@Column(name = "not_modified")
	public Boolean getNotModified() {
		return this.notModified;
	}

	public void setNotModified(Boolean notModified) {
		this.notModified = notModified;
	}

	@Column(name = "is_aborted")
	public Boolean getIsAborted() {
		return this.isAborted;
	}

	public void setIsAborted(Boolean isAborted) {
		this.isAborted = isAborted;
	}

	@Column(name = "is_modified")
	public Boolean getIsModified() {
		return this.isModified;
	}

	public void setIsModified(Boolean isModified) {
		this.isModified = isModified;
	}

	@Column(name = "is_new")
	public Boolean getIsNew() {
		return this.isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	@Column(name = "document_no")
	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	@Id
	@Column(name = "contract_detail_id")
	public String getContractDetailId() {
		return this.contractDetailId;
	}

	public void setContractDetailId(String contractDetailId) {
		this.contractDetailId = contractDetailId;
	}

	@Column(name = "contract_no")
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "vehicle_vno")
	public String getVehicleVno() {
		return this.vehicleVno;
	}

	public void setVehicleVno(String vehicleVno) {
		this.vehicleVno = vehicleVno;
	}

	@Column(name = "ori_vehicle_price_total")
	public Double getOriVehiclePriceTotal() {
		return this.oriVehiclePriceTotal;
	}

	public void setOriVehiclePriceTotal(Double oriVehiclePriceTotal) {
		this.oriVehiclePriceTotal = oriVehiclePriceTotal;
	}

	@Column(name = "ori_vehicle_price")
	public Double getOriVehiclePrice() {
		return this.oriVehiclePrice;
	}

	public void setOriVehiclePrice(Double oriVehiclePrice) {
		this.oriVehiclePrice = oriVehiclePrice;
	}

	@Column(name = "ori_vehicle_cost")
	public Double getOriVehicleCost() {
		return this.oriVehicleCost;
	}

	public void setOriVehicleCost(Double oriVehicleCost) {
		this.oriVehicleCost = oriVehicleCost;
	}

	@Column(name = "ori_vehicle_quantity")
	public Integer getOriVehicleQuantity() {
		return this.oriVehicleQuantity;
	}

	public void setOriVehicleQuantity(Integer oriVehicleQuantity) {
		this.oriVehicleQuantity = oriVehicleQuantity;
	}

	@Column(name = "ori_insurance_income")
	public Double getOriInsuranceIncome() {
		return this.oriInsuranceIncome;
	}

	public void setOriInsuranceIncome(Double oriInsuranceIncome) {
		this.oriInsuranceIncome = oriInsuranceIncome;
	}

	@Column(name = "ori_insurance_pf")
	public Double getOriInsurancePf() {
		return this.oriInsurancePf;
	}

	public void setOriInsurancePf(Double oriInsurancePf) {
		this.oriInsurancePf = oriInsurancePf;
	}

	@Column(name = "ori_insurance_cost")
	public Double getOriInsuranceCost() {
		return this.oriInsuranceCost;
	}

	public void setOriInsuranceCost(Double oriInsuranceCost) {
		this.oriInsuranceCost = oriInsuranceCost;
	}

	@Column(name = "ori_present_income")
	public Double getOriPresentIncome() {
		return this.oriPresentIncome;
	}

	public void setOriPresentIncome(Double oriPresentIncome) {
		this.oriPresentIncome = oriPresentIncome;
	}

	@Column(name = "ori_present_pf")
	public Double getOriPresentPf() {
		return this.oriPresentPf;
	}

	public void setOriPresentPf(Double oriPresentPf) {
		this.oriPresentPf = oriPresentPf;
	}

	@Column(name = "ori_present_cost")
	public Double getOriPresentCost() {
		return this.oriPresentCost;
	}

	public void setOriPresentCost(Double oriPresentCost) {
		this.oriPresentCost = oriPresentCost;
	}

	@Column(name = "ori_conversion_income")
	public Double getOriConversionIncome() {
		return this.oriConversionIncome;
	}

	public void setOriConversionIncome(Double oriConversionIncome) {
		this.oriConversionIncome = oriConversionIncome;
	}

	@Column(name = "ori_conversion_pf")
	public Double getOriConversionPf() {
		return this.oriConversionPf;
	}

	public void setOriConversionPf(Double oriConversionPf) {
		this.oriConversionPf = oriConversionPf;
	}

	@Column(name = "ori_conversion_cost")
	public Double getOriConversionCost() {
		return this.oriConversionCost;
	}

	public void setOriConversionCost(Double oriConversionCost) {
		this.oriConversionCost = oriConversionCost;
	}

	@Column(name = "ori_modified_fee")
	public Double getOriModifiedFee() {
		return this.oriModifiedFee;
	}

	public void setOriModifiedFee(Double oriModifiedFee) {
		this.oriModifiedFee = oriModifiedFee;
	}

	@Column(name = "ori_charge_income")
	public Double getOriChargeIncome() {
		return this.oriChargeIncome;
	}

	public void setOriChargeIncome(Double oriChargeIncome) {
		this.oriChargeIncome = oriChargeIncome;
	}

	@Column(name = "ori_charge_pf")
	public Double getOriChargePf() {
		return this.oriChargePf;
	}

	public void setOriChargePf(Double oriChargePf) {
		this.oriChargePf = oriChargePf;
	}

	@Column(name = "ori_charge_cost")
	public Double getOriChargeCost() {
		return this.oriChargeCost;
	}

	public void setOriChargeCost(Double oriChargeCost) {
		this.oriChargeCost = oriChargeCost;
	}

	@Column(name = "ori_vehicle_profit")
	public Double getOriVehicleProfit() {
		return this.oriVehicleProfit;
	}

	public void setOriVehicleProfit(Double oriVehicleProfit) {
		this.oriVehicleProfit = oriVehicleProfit;
	}

	@Column(name = "ori_maintain_fee")
	public Double getOriMaintainFee() {
		return this.oriMaintainFee;
	}

	public void setOriMaintainFee(Double oriMaintainFee) {
		this.oriMaintainFee = oriMaintainFee;
	}

	@Column(name = "ori_discount_amount")
	public Double getOriDiscountAmount() {
		return this.oriDiscountAmount;
	}

	public void setOriDiscountAmount(Double oriDiscountAmount) {
		this.oriDiscountAmount = oriDiscountAmount;
	}

	@Column(name = "ori_taxation_amount")
	public Double getOriTaxationAmount() {
		return this.oriTaxationAmount;
	}

	public void setOriTaxationAmount(Double oriTaxationAmount) {
		this.oriTaxationAmount = oriTaxationAmount;
	}

	@Column(name = "ori_profit_return")
	public Double getOriProfitReturn() {
		return this.oriProfitReturn;
	}

	public void setOriProfitReturn(Double oriProfitReturn) {
		this.oriProfitReturn = oriProfitReturn;
	}

	@Column(name = "ori_other_cost")
	public Double getOriOtherCost() {
		return this.oriOtherCost;
	}

	public void setOriOtherCost(Double oriOtherCost) {
		this.oriOtherCost = oriOtherCost;
	}

	@Column(name="ori_vehicle_vno")
	public String getOriVehicleVno() {
		return this.oriVehicleVno;
	}

	public void setOriVehicleVno(String oriVehicleVno) {
		this.oriVehicleVno = oriVehicleVno;
	}

	@Column(name = "vehicle_price_total")
	public Double getVehiclePriceTotal() {
		return this.vehiclePriceTotal;
	}

	public void setVehiclePriceTotal(Double vehiclePriceTotal) {
		this.vehiclePriceTotal = vehiclePriceTotal;
	}

	@Column(name = "vehicle_price")
	public Double getVehiclePrice() {
		return this.vehiclePrice;
	}

	public void setVehiclePrice(Double vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}

	@Column(name = "vehicle_cost")
	public Double getVehicleCost() {
		return this.vehicleCost;
	}

	public void setVehicleCost(Double vehicleCost) {
		this.vehicleCost = vehicleCost;
	}
	
//	@Column(name = "vehicle_carriage")
//	public Double getVehicleCarriage() {
//		return this.vehicleCarriage;
//	}
//
//	public void setVehicleCarriage(Double vehicleCarriage) {
//		this.vehicleCarriage = vehicleCarriage;
//	}

	@Column(name = "vehicle_quantity")
	public Integer getVehicleQuantity() {
		return this.vehicleQuantity;
	}

	public void setVehicleQuantity(Integer vehicleQuantity) {
		this.vehicleQuantity = vehicleQuantity;
	}

	@Column(name = "insurance_income")
	public Double getInsuranceIncome() {
		return this.insuranceIncome;
	}

	public void setInsuranceIncome(Double insuranceIncome) {
		this.insuranceIncome = insuranceIncome;
	}

	@Column(name = "insurance_pf")
	public Double getInsurancePf() {
		return this.insurancePf;
	}

	public void setInsurancePf(Double insurancePf) {
		this.insurancePf = insurancePf;
	}

	@Column(name = "insurance_cost")
	public Double getInsuranceCost() {
		return this.insuranceCost;
	}

	public void setInsuranceCost(Double insuranceCost) {
		this.insuranceCost = insuranceCost;
	}

	@Column(name = "present_income")
	public Double getPresentIncome() {
		return this.presentIncome;
	}

	public void setPresentIncome(Double presentIncome) {
		this.presentIncome = presentIncome;
	}

	@Column(name = "present_pf")
	public Double getPresentPf() {
		return this.presentPf;
	}

	public void setPresentPf(Double presentPf) {
		this.presentPf = presentPf;
	}

	@Column(name = "present_cost")
	public Double getPresentCost() {
		return this.presentCost;
	}

	public void setPresentCost(Double presentCost) {
		this.presentCost = presentCost;
	}

	@Column(name = "conversion_income")
	public Double getConversionIncome() {
		return this.conversionIncome;
	}

	public void setConversionIncome(Double conversionIncome) {
		this.conversionIncome = conversionIncome;
	}

	@Column(name = "conversion_pf")
	public Double getConversionPf() {
		return this.conversionPf;
	}

	public void setConversionPf(Double conversionPf) {
		this.conversionPf = conversionPf;
	}

	@Column(name = "conversion_cost")
	public Double getConversionCost() {
		return this.conversionCost;
	}

	public void setConversionCost(Double conversionCost) {
		this.conversionCost = conversionCost;
	}

	@Column(name = "modified_fee")
	public Double getModifiedFee() {
		return this.modifiedFee;
	}

	public void setModifiedFee(Double modifiedFee) {
		this.modifiedFee = modifiedFee;
	}

	@Column(name = "charge_income")
	public Double getChargeIncome() {
		return this.chargeIncome;
	}

	public void setChargeIncome(Double chargeIncome) {
		this.chargeIncome = chargeIncome;
	}

	@Column(name = "charge_pf")
	public Double getChargePf() {
		return this.chargePf;
	}

	public void setChargePf(Double chargePf) {
		this.chargePf = chargePf;
	}

	@Column(name = "charge_cost")
	public Double getChargeCost() {
		return this.chargeCost;
	}

	public void setChargeCost(Double chargeCost) {
		this.chargeCost = chargeCost;
	}

	@Column(name = "vehicle_profit")
	public Double getVehicleProfit() {
		return this.vehicleProfit;
	}

	public void setVehicleProfit(Double vehicleProfit) {
		this.vehicleProfit = vehicleProfit;
	}

	@Column(name = "maintain_fee")
	public Double getMaintainFee() {
		return this.maintainFee;
	}

	public void setMaintainFee(Double maintainFee) {
		this.maintainFee = maintainFee;
	}

	@Column(name = "discount_amount")
	public Double getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(name = "taxation_amount")
	public Double getTaxationAmount() {
		return this.taxationAmount;
	}

	public void setTaxationAmount(Double taxationAmount) {
		this.taxationAmount = taxationAmount;
	}

	@Column(name = "profit_return")
	public Double getProfitReturn() {
		return this.profitReturn;
	}

	public void setProfitReturn(Double profitReturn) {
		this.profitReturn = profitReturn;
	}

	@Column(name = "other_cost")
	public Double getOtherCost() {
		return this.otherCost;
	}

	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}

	@Column(name="not_approved")
	public Boolean getNotApproved() {
		return notApproved;
	}

	public void setNotApproved(Boolean notApproved) {
		this.notApproved = notApproved;
	}
	


}
