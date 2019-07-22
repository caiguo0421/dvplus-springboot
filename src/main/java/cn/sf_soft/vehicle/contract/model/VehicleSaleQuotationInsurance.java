package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by henry on 2018/5/29.
 */
@Entity
@Table(name = "vehicle_sale_quotation_insurance", schema = "dbo")
public class VehicleSaleQuotationInsurance {
    private String selfId;
    private Integer insuranceYear;
    private String categoryId;
    private String categoryName;
    private String categoryType;
    private BigDecimal categoryIncome;
    private BigDecimal categoryScale;
    private String remark;
    private String supplierId;
    private Integer purchaseSort;
    private Boolean isFree;
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
    @Column(name = "insurance_year")
    public Integer getInsuranceYear() {
        return insuranceYear;
    }

    public void setInsuranceYear(Integer insuranceYear) {
        this.insuranceYear = insuranceYear;
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
    @Column(name = "category_name")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
    @Column(name = "category_income")
    public BigDecimal getCategoryIncome() {
        return categoryIncome;
    }

    public void setCategoryIncome(BigDecimal categoryIncome) {
        this.categoryIncome = categoryIncome;
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
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
    @Column(name = "purchase_sort")
    public Integer getPurchaseSort() {
        return purchaseSort;
    }

    public void setPurchaseSort(Integer purchaseSort) {
        this.purchaseSort = purchaseSort;
    }

    @Basic
    @Column(name = "is_free")
    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean free) {
        isFree = free;
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

        VehicleSaleQuotationInsurance that = (VehicleSaleQuotationInsurance) o;

        if (selfId != null ? !selfId.equals(that.selfId) : that.selfId != null) return false;
        if (insuranceYear != null ? !insuranceYear.equals(that.insuranceYear) : that.insuranceYear != null)
            return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (categoryName != null ? !categoryName.equals(that.categoryName) : that.categoryName != null) return false;
        if (categoryType != null ? !categoryType.equals(that.categoryType) : that.categoryType != null) return false;
        if (categoryIncome != null ? !categoryIncome.equals(that.categoryIncome) : that.categoryIncome != null)
            return false;
        if (categoryScale != null ? !categoryScale.equals(that.categoryScale) : that.categoryScale != null)
            return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (supplierId != null ? !supplierId.equals(that.supplierId) : that.supplierId != null) return false;
        if (purchaseSort != null ? !purchaseSort.equals(that.purchaseSort) : that.purchaseSort != null) return false;
        if (quotationId != null ? !quotationId.equals(that.quotationId) : that.quotationId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = selfId != null ? selfId.hashCode() : 0;
        result = 31 * result + (insuranceYear != null ? insuranceYear.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (categoryType != null ? categoryType.hashCode() : 0);
        result = 31 * result + (categoryIncome != null ? categoryIncome.hashCode() : 0);
        result = 31 * result + (categoryScale != null ? categoryScale.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (supplierId != null ? supplierId.hashCode() : 0);
        result = 31 * result + (purchaseSort != null ? purchaseSort.hashCode() : 0);
        result = 31 * result + (quotationId != null ? quotationId.hashCode() : 0);
        return result;
    }
}
