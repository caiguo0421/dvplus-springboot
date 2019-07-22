package cn.sf_soft.office.approval.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 车辆改装明细
 * 
 * @author caigx
 *
 */
public class VehicleConversionDetail implements java.io.Serializable {

	private static final long serialVersionUID = -1418587103388475909L;
	private String conversionDetailId;
	private String inDetailId;
	private String vehicleVin;
	private Short vehicleKind;
	private String conversionNo;
	private Short conversionType;
	private Short status;
	private String itemId;
	private String itemNo;
	private String itemName;
	private String supplierId;
	private String supplierNo;
	private String supplierName;
	private BigDecimal itemCost;
	private String remark;
	private Short paymentStatus;
	private Boolean varyFlag;
	private String confirmMan;
	private Timestamp confirmTime;
	private BigDecimal itemCostOri;
	private Timestamp confirmPriceDate;
	private String confirmerPrice;
	private String vehicleId;
	private Timestamp futurePayDate;
	private String saleContractItemId;
	private String confirmUnitNo;
	private String confirmUnitName;
	private String confirmManNo;
	private BigDecimal taxMoney;
	private BigDecimal noTaxMoney;
	private BigDecimal tax;
	private BigDecimal taxRate;

	//用于标识此表的sale_contract_item_id字段是否是自动关联赋值的 caigx 20171221
	private Boolean isAutoRelation;
	private Timestamp conversionTime;
	private String conversionId;
	private String groupId;
	private String vocdId;

	public VehicleConversionDetail() {
	}




	public String getConversionDetailId() {
		return this.conversionDetailId;
	}

	public void setConversionDetailId(String conversionDetailId) {
		this.conversionDetailId = conversionDetailId;
	}

	public String getInDetailId() {
		return this.inDetailId;
	}

	public void setInDetailId(String inDetailId) {
		this.inDetailId = inDetailId;
	}

	public String getVehicleVin() {
		return this.vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
	}

	public Short getVehicleKind() {
		return this.vehicleKind;
	}

	public void setVehicleKind(Short vehicleKind) {
		this.vehicleKind = vehicleKind;
	}

	public String getConversionNo() {
		return this.conversionNo;
	}

	public void setConversionNo(String conversionNo) {
		this.conversionNo = conversionNo;
	}

	public Short getConversionType() {
		return this.conversionType;
	}

	public void setConversionType(Short conversionType) {
		this.conversionType = conversionType;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierNo() {
		return this.supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public BigDecimal getItemCost() {
		return this.itemCost;
	}

	public void setItemCost(BigDecimal itemCost) {
		this.itemCost = itemCost;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(Short paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Boolean getVaryFlag() {
		return this.varyFlag;
	}

	public void setVaryFlag(Boolean varyFlag) {
		this.varyFlag = varyFlag;
	}

	public String getConfirmMan() {
		return this.confirmMan;
	}

	public void setConfirmMan(String confirmMan) {
		this.confirmMan = confirmMan;
	}

	public Timestamp getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}

	public BigDecimal getItemCostOri() {
		return this.itemCostOri;
	}

	public void setItemCostOri(BigDecimal itemCostOri) {
		this.itemCostOri = itemCostOri;
	}

	public Timestamp getConfirmPriceDate() {
		return this.confirmPriceDate;
	}

	public void setConfirmPriceDate(Timestamp confirmPriceDate) {
		this.confirmPriceDate = confirmPriceDate;
	}

	public String getConfirmerPrice() {
		return this.confirmerPrice;
	}

	public void setConfirmerPrice(String confirmerPrice) {
		this.confirmerPrice = confirmerPrice;
	}

	public String getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Timestamp getFuturePayDate() {
		return this.futurePayDate;
	}

	public void setFuturePayDate(Timestamp futurePayDate) {
		this.futurePayDate = futurePayDate;
	}

	public String getSaleContractItemId() {
		return this.saleContractItemId;
	}

	public void setSaleContractItemId(String saleContractItemId) {
		this.saleContractItemId = saleContractItemId;
	}

	public String getConfirmUnitNo() {
		return this.confirmUnitNo;
	}

	public void setConfirmUnitNo(String confirmUnitNo) {
		this.confirmUnitNo = confirmUnitNo;
	}

	public String getConfirmUnitName() {
		return this.confirmUnitName;
	}

	public void setConfirmUnitName(String confirmUnitName) {
		this.confirmUnitName = confirmUnitName;
	}

	public String getConfirmManNo() {
		return this.confirmManNo;
	}

	public void setConfirmManNo(String confirmManNo) {
		this.confirmManNo = confirmManNo;
	}

	public BigDecimal getTaxMoney() {
		return this.taxMoney;
	}

	public void setTaxMoney(BigDecimal taxMoney) {
		this.taxMoney = taxMoney;
	}

	public BigDecimal getNoTaxMoney() {
		return this.noTaxMoney;
	}

	public void setNoTaxMoney(BigDecimal noTaxMoney) {
		this.noTaxMoney = noTaxMoney;
	}

	public BigDecimal getTax() {
		return this.tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getTaxRate() {
		return this.taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public Boolean getIsAutoRelation() {
		return isAutoRelation;
	}

	public void setIsAutoRelation(Boolean isAutoRelation) {
		this.isAutoRelation = isAutoRelation;
	}

	public Boolean getAutoRelation() {
		return isAutoRelation;
	}

	public void setAutoRelation(Boolean autoRelation) {
		isAutoRelation = autoRelation;
	}

	public Timestamp getConversionTime() {
		return conversionTime;
	}

	public void setConversionTime(Timestamp conversionTime) {
		this.conversionTime = conversionTime;
	}

	public String getConversionId() {
		return conversionId;
	}

	public void setConversionId(String conversionId) {
		this.conversionId = conversionId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getVocdId() {
		return vocdId;
	}

	public void setVocdId(String vocdId) {
		this.vocdId = vocdId;
	}
}
