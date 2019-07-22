package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * SuppliesPurchasePlan entity. @author MyEclipse Persistence Tools
 */

public class SuppliesPurchasePlan extends
		ApproveDocuments<SuppliesPurchasePlanDetail> implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2038527175360142193L;
	private Short payType;
	private Double totPrice;
	private Short planStatus;
	private Short supplierType;
	private String supplierId;
	private String supplierNo;
	private String supplierName;
	private Timestamp planEndDate;
	private Timestamp realEndDate;
	private String remark;
	private String unitNo;
	private String unitName;
	private String approver;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;
	private Double totPlanQuantity;
	private Double totPisQuantity;
	private Double totStopQuantity;
	private Double totSabQuantity;
	private Boolean isUnifiedPurchase;
	private Boolean isUnifiedPurchaseDept;
	private Integer aplanType;

	private String aplanTypeMean;
	private String planStatuMean;

	// Constructors

	/** default constructor */
	public SuppliesPurchasePlan() {
	}

	/** minimal constructor */
	public SuppliesPurchasePlan(String documentNo, String stationId) {
		this.documentNo = documentNo;
		this.stationId = stationId;
	}

	/** full constructor */
	public SuppliesPurchasePlan(String documentNo, String stationId,
			Short payType, Double totPrice, Short planStatus,
			Short supplierType, String supplierId, String supplierNo,
			String supplierName, Timestamp planEndDate, Timestamp realEndDate,
			Short status, String remark, String unitNo, String unitName,
			String approver, Timestamp approveTime, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime,
			Double totPlanQuantity, Double totPisQuantity,
			Double totStopQuantity, Double totSabQuantity,
			Boolean isUnifiedPurchase, String departmentId,
			String departmentNo, String departmentName,
			Boolean isUnifiedPurchaseDept, String userId, String userNo,
			String userName, String submitStationId, String submitStationName,
			Timestamp submitTime, String approverId, String approverNo,
			String approverName, Integer aplanType) {
		this.documentNo = documentNo;
		this.stationId = stationId;
		this.payType = payType;
		this.totPrice = totPrice;
		this.planStatus = planStatus;
		this.supplierType = supplierType;
		this.supplierId = supplierId;
		this.supplierNo = supplierNo;
		this.supplierName = supplierName;
		this.planEndDate = planEndDate;
		this.realEndDate = realEndDate;
		this.status = status;
		this.remark = remark;
		this.unitNo = unitNo;
		this.unitName = unitName;
		this.approver = approver;
		this.approveTime = approveTime;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.totPlanQuantity = totPlanQuantity;
		this.totPisQuantity = totPisQuantity;
		this.totStopQuantity = totStopQuantity;
		this.totSabQuantity = totSabQuantity;
		this.isUnifiedPurchase = isUnifiedPurchase;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.isUnifiedPurchaseDept = isUnifiedPurchaseDept;
		this.userId = userId;
		this.userNo = userNo;
		this.userName = userName;
		this.submitStationId = submitStationId;
		this.submitStationName = submitStationName;
		this.submitTime = submitTime;
		this.approverId = approverId;
		this.approverNo = approverNo;
		this.approverName = approverName;
		this.aplanType = aplanType;
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

	public Short getPayType() {
		return this.payType;
	}

	public void setPayType(Short payType) {
		this.payType = payType;
	}

	public Double getTotPrice() {
		return this.totPrice;
	}

	public void setTotPrice(Double totPrice) {
		this.totPrice = totPrice;
	}

	public Short getPlanStatus() {
		return this.planStatus;
	}

	public void setPlanStatus(Short planStatus) {
		this.planStatus = planStatus;
	}

	public Short getSupplierType() {
		return this.supplierType;
	}

	public void setSupplierType(Short supplierType) {
		this.supplierType = supplierType;
	}

	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierNo() {
		return this.supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Timestamp getPlanEndDate() {
		return this.planEndDate;
	}

	public void setPlanEndDate(Timestamp planEndDate) {
		this.planEndDate = planEndDate;
	}

	public Timestamp getRealEndDate() {
		return this.realEndDate;
	}

	public void setRealEndDate(Timestamp realEndDate) {
		this.realEndDate = realEndDate;
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

	public String getUnitNo() {
		return this.unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public Double getTotPlanQuantity() {
		return this.totPlanQuantity;
	}

	public void setTotPlanQuantity(Double totPlanQuantity) {
		this.totPlanQuantity = totPlanQuantity;
	}

	public Double getTotPisQuantity() {
		return this.totPisQuantity;
	}

	public void setTotPisQuantity(Double totPisQuantity) {
		this.totPisQuantity = totPisQuantity;
	}

	public Double getTotStopQuantity() {
		return this.totStopQuantity;
	}

	public void setTotStopQuantity(Double totStopQuantity) {
		this.totStopQuantity = totStopQuantity;
	}

	public Double getTotSabQuantity() {
		return this.totSabQuantity;
	}

	public void setTotSabQuantity(Double totSabQuantity) {
		this.totSabQuantity = totSabQuantity;
	}

	public Boolean getIsUnifiedPurchase() {
		return this.isUnifiedPurchase;
	}

	public void setIsUnifiedPurchase(Boolean isUnifiedPurchase) {
		this.isUnifiedPurchase = isUnifiedPurchase;
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

	public Boolean getIsUnifiedPurchaseDept() {
		return this.isUnifiedPurchaseDept;
	}

	public void setIsUnifiedPurchaseDept(Boolean isUnifiedPurchaseDept) {
		this.isUnifiedPurchaseDept = isUnifiedPurchaseDept;
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

	public Integer getAplanType() {
		return this.aplanType;
	}

	public void setAplanType(Integer aplanType) {
		this.aplanType = aplanType;
	}

	public String getAplanTypeMean() {
		return aplanTypeMean;
	}

	public void setAplanTypeMean(String aplanTypeMean) {
		this.aplanTypeMean = aplanTypeMean;
	}

	public String getPlanStatuMean() {
		return planStatuMean;
	}

	public void setPlanStatuMean(String planStatuMean) {
		this.planStatuMean = planStatuMean;
	}

}
