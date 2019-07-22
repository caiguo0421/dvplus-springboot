package cn.sf_soft.vehicle.customer.model;
/**
 * 
 * @author cw
 * @date 2014-9-11 下午4:42:45
 */
public class UserMaintenanceWorkgroups {
	private String bmwId;
	private String bmwName;
	private String principal;
	
	public UserMaintenanceWorkgroups(){
		
	}
	
	public UserMaintenanceWorkgroups(String bmwId, String bmwName,
			String principal) {
		super();
		this.bmwId = bmwId;
		this.bmwName = bmwName;
		this.principal = principal;
	}
	public String getBmwId() {
		return bmwId;
	}
	public void setBmwId(String bmwId) {
		this.bmwId = bmwId;
	}
	public String getBmwName() {
		return bmwName;
	}
	public void setBmwName(String bmwName) {
		this.bmwName = bmwName;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	
	
}
