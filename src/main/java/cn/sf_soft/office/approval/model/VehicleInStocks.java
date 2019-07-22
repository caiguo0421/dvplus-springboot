package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

public class VehicleInStocks implements java.io.Serializable {

	private static final long serialVersionUID = 4747878474860876856L;
	private String inStockNo;
	private String stationId;
	private Short supplierType;
	private String supplierId;
	private String supplierNo;
	private String supplierName;
	private Short payType;
	private Timestamp inStockTime;
	private Integer inStockCount;
	private Double inStockMoney;
	private Short inStockType;
	private Short originalStockType;
	private String warehouseName;
	private String warehouseId;
	private String identifier;
	private String contractNo;
	private String contractCode;
	private Double inStockCarriage;
	private String carryCompanyId;
	private String carryCompanyNo;
	private String carryCompanyName;
	private Short paymentStatus;
	private Short hypervalent;
	private String carryNo;
	private String comment;
	private String approver;
	private Timestamp approveTime;
	private Short approveStatus;
	private String approveComment;
	private String originalInStockNo;
	private String cancelReason;
	private String sourceStationId;
	private String unitNo;
	private String unitName;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String refOutStocksNo;
	private Timestamp carryFuturePayDate;
	private Double refundmentAmount;
	private String creatorUnitId;
	private String approverUnitId;
	private String trackingNo;
	private String errorCode;
	private String errorMsg;

	public VehicleInStocks() {
	}

	public String getInStockNo() {
		return this.inStockNo;
	}

	public void setInStockNo(String inStockNo) {
		this.inStockNo = inStockNo;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Short getSupplierType() {
		return this.supplierType;
	}

	public void setSupplierType(Short supplierType) {
		this.supplierType = supplierType;
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

	public Short getPayType() {
		return this.payType;
	}

	public void setPayType(Short payType) {
		this.payType = payType;
	}

	public Timestamp getInStockTime() {
		return this.inStockTime;
	}

	public void setInStockTime(Timestamp inStockTime) {
		this.inStockTime = inStockTime;
	}

	public Integer getInStockCount() {
		return this.inStockCount;
	}

	public void setInStockCount(Integer inStockCount) {
		this.inStockCount = inStockCount;
	}

	public Double getInStockMoney() {
		return this.inStockMoney;
	}

	public void setInStockMoney(Double inStockMoney) {
		this.inStockMoney = inStockMoney;
	}

	public Short getInStockType() {
		return this.inStockType;
	}

	public void setInStockType(Short inStockType) {
		this.inStockType = inStockType;
	}

	public Short getOriginalStockType() {
		return this.originalStockType;
	}

	public void setOriginalStockType(Short originalStockType) {
		this.originalStockType = originalStockType;
	}

	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractCode() {
		return this.contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Double getInStockCarriage() {
		return this.inStockCarriage;
	}

	public void setInStockCarriage(Double inStockCarriage) {
		this.inStockCarriage = inStockCarriage;
	}

	public String getCarryCompanyId() {
		return this.carryCompanyId;
	}

	public void setCarryCompanyId(String carryCompanyId) {
		this.carryCompanyId = carryCompanyId;
	}

	public String getCarryCompanyNo() {
		return this.carryCompanyNo;
	}

	public void setCarryCompanyNo(String carryCompanyNo) {
		this.carryCompanyNo = carryCompanyNo;
	}

	public String getCarryCompanyName() {
		return this.carryCompanyName;
	}

	public void setCarryCompanyName(String carryCompanyName) {
		this.carryCompanyName = carryCompanyName;
	}

	public Short getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(Short paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Short getHypervalent() {
		return this.hypervalent;
	}

	public void setHypervalent(Short hypervalent) {
		this.hypervalent = hypervalent;
	}

	public String getCarryNo() {
		return this.carryNo;
	}

	public void setCarryNo(String carryNo) {
		this.carryNo = carryNo;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getApprover() {
		return this.approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public Short getApproveStatus() {
		return this.approveStatus;
	}

	public void setApproveStatus(Short approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getApproveComment() {
		return this.approveComment;
	}

	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}

	public String getOriginalInStockNo() {
		return this.originalInStockNo;
	}

	public void setOriginalInStockNo(String originalInStockNo) {
		this.originalInStockNo = originalInStockNo;
	}

	public String getCancelReason() {
		return this.cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getSourceStationId() {
		return this.sourceStationId;
	}

	public void setSourceStationId(String sourceStationId) {
		this.sourceStationId = sourceStationId;
	}

	public String getUnitNo() {
		return this.unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public String getRefOutStocksNo() {
		return this.refOutStocksNo;
	}

	public void setRefOutStocksNo(String refOutStocksNo) {
		this.refOutStocksNo = refOutStocksNo;
	}

	public Timestamp getCarryFuturePayDate() {
		return this.carryFuturePayDate;
	}

	public void setCarryFuturePayDate(Timestamp carryFuturePayDate) {
		this.carryFuturePayDate = carryFuturePayDate;
	}

	public Double getRefundmentAmount() {
		return this.refundmentAmount;
	}

	public void setRefundmentAmount(Double refundmentAmount) {
		this.refundmentAmount = refundmentAmount;
	}

	public String getCreatorUnitId() {
		return this.creatorUnitId;
	}

	public void setCreatorUnitId(String creatorUnitId) {
		this.creatorUnitId = creatorUnitId;
	}

	public String getApproverUnitId() {
		return this.approverUnitId;
	}

	public void setApproverUnitId(String approverUnitId) {
		this.approverUnitId = approverUnitId;
	}

	public String getTrackingNo() {
		return this.trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
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

}
