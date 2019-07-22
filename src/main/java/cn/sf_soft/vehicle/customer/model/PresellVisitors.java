package cn.sf_soft.vehicle.customer.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 意向线索
 */
@Entity
@Table(name = "presell_visitors", schema = "dbo")
public class PresellVisitors implements java.io.Serializable {

    // Fields
    private String visitorNo;
    private String stationId;
    private Timestamp visitTime;
    private String visitorCount;
    private Short visitAddr;
    private String visitWay;
    private String visitorLevel;
    private String visitorId;
    private String visitorName;
    private String visitorSex;
    private String visitorAge;
    private String visitorEducation;
    private String visitorOccupation;
    private String visitorArea;
    private String visitorIncome;
    private String visitorMobile;
    private String visitorPhone;
    private String visitorAddress;
    private String visitorPostalcode;
    private String visitorEmail;
    private String knowWay;
    private String introducer;
    private String workCompany;
    private String backWay;
    private String backPlace;
    private String backTime;
    private String vehicleVno;
    private String vehicleName;
    private String vehicleColor;
    private Double vehiclePrice;
    private String visitorQq;
    private Timestamp planPurchaseTime;
    private String attentionEmphases;
    private String purchaseUse;
    private Boolean tryDriveFlag;
    private String seller;
    private String comment;
    private String firstTalkComment;
    private Timestamp planBackTime;
    private String finalResult;
    private String reason;
    private String selfOpinion;
    private String otherOpinion;
    private String unitNo;
    private String unitName;
    private String creator;
    private Timestamp createTime;
    private String modifier;
    private Timestamp modifyTime;
    private Short visitResult;
    private String profession;
    private String visitorMsn;
    private String introducerMobile;
    private String introducerAddress;
    private String emphasis;
    private String vehicleKind;
    private String distance;
    private String factLoad;
    private String tonnage;
    private String sellerId;
    private String vehicleSalesCode;
    private String introducerId;
    private String deliveryLocus;
    private Short buyType;
    private Boolean usedFlag;
    private Boolean partFlag;
    private Boolean serviceFlag;
    private Boolean insuranceFlag;
    private String remark;
    private Timestamp finishDate;
    private Short lastVisitResult;
    private Boolean lastIsCompetitive;
    private Integer purposeQuantity;
    private String vnoId;
    private String subjectMatter;
    private String transportRoutes;
    private String startPoint;
    private String waysPoint;
    private String endPoint;
    private String intentLevel;
    private String intentLevelBak;
    private String shortNameVno;
    private String vehicleStrain;
    private String exportId;
    private String horsepower;
    private String workingCondition;

    /**
     * 客户端类型,记录新增的操作的客户端类型（PC H5）
     */
    private String clientType;

    private String createUnitId;

    // Constructors

    public PresellVisitors() {

    }


    // Property accessors
    @Id
    @Column(name = "visitor_no", unique = true, nullable = false, length = 40)
    public String getVisitorNo() {
        return this.visitorNo;
    }

    public void setVisitorNo(String visitorNo) {
        this.visitorNo = visitorNo;
    }

    @Column(name = "station_id", length = 10)
    public String getStationId() {
        return this.stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Column(name = "visit_time", length = 23)
    public Timestamp getVisitTime() {
        return this.visitTime;
    }

    public void setVisitTime(Timestamp visitTime) {
        this.visitTime = visitTime;
    }

    @Column(name = "visitor_count", length = 20)
    public String getVisitorCount() {
        return this.visitorCount;
    }

    public void setVisitorCount(String visitorCount) {
        this.visitorCount = visitorCount;
    }

    @Column(name = "visit_addr")
    public Short getVisitAddr() {
        return this.visitAddr;
    }

    public void setVisitAddr(Short visitAddr) {
        this.visitAddr = visitAddr;
    }

    @Column(name = "visit_way", length = 20)
    public String getVisitWay() {
        return this.visitWay;
    }

    public void setVisitWay(String visitWay) {
        this.visitWay = visitWay;
    }

    @Column(name = "visitor_level", length = 40)
    public String getVisitorLevel() {
        return this.visitorLevel;
    }

    public void setVisitorLevel(String visitorLevel) {
        this.visitorLevel = visitorLevel;
    }

    @Column(name = "visitor_id", length = 40)
    public String getVisitorId() {
        return this.visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    @Column(name = "visitor_name", length = 40)
    public String getVisitorName() {
        return this.visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    @Column(name = "visitor_sex", length = 10)
    public String getVisitorSex() {
        return this.visitorSex;
    }

    public void setVisitorSex(String visitorSex) {
        this.visitorSex = visitorSex;
    }

    @Column(name = "visitor_age", length = 20)
    public String getVisitorAge() {
        return this.visitorAge;
    }

    public void setVisitorAge(String visitorAge) {
        this.visitorAge = visitorAge;
    }

    @Column(name = "visitor_education", length = 20)
    public String getVisitorEducation() {
        return this.visitorEducation;
    }

    public void setVisitorEducation(String visitorEducation) {
        this.visitorEducation = visitorEducation;
    }

    @Column(name = "visitor_occupation", length = 20)
    public String getVisitorOccupation() {
        return this.visitorOccupation;
    }

    public void setVisitorOccupation(String visitorOccupation) {
        this.visitorOccupation = visitorOccupation;
    }

    @Column(name = "visitor_area", length = 20)
    public String getVisitorArea() {
        return this.visitorArea;
    }

    public void setVisitorArea(String visitorArea) {
        this.visitorArea = visitorArea;
    }

    @Column(name = "visitor_income", length = 20)
    public String getVisitorIncome() {
        return this.visitorIncome;
    }

    public void setVisitorIncome(String visitorIncome) {
        this.visitorIncome = visitorIncome;
    }

    @Column(name = "visitor_mobile", length = 30)
    public String getVisitorMobile() {
        return this.visitorMobile;
    }

    public void setVisitorMobile(String visitorMobile) {
        this.visitorMobile = visitorMobile;
    }

    @Column(name = "visitor_phone", length = 30)
    public String getVisitorPhone() {
        return this.visitorPhone;
    }

    public void setVisitorPhone(String visitorPhone) {
        this.visitorPhone = visitorPhone;
    }

    @Column(name = "visitor_address", length = 60)
    public String getVisitorAddress() {
        return this.visitorAddress;
    }

    public void setVisitorAddress(String visitorAddress) {
        this.visitorAddress = visitorAddress;
    }

    @Column(name = "visitor_postalcode", length = 10)
    public String getVisitorPostalcode() {
        return this.visitorPostalcode;
    }

    public void setVisitorPostalcode(String visitorPostalcode) {
        this.visitorPostalcode = visitorPostalcode;
    }

    @Column(name = "visitor_email", length = 30)
    public String getVisitorEmail() {
        return this.visitorEmail;
    }

    public void setVisitorEmail(String visitorEmail) {
        this.visitorEmail = visitorEmail;
    }

    @Column(name = "know_way", length = 20)
    public String getKnowWay() {
        return this.knowWay;
    }

    public void setKnowWay(String knowWay) {
        this.knowWay = knowWay;
    }

    @Column(name = "introducer", length = 20)
    public String getIntroducer() {
        return this.introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    @Column(name = "work_company", length = 40)
    public String getWorkCompany() {
        return this.workCompany;
    }

    public void setWorkCompany(String workCompany) {
        this.workCompany = workCompany;
    }

    @Column(name = "back_way", length = 20)
    public String getBackWay() {
        return this.backWay;
    }

    public void setBackWay(String backWay) {
        this.backWay = backWay;
    }

    @Column(name = "back_place", length = 20)
    public String getBackPlace() {
        return this.backPlace;
    }

    public void setBackPlace(String backPlace) {
        this.backPlace = backPlace;
    }

    @Column(name = "back_time", length = 20)
    public String getBackTime() {
        return this.backTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }

    @Column(name = "vehicle_vno", length = 40)
    public String getVehicleVno() {
        return this.vehicleVno;
    }

    public void setVehicleVno(String vehicleVno) {
        this.vehicleVno = vehicleVno;
    }

    @Column(name = "vehicle_name", length = 40)
    public String getVehicleName() {
        return this.vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Column(name = "vehicle_color", length = 30)
    public String getVehicleColor() {
        return this.vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    @Column(name = "vehicle_price", precision = 18)
    public Double getVehiclePrice() {
        return this.vehiclePrice;
    }

    public void setVehiclePrice(Double vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    @Column(name = "visitor_qq", length = 15)
    public String getVisitorQq() {
        return this.visitorQq;
    }

    public void setVisitorQq(String visitorQq) {
        this.visitorQq = visitorQq;
    }

    @Column(name = "plan_purchase_time", length = 23)
    public Timestamp getPlanPurchaseTime() {
        return this.planPurchaseTime;
    }

    public void setPlanPurchaseTime(Timestamp planPurchaseTime) {
        this.planPurchaseTime = planPurchaseTime;
    }

    @Column(name = "attention_emphases", length = 60)
    public String getAttentionEmphases() {
        return this.attentionEmphases;
    }

    public void setAttentionEmphases(String attentionEmphases) {
        this.attentionEmphases = attentionEmphases;
    }

    @Column(name = "purchase_use", length = 30)
    public String getPurchaseUse() {
        return this.purchaseUse;
    }

    public void setPurchaseUse(String purchaseUse) {
        this.purchaseUse = purchaseUse;
    }

    @Column(name = "try_drive_flag")
    public Boolean getTryDriveFlag() {
        return this.tryDriveFlag;
    }

    public void setTryDriveFlag(Boolean tryDriveFlag) {
        this.tryDriveFlag = tryDriveFlag;
    }

    @Column(name = "seller", length = 20)
    public String getSeller() {
        return this.seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Column(name = "comment", length = 100)
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "first_talk_comment", length = 600)
    public String getFirstTalkComment() {
        return this.firstTalkComment;
    }

    public void setFirstTalkComment(String firstTalkComment) {
        this.firstTalkComment = firstTalkComment;
    }

    @Column(name = "plan_back_time", length = 23)
    public Timestamp getPlanBackTime() {
        return this.planBackTime;
    }

    public void setPlanBackTime(Timestamp planBackTime) {
        this.planBackTime = planBackTime;
    }

    @Column(name = "final_result", length = 20)
    public String getFinalResult() {
        return this.finalResult;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }

    @Column(name = "reason", length = 20)
    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Column(name = "self_opinion", length = 600)
    public String getSelfOpinion() {
        return this.selfOpinion;
    }

    public void setSelfOpinion(String selfOpinion) {
        this.selfOpinion = selfOpinion;
    }

    @Column(name = "other_opinion", length = 600)
    public String getOtherOpinion() {
        return this.otherOpinion;
    }

    public void setOtherOpinion(String otherOpinion) {
        this.otherOpinion = otherOpinion;
    }

    @Column(name = "unit_no", length = 40)
    public String getUnitNo() {
        return this.unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    @Column(name = "unit_name", length = 40)
    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    @Column(name = "visit_result")
    public Short getVisitResult() {
        return this.visitResult;
    }

    public void setVisitResult(Short visitResult) {
        this.visitResult = visitResult;
    }

    @Column(name = "profession", length = 20)
    public String getProfession() {
        return this.profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Column(name = "visitor_msn", length = 30)
    public String getVisitorMsn() {
        return this.visitorMsn;
    }

    public void setVisitorMsn(String visitorMsn) {
        this.visitorMsn = visitorMsn;
    }

    @Column(name = "introducer_mobile", length = 30)
    public String getIntroducerMobile() {
        return this.introducerMobile;
    }

    public void setIntroducerMobile(String introducerMobile) {
        this.introducerMobile = introducerMobile;
    }

    @Column(name = "introducer_address", length = 60)
    public String getIntroducerAddress() {
        return this.introducerAddress;
    }

    public void setIntroducerAddress(String introducerAddress) {
        this.introducerAddress = introducerAddress;
    }

    @Column(name = "emphasis", length = 100)
    public String getEmphasis() {
        return this.emphasis;
    }

    public void setEmphasis(String emphasis) {
        this.emphasis = emphasis;
    }

    @Column(name = "vehicle_kind", length = 20)
    public String getVehicleKind() {
        return this.vehicleKind;
    }

    public void setVehicleKind(String vehicleKind) {
        this.vehicleKind = vehicleKind;
    }

    @Column(name = "distance", length = 20)
    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Column(name = "fact_load", length = 20)
    public String getFactLoad() {
        return this.factLoad;
    }

    public void setFactLoad(String factLoad) {
        this.factLoad = factLoad;
    }

    @Column(name = "tonnage", length = 20)
    public String getTonnage() {
        return this.tonnage;
    }

    public void setTonnage(String tonnage) {
        this.tonnage = tonnage;
    }

    @Column(name = "seller_id", length = 40)
    public String getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    @Column(name = "vehicle_sales_code", length = 50)
    public String getVehicleSalesCode() {
        return this.vehicleSalesCode;
    }

    public void setVehicleSalesCode(String vehicleSalesCode) {
        this.vehicleSalesCode = vehicleSalesCode;
    }

    @Column(name = "introducer_id", length = 40)
    public String getIntroducerId() {
        return this.introducerId;
    }

    public void setIntroducerId(String introducerId) {
        this.introducerId = introducerId;
    }

    @Column(name = "delivery_locus", length = 20)
    public String getDeliveryLocus() {
        return this.deliveryLocus;
    }

    public void setDeliveryLocus(String deliveryLocus) {
        this.deliveryLocus = deliveryLocus;
    }

    @Column(name = "buy_type")
    public Short getBuyType() {
        return this.buyType;
    }

    public void setBuyType(Short buyType) {
        this.buyType = buyType;
    }

    @Column(name = "used_flag")
    public Boolean getUsedFlag() {
        return this.usedFlag;
    }

    public void setUsedFlag(Boolean usedFlag) {
        this.usedFlag = usedFlag;
    }

    @Column(name = "part_flag")
    public Boolean getPartFlag() {
        return this.partFlag;
    }

    public void setPartFlag(Boolean partFlag) {
        this.partFlag = partFlag;
    }

    @Column(name = "service_flag")
    public Boolean getServiceFlag() {
        return this.serviceFlag;
    }

    public void setServiceFlag(Boolean serviceFlag) {
        this.serviceFlag = serviceFlag;
    }

    @Column(name = "insurance_flag")
    public Boolean getInsuranceFlag() {
        return this.insuranceFlag;
    }

    public void setInsuranceFlag(Boolean insuranceFlag) {
        this.insuranceFlag = insuranceFlag;
    }

    @Column(name = "remark", length = 100)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "finish_date", length = 23)
    public Timestamp getFinishDate() {
        return this.finishDate;
    }

    public void setFinishDate(Timestamp finishDate) {
        this.finishDate = finishDate;
    }

    @Column(name = "last_visit_result")
    public Short getLastVisitResult() {
        return this.lastVisitResult;
    }

    public void setLastVisitResult(Short lastVisitResult) {
        this.lastVisitResult = lastVisitResult;
    }

    @Column(name = "last_is_competitive")
    public Boolean getLastIsCompetitive() {
        return this.lastIsCompetitive;
    }

    public void setLastIsCompetitive(Boolean lastIsCompetitive) {
        this.lastIsCompetitive = lastIsCompetitive;
    }

    @Column(name = "purpose_quantity")
    public Integer getPurposeQuantity() {
        return this.purposeQuantity;
    }

    public void setPurposeQuantity(Integer purposeQuantity) {
        this.purposeQuantity = purposeQuantity;
    }

    @Column(name = "vno_id", length = 40)
    public String getVnoId() {
        return this.vnoId;
    }

    public void setVnoId(String vnoId) {
        this.vnoId = vnoId;
    }

    @Column(name = "subject_matter", length = 40)
    public String getSubjectMatter() {
        return this.subjectMatter;
    }

    public void setSubjectMatter(String subjectMatter) {
        this.subjectMatter = subjectMatter;
    }

    @Column(name = "transport_routes", length = 40)
    public String getTransportRoutes() {
        return this.transportRoutes;
    }

    public void setTransportRoutes(String transportRoutes) {
        this.transportRoutes = transportRoutes;
    }

    @Column(name = "start_point", length = 10)
    public String getStartPoint() {
        return this.startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    @Column(name = "ways_point", length = 40)
    public String getWaysPoint() {
        return this.waysPoint;
    }

    public void setWaysPoint(String waysPoint) {
        this.waysPoint = waysPoint;
    }

    @Column(name = "end_point", length = 10)
    public String getEndPoint() {
        return this.endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @Column(name = "intent_level", length = 40)
    public String getIntentLevel() {
        return this.intentLevel;
    }

    public void setIntentLevel(String intentLevel) {
        this.intentLevel = intentLevel;
    }

    @Column(name = "intent_level_bak", length = 40)
    public String getIntentLevelBak() {
        return this.intentLevelBak;
    }

    public void setIntentLevelBak(String intentLevelBak) {
        this.intentLevelBak = intentLevelBak;
    }

    @Column(name = "short_name_vno", length = 40)
    public String getShortNameVno() {
        return this.shortNameVno;
    }

    public void setShortNameVno(String shortNameVno) {
        this.shortNameVno = shortNameVno;
    }

    @Column(name = "vehicle_strain", length = 40)
    public String getVehicleStrain() {
        return this.vehicleStrain;
    }

    public void setVehicleStrain(String vehicleStrain) {
        this.vehicleStrain = vehicleStrain;
    }

    @Column(name = "export_id", length = 40)
    public String getExportId() {
        return this.exportId;
    }

    public void setExportId(String exportId) {
        this.exportId = exportId;
    }

    @Column(name = "horsepower", length = 100)
    public String getHorsepower() {
        return this.horsepower;
    }

    public void setHorsepower(String horsepower) {
        this.horsepower = horsepower;
    }

    @Column(name = "working_condition", length = 100)
    public String getWorkingCondition() {
        return this.workingCondition;
    }

    public void setWorkingCondition(String workingCondition) {
        this.workingCondition = workingCondition;
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