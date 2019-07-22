package cn.sf_soft.office.approval.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Auther: chenbiao
 * @Date: 2019/7/15 16:20
 * @Description:
 */
@Entity
@Table(name = "service_work_lists_charge")
public class ServiceWorkListsCharge {
    private String selfId;
    private String taskNo;
    private String chargeId;
    private String chargeName;
    private BigDecimal chargeIncome;
    private BigDecimal chargePf;
    private BigDecimal chargeCost;
    private Boolean chargeInTask;
    private String accountId;
    private String accountName;
    private Byte accountType;
    private String supplierId;
    private String supplierNo;
    private String supplierName;
    private String chargeComment;
    private String appraisalId;
    private Boolean sabFlag;
    private Byte claimsDetailStatus;
    private String remark;
    private BigDecimal taxMoney;
    private BigDecimal noTaxMoney;
    private BigDecimal taxRate;
    private BigDecimal tax;
    private BigDecimal costTaxMoney;
    private BigDecimal costNoTaxMoney;
    private BigDecimal costTaxRate;
    private BigDecimal costTax;
    private String referenceAppraisalerId;
    private String referenceAppraisalerNo;
    private String referenceAppraisaler;
    private String workGroupId;
    private String workGroupName;
    private Integer claimsSelectSeq;
    private String appraisalInfo;
    private String maintenanceType;
    private BigDecimal sosMileage;
    private String expenseItem;
    private String sosSite;

    @Id
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
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
    @Column(name = "charge_income")
    public BigDecimal getChargeIncome() {
        return chargeIncome;
    }

    public void setChargeIncome(BigDecimal chargeIncome) {
        this.chargeIncome = chargeIncome;
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
    @Column(name = "charge_cost")
    public BigDecimal getChargeCost() {
        return chargeCost;
    }

    public void setChargeCost(BigDecimal chargeCost) {
        this.chargeCost = chargeCost;
    }

    @Basic
    @Column(name = "charge_in_task")
    public Boolean getChargeInTask() {
        return chargeInTask;
    }

    public void setChargeInTask(Boolean chargeInTask) {
        this.chargeInTask = chargeInTask;
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
    @Column(name = "charge_comment")
    public String getChargeComment() {
        return chargeComment;
    }

    public void setChargeComment(String chargeComment) {
        this.chargeComment = chargeComment;
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
    @Column(name = "sab_flag")
    public Boolean getSabFlag() {
        return sabFlag;
    }

    public void setSabFlag(Boolean sabFlag) {
        this.sabFlag = sabFlag;
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
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Basic
    @Column(name = "sos_mileage")
    public BigDecimal getSosMileage() {
        return sosMileage;
    }

    public void setSosMileage(BigDecimal sosMileage) {
        this.sosMileage = sosMileage;
    }

    @Basic
    @Column(name = "expense_item")
    public String getExpenseItem() {
        return expenseItem;
    }

    public void setExpenseItem(String expenseItem) {
        this.expenseItem = expenseItem;
    }

    @Basic
    @Column(name = "sos_site")
    public String getSosSite() {
        return sosSite;
    }

    public void setSosSite(String sosSite) {
        this.sosSite = sosSite;
    }
}
