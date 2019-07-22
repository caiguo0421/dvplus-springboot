package cn.sf_soft.finance.voucher.model;

/**
 * 帐套
 * AcctCompanyInfo entity. @author MyEclipse Persistence Tools
 */

public class AcctCompanyInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3267471694856177106L;
	private Integer tcompanyId;
	private String tcompanyNo;
	private String tcompanyName;
	private Integer tstationId;

	// Constructors

	/** default constructor */
	public AcctCompanyInfo() {
	}

	/** minimal constructor */
	public AcctCompanyInfo(Integer tcompanyId, String tcompanyNo,
			String tcompanyName) {
		this.tcompanyId = tcompanyId;
		this.tcompanyNo = tcompanyNo;
		this.tcompanyName = tcompanyName;
	}

	/** full constructor */
	public AcctCompanyInfo(Integer tcompanyId, String tcompanyNo,
			String tcompanyName, Integer tstationId) {
		this.tcompanyId = tcompanyId;
		this.tcompanyNo = tcompanyNo;
		this.tcompanyName = tcompanyName;
		this.tstationId = tstationId;
	}

	// Property accessors

	public Integer getTcompanyId() {
		return this.tcompanyId;
	}

	public void setTcompanyId(Integer tcompanyId) {
		this.tcompanyId = tcompanyId;
	}

	public String getTcompanyNo() {
		return this.tcompanyNo;
	}

	public void setTcompanyNo(String tcompanyNo) {
		this.tcompanyNo = tcompanyNo;
	}

	public String getTcompanyName() {
		return this.tcompanyName;
	}

	public void setTcompanyName(String tcompanyName) {
		this.tcompanyName = tcompanyName;
	}

	public Integer getTstationId() {
		return this.tstationId;
	}

	public void setTstationId(Integer tstationId) {
		this.tstationId = tstationId;
	}

	@Override
	public String toString() {
		return "AcctCompanyInfo [tcompanyId=" + tcompanyId + ", tcompanyNo="
				+ tcompanyNo + ", tcompanyName=" + tcompanyName
				+ ", tstationId=" + tstationId + "]";
	}

}
