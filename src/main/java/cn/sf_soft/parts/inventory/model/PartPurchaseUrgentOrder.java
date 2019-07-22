package cn.sf_soft.parts.inventory.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by henry on 2018/1/6.
 */
@Entity
@Table(name = "part_purchfase_urgent_order", schema = "dbo")
public class PartPurchaseUrgentOrder {
    private String urgentOrderId;
    private String purchaseId;
    private String appraisalNo;
    private String vehicleVno;
    private String saleOrderType;
    private Timestamp faultDate;
    private String receivingUnitsName;
    private String stationId;
    private String stationLinkman;
    private String stationFax;
    private Timestamp vehiclePurchaseTime;
    private String serviceCode;
    private String serviceName;
    private String orgId;
    private String customerPhone;
    private String zipCode;
    private String customerDept;
    private String faultDescription;
    private Timestamp reportingDate;
    private String shippingAddress;
    private String vehicleLinkmanPhone;
    private String vehicleEngineNo;
    private Timestamp urgentOrderDate;
    private String isClaimsPart;
    private String urgentOrderStatus;
    private String faultLocation;
    private String shippingType;
    private String drivenDistance;
    private String userName;
    private String failureReceivingStation;
    private String vehicleChassisNumber;
    private String urgentCustomerNo;
    private String urgentCustomerName;
    private String stopWaiting;
    private String errorCode;
    private String errorMsg;
    private String crmPurchaseId;
    private String crmPurchaseNo;
    private Timestamp orderDate;
    private String orderStatus;
    private String underpaNo;
    private String purchaseNo;

    @Id
    @Column(name = "urgent_order_id", nullable = false, length = 40)
    public String getUrgentOrderId() {
        return urgentOrderId;
    }

    public void setUrgentOrderId(String urgentOrderId) {
        this.urgentOrderId = urgentOrderId;
    }

    @Basic
    @Column(name = "purchase_id", nullable = true, length = 40)
    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    @Basic
    @Column(name = "appraisal_no", nullable = true, length = 50)
    public String getAppraisalNo() {
        return appraisalNo;
    }

    public void setAppraisalNo(String appraisalNo) {
        this.appraisalNo = appraisalNo;
    }

    @Basic
    @Column(name = "vehicle_vno", nullable = true, length = 100)
    public String getVehicleVno() {
        return vehicleVno;
    }

    public void setVehicleVno(String vehicleVno) {
        this.vehicleVno = vehicleVno;
    }

    @Basic
    @Column(name = "sale_order_type", nullable = true, length = 20)
    public String getSaleOrderType() {
        return saleOrderType;
    }

    public void setSaleOrderType(String saleOrderType) {
        this.saleOrderType = saleOrderType;
    }

    @Basic
    @Column(name = "fault_date", nullable = true)
    public Timestamp getFaultDate() {
        return faultDate;
    }

    public void setFaultDate(Timestamp faultDate) {
        this.faultDate = faultDate;
    }

    @Basic
    @Column(name = "receiving_units_name", nullable = true, length = 500)
    public String getReceivingUnitsName() {
        return receivingUnitsName;
    }

    public void setReceivingUnitsName(String receivingUnitsName) {
        this.receivingUnitsName = receivingUnitsName;
    }

    @Basic
    @Column(name = "station_id", nullable = true, length = 40)
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "station_linkman", nullable = true, length = 100)
    public String getStationLinkman() {
        return stationLinkman;
    }

    public void setStationLinkman(String stationLinkman) {
        this.stationLinkman = stationLinkman;
    }

    @Basic
    @Column(name = "station_fax", nullable = true, length = 100)
    public String getStationFax() {
        return stationFax;
    }

    public void setStationFax(String stationFax) {
        this.stationFax = stationFax;
    }

    @Basic
    @Column(name = "vehicle_purchase_time", nullable = true)
    public Timestamp getVehiclePurchaseTime() {
        return vehiclePurchaseTime;
    }

    public void setVehiclePurchaseTime(Timestamp vehiclePurchaseTime) {
        this.vehiclePurchaseTime = vehiclePurchaseTime;
    }

    @Basic
    @Column(name = "service_code", nullable = true, length = 15)
    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    @Basic
    @Column(name = "service_name", nullable = true, length = 200)
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Basic
    @Column(name = "org_id", nullable = true, length = 30)
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Basic
    @Column(name = "customer_phone", nullable = true, length = 100)
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Basic
    @Column(name = "zip_code", nullable = true, length = 20)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Basic
    @Column(name = "customer_dept", nullable = true, length = 500)
    public String getCustomerDept() {
        return customerDept;
    }

    public void setCustomerDept(String customerDept) {
        this.customerDept = customerDept;
    }

    @Basic
    @Column(name = "fault_description", nullable = true, length = 1000)
    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    @Basic
    @Column(name = "reporting_date", nullable = true)
    public Timestamp getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(Timestamp reportingDate) {
        this.reportingDate = reportingDate;
    }

    @Basic
    @Column(name = "shipping_address", nullable = true, length = 500)
    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Basic
    @Column(name = "vehicle_linkman_phone", nullable = true, length = 100)
    public String getVehicleLinkmanPhone() {
        return vehicleLinkmanPhone;
    }

    public void setVehicleLinkmanPhone(String vehicleLinkmanPhone) {
        this.vehicleLinkmanPhone = vehicleLinkmanPhone;
    }

    @Basic
    @Column(name = "vehicle_engine_no", nullable = true, length = 50)
    public String getVehicleEngineNo() {
        return vehicleEngineNo;
    }

    public void setVehicleEngineNo(String vehicleEngineNo) {
        this.vehicleEngineNo = vehicleEngineNo;
    }

    @Basic
    @Column(name = "urgent_order_date", nullable = true)
    public Timestamp getUrgentOrderDate() {
        return urgentOrderDate;
    }

    public void setUrgentOrderDate(Timestamp urgentOrderDate) {
        this.urgentOrderDate = urgentOrderDate;
    }

    @Basic
    @Column(name = "is_claims_part", nullable = true, length = 1)
    public String getIsClaimsPart() {
        return isClaimsPart;
    }

    public void setIsClaimsPart(String isClaimsPart) {
        this.isClaimsPart = isClaimsPart;
    }

    @Basic
    @Column(name = "urgent_order_status", nullable = true, length = 20)
    public String getUrgentOrderStatus() {
        return urgentOrderStatus;
    }

    public void setUrgentOrderStatus(String urgentOrderStatus) {
        this.urgentOrderStatus = urgentOrderStatus;
    }

    @Basic
    @Column(name = "fault_location", nullable = true, length = 500)
    public String getFaultLocation() {
        return faultLocation;
    }

    public void setFaultLocation(String faultLocation) {
        this.faultLocation = faultLocation;
    }

    @Basic
    @Column(name = "shipping_type", nullable = true, length = 20)
    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    @Basic
    @Column(name = "driven_distance", nullable = true, length = 50)
    public String getDrivenDistance() {
        return drivenDistance;
    }

    public void setDrivenDistance(String drivenDistance) {
        this.drivenDistance = drivenDistance;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 100)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "failure_receiving_station", nullable = true, length = 500)
    public String getFailureReceivingStation() {
        return failureReceivingStation;
    }

    public void setFailureReceivingStation(String failureReceivingStation) {
        this.failureReceivingStation = failureReceivingStation;
    }

    @Basic
    @Column(name = "vehicle_chassis_number", nullable = true, length = 50)
    public String getVehicleChassisNumber() {
        return vehicleChassisNumber;
    }

    public void setVehicleChassisNumber(String vehicleChassisNumber) {
        this.vehicleChassisNumber = vehicleChassisNumber;
    }

    @Basic
    @Column(name = "urgent_customer_no", nullable = true, length = 15)
    public String getUrgentCustomerNo() {
        return urgentCustomerNo;
    }

    public void setUrgentCustomerNo(String urgentCustomerNo) {
        this.urgentCustomerNo = urgentCustomerNo;
    }

    @Basic
    @Column(name = "urgent_customer_name", nullable = true, length = 200)
    public String getUrgentCustomerName() {
        return urgentCustomerName;
    }

    public void setUrgentCustomerName(String urgentCustomerName) {
        this.urgentCustomerName = urgentCustomerName;
    }

    @Basic
    @Column(name = "stop_waiting", nullable = true, length = 1)
    public String getStopWaiting() {
        return stopWaiting;
    }

    public void setStopWaiting(String stopWaiting) {
        this.stopWaiting = stopWaiting;
    }

    @Basic
    @Column(name = "error_code", nullable = true, length = 20)
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Basic
    @Column(name = "error_msg", nullable = true, length = 2147483647)
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Basic
    @Column(name = "crm_purchase_id", nullable = true, length = 40)
    public String getCrmPurchaseId() {
        return crmPurchaseId;
    }

    public void setCrmPurchaseId(String crmPurchaseId) {
        this.crmPurchaseId = crmPurchaseId;
    }

    @Basic
    @Column(name = "crm_purchase_no", nullable = true, length = 40)
    public String getCrmPurchaseNo() {
        return crmPurchaseNo;
    }

    public void setCrmPurchaseNo(String crmPurchaseNo) {
        this.crmPurchaseNo = crmPurchaseNo;
    }

    @Basic
    @Column(name = "order_date", nullable = true)
    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    @Basic
    @Column(name = "order_status", nullable = true, length = 40)
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Basic
    @Column(name = "underpa_no", nullable = true, length = 50)
    public String getUnderpaNo() {
        return underpaNo;
    }

    public void setUnderpaNo(String underpaNo) {
        this.underpaNo = underpaNo;
    }

    @Basic
    @Column(name = "purchase_no", nullable = true, length = 40)
    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartPurchaseUrgentOrder that = (PartPurchaseUrgentOrder) o;

        if (urgentOrderId != null ? !urgentOrderId.equals(that.urgentOrderId) : that.urgentOrderId != null)
            return false;
        if (purchaseId != null ? !purchaseId.equals(that.purchaseId) : that.purchaseId != null) return false;
        if (appraisalNo != null ? !appraisalNo.equals(that.appraisalNo) : that.appraisalNo != null) return false;
        if (vehicleVno != null ? !vehicleVno.equals(that.vehicleVno) : that.vehicleVno != null) return false;
        if (saleOrderType != null ? !saleOrderType.equals(that.saleOrderType) : that.saleOrderType != null)
            return false;
        if (faultDate != null ? !faultDate.equals(that.faultDate) : that.faultDate != null) return false;
        if (receivingUnitsName != null ? !receivingUnitsName.equals(that.receivingUnitsName) : that.receivingUnitsName != null)
            return false;
        if (stationId != null ? !stationId.equals(that.stationId) : that.stationId != null) return false;
        if (stationLinkman != null ? !stationLinkman.equals(that.stationLinkman) : that.stationLinkman != null)
            return false;
        if (stationFax != null ? !stationFax.equals(that.stationFax) : that.stationFax != null) return false;
        if (vehiclePurchaseTime != null ? !vehiclePurchaseTime.equals(that.vehiclePurchaseTime) : that.vehiclePurchaseTime != null)
            return false;
        if (serviceCode != null ? !serviceCode.equals(that.serviceCode) : that.serviceCode != null) return false;
        if (serviceName != null ? !serviceName.equals(that.serviceName) : that.serviceName != null) return false;
        if (orgId != null ? !orgId.equals(that.orgId) : that.orgId != null) return false;
        if (customerPhone != null ? !customerPhone.equals(that.customerPhone) : that.customerPhone != null)
            return false;
        if (zipCode != null ? !zipCode.equals(that.zipCode) : that.zipCode != null) return false;
        if (customerDept != null ? !customerDept.equals(that.customerDept) : that.customerDept != null) return false;
        if (faultDescription != null ? !faultDescription.equals(that.faultDescription) : that.faultDescription != null)
            return false;
        if (reportingDate != null ? !reportingDate.equals(that.reportingDate) : that.reportingDate != null)
            return false;
        if (shippingAddress != null ? !shippingAddress.equals(that.shippingAddress) : that.shippingAddress != null)
            return false;
        if (vehicleLinkmanPhone != null ? !vehicleLinkmanPhone.equals(that.vehicleLinkmanPhone) : that.vehicleLinkmanPhone != null)
            return false;
        if (vehicleEngineNo != null ? !vehicleEngineNo.equals(that.vehicleEngineNo) : that.vehicleEngineNo != null)
            return false;
        if (urgentOrderDate != null ? !urgentOrderDate.equals(that.urgentOrderDate) : that.urgentOrderDate != null)
            return false;
        if (isClaimsPart != null ? !isClaimsPart.equals(that.isClaimsPart) : that.isClaimsPart != null) return false;
        if (urgentOrderStatus != null ? !urgentOrderStatus.equals(that.urgentOrderStatus) : that.urgentOrderStatus != null)
            return false;
        if (faultLocation != null ? !faultLocation.equals(that.faultLocation) : that.faultLocation != null)
            return false;
        if (shippingType != null ? !shippingType.equals(that.shippingType) : that.shippingType != null) return false;
        if (drivenDistance != null ? !drivenDistance.equals(that.drivenDistance) : that.drivenDistance != null)
            return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (failureReceivingStation != null ? !failureReceivingStation.equals(that.failureReceivingStation) : that.failureReceivingStation != null)
            return false;
        if (vehicleChassisNumber != null ? !vehicleChassisNumber.equals(that.vehicleChassisNumber) : that.vehicleChassisNumber != null)
            return false;
        if (urgentCustomerNo != null ? !urgentCustomerNo.equals(that.urgentCustomerNo) : that.urgentCustomerNo != null)
            return false;
        if (urgentCustomerName != null ? !urgentCustomerName.equals(that.urgentCustomerName) : that.urgentCustomerName != null)
            return false;
        if (stopWaiting != null ? !stopWaiting.equals(that.stopWaiting) : that.stopWaiting != null) return false;
        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;
        if (errorMsg != null ? !errorMsg.equals(that.errorMsg) : that.errorMsg != null) return false;
        if (crmPurchaseId != null ? !crmPurchaseId.equals(that.crmPurchaseId) : that.crmPurchaseId != null)
            return false;
        if (crmPurchaseNo != null ? !crmPurchaseNo.equals(that.crmPurchaseNo) : that.crmPurchaseNo != null)
            return false;
        if (orderDate != null ? !orderDate.equals(that.orderDate) : that.orderDate != null) return false;
        if (orderStatus != null ? !orderStatus.equals(that.orderStatus) : that.orderStatus != null) return false;
        if (underpaNo != null ? !underpaNo.equals(that.underpaNo) : that.underpaNo != null) return false;
        if (purchaseNo != null ? !purchaseNo.equals(that.purchaseNo) : that.purchaseNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = urgentOrderId != null ? urgentOrderId.hashCode() : 0;
        result = 31 * result + (purchaseId != null ? purchaseId.hashCode() : 0);
        result = 31 * result + (appraisalNo != null ? appraisalNo.hashCode() : 0);
        result = 31 * result + (vehicleVno != null ? vehicleVno.hashCode() : 0);
        result = 31 * result + (saleOrderType != null ? saleOrderType.hashCode() : 0);
        result = 31 * result + (faultDate != null ? faultDate.hashCode() : 0);
        result = 31 * result + (receivingUnitsName != null ? receivingUnitsName.hashCode() : 0);
        result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
        result = 31 * result + (stationLinkman != null ? stationLinkman.hashCode() : 0);
        result = 31 * result + (stationFax != null ? stationFax.hashCode() : 0);
        result = 31 * result + (vehiclePurchaseTime != null ? vehiclePurchaseTime.hashCode() : 0);
        result = 31 * result + (serviceCode != null ? serviceCode.hashCode() : 0);
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (orgId != null ? orgId.hashCode() : 0);
        result = 31 * result + (customerPhone != null ? customerPhone.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (customerDept != null ? customerDept.hashCode() : 0);
        result = 31 * result + (faultDescription != null ? faultDescription.hashCode() : 0);
        result = 31 * result + (reportingDate != null ? reportingDate.hashCode() : 0);
        result = 31 * result + (shippingAddress != null ? shippingAddress.hashCode() : 0);
        result = 31 * result + (vehicleLinkmanPhone != null ? vehicleLinkmanPhone.hashCode() : 0);
        result = 31 * result + (vehicleEngineNo != null ? vehicleEngineNo.hashCode() : 0);
        result = 31 * result + (urgentOrderDate != null ? urgentOrderDate.hashCode() : 0);
        result = 31 * result + (isClaimsPart != null ? isClaimsPart.hashCode() : 0);
        result = 31 * result + (urgentOrderStatus != null ? urgentOrderStatus.hashCode() : 0);
        result = 31 * result + (faultLocation != null ? faultLocation.hashCode() : 0);
        result = 31 * result + (shippingType != null ? shippingType.hashCode() : 0);
        result = 31 * result + (drivenDistance != null ? drivenDistance.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (failureReceivingStation != null ? failureReceivingStation.hashCode() : 0);
        result = 31 * result + (vehicleChassisNumber != null ? vehicleChassisNumber.hashCode() : 0);
        result = 31 * result + (urgentCustomerNo != null ? urgentCustomerNo.hashCode() : 0);
        result = 31 * result + (urgentCustomerName != null ? urgentCustomerName.hashCode() : 0);
        result = 31 * result + (stopWaiting != null ? stopWaiting.hashCode() : 0);
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        result = 31 * result + (errorMsg != null ? errorMsg.hashCode() : 0);
        result = 31 * result + (crmPurchaseId != null ? crmPurchaseId.hashCode() : 0);
        result = 31 * result + (crmPurchaseNo != null ? crmPurchaseNo.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (underpaNo != null ? underpaNo.hashCode() : 0);
        result = 31 * result + (purchaseNo != null ? purchaseNo.hashCode() : 0);
        return result;
    }
}
