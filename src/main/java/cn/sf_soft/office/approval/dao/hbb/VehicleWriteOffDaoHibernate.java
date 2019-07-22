package cn.sf_soft.office.approval.dao.hbb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.VehicleWriteOffDao;
import cn.sf_soft.office.approval.model.VehicleInStockDetail;
import cn.sf_soft.office.approval.model.VehicleInStocks;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.office.approval.model.VehicleWriteOff;
import cn.sf_soft.office.approval.model.VehicleWriteOffDetails;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;

@Repository("vehicleWriteOffDao")
public class VehicleWriteOffDaoHibernate extends BaseDaoHibernateImpl implements VehicleWriteOffDao {
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleWriteOffDaoHibernate.class);

	@SuppressWarnings("unchecked")
	@Override
	public VehicleWriteOff getApplyByDocumentNo(String documentNo) {
		String hql = "from VehicleWriteOff where documentNo = ?";
		List<VehicleWriteOff> applys = (List<VehicleWriteOff>) getHibernateTemplate().find(hql,
				new Object[] { documentNo });
		if (applys == null || applys.size() == 0) {
			return null;
		} else if (applys.size() == 1) {
			return applys.get(0);
		} else {
			logger.error(String.format("车辆销账申请: %s 对应了多个VehicleWriteOff记录", documentNo));
			throw new ServiceException("服务器内部异常");
		}
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<VehicleWriteOffDetails>  getVehicleWriteOffDetailsByUnderpan(String documentNo,String underPan){
		String hql = "from VehicleWriteOffDetails  where  documentNo=? and underpanNo = ?";
		List<VehicleWriteOffDetails> details = (List<VehicleWriteOffDetails>) getHibernateTemplate().find(hql,
				new Object[] { documentNo,underPan });
		return details;
	}

	@Override
	public List<Map<String, Object>> getStocksInOtherApprove(String documentNo, String vehicleId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.vehicle_vin,g.status,a.document_no FROM vehicle_write_off_details a").append("\r\n")
				.append("LEFT JOIN vehicle_write_off g ON a.document_no=g.document_no").append("\r\n")
				.append("WHERE a.document_no <>:documentNo").append("\r\n")
				.append("AND g.status IN (20,30,50,60) AND a.vehicle_id = :vehicleId");
		Map<String, Object> params = new HashMap<>();
		params.put("documentNo", documentNo);
		params.put("vehicleId", vehicleId);
		List<Map<String, Object>> stocks = getMapBySQL(sqlBuffer.toString(), params);
		return stocks;
	}
	
	


	/**
	 * 审批同意前先同步入库单数据(金额不同步，以销账的为准)，因为可能存在入库单和销账单同时做单，然后先审入库单再审销账单的情况
	 * 根据销账单中未入库的车辆，获取已经入库的车辆
	 * 
	 * @param lstUnderpanAll
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getVehicleInStock(String lstUnderpanAll) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("SELECT  a.in_stock_detail_id ,h.document_amount AS vehicle_cost_in ,c.vehicle_cost,").append("\r\n")
				.append(" c.vehicle_id,c.vehicle_vin,c.vno_id,c.vehicle_sales_code,c.vehicle_vno,c.vehicle_name,").append("\r\n")
				.append("c.vehicle_strain,c.vehicle_color,c.vehicle_engine_type,c.vehicle_engine_no,c.vehicle_out_factory_time,").append("\r\n")
				.append("c.station_id,c.status,c.vehicle_eligible_no,c.in_stock_no,c.in_stock_time,c.invoice_no,c.warehouse_name,").append("\r\n")
				.append("c.seller,c.vehicle_sale_documents,c.supplier_name,c.supplier_id,d.vehicle_brand ,d.full_no ,d.full_name ,").append("\r\n")
				.append("e.object_no AS supplier_no ,f.station_id AS in_stock_station_id ,g.station_name AS in_stock_station_name ,").append("\r\n")
				.append("ISNULL(n.order_type, '监控车') AS order_type ,ISNULL(n.sap_order_no, c.vehicle_sale_documents) AS sap_order_no").append("\r\n")
				.append(",c.out_stock_time,c.pay_type,c.write_off_date,c.write_off_flag,c.tax_rate,c.underpan_no")
				.append("\r\n").append("FROM    vehicle_in_stock_detail a").append("\r\n")
				.append("INNER JOIN vehicle_in_stocks b ON b.in_stock_no = a.in_stock_no").append("\r\n")
				.append("INNER JOIN vehicle_stocks c ON a.vehicle_id = c.vehicle_id").append("\r\n")
				.append("INNER JOIN vw_vehicle_type d ON c.vno_id = d.vno_id").append("\r\n")
				.append("INNER JOIN base_related_objects e ON c.supplier_id = e.object_id").append("\r\n")
				.append("LEFT JOIN ( SELECT  *").append("\r\n").append("FROM    ( SELECT    x.vehicle_id ,")
				.append("\r\n").append("y.station_id ,").append("\r\n")
				.append("ROW_NUMBER() OVER ( PARTITION BY vehicle_id ORDER BY y.approve_time DESC ) rowNumber")
				.append("\r\n").append("FROM      vehicle_in_stock_detail x").append("\r\n")
				.append("LEFT JOIN dbo.vehicle_in_stocks y ON y.in_stock_no = x.in_stock_no").append("\r\n")
				.append("WHERE     y.approve_status = 1").append("\r\n").append("AND y.in_stock_type = 0")
				.append("\r\n").append(") t").append("\r\n").append("WHERE   rowNumber = 1").append("\r\n")
				.append(") f ON f.vehicle_id = a.vehicle_id").append("\r\n")
				.append("LEFT JOIN dbo.sys_stations g ON f.station_id = g.station_id").append("\r\n")
				.append("LEFT JOIN dbo.finance_document_entries h ON document_id = a.in_stock_detail_id")
				.append("\r\n").append("AND document_type IN (").append("\r\n").append("'车辆-采购入库',").append("\r\n")
				.append("'车辆-监控车入库' )").append("\r\n")
				.append("LEFT JOIN vehicle_DF_sap_delivery AS m ON c.underpan_no = m.underpan_no").append("\r\n")
				.append("LEFT JOIN vehicle_DF_sap_order AS n ON m.sap_order_no = n.sap_order_no").append("\r\n")
				.append("WHERE   b.approve_status = 1").append("\r\n").append("AND ISNULL(a.error_flag, 0) = 0")
				.append("\r\n").append("AND ISNULL(c.pay_type, 0) IN ( 20, 30 )").append("\r\n")
				.append("AND ISNULL(c.write_off_flag, 0) = 0").append("\r\n").append("AND b.in_stock_type = 0")
				.append("\r\n").append("AND a.underpan_no =:lstUnderpanAll").append("\r\n");
		Map<String, Object> params = new HashMap<>();
		params.put("lstUnderpanAll", lstUnderpanAll);
		List<Map<String, Object>> stocks = getMapBySQL(sqlBuffer.toString(), params);
		return stocks;
	}
	
	
	/**
	 * 查询在库存中的底盘号
	 * @param stockDetailIds 入库Id
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getInStockUnderpans(Set<String> stockDetailIds) {
		  StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer
			.append("SELECT a.underpan_no FROM dbo.vehicle_in_stock_detail a").append("\r\n")
			.append(" LEFT JOIN dbo.vehicle_in_stocks b ON b.in_stock_no = a.in_stock_no").append("\r\n")
			.append("  WHERE ISNULL(a.error_flag,0)=0 AND b.approve_status=1 AND a.in_stock_detail_id IN ('");
			for(String stockDetailId:stockDetailIds){
				sqlBuffer.append(stockDetailId).append("','");
			}
			sqlBuffer.append("')").append("\r\n");
			
			List<Map<String, Object>> stocks = getMapBySQL(sqlBuffer.toString(), null);
			return stocks;
	  }
	
	/**
	 * 查询在其他单据中的底盘号
	 * @param lstUnderpan
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getUnderPansInOtherDocument(String documentNo,List<String> lstUnderpan){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
		.append("SELECT underpan_no FROM vehicle_write_off_details a").append("\r\n")
		.append("LEFT JOIN vehicle_write_off g ON a.document_no=g.document_no").append("\r\n")
		.append("WHERE a.document_no <> :documentNo").append("\r\n")
		.append("AND g.status <=60 AND a.underpan_no IN ('");
		for(String underpanStr :lstUnderpan){
			sqlBuffer.append(underpanStr).append("','");
		}
		sqlBuffer.append("')").append("\r\n");
		Map<String, Object> params = new HashMap<>();
		params.put("documentNo", documentNo);
		List<Map<String, Object>> stocks = getMapBySQL(sqlBuffer.toString(), params);
		return stocks;
	}

	@Override
	public List<Map<String, Object>> getDetailCountPerSapNo(String documentNo) {
		Query namedQuery = getSessionFactory().getCurrentSession().getNamedQuery("vehicleInStockDetailView");
		String queryString = namedQuery.getQueryString();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select  v_detail.vehicle_sale_documents,count(*) as detail_count from (").append("\r\n")
		.append(queryString).append("\r\n")
		.append("AND a.document_no = :documentNo)  v_detail").append("\r\n")
		.append("group by v_detail.vehicle_sale_documents").append("\r\n");
		
		Map<String, Object> params = new HashMap<>();
		params.put("documentNo", documentNo);
		List<Map<String, Object>> result = getMapBySQL(sqlBuffer.toString(), params);
		return result;
	}

	@Override
	public List<Map<String, Object>> getDetailByVehicleSaleDocuments(String documentNo, String vehicleSaleDocuments) {
		
		Query namedQuery = getSessionFactory().getCurrentSession().getNamedQuery("vehicleInStockDetailView");
		String queryString = namedQuery.getQueryString();
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from (").append("\r\n")
		.append(queryString).append(")  v_detail").append("\r\n")
		.append("WHERE v_detail.document_no = :documentNo  and  v_detail.vehicle_sale_documents= :vehicleSaleDocuments");
		
		Map<String, Object> params = new HashMap<>();
		params.put("documentNo", documentNo);
		params.put("vehicleSaleDocuments", vehicleSaleDocuments);
		List<Map<String, Object>> result = getMapBySQL(sqlBuffer.toString(), params);
		return result;
	}
	
	@Override
	public void updateVehicleWriteOffDetails(VehicleWriteOffDetails detail){
		update(detail);
	}
	
	/**
	 * 查找采购入库单明细
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<VehicleInStockDetail> getStockDetailByVehicleId(Set<String> inStockDetailIdSet, String sVehicleId){
		String hql = "from VehicleInStockDetail  where inStockDetailId in (:inStockDetailIdSet) and vehicleId=:sVehicleId";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);  
		query.setParameterList("inStockDetailIdSet", inStockDetailIdSet);
		query.setString("sVehicleId", sVehicleId);
		return query.list();
	}
	
	/**
	 * 查找采购入库单
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<VehicleInStocks> getStockByInStockNo(Set<String> inStockDetailIdSet,String sInStockNo){
		String hql = "from  VehicleInStocks  where inStockNo in (select inStockNo from  VehicleInStockDetail where inStockDetailId in (:inStockDetailIdSet)) and inStockNo = :sInStockNo";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql); 
		query.setParameterList("inStockDetailIdSet", inStockDetailIdSet);
		query.setString("sInStockNo", sInStockNo);
		return query.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<VehicleSaleContractDetail> getContractDetailByVehicleId(List<String> lstVehicleId ,String sVehicleId){
		String hql ="from  VehicleSaleContractDetail  where vehicleId in (:lstVehicleId)  and vehicleId = :sVehicleId";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql); 
		query.setParameterList("lstVehicleId", lstVehicleId);
		query.setString("sVehicleId", sVehicleId);
		List<VehicleSaleContractDetail> details = query.list();
		for(int i= details.size()-1;i>=0;i--){
			VehicleSaleContractDetail detail = details.get(i);
			VehicleSaleContracts contract = getHibernateTemplate().get(VehicleSaleContracts.class, detail.getContractNo());
			if(contract== null || (null != contract.getIsSecondhand() && contract.getIsSecondhand()==true)){
				details.remove(i);
			}
		}
		
		return details;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleStocks> getVehicleStocksByVehicleId(List<String> lstVehicleId, String sVehicleId){
		String hql = "from VehicleStocks  where vehicleId in (:lstVehicleId)  and vehicleId = :sVehicleId";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql); 
		query.setParameterList("lstVehicleId", lstVehicleId);
		query.setString("sVehicleId", sVehicleId);
		return query.list();
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BaseRelatedObjects> getRelatedObjectsByObjectNo(String m_sDFObjectNo){
		String hql = "from BaseRelatedObjects where  objectNo = ?";
		List<BaseRelatedObjects> relatedObjects = (List<BaseRelatedObjects>) getHibernateTemplate().find(hql,
				new Object[] { m_sDFObjectNo });
		return relatedObjects;
	}
	
	
	/**
	 * 查询vehicleSaleDocuments
	 * @param applyDetailId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getVehicleSaleDocumentsByDetailId(String applyDetailId){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.*, ISNULL(b.vehicle_sale_documents,m.sap_order_no) AS vehicle_sale_documents").append("\r\n")
		.append("FROM vehicle_write_off_details AS a").append("\r\n")
		.append("LEFT JOIN vehicle_in_stock_detail b ON b.in_stock_detail_id=a.in_stock_detail_id").append("\r\n")
		.append("LEFT JOIN vehicle_DF_sap_delivery AS m ON a.underpan_no = m.underpan_no").append("\r\n")
		.append("WHERE a.apply_detail_id=:applyDetailId").append("\r\n");
		Map<String, Object> params = new HashMap<>();
		params.put("applyDetailId", applyDetailId);
		List<Map<String, Object>> result = getMapBySQL(sqlBuffer.toString(), params);
		return result;
	}
	
}
