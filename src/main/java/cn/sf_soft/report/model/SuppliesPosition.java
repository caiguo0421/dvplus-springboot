package cn.sf_soft.report.model;

public class SuppliesPosition {
	private String id;
	private String stationId;
	private String positionCode;
	private String positionName;
	private String modifyUserName;
	private String modifyDatetime;
	private String status;
	private String remark;
	private String nature;
	
	
	public SuppliesPosition(){
		
	}
	
	public SuppliesPosition(String id, String stationId, String positionCode,
			String positionName, String modifyUserName, String modifyDatetime,
			String status, String remark, String nature) {
		super();
		this.id = id;
		this.stationId = stationId;
		this.positionCode = positionCode;
		this.positionName = positionName;
		this.modifyUserName = modifyUserName;
		this.modifyDatetime = modifyDatetime;
		this.status = status;
		this.remark = remark;
		this.nature = nature;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	public String getModifyDatetime() {
		return modifyDatetime;
	}
	public void setModifyDatetime(String modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	
	
}
