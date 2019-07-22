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
@Table(name = "service_work_list_items")
public class ServiceWorkListItems {
    private String listItemId;
    private String taskNo;
    private BigDecimal price;
    private BigDecimal income;
    private BigDecimal quantity;
    private BigDecimal agio;
    private String itemId;
    private String itemNo;
    private String itemName;
    private String itemType;
    private Byte itemCategory;
    private String priceStandard;
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
    private Integer addFrequency;
    private String sender;
    private String checker;
    private String conversionDetailId;
    private String remark;
    private String itemNature;
    private String responsiblePerson;
    private String priceStandardId;
    private String proposedId;
    private Boolean sabFlag;
    private BigDecimal modifiedAgio;
    private String modifier;
    private Boolean packageFlag;
    private Byte claimsDetailStatus;
    private Integer serviceProperty;
    private String appraisaler;
    private BigDecimal taxMoney;
    private BigDecimal noTaxMoney;
    private BigDecimal taxRate;
    private BigDecimal tax;
    private Boolean reworkIsConfirm;
    private BigDecimal priceSaleSelect;
    private BigDecimal priceCounterclaimSelect;
    private BigDecimal priceInsuranceSelect;
    private BigDecimal priceInnerSelect;
    private BigDecimal disQuantity;
    private String referenceAppraisalerId;
    private String referenceAppraisalerNo;
    private String referenceAppraisaler;
    private BigDecimal incomeProportion;
    private Integer claimsSelectSeq;
    private String appraisalInfo;
    private String maintenanceType;

    @Id
    @Column(name = "list_item_id")
    public String getListItemId() {
        return listItemId;
    }

    public void setListItemId(String listItemId) {
        this.listItemId = listItemId;
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
    @Column(name = "agio")
    public BigDecimal getAgio() {
        return agio;
    }

    public void setAgio(BigDecimal agio) {
        this.agio = agio;
    }

    @Basic
    @Column(name = "item_id")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "item_no")
    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    @Basic
    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "item_type")
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Basic
    @Column(name = "item_category")
    public Byte getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(Byte itemCategory) {
        this.itemCategory = itemCategory;
    }

    @Basic
    @Column(name = "price_standard")
    public String getPriceStandard() {
        return priceStandard;
    }

    public void setPriceStandard(String priceStandard) {
        this.priceStandard = priceStandard;
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
    @Column(name = "add_frequency")
    public Integer getAddFrequency() {
        return addFrequency;
    }

    public void setAddFrequency(Integer addFrequency) {
        this.addFrequency = addFrequency;
    }

    @Basic
    @Column(name = "sender")
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Basic
    @Column(name = "checker")
    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    @Basic
    @Column(name = "conversion_detail_id")
    public String getConversionDetailId() {
        return conversionDetailId;
    }

    public void setConversionDetailId(String conversionDetailId) {
        this.conversionDetailId = conversionDetailId;
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
    @Column(name = "item_nature")
    public String getItemNature() {
        return itemNature;
    }

    public void setItemNature(String itemNature) {
        this.itemNature = itemNature;
    }

    @Basic
    @Column(name = "responsible_person")
    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    @Basic
    @Column(name = "price_standard_id")
    public String getPriceStandardId() {
        return priceStandardId;
    }

    public void setPriceStandardId(String priceStandardId) {
        this.priceStandardId = priceStandardId;
    }

    @Basic
    @Column(name = "proposed_id")
    public String getProposedId() {
        return proposedId;
    }

    public void setProposedId(String proposedId) {
        this.proposedId = proposedId;
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
    @Column(name = "service_property")
    public Integer getServiceProperty() {
        return serviceProperty;
    }

    public void setServiceProperty(Integer serviceProperty) {
        this.serviceProperty = serviceProperty;
    }

    @Basic
    @Column(name = "appraisaler")
    public String getAppraisaler() {
        return appraisaler;
    }

    public void setAppraisaler(String appraisaler) {
        this.appraisaler = appraisaler;
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
    @Column(name = "rework_is_confirm")
    public Boolean getReworkIsConfirm() {
        return reworkIsConfirm;
    }

    public void setReworkIsConfirm(Boolean reworkIsConfirm) {
        this.reworkIsConfirm = reworkIsConfirm;
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
    @Column(name = "dis_quantity")
    public BigDecimal getDisQuantity() {
        return disQuantity;
    }

    public void setDisQuantity(BigDecimal disQuantity) {
        this.disQuantity = disQuantity;
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
    @Column(name = "income_proportion")
    public BigDecimal getIncomeProportion() {
        return incomeProportion;
    }

    public void setIncomeProportion(BigDecimal incomeProportion) {
        this.incomeProportion = incomeProportion;
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
