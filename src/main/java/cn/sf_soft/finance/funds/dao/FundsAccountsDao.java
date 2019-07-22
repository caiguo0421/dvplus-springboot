package cn.sf_soft.finance.funds.dao;

import java.util.List;
import java.util.Map;

import cn.sf_soft.finance.funds.model.FinanceDocument;
import cn.sf_soft.finance.funds.model.FundsAccounts;
import cn.sf_soft.finance.funds.model.FundsAccountsSearchCriteria;
import cn.sf_soft.finance.funds.model.TotalCountOfFinance;
import cn.sf_soft.finance.funds.model.TotalCountOfFundsAccounts;

public interface FundsAccountsDao {

	/**
	 * 获取资金查询数据
	 * @param serchCriteria 查询条件
	 * @param pageNo 
	 * @param pageSize 
	 * @return
	 */
	public List<FundsAccounts> getFundsAccountsData(FundsAccountsSearchCriteria serchCriteria,final int pageNo,final int pageSize);

	/**
	 * 获取出入明细
	 * @param serchCriteria 查询条件
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<FinanceDocument> getFinanceDocumentEntries(final FundsAccountsSearchCriteria serchCriteria,final int pageNo,final int pageSize);
	
	/**
	 * 获取资金查询数据总记录数
	 * @param serchCriteria 查询条件
	 * @return
	 */
	public List<Integer> getFundsAccountsCount(final FundsAccountsSearchCriteria serchCriteria);
	/**
	 * 获取出入明细总记录数
	 * @param serchCriteria
	 * @return
	 */
	public List<Integer> getFinanceDocumentEntriesCount(final FundsAccountsSearchCriteria serchCriteria);
	/**
	 * 获取 出入明细总计
	 * @param serchCriteria
	 * @return
	 */
	public List<TotalCountOfFinance> getTotalCountOfFinance(final FundsAccountsSearchCriteria serchCriteria);
	/**
	 * 获取资金查询总计
	 * @param serchCriteria
	 * @return
	 */
	public List<TotalCountOfFundsAccounts> getTotalCountOfFundsAccounts(final FundsAccountsSearchCriteria serchCriteria);
	/**
	 * 初始化查询条件
	 */
	public List<String> getFundsAccountSearchCriteriaDate() ;

	/**
	 * 查询应付款统计
	 * @param stationIds
	 * @param objectName
	 * @return
	 */
	List<Map<String,Object>> getPayable(List<String> stationIds, String objectName);

	/**
	 * 查询应收款统计
	 * @param stationIds
	 * @param objectName
	 * @return
	 */
	List<Map<String,Object>> getReceivable(List<String> stationIds, String objectName);

	/**
	 * 往来走势对比
	 * @param stationIds
	 * @param objectName
	 * @param businessType
	 * @return
	 */
    List<Map<String,Object>> getPayableTrend(List<String> stationIds, String objectName, String businessType);

	/**
	 * 往来走势对比
	 * @param stationIds
	 * @param objectName
	 * @param businessType
	 * @return
	 */
	List<Map<String,Object>> getReceivableTrend(List<String> stationIds, String objectName, String businessType);
}
