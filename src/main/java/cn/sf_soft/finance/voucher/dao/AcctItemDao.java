package cn.sf_soft.finance.voucher.dao;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.finance.voucher.model.AcctItem;

public interface AcctItemDao extends BaseDao{

	public void addAcctItem(AcctItem item);
}
