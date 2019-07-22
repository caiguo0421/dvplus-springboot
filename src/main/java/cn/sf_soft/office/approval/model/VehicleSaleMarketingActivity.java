package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Set;

/**
 * VehicleSaleMarketingActivity entity. @author MyEclipse Persistence Tools
 */

public class VehicleSaleMarketingActivity extends
		ApproveDocuments<VehicleSaleMarketingCharge> implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5153936829564878460L;

	private String marketingActivityCode;
	private Timestamp beginDate;
	private Timestamp endDate;
	private Double advanceMoney;
	private Double futureMoney;
	private Double factMoney;
	private Double supportMoney;
	private String marketingActivityName;
	private String content;
	private String marketingActivityMode;
	private Integer futurePersonCount;
	private Integer futurePersonCountOut;
	private Integer factPersonCount;
	private String remark;
	private Boolean isCounted;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;
	private String creditGiver;
	private String creditGiverId;
	private String activityDescription;

	private Set<VehicleSaleMarketingActivitySupporters> activitySupporters;// 活动赞助商
	private Set<VehicleSaleMarketingActivityDetails> activityDetails;// 活动信息

	// Constructors

	/** default constructor */
	public VehicleSaleMarketingActivity() {
	}

	/** minimal constructor */
	public VehicleSaleMarketingActivity(String documentNo, String stationId,
			String userId, String userNo, String userName, String departmentId,
			String departmentNo, String departmentName, String submitStationId,
			String submitStationName) {
		this.documentNo = documentNo;
		this.stationId = stationId;
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
	public VehicleSaleMarketingActivity(String documentNo, String stationId,
			String marketingActivityCode, Timestamp beginDate,
			Timestamp endDate, Double advanceMoney, Double futureMoney,
			Double factMoney, Double supportMoney,
			String marketingActivityName, String content,
			String marketingActivityMode, Integer futurePersonCount,
			Integer futurePersonCountOut, Integer factPersonCount,
			Short status, String remark, String userId, String userNo,
			String userName, String departmentId, String departmentNo,
			String departmentName, String submitStationId,
			String submitStationName, Boolean isCounted, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime,
			Timestamp submitTime, String approverId, String approverNo,
			String approverName, Timestamp approveTime, String creditGiver,
			String creditGiverId, String activityDescription) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.marketingActivityCode = marketingActivityCode;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.advanceMoney = advanceMoney;
		this.futureMoney = futureMoney;
		this.factMoney = factMoney;
		this.supportMoney = supportMoney;
		this.marketingActivityName = marketingActivityName;
		this.content = content;
		this.marketingActivityMode = marketingActivityMode;
		this.futurePersonCount = futurePersonCount;
		this.futurePersonCountOut = futurePersonCountOut;
		this.factPersonCount = factPersonCount;
		this.status = status;
		this.remark = remark;
		this.userId = userId;
		this.userNo = userNo;
		this.userName = userName;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.submitStationId = submitStationId;
		this.submitStationName = submitStationName;
		this.isCounted = isCounted;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.submitTime = submitTime;
		this.approverId = approverId;
		this.approverNo = approverNo;
		this.approverName = approverName;
		this.approveTime = approveTime;
		this.creditGiver = creditGiver;
		this.creditGiverId = creditGiverId;
		this.activityDescription = activityDescription;
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

	public String getMarketingActivityCode() {
		return this.marketingActivityCode;
	}

	public void setMarketingActivityCode(String marketingActivityCode) {
		this.marketingActivityCode = marketingActivityCode;
	}

	public Timestamp getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Timestamp beginDate) {
		this.beginDate = beginDate;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Double getAdvanceMoney() {
		return this.advanceMoney;
	}

	public void setAdvanceMoney(Double advanceMoney) {
		this.advanceMoney = advanceMoney;
	}

	public Double getFutureMoney() {
		return this.futureMoney;
	}

	public void setFutureMoney(Double futureMoney) {
		this.futureMoney = futureMoney;
	}

	public Double getFactMoney() {
		return this.factMoney;
	}

	public void setFactMoney(Double factMoney) {
		this.factMoney = factMoney;
	}

	public Double getSupportMoney() {
		return this.supportMoney;
	}

	public void setSupportMoney(Double supportMoney) {
		this.supportMoney = supportMoney;
	}

	public String getMarketingActivityName() {
		return this.marketingActivityName;
	}

	public void setMarketingActivityName(String marketingActivityName) {
		this.marketingActivityName = marketingActivityName;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMarketingActivityMode() {
		return this.marketingActivityMode;
	}

	public void setMarketingActivityMode(String marketingActivityMode) {
		this.marketingActivityMode = marketingActivityMode;
	}

	public Integer getFuturePersonCount() {
		return this.futurePersonCount;
	}

	public void setFuturePersonCount(Integer futurePersonCount) {
		this.futurePersonCount = futurePersonCount;
	}

	public Integer getFuturePersonCountOut() {
		return this.futurePersonCountOut;
	}

	public void setFuturePersonCountOut(Integer futurePersonCountOut) {
		this.futurePersonCountOut = futurePersonCountOut;
	}

	public Integer getFactPersonCount() {
		return this.factPersonCount;
	}

	public void setFactPersonCount(Integer factPersonCount) {
		this.factPersonCount = factPersonCount;
	}

	@Override
	public Short getStatus() {
		return this.status;
	}

	@Override
	public void setStatus(Short status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Boolean getIsCounted() {
		return this.isCounted;
	}

	public void setIsCounted(Boolean isCounted) {
		this.isCounted = isCounted;
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

	public String getCreditGiver() {
		return this.creditGiver;
	}

	public void setCreditGiver(String creditGiver) {
		this.creditGiver = creditGiver;
	}

	public String getCreditGiverId() {
		return this.creditGiverId;
	}

	public void setCreditGiverId(String creditGiverId) {
		this.creditGiverId = creditGiverId;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public void setActivitySupporters(
			Set<VehicleSaleMarketingActivitySupporters> activitySupporters) {
		this.activitySupporters = activitySupporters;
	}

	public Set<VehicleSaleMarketingActivitySupporters> getActivitySupporters() {
		return activitySupporters;
	}

	public Set<VehicleSaleMarketingActivityDetails> getActivityDetails() {
		return activityDetails;
	}

	public void setActivityDetails(
			Set<VehicleSaleMarketingActivityDetails> activityDetails) {
		this.activityDetails = activityDetails;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (chargeDetail != null) {
			Iterator<VehicleSaleMarketingCharge> it = chargeDetail.iterator();
			while (it.hasNext()) {
				sb.append("\n\t" + it.next().toString());
			}
			for (VehicleSaleMarketingActivitySupporters supporter : activitySupporters) {
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
}
