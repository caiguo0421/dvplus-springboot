package cn.sf_soft.finance.voucher.model;

import java.sql.Timestamp;

/**
 * 会计期间
 * AcctPeriod entity. @author MyEclipse Persistence Tools
 */

public class AcctPeriod implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5204406759980883821L;
	private Integer tcompanyId;
	private Integer tyear;
	private Integer tmonth;
	private Timestamp tstartDate;
	private Timestamp tendDate;

	// Property accessors


	public Timestamp getTstartDate() {
		return this.tstartDate;
	}

	public void setTstartDate(Timestamp tstartDate) {
		this.tstartDate = tstartDate;
	}

	public Timestamp getTendDate() {
		return this.tendDate;
	}

	public void setTendDate(Timestamp tendDate) {
		this.tendDate = tendDate;
	}

	public Integer getTcompanyId() {
		return tcompanyId;
	}

	public void setTcompanyId(Integer tcompanyId) {
		this.tcompanyId = tcompanyId;
	}

	public Integer getTyear() {
		return tyear;
	}

	public void setTyear(Integer tyear) {
		this.tyear = tyear;
	}

	public Integer getTmonth() {
		return tmonth;
	}

	public void setTmonth(Integer tmonth) {
		this.tmonth = tmonth;
	}

	@Override
	public String toString() {
		return "AcctPeriod [tcompanyId=" + tcompanyId + ", tyear=" + tyear
				+ ", tmonth=" + tmonth + ", tstartDate=" + tstartDate
				+ ", tendDate=" + tendDate + "]";
	}


}
