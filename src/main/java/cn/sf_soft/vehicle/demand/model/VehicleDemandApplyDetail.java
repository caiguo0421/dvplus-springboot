package cn.sf_soft.vehicle.demand.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 资源需求申报-明细
 * 
 * @author caigx
 *
 */
@Entity
@Table(name = "vehicle_demand_apply_detail")
public class VehicleDemandApplyDetail implements java.io.Serializable {
	private static final long serialVersionUID = 5015120529373824759L;

	// Fields
	private String demandDetailId;
	private String demandId;
	private String orderNo;
	private String accountId;
	private String applyOrderNo;
	private String color;
	private String colorName;
	private String remark;
	private String differenceFlag;
	private String exchangeId;
	private String exchangeArea;
	private String exchangeEnterp;
	private String exchangeFlag;
	private Timestamp expectDate;
	private String isAccountOrder;
	private String opptyId;
	private String parentGroupFlag;
	private String payMethod;
	private Integer quantity;
	private String saleContract;
	private String subStorageFlag;
	private String factoryDealerCode;
	private String factoryDealerName;
	private String subStorageCode;
	private String subStorage;
	private String transportTo;
	private String transportWay;
	private String vehicleVno;
	private String vehicleVnoId;
	private Double purchasePrice;
	private String level;
	private String detailStatus;
	private String detailRemark;
	private String errorCode;
	private String errorMsg;
	private String colorCode;
	private String dfObjId;
	private String workStateAudit;

	// Constructors
	public VehicleDemandApplyDetail() {
	}

	// Property accessors
	@Id
	@Column(name = "demand_detail_id", unique = true, nullable = false, length = 40)
	public String getDemandDetailId() {
		return this.demandDetailId;
	}

	public void setDemandDetailId(String demandDetailId) {
		this.demandDetailId = demandDetailId;
	}

	@Column(name = "demand_id")
	public String getDemandId() {
		return this.demandId;
	}

	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	@Column(name = "order_no")
	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "account_id")
	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Column(name = "apply_order_no")
	public String getApplyOrderNo() {
		return this.applyOrderNo;
	}

	public void setApplyOrderNo(String applyOrderNo) {
		this.applyOrderNo = applyOrderNo;
	}

	@Column(name = "color")
	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name = "color_name")
	public String getColorName() {
		return this.colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "difference_flag")
	public String getDifferenceFlag() {
		return this.differenceFlag;
	}

	public void setDifferenceFlag(String differenceFlag) {
		this.differenceFlag = differenceFlag;
	}

	@Column(name = "exchange_id")
	public String getExchangeId() {
		return this.exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	@Column(name = "exchange_area")
	public String getExchangeArea() {
		return this.exchangeArea;
	}

	public void setExchangeArea(String exchangeArea) {
		this.exchangeArea = exchangeArea;
	}

	@Column(name = "exchange_enterp")
	public String getExchangeEnterp() {
		return this.exchangeEnterp;
	}

	public void setExchangeEnterp(String exchangeEnterp) {
		this.exchangeEnterp = exchangeEnterp;
	}

	@Column(name = "exchange_flag")
	public String getExchangeFlag() {
		return this.exchangeFlag;
	}

	public void setExchangeFlag(String exchangeFlag) {
		this.exchangeFlag = exchangeFlag;
	}

	@Column(name = "expect_date", length = 23)
	public Timestamp getExpectDate() {
		return this.expectDate;
	}

	public void setExpectDate(Timestamp expectDate) {
		this.expectDate = expectDate;
	}

	@Column(name = "is_account_order")
	public String getIsAccountOrder() {
		return this.isAccountOrder;
	}

	public void setIsAccountOrder(String isAccountOrder) {
		this.isAccountOrder = isAccountOrder;
	}

	@Column(name = "oppty_id")
	public String getOpptyId() {
		return this.opptyId;
	}

	public void setOpptyId(String opptyId) {
		this.opptyId = opptyId;
	}

	@Column(name = "parent_group_flag")
	public String getParentGroupFlag() {
		return this.parentGroupFlag;
	}

	public void setParentGroupFlag(String parentGroupFlag) {
		this.parentGroupFlag = parentGroupFlag;
	}

	@Column(name = "pay_method")
	public String getPayMethod() {
		return this.payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	@Column(name = "quantity")
	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "sale_contract")
	public String getSaleContract() {
		return this.saleContract;
	}

	public void setSaleContract(String saleContract) {
		this.saleContract = saleContract;
	}

	@Column(name = "sub_storage_flag")
	public String getSubStorageFlag() {
		return this.subStorageFlag;
	}

	public void setSubStorageFlag(String subStorageFlag) {
		this.subStorageFlag = subStorageFlag;
	}

	@Column(name = "factory_dealer_code")
	public String getFactoryDealerCode() {
		return this.factoryDealerCode;
	}

	public void setFactoryDealerCode(String factoryDealerCode) {
		this.factoryDealerCode = factoryDealerCode;
	}

	@Column(name = "factory_dealer_name")
	public String getFactoryDealerName() {
		return this.factoryDealerName;
	}

	public void setFactoryDealerName(String factoryDealerName) {
		this.factoryDealerName = factoryDealerName;
	}

	@Column(name = "sub_storage_code")
	public String getSubStorageCode() {
		return this.subStorageCode;
	}

	public void setSubStorageCode(String subStorageCode) {
		this.subStorageCode = subStorageCode;
	}

	@Column(name = "sub_storage")
	public String getSubStorage() {
		return this.subStorage;
	}

	public void setSubStorage(String subStorage) {
		this.subStorage = subStorage;
	}

	@Column(name = "transport_to")
	public String getTransportTo() {
		return this.transportTo;
	}

	public void setTransportTo(String transportTo) {
		this.transportTo = transportTo;
	}

	@Column(name = "transport_way")
	public String getTransportWay() {
		return this.transportWay;
	}

	public void setTransportWay(String transportWay) {
		this.transportWay = transportWay;
	}

	@Column(name = "vehicle_vno")
	public String getVehicleVno() {
		return this.vehicleVno;
	}

	public void setVehicleVno(String vehicleVno) {
		this.vehicleVno = vehicleVno;
	}

	@Column(name = "vehicle_vno_id")
	public String getVehicleVnoId() {
		return this.vehicleVnoId;
	}

	public void setVehicleVnoId(String vehicleVnoId) {
		this.vehicleVnoId = vehicleVnoId;
	}

	@Column(name = "purchase_price", precision = 12)
	public Double getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	@Column(name = "level")
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(name = "detail_status")
	public String getDetailStatus() {
		return this.detailStatus;
	}

	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}

	@Column(name = "detail_remark")
	public String getDetailRemark() {
		return this.detailRemark;
	}

	public void setDetailRemark(String detailRemark) {
		this.detailRemark = detailRemark;
	}

	@Column(name = "error_code")
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name = "error_msg")
	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Column(name = "color_code")
	public String getColorCode() {
		return this.colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	@Column(name = "DF_objId")
	public String getDfObjId() {
		return this.dfObjId;
	}

	public void setDfObjId(String dfObjId) {
		this.dfObjId = dfObjId;
	}

	@Column(name = "work_state_audit")
	public String getWorkStateAudit() {
		return this.workStateAudit;
	}

	public void setWorkStateAudit(String workStateAudit) {
		this.workStateAudit = workStateAudit;
	}

}