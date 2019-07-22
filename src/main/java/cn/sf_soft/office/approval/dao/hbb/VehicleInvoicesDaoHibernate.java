package cn.sf_soft.office.approval.dao.hbb;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.VehicleInvoicesDao;
import cn.sf_soft.office.approval.model.VehicleInvoices;

@Repository("vehicleInvoicesDao")
public class VehicleInvoicesDaoHibernate extends BaseDaoHibernateImpl implements VehicleInvoicesDao {

	// 根据合同明细查找购车发票
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleInvoices> getVehicleInvoicesByContractId(String contractDetailId) {
		String hql = "from VehicleInvoices where contractDetailId = ?  AND invoiceType = '购车发票'";
		List<VehicleInvoices> invoinces = (List<VehicleInvoices>) getHibernateTemplate().find(hql,
				new Object[] { contractDetailId });
		return invoinces;
	}

}
