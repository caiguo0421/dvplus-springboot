package cn.sf_soft.vehicle.stockbrowse.service;

import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.office.approval.model.VehicleConversionDetail;
import cn.sf_soft.vehicle.stockbrowse.model.*;

import java.util.List;
import java.util.Map;
/**
 * 车辆库存相关Service
 * @author king
 * @create 2013-8-15下午3:54:08
 */
public interface VehicleStockBrowseService {
	
	/**
	 * 根据搜索条件得到车辆库存信息
	 * @param criterial
	 * @return
	 */
	public PageModel<VwVehicleStock> getVehicStockByCriteria(VehicleStockSearchCriteria criterial, int pageNo, int pageSize);

	public PageModel<Map<String, Object>> getVehicleStock(VehicleStockSearchCriteria criterial, int pageNo, int pageSize);

	/*public long getVehicleStockCount(VehicleStockSearchCriteria criterial);*/

	public PageModel<Map<String, Object>> getOnWayVehicle(VehicleStockSearchCriteria criteria,String onWayType, int pageNo, int pageSize);

	/*public long getOnWayVehicleCount(VehicleStockSearchCriteria criteria,String onWayType);*/
	
	/**
	 * 根据车辆VIN码得到车辆的出入库历史信息
	 * @param vehicleVin
	 * @return
	 */
	public List<VehicleStockHistory> getVehicleHistory(String vehicleVin);
	
	/**
	 * 根据车辆VIN码得到车辆的改装明细信息
	 * @param vehicleId
	 * @return
	 */
	public List<Map<String, Object>> getVehicleConversionDetails(String vehicleId);

	/**
	 * 查询库存需要的初始化基础信息
	 * @return
	 */
	public VehicleStockInitBaseData getInitBaseData();
	
	
	/**
	 * 
	 * 得到车辆总台数  订购台数 可售台数  总成本
	 * @return
	 * @throws Exception
	 */
	public  VehicleStockStatistical getVehicleStockStatistical(VehicleStockSearchCriteria criterial);


	public  Map<String, Object> getOnWayVehicleStockStatistical(VehicleStockSearchCriteria criterial,String onwayType);

    Object getVehicleShortName(Map<String,Object> filter);
}
