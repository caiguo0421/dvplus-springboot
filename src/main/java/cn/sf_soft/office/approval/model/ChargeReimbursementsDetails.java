package cn.sf_soft.office.approval.model;

/**
 * 车辆费用报销单明细
 * OfficeChargeReimbursementsDetails entity. @author MyEclipse Persistence Tools
 */

public class ChargeReimbursementsDetails implements java.io.Serializable {

	private static final long serialVersionUID = -154459054480127676L;
	private String ocrdId;
	private String documentNo;
	private String contractNo;
	private String vscvId;
	private String vehicleVno;
	private String vehicleName;
	private String vehicleVin;
//	private Short vehicleKind;
	private String vsccId;
	private String chargeId;
	private String chargeName;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 报销金额
	 */
	private Double chargeAmount;

	/**
	 * 报销差额
	 */
	private Double reimbursementDifference;

	/**
	 * 差额原因
	 */
	private String differenceReason;


	/**
	 * 库存ID
	 */
	private String vehicleId;

	
	// Constructors
	public ChargeReimbursementsDetails() {
	}



	public String getOcrdId() {
		return this.ocrdId;
	}

	public void setOcrdId(String ocrdId) {
		this.ocrdId = ocrdId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getVscvId() {
		return this.vscvId;
	}

	public void setVscvId(String vscvId) {
		this.vscvId = vscvId;
	}

	public String getVehicleVno() {
		return this.vehicleVno;
	}

	public void setVehicleVno(String vehicleVno) {
		this.vehicleVno = vehicleVno;
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


	public String getVsccId() {
		return this.vsccId;
	}

	public void setVsccId(String vsccId) {
		this.vsccId = vsccId;
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

	public Double getReimbursementDifference() {
		return reimbursementDifference;
	}

	public void setReimbursementDifference(Double reimbursementDifference) {
		this.reimbursementDifference = reimbursementDifference;
	}

	public String getDifferenceReason() {
		return differenceReason;
	}

	public void setDifferenceReason(String differenceReason) {
		this.differenceReason = differenceReason;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	@Override
	public String toString() {
		return "ChargeReimbursementsDetails [chargeAmount=" + chargeAmount
				+ ", chargeId=" + chargeId + ", chargeName=" + chargeName
				+ ", contractNo=" + contractNo + ", documentNo=" + documentNo
				+ ", ocrdId=" + ocrdId 
				+ ", vehicleName=" + vehicleName + ", vehicleVin=" + vehicleVin
				+ ", vehicleVno=" + vehicleVno + ", vsccId=" + vsccId
				+ ", vscvId=" + vscvId + "]";
	}

}
