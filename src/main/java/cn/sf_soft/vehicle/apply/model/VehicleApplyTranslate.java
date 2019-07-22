package cn.sf_soft.vehicle.apply.model;

import cn.sf_soft.office.approval.model.ApproveDocuments;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 车辆请调
 * @author :caigx
 *
 */
public class VehicleApplyTranslate extends ApproveDocuments<VehicleApplyTranslateDetail> implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    private Timestamp applyTime;
    private String outStationId;
    private String warehouseId;
    private String warehouseName;
    private String remark;
    private String creator;
    private Timestamp createTime;
    private String modifier;
    private String errorCode;
    private String errorMsg;

    //Transient 调入调出站点名称
    private String inStationName;
    private String outStationName;


    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    public String getOutStationId() {
        return outStationId;
    }

    public void setOutStationId(String outStationId) {
        this.outStationId = outStationId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getApproverNo() {
        return approverNo;
    }

    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getInStationName() {
        return inStationName;
    }

    public void setInStationName(String inStationName) {
        this.inStationName = inStationName;
    }

    public String getOutStationName() {
        return outStationName;
    }

    public void setOutStationName(String outStationName) {
        this.outStationName = outStationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleApplyTranslate that = (VehicleApplyTranslate) o;
        return status == that.status && Objects.equals(documentNo, that.documentNo) && Objects.equals(stationId, that.stationId) && Objects.equals(applyTime, that.applyTime) && Objects.equals(outStationId, that.outStationId) && Objects.equals(warehouseId, that.warehouseId) && Objects.equals(warehouseName, that.warehouseName) && Objects.equals(userId, that.userId) && Objects.equals(userNo, that.userNo) && Objects.equals(userName, that.userName) && Objects.equals(departmentId, that.departmentId) && Objects.equals(departmentNo, that.departmentNo) && Objects.equals(departmentName, that.departmentName) && Objects.equals(submitStationId, that.submitStationId) && Objects.equals(submitStationName, that.submitStationName) && Objects.equals(remark, that.remark) && Objects.equals(creator, that.creator) && Objects.equals(createTime, that.createTime) && Objects.equals(modifier, that.modifier) && Objects.equals(modifyTime, that.modifyTime) && Objects.equals(submitTime, that.submitTime) && Objects.equals(approverId, that.approverId) && Objects.equals(approverNo, that.approverNo) && Objects.equals(approverName, that.approverName) && Objects.equals(approveTime, that.approveTime) && Objects.equals(errorCode, that.errorCode) && Objects.equals(errorMsg, that.errorMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentNo, stationId, status, applyTime, outStationId, warehouseId, warehouseName, userId, userNo, userName, departmentId, departmentNo, departmentName, submitStationId, submitStationName, remark, creator, createTime, modifier, modifyTime, submitTime, approverId, approverNo, approverName, approveTime, errorCode, errorMsg);
    }
}
