package cn.sf_soft.vehicle.customer.model;

/**
 * 用于查询是否多线索跟进
 * SysOptions entity. @author MyEclipse Persistence Tools
 */

public class SysOptions implements java.io.Serializable {

	// Fields

	private String optionId;
	private String stationId;
	private String optionNo;
	private String optionName;
	private String optionType;
	private Short dataType;
	private String optionValue;
	private String valueItems;
	private String remark;

	// Constructors

	/** default constructor */
	public SysOptions() {
	}

	/** minimal constructor */
	public SysOptions(String optionId, String optionNo, String optionName,
			String optionType, Short dataType) {
		this.optionId = optionId;
		this.optionNo = optionNo;
		this.optionName = optionName;
		this.optionType = optionType;
		this.dataType = dataType;
	}

	/** full constructor */
	public SysOptions(String optionId, String stationId, String optionNo,
			String optionName, String optionType, Short dataType,
			String optionValue, String valueItems, String remark) {
		this.optionId = optionId;
		this.stationId = stationId;
		this.optionNo = optionNo;
		this.optionName = optionName;
		this.optionType = optionType;
		this.dataType = dataType;
		this.optionValue = optionValue;
		this.valueItems = valueItems;
		this.remark = remark;
	}

	// Property accessors

	public String getOptionId() {
		return this.optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getOptionNo() {
		return this.optionNo;
	}

	public void setOptionNo(String optionNo) {
		this.optionNo = optionNo;
	}

	public String getOptionName() {
		return this.optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionType() {
		return this.optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public Short getDataType() {
		return this.dataType;
	}

	public void setDataType(Short dataType) {
		this.dataType = dataType;
	}

	public String getOptionValue() {
		return this.optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public String getValueItems() {
		return this.valueItems;
	}

	public void setValueItems(String valueItems) {
		this.valueItems = valueItems;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
