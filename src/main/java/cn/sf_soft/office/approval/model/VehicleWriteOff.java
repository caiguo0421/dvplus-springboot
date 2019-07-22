package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 车辆销账申请
 * @author caigx
 *
 */
public class VehicleWriteOff extends ApproveDocuments<VehicleWriteOffDetails> implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;
	private String applyNo;
	private Integer applyQuantity;
	private String dealerCode;
	private String applyDealerCode;
	private Timestamp applyTime;
	private String applyType;
	private String remark;
	private String extendOrderNo;
	private String finance;
	private String invoiceProcess;
	private String orderName;
	private Timestamp actualDeliveryDate;
	private String offsetComment;
	private String zbOffsetComment;
	private String creator;
	private Timestamp createTime;
	private String uploader;
	private Timestamp uploadTime;
	private Short uploadStatus;
//	private String documentNo;
//	private String stationId;
//	private Short status;
//	private String userId;
//	private String userNo;
//	private String userName;
//	private String departmentId;
//	private String departmentNo;
//	private String departmentName;
//	private String submitStationId;
//	private String submitStationName;
//	private Timestamp submitTime;
//	private String approverId;
//	private String approverNo;
//	private String approverName;
//	private Timestamp approveTime;
	private String invoiceNum;
	private String errorCode;
	private String errorMsg;
	private String dfObjId;
	private String crmWriteOffId;
//	private Timestamp modifyTime;
	private String modifier;
	
	private Double totalWriteOffAmount; //款项合计
	private Integer totalWriteOffCount; //总台数
	

	//构造方法
	public VehicleWriteOff() {
		
	}
	

	//属性

	public String getApplyNo() {
		return this.applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public Integer getApplyQuantity() {
		return this.applyQuantity;
	}

	public void setApplyQuantity(Integer applyQuantity) {
		this.applyQuantity = applyQuantity;
	}

	public String getDealerCode() {
		return this.dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getApplyDealerCode() {
		return this.applyDealerCode;
	}

	public void setApplyDealerCode(String applyDealerCode) {
		this.applyDealerCode = applyDealerCode;
	}

	public Timestamp getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyType() {
		return this.applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExtendOrderNo() {
		return this.extendOrderNo;
	}

	public void setExtendOrderNo(String extendOrderNo) {
		this.extendOrderNo = extendOrderNo;
	}

	public String getFinance() {
		return this.finance;
	}

	public void setFinance(String finance) {
		this.finance = finance;
	}

	public String getInvoiceProcess() {
		return this.invoiceProcess;
	}

	public void setInvoiceProcess(String invoiceProcess) {
		this.invoiceProcess = invoiceProcess;
	}

	public String getOrderName() {
		return this.orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Timestamp getActualDeliveryDate() {
		return this.actualDeliveryDate;
	}

	public void setActualDeliveryDate(Timestamp actualDeliveryDate) {
		this.actualDeliveryDate = actualDeliveryDate;
	}

	public String getOffsetComment() {
		return this.offsetComment;
	}

	public void setOffsetComment(String offsetComment) {
		this.offsetComment = offsetComment;
	}

	public String getZbOffsetComment() {
		return this.zbOffsetComment;
	}

	public void setZbOffsetComment(String zbOffsetComment) {
		this.zbOffsetComment = zbOffsetComment;
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

	public Short getUploadStatus() {
		return this.uploadStatus;
	}

	public void setUploadStatus(Short uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
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

	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
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

	public String getInvoiceNum() {
		return this.invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
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

	public String getDfObjId() {
		return this.dfObjId;
	}

	public void setDfObjId(String dfObjId) {
		this.dfObjId = dfObjId;
	}

	public String getCrmWriteOffId() {
		return this.crmWriteOffId;
	}

	public void setCrmWriteOffId(String crmWriteOffId) {
		this.crmWriteOffId = crmWriteOffId;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}


	public Double getTotalWriteOffAmount() {
		return totalWriteOffAmount;
	}


	public void setTotalWriteOffAmount(Double totalWriteOffAmount) {
		this.totalWriteOffAmount = totalWriteOffAmount;
	}


	public Integer getTotalWriteOffCount() {
		return totalWriteOffCount;
	}


	public void setTotalWriteOffCount(Integer totalWriteOffCount) {
		this.totalWriteOffCount = totalWriteOffCount;
	}

}
