package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 车辆-档案
 * 
 * @author caigx
 *
 */
public class VehicleArchives implements java.io.Serializable {

	private static final long serialVersionUID = -7555579844509535869L;
	private String vehicleId;
	private String vehicleCardNo;
	private Timestamp vehicleCardTime;
	private String vehicleSelfNo;
	private String vehicleVin;
	private String vnoId;
	private String vehicleSalesCode;
	private String vehicleVno;
	private String vehicleName;
	private String vehicleStrain;
	private String vehicleColor;
	private Short vehicleKind;
	private String vehicleEngineType;
	private String vehicleEngineNo;
	private String vehicleEligibleNo;
	private Timestamp vehicleOutFactoryTime;
	private Timestamp vehiclePurchaseTime;
	private String vehicleMaintainNo;
	private String vehicleFactoryNo;
	private String driveRoomNo;
	private String vehicleGearBox;
	private String vehicleMemberId;
	private String vehicleMemberType;
	private String vehicleMemberNo;
	private Timestamp vehicleMemberBeginTime;
	private Timestamp vehicleMemberEndTime;
	private String vehicleLinkman;
	private String vehicleLinkmanMobile;
	private String vehicleLinkmanPhone;
	private String vehicleLinkmanAddress;
	private Boolean vehiclePurchaseFlag;
	private String seller;
	private Double vehiclePrice;
	private String vehicleComment;
	private Timestamp insuranceBeginDate;
	private Timestamp insuranceEndDate;
	private String supplierName;
	private String vehicleInsuranceStatus;
	private Timestamp insuranceRemindDate;
	private String insuranceReminder;
	private String insuranceRemindMode;
	private String insuranceRemindResult;
	private String insuranceRemindHistory;
	private Timestamp yearCensorDate;
	private Timestamp yearCensorRemindDate;
	private String yearCensorReminder;
	private String yearCensorRemindMode;
	private String yearCensorRemindResult;
	private Timestamp drivingLicenseRegisterDate;
	private Double lastServiceMileage;
	private Timestamp lastServiceTime;
	private Timestamp lastMaintainDate;
	private Double lastMaintainMileage;
	private Timestamp maintainDateLast;
	private Timestamp maintainRemindDate;
	private String maintainReminder;
	private Boolean maintainRemindFlag;
	private Double averageMileage;
	private Double vehicleIntegral;
	private Double vehicleIntegralUsed;
	private String ticketNo;
	private String customerId;
	private Short status;
	private String stationId;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private Double averageMilege;
	private Double maintainMileageNext;
	private Timestamp maintainDateNext;
	private Short remindResult;
	private Short remindType;
	private Boolean vehicleBelongTo;
	private String belongToSupplierId;
	private String belongToSupplierNo;
	private String belongToSupplierName;
	private Integer backAllow;
	private String vehicleSaleCode;
	private String lastServiceReceiver;
	private String OVehicleMemberId;
	private String OVehicleMemberType;
	private String OObjectNo;
	private String profession;
	private String imagesUrls;
	private Timestamp compulsoryInsuranceEndDate;
	private Timestamp operationCertificateRecordDate;
	private Timestamp countMaintainDateNext;
	private String sellerId;
	private Timestamp compulsoryInsuranceBeginDate;
	private String subjectMatter;
	private String transportRoutes;
	private String drivingLicenseNumber;
	private Timestamp invoiceTime;
	private String startPoint;
	private String waysPoint;
	private String endPoint;
	private String serviceRange;
	private String relatedVehicleCard;
	
	private String vehicleCardModel;

	public VehicleArchives() {
	}

	public VehicleArchives(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public VehicleArchives(String vehicleId, String vehicleCardNo, Timestamp vehicleCardTime, String vehicleSelfNo,
			String vehicleVin, String vnoId, String vehicleSalesCode, String vehicleVno, String vehicleName,
			String vehicleStrain, String vehicleColor, Short vehicleKind, String vehicleEngineType,
			String vehicleEngineNo, String vehicleEligibleNo, Timestamp vehicleOutFactoryTime,
			Timestamp vehiclePurchaseTime, String vehicleMaintainNo, String vehicleFactoryNo, String driveRoomNo,
			String vehicleGearBox, String vehicleMemberId, String vehicleMemberType, String vehicleMemberNo,
			Timestamp vehicleMemberBeginTime, Timestamp vehicleMemberEndTime, String vehicleLinkman,
			String vehicleLinkmanMobile, String vehicleLinkmanPhone, String vehicleLinkmanAddress,
			Boolean vehiclePurchaseFlag, String seller, Double vehiclePrice, String vehicleComment,
			Timestamp insuranceBeginDate, Timestamp insuranceEndDate, String supplierName,
			String vehicleInsuranceStatus, Timestamp insuranceRemindDate, String insuranceReminder,
			String insuranceRemindMode, String insuranceRemindResult, String insuranceRemindHistory,
			Timestamp yearCensorDate, Timestamp yearCensorRemindDate, String yearCensorReminder,
			String yearCensorRemindMode, String yearCensorRemindResult, Timestamp drivingLicenseRegisterDate,
			Double lastServiceMileage, Timestamp lastServiceTime, Timestamp lastMaintainDate,
			Double lastMaintainMileage, Timestamp maintainDateLast, Timestamp maintainRemindDate,
			String maintainReminder, Boolean maintainRemindFlag, Double averageMileage, Double vehicleIntegral,
			Double vehicleIntegralUsed, String ticketNo, String customerId, Short status, String stationId,
			String creator, Timestamp createTime, String modifier, Timestamp modifyTime, Double averageMilege,
			Double maintainMileageNext, Timestamp maintainDateNext, Short remindResult, Short remindType,
			Boolean vehicleBelongTo, String belongToSupplierId, String belongToSupplierNo, String belongToSupplierName,
			Integer backAllow, String vehicleSaleCode, String lastServiceReceiver, String OVehicleMemberId,
			String OVehicleMemberType, String OObjectNo, String profession, String imagesUrls,
			Timestamp compulsoryInsuranceEndDate, Timestamp operationCertificateRecordDate,
			Timestamp countMaintainDateNext, String sellerId, Timestamp compulsoryInsuranceBeginDate,
			String subjectMatter, String transportRoutes, String drivingLicenseNumber, Timestamp invoiceTime,
			String startPoint, String waysPoint, String endPoint, String serviceRange, String relatedVehicleCard) {
		this.vehicleId = vehicleId;
		this.vehicleCardNo = vehicleCardNo;
		this.vehicleCardTime = vehicleCardTime;
		this.vehicleSelfNo = vehicleSelfNo;
		this.vehicleVin = vehicleVin;
		this.vnoId = vnoId;
		this.vehicleSalesCode = vehicleSalesCode;
		this.vehicleVno = vehicleVno;
		this.vehicleName = vehicleName;
		this.vehicleStrain = vehicleStrain;
		this.vehicleColor = vehicleColor;
		this.vehicleKind = vehicleKind;
		this.vehicleEngineType = vehicleEngineType;
		this.vehicleEngineNo = vehicleEngineNo;
		this.vehicleEligibleNo = vehicleEligibleNo;
		this.vehicleOutFactoryTime = vehicleOutFactoryTime;
		this.vehiclePurchaseTime = vehiclePurchaseTime;
		this.vehicleMaintainNo = vehicleMaintainNo;
		this.vehicleFactoryNo = vehicleFactoryNo;
		this.driveRoomNo = driveRoomNo;
		this.vehicleGearBox = vehicleGearBox;
		this.vehicleMemberId = vehicleMemberId;
		this.vehicleMemberType = vehicleMemberType;
		this.vehicleMemberNo = vehicleMemberNo;
		this.vehicleMemberBeginTime = vehicleMemberBeginTime;
		this.vehicleMemberEndTime = vehicleMemberEndTime;
		this.vehicleLinkman = vehicleLinkman;
		this.vehicleLinkmanMobile = vehicleLinkmanMobile;
		this.vehicleLinkmanPhone = vehicleLinkmanPhone;
		this.vehicleLinkmanAddress = vehicleLinkmanAddress;
		this.vehiclePurchaseFlag = vehiclePurchaseFlag;
		this.seller = seller;
		this.vehiclePrice = vehiclePrice;
		this.vehicleComment = vehicleComment;
		this.insuranceBeginDate = insuranceBeginDate;
		this.insuranceEndDate = insuranceEndDate;
		this.supplierName = supplierName;
		this.vehicleInsuranceStatus = vehicleInsuranceStatus;
		this.insuranceRemindDate = insuranceRemindDate;
		this.insuranceReminder = insuranceReminder;
		this.insuranceRemindMode = insuranceRemindMode;
		this.insuranceRemindResult = insuranceRemindResult;
		this.insuranceRemindHistory = insuranceRemindHistory;
		this.yearCensorDate = yearCensorDate;
		this.yearCensorRemindDate = yearCensorRemindDate;
		this.yearCensorReminder = yearCensorReminder;
		this.yearCensorRemindMode = yearCensorRemindMode;
		this.yearCensorRemindResult = yearCensorRemindResult;
		this.drivingLicenseRegisterDate = drivingLicenseRegisterDate;
		this.lastServiceMileage = lastServiceMileage;
		this.lastServiceTime = lastServiceTime;
		this.lastMaintainDate = lastMaintainDate;
		this.lastMaintainMileage = lastMaintainMileage;
		this.maintainDateLast = maintainDateLast;
		this.maintainRemindDate = maintainRemindDate;
		this.maintainReminder = maintainReminder;
		this.maintainRemindFlag = maintainRemindFlag;
		this.averageMileage = averageMileage;
		this.vehicleIntegral = vehicleIntegral;
		this.vehicleIntegralUsed = vehicleIntegralUsed;
		this.ticketNo = ticketNo;
		this.customerId = customerId;
		this.status = status;
		this.stationId = stationId;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.averageMilege = averageMilege;
		this.maintainMileageNext = maintainMileageNext;
		this.maintainDateNext = maintainDateNext;
		this.remindResult = remindResult;
		this.remindType = remindType;
		this.vehicleBelongTo = vehicleBelongTo;
		this.belongToSupplierId = belongToSupplierId;
		this.belongToSupplierNo = belongToSupplierNo;
		this.belongToSupplierName = belongToSupplierName;
		this.backAllow = backAllow;
		this.vehicleSaleCode = vehicleSaleCode;
		this.lastServiceReceiver = lastServiceReceiver;
		this.OVehicleMemberId = OVehicleMemberId;
		this.OVehicleMemberType = OVehicleMemberType;
		this.OObjectNo = OObjectNo;
		this.profession = profession;
		this.imagesUrls = imagesUrls;
		this.compulsoryInsuranceEndDate = compulsoryInsuranceEndDate;
		this.operationCertificateRecordDate = operationCertificateRecordDate;
		this.countMaintainDateNext = countMaintainDateNext;
		this.sellerId = sellerId;
		this.compulsoryInsuranceBeginDate = compulsoryInsuranceBeginDate;
		this.subjectMatter = subjectMatter;
		this.transportRoutes = transportRoutes;
		this.drivingLicenseNumber = drivingLicenseNumber;
		this.invoiceTime = invoiceTime;
		this.startPoint = startPoint;
		this.waysPoint = waysPoint;
		this.endPoint = endPoint;
		this.serviceRange = serviceRange;
		this.relatedVehicleCard = relatedVehicleCard;
	}

	// Property accessors

	public String getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
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

	public String getVehicleSelfNo() {
		return this.vehicleSelfNo;
	}

	public void setVehicleSelfNo(String vehicleSelfNo) {
		this.vehicleSelfNo = vehicleSelfNo;
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

	public Short getVehicleKind() {
		return this.vehicleKind;
	}

	public void setVehicleKind(Short vehicleKind) {
		this.vehicleKind = vehicleKind;
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

	public Timestamp getVehiclePurchaseTime() {
		return this.vehiclePurchaseTime;
	}

	public void setVehiclePurchaseTime(Timestamp vehiclePurchaseTime) {
		this.vehiclePurchaseTime = vehiclePurchaseTime;
	}

	public String getVehicleMaintainNo() {
		return this.vehicleMaintainNo;
	}

	public void setVehicleMaintainNo(String vehicleMaintainNo) {
		this.vehicleMaintainNo = vehicleMaintainNo;
	}

	public String getVehicleFactoryNo() {
		return this.vehicleFactoryNo;
	}

	public void setVehicleFactoryNo(String vehicleFactoryNo) {
		this.vehicleFactoryNo = vehicleFactoryNo;
	}

	public String getDriveRoomNo() {
		return this.driveRoomNo;
	}

	public void setDriveRoomNo(String driveRoomNo) {
		this.driveRoomNo = driveRoomNo;
	}

	public String getVehicleGearBox() {
		return this.vehicleGearBox;
	}

	public void setVehicleGearBox(String vehicleGearBox) {
		this.vehicleGearBox = vehicleGearBox;
	}

	public String getVehicleMemberId() {
		return this.vehicleMemberId;
	}

	public void setVehicleMemberId(String vehicleMemberId) {
		this.vehicleMemberId = vehicleMemberId;
	}

	public String getVehicleMemberType() {
		return this.vehicleMemberType;
	}

	public void setVehicleMemberType(String vehicleMemberType) {
		this.vehicleMemberType = vehicleMemberType;
	}

	public String getVehicleMemberNo() {
		return this.vehicleMemberNo;
	}

	public void setVehicleMemberNo(String vehicleMemberNo) {
		this.vehicleMemberNo = vehicleMemberNo;
	}

	public Timestamp getVehicleMemberBeginTime() {
		return this.vehicleMemberBeginTime;
	}

	public void setVehicleMemberBeginTime(Timestamp vehicleMemberBeginTime) {
		this.vehicleMemberBeginTime = vehicleMemberBeginTime;
	}

	public Timestamp getVehicleMemberEndTime() {
		return this.vehicleMemberEndTime;
	}

	public void setVehicleMemberEndTime(Timestamp vehicleMemberEndTime) {
		this.vehicleMemberEndTime = vehicleMemberEndTime;
	}

	public String getVehicleLinkman() {
		return this.vehicleLinkman;
	}

	public void setVehicleLinkman(String vehicleLinkman) {
		this.vehicleLinkman = vehicleLinkman;
	}

	public String getVehicleLinkmanMobile() {
		return this.vehicleLinkmanMobile;
	}

	public void setVehicleLinkmanMobile(String vehicleLinkmanMobile) {
		this.vehicleLinkmanMobile = vehicleLinkmanMobile;
	}

	public String getVehicleLinkmanPhone() {
		return this.vehicleLinkmanPhone;
	}

	public void setVehicleLinkmanPhone(String vehicleLinkmanPhone) {
		this.vehicleLinkmanPhone = vehicleLinkmanPhone;
	}

	public String getVehicleLinkmanAddress() {
		return this.vehicleLinkmanAddress;
	}

	public void setVehicleLinkmanAddress(String vehicleLinkmanAddress) {
		this.vehicleLinkmanAddress = vehicleLinkmanAddress;
	}

	public Boolean getVehiclePurchaseFlag() {
		return this.vehiclePurchaseFlag;
	}

	public void setVehiclePurchaseFlag(Boolean vehiclePurchaseFlag) {
		this.vehiclePurchaseFlag = vehiclePurchaseFlag;
	}

	public String getSeller() {
		return this.seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Double getVehiclePrice() {
		return this.vehiclePrice;
	}

	public void setVehiclePrice(Double vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}

	public String getVehicleComment() {
		return this.vehicleComment;
	}

	public void setVehicleComment(String vehicleComment) {
		this.vehicleComment = vehicleComment;
	}

	public Timestamp getInsuranceBeginDate() {
		return this.insuranceBeginDate;
	}

	public void setInsuranceBeginDate(Timestamp insuranceBeginDate) {
		this.insuranceBeginDate = insuranceBeginDate;
	}

	public Timestamp getInsuranceEndDate() {
		return this.insuranceEndDate;
	}

	public void setInsuranceEndDate(Timestamp insuranceEndDate) {
		this.insuranceEndDate = insuranceEndDate;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getVehicleInsuranceStatus() {
		return this.vehicleInsuranceStatus;
	}

	public void setVehicleInsuranceStatus(String vehicleInsuranceStatus) {
		this.vehicleInsuranceStatus = vehicleInsuranceStatus;
	}

	public Timestamp getInsuranceRemindDate() {
		return this.insuranceRemindDate;
	}

	public void setInsuranceRemindDate(Timestamp insuranceRemindDate) {
		this.insuranceRemindDate = insuranceRemindDate;
	}

	public String getInsuranceReminder() {
		return this.insuranceReminder;
	}

	public void setInsuranceReminder(String insuranceReminder) {
		this.insuranceReminder = insuranceReminder;
	}

	public String getInsuranceRemindMode() {
		return this.insuranceRemindMode;
	}

	public void setInsuranceRemindMode(String insuranceRemindMode) {
		this.insuranceRemindMode = insuranceRemindMode;
	}

	public String getInsuranceRemindResult() {
		return this.insuranceRemindResult;
	}

	public void setInsuranceRemindResult(String insuranceRemindResult) {
		this.insuranceRemindResult = insuranceRemindResult;
	}

	public String getInsuranceRemindHistory() {
		return this.insuranceRemindHistory;
	}

	public void setInsuranceRemindHistory(String insuranceRemindHistory) {
		this.insuranceRemindHistory = insuranceRemindHistory;
	}

	public Timestamp getYearCensorDate() {
		return this.yearCensorDate;
	}

	public void setYearCensorDate(Timestamp yearCensorDate) {
		this.yearCensorDate = yearCensorDate;
	}

	public Timestamp getYearCensorRemindDate() {
		return this.yearCensorRemindDate;
	}

	public void setYearCensorRemindDate(Timestamp yearCensorRemindDate) {
		this.yearCensorRemindDate = yearCensorRemindDate;
	}

	public String getYearCensorReminder() {
		return this.yearCensorReminder;
	}

	public void setYearCensorReminder(String yearCensorReminder) {
		this.yearCensorReminder = yearCensorReminder;
	}

	public String getYearCensorRemindMode() {
		return this.yearCensorRemindMode;
	}

	public void setYearCensorRemindMode(String yearCensorRemindMode) {
		this.yearCensorRemindMode = yearCensorRemindMode;
	}

	public String getYearCensorRemindResult() {
		return this.yearCensorRemindResult;
	}

	public void setYearCensorRemindResult(String yearCensorRemindResult) {
		this.yearCensorRemindResult = yearCensorRemindResult;
	}

	public Timestamp getDrivingLicenseRegisterDate() {
		return this.drivingLicenseRegisterDate;
	}

	public void setDrivingLicenseRegisterDate(Timestamp drivingLicenseRegisterDate) {
		this.drivingLicenseRegisterDate = drivingLicenseRegisterDate;
	}

	public Double getLastServiceMileage() {
		return this.lastServiceMileage;
	}

	public void setLastServiceMileage(Double lastServiceMileage) {
		this.lastServiceMileage = lastServiceMileage;
	}

	public Timestamp getLastServiceTime() {
		return this.lastServiceTime;
	}

	public void setLastServiceTime(Timestamp lastServiceTime) {
		this.lastServiceTime = lastServiceTime;
	}

	public Timestamp getLastMaintainDate() {
		return this.lastMaintainDate;
	}

	public void setLastMaintainDate(Timestamp lastMaintainDate) {
		this.lastMaintainDate = lastMaintainDate;
	}

	public Double getLastMaintainMileage() {
		return this.lastMaintainMileage;
	}

	public void setLastMaintainMileage(Double lastMaintainMileage) {
		this.lastMaintainMileage = lastMaintainMileage;
	}

	public Timestamp getMaintainDateLast() {
		return this.maintainDateLast;
	}

	public void setMaintainDateLast(Timestamp maintainDateLast) {
		this.maintainDateLast = maintainDateLast;
	}

	public Timestamp getMaintainRemindDate() {
		return this.maintainRemindDate;
	}

	public void setMaintainRemindDate(Timestamp maintainRemindDate) {
		this.maintainRemindDate = maintainRemindDate;
	}

	public String getMaintainReminder() {
		return this.maintainReminder;
	}

	public void setMaintainReminder(String maintainReminder) {
		this.maintainReminder = maintainReminder;
	}

	public Boolean getMaintainRemindFlag() {
		return this.maintainRemindFlag;
	}

	public void setMaintainRemindFlag(Boolean maintainRemindFlag) {
		this.maintainRemindFlag = maintainRemindFlag;
	}

	public Double getAverageMileage() {
		return this.averageMileage;
	}

	public void setAverageMileage(Double averageMileage) {
		this.averageMileage = averageMileage;
	}

	public Double getVehicleIntegral() {
		return this.vehicleIntegral;
	}

	public void setVehicleIntegral(Double vehicleIntegral) {
		this.vehicleIntegral = vehicleIntegral;
	}

	public Double getVehicleIntegralUsed() {
		return this.vehicleIntegralUsed;
	}

	public void setVehicleIntegralUsed(Double vehicleIntegralUsed) {
		this.vehicleIntegralUsed = vehicleIntegralUsed;
	}

	public String getTicketNo() {
		return this.ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Double getAverageMilege() {
		return this.averageMilege;
	}

	public void setAverageMilege(Double averageMilege) {
		this.averageMilege = averageMilege;
	}

	public Double getMaintainMileageNext() {
		return this.maintainMileageNext;
	}

	public void setMaintainMileageNext(Double maintainMileageNext) {
		this.maintainMileageNext = maintainMileageNext;
	}

	public Timestamp getMaintainDateNext() {
		return this.maintainDateNext;
	}

	public void setMaintainDateNext(Timestamp maintainDateNext) {
		this.maintainDateNext = maintainDateNext;
	}

	public Short getRemindResult() {
		return this.remindResult;
	}

	public void setRemindResult(Short remindResult) {
		this.remindResult = remindResult;
	}

	public Short getRemindType() {
		return this.remindType;
	}

	public void setRemindType(Short remindType) {
		this.remindType = remindType;
	}

	public Boolean getVehicleBelongTo() {
		return this.vehicleBelongTo;
	}

	public void setVehicleBelongTo(Boolean vehicleBelongTo) {
		this.vehicleBelongTo = vehicleBelongTo;
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

	public Integer getBackAllow() {
		return this.backAllow;
	}

	public void setBackAllow(Integer backAllow) {
		this.backAllow = backAllow;
	}

	public String getVehicleSaleCode() {
		return this.vehicleSaleCode;
	}

	public void setVehicleSaleCode(String vehicleSaleCode) {
		this.vehicleSaleCode = vehicleSaleCode;
	}

	public String getLastServiceReceiver() {
		return this.lastServiceReceiver;
	}

	public void setLastServiceReceiver(String lastServiceReceiver) {
		this.lastServiceReceiver = lastServiceReceiver;
	}

	public String getOVehicleMemberId() {
		return this.OVehicleMemberId;
	}

	public void setOVehicleMemberId(String OVehicleMemberId) {
		this.OVehicleMemberId = OVehicleMemberId;
	}

	public String getOVehicleMemberType() {
		return this.OVehicleMemberType;
	}

	public void setOVehicleMemberType(String OVehicleMemberType) {
		this.OVehicleMemberType = OVehicleMemberType;
	}

	public String getOObjectNo() {
		return this.OObjectNo;
	}

	public void setOObjectNo(String OObjectNo) {
		this.OObjectNo = OObjectNo;
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

	public Timestamp getCompulsoryInsuranceEndDate() {
		return this.compulsoryInsuranceEndDate;
	}

	public void setCompulsoryInsuranceEndDate(Timestamp compulsoryInsuranceEndDate) {
		this.compulsoryInsuranceEndDate = compulsoryInsuranceEndDate;
	}

	public Timestamp getOperationCertificateRecordDate() {
		return this.operationCertificateRecordDate;
	}

	public void setOperationCertificateRecordDate(Timestamp operationCertificateRecordDate) {
		this.operationCertificateRecordDate = operationCertificateRecordDate;
	}

	public Timestamp getCountMaintainDateNext() {
		return this.countMaintainDateNext;
	}

	public void setCountMaintainDateNext(Timestamp countMaintainDateNext) {
		this.countMaintainDateNext = countMaintainDateNext;
	}

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public Timestamp getCompulsoryInsuranceBeginDate() {
		return this.compulsoryInsuranceBeginDate;
	}

	public void setCompulsoryInsuranceBeginDate(Timestamp compulsoryInsuranceBeginDate) {
		this.compulsoryInsuranceBeginDate = compulsoryInsuranceBeginDate;
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

	public String getDrivingLicenseNumber() {
		return this.drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}

	public Timestamp getInvoiceTime() {
		return this.invoiceTime;
	}

	public void setInvoiceTime(Timestamp invoiceTime) {
		this.invoiceTime = invoiceTime;
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

	public String getServiceRange() {
		return this.serviceRange;
	}

	public void setServiceRange(String serviceRange) {
		this.serviceRange = serviceRange;
	}

	public String getRelatedVehicleCard() {
		return this.relatedVehicleCard;
	}

	public void setRelatedVehicleCard(String relatedVehicleCard) {
		this.relatedVehicleCard = relatedVehicleCard;
	}

	public String getVehicleCardModel() {
		return vehicleCardModel;
	}

	public void setVehicleCardModel(String vehicleCardModel) {
		this.vehicleCardModel = vehicleCardModel;
	}

}
