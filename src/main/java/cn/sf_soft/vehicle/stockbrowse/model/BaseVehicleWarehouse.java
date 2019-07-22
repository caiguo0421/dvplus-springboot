package cn.sf_soft.vehicle.stockbrowse.model;



/**
 * 车辆仓库基础信息
 * BaseVehicleWarehouse entity. @author MyEclipse Persistence Tools
 */

public class BaseVehicleWarehouse  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -1901527862009834441L;
	private String warehouseId;
     private String warehouseName;
     private Short warehouseType;
     private String stationId;
     private Boolean forbidFlag;
     
//     private String principalId;
//     private String mobile;
//     private String parentId;
//     private String principal;
//     private String position;  
//     private String remark;
//     private Boolean isGroup;
//     private String groupName;

    // Constructors

    /** default constructor */
    public BaseVehicleWarehouse() {
    }

	/** minimal constructor */
    public BaseVehicleWarehouse(String warehouseId) {
        this.warehouseId = warehouseId;
    }
    
    // Property accessors

    public BaseVehicleWarehouse(String warehouseId, String warehouseName,
			String stationId) {
		super();
		this.warehouseId = warehouseId;
		this.warehouseName = warehouseName;
		this.stationId = stationId;
	}

	public String getWarehouseId() {
        return this.warehouseId;
    }
    
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return this.warehouseName;
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Short getWarehouseType() {
        return this.warehouseType;
    }
    
    public void setWarehouseType(Short warehouseType) {
        this.warehouseType = warehouseType;
    }

    public String getStationId() {
        return this.stationId;
    }
    
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

   

    public Boolean getForbidFlag() {
        return this.forbidFlag;
    }
    
    public void setForbidFlag(Boolean forbidFlag) {
        this.forbidFlag = forbidFlag;
    }

	@Override
	public String toString() {
		return "BaseVehicleWarehouse [warehouseId=" + warehouseId
				+ ", warehouseName=" + warehouseName + ", warehouseType="
				+ warehouseType + ", stationId=" + stationId + ", forbidFlag="
				+ forbidFlag + "]";
	}

}
