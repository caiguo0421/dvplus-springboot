package cn.sf_soft.parts.inventory.model;

import java.sql.Timestamp;

/**
 * 配件出库
 * @author cw
 * @date 2014-4-17 上午11:23:41
 */
public class PartOutStocks implements java.io.Serializable {

	// Fields

	private String posNo;
	private String oriPosNo;
	private Short posType;
	private Short paymentStatus;
	private String priceSystem;
	private String seller;
	private String linkman;
	private String mobile;
	private String remark;
	private Double posPrice;
	private Double factPrice;
	private Double sabPrice;
	private Double cost;
	private Double carriage;
	private Double largessPrice;
	private String partTo;
	private Short payType;
	private String customerId;
	private String customerNo;
	private String customerName;
	private String packagesNo;
	private String packagesVehiclesId;
	private String supplierId;
	private String supplierNo;
	private String supplierName;
	private String departName;
	private String receiver;
	private String taskNo;
	private String contractNo;
	private String contractDetailId;
	private Boolean isSab;
	private String sabReason;
	private String originNo;
	private String saleOrderNo;
	private String stationId;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;
	private String approver;
	private Short approveStatus;
	private Timestamp approveTime;
	private String checkRemark;
	private String checkMan;
	private Timestamp checkTime;
	private Short checkStatus;
	private String toStationId;
	private String toWarehouseId;
	private String confirmer;
	private Timestamp confirmTime;
	private String confirmRemark;
	private String pisNo;
	private Short pisType;
	private String moveType;
	private Short claimType;
	private Short carriageType;
	private Double carriageOut;
	private Timestamp futureDate;
	private String departId;
	private String customerTypeName;
	private Short customerType;
	private Timestamp stopTime;
	private String stoper;
	private Timestamp partRepayTime;
	private Timestamp carriageRepayTime;
	private Double commission;
	private String commissionUserId;
	private String commissionUserNo;
	private String commissionUserName;
	private Boolean isCommissionIn;
	private Short takeType;
	private Double carriageReal;
	private String remarkHandle;
	private Short handleStatus;
	private Boolean isContainCarriage;
	private Boolean isPayCarriage;
	private Boolean isCollection;
	private String conversionNo;
	private Double taxRate;
	private String creatorNo;
	private String creatorUnitNo;
	private String creatorUnitName;
	private String approverId;
	private String approverNo;
	private String approverUnitId;
	private String approverUnitNo;
	private String approverUnitName;
	private Double totTaxMoney;
	private Double totNoTaxMoney;
	private Double totTax;
	private String departNo;
	private String sellerNo;
	private String sellerDepartNo;
	private String sellerDepartName;
	private String receiverNo;


	// Constructors

	/** default constructor */
	public PartOutStocks() {
	}

	/** minimal constructor */
	public PartOutStocks(String posNo) {
		this.posNo = posNo;
	}

	/** full constructor */
	public PartOutStocks(String posNo, String oriPosNo, Short posType,
			Short paymentStatus, String priceSystem, String seller,
			String linkman, String mobile, String remark, Double posPrice,
			Double factPrice, Double sabPrice, Double cost, Double carriage,
			Double largessPrice, String partTo, Short payType,
			String customerId, String customerNo, String customerName,
			String packagesNo, String packagesVehiclesId, String supplierId,
			String supplierNo, String supplierName, String departName,
			String receiver, String taskNo, String contractNo,
			String contractDetailId, Boolean isSab, String sabReason,
			String originNo, String saleOrderNo, String stationId,
			String creator, Timestamp createTime, String modifier,
			Timestamp modifyTime, String approver, Short approveStatus,
			Timestamp approveTime, String checkRemark, String checkMan,
			Timestamp checkTime, Short checkStatus, String toStationId,
			String toWarehouseId, String confirmer, Timestamp confirmTime,
			String confirmRemark, String pisNo, Short pisType, String moveType,
			Short claimType, Short carriageType, Double carriageOut,
			Timestamp futureDate, String departId, String customerTypeName,
			Short customerType, Timestamp stopTime, String stoper,
			Timestamp partRepayTime, Timestamp carriageRepayTime,
			Double commission, String commissionUserId,
			String commissionUserNo, String commissionUserName,
			Boolean isCommissionIn, Short takeType, Double carriageReal,
			String remarkHandle, Short handleStatus, Boolean isContainCarriage,
			Boolean isPayCarriage, Boolean isCollection, String conversionNo,
			Double taxRate, String creatorNo, String creatorUnitNo,
			String creatorUnitName, String approverNo, String approverUnitNo,
			String approverUnitName, Double totTaxMoney, Double totNoTaxMoney,
			Double totTax, String departNo, String sellerNo,
			String sellerDepartNo, String sellerDepartName, String receiverNo) {
		this.posNo = posNo;
		this.oriPosNo = oriPosNo;
		this.posType = posType;
		this.paymentStatus = paymentStatus;
		this.priceSystem = priceSystem;
		this.seller = seller;
		this.linkman = linkman;
		this.mobile = mobile;
		this.remark = remark;
		this.posPrice = posPrice;
		this.factPrice = factPrice;
		this.sabPrice = sabPrice;
		this.cost = cost;
		this.carriage = carriage;
		this.largessPrice = largessPrice;
		this.partTo = partTo;
		this.payType = payType;
		this.customerId = customerId;
		this.customerNo = customerNo;
		this.customerName = customerName;
		this.packagesNo = packagesNo;
		this.packagesVehiclesId = packagesVehiclesId;
		this.supplierId = supplierId;
		this.supplierNo = supplierNo;
		this.supplierName = supplierName;
		this.departName = departName;
		this.receiver = receiver;
		this.taskNo = taskNo;
		this.contractNo = contractNo;
		this.contractDetailId = contractDetailId;
		this.isSab = isSab;
		this.sabReason = sabReason;
		this.originNo = originNo;
		this.saleOrderNo = saleOrderNo;
		this.stationId = stationId;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
		this.approver = approver;
		this.approveStatus = approveStatus;
		this.approveTime = approveTime;
		this.checkRemark = checkRemark;
		this.checkMan = checkMan;
		this.checkTime = checkTime;
		this.checkStatus = checkStatus;
		this.toStationId = toStationId;
		this.toWarehouseId = toWarehouseId;
		this.confirmer = confirmer;
		this.confirmTime = confirmTime;
		this.confirmRemark = confirmRemark;
		this.pisNo = pisNo;
		this.pisType = pisType;
		this.moveType = moveType;
		this.claimType = claimType;
		this.carriageType = carriageType;
		this.carriageOut = carriageOut;
		this.futureDate = futureDate;
		this.departId = departId;
		this.customerTypeName = customerTypeName;
		this.customerType = customerType;
		this.stopTime = stopTime;
		this.stoper = stoper;
		this.partRepayTime = partRepayTime;
		this.carriageRepayTime = carriageRepayTime;
		this.commission = commission;
		this.commissionUserId = commissionUserId;
		this.commissionUserNo = commissionUserNo;
		this.commissionUserName = commissionUserName;
		this.isCommissionIn = isCommissionIn;
		this.takeType = takeType;
		this.carriageReal = carriageReal;
		this.remarkHandle = remarkHandle;
		this.handleStatus = handleStatus;
		this.isContainCarriage = isContainCarriage;
		this.isPayCarriage = isPayCarriage;
		this.isCollection = isCollection;
		this.conversionNo = conversionNo;
		this.taxRate = taxRate;
		this.creatorNo = creatorNo;
		this.creatorUnitNo = creatorUnitNo;
		this.creatorUnitName = creatorUnitName;
		this.approverNo = approverNo;
		this.approverUnitNo = approverUnitNo;
		this.approverUnitName = approverUnitName;
		this.totTaxMoney = totTaxMoney;
		this.totNoTaxMoney = totNoTaxMoney;
		this.totTax = totTax;
		this.departNo = departNo;
		this.sellerNo = sellerNo;
		this.sellerDepartNo = sellerDepartNo;
		this.sellerDepartName = sellerDepartName;
		this.receiverNo = receiverNo;
	}

	// Property accessors

	public String getPosNo() {
		return this.posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	public String getOriPosNo() {
		return this.oriPosNo;
	}

	public void setOriPosNo(String oriPosNo) {
		this.oriPosNo = oriPosNo;
	}

	public Short getPosType() {
		return this.posType;
	}

	public void setPosType(Short posType) {
		this.posType = posType;
	}

	public Short getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(Short paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPriceSystem() {
		return this.priceSystem;
	}

	public void setPriceSystem(String priceSystem) {
		this.priceSystem = priceSystem;
	}

	public String getSeller() {
		return this.seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getPosPrice() {
		return this.posPrice;
	}

	public void setPosPrice(Double posPrice) {
		this.posPrice = posPrice;
	}

	public Double getFactPrice() {
		return this.factPrice;
	}

	public void setFactPrice(Double factPrice) {
		this.factPrice = factPrice;
	}

	public Double getSabPrice() {
		return this.sabPrice;
	}

	public void setSabPrice(Double sabPrice) {
		this.sabPrice = sabPrice;
	}

	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getCarriage() {
		return this.carriage;
	}

	public void setCarriage(Double carriage) {
		this.carriage = carriage;
	}

	public Double getLargessPrice() {
		return this.largessPrice;
	}

	public void setLargessPrice(Double largessPrice) {
		this.largessPrice = largessPrice;
	}

	public String getPartTo() {
		return this.partTo;
	}

	public void setPartTo(String partTo) {
		this.partTo = partTo;
	}

	public Short getPayType() {
		return this.payType;
	}

	public void setPayType(Short payType) {
		this.payType = payType;
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

	public String getPackagesNo() {
		return this.packagesNo;
	}

	public void setPackagesNo(String packagesNo) {
		this.packagesNo = packagesNo;
	}

	public String getPackagesVehiclesId() {
		return this.packagesVehiclesId;
	}

	public void setPackagesVehiclesId(String packagesVehiclesId) {
		this.packagesVehiclesId = packagesVehiclesId;
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

	public String getDepartName() {
		return this.departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getTaskNo() {
		return this.taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractDetailId() {
		return this.contractDetailId;
	}

	public void setContractDetailId(String contractDetailId) {
		this.contractDetailId = contractDetailId;
	}

	public Boolean getIsSab() {
		return this.isSab;
	}

	public void setIsSab(Boolean isSab) {
		this.isSab = isSab;
	}

	public String getSabReason() {
		return this.sabReason;
	}

	public void setSabReason(String sabReason) {
		this.sabReason = sabReason;
	}

	public String getOriginNo() {
		return this.originNo;
	}

	public void setOriginNo(String originNo) {
		this.originNo = originNo;
	}

	public String getSaleOrderNo() {
		return this.saleOrderNo;
	}

	public void setSaleOrderNo(String saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
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

	public String getCheckRemark() {
		return this.checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public String getCheckMan() {
		return this.checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
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

	public String getToStationId() {
		return this.toStationId;
	}

	public void setToStationId(String toStationId) {
		this.toStationId = toStationId;
	}

	public String getToWarehouseId() {
		return this.toWarehouseId;
	}

	public void setToWarehouseId(String toWarehouseId) {
		this.toWarehouseId = toWarehouseId;
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

	public String getMoveType() {
		return this.moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public Short getClaimType() {
		return this.claimType;
	}

	public void setClaimType(Short claimType) {
		this.claimType = claimType;
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

	public Timestamp getFutureDate() {
		return this.futureDate;
	}

	public void setFutureDate(Timestamp futureDate) {
		this.futureDate = futureDate;
	}

	public String getDepartId() {
		return this.departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getCustomerTypeName() {
		return this.customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	public Short getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(Short customerType) {
		this.customerType = customerType;
	}

	public Timestamp getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(Timestamp stopTime) {
		this.stopTime = stopTime;
	}

	public String getStoper() {
		return this.stoper;
	}

	public void setStoper(String stoper) {
		this.stoper = stoper;
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

	public Double getCommission() {
		return this.commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getCommissionUserId() {
		return this.commissionUserId;
	}

	public void setCommissionUserId(String commissionUserId) {
		this.commissionUserId = commissionUserId;
	}

	public String getCommissionUserNo() {
		return this.commissionUserNo;
	}

	public void setCommissionUserNo(String commissionUserNo) {
		this.commissionUserNo = commissionUserNo;
	}

	public String getCommissionUserName() {
		return this.commissionUserName;
	}

	public void setCommissionUserName(String commissionUserName) {
		this.commissionUserName = commissionUserName;
	}

	public Boolean getIsCommissionIn() {
		return this.isCommissionIn;
	}

	public void setIsCommissionIn(Boolean isCommissionIn) {
		this.isCommissionIn = isCommissionIn;
	}

	public Short getTakeType() {
		return this.takeType;
	}

	public void setTakeType(Short takeType) {
		this.takeType = takeType;
	}

	public Double getCarriageReal() {
		return this.carriageReal;
	}

	public void setCarriageReal(Double carriageReal) {
		this.carriageReal = carriageReal;
	}

	public String getRemarkHandle() {
		return this.remarkHandle;
	}

	public void setRemarkHandle(String remarkHandle) {
		this.remarkHandle = remarkHandle;
	}

	public Short getHandleStatus() {
		return this.handleStatus;
	}

	public void setHandleStatus(Short handleStatus) {
		this.handleStatus = handleStatus;
	}

	public Boolean getIsContainCarriage() {
		return this.isContainCarriage;
	}

	public void setIsContainCarriage(Boolean isContainCarriage) {
		this.isContainCarriage = isContainCarriage;
	}

	public Boolean getIsPayCarriage() {
		return this.isPayCarriage;
	}

	public void setIsPayCarriage(Boolean isPayCarriage) {
		this.isPayCarriage = isPayCarriage;
	}

	public Boolean getIsCollection() {
		return this.isCollection;
	}

	public void setIsCollection(Boolean isCollection) {
		this.isCollection = isCollection;
	}

	public String getConversionNo() {
		return this.conversionNo;
	}

	public void setConversionNo(String conversionNo) {
		this.conversionNo = conversionNo;
	}

	public Double getTaxRate() {
		return this.taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
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

	public String getDepartNo() {
		return this.departNo;
	}

	public void setDepartNo(String departNo) {
		this.departNo = departNo;
	}

	public String getSellerNo() {
		return this.sellerNo;
	}

	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
	}

	public String getSellerDepartNo() {
		return this.sellerDepartNo;
	}

	public void setSellerDepartNo(String sellerDepartNo) {
		this.sellerDepartNo = sellerDepartNo;
	}

	public String getSellerDepartName() {
		return this.sellerDepartName;
	}

	public void setSellerDepartName(String sellerDepartName) {
		this.sellerDepartName = sellerDepartName;
	}

	public String getReceiverNo() {
		return this.receiverNo;
	}

	public void setReceiverNo(String receiverNo) {
		this.receiverNo = receiverNo;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getApproverUnitId() {
		return approverUnitId;
	}

	public void setApproverUnitId(String approverUnitId) {
		this.approverUnitId = approverUnitId;
	}
}
