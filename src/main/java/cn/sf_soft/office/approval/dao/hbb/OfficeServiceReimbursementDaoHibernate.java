package cn.sf_soft.office.approval.dao.hbb;

import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.OfficeServiceReimbursementDao;
import cn.sf_soft.office.approval.model.OfficeServiceReimbursements;
import cn.sf_soft.office.approval.model.ServiceWorkLists;
import cn.sf_soft.office.approval.model.ServiceWorkListsCharge;
/**
 * 维修费用报销DAO Hibernate实现
 */
@Repository("officeServiceReimbursementDao")
public class OfficeServiceReimbursementDaoHibernate extends BaseDaoHibernateImpl
		implements OfficeServiceReimbursementDao {

	/**
	 * 根据单据编号得到维修费用报销单的详细信息
	 */
	@SuppressWarnings("unchecked")
	public OfficeServiceReimbursements getDocumentDetail(String documentNo) {
		List<OfficeServiceReimbursements> result = (List<OfficeServiceReimbursements>) getHibernateTemplate().find("from OfficeServiceReimbursements o left join fetch o.chargeDetail where o.documentNo=?",documentNo);
		if(result != null && result.size() > 0)
			return result.get(0);
		return null;
	}

	/**
	 * 修改维修费用报销单
	 */
	public boolean updateOfficeServiceReimbursements(
			OfficeServiceReimbursements officeServiceReimbursements) {
		try {
			getHibernateTemplate().update(officeServiceReimbursements);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 根据Id得到可报销的维修工单费用信息
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<ServiceWorkListsCharge> getServiceWorkListChargesReimburseAble(
			final String[] swlcIds) {
		return (List<ServiceWorkListsCharge>) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException{
				return session.createQuery("from ServiceWorkListsCharge c where c.chargePf<>0 and (c.supplierId is null or c.supplierId='') " +
				"and c.taskNo in(select l.taskNo from ServiceWorkLists l where l.taskStatus=60)" +
				"and c.chargeCost=0" +
				"and c.selfId in(:ids)").setParameterList("ids", swlcIds).list();
			}
		});
//		getHibernateTemplate().find("from ServiceWorkListsCharge c where c.chargePf<>0 and (c.supplierId is not null or supplierId='') " +
//				"and c.taskNo in(select l.taskNo from ServiceWorkLists l where l.taskStatus=0)" +
//				"and c.selfId in(?)");
	}

	/**
	 * 根据Id得到维修工单费用信息
	 */
	public ServiceWorkListsCharge getServiceWorkListCharges(String swlcId) {
		return (ServiceWorkListsCharge) getHibernateTemplate().load(ServiceWorkListsCharge.class, swlcId);
	}

	/**
	 * 根据工单号得到维修工单的详细信息
	 */
	public ServiceWorkLists getServiceWorkLists(String taskNo) {
		return (ServiceWorkLists) getHibernateTemplate().load(ServiceWorkLists.class, taskNo);
	}

	/**
	 * 修改维修工单费用信息
	 */
	public boolean updateServiceWorkListsCharge(
			ServiceWorkListsCharge serviceWorkListsCharge) {
		getHibernateTemplate().update(serviceWorkListsCharge);
		return true;
	}

}
