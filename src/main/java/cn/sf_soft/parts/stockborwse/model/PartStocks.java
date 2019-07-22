package cn.sf_soft.parts.stockborwse.model;

import java.sql.Timestamp;

/**
 * 配件库存
 * @author cw
 * @date 2014-4-17 上午11:22:52
 */
public class PartStocks implements java.io.Serializable {

	// Fields

	private String stockId;
	private String stationId;
	private Boolean isActive;
	private String warehouseId;
	private String partId;
	private Double quantity;
	private Double quantityBorrow;
	private Double quantityWay;
	private Double cost;
	private Double costNoTax;
	private Double carriage;
	private Double carriageNoTax;
	private Double proxyPrice;
	private Double costLoss;
	private Double costNoTaxLoss;
	private String depositPosition;
	private String liquidity;
	private Double maxMultiple;
	private Double minMultiple;
	private Double msq;
	private Double maxQuantityCompute;
	private Double minQuantityCompute;
	private Double maxQuantityManual;
	private Double minQuantityManual;
	private Integer stockTerm;
	private Timestamp lastPisTime;
	private Double lastPisPrice;
	private Timestamp lastPosTime;
	private Timestamp lastSaleTime;
	private Double lastPosPrice;
	private Double velocity;
	private Short levelCompute;
	private Short levelManual;
	private String liquidityId;
	private Boolean isAllowIncome;
	private Double quantityBorrowOut;
	private Double quantityRefuse;
	private Double maxQuantityComputeLast;
	private Double minQuantityComputeLast;
	private Timestamp stockingPlanLastTime;
	private Timestamp inventoryTime;
	private Double stockTotalCost;
	private Timestamp modifyTime;
	// Constructors

	/** default constructor */
	public PartStocks() {
	}

	/** minimal constructor */
	public PartStocks(String stockId) {
		this.stockId = stockId;
	}

	/** full constructor */
	public PartStocks(String stockId, String stationId, Boolean isActive,
			String warehouseId, String partId, Double quantity,
			Double quantityBorrow, Double quantityWay, Double cost,
			Double costNoTax, Double carriage, Double carriageNoTax,
			Double proxyPrice, Double costLoss, Double costNoTaxLoss,
			String depositPosition, String liquidity, Double maxMultiple,
			Double minMultiple, Double msq, Double maxQuantityCompute,
			Double minQuantityCompute, Double maxQuantityManual,
			Double minQuantityManual, Integer stockTerm, Timestamp lastPisTime,
			Double lastPisPrice, Timestamp lastPosTime, Timestamp lastSaleTime,
			Double lastPosPrice, Double velocity, Short levelCompute,
			Short levelManual, String liquidityId, Boolean isAllowIncome,
			Double quantityBorrowOut, Double quantityRefuse,
			Double maxQuantityComputeLast, Double minQuantityComputeLast,
			Timestamp stockingPlanLastTime, Timestamp inventoryTime, Double stockTotalCost) {
		this.stockId = stockId;
		this.stationId = stationId;
		this.isActive = isActive;
		this.warehouseId = warehouseId;
		this.partId = partId;
		this.quantity = quantity;
		this.quantityBorrow = quantityBorrow;
		this.quantityWay = quantityWay;
		this.cost = cost;
		this.costNoTax = costNoTax;
		this.carriage = carriage;
		this.carriageNoTax = carriageNoTax;
		this.proxyPrice = proxyPrice;
		this.costLoss = costLoss;
		this.costNoTaxLoss = costNoTaxLoss;
		this.depositPosition = depositPosition;
		this.liquidity = liquidity;
		this.maxMultiple = maxMultiple;
		this.minMultiple = minMultiple;
		this.msq = msq;
		this.maxQuantityCompute = maxQuantityCompute;
		this.minQuantityCompute = minQuantityCompute;
		this.maxQuantityManual = maxQuantityManual;
		this.minQuantityManual = minQuantityManual;
		this.stockTerm = stockTerm;
		this.lastPisTime = lastPisTime;
		this.lastPisPrice = lastPisPrice;
		this.lastPosTime = lastPosTime;
		this.lastSaleTime = lastSaleTime;
		this.lastPosPrice = lastPosPrice;
		this.velocity = velocity;
		this.levelCompute = levelCompute;
		this.levelManual = levelManual;
		this.liquidityId = liquidityId;
		this.isAllowIncome = isAllowIncome;
		this.quantityBorrowOut = quantityBorrowOut;
		this.quantityRefuse = quantityRefuse;
		this.maxQuantityComputeLast = maxQuantityComputeLast;
		this.minQuantityComputeLast = minQuantityComputeLast;
		this.stockingPlanLastTime = stockingPlanLastTime;
		this.inventoryTime = inventoryTime;
		this.stockTotalCost = stockTotalCost;
	}

	// Property accessors

	public String getStockId() {
		return this.stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getPartId() {
		return this.partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getQuantityBorrow() {
		return this.quantityBorrow;
	}

	public void setQuantityBorrow(Double quantityBorrow) {
		this.quantityBorrow = quantityBorrow;
	}

	public Double getQuantityWay() {
		return this.quantityWay;
	}

	public void setQuantityWay(Double quantityWay) {
		this.quantityWay = quantityWay;
	}

	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getCostNoTax() {
		return this.costNoTax;
	}

	public void setCostNoTax(Double costNoTax) {
		this.costNoTax = costNoTax;
	}

	public Double getCarriage() {
		return this.carriage;
	}

	public void setCarriage(Double carriage) {
		this.carriage = carriage;
	}

	public Double getCarriageNoTax() {
		return this.carriageNoTax;
	}

	public void setCarriageNoTax(Double carriageNoTax) {
		this.carriageNoTax = carriageNoTax;
	}

	public Double getProxyPrice() {
		return this.proxyPrice;
	}

	public void setProxyPrice(Double proxyPrice) {
		this.proxyPrice = proxyPrice;
	}

	public Double getCostLoss() {
		return this.costLoss;
	}

	public void setCostLoss(Double costLoss) {
		this.costLoss = costLoss;
	}

	public Double getCostNoTaxLoss() {
		return this.costNoTaxLoss;
	}

	public void setCostNoTaxLoss(Double costNoTaxLoss) {
		this.costNoTaxLoss = costNoTaxLoss;
	}

	public String getDepositPosition() {
		return this.depositPosition;
	}

	public void setDepositPosition(String depositPosition) {
		this.depositPosition = depositPosition;
	}

	public String getLiquidity() {
		return this.liquidity;
	}

	public void setLiquidity(String liquidity) {
		this.liquidity = liquidity;
	}

	public Double getMaxMultiple() {
		return this.maxMultiple;
	}

	public void setMaxMultiple(Double maxMultiple) {
		this.maxMultiple = maxMultiple;
	}

	public Double getMinMultiple() {
		return this.minMultiple;
	}

	public void setMinMultiple(Double minMultiple) {
		this.minMultiple = minMultiple;
	}

	public Double getMsq() {
		return this.msq;
	}

	public void setMsq(Double msq) {
		this.msq = msq;
	}

	public Double getMaxQuantityCompute() {
		return this.maxQuantityCompute;
	}

	public void setMaxQuantityCompute(Double maxQuantityCompute) {
		this.maxQuantityCompute = maxQuantityCompute;
	}

	public Double getMinQuantityCompute() {
		return this.minQuantityCompute;
	}

	public void setMinQuantityCompute(Double minQuantityCompute) {
		this.minQuantityCompute = minQuantityCompute;
	}

	public Double getMaxQuantityManual() {
		return this.maxQuantityManual;
	}

	public void setMaxQuantityManual(Double maxQuantityManual) {
		this.maxQuantityManual = maxQuantityManual;
	}

	public Double getMinQuantityManual() {
		return this.minQuantityManual;
	}

	public void setMinQuantityManual(Double minQuantityManual) {
		this.minQuantityManual = minQuantityManual;
	}

	public Integer getStockTerm() {
		return this.stockTerm;
	}

	public void setStockTerm(Integer stockTerm) {
		this.stockTerm = stockTerm;
	}

	public Timestamp getLastPisTime() {
		return this.lastPisTime;
	}

	public void setLastPisTime(Timestamp lastPisTime) {
		this.lastPisTime = lastPisTime;
	}

	public Double getLastPisPrice() {
		return this.lastPisPrice;
	}

	public void setLastPisPrice(Double lastPisPrice) {
		this.lastPisPrice = lastPisPrice;
	}

	public Timestamp getLastPosTime() {
		return this.lastPosTime;
	}

	public void setLastPosTime(Timestamp lastPosTime) {
		this.lastPosTime = lastPosTime;
	}

	public Timestamp getLastSaleTime() {
		return this.lastSaleTime;
	}

	public void setLastSaleTime(Timestamp lastSaleTime) {
		this.lastSaleTime = lastSaleTime;
	}

	public Double getLastPosPrice() {
		return this.lastPosPrice;
	}

	public void setLastPosPrice(Double lastPosPrice) {
		this.lastPosPrice = lastPosPrice;
	}

	public Double getVelocity() {
		return this.velocity;
	}

	public void setVelocity(Double velocity) {
		this.velocity = velocity;
	}

	public Short getLevelCompute() {
		return this.levelCompute;
	}

	public void setLevelCompute(Short levelCompute) {
		this.levelCompute = levelCompute;
	}

	public Short getLevelManual() {
		return this.levelManual;
	}

	public void setLevelManual(Short levelManual) {
		this.levelManual = levelManual;
	}

	public String getLiquidityId() {
		return this.liquidityId;
	}

	public void setLiquidityId(String liquidityId) {
		this.liquidityId = liquidityId;
	}

	public Boolean getIsAllowIncome() {
		return this.isAllowIncome;
	}

	public void setIsAllowIncome(Boolean isAllowIncome) {
		this.isAllowIncome = isAllowIncome;
	}

	public Double getQuantityBorrowOut() {
		return this.quantityBorrowOut;
	}

	public void setQuantityBorrowOut(Double quantityBorrowOut) {
		this.quantityBorrowOut = quantityBorrowOut;
	}

	public Double getQuantityRefuse() {
		return this.quantityRefuse;
	}

	public void setQuantityRefuse(Double quantityRefuse) {
		this.quantityRefuse = quantityRefuse;
	}

	public Double getMaxQuantityComputeLast() {
		return this.maxQuantityComputeLast;
	}

	public void setMaxQuantityComputeLast(Double maxQuantityComputeLast) {
		this.maxQuantityComputeLast = maxQuantityComputeLast;
	}

	public Double getMinQuantityComputeLast() {
		return this.minQuantityComputeLast;
	}

	public void setMinQuantityComputeLast(Double minQuantityComputeLast) {
		this.minQuantityComputeLast = minQuantityComputeLast;
	}

	public Timestamp getStockingPlanLastTime() {
		return this.stockingPlanLastTime;
	}

	public void setStockingPlanLastTime(Timestamp stockingPlanLastTime) {
		this.stockingPlanLastTime = stockingPlanLastTime;
	}

	public Timestamp getInventoryTime() {
		return this.inventoryTime;
	}

	public void setInventoryTime(Timestamp inventoryTime) {
		this.inventoryTime = inventoryTime;
	}

	public Double getStockTotalCost() {
		return stockTotalCost;
	}

	public void setStockTotalCost(Double stockTotalCost) {
		this.stockTotalCost = stockTotalCost;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
}
