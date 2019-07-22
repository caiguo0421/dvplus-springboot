package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 监控车销账 -明细EO
 * @author caigx
 *
 */
public class VehicleDfWriteOffApplyDetail implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private String writeOffDetailId;
	private String writeOffNo;
	private Integer offsetSequence;
	private String vehicleVno;
	private String underpanNo;
	private String offsetComment;
	private String vehicleId;
	private String vehicleVin;
	private Double writeOffAmount;
	private Double writeOffAmountDfs;
	private String documentNo;
	private String inStockDetailId;
	private String invoiceNum;
	private String errorCode;
	private String errorMsg;
	private String dfObjId;
	private String crmWriteOffId;
	private String crmWriteOffDetailId;
	private String detailStatus;
	private Timestamp vehicleOutFactoryTime;

	// Constructors

	public VehicleDfWriteOffApplyDetail() {
	}

	// Property accessors

	public String getWriteOffDetailId() {
		return this.writeOffDetailId;
	}

	public void setWriteOffDetailId(String writeOffDetailId) {
		this.writeOffDetailId = writeOffDetailId;
	}

	public String getWriteOffNo() {
		return this.writeOffNo;
	}

	public void setWriteOffNo(String writeOffNo) {
		this.writeOffNo = writeOffNo;
	}

	public Integer getOffsetSequence() {
		return this.offsetSequence;
	}

	public void setOffsetSequence(Integer offsetSequence) {
		this.offsetSequence = offsetSequence;
	}

	public String getVehicleVno() {
		return this.vehicleVno;
	}

	public void setVehicleVno(String vehicleVno) {
		this.vehicleVno = vehicleVno;
	}

	public String getUnderpanNo() {
		return this.underpanNo;
	}

	public void setUnderpanNo(String underpanNo) {
		this.underpanNo = underpanNo;
	}

	public String getOffsetComment() {
		return this.offsetComment;
	}

	public void setOffsetComment(String offsetComment) {
		this.offsetComment = offsetComment;
	}

	public String getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleVin() {
		return this.vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
	}

	public Double getWriteOffAmount() {
		return this.writeOffAmount;
	}

	public void setWriteOffAmount(Double writeOffAmount) {
		this.writeOffAmount = writeOffAmount;
	}

	public Double getWriteOffAmountDfs() {
		return this.writeOffAmountDfs;
	}

	public void setWriteOffAmountDfs(Double writeOffAmountDfs) {
		this.writeOffAmountDfs = writeOffAmountDfs;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getInStockDetailId() {
		return this.inStockDetailId;
	}

	public void setInStockDetailId(String inStockDetailId) {
		this.inStockDetailId = inStockDetailId;
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

	public String getCrmWriteOffDetailId() {
		return this.crmWriteOffDetailId;
	}

	public void setCrmWriteOffDetailId(String crmWriteOffDetailId) {
		this.crmWriteOffDetailId = crmWriteOffDetailId;
	}

	public String getDetailStatus() {
		return this.detailStatus;
	}

	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}

	public Timestamp getVehicleOutFactoryTime() {
		return vehicleOutFactoryTime;
	}

	public void setVehicleOutFactoryTime(Timestamp vehicleOutFactoryTime) {
		this.vehicleOutFactoryTime = vehicleOutFactoryTime;
	}

}
