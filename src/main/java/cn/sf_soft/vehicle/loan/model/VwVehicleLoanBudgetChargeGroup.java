package cn.sf_soft.vehicle.loan.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "vw_vehicle_loan_budget_charge_group")
@org.hibernate.annotations.Entity(mutable = false)
public class VwVehicleLoanBudgetChargeGroup implements java.io.Serializable {

    private String selfId;
    private String groupId;
    private String chargeGroupId;
    private String chargeId;
    private String objectId;
    private Timestamp paymentDate;
    private Byte moneyType;
    private BigDecimal income;
    private BigDecimal expenditure;
    private BigDecimal loanAmount;
    private String abortReason;
    private Byte status;
    private String remark;
    private String creatorId;
    private Timestamp createTime;
    private Timestamp abortTime;
    private String abortPersonId;
    private String objectNo;
    private String objectName;
    private String moneyTypeMeaning;
    private String statusMeaning;
    private String chargeName;
    private BigDecimal arCharge;
    private String documentNo;

    //应收对象ID ADM19010008
    private String arObjectId;

    //应收日期 ADM19010056
    private Timestamp arDate;

    @Id
    @Column(name = "self_id", nullable = true)
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Basic
    @Column(name = "group_id", nullable = true, length = 40)
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "charge_id", nullable = true, length = 40)
    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    @Basic
    @Column(name = "object_id", nullable = true, length = 40)
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Basic
    @Column(name = "payment_date", nullable = true)
    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Basic
    @Column(name = "money_type", nullable = false)
    public Byte getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Byte moneyType) {
        this.moneyType = moneyType;
    }

    @Basic
    @Column(name = "income", nullable = true)
    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    @Basic
    @Column(name = "expenditure", nullable = true)
    public BigDecimal getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(BigDecimal expenditure) {
        this.expenditure = expenditure;
    }

    @Basic
    @Column(name = "loan_amount", nullable = true)
    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Basic
    @Column(name = "abort_reason", nullable = true, length = 2147483647)
    public String getAbortReason() {
        return abortReason;
    }

    public void setAbortReason(String abortReason) {
        this.abortReason = abortReason;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
    @Column(name = "creator_id", nullable = false, length = 40)
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "abort_time", nullable = true)
    public Timestamp getAbortTime() {
        return abortTime;
    }

    public void setAbortTime(Timestamp abortTime) {
        this.abortTime = abortTime;
    }

    @Basic
    @Column(name = "abort_person_id", nullable = true, length = 40)
    public String getAbortPersonId() {
        return abortPersonId;
    }

    public void setAbortPersonId(String abortPersonId) {
        this.abortPersonId = abortPersonId;
    }

    @Basic
    @Column(name = "object_no", nullable = true, length = 40)
    public String getObjectNo() {
        return objectNo;
    }

    public void setObjectNo(String objectNo) {
        this.objectNo = objectNo;
    }

    @Basic
    @Column(name = "object_name", nullable = true, length = 40)
    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Basic
    @Column(name = "money_type_meaning", nullable = true, length = 10)
    public String getMoneyTypeMeaning() {
        return moneyTypeMeaning;
    }

    public void setMoneyTypeMeaning(String moneyTypeMeaning) {
        this.moneyTypeMeaning = moneyTypeMeaning;
    }

    @Basic
    @Column(name = "status_meaning", nullable = true, length = 10)
    public String getStatusMeaning() {
        return statusMeaning;
    }

    public void setStatusMeaning(String statusMeaning) {
        this.statusMeaning = statusMeaning;
    }

    @Basic
    @Column(name = "charge_name", nullable = true, length = 20)
    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    @Basic
    @Column(name = "ar_charge", nullable = true)
    public BigDecimal getArCharge() {
        return arCharge;
    }

    public void setArCharge(BigDecimal arCharge) {
        this.arCharge = arCharge;
    }

    @Basic
    @Column(name = "document_no", nullable = true, length = 40)
    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Basic
    @Column(name = "charge_group_id")
    public String getChargeGroupId() {
        return chargeGroupId;
    }

    public void setChargeGroupId(String chargeGroupId) {
        this.chargeGroupId = chargeGroupId;
    }

    @Basic
    @Column(name = "ar_object_id")
    public String getArObjectId() {
        return arObjectId;
    }

    public void setArObjectId(String arObjectId) {
        this.arObjectId = arObjectId;
    }

    @Basic
    @Column(name = "ar_date")
    public Timestamp getArDate() {
        return arDate;
    }

    public void setArDate(Timestamp arDate) {
        this.arDate = arDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VwVehicleLoanBudgetChargeGroup that = (VwVehicleLoanBudgetChargeGroup) o;
        return moneyType == that.moneyType && status == that.status && Objects.equals(selfId, that.selfId) && Objects.equals(groupId, that.groupId) && Objects.equals(chargeId, that.chargeId) && Objects.equals(objectId, that.objectId) && Objects.equals(paymentDate, that.paymentDate) && Objects.equals(income, that.income) && Objects.equals(expenditure, that.expenditure) && Objects.equals(loanAmount, that.loanAmount) && Objects.equals(abortReason, that.abortReason) && Objects.equals(remark, that.remark) && Objects.equals(creatorId, that.creatorId) && Objects.equals(createTime, that.createTime) && Objects.equals(abortTime, that.abortTime) && Objects.equals(abortPersonId, that.abortPersonId) && Objects.equals(objectNo, that.objectNo) && Objects.equals(objectName, that.objectName) && Objects.equals(moneyTypeMeaning, that.moneyTypeMeaning) && Objects.equals(statusMeaning, that.statusMeaning) && Objects.equals(chargeName, that.chargeName) && Objects.equals(arCharge, that.arCharge) && Objects.equals(documentNo, that.documentNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selfId, groupId, chargeId, objectId, paymentDate, moneyType, income, expenditure, loanAmount, abortReason, status, remark, creatorId, createTime, abortTime, abortPersonId, objectNo, objectName, moneyTypeMeaning, statusMeaning, chargeName, arCharge, documentNo);
    }


}
