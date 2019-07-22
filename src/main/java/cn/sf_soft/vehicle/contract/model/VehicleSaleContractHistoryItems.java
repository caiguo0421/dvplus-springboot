package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/6 11:10
 * @Description:
 */
@Entity
@Table(name = "vehicle_sale_contract_history_items", schema = "dbo")
public class VehicleSaleContractHistoryItems {
    private String selfId;
    private String historyId;
    private String historyGroupId;
    private String groupId;
    private String groupNo;
    private String contractNo;
    private String itemType;
    private String itemId;
    private String itemName;
    private String varyType;
    private String quantity;
    private Integer quantityOri;
    private Integer quantityNew;
    private String income;
    private BigDecimal incomeOri;
    private BigDecimal incomeNew;
    private String cost;
    private BigDecimal costOri;
    private BigDecimal costNew;
    private String profit;
    private BigDecimal profitOri;
    private BigDecimal profitNew;
    private String creator;
    private String createTime;
    private Timestamp modifyTime;
    private String remark;
    private String invoiceTypeOri;
    private String invoiceTypeNew;
    private String invoiceType;
    private BigDecimal invoiceAmountOri;
    private BigDecimal invoiceAmountNew;
    private String invoiceAmount;
    private String invoiceObjectOri;
    private String invoiceObjectNew;
    private String invoiceObject;
    private String giftNameOri;
    private String giftNameNew;
    private String giftName;
    private String giftTypeOri;
    private String giftTypeNew;
    private String giftType;
    private BigDecimal giftAmountOri;
    private BigDecimal giftAmountNew;
    private String giftAmount;
    private String giftGiveFlagOri;
    private String giftGiveFlagNew;
    private String giftGiveFlag;

    @Id
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Basic
    @Column(name = "history_id")
    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    @Basic
    @Column(name = "history_group_id")
    public String getHistoryGroupId() {
        return historyGroupId;
    }

    public void setHistoryGroupId(String historyGroupId) {
        this.historyGroupId = historyGroupId;
    }

    @Basic
    @Column(name = "group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "group_no")
    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    @Basic
    @Column(name = "contract_no")
    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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
    @Column(name = "item_id")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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
    @Column(name = "vary_type")
    public String getVaryType() {
        return varyType;
    }

    public void setVaryType(String varyType) {
        this.varyType = varyType;
    }

    @Basic
    @Column(name = "quantity")
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "quantity_ori")
    public Integer getQuantityOri() {
        return quantityOri;
    }

    public void setQuantityOri(Integer quantityOri) {
        this.quantityOri = quantityOri;
    }

    @Basic
    @Column(name = "quantity_new")
    public Integer getQuantityNew() {
        return quantityNew;
    }

    public void setQuantityNew(Integer quantityNew) {
        this.quantityNew = quantityNew;
    }

    @Basic
    @Column(name = "income")
    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    @Basic
    @Column(name = "income_ori")
    public BigDecimal getIncomeOri() {
        return incomeOri;
    }

    public void setIncomeOri(BigDecimal incomeOri) {
        this.incomeOri = incomeOri;
    }

    @Basic
    @Column(name = "income_new")
    public BigDecimal getIncomeNew() {
        return incomeNew;
    }

    public void setIncomeNew(BigDecimal incomeNew) {
        this.incomeNew = incomeNew;
    }

    @Basic
    @Column(name = "cost")
    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "cost_ori")
    public BigDecimal getCostOri() {
        return costOri;
    }

    public void setCostOri(BigDecimal costOri) {
        this.costOri = costOri;
    }

    @Basic
    @Column(name = "cost_new")
    public BigDecimal getCostNew() {
        return costNew;
    }

    public void setCostNew(BigDecimal costNew) {
        this.costNew = costNew;
    }

    @Basic
    @Column(name = "profit")
    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    @Basic
    @Column(name = "profit_ori")
    public BigDecimal getProfitOri() {
        return profitOri;
    }

    public void setProfitOri(BigDecimal profitOri) {
        this.profitOri = profitOri;
    }

    @Basic
    @Column(name = "profit_new")
    public BigDecimal getProfitNew() {
        return profitNew;
    }

    public void setProfitNew(BigDecimal profitNew) {
        this.profitNew = profitNew;
    }

    @Basic
    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modify_time")
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
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
    @Column(name = "invoice_type_ori")
    public String getInvoiceTypeOri() {
        return invoiceTypeOri;
    }

    public void setInvoiceTypeOri(String invoiceTypeOri) {
        this.invoiceTypeOri = invoiceTypeOri;
    }

    @Basic
    @Column(name = "invoice_type_new")
    public String getInvoiceTypeNew() {
        return invoiceTypeNew;
    }

    public void setInvoiceTypeNew(String invoiceTypeNew) {
        this.invoiceTypeNew = invoiceTypeNew;
    }

    @Basic
    @Column(name = "invoice_type")
    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    @Basic
    @Column(name = "invoice_amount_ori")
    public BigDecimal getInvoiceAmountOri() {
        return invoiceAmountOri;
    }

    public void setInvoiceAmountOri(BigDecimal invoiceAmountOri) {
        this.invoiceAmountOri = invoiceAmountOri;
    }

    @Basic
    @Column(name = "invoice_amount_new")
    public BigDecimal getInvoiceAmountNew() {
        return invoiceAmountNew;
    }

    public void setInvoiceAmountNew(BigDecimal invoiceAmountNew) {
        this.invoiceAmountNew = invoiceAmountNew;
    }

    @Basic
    @Column(name = "invoice_amount")
    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    @Basic
    @Column(name = "invoice_object_ori")
    public String getInvoiceObjectOri() {
        return invoiceObjectOri;
    }

    public void setInvoiceObjectOri(String invoiceObjectOri) {
        this.invoiceObjectOri = invoiceObjectOri;
    }

    @Basic
    @Column(name = "invoice_object_new")
    public String getInvoiceObjectNew() {
        return invoiceObjectNew;
    }

    public void setInvoiceObjectNew(String invoiceObjectNew) {
        this.invoiceObjectNew = invoiceObjectNew;
    }

    @Basic
    @Column(name = "invoice_object")
    public String getInvoiceObject() {
        return invoiceObject;
    }

    public void setInvoiceObject(String invoiceObject) {
        this.invoiceObject = invoiceObject;
    }

    @Basic
    @Column(name = "gift_name_ori")
    public String getGiftNameOri() {
        return giftNameOri;
    }

    public void setGiftNameOri(String giftNameOri) {
        this.giftNameOri = giftNameOri;
    }

    @Basic
    @Column(name = "gift_name_new")
    public String getGiftNameNew() {
        return giftNameNew;
    }

    public void setGiftNameNew(String giftNameNew) {
        this.giftNameNew = giftNameNew;
    }

    @Basic
    @Column(name = "gift_name")
    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    @Basic
    @Column(name = "gift_type_ori")
    public String getGiftTypeOri() {
        return giftTypeOri;
    }

    public void setGiftTypeOri(String giftTypeOri) {
        this.giftTypeOri = giftTypeOri;
    }

    @Basic
    @Column(name = "gift_type_new")
    public String getGiftTypeNew() {
        return giftTypeNew;
    }

    public void setGiftTypeNew(String giftTypeNew) {
        this.giftTypeNew = giftTypeNew;
    }

    @Basic
    @Column(name = "gift_type")
    public String getGiftType() {
        return giftType;
    }

    public void setGiftType(String giftType) {
        this.giftType = giftType;
    }

    @Basic
    @Column(name = "gift_amount_ori")
    public BigDecimal getGiftAmountOri() {
        return giftAmountOri;
    }

    public void setGiftAmountOri(BigDecimal giftAmountOri) {
        this.giftAmountOri = giftAmountOri;
    }

    @Basic
    @Column(name = "gift_amount_new")
    public BigDecimal getGiftAmountNew() {
        return giftAmountNew;
    }

    public void setGiftAmountNew(BigDecimal giftAmountNew) {
        this.giftAmountNew = giftAmountNew;
    }

    @Basic
    @Column(name = "gift_amount")
    public String getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(String giftAmount) {
        this.giftAmount = giftAmount;
    }

    @Basic
    @Column(name = "gift_give_flag_ori")
    public String getGiftGiveFlagOri() {
        return giftGiveFlagOri;
    }

    public void setGiftGiveFlagOri(String giftGiveFlagOri) {
        this.giftGiveFlagOri = giftGiveFlagOri;
    }

    @Basic
    @Column(name = "gift_give_flag_new")
    public String getGiftGiveFlagNew() {
        return giftGiveFlagNew;
    }

    public void setGiftGiveFlagNew(String giftGiveFlagNew) {
        this.giftGiveFlagNew = giftGiveFlagNew;
    }

    @Basic
    @Column(name = "gift_give_flag")
    public String getGiftGiveFlag() {
        return giftGiveFlag;
    }

    public void setGiftGiveFlag(String giftGiveFlag) {
        this.giftGiveFlag = giftGiveFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleSaleContractHistoryItems that = (VehicleSaleContractHistoryItems) o;
        return Objects.equals(selfId, that.selfId) &&
                Objects.equals(historyId, that.historyId) &&
                Objects.equals(historyGroupId, that.historyGroupId) &&
                Objects.equals(groupId, that.groupId) &&
                Objects.equals(groupNo, that.groupNo) &&
                Objects.equals(contractNo, that.contractNo) &&
                Objects.equals(itemType, that.itemType) &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(varyType, that.varyType) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(quantityOri, that.quantityOri) &&
                Objects.equals(quantityNew, that.quantityNew) &&
                Objects.equals(income, that.income) &&
                Objects.equals(incomeOri, that.incomeOri) &&
                Objects.equals(incomeNew, that.incomeNew) &&
                Objects.equals(cost, that.cost) &&
                Objects.equals(costOri, that.costOri) &&
                Objects.equals(costNew, that.costNew) &&
                Objects.equals(profit, that.profit) &&
                Objects.equals(profitOri, that.profitOri) &&
                Objects.equals(profitNew, that.profitNew) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(modifyTime, that.modifyTime) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(invoiceTypeOri, that.invoiceTypeOri) &&
                Objects.equals(invoiceTypeNew, that.invoiceTypeNew) &&
                Objects.equals(invoiceType, that.invoiceType) &&
                Objects.equals(invoiceAmountOri, that.invoiceAmountOri) &&
                Objects.equals(invoiceAmountNew, that.invoiceAmountNew) &&
                Objects.equals(invoiceAmount, that.invoiceAmount) &&
                Objects.equals(invoiceObjectOri, that.invoiceObjectOri) &&
                Objects.equals(invoiceObjectNew, that.invoiceObjectNew) &&
                Objects.equals(invoiceObject, that.invoiceObject) &&
                Objects.equals(giftNameOri, that.giftNameOri) &&
                Objects.equals(giftNameNew, that.giftNameNew) &&
                Objects.equals(giftName, that.giftName) &&
                Objects.equals(giftTypeOri, that.giftTypeOri) &&
                Objects.equals(giftTypeNew, that.giftTypeNew) &&
                Objects.equals(giftType, that.giftType) &&
                Objects.equals(giftAmountOri, that.giftAmountOri) &&
                Objects.equals(giftAmountNew, that.giftAmountNew) &&
                Objects.equals(giftAmount, that.giftAmount) &&
                Objects.equals(giftGiveFlagOri, that.giftGiveFlagOri) &&
                Objects.equals(giftGiveFlagNew, that.giftGiveFlagNew) &&
                Objects.equals(giftGiveFlag, that.giftGiveFlag);
    }

    @Override
    public int hashCode() {

        return Objects.hash(selfId, historyId, historyGroupId, groupId, groupNo, contractNo, itemType, itemId, itemName, varyType, quantity, quantityOri, quantityNew, income, incomeOri, incomeNew, cost, costOri, costNew, profit, profitOri, profitNew, creator, createTime, modifyTime, remark, invoiceTypeOri, invoiceTypeNew, invoiceType, invoiceAmountOri, invoiceAmountNew, invoiceAmount, invoiceObjectOri, invoiceObjectNew, invoiceObject, giftNameOri, giftNameNew, giftName, giftTypeOri, giftTypeNew, giftType, giftAmountOri, giftAmountNew, giftAmount, giftGiveFlagOri, giftGiveFlagNew, giftGiveFlag);
    }
}
