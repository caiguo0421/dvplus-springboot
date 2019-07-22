package cn.sf_soft.finance.voucher.model;

/**
 * 凭证编号复合主键
 * AcctVoucherNoId entity. @author MyEclipse Persistence Tools
 */

public class AcctVoucherNoId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4591639717597729080L;
	private Integer tcompanyId;
	private Integer tyear;
	private Integer tmonth;
	private Integer tvoucherWId;
	private Integer tnumber;

	// Constructors

	/** default constructor */
	public AcctVoucherNoId() {
	}

	/** full constructor */
	public AcctVoucherNoId(Integer tcompanyId, Integer tyear, Integer tmonth,
			Integer tvoucherWId, Integer tnumber) {
		this.tcompanyId = tcompanyId;
		this.tyear = tyear;
		this.tmonth = tmonth;
		this.tvoucherWId = tvoucherWId;
		this.tnumber = tnumber;
	}

	// Property accessors

	public Integer getTcompanyId() {
		return this.tcompanyId;
	}

	public void setTcompanyId(Integer tcompanyId) {
		this.tcompanyId = tcompanyId;
	}

	public Integer getTyear() {
		return this.tyear;
	}

	public void setTyear(Integer tyear) {
		this.tyear = tyear;
	}

	public Integer getTmonth() {
		return this.tmonth;
	}

	public void setTmonth(Integer tmonth) {
		this.tmonth = tmonth;
	}

	public Integer getTvoucherWId() {
		return this.tvoucherWId;
	}

	public void setTvoucherWId(Integer tvoucherWId) {
		this.tvoucherWId = tvoucherWId;
	}

	public Integer getTnumber() {
		return this.tnumber;
	}

	public void setTnumber(Integer tnumber) {
		this.tnumber = tnumber;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AcctVoucherNoId))
			return false;
		AcctVoucherNoId castOther = (AcctVoucherNoId) other;

		return ((this.getTcompanyId() == castOther.getTcompanyId()) || (this
				.getTcompanyId() != null && castOther.getTcompanyId() != null && this
				.getTcompanyId().equals(castOther.getTcompanyId())))
				&& ((this.getTyear() == castOther.getTyear()) || (this
						.getTyear() != null && castOther.getTyear() != null && this
						.getTyear().equals(castOther.getTyear())))
				&& ((this.getTmonth() == castOther.getTmonth()) || (this
						.getTmonth() != null && castOther.getTmonth() != null && this
						.getTmonth().equals(castOther.getTmonth())))
				&& ((this.getTvoucherWId() == castOther.getTvoucherWId()) || (this
						.getTvoucherWId() != null
						&& castOther.getTvoucherWId() != null && this
						.getTvoucherWId().equals(castOther.getTvoucherWId())))
				&& ((this.getTnumber() == castOther.getTnumber()) || (this
						.getTnumber() != null && castOther.getTnumber() != null && this
						.getTnumber().equals(castOther.getTnumber())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTcompanyId() == null ? 0 : this.getTcompanyId()
						.hashCode());
		result = 37 * result
				+ (getTyear() == null ? 0 : this.getTyear().hashCode());
		result = 37 * result
				+ (getTmonth() == null ? 0 : this.getTmonth().hashCode());
		result = 37
				* result
				+ (getTvoucherWId() == null ? 0 : this.getTvoucherWId()
						.hashCode());
		result = 37 * result
				+ (getTnumber() == null ? 0 : this.getTnumber().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "AcctVoucherNoId [tcompanyId=" + tcompanyId + ", tyear=" + tyear
				+ ", tmonth=" + tmonth + ", tvoucherWId=" + tvoucherWId
				+ ", tnumber=" + tnumber + "]";
	}

}
