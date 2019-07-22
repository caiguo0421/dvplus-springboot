package cn.sf_soft.vehicle.customer.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 意向客户-保有车辆
 */
@Entity
@Table(name = "customer_retain_vehicle_overview", schema = "dbo")
public class CustomerRetainVehicleOverview implements java.io.Serializable {

	// Fields

	private String selfId;
	private String customerId;
	private String profession;
	private String vehicleBrand;
	private String vehicleVno;
	private Double vehicleAge;
	private Double vehicleCount;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String vehiclePurchaseYear;
	private String vehicleName;
	private Boolean isOurPurchase;
	private Boolean isDfVehicle;

	// Constructors

	public CustomerRetainVehicleOverview() {
	}

	// Property accessors
	@Id
	@Column(name = "self_id", unique = true, nullable = false, length = 40)
	public String getSelfId() {
		return this.selfId;
	}

	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}

	@Column(name = "customer_id", length = 40)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "profession", length = 100)
	public String getProfession() {
		return this.profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	@Column(name = "vehicle_brand", length = 100)
	public String getVehicleBrand() {
		return this.vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	@Column(name = "vehicle_vno", length = 100)
	public String getVehicleVno() {
		return this.vehicleVno;
	}

	public void setVehicleVno(String vehicleVno) {
		this.vehicleVno = vehicleVno;
	}

	@Column(name = "vehicle_age", precision = 10, scale = 0)
	public Double getVehicleAge() {
		return this.vehicleAge;
	}

	public void setVehicleAge(Double vehicleAge) {
		this.vehicleAge = vehicleAge;
	}

	@Column(name = "vehicle_count", precision = 10, scale = 0)
	public Double getVehicleCount() {
		return this.vehicleCount;
	}

	public void setVehicleCount(Double vehicleCount) {
		this.vehicleCount = vehicleCount;
	}

	@Column(name = "creator", length = 50)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "create_time", length = 23)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "modifier", length = 50)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "modify_time", length = 23)
	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "vehicle_purchase_year", length = 40)
	public String getVehiclePurchaseYear() {
		return this.vehiclePurchaseYear;
	}

	public void setVehiclePurchaseYear(String vehiclePurchaseYear) {
		this.vehiclePurchaseYear = vehiclePurchaseYear;
	}

	@Column(name = "vehicle_name", length = 100)
	public String getVehicleName() {
		return this.vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	@Column(name = "is_our_purchase")
	public Boolean getIsOurPurchase() {
		return this.isOurPurchase;
	}

	public void setIsOurPurchase(Boolean isOurPurchase) {
		this.isOurPurchase = isOurPurchase;
	}

	@Column(name = "is_df_vehicle")
	public Boolean getIsDfVehicle() {
		return this.isDfVehicle;
	}

	public void setIsDfVehicle(Boolean isDfVehicle) {
		this.isDfVehicle = isDfVehicle;
	}

}