package cn.sf_soft.user.model;
/**
 * 申请认证的信息
 * @author king
 * @creation 2013-9-13
 */
public class ApplyForCertificationData {
	private String mobileKey;//手机凭证
	private String applyUser;//申请人
	private String stationId;//申请站点ID
	private String applyDept;//申请部门
	private String applyRemark;//备注
	
	
	public String getMobileKey() {
		return mobileKey;
	}
	public void setMobileKey(String mobileKey) {
		this.mobileKey = mobileKey;
	}
	public String getApplyUser() {
		return applyUser;
	}
	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getApplyDept() {
		return applyDept;
	}
	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}
	public String getApplyRemark() {
		return applyRemark;
	}
	public void setApplyRemark(String applyRemark) {
		this.applyRemark = applyRemark;
	}
	@Override
	public String toString() {
		return "ApplyForCertificationData [mobileKey=" + mobileKey
				+ ", applyUser=" + applyUser + ", stationId=" + stationId
				+ ", applyDept=" + applyDept + ", applyRemark=" + applyRemark
				+ "]";
	}
	
}
