package cn.sf_soft.parts.stockborwse.model;

import java.io.Serializable;

/**
 * 配件仓库
 * @author king
 * @create 2013-9-11下午3:56:42
 */
public class PartWarehouse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3077433407721329401L;
	private String warehouseId;
	private String warehouseName;
	private String stationId;
	private String stationName;
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
}
