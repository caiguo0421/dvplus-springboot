package cn.sf_soft.vehicle.certificate.model;

import cn.sf_soft.office.approval.model.ApproveDocuments;
import org.hibernate.annotations.Formula;

import java.sql.Timestamp;
import javax.persistence.*;

/**
 * 车辆合格证出库
 *
 * @author caigu
 */
@Entity
@Table(name = "vehicle_certificate_out_stocks", schema = "dbo")
public class VehicleCertificateOutStocks extends ApproveDocuments<Object> implements java.io.Serializable {

    // Fields
    private String outStockNo;
//    private String stationId;
    private Timestamp outStockTime;
    private Short documentStatus;
    private String outStockType;
    private String objectId;
    private String objectNo;
    private String objectName;
    private String creator;
    private Timestamp createTime;
    private String modifier;
//    private Timestamp modifyTime;
    private String approver;
//    private Timestamp approveTime;
    private String handler;
    private String remark;
//    private String documentNo;
//    private Short status;
//    private String userId;
//    private String userNo;
//    private String userName;
//    private String departmentId;
//    private String departmentName;
//    private String departmentNo;
//    private String submitStationId;
//    private String submitStationName;
//    private Timestamp submitTime;
//    private String approverId;
//    private String approverNo;
//    private String approverName;
    private String errorCode;
    private String errorMsg;
    private String fileUrls;
    private String buyTypeName;

    private String outStockTypeMeaning;

    // Constructors
    public VehicleCertificateOutStocks() {
    }


    // Property accessors
    @Id
    @Column(name = "out_stock_no", unique = true, nullable = false, length = 20)
    public String getOutStockNo() {
        return this.outStockNo;
    }

    public void setOutStockNo(String outStockNo) {
        this.outStockNo = outStockNo;
    }

    @Column(name = "station_id")
    public String getStationId() {
        return this.stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Column(name = "out_stock_time")
    public Timestamp getOutStockTime() {
        return this.outStockTime;
    }

    public void setOutStockTime(Timestamp outStockTime) {
        this.outStockTime = outStockTime;
    }

    @Column(name = "document_status")
    public Short getDocumentStatus() {
        return this.documentStatus;
    }

    public void setDocumentStatus(Short documentStatus) {
        this.documentStatus = documentStatus;
    }

    @Column(name = "out_stock_type")
    public String getOutStockType() {
        return this.outStockType;
    }

    public void setOutStockType(String outStockType) {
        this.outStockType = outStockType;
    }

    @Column(name = "object_id")
    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Column(name = "object_no")
    public String getObjectNo() {
        return this.objectNo;
    }

    public void setObjectNo(String objectNo) {
        this.objectNo = objectNo;
    }

    @Column(name = "object_name")
    public String getObjectName() {
        return this.objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Column(name = "creator")
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "modifier")
    public String getModifier() {
        return this.modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Column(name = "modify_time")
    public Timestamp getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Column(name = "approver")
    public String getApprover() {
        return this.approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    @Column(name = "approve_time")
    public Timestamp getApproveTime() {
        return this.approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    @Column(name = "handler")
    public String getHandler() {
        return this.handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    @Column(name = "remark")
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "document_no")
    public String getDocumentNo() {
        return this.documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Column(name = "status")
    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "user_no")
    public String getUserNo() {
        return this.userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "department_id")
    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "department_name")
    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Column(name = "department_no")
    public String getDepartmentNo() {
        return this.departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    @Column(name = "submit_station_id")
    public String getSubmitStationId() {
        return this.submitStationId;
    }

    public void setSubmitStationId(String submitStationId) {
        this.submitStationId = submitStationId;
    }

    @Column(name = "submit_station_name")
    public String getSubmitStationName() {
        return this.submitStationName;
    }

    public void setSubmitStationName(String submitStationName) {
        this.submitStationName = submitStationName;
    }

    @Column(name = "submit_time")
    public Timestamp getSubmitTime() {
        return this.submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    @Column(name = "approver_id")
    public String getApproverId() {
        return this.approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    @Column(name = "approver_no")
    public String getApproverNo() {
        return this.approverNo;
    }

    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    @Column(name = "approver_name")
    public String getApproverName() {
        return this.approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    @Column(name = "error_code")
    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Column(name = "error_msg")
    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Column(name = "file_urls")
    public String getFileUrls() {
        return this.fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }

    @Column(name = "buy_type_name")
    public String getBuyTypeName() {
        return this.buyTypeName;
    }

    public void setBuyTypeName(String buyTypeName) {
        this.buyTypeName = buyTypeName;
    }

    @Formula("(select t.meaning from sys_flags t where t.field_no = 'certificate_type' AND t.code = out_stock_type)")
    public String getOutStockTypeMeaning() {
        return outStockTypeMeaning;
    }

    public void setOutStockTypeMeaning(String outStockTypeMeaning) {
        this.outStockTypeMeaning = outStockTypeMeaning;
    }
}