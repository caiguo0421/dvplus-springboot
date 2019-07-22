package cn.sf_soft.finance.voucher.model;

/**
 * 会计科目核算项目表
 * AcctItemClass entity. @author MyEclipse Persistence Tools
 */

public class AcctItemClass implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1838386427503005877L;
	// Fields

	private Integer titemClassId;
	private String tno;
	private String tname;
	private String ttableName;
	private Boolean tuse;
	private String tcompanyNo;
	private Short ttype;
	private String tnote;
	private Boolean tsysFixed;

	// Constructors

	/** default constructor */
	public AcctItemClass() {
	}

	/** full constructor */
	public AcctItemClass(Integer titemClassId, String tno, String tname,
			String ttableName, Boolean tuse, String tcompanyNo, Short ttype,
			String tnote, Boolean tsysFixed) {
		this.titemClassId = titemClassId;
		this.tno = tno;
		this.tname = tname;
		this.ttableName = ttableName;
		this.tuse = tuse;
		this.tcompanyNo = tcompanyNo;
		this.ttype = ttype;
		this.tnote = tnote;
		this.tsysFixed = tsysFixed;
	}

	// Property accessors

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

	public String getTname() {
		return this.tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTtableName() {
		return this.ttableName;
	}

	public void setTtableName(String ttableName) {
		this.ttableName = ttableName;
	}

	public Boolean getTuse() {
		return this.tuse;
	}

	public void setTuse(Boolean tuse) {
		this.tuse = tuse;
	}

	public String getTcompanyNo() {
		return this.tcompanyNo;
	}

	public void setTcompanyNo(String tcompanyNo) {
		this.tcompanyNo = tcompanyNo;
	}

	public Short getTtype() {
		return this.ttype;
	}

	public void setTtype(Short ttype) {
		this.ttype = ttype;
	}

	public String getTnote() {
		return this.tnote;
	}

	public void setTnote(String tnote) {
		this.tnote = tnote;
	}

	public Boolean getTsysFixed() {
		return this.tsysFixed;
	}

	public void setTsysFixed(Boolean tsysFixed) {
		this.tsysFixed = tsysFixed;
	}

	@Override
	public String toString() {
		return "AcctItemClass [titemClassId=" + titemClassId + ", tno=" + tno
				+ ", tname=" + tname + ", ttableName=" + ttableName + ", tuse="
				+ tuse + ", tcompanyNo=" + tcompanyNo + ", ttype=" + ttype
				+ ", tnote=" + tnote + ", tsysFixed=" + tsysFixed + "]";
	}

}
