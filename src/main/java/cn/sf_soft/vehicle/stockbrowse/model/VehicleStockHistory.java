package cn.sf_soft.vehicle.stockbrowse.model;

import java.sql.Timestamp;

/**
 * 车辆出入库历史
 * VehicleStockHistoryId entity. @author MyEclipse Persistence Tools
 */
public class VehicleStockHistory implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1139196492829531682L;
	private String vehicleId;
	private String inStockNo;
	private Double vehicleCost;
	private Double vehicleCarriage;
	private Timestamp inStockTime;
	private String supplierName;
	private String stationId;
	private String warehouseName;
	private String creator;
	private Timestamp createTime;
	private String approver;
	private Timestamp approveTime;
	private String meaning;
	private String stationName;

	// Constructors

	/** default constructor */
	public VehicleStockHistory() {
	}

	/** full constructor */
	public VehicleStockHistory(String vehicleId, String inStockNo,
			Double vehicleCost, Double vehicleCarriage, Timestamp inStockTime,
			String supplierName, String stationId, String warehouseName,
			String creator, Timestamp createTime, String approver,
			Timestamp approveTime, String meaning, String stationName) {
		this.vehicleId = vehicleId;
		this.inStockNo = inStockNo;
		this.vehicleCost = vehicleCost;
		this.vehicleCarriage = vehicleCarriage;
		this.inStockTime = inStockTime;
		this.supplierName = supplierName;
		this.stationId = stationId;
		this.warehouseName = warehouseName;
		this.creator = creator;
		this.createTime = createTime;
		this.approver = approver;
		this.approveTime = approveTime;
		this.meaning = meaning;
		this.stationName = stationName;
	}

	// Property accessors

	public String getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getInStockNo() {
		return this.inStockNo;
	}

	public void setInStockNo(String inStockNo) {
		this.inStockNo = inStockNo;
	}

	public Double getVehicleCost() {
		return this.vehicleCost;
	}

	public void setVehicleCost(Double vehicleCost) {
		this.vehicleCost = vehicleCost;
	}

	public Double getVehicleCarriage() {
		return this.vehicleCarriage;
	}

	public void setVehicleCarriage(Double vehicleCarriage) {
		this.vehicleCarriage = vehicleCarriage;
	}

	public Timestamp getInStockTime() {
		return this.inStockTime;
	}

	public void setInStockTime(Timestamp inStockTime) {
		this.inStockTime = inStockTime;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getApprover() {
		return this.approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public String getMeaning() {
		return this.meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VehicleStockHistory))
			return false;
		VehicleStockHistory castOther = (VehicleStockHistory) other;

		return ((this.getVehicleId() == castOther.getVehicleId()) || (this
				.getVehicleId() != null && castOther.getVehicleId() != null && this
				.getVehicleId().equals(castOther.getVehicleId())))
				&& ((this.getInStockNo() == castOther.getInStockNo()) || (this
						.getInStockNo() != null
						&& castOther.getInStockNo() != null && this
						.getInStockNo().equals(castOther.getInStockNo())))
				&& ((this.getVehicleCost() == castOther.getVehicleCost()) || (this
						.getVehicleCost() != null
						&& castOther.getVehicleCost() != null && this
						.getVehicleCost().equals(castOther.getVehicleCost())))
				&& ((this.getVehicleCarriage() == castOther
						.getVehicleCarriage()) || (this.getVehicleCarriage() != null
						&& castOther.getVehicleCarriage() != null && this
						.getVehicleCarriage().equals(
								castOther.getVehicleCarriage())))
				&& ((this.getInStockTime() == castOther.getInStockTime()) || (this
						.getInStockTime() != null
						&& castOther.getInStockTime() != null && this
						.getInStockTime().equals(castOther.getInStockTime())))
				&& ((this.getSupplierName() == castOther.getSupplierName()) || (this
						.getSupplierName() != null
						&& castOther.getSupplierName() != null && this
						.getSupplierName().equals(castOther.getSupplierName())))
				&& ((this.getStationId() == castOther.getStationId()) || (this
						.getStationId() != null
						&& castOther.getStationId() != null && this
						.getStationId().equals(castOther.getStationId())))
				&& ((this.getWarehouseName() == castOther.getWarehouseName()) || (this
						.getWarehouseName() != null
						&& castOther.getWarehouseName() != null && this
						.getWarehouseName()
						.equals(castOther.getWarehouseName())))
				&& ((this.getCreator() == castOther.getCreator()) || (this
						.getCreator() != null && castOther.getCreator() != null && this
						.getCreator().equals(castOther.getCreator())))
				&& ((this.getCreateTime() == castOther.getCreateTime()) || (this
						.getCreateTime() != null
						&& castOther.getCreateTime() != null && this
						.getCreateTime().equals(castOther.getCreateTime())))
				&& ((this.getApprover() == castOther.getApprover()) || (this
						.getApprover() != null
						&& castOther.getApprover() != null && this
						.getApprover().equals(castOther.getApprover())))
				&& ((this.getApproveTime() == castOther.getApproveTime()) || (this
						.getApproveTime() != null
						&& castOther.getApproveTime() != null && this
						.getApproveTime().equals(castOther.getApproveTime())))
				&& ((this.getMeaning() == castOther.getMeaning()) || (this
						.getMeaning() != null && castOther.getMeaning() != null && this
						.getMeaning().equals(castOther.getMeaning())))
				&& ((this.getStationName() == castOther.getStationName()) || (this
						.getStationName() != null
						&& castOther.getStationName() != null && this
						.getStationName().equals(castOther.getStationName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getVehicleId() == null ? 0 : this.getVehicleId().hashCode());
		result = 37 * result
				+ (getInStockNo() == null ? 0 : this.getInStockNo().hashCode());
		result = 37
				* result
				+ (getVehicleCost() == null ? 0 : this.getVehicleCost()
						.hashCode());
		result = 37
				* result
				+ (getVehicleCarriage() == null ? 0 : this.getVehicleCarriage()
						.hashCode());
		result = 37
				* result
				+ (getInStockTime() == null ? 0 : this.getInStockTime()
						.hashCode());
		result = 37
				* result
				+ (getSupplierName() == null ? 0 : this.getSupplierName()
						.hashCode());
		result = 37 * result
				+ (getStationId() == null ? 0 : this.getStationId().hashCode());
		result = 37
				* result
				+ (getWarehouseName() == null ? 0 : this.getWarehouseName()
						.hashCode());
		result = 37 * result
				+ (getCreator() == null ? 0 : this.getCreator().hashCode());
		result = 37
				* result
				+ (getCreateTime() == null ? 0 : this.getCreateTime()
						.hashCode());
		result = 37 * result
				+ (getApprover() == null ? 0 : this.getApprover().hashCode());
		result = 37
				* result
				+ (getApproveTime() == null ? 0 : this.getApproveTime()
						.hashCode());
		result = 37 * result
				+ (getMeaning() == null ? 0 : this.getMeaning().hashCode());
		result = 37
				* result
				+ (getStationName() == null ? 0 : this.getStationName()
						.hashCode());
		return result;
	}

}
