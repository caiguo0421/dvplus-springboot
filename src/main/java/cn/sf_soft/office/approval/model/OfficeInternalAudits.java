package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 内部审计 OfficeInternalAudits entity. @author MyEclipse Persistence Tools
 */

public class OfficeInternalAudits extends
		ApproveDocuments<OfficeInternalAuditsDetails> implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6680706118263368094L;
	private Double auditAmount;
	private Timestamp auditTime;
	private Double bearAmount;
	private String bearerId;
	private String bearerNo;
	private String bearerName;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;

	// private Timestamp modifyTime;

	// Constructors

	/** default constructor */
	public OfficeInternalAudits() {
	}

	/** minimal constructor */
	public OfficeInternalAudits(String documentNo, String stationId,
			Short status, Double auditAmount, Timestamp auditTime,
			Double bearAmount, String userId, String userNo, String userName,
			String departmentId, String departmentNo, String departmentName,
			String submitStationId, String submitStationName) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.status = status;
		this.auditAmount = auditAmount;
		this.auditTime = auditTime;
		this.bearAmount = bearAmount;
		this.userId = userId;
		this.userNo = userNo;
		this.userName = userName;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.submitStationId = submitStationId;
		this.submitStationName = submitStationName;
	}

	/** full constructor */
	public OfficeInternalAudits(String documentNo, String stationId,
			Short status, Double auditAmount, Timestamp auditTime,
			Double bearAmount, String bearerId, String bearerNo,
			String bearerName, String userId, String userNo, String userName,
			String departmentId, String departmentNo, String departmentName,
			String submitStationId, String submitStationName, String remark,
			String creator, Timestamp createTime, String modifier,
			Timestamp modifyTime, Timestamp submitTime, String approverId,
			String approverNo, String approverName, Timestamp approveTime) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.status = status;
		this.auditAmount = auditAmount;
		this.auditTime = auditTime;
		this.bearAmount = bearAmount;
		this.bearerId = bearerId;
		this.bearerNo = bearerNo;
		this.bearerName = bearerName;
		this.userId = userId;
		this.userNo = userNo;
		this.userName = userName;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.submitStationId = submitStationId;
		this.submitStationName = submitStationName;
		this.remark = remark;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.submitTime = submitTime;
		this.approverId = approverId;
		this.approverNo = approverNo;
		this.approverName = approverName;
		this.approveTime = approveTime;
	}

	// Property accessors

	public Double getAuditAmount() {
		return this.auditAmount;
	}

	public void setAuditAmount(Double auditAmount) {
		this.auditAmount = auditAmount;
	}

	public Timestamp getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	public Double getBearAmount() {
		return this.bearAmount;
	}

	public void setBearAmount(Double bearAmount) {
		this.bearAmount = bearAmount;
	}

	public String getBearerId() {
		return this.bearerId;
	}

	public void setBearerId(String bearerId) {
		this.bearerId = bearerId;
	}

	public String getBearerNo() {
		return this.bearerNo;
	}

	public void setBearerNo(String bearerNo) {
		this.bearerNo = bearerNo;
	}

	public String getBearerName() {
		return this.bearerName;
	}

	public void setBearerName(String bearerName) {
		this.bearerName = bearerName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/*
	 * @Override public Timestamp getModifyTime() { return this.modifyTime; }
	 * 
	 * @Override public void setModifyTime(Timestamp modifyTime) {
	 * this.modifyTime = modifyTime; }
	 */

}
