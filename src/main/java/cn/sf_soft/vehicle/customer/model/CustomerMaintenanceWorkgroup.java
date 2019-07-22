package cn.sf_soft.vehicle.customer.model;

/**
 * CustomerMaintenanceWorkgroup entity. @author MyEclipse Persistence Tools
 */

public class CustomerMaintenanceWorkgroup implements java.io.Serializable {

	// Fields

	private String selfId;
	private String objectId;
	private String stationId;
	private String bmwId;

	// Constructors

	/** default constructor */
	public CustomerMaintenanceWorkgroup() {
	}

	/** minimal constructor */
	public CustomerMaintenanceWorkgroup(String selfId) {
		this.selfId = selfId;
	}

	/** full constructor */
	public CustomerMaintenanceWorkgroup(String selfId, String objectId,
			String stationId, String bmwId) {
		this.selfId = selfId;
		this.objectId = objectId;
		this.stationId = stationId;
		this.bmwId = bmwId;
	}

	// Property accessors

	public String getSelfId() {
		return this.selfId;
	}

	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getBmwId() {
		return this.bmwId;
	}

	public void setBmwId(String bmwId) {
		this.bmwId = bmwId;
	}

}
