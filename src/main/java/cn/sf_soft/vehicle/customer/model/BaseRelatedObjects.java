package cn.sf_soft.vehicle.customer.model;

import org.hibernate.annotations.Formula;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 往来对象
 * @author caigx
 */
@Entity
@Table(name = "base_related_objects", schema = "dbo")
public class BaseRelatedObjects implements java.io.Serializable {

	// Fields

	private String objectId;
	private String parentId;
	private String fullId;
	private String stationId;
	private Boolean isParent;
	private Short status;
	private String objectNo;
	private String objectName;
	private String namePinyin;
	private String shortName;
	private String companyName;
	private String sex;
	private String nation;
	private Timestamp birthday;
	private Boolean isLunarBirthday;
	private String education;
	private String occupation;
	private String position;
	private String profession;
	private String certificateType;
	private String certificateNo;
	private String linkman;
	private String phone;
	private String mobile;
	private String fax;
	private String icq;
	private String wechat;
	private String url;
	private String email;
	private String postalcode;
	private String address;
	private String residenceAddress;
	private String companyAddress;
	private String area;
	private String city;
	private String province;
	private String country;
	private String accountBank;
	private String accountNo;
	private String legalPerson;
	private String businessLicense;
	private String taxRegisterNo;
	private Integer objectType;
	private Integer objectKind;
	private Short objectNature;
	private String objectProperty;
	private String moduleId;
	private Short customerType;
	private Boolean isCompetitive;
	private String income;
	private String hobby;
	private Double registeredCapital;
	private Double fixedAssets;
	private String mainBusiness;
	private String businessScope;
	private Integer staffNumber;
	private Integer shareholderNumber;
	private String topShareholder;
	private Double topShares;
	private String vehicleLinkman;
	private String vehiclePhone;
	private String partLinkman;
	private String partPhone;
	private String serviceLinkman;
	private String servicePhone;
	private String insuranceLinkman;
	private String insurancePhone;
	private String memberTypeId;
	private String memberType;
	private String memberNo;
	private Timestamp memberBeginTime;
	private Timestamp memberEndTime;
	private String introducerId;
	private String introducerNo;
	private String introducerName;
	private String introducerLinkman;
	private String relatedCustomer;
	private String relatedBusiness;
	private String serviceSite;
	private String insuranceChannel;
	private Timestamp planBackTime;
	private Boolean backFlag;
	private Boolean autoChangeCustomerFlag;
	private Integer deliveryPeriod;
	private Integer emergentDeliveryPeriod;
	private Integer deliveryDays;
	private Integer billingDay;
	private Integer repayMonth;
	private Integer repayDay;
	private String remark;
	private String createStationId;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String errorCode;
	private String errorMsg;
	private String dfsSupplierId;
	private String customerKind;
	private Short partPriceSystem;
	private Double longitude;
	private Double latitude;
	private String locationAddress;
	private Short partSaleType;
	private Short insurancePayType;
	private String maintainerName;
	private String maintainerId;
	private String customerSource;
	private String customerGroupId;
	private Boolean isLeader;

	/**
	 * 客户分组名称，计算字段
	 */
	private String customerGroupName;

	/**
	 * 客户端类型,记录新增的操作的客户端类型（PC H5）
	 */
	private String clientType;

	private String createUnitId;
	// Constructors
	public BaseRelatedObjects() {
	}



	// Property accessors
	@Id
	@Column(name = "object_id", unique = true, nullable = false, length = 40)
	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@Column(name = "parent_id", length = 40)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "full_id", nullable = false)
	public String getFullId() {
		return this.fullId;
	}

	public void setFullId(String fullId) {
		this.fullId = fullId;
	}

	@Column(name = "station_id", nullable = false)
	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	@Column(name = "is_parent", nullable = false)
	public Boolean getIsParent() {
		return this.isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	@Column(name = "status", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "object_no", unique = true, length = 40)
	public String getObjectNo() {
		return this.objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	@Column(name = "object_name", length = 40)
	public String getObjectName() {
		return this.objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Column(name = "name_pinyin", length = 40)
	public String getNamePinyin() {
		return this.namePinyin;
	}

	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}

	@Column(name = "short_name", length = 40)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "company_name", length = 40)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "sex", length = 10)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "nation", length = 10)
	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "birthday", length = 23)
	public Timestamp getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	@Column(name = "is_lunar_birthday")
	public Boolean getIsLunarBirthday() {
		return this.isLunarBirthday;
	}

	public void setIsLunarBirthday(Boolean isLunarBirthday) {
		this.isLunarBirthday = isLunarBirthday;
	}

	@Column(name = "education", length = 10)
	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "occupation", length = 10)
	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	@Column(name = "position", length = 10)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "profession", length = 100)
	public String getProfession() {
		return this.profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	@Column(name = "certificate_type", length = 10)
	public String getCertificateType() {
		return this.certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	@Column(name = "certificate_no", length = 40)
	public String getCertificateNo() {
		return this.certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	@Column(name = "linkman", length = 20)
	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "phone", length = 40)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "mobile", length = 40)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "fax", length = 40)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "icq", length = 40)
	public String getIcq() {
		return this.icq;
	}

	public void setIcq(String icq) {
		this.icq = icq;
	}

	@Column(name = "wechat", length = 40)
	public String getWechat() {
		return this.wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	@Column(name = "url", length = 40)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "email", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "postalcode", length = 40)
	public String getPostalcode() {
		return this.postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	@Column(name = "address", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "residence_address", length = 100)
	public String getResidenceAddress() {
		return this.residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	@Column(name = "company_address", length = 100)
	public String getCompanyAddress() {
		return this.companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	@Column(name = "area", length = 20)
	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "city", length = 20)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "province", length = 20)
	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "country", length = 10)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "account_bank", length = 40)
	public String getAccountBank() {
		return this.accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	@Column(name = "account_no", length = 40)
	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Column(name = "legal_person", length = 10)
	public String getLegalPerson() {
		return this.legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	@Column(name = "business_license", length = 40)
	public String getBusinessLicense() {
		return this.businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	@Column(name = "tax_register_no", length = 40)
	public String getTaxRegisterNo() {
		return this.taxRegisterNo;
	}

	public void setTaxRegisterNo(String taxRegisterNo) {
		this.taxRegisterNo = taxRegisterNo;
	}

	@Column(name = "object_type", nullable = false)
	public Integer getObjectType() {
		return this.objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	@Column(name = "object_kind", nullable = false)
	public Integer getObjectKind() {
		return this.objectKind;
	}

	public void setObjectKind(Integer objectKind) {
		this.objectKind = objectKind;
	}

	@Column(name = "object_nature")
	public Short getObjectNature() {
		return this.objectNature;
	}

	public void setObjectNature(Short objectNature) {
		this.objectNature = objectNature;
	}

	@Column(name = "object_property", length = 10)
	public String getObjectProperty() {
		return this.objectProperty;
	}

	public void setObjectProperty(String objectProperty) {
		this.objectProperty = objectProperty;
	}

	@Column(name = "module_id", length = 10)
	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "customer_type")
	public Short getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(Short customerType) {
		this.customerType = customerType;
	}

	@Column(name = "is_competitive")
	public Boolean getIsCompetitive() {
		return this.isCompetitive;
	}

	public void setIsCompetitive(Boolean isCompetitive) {
		this.isCompetitive = isCompetitive;
	}

	@Column(name = "income", length = 40)
	public String getIncome() {
		return this.income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	@Column(name = "hobby", length = 40)
	public String getHobby() {
		return this.hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	@Column(name = "registered_capital")
	public Double getRegisteredCapital() {
		return this.registeredCapital;
	}

	public void setRegisteredCapital(Double registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	@Column(name = "fixed_assets")
	public Double getFixedAssets() {
		return this.fixedAssets;
	}

	public void setFixedAssets(Double fixedAssets) {
		this.fixedAssets = fixedAssets;
	}

	@Column(name = "main_business", length = 200)
	public String getMainBusiness() {
		return this.mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	@Column(name = "business_scope", length = 200)
	public String getBusinessScope() {
		return this.businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	@Column(name = "staff_number")
	public Integer getStaffNumber() {
		return this.staffNumber;
	}

	public void setStaffNumber(Integer staffNumber) {
		this.staffNumber = staffNumber;
	}

	@Column(name = "shareholder_number")
	public Integer getShareholderNumber() {
		return this.shareholderNumber;
	}

	public void setShareholderNumber(Integer shareholderNumber) {
		this.shareholderNumber = shareholderNumber;
	}

	@Column(name = "top_shareholder", length = 40)
	public String getTopShareholder() {
		return this.topShareholder;
	}

	public void setTopShareholder(String topShareholder) {
		this.topShareholder = topShareholder;
	}

	@Column(name = "top_shares", precision = 9, scale = 4)
	public Double getTopShares() {
		return this.topShares;
	}

	public void setTopShares(Double topShares) {
		this.topShares = topShares;
	}

	@Column(name = "vehicle_linkman", length = 20)
	public String getVehicleLinkman() {
		return this.vehicleLinkman;
	}

	public void setVehicleLinkman(String vehicleLinkman) {
		this.vehicleLinkman = vehicleLinkman;
	}

	@Column(name = "vehicle_phone", length = 40)
	public String getVehiclePhone() {
		return this.vehiclePhone;
	}

	public void setVehiclePhone(String vehiclePhone) {
		this.vehiclePhone = vehiclePhone;
	}

	@Column(name = "part_linkman", length = 20)
	public String getPartLinkman() {
		return this.partLinkman;
	}

	public void setPartLinkman(String partLinkman) {
		this.partLinkman = partLinkman;
	}

	@Column(name = "part_phone", length = 40)
	public String getPartPhone() {
		return this.partPhone;
	}

	public void setPartPhone(String partPhone) {
		this.partPhone = partPhone;
	}

	@Column(name = "service_linkman", length = 20)
	public String getServiceLinkman() {
		return this.serviceLinkman;
	}

	public void setServiceLinkman(String serviceLinkman) {
		this.serviceLinkman = serviceLinkman;
	}

	@Column(name = "service_phone", length = 40)
	public String getServicePhone() {
		return this.servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	@Column(name = "insurance_linkman", length = 20)
	public String getInsuranceLinkman() {
		return this.insuranceLinkman;
	}

	public void setInsuranceLinkman(String insuranceLinkman) {
		this.insuranceLinkman = insuranceLinkman;
	}

	@Column(name = "insurance_phone", length = 40)
	public String getInsurancePhone() {
		return this.insurancePhone;
	}

	public void setInsurancePhone(String insurancePhone) {
		this.insurancePhone = insurancePhone;
	}

	@Column(name = "member_type_id", length = 40)
	public String getMemberTypeId() {
		return this.memberTypeId;
	}

	public void setMemberTypeId(String memberTypeId) {
		this.memberTypeId = memberTypeId;
	}

	@Column(name = "member_type", length = 20)
	public String getMemberType() {
		return this.memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	@Column(name = "member_no", length = 40)
	public String getMemberNo() {
		return this.memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	@Column(name = "member_begin_time", length = 23)
	public Timestamp getMemberBeginTime() {
		return this.memberBeginTime;
	}

	public void setMemberBeginTime(Timestamp memberBeginTime) {
		this.memberBeginTime = memberBeginTime;
	}

	@Column(name = "member_end_time", length = 23)
	public Timestamp getMemberEndTime() {
		return this.memberEndTime;
	}

	public void setMemberEndTime(Timestamp memberEndTime) {
		this.memberEndTime = memberEndTime;
	}

	@Column(name = "introducer_id", length = 40)
	public String getIntroducerId() {
		return this.introducerId;
	}

	public void setIntroducerId(String introducerId) {
		this.introducerId = introducerId;
	}

	@Column(name = "introducer_no", length = 40)
	public String getIntroducerNo() {
		return this.introducerNo;
	}

	public void setIntroducerNo(String introducerNo) {
		this.introducerNo = introducerNo;
	}

	@Column(name = "introducer_name", length = 40)
	public String getIntroducerName() {
		return this.introducerName;
	}

	public void setIntroducerName(String introducerName) {
		this.introducerName = introducerName;
	}

	@Column(name = "introducer_linkman", length = 40)
	public String getIntroducerLinkman() {
		return this.introducerLinkman;
	}

	public void setIntroducerLinkman(String introducerLinkman) {
		this.introducerLinkman = introducerLinkman;
	}

	@Column(name = "related_customer", length = 40)
	public String getRelatedCustomer() {
		return this.relatedCustomer;
	}

	public void setRelatedCustomer(String relatedCustomer) {
		this.relatedCustomer = relatedCustomer;
	}

	@Column(name = "related_business", length = 40)
	public String getRelatedBusiness() {
		return this.relatedBusiness;
	}

	public void setRelatedBusiness(String relatedBusiness) {
		this.relatedBusiness = relatedBusiness;
	}

	@Column(name = "service_site", length = 40)
	public String getServiceSite() {
		return this.serviceSite;
	}

	public void setServiceSite(String serviceSite) {
		this.serviceSite = serviceSite;
	}

	@Column(name = "insurance_channel", length = 40)
	public String getInsuranceChannel() {
		return this.insuranceChannel;
	}

	public void setInsuranceChannel(String insuranceChannel) {
		this.insuranceChannel = insuranceChannel;
	}

	@Column(name = "plan_back_time", length = 23)
	public Timestamp getPlanBackTime() {
		return this.planBackTime;
	}

	public void setPlanBackTime(Timestamp planBackTime) {
		this.planBackTime = planBackTime;
	}

	@Column(name = "back_flag")
	public Boolean getBackFlag() {
		return this.backFlag;
	}

	public void setBackFlag(Boolean backFlag) {
		this.backFlag = backFlag;
	}

	@Column(name = "auto_change_customer_flag")
	public Boolean getAutoChangeCustomerFlag() {
		return this.autoChangeCustomerFlag;
	}

	public void setAutoChangeCustomerFlag(Boolean autoChangeCustomerFlag) {
		this.autoChangeCustomerFlag = autoChangeCustomerFlag;
	}

	@Column(name = "delivery_period")
	public Integer getDeliveryPeriod() {
		return this.deliveryPeriod;
	}

	public void setDeliveryPeriod(Integer deliveryPeriod) {
		this.deliveryPeriod = deliveryPeriod;
	}

	@Column(name = "emergent_delivery_period")
	public Integer getEmergentDeliveryPeriod() {
		return this.emergentDeliveryPeriod;
	}

	public void setEmergentDeliveryPeriod(Integer emergentDeliveryPeriod) {
		this.emergentDeliveryPeriod = emergentDeliveryPeriod;
	}

	@Column(name = "delivery_days")
	public Integer getDeliveryDays() {
		return this.deliveryDays;
	}

	public void setDeliveryDays(Integer deliveryDays) {
		this.deliveryDays = deliveryDays;
	}

	@Column(name = "billing_day")
	public Integer getBillingDay() {
		return this.billingDay;
	}

	public void setBillingDay(Integer billingDay) {
		this.billingDay = billingDay;
	}

	@Column(name = "repay_month")
	public Integer getRepayMonth() {
		return this.repayMonth;
	}

	public void setRepayMonth(Integer repayMonth) {
		this.repayMonth = repayMonth;
	}

	@Column(name = "repay_day")
	public Integer getRepayDay() {
		return this.repayDay;
	}

	public void setRepayDay(Integer repayDay) {
		this.repayDay = repayDay;
	}

	@Column(name = "remark", length = 1073741823)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "create_station_id", length = 10)
	public String getCreateStationId() {
		return this.createStationId;
	}

	public void setCreateStationId(String createStationId) {
		this.createStationId = createStationId;
	}

	@Column(name = "creator", length = 20)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "create_time", length = 23)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "modifier", length = 20)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "modify_time", length = 23)
	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "error_code", length = 20)
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name = "error_msg", length = 1073741823)
	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Column(name = "DFS_supplier_id", length = 40)
	public String getDfsSupplierId() {
		return this.dfsSupplierId;
	}

	public void setDfsSupplierId(String dfsSupplierId) {
		this.dfsSupplierId = dfsSupplierId;
	}

	@Column(name = "customer_kind", length = 100)
	public String getCustomerKind() {
		return this.customerKind;
	}

	public void setCustomerKind(String customerKind) {
		this.customerKind = customerKind;
	}

	@Column(name = "part_price_system")
	public Short getPartPriceSystem() {
		return this.partPriceSystem;
	}

	public void setPartPriceSystem(Short partPriceSystem) {
		this.partPriceSystem = partPriceSystem;
	}

	@Column(name = "longitude", precision = 15, scale = 0)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", precision = 15, scale = 0)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "location_address", length = 100)
	public String getLocationAddress() {
		return this.locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	@Column(name = "part_sale_type")
	public Short getPartSaleType() {
		return this.partSaleType;
	}

	public void setPartSaleType(Short partSaleType) {
		this.partSaleType = partSaleType;
	}

	@Column(name = "insurance_pay_type")
	public Short getInsurancePayType() {
		return this.insurancePayType;
	}

	public void setInsurancePayType(Short insurancePayType) {
		this.insurancePayType = insurancePayType;
	}

	@Column(name = "maintainer_name", length = 1073741823)
	public String getMaintainerName() {
		return this.maintainerName;
	}

	public void setMaintainerName(String maintainerName) {
		this.maintainerName = maintainerName;
	}

	@Column(name = "maintainer_id", length = 1073741823)
	public String getMaintainerId() {
		return this.maintainerId;
	}

	public void setMaintainerId(String maintainerId) {
		this.maintainerId = maintainerId;
	}

	@Column(name = "customer_source", length = 100)
	public String getCustomerSource() {
		return this.customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	@Column(name = "customer_group_id", length = 40)
	public String getCustomerGroupId() {
		return this.customerGroupId;
	}

	public void setCustomerGroupId(String customerGroupId) {
		this.customerGroupId = customerGroupId;
	}

	@Column(name = "is_leader")
	public Boolean getIsLeader() {
		return this.isLeader;
	}

	public void setIsLeader(Boolean isLeader) {
		this.isLeader = isLeader;
	}

	@Formula("(select t.customer_group_name from base_customer_groups t where t.customer_group_id=customer_group_id)")
	public String getCustomerGroupName() {
		return customerGroupName;
	}

	public void setCustomerGroupName(String customerGroupName) {
		this.customerGroupName = customerGroupName;
	}

	@Column(name = "client_type")
	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	@Column(name="create_unit_id")
	public String getCreateUnitId() {
		return createUnitId;
	}

	public void setCreateUnitId(String createUnitId) {
		this.createUnitId = createUnitId;
	}
}