package cn.sf_soft.vehicle.customer.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by henry on 2018/1/25.
 */
@Entity
@Table(name = "vw_stat_effective_customer", schema = "dbo", catalog = "YPZ")
public class VwStatEffectiveCustomer implements Serializable  {

    private static final long serialVersionUID = -3441403404246385231L;

    private String tableName;
    private String objectId;
    private String parentId;
    private String fullId;
    private String stationId;
    private Boolean isParent;
    private Byte status;
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
    private Byte objectNature;
    private String objectProperty;
    private String moduleId;
    private Byte customerType;
    private Boolean isCompetitive;
    private String income;
    private String hobby;
    private BigDecimal registeredCapital;
    private BigDecimal fixedAssets;
    private String mainBusiness;
    private String businessScope;
    private Integer staffNumber;
    private Integer shareholderNumber;
    private String topShareholder;
    private BigDecimal topShares;
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
    private Byte partPriceSystem;
    private Double longitude;
    private Double latitude;
    private String locationAddress;
    private Byte partSaleType;

    @Basic
    @Column(name = "table_name", nullable = false, length = 20)
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Id
    @Basic
    @Column(name = "object_id", nullable = true, length = 40)
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Basic
    @Column(name = "parent_id", nullable = true, length = 40)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "full_id", nullable = true, length = 2147483647)
    public String getFullId() {
        return fullId;
    }

    public void setFullId(String fullId) {
        this.fullId = fullId;
    }

    @Basic
    @Column(name = "station_id", nullable = true, length = 2147483647)
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "is_parent", nullable = true)
    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean parent) {
        isParent = parent;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "object_no", nullable = true, length = 40)
    public String getObjectNo() {
        return objectNo;
    }

    public void setObjectNo(String objectNo) {
        this.objectNo = objectNo;
    }

    @Basic
    @Column(name = "object_name", nullable = true, length = 40)
    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Basic
    @Column(name = "name_pinyin", nullable = true, length = 40)
    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    @Basic
    @Column(name = "short_name", nullable = true, length = 40)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "company_name", nullable = true, length = 40)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "sex", nullable = true, length = 10)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "nation", nullable = true, length = 10)
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Basic
    @Column(name = "birthday", nullable = true)
    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "is_lunar_birthday", nullable = true)
    public Boolean getIsLunarBirthday() {
        return isLunarBirthday;
    }

    public void setIsLunarBirthday(Boolean lunarBirthday) {
        isLunarBirthday = lunarBirthday;
    }

    @Basic
    @Column(name = "education", nullable = true, length = 10)
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Basic
    @Column(name = "occupation", nullable = true, length = 10)
    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Basic
    @Column(name = "position", nullable = true, length = 10)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "profession", nullable = true, length = 100)
    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Basic
    @Column(name = "certificate_type", nullable = true, length = 10)
    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    @Basic
    @Column(name = "certificate_no", nullable = true, length = 40)
    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    @Basic
    @Column(name = "linkman", nullable = true, length = 20)
    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 40)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "mobile", nullable = true, length = 20)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "fax", nullable = true, length = 40)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "icq", nullable = true, length = 20)
    public String getIcq() {
        return icq;
    }

    public void setIcq(String icq) {
        this.icq = icq;
    }

    @Basic
    @Column(name = "wechat", nullable = true, length = 40)
    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Basic
    @Column(name = "url", nullable = true, length = 40)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 40)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "postalcode", nullable = true, length = 10)
    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 100)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "residence_address", nullable = true, length = 100)
    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    @Basic
    @Column(name = "company_address", nullable = true, length = 100)
    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    @Basic
    @Column(name = "area", nullable = true, length = 20)
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Basic
    @Column(name = "city", nullable = true, length = 20)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "province", nullable = true, length = 20)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "country", nullable = true, length = 10)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "account_bank", nullable = true, length = 40)
    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    @Basic
    @Column(name = "account_no", nullable = true, length = 40)
    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @Basic
    @Column(name = "legal_person", nullable = true, length = 10)
    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    @Basic
    @Column(name = "business_license", nullable = true, length = 40)
    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    @Basic
    @Column(name = "tax_register_no", nullable = true, length = 40)
    public String getTaxRegisterNo() {
        return taxRegisterNo;
    }

    public void setTaxRegisterNo(String taxRegisterNo) {
        this.taxRegisterNo = taxRegisterNo;
    }

    @Basic
    @Column(name = "object_type", nullable = true)
    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    @Basic
    @Column(name = "object_kind", nullable = true)
    public Integer getObjectKind() {
        return objectKind;
    }

    public void setObjectKind(Integer objectKind) {
        this.objectKind = objectKind;
    }

    @Basic
    @Column(name = "object_nature", nullable = true)
    public Byte getObjectNature() {
        return objectNature;
    }

    public void setObjectNature(Byte objectNature) {
        this.objectNature = objectNature;
    }

    @Basic
    @Column(name = "object_property", nullable = true, length = 10)
    public String getObjectProperty() {
        return objectProperty;
    }

    public void setObjectProperty(String objectProperty) {
        this.objectProperty = objectProperty;
    }

    @Basic
    @Column(name = "module_id", nullable = true, length = 10)
    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    @Basic
    @Column(name = "customer_type", nullable = true)
    public Byte getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Byte customerType) {
        this.customerType = customerType;
    }

    @Basic
    @Column(name = "is_competitive", nullable = true)
    public Boolean getIsCompetitive() {
        return isCompetitive;
    }

    public void setIsCompetitive(Boolean competitive) {
        isCompetitive = competitive;
    }

    @Basic
    @Column(name = "income", nullable = true, length = 40)
    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    @Basic
    @Column(name = "hobby", nullable = true, length = 40)
    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Basic
    @Column(name = "registered_capital", nullable = true, precision = 2)
    public BigDecimal getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    @Basic
    @Column(name = "fixed_assets", nullable = true, precision = 2)
    public BigDecimal getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(BigDecimal fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    @Basic
    @Column(name = "main_business", nullable = true, length = 200)
    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    @Basic
    @Column(name = "business_scope", nullable = true, length = 200)
    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    @Basic
    @Column(name = "staff_number", nullable = true)
    public Integer getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(Integer staffNumber) {
        this.staffNumber = staffNumber;
    }

    @Basic
    @Column(name = "shareholder_number", nullable = true)
    public Integer getShareholderNumber() {
        return shareholderNumber;
    }

    public void setShareholderNumber(Integer shareholderNumber) {
        this.shareholderNumber = shareholderNumber;
    }

    @Basic
    @Column(name = "top_shareholder", nullable = true, length = 40)
    public String getTopShareholder() {
        return topShareholder;
    }

    public void setTopShareholder(String topShareholder) {
        this.topShareholder = topShareholder;
    }

    @Basic
    @Column(name = "top_shares", nullable = true, precision = 4)
    public BigDecimal getTopShares() {
        return topShares;
    }

    public void setTopShares(BigDecimal topShares) {
        this.topShares = topShares;
    }

    @Basic
    @Column(name = "vehicle_linkman", nullable = true, length = 20)
    public String getVehicleLinkman() {
        return vehicleLinkman;
    }

    public void setVehicleLinkman(String vehicleLinkman) {
        this.vehicleLinkman = vehicleLinkman;
    }

    @Basic
    @Column(name = "vehicle_phone", nullable = true, length = 40)
    public String getVehiclePhone() {
        return vehiclePhone;
    }

    public void setVehiclePhone(String vehiclePhone) {
        this.vehiclePhone = vehiclePhone;
    }

    @Basic
    @Column(name = "part_linkman", nullable = true, length = 20)
    public String getPartLinkman() {
        return partLinkman;
    }

    public void setPartLinkman(String partLinkman) {
        this.partLinkman = partLinkman;
    }

    @Basic
    @Column(name = "part_phone", nullable = true, length = 40)
    public String getPartPhone() {
        return partPhone;
    }

    public void setPartPhone(String partPhone) {
        this.partPhone = partPhone;
    }

    @Basic
    @Column(name = "service_linkman", nullable = true, length = 20)
    public String getServiceLinkman() {
        return serviceLinkman;
    }

    public void setServiceLinkman(String serviceLinkman) {
        this.serviceLinkman = serviceLinkman;
    }

    @Basic
    @Column(name = "service_phone", nullable = true, length = 40)
    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    @Basic
    @Column(name = "insurance_linkman", nullable = true, length = 20)
    public String getInsuranceLinkman() {
        return insuranceLinkman;
    }

    public void setInsuranceLinkman(String insuranceLinkman) {
        this.insuranceLinkman = insuranceLinkman;
    }

    @Basic
    @Column(name = "insurance_phone", nullable = true, length = 40)
    public String getInsurancePhone() {
        return insurancePhone;
    }

    public void setInsurancePhone(String insurancePhone) {
        this.insurancePhone = insurancePhone;
    }

    @Basic
    @Column(name = "member_type_id", nullable = true, length = 40)
    public String getMemberTypeId() {
        return memberTypeId;
    }

    public void setMemberTypeId(String memberTypeId) {
        this.memberTypeId = memberTypeId;
    }

    @Basic
    @Column(name = "member_type", nullable = true, length = 20)
    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    @Basic
    @Column(name = "member_no", nullable = true, length = 20)
    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    @Basic
    @Column(name = "member_begin_time", nullable = true)
    public Timestamp getMemberBeginTime() {
        return memberBeginTime;
    }

    public void setMemberBeginTime(Timestamp memberBeginTime) {
        this.memberBeginTime = memberBeginTime;
    }

    @Basic
    @Column(name = "member_end_time", nullable = true)
    public Timestamp getMemberEndTime() {
        return memberEndTime;
    }

    public void setMemberEndTime(Timestamp memberEndTime) {
        this.memberEndTime = memberEndTime;
    }

    @Basic
    @Column(name = "introducer_id", nullable = true, length = 40)
    public String getIntroducerId() {
        return introducerId;
    }

    public void setIntroducerId(String introducerId) {
        this.introducerId = introducerId;
    }

    @Basic
    @Column(name = "introducer_no", nullable = true, length = 40)
    public String getIntroducerNo() {
        return introducerNo;
    }

    public void setIntroducerNo(String introducerNo) {
        this.introducerNo = introducerNo;
    }

    @Basic
    @Column(name = "introducer_name", nullable = true, length = 40)
    public String getIntroducerName() {
        return introducerName;
    }

    public void setIntroducerName(String introducerName) {
        this.introducerName = introducerName;
    }

    @Basic
    @Column(name = "introducer_linkman", nullable = true, length = 40)
    public String getIntroducerLinkman() {
        return introducerLinkman;
    }

    public void setIntroducerLinkman(String introducerLinkman) {
        this.introducerLinkman = introducerLinkman;
    }

    @Basic
    @Column(name = "related_customer", nullable = true, length = 40)
    public String getRelatedCustomer() {
        return relatedCustomer;
    }

    public void setRelatedCustomer(String relatedCustomer) {
        this.relatedCustomer = relatedCustomer;
    }

    @Basic
    @Column(name = "related_business", nullable = true, length = 40)
    public String getRelatedBusiness() {
        return relatedBusiness;
    }

    public void setRelatedBusiness(String relatedBusiness) {
        this.relatedBusiness = relatedBusiness;
    }

    @Basic
    @Column(name = "service_site", nullable = true, length = 40)
    public String getServiceSite() {
        return serviceSite;
    }

    public void setServiceSite(String serviceSite) {
        this.serviceSite = serviceSite;
    }

    @Basic
    @Column(name = "insurance_channel", nullable = true, length = 40)
    public String getInsuranceChannel() {
        return insuranceChannel;
    }

    public void setInsuranceChannel(String insuranceChannel) {
        this.insuranceChannel = insuranceChannel;
    }

    @Basic
    @Column(name = "plan_back_time", nullable = true)
    public Timestamp getPlanBackTime() {
        return planBackTime;
    }

    public void setPlanBackTime(Timestamp planBackTime) {
        this.planBackTime = planBackTime;
    }

    @Basic
    @Column(name = "back_flag", nullable = true)
    public Boolean getBackFlag() {
        return backFlag;
    }

    public void setBackFlag(Boolean backFlag) {
        this.backFlag = backFlag;
    }

    @Basic
    @Column(name = "auto_change_customer_flag", nullable = true)
    public Boolean getAutoChangeCustomerFlag() {
        return autoChangeCustomerFlag;
    }

    public void setAutoChangeCustomerFlag(Boolean autoChangeCustomerFlag) {
        this.autoChangeCustomerFlag = autoChangeCustomerFlag;
    }

    @Basic
    @Column(name = "delivery_period", nullable = true)
    public Integer getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public void setDeliveryPeriod(Integer deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    @Basic
    @Column(name = "emergent_delivery_period", nullable = true)
    public Integer getEmergentDeliveryPeriod() {
        return emergentDeliveryPeriod;
    }

    public void setEmergentDeliveryPeriod(Integer emergentDeliveryPeriod) {
        this.emergentDeliveryPeriod = emergentDeliveryPeriod;
    }

    @Basic
    @Column(name = "delivery_days", nullable = true)
    public Integer getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(Integer deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    @Basic
    @Column(name = "billing_day", nullable = true)
    public Integer getBillingDay() {
        return billingDay;
    }

    public void setBillingDay(Integer billingDay) {
        this.billingDay = billingDay;
    }

    @Basic
    @Column(name = "repay_month", nullable = true)
    public Integer getRepayMonth() {
        return repayMonth;
    }

    public void setRepayMonth(Integer repayMonth) {
        this.repayMonth = repayMonth;
    }

    @Basic
    @Column(name = "repay_day", nullable = true)
    public Integer getRepayDay() {
        return repayDay;
    }

    public void setRepayDay(Integer repayDay) {
        this.repayDay = repayDay;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 2147483647)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "create_station_id", nullable = true, length = 10)
    public String getCreateStationId() {
        return createStationId;
    }

    public void setCreateStationId(String createStationId) {
        this.createStationId = createStationId;
    }

    @Basic
    @Column(name = "creator", nullable = true, length = 20)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modifier", nullable = true, length = 20)
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Basic
    @Column(name = "modify_time", nullable = true)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "error_code", nullable = true, length = 20)
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Basic
    @Column(name = "error_msg", nullable = true, length = 2147483647)
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Basic
    @Column(name = "DFS_supplier_id", nullable = true, length = 40)
    public String getDfsSupplierId() {
        return dfsSupplierId;
    }

    public void setDfsSupplierId(String dfsSupplierId) {
        this.dfsSupplierId = dfsSupplierId;
    }

    @Basic
    @Column(name = "customer_kind", nullable = true, length = 100)
    public String getCustomerKind() {
        return customerKind;
    }

    public void setCustomerKind(String customerKind) {
        this.customerKind = customerKind;
    }

    @Basic
    @Column(name = "part_price_system", nullable = true)
    public Byte getPartPriceSystem() {
        return partPriceSystem;
    }

    public void setPartPriceSystem(Byte partPriceSystem) {
        this.partPriceSystem = partPriceSystem;
    }

    @Basic
    @Column(name = "longitude", nullable = true, precision = 0)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude", nullable = true, precision = 0)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "location_address", nullable = true, length = 100)
    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VwStatEffectiveCustomer that = (VwStatEffectiveCustomer) o;

        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) return false;
        if (objectId != null ? !objectId.equals(that.objectId) : that.objectId != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (fullId != null ? !fullId.equals(that.fullId) : that.fullId != null) return false;
        if (stationId != null ? !stationId.equals(that.stationId) : that.stationId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (objectNo != null ? !objectNo.equals(that.objectNo) : that.objectNo != null) return false;
        if (objectName != null ? !objectName.equals(that.objectName) : that.objectName != null) return false;
        if (namePinyin != null ? !namePinyin.equals(that.namePinyin) : that.namePinyin != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (nation != null ? !nation.equals(that.nation) : that.nation != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (education != null ? !education.equals(that.education) : that.education != null) return false;
        if (occupation != null ? !occupation.equals(that.occupation) : that.occupation != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (profession != null ? !profession.equals(that.profession) : that.profession != null) return false;
        if (certificateType != null ? !certificateType.equals(that.certificateType) : that.certificateType != null)
            return false;
        if (certificateNo != null ? !certificateNo.equals(that.certificateNo) : that.certificateNo != null)
            return false;
        if (linkman != null ? !linkman.equals(that.linkman) : that.linkman != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (icq != null ? !icq.equals(that.icq) : that.icq != null) return false;
        if (wechat != null ? !wechat.equals(that.wechat) : that.wechat != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (postalcode != null ? !postalcode.equals(that.postalcode) : that.postalcode != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (residenceAddress != null ? !residenceAddress.equals(that.residenceAddress) : that.residenceAddress != null)
            return false;
        if (companyAddress != null ? !companyAddress.equals(that.companyAddress) : that.companyAddress != null)
            return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (accountBank != null ? !accountBank.equals(that.accountBank) : that.accountBank != null) return false;
        if (accountNo != null ? !accountNo.equals(that.accountNo) : that.accountNo != null) return false;
        if (legalPerson != null ? !legalPerson.equals(that.legalPerson) : that.legalPerson != null) return false;
        if (businessLicense != null ? !businessLicense.equals(that.businessLicense) : that.businessLicense != null)
            return false;
        if (taxRegisterNo != null ? !taxRegisterNo.equals(that.taxRegisterNo) : that.taxRegisterNo != null)
            return false;
        if (objectType != null ? !objectType.equals(that.objectType) : that.objectType != null) return false;
        if (objectKind != null ? !objectKind.equals(that.objectKind) : that.objectKind != null) return false;
        if (objectNature != null ? !objectNature.equals(that.objectNature) : that.objectNature != null) return false;
        if (objectProperty != null ? !objectProperty.equals(that.objectProperty) : that.objectProperty != null)
            return false;
        if (moduleId != null ? !moduleId.equals(that.moduleId) : that.moduleId != null) return false;
        if (customerType != null ? !customerType.equals(that.customerType) : that.customerType != null) return false;
        if (income != null ? !income.equals(that.income) : that.income != null) return false;
        if (hobby != null ? !hobby.equals(that.hobby) : that.hobby != null) return false;
        if (registeredCapital != null ? !registeredCapital.equals(that.registeredCapital) : that.registeredCapital != null)
            return false;
        if (fixedAssets != null ? !fixedAssets.equals(that.fixedAssets) : that.fixedAssets != null) return false;
        if (mainBusiness != null ? !mainBusiness.equals(that.mainBusiness) : that.mainBusiness != null) return false;
        if (businessScope != null ? !businessScope.equals(that.businessScope) : that.businessScope != null)
            return false;
        if (staffNumber != null ? !staffNumber.equals(that.staffNumber) : that.staffNumber != null) return false;
        if (shareholderNumber != null ? !shareholderNumber.equals(that.shareholderNumber) : that.shareholderNumber != null)
            return false;
        if (topShareholder != null ? !topShareholder.equals(that.topShareholder) : that.topShareholder != null)
            return false;
        if (topShares != null ? !topShares.equals(that.topShares) : that.topShares != null) return false;
        if (vehicleLinkman != null ? !vehicleLinkman.equals(that.vehicleLinkman) : that.vehicleLinkman != null)
            return false;
        if (vehiclePhone != null ? !vehiclePhone.equals(that.vehiclePhone) : that.vehiclePhone != null) return false;
        if (partLinkman != null ? !partLinkman.equals(that.partLinkman) : that.partLinkman != null) return false;
        if (partPhone != null ? !partPhone.equals(that.partPhone) : that.partPhone != null) return false;
        if (serviceLinkman != null ? !serviceLinkman.equals(that.serviceLinkman) : that.serviceLinkman != null)
            return false;
        if (servicePhone != null ? !servicePhone.equals(that.servicePhone) : that.servicePhone != null) return false;
        if (insuranceLinkman != null ? !insuranceLinkman.equals(that.insuranceLinkman) : that.insuranceLinkman != null)
            return false;
        if (insurancePhone != null ? !insurancePhone.equals(that.insurancePhone) : that.insurancePhone != null)
            return false;
        if (memberTypeId != null ? !memberTypeId.equals(that.memberTypeId) : that.memberTypeId != null) return false;
        if (memberType != null ? !memberType.equals(that.memberType) : that.memberType != null) return false;
        if (memberNo != null ? !memberNo.equals(that.memberNo) : that.memberNo != null) return false;
        if (memberBeginTime != null ? !memberBeginTime.equals(that.memberBeginTime) : that.memberBeginTime != null)
            return false;
        if (memberEndTime != null ? !memberEndTime.equals(that.memberEndTime) : that.memberEndTime != null)
            return false;
        if (introducerId != null ? !introducerId.equals(that.introducerId) : that.introducerId != null) return false;
        if (introducerNo != null ? !introducerNo.equals(that.introducerNo) : that.introducerNo != null) return false;
        if (introducerName != null ? !introducerName.equals(that.introducerName) : that.introducerName != null)
            return false;
        if (introducerLinkman != null ? !introducerLinkman.equals(that.introducerLinkman) : that.introducerLinkman != null)
            return false;
        if (relatedCustomer != null ? !relatedCustomer.equals(that.relatedCustomer) : that.relatedCustomer != null)
            return false;
        if (relatedBusiness != null ? !relatedBusiness.equals(that.relatedBusiness) : that.relatedBusiness != null)
            return false;
        if (serviceSite != null ? !serviceSite.equals(that.serviceSite) : that.serviceSite != null) return false;
        if (insuranceChannel != null ? !insuranceChannel.equals(that.insuranceChannel) : that.insuranceChannel != null)
            return false;
        if (planBackTime != null ? !planBackTime.equals(that.planBackTime) : that.planBackTime != null) return false;
        if (backFlag != null ? !backFlag.equals(that.backFlag) : that.backFlag != null) return false;
        if (autoChangeCustomerFlag != null ? !autoChangeCustomerFlag.equals(that.autoChangeCustomerFlag) : that.autoChangeCustomerFlag != null)
            return false;
        if (deliveryPeriod != null ? !deliveryPeriod.equals(that.deliveryPeriod) : that.deliveryPeriod != null)
            return false;
        if (emergentDeliveryPeriod != null ? !emergentDeliveryPeriod.equals(that.emergentDeliveryPeriod) : that.emergentDeliveryPeriod != null)
            return false;
        if (deliveryDays != null ? !deliveryDays.equals(that.deliveryDays) : that.deliveryDays != null) return false;
        if (billingDay != null ? !billingDay.equals(that.billingDay) : that.billingDay != null) return false;
        if (repayMonth != null ? !repayMonth.equals(that.repayMonth) : that.repayMonth != null) return false;
        if (repayDay != null ? !repayDay.equals(that.repayDay) : that.repayDay != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (createStationId != null ? !createStationId.equals(that.createStationId) : that.createStationId != null)
            return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;
        if (errorMsg != null ? !errorMsg.equals(that.errorMsg) : that.errorMsg != null) return false;
        if (dfsSupplierId != null ? !dfsSupplierId.equals(that.dfsSupplierId) : that.dfsSupplierId != null)
            return false;
        if (customerKind != null ? !customerKind.equals(that.customerKind) : that.customerKind != null) return false;
        if (partPriceSystem != null ? !partPriceSystem.equals(that.partPriceSystem) : that.partPriceSystem != null)
            return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (locationAddress != null ? !locationAddress.equals(that.locationAddress) : that.locationAddress != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tableName != null ? tableName.hashCode() : 0;
        result = 31 * result + (objectId != null ? objectId.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (fullId != null ? fullId.hashCode() : 0);
        result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (objectNo != null ? objectNo.hashCode() : 0);
        result = 31 * result + (objectName != null ? objectName.hashCode() : 0);
        result = 31 * result + (namePinyin != null ? namePinyin.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (nation != null ? nation.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (education != null ? education.hashCode() : 0);
        result = 31 * result + (occupation != null ? occupation.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (profession != null ? profession.hashCode() : 0);
        result = 31 * result + (certificateType != null ? certificateType.hashCode() : 0);
        result = 31 * result + (certificateNo != null ? certificateNo.hashCode() : 0);
        result = 31 * result + (linkman != null ? linkman.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (icq != null ? icq.hashCode() : 0);
        result = 31 * result + (wechat != null ? wechat.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (postalcode != null ? postalcode.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (residenceAddress != null ? residenceAddress.hashCode() : 0);
        result = 31 * result + (companyAddress != null ? companyAddress.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (accountBank != null ? accountBank.hashCode() : 0);
        result = 31 * result + (accountNo != null ? accountNo.hashCode() : 0);
        result = 31 * result + (legalPerson != null ? legalPerson.hashCode() : 0);
        result = 31 * result + (businessLicense != null ? businessLicense.hashCode() : 0);
        result = 31 * result + (taxRegisterNo != null ? taxRegisterNo.hashCode() : 0);
        result = 31 * result + (objectType != null ? objectType.hashCode() : 0);
        result = 31 * result + (objectKind != null ? objectKind.hashCode() : 0);
        result = 31 * result + (objectNature != null ? objectNature.hashCode() : 0);
        result = 31 * result + (objectProperty != null ? objectProperty.hashCode() : 0);
        result = 31 * result + (moduleId != null ? moduleId.hashCode() : 0);
        result = 31 * result + (customerType != null ? customerType.hashCode() : 0);
        result = 31 * result + (income != null ? income.hashCode() : 0);
        result = 31 * result + (hobby != null ? hobby.hashCode() : 0);
        result = 31 * result + (registeredCapital != null ? registeredCapital.hashCode() : 0);
        result = 31 * result + (fixedAssets != null ? fixedAssets.hashCode() : 0);
        result = 31 * result + (mainBusiness != null ? mainBusiness.hashCode() : 0);
        result = 31 * result + (businessScope != null ? businessScope.hashCode() : 0);
        result = 31 * result + (staffNumber != null ? staffNumber.hashCode() : 0);
        result = 31 * result + (shareholderNumber != null ? shareholderNumber.hashCode() : 0);
        result = 31 * result + (topShareholder != null ? topShareholder.hashCode() : 0);
        result = 31 * result + (topShares != null ? topShares.hashCode() : 0);
        result = 31 * result + (vehicleLinkman != null ? vehicleLinkman.hashCode() : 0);
        result = 31 * result + (vehiclePhone != null ? vehiclePhone.hashCode() : 0);
        result = 31 * result + (partLinkman != null ? partLinkman.hashCode() : 0);
        result = 31 * result + (partPhone != null ? partPhone.hashCode() : 0);
        result = 31 * result + (serviceLinkman != null ? serviceLinkman.hashCode() : 0);
        result = 31 * result + (servicePhone != null ? servicePhone.hashCode() : 0);
        result = 31 * result + (insuranceLinkman != null ? insuranceLinkman.hashCode() : 0);
        result = 31 * result + (insurancePhone != null ? insurancePhone.hashCode() : 0);
        result = 31 * result + (memberTypeId != null ? memberTypeId.hashCode() : 0);
        result = 31 * result + (memberType != null ? memberType.hashCode() : 0);
        result = 31 * result + (memberNo != null ? memberNo.hashCode() : 0);
        result = 31 * result + (memberBeginTime != null ? memberBeginTime.hashCode() : 0);
        result = 31 * result + (memberEndTime != null ? memberEndTime.hashCode() : 0);
        result = 31 * result + (introducerId != null ? introducerId.hashCode() : 0);
        result = 31 * result + (introducerNo != null ? introducerNo.hashCode() : 0);
        result = 31 * result + (introducerName != null ? introducerName.hashCode() : 0);
        result = 31 * result + (introducerLinkman != null ? introducerLinkman.hashCode() : 0);
        result = 31 * result + (relatedCustomer != null ? relatedCustomer.hashCode() : 0);
        result = 31 * result + (relatedBusiness != null ? relatedBusiness.hashCode() : 0);
        result = 31 * result + (serviceSite != null ? serviceSite.hashCode() : 0);
        result = 31 * result + (insuranceChannel != null ? insuranceChannel.hashCode() : 0);
        result = 31 * result + (planBackTime != null ? planBackTime.hashCode() : 0);
        result = 31 * result + (backFlag != null ? backFlag.hashCode() : 0);
        result = 31 * result + (autoChangeCustomerFlag != null ? autoChangeCustomerFlag.hashCode() : 0);
        result = 31 * result + (deliveryPeriod != null ? deliveryPeriod.hashCode() : 0);
        result = 31 * result + (emergentDeliveryPeriod != null ? emergentDeliveryPeriod.hashCode() : 0);
        result = 31 * result + (deliveryDays != null ? deliveryDays.hashCode() : 0);
        result = 31 * result + (billingDay != null ? billingDay.hashCode() : 0);
        result = 31 * result + (repayMonth != null ? repayMonth.hashCode() : 0);
        result = 31 * result + (repayDay != null ? repayDay.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createStationId != null ? createStationId.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        result = 31 * result + (errorMsg != null ? errorMsg.hashCode() : 0);
        result = 31 * result + (dfsSupplierId != null ? dfsSupplierId.hashCode() : 0);
        result = 31 * result + (customerKind != null ? customerKind.hashCode() : 0);
        result = 31 * result + (partPriceSystem != null ? partPriceSystem.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (locationAddress != null ? locationAddress.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "part_sale_type")
    public Byte getPartSaleType() {
        return partSaleType;
    }

    public void setPartSaleType(Byte partSaleType) {
        this.partSaleType = partSaleType;
    }
}
