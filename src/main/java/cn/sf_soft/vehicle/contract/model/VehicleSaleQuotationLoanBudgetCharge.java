package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by henry on 2018/5/29.
 */
@Entity
@Table(name = "vehicle_sale_quotation_loan_budget_charge", schema = "dbo")
public class VehicleSaleQuotationLoanBudgetCharge {
    private String selfId;
    private String quotationLoanBudgetId;
    private String chargeId;
    private Short direction;
    private String objectId;
    private Timestamp arapDate;
    private Timestamp returnDate;
    private Short moneyType;
    private BigDecimal amount;
    private BigDecimal loanAmount;
    private BigDecimal cost;
    private Boolean isReimbursed;
    private String abortReason;
    private Short status;
    private String remark;
    private String creatorId;
    private Timestamp createTime;
    private Timestamp abortTime;
    private String abortPersonId;
    private String chargeName;

    @Id
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Basic
    @Column(name = "quotation_loan_budget_id")
    public String getQuotationLoanBudgetId() {
        return quotationLoanBudgetId;
    }

    public void setQuotationLoanBudgetId(String quotationLoanBudgetId) {
        this.quotationLoanBudgetId = quotationLoanBudgetId;
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
    @Column(name = "direction")
    public Short getDirection() {
        return direction;
    }

    public void setDirection(Short direction) {
        this.direction = direction;
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
    @Column(name = "arap_date")
    public Timestamp getArapDate() {
        return arapDate;
    }

    public void setArapDate(Timestamp arapDate) {
        this.arapDate = arapDate;
    }

    @Basic
    @Column(name = "return_date")
    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    @Basic
    @Column(name = "money_type")
    public Short getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Short moneyType) {
        this.moneyType = moneyType;
    }

    @Basic
    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
    @Column(name = "cost")
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
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
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleSaleQuotationLoanBudgetCharge that = (VehicleSaleQuotationLoanBudgetCharge) o;

        if (selfId != null ? !selfId.equals(that.selfId) : that.selfId != null) return false;
        if (quotationLoanBudgetId != null ? !quotationLoanBudgetId.equals(that.quotationLoanBudgetId) : that.quotationLoanBudgetId != null)
            return false;
        if (chargeId != null ? !chargeId.equals(that.chargeId) : that.chargeId != null) return false;
        if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;
        if (objectId != null ? !objectId.equals(that.objectId) : that.objectId != null) return false;
        if (arapDate != null ? !arapDate.equals(that.arapDate) : that.arapDate != null) return false;
        if (returnDate != null ? !returnDate.equals(that.returnDate) : that.returnDate != null) return false;
        if (moneyType != null ? !moneyType.equals(that.moneyType) : that.moneyType != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (loanAmount != null ? !loanAmount.equals(that.loanAmount) : that.loanAmount != null) return false;
        if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
        if (isReimbursed != null ? !isReimbursed.equals(that.isReimbursed) : that.isReimbursed != null) return false;
        if (abortReason != null ? !abortReason.equals(that.abortReason) : that.abortReason != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (creatorId != null ? !creatorId.equals(that.creatorId) : that.creatorId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (abortTime != null ? !abortTime.equals(that.abortTime) : that.abortTime != null) return false;
        if (abortPersonId != null ? !abortPersonId.equals(that.abortPersonId) : that.abortPersonId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = selfId != null ? selfId.hashCode() : 0;
        result = 31 * result + (quotationLoanBudgetId != null ? quotationLoanBudgetId.hashCode() : 0);
        result = 31 * result + (chargeId != null ? chargeId.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (objectId != null ? objectId.hashCode() : 0);
        result = 31 * result + (arapDate != null ? arapDate.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        result = 31 * result + (moneyType != null ? moneyType.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (loanAmount != null ? loanAmount.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (isReimbursed != null ? isReimbursed.hashCode() : 0);
        result = 31 * result + (abortReason != null ? abortReason.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (abortTime != null ? abortTime.hashCode() : 0);
        result = 31 * result + (abortPersonId != null ? abortPersonId.hashCode() : 0);
        return result;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }
}
