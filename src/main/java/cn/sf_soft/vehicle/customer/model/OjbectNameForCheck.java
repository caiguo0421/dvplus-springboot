package cn.sf_soft.vehicle.customer.model;

public class OjbectNameForCheck {

	private String objectId;
	private String objectName;
	private Short objectNature;
	private String mobile;
	private String certificateNo;//证件号
	private String shortName;//简称
	public OjbectNameForCheck(){
		
	}
	public OjbectNameForCheck(String objectId, String objectName,Short objectNature,
			String mobile,String certificateNo,String shortName) {
		super();
		this.objectId = objectId;
		this.objectName = objectName;
		this.objectNature = objectNature;
		this.mobile = mobile;
		this.certificateNo = certificateNo;
		this.shortName =shortName;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public Short getObjectNature() {
		return objectNature;
	}
	public void setObjectNature(Short objectNature) {
		this.objectNature = objectNature;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
