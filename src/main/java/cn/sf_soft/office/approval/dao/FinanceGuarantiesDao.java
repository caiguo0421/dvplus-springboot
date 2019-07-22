package cn.sf_soft.office.approval.dao;

import cn.sf_soft.office.approval.model.FinanceGuaranties;

/**
 * 应收担保
 * @author minggo
 * @created 2013-01-16
 */
public interface FinanceGuarantiesDao {
	/**
	 * According to the single number inquires the accounts receivable guarantee documents
	 * @param documentNo
	 * @return
	 */
	public FinanceGuaranties getDocumentDetail(String documentNo);
	/**
	 * Update accounts receivable guarantee information
	 * @param financeGuaranties
	 * @return
	 */
	public boolean updateFinanceGuaranties(FinanceGuaranties financeGuaranties);
}
