package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * BaseSalaryItems entity. @author MyEclipse Persistence Tools
 */

public class BaseSalaryItems implements java.io.Serializable {

	// Fields

	private String itemId;
	private String stationId;
	private Short status;
	private String itemNo;
	private String itemName;
	private Boolean isPositive;
	private String itemField;
	private Short itemType;
	private String objectId;
	private String expenseId;
	private String departmentId;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;

	// Constructors

	/** default constructor */
	public BaseSalaryItems() {
	}

	/** minimal constructor */
	public BaseSalaryItems(String itemId, String stationId, Short status,
			String itemNo, String itemName, Boolean isPositive,
			String itemField, Short itemType) {
		this.itemId = itemId;
		this.stationId = stationId;
		this.status = status;
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.isPositive = isPositive;
		this.itemField = itemField;
		this.itemType = itemType;
	}

	/** full constructor */
	public BaseSalaryItems(String itemId, String stationId, Short status,
			String itemNo, String itemName, Boolean isPositive,
			String itemField, Short itemType, String objectId,
			String expenseId, String departmentId, String remark,
			String creator, Timestamp createTime, String modifier,
			Timestamp modifyTime) {
		this.itemId = itemId;
		this.stationId = stationId;
		this.status = status;
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.isPositive = isPositive;
		this.itemField = itemField;
		this.itemType = itemType;
		this.objectId = objectId;
		this.expenseId = expenseId;
		this.departmentId = departmentId;
		this.remark = remark;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
	}

	// Property accessors

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Boolean getIsPositive() {
		return this.isPositive;
	}

	public void setIsPositive(Boolean isPositive) {
		this.isPositive = isPositive;
	}

	public String getItemField() {
		return this.itemField;
	}

	public void setItemField(String itemField) {
		this.itemField = itemField;
	}

	public Short getItemType() {
		return this.itemType;
	}

	public void setItemType(Short itemType) {
		this.itemType = itemType;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getExpenseId() {
		return this.expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
