package cn.sf_soft.office.approval.dao;

import cn.sf_soft.office.approval.model.FinanceLoanRequests;

/**
 * Interface for staffs'loan event
 * @author minggo
 * @created 2012-12-13
 *
 */
public interface FinanceLoanRequestsDao {
	/**
	 * According to the document Numbers get staff loan documents detailed information (not detail)
	 * @param documentNo
	 * @return
	 */
	public FinanceLoanRequests getFinanceLoanRequestsDetail(String documentNo);
	/**
	 * Modify the document of staff's loan
	 * @param financeLoanRequests
	 * @return
	 */
	public boolean updateFinanceLoanRequests(FinanceLoanRequests financeLoanRequests);   
	
}
