package cn.sf_soft.office.approval.model;

/**
 * AccountsReceivableInstalmentDocument entity. @author MyEclipse Persistence
 * Tools
 */

public class AccountsReceivableInstalmentDocument implements
		java.io.Serializable {

	// Fields

	private String selfId;
	private String documentNo;
	private String receivableNo;
	private String documentId;
	private String instalmentNo;
	private String instalmentId;
	private Double arAmount;
	private Double arPrincipal;
	private Double arInterest;
	private Double arAmountOri;
	private Double arPrincipalOri;
	private Double arInterestOri;
	private String documentType;
	private Short amountType;
	private String remark;
	
	private String amountTypeMeaning;
	// Constructors

	/** default constructor */
	public AccountsReceivableInstalmentDocument() {
	}

	/** minimal constructor */
	public AccountsReceivableInstalmentDocument(String selfId,
			String documentNo, String receivableNo, String documentId) {
		this.selfId = selfId;
		this.documentNo = documentNo;
		this.receivableNo = receivableNo;
		this.documentId = documentId;
	}

	/** full constructor */
	public AccountsReceivableInstalmentDocument(String selfId,
			String documentNo, String receivableNo, String documentId,
			String instalmentNo, String instalmentId, Double arAmount,
			Double arPrincipal, Double arInterest, Double arAmountOri,
			Double arPrincipalOri, Double arInterestOri, String documentType,
			Short amountType, String remark) {
		this.selfId = selfId;
		this.documentNo = documentNo;
		this.receivableNo = receivableNo;
		this.documentId = documentId;
		this.instalmentNo = instalmentNo;
		this.instalmentId = instalmentId;
		this.arAmount = arAmount;
		this.arPrincipal = arPrincipal;
		this.arInterest = arInterest;
		this.arAmountOri = arAmountOri;
		this.arPrincipalOri = arPrincipalOri;
		this.arInterestOri = arInterestOri;
		this.documentType = documentType;
		this.amountType = amountType;
		this.remark = remark;
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

	public String getReceivableNo() {
		return this.receivableNo;
	}

	public void setReceivableNo(String receivableNo) {
		this.receivableNo = receivableNo;
	}

	public String getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getInstalmentNo() {
		return this.instalmentNo;
	}

	public void setInstalmentNo(String instalmentNo) {
		this.instalmentNo = instalmentNo;
	}

	public String getInstalmentId() {
		return this.instalmentId;
	}

	public void setInstalmentId(String instalmentId) {
		this.instalmentId = instalmentId;
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

	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public Short getAmountType() {
		return this.amountType;
	}

	public void setAmountType(Short amountType) {
		this.amountType = amountType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAmountTypeMeaning() {
		return amountTypeMeaning;
	}

	public void setAmountTypeMeaning(String amountTypeMeaning) {
		this.amountTypeMeaning = amountTypeMeaning;
	}

}
