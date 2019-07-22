package cn.sf_soft.vehicle.customer.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by henry on 2018/6/11.
 */
@Entity
@Table(name = "vw_interested_customers", schema = "dbo")
public class VwInterestedCustomers {
    private String objectId;
    private String objectNo;
    private String objectName;
    private String namePinyin;
    private String shortName;
    private String stationId;
    private Byte status;
    private String sex;
    private String nation;
    private Timestamp birthday;
    private Boolean isLunarBirthday;
    private Integer objectType;
    private Integer objectKind;
    private Byte objectNature;
    private String objectProperty;
    private Byte customerType;
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

    @Basic
    @Id
    @Column(name = "object_id")
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Basic
    @Column(name = "object_no")
    public String getObjectNo() {
        return objectNo;
    }

    public void setObjectNo(String objectNo) {
        this.objectNo = objectNo;
    }

    @Basic
    @Column(name = "object_name")
    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Basic
    @Column(name = "name_pinyin")
    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    @Basic
    @Column(name = "short_name")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "station_id")
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "nation")
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Basic
    @Column(name = "birthday")
    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "is_lunar_birthday")
    public Boolean getIsLunarBirthday() {
        return isLunarBirthday;
    }

    public void setIsLunarBirthday(Boolean lunarBirthday) {
        isLunarBirthday = lunarBirthday;
    }

    @Basic
    @Column(name = "object_type")
    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    @Basic
    @Column(name = "object_kind")
    public Integer getObjectKind() {
        return objectKind;
    }

    public void setObjectKind(Integer objectKind) {
        this.objectKind = objectKind;
    }

    @Basic
    @Column(name = "object_nature")
    public Byte getObjectNature() {
        return objectNature;
    }

    public void setObjectNature(Byte objectNature) {
        this.objectNature = objectNature;
    }

    @Basic
    @Column(name = "object_property")
    public String getObjectProperty() {
        return objectProperty;
    }

    public void setObjectProperty(String objectProperty) {
        this.objectProperty = objectProperty;
    }

    @Basic
    @Column(name = "customer_type")
    public Byte getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Byte customerType) {
        this.customerType = customerType;
    }

    @Basic
    @Column(name = "customer_kind")
    public String getCustomerKind() {
        return customerKind;
    }

    public void setCustomerKind(String customerKind) {
        this.customerKind = customerKind;
    }

    @Basic
    @Column(name = "is_competitive")
    public Boolean getIsCompetitive() {
        return isCompetitive;
    }

    public void setIsCompetitive(Boolean competitive) {
        isCompetitive = competitive;
    }

    @Basic
    @Column(name = "profession")
    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Basic
    @Column(name = "certificate_type")
    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    @Basic
    @Column(name = "certificate_no")
    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    @Basic
    @Column(name = "linkman")
    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "icq")
    public String getIcq() {
        return icq;
    }

    public void setIcq(String icq) {
        this.icq = icq;
    }

    @Basic
    @Column(name = "wechat")
    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "postalcode")
    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "area")
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "registered_capital")
    public BigDecimal getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    @Basic
    @Column(name = "fixed_assets")
    public BigDecimal getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(BigDecimal fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    @Basic
    @Column(name = "main_business")
    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    @Basic
    @Column(name = "business_scope")
    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    @Basic
    @Column(name = "staff_number")
    public Integer getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(Integer staffNumber) {
        this.staffNumber = staffNumber;
    }

    @Basic
    @Column(name = "shareholder_number")
    public Integer getShareholderNumber() {
        return shareholderNumber;
    }

    public void setShareholderNumber(Integer shareholderNumber) {
        this.shareholderNumber = shareholderNumber;
    }

    @Basic
    @Column(name = "top_shareholder")
    public String getTopShareholder() {
        return topShareholder;
    }

    public void setTopShareholder(String topShareholder) {
        this.topShareholder = topShareholder;
    }

    @Basic
    @Column(name = "top_shares")
    public BigDecimal getTopShares() {
        return topShares;
    }

    public void setTopShares(BigDecimal topShares) {
        this.topShares = topShares;
    }

    @Basic
    @Column(name = "vehicle_linkman")
    public String getVehicleLinkman() {
        return vehicleLinkman;
    }

    public void setVehicleLinkman(String vehicleLinkman) {
        this.vehicleLinkman = vehicleLinkman;
    }

    @Basic
    @Column(name = "vehicle_phone")
    public String getVehiclePhone() {
        return vehiclePhone;
    }

    public void setVehiclePhone(String vehiclePhone) {
        this.vehiclePhone = vehiclePhone;
    }

    @Basic
    @Column(name = "part_linkman")
    public String getPartLinkman() {
        return partLinkman;
    }

    public void setPartLinkman(String partLinkman) {
        this.partLinkman = partLinkman;
    }

    @Basic
    @Column(name = "part_phone")
    public String getPartPhone() {
        return partPhone;
    }

    public void setPartPhone(String partPhone) {
        this.partPhone = partPhone;
    }

    @Basic
    @Column(name = "service_linkman")
    public String getServiceLinkman() {
        return serviceLinkman;
    }

    public void setServiceLinkman(String serviceLinkman) {
        this.serviceLinkman = serviceLinkman;
    }

    @Basic
    @Column(name = "service_phone")
    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    @Basic
    @Column(name = "insurance_linkman")
    public String getInsuranceLinkman() {
        return insuranceLinkman;
    }

    public void setInsuranceLinkman(String insuranceLinkman) {
        this.insuranceLinkman = insuranceLinkman;
    }

    @Basic
    @Column(name = "insurance_phone")
    public String getInsurancePhone() {
        return insurancePhone;
    }

    public void setInsurancePhone(String insurancePhone) {
        this.insurancePhone = insurancePhone;
    }

    @Basic
    @Column(name = "introducer_id")
    public String getIntroducerId() {
        return introducerId;
    }

    public void setIntroducerId(String introducerId) {
        this.introducerId = introducerId;
    }

    @Basic
    @Column(name = "introducer_no")
    public String getIntroducerNo() {
        return introducerNo;
    }

    public void setIntroducerNo(String introducerNo) {
        this.introducerNo = introducerNo;
    }

    @Basic
    @Column(name = "introducer_name")
    public String getIntroducerName() {
        return introducerName;
    }

    public void setIntroducerName(String introducerName) {
        this.introducerName = introducerName;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "create_station_id")
    public String getCreateStationId() {
        return createStationId;
    }

    public void setCreateStationId(String createStationId) {
        this.createStationId = createStationId;
    }

    @Basic
    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modifier")
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Basic
    @Column(name = "modify_time")
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "related_object_id")
    public String getRelatedObjectId() {
        return relatedObjectId;
    }

    public void setRelatedObjectId(String relatedObjectId) {
        this.relatedObjectId = relatedObjectId;
    }

    @Basic
    @Column(name = "repeat_creator")
    public String getRepeatCreator() {
        return repeatCreator;
    }

    public void setRepeatCreator(String repeatCreator) {
        this.repeatCreator = repeatCreator;
    }

    @Basic
    @Column(name = "repeat_create_time")
    public Timestamp getRepeatCreateTime() {
        return repeatCreateTime;
    }

    public void setRepeatCreateTime(Timestamp repeatCreateTime) {
        this.repeatCreateTime = repeatCreateTime;
    }

    @Basic
    @Column(name = "repeat_customer_name")
    public String getRepeatCustomerName() {
        return repeatCustomerName;
    }

    public void setRepeatCustomerName(String repeatCustomerName) {
        this.repeatCustomerName = repeatCustomerName;
    }

    @Basic
    @Column(name = "repeat_mobile")
    public String getRepeatMobile() {
        return repeatMobile;
    }

    public void setRepeatMobile(String repeatMobile) {
        this.repeatMobile = repeatMobile;
    }

    @Basic
    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "location_address")
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

        VwInterestedCustomers that = (VwInterestedCustomers) o;

        if (objectId != null ? !objectId.equals(that.objectId) : that.objectId != null) return false;
        if (objectNo != null ? !objectNo.equals(that.objectNo) : that.objectNo != null) return false;
        if (objectName != null ? !objectName.equals(that.objectName) : that.objectName != null) return false;
        if (namePinyin != null ? !namePinyin.equals(that.namePinyin) : that.namePinyin != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (stationId != null ? !stationId.equals(that.stationId) : that.stationId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (nation != null ? !nation.equals(that.nation) : that.nation != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (isLunarBirthday != null ? !isLunarBirthday.equals(that.isLunarBirthday) : that.isLunarBirthday != null)
            return false;
        if (objectType != null ? !objectType.equals(that.objectType) : that.objectType != null) return false;
        if (objectKind != null ? !objectKind.equals(that.objectKind) : that.objectKind != null) return false;
        if (objectNature != null ? !objectNature.equals(that.objectNature) : that.objectNature != null) return false;
        if (objectProperty != null ? !objectProperty.equals(that.objectProperty) : that.objectProperty != null)
            return false;
        if (customerType != null ? !customerType.equals(that.customerType) : that.customerType != null) return false;
        if (customerKind != null ? !customerKind.equals(that.customerKind) : that.customerKind != null) return false;
        if (isCompetitive != null ? !isCompetitive.equals(that.isCompetitive) : that.isCompetitive != null)
            return false;
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
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (postalcode != null ? !postalcode.equals(that.postalcode) : that.postalcode != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
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
        if (introducerId != null ? !introducerId.equals(that.introducerId) : that.introducerId != null) return false;
        if (introducerNo != null ? !introducerNo.equals(that.introducerNo) : that.introducerNo != null) return false;
        if (introducerName != null ? !introducerName.equals(that.introducerName) : that.introducerName != null)
            return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (createStationId != null ? !createStationId.equals(that.createStationId) : that.createStationId != null)
            return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (relatedObjectId != null ? !relatedObjectId.equals(that.relatedObjectId) : that.relatedObjectId != null)
            return false;
        if (repeatCreator != null ? !repeatCreator.equals(that.repeatCreator) : that.repeatCreator != null)
            return false;
        if (repeatCreateTime != null ? !repeatCreateTime.equals(that.repeatCreateTime) : that.repeatCreateTime != null)
            return false;
        if (repeatCustomerName != null ? !repeatCustomerName.equals(that.repeatCustomerName) : that.repeatCustomerName != null)
            return false;
        if (repeatMobile != null ? !repeatMobile.equals(that.repeatMobile) : that.repeatMobile != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (locationAddress != null ? !locationAddress.equals(that.locationAddress) : that.locationAddress != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = objectId != null ? objectId.hashCode() : 0;
        result = 31 * result + (objectNo != null ? objectNo.hashCode() : 0);
        result = 31 * result + (objectName != null ? objectName.hashCode() : 0);
        result = 31 * result + (namePinyin != null ? namePinyin.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (nation != null ? nation.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (isLunarBirthday != null ? isLunarBirthday.hashCode() : 0);
        result = 31 * result + (objectType != null ? objectType.hashCode() : 0);
        result = 31 * result + (objectKind != null ? objectKind.hashCode() : 0);
        result = 31 * result + (objectNature != null ? objectNature.hashCode() : 0);
        result = 31 * result + (objectProperty != null ? objectProperty.hashCode() : 0);
        result = 31 * result + (customerType != null ? customerType.hashCode() : 0);
        result = 31 * result + (customerKind != null ? customerKind.hashCode() : 0);
        result = 31 * result + (isCompetitive != null ? isCompetitive.hashCode() : 0);
        result = 31 * result + (profession != null ? profession.hashCode() : 0);
        result = 31 * result + (certificateType != null ? certificateType.hashCode() : 0);
        result = 31 * result + (certificateNo != null ? certificateNo.hashCode() : 0);
        result = 31 * result + (linkman != null ? linkman.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (icq != null ? icq.hashCode() : 0);
        result = 31 * result + (wechat != null ? wechat.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (postalcode != null ? postalcode.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
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
        result = 31 * result + (introducerId != null ? introducerId.hashCode() : 0);
        result = 31 * result + (introducerNo != null ? introducerNo.hashCode() : 0);
        result = 31 * result + (introducerName != null ? introducerName.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createStationId != null ? createStationId.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (relatedObjectId != null ? relatedObjectId.hashCode() : 0);
        result = 31 * result + (repeatCreator != null ? repeatCreator.hashCode() : 0);
        result = 31 * result + (repeatCreateTime != null ? repeatCreateTime.hashCode() : 0);
        result = 31 * result + (repeatCustomerName != null ? repeatCustomerName.hashCode() : 0);
        result = 31 * result + (repeatMobile != null ? repeatMobile.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (locationAddress != null ? locationAddress.hashCode() : 0);
        return result;
    }
}
