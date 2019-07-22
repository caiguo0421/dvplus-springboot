package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by henry on 2018/5/9.
 */
@Entity
@Table(name = "insurance_company_catalog", schema = "dbo")
public class InsuranceCompanyCatalog {
    private String selfId;
    private String supplierId;
    private String categoryId;
    private BigDecimal categoryScale;
    private String categoryType;
    private String comment;
    private BigDecimal categoryScaleSecond;
    private BigDecimal commission;
    private BigDecimal categoryScaleThird;
    private BigDecimal discountRate;
    private BigDecimal taxRate;
    private BigDecimal rebateRatio;
    private BigDecimal rebateRatioSecond;
    private BigDecimal rebateRatioThird;

    @Id
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Basic
    @Column(name = "supplier_id")
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Basic
    @Column(name = "category_id")
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "category_scale")
    public BigDecimal getCategoryScale() {
        return categoryScale;
    }

    public void setCategoryScale(BigDecimal categoryScale) {
        this.categoryScale = categoryScale;
    }

    @Basic
    @Column(name = "category_type")
    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "category_scale_second")
    public BigDecimal getCategoryScaleSecond() {
        return categoryScaleSecond;
    }

    public void setCategoryScaleSecond(BigDecimal categoryScaleSecond) {
        this.categoryScaleSecond = categoryScaleSecond;
    }

    @Basic
    @Column(name = "commission")
    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    @Basic
    @Column(name = "category_scale_third")
    public BigDecimal getCategoryScaleThird() {
        return categoryScaleThird;
    }

    public void setCategoryScaleThird(BigDecimal categoryScaleThird) {
        this.categoryScaleThird = categoryScaleThird;
    }

    @Basic
    @Column(name = "discount_rate")
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    @Basic
    @Column(name = "tax_rate")
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Basic
    @Column(name = "rebate_ratio")
    public BigDecimal getRebateRatio() {
        return rebateRatio;
    }

    public void setRebateRatio(BigDecimal rebateRatio) {
        this.rebateRatio = rebateRatio;
    }

    @Basic
    @Column(name = "rebate_ratio_second")
    public BigDecimal getRebateRatioSecond() {
        return rebateRatioSecond;
    }

    public void setRebateRatioSecond(BigDecimal rebateRatioSecond) {
        this.rebateRatioSecond = rebateRatioSecond;
    }

    @Basic
    @Column(name = "rebate_ratio_third")
    public BigDecimal getRebateRatioThird() {
        return rebateRatioThird;
    }

    public void setRebateRatioThird(BigDecimal rebateRatioThird) {
        this.rebateRatioThird = rebateRatioThird;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InsuranceCompanyCatalog that = (InsuranceCompanyCatalog) o;

        if (selfId != null ? !selfId.equals(that.selfId) : that.selfId != null) return false;
        if (supplierId != null ? !supplierId.equals(that.supplierId) : that.supplierId != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (categoryScale != null ? !categoryScale.equals(that.categoryScale) : that.categoryScale != null)
            return false;
        if (categoryType != null ? !categoryType.equals(that.categoryType) : that.categoryType != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (categoryScaleSecond != null ? !categoryScaleSecond.equals(that.categoryScaleSecond) : that.categoryScaleSecond != null)
            return false;
        if (commission != null ? !commission.equals(that.commission) : that.commission != null) return false;
        if (categoryScaleThird != null ? !categoryScaleThird.equals(that.categoryScaleThird) : that.categoryScaleThird != null)
            return false;
        if (discountRate != null ? !discountRate.equals(that.discountRate) : that.discountRate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = selfId != null ? selfId.hashCode() : 0;
        result = 31 * result + (supplierId != null ? supplierId.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (categoryScale != null ? categoryScale.hashCode() : 0);
        result = 31 * result + (categoryType != null ? categoryType.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (categoryScaleSecond != null ? categoryScaleSecond.hashCode() : 0);
        result = 31 * result + (commission != null ? commission.hashCode() : 0);
        result = 31 * result + (categoryScaleThird != null ? categoryScaleThird.hashCode() : 0);
        result = 31 * result + (discountRate != null ? discountRate.hashCode() : 0);
        return result;
    }
}
