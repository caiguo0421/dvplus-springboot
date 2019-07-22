package cn.sf_soft.basedata.model;

import java.sql.Timestamp;

/**
 * SysLogs entity. @author MyEclipse Persistence Tools
 */

public class SysLogs implements java.io.Serializable {

	// Fields

	private String logId;
	private String stationId;
	private String logType;
	private Timestamp occurTime;
	private String module;
	private String sysUser;
	private String computer;
	private String description;

	// Constructors

	/** default constructor */
	public SysLogs() {
	}

	/** minimal constructor */
	public SysLogs(String logId, String stationId, String logType,
			Timestamp occurTime, String module, String sysUser, String computer) {
		this.logId = logId;
		this.stationId = stationId;
		this.logType = logType;
		this.occurTime = occurTime;
		this.module = module;
		this.sysUser = sysUser;
		this.computer = computer;
	}

	/** full constructor */
	public SysLogs(String logId, String stationId, String logType,
			Timestamp occurTime, String module, String sysUser,
			String computer, String description) {
		this.logId = logId;
		this.stationId = stationId;
		this.logType = logType;
		this.occurTime = occurTime;
		this.module = module;
		this.sysUser = sysUser;
		this.computer = computer;
		this.description = description;
	}

	// Property accessors

	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public Timestamp getOccurTime() {
		return this.occurTime;
	}

	public void setOccurTime(Timestamp occurTime) {
		this.occurTime = occurTime;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(String sysUser) {
		this.sysUser = sysUser;
	}

	public String getComputer() {
		return this.computer;
	}

	public void setComputer(String computer) {
		this.computer = computer;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
