package cn.sf_soft.parts.stockborwse.model;

import java.sql.Timestamp;

/**
 * 配件出入库历史信息
 * VwPartIomListId entity. @author MyEclipse Persistence Tools
 */
public class VwPartIomList implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3861992577510879845L;
	private String stockId;
	private Double quantity;
	private Double price;
	private Double costRef;
	private Double cost;
	private Integer isSab;
	private String orderNo;
	private String partTo;
	private String stationId;
	private Timestamp createTime;
	private Timestamp approveTime;
	private Short partIoType;
	private String toFrom;
	private Integer approveStatus;
	private String pioType;
	private Double quantityRecord;
	private String consignmentNo;
	private Integer outType;

	// Constructors

	/** default constructor */
	public VwPartIomList() {
	}

	/** minimal constructor */
	public VwPartIomList(Integer outType) {
		this.outType = outType;
	}

	/** full constructor */
	public VwPartIomList(String stockId, Double quantity, Double price,
			Double costRef, Double cost, Integer isSab, String orderNo,
			String partTo, String stationId, Timestamp createTime,
			Timestamp approveTime, Short partIoType, String toFrom,
			Integer approveStatus, String pioType, Double quantityRecord,
			String consignmentNo, Integer outType) {
		this.stockId = stockId;
		this.quantity = quantity;
		this.price = price;
		this.costRef = costRef;
		this.cost = cost;
		this.isSab = isSab;
		this.orderNo = orderNo;
		this.partTo = partTo;
		this.stationId = stationId;
		this.createTime = createTime;
		this.approveTime = approveTime;
		this.partIoType = partIoType;
		this.toFrom = toFrom;
		this.approveStatus = approveStatus;
		this.pioType = pioType;
		this.quantityRecord = quantityRecord;
		this.consignmentNo = consignmentNo;
		this.outType = outType;
	}

	// Property accessors

	public String getStockId() {
		return this.stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCostRef() {
		return this.costRef;
	}

	public void setCostRef(Double costRef) {
		this.costRef = costRef;
	}

	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getIsSab() {
		return this.isSab;
	}

	public void setIsSab(Integer isSab) {
		this.isSab = isSab;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPartTo() {
		return this.partTo;
	}

	public void setPartTo(String partTo) {
		this.partTo = partTo;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public Short getPartIoType() {
		return this.partIoType;
	}

	public void setPartIoType(Short partIoType) {
		this.partIoType = partIoType;
	}

	public String getToFrom() {
		return this.toFrom;
	}

	public void setToFrom(String toFrom) {
		this.toFrom = toFrom;
	}

	public Integer getApproveStatus() {
		return this.approveStatus;
	}

	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getPioType() {
		return this.pioType;
	}

	public void setPioType(String pioType) {
		this.pioType = pioType;
	}

	public Double getQuantityRecord() {
		return this.quantityRecord;
	}

	public void setQuantityRecord(Double quantityRecord) {
		this.quantityRecord = quantityRecord;
	}

	public String getConsignmentNo() {
		return this.consignmentNo;
	}

	public void setConsignmentNo(String consignmentNo) {
		this.consignmentNo = consignmentNo;
	}

	public Integer getOutType() {
		return this.outType;
	}

	public void setOutType(Integer outType) {
		this.outType = outType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VwPartIomList))
			return false;
		VwPartIomList castOther = (VwPartIomList) other;

		return ((this.getStockId() == castOther.getStockId()) || (this
				.getStockId() != null && castOther.getStockId() != null && this
				.getStockId().equals(castOther.getStockId())))
				&& ((this.getQuantity() == castOther.getQuantity()) || (this
						.getQuantity() != null
						&& castOther.getQuantity() != null && this
						.getQuantity().equals(castOther.getQuantity())))
				&& ((this.getPrice() == castOther.getPrice()) || (this
						.getPrice() != null && castOther.getPrice() != null && this
						.getPrice().equals(castOther.getPrice())))
				&& ((this.getCostRef() == castOther.getCostRef()) || (this
						.getCostRef() != null && castOther.getCostRef() != null && this
						.getCostRef().equals(castOther.getCostRef())))
				&& ((this.getCost() == castOther.getCost()) || (this.getCost() != null
						&& castOther.getCost() != null && this.getCost()
						.equals(castOther.getCost())))
				&& ((this.getIsSab() == castOther.getIsSab()) || (this
						.getIsSab() != null && castOther.getIsSab() != null && this
						.getIsSab().equals(castOther.getIsSab())))
				&& ((this.getOrderNo() == castOther.getOrderNo()) || (this
						.getOrderNo() != null && castOther.getOrderNo() != null && this
						.getOrderNo().equals(castOther.getOrderNo())))
				&& ((this.getPartTo() == castOther.getPartTo()) || (this
						.getPartTo() != null && castOther.getPartTo() != null && this
						.getPartTo().equals(castOther.getPartTo())))
				&& ((this.getStationId() == castOther.getStationId()) || (this
						.getStationId() != null
						&& castOther.getStationId() != null && this
						.getStationId().equals(castOther.getStationId())))
				&& ((this.getCreateTime() == castOther.getCreateTime()) || (this
						.getCreateTime() != null
						&& castOther.getCreateTime() != null && this
						.getCreateTime().equals(castOther.getCreateTime())))
				&& ((this.getApproveTime() == castOther.getApproveTime()) || (this
						.getApproveTime() != null
						&& castOther.getApproveTime() != null && this
						.getApproveTime().equals(castOther.getApproveTime())))
				&& ((this.getPartIoType() == castOther.getPartIoType()) || (this
						.getPartIoType() != null
						&& castOther.getPartIoType() != null && this
						.getPartIoType().equals(castOther.getPartIoType())))
				&& ((this.getToFrom() == castOther.getToFrom()) || (this
						.getToFrom() != null && castOther.getToFrom() != null && this
						.getToFrom().equals(castOther.getToFrom())))
				&& ((this.getApproveStatus() == castOther.getApproveStatus()) || (this
						.getApproveStatus() != null
						&& castOther.getApproveStatus() != null && this
						.getApproveStatus()
						.equals(castOther.getApproveStatus())))
				&& ((this.getPioType() == castOther.getPioType()) || (this
						.getPioType() != null && castOther.getPioType() != null && this
						.getPioType().equals(castOther.getPioType())))
				&& ((this.getQuantityRecord() == castOther.getQuantityRecord()) || (this
						.getQuantityRecord() != null
						&& castOther.getQuantityRecord() != null && this
						.getQuantityRecord().equals(
								castOther.getQuantityRecord())))
				&& ((this.getConsignmentNo() == castOther.getConsignmentNo()) || (this
						.getConsignmentNo() != null
						&& castOther.getConsignmentNo() != null && this
						.getConsignmentNo()
						.equals(castOther.getConsignmentNo())))
				&& ((this.getOutType() == castOther.getOutType()) || (this
						.getOutType() != null && castOther.getOutType() != null && this
						.getOutType().equals(castOther.getOutType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getStockId() == null ? 0 : this.getStockId().hashCode());
		result = 37 * result
				+ (getQuantity() == null ? 0 : this.getQuantity().hashCode());
		result = 37 * result
				+ (getPrice() == null ? 0 : this.getPrice().hashCode());
		result = 37 * result
				+ (getCostRef() == null ? 0 : this.getCostRef().hashCode());
		result = 37 * result
				+ (getCost() == null ? 0 : this.getCost().hashCode());
		result = 37 * result
				+ (getIsSab() == null ? 0 : this.getIsSab().hashCode());
		result = 37 * result
				+ (getOrderNo() == null ? 0 : this.getOrderNo().hashCode());
		result = 37 * result
				+ (getPartTo() == null ? 0 : this.getPartTo().hashCode());
		result = 37 * result
				+ (getStationId() == null ? 0 : this.getStationId().hashCode());
		result = 37
				* result
				+ (getCreateTime() == null ? 0 : this.getCreateTime()
						.hashCode());
		result = 37
				* result
				+ (getApproveTime() == null ? 0 : this.getApproveTime()
						.hashCode());
		result = 37
				* result
				+ (getPartIoType() == null ? 0 : this.getPartIoType()
						.hashCode());
		result = 37 * result
				+ (getToFrom() == null ? 0 : this.getToFrom().hashCode());
		result = 37
				* result
				+ (getApproveStatus() == null ? 0 : this.getApproveStatus()
						.hashCode());
		result = 37 * result
				+ (getPioType() == null ? 0 : this.getPioType().hashCode());
		result = 37
				* result
				+ (getQuantityRecord() == null ? 0 : this.getQuantityRecord()
						.hashCode());
		result = 37
				* result
				+ (getConsignmentNo() == null ? 0 : this.getConsignmentNo()
						.hashCode());
		result = 37 * result
				+ (getOutType() == null ? 0 : this.getOutType().hashCode());
		return result;
	}

}
