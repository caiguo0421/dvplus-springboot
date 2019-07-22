package cn.sf_soft.finance.funds.service.impl;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.finance.funds.dao.FundsAccountsDao;
import cn.sf_soft.finance.funds.model.FinanceDocument;
import cn.sf_soft.finance.funds.model.FundsAccounts;
import cn.sf_soft.finance.funds.model.FundsAccountsSearchCriteria;
import cn.sf_soft.finance.funds.model.TotalCountOfFinance;
import cn.sf_soft.finance.funds.model.TotalCountOfFundsAccounts;
import cn.sf_soft.finance.funds.service.FundsAccountService;

@Service("fundsAccountService")
public class FundsAccountServiceImpl implements FundsAccountService {
	@Autowired
	@Qualifier("fundsAccountsDao")
	private FundsAccountsDao fundsAccountsDao;

	public void setFundsAccountsDao(FundsAccountsDao fundsAccountsDao) {
		this.fundsAccountsDao = fundsAccountsDao;
	}

	/**
	 * 获取资金查询信息
	 */
	public List<FundsAccounts> getFundsAccountsData(
			FundsAccountsSearchCriteria serchCriteria, final int pageNo,
			final int pageSize) {
		return fundsAccountsDao.getFundsAccountsData(serchCriteria, pageNo,
				pageSize);
	}

	/**
	 * 获取出入明细
	 */
	public List<FinanceDocument> getFinanceDocumentEntries(
			FundsAccountsSearchCriteria serchCriteria, int pageNo, int pageSize) {
		return fundsAccountsDao.getFinanceDocumentEntries(serchCriteria,
				pageNo, pageSize);
	}

	/**
	 * 获取资金查询信息总记录数
	 */
	public int getFundsAccountsCount(FundsAccountsSearchCriteria serchCriteria) {
		List list = fundsAccountsDao.getFundsAccountsCount(serchCriteria);
		if (list.size() > 0) {
			return Integer.parseInt(list.get(0).toString());
		} else {
			return 0;
		}
	}

	/**
	 * 获取出入明细总记录数
	 */
	public int getFinanceDocumentEntriesCount(
			FundsAccountsSearchCriteria serchCriteria) {
		List list = fundsAccountsDao
				.getFinanceDocumentEntriesCount(serchCriteria);
		if (list.size() > 0) {
			return Integer.parseInt(list.get(0).toString());
		} else {
			return 0;
		}
	}

	/**
	 * 获取出入明细总计
	 */
	public TotalCountOfFinance getTotalCountOfFinance(
			FundsAccountsSearchCriteria serchCriteria) {
		TotalCountOfFinance totalCount = new TotalCountOfFinance();
		List<TotalCountOfFinance> list = fundsAccountsDao
				.getTotalCountOfFinance(serchCriteria);
		if (list.get(0) != null) {
			totalCount.setTotalCreditAmount(list.get(0).getTotalCreditAmount());
			totalCount.setTotalDebitAmount(list.get(0).getTotalDebitAmount());
		} else {
			totalCount.setTotalCreditAmount("0.00");
			totalCount.setTotalDebitAmount("0.00");
		}
		return totalCount;
	}

	/**
	 * 获取资金查询总计
	 */
	public TotalCountOfFundsAccounts getTotalCountOfFundsAccounts(
			FundsAccountsSearchCriteria serchCriteria) {
		TotalCountOfFundsAccounts totalCount = new TotalCountOfFundsAccounts();
		List<TotalCountOfFundsAccounts> list = fundsAccountsDao
				.getTotalCountOfFundsAccounts(serchCriteria);
		if (list.get(0) != null) {
			totalCount.setTotalCreditAmount(list.get(0).getTotalCreditAmount());
			totalCount.setTotalDebitAmount(list.get(0).getTotalDebitAmount());
			totalCount.setTotalBeginningBalance(list.get(0)
					.getTotalBeginningBalance());
			totalCount.setTotalCreditCount(list.get(0).getTotalCreditCount());
			totalCount.setTotalDebitCount(list.get(0).getTotalDebitCount());
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			totalCount.setTotalEndingBalance(nf.format(
					Double.parseDouble(list.get(0).getTotalBeginningBalance())
							+ Double.parseDouble(list.get(0)
									.getTotalCreditAmount())
							- Double.parseDouble(list.get(0)
									.getTotalDebitAmount())).toString());
		} else {
			totalCount.setTotalCreditAmount("0.00");
			totalCount.setTotalDebitAmount("0.00");
			totalCount.setTotalBeginningBalance("0.00");
			totalCount.setTotalEndingBalance("0.00");
			totalCount.setTotalCreditCount("0");
			totalCount.setTotalDebitCount("0");
		}
		return totalCount;
	}

	/**
	 * 初始化查询条件
	 */
	public String[] getFundsAccountSearchCriteriaDate() {
		List<String> list = fundsAccountsDao
				.getFundsAccountSearchCriteriaDate();
		String[] fundsAccountType = new String[list.size()];
		list.toArray(fundsAccountType);
		return fundsAccountType;
	}

	@Override
	public List<Map<String, Object>> getPayable(List<String> stationIds, String objectName) {
		return fundsAccountsDao.getPayable(stationIds, objectName);
	}

	@Override
	public List<Map<String, Object>> getReceivable(List<String> stationIds, String objectName) {
		return fundsAccountsDao.getReceivable(stationIds, objectName);
	}

	@Override
	public List<Map<String, Object>> getReceivableTrend(List<String> stationIds, String objectName, String businessType) {
		return fundsAccountsDao.getReceivableTrend(stationIds, objectName, businessType);
	}


	@Override
	public List<Map<String, Object>> getPayableTrend(List<String> stationIds, String objectName, String businessType) {
		return fundsAccountsDao.getPayableTrend(stationIds, objectName, businessType);
	}
}
