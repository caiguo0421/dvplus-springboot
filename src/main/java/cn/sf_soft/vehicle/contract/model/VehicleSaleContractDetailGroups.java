package cn.sf_soft.vehicle.contract.model;

import cn.sf_soft.support.LogProperty;
import cn.sf_soft.support.LogPropertyType;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by henry on 2018/5/2.
 */
@Entity
@Table(name = "vehicle_sale_contract_detail_groups", schema = "dbo")
public class VehicleSaleContractDetailGroups {
    private String groupId;
    private String groupNo;
    private String contractNo;
    private String vnoId;
    @LogProperty(join = false)
    private String vehicleVno;
    private String shortNameVno;
    private String vehicleName;
    private String vehicleStrain;
    @LogProperty(join = false)
    private String vehicleColor;
    @LogProperty(type = LogPropertyType.BIG_DECIMAL, nullDefault = "0")
    private BigDecimal vehiclePriceTotal = BigDecimal.ZERO;
    @LogProperty(type = LogPropertyType.BIG_DECIMAL, nullDefault = "0")
    private BigDecimal vehiclePrice = BigDecimal.ZERO;
    @LogProperty(type = LogPropertyType.BIG_DECIMAL, nullDefault = "0")
    private BigDecimal deposit = BigDecimal.ZERO;
    @LogProperty(type = LogPropertyType.BIG_DECIMAL, nullDefault = "0")
    private BigDecimal discountAmount = BigDecimal.ZERO;
    @LogProperty(name = "quantity", type = LogPropertyType.INTEGER, nullDefault = "0")
    private Integer vehicleQuantity;
    private String vnoIdNew;
    @LogProperty(name = "vehicleCardModel", join = false)
    private String vehicleVnoNew;
    private String vehicleNameNew;
    @LogProperty(type = LogPropertyType.BOOLEAN, join = false)
    private Boolean isContainInsuranceCost = false;
    private BigDecimal insuranceIncome = BigDecimal.ZERO;
    private BigDecimal insurancePf = BigDecimal.ZERO;
    private BigDecimal presentIncome = BigDecimal.ZERO;
    private BigDecimal presentPf = BigDecimal.ZERO;
    private BigDecimal conversionIncome = BigDecimal.ZERO;
    private BigDecimal conversionPf = BigDecimal.ZERO;
    private BigDecimal chargeIncome = BigDecimal.ZERO;
    private BigDecimal chargePf = BigDecimal.ZERO;
    private String customerIdProfit;
    private String customerNoProfit;
    @LogProperty(name = "customerNameDetail", join = false)
    private String customerNameProfit;
    @LogProperty(join = false, type = LogPropertyType.BIG_DECIMAL)
    private BigDecimal vehicleProfit = BigDecimal.ZERO;
    @LogProperty(type = LogPropertyType.BIG_DECIMAL)
    private BigDecimal largessAmount = BigDecimal.ZERO;
    private String belongToSupplierId;
    private String belongToSupplierNo;
    @LogProperty(join = false)
    private String belongToSupplierName;
    @LogProperty()
    private String subjectMatter;
    private String startPoint;
    private String waysPoint;
    private String endPoint;
    private String vehicleOwnerId;
    @LogProperty(join = false)
    private String vehicleOwnerName;
    private String visitorNo;
    private String imagesUrls;
    private String vehicleComment;
    private String groupNoRef;

    /**
     * 整车尺寸
     */
    private String vehicleSize;


    /**
     * 职业
     */
    private String profession;


    /**
     * 货箱尺寸
     */
    private String containerSize;


    /**
     * 总质量
     */
    private BigDecimal vehicleWeight;

    /**
     * 整备质量
     */
    private BigDecimal curbWeight;


    private BigDecimal registrationTonnage;

    private String registrationAddress;



    /**
     * 最低限价
     */
    private BigDecimal minSalePrice;

    /**
     * 最低利润
     */
    private BigDecimal minProfit;

    /**
     * 参考成本
     */
    private BigDecimal vehicleCostRef;


    /**
     * 上牌类型
     */
    private String cardType;

    /**
     * 使用性质
     */
    private String useProperty;

    /**
     * 准乘人数
     */
    private Integer peopleNumber;

    /**
     * 环保公告
     */
    private String epNotice;

    /**
     * 燃油公告
     */
    private String oilNotice;

    /**
     * 准牵引质量
     */
    private BigDecimal tractiveTonnage;


    /**
     * 车厢内尺寸
     */
    private String containerInsideSize;


    /**
     * 保险代购(年)
     */
    private String insuranceYear;


    /**
     * 保险指定机构
     */
    private String insuranceAppointUnit;


    /**
     * 冗余字段
     */
    @LogProperty(name = "income", type = LogPropertyType.BIG_DECIMAL, nullDefault = "0")
    private BigDecimal incomeTot;
    @LogProperty(name = "cost", type = LogPropertyType.BIG_DECIMAL, nullDefault = "0")
    private BigDecimal costTot;
    private BigDecimal priceSale;
    @LogProperty(name = "profit", type = LogPropertyType.BIG_DECIMAL, nullDefault = "0")
    private BigDecimal profitTot;
    private BigDecimal profitMin;

    @LogProperty(name = "transportRoutes", join = true)
    private String transportRoutes;

    @Id
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

    public void setIsContainInsuranceCost(Boolean containInsuranceCost) {
        isContainInsuranceCost = containInsuranceCost;
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
    @Column(name = "customer_id_profit")
    public String getCustomerIdProfit() {
        return customerIdProfit;
    }

    public void setCustomerIdProfit(String customerIdProfit) {
        this.customerIdProfit = customerIdProfit;
    }

    @Basic
    @Column(name = "customer_no_profit")
    public String getCustomerNoProfit() {
        return customerNoProfit;
    }

    public void setCustomerNoProfit(String customerNoProfit) {
        this.customerNoProfit = customerNoProfit;
    }

    @Basic
    @Column(name = "customer_name_profit")
    public String getCustomerNameProfit() {
        return customerNameProfit;
    }

    public void setCustomerNameProfit(String customerNameProfit) {
        this.customerNameProfit = customerNameProfit;
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

    @Basic
    @Column(name = "group_no_ref")
    public String getGroupNoRef() {
        return groupNoRef;
    }

    public void setGroupNoRef(String groupNoRef) {
        this.groupNoRef = groupNoRef;
    }

    public BigDecimal getIncomeTot() {
        return incomeTot;
    }

    public void setIncomeTot(BigDecimal incomeTot) {
        this.incomeTot = incomeTot;
    }

    public BigDecimal getCostTot() {
        return costTot;
    }

    public void setCostTot(BigDecimal costTot) {
        this.costTot = costTot;
    }

    public BigDecimal getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(BigDecimal priceSale) {
        this.priceSale = priceSale;
    }

    public BigDecimal getProfitTot() {
        return profitTot;
    }

    public void setProfitTot(BigDecimal profitTot) {
        this.profitTot = profitTot;
    }

    public BigDecimal getProfitMin() {
        return profitMin;
    }

    public void setProfitMin(BigDecimal profitMin) {
        this.profitMin = profitMin;
    }

    public String getTransportRoutes() {
        return transportRoutes;
    }

    @Transient
    public void setTransportRoutes(String transportRoutes) {
        this.transportRoutes = transportRoutes;
    }


    @Basic
    @Column(name = "min_sale_price")
    public BigDecimal getMinSalePrice() {
        return minSalePrice;
    }


    public void setMinSalePrice(BigDecimal minSalePrice) {
        this.minSalePrice = minSalePrice;
    }

    @Basic
    @Column(name = "min_profit")
    public BigDecimal getMinProfit() {
        return minProfit;
    }

    public void setMinProfit(BigDecimal minProfit) {
        this.minProfit = minProfit;
    }


    @Basic
    @Column(name = "vehicle_cost_ref")
    public BigDecimal getVehicleCostRef() {
        return vehicleCostRef;
    }

    public void setVehicleCostRef(BigDecimal vehicleCostRef) {
        this.vehicleCostRef = vehicleCostRef;
    }


    @Basic
    @Column(name = "card_type")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Basic
    @Column(name = "people_number")
    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    @Basic
    @Column(name = "ep_notice")
    public String getEpNotice() {
        return epNotice;
    }

    public void setEpNotice(String epNotice) {
        this.epNotice = epNotice;
    }

    @Basic
    @Column(name = "tractive_tonnage")
    public BigDecimal getTractiveTonnage() {
        return tractiveTonnage;
    }

    public void setTractiveTonnage(BigDecimal tractiveTonnage) {
        this.tractiveTonnage = tractiveTonnage;
    }

    @Basic
    @Column(name = "container_inside_size")
    public String getContainerInsideSize() {
        return containerInsideSize;
    }

    public void setContainerInsideSize(String containerInsideSize) {
        this.containerInsideSize = containerInsideSize;
    }


    @Basic
    @Column(name = "profession")
    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Basic
    @Column(name = "container_size")
    public String getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(String containerSize) {
        this.containerSize = containerSize;
    }

    @Basic
    @Column(name = "vehicle_weight")
    public BigDecimal getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(BigDecimal vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }

    @Basic
    @Column(name = "curb_weight")
    public BigDecimal getCurbWeight() {
        return curbWeight;
    }

    public void setCurbWeight(BigDecimal curbWeight) {
        this.curbWeight = curbWeight;
    }

    @Basic
    @Column(name = "registration_tonnage")
    public BigDecimal getRegistrationTonnage() {
        return registrationTonnage;
    }

    public void setRegistrationTonnage(BigDecimal registrationTonnage) {
        this.registrationTonnage = registrationTonnage;
    }

    @Basic
    @Column(name = "registration_address")
    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    @Basic
    @Column(name = "oil_notice")
    public String getOilNotice() {
        return oilNotice;
    }

    public void setOilNotice(String oilNotice) {
        this.oilNotice = oilNotice;
    }

    @Basic
    @Column(name = "vehicle_size")
    public String getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(String vehicleSize) {
        this.vehicleSize = vehicleSize;
    }

    @Basic
    @Column(name = "use_property")
    public String getUseProperty() {
        return useProperty;
    }

    public void setUseProperty(String useProperty) {
        this.useProperty = useProperty;
    }

    @Basic
    @Column(name = "insurance_year")
    public String getInsuranceYear() {
        return insuranceYear;
    }

    public void setInsuranceYear(String insuranceYear) {
        this.insuranceYear = insuranceYear;
    }

    @Basic
    @Column(name = "insurance_appoint_unit")
    public String getInsuranceAppointUnit() {
        return insuranceAppointUnit;
    }

    public void setInsuranceAppointUnit(String insuranceAppointUnit) {
        this.insuranceAppointUnit = insuranceAppointUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleSaleContractDetailGroups that = (VehicleSaleContractDetailGroups) o;

        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (groupNo != null ? !groupNo.equals(that.groupNo) : that.groupNo != null) return false;
        if (contractNo != null ? !contractNo.equals(that.contractNo) : that.contractNo != null) return false;
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
        if (customerIdProfit != null ? !customerIdProfit.equals(that.customerIdProfit) : that.customerIdProfit != null)
            return false;
        if (customerNoProfit != null ? !customerNoProfit.equals(that.customerNoProfit) : that.customerNoProfit != null)
            return false;
        if (customerNameProfit != null ? !customerNameProfit.equals(that.customerNameProfit) : that.customerNameProfit != null)
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
        if (groupNoRef != null ? !groupNoRef.equals(that.groupNoRef) : that.groupNoRef != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (groupNo != null ? groupNo.hashCode() : 0);
        result = 31 * result + (contractNo != null ? contractNo.hashCode() : 0);
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
        result = 31 * result + (customerIdProfit != null ? customerIdProfit.hashCode() : 0);
        result = 31 * result + (customerNoProfit != null ? customerNoProfit.hashCode() : 0);
        result = 31 * result + (customerNameProfit != null ? customerNameProfit.hashCode() : 0);
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
        result = 31 * result + (groupNoRef != null ? groupNoRef.hashCode() : 0);
        return result;
    }
}
