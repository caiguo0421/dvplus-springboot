package cn.sf_soft.vehicle.purchase.model;

import cn.sf_soft.office.approval.model.ApproveDocuments;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/30 10:54
 * @Description:
 */
@Entity
@Table(name = "vehicle_purchase_order")
public class VehiclePurchaseOrder extends ApproveDocuments implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String selfId;
    private Byte orderType;
    private String vnoId;
    private String color;
    private Timestamp expectDate;
    private Integer vehicleQuantity;
    private Integer declareQuantity;
    private Integer abortQuantity;
    private BigDecimal purchasePrice;
    private BigDecimal purchasePriceDiff;
    private String saleContractGroupId;
    private String saleContractNo;
    private String engine;
    private BigDecimal vehicleWeight;
    private BigDecimal curbWeight;
    private BigDecimal registrationTonnage;
    private String containerSize;
    private String containerInsideSize;
    private String tyreModel;
    private String tread;
    private String leafSpringNumber;
    private Integer peopleNumber;
    private Timestamp deliveryTime;
    private String deliveryAddress;
    private BigDecimal cargoWeight;
    private String bulletinId;
    private String bulletinNo;
    private String workState;
    private String remark;
    //    private String userId;
//    private String userNo;
//    private String userName;
//    private String departmentId;
//    private String departmentNo;
//    private String departmentName;
//    private String submitStationId;
//    private String submitStationName;
//    private Timestamp submitTime;
//    private String approverId;
//    private String approverNo;
//    private String approverName;
//    private Timestamp approveTime;
    private String creator;
    private Timestamp createTime;
    private String modifier;


    /**
     * 准牵引质量
     */
    private BigDecimal tractiveTonnage;

    /**
     * 整车尺寸
     */
    private String vehicleSize;

    /**
     * 轴距
     */
    private String wheelBase;


    /**
     * 最高车速
     */
    private String maxSpeed;

    /**
     * 送达地
     */
    private String transportTo;


    @Id
    @Column(name = "self_id", nullable = false)
    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Basic
    @Column(name = "document_no", nullable = false)
    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Basic
    @Column(name = "order_type", nullable = true)
    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Basic
    @Column(name = "vno_id", nullable = true, length = 40)
    public String getVnoId() {
        return vnoId;
    }

    public void setVnoId(String vnoId) {
        this.vnoId = vnoId;
    }

    @Basic
    @Column(name = "color", nullable = true, length = 10)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Basic
    @Column(name = "expect_date", nullable = true)
    public Timestamp getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Timestamp expectDate) {
        this.expectDate = expectDate;
    }

    @Basic
    @Column(name = "vehicle_quantity", nullable = true)
    public Integer getVehicleQuantity() {
        return vehicleQuantity;
    }

    public void setVehicleQuantity(Integer vehicleQuantity) {
        this.vehicleQuantity = vehicleQuantity;
    }

    @Basic
    @Column(name = "declare_quantity", nullable = true)
    public Integer getDeclareQuantity() {
        return declareQuantity;
    }

    public void setDeclareQuantity(Integer declareQuantity) {
        this.declareQuantity = declareQuantity;
    }

    @Basic
    @Column(name = "abort_quantity", nullable = true)
    public Integer getAbortQuantity() {
        return abortQuantity;
    }

    public void setAbortQuantity(Integer abortQuantity) {
        this.abortQuantity = abortQuantity;
    }

    @Basic
    @Column(name = "purchase_price", nullable = true, precision = 2)
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Basic
    @Column(name = "purchase_price_diff", nullable = true, precision = 2)
    public BigDecimal getPurchasePriceDiff() {
        return purchasePriceDiff;
    }

    public void setPurchasePriceDiff(BigDecimal purchasePriceDiff) {
        this.purchasePriceDiff = purchasePriceDiff;
    }

    @Basic
    @Column(name = "sale_contract_group_id", nullable = true, length = 40)
    public String getSaleContractGroupId() {
        return saleContractGroupId;
    }

    public void setSaleContractGroupId(String saleContractGroupId) {
        this.saleContractGroupId = saleContractGroupId;
    }

    @Basic
    @Column(name = "sale_contract_no", nullable = true, length = 60)
    public String getSaleContractNo() {
        return saleContractNo;
    }

    public void setSaleContractNo(String saleContractNo) {
        this.saleContractNo = saleContractNo;
    }

    @Basic
    @Column(name = "engine", nullable = true, length = 60)
    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @Basic
    @Column(name = "vehicle_weight", nullable = true, precision = 2)
    public BigDecimal getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(BigDecimal vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }

    @Basic
    @Column(name = "curb_weight", nullable = true, precision = 2)
    public BigDecimal getCurbWeight() {
        return curbWeight;
    }

    public void setCurbWeight(BigDecimal curbWeight) {
        this.curbWeight = curbWeight;
    }

    @Basic
    @Column(name = "registration_tonnage", nullable = true, precision = 2)
    public BigDecimal getRegistrationTonnage() {
        return registrationTonnage;
    }

    public void setRegistrationTonnage(BigDecimal registrationTonnage) {
        this.registrationTonnage = registrationTonnage;
    }

    @Basic
    @Column(name = "container_size", nullable = true, length = 40)
    public String getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(String containerSize) {
        this.containerSize = containerSize;
    }

    @Basic
    @Column(name = "tyre_model", nullable = true, length = 40)
    public String getTyreModel() {
        return tyreModel;
    }

    public void setTyreModel(String tyreModel) {
        this.tyreModel = tyreModel;
    }

    @Basic
    @Column(name = "work_state", nullable = true, length = 20)
    public String getWorkState() {
        return workState;
    }

    public void setWorkState(String workState) {
        this.workState = workState;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 2147483647)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "user_id", nullable = true, length = 40)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_no", nullable = true, length = 10)
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 10)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "department_id", nullable = true, length = 40)
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "department_no", nullable = true, length = 40)
    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    @Basic
    @Column(name = "department_name", nullable = true, length = 40)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "submit_station_id", nullable = true, length = 10)
    public String getSubmitStationId() {
        return submitStationId;
    }

    public void setSubmitStationId(String submitStationId) {
        this.submitStationId = submitStationId;
    }

    @Basic
    @Column(name = "submit_station_name", nullable = true, length = 10)
    public String getSubmitStationName() {
        return submitStationName;
    }

    public void setSubmitStationName(String submitStationName) {
        this.submitStationName = submitStationName;
    }

    @Basic
    @Column(name = "submit_time", nullable = true)
    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    @Basic
    @Column(name = "approver_id", nullable = true, length = 2147483647)
    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    @Basic
    @Column(name = "approver_no", nullable = true, length = 2147483647)
    public String getApproverNo() {
        return approverNo;
    }

    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    @Basic
    @Column(name = "approver_name", nullable = true, length = 2147483647)
    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    @Basic
    @Column(name = "approve_time", nullable = true)
    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    @Basic
    @Column(name = "creator", nullable = true, length = 20)
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modifier", nullable = true, length = 20)
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Basic
    @Column(name = "modify_time", nullable = true)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }


    @Basic
    @Column(name = "station_id", nullable = true)
    @Override
    public String getStationId() {
        return super.getStationId();
    }

    @Override
    public void setStationId(String stationId) {
        super.setStationId(stationId);
    }

    @Basic
    @Column(name = "cargo_weight", nullable = true)
    public BigDecimal getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(BigDecimal cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    @Basic
    @Column(name = "bulletin_id", nullable = true)
    public String getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(String bulletinId) {
        this.bulletinId = bulletinId;
    }

    @Basic
    @Column(name = "bulletin_no", nullable = true)
    public String getBulletinNo() {
        return bulletinNo;
    }

    public void setBulletinNo(String bulletinNo) {
        this.bulletinNo = bulletinNo;
    }

    @Column(name = "container_inside_size")
    public String getContainerInsideSize() {
        return containerInsideSize;
    }

    public void setContainerInsideSize(String containerInsideSize) {
        this.containerInsideSize = containerInsideSize;
    }

    @Column(name = "tread")
    public String getTread() {
        return tread;
    }

    public void setTread(String tread) {
        this.tread = tread;
    }

    @Column(name = "leaf_spring_number")
    public String getLeafSpringNumber() {
        return leafSpringNumber;
    }

    public void setLeafSpringNumber(String leafSpringNumber) {
        this.leafSpringNumber = leafSpringNumber;
    }

    @Column(name = "people_number")
    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    @Column(name = "delivery_time")
    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Column(name = "delivery_address")
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Column(name = "tractive_tonnage")
    public BigDecimal getTractiveTonnage() {
        return tractiveTonnage;
    }

    public void setTractiveTonnage(BigDecimal tractiveTonnage) {
        this.tractiveTonnage = tractiveTonnage;
    }

    @Column(name = "vehicle_size")
    public String getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(String vehicleSize) {
        this.vehicleSize = vehicleSize;
    }

    @Column(name = "wheel_base")
    public String getWheelBase() {
        return wheelBase;
    }

    public void setWheelBase(String wheelBase) {
        this.wheelBase = wheelBase;
    }

    @Column(name = "max_speed")
    public String getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Column(name = "transport_to")
    public String getTransportTo() {
        return transportTo;
    }

    public void setTransportTo(String transportTo) {
        this.transportTo = transportTo;
    }
}
