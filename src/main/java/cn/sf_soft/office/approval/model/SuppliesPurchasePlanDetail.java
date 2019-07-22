package cn.sf_soft.office.approval.model;

/**
 * SuppliesPurchasePlanDetail entity. @author MyEclipse Persistence Tools
 */

public class SuppliesPurchasePlanDetail implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1683168530064526058L;
	private String sppdId;
	private String documentNo;
	private String supplierName;
	private String suppliesNo;
	private String suppliesName;
	private String suppliesKindName;
	private String suppliesKindNo;
	private String suppliesModel;
	private String suppliesSpec;
	private Double planQuantity;
	private Double planPrice;
	private Double pisQuantity;
	private Double pisPrice;
	private String unit;
	private String DRemark;
	private Short DStatus;
	private String stopComment;
	private Double stopQuantity;
	private String stationId;
	private Short flag;
	private Double sabQuantity;
	private String purchaseNo;
	private String supplierId;
	private String supplierNo;

	private String stationName;
	private String dStatusMean;
	// Constructors

	/** default constructor */
	public SuppliesPurchasePlanDetail() {
	}

	/** minimal constructor */
	public SuppliesPurchasePlanDetail(String sppdId) {
		this.sppdId = sppdId;
	}

	/** full constructor */
	public SuppliesPurchasePlanDetail(String sppdId, String documentNo,
			String supplierName, String suppliesNo, String suppliesName,
			String suppliesKindName, String suppliesKindNo,
			String suppliesModel, String suppliesSpec, Double planQuantity,
			Double planPrice, Double pisQuantity, Double pisPrice, String unit,
			String DRemark, Short DStatus, String stopComment,
			Double stopQuantity, String stationId, Short flag,
			Double sabQuantity, String purchaseNo, String supplierId,
			String supplierNo) {
		this.sppdId = sppdId;
		this.documentNo = documentNo;
		this.supplierName = supplierName;
		this.suppliesNo = suppliesNo;
		this.suppliesName = suppliesName;
		this.suppliesKindName = suppliesKindName;
		this.suppliesKindNo = suppliesKindNo;
		this.suppliesModel = suppliesModel;
		this.suppliesSpec = suppliesSpec;
		this.planQuantity = planQuantity;
		this.planPrice = planPrice;
		this.pisQuantity = pisQuantity;
		this.pisPrice = pisPrice;
		this.unit = unit;
		this.DRemark = DRemark;
		this.DStatus = DStatus;
		this.stopComment = stopComment;
		this.stopQuantity = stopQuantity;
		this.stationId = stationId;
		this.flag = flag;
		this.sabQuantity = sabQuantity;
		this.purchaseNo = purchaseNo;
		this.supplierId = supplierId;
		this.supplierNo = supplierNo;
	}

	// Property accessors

	public String getSppdId() {
		return this.sppdId;
	}

	public void setSppdId(String sppdId) {
		this.sppdId = sppdId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSuppliesNo() {
		return this.suppliesNo;
	}

	public void setSuppliesNo(String suppliesNo) {
		this.suppliesNo = suppliesNo;
	}

	public String getSuppliesName() {
		return this.suppliesName;
	}

	public void setSuppliesName(String suppliesName) {
		this.suppliesName = suppliesName;
	}

	public String getSuppliesKindName() {
		return this.suppliesKindName;
	}

	public void setSuppliesKindName(String suppliesKindName) {
		this.suppliesKindName = suppliesKindName;
	}

	public String getSuppliesKindNo() {
		return this.suppliesKindNo;
	}

	public void setSuppliesKindNo(String suppliesKindNo) {
		this.suppliesKindNo = suppliesKindNo;
	}

	public String getSuppliesModel() {
		return this.suppliesModel;
	}

	public void setSuppliesModel(String suppliesModel) {
		this.suppliesModel = suppliesModel;
	}

	public String getSuppliesSpec() {
		return this.suppliesSpec;
	}

	public void setSuppliesSpec(String suppliesSpec) {
		this.suppliesSpec = suppliesSpec;
	}

	public Double getPlanQuantity() {
		return this.planQuantity;
	}

	public void setPlanQuantity(Double planQuantity) {
		this.planQuantity = planQuantity;
	}

	public Double getPlanPrice() {
		return this.planPrice;
	}

	public void setPlanPrice(Double planPrice) {
		this.planPrice = planPrice;
	}

	public Double getPisQuantity() {
		return this.pisQuantity;
	}

	public void setPisQuantity(Double pisQuantity) {
		this.pisQuantity = pisQuantity;
	}

	public Double getPisPrice() {
		return this.pisPrice;
	}

	public void setPisPrice(Double pisPrice) {
		this.pisPrice = pisPrice;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDRemark() {
		return this.DRemark;
	}

	public void setDRemark(String DRemark) {
		this.DRemark = DRemark;
	}

	public Short getDStatus() {
		return this.DStatus;
	}

	public void setDStatus(Short DStatus) {
		this.DStatus = DStatus;
	}

	public String getStopComment() {
		return this.stopComment;
	}

	public void setStopComment(String stopComment) {
		this.stopComment = stopComment;
	}

	public Double getStopQuantity() {
		return this.stopQuantity;
	}

	public void setStopQuantity(Double stopQuantity) {
		this.stopQuantity = stopQuantity;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Short getFlag() {
		return this.flag;
	}

	public void setFlag(Short flag) {
		this.flag = flag;
	}

	public Double getSabQuantity() {
		return this.sabQuantity;
	}

	public void setSabQuantity(Double sabQuantity) {
		this.sabQuantity = sabQuantity;
	}

	public String getPurchaseNo() {
		return this.purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
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

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getdStatusMean() {
		return dStatusMean;
	}

	public void setdStatusMean(String dStatusMean) {
		this.dStatusMean = dStatusMean;
	}

}
