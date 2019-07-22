package cn.sf_soft.office.postaudit.model;

import java.sql.Timestamp;

/**事后审核
 * OfficeAuditEntries entity. @author MyEclipse Persistence Tools
 */

public class OfficeAuditEntries implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7580877241975865750L;
	private String entryId;
	private String stationId;
	private String businessType;
	private String summary;
	private String documentId;
	private String documentNo;
	private String objectId;
	private String objectNo;
	private String objectName;
	private String auditItem;
	private Double actualValue;
	private Double specifiedValue;
	private String operator;
	private Timestamp operateTime;
	private String handleResult;
	private String handleOpinion;
	private String handler1;
	private Timestamp handleTime;
	private String approveOpinion;
	private String approver;
	private Timestamp approveTime;

	private String stationName;
	// Constructors

	/** default constructor */
	public OfficeAuditEntries() {
	}

	/** minimal constructor */
	public OfficeAuditEntries(String entryId, String stationId,
			String businessType, String summary, String documentId,
			String documentNo, String objectId, String objectName,
			String auditItem, Double actualValue, Double specifiedValue,
			String operator, Timestamp operateTime) {
		this.entryId = entryId;
		this.stationId = stationId;
		this.businessType = businessType;
		this.summary = summary;
		this.documentId = documentId;
		this.documentNo = documentNo;
		this.objectId = objectId;
		this.objectName = objectName;
		this.auditItem = auditItem;
		this.actualValue = actualValue;
		this.specifiedValue = specifiedValue;
		this.operator = operator;
		this.operateTime = operateTime;
	}

	/** full constructor */
	public OfficeAuditEntries(String entryId, String stationId,
			String businessType, String summary, String documentId,
			String documentNo, String objectId, String objectNo,
			String objectName, String auditItem, Double actualValue,
			Double specifiedValue, String operator, Timestamp operateTime,
			String handleResult, String handleOpinion, String handler,
			Timestamp handleTime, String approveOpinion, String approver,
			Timestamp approveTime) {
		this.entryId = entryId;
		this.stationId = stationId;
		this.businessType = businessType;
		this.summary = summary;
		this.documentId = documentId;
		this.documentNo = documentNo;
		this.objectId = objectId;
		this.objectNo = objectNo;
		this.objectName = objectName;
		this.auditItem = auditItem;
		this.actualValue = actualValue;
		this.specifiedValue = specifiedValue;
		this.operator = operator;
		this.operateTime = operateTime;
		this.handleResult = handleResult;
		this.handleOpinion = handleOpinion;
		this.handler1 = handler;
		this.handleTime = handleTime;
		this.approveOpinion = approveOpinion;
		this.approver = approver;
		this.approveTime = approveTime;
	}

	// Property accessors

	public String getEntryId() {
		return this.entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

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

	public String getAuditItem() {
		return this.auditItem;
	}

	public void setAuditItem(String auditItem) {
		this.auditItem = auditItem;
	}

	public Double getActualValue() {
		return this.actualValue;
	}

	public void setActualValue(Double actualValue) {
		this.actualValue = actualValue;
	}

	public Double getSpecifiedValue() {
		return this.specifiedValue;
	}

	public void setSpecifiedValue(Double specifiedValue) {
		this.specifiedValue = specifiedValue;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

	public String getHandleResult() {
		return this.handleResult;
	}

	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}

	public String getHandleOpinion() {
		return this.handleOpinion;
	}

	public void setHandleOpinion(String handleOpinion) {
		this.handleOpinion = handleOpinion;
	}

	public String getHandler1() {
		return this.handler1;
	}

	public void setHandler1(String handler) {
		this.handler1 = handler;
	}

	public Timestamp getHandleTime() {
		return this.handleTime;
	}

	public void setHandleTime(Timestamp handleTime) {
		this.handleTime = handleTime;
	}

	public String getApproveOpinion() {
		return this.approveOpinion;
	}

	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
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

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationName() {
		return stationName;
	}

	@Override
	public String toString() {
		return "OfficeAuditEntries [actualValue=" + actualValue
				+ ", approveOpinion=" + approveOpinion + ", approveTime="
				+ approveTime + ", approver=" + approver + ", auditItem="
				+ auditItem + ", businessType=" + businessType
				+ ", documentId=" + documentId + ", documentNo=" + documentNo
				+ ", entryId=" + entryId + ", handleOpinion=" + handleOpinion
				+ ", handleResult=" + handleResult + ", handleTime="
				+ handleTime + ", handler=" + handler1 + ", objectId="
				+ objectId + ", objectName=" + objectName + ", objectNo="
				+ objectNo + ", operateTime=" + operateTime + ", operator="
				+ operator + ", specifiedValue=" + specifiedValue
				+ ", stationId=" + stationId + ", stationName=" + stationName
				+ ", summary=" + summary + "]";
	}

}
