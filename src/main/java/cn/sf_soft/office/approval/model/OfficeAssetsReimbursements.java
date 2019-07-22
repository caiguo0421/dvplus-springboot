package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;
import java.util.Set;

import cn.sf_soft.basedata.model.SysStations;

/**
 * 资产费用 OfficeAssetsReimbursements entity. @author MyEclipse Persistence Tools
 */

public class OfficeAssetsReimbursements extends
		ApproveDocuments<OfficeAssetsReimbursementsDetails> implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 570075007087095800L;
	private Double reimburseAmount;
	private Timestamp reimburseTime;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;

	private Set<OfficeAssetsReimbursementsApportionments> apportionments;

	private Double paidAmount;
	private Timestamp paidTime;
	private String fileUrls;

	// private SysStations station;

	// Constructors

	/** default constructor */
	public OfficeAssetsReimbursements() {
	}

	/** minimal constructor */
	public OfficeAssetsReimbursements(String documentNo, String stationId,
			Short status, Double reimburseAmount, Timestamp reimburseTime,
			String userId, String userNo, String userName, String departmentId,
			String departmentNo, String departmentName, String submitStationId,
			String submitStationName, SysStations allocateStation) {
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
	public OfficeAssetsReimbursements(String documentNo, String stationId,
			Short status, Double reimburseAmount, Timestamp reimburseTime,
			String userId, String userNo, String userName, String departmentId,
			String departmentNo, String departmentName, String submitStationId,
			String submitStationName, String remark, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime,
			Timestamp submitTime, String approverId, String approverNo,
			String approverName, Timestamp approveTime, SysStations station, String fileUrls) {
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

	public Set<OfficeAssetsReimbursementsApportionments> getApportionments() {
		return apportionments;
	}

	public void setApportionments(
			Set<OfficeAssetsReimbursementsApportionments> apportionments) {
		this.apportionments = apportionments;
	}

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
