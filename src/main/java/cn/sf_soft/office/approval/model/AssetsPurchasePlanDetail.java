package cn.sf_soft.office.approval.model;



/**
 * AssetsPurchasePlanDetail entity. @author MyEclipse Persistence Tools
 */

public class AssetsPurchasePlanDetail  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -8519097661927469940L;
	private String appdId;
     private String documentNo;
     private String supplierName;
     private String supplierNo;
     private String assetsName;
     private String assetsKindName;
     private String assetsKindNo;
     private String assetsModel;
     private String assetsSpec;
     private Double planQuantity;
     private Double planPrice;
     private Double pisQuantity;
     private Double stopQuantity;
     private Double pisPrice;
     private String unit;
     private String DRemark;
     private Short DStatus;
     private String stopComment;
     private String assetsTypeName;
     private String assetsBrandName;
     private String assetsNameNo;

     private String statusMean;//状态的含义
    // Constructors

    /** default constructor */
    public AssetsPurchasePlanDetail() {
    }

	/** minimal constructor */
    public AssetsPurchasePlanDetail(String appdId) {
        this.appdId = appdId;
    }
    
    /** full constructor */
    public AssetsPurchasePlanDetail(String appdId, String documentNo, String supplierName, String supplierNo, String assetsName, String assetsKindName, String assetsKindNo, String assetsModel, String assetsSpec, Double planQuantity, Double planPrice, Double pisQuantity, Double stopQuantity, Double pisPrice, String unit, String DRemark, Short DStatus, String stopComment, String assetsTypeName, String assetsBrandName, String assetsNameNo) {
        this.appdId = appdId;
        this.documentNo = documentNo;
        this.supplierName = supplierName;
        this.supplierNo = supplierNo;
        this.assetsName = assetsName;
        this.assetsKindName = assetsKindName;
        this.assetsKindNo = assetsKindNo;
        this.assetsModel = assetsModel;
        this.assetsSpec = assetsSpec;
        this.planQuantity = planQuantity;
        this.planPrice = planPrice;
        this.pisQuantity = pisQuantity;
        this.stopQuantity = stopQuantity;
        this.pisPrice = pisPrice;
        this.unit = unit;
        this.DRemark = DRemark;
        this.DStatus = DStatus;
        this.stopComment = stopComment;
        this.assetsTypeName = assetsTypeName;
        this.assetsBrandName = assetsBrandName;
        this.assetsNameNo = assetsNameNo;
    }

   
    // Property accessors

    public String getAppdId() {
        return this.appdId;
    }
    
    public void setAppdId(String appdId) {
        this.appdId = appdId;
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

    public String getSupplierNo() {
        return this.supplierNo;
    }
    
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getAssetsName() {
        return this.assetsName;
    }
    
    public void setAssetsName(String assetsName) {
        this.assetsName = assetsName;
    }

    public String getAssetsKindName() {
        return this.assetsKindName;
    }
    
    public void setAssetsKindName(String assetsKindName) {
        this.assetsKindName = assetsKindName;
    }

    public String getAssetsKindNo() {
        return this.assetsKindNo;
    }
    
    public void setAssetsKindNo(String assetsKindNo) {
        this.assetsKindNo = assetsKindNo;
    }

    public String getAssetsModel() {
        return this.assetsModel;
    }
    
    public void setAssetsModel(String assetsModel) {
        this.assetsModel = assetsModel;
    }

    public String getAssetsSpec() {
        return this.assetsSpec;
    }
    
    public void setAssetsSpec(String assetsSpec) {
        this.assetsSpec = assetsSpec;
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

    public Double getStopQuantity() {
        return this.stopQuantity;
    }
    
    public void setStopQuantity(Double stopQuantity) {
        this.stopQuantity = stopQuantity;
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

    public String getAssetsTypeName() {
        return this.assetsTypeName;
    }
    
    public void setAssetsTypeName(String assetsTypeName) {
        this.assetsTypeName = assetsTypeName;
    }

    public String getAssetsBrandName() {
        return this.assetsBrandName;
    }
    
    public void setAssetsBrandName(String assetsBrandName) {
        this.assetsBrandName = assetsBrandName;
    }

    public String getAssetsNameNo() {
        return this.assetsNameNo;
    }
    
    public void setAssetsNameNo(String assetsNameNo) {
        this.assetsNameNo = assetsNameNo;
    }

	@Override
	public String toString() {
		return "AssetsPurchasePlanDetail [DRemark=" + DRemark + ", DStatus="
				+ DStatus + ", appdId=" + appdId + ", assetsBrandName="
				+ assetsBrandName + ", assetsKindName=" + assetsKindName
				+ ", assetsKindNo=" + assetsKindNo + ", assetsModel="
				+ assetsModel + ", assetsName=" + assetsName
				+ ", assetsNameNo=" + assetsNameNo + ", assetsSpec="
				+ assetsSpec + ", assetsTypeName=" + assetsTypeName
				+ ", documentNo=" + documentNo + ", pisPrice=" + pisPrice
				+ ", pisQuantity=" + pisQuantity + ", planPrice=" + planPrice
				+ ", planQuantity=" + planQuantity + ", stopComment="
				+ stopComment + ", stopQuantity=" + stopQuantity
				+ ", supplierName=" + supplierName + ", supplierNo="
				+ supplierNo + ", unit=" + unit + ", statusMean=" + statusMean + "]";
	}

	public void setStatusMean(String statusMean) {
		this.statusMean = statusMean;
	}

	public String getStatusMean() {
		return statusMean;
	}
   

}
