package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Auther: chenbiao
 * @Date: 2018/11/16 11:06
 * @Description:
 */
@Entity
@Table(name = "vw_vehicle_type")
@org.hibernate.annotations.Entity(mutable = false)
public class VwVehicleType {
    private String vnoId;
    private String vehicleName;
    private String commonNo;
    private String vehicleStrain;
    private String vehicleBrand;
    private String vehicleKindMeaning;
    private String fullName;
    private String fullNo;
    private String selfId;
    private String modelsId;
    private String productModel;
    private String engineType;
    private String ratio;
    private String transType;
    private String modelName;
    private String clutch;
    private String carType;
    private String rearType;
    private String tyre;
    private String engine;
    private String driverCab;
    private String cabType;
    private String frontAxle;
    private String ratioType;
    private String rearAxle;
    private String status;
    private String gearbox;
    private String masisModels;
    private String worktimeType;
    private String subStorageCode;
    private String factoryDealerName;
    private String subStorageName;
    private String color;
    private String colorDesc;
    private String underpanFlag;
    private String werksCode;
    private String subStorageFlag;
    private String productNo;
    private String isSaleModel;
    private String bulletinNo;
    private String shortName;
    private Boolean isPrimary;
    private Boolean isDongfeng;
    private String emissionStandard;
    private String variety;
    private String technologyLine;
    private String baseConfigure;
    private String vehicleFrame;
    private String rearAxleTonnage;
    private String rearAxleSupplier;
    private String rearAxleVelocityRatio;
    private String airConditioning;
    private String leafSpring;
    private String oilBox;
    private String wheelBase;
    private String boxLong;
    private String autoAdjustableArm;
    private String liftConfigure;
    private String navigationSystem;
    private String remark;
    private BigDecimal pricePurchase;
    private BigDecimal purchaseDiscountRatio;
    private BigDecimal purchaseDiscountAmount;
    private BigDecimal purchaseOtherAmount;
    private BigDecimal priceSale;
    private BigDecimal profitMin;
    private Timestamp createTime;
    private String creator;
    private Timestamp modifyTime;
    private String modifier;
    private String vehicleNameId;
    private Byte vehicleKind;
    private String errorCode;
    private String errorMsg;
    private String dfsUploadStatus;
    private String detailConfig;
    private BigDecimal priceSaleBigCustomer;
    private BigDecimal priceSaleTerminal;
    private BigDecimal priceSaleSecLvl;
    private Boolean isSellable;
    private BigDecimal referenceCost;

    @Id
    @Column(name = "vno_id")
    public String getVnoId() {
        return vnoId;
    }

    public void setVnoId(String vnoId) {
        this.vnoId = vnoId;
    }

    @Basic
    @Column(name = "vehicle_name")
    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Basic
    @Column(name = "common_no")
    public String getCommonNo() {
        return commonNo;
    }

    public void setCommonNo(String commonNo) {
        this.commonNo = commonNo;
    }

    @Basic
    @Column(name = "vehicleStrain")
    public String getVehicleStrain() {
        return vehicleStrain;
    }

    public void setVehicleStrain(String vehicleStrain) {
        this.vehicleStrain = vehicleStrain;
    }

    @Basic
    @Column(name = "vehicle_brand")
    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    @Basic
    @Column(name = "vehicle_kind_meaning")
    public String getVehicleKindMeaning() {
        return vehicleKindMeaning;
    }

    public void setVehicleKindMeaning(String vehicleKindMeaning) {
        this.vehicleKindMeaning = vehicleKindMeaning;
    }

    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "full_no")
    public String getFullNo() {
        return fullNo;
    }

    public void setFullNo(String fullNo) {
        this.fullNo = fullNo;
    }

    @Basic
    @Column(name = "self_id")
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Basic
    @Column(name = "models_id")
    public String getModelsId() {
        return modelsId;
    }

    public void setModelsId(String modelsId) {
        this.modelsId = modelsId;
    }

    @Basic
    @Column(name = "product_model")
    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    @Basic
    @Column(name = "engine_type")
    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    @Basic
    @Column(name = "ratio")
    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    @Basic
    @Column(name = "trans_type")
    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    @Basic
    @Column(name = "model_name")
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Basic
    @Column(name = "clutch")
    public String getClutch() {
        return clutch;
    }

    public void setClutch(String clutch) {
        this.clutch = clutch;
    }

    @Basic
    @Column(name = "car_type")
    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    @Basic
    @Column(name = "rear_type")
    public String getRearType() {
        return rearType;
    }

    public void setRearType(String rearType) {
        this.rearType = rearType;
    }

    @Basic
    @Column(name = "tyre")
    public String getTyre() {
        return tyre;
    }

    public void setTyre(String tyre) {
        this.tyre = tyre;
    }

    @Basic
    @Column(name = "engine")
    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @Basic
    @Column(name = "driver_cab")
    public String getDriverCab() {
        return driverCab;
    }

    public void setDriverCab(String driverCab) {
        this.driverCab = driverCab;
    }

    @Basic
    @Column(name = "cab_type")
    public String getCabType() {
        return cabType;
    }

    public void setCabType(String cabType) {
        this.cabType = cabType;
    }

    @Basic
    @Column(name = "front_axle")
    public String getFrontAxle() {
        return frontAxle;
    }

    public void setFrontAxle(String frontAxle) {
        this.frontAxle = frontAxle;
    }

    @Basic
    @Column(name = "ratio_type")
    public String getRatioType() {
        return ratioType;
    }

    public void setRatioType(String ratioType) {
        this.ratioType = ratioType;
    }

    @Basic
    @Column(name = "rear_axle")
    public String getRearAxle() {
        return rearAxle;
    }

    public void setRearAxle(String rearAxle) {
        this.rearAxle = rearAxle;
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
    @Column(name = "gearbox")
    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    @Basic
    @Column(name = "masis_models")
    public String getMasisModels() {
        return masisModels;
    }

    public void setMasisModels(String masisModels) {
        this.masisModels = masisModels;
    }

    @Basic
    @Column(name = "worktime_type")
    public String getWorktimeType() {
        return worktimeType;
    }

    public void setWorktimeType(String worktimeType) {
        this.worktimeType = worktimeType;
    }

    @Basic
    @Column(name = "sub_storage_code")
    public String getSubStorageCode() {
        return subStorageCode;
    }

    public void setSubStorageCode(String subStorageCode) {
        this.subStorageCode = subStorageCode;
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
    @Column(name = "sub_storage_name")
    public String getSubStorageName() {
        return subStorageName;
    }

    public void setSubStorageName(String subStorageName) {
        this.subStorageName = subStorageName;
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
    @Column(name = "color_desc")
    public String getColorDesc() {
        return colorDesc;
    }

    public void setColorDesc(String colorDesc) {
        this.colorDesc = colorDesc;
    }

    @Basic
    @Column(name = "underpan_flag")
    public String getUnderpanFlag() {
        return underpanFlag;
    }

    public void setUnderpanFlag(String underpanFlag) {
        this.underpanFlag = underpanFlag;
    }

    @Basic
    @Column(name = "werks_code")
    public String getWerksCode() {
        return werksCode;
    }

    public void setWerksCode(String werksCode) {
        this.werksCode = werksCode;
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
    @Column(name = "product_no")
    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    @Basic
    @Column(name = "is_sale_model")
    public String getIsSaleModel() {
        return isSaleModel;
    }

    public void setIsSaleModel(String isSaleModel) {
        this.isSaleModel = isSaleModel;
    }

    @Basic
    @Column(name = "bulletin_no")
    public String getBulletinNo() {
        return bulletinNo;
    }

    public void setBulletinNo(String bulletinNo) {
        this.bulletinNo = bulletinNo;
    }

    @Basic
    @Column(name = "short_name")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "is_primary")
    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    @Basic
    @Column(name = "is_dongfeng")
    public Boolean getDongfeng() {
        return isDongfeng;
    }

    public void setDongfeng(Boolean dongfeng) {
        isDongfeng = dongfeng;
    }

    @Basic
    @Column(name = "emission_standard")
    public String getEmissionStandard() {
        return emissionStandard;
    }

    public void setEmissionStandard(String emissionStandard) {
        this.emissionStandard = emissionStandard;
    }

    @Basic
    @Column(name = "variety")
    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    @Basic
    @Column(name = "technology_line")
    public String getTechnologyLine() {
        return technologyLine;
    }

    public void setTechnologyLine(String technologyLine) {
        this.technologyLine = technologyLine;
    }

    @Basic
    @Column(name = "base_configure")
    public String getBaseConfigure() {
        return baseConfigure;
    }

    public void setBaseConfigure(String baseConfigure) {
        this.baseConfigure = baseConfigure;
    }

    @Basic
    @Column(name = "vehicle_frame")
    public String getVehicleFrame() {
        return vehicleFrame;
    }

    public void setVehicleFrame(String vehicleFrame) {
        this.vehicleFrame = vehicleFrame;
    }

    @Basic
    @Column(name = "rear_axle_tonnage")
    public String getRearAxleTonnage() {
        return rearAxleTonnage;
    }

    public void setRearAxleTonnage(String rearAxleTonnage) {
        this.rearAxleTonnage = rearAxleTonnage;
    }

    @Basic
    @Column(name = "rear_axle_supplier")
    public String getRearAxleSupplier() {
        return rearAxleSupplier;
    }

    public void setRearAxleSupplier(String rearAxleSupplier) {
        this.rearAxleSupplier = rearAxleSupplier;
    }

    @Basic
    @Column(name = "rear_axle_velocity_ratio")
    public String getRearAxleVelocityRatio() {
        return rearAxleVelocityRatio;
    }

    public void setRearAxleVelocityRatio(String rearAxleVelocityRatio) {
        this.rearAxleVelocityRatio = rearAxleVelocityRatio;
    }

    @Basic
    @Column(name = "air_conditioning")
    public String getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(String airConditioning) {
        this.airConditioning = airConditioning;
    }

    @Basic
    @Column(name = "leaf_spring")
    public String getLeafSpring() {
        return leafSpring;
    }

    public void setLeafSpring(String leafSpring) {
        this.leafSpring = leafSpring;
    }

    @Basic
    @Column(name = "oil_box")
    public String getOilBox() {
        return oilBox;
    }

    public void setOilBox(String oilBox) {
        this.oilBox = oilBox;
    }

    @Basic
    @Column(name = "wheel_base")
    public String getWheelBase() {
        return wheelBase;
    }

    public void setWheelBase(String wheelBase) {
        this.wheelBase = wheelBase;
    }

    @Basic
    @Column(name = "box_long")
    public String getBoxLong() {
        return boxLong;
    }

    public void setBoxLong(String boxLong) {
        this.boxLong = boxLong;
    }

    @Basic
    @Column(name = "auto_adjustable_arm")
    public String getAutoAdjustableArm() {
        return autoAdjustableArm;
    }

    public void setAutoAdjustableArm(String autoAdjustableArm) {
        this.autoAdjustableArm = autoAdjustableArm;
    }

    @Basic
    @Column(name = "lift_configure")
    public String getLiftConfigure() {
        return liftConfigure;
    }

    public void setLiftConfigure(String liftConfigure) {
        this.liftConfigure = liftConfigure;
    }

    @Basic
    @Column(name = "navigation_system")
    public String getNavigationSystem() {
        return navigationSystem;
    }

    public void setNavigationSystem(String navigationSystem) {
        this.navigationSystem = navigationSystem;
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
    @Column(name = "price_purchase")
    public BigDecimal getPricePurchase() {
        return pricePurchase;
    }

    public void setPricePurchase(BigDecimal pricePurchase) {
        this.pricePurchase = pricePurchase;
    }

    @Basic
    @Column(name = "purchase_discount_ratio")
    public BigDecimal getPurchaseDiscountRatio() {
        return purchaseDiscountRatio;
    }

    public void setPurchaseDiscountRatio(BigDecimal purchaseDiscountRatio) {
        this.purchaseDiscountRatio = purchaseDiscountRatio;
    }

    @Basic
    @Column(name = "purchase_discount_amount")
    public BigDecimal getPurchaseDiscountAmount() {
        return purchaseDiscountAmount;
    }

    public void setPurchaseDiscountAmount(BigDecimal purchaseDiscountAmount) {
        this.purchaseDiscountAmount = purchaseDiscountAmount;
    }

    @Basic
    @Column(name = "purchase_other_amount")
    public BigDecimal getPurchaseOtherAmount() {
        return purchaseOtherAmount;
    }

    public void setPurchaseOtherAmount(BigDecimal purchaseOtherAmount) {
        this.purchaseOtherAmount = purchaseOtherAmount;
    }

    @Basic
    @Column(name = "price_sale")
    public BigDecimal getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(BigDecimal priceSale) {
        this.priceSale = priceSale;
    }

    @Basic
    @Column(name = "profit_min")
    public BigDecimal getProfitMin() {
        return profitMin;
    }

    public void setProfitMin(BigDecimal profitMin) {
        this.profitMin = profitMin;
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
    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
    @Column(name = "modifier")
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Basic
    @Column(name = "vehicle_name_id")
    public String getVehicleNameId() {
        return vehicleNameId;
    }

    public void setVehicleNameId(String vehicleNameId) {
        this.vehicleNameId = vehicleNameId;
    }

    @Basic
    @Column(name = "vehicle_kind")
    public Byte getVehicleKind() {
        return vehicleKind;
    }

    public void setVehicleKind(Byte vehicleKind) {
        this.vehicleKind = vehicleKind;
    }

    @Basic
    @Column(name = "error_code")
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Basic
    @Column(name = "error_msg")
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Basic
    @Column(name = "DFS_upload_status")
    public String getDfsUploadStatus() {
        return dfsUploadStatus;
    }

    public void setDfsUploadStatus(String dfsUploadStatus) {
        this.dfsUploadStatus = dfsUploadStatus;
    }

    @Basic
    @Column(name = "detail_config")
    public String getDetailConfig() {
        return detailConfig;
    }

    public void setDetailConfig(String detailConfig) {
        this.detailConfig = detailConfig;
    }

    @Basic
    @Column(name = "price_sale_big_customer")
    public BigDecimal getPriceSaleBigCustomer() {
        return priceSaleBigCustomer;
    }

    public void setPriceSaleBigCustomer(BigDecimal priceSaleBigCustomer) {
        this.priceSaleBigCustomer = priceSaleBigCustomer;
    }

    @Basic
    @Column(name = "price_sale_terminal")
    public BigDecimal getPriceSaleTerminal() {
        return priceSaleTerminal;
    }

    public void setPriceSaleTerminal(BigDecimal priceSaleTerminal) {
        this.priceSaleTerminal = priceSaleTerminal;
    }

    @Basic
    @Column(name = "price_sale_sec_lvl")
    public BigDecimal getPriceSaleSecLvl() {
        return priceSaleSecLvl;
    }

    public void setPriceSaleSecLvl(BigDecimal priceSaleSecLvl) {
        this.priceSaleSecLvl = priceSaleSecLvl;
    }

    @Basic
    @Column(name = "is_sellable")
    public Boolean getSellable() {
        return isSellable;
    }

    public void setSellable(Boolean sellable) {
        isSellable = sellable;
    }

    @Basic
    @Column(name = "reference_cost")
    public BigDecimal getReferenceCost() {
        return referenceCost;
    }

    public void setReferenceCost(BigDecimal referenceCost) {
        this.referenceCost = referenceCost;
    }
}
