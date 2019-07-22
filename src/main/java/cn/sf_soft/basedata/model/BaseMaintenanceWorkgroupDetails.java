package cn.sf_soft.basedata.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseMaintenanceWorkgroupDetails entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_maintenance_workgroup_details", schema = "dbo")
public class BaseMaintenanceWorkgroupDetails implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5258924871567330528L;
	private String bmwdId;
	private String userId;
	private String bmwId;
	private Boolean isGroupLeader;
	private String remark;

	// Constructors

	/** default constructor */
	public BaseMaintenanceWorkgroupDetails() {
	}

	/** minimal constructor */
	public BaseMaintenanceWorkgroupDetails(String bmwdId) {
		this.bmwdId = bmwdId;
	}

	/** full constructor */
	public BaseMaintenanceWorkgroupDetails(String bmwdId, String userId,
			String bmwId, Boolean isGroupLeader, String remark) {
		this.bmwdId = bmwdId;
		this.userId = userId;
		this.bmwId = bmwId;
		this.isGroupLeader = isGroupLeader;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "bmwd_id", unique = true, nullable = false, length = 40)
	public String getBmwdId() {
		return this.bmwdId;
	}

	public void setBmwdId(String bmwdId) {
		this.bmwdId = bmwdId;
	}

	@Column(name = "user_id", length = 40)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "bmw_id", length = 40)
	public String getBmwId() {
		return this.bmwId;
	}

	public void setBmwId(String bmwId) {
		this.bmwId = bmwId;
	}

	@Column(name = "is_group_leader")
	public Boolean getIsGroupLeader() {
		return this.isGroupLeader;
	}

	public void setIsGroupLeader(Boolean isGroupLeader) {
		this.isGroupLeader = isGroupLeader;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
