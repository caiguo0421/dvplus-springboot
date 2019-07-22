package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by henry on 2018/5/29.
 */
@Entity
@Table(name = "vehicle_sale_quotation_charge", schema = "dbo")
public class VehicleSaleQuotationCharge {
    private String selfId;
    private String chargeId;
    private String chargeName;
    private BigDecimal income;
    private BigDecimal chargePf;
    private String chargeComment;
    private String remark;
    private Boolean paidByBill;
    private String quotationId;

    @Id
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
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
    @Column(name = "charge_name")
    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    @Basic
    @Column(name = "income")
    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    @Basic
    @Column(name = "charge_pf")
    public BigDecimal getChargePf() {
        return chargePf;
    }

    public void setChargePf(BigDecimal chargePf) {
        this.chargePf = chargePf;
    }

    @Basic
    @Column(name = "charge_comment")
    public String getChargeComment() {
        return chargeComment;
    }

    public void setChargeComment(String chargeComment) {
        this.chargeComment = chargeComment;
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
    @Column(name = "paid_by_bill")
    public Boolean getPaidByBill() {
        return paidByBill;
    }

    public void setPaidByBill(Boolean paidByBill) {
        this.paidByBill = paidByBill;
    }

    @Basic
    @Column(name = "quotation_id")
    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleSaleQuotationCharge that = (VehicleSaleQuotationCharge) o;

        if (selfId != null ? !selfId.equals(that.selfId) : that.selfId != null) return false;
        if (chargeId != null ? !chargeId.equals(that.chargeId) : that.chargeId != null) return false;
        if (chargeName != null ? !chargeName.equals(that.chargeName) : that.chargeName != null) return false;
        if (income != null ? !income.equals(that.income) : that.income != null) return false;
        if (chargePf != null ? !chargePf.equals(that.chargePf) : that.chargePf != null) return false;
        if (chargeComment != null ? !chargeComment.equals(that.chargeComment) : that.chargeComment != null)
            return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (paidByBill != null ? !paidByBill.equals(that.paidByBill) : that.paidByBill != null) return false;
        if (quotationId != null ? !quotationId.equals(that.quotationId) : that.quotationId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = selfId != null ? selfId.hashCode() : 0;
        result = 31 * result + (chargeId != null ? chargeId.hashCode() : 0);
        result = 31 * result + (chargeName != null ? chargeName.hashCode() : 0);
        result = 31 * result + (income != null ? income.hashCode() : 0);
        result = 31 * result + (chargePf != null ? chargePf.hashCode() : 0);
        result = 31 * result + (chargeComment != null ? chargeComment.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (paidByBill != null ? paidByBill.hashCode() : 0);
        result = 31 * result + (quotationId != null ? quotationId.hashCode() : 0);
        return result;
    }
}
