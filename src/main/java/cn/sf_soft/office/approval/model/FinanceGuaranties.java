package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * FinanceGuaranties entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("unchecked")
public class FinanceGuaranties extends
		ApproveDocuments<FinanceGuarantiesAdditions> implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2458399815599739747L;

	private Boolean rorp;
	private String objectId;
	private String objectNo;
	private String objectName;
	private Double guarantyAmount;
	private Integer guarantyPeriod;
	private Short periodUnit;
	private Timestamp guarantyTime;
	private Timestamp expireTime;
	private Timestamp repayTime;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;
	private Double finalAmount;

	private String periodUnitMean;// 担保期单位

	// Constructors

	/** default constructor */
	public FinanceGuaranties() {
	}

	/** minimal constructor */
	public FinanceGuaranties(String documentNo, String stationId, Boolean rorp,
			Short status, String objectId, String objectName,
			Double guarantyAmount, Integer guarantyPeriod, Short periodUnit,
			Timestamp guarantyTime, Timestamp expireTime, Timestamp repayTime,
			String userId, String userNo, String userName, String departmentId,
			String departmentNo, String departmentName, String submitStationId,
			String submitStationName, Double finalAmount) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.rorp = rorp;
		this.status = status;
		this.objectId = objectId;
		this.objectName = objectName;
		this.guarantyAmount = guarantyAmount;
		this.guarantyPeriod = guarantyPeriod;
		this.periodUnit = periodUnit;
		this.guarantyTime = guarantyTime;
		this.expireTime = expireTime;
		this.repayTime = repayTime;
		this.userId = userId;
		this.userNo = userNo;
		this.userName = userName;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.submitStationId = submitStationId;
		this.submitStationName = submitStationName;
		this.finalAmount = finalAmount;
	}

	/** full constructor */
	public FinanceGuaranties(String documentNo, String stationId, Boolean rorp,
			Short status, String objectId, String objectNo, String objectName,
			Double guarantyAmount, Integer guarantyPeriod, Short periodUnit,
			Timestamp guarantyTime, Timestamp expireTime, Timestamp repayTime,
			String userId, String userNo, String userName, String departmentId,
			String departmentNo, String departmentName, String submitStationId,
			String submitStationName, String remark, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime,
			Timestamp submitTime, String approverId, String approverNo,
			String approverName, Timestamp approveTime, Double finalAmount) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.rorp = rorp;
		this.status = status;
		this.objectId = objectId;
		this.objectNo = objectNo;
		this.objectName = objectName;
		this.guarantyAmount = guarantyAmount;
		this.guarantyPeriod = guarantyPeriod;
		this.periodUnit = periodUnit;
		this.guarantyTime = guarantyTime;
		this.expireTime = expireTime;
		this.repayTime = repayTime;
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
		this.finalAmount = finalAmount;
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

	public Boolean getRorp() {
		return this.rorp;
	}

	public void setRorp(Boolean rorp) {
		this.rorp = rorp;
	}

	@Override
	public Short getStatus() {
		return this.status;
	}

	@Override
	public void setStatus(Short status) {
		this.status = status;
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

	public Double getGuarantyAmount() {
		return this.guarantyAmount;
	}

	public void setGuarantyAmount(Double guarantyAmount) {
		this.guarantyAmount = guarantyAmount;
	}

	public Integer getGuarantyPeriod() {
		return this.guarantyPeriod;
	}

	public void setGuarantyPeriod(Integer guarantyPeriod) {
		this.guarantyPeriod = guarantyPeriod;
	}

	public Short getPeriodUnit() {
		return this.periodUnit;
	}

	public void setPeriodUnit(Short periodUnit) {
		this.periodUnit = periodUnit;
	}

	public Timestamp getGuarantyTime() {
		return this.guarantyTime;
	}

	public void setGuarantyTime(Timestamp guarantyTime) {
		this.guarantyTime = guarantyTime;
	}

	public Timestamp getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(Timestamp expireTime) {
		this.expireTime = expireTime;
	}

	public Timestamp getRepayTime() {
		return this.repayTime;
	}

	public void setRepayTime(Timestamp repayTime) {
		this.repayTime = repayTime;
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

	public Double getFinalAmount() {
		return this.finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	@Override
	public String toString() {
		return "FinanceGuaranties [approveTime=" + approveTime
				+ ", approverId=" + approverId + ", approverName="
				+ approverName + ", approverNo=" + approverNo + ", createTime="
				+ createTime + ", creator=" + creator + ", departmentId="
				+ departmentId + ", departmentName=" + departmentName
				+ ", departmentNo=" + departmentNo + ", documentNo="
				+ documentNo + ", expireTime=" + expireTime + ", finalAmount="
				+ finalAmount + ", guarantyAmount=" + guarantyAmount
				+ ", guarantyPeriod=" + guarantyPeriod + ", guarantyTime="
				+ guarantyTime + ", modifier=" + modifier + ", modifyTime="
				+ modifyTime + ", objectId=" + objectId + ", objectName="
				+ objectName + ", objectNo=" + objectNo + ", periodUnit="
				+ periodUnit + ", remark=" + remark + ", repayTime="
				+ repayTime + ", rorp=" + rorp + ", stationId=" + stationId
				+ ", status=" + status + ", submitStationId=" + submitStationId
				+ ", submitStationName=" + submitStationName + ", submitTime="
				+ submitTime + ", userId=" + userId + ", userName=" + userName
				+ ", userNo=" + userNo + ", periodUnitMean=" + periodUnitMean
				+ "]";
	}

	public void setPeriodUnitMean(String periodUnitMean) {
		this.periodUnitMean = periodUnitMean;
	}

	public String getPeriodUnitMean() {
		return periodUnitMean;
	}

}
