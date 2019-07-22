package cn.sf_soft.office.approval.model;

/**
 * OfficePartCarriageReimbursementsDetails entity. @author MyEclipse Persistence
 * Tools
 */

public class OfficePartCarriageReimbursementsDetails implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1031457402106470256L;
	private String opcrdId;
	private String documentNo;
	private String partDocumentNo;
	private String carryCompanyId;
	private String carryCompanyNo;
	private String carryCompanyName;
	private String documentType;
	private Double carriageAmount;

	// Constructors

	/** default constructor */
	public OfficePartCarriageReimbursementsDetails() {
	}

	/** full constructor */
	public OfficePartCarriageReimbursementsDetails(String opcrdId,
			String documentNo, String partDocumentNo, String carryCompanyId,
			String carryCompanyNo, String carryCompanyName,
			String documentType, Double carriageAmount) {
		this.opcrdId = opcrdId;
		this.documentNo = documentNo;
		this.partDocumentNo = partDocumentNo;
		this.carryCompanyId = carryCompanyId;
		this.carryCompanyNo = carryCompanyNo;
		this.carryCompanyName = carryCompanyName;
		this.documentType = documentType;
		this.carriageAmount = carriageAmount;
	}

	public String getOpcrdId() {
		return opcrdId;
	}

	public void setOpcrdId(String opcrdId) {
		this.opcrdId = opcrdId;
	}

	public String getPartDocumentNo() {
		return partDocumentNo;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public void setPartDocumentNo(String partDocumentNo) {
		this.partDocumentNo = partDocumentNo;
	}

	public String getCarryCompanyId() {
		return carryCompanyId;
	}

	public void setCarryCompanyId(String carryCompanyId) {
		this.carryCompanyId = carryCompanyId;
	}

	public String getCarryCompanyNo() {
		return carryCompanyNo;
	}

	public void setCarryCompanyNo(String carryCompanyNo) {
		this.carryCompanyNo = carryCompanyNo;
	}

	public String getCarryCompanyName() {
		return carryCompanyName;
	}

	public void setCarryCompanyName(String carryCompanyName) {
		this.carryCompanyName = carryCompanyName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public Double getCarriageAmount() {
		return carriageAmount;
	}

	public void setCarriageAmount(Double carriageAmount) {
		this.carriageAmount = carriageAmount;
	}

	@Override
	public String toString() {
		return "OfficePartCarriageReimbursementsDetails [opcrdId=" + opcrdId
				+ ", documentNo=" + documentNo + ", partDocumentNo="
				+ partDocumentNo + ", carryCompanyId=" + carryCompanyId
				+ ",carryCompanyNo=" + carryCompanyNo + ",carryCompanyName="
				+ carryCompanyName + ",documentType=" + documentType
				+ ",carriageAmount=" + carriageAmount + "]";
	}

}
