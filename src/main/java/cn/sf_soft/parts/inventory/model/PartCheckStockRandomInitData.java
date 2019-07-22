package cn.sf_soft.parts.inventory.model;

import java.util.Map;

public class PartCheckStockRandomInitData {

	private Map<String,String> warehouse;
	private String[] partPosition;
	
	public PartCheckStockRandomInitData(){
		
	}
	public PartCheckStockRandomInitData(Map<String, String> warehouse,
			String[] partPosition) {
		super();
		this.warehouse = warehouse;
		this.partPosition = partPosition;
	}

	public Map<String, String> getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Map<String, String> warehouse) {
		this.warehouse = warehouse;
	}

	public String[] getPartPosition() {
		return partPosition;
	}

	public void setPartPosition(String[] partPosition) {
		this.partPosition = partPosition;
	}
	
	
}
