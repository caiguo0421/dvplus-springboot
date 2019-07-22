package cn.sf_soft.finance.voucher.dao.hbb;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.finance.voucher.dao.AcctPeriodDao;
import cn.sf_soft.finance.voucher.model.AcctPeriod;

/**
 * 会计期间
 * @author liujin
 *
 */
@Repository("acctPeriodDao")
public class AcctPeriodDaoHibernate extends BaseDaoHibernateImpl
		implements AcctPeriodDao {

	public AcctPeriod getCurAcctPeriodByCompanyId(final int companyId) {
		return (AcctPeriod) getHibernateTemplate().execute(new HibernateCallback() {

			public AcctPeriod doInHibernate(Session session)
					throws HibernateException {
				return (AcctPeriod) session.createSQLQuery("SELECT * FROM acct_period WHERE tcompany_id=? AND tstart_date <= GETDATE() AND DATEADD(day, 1, tend_date) > GETDATE() ")
					   .addEntity(AcctPeriod.class)
					   .setInteger(0, companyId)
					   .list().get(0);
			}
		});
	}

}
