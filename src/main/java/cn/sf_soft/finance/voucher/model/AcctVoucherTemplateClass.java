package cn.sf_soft.finance.voucher.model;

/**
 * 凭证模板核算对象
 * AcctVoucherTemplateClass entity. @author MyEclipse Persistence Tools
 */

public class AcctVoucherTemplateClass implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -131753657287086675L;
	private String tid;
	private String tno;
	private Integer tclassId;
	private String tfields;
	private Boolean tisGroup;
	private Boolean tisSum;

	// Constructors

	/** default constructor */
	public AcctVoucherTemplateClass() {
	}

	/** minimal constructor */
	public AcctVoucherTemplateClass(String tid, Integer tclassId,
			String tfields, Boolean tisGroup, Boolean tisSum) {
		this.tid = tid;
		this.tclassId = tclassId;
		this.tfields = tfields;
		this.tisGroup = tisGroup;
		this.tisSum = tisSum;
	}

	/** full constructor */
	public AcctVoucherTemplateClass(String tid, String tno, Integer tclassId,
			String tfields, Boolean tisGroup, Boolean tisSum) {
		this.tid = tid;
		this.tno = tno;
		this.tclassId = tclassId;
		this.tfields = tfields;
		this.tisGroup = tisGroup;
		this.tisSum = tisSum;
	}

	// Property accessors

	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTno() {
		return this.tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public Integer getTclassId() {
		return this.tclassId;
	}

	public void setTclassId(Integer tclassId) {
		this.tclassId = tclassId;
	}

	public String getTfields() {
		return this.tfields;
	}

	public void setTfields(String tfields) {
		this.tfields = tfields;
	}

	public Boolean getTisGroup() {
		return this.tisGroup;
	}

	public void setTisGroup(Boolean tisGroup) {
		this.tisGroup = tisGroup;
	}

	public Boolean getTisSum() {
		return this.tisSum;
	}

	public void setTisSum(Boolean tisSum) {
		this.tisSum = tisSum;
	}

	@Override
	public String toString() {
		return "AcctVoucherTemplateClass [tid=" + tid + ", tno=" + tno
				+ ", tclassId=" + tclassId + ", tfields=" + tfields
				+ ", tisGroup=" + tisGroup + ", tisSum=" + tisSum + "]";
	}

}
