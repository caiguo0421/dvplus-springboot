package cn.sf_soft.parts.inventory.model;

import java.sql.Timestamp;

/**
 * VwVehicleStockId entity. @author MyEclipse Persistence Tools
 */

public class Warehouse implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1406952656160126508L;
	// Fields

	
	private String warehouseName;
	private String warehouseId;
	

	// Constructors

	/** default constructor */
	public Warehouse() {
	}
	

	public Warehouse(String warehouseName, String warehouseId) {
		super();
		this.warehouseName = warehouseName;
		this.warehouseId = warehouseId;
	}


	public String getWarehouseName() {
		return warehouseName;
	}


	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}


	public String getWarehouseId() {
		return warehouseId;
	}


	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	

}
