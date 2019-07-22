package cn.sf_soft.vehicle.customer.model;

/**
 * 
 * @Title: 销售分组
 * @date 2013-12-11 下午02:19:51 
 * @author cw
 */

public class BaseSaleWorkgroupDetail implements java.io.Serializable {

	// Fields

	private String bswdId;
	private String userId;
	private String swId;
	private Boolean isGroupLeader;
	private String remark;

	// Constructors

	/** default constructor */
	public BaseSaleWorkgroupDetail() {
	}

	/** minimal constructor */
	public BaseSaleWorkgroupDetail(String bswdId) {
		this.bswdId = bswdId;
	}

	/** full constructor */
	public BaseSaleWorkgroupDetail(String bswdId, String userId, String swId,
			Boolean isGroupLeader, String remark) {
		this.bswdId = bswdId;
		this.userId = userId;
		this.swId = swId;
		this.isGroupLeader = isGroupLeader;
		this.remark = remark;
	}

	// Property accessors

	public String getBswdId() {
		return this.bswdId;
	}

	public void setBswdId(String bswdId) {
		this.bswdId = bswdId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSwId() {
		return this.swId;
	}

	public void setSwId(String swId) {
		this.swId = swId;
	}

	public Boolean getIsGroupLeader() {
		return this.isGroupLeader;
	}

	public void setIsGroupLeader(Boolean isGroupLeader) {
		this.isGroupLeader = isGroupLeader;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
