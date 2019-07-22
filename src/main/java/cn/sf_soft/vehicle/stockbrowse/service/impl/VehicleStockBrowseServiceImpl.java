package cn.sf_soft.vehicle.stockbrowse.service.impl;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.office.approval.model.VehicleConversionDetail;
import cn.sf_soft.vehicle.stockbrowse.dao.VehicleStockBrowseDao;
import cn.sf_soft.vehicle.stockbrowse.model.*;
import cn.sf_soft.vehicle.stockbrowse.service.VehicleStockBrowseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 车辆库存浏览
 *
 * @创建人 LiuJin
 * @创建时间 2014-8-21 下午2:04:24
 * @修改人
 * @修改时间
 */
@Service("vehicleStockBrowseService")
public class VehicleStockBrowseServiceImpl implements VehicleStockBrowseService {

    @Autowired
    private VehicleStockBrowseDao stockBrowseDao;

    @Autowired
    private BaseDao baseDao;

    /**
     * @author LiuJin
     */
    public PageModel<VwVehicleStock> getVehicStockByCriteria(
            VehicleStockSearchCriteria criterial, int pageNo, int pageSize) {
        return stockBrowseDao.getVehicleStockByCondition(criterial, pageNo, pageSize);
    }

    @Override
    public PageModel<Map<String, Object>> getVehicleStock(VehicleStockSearchCriteria criterial, int pageNo, int pageSize) {
        return stockBrowseDao.getVehicleStock(criterial, pageNo, pageSize);
    }

    /*@Override
    public long getVehicleStockCount(VehicleStockSearchCriteria criterial) {
        return stockBrowseDao.getVehicleStockCount(criterial);
    }*/

    @Override
    public PageModel<Map<String, Object>> getOnWayVehicle(VehicleStockSearchCriteria criteria, String onWayType, int pageNo, int pageSize) {
        return stockBrowseDao.getOnWayVehicle(criteria, onWayType, pageNo, pageSize);
    }

    /*@Override
    public long getOnWayVehicleCount(VehicleStockSearchCriteria criteria, String onWayType) {
        return stockBrowseDao.getOnWayVehicleCount(criteria, onWayType);
    }*/

    //获得车辆总数，可售台数，订购台数，总金额
    public VehicleStockStatistical getVehicleStockStatistical(VehicleStockSearchCriteria criterial) {
        return stockBrowseDao.getVehicleStockStatistical(criterial);
    }

    @Override
    public Map<String, Object> getOnWayVehicleStockStatistical(VehicleStockSearchCriteria criterial, String onwayType) {
        return stockBrowseDao.getOnWayVehicleStockStatistical(criterial, onwayType);
    }


    public VehicleStockInitBaseData getInitBaseData() {
        return stockBrowseDao.getInitBaseData();
    }

    /**
     * 获取车辆出入库历史
     *
     * @author caiwei
     */
    public List<VehicleStockHistory> getVehicleHistory(String vehicleVin) {
        return stockBrowseDao.getVehicleStockHistory(vehicleVin);

    }

    /**
     * 获取车辆改装明细
     *
     * @author caiwei
     */
    public List<Map<String, Object>> getVehicleConversionDetails(
            String vehicleId) {
        return stockBrowseDao.getVehicleConversionDetail(vehicleId);
    }


    /**
     * 车型简称
     *
     * @param filter
     * @return
     */
    @Override
    public Object getVehicleShortName(Map<String, Object> filter) {
//        String baseSql = "SELECT DISTINCT vehicle_strain,short_name from base_vehicle_model_catalog a WHERE short_name IS NOT NULL AND short_name<>'' ";
        String baseSql ="select DISTINCT vehicleStrain,short_name from vw_vehicle_type a  WHERE short_name IS NOT NULL AND short_name<>'' ";
        String filterCondition = baseDao.mapToFilterString(filter, "a");
        if (StringUtils.isNotBlank(filterCondition)) {
            baseSql = baseSql + " AND " + filterCondition;
        }

        List<Map<String, Object>> result = baseDao.getMapBySQL(baseSql, null);
        return result;
    }

}
