package cn.sf_soft.oa.notification.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * 审批单-发送通知视图
 * @author caiguoxin
 *
 */
@Entity
@Table(name = "vw_approve_documents_notification")
public class VwApproveDocumentsNotification implements java.io.Serializable,Cloneable  {
	
	private static final long serialVersionUID = -3136577577507935652L;
	// Fields
	private String documentId;
	private String stationId;
	private Short status;
	private String moduleId;
	private String moduleName;
	private String documentNo;
	private String userId;
	private String userNo;
	private String userName;
	private String departmentId;
	private String departmentNo;
	private String departmentName;
	private String submitStationId;
	private String submitStationName;
	private Timestamp submitTime;
	private Integer approveLevel;
	private String approveName;
	private String approverId;
	private String approverNo;
	private String approverName;
	private Timestamp revokeTime;
	private Timestamp invalidTime;
	private String oadpId;

	// Constructors

	/** default constructor */
	public VwApproveDocumentsNotification() {
	}

	@Override
	public VwApproveDocumentsNotification clone() throws CloneNotSupportedException {
		return  (VwApproveDocumentsNotification)super.clone();
    }  

	// Property accessors
	@Id
	@Column(name = "document_id", nullable = false, length = 40)
	public String getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
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

	@Column(name = "module_id", length = 10)
	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "module_name")
	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name = "document_no", length = 40)
	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
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

	@Column(name = "submit_time",  length = 23)
	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	@Column(name = "approve_level")
	public Integer getApproveLevel() {
		return this.approveLevel;
	}

	public void setApproveLevel(Integer approveLevel) {
		this.approveLevel = approveLevel;
	}

	@Column(name = "approve_name")
	public String getApproveName() {
		return this.approveName;
	}

	public void setApproveName(String approveName) {
		this.approveName = approveName;
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

	@Column(name = "revoke_time", length = 23)
	public Timestamp getRevokeTime() {
		return this.revokeTime;
	}

	public void setRevokeTime(Timestamp revokeTime) {
		this.revokeTime = revokeTime;
	}

	@Column(name = "invalid_time", length = 23)
	public Timestamp getInvalidTime() {
		return this.invalidTime;
	}

	public void setInvalidTime(Timestamp invalidTime) {
		this.invalidTime = invalidTime;
	}

	@Column(name = "oadp_id",  length = 40)
	public String getOadpId() {
		return this.oadpId;
	}

	public void setOadpId(String oadpId) {
		this.oadpId = oadpId;
	}

	}
