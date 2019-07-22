package cn.sf_soft.office.approval.dao.hbb;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.FinanceLoanRequestsDao;
import cn.sf_soft.office.approval.model.FinanceLoanRequests;

/**
 * deal with the staffs'loan events
 * @author minggo
 * @created 2012-12-13
 */
@Repository("financeLoanRequestsDao")
public class FinanceLoanRequestsDaoHibernate extends BaseDaoHibernateImpl implements FinanceLoanRequestsDao {
	/**
	 * For staff loan documents
	 */
	@SuppressWarnings("unchecked")
	public FinanceLoanRequests getFinanceLoanRequestsDetail(String documentNo) {
		String hql = "from FinanceLoanRequests f where f.documentNo=?";
		List<FinanceLoanRequests> list = (List<FinanceLoanRequests>)getHibernateTemplate().find(hql,documentNo);
		if(!list.isEmpty())
			return list.get(0);
		return null;
		
	}
	/**
	 * Update the employee loan documents
	 */
	public boolean updateFinanceLoanRequests(final FinanceLoanRequests financeLoanRequests) {
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException {
					    session.saveOrUpdate(financeLoanRequests);
		           return true;
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
}
