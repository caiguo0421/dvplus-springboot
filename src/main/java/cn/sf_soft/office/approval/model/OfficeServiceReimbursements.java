package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 维修费用报销 OfficeServiceReimbursements entity. @author MyEclipse Persistence
 * Tools
 */

public class OfficeServiceReimbursements extends
		ApproveDocuments<OfficeServiceReimbursementsDetails> implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -815191913839359075L;
	private Double reimburseAmount;
	private Timestamp reimburseTime;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;

	private Double paidAmount;
	private Timestamp paidTime;
	private String fileUrls;

	// Constructors

	/** default constructor */
	public OfficeServiceReimbursements() {
	}

	/** minimal constructor */
	public OfficeServiceReimbursements(String documentNo, String stationId,
			Short status, Double reimburseAmount, Timestamp reimburseTime,
			String userId, String userNo, String userName, String departmentId,
			String departmentNo, String departmentName, String submitStationId,
			String submitStationName) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.status = status;
		this.reimburseAmount = reimburseAmount;
		this.reimburseTime = reimburseTime;
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
	public OfficeServiceReimbursements(String documentNo, String stationId,
			Short status, Double reimburseAmount, Timestamp reimburseTime,
			String userId, String userNo, String userName, String departmentId,
			String departmentNo, String departmentName, String submitStationId,
			String submitStationName, String remark, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime,
			Timestamp submitTime, String approverId, String approverNo,
			String approverName, Timestamp approveTime, String fileUrls) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.status = status;
		this.reimburseAmount = reimburseAmount;
		this.reimburseTime = reimburseTime;
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
		this.fileUrls = fileUrls;
	}

	// Property accessors

	public Double getReimburseAmount() {
		return this.reimburseAmount;
	}

	public void setReimburseAmount(Double reimburseAmount) {
		this.reimburseAmount = reimburseAmount;
	}

	public Timestamp getReimburseTime() {
		return this.reimburseTime;
	}

	public void setReimburseTime(Timestamp reimburseTime) {
		this.reimburseTime = reimburseTime;
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

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Timestamp getPaidTime() {
		return paidTime;
	}

	public void setPaidTime(Timestamp paidTime) {
		this.paidTime = paidTime;
	}

	public String getFileUrls() {
		return fileUrls;
	}

	public void setFileUrls(String fileUrls) {
		this.fileUrls = fileUrls;
	}
}
