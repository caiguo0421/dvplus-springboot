package cn.sf_soft.office.approval.dao.hbb;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.SaleContractsDao;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetCharge;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("saleContractsDao")
public class SaleContractsDaoHibernate extends BaseDaoHibernateImpl implements SaleContractsDao {
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractsDaoHibernate.class);

	@Override
	@SuppressWarnings("unchecked")
	public VehicleSaleContracts getContractByContractNo(String contractNo) {
		String hql = "from VehicleSaleContracts where  contractNo=?";
		List<VehicleSaleContracts> details = (List<VehicleSaleContracts>) getHibernateTemplate().find(hql,
				new Object[] { contractNo });
		if (details == null || details.size() == 0) {
			return null;
		} else if (details.size() == 1) {
			return details.get(0);
		} else {
			logger.error(String.format("%s对应了多个VehicleSaleContract记录", contractNo));
			throw new ServiceException("服务器内部异常");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public VehicleSaleContractDetail getDetailByDocumentNo(String documentNo) {
		String hql = "from VehicleSaleContractDetail  where  documentNo=?";
		List<VehicleSaleContractDetail> details = (List<VehicleSaleContractDetail>) getHibernateTemplate().find(hql,
				new Object[] { documentNo });
		if (details == null || details.size() == 0) {
			return null;
		} else if (details.size() == 1) {
			return details.get(0);
		} else {
			logger.error(String.format("%s对应了多个VehicleSaleContractDetail记录", documentNo));
			throw new ServiceException("服务器内部异常");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public VehicleSaleContractDetail getDetailByContractNo(String contractNo) {
		String hql = "from VehicleSaleContractDetail  where  contractNo=?";
		List<VehicleSaleContractDetail> details = (List<VehicleSaleContractDetail>) getHibernateTemplate().find(hql,
				new Object[] { contractNo });
		if (details == null || details.size() == 0) {
			return null;
		} else if (details.size() == 1) {
			return details.get(0);
		} else {
			logger.error(String.format("%s对应了多个VehicleSaleContractDetail记录", contractNo));
			throw new ServiceException("服务器内部异常");
		}
	}

	@Override
	public List<VehicleSaleContractDetail> getDetailByContractsNo(String contractNo) {
		String hql = "from VehicleSaleContractDetail  where  contractNo=?";
		List<VehicleSaleContractDetail> details = (List<VehicleSaleContractDetail>) getHibernateTemplate().find(hql,
				new Object[] { contractNo });
		if (details == null || details.size() == 0) {
			return null;
		} else {
			return details;
		}
	}

	// //合同主表信息
	// public VehicleSaleContracts getContractsByContractNo(String ContractNo){
	// return
	// }

	// 保险
	@Override
	public List<VehicleSaleContractInsurance> getInsurancesByDetailId(String detailId) {
		String hql = "from VehicleSaleContractInsurance  where contractDetailId=?";
		return (List<VehicleSaleContractInsurance>) getHibernateTemplate().find(hql, new Object[] { detailId });
	}

	@Override
	public List<VehicleSaleContractInsurance> getInsureanceBySaleContractInsuranceId(String saleContractInsuranceId) {
		String hql = "from VehicleSaleContractInsurance  where saleContractInsuranceId=?";
		return (List<VehicleSaleContractInsurance>) getHibernateTemplate().find(hql, new Object[] { saleContractInsuranceId });
	}

	// 精品
	@Override
	public List<VehicleSaleContractPresent> getPresentsByDetailId(String detailId) {
		String hql = "from VehicleSaleContractPresent  where contractDetailId=?";
		return (List<VehicleSaleContractPresent>) getHibernateTemplate().find(hql, new Object[] { detailId });
	}

	@Override
	public VehicleSaleContractPresent getPresentBySaleContractPresentId(String saleContractPresentId) {
		String hql = "from VehicleSaleContractPresent  where saleContractPresentId=?";
		List list = getHibernateTemplate().find(hql, new Object[] { saleContractPresentId });
		return list.size() > 0 ? ((VehicleSaleContractPresent)(list.get(0))) : null;
	}

	// 改装
	@Override
	public List<VehicleSaleContractItem> getItemsByDetailId(String detailId) {
		String hql = "from VehicleSaleContractItem  where contractDetailId=?";
		return (List<VehicleSaleContractItem>) getHibernateTemplate().find(hql, new Object[] { detailId });
	}

	@Override
	public VehicleSaleContractItem getItemBySaleContractItemId(String saleContractItemId) {
		String hql = "from VehicleSaleContractItem  where saleContractItemId=?";
		return (VehicleSaleContractItem) getHibernateTemplate().find(hql, new Object[] { saleContractItemId }).get(0);
	}

	// 其他费用
	@Override
	public List<VehicleSaleContractCharge> getChargesByDetailId(String detailId) {
		String hql = "from VehicleSaleContractCharge  where contractDetailId=?";
		return (List<VehicleSaleContractCharge>) getHibernateTemplate().find(hql, new Object[] { detailId });
	}

	// 赠品
	@Override
	public List<VehicleSaleContractGifts> getGiftsByDetailId(String detailId) {
		String hql = "from VehicleSaleContractGifts  where contractDetailId=?";
		return (List<VehicleSaleContractGifts>) getHibernateTemplate().find(hql, new Object[] { detailId });
	}

	// 发票
	@Override
	public List<VehicleInvoices> getInvoicesByDetailId(String detailId) {
		String hql = "from VehicleInvoices  where contractDetailId=?";
		return (List<VehicleInvoices>) getHibernateTemplate().find(hql, new Object[] { detailId });
	}

	// 车辆消贷
	@Override
	public List<VwVehicleLoanBudgetCharge> getBudgetChargesByDetailId(String detailId) {
		String hql = "from VwVehicleLoanBudgetCharge where saleContractDetailId = ?";
		return (List<VwVehicleLoanBudgetCharge>) getHibernateTemplate().find(hql, new Object[] { detailId });
	}

	// 合同明细视图
	@Override
	public VwVehicleSaleContractDetail getVwContractDetailByDetailId(String detailId) {
		String hql = "from VwVehicleSaleContractDetail where contractDetailId = ?";
		List<VwVehicleSaleContractDetail> details = (List<VwVehicleSaleContractDetail>) getHibernateTemplate().find(
				hql, new Object[] { detailId });
		if (details != null && details.size() > 0) {
			return details.get(0);
		}
		return null;
	}


	// 合同明细视图
	@Override
	public VehicleSaleContractDetail getContractDetailByDetailId(String detailId) {
		String hql = "from VehicleSaleContractDetail where contractDetailId = ?";
		List<VehicleSaleContractDetail> details = (List<VehicleSaleContractDetail>) getHibernateTemplate().find(
				hql, new Object[] { detailId });
		if (details != null && details.size() > 0) {
			return details.get(0);
		}
		return null;
	}

	// 获得车辆设定的最小利润和售价
	@Override
	public List<Map<String, Object>> getVehiclePriceCatlog(String stationId, String selfId) {
		String sql = "SELECT ISNULL(b.profit_min,a.profit_min) profit_min,ISNULL(b.price_sale,a.price_sale) price_sale FROM base_vehicle_model_catalog a LEFT JOIN dbo.base_vehicle_model_catalog_price b ON a.self_id=b.parent_id AND b.station_id=:stationId WHERE a.self_id=:selfId";
		Map<String, Object> params = new HashMap<>();
		params.put("stationId", stationId);
		params.put("selfId", selfId);
		List<Map<String, Object>> catalogPrices = getMapBySQL(sql, params);

		return catalogPrices;

	}

	//获取 凭单支付的费用明细
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractCharge> getPaidCharges(String detailId) {
		String hql = "from VehicleSaleContractCharge  where contractDetailId=? AND  paidByBill = ?";
		List<VehicleSaleContractCharge> charges = (List<VehicleSaleContractCharge>) getHibernateTemplate().find(hql,
				new Object[] { detailId, true });
		return charges;
	}

}
