package cn.sf_soft.office.approval.dao.hbb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.SaleContractsVaryDao;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleInvoices;
import cn.sf_soft.office.approval.model.VehicleInvoicesVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractCharge;
import cn.sf_soft.office.approval.model.VehicleSaleContractChargeVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractGiftsVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractInsuranceVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractItemVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractPresent;
import cn.sf_soft.office.approval.model.VehicleSaleContractPresentVary;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.office.approval.model.VehicleSaleContractsVary;

@Repository("saleContractsVaryDao")
public  class SaleContractsVaryDaoHibernate extends BaseDaoHibernateImpl implements SaleContractsVaryDao {
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractsVaryDaoHibernate.class);
	@Override
	@SuppressWarnings("unchecked")
	public List<VehicleSaleContractDetailVary> getContractDetailVaryByDocumentNo(String documentNo) {
		String hql = "from  VehicleSaleContractDetailVary  where documentNo = ?";
		List<VehicleSaleContractDetailVary> details = (List<VehicleSaleContractDetailVary>) getHibernateTemplate()
				.find(hql, new Object[] { documentNo });
		return details;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractsVary> getContractVeryInOtherDocument(String contractNo, String documentNo) {
		String hql = "from VehicleSaleContractsVary  where contractNo = ? and documentNo<> ? AND status IN (5,10,20,30)";
		List<VehicleSaleContractsVary> results = (List<VehicleSaleContractsVary>) getHibernateTemplate().find(hql,
				new Object[] { contractNo,documentNo });
		return results;
	}

	@Override
	public List<Map<String, Object>> getOriChargeListByCondition(String condition, Map<String, Object> params) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("SELECT * from (")
				.append("SELECT a.sale_contract_charge_id,a.contract_detail_id,a.paid_by_bill,a.charge_id,a.charge_name,a.cost_status,a.charge_cost,")
				.append("\r\n")
				.append("d.document_amount,d.left_amount,d.used_credit,d.after_no,")
				.append("\r\n")
				.append("CASE WHEN ISNULL(d.document_amount,0)-ISNULL(d.left_amount,0)+ISNULL(d.used_credit,0)>ISNULL(d.document_amount,0) THEN ISNULL(d.document_amount,0)")
				.append("\r\n")
				.append("ELSE ISNULL(d.document_amount,0)-ISNULL(d.left_amount,0)+ISNULL(d.used_credit,0) END AS paidAmount")
				.append("\r\n").append("FROM dbo.vehicle_sale_contract_charge a").append("\r\n")
				.append("LEFT JOIN dbo.vehicle_sale_contract_detail b ON a.contract_detail_id=b.contract_detail_id")
				.append("\r\n").append("LEFT JOIN dbo.vehicle_sale_contracts c ON b.contract_no=c.contract_no ")
				.append("\r\n")
				.append("LEFT JOIN dbo.finance_document_entries d ON a.sale_contract_charge_id=d.document_id")
				.append("\r\n")
				.append("WHERE b.approve_status IN (0,1,2,20) AND c.contract_status<>3 AND c.contract_status<>4")
				.append("\r\n").append("AND c.contract_no= :contractNo").append("\r\n").append("\r\n")
				.append(") as a  where 1=1 ").append("\r\n");

		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.append(condition).toString(), params);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractInsuranceVary> getInsuranceVaryByDetailVaryId(String detailVaryId) {
		String hql = " from VehicleSaleContractInsuranceVary  where detailVaryId = ?";
		List<VehicleSaleContractInsuranceVary> results = (List<VehicleSaleContractInsuranceVary>) getHibernateTemplate()
				.find(hql, new Object[] { detailVaryId });
		return results;
	}

	@Override
	public List<Map<String, Object>> getInsuranceListByCondition(String condition, Map<String, Object> params) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("select * from (")
				.append("\r\n")
				.append("SELECT contract_detail_id,vehicle_vin,b.sale_contract_insurance_id FROM dbo.insurance a LEFT JOIN dbo.insurance_detail b ON b.insurance_no = a.insurance_no WHERE approve_status IN (0,1) ) as a WHERE 1=1 ")
				.append("\r\n");
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.append(condition).toString(), params);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractPresentVary> getPresentVaryByDetailVaryId(String detailVaryId) {
		String hql = "from VehicleSaleContractPresentVary where detailVaryId = ? ";
		List<VehicleSaleContractPresentVary> results = (List<VehicleSaleContractPresentVary>) getHibernateTemplate()
				.find(hql, new Object[] { detailVaryId });
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractItemVary> getItemVaryByDetailVaryId(String detailVaryId) {
		String hql = "from VehicleSaleContractItemVary where detailVaryId = ? ";
		List<VehicleSaleContractItemVary> results = (List<VehicleSaleContractItemVary>) getHibernateTemplate().find(
				hql, new Object[] { detailVaryId });
		return results;
	}

	@Override
	public List<Map<String, Object>> getConversionListByCondition(String condition, Map<String, Object> params) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("select *  from (")
				.append("\r\n")
				.append("SELECT a.vehicle_vin,b.vscd_id,a.item_id,a.item_name,a.item_cost,a.sale_contract_item_id,a.status FROM dbo.vehicle_conversion_detail a  ")
				.append("\r\n").append("LEFT JOIN dbo.vehicle_conversion b ON b.conversion_no = a.conversion_no")
				.append("\r\n").append("WHERE  a.status<> 3 AND a.status<> 4 AND b.status<=60 ) as a  where 1=1")
				.append("\r\n");

		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.append(condition).toString(), params);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractGiftsVary> getGiftVaryByDetailVaryId(String detailVaryId) {
		String hql = "from VehicleSaleContractGiftsVary where detailVaryId = ? ";
		List<VehicleSaleContractGiftsVary> results = (List<VehicleSaleContractGiftsVary>) getHibernateTemplate().find(
				hql, new Object[] { detailVaryId });
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractChargeVary> getChargeVaryByDetailVaryId(String detailVaryId) {
		String hql = "from  VehicleSaleContractChargeVary  where detailVaryId = ?";
		List<VehicleSaleContractChargeVary> results = (List<VehicleSaleContractChargeVary>) getHibernateTemplate()
				.find(hql, new Object[] { detailVaryId });
		return results;
	}

	@Override
	public List<Map<String, Object>> getoutStocksInTime(String contractDetailId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT  a.contract_detail_id FROM    vw_vehicle_sale_contract_detail a").append("\r\n");
		sqlBuffer.append("LEFT JOIN ( SELECT  a.vehicle_id , b.contract_no , b.approve_time").append("\r\n");
		sqlBuffer.append("FROM    dbo.vehicle_out_stock_detail a").append("\r\n");
		sqlBuffer.append("LEFT JOIN dbo.vehicle_out_stocks b ON a.out_stock_no = b.out_stock_no").append("\r\n");
		sqlBuffer.append(" WHERE   b.approve_status = 1").append("\r\n");
		sqlBuffer.append("AND ISNULL(a.error_flag, 0) = 0").append("\r\n");
		sqlBuffer.append(") b ON b.vehicle_id = a.vehicle_id  AND a.contract_no = b.contract_no").append("\r\n");
		sqlBuffer.append("WHERE   ( abort_status <> 10OR abort_status IS NULL)").append("\r\n");
		sqlBuffer.append("AND approve_status = 1 AND ( a.real_deliver_time IS NULL").append("\r\n");
		sqlBuffer.append("OR ( a.real_deliver_time IS NOT NULL").append("\r\n");
		sqlBuffer.append("AND DATEDIFF(MONTH, b.approve_time, GETDATE()) = 0 ").append("\r\n");
		sqlBuffer.append("AND DATEDIFF(DAY, b.approve_time, GETDATE()) < ( SELECT option_value FROM dbo.sys_options WHERE option_no = 'VEHICLE_OUT_CAN_ADD_CHARGE'AND station_id = a.station_id)")
				.append("\r\n");
		sqlBuffer.append(")").append("\r\n");
		sqlBuffer.append(") AND a.contract_detail_id = :contractDetailId").append("\r\n");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractDetailId", contractDetailId);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceDocumentEntries> getBrokerageEntriesByDocumentId(String contractDetailId) {
		String hql = "from  FinanceDocumentEntries   WHERE documentType='车辆-客户佣金' AND documentId =?";
		List<FinanceDocumentEntries> results = (List<FinanceDocumentEntries>) getHibernateTemplate()
				.find(hql, new Object[] { contractDetailId });
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractPresent> getOutPresent(String contractDetailId) {
		String hql = "from  VehicleSaleContractPresent   WHERE contractDetailId = ? AND isnull(getQuantity,0)>0";
		List<VehicleSaleContractPresent> results = (List<VehicleSaleContractPresent>) getHibernateTemplate()
				.find(hql, new Object[] { contractDetailId });
		return results;
	}

	@Override
	public List<Map<String, Object>> getInvoiceListByCondition(String condition, Map<String, Object> params) {
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT * FROM (SELECT a.invoices_detail_vary_id,a.contract_detail_id,b.after_no FROM dbo.vehicle_invoices_vary a").append("\r\n");
		sqlBuffer.append("LEFT JOIN dbo.finance_document_entries b ON a.invoices_detail_id=b.document_id").append("\r\n");
		sqlBuffer.append("WHERE  entry_property&4=4 AND ISNULL(a.abort_status,20)=10 ) AS a WHERE 1=1").append("\r\n");
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.append(condition).toString(), params);
		return list;	
	}

	@Override
	public List<Map<String, Object>> getBudgetListByCondition(String condition, Map<String, Object> params) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT * from (SELECT a.sale_contract_detail_id ,a.loan_amount,a.vehicle_price_total,a.vno_id FROM dbo.vehicle_loan_budget_details a").append("\r\n");
		sqlBuffer.append("LEFT JOIN dbo.vehicle_loan_budget b ON a.document_no = b.document_no").append("\r\n");
		sqlBuffer.append("WHERE b.flow_status IN (0,1,30,40,45,50,60,70) ) AS a where 1=1").append("\r\n");
		
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.append(condition).toString(), params);
		return list;	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleInvoicesVary> getInvoiceVaryByContractDetailId(String contractDetailId,String detailVaryId) {
		String hql = " from VehicleInvoicesVary  where contractDetailId = ? AND detailVaryId = ?";
		List<VehicleInvoicesVary> results = (List<VehicleInvoicesVary>) getHibernateTemplate()
				.find(hql, new Object[] { contractDetailId,detailVaryId });
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleInvoices> getOriInvoiceByContractDetailId(String contractDetailId) {
		String hql = " from VehicleInvoices  where contractDetailId = ?";
		List<VehicleInvoices> results = (List<VehicleInvoices>) getHibernateTemplate()
				.find(hql, new Object[] { contractDetailId });
		return results;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleInvoicesVary> getVehicleInvoiceVaryList(String contractDetailId) {
		String hql = " from VehicleInvoicesVary  where contractDetailId = ? AND invoiceType ='购车发票' AND ISNULL(abortStatus,20)<>10";
		List<VehicleInvoicesVary> results = (List<VehicleInvoicesVary>) getHibernateTemplate()
				.find(hql, new Object[] { contractDetailId });
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleInvoices> getOriInvoiceNotInVary(String contractDetailId) {
		String hql = "from VehicleInvoices AS a  where  a.contractDetailId = ? AND NOT Exists (select 1 from VehicleInvoicesVary as b where a.invoicesDetailId =b.invoicesDetailId)";
		List<VehicleInvoices> results = (List<VehicleInvoices>) getHibernateTemplate()
				.find(hql, new Object[] { contractDetailId });
		return results;
	}

	@Override
	public List<Map<String, Object>> getVehicleInOtherContract(String vehicleVin, String contractNo) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.contract_no,a.vehicle_vin FROM dbo.vehicle_sale_contract_detail a").append("\r\n");
		sqlBuffer.append("INNER JOIN dbo.vehicle_sale_contracts b ON a.contract_no=b.contract_no").append("\r\n");
		sqlBuffer.append("WHERE a.approve_status<>30 AND a.approve_status<>40 AND b.contract_status<>3 AND b.contract_quantity<>4 AND a.contract_no<>:contractNo AND a.vehicle_vin =:vehicleVin").append("\r\n");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractNo", contractNo);
		params.put("vehicleVin", vehicleVin);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public VehicleSaleContracts getSaleContractsByContractNo(String contractNo) {
		String hql = "from VehicleSaleContracts  where contractNo = ?";
		List<VehicleSaleContracts> results = (List<VehicleSaleContracts>) getHibernateTemplate()
				.find(hql, new Object[] { contractNo });
		if(results!=null && results.size()>0){
			return results.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractDetail> getVehicleOriContractDetailList(String vehicleVin) {
		String hql = "from VehicleSaleContractDetail where vehicleVin = ? and (abortStatus<>10 OR abortStatus is null)";
		List<VehicleSaleContractDetail> results = (List<VehicleSaleContractDetail>) getHibernateTemplate()
				.find(hql, new Object[] { vehicleVin });
		return results;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractDetail> getOriDetailListByRelation(String contractDetailId) {
		String hql = "from VehicleSaleContractDetail where relationDetailId = ? ";
		List<VehicleSaleContractDetail> results = (List<VehicleSaleContractDetail>) getHibernateTemplate()
				.find(hql, new Object[] { contractDetailId });
		return results;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleInvoices> getVehicleInvoiceList(String contractDetailId) {
		String hql = " from VehicleInvoices  where contractDetailId = ? AND invoiceType ='购车发票' ";
		List<VehicleInvoices> results = (List<VehicleInvoices>) getHibernateTemplate()
				.find(hql, new Object[] { contractDetailId });
		return results;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractCharge> getOriChargeListByChargeName(String contractDetailId, String sPurchaseTaxName) {
		String hql = "from VehicleSaleContractCharge  where contractDetailId = ? AND chargeName = ?";
		
		List<VehicleSaleContractCharge> results = (List<VehicleSaleContractCharge>) getHibernateTemplate()
				.find(hql, new Object[] { contractDetailId,sPurchaseTaxName });
		return results;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleSaleContractChargeVary> getChargeVaryListByChargeName(String contractDetailId,String sPurchaseTaxName) {
		String hql = "from VehicleSaleContractChargeVary  where contractDetailId = ? AND chargeName = ? AND  ISNULL(abortStatus,20)<>10";
		
		List<VehicleSaleContractChargeVary> results = (List<VehicleSaleContractChargeVary>) getHibernateTemplate()
				.find(hql, new Object[] { contractDetailId,sPurchaseTaxName });
		return results;
	}

	@Override
	public List<Map<String, Object>> getSumLoanAmount(String contractDetailId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT SUM(a.loan_amount) loan_amount FROM dbo.vehicle_loan_budget_details a").append("\r\n");
		sqlBuffer.append("LEFT JOIN dbo.vehicle_loan_budget b ON b.document_no = a.document_no").append("\r\n");
		sqlBuffer.append("WHERE b.flow_status IN (0,1,30,40,45,50,60,70) AND a.status=1 AND a.sale_contract_detail_id =:contractDetailId").append("\r\n");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractDetailId",contractDetailId);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@Override
	public List<Map<String, Object>> getInsuranceAmountTotal(String contractNo) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT SUM(ISNULL(category_income,0)) category_income FROM vehicle_sale_contract_insurance ").append("\r\n");
		sqlBuffer.append("WHERE contract_detail_id IN (").append("\r\n");
		sqlBuffer.append("SELECT isnull(a.contract_detail_id,'') FROM vehicle_sale_contract_detail a INNER JOIN vehicle_sale_contracts b ON a.contract_no=b.contract_no WHERE isnull(a.abort_status,20)<>10 AND b.contract_status<>3 AND b.contract_status<>4 AND b.contract_no=:contractNo AND isnull(a.is_contain_insurance_cost,0)=0 ) AND isnull(abort_status,20)<>10 ").append("\r\n");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractNo",contractNo);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@Override
	public List<Map<String, Object>> getInstDocumentList(String documentNo) {
		StringBuffer sqlBuffer = new StringBuffer();
		/**
		 *  检查合同明细变更中的is_contain_insurance_cost 和 ori_is_contain_insurance_cost 来判断“含保费”是否变更
		 *  caigx 20170309
		 */
		sqlBuffer.append("SELECT a.is_contain_insurance_cost AS is_contain_v,a.ori_is_contain_insurance_cost AS is_contain_s, ").append("\r\n");
		sqlBuffer.append("c.station_id,c.vehicle_id,c.vehicle_vin,c.contract_detail_id,c.insurance_no,c.supplier_id,c.supplier_no,c.supplier_name,").append("\r\n");
		sqlBuffer.append("c.customer_id,c.customer_no,c.customer_name,c.insurance_pay_mode,c.insurance_income,c.rebate_receivable_date,").append("\r\n");
		sqlBuffer.append("c.return_money_insurance,c.contract_no,c.payables_date").append("\r\n");
		sqlBuffer.append(",d.document_id,d.document_type,d.after_no FROM dbo.vehicle_sale_contract_detail_vary  a ").append("\r\n");
		//sqlBuffer.append("INNER JOIN dbo.vehicle_sale_contract_detail b ON a.contract_no=b.contract_no AND a.contract_detail_id=b.contract_detail_id").append("\r\n");
		sqlBuffer.append("INNER JOIN dbo.insurance c ON a.contract_detail_id=c.contract_detail_id AND a.contract_no=c.contract_no AND c.approve_status IN (0,1)").append("\r\n");
		sqlBuffer.append("LEFT JOIN dbo.finance_document_entries d ON d.document_id=(c.insurance_no+','+c.vehicle_vin) AND document_type IN ('保险-购买应收','保险-购买代收')").append("\r\n");
		sqlBuffer.append("WHERE ISNULL(a.is_contain_insurance_cost,0)<>ISNULL(a.ori_is_contain_insurance_cost,0)").append("\r\n");
		sqlBuffer.append("AND c.approve_status IN (0,1) AND a.document_no=:documentNo").append("\r\n");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("documentNo",documentNo);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@Override
	public List<Map<String, Object>> getSaleBillDocument(String contractNo) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.*,b.request_amount FROM dbo.finance_document_entries a").append("\r\n");
		sqlBuffer.append("LEFT JOIN(").append("\r\n");
		sqlBuffer.append("SELECT a.entry_id,SUM(a.request_amount) request_amount FROM dbo.finance_payment_requests_details a").append("\r\n");
		sqlBuffer.append("INNER JOIN dbo.finance_payment_requests b ON b.document_no = a.document_no AND b.status=50").append("\r\n");
		sqlBuffer.append("GROUP BY a.entry_id").append("\r\n");
		sqlBuffer.append(") b ON a.entry_id=b.entry_id").append("\r\n");
		sqlBuffer.append("WHERE a.document_type IN ('车辆-销售合同') AND a.document_no =:contractNo").append("\r\n");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractNo",contractNo);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	

	@Override
	public List<Map<String, Object>> getSaleBillDocumentVary(String contractNo) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.*,b.request_amount FROM dbo.finance_document_entries a").append("\r\n");
		sqlBuffer.append("LEFT JOIN(").append("\r\n");
		sqlBuffer.append("SELECT a.entry_id,SUM(a.request_amount) request_amount FROM dbo.finance_payment_requests_details a").append("\r\n");
		sqlBuffer.append("INNER JOIN dbo.finance_payment_requests b ON b.document_no = a.document_no AND b.status=50").append("\r\n");
		sqlBuffer.append("GROUP BY a.entry_id").append("\r\n");
		sqlBuffer.append(") b ON a.entry_id=b.entry_id").append("\r\n");
		sqlBuffer.append("WHERE a.document_type IN ('车辆-销售合同变更') AND a.document_no =:contractNo").append("\r\n");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractNo",contractNo);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@Override
	public List<Map<String, Object>> getBudgetDetailList(String contractDetailId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.* FROM dbo.vehicle_loan_budget_details a").append("\r\n");
		sqlBuffer.append("LEFT JOIN dbo.vehicle_loan_budget b ON b.document_no = a.document_no WHERE b.flow_status IN (0,1,30,40,45,50,60,70) AND a.sale_contract_detail_id =:contractDetailId").append("\r\n");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractDetailId",contractDetailId);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@Override
	public List<Map<String, Object>> getBudgetChargeList(String contractDetailId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.self_id,a.direction,a.money_type,a.object_id,b.vehicle_vin,").append("\r\n");
		sqlBuffer.append("ISNULL(a.is_reimbursed,0) is_reimbursed,c.document_no,c.sale_contract_no").append("\r\n");
		sqlBuffer.append("FROM dbo.vehicle_loan_budget_charge a").append("\r\n");
		sqlBuffer.append("LEFT JOIN dbo.vehicle_loan_budget_details b ON a.budget_detail_id=b.self_id").append("\r\n");
		sqlBuffer.append(" LEFT JOIN dbo.vehicle_loan_budget c ON b.document_no=c.document_no").append("\r\n");
		sqlBuffer.append("WHERE c.flow_status IN (0,1,30,40,45,50,60,70) AND b.sale_contract_detail_id=:contractDetailId").append("\r\n");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractDetailId",contractDetailId);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@Override
	public List<Map<String, Object>> getDocumentEntriesByBudgetCharge(String contractDetailId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT entry_id,after_no,document_id,document_type FROM dbo.finance_document_entries WHERE document_id IN (").append("\r\n");
		sqlBuffer.append("SELECT self_id FROM dbo.vw_vehicle_loan_budget_charge WHERE sale_contract_detail_id=:contractDetailId)").append("\r\n");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractDetailId",contractDetailId);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@Override
	public List<Map<String, Object>> getBudgetChargeListBySelfId(String contractDetailId, String selfId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.self_id,a.direction,a.money_type,a.object_id,b.vehicle_vin,").append("\r\n");
		sqlBuffer.append("ISNULL(a.is_reimbursed,0) is_reimbursed,c.document_no,c.sale_contract_no").append("\r\n");
		sqlBuffer.append("FROM dbo.vehicle_loan_budget_charge a").append("\r\n");
		sqlBuffer.append("LEFT JOIN dbo.vehicle_loan_budget_details b ON a.budget_detail_id=b.self_id").append("\r\n");
		sqlBuffer.append(" LEFT JOIN dbo.vehicle_loan_budget c ON b.document_no=c.document_no").append("\r\n");
		sqlBuffer.append("WHERE c.flow_status IN (0,1,30,40,45,50,60,70) AND b.sale_contract_detail_id=:contractDetailId and a.self_id =:selfId").append("\r\n");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractDetailId",contractDetailId);
		params.put("selfId",selfId);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@Override
	public List<Map<String, Object>> getSettlementsDetailList(String entryId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.* FROM dbo.finance_settlements_details a").append("\r\n");
		sqlBuffer.append("LEFT JOIN dbo.finance_settlements b ON b.settlement_no = a.settlement_no").append("\r\n");
		sqlBuffer.append("WHERE b.settle_kind=1 AND b.origin_no IS NULL AND b.status=40 AND a.entry_id=:entryId").append("\r\n");
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("entryId",entryId);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@Override
	public List<Map<String, Object>> getUserByCreator(String creator) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.user_id,a.user_no,a.user_name,b.unit_id,b.unit_no,b.unit_name FROM dbo.sys_users a").append("\r\n");
		sqlBuffer.append("LEFT JOIN dbo.sys_units b ON a.department=b.unit_id").append("\r\n");
		sqlBuffer.append("WHERE (a.user_name+'('+a.user_no+')')=:creator").append("\r\n");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("creator",creator);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@Override
	public List<Map<String, Object>> getInvoicePaid(String invoicesDetailId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.*,ISNULL(b.invoice_amount,0) invoice_paid,b.after_no,b.invoice_time FROM vehicle_invoices a").append("\r\n");
		sqlBuffer.append("LEFT JOIN finance_document_entries b ON b.document_id=a.invoices_detail_id AND entry_property=4 AND entry_type=10").append("\r\n");
		sqlBuffer.append("WHERE a.invoices_detail_id =:invoicesDetailId ").append("\r\n");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("invoicesDetailId",invoicesDetailId);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@Override
	public List<Map<String, Object>> findVehicleVnoList(String contractNo) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select distinct vehicle_vno,contract_no from").append("\r\n");
		sqlBuffer.append("(select vehicle_vno,contract_no").append("\r\n");
		sqlBuffer.append("from vehicle_sale_contract_detail union select vehicle_Vno,contract_no from vehicle_sale_contract_detail_vary)").append("\r\n");
		sqlBuffer.append("as a  where  a.contract_no =:contractNo").append("\r\n");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractNo",contractNo);
		List<Map<String, Object>> list = getMapBySQL(sqlBuffer.toString(), params);
		return list;
	}

	@Override
	public List<Map<String, Object>> getChargeVaryMerge(String contractDetailId, String detailVaryId) {
		String queryStr = getSessionFactory().getCurrentSession().getNamedQuery("vwVehicleSaleContractChargeVaryMerge").getQueryString();
		StringBuffer buf = new StringBuffer();
		buf.append("select sum(case when abort_Status<>10 then income end) as  income,").append("\r\n");
		buf.append("sum(case when abort_Status<>10 then out_come end) as  outcome,").append("\r\n");
		buf.append("sum(case when detail_vary_Id is null then income else ori_Income end) as oriIncome,").append("\r\n");
		buf.append("sum(case when detail_vary_Id is null then out_Come else ori_Out_Come end) as oriOutcome,").append("\r\n");
		buf.append("sum(real_Cost) as realCost").append("\r\n");
		buf.append("from (").append(queryStr).append(") as tmp1");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contract_detail_id",contractDetailId);
		params.put("detail_vary_id",detailVaryId);
		logger.debug(String.format("费用合计：contractDetailId %s,detailVaryId %s ,sql %s", contractDetailId,detailVaryId,buf.toString()));
		
		List<Map<String, Object>> list = getMapBySQL(buf.toString(), params);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> getInsuranceVaryMerge(String contractDetailId, String detailVaryId) {
		String queryStr = getSessionFactory().getCurrentSession().getNamedQuery("vwVehicleSaleContractInsuranceVaryMarge").getQueryString();
		StringBuffer buf = new StringBuffer();
		buf.append("select sum(case when abort_Status<>10 then income end) as  income,").append("\r\n");
		buf.append("sum(case when abort_Status<>10 then out_come end) as  outcome,").append("\r\n");
		buf.append("sum(case when detail_vary_Id is null then income else ori_Income end) as oriIncome,").append("\r\n");
		buf.append("sum(case when detail_vary_Id is null then out_Come else ori_Out_Come end) as oriOutcome,").append("\r\n");
		buf.append("sum(real_Cost) as realCost").append("\r\n");
		buf.append("from (").append(queryStr).append(") as tmp1");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contract_detail_id",contractDetailId);
		params.put("detail_vary_id",detailVaryId);
		List<Map<String, Object>> list = getMapBySQL(buf.toString(), params);
		
		return list;
	}
	
	
	@Override
	public List<Map<String, Object>> getPresentVaryMerge(String contractDetailId, String detailVaryId) {
		String queryStr = getSessionFactory().getCurrentSession().getNamedQuery("vwVehicleSaleContractPresentVaryMerge").getQueryString();
		StringBuffer buf = new StringBuffer();
		buf.append("select sum(case when abort_Status<>10 then income end) as  income,").append("\r\n");
		buf.append("sum(case when abort_Status<>10 then out_come end) as  outcome,").append("\r\n");
		buf.append("sum(case when detail_vary_Id is null then income else ori_Income end) as oriIncome,").append("\r\n");
		buf.append("sum(case when detail_vary_Id is null then out_Come else ori_Out_Come end) as oriOutcome,").append("\r\n");
		buf.append("sum(real_Cost) as realCost").append("\r\n");
		buf.append("from (").append(queryStr).append(") as tmp1");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contract_detail_id",contractDetailId);
		params.put("detail_vary_id",detailVaryId);
		logger.debug(String.format("精品合计：contractDetailId %s,detailVaryId %s ,sql %s", contractDetailId,detailVaryId,buf.toString()));
		List<Map<String, Object>> list = getMapBySQL(buf.toString(), params);
		
		return list;
	}
	
	
	@Override
	public List<Map<String, Object>> getItemVaryMerge(String contractDetailId, String detailVaryId) {
		String queryStr = getSessionFactory().getCurrentSession().getNamedQuery("vwVehicleSaleContractItemVaryMerge").getQueryString();
		StringBuffer buf = new StringBuffer();
		buf.append("select sum(case when abort_Status<>10 then income end) as  income,").append("\r\n");
		buf.append("sum(case when abort_Status<>10 then out_come end) as  outcome,").append("\r\n");
		buf.append("sum(case when detail_vary_Id is null then income else ori_Income end) as oriIncome,").append("\r\n");
		buf.append("sum(case when detail_vary_Id is null then out_Come else ori_Out_Come end) as oriOutcome").append("\r\n");
		//buf.append("sum(real_Cost) as realCost").append("\r\n");
		buf.append("from (").append(queryStr).append(") as tmp1");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contract_detail_id",contractDetailId);
		params.put("detail_vary_id",detailVaryId);
		List<Map<String, Object>> list = getMapBySQL(buf.toString(), params);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> getInsuranceByContractDetailId(String contractDetailId) {
		String sql = "SELECT contract_detail_id,vehicle_vin,b.sale_contract_insurance_id FROM dbo.insurance a LEFT JOIN dbo.insurance_detail b ON b.insurance_no = a.insurance_no WHERE approve_status =1 AND contract_detail_id = :contractDetailId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractDetailId",contractDetailId);
		
		List<Map<String, Object>> list = getMapBySQL(sql, params);
		return list;
	}
	
	
	
	

}
