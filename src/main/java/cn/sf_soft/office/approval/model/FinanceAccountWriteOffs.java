package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Set;

/**
 * FinanceAccountWriteOffs entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class FinanceAccountWriteOffs extends
		ApproveDocuments<FinanceAccountWriteOffsDetails> implements
		java.io.Serializable {

	// Fields

	private Short writeOffType;
	private String objectId;
	private String objectNo;
	private String objectName;
	private Double writeOffAmount;
	private Timestamp writeOffTime;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;

	private Set<FinanceAccountWriteOffsApportionments> apportionments;

	private String writeOffTypeMean;

	private String fileUrls;

	// Constructors

	/** default constructor */
	public FinanceAccountWriteOffs() {
	}

	/** minimal constructor */
	public FinanceAccountWriteOffs(String documentNo, String stationId,
			Short status, Short writeOffType, String objectId,
			String objectName, Double writeOffAmount, Timestamp writeOffTime,
			String userId, String userNo, String userName, String departmentId,
			String departmentNo, String departmentName, String submitStationId,
			String submitStationName) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.status = status;
		this.writeOffType = writeOffType;
		this.objectId = objectId;
		this.objectName = objectName;
		this.writeOffAmount = writeOffAmount;
		this.writeOffTime = writeOffTime;
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
	public FinanceAccountWriteOffs(String documentNo, String stationId,
			Short status, Short writeOffType, String objectId, String objectNo,
			String objectName, Double writeOffAmount, Timestamp writeOffTime,
			String userId, String userNo, String userName, String departmentId,
			String departmentNo, String departmentName, String submitStationId,
			String submitStationName, String remark, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime,
			Timestamp submitTime, String approverId, String approverNo,
			String approverName, Timestamp approveTime) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.status = status;
		this.writeOffType = writeOffType;
		this.objectId = objectId;
		this.objectNo = objectNo;
		this.objectName = objectName;
		this.writeOffAmount = writeOffAmount;
		this.writeOffTime = writeOffTime;
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

	public Short getWriteOffType() {
		return this.writeOffType;
	}

	public void setWriteOffType(Short writeOffType) {
		this.writeOffType = writeOffType;
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

	public Double getWriteOffAmount() {
		return this.writeOffAmount;
	}

	public void setWriteOffAmount(Double writeOffAmount) {
		this.writeOffAmount = writeOffAmount;
	}

	public Timestamp getWriteOffTime() {
		return this.writeOffTime;
	}

	public void setWriteOffTime(Timestamp writeOffTime) {
		this.writeOffTime = writeOffTime;
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

	public void setApportionments(
			Set<FinanceAccountWriteOffsApportionments> apportionments) {
		this.apportionments = apportionments;
	}

	public String getFileUrls() {
		return fileUrls;
	}

	public void setFileUrls(String fileUrls) {
		this.fileUrls = fileUrls;
	}

	public Set<FinanceAccountWriteOffsApportionments> getApportionments() {
		return apportionments;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (chargeDetail != null) {
			Iterator<FinanceAccountWriteOffsDetails> it = chargeDetail
					.iterator();
			while (it.hasNext()) {
				sb.append("\n\t" + it.next().toString());
			}
			for (FinanceAccountWriteOffsApportionments supporter : apportionments) {
				sb.append("\n\t" + supporter.toString());
			}
			return "ApproveDocuments [approverId=" + approverId
					+ ", approverName=" + approverName + ", approverNo="
					+ approverNo + ", departmentId=" + departmentId
					+ ", departmentName=" + departmentName + ", departmentNo="
					+ departmentNo + ", documentNo=" + documentNo
					+ ", stationId=" + stationId + ", status=" + status
					+ ", submitStationId=" + submitStationId
					+ ", submitStationName=" + submitStationName
					+ ", submitTime=" + submitTime + ", userId=" + userId
					+ ", userName=" + userName + ", userNo=" + userNo + "]"
					+ sb.toString();
		} else {
			return "ApproveDocuments [approverId=" + approverId
					+ ", approverName=" + approverName + ", approverNo="
					+ approverNo + ", departmentId=" + departmentId
					+ ", departmentName=" + departmentName + ", departmentNo="
					+ departmentNo + ", documentNo=" + documentNo
					+ ", stationId=" + stationId + ", status=" + status
					+ ", submitStationId=" + submitStationId
					+ ", submitStationName=" + submitStationName
					+ ", submitTime=" + submitTime + ", userId=" + userId
					+ ", userName=" + userName + ", userNo=" + userNo + "]";
		}
	}

	public void setWriteOffTypeMean(String writeOffTypeMean) {
		this.writeOffTypeMean = writeOffTypeMean;
	}

	public String getWriteOffTypeMean() {
		return writeOffTypeMean;
	}
}
