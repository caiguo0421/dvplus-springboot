package cn.sf_soft.office.approval.dao.hbb;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.AssetsPurchasePlanDao;
import cn.sf_soft.office.approval.model.AssetsPurchasePlan;

/**
 * 资产采购计划的hibernate操作
 * 
 * @author minggo
 * @created 2013-01-15
 */
@Repository("assetsPurchasePlanDao")
public class AssetsPurchasePlanDaoHibernate extends BaseDaoHibernateImpl
		implements AssetsPurchasePlanDao {
	/**
	 * According to the document number inquires the documents and detail
	 */
	@SuppressWarnings("unchecked")
	public AssetsPurchasePlan getDocumentDetail(String documentNo) {
		String hql = "from AssetsPurchasePlan a left join fetch a.chargeDetail where a.documentNo=?";
		List<AssetsPurchasePlan> list = (List<AssetsPurchasePlan>) getHibernateTemplate().find(hql,
				documentNo);
		if (!list.isEmpty())
			return list.get(0);
		return null;
	}

	/**
	 * Update asset purchase schedule
	 */
	public boolean updateAssetsPurchasePlan(
			final AssetsPurchasePlan assetsPurchasePlan) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException {
				session.saveOrUpdate(assetsPurchasePlan);
				return true;
			}
		});
		return false;
	}

}
