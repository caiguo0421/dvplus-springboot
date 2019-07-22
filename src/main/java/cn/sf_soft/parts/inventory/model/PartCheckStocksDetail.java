package cn.sf_soft.parts.inventory.model;

import java.sql.Timestamp;

/**
 * 配件库存盘整明细
 * @author cw
 * @date 2014-3-31 上午10:51:28
 */

public class PartCheckStocksDetail implements java.io.Serializable {

	// Fields

	private String pcsDetailId;
	private String documentNo;
	private Double cost;
	private Double carriage;
	private Double quantityStock;
	private Double quantityFact;
	private String depositPosition;
	private String stockId;
	private String partId;
	private String partNo;
	private String partName;
	private String partMnemonic;
	private String producingArea;
	private String partType;
	private String specModel;
	private String applicableModel;
	private String color;
	private String material;
	private String unit;
	private String warehouseName;
	private String remark;
	private String planNo;
	private String warehouseId;

	private Double quantityStockNow; //扩展字段

	private Double checkTotalCost;
	private Double checkTotalCostRecord;

	/**
	 * 0/null:未盘点
	 * 1:已盘点
	 */
	private Short checkStatus = 0;
	/**
	 * 盘点时间
	 */
	private Timestamp checkTime;
	/**
	 * 盘点人编码
	 */
	private String checkUserNo;
	/**
	 * 盘点人
	 */
	private String checkUserName;
	// Constructors

	public PartCheckStocksDetail(String pcsDetailId,Double quantityFact,Double quantityStock) {
		this.pcsDetailId = pcsDetailId;
		this.quantityFact = quantityFact;
		this.quantityStock = quantityStock;
	}
	/** default constructor */
	public PartCheckStocksDetail() {
	}

	/** minimal constructor */
	public PartCheckStocksDetail(String pcsDetailId) {
		this.pcsDetailId = pcsDetailId;
	}

	/** full constructor */
	public PartCheckStocksDetail(String pcsDetailId, String documentNo, Double cost,
			Double carriage, Double quantityStock, Double quantityFact,
			String depositPosition, String stockId, String partId,
			String partNo, String partName, String partMnemonic,
			String producingArea, String partType, String specModel,
			String applicableModel, String color, String material, String unit,
			String warehouseName, String remark, String planNo,
			String warehouseId) {
		this.pcsDetailId = pcsDetailId;
		this.documentNo = documentNo;
		this.cost = cost;
		this.carriage = carriage;
		this.quantityStock = quantityStock;
		this.quantityFact = quantityFact;
		this.depositPosition = depositPosition;
		this.stockId = stockId;
		this.partId = partId;
		this.partNo = partNo;
		this.partName = partName;
		this.partMnemonic = partMnemonic;
		this.producingArea = producingArea;
		this.partType = partType;
		this.specModel = specModel;
		this.applicableModel = applicableModel;
		this.color = color;
		this.material = material;
		this.unit = unit;
		this.warehouseName = warehouseName;
		this.remark = remark;
		this.planNo = planNo;
		this.warehouseId = warehouseId;
	}

	// Property accessors

	public String getPcsDetailId() {
		return this.pcsDetailId;
	}

	public void setPcsDetailId(String pcsDetailId) {
		this.pcsDetailId = pcsDetailId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public Double getCost() {
		if(this.cost==null)
		{
			this.cost=0.0;
		}
		return this.cost;
	}

	public void setCost(Double cost) {
		
		this.cost = cost;
		if(this.cost==null)
		{
			this.cost=0.0;
		}
	}

	public Double getCarriage() {
		return this.carriage;
	}

	public void setCarriage(Double carriage) {
		this.carriage = carriage;
	}

	public Double getQuantityStock() {
		if(this.quantityStock==null)
		{
			this.quantityStock=0.0;	
			
		}
		return this.quantityStock;
	}

	public void setQuantityStock(Double quantityStock) {
		this.quantityStock = quantityStock;
	}

	public Double getQuantityFact() {
		if(this.quantityFact==null)
		{
			this.quantityFact=0.0;
			
		}
		return this.quantityFact;
	}

	public void setQuantityFact(Double quantityFact) {
		this.quantityFact = quantityFact;
	}

	public String getDepositPosition() {
		return this.depositPosition;
	}

	public void setDepositPosition(String depositPosition) {
		this.depositPosition = depositPosition;
	}

	public String getStockId() {
		return this.stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
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

	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPlanNo() {
		return this.planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Double getQuantityStockNow() {
		return quantityStockNow;
	}

	public void setQuantityStockNow(Double quantityStockNow) {
		this.quantityStockNow = quantityStockNow;
	}

	public Short getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Timestamp getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckUserNo() {
		return checkUserNo;
	}

	public void setCheckUserNo(String checkUserNo) {
		this.checkUserNo = checkUserNo;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public Double getCheckTotalCost() {
		return checkTotalCost;
	}

	public void setCheckTotalCost(Double checkTotalCost) {
		this.checkTotalCost = checkTotalCost;
	}

	public Double getCheckTotalCostRecord() {
		return checkTotalCostRecord;
	}

	public void setCheckTotalCostRecord(Double checkTotalCostRecord) {
		this.checkTotalCostRecord = checkTotalCostRecord;
	}

	@Override
	public String toString() {
		return "PartCheckStocksDetail [pcsDetailId=" + pcsDetailId + ", documentNo="
				+ documentNo + ", cost=" + cost + ", carriage="
				+ carriage + ", quantityStock=" + quantityStock + ", quantityFact=" + quantityFact
				+ ", depositPosition=" + depositPosition
				+ ", stockId=" + stockId
				+ ", partId=" + partId
				+ ", partNo=" + partNo
				+ ", partName=" + partName
				+ ", partMnemonic=" + partMnemonic
				+ ", producingArea=" + producingArea
				+ ", partType=" + partType
				+ ", specModel=" + specModel
				+ ", applicableModel=" + applicableModel
				+ ", color=" + color
				+ ", material=" + material
				+ ", unit=" + unit
				+ ", warehouseName=" + warehouseName
				+ ", remark=" + remark
				+ ", planNo=" + planNo
				+ ", warehouseId=" + warehouseId
				+ "]";		
	}
	
}
