package cn.sf_soft.office.approval.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/15 16:24
 * @Description:
 */
@Entity
@Table(name = "vehicle_loan_budget_charge")
public class VehicleLoanBudgetCharge {
    private String selfId;
    private String budgetDetailId;
    private String chargeId;
    private String objectId;
    private Timestamp paymentDate;
    private Byte moneyType;
    private Double income;
    private Double expenditure;
    private Double loanAmount;
    private Double cost;
    private Boolean isReimbursed;
    private String abortReason;
    private Byte status;
    private String remark;
    private String creatorId;
    private Timestamp createTime;
    private Timestamp abortTime;
    private String abortPersonId;
    private String chargeGroupId;
    //应收对象ID ADM19010008
    private String arObjectId;

    //应收日期 ADM19010056
    private Timestamp arDate;

    @Id
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Basic
    @Column(name = "budget_detail_id")
    public String getBudgetDetailId() {
        return budgetDetailId;
    }

    public void setBudgetDetailId(String budgetDetailId) {
        this.budgetDetailId = budgetDetailId;
    }

    @Basic
    @Column(name = "charge_id")
    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    @Basic
    @Column(name = "object_id")
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Basic
    @Column(name = "payment_date")
    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Basic
    @Column(name = "money_type")
    public Byte getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Byte moneyType) {
        this.moneyType = moneyType;
    }

    @Basic
    @Column(name = "income")
    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    @Basic
    @Column(name = "expenditure")
    public Double getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(Double expenditure) {
        this.expenditure = expenditure;
    }

    @Basic
    @Column(name = "loan_amount")
    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Basic
    @Column(name = "cost")
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "is_reimbursed")
    public Boolean getIsReimbursed() {
        return isReimbursed;
    }

    public void setIsReimbursed(Boolean reimbursed) {
        isReimbursed = reimbursed;
    }

    @Basic
    @Column(name = "abort_reason")
    public String getAbortReason() {
        return abortReason;
    }

    public void setAbortReason(String abortReason) {
        this.abortReason = abortReason;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
    @Column(name = "abort_time")
    public Timestamp getAbortTime() {
        return abortTime;
    }

    public void setAbortTime(Timestamp abortTime) {
        this.abortTime = abortTime;
    }

    @Basic
    @Column(name = "abort_person_id")
    public String getAbortPersonId() {
        return abortPersonId;
    }

    public void setAbortPersonId(String abortPersonId) {
        this.abortPersonId = abortPersonId;
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
        VehicleLoanBudgetCharge that = (VehicleLoanBudgetCharge) o;
        return moneyType == that.moneyType &&
                status == that.status &&
                Objects.equals(selfId, that.selfId) &&
                Objects.equals(budgetDetailId, that.budgetDetailId) &&
                Objects.equals(chargeId, that.chargeId) &&
                Objects.equals(objectId, that.objectId) &&
                Objects.equals(paymentDate, that.paymentDate) &&
                Objects.equals(income, that.income) &&
                Objects.equals(expenditure, that.expenditure) &&
                Objects.equals(loanAmount, that.loanAmount) &&
                Objects.equals(cost, that.cost) &&
                Objects.equals(abortReason, that.abortReason) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(creatorId, that.creatorId) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(abortTime, that.abortTime) &&
                Objects.equals(abortPersonId, that.abortPersonId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(selfId, budgetDetailId, chargeId, objectId, paymentDate, moneyType, income, expenditure, loanAmount, cost, abortReason, status, remark, creatorId, createTime, abortTime, abortPersonId);
    }
}
