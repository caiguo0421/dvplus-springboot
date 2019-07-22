package cn.sf_soft.office.approval.model;

/**
 * OfficeInternalAuditsDetails entity. @author MyEclipse Persistence Tools
 */

public class OfficeInternalAuditsDetails implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7301751671626062295L;
	private String oiadId;
	private String documentNo;
	private String documentType;
	private String documentNoAudit;
	private String objectName;
	private String itemId;
	private String itemNo;
	private String itemName;
	private Double itemAmount;

	// Constructors

	/** default constructor */
	public OfficeInternalAuditsDetails() {
	}

	/** minimal constructor */
	public OfficeInternalAuditsDetails(String oiadId, String documentNo,
			String documentType, String documentNoAudit, String itemId,
			String itemName, Double itemAmount) {
		this.oiadId = oiadId;
		this.documentNo = documentNo;
		this.documentType = documentType;
		this.documentNoAudit = documentNoAudit;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemAmount = itemAmount;
	}

	/** full constructor */
	public OfficeInternalAuditsDetails(String oiadId, String documentNo,
			String documentType, String documentNoAudit, String objectName,
			String itemId, String itemNo, String itemName, Double itemAmount) {
		this.oiadId = oiadId;
		this.documentNo = documentNo;
		this.documentType = documentType;
		this.documentNoAudit = documentNoAudit;
		this.objectName = objectName;
		this.itemId = itemId;
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.itemAmount = itemAmount;
	}

	// Property accessors

	public String getOiadId() {
		return this.oiadId;
	}

	public void setOiadId(String oiadId) {
		this.oiadId = oiadId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNoAudit() {
		return this.documentNoAudit;
	}

	public void setDocumentNoAudit(String documentNoAudit) {
		this.documentNoAudit = documentNoAudit;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getItemAmount() {
		return this.itemAmount;
	}

	public void setItemAmount(Double itemAmount) {
		this.itemAmount = itemAmount;
	}

	@Override
	public String toString() {
		return "OfficeInternalAuditsDetails [documentNo=" + documentNo
				+ ", documentNoAudit=" + documentNoAudit + ", documentType="
				+ documentType + ", itemAmount=" + itemAmount + ", itemId="
				+ itemId + ", itemName=" + itemName + ", itemNo=" + itemNo
				+ ", objectName=" + objectName + ", oiadId=" + oiadId + "]";
	}

}
