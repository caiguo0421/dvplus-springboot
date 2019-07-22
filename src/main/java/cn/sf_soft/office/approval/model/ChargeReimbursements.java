package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 车辆费用报销单，继承自审批单
 * 
 * @author king
 * @create 2012-12-8下午03:14:02
 */
public class ChargeReimbursements extends ApproveDocuments<ChargeReimbursementsDetails> implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 4504590071384069144L;
	private Double reimburseAmount;
	private Timestamp reimburseTime;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;

	private Double paidAmount;
	private Timestamp paidTime;
	private String chargeId;
	
	private Short reimbursementMode;//10是合同费用、20为新增费用
	
	private String vehicleBrand; //新增 caigx 20170313
	private String fileUrls;


	// Constructors

	/** default constructor */
	public ChargeReimbursements() {
	}

	/** minimal constructor */
	public ChargeReimbursements(String stationId, Short status, Double reimburseAmount, Timestamp reimburseTime,
			String userId, String userNo, String userName, String departmentId, String departmentNo,
			String departmentName, String submitStationId, String submitStationName, String documentNo) {
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
		this.documentNo = documentNo;
	}

	/** full constructor */
	public ChargeReimbursements(String stationId, Short status, Double reimburseAmount, Timestamp reimburseTime,
			String userId, String userNo, String userName, String departmentId, String departmentNo,
			String departmentName, String submitStationId, String submitStationName, String remark, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime, Timestamp submitTime, String approverId,
			String approverNo, String approverName, Timestamp approveTime, String documentNo, String fileUrls) {
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
		this.documentNo = documentNo;
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

	public Timestamp getPaidTime() {
		return paidTime;
	}

	public void setPaidTime(Timestamp paidTime) {
		this.paidTime = paidTime;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public Short getReimbursementMode() {
		return reimbursementMode;
	}

	public void setReimbursementMode(Short reimbursementMode) {
		this.reimbursementMode = reimbursementMode;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getFileUrls() {
		return fileUrls;
	}

	public void setFileUrls(String fileUrls) {
		this.fileUrls = fileUrls;
	}
}
