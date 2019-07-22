package cn.sf_soft.vehicle.customer.model;

public class PresellVisitorsForCheck {
	private String visitorNo;
	private String visitResult;
	private String sellerId;
	//private String clueType;
	public PresellVisitorsForCheck(){
		
	}
	
	public PresellVisitorsForCheck(String visitorNo, String visitResult,
			String sellerId) {
		super();
		this.visitorNo = visitorNo;
		this.visitResult = visitResult;
		this.sellerId = sellerId;
	}
	public String getVisitorNo() {
		return visitorNo;
	}
	public void setVisitorNo(String visitorNo) {
		this.visitorNo = visitorNo;
	}
	public String getVisitResult() {
		return visitResult;
	}
	public void setVisitResult(String visitResult) {
		this.visitResult = visitResult;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	/*public String getClueType() {
		return clueType;
	}
	public void setClueType(String clueType) {
		this.clueType = clueType;
	}*/
	
}
