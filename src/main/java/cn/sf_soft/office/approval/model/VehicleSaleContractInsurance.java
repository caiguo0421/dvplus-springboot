package cn.sf_soft.office.approval.model;

import java.math.BigDecimal;

/**
 * 车辆销售合同-保险
 * 
 * @author caigx
 *
 */
public class VehicleSaleContractInsurance implements java.io.Serializable {

	private static final long serialVersionUID = -1996956844550417690L;
	private String saleContractInsuranceId;
	private String contractDetailId;
	private Integer insuranceYear;
	private String categoryId;
	private String categoryName;
	private String categoryType;
	private BigDecimal categoryIncome = BigDecimal.ZERO;
	private BigDecimal categoryScale = BigDecimal.ZERO;
	private BigDecimal categoryCost = BigDecimal.ZERO;
	private Short costStatus;
	private Short abortStatus;
	private String remark;
	private String supplierId;
	private Integer purchaseSort;
	private Boolean isFree;
	private String contractNo;
	private String insuranceGroupId;
	private String supplierName;//保险公司名称
	private BigDecimal taxRate = BigDecimal.ZERO;
	private BigDecimal rebateRatio = BigDecimal.ZERO;
	private BigDecimal rebateAmount = BigDecimal.ZERO;


	public VehicleSaleContractInsurance() {
	}



	public String getSaleContractInsuranceId() {
		return this.saleContractInsuranceId;
	}

	public void setSaleContractInsuranceId(String saleContractInsuranceId) {
		this.saleContractInsuranceId = saleContractInsuranceId;
	}

	public String getContractDetailId() {
		return this.contractDetailId;
	}

	public void setContractDetailId(String contractDetailId) {
		this.contractDetailId = contractDetailId;
	}

	public Integer getInsuranceYear() {
		return this.insuranceYear;
	}

	public void setInsuranceYear(Integer insuranceYear) {
		this.insuranceYear = insuranceYear;
	}

	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryType() {
		return this.categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public BigDecimal getCategoryIncome() {
		return this.categoryIncome;
	}

	public void setCategoryIncome(BigDecimal categoryIncome) {
		this.categoryIncome = categoryIncome;
	}

	public BigDecimal getCategoryScale() {
		return this.categoryScale;
	}

	public void setCategoryScale(BigDecimal categoryScale) {
		this.categoryScale = categoryScale;
	}

	public BigDecimal getCategoryCost() {
		return this.categoryCost;
	}

	public void setCategoryCost(BigDecimal categoryCost) {
		this.categoryCost = categoryCost;
	}

	public Short getCostStatus() {
		return this.costStatus;
	}

	public void setCostStatus(Short costStatus) {
		this.costStatus = costStatus;
	}

	public Short getAbortStatus() {
		return this.abortStatus;
	}

	public void setAbortStatus(Short abortStatus) {
		this.abortStatus = abortStatus;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getPurchaseSort() {
		return this.purchaseSort;
	}

	public void setPurchaseSort(Integer purchaseSort) {
		this.purchaseSort = purchaseSort;
	}

	public Boolean getIsFree() {
		return this.isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getInsuranceGroupId() {
		return insuranceGroupId;
	}

	public void setInsuranceGroupId(String insuranceGroupId) {
		this.insuranceGroupId = insuranceGroupId;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getRebateRatio() {
		return rebateRatio;
	}

	public void setRebateRatio(BigDecimal rebateRatio) {
		this.rebateRatio = rebateRatio;
	}

	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}
}
