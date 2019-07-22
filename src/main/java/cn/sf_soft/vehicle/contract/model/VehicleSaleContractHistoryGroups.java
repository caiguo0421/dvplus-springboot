package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/6 11:10
 * @Description:
 */
@Entity
@Table(name = "vehicle_sale_contract_history_groups", schema = "dbo")
public class VehicleSaleContractHistoryGroups {
    private String historyGroupId;
    private String historyId;
    private String groupId;
    private String groupNo;
    private String contractNo;
    private String varyType;
    private String vehicleVno;
    private String vehicleVnoOri;
    private String vehicleVnoNew;
    private String vehicleColor;
    private String vehicleColorOri;
    private String vehicleColorNew;
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
    private String belongToSupplierName;
    private String belongToSupplierNameOri;
    private String belongToSupplierNameNew;
    private String vehicleOwnerName;
    private String vehicleOwnerNameOri;
    private String vehicleOwnerNameNew;
    private String vehiclePrice;
    private BigDecimal vehiclePriceOri;
    private BigDecimal vehiclePriceNew;
    private String vehiclePriceTotal;
    private BigDecimal vehiclePriceTotalOri;
    private BigDecimal vehiclePriceTotalNew;
    private String isContainInsuranceCost;
    private Boolean isContainInsuranceCostOri;
    private Boolean isContainInsuranceCostNew;
    private String discountAmount;
    private BigDecimal discountAmountOri;
    private BigDecimal discountAmountNew;
    private String largessAmount;
    private BigDecimal largessAmountOri;
    private BigDecimal largessAmountNew;
    private String customerNameDetail;
    private String customerNameDetailOri;
    private String customerNameDetailNew;
    private String vehicleProfit;
    private BigDecimal vehicleProfitOri;
    private BigDecimal vehicleProfitNew;
    private String deposit;
    private BigDecimal depositOri;
    private BigDecimal depositNew;
    private String subjectMatter;
    private String subjectMatterOri;
    private String subjectMatterNew;
    private String vehicleCardModel;
    private String vehicleCardModelOri;
    private String vehicleCardModelNew;
    private String transportRoutes;
    private String transportRoutesOri;
    private String transportRoutesNew;
    private String vins;
    private String vinsOri;
    private String vinsNew;
    private String creator;
    private String createTime;
    private Timestamp modifyTime;
    private String remark;

    private List<VehicleSaleContractHistoryItems> items;

    @Id
    @Column(name = "history_group_id")
    public String getHistoryGroupId() {
        return historyGroupId;
    }

    public void setHistoryGroupId(String historyGroupId) {
        this.historyGroupId = historyGroupId;
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
    @Column(name = "vary_type")
    public String getVaryType() {
        return varyType;
    }

    public void setVaryType(String varyType) {
        this.varyType = varyType;
    }

    @Basic
    @Column(name = "vehicle_vno")
    public String getVehicleVno() {
        return vehicleVno;
    }

    public void setVehicleVno(String vehicleVno) {
        this.vehicleVno = vehicleVno;
    }

    @Basic
    @Column(name = "vehicle_vno_ori")
    public String getVehicleVnoOri() {
        return vehicleVnoOri;
    }

    public void setVehicleVnoOri(String vehicleVnoOri) {
        this.vehicleVnoOri = vehicleVnoOri;
    }

    @Basic
    @Column(name = "vehicle_vno_new")
    public String getVehicleVnoNew() {
        return vehicleVnoNew;
    }

    public void setVehicleVnoNew(String vehicleVnoNew) {
        this.vehicleVnoNew = vehicleVnoNew;
    }

    @Basic
    @Column(name = "vehicle_color")
    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    @Basic
    @Column(name = "vehicle_color_ori")
    public String getVehicleColorOri() {
        return vehicleColorOri;
    }

    public void setVehicleColorOri(String vehicleColorOri) {
        this.vehicleColorOri = vehicleColorOri;
    }

    @Basic
    @Column(name = "vehicle_color_new")
    public String getVehicleColorNew() {
        return vehicleColorNew;
    }

    public void setVehicleColorNew(String vehicleColorNew) {
        this.vehicleColorNew = vehicleColorNew;
    }

    @Basic
    @Column(name = "vehicle_quantity")
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
    @Column(name = "belong_to_supplier_name")
    public String getBelongToSupplierName() {
        return belongToSupplierName;
    }

    public void setBelongToSupplierName(String belongToSupplierName) {
        this.belongToSupplierName = belongToSupplierName;
    }

    @Basic
    @Column(name = "belong_to_supplier_name_ori")
    public String getBelongToSupplierNameOri() {
        return belongToSupplierNameOri;
    }

    public void setBelongToSupplierNameOri(String belongToSupplierNameOri) {
        this.belongToSupplierNameOri = belongToSupplierNameOri;
    }

    @Basic
    @Column(name = "belong_to_supplier_name_new")
    public String getBelongToSupplierNameNew() {
        return belongToSupplierNameNew;
    }

    public void setBelongToSupplierNameNew(String belongToSupplierNameNew) {
        this.belongToSupplierNameNew = belongToSupplierNameNew;
    }

    @Basic
    @Column(name = "vehicle_owner_name")
    public String getVehicleOwnerName() {
        return vehicleOwnerName;
    }

    public void setVehicleOwnerName(String vehicleOwnerName) {
        this.vehicleOwnerName = vehicleOwnerName;
    }

    @Basic
    @Column(name = "vehicle_owner_name_ori")
    public String getVehicleOwnerNameOri() {
        return vehicleOwnerNameOri;
    }

    public void setVehicleOwnerNameOri(String vehicleOwnerNameOri) {
        this.vehicleOwnerNameOri = vehicleOwnerNameOri;
    }

    @Basic
    @Column(name = "vehicle_owner_name_new")
    public String getVehicleOwnerNameNew() {
        return vehicleOwnerNameNew;
    }

    public void setVehicleOwnerNameNew(String vehicleOwnerNameNew) {
        this.vehicleOwnerNameNew = vehicleOwnerNameNew;
    }

    @Basic
    @Column(name = "vehicle_price")
    public String getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(String vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    @Basic
    @Column(name = "vehicle_price_ori")
    public BigDecimal getVehiclePriceOri() {
        return vehiclePriceOri;
    }

    public void setVehiclePriceOri(BigDecimal vehiclePriceOri) {
        this.vehiclePriceOri = vehiclePriceOri;
    }

    @Basic
    @Column(name = "vehicle_price_new")
    public BigDecimal getVehiclePriceNew() {
        return vehiclePriceNew;
    }

    public void setVehiclePriceNew(BigDecimal vehiclePriceNew) {
        this.vehiclePriceNew = vehiclePriceNew;
    }

    @Basic
    @Column(name = "vehicle_price_total")
    public String getVehiclePriceTotal() {
        return vehiclePriceTotal;
    }

    public void setVehiclePriceTotal(String vehiclePriceTotal) {
        this.vehiclePriceTotal = vehiclePriceTotal;
    }

    @Basic
    @Column(name = "vehicle_price_total_ori")
    public BigDecimal getVehiclePriceTotalOri() {
        return vehiclePriceTotalOri;
    }

    public void setVehiclePriceTotalOri(BigDecimal vehiclePriceTotalOri) {
        this.vehiclePriceTotalOri = vehiclePriceTotalOri;
    }

    @Basic
    @Column(name = "vehicle_price_total_new")
    public BigDecimal getVehiclePriceTotalNew() {
        return vehiclePriceTotalNew;
    }

    public void setVehiclePriceTotalNew(BigDecimal vehiclePriceTotalNew) {
        this.vehiclePriceTotalNew = vehiclePriceTotalNew;
    }

    @Basic
    @Column(name = "is_contain_insurance_cost")
    public String getIsContainInsuranceCost() {
        return isContainInsuranceCost;
    }

    public void setIsContainInsuranceCost(String isContainInsuranceCost) {
        this.isContainInsuranceCost = isContainInsuranceCost;
    }

    @Basic
    @Column(name = "is_contain_insurance_cost_ori")
    public Boolean getIsContainInsuranceCostOri() {
        return isContainInsuranceCostOri;
    }

    public void setIsContainInsuranceCostOri(Boolean containInsuranceCostOri) {
        isContainInsuranceCostOri = containInsuranceCostOri;
    }

    @Basic
    @Column(name = "is_contain_insurance_cost_new")
    public Boolean getIsContainInsuranceCostNew() {
        return isContainInsuranceCostNew;
    }

    public void setIsContainInsuranceCostNew(Boolean containInsuranceCostNew) {
        isContainInsuranceCostNew = containInsuranceCostNew;
    }

    @Basic
    @Column(name = "discount_amount")
    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Basic
    @Column(name = "discount_amount_ori")
    public BigDecimal getDiscountAmountOri() {
        return discountAmountOri;
    }

    public void setDiscountAmountOri(BigDecimal discountAmountOri) {
        this.discountAmountOri = discountAmountOri;
    }

    @Basic
    @Column(name = "discount_amount_new")
    public BigDecimal getDiscountAmountNew() {
        return discountAmountNew;
    }

    public void setDiscountAmountNew(BigDecimal discountAmountNew) {
        this.discountAmountNew = discountAmountNew;
    }

    @Basic
    @Column(name = "largess_amount")
    public String getLargessAmount() {
        return largessAmount;
    }

    public void setLargessAmount(String largessAmount) {
        this.largessAmount = largessAmount;
    }

    @Basic
    @Column(name = "largess_amount_ori")
    public BigDecimal getLargessAmountOri() {
        return largessAmountOri;
    }

    public void setLargessAmountOri(BigDecimal largessAmountOri) {
        this.largessAmountOri = largessAmountOri;
    }

    @Basic
    @Column(name = "largess_amount_new")
    public BigDecimal getLargessAmountNew() {
        return largessAmountNew;
    }

    public void setLargessAmountNew(BigDecimal largessAmountNew) {
        this.largessAmountNew = largessAmountNew;
    }

    @Basic
    @Column(name = "customer_name_detail")
    public String getCustomerNameDetail() {
        return customerNameDetail;
    }

    public void setCustomerNameDetail(String customerNameDetail) {
        this.customerNameDetail = customerNameDetail;
    }

    @Basic
    @Column(name = "customer_name_detail_ori")
    public String getCustomerNameDetailOri() {
        return customerNameDetailOri;
    }

    public void setCustomerNameDetailOri(String customerNameDetailOri) {
        this.customerNameDetailOri = customerNameDetailOri;
    }

    @Basic
    @Column(name = "customer_name_detail_new")
    public String getCustomerNameDetailNew() {
        return customerNameDetailNew;
    }

    public void setCustomerNameDetailNew(String customerNameDetailNew) {
        this.customerNameDetailNew = customerNameDetailNew;
    }

    @Basic
    @Column(name = "vehicle_profit")
    public String getVehicleProfit() {
        return vehicleProfit;
    }

    public void setVehicleProfit(String vehicleProfit) {
        this.vehicleProfit = vehicleProfit;
    }

    @Basic
    @Column(name = "vehicle_profit_ori")
    public BigDecimal getVehicleProfitOri() {
        return vehicleProfitOri;
    }

    public void setVehicleProfitOri(BigDecimal vehicleProfitOri) {
        this.vehicleProfitOri = vehicleProfitOri;
    }

    @Basic
    @Column(name = "vehicle_profit_new")
    public BigDecimal getVehicleProfitNew() {
        return vehicleProfitNew;
    }

    public void setVehicleProfitNew(BigDecimal vehicleProfitNew) {
        this.vehicleProfitNew = vehicleProfitNew;
    }

    @Basic
    @Column(name = "deposit")
    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    @Basic
    @Column(name = "deposit_ori")
    public BigDecimal getDepositOri() {
        return depositOri;
    }

    public void setDepositOri(BigDecimal depositOri) {
        this.depositOri = depositOri;
    }

    @Basic
    @Column(name = "deposit_new")
    public BigDecimal getDepositNew() {
        return depositNew;
    }

    public void setDepositNew(BigDecimal depositNew) {
        this.depositNew = depositNew;
    }

    @Basic
    @Column(name = "subject_matter")
    public String getSubjectMatter() {
        return subjectMatter;
    }

    public void setSubjectMatter(String subjectMatter) {
        this.subjectMatter = subjectMatter;
    }

    @Basic
    @Column(name = "subject_matter_ori")
    public String getSubjectMatterOri() {
        return subjectMatterOri;
    }

    public void setSubjectMatterOri(String subjectMatterOri) {
        this.subjectMatterOri = subjectMatterOri;
    }

    @Basic
    @Column(name = "subject_matter_new")
    public String getSubjectMatterNew() {
        return subjectMatterNew;
    }

    public void setSubjectMatterNew(String subjectMatterNew) {
        this.subjectMatterNew = subjectMatterNew;
    }

    @Basic
    @Column(name = "vehicle_card_model")
    public String getVehicleCardModel() {
        return vehicleCardModel;
    }

    public void setVehicleCardModel(String vehicleCardModel) {
        this.vehicleCardModel = vehicleCardModel;
    }

    @Basic
    @Column(name = "vehicle_card_model_ori")
    public String getVehicleCardModelOri() {
        return vehicleCardModelOri;
    }

    public void setVehicleCardModelOri(String vehicleCardModelOri) {
        this.vehicleCardModelOri = vehicleCardModelOri;
    }

    @Basic
    @Column(name = "vehicle_card_model_new")
    public String getVehicleCardModelNew() {
        return vehicleCardModelNew;
    }

    public void setVehicleCardModelNew(String vehicleCardModelNew) {
        this.vehicleCardModelNew = vehicleCardModelNew;
    }

    @Basic
    @Column(name = "transport_routes")
    public String getTransportRoutes() {
        return transportRoutes;
    }

    public void setTransportRoutes(String transportRoutes) {
        this.transportRoutes = transportRoutes;
    }

    @Basic
    @Column(name = "transport_routes_ori")
    public String getTransportRoutesOri() {
        return transportRoutesOri;
    }

    public void setTransportRoutesOri(String transportRoutesOri) {
        this.transportRoutesOri = transportRoutesOri;
    }

    @Basic
    @Column(name = "transport_routes_new")
    public String getTransportRoutesNew() {
        return transportRoutesNew;
    }

    public void setTransportRoutesNew(String transportRoutesNew) {
        this.transportRoutesNew = transportRoutesNew;
    }

    @Basic
    @Column(name = "vins")
    public String getVins() {
        return vins;
    }

    public void setVins(String vins) {
        this.vins = vins;
    }

    @Basic
    @Column(name = "vins_ori")
    public String getVinsOri() {
        return vinsOri;
    }

    public void setVinsOri(String vinsOri) {
        this.vinsOri = vinsOri;
    }

    @Basic
    @Column(name = "vins_new")
    public String getVinsNew() {
        return vinsNew;
    }

    public void setVinsNew(String vinsNew) {
        this.vinsNew = vinsNew;
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

    @Transient
    public List<VehicleSaleContractHistoryItems> getItems() {
        return items;
    }

    public void setItems(List<VehicleSaleContractHistoryItems> items) {
        this.items = items;
    }
}
