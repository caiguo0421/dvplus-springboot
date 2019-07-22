package cn.sf_soft.parts.inventory.model;

import java.sql.Timestamp;

/**
 * 配件入库明细
 * @author cw
 * @date 2014-4-17 上午11:23:26
 */

public class PartInStockDetail implements java.io.Serializable {

	// Fields

	private String pisDetailId;
	private String pisNo;
	private Double pisQuantity;
	private Double pisPrice;
	private Double pisCarriage;
	private Double costRef;
	private Double carriageRef;
	private Double noTaxPrice;
	private Double noTaxCarriage;
	private String stockId;
	private String depositPosition;
	private String partId;
	private String partNo;
	private String partName;
	private String partProperty;
	private String partMnemonic;
	private String producingArea;
	private String partType;
	private String specModel;
	private String applicableModel;
	private String color;
	private String material;
	private String unit;
	private String warehouseId;
	private String warehouseName;
	private String originDetailId;
	private Double quantitySab;
	private Double quantityRecord;
	private Double carriageSab;
	private Double carriageNoTaxSab;
	private String remark;
	private String partNamePro;
	private Double posPrice;
	private String reasonHighPrice;
	private String remarkHighPrice;
	private Short approveResultHighPrice;
	private String approverHighPrice;
	private Timestamp approveTimeHighPrice;
	private String toStockId;
	private String toWarehouseId;
	private String toWarehouseName;
	private Double carriageOut;
	private Double carriage;
	private Double cost;
	private String posNo;
	private String partFullCode;
	private String partFullName;
	private Double partTaxMoney;
	private Double partNoTaxMoney;
	private Double partTax;
	private Double partTaxRate;
	private Double partCostTaxMoney;
	private Double partCostNoTaxMoney;
	private Double partCostTax;
	private Double inTotalCost;
	private Double inTotalCostRecord;

	private Double inCostRecord;
	// Constructors

	/** default constructor */
	public PartInStockDetail() {
	}

	/** minimal constructor */
	public PartInStockDetail(String pisDetailId) {
		this.pisDetailId = pisDetailId;
	}

	/** full constructor */
	public PartInStockDetail(String pisDetailId, String pisNo,
			Double pisQuantity, Double pisPrice, Double pisCarriage,
			Double costRef, Double carriageRef, Double noTaxPrice,
			Double noTaxCarriage, String stockId, String depositPosition,
			String partId, String partNo, String partName, String partProperty,
			String partMnemonic, String producingArea, String partType,
			String specModel, String applicableModel, String color,
			String material, String unit, String warehouseId,
			String warehouseName, String originDetailId, Double quantitySab,
			Double quantityRecord, Double carriageSab, Double carriageNoTaxSab,
			String remark, String partNamePro, Double posPrice,
			String reasonHighPrice, String remarkHighPrice,
			Short approveResultHighPrice, String approverHighPrice,
			Timestamp approveTimeHighPrice, String toStockId,
			String toWarehouseId, String toWarehouseName, Double carriageOut,
			Double carriage, Double cost, String posNo, String partFullCode,
			String partFullName, Double partTaxMoney, Double partNoTaxMoney,
			Double partTax, Double partTaxRate, Double partCostTaxMoney,
			Double partCostNoTaxMoney, Double partCostTax, Double inTotalCost, Double inTotalCostRecord) {
		this.pisDetailId = pisDetailId;
		this.pisNo = pisNo;
		this.pisQuantity = pisQuantity;
		this.pisPrice = pisPrice;
		this.pisCarriage = pisCarriage;
		this.costRef = costRef;
		this.carriageRef = carriageRef;
		this.noTaxPrice = noTaxPrice;
		this.noTaxCarriage = noTaxCarriage;
		this.stockId = stockId;
		this.depositPosition = depositPosition;
		this.partId = partId;
		this.partNo = partNo;
		this.partName = partName;
		this.partProperty = partProperty;
		this.partMnemonic = partMnemonic;
		this.producingArea = producingArea;
		this.partType = partType;
		this.specModel = specModel;
		this.applicableModel = applicableModel;
		this.color = color;
		this.material = material;
		this.unit = unit;
		this.warehouseId = warehouseId;
		this.warehouseName = warehouseName;
		this.originDetailId = originDetailId;
		this.quantitySab = quantitySab;
		this.quantityRecord = quantityRecord;
		this.carriageSab = carriageSab;
		this.carriageNoTaxSab = carriageNoTaxSab;
		this.remark = remark;
		this.partNamePro = partNamePro;
		this.posPrice = posPrice;
		this.reasonHighPrice = reasonHighPrice;
		this.remarkHighPrice = remarkHighPrice;
		this.approveResultHighPrice = approveResultHighPrice;
		this.approverHighPrice = approverHighPrice;
		this.approveTimeHighPrice = approveTimeHighPrice;
		this.toStockId = toStockId;
		this.toWarehouseId = toWarehouseId;
		this.toWarehouseName = toWarehouseName;
		this.carriageOut = carriageOut;
		this.carriage = carriage;
		this.cost = cost;
		this.posNo = posNo;
		this.partFullCode = partFullCode;
		this.partFullName = partFullName;
		this.partTaxMoney = partTaxMoney;
		this.partNoTaxMoney = partNoTaxMoney;
		this.partTax = partTax;
		this.partTaxRate = partTaxRate;
		this.partCostTaxMoney = partCostTaxMoney;
		this.partCostNoTaxMoney = partCostNoTaxMoney;
		this.partCostTax = partCostTax;
		this.inTotalCost = inTotalCost;
		this.inTotalCostRecord = inTotalCostRecord;
	}

	// Property accessors

	public String getPisDetailId() {
		return this.pisDetailId;
	}

	public void setPisDetailId(String pisDetailId) {
		this.pisDetailId = pisDetailId;
	}

	public String getPisNo() {
		return this.pisNo;
	}

	public void setPisNo(String pisNo) {
		this.pisNo = pisNo;
	}

	public Double getPisQuantity() {
		return this.pisQuantity;
	}

	public void setPisQuantity(Double pisQuantity) {
		this.pisQuantity = pisQuantity;
	}

	public Double getPisPrice() {
		return this.pisPrice;
	}

	public void setPisPrice(Double pisPrice) {
		this.pisPrice = pisPrice;
	}

	public Double getPisCarriage() {
		return this.pisCarriage;
	}

	public void setPisCarriage(Double pisCarriage) {
		this.pisCarriage = pisCarriage;
	}

	public Double getCostRef() {
		return this.costRef;
	}

	public void setCostRef(Double costRef) {
		this.costRef = costRef;
	}

	public Double getCarriageRef() {
		return this.carriageRef;
	}

	public void setCarriageRef(Double carriageRef) {
		this.carriageRef = carriageRef;
	}

	public Double getNoTaxPrice() {
		return this.noTaxPrice;
	}

	public void setNoTaxPrice(Double noTaxPrice) {
		this.noTaxPrice = noTaxPrice;
	}

	public Double getNoTaxCarriage() {
		return this.noTaxCarriage;
	}

	public void setNoTaxCarriage(Double noTaxCarriage) {
		this.noTaxCarriage = noTaxCarriage;
	}

	public String getStockId() {
		return this.stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getDepositPosition() {
		return this.depositPosition;
	}

	public void setDepositPosition(String depositPosition) {
		this.depositPosition = depositPosition;
	}

	public String getPartId() {
		return this.partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getPartNo() {
		return this.partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartName() {
		return this.partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartProperty() {
		return this.partProperty;
	}

	public void setPartProperty(String partProperty) {
		this.partProperty = partProperty;
	}

	public String getPartMnemonic() {
		return this.partMnemonic;
	}

	public void setPartMnemonic(String partMnemonic) {
		this.partMnemonic = partMnemonic;
	}

	public String getProducingArea() {
		return this.producingArea;
	}

	public void setProducingArea(String producingArea) {
		this.producingArea = producingArea;
	}

	public String getPartType() {
		return this.partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public String getSpecModel() {
		return this.specModel;
	}

	public void setSpecModel(String specModel) {
		this.specModel = specModel;
	}

	public String getApplicableModel() {
		return this.applicableModel;
	}

	public void setApplicableModel(String applicableModel) {
		this.applicableModel = applicableModel;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMaterial() {
		return this.material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getOriginDetailId() {
		return this.originDetailId;
	}

	public void setOriginDetailId(String originDetailId) {
		this.originDetailId = originDetailId;
	}

	public Double getQuantitySab() {
		return this.quantitySab;
	}

	public void setQuantitySab(Double quantitySab) {
		this.quantitySab = quantitySab;
	}

	public Double getQuantityRecord() {
		return this.quantityRecord;
	}

	public void setQuantityRecord(Double quantityRecord) {
		this.quantityRecord = quantityRecord;
	}

	public Double getCarriageSab() {
		return this.carriageSab;
	}

	public void setCarriageSab(Double carriageSab) {
		this.carriageSab = carriageSab;
	}

	public Double getCarriageNoTaxSab() {
		return this.carriageNoTaxSab;
	}

	public void setCarriageNoTaxSab(Double carriageNoTaxSab) {
		this.carriageNoTaxSab = carriageNoTaxSab;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPartNamePro() {
		return this.partNamePro;
	}

	public void setPartNamePro(String partNamePro) {
		this.partNamePro = partNamePro;
	}

	public Double getPosPrice() {
		return this.posPrice;
	}

	public void setPosPrice(Double posPrice) {
		this.posPrice = posPrice;
	}

	public String getReasonHighPrice() {
		return this.reasonHighPrice;
	}

	public void setReasonHighPrice(String reasonHighPrice) {
		this.reasonHighPrice = reasonHighPrice;
	}

	public String getRemarkHighPrice() {
		return this.remarkHighPrice;
	}

	public void setRemarkHighPrice(String remarkHighPrice) {
		this.remarkHighPrice = remarkHighPrice;
	}

	public Short getApproveResultHighPrice() {
		return this.approveResultHighPrice;
	}

	public void setApproveResultHighPrice(Short approveResultHighPrice) {
		this.approveResultHighPrice = approveResultHighPrice;
	}

	public String getApproverHighPrice() {
		return this.approverHighPrice;
	}

	public void setApproverHighPrice(String approverHighPrice) {
		this.approverHighPrice = approverHighPrice;
	}

	public Timestamp getApproveTimeHighPrice() {
		return this.approveTimeHighPrice;
	}

	public void setApproveTimeHighPrice(Timestamp approveTimeHighPrice) {
		this.approveTimeHighPrice = approveTimeHighPrice;
	}

	public String getToStockId() {
		return this.toStockId;
	}

	public void setToStockId(String toStockId) {
		this.toStockId = toStockId;
	}

	public String getToWarehouseId() {
		return this.toWarehouseId;
	}

	public void setToWarehouseId(String toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
	}

	public String getToWarehouseName() {
		return this.toWarehouseName;
	}

	public void setToWarehouseName(String toWarehouseName) {
		this.toWarehouseName = toWarehouseName;
	}

	public Double getCarriageOut() {
		return this.carriageOut;
	}

	public void setCarriageOut(Double carriageOut) {
		this.carriageOut = carriageOut;
	}

	public Double getCarriage() {
		return this.carriage;
	}

	public void setCarriage(Double carriage) {
		this.carriage = carriage;
	}

	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getPosNo() {
		return this.posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	public String getPartFullCode() {
		return this.partFullCode;
	}

	public void setPartFullCode(String partFullCode) {
		this.partFullCode = partFullCode;
	}

	public String getPartFullName() {
		return this.partFullName;
	}

	public void setPartFullName(String partFullName) {
		this.partFullName = partFullName;
	}

	public Double getPartTaxMoney() {
		return this.partTaxMoney;
	}

	public void setPartTaxMoney(Double partTaxMoney) {
		this.partTaxMoney = partTaxMoney;
	}

	public Double getPartNoTaxMoney() {
		return this.partNoTaxMoney;
	}

	public void setPartNoTaxMoney(Double partNoTaxMoney) {
		this.partNoTaxMoney = partNoTaxMoney;
	}

	public Double getPartTax() {
		return this.partTax;
	}

	public void setPartTax(Double partTax) {
		this.partTax = partTax;
	}

	public Double getPartTaxRate() {
		return this.partTaxRate;
	}

	public void setPartTaxRate(Double partTaxRate) {
		this.partTaxRate = partTaxRate;
	}

	public Double getPartCostTaxMoney() {
		return this.partCostTaxMoney;
	}

	public void setPartCostTaxMoney(Double partCostTaxMoney) {
		this.partCostTaxMoney = partCostTaxMoney;
	}

	public Double getPartCostNoTaxMoney() {
		return this.partCostNoTaxMoney;
	}

	public void setPartCostNoTaxMoney(Double partCostNoTaxMoney) {
		this.partCostNoTaxMoney = partCostNoTaxMoney;
	}

	public Double getPartCostTax() {
		return this.partCostTax;
	}

	public void setPartCostTax(Double partCostTax) {
		this.partCostTax = partCostTax;
	}

	public Double getInTotalCost() {
		return inTotalCost;
	}

	public void setInTotalCost(Double inTotalCost) {
		this.inTotalCost = inTotalCost;
	}

	public Double getInTotalCostRecord() {
		return inTotalCostRecord;
	}

	public void setInTotalCostRecord(Double inTotalCostRecord) {
		this.inTotalCostRecord = inTotalCostRecord;
	}

	public Double getInCostRecord() {
		return inCostRecord;
	}

	public void setInCostRecord(Double inCostRecord) {
		this.inCostRecord = inCostRecord;
	}
}
