package cn.sf_soft.office.approval.dao;

import java.util.List;
import java.util.Map;

import cn.sf_soft.office.approval.model.FinancePaymentRequests;

/**
 * 业务请款
 * 
 * @author minggo
 * @created 2013-01-07
 */
public interface FinancePaymentRequestsDao {
	/**
	 * According to documents, inquires the business of your detailed
	 * information
	 * 
	 * @param documentNo
	 * @return
	 */
	public FinancePaymentRequests getDocumentDetail(String documentNo);

	/**
	 * update the business model of documents
	 * 
	 * @param financePaymentRequests
	 * @return
	 */
	public boolean updateFinancePaymentRequests(
            FinancePaymentRequests financePaymentRequests);

	/**
	 * add by shichunshan 根据分录id找出所有的请款明细
	 */
    public List<Map<String, Object>> findFinancePaymentRequestsDetailsByEnterId(
            String enterId);
}
