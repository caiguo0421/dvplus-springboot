package cn.sf_soft.vehicle.stockbrowse.action;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.common.annotation.ModuleAccess;
import cn.sf_soft.common.annotation.Modules;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.office.approval.model.VehicleConversionDetail;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStockHistory;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStockSearchCriteria;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStockStatistical;
import cn.sf_soft.vehicle.stockbrowse.model.VwVehicleStock;
import cn.sf_soft.vehicle.stockbrowse.service.VehicleStockBrowseService;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆库存浏览
 *
 * @author king
 * @create 2013-8-20上午10:56:41
 */
//@ModuleAccess(moduleId = Modules.Vehicle.STOCK_BROWSE, needPopedom = AccessPopedom.StockBrowse.VEHICLE_STOCK_BROWSE)
public class VehicleStockBrowseAction extends BaseAction {

    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleStockBrowseAction.class);
    /**
     *
     */
    private static final long serialVersionUID = -5207011025247887319L;
    @Autowired
    private VehicleStockBrowseService stockBrowseService;
    private String vehicleId;
    private String filter;

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * 订单及在途车辆，onWay：在途车辆 order：订单车辆
     */
    private String onWayType = "onWay";

    public void setOnWayType(String onWayType) {
        this.onWayType = onWayType;
    }

    @Access(needPopedom = AccessPopedom.StockBrowse.VEHICLE_STOCK_BROWSE)
    public String getVehicleStock() {
        PageModel<VwVehicleStock> pageModel = stockBrowseService.getVehicStockByCriteria(initCriteria(), pageNo, pageSize);
        ResponseMessage<List<VwVehicleStock>> result = new ResponseMessage<List<VwVehicleStock>>(pageModel.getData());
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setTotalSize(pageModel.getTotalSize());
        setResponseMessageData(result);
        return SUCCESS;
    }

    /**
     * 车辆库存-在库车辆-明细
     *
     * @return
     */
    @Access(needPopedom = AccessPopedom.StockBrowse.VEHICLE_STOCK_BROWSE)
    public String getVehicleStockV2() {
        /*ResponseMessage<List<Map<String, Object>>> result = new ResponseMessage<List<Map<String, Object>>>(
                stockBrowseService.getVehicleStock(initCriteria(), pageNo, pageSize)
        );
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setTotalSize(stockBrowseService.getVehicleStockCount(initCriteria()));
        setResponseMessageData(result);
        return SUCCESS;*/
        PageModel<Map<String, Object>> pageModel = stockBrowseService.getVehicleStock(initCriteria(), pageNo, pageSize);
        ResponseMessage<Object> respMsg = new ResponseMessage();
        respMsg.setPageNo(pageModel.getPage());
        respMsg.setPageSize(pageModel.getPerPage());
        respMsg.setTotalSize(pageModel.getTotalSize());
        respMsg.setData(pageModel.getData());
        setResponseMessageData(respMsg);
        return SUCCESS;
    }

    /**
     * 车辆库存-在库车辆-统计
     */
    @Access(needPopedom = AccessPopedom.StockBrowse.VEHICLE_STOCK_BROWSE)
    public String getListCountOfVehicle() {
        VehicleStockStatistical list = stockBrowseService.getVehicleStockStatistical(initCriteria());
        setResponseData(list);
        return SUCCESS;
    }

    /**
     * 车辆库存-在途车辆-明细
     *
     * @return
     */
    @Access(needPopedom = AccessPopedom.StockBrowse.VEHICLE_STOCK_BROWSE)
    public String getVehicleOnWay() {
        /*ResponseMessage<List<Map<String, Object>>> result = new ResponseMessage<List<Map<String, Object>>>(
                stockBrowseService.getOnWayVehicle(initCriteria(), onWayType, pageNo, pageSize)
        );
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setTotalSize(stockBrowseService.getOnWayVehicleCount(initCriteria(), onWayType));
        setResponseMessageData(result);
        return SUCCESS;*/
        PageModel<Map<String, Object>> pageModel = stockBrowseService.getOnWayVehicle(initCriteria(), onWayType, pageNo, pageSize);
        ResponseMessage<Object> respMsg = new ResponseMessage();
        respMsg.setPageNo(pageModel.getPage());
        respMsg.setPageSize(pageModel.getPerPage());
        respMsg.setTotalSize(pageModel.getTotalSize());
        respMsg.setData(pageModel.getData());
        setResponseMessageData(respMsg);
        return SUCCESS;
    }


    /**
     * 车辆库存-在途车辆-统计
     *
     * @return
     */
    @Access(needPopedom = AccessPopedom.StockBrowse.VEHICLE_STOCK_BROWSE)
    public String getListCountOfOnWayVehicle() {
        Map<String, Object> data = stockBrowseService.getOnWayVehicleStockStatistical(initCriteria(), onWayType);
        setResponseData(data);
        return SUCCESS;
    }

    /**
     * 获取车辆出入历史
     */
    @Access(needPopedom = AccessPopedom.StockBrowse.VEHICLE_STOCK_BROWSE)
    public String getVehicleStockHistory() {
        List<VehicleStockHistory> list = stockBrowseService.getVehicleHistory(vehicleId);
        setResponseData(list);
        return SUCCESS;
    }


    /**
     * 获取车辆改装明细
     */
    @Access(needPopedom = AccessPopedom.StockBrowse.VEHICLE_STOCK_BROWSE)
    public String getVehicleConversionDetail() {
        List<Map<String, Object>> list = stockBrowseService.getVehicleConversionDetails(vehicleId);
        setResponseData(list);
        return SUCCESS;
    }

    /**
     * 获取车辆查询条件信息
     */
    @Access(pass = true)
    public String getVehicleCondition() {
        setResponseData(stockBrowseService.getInitBaseData());

        return SUCCESS;
    }


    /**
     * 获取车型简称
     *
     * @return
     */
    @Access(pass = true)
    public String getVehicleShortName() {
        Object result = stockBrowseService.getVehicleShortName(getFilter());
        setResponseData(result);

        return SUCCESS;
    }


    private Map<String, Object> getFilter() {
        try {
            if (StringUtils.isBlank(filter)) {
                return new HashMap<>();
            }
            HashMap filter_map = gson.fromJson(filter, HashMap.class);
            return filter_map;
        } catch (Exception e) {
            throw new ServiceException("查询条件不合法");
        }
    }


    private VehicleStockSearchCriteria initCriteria() {
        VehicleStockSearchCriteria criteria = null;
        try {
            criteria = gson.fromJson(searchCriteria, VehicleStockSearchCriteria.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw new ServiceException("参数不合法");
        }
        if (criteria == null) {
            criteria = new VehicleStockSearchCriteria();
        }
        List<String> sts = criteria.getStationIds();
        if (sts == null || sts.size() == 0) {
            criteria.setStationIds(stationIds);
        }
        return criteria;
    }
}
