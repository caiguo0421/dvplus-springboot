package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * FinanceDocumentEntries entity. @author MyEclipse Persistence Tools
 */

public class FinanceDocumentEntries implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7964854373239111459L;
	private String entryId;
	private String stationId;
	private Integer entryProperty;
	private Short entryType;
	private String documentType;
	private String documentId;
	private String documentNo;
	private String subDocumentNo;
	private String objectId;
	private String objectNo;
	private String objectName;
	private String accountId;
	private Short amountType;
	private Double leftAmount;
	private Double documentAmount;
	private Timestamp documentTime;
	private Timestamp arapTime;
	private Double offsetAmount;
	private Timestamp offsetTime;
	private Double paidAmount;
	private Timestamp paidTime;
	private Double writeOffAmount;
	private Timestamp writeOffTime;
	private Double invoiceAmount;
	private Timestamp invoiceTime;
	private String afterNo;
	private String userId;
	private String userNo;
	private String userName;
	private String departmentId;
	private String departmentNo;
	private String departmentName;
	// add by shichunshan 2016/02/04
	private Double usedCredit = 0.0;

	// 2013-12-10 新增字段 by liujin
	private String summary;

	// Constructors

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	/** default constructor */
	public FinanceDocumentEntries() {
	}

	/** minimal constructor */
	public FinanceDocumentEntries(String entryId, String stationId,
			Integer entryProperty, Short entryType, String documentType,
			String documentId, String documentNo, String subDocumentNo,
			String objectId, String objectName, Short amountType,
			Double leftAmount, Double documentAmount, Timestamp documentTime,
			Double offsetAmount, Double paidAmount, Double writeOffAmount,
			Double invoiceAmount) {
		this.entryId = entryId;
		this.stationId = stationId;
		this.entryProperty = entryProperty;
		this.entryType = entryType;
		this.documentType = documentType;
		this.documentId = documentId;
		this.documentNo = documentNo;
		this.subDocumentNo = subDocumentNo;
		this.objectId = objectId;
		this.objectName = objectName;
		this.amountType = amountType;
		this.leftAmount = leftAmount;
		this.documentAmount = documentAmount;
		this.documentTime = documentTime;
		this.offsetAmount = offsetAmount;
		this.paidAmount = paidAmount;
		this.writeOffAmount = writeOffAmount;
		this.invoiceAmount = invoiceAmount;
	}

	/** full constructor */
	public FinanceDocumentEntries(String entryId, String stationId,
			Integer entryProperty, Short entryType, String documentType,
			String documentId, String documentNo, String subDocumentNo,
			String objectId, String objectNo, String objectName,
			String accountId, Short amountType, Double leftAmount,
			Double documentAmount, Timestamp documentTime, Timestamp arapTime,
			Double offsetAmount, Timestamp offsetTime, Double paidAmount,
			Timestamp paidTime, Double writeOffAmount, Timestamp writeOffTime,
			Double invoiceAmount, Timestamp invoiceTime, String afterNo,
			String userId, String userNo, String userName, String departmentId,
			String departmentNo, String departmentName) {
		this.entryId = entryId;
		this.stationId = stationId;
		this.entryProperty = entryProperty;
		this.entryType = entryType;
		this.documentType = documentType;
		this.documentId = documentId;
		this.documentNo = documentNo;
		this.subDocumentNo = subDocumentNo;
		this.objectId = objectId;
		this.objectNo = objectNo;
		this.objectName = objectName;
		this.accountId = accountId;
		this.amountType = amountType;
		this.leftAmount = leftAmount;
		this.documentAmount = documentAmount;
		this.documentTime = documentTime;
		this.arapTime = arapTime;
		this.offsetAmount = offsetAmount;
		this.offsetTime = offsetTime;
		this.paidAmount = paidAmount;
		this.paidTime = paidTime;
		this.writeOffAmount = writeOffAmount;
		this.writeOffTime = writeOffTime;
		this.invoiceAmount = invoiceAmount;
		this.invoiceTime = invoiceTime;
		this.afterNo = afterNo;
		this.userId = userId;
		this.userNo = userNo;
		this.userName = userName;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
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

	public Integer getEntryProperty() {
		return this.entryProperty;
	}

	public void setEntryProperty(Integer entryProperty) {
		this.entryProperty = entryProperty;
	}

	public Short getEntryType() {
		return this.entryType;
	}

	public void setEntryType(Short entryType) {
		this.entryType = entryType;
	}

	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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

	public String getSubDocumentNo() {
		return this.subDocumentNo;
	}

	public void setSubDocumentNo(String subDocumentNo) {
		this.subDocumentNo = subDocumentNo;
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

	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Short getAmountType() {
		return this.amountType;
	}

	public void setAmountType(Short amountType) {
		this.amountType = amountType;
	}

	public Double getLeftAmount() {
		return this.leftAmount;
	}

	public void setLeftAmount(Double leftAmount) {
		this.leftAmount = leftAmount;
	}

	public Double getDocumentAmount() {
		return this.documentAmount;
	}

	public void setDocumentAmount(Double documentAmount) {
		this.documentAmount = documentAmount;
	}

	public Timestamp getDocumentTime() {
		return this.documentTime;
	}

	public void setDocumentTime(Timestamp documentTime) {
		this.documentTime = documentTime;
	}

	public Timestamp getArapTime() {
		return this.arapTime;
	}

	public void setArapTime(Timestamp arapTime) {
		this.arapTime = arapTime;
	}

	public Double getOffsetAmount() {
		return this.offsetAmount;
	}

	public void setOffsetAmount(Double offsetAmount) {
		this.offsetAmount = offsetAmount;
	}

	public Timestamp getOffsetTime() {
		return this.offsetTime;
	}

	public void setOffsetTime(Timestamp offsetTime) {
		this.offsetTime = offsetTime;
	}

	public Double getPaidAmount() {
		return this.paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Timestamp getPaidTime() {
		return this.paidTime;
	}

	public void setPaidTime(Timestamp paidTime) {
		this.paidTime = paidTime;
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

	public Double getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Timestamp getInvoiceTime() {
		return this.invoiceTime;
	}

	public void setInvoiceTime(Timestamp invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public String getAfterNo() {
		return this.afterNo;
	}

	public void setAfterNo(String afterNo) {
		this.afterNo = afterNo;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Double getUsedCredit() {
		return usedCredit;
	}

	public void setUsedCredit(Double usedCredit) {
		this.usedCredit = usedCredit;
	}

	@Override
	public String toString() {
		return "FinanceDocumentEntries [accountId=" + accountId + ", afterNo="
				+ afterNo + ", amountType=" + amountType + ", arapTime="
				+ arapTime + ", departmentId=" + departmentId
				+ ", departmentName=" + departmentName + ", departmentNo="
				+ departmentNo + ", documentAmount=" + documentAmount
				+ ", documentId=" + documentId + ", documentNo=" + documentNo
				+ ", documentTime=" + documentTime + ", documentType="
				+ documentType + ", entryId=" + entryId + ", entryProperty="
				+ entryProperty + ", entryType=" + entryType
				+ ", invoiceAmount=" + invoiceAmount + ", invoiceTime="
				+ invoiceTime + ", leftAmount=" + leftAmount + ", objectId="
				+ objectId + ", objectName=" + objectName + ", objectNo="
				+ objectNo + ", offsetAmount=" + offsetAmount + ", offsetTime="
				+ offsetTime + ", paidAmount=" + paidAmount + ", paidTime="
				+ paidTime + ", stationId=" + stationId + ", subDocumentNo="
				+ subDocumentNo + ", userId=" + userId + ", userName="
				+ userName + ", userNo=" + userNo + ", writeOffAmount="
				+ writeOffAmount + ", writeOffTime=" + writeOffTime
				+ ", summary=" + summary + ", usedCredit=" + usedCredit+"]";
	}

}
