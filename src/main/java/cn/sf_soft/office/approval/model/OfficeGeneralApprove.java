package cn.sf_soft.office.approval.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by henry on 2017/12/1.
 */
@Entity
@Table(name = "office_general_approve", schema = "dbo")
public class OfficeGeneralApprove extends ApproveDocuments {
    private String documentNo;
    private String stationId;
    private Short status;
    private String approveTheme;
    private String approveType;
    private Timestamp applyTime;
    private String userId;
    private String userNo;
    private String userName;
    private String departmentId;
    private String departmentNo;
    private String departmentName;
    private String submitStationId;
    private String submitStationName;
    private String remark;
    private String creator;
    private Timestamp createTime;
    private String modifier;
    private Timestamp modifyTime;
    private Timestamp submitTime;
    private String approverId;
    private String approverNo;
    private String approverName;
    private Timestamp approveTime;
    private String errorCode;
    private String errorMsg;

    @Id
    @Column(name = "document_no", nullable = false, length = 40)
    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Basic
    @Column(name = "station_id", nullable = true, length = 10)
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Basic
    @Column(name = "approve_theme", nullable = true, length = 100)
    public String getApproveTheme() {
        return approveTheme;
    }

    public void setApproveTheme(String approveTheme) {
        this.approveTheme = approveTheme;
    }

    @Basic
    @Column(name = "approve_type", nullable = true, length = 100)
    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    @Basic
    @Column(name = "apply_time", nullable = true)
    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    @Basic
    @Column(name = "user_id", nullable = true, length = 40)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_no", nullable = true, length = 10)
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 10)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "department_id", nullable = true, length = 40)
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "department_no", nullable = true, length = 40)
    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    @Basic
    @Column(name = "department_name", nullable = true, length = 40)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "submit_station_id", nullable = true, length = 10)
    public String getSubmitStationId() {
        return submitStationId;
    }

    public void setSubmitStationId(String submitStationId) {
        this.submitStationId = submitStationId;
    }

    @Basic
    @Column(name = "submit_station_name", nullable = true, length = 10)
    public String getSubmitStationName() {
        return submitStationName;
    }

    public void setSubmitStationName(String submitStationName) {
        this.submitStationName = submitStationName;
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
    @Column(name = "submit_time", nullable = true)
    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    @Basic
    @Column(name = "approver_id", nullable = true, length = 2147483647)
    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    @Basic
    @Column(name = "approver_no", nullable = true, length = 2147483647)
    public String getApproverNo() {
        return approverNo;
    }

    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    @Basic
    @Column(name = "approver_name", nullable = true, length = 2147483647)
    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    @Basic
    @Column(name = "approve_time", nullable = true)
    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OfficeGeneralApprove that = (OfficeGeneralApprove) o;

        if (documentNo != null ? !documentNo.equals(that.documentNo) : that.documentNo != null) return false;
        if (stationId != null ? !stationId.equals(that.stationId) : that.stationId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (approveTheme != null ? !approveTheme.equals(that.approveTheme) : that.approveTheme != null) return false;
        if (approveType != null ? !approveType.equals(that.approveType) : that.approveType != null) return false;
        if (applyTime != null ? !applyTime.equals(that.applyTime) : that.applyTime != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (userNo != null ? !userNo.equals(that.userNo) : that.userNo != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null) return false;
        if (departmentNo != null ? !departmentNo.equals(that.departmentNo) : that.departmentNo != null) return false;
        if (departmentName != null ? !departmentName.equals(that.departmentName) : that.departmentName != null)
            return false;
        if (submitStationId != null ? !submitStationId.equals(that.submitStationId) : that.submitStationId != null)
            return false;
        if (submitStationName != null ? !submitStationName.equals(that.submitStationName) : that.submitStationName != null)
            return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (submitTime != null ? !submitTime.equals(that.submitTime) : that.submitTime != null) return false;
        if (approverId != null ? !approverId.equals(that.approverId) : that.approverId != null) return false;
        if (approverNo != null ? !approverNo.equals(that.approverNo) : that.approverNo != null) return false;
        if (approverName != null ? !approverName.equals(that.approverName) : that.approverName != null) return false;
        if (approveTime != null ? !approveTime.equals(that.approveTime) : that.approveTime != null) return false;
        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;
        if (errorMsg != null ? !errorMsg.equals(that.errorMsg) : that.errorMsg != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = documentNo != null ? documentNo.hashCode() : 0;
        result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (approveTheme != null ? approveTheme.hashCode() : 0);
        result = 31 * result + (approveType != null ? approveType.hashCode() : 0);
        result = 31 * result + (applyTime != null ? applyTime.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (userNo != null ? userNo.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (departmentNo != null ? departmentNo.hashCode() : 0);
        result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
        result = 31 * result + (submitStationId != null ? submitStationId.hashCode() : 0);
        result = 31 * result + (submitStationName != null ? submitStationName.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (submitTime != null ? submitTime.hashCode() : 0);
        result = 31 * result + (approverId != null ? approverId.hashCode() : 0);
        result = 31 * result + (approverNo != null ? approverNo.hashCode() : 0);
        result = 31 * result + (approverName != null ? approverName.hashCode() : 0);
        result = 31 * result + (approveTime != null ? approveTime.hashCode() : 0);
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        result = 31 * result + (errorMsg != null ? errorMsg.hashCode() : 0);
        return result;
    }
}
