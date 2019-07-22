package cn.sf_soft.finance.voucher.model;

/**
 * 凭证编号
 * AcctVoucherNo entity. @author MyEclipse Persistence Tools
 */

public class AcctVoucherNo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -589478853893099801L;
	// Fields

	private AcctVoucherNoId id;
	private String ttype;

	// Constructors

	/** default constructor */
	public AcctVoucherNo() {
	}

	/** full constructor */
	public AcctVoucherNo(AcctVoucherNoId id, String ttype) {
		this.id = id;
		this.ttype = ttype;
	}

	// Property accessors

	public AcctVoucherNoId getId() {
		return this.id;
	}

	public void setId(AcctVoucherNoId id) {
		this.id = id;
	}

	public String getTtype() {
		return this.ttype;
	}

	public void setTtype(String ttype) {
		this.ttype = ttype;
	}

	@Override
	public String toString() {
		return "AcctVoucherNo [id=" + id + ", ttype=" + ttype + "]";
	}


}
