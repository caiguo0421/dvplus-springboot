package cn.sf_soft.vehicle.stockbrowse.model;

import java.util.List;
import java.util.Map;

/**
 * 车辆库存浏览界面的初始化搜索条件数据，需从服务端加载
 * 
 * @author king
 * @creation 2013-8-23
 */
public class VehicleStockInitBaseData {
	private List<BaseVehicleWarehouse> vehicleWarehouse;// 车辆仓库
	private Map<String, String> warehouses;// 仓库名-ID
	private String[] vehicleVnos;
	private String[] vehicleNames;
	private String[] vehicleColors;
	private String[] vinPrefix;
	private String[] statusMean;// 库存状态
	private Map<Short, String> viPayTypes;// add by shichunshan 2015/12/1 付款类型
	private Map<String,String> vehicleStrains; //车辆品系
	private String[] vehicleCatalogShortNames; //车型简称
	private List<Object> vehicleKinds;//车型类型


	public Map<String, String> getWarehouses() {
		return warehouses;
	}

	public void setWarehouses(Map<String, String> warehouses) {
		this.warehouses = warehouses;
	}

	public String[] getVehicleVnos() {
		return vehicleVnos;
	}

	public void setVehicleVnos(String[] vehicleVnos) {
		this.vehicleVnos = vehicleVnos;
	}

	public String[] getVehicleNames() {
		return vehicleNames;
	}

	public void setVehicleNames(String[] vehicleNames) {
		this.vehicleNames = vehicleNames;
	}

	public String[] getVehicleColors() {
		return vehicleColors;
	}

	public void setVehicleColors(String[] vehicleColors) {
		this.vehicleColors = vehicleColors;
	}

	public String[] getVinPrefix() {
		return vinPrefix;
	}

	public void setVinPrefix(String[] vinPrefix) {
		this.vinPrefix = vinPrefix;
	}

	public String[] getStatusMean() {
		return statusMean;
	}

	public void setStatusMean(String[] statusMean) {
		this.statusMean = statusMean;
	}

	public List<BaseVehicleWarehouse> getVehicleWarehouse() {
		return vehicleWarehouse;
	}

	public void setVehicleWarehouse(List<BaseVehicleWarehouse> vehicleWarehouse) {
		this.vehicleWarehouse = vehicleWarehouse;
	}

	public Map<Short, String> getViPayTypes() {
		return viPayTypes;
	}

	public void setViPayTypes(Map<Short, String> viPayTypes) {
		this.viPayTypes = viPayTypes;
	}


	public Map<String, String> getVehicleStrains() {
		return vehicleStrains;
	}

	public void setVehicleStrains(Map<String, String> vehicleStrains) {
		this.vehicleStrains = vehicleStrains;
	}

	public String[] getVehicleCatalogShortNames() {
		return vehicleCatalogShortNames;
	}

	public void setVehicleCatalogShortNames(String[] vehicleCatalogShortNames) {
		this.vehicleCatalogShortNames = vehicleCatalogShortNames;
	}

	public List<Object> getVehicleKinds() {
		return vehicleKinds;
	}

	public void setVehicleKinds(List<Object> vehicleKinds) {
		this.vehicleKinds = vehicleKinds;
	}

}
