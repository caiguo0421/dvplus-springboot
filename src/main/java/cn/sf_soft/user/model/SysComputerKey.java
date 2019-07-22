package cn.sf_soft.user.model;

import java.sql.Timestamp;

/**
 * SysComputerKey entity. @author MyEclipse Persistence Tools
 */

public class SysComputerKey implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2060572919565171574L;
	private String cpkeyId;
	private String computerKey;
	private String computerType;
	private String stationId;
	private String userName;
	private String creator;
	private Timestamp createTime;
	private String unitName;
	private String remark;
	private Short approveStatus;
	private String approver;
	private Timestamp approveTime;
	private String approvePostil;

	// Constructors

	/** default constructor */
	public SysComputerKey() {
	}

	/** minimal constructor */
	public SysComputerKey(String cpkeyId) {
		this.cpkeyId = cpkeyId;
	}

	/** full constructor */
	public SysComputerKey(String cpkeyId, String computerKey,
			String computerType, String stationId, String creator,
			Timestamp createTime, String unitName, String remark,
			Short approveStatus, String approver, Timestamp approveTime,
			String approvePostil) {
		this.cpkeyId = cpkeyId;
		this.computerKey = computerKey;
		this.computerType = computerType;
		this.stationId = stationId;
		this.creator = creator;
		this.createTime = createTime;
		this.unitName = unitName;
		this.remark = remark;
		this.approveStatus = approveStatus;
		this.approver = approver;
		this.approveTime = approveTime;
		this.approvePostil = approvePostil;
	}

	// Property accessors

	public String getCpkeyId() {
		return this.cpkeyId;
	}

	public void setCpkeyId(String cpkeyId) {
		this.cpkeyId = cpkeyId;
	}

	public String getComputerKey() {
		return this.computerKey;
	}

	public void setComputerKey(String computerKey) {
		this.computerKey = computerKey;
	}

	public String getComputerType() {
		return this.computerType;
	}

	public void setComputerType(String computerType) {
		this.computerType = computerType;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getApproveStatus() {
		return this.approveStatus;
	}

	public void setApproveStatus(Short approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getApprover() {
		return this.approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public String getApprovePostil() {
		return this.approvePostil;
	}

	public void setApprovePostil(String approvePostil) {
		this.approvePostil = approvePostil;
	}

	// add by shichunshan 记录日志用 fix多次认证的问题
	@Override
	public String toString() {
		return "SysComputerKey [computerKey=" + computerKey + ", computerType="
				+ computerType + ", createTime=" + createTime + ", unitName="
				+ unitName + ", approveStatus=" + approveStatus + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
