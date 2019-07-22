package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;


/**
 * 合同变更主表
 * @author caigx
 *
 */
public class VehicleSaleContractsVary extends ApproveDocuments {


	private static final long serialVersionUID = -215089345317455204L;
//	private String documentNo;
	private String stationId;
	private String contractNo;
	private Double oriContractMoney;
	private Double oriCommissionAmt;
	private Double priceVary;
	private Double commissionAmtVary;
	private Double priceRequest;
	private Integer varyCount;
	private String remark;
	private String version;
//	private Short status;
//	private String approverName;
	private Timestamp approveTime;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
//	private String userId;
//	private String userNo;
//	private String userName;
//	private String departmentId;
//	private String departmentNo;
//	private String departmentName;
//	private String submitStationId;
//	private String submitStationName;
//	private String approverId;
//	private String approverNo;
//	private Timestamp submitTime;


	// Constructors
	public VehicleSaleContractsVary() {
	}


	// Property accessors
	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Double getOriContractMoney() {
		return this.oriContractMoney;
	}

	public void setOriContractMoney(Double oriContractMoney) {
		this.oriContractMoney = oriContractMoney;
	}

	public Double getOriCommissionAmt() {
		return this.oriCommissionAmt;
	}

	public void setOriCommissionAmt(Double oriCommissionAmt) {
		this.oriCommissionAmt = oriCommissionAmt;
	}

	public Double getPriceVary() {
		return this.priceVary;
	}

	public void setPriceVary(Double priceVary) {
		this.priceVary = priceVary;
	}

	public Double getCommissionAmtVary() {
		return this.commissionAmtVary;
	}

	public void setCommissionAmtVary(Double commissionAmtVary) {
		this.commissionAmtVary = commissionAmtVary;
	}

	public Double getPriceRequest() {
		return this.priceRequest;
	}

	public void setPriceRequest(Double priceRequest) {
		this.priceRequest = priceRequest;
	}

	public Integer getVaryCount() {
		return this.varyCount;
	}

	public void setVaryCount(Integer varyCount) {
		this.varyCount = varyCount;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getApproverName() {
		return this.approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Timestamp getApproveTime() {
		return this.approveTime;
	}

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

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentNo() {
		return this.departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getSubmitStationId() {
		return this.submitStationId;
	}

	public void setSubmitStationId(String submitStationId) {
		this.submitStationId = submitStationId;
	}

	public String getSubmitStationName() {
		return this.submitStationName;
	}

	public void setSubmitStationName(String submitStationName) {
		this.submitStationName = submitStationName;
	}

	public String getApproverId() {
		return this.approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getApproverNo() {
		return this.approverNo;
	}

	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}

	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}


}
