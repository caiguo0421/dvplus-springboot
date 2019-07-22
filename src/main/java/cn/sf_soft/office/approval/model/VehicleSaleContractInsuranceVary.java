package cn.sf_soft.office.approval.model;

/**
 * 保险变更明细
 * @author caigx
 *
 */
public class VehicleSaleContractInsuranceVary implements java.io.Serializable {

	private static final long serialVersionUID = 8463586268829970092L;
	private String saleContractInsuranceVaryId;
	private String detailVaryId;
	private String saleContractInsuranceId;
	private String contractDetailId;
	private Integer insuranceYear;
	private String categoryId;
	private String categoryName;
	private String categoryType;
	private Double categoryIncome;
	private Double categoryScale;
	private Double categoryCost;
	private Short costStatus;
	private Short abortStatus;
	private String abortComment;
	private String remark;
	private String supplierId;
	private Integer purchaseSort;
	private Boolean isFree;
	private String contractNo;
	private Double oriCategoryIncome;

	// Constructors

	public VehicleSaleContractInsuranceVary() {
	}
	
	// Property accessors
	public String getSaleContractInsuranceVaryId() {
		return this.saleContractInsuranceVaryId;
	}

	public void setSaleContractInsuranceVaryId(
			String saleContractInsuranceVaryId) {
		this.saleContractInsuranceVaryId = saleContractInsuranceVaryId;
	}

	public String getDetailVaryId() {
		return this.detailVaryId;
	}

	public void setDetailVaryId(String detailVaryId) {
		this.detailVaryId = detailVaryId;
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

	public Double getCategoryIncome() {
		return this.categoryIncome;
	}

	public void setCategoryIncome(Double categoryIncome) {
		this.categoryIncome = categoryIncome;
	}

	public Double getCategoryScale() {
		return this.categoryScale;
	}

	public void setCategoryScale(Double categoryScale) {
		this.categoryScale = categoryScale;
	}

	public Double getCategoryCost() {
		return this.categoryCost;
	}

	public void setCategoryCost(Double categoryCost) {
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

	public String getAbortComment() {
		return this.abortComment;
	}

	public void setAbortComment(String abortComment) {
		this.abortComment = abortComment;
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

	public Double getOriCategoryIncome() {
		return this.oriCategoryIncome;
	}

	public void setOriCategoryIncome(Double oriCategoryIncome) {
		this.oriCategoryIncome = oriCategoryIncome;
	}

}
