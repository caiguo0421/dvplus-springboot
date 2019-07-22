package cn.sf_soft.office.approval.model;

// Generated 2015-4-28 15:14:05 by Hibernate Tools 3.4.0.CR1

import java.sql.Timestamp;
import java.util.Set;

/**
 * @ClassName: AssetsOutStocks
 * @Description: 资产出库主表实体类
 * @author xiongju
 * @date 2015-4-29 上午11:00:21
 * 
 */
public class AssetsOutStocks extends ApproveDocuments<AssetsOutStockDetail>
		implements java.io.Serializable {

	private Short posType;
	private Double totPosPrice;
	private String toStationId;
	private String assetsInNo;
	private String assetsTo;
	private Timestamp posDate;
	private Short scrappedFrom;
	private Short originPosType;
	private Short businessUnitsType;
	private String businessUnitsId;
	private String businessUnitsNo;
	private String businessUnitsName;
	private String targetConfirmMan;
	private Timestamp targetConfirmDate;
	private Short targetStatus;
	private String targetRemark;
	private String remark;
	private String approver;
	// private Timestamp approveTime;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;
	private Double companyAmt;
	private Double employeesAmt;
	private Short receivablesType;
	private Double deptAmt;
	private String creatorNo;
	private String creatorUnitNo;
	private String creatorUnitName;
	private String approverUnitNo;
	private String approverUnitName;

	// protected Set<AssetsOutStockDetail> chargeDetail;//单据的费用明细

	public AssetsOutStocks() {
	}

	public AssetsOutStocks(Short posType, Double totPosPrice,
			String toStationId, String assetsInNo, String assetsTo,
			Timestamp posDate, Short scrappedFrom, Short originPosType,
			Short businessUnitsType, String businessUnitsId,
			String businessUnitsNo, String businessUnitsName,
			String targetConfirmMan, Timestamp targetConfirmDate,
			Short targetStatus, String targetRemark, String remark,
			String approver, Timestamp approveTime, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime,
			Double companyAmt, Double employeesAmt, Short receivablesType,
			Double deptAmt, String creatorNo, String creatorUnitNo,
			String creatorUnitName, String approverUnitNo,
			String approverUnitName) {
		super();
		this.posType = posType;
		this.totPosPrice = totPosPrice;
		this.toStationId = toStationId;
		this.assetsInNo = assetsInNo;
		this.assetsTo = assetsTo;
		this.posDate = posDate;
		this.scrappedFrom = scrappedFrom;
		this.originPosType = originPosType;
		this.businessUnitsType = businessUnitsType;
		this.businessUnitsId = businessUnitsId;
		this.businessUnitsNo = businessUnitsNo;
		this.businessUnitsName = businessUnitsName;
		this.targetConfirmMan = targetConfirmMan;
		this.targetConfirmDate = targetConfirmDate;
		this.targetStatus = targetStatus;
		this.targetRemark = targetRemark;
		this.remark = remark;
		this.approver = approver;
		this.approveTime = approveTime;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.companyAmt = companyAmt;
		this.employeesAmt = employeesAmt;
		this.receivablesType = receivablesType;
		this.deptAmt = deptAmt;
		this.creatorNo = creatorNo;
		this.creatorUnitNo = creatorUnitNo;
		this.creatorUnitName = creatorUnitName;
		this.approverUnitNo = approverUnitNo;
		this.approverUnitName = approverUnitName;
	}

	public Short getPosType() {
		return posType;
	}

	public void setPosType(Short posType) {
		this.posType = posType;
	}

	public Double getTotPosPrice() {
		return totPosPrice;
	}

	public void setTotPosPrice(Double totPosPrice) {
		this.totPosPrice = totPosPrice;
	}

	public String getToStationId() {
		return toStationId;
	}

	public void setToStationId(String toStationId) {
		this.toStationId = toStationId;
	}

	public String getAssetsInNo() {
		return assetsInNo;
	}

	public void setAssetsInNo(String assetsInNo) {
		this.assetsInNo = assetsInNo;
	}

	public String getAssetsTo() {
		return assetsTo;
	}

	public void setAssetsTo(String assetsTo) {
		this.assetsTo = assetsTo;
	}

	public Timestamp getPosDate() {
		return posDate;
	}

	public void setPosDate(Timestamp posDate) {
		this.posDate = posDate;
	}

	public Short getScrappedFrom() {
		return scrappedFrom;
	}

	public void setScrappedFrom(Short scrappedFrom) {
		this.scrappedFrom = scrappedFrom;
	}

	public Short getOriginPosType() {
		return originPosType;
	}

	public void setOriginPosType(Short originPosType) {
		this.originPosType = originPosType;
	}

	public Short getBusinessUnitsType() {
		return businessUnitsType;
	}

	public void setBusinessUnitsType(Short businessUnitsType) {
		this.businessUnitsType = businessUnitsType;
	}

	public String getBusinessUnitsId() {
		return businessUnitsId;
	}

	public void setBusinessUnitsId(String businessUnitsId) {
		this.businessUnitsId = businessUnitsId;
	}

	public String getBusinessUnitsNo() {
		return businessUnitsNo;
	}

	public void setBusinessUnitsNo(String businessUnitsNo) {
		this.businessUnitsNo = businessUnitsNo;
	}

	public String getBusinessUnitsName() {
		return businessUnitsName;
	}

	public void setBusinessUnitsName(String businessUnitsName) {
		this.businessUnitsName = businessUnitsName;
	}

	public String getTargetConfirmMan() {
		return targetConfirmMan;
	}

	public void setTargetConfirmMan(String targetConfirmMan) {
		this.targetConfirmMan = targetConfirmMan;
	}

	public Timestamp getTargetConfirmDate() {
		return targetConfirmDate;
	}

	public void setTargetConfirmDate(Timestamp targetConfirmDate) {
		this.targetConfirmDate = targetConfirmDate;
	}

	public Short getTargetStatus() {
		return targetStatus;
	}

	public void setTargetStatus(Short targetStatus) {
		this.targetStatus = targetStatus;
	}

	public String getTargetRemark() {
		return targetRemark;
	}

	public void setTargetRemark(String targetRemark) {
		this.targetRemark = targetRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	@Override
	public Timestamp getApproveTime() {
		return approveTime;
	}

	@Override
	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/*
	 * @Override public Timestamp getModifyTime() { return modifyTime; }
	 * 
	 * @Override public void setModifyTime(Timestamp modifyTime) {
	 * this.modifyTime = modifyTime; }
	 */

	public Double getCompanyAmt() {
		return companyAmt;
	}

	public void setCompanyAmt(Double companyAmt) {
		this.companyAmt = companyAmt;
	}

	public Double getEmployeesAmt() {
		return employeesAmt;
	}

	public void setEmployeesAmt(Double employeesAmt) {
		this.employeesAmt = employeesAmt;
	}

	public Short getReceivablesType() {
		return receivablesType;
	}

	public void setReceivablesType(Short receivablesType) {
		this.receivablesType = receivablesType;
	}

	public Double getDeptAmt() {
		return deptAmt;
	}

	public void setDeptAmt(Double deptAmt) {
		this.deptAmt = deptAmt;
	}

	public String getCreatorNo() {
		return creatorNo;
	}

	public void setCreatorNo(String creatorNo) {
		this.creatorNo = creatorNo;
	}

	public String getCreatorUnitNo() {
		return creatorUnitNo;
	}

	public void setCreatorUnitNo(String creatorUnitNo) {
		this.creatorUnitNo = creatorUnitNo;
	}

	public String getCreatorUnitName() {
		return creatorUnitName;
	}

	public void setCreatorUnitName(String creatorUnitName) {
		this.creatorUnitName = creatorUnitName;
	}

	public String getApproverUnitNo() {
		return approverUnitNo;
	}

	public void setApproverUnitNo(String approverUnitNo) {
		this.approverUnitNo = approverUnitNo;
	}

	public String getApproverUnitName() {
		return approverUnitName;
	}

	public void setApproverUnitName(String approverUnitName) {
		this.approverUnitName = approverUnitName;
	}

	@Override
	public Set<AssetsOutStockDetail> getChargeDetail() {
		return chargeDetail;
	}

	@Override
	public void setChargeDetail(Set<AssetsOutStockDetail> chargeDetail) {
		this.chargeDetail = chargeDetail;
	}

}
