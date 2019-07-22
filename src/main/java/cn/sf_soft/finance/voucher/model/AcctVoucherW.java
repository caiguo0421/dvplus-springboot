package cn.sf_soft.finance.voucher.model;

/**
 * 凭证字
 * AcctVoucherW entity. @author MyEclipse Persistence Tools
 */

public class AcctVoucherW implements java.io.Serializable {

	// Fields

	@Override
	public String toString() {
		return "AcctVoucherW [tvoucherWId=" + tvoucherWId + ", tcompanyId="
				+ tcompanyId + ", tname=" + tname + ", tused=" + tused + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2468914836970497449L;
	private Integer tvoucherWId;
	private Integer tcompanyId;
	private String tname;
	private Boolean tused;

	// Constructors

	/** default constructor */
	public AcctVoucherW() {
	}

	/** full constructor */
	public AcctVoucherW(Integer tvoucherWId, Integer tcompanyId, String tname,
			Boolean tused) {
		this.tvoucherWId = tvoucherWId;
		this.tcompanyId = tcompanyId;
		this.tname = tname;
		this.tused = tused;
	}

	// Property accessors

	public Integer getTvoucherWId() {
		return this.tvoucherWId;
	}

	public void setTvoucherWId(Integer tvoucherWId) {
		this.tvoucherWId = tvoucherWId;
	}

	public Integer getTcompanyId() {
		return this.tcompanyId;
	}

	public void setTcompanyId(Integer tcompanyId) {
		this.tcompanyId = tcompanyId;
	}

	public String getTname() {
		return this.tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Boolean getTused() {
		return this.tused;
	}

	public void setTused(Boolean tused) {
		this.tused = tused;
	}

}
