package cn.sf_soft.vehicle.customer.model;

import java.util.List;
import java.util.Map;
/**
 * 记录回访信息 页面  基础数据
 * @author Administrator
 *
 */
public class CallBackInitData {
	
	private List<BaseVehicleVisitorsBack> visitorLevel;
	private String[] backWay;//方式
	private String[] purpose;//目的
	private String[] reason;//成败原因
	private Map<String,String> map; //回访结果
	public CallBackInitData(){
		
	}
	public CallBackInitData(List<BaseVehicleVisitorsBack> visitorLevel,
			String[] backWay, String[] purpose,String[] reason,Map<String,String> map) {
		super();
		this.visitorLevel = visitorLevel;
		this.backWay = backWay;
		this.purpose = purpose;
		this.reason  = reason;
		this.map     = map;
	}


	public List<BaseVehicleVisitorsBack> getVisitorLevel() {
		return visitorLevel;
	}


	public void setVisitorLevel(List<BaseVehicleVisitorsBack> visitorLevel) {
		this.visitorLevel = visitorLevel;
	}


	public String[] getBackWay() {
		return backWay;
	}


	public void setBackWay(String[] backWay) {
		this.backWay = backWay;
	}


	public String[] getPurpose() {
		return purpose;
	}


	public void setPurpose(String[] purpose) {
		this.purpose = purpose;
	}
	public String[] getReason() {
		return reason;
	}
	public void setReason(String[] reason) {
		this.reason = reason;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	
	
}
