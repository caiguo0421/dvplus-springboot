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
@Table(name = "vehicle_DF_sap_order")
public class VehicleDfSapOrder {
    private String selfId;
    private String sapOrderNo;
    private String sapContractNo;
    private Integer quantity;
    private String orderType;
    private String status;
    private String refOrderNo;
    private Timestamp createDate;
    private String sendTo;
    private String saleTo;
    private Integer monitorDays;

    @Id
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
    @Column(name = "sap_contract_no")
    public String getSapContractNo() {
        return sapContractNo;
    }

    public void setSapContractNo(String sapContractNo) {
        this.sapContractNo = sapContractNo;
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
    @Column(name = "order_type")
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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
    @Column(name = "ref_order_no")
    public String getRefOrderNo() {
        return refOrderNo;
    }

    public void setRefOrderNo(String refOrderNo) {
        this.refOrderNo = refOrderNo;
    }

    @Basic
    @Column(name = "create_date")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
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
    @Column(name = "sale_to")
    public String getSaleTo() {
        return saleTo;
    }

    public void setSaleTo(String saleTo) {
        this.saleTo = saleTo;
    }

    @Basic
    @Column(name = "monitor_days")
    public Integer getMonitorDays() {
        return monitorDays;
    }

    public void setMonitorDays(Integer monitorDays) {
        this.monitorDays = monitorDays;
    }

}
