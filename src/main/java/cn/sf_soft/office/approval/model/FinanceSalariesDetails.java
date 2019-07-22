package cn.sf_soft.office.approval.model;

/**
 * FinanceSalariesDetails entity. @author MyEclipse Persistence Tools
 */

public class FinanceSalariesDetails implements java.io.Serializable {

	// Fields

	private String fsdId;
	private String documentNo;
	private String userId;
	private String userNo;
	private String userName;
	private Double payAmount;
	private Double deductAmount;
	

	// Constructors

	/** default constructor */
	public FinanceSalariesDetails() {
	}

	/** minimal constructor */
	public FinanceSalariesDetails(String documentNo, String userId,
			String userNo, String userName, Double payAmount,
			Double deductAmount) {
		this.documentNo = documentNo;
		this.userId = userId;
		this.userNo = userNo;
		this.userName = userName;
		this.payAmount = payAmount;
		this.deductAmount = deductAmount;
	}

	

	// Property accessors

	public String getFsdId() {
		return this.fsdId;
	}

	public void setFsdId(String fsdId) {
		this.fsdId = fsdId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getDeductAmount() {
		return this.deductAmount;
	}

	public void setDeductAmount(Double deductAmount) {
		this.deductAmount = deductAmount;
	}

	
}
