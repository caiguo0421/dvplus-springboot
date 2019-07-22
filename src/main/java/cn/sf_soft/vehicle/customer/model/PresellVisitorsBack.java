package cn.sf_soft.vehicle.customer.model;

import java.sql.Timestamp;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * 意向线索回访
 */
@Entity
@Table(name = "presell_visitors_back", schema = "dbo")
public class PresellVisitorsBack implements java.io.Serializable {

    // Fields
    private String backId;
    private String visitorNo;
    //跟进类型
    private String backPurpose;
    //跟进目的
    private String backPurposeTmp;
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
    private String backIntentLevel;
    private String pics;
    private String beforeBackIntentLevel;
    private String beforeBackVisitorLevel;
    private String remark;
    private Double longitude;
    private Double latitude;
    private String address;

    /**
     * 标识当前数据由哪种平台增加的，null/0：PC端；1：移动端
     */
    private Short osType;
    private String exportId;
    private Boolean isContinueBack = false;
    private Timestamp nextBackTime;
    private String checkResult;
    private String objectId;
    private String imgUrls;
    private String checkerId;

    //	======添加的辅助字段=======
    private Short visitResult;//跟踪结果
    private String reason;//成败原因
    private String selfOpinion;//总结

    private String stationId;

    private String creator;

    private Timestamp createTime;

    private String modifier;

    private Timestamp modifyTime;

    // Constructors
    public PresellVisitorsBack() {
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

    @Column(name = "back_purpose")
    public String getBackPurpose() {
        return this.backPurpose;
    }

    public void setBackPurpose(String backPurpose) {
        this.backPurpose = backPurpose;
    }

    @Column(name = "back_purpose_tmp")
    public String getBackPurposeTmp() {
        return backPurposeTmp;
    }

    public void setBackPurposeTmp(String backPurposeTmp) {
        this.backPurposeTmp = backPurposeTmp;
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

    @Column(name = "back_intent_level", length = 40)
    public String getBackIntentLevel() {
        return this.backIntentLevel;
    }

    public void setBackIntentLevel(String backIntentLevel) {
        this.backIntentLevel = backIntentLevel;
    }

    @Column(name = "pics", length = 1073741823)
    public String getPics() {
        return this.pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    @Column(name = "before_back_intent_level", length = 40)
    public String getBeforeBackIntentLevel() {
        return this.beforeBackIntentLevel;
    }

    public void setBeforeBackIntentLevel(String beforeBackIntentLevel) {
        this.beforeBackIntentLevel = beforeBackIntentLevel;
    }

    @Column(name = "before_back_visitor_level", length = 40)
    public String getBeforeBackVisitorLevel() {
        return this.beforeBackVisitorLevel;
    }

    public void setBeforeBackVisitorLevel(String beforeBackVisitorLevel) {
        this.beforeBackVisitorLevel = beforeBackVisitorLevel;
    }

    @Column(name = "remark", length = 600)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Column(name = "address", length = 200)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "os_type")
    public Short getOsType() {
        return this.osType;
    }

    public void setOsType(Short osType) {
        this.osType = osType;
    }

    @Column(name = "export_id", length = 40)
    public String getExportId() {
        return this.exportId;
    }

    public void setExportId(String exportId) {
        this.exportId = exportId;
    }

    @Column(name = "is_continue_back")
    public Boolean getIsContinueBack() {
        return this.isContinueBack;
    }

    public void setIsContinueBack(Boolean isContinueBack) {
        this.isContinueBack = isContinueBack;
    }

    @Column(name = "next_back_time", length = 23)
    public Timestamp getNextBackTime() {
        return this.nextBackTime;
    }

    public void setNextBackTime(Timestamp nextBackTime) {
        this.nextBackTime = nextBackTime;
    }

    @Column(name = "check_result", length = 100)
    public String getCheckResult() {
        return this.checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    @Column(name = "object_id", length = 40)
    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Column(name = "img_urls", length = 1073741823)
    public String getImgUrls() {
        return this.imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    @Column(name = "checker_id")
    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }

    @Column(name = "station_id")
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "modifier")
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Column(name = "modify_time")
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

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
}