package cn.sf_soft.finance.funds.model;

/**
 * 
 * @Title: 出入明细总计
 * 总支出金额，总存入金额
 * @date 2013-9-26 上午09:37:19 
 * @author cw
 */
public class TotalCountOfFinance {

	private String totalDebitAmount;
	private String totalCreditAmount;
	
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
