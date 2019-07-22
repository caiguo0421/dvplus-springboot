package cn.sf_soft.office.approval.dao.hbb;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.VehicleSaleMarketingActivityDao;
import cn.sf_soft.office.approval.model.VehicleSaleMarketingActivity;

/**
 * 营销活动管理审批hibernate操作
 * @author king
 * @created 2013-02-21
 */
@Repository("vehicleSaleMarketingActivityDao")
public class VehicleSaleMarketingActivityDaoHibernate extends
BaseDaoHibernateImpl implements VehicleSaleMarketingActivityDao {
	/**
	 * According to the single number inquires the documents
	 */
	@SuppressWarnings("unchecked")
	public VehicleSaleMarketingActivity getDocumentDetail(String documentNo) {
		String hql = "from VehicleSaleMarketingActivity v left join fetch v.chargeDetail left join fetch v.activitySupporters where v.documentNo=?";
		List<VehicleSaleMarketingActivity> list = (List<VehicleSaleMarketingActivity>) getHibernateTemplate().find(hql,documentNo);
		if(!list.isEmpty())
			return list.get(0);
		return null;
	}
	/**
	 * Update marketing activities documents information
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean updateVehicleSaleMarketingActivity(
			final VehicleSaleMarketingActivity vehicleSaleMarketingActivity) {
		getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException {
				session.saveOrUpdate(vehicleSaleMarketingActivity);
				return true;
			}
		});
		return false;
	}

}
