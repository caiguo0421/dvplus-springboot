package cn.sf_soft.office.approval.model;

/**
 * OfficeAssetsReimbursementsDetails entity. @author MyEclipse Persistence Tools
 */

public class OfficeAssetsReimbursementsDetails implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1031457402106470256L;
	private String oardId;
	private String documentNo;
	private String objNo;
	private Double chargeAmount;

	// Constructors

	/** default constructor */
	public OfficeAssetsReimbursementsDetails() {
	}

	/** full constructor */
	public OfficeAssetsReimbursementsDetails(String oardId, String documentNo,
			String objNo, Double chargeAmount) {
		this.oardId = oardId;
		this.documentNo = documentNo;
		this.objNo = objNo;
		this.chargeAmount = chargeAmount;
	}

	// Property accessors

	public String getOardId() {
		return this.oardId;
	}

	public void setOardId(String oardId) {
		this.oardId = oardId;
	}

	public String getDocumentNo() {
		return this.documentNo;
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
		return "OfficeAssetsReimbursementsDetails [oardId=" + oardId
				+ ", documentNo=" + documentNo + ", objNo=" + objNo
				+ ", chargeAmount=" + chargeAmount + "]";
	}

}
