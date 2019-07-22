package cn.sf_soft.vehicle.stockbrowse.model;

import java.sql.Timestamp;

/**
 * VwVehicleStockId entity. @author MyEclipse Persistence Tools
 */

public class VwVehicleStock implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1406952656160126508L;
	// Fields

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
	private String driveRoomNo;
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
	private Short vehicleKind;
	private Timestamp validateTime;
	private String vehicleVnoNew;
	private String vehicleVinNew;
	private Double profitReturn;
	private Double modifiedFee;
	private String invoiceNo;
	private Double invoiceAmount;
	private String stockRemark;
	private Double maintainFee;
	private Timestamp futurePayDate;
	private String realStatus;
	private String inStockType_1;
	private String outStockType_1;
	private String stationName;
	private String vehicleKindMeaning;
	private String conversionStatusMeaning;
	private String vehicleId;
	private Short conversionStatus;
	private Double minSalePrice;
	private String oilCardRegisterNo;
	private String cardOutNo;
	private Double vehicleLowestProfit;
	private Double vehicleCostReference;
	private Double vehicleSalePriceReference;
	private Double vehicleLowestProfitReference;
	private Timestamp writeOffDate;
	private String payStatusMeaning;
	private String imagesUrls;
	private Double moveStockCharge;
	private Short vehicleType;
	private String vehicleTypeMeaning;
	private String propertyId;
	private String propertyNo;
	private String propertyName;
	private Timestamp invoiceDate;
	private String viPayTypeMeaning;
	private Short viPayType;
	private Integer stockAge;

	// Constructors

	/** default constructor */
	public VwVehicleStock() {
	}

	/** minimal constructor */
	public VwVehicleStock(String vehicleVin, String vehicleId) {
		this.vehicleVin = vehicleVin;
		this.vehicleId = vehicleId;
	}

	/** full constructor */
	public VwVehicleStock(String vehicleVin, String vnoId,
			String vehicleSalesCode, String vehicleVno, String vehicleName,
			String vehicleStrain, String vehicleColor,
			String vehicleEngineType, String vehicleEngineNo,
			String vehicleEligibleNo, String driveRoomNo,
			Timestamp vehicleOutFactoryTime, Double vehicleCost,
			Double vehicleCarriage, String inComment, String inStockNo,
			Timestamp inStockTime, String inStockType, String warehouseName,
			String warehouseId, String stationId, String supplierId,
			String supplierName, String carryNo, String saleContractNo,
			String saleContractCode, String customerId, String customerName,
			String seller, String outStockNo, Timestamp outStockTime,
			Short outStockType, Double vehiclePrice, Integer vehicleQuantity,
			String purchaseContractNo, String purchaseContractCode,
			String deliveryLocus, Short status, Short vehicleKind,
			Timestamp validateTime, String vehicleVnoNew, String vehicleVinNew,
			Double profitReturn, Double modifiedFee, String invoiceNo,
			Double invoiceAmount, String stockRemark, Double maintainFee,
			Timestamp futurePayDate, String realStatus, String inStockType_1,
			String outStockType_1, String stationName,
			String vehicleKindMeaning, String conversionStatusMeaning,
			String vehicleId, Short conversionStatus, Double minSalePrice,
			String oilCardRegisterNo, String cardOutNo,
			Double vehicleLowestProfit, Double vehicleCostReference,
			Double vehicleSalePriceReference,
			Double vehicleLowestProfitReference, Timestamp writeOffDate,
			String payStatusMeaning, String imagesUrls, Double moveStockCharge,
			Short vehicleType, String vehicleTypeMeaning, String propertyId,
			String propertyNo, String propertyName, Timestamp invoiceDate,
			String viPayTypeMeaning, Short viPayType, Integer stockAge) {
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
		this.driveRoomNo = driveRoomNo;
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
		this.vehicleKind = vehicleKind;
		this.validateTime = validateTime;
		this.vehicleVnoNew = vehicleVnoNew;
		this.vehicleVinNew = vehicleVinNew;
		this.profitReturn = profitReturn;
		this.modifiedFee = modifiedFee;
		this.invoiceNo = invoiceNo;
		this.invoiceAmount = invoiceAmount;
		this.stockRemark = stockRemark;
		this.maintainFee = maintainFee;
		this.futurePayDate = futurePayDate;
		this.realStatus = realStatus;
		this.inStockType_1 = inStockType_1;
		this.outStockType_1 = outStockType_1;
		this.stationName = stationName;
		this.vehicleKindMeaning = vehicleKindMeaning;
		this.conversionStatusMeaning = conversionStatusMeaning;
		this.vehicleId = vehicleId;
		this.conversionStatus = conversionStatus;
		this.minSalePrice = minSalePrice;
		this.oilCardRegisterNo = oilCardRegisterNo;
		this.cardOutNo = cardOutNo;
		this.vehicleLowestProfit = vehicleLowestProfit;
		this.vehicleCostReference = vehicleCostReference;
		this.vehicleSalePriceReference = vehicleSalePriceReference;
		this.vehicleLowestProfitReference = vehicleLowestProfitReference;
		this.writeOffDate = writeOffDate;
		this.payStatusMeaning = payStatusMeaning;
		this.imagesUrls = imagesUrls;
		this.moveStockCharge = moveStockCharge;
		this.vehicleType = vehicleType;
		this.vehicleTypeMeaning = vehicleTypeMeaning;
		this.propertyId = propertyId;
		this.propertyNo = propertyNo;
		this.propertyName = propertyName;
		this.invoiceDate = invoiceDate;
		this.viPayTypeMeaning = viPayTypeMeaning;
		this.viPayType = viPayType;
		this.stockAge = stockAge;
	}

	// Property accessors

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

	public String getDriveRoomNo() {
		return this.driveRoomNo;
	}

	public void setDriveRoomNo(String driveRoomNo) {
		this.driveRoomNo = driveRoomNo;
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

	public Short getVehicleKind() {
		return this.vehicleKind;
	}

	public void setVehicleKind(Short vehicleKind) {
		this.vehicleKind = vehicleKind;
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

	public String getRealStatus() {
		return this.realStatus;
	}

	public void setRealStatus(String realStatus) {
		this.realStatus = realStatus;
	}

	public String getInStockType_1() {
		return this.inStockType_1;
	}

	public void setInStockType_1(String inStockType_1) {
		this.inStockType_1 = inStockType_1;
	}

	public String getOutStockType_1() {
		return this.outStockType_1;
	}

	public void setOutStockType_1(String outStockType_1) {
		this.outStockType_1 = outStockType_1;
	}

	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getVehicleKindMeaning() {
		return this.vehicleKindMeaning;
	}

	public void setVehicleKindMeaning(String vehicleKindMeaning) {
		this.vehicleKindMeaning = vehicleKindMeaning;
	}

	public String getConversionStatusMeaning() {
		return this.conversionStatusMeaning;
	}

	public void setConversionStatusMeaning(String conversionStatusMeaning) {
		this.conversionStatusMeaning = conversionStatusMeaning;
	}

	public String getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Short getConversionStatus() {
		return this.conversionStatus;
	}

	public void setConversionStatus(Short conversionStatus) {
		this.conversionStatus = conversionStatus;
	}

	public Double getMinSalePrice() {
		return this.minSalePrice;
	}

	public void setMinSalePrice(Double minSalePrice) {
		this.minSalePrice = minSalePrice;
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

	public Double getVehicleLowestProfit() {
		return this.vehicleLowestProfit;
	}

	public void setVehicleLowestProfit(Double vehicleLowestProfit) {
		this.vehicleLowestProfit = vehicleLowestProfit;
	}

	public Double getVehicleCostReference() {
		return this.vehicleCostReference;
	}

	public void setVehicleCostReference(Double vehicleCostReference) {
		this.vehicleCostReference = vehicleCostReference;
	}

	public Double getVehicleSalePriceReference() {
		return this.vehicleSalePriceReference;
	}

	public void setVehicleSalePriceReference(Double vehicleSalePriceReference) {
		this.vehicleSalePriceReference = vehicleSalePriceReference;
	}

	public Double getVehicleLowestProfitReference() {
		return this.vehicleLowestProfitReference;
	}

	public void setVehicleLowestProfitReference(
			Double vehicleLowestProfitReference) {
		this.vehicleLowestProfitReference = vehicleLowestProfitReference;
	}

	public Timestamp getWriteOffDate() {
		return this.writeOffDate;
	}

	public void setWriteOffDate(Timestamp writeOffDate) {
		this.writeOffDate = writeOffDate;
	}

	public String getPayStatusMeaning() {
		return this.payStatusMeaning;
	}

	public void setPayStatusMeaning(String payStatusMeaning) {
		this.payStatusMeaning = payStatusMeaning;
	}

	public String getImagesUrls() {
		return this.imagesUrls;
	}

	public void setImagesUrls(String imagesUrls) {
		this.imagesUrls = imagesUrls;
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

	public String getVehicleTypeMeaning() {
		return this.vehicleTypeMeaning;
	}

	public void setVehicleTypeMeaning(String vehicleTypeMeaning) {
		this.vehicleTypeMeaning = vehicleTypeMeaning;
	}

	public String getPropertyId() {
		return this.propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyNo() {
		return this.propertyNo;
	}

	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Timestamp getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getViPayTypeMeaning() {
		return this.viPayTypeMeaning;
	}

	public void setViPayTypeMeaning(String viPayTypeMeaning) {
		this.viPayTypeMeaning = viPayTypeMeaning;
	}

	public Short getViPayType() {
		return this.viPayType;
	}

	public void setViPayType(Short viPayType) {
		this.viPayType = viPayType;
	}

	public Integer getStockAge() {
		return this.stockAge;
	}

	public void setStockAge(Integer stockAge) {
		this.stockAge = stockAge;
	}


}
