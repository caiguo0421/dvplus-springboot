package cn.sf_soft.parts.inventory.model;

import java.sql.Timestamp;

/**
 * 带调整的配件信息
 * @author cw
 * @date 2014-4-9 下午2:27:46
 */
public class PartCheckStoksAdjust implements java.io.Serializable  {

	private String stockId;
	private String warehouseId;
	private Double cost;//配件成本
	private Double costNoTax;
	private Double carriage;//运费成本
	private Double quantity;//库存数量
	private Double quantityWay;
	private String depositPosition;//库位
	private Double proxyPrice;
	private String warehouseName;
	private String supplierName;
	private String planNo;//计划单号
	private Double quantityStock;//盘点数量
	private String partId;
	private String partName;
	private String partNo;//配件编码
	private String partMnemonic;//配件自编码
	private Double costRef;//参考成本
	private String partType;//配件类型
	private String partProperty;//配件属性
	private String specModel;//规格型号
	private String producingArea;//配件产地
	private String applicableModel;//适用车型
	private String dosage;//车用量
	private String unit;//计量单位
	private String color;//颜色
	private String material;//材质
	private Timestamp inventoryTime;
	
	private String remark;//盘整备注
	private Double quantityFact;//盘存调整数量
	
	public PartCheckStoksAdjust(){
		
	}
	public PartCheckStoksAdjust(String stockId, String warehouseId,
			Double cost, Double costNoTax, Double carriage, Double quantity,
			Double quantityWay, String depositPosition, Double proxyPrice,
			String warehouseName, String supplierName, String planNo,
			Double quantityStock, String partId, String partName,
			String partNo, String partMnemonic, Double costRef,
			String partType, String partProperty, String specModel,
			String producingArea, String applicableModel, String dosage,
			String unit,String remark,Double quantityFact,String color,String material,Timestamp inventoryTime) {
		super();
		this.stockId = stockId;
		this.warehouseId = warehouseId;
		this.cost = cost;
		this.costNoTax = costNoTax;
		this.carriage = carriage;
		this.quantity = quantity;
		this.quantityWay = quantityWay;
		this.depositPosition = depositPosition;
		this.proxyPrice = proxyPrice;
		this.warehouseName = warehouseName;
		this.supplierName = supplierName;
		this.planNo = planNo;
		this.quantityStock = quantityStock;
		this.partId = partId;
		this.partName = partName;
		this.partNo = partNo;
		this.partMnemonic = partMnemonic;
		this.costRef = costRef;
		this.partType = partType;
		this.partProperty = partProperty;
		this.specModel = specModel;
		this.producingArea = producingArea;
		this.applicableModel = applicableModel;
		this.dosage = dosage;
		this.unit = unit;
		this.color = color;
		this.material = material;
		this.inventoryTime = inventoryTime;
		
		this.remark = remark;
		this.quantityFact = quantityFact;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getCostNoTax() {
		return costNoTax;
	}
	public void setCostNoTax(Double costNoTax) {
		this.costNoTax = costNoTax;
	}
	public Double getCarriage() {
		return carriage;
	}
	public void setCarriage(Double carriage) {
		this.carriage = carriage;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getQuantityWay() {
		return quantityWay;
	}
	public void setQuantityWay(Double quantityWay) {
		this.quantityWay = quantityWay;
	}
	public String getDepositPosition() {
		return depositPosition;
	}
	public void setDepositPosition(String depositPosition) {
		this.depositPosition = depositPosition;
	}
	public Double getProxyPrice() {
		return proxyPrice;
	}
	public void setProxyPrice(Double proxyPrice) {
		this.proxyPrice = proxyPrice;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}
	public Double getQuantityStock() {
		return quantityStock;
	}
	public void setQuantityStock(Double quantityStock) {
		this.quantityStock = quantityStock;
	}
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getPartMnemonic() {
		return partMnemonic;
	}
	public void setPartMnemonic(String partMnemonic) {
		this.partMnemonic = partMnemonic;
	}
	public Double getCostRef() {
		return costRef;
	}
	public void setCostRef(Double costRef) {
		this.costRef = costRef;
	}
	public String getPartType() {
		return partType;
	}
	public void setPartType(String partType) {
		this.partType = partType;
	}
	public String getPartProperty() {
		return partProperty;
	}
	public void setPartProperty(String partProperty) {
		this.partProperty = partProperty;
	}
	public String getSpecModel() {
		return specModel;
	}
	public void setSpecModel(String specModel) {
		this.specModel = specModel;
	}
	public String getProducingArea() {
		return producingArea;
	}
	public void setProducingArea(String producingArea) {
		this.producingArea = producingArea;
	}
	public String getApplicableModel() {
		return applicableModel;
	}
	public void setApplicableModel(String applicableModel) {
		this.applicableModel = applicableModel;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Double getQuantityFact() {
		return quantityFact;
	}
	public void setQuantityFact(Double quantityFact) {
		this.quantityFact = quantityFact;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Timestamp getInventoryTime() {
		return inventoryTime;
	}
	public void setInventoryTime(Timestamp inventoryTime) {
		this.inventoryTime = inventoryTime;
	}
	
	
	
	
	
}
