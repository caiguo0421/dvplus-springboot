package cn.sf_soft.office.approval.model;

import cn.sf_soft.support.LogProperty;
import cn.sf_soft.support.LogPropertyType;
import org.hibernate.annotations.Entity;

import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


/**
 * 车辆销售合同
 *
 * @author caigx
 */
@Entity
@Table(name = "vehicle_sale_contracts", schema = "dbo")
public class VehicleSaleContracts extends ApproveDocuments implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String contractNo;
	private String contractCode;
	// private String stationId;
	@LogProperty(type = LogPropertyType.STRING, dateFormat = "yyyy-MM-dd")
	private Timestamp signTime;
	@LogProperty(type = LogPropertyType.STRING, dateFormat = "yyyy-MM-dd")
	private Timestamp planFinishTime;
	private Timestamp realFinishTime;
	/**
	 * 销售模式
	 */
	private Short saleMode;
	@LogProperty(name = "saleMode")
	private String saleModeMeaning; //扩展属性
	@LogProperty()
	private String seller;
	private Double contractMoney;
	private Double contractCost;
	private Integer contractQuantity;
	private Double arrivedMoney;
	private Integer arrivedQuantity;
	@LogProperty(name = "deposit", type = LogPropertyType.BIG_DECIMAL, nullDefault = "0")
	private Double deposit;
	private Double vehicleAmt;
	private Double presentAmt;
	private Double insuranceAmt;
	private Double conversionAmt;
	private Double expenseAmt;
	private Double commissionAmt;
	private Short contractStatus;
	private String contractComment;
	@LogProperty()
	private String deliveryLocus;
	private String customerId;
	private String customerNo;
	@LogProperty()
	private String customerName;
	private String cusotmerSex;
	private Timestamp customerBirthday;
	@LogProperty()
	private String customerCertificateType;
	@LogProperty()
	private String customerCertificateNo;
	private String customerAddress;
	private String customerEducation;
	private String customerOccupation;
	private String customerArea;
	private String customerPhone;
	@LogProperty()
	private String customerMobile;
	private String customerFax;
	private String customerEmail;
	private String customerPostcode;
	@LogProperty(name = "profession")
	private String customerProfession;
	private String linkman;
	/**
	 * 付款方式
	 */
	private Short payMode;
	private String payTerm;
	private Short clubCallBackResult;
	private Short clubCallBackFinalResult;
	private String clubCallBackRemark;
	private Boolean clubCallBackFlag;
	private String clubCallBackDealRemark;
	private String clubCallBackAbstract;
	private String clubCallBackAbstractFinal;
	private Short clubCallBackSuccess;
	private String clubCallBackFailReason;
	private String unitNo;
	private String unitName;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	// private Timestamp modifyTime;
	private Timestamp backTime;
	private String belongToSupplierId;
	private String belongToSupplierNo;
	private String belongToSupplierName;
	private String visitorId;
	private String visitorNo;
	@LogProperty(name = "visitorName")
	private String visitorName; //扩展属性
	private String commissionMerchantId;
	private String commissionMerchantNo;
	private String commissionMerchantName;
	private String introducerId;
	private String introducer;
	private String introducerMobile;
	private Integer varyCount;
	private String callBackHandleDepartment;
	private String callBackEmpId;
	private String callBackEmpName;
	private Double firstPayLoanAmount;
	private String version;
	private String sellerId;
	private String customerProvince;
	private String customerCity;
	private Boolean isSecondhand;
	/**
	 * 购车方式
	 */
	private Short buyType;
	private Boolean isChanging;

	private String buyTypeName;//付款方式中文名称

	private List<VehicleSaleContractDetail> details;


	private Timestamp appointedTime; //约定回款日期


	/**
	 * 图片
	 */
	private String pics;

	/**
	 * 附件
	 */
	private String fileUrls;

	/**
	 * 简明配置
	 */
	private String conciseConfigure;

	/**
	 * 加装项目
	 */
	private String conversionConfigure;


	/**
	 * 上装项目
	 */
	private String superstructureConfigure;


	/**
	 * 车辆概要-手机的合同列表要显示
	 */
	private String vehicleGroupInfo;

	/**
	 * 客户端类型,记录新增的操作的客户端类型（PC H5）
	 */
	private String clientType;

	public VehicleSaleContracts() {
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

//	public String getStationId() {
//		return this.stationId;
//	}
//
//	public void setStationId(String stationId) {
//		this.stationId = stationId;
//	}

	public Timestamp getSignTime() {
		return this.signTime;
	}

	public void setSignTime(Timestamp signTime) {
		this.signTime = signTime;
	}

	public Timestamp getPlanFinishTime() {
		return this.planFinishTime;
	}

	public void setPlanFinishTime(Timestamp planFinishTime) {
		this.planFinishTime = planFinishTime;
	}

	public Timestamp getRealFinishTime() {
		return this.realFinishTime;
	}

	public void setRealFinishTime(Timestamp realFinishTime) {
		this.realFinishTime = realFinishTime;
	}

	public Short getSaleMode() {
		return this.saleMode;
	}

	public void setSaleMode(Short saleMode) {
		this.saleMode = saleMode;
	}

	public String getSeller() {
		return this.seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Double getContractMoney() {
		return this.contractMoney;
	}

	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}

	public Double getContractCost() {
		return this.contractCost;
	}

	public void setContractCost(Double contractCost) {
		this.contractCost = contractCost;
	}

	public Integer getContractQuantity() {
		return this.contractQuantity;
	}

	public void setContractQuantity(Integer contractQuantity) {
		this.contractQuantity = contractQuantity;
	}

	public Double getArrivedMoney() {
		return this.arrivedMoney;
	}

	public void setArrivedMoney(Double arrivedMoney) {
		this.arrivedMoney = arrivedMoney;
	}

	public Integer getArrivedQuantity() {
		return this.arrivedQuantity;
	}

	public void setArrivedQuantity(Integer arrivedQuantity) {
		this.arrivedQuantity = arrivedQuantity;
	}

	public Double getDeposit() {
		return this.deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Double getVehicleAmt() {
		return this.vehicleAmt;
	}

	public void setVehicleAmt(Double vehicleAmt) {
		this.vehicleAmt = vehicleAmt;
	}

	public Double getPresentAmt() {
		return this.presentAmt;
	}

	public void setPresentAmt(Double presentAmt) {
		this.presentAmt = presentAmt;
	}

	public Double getInsuranceAmt() {
		return this.insuranceAmt;
	}

	public void setInsuranceAmt(Double insuranceAmt) {
		this.insuranceAmt = insuranceAmt;
	}

	public Double getConversionAmt() {
		return this.conversionAmt;
	}

	public void setConversionAmt(Double conversionAmt) {
		this.conversionAmt = conversionAmt;
	}

	public Double getExpenseAmt() {
		return this.expenseAmt;
	}

	public void setExpenseAmt(Double expenseAmt) {
		this.expenseAmt = expenseAmt;
	}

	public Double getCommissionAmt() {
		return this.commissionAmt;
	}

	public void setCommissionAmt(Double commissionAmt) {
		this.commissionAmt = commissionAmt;
	}

	public Short getContractStatus() {
		return this.contractStatus;
	}

	public void setContractStatus(Short contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getContractComment() {
		return this.contractComment;
	}

	public void setContractComment(String contractComment) {
		this.contractComment = contractComment;
	}

	public String getDeliveryLocus() {
		return this.deliveryLocus;
	}

	public void setDeliveryLocus(String deliveryLocus) {
		this.deliveryLocus = deliveryLocus;
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

	public String getCusotmerSex() {
		return this.cusotmerSex;
	}

	public void setCusotmerSex(String cusotmerSex) {
		this.cusotmerSex = cusotmerSex;
	}

	public Timestamp getCustomerBirthday() {
		return this.customerBirthday;
	}

	public void setCustomerBirthday(Timestamp customerBirthday) {
		this.customerBirthday = customerBirthday;
	}

	public String getCustomerCertificateType() {
		return this.customerCertificateType;
	}

	public void setCustomerCertificateType(String customerCertificateType) {
		this.customerCertificateType = customerCertificateType;
	}

	public String getCustomerCertificateNo() {
		return this.customerCertificateNo;
	}

	public void setCustomerCertificateNo(String customerCertificateNo) {
		this.customerCertificateNo = customerCertificateNo;
	}

	public String getCustomerAddress() {
		return this.customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerEducation() {
		return this.customerEducation;
	}

	public void setCustomerEducation(String customerEducation) {
		this.customerEducation = customerEducation;
	}

	public String getCustomerOccupation() {
		return this.customerOccupation;
	}

	public void setCustomerOccupation(String customerOccupation) {
		this.customerOccupation = customerOccupation;
	}

	public String getCustomerArea() {
		return this.customerArea;
	}

	public void setCustomerArea(String customerArea) {
		this.customerArea = customerArea;
	}

	public String getCustomerPhone() {
		return this.customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerMobile() {
		return this.customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerFax() {
		return this.customerFax;
	}

	public void setCustomerFax(String customerFax) {
		this.customerFax = customerFax;
	}

	public String getCustomerEmail() {
		return this.customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPostcode() {
		return this.customerPostcode;
	}

	public void setCustomerPostcode(String customerPostcode) {
		this.customerPostcode = customerPostcode;
	}

	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public Short getPayMode() {
		return this.payMode;
	}

	public void setPayMode(Short payMode) {
		this.payMode = payMode;
	}

	public String getPayTerm() {
		return this.payTerm;
	}

	public void setPayTerm(String payTerm) {
		this.payTerm = payTerm;
	}

	public Short getClubCallBackResult() {
		return this.clubCallBackResult;
	}

	public void setClubCallBackResult(Short clubCallBackResult) {
		this.clubCallBackResult = clubCallBackResult;
	}

	public Short getClubCallBackFinalResult() {
		return this.clubCallBackFinalResult;
	}

	public void setClubCallBackFinalResult(Short clubCallBackFinalResult) {
		this.clubCallBackFinalResult = clubCallBackFinalResult;
	}

	public String getClubCallBackRemark() {
		return this.clubCallBackRemark;
	}

	public void setClubCallBackRemark(String clubCallBackRemark) {
		this.clubCallBackRemark = clubCallBackRemark;
	}

	public Boolean getClubCallBackFlag() {
		return this.clubCallBackFlag;
	}

	public void setClubCallBackFlag(Boolean clubCallBackFlag) {
		this.clubCallBackFlag = clubCallBackFlag;
	}

	public String getClubCallBackDealRemark() {
		return this.clubCallBackDealRemark;
	}

	public void setClubCallBackDealRemark(String clubCallBackDealRemark) {
		this.clubCallBackDealRemark = clubCallBackDealRemark;
	}

	public String getClubCallBackAbstract() {
		return this.clubCallBackAbstract;
	}

	public void setClubCallBackAbstract(String clubCallBackAbstract) {
		this.clubCallBackAbstract = clubCallBackAbstract;
	}

	public String getClubCallBackAbstractFinal() {
		return this.clubCallBackAbstractFinal;
	}

	public void setClubCallBackAbstractFinal(String clubCallBackAbstractFinal) {
		this.clubCallBackAbstractFinal = clubCallBackAbstractFinal;
	}

	public Short getClubCallBackSuccess() {
		return this.clubCallBackSuccess;
	}

	public void setClubCallBackSuccess(Short clubCallBackSuccess) {
		this.clubCallBackSuccess = clubCallBackSuccess;
	}

	public String getClubCallBackFailReason() {
		return this.clubCallBackFailReason;
	}

	public void setClubCallBackFailReason(String clubCallBackFailReason) {
		this.clubCallBackFailReason = clubCallBackFailReason;
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

//	public Timestamp getModifyTime() {
//		return this.modifyTime;
//	}
//
//	public void setModifyTime(Timestamp modifyTime) {
//		this.modifyTime = modifyTime;
//	}

	public Timestamp getBackTime() {
		return this.backTime;
	}

	public void setBackTime(Timestamp backTime) {
		this.backTime = backTime;
	}

	public String getBelongToSupplierId() {
		return this.belongToSupplierId;
	}

	public void setBelongToSupplierId(String belongToSupplierId) {
		this.belongToSupplierId = belongToSupplierId;
	}

	public String getBelongToSupplierNo() {
		return this.belongToSupplierNo;
	}

	public void setBelongToSupplierNo(String belongToSupplierNo) {
		this.belongToSupplierNo = belongToSupplierNo;
	}

	public String getBelongToSupplierName() {
		return this.belongToSupplierName;
	}

	public void setBelongToSupplierName(String belongToSupplierName) {
		this.belongToSupplierName = belongToSupplierName;
	}

	public String getVisitorNo() {
		return this.visitorNo;
	}

	public void setVisitorNo(String visitorNo) {
		this.visitorNo = visitorNo;
	}

	public String getCommissionMerchantId() {
		return this.commissionMerchantId;
	}

	public void setCommissionMerchantId(String commissionMerchantId) {
		this.commissionMerchantId = commissionMerchantId;
	}

	public String getCommissionMerchantNo() {
		return this.commissionMerchantNo;
	}

	public void setCommissionMerchantNo(String commissionMerchantNo) {
		this.commissionMerchantNo = commissionMerchantNo;
	}

	public String getCommissionMerchantName() {
		return this.commissionMerchantName;
	}

	public void setCommissionMerchantName(String commissionMerchantName) {
		this.commissionMerchantName = commissionMerchantName;
	}

	public String getIntroducerId() {
		return this.introducerId;
	}

	public void setIntroducerId(String introducerId) {
		this.introducerId = introducerId;
	}

	public String getIntroducer() {
		return this.introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}

	public String getIntroducerMobile() {
		return this.introducerMobile;
	}

	public void setIntroducerMobile(String introducerMobile) {
		this.introducerMobile = introducerMobile;
	}

	public Integer getVaryCount() {
		return this.varyCount;
	}

	public void setVaryCount(Integer varyCount) {
		this.varyCount = varyCount;
	}

	public String getCallBackHandleDepartment() {
		return this.callBackHandleDepartment;
	}

	public void setCallBackHandleDepartment(String callBackHandleDepartment) {
		this.callBackHandleDepartment = callBackHandleDepartment;
	}

	public String getCallBackEmpId() {
		return this.callBackEmpId;
	}

	public void setCallBackEmpId(String callBackEmpId) {
		this.callBackEmpId = callBackEmpId;
	}

	public String getCallBackEmpName() {
		return this.callBackEmpName;
	}

	public void setCallBackEmpName(String callBackEmpName) {
		this.callBackEmpName = callBackEmpName;
	}

	public Double getFirstPayLoanAmount() {
		return this.firstPayLoanAmount;
	}

	public void setFirstPayLoanAmount(Double firstPayLoanAmount) {
		this.firstPayLoanAmount = firstPayLoanAmount;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getCustomerProvince() {
		return this.customerProvince;
	}

	public void setCustomerProvince(String customerProvince) {
		this.customerProvince = customerProvince;
	}

	public String getCustomerCity() {
		return this.customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public Boolean getIsSecondhand() {
		return this.isSecondhand;
	}

	public void setIsSecondhand(Boolean isSecondhand) {
		this.isSecondhand = isSecondhand;
	}

	public Short getBuyType() {
		return this.buyType;
	}

	public void setBuyType(Short buyType) {
		this.buyType = buyType;
	}

	public String getBuyTypeName() {
		return buyTypeName;
	}

	public void setBuyTypeName(String buyTypeName) {
		this.buyTypeName = buyTypeName;
	}

	public List<VehicleSaleContractDetail> getDetails() {
		return details;
	}

	public void setDetails(List<VehicleSaleContractDetail> details) {
		this.details = details;
	}

	public Boolean getIsChanging() {
		return isChanging;
	}

	public void setIsChanging(Boolean isChanging) {
		this.isChanging = isChanging;
	}

	public String getCustomerProfession() {
		return customerProfession;
	}

	public void setCustomerProfession(String customerProfession) {
		this.customerProfession = customerProfession;
	}


	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public String getFileUrls() {
		return fileUrls;
	}

	public void setFileUrls(String fileUrls) {
		this.fileUrls = fileUrls;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getSaleModeMeaning() {
		return saleModeMeaning;
	}

	public void setSaleModeMeaning(String saleModeMeaning) {
		this.saleModeMeaning = saleModeMeaning;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public Timestamp getAppointedTime() {
		return appointedTime;
	}

	public void setAppointedTime(Timestamp appointedTime) {
		this.appointedTime = appointedTime;
	}

	public String getConciseConfigure() {
		return conciseConfigure;
	}

	public void setConciseConfigure(String conciseConfigure) {
		this.conciseConfigure = conciseConfigure;
	}

	public String getConversionConfigure() {
		return conversionConfigure;
	}

	public void setConversionConfigure(String conversionConfigure) {
		this.conversionConfigure = conversionConfigure;
	}

	public String getSuperstructureConfigure() {
		return superstructureConfigure;
	}

	public void setSuperstructureConfigure(String superstructureConfigure) {
		this.superstructureConfigure = superstructureConfigure;
	}

	public String getVehicleGroupInfo() {
		return vehicleGroupInfo;
	}

	public void setVehicleGroupInfo(String vehicleGroupInfo) {
		this.vehicleGroupInfo = vehicleGroupInfo;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
}
