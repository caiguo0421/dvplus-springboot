package cn.sf_soft.office.approval.model;

/**
 * OfficeServiceReimbursementsDetails entity. @author MyEclipse Persistence
 * Tools
 */

public class OfficeServiceReimbursementsDetails implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3352538724262964476L;
	private String osrdId;
	private String documentNo;
	private String taskNo;
	private String swlcId;
	private String chargeId;
	private String chargeName;
	private Double chargeAmount;

	// Constructors

	/** default constructor */
	public OfficeServiceReimbursementsDetails() {
	}

	/** full constructor */
	public OfficeServiceReimbursementsDetails(String osrdId, String documentNo,
			String taskNo, String swlcId, String chargeId, String chargeName,
			Double chargeAmount) {
		this.osrdId = osrdId;
		this.documentNo = documentNo;
		this.taskNo = taskNo;
		this.swlcId = swlcId;
		this.chargeId = chargeId;
		this.chargeName = chargeName;
		this.chargeAmount = chargeAmount;
	}

	// Property accessors

	public String getOsrdId() {
		return this.osrdId;
	}

	public void setOsrdId(String osrdId) {
		this.osrdId = osrdId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getTaskNo() {
		return this.taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getSwlcId() {
		return this.swlcId;
	}

	public void setSwlcId(String swlcId) {
		this.swlcId = swlcId;
	}

	public String getChargeId() {
		return this.chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public String getChargeName() {
		return this.chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public Double getChargeAmount() {
		return this.chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	@Override
	public String toString() {
		return "OfficeServiceReimbursementsDetails [chargeAmount="
				+ chargeAmount + ", chargeId=" + chargeId + ", chargeName="
				+ chargeName + ", documentNo=" + documentNo + ", osrdId="
				+ osrdId + ", swlcId=" + swlcId + ", taskNo=" + taskNo + "]";
	}

}
