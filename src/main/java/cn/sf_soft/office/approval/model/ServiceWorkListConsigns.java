package cn.sf_soft.office.approval.model;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Auther: chenbiao
 * @Date: 2019/7/15 16:19
 * @Description:
 */
@Entity
@Table(name = "service_work_list_consigns")
public class ServiceWorkListConsigns {
    private String listConsignId;
    private String taskNo;
    private String consignId;
    private String consignName;
    private String consignNo;
    private Byte consignCategory;
    private String consignType;
    private String supplierId;
    private String supplierNo;
    private String supplierName;
    private BigDecimal agio;
    private BigDecimal cost;
    private BigDecimal price;
    private BigDecimal income;
    private BigDecimal quantity;
    private BigDecimal manhour;
    private BigDecimal manhourPrice;
    private String accountId;
    private String accountName;
    private Byte accountType;
    private String workGroupId;
    private String workGroupName;
    private String keeper;
    private Byte workStatus;
    private Timestamp sendTime;
    private Timestamp endTime;
    private String appraisalId;
    private String appraisalNo;
    private String remark;
    private Boolean sabFlag;
    private BigDecimal modifiedAgio;
    private String modifier;
    private Boolean packageFlag;
    private Byte claimsDetailStatus;
    private BigDecimal taxMoney;
    private BigDecimal noTaxMoney;
    private BigDecimal taxRate;
    private BigDecimal tax;
    private BigDecimal costTaxMoney;
    private BigDecimal costNoTaxMoney;
    private BigDecimal costTaxRate;
    private BigDecimal costTax;
    private BigDecimal priceSaleSelect;
    private BigDecimal priceCounterclaimSelect;
    private BigDecimal priceInsuranceSelect;
    private BigDecimal priceInnerSelect;
    private String referenceAppraisalerId;
    private String referenceAppraisalerNo;
    private String referenceAppraisaler;
    private Integer claimsSelectSeq;
    private String appraisalInfo;
    private String maintenanceType;

    @Id
    @Column(name = "list_consign_id")
    public String getListConsignId() {
        return listConsignId;
    }

    public void setListConsignId(String listConsignId) {
        this.listConsignId = listConsignId;
    }

    @Basic
    @Column(name = "task_no")
    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    @Basic
    @Column(name = "consign_id")
    public String getConsignId() {
        return consignId;
    }

    public void setConsignId(String consignId) {
        this.consignId = consignId;
    }

    @Basic
    @Column(name = "consign_name")
    public String getConsignName() {
        return consignName;
    }

    public void setConsignName(String consignName) {
        this.consignName = consignName;
    }

    @Basic
    @Column(name = "consign_no")
    public String getConsignNo() {
        return consignNo;
    }

    public void setConsignNo(String consignNo) {
        this.consignNo = consignNo;
    }

    @Basic
    @Column(name = "consign_category")
    public Byte getConsignCategory() {
        return consignCategory;
    }

    public void setConsignCategory(Byte consignCategory) {
        this.consignCategory = consignCategory;
    }

    @Basic
    @Column(name = "consign_type")
    public String getConsignType() {
        return consignType;
    }

    public void setConsignType(String consignType) {
        this.consignType = consignType;
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
    @Column(name = "supplier_no")
    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    @Basic
    @Column(name = "supplier_name")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Basic
    @Column(name = "agio")
    public BigDecimal getAgio() {
        return agio;
    }

    public void setAgio(BigDecimal agio) {
        this.agio = agio;
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
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
    @Column(name = "quantity")
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "manhour")
    public BigDecimal getManhour() {
        return manhour;
    }

    public void setManhour(BigDecimal manhour) {
        this.manhour = manhour;
    }

    @Basic
    @Column(name = "manhour_price")
    public BigDecimal getManhourPrice() {
        return manhourPrice;
    }

    public void setManhourPrice(BigDecimal manhourPrice) {
        this.manhourPrice = manhourPrice;
    }

    @Basic
    @Column(name = "account_id")
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "account_name")
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Basic
    @Column(name = "account_type")
    public Byte getAccountType() {
        return accountType;
    }

    public void setAccountType(Byte accountType) {
        this.accountType = accountType;
    }

    @Basic
    @Column(name = "work_group_id")
    public String getWorkGroupId() {
        return workGroupId;
    }

    public void setWorkGroupId(String workGroupId) {
        this.workGroupId = workGroupId;
    }

    @Basic
    @Column(name = "work_group_name")
    public String getWorkGroupName() {
        return workGroupName;
    }

    public void setWorkGroupName(String workGroupName) {
        this.workGroupName = workGroupName;
    }

    @Basic
    @Column(name = "keeper")
    public String getKeeper() {
        return keeper;
    }

    public void setKeeper(String keeper) {
        this.keeper = keeper;
    }

    @Basic
    @Column(name = "work_status")
    public Byte getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Byte workStatus) {
        this.workStatus = workStatus;
    }

    @Basic
    @Column(name = "send_time")
    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    @Basic
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "appraisal_id")
    public String getAppraisalId() {
        return appraisalId;
    }

    public void setAppraisalId(String appraisalId) {
        this.appraisalId = appraisalId;
    }

    @Basic
    @Column(name = "appraisal_no")
    public String getAppraisalNo() {
        return appraisalNo;
    }

    public void setAppraisalNo(String appraisalNo) {
        this.appraisalNo = appraisalNo;
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
    @Column(name = "sab_flag")
    public Boolean getSabFlag() {
        return sabFlag;
    }

    public void setSabFlag(Boolean sabFlag) {
        this.sabFlag = sabFlag;
    }

    @Basic
    @Column(name = "modified_agio")
    public BigDecimal getModifiedAgio() {
        return modifiedAgio;
    }

    public void setModifiedAgio(BigDecimal modifiedAgio) {
        this.modifiedAgio = modifiedAgio;
    }

    @Basic
    @Column(name = "modifier")
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Basic
    @Column(name = "package_flag")
    public Boolean getPackageFlag() {
        return packageFlag;
    }

    public void setPackageFlag(Boolean packageFlag) {
        this.packageFlag = packageFlag;
    }

    @Basic
    @Column(name = "claims_detail_status")
    public Byte getClaimsDetailStatus() {
        return claimsDetailStatus;
    }

    public void setClaimsDetailStatus(Byte claimsDetailStatus) {
        this.claimsDetailStatus = claimsDetailStatus;
    }

    @Basic
    @Column(name = "tax_money")
    public BigDecimal getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(BigDecimal taxMoney) {
        this.taxMoney = taxMoney;
    }

    @Basic
    @Column(name = "no_tax_money")
    public BigDecimal getNoTaxMoney() {
        return noTaxMoney;
    }

    public void setNoTaxMoney(BigDecimal noTaxMoney) {
        this.noTaxMoney = noTaxMoney;
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
    @Column(name = "tax")
    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    @Basic
    @Column(name = "cost_tax_money")
    public BigDecimal getCostTaxMoney() {
        return costTaxMoney;
    }

    public void setCostTaxMoney(BigDecimal costTaxMoney) {
        this.costTaxMoney = costTaxMoney;
    }

    @Basic
    @Column(name = "cost_no_tax_money")
    public BigDecimal getCostNoTaxMoney() {
        return costNoTaxMoney;
    }

    public void setCostNoTaxMoney(BigDecimal costNoTaxMoney) {
        this.costNoTaxMoney = costNoTaxMoney;
    }

    @Basic
    @Column(name = "cost_tax_rate")
    public BigDecimal getCostTaxRate() {
        return costTaxRate;
    }

    public void setCostTaxRate(BigDecimal costTaxRate) {
        this.costTaxRate = costTaxRate;
    }

    @Basic
    @Column(name = "cost_tax")
    public BigDecimal getCostTax() {
        return costTax;
    }

    public void setCostTax(BigDecimal costTax) {
        this.costTax = costTax;
    }

    @Basic
    @Column(name = "price_sale_select")
    public BigDecimal getPriceSaleSelect() {
        return priceSaleSelect;
    }

    public void setPriceSaleSelect(BigDecimal priceSaleSelect) {
        this.priceSaleSelect = priceSaleSelect;
    }

    @Basic
    @Column(name = "price_counterclaim_select")
    public BigDecimal getPriceCounterclaimSelect() {
        return priceCounterclaimSelect;
    }

    public void setPriceCounterclaimSelect(BigDecimal priceCounterclaimSelect) {
        this.priceCounterclaimSelect = priceCounterclaimSelect;
    }

    @Basic
    @Column(name = "price_insurance_select")
    public BigDecimal getPriceInsuranceSelect() {
        return priceInsuranceSelect;
    }

    public void setPriceInsuranceSelect(BigDecimal priceInsuranceSelect) {
        this.priceInsuranceSelect = priceInsuranceSelect;
    }

    @Basic
    @Column(name = "price_inner_select")
    public BigDecimal getPriceInnerSelect() {
        return priceInnerSelect;
    }

    public void setPriceInnerSelect(BigDecimal priceInnerSelect) {
        this.priceInnerSelect = priceInnerSelect;
    }

    @Basic
    @Column(name = "reference_appraisaler_id")
    public String getReferenceAppraisalerId() {
        return referenceAppraisalerId;
    }

    public void setReferenceAppraisalerId(String referenceAppraisalerId) {
        this.referenceAppraisalerId = referenceAppraisalerId;
    }

    @Basic
    @Column(name = "reference_appraisaler_no")
    public String getReferenceAppraisalerNo() {
        return referenceAppraisalerNo;
    }

    public void setReferenceAppraisalerNo(String referenceAppraisalerNo) {
        this.referenceAppraisalerNo = referenceAppraisalerNo;
    }

    @Basic
    @Column(name = "reference_appraisaler")
    public String getReferenceAppraisaler() {
        return referenceAppraisaler;
    }

    public void setReferenceAppraisaler(String referenceAppraisaler) {
        this.referenceAppraisaler = referenceAppraisaler;
    }

    @Basic
    @Column(name = "claims_select_seq")
    public Integer getClaimsSelectSeq() {
        return claimsSelectSeq;
    }

    public void setClaimsSelectSeq(Integer claimsSelectSeq) {
        this.claimsSelectSeq = claimsSelectSeq;
    }

    @Basic
    @Column(name = "appraisal_info")
    public String getAppraisalInfo() {
        return appraisalInfo;
    }

    public void setAppraisalInfo(String appraisalInfo) {
        this.appraisalInfo = appraisalInfo;
    }

    @Basic
    @Column(name = "maintenance_type")
    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }
}
