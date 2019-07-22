package cn.sf_soft.finance.voucher.model;

/**
 * 凭证模板明细
 * AcctVoucherTemplateD entity. @author MyEclipse Persistence Tools
 */

public class AcctVoucherTemplateD implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -431651073246162029L;
	private String tid;
	private String tno;
	private Integer taccountId;
	private String taccoutName;
	private Short tdc;
	private Boolean tisRed;
	private String tpriceField;
	private String tquantityField;
	private String tdocumnetNoField;
	private String tunitField;
	private String tresumeFields;
	private String tresumeExpresion;
	private String tfilterFields;
	private Integer tsort;
	private String tobjectNoField;

	private AcctAccount acctAcount;//会计科目
	// Constructors

	/** default constructor */
	public AcctVoucherTemplateD() {
	}

	/** minimal constructor */
	public AcctVoucherTemplateD(String tid, String tno, Integer taccountId,
			String taccoutName, Short tdc) {
		this.tid = tid;
		this.tno = tno;
		this.taccountId = taccountId;
		this.taccoutName = taccoutName;
		this.tdc = tdc;
	}

	/** full constructor */
	public AcctVoucherTemplateD(String tid, String tno, Integer taccountId,
			String taccoutName, Short tdc, Boolean tisRed, String tpriceField,
			String tquantityField, String tdocumnetNoField, String tunitField,
			String tresumeFields, String tresumeExpresion,
			String tfilterFields, Integer tsort, String tobjectNoField) {
		this.tid = tid;
		this.tno = tno;
		this.taccountId = taccountId;
		this.taccoutName = taccoutName;
		this.tdc = tdc;
		this.tisRed = tisRed;
		this.tpriceField = tpriceField;
		this.tquantityField = tquantityField;
		this.tdocumnetNoField = tdocumnetNoField;
		this.tunitField = tunitField;
		this.tresumeFields = tresumeFields;
		this.tresumeExpresion = tresumeExpresion;
		this.tfilterFields = tfilterFields;
		this.tsort = tsort;
		this.tobjectNoField = tobjectNoField;
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

	public Integer getTaccountId() {
		return this.taccountId;
	}

	public void setTaccountId(Integer taccountId) {
		this.taccountId = taccountId;
	}

	public String getTaccoutName() {
		return this.taccoutName;
	}

	public void setTaccoutName(String taccoutName) {
		this.taccoutName = taccoutName;
	}

	public Short getTdc() {
		return this.tdc;
	}

	public void setTdc(Short tdc) {
		this.tdc = tdc;
	}

	public Boolean getTisRed() {
		return this.tisRed == null ? false:this.tisRed;
	}

	public void setTisRed(Boolean tisRed) {
		this.tisRed = tisRed;
	}

	public String getTpriceField() {
		return this.tpriceField;
	}

	public void setTpriceField(String tpriceField) {
		this.tpriceField = tpriceField;
	}

	public String getTquantityField() {
		return this.tquantityField;
	}

	public void setTquantityField(String tquantityField) {
		this.tquantityField = tquantityField;
	}

	public String getTdocumnetNoField() {
		return this.tdocumnetNoField;
	}

	public void setTdocumnetNoField(String tdocumnetNoField) {
		this.tdocumnetNoField = tdocumnetNoField;
	}

	public String getTunitField() {
		return this.tunitField;
	}

	public void setTunitField(String tunitField) {
		this.tunitField = tunitField;
	}

	public String getTresumeFields() {
		return this.tresumeFields;
	}

	public void setTresumeFields(String tresumeFields) {
		this.tresumeFields = tresumeFields;
	}

	public String getTresumeExpresion() {
		return this.tresumeExpresion;
	}

	public void setTresumeExpresion(String tresumeExpresion) {
		this.tresumeExpresion = tresumeExpresion;
	}

	public String getTfilterFields() {
		return this.tfilterFields;
	}

	public void setTfilterFields(String tfilterFields) {
		this.tfilterFields = tfilterFields;
	}

	public Integer getTsort() {
		return this.tsort;
	}

	public void setTsort(Integer tsort) {
		this.tsort = tsort;
	}

	public String getTobjectNoField() {
		return this.tobjectNoField;
	}

	public void setTobjectNoField(String tobjectNoField) {
		this.tobjectNoField = tobjectNoField;
	}

	public AcctAccount getAcctAcount() {
		return acctAcount;
	}

	public void setAcctAcount(AcctAccount acctAcount) {
		this.acctAcount = acctAcount;
	}

	@Override
	public String toString() {
		return "AcctVoucherTemplateD [tid=" + tid + ", tno=" + tno
				+ ", taccountId=" + taccountId + ", taccoutName=" + taccoutName
				+ ", tdc=" + tdc + ", tisRed=" + tisRed + ", tpriceField="
				+ tpriceField + ", tquantityField=" + tquantityField
				+ ", tdocumnetNoField=" + tdocumnetNoField + ", tunitField="
				+ tunitField + ", tresumeFields=" + tresumeFields
				+ ", tresumeExpresion=" + tresumeExpresion + ", tfilterFields="
				+ tfilterFields + ", tsort=" + tsort + ", tobjectNoField="
				+ tobjectNoField + " ]";
	}

}
