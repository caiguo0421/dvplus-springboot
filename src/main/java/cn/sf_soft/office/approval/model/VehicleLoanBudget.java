package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 消贷费用预算
 * @author caigx
 *
 */
@SuppressWarnings("rawtypes")
public class VehicleLoanBudget extends ApproveDocuments {
	private static final long serialVersionUID = 593749562188419607L;
	
	//field
//	private String documentNo;
//	private String stationId;
//	private Short status;
	private Short loanMode;
	private String customerId;
	private String loanObjectId;
	private String agentId;
	private String saleContractNo;
	private String remark;
	private String creatorId;
	private Timestamp createTime;
	private String modifierId;
//	private Timestamp modifyTime;
//	private String approverId;
//	private Timestamp approveTime;
	private Short loanType;
	private Short flowStatus;
//	private String userId;
//	private String departmentId;
//	private String submitStationId;
//	private Timestamp submitTime;
//	private String approverNo;
//	private String approverName;
	private String fileUrls;

	// Constructors

	public VehicleLoanBudget() {
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

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getLoanMode() {
		return this.loanMode;
	}

	public void setLoanMode(Short loanMode) {
		this.loanMode = loanMode;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getLoanObjectId() {
		return this.loanObjectId;
	}

	public void setLoanObjectId(String loanObjectId) {
		this.loanObjectId = loanObjectId;
	}

	public String getAgentId() {
		return this.agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getSaleContractNo() {
		return this.saleContractNo;
	}

	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getModifierId() {
		return this.modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getApproverId() {
		return this.approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public Short getLoanType() {
		return this.loanType;
	}

	public void setLoanType(Short loanType) {
		this.loanType = loanType;
	}

	public Short getFlowStatus() {
		return this.flowStatus;
	}

	public void setFlowStatus(Short flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getSubmitStationId() {
		return this.submitStationId;
	}

	public void setSubmitStationId(String submitStationId) {
		this.submitStationId = submitStationId;
	}

	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	public String getApproverNo() {
		return this.approverNo;
	}

	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}

	public String getApproverName() {
		return this.approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getFileUrls() {
		return fileUrls;
	}

	public void setFileUrls(String fileUrls) {
		this.fileUrls = fileUrls;
	}
}