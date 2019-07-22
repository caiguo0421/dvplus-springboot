package cn.sf_soft.parts.inventory.model;

public class PartCheckStockPlanInitData {

	private String[] planNos;//计划单号
	private String[] partPosition;//库位
	
	public PartCheckStockPlanInitData(){
		
	}
	public PartCheckStockPlanInitData(String[] planNos, String[] partPosition) {
		super();
		this.planNos = planNos;
		this.partPosition = partPosition;
	}
	public String[] getPlanNos() {
		return planNos;
	}
	public void setPlanNos(String[] planNos) {
		this.planNos = planNos;
	}
	public String[] getPartPosition() {
		return partPosition;
	}
	public void setPartPosition(String[] partPosition) {
		this.partPosition = partPosition;
	}
	
	
}
