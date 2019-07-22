package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 监控车销账-EO
 * @author caigx
 *
 */
public class VehicleDfWriteOffApply extends ApproveDocuments<VehicleDfWriteOffApplyDetail> implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;
	private String writeOffNo;
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
	
	private String modifier; 
	//protected Timestamp modifyTime;
	private String vwaNo;
	

	private Double totalWriteOffAmount; //款项合计
	private Integer totalWriteOffCount; //总台数


	public VehicleDfWriteOffApply() {
	}


	public String getWriteOffNo() {
		return this.writeOffNo;
	}

	public void setWriteOffNo(String writeOffNo) {
		this.writeOffNo = writeOffNo;
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

	@Override
	public String getDocumentNo() {
		return this.documentNo;
	}

	@Override
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	@Override
	public String getStationId() {
		return this.stationId;
	}

	@Override
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	@Override
	public Short getStatus() {
		return this.status;
	}

	@Override
	public void setStatus(Short status) {
		this.status = status;
	}

	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getUserNo() {
		return this.userNo;
	}

	@Override
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Override
	public String getUserName() {
		return this.userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getDepartmentId() {
		return this.departmentId;
	}

	@Override
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	public String getDepartmentNo() {
		return this.departmentNo;
	}

	@Override
	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	@Override
	public String getDepartmentName() {
		return this.departmentName;
	}

	@Override
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public String getSubmitStationId() {
		return this.submitStationId;
	}

	@Override
	public void setSubmitStationId(String submitStationId) {
		this.submitStationId = submitStationId;
	}

	@Override
	public String getSubmitStationName() {
		return this.submitStationName;
	}

	@Override
	public void setSubmitStationName(String submitStationName) {
		this.submitStationName = submitStationName;
	}

	@Override
	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	@Override
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	@Override
	public String getApproverId() {
		return this.approverId;
	}

	@Override
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	@Override
	public String getApproverNo() {
		return this.approverNo;
	}

	@Override
	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}

	@Override
	public String getApproverName() {
		return this.approverName;
	}

	@Override
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	@Override
	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	@Override
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

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Override
	public Timestamp getModifyTime() {
		return modifyTime;
	}

	@Override
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}


	public String getVwaNo() {
		return vwaNo;
	}


	public void setVwaNo(String vwaNo) {
		this.vwaNo = vwaNo;
	}

}
