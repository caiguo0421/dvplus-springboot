package cn.sf_soft.finance.funds.model;

import java.sql.Timestamp;

/**
 * FinanceSettlementsModes entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class FinanceSettlementsModes implements java.io.Serializable {

	// Fields
	private String fsmId;
	private String settlementNo;
	private String originId;
	private Short paymentMode;
	private String accountId;
	private String accountNo;
	private String accountName;
	private String fprdId;
	private Double settleAmount;
	private Double repayAmount;
	private Double posFee;
	private String departmentId;
	private String departmentNo;
	private String departmentName;
	private Boolean isChecked;
	private String checker;
	private Timestamp checkTime;

	// Constructors

	/** default constructor */
	public FinanceSettlementsModes() {
	}

	/** minimal constructor */
	public FinanceSettlementsModes(String fsmId, String settlementNo,
			Short paymentMode, String accountId, String accountNo,
			String accountName, Double settleAmount, Double repayAmount,
			Double posFee) {
		this.fsmId = fsmId;
		this.settlementNo = settlementNo;
		this.paymentMode = paymentMode;
		this.accountId = accountId;
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.settleAmount = settleAmount;
		this.repayAmount = repayAmount;
		this.posFee = posFee;
	}

	/** full constructor */
	public FinanceSettlementsModes(String fsmId, String settlementNo,
			String originId, Short paymentMode, String accountId,
			String accountNo, String accountName, String fprdId,
			Double settleAmount, Double repayAmount, Double posFee, String departmentId, String departmentNo,
			String departmentName, Boolean isChecked, String checker,
			Timestamp checkTime) {
		this.fsmId = fsmId;
		this.settlementNo = settlementNo;
		this.originId = originId;
		this.paymentMode = paymentMode;
		this.accountId = accountId;
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.fprdId = fprdId;
		this.settleAmount = settleAmount;
		this.repayAmount = repayAmount;
		this.posFee = posFee;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.isChecked = isChecked;
		this.checker = checker;
		this.checkTime = checkTime;
	}

	// Property accessors

	public String getFsmId() {
		return this.fsmId;
	}

	public void setFsmId(String fsmId) {
		this.fsmId = fsmId;
	}

	public String getSettlementNo() {
		return this.settlementNo;
	}

	public void setSettlementNo(String settlementNo) {
		this.settlementNo = settlementNo;
	}

	public String getOriginId() {
		return this.originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	public Short getPaymentMode() {
		return this.paymentMode;
	}

	public void setPaymentMode(Short paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getFprdId() {
		return this.fprdId;
	}

	public void setFprdId(String fprdId) {
		this.fprdId = fprdId;
	}

	public Double getSettleAmount() {
		return this.settleAmount;
	}

	public void setSettleAmount(Double settleAmount) {
		this.settleAmount = settleAmount;
	}

	public Double getRepayAmount() {
		return this.repayAmount;
	}

	public void setRepayAmount(Double repayAmount) {
		this.repayAmount = repayAmount;
	}

	public Double getPosFee() {
		return this.posFee;
	}

	public void setPosFee(Double posFee) {
		this.posFee = posFee;
	}

	

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentNo() {
		return this.departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Boolean getIsChecked() {
		return this.isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getChecker() {
		return this.checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public Timestamp getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

}
