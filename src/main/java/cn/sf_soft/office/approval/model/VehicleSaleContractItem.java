package cn.sf_soft.office.approval.model;

import java.math.BigDecimal;

/**
 * 车辆销售合同-改装
 * @author caigx
 *
 */
public class VehicleSaleContractItem implements java.io.Serializable {

	private static final long serialVersionUID = 2868614105553462293L;
	private String saleContractItemId;
	private String contractDetailId;
	private String itemId;
	private String itemNo;
	private String itemName;
	private String supplierId;
	private String supplierNo;
	private String supplierName;
	private BigDecimal itemCost = BigDecimal.ZERO;
	private String comment;
	private Short abortStatus;
	private BigDecimal income = BigDecimal.ZERO;
	private String contractNo;
	private String itemGroupId;

	public VehicleSaleContractItem() {
	}

	// Property accessors

	public String getSaleContractItemId() {
		return this.saleContractItemId;
	}

	public void setSaleContractItemId(String saleContractItemId) {
		this.saleContractItemId = saleContractItemId;
	}

	public String getContractDetailId() {
		return this.contractDetailId;
	}

	public void setContractDetailId(String contractDetailId) {
		this.contractDetailId = contractDetailId;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
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

	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierNo() {
		return this.supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public BigDecimal getItemCost() {
		return this.itemCost;
	}

	public void setItemCost(BigDecimal itemCost) {
		this.itemCost = itemCost;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Short getAbortStatus() {
		return this.abortStatus;
	}

	public void setAbortStatus(Short abortStatus) {
		this.abortStatus = abortStatus;
	}

	public BigDecimal getIncome() {
		return this.income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getItemGroupId() {
		return itemGroupId;
	}

	public void setItemGroupId(String itemGroupId) {
		this.itemGroupId = itemGroupId;
	}
}
