
package cn.sf_soft.parts.inventory.model;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 配件采购订单
 * @author ZHJ
 * @date 2016-12-17
 *
 */


public class PartPurchaseOrder  implements java.io.Serializable {


    // Fields    

     private String purchaseNo;
     private Short purchaseStatus;
     private Short payType;
     private Short paymentStatus;
     private Double deposit;
     private Double pricePlan;
     private Double priceApprove;
     private Double priceSupplier;
     private Double quantityPlan;
     private Double quantityApprove;
     private Double quantitySupplier;
     private Double quantityPis;
     private Double quantityAbort;
     private Double quantitySab;
     private Double pricePis;
     private String planNo;
     private String supplierId;
     private String supplierNo;
     private String supplierName;
     private String stationId;
     private String stationIdPurchase;
     private String remark;
     private String creator;
     private Timestamp createTime;
     private String modifier;
     private Timestamp modifyTime;
     private String approver;
     private Short approveStatus;
     private Timestamp approveTime;
     private String approvePostil;
     private Timestamp finishTime;
     private String confirmNo;
     private Timestamp repayTime;
     private Timestamp confirmDate;
     private String purchaseId;
     private Integer purchaseOrderType;
     private String orderType;
     private String carType;
     private String objectNoSell;
     private String objectNameSell;
     private String objectNoInvoice;
     private String objectNameInvoice;
     private Timestamp orderDate;
     private Timestamp anticipantDate;
     private String orderStatus;
     private String orderFactory;
     private String deliveryMode;
     private String selfPayCarryFee;
     private String predirectType;
     private Timestamp deliveryTime;
     private Boolean isUpload;
     private String uploader;
     private Timestamp uploadTime;
     private String customerId;
     private String customerNo;
     private String customerName;
     private String objectIdSell;
     private String objectIdInvoice;
     private String objectIdPayment;
     private String objectNoPayment;
     private String objectNamePayment;
     private String objectIdReceive;
     private String objectNoReceive;
     private String objectNameReceive;
     private Integer earnestPercent;
     private String orgId;
     private String errorCode;
     private String errorMsg;
     private String crmPurchaseId;
     private String crmPurchaseNo;
     private BigDecimal creditCanUseMoney;
     private BigDecimal moneyCanUse;
     private String saleOrderNoRef;
     private String orderReceivableStatus;
     private String crmPurchaseNoReference;
     private String issueOrderNum;
     private String sapOrderNum;
     private String orderSource;
     private Double crmQuantityApprove;
     private Double crmPriceApprove;
     private Short uploadStatus;


    // Constructors

    /** default constructor */
    public PartPurchaseOrder() {
    }

	/** minimal constructor */
    public PartPurchaseOrder(String purchaseId, String orderType, String objectNoSell, String objectNameSell, String objectNoInvoice, String deliveryMode, String objectIdSell, String objectIdInvoice, String objectIdPayment, String objectNoPayment, String objectIdReceive, String objectNoReceive, String orgId) {
        this.purchaseId = purchaseId;
        this.orderType = orderType;
        this.objectNoSell = objectNoSell;
        this.objectNameSell = objectNameSell;
        this.objectNoInvoice = objectNoInvoice;
        this.deliveryMode = deliveryMode;
        this.objectIdSell = objectIdSell;
        this.objectIdInvoice = objectIdInvoice;
        this.objectIdPayment = objectIdPayment;
        this.objectNoPayment = objectNoPayment;
        this.objectIdReceive = objectIdReceive;
        this.objectNoReceive = objectNoReceive;
        this.orgId = orgId;
    }
    
    /** full constructor */
    public PartPurchaseOrder(Short purchaseStatus, Short payType, Short paymentStatus, Double deposit, Double pricePlan, Double priceApprove, Double priceSupplier, Double quantityPlan, Double quantityApprove, Double quantitySupplier, Double quantityPis, Double quantityAbort, Double quantitySab, Double pricePis, String planNo, String supplierId, String supplierNo, String supplierName, String stationId, String stationIdPurchase, String remark, String creator, Timestamp createTime, String modifier, Timestamp modifyTime, String approver, Short approveStatus, Timestamp approveTime, String approvePostil, Timestamp finishTime, String confirmNo, Timestamp repayTime, Timestamp confirmDate, String purchaseId, Integer purchaseOrderType, String orderType, String carType, String objectNoSell, String objectNameSell, String objectNoInvoice, String objectNameInvoice, Timestamp orderDate, Timestamp anticipantDate, String orderStatus, String orderFactory, String deliveryMode, String selfPayCarryFee, String predirectType, Timestamp deliveryTime, Boolean isUpload, String uploader, Timestamp uploadTime, String customerId, String customerNo, String customerName, String objectIdSell, String objectIdInvoice, String objectIdPayment, String objectNoPayment, String objectNamePayment, String objectIdReceive, String objectNoReceive, String objectNameReceive, Integer earnestPercent, String orgId, String errorCode, String errorMsg, String crmPurchaseId, String crmPurchaseNo, BigDecimal creditCanUseMoney, BigDecimal moneyCanUse, String saleOrderNoRef, String orderReceivableStatus, String crmPurchaseNoReference, String issueOrderNum, String sapOrderNum, String orderSource, Double crmQuantityApprove, Double crmPriceApprove, Short uploadStatus) {
        this.purchaseStatus = purchaseStatus;
        this.payType = payType;
        this.paymentStatus = paymentStatus;
        this.deposit = deposit;
        this.pricePlan = pricePlan;
        this.priceApprove = priceApprove;
        this.priceSupplier = priceSupplier;
        this.quantityPlan = quantityPlan;
        this.quantityApprove = quantityApprove;
        this.quantitySupplier = quantitySupplier;
        this.quantityPis = quantityPis;
        this.quantityAbort = quantityAbort;
        this.quantitySab = quantitySab;
        this.pricePis = pricePis;
        this.planNo = planNo;
        this.supplierId = supplierId;
        this.supplierNo = supplierNo;
        this.supplierName = supplierName;
        this.stationId = stationId;
        this.stationIdPurchase = stationIdPurchase;
        this.remark = remark;
        this.creator = creator;
        this.createTime = createTime;
        this.modifier = modifier;
        this.modifyTime = modifyTime;
        this.approver = approver;
        this.approveStatus = approveStatus;
        this.approveTime = approveTime;
        this.approvePostil = approvePostil;
        this.finishTime = finishTime;
        this.confirmNo = confirmNo;
        this.repayTime = repayTime;
        this.confirmDate = confirmDate;
        this.purchaseId = purchaseId;
        this.purchaseOrderType = purchaseOrderType;
        this.orderType = orderType;
        this.carType = carType;
        this.objectNoSell = objectNoSell;
        this.objectNameSell = objectNameSell;
        this.objectNoInvoice = objectNoInvoice;
        this.objectNameInvoice = objectNameInvoice;
        this.orderDate = orderDate;
        this.anticipantDate = anticipantDate;
        this.orderStatus = orderStatus;
        this.orderFactory = orderFactory;
        this.deliveryMode = deliveryMode;
        this.selfPayCarryFee = selfPayCarryFee;
        this.predirectType = predirectType;
        this.deliveryTime = deliveryTime;
        this.isUpload = isUpload;
        this.uploader = uploader;
        this.uploadTime = uploadTime;
        this.customerId = customerId;
        this.customerNo = customerNo;
        this.customerName = customerName;
        this.objectIdSell = objectIdSell;
        this.objectIdInvoice = objectIdInvoice;
        this.objectIdPayment = objectIdPayment;
        this.objectNoPayment = objectNoPayment;
        this.objectNamePayment = objectNamePayment;
        this.objectIdReceive = objectIdReceive;
        this.objectNoReceive = objectNoReceive;
        this.objectNameReceive = objectNameReceive;
        this.earnestPercent = earnestPercent;
        this.orgId = orgId;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.crmPurchaseId = crmPurchaseId;
        this.crmPurchaseNo = crmPurchaseNo;
        this.creditCanUseMoney = creditCanUseMoney;
        this.moneyCanUse = moneyCanUse;
        this.saleOrderNoRef = saleOrderNoRef;
        this.orderReceivableStatus = orderReceivableStatus;
        this.crmPurchaseNoReference = crmPurchaseNoReference;
        this.issueOrderNum = issueOrderNum;
        this.sapOrderNum = sapOrderNum;
        this.orderSource = orderSource;
        this.crmQuantityApprove = crmQuantityApprove;
        this.crmPriceApprove = crmPriceApprove;
        this.uploadStatus = uploadStatus;
    }

   
    // Property accessors

    public String getPurchaseNo() {
        return this.purchaseNo;
    }
    
    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public Short getPurchaseStatus() {
        return this.purchaseStatus;
    }
    
    public void setPurchaseStatus(Short purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public Short getPayType() {
        return this.payType;
    }
    
    public void setPayType(Short payType) {
        this.payType = payType;
    }

    public Short getPaymentStatus() {
        return this.paymentStatus;
    }
    
    public void setPaymentStatus(Short paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getDeposit() {
        return this.deposit;
    }
    
    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getPricePlan() {
        return this.pricePlan;
    }
    
    public void setPricePlan(Double pricePlan) {
        this.pricePlan = pricePlan;
    }

    public Double getPriceApprove() {
        return this.priceApprove;
    }
    
    public void setPriceApprove(Double priceApprove) {
        this.priceApprove = priceApprove;
    }

    public Double getPriceSupplier() {
        return this.priceSupplier;
    }
    
    public void setPriceSupplier(Double priceSupplier) {
        this.priceSupplier = priceSupplier;
    }

    public Double getQuantityPlan() {
        return this.quantityPlan;
    }
    
    public void setQuantityPlan(Double quantityPlan) {
        this.quantityPlan = quantityPlan;
    }

    public Double getQuantityApprove() {
        return this.quantityApprove;
    }
    
    public void setQuantityApprove(Double quantityApprove) {
        this.quantityApprove = quantityApprove;
    }

    public Double getQuantitySupplier() {
        return this.quantitySupplier;
    }
    
    public void setQuantitySupplier(Double quantitySupplier) {
        this.quantitySupplier = quantitySupplier;
    }

    public Double getQuantityPis() {
        return this.quantityPis;
    }
    
    public void setQuantityPis(Double quantityPis) {
        this.quantityPis = quantityPis;
    }

    public Double getQuantityAbort() {
        return this.quantityAbort;
    }
    
    public void setQuantityAbort(Double quantityAbort) {
        this.quantityAbort = quantityAbort;
    }

    public Double getQuantitySab() {
        return this.quantitySab;
    }
    
    public void setQuantitySab(Double quantitySab) {
        this.quantitySab = quantitySab;
    }

    public Double getPricePis() {
        return this.pricePis;
    }
    
    public void setPricePis(Double pricePis) {
        this.pricePis = pricePis;
    }

    public String getPlanNo() {
        return this.planNo;
    }
    
    public void setPlanNo(String planNo) {
        this.planNo = planNo;
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

    public String getStationId() {
        return this.stationId;
    }
    
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationIdPurchase() {
        return this.stationIdPurchase;
    }
    
    public void setStationIdPurchase(String stationIdPurchase) {
        this.stationIdPurchase = stationIdPurchase;
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

    public String getApprover() {
        return this.approver;
    }
    
    public void setApprover(String approver) {
        this.approver = approver;
    }

    public Short getApproveStatus() {
        return this.approveStatus;
    }
    
    public void setApproveStatus(Short approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Timestamp getApproveTime() {
        return this.approveTime;
    }
    
    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    public String getApprovePostil() {
        return this.approvePostil;
    }
    
    public void setApprovePostil(String approvePostil) {
        this.approvePostil = approvePostil;
    }

    public Timestamp getFinishTime() {
        return this.finishTime;
    }
    
    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    public String getConfirmNo() {
        return this.confirmNo;
    }
    
    public void setConfirmNo(String confirmNo) {
        this.confirmNo = confirmNo;
    }

    public Timestamp getRepayTime() {
        return this.repayTime;
    }
    
    public void setRepayTime(Timestamp repayTime) {
        this.repayTime = repayTime;
    }

    public Timestamp getConfirmDate() {
        return this.confirmDate;
    }
    
    public void setConfirmDate(Timestamp confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getPurchaseId() {
        return this.purchaseId;
    }
    
    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getPurchaseOrderType() {
        return this.purchaseOrderType;
    }
    
    public void setPurchaseOrderType(Integer purchaseOrderType) {
        this.purchaseOrderType = purchaseOrderType;
    }

    public String getOrderType() {
        return this.orderType;
    }
    
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCarType() {
        return this.carType;
    }
    
    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getObjectNoSell() {
        return this.objectNoSell;
    }
    
    public void setObjectNoSell(String objectNoSell) {
        this.objectNoSell = objectNoSell;
    }

    public String getObjectNameSell() {
        return this.objectNameSell;
    }
    
    public void setObjectNameSell(String objectNameSell) {
        this.objectNameSell = objectNameSell;
    }

    public String getObjectNoInvoice() {
        return this.objectNoInvoice;
    }
    
    public void setObjectNoInvoice(String objectNoInvoice) {
        this.objectNoInvoice = objectNoInvoice;
    }

    public String getObjectNameInvoice() {
        return this.objectNameInvoice;
    }
    
    public void setObjectNameInvoice(String objectNameInvoice) {
        this.objectNameInvoice = objectNameInvoice;
    }

    public Timestamp getOrderDate() {
        return this.orderDate;
    }
    
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Timestamp getAnticipantDate() {
        return this.anticipantDate;
    }
    
    public void setAnticipantDate(Timestamp anticipantDate) {
        this.anticipantDate = anticipantDate;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }
    
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderFactory() {
        return this.orderFactory;
    }
    
    public void setOrderFactory(String orderFactory) {
        this.orderFactory = orderFactory;
    }

    public String getDeliveryMode() {
        return this.deliveryMode;
    }
    
    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getSelfPayCarryFee() {
        return this.selfPayCarryFee;
    }
    
    public void setSelfPayCarryFee(String selfPayCarryFee) {
        this.selfPayCarryFee = selfPayCarryFee;
    }

    public String getPredirectType() {
        return this.predirectType;
    }
    
    public void setPredirectType(String predirectType) {
        this.predirectType = predirectType;
    }

    public Timestamp getDeliveryTime() {
        return this.deliveryTime;
    }
    
    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Boolean getIsUpload() {
        return this.isUpload;
    }
    
    public void setIsUpload(Boolean isUpload) {
        this.isUpload = isUpload;
    }

    public String getUploader() {
        return this.uploader;
    }
    
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public Timestamp getUploadTime() {
        return this.uploadTime;
    }
    
    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getCustomerId() {
        return this.customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNo() {
        return this.customerNo;
    }
    
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return this.customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getObjectIdSell() {
        return this.objectIdSell;
    }
    
    public void setObjectIdSell(String objectIdSell) {
        this.objectIdSell = objectIdSell;
    }

    public String getObjectIdInvoice() {
        return this.objectIdInvoice;
    }
    
    public void setObjectIdInvoice(String objectIdInvoice) {
        this.objectIdInvoice = objectIdInvoice;
    }

    public String getObjectIdPayment() {
        return this.objectIdPayment;
    }
    
    public void setObjectIdPayment(String objectIdPayment) {
        this.objectIdPayment = objectIdPayment;
    }

    public String getObjectNoPayment() {
        return this.objectNoPayment;
    }
    
    public void setObjectNoPayment(String objectNoPayment) {
        this.objectNoPayment = objectNoPayment;
    }

    public String getObjectNamePayment() {
        return this.objectNamePayment;
    }
    
    public void setObjectNamePayment(String objectNamePayment) {
        this.objectNamePayment = objectNamePayment;
    }

    public String getObjectIdReceive() {
        return this.objectIdReceive;
    }
    
    public void setObjectIdReceive(String objectIdReceive) {
        this.objectIdReceive = objectIdReceive;
    }

    public String getObjectNoReceive() {
        return this.objectNoReceive;
    }
    
    public void setObjectNoReceive(String objectNoReceive) {
        this.objectNoReceive = objectNoReceive;
    }

    public String getObjectNameReceive() {
        return this.objectNameReceive;
    }
    
    public void setObjectNameReceive(String objectNameReceive) {
        this.objectNameReceive = objectNameReceive;
    }

    public Integer getEarnestPercent() {
        return this.earnestPercent;
    }
    
    public void setEarnestPercent(Integer earnestPercent) {
        this.earnestPercent = earnestPercent;
    }

    public String getOrgId() {
        return this.orgId;
    }
    
    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    public String getCrmPurchaseId() {
        return this.crmPurchaseId;
    }
    
    public void setCrmPurchaseId(String crmPurchaseId) {
        this.crmPurchaseId = crmPurchaseId;
    }

    public String getCrmPurchaseNo() {
        return this.crmPurchaseNo;
    }
    
    public void setCrmPurchaseNo(String crmPurchaseNo) {
        this.crmPurchaseNo = crmPurchaseNo;
    }

    public BigDecimal getCreditCanUseMoney() {
        return this.creditCanUseMoney;
    }
    
    public void setCreditCanUseMoney(BigDecimal creditCanUseMoney) {
        this.creditCanUseMoney = creditCanUseMoney;
    }

    public BigDecimal getMoneyCanUse() {
        return this.moneyCanUse;
    }
    
    public void setMoneyCanUse(BigDecimal moneyCanUse) {
        this.moneyCanUse = moneyCanUse;
    }

    public String getSaleOrderNoRef() {
        return this.saleOrderNoRef;
    }
    
    public void setSaleOrderNoRef(String saleOrderNoRef) {
        this.saleOrderNoRef = saleOrderNoRef;
    }

    public String getOrderReceivableStatus() {
        return this.orderReceivableStatus;
    }
    
    public void setOrderReceivableStatus(String orderReceivableStatus) {
        this.orderReceivableStatus = orderReceivableStatus;
    }

    public String getCrmPurchaseNoReference() {
        return this.crmPurchaseNoReference;
    }
    
    public void setCrmPurchaseNoReference(String crmPurchaseNoReference) {
        this.crmPurchaseNoReference = crmPurchaseNoReference;
    }

    public String getIssueOrderNum() {
        return this.issueOrderNum;
    }
    
    public void setIssueOrderNum(String issueOrderNum) {
        this.issueOrderNum = issueOrderNum;
    }

    public String getSapOrderNum() {
        return this.sapOrderNum;
    }
    
    public void setSapOrderNum(String sapOrderNum) {
        this.sapOrderNum = sapOrderNum;
    }

    public String getOrderSource() {
        return this.orderSource;
    }
    
    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public Double getCrmQuantityApprove() {
        return this.crmQuantityApprove;
    }
    
    public void setCrmQuantityApprove(Double crmQuantityApprove) {
        this.crmQuantityApprove = crmQuantityApprove;
    }

    public Double getCrmPriceApprove() {
        return this.crmPriceApprove;
    }
    
    public void setCrmPriceApprove(Double crmPriceApprove) {
        this.crmPriceApprove = crmPriceApprove;
    }

    public Short getUploadStatus() {
        return this.uploadStatus;
    }
    
    public void setUploadStatus(Short uploadStatus) {
        this.uploadStatus = uploadStatus;
    }
   








}
