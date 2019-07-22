package cn.sf_soft.office.approval.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Auther: chenbiao
 * @Date: 2018/9/20 11:19
 * @Description:
 */
@Entity
@Table(name = "vw_vehicle_sale_contract_detail_groups")
public class VwVehicleSaleContractDetailGroups implements Serializable {
    private String groupId;
    private String groupNo;
    private String contractNo;
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
    private String customerIdProfit;
    private String customerNoProfit;
    private String customerNameProfit;
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
    private String groupNoRef;
    private String profession;
    private String containerSize;
    private String vehicleSize;
    private BigDecimal vehicleWeight;
    private BigDecimal curbWeight;
    private BigDecimal registrationTonnage;
    private String registrationAddress;
    private BigDecimal vehicleCostRef;
    private BigDecimal minSalePrice;
    private BigDecimal minProfit;
    private String objectProperty;
    private String commissionMerchantName;
    private int matchQuantity;
    private BigDecimal vehicleCost;
    private BigDecimal profitMin;
    private BigDecimal priceSale;
    private BigDecimal priceSaleBase;
    private BigDecimal referenceCost;
    private BigDecimal referenceCostGz;
    private BigDecimal totInsuranceIncome;
    private BigDecimal totPresentIncome;
    private BigDecimal totItemIncome;
    private BigDecimal totChargeIncome;
    private BigDecimal incomeTot;
    private BigDecimal costTot;
    private String transportRoutes;
    private String vehicleBrand;
    private Byte vehicleKind;
    private String vehicleKindMeaning;
    private BigDecimal invoiceAmountTot;
    private int outStockQuantity;
    private BigDecimal profitMinRef;
    private BigDecimal priceSaleRef;
    private BigDecimal itemCostOri;
    private BigDecimal loanChargeIncome;
    private BigDecimal loanChargeExpenditure;
    private BigDecimal loanChargeProfit;
    private BigDecimal priceDiff;
    private BigDecimal profitDiff;
    private int isBelowPrice;
    private int isBelowProfit;

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
    @Column(name = "vehicle_size")
    public String getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(String vehicleSize) {
        this.vehicleSize = vehicleSize;
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
    @Column(name = "vehicle_cost_ref")
    public BigDecimal getVehicleCostRef() {
        return vehicleCostRef;
    }

    public void setVehicleCostRef(BigDecimal vehicleCostRef) {
        this.vehicleCostRef = vehicleCostRef;
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
    @Column(name = "object_property")
    public String getObjectProperty() {
        return objectProperty;
    }

    public void setObjectProperty(String objectProperty) {
        this.objectProperty = objectProperty;
    }

    @Basic
    @Column(name = "commission_merchant_name")
    public String getCommissionMerchantName() {
        return commissionMerchantName;
    }

    public void setCommissionMerchantName(String commissionMerchantName) {
        this.commissionMerchantName = commissionMerchantName;
    }

    @Basic
    @Column(name = "match_quantity")
    public int getMatchQuantity() {
        return matchQuantity;
    }

    public void setMatchQuantity(int matchQuantity) {
        this.matchQuantity = matchQuantity;
    }

    @Basic
    @Column(name = "vehicle_cost")
    public BigDecimal getVehicleCost() {
        return vehicleCost;
    }

    public void setVehicleCost(BigDecimal vehicleCost) {
        this.vehicleCost = vehicleCost;
    }

    @Basic
    @Column(name = "profit_min")
    public BigDecimal getProfitMin() {
        return profitMin;
    }

    public void setProfitMin(BigDecimal profitMin) {
        this.profitMin = profitMin;
    }

    @Basic
    @Column(name = "price_sale")
    public BigDecimal getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(BigDecimal priceSale) {
        this.priceSale = priceSale;
    }

    @Basic
    @Column(name = "price_sale_base")
    public BigDecimal getPriceSaleBase() {
        return priceSaleBase;
    }

    public void setPriceSaleBase(BigDecimal priceSaleBase) {
        this.priceSaleBase = priceSaleBase;
    }

    @Basic
    @Column(name = "reference_cost")
    public BigDecimal getReferenceCost() {
        return referenceCost;
    }

    public void setReferenceCost(BigDecimal referenceCost) {
        this.referenceCost = referenceCost;
    }

    @Basic
    @Column(name = "reference_cost_gz")
    public BigDecimal getReferenceCostGz() {
        return referenceCostGz;
    }

    public void setReferenceCostGz(BigDecimal referenceCostGz) {
        this.referenceCostGz = referenceCostGz;
    }

    @Basic
    @Column(name = "tot_insurance_income")
    public BigDecimal getTotInsuranceIncome() {
        return totInsuranceIncome;
    }

    public void setTotInsuranceIncome(BigDecimal totInsuranceIncome) {
        this.totInsuranceIncome = totInsuranceIncome;
    }

    @Basic
    @Column(name = "tot_present_income")
    public BigDecimal getTotPresentIncome() {
        return totPresentIncome;
    }

    public void setTotPresentIncome(BigDecimal totPresentIncome) {
        this.totPresentIncome = totPresentIncome;
    }

    @Basic
    @Column(name = "tot_item_income")
    public BigDecimal getTotItemIncome() {
        return totItemIncome;
    }

    public void setTotItemIncome(BigDecimal totItemIncome) {
        this.totItemIncome = totItemIncome;
    }

    @Basic
    @Column(name = "tot_charge_income")
    public BigDecimal getTotChargeIncome() {
        return totChargeIncome;
    }

    public void setTotChargeIncome(BigDecimal totChargeIncome) {
        this.totChargeIncome = totChargeIncome;
    }

    @Basic
    @Column(name = "income_tot")
    public BigDecimal getIncomeTot() {
        return incomeTot;
    }

    public void setIncomeTot(BigDecimal incomeTot) {
        this.incomeTot = incomeTot;
    }

    @Basic
    @Column(name = "cost_tot")
    public BigDecimal getCostTot() {
        return costTot;
    }

    public void setCostTot(BigDecimal costTot) {
        this.costTot = costTot;
    }

    @Basic
    @Column(name = "transportRoutes")
    public String getTransportRoutes() {
        return transportRoutes;
    }

    public void setTransportRoutes(String transportRoutes) {
        this.transportRoutes = transportRoutes;
    }

    @Basic
    @Column(name = "vehicle_brand")
    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    @Basic
    @Column(name = "vehicle_kind")
    public Byte getVehicleKind() {
        return vehicleKind;
    }

    public void setVehicleKind(Byte vehicleKind) {
        this.vehicleKind = vehicleKind;
    }

    @Basic
    @Column(name = "vehicle_kind_meaning")
    public String getVehicleKindMeaning() {
        return vehicleKindMeaning;
    }

    public void setVehicleKindMeaning(String vehicleKindMeaning) {
        this.vehicleKindMeaning = vehicleKindMeaning;
    }

    @Basic
    @Column(name = "invoice_amount_tot")
    public BigDecimal getInvoiceAmountTot() {
        return invoiceAmountTot;
    }

    public void setInvoiceAmountTot(BigDecimal invoiceAmountTot) {
        this.invoiceAmountTot = invoiceAmountTot;
    }

    @Basic
    @Column(name = "out_stock_quantity")
    public int getOutStockQuantity() {
        return outStockQuantity;
    }

    public void setOutStockQuantity(int outStockQuantity) {
        this.outStockQuantity = outStockQuantity;
    }

    @Basic
    @Column(name = "profit_min_ref")
    public BigDecimal getProfitMinRef() {
        return profitMinRef;
    }

    public void setProfitMinRef(BigDecimal profitMinRef) {
        this.profitMinRef = profitMinRef;
    }

    @Basic
    @Column(name = "price_sale_ref")
    public BigDecimal getPriceSaleRef() {
        return priceSaleRef;
    }

    public void setPriceSaleRef(BigDecimal priceSaleRef) {
        this.priceSaleRef = priceSaleRef;
    }

    @Basic
    @Column(name = "item_cost_ori")
    public BigDecimal getItemCostOri() {
        return itemCostOri;
    }

    public void setItemCostOri(BigDecimal itemCostOri) {
        this.itemCostOri = itemCostOri;
    }

    @Basic
    @Column(name = "loan_charge_income")
    public BigDecimal getLoanChargeIncome() {
        return loanChargeIncome;
    }

    public void setLoanChargeIncome(BigDecimal loanChargeIncome) {
        this.loanChargeIncome = loanChargeIncome;
    }

    @Basic
    @Column(name = "loan_charge_expenditure")
    public BigDecimal getLoanChargeExpenditure() {
        return loanChargeExpenditure;
    }

    public void setLoanChargeExpenditure(BigDecimal loanChargeExpenditure) {
        this.loanChargeExpenditure = loanChargeExpenditure;
    }

    @Basic
    @Column(name = "loan_charge_profit")
    public BigDecimal getLoanChargeProfit() {
        return loanChargeProfit;
    }

    public void setLoanChargeProfit(BigDecimal loanChargeProfit) {
        this.loanChargeProfit = loanChargeProfit;
    }

    @Basic
    @Column(name = "price_diff")
    public BigDecimal getPriceDiff() {
        return priceDiff;
    }

    public void setPriceDiff(BigDecimal priceDiff) {
        this.priceDiff = priceDiff;
    }

    @Basic
    @Column(name = "profit_diff")
    public BigDecimal getProfitDiff() {
        return profitDiff;
    }

    public void setProfitDiff(BigDecimal profitDiff) {
        this.profitDiff = profitDiff;
    }

    @Basic
    @Column(name = "is_below_price")
    public int getIsBelowPrice() {
        return isBelowPrice;
    }

    public void setIsBelowPrice(int isBelowPrice) {
        this.isBelowPrice = isBelowPrice;
    }

    @Basic
    @Column(name = "is_below_profit")
    public int getIsBelowProfit() {
        return isBelowProfit;
    }

    public void setIsBelowProfit(int isBelowProfit) {
        this.isBelowProfit = isBelowProfit;
    }
}
