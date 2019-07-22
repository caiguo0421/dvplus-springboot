package cn.sf_soft.office.approval.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2019/7/15 16:20
 * @Description:
 */
@Entity
@Table(name = "service_work_lists")
public class ServiceWorkLists extends ApproveDocuments<Object> {
    private String taskNo;
    private String oriTaskNo;
    private String taskType;
    private Byte taskStatus;
    private Byte taskMode;
    private String receiver;
    private Timestamp enterTime;
    private Timestamp endTimePlan;
    private String bookingNo;
    private String conversionNo;
    private String vehicleMaintainNo;
    private String vehicleFactoryNo;
    private String driveRoomNo;
    private String workPlace;
    private Byte servicePlace;
    private String outServiceAddress;
    private BigDecimal outServiceMileage;
    private String remark;
    private String troubleDescription;
    private String vehicleId;
    private String vehicleCardNo;
    private String vehicleSalesCode;
    private String vehicleVno;
    private String vehicleName;
    private String vehicleColor;
    private String vehicleVin;
    private String vehicleEngineNo;
    private String vehicleEngineType;
    private Timestamp vehiclePurchaseTime;
    private Timestamp vehicleOutFactoryTime;
    private Boolean vehiclePurchaseFlag;
    private Timestamp repairTime;
    private BigDecimal repairMileage;
    private BigDecimal lastServiceMileage;
    private Timestamp lastServiceTime;
    private BigDecimal enterMilege;
    private Timestamp claimEndTime;
    private BigDecimal claimMilege;
    private String customerId;
    private String customerNo;
    private String customerName;
    private String customerLinkman;
    private String customerMobile;
    private String memberNo;
    private String memberType;
    private Timestamp memberEndTime;
    private String linkman;
    private String phone;
    private String mobile;
    private String address;
    private String remark2;
    private String remark3;
    private String imagesUrls;
    private Byte paymentStatus;
    private Byte workStatus;
    private String workPlaceId;
    private String workPlaceNo;
    private String workPlaceName;
    private BigDecimal itemsPrice;
    private BigDecimal partsPrice;
    private BigDecimal itemsPriceClaim;
    private BigDecimal partsPriceClaim;
    private BigDecimal itemsPriceInsurance;
    private BigDecimal partsPriceInsurance;
    private BigDecimal itemsPriceInner;
    private BigDecimal partsPriceInner;
    private BigDecimal factItemsPrice;
    private BigDecimal factPartsPrice;
    private BigDecimal claimTot;
    private BigDecimal claimTotApply;
    private BigDecimal insuranceTot;
    private BigDecimal innerTot;
    private BigDecimal guaranteeMoney;
    private BigDecimal insuranceClaimItem;
    private BigDecimal insuranceClaimPart;
    private BigDecimal insuranceApproveItem;
    private BigDecimal insuranceApprovePart;
    private String insuranceClaimNo;
    private String insuranceClaimer;
    private String insuranceApprover;
    private Timestamp insuranceClaimTime;
    private Timestamp insuranceApproveTime;
    private String insuranceFactoryId;
    private String insuranceFactoryNo;
    private String insuranceFactoryName;
    private String insuranceRemark;
    private Byte insurancePayType;
    // private String stationId;
    private String creator;
    private Timestamp createTime;
    private String modifier;
    // private Timestamp modifyTime;
    private Timestamp endTime;
    private String censor;
    private String workPostil;
    private String balancer;
    private Timestamp balanceTime;
    private String balancerAgainst;
    private Timestamp balanceTimeAgainst;
    private Integer addFrequency;
    private Byte receiverPoint;
    private Byte workGroupPoint;
    private Byte partDepartmentPoint;
    private Byte claimsStatus;
    private Byte insuranceStatus;
    private BigDecimal partsAgio;
    private BigDecimal itemsAgio;
    private BigDecimal vehicleExpenseTot;
    private Byte callBackSuccess;
    private Byte callBackResult;
    private String callBackAbstract;
    private Byte callBackFinalResult;
    private String callBackRemark;
    private Boolean callBackFlag;
    private String callBackDealRemark;
    private String callBackFailReason;
    private Timestamp backTime;
    private Byte resrvStatus;
    private String resrvMan;
    private Timestamp resrvDate;
    private String resrvReason;
    private String resrvDesc;
    private Byte resrvStatusDeal;
    private String resrvDoMan;
    private Timestamp resrvDoDate;
    private String resrvDoDesc;
    private BigDecimal ticketMoneyFace;
    private BigDecimal ticketMoneyCanuse;
    private String supplierId;
    private String supplierNo;
    private String supplierName;
    private String stationIdDelegate;
    private String checker;
    private Timestamp checkTime;
    private BigDecimal maintainMileageNext;
    private Timestamp maintainDateNext;
    private Byte taskKind;
    private BigDecimal priceTicket;
    private Boolean vehicleBelongTo;
    private BigDecimal otherTot;
    private String belongToSupplierId;
    private String belongToSupplierNo;
    private String belongToSupplierName;
    private BigDecimal itemsPriceTicket;
    private BigDecimal partsPriceTicket;
    private BigDecimal ticketTot;
    private BigDecimal feePrice;
    private BigDecimal feePriceInner;
    private BigDecimal feePriceClaim;
    private BigDecimal feePriceInsurance;
    private BigDecimal feePriceTicket;
    private String outDriver;
    private String vehicleSaleCode;
    private String agioModifier;
    private String vehicleStrain;
    private String callBackHandleDepartment;
    private String callBackEmpId;
    private String callBackEmpName;
    private String insuranceReceiver;
    private String lowForecastReason;
    private String lowApproveReason;
    private BigDecimal partManagePercent;
    private String sendMsgPerson;
    private Timestamp sendMsgTime;
    private String receiverNo;
    private String creatorNo;
    private String creatorUnitNo;
    private String creatorUnitName;
    private String balancerNo;
    private String balancerUnitNo;
    private String balancerUnitName;
    private BigDecimal totTaxMoney;
    private BigDecimal totNoTaxMoney;
    private BigDecimal totTax;
    private String insuranceCompanyId;
    private String insuranceCompanyNo;
    private String insuranceCompanyName;
    private BigDecimal insuranceClaimConsigns;
    private BigDecimal insuranceClaimCharge;
    private BigDecimal insuranceApproveConsigns;
    private BigDecimal insuranceApproveCharge;
    private BigDecimal consignsPriceInsurance;
    private BigDecimal chargePriceInsurance;
    private String drivingLicenseNumber;
    private String errorCode;
    private String errorMsg;
    private Boolean isSecondhand;
    private String vehicleCardModel;
    private String vehicleWeight;
    private String wechatEvaluationCode;
    private BigDecimal companyTicket;
    private Byte dfsPaymentMethod;
    private String resrvDoMode;
    private BigDecimal oilAgio;
    private BigDecimal filterAgio;
    private Boolean isModifyTotalAmount;
    private String receiverId;
    private Timestamp goOutDepartureTime;
    private Timestamp goOutReturnTime;
    private String appraisalInfo;
    private String additionalSummary;
    private String parentMaintenanceNum;
    private String preCheckSummary;
    private String serviceNoticeNumber;
    private String userType;
    private String subType;
    private String crmRepairOrder;
    // private String documentNo;
    // private Byte status;
    // private String userId;
    // private String userNo;
    // private String userName;
    // private String departmentId;
    // private String departmentNo;
    // private String departmentName;
    private Byte submitStatus;
    // private Timestamp submitTime;
    // private String submitStationId;
    // private String submitStationName;
    private BigDecimal serviceItemAgioMin;
    private BigDecimal taskTotalAgio;
    private BigDecimal taskAgioMoney;
    // private String approverId;
    // private String approverNo;
    // private String approverName;
    // private Timestamp approveTime;

    // 维修项目
    private List<ServiceWorkListItems> items;
    // 配件
    private List<ServiceWorkListParts> parts;
    // 外委项目
    private List<ServiceWorkListConsigns> consigns;
    //费用信息
    private List<ServiceWorkListsCharge> charges;
    // 建议项目
    private List<ServiceWorkListProposedItems> proposedItems;

    @Id
    @Column(name = "task_no")
    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    @Basic
    @Column(name = "ori_task_no")
    public String getOriTaskNo() {
        return oriTaskNo;
    }

    public void setOriTaskNo(String oriTaskNo) {
        this.oriTaskNo = oriTaskNo;
    }

    @Basic
    @Column(name = "task_type")
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @Basic
    @Column(name = "task_status")
    public Byte getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Byte taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Basic
    @Column(name = "task_mode")
    public Byte getTaskMode() {
        return taskMode;
    }

    public void setTaskMode(Byte taskMode) {
        this.taskMode = taskMode;
    }

    @Basic
    @Column(name = "receiver")
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Basic
    @Column(name = "enter_time")
    public Timestamp getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Timestamp enterTime) {
        this.enterTime = enterTime;
    }

    @Basic
    @Column(name = "end_time_plan")
    public Timestamp getEndTimePlan() {
        return endTimePlan;
    }

    public void setEndTimePlan(Timestamp endTimePlan) {
        this.endTimePlan = endTimePlan;
    }

    @Basic
    @Column(name = "booking_no")
    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    @Basic
    @Column(name = "conversion_no")
    public String getConversionNo() {
        return conversionNo;
    }

    public void setConversionNo(String conversionNo) {
        this.conversionNo = conversionNo;
    }

    @Basic
    @Column(name = "vehicle_maintain_no")
    public String getVehicleMaintainNo() {
        return vehicleMaintainNo;
    }

    public void setVehicleMaintainNo(String vehicleMaintainNo) {
        this.vehicleMaintainNo = vehicleMaintainNo;
    }

    @Basic
    @Column(name = "vehicle_factory_no")
    public String getVehicleFactoryNo() {
        return vehicleFactoryNo;
    }

    public void setVehicleFactoryNo(String vehicleFactoryNo) {
        this.vehicleFactoryNo = vehicleFactoryNo;
    }

    @Basic
    @Column(name = "drive_room_no")
    public String getDriveRoomNo() {
        return driveRoomNo;
    }

    public void setDriveRoomNo(String driveRoomNo) {
        this.driveRoomNo = driveRoomNo;
    }

    @Basic
    @Column(name = "work_place")
    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    @Basic
    @Column(name = "service_place")
    public Byte getServicePlace() {
        return servicePlace;
    }

    public void setServicePlace(Byte servicePlace) {
        this.servicePlace = servicePlace;
    }

    @Basic
    @Column(name = "out_service_address")
    public String getOutServiceAddress() {
        return outServiceAddress;
    }

    public void setOutServiceAddress(String outServiceAddress) {
        this.outServiceAddress = outServiceAddress;
    }

    @Basic
    @Column(name = "out_service_mileage")
    public BigDecimal getOutServiceMileage() {
        return outServiceMileage;
    }

    public void setOutServiceMileage(BigDecimal outServiceMileage) {
        this.outServiceMileage = outServiceMileage;
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
    @Column(name = "trouble_description")
    public String getTroubleDescription() {
        return troubleDescription;
    }

    public void setTroubleDescription(String troubleDescription) {
        this.troubleDescription = troubleDescription;
    }

    @Basic
    @Column(name = "vehicle_id")
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Basic
    @Column(name = "vehicle_card_no")
    public String getVehicleCardNo() {
        return vehicleCardNo;
    }

    public void setVehicleCardNo(String vehicleCardNo) {
        this.vehicleCardNo = vehicleCardNo;
    }

    @Basic
    @Column(name = "vehicle_sales_code")
    public String getVehicleSalesCode() {
        return vehicleSalesCode;
    }

    public void setVehicleSalesCode(String vehicleSalesCode) {
        this.vehicleSalesCode = vehicleSalesCode;
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
    @Column(name = "vehicle_name")
    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Basic
    @Column(name = "vehicle_color")
    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    @Basic
    @Column(name = "vehicle_vin")
    public String getVehicleVin() {
        return vehicleVin;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }

    @Basic
    @Column(name = "vehicle_engine_no")
    public String getVehicleEngineNo() {
        return vehicleEngineNo;
    }

    public void setVehicleEngineNo(String vehicleEngineNo) {
        this.vehicleEngineNo = vehicleEngineNo;
    }

    @Basic
    @Column(name = "vehicle_engine_type")
    public String getVehicleEngineType() {
        return vehicleEngineType;
    }

    public void setVehicleEngineType(String vehicleEngineType) {
        this.vehicleEngineType = vehicleEngineType;
    }

    @Basic
    @Column(name = "vehicle_purchase_time")
    public Timestamp getVehiclePurchaseTime() {
        return vehiclePurchaseTime;
    }

    public void setVehiclePurchaseTime(Timestamp vehiclePurchaseTime) {
        this.vehiclePurchaseTime = vehiclePurchaseTime;
    }

    @Basic
    @Column(name = "vehicle_out_factory_time")
    public Timestamp getVehicleOutFactoryTime() {
        return vehicleOutFactoryTime;
    }

    public void setVehicleOutFactoryTime(Timestamp vehicleOutFactoryTime) {
        this.vehicleOutFactoryTime = vehicleOutFactoryTime;
    }

    @Basic
    @Column(name = "vehicle_purchase_flag")
    public Boolean getVehiclePurchaseFlag() {
        return vehiclePurchaseFlag;
    }

    public void setVehiclePurchaseFlag(Boolean vehiclePurchaseFlag) {
        this.vehiclePurchaseFlag = vehiclePurchaseFlag;
    }

    @Basic
    @Column(name = "repair_time")
    public Timestamp getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Timestamp repairTime) {
        this.repairTime = repairTime;
    }

    @Basic
    @Column(name = "repair_mileage")
    public BigDecimal getRepairMileage() {
        return repairMileage;
    }

    public void setRepairMileage(BigDecimal repairMileage) {
        this.repairMileage = repairMileage;
    }

    @Basic
    @Column(name = "last_service_mileage")
    public BigDecimal getLastServiceMileage() {
        return lastServiceMileage;
    }

    public void setLastServiceMileage(BigDecimal lastServiceMileage) {
        this.lastServiceMileage = lastServiceMileage;
    }

    @Basic
    @Column(name = "last_service_time")
    public Timestamp getLastServiceTime() {
        return lastServiceTime;
    }

    public void setLastServiceTime(Timestamp lastServiceTime) {
        this.lastServiceTime = lastServiceTime;
    }

    @Basic
    @Column(name = "enter_milege")
    public BigDecimal getEnterMilege() {
        return enterMilege;
    }

    public void setEnterMilege(BigDecimal enterMilege) {
        this.enterMilege = enterMilege;
    }

    @Basic
    @Column(name = "claim_end_time")
    public Timestamp getClaimEndTime() {
        return claimEndTime;
    }

    public void setClaimEndTime(Timestamp claimEndTime) {
        this.claimEndTime = claimEndTime;
    }

    @Basic
    @Column(name = "claim_milege")
    public BigDecimal getClaimMilege() {
        return claimMilege;
    }

    public void setClaimMilege(BigDecimal claimMilege) {
        this.claimMilege = claimMilege;
    }

    @Basic
    @Column(name = "customer_id")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "customer_no")
    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    @Basic
    @Column(name = "customer_name")
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Basic
    @Column(name = "customer_linkman")
    public String getCustomerLinkman() {
        return customerLinkman;
    }

    public void setCustomerLinkman(String customerLinkman) {
        this.customerLinkman = customerLinkman;
    }

    @Basic
    @Column(name = "customer_mobile")
    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    @Basic
    @Column(name = "member_no")
    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    @Basic
    @Column(name = "member_type")
    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    @Basic
    @Column(name = "member_end_time")
    public Timestamp getMemberEndTime() {
        return memberEndTime;
    }

    public void setMemberEndTime(Timestamp memberEndTime) {
        this.memberEndTime = memberEndTime;
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
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "remark2")
    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    @Basic
    @Column(name = "remark3")
    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    @Basic
    @Column(name = "images_urls")
    public String getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(String imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    @Basic
    @Column(name = "payment_status")
    public Byte getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Byte paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Basic
    @Column(name = "work_status")
    public Byte getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Byte workStatus) {
        this.workStatus = workStatus;
    }

    @Basic
    @Column(name = "work_place_id")
    public String getWorkPlaceId() {
        return workPlaceId;
    }

    public void setWorkPlaceId(String workPlaceId) {
        this.workPlaceId = workPlaceId;
    }

    @Basic
    @Column(name = "work_place_no")
    public String getWorkPlaceNo() {
        return workPlaceNo;
    }

    public void setWorkPlaceNo(String workPlaceNo) {
        this.workPlaceNo = workPlaceNo;
    }

    @Basic
    @Column(name = "work_place_name")
    public String getWorkPlaceName() {
        return workPlaceName;
    }

    public void setWorkPlaceName(String workPlaceName) {
        this.workPlaceName = workPlaceName;
    }

    @Basic
    @Column(name = "items_price")
    public BigDecimal getItemsPrice() {
        return itemsPrice;
    }

    public void setItemsPrice(BigDecimal itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    @Basic
    @Column(name = "parts_price")
    public BigDecimal getPartsPrice() {
        return partsPrice;
    }

    public void setPartsPrice(BigDecimal partsPrice) {
        this.partsPrice = partsPrice;
    }

    @Basic
    @Column(name = "items_price_claim")
    public BigDecimal getItemsPriceClaim() {
        return itemsPriceClaim;
    }

    public void setItemsPriceClaim(BigDecimal itemsPriceClaim) {
        this.itemsPriceClaim = itemsPriceClaim;
    }

    @Basic
    @Column(name = "parts_price_claim")
    public BigDecimal getPartsPriceClaim() {
        return partsPriceClaim;
    }

    public void setPartsPriceClaim(BigDecimal partsPriceClaim) {
        this.partsPriceClaim = partsPriceClaim;
    }

    @Basic
    @Column(name = "items_price_insurance")
    public BigDecimal getItemsPriceInsurance() {
        return itemsPriceInsurance;
    }

    public void setItemsPriceInsurance(BigDecimal itemsPriceInsurance) {
        this.itemsPriceInsurance = itemsPriceInsurance;
    }

    @Basic
    @Column(name = "parts_price_insurance")
    public BigDecimal getPartsPriceInsurance() {
        return partsPriceInsurance;
    }

    public void setPartsPriceInsurance(BigDecimal partsPriceInsurance) {
        this.partsPriceInsurance = partsPriceInsurance;
    }

    @Basic
    @Column(name = "items_price_inner")
    public BigDecimal getItemsPriceInner() {
        return itemsPriceInner;
    }

    public void setItemsPriceInner(BigDecimal itemsPriceInner) {
        this.itemsPriceInner = itemsPriceInner;
    }

    @Basic
    @Column(name = "parts_price_inner")
    public BigDecimal getPartsPriceInner() {
        return partsPriceInner;
    }

    public void setPartsPriceInner(BigDecimal partsPriceInner) {
        this.partsPriceInner = partsPriceInner;
    }

    @Basic
    @Column(name = "fact_items_price")
    public BigDecimal getFactItemsPrice() {
        return factItemsPrice;
    }

    public void setFactItemsPrice(BigDecimal factItemsPrice) {
        this.factItemsPrice = factItemsPrice;
    }

    @Basic
    @Column(name = "fact_parts_price")
    public BigDecimal getFactPartsPrice() {
        return factPartsPrice;
    }

    public void setFactPartsPrice(BigDecimal factPartsPrice) {
        this.factPartsPrice = factPartsPrice;
    }

    @Basic
    @Column(name = "claim_tot")
    public BigDecimal getClaimTot() {
        return claimTot;
    }

    public void setClaimTot(BigDecimal claimTot) {
        this.claimTot = claimTot;
    }

    @Basic
    @Column(name = "claim_tot_apply")
    public BigDecimal getClaimTotApply() {
        return claimTotApply;
    }

    public void setClaimTotApply(BigDecimal claimTotApply) {
        this.claimTotApply = claimTotApply;
    }

    @Basic
    @Column(name = "insurance_tot")
    public BigDecimal getInsuranceTot() {
        return insuranceTot;
    }

    public void setInsuranceTot(BigDecimal insuranceTot) {
        this.insuranceTot = insuranceTot;
    }

    @Basic
    @Column(name = "inner_tot")
    public BigDecimal getInnerTot() {
        return innerTot;
    }

    public void setInnerTot(BigDecimal innerTot) {
        this.innerTot = innerTot;
    }

    @Basic
    @Column(name = "guarantee_money")
    public BigDecimal getGuaranteeMoney() {
        return guaranteeMoney;
    }

    public void setGuaranteeMoney(BigDecimal guaranteeMoney) {
        this.guaranteeMoney = guaranteeMoney;
    }

    @Basic
    @Column(name = "insurance_claim_item")
    public BigDecimal getInsuranceClaimItem() {
        return insuranceClaimItem;
    }

    public void setInsuranceClaimItem(BigDecimal insuranceClaimItem) {
        this.insuranceClaimItem = insuranceClaimItem;
    }

    @Basic
    @Column(name = "insurance_claim_part")
    public BigDecimal getInsuranceClaimPart() {
        return insuranceClaimPart;
    }

    public void setInsuranceClaimPart(BigDecimal insuranceClaimPart) {
        this.insuranceClaimPart = insuranceClaimPart;
    }

    @Basic
    @Column(name = "insurance_approve_item")
    public BigDecimal getInsuranceApproveItem() {
        return insuranceApproveItem;
    }

    public void setInsuranceApproveItem(BigDecimal insuranceApproveItem) {
        this.insuranceApproveItem = insuranceApproveItem;
    }

    @Basic
    @Column(name = "insurance_approve_part")
    public BigDecimal getInsuranceApprovePart() {
        return insuranceApprovePart;
    }

    public void setInsuranceApprovePart(BigDecimal insuranceApprovePart) {
        this.insuranceApprovePart = insuranceApprovePart;
    }

    @Basic
    @Column(name = "insurance_claim_no")
    public String getInsuranceClaimNo() {
        return insuranceClaimNo;
    }

    public void setInsuranceClaimNo(String insuranceClaimNo) {
        this.insuranceClaimNo = insuranceClaimNo;
    }

    @Basic
    @Column(name = "insurance_claimer")
    public String getInsuranceClaimer() {
        return insuranceClaimer;
    }

    public void setInsuranceClaimer(String insuranceClaimer) {
        this.insuranceClaimer = insuranceClaimer;
    }

    @Basic
    @Column(name = "insurance_approver")
    public String getInsuranceApprover() {
        return insuranceApprover;
    }

    public void setInsuranceApprover(String insuranceApprover) {
        this.insuranceApprover = insuranceApprover;
    }

    @Basic
    @Column(name = "insurance_claim_time")
    public Timestamp getInsuranceClaimTime() {
        return insuranceClaimTime;
    }

    public void setInsuranceClaimTime(Timestamp insuranceClaimTime) {
        this.insuranceClaimTime = insuranceClaimTime;
    }

    @Basic
    @Column(name = "insurance_approve_time")
    public Timestamp getInsuranceApproveTime() {
        return insuranceApproveTime;
    }

    public void setInsuranceApproveTime(Timestamp insuranceApproveTime) {
        this.insuranceApproveTime = insuranceApproveTime;
    }

    @Basic
    @Column(name = "insurance_factory_id")
    public String getInsuranceFactoryId() {
        return insuranceFactoryId;
    }

    public void setInsuranceFactoryId(String insuranceFactoryId) {
        this.insuranceFactoryId = insuranceFactoryId;
    }

    @Basic
    @Column(name = "insurance_factory_no")
    public String getInsuranceFactoryNo() {
        return insuranceFactoryNo;
    }

    public void setInsuranceFactoryNo(String insuranceFactoryNo) {
        this.insuranceFactoryNo = insuranceFactoryNo;
    }

    @Basic
    @Column(name = "insurance_factory_name")
    public String getInsuranceFactoryName() {
        return insuranceFactoryName;
    }

    public void setInsuranceFactoryName(String insuranceFactoryName) {
        this.insuranceFactoryName = insuranceFactoryName;
    }

    @Basic
    @Column(name = "insurance_remark")
    public String getInsuranceRemark() {
        return insuranceRemark;
    }

    public void setInsuranceRemark(String insuranceRemark) {
        this.insuranceRemark = insuranceRemark;
    }

    @Basic
    @Column(name = "insurance_pay_type")
    public Byte getInsurancePayType() {
        return insurancePayType;
    }

    public void setInsurancePayType(Byte insurancePayType) {
        this.insurancePayType = insurancePayType;
    }

    @Basic
    @Column(name = "station_id")
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
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
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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
    @Column(name = "modify_time")
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "censor")
    public String getCensor() {
        return censor;
    }

    public void setCensor(String censor) {
        this.censor = censor;
    }

    @Basic
    @Column(name = "work_postil")
    public String getWorkPostil() {
        return workPostil;
    }

    public void setWorkPostil(String workPostil) {
        this.workPostil = workPostil;
    }

    @Basic
    @Column(name = "balancer")
    public String getBalancer() {
        return balancer;
    }

    public void setBalancer(String balancer) {
        this.balancer = balancer;
    }

    @Basic
    @Column(name = "balance_time")
    public Timestamp getBalanceTime() {
        return balanceTime;
    }

    public void setBalanceTime(Timestamp balanceTime) {
        this.balanceTime = balanceTime;
    }

    @Basic
    @Column(name = "balancer_against")
    public String getBalancerAgainst() {
        return balancerAgainst;
    }

    public void setBalancerAgainst(String balancerAgainst) {
        this.balancerAgainst = balancerAgainst;
    }

    @Basic
    @Column(name = "balance_time_against")
    public Timestamp getBalanceTimeAgainst() {
        return balanceTimeAgainst;
    }

    public void setBalanceTimeAgainst(Timestamp balanceTimeAgainst) {
        this.balanceTimeAgainst = balanceTimeAgainst;
    }

    @Basic
    @Column(name = "add_frequency")
    public Integer getAddFrequency() {
        return addFrequency;
    }

    public void setAddFrequency(Integer addFrequency) {
        this.addFrequency = addFrequency;
    }

    @Basic
    @Column(name = "receiver_point")
    public Byte getReceiverPoint() {
        return receiverPoint;
    }

    public void setReceiverPoint(Byte receiverPoint) {
        this.receiverPoint = receiverPoint;
    }

    @Basic
    @Column(name = "work_group_point")
    public Byte getWorkGroupPoint() {
        return workGroupPoint;
    }

    public void setWorkGroupPoint(Byte workGroupPoint) {
        this.workGroupPoint = workGroupPoint;
    }

    @Basic
    @Column(name = "part_department_point")
    public Byte getPartDepartmentPoint() {
        return partDepartmentPoint;
    }

    public void setPartDepartmentPoint(Byte partDepartmentPoint) {
        this.partDepartmentPoint = partDepartmentPoint;
    }

    @Basic
    @Column(name = "claims_status")
    public Byte getClaimsStatus() {
        return claimsStatus;
    }

    public void setClaimsStatus(Byte claimsStatus) {
        this.claimsStatus = claimsStatus;
    }

    @Basic
    @Column(name = "insurance_status")
    public Byte getInsuranceStatus() {
        return insuranceStatus;
    }

    public void setInsuranceStatus(Byte insuranceStatus) {
        this.insuranceStatus = insuranceStatus;
    }

    @Basic
    @Column(name = "parts_agio")
    public BigDecimal getPartsAgio() {
        return partsAgio;
    }

    public void setPartsAgio(BigDecimal partsAgio) {
        this.partsAgio = partsAgio;
    }

    @Basic
    @Column(name = "items_agio")
    public BigDecimal getItemsAgio() {
        return itemsAgio;
    }

    public void setItemsAgio(BigDecimal itemsAgio) {
        this.itemsAgio = itemsAgio;
    }

    @Basic
    @Column(name = "vehicle_expense_tot")
    public BigDecimal getVehicleExpenseTot() {
        return vehicleExpenseTot;
    }

    public void setVehicleExpenseTot(BigDecimal vehicleExpenseTot) {
        this.vehicleExpenseTot = vehicleExpenseTot;
    }

    @Basic
    @Column(name = "call_back_success")
    public Byte getCallBackSuccess() {
        return callBackSuccess;
    }

    public void setCallBackSuccess(Byte callBackSuccess) {
        this.callBackSuccess = callBackSuccess;
    }

    @Basic
    @Column(name = "call_back_result")
    public Byte getCallBackResult() {
        return callBackResult;
    }

    public void setCallBackResult(Byte callBackResult) {
        this.callBackResult = callBackResult;
    }

    @Basic
    @Column(name = "call_back_abstract")
    public String getCallBackAbstract() {
        return callBackAbstract;
    }

    public void setCallBackAbstract(String callBackAbstract) {
        this.callBackAbstract = callBackAbstract;
    }

    @Basic
    @Column(name = "call_back_final_result")
    public Byte getCallBackFinalResult() {
        return callBackFinalResult;
    }

    public void setCallBackFinalResult(Byte callBackFinalResult) {
        this.callBackFinalResult = callBackFinalResult;
    }

    @Basic
    @Column(name = "call_back_remark")
    public String getCallBackRemark() {
        return callBackRemark;
    }

    public void setCallBackRemark(String callBackRemark) {
        this.callBackRemark = callBackRemark;
    }

    @Basic
    @Column(name = "call_back_flag")
    public Boolean getCallBackFlag() {
        return callBackFlag;
    }

    public void setCallBackFlag(Boolean callBackFlag) {
        this.callBackFlag = callBackFlag;
    }

    @Basic
    @Column(name = "call_back_deal_remark")
    public String getCallBackDealRemark() {
        return callBackDealRemark;
    }

    public void setCallBackDealRemark(String callBackDealRemark) {
        this.callBackDealRemark = callBackDealRemark;
    }

    @Basic
    @Column(name = "call_back_fail_reason")
    public String getCallBackFailReason() {
        return callBackFailReason;
    }

    public void setCallBackFailReason(String callBackFailReason) {
        this.callBackFailReason = callBackFailReason;
    }

    @Basic
    @Column(name = "back_time")
    public Timestamp getBackTime() {
        return backTime;
    }

    public void setBackTime(Timestamp backTime) {
        this.backTime = backTime;
    }

    @Basic
    @Column(name = "resrv_status")
    public Byte getResrvStatus() {
        return resrvStatus;
    }

    public void setResrvStatus(Byte resrvStatus) {
        this.resrvStatus = resrvStatus;
    }

    @Basic
    @Column(name = "resrv_man")
    public String getResrvMan() {
        return resrvMan;
    }

    public void setResrvMan(String resrvMan) {
        this.resrvMan = resrvMan;
    }

    @Basic
    @Column(name = "resrv_date")
    public Timestamp getResrvDate() {
        return resrvDate;
    }

    public void setResrvDate(Timestamp resrvDate) {
        this.resrvDate = resrvDate;
    }

    @Basic
    @Column(name = "resrv_reason")
    public String getResrvReason() {
        return resrvReason;
    }

    public void setResrvReason(String resrvReason) {
        this.resrvReason = resrvReason;
    }

    @Basic
    @Column(name = "resrv_desc")
    public String getResrvDesc() {
        return resrvDesc;
    }

    public void setResrvDesc(String resrvDesc) {
        this.resrvDesc = resrvDesc;
    }

    @Basic
    @Column(name = "resrv_status_deal")
    public Byte getResrvStatusDeal() {
        return resrvStatusDeal;
    }

    public void setResrvStatusDeal(Byte resrvStatusDeal) {
        this.resrvStatusDeal = resrvStatusDeal;
    }

    @Basic
    @Column(name = "resrv_do_man")
    public String getResrvDoMan() {
        return resrvDoMan;
    }

    public void setResrvDoMan(String resrvDoMan) {
        this.resrvDoMan = resrvDoMan;
    }

    @Basic
    @Column(name = "resrv_do_date")
    public Timestamp getResrvDoDate() {
        return resrvDoDate;
    }

    public void setResrvDoDate(Timestamp resrvDoDate) {
        this.resrvDoDate = resrvDoDate;
    }

    @Basic
    @Column(name = "resrv_do_desc")
    public String getResrvDoDesc() {
        return resrvDoDesc;
    }

    public void setResrvDoDesc(String resrvDoDesc) {
        this.resrvDoDesc = resrvDoDesc;
    }

    @Basic
    @Column(name = "ticket_money_face")
    public BigDecimal getTicketMoneyFace() {
        return ticketMoneyFace;
    }

    public void setTicketMoneyFace(BigDecimal ticketMoneyFace) {
        this.ticketMoneyFace = ticketMoneyFace;
    }

    @Basic
    @Column(name = "ticket_money_canuse")
    public BigDecimal getTicketMoneyCanuse() {
        return ticketMoneyCanuse;
    }

    public void setTicketMoneyCanuse(BigDecimal ticketMoneyCanuse) {
        this.ticketMoneyCanuse = ticketMoneyCanuse;
    }

    @Basic
    @Column(name = "supplier_id")
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Basic
    @Column(name = "supplier_no")
    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    @Basic
    @Column(name = "supplier_name")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Basic
    @Column(name = "station_id_delegate")
    public String getStationIdDelegate() {
        return stationIdDelegate;
    }

    public void setStationIdDelegate(String stationIdDelegate) {
        this.stationIdDelegate = stationIdDelegate;
    }

    @Basic
    @Column(name = "checker")
    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    @Basic
    @Column(name = "check_time")
    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }

    @Basic
    @Column(name = "maintain_mileage_next")
    public BigDecimal getMaintainMileageNext() {
        return maintainMileageNext;
    }

    public void setMaintainMileageNext(BigDecimal maintainMileageNext) {
        this.maintainMileageNext = maintainMileageNext;
    }

    @Basic
    @Column(name = "maintain_date_next")
    public Timestamp getMaintainDateNext() {
        return maintainDateNext;
    }

    public void setMaintainDateNext(Timestamp maintainDateNext) {
        this.maintainDateNext = maintainDateNext;
    }

    @Basic
    @Column(name = "task_kind")
    public Byte getTaskKind() {
        return taskKind;
    }

    public void setTaskKind(Byte taskKind) {
        this.taskKind = taskKind;
    }

    @Basic
    @Column(name = "price_ticket")
    public BigDecimal getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(BigDecimal priceTicket) {
        this.priceTicket = priceTicket;
    }

    @Basic
    @Column(name = "vehicle_belong_to")
    public Boolean getVehicleBelongTo() {
        return vehicleBelongTo;
    }

    public void setVehicleBelongTo(Boolean vehicleBelongTo) {
        this.vehicleBelongTo = vehicleBelongTo;
    }

    @Basic
    @Column(name = "other_tot")
    public BigDecimal getOtherTot() {
        return otherTot;
    }

    public void setOtherTot(BigDecimal otherTot) {
        this.otherTot = otherTot;
    }

    @Basic
    @Column(name = "belong_to_supplier_id")
    public String getBelongToSupplierId() {
        return belongToSupplierId;
    }

    public void setBelongToSupplierId(String belongToSupplierId) {
        this.belongToSupplierId = belongToSupplierId;
    }

    @Basic
    @Column(name = "belong_to_supplier_no")
    public String getBelongToSupplierNo() {
        return belongToSupplierNo;
    }

    public void setBelongToSupplierNo(String belongToSupplierNo) {
        this.belongToSupplierNo = belongToSupplierNo;
    }

    @Basic
    @Column(name = "belong_to_supplier_name")
    public String getBelongToSupplierName() {
        return belongToSupplierName;
    }

    public void setBelongToSupplierName(String belongToSupplierName) {
        this.belongToSupplierName = belongToSupplierName;
    }

    @Basic
    @Column(name = "items_price_ticket")
    public BigDecimal getItemsPriceTicket() {
        return itemsPriceTicket;
    }

    public void setItemsPriceTicket(BigDecimal itemsPriceTicket) {
        this.itemsPriceTicket = itemsPriceTicket;
    }

    @Basic
    @Column(name = "parts_price_ticket")
    public BigDecimal getPartsPriceTicket() {
        return partsPriceTicket;
    }

    public void setPartsPriceTicket(BigDecimal partsPriceTicket) {
        this.partsPriceTicket = partsPriceTicket;
    }

    @Basic
    @Column(name = "ticket_tot")
    public BigDecimal getTicketTot() {
        return ticketTot;
    }

    public void setTicketTot(BigDecimal ticketTot) {
        this.ticketTot = ticketTot;
    }

    @Basic
    @Column(name = "fee_price")
    public BigDecimal getFeePrice() {
        return feePrice;
    }

    public void setFeePrice(BigDecimal feePrice) {
        this.feePrice = feePrice;
    }

    @Basic
    @Column(name = "fee_price_inner")
    public BigDecimal getFeePriceInner() {
        return feePriceInner;
    }

    public void setFeePriceInner(BigDecimal feePriceInner) {
        this.feePriceInner = feePriceInner;
    }

    @Basic
    @Column(name = "fee_price_claim")
    public BigDecimal getFeePriceClaim() {
        return feePriceClaim;
    }

    public void setFeePriceClaim(BigDecimal feePriceClaim) {
        this.feePriceClaim = feePriceClaim;
    }

    @Basic
    @Column(name = "fee_price_insurance")
    public BigDecimal getFeePriceInsurance() {
        return feePriceInsurance;
    }

    public void setFeePriceInsurance(BigDecimal feePriceInsurance) {
        this.feePriceInsurance = feePriceInsurance;
    }

    @Basic
    @Column(name = "fee_price_ticket")
    public BigDecimal getFeePriceTicket() {
        return feePriceTicket;
    }

    public void setFeePriceTicket(BigDecimal feePriceTicket) {
        this.feePriceTicket = feePriceTicket;
    }

    @Basic
    @Column(name = "out_driver")
    public String getOutDriver() {
        return outDriver;
    }

    public void setOutDriver(String outDriver) {
        this.outDriver = outDriver;
    }

    @Basic
    @Column(name = "vehicle_sale_code")
    public String getVehicleSaleCode() {
        return vehicleSaleCode;
    }

    public void setVehicleSaleCode(String vehicleSaleCode) {
        this.vehicleSaleCode = vehicleSaleCode;
    }

    @Basic
    @Column(name = "agio_modifier")
    public String getAgioModifier() {
        return agioModifier;
    }

    public void setAgioModifier(String agioModifier) {
        this.agioModifier = agioModifier;
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
    @Column(name = "call_back_handle_department")
    public String getCallBackHandleDepartment() {
        return callBackHandleDepartment;
    }

    public void setCallBackHandleDepartment(String callBackHandleDepartment) {
        this.callBackHandleDepartment = callBackHandleDepartment;
    }

    @Basic
    @Column(name = "call_back_emp_id")
    public String getCallBackEmpId() {
        return callBackEmpId;
    }

    public void setCallBackEmpId(String callBackEmpId) {
        this.callBackEmpId = callBackEmpId;
    }

    @Basic
    @Column(name = "call_back_emp_name")
    public String getCallBackEmpName() {
        return callBackEmpName;
    }

    public void setCallBackEmpName(String callBackEmpName) {
        this.callBackEmpName = callBackEmpName;
    }

    @Basic
    @Column(name = "insurance_receiver")
    public String getInsuranceReceiver() {
        return insuranceReceiver;
    }

    public void setInsuranceReceiver(String insuranceReceiver) {
        this.insuranceReceiver = insuranceReceiver;
    }

    @Basic
    @Column(name = "low_forecast_reason")
    public String getLowForecastReason() {
        return lowForecastReason;
    }

    public void setLowForecastReason(String lowForecastReason) {
        this.lowForecastReason = lowForecastReason;
    }

    @Basic
    @Column(name = "low_approve_reason")
    public String getLowApproveReason() {
        return lowApproveReason;
    }

    public void setLowApproveReason(String lowApproveReason) {
        this.lowApproveReason = lowApproveReason;
    }

    @Basic
    @Column(name = "part_manage_percent")
    public BigDecimal getPartManagePercent() {
        return partManagePercent;
    }

    public void setPartManagePercent(BigDecimal partManagePercent) {
        this.partManagePercent = partManagePercent;
    }

    @Basic
    @Column(name = "send_msg_person")
    public String getSendMsgPerson() {
        return sendMsgPerson;
    }

    public void setSendMsgPerson(String sendMsgPerson) {
        this.sendMsgPerson = sendMsgPerson;
    }

    @Basic
    @Column(name = "send_msg_time")
    public Timestamp getSendMsgTime() {
        return sendMsgTime;
    }

    public void setSendMsgTime(Timestamp sendMsgTime) {
        this.sendMsgTime = sendMsgTime;
    }

    @Basic
    @Column(name = "receiver_no")
    public String getReceiverNo() {
        return receiverNo;
    }

    public void setReceiverNo(String receiverNo) {
        this.receiverNo = receiverNo;
    }

    @Basic
    @Column(name = "creator_no")
    public String getCreatorNo() {
        return creatorNo;
    }

    public void setCreatorNo(String creatorNo) {
        this.creatorNo = creatorNo;
    }

    @Basic
    @Column(name = "creator_unit_no")
    public String getCreatorUnitNo() {
        return creatorUnitNo;
    }

    public void setCreatorUnitNo(String creatorUnitNo) {
        this.creatorUnitNo = creatorUnitNo;
    }

    @Basic
    @Column(name = "creator_unit_name")
    public String getCreatorUnitName() {
        return creatorUnitName;
    }

    public void setCreatorUnitName(String creatorUnitName) {
        this.creatorUnitName = creatorUnitName;
    }

    @Basic
    @Column(name = "balancer_no")
    public String getBalancerNo() {
        return balancerNo;
    }

    public void setBalancerNo(String balancerNo) {
        this.balancerNo = balancerNo;
    }

    @Basic
    @Column(name = "balancer_unit_no")
    public String getBalancerUnitNo() {
        return balancerUnitNo;
    }

    public void setBalancerUnitNo(String balancerUnitNo) {
        this.balancerUnitNo = balancerUnitNo;
    }

    @Basic
    @Column(name = "balancer_unit_name")
    public String getBalancerUnitName() {
        return balancerUnitName;
    }

    public void setBalancerUnitName(String balancerUnitName) {
        this.balancerUnitName = balancerUnitName;
    }

    @Basic
    @Column(name = "tot_tax_money")
    public BigDecimal getTotTaxMoney() {
        return totTaxMoney;
    }

    public void setTotTaxMoney(BigDecimal totTaxMoney) {
        this.totTaxMoney = totTaxMoney;
    }

    @Basic
    @Column(name = "tot_no_tax_money")
    public BigDecimal getTotNoTaxMoney() {
        return totNoTaxMoney;
    }

    public void setTotNoTaxMoney(BigDecimal totNoTaxMoney) {
        this.totNoTaxMoney = totNoTaxMoney;
    }

    @Basic
    @Column(name = "tot_tax")
    public BigDecimal getTotTax() {
        return totTax;
    }

    public void setTotTax(BigDecimal totTax) {
        this.totTax = totTax;
    }

    @Basic
    @Column(name = "insurance_company_id")
    public String getInsuranceCompanyId() {
        return insuranceCompanyId;
    }

    public void setInsuranceCompanyId(String insuranceCompanyId) {
        this.insuranceCompanyId = insuranceCompanyId;
    }

    @Basic
    @Column(name = "insurance_company_no")
    public String getInsuranceCompanyNo() {
        return insuranceCompanyNo;
    }

    public void setInsuranceCompanyNo(String insuranceCompanyNo) {
        this.insuranceCompanyNo = insuranceCompanyNo;
    }

    @Basic
    @Column(name = "insurance_company_name")
    public String getInsuranceCompanyName() {
        return insuranceCompanyName;
    }

    public void setInsuranceCompanyName(String insuranceCompanyName) {
        this.insuranceCompanyName = insuranceCompanyName;
    }

    @Basic
    @Column(name = "insurance_claim_consigns")
    public BigDecimal getInsuranceClaimConsigns() {
        return insuranceClaimConsigns;
    }

    public void setInsuranceClaimConsigns(BigDecimal insuranceClaimConsigns) {
        this.insuranceClaimConsigns = insuranceClaimConsigns;
    }

    @Basic
    @Column(name = "insurance_claim_charge")
    public BigDecimal getInsuranceClaimCharge() {
        return insuranceClaimCharge;
    }

    public void setInsuranceClaimCharge(BigDecimal insuranceClaimCharge) {
        this.insuranceClaimCharge = insuranceClaimCharge;
    }

    @Basic
    @Column(name = "insurance_approve_consigns")
    public BigDecimal getInsuranceApproveConsigns() {
        return insuranceApproveConsigns;
    }

    public void setInsuranceApproveConsigns(BigDecimal insuranceApproveConsigns) {
        this.insuranceApproveConsigns = insuranceApproveConsigns;
    }

    @Basic
    @Column(name = "insurance_approve_charge")
    public BigDecimal getInsuranceApproveCharge() {
        return insuranceApproveCharge;
    }

    public void setInsuranceApproveCharge(BigDecimal insuranceApproveCharge) {
        this.insuranceApproveCharge = insuranceApproveCharge;
    }

    @Basic
    @Column(name = "consigns_price_insurance")
    public BigDecimal getConsignsPriceInsurance() {
        return consignsPriceInsurance;
    }

    public void setConsignsPriceInsurance(BigDecimal consignsPriceInsurance) {
        this.consignsPriceInsurance = consignsPriceInsurance;
    }

    @Basic
    @Column(name = "charge_price_insurance")
    public BigDecimal getChargePriceInsurance() {
        return chargePriceInsurance;
    }

    public void setChargePriceInsurance(BigDecimal chargePriceInsurance) {
        this.chargePriceInsurance = chargePriceInsurance;
    }

    @Basic
    @Column(name = "driving_license_number")
    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
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
    @Column(name = "is_secondhand")
    public Boolean getSecondhand() {
        return isSecondhand;
    }

    public void setSecondhand(Boolean secondhand) {
        isSecondhand = secondhand;
    }

    @Basic
    @Column(name = "vehicle_card_model")
    public String getVehicleCardModel() {
        return vehicleCardModel;
    }

    public void setVehicleCardModel(String vehicleCardModel) {
        this.vehicleCardModel = vehicleCardModel;
    }

    @Basic
    @Column(name = "vehicle_weight")
    public String getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(String vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }

    @Basic
    @Column(name = "wechat_evaluation_code")
    public String getWechatEvaluationCode() {
        return wechatEvaluationCode;
    }

    public void setWechatEvaluationCode(String wechatEvaluationCode) {
        this.wechatEvaluationCode = wechatEvaluationCode;
    }

    @Basic
    @Column(name = "company_ticket")
    public BigDecimal getCompanyTicket() {
        return companyTicket;
    }

    public void setCompanyTicket(BigDecimal companyTicket) {
        this.companyTicket = companyTicket;
    }

    @Basic
    @Column(name = "dfs_payment_method")
    public Byte getDfsPaymentMethod() {
        return dfsPaymentMethod;
    }

    public void setDfsPaymentMethod(Byte dfsPaymentMethod) {
        this.dfsPaymentMethod = dfsPaymentMethod;
    }

    @Basic
    @Column(name = "resrv_do_mode")
    public String getResrvDoMode() {
        return resrvDoMode;
    }

    public void setResrvDoMode(String resrvDoMode) {
        this.resrvDoMode = resrvDoMode;
    }

    @Basic
    @Column(name = "oil_agio")
    public BigDecimal getOilAgio() {
        return oilAgio;
    }

    public void setOilAgio(BigDecimal oilAgio) {
        this.oilAgio = oilAgio;
    }

    @Basic
    @Column(name = "filter_agio")
    public BigDecimal getFilterAgio() {
        return filterAgio;
    }

    public void setFilterAgio(BigDecimal filterAgio) {
        this.filterAgio = filterAgio;
    }

    @Basic
    @Column(name = "is_modify_total_amount")
    public Boolean getModifyTotalAmount() {
        return isModifyTotalAmount;
    }

    public void setModifyTotalAmount(Boolean modifyTotalAmount) {
        isModifyTotalAmount = modifyTotalAmount;
    }

    @Basic
    @Column(name = "receiver_id")
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Basic
    @Column(name = "go_out_departure_time")
    public Timestamp getGoOutDepartureTime() {
        return goOutDepartureTime;
    }

    public void setGoOutDepartureTime(Timestamp goOutDepartureTime) {
        this.goOutDepartureTime = goOutDepartureTime;
    }

    @Basic
    @Column(name = "go_out_return_time")
    public Timestamp getGoOutReturnTime() {
        return goOutReturnTime;
    }

    public void setGoOutReturnTime(Timestamp goOutReturnTime) {
        this.goOutReturnTime = goOutReturnTime;
    }

    @Basic
    @Column(name = "appraisal_info")
    public String getAppraisalInfo() {
        return appraisalInfo;
    }

    public void setAppraisalInfo(String appraisalInfo) {
        this.appraisalInfo = appraisalInfo;
    }

    @Basic
    @Column(name = "additional_summary")
    public String getAdditionalSummary() {
        return additionalSummary;
    }

    public void setAdditionalSummary(String additionalSummary) {
        this.additionalSummary = additionalSummary;
    }

    @Basic
    @Column(name = "parent_maintenance_num")
    public String getParentMaintenanceNum() {
        return parentMaintenanceNum;
    }

    public void setParentMaintenanceNum(String parentMaintenanceNum) {
        this.parentMaintenanceNum = parentMaintenanceNum;
    }

    @Basic
    @Column(name = "pre_check_summary")
    public String getPreCheckSummary() {
        return preCheckSummary;
    }

    public void setPreCheckSummary(String preCheckSummary) {
        this.preCheckSummary = preCheckSummary;
    }

    @Basic
    @Column(name = "service_notice_number")
    public String getServiceNoticeNumber() {
        return serviceNoticeNumber;
    }

    public void setServiceNoticeNumber(String serviceNoticeNumber) {
        this.serviceNoticeNumber = serviceNoticeNumber;
    }

    @Basic
    @Column(name = "user_type")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "sub_type")
    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    @Basic
    @Column(name = "crm_repair_order")
    public String getCrmRepairOrder() {
        return crmRepairOrder;
    }

    public void setCrmRepairOrder(String crmRepairOrder) {
        this.crmRepairOrder = crmRepairOrder;
    }

    @Basic
    @Column(name = "document_no")
    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    @Basic
    @Column(name = "status")
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Basic
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_no")
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "department_id")
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "department_no")
    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    @Basic
    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Basic
    @Column(name = "submit_status")
    public Byte getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(Byte submitStatus) {
        this.submitStatus = submitStatus;
    }

    @Basic
    @Column(name = "submit_time")
    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    @Basic
    @Column(name = "submit_station_id")
    public String getSubmitStationId() {
        return submitStationId;
    }

    public void setSubmitStationId(String submitStationId) {
        this.submitStationId = submitStationId;
    }

    @Basic
    @Column(name = "submit_station_name")
    public String getSubmitStationName() {
        return submitStationName;
    }

    public void setSubmitStationName(String submitStationName) {
        this.submitStationName = submitStationName;
    }

    @Basic
    @Column(name = "service_item_agio_min")
    public BigDecimal getServiceItemAgioMin() {
        return serviceItemAgioMin;
    }

    public void setServiceItemAgioMin(BigDecimal serviceItemAgioMin) {
        this.serviceItemAgioMin = serviceItemAgioMin;
    }

    @Basic
    @Column(name = "task_total_agio")
    public BigDecimal getTaskTotalAgio() {
        return taskTotalAgio;
    }

    public void setTaskTotalAgio(BigDecimal taskTotalAgio) {
        this.taskTotalAgio = taskTotalAgio;
    }

    @Basic
    @Column(name = "task_agio_money")
    public BigDecimal getTaskAgioMoney() {
        return taskAgioMoney;
    }

    public void setTaskAgioMoney(BigDecimal taskAgioMoney) {
        this.taskAgioMoney = taskAgioMoney;
    }

    @Basic
    @Column(name = "approver_id")
    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    @Basic
    @Column(name = "approver_no")
    public String getApproverNo() {
        return approverNo;
    }

    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    @Basic
    @Column(name = "approver_name")
    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    @Basic
    @Column(name = "approve_time")
    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }
    

    @Transient
    public List<ServiceWorkListItems> getItems() {
        return this.items;
    }

    public void setItems(List<ServiceWorkListItems> items) {
        this.items = items;
    }

    @Transient
    public List<ServiceWorkListParts> getParts() {
        return this.parts;
    }

    public void setParts(List<ServiceWorkListParts> parts) {
        this.parts = parts;
    }

    @Transient
    public List<ServiceWorkListConsigns> getConsigns() {
        return this.consigns;
    }

    public void setConsigns(List<ServiceWorkListConsigns> consigns) {
        this.consigns = consigns;
    }

    @Transient
    public List<ServiceWorkListsCharge> getCharges() {
        return this.charges;
    }

    public void setCharges(List<ServiceWorkListsCharge> charges) {
        this.charges = charges;
    }

    @Transient
    public List<ServiceWorkListProposedItems> getProposedItems() {
        return this.proposedItems;
    }

    public void setProposedItems(List<ServiceWorkListProposedItems> proposedItems) {
        this.proposedItems = proposedItems;
    }
    
}
