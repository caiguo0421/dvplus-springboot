package cn.sf_soft.basedata.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseMaintenanceWorkgroups entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_maintenance_workgroups", schema = "dbo")
public class BaseMaintenanceWorkgroups implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4036858407714899045L;
	// Fields

	private String bmwId;
	private String bmwName;
	private String principalId;
	private String principal;
	private String stationId;
	private String remark;

	// Constructors

	/** default constructor */
	public BaseMaintenanceWorkgroups() {
	}

	/** minimal constructor */
	public BaseMaintenanceWorkgroups(String bmwId, String principalId,
			String principal) {
		this.bmwId = bmwId;
		this.principalId = principalId;
		this.principal = principal;
	}

	/** full constructor */
	public BaseMaintenanceWorkgroups(String bmwId, String bmwName,
			String principalId, String principal, String stationId,
			String remark) {
		this.bmwId = bmwId;
		this.bmwName = bmwName;
		this.principalId = principalId;
		this.principal = principal;
		this.stationId = stationId;
		this.remark = remark;
	}

	// Property accessors
	@Id
	@Column(name = "bmw_id", unique = true, nullable = false, length = 40)
	public String getBmwId() {
		return this.bmwId;
	}

	public void setBmwId(String bmwId) {
		this.bmwId = bmwId;
	}

	@Column(name = "bmw_name", length = 20)
	public String getBmwName() {
		return this.bmwName;
	}

	public void setBmwName(String bmwName) {
		this.bmwName = bmwName;
	}

	@Column(name = "principal_id", nullable = false, length = 40)
	public String getPrincipalId() {
		return this.principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	@Column(name = "principal", nullable = false, length = 20)
	public String getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	@Column(name = "station_id", length = 10)
	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "BaseMaintenanceWorkgroups [bmwId=" + bmwId + ", bmwName="
				+ bmwName + ", principalId=" + principalId + ", principal="
				+ principal + ", stationId=" + stationId + ", remark=" + remark
				+ "]";
	}

}
