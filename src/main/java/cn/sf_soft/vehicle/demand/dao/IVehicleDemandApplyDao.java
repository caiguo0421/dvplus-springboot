package cn.sf_soft.vehicle.demand.dao;

import java.util.List;

import cn.sf_soft.vehicle.demand.model.VehicleDemandApply;
import cn.sf_soft.vehicle.demand.model.VehicleDemandApplyDetail;

public interface IVehicleDemandApplyDao {

	/**
	 * 查找需求申报单主单
	 * @param documentNo
	 * @return
	 */
	VehicleDemandApply getDemandApplayByDocumentNo(String documentNo);

	/**
	 * 查找需求申报单明细
	 * @param demandId
	 * @return
	 */
	List<VehicleDemandApplyDetail> getDetailByDemandId(String demandId);

}
