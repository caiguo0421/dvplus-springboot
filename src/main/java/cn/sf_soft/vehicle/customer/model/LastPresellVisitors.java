package cn.sf_soft.vehicle.customer.model;
/**
 * 上一条销售线索
 * @Title: LastPresellVisitors.java
 * @date 2014-1-21 下午06:22:46 
 * @author cw
 */
public class LastPresellVisitors {
	private String visitorNo;
	//private Short clueType;
	private Short visitResult;
	
	public LastPresellVisitors(){
		
	}
	public LastPresellVisitors(String visitorNo,
			Short visitResult) {
		super();
		this.visitorNo = visitorNo;
		this.visitResult = visitResult;
	}
	public String getVisitorNo() {
		return visitorNo;
	}
	public void setVisitorNo(String visitorNo) {
		this.visitorNo = visitorNo;
	}
	
	public Short getVisitResult() {
		return visitResult;
	}
	public void setVisitResult(Short visitResult) {
		this.visitResult = visitResult;
	}
	
	
}
