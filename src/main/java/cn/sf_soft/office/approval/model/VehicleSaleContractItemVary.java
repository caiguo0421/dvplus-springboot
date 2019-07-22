package cn.sf_soft.office.approval.model;

/**
 * 预估改装项目变更表
 * @author caigx
 *
 */
public class VehicleSaleContractItemVary implements java.io.Serializable {

	private static final long serialVersionUID = 307998395993037650L;
	private String saleContractItemVaryId;
	private String detailVaryId;
	private String saleContractItemId;
	private String contractDetailId;
	private String itemId;
	private String itemNo;
	private String itemName;
	private String supplierId;
	private String supplierNo;
	private String supplierName;
	private Double itemCost;
	private String comment;
	private Short abortStatus;
	private String abortComment;
	private Double income;
	private String contractNo;
	private Double oriItemCost;
	private Double oriIncome;

	// Constructors

	public VehicleSaleContractItemVary() {
	}

	
	// Property accessors

	public String getSaleContractItemVaryId() {
		return this.saleContractItemVaryId;
	}

	public void setSaleContractItemVaryId(String saleContractItemVaryId) {
		this.saleContractItemVaryId = saleContractItemVaryId;
	}

	public String getDetailVaryId() {
		return this.detailVaryId;
	}

	public void setDetailVaryId(String detailVaryId) {
		this.detailVaryId = detailVaryId;
	}

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

	public Double getItemCost() {
		return this.itemCost;
	}

	public void setItemCost(Double itemCost) {
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

	public String getAbortComment() {
		return this.abortComment;
	}

	public void setAbortComment(String abortComment) {
		this.abortComment = abortComment;
	}

	public Double getIncome() {
		return this.income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Double getOriItemCost() {
		return this.oriItemCost;
	}

	public void setOriItemCost(Double oriItemCost) {
		this.oriItemCost = oriItemCost;
	}

	public Double getOriIncome() {
		return this.oriIncome;
	}

	public void setOriIncome(Double oriIncome) {
		this.oriIncome = oriIncome;
	}

}
