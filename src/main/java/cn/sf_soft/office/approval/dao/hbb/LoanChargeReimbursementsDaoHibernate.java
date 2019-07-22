package cn.sf_soft.office.approval.dao.hbb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.LoanChargeReimbursementsDao;

@Repository("loanChargeReimbursementsDao")
public class LoanChargeReimbursementsDaoHibernate extends BaseDaoHibernateImpl implements LoanChargeReimbursementsDao{

	@Override
	public List<Map<String, Object>> getVehicleLoanBudgetChargeById(
			String budgetChargeId) {
//		String budgeChargeString="SELECT  a.charge_id ," +
//				"		a.status," +
//				"		a.is_reimbursed," +
//				"		a.money_type," +
//				"		a.cost," +
//				"       b.vehicle_vin ," +
//				"       b.vehicle_sales_code ," +
//				"       b.vehicle_name ," +
//				"       c.charge_name ," +
//				"       d.status AS status_lcb ," +
//				"       d.document_no" +
//				"	    FROM    vehicle_loan_budget_charge a" +
//				"       LEFT JOIN dbo.vw_vehicle_loan_budget_details b" +
//				"       ON a.budget_detail_id = b.self_id" +
//				"       LEFT JOIN dbo.charge_catalog c ON a.charge_id = c.charge_id" +
//				"       LEFT JOIN dbo.vehicle_loan_budget d ON b.document_no = d.document_no" +
//				"       WHERE   ISNULL(a.amount, 0) <> 0.00" +
//				"       AND a.self_id=:budgeChargeId";

		String budgeChargeString = "SELECT a.charge_id, a.status, a.is_reimbursed, a.money_type, a.cost\n" +
				"\t, b.vehicle_vin, b.vehicle_sales_code, b.vehicle_name, c.charge_name, d.status AS status_lcb\n" +
				"\t, d.document_no\n" +
				"FROM vehicle_loan_budget_charge a\n" +
				"\tLEFT JOIN dbo.vw_vehicle_loan_budget_details b ON a.budget_detail_id = b.self_id\n" +
				"\tLEFT JOIN dbo.charge_catalog c ON a.charge_id = c.charge_id\n" +
				"\tLEFT JOIN dbo.vehicle_loan_budget d ON b.document_no = d.document_no\n" +
				"WHERE ISNULL(a.expenditure, 0) <> 0.00\n" +
				"\tAND a.self_id = :budgetChargeId";
		Map<String, Object> params=new HashMap<>();
		params.put("budgetChargeId", budgetChargeId);
		List<Map<String,Object>> budgetCharges=getMapBySQL(budgeChargeString, params);
		
		return budgetCharges;
	}

}
