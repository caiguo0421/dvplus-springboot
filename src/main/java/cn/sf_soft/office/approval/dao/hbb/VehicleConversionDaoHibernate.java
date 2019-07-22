package cn.sf_soft.office.approval.dao.hbb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sf_soft.office.approval.model.*;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.VehicleConversionDao;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;

@Repository("vehicleConversionDao")
public class VehicleConversionDaoHibernate extends BaseDaoHibernateImpl implements VehicleConversionDao {
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleConversionDaoHibernate.class);

	// 根据单号查询记录
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleConversion> getConversionsByDocumentNo(String documentNo) {
		String hql = "from VehicleConversion  where  documentNo= ? OR masterNo= ?";
		List<VehicleConversion> conversions = (List<VehicleConversion>) getHibernateTemplate().find(hql,
				new Object[] { documentNo, documentNo });
		if (conversions == null || conversions.size() == 0) {
			return null;
		} else {
			return conversions;
		}
	}

	@Override
	public VehicleConversionMaster getConversionMasterByDocumentNo(String documentNo) {
		String hql = "from VehicleConversionMaster where  documentNo= ?";
		List<VehicleConversionMaster> masters = (List<VehicleConversionMaster>) getHibernateTemplate().find(hql,
				new Object[] { documentNo });
		if (masters == null || masters.size() == 0) {
			return null;
		} else {
			return masters.get(0);
		}
	}

	// 根据拆装类型 找到拆装明细
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSuperstructureRemoveAndInstalls> getSuperstructureByInstallType(VehicleConversion conversion,
			String installType) {
		String hql = "from  VehicleSuperstructureRemoveAndInstalls where conversionNo = ? and installType = ?";
		List<VehicleSuperstructureRemoveAndInstalls> items = (List<VehicleSuperstructureRemoveAndInstalls>) getHibernateTemplate()
				.find(hql, new Object[] { conversion.getConversionNo(), installType });
		return items;
	}

	// 查找有相同底盘号的改装单
	@Override
	public List<Map<String, Object>> getOtherConversionsByVin(String conversionNo, String vehicleVin) {
		String sql = "SELECT conversion_no FROM  vehicle_conversion_detail "
				+ " WHERE conversion_no <> :conversionNo AND vehicle_vin = :vehicleVin  AND in_detail_id IN (SELECT isnull(in_detail_id,'') FROM vehicle_conversion_detail "
				+ " WHERE conversion_no = :conversionNo) AND (status = 1 OR status = 2 OR status=30 OR status=50 )";
		Map<String, Object> params = new HashMap<>();
		params.put("conversionNo", conversionNo);
		params.put("vehicleVin", vehicleVin);
		List<Map<String, Object>> conversions = getMapBySQL(sql, params);
		return conversions;
	}

	// 查找改装明细对应的合同改装明细
	@Override
	public List<Map<String, Object>> getSaleContractItems(VehicleConversionDetail detail) {
		String sSqlTemp = "SELECT sale_contract_item_id FROM vehicle_sale_contract_item a INNER JOIN vehicle_sale_contract_detail b ON a.contract_detail_id=b.contract_detail_id WHERE a.sale_contract_item_id= :saleContractItemId AND a.item_id= :itemId AND isnull(b.abort_status,0)<>10";
		Map<String, Object> params = new HashMap<>();
		params.put("saleContractItemId", detail.getInDetailId());
		params.put("itemId", detail.getItemId());
		return getMapBySQL(sSqlTemp, params);
	}

	// 查找上装的加装项目
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSuperstructureRemoveAndInstalls> getVehicleInstallItems(VehicleConversion conversion) {
		String hql = "from VehicleSuperstructureRemoveAndInstalls where conversionNo = ? and installType = ? and itemType = ?";
		List<VehicleSuperstructureRemoveAndInstalls> items = (List<VehicleSuperstructureRemoveAndInstalls>) getHibernateTemplate()
				.find(hql, new Object[] { conversion.getConversionNo(), "10", (short) 10 });
		return items;
	}

	// 查找对应的车辆销售合同明细
	// @SuppressWarnings("unchecked")
	// @Override
	// public List<VehicleSaleContractDetail>
	// getContractDetailByVehicleId(String vehicleId, boolean isSecondHand) {
	// String hql =
	// "from VehicleSaleContractDetail WHERE vehicleId = ? AND contractNo in (select contractNo from VehicleSaleContracts where ISNULL(isSecondhand,false) = ?)";
	// List<VehicleSaleContractDetail> list = (List<VehicleSaleContractDetail>)
	// getHibernateTemplate().find(hql,
	// new Object[] { vehicleId, isSecondHand });
	// return list;
	// }

	

	// 查找对应合同的车辆改装明细
	// @Override
	// @SuppressWarnings("unchecked")
	// public List<VehicleSaleContractItem> getContractItemsByVehicleId(String
	// vehicleId, boolean isSecondHand) {
	// String hql =
	// " from VehicleSaleContractItem  where contractDetailId IN (SELECT contractDetailId FROM VehicleSaleContractDetail WHERE vehicleId=? AND contractNo IN (SELECT contractNo FROM VehicleSaleContracts WHERE ISNULL(isSecondhand,false) = ?))";
	// List<VehicleSaleContractItem> list = (List<VehicleSaleContractItem>)
	// getHibernateTemplate().find(hql,
	// new Object[] { vehicleId, isSecondHand });
	// return list;
	// }

	

	// 查找对应改装的改装明细
	@Override
	@SuppressWarnings("unchecked")
	public List<VehicleConversionDetail> getConversionDetailsByVehicleId(String vehicleId, boolean isSecondhand,
			List<String> saleContractItemIds) {
		String hql = "from VehicleConversionDetail WHERE status IN (1,2,30,50) AND vehicleId = :vehicleId AND conversionNo IN (SELECT contractNo FROM VehicleSaleContracts WHERE ISNULL(isSecondhand,false) = :isSecondhand) AND saleContractItemId NOT IN (:saleContractItemIds)";
		// List<VehicleConversionDetail> list = (List<VehicleConversionDetail>)
		// getHibernateTemplate().find(hql,
		// new Object[] { vehicleId, isSecondHand,saleContractItemIds });
		// Query query = getSession().createQuery(hql);

		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		query.setString("vehicleId", vehicleId);
		query.setBoolean("isSecondhand", isSecondhand);
		query.setParameterList("saleContractItemIds", saleContractItemIds);
		return query.list();
	}

	// 该车已经加装或拆装的成本之和
	@Override
	public List<Map<String, Object>> getVsriAmountByVehicleId(String vehicleId, boolean isSecondHand) {
		String sql = "SELECT vehicle_id,SUM(vsri_amount) AS vsri_amount FROM ("
				+ "  SELECT b.vehicle_id, CASE WHEN a.install_type=10 THEN ISNULL(a.item_cost,0)*ISNULL(a.quantity,0) ELSE"
				+ "  ISNULL(a.item_cost,0)*ISNULL(a.quantity,0)*-1 END AS vsri_amount"
				+ "  FROM vehicle_superstructure_remove_and_installs a"
				+ "  INNER JOIN dbo.vehicle_conversion b ON b.conversion_no = a.conversion_no"
				+ "  WHERE a.status IN (10) AND b.status=60 AND b.vehicle_id=:vehicleId AND isnull(b.is_secondhand,0)=:isSecondHand) a GROUP BY vehicle_id";
		Map<String, Object> params = new HashMap<>();
		params.put("vehicleId", vehicleId);
		params.put("isSecondHand", isSecondHand ? 1 : 0);
		List<Map<String, Object>> vsriAmounts = getMapBySQL(sql, params);
		return vsriAmounts;
	}

	// 计算实际改装金额
	@Override
	public List<Map<String, Object>> getRealCost(String vehicleId, boolean isSecondHand) {
		String sql = "SELECT SUM(ISNULL(item_cost,0)) AS item_cost,SUM(ISNULL(item_cost_ori,0)) AS item_cost_ori FROM vehicle_conversion_detail"
				+ " WHERE status =2 AND vehicle_id = :vehicleId "
				+ " AND conversion_no IN "
				+ "( SELECT conversion_no FROM dbo.vehicle_conversion WHERE ISNULL(is_secondhand, 0) = :isSecondHand)";
		Map<String, Object> params = new HashMap<>();
		params.put("vehicleId", vehicleId);
		params.put("isSecondHand", isSecondHand ? 1 : 0);
		List<Map<String, Object>> realCost = getMapBySQL(sql, params);
		return realCost;
	}

	// 查找对应的库存车
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleStocks> getVehicleStocksByVin(String vehicleVin) {
		String hql = "from VehicleStocks  where vehicleVin = ? and (status = 0 OR  status=1 OR status = 2 OR status = 3) ";
		List<VehicleStocks> list = (List<VehicleStocks>) getHibernateTemplate().find(hql, new Object[] { vehicleVin });
		return list;
	}

	// 查找对应的二手车库存
	@SuppressWarnings("unchecked")
	@Override
	public List<SecondhandVehicleStocks> getSecondhandVehicleStocksByVin(String vehicleVin) {
		String hql = "from SecondhandVehicleStocks  where vehicleVin = ? and (status = 0 OR  status=1 OR status = 2 OR status = 3) ";
		List<SecondhandVehicleStocks> list = (List<SecondhandVehicleStocks>) getHibernateTemplate().find(hql,
				new Object[] { vehicleVin });
		return list;
	}

	// 根据合同明细Id和 类型查找销售合同对应的单据分录
	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceDocumentEntries> getDocumentEntriesByType(String vscdId, String saleDocumentType) {
		String hql = "from FinanceDocumentEntries where documentType = ? and documentNo in  (select  contractNo from VehicleSaleContractDetail where contractDetailId = ?) ";
		List<FinanceDocumentEntries> list = (List<FinanceDocumentEntries>) getHibernateTemplate().find(hql,
				new Object[] { saleDocumentType, vscdId });
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceDocumentEntries> getDocumentEntries(String vscdId) {
		String hql = "from FinanceDocumentEntries where documentType in ('车辆-销售合同','车辆-销售合同变更') and documentNo in  (select  contractNo from VehicleSaleContractDetail where contractDetailId = ?) ";
		List<FinanceDocumentEntries> list = (List<FinanceDocumentEntries>) getHibernateTemplate().find(hql,
				new Object[] { vscdId });
		return list;
	}

	// 查找车辆销售合同的内容
	@Override
	public List<Map<String, Object>> getContractInfo(String vscdId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("SELECT  a.contract_no,a.station_id,a.customer_id,a.customer_no,a.customer_name,")
				.append("\r\n")
				.append("is_secondhand,b.plan_deliver_time,ISNULL(a.contract_money,0)-ISNULL(a.loan_amount_totv,0) AS frist_amount")
				.append("\r\n").append("FROM    dbo.vw_vehicle_sale_contracts a").append("\r\n")
				.append("LEFT JOIN ( SELECT  contract_no ,").append("\r\n")
				.append("MAX(plan_deliver_time) plan_deliver_time").append("\r\n")
				.append("FROM    dbo.vehicle_sale_contract_detail").append("\r\n")
				.append("WHERE   approve_status IN ( 0, 1, 2, 20 )").append("\r\n").append("GROUP BY contract_no")
				.append("\r\n").append(") b ON a.contract_no = b.contract_no").append("\r\n")
				.append("WHERE   a.contract_no IN ( SELECT contract_no").append("\r\n")
				.append("FROM   dbo.vehicle_sale_contract_detail").append("\r\n")
				.append("WHERE  contract_detail_id = :vscdId)").append("\r\n");
		Map<String, Object> params = new HashMap<>();
		params.put("vscdId", vscdId);
		//logger.debug(sqlBuffer.toString()+"  vscdId:"+vscdId);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;

	}

	// 根据entryId 查找请款金额合计
	@Override
	public List<Map<String, Object>> getRequestAmountByEntryId(String entryId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("SELECT a.entry_id,SUM(a.request_amount) request_amount FROM dbo.finance_payment_requests_details a")
				.append("\r\n")
				.append("INNER JOIN dbo.finance_payment_requests b ON b.document_no = a.document_no  WHERE  b.status=50 and a.entry_id =:entryId")
				.append("\r\n").append("GROUP BY a.entry_id").append("\r\n");
		Map<String, Object> params = new HashMap<>();
		params.put("entryId", entryId);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}
	
	
	//根据车辆Id查找合同
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContracts> getVscList(String vehicleId){
		//String hql = "from VehicleSaleContracts  WHERE contractStatus<>3 AND contractStatus<>4 AND contractNo IN (select contractNo from VehicleSaleContractDetail  WHERE approveStatus IN (0,1,2,20) AND vehicleId=?) ";
		String hql = "from VehicleSaleContracts  WHERE contractStatus<>3 AND contractStatus<>4 AND contractNo IN (select contractNo from VehicleSaleContractDetail WHERE  vehicleId=? and approveStatus IN (0,1,2,20) ) ";
		List<VehicleSaleContracts> list = (List<VehicleSaleContracts>) getHibernateTemplate().find(hql,
				new Object[] { vehicleId });
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractDetail> getVscdList(String vehicleId){
		String hql = "from  VehicleSaleContractDetail where vehicleId = ? AND approveStatus IN (0,1,2,20)";
		List<VehicleSaleContractDetail> list = (List<VehicleSaleContractDetail>) getHibernateTemplate().find(hql,
				new Object[] { vehicleId });
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractItem> getContractItemsByVehicleId(String vehicleId){
		String hql = "from  VehicleSaleContractItem  where  contractDetailId in (select contractDetailId from  VehicleSaleContractDetail where vehicleId = ? AND approveStatus IN (0,1,2,20))";
		List<VehicleSaleContractItem> list = (List<VehicleSaleContractItem>) getHibernateTemplate().find(hql,
				new Object[] { vehicleId });
		return list;
	}

}
