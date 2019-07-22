package cn.sf_soft.finance.voucher.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;

/**
 * 凭证主表
 * AcctVoucher entity. @author MyEclipse Persistence Tools
 */

public class AcctVoucher implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1462164512087804211L;
	private Integer tvoucherId;
	private Integer tcompanyId;
	private Timestamp tacctDate;
	private Integer tyear;
	private Integer tmonth;
	private Integer tvoucherWId;
	private Integer tnumber;
	private String tnote;
	private Integer tattachment;
	private String tpreparer;
	private String tchecker;
	private String tposter;
	private String ttranType;
	private String ttranNo;
	private Boolean tenter;//是否机制凭证(即自动产生的凭证)
	private Timestamp tCreateTime;
	private Boolean tValidate;
	
	private List<AcctVoucherEntry> voucherEntries;//凭证明细分录
	// Constructors

	/** default constructor */
	public AcctVoucher() {
	}

	/** minimal constructor */
	public AcctVoucher(Integer tvoucherId, Integer tcompanyId,
			Timestamp tacctDate, Integer tyear, Integer tmonth,
			Integer tvoucherWId, Integer tnumber, String tnote,
			Integer tattachment, String ttranType, String ttranNo,
			Boolean tenter) {
		this.tvoucherId = tvoucherId;
		this.tcompanyId = tcompanyId;
		this.tacctDate = tacctDate;
		this.tyear = tyear;
		this.tmonth = tmonth;
		this.tvoucherWId = tvoucherWId;
		this.tnumber = tnumber;
		this.tnote = tnote;
		this.tattachment = tattachment;
		this.ttranType = ttranType;
		this.ttranNo = ttranNo;
		this.tenter = tenter;
	}

	/** full constructor */
	public AcctVoucher(Integer tvoucherId, Integer tcompanyId,
			Timestamp tacctDate, Integer tyear, Integer tmonth,
			Integer tvoucherWId, Integer tnumber, String tnote,
			Integer tattachment, String tpreparer, String tchecker,
			String tposter, String ttranType, String ttranNo, Boolean tenter) {
		this.tvoucherId = tvoucherId;
		this.tcompanyId = tcompanyId;
		this.tacctDate = tacctDate;
		this.tyear = tyear;
		this.tmonth = tmonth;
		this.tvoucherWId = tvoucherWId;
		this.tnumber = tnumber;
		this.tnote = tnote;
		this.tattachment = tattachment;
		this.tpreparer = tpreparer;
		this.tchecker = tchecker;
		this.tposter = tposter;
		this.ttranType = ttranType;
		this.ttranNo = ttranNo;
		this.tenter = tenter;
	}

	// Property accessors

	public Integer getTvoucherId() {
		return this.tvoucherId;
	}

	public void setTvoucherId(Integer tvoucherId) {
		this.tvoucherId = tvoucherId;
	}

	public Integer getTcompanyId() {
		return this.tcompanyId;
	}

	public void setTcompanyId(Integer tcompanyId) {
		this.tcompanyId = tcompanyId;
	}

	public Timestamp getTacctDate() {
		return this.tacctDate;
	}

	public void setTacctDate(Timestamp tacctDate) {
		this.tacctDate = tacctDate;
	}

	public Integer getTyear() {
		return this.tyear;
	}

	public void setTyear(Integer tyear) {
		this.tyear = tyear;
	}

	public Integer getTmonth() {
		return this.tmonth;
	}

	public void setTmonth(Integer tmonth) {
		this.tmonth = tmonth;
	}

	public Integer getTvoucherWId() {
		return this.tvoucherWId;
	}

	public void setTvoucherWId(Integer tvoucherWId) {
		this.tvoucherWId = tvoucherWId;
	}

	public Integer getTnumber() {
		return this.tnumber;
	}

	public void setTnumber(Integer tnumber) {
		this.tnumber = tnumber;
	}

	public String getTnote() {
		return this.tnote;
	}

	public void setTnote(String tnote) {
		this.tnote = tnote;
	}

	public Integer getTattachment() {
		return this.tattachment;
	}

	public void setTattachment(Integer tattachment) {
		this.tattachment = tattachment;
	}

	public String getTpreparer() {
		return this.tpreparer;
	}

	public void setTpreparer(String tpreparer) {
		this.tpreparer = tpreparer;
	}

	public String getTchecker() {
		return this.tchecker;
	}

	public void setTchecker(String tchecker) {
		this.tchecker = tchecker;
	}

	public String getTposter() {
		return this.tposter;
	}

	public void setTposter(String tposter) {
		this.tposter = tposter;
	}

	public String getTtranType() {
		return this.ttranType;
	}

	public void setTtranType(String ttranType) {
		this.ttranType = ttranType;
	}

	public String getTtranNo() {
		return this.ttranNo;
	}

	public void setTtranNo(String ttranNo) {
		this.ttranNo = ttranNo;
	}

	public Boolean getTenter() {
		return this.tenter;
	}

	public void setTenter(Boolean tenter) {
		this.tenter = tenter;
	}

	public Timestamp gettCreateTime() {
		return tCreateTime;
	}

	public void settCreateTime(Timestamp tCreateTime) {
		tCreateTime.setNanos(0);
		this.tCreateTime = tCreateTime;
	}

	public Boolean gettValidate() {
		return tValidate;
	}

	public void settValidate(Boolean tValidate) {
		this.tValidate = tValidate;
	}


	public List<AcctVoucherEntry> getVoucherEntries() {
		return voucherEntries;
	}

	public void setVoucherEntries(List<AcctVoucherEntry> voucherEntries) {
		this.voucherEntries = voucherEntries;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String s = "AcctVoucher [tvoucherId=" + tvoucherId + ", tcompanyId="
				+ tcompanyId + ", tacctDate=" + tacctDate + ", tyear=" + tyear
				+ ", tmonth=" + tmonth + ", tvoucherWId=" + tvoucherWId
				+ ", tnumber=" + tnumber + ", tnote=" + tnote
				+ ", tattachment=" + tattachment + ", tpreparer=" + tpreparer
				+ ", tchecker=" + tchecker + ", tposter=" + tposter
				+ ", ttranType=" + ttranType + ", ttranNo=" + ttranNo
				+ ", tenter=" + tenter
				+ ", tCreateTime=" + tCreateTime
				+ ", tValidate=" + tValidate + "]";
		sb.append(s);
		if(voucherEntries != null){
			sb.append("\n");
			for(AcctVoucherEntry entry:voucherEntries){
				sb.append("\t" + entry.toString() + "\n");
			}
		}
		return sb.toString();
	}

}
