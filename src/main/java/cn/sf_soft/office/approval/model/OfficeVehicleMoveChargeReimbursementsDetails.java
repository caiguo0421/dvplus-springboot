package cn.sf_soft.office.approval.model;

/**
 * OfficeVehicleMoveChargeReimbursementsDetails entity. @author MyEclipse
 * Persistence Tools
 */

public class OfficeVehicleMoveChargeReimbursementsDetails implements
		java.io.Serializable {

	// Fields

	/**
	 *
	 */
	private static final long serialVersionUID = 4031947364085240899L;
	private String ovmcdId;
	private String documentNo;
	private String chargeId;
	private String chargeName;
	private String vehicleId;
	private String vehicleVin;
	private String vehicleVno;
	private String vehicleName;
	private String vehicleKind;
	private Double chargeAmount;
	private String outStockNo;

	// Constructors

	/** default constructor */
	public OfficeVehicleMoveChargeReimbursementsDetails() {
	}

	/** minimal constructor */
	public OfficeVehicleMoveChargeReimbursementsDetails(String ovmcdId,
														String documentNo, String chargeId, String chargeName,
														String vehicleId, String vehicleVin, String vehicleName,
														Double chargeAmount) {
		this.ovmcdId = ovmcdId;
		this.documentNo = documentNo;
		this.chargeId = chargeId;
		this.chargeName = chargeName;
		this.vehicleId = vehicleId;
		this.vehicleVin = vehicleVin;
		this.vehicleName = vehicleName;
		this.chargeAmount = chargeAmount;
	}

	/** full constructor */
	public OfficeVehicleMoveChargeReimbursementsDetails(String ovmcdId,
														String documentNo, String chargeId, String chargeName,
														String vehicleId, String vehicleVin, String vehicleVno,
														String vehicleName, String vehicleKind, Double chargeAmount) {
		this.ovmcdId = ovmcdId;
		this.documentNo = documentNo;
		this.chargeId = chargeId;
		this.chargeName = chargeName;
		this.vehicleId = vehicleId;
		this.vehicleVin = vehicleVin;
		this.vehicleVno = vehicleVno;
		this.vehicleName = vehicleName;
		this.vehicleKind = vehicleKind;
		this.chargeAmount = chargeAmount;
	}

	// Property accessors

	public String getOvmcdId() {
		return this.ovmcdId;
	}

	public void setOvmcdId(String ovmcdId) {
		this.ovmcdId = ovmcdId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
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

	public String getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleVin() {
		return this.vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
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

	public String getVehicleKind() {
		return this.vehicleKind;
	}

	public void setVehicleKind(String vehicleKind) {
		this.vehicleKind = vehicleKind;
	}

	public Double getChargeAmount() {
		return this.chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getOutStockNo() {
		return outStockNo;
	}

	public void setOutStockNo(String outStockNo) {
		this.outStockNo = outStockNo;
	}
}
