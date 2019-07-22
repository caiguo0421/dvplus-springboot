package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by henry on 2018/5/29.
 */
@Entity
@Table(name = "vehicle_sale_quotation", schema = "dbo")
public class VehicleSaleQuotation {
    private String quotationId;
    private String vnoId;
    private String vehicleVno;
    private String shortNameVno;
    private String vehicleName;
    private String vehicleStrain;
    private String vehicleColor;
    private BigDecimal vehiclePriceTotal;
    private BigDecimal vehiclePrice;
    private BigDecimal deposit;
    private BigDecimal discountAmount;
    private Integer vehicleQuantity;
    private String vnoIdNew;
    private String vehicleVnoNew;
    private String vehicleNameNew;
    private Boolean isContainInsuranceCost;
    private BigDecimal insuranceIncome;
    private BigDecimal insurancePf;
    private BigDecimal presentIncome;
    private BigDecimal presentPf;
    private BigDecimal conversionIncome;
    private BigDecimal conversionPf;
    private BigDecimal chargeIncome;
    private BigDecimal chargePf;
    private String customerId;
    private String customerNo;
    private String customerName;
    private BigDecimal vehicleProfit;
    private BigDecimal largessAmount;
    private String belongToSupplierId;
    private String belongToSupplierNo;
    private String belongToSupplierName;
    private String subjectMatter;
    private String startPoint;
    private String waysPoint;
    private String endPoint;
    private String vehicleOwnerId;
    private String vehicleOwnerName;
    private String visitorNo;
    private String imagesUrls;
    private String vehicleComment;
    private String contractNo;

    private Short buyType;
    private Timestamp planFinishTime;

    private Timestamp createTime;

    /**
     * 30 : 已作废
     */
    private Short status;

    private String seller;
    private String sellerId;

    private String departmentId;

    private String linkman;

    @Id
    @Column(name = "quotation_id")
    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    @Basic
    @Column(name = "vno_id")
    public String getVnoId() {
        return vnoId;
    }

    public void setVnoId(String vnoId) {
        this.vnoId = vnoId;
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
    @Column(name = "short_name_vno")
    public String getShortNameVno() {
        return shortNameVno;
    }

    public void setShortNameVno(String shortNameVno) {
        this.shortNameVno = shortNameVno;
    }

    @Basic
    @Column(name = "vehicle_name")
    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Basic
    @Column(name = "vehicle_strain")
    public String getVehicleStrain() {
        return vehicleStrain;
    }

    public void setVehicleStrain(String vehicleStrain) {
        this.vehicleStrain = vehicleStrain;
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
    @Column(name = "vehicle_price_total")
    public BigDecimal getVehiclePriceTotal() {
        return vehiclePriceTotal;
    }

    public void setVehiclePriceTotal(BigDecimal vehiclePriceTotal) {
        this.vehiclePriceTotal = vehiclePriceTotal;
    }

    @Basic
    @Column(name = "vehicle_price")
    public BigDecimal getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(BigDecimal vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    @Basic
    @Column(name = "deposit")
    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    @Basic
    @Column(name = "discount_amount")
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Basic
    @Column(name = "vehicle_quantity")
    public Integer getVehicleQuantity() {
        return vehicleQuantity;
    }

    public void setVehicleQuantity(Integer vehicleQuantity) {
        this.vehicleQuantity = vehicleQuantity;
    }

    @Basic
    @Column(name = "vno_id_new")
    public String getVnoIdNew() {
        return vnoIdNew;
    }

    public void setVnoIdNew(String vnoIdNew) {
        this.vnoIdNew = vnoIdNew;
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
    @Column(name = "vehicle_name_new")
    public String getVehicleNameNew() {
        return vehicleNameNew;
    }

    public void setVehicleNameNew(String vehicleNameNew) {
        this.vehicleNameNew = vehicleNameNew;
    }

    @Basic
    @Column(name = "is_contain_insurance_cost")
    public Boolean getIsContainInsuranceCost() {
        return isContainInsuranceCost;
    }

    public void setIsContainInsuranceCost(Boolean isContainInsuranceCost) {
        isContainInsuranceCost = isContainInsuranceCost;
    }

    @Basic
    @Column(name = "insurance_income")
    public BigDecimal getInsuranceIncome() {
        return insuranceIncome;
    }

    public void setInsuranceIncome(BigDecimal insuranceIncome) {
        this.insuranceIncome = insuranceIncome;
    }

    @Basic
    @Column(name = "insurance_pf")
    public BigDecimal getInsurancePf() {
        return insurancePf;
    }

    public void setInsurancePf(BigDecimal insurancePf) {
        this.insurancePf = insurancePf;
    }

    @Basic
    @Column(name = "present_income")
    public BigDecimal getPresentIncome() {
        return presentIncome;
    }

    public void setPresentIncome(BigDecimal presentIncome) {
        this.presentIncome = presentIncome;
    }

    @Basic
    @Column(name = "present_pf")
    public BigDecimal getPresentPf() {
        return presentPf;
    }

    public void setPresentPf(BigDecimal presentPf) {
        this.presentPf = presentPf;
    }

    @Basic
    @Column(name = "conversion_income")
    public BigDecimal getConversionIncome() {
        return conversionIncome;
    }

    public void setConversionIncome(BigDecimal conversionIncome) {
        this.conversionIncome = conversionIncome;
    }

    @Basic
    @Column(name = "conversion_pf")
    public BigDecimal getConversionPf() {
        return conversionPf;
    }

    public void setConversionPf(BigDecimal conversionPf) {
        this.conversionPf = conversionPf;
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
    @Column(name = "customer_id")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "customer_no")
    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    @Basic
    @Column(name = "customer_name")
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Basic
    @Column(name = "vehicle_profit")
    public BigDecimal getVehicleProfit() {
        return vehicleProfit;
    }

    public void setVehicleProfit(BigDecimal vehicleProfit) {
        this.vehicleProfit = vehicleProfit;
    }

    @Basic
    @Column(name = "largess_amount")
    public BigDecimal getLargessAmount() {
        return largessAmount;
    }

    public void setLargessAmount(BigDecimal largessAmount) {
        this.largessAmount = largessAmount;
    }

    @Basic
    @Column(name = "belong_to_supplier_id")
    public String getBelongToSupplierId() {
        return belongToSupplierId;
    }

    public void setBelongToSupplierId(String belongToSupplierId) {
        this.belongToSupplierId = belongToSupplierId;
    }

    @Basic
    @Column(name = "belong_to_supplier_no")
    public String getBelongToSupplierNo() {
        return belongToSupplierNo;
    }

    public void setBelongToSupplierNo(String belongToSupplierNo) {
        this.belongToSupplierNo = belongToSupplierNo;
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
    @Column(name = "subject_matter")
    public String getSubjectMatter() {
        return subjectMatter;
    }

    public void setSubjectMatter(String subjectMatter) {
        this.subjectMatter = subjectMatter;
    }

    @Basic
    @Column(name = "start_point")
    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    @Basic
    @Column(name = "ways_point")
    public String getWaysPoint() {
        return waysPoint;
    }

    public void setWaysPoint(String waysPoint) {
        this.waysPoint = waysPoint;
    }

    @Basic
    @Column(name = "end_point")
    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @Basic
    @Column(name = "vehicle_owner_id")
    public String getVehicleOwnerId() {
        return vehicleOwnerId;
    }

    public void setVehicleOwnerId(String vehicleOwnerId) {
        this.vehicleOwnerId = vehicleOwnerId;
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
    @Column(name = "visitor_no")
    public String getVisitorNo() {
        return visitorNo;
    }

    public void setVisitorNo(String visitorNo) {
        this.visitorNo = visitorNo;
    }

    @Basic
    @Column(name = "images_urls")
    public String getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(String imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    @Basic
    @Column(name = "vehicle_comment")
    public String getVehicleComment() {
        return vehicleComment;
    }

    public void setVehicleComment(String vehicleComment) {
        this.vehicleComment = vehicleComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleSaleQuotation that = (VehicleSaleQuotation) o;

        if (quotationId != null ? !quotationId.equals(that.quotationId) : that.quotationId != null) return false;
        if (vnoId != null ? !vnoId.equals(that.vnoId) : that.vnoId != null) return false;
        if (vehicleVno != null ? !vehicleVno.equals(that.vehicleVno) : that.vehicleVno != null) return false;
        if (shortNameVno != null ? !shortNameVno.equals(that.shortNameVno) : that.shortNameVno != null) return false;
        if (vehicleName != null ? !vehicleName.equals(that.vehicleName) : that.vehicleName != null) return false;
        if (vehicleStrain != null ? !vehicleStrain.equals(that.vehicleStrain) : that.vehicleStrain != null)
            return false;
        if (vehicleColor != null ? !vehicleColor.equals(that.vehicleColor) : that.vehicleColor != null) return false;
        if (vehiclePriceTotal != null ? !vehiclePriceTotal.equals(that.vehiclePriceTotal) : that.vehiclePriceTotal != null)
            return false;
        if (vehiclePrice != null ? !vehiclePrice.equals(that.vehiclePrice) : that.vehiclePrice != null) return false;
        if (deposit != null ? !deposit.equals(that.deposit) : that.deposit != null) return false;
        if (discountAmount != null ? !discountAmount.equals(that.discountAmount) : that.discountAmount != null)
            return false;
        if (vehicleQuantity != null ? !vehicleQuantity.equals(that.vehicleQuantity) : that.vehicleQuantity != null)
            return false;
        if (vnoIdNew != null ? !vnoIdNew.equals(that.vnoIdNew) : that.vnoIdNew != null) return false;
        if (vehicleVnoNew != null ? !vehicleVnoNew.equals(that.vehicleVnoNew) : that.vehicleVnoNew != null)
            return false;
        if (vehicleNameNew != null ? !vehicleNameNew.equals(that.vehicleNameNew) : that.vehicleNameNew != null)
            return false;
        if (isContainInsuranceCost != null ? !isContainInsuranceCost.equals(that.isContainInsuranceCost) : that.isContainInsuranceCost != null)
            return false;
        if (insuranceIncome != null ? !insuranceIncome.equals(that.insuranceIncome) : that.insuranceIncome != null)
            return false;
        if (insurancePf != null ? !insurancePf.equals(that.insurancePf) : that.insurancePf != null) return false;
        if (presentIncome != null ? !presentIncome.equals(that.presentIncome) : that.presentIncome != null)
            return false;
        if (presentPf != null ? !presentPf.equals(that.presentPf) : that.presentPf != null) return false;
        if (conversionIncome != null ? !conversionIncome.equals(that.conversionIncome) : that.conversionIncome != null)
            return false;
        if (conversionPf != null ? !conversionPf.equals(that.conversionPf) : that.conversionPf != null) return false;
        if (chargeIncome != null ? !chargeIncome.equals(that.chargeIncome) : that.chargeIncome != null) return false;
        if (chargePf != null ? !chargePf.equals(that.chargePf) : that.chargePf != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null)
            return false;
        if (customerNo != null ? !customerNo.equals(that.customerNo) : that.customerNo != null)
            return false;
        if (customerName != null ? !customerName.equals(that.customerName) : that.customerName != null)
            return false;
        if (vehicleProfit != null ? !vehicleProfit.equals(that.vehicleProfit) : that.vehicleProfit != null)
            return false;
        if (largessAmount != null ? !largessAmount.equals(that.largessAmount) : that.largessAmount != null)
            return false;
        if (belongToSupplierId != null ? !belongToSupplierId.equals(that.belongToSupplierId) : that.belongToSupplierId != null)
            return false;
        if (belongToSupplierNo != null ? !belongToSupplierNo.equals(that.belongToSupplierNo) : that.belongToSupplierNo != null)
            return false;
        if (belongToSupplierName != null ? !belongToSupplierName.equals(that.belongToSupplierName) : that.belongToSupplierName != null)
            return false;
        if (subjectMatter != null ? !subjectMatter.equals(that.subjectMatter) : that.subjectMatter != null)
            return false;
        if (startPoint != null ? !startPoint.equals(that.startPoint) : that.startPoint != null) return false;
        if (waysPoint != null ? !waysPoint.equals(that.waysPoint) : that.waysPoint != null) return false;
        if (endPoint != null ? !endPoint.equals(that.endPoint) : that.endPoint != null) return false;
        if (vehicleOwnerId != null ? !vehicleOwnerId.equals(that.vehicleOwnerId) : that.vehicleOwnerId != null)
            return false;
        if (vehicleOwnerName != null ? !vehicleOwnerName.equals(that.vehicleOwnerName) : that.vehicleOwnerName != null)
            return false;
        if (visitorNo != null ? !visitorNo.equals(that.visitorNo) : that.visitorNo != null) return false;
        if (imagesUrls != null ? !imagesUrls.equals(that.imagesUrls) : that.imagesUrls != null) return false;
        if (vehicleComment != null ? !vehicleComment.equals(that.vehicleComment) : that.vehicleComment != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = quotationId != null ? quotationId.hashCode() : 0;
        result = 31 * result + (vnoId != null ? vnoId.hashCode() : 0);
        result = 31 * result + (vehicleVno != null ? vehicleVno.hashCode() : 0);
        result = 31 * result + (shortNameVno != null ? shortNameVno.hashCode() : 0);
        result = 31 * result + (vehicleName != null ? vehicleName.hashCode() : 0);
        result = 31 * result + (vehicleStrain != null ? vehicleStrain.hashCode() : 0);
        result = 31 * result + (vehicleColor != null ? vehicleColor.hashCode() : 0);
        result = 31 * result + (vehiclePriceTotal != null ? vehiclePriceTotal.hashCode() : 0);
        result = 31 * result + (vehiclePrice != null ? vehiclePrice.hashCode() : 0);
        result = 31 * result + (deposit != null ? deposit.hashCode() : 0);
        result = 31 * result + (discountAmount != null ? discountAmount.hashCode() : 0);
        result = 31 * result + (vehicleQuantity != null ? vehicleQuantity.hashCode() : 0);
        result = 31 * result + (vnoIdNew != null ? vnoIdNew.hashCode() : 0);
        result = 31 * result + (vehicleVnoNew != null ? vehicleVnoNew.hashCode() : 0);
        result = 31 * result + (vehicleNameNew != null ? vehicleNameNew.hashCode() : 0);
        result = 31 * result + (isContainInsuranceCost != null ? isContainInsuranceCost.hashCode() : 0);
        result = 31 * result + (insuranceIncome != null ? insuranceIncome.hashCode() : 0);
        result = 31 * result + (insurancePf != null ? insurancePf.hashCode() : 0);
        result = 31 * result + (presentIncome != null ? presentIncome.hashCode() : 0);
        result = 31 * result + (presentPf != null ? presentPf.hashCode() : 0);
        result = 31 * result + (conversionIncome != null ? conversionIncome.hashCode() : 0);
        result = 31 * result + (conversionPf != null ? conversionPf.hashCode() : 0);
        result = 31 * result + (chargeIncome != null ? chargeIncome.hashCode() : 0);
        result = 31 * result + (chargePf != null ? chargePf.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (customerNo != null ? customerNo.hashCode() : 0);
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (vehicleProfit != null ? vehicleProfit.hashCode() : 0);
        result = 31 * result + (largessAmount != null ? largessAmount.hashCode() : 0);
        result = 31 * result + (belongToSupplierId != null ? belongToSupplierId.hashCode() : 0);
        result = 31 * result + (belongToSupplierNo != null ? belongToSupplierNo.hashCode() : 0);
        result = 31 * result + (belongToSupplierName != null ? belongToSupplierName.hashCode() : 0);
        result = 31 * result + (subjectMatter != null ? subjectMatter.hashCode() : 0);
        result = 31 * result + (startPoint != null ? startPoint.hashCode() : 0);
        result = 31 * result + (waysPoint != null ? waysPoint.hashCode() : 0);
        result = 31 * result + (endPoint != null ? endPoint.hashCode() : 0);
        result = 31 * result + (vehicleOwnerId != null ? vehicleOwnerId.hashCode() : 0);
        result = 31 * result + (vehicleOwnerName != null ? vehicleOwnerName.hashCode() : 0);
        result = 31 * result + (visitorNo != null ? visitorNo.hashCode() : 0);
        result = 31 * result + (imagesUrls != null ? imagesUrls.hashCode() : 0);
        result = 31 * result + (vehicleComment != null ? vehicleComment.hashCode() : 0);
        result = 31 * result + (contractNo != null ? contractNo.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "contract_no")
    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    @Column(name = "buy_type")
    public Short getBuyType() {
        return buyType;
    }

    public void setBuyType(Short buyType) {
        this.buyType = buyType;
    }

    @Column(name = "plan_finish_time")
    public Timestamp getPlanFinishTime() {
        return planFinishTime;
    }

    public void setPlanFinishTime(Timestamp planFinishTime) {
        this.planFinishTime = planFinishTime;
    }

    @Column(name = "status")
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Column(name = "seller")
    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Column(name = "seller_id")
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "department_id")
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "linkman")
    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }
}
