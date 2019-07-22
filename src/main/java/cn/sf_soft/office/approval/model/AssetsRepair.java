package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * AssetsRepair entity. @author MyEclipse Persistence Tools
 */

public class AssetsRepair implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7081399474437820123L;
	private String documentNo;
	private String stationId;
	private Double totAmount;
	private Timestamp repairDate;
	private Short status;
	private String remark;
	private String approver;
	private Timestamp approveTime;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String businessUnitsId;
	private String businessUnitsNo;
	private String businessUnitsName;
	private String repairUserId;
	private String repairUserNo;
	private String repairUserName;
	private Double reimburseAmount;
	private String confirmer;
	private Timestamp confirmTime;
	private Double totAmountEstimate;

	// Constructors

	/** default constructor */
	public AssetsRepair() {
	}

	/** minimal constructor */
	public AssetsRepair(String documentNo, String stationId) {
		this.documentNo = documentNo;
		this.stationId = stationId;
	}

	/** full constructor */
	public AssetsRepair(String documentNo, String stationId, Double totAmount,
			Timestamp repairDate, Short status, String remark, String approver,
			Timestamp approveTime, String creator, Timestamp createTime,
			String modifier, Timestamp modifyTime, String businessUnitsId,
			String businessUnitsNo, String businessUnitsName,
			String repairUserId, String repairUserNo, String repairUserName,
			Double reimburseAmount, String confirmer, Timestamp confirmTime,
			Double totAmountEstimate) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.totAmount = totAmount;
		this.repairDate = repairDate;
		this.status = status;
		this.remark = remark;
		this.approver = approver;
		this.approveTime = approveTime;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.businessUnitsId = businessUnitsId;
		this.businessUnitsNo = businessUnitsNo;
		this.businessUnitsName = businessUnitsName;
		this.repairUserId = repairUserId;
		this.repairUserNo = repairUserNo;
		this.repairUserName = repairUserName;
		this.reimburseAmount = reimburseAmount;
		this.confirmer = confirmer;
		this.confirmTime = confirmTime;
		this.totAmountEstimate = totAmountEstimate;
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

	public Double getTotAmount() {
		return this.totAmount;
	}

	public void setTotAmount(Double totAmount) {
		this.totAmount = totAmount;
	}

	public Timestamp getRepairDate() {
		return this.repairDate;
	}

	public void setRepairDate(Timestamp repairDate) {
		this.repairDate = repairDate;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApprover() {
		return this.approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
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

	public String getBusinessUnitsId() {
		return this.businessUnitsId;
	}

	public void setBusinessUnitsId(String businessUnitsId) {
		this.businessUnitsId = businessUnitsId;
	}

	public String getBusinessUnitsNo() {
		return this.businessUnitsNo;
	}

	public void setBusinessUnitsNo(String businessUnitsNo) {
		this.businessUnitsNo = businessUnitsNo;
	}

	public String getBusinessUnitsName() {
		return this.businessUnitsName;
	}

	public void setBusinessUnitsName(String businessUnitsName) {
		this.businessUnitsName = businessUnitsName;
	}

	public String getRepairUserId() {
		return this.repairUserId;
	}

	public void setRepairUserId(String repairUserId) {
		this.repairUserId = repairUserId;
	}

	public String getRepairUserNo() {
		return this.repairUserNo;
	}

	public void setRepairUserNo(String repairUserNo) {
		this.repairUserNo = repairUserNo;
	}

	public String getRepairUserName() {
		return this.repairUserName;
	}

	public void setRepairUserName(String repairUserName) {
		this.repairUserName = repairUserName;
	}

	public Double getReimburseAmount() {
		return this.reimburseAmount;
	}

	public void setReimburseAmount(Double reimburseAmount) {
		this.reimburseAmount = reimburseAmount;
	}

	public String getConfirmer() {
		return this.confirmer;
	}

	public void setConfirmer(String confirmer) {
		this.confirmer = confirmer;
	}

	public Timestamp getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Double getTotAmountEstimate() {
		return this.totAmountEstimate;
	}

	public void setTotAmountEstimate(Double totAmountEstimate) {
		this.totAmountEstimate = totAmountEstimate;
	}

}
