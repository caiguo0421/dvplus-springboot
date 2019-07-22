package cn.sf_soft.vehicle.certificate.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 车辆合格证历史
 *
 * @author caigu
 */
@Entity
@Table(name = "vehicle_certificate_history_info", schema = "dbo")
public class VehicleCertificateHistoryInfo implements java.io.Serializable {

    // Fields
    private String infoId;
    private String certificateId;
    private String infoNo;
    private String infoType;
    private Timestamp infoTime;
    private String stockId;
    private String creator;
    private Timestamp createTime;
    private String approver;
    private Timestamp approveTime;
    private String infoRemark;

    // Constructors
    public VehicleCertificateHistoryInfo() {
    }


    // Property accessors
    @Id
    @Column(name = "info_id", unique = true, nullable = false)
    public String getInfoId() {
        return this.infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    @Column(name = "certificate_id")
    public String getCertificateId() {
        return this.certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    @Column(name = "info_no")
    public String getInfoNo() {
        return this.infoNo;
    }

    public void setInfoNo(String infoNo) {
        this.infoNo = infoNo;
    }

    @Column(name = "info_type")
    public String getInfoType() {
        return this.infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    @Column(name = "info_time")
    public Timestamp getInfoTime() {
        return this.infoTime;
    }

    public void setInfoTime(Timestamp infoTime) {
        this.infoTime = infoTime;
    }

    @Column(name = "stock_id")
    public String getStockId() {
        return this.stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    @Column(name = "creator")
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "approver")
    public String getApprover() {
        return this.approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    @Column(name = "approve_time")
    public Timestamp getApproveTime() {
        return this.approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    @Column(name = "info_remark")
    public String getInfoRemark() {
        return this.infoRemark;
    }

    public void setInfoRemark(String infoRemark) {
        this.infoRemark = infoRemark;
    }

}