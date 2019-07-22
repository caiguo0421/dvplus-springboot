package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * FinanceGuarantiesAdditions entity. @author MyEclipse Persistence Tools
 */

public class FinanceGuarantiesAdditions implements java.io.Serializable {

	// Fields

	private String fgaId;
	private String documentNo;
	private Double addAmount;
	private String userId;
	private String userNo;
	private String userName;
	private Timestamp addTime;

	// Constructors

	/** default constructor */
	public FinanceGuarantiesAdditions() {
	}

	/** full constructor */
	public FinanceGuarantiesAdditions(String fgaId, String documentNo,
			Double addAmount, String userId, String userNo, String userName,
			Timestamp addTime) {
		this.fgaId = fgaId;
		this.documentNo = documentNo;
		this.addAmount = addAmount;
		this.userId = userId;
		this.userNo = userNo;
		this.userName = userName;
		this.addTime = addTime;
	}

	// Property accessors

	public String getFgaId() {
		return this.fgaId;
	}

	public void setFgaId(String fgaId) {
		this.fgaId = fgaId;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public Double getAddAmount() {
		return this.addAmount;
	}

	public void setAddAmount(Double addAmount) {
		this.addAmount = addAmount;
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

	public Timestamp getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

}
