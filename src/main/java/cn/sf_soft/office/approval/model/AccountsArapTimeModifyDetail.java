package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * AccountsArapTimeModifyDetail entity. @author MyEclipse Persistence Tools
 */

public class AccountsArapTimeModifyDetail implements java.io.Serializable {

	// Fields

	private String selfId;
	private String documentNo;
	private String objectId;
	private String objectNo;
	private String objectName;
	private String documentType;
	private String arapNo;
	private String documentId;
	private String entryId;
	private Timestamp documentTime;
	private Timestamp documentTimeModify;
	private Timestamp arapTime;
	private Timestamp arapTimeModify;

	// Constructors

	/** default constructor */
	public AccountsArapTimeModifyDetail() {
	}

	/** minimal constructor */
	public AccountsArapTimeModifyDetail(String documentNo, String objectId,
			String arapNo, String documentId, String entryId) {
		this.documentNo = documentNo;
		this.objectId = objectId;
		this.arapNo = arapNo;
		this.documentId = documentId;
		this.entryId = entryId;
	}

	/** full constructor */
	public AccountsArapTimeModifyDetail(String documentNo, String objectId,
			String objectNo, String objectName, String documentType,
			String arapNo, String documentId, String entryId,
			Timestamp documentTime, Timestamp documentTimeModify,
			Timestamp arapTime, Timestamp arapTimeModify) {
		this.documentNo = documentNo;
		this.objectId = objectId;
		this.objectNo = objectNo;
		this.objectName = objectName;
		this.documentType = documentType;
		this.arapNo = arapNo;
		this.documentId = documentId;
		this.entryId = entryId;
		this.documentTime = documentTime;
		this.documentTimeModify = documentTimeModify;
		this.arapTime = arapTime;
		this.arapTimeModify = arapTimeModify;
	}

	// Property accessors

	public String getSelfId() {
		return this.selfId;
	}

	public void setSelfId(String selfId) {
		this.selfId = selfId;
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

	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getArapNo() {
		return this.arapNo;
	}

	public void setArapNo(String arapNo) {
		this.arapNo = arapNo;
	}

	public String getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getEntryId() {
		return this.entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public Timestamp getDocumentTime() {
		return this.documentTime;
	}

	public void setDocumentTime(Timestamp documentTime) {
		this.documentTime = documentTime;
	}

	public Timestamp getDocumentTimeModify() {
		return this.documentTimeModify;
	}

	public void setDocumentTimeModify(Timestamp documentTimeModify) {
		this.documentTimeModify = documentTimeModify;
	}

	public Timestamp getArapTime() {
		return this.arapTime;
	}

	public void setArapTime(Timestamp arapTime) {
		this.arapTime = arapTime;
	}

	public Timestamp getArapTimeModify() {
		return this.arapTimeModify;
	}

	public void setArapTimeModify(Timestamp arapTimeModify) {
		this.arapTimeModify = arapTimeModify;
	}

}
