package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 上装库存
 * 
 * @author caigx
 *
 */
public class VehicleSuperstructureStocks implements java.io.Serializable {

	private static final long serialVersionUID = 6592608985781362101L;
	private String superstructureId;
	private String superstructureNo;
	private String snoId;
	private String superstructureSno;
	private String superstructureName;
	private String superstructureType;
	private String superstructureModel;
	private String superstructureColor;
	private Timestamp outFactoryTime;
	private String stationId;
	private String warehouseId;
	private String warehouseName;
	private Double superstructureCost;
	private Double superstructureCarriage;
	private String carryNo;
	private String inStockNo;
	private Timestamp inStockTime;
	private Short inStockType;
	private String inComment;
	private String invoiceNo;
	private Timestamp futurePayDate;
	private String supplierId;
	private String supplierName;
	private String purchaseContractNo;
	private String purchaseContractCode;
	private Short status;
	private Short installStatus;
	private String customerId;
	private String customerName;
	private String seller;
	private String outStockNo;
	private Timestamp outStockTime;
	private Short outStockType;
	private Double superstructurePrice;
	private Integer superstructureQuantity;
	private String remark;
	private String conversionNo;

	public VehicleSuperstructureStocks() {
	}

	public VehicleSuperstructureStocks(String superstructureId, String superstructureNo, String snoId,
			String superstructureSno, String superstructureName, String superstructureType, String superstructureModel,
			String superstructureColor, Timestamp outFactoryTime, String stationId, String warehouseId,
			String warehouseName, Double superstructureCost, Double superstructureCarriage, String carryNo,
			String inStockNo, Timestamp inStockTime, Short inStockType, String inComment, String invoiceNo,
			Timestamp futurePayDate, String supplierId, String supplierName, String purchaseContractNo,
			String purchaseContractCode, Short status, Short installStatus, String customerId, String customerName,
			String seller, String outStockNo, Timestamp outStockTime, Short outStockType, Double superstructurePrice,
			Integer superstructureQuantity, String remark, String conversionNo) {
		this.superstructureId = superstructureId;
		this.superstructureNo = superstructureNo;
		this.snoId = snoId;
		this.superstructureSno = superstructureSno;
		this.superstructureName = superstructureName;
		this.superstructureType = superstructureType;
		this.superstructureModel = superstructureModel;
		this.superstructureColor = superstructureColor;
		this.outFactoryTime = outFactoryTime;
		this.stationId = stationId;
		this.warehouseId = warehouseId;
		this.warehouseName = warehouseName;
		this.superstructureCost = superstructureCost;
		this.superstructureCarriage = superstructureCarriage;
		this.carryNo = carryNo;
		this.inStockNo = inStockNo;
		this.inStockTime = inStockTime;
		this.inStockType = inStockType;
		this.inComment = inComment;
		this.invoiceNo = invoiceNo;
		this.futurePayDate = futurePayDate;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.purchaseContractNo = purchaseContractNo;
		this.purchaseContractCode = purchaseContractCode;
		this.status = status;
		this.installStatus = installStatus;
		this.customerId = customerId;
		this.customerName = customerName;
		this.seller = seller;
		this.outStockNo = outStockNo;
		this.outStockTime = outStockTime;
		this.outStockType = outStockType;
		this.superstructurePrice = superstructurePrice;
		this.superstructureQuantity = superstructureQuantity;
		this.remark = remark;
		this.conversionNo = conversionNo;
	}

	public String getSuperstructureId() {
		return this.superstructureId;
	}

	public void setSuperstructureId(String superstructureId) {
		this.superstructureId = superstructureId;
	}

	public String getSuperstructureNo() {
		return this.superstructureNo;
	}

	public void setSuperstructureNo(String superstructureNo) {
		this.superstructureNo = superstructureNo;
	}

	public String getSnoId() {
		return this.snoId;
	}

	public void setSnoId(String snoId) {
		this.snoId = snoId;
	}

	public String getSuperstructureSno() {
		return this.superstructureSno;
	}

	public void setSuperstructureSno(String superstructureSno) {
		this.superstructureSno = superstructureSno;
	}

	public String getSuperstructureName() {
		return this.superstructureName;
	}

	public void setSuperstructureName(String superstructureName) {
		this.superstructureName = superstructureName;
	}

	public String getSuperstructureType() {
		return this.superstructureType;
	}

	public void setSuperstructureType(String superstructureType) {
		this.superstructureType = superstructureType;
	}

	public String getSuperstructureModel() {
		return this.superstructureModel;
	}

	public void setSuperstructureModel(String superstructureModel) {
		this.superstructureModel = superstructureModel;
	}

	public String getSuperstructureColor() {
		return this.superstructureColor;
	}

	public void setSuperstructureColor(String superstructureColor) {
		this.superstructureColor = superstructureColor;
	}

	public Timestamp getOutFactoryTime() {
		return this.outFactoryTime;
	}

	public void setOutFactoryTime(Timestamp outFactoryTime) {
		this.outFactoryTime = outFactoryTime;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
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

	public Double getSuperstructureCost() {
		return this.superstructureCost;
	}

	public void setSuperstructureCost(Double superstructureCost) {
		this.superstructureCost = superstructureCost;
	}

	public Double getSuperstructureCarriage() {
		return this.superstructureCarriage;
	}

	public void setSuperstructureCarriage(Double superstructureCarriage) {
		this.superstructureCarriage = superstructureCarriage;
	}

	public String getCarryNo() {
		return this.carryNo;
	}

	public void setCarryNo(String carryNo) {
		this.carryNo = carryNo;
	}

	public String getInStockNo() {
		return this.inStockNo;
	}

	public void setInStockNo(String inStockNo) {
		this.inStockNo = inStockNo;
	}

	public Timestamp getInStockTime() {
		return this.inStockTime;
	}

	public void setInStockTime(Timestamp inStockTime) {
		this.inStockTime = inStockTime;
	}

	public Short getInStockType() {
		return this.inStockType;
	}

	public void setInStockType(Short inStockType) {
		this.inStockType = inStockType;
	}

	public String getInComment() {
		return this.inComment;
	}

	public void setInComment(String inComment) {
		this.inComment = inComment;
	}

	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Timestamp getFuturePayDate() {
		return this.futurePayDate;
	}

	public void setFuturePayDate(Timestamp futurePayDate) {
		this.futurePayDate = futurePayDate;
	}

	public String getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPurchaseContractNo() {
		return this.purchaseContractNo;
	}

	public void setPurchaseContractNo(String purchaseContractNo) {
		this.purchaseContractNo = purchaseContractNo;
	}

	public String getPurchaseContractCode() {
		return this.purchaseContractCode;
	}

	public void setPurchaseContractCode(String purchaseContractCode) {
		this.purchaseContractCode = purchaseContractCode;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getInstallStatus() {
		return this.installStatus;
	}

	public void setInstallStatus(Short installStatus) {
		this.installStatus = installStatus;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSeller() {
		return this.seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getOutStockNo() {
		return this.outStockNo;
	}

	public void setOutStockNo(String outStockNo) {
		this.outStockNo = outStockNo;
	}

	public Timestamp getOutStockTime() {
		return this.outStockTime;
	}

	public void setOutStockTime(Timestamp outStockTime) {
		this.outStockTime = outStockTime;
	}

	public Short getOutStockType() {
		return this.outStockType;
	}

	public void setOutStockType(Short outStockType) {
		this.outStockType = outStockType;
	}

	public Double getSuperstructurePrice() {
		return this.superstructurePrice;
	}

	public void setSuperstructurePrice(Double superstructurePrice) {
		this.superstructurePrice = superstructurePrice;
	}

	public Integer getSuperstructureQuantity() {
		return this.superstructureQuantity;
	}

	public void setSuperstructureQuantity(Integer superstructureQuantity) {
		this.superstructureQuantity = superstructureQuantity;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getConversionNo() {
		return this.conversionNo;
	}

	public void setConversionNo(String conversionNo) {
		this.conversionNo = conversionNo;
	}

}
