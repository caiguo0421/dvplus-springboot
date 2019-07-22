package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * FinanceSalaries entity. @author MyEclipse Persistence Tools
 */

public class FinanceSalaries extends ApproveDocuments<FinanceSalariesDetails> {

	// Fields

	private static final long serialVersionUID = -6490974200808555005L;
	// String stationId;
	// private Short status;
	private Timestamp payMonth;
	private String objectId;
	private String objectNo;
	private String objectName;
	private Double payAmount;
	private Integer payNumber;
	private Timestamp payTime;
	// private String userId;
	// private String userNo;
	// private String userName;
	// private String departmentId;
	// private String departmentNo;
	// private String departmentName;
	// private String submitStationId;
	// private String submitStationName;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;

	// private Timestamp modifyTime;
	// private Timestamp submitTime;
	// private String approverId;
	// private String approverNo;
	// private String approverName;
	// private Timestamp approveTime;

	// Constructors

	/** default constructor */
	public FinanceSalaries() {
	}

	/** minimal constructor */

	public FinanceSalaries(String stationId, Short status, Timestamp payMonth,
			String objectId, String objectName, Double payAmount,
			Integer payNumber, Timestamp payTime, String userId, String userNo,
			String userName, String departmentId, String departmentNo,
			String departmentName, String submitStationId,
			String submitStationName) {
		this.stationId = stationId;
		this.status = status;
		this.payMonth = payMonth;
		this.objectId = objectId;
		this.objectName = objectName;
		this.payAmount = payAmount;
		this.payNumber = payNumber;
		this.payTime = payTime;
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
	public FinanceSalaries(String stationId, Short status, Timestamp payMonth,
			String objectId, String objectNo, String objectName,
			Double payAmount, Integer payNumber, Timestamp payTime,
			String userId, String userNo, String userName, String departmentId,
			String departmentNo, String departmentName, String submitStationId,
			String submitStationName, String remark, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime,
			Timestamp submitTime, String approverId, String approverNo,
			String approverName, Timestamp approveTime) {
		this.stationId = stationId;
		this.status = status;
		this.payMonth = payMonth;
		this.objectId = objectId;
		this.objectNo = objectNo;
		this.objectName = objectName;
		this.payAmount = payAmount;
		this.payNumber = payNumber;
		this.payTime = payTime;
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

	@Override
	public String getDocumentNo() {
		return this.documentNo;
	}

	@Override
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	@Override
	public String getStationId() {
		return this.stationId;
	}

	@Override
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	@Override
	public Short getStatus() {
		return this.status;
	}

	@Override
	public void setStatus(Short status) {
		this.status = status;
	}

	public Timestamp getPayMonth() {
		return this.payMonth;
	}

	public void setPayMonth(Timestamp payMonth) {
		this.payMonth = payMonth;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectNo() {
		return this.objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public Double getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getPayNumber() {
		return this.payNumber;
	}

	public void setPayNumber(Integer payNumber) {
		this.payNumber = payNumber;
	}

	public Timestamp getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getUserNo() {
		return this.userNo;
	}

	@Override
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Override
	public String getUserName() {
		return this.userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getDepartmentId() {
		return this.departmentId;
	}

	@Override
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	public String getDepartmentNo() {
		return this.departmentNo;
	}

	@Override
	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	@Override
	public String getDepartmentName() {
		return this.departmentName;
	}

	@Override
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public String getSubmitStationId() {
		return this.submitStationId;
	}

	@Override
	public void setSubmitStationId(String submitStationId) {
		this.submitStationId = submitStationId;
	}

	@Override
	public String getSubmitStationName() {
		return this.submitStationName;
	}

	@Override
	public void setSubmitStationName(String submitStationName) {
		this.submitStationName = submitStationName;
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

	@Override
	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	@Override
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	@Override
	public String getApproverId() {
		return this.approverId;
	}

	@Override
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	@Override
	public String getApproverNo() {
		return this.approverNo;
	}

	@Override
	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}

	@Override
	public String getApproverName() {
		return this.approverName;
	}

	@Override
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	@Override
	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	@Override
	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

}
