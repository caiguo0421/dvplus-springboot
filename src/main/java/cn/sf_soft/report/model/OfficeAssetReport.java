package cn.sf_soft.report.model;

import cn.sf_soft.report.ichartjs.ColumnAble;
import cn.sf_soft.report.ichartjs.LineAble;
import cn.sf_soft.report.ichartjs.TouchChartAble;

public class OfficeAssetReport implements ColumnAble,LineAble,TouchChartAble{

	private String  id;
	private String  stationId;
	private String  stationName;
	private String  type;
	private String  typeMeaning;
	private Integer quantity;
	private Double  price;
	
	public OfficeAssetReport(){
		
	}
	
	public OfficeAssetReport(String id, String stationId,
			String stationName, String type, String typeMeaning,
			Integer quantity, Double price) {
		super();
		this.id = id;
		this.stationId = stationId;
		this.stationName = stationName;
		this.type = type;
		this.typeMeaning = typeMeaning;
		this.quantity = quantity;
		this.price = price;
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

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeMeaning() {
		return typeMeaning;
	}

	public void setTypeMeaning(String typeMeaning) {
		this.typeMeaning = typeMeaning;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "OfficeAssetReport [id=" + id + ", stationName="
				+ stationName + ", type=" + type + ", typeMeaning="
				+ typeMeaning + ", quantity=" + quantity + ", price="
				+ price + "]";
	}


	public String getName() {
		return id;
	}


	public float getColumnValue() {
		return quantity == null ? 0:quantity;
	}


	public float getLineValue(int line) {
		Double result = price == null ? 0 : price;
		return Float.valueOf(result.toString());
	}


	public String getLabel() {
		return id;
	}

	public String getTouchChartName() {
		return id;
	}

	public float getTouchChartValue() {
		return quantity == null ? 0:quantity;
	}

	public float getTouchChartValue2() {
		Double result = price == null ? 0 : price;
		return Float.valueOf(result.toString());
	}
	
}
