package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by henry on 2018/5/29.
 */
@Entity
@Table(name = "vehicle_sale_quotation_loan_budget", schema = "dbo")
public class VehicleSaleQuotationLoanBudget {
    private String selfId;
    private String stationId;
    private Short status;
    private Short loanMode;
    private String customerId;
    private String loanObjectId;
    private String agentId;
    private String quotationId;
    private String remark;
    private String creatorId;
    private Timestamp createTime;
    private String modifierId;
    private Timestamp modifyTime;
    private String approverId;
    private Timestamp approveTime;
    private Short loanType;
    private Short flowStatus;
    private String userId;
    private String departmentId;
    private String submitStationId;
    private Timestamp submitTime;
    private String approverNo;
    private String approverName;
    private String fileUrls;
    private BigDecimal vehiclePriceTotal;
    private BigDecimal superstructureAmount;
    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private Integer periodNumber;
    private BigDecimal monthPay;
    private BigDecimal loanRatio;
    private BigDecimal paymentRatio;

    @Id
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
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
    @Column(name = "loan_mode")
    public Short getLoanMode() {
        return loanMode;
    }

    public void setLoanMode(Short loanMode) {
        this.loanMode = loanMode;
    }

    @Basic
    @Column(name = "customer_id")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "loan_object_id")
    public String getLoanObjectId() {
        return loanObjectId;
    }

    public void setLoanObjectId(String loanObjectId) {
        this.loanObjectId = loanObjectId;
    }

    @Basic
    @Column(name = "agent_id")
    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Basic
    @Column(name = "quotation_id")
    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
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
    @Column(name = "creator_id")
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
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
    @Column(name = "modifier_id")
    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
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
    @Column(name = "approver_id")
    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
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
    @Column(name = "loan_type")
    public Short getLoanType() {
        return loanType;
    }

    public void setLoanType(Short loanType) {
        this.loanType = loanType;
    }

    @Basic
    @Column(name = "flow_status")
    public Short getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(Short flowStatus) {
        this.flowStatus = flowStatus;
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
    @Column(name = "department_id")
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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
    @Column(name = "submit_time")
    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
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
    @Column(name = "file_urls")
    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }

    @Basic
    @Column(name = "vehicle_price_total")
    public BigDecimal getVehiclePriceTotal() {
        return vehiclePriceTotal;
    }

    public void setVehiclePriceTotal(BigDecimal vehiclePriceTotal) {
        this.vehiclePriceTotal = vehiclePriceTotal;
    }

    @Basic
    @Column(name = "superstructure_amount")
    public BigDecimal getSuperstructureAmount() {
        return superstructureAmount;
    }

    public void setSuperstructureAmount(BigDecimal superstructureAmount) {
        this.superstructureAmount = superstructureAmount;
    }

    @Basic
    @Column(name = "loan_amount")
    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Basic
    @Column(name = "interest_rate")
    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    @Basic
    @Column(name = "period_number")
    public Integer getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(Integer periodNumber) {
        this.periodNumber = periodNumber;
    }

    @Basic
    @Column(name = "month_pay")
    public BigDecimal getMonthPay() {
        return monthPay;
    }

    public void setMonthPay(BigDecimal monthPay) {
        this.monthPay = monthPay;
    }

    @Basic
    @Column(name = "loan_ratio")
    public BigDecimal getLoanRatio() {
        return loanRatio;
    }

    public void setLoanRatio(BigDecimal loanRatio) {
        this.loanRatio = loanRatio;
    }

    @Basic
    @Column(name = "payment_ratio")
    public BigDecimal getPaymentRatio() {
        return paymentRatio;
    }

    public void setPaymentRatio(BigDecimal paymentRatio) {
        this.paymentRatio = paymentRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleSaleQuotationLoanBudget that = (VehicleSaleQuotationLoanBudget) o;

        if (selfId != null ? !selfId.equals(that.selfId) : that.selfId != null) return false;
        if (stationId != null ? !stationId.equals(that.stationId) : that.stationId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (loanMode != null ? !loanMode.equals(that.loanMode) : that.loanMode != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (loanObjectId != null ? !loanObjectId.equals(that.loanObjectId) : that.loanObjectId != null) return false;
        if (agentId != null ? !agentId.equals(that.agentId) : that.agentId != null) return false;
        if (quotationId != null ? !quotationId.equals(that.quotationId) : that.quotationId != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (creatorId != null ? !creatorId.equals(that.creatorId) : that.creatorId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifierId != null ? !modifierId.equals(that.modifierId) : that.modifierId != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (approverId != null ? !approverId.equals(that.approverId) : that.approverId != null) return false;
        if (approveTime != null ? !approveTime.equals(that.approveTime) : that.approveTime != null) return false;
        if (loanType != null ? !loanType.equals(that.loanType) : that.loanType != null) return false;
        if (flowStatus != null ? !flowStatus.equals(that.flowStatus) : that.flowStatus != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null) return false;
        if (submitStationId != null ? !submitStationId.equals(that.submitStationId) : that.submitStationId != null)
            return false;
        if (submitTime != null ? !submitTime.equals(that.submitTime) : that.submitTime != null) return false;
        if (approverNo != null ? !approverNo.equals(that.approverNo) : that.approverNo != null) return false;
        if (approverName != null ? !approverName.equals(that.approverName) : that.approverName != null) return false;
        if (fileUrls != null ? !fileUrls.equals(that.fileUrls) : that.fileUrls != null) return false;
        if (vehiclePriceTotal != null ? !vehiclePriceTotal.equals(that.vehiclePriceTotal) : that.vehiclePriceTotal != null)
            return false;
        if (superstructureAmount != null ? !superstructureAmount.equals(that.superstructureAmount) : that.superstructureAmount != null)
            return false;
        if (loanAmount != null ? !loanAmount.equals(that.loanAmount) : that.loanAmount != null) return false;
        if (interestRate != null ? !interestRate.equals(that.interestRate) : that.interestRate != null) return false;
        if (periodNumber != null ? !periodNumber.equals(that.periodNumber) : that.periodNumber != null) return false;
        if (monthPay != null ? !monthPay.equals(that.monthPay) : that.monthPay != null) return false;
        if (loanRatio != null ? !loanRatio.equals(that.loanRatio) : that.loanRatio != null) return false;
        if (paymentRatio != null ? !paymentRatio.equals(that.paymentRatio) : that.paymentRatio != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = selfId != null ? selfId.hashCode() : 0;
        result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (loanMode != null ? loanMode.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (loanObjectId != null ? loanObjectId.hashCode() : 0);
        result = 31 * result + (agentId != null ? agentId.hashCode() : 0);
        result = 31 * result + (quotationId != null ? quotationId.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifierId != null ? modifierId.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (approverId != null ? approverId.hashCode() : 0);
        result = 31 * result + (approveTime != null ? approveTime.hashCode() : 0);
        result = 31 * result + (loanType != null ? loanType.hashCode() : 0);
        result = 31 * result + (flowStatus != null ? flowStatus.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (submitStationId != null ? submitStationId.hashCode() : 0);
        result = 31 * result + (submitTime != null ? submitTime.hashCode() : 0);
        result = 31 * result + (approverNo != null ? approverNo.hashCode() : 0);
        result = 31 * result + (approverName != null ? approverName.hashCode() : 0);
        result = 31 * result + (fileUrls != null ? fileUrls.hashCode() : 0);
        result = 31 * result + (vehiclePriceTotal != null ? vehiclePriceTotal.hashCode() : 0);
        result = 31 * result + (superstructureAmount != null ? superstructureAmount.hashCode() : 0);
        result = 31 * result + (loanAmount != null ? loanAmount.hashCode() : 0);
        result = 31 * result + (interestRate != null ? interestRate.hashCode() : 0);
        result = 31 * result + (periodNumber != null ? periodNumber.hashCode() : 0);
        result = 31 * result + (monthPay != null ? monthPay.hashCode() : 0);
        result = 31 * result + (loanRatio != null ? loanRatio.hashCode() : 0);
        result = 31 * result + (paymentRatio != null ? paymentRatio.hashCode() : 0);
        return result;
    }
}
