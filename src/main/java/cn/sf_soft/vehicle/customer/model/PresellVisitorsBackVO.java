package cn.sf_soft.vehicle.customer.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * PresellVisitorsBack entity. @author MyEclipse Persistence Tools
 */
@Entity
public class PresellVisitorsBackVO implements java.io.Serializable {

	// Fields

	/**
	 *
	 */
	private static final long serialVersionUID = -3441403404246385230L;
	private String backId;
	private String visitorNo;
	private String backPurpose;
	private String backWay;
	private Timestamp planBackTime;
	private Timestamp realBackTime;
	private String backSchedule;
	private String backContent;
	private String backVisitorLevel;
	private String backer;
	private String checker;
	private Timestamp checkTime;
	private String checkContent;
	private String pics;
	private String address;
	private Float longitude;
	private Float latitude;

	private String backIntentLevel;

//	======添加的辅助字段=======
	private Short visitResult;//跟踪结果
	private String reason;//成败原因
	private String selfOpinion;//总结

	private String beforeBackVisitorLevel;
	private String beforeBackIntentLevel;

	private String remark;
	// Constructors

	private String visitorName;
	private String visitorMobile;
	private String visitorAddress;
	private String visitorLocationAddress;

	/** default constructor */
	public PresellVisitorsBackVO() {
	}

	/** minimal constructor */
	public PresellVisitorsBackVO(String backId) {
		this.backId = backId;
	}

	/** full constructor */
	public PresellVisitorsBackVO(String backId, String visitorNo,
                                 String backPurpose, String backWay, Timestamp planBackTime,
                                 Timestamp realBackTime, String backSchedule, String backContent,
                                 String backVisitorLevel, String backer, String checker,
                                 Timestamp checkTime, String checkContent, String beforeBackIntentLevel,
								 String beforeBackVisitorLevel, String remark, String visitorName,
								 String visitorMobile, String visitorAddress, String visitorLocationAddress, Float latitude, Float longitude, String address) {
		this.backId = backId;
		this.visitorNo = visitorNo;
		this.backPurpose = backPurpose;
		this.backWay = backWay;
		this.planBackTime = planBackTime;
		this.realBackTime = realBackTime;
		this.backSchedule = backSchedule;
		this.backContent = backContent;
		this.backVisitorLevel = backVisitorLevel;
		this.backer = backer;
		this.checker = checker;
		this.checkTime = checkTime;
		this.checkContent = checkContent;
		this.beforeBackIntentLevel = beforeBackIntentLevel;
		this.beforeBackVisitorLevel = beforeBackVisitorLevel;
		this.remark = remark;
		this.visitorName = visitorName;
		this.visitorMobile = visitorMobile;
		this.visitorAddress = visitorAddress;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.visitorLocationAddress = visitorLocationAddress;
	}

	// Property accessors
	@Id
	@Column(name = "back_id", unique = true, nullable = false, length = 40)
	public String getBackId() {
		return this.backId;
	}

	public void setBackId(String backId) {
		this.backId = backId;
	}

	@Column(name = "visitor_no", length = 40)
	public String getVisitorNo() {
		return this.visitorNo;
	}

	public void setVisitorNo(String visitorNo) {
		this.visitorNo = visitorNo;
	}

	@Column(name = "back_purpose", length = 20)
	public String getBackPurpose() {
		return this.backPurpose;
	}

	public void setBackPurpose(String backPurpose) {
		this.backPurpose = backPurpose;
	}

	@Column(name = "back_way", length = 20)
	public String getBackWay() {
		return this.backWay;
	}

	public void setBackWay(String backWay) {
		this.backWay = backWay;
	}

	@Column(name = "plan_back_time", length = 23)
	public Timestamp getPlanBackTime() {
		return this.planBackTime;
	}

	public void setPlanBackTime(Timestamp planBackTime) {
		this.planBackTime = planBackTime;
	}

	@Column(name = "real_back_time", length = 23)
	public Timestamp getRealBackTime() {
		return this.realBackTime;
	}

	public void setRealBackTime(Timestamp realBackTime) {
		this.realBackTime = realBackTime;
	}

	@Column(name = "back_schedule", length = 20)
	public String getBackSchedule() {
		return this.backSchedule;
	}

	public void setBackSchedule(String backSchedule) {
		this.backSchedule = backSchedule;
	}

	@Column(name = "back_content", length = 600)
	public String getBackContent() {
		return this.backContent;
	}

	public void setBackContent(String backContent) {
		this.backContent = backContent;
	}

	@Column(name = "back_visitor_level", length = 40)
	public String getBackVisitorLevel() {
		return this.backVisitorLevel;
	}

	public void setBackVisitorLevel(String backVisitorLevel) {
		this.backVisitorLevel = backVisitorLevel;
	}

	@Column(name = "backer", length = 20)
	public String getBacker() {
		return this.backer;
	}

	public void setBacker(String backer) {
		this.backer = backer;
	}

	@Column(name = "checker", length = 20)
	public String getChecker() {
		return this.checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	@Column(name = "check_time", length = 23)
	public Timestamp getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	@Column(name = "check_content", length = 600)
	public String getCheckContent() {
		return this.checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}

	
//	==============添加的辅助字段==========
	@Transient
	public Short getVisitResult() {
		return visitResult;
	}

	public void setVisitResult(Short visitResult) {
		this.visitResult = visitResult;
	}

	@Transient
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Transient
	public String getSelfOpinion() {
		return selfOpinion;
	}

	public void setSelfOpinion(String selfOpinion) {
		this.selfOpinion = selfOpinion;
	}

	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	@Column(name = "back_intent_level", length = 40)
	public String getBackIntentLevel() {
		return backIntentLevel;
	}

	public void setBackIntentLevel(String backIntentLevel) {
		this.backIntentLevel = backIntentLevel;
	}

	@Column(name = "before_back_visitor_level", length = 40)
	public String getBeforeBackVisitorLevel() {
		return beforeBackVisitorLevel;
	}

	public void setBeforeBackVisitorLevel(String beforeBackVisitorLevel) {
		this.beforeBackVisitorLevel = beforeBackVisitorLevel;
	}

	@Column(name = "before_back_intent_level", length = 40)
	public String getBeforeBackIntentLevel() {
		return beforeBackIntentLevel;
	}

	public void setBeforeBackIntentLevel(String beforeBackIntentLevel) {
		this.beforeBackIntentLevel = beforeBackIntentLevel;
	}

	@Column(name = "remark", length = 600)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "visitor_name", length = 60)
	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	@Column(name = "visitor_mobile", length = 60)
	public String getVisitorMobile() {
		return visitorMobile;
	}

	public void setVisitorMobile(String visitorMobile) {
		this.visitorMobile = visitorMobile;
	}

	@Column(name = "visitor_address", length = 200)
	public String getVisitorAddress() {
		return visitorAddress;
	}

	public void setVisitorAddress(String visitorAddress) {
		this.visitorAddress = visitorAddress;
	}

	@Column(name = "longitude")
	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude")
	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}


	@Column(name = "address", length = 200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	@Column(name = "visitor_location_address", length = 200)
	public String getVisitorLocationAddress() {
		return visitorLocationAddress;
	}

	public void setVisitorLocationAddress(String visitorLocationAddress) {
		this.visitorLocationAddress = visitorLocationAddress;
	}
}
