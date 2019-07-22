package cn.sf_soft.vehicle.customer.model;

import java.sql.Timestamp;

/**
 * BaseVehicleName entity. @author MyEclipse Persistence Tools
 */

public class BaseVehicleName implements java.io.Serializable {

	// Fields

	private String nameId;
	//private String name;
	private Integer repairTime;
	private Double repairMileage;
	private String remark;
	//private String supplierId;
	//private String supplierNo;
	//private String supplierName;
	//private String vehicleType;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String stationId;

	// Constructors

	/** default constructor */
	public BaseVehicleName() {
	}

	/** minimal constructor */
	public BaseVehicleName(String nameId, String name, Integer repairTime,
			Double repairMileage) {
		this.nameId = nameId;
		//this.name = name;
		this.repairTime = repairTime;
		this.repairMileage = repairMileage;
	}

	/** full constructor */
	public BaseVehicleName(String nameId, String name, Integer repairTime,
			Double repairMileage, String remark, String supplierId,
			String supplierNo, String supplierName, String vehicleType,
			String creator, Timestamp createTime, String modifier,
			Timestamp modifyTime, String stationId) {
		this.nameId = nameId;
		//this.name = name;
		this.repairTime = repairTime;
		this.repairMileage = repairMileage;
		this.remark = remark;
		/*this.supplierId = supplierId;
		this.supplierNo = supplierNo;
		this.supplierName = supplierName;
		this.vehicleType = vehicleType;*/
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.stationId = stationId;
	}

	// Property accessors

	public String getNameId() {
		return this.nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	/*public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}*/

	public Integer getRepairTime() {
		return this.repairTime;
	}

	public void setRepairTime(Integer repairTime) {
		this.repairTime = repairTime;
	}

	public Double getRepairMileage() {
		return this.repairMileage;
	}

	public void setRepairMileage(Double repairMileage) {
		this.repairMileage = repairMileage;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

/*	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierNo() {
		return this.supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getVehicleType() {
		return this.vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}*/

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

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

}
