package cn.sf_soft.report.action;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseReportAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.common.annotation.ModuleAccess;
import cn.sf_soft.common.annotation.Modules;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.report.service.SalesReportService;
import cn.sf_soft.user.model.SysUsers;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by henry on 17-5-10.
 */
//@ModuleAccess(moduleId = Modules.Vehicle.STOCK_BROWSE, pass = true)
public class SalesReportAction extends BaseReportAction {
    private static final long serialVersionUID = 7952246438910447221L;
    private SalesReportService service;
    private String vehicleVin;
    private int lastMonths;
    private String targetId;

    public void setService(SalesReportService service) {
        this.service = service;
    }


    private String stationId;
    private String reportType;
    private String reportCountType;
    private String customerName;
    private String startDate;
    private String endDate;
    private String mode;
    private String[] stationIds;
    private String jsonData;

    public void setStationId(String stationId) {
        Pattern pattern = Pattern.compile("^[A-Z,]+$");
        if(stationId!=null){
          if(!pattern.matcher(stationId).matches()){
              stationId = null;
          }
        }
        if(stationId == null || stationId.length() == 0){
            SysUsers user = (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Constant.Attribute.SESSION_USER);
            stationId = StringUtils.join(user.getLoginStationId(), ",");
        }
        this.stationId = stationId;
    }

    public void setReportType(String reportType) {this.reportType = reportType;}

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setReportCountType(String reportCountType) {
        this.reportCountType = reportCountType;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }

    public void setStartDate(String startDate) {
        if(startDate!=null){
            Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
            if(!pattern.matcher(startDate).matches()){
                startDate = null;
            }
        }
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        if(endDate!=null){
            Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
            if(!pattern.matcher(endDate).matches()){
                endDate = null;
            }
        }
        this.endDate = endDate;
    }
    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    private Map<String, Object> getPostJson() {
        try {
            HashMap json_map = gson.fromJson(jsonData, HashMap.class);
            return json_map;
        } catch (Exception e) {
            throw new ServiceException("提交数据不合法");
        }
    }

    private List<String> getReportSellerIds(){
        SysUsers user = (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Constant.Attribute.SESSION_USER);
        return user.getReportSellerIds();
    }

    /**
     * 销售员销量及目标完成情况
     * @return
     */
    @Access(needPopedom= AccessPopedom.ReportForm.VEHICLE_SALE_DAY)
    public String getSalesAndGoal(){
        if(stationId == null){
            this.setStationId(null);
        }

        int reportType = service.typeStringToInt(this.reportType);
        int countType = service.countTypeToInt(this.reportCountType);

        setResponseData(service.getSalesAndGoalReports(reportType, countType, stationId, getReportSellerIds(),
                startDate, endDate
        ));
        return SUCCESS;
    }

    /**
     * 交付报表
     * @return
     */
    @Access(needPopedom= AccessPopedom.ReportForm.VEHICLE_DELIVERY)
    public String getDeliveryLog(){
        if(stationId == null){
            this.setStationId(null);
        }

        List<Map<String, Object>> list = service.getDeliveryLog(vehicleVin, customerName, stationId, pageNo, pageSize, getReportSellerIds());
        ResponseMessage<List<Map<String, Object>>> result = new ResponseMessage<List<Map<String, Object>>>(list);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setTotalSize(service.getDeliveryLogCount(vehicleVin, customerName, stationId, getReportSellerIds()));
        setResponseMessageData(result);
        return SUCCESS;
    }

    /**
     * 交付报表 统计
     * @return
     */
    @Access(needPopedom= AccessPopedom.ReportForm.VEHICLE_DELIVERY)
    public String getDeliveryLogTotal(){
        if(stationId == null){
            this.setStationId(null);
        }
        Map<String, Object> result = service.getDeliveryLogTotal(vehicleVin, customerName, stationId, getReportSellerIds());
        setResponseData(result);
        return SUCCESS;
    }

    /**
     * 客户价值
     * @return
     */
    @Access(needPopedom= AccessPopedom.ReportForm.CUSTOMER_VALUE)
    public String getCustomerValue(){
        if(stationId == null){
            this.setStationId(null);
        }

        int countType = 0;
        if(this.reportCountType!=null) {
            switch (this.reportCountType) {
                case "export":
                    countType = SalesReportService.REPORT_COUNT_TYPE_EXPORT;
                    break;
                case "order":
                default:
                    countType = SalesReportService.REPORT_COUNT_TYPE_ORDER;
                    break;
            }
        }
        setResponseData(service.getCustomerValue(countType, null, this.stationId, this.startDate, this.endDate, getReportSellerIds()));
        return SUCCESS;
    }

    /**
     * 水平事业
     * @return
     */
    @Access(needPopedom= AccessPopedom.ReportForm.PERIPHERY_VALUE)
    public String getBusinessPermeate(){
        if(stationId == null){
            this.setStationId(null);
        }

        int reportType = service.typeStringToInt(this.reportType);
        setResponseData(service.getBusinessPermeate(reportType, mode, stationId, startDate, endDate));
        return SUCCESS;
    }

    @Access(needPopedom= AccessPopedom.ReportForm.VEHICLE_SALE_DAY)
    public String getSalesTrend(){
        if(stationId == null){
            this.setStationId(null);
        }

        int reportType = service.typeStringToInt(this.reportType);
        int countType = service.countTypeToInt(this.reportCountType);
        setResponseData(service.getSalesTrend(reportType, countType, this.stationId, this.targetId, this.lastMonths, getReportSellerIds()));
        return SUCCESS;
    }

    @Access(pass= true)
    public String getDashboardCount(){
        if(stationId == null){
            this.setStationId(null);
        }
        SysUsers user = (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Constant.Attribute.SESSION_USER);
//        setResponseData(service.getDashboardCount(stationIds, user));
        setResponseData(service.getDashboardData());
        return SUCCESS;
    }

    public void setLastMonths(int lastMonths) {
        this.lastMonths = lastMonths;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public void setStationIds(String[] stationIds) {
        this.stationIds = stationIds;
    }

    /**
     * 首页销售报表
     * @return
     */
    @Access(pass= true)
    public String getSaleReportForHome(){
        this.setResponseData(service.getSaleReportForHome());
        return SUCCESS;
    }

//    /**
//     * 意向明细
//     * @return
//     */
//    public String getReportOfPurposeDetail(){
//        Map<String, Object> filter = this.getPostJson();
//        this.setResponseData(service.getReportOfPurposeDetail(filter));
//        return SUCCESS;
//    }
//
//    /**
//     * 定车明细
//     * @return
//     */
//    public String getReportOfOrderDetail(){
//        Map<String, Object> filter = this.getPostJson();
//        this.setResponseData(service.getReportOfOrderDetail(filter));
//        return SUCCESS;
//    }
//
//    /**
//     * 交车明细
//     * @return
//     */
//    public String getReportOfDeliveryDetail(){
//        Map<String, Object> filter = this.getPostJson();
//        this.setResponseData(service.getReportOfDeliveryDetail(filter));
//        return SUCCESS;
//    }
//
//    /**
//     * 战败明细
//     * @return
//     */
//    public String getReportOfDefeatDetail(){
//        Map<String, Object> filter = this.getPostJson();
//        this.setResponseData(service.getReportOfDefeatDetail(filter));
//        return SUCCESS;
//    }



    /**
     * 销售明细类型
     */
    private short saleReportType = 1;


    /**
     * 年月，例如201801
     */
    private String yearMonth;
    /**
     * 年
     */
    private String year;
    private String month ;

    public void setSaleReportType(short saleReportType) {
        this.saleReportType = saleReportType;
    }
    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 销售战报
     *
     * @return
     */
    @Access(pass = true)
    public String getSaleReport() {
        Object reportData = service.getSaleReport(getPostJson(),yearMonth);
        setResponseData(reportData);
        return SUCCESS;
    }

    /**
     * 销售战报明细
     *
     * @return
     */
    @Access(pass = true)
    public String getSaleReportDetail() {
        Object reportData = service.getSaleReportDetail(getPostJson(), saleReportType,yearMonth);
        setResponseData(reportData);
        return SUCCESS;
    }

    /**
     * 销售年报
     *
     * @return
     */
    @Access(pass = true)
    public String getSaleYearReport() {
        Object reportData = service.getSaleYearReport(getPostJson(),year);
        setResponseData(reportData);
        return SUCCESS;
    }


    /**
     * 销售年报-明细
     * @return
     */
    @Access(pass = true)
    public String getSaleYearReportDetail(){
        Object reportData = service.getSaleYearReportDetail(getPostJson(), saleReportType,year,month);
        setResponseData(reportData);
        return SUCCESS;
    }


    /**
     * 销售战报，销售年报初始化数据
     * @return
     */
    @Access(pass = true)
    public String getSaleReportInitData(){
        Object reportData = service.getSaleReportInitData();
        setResponseData(reportData);
        return SUCCESS;
    }

    /**
     * 实时战报战况
     * @return
     */
    @Access(pass = true)
    public String getRealTimeReport(){
        Map<String, Object> json = this.getPostJson();
        String stationId = null;
        String departmentId = null; //部门
        String vehicleStrain = null;   //品系
        if(null != json) {
            if(json.containsKey("station_id")){
                stationId = json.get("station_id").toString();
            }
            if(json.containsKey("department_id")){
                departmentId = json.get("department_id").toString();
            }
            if(json.containsKey("department_id")){
                departmentId = json.get("department_id").toString();
            }

            if(json.containsKey("vehicle_strain_type")){
                vehicleStrain = json.get("vehicle_strain_type").toString();
            }
        }
        Object reportData = service.getRealTimeReport(stationId, departmentId, vehicleStrain);
        setResponseData(reportData);
        return SUCCESS;
    }

    /**
     * 实时战报按时间获取日，周，月报表
     * @return
     */
    @Access(pass = true)
    public String getRealTimeTrendReport(){
        Map<String, Object> json = this.getPostJson();
        String stationId = null;
        String departmentId = null; //部门
        String vehicleStrain = null;   //品系
        if(null != json) {
            if(json.containsKey("station_id")){
                stationId = json.get("station_id").toString();
            }
            if(json.containsKey("department_id")){
                departmentId = json.get("department_id").toString();
            }

            if(json.containsKey("vehicle_strain_type")){
                vehicleStrain = json.get("vehicle_strain_type").toString();
            }
        }
        Object reportData = service.getRealTimeTrendReport(stationId, departmentId, vehicleStrain);
        setResponseData(reportData);
        return SUCCESS;
    }
}
