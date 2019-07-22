package cn.sf_soft.vehicle.df.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/31 10:31
 * @Description:
 */
@Entity
@Table(name = "vehicle_DF_purchase_order")
public class VehicleDfPurchaseOrder {
    private String selfId;
    private String applyOrderNo;
    private String purchaseOrderNo;
    private Integer affirmedAmount;
    private Integer affirmingAmount;
    private String agentName;
    private String color;
    private String colorName;
    private String remark;
    private String linkman;
    private String linkmanPhone;
    private String deferenceFlag;
    private String expectCycle;
    private Timestamp expectDate;
    private String factoryDealerName;
    private String level;
    private Timestamp orderAffirmedTime;
    private Timestamp orderRepliedTime;
    private Integer quantity;
    private String status;
    private String subStorageFlag;
    private String transportTo;
    private String transportWay;
    private String vehicleVno;
    private String workStateAudit;
    private Integer matchedQuantity;

    @Id
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Basic
    @Column(name = "apply_order_no")
    public String getApplyOrderNo() {
        return applyOrderNo;
    }

    public void setApplyOrderNo(String applyOrderNo) {
        this.applyOrderNo = applyOrderNo;
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
    @Column(name = "affirmed_amount")
    public Integer getAffirmedAmount() {
        return affirmedAmount;
    }

    public void setAffirmedAmount(Integer affirmedAmount) {
        this.affirmedAmount = affirmedAmount;
    }

    @Basic
    @Column(name = "affirming_amount")
    public Integer getAffirmingAmount() {
        return affirmingAmount;
    }

    public void setAffirmingAmount(Integer affirmingAmount) {
        this.affirmingAmount = affirmingAmount;
    }

    @Basic
    @Column(name = "agent_name")
    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
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
    @Column(name = "color_name")
    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
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
    @Column(name = "linkman")
    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    @Basic
    @Column(name = "linkman_phone")
    public String getLinkmanPhone() {
        return linkmanPhone;
    }

    public void setLinkmanPhone(String linkmanPhone) {
        this.linkmanPhone = linkmanPhone;
    }

    @Basic
    @Column(name = "deference_flag")
    public String getDeferenceFlag() {
        return deferenceFlag;
    }

    public void setDeferenceFlag(String deferenceFlag) {
        this.deferenceFlag = deferenceFlag;
    }

    @Basic
    @Column(name = "expect_cycle")
    public String getExpectCycle() {
        return expectCycle;
    }

    public void setExpectCycle(String expectCycle) {
        this.expectCycle = expectCycle;
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
    @Column(name = "factory_dealer_name")
    public String getFactoryDealerName() {
        return factoryDealerName;
    }

    public void setFactoryDealerName(String factoryDealerName) {
        this.factoryDealerName = factoryDealerName;
    }

    @Basic
    @Column(name = "level")
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Basic
    @Column(name = "order_affirmed_time")
    public Timestamp getOrderAffirmedTime() {
        return orderAffirmedTime;
    }

    public void setOrderAffirmedTime(Timestamp orderAffirmedTime) {
        this.orderAffirmedTime = orderAffirmedTime;
    }

    @Basic
    @Column(name = "order_replied_time")
    public Timestamp getOrderRepliedTime() {
        return orderRepliedTime;
    }

    public void setOrderRepliedTime(Timestamp orderRepliedTime) {
        this.orderRepliedTime = orderRepliedTime;
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
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "sub_storage_flag")
    public String getSubStorageFlag() {
        return subStorageFlag;
    }

    public void setSubStorageFlag(String subStorageFlag) {
        this.subStorageFlag = subStorageFlag;
    }

    @Basic
    @Column(name = "transport_to")
    public String getTransportTo() {
        return transportTo;
    }

    public void setTransportTo(String transportTo) {
        this.transportTo = transportTo;
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
    @Column(name = "vehicle_vno")
    public String getVehicleVno() {
        return vehicleVno;
    }

    public void setVehicleVno(String vehicleVno) {
        this.vehicleVno = vehicleVno;
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
    @Column(name = "matched_quantity")
    public Integer getMatchedQuantity() {
        return matchedQuantity;
    }

    public void setMatchedQuantity(Integer matchedQuantity) {
        this.matchedQuantity = matchedQuantity;
    }

}
