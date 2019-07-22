package cn.sf_soft.office.approval.model;

/**
 * FinancePaymentRequestsDetails entity. @author MyEclipse Persistence Tools
 */

public class FinancePaymentRequestsDetails implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3726060299650268411L;
	// Fields

	private String fprdId;
	private String documentNo;
	private String entryId;
	private String documentType;
	private String documentId;
	private String documentNoEntry;
	private Double requestAmount;
	private String summary; //关联信息
	private Double paidAmount;
	
	@Override
	public String toString() {
		return "FinancePaymentRequestsDetails [fprdId=" + fprdId
				+ ", documentNo=" + documentNo + ", entryId=" + entryId
				+ ", documentType=" + documentType + ", documentId=" + documentId
				+ ", documentNoEntry=" + documentNoEntry + ", requestAmount=" + requestAmount
				+ "]";
	}
	// Constructors

	/** default constructor */
	public FinancePaymentRequestsDetails() {
	}

	/** full constructor */
	public FinancePaymentRequestsDetails(String fprdId, String documentNo,
			String entryId, String documentType, String documentId,
			String documentNoEntry, Double requestAmount) {
		this.fprdId = fprdId;
		this.documentNo = documentNo;
		this.entryId = entryId;
		this.documentType = documentType;
		this.documentId = documentId;
		this.documentNoEntry = documentNoEntry;
		this.requestAmount = requestAmount;
	}

	// Property accessors

	public String getFprdId() {
		return this.fprdId;
	}

	public void setFprdId(String fprdId) {
		this.fprdId = fprdId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getEntryId() {
		return this.entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
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

	public String getDocumentNoEntry() {
		return this.documentNoEntry;
	}

	public void setDocumentNoEntry(String documentNoEntry) {
		this.documentNoEntry = documentNoEntry;
	}

	public Double getRequestAmount() {
		return this.requestAmount;
	}

	public void setRequestAmount(Double requestAmount) {
		this.requestAmount = requestAmount;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
}
