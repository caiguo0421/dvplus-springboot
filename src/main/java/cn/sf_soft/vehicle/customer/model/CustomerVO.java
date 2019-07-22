package cn.sf_soft.vehicle.customer.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @Title: 客户信息
 * @date 2013-12-11 下午02:20:18 
 * @author cw
 * @Modify liujin 2014-11-15
 */
@Entity
public class CustomerVO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String stationName;
	private String customerTypeMeaning;//客户类型
	private String objectNatureMeaning;//客户性质
	private String selId;
	private String lastSeller;//销售员
	private String lastVisitResult;//回访结果
	private String lastVisitResultMeaning;
	private Boolean isBought;//已购车
	private Boolean isConsumption;//是否有过消费情况，如有，不允许修改客户名称

	private String introducerMobile;//介绍人手机
	private String introducerAddress;//介绍人地址
	
	private String firstTalkComment;
	private String lastRealBackTime;

	
	private String retainVehicleId;//保有车辆Id
	private String retainVehicleBrand;//保有车辆品牌
	private String retainVehicleStrain;//保有车辆品系
	

	@Column(name="station_name", insertable=false, updatable=false)
	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	@Column(name="customer_type_meaning", insertable=false, updatable=false)
	public String getCustomerTypeMeaning() {
		return customerTypeMeaning;
	}

	public void setCustomerTypeMeaning(String customerTypeMeaning) {
		this.customerTypeMeaning = customerTypeMeaning;
	}

	@Column(name="object_nature_meaning", insertable=false, updatable=false)
	public String getObjectNatureMeaning() {
		return objectNatureMeaning;
	}

	public void setObjectNatureMeaning(String objectNatureMeaning) {
		this.objectNatureMeaning = objectNatureMeaning;
	}

	@Column(name="sel_id", insertable=false, updatable=false)
	public String getSelId() {
		return selId;
	}

	public void setSelId(String selId) {
		this.selId = selId;
	}

	@Column(name="last_seller", insertable=false, updatable=false)
	public String getLastSeller() {
		return lastSeller;
	}

	public void setLastSeller(String lastSeller) {
		this.lastSeller = lastSeller;
	}

	@Column(name="last_visit_result", insertable=false, updatable=false)
	public String getLastVisitResult() {
		return lastVisitResult;
	}

	public void setLastVisitResult(String lastVisitResult) {
		this.lastVisitResult = lastVisitResult;
	}

	@Column(name="last_visit_result_meaning", insertable=false, updatable=false)
	public String getLastVisitResultMeaning() {
		return lastVisitResultMeaning;
	}

	public void setLastVisitResultMeaning(String lastVisitResultMeaning) {
		this.lastVisitResultMeaning = lastVisitResultMeaning;
	}

	@Column(name="is_bought", insertable=false, updatable=false)
	public Boolean getIsBought() {
		return isBought;
	}

	public void setIsBought(Boolean isBought) {
		this.isBought = isBought;
	}

	@Column(name="is_consumption", insertable=false, updatable=false)
	public Boolean getIsConsumption() {
		return isConsumption;
	}

	public void setIsConsumption(Boolean isConsumption) {
		this.isConsumption = isConsumption;
	}

	@Column(name="introducer_mobile", insertable=false, updatable=false)
	public String getIntroducerMobile() {
		return introducerMobile;
	}

	public void setIntroducerMobile(String introducerMobile) {
		this.introducerMobile = introducerMobile;
	}

	@Column(name="introducer_address", insertable=false, updatable=false)
	public String getIntroducerAddress() {
		return introducerAddress;
	}

	public void setIntroducerAddress(String introducerAddress) {
		this.introducerAddress = introducerAddress;
	}

	@Column(name="first_talk_comment", insertable=false, updatable=false)
	public String getFirstTalkComment() {
		return firstTalkComment;
	}

	public void setFirstTalkComment(String firstTalkComment) {
		this.firstTalkComment = firstTalkComment;
	}

	@Column(name="last_real_back_time", insertable=false, updatable=false)
	public String getLastRealBackTime() {
		return lastRealBackTime;
	}

	public void setLastRealBackTime(String lastRealBackTime) {
		this.lastRealBackTime = lastRealBackTime;
	}

	
	@Column(name="self_id", insertable=false, updatable=false)
	public String getRetainVehicleId() {
		return retainVehicleId;
	}

	public void setRetainVehicleId(String retainVehicleId) {
		this.retainVehicleId = retainVehicleId;
	}

	@Column(name="vehicle_brand", insertable=false, updatable=false)
	public String getRetainVehicleBrand() {
		return retainVehicleBrand;
	}

	public void setRetainVehicleBrand(String retainVehicleBrand) {
		this.retainVehicleBrand = retainVehicleBrand;
	}

	@Column(name="vehicle_strain", insertable=false, updatable=false)
	public String getRetainVehicleStrain() {
		return retainVehicleStrain;
	}

	public void setRetainVehicleStrain(String retainVehicleStrain) {
		this.retainVehicleStrain = retainVehicleStrain;
	}

	private String objectId;
	private String fullId;
	private String stationId;
	private Boolean isParent;
	private Short status;
	private String objectNo;
	private String objectName;
	private String shortName;
	private String namePinyin;
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
	
	private Integer objectType;
	private Integer objectKind;
	private Short objectNature;
	private String objectProperty;
	private String moduleId;
	private Short customerType;
	private Boolean isCompetitive;
	private String income;
	private String hobby;
	
	private String introducerId;
	private String introducerNo;
	private String introducerName;
	private Timestamp planBackTime;
	private Boolean backFlag;
	private Boolean autoChangeCustomerFlag;
	
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String createStationId;




	// Property accessors
	@Id
	@Column(name = "object_id", unique = true, nullable = false, length = 40)
	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
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

	@Column(name = "object_no", length = 40)
	public String getObjectNo() {
		return this.objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	@Column(name = "object_name", nullable = false, length = 40)
	public String getObjectName() {
		return this.objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Column(name = "short_name", length = 20)
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

	@Column(name = "profession", length = 10)
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

	@Column(name = "mobile", length = 20)
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

	@Column(name = "icq", length = 20)
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

	@Column(name = "postalcode", length = 10)
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

	@Column(name = "create_station_id", length = 40)
	public String getCreateStationId() {
		return this.createStationId;
	}

	public void setCreateStationId(String createStationId) {
		this.createStationId = createStationId;
	}
	
	@Column(name = "name_pinyin", nullable = false, length = 40)
	public String getNamePinyin() {
		return this.namePinyin;
	}

	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}
}
