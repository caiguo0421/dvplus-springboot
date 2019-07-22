package cn.sf_soft.parts.stockborwse.model;

import java.util.List;

/**
 * 配件库存查询条件
 * @author king
 * @create 2013-9-5上午10:23:20
 */
public class PartStockSearchCriteria {

	private String partNo;//配件编码或自编码
	private String partType;//配件类型
	private List<String> stationIds;//站点ID
	private String warehouseId;//仓库ID
	private String applicableModel;//适用车型
	private String partName;//配件名称
	private String specModel;//规格型号
	
	
	public List<String> getStationIds() {
		return stationIds;
	}
	public void setStationIds(List<String> stationId) {
		this.stationIds = stationId;
	}
	public String getApplicableModel() {
		return applicableModel;
	}
	public void setApplicableModel(String applicableModel) {
		this.applicableModel = applicableModel;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getSpecModel() {
		return specModel;
	}
	public void setSpecModel(String specModel) {
		this.specModel = specModel;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getPartType() {
		return partType;
	}
	public void setPartType(String partType) {
		this.partType = partType;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	@Override
	public String toString() {
		return "PartStockSearchCriteria [partNo=" + partNo + ", partType="
				+ partType + ", stationIds=" + stationIds + ", warehouseId="
				+ warehouseId + ", applicableModel=" + applicableModel
				+ ", partName=" + partName + ", specModel=" + specModel + "]";
	}
}
