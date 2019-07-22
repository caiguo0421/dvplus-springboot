package cn.sf_soft.vehicle.out.model;

import cn.sf_soft.office.approval.model.ApproveDocuments;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 车辆出库
 * @author  caigx
 */
public class VehicleOutStocks extends ApproveDocuments<VehicleOutStockDetail> implements java.io.Serializable  {

    private static final long serialVersionUID = 1L;

    private String outStockNo;
//    private String stationId;
    private Timestamp outStockTime;
    private Integer outStockCount;
    private BigDecimal outStockMoney;
    private Byte outStockType;
    private Byte originalStockType;
    private String contractNo;
    private String contractCode;
    private Byte customerType;
    private String customerId;
    private String customerNo;
    private String customerName;
    private String deliveryLocus;
    private String comment;
    private String approver;
//    private Timestamp approveTime;
    private Byte approveStatus;
    private String originalOutStockNo;
    private String cancelReason;
    private String targetStationId;
    private Byte confirmStatus;
    private String confirmMan;
    private Timestamp confirmTime;
    private String vmiNo;
    private String seller;
    private BigDecimal outStockCost;
    private BigDecimal outStockCarriage;
    private BigDecimal totModifiedFee;
    private BigDecimal insuranceCost;
    private BigDecimal presentCost;
    private BigDecimal chargeCost;
    private String unitNo;
    private String unitName;
    private String creator;
    private Timestamp createTime;
    private String modifier;
//    private Timestamp modifyTime;
    private String carryCompanyId;
    private String carryCompanyNo;
    private String carryCompanyName;
    private BigDecimal carriage;
    private String remark;
    private Integer sellerCallBackFlag;
    private String errorCode;
    private String errorMsg;
    private String identifier;
    private String saleDepartmentId;
//    private String departmentId;
//    private String documentNo;
//    private Short status;
//    private String userId;
//    private String userNo;
//    private String userName;
//    private String departmentNo;
//    private String departmentName;
//    private String submitStationId;
//    private String submitStationName;
//    private Timestamp submitTime;
//    private String approverId;
//    private String approverNo;
//    private String approverName;
    private String vatNo;

    //Transient 调入调出站点名称
    private String inStationName;
    private String outStationName;

    public String getOutStockNo() {
        return outStockNo;
    }

    public void setOutStockNo(String outStockNo) {
        this.outStockNo = outStockNo;
    }

    @Override
    public String getStationId() {
        return stationId;
    }

    @Override
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Timestamp getOutStockTime() {
        return outStockTime;
    }

    public void setOutStockTime(Timestamp outStockTime) {
        this.outStockTime = outStockTime;
    }

    public Integer getOutStockCount() {
        return outStockCount;
    }

    public void setOutStockCount(Integer outStockCount) {
        this.outStockCount = outStockCount;
    }

    public BigDecimal getOutStockMoney() {
        return outStockMoney;
    }

    public void setOutStockMoney(BigDecimal outStockMoney) {
        this.outStockMoney = outStockMoney;
    }

    public Byte getOutStockType() {
        return outStockType;
    }

    public void setOutStockType(Byte outStockType) {
        this.outStockType = outStockType;
    }

    public Byte getOriginalStockType() {
        return originalStockType;
    }

    public void setOriginalStockType(Byte originalStockType) {
        this.originalStockType = originalStockType;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public Byte getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Byte customerType) {
        this.customerType = customerType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliveryLocus() {
        return deliveryLocus;
    }

    public void setDeliveryLocus(String deliveryLocus) {
        this.deliveryLocus = deliveryLocus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    public Byte getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Byte approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getOriginalOutStockNo() {
        return originalOutStockNo;
    }

    public void setOriginalOutStockNo(String originalOutStockNo) {
        this.originalOutStockNo = originalOutStockNo;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getTargetStationId() {
        return targetStationId;
    }

    public void setTargetStationId(String targetStationId) {
        this.targetStationId = targetStationId;
    }

    public Byte getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Byte confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getConfirmMan() {
        return confirmMan;
    }

    public void setConfirmMan(String confirmMan) {
        this.confirmMan = confirmMan;
    }

    public Timestamp getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Timestamp confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getVmiNo() {
        return vmiNo;
    }

    public void setVmiNo(String vmiNo) {
        this.vmiNo = vmiNo;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public BigDecimal getOutStockCost() {
        return outStockCost;
    }

    public void setOutStockCost(BigDecimal outStockCost) {
        this.outStockCost = outStockCost;
    }

    public BigDecimal getOutStockCarriage() {
        return outStockCarriage;
    }

    public void setOutStockCarriage(BigDecimal outStockCarriage) {
        this.outStockCarriage = outStockCarriage;
    }

    public BigDecimal getTotModifiedFee() {
        return totModifiedFee;
    }

    public void setTotModifiedFee(BigDecimal totModifiedFee) {
        this.totModifiedFee = totModifiedFee;
    }

    public BigDecimal getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(BigDecimal insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    public BigDecimal getPresentCost() {
        return presentCost;
    }

    public void setPresentCost(BigDecimal presentCost) {
        this.presentCost = presentCost;
    }

    public BigDecimal getChargeCost() {
        return chargeCost;
    }

    public void setChargeCost(BigDecimal chargeCost) {
        this.chargeCost = chargeCost;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    public String getCarryCompanyId() {
        return carryCompanyId;
    }

    public void setCarryCompanyId(String carryCompanyId) {
        this.carryCompanyId = carryCompanyId;
    }

    public String getCarryCompanyNo() {
        return carryCompanyNo;
    }

    public void setCarryCompanyNo(String carryCompanyNo) {
        this.carryCompanyNo = carryCompanyNo;
    }

    public String getCarryCompanyName() {
        return carryCompanyName;
    }

    public void setCarryCompanyName(String carryCompanyName) {
        this.carryCompanyName = carryCompanyName;
    }

    public BigDecimal getCarriage() {
        return carriage;
    }

    public void setCarriage(BigDecimal carriage) {
        this.carriage = carriage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSellerCallBackFlag() {
        return sellerCallBackFlag;
    }

    public void setSellerCallBackFlag(Integer sellerCallBackFlag) {
        this.sellerCallBackFlag = sellerCallBackFlag;
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSaleDepartmentId() {
        return saleDepartmentId;
    }

    public void setSaleDepartmentId(String saleDepartmentId) {
        this.saleDepartmentId = saleDepartmentId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String getDocumentNo() {
        return documentNo;
    }

    @Override
    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Override
    public Short getStatus() {
        return status;
    }

    @Override
    public void setStatus(Short status) {
        this.status = status;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserNo() {
        return userNo;
    }

    @Override
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getDepartmentNo() {
        return departmentNo;
    }

    @Override
    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    @Override
    public String getDepartmentName() {
        return departmentName;
    }

    @Override
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String getSubmitStationId() {
        return submitStationId;
    }

    public void setSubmitStationId(String submitStationId) {
        this.submitStationId = submitStationId;
    }

    @Override
    public String getSubmitStationName() {
        return submitStationName;
    }

    @Override
    public void setSubmitStationName(String submitStationName) {
        this.submitStationName = submitStationName;
    }

    @Override
    public Timestamp getSubmitTime() {
        return submitTime;
    }

    @Override
    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    @Override
    public String getApproverId() {
        return approverId;
    }

    @Override
    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    @Override
    public String getApproverNo() {
        return approverNo;
    }

    @Override
    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    @Override
    public String getApproverName() {
        return approverName;
    }

    @Override
    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
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
        VehicleOutStocks that = (VehicleOutStocks) o;
        return Objects.equals(outStockNo, that.outStockNo) && Objects.equals(stationId, that.stationId) && Objects.equals(outStockTime, that.outStockTime) && Objects.equals(outStockCount, that.outStockCount) && Objects.equals(outStockMoney, that.outStockMoney) && Objects.equals(outStockType, that.outStockType) && Objects.equals(originalStockType, that.originalStockType) && Objects.equals(contractNo, that.contractNo) && Objects.equals(contractCode, that.contractCode) && Objects.equals(customerType, that.customerType) && Objects.equals(customerId, that.customerId) && Objects.equals(customerNo, that.customerNo) && Objects.equals(customerName, that.customerName) && Objects.equals(deliveryLocus, that.deliveryLocus) && Objects.equals(comment, that.comment) && Objects.equals(approver, that.approver) && Objects.equals(approveTime, that.approveTime) && Objects.equals(approveStatus, that.approveStatus) && Objects.equals(originalOutStockNo, that.originalOutStockNo) && Objects.equals(cancelReason, that.cancelReason) && Objects.equals(targetStationId, that.targetStationId) && Objects.equals(confirmStatus, that.confirmStatus) && Objects.equals(confirmMan, that.confirmMan) && Objects.equals(confirmTime, that.confirmTime) && Objects.equals(vmiNo, that.vmiNo) && Objects.equals(seller, that.seller) && Objects.equals(outStockCost, that.outStockCost) && Objects.equals(outStockCarriage, that.outStockCarriage) && Objects.equals(totModifiedFee, that.totModifiedFee) && Objects.equals(insuranceCost, that.insuranceCost) && Objects.equals(presentCost, that.presentCost) && Objects.equals(chargeCost, that.chargeCost) && Objects.equals(unitNo, that.unitNo) && Objects.equals(unitName, that.unitName) && Objects.equals(creator, that.creator) && Objects.equals(createTime, that.createTime) && Objects.equals(modifier, that.modifier) && Objects.equals(modifyTime, that.modifyTime) && Objects.equals(carryCompanyId, that.carryCompanyId) && Objects.equals(carryCompanyNo, that.carryCompanyNo) && Objects.equals(carryCompanyName, that.carryCompanyName) && Objects.equals(carriage, that.carriage) && Objects.equals(remark, that.remark) && Objects.equals(sellerCallBackFlag, that.sellerCallBackFlag) && Objects.equals(errorCode, that.errorCode) && Objects.equals(errorMsg, that.errorMsg) && Objects.equals(identifier, that.identifier) && Objects.equals(saleDepartmentId, that.saleDepartmentId) && Objects.equals(departmentId, that.departmentId) && Objects.equals(documentNo, that.documentNo) && Objects.equals(status, that.status) && Objects.equals(userId, that.userId) && Objects.equals(userNo, that.userNo) && Objects.equals(userName, that.userName) && Objects.equals(departmentNo, that.departmentNo) && Objects.equals(departmentName, that.departmentName) && Objects.equals(submitStationId, that.submitStationId) && Objects.equals(submitStationName, that.submitStationName) && Objects.equals(submitTime, that.submitTime) && Objects.equals(approverId, that.approverId) && Objects.equals(approverNo, that.approverNo) && Objects.equals(approverName, that.approverName) && Objects.equals(vatNo, that.vatNo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(outStockNo, stationId, outStockTime, outStockCount, outStockMoney, outStockType, originalStockType, contractNo, contractCode, customerType, customerId, customerNo, customerName, deliveryLocus, comment, approver, approveTime, approveStatus, originalOutStockNo, cancelReason, targetStationId, confirmStatus, confirmMan, confirmTime, vmiNo, seller, outStockCost, outStockCarriage, totModifiedFee, insuranceCost, presentCost, chargeCost, unitNo, unitName, creator, createTime, modifier, modifyTime, carryCompanyId, carryCompanyNo, carryCompanyName, carriage, remark, sellerCallBackFlag, errorCode, errorMsg, identifier, saleDepartmentId, departmentId, documentNo, status, userId, userNo, userName, departmentNo, departmentName, submitStationId, submitStationName, submitTime, approverId, approverNo, approverName, vatNo);
    }
}
