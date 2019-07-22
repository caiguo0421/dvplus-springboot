package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by henry on 2018/5/2.
 */
@Entity
@Table(name = "vehicle_sale_contract_present_groups", schema = "dbo")
public class VehicleSaleContractPresentGroups {
    private String presentGroupId;
    private String groupId;
    private BigDecimal income = BigDecimal.ZERO;
    private BigDecimal planQuantity = BigDecimal.ZERO;
    private BigDecimal posPrice = BigDecimal.ZERO;
    private BigDecimal posAgio = BigDecimal.ZERO;
    private String stockId;
    private String depositPosition;
    private String partName;
    private String partNo;
    private String partMnemonic;
    private String producingArea;
    private String partType;
    private String specModel;
    private String applicableModel;
    private String unit;
    private String warehouseName;
    private BigDecimal costRecord = BigDecimal.ZERO;
    private BigDecimal carriageRecord = BigDecimal.ZERO;
    private String remark;
    private String contractNo;
    private BigDecimal costPf = BigDecimal.ZERO;  //计算字段

    public BigDecimal getCostPf() {
        return costPf;
    }

    public void setCostPf() {
        BigDecimal categoryIncome = null == this.getPosPrice() ? BigDecimal.ZERO : this.getPosPrice();
        BigDecimal categoryScale = null == this.getPlanQuantity() ? BigDecimal.ZERO : this.getPlanQuantity();
        this.costPf = categoryIncome.multiply(categoryScale);
    }
    @Id
    @Column(name = "present_group_id")
    public String getPresentGroupId() {
        return presentGroupId;
    }

    public void setPresentGroupId(String presentGroupId) {
        this.presentGroupId = presentGroupId;
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
    @Column(name = "income")
    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    @Basic
    @Column(name = "plan_quantity")
    public BigDecimal getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(BigDecimal planQuantity) {
        this.planQuantity = planQuantity;
        setCostPf();
    }

    @Basic
    @Column(name = "pos_price")
    public BigDecimal getPosPrice() {
        return posPrice;
    }

    public void setPosPrice(BigDecimal posPrice) {
        this.posPrice = posPrice;
        setCostPf();
    }

    @Basic
    @Column(name = "pos_agio")
    public BigDecimal getPosAgio() {
        return posAgio;
    }

    public void setPosAgio(BigDecimal posAgio) {
        this.posAgio = posAgio;
    }

    @Basic
    @Column(name = "stock_id")
    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    @Basic
    @Column(name = "deposit_position")
    public String getDepositPosition() {
        return depositPosition;
    }

    public void setDepositPosition(String depositPosition) {
        this.depositPosition = depositPosition;
    }

    @Basic
    @Column(name = "part_name")
    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    @Basic
    @Column(name = "part_no")
    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    @Basic
    @Column(name = "part_mnemonic")
    public String getPartMnemonic() {
        return partMnemonic;
    }

    public void setPartMnemonic(String partMnemonic) {
        this.partMnemonic = partMnemonic;
    }

    @Basic
    @Column(name = "producing_area")
    public String getProducingArea() {
        return producingArea;
    }

    public void setProducingArea(String producingArea) {
        this.producingArea = producingArea;
    }

    @Basic
    @Column(name = "part_type")
    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }

    @Basic
    @Column(name = "spec_model")
    public String getSpecModel() {
        return specModel;
    }

    public void setSpecModel(String specModel) {
        this.specModel = specModel;
    }

    @Basic
    @Column(name = "applicable_model")
    public String getApplicableModel() {
        return applicableModel;
    }

    public void setApplicableModel(String applicableModel) {
        this.applicableModel = applicableModel;
    }

    @Basic
    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "warehouse_name")
    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Basic
    @Column(name = "cost_record")
    public BigDecimal getCostRecord() {
        return costRecord;
    }

    public void setCostRecord(BigDecimal costRecord) {
        this.costRecord = costRecord;
    }

    @Basic
    @Column(name = "carriage_record")
    public BigDecimal getCarriageRecord() {
        return carriageRecord;
    }

    public void setCarriageRecord(BigDecimal carriageRecord) {
        this.carriageRecord = carriageRecord;
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
    @Column(name = "contract_no")
    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleSaleContractPresentGroups that = (VehicleSaleContractPresentGroups) o;

        if (presentGroupId != null ? !presentGroupId.equals(that.presentGroupId) : that.presentGroupId != null)
            return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (income != null ? !income.equals(that.income) : that.income != null) return false;
        if (planQuantity != null ? !planQuantity.equals(that.planQuantity) : that.planQuantity != null) return false;
        if (posPrice != null ? !posPrice.equals(that.posPrice) : that.posPrice != null) return false;
        if (posAgio != null ? !posAgio.equals(that.posAgio) : that.posAgio != null) return false;
        if (stockId != null ? !stockId.equals(that.stockId) : that.stockId != null) return false;
        if (depositPosition != null ? !depositPosition.equals(that.depositPosition) : that.depositPosition != null)
            return false;
        if (partName != null ? !partName.equals(that.partName) : that.partName != null) return false;
        if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;
        if (partMnemonic != null ? !partMnemonic.equals(that.partMnemonic) : that.partMnemonic != null) return false;
        if (producingArea != null ? !producingArea.equals(that.producingArea) : that.producingArea != null)
            return false;
        if (partType != null ? !partType.equals(that.partType) : that.partType != null) return false;
        if (specModel != null ? !specModel.equals(that.specModel) : that.specModel != null) return false;
        if (applicableModel != null ? !applicableModel.equals(that.applicableModel) : that.applicableModel != null)
            return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;
        if (warehouseName != null ? !warehouseName.equals(that.warehouseName) : that.warehouseName != null)
            return false;
        if (costRecord != null ? !costRecord.equals(that.costRecord) : that.costRecord != null) return false;
        if (carriageRecord != null ? !carriageRecord.equals(that.carriageRecord) : that.carriageRecord != null)
            return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (contractNo != null ? !contractNo.equals(that.contractNo) : that.contractNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = presentGroupId != null ? presentGroupId.hashCode() : 0;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (income != null ? income.hashCode() : 0);
        result = 31 * result + (planQuantity != null ? planQuantity.hashCode() : 0);
        result = 31 * result + (posPrice != null ? posPrice.hashCode() : 0);
        result = 31 * result + (posAgio != null ? posAgio.hashCode() : 0);
        result = 31 * result + (stockId != null ? stockId.hashCode() : 0);
        result = 31 * result + (depositPosition != null ? depositPosition.hashCode() : 0);
        result = 31 * result + (partName != null ? partName.hashCode() : 0);
        result = 31 * result + (partNo != null ? partNo.hashCode() : 0);
        result = 31 * result + (partMnemonic != null ? partMnemonic.hashCode() : 0);
        result = 31 * result + (producingArea != null ? producingArea.hashCode() : 0);
        result = 31 * result + (partType != null ? partType.hashCode() : 0);
        result = 31 * result + (specModel != null ? specModel.hashCode() : 0);
        result = 31 * result + (applicableModel != null ? applicableModel.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (warehouseName != null ? warehouseName.hashCode() : 0);
        result = 31 * result + (costRecord != null ? costRecord.hashCode() : 0);
        result = 31 * result + (carriageRecord != null ? carriageRecord.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (contractNo != null ? contractNo.hashCode() : 0);
        return result;
    }
}
