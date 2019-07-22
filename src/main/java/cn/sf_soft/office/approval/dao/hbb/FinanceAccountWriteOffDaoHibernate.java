package cn.sf_soft.office.approval.dao.hbb;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.FinanceAccountWriteOffDao;
import cn.sf_soft.office.approval.model.FinanceAccountWriteOffs;

/**
 * 销账处理
 * @author liujin
 *
 */
@Repository("financeAccountWritesOffDao")
public class FinanceAccountWriteOffDaoHibernate extends BaseDaoHibernateImpl
		implements FinanceAccountWriteOffDao {

	@SuppressWarnings("unchecked")
	public List<FinanceAccountWriteOffs> findByProperty(String property,
			Object value) {
		return (List<FinanceAccountWriteOffs>) getHibernateTemplate().find("from FinanceAccountWriteOffs f left join fetch f.chargeDetail left join fetch f.apportionments where f."
				+ property + "=?", value);
	}

	@SuppressWarnings("unchecked")
	public FinanceAccountWriteOffs getDocumentDetails(String documentNo) {
		List<FinanceAccountWriteOffs> list = (List<FinanceAccountWriteOffs>) getHibernateTemplate().find("from FinanceAccountWriteOffs f left join fetch f.chargeDetail left join fetch f.apportionments where f.documentNo = ?", documentNo);
		return list.get(0);
	}

	public boolean save(FinanceAccountWriteOffs financeAccountWriteOff) {
		getHibernateTemplate().save(financeAccountWriteOff);
		return true;
	}

	public void update(FinanceAccountWriteOffs accountWriteOff) {
		getHibernateTemplate().update(accountWriteOff);
	}

}
