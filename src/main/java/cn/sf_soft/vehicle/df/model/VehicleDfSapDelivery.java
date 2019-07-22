package cn.sf_soft.vehicle.df.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/31 11:24
 * @Description:
 */
@Entity
@Table(name = "vehicle_DF_sap_delivery")
public class VehicleDfSapDelivery {
    private String selfId;
    private String vehicleId;
    private String sapOrderNo;
    private Integer quantity;
    private Timestamp deliveryDate;
    private String deliveryNo;
    private Timestamp createDate;
    private String underpanNo;
    private Boolean finishStatus;
    private String crmDeliveryId;
    private String deliveryStationId;
    private Boolean isOut;
    private Byte comfirmStatus;
    private String comfirmPerson;
    private Timestamp comfirmTime;
    private String contractDetailId;
    private Timestamp invoiceDate;
    private String invoiceType;
    private BigDecimal invoiceAmount;
    private Timestamp futurePayDate;
    private String remark;

    @Id
    @Basic
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
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
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "delivery_date")
    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Basic
    @Column(name = "delivery_no")
    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    @Basic
    @Column(name = "create_date")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Column(name = "underpan_no")
    public String getUnderpanNo() {
        return underpanNo;
    }

    public void setUnderpanNo(String underpanNo) {
        this.underpanNo = underpanNo;
    }

    @Basic
    @Column(name = "finish_status")
    public Boolean getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(Boolean finishStatus) {
        this.finishStatus = finishStatus;
    }

    @Basic
    @Column(name = "crm_delivery_id")
    public String getCrmDeliveryId() {
        return crmDeliveryId;
    }

    public void setCrmDeliveryId(String crmDeliveryId) {
        this.crmDeliveryId = crmDeliveryId;
    }

    @Basic
    @Column(name = "delivery_station_id")
    public String getDeliveryStationId() {
        return deliveryStationId;
    }

    public void setDeliveryStationId(String deliveryStationId) {
        this.deliveryStationId = deliveryStationId;
    }

    @Basic
    @Column(name = "is_out")
    public Boolean getOut() {
        return isOut;
    }

    public void setOut(Boolean out) {
        isOut = out;
    }

    @Basic
    @Column(name = "comfirm_status")
    public Byte getComfirmStatus() {
        return comfirmStatus;
    }

    public void setComfirmStatus(Byte comfirmStatus) {
        this.comfirmStatus = comfirmStatus;
    }

    @Basic
    @Column(name = "comfirm_person")
    public String getComfirmPerson() {
        return comfirmPerson;
    }

    public void setComfirmPerson(String comfirmPerson) {
        this.comfirmPerson = comfirmPerson;
    }

    @Basic
    @Column(name = "comfirm_time")
    public Timestamp getComfirmTime() {
        return comfirmTime;
    }

    public void setComfirmTime(Timestamp comfirmTime) {
        this.comfirmTime = comfirmTime;
    }

    @Basic
    @Column(name = "contract_detail_id")
    public String getContractDetailId() {
        return contractDetailId;
    }

    public void setContractDetailId(String contractDetailId) {
        this.contractDetailId = contractDetailId;
    }

    @Basic
    @Column(name = "invoice_date")
    public Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
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
    @Column(name = "invoice_amount")
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    @Basic
    @Column(name = "future_pay_date")
    public Timestamp getFuturePayDate() {
        return futurePayDate;
    }

    public void setFuturePayDate(Timestamp futurePayDate) {
        this.futurePayDate = futurePayDate;
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
    @Column(name = "vehicle_id")
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
