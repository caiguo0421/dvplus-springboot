package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 二手车库存
 * 
 * @author caigx
 *
 */
public class SecondhandVehicleStocks implements java.io.Serializable {

	private static final long serialVersionUID = 3787174952726730956L;
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
	private String vehicleSelfNo;
	private Double vehicleCost;
	private Double vehicleCarriage;
	private String vehicleCardNo;
	private String inComment;
	private String inStockNo;
	private Timestamp inStockTime;
	private String inStockType;
	private String warehouseName;
	private String warehouseId;
	private String stationId;
	private String supplierId;
	private String supplierNo;
	private String supplierName;
	private String saleContractNo;
	private String saleContractCode;
	private String customerId;
	private String customerName;
	private String seller;
	private String sellerId;
	private String outStockNo;
	private Timestamp outStockTime;
	private Short outStockType;
	private Double vehiclePrice;
	private Integer vehicleQuantity;
	private Double modifiedFee;
	private Double profitReturn;
	private Double maintainFee;
	private Double moveStockCharge;
	private Double minSalePrice;
	private Double vehicleLowestProfit;
	private Double otherCost;
	private Double costVary;
	private Double saleReferencePrice;
	private Boolean isMinSalePrice;
	private Boolean isLowestProfit;
	private String purchaseContractNo;
	private String purchaseContractCode;
	private String deliveryLocus;
	private Short status;
	private Short stockProperty;
	private Short conversionStatus;
	private Timestamp validateTime;
	private String vehicleVnoNew;
	private String vehicleVinNew;
	private String invoiceNo;
	private Double invoiceAmount;
	private Timestamp invoiceDate;
	private String stockRemark;
	private String driveRoomNo;
	private Double drivingMileage;
	private String imagesUrls;
	private String issuedNo;
	private String speedRatio;
	private String tireType;

	public SecondhandVehicleStocks() {
	}

	public SecondhandVehicleStocks(String vehicleId, String vehicleVin, String vnoId, String vehicleSalesCode,
			String vehicleVno, String vehicleName, String vehicleStrain, String vehicleColor, String vehicleEngineType,
			String vehicleEngineNo, String vehicleEligibleNo, Timestamp vehicleOutFactoryTime, String vehicleSelfNo,
			Double vehicleCost, Double vehicleCarriage, String vehicleCardNo, String inComment, String inStockNo,
			Timestamp inStockTime, String inStockType, String warehouseName, String warehouseId, String stationId,
			String supplierId, String supplierNo, String supplierName, String saleContractNo, String saleContractCode,
			String customerId, String customerName, String seller, String sellerId, String outStockNo,
			Timestamp outStockTime, Short outStockType, Double vehiclePrice, Integer vehicleQuantity,
			Double modifiedFee, Double profitReturn, Double maintainFee, Double moveStockCharge, Double minSalePrice,
			Double vehicleLowestProfit, Double otherCost, Double costVary, Double saleReferencePrice,
			Boolean isMinSalePrice, Boolean isLowestProfit, String purchaseContractNo, String purchaseContractCode,
			String deliveryLocus, Short status, Short stockProperty, Short conversionStatus, Timestamp validateTime,
			String vehicleVnoNew, String vehicleVinNew, String invoiceNo, Double invoiceAmount, Timestamp invoiceDate,
			String stockRemark, String driveRoomNo, Double drivingMileage, String imagesUrls, String issuedNo,
			String speedRatio, String tireType) {
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
		this.vehicleSelfNo = vehicleSelfNo;
		this.vehicleCost = vehicleCost;
		this.vehicleCarriage = vehicleCarriage;
		this.vehicleCardNo = vehicleCardNo;
		this.inComment = inComment;
		this.inStockNo = inStockNo;
		this.inStockTime = inStockTime;
		this.inStockType = inStockType;
		this.warehouseName = warehouseName;
		this.warehouseId = warehouseId;
		this.stationId = stationId;
		this.supplierId = supplierId;
		this.supplierNo = supplierNo;
		this.supplierName = supplierName;
		this.saleContractNo = saleContractNo;
		this.saleContractCode = saleContractCode;
		this.customerId = customerId;
		this.customerName = customerName;
		this.seller = seller;
		this.sellerId = sellerId;
		this.outStockNo = outStockNo;
		this.outStockTime = outStockTime;
		this.outStockType = outStockType;
		this.vehiclePrice = vehiclePrice;
		this.vehicleQuantity = vehicleQuantity;
		this.modifiedFee = modifiedFee;
		this.profitReturn = profitReturn;
		this.maintainFee = maintainFee;
		this.moveStockCharge = moveStockCharge;
		this.minSalePrice = minSalePrice;
		this.vehicleLowestProfit = vehicleLowestProfit;
		this.otherCost = otherCost;
		this.costVary = costVary;
		this.saleReferencePrice = saleReferencePrice;
		this.isMinSalePrice = isMinSalePrice;
		this.isLowestProfit = isLowestProfit;
		this.purchaseContractNo = purchaseContractNo;
		this.purchaseContractCode = purchaseContractCode;
		this.deliveryLocus = deliveryLocus;
		this.status = status;
		this.stockProperty = stockProperty;
		this.conversionStatus = conversionStatus;
		this.validateTime = validateTime;
		this.vehicleVnoNew = vehicleVnoNew;
		this.vehicleVinNew = vehicleVinNew;
		this.invoiceNo = invoiceNo;
		this.invoiceAmount = invoiceAmount;
		this.invoiceDate = invoiceDate;
		this.stockRemark = stockRemark;
		this.driveRoomNo = driveRoomNo;
		this.drivingMileage = drivingMileage;
		this.imagesUrls = imagesUrls;
		this.issuedNo = issuedNo;
		this.speedRatio = speedRatio;
		this.tireType = tireType;
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

	public String getVehicleSelfNo() {
		return this.vehicleSelfNo;
	}

	public void setVehicleSelfNo(String vehicleSelfNo) {
		this.vehicleSelfNo = vehicleSelfNo;
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

	public String getVehicleCardNo() {
		return this.vehicleCardNo;
	}

	public void setVehicleCardNo(String vehicleCardNo) {
		this.vehicleCardNo = vehicleCardNo;
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

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
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

	public Double getModifiedFee() {
		return this.modifiedFee;
	}

	public void setModifiedFee(Double modifiedFee) {
		this.modifiedFee = modifiedFee;
	}

	public Double getProfitReturn() {
		return this.profitReturn;
	}

	public void setProfitReturn(Double profitReturn) {
		this.profitReturn = profitReturn;
	}

	public Double getMaintainFee() {
		return this.maintainFee;
	}

	public void setMaintainFee(Double maintainFee) {
		this.maintainFee = maintainFee;
	}

	public Double getMoveStockCharge() {
		return this.moveStockCharge;
	}

	public void setMoveStockCharge(Double moveStockCharge) {
		this.moveStockCharge = moveStockCharge;
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

	public Double getOtherCost() {
		return this.otherCost;
	}

	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}

	public Double getCostVary() {
		return this.costVary;
	}

	public void setCostVary(Double costVary) {
		this.costVary = costVary;
	}

	public Double getSaleReferencePrice() {
		return this.saleReferencePrice;
	}

	public void setSaleReferencePrice(Double saleReferencePrice) {
		this.saleReferencePrice = saleReferencePrice;
	}

	public Boolean getIsMinSalePrice() {
		return this.isMinSalePrice;
	}

	public void setIsMinSalePrice(Boolean isMinSalePrice) {
		this.isMinSalePrice = isMinSalePrice;
	}

	public Boolean getIsLowestProfit() {
		return this.isLowestProfit;
	}

	public void setIsLowestProfit(Boolean isLowestProfit) {
		this.isLowestProfit = isLowestProfit;
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

	public Short getStockProperty() {
		return this.stockProperty;
	}

	public void setStockProperty(Short stockProperty) {
		this.stockProperty = stockProperty;
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

	public Timestamp getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
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

	public Double getDrivingMileage() {
		return this.drivingMileage;
	}

	public void setDrivingMileage(Double drivingMileage) {
		this.drivingMileage = drivingMileage;
	}

	public String getImagesUrls() {
		return this.imagesUrls;
	}

	public void setImagesUrls(String imagesUrls) {
		this.imagesUrls = imagesUrls;
	}

	public String getIssuedNo() {
		return this.issuedNo;
	}

	public void setIssuedNo(String issuedNo) {
		this.issuedNo = issuedNo;
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

}
