package cn.sf_soft.office.approval.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Auther: chenbiao
 * @Date: 2019/7/15 16:20
 * @Description:
 */
@Entity
@Table(name = "service_work_list_proposed_items")
public class ServiceWorkListProposedItems {
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
    private Integer itemStatus;
    private String lastId;

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
    @Column(name = "item_status")
    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
    }

    @Basic
    @Column(name = "last_id")
    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }
}
