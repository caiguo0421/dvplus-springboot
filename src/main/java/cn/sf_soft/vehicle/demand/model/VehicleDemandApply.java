package cn.sf_soft.vehicle.demand.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.sf_soft.office.approval.model.ApproveDocuments;

/**
 * 资源需求申报
 * 
 * @author caigx
 *
 */
@SuppressWarnings("rawtypes")
@Entity
@Table(name = "vehicle_demand_apply")
public class VehicleDemandApply extends ApproveDocuments {
	private static final long serialVersionUID = 4104532818440684685L;

	// Fields
	private String demandId;
	private String orderNo;
	private String agentCode;
	private String linkman;
	private String linkmanPhone;
	private String reportPerson;
	private Timestamp reportTime;
	private Short uploadStatus;
	private String crmStatus;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private Timestamp modifyTime;
	private String modifier;
//	private String documentNo;
//	private String stationId;
//	private Short status;
//	private String userId;
//	private String userNo;
//	private String userName;
//	private String departmentId;
//	private String departmentNo;
//	private String departmentName;
//	private String submitStationId;
//	private String submitStationName;
//	private Timestamp submitTime;
//	private String approverId;
//	private String approverNo;
//	private String approverName;
//	private Timestamp approveTime;
	private String errorCode;
	private String errorMsg;
	private String dfObjId;
	private String orderType;

	// Constructors

	public VehicleDemandApply() {
	}

	// Property accessors
	@Id
	@Column(name = "demand_id", unique = true, nullable = false, length = 40)
	public String getDemandId() {
		return this.demandId;
	}

	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	@Column(name = "order_no")
	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "agent_code")
	public String getAgentCode() {
		return this.agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	@Column(name = "linkman")
	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "linkman_phone")
	public String getLinkmanPhone() {
		return this.linkmanPhone;
	}

	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}

	@Column(name = "report_person")
	public String getReportPerson() {
		return this.reportPerson;
	}

	public void setReportPerson(String reportPerson) {
		this.reportPerson = reportPerson;
	}

	@Column(name = "report_time", length = 23)
	public Timestamp getReportTime() {
		return this.reportTime;
	}

	public void setReportTime(Timestamp reportTime) {
		this.reportTime = reportTime;
	}

	@Column(name = "upload_status", nullable = false)
	public Short getUploadStatus() {
		return this.uploadStatus;
	}

	public void setUploadStatus(Short uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	@Column(name = "crm_status")
	public String getCrmStatus() {
		return this.crmStatus;
	}

	public void setCrmStatus(String crmStatus) {
		this.crmStatus = crmStatus;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "creator")
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "create_time", length = 23)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "modify_time", length = 23)
	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "modifier")
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "document_no")
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

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "user_id")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "user_no")
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

	@Column(name = "department_id")
	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "department_no")
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

	@Column(name = "submit_station_id")
	public String getSubmitStationId() {
		return this.submitStationId;
	}

	public void setSubmitStationId(String submitStationId) {
		this.submitStationId = submitStationId;
	}

	@Column(name = "submit_station_name")
	public String getSubmitStationName() {
		return this.submitStationName;
	}

	public void setSubmitStationName(String submitStationName) {
		this.submitStationName = submitStationName;
	}

	@Column(name = "submit_time", length = 23)
	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	@Column(name = "approver_id")
	public String getApproverId() {
		return this.approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
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

	@Column(name = "approve_time", length = 23)
	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	@Column(name = "error_code")
	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name = "error_msg")
	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Column(name = "DF_objId")
	public String getDfObjId() {
		return this.dfObjId;
	}

	public void setDfObjId(String dfObjId) {
		this.dfObjId = dfObjId;
	}

	@Column(name = "order_type")
	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}