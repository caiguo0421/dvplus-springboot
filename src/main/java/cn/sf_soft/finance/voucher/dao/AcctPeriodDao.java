package cn.sf_soft.finance.voucher.dao;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.finance.voucher.model.AcctPeriod;

public interface AcctPeriodDao extends BaseDao {

	/**
	 * 根据帐套ID获取当前时间的会计期间
	 * @param companyId
	 * @return
	 */
	public AcctPeriod getCurAcctPeriodByCompanyId(int companyId);
}
