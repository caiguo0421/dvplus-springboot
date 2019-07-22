package cn.sf_soft.vehicle.customer.model;

import java.util.List;
import java.util.Map;

import cn.sf_soft.basedata.model.BaseOthers;

/**
 * 新建客户页面  基础数据
 * @author cw
 *
 */
public class AddCustomerInitData {
	
	private  List<String> objectProperty;//客户价值
	private  List<String> nation;//民族
	private  List<String> profession;//行业
	private  List<String> position;//职位
	private  List<BaseOthers> area;//区域
	private  List<String> vehicleTrademark;//保有车辆_品牌
	private  Map<String,String> objectNature;//客户性质
	private	 Map<String,String> workGroup;//维系小组
	private  List<ObjectOfPlace> place;//省市区
	//private  String[] vehicleStrain;//保有车辆_品系
	private List<VehicleType> vehicleStrain;//保有车辆_品系
	private AddValidClueInitData addValidClueInitData;//有效线索的基础数据
	private AddIntentClueInitData addIntentClueInitData;//意向线索的基础数据
	
	private List<String> certificateType;//证件类型
	public AddCustomerInitData(){
		
	}
	
	
	
	public List<String> getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(List<String> certificateType) {
		this.certificateType = certificateType;
	}

	public List<String> getObjectProperty() {
		return objectProperty;
	}
	public void setObjectProperty(List<String> objectProperty) {
		this.objectProperty = objectProperty;
	}
	public List<String> getNation() {
		return nation;
	}
	public void setNation(List<String> nation) {
		this.nation = nation;
	}
	public List<String> getProfession() {
		return profession;
	}
	public void setProfession(List<String> profession) {
		this.profession = profession;
	}
	public List<String> getPosition() {
		return position;
	}
	public void setPosition(List<String> position) {
		this.position = position;
	}
	
	
	public List<BaseOthers> getArea() {
		return area;
	}

	public void setArea(List<BaseOthers> area) {
		this.area = area;
	}

	public List<String> getVehicleTrademark() {
		return vehicleTrademark;
	}
	public void setVehicleTrademark(List<String> vehicleTrademark) {
		this.vehicleTrademark = vehicleTrademark;
	}
	
	
	public List<VehicleType> getVehicleStrain() {
		return vehicleStrain;
	}

	public void setVehicleStrain(List<VehicleType> vehicleStrain) {
		this.vehicleStrain = vehicleStrain;
	}

	public AddValidClueInitData getAddValidClueInitData() {
		return addValidClueInitData;
	}
	public void setAddValidClueInitData(AddValidClueInitData addValidClueInitData) {
		this.addValidClueInitData = addValidClueInitData;
	}
	public AddIntentClueInitData getAddIntentClueInitData() {
		return addIntentClueInitData;
	}
	public void setAddIntentClueInitData(AddIntentClueInitData addIntentClueInitData) {
		this.addIntentClueInitData = addIntentClueInitData;
	}

	public Map<String, String> getObjectNature() {
		return objectNature;
	}

	public void setObjectNature(Map<String, String> objectNature) {
		this.objectNature = objectNature;
	}

	public List<ObjectOfPlace> getPlace() {
		return place;
	}

	public void setPlace(List<ObjectOfPlace> place) {
		this.place = place;
	}

	public Map<String, String> getWorkGroup() {
		return workGroup;
	}

	public void setWorkGroup(Map<String, String> workGroup) {
		this.workGroup = workGroup;
	}
	
	
	
}
