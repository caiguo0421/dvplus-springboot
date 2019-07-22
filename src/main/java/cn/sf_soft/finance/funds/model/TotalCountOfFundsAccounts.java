package cn.sf_soft.finance.funds.model;

/**
 * 
 * @Title: 资金账户查询总计
 * 总支出金额，总存入金额,总期初余额，总期末余额，总支出笔数，总存入笔数
 * @date 2013-9-26 上午09:37:19 
 * @author cw
 */
public class TotalCountOfFundsAccounts {
	private String totalBeginningBalance;
	private String totalEndingBalance;
	private String totalDebitCount;
	private String totalCreditCount;
	private String totalDebitAmount;
	private String totalCreditAmount;
	public String getTotalBeginningBalance() {
		return totalBeginningBalance;
	}
	public void setTotalBeginningBalance(String totalBeginningBalance) {
		this.totalBeginningBalance = totalBeginningBalance;
	}
	public String getTotalEndingBalance() {
		return totalEndingBalance;
	}
	public void setTotalEndingBalance(String totalEndingBalance) {
		this.totalEndingBalance = totalEndingBalance;
	}
	public String getTotalDebitCount() {
		return totalDebitCount;
	}
	public void setTotalDebitCount(String totalDebitCount) {
		this.totalDebitCount = totalDebitCount;
	}
	public String getTotalCreditCount() {
		return totalCreditCount;
	}
	public void setTotalCreditCount(String totalCreditCount) {
		this.totalCreditCount = totalCreditCount;
	}
	public String getTotalDebitAmount() {
		return totalDebitAmount;
	}
	public void setTotalDebitAmount(String totalDebitAmount) {
		this.totalDebitAmount = totalDebitAmount;
	}
	public String getTotalCreditAmount() {
		return totalCreditAmount;
	}
	public void setTotalCreditAmount(String totalCreditAmount) {
		this.totalCreditAmount = totalCreditAmount;
	}
	
	
	
	
}
