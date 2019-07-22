package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 合同变更明细 视图
 * 
 * @author caigx
 *
 */
public class VwVehicleSaleContractDetailVary implements java.io.Serializable {

	private static final long serialVersionUID = -5758170379098766284L;
	private String detailVaryId;
	private String documentNo;
	private String contractDetailId;
	private String contractNo;
	private String oriWarehouseName;
	private String oriVehicleId;
	private String oriVnoId;
	private String oriVehicleSalesCode;
	private String oriVehicleVno;
	private String oriVehicleName;
	private String oriVehicleStrain;
	private String oriVehicleColor;
	private String oriVehicleVin;
	private String oriVehicleEngineType;
	private String oriVehicleEngineNo;
	private String oriVehicleEligibleNo;
	private Timestamp oriVehicleOutFactoryTime;
	private Double oriVehiclePriceTotal;
	private Double oriVehiclePrice;
	private Double oriVehicleCost;
	private Double oriVehicleCarriage;
	private Integer oriVehicleQuantity;
	private String oriWarehouseId;
	private String oriVehicleVnoNew;
	private String oriVehicleVinNew;
	private Double oriMinSalePrice;
	private Double oriMinProfit;
	private Double oriInsuranceIncome;
	private Double oriInsurancePf;
	private Double oriInsuranceCost;
	private Double oriPresentIncome;
	private Double oriPresentPf;
	private Double oriPresentCost;
	private Double oriConversionIncome;
	private Double oriConversionPf;
	private Double oriConversionCost;
	private Double oriModifiedFee;
	private Double oriChargeIncome;
	private Double oriChargePf;
	private Double oriChargeCost;
	private Double oriVehicleProfit;
	private Double oriLargessAmount;
	private String oriDeliverAddress;
	private Timestamp oriPlanDeliverTime;
	private Timestamp oriRealDeliverTime;
	private String oriVehicleCardNo;
	private Timestamp oriVehicleCardTime;
	private String oriInsuranceNo;
	private String oriInsuranceCompanyName;
	private String oriInsuranceCompanyNo;
	private String oriInsuranceCompanyId;
	private String oriDriveRoomNo;
	private Double oriMaintainFee;
	private Double oriLargessPart;
	private Double oriLargessService;
	private String oriCustomerIdProfit;
	private String oriCustomerNoProfit;
	private String oriCustomerNameProfit;
	private Double oriVehicleCostRef;
	private String oriVehicleNameNew;
	private String oriVehicleEligibleNoNew;
	private String oriVnoIdNew;
	private Boolean oriIsContainInsuranceCost;
	private String oriProfession;
	private Boolean oriTicketOutStockFlag;
	private Double oriTicketOutStockAmount;
	private Double oriBelongToSupplierRebate;
	private Double oriDiscountAmount;
	private Double oriTaxationAmount;
	private Double oriProfitReturn;
	private Double oriOtherCost;
	private String oriSubjectMatter;
	private String oriTransportRoutes;
	private String oriStartPoint;
	private String oriWaysPoint;
	private String oriEndPoint;
	private String warehouseName;
	private String vehicleId;
	private String vnoId;
	private String vehicleSalesCode;
	private String vehicleVno;
	private String vehicleName;
	private String vehicleStrain;
	private String vehicleColor;
	private String vehicleVin;
	private String vehicleEngineType;
	private String vehicleEngineNo;
	private String vehicleEligibleNo;
	private Timestamp vehicleOutFactoryTime;
	private Double vehiclePriceTotal;
	private Double vehiclePrice;
	private Double vehicleCost;
	private Double vehicleCarriage;
	private Integer vehicleQuantity;
	private String warehouseId;
	private String vehicleVnoNew;
	private String vehicleVinNew;
	private Double minSalePrice;
	private Double minProfit;
	private Double insuranceIncome;
	private Double insurancePf;
	private Double insuranceCost;
	private Double presentIncome;
	private Double presentPf;
	private Double presentCost;
	private Double conversionIncome;
	private Double conversionPf;
	private Double conversionCost;
	private Double modifiedFee;
	private Double chargeIncome;
	private Double chargePf;
	private Double chargeCost;
	private Double vehicleProfit;
	private Double largessAmount;
	private String deliverAddress;
	private Timestamp planDeliverTime;
	private Timestamp realDeliverTime;
	private String vehicleCardNo;
	private Timestamp vehicleCardTime;
	private String insuranceNo;
	private String insuranceCompanyName;
	private String insuranceCompanyNo;
	private String insuranceCompanyId;
	private String driveRoomNo;
	private Double maintainFee;
	private Double largessPart;
	private Double largessService;
	private String customerIdProfit;
	private String customerNoProfit;
	private String customerNameProfit;
	private Double vehicleCostRef;
	private String vehicleNameNew;
	private String vehicleEligibleNoNew;
	private String vnoIdNew;
	private Boolean isContainInsuranceCost;
	private String profession;
	private Boolean ticketOutStockFlag;
	private Double ticketOutStockAmount;
	private Double belongToSupplierRebate;
	private Double discountAmount;
	private Double taxationAmount;
	private Double profitReturn;
	private Double otherCost;
	private String subjectMatter;
	private String transportRoutes;
	private String startPoint;
	private String waysPoint;
	private String endPoint;
	private Short abortStatus;
	private String abortComment;
	private String vehicleComment;
	private String relationDetailId;
	private String oriRelationDetailId;
	private Double modifiedFeeAmt;
	private Double moveStockCharge;
	private String purchaseContractNo;
	private String abortStatusMeaning;
	private Double chargeLoanMoney;
	private Double chargeMoneyLd;
	private Double loanAmountTot;
	private Double loanAmountLv;
	private Double chargeFirstPay;
	private String oriTransportRoutes_1;
	private String transportRoutes_1;
	private Double oilCardProfit;
	private Double profitReturnPf;
	private Double profitReturnReal;
	private Double profitPf;
	private Short vehicleKind;
	private String vehicleKindMeaning;
	private Double profitMinRef;
	private Double priceSaleRef;
	private String relationVehicleVin;
	private String oriRelationVehicleVin;
	private Integer isBelowPrice;
	private Integer isBelowProfit;

	// Constructors

	public VwVehicleSaleContractDetailVary() {
	}

	// Property accessors

	public String getDetailVaryId() {
		return this.detailVaryId;
	}

	public void setDetailVaryId(String detailVaryId) {
		this.detailVaryId = detailVaryId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getContractDetailId() {
		return this.contractDetailId;
	}

	public void setContractDetailId(String contractDetailId) {
		this.contractDetailId = contractDetailId;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getOriWarehouseName() {
		return this.oriWarehouseName;
	}

	public void setOriWarehouseName(String oriWarehouseName) {
		this.oriWarehouseName = oriWarehouseName;
	}

	public String getOriVehicleId() {
		return this.oriVehicleId;
	}

	public void setOriVehicleId(String oriVehicleId) {
		this.oriVehicleId = oriVehicleId;
	}

	public String getOriVnoId() {
		return this.oriVnoId;
	}

	public void setOriVnoId(String oriVnoId) {
		this.oriVnoId = oriVnoId;
	}

	public String getOriVehicleSalesCode() {
		return this.oriVehicleSalesCode;
	}

	public void setOriVehicleSalesCode(String oriVehicleSalesCode) {
		this.oriVehicleSalesCode = oriVehicleSalesCode;
	}

	public String getOriVehicleVno() {
		return this.oriVehicleVno;
	}

	public void setOriVehicleVno(String oriVehicleVno) {
		this.oriVehicleVno = oriVehicleVno;
	}

	public String getOriVehicleName() {
		return this.oriVehicleName;
	}

	public void setOriVehicleName(String oriVehicleName) {
		this.oriVehicleName = oriVehicleName;
	}

	public String getOriVehicleStrain() {
		return this.oriVehicleStrain;
	}

	public void setOriVehicleStrain(String oriVehicleStrain) {
		this.oriVehicleStrain = oriVehicleStrain;
	}

	public String getOriVehicleColor() {
		return this.oriVehicleColor;
	}

	public void setOriVehicleColor(String oriVehicleColor) {
		this.oriVehicleColor = oriVehicleColor;
	}

	public String getOriVehicleVin() {
		return this.oriVehicleVin;
	}

	public void setOriVehicleVin(String oriVehicleVin) {
		this.oriVehicleVin = oriVehicleVin;
	}

	public String getOriVehicleEngineType() {
		return this.oriVehicleEngineType;
	}

	public void setOriVehicleEngineType(String oriVehicleEngineType) {
		this.oriVehicleEngineType = oriVehicleEngineType;
	}

	public String getOriVehicleEngineNo() {
		return this.oriVehicleEngineNo;
	}

	public void setOriVehicleEngineNo(String oriVehicleEngineNo) {
		this.oriVehicleEngineNo = oriVehicleEngineNo;
	}

	public String getOriVehicleEligibleNo() {
		return this.oriVehicleEligibleNo;
	}

	public void setOriVehicleEligibleNo(String oriVehicleEligibleNo) {
		this.oriVehicleEligibleNo = oriVehicleEligibleNo;
	}

	public Timestamp getOriVehicleOutFactoryTime() {
		return this.oriVehicleOutFactoryTime;
	}

	public void setOriVehicleOutFactoryTime(Timestamp oriVehicleOutFactoryTime) {
		this.oriVehicleOutFactoryTime = oriVehicleOutFactoryTime;
	}

	public Double getOriVehiclePriceTotal() {
		return this.oriVehiclePriceTotal;
	}

	public void setOriVehiclePriceTotal(Double oriVehiclePriceTotal) {
		this.oriVehiclePriceTotal = oriVehiclePriceTotal;
	}

	public Double getOriVehiclePrice() {
		return this.oriVehiclePrice;
	}

	public void setOriVehiclePrice(Double oriVehiclePrice) {
		this.oriVehiclePrice = oriVehiclePrice;
	}

	public Double getOriVehicleCost() {
		return this.oriVehicleCost;
	}

	public void setOriVehicleCost(Double oriVehicleCost) {
		this.oriVehicleCost = oriVehicleCost;
	}

	public Double getOriVehicleCarriage() {
		return this.oriVehicleCarriage;
	}

	public void setOriVehicleCarriage(Double oriVehicleCarriage) {
		this.oriVehicleCarriage = oriVehicleCarriage;
	}

	public Integer getOriVehicleQuantity() {
		return this.oriVehicleQuantity;
	}

	public void setOriVehicleQuantity(Integer oriVehicleQuantity) {
		this.oriVehicleQuantity = oriVehicleQuantity;
	}

	public String getOriWarehouseId() {
		return this.oriWarehouseId;
	}

	public void setOriWarehouseId(String oriWarehouseId) {
		this.oriWarehouseId = oriWarehouseId;
	}

	public String getOriVehicleVnoNew() {
		return this.oriVehicleVnoNew;
	}

	public void setOriVehicleVnoNew(String oriVehicleVnoNew) {
		this.oriVehicleVnoNew = oriVehicleVnoNew;
	}

	public String getOriVehicleVinNew() {
		return this.oriVehicleVinNew;
	}

	public void setOriVehicleVinNew(String oriVehicleVinNew) {
		this.oriVehicleVinNew = oriVehicleVinNew;
	}

	public Double getOriMinSalePrice() {
		return this.oriMinSalePrice;
	}

	public void setOriMinSalePrice(Double oriMinSalePrice) {
		this.oriMinSalePrice = oriMinSalePrice;
	}

	public Double getOriMinProfit() {
		return this.oriMinProfit;
	}

	public void setOriMinProfit(Double oriMinProfit) {
		this.oriMinProfit = oriMinProfit;
	}

	public Double getOriInsuranceIncome() {
		return this.oriInsuranceIncome;
	}

	public void setOriInsuranceIncome(Double oriInsuranceIncome) {
		this.oriInsuranceIncome = oriInsuranceIncome;
	}

	public Double getOriInsurancePf() {
		return this.oriInsurancePf;
	}

	public void setOriInsurancePf(Double oriInsurancePf) {
		this.oriInsurancePf = oriInsurancePf;
	}

	public Double getOriInsuranceCost() {
		return this.oriInsuranceCost;
	}

	public void setOriInsuranceCost(Double oriInsuranceCost) {
		this.oriInsuranceCost = oriInsuranceCost;
	}

	public Double getOriPresentIncome() {
		return this.oriPresentIncome;
	}

	public void setOriPresentIncome(Double oriPresentIncome) {
		this.oriPresentIncome = oriPresentIncome;
	}

	public Double getOriPresentPf() {
		return this.oriPresentPf;
	}

	public void setOriPresentPf(Double oriPresentPf) {
		this.oriPresentPf = oriPresentPf;
	}

	public Double getOriPresentCost() {
		return this.oriPresentCost;
	}

	public void setOriPresentCost(Double oriPresentCost) {
		this.oriPresentCost = oriPresentCost;
	}

	public Double getOriConversionIncome() {
		return this.oriConversionIncome;
	}

	public void setOriConversionIncome(Double oriConversionIncome) {
		this.oriConversionIncome = oriConversionIncome;
	}

	public Double getOriConversionPf() {
		return this.oriConversionPf;
	}

	public void setOriConversionPf(Double oriConversionPf) {
		this.oriConversionPf = oriConversionPf;
	}

	public Double getOriConversionCost() {
		return this.oriConversionCost;
	}

	public void setOriConversionCost(Double oriConversionCost) {
		this.oriConversionCost = oriConversionCost;
	}

	public Double getOriModifiedFee() {
		return this.oriModifiedFee;
	}

	public void setOriModifiedFee(Double oriModifiedFee) {
		this.oriModifiedFee = oriModifiedFee;
	}

	public Double getOriChargeIncome() {
		return this.oriChargeIncome;
	}

	public void setOriChargeIncome(Double oriChargeIncome) {
		this.oriChargeIncome = oriChargeIncome;
	}

	public Double getOriChargePf() {
		return this.oriChargePf;
	}

	public void setOriChargePf(Double oriChargePf) {
		this.oriChargePf = oriChargePf;
	}

	public Double getOriChargeCost() {
		return this.oriChargeCost;
	}

	public void setOriChargeCost(Double oriChargeCost) {
		this.oriChargeCost = oriChargeCost;
	}

	public Double getOriVehicleProfit() {
		return this.oriVehicleProfit;
	}

	public void setOriVehicleProfit(Double oriVehicleProfit) {
		this.oriVehicleProfit = oriVehicleProfit;
	}

	public Double getOriLargessAmount() {
		return this.oriLargessAmount;
	}

	public void setOriLargessAmount(Double oriLargessAmount) {
		this.oriLargessAmount = oriLargessAmount;
	}

	public String getOriDeliverAddress() {
		return this.oriDeliverAddress;
	}

	public void setOriDeliverAddress(String oriDeliverAddress) {
		this.oriDeliverAddress = oriDeliverAddress;
	}

	public Timestamp getOriPlanDeliverTime() {
		return this.oriPlanDeliverTime;
	}

	public void setOriPlanDeliverTime(Timestamp oriPlanDeliverTime) {
		this.oriPlanDeliverTime = oriPlanDeliverTime;
	}

	public Timestamp getOriRealDeliverTime() {
		return this.oriRealDeliverTime;
	}

	public void setOriRealDeliverTime(Timestamp oriRealDeliverTime) {
		this.oriRealDeliverTime = oriRealDeliverTime;
	}

	public String getOriVehicleCardNo() {
		return this.oriVehicleCardNo;
	}

	public void setOriVehicleCardNo(String oriVehicleCardNo) {
		this.oriVehicleCardNo = oriVehicleCardNo;
	}

	public Timestamp getOriVehicleCardTime() {
		return this.oriVehicleCardTime;
	}

	public void setOriVehicleCardTime(Timestamp oriVehicleCardTime) {
		this.oriVehicleCardTime = oriVehicleCardTime;
	}

	public String getOriInsuranceNo() {
		return this.oriInsuranceNo;
	}

	public void setOriInsuranceNo(String oriInsuranceNo) {
		this.oriInsuranceNo = oriInsuranceNo;
	}

	public String getOriInsuranceCompanyName() {
		return this.oriInsuranceCompanyName;
	}

	public void setOriInsuranceCompanyName(String oriInsuranceCompanyName) {
		this.oriInsuranceCompanyName = oriInsuranceCompanyName;
	}

	public String getOriInsuranceCompanyNo() {
		return this.oriInsuranceCompanyNo;
	}

	public void setOriInsuranceCompanyNo(String oriInsuranceCompanyNo) {
		this.oriInsuranceCompanyNo = oriInsuranceCompanyNo;
	}

	public String getOriInsuranceCompanyId() {
		return this.oriInsuranceCompanyId;
	}

	public void setOriInsuranceCompanyId(String oriInsuranceCompanyId) {
		this.oriInsuranceCompanyId = oriInsuranceCompanyId;
	}

	public String getOriDriveRoomNo() {
		return this.oriDriveRoomNo;
	}

	public void setOriDriveRoomNo(String oriDriveRoomNo) {
		this.oriDriveRoomNo = oriDriveRoomNo;
	}

	public Double getOriMaintainFee() {
		return this.oriMaintainFee;
	}

	public void setOriMaintainFee(Double oriMaintainFee) {
		this.oriMaintainFee = oriMaintainFee;
	}

	public Double getOriLargessPart() {
		return this.oriLargessPart;
	}

	public void setOriLargessPart(Double oriLargessPart) {
		this.oriLargessPart = oriLargessPart;
	}

	public Double getOriLargessService() {
		return this.oriLargessService;
	}

	public void setOriLargessService(Double oriLargessService) {
		this.oriLargessService = oriLargessService;
	}

	public String getOriCustomerIdProfit() {
		return this.oriCustomerIdProfit;
	}

	public void setOriCustomerIdProfit(String oriCustomerIdProfit) {
		this.oriCustomerIdProfit = oriCustomerIdProfit;
	}

	public String getOriCustomerNoProfit() {
		return this.oriCustomerNoProfit;
	}

	public void setOriCustomerNoProfit(String oriCustomerNoProfit) {
		this.oriCustomerNoProfit = oriCustomerNoProfit;
	}

	public String getOriCustomerNameProfit() {
		return this.oriCustomerNameProfit;
	}

	public void setOriCustomerNameProfit(String oriCustomerNameProfit) {
		this.oriCustomerNameProfit = oriCustomerNameProfit;
	}

	public Double getOriVehicleCostRef() {
		return this.oriVehicleCostRef;
	}

	public void setOriVehicleCostRef(Double oriVehicleCostRef) {
		this.oriVehicleCostRef = oriVehicleCostRef;
	}

	public String getOriVehicleNameNew() {
		return this.oriVehicleNameNew;
	}

	public void setOriVehicleNameNew(String oriVehicleNameNew) {
		this.oriVehicleNameNew = oriVehicleNameNew;
	}

	public String getOriVehicleEligibleNoNew() {
		return this.oriVehicleEligibleNoNew;
	}

	public void setOriVehicleEligibleNoNew(String oriVehicleEligibleNoNew) {
		this.oriVehicleEligibleNoNew = oriVehicleEligibleNoNew;
	}

	public String getOriVnoIdNew() {
		return this.oriVnoIdNew;
	}

	public void setOriVnoIdNew(String oriVnoIdNew) {
		this.oriVnoIdNew = oriVnoIdNew;
	}

	public Boolean getOriIsContainInsuranceCost() {
		return this.oriIsContainInsuranceCost;
	}

	public void setOriIsContainInsuranceCost(Boolean oriIsContainInsuranceCost) {
		this.oriIsContainInsuranceCost = oriIsContainInsuranceCost;
	}

	public String getOriProfession() {
		return this.oriProfession;
	}

	public void setOriProfession(String oriProfession) {
		this.oriProfession = oriProfession;
	}

	public Boolean getOriTicketOutStockFlag() {
		return this.oriTicketOutStockFlag;
	}

	public void setOriTicketOutStockFlag(Boolean oriTicketOutStockFlag) {
		this.oriTicketOutStockFlag = oriTicketOutStockFlag;
	}

	public Double getOriTicketOutStockAmount() {
		return this.oriTicketOutStockAmount;
	}

	public void setOriTicketOutStockAmount(Double oriTicketOutStockAmount) {
		this.oriTicketOutStockAmount = oriTicketOutStockAmount;
	}

	public Double getOriBelongToSupplierRebate() {
		return this.oriBelongToSupplierRebate;
	}

	public void setOriBelongToSupplierRebate(Double oriBelongToSupplierRebate) {
		this.oriBelongToSupplierRebate = oriBelongToSupplierRebate;
	}

	public Double getOriDiscountAmount() {
		return this.oriDiscountAmount;
	}

	public void setOriDiscountAmount(Double oriDiscountAmount) {
		this.oriDiscountAmount = oriDiscountAmount;
	}

	public Double getOriTaxationAmount() {
		return this.oriTaxationAmount;
	}

	public void setOriTaxationAmount(Double oriTaxationAmount) {
		this.oriTaxationAmount = oriTaxationAmount;
	}

	public Double getOriProfitReturn() {
		return this.oriProfitReturn;
	}

	public void setOriProfitReturn(Double oriProfitReturn) {
		this.oriProfitReturn = oriProfitReturn;
	}

	public Double getOriOtherCost() {
		return this.oriOtherCost;
	}

	public void setOriOtherCost(Double oriOtherCost) {
		this.oriOtherCost = oriOtherCost;
	}

	public String getOriSubjectMatter() {
		return this.oriSubjectMatter;
	}

	public void setOriSubjectMatter(String oriSubjectMatter) {
		this.oriSubjectMatter = oriSubjectMatter;
	}

	public String getOriTransportRoutes() {
		return this.oriTransportRoutes;
	}

	public void setOriTransportRoutes(String oriTransportRoutes) {
		this.oriTransportRoutes = oriTransportRoutes;
	}

	public String getOriStartPoint() {
		return this.oriStartPoint;
	}

	public void setOriStartPoint(String oriStartPoint) {
		this.oriStartPoint = oriStartPoint;
	}

	public String getOriWaysPoint() {
		return this.oriWaysPoint;
	}

	public void setOriWaysPoint(String oriWaysPoint) {
		this.oriWaysPoint = oriWaysPoint;
	}

	public String getOriEndPoint() {
		return this.oriEndPoint;
	}

	public void setOriEndPoint(String oriEndPoint) {
		this.oriEndPoint = oriEndPoint;
	}

	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
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

	public String getVehicleVin() {
		return this.vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
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

	public Double getVehiclePriceTotal() {
		return this.vehiclePriceTotal;
	}

	public void setVehiclePriceTotal(Double vehiclePriceTotal) {
		this.vehiclePriceTotal = vehiclePriceTotal;
	}

	public Double getVehiclePrice() {
		return this.vehiclePrice;
	}

	public void setVehiclePrice(Double vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
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

	public Integer getVehicleQuantity() {
		return this.vehicleQuantity;
	}

	public void setVehicleQuantity(Integer vehicleQuantity) {
		this.vehicleQuantity = vehicleQuantity;
	}

	public String getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
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

	public Double getMinSalePrice() {
		return this.minSalePrice;
	}

	public void setMinSalePrice(Double minSalePrice) {
		this.minSalePrice = minSalePrice;
	}

	public Double getMinProfit() {
		return this.minProfit;
	}

	public void setMinProfit(Double minProfit) {
		this.minProfit = minProfit;
	}

	public Double getInsuranceIncome() {
		return this.insuranceIncome;
	}

	public void setInsuranceIncome(Double insuranceIncome) {
		this.insuranceIncome = insuranceIncome;
	}

	public Double getInsurancePf() {
		return this.insurancePf;
	}

	public void setInsurancePf(Double insurancePf) {
		this.insurancePf = insurancePf;
	}

	public Double getInsuranceCost() {
		return this.insuranceCost;
	}

	public void setInsuranceCost(Double insuranceCost) {
		this.insuranceCost = insuranceCost;
	}

	public Double getPresentIncome() {
		return this.presentIncome;
	}

	public void setPresentIncome(Double presentIncome) {
		this.presentIncome = presentIncome;
	}

	public Double getPresentPf() {
		return this.presentPf;
	}

	public void setPresentPf(Double presentPf) {
		this.presentPf = presentPf;
	}

	public Double getPresentCost() {
		return this.presentCost;
	}

	public void setPresentCost(Double presentCost) {
		this.presentCost = presentCost;
	}

	public Double getConversionIncome() {
		return this.conversionIncome;
	}

	public void setConversionIncome(Double conversionIncome) {
		this.conversionIncome = conversionIncome;
	}

	public Double getConversionPf() {
		return this.conversionPf;
	}

	public void setConversionPf(Double conversionPf) {
		this.conversionPf = conversionPf;
	}

	public Double getConversionCost() {
		return this.conversionCost;
	}

	public void setConversionCost(Double conversionCost) {
		this.conversionCost = conversionCost;
	}

	public Double getModifiedFee() {
		return this.modifiedFee;
	}

	public void setModifiedFee(Double modifiedFee) {
		this.modifiedFee = modifiedFee;
	}

	public Double getChargeIncome() {
		return this.chargeIncome;
	}

	public void setChargeIncome(Double chargeIncome) {
		this.chargeIncome = chargeIncome;
	}

	public Double getChargePf() {
		return this.chargePf;
	}

	public void setChargePf(Double chargePf) {
		this.chargePf = chargePf;
	}

	public Double getChargeCost() {
		return this.chargeCost;
	}

	public void setChargeCost(Double chargeCost) {
		this.chargeCost = chargeCost;
	}

	public Double getVehicleProfit() {
		return this.vehicleProfit;
	}

	public void setVehicleProfit(Double vehicleProfit) {
		this.vehicleProfit = vehicleProfit;
	}

	public Double getLargessAmount() {
		return this.largessAmount;
	}

	public void setLargessAmount(Double largessAmount) {
		this.largessAmount = largessAmount;
	}

	public String getDeliverAddress() {
		return this.deliverAddress;
	}

	public void setDeliverAddress(String deliverAddress) {
		this.deliverAddress = deliverAddress;
	}

	public Timestamp getPlanDeliverTime() {
		return this.planDeliverTime;
	}

	public void setPlanDeliverTime(Timestamp planDeliverTime) {
		this.planDeliverTime = planDeliverTime;
	}

	public Timestamp getRealDeliverTime() {
		return this.realDeliverTime;
	}

	public void setRealDeliverTime(Timestamp realDeliverTime) {
		this.realDeliverTime = realDeliverTime;
	}

	public String getVehicleCardNo() {
		return this.vehicleCardNo;
	}

	public void setVehicleCardNo(String vehicleCardNo) {
		this.vehicleCardNo = vehicleCardNo;
	}

	public Timestamp getVehicleCardTime() {
		return this.vehicleCardTime;
	}

	public void setVehicleCardTime(Timestamp vehicleCardTime) {
		this.vehicleCardTime = vehicleCardTime;
	}

	public String getInsuranceNo() {
		return this.insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getInsuranceCompanyName() {
		return this.insuranceCompanyName;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}

	public String getInsuranceCompanyNo() {
		return this.insuranceCompanyNo;
	}

	public void setInsuranceCompanyNo(String insuranceCompanyNo) {
		this.insuranceCompanyNo = insuranceCompanyNo;
	}

	public String getInsuranceCompanyId() {
		return this.insuranceCompanyId;
	}

	public void setInsuranceCompanyId(String insuranceCompanyId) {
		this.insuranceCompanyId = insuranceCompanyId;
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

	public Double getLargessPart() {
		return this.largessPart;
	}

	public void setLargessPart(Double largessPart) {
		this.largessPart = largessPart;
	}

	public Double getLargessService() {
		return this.largessService;
	}

	public void setLargessService(Double largessService) {
		this.largessService = largessService;
	}

	public String getCustomerIdProfit() {
		return this.customerIdProfit;
	}

	public void setCustomerIdProfit(String customerIdProfit) {
		this.customerIdProfit = customerIdProfit;
	}

	public String getCustomerNoProfit() {
		return this.customerNoProfit;
	}

	public void setCustomerNoProfit(String customerNoProfit) {
		this.customerNoProfit = customerNoProfit;
	}

	public String getCustomerNameProfit() {
		return this.customerNameProfit;
	}

	public void setCustomerNameProfit(String customerNameProfit) {
		this.customerNameProfit = customerNameProfit;
	}

	public Double getVehicleCostRef() {
		return this.vehicleCostRef;
	}

	public void setVehicleCostRef(Double vehicleCostRef) {
		this.vehicleCostRef = vehicleCostRef;
	}

	public String getVehicleNameNew() {
		return this.vehicleNameNew;
	}

	public void setVehicleNameNew(String vehicleNameNew) {
		this.vehicleNameNew = vehicleNameNew;
	}

	public String getVehicleEligibleNoNew() {
		return this.vehicleEligibleNoNew;
	}

	public void setVehicleEligibleNoNew(String vehicleEligibleNoNew) {
		this.vehicleEligibleNoNew = vehicleEligibleNoNew;
	}

	public String getVnoIdNew() {
		return this.vnoIdNew;
	}

	public void setVnoIdNew(String vnoIdNew) {
		this.vnoIdNew = vnoIdNew;
	}

	public Boolean getIsContainInsuranceCost() {
		return this.isContainInsuranceCost;
	}

	public void setIsContainInsuranceCost(Boolean isContainInsuranceCost) {
		this.isContainInsuranceCost = isContainInsuranceCost;
	}

	public String getProfession() {
		return this.profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Boolean getTicketOutStockFlag() {
		return this.ticketOutStockFlag;
	}

	public void setTicketOutStockFlag(Boolean ticketOutStockFlag) {
		this.ticketOutStockFlag = ticketOutStockFlag;
	}

	public Double getTicketOutStockAmount() {
		return this.ticketOutStockAmount;
	}

	public void setTicketOutStockAmount(Double ticketOutStockAmount) {
		this.ticketOutStockAmount = ticketOutStockAmount;
	}

	public Double getBelongToSupplierRebate() {
		return this.belongToSupplierRebate;
	}

	public void setBelongToSupplierRebate(Double belongToSupplierRebate) {
		this.belongToSupplierRebate = belongToSupplierRebate;
	}

	public Double getDiscountAmount() {
		return this.discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getTaxationAmount() {
		return this.taxationAmount;
	}

	public void setTaxationAmount(Double taxationAmount) {
		this.taxationAmount = taxationAmount;
	}

	public Double getProfitReturn() {
		return this.profitReturn;
	}

	public void setProfitReturn(Double profitReturn) {
		this.profitReturn = profitReturn;
	}

	public Double getOtherCost() {
		return this.otherCost;
	}

	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}

	public String getSubjectMatter() {
		return this.subjectMatter;
	}

	public void setSubjectMatter(String subjectMatter) {
		this.subjectMatter = subjectMatter;
	}

	public String getTransportRoutes() {
		return this.transportRoutes;
	}

	public void setTransportRoutes(String transportRoutes) {
		this.transportRoutes = transportRoutes;
	}

	public String getStartPoint() {
		return this.startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getWaysPoint() {
		return this.waysPoint;
	}

	public void setWaysPoint(String waysPoint) {
		this.waysPoint = waysPoint;
	}

	public String getEndPoint() {
		return this.endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public Short getAbortStatus() {
		return this.abortStatus;
	}

	public void setAbortStatus(Short abortStatus) {
		this.abortStatus = abortStatus;
	}

	public String getAbortComment() {
		return this.abortComment;
	}

	public void setAbortComment(String abortComment) {
		this.abortComment = abortComment;
	}

	public String getVehicleComment() {
		return this.vehicleComment;
	}

	public void setVehicleComment(String vehicleComment) {
		this.vehicleComment = vehicleComment;
	}

	public String getRelationDetailId() {
		return this.relationDetailId;
	}

	public void setRelationDetailId(String relationDetailId) {
		this.relationDetailId = relationDetailId;
	}

	public String getOriRelationDetailId() {
		return this.oriRelationDetailId;
	}

	public void setOriRelationDetailId(String oriRelationDetailId) {
		this.oriRelationDetailId = oriRelationDetailId;
	}

	public Double getModifiedFeeAmt() {
		return this.modifiedFeeAmt;
	}

	public void setModifiedFeeAmt(Double modifiedFeeAmt) {
		this.modifiedFeeAmt = modifiedFeeAmt;
	}

	public Double getMoveStockCharge() {
		return this.moveStockCharge;
	}

	public void setMoveStockCharge(Double moveStockCharge) {
		this.moveStockCharge = moveStockCharge;
	}

	public String getPurchaseContractNo() {
		return this.purchaseContractNo;
	}

	public void setPurchaseContractNo(String purchaseContractNo) {
		this.purchaseContractNo = purchaseContractNo;
	}

	public String getAbortStatusMeaning() {
		return this.abortStatusMeaning;
	}

	public void setAbortStatusMeaning(String abortStatusMeaning) {
		this.abortStatusMeaning = abortStatusMeaning;
	}

	public Double getChargeLoanMoney() {
		return this.chargeLoanMoney;
	}

	public void setChargeLoanMoney(Double chargeLoanMoney) {
		this.chargeLoanMoney = chargeLoanMoney;
	}

	public Double getChargeMoneyLd() {
		return this.chargeMoneyLd;
	}

	public void setChargeMoneyLd(Double chargeMoneyLd) {
		this.chargeMoneyLd = chargeMoneyLd;
	}

	public Double getLoanAmountTot() {
		return this.loanAmountTot;
	}

	public void setLoanAmountTot(Double loanAmountTot) {
		this.loanAmountTot = loanAmountTot;
	}

	public Double getLoanAmountLv() {
		return this.loanAmountLv;
	}

	public void setLoanAmountLv(Double loanAmountLv) {
		this.loanAmountLv = loanAmountLv;
	}

	public Double getChargeFirstPay() {
		return this.chargeFirstPay;
	}

	public void setChargeFirstPay(Double chargeFirstPay) {
		this.chargeFirstPay = chargeFirstPay;
	}

	public String getOriTransportRoutes_1() {
		return this.oriTransportRoutes_1;
	}

	public void setOriTransportRoutes_1(String oriTransportRoutes_1) {
		this.oriTransportRoutes_1 = oriTransportRoutes_1;
	}

	public String getTransportRoutes_1() {
		return this.transportRoutes_1;
	}

	public void setTransportRoutes_1(String transportRoutes_1) {
		this.transportRoutes_1 = transportRoutes_1;
	}

	public Double getOilCardProfit() {
		return this.oilCardProfit;
	}

	public void setOilCardProfit(Double oilCardProfit) {
		this.oilCardProfit = oilCardProfit;
	}

	public Double getProfitReturnPf() {
		return this.profitReturnPf;
	}

	public void setProfitReturnPf(Double profitReturnPf) {
		this.profitReturnPf = profitReturnPf;
	}

	public Double getProfitReturnReal() {
		return this.profitReturnReal;
	}

	public void setProfitReturnReal(Double profitReturnReal) {
		this.profitReturnReal = profitReturnReal;
	}

	public Double getProfitPf() {
		return this.profitPf;
	}

	public void setProfitPf(Double profitPf) {
		this.profitPf = profitPf;
	}

	public Short getVehicleKind() {
		return this.vehicleKind;
	}

	public void setVehicleKind(Short vehicleKind) {
		this.vehicleKind = vehicleKind;
	}

	public String getVehicleKindMeaning() {
		return this.vehicleKindMeaning;
	}

	public void setVehicleKindMeaning(String vehicleKindMeaning) {
		this.vehicleKindMeaning = vehicleKindMeaning;
	}

	public Double getProfitMinRef() {
		return this.profitMinRef;
	}

	public void setProfitMinRef(Double profitMinRef) {
		this.profitMinRef = profitMinRef;
	}

	public Double getPriceSaleRef() {
		return this.priceSaleRef;
	}

	public void setPriceSaleRef(Double priceSaleRef) {
		this.priceSaleRef = priceSaleRef;
	}

	public String getRelationVehicleVin() {
		return this.relationVehicleVin;
	}

	public void setRelationVehicleVin(String relationVehicleVin) {
		this.relationVehicleVin = relationVehicleVin;
	}

	public String getOriRelationVehicleVin() {
		return this.oriRelationVehicleVin;
	}

	public void setOriRelationVehicleVin(String oriRelationVehicleVin) {
		this.oriRelationVehicleVin = oriRelationVehicleVin;
	}

	public Integer getIsBelowPrice() {
		return this.isBelowPrice;
	}

	public void setIsBelowPrice(Integer isBelowPrice) {
		this.isBelowPrice = isBelowPrice;
	}

	public Integer getIsBelowProfit() {
		return this.isBelowProfit;
	}

	public void setIsBelowProfit(Integer isBelowProfit) {
		this.isBelowProfit = isBelowProfit;
	}

}
