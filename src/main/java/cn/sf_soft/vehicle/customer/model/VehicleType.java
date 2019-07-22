package cn.sf_soft.vehicle.customer.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * VehicleType entity. @author MyEclipse Persistence Tools
 */

/**
 * 车辆型号,车辆名称，车辆品系等
 */
@Entity
public class VehicleType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1581131087828139586L;

	@Id
	@Column(name = "vno_id")
	private String vnoId;
	
	@Column(name="station_id")
	private String stationId;
	// private String vehicleSalesCode;
	@Column(name="vehicle_vno")
	private String vehicleVno;
	
	@Column(name="vehicle_brand")
	private String vehicleBrand;// 品牌
	
	@Column(name="vehicle_name")
	private String vehicleName;// 名称
	
	@Column(name="vehicle_strain")
	private String vehicleStrain;// 品系
	
//	private Short catalogsType;
	@Column(name="vehicle_name_id")
	private String vehicleNameId;
	
	@Column(name="parent_id")
	private String parentId;
	
	@Column(name="name_group")
	private String nameGroup;// 品牌，品系或名称

	public VehicleType() {

	}


	public String getVnoId() {
		return vnoId;
	}

	public void setVnoId(String vnoId) {
		this.vnoId = vnoId;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getVehicleVno() {
		return vehicleVno;
	}

	public void setVehicleVno(String vehicleVno) {
		this.vehicleVno = vehicleVno;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehicleNameId() {
		return vehicleNameId;
	}

	public void setVehicleNameId(String vehicleNameId) {
		this.vehicleNameId = vehicleNameId;
	}

//	public Short getCatalogsType() {
//		return catalogsType;
//	}
//
//	public void setCatalogsType(Short catalogsType) {
//		this.catalogsType = catalogsType;
//	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getVehicleStrain() {
		return vehicleStrain;
	}

	public void setVehicleStrain(String vehicleStrain) {
		this.vehicleStrain = vehicleStrain;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getNameGroup() {
		return nameGroup;
	}

	public void setNameGroup(String nameGroup) {
		this.nameGroup = nameGroup;
	}

	@Override
	public String toString() {
		return "VehicleType [vnoId=" + vnoId + ", stationId=" + stationId
				+ ", vehicleVno=" + vehicleVno + ", vehicleBrand="
				+ vehicleBrand + ", vehicleName=" + vehicleName
				+ ", vehicleStrain=" + vehicleStrain 
				+ ", vehicleNameId=" + vehicleNameId
				+ ", parentId=" + parentId + ", nameGroup=" + nameGroup + "]";
	}

}
