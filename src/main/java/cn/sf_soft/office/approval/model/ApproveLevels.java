package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;
import java.util.Set;

/**
 * BaseApproveLevels entity. @author MyEclipse Persistence Tools
 */

public class ApproveLevels implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7344701796067788526L;
	private String levelId;
	private String moduleId;
	private String stationId;
	private Integer approveLevel;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private Set<ApproveLevelsPoints> approvelPoint;

	// Constructors

	/** default constructor */
	public ApproveLevels() {
	}

	/** minimal constructor */
	public ApproveLevels(String moduleId, String stationId,
			Integer approveLevel) {
		this.moduleId = moduleId;
		this.stationId = stationId;
		this.approveLevel = approveLevel;
	}

	/** full constructor */
	public ApproveLevels(String moduleId, String stationId,
			Integer approveLevel, String creator, Timestamp createTime,
			String modifier, Timestamp modifyTime) {
		this.moduleId = moduleId;
		this.stationId = stationId;
		this.approveLevel = approveLevel;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
	}

	// Property accessors

	public String getLevelId() {
		return this.levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Integer getApproveLevel() {
		return this.approveLevel;
	}

	public void setApproveLevel(Integer approveLevel) {
		this.approveLevel = approveLevel;
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

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public void setApprovelPoint(Set<ApproveLevelsPoints> approvelPoint) {
		this.approvelPoint = approvelPoint;
	}

	public Set<ApproveLevelsPoints> getApprovelPoint() {
		return approvelPoint;
	}

}
