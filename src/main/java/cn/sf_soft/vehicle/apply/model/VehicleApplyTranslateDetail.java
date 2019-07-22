package cn.sf_soft.vehicle.apply.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 车辆请调-明细
 * @author :caigx
 *
 */
public class VehicleApplyTranslateDetail implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    private String detailId;
    private String documentNo;
    private String warehouseId;
    private String warehouseName;
    private String vehicleId;
    private String vehicleVin;
    private String underpanNo;
    private String vnoId;
    private String vehicleVno;
    private String vehicleName;
    private String vehicleStrain;
    private String vehicleColor;
    private String vehicleEngineType;
    private String vehicleEngineNo;
    private String vehicleEligibleNo;
    private Timestamp vehicleOutFactoryTime;
    private BigDecimal vehicleCost;
    private BigDecimal vehiclePrice;
    private BigDecimal vehicleCarriage;
    private BigDecimal modifiedFee;
    private Integer vehicleQuantity;
    private String vehicleComment;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleVin() {
        return vehicleVin;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }

    public String getUnderpanNo() {
        return underpanNo;
    }

    public void setUnderpanNo(String underpanNo) {
        this.underpanNo = underpanNo;
    }

    public String getVnoId() {
        return vnoId;
    }

    public void setVnoId(String vnoId) {
        this.vnoId = vnoId;
    }

    public String getVehicleVno() {
        return vehicleVno;
    }

    public void setVehicleVno(String vehicleVno) {
        this.vehicleVno = vehicleVno;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleStrain() {
        return vehicleStrain;
    }

    public void setVehicleStrain(String vehicleStrain) {
        this.vehicleStrain = vehicleStrain;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleEngineType() {
        return vehicleEngineType;
    }

    public void setVehicleEngineType(String vehicleEngineType) {
        this.vehicleEngineType = vehicleEngineType;
    }

    public String getVehicleEngineNo() {
        return vehicleEngineNo;
    }

    public void setVehicleEngineNo(String vehicleEngineNo) {
        this.vehicleEngineNo = vehicleEngineNo;
    }

    public String getVehicleEligibleNo() {
        return vehicleEligibleNo;
    }

    public void setVehicleEligibleNo(String vehicleEligibleNo) {
        this.vehicleEligibleNo = vehicleEligibleNo;
    }

    public Timestamp getVehicleOutFactoryTime() {
        return vehicleOutFactoryTime;
    }

    public void setVehicleOutFactoryTime(Timestamp vehicleOutFactoryTime) {
        this.vehicleOutFactoryTime = vehicleOutFactoryTime;
    }

    public BigDecimal getVehicleCost() {
        return vehicleCost;
    }

    public void setVehicleCost(BigDecimal vehicleCost) {
        this.vehicleCost = vehicleCost;
    }

    public BigDecimal getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(BigDecimal vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    public BigDecimal getVehicleCarriage() {
        return vehicleCarriage;
    }

    public void setVehicleCarriage(BigDecimal vehicleCarriage) {
        this.vehicleCarriage = vehicleCarriage;
    }

    public BigDecimal getModifiedFee() {
        return modifiedFee;
    }

    public void setModifiedFee(BigDecimal modifiedFee) {
        this.modifiedFee = modifiedFee;
    }

    public Integer getVehicleQuantity() {
        return vehicleQuantity;
    }

    public void setVehicleQuantity(Integer vehicleQuantity) {
        this.vehicleQuantity = vehicleQuantity;
    }

    public String getVehicleComment() {
        return vehicleComment;
    }

    public void setVehicleComment(String vehicleComment) {
        this.vehicleComment = vehicleComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleApplyTranslateDetail that = (VehicleApplyTranslateDetail) o;
        return Objects.equals(detailId, that.detailId) && Objects.equals(documentNo, that.documentNo) && Objects.equals(warehouseId, that.warehouseId) && Objects.equals(warehouseName, that.warehouseName) && Objects.equals(vehicleId, that.vehicleId) && Objects.equals(vehicleVin, that.vehicleVin) && Objects.equals(underpanNo, that.underpanNo) && Objects.equals(vnoId, that.vnoId) && Objects.equals(vehicleVno, that.vehicleVno) && Objects.equals(vehicleName, that.vehicleName) && Objects.equals(vehicleStrain, that.vehicleStrain) && Objects.equals(vehicleColor, that.vehicleColor) && Objects.equals(vehicleEngineType, that.vehicleEngineType) && Objects.equals(vehicleEngineNo, that.vehicleEngineNo) && Objects.equals(vehicleEligibleNo, that.vehicleEligibleNo) && Objects.equals(vehicleOutFactoryTime, that.vehicleOutFactoryTime) && Objects.equals(vehicleCost, that.vehicleCost) && Objects.equals(vehiclePrice, that.vehiclePrice) && Objects.equals(vehicleCarriage, that.vehicleCarriage) && Objects.equals(modifiedFee, that.modifiedFee) && Objects.equals(vehicleQuantity, that.vehicleQuantity) && Objects.equals(vehicleComment, that.vehicleComment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(detailId, documentNo, warehouseId, warehouseName, vehicleId, vehicleVin, underpanNo, vnoId, vehicleVno, vehicleName, vehicleStrain, vehicleColor, vehicleEngineType, vehicleEngineNo, vehicleEligibleNo, vehicleOutFactoryTime, vehicleCost, vehiclePrice, vehicleCarriage, modifiedFee, vehicleQuantity, vehicleComment);
    }
}
