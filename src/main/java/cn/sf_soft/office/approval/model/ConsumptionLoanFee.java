package cn.sf_soft.office.approval.model;

/**
 * 消贷费用相关-IOS需要
 * 
 * @author caigx
 *
 */
public class ConsumptionLoanFee implements java.io.Serializable {

	private static final long serialVersionUID = -243183878054547778L;
	private String contractDetailId; // 对应的消贷合同明细ID
	private Double loanAmountLv;// 消贷-车辆贷款
	private Double firstPayAmountVd;// 消贷-车辆首付

	public ConsumptionLoanFee() {
		super();
	}

	public ConsumptionLoanFee(String contractDetailId) {
		super();
		this.contractDetailId = contractDetailId;
	}
	

	public String getContractDetailId() {
		return contractDetailId;
	}

	public void setContractDetailId(String contractDetailId) {
		this.contractDetailId = contractDetailId;
	}

	public Double getFirstPayAmountVd() {
		return firstPayAmountVd;
	}

	public void setFirstPayAmountVd(Double firstPayAmountVd) {
		this.firstPayAmountVd = firstPayAmountVd;
	}

	public Double getLoanAmountLv() {
		return loanAmountLv;
	}

	public void setLoanAmountLv(Double loanAmountLv) {
		this.loanAmountLv = loanAmountLv;
	}
}
