package cn.sf_soft.vehicle.contract.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by henry on 2018/5/9.
 */
@Entity
@Table(name = "base_vehicle_model_catalog", schema = "dbo")
public class BaseVehicleModelCatalog {
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
    private String vehicleStrain;
    private Byte vehicleKind;
    private String errorCode;
    private String errorMsg;
    private String dfsUploadStatus;
    private String detailConfig;
    private BigDecimal priceSaleBigCustomer;
    private BigDecimal priceSaleTerminal;
    private BigDecimal priceSaleSecLvl;

    @Id
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
    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean primary) {
        isPrimary = primary;
    }

    @Basic
    @Column(name = "is_dongfeng")
    public Boolean getIsDongfeng() {
        return isDongfeng;
    }

    public void setIsDongfeng(Boolean dongfeng) {
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
    @Column(name = "vehicle_strain")
    public String getVehicleStrain() {
        return vehicleStrain;
    }

    public void setVehicleStrain(String vehicleStrain) {
        this.vehicleStrain = vehicleStrain;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseVehicleModelCatalog that = (BaseVehicleModelCatalog) o;

        if (selfId != null ? !selfId.equals(that.selfId) : that.selfId != null) return false;
        if (modelsId != null ? !modelsId.equals(that.modelsId) : that.modelsId != null) return false;
        if (productModel != null ? !productModel.equals(that.productModel) : that.productModel != null) return false;
        if (engineType != null ? !engineType.equals(that.engineType) : that.engineType != null) return false;
        if (ratio != null ? !ratio.equals(that.ratio) : that.ratio != null) return false;
        if (transType != null ? !transType.equals(that.transType) : that.transType != null) return false;
        if (modelName != null ? !modelName.equals(that.modelName) : that.modelName != null) return false;
        if (clutch != null ? !clutch.equals(that.clutch) : that.clutch != null) return false;
        if (carType != null ? !carType.equals(that.carType) : that.carType != null) return false;
        if (rearType != null ? !rearType.equals(that.rearType) : that.rearType != null) return false;
        if (tyre != null ? !tyre.equals(that.tyre) : that.tyre != null) return false;
        if (engine != null ? !engine.equals(that.engine) : that.engine != null) return false;
        if (driverCab != null ? !driverCab.equals(that.driverCab) : that.driverCab != null) return false;
        if (cabType != null ? !cabType.equals(that.cabType) : that.cabType != null) return false;
        if (frontAxle != null ? !frontAxle.equals(that.frontAxle) : that.frontAxle != null) return false;
        if (ratioType != null ? !ratioType.equals(that.ratioType) : that.ratioType != null) return false;
        if (rearAxle != null ? !rearAxle.equals(that.rearAxle) : that.rearAxle != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (gearbox != null ? !gearbox.equals(that.gearbox) : that.gearbox != null) return false;
        if (masisModels != null ? !masisModels.equals(that.masisModels) : that.masisModels != null) return false;
        if (worktimeType != null ? !worktimeType.equals(that.worktimeType) : that.worktimeType != null) return false;
        if (subStorageCode != null ? !subStorageCode.equals(that.subStorageCode) : that.subStorageCode != null)
            return false;
        if (factoryDealerName != null ? !factoryDealerName.equals(that.factoryDealerName) : that.factoryDealerName != null)
            return false;
        if (subStorageName != null ? !subStorageName.equals(that.subStorageName) : that.subStorageName != null)
            return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (colorDesc != null ? !colorDesc.equals(that.colorDesc) : that.colorDesc != null) return false;
        if (underpanFlag != null ? !underpanFlag.equals(that.underpanFlag) : that.underpanFlag != null) return false;
        if (werksCode != null ? !werksCode.equals(that.werksCode) : that.werksCode != null) return false;
        if (subStorageFlag != null ? !subStorageFlag.equals(that.subStorageFlag) : that.subStorageFlag != null)
            return false;
        if (productNo != null ? !productNo.equals(that.productNo) : that.productNo != null) return false;
        if (isSaleModel != null ? !isSaleModel.equals(that.isSaleModel) : that.isSaleModel != null) return false;
        if (bulletinNo != null ? !bulletinNo.equals(that.bulletinNo) : that.bulletinNo != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (emissionStandard != null ? !emissionStandard.equals(that.emissionStandard) : that.emissionStandard != null)
            return false;
        if (variety != null ? !variety.equals(that.variety) : that.variety != null) return false;
        if (technologyLine != null ? !technologyLine.equals(that.technologyLine) : that.technologyLine != null)
            return false;
        if (baseConfigure != null ? !baseConfigure.equals(that.baseConfigure) : that.baseConfigure != null)
            return false;
        if (vehicleFrame != null ? !vehicleFrame.equals(that.vehicleFrame) : that.vehicleFrame != null) return false;
        if (rearAxleTonnage != null ? !rearAxleTonnage.equals(that.rearAxleTonnage) : that.rearAxleTonnage != null)
            return false;
        if (rearAxleSupplier != null ? !rearAxleSupplier.equals(that.rearAxleSupplier) : that.rearAxleSupplier != null)
            return false;
        if (rearAxleVelocityRatio != null ? !rearAxleVelocityRatio.equals(that.rearAxleVelocityRatio) : that.rearAxleVelocityRatio != null)
            return false;
        if (airConditioning != null ? !airConditioning.equals(that.airConditioning) : that.airConditioning != null)
            return false;
        if (leafSpring != null ? !leafSpring.equals(that.leafSpring) : that.leafSpring != null) return false;
        if (oilBox != null ? !oilBox.equals(that.oilBox) : that.oilBox != null) return false;
        if (wheelBase != null ? !wheelBase.equals(that.wheelBase) : that.wheelBase != null) return false;
        if (boxLong != null ? !boxLong.equals(that.boxLong) : that.boxLong != null) return false;
        if (autoAdjustableArm != null ? !autoAdjustableArm.equals(that.autoAdjustableArm) : that.autoAdjustableArm != null)
            return false;
        if (liftConfigure != null ? !liftConfigure.equals(that.liftConfigure) : that.liftConfigure != null)
            return false;
        if (navigationSystem != null ? !navigationSystem.equals(that.navigationSystem) : that.navigationSystem != null)
            return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (pricePurchase != null ? !pricePurchase.equals(that.pricePurchase) : that.pricePurchase != null)
            return false;
        if (purchaseDiscountRatio != null ? !purchaseDiscountRatio.equals(that.purchaseDiscountRatio) : that.purchaseDiscountRatio != null)
            return false;
        if (purchaseDiscountAmount != null ? !purchaseDiscountAmount.equals(that.purchaseDiscountAmount) : that.purchaseDiscountAmount != null)
            return false;
        if (purchaseOtherAmount != null ? !purchaseOtherAmount.equals(that.purchaseOtherAmount) : that.purchaseOtherAmount != null)
            return false;
        if (priceSale != null ? !priceSale.equals(that.priceSale) : that.priceSale != null) return false;
        if (profitMin != null ? !profitMin.equals(that.profitMin) : that.profitMin != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (vehicleNameId != null ? !vehicleNameId.equals(that.vehicleNameId) : that.vehicleNameId != null)
            return false;
        if (vehicleStrain != null ? !vehicleStrain.equals(that.vehicleStrain) : that.vehicleStrain != null)
            return false;
        if (vehicleKind != null ? !vehicleKind.equals(that.vehicleKind) : that.vehicleKind != null) return false;
        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;
        if (errorMsg != null ? !errorMsg.equals(that.errorMsg) : that.errorMsg != null) return false;
        if (dfsUploadStatus != null ? !dfsUploadStatus.equals(that.dfsUploadStatus) : that.dfsUploadStatus != null)
            return false;
        if (detailConfig != null ? !detailConfig.equals(that.detailConfig) : that.detailConfig != null) return false;
        if (priceSaleBigCustomer != null ? !priceSaleBigCustomer.equals(that.priceSaleBigCustomer) : that.priceSaleBigCustomer != null)
            return false;
        if (priceSaleTerminal != null ? !priceSaleTerminal.equals(that.priceSaleTerminal) : that.priceSaleTerminal != null)
            return false;
        if (priceSaleSecLvl != null ? !priceSaleSecLvl.equals(that.priceSaleSecLvl) : that.priceSaleSecLvl != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = selfId != null ? selfId.hashCode() : 0;
        result = 31 * result + (modelsId != null ? modelsId.hashCode() : 0);
        result = 31 * result + (productModel != null ? productModel.hashCode() : 0);
        result = 31 * result + (engineType != null ? engineType.hashCode() : 0);
        result = 31 * result + (ratio != null ? ratio.hashCode() : 0);
        result = 31 * result + (transType != null ? transType.hashCode() : 0);
        result = 31 * result + (modelName != null ? modelName.hashCode() : 0);
        result = 31 * result + (clutch != null ? clutch.hashCode() : 0);
        result = 31 * result + (carType != null ? carType.hashCode() : 0);
        result = 31 * result + (rearType != null ? rearType.hashCode() : 0);
        result = 31 * result + (tyre != null ? tyre.hashCode() : 0);
        result = 31 * result + (engine != null ? engine.hashCode() : 0);
        result = 31 * result + (driverCab != null ? driverCab.hashCode() : 0);
        result = 31 * result + (cabType != null ? cabType.hashCode() : 0);
        result = 31 * result + (frontAxle != null ? frontAxle.hashCode() : 0);
        result = 31 * result + (ratioType != null ? ratioType.hashCode() : 0);
        result = 31 * result + (rearAxle != null ? rearAxle.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (gearbox != null ? gearbox.hashCode() : 0);
        result = 31 * result + (masisModels != null ? masisModels.hashCode() : 0);
        result = 31 * result + (worktimeType != null ? worktimeType.hashCode() : 0);
        result = 31 * result + (subStorageCode != null ? subStorageCode.hashCode() : 0);
        result = 31 * result + (factoryDealerName != null ? factoryDealerName.hashCode() : 0);
        result = 31 * result + (subStorageName != null ? subStorageName.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (colorDesc != null ? colorDesc.hashCode() : 0);
        result = 31 * result + (underpanFlag != null ? underpanFlag.hashCode() : 0);
        result = 31 * result + (werksCode != null ? werksCode.hashCode() : 0);
        result = 31 * result + (subStorageFlag != null ? subStorageFlag.hashCode() : 0);
        result = 31 * result + (productNo != null ? productNo.hashCode() : 0);
        result = 31 * result + (isSaleModel != null ? isSaleModel.hashCode() : 0);
        result = 31 * result + (bulletinNo != null ? bulletinNo.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (emissionStandard != null ? emissionStandard.hashCode() : 0);
        result = 31 * result + (variety != null ? variety.hashCode() : 0);
        result = 31 * result + (technologyLine != null ? technologyLine.hashCode() : 0);
        result = 31 * result + (baseConfigure != null ? baseConfigure.hashCode() : 0);
        result = 31 * result + (vehicleFrame != null ? vehicleFrame.hashCode() : 0);
        result = 31 * result + (rearAxleTonnage != null ? rearAxleTonnage.hashCode() : 0);
        result = 31 * result + (rearAxleSupplier != null ? rearAxleSupplier.hashCode() : 0);
        result = 31 * result + (rearAxleVelocityRatio != null ? rearAxleVelocityRatio.hashCode() : 0);
        result = 31 * result + (airConditioning != null ? airConditioning.hashCode() : 0);
        result = 31 * result + (leafSpring != null ? leafSpring.hashCode() : 0);
        result = 31 * result + (oilBox != null ? oilBox.hashCode() : 0);
        result = 31 * result + (wheelBase != null ? wheelBase.hashCode() : 0);
        result = 31 * result + (boxLong != null ? boxLong.hashCode() : 0);
        result = 31 * result + (autoAdjustableArm != null ? autoAdjustableArm.hashCode() : 0);
        result = 31 * result + (liftConfigure != null ? liftConfigure.hashCode() : 0);
        result = 31 * result + (navigationSystem != null ? navigationSystem.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (pricePurchase != null ? pricePurchase.hashCode() : 0);
        result = 31 * result + (purchaseDiscountRatio != null ? purchaseDiscountRatio.hashCode() : 0);
        result = 31 * result + (purchaseDiscountAmount != null ? purchaseDiscountAmount.hashCode() : 0);
        result = 31 * result + (purchaseOtherAmount != null ? purchaseOtherAmount.hashCode() : 0);
        result = 31 * result + (priceSale != null ? priceSale.hashCode() : 0);
        result = 31 * result + (profitMin != null ? profitMin.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        result = 31 * result + (vehicleNameId != null ? vehicleNameId.hashCode() : 0);
        result = 31 * result + (vehicleStrain != null ? vehicleStrain.hashCode() : 0);
        result = 31 * result + (vehicleKind != null ? vehicleKind.hashCode() : 0);
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        result = 31 * result + (errorMsg != null ? errorMsg.hashCode() : 0);
        result = 31 * result + (dfsUploadStatus != null ? dfsUploadStatus.hashCode() : 0);
        result = 31 * result + (detailConfig != null ? detailConfig.hashCode() : 0);
        result = 31 * result + (priceSaleBigCustomer != null ? priceSaleBigCustomer.hashCode() : 0);
        result = 31 * result + (priceSaleTerminal != null ? priceSaleTerminal.hashCode() : 0);
        result = 31 * result + (priceSaleSecLvl != null ? priceSaleSecLvl.hashCode() : 0);
        return result;
    }
}
