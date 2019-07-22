package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 车辆加装、拆装明细
 * 
 * @author caigx
 *
 */
public class VehicleSuperstructureRemoveAndInstalls implements java.io.Serializable {

	private static final long serialVersionUID = 3170371274412134198L;
	private String sriId;
	private String conversionNo;
	private String itemId;
	private Short itemType;
	private String installType;
	private String itemNo;
	private String itemName;
	private Double itemCost;
	private Integer quantity;
	private Integer outInQuantity;
	private Short status;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String auditor;
	private Timestamp auditTime;
	
	private String itemTypeMeaning;//加装/拆装 类型中文


	public VehicleSuperstructureRemoveAndInstalls() {
	}


	public VehicleSuperstructureRemoveAndInstalls(String sriId, String conversionNo, String itemId, Short itemType,
			String installType, String itemNo, String itemName, Double itemCost, Integer quantity,
			Integer outInQuantity, Short status, String remark, String creator, Timestamp createTime, String modifier,
			Timestamp modifyTime, String auditor, Timestamp auditTime) {
		this.sriId = sriId;
		this.conversionNo = conversionNo;
		this.itemId = itemId;
		this.itemType = itemType;
		this.installType = installType;
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.itemCost = itemCost;
		this.quantity = quantity;
		this.outInQuantity = outInQuantity;
		this.status = status;
		this.remark = remark;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.auditor = auditor;
		this.auditTime = auditTime;
	}

	// Property accessors

	public String getSriId() {
		return this.sriId;
	}

	public void setSriId(String sriId) {
		this.sriId = sriId;
	}

	public String getConversionNo() {
		return this.conversionNo;
	}

	public void setConversionNo(String conversionNo) {
		this.conversionNo = conversionNo;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Short getItemType() {
		return this.itemType;
	}

	public void setItemType(Short itemType) {
		this.itemType = itemType;
	}

	public String getInstallType() {
		return this.installType;
	}

	public void setInstallType(String installType) {
		this.installType = installType;
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

	public Double getItemCost() {
		return this.itemCost;
	}

	public void setItemCost(Double itemCost) {
		this.itemCost = itemCost;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getOutInQuantity() {
		return this.outInQuantity;
	}

	public void setOutInQuantity(Integer outInQuantity) {
		this.outInQuantity = outInQuantity;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
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

	public String getAuditor() {
		return this.auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public Timestamp getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}


	public String getItemTypeMeaning() {
		return itemTypeMeaning;
	}


	public void setItemTypeMeaning(String itemTypeMeaning) {
		this.itemTypeMeaning = itemTypeMeaning;
	}

}
