package cn.sf_soft.report.service;

import cn.sf_soft.user.model.SysUsers;

import java.util.List;
import java.util.Map;

/**
 * Created by henry on 17-5-10.
 */
public interface SalesReportService {
    final static int REPORT_TYPE_COMPANY = 0;
    final static int REPORT_TYPE_SELLER = 1;
    final static int REPORT_TYPE_DEPARTMENT = 2;
    final static int REPORT_TYPE_LOCUS = 3;
    final static int REPORT_TYPE_MODE = 4;
    final static int REPORT_TYPE_STRAIN = 5;
    final static int REPORT_TYPE_VNO = 9;

    final static int REPORT_TYPE_CUSTOMER_NAME = 6;
    final static int REPORT_TYPE_CUSTOMER_PROFESSION = 7;
    final static int REPORT_TYPE_CUSTOMER_AREA = 8;

    final static int REPORT_COUNT_TYPE_EXPORT = 0;
    final static int REPORT_COUNT_TYPE_ORDER = 1;

    public List<Map<String, Object>> getSalesAndGoalReports(int reportType, int countType, String stationId, List<String> reportSellerIds, String startDate, String endDate);

    public List<Map<String, Object>> getDeliveryLog(String vehicleVin, String customerName, String stationId, int pageNo, int pageSize, List<String> reportSellerIds);

    public Map<String,Object> getDeliveryLogTotal(String vehicleVin, String customerName, String stationId, List<String> reportSellerIds);

    public int getDeliveryLogCount(String vehicleVin, String customerName, String stationId, List<String> reportSellerIds);

    public List<Map<String, Object>> getCustomerValue(int countType, String customerName, String stationId, String startDate, String endDate, List<String> reportSellerIds);

    public List<Map<String, Object>> getBusinessPermeate(int reportType, String mode, String stationId, String startDate, String endDate);

    public int typeStringToInt(String typeString);

    public int countTypeToInt(String countTypeString);

    public List<Map<String, Object>> getSalesTrend(int reportType, int countType, String stationId, String targetId, int lastMonths, List<String> reportSellerIds);

//    public Map<String, Object> getDashboardCount(String[] stationIds, SysUsers user);

    public Map<String, Object> getDashboardData();

    public Map<String, Object> getSaleReportForHome();
    /*public List<Map<String, Object>> getReportOfPurposeDetail(Map<String, Object> filter);
    public List<Map<String, Object>> getReportOfOrderDetail(Map<String, Object> filter);
    public List<Map<String, Object>> getReportOfDeliveryDetail(Map<String, Object> filter);
    public List<Map<String, Object>> getReportOfDefeatDetail(Map<String, Object> filter);*/

    /**
     * 销售战报
     * @return
     */
    public List<Map<String, Object>> getSaleReport(Map<String, Object> filter, String yearMonth);

    /**
     * 销售战报明细
     * @return
     */
    public List<Map<String, Object>> getSaleReportDetail(Map<String, Object> filter, short saleReportType,String yearMonth);

    /**
     * 销售年报
     * @param filter
     * @return
     */
    public List<Map<String, Object>> getSaleYearReport(Map<String, Object> filter, String year);

    /**
     * 销售年报明细
     * @param filter
     * @param saleReportType
     * @return
     */
    public List<Map<String, Object>> getSaleYearReportDetail(Map<String, Object> filter, short saleReportType,String year,String month);

    public Object getSaleReportInitData();

    public Map<String, Object> getRealTimeReport(String stationId, String departmentId, String vehicleStrain);
    public List<Map<String, Object>> getRealTimeTrendReport(String stationId, String departmentId, String vehicleStrain);
}
