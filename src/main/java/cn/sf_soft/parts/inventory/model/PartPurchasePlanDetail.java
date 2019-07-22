package cn.sf_soft.parts.inventory.model;
import java.sql.Timestamp;

import javax.persistence.Transient;


/**
 *配件采购计划明细
 *@author ZHJ
 *@date 2016-12-17
 */

public class PartPurchasePlanDetail  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 4549679544395287667L;
	private String ppdetailId;
     private Short ppdetailStatus;
     private String purchaseNo;
     private String documentNo;
     private PartPurchasePlans document;
	 private String supplierId;
     private String supplierNo;
     private String supplierName;
     private String stockId;
     private String warehouseId;
     private String warehouseName;
     private String partId;
     private String partNo;
     private String partMnemonic;
     private String partName;
     private String producingArea;
     private String partType;
     private String partProperty;
     private String specModel;
     private String applicableModel;
     private String color;
     private String material;
     private String unit;
     private Double minQuantityManual;
     private Double stockQuantityRecord;
     private Double costRef;
     private Double planPrice;
     private Double supplierPrice;
     private Double autoQuantity;
     private Double planQuantity;
     private Double approveQuantity;
     private Double supplierQuantity;
     private Double pisQuantity;
     private Double abortQuantity;
     private Double quantityWay;
     private Integer quantityPackages;
     private Double planQuantityPackage;
     private Integer deliveryDays;
     private String remark;
     private Double minQuantityCompute;
     private Double maxQuantityCompute;
     private Double maxQuantityManual;
     private String saleNo;
     private String confirmNo;
     private Timestamp pisDate;
     private Timestamp confirmDate;
     private String isAuto;
     private Double adviceQuantity;
     private String purchaseId;
     private String selfStatus;
     private String isReplaced;
     private String isMatched;
     private String parRowId;
     private String mateRowId;
     private String tempPartNo;
     private String technicalParametersDesc;
     private String mateFactory;
     private Double withdrawQuantity;
     private Double balanceQuantity;
     private String originDetailId;
     private String orderNumber;
     private String materialNo;
     private String partIdSap;
     private String errorCode;
     private String errorMsg;
     private String crmPurchaseNo;
     private String crmSelfId;
     private String orderNo;
     private String factoryName;
     private String totMoney;
     private Timestamp lastUpdateTime;
     private String abcSort;
     private Double quantityRef;
     private String detailRemark;
     private String detailStatus;
     private Double approvePrice;
     private String matchMateCode;
     private String originPurchaseId;
     private String originPurchaseDetailId;
     private Double crmApproveQuantity;
     private Double crmApprovePrice;
     //
     private Double cost;
     
     private Double orderQuantity;
     
     private Double preOrderCount;
     
	 private String managementState;


    // Constructors

   

	public String getManagementState() {
		return managementState;
	}


	public void setManagementState(String managementState) {
		this.managementState = managementState;
	}


	/** default constructor */
    public PartPurchasePlanDetail() {
    }

    
    /** full constructor */
    public PartPurchasePlanDetail(Short ppdetailStatus, String purchaseNo, String documentNo, String supplierId, String supplierNo, String supplierName, String stockId, String warehouseId, String warehouseName, String partId, String partNo, String partMnemonic, String partName, String producingArea, String partType, String partProperty, String specModel, String applicableModel, String color, String material, String unit, Double minQuantityManual, Double stockQuantityRecord, Double costRef, Double planPrice, Double supplierPrice, Double autoQuantity, Double planQuantity, Double approveQuantity, Double supplierQuantity, Double pisQuantity, Double abortQuantity, Double quantityWay, Integer quantityPackages, Double planQuantityPackage, Integer deliveryDays, String remark, Double minQuantityCompute, Double maxQuantityCompute, Double maxQuantityManual, String saleNo, String confirmNo, Timestamp pisDate, Timestamp confirmDate, String isAuto, Double adviceQuantity, String purchaseId, String selfStatus, String isReplaced, String isMatched, String parRowId, String mateRowId, String tempPartNo, String technicalParametersDesc, String mateFactory, Double withdrawQuantity, Double balanceQuantity, String originDetailId, String orderNumber, String materialNo, String partIdSap, String errorCode, String errorMsg, String crmPurchaseNo, String crmSelfId, String orderNo, String factoryName, String totMoney, Timestamp lastUpdateTime, String abcSort, Double quantityRef, String detailRemark, String detailStatus, Double approvePrice, String matchMateCode, String originPurchaseId, String originPurchaseDetailId, Double crmApproveQuantity, Double crmApprovePrice) {
        this.ppdetailStatus = ppdetailStatus;
        this.purchaseNo = purchaseNo;
        this.documentNo = documentNo;
        this.supplierId = supplierId;
        this.supplierNo = supplierNo;
        this.supplierName = supplierName;
        this.stockId = stockId;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.partId = partId;
        this.partNo = partNo;
        this.partMnemonic = partMnemonic;
        this.partName = partName;
        this.producingArea = producingArea;
        this.partType = partType;
        this.partProperty = partProperty;
        this.specModel = specModel;
        this.applicableModel = applicableModel;
        this.color = color;
        this.material = material;
        this.unit = unit;
        this.minQuantityManual = minQuantityManual;
        this.stockQuantityRecord = stockQuantityRecord;
        this.costRef = costRef;
        this.planPrice = planPrice;
        this.supplierPrice = supplierPrice;
        this.autoQuantity = autoQuantity;
        this.planQuantity = planQuantity;
        this.approveQuantity = approveQuantity;
        this.supplierQuantity = supplierQuantity;
        this.pisQuantity = pisQuantity;
        this.abortQuantity = abortQuantity;
        this.quantityWay = quantityWay;
        this.quantityPackages = quantityPackages;
        this.planQuantityPackage = planQuantityPackage;
        this.deliveryDays = deliveryDays;
        this.remark = remark;
        this.minQuantityCompute = minQuantityCompute;
        this.maxQuantityCompute = maxQuantityCompute;
        this.maxQuantityManual = maxQuantityManual;
        this.saleNo = saleNo;
        this.confirmNo = confirmNo;
        this.pisDate = pisDate;
        this.confirmDate = confirmDate;
        this.isAuto = isAuto;
        this.adviceQuantity = adviceQuantity;
        this.purchaseId = purchaseId;
        this.selfStatus = selfStatus;
        this.isReplaced = isReplaced;
        this.isMatched = isMatched;
        this.parRowId = parRowId;
        this.mateRowId = mateRowId;
        this.tempPartNo = tempPartNo;
        this.technicalParametersDesc = technicalParametersDesc;
        this.mateFactory = mateFactory;
        this.withdrawQuantity = withdrawQuantity;
        this.balanceQuantity = balanceQuantity;
        this.originDetailId = originDetailId;
        this.orderNumber = orderNumber;
        this.materialNo = materialNo;
        this.partIdSap = partIdSap;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.crmPurchaseNo = crmPurchaseNo;
        this.crmSelfId = crmSelfId;
        this.orderNo = orderNo;
        this.factoryName = factoryName;
        this.totMoney = totMoney;
        this.lastUpdateTime = lastUpdateTime;
        this.abcSort = abcSort;
        this.quantityRef = quantityRef;
        this.detailRemark = detailRemark;
        this.detailStatus = detailStatus;
        this.approvePrice = approvePrice;
        this.matchMateCode = matchMateCode;
        this.originPurchaseId = originPurchaseId;
        this.originPurchaseDetailId = originPurchaseDetailId;
        this.crmApproveQuantity = crmApproveQuantity;
        this.crmApprovePrice = crmApprovePrice;
    }

   
    // Property accessors

    public String getPpdetailId() {
        return this.ppdetailId;
    }
    
    public void setPpdetailId(String ppdetailId) {
        this.ppdetailId = ppdetailId;
    }

    public Short getPpdetailStatus() {
        return this.ppdetailStatus;
    }
    
    public void setPpdetailStatus(Short ppdetailStatus) {
        this.ppdetailStatus = ppdetailStatus;
    }

    public String getPurchaseNo() {
        return this.purchaseNo;
    }
    
    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getDocumentNo() {
        return this.documentNo;
    }
    
    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
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

    public String getStockId() {
        return this.stockId;
    }
    
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getWarehouseId() {
        return this.warehouseId;
    }
    
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return this.warehouseName;
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getPartId() {
        return this.partId;
    }
    
    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getPartNo() {
        return this.partNo;
    }
    
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getPartMnemonic() {
        return this.partMnemonic;
    }
    
    public void setPartMnemonic(String partMnemonic) {
        this.partMnemonic = partMnemonic;
    }

    public String getPartName() {
        return this.partName;
    }
    
    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getProducingArea() {
        return this.producingArea;
    }
    
    public void setProducingArea(String producingArea) {
        this.producingArea = producingArea;
    }

    public String getPartType() {
        return this.partType;
    }
    
    public void setPartType(String partType) {
        this.partType = partType;
    }

    public String getPartProperty() {
        return this.partProperty;
    }
    
    public void setPartProperty(String partProperty) {
        this.partProperty = partProperty;
    }

    public String getSpecModel() {
        return this.specModel;
    }
    
    public void setSpecModel(String specModel) {
        this.specModel = specModel;
    }

    public String getApplicableModel() {
        return this.applicableModel;
    }
    
    public void setApplicableModel(String applicableModel) {
        this.applicableModel = applicableModel;
    }

    public String getColor() {
        return this.color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return this.material;
    }
    
    public void setMaterial(String material) {
        this.material = material;
    }

    public String getUnit() {
        return this.unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getMinQuantityManual() {
        return this.minQuantityManual;
    }
    
    public void setMinQuantityManual(Double minQuantityManual) {
        this.minQuantityManual = minQuantityManual;
    }

    public Double getStockQuantityRecord() {
        return this.stockQuantityRecord;
    }
    
    public void setStockQuantityRecord(Double stockQuantityRecord) {
        this.stockQuantityRecord = stockQuantityRecord;
    }

    public Double getCostRef() {
        return this.costRef;
    }
    
    public void setCostRef(Double costRef) {
        this.costRef = costRef;
    }

    public Double getPlanPrice() {
        return this.planPrice;
    }
    
    public void setPlanPrice(Double planPrice) {
        this.planPrice = planPrice;
    }

    public Double getSupplierPrice() {
        return this.supplierPrice;
    }
    
    public void setSupplierPrice(Double supplierPrice) {
        this.supplierPrice = supplierPrice;
    }

    public Double getAutoQuantity() {
        return this.autoQuantity;
    }
    
    public void setAutoQuantity(Double autoQuantity) {
        this.autoQuantity = autoQuantity;
    }

    public Double getPlanQuantity() {
        return this.planQuantity;
    }
    
    public void setPlanQuantity(Double planQuantity) {
        this.planQuantity = planQuantity;
    }

    public Double getApproveQuantity() {
        return this.approveQuantity;
    }
    
    public void setApproveQuantity(Double approveQuantity) {
        this.approveQuantity = approveQuantity;
    }

    public Double getSupplierQuantity() {
        return this.supplierQuantity;
    }
    
    public void setSupplierQuantity(Double supplierQuantity) {
        this.supplierQuantity = supplierQuantity;
    }

    public Double getPisQuantity() {
        return this.pisQuantity;
    }
    
    public void setPisQuantity(Double pisQuantity) {
        this.pisQuantity = pisQuantity;
    }

    public Double getAbortQuantity() {
        return this.abortQuantity;
    }
    
    public void setAbortQuantity(Double abortQuantity) {
        this.abortQuantity = abortQuantity;
    }

    public Double getQuantityWay() {
        return this.quantityWay;
    }
    
    public void setQuantityWay(Double quantityWay) {
        this.quantityWay = quantityWay;
    }

    public Integer getQuantityPackages() {
        return this.quantityPackages;
    }
    
    public void setQuantityPackages(Integer quantityPackages) {
        this.quantityPackages = quantityPackages;
    }

    public Double getPlanQuantityPackage() {
        return this.planQuantityPackage;
    }
    
    public void setPlanQuantityPackage(Double planQuantityPackage) {
        this.planQuantityPackage = planQuantityPackage;
    }

    public Integer getDeliveryDays() {
        return this.deliveryDays;
    }
    
    public void setDeliveryDays(Integer deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getMinQuantityCompute() {
        return this.minQuantityCompute;
    }
    
    public void setMinQuantityCompute(Double minQuantityCompute) {
        this.minQuantityCompute = minQuantityCompute;
    }

    public Double getMaxQuantityCompute() {
        return this.maxQuantityCompute;
    }
    
    public void setMaxQuantityCompute(Double maxQuantityCompute) {
        this.maxQuantityCompute = maxQuantityCompute;
    }

    public Double getMaxQuantityManual() {
        return this.maxQuantityManual;
    }
    
    public void setMaxQuantityManual(Double maxQuantityManual) {
        this.maxQuantityManual = maxQuantityManual;
    }

    public String getSaleNo() {
        return this.saleNo;
    }
    
    public void setSaleNo(String saleNo) {
        this.saleNo = saleNo;
    }

    public String getConfirmNo() {
        return this.confirmNo;
    }
    
    public void setConfirmNo(String confirmNo) {
        this.confirmNo = confirmNo;
    }

    public Timestamp getPisDate() {
        return this.pisDate;
    }
    
    public void setPisDate(Timestamp pisDate) {
        this.pisDate = pisDate;
    }

    public Timestamp getConfirmDate() {
        return this.confirmDate;
    }
    
    public void setConfirmDate(Timestamp confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getIsAuto() {
        return this.isAuto;
    }
    
    public void setIsAuto(String isAuto) {
        this.isAuto = isAuto;
    }

    public Double getAdviceQuantity() {
        return this.adviceQuantity;
    }
    
    public void setAdviceQuantity(Double adviceQuantity) {
        this.adviceQuantity = adviceQuantity;
    }

    public String getPurchaseId() {
        return this.purchaseId;
    }
    
    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getSelfStatus() {
        return this.selfStatus;
    }
    
    public void setSelfStatus(String selfStatus) {
        this.selfStatus = selfStatus;
    }

    public String getIsReplaced() {
        return this.isReplaced;
    }
    
    public void setIsReplaced(String isReplaced) {
        this.isReplaced = isReplaced;
    }

    public String getIsMatched() {
        return this.isMatched;
    }
    
    public void setIsMatched(String isMatched) {
        this.isMatched = isMatched;
    }

    public String getParRowId() {
        return this.parRowId;
    }
    
    public void setParRowId(String parRowId) {
        this.parRowId = parRowId;
    }

    public String getMateRowId() {
        return this.mateRowId;
    }
    
    public void setMateRowId(String mateRowId) {
        this.mateRowId = mateRowId;
    }

    public String getTempPartNo() {
        return this.tempPartNo;
    }
    
    public void setTempPartNo(String tempPartNo) {
        this.tempPartNo = tempPartNo;
    }

    public String getTechnicalParametersDesc() {
        return this.technicalParametersDesc;
    }
    
    public void setTechnicalParametersDesc(String technicalParametersDesc) {
        this.technicalParametersDesc = technicalParametersDesc;
    }

    public String getMateFactory() {
        return this.mateFactory;
    }
    
    public void setMateFactory(String mateFactory) {
        this.mateFactory = mateFactory;
    }

    public Double getWithdrawQuantity() {
        return this.withdrawQuantity;
    }
    
    public void setWithdrawQuantity(Double withdrawQuantity) {
        this.withdrawQuantity = withdrawQuantity;
    }

    public Double getBalanceQuantity() {
        return this.balanceQuantity;
    }
    
    public void setBalanceQuantity(Double balanceQuantity) {
        this.balanceQuantity = balanceQuantity;
    }

    public String getOriginDetailId() {
        return this.originDetailId;
    }
    
    public void setOriginDetailId(String originDetailId) {
        this.originDetailId = originDetailId;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }
    
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getMaterialNo() {
        return this.materialNo;
    }
    
    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getPartIdSap() {
        return this.partIdSap;
    }
    
    public void setPartIdSap(String partIdSap) {
        this.partIdSap = partIdSap;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }
    
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCrmPurchaseNo() {
        return this.crmPurchaseNo;
    }
    
    public void setCrmPurchaseNo(String crmPurchaseNo) {
        this.crmPurchaseNo = crmPurchaseNo;
    }

    public String getCrmSelfId() {
        return this.crmSelfId;
    }
    
    public void setCrmSelfId(String crmSelfId) {
        this.crmSelfId = crmSelfId;
    }

    public String getOrderNo() {
        return this.orderNo;
    }
    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFactoryName() {
        return this.factoryName;
    }
    
    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getTotMoney() {
        return this.totMoney;
    }
    
    public void setTotMoney(String totMoney) {
        this.totMoney = totMoney;
    }

    public Timestamp getLastUpdateTime() {
        return this.lastUpdateTime;
    }
    
    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getAbcSort() {
        return this.abcSort;
    }
    
    public void setAbcSort(String abcSort) {
        this.abcSort = abcSort;
    }

    public Double getQuantityRef() {
        return this.quantityRef;
    }
    
    public void setQuantityRef(Double quantityRef) {
        this.quantityRef = quantityRef;
    }

    public String getDetailRemark() {
        return this.detailRemark;
    }
    
    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    public String getDetailStatus() {
        return this.detailStatus;
    }
    
    public void setDetailStatus(String detailStatus) {
        this.detailStatus = detailStatus;
    }

    public Double getApprovePrice() {
        return this.approvePrice;
    }
    
    public void setApprovePrice(Double approvePrice) {
        this.approvePrice = approvePrice;
    }

    public String getMatchMateCode() {
        return this.matchMateCode;
    }
    
    public void setMatchMateCode(String matchMateCode) {
        this.matchMateCode = matchMateCode;
    }

    public String getOriginPurchaseId() {
        return this.originPurchaseId;
    }
    
    public void setOriginPurchaseId(String originPurchaseId) {
        this.originPurchaseId = originPurchaseId;
    }

    public String getOriginPurchaseDetailId() {
        return this.originPurchaseDetailId;
    }
    
    public void setOriginPurchaseDetailId(String originPurchaseDetailId) {
        this.originPurchaseDetailId = originPurchaseDetailId;
    }

    public Double getCrmApproveQuantity() {
        return this.crmApproveQuantity;
    }
    
    public void setCrmApproveQuantity(Double crmApproveQuantity) {
        this.crmApproveQuantity = crmApproveQuantity;
    }

    public Double getCrmApprovePrice() {
        return this.crmApprovePrice;
    }
    
    public void setCrmApprovePrice(Double crmApprovePrice) {
        this.crmApprovePrice = crmApprovePrice;
    }
    
    public PartPurchasePlans getDocument() {
  		return document;
  	}


  	public void setDocument(PartPurchasePlans document) {
  		this.document = document;
  	}
  	
  	@Transient
  	 public Double getCost() {
 		return cost;
 	}


 	public void setCost(Double cost) {
 		this.cost = cost;
 	}
	@Transient
  	public Double getPreOrderCount() {
  		return preOrderCount;
  	}


  	public void setPreOrderCount(Double preOrderCount) {
  		this.preOrderCount = preOrderCount;
  	}


	@Transient
  	public Double getOrderQuantity() {
  		return orderQuantity;
  	}


  	public void setOrderQuantity(Double orderQuantity) {
  		this.orderQuantity = orderQuantity;
  	}



}
