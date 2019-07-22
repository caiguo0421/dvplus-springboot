package cn.sf_soft.office.approval.model;



/**
 * OfficeLoanChargeReimbursementsDetails entity. @author MyEclipse Persistence
 * Tools
 */

public class OfficeLoanChargeReimbursementsDetails implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String olcrdId;
	private String documentNo;
	private String budgetChargeId;
	private String budgetNo;
	private String saleContractNo;
	private String vehicleSalesCode;
	private String vehicleName;
	private String vehicleVin;
	private String chargeId;
	private String chargeName;
	private Double chargeAmount;
	private String customerName;
	private String loanObjectName;
	// Constructors

	/** default constructor */
	public OfficeLoanChargeReimbursementsDetails() {
	}

	/** minimal constructor */
	public OfficeLoanChargeReimbursementsDetails(String olcrdId,
			String documentNo, String budgetChargeId, String budgetNo,
			String vehicleSalesCode, String vehicleName, String vehicleVin,
			String chargeId, String chargeName, Double chargeAmount) {
		this.olcrdId = olcrdId;
		this.documentNo = documentNo;
		this.budgetChargeId = budgetChargeId;
		this.budgetNo = budgetNo;
		this.vehicleSalesCode = vehicleSalesCode;
		this.vehicleName = vehicleName;
		this.vehicleVin = vehicleVin;
		this.chargeId = chargeId;
		this.chargeName = chargeName;
		this.chargeAmount = chargeAmount;
	}

	/** full constructor */
	public OfficeLoanChargeReimbursementsDetails(String olcrdId,
			String documentNo, String budgetChargeId, String budgetNo,
			String saleContractNo, String vehicleSalesCode, String vehicleName,
			String vehicleVin, String chargeId, String chargeName,
			Double chargeAmount) {
		this.olcrdId = olcrdId;
		this.documentNo = documentNo;
		this.budgetChargeId = budgetChargeId;
		this.budgetNo = budgetNo;
		this.saleContractNo = saleContractNo;
		this.vehicleSalesCode = vehicleSalesCode;
		this.vehicleName = vehicleName;
		this.vehicleVin = vehicleVin;
		this.chargeId = chargeId;
		this.chargeName = chargeName;
		this.chargeAmount = chargeAmount;
	}

	// Property accessors

	public String getOlcrdId() {
		return this.olcrdId;
	}

	public void setOlcrdId(String olcrdId) {
		this.olcrdId = olcrdId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getBudgetChargeId() {
		return this.budgetChargeId;
	}

	public void setBudgetChargeId(String budgetChargeId) {
		this.budgetChargeId = budgetChargeId;
	}

	public String getBudgetNo() {
		return this.budgetNo;
	}

	public void setBudgetNo(String budgetNo) {
		this.budgetNo = budgetNo;
	}

	public String getSaleContractNo() {
		return this.saleContractNo;
	}

	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}

	public String getVehicleSalesCode() {
		return this.vehicleSalesCode;
	}

	public void setVehicleSalesCode(String vehicleSalesCode) {
		this.vehicleSalesCode = vehicleSalesCode;
	}

	public String getVehicleName() {
		return this.vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehicleVin() {
		return this.vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
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
		return "OfficeLoanChargeReimbursementsDetails [chargeAmount=" + chargeAmount
				+ ", chargeId=" + chargeId + ", chargeName=" + chargeName
				+ ", budgetNo=" + budgetNo + ", documentNo=" + documentNo
				+ ", olcrdId=" + olcrdId 
				+ ", vehicleName=" + vehicleName + ", vehicleVin=" + vehicleVin
				+ ", saleContractNo=" + saleContractNo + ", budgetChargeId=" + budgetChargeId
				+ ", vehicleSalesCode=" + vehicleSalesCode + "]";
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLoanObjectName() {
		return loanObjectName;
	}

	public void setLoanObjectName(String loanObjectName) {
		this.loanObjectName = loanObjectName;
	}
}
