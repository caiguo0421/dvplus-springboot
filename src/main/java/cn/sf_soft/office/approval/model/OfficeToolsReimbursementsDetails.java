package cn.sf_soft.office.approval.model;

/**
 * OfficeAssetsReimbursementsDetails entity. @author MyEclipse Persistence Tools
 */

public class OfficeToolsReimbursementsDetails implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1031457402106470256L;
	private String otraId;
	private String documentNo;
	private String objNo;
	private Double chargeAmount;

	// Constructors

	/** default constructor */
	public OfficeToolsReimbursementsDetails() {
	}

	/** full constructor */
	public OfficeToolsReimbursementsDetails(String oardId, String documentNo,
			String objNo, Double chargeAmount) {
		this.otraId = oardId;
		this.documentNo = documentNo;
		this.objNo = objNo;
		this.chargeAmount = chargeAmount;
	}

	// Property accessors

	

	public String getDocumentNo() {
		return this.documentNo;
	}

	/**
	 * @return the otraId
	 */
	public String getOtraId() {
		return otraId;
	}

	/**
	 * @param otraId the otraId to set
	 */
	public void setOtraId(String otraId) {
		this.otraId = otraId;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getObjNo() {
		return this.objNo;
	}

	public void setObjNo(String objNo) {
		this.objNo = objNo;
	}

	public Double getChargeAmount() {
		return this.chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	@Override
	public String toString() {
		return "OfficeAssetsReimbursementsDetails [oardId=" + otraId
				+ ", documentNo=" + documentNo + ", objNo=" + objNo
				+ ", chargeAmount=" + chargeAmount + "]";
	}

}
