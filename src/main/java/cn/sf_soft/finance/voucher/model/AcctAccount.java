package cn.sf_soft.finance.voucher.model;

import java.util.Set;

/**
 * 会计科目
 * AcctAccount entity. @author MyEclipse Persistence Tools
 */

public class AcctAccount implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8684881147281512328L;
	private Integer taccountId;
	private Integer tcompanyId;
	private String tno;
	private String tname;
	private String tfullname;
	private Short tlevel;
	private Boolean tdetail;
	private Integer tparentId;
	private Integer tclassId;
	private Short tdc;
	private Integer tcurrencyId;
	private Boolean tadjustRate;
	private Boolean tquantity;
	private Boolean tcashFlow;
	private Boolean tcash;
	private Boolean tbank;
	private Integer tdetailId;
	private Boolean tcontact;
	private Boolean tstop;
	private String tunit;
	private String tupdatedTime;
	private Boolean tuse;
	
	private Set<AcctItemClass> itemClasses;//会计科目核算项目
	// Constructors

	/** default constructor */
	public AcctAccount() {
	}


	/** full constructor */
	public AcctAccount(Integer taccountId,
			Integer tcompanyId, String tno, String tname, String tfullname,
			Short tlevel, Boolean tdetail, Integer tparentId, Integer tclassId,
			Short tdc, Integer tcurrencyId, Boolean tadjustRate,
			Boolean tquantity, Boolean tcashFlow, Boolean tcash, Boolean tbank,
			Boolean tcontact, Boolean tstop, String tunit, String tupdatedTime, Boolean tuse) {
		this.taccountId = taccountId;
		this.tcompanyId = tcompanyId;
		this.tno = tno;
		this.tname = tname;
		this.tfullname = tfullname;
		this.tlevel = tlevel;
		this.tdetail = tdetail;
		this.tparentId = tparentId;
		this.tclassId = tclassId;
		this.tdc = tdc;
		this.tcurrencyId = tcurrencyId;
		this.tadjustRate = tadjustRate;
		this.tquantity = tquantity;
		this.tcashFlow = tcashFlow;
		this.tcash = tcash;
		this.tbank = tbank;
		this.tcontact = tcontact;
		this.tstop = tstop;
		this.tunit = tunit;
		this.tupdatedTime = tupdatedTime;
		this.tuse = tuse;
	}

	// Property accessors

	public Integer getTaccountId() {
		return this.taccountId;
	}

	public void setTaccountId(Integer taccountId) {
		this.taccountId = taccountId;
	}

	public Integer getTcompanyId() {
		return this.tcompanyId;
	}

	public void setTcompanyId(Integer tcompanyId) {
		this.tcompanyId = tcompanyId;
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

	public String getTfullname() {
		return this.tfullname;
	}

	public void setTfullname(String tfullname) {
		this.tfullname = tfullname;
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

	public Integer getTparentId() {
		return this.tparentId;
	}

	public void setTparentId(Integer tparentId) {
		this.tparentId = tparentId;
	}

	public Integer getTclassId() {
		return this.tclassId;
	}

	public void setTclassId(Integer tclassId) {
		this.tclassId = tclassId;
	}

	public Short getTdc() {
		return this.tdc;
	}

	public void setTdc(Short tdc) {
		this.tdc = tdc;
	}

	public Integer getTcurrencyId() {
		return this.tcurrencyId;
	}

	public void setTcurrencyId(Integer tcurrencyId) {
		this.tcurrencyId = tcurrencyId;
	}

	public Boolean getTadjustRate() {
		return this.tadjustRate;
	}

	public void setTadjustRate(Boolean tadjustRate) {
		this.tadjustRate = tadjustRate;
	}

	public Boolean getTquantity() {
		return this.tquantity;
	}

	public void setTquantity(Boolean tquantity) {
		this.tquantity = tquantity;
	}

	public Boolean getTcashFlow() {
		return this.tcashFlow;
	}

	public void setTcashFlow(Boolean tcashFlow) {
		this.tcashFlow = tcashFlow;
	}

	public Boolean getTcash() {
		return this.tcash;
	}

	public void setTcash(Boolean tcash) {
		this.tcash = tcash;
	}

	public Boolean getTbank() {
		return this.tbank;
	}

	public void setTbank(Boolean tbank) {
		this.tbank = tbank;
	}

	public Boolean getTcontact() {
		return this.tcontact;
	}

	public void setTcontact(Boolean tcontact) {
		this.tcontact = tcontact;
	}

	public Boolean getTstop() {
		return this.tstop;
	}

	public void setTstop(Boolean tstop) {
		this.tstop = tstop;
	}

	public String getTunit() {
		return this.tunit;
	}

	public void setTunit(String tunit) {
		this.tunit = tunit;
	}

	public String getTupdatedTime() {
		return this.tupdatedTime;
	}

	public void setTupdatedTime(String tupdatedTime) {
		this.tupdatedTime = tupdatedTime;
	}

	public Boolean getTuse() {
		return this.tuse;
	}

	public void setTuse(Boolean tuse) {
		this.tuse = tuse;
	}


	public Set<AcctItemClass> getItemClasses() {
		return itemClasses;
	}


	public void setItemClasses(Set<AcctItemClass> itemClasses) {
		this.itemClasses = itemClasses;
	}


	@Override
	public String toString() {
		return "AcctAccount [taccountId=" + taccountId + ", tcompanyId="
				+ tcompanyId + ", tno=" + tno + ", tname=" + tname
				+ ", tfullname=" + tfullname + ", tlevel=" + tlevel
				+ ", tdetail=" + tdetail + ", tparentId=" + tparentId
				+ ", tclassId=" + tclassId + ", tdc=" + tdc + ", tcurrencyId="
				+ tcurrencyId + ", tadjustRate=" + tadjustRate + ", tquantity="
				+ tquantity + ", tcashFlow=" + tcashFlow + ", tcash=" + tcash
				+ ", tbank=" + tbank + ", tcontact=" + tcontact + ", tstop="
				+ tstop + ", tunit=" + tunit + ", tupdatedTime=" + tupdatedTime
				+ ", tuse=" + tuse + ", itemClasses=" + itemClasses 
				+ ", tdetailId=" + tdetailId + " ]";
	}


	public Integer getTdetailId() {
		return tdetailId;
	}


	public void setTdetailId(Integer tdetailId) {
		this.tdetailId = tdetailId;
	}

}
