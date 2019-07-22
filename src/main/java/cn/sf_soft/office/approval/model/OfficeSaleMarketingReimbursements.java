package cn.sf_soft.office.approval.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

/**
 * @Auther: caigx
 * @Date: 2018/11/21 12:31
 * @Description:营销活动报销
 */
@Entity
@Table(name = "office_sale_marketing_reimbursements", schema = "dbo")
public class OfficeSaleMarketingReimbursements extends ApproveDocuments<OfficeSaleMarketingReimbursementsDetails> {

    //    private String documentNo;
//    private String stationId;
//    private Short status;
    private BigDecimal reimburseAmount;
    private Timestamp reimburseTime;
//    private String userId;
//    private String userNo;
//    private String userName;
//    private String departmentId;
//    private String departmentNo;
//    private String departmentName;
//    private String submitStationId;
//    private String submitStationName;

    private String remark;
    private String creator;
    private Timestamp createTime;
    private String modifier;
//    private Timestamp modifyTime;
//    private Timestamp submitTime;
//    private String approverId;
//    private String approverNo;
//    private String approverName;
//    private Timestamp approveTime;
    private String errorCode;
    private String errorMsg;

    private String fileUrls;
    private BigDecimal estimateAmount;
    private BigDecimal budgetDifference;

    @Id
    @Column(name = "document_no")
    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
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
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Basic
    @Column(name = "reimburse_amount")
    public BigDecimal getReimburseAmount() {
        return reimburseAmount;
    }

    public void setReimburseAmount(BigDecimal reimburseAmount) {
        this.reimburseAmount = reimburseAmount;
    }

    @Basic
    @Column(name = "reimburse_time")
    public Timestamp getReimburseTime() {
        return reimburseTime;
    }

    public void setReimburseTime(Timestamp reimburseTime) {
        this.reimburseTime = reimburseTime;
    }

    @Basic
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_no")
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "department_id")
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "department_no")
    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    @Basic
    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "submit_station_id")
    public String getSubmitStationId() {
        return submitStationId;
    }

    public void setSubmitStationId(String submitStationId) {
        this.submitStationId = submitStationId;
    }

    @Basic
    @Column(name = "submit_station_name")
    public String getSubmitStationName() {
        return submitStationName;
    }

    public void setSubmitStationName(String submitStationName) {
        this.submitStationName = submitStationName;
    }

    @Basic
    @Column(name = "file_urls")
    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
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
    @Column(name = "submit_time")
    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    @Basic
    @Column(name = "approver_id")
    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    @Basic
    @Column(name = "approver_no")
    public String getApproverNo() {
        return approverNo;
    }

    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    @Basic
    @Column(name = "approver_name")
    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    @Basic
    @Column(name = "approve_time")
    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    @Basic
    @Column(name = "error_code")
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Basic
    @Column(name = "error_msg")
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Basic
    @Column(name = "estimate_amount")
    public BigDecimal getEstimateAmount() {
        return estimateAmount;
    }

    public void setEstimateAmount(BigDecimal estimateAmount) {
        this.estimateAmount = estimateAmount;
    }

    @Basic
    @Column(name = "budget_difference")
    public BigDecimal getBudgetDifference() {
        return budgetDifference;
    }

    public void setBudgetDifference(BigDecimal budgetDifference) {
        this.budgetDifference = budgetDifference;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "document_no")
    public Set<OfficeSaleMarketingReimbursementsDetails> getChargeDetail() {
        return chargeDetail;
    }

    public void setChargeDetail(Set<OfficeSaleMarketingReimbursementsDetails> chargeDetail) {
        this.chargeDetail = chargeDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfficeSaleMarketingReimbursements that = (OfficeSaleMarketingReimbursements) o;
        return status == that.status &&
                Objects.equals(documentNo, that.documentNo) &&
                Objects.equals(stationId, that.stationId) &&
                Objects.equals(reimburseAmount, that.reimburseAmount) &&
                Objects.equals(reimburseTime, that.reimburseTime) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(userNo, that.userNo) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(departmentId, that.departmentId) &&
                Objects.equals(departmentNo, that.departmentNo) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(submitStationId, that.submitStationId) &&
                Objects.equals(submitStationName, that.submitStationName) &&
                Objects.equals(fileUrls, that.fileUrls) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(modifier, that.modifier) &&
                Objects.equals(modifyTime, that.modifyTime) &&
                Objects.equals(submitTime, that.submitTime) &&
                Objects.equals(approverId, that.approverId) &&
                Objects.equals(approverNo, that.approverNo) &&
                Objects.equals(approverName, that.approverName) &&
                Objects.equals(approveTime, that.approveTime) &&
                Objects.equals(errorCode, that.errorCode) &&
                Objects.equals(errorMsg, that.errorMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentNo, stationId, status, reimburseAmount, reimburseTime, userId, userNo, userName, departmentId, departmentNo, departmentName, submitStationId, submitStationName, fileUrls, remark, creator, createTime, modifier, modifyTime, submitTime, approverId, approverNo, approverName, approveTime, errorCode, errorMsg);
    }
}
