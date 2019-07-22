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
@Table(name = "vehicle_DF_sap_contract")
public class VehicleDfSapContract {
    private String selfId;
    private String purchaseOrderNo;
    private String auditStatus;
    private String contractNo;
    private String contractType;
    private Timestamp createDate;
    private Timestamp endDate;
    private Timestamp postponedDate;
    private String postponedReason;
    private Integer quantity;
    private String refuseReason;
    private Timestamp startDate;
    private String status;
    private String submitCycle;

    @Id
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
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
    @Column(name = "audit_status")
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
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
    @Column(name = "contract_type")
    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
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
    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "postponed_date")
    public Timestamp getPostponedDate() {
        return postponedDate;
    }

    public void setPostponedDate(Timestamp postponedDate) {
        this.postponedDate = postponedDate;
    }

    @Basic
    @Column(name = "postponed_reason")
    public String getPostponedReason() {
        return postponedReason;
    }

    public void setPostponedReason(String postponedReason) {
        this.postponedReason = postponedReason;
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
    @Column(name = "refuse_reason")
    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    @Basic
    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
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
    @Column(name = "submit_cycle")
    public String getSubmitCycle() {
        return submitCycle;
    }

    public void setSubmitCycle(String submitCycle) {
        this.submitCycle = submitCycle;
    }

}
