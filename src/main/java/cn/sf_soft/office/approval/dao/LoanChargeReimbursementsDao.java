package cn.sf_soft.office.approval.dao;

import java.util.List;
import java.util.Map;

public interface LoanChargeReimbursementsDao {
	public List<Map<String, Object>> getVehicleLoanBudgetChargeById(String budgetChargeId);
}
