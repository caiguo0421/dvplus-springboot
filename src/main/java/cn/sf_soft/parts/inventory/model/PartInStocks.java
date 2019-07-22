package cn.sf_soft.parts.inventory.model;

import java.sql.Timestamp;

/**
 * 配件入库
 * @author cw
 * @date 2014-4-17 上午11:23:14
 */
public class PartInStocks implements java.io.Serializable {

	// Fields

	private String pisNo;
	private Short pisType;
	private String partFrom;
	private String carryCompanyId;
	private String carryCompanyNo;
	private String carryCompanyName;
	private String consignmentMode;
	private String consignmentNo;
	private String purchaseNo;
	private String identifier;
	private String purchaser;
	private Short payType;
	private Short paymentStatusSupplier;
	private Short paymentStatusConsign;
	private String supplierId;
	private String supplierNo;
	private String supplierName;
	private Double tax;
	private Double taxPrice;
	private Double taxCarriage;
	private Double restCarriage;
	private String remark;
	private String originNo;
	private Boolean isSab;
	private Boolean sabCarriage;
	private String sabReason;
	private Double pisPrice;
	private Double pisCarriage;
	private Double sabPrice;
	private String stationId;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String approver;
	private Short approveStatus;
	private Timestamp approveTime;
	private String checker;
	private Timestamp checkTime;
	private Short checkStatus;
	private String checkRemark;
	private Double posPrice;
	private String linkman;
	private String mobile;
	private String fromStationId;
	private String confirmer;
	private Timestamp confirmTime;
	private String confirmRemark;
	private Short carriageType;
	private Double carriageOut;
	private String posNo;
	private Short supplierType;
	private String supplierTypeName;
	private Timestamp partRepayTime;
	private Timestamp carriageRepayTime;
	private String conversionNo;
	private String creatorNo;
	private String creatorUnitNo;
	private String creatorUnitName;
	private String approverNo;
	private String approverUnitNo;
	private String approverUnitName;
	private Double totTaxMoney;
	private Double totNoTaxMoney;
	private Double totTax;
	private String identifierNo;

	private String approverUnitId;
	private String approverId;
	// Constructors

	/** default constructor */
	public PartInStocks() {
	}

	/** minimal constructor */
	public PartInStocks(String pisNo) {
		this.pisNo = pisNo;
	}

	/** full constructor */
	public PartInStocks(String pisNo, Short pisType, String partFrom,
			String carryCompanyId, String carryCompanyNo,
			String carryCompanyName, String consignmentMode,
			String consignmentNo, String purchaseNo, String identifier,
			String purchaser, Short payType, Short paymentStatusSupplier,
			Short paymentStatusConsign, String supplierId, String supplierNo,
			String supplierName, Double tax, Double taxPrice,
			Double taxCarriage, Double restCarriage, String remark,
			String originNo, Boolean isSab, Boolean sabCarriage,
			String sabReason, Double pisPrice, Double pisCarriage,
			Double sabPrice, String stationId, String creator,
			Timestamp createTime, String modifier, Timestamp modifyTime,
			String approver, Short approveStatus, Timestamp approveTime,
			String checker, Timestamp checkTime, Short checkStatus,
			String checkRemark, Double posPrice, String linkman, String mobile,
			String fromStationId, String confirmer, Timestamp confirmTime,
			String confirmRemark, Short carriageType, Double carriageOut,
			String posNo, Short supplierType, String supplierTypeName,
			Timestamp partRepayTime, Timestamp carriageRepayTime,
			String conversionNo, String creatorNo, String creatorUnitNo,
			String creatorUnitName, String approverNo, String approverUnitNo,
			String approverUnitName, Double totTaxMoney, Double totNoTaxMoney,
			Double totTax, String identifierNo) {
		this.pisNo = pisNo;
		this.pisType = pisType;
		this.partFrom = partFrom;
		this.carryCompanyId = carryCompanyId;
		this.carryCompanyNo = carryCompanyNo;
		this.carryCompanyName = carryCompanyName;
		this.consignmentMode = consignmentMode;
		this.consignmentNo = consignmentNo;
		this.purchaseNo = purchaseNo;
		this.identifier = identifier;
		this.purchaser = purchaser;
		this.payType = payType;
		this.paymentStatusSupplier = paymentStatusSupplier;
		this.paymentStatusConsign = paymentStatusConsign;
		this.supplierId = supplierId;
		this.supplierNo = supplierNo;
		this.supplierName = supplierName;
		this.tax = tax;
		this.taxPrice = taxPrice;
		this.taxCarriage = taxCarriage;
		this.restCarriage = restCarriage;
		this.remark = remark;
		this.originNo = originNo;
		this.isSab = isSab;
		this.sabCarriage = sabCarriage;
		this.sabReason = sabReason;
		this.pisPrice = pisPrice;
		this.pisCarriage = pisCarriage;
		this.sabPrice = sabPrice;
		this.stationId = stationId;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.approver = approver;
		this.approveStatus = approveStatus;
		this.approveTime = approveTime;
		this.checker = checker;
		this.checkTime = checkTime;
		this.checkStatus = checkStatus;
		this.checkRemark = checkRemark;
		this.posPrice = posPrice;
		this.linkman = linkman;
		this.mobile = mobile;
		this.fromStationId = fromStationId;
		this.confirmer = confirmer;
		this.confirmTime = confirmTime;
		this.confirmRemark = confirmRemark;
		this.carriageType = carriageType;
		this.carriageOut = carriageOut;
		this.posNo = posNo;
		this.supplierType = supplierType;
		this.supplierTypeName = supplierTypeName;
		this.partRepayTime = partRepayTime;
		this.carriageRepayTime = carriageRepayTime;
		this.conversionNo = conversionNo;
		this.creatorNo = creatorNo;
		this.creatorUnitNo = creatorUnitNo;
		this.creatorUnitName = creatorUnitName;
		this.approverNo = approverNo;
		this.approverUnitNo = approverUnitNo;
		this.approverUnitName = approverUnitName;
		this.totTaxMoney = totTaxMoney;
		this.totNoTaxMoney = totNoTaxMoney;
		this.totTax = totTax;
		this.identifierNo = identifierNo;
	}

	// Property accessors

	public String getPisNo() {
		return this.pisNo;
	}

	public void setPisNo(String pisNo) {
		this.pisNo = pisNo;
	}

	public Short getPisType() {
		return this.pisType;
	}

	public void setPisType(Short pisType) {
		this.pisType = pisType;
	}

	public String getPartFrom() {
		return this.partFrom;
	}

	public void setPartFrom(String partFrom) {
		this.partFrom = partFrom;
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

	public String getConsignmentMode() {
		return this.consignmentMode;
	}

	public void setConsignmentMode(String consignmentMode) {
		this.consignmentMode = consignmentMode;
	}

	public String getConsignmentNo() {
		return this.consignmentNo;
	}

	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	public String getPurchaseNo() {
		return this.purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getPurchaser() {
		return this.purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public Short getPayType() {
		return this.payType;
	}

	public void setPayType(Short payType) {
		this.payType = payType;
	}

	public Short getPaymentStatusSupplier() {
		return this.paymentStatusSupplier;
	}

	public void setPaymentStatusSupplier(Short paymentStatusSupplier) {
		this.paymentStatusSupplier = paymentStatusSupplier;
	}

	public Short getPaymentStatusConsign() {
		return this.paymentStatusConsign;
	}

	public void setPaymentStatusConsign(Short paymentStatusConsign) {
		this.paymentStatusConsign = paymentStatusConsign;
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

	public Double getTax() {
		return this.tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTaxPrice() {
		return this.taxPrice;
	}

	public void setTaxPrice(Double taxPrice) {
		this.taxPrice = taxPrice;
	}

	public Double getTaxCarriage() {
		return this.taxCarriage;
	}

	public void setTaxCarriage(Double taxCarriage) {
		this.taxCarriage = taxCarriage;
	}

	public Double getRestCarriage() {
		return this.restCarriage;
	}

	public void setRestCarriage(Double restCarriage) {
		this.restCarriage = restCarriage;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOriginNo() {
		return this.originNo;
	}

	public void setOriginNo(String originNo) {
		this.originNo = originNo;
	}

	public Boolean getIsSab() {
		return this.isSab;
	}

	public void setIsSab(Boolean isSab) {
		this.isSab = isSab;
	}

	public Boolean getSabCarriage() {
		return this.sabCarriage;
	}

	public void setSabCarriage(Boolean sabCarriage) {
		this.sabCarriage = sabCarriage;
	}

	public String getSabReason() {
		return this.sabReason;
	}

	public void setSabReason(String sabReason) {
		this.sabReason = sabReason;
	}

	public Double getPisPrice() {
		return this.pisPrice;
	}

	public void setPisPrice(Double pisPrice) {
		this.pisPrice = pisPrice;
	}

	public Double getPisCarriage() {
		return this.pisCarriage;
	}

	public void setPisCarriage(Double pisCarriage) {
		this.pisCarriage = pisCarriage;
	}

	public Double getSabPrice() {
		return this.sabPrice;
	}

	public void setSabPrice(Double sabPrice) {
		this.sabPrice = sabPrice;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
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

	public String getChecker() {
		return this.checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public Timestamp getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	public Short getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckRemark() {
		return this.checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public Double getPosPrice() {
		return this.posPrice;
	}

	public void setPosPrice(Double posPrice) {
		this.posPrice = posPrice;
	}

	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFromStationId() {
		return this.fromStationId;
	}

	public void setFromStationId(String fromStationId) {
		this.fromStationId = fromStationId;
	}

	public String getConfirmer() {
		return this.confirmer;
	}

	public void setConfirmer(String confirmer) {
		this.confirmer = confirmer;
	}

	public Timestamp getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getConfirmRemark() {
		return this.confirmRemark;
	}

	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}

	public Short getCarriageType() {
		return this.carriageType;
	}

	public void setCarriageType(Short carriageType) {
		this.carriageType = carriageType;
	}

	public Double getCarriageOut() {
		return this.carriageOut;
	}

	public void setCarriageOut(Double carriageOut) {
		this.carriageOut = carriageOut;
	}

	public String getPosNo() {
		return this.posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	public Short getSupplierType() {
		return this.supplierType;
	}

	public void setSupplierType(Short supplierType) {
		this.supplierType = supplierType;
	}

	public String getSupplierTypeName() {
		return this.supplierTypeName;
	}

	public void setSupplierTypeName(String supplierTypeName) {
		this.supplierTypeName = supplierTypeName;
	}

	public Timestamp getPartRepayTime() {
		return this.partRepayTime;
	}

	public void setPartRepayTime(Timestamp partRepayTime) {
		this.partRepayTime = partRepayTime;
	}

	public Timestamp getCarriageRepayTime() {
		return this.carriageRepayTime;
	}

	public void setCarriageRepayTime(Timestamp carriageRepayTime) {
		this.carriageRepayTime = carriageRepayTime;
	}

	public String getConversionNo() {
		return this.conversionNo;
	}

	public void setConversionNo(String conversionNo) {
		this.conversionNo = conversionNo;
	}

	public String getCreatorNo() {
		return this.creatorNo;
	}

	public void setCreatorNo(String creatorNo) {
		this.creatorNo = creatorNo;
	}

	public String getCreatorUnitNo() {
		return this.creatorUnitNo;
	}

	public void setCreatorUnitNo(String creatorUnitNo) {
		this.creatorUnitNo = creatorUnitNo;
	}

	public String getCreatorUnitName() {
		return this.creatorUnitName;
	}

	public void setCreatorUnitName(String creatorUnitName) {
		this.creatorUnitName = creatorUnitName;
	}

	public String getApproverNo() {
		return this.approverNo;
	}

	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}

	public String getApproverUnitNo() {
		return this.approverUnitNo;
	}

	public void setApproverUnitNo(String approverUnitNo) {
		this.approverUnitNo = approverUnitNo;
	}

	public String getApproverUnitName() {
		return this.approverUnitName;
	}

	public void setApproverUnitName(String approverUnitName) {
		this.approverUnitName = approverUnitName;
	}

	public Double getTotTaxMoney() {
		return this.totTaxMoney;
	}

	public void setTotTaxMoney(Double totTaxMoney) {
		this.totTaxMoney = totTaxMoney;
	}

	public Double getTotNoTaxMoney() {
		return this.totNoTaxMoney;
	}

	public void setTotNoTaxMoney(Double totNoTaxMoney) {
		this.totNoTaxMoney = totNoTaxMoney;
	}

	public Double getTotTax() {
		return this.totTax;
	}

	public void setTotTax(Double totTax) {
		this.totTax = totTax;
	}

	public String getIdentifierNo() {
		return this.identifierNo;
	}

	public void setIdentifierNo(String identifierNo) {
		this.identifierNo = identifierNo;
	}

	public String getApproverUnitId() {
		return approverUnitId;
	}

	public void setApproverUnitId(String approverUnitId) {
		this.approverUnitId = approverUnitId;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
}
