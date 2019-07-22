package cn.sf_soft.office.approval.model;

/**
 * VwFinanceGuarantorsId entity. @author MyEclipse Persistence Tools
 */

public class VwFinanceGuarantorsId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7306098825456172835L;
	private String userId;
	private Short status;
	private String userNo;
	private String userName;
	private Double guarantyLimit;
	private String fullId;
	private Integer guarantyCount;
	private Integer addCount;
	private Double guaranteedAmount;

	// Constructors

	/** default constructor */
	public VwFinanceGuarantorsId() {
	}

	/** minimal constructor */
	public VwFinanceGuarantorsId(String userId, Short status, String userNo,
			String userName, Double guarantyLimit) {
		this.userId = userId;
		this.status = status;
		this.userNo = userNo;
		this.userName = userName;
		this.guarantyLimit = guarantyLimit;
	}

	/** full constructor */
	public VwFinanceGuarantorsId(String userId, Short status, String userNo,
			String userName, Double guarantyLimit, String fullId,
			Integer guarantyCount, Integer addCount, Double guaranteedAmount) {
		this.userId = userId;
		this.status = status;
		this.userNo = userNo;
		this.userName = userName;
		this.guarantyLimit = guarantyLimit;
		this.fullId = fullId;
		this.guarantyCount = guarantyCount;
		this.addCount = addCount;
		this.guaranteedAmount = guaranteedAmount;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
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

	public Double getGuarantyLimit() {
		return this.guarantyLimit;
	}

	public void setGuarantyLimit(Double guarantyLimit) {
		this.guarantyLimit = guarantyLimit;
	}

	public String getFullId() {
		return this.fullId;
	}

	public void setFullId(String fullId) {
		this.fullId = fullId;
	}

	public Integer getGuarantyCount() {
		return this.guarantyCount;
	}

	public void setGuarantyCount(Integer guarantyCount) {
		this.guarantyCount = guarantyCount;
	}

	public Integer getAddCount() {
		return this.addCount;
	}

	public void setAddCount(Integer addCount) {
		this.addCount = addCount;
	}

	public Double getGuaranteedAmount() {
		return this.guaranteedAmount;
	}

	public void setGuaranteedAmount(Double guaranteedAmount) {
		this.guaranteedAmount = guaranteedAmount;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VwFinanceGuarantorsId))
			return false;
		VwFinanceGuarantorsId castOther = (VwFinanceGuarantorsId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null
				&& castOther.getUserId() != null && this.getUserId().equals(
				castOther.getUserId())))
				&& ((this.getStatus() == castOther.getStatus()) || (this
						.getStatus() != null
						&& castOther.getStatus() != null && this.getStatus()
						.equals(castOther.getStatus())))
				&& ((this.getUserNo() == castOther.getUserNo()) || (this
						.getUserNo() != null
						&& castOther.getUserNo() != null && this.getUserNo()
						.equals(castOther.getUserNo())))
				&& ((this.getUserName() == castOther.getUserName()) || (this
						.getUserName() != null
						&& castOther.getUserName() != null && this
						.getUserName().equals(castOther.getUserName())))
				&& ((this.getGuarantyLimit() == castOther.getGuarantyLimit()) || (this
						.getGuarantyLimit() != null
						&& castOther.getGuarantyLimit() != null && this
						.getGuarantyLimit()
						.equals(castOther.getGuarantyLimit())))
				&& ((this.getFullId() == castOther.getFullId()) || (this
						.getFullId() != null
						&& castOther.getFullId() != null && this.getFullId()
						.equals(castOther.getFullId())))
				&& ((this.getGuarantyCount() == castOther.getGuarantyCount()) || (this
						.getGuarantyCount() != null
						&& castOther.getGuarantyCount() != null && this
						.getGuarantyCount()
						.equals(castOther.getGuarantyCount())))
				&& ((this.getAddCount() == castOther.getAddCount()) || (this
						.getAddCount() != null
						&& castOther.getAddCount() != null && this
						.getAddCount().equals(castOther.getAddCount())))
				&& ((this.getGuaranteedAmount() == castOther
						.getGuaranteedAmount()) || (this.getGuaranteedAmount() != null
						&& castOther.getGuaranteedAmount() != null && this
						.getGuaranteedAmount().equals(
								castOther.getGuaranteedAmount())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getStatus() == null ? 0 : this.getStatus().hashCode());
		result = 37 * result
				+ (getUserNo() == null ? 0 : this.getUserNo().hashCode());
		result = 37 * result
				+ (getUserName() == null ? 0 : this.getUserName().hashCode());
		result = 37
				* result
				+ (getGuarantyLimit() == null ? 0 : this.getGuarantyLimit()
						.hashCode());
		result = 37 * result
				+ (getFullId() == null ? 0 : this.getFullId().hashCode());
		result = 37
				* result
				+ (getGuarantyCount() == null ? 0 : this.getGuarantyCount()
						.hashCode());
		result = 37 * result
				+ (getAddCount() == null ? 0 : this.getAddCount().hashCode());
		result = 37
				* result
				+ (getGuaranteedAmount() == null ? 0 : this
						.getGuaranteedAmount().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "VwFinanceGuarantorsId [addCount=" + addCount + ", fullId="
				+ fullId + ", guaranteedAmount=" + guaranteedAmount
				+ ", guarantyCount=" + guarantyCount + ", guarantyLimit="
				+ guarantyLimit + ", status=" + status + ", userId=" + userId
				+ ", userName=" + userName + ", userNo=" + userNo + "]";
	}

}
