package cn.sf_soft.office.approval.model;

/**
 * FinanceAccountWriteOffsDetails entity. @author MyEclipse Persistence Tools
 */

public class FinanceAccountWriteOffsDetails implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6073635507926055686L;
	private String fawdId;
	private String documentNo;
	private String entryId;
	private String documentType;
	private String documentId;
	private String documentNoEntry;
	private Double writeOffAmount;

	// Constructors

	/** default constructor */
	public FinanceAccountWriteOffsDetails() {
	}

	/** full constructor */
	public FinanceAccountWriteOffsDetails(String fawdId, String documentNo,
			String entryId, String documentType, String documentId,
			String documentNoEntry, Double writeOffAmount) {
		this.fawdId = fawdId;
		this.documentNo = documentNo;
		this.entryId = entryId;
		this.documentType = documentType;
		this.documentId = documentId;
		this.documentNoEntry = documentNoEntry;
		this.writeOffAmount = writeOffAmount;
	}

	// Property accessors

	public String getFawdId() {
		return this.fawdId;
	}

	public void setFawdId(String fawdId) {
		this.fawdId = fawdId;
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

	public Double getWriteOffAmount() {
		return this.writeOffAmount;
	}

	public void setWriteOffAmount(Double writeOffAmount) {
		this.writeOffAmount = writeOffAmount;
	}

	@Override
	public String toString() {
		return "FinanceAccountWriteOffsDetails [documentId=" + documentId
				+ ", documentNo=" + documentNo + ", documentNoEntry="
				+ documentNoEntry + ", documentType=" + documentType
				+ ", entryId=" + entryId + ", fawdId=" + fawdId
				+ ", writeOffAmount=" + writeOffAmount + "]";
	}

}
