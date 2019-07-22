package cn.sf_soft.vehicle.customer.model;
/**
 * 
 * @Title: 用户所关联的部门
 * @date 2013-12-11 上午11:14:32 
 * @author cw
 */
public class UserUnitRelation {
	
	private String userId;
	private String userName;
	private String userFullName;
	private String unidId;//部门id
	private String unitRelation;//关联部门
	
	public UserUnitRelation(){
		
	}

	public UserUnitRelation(String userId, String userName,
			String userFullName, String unidId, String unitRelation) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userFullName = userFullName;
		this.unidId = unidId;
		this.unitRelation = unitRelation;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUnidId() {
		return unidId;
	}

	public void setUnidId(String unidId) {
		this.unidId = unidId;
	}

	public String getUnitRelation() {
		return unitRelation;
	}

	public void setUnitRelation(String unitRelation) {
		this.unitRelation = unitRelation;
	}
	
	
	
	
}
