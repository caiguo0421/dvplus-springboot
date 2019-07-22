package cn.sf_soft.office.approval.model;

/**
 * FinanceFundsRequestsInAccounts entity. @author MyEclipse Persistence Tools
 */

public class FinanceFundsRequestsInAccounts implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2234823201621653888L;
	private String ffriaId;
	private String documentNo;
	private String accountId;
	private String accountNo;
	private String accountName;
	private Double requestAmount;
	private Double allocateAmount;

	// Constructors

	/** default constructor */
	public FinanceFundsRequestsInAccounts() {
	}

	/** full constructor */
	public FinanceFundsRequestsInAccounts(String ffriaId, String documentNo,
			String accountId, String accountNo, String accountName,
			Double requestAmount, Double allocateAmount) {
		this.ffriaId = ffriaId;
		this.documentNo = documentNo;
		this.accountId = accountId;
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.requestAmount = requestAmount;
		this.allocateAmount = allocateAmount;
	}

	// Property accessors

	public String getFfriaId() {
		return this.ffriaId;
	}

	public void setFfriaId(String ffriaId) {
		this.ffriaId = ffriaId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
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

	public Double getRequestAmount() {
		return this.requestAmount;
	}

	public void setRequestAmount(Double requestAmount) {
		this.requestAmount = requestAmount;
	}

	public Double getAllocateAmount() {
		return this.allocateAmount;
	}

	public void setAllocateAmount(Double allocateAmount) {
		this.allocateAmount = allocateAmount;
	}

	@Override
	public String toString() {
		return "FinanceFundsRequestsInAccounts [accountId=" + accountId
				+ ", accountName=" + accountName + ", accountNo=" + accountNo
				+ ", allocateAmount=" + allocateAmount + ", documentNo="
				+ documentNo + ", ffriaId=" + ffriaId + ", requestAmount="
				+ requestAmount + "]";
	}

}
