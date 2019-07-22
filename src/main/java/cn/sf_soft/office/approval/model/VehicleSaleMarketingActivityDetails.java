package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * VehicleSaleMarketingActivityDetails entity. @author MyEclipse Persistence
 * Tools
 */

public class VehicleSaleMarketingActivityDetails implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8188679415399635507L;
	private String selfId;
	private String documentNo;
	private String customerNo;
	private String customerName;
	private Short customerFlag;
	private Short customerType;
	private String customerSlideNo;
	private String mobile;
	private String area;
	private String vehicleName;
	private String vno;
	private Timestamp futurePurchaseTime;
	private Integer futruePurchaseCount;
	private Short arrivedStatus;
	private String reasonNotArrived;
	private String remark;

	private String customerTypeMean;//客户类型
	private String customerFlagMean;//客户性质
	// Constructors

	/** default constructor */
	public VehicleSaleMarketingActivityDetails() {
	}

	/** minimal constructor */
	public VehicleSaleMarketingActivityDetails(String selfId, String documentNo) {
		this.selfId = selfId;
		this.documentNo = documentNo;
	}

	/** full constructor */
	public VehicleSaleMarketingActivityDetails(String selfId,
			String documentNo, String customerNo, String customerName,
			Short customerFlag, Short customerType, String customerSlideNo,
			String mobile, String area, String vehicleName, String vno,
			Timestamp futurePurchaseTime, Integer futruePurchaseCount,
			Short arrivedStatus, String reasonNotArrived, String remark) {
		this.selfId = selfId;
		this.documentNo = documentNo;
		this.customerNo = customerNo;
		this.customerName = customerName;
		this.customerFlag = customerFlag;
		this.customerType = customerType;
		this.customerSlideNo = customerSlideNo;
		this.mobile = mobile;
		this.area = area;
		this.vehicleName = vehicleName;
		this.vno = vno;
		this.futurePurchaseTime = futurePurchaseTime;
		this.futruePurchaseCount = futruePurchaseCount;
		this.arrivedStatus = arrivedStatus;
		this.reasonNotArrived = reasonNotArrived;
		this.remark = remark;
	}

	// Property accessors

	public String getSelfId() {
		return this.selfId;
	}

	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
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

	public Short getCustomerFlag() {
		return this.customerFlag;
	}

	public void setCustomerFlag(Short customerFlag) {
		this.customerFlag = customerFlag;
	}

	public Short getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(Short customerType) {
		this.customerType = customerType;
	}

	public String getCustomerSlideNo() {
		return this.customerSlideNo;
	}

	public void setCustomerSlideNo(String customerSlideNo) {
		this.customerSlideNo = customerSlideNo;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getVehicleName() {
		return this.vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVno() {
		return this.vno;
	}

	public void setVno(String vno) {
		this.vno = vno;
	}

	public Timestamp getFuturePurchaseTime() {
		return this.futurePurchaseTime;
	}

	public void setFuturePurchaseTime(Timestamp futurePurchaseTime) {
		this.futurePurchaseTime = futurePurchaseTime;
	}

	public Integer getFutruePurchaseCount() {
		return this.futruePurchaseCount;
	}

	public void setFutruePurchaseCount(Integer futruePurchaseCount) {
		this.futruePurchaseCount = futruePurchaseCount;
	}

	public Short getArrivedStatus() {
		return this.arrivedStatus;
	}

	public void setArrivedStatus(Short arrivedStatus) {
		this.arrivedStatus = arrivedStatus;
	}

	public String getReasonNotArrived() {
		return this.reasonNotArrived;
	}

	public void setReasonNotArrived(String reasonNotArrived) {
		this.reasonNotArrived = reasonNotArrived;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "VehicleSaleMarketingActivityDetails [area=" + area
				+ ", arrivedStatus=" + arrivedStatus + ", customerFlag="
				+ customerFlag + ", customerName=" + customerName
				+ ", customerNo=" + customerNo + ", customerSlideNo="
				+ customerSlideNo + ", customerType=" + customerType
				+ ", documentNo=" + documentNo + ", futruePurchaseCount="
				+ futruePurchaseCount + ", futurePurchaseTime="
				+ futurePurchaseTime + ", mobile=" + mobile
				+ ", reasonNotArrived=" + reasonNotArrived + ", remark="
				+ remark + ", selfId=" + selfId + ", vehicleName="
				+ vehicleName + ", vno=" + vno + ", customerTypeMean=" 
				+ customerTypeMean+", customerFlagMean=" + customerFlagMean + "]";
	}

	public void setCustomerTypeMean(String customerTypeMean) {
		this.customerTypeMean = customerTypeMean;
	}

	public String getCustomerTypeMean() {
		return customerTypeMean;
	}

	public void setCustomerFlagMean(String customerFlagMean) {
		this.customerFlagMean = customerFlagMean;
	}

	public String getCustomerFlagMean() {
		return customerFlagMean;
	}
	
}
