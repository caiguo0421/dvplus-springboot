package cn.sf_soft.office.approval.model;

/**
 * ToolsPurchasePlanDetail entity. @author MyEclipse Persistence Tools
 */

public class ToolsPurchasePlanDetail implements java.io.Serializable {

	// Fields

	private String sppdId;
	private String documentNo;
	private String toolsNo;
	private String toolsName;
	private String toolsKindName;
	private String toolsKindNo;
	private String toolsModel;
	private String toolsSpec;
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
	private String supplierName;

	private String stationName;
	private String dStatusMean;
	// Constructors

	/** default constructor */
	public ToolsPurchasePlanDetail() {
	}

	/** minimal constructor */
	public ToolsPurchasePlanDetail(String sppdId) {
		this.sppdId = sppdId;
	}

	/** full constructor */
	public ToolsPurchasePlanDetail(String sppdId, String documentNo,
			String toolsNo, String toolsName, String toolsKindName,
			String toolsKindNo, String toolsModel, String toolsSpec,
			Double planQuantity, Double planPrice, Double pisQuantity,
			Double pisPrice, String unit, String DRemark, Short DStatus,
			String stopComment, Double stopQuantity, String stationId,
			Short flag, Double sabQuantity, String purchaseNo,
			String supplierId, String supplierNo, String supplierName) {
		this.sppdId = sppdId;
		this.documentNo = documentNo;
		this.toolsNo = toolsNo;
		this.toolsName = toolsName;
		this.toolsKindName = toolsKindName;
		this.toolsKindNo = toolsKindNo;
		this.toolsModel = toolsModel;
		this.toolsSpec = toolsSpec;
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
		this.supplierName = supplierName;
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

	public String getToolsNo() {
		return this.toolsNo;
	}

	public void setToolsNo(String toolsNo) {
		this.toolsNo = toolsNo;
	}

	public String getToolsName() {
		return this.toolsName;
	}

	public void setToolsName(String toolsName) {
		this.toolsName = toolsName;
	}

	public String getToolsKindName() {
		return this.toolsKindName;
	}

	public void setToolsKindName(String toolsKindName) {
		this.toolsKindName = toolsKindName;
	}

	public String getToolsKindNo() {
		return this.toolsKindNo;
	}

	public void setToolsKindNo(String toolsKindNo) {
		this.toolsKindNo = toolsKindNo;
	}

	public String getToolsModel() {
		return this.toolsModel;
	}

	public void setToolsModel(String toolsModel) {
		this.toolsModel = toolsModel;
	}

	public String getToolsSpec() {
		return this.toolsSpec;
	}

	public void setToolsSpec(String toolsSpec) {
		this.toolsSpec = toolsSpec;
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

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
