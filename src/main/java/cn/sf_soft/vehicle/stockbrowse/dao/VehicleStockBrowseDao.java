package cn.sf_soft.vehicle.stockbrowse.dao;

import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.office.approval.model.VehicleConversionDetail;
import cn.sf_soft.vehicle.stockbrowse.model.*;

import java.util.List;
import java.util.Map;

/**
 * 车辆库存浏览
 * @创建人 LiuJin
 * @创建时间 2014-8-20 下午4:36:06
 * @修改人 
 * @修改时间
 */
public interface VehicleStockBrowseDao {

	/**
	 * 获取初始化需要的基础数据
	 * @return
	 */
	public VehicleStockInitBaseData getInitBaseData();
	
	/**
	 * 根据查询条件分页获取车辆库存信息
	 * @param condition
	 * @param pageNo 页码，从1开始
	 * @return
	 */
	public PageModel<VwVehicleStock> getVehicleStockByCondition(VehicleStockSearchCriteria condition, int pageNo, int pageSize);

//	/**
//	 * 根据查询条件分页获取车辆库存信息 V2
//	 * @param condition
//	 * @param pageNo 页码，从1开始
//	 * @return
//	 */
//
//	public long getVehicleStockCount(VehicleStockSearchCriteria condition);
	public PageModel<Map<String, Object>> getVehicleStock(VehicleStockSearchCriteria condition, int page, int perPage);
	public PageModel<Map<String, Object>> getOnWayVehicle(VehicleStockSearchCriteria condition,String onWayType, int pageNo, int pageSize);

	/*public long getOnWayVehicleCount(VehicleStockSearchCriteria condition,String onWayType);*/

	/**
	 * 车辆出入库历史
	 * @param vehicleId
	 * @return
	 */
	public List<VehicleStockHistory> getVehicleStockHistory(String vehicleId);
	
	/**
	 * 车辆改装明细
	 * @param vehicleId
	 * @return
	 */
	public List<Map<String, Object>> getVehicleConversionDetail(String vehicleId);
	
	/**
	 * 根据查询条件获取车辆库存统计信息
	 * @param condition
	 * @return
	 */
	public VehicleStockStatistical getVehicleStockStatistical(VehicleStockSearchCriteria condition);

	public Map<String,Object> getOnWayVehicleStockStatistical(VehicleStockSearchCriteria condition,String onWayType);
}
