package cn.sf_soft.office.approval.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetCharge;

/**
 * 车辆销售合同--车辆
 *
 * @author caigx
 */
@SuppressWarnings("rawtypes")
public class VehicleSaleContractDetail extends ApproveDocuments {

	private static final long serialVersionUID = 1L;
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
	private Double vehiclePriceTotal = 0d;
	private Double vehiclePrice = 0d;
	private Double vehicleCost = 0d;
	private Double vehicleCostRef = 0d;
	private Double vehicleCarriage = 0d;
	private Integer vehicleQuantity;
	private String warehouseId;
	private String vehicleVnoNew;
	private String vehicleVinNew;
	private Double minSalePrice = 0d;
	private Double minProfit = 0d;
	private Double insuranceIncome = 0d;
	private Double insurancePf = 0d;
	private Double insuranceCost = 0d;
	private Double presentIncome = 0d;
	private Double presentPf = 0d;
	private Double presentCost = 0d;
	private Double conversionIncome = 0d;
	private Double conversionPf = 0d;
	private Double conversionCost = 0d;
	private Double modifiedFee = 0d;
	private Double chargeIncome = 0d;
	private Double chargePf = 0d;
	private Double chargeCost = 0d;
	private Double vehicleProfit = 0d;
	private Double largessAmount = 0d;
	private String deliverAddress;
	private Timestamp planDeliverTime;
	private Timestamp realDeliverTime;
	private Timestamp bestDeliverTime; // 新增 最佳交付 caigx 20170310
	private String vehicleCardNo;
	private Timestamp vehicleCardTime;
	private String insuranceNo;
	private String insuranceCompanyName;
	private String insuranceCompanyNo;
	private String insuranceCompanyId;
	private String approveComment;
	private Short approveStatus;
	private String vehicleComment;
	private Short paymentStatus;
	private String driveRoomNo;
	private Double maintainFee = 0d;
	private Double largessPart = 0d;
	private Double largessService = 0d;
	private String customerIdProfit;
	private String customerNoProfit;
	private String customerNameProfit;
	private String belongToSupplierId;
	private String belongToSupplierNo;
	private String belongToSupplierName;
	private Short abortStatus;
	private Double invoicePrice = 0d;
	private String vehicleNameNew;
	private String vehicleEligibleNoNew;
	private String vnoIdNew;
	private Boolean isContainInsuranceCost;
	private String profession;
	private String imagesUrls;
	private Boolean ticketOutStockFlag;
	private Double ticketOutStockAmount = 0d;
	private Double belongToSupplierRebate = 0d;
	private Double discountAmount = 0d;
	private Short vehicleType;
	private Double energyConservationSubsidy = 0d;
	private String subsidySupplierId;
	private String subsidySupplierNo;
	private String subsidySupplierName;
	private Double energyConservationSubsidyRef = 0d;
	private String subsidySupplierIdRef;
	private String subsidySupplierNoRef;
	private String subsidySupplierNameRef;
	private String auditor;
	private Timestamp auditTime;
	private String auditComment;
	private String vehicleSelfNo;
	private Double taxationAmount = 0d;
	private Boolean isPayByBillInsurance;
	private Double profitReturn = 0d;
	private Boolean isPayByBillCharge;
	private Double otherCost = 0d;
	private String subjectMatter;
	private String transportRoutes;
	private String startPoint;
	private String waysPoint;
	private String endPoint;
	private String relationDetailId;
	// private Timestamp submitTime;
	private String errorCode;
	private String errorMsg;

	// private String documentNo;
	// private Short status;
	// private String userId;
	// private String userNo;
	// private String userName;
	// private String departmentId;
	// private String departmentNo;
	// private String departmentName;
	// private String submitStationId;
	// private String submitStationName;
	// private String approverId;
	// private String approverNo;
	// private String approverName;
	// private Timestamp approveTime;

	private String modifier;
	// protected Timestamp modifyTime;

	// 要显示在车辆合同明细中的内容
	private Double totalInsurance = 0d; // 保险总额
	private Double totalPresentCost = 0d;// 精品成本
	private Double totalItemCost = 0d;// 改装成本
	private Double totalChargePf = 0d; // 其他费用成本预估
	private Double totalInvoiceAmount = 0d; // 发票金额
	private Double totalGiftAmount = 0d; // 赠品金额
	private Short buyType; // 付款方式
	private String buyTypeName;// 付款方式中文名称
	private Double totalProfit = 0d; // 预估利润
	private Double setupProfit = 0d; // 设定最低利润, null表示未设定
	private Double setupVehiclePrice = 0d;// 设定的车辆单价, null表示未设定
	private Double loanAmountLv = 0d;// 消贷-车辆贷款
	private Double firstPayAmountVd = 0d;// 消贷-车辆首付

	private String customerName; // 客户
	private String seller; // 销售员
	private String contractComment;// 合同说明
	private String creator; // 制单人，指合同的制单人
	private Timestamp createTime; // 制单时间，指合同的制单时间

	private List<VehicleSaleContractInsurance> insurances; // 保险
	private List<VehicleSaleContractPresent> presents; // 精品
	private List<VehicleSaleContractItem> items; // 改装
	private List<VehicleSaleContractCharge> charges; // 其他费用
	private List<VehicleInvoices> invoices; // 发票
	private List<VwVehicleLoanBudgetCharge> budgetCharges; // 车辆消贷
	private List<VehicleSaleContractGifts> gifts; // 赠品

	private List<ConsumptionLoanFee> loanFee;// 消贷费用相关，IOS需要

	// 20170516 新增
	private String vehicleOwnerId;

	private String vehicleOwnerName;

	// 预付订金 caigx-20171108
	private Double deposit = 0d;

	private String groupId;

	/**
	 * 上牌类型
	 */
	private String cardType;

	/**
	 * 使用性质
	 */
	private String useProperty;

	/**
	 * 准乘人数
	 */
	private Integer peopleNumber;

	/**
	 * 环保公告
	 */
	private String epNotice;

	/**
	 * 准牵引质量
	 */
	private BigDecimal tractiveTonnage;


	/**
	 * 车厢内尺寸
	 */
	private String containerInsideSize;


	/**
	 * 线索ID
	 */
	private String visitorNo;

	private String containerSize;
	private String vehicleSize;
	private BigDecimal vehicleWeight;
	private BigDecimal curbWeight;
	private BigDecimal registrationTonnage;
	private String registrationAddress;
	private String oilNotice;


	/**
	 * 上次交付日期,数据中心报表需要
	 */
	private Timestamp lastDeliverTime;

	/**
	 * 延期交付原因
	 */
	private String delayDeliverReason;

	/**
	 * 保险代购(年)
	 */
	private String insuranceYear;


	/**
	 * 保险指定机构
	 */
	private String insuranceAppointUnit;

	public VehicleSaleContractDetail() {
	}

	public VehicleSaleContractDetail(String contractDetailId) {
		this.contractDetailId = contractDetailId;
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

	public void setEnergyConservationSubsidyRef(
			Double energyConservationSubsidyRef) {
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

	@Override
	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	@Override
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

	@Override
	public String getDocumentNo() {
		return this.documentNo;
	}

	@Override
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	@Override
	public Short getStatus() {
		return this.status;
	}

	@Override
	public void setStatus(Short status) {
		this.status = status;
	}

	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getUserNo() {
		return this.userNo;
	}

	@Override
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Override
	public String getUserName() {
		return this.userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getDepartmentId() {
		return this.departmentId;
	}

	@Override
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	public String getDepartmentNo() {
		return this.departmentNo;
	}

	@Override
	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	@Override
	public String getDepartmentName() {
		return this.departmentName;
	}

	@Override
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public String getSubmitStationId() {
		return this.submitStationId;
	}

	@Override
	public void setSubmitStationId(String submitStationId) {
		this.submitStationId = submitStationId;
	}

	@Override
	public String getSubmitStationName() {
		return this.submitStationName;
	}

	@Override
	public void setSubmitStationName(String submitStationName) {
		this.submitStationName = submitStationName;
	}

	@Override
	public String getApproverId() {
		return this.approverId;
	}

	@Override
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	@Override
	public String getApproverNo() {
		return this.approverNo;
	}

	@Override
	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}

	@Override
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	@Override
	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	@Override
	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public Double getTotalInsurance() {
		return totalInsurance;
	}

	public void setTotalInsurance(Double totalInsurance) {
		this.totalInsurance = totalInsurance;
	}

	public Double getTotalPresentCost() {
		return totalPresentCost;
	}

	public void setTotalPresentCost(Double totalPresentCost) {
		this.totalPresentCost = totalPresentCost;
	}

	public Double getTotalItemCost() {
		return totalItemCost;
	}

	public void setTotalItemCost(Double totalItemCost) {
		this.totalItemCost = totalItemCost;
	}

	public Double getTotalChargePf() {
		return totalChargePf;
	}

	public void setTotalChargePf(Double totalChargePf) {
		this.totalChargePf = totalChargePf;
	}

	public Double getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}

	public void setTotalInvoiceAmount(Double totalInvoiceAmount) {
		this.totalInvoiceAmount = totalInvoiceAmount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getContractComment() {
		return contractComment;
	}

	public void setContractComment(String contractComment) {
		this.contractComment = contractComment;
	}

	public List<VehicleSaleContractInsurance> getInsurances() {
		return insurances;
	}

	public void setInsurances(List<VehicleSaleContractInsurance> insurances) {
		this.insurances = insurances;
	}

	public List<VehicleSaleContractPresent> getPresents() {
		return presents;
	}

	public void setPresents(List<VehicleSaleContractPresent> presents) {
		this.presents = presents;
	}

	public List<VehicleSaleContractItem> getItems() {
		return items;
	}

	public void setItems(List<VehicleSaleContractItem> items) {
		this.items = items;
	}

	public List<VehicleSaleContractCharge> getCharges() {
		return charges;
	}

	public void setCharges(List<VehicleSaleContractCharge> charges) {
		this.charges = charges;
	}

	public List<VehicleInvoices> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<VehicleInvoices> invoices) {
		this.invoices = invoices;
	}

	public List<VwVehicleLoanBudgetCharge> getBudgetCharges() {
		return budgetCharges;
	}

	public void setBudgetCharges(List<VwVehicleLoanBudgetCharge> budgetCharges) {
		this.budgetCharges = budgetCharges;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Short getBuyType() {
		return buyType;
	}

	public void setBuyType(Short buyType) {
		this.buyType = buyType;
	}

	public String getBuyTypeName() {
		return buyTypeName;
	}

	public void setBuyTypeName(String buyTypeName) {
		this.buyTypeName = buyTypeName;
	}

	public Double getTotalGiftAmount() {
		return totalGiftAmount;
	}

	public void setTotalGiftAmount(Double totalGiftAmount) {
		this.totalGiftAmount = totalGiftAmount;
	}

	public List<VehicleSaleContractGifts> getGifts() {
		return gifts;
	}

	public void setGifts(List<VehicleSaleContractGifts> gifts) {
		this.gifts = gifts;
	}

	public Double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public Double getSetupProfit() {
		return setupProfit;
	}

	public void setSetupProfit(Double setupProfit) {
		this.setupProfit = setupProfit;
	}

	public Double getSetupVehiclePrice() {
		return setupVehiclePrice;
	}

	public void setSetupVehiclePrice(Double setupVehiclePrice) {
		this.setupVehiclePrice = setupVehiclePrice;
	}

	public Double getLoanAmountLv() {
		return loanAmountLv;
	}

	public void setLoanAmountLv(Double loanAmountLv) {
		this.loanAmountLv = loanAmountLv;
	}

	public Double getFirstPayAmountVd() {
		return firstPayAmountVd;
	}

	public void setFirstPayAmountVd(Double firstPayAmountVd) {
		this.firstPayAmountVd = firstPayAmountVd;
	}

	public List<ConsumptionLoanFee> getLoanFee() {
		return loanFee;
	}

	public void setLoanFee(List<ConsumptionLoanFee> loanFee) {
		this.loanFee = loanFee;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Override
	public Timestamp getModifyTime() {
		return modifyTime;
	}

	@Override
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Timestamp getBestDeliverTime() {
		return bestDeliverTime;
	}

	public void setBestDeliverTime(Timestamp bestDeliverTime) {
		this.bestDeliverTime = bestDeliverTime;
	}

	public String getVehicleOwnerId() {
		return vehicleOwnerId;
	}

	public void setVehicleOwnerId(String vehicleOwnerId) {
		this.vehicleOwnerId = vehicleOwnerId;
	}

	public String getVehicleOwnerName() {
		return vehicleOwnerName;
	}

	public void setVehicleOwnerName(String vehicleOwnerName) {
		this.vehicleOwnerName = vehicleOwnerName;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}


	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public String getEpNotice() {
		return epNotice;
	}

	public void setEpNotice(String epNotice) {
		this.epNotice = epNotice;
	}

	public BigDecimal getTractiveTonnage() {
		return tractiveTonnage;
	}

	public void setTractiveTonnage(BigDecimal tractiveTonnage) {
		this.tractiveTonnage = tractiveTonnage;
	}

	public String getContainerInsideSize() {
		return containerInsideSize;
	}

	public void setContainerInsideSize(String containerInsideSize) {
		this.containerInsideSize = containerInsideSize;
	}

	public String getVisitorNo() {
		return visitorNo;
	}

	public void setVisitorNo(String visitorNo) {
		this.visitorNo = visitorNo;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public String getVehicleSize() {
		return vehicleSize;
	}

	public void setVehicleSize(String vehicleSize) {
		this.vehicleSize = vehicleSize;
	}

	public BigDecimal getVehicleWeight() {
		return vehicleWeight;
	}

	public void setVehicleWeight(BigDecimal vehicleWeight) {
		this.vehicleWeight = vehicleWeight;
	}

	public BigDecimal getCurbWeight() {
		return curbWeight;
	}

	public void setCurbWeight(BigDecimal curbWeight) {
		this.curbWeight = curbWeight;
	}

	public BigDecimal getRegistrationTonnage() {
		return registrationTonnage;
	}

	public void setRegistrationTonnage(BigDecimal registrationTonnage) {
		this.registrationTonnage = registrationTonnage;
	}

	public String getRegistrationAddress() {
		return registrationAddress;
	}

	public void setRegistrationAddress(String registrationAddress) {
		this.registrationAddress = registrationAddress;
	}

	public String getUseProperty() {
		return useProperty;
	}

	public void setUseProperty(String useProperty) {
		this.useProperty = useProperty;
	}

	public String getOilNotice() {
		return oilNotice;
	}

	public void setOilNotice(String oilNotice) {
		this.oilNotice = oilNotice;
	}

	public Timestamp getLastDeliverTime() {
		return lastDeliverTime;
	}

	public void setLastDeliverTime(Timestamp lastDeliverTime) {
		this.lastDeliverTime = lastDeliverTime;
	}

	public String getDelayDeliverReason() {
		return delayDeliverReason;
	}

	public void setDelayDeliverReason(String delayDeliverReason) {
		this.delayDeliverReason = delayDeliverReason;
	}

	public String getInsuranceYear() {
		return insuranceYear;
	}

	public void setInsuranceYear(String insuranceYear) {
		this.insuranceYear = insuranceYear;
	}

	public String getInsuranceAppointUnit() {
		return insuranceAppointUnit;
	}

	public void setInsuranceAppointUnit(String insuranceAppointUnit) {
		this.insuranceAppointUnit = insuranceAppointUnit;
	}
}
