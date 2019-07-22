package cn.sf_soft.finance.voucher.dao.hbb;

import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.finance.voucher.dao.AcctItemDao;
import cn.sf_soft.finance.voucher.model.AcctItem;

/**
 * 核算对象
 * @author liujin
 *
 */
@Repository("accItemDao")
public class AcctItemDaoHibernate extends BaseDaoHibernateImpl
		implements AcctItemDao {

	public void addAcctItem(AcctItem item){
		getHibernateTemplate().save(item);
	}

}
