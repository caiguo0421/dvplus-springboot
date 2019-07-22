package cn.sf_soft.office.approval.dao;

import java.util.List;

import cn.sf_soft.office.approval.model.FinanceFundsRequests;

/**
 * 资金调拨申请
 * @author king
 * @create 2013-1-16下午05:13:04
 */
public interface FinanceFundsRequestsDao {

	/**
	 * 根据单据编号得到资金调拨申请的详细信息
	 * @param documentNo
	 * @return
	 */
	public FinanceFundsRequests getDocumentDetail(String documentNo);
	/**
	 * 修改资金调拨申请
	 * @param financeFundsRequests
	 * @return
	 */
	public boolean updateFinanceFundsRequests(FinanceFundsRequests financeFundsRequests);
	
	/**
	 * 根据单据编号得到已经审批同意的资金调入申请单
	 * @param documentNo
	 * @return
	 */
	public FinanceFundsRequests getAgreedRequestIn(String documentNo);
}
