package cn.sf_soft.finance.funds.model;

import java.sql.Timestamp;



public class FinanceSettlements implements java.io.Serializable {


	
	private static final long serialVersionUID = -8675360124955548362L;
	private String settlementNo;
	private String stationId;
	private Short settleKind;
	private Short status;
	private String originNo;
	private Short settleType;
	private String detailType;
	private String objectId;
	private String objectNo;
	private String objectName;
	private Boolean forOthers;
	private Double documentAmount;
	private Double settleAmount;
	private Timestamp settleTime;
	private String settleStationId;
	private String settleStationName;
	private Boolean isCounted;
	private String advanceType;
	private Timestamp arapTime;
	private String departmentId;
	private String departmentNo;
	private String departmentName;
	private Boolean isInvoicing;
	private String invoiceStationId;
	private String invoiceStationName;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String approver;
	private Timestamp approveTime;


	/** default constructor */
	public FinanceSettlements() {
	}


	public String getSettlementNo() {
		return this.settlementNo;
	}

	public void setSettlementNo(String settlementNo) {
		this.settlementNo = settlementNo;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Short getSettleKind() {
		return this.settleKind;
	}

	public void setSettleKind(Short settleKind) {
		this.settleKind = settleKind;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getOriginNo() {
		return this.originNo;
	}

	public void setOriginNo(String originNo) {
		this.originNo = originNo;
	}

	public Short getSettleType() {
		return this.settleType;
	}

	public void setSettleType(Short settleType) {
		this.settleType = settleType;
	}

	public String getDetailType() {
		return this.detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
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

	public Boolean getForOthers() {
		return this.forOthers;
	}

	public void setForOthers(Boolean forOthers) {
		this.forOthers = forOthers;
	}

	public Double getDocumentAmount() {
		return this.documentAmount;
	}

	public void setDocumentAmount(Double documentAmount) {
		this.documentAmount = documentAmount;
	}

	public Double getSettleAmount() {
		return this.settleAmount;
	}

	public void setSettleAmount(Double settleAmount) {
		this.settleAmount = settleAmount;
	}

	public Timestamp getSettleTime() {
		return this.settleTime;
	}

	public void setSettleTime(Timestamp settleTime) {
		this.settleTime = settleTime;
	}

	public String getSettleStationId() {
		return this.settleStationId;
	}

	public void setSettleStationId(String settleStationId) {
		this.settleStationId = settleStationId;
	}

	public String getSettleStationName() {
		return this.settleStationName;
	}

	public void setSettleStationName(String settleStationName) {
		this.settleStationName = settleStationName;
	}

	public Boolean getIsCounted() {
		return this.isCounted;
	}

	public void setIsCounted(Boolean isCounted) {
		this.isCounted = isCounted;
	}

	public String getAdvanceType() {
		return this.advanceType;
	}

	public void setAdvanceType(String advanceType) {
		this.advanceType = advanceType;
	}

	public Timestamp getArapTime() {
		return this.arapTime;
	}

	public void setArapTime(Timestamp arapTime) {
		this.arapTime = arapTime;
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

	public Boolean getIsInvoicing() {
		return this.isInvoicing;
	}

	public void setIsInvoicing(Boolean isInvoicing) {
		this.isInvoicing = isInvoicing;
	}

	public String getInvoiceStationId() {
		return this.invoiceStationId;
	}

	public void setInvoiceStationId(String invoiceStationId) {
		this.invoiceStationId = invoiceStationId;
	}

	public String getInvoiceStationName() {
		return this.invoiceStationName;
	}

	public void setInvoiceStationName(String invoiceStationName) {
		this.invoiceStationName = invoiceStationName;
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

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
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

}
