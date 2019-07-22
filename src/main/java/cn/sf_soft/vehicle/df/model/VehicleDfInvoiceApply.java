package cn.sf_soft.vehicle.df.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/31 10:30
 * @Description:
 */
@Entity
@Table(name = "vehicle_DF_invoice_apply")
public class VehicleDfInvoiceApply {
    private String selfId;
    private String orderNo;
    private String sapContractNo;
    private String status;
    private Timestamp auditTime;
    private String auditComment;
    private Timestamp invoiceTime;
    private String invoiceComment;
    private Timestamp actualDeliveryDate;
    private String sapOrderNo;
    private String sendTo;
    private String pickAddress;
    private String remark;
    private Timestamp planDeliveryDate;
    private String applyType;
    private String transportWay;
    private String purchaseOrderNo;
    private Timestamp applyTime;
    private String applyLog;
    private Timestamp applyDeliveryDate;

    @Id
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Basic
    @Column(name = "order_no")
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Column(name = "sap_contract_no")
    public String getSapContractNo() {
        return sapContractNo;
    }

    public void setSapContractNo(String sapContractNo) {
        this.sapContractNo = sapContractNo;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "audit_time")
    public Timestamp getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Timestamp auditTime) {
        this.auditTime = auditTime;
    }

    @Basic
    @Column(name = "audit_comment")
    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

    @Basic
    @Column(name = "invoice_time")
    public Timestamp getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(Timestamp invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    @Basic
    @Column(name = "invoice_comment")
    public String getInvoiceComment() {
        return invoiceComment;
    }

    public void setInvoiceComment(String invoiceComment) {
        this.invoiceComment = invoiceComment;
    }

    @Basic
    @Column(name = "actual_delivery_date")
    public Timestamp getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(Timestamp actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
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
    @Column(name = "send_to")
    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    @Basic
    @Column(name = "pick_address")
    public String getPickAddress() {
        return pickAddress;
    }

    public void setPickAddress(String pickAddress) {
        this.pickAddress = pickAddress;
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
    @Column(name = "plan_delivery_date")
    public Timestamp getPlanDeliveryDate() {
        return planDeliveryDate;
    }

    public void setPlanDeliveryDate(Timestamp planDeliveryDate) {
        this.planDeliveryDate = planDeliveryDate;
    }

    @Basic
    @Column(name = "apply_type")
    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    @Basic
    @Column(name = "transport_way")
    public String getTransportWay() {
        return transportWay;
    }

    public void setTransportWay(String transportWay) {
        this.transportWay = transportWay;
    }

    @Basic
    @Column(name = "purchase_order_no")
    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
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
    @Column(name = "apply_log")
    public String getApplyLog() {
        return applyLog;
    }

    public void setApplyLog(String applyLog) {
        this.applyLog = applyLog;
    }

    @Basic
    @Column(name = "apply_delivery_date")
    public Timestamp getApplyDeliveryDate() {
        return applyDeliveryDate;
    }

    public void setApplyDeliveryDate(Timestamp applyDeliveryDate) {
        this.applyDeliveryDate = applyDeliveryDate;
    }

}
