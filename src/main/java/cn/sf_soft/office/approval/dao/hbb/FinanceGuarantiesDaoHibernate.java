package cn.sf_soft.office.approval.dao.hbb;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.FinanceGuarantiesDao;
import cn.sf_soft.office.approval.model.FinanceGuaranties;

/**
 * 应收担保审批的hibernate操作
 * @author minggo
 * @created 2013-01-16
 */
@Repository("financeGuarantiesDao")
public class FinanceGuarantiesDaoHibernate extends BaseDaoHibernateImpl
		implements FinanceGuarantiesDao {
	/**
	 * According to the single number inquires the documents information
	 */
	@SuppressWarnings("unchecked")
	public FinanceGuaranties getDocumentDetail(String documentNo) {
		List<FinanceGuaranties> list = (List<FinanceGuaranties>) getHibernateTemplate().find("from FinanceGuaranties f where f.documentNo=?",documentNo);
		if(!list.isEmpty())
			return list.get(0);
		return null;
	}
	/**
	 * Update accounts receivable guarantee information
	 */
	public boolean updateFinanceGuaranties(final FinanceGuaranties financeGuaranties) {
		getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				session.saveOrUpdate(financeGuaranties);
				return true;
			}
		});
		return false;
	}
	//可能在这里写追加金额的hibernate操作。。。。
}
