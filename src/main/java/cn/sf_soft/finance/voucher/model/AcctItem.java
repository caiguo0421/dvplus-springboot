package cn.sf_soft.finance.voucher.model;

/**
 * 核算对象
 * AcctItem entity. @author MyEclipse Persistence Tools
 */

public class AcctItem implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1100231684657990663L;
	private Integer titemId;
	private Integer titemClassId;
	private String tno;
	private Integer tparentId;
	private Short tlevel;
	private Boolean tdetail;
	private String tname;
	private Boolean tused;
	private String tid;
	private String tcompanyNo;

	// Constructors

	/** default constructor */
	public AcctItem() {
	}

	/** minimal constructor */
	public AcctItem(Integer titemId, Integer titemClassId, String tno,
			Integer tparentId, Short tlevel, Boolean tdetail, String tname,
			Boolean tused, String tid) {
		this.titemId = titemId;
		this.titemClassId = titemClassId;
		this.tno = tno;
		this.tparentId = tparentId;
		this.tlevel = tlevel;
		this.tdetail = tdetail;
		this.tname = tname;
		this.tused = tused;
		this.tid = tid;
	}

	/** full constructor */
	public AcctItem(Integer titemId, Integer titemClassId, String tno,
			Integer tparentId, Short tlevel, Boolean tdetail, String tname,
			Boolean tused, String tid, String tcompanyNo) {
		this.titemId = titemId;
		this.titemClassId = titemClassId;
		this.tno = tno;
		this.tparentId = tparentId;
		this.tlevel = tlevel;
		this.tdetail = tdetail;
		this.tname = tname;
		this.tused = tused;
		this.tid = tid;
		this.tcompanyNo = tcompanyNo;
	}

	// Property accessors

	public Integer getTitemId() {
		return this.titemId;
	}

	public void setTitemId(Integer titemId) {
		this.titemId = titemId;
	}

	public Integer getTitemClassId() {
		return this.titemClassId;
	}

	public void setTitemClassId(Integer titemClassId) {
		this.titemClassId = titemClassId;
	}

	public String getTno() {
		return this.tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public Integer getTparentId() {
		return this.tparentId;
	}

	public void setTparentId(Integer tparentId) {
		this.tparentId = tparentId;
	}

	public Short getTlevel() {
		return this.tlevel;
	}

	public void setTlevel(Short tlevel) {
		this.tlevel = tlevel;
	}

	public Boolean getTdetail() {
		return this.tdetail;
	}

	public void setTdetail(Boolean tdetail) {
		this.tdetail = tdetail;
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

	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTcompanyNo() {
		return this.tcompanyNo;
	}

	public void setTcompanyNo(String tcompanyNo) {
		this.tcompanyNo = tcompanyNo;
	}

	@Override
	public String toString() {
		return "AcctItem [titemId=" + titemId + ", titemClassId="
				+ titemClassId + ", tno=" + tno + ", tparentId=" + tparentId
				+ ", tlevel=" + tlevel + ", tdetail=" + tdetail + ", tname="
				+ tname + ", tused=" + tused + ", tid=" + tid + ", tcompanyNo="
				+ tcompanyNo + "]";
	}

}
