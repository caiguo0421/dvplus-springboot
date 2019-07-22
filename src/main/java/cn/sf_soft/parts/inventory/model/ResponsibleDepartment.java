package cn.sf_soft.parts.inventory.model;
/**
 * 负责部门
 * @author cw
 * @date 2014-4-12 下午3:52:47
 */
public class ResponsibleDepartment {

	private String unitId;
	private String unitName;
	private String unitNo;
	
	public ResponsibleDepartment(){
		
	}
	
	public ResponsibleDepartment(String unitId, String unitName,String unitNo) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitNo = unitNo;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	
	
}
