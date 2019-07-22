package cn.sf_soft.vehicle.customer.model;

import java.util.List;
import java.util.Map;

import cn.sf_soft.basedata.model.BaseOthers;

/**
 * 
 * @Title: 新建有效线索的基础数据
 * @date 2014-1-2 上午10:17:07 
 * @author cw
 */
public class AddValidClueInitData {

		private List<BaseOthers> purchaseUse; //购车用途
		private String[] distance;//运输距离
		private String[] tonnage;//上牌吨位
		private String[] factLoad;//实际载重
		private Map<String,String> visitAddr;//接触地点
		
		private String[] backWay;//方式
		private String[] purpose;//目的
		public AddValidClueInitData(){
			
		}
		
		public AddValidClueInitData(List<BaseOthers> purchaseUse, String[] distance,
				String[] tonnage, String[] factLoad,
				Map<String, String> visitAddr,String[] backWay,String[] purpose) {
			super();
			this.purchaseUse = purchaseUse;
			this.distance = distance;
			this.tonnage = tonnage;
			this.factLoad = factLoad;
			this.visitAddr = visitAddr;
			this.backWay = backWay;
			this.purpose = purpose;
		}
		
		public List<BaseOthers> getPurchaseUse() {
			return purchaseUse;
		}

		public void setPurchaseUse(List<BaseOthers> purchaseUse) {
			this.purchaseUse = purchaseUse;
		}

		public String[] getDistance() {
			return distance;
		}
		public void setDistance(String[] distance) {
			this.distance = distance;
		}
		public String[] getTonnage() {
			return tonnage;
		}
		public void setTonnage(String[] tonnage) {
			this.tonnage = tonnage;
		}
		public String[] getFactLoad() {
			return factLoad;
		}
		public void setFactLoad(String[] factLoad) {
			this.factLoad = factLoad;
		}
		public Map<String, String> getVisitAddr() {
			return visitAddr;
		}
		public void setVisitAddr(Map<String, String> visitAddr) {
			this.visitAddr = visitAddr;
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
		
		
		
}
