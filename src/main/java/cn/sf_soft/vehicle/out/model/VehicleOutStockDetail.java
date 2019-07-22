package cn.sf_soft.vehicle.out.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 车辆出库-明细
 * @author  caigx
 *
 */
public class VehicleOutStockDetail  {
    private String outStockDetailId;
    private String outStockNo;
    private String warehouseName;
    private String warehouseId;
    private String vehicleId;
    private String vnoId;
    private String vehicleSalesCode;
    private String vehicleVno;
    private String vehicleName;
    private String vehicleStrain;
    private String vehicleColor;
    private String vehicleVin;
    private Byte vehicleKind;
    private String vehicleEngineType;
    private String vehicleEngineNo;
    private String vehicleEligibleNo;
    private Timestamp vehicleOutFactoryTime;
    private BigDecimal vehiclePrice;
    private BigDecimal vehicleCost;
    private BigDecimal vehicleCarriage;
    private Integer vehicleQuantity;
    private BigDecimal modifiedFee;
    private String errorFlag;
    private BigDecimal insuranceCost;
    private BigDecimal presentCost;
    private BigDecimal chargeCost;
    private String driveRoomNo;
    private BigDecimal carriage;
    private String oriDetailId;
    private Byte conversionStatus;
    private String vehicleSelfNo;
    private Boolean sellerBackFlag;
    private Boolean saleBackFlag;
    private Timestamp scanningOutStocks;
    private String gpsLocation;
    private String vehicleComment;
    private BigDecimal customerIntegral;
    private String vatdId;

    public String getOutStockDetailId() {
        return outStockDetailId;
    }

    public void setOutStockDetailId(String outStockDetailId) {
        this.outStockDetailId = outStockDetailId;
    }

    public String getOutStockNo() {
        return outStockNo;
    }

    public void setOutStockNo(String outStockNo) {
        this.outStockNo = outStockNo;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVnoId() {
        return vnoId;
    }

    public void setVnoId(String vnoId) {
        this.vnoId = vnoId;
    }

    public String getVehicleSalesCode() {
        return vehicleSalesCode;
    }

    public void setVehicleSalesCode(String vehicleSalesCode) {
        this.vehicleSalesCode = vehicleSalesCode;
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

    public String getVehicleVin() {
        return vehicleVin;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }

    public Byte getVehicleKind() {
        return vehicleKind;
    }

    public void setVehicleKind(Byte vehicleKind) {
        this.vehicleKind = vehicleKind;
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

    public BigDecimal getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(BigDecimal vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    public BigDecimal getVehicleCost() {
        return vehicleCost;
    }

    public void setVehicleCost(BigDecimal vehicleCost) {
        this.vehicleCost = vehicleCost;
    }

    public BigDecimal getVehicleCarriage() {
        return vehicleCarriage;
    }

    public void setVehicleCarriage(BigDecimal vehicleCarriage) {
        this.vehicleCarriage = vehicleCarriage;
    }

    public Integer getVehicleQuantity() {
        return vehicleQuantity;
    }

    public void setVehicleQuantity(Integer vehicleQuantity) {
        this.vehicleQuantity = vehicleQuantity;
    }

    public BigDecimal getModifiedFee() {
        return modifiedFee;
    }

    public void setModifiedFee(BigDecimal modifiedFee) {
        this.modifiedFee = modifiedFee;
    }

    public String getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(String errorFlag) {
        this.errorFlag = errorFlag;
    }

    public BigDecimal getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(BigDecimal insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    public BigDecimal getPresentCost() {
        return presentCost;
    }

    public void setPresentCost(BigDecimal presentCost) {
        this.presentCost = presentCost;
    }

    public BigDecimal getChargeCost() {
        return chargeCost;
    }

    public void setChargeCost(BigDecimal chargeCost) {
        this.chargeCost = chargeCost;
    }

    public String getDriveRoomNo() {
        return driveRoomNo;
    }

    public void setDriveRoomNo(String driveRoomNo) {
        this.driveRoomNo = driveRoomNo;
    }

    public BigDecimal getCarriage() {
        return carriage;
    }

    public void setCarriage(BigDecimal carriage) {
        this.carriage = carriage;
    }

    public String getOriDetailId() {
        return oriDetailId;
    }

    public void setOriDetailId(String oriDetailId) {
        this.oriDetailId = oriDetailId;
    }

    public Byte getConversionStatus() {
        return conversionStatus;
    }

    public void setConversionStatus(Byte conversionStatus) {
        this.conversionStatus = conversionStatus;
    }

    public String getVehicleSelfNo() {
        return vehicleSelfNo;
    }

    public void setVehicleSelfNo(String vehicleSelfNo) {
        this.vehicleSelfNo = vehicleSelfNo;
    }

    public Boolean getSellerBackFlag() {
        return sellerBackFlag;
    }

    public void setSellerBackFlag(Boolean sellerBackFlag) {
        this.sellerBackFlag = sellerBackFlag;
    }

    public Boolean getSaleBackFlag() {
        return saleBackFlag;
    }

    public void setSaleBackFlag(Boolean saleBackFlag) {
        this.saleBackFlag = saleBackFlag;
    }

    public Timestamp getScanningOutStocks() {
        return scanningOutStocks;
    }

    public void setScanningOutStocks(Timestamp scanningOutStocks) {
        this.scanningOutStocks = scanningOutStocks;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getVehicleComment() {
        return vehicleComment;
    }

    public void setVehicleComment(String vehicleComment) {
        this.vehicleComment = vehicleComment;
    }

    public BigDecimal getCustomerIntegral() {
        return customerIntegral;
    }

    public void setCustomerIntegral(BigDecimal customerIntegral) {
        this.customerIntegral = customerIntegral;
    }

    public String getVatdId() {
        return vatdId;
    }

    public void setVatdId(String vatdId) {
        this.vatdId = vatdId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleOutStockDetail that = (VehicleOutStockDetail) o;
        return Objects.equals(outStockDetailId, that.outStockDetailId) && Objects.equals(outStockNo, that.outStockNo) && Objects.equals(warehouseName, that.warehouseName) && Objects.equals(warehouseId, that.warehouseId) && Objects.equals(vehicleId, that.vehicleId) && Objects.equals(vnoId, that.vnoId) && Objects.equals(vehicleSalesCode, that.vehicleSalesCode) && Objects.equals(vehicleVno, that.vehicleVno) && Objects.equals(vehicleName, that.vehicleName) && Objects.equals(vehicleStrain, that.vehicleStrain) && Objects.equals(vehicleColor, that.vehicleColor) && Objects.equals(vehicleVin, that.vehicleVin) && Objects.equals(vehicleKind, that.vehicleKind) && Objects.equals(vehicleEngineType, that.vehicleEngineType) && Objects.equals(vehicleEngineNo, that.vehicleEngineNo) && Objects.equals(vehicleEligibleNo, that.vehicleEligibleNo) && Objects.equals(vehicleOutFactoryTime, that.vehicleOutFactoryTime) && Objects.equals(vehiclePrice, that.vehiclePrice) && Objects.equals(vehicleCost, that.vehicleCost) && Objects.equals(vehicleCarriage, that.vehicleCarriage) && Objects.equals(vehicleQuantity, that.vehicleQuantity) && Objects.equals(modifiedFee, that.modifiedFee) && Objects.equals(errorFlag, that.errorFlag) && Objects.equals(insuranceCost, that.insuranceCost) && Objects.equals(presentCost, that.presentCost) && Objects.equals(chargeCost, that.chargeCost) && Objects.equals(driveRoomNo, that.driveRoomNo) && Objects.equals(carriage, that.carriage) && Objects.equals(oriDetailId, that.oriDetailId) && Objects.equals(conversionStatus, that.conversionStatus) && Objects.equals(vehicleSelfNo, that.vehicleSelfNo) && Objects.equals(sellerBackFlag, that.sellerBackFlag) && Objects.equals(saleBackFlag, that.saleBackFlag) && Objects.equals(scanningOutStocks, that.scanningOutStocks) && Objects.equals(gpsLocation, that.gpsLocation) && Objects.equals(vehicleComment, that.vehicleComment) && Objects.equals(customerIntegral, that.customerIntegral) && Objects.equals(vatdId, that.vatdId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(outStockDetailId, outStockNo, warehouseName, warehouseId, vehicleId, vnoId, vehicleSalesCode, vehicleVno, vehicleName, vehicleStrain, vehicleColor, vehicleVin, vehicleKind, vehicleEngineType, vehicleEngineNo, vehicleEligibleNo, vehicleOutFactoryTime, vehiclePrice, vehicleCost, vehicleCarriage, vehicleQuantity, modifiedFee, errorFlag, insuranceCost, presentCost, chargeCost, driveRoomNo, carriage, oriDetailId, conversionStatus, vehicleSelfNo, sellerBackFlag, saleBackFlag, scanningOutStocks, gpsLocation, vehicleComment, customerIntegral, vatdId);
    }
}
