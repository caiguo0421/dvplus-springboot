package cn.sf_soft.office.approval.model;

import cn.sf_soft.vehicle.contract.model.VehicleSaleContractHistoryGroups;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by henry on 2018/4/16.
 */
@Entity
@Table(name = "vehicle_sale_contract_history", schema = "dbo")
public class VehicleSaleContractHistory {
    private String historyId;
    private Integer sort;
    private String contractNo;
    private Timestamp varyTime;
    private String deposit;
    private BigDecimal depositOri;
    private BigDecimal depositNew;
    private String saleMode;
    private String saleModeOri;
    private String saleModeNew;
    private String signTime;
    private String signTimeOri;
    private String signTimeNew;
    private String planFinishTime;
    private String planFinishTimeOri;
    private String planFinishTimeNew;
    private String deliveryLocus;
    private String deliveryLocusOri;
    private String deliveryLocusNew;
    private String seller;
    private String sellerOri;
    private String sellerNew;
    private String visitorName;
    private String visitorNameOri;
    private String visitorNameNew;
    private String customerName;
    private String customerNameOri;
    private String customerNameNew;
    private String customerCertificateType;
    private String customerCertificateTypeOri;
    private String customerCertificateTypeNew;
    private String customerCertificateNo;
    private String customerCertificateNoOri;
    private String customerCertificateNoNew;
    private String customerMobile;
    private String customerMobileOri;
    private String customerMobileNew;
    private String profession;
    private String professionOri;
    private String professionNew;
    private String creator;
    private String createTime;
    private Byte status;
    private List<VehicleSaleContractHistoryGroups> groups;

    @Id
    @Column(name = "history_id")
    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    @Basic
    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
    @Column(name = "vary_time")
    public Timestamp getVaryTime() {
        return varyTime;
    }

    public void setVaryTime(Timestamp varyTime) {
        this.varyTime = varyTime;
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
    @Column(name = "sale_mode")
    public String getSaleMode() {
        return saleMode;
    }

    public void setSaleMode(String saleMode) {
        this.saleMode = saleMode;
    }

    @Basic
    @Column(name = "sale_mode_ori")
    public String getSaleModeOri() {
        return saleModeOri;
    }

    public void setSaleModeOri(String saleModeOri) {
        this.saleModeOri = saleModeOri;
    }

    @Basic
    @Column(name = "sale_mode_new")
    public String getSaleModeNew() {
        return saleModeNew;
    }

    public void setSaleModeNew(String saleModeNew) {
        this.saleModeNew = saleModeNew;
    }

    @Basic
    @Column(name = "sign_time")
    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    @Basic
    @Column(name = "sign_time_ori")
    public String getSignTimeOri() {
        return signTimeOri;
    }

    public void setSignTimeOri(String signTimeOri) {
        this.signTimeOri = signTimeOri;
    }

    @Basic
    @Column(name = "sign_time_new")
    public String getSignTimeNew() {
        return signTimeNew;
    }

    public void setSignTimeNew(String signTimeNew) {
        this.signTimeNew = signTimeNew;
    }

    @Basic
    @Column(name = "plan_finish_time")
    public String getPlanFinishTime() {
        return planFinishTime;
    }

    public void setPlanFinishTime(String planFinishTime) {
        this.planFinishTime = planFinishTime;
    }

    @Basic
    @Column(name = "plan_finish_time_ori")
    public String getPlanFinishTimeOri() {
        return planFinishTimeOri;
    }

    public void setPlanFinishTimeOri(String planFinishTimeOri) {
        this.planFinishTimeOri = planFinishTimeOri;
    }

    @Basic
    @Column(name = "plan_finish_time_new")
    public String getPlanFinishTimeNew() {
        return planFinishTimeNew;
    }

    public void setPlanFinishTimeNew(String planFinishTimeNew) {
        this.planFinishTimeNew = planFinishTimeNew;
    }

    @Basic
    @Column(name = "delivery_locus")
    public String getDeliveryLocus() {
        return deliveryLocus;
    }

    public void setDeliveryLocus(String deliveryLocus) {
        this.deliveryLocus = deliveryLocus;
    }

    @Basic
    @Column(name = "delivery_locus_ori")
    public String getDeliveryLocusOri() {
        return deliveryLocusOri;
    }

    public void setDeliveryLocusOri(String deliveryLocusOri) {
        this.deliveryLocusOri = deliveryLocusOri;
    }

    @Basic
    @Column(name = "delivery_locus_new")
    public String getDeliveryLocusNew() {
        return deliveryLocusNew;
    }

    public void setDeliveryLocusNew(String deliveryLocusNew) {
        this.deliveryLocusNew = deliveryLocusNew;
    }

    @Basic
    @Column(name = "seller")
    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Basic
    @Column(name = "seller_ori")
    public String getSellerOri() {
        return sellerOri;
    }

    public void setSellerOri(String sellerOri) {
        this.sellerOri = sellerOri;
    }

    @Basic
    @Column(name = "seller_new")
    public String getSellerNew() {
        return sellerNew;
    }

    public void setSellerNew(String sellerNew) {
        this.sellerNew = sellerNew;
    }

    @Basic
    @Column(name = "visitor_name")
    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    @Basic
    @Column(name = "visitor_name_ori")
    public String getVisitorNameOri() {
        return visitorNameOri;
    }

    public void setVisitorNameOri(String visitorNameOri) {
        this.visitorNameOri = visitorNameOri;
    }

    @Basic
    @Column(name = "visitor_name_new")
    public String getVisitorNameNew() {
        return visitorNameNew;
    }

    public void setVisitorNameNew(String visitorNameNew) {
        this.visitorNameNew = visitorNameNew;
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
    @Column(name = "customer_name_ori")
    public String getCustomerNameOri() {
        return customerNameOri;
    }

    public void setCustomerNameOri(String customerNameOri) {
        this.customerNameOri = customerNameOri;
    }

    @Basic
    @Column(name = "customer_name_new")
    public String getCustomerNameNew() {
        return customerNameNew;
    }

    public void setCustomerNameNew(String customerNameNew) {
        this.customerNameNew = customerNameNew;
    }

    @Basic
    @Column(name = "customer_certificate_type")
    public String getCustomerCertificateType() {
        return customerCertificateType;
    }

    public void setCustomerCertificateType(String customerCertificateType) {
        this.customerCertificateType = customerCertificateType;
    }

    @Basic
    @Column(name = "customer_certificate_type_ori")
    public String getCustomerCertificateTypeOri() {
        return customerCertificateTypeOri;
    }

    public void setCustomerCertificateTypeOri(String customerCertificateTypeOri) {
        this.customerCertificateTypeOri = customerCertificateTypeOri;
    }

    @Basic
    @Column(name = "customer_certificate_type_new")
    public String getCustomerCertificateTypeNew() {
        return customerCertificateTypeNew;
    }

    public void setCustomerCertificateTypeNew(String customerCertificateTypeNew) {
        this.customerCertificateTypeNew = customerCertificateTypeNew;
    }

    @Basic
    @Column(name = "customer_certificate_no")
    public String getCustomerCertificateNo() {
        return customerCertificateNo;
    }

    public void setCustomerCertificateNo(String customerCertificateNo) {
        this.customerCertificateNo = customerCertificateNo;
    }

    @Basic
    @Column(name = "customer_certificate_no_ori")
    public String getCustomerCertificateNoOri() {
        return customerCertificateNoOri;
    }

    public void setCustomerCertificateNoOri(String customerCertificateNoOri) {
        this.customerCertificateNoOri = customerCertificateNoOri;
    }

    @Basic
    @Column(name = "customer_certificate_no_new")
    public String getCustomerCertificateNoNew() {
        return customerCertificateNoNew;
    }

    public void setCustomerCertificateNoNew(String customerCertificateNoNew) {
        this.customerCertificateNoNew = customerCertificateNoNew;
    }

    @Basic
    @Column(name = "customer_mobile")
    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    @Basic
    @Column(name = "customer_mobile_ori")
    public String getCustomerMobileOri() {
        return customerMobileOri;
    }

    public void setCustomerMobileOri(String customerMobileOri) {
        this.customerMobileOri = customerMobileOri;
    }

    @Basic
    @Column(name = "customer_mobile_new")
    public String getCustomerMobileNew() {
        return customerMobileNew;
    }

    public void setCustomerMobileNew(String customerMobileNew) {
        this.customerMobileNew = customerMobileNew;
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
    @Column(name = "profession_ori")
    public String getProfessionOri() {
        return professionOri;
    }

    public void setProfessionOri(String professionOri) {
        this.professionOri = professionOri;
    }

    @Basic
    @Column(name = "profession_new")
    public String getProfessionNew() {
        return professionNew;
    }

    public void setProfessionNew(String professionNew) {
        this.professionNew = professionNew;
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
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Transient
    public List<VehicleSaleContractHistoryGroups> getGroups() {
        return groups;
    }

    public void setGroups(List<VehicleSaleContractHistoryGroups> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleSaleContractHistory that = (VehicleSaleContractHistory) o;

        if (historyId != null ? !historyId.equals(that.historyId) : that.historyId != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
        if (contractNo != null ? !contractNo.equals(that.contractNo) : that.contractNo != null) return false;
        if (varyTime != null ? !varyTime.equals(that.varyTime) : that.varyTime != null) return false;
        if (deposit != null ? !deposit.equals(that.deposit) : that.deposit != null) return false;
        if (depositOri != null ? !depositOri.equals(that.depositOri) : that.depositOri != null) return false;
        if (depositNew != null ? !depositNew.equals(that.depositNew) : that.depositNew != null) return false;
        if (saleMode != null ? !saleMode.equals(that.saleMode) : that.saleMode != null) return false;
        if (saleModeOri != null ? !saleModeOri.equals(that.saleModeOri) : that.saleModeOri != null) return false;
        if (saleModeNew != null ? !saleModeNew.equals(that.saleModeNew) : that.saleModeNew != null) return false;
        if (signTime != null ? !signTime.equals(that.signTime) : that.signTime != null) return false;
        if (signTimeOri != null ? !signTimeOri.equals(that.signTimeOri) : that.signTimeOri != null) return false;
        if (signTimeNew != null ? !signTimeNew.equals(that.signTimeNew) : that.signTimeNew != null) return false;
        if (planFinishTime != null ? !planFinishTime.equals(that.planFinishTime) : that.planFinishTime != null)
            return false;
        if (planFinishTimeOri != null ? !planFinishTimeOri.equals(that.planFinishTimeOri) : that.planFinishTimeOri != null)
            return false;
        if (planFinishTimeNew != null ? !planFinishTimeNew.equals(that.planFinishTimeNew) : that.planFinishTimeNew != null)
            return false;
        if (deliveryLocus != null ? !deliveryLocus.equals(that.deliveryLocus) : that.deliveryLocus != null)
            return false;
        if (deliveryLocusOri != null ? !deliveryLocusOri.equals(that.deliveryLocusOri) : that.deliveryLocusOri != null)
            return false;
        if (deliveryLocusNew != null ? !deliveryLocusNew.equals(that.deliveryLocusNew) : that.deliveryLocusNew != null)
            return false;
        if (seller != null ? !seller.equals(that.seller) : that.seller != null) return false;
        if (sellerOri != null ? !sellerOri.equals(that.sellerOri) : that.sellerOri != null) return false;
        if (sellerNew != null ? !sellerNew.equals(that.sellerNew) : that.sellerNew != null) return false;
        if (visitorName != null ? !visitorName.equals(that.visitorName) : that.visitorName != null) return false;
        if (visitorNameOri != null ? !visitorNameOri.equals(that.visitorNameOri) : that.visitorNameOri != null)
            return false;
        if (visitorNameNew != null ? !visitorNameNew.equals(that.visitorNameNew) : that.visitorNameNew != null)
            return false;
        if (customerName != null ? !customerName.equals(that.customerName) : that.customerName != null) return false;
        if (customerNameOri != null ? !customerNameOri.equals(that.customerNameOri) : that.customerNameOri != null)
            return false;
        if (customerNameNew != null ? !customerNameNew.equals(that.customerNameNew) : that.customerNameNew != null)
            return false;
        if (customerCertificateType != null ? !customerCertificateType.equals(that.customerCertificateType) : that.customerCertificateType != null)
            return false;
        if (customerCertificateTypeOri != null ? !customerCertificateTypeOri.equals(that.customerCertificateTypeOri) : that.customerCertificateTypeOri != null)
            return false;
        if (customerCertificateTypeNew != null ? !customerCertificateTypeNew.equals(that.customerCertificateTypeNew) : that.customerCertificateTypeNew != null)
            return false;
        if (customerCertificateNo != null ? !customerCertificateNo.equals(that.customerCertificateNo) : that.customerCertificateNo != null)
            return false;
        if (customerCertificateNoOri != null ? !customerCertificateNoOri.equals(that.customerCertificateNoOri) : that.customerCertificateNoOri != null)
            return false;
        if (customerCertificateNoNew != null ? !customerCertificateNoNew.equals(that.customerCertificateNoNew) : that.customerCertificateNoNew != null)
            return false;
        if (customerMobile != null ? !customerMobile.equals(that.customerMobile) : that.customerMobile != null)
            return false;
        if (customerMobileOri != null ? !customerMobileOri.equals(that.customerMobileOri) : that.customerMobileOri != null)
            return false;
        if (customerMobileNew != null ? !customerMobileNew.equals(that.customerMobileNew) : that.customerMobileNew != null)
            return false;
        if (profession != null ? !profession.equals(that.profession) : that.profession != null) return false;
        if (professionOri != null ? !professionOri.equals(that.professionOri) : that.professionOri != null)
            return false;
        if (professionNew != null ? !professionNew.equals(that.professionNew) : that.professionNew != null)
            return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = historyId != null ? historyId.hashCode() : 0;
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (contractNo != null ? contractNo.hashCode() : 0);
        result = 31 * result + (varyTime != null ? varyTime.hashCode() : 0);
        result = 31 * result + (deposit != null ? deposit.hashCode() : 0);
        result = 31 * result + (depositOri != null ? depositOri.hashCode() : 0);
        result = 31 * result + (depositNew != null ? depositNew.hashCode() : 0);
        result = 31 * result + (saleMode != null ? saleMode.hashCode() : 0);
        result = 31 * result + (saleModeOri != null ? saleModeOri.hashCode() : 0);
        result = 31 * result + (saleModeNew != null ? saleModeNew.hashCode() : 0);
        result = 31 * result + (signTime != null ? signTime.hashCode() : 0);
        result = 31 * result + (signTimeOri != null ? signTimeOri.hashCode() : 0);
        result = 31 * result + (signTimeNew != null ? signTimeNew.hashCode() : 0);
        result = 31 * result + (planFinishTime != null ? planFinishTime.hashCode() : 0);
        result = 31 * result + (planFinishTimeOri != null ? planFinishTimeOri.hashCode() : 0);
        result = 31 * result + (planFinishTimeNew != null ? planFinishTimeNew.hashCode() : 0);
        result = 31 * result + (deliveryLocus != null ? deliveryLocus.hashCode() : 0);
        result = 31 * result + (deliveryLocusOri != null ? deliveryLocusOri.hashCode() : 0);
        result = 31 * result + (deliveryLocusNew != null ? deliveryLocusNew.hashCode() : 0);
        result = 31 * result + (seller != null ? seller.hashCode() : 0);
        result = 31 * result + (sellerOri != null ? sellerOri.hashCode() : 0);
        result = 31 * result + (sellerNew != null ? sellerNew.hashCode() : 0);
        result = 31 * result + (visitorName != null ? visitorName.hashCode() : 0);
        result = 31 * result + (visitorNameOri != null ? visitorNameOri.hashCode() : 0);
        result = 31 * result + (visitorNameNew != null ? visitorNameNew.hashCode() : 0);
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (customerNameOri != null ? customerNameOri.hashCode() : 0);
        result = 31 * result + (customerNameNew != null ? customerNameNew.hashCode() : 0);
        result = 31 * result + (customerCertificateType != null ? customerCertificateType.hashCode() : 0);
        result = 31 * result + (customerCertificateTypeOri != null ? customerCertificateTypeOri.hashCode() : 0);
        result = 31 * result + (customerCertificateTypeNew != null ? customerCertificateTypeNew.hashCode() : 0);
        result = 31 * result + (customerCertificateNo != null ? customerCertificateNo.hashCode() : 0);
        result = 31 * result + (customerCertificateNoOri != null ? customerCertificateNoOri.hashCode() : 0);
        result = 31 * result + (customerCertificateNoNew != null ? customerCertificateNoNew.hashCode() : 0);
        result = 31 * result + (customerMobile != null ? customerMobile.hashCode() : 0);
        result = 31 * result + (customerMobileOri != null ? customerMobileOri.hashCode() : 0);
        result = 31 * result + (customerMobileNew != null ? customerMobileNew.hashCode() : 0);
        result = 31 * result + (profession != null ? profession.hashCode() : 0);
        result = 31 * result + (professionOri != null ? professionOri.hashCode() : 0);
        result = 31 * result + (professionNew != null ? professionNew.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
