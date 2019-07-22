package cn.sf_soft.finance.funds.model;

import java.util.List;
/**
 * 资金查询以及出入明细查询条件
 * @author CW
 * @create 2013-9-23上午9:36
 */
public class FundsAccountsSearchCriteria {

	private String accountName;
	private String accountNo;
	private String accountTypeMeaning;
	private String beginTime;
	private String endTime;
	private String userId;
	private String accountId;//用于查询出入明细
	private String keyword; // 模糊查询
	private List<String> stationIds;
	
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getAccountTypeMeaning() {
		return accountTypeMeaning;
	}
	public void setAccountTypeMeaning(String accountTypeMeaning) {
		this.accountTypeMeaning = accountTypeMeaning;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getStationIds() {
		return stationIds;
	}
	public void setStationIds(List<String> stationIds) {
		this.stationIds = stationIds;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
