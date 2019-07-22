package cn.sf_soft.user.model;

import java.util.Set;


/**
 * SysRoles entity. @author MyEclipse Persistence Tools
 */

public class SysRoles implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7495417827495432948L;
	private String roleId;
	private String roleName;
	private String roleType;
	
//	private String remark;
//	private String creator;
//	private Timestamp createTime;
//	private String modifier;
//	private Timestamp modifyTime;

	
	private Set<SysPopedoms> popedoms;
	// Constructors

	/** default constructor */
	public SysRoles() {
	}

	/** minimal constructor */
	public SysRoles(String roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}


	// Property accessors

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleType() {
		return this.roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public void setPopedoms(Set<SysPopedoms> popedoms) {
		this.popedoms = popedoms;
	}

	public Set<SysPopedoms> getPopedoms() {
		return popedoms;
	}

	@Override
	public String toString() {
		return "SysRoles ["
				+ /*", popedoms=" + popedoms +*/ ", roleId="
				+ roleId + ", roleName=" + roleName + ", roleType=" + roleType
				+ "]";
	}

}
