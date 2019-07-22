package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;
import java.util.List;

/**
 * 车辆改装
 * 
 * @author caigx
 *
 */
public class VehicleConversion extends ApproveDocuments<VehicleConversionDetail> implements java.io.Serializable {

	private static final long serialVersionUID = -6315990871994688094L;
	private String conversionNo;
	private String vehicleVin;
	private String vnoId;
	private String vehicleSalesCode;
	private String vehicleVno;
	private String vehicleName;
	private String vehicleStrain;
	private Short vehicleKind;
	private Double conversionAmt;
	private Double inConversionAmt;
	private Double inConversionAmtReal;
	private Double outConversionAmt;
	private Short payType;
	//private Short status;
	private Boolean isExists;
	//private String stationId;
	private Timestamp submitDate;
	private Timestamp endDate;
	//private String approverName;
	//private Timestamp approveTime;
	private String remark;
	private String confirmMan;
	private Timestamp confirmTime;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	//private Timestamp modifyTime;
	private String vehicleId;
	private String warehouseId;
	private Boolean isSecondhand;
	//private String documentNo;
	//private String userId;
	//private String userNo;
	//private String userName;
	//private String departmentId;
	//private String departmentNo;
	//private String departmentName;
	//private String submitStationId;
	//private String submitStationName;
	//private String approverId;
	//private String approverNo;
	//private Timestamp submitTime;
	
	private String vscdId;//车辆合同明细Id
	
	
	private String isExistsMeaning; //改装性质  库存改装, 合同改装
	private Double totalInstallAmount; //加装总金额
	private Double totalRemoveAmount; //拆装总金额
	private List<VehicleSuperstructureRemoveAndInstalls> installItems; //加装明细
	private List<VehicleSuperstructureRemoveAndInstalls> removeItems; //拆装明细

	private String masterNo;

	public VehicleConversion() {
	}

	

	public String getConversionNo() {
		return this.conversionNo;
	}

	public void setConversionNo(String conversionNo) {
		this.conversionNo = conversionNo;
	}

	public String getVehicleVin() {
		return this.vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
	}

	public String getVnoId() {
		return this.vnoId;
	}

	public void setVnoId(String vnoId) {
		this.vnoId = vnoId;
	}

	public String getVehicleSalesCode() {
		return this.vehicleSalesCode;
	}

	public void setVehicleSalesCode(String vehicleSalesCode) {
		this.vehicleSalesCode = vehicleSalesCode;
	}

	public String getVehicleVno() {
		return this.vehicleVno;
	}

	public void setVehicleVno(String vehicleVno) {
		this.vehicleVno = vehicleVno;
	}

	public String getVehicleName() {
		return this.vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehicleStrain() {
		return this.vehicleStrain;
	}

	public void setVehicleStrain(String vehicleStrain) {
		this.vehicleStrain = vehicleStrain;
	}

	public Short getVehicleKind() {
		return this.vehicleKind;
	}

	public void setVehicleKind(Short vehicleKind) {
		this.vehicleKind = vehicleKind;
	}

	public Double getConversionAmt() {
		return this.conversionAmt;
	}

	public void setConversionAmt(Double conversionAmt) {
		this.conversionAmt = conversionAmt;
	}

	public Double getInConversionAmt() {
		return this.inConversionAmt;
	}

	public void setInConversionAmt(Double inConversionAmt) {
		this.inConversionAmt = inConversionAmt;
	}

	public Double getInConversionAmtReal() {
		return this.inConversionAmtReal;
	}

	public void setInConversionAmtReal(Double inConversionAmtReal) {
		this.inConversionAmtReal = inConversionAmtReal;
	}

	public Double getOutConversionAmt() {
		return this.outConversionAmt;
	}

	public void setOutConversionAmt(Double outConversionAmt) {
		this.outConversionAmt = outConversionAmt;
	}

	public Short getPayType() {
		return this.payType;
	}

	public void setPayType(Short payType) {
		this.payType = payType;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Boolean getIsExists() {
		return this.isExists;
	}

	public void setIsExists(Boolean isExists) {
		this.isExists = isExists;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Timestamp getSubmitDate() {
		return this.submitDate;
	}

	public void setSubmitDate(Timestamp submitDate) {
		this.submitDate = submitDate;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getApproverName() {
		return this.approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getConfirmMan() {
		return this.confirmMan;
	}

	public void setConfirmMan(String confirmMan) {
		this.confirmMan = confirmMan;
	}

	public Timestamp getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
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

	public String getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Boolean getIsSecondhand() {
		return this.isSecondhand;
	}

	public void setIsSecondhand(Boolean isSecondhand) {
		this.isSecondhand = isSecondhand;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentNo() {
		return this.departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getSubmitStationId() {
		return this.submitStationId;
	}

	public void setSubmitStationId(String submitStationId) {
		this.submitStationId = submitStationId;
	}

	public String getSubmitStationName() {
		return this.submitStationName;
	}

	public void setSubmitStationName(String submitStationName) {
		this.submitStationName = submitStationName;
	}

	public String getApproverId() {
		return this.approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getApproverNo() {
		return this.approverNo;
	}

	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}

	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}



	public Double getTotalInstallAmount() {
		return totalInstallAmount;
	}



	public void setTotalInstallAmount(Double totalInstallAmount) {
		this.totalInstallAmount = totalInstallAmount;
	}



	public Double getTotalRemoveAmount() {
		return totalRemoveAmount;
	}



	public void setTotalRemoveAmount(Double totalRemoveAmount) {
		this.totalRemoveAmount = totalRemoveAmount;
	}



	public List<VehicleSuperstructureRemoveAndInstalls> getInstallItems() {
		return installItems;
	}



	public void setInstallItems(List<VehicleSuperstructureRemoveAndInstalls> installItems) {
		this.installItems = installItems;
	}



	public List<VehicleSuperstructureRemoveAndInstalls> getRemoveItems() {
		return removeItems;
	}



	public void setRemoveItems(List<VehicleSuperstructureRemoveAndInstalls> removeItems) {
		this.removeItems = removeItems;
	}



	public String getIsExistsMeaning() {
		return isExistsMeaning;
	}



	public void setIsExistsMeaning(String isExistsMeaning) {
		this.isExistsMeaning = isExistsMeaning;
	}



	public String getVscdId() {
		return vscdId;
	}



	public void setVscdId(String vscdId) {
		this.vscdId = vscdId;
	}

	public String getMasterNo() {
		return masterNo;
	}

	public void setMasterNo(String masterNo) {
		this.masterNo = masterNo;
	}

}
