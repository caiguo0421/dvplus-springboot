package cn.sf_soft.basedata.model;

import java.sql.Timestamp;

/**
 * SysCodeRules entity. @author MyEclipse Persistence Tools
 */

public class SysCodeRules implements java.io.Serializable {

	// Fields

	private String ruleId;
	private String stationId;
	private String ruleNo;
	private String ruleName;
	private String prefix;
	private Integer serialNumber;
	private Short numberWidth;
	private Short resetMode;
	private Boolean yearFormat;
	private Timestamp assignTime;
	private String sample;
	private String remark;

	// Constructors

	/** default constructor */
	public SysCodeRules() {
	}

	/** minimal constructor */
	public SysCodeRules(String ruleId, String stationId, String ruleNo,
			String ruleName, Integer serialNumber, Short numberWidth,
			Short resetMode, Boolean yearFormat, Timestamp assignTime) {
		this.ruleId = ruleId;
		this.stationId = stationId;
		this.ruleNo = ruleNo;
		this.ruleName = ruleName;
		this.serialNumber = serialNumber;
		this.numberWidth = numberWidth;
		this.resetMode = resetMode;
		this.yearFormat = yearFormat;
		this.assignTime = assignTime;
	}

	/** full constructor */
	public SysCodeRules(String ruleId, String stationId, String ruleNo,
			String ruleName, String prefix, Integer serialNumber,
			Short numberWidth, Short resetMode, Boolean yearFormat,
			Timestamp assignTime, String sample, String remark) {
		this.ruleId = ruleId;
		this.stationId = stationId;
		this.ruleNo = ruleNo;
		this.ruleName = ruleName;
		this.prefix = prefix;
		this.serialNumber = serialNumber;
		this.numberWidth = numberWidth;
		this.resetMode = resetMode;
		this.yearFormat = yearFormat;
		this.assignTime = assignTime;
		this.sample = sample;
		this.remark = remark;
	}

	// Property accessors

	public String getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getRuleNo() {
		return this.ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Short getNumberWidth() {
		return this.numberWidth;
	}

	public void setNumberWidth(Short numberWidth) {
		this.numberWidth = numberWidth;
	}

	public Short getResetMode() {
		return this.resetMode;
	}

	public void setResetMode(Short resetMode) {
		this.resetMode = resetMode;
	}

	public Boolean getYearFormat() {
		return this.yearFormat;
	}

	public void setYearFormat(Boolean yearFormat) {
		this.yearFormat = yearFormat;
	}

	public Timestamp getAssignTime() {
		return this.assignTime;
	}

	public void setAssignTime(Timestamp assignTime) {
		this.assignTime = assignTime;
	}

	public String getSample() {
		return this.sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
