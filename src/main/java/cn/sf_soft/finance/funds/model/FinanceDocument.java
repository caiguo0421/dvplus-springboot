package cn.sf_soft.finance.funds.model;

import java.sql.Timestamp;

/**
 * 
 * @Title: 出入明细
 * @date 2013-9-23 下午02:49:01 
 * @author cw
 */
public class FinanceDocument implements java.io.Serializable {
	
	private String entryId;
	private String accountNo;//银行账号
	private String accountName;//账户名称
	private String stationId;	
	private String stationName;//站点
	private String amountTypeMeaning;//款项类型
	private String accountTypeMeaning;//账户类型
	private Double debitAmount;//支出金额
	private Double creditAmount;//存入金额
	private Double accountBalance;//账户余额
	private String documentType;//单据类型
	private String documentNo;//单据编号
	private String objectName;//往来对象
	private Timestamp documentTime;//单据日期
	private String summary;//摘要
	
	
	/*	
	private String entryProperty;	
	private String entryType;
	private String documentId;
	private String subDocumentNo;
	private String objectId;
	private String objectNo;
	private String accountId;
	private String amountType;
	private Double leftAmount;
	private Double documentAmount;
	private String arapTime;
	private String userId;
	private String userNo;
	private String userName;
	private String departmentId;
	private String departmentNo;
	private String departmentName;
	private Double offsetAmount;
	private String offsetTime;
	private Double paidAmount;
	private String paidTime;
	private Double writeOffAmount;
	private String writeOffTime;
	private Double invoiceAmount;
	private String invoiceTime;
	private String afterNo;*/
	
	
	public String getEntryId() {
		return entryId;
	}
	public void setEntryId(String entryId) {
		this.entryId = entryId;
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
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getAmountTypeMeaning() {
		return amountTypeMeaning;
	}
	public void setAmountTypeMeaning(String amountTypeMeaning) {
		this.amountTypeMeaning = amountTypeMeaning;
	}
	public String getAccountTypeMeaning() {
		return accountTypeMeaning;
	}
	public void setAccountTypeMeaning(String accountTypeMeaning) {
		this.accountTypeMeaning = accountTypeMeaning;
	}
	public Double getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}
	public Double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public Double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public Timestamp getDocumentTime() {
		return documentTime;
	}
	public void setDocumentTime(Timestamp documentTime) {
		this.documentTime = documentTime;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
	
	
	

	
	
	
	
	
}
