package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;
import java.util.Set;

/**
 * AccountsReceivableInstalment entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class AccountsReceivableInstalment extends
		ApproveDocuments<VehicleLoanStandingBooks> implements
		java.io.Serializable {

	// Fields

	// private String documentNo;
	private String objectId;
	private String objectNo;
	private String objectName;
	private String loanUse;
	private String vehicleCardNo;
	private Double arAmount;
	private Double arPrincipal;
	private Double arInterest;
	private Double arAmountDue;
	private Double arPrincipalDue;
	private Double arInterestDue;
	private Double arAmountOri;
	private Double arPrincipalOri;
	private Double arInterestOri;
	private Integer periodNumber;
	private Double monthRate;
	private Short rateType;
	private Timestamp firstPayDate;
	private String warranterId;
	private String warranterNo;
	private String warranterName;
	private Boolean containOriAll;
	private String approver;
	// private Timestamp approveTime;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;
	// private String stationId;
	private String follower;
	private String followerFull;
	private String remark;
	private String imagesUrls;

	private String rateTypeMeaning;
	private String mobile;
	private String warranterMobile;
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
	// private String departmetnName;

	private Set<AccountsReceivableInstalmentDocument> instalmentdocument;

	// private Set<VehicleLoanStandingBooks> instalmentDetail;
	// Constructors

	/** default constructor */
	public AccountsReceivableInstalment() {
	}

	/** minimal constructor */
	public AccountsReceivableInstalment(String documentNo, String objectId,
			String objectName, String submitStationName) {
		this.documentNo = documentNo;
		this.objectId = objectId;
		this.objectName = objectName;
		this.submitStationName = submitStationName;
	}

	/** full constructor */
	public AccountsReceivableInstalment(String documentNo, String objectId,
			String objectNo, String objectName, String loanUse,
			String vehicleCardNo, Double arAmount, Double arPrincipal,
			Double arInterest, Double arAmountDue, Double arPrincipalDue,
			Double arInterestDue, Double arAmountOri, Double arPrincipalOri,
			Double arInterestOri, Integer periodNumber, Double monthRate,
			Short rateType, Timestamp firstPayDate, String warranterId,
			String warranterNo, String warranterName, Boolean containOriAll,
			String approver, Timestamp approveTime, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime,
			String stationId, String follower, String followerFull,
			String remark, String imagesUrls, Short status, String approverId,
			String approverNo, String approverName, Timestamp submitTime,
			String submitStationId, String submitStationName, String userId,
			String userNo, String userName, String departmentId,
			String departmentNo, String departmentName) {
		this.documentNo = documentNo;
		this.objectId = objectId;
		this.objectNo = objectNo;
		this.objectName = objectName;
		this.loanUse = loanUse;
		this.vehicleCardNo = vehicleCardNo;
		this.arAmount = arAmount;
		this.arPrincipal = arPrincipal;
		this.arInterest = arInterest;
		this.arAmountDue = arAmountDue;
		this.arPrincipalDue = arPrincipalDue;
		this.arInterestDue = arInterestDue;
		this.arAmountOri = arAmountOri;
		this.arPrincipalOri = arPrincipalOri;
		this.arInterestOri = arInterestOri;
		this.periodNumber = periodNumber;
		this.monthRate = monthRate;
		this.rateType = rateType;
		this.firstPayDate = firstPayDate;
		this.warranterId = warranterId;
		this.warranterNo = warranterNo;
		this.warranterName = warranterName;
		this.containOriAll = containOriAll;
		this.approver = approver;
		this.approveTime = approveTime;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.stationId = stationId;
		this.follower = follower;
		this.followerFull = followerFull;
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

	public String getLoanUse() {
		return this.loanUse;
	}

	public void setLoanUse(String loanUse) {
		this.loanUse = loanUse;
	}

	public String getVehicleCardNo() {
		return this.vehicleCardNo;
	}

	public void setVehicleCardNo(String vehicleCardNo) {
		this.vehicleCardNo = vehicleCardNo;
	}

	public Double getArAmount() {
		return this.arAmount;
	}

	public void setArAmount(Double arAmount) {
		this.arAmount = arAmount;
	}

	public Double getArPrincipal() {
		return this.arPrincipal;
	}

	public void setArPrincipal(Double arPrincipal) {
		this.arPrincipal = arPrincipal;
	}

	public Double getArInterest() {
		return this.arInterest;
	}

	public void setArInterest(Double arInterest) {
		this.arInterest = arInterest;
	}

	public Double getArAmountDue() {
		return this.arAmountDue;
	}

	public void setArAmountDue(Double arAmountDue) {
		this.arAmountDue = arAmountDue;
	}

	public Double getArPrincipalDue() {
		return this.arPrincipalDue;
	}

	public void setArPrincipalDue(Double arPrincipalDue) {
		this.arPrincipalDue = arPrincipalDue;
	}

	public Double getArInterestDue() {
		return this.arInterestDue;
	}

	public void setArInterestDue(Double arInterestDue) {
		this.arInterestDue = arInterestDue;
	}

	public Double getArAmountOri() {
		return this.arAmountOri;
	}

	public void setArAmountOri(Double arAmountOri) {
		this.arAmountOri = arAmountOri;
	}

	public Double getArPrincipalOri() {
		return this.arPrincipalOri;
	}

	public void setArPrincipalOri(Double arPrincipalOri) {
		this.arPrincipalOri = arPrincipalOri;
	}

	public Double getArInterestOri() {
		return this.arInterestOri;
	}

	public void setArInterestOri(Double arInterestOri) {
		this.arInterestOri = arInterestOri;
	}

	public Integer getPeriodNumber() {
		return this.periodNumber;
	}

	public void setPeriodNumber(Integer periodNumber) {
		this.periodNumber = periodNumber;
	}

	public Double getMonthRate() {
		return this.monthRate;
	}

	public void setMonthRate(Double monthRate) {
		this.monthRate = monthRate;
	}

	public Short getRateType() {
		return this.rateType;
	}

	public void setRateType(Short rateType) {
		this.rateType = rateType;
	}

	public Timestamp getFirstPayDate() {
		return this.firstPayDate;
	}

	public void setFirstPayDate(Timestamp firstPayDate) {
		this.firstPayDate = firstPayDate;
	}

	public String getWarranterId() {
		return this.warranterId;
	}

	public void setWarranterId(String warranterId) {
		this.warranterId = warranterId;
	}

	public String getWarranterNo() {
		return this.warranterNo;
	}

	public void setWarranterNo(String warranterNo) {
		this.warranterNo = warranterNo;
	}

	public String getWarranterName() {
		return this.warranterName;
	}

	public void setWarranterName(String warranterName) {
		this.warranterName = warranterName;
	}

	public Boolean getContainOriAll() {
		return this.containOriAll;
	}

	public void setContainOriAll(Boolean containOriAll) {
		this.containOriAll = containOriAll;
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

	/*
	 * @Override public Timestamp getModifyTime() { return this.modifyTime; }
	 * 
	 * @Override public void setModifyTime(Timestamp modifyTime) {
	 * this.modifyTime = modifyTime; }
	 */

	@Override
	public String getStationId() {
		return this.stationId;
	}

	@Override
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getFollower() {
		return this.follower;
	}

	public void setFollower(String follower) {
		this.follower = follower;
	}

	public String getFollowerFull() {
		return this.followerFull;
	}

	public void setFollowerFull(String followerFull) {
		this.followerFull = followerFull;
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

	public Set<AccountsReceivableInstalmentDocument> getInstalmentdocument() {
		return instalmentdocument;
	}

	public void setInstalmentdocument(
			Set<AccountsReceivableInstalmentDocument> instalmentdocument) {
		this.instalmentdocument = instalmentdocument;
	}

	/*
	 * public Set<VehicleLoanStandingBooks> getInstalmentDetail() { return
	 * instalmentDetail; }
	 * 
	 * public void setInstalmentDetail(Set<VehicleLoanStandingBooks>
	 * instalmentDetail) { this.instalmentDetail = instalmentDetail; }
	 */

	public String getRateTypeMeaning() {
		return rateTypeMeaning;
	}

	public void setRateTypeMeaning(String rateTypeMeaning) {
		this.rateTypeMeaning = rateTypeMeaning;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWarranterMobile() {
		return warranterMobile;
	}

	public void setWarranterMobile(String warranterMobile) {
		this.warranterMobile = warranterMobile;
	}

	@Override
	public String toString() {
		return "AccountsReceivableInstalment [objectId=" + objectId
				+ ", objectNo=" + objectNo + ", objectName=" + objectName
				+ ", loanUse=" + loanUse + ", vehicleCardNo=" + vehicleCardNo
				+ ", arAmount=" + arAmount + ", arPrincipal=" + arPrincipal
				+ ", arInterest=" + arInterest + ", arAmountDue=" + arAmountDue
				+ ", arPrincipalDue=" + arPrincipalDue + ", arInterestDue="
				+ arInterestDue + ", arAmountOri=" + arAmountOri
				+ ", arPrincipalOri=" + arPrincipalOri + ", arInterestOri="
				+ arInterestOri + ", periodNumber=" + periodNumber
				+ ", monthRate=" + monthRate + ", rateType=" + rateType
				+ ", firstPayDate=" + firstPayDate + ", warranterId="
				+ warranterId + ", warranterNo=" + warranterNo
				+ ", warranterName=" + warranterName + ", containOriAll="
				+ containOriAll + ", approver=" + approver + ", creator="
				+ creator + ", createTime=" + createTime + ", modifier="
				+ modifier + ", modifyTime=" + modifyTime + ", follower="
				+ follower + ", followerFull=" + followerFull + ", remark="
				+ remark + ", imagesUrls=" + imagesUrls + "]";
	}
}
