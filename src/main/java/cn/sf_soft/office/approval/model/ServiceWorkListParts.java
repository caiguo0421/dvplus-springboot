package cn.sf_soft.office.approval.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Auther: chenbiao
 * @Date: 2019/7/15 16:20
 * @Description:
 */
@Entity
@Table(name = "service_work_list_parts")
public class ServiceWorkListParts {
    private String listPartId;
    private String posDetailId;
    private String taskNo;
    private BigDecimal planQuantity;
    private BigDecimal getQuantity;
    private BigDecimal posPrice;
    private BigDecimal posAgio;
    private BigDecimal costRef;
    private BigDecimal cost;
    private BigDecimal costNoTax;
    private BigDecimal carriage;
    private BigDecimal carriageNoTax;
    private String accountId;
    private String accountName;
    private Byte accountType;
    private String stockId;
    private String depositPosition;
    private String partName;
    private String partNo;
    private String partProperty;
    private String partMnemonic;
    private String producingArea;
    private String partType;
    private String specModel;
    private String applicableModel;
    private String color;
    private String material;
    private String unit;
    private String warehouseName;
    private String appraisalId;
    private String appraisalNo;
    private Boolean partIsTakenOut;
    private Byte claimsDetailStatus;
    private String partFullCode;
    private String partFullName;
    private BigDecimal taxMoney;
    private BigDecimal noTaxMoney;
    private BigDecimal taxRate;
    private BigDecimal tax;
    private BigDecimal costTaxMoney;
    private BigDecimal costNoTaxMoney;
    private BigDecimal costTaxRate;
    private BigDecimal costTax;
    private Byte isReturn;
    private String referenceAppraisalerId;
    private String referenceAppraisalerNo;
    private String referenceAppraisaler;
    private String partCategoryMeaning;
    private BigDecimal memberCustomerOriPrice;
    private BigDecimal memberCustomerOriAgio;
    private String partIdDf;
    private BigDecimal outTotalCost;
    private Integer claimsSelectSeq;
    private String posPriceDiscountType;
    private String appraisalInfo;
    private BigDecimal oriPosPrice;
    private String maintenanceType;
    private String adjustedListPrice;
    private String partsSource;
    private String actualProductArea;

    @Id
    @Column(name = "list_part_id")
    public String getListPartId() {
        return listPartId;
    }

    public void setListPartId(String listPartId) {
        this.listPartId = listPartId;
    }

    @Basic
    @Column(name = "pos_detail_id")
    public String getPosDetailId() {
        return posDetailId;
    }

    public void setPosDetailId(String posDetailId) {
        this.posDetailId = posDetailId;
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
    @Column(name = "plan_quantity")
    public BigDecimal getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(BigDecimal planQuantity) {
        this.planQuantity = planQuantity;
    }

    @Basic
    @Column(name = "get_quantity")
    public BigDecimal getGetQuantity() {
        return getQuantity;
    }

    public void setGetQuantity(BigDecimal getQuantity) {
        this.getQuantity = getQuantity;
    }

    @Basic
    @Column(name = "pos_price")
    public BigDecimal getPosPrice() {
        return posPrice;
    }

    public void setPosPrice(BigDecimal posPrice) {
        this.posPrice = posPrice;
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
    @Column(name = "cost_ref")
    public BigDecimal getCostRef() {
        return costRef;
    }

    public void setCostRef(BigDecimal costRef) {
        this.costRef = costRef;
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
    @Column(name = "cost_no_tax")
    public BigDecimal getCostNoTax() {
        return costNoTax;
    }

    public void setCostNoTax(BigDecimal costNoTax) {
        this.costNoTax = costNoTax;
    }

    @Basic
    @Column(name = "carriage")
    public BigDecimal getCarriage() {
        return carriage;
    }

    public void setCarriage(BigDecimal carriage) {
        this.carriage = carriage;
    }

    @Basic
    @Column(name = "carriage_no_tax")
    public BigDecimal getCarriageNoTax() {
        return carriageNoTax;
    }

    public void setCarriageNoTax(BigDecimal carriageNoTax) {
        this.carriageNoTax = carriageNoTax;
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
    @Column(name = "part_property")
    public String getPartProperty() {
        return partProperty;
    }

    public void setPartProperty(String partProperty) {
        this.partProperty = partProperty;
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
    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Basic
    @Column(name = "material")
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
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
    @Column(name = "part_is_taken_out")
    public Boolean getPartIsTakenOut() {
        return partIsTakenOut;
    }

    public void setPartIsTakenOut(Boolean partIsTakenOut) {
        this.partIsTakenOut = partIsTakenOut;
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
    @Column(name = "part_full_code")
    public String getPartFullCode() {
        return partFullCode;
    }

    public void setPartFullCode(String partFullCode) {
        this.partFullCode = partFullCode;
    }

    @Basic
    @Column(name = "part_full_name")
    public String getPartFullName() {
        return partFullName;
    }

    public void setPartFullName(String partFullName) {
        this.partFullName = partFullName;
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
    @Column(name = "is_return")
    public Byte getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Byte isReturn) {
        this.isReturn = isReturn;
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
    @Column(name = "part_category_meaning")
    public String getPartCategoryMeaning() {
        return partCategoryMeaning;
    }

    public void setPartCategoryMeaning(String partCategoryMeaning) {
        this.partCategoryMeaning = partCategoryMeaning;
    }

    @Basic
    @Column(name = "member_customer_ori_price")
    public BigDecimal getMemberCustomerOriPrice() {
        return memberCustomerOriPrice;
    }

    public void setMemberCustomerOriPrice(BigDecimal memberCustomerOriPrice) {
        this.memberCustomerOriPrice = memberCustomerOriPrice;
    }

    @Basic
    @Column(name = "member_customer_ori_agio")
    public BigDecimal getMemberCustomerOriAgio() {
        return memberCustomerOriAgio;
    }

    public void setMemberCustomerOriAgio(BigDecimal memberCustomerOriAgio) {
        this.memberCustomerOriAgio = memberCustomerOriAgio;
    }

    @Basic
    @Column(name = "part_id_df")
    public String getPartIdDf() {
        return partIdDf;
    }

    public void setPartIdDf(String partIdDf) {
        this.partIdDf = partIdDf;
    }

    @Basic
    @Column(name = "out_total_cost")
    public BigDecimal getOutTotalCost() {
        return outTotalCost;
    }

    public void setOutTotalCost(BigDecimal outTotalCost) {
        this.outTotalCost = outTotalCost;
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
    @Column(name = "pos_price_discount_type")
    public String getPosPriceDiscountType() {
        return posPriceDiscountType;
    }

    public void setPosPriceDiscountType(String posPriceDiscountType) {
        this.posPriceDiscountType = posPriceDiscountType;
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
    @Column(name = "ori_pos_price")
    public BigDecimal getOriPosPrice() {
        return oriPosPrice;
    }

    public void setOriPosPrice(BigDecimal oriPosPrice) {
        this.oriPosPrice = oriPosPrice;
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
    @Column(name = "adjusted_list_price")
    public String getAdjustedListPrice() {
        return adjustedListPrice;
    }

    public void setAdjustedListPrice(String adjustedListPrice) {
        this.adjustedListPrice = adjustedListPrice;
    }

    @Basic
    @Column(name = "parts_source")
    public String getPartsSource() {
        return partsSource;
    }

    public void setPartsSource(String partsSource) {
        this.partsSource = partsSource;
    }

    @Basic
    @Column(name = "actual_product_area")
    public String getActualProductArea() {
        return actualProductArea;
    }

    public void setActualProductArea(String actualProductArea) {
        this.actualProductArea = actualProductArea;
    }
}
