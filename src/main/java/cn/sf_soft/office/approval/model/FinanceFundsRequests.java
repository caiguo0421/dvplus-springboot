package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

import cn.sf_soft.basedata.model.SysStations;

/**
 * 资金调入申请 FinanceFundsRequests entity. @author MyEclipse Persistence Tools
 */

public class FinanceFundsRequests extends
		ApproveDocuments<FinanceFundsRequestsInAccounts> implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1666840192129415776L;
	// Fields

	private String requestNo;
	private SysStations allocateStation;
	private Double requestAmount;
	private Timestamp requestTime;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;
	private Short allocateStatus;
	private Double allocateAmount;

	private String allocateStationMean;// 调出站点的名称

	// Constructors

	/** default constructor */
	public FinanceFundsRequests() {
	}

	/** minimal constructor */
	public FinanceFundsRequests(String documentNo, String stationId,
			Short status, SysStations allocateStation, Double requestAmount,
			Timestamp requestTime, String userId, String userNo,
			String userName, String departmentId, String departmentNo,
			String departmentName, String submitStationId,
			String submitStationName, Short allocateStatus,
			Double allocateAmount) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.status = status;
		this.allocateStation = allocateStation;
		this.requestAmount = requestAmount;
		this.requestTime = requestTime;
		this.userId = userId;
		this.userNo = userNo;
		this.userName = userName;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.submitStationId = submitStationId;
		this.submitStationName = submitStationName;
		this.allocateStatus = allocateStatus;
		this.allocateAmount = allocateAmount;
	}

	/** full constructor */
	public FinanceFundsRequests(String documentNo, String stationId,
			Short status, String requestNo, SysStations allocateStation,
			Double requestAmount, Timestamp requestTime, String userId,
			String userNo, String userName, String departmentId,
			String departmentNo, String departmentName, String submitStationId,
			String submitStationName, String remark, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime,
			Timestamp submitTime, String approverId, String approverNo,
			String approverName, Timestamp approveTime, Short allocateStatus,
			Double allocateAmount) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.status = status;
		this.requestNo = requestNo;
		this.allocateStation = allocateStation;
		this.requestAmount = requestAmount;
		this.requestTime = requestTime;
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
		this.allocateStatus = allocateStatus;
		this.allocateAmount = allocateAmount;
	}

	// Property accessors

	public String getRequestNo() {
		return this.requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public SysStations getAllocateStation() {
		return this.allocateStation;
	}

	public void setAllocateStation(SysStations allocateStation) {
		this.allocateStation = allocateStation;
	}

	public Double getRequestAmount() {
		return this.requestAmount;
	}

	public void setRequestAmount(Double requestAmount) {
		this.requestAmount = requestAmount;
	}

	public Timestamp getRequestTime() {
		return this.requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
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

	public Short getAllocateStatus() {
		return this.allocateStatus;
	}

	public void setAllocateStatus(Short allocateStatus) {
		this.allocateStatus = allocateStatus;
	}

	public Double getAllocateAmount() {
		return this.allocateAmount;
	}

	public void setAllocateAmount(Double allocateAmount) {
		this.allocateAmount = allocateAmount;
	}

	public void setAllocateStationMean(String allocateStationMean) {
		this.allocateStationMean = allocateStationMean;
	}

	public String getAllocateStationMean() {
		return allocateStationMean;
	}

}
