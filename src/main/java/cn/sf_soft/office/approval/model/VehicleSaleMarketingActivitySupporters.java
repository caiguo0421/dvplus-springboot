package cn.sf_soft.office.approval.model;

/**
 * VehicleSaleMarketingActivitySupporters entity. @author MyEclipse Persistence
 * Tools
 */

public class VehicleSaleMarketingActivitySupporters implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5551138545611239286L;
	private String selfId;
	private String documentNo;
	private String supporterId;
	private String supporterNo;
	private String supporterName;
	private Double supportMoney;
	private String remark;

	// Constructors

	/** default constructor */
	public VehicleSaleMarketingActivitySupporters() {
	}

	/** minimal constructor */
	public VehicleSaleMarketingActivitySupporters(String selfId,
			String documentNo, String supporterName, Double supportMoney) {
		this.selfId = selfId;
		this.documentNo = documentNo;
		this.supporterName = supporterName;
		this.supportMoney = supportMoney;
	}

	/** full constructor */
	public VehicleSaleMarketingActivitySupporters(String selfId,
			String documentNo, String supporterId, String supporterNo,
			String supporterName, Double supportMoney, String remark) {
		this.selfId = selfId;
		this.documentNo = documentNo;
		this.supporterId = supporterId;
		this.supporterNo = supporterNo;
		this.supporterName = supporterName;
		this.supportMoney = supportMoney;
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

	public String getSupporterId() {
		return this.supporterId;
	}

	public void setSupporterId(String supporterId) {
		this.supporterId = supporterId;
	}

	public String getSupporterNo() {
		return this.supporterNo;
	}

	public void setSupporterNo(String supporterNo) {
		this.supporterNo = supporterNo;
	}

	public String getSupporterName() {
		return this.supporterName;
	}

	public void setSupporterName(String supporterName) {
		this.supporterName = supporterName;
	}

	public Double getSupportMoney() {
		return this.supportMoney;
	}

	public void setSupportMoney(Double supportMoney) {
		this.supportMoney = supportMoney;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "VehicleSaleMarketingActivitySupporters [documentNo="
				+ documentNo + ", remark=" + remark + ", selfId=" + selfId
				+ ", supportMoney=" + supportMoney + ", supporterId="
				+ supporterId + ", supporterName=" + supporterName
				+ ", supporterNo=" + supporterNo + "]";
	}
	
}
