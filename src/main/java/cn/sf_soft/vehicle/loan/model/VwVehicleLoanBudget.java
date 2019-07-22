package cn.sf_soft.vehicle.loan.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 视图-预算
 * @author caigx
 *
 */
@Entity
@Table(name = "vw_vehicle_loan_budget")
public class VwVehicleLoanBudget implements java.io.Serializable {
	private static final long serialVersionUID = 2067963717496557483L;

	// Fields
	private String documentNo;
	private String stationId;
	private Short status;
	private Short loanMode;
	private String customerId;
	private String loanObjectId;
	private String agentId;
	private String saleContractNo;
	private String remark;
	private String creatorId;
	private Timestamp createTime;
	private String modifierId;
	private Timestamp modifyTime;
	private String approverId;
	private Timestamp approveTime;
	private Short loanType;
	private Short flowStatus;
	private String userId;
	private String departmentId;
	private String submitStationId;
	private Timestamp submitTime;
	private String approverNo;
	private String approverName;
	private String stationName;
	private String customerNo;
	private String customerName;
	private String customerProvince;
	private String customerCity;
	private String customerArea;
	private String customerAddress;
	private String customerPhone;
	private String customerMobile;
	private String customerCertificateType;
	private String customerCertificateNo;
	private String agentNo;
	private String agentName;
	private String loanObjectNo;
	private String loanObjectName;
	private String loanObjectProvince;
	private String loanObjectCity;
	private String loanObjectArea;
	private String loanObjectAddress;
	private String loanObjectPhone;
	private String loanObjectMobile;
	private String loanObjectCertificateType;
	private String loanObjectCertificateNo;
	private String loanObjectEducation;
	private String loanModeMeaning;
	private String creator;
	private String creatorNo;
	private String modifier;
	private Integer vehicleQuantity;
	private Double loanAmountTotv;
	private Double loanAmountTotc;
	private Double loanAmountTot;
	private Double firstPayTotv;
	private Double firstPayTotc;
	private Double firstPayTot;
	private String statusMeaning;
	private Boolean isSecondhand;
	private String loanTypeMeaning;
	private Double agentAmountTot;
	private Double loanAmountTott;
	private String flowStatusMeaning;
	private String userNo;
	private String userName;
	private String departmentNo;
	private String departmentName;
	private String submitStationName;
	private String vehicleBrand;

	// Constructors

	public VwVehicleLoanBudget() {
	}

	// Property accessors
	@Id
	@Column(name = "document_no", nullable = false, length = 40)
	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "station_id")
	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	@Column(name = "status", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "loan_mode", nullable = false)
	public Short getLoanMode() {
		return this.loanMode;
	}

	public void setLoanMode(Short loanMode) {
		this.loanMode = loanMode;
	}

	@Column(name = "customer_id")
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "loan_object_id")
	public String getLoanObjectId() {
		return this.loanObjectId;
	}

	public void setLoanObjectId(String loanObjectId) {
		this.loanObjectId = loanObjectId;
	}

	@Column(name = "agent_id")
	public String getAgentId() {
		return this.agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	@Column(name = "sale_contract_no", length = 40)
	public String getSaleContractNo() {
		return this.saleContractNo;
	}

	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "creator_id")
	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "create_time", nullable = false, length = 23)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "modifier_id")
	public String getModifierId() {
		return this.modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	@Column(name = "modify_time", length = 23)
	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "approver_id")
	public String getApproverId() {
		return this.approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	@Column(name = "approve_time", length = 23)
	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	@Column(name = "loan_type")
	public Short getLoanType() {
		return this.loanType;
	}

	public void setLoanType(Short loanType) {
		this.loanType = loanType;
	}

	@Column(name = "flow_status")
	public Short getFlowStatus() {
		return this.flowStatus;
	}

	public void setFlowStatus(Short flowStatus) {
		this.flowStatus = flowStatus;
	}

	@Column(name = "user_id")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "department_id")
	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "submit_station_id")
	public String getSubmitStationId() {
		return this.submitStationId;
	}

	public void setSubmitStationId(String submitStationId) {
		this.submitStationId = submitStationId;
	}

	@Column(name = "submit_time", length = 23)
	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	@Column(name = "approver_no")
	public String getApproverNo() {
		return this.approverNo;
	}

	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}

	@Column(name = "approver_name")
	public String getApproverName() {
		return this.approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	@Column(name = "station_name")
	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	@Column(name = "customer_no", length = 40)
	public String getCustomerNo() {
		return this.customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Column(name = "customer_name")
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "customer_province")
	public String getCustomerProvince() {
		return this.customerProvince;
	}

	public void setCustomerProvince(String customerProvince) {
		this.customerProvince = customerProvince;
	}

	@Column(name = "customer_city")
	public String getCustomerCity() {
		return this.customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	@Column(name = "customer_area")
	public String getCustomerArea() {
		return this.customerArea;
	}

	public void setCustomerArea(String customerArea) {
		this.customerArea = customerArea;
	}

	@Column(name = "customer_address")
	public String getCustomerAddress() {
		return this.customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	@Column(name = "customer_phone")
	public String getCustomerPhone() {
		return this.customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	@Column(name = "customer_mobile")
	public String getCustomerMobile() {
		return this.customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	@Column(name = "customer_certificate_type")
	public String getCustomerCertificateType() {
		return this.customerCertificateType;
	}

	public void setCustomerCertificateType(String customerCertificateType) {
		this.customerCertificateType = customerCertificateType;
	}

	@Column(name = "customer_certificate_no")
	public String getCustomerCertificateNo() {
		return this.customerCertificateNo;
	}

	public void setCustomerCertificateNo(String customerCertificateNo) {
		this.customerCertificateNo = customerCertificateNo;
	}

	@Column(name = "agent_no", length = 40)
	public String getAgentNo() {
		return this.agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	@Column(name = "agent_name")
	public String getAgentName() {
		return this.agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@Column(name = "loan_object_no", length = 40)
	public String getLoanObjectNo() {
		return this.loanObjectNo;
	}

	public void setLoanObjectNo(String loanObjectNo) {
		this.loanObjectNo = loanObjectNo;
	}

	@Column(name = "loan_object_name")
	public String getLoanObjectName() {
		return this.loanObjectName;
	}

	public void setLoanObjectName(String loanObjectName) {
		this.loanObjectName = loanObjectName;
	}

	@Column(name = "loan_object_province")
	public String getLoanObjectProvince() {
		return this.loanObjectProvince;
	}

	public void setLoanObjectProvince(String loanObjectProvince) {
		this.loanObjectProvince = loanObjectProvince;
	}

	@Column(name = "loan_object_city")
	public String getLoanObjectCity() {
		return this.loanObjectCity;
	}

	public void setLoanObjectCity(String loanObjectCity) {
		this.loanObjectCity = loanObjectCity;
	}

	@Column(name = "loan_object_area")
	public String getLoanObjectArea() {
		return this.loanObjectArea;
	}

	public void setLoanObjectArea(String loanObjectArea) {
		this.loanObjectArea = loanObjectArea;
	}

	@Column(name = "loan_object_address")
	public String getLoanObjectAddress() {
		return this.loanObjectAddress;
	}

	public void setLoanObjectAddress(String loanObjectAddress) {
		this.loanObjectAddress = loanObjectAddress;
	}

	@Column(name = "loan_object_phone")
	public String getLoanObjectPhone() {
		return this.loanObjectPhone;
	}

	public void setLoanObjectPhone(String loanObjectPhone) {
		this.loanObjectPhone = loanObjectPhone;
	}

	@Column(name = "loan_object_mobile")
	public String getLoanObjectMobile() {
		return this.loanObjectMobile;
	}

	public void setLoanObjectMobile(String loanObjectMobile) {
		this.loanObjectMobile = loanObjectMobile;
	}

	@Column(name = "loan_object_certificate_type")
	public String getLoanObjectCertificateType() {
		return this.loanObjectCertificateType;
	}

	public void setLoanObjectCertificateType(String loanObjectCertificateType) {
		this.loanObjectCertificateType = loanObjectCertificateType;
	}

	@Column(name = "loan_object_certificate_no")
	public String getLoanObjectCertificateNo() {
		return this.loanObjectCertificateNo;
	}

	public void setLoanObjectCertificateNo(String loanObjectCertificateNo) {
		this.loanObjectCertificateNo = loanObjectCertificateNo;
	}

	@Column(name = "loan_object_education")
	public String getLoanObjectEducation() {
		return this.loanObjectEducation;
	}

	public void setLoanObjectEducation(String loanObjectEducation) {
		this.loanObjectEducation = loanObjectEducation;
	}

	@Column(name = "loan_mode_meaning")
	public String getLoanModeMeaning() {
		return this.loanModeMeaning;
	}

	public void setLoanModeMeaning(String loanModeMeaning) {
		this.loanModeMeaning = loanModeMeaning;
	}

	@Column(name = "creator")
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "creator_no", length = 10)
	public String getCreatorNo() {
		return this.creatorNo;
	}

	public void setCreatorNo(String creatorNo) {
		this.creatorNo = creatorNo;
	}

	@Column(name = "modifier")
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "vehicle_quantity")
	public Integer getVehicleQuantity() {
		return this.vehicleQuantity;
	}

	public void setVehicleQuantity(Integer vehicleQuantity) {
		this.vehicleQuantity = vehicleQuantity;
	}

	@Column(name = "loan_amount_totv", scale = 4)
	public Double getLoanAmountTotv() {
		return this.loanAmountTotv;
	}

	public void setLoanAmountTotv(Double loanAmountTotv) {
		this.loanAmountTotv = loanAmountTotv;
	}

	@Column(name = "loan_amount_totc", scale = 4)
	public Double getLoanAmountTotc() {
		return this.loanAmountTotc;
	}

	public void setLoanAmountTotc(Double loanAmountTotc) {
		this.loanAmountTotc = loanAmountTotc;
	}

	@Column(name = "loan_amount_tot", scale = 4)
	public Double getLoanAmountTot() {
		return this.loanAmountTot;
	}

	public void setLoanAmountTot(Double loanAmountTot) {
		this.loanAmountTot = loanAmountTot;
	}

	@Column(name = "first_pay_totv", scale = 4)
	public Double getFirstPayTotv() {
		return this.firstPayTotv;
	}

	public void setFirstPayTotv(Double firstPayTotv) {
		this.firstPayTotv = firstPayTotv;
	}

	@Column(name = "first_pay_totc", scale = 4)
	public Double getFirstPayTotc() {
		return this.firstPayTotc;
	}

	public void setFirstPayTotc(Double firstPayTotc) {
		this.firstPayTotc = firstPayTotc;
	}

	@Column(name = "first_pay_tot", scale = 4)
	public Double getFirstPayTot() {
		return this.firstPayTot;
	}

	public void setFirstPayTot(Double firstPayTot) {
		this.firstPayTot = firstPayTot;
	}

	@Column(name = "status_meaning")
	public String getStatusMeaning() {
		return this.statusMeaning;
	}

	public void setStatusMeaning(String statusMeaning) {
		this.statusMeaning = statusMeaning;
	}

	@Column(name = "is_secondhand")
	public Boolean getIsSecondhand() {
		return this.isSecondhand;
	}

	public void setIsSecondhand(Boolean isSecondhand) {
		this.isSecondhand = isSecondhand;
	}

	@Column(name = "loan_type_meaning")
	public String getLoanTypeMeaning() {
		return this.loanTypeMeaning;
	}

	public void setLoanTypeMeaning(String loanTypeMeaning) {
		this.loanTypeMeaning = loanTypeMeaning;
	}

	@Column(name = "agent_amount_tot", precision = 38)
	public Double getAgentAmountTot() {
		return this.agentAmountTot;
	}

	public void setAgentAmountTot(Double agentAmountTot) {
		this.agentAmountTot = agentAmountTot;
	}

	@Column(name = "loan_amount_tott", precision = 38)
	public Double getLoanAmountTott() {
		return this.loanAmountTott;
	}

	public void setLoanAmountTott(Double loanAmountTott) {
		this.loanAmountTott = loanAmountTott;
	}

	@Column(name = "flow_status_meaning")
	public String getFlowStatusMeaning() {
		return this.flowStatusMeaning;
	}

	public void setFlowStatusMeaning(String flowStatusMeaning) {
		this.flowStatusMeaning = flowStatusMeaning;
	}

	@Column(name = "user_no", length = 10)
	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Column(name = "user_name")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "department_no", length = 40)
	public String getDepartmentNo() {
		return this.departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	@Column(name = "department_name")
	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Column(name = "submit_station_name")
	public String getSubmitStationName() {
		return this.submitStationName;
	}

	public void setSubmitStationName(String submitStationName) {
		this.submitStationName = submitStationName;
	}

	@Column(name = "vehicle_brand")
	public String getVehicleBrand() {
		return this.vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

}