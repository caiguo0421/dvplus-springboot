package cn.sf_soft.vehicle.customer.model;

import java.sql.Timestamp;

/**
 * 保有车辆
 * CustomerRetainVehicle entity. @author MyEclipse Persistence Tools
 */

public class CustomerRetainVehicle implements java.io.Serializable {

	// Fields

	private String selfId;
	private String customerId;
	private String vehicleBrand;
	private String vehicleStrain;
	private String vehicleSalesCode;
	private String vehicleVno;
	private String driveType;
	private String engineModel;
	private String trailerType;
	private String profession;
	private Integer quantity;
	private String transportObject;
	private Timestamp purchaseDate;
	private String highwayCondition;
	private String line;
	private String advantagesDisadvantages;
	private String remark;

	// Constructors

	/** default constructor */
	public CustomerRetainVehicle() {
	}

	/** minimal constructor */
	public CustomerRetainVehicle(String selfId, String customerId,
			String vehicleBrand) {
		this.selfId = selfId;
		this.customerId = customerId;
		this.vehicleBrand = vehicleBrand;
	}

	/** full constructor */
	public CustomerRetainVehicle(String selfId, String customerId,
			String vehicleBrand, String vehicleStrain, String vehicleSalesCode,
			String vehicleVno, String driveType, String engineModel,
			String trailerType, String profession, Integer quantity,
			String transportObject, Timestamp purchaseDate,
			String highwayCondition, String line,
			String advantagesDisadvantages, String remark) {
		this.selfId = selfId;
		this.customerId = customerId;
		this.vehicleBrand = vehicleBrand;
		this.vehicleStrain = vehicleStrain;
		this.vehicleSalesCode = vehicleSalesCode;
		this.vehicleVno = vehicleVno;
		this.driveType = driveType;
		this.engineModel = engineModel;
		this.trailerType = trailerType;
		this.profession = profession;
		this.quantity = quantity;
		this.transportObject = transportObject;
		this.purchaseDate = purchaseDate;
		this.highwayCondition = highwayCondition;
		this.line = line;
		this.advantagesDisadvantages = advantagesDisadvantages;
		this.remark = remark;
	}

	// Property accessors

	public String getSelfId() {
		return this.selfId;
	}

	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getVehicleBrand() {
		return this.vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getVehicleStrain() {
		return this.vehicleStrain;
	}

	public void setVehicleStrain(String vehicleStrain) {
		this.vehicleStrain = vehicleStrain;
	}

	public String getVehicleSalesCode() {
		return this.vehicleSalesCode;
	}

	public void setVehicleSalesCode(String vehicleSalesCode) {
		this.vehicleSalesCode = vehicleSalesCode;
	}

	public String getVehicleVno() {
		return this.vehicleVno;
	}

	public void setVehicleVno(String vehicleVno) {
		this.vehicleVno = vehicleVno;
	}

	public String getDriveType() {
		return this.driveType;
	}

	public void setDriveType(String driveType) {
		this.driveType = driveType;
	}

	public String getEngineModel() {
		return this.engineModel;
	}

	public void setEngineModel(String engineModel) {
		this.engineModel = engineModel;
	}

	public String getTrailerType() {
		return this.trailerType;
	}

	public void setTrailerType(String trailerType) {
		this.trailerType = trailerType;
	}

	public String getProfession() {
		return this.profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getTransportObject() {
		return this.transportObject;
	}

	public void setTransportObject(String transportObject) {
		this.transportObject = transportObject;
	}

	public Timestamp getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Timestamp purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getHighwayCondition() {
		return this.highwayCondition;
	}

	public void setHighwayCondition(String highwayCondition) {
		this.highwayCondition = highwayCondition;
	}

	public String getLine() {
		return this.line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getAdvantagesDisadvantages() {
		return this.advantagesDisadvantages;
	}

	public void setAdvantagesDisadvantages(String advantagesDisadvantages) {
		this.advantagesDisadvantages = advantagesDisadvantages;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
