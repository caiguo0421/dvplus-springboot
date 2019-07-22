package cn.sf_soft.vehicle.customer.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 意向客户
 */
@Entity
@Table(name = "interested_customers", schema = "dbo")
public class InterestedCustomers implements java.io.Serializable {

    private String objectId;
    private String objectNo;
    private String objectName;
    private String namePinyin;
    private String shortName;
    private String stationId;
    private Short status;
    private String sex;
    private String nation;
    private Timestamp birthday;
    private Boolean isLunarBirthday;
    private Integer objectType;
    private Integer objectKind;
    private Short objectNature;
    private String objectProperty;
    private Short customerType;
    private String customerKind;
    private Boolean isCompetitive;
    private String profession;
    private String certificateType;
    private String certificateNo;
    private String linkman;
    private String phone;
    private String mobile;
    private String fax;
    private String icq;
    private String wechat;
    private String email;
    private String postalcode;
    private String address;
    private String area;
    private String city;
    private String province;
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
    private String introducerId;
    private String introducerNo;
    private String introducerName;
    private String remark;
    private String createStationId;
    private String creator;
    private Timestamp createTime;
    private String modifier;
    private Timestamp modifyTime;
    private String relatedObjectId;
    private String repeatCreator;
    private Timestamp repeatCreateTime;
    private String repeatCustomerName;
    private String repeatMobile;
    private Double longitude;
    private Double latitude;
    private String locationAddress;
    /**
     * 标识当前数据由哪种平台增加的，null/0：PC端；1：移动端
     */
    private Short osType;
    /**
     * 图片
     */
    private String pics;
    private String exportId;

    /**
     * 客户来源
     */
    private String customerSource;

    /**
     * 计划跟进日期
     */
//    private Timestamp planBackTime;

    /**
     * 客户群id
     */
    private String customerGroupId;

    /**
     * 是否领袖
     */
    private Boolean isLeader;

    /**
     * 维系人
     */
    private String maintainerName;

    /**
     * 维系人ID
     */
    private String maintainerId;

    //检查人（名称+编号）
    private String checker;
    //检查时间
    private Timestamp checkTime;
    //检查结果
    private String checkResult;
    //检查详情
    private String checkContent;

    //来访方式
    private String visitWay;

    //计划跟进日期 -20190213
    private Timestamp planBackTime;

    //放弃维系日期 -20190213
    private Timestamp abandonTime;

    //放弃维系的原因
    private String abandonReason;

    //客户设置无效的原因
    private String invalidReason;

    /**
     * 客户端类型,记录新增的操作的客户端类型（PC H5）
     */
    private String clientType;

    //建档部门ID
    private String createUnitId;

    // Constructors

    public InterestedCustomers() {
    }


    @Id
    @Column(name = "object_id", unique = true, nullable = false, length = 40)
    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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

    @Column(name = "station_id", nullable = false)
    public String getStationId() {
        return this.stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Column(name = "status", nullable = false)
    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

    @Column(name = "customer_type")
    public Short getCustomerType() {
        return this.customerType;
    }

    public void setCustomerType(Short customerType) {
        this.customerType = customerType;
    }

    @Column(name = "customer_kind", length = 100)
    public String getCustomerKind() {
        return this.customerKind;
    }

    public void setCustomerKind(String customerKind) {
        this.customerKind = customerKind;
    }

    @Column(name = "is_competitive")
    public Boolean getIsCompetitive() {
        return this.isCompetitive;
    }

    public void setIsCompetitive(Boolean isCompetitive) {
        this.isCompetitive = isCompetitive;
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

    @Column(name = "related_object_id", length = 40)
    public String getRelatedObjectId() {
        return this.relatedObjectId;
    }

    public void setRelatedObjectId(String relatedObjectId) {
        this.relatedObjectId = relatedObjectId;
    }

    @Column(name = "repeat_creator", length = 20)
    public String getRepeatCreator() {
        return this.repeatCreator;
    }

    public void setRepeatCreator(String repeatCreator) {
        this.repeatCreator = repeatCreator;
    }

    @Column(name = "repeat_create_time", length = 23)
    public Timestamp getRepeatCreateTime() {
        return this.repeatCreateTime;
    }

    public void setRepeatCreateTime(Timestamp repeatCreateTime) {
        this.repeatCreateTime = repeatCreateTime;
    }

    @Column(name = "repeat_customer_name", length = 40)
    public String getRepeatCustomerName() {
        return this.repeatCustomerName;
    }

    public void setRepeatCustomerName(String repeatCustomerName) {
        this.repeatCustomerName = repeatCustomerName;
    }

    @Column(name = "repeat_mobile", length = 20)
    public String getRepeatMobile() {
        return this.repeatMobile;
    }

    public void setRepeatMobile(String repeatMobile) {
        this.repeatMobile = repeatMobile;
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

    @Column(name = "os_type")
    public Short getOsType() {
        return this.osType;
    }

    public void setOsType(Short osType) {
        this.osType = osType;
    }

    @Column(name = "pics", length = 1073741823)
    public String getPics() {
        return this.pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    @Column(name = "export_id", length = 40)
    public String getExportId() {
        return this.exportId;
    }

    public void setExportId(String exportId) {
        this.exportId = exportId;
    }

    @Column(name = "customer_source", length = 100)
    public String getCustomerSource() {
        return this.customerSource;
    }

    public void setCustomerSource(String customerSource) {
        this.customerSource = customerSource;
    }

//    @Column(name = "plan_back_time", length = 23)
//    public Timestamp getPlanBackTime() {
//        return this.planBackTime;
//    }
//
//    public void setPlanBackTime(Timestamp planBackTime) {
//        this.planBackTime = planBackTime;
//    }

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

    @Column(name = "checker")
    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    @Column(name = "check_time")
    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }

    @Column(name = "check_result")
    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    @Column(name = "check_content")
    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    @Column(name = "visit_way")
    public String getVisitWay() {
        return visitWay;
    }

    public void setVisitWay(String visitWay) {
        this.visitWay = visitWay;
    }

    @Column(name = "plan_back_time")
    public Timestamp getPlanBackTime() {
        return planBackTime;
    }

    public void setPlanBackTime(Timestamp planBackTime) {
        this.planBackTime = planBackTime;
    }

    @Column(name = "abandon_time")
    public Timestamp getAbandonTime() {
        return abandonTime;
    }

    public void setAbandonTime(Timestamp abandonTime) {
        this.abandonTime = abandonTime;
    }

    @Column(name="abandon_reason")
    public String getAbandonReason() {
        return abandonReason;
    }

    public void setAbandonReason(String abandonReason) {
        this.abandonReason = abandonReason;
    }

    @Column(name="invalid_reason")
    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    @Column(name="client_type")
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