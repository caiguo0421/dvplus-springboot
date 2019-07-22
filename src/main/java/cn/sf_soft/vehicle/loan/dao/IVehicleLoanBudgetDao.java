package cn.sf_soft.vehicle.loan.dao;

import java.util.List;
import java.util.Map;

import cn.sf_soft.office.approval.model.VehicleLoanBudgetDetails;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetCharge;

public interface IVehicleLoanBudgetDao {

	/**
	 * 查找预算明细
	 * @param documentNo
	 * @return
	 */
	List<VehicleLoanBudgetDetails> getBudgetDetailList(String documentNo);

	/**
	 * 查找预算费用
	 * @param selfId
	 * @return
	 */
	List<VwVehicleLoanBudgetCharge> getBudgetCharge(String selfId);

	/**
	 * 查找贷款合同的贷款金额
	 * @param selfId
	 * @return
	 */
	List<Map<String, Object>> getLoanAmount(String selfId);

	/**
	 * 根据entryId 查找请款金额合计
	 * @param entryId
	 * @return
	 */
	List<Map<String, Object>> getRequestAmountByEntryId(String entryId);

}
