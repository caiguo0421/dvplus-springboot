package cn.sf_soft.finance.voucher.model;

import java.util.Set;

/**
 * 凭证模板
 * AcctVoucherTemplate entity. @author MyEclipse Persistence Tools
 */

public class AcctVoucherTemplate implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6654879830694559857L;
	private String tno;				//模板编号
	private String tmoduleId;
	private Integer tvoucherWId;
	private Boolean tstatus;
	private String tremark;
	private String tname;
	private Integer tsort;
	private String voucherCreatorId;
	private String voucherCreatorNo;
	private String voucherCreatorName;
	public String getVoucherCreatorId() {
		return voucherCreatorId;
	}

	public void setVoucherCreatorId(String voucherCreatorId) {
		this.voucherCreatorId = voucherCreatorId;
	}

	public String getVoucherCreatorNo() {
		return voucherCreatorNo;
	}

	public void setVoucherCreatorNo(String voucherCreatorNo) {
		this.voucherCreatorNo = voucherCreatorNo;
	}

	/**
	 * 凭证字
	 */
	private Set<AcctVoucherW> voucherWs;
	private Set<AcctVoucherTemplateD> voucherTemplateDetails;//凭证模板明细
	private Set<AcctVoucherTemplateClass> voucherTemplateClasses; //凭证模板核算对象
	// Constructors

	/** default constructor */
	public AcctVoucherTemplate() {
	}

	/** minimal constructor */
	public AcctVoucherTemplate(String tno, String tmoduleId, Integer tvoucherWId) {
		this.tno = tno;
		this.tmoduleId = tmoduleId;
		this.tvoucherWId = tvoucherWId;
	}

	/** full constructor */
	public AcctVoucherTemplate(String tno, String tmoduleId,
			Integer tvoucherWId, Boolean tstatus, String tremark, String tname,
			Integer tsort) {
		this.tno = tno;
		this.tmoduleId = tmoduleId;
		this.tvoucherWId = tvoucherWId;
		this.tstatus = tstatus;
		this.tremark = tremark;
		this.tname = tname;
		this.tsort = tsort;
	}

	// Property accessors

	public String getTno() {
		return this.tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public String getTmoduleId() {
		return this.tmoduleId;
	}

	public void setTmoduleId(String tmoduleId) {
		this.tmoduleId = tmoduleId;
	}

	public Integer getTvoucherWId() {
		return this.tvoucherWId;
	}

	public void setTvoucherWId(Integer tvoucherWId) {
		this.tvoucherWId = tvoucherWId;
	}

	public Boolean getTstatus() {
		return this.tstatus;
	}

	public void setTstatus(Boolean tstatus) {
		this.tstatus = tstatus;
	}

	public String getTremark() {
		return this.tremark;
	}

	public void setTremark(String tremark) {
		this.tremark = tremark;
	}

	public String getTname() {
		return this.tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Integer getTsort() {
		return this.tsort;
	}

	public void setTsort(Integer tsort) {
		this.tsort = tsort;
	}

	public Set<AcctVoucherTemplateD> getVoucherTemplateDetails() {
		return voucherTemplateDetails;
	}

	public void setVoucherTemplateDetails(Set<AcctVoucherTemplateD> voucherTemplateDetails) {
		this.voucherTemplateDetails = voucherTemplateDetails;
	}


	public Set<AcctVoucherW> getVoucherWs() {
		return voucherWs;
	}

	public void setVoucherWs(Set<AcctVoucherW> voucherWs) {
		this.voucherWs = voucherWs;
	}

	public Set<AcctVoucherTemplateClass> getVoucherTemplateClasses() {
		return voucherTemplateClasses;
	}

	public void setVoucherTemplateClasses(Set<AcctVoucherTemplateClass> voucherTemplateClasses) {
		this.voucherTemplateClasses = voucherTemplateClasses;
	}

	public String getVoucherCreatorName() {
		return voucherCreatorName;
	}

	public void setVoucherCreatorName(String voucherCreatorName) {
		this.voucherCreatorName = voucherCreatorName;
	}

	public String getCreatorFullName(){
		return voucherCreatorName + "(" + voucherCreatorNo + ")";
	}
	
	@Override
	public String toString() {
		return "AcctVoucherTemplate [tno=" + tno + ", tmoduleId=" + tmoduleId
				+ ", tvoucherWId=" + tvoucherWId + ", tstatus=" + tstatus
				+ ", tremark=" + tremark + ", tname=" + tname + ", tsort="
				+ tsort + ", voucherCreatorId=" + voucherCreatorId
				+ ", voucherCreatorNo=" + voucherCreatorNo
				+ ", voucherCreatorName=" + voucherCreatorName + "]";
	}


}
