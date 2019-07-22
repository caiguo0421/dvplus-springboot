package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * FinanceLoanRequests entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("unchecked")
public class FinanceLoanRequests extends ApproveDocuments implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Short requestType;
	private String detailType;
	private Double requestAmount;
	private Timestamp requestTime;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;

	private String requestTypeMean;// 借款类型
	private Double paidAmount;
	private Timestamp paidTime;
	private String fileUrls;

	@Override
	public String toString() {
		return "FinanceLoanRequests [documentNo=" + documentNo + ", stationId="
				+ stationId + ", status=" + status + ", requestType="
				+ requestType + ", detailType=" + detailType
				+ ", requestAmount=" + requestAmount + ", requestTime="
				+ requestTime + ", userId=" + userId + ", userNo=" + userNo
				+ ", userName=" + userName + ", departmentId=" + departmentId
				+ ", departmentNo=" + departmentNo + ", departmentName="
				+ departmentName + ", submitStationId=" + submitStationId
				+ ", submitStationName=" + submitStationName + ", remark="
				+ remark + ", creator=" + creator + ", createTime="
				+ createTime + ", modifier=" + modifier + ", submitTime="
				+ submitTime + ", approverId=" + approverId + ", approverNo="
				+ approverNo + ", approverName=" + approverName
				+ ", approveTime=" + approveTime + ", requestTypeMean="
				+ requestTypeMean + "]";

	}

	// Constructors

	/** default constructor */
	public FinanceLoanRequests() {
	}

	/** minimal constructor */
	public FinanceLoanRequests(String documentNo, String stationId,
			Short status, Short requestType, Double requestAmount,
			Timestamp requestTime, String userId, String userNo,
			String userName, String departmentId, String departmentNo,
			String departmentName, String submitStationId,
			String submitStationName) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.status = status;
		this.requestType = requestType;
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
	}

	/** full constructor */
	public FinanceLoanRequests(String documentNo, String stationId,
			Short status, Short requestType, String detailType,
			Double requestAmount, Timestamp requestTime, String userId,
			String userNo, String userName, String departmentId,
			String departmentNo, String departmentName, String submitStationId,
			String submitStationName, String remark, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime,
			Timestamp submitTime, String approverId, String approverNo,
			String approverName, Timestamp approveTime) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.status = status;
		this.requestType = requestType;
		this.detailType = detailType;
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

	public Short getRequestType() {
		return this.requestType;
	}

	public void setRequestType(Short requestType) {
		this.requestType = requestType;
	}

	public String getDetailType() {
		return this.detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
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

	@Override
	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	@Override
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
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

	public void setRequestTypeMean(String requestTypeMean) {
		this.requestTypeMean = requestTypeMean;
	}

	public String getRequestTypeMean() {
		return requestTypeMean;
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
