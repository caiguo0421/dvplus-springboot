package cn.sf_soft.vehicle.stockbrowse.model;

import java.sql.Timestamp;

/**
 * 库存车
 * 
 * @author caigx
 *
 */
public class VehicleStocks implements java.io.Serializable {

	private static final long serialVersionUID = -6832256342500416799L;
	private String vehicleId;
	private String vehicleVin;
	private String vnoId;
	private String vehicleSalesCode;
	private String vehicleVno;
	private String vehicleName;
	private String vehicleStrain;
	private String vehicleColor;
	private String vehicleEngineType;
	private String vehicleEngineNo;
	private String vehicleEligibleNo;
	private Timestamp vehicleOutFactoryTime;
	private Double vehicleCost;
	private Double vehicleCarriage;
	private String inComment;
	private String inStockNo;
	private Timestamp inStockTime;
	private String inStockType;
	private String warehouseName;
	private String warehouseId;
	private String stationId;
	private String supplierId;
	private String supplierName;
	private String carryNo;
	private String saleContractNo;
	private String saleContractCode;
	private String customerId;
	private String customerName;
	private String seller;
	private String outStockNo;
	private Timestamp outStockTime;
	private Short outStockType;
	private Double vehiclePrice;
	private Integer vehicleQuantity;
	private String purchaseContractNo;
	private String purchaseContractCode;
	private String deliveryLocus;
	private Short status;
	private Short conversionStatus;
	private Timestamp validateTime;
	private String vehicleVnoNew;
	private String vehicleVinNew;
	private Double profitReturn;
	private Double modifiedFee;
	private String invoiceNo;
	private Double invoiceAmount;
	private String stockRemark;
	private String driveRoomNo;
	private Double maintainFee;
	private Timestamp futurePayDate;
	private Double minSalePrice;
	private Double vehicleLowestProfit;
	private Timestamp writeOffDate;
	private String imagesUrls;
	private String oilCardRegisterNo;
	private String cardOutNo;
	private Double moveStockCharge;
	private Short vehicleType;
	private Timestamp scanningOutStocks;
	private String vehicleSelfNo;
	private Double otherCost;
	private String otherCostSaleContractId;
	private Double taxRate;
	private Double noTaxPrice;
	private String sellerId;
	private Short payType;
	private String vehicleSaleDocuments;
	private Timestamp invoiceDate;
	private String issuedNo;
	private String sapContractNo;
	private Short writeOffFlag;
	private String writeOffNo;
	private String vwoNo;
	private String speedRatio;
	private String tireType;
	private Double costVary;
	private String underpanNo;

	private String vatNo;
	private String vatStationId;

    /**
     * 车辆采购订单ID
     */
	private String orderId;
	private String resourcesNo;

	public VehicleStocks() {
	}

	public VehicleStocks(String vehicleId, String vehicleVin) {
		this.vehicleId = vehicleId;
		this.vehicleVin = vehicleVin;
	}

	public VehicleStocks(String vehicleId, String vehicleVin, String vnoId, String vehicleSalesCode, String vehicleVno,
			String vehicleName, String vehicleStrain, String vehicleColor, String vehicleEngineType,
			String vehicleEngineNo, String vehicleEligibleNo, Timestamp vehicleOutFactoryTime, Double vehicleCost,
			Double vehicleCarriage, String inComment, String inStockNo, Timestamp inStockTime, String inStockType,
			String warehouseName, String warehouseId, String stationId, String supplierId, String supplierName,
			String carryNo, String saleContractNo, String saleContractCode, String customerId, String customerName,
			String seller, String outStockNo, Timestamp outStockTime, Short outStockType, Double vehiclePrice,
			Integer vehicleQuantity, String purchaseContractNo, String purchaseContractCode, String deliveryLocus,
			Short status, Short conversionStatus, Timestamp validateTime, String vehicleVnoNew, String vehicleVinNew,
			Double profitReturn, Double modifiedFee, String invoiceNo, Double invoiceAmount, String stockRemark,
			String driveRoomNo, Double maintainFee, Timestamp futurePayDate, Double minSalePrice,
			Double vehicleLowestProfit, Timestamp writeOffDate, String imagesUrls, String oilCardRegisterNo,
			String cardOutNo, Double moveStockCharge, Short vehicleType, Timestamp scanningOutStocks,
			String vehicleSelfNo, Double otherCost, String otherCostSaleContractId, Double taxRate, Double noTaxPrice,
			String sellerId, Short payType, String vehicleSaleDocuments, Timestamp invoiceDate, String issuedNo,
			String sapContractNo, Short writeOffFlag, String writeOffNo, String vwoNo, String speedRatio,
			String tireType, Double costVary, String underpanNo) {
		this.vehicleId = vehicleId;
		this.vehicleVin = vehicleVin;
		this.vnoId = vnoId;
		this.vehicleSalesCode = vehicleSalesCode;
		this.vehicleVno = vehicleVno;
		this.vehicleName = vehicleName;
		this.vehicleStrain = vehicleStrain;
		this.vehicleColor = vehicleColor;
		this.vehicleEngineType = vehicleEngineType;
		this.vehicleEngineNo = vehicleEngineNo;
		this.vehicleEligibleNo = vehicleEligibleNo;
		this.vehicleOutFactoryTime = vehicleOutFactoryTime;
		this.vehicleCost = vehicleCost;
		this.vehicleCarriage = vehicleCarriage;
		this.inComment = inComment;
		this.inStockNo = inStockNo;
		this.inStockTime = inStockTime;
		this.inStockType = inStockType;
		this.warehouseName = warehouseName;
		this.warehouseId = warehouseId;
		this.stationId = stationId;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.carryNo = carryNo;
		this.saleContractNo = saleContractNo;
		this.saleContractCode = saleContractCode;
		this.customerId = customerId;
		this.customerName = customerName;
		this.seller = seller;
		this.outStockNo = outStockNo;
		this.outStockTime = outStockTime;
		this.outStockType = outStockType;
		this.vehiclePrice = vehiclePrice;
		this.vehicleQuantity = vehicleQuantity;
		this.purchaseContractNo = purchaseContractNo;
		this.purchaseContractCode = purchaseContractCode;
		this.deliveryLocus = deliveryLocus;
		this.status = status;
		this.conversionStatus = conversionStatus;
		this.validateTime = validateTime;
		this.vehicleVnoNew = vehicleVnoNew;
		this.vehicleVinNew = vehicleVinNew;
		this.profitReturn = profitReturn;
		this.modifiedFee = modifiedFee;
		this.invoiceNo = invoiceNo;
		this.invoiceAmount = invoiceAmount;
		this.stockRemark = stockRemark;
		this.driveRoomNo = driveRoomNo;
		this.maintainFee = maintainFee;
		this.futurePayDate = futurePayDate;
		this.minSalePrice = minSalePrice;
		this.vehicleLowestProfit = vehicleLowestProfit;
		this.writeOffDate = writeOffDate;
		this.imagesUrls = imagesUrls;
		this.oilCardRegisterNo = oilCardRegisterNo;
		this.cardOutNo = cardOutNo;
		this.moveStockCharge = moveStockCharge;
		this.vehicleType = vehicleType;
		this.scanningOutStocks = scanningOutStocks;
		this.vehicleSelfNo = vehicleSelfNo;
		this.otherCost = otherCost;
		this.otherCostSaleContractId = otherCostSaleContractId;
		this.taxRate = taxRate;
		this.noTaxPrice = noTaxPrice;
		this.sellerId = sellerId;
		this.payType = payType;
		this.vehicleSaleDocuments = vehicleSaleDocuments;
		this.invoiceDate = invoiceDate;
		this.issuedNo = issuedNo;
		this.sapContractNo = sapContractNo;
		this.writeOffFlag = writeOffFlag;
		this.writeOffNo = writeOffNo;
		this.vwoNo = vwoNo;
		this.speedRatio = speedRatio;
		this.tireType = tireType;
		this.costVary = costVary;
		this.underpanNo = underpanNo;
	}


	public String getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleVin() {
		return this.vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
	}

	public String getVnoId() {
		return this.vnoId;
	}

	public void setVnoId(String vnoId) {
		this.vnoId = vnoId;
	}

	public String getVehicleSalesCode() {
		return this.vehicleSalesCode;
	}

	public void setVehicleSalesCode(String vehicleSalesCode) {
		this.vehicleSalesCode = vehicleSalesCode;
	}

	public String getVehicleVno() {
		return this.vehicleVno;
	}

	public void setVehicleVno(String vehicleVno) {
		this.vehicleVno = vehicleVno;
	}

	public String getVehicleName() {
		return this.vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehicleStrain() {
		return this.vehicleStrain;
	}

	public void setVehicleStrain(String vehicleStrain) {
		this.vehicleStrain = vehicleStrain;
	}

	public String getVehicleColor() {
		return this.vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getVehicleEngineType() {
		return this.vehicleEngineType;
	}

	public void setVehicleEngineType(String vehicleEngineType) {
		this.vehicleEngineType = vehicleEngineType;
	}

	public String getVehicleEngineNo() {
		return this.vehicleEngineNo;
	}

	public void setVehicleEngineNo(String vehicleEngineNo) {
		this.vehicleEngineNo = vehicleEngineNo;
	}

	public String getVehicleEligibleNo() {
		return this.vehicleEligibleNo;
	}

	public void setVehicleEligibleNo(String vehicleEligibleNo) {
		this.vehicleEligibleNo = vehicleEligibleNo;
	}

	public Timestamp getVehicleOutFactoryTime() {
		return this.vehicleOutFactoryTime;
	}

	public void setVehicleOutFactoryTime(Timestamp vehicleOutFactoryTime) {
		this.vehicleOutFactoryTime = vehicleOutFactoryTime;
	}

	public Double getVehicleCost() {
		return this.vehicleCost;
	}

	public void setVehicleCost(Double vehicleCost) {
		this.vehicleCost = vehicleCost;
	}

	public Double getVehicleCarriage() {
		return this.vehicleCarriage;
	}

	public void setVehicleCarriage(Double vehicleCarriage) {
		this.vehicleCarriage = vehicleCarriage;
	}

	public String getInComment() {
		return this.inComment;
	}

	public void setInComment(String inComment) {
		this.inComment = inComment;
	}

	public String getInStockNo() {
		return this.inStockNo;
	}

	public void setInStockNo(String inStockNo) {
		this.inStockNo = inStockNo;
	}

	public Timestamp getInStockTime() {
		return this.inStockTime;
	}

	public void setInStockTime(Timestamp inStockTime) {
		this.inStockTime = inStockTime;
	}

	public String getInStockType() {
		return this.inStockType;
	}

	public void setInStockType(String inStockType) {
		this.inStockType = inStockType;
	}

	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCarryNo() {
		return this.carryNo;
	}

	public void setCarryNo(String carryNo) {
		this.carryNo = carryNo;
	}

	public String getSaleContractNo() {
		return this.saleContractNo;
	}

	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}

	public String getSaleContractCode() {
		return this.saleContractCode;
	}

	public void setSaleContractCode(String saleContractCode) {
		this.saleContractCode = saleContractCode;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSeller() {
		return this.seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getOutStockNo() {
		return this.outStockNo;
	}

	public void setOutStockNo(String outStockNo) {
		this.outStockNo = outStockNo;
	}

	public Timestamp getOutStockTime() {
		return this.outStockTime;
	}

	public void setOutStockTime(Timestamp outStockTime) {
		this.outStockTime = outStockTime;
	}

	public Short getOutStockType() {
		return this.outStockType;
	}

	public void setOutStockType(Short outStockType) {
		this.outStockType = outStockType;
	}

	public Double getVehiclePrice() {
		return this.vehiclePrice;
	}

	public void setVehiclePrice(Double vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}

	public Integer getVehicleQuantity() {
		return this.vehicleQuantity;
	}

	public void setVehicleQuantity(Integer vehicleQuantity) {
		this.vehicleQuantity = vehicleQuantity;
	}

	public String getPurchaseContractNo() {
		return this.purchaseContractNo;
	}

	public void setPurchaseContractNo(String purchaseContractNo) {
		this.purchaseContractNo = purchaseContractNo;
	}

	public String getPurchaseContractCode() {
		return this.purchaseContractCode;
	}

	public void setPurchaseContractCode(String purchaseContractCode) {
		this.purchaseContractCode = purchaseContractCode;
	}

	public String getDeliveryLocus() {
		return this.deliveryLocus;
	}

	public void setDeliveryLocus(String deliveryLocus) {
		this.deliveryLocus = deliveryLocus;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getConversionStatus() {
		return this.conversionStatus;
	}

	public void setConversionStatus(Short conversionStatus) {
		this.conversionStatus = conversionStatus;
	}

	public Timestamp getValidateTime() {
		return this.validateTime;
	}

	public void setValidateTime(Timestamp validateTime) {
		this.validateTime = validateTime;
	}

	public String getVehicleVnoNew() {
		return this.vehicleVnoNew;
	}

	public void setVehicleVnoNew(String vehicleVnoNew) {
		this.vehicleVnoNew = vehicleVnoNew;
	}

	public String getVehicleVinNew() {
		return this.vehicleVinNew;
	}

	public void setVehicleVinNew(String vehicleVinNew) {
		this.vehicleVinNew = vehicleVinNew;
	}

	public Double getProfitReturn() {
		return this.profitReturn;
	}

	public void setProfitReturn(Double profitReturn) {
		this.profitReturn = profitReturn;
	}

	public Double getModifiedFee() {
		return this.modifiedFee;
	}

	public void setModifiedFee(Double modifiedFee) {
		this.modifiedFee = modifiedFee;
	}

	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Double getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getStockRemark() {
		return this.stockRemark;
	}

	public void setStockRemark(String stockRemark) {
		this.stockRemark = stockRemark;
	}

	public String getDriveRoomNo() {
		return this.driveRoomNo;
	}

	public void setDriveRoomNo(String driveRoomNo) {
		this.driveRoomNo = driveRoomNo;
	}

	public Double getMaintainFee() {
		return this.maintainFee;
	}

	public void setMaintainFee(Double maintainFee) {
		this.maintainFee = maintainFee;
	}

	public Timestamp getFuturePayDate() {
		return this.futurePayDate;
	}

	public void setFuturePayDate(Timestamp futurePayDate) {
		this.futurePayDate = futurePayDate;
	}

	public Double getMinSalePrice() {
		return this.minSalePrice;
	}

	public void setMinSalePrice(Double minSalePrice) {
		this.minSalePrice = minSalePrice;
	}

	public Double getVehicleLowestProfit() {
		return this.vehicleLowestProfit;
	}

	public void setVehicleLowestProfit(Double vehicleLowestProfit) {
		this.vehicleLowestProfit = vehicleLowestProfit;
	}

	public Timestamp getWriteOffDate() {
		return this.writeOffDate;
	}

	public void setWriteOffDate(Timestamp writeOffDate) {
		this.writeOffDate = writeOffDate;
	}

	public String getImagesUrls() {
		return this.imagesUrls;
	}

	public void setImagesUrls(String imagesUrls) {
		this.imagesUrls = imagesUrls;
	}

	public String getOilCardRegisterNo() {
		return this.oilCardRegisterNo;
	}

	public void setOilCardRegisterNo(String oilCardRegisterNo) {
		this.oilCardRegisterNo = oilCardRegisterNo;
	}

	public String getCardOutNo() {
		return this.cardOutNo;
	}

	public void setCardOutNo(String cardOutNo) {
		this.cardOutNo = cardOutNo;
	}

	public Double getMoveStockCharge() {
		return this.moveStockCharge;
	}

	public void setMoveStockCharge(Double moveStockCharge) {
		this.moveStockCharge = moveStockCharge;
	}

	public Short getVehicleType() {
		return this.vehicleType;
	}

	public void setVehicleType(Short vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Timestamp getScanningOutStocks() {
		return this.scanningOutStocks;
	}

	public void setScanningOutStocks(Timestamp scanningOutStocks) {
		this.scanningOutStocks = scanningOutStocks;
	}

	public String getVehicleSelfNo() {
		return this.vehicleSelfNo;
	}

	public void setVehicleSelfNo(String vehicleSelfNo) {
		this.vehicleSelfNo = vehicleSelfNo;
	}

	public Double getOtherCost() {
		return this.otherCost;
	}

	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}

	public String getOtherCostSaleContractId() {
		return this.otherCostSaleContractId;
	}

	public void setOtherCostSaleContractId(String otherCostSaleContractId) {
		this.otherCostSaleContractId = otherCostSaleContractId;
	}

	public Double getTaxRate() {
		return this.taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public Double getNoTaxPrice() {
		return this.noTaxPrice;
	}

	public void setNoTaxPrice(Double noTaxPrice) {
		this.noTaxPrice = noTaxPrice;
	}

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public Short getPayType() {
		return this.payType;
	}

	public void setPayType(Short payType) {
		this.payType = payType;
	}

	public String getVehicleSaleDocuments() {
		return this.vehicleSaleDocuments;
	}

	public void setVehicleSaleDocuments(String vehicleSaleDocuments) {
		this.vehicleSaleDocuments = vehicleSaleDocuments;
	}

	public Timestamp getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getIssuedNo() {
		return this.issuedNo;
	}

	public void setIssuedNo(String issuedNo) {
		this.issuedNo = issuedNo;
	}

	public String getSapContractNo() {
		return this.sapContractNo;
	}

	public void setSapContractNo(String sapContractNo) {
		this.sapContractNo = sapContractNo;
	}

	public Short getWriteOffFlag() {
		return this.writeOffFlag;
	}

	public void setWriteOffFlag(Short writeOffFlag) {
		this.writeOffFlag = writeOffFlag;
	}

	public String getWriteOffNo() {
		return this.writeOffNo;
	}

	public void setWriteOffNo(String writeOffNo) {
		this.writeOffNo = writeOffNo;
	}

	public String getVwoNo() {
		return this.vwoNo;
	}

	public void setVwoNo(String vwoNo) {
		this.vwoNo = vwoNo;
	}

	public String getSpeedRatio() {
		return this.speedRatio;
	}

	public void setSpeedRatio(String speedRatio) {
		this.speedRatio = speedRatio;
	}

	public String getTireType() {
		return this.tireType;
	}

	public void setTireType(String tireType) {
		this.tireType = tireType;
	}

	public Double getCostVary() {
		return this.costVary;
	}

	public void setCostVary(Double costVary) {
		this.costVary = costVary;
	}

	public String getUnderpanNo() {
		return this.underpanNo;
	}

	public void setUnderpanNo(String underpanNo) {
		this.underpanNo = underpanNo;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	public String getVatStationId() {
		return vatStationId;
	}

	public void setVatStationId(String vatStationId) {
		this.vatStationId = vatStationId;
	}

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

	public String getResourcesNo() {
		return resourcesNo;
	}

	public void setResourcesNo(String resourcesNo) {
		this.resourcesNo = resourcesNo;
	}
}
