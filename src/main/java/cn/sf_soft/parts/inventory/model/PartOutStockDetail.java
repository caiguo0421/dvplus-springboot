package cn.sf_soft.parts.inventory.model;

import java.sql.Timestamp;

/**
 * 配件出库明细
 * @author cw
 * @date 2014-4-17 上午11:23:49
 */
public class PartOutStockDetail implements java.io.Serializable {

	// Fields

	private String posDetailId;
	private String posNo;
	private Double posQuantity;
	private Double priceRef;
	private Double posPrice;
	private Double posAgio;
	private Double costRef;
	private Double cost;
	private Double costNoTax;
	private Double carriage;
	private Double carriageNoTax;
	private Double largessPrice;
	private String stockId;
	private String partId;
	private String partNo;
	private String partName;
	private String partMnemonic;
	private String producingArea;
	private String partType;
	private String partProperty;
	private String specModel;
	private String applicableModel;
	private String color;
	private String material;
	private String unit;
	private String depositPosition;
	private String warehouseName;
	private String remark;
	private Boolean isRecycle;
	private Boolean trusteeship;
	private String trusteeshipNo;
	private String taskNo;
	private String psoDetailId;
	private String accountId;
	private String accountName;
	private Short accountType;
	private String workGroupId;
	private String workGroupName;
	private String originDetailId;
	private Double quantitySab;
	private Double quantityRecord;
	private Double carriageSab;
	private Double carriageNoTaxSab;
	private Double toQuantityRecord;
	private String toStockId;
	private String toWarehouseId;
	private String toWarehouseName;
	private Double carriageOut;
	private String ptdNo;
	private Short claimPayType;
	private Double claimItemPrice;
	private Double claimPartPrice;
	private String claimRejectReason;
	private Short claimStatus;
	private String claimRemark;
	private Double claimQuantity;
	private Double claimInQuantity;
	private Timestamp claimTime;
	private String claimer;
	private String serialNo;
	private String servicePartId;
	private Double priceAgio;
	private String reasonLowPrice;
	private String remarkLowPrice;
	private Short approveResultLowPrice;
	private String approverLowPrice;
	private Timestamp approveTimeLowPrice;
	private Boolean isAllowIncome;
	private Double carriageRef;
	private Double stopQuantityRecord;
	private Double quantityBorrowOut;
	private Short warehouseType;
	private Double quantityBorrowBack;
	private String oriDetailId;
	private String oriPosNo;
	private Double stopToQuantityRecord;
	private Boolean isPackage;
	private Double posAgioBefore;
	private Boolean isOverOrderCount;
	private Double orderCount;
	private String partBarCode;
	private String partFullCode;
	private String partFullName;
	private Double partTaxMoney;
	private Double partNoTaxMoney;
	private Double partTax;
	private Double partTaxRate;
	private Double partCostTaxMoney;
	private Double partCostNoTaxMoney;
	private Double partCostTax;
	private Double oriPosPrice;
	private Boolean isSaleOrder;
	private Double outTotalCost;
	private Double outTotalCostRecord;

	// Constructors

	/** default constructor */
	public PartOutStockDetail() {
	}

	/** minimal constructor */
	public PartOutStockDetail(String posDetailId) {
		this.posDetailId = posDetailId;
	}

	/** full constructor */
	public PartOutStockDetail(String posDetailId, String posNo,
			Double posQuantity, Double priceRef, Double posPrice,
			Double posAgio, Double costRef, Double cost, Double costNoTax,
			Double carriage, Double carriageNoTax, Double largessPrice,
			String stockId, String partId, String partNo, String partName,
			String partMnemonic, String producingArea, String partType,
			String partProperty, String specModel, String applicableModel,
			String color, String material, String unit, String depositPosition,
			String warehouseName, String remark, Boolean isRecycle,
			Boolean trusteeship, String trusteeshipNo, String taskNo,
			String psoDetailId, String accountId, String accountName,
			Short accountType, String workGroupId, String workGroupName,
			String originDetailId, Double quantitySab, Double quantityRecord,
			Double carriageSab, Double carriageNoTaxSab,
			Double toQuantityRecord, String toStockId, String toWarehouseId,
			String toWarehouseName, Double carriageOut, String ptdNo,
			Short claimPayType, Double claimItemPrice, Double claimPartPrice,
			String claimRejectReason, Short claimStatus, String claimRemark,
			Double claimQuantity, Double claimInQuantity, Timestamp claimTime,
			String claimer, String serialNo, String servicePartId,
			Double priceAgio, String reasonLowPrice, String remarkLowPrice,
			Short approveResultLowPrice, String approverLowPrice,
			Timestamp approveTimeLowPrice, Boolean isAllowIncome,
			Double carriageRef, Double stopQuantityRecord,
			Double quantityBorrowOut, Short warehouseType,
			Double quantityBorrowBack, String oriDetailId, String oriPosNo,
			Double stopToQuantityRecord, Boolean isPackage,
			Double posAgioBefore, Boolean isOverOrderCount, Double orderCount,
			String partBarCode, String partFullCode, String partFullName,
			Double partTaxMoney, Double partNoTaxMoney, Double partTax,
			Double partTaxRate, Double partCostTaxMoney,
			Double partCostNoTaxMoney, Double partCostTax, Double oriPosPrice,
			Boolean isSaleOrder, Double outTotalCost, Double outTotalCostRecord) {
		this.posDetailId = posDetailId;
		this.posNo = posNo;
		this.posQuantity = posQuantity;
		this.priceRef = priceRef;
		this.posPrice = posPrice;
		this.posAgio = posAgio;
		this.costRef = costRef;
		this.cost = cost;
		this.costNoTax = costNoTax;
		this.carriage = carriage;
		this.carriageNoTax = carriageNoTax;
		this.largessPrice = largessPrice;
		this.stockId = stockId;
		this.partId = partId;
		this.partNo = partNo;
		this.partName = partName;
		this.partMnemonic = partMnemonic;
		this.producingArea = producingArea;
		this.partType = partType;
		this.partProperty = partProperty;
		this.specModel = specModel;
		this.applicableModel = applicableModel;
		this.color = color;
		this.material = material;
		this.unit = unit;
		this.depositPosition = depositPosition;
		this.warehouseName = warehouseName;
		this.remark = remark;
		this.isRecycle = isRecycle;
		this.trusteeship = trusteeship;
		this.trusteeshipNo = trusteeshipNo;
		this.taskNo = taskNo;
		this.psoDetailId = psoDetailId;
		this.accountId = accountId;
		this.accountName = accountName;
		this.accountType = accountType;
		this.workGroupId = workGroupId;
		this.workGroupName = workGroupName;
		this.originDetailId = originDetailId;
		this.quantitySab = quantitySab;
		this.quantityRecord = quantityRecord;
		this.carriageSab = carriageSab;
		this.carriageNoTaxSab = carriageNoTaxSab;
		this.toQuantityRecord = toQuantityRecord;
		this.toStockId = toStockId;
		this.toWarehouseId = toWarehouseId;
		this.toWarehouseName = toWarehouseName;
		this.carriageOut = carriageOut;
		this.ptdNo = ptdNo;
		this.claimPayType = claimPayType;
		this.claimItemPrice = claimItemPrice;
		this.claimPartPrice = claimPartPrice;
		this.claimRejectReason = claimRejectReason;
		this.claimStatus = claimStatus;
		this.claimRemark = claimRemark;
		this.claimQuantity = claimQuantity;
		this.claimInQuantity = claimInQuantity;
		this.claimTime = claimTime;
		this.claimer = claimer;
		this.serialNo = serialNo;
		this.servicePartId = servicePartId;
		this.priceAgio = priceAgio;
		this.reasonLowPrice = reasonLowPrice;
		this.remarkLowPrice = remarkLowPrice;
		this.approveResultLowPrice = approveResultLowPrice;
		this.approverLowPrice = approverLowPrice;
		this.approveTimeLowPrice = approveTimeLowPrice;
		this.isAllowIncome = isAllowIncome;
		this.carriageRef = carriageRef;
		this.stopQuantityRecord = stopQuantityRecord;
		this.quantityBorrowOut = quantityBorrowOut;
		this.warehouseType = warehouseType;
		this.quantityBorrowBack = quantityBorrowBack;
		this.oriDetailId = oriDetailId;
		this.oriPosNo = oriPosNo;
		this.stopToQuantityRecord = stopToQuantityRecord;
		this.isPackage = isPackage;
		this.posAgioBefore = posAgioBefore;
		this.isOverOrderCount = isOverOrderCount;
		this.orderCount = orderCount;
		this.partBarCode = partBarCode;
		this.partFullCode = partFullCode;
		this.partFullName = partFullName;
		this.partTaxMoney = partTaxMoney;
		this.partNoTaxMoney = partNoTaxMoney;
		this.partTax = partTax;
		this.partTaxRate = partTaxRate;
		this.partCostTaxMoney = partCostTaxMoney;
		this.partCostNoTaxMoney = partCostNoTaxMoney;
		this.partCostTax = partCostTax;
		this.oriPosPrice = oriPosPrice;
		this.isSaleOrder = isSaleOrder;
		this.outTotalCost = outTotalCost;
		this.outTotalCostRecord = outTotalCostRecord;
	}

	// Property accessors

	public String getPosDetailId() {
		return this.posDetailId;
	}

	public void setPosDetailId(String posDetailId) {
		this.posDetailId = posDetailId;
	}

	public String getPosNo() {
		return this.posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	public Double getPosQuantity() {
		return this.posQuantity;
	}

	public void setPosQuantity(Double posQuantity) {
		this.posQuantity = posQuantity;
	}

	public Double getPriceRef() {
		return this.priceRef;
	}

	public void setPriceRef(Double priceRef) {
		this.priceRef = priceRef;
	}

	public Double getPosPrice() {
		return this.posPrice;
	}

	public void setPosPrice(Double posPrice) {
		this.posPrice = posPrice;
	}

	public Double getPosAgio() {
		return this.posAgio;
	}

	public void setPosAgio(Double posAgio) {
		this.posAgio = posAgio;
	}

	public Double getCostRef() {
		return this.costRef;
	}

	public void setCostRef(Double costRef) {
		this.costRef = costRef;
	}

	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getCostNoTax() {
		return this.costNoTax;
	}

	public void setCostNoTax(Double costNoTax) {
		this.costNoTax = costNoTax;
	}

	public Double getCarriage() {
		return this.carriage;
	}

	public void setCarriage(Double carriage) {
		this.carriage = carriage;
	}

	public Double getCarriageNoTax() {
		return this.carriageNoTax;
	}

	public void setCarriageNoTax(Double carriageNoTax) {
		this.carriageNoTax = carriageNoTax;
	}

	public Double getLargessPrice() {
		return this.largessPrice;
	}

	public void setLargessPrice(Double largessPrice) {
		this.largessPrice = largessPrice;
	}

	public String getStockId() {
		return this.stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
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

	public String getPartName() {
		return this.partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartMnemonic() {
		return this.partMnemonic;
	}

	public void setPartMnemonic(String partMnemonic) {
		this.partMnemonic = partMnemonic;
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

	public String getDepositPosition() {
		return this.depositPosition;
	}

	public void setDepositPosition(String depositPosition) {
		this.depositPosition = depositPosition;
	}

	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsRecycle() {
		return this.isRecycle;
	}

	public void setIsRecycle(Boolean isRecycle) {
		this.isRecycle = isRecycle;
	}

	public Boolean getTrusteeship() {
		return this.trusteeship;
	}

	public void setTrusteeship(Boolean trusteeship) {
		this.trusteeship = trusteeship;
	}

	public String getTrusteeshipNo() {
		return this.trusteeshipNo;
	}

	public void setTrusteeshipNo(String trusteeshipNo) {
		this.trusteeshipNo = trusteeshipNo;
	}

	public String getTaskNo() {
		return this.taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getPsoDetailId() {
		return this.psoDetailId;
	}

	public void setPsoDetailId(String psoDetailId) {
		this.psoDetailId = psoDetailId;
	}

	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Short getAccountType() {
		return this.accountType;
	}

	public void setAccountType(Short accountType) {
		this.accountType = accountType;
	}

	public String getWorkGroupId() {
		return this.workGroupId;
	}

	public void setWorkGroupId(String workGroupId) {
		this.workGroupId = workGroupId;
	}

	public String getWorkGroupName() {
		return this.workGroupName;
	}

	public void setWorkGroupName(String workGroupName) {
		this.workGroupName = workGroupName;
	}

	public String getOriginDetailId() {
		return this.originDetailId;
	}

	public void setOriginDetailId(String originDetailId) {
		this.originDetailId = originDetailId;
	}

	public Double getQuantitySab() {
		return this.quantitySab;
	}

	public void setQuantitySab(Double quantitySab) {
		this.quantitySab = quantitySab;
	}

	public Double getQuantityRecord() {
		return this.quantityRecord;
	}

	public void setQuantityRecord(Double quantityRecord) {
		this.quantityRecord = quantityRecord;
	}

	public Double getCarriageSab() {
		return this.carriageSab;
	}

	public void setCarriageSab(Double carriageSab) {
		this.carriageSab = carriageSab;
	}

	public Double getCarriageNoTaxSab() {
		return this.carriageNoTaxSab;
	}

	public void setCarriageNoTaxSab(Double carriageNoTaxSab) {
		this.carriageNoTaxSab = carriageNoTaxSab;
	}

	public Double getToQuantityRecord() {
		return this.toQuantityRecord;
	}

	public void setToQuantityRecord(Double toQuantityRecord) {
		this.toQuantityRecord = toQuantityRecord;
	}

	public String getToStockId() {
		return this.toStockId;
	}

	public void setToStockId(String toStockId) {
		this.toStockId = toStockId;
	}

	public String getToWarehouseId() {
		return this.toWarehouseId;
	}

	public void setToWarehouseId(String toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
	}

	public String getToWarehouseName() {
		return this.toWarehouseName;
	}

	public void setToWarehouseName(String toWarehouseName) {
		this.toWarehouseName = toWarehouseName;
	}

	public Double getCarriageOut() {
		return this.carriageOut;
	}

	public void setCarriageOut(Double carriageOut) {
		this.carriageOut = carriageOut;
	}

	public String getPtdNo() {
		return this.ptdNo;
	}

	public void setPtdNo(String ptdNo) {
		this.ptdNo = ptdNo;
	}

	public Short getClaimPayType() {
		return this.claimPayType;
	}

	public void setClaimPayType(Short claimPayType) {
		this.claimPayType = claimPayType;
	}

	public Double getClaimItemPrice() {
		return this.claimItemPrice;
	}

	public void setClaimItemPrice(Double claimItemPrice) {
		this.claimItemPrice = claimItemPrice;
	}

	public Double getClaimPartPrice() {
		return this.claimPartPrice;
	}

	public void setClaimPartPrice(Double claimPartPrice) {
		this.claimPartPrice = claimPartPrice;
	}

	public String getClaimRejectReason() {
		return this.claimRejectReason;
	}

	public void setClaimRejectReason(String claimRejectReason) {
		this.claimRejectReason = claimRejectReason;
	}

	public Short getClaimStatus() {
		return this.claimStatus;
	}

	public void setClaimStatus(Short claimStatus) {
		this.claimStatus = claimStatus;
	}

	public String getClaimRemark() {
		return this.claimRemark;
	}

	public void setClaimRemark(String claimRemark) {
		this.claimRemark = claimRemark;
	}

	public Double getClaimQuantity() {
		return this.claimQuantity;
	}

	public void setClaimQuantity(Double claimQuantity) {
		this.claimQuantity = claimQuantity;
	}

	public Double getClaimInQuantity() {
		return this.claimInQuantity;
	}

	public void setClaimInQuantity(Double claimInQuantity) {
		this.claimInQuantity = claimInQuantity;
	}

	public Timestamp getClaimTime() {
		return this.claimTime;
	}

	public void setClaimTime(Timestamp claimTime) {
		this.claimTime = claimTime;
	}

	public String getClaimer() {
		return this.claimer;
	}

	public void setClaimer(String claimer) {
		this.claimer = claimer;
	}

	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getServicePartId() {
		return this.servicePartId;
	}

	public void setServicePartId(String servicePartId) {
		this.servicePartId = servicePartId;
	}

	public Double getPriceAgio() {
		return this.priceAgio;
	}

	public void setPriceAgio(Double priceAgio) {
		this.priceAgio = priceAgio;
	}

	public String getReasonLowPrice() {
		return this.reasonLowPrice;
	}

	public void setReasonLowPrice(String reasonLowPrice) {
		this.reasonLowPrice = reasonLowPrice;
	}

	public String getRemarkLowPrice() {
		return this.remarkLowPrice;
	}

	public void setRemarkLowPrice(String remarkLowPrice) {
		this.remarkLowPrice = remarkLowPrice;
	}

	public Short getApproveResultLowPrice() {
		return this.approveResultLowPrice;
	}

	public void setApproveResultLowPrice(Short approveResultLowPrice) {
		this.approveResultLowPrice = approveResultLowPrice;
	}

	public String getApproverLowPrice() {
		return this.approverLowPrice;
	}

	public void setApproverLowPrice(String approverLowPrice) {
		this.approverLowPrice = approverLowPrice;
	}

	public Timestamp getApproveTimeLowPrice() {
		return this.approveTimeLowPrice;
	}

	public void setApproveTimeLowPrice(Timestamp approveTimeLowPrice) {
		this.approveTimeLowPrice = approveTimeLowPrice;
	}

	public Boolean getIsAllowIncome() {
		return this.isAllowIncome;
	}

	public void setIsAllowIncome(Boolean isAllowIncome) {
		this.isAllowIncome = isAllowIncome;
	}

	public Double getCarriageRef() {
		return this.carriageRef;
	}

	public void setCarriageRef(Double carriageRef) {
		this.carriageRef = carriageRef;
	}

	public Double getStopQuantityRecord() {
		return this.stopQuantityRecord;
	}

	public void setStopQuantityRecord(Double stopQuantityRecord) {
		this.stopQuantityRecord = stopQuantityRecord;
	}

	public Double getQuantityBorrowOut() {
		return this.quantityBorrowOut;
	}

	public void setQuantityBorrowOut(Double quantityBorrowOut) {
		this.quantityBorrowOut = quantityBorrowOut;
	}

	public Short getWarehouseType() {
		return this.warehouseType;
	}

	public void setWarehouseType(Short warehouseType) {
		this.warehouseType = warehouseType;
	}

	public Double getQuantityBorrowBack() {
		return this.quantityBorrowBack;
	}

	public void setQuantityBorrowBack(Double quantityBorrowBack) {
		this.quantityBorrowBack = quantityBorrowBack;
	}

	public String getOriDetailId() {
		return this.oriDetailId;
	}

	public void setOriDetailId(String oriDetailId) {
		this.oriDetailId = oriDetailId;
	}

	public String getOriPosNo() {
		return this.oriPosNo;
	}

	public void setOriPosNo(String oriPosNo) {
		this.oriPosNo = oriPosNo;
	}

	public Double getStopToQuantityRecord() {
		return this.stopToQuantityRecord;
	}

	public void setStopToQuantityRecord(Double stopToQuantityRecord) {
		this.stopToQuantityRecord = stopToQuantityRecord;
	}

	public Boolean getIsPackage() {
		return this.isPackage;
	}

	public void setIsPackage(Boolean isPackage) {
		this.isPackage = isPackage;
	}

	public Double getPosAgioBefore() {
		return this.posAgioBefore;
	}

	public void setPosAgioBefore(Double posAgioBefore) {
		this.posAgioBefore = posAgioBefore;
	}

	public Boolean getIsOverOrderCount() {
		return this.isOverOrderCount;
	}

	public void setIsOverOrderCount(Boolean isOverOrderCount) {
		this.isOverOrderCount = isOverOrderCount;
	}

	public Double getOrderCount() {
		return this.orderCount;
	}

	public void setOrderCount(Double orderCount) {
		this.orderCount = orderCount;
	}

	public String getPartBarCode() {
		return this.partBarCode;
	}

	public void setPartBarCode(String partBarCode) {
		this.partBarCode = partBarCode;
	}

	public String getPartFullCode() {
		return this.partFullCode;
	}

	public void setPartFullCode(String partFullCode) {
		this.partFullCode = partFullCode;
	}

	public String getPartFullName() {
		return this.partFullName;
	}

	public void setPartFullName(String partFullName) {
		this.partFullName = partFullName;
	}

	public Double getPartTaxMoney() {
		return this.partTaxMoney;
	}

	public void setPartTaxMoney(Double partTaxMoney) {
		this.partTaxMoney = partTaxMoney;
	}

	public Double getPartNoTaxMoney() {
		return this.partNoTaxMoney;
	}

	public void setPartNoTaxMoney(Double partNoTaxMoney) {
		this.partNoTaxMoney = partNoTaxMoney;
	}

	public Double getPartTax() {
		return this.partTax;
	}

	public void setPartTax(Double partTax) {
		this.partTax = partTax;
	}

	public Double getPartTaxRate() {
		return this.partTaxRate;
	}

	public void setPartTaxRate(Double partTaxRate) {
		this.partTaxRate = partTaxRate;
	}

	public Double getPartCostTaxMoney() {
		return this.partCostTaxMoney;
	}

	public void setPartCostTaxMoney(Double partCostTaxMoney) {
		this.partCostTaxMoney = partCostTaxMoney;
	}

	public Double getPartCostNoTaxMoney() {
		return this.partCostNoTaxMoney;
	}

	public void setPartCostNoTaxMoney(Double partCostNoTaxMoney) {
		this.partCostNoTaxMoney = partCostNoTaxMoney;
	}

	public Double getPartCostTax() {
		return this.partCostTax;
	}

	public void setPartCostTax(Double partCostTax) {
		this.partCostTax = partCostTax;
	}

	public Double getOriPosPrice() {
		return this.oriPosPrice;
	}

	public void setOriPosPrice(Double oriPosPrice) {
		this.oriPosPrice = oriPosPrice;
	}

	public Boolean getIsSaleOrder() {
		return this.isSaleOrder;
	}

	public void setIsSaleOrder(Boolean isSaleOrder) {
		this.isSaleOrder = isSaleOrder;
	}

	public Double getOutTotalCost() {
		return outTotalCost;
	}

	public void setOutTotalCost(Double outTotalCost) {
		this.outTotalCost = outTotalCost;
	}

	public Double getOutTotalCostRecord() {
		return outTotalCostRecord;
	}

	public void setOutTotalCostRecord(Double outTotalCostRecord) {
		this.outTotalCostRecord = outTotalCostRecord;
	}
}
