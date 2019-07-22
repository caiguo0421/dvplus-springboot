package cn.sf_soft.office.approval.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 车辆销售合同-精品
 * 
 * @author caigx
 *
 */
public class VehicleSaleContractPresent implements java.io.Serializable {

	private static final long serialVersionUID = -1019929549315641378L;
	private String saleContractPresentId;
	private String contractDetailId;
	private BigDecimal planQuantity;
	private BigDecimal getQuantity;
	private BigDecimal posPrice;
	private BigDecimal posAgio;
	private String stockId;
	private String depositPosition;
	private String partName;
	private String partNo;
	private String partMnemonic;
	private String producingArea;
	private String partType;
	private String specModel;
	private String applicableModel;
	private String unit;
	private String warehouseName;
	private BigDecimal costRecord;
	private BigDecimal carriageRecord;
	private Short abortStatus;
	private BigDecimal income;
	private String remark;
	private Timestamp modifyTime;
	private String contractNo;
	private String presentGroupId;

	public VehicleSaleContractPresent() {
	}



	// Property accessors

	public String getSaleContractPresentId() {
		return this.saleContractPresentId;
	}

	public void setSaleContractPresentId(String saleContractPresentId) {
		this.saleContractPresentId = saleContractPresentId;
	}

	public String getContractDetailId() {
		return this.contractDetailId;
	}

	public void setContractDetailId(String contractDetailId) {
		this.contractDetailId = contractDetailId;
	}

	public BigDecimal getPlanQuantity() {
		return this.planQuantity;
	}

	public void setPlanQuantity(BigDecimal planQuantity) {
		this.planQuantity = planQuantity;
	}

	public BigDecimal getGetQuantity() {
		return this.getQuantity;
	}

	public void setGetQuantity(BigDecimal getQuantity) {
		this.getQuantity = getQuantity;
	}

	public BigDecimal getPosPrice() {
		return this.posPrice;
	}

	public void setPosPrice(BigDecimal posPrice) {
		this.posPrice = posPrice;
	}

	public BigDecimal getPosAgio() {
		return this.posAgio;
	}

	public void setPosAgio(BigDecimal posAgio) {
		this.posAgio = posAgio;
	}

	public String getStockId() {
		return this.stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getDepositPosition() {
		return this.depositPosition;
	}

	public void setDepositPosition(String depositPosition) {
		this.depositPosition = depositPosition;
	}

	public String getPartName() {
		return this.partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartNo() {
		return this.partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
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

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getWarehouseName() {
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public BigDecimal getCostRecord() {
		return this.costRecord;
	}

	public void setCostRecord(BigDecimal costRecord) {
		this.costRecord = costRecord;
	}

	public BigDecimal getCarriageRecord() {
		return this.carriageRecord;
	}

	public void setCarriageRecord(BigDecimal carriageRecord) {
		this.carriageRecord = carriageRecord;
	}

	public Short getAbortStatus() {
		return this.abortStatus;
	}

	public void setAbortStatus(Short abortStatus) {
		this.abortStatus = abortStatus;
	}

	public BigDecimal getIncome() {
		return this.income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getPresentGroupId() {
		return presentGroupId;
	}

	public void setPresentGroupId(String presentGroupId) {
		this.presentGroupId = presentGroupId;
	}
}
