package cn.sf_soft.vehicle.loan.dao.hbb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.model.VehicleLoanBudgetDetails;
import cn.sf_soft.vehicle.loan.dao.IVehicleLoanBudgetDao;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetCharge;

@Repository("vehicleLoanBudgetDao")
public  class VehicleLoanBudgetDao extends BaseDaoHibernateImpl implements IVehicleLoanBudgetDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleLoanBudgetDetails> getBudgetDetailList(String documentNo) {
		String hql = "from  VehicleLoanBudgetDetails  where documentNo = ?";
		List<VehicleLoanBudgetDetails> details = (List<VehicleLoanBudgetDetails>) getHibernateTemplate()
				.find(hql, new Object[] { documentNo });
		return details;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VwVehicleLoanBudgetCharge> getBudgetCharge(String selfId) {
		String hql = "from  VwVehicleLoanBudgetCharge  where budgetDetailId = ?";
		List<VwVehicleLoanBudgetCharge> charges = (List<VwVehicleLoanBudgetCharge>) getHibernateTemplate()
				.find(hql, new Object[] { selfId });
		return charges;
	}

	@Override
	public List<Map<String, Object>> getLoanAmount(String selfId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.budget_detail_id,SUM(a.loan_amount) loan_amount FROM dbo.vehicle_loan_contracts_vehicles a").append("\r\n");
		sqlBuffer.append("INNER JOIN dbo.vehicle_loan_contracts b ON a.slc_no=b.slc_no").append("\r\n");
		sqlBuffer.append("WHERE a.budget_detail_id =:selfId").append("\r\n");
		sqlBuffer.append("AND b.status<>40 AND b.status<>50 GROUP BY  a.budget_detail_id").append("\r\n");
		Map<String, Object> params = new HashMap<>();
		params.put("selfId", selfId);
		List<Map<String, Object>> results = getMapBySQL(sqlBuffer.toString(), params);

		return results;
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
		

}
