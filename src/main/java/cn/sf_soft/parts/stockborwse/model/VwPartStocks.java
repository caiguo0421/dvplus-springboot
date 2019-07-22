package cn.sf_soft.parts.stockborwse.model;

import java.sql.Timestamp;

/**
 * 配件库存信息
 * VwPartStocksId entity. @author MyEclipse Persistence Tools
 */
public class VwPartStocks implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6731336057344636943L;
	private String stockId; //库存ID
	private String stationId;//站点ID
	private Boolean isActive;//库存状态
	private String warehouseId;//仓库ID
	private String partId;//配件ID
	private Double quantity;//库存数量
	private Double quantityBorrow;//借入数量
	private Double quantityWay;//在途数量
	private Double cost;//成本
	private Double costNoTax;
	private Double carriage;//总运费
	private Double carriageNoTax;
	private Double proxyPrice;//代销价
	private String depositPosition;//存放库位
	private Double maxQuantityCompute;//计高
	private Double minQuantityCompute;//计低
	private Double maxQuantityManual;//设高
	private Double minQuantityManual;//设低
	private Timestamp lastPisTime;//最近入库日期
	private Timestamp lastPosTime;//最近出库日期
	private Double velocity;//周转率
	private Boolean isAllowIncome;//是否允许提成
	private Double quantityBorrowOut;//投放外借
	private Double quantityRefuse;//投放拒批
	private Timestamp stockingPlanLastTime;//盘存计划时间
	private String warehouseName;//仓库名称
	private Integer stockType;
	private String partNo;
	private String partMnemonic;//自编码
	private String partName;
	private String namePinyin;
	private String producingArea;//产地
	private String partType;
	private String partProperty;
	private String specModel;//规格型号
	private String applicableModel;//适用车型
	private String color;
	private String unit;//单位
	private Double costRef;//参考成本
	private Double priceRetail;
	private Double priceTrade;
	private Double priceCounterclaim;
	private Double priceInsurance;
	private Double priceInner;
	private String secondSupplierId;//2级供应商
	private String defaultSupplierId;
	private String stationName;
	private Double backQuantity;
	private String defaultSupplierNo;
	private String defaultSupplierName;
	private Double orderQuantity;
	private Double preOrderCount;
	/*
	private Double stockWarn;

	private String material;
	private String dosage;
	private Double lastPisPrice;
	private Double costLoss;
	private Double costNoTaxLoss;
	private String liquidity;
	private Double maxMultiple;
	private Double minMultiple;
	private Double msq;
	private Integer stockTerm;
	private Timestamp lastSaleTime;
	private Double lastPosPrice;
	private Short levelCompute;
	private Short levelManual;
	private String liquidityId;
	private Double maxQuantityComputeLast;
	private Double minQuantityComputeLast;
	private Double costReal;
	private Double carriageReal;
	private Double quantityBuy;
	private Long warehouseProperty;
	private String imagesUrls;
	private Integer quantityPackages;
	private Short liquidityType;
	private Double minMultipleCompute;
	private Double maxMultipleCompute;
	private String liquidityTypeMeaning;
	private Double quantityWayNotDeal;
	private Double quantityWayDealing;
	private Double quantityWayDealed;
	
	private Double priceTradeUse;
	
	
	private Double averageQuantity;
	private String partTypeFullId;
	private Integer emergentDeliveryPeriod;
	private Integer deliveryDays;
	private String shortName;
	private Integer deliveryPeriod;
	private String levelComputeMeaning;
	private String levelManualMeaning;
	private Double minMultipleManual;
	private Double maxMultipleManual;
	 Constructors
	*/
	private Double priceTradeUse;//批发价
	private Double priceRetailUse;//销售价
	private Double priceInsuranceUse;//保险价
	private Double priceCounterclaimUse;//索赔价
	private Double priceInnerUse;//内部价
	private String vehicleTrademark;//车辆品牌
	/** default constructor */
	public VwPartStocks() {
	}
	
	



	public String getStockId() {
		return stockId;
	}



	public void setStockId(String stockId) {
		this.stockId = stockId;
	}



	public String getStationId() {
		return stationId;
	}



	public void setStationId(String stationId) {
		this.stationId = stationId;
	}



	public Boolean getIsActive() {
		return isActive;
	}



	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}



	public String getWarehouseId() {
		return warehouseId;
	}



	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}



	public String getPartId() {
		return partId;
	}



	public void setPartId(String partId) {
		this.partId = partId;
	}



	public Double getQuantity() {
		return quantity;
	}



	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}



	public Double getQuantityBorrow() {
		return quantityBorrow;
	}



	public void setQuantityBorrow(Double quantityBorrow) {
		this.quantityBorrow = quantityBorrow;
	}



	public Double getQuantityWay() {
		return quantityWay;
	}



	public void setQuantityWay(Double quantityWay) {
		this.quantityWay = quantityWay;
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



	public Double getCarriageNoTax() {
		return carriageNoTax;
	}



	public void setCarriageNoTax(Double carriageNoTax) {
		this.carriageNoTax = carriageNoTax;
	}



	public Double getProxyPrice() {
		return proxyPrice;
	}



	public void setProxyPrice(Double proxyPrice) {
		this.proxyPrice = proxyPrice;
	}



	public String getDepositPosition() {
		return depositPosition;
	}



	public void setDepositPosition(String depositPosition) {
		this.depositPosition = depositPosition;
	}



	public Double getMaxQuantityCompute() {
		return maxQuantityCompute;
	}



	public void setMaxQuantityCompute(Double maxQuantityCompute) {
		this.maxQuantityCompute = maxQuantityCompute;
	}



	public Double getMinQuantityCompute() {
		return minQuantityCompute;
	}



	public void setMinQuantityCompute(Double minQuantityCompute) {
		this.minQuantityCompute = minQuantityCompute;
	}



	public Double getMaxQuantityManual() {
		return maxQuantityManual;
	}



	public void setMaxQuantityManual(Double maxQuantityManual) {
		this.maxQuantityManual = maxQuantityManual;
	}



	public Double getMinQuantityManual() {
		return minQuantityManual;
	}



	public void setMinQuantityManual(Double minQuantityManual) {
		this.minQuantityManual = minQuantityManual;
	}



	public Timestamp getLastPisTime() {
		return lastPisTime;
	}



	public void setLastPisTime(Timestamp lastPisTime) {
		this.lastPisTime = lastPisTime;
	}



	public Timestamp getLastPosTime() {
		return lastPosTime;
	}



	public void setLastPosTime(Timestamp lastPosTime) {
		this.lastPosTime = lastPosTime;
	}



	public Double getVelocity() {
		return velocity;
	}



	public void setVelocity(Double velocity) {
		this.velocity = velocity;
	}



	public Boolean getIsAllowIncome() {
		return isAllowIncome;
	}



	public void setIsAllowIncome(Boolean isAllowIncome) {
		this.isAllowIncome = isAllowIncome;
	}



	public Double getQuantityBorrowOut() {
		return quantityBorrowOut;
	}



	public void setQuantityBorrowOut(Double quantityBorrowOut) {
		this.quantityBorrowOut = quantityBorrowOut;
	}



	public Double getQuantityRefuse() {
		return quantityRefuse;
	}



	public void setQuantityRefuse(Double quantityRefuse) {
		this.quantityRefuse = quantityRefuse;
	}



	public Timestamp getStockingPlanLastTime() {
		return stockingPlanLastTime;
	}



	public void setStockingPlanLastTime(Timestamp stockingPlanLastTime) {
		this.stockingPlanLastTime = stockingPlanLastTime;
	}



	public String getWarehouseName() {
		return warehouseName;
	}



	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}



	public Integer getStockType() {
		return stockType;
	}



	public void setStockType(Integer stockType) {
		this.stockType = stockType;
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



	public String getPartName() {
		return partName;
	}



	public void setPartName(String partName) {
		this.partName = partName;
	}



	public String getNamePinyin() {
		return namePinyin;
	}



	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}



	public String getProducingArea() {
		return producingArea;
	}



	public void setProducingArea(String producingArea) {
		this.producingArea = producingArea;
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



	public String getApplicableModel() {
		return applicableModel;
	}



	public void setApplicableModel(String applicableModel) {
		this.applicableModel = applicableModel;
	}



	public String getColor() {
		return color;
	}



	public void setColor(String color) {
		this.color = color;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	public Double getCostRef() {
		return costRef;
	}



	public void setCostRef(Double costRef) {
		this.costRef = costRef;
	}



	public Double getPriceRetail() {
		return priceRetail;
	}



	public void setPriceRetail(Double priceRetail) {
		this.priceRetail = priceRetail;
	}



	public Double getPriceTrade() {
		return priceTrade;
	}



	public void setPriceTrade(Double priceTrade) {
		this.priceTrade = priceTrade;
	}



	public Double getPriceCounterclaim() {
		return priceCounterclaim;
	}



	public void setPriceCounterclaim(Double priceCounterclaim) {
		this.priceCounterclaim = priceCounterclaim;
	}



	public Double getPriceInsurance() {
		return priceInsurance;
	}



	public void setPriceInsurance(Double priceInsurance) {
		this.priceInsurance = priceInsurance;
	}



	public Double getPriceInner() {
		return priceInner;
	}



	public void setPriceInner(Double priceInner) {
		this.priceInner = priceInner;
	}



	public String getSecondSupplierId() {
		return secondSupplierId;
	}



	public void setSecondSupplierId(String secondSupplierId) {
		this.secondSupplierId = secondSupplierId;
	}



	public String getDefaultSupplierId() {
		return defaultSupplierId;
	}



	public void setDefaultSupplierId(String defaultSupplierId) {
		this.defaultSupplierId = defaultSupplierId;
	}



	public String getStationName() {
		return stationName;
	}



	public void setStationName(String stationName) {
		this.stationName = stationName;
	}



	public Double getBackQuantity() {
		return backQuantity;
	}



	public void setBackQuantity(Double backQuantity) {
		this.backQuantity = backQuantity;
	}



	public String getDefaultSupplierNo() {
		return defaultSupplierNo;
	}



	public void setDefaultSupplierNo(String defaultSupplierNo) {
		this.defaultSupplierNo = defaultSupplierNo;
	}



	public String getDefaultSupplierName() {
		return defaultSupplierName;
	}



	public void setDefaultSupplierName(String defaultSupplierName) {
		this.defaultSupplierName = defaultSupplierName;
	}



	public Double getOrderQuantity() {
		return orderQuantity;
	}



	public void setOrderQuantity(Double orderQuantity) {
		this.orderQuantity = orderQuantity;
	}



	public Double getPreOrderCount() {
		return preOrderCount;
	}



	public void setPreOrderCount(Double preOrderCount) {
		this.preOrderCount = preOrderCount;
	}



//	public Double getStockWarn() {
//		return stockWarn;
//	}
//
//
//
//	public void setStockWarn(Double stockWarn) {
//		this.stockWarn = stockWarn;
//	}


	// Property accessors

	
	public Double getPriceRetailUse() {
		return priceRetailUse;
	}



	public void setPriceRetailUse(Double priceRetailUse) {
		this.priceRetailUse = priceRetailUse;
	}


	
	public Double getPriceTradeUse() {
		return priceTradeUse;
	}



	public void setPriceTradeUse(Double priceTradeUse) {
		this.priceTradeUse = priceTradeUse;
	}



	public Double getPriceInsuranceUse() {
		return priceInsuranceUse;
	}
	


	public void setPriceInsuranceUse(Double priceInsuranceUse) {
		this.priceInsuranceUse = priceInsuranceUse;
	}



	public Double getPriceCounterclaimUse() {
		return priceCounterclaimUse;
	}



	public void setPriceCounterclaimUse(Double priceCounterclaimUse) {
		this.priceCounterclaimUse = priceCounterclaimUse;
	}



	public Double getPriceInnerUse() {
		return priceInnerUse;
	}



	public void setPriceInnerUse(Double priceInnerUse) {
		this.priceInnerUse = priceInnerUse;
	}



	public String getVehicleTrademark() {
		return vehicleTrademark;
	}



	public void setVehicleTrademark(String vehicleTrademark) {
		this.vehicleTrademark = vehicleTrademark;
	}
	
}
