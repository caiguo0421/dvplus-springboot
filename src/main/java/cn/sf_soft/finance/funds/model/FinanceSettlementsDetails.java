package cn.sf_soft.finance.funds.model;


public class FinanceSettlementsDetails implements java.io.Serializable {

	private static final long serialVersionUID = 4525910041474730124L;
	private String fsdId;
	private String settlementNo;
	private String entryId;
	private String documentType;
	private String documentId;
	private String documentNo;
	private String objectId;
	private String objectNo;
	private String objectName;
	private String fprdId;
	private Double settleAmount;

	// Constructors

	public FinanceSettlementsDetails() {
	}

	

	public String getFsdId() {
		return this.fsdId;
	}

	public void setFsdId(String fsdId) {
		this.fsdId = fsdId;
	}

	public String getSettlementNo() {
		return this.settlementNo;
	}

	public void setSettlementNo(String settlementNo) {
		this.settlementNo = settlementNo;
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

	public String getFprdId() {
		return this.fprdId;
	}

	public void setFprdId(String fprdId) {
		this.fprdId = fprdId;
	}

	public Double getSettleAmount() {
		return this.settleAmount;
	}

	public void setSettleAmount(Double settleAmount) {
		this.settleAmount = settleAmount;
	}

}
