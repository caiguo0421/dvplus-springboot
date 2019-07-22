package cn.sf_soft.office.approval.dao.hbb;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.OfficeInternalAuditsDao;
import cn.sf_soft.office.approval.model.OfficeInternalAudits;
/**
 * 内部审计DAO Hibernate实现
 * @author king
 * @create 2013-1-17下午02:59:51
 */
@Repository("officeInternalAuditsDao")
public class OfficeInternalAuditsDaoHibernate extends BaseDaoHibernateImpl
		implements OfficeInternalAuditsDao {

	/**
	 * 根据单据编号得到内部审计单据的详细信息
	 */
	@SuppressWarnings("unchecked")
	public OfficeInternalAudits getDocumentDetail(String documentNo) {
		List<OfficeInternalAudits> result = (List<OfficeInternalAudits>) getHibernateTemplate().find("from OfficeInternalAudits o left join fetch o.chargeDetail where o.documentNo=?",documentNo);
		if(result != null && result.size() > 0)
			return result.get(0);
		return null;
	}

	/**
	 * 更新单据
	 */
	public boolean updateOfficeInternalAutits(
			OfficeInternalAudits officeInternalAudits) {
		try {
			getHibernateTemplate().update(officeInternalAudits);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
