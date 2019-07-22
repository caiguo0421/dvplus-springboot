package cn.sf_soft.vehicle.customer.model;

import java.util.List;
import java.util.Map;

import cn.sf_soft.basedata.model.BaseOthers;

/**
 * 编辑(新建、修改)意向客户时需要的基础数据
 * @创建人 LiuJin
 * @创建时间 2014-11-14 下午2:46:12
 * @修改人 
 * @修改时间
 */
public class EditCustomerInitData {

	private List<String> objectProperty;//客户价值
	private List<String> nation;//民族
	private List<String> profession;//行业
	private List<String> position;//职位
	private List<String> vehicleTrademark;//保有车辆_品牌
	private List<VehicleType> vehicleStrain;//保有车辆_品系
	private Map<String, Short> objectNature;//对象性质
	private List<ObjectOfPlace> place;//省市区
	private List<String> certificateType;//证件类型
	
	private List<BaseOthers> vehicleColor;//车辆颜色
	private List<String> attentionEmphases;//关注重点
	private Map<String, Short> buyType; //购买方式
	private List<String> visitMode;//拜访方式
	private List<String> visitorMode;//来访方式
	private List<String> knowWay;//了解渠道 
	private List<String> visitorLevel; //成交概率
	private Map<String, Short> visitResult; //跟踪结果
	private List<String> reason;//成败原因
	private List<BaseOthers> deliveryLocus;//销售网点
	
	
	private List<BaseOthers> purchaseUse; //购车用途
	private List<String> distance;//运输距离
	private List<String> factLoad;//实际载重
	private Map<String, Short> visitAddr;//接触地点
	 
	private List<String> backWay;//方式
	private List<String> purpose;//目的
	
	private List<BaseOthers> subjectMatter;//标的物
	private List<String> transportRoutes;//运输路线

	private List<String> workingCondtion;//工况

	private List<String> customerSource; //客户来源

	private List<Map<String, Object>> baseVehicleName;
	
	
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

	public List<String> getVehicleTrademark() {
		return vehicleTrademark;
	}

	public void setVehicleTrademark(List<String> vehicleTrademark) {
		this.vehicleTrademark = vehicleTrademark;
	}

	public Map<String, Short> getObjectNature() {
		return objectNature;
	}

	public void setObjectNature(Map<String, Short> objectNature) {
		this.objectNature = objectNature;
	}

//	public Map<String, String> getWorkGroup() {
//		return workGroup;
//	}
//
//	public void setWorkGroup(Map<String, String> workGroup) {
//		this.workGroup = workGroup;
//	}

	public List<ObjectOfPlace> getPlace() {
		return place;
	}

	public void setPlace(List<ObjectOfPlace> place) {
		this.place = place;
	}

	public List<VehicleType> getVehicleStrain() {
		return vehicleStrain;
	}

	public void setVehicleStrain(List<VehicleType> vehicleStrain) {
		this.vehicleStrain = vehicleStrain;
	}

	public List<String> getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(List<String> certificateType) {
		this.certificateType = certificateType;
	}

	public List<BaseOthers> getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(List<BaseOthers> vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public List<String> getAttentionEmphases() {
		return attentionEmphases;
	}

	public void setAttentionEmphases(List<String> attentionEmphases) {
		this.attentionEmphases = attentionEmphases;
	}

	public Map<String, Short> getBuyType() {
		return buyType;
	}

	public void setBuyType(Map<String, Short> buyType) {
		this.buyType = buyType;
	}

	public List<String> getVisitMode() {
		return visitMode;
	}

	public void setVisitMode(List<String> visitMode) {
		this.visitMode = visitMode;
	}

	public List<String> getVisitorMode() {
		return visitorMode;
	}

	public void setVisitorMode(List<String> visitorMode) {
		this.visitorMode = visitorMode;
	}

	public List<String> getKnowWay() {
		return knowWay;
	}

	public void setKnowWay(List<String> knowWay) {
		this.knowWay = knowWay;
	}

	public List<String> getVisitorLevel() {
		return visitorLevel;
	}

	public void setVisitorLevel(List<String> visitorLevel) {
		this.visitorLevel = visitorLevel;
	}

	public Map<String, Short> getVisitResult() {
		return visitResult;
	}

	public void setVisitResult(Map<String, Short> visitResult) {
		this.visitResult = visitResult;
	}

	public List<String> getReason() {
		return reason;
	}

	public void setReason(List<String> reason) {
		this.reason = reason;
	}

	public List<BaseOthers> getDeliveryLocus() {
		return deliveryLocus;
	}

	public void setDeliveryLocus(List<BaseOthers> deliveryLocus) {
		this.deliveryLocus = deliveryLocus;
	}

	public List<BaseOthers> getPurchaseUse() {
		return purchaseUse;
	}

	public void setPurchaseUse(List<BaseOthers> purchaseUse) {
		this.purchaseUse = purchaseUse;
	}

	public List<String> getDistance() {
		return distance;
	}

	public void setDistance(List<String> distance) {
		this.distance = distance;
	}

	public List<String> getFactLoad() {
		return factLoad;
	}

	public void setFactLoad(List<String> factLoad) {
		this.factLoad = factLoad;
	}

	public Map<String, Short> getVisitAddr() {
		return visitAddr;
	}

	public void setVisitAddr(Map<String, Short> visitAddr) {
		this.visitAddr = visitAddr;
	}

	public List<String> getBackWay() {
		return backWay;
	}

	public void setBackWay(List<String> backWay) {
		this.backWay = backWay;
	}

	public List<String> getPurpose() {
		return purpose;
	}

	public void setPurpose(List<String> purpose) {
		this.purpose = purpose;
	}

	public List<BaseOthers> getSubjectMatter() {
		return subjectMatter;
	}

	public void setSubjectMatter(List<BaseOthers> subjectMatter) {
		this.subjectMatter = subjectMatter;
	}

	public List<String> getTransportRoutes() {
		return transportRoutes;
	}

	public void setTransportRoutes(List<String> transportRoutes) {
		this.transportRoutes = transportRoutes;
	}

	public List<String> getWorkingCondtion() {
		return workingCondtion;
	}

	public void setWorkingCondtion(List<String> workingCondtion) {
		this.workingCondtion = workingCondtion;
	}

	public List<Map<String, Object>> getBaseVehicleName() {
		return baseVehicleName;
	}

	public void setBaseVehicleName(List<Map<String, Object>> baseVehicleName) {
		this.baseVehicleName = baseVehicleName;
	}

	public List<String> getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(List<String> customerSource) {
		this.customerSource = customerSource;
	}

	@Override
	public String toString() {
		return "EditCustomerInitData [objectProperty=" + objectProperty
				+ ", nation=" + nation + ", profession=" + profession
				+ ", position=" + position + ", vehicleTrademark="
				+ vehicleTrademark + ", objectNature=" + objectNature
				+ /*", workGroup=" + workGroup +*/ ", place=" + place
				+ ", vehicleStrain=" + vehicleStrain + ", certificateType="
				+ certificateType + ", vehicleColor=" + vehicleColor
				+ ", attentionEmphases=" + attentionEmphases + ", buyType="
				+ buyType + ", visitMode=" + visitMode + ", visitorMode="
				+ visitorMode + ", knowWay=" + knowWay + ", visitorLevel="
				+ visitorLevel + ", visitResult=" + visitResult + ", reason="
				+ reason + ", deliveryLocus=" + deliveryLocus
				+ ", purchaseUse=" + purchaseUse + ", distance=" + distance
				+ ", factLoad=" + factLoad + ", visitAddr=" + visitAddr
				+ ", backWay=" + backWay + ", purpose=" + purpose
				+ ", subjectMatter=" + subjectMatter + ", transportRoutes="
				+ transportRoutes + "]";
	}
	
	
}
