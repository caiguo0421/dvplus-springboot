package cn.sf_soft.finance.funds.model;
/**
 * 
 * @Title: 资金查询
 * @date 2013-9-22 下午04:06:47 
 * @author cw
 */
public class FundsAccounts implements java.io.Serializable {
	private String  accountId;
	private String  accountNo;	
	private String  accountName;	
	private String  bankAccountName;
	private String  bankName;
	private String  bankNumber;
	private String  address;
	private Integer accountType;
	private Integer accountKind;
	private String  accountTypeMeaning;
	private String  accountKindMeaning;
	private String  beginningBalance;
	private String  debitAmount;
	private Integer debitCount;
	private String  creditAmount;
	private Integer creditCount;
	 
	
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getAccountKind() {
		return accountKind;
	}
	public void setAccountKind(Integer accountKind) {
		this.accountKind = accountKind;
	}
	public String getAccountTypeMeaning() {
		return accountTypeMeaning;
	}
	public void setAccountTypeMeaning(String accountTypeMeaning) {
		this.accountTypeMeaning = accountTypeMeaning;
	}
	public String getAccountKindMeaning() {
		return accountKindMeaning;
	}
	public void setAccountKindMeaning(String accountKindMeaning) {
		this.accountKindMeaning = accountKindMeaning;
	}
	
	
	public Integer getDebitCount() {
		return debitCount;
	}
	public void setDebitCount(Integer debitCount) {
		this.debitCount = debitCount;
	}
	
	public String getBeginningBalance() {
		return beginningBalance;
	}
	public void setBeginningBalance(String beginningBalance) {
		this.beginningBalance = beginningBalance;
	}
	public String getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}
	public String getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	public Integer getCreditCount() {
		return creditCount;
	}
	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
	}
	
	
}
