package cn.sf_soft.vehicle.stockbrowse.model;

import java.io.Serializable;
import java.util.List;

/**
 * 车辆库存浏览的查询条件，条件增加时请自行扩展
 * 
 * @author king
 * @create 2013-8-15下午2:53:58
 */
public class VehicleStockSearchCriteria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5649808589094055212L;
	private String vehicleVno; // 车辆型号
	private String vehicleName; // 车辆名称
	private String vehicleColor;// 车辆颜色
	private String vehicleVin; // VIN码
	private String vehicleStatus;// 车辆状态
	private String wareHouseId; // 仓库ID
	private short vehiclePayType; // 付款类型 add by shichunshan
	private int stockAgeMin; // 库龄
	private int stockAgeMax; // 库龄
	private List<String> vehicleVnos;

	private String vehicleCatalogShortName;//车型简称
	private String  vehicleStrain; //品系
	private Short viPayType;//付款方式
	private Integer[] wirteOffFlag;//销账状态,多选
	private Integer payDayMin;//距监控到期天数
	private Integer payDayMax;//距监控到期天数
	private String filterStr;//筛选条件
	private List<Short> vehicleKind; //车型类型
	private String storageAgeType;//库龄类型： 入库库龄/开票库龄

	private String orderBy;//排序方式：asc/desc


	transient private List<String> stationIds;// 站点ID;

	// transient标识此字段不被gson序列化和反序列化，stationId作为单独的参数由拦截器进行校验后设置进来

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

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getVehicleVin() {
		return vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
	}

	public int getStockAgeMin() {
		return stockAgeMin;
	}

	public void setStockAgeMin(int stockAgeMin) {
		this.stockAgeMin = stockAgeMin;
	}

	public int getStockAgeMax() {
		return stockAgeMax;
	}

	public void setStockAgeMax(int stockAgeMax) {
		this.stockAgeMax = stockAgeMax;
	}

	public String getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(String wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public List<String> getStationIds() {
		return stationIds;
	}

	public void setStationIds(List<String> stationIds) {
		this.stationIds = stationIds;
	}

	@Override
	public String toString() {
		return "VehicleStockSearchCriteria [vehicleVno=" + vehicleVno
				+ ", vehicleName=" + vehicleName + ", vehicleColor="
				+ vehicleColor + ", vehicleVin=" + vehicleVin
				+ ", vehiclePayType=" + vehiclePayType + ", vehicleStatus="
				+ vehicleStatus + ", wareHouseId=" + wareHouseId
				+ ", stockAgeMin=" + stockAgeMin + ", stockAgeMax="
				+ stockAgeMax + ", stationIds=" + stationIds + "]";
	}

	public short getVehiclePayType() {
		return vehiclePayType;
	}

	public void setVehiclePayType(short vehiclePayType) {
		this.vehiclePayType = vehiclePayType;
	}

	public List<String> getVehicleVnos() {
		return vehicleVnos;
	}

	public void setVehicleVnos(List<String> vehicleVnos) {
		this.vehicleVnos = vehicleVnos;
	}

	public String getVehicleCatalogShortName() {
		return vehicleCatalogShortName;
	}

	public void setVehicleCatalogShortName(String vehicleCatalogShortName) {
		this.vehicleCatalogShortName = vehicleCatalogShortName;
	}

	public String getVehicleStrain() {
		return vehicleStrain;
	}

	public void setVehicleStrain(String vehicleStrain) {
		this.vehicleStrain = vehicleStrain;
	}

	public Short getViPayType() {
		return viPayType;
	}

	public void setViPayType(Short viPayType) {
		this.viPayType = viPayType;
	}

	public Integer[] getWirteOffFlag() {
		return wirteOffFlag;
	}

	public void setWirteOffFlag(Integer[] wirteOffFlag) {
		this.wirteOffFlag = wirteOffFlag;
	}

	public Integer getPayDayMin() {
		return payDayMin;
	}

	public void setPayDayMin(Integer payDayMin) {
		this.payDayMin = payDayMin;
	}

	public Integer getPayDayMax() {
		return payDayMax;
	}

	public void setPayDayMax(Integer payDayMax) {
		this.payDayMax = payDayMax;
	}

	public String getFilterStr() {
		return filterStr;
	}

	public void setFilterStr(String filterStr) {
		this.filterStr = filterStr;
	}

	public List<Short> getVehicleKind() {
		return vehicleKind;
	}

	public void setVehicleKind(List<Short> vehicleKind) {
		this.vehicleKind = vehicleKind;
	}

	public String getStorageAgeType() {
		return storageAgeType;
	}

	public void setStorageAgeType(String storageAgeType) {
		this.storageAgeType = storageAgeType;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
