package cn.sf_soft.vehicle.certificate.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 车辆合格证库存
 *
 * @author caigu
 */
@Entity
@Table(name = "vehicle_certificate_stocks", schema = "dbo")
public class VehicleCertificateStocks implements java.io.Serializable {


    // Fields
    private String certificateId;
    private String certificateNo;
    private Timestamp certificateTime;
    private String vehicleId;
    private String vehicleVin;
    private String stockId;
    private String stockName;
    private String objectId;
    private String objectNo;
    private String objectName;
    private String stationId;
    private Short status;
    private Boolean isUpload;
    private String remark;
    private String sapDeliveryId;


    // Constructors


    public VehicleCertificateStocks() {
    }


    @Id
    @Column(name = "certificate_id", unique = true, nullable = false, length = 40)
    public String getCertificateId() {
        return this.certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    @Column(name = "certificate_no")
    public String getCertificateNo() {
        return this.certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    @Column(name = "certificate_time")
    public Timestamp getCertificateTime() {
        return this.certificateTime;
    }

    public void setCertificateTime(Timestamp certificateTime) {
        this.certificateTime = certificateTime;
    }

    @Column(name = "vehicle_id")
    public String getVehicleId() {
        return this.vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Column(name = "vehicle_vin")
    public String getVehicleVin() {
        return this.vehicleVin;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }

    @Column(name = "stock_id")
    public String getStockId() {
        return this.stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    @Column(name = "stock_name")
    public String getStockName() {
        return this.stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    @Column(name = "object_id")
    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Column(name = "object_no")
    public String getObjectNo() {
        return this.objectNo;
    }

    public void setObjectNo(String objectNo) {
        this.objectNo = objectNo;
    }

    @Column(name = "object_name")
    public String getObjectName() {
        return this.objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Column(name = "station_id")
    public String getStationId() {
        return this.stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Column(name = "status")
    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Column(name = "is_upload")
    public Boolean getIsUpload() {
        return this.isUpload;
    }

    public void setIsUpload(Boolean isUpload) {
        this.isUpload = isUpload;
    }

    @Column(name = "remark")
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "sap_delivery_id")
    public String getSapDeliveryId() {
        return this.sapDeliveryId;
    }

    public void setSapDeliveryId(String sapDeliveryId) {
        this.sapDeliveryId = sapDeliveryId;
    }


}