package cn.sf_soft.finance.voucher.model;

import java.sql.Timestamp;

/**
 * 凭证明细分录
 * AcctVoucherEntry entity. @author MyEclipse Persistence Tools
 */

public class AcctVoucherEntry implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1333819889491987597L;
	private Integer tvoucherEntryId;
	private Integer tdetailId;
	private Integer taccountId;
	private Integer tvoucherId;
	private Integer torderNumber;
	private Integer tcompanyId;
	private String tresume;
	private Integer tcurrencyId;
	private Double texchangeRate;
	private Short tdc;
	private Double tamountFor;
	private Double tamount;
	private Double tquantity;
	private String tunit;
	private Double tunitPrice;
	private Integer tsettleTypeId;
	private String tsettleNo;
	private Timestamp tdate;
	private String ttransNo;
	private Boolean tprofitLossFlag;
	
	/**
	 * 附加的字段，用于验证产生的凭证分录明细
	 */
	private int tclassId;//

	// Constructors

	/** default constructor */
	public AcctVoucherEntry() {
	}

	/** minimal constructor */
	public AcctVoucherEntry(Integer tvoucherEntryId,
			Integer tdetailId, Integer taccountId,
			Integer tvoucherId, Integer torderNumber, Integer tcompanyId,
			String tresume, Integer tcurrencyId, Double texchangeRate,
			Short tdc, Double tamountFor, Double tamount, Double tquantity,
			Double tunitPrice, Integer tsettleTypeId, String tsettleNo,
			Timestamp tdate, String ttransNo, Boolean tprofitLossFlag) {
		this.tvoucherEntryId = tvoucherEntryId;
		this.tdetailId = tdetailId;
		this.taccountId = taccountId;
		this.tvoucherId = tvoucherId;
		this.torderNumber = torderNumber;
		this.tcompanyId = tcompanyId;
		this.tresume = tresume;
		this.tcurrencyId = tcurrencyId;
		this.texchangeRate = texchangeRate;
		this.tdc = tdc;
		this.tamountFor = tamountFor;
		this.tamount = tamount;
		this.tquantity = tquantity;
		this.tunitPrice = tunitPrice;
		this.tsettleTypeId = tsettleTypeId;
		this.tsettleNo = tsettleNo;
		this.tdate = tdate;
		this.ttransNo = ttransNo;
		this.tprofitLossFlag = tprofitLossFlag;
	}

	/** full constructor */
	public AcctVoucherEntry(Integer tvoucherEntryId,
			Integer tdetailId, Integer taccountId,
			Integer tvoucherId, Integer torderNumber, Integer tcompanyId,
			String tresume, Integer tcurrencyId, Double texchangeRate,
			Short tdc, Double tamountFor, Double tamount, Double tquantity,
			String tunit, Double tunitPrice, Integer tsettleTypeId,
			String tsettleNo, Timestamp tdate, String ttransNo,
			Boolean tprofitLossFlag) {
		this.tvoucherEntryId = tvoucherEntryId;
		this.tdetailId = tdetailId;
		this.taccountId = taccountId;
		this.tvoucherId = tvoucherId;
		this.torderNumber = torderNumber;
		this.tcompanyId = tcompanyId;
		this.tresume = tresume;
		this.tcurrencyId = tcurrencyId;
		this.texchangeRate = texchangeRate;
		this.tdc = tdc;
		this.tamountFor = tamountFor;
		this.tamount = tamount;
		this.tquantity = tquantity;
		this.tunit = tunit;
		this.tunitPrice = tunitPrice;
		this.tsettleTypeId = tsettleTypeId;
		this.tsettleNo = tsettleNo;
		this.tdate = tdate;
		this.ttransNo = ttransNo;
		this.tprofitLossFlag = tprofitLossFlag;
	}

	// Property accessors

	public Integer getTvoucherEntryId() {
		return this.tvoucherEntryId;
	}

	public void setTvoucherEntryId(Integer tvoucherEntryId) {
		this.tvoucherEntryId = tvoucherEntryId;
	}

	public Integer getTdetailId() {
		return this.tdetailId;
	}

	public void setTdetailId(Integer tdetailId) {
		this.tdetailId = tdetailId;
	}

	public Integer getTaccountId() {
		return this.taccountId;
	}

	public void setTaccountId(Integer taccountId) {
		this.taccountId = taccountId;
	}

	public Integer getTvoucherId() {
		return this.tvoucherId;
	}

	public void setTvoucherId(Integer tvoucherId) {
		this.tvoucherId = tvoucherId;
	}

	public Integer getTorderNumber() {
		return this.torderNumber;
	}

	public void setTorderNumber(Integer torderNumber) {
		this.torderNumber = torderNumber;
	}

	public Integer getTcompanyId() {
		return this.tcompanyId;
	}

	public void setTcompanyId(Integer tcompanyId) {
		this.tcompanyId = tcompanyId;
	}

	public String getTresume() {
		return this.tresume;
	}

	public void setTresume(String tresume) {
		this.tresume = tresume;
	}

	public Integer getTcurrencyId() {
		return this.tcurrencyId;
	}

	public void setTcurrencyId(Integer tcurrencyId) {
		this.tcurrencyId = tcurrencyId;
	}

	public Double getTexchangeRate() {
		return this.texchangeRate;
	}

	public void setTexchangeRate(Double texchangeRate) {
		this.texchangeRate = texchangeRate;
	}

	public Short getTdc() {
		return this.tdc;
	}

	public void setTdc(Short tdc) {
		this.tdc = tdc;
	}

	public Double getTamountFor() {
		return this.tamountFor;
	}

	public void setTamountFor(Double tamountFor) {
		this.tamountFor = tamountFor;
	}

	public Double getTamount() {
		return this.tamount;
	}

	public void setTamount(Double tamount) {
		this.tamount = tamount;
	}

	public Double getTquantity() {
		return this.tquantity;
	}

	public void setTquantity(Double tquantity) {
		this.tquantity = tquantity;
	}

	public String getTunit() {
		return this.tunit;
	}

	public void setTunit(String tunit) {
		this.tunit = tunit;
	}

	public Double getTunitPrice() {
		return this.tunitPrice;
	}

	public void setTunitPrice(Double tunitPrice) {
		this.tunitPrice = tunitPrice;
	}

	public Integer getTsettleTypeId() {
		return this.tsettleTypeId;
	}

	public void setTsettleTypeId(Integer tsettleTypeId) {
		this.tsettleTypeId = tsettleTypeId;
	}

	public String getTsettleNo() {
		return this.tsettleNo;
	}

	public void setTsettleNo(String tsettleNo) {
		this.tsettleNo = tsettleNo;
	}

	public Timestamp getTdate() {
		return this.tdate;
	}

	public void setTdate(Timestamp tdate) {
		this.tdate = tdate;
	}

	public String getTtransNo() {
		return this.ttransNo;
	}

	public void setTtransNo(String ttransNo) {
		this.ttransNo = ttransNo;
	}

	public Boolean getTprofitLossFlag() {
		return this.tprofitLossFlag;
	}

	public void setTprofitLossFlag(Boolean tprofitLossFlag) {
		this.tprofitLossFlag = tprofitLossFlag;
	}

	@Override
	public String toString() {
		return "AcctVoucherEntry [tvoucherEntryId=" + tvoucherEntryId
				+ ", tdetailId=" + tdetailId + ", taccountId=" + taccountId
				+ ", tvoucherId=" + tvoucherId + ", torderNumber="
				+ torderNumber + ", tcompanyId=" + tcompanyId + ", tresume="
				+ tresume + ", tcurrencyId=" + tcurrencyId + ", texchangeRate="
				+ texchangeRate + ", tdc=" + tdc + ", tamountFor=" + tamountFor
				+ ", tamount=" + tamount + ", tquantity=" + tquantity
				+ ", tunit=" + tunit + ", tunitPrice=" + tunitPrice
				+ ", tsettleTypeId=" + tsettleTypeId + ", tsettleNo="
				+ tsettleNo + ", tdate=" + tdate + ", ttransNo=" + ttransNo
				+ ", tprofitLossFlag=" + tprofitLossFlag + ", tclassId=" + tclassId +"]";
	}

	public int getTclassId() {
		return tclassId;
	}

	public void setTclassId(int tclassId) {
		this.tclassId = tclassId;
	}

}
