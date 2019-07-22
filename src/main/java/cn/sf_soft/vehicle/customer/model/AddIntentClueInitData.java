package cn.sf_soft.vehicle.customer.model;

import java.util.List;
import java.util.Map;

import cn.sf_soft.basedata.model.BaseOthers;
/**
 * 新建意向线索中的初始化数据
 * @Title: AddIntentClueInitData.java
 * @date 2014-1-7 下午04:03:23 
 * @author cw
 */
public class AddIntentClueInitData {

	private List<BaseOthers> vehicleColor;//车辆颜色
	private List<VehicleType> vehicleName;//车辆名称
	private List<String> attentionEmphases;//关注重点
	private Map<String,Short> buyType; //购买方式
	private List<String> visitMode;//拜访方式
	private List<String> visitorMode;//来访方式
	private List<String> knowWay;//了解渠道 
	private List<BaseVehicleVisitorsBack> visitorLevel; //成交机会
	private Map<String,Short> visitResult; //跟踪结果
	private List<String> reason;//成败原因
	private List<BaseOthers> deliveryLocus;//销售网点
	
	
	private List<BaseOthers> purchaseUse; //购车用途
	private List<String> distance;//运输距离
	private List<String> tonnage;//上牌吨位
	private List<String> factLoad;//实际载重
	private Map<String,String> visitAddr;//接触地点
	 
	private List<String> backWay;//方式
	private List<String> purpose;//目的
	
	public AddIntentClueInitData(){
		
	}
	
	
	public List<BaseOthers> getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(List<BaseOthers> vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public List<VehicleType> getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(List<VehicleType> vehicleName) {
		this.vehicleName = vehicleName;
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
	public List<String> getKnowWay() {
		return knowWay;
	}
	public void setKnowWay(List<String> knowWay) {
		this.knowWay = knowWay;
	}

	public List<BaseVehicleVisitorsBack> getVisitorLevel() {
		return visitorLevel;
	}

	public void setVisitorLevel(List<BaseVehicleVisitorsBack> visitorLevel) {
		this.visitorLevel = visitorLevel;
	}

	public List<String> getVisitorMode() {
		return visitorMode;
	}

	public void setVisitorMode(List<String> visitorMode) {
		this.visitorMode = visitorMode;
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


	public List<String> getTonnage() {
		return tonnage;
	}


	public void setTonnage(List<String> tonnage) {
		this.tonnage = tonnage;
	}


	public List<String> getFactLoad() {
		return factLoad;
	}


	public void setFactLoad(List<String> factLoad) {
		this.factLoad = factLoad;
	}


	public Map<String, String> getVisitAddr() {
		return visitAddr;
	}


	public void setVisitAddr(Map<String, String> visitAddr) {
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
	
	
}
