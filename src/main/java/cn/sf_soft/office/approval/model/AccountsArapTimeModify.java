package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * AccountsArapTimeModify entity. @author MyEclipse Persistence Tools
 */

public class AccountsArapTimeModify extends
		ApproveDocuments<AccountsArapTimeModifyDetail> implements
		java.io.Serializable {

	// Fields

	// private String documentNo;
	private String approver;
	// private Timestamp approveTime;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;
	// private String stationId;
	private String remark;
	private String imagesUrls;

	// private Short status;
	// private String approverId;
	// private String approverNo;
	// private String approverName;
	// private Timestamp submitTime;
	// private String submitStationId;
	// private String submitStationName;
	// private String userId;
	// private String userNo;
	// private String userName;
	// private String departmentId;
	// private String departmentNo;
	// private String departmentName;

	// Constructors
	// private Set<AccountsArapTimeModifyDetail> accountsArapTimeModifyDetail;
	/** default constructor */
	public AccountsArapTimeModify() {
	}

	/** minimal constructor */
	public AccountsArapTimeModify(String submitStationName) {
		this.submitStationName = submitStationName;
	}

	/** full constructor */
	public AccountsArapTimeModify(String approver, Timestamp approveTime,
			String creator, Timestamp createTime, String modifier,
			Timestamp modifyTime, String stationId, String remark,
			String imagesUrls, Short status, String approverId,
			String approverNo, String approverName, Timestamp submitTime,
			String submitStationId, String submitStationName, String userId,
			String userNo, String userName, String departmentId,
			String departmentNo, String departmentName) {
		this.approver = approver;
		this.approveTime = approveTime;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.stationId = stationId;
		this.remark = remark;
		this.imagesUrls = imagesUrls;
		this.status = status;
		this.approverId = approverId;
		this.approverNo = approverNo;
		this.approverName = approverName;
		this.submitTime = submitTime;
		this.submitStationId = submitStationId;
		this.submitStationName = submitStationName;
		this.userId = userId;
		this.userNo = userNo;
		this.userName = userName;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
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

	public String getApprover() {
		return this.approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	@Override
	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	@Override
	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
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

	@Override
	public String getStationId() {
		return this.stationId;
	}

	@Override
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImagesUrls() {
		return this.imagesUrls;
	}

	public void setImagesUrls(String imagesUrls) {
		this.imagesUrls = imagesUrls;
	}

	@Override
	public Short getStatus() {
		return this.status;
	}

	@Override
	public void setStatus(Short status) {
		this.status = status;
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
	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	@Override
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
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

	/*
	 * public Set<AccountsArapTimeModifyDetail>
	 * getAccountsArapTimeModifyDetail() { return accountsArapTimeModifyDetail;
	 * }
	 * 
	 * public void setAccountsArapTimeModifyDetail(
	 * Set<AccountsArapTimeModifyDetail> accountsArapTimeModifyDetail) {
	 * this.accountsArapTimeModifyDetail = accountsArapTimeModifyDetail; }
	 */
	@Override
	public String toString() {
		return "AccountsArapTimeModify [approver=" + approver
				+ ", approveTime=" + approveTime + ", creator=" + creator
				+ ", createTime=" + createTime + ", modifier=" + modifier
				+ ", modifyTime=" + modifyTime + ", remark=" + remark
				+ ", imagesUrls=" + imagesUrls + ", status=" + status + "]";
	}
}
