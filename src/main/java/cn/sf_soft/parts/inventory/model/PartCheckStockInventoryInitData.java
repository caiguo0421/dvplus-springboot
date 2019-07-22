package cn.sf_soft.parts.inventory.model;

import java.util.List;

/**
 * 已盘点界面基础数据
 * @author cw
 * @date 2014-4-12 下午4:14:48
 */
public class PartCheckStockInventoryInitData {

	private List<ResponsibleDepartment> responsibleDepartment;
	
	
	public PartCheckStockInventoryInitData(){
		
	}


	public PartCheckStockInventoryInitData(
			List<ResponsibleDepartment> responsibleDepartment) {
		super();
		this.responsibleDepartment = responsibleDepartment;
	}


	public List<ResponsibleDepartment> getResponsibleDepartment() {
		return responsibleDepartment;
	}


	public void setResponsibleDepartment(
			List<ResponsibleDepartment> responsibleDepartment) {
		this.responsibleDepartment = responsibleDepartment;
	}
	
	
	
	
}
