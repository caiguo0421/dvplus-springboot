package cn.sf_soft.office.approval.dao;

import java.util.List;
import java.util.Map;

import cn.sf_soft.office.approval.model.VehicleDfWriteOffApply;
import cn.sf_soft.office.approval.model.VehicleDfWriteOffApplyDetail;

public interface VehicleDfWriteOffApplyDao {

	List<VehicleDfWriteOffApplyDetail> getApplyDetail(VehicleDfWriteOffApply writeOffApply);

	VehicleDfWriteOffApply getApplyByDocumentNo(String documentNo);

	/**
	 * 查询在其他销账单中的监控车
	 * @param documentNo
	 * @param vehicleId
	 * @return
	 */
	List<Map<String, Object>> getStocksInOtherApprove(String documentNo, String vehicleId);

}
