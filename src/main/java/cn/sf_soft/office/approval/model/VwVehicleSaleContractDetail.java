package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 车辆合同 明细-视图
 * 
 * @author caigx
 *
 */
public class VwVehicleSaleContractDetail implements java.io.Serializable {

	private static final long serialVersionUID = -462888040331435718L;
	private String contractDetailId;
	private String contractNo;
	private String outStockNo;
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
	private Double vehicleCostRef;
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
	private String approverName;
	private Timestamp approveTime;
	private String approveComment;
	private Short approveStatus;
	private String vehicleComment;
	private Short paymentStatus;
	private String driveRoomNo;
	private Double maintainFee;
	private Double largessPart;
	private Double largessService;
	private String customerIdProfit;
	private String customerNoProfit;
	private String customerNameProfit;
	private String belongToSupplierId;
	private String belongToSupplierNo;
	private String belongToSupplierName;
	private Short abortStatus;
	private Double invoicePrice;
	private String vehicleNameNew;
	private String vehicleEligibleNoNew;
	private String vnoIdNew;
	private Boolean isContainInsuranceCost;
	private String profession;
	private String imagesUrls;
	private Boolean ticketOutStockFlag;
	private Double ticketOutStockAmount;
	private Double belongToSupplierRebate;
	private Double discountAmount;
	private Short vehicleType;
	private Double energyConservationSubsidy;
	private String subsidySupplierId;
	private String subsidySupplierNo;
	private String subsidySupplierName;
	private Double energyConservationSubsidyRef;
	private String subsidySupplierIdRef;
	private String subsidySupplierNoRef;
	private String subsidySupplierNameRef;
	private String auditor;
	private Timestamp auditTime;
	private String auditComment;
	private String vehicleSelfNo;
	private Double taxationAmount;
	private Boolean isPayByBillInsurance;
	private Double profitReturn;
	private Boolean isPayByBillCharge;
	private Double otherCost;
	private String subjectMatter;
	private String transportRoutes;
	private String startPoint;
	private String waysPoint;
	private String endPoint;
	private String relationDetailId;
	private Timestamp submitTime;
	private String errorCode;
	private String errorMsg;
	private String documentNo;
	private Short status;
	private String userId;
	private String userNo;
	private String userName;
	private String departmentId;
	private String departmentNo;
	private String departmentName;
	private String submitStationId;
	private String submitStationName;
	private String approverId;
	private String approverNo;
	private String relationVehicleVin;
	private String approveStatusMeaning;
	private Double modifiedFeeAmt;
	private Double moveStockCharge;
//	private String purchaseContractNo;
	private String abortStatusMeaning;
	private Double chargeLoanMoney;
	private Double chargeMoneyLd;
	private Double loanAmountTot;
	private Double loanAmountLv;
	private Double chargeFirstPay;
	private String transportRoutes_1;
	private Double oilCardProfit;
	private Double profitReturnPf;
	private Double profitReturnReal;
	private Double profitPf;
	private Short vehicleKind;
	private String vehicleKindMeaning;
	private String statusMeaning;
	private String stationId;
	private String stationName;
	private String customerId;
	private String customerNo;
	private String customerName;

	public VwVehicleSaleContractDetail() {
	}

	// Property accessors

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

	public String getOutStockNo() {
		return this.outStockNo;
	}

	public void setOutStockNo(String outStockNo) {
		this.outStockNo = outStockNo;
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

	public Double getVehicleCostRef() {
		return this.vehicleCostRef;
	}

	public void setVehicleCostRef(Double vehicleCostRef) {
		this.vehicleCostRef = vehicleCostRef;
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

	public String getApproverName() {
		return this.approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveComment() {
		return this.approveComment;
	}

	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}

	public Short getApproveStatus() {
		return this.approveStatus;
	}

	public void setApproveStatus(Short approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getVehicleComment() {
		return this.vehicleComment;
	}

	public void setVehicleComment(String vehicleComment) {
		this.vehicleComment = vehicleComment;
	}

	public Short getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(Short paymentStatus) {
		this.paymentStatus = paymentStatus;
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

	public String getBelongToSupplierId() {
		return this.belongToSupplierId;
	}

	public void setBelongToSupplierId(String belongToSupplierId) {
		this.belongToSupplierId = belongToSupplierId;
	}

	public String getBelongToSupplierNo() {
		return this.belongToSupplierNo;
	}

	public void setBelongToSupplierNo(String belongToSupplierNo) {
		this.belongToSupplierNo = belongToSupplierNo;
	}

	public String getBelongToSupplierName() {
		return this.belongToSupplierName;
	}

	public void setBelongToSupplierName(String belongToSupplierName) {
		this.belongToSupplierName = belongToSupplierName;
	}

	public Short getAbortStatus() {
		return this.abortStatus;
	}

	public void setAbortStatus(Short abortStatus) {
		this.abortStatus = abortStatus;
	}

	public Double getInvoicePrice() {
		return this.invoicePrice;
	}

	public void setInvoicePrice(Double invoicePrice) {
		this.invoicePrice = invoicePrice;
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

	public String getImagesUrls() {
		return this.imagesUrls;
	}

	public void setImagesUrls(String imagesUrls) {
		this.imagesUrls = imagesUrls;
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

	public Short getVehicleType() {
		return this.vehicleType;
	}

	public void setVehicleType(Short vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Double getEnergyConservationSubsidy() {
		return this.energyConservationSubsidy;
	}

	public void setEnergyConservationSubsidy(Double energyConservationSubsidy) {
		this.energyConservationSubsidy = energyConservationSubsidy;
	}

	public String getSubsidySupplierId() {
		return this.subsidySupplierId;
	}

	public void setSubsidySupplierId(String subsidySupplierId) {
		this.subsidySupplierId = subsidySupplierId;
	}

	public String getSubsidySupplierNo() {
		return this.subsidySupplierNo;
	}

	public void setSubsidySupplierNo(String subsidySupplierNo) {
		this.subsidySupplierNo = subsidySupplierNo;
	}

	public String getSubsidySupplierName() {
		return this.subsidySupplierName;
	}

	public void setSubsidySupplierName(String subsidySupplierName) {
		this.subsidySupplierName = subsidySupplierName;
	}

	public Double getEnergyConservationSubsidyRef() {
		return this.energyConservationSubsidyRef;
	}

	public void setEnergyConservationSubsidyRef(Double energyConservationSubsidyRef) {
		this.energyConservationSubsidyRef = energyConservationSubsidyRef;
	}

	public String getSubsidySupplierIdRef() {
		return this.subsidySupplierIdRef;
	}

	public void setSubsidySupplierIdRef(String subsidySupplierIdRef) {
		this.subsidySupplierIdRef = subsidySupplierIdRef;
	}

	public String getSubsidySupplierNoRef() {
		return this.subsidySupplierNoRef;
	}

	public void setSubsidySupplierNoRef(String subsidySupplierNoRef) {
		this.subsidySupplierNoRef = subsidySupplierNoRef;
	}

	public String getSubsidySupplierNameRef() {
		return this.subsidySupplierNameRef;
	}

	public void setSubsidySupplierNameRef(String subsidySupplierNameRef) {
		this.subsidySupplierNameRef = subsidySupplierNameRef;
	}

	public String getAuditor() {
		return this.auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public Timestamp getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditComment() {
		return this.auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	public String getVehicleSelfNo() {
		return this.vehicleSelfNo;
	}

	public void setVehicleSelfNo(String vehicleSelfNo) {
		this.vehicleSelfNo = vehicleSelfNo;
	}

	public Double getTaxationAmount() {
		return this.taxationAmount;
	}

	public void setTaxationAmount(Double taxationAmount) {
		this.taxationAmount = taxationAmount;
	}

	public Boolean getIsPayByBillInsurance() {
		return this.isPayByBillInsurance;
	}

	public void setIsPayByBillInsurance(Boolean isPayByBillInsurance) {
		this.isPayByBillInsurance = isPayByBillInsurance;
	}

	public Double getProfitReturn() {
		return this.profitReturn;
	}

	public void setProfitReturn(Double profitReturn) {
		this.profitReturn = profitReturn;
	}

	public Boolean getIsPayByBillCharge() {
		return this.isPayByBillCharge;
	}

	public void setIsPayByBillCharge(Boolean isPayByBillCharge) {
		this.isPayByBillCharge = isPayByBillCharge;
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

	public String getRelationDetailId() {
		return this.relationDetailId;
	}

	public void setRelationDetailId(String relationDetailId) {
		this.relationDetailId = relationDetailId;
	}

	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentNo() {
		return this.departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getSubmitStationId() {
		return this.submitStationId;
	}

	public void setSubmitStationId(String submitStationId) {
		this.submitStationId = submitStationId;
	}

	public String getSubmitStationName() {
		return this.submitStationName;
	}

	public void setSubmitStationName(String submitStationName) {
		this.submitStationName = submitStationName;
	}

	public String getApproverId() {
		return this.approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getApproverNo() {
		return this.approverNo;
	}

	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}

	public String getRelationVehicleVin() {
		return this.relationVehicleVin;
	}

	public void setRelationVehicleVin(String relationVehicleVin) {
		this.relationVehicleVin = relationVehicleVin;
	}

	public String getApproveStatusMeaning() {
		return this.approveStatusMeaning;
	}

	public void setApproveStatusMeaning(String approveStatusMeaning) {
		this.approveStatusMeaning = approveStatusMeaning;
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

//	public String getPurchaseContractNo() {
//		return this.purchaseContractNo;
//	}
//
//	public void setPurchaseContractNo(String purchaseContractNo) {
//		this.purchaseContractNo = purchaseContractNo;
//	}

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

	public String getStatusMeaning() {
		return this.statusMeaning;
	}

	public void setStatusMeaning(String statusMeaning) {
		this.statusMeaning = statusMeaning;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerNo() {
		return this.customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
