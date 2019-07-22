package cn.sf_soft.vehicle.purchase.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: chenbiao
 * @Date: 2018/9/25 16:30
 * @Description:
 */
@Entity
@Table(name = "vehicle_purchase_contract")
public class VehiclePurchaseContract {
    private String contractId;
    private String contractNo;
    private String stationId;
    private String contractCode;
    private Byte status = 0;
    private String supplierId;
    private String supplierNo;
    private String supplierName;
    private String vnoId;
    private String color;
    private BigDecimal purchasePrice;
    private BigDecimal contractAmount;
    private Integer declareQuantity;
    private Integer affirmedQuantity;
    private Timestamp expectDate;
    private String dfOrderStatus;
    private Byte payType;
    private String dfPurchaseOrderNo;
    private String sapOrderNo;
    private String workStateAudit;
    private String applicant;
    private Timestamp applyTime;
    private String remark;
    private String creator;
    private Timestamp createTime;
    private String modifier;
    private Timestamp modifyTime;
    private String confirmPerson;
    private Timestamp confirmTime;
    private List<VehiclePurchaseContractDetail> details;

    @Id
    @Column(name = "contract_id")
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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
    @Column(name = "station_id")
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "contract_code")
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "supplier_id")
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Basic
    @Column(name = "supplier_no")
    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    @Basic
    @Column(name = "supplier_name")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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
    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Basic
    @Column(name = "purchase_price")
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Basic
    @Column(name = "contract_amount")
    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    @Basic
    @Column(name = "declare_quantity")
    public Integer getDeclareQuantity() {
        return declareQuantity;
    }

    public void setDeclareQuantity(Integer declareQuantity) {
        this.declareQuantity = declareQuantity;
    }

    @Basic
    @Column(name = "affirmed_quantity")
    public Integer getAffirmedQuantity() {
        return affirmedQuantity;
    }

    public void setAffirmedQuantity(Integer affirmedQuantity) {
        this.affirmedQuantity = affirmedQuantity;
    }

    @Basic
    @Column(name = "expect_date")
    public Timestamp getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Timestamp expectDate) {
        this.expectDate = expectDate;
    }

    @Basic
    @Column(name = "df_order_status")
    public String getDfOrderStatus() {
        return dfOrderStatus;
    }

    public void setDfOrderStatus(String dfOrderStatus) {
        this.dfOrderStatus = dfOrderStatus;
    }

    @Basic
    @Column(name = "pay_type")
    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    @Basic
    @Column(name = "df_purchase_order_no")
    public String getDfPurchaseOrderNo() {
        return dfPurchaseOrderNo;
    }

    public void setDfPurchaseOrderNo(String dfPurchaseOrderNo) {
        this.dfPurchaseOrderNo = dfPurchaseOrderNo;
    }

    @Basic
    @Column(name = "sap_order_no")
    public String getSapOrderNo() {
        return sapOrderNo;
    }

    public void setSapOrderNo(String sapOrderNo) {
        this.sapOrderNo = sapOrderNo;
    }

    @Basic
    @Column(name = "work_state_audit")
    public String getWorkStateAudit() {
        return workStateAudit;
    }

    public void setWorkStateAudit(String workStateAudit) {
        this.workStateAudit = workStateAudit;
    }

    @Basic
    @Column(name = "applicant")
    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    @Basic
    @Column(name = "apply_time")
    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
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
    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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
    @Column(name = "modify_time")
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "confirm_person")
    public String getConfirmPerson() {
        return confirmPerson;
    }

    public void setConfirmPerson(String confirmPerson) {
        this.confirmPerson = confirmPerson;
    }

    @Basic
    @Column(name = "confirm_time")
    public Timestamp getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Timestamp confirmTime) {
        this.confirmTime = confirmTime;
    }

}
