package cn.sf_soft.vehicle.customer.model;

public class ObjectOfPlace {

	private String placeId;
	private String name;
	private String parentId;
	
	public ObjectOfPlace(){
		
	}
	public ObjectOfPlace(String placeId, String name, String parentId) {
		super();
		this.placeId = placeId;
		this.name = name;
		this.parentId = parentId;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Override
	public String toString() {
		return "ObjectOfPlace [placeId=" + placeId + ", name=" + name
				+ ", parentId=" + parentId + "]";
	}
	
	
}
