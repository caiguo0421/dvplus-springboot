package cn.sf_soft.report.service.impl;

import cn.sf_soft.basedata.model.SysDesktopItems;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.report.service.OfficeReportService;
import cn.sf_soft.report.service.SalesReportService;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.user.service.UserService;
import cn.sf_soft.vehicle.customer.service.impl.InterestedCustomersService;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by henry on 17-5-10.
 */
@Service("salesReportService")
public class SalesReportServiceImpl implements SalesReportService {
    @Autowired
    private BaseDao baseDao;

    @Autowired
    private UserService userService;

    @Autowired
    private InterestedCustomersService interestedCustomersService;

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SalesReportServiceImpl.class);

    final static String COUNT_TIME_DAY = "DAY";
    final static String COUNT_TIME_MONTH = "MONTH";
    final static String COUNT_TIME_YEAR = "YEAR";

    /**
     * 有效客户管理的module_id
     * A4S.Vehicle.SaleVisits.pfEffectiveCustomerNew
     */
    private static final String MODULE_ID_EFFECTIVE_CUSTOMER = "102010";

    @Override
    public List<Map<String, Object>> getSalesAndGoalReports(int reportType, int countType, String stationId, List<String> reportSellerIds, String startDate, String endDate) {
        String sql = buildSalesAndGoalReportsSql(reportType, countType, stationId, reportSellerIds, startDate, endDate);
        return baseDao.getMapBySQL(sql, null);
    }

    private String buildDeliveryLogCondition(String vehicleVin, String customerName, String stationId, List<String> reportSellerIds) {
        List<String> conditionArray = new ArrayList<String>();
        conditionArray.add("1 = 1");
        if (vehicleVin != null && vehicleVin.length() > 0) {
            conditionArray.add("a.vehicle_vin LIKE '%" + vehicleVin + "%'");
        }
        if (customerName != null && customerName.length() > 0) {
            conditionArray.add("a.customer_name LIKE '%" + customerName + "%'");
        }

        if (stationId != null && stationId.length() > 0) {
            conditionArray.add(buildStationCondition(stationId));
            // conditionArray.add("a.station_id LIKE '%" + stationId + "%'");
        }

        if (reportSellerIds != null && reportSellerIds.size() > 0) {
            conditionArray.add("a.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "')");
        }
        return StringUtils.join(conditionArray, " AND ");
    }

    @Override
    public int getDeliveryLogCount(String vehicleVin, String customerName, String stationId, List<String> reportSellerIds) {
        String conditionStr = buildDeliveryLogCondition(vehicleVin, customerName, stationId, reportSellerIds);
        String sql = "SELECT COUNT(*) AS c FROM (\n" +
                "SELECT ROW_NUMBER() OVER (ORDER BY a.contract_no) as row_id,\n" +
                "\t\tb.station_id,--站点ID\n" +
                "\t\td.station_name,--站点名称\n" +
                "\t\tb.customer_name,--客户名称\n" +
                "\t\ta.vehicle_vno,--车型\n" +
                "\t\tbvmc.short_name,--车型简称\n" +
                "\t\ta.vehicle_vin,--VIN码\n" +
                "\t\tDATEDIFF(DAY,GETDATE(),a.best_deliver_time) AS deliver_time_diff,--距交车天数\n" +
                "\t\tCASE WHEN vscp.contract_detail_id IS NULL AND vi.contract_detail_id IS NULL AND vc.contract_detail_id IS NULL AND \n" +
                "\t\t\tvscc.contract_detail_id IS NULL THEN '办理完' ELSE '办理中' END AS business_status ,--业务办理 \n" +
                "\t\ta.vehicle_price_total,--车款金额\n" +
                "\t\tISNULL(c.settle_amount,0)-ISNULL(i.insurance_ar,0)-ISNULL(h.vehicle_price_total,0) AS can_use_money,-- 合同可用金额\n" +
                "\t\tb.seller,--销售员\n" +
                "\t\tb.seller_id,--销售员ID\n" +
                "\t\ta.contract_no, --合同号\t\n" +
                "\t\tb.sign_time --合同签订日期\n" +
                "FROM dbo.vehicle_sale_contract_detail a\n" +
                "LEFT JOIN dbo.base_vehicle_model_catalog bvmc ON a.vno_id = bvmc.self_id\n" +
                "LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no=b.contract_no\n" +
                "LEFT JOIN (SELECT a.contract_no,SUM(a.settle_amount) AS settle_amount,MAX(settle_time) AS settle_time FROM (\n" +
                "\t\t\tSELECT a.contract_no,b.settle_amount,b.settle_time FROM dbo.vehicle_sale_contracts a\n" +
                "\t\t\tLEFT JOIN dbo.vw_finance_DFS_receive b ON a.contract_no=b.document_no\n" +
                "\t\t\tUNION ALL --变更单已收\n" +
                "\t\t\tSELECT a.contract_no,b.settle_amount,b.settle_time FROM vehicle_sale_contracts_vary a\n" +
                "\t\t\tLEFT JOIN dbo.vw_finance_DFS_receive b ON a.document_no=b.document_no\n" +
                "\t\t\tWHERE a.status=50\n" +
                "\t\t\tUNION ALL--保险已收\n" +
                "\t\t\tSELECT a.contract_no,b.settle_amount,b.settle_time FROM dbo.insurance a\n" +
                "\t\t\tINNER JOIN vw_finance_DFS_receive b ON a.insurance_no=b.document_no\t\t\t\n" +
                "\t\t\t) a GROUP BY contract_no) c ON c.contract_no = a.contract_no\n" +
                "LEFT JOIN dbo.sys_stations d ON b.station_id=d.station_id\n" +
                "LEFT JOIN dbo.vehicle_archives e ON a.vehicle_id=e.vehicle_id\n" +
                "LEFT JOIN (SELECT a.contract_no,SUM(a.settle_amount) AS settle_amount FROM (\n" +
                "\t\t\tSELECT a.contract_no,b.settle_amount FROM dbo.vehicle_sale_contracts a\n" +
                "\t\t\tLEFT JOIN dbo.vw_finance_DFS_receive b ON a.contract_no=b.document_no\n" +
                "\t\t\tUNION ALL --变更单已收\n" +
                "\t\t\tSELECT a.contract_no,b.settle_amount FROM vehicle_sale_contracts_vary a\n" +
                "\t\t\tLEFT JOIN dbo.vw_finance_DFS_receive b ON a.document_no=b.document_no\n" +
                "\t\t\tWHERE a.status=50\n" +
                "\t\t\tUNION ALL--保险已收\n" +
                "\t\t\tSELECT a.contract_no,SUM(b.settle_amount) AS settle_amount FROM dbo.insurance a\n" +
                "\t\t\tINNER JOIN vw_finance_DFS_receive b ON a.insurance_no=b.document_no\n" +
                "\t\t\tGROUP BY a.contract_no\n" +
                "\t\t\t) a GROUP BY contract_no) f ON f.contract_no = a.contract_no\n" +
                "LEFT JOIN (--保险应收\n" +
                "\t\t\tSELECT a.contract_no,SUM(a.document_amount) AS insurance_ar FROM (\n" +
                "\t\t\tSELECT a.contract_no,c.document_amount\n" +
                "\t\t\tFROM dbo.insurance a\n" +
                "\t\t\tINNER JOIN dbo.finance_document_entries c ON (a.insurance_no+','+a.vehicle_vin) =c.document_id \n" +
                "\t\t\t\tAND c.document_type IN ('保险-购买应收','保险-购买代收')\n" +
                "\t\t\tWHERE a.approve_status=1 AND a.contract_detail_id IN (\n" +
                "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_detail WHERE a.approve_status IN (1)\n" +
                "\t\t\t)) a GROUP BY a.contract_no) g ON g.contract_no = a.contract_no\n" +
                "LEFT JOIN (--已出库车辆金额\n" +
                "\t\t\tSELECT a.contract_no,SUM(a.vehicle_price_total) vehicle_price_total FROM dbo.vehicle_sale_contract_detail a\n" +
                "\t\t\tWHERE a.approve_status=1 AND a.real_deliver_time IS NOT NULL\n" +
                "\t\t\tGROUP BY a.contract_no) h ON h.contract_no = a.contract_no\n" +
                "LEFT JOIN (--已出库保险\n" +
                "\t\t\tSELECT a.contract_no,SUM(a.document_amount) AS insurance_ar FROM (\n" +
                "\t\t\tSELECT a.contract_no,c.document_amount\n" +
                "\t\t\tFROM dbo.insurance a\n" +
                "\t\t\tINNER JOIN dbo.finance_document_entries c ON (a.insurance_no+','+a.vehicle_vin) =c.document_id \n" +
                "\t\t\t\tAND c.document_type IN ('保险-购买应收','保险-购买代收')\n" +
                "\t\t\tINNER JOIN dbo.vehicle_sale_contract_detail d ON a.contract_detail_id=d.contract_detail_id\n" +
                "\t\t\tWHERE a.approve_status=1 AND d.approve_status=1 AND d.real_deliver_time IS NOT NULL\n" +
                "\t\t\t) a GROUP BY a.contract_no) i ON i.contract_no = a.contract_no\n" +
                "LEFT JOIN (--判断精品是否出库完(能连到说明没做完)\n" +
                "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_present WHERE plan_quantity>get_quantity \n" +
                "\t\t\t\tGROUP BY contract_detail_id\n" +
                "\t\t\t) vscp ON vscp.contract_detail_id = a.contract_detail_id\n" +
                "LEFT JOIN (--判断保险是否已做完(能连到说明合同预定的保险没做完)\n" +
                "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_insurance WHERE ISNULL(sale_contract_insurance_id,'') NOT IN\n" +
                "\t\t\t(SELECT ISNULL(b.sale_contract_insurance_id,'') FROM dbo.insurance a\n" +
                "\t\t\t LEFT JOIN dbo.insurance_detail b ON b.insurance_no = a.insurance_no\n" +
                "\t\t\tWHERE a.approve_status=1) GROUP BY contract_detail_id\n" +
                "\t\t\t) vi ON vi.contract_detail_id = a.contract_detail_id\n" +
                "LEFT JOIN (--判断改装是否已做完(能连到说明合同约定的改装项目没做完)\n" +
                "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_item WHERE ISNULL(sale_contract_item_id,'') NOT IN \n" +
                "\t\t\t(SELECT ISNULL(sale_contract_item_id,'') FROM dbo.vehicle_conversion_detail WHERE ISNULL(status,0)=2) \n" +
                "\t\t\t\t\tGROUP BY contract_detail_id\n" +
                "\t\t\t) vc ON vc.contract_detail_id = a.contract_detail_id\n" +
                "LEFT JOIN (--判断费用是否已经报销（能连到说明还有费用没报销）\n" +
                "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_charge WHERE ISNULL(cost_status,0)<>1 \n" +
                "\t\t\t\tGROUP BY contract_detail_id\n" +
                "\t\t\t) vscc ON vscc.contract_detail_id = a.contract_detail_id\n" +
                "WHERE a.approve_status<>30 AND b.contract_status<>3 AND b.contract_status<>4 AND ISNULL(a.real_deliver_time,'')=''\n" +
                ") a\n" +
                "WHERE " + conditionStr;
        List<Map<String, Object>> result = baseDao.getMapBySQL(sql, null);
        return Integer.parseInt(result.get(0).get("c").toString());
    }

    @Override
    public List<Map<String, Object>> getDeliveryLog(String vehicleVin, String customerName, String stationId, int pageNo, int pageSize, List<String> reportSellerIds) {

        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize < 20) {
            pageSize = 1;
        }
        String conditionStr = buildDeliveryLogCondition(vehicleVin, customerName, stationId, reportSellerIds);
        String sql =
                "SELECT * FROM ( " +
                        "SELECT *, " +
                        "ROW_NUMBER() OVER (ORDER BY a.contract_no) as row_id\n" +
                        " FROM (\n" +
                        "\t\tSELECT b.station_id,--站点ID\n" +
                        "\t\td.station_name,--站点名称\n" +
                        "\t\tb.customer_name,--客户名称\n" +
                        "\t\ta.vehicle_vno,--车型\n" +
                        "\t\tbvmc.short_name,--车型简称\n" +
                        "\t\ta.vehicle_vin,--VIN码\n" +
                        "\t\tDATEDIFF(DAY,GETDATE(),a.best_deliver_time) AS deliver_time_diff,--距交车天数\n" +
                        "\t\tCASE WHEN vscp.contract_detail_id IS NULL AND vi.contract_detail_id IS NULL AND vc.contract_detail_id IS NULL AND \n" +
                        "\t\t\tvscc.contract_detail_id IS NULL THEN '办理完' ELSE '办理中' END AS business_status ,--业务办理 \n" +
                        "\t\ta.vehicle_price_total,--车款金额\n" +
                        "\t\tISNULL(c.settle_amount,0)-ISNULL(i.insurance_ar,0)-ISNULL(h.vehicle_price_total,0) AS can_use_money,-- 合同可用金额\n" +
                        "\t\tb.seller,--销售员\n" +
                        "\t\tb.seller_id,--销售员ID\n" +
                        "\t\ta.contract_no, --系统销售单号\t\n" +
                        "\t\tb.sign_time, --合同签订日期\n" +
                        "\t\tb.contract_code --合同号\n" +
                        "FROM dbo.vehicle_sale_contract_detail a\n" +
                        "LEFT JOIN dbo.base_vehicle_model_catalog bvmc ON a.vno_id = bvmc.self_id\n" +
                        "LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no=b.contract_no\n" +
                        "LEFT JOIN (SELECT a.contract_no,SUM(a.settle_amount) AS settle_amount,MAX(settle_time) AS settle_time FROM (\n" +
                        "\t\t\tSELECT a.contract_no,b.settle_amount,b.settle_time FROM dbo.vehicle_sale_contracts a\n" +
                        "\t\t\tLEFT JOIN dbo.vw_finance_DFS_receive b ON a.contract_no=b.document_no\n" +
                        "\t\t\tUNION ALL --变更单已收\n" +
                        "\t\t\tSELECT a.contract_no,b.settle_amount,b.settle_time FROM vehicle_sale_contracts_vary a\n" +
                        "\t\t\tLEFT JOIN dbo.vw_finance_DFS_receive b ON a.document_no=b.document_no\n" +
                        "\t\t\tWHERE a.status=50\n" +
                        "\t\t\tUNION ALL--保险已收\n" +
                        "\t\t\tSELECT a.contract_no,b.settle_amount,b.settle_time FROM dbo.insurance a\n" +
                        "\t\t\tINNER JOIN vw_finance_DFS_receive b ON a.insurance_no=b.document_no\t\t\t\n" +
                        "\t\t\t) a GROUP BY contract_no) c ON c.contract_no = a.contract_no\n" +
                        "LEFT JOIN dbo.sys_stations d ON b.station_id=d.station_id\n" +
                        "LEFT JOIN dbo.vehicle_archives e ON a.vehicle_id=e.vehicle_id\n" +
                        "LEFT JOIN (SELECT a.contract_no,SUM(a.settle_amount) AS settle_amount FROM (\n" +
                        "\t\t\tSELECT a.contract_no,b.settle_amount FROM dbo.vehicle_sale_contracts a\n" +
                        "\t\t\tLEFT JOIN dbo.vw_finance_DFS_receive b ON a.contract_no=b.document_no\n" +
                        "\t\t\tUNION ALL --变更单已收\n" +
                        "\t\t\tSELECT a.contract_no,b.settle_amount FROM vehicle_sale_contracts_vary a\n" +
                        "\t\t\tLEFT JOIN dbo.vw_finance_DFS_receive b ON a.document_no=b.document_no\n" +
                        "\t\t\tWHERE a.status=50\n" +
                        "\t\t\tUNION ALL--保险已收\n" +
                        "\t\t\tSELECT a.contract_no,SUM(b.settle_amount) AS settle_amount FROM dbo.insurance a\n" +
                        "\t\t\tINNER JOIN vw_finance_DFS_receive b ON a.insurance_no=b.document_no\n" +
                        "\t\t\tGROUP BY a.contract_no\n" +
                        "\t\t\t) a GROUP BY contract_no) f ON f.contract_no = a.contract_no\n" +
                        "LEFT JOIN (--保险应收\n" +
                        "\t\t\tSELECT a.contract_no,SUM(a.document_amount) AS insurance_ar FROM (\n" +
                        "\t\t\tSELECT a.contract_no,c.document_amount\n" +
                        "\t\t\tFROM dbo.insurance a\n" +
                        "\t\t\tINNER JOIN dbo.finance_document_entries c ON (a.insurance_no+','+a.vehicle_vin) =c.document_id \n" +
                        "\t\t\t\tAND c.document_type IN ('保险-购买应收','保险-购买代收')\n" +
                        "\t\t\tWHERE a.approve_status=1 AND a.contract_detail_id IN (\n" +
                        "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_detail WHERE a.approve_status IN (1)\n" +
                        "\t\t\t)) a GROUP BY a.contract_no) g ON g.contract_no = a.contract_no\n" +
                        "LEFT JOIN (--已出库车辆金额\n" +
                        "\t\t\tSELECT a.contract_no,SUM(a.vehicle_price_total) vehicle_price_total FROM dbo.vehicle_sale_contract_detail a\n" +
                        "\t\t\tWHERE a.approve_status=1 AND a.real_deliver_time IS NOT NULL\n" +
                        "\t\t\tGROUP BY a.contract_no) h ON h.contract_no = a.contract_no\n" +
                        "LEFT JOIN (--已出库保险\n" +
                        "\t\t\tSELECT a.contract_no,SUM(a.document_amount) AS insurance_ar FROM (\n" +
                        "\t\t\tSELECT a.contract_no,c.document_amount\n" +
                        "\t\t\tFROM dbo.insurance a\n" +
                        "\t\t\tINNER JOIN dbo.finance_document_entries c ON (a.insurance_no+','+a.vehicle_vin) =c.document_id \n" +
                        "\t\t\t\tAND c.document_type IN ('保险-购买应收','保险-购买代收')\n" +
                        "\t\t\tINNER JOIN dbo.vehicle_sale_contract_detail d ON a.contract_detail_id=d.contract_detail_id\n" +
                        "\t\t\tWHERE a.approve_status=1 AND d.approve_status=1 AND d.real_deliver_time IS NOT NULL\n" +
                        "\t\t\t) a GROUP BY a.contract_no) i ON i.contract_no = a.contract_no\n" +
                        "LEFT JOIN (--判断精品是否出库完(能连到说明没做完)\n" +
                        "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_present WHERE plan_quantity>get_quantity \n" +
                        "\t\t\t\tGROUP BY contract_detail_id\n" +
                        "\t\t\t) vscp ON vscp.contract_detail_id = a.contract_detail_id\n" +
                        "LEFT JOIN (--判断保险是否已做完(能连到说明合同预定的保险没做完)\n" +
                        "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_insurance WHERE ISNULL(sale_contract_insurance_id,'') NOT IN\n" +
                        "\t\t\t(SELECT ISNULL(b.sale_contract_insurance_id,'') FROM dbo.insurance a\n" +
                        "\t\t\t LEFT JOIN dbo.insurance_detail b ON b.insurance_no = a.insurance_no\n" +
                        "\t\t\tWHERE a.approve_status=1) GROUP BY contract_detail_id\n" +
                        "\t\t\t) vi ON vi.contract_detail_id = a.contract_detail_id\n" +
                        "LEFT JOIN (--判断改装是否已做完(能连到说明合同约定的改装项目没做完)\n" +
                        "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_item WHERE ISNULL(sale_contract_item_id,'') NOT IN \n" +
                        "\t\t\t(SELECT ISNULL(sale_contract_item_id,'') FROM dbo.vehicle_conversion_detail WHERE ISNULL(status,0)=2) \n" +
                        "\t\t\t\t\tGROUP BY contract_detail_id\n" +
                        "\t\t\t) vc ON vc.contract_detail_id = a.contract_detail_id\n" +
                        "LEFT JOIN (--判断费用是否已经报销（能连到说明还有费用没报销）\n" +
                        "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_charge WHERE ISNULL(cost_status,0)<>1 \n" +
                        "\t\t\t\tGROUP BY contract_detail_id\n" +
                        "\t\t\t) vscc ON vscc.contract_detail_id = a.contract_detail_id\n" +
                        "WHERE a.approve_status<>30 AND b.contract_status<>3 AND b.contract_status<>4 AND ISNULL(a.real_deliver_time,'')=''\n" +
                        ") a\n" +
                        "WHERE " + conditionStr +
                        ") r WHERE " +
                        " r.row_id > " + (pageNo - 1) * pageSize +
                        " AND " +
                        " r.row_id <= " + (pageNo) * pageSize;
        return baseDao.getMapBySQL(sql, null);
    }

    @Override
    public Map<String, Object> getDeliveryLogTotal(String vehicleVin, String customerName, String stationId, List<String> reportSellerIds) {
        String conditionStr = buildDeliveryLogCondition(vehicleVin, customerName, stationId, reportSellerIds);
        String sql = "SELECT COUNT(*) as quantity, \n" +
                " SUM(vehicle_price_total) as total\n FROM (" +
                "SELECT * " +
                " FROM (\n" +
                "\t\tSELECT b.station_id,--站点ID\n" +
                "\t\td.station_name,--站点名称\n" +
                "\t\tb.customer_name,--客户名称\n" +
                "\t\ta.vehicle_vno,--车型\n" +
                "\t\tbvmc.short_name,--车型简称\n" +
                "\t\ta.vehicle_vin,--VIN码\n" +
                "\t\tDATEDIFF(DAY,GETDATE(),a.best_deliver_time) AS deliver_time_diff,--距交车天数\n" +
                "\t\tCASE WHEN vscp.contract_detail_id IS NULL AND vi.contract_detail_id IS NULL AND vc.contract_detail_id IS NULL AND \n" +
                "\t\t\tvscc.contract_detail_id IS NULL THEN '办理完' ELSE '办理中' END AS business_status ,--业务办理 \n" +
                "\t\ta.vehicle_price_total,--车款金额\n" +
                "\t\tISNULL(c.settle_amount,0)-ISNULL(i.insurance_ar,0)-ISNULL(h.vehicle_price_total,0) AS can_use_money,-- 合同可用金额\n" +
                "\t\tb.seller,--销售员\n" +
                "\t\tb.seller_id,--销售员ID\n" +
                "\t\ta.contract_no, --合同号\t\n" +
                "\t\tb.sign_time --合同签订日期\n" +
                "FROM dbo.vehicle_sale_contract_detail a\n" +
                "LEFT JOIN dbo.base_vehicle_model_catalog bvmc ON a.vno_id = bvmc.self_id\n" +
                "LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no=b.contract_no\n" +
                "LEFT JOIN (SELECT a.contract_no,SUM(a.settle_amount) AS settle_amount,MAX(settle_time) AS settle_time FROM (\n" +
                "\t\t\tSELECT a.contract_no,b.settle_amount,b.settle_time FROM dbo.vehicle_sale_contracts a\n" +
                "\t\t\tLEFT JOIN dbo.vw_finance_DFS_receive b ON a.contract_no=b.document_no\n" +
                "\t\t\tUNION ALL --变更单已收\n" +
                "\t\t\tSELECT a.contract_no,b.settle_amount,b.settle_time FROM vehicle_sale_contracts_vary a\n" +
                "\t\t\tLEFT JOIN dbo.vw_finance_DFS_receive b ON a.document_no=b.document_no\n" +
                "\t\t\tWHERE a.status=50\n" +
                "\t\t\tUNION ALL--保险已收\n" +
                "\t\t\tSELECT a.contract_no,b.settle_amount,b.settle_time FROM dbo.insurance a\n" +
                "\t\t\tINNER JOIN vw_finance_DFS_receive b ON a.insurance_no=b.document_no\t\t\t\n" +
                "\t\t\t) a GROUP BY contract_no) c ON c.contract_no = a.contract_no\n" +
                "LEFT JOIN dbo.sys_stations d ON b.station_id=d.station_id\n" +
                "LEFT JOIN dbo.vehicle_archives e ON a.vehicle_id=e.vehicle_id\n" +
                "LEFT JOIN (SELECT a.contract_no,SUM(a.settle_amount) AS settle_amount FROM (\n" +
                "\t\t\tSELECT a.contract_no,b.settle_amount FROM dbo.vehicle_sale_contracts a\n" +
                "\t\t\tLEFT JOIN dbo.vw_finance_DFS_receive b ON a.contract_no=b.document_no\n" +
                "\t\t\tUNION ALL --变更单已收\n" +
                "\t\t\tSELECT a.contract_no,b.settle_amount FROM vehicle_sale_contracts_vary a\n" +
                "\t\t\tLEFT JOIN dbo.vw_finance_DFS_receive b ON a.document_no=b.document_no\n" +
                "\t\t\tWHERE a.status=50\n" +
                "\t\t\tUNION ALL--保险已收\n" +
                "\t\t\tSELECT a.contract_no,SUM(b.settle_amount) AS settle_amount FROM dbo.insurance a\n" +
                "\t\t\tINNER JOIN vw_finance_DFS_receive b ON a.insurance_no=b.document_no\n" +
                "\t\t\tGROUP BY a.contract_no\n" +
                "\t\t\t) a GROUP BY contract_no) f ON f.contract_no = a.contract_no\n" +
                "LEFT JOIN (--保险应收\n" +
                "\t\t\tSELECT a.contract_no,SUM(a.document_amount) AS insurance_ar FROM (\n" +
                "\t\t\tSELECT a.contract_no,c.document_amount\n" +
                "\t\t\tFROM dbo.insurance a\n" +
                "\t\t\tINNER JOIN dbo.finance_document_entries c ON (a.insurance_no+','+a.vehicle_vin) =c.document_id \n" +
                "\t\t\t\tAND c.document_type IN ('保险-购买应收','保险-购买代收')\n" +
                "\t\t\tWHERE a.approve_status=1 AND a.contract_detail_id IN (\n" +
                "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_detail WHERE a.approve_status IN (1)\n" +
                "\t\t\t)) a GROUP BY a.contract_no) g ON g.contract_no = a.contract_no\n" +
                "LEFT JOIN (--已出库车辆金额\n" +
                "\t\t\tSELECT a.contract_no,SUM(a.vehicle_price_total) vehicle_price_total FROM dbo.vehicle_sale_contract_detail a\n" +
                "\t\t\tWHERE a.approve_status=1 AND a.real_deliver_time IS NOT NULL\n" +
                "\t\t\tGROUP BY a.contract_no) h ON h.contract_no = a.contract_no\n" +
                "LEFT JOIN (--已出库保险\n" +
                "\t\t\tSELECT a.contract_no,SUM(a.document_amount) AS insurance_ar FROM (\n" +
                "\t\t\tSELECT a.contract_no,c.document_amount\n" +
                "\t\t\tFROM dbo.insurance a\n" +
                "\t\t\tINNER JOIN dbo.finance_document_entries c ON (a.insurance_no+','+a.vehicle_vin) =c.document_id \n" +
                "\t\t\t\tAND c.document_type IN ('保险-购买应收','保险-购买代收')\n" +
                "\t\t\tINNER JOIN dbo.vehicle_sale_contract_detail d ON a.contract_detail_id=d.contract_detail_id\n" +
                "\t\t\tWHERE a.approve_status=1 AND d.approve_status=1 AND d.real_deliver_time IS NOT NULL\n" +
                "\t\t\t) a GROUP BY a.contract_no) i ON i.contract_no = a.contract_no\n" +
                "LEFT JOIN (--判断精品是否出库完(能连到说明没做完)\n" +
                "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_present WHERE plan_quantity>get_quantity \n" +
                "\t\t\t\tGROUP BY contract_detail_id\n" +
                "\t\t\t) vscp ON vscp.contract_detail_id = a.contract_detail_id\n" +
                "LEFT JOIN (--判断保险是否已做完(能连到说明合同预定的保险没做完)\n" +
                "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_insurance WHERE ISNULL(sale_contract_insurance_id,'') NOT IN\n" +
                "\t\t\t(SELECT ISNULL(b.sale_contract_insurance_id,'') FROM dbo.insurance a\n" +
                "\t\t\t LEFT JOIN dbo.insurance_detail b ON b.insurance_no = a.insurance_no\n" +
                "\t\t\tWHERE a.approve_status=1) GROUP BY contract_detail_id\n" +
                "\t\t\t) vi ON vi.contract_detail_id = a.contract_detail_id\n" +
                "LEFT JOIN (--判断改装是否已做完(能连到说明合同约定的改装项目没做完)\n" +
                "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_item WHERE ISNULL(sale_contract_item_id,'') NOT IN \n" +
                "\t\t\t(SELECT ISNULL(sale_contract_item_id,'') FROM dbo.vehicle_conversion_detail WHERE ISNULL(status,0)=2) \n" +
                "\t\t\t\t\tGROUP BY contract_detail_id\n" +
                "\t\t\t) vc ON vc.contract_detail_id = a.contract_detail_id\n" +
                "LEFT JOIN (--判断费用是否已经报销（能连到说明还有费用没报销）\n" +
                "\t\t\tSELECT contract_detail_id FROM dbo.vehicle_sale_contract_charge WHERE ISNULL(cost_status,0)<>1 \n" +
                "\t\t\t\tGROUP BY contract_detail_id\n" +
                "\t\t\t) vscc ON vscc.contract_detail_id = a.contract_detail_id\n" +
                "WHERE a.approve_status<>30 AND b.contract_status<>3 AND b.contract_status<>4 AND ISNULL(a.real_deliver_time,'')=''\n" +
                ") a WHERE " + conditionStr + ") t";
        List<Map<String, Object>> result = baseDao.getMapBySQL(sql, null);
        if (result.size() > 0) {
            return baseDao.getMapBySQL(sql, null).get(0);
        } else {
            Map<String, Object> v = new HashMap<String, Object>(2);
            v.put("quantity", 0);
            v.put("total", 0);
            return v;
        }
    }

    @Override
    public List<Map<String, Object>> getCustomerValue(int countType, String customerName, String stationId, String startDate, String endDate, List<String> reportSellerIds) {
        List<String> conditionArray = new ArrayList<String>();
        conditionArray.add("1 = 1");
        if (customerName != null) {
            conditionArray.add("customer_name LIKE '%" + customerName + "%'");
        }
        if (stationId != null) {
            conditionArray.add("station_id IN ('" + StringUtils.join(stationId.split(","), "','") + "')");
        }
        if (startDate != null) {
            if (countType == REPORT_COUNT_TYPE_ORDER) {
                conditionArray.add("approve_time >= '" + startDate + "'");
            } else {
                conditionArray.add("real_deliver_time >= '" + startDate + "'");
            }
        }
        if (endDate != null) {
            if (countType == REPORT_COUNT_TYPE_ORDER) {
                conditionArray.add("approve_time <= '" + startDate + "'");
            } else {
                conditionArray.add("real_deliver_time <= '" + startDate + "'");
            }
        }

        String conditionStr = StringUtils.join(conditionArray, " AND ");

        String sql = "SELECT a.station_id, --站点ID\n" +
                "\t   a.station_name, --站点名称\n" +
                "\t   a.customer_id, --客户ID\n" +
                "\t   a.customer_name, --客户名称\n" +
                "\t   a.maintainer_name AS workgroup_name, --维系人\n" +
                "\t   SUM (a.vehicle_count) vehicle_count, --购车数量\n" +
                "\t   SUM (a.introducer_vehicle_count) introducer_vehicle_count, --转介绍购车数量\n" +
                "\t   SUM (a.insurance_count) insurance_count, --保险台数\n" +
                "\t   SUM (a.vehicle_loan_count) vehicle_loan_count, --消贷台数\n" +
                "\t   SUM (a.affiliated_count) affiliated_count --挂靠台数\n" +
                "FROM ( SELECT b.object_id AS customer_id, b.object_name AS customer_name, b.object_no, b.short_name,\n" +
                "\t\t\t  d.station_id, c.station_name, a.approve_time, 1 AS vehicle_count,\n" +
                "\t\t\t  0 AS introducer_vehicle_count, 0 AS insurance_count, 0 AS vehicle_loan_count,\n" +
                "\t\t\t  0 AS affiliated_count, b.maintainer_name as maintainer_name\n" +
                "\t   FROM dbo.vehicle_sale_contract_detail AS a\n" +
                "\t   LEFT JOIN dbo.vehicle_sale_contracts d ON a.contract_no = d.contract_no\n" +
                "\t   LEFT JOIN base_related_objects AS b ON d.customer_id = b.object_id\n" +
                "\t   LEFT JOIN sys_stations AS c ON c.station_id = d.station_id\n" +
                "\t   WHERE a.approve_status IN ( 1, 2 )\n" +
                "\t   UNION ALL\n" +
                "\t   SELECT e.object_id, e.object_name, e.object_no, e.short_name, d.station_id, c.station_name,\n" +
                "\t\t\t  a.approve_time, 0, 1, 0, 0, 0, b.maintainer_name as maintainer_name\n" +
                "\t   FROM dbo.vehicle_sale_contract_detail AS a\n" +
                "\t   LEFT JOIN dbo.vehicle_sale_contracts d ON a.contract_no = d.contract_no\n" +
                "\t   LEFT JOIN base_related_objects AS b ON d.customer_id = b.object_id\n" +
                "\t   LEFT JOIN dbo.base_related_objects e ON b.introducer_id = e.object_id\n" +
                "\t   LEFT JOIN sys_stations AS c ON c.station_id = d.station_id\n" +
                "\t   WHERE a.approve_status IN ( 1, 2 )\n" +
                "\t\t   AND e.object_id IS NOT NULL\n" +
                "\t   UNION ALL\n" +
                "\t   SELECT b.object_id, b.object_name, b.object_no, b.short_name, a.station_id, c.station_name,\n" +
                "\t\t\t  a.approve_time, 0, 0, 1, 0, 0,b.maintainer_name as maintainer_name\n" +
                "\t   FROM insurance AS a\n" +
                "\t   LEFT JOIN dbo.base_related_objects b ON a.customer_id = b.object_id\n" +
                "\t   LEFT JOIN dbo.sys_stations c ON a.station_id = c.station_id\n" +
                "\t   WHERE 1 = 1\n" +
                "\t\t   AND a.approve_status = 1\n" +
                "\t   UNION ALL\n" +
                "\t   SELECT c.object_id, c.object_name, c.object_no, c.short_name, d.station_id, d.station_name,\n" +
                "\t\t\t  b.approve_time, 0, 0, 0, 1, 0,c.maintainer_name as maintainer_name\n" +
                "\t   FROM dbo.vehicle_loan_contracts_vehicles a\n" +
                "\t   LEFT JOIN dbo.vehicle_loan_contracts b ON b.slc_no = a.slc_no\n" +
                "\t   LEFT JOIN dbo.base_related_objects c ON b.customer_id = c.object_id\n" +
                "\t   LEFT JOIN dbo.sys_stations d ON d.station_id = b.station_id\n" +
                "\t   WHERE b.status IN ( 20, 30, 35 )\n" +
                "\t   UNION ALL\n" +
                "\t   SELECT c.object_id, c.object_name, c.object_no, c.short_name, d.station_id, d.station_name,\n" +
                "\t\t\t  b.approve_time, 0, 0, 0, 0, 1,c.maintainer_name as maintainer_name\n" +
                "\t   FROM dbo.affiliated_vehicle_archives_detail a\n" +
                "\t   LEFT JOIN dbo.affiliated_vehicle_archives b ON b.archives_no = a.archives_no\n" +
                "\t   LEFT JOIN dbo.base_related_objects c ON a.customer_id = c.object_id\n" +
                "\t   LEFT JOIN dbo.sys_stations d ON d.station_id = b.station_id\n" +
                "\t   WHERE b.approve_status = 1 ) a\n" +
                " WHERE 1 = 1\n" +
                "\tAND " + conditionStr +
                " GROUP BY a.station_id, a.station_name, a.customer_id, a.customer_name ,a.maintainer_name;";

        return baseDao.getMapBySQL(sql, null);
    }

    @Override
    public List<Map<String, Object>> getBusinessPermeate(int reportType, String mode, String stationId, String startDate, String endDate) {
        List<String> conditionArray = new ArrayList<String>();
        conditionArray.add("1 = 1");
        if (stationId != null) {
            conditionArray.add("station_id IN ('" + StringUtils.join(stationId.split(","), "','") + "')");
        }
        if (startDate != null) {
            conditionArray.add("real_deliver_time >= '" + startDate + "'");
        }
        if (endDate != null) {
            conditionArray.add("real_deliver_time <= '" + startDate + "'");
        }
        if (mode != null) {
            conditionArray.add("sale_mode_meaning LIKE '" + mode + "'");
        }

        String conditionStr = StringUtils.join(conditionArray, " AND ");

        String sql = "";

        switch (reportType) {
            case REPORT_TYPE_COMPANY:
                sql = "SELECT  a.station_id,--站点ID\n" +
                        "\t\ta.station_name, --站点名称\n" +
                        "\t\ta.vehicle_sale_count,--销量\n" +
                        "        b.vehicle_loan_count * 1.0 / a.vehicle_sale_count AS vehicle_loan_scale,--消贷渗透率\n" +
                        "        c.insurance_count * 1.0 / a.vehicle_sale_count AS insurance_scale,--保险渗透率\n" +
                        "        d.affiliated_count * 1.0 / a.vehicle_sale_count AS affiliated_scale--挂靠渗透率\n" +
                        "FROM    ( SELECT    a.station_id,a.station_name, ISNULL(COUNT(*), 0) AS vehicle_sale_count\n" +
                        "          FROM      vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "          WHERE     real_deliver_time IS NOT NULL\n" +
                        "          AND " + conditionStr +
                        "          GROUP BY  station_id,a.station_name ) AS a\n" +
                        "LEFT JOIN ( SELECT  station_id, ISNULL(COUNT(*), 0) AS vehicle_loan_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_vehicle_loan = 1\n" +
                        "          AND " + conditionStr +
                        "            GROUP BY station_id ) AS b ON a.station_id = b.station_id\n" +
                        "LEFT JOIN ( SELECT  station_id, ISNULL(COUNT(*), 0) AS insurance_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_insurance = 1\n" +
                        "          AND " + conditionStr +
                        "            GROUP BY station_id ) AS c ON a.station_id = c.station_id\n" +
                        "LEFT JOIN ( SELECT  station_id, ISNULL(COUNT(*), 0) AS affiliated_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_affiliated = 1\n" +
                        "          AND " + conditionStr +
                        "            GROUP BY station_id ) AS d ON a.station_id = d.station_id\n" +
                        "WHERE   1 = 1\n" +
                        "ORDER BY station_id;";
                break;
            case REPORT_TYPE_SELLER:
                sql = "SELECT  a.station_name, --站点名称\n" +
                        "        a.seller, --销售员\t\n" +
                        "        a.vehicle_sale_count,--销量\n" +
                        "        b.vehicle_loan_count * 1.0 / a.vehicle_sale_count AS vehicle_loan_scale,--消贷渗透率\n" +
                        "        c.insurance_count * 1.0 / a.vehicle_sale_count AS insurance_scale,--保险渗透率\n" +
                        "        d.affiliated_count * 1.0 / a.vehicle_sale_count AS affiliated_scale--挂靠渗透率\n" +
                        "FROM    ( SELECT    station_name, seller,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_sale_count\n" +
                        "          FROM      vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "          WHERE     real_deliver_time IS NOT NULL\n" +
                        "                    AND " + conditionStr +
                        "          GROUP BY  station_name, seller ) AS a\n" +
                        "LEFT JOIN ( SELECT  station_name, seller,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_loan_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_vehicle_loan = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, seller ) AS b ON a.station_name = b.station_name\n" +
                        "                                                              AND a.seller = b.seller\n" +
                        "LEFT JOIN ( SELECT  station_name, seller,\n" +
                        "                    ISNULL(COUNT(*), 0) AS insurance_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_insurance = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, seller ) AS c ON a.station_name = c.station_name\n" +
                        "                                                              AND a.seller = c.seller\n" +
                        "LEFT JOIN ( SELECT  station_name, seller,\n" +
                        "                    ISNULL(COUNT(*), 0) AS affiliated_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_affiliated = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, seller ) AS d ON a.station_name = d.station_name\n" +
                        "                                                              AND a.seller = d.seller\n" +
                        "WHERE   1 = 1\n" +
                        "ORDER BY vehicle_sale_count DESC;";
                break;
            case REPORT_TYPE_DEPARTMENT:
                sql = "SELECT  a.station_name, --站点名称\n" +
                        "        a.sale_department_name, --部门\t\n" +
                        "        a.vehicle_sale_count,--销量\n" +
                        "        b.vehicle_loan_count * 1.0 / a.vehicle_sale_count AS vehicle_loan_scale,--消贷渗透率\n" +
                        "        c.insurance_count * 1.0 / a.vehicle_sale_count AS insurance_scale,--保险渗透率\n" +
                        "        d.affiliated_count * 1.0 / a.vehicle_sale_count AS affiliated_scale--挂靠渗透率\n" +
                        "FROM    ( SELECT    station_name, sale_department_name,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_sale_count\n" +
                        "          FROM      vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "          WHERE     real_deliver_time IS NOT NULL\n" +
                        "                    AND " + conditionStr +
                        "          GROUP BY  station_name, sale_department_name ) AS a\n" +
                        "LEFT JOIN ( SELECT  station_name, sale_department_name,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_loan_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_vehicle_loan = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, sale_department_name ) AS b ON a.station_name = b.station_name\n" +
                        "                                                              AND a.sale_department_name = b.sale_department_name\n" +
                        "LEFT JOIN ( SELECT  station_name, sale_department_name,\n" +
                        "                    ISNULL(COUNT(*), 0) AS insurance_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_insurance = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, sale_department_name ) AS c ON a.station_name = c.station_name\n" +
                        "                                                              AND a.sale_department_name = c.sale_department_name\n" +
                        "LEFT JOIN ( SELECT  station_name, sale_department_name,\n" +
                        "                    ISNULL(COUNT(*), 0) AS affiliated_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_affiliated = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, sale_department_name ) AS d ON a.station_name = d.station_name\n" +
                        "                                                              AND a.sale_department_name = d.sale_department_name\n" +
                        "WHERE   1 = 1\n" +
                        "ORDER BY vehicle_sale_count DESC;";
                break;
            case REPORT_TYPE_LOCUS:
                sql = "SELECT  a.station_name, --站点名称\n" +
                        "        a.delivery_locus, --销售网点\t\n" +
                        "        a.vehicle_sale_count,--销量\n" +
                        "        b.vehicle_loan_count * 1.0 / a.vehicle_sale_count AS vehicle_loan_scale,--消贷渗透率\n" +
                        "        c.insurance_count * 1.0 / a.vehicle_sale_count AS insurance_scale,--保险渗透率\n" +
                        "        d.affiliated_count * 1.0 / a.vehicle_sale_count AS affiliated_scale--挂靠渗透率\n" +
                        "FROM    ( SELECT    station_name, delivery_locus,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_sale_count\n" +
                        "          FROM      vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "          WHERE     real_deliver_time IS NOT NULL\n" +
                        "                    AND " + conditionStr +
                        "          GROUP BY  station_name, delivery_locus ) AS a\n" +
                        "LEFT JOIN ( SELECT  station_name, delivery_locus,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_loan_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_vehicle_loan = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, delivery_locus ) AS b ON a.station_name = b.station_name\n" +
                        "                                                              AND a.delivery_locus = b.delivery_locus\n" +
                        "LEFT JOIN ( SELECT  station_name, delivery_locus,\n" +
                        "                    ISNULL(COUNT(*), 0) AS insurance_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_insurance = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, delivery_locus ) AS c ON a.station_name = c.station_name\n" +
                        "                                                              AND a.delivery_locus = c.delivery_locus\n" +
                        "LEFT JOIN ( SELECT  station_name, delivery_locus,\n" +
                        "                    ISNULL(COUNT(*), 0) AS affiliated_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_affiliated = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, delivery_locus ) AS d ON a.station_name = d.station_name\n" +
                        "                                                              AND a.delivery_locus = d.delivery_locus\n" +
                        "WHERE   1 = 1\n" +
                        "ORDER BY vehicle_sale_count DESC;";
                break;
            case REPORT_TYPE_STRAIN:
                sql = "SELECT  a.station_name, --站点名称\n" +
                        "        a.vehicle_strain, --品系\t\n" +
                        "        a.vehicle_sale_count,--销量\n" +
                        "        b.vehicle_loan_count * 1.0 / a.vehicle_sale_count AS vehicle_loan_scale,--消贷渗透率\n" +
                        "        c.insurance_count * 1.0 / a.vehicle_sale_count AS insurance_scale,--保险渗透率\n" +
                        "        d.affiliated_count * 1.0 / a.vehicle_sale_count AS affiliated_scale--挂靠渗透率\n" +
                        "FROM    ( SELECT    station_name, vehicle_strain,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_sale_count\n" +
                        "          FROM      vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "          WHERE     real_deliver_time IS NOT NULL\n" +
                        "                    AND " + conditionStr +
                        "          GROUP BY  station_name, vehicle_strain ) AS a\n" +
                        "LEFT JOIN ( SELECT  station_name, vehicle_strain,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_loan_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_vehicle_loan = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, vehicle_strain ) AS b ON a.station_name = b.station_name\n" +
                        "                                                              AND a.vehicle_strain = b.vehicle_strain\n" +
                        "LEFT JOIN ( SELECT  station_name, vehicle_strain,\n" +
                        "                    ISNULL(COUNT(*), 0) AS insurance_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_insurance = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, vehicle_strain ) AS c ON a.station_name = c.station_name\n" +
                        "                                                              AND a.vehicle_strain = c.vehicle_strain\n" +
                        "LEFT JOIN ( SELECT  station_name, vehicle_strain,\n" +
                        "                    ISNULL(COUNT(*), 0) AS affiliated_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_affiliated = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, vehicle_strain ) AS d ON a.station_name = d.station_name\n" +
                        "                                                              AND a.vehicle_strain = d.vehicle_strain\n" +
                        "WHERE   1 = 1\n" +
                        "ORDER BY vehicle_sale_count DESC;";
                break;
            case REPORT_TYPE_CUSTOMER_NAME:
                sql = "SELECT  a.station_name, --站点名称\n" +
                        "        a.customer_name, --客户名称\t\n" +
                        "        a.vehicle_sale_count,--销量\n" +
                        "        b.vehicle_loan_count * 1.0 / a.vehicle_sale_count AS vehicle_loan_scale,--消贷渗透率\n" +
                        "        c.insurance_count * 1.0 / a.vehicle_sale_count AS insurance_scale,--保险渗透率\n" +
                        "        d.affiliated_count * 1.0 / a.vehicle_sale_count AS affiliated_scale--挂靠渗透率\n" +
                        "FROM    ( SELECT    station_name, customer_name,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_sale_count\n" +
                        "          FROM      vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "          WHERE     real_deliver_time IS NOT NULL\n" +
                        "                    AND " + conditionStr +
                        "          GROUP BY  station_name, customer_name ) AS a\n" +
                        "LEFT JOIN ( SELECT  station_name, customer_name,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_loan_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_vehicle_loan = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, customer_name ) AS b ON a.station_name = b.station_name\n" +
                        "                                                              AND a.customer_name = b.customer_name\n" +
                        "LEFT JOIN ( SELECT  station_name, customer_name,\n" +
                        "                    ISNULL(COUNT(*), 0) AS insurance_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_insurance = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, customer_name ) AS c ON a.station_name = c.station_name\n" +
                        "                                                              AND a.customer_name = c.customer_name\n" +
                        "LEFT JOIN ( SELECT  station_name, customer_name,\n" +
                        "                    ISNULL(COUNT(*), 0) AS affiliated_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_affiliated = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, customer_name ) AS d ON a.station_name = d.station_name\n" +
                        "                                                              AND a.customer_name = d.customer_name\n" +
                        "WHERE   1 = 1\n" +
                        "ORDER BY vehicle_sale_count DESC;";
                break;
            case REPORT_TYPE_CUSTOMER_PROFESSION:
                sql = "SELECT  a.station_name, --站点名称\n" +
                        "        a.customer_profession, --客户行业\t\n" +
                        "        a.vehicle_sale_count,--销量\n" +
                        "        b.vehicle_loan_count * 1.0 / a.vehicle_sale_count AS vehicle_loan_scale,--消贷渗透率\n" +
                        "        c.insurance_count * 1.0 / a.vehicle_sale_count AS insurance_scale,--保险渗透率\n" +
                        "        d.affiliated_count * 1.0 / a.vehicle_sale_count AS affiliated_scale--挂靠渗透率\n" +
                        "FROM    ( SELECT    station_name, customer_profession,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_sale_count\n" +
                        "          FROM      vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "          WHERE     real_deliver_time IS NOT NULL\n" +
                        "                    AND " + conditionStr +
                        "          GROUP BY  station_name, customer_profession ) AS a\n" +
                        "LEFT JOIN ( SELECT  station_name, customer_profession,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_loan_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_vehicle_loan = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, customer_profession ) AS b ON a.station_name = b.station_name\n" +
                        "                                                              AND a.customer_profession = b.customer_profession\n" +
                        "LEFT JOIN ( SELECT  station_name, customer_profession,\n" +
                        "                    ISNULL(COUNT(*), 0) AS insurance_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_insurance = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, customer_profession ) AS c ON a.station_name = c.station_name\n" +
                        "                                                              AND a.customer_profession = c.customer_profession\n" +
                        "LEFT JOIN ( SELECT  station_name, customer_profession,\n" +
                        "                    ISNULL(COUNT(*), 0) AS affiliated_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_affiliated = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, customer_profession ) AS d ON a.station_name = d.station_name\n" +
                        "                                                              AND a.customer_profession = d.customer_profession\n" +
                        "WHERE   1 = 1\n" +
                        "ORDER BY vehicle_sale_count DESC;";
                break;
            case REPORT_TYPE_CUSTOMER_AREA:
            default:
                sql = "SELECT  a.station_name, --站点名称\n" +
                        "        a.customer_area, --客户区域\t\n" +
                        "        a.vehicle_sale_count,--销量\n" +
                        "        b.vehicle_loan_count * 1.0 / a.vehicle_sale_count AS vehicle_loan_scale,--消贷渗透率\n" +
                        "        c.insurance_count * 1.0 / a.vehicle_sale_count AS insurance_scale,--保险渗透率\n" +
                        "        d.affiliated_count * 1.0 / a.vehicle_sale_count AS affiliated_scale--挂靠渗透率\n" +
                        "FROM    ( SELECT    station_name, customer_area,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_sale_count\n" +
                        "          FROM      vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "          WHERE     real_deliver_time IS NOT NULL\n" +
                        "                    AND " + conditionStr +
                        "          GROUP BY  station_name, customer_area ) AS a\n" +
                        "LEFT JOIN ( SELECT  station_name, customer_area,\n" +
                        "                    ISNULL(COUNT(*), 0) AS vehicle_loan_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_vehicle_loan = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, customer_area ) AS b ON a.station_name = b.station_name\n" +
                        "                                                              AND a.customer_area = b.customer_area\n" +
                        "LEFT JOIN ( SELECT  station_name, customer_area,\n" +
                        "                    ISNULL(COUNT(*), 0) AS insurance_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_insurance = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, customer_area ) AS c ON a.station_name = c.station_name\n" +
                        "                                                              AND a.customer_area = c.customer_area\n" +
                        "LEFT JOIN ( SELECT  station_name, customer_area,\n" +
                        "                    ISNULL(COUNT(*), 0) AS affiliated_count\n" +
                        "            FROM    vw_stat_vehicle_sale_contract_detail AS a\n" +
                        "            WHERE   real_deliver_time IS NOT NULL\n" +
                        "                    AND is_affiliated = 1\n" +
                        "                    AND " + conditionStr +
                        "            GROUP BY station_name, customer_area ) AS d ON a.station_name = d.station_name\n" +
                        "                                                              AND a.customer_area = d.customer_area\n" +
                        "WHERE   1 = 1\n" +
                        "ORDER BY vehicle_sale_count DESC;";
                break;
        }

        return baseDao.getMapBySQL(sql, null);
    }

    @Override
    public List<Map<String, Object>> getSalesTrend(int reportType, int countType, String stationId, String targetId, int lastMonths, List<String> reportSellerIds) {
        List<String> conditionArray = new ArrayList<String>();
        conditionArray.add("1 = 1");
        if (stationId != null) {
            conditionArray.add("station_id IN ('" + StringUtils.join(stationId.split(","), "','") + "')");
        }
        conditionArray.add("DATEDIFF(MONTH, meaning_time, GETDATE()) > 0");
        conditionArray.add("DATEDIFF(MONTH, meaning_time, GETDATE()) < " + Integer.toString(lastMonths));
        String conditionStr = " " + StringUtils.join(conditionArray, " AND ") + " ";
        String meanTimeDefine = countType == REPORT_COUNT_TYPE_EXPORT ?
                "   a.real_deliver_time as meaning_time   --查找条件为“出库  \n" :
                "   b.sign_time as meaning_time --查找条件为“订车”  \n";
        String sql;
        switch (reportType) {
            case REPORT_TYPE_VNO:
                sql = "SELECT  station_id, --站点ID\n" +
                        "        station_name, --站点名称\n" +
                        "        vehicle_vno, --车型\n" +
                        "        short_name, --车型简称\n" +
                        " LEFT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 4) as year, \n" +
                        " RIGHT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 2) as month, \n" +
                        "        CONVERT(NVARCHAR(7), a.meaning_time, 23) AS sales_month,--月份\n" +
                        "        COUNT(*) AS sales_volume_month --销量\n" +
                        "FROM    ( SELECT    b.station_id, c.station_name, b.delivery_locus, vehicle_vno, d.short_name,\n" +
                        meanTimeDefine +
                        "          FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "          LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "          LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "          LEFT JOIN dbo.vw_vehicle_type d ON a.vno_id = d.vno_id\n " +
                        "          WHERE     b.contract_status <> 3\n" +
                        "                    AND b.contract_status <> 4\n" +
                        "                    AND a.approve_status <> 30 \n" +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        "\t\t\t\t\t) a\n" +
                        "WHERE   a.vehicle_vno LIKE '%" + targetId + "%' AND " + conditionStr +
                        "GROUP BY station_id, station_name, a.vehicle_vno,a.short_name,\n" +
                        "        CONVERT(NVARCHAR(7), a.meaning_time, 23)\n" +
                        "ORDER BY station_id, station_name, vehicle_vno, a.short_name, sales_month;";
                break;
            case REPORT_TYPE_COMPANY:
            default:
                sql = "SELECT   station_id, --站点ID\n" +
                        "  station_name,--站点名称\n" +
                        " LEFT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 4) as year, \n" +
                        " RIGHT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 2) as month, \n" +
                        "  CONVERT(NVARCHAR(7), a.meaning_time, 23) AS meaning_time,--月份\n" +
                        "  COUNT(*) AS sales_volume_month --销量\n" +
                        "FROM    ( SELECT    b.station_id, c.station_name,\n" +
                        meanTimeDefine +
                        "          FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "            LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "            LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "          WHERE     b.contract_status <> 3\n" +
                        "                    AND b.contract_status <> 4\n" +
                        "                    AND a.approve_status <> 30\n" +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "WHERE  a.station_id LIKE '%" + targetId + "%' AND " + conditionStr +
                        "  GROUP BY station_id, station_name,\n" +
                        "  CONVERT(NVARCHAR(7), a.meaning_time, 23)";
                break;
            case REPORT_TYPE_SELLER:
                sql = "SELECT  station_id, --站点ID\n" +
                        "\t\tstation_name, --站点名称\n" +
                        "\t\tseller_id, --销售员ID\n" +
                        "\t\tseller,--销售员\n" +
                        " LEFT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 4) as year, \n" +
                        " RIGHT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 2) as month, \n" +
                        "\t\tCONVERT(NVARCHAR(7), a.meaning_time, 23) AS meaning_time,--月份\n" +
                        "        COUNT(*) AS sales_volume_month --销量\n" +
                        "FROM    ( SELECT    b.station_id, c.station_name, b.seller_id, b.seller,\n" +
                        meanTimeDefine +
                        "          FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "          LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "          LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "          WHERE     b.contract_status <> 3\n" +
                        "                    AND b.contract_status <> 4\n" +
                        "                    AND a.approve_status <> 30 ) a\n" +
                        "WHERE   a.seller_id LIKE  '%" + targetId + "%' AND " + conditionStr +
                        (reportSellerIds == null ? "" : " AND a.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        "GROUP BY  station_id,station_name,seller_id, seller,\n" +
                        "        CONVERT(NVARCHAR(7), a.meaning_time, 23)\n" +
                        "ORDER BY station_id,station_name,seller_id, seller,meaning_time;";
                break;
            case REPORT_TYPE_DEPARTMENT:
                sql = "SELECT  station_id, --站点ID\n" +
                        "        station_name, --站点名称\n" +
                        "        department_name, --部门\n" +
                        " LEFT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 4) as year, \n" +
                        " RIGHT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 2) as month, \n" +
                        "        CONVERT(NVARCHAR(7), a.meaning_time, 23) AS meaning_time,--月份\n" +
                        "        COUNT(*) AS sales_volume_month --销量\n" +
                        "FROM    ( SELECT    b.station_id, c.station_name,\n" +
                        "                    e.unit_name AS department_name, a.real_deliver_time,\n" +
                        meanTimeDefine +
                        "          FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "          LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "          LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "          LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "          LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "          WHERE     b.contract_status <> 3\n" +
                        "                    AND b.contract_status <> 4\n" +
                        "                    AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "WHERE   a.department_name LIKE  '%" + targetId + "%' AND " + conditionStr +
                        "GROUP BY station_id, station_name, department_name,\n" +
                        "        CONVERT(NVARCHAR(7), a.meaning_time, 23)\n" +
                        "ORDER BY station_id, station_name, department_name, meaning_time;";
                break;
            case REPORT_TYPE_LOCUS:
                sql = "SELECT  station_id, --站点ID\n" +
                        "        station_name, --站点名称\n" +
                        "        delivery_locus, --销售网点\n" +
                        " LEFT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 4) as year, \n" +
                        " RIGHT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 2) as month, \n" +
                        "        CONVERT(NVARCHAR(7), a.meaning_time, 23) AS meaning_time,--月份\n" +
                        "        COUNT(*) AS sales_volume_month --销量\n" +
                        "FROM    ( SELECT    b.station_id, c.station_name, b.delivery_locus,\n" +
                        meanTimeDefine +
                        "          FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "          LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "          LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "          WHERE     b.contract_status <> 3\n" +
                        "                    AND b.contract_status <> 4\n" +
                        "                    AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "WHERE a.delivery_locus LIKE  '%" + targetId + "%' AND " + conditionStr +
                        "GROUP BY station_id, station_name, delivery_locus,\n" +
                        "        CONVERT(NVARCHAR(7), a.meaning_time, 23)\n" +
                        "ORDER BY station_id, station_name, delivery_locus, meaning_time;";
                break;
            case REPORT_TYPE_MODE:
                sql = "SELECT  station_id, --站点ID\n" +
                        "        station_name, --站点名称\n" +
                        "        sale_mode_meaning, --销售模式\n" +
                        " LEFT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 4) as year, \n" +
                        " RIGHT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 2) as month, \n" +
                        "        CONVERT(NVARCHAR(7), a.meaning_time, 23) AS meaning_time,--月份\n" +
                        "        COUNT(*) AS sales_volume_month --销量\n" +
                        "FROM    ( SELECT    b.station_id, c.station_name, b.delivery_locus,\n" +
                        "                    a.real_deliver_time, b.sign_time,f.meaning AS sale_mode_meaning,\n" +
                        meanTimeDefine +
                        "          FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "          LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "          LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "\t\t   LEFT JOIN dbo.sys_flags f ON b.sale_mode = f.code AND f.field_no = 'sale_mode'\n" +
                        "          WHERE     b.contract_status <> 3\n" +
                        "                    AND b.contract_status <> 4\n" +
                        "                    AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "WHERE   a.sale_mode_meaning LIKE  '%" + targetId + "%' AND " + conditionStr +
                        "GROUP BY station_id, station_name, sale_mode_meaning,\n" +
                        "        CONVERT(NVARCHAR(7), a.meaning_time, 23)\n" +
                        "ORDER BY station_id, station_name, sale_mode_meaning, meaning_time;";
                break;
            case REPORT_TYPE_STRAIN:
                sql = "SELECT  station_id, --站点ID\n" +
                        "        station_name, --站点名称\n" +
                        "        vehicle_strain, --销售模式\n" +
                        " LEFT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 4) as year, \n" +
                        " RIGHT(CONVERT(NVARCHAR(7), a.meaning_time, 23), 2) as month, \n" +
                        "        CONVERT(NVARCHAR(7), a.meaning_time, 23) AS meaning_time,--月份\n" +
                        "        COUNT(*) AS sales_volume_month --销量\n" +
                        "FROM    ( SELECT    b.station_id, c.station_name, b.delivery_locus,\n" +
                        "                    a.real_deliver_time, b.sign_time,vehicle_strain,\n" +
                        meanTimeDefine +
                        "          FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "          LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "          LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "          WHERE     b.contract_status <> 3\n" +
                        "                    AND b.contract_status <> 4\n" +
                        "                    AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "WHERE  a.vehicle_strain LIKE  '%" + targetId + "%' AND " + conditionStr +
                        "GROUP BY station_id, station_name, vehicle_strain,\n" +
                        "        CONVERT(NVARCHAR(7), a.meaning_time, 23)\n" +
                        "ORDER BY station_id, station_name, vehicle_strain, meaning_time;";
                break;
        }
        return baseDao.getMapBySQL(sql, null);
    }

    /**
     * 手机端桌面统计查询语句调整，从sys_desktop_items（item_type='50'）中配置
     * '[STATION]' :代表当前登陆站点。
     * '[STATIONS]'：代表管辖站点，为了取管辖站点，配置表需要配置查询语句对应的模块ID. 如果根据模块ID取不取模块的管辖站点，需取当前登陆部门的管辖站点
     * [DATEPART] : DATEDIFF函数的参数
     *
     * @return
     */
    @Override
    public Map<String, Object> getDashboardData() {
        Map<String, Object> result = new HashMap<String, Object>();
        SysUsers user = HttpSessionStore.getSessionUser();
        // 整车订购
        if (user.hasPopedom(AccessPopedom.ReportForm.DASHBOARD_REPORT_VEHICLE_ORDER_COUNT)) {
            //301010
            SysDesktopItems item = baseDao.get(SysDesktopItems.class, "501010");
            String sql = item.getExpression();
            String[] stations = userService.getModuleStationId(user, item.getModuleId());
            String station = user.getDefaulStationId();
            sql = sql.replace("[STATIONS]", StringUtils.join(stations, "','"))
                    .replace("[STATION]", station);
            result.put("this_day_vehicle_order_count", baseDao.getMapBySQL(sql.replace("[DATEPART]", "DAY"), null).get(0).get("order_count"));
            result.put("this_month_vehicle_order_count", baseDao.getMapBySQL(sql.replace("[DATEPART]", "MONTH"), null).get(0).get("order_count"));
            result.put("this_year_vehicle_order_count", baseDao.getMapBySQL(sql.replace("[DATEPART]", "YEAR"), null).get(0).get("order_count"));
            result.put("this_quarter_vehicle_order_count", baseDao.getMapBySQL(sql.replace("[DATEPART]", "QUARTER"), null).get(0).get("order_count"));
        }

        //整车销售
        if (user.hasPopedom(AccessPopedom.ReportForm.DASHBOARD_REPORT_VEHICLE_SALE_COUNT)) {
            //301020
            SysDesktopItems item = baseDao.get(SysDesktopItems.class, "501020");
            String sql = item.getExpression();
            String[] stations = userService.getModuleStationId(user, item.getModuleId());
            String station = user.getDefaulStationId();
            sql = sql.replace("[STATIONS]", StringUtils.join(stations, "','"))
                    .replace("[STATION]", station);
            result.put("this_day_vehicle_sale_count", baseDao.getMapBySQL(sql.replace("[DATEPART]", "DAY"), null).get(0).get("order_count"));
            result.put("this_month_vehicle_sale_count", baseDao.getMapBySQL(sql.replace("[DATEPART]", "MONTH"), null).get(0).get("order_count"));
            result.put("this_year_vehicle_sale_count", baseDao.getMapBySQL(sql.replace("[DATEPART]", "YEAR"), null).get(0).get("order_count"));
            result.put("this_quarter_vehicle_sale_count", baseDao.getMapBySQL(sql.replace("[DATEPART]", "QUARTER"), null).get(0).get("order_count"));
        }

        // 配件销售
        if (user.hasPopedom(AccessPopedom.ReportForm.DASHBOARD_REPORT_PART_SALE_MONEY)) {
            //302010
            SysDesktopItems item = baseDao.get(SysDesktopItems.class, "502010");
            String sql = item.getExpression();
            String[] stations = userService.getModuleStationId(user, item.getModuleId());
            String station = user.getDefaulStationId();
            sql = sql.replace("[STATIONS]", StringUtils.join(stations, "','"))
                    .replace("[STATION]", station);
            result.put("this_day_part_sale_money", baseDao.getMapBySQL(sql.replace("[DATEPART]", "DAY"), null).get(0).get("part_money"));
            result.put("this_month_part_sale_money", baseDao.getMapBySQL(sql.replace("[DATEPART]", "MONTH"), null).get(0).get("part_money"));
            result.put("this_year_part_sale_money", baseDao.getMapBySQL(sql.replace("[DATEPART]", "YEAR"), null).get(0).get("part_money"));
            result.put("this_quarter_part_sale_money", baseDao.getMapBySQL(sql.replace("[DATEPART]", "QUARTER"), null).get(0).get("part_money"));
        }

        // 维修台次
        if (user.hasPopedom(AccessPopedom.ReportForm.DASHBOARD_REPORT_MAINTAIN_COUNT)) {
            //303010
            SysDesktopItems item = baseDao.get(SysDesktopItems.class, "503010");
            String sql = item.getExpression();
            String[] stations = userService.getModuleStationId(user, item.getModuleId());
            String station = user.getDefaulStationId();
            sql = sql.replace("[STATIONS]", StringUtils.join(stations, "','"))
                    .replace("[STATION]", station);
            result.put("this_day_maintain_count", baseDao.getMapBySQL(sql.replace("[DATEPART]", "DAY"), null).get(0).get("maintain_count"));
            result.put("this_month_maintain_count", baseDao.getMapBySQL(sql.replace("[DATEPART]", "MONTH"), null).get(0).get("maintain_count"));
            result.put("this_year_maintain_count", baseDao.getMapBySQL(sql.replace("[DATEPART]", "YEAR"), null).get(0).get("maintain_count"));
            result.put("this_quarter_maintain_count", baseDao.getMapBySQL(sql.replace("[DATEPART]", "QUARTER"), null).get(0).get("maintain_count"));
        }

        // 维修总额
        if (user.hasPopedom(AccessPopedom.ReportForm.DASHBOARD_REPORT_MAINTAIN_MONEY)) {
            // 303020
            SysDesktopItems item = baseDao.get(SysDesktopItems.class, "503020");
            String sql = item.getExpression();
            String[] stations = userService.getModuleStationId(user, item.getModuleId());
            String station = user.getDefaulStationId();
            sql = sql.replace("[STATIONS]", StringUtils.join(stations, "','"))
                    .replace("[STATION]", station);
            result.put("this_day_maintain_money", baseDao.getMapBySQL(sql.replace("[DATEPART]", "DAY"), null).get(0).get("maintain_money"));
            result.put("this_month_maintain_money", baseDao.getMapBySQL(sql.replace("[DATEPART]", "MONTH"), null).get(0).get("maintain_money"));
            result.put("this_year_maintain_money", baseDao.getMapBySQL(sql.replace("[DATEPART]", "YEAR"), null).get(0).get("maintain_money"));
            result.put("this_quarter_maintain_money", baseDao.getMapBySQL(sql.replace("[DATEPART]", "QUARTER"), null).get(0).get("maintain_money"));
        }

        return result;
    }

    private String getStationWithSQLByModuleId(String moduleId) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String[] stations = userService.getModuleStationId(user, moduleId);
        List<String> stationList = null;
        if (null != stations) {
            stationList = Arrays.asList(stations);
        } else {
            stationList = new ArrayList<String>(1);
            stationList.add(user.getDefaulStationId());
        }
        return Tools.join(stationList, ",", "'", "'");
    }

    /**
     * 查询销售报表
     * --AND a.agency_name='兰州桃园东风汽车技术服务有限公司'
     * --AND a.station_id='A'
     * --AND a.seller_id='26a6607c-9e43-4087-88ac-27796eaf9401'
     * --AND a.seller=''
     * --AND a.department_name='广州销售部'
     *
     * @return
     */
    public Map<String, Object> getSaleReportForHome() {
        String sql = baseDao.getCurrentSession().getNamedQuery("saleReportWithHome").getQueryString();
        StringBuffer sqlBuff = new StringBuffer(sql);
        String stationStr = this.getStationWithSQLByModuleId(MODULE_ID_EFFECTIVE_CUSTOMER);
        sqlBuff.append(" where a.station_id in (").append(stationStr).append(")");
        String maintenanceUserCondition = interestedCustomersService.getMaintenanceUserCondition("seller_id", "a",true);
        if(StringUtils.isNotEmpty(maintenanceUserCondition)){
            sqlBuff.append(" ").append(maintenanceUserCondition);
        }else{
            sqlBuff.append(" and 1<>1 ");
        }
//        logger.debug(String.format("销售报表-首页，sql:%s",sqlBuff.toString()));
        List<Map<String, Object>> list = baseDao.getMapBySQL(sqlBuff.toString(), null);
        if (null != list && !list.isEmpty()) {
            Map<String, Object> result = list.get(0);
            result.put("month_potential_customer_quantity", this.getCurrentMonthPotentialCustomer());
            return result;
        }

        return null;
    }

    /**
     * 获取本月潜在客户数量
     * @return
     */
    public Integer getCurrentMonthPotentialCustomer(){
        StringBuffer sqlBuff = new StringBuffer("SELECT count(1) as month_potential_customer_quantity FROM vw_interested_customers a " +
                "WHERE DATEDIFF(MONTH,GETDATE(), a.create_time)=0 and isnull(a.customer_type, 50) <> 50 " +
                " AND (a.object_type & 2 = 2 OR a.object_type = 0) AND ISNULL (a.status, 0) = 1");
        String stationStr = this.getStationWithSQLByModuleId(MODULE_ID_EFFECTIVE_CUSTOMER);
        sqlBuff.append(" and a.create_station_id in (").append(stationStr).append(")");
        String maintenanceUserCondition = interestedCustomersService.getMaintenanceUserCondition("maintainer_id", "a",true);
        if(StringUtils.isNotEmpty(maintenanceUserCondition)){
            sqlBuff.append(" ").append(maintenanceUserCondition);
        }else{
            sqlBuff.append(" and 1<>1 ");
        }
//        logger.debug(String.format("销售报表首页-本月潜在-sql:%s",sqlBuff.toString()));
        List<Map<String, Object>> list = baseDao.getMapBySQL(sqlBuff.toString(), null);
        if(null != list && !list.isEmpty()){
            return Integer.parseInt(list.get(0).get("month_potential_customer_quantity").toString());
        }
        return 0;
    }

    /*public List<Map<String, Object>> getReportOfPurposeDetail(Map<String, Object> filter) {
        return getReportDetailBySQLName("purposeDetail", filter, null);
    }

    public List<Map<String, Object>> getReportOfOrderDetail(Map<String, Object> filter) {
        return getReportDetailBySQLName("orderDetail", filter, "deliver_days desc");
    }

    public List<Map<String, Object>> getReportOfDeliveryDetail(Map<String, Object> filter) {
        return getReportDetailBySQLName("deliveryDetail", filter, "real_deliver_time desc");
    }

    public List<Map<String, Object>> getReportOfDefeatDetail(Map<String, Object> filter) {
        return getReportDetailBySQLName("defeatDetail", filter, "finish_date desc");
    }*/

    private List<Map<String, Object>> getReportDetailBySQLName(String sqlName, Map<String, Object> filter, String orderBy) {
        String sql = baseDao.getCurrentSession().getNamedQuery(sqlName).getQueryString();
        StringBuffer sqlBuff = new StringBuffer();
        sqlBuff.append("select * from (").append(sql).append(") base_tmp where 1=1");
        //查询参数
        String stationStr = this.getStationWithSQLByModuleId(MODULE_ID_EFFECTIVE_CUSTOMER);
        sqlBuff.append(" and base_tmp.station_id in (").append(stationStr).append(")");
        String filterString = null;
        if (null != filter && null != filter.get("condition")) {
            Map<String, Object> condition = (Map<String, Object>) filter.get("condition");
            filterString = baseDao.mapToFilterString(condition, "base_tmp");
            if (StringUtils.isNotEmpty(filterString)) {
                sqlBuff.append(" and ").append(filterString);
            }
        }

        if (StringUtils.isNotEmpty(orderBy)) {
            sqlBuff.append(" order by ").append(orderBy);
        }
        List<Map<String, Object>> list = baseDao.getMapBySQL(sqlBuff.toString(), null);
        return list;
    }

    //牵引
    private static String[] VEHICLE_STRAIN1 = new String[]{"牵引", "东风牵引"};
    //载货
    private static String[] VEHICLE_STRAIN2 = new String[]{"重载", "中载","东风重载", "东风中载"};
    //重工
    private static String[] VEHICLE_STRAIN3 = new String[]{"重工", "中工"};
    //专用
    private static String[] VEHICLE_STRAIN4 = new String[]{"专用", "东风专用"};
    //其他
    private static String[] VEHICLE_STRAIN_OTHER = null;

    /**
     *
     * @param stationId 站点ID
     * @param departmentId 部门ID
     * @param vehicleStrain 品系(null/空字符串:全系;1:牵引;2:载货;3:工程;4:专用;20:其他)
     * @return
     */
    public Map<String, Object> getRealTimeReport(String stationId, String departmentId, String vehicleStrainType) {
        String sql = baseDao.getCurrentSession().getNamedQuery("realTimeReport").getQueryString();
        StringBuffer condition = new StringBuffer();

        String stationStr = this.getStationWithSQLByModuleId(MODULE_ID_EFFECTIVE_CUSTOMER);
        condition.append(" AND station_id in (").append(stationStr).append(")");
        if(StringUtils.isNotEmpty(stationId)){
            condition.append(" AND station_id='").append(stationId).append("'");
        }
        if (StringUtils.isNotEmpty(departmentId)) {
            condition.append(" AND department_id='").append(departmentId).append("'");
        }
        String maintenanceUserCondition = interestedCustomersService.getMaintenanceUserCondition("seller_id", "alias",true);
        if(StringUtils.isNotEmpty(maintenanceUserCondition)){
            condition.append(" ").append(maintenanceUserCondition);
        }else {
            condition.append(" and 1<>1 ");
        }
        String summaryCondition = condition.toString();

        String vehicleStrainCondition = getVehicleStrainCondition(vehicleStrainType, "vehicle_strain");
        if(StringUtils.isNotEmpty(vehicleStrainCondition)){
            condition.append(" AND ").append(vehicleStrainCondition);
        }
        if(condition.length() == 0){
            condition.append("1=1");
        }
        if(StringUtils.isEmpty(summaryCondition)){
            summaryCondition = "1=1";
        }
        sql = sql.replaceAll("\\$condition", condition.toString());
        sql = sql.replaceAll("\\$summaryCondition", summaryCondition);
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, null);
        Map<String, Object> dayMap = new HashMap<String, Object>();
        dayMap.put("intent", 0);
        dayMap.put("order", 0);
        dayMap.put("defeat", 0);
        dayMap.put("delivery", 0);
        Map<String, Object> weekMap = new HashMap<String, Object>();
        weekMap.putAll(dayMap);
        Map<String, Object> monthMap = new HashMap<String, Object>();
        monthMap.putAll(dayMap);
        Map<String, Object> summaryMap = new HashMap<String, Object>();
        summaryMap.put("monthSaleQuantity", 0);
        summaryMap.put("monthTargetQuantity", 0);
        summaryMap.put("monthCompletionRate", 0);
        summaryMap.put("yearSaleQuantity", 0);
        summaryMap.put("yearTargetQuantity", 0);
        summaryMap.put("yearCompletionRate", 0);
        if (null != list && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                String rptType = map.get("rpt_type").toString();
                if ("1".equals(rptType)) {
                    String dataType = map.get("data_type").toString();
                    int dayQuantity = Integer.parseInt(map.get("day_quantity").toString());
                    int weekQuantity = Integer.parseInt(map.get("week_quantity").toString());
                    int monthQuantity = Integer.parseInt(map.get("month_quantity").toString());
                    String key = null;
                    if ("1".equals(dataType)) {   //意向
                        key = "intent";
                    } else if ("2".equals(dataType)) {
                        key = "order";
                    } else if ("3".equals(dataType)) {
                        key = "delivery";
                    } else {
                        key = "defeat";
                    }
                    dayMap.put(key, dayQuantity);
                    weekMap.put(key, weekQuantity);
                    monthMap.put(key, monthQuantity);
                } else {
                    summaryMap.put("monthSaleQuantity", map.get("month_sale_quantity"));
                    summaryMap.put("monthTargetQuantity", map.get("month_target_quantity"));
                    summaryMap.put("monthCompletionRate", map.get("month_completion_rate"));
                    summaryMap.put("yearSaleQuantity", map.get("year_sale_quantity"));
                    summaryMap.put("yearTargetQuantity", map.get("year_target_quantity"));
                    summaryMap.put("yearCompletionRate", map.get("year_completion_rate"));
                }
            }
        }
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("day", dayMap);
        result.put("week", weekMap);
        result.put("month", monthMap);
        result.put("summary", summaryMap);
        return result;
    }

    public List<Map<String, Object>> getRealTimeTrendReport(String stationId, String departmentId, String vehicleStrainType) {
        String sql = baseDao.getCurrentSession().getNamedQuery("realTimeReportByDate").getQueryString();
        StringBuffer condition = new StringBuffer();

        String stationStr = this.getStationWithSQLByModuleId(MODULE_ID_EFFECTIVE_CUSTOMER);
        condition.append(" station_id in (").append(stationStr).append(")");
        if(StringUtils.isNotEmpty(stationId)){
            condition.append(" AND station_id='").append(stationId).append("'");
        }
        if (StringUtils.isNotEmpty(departmentId)) {
            condition.append(" AND department_id='").append(departmentId).append("'");
        }
        String vehicleStrainCondition = getVehicleStrainCondition(vehicleStrainType, "vehicle_strain");
        if(StringUtils.isNotEmpty(vehicleStrainCondition)){
            condition.append(" AND ").append(vehicleStrainCondition);
        }
        String maintenanceUserCondition = interestedCustomersService.getMaintenanceUserCondition("seller_id", "allData",true);
        if(StringUtils.isNotEmpty(maintenanceUserCondition)){
            condition.append(" ").append(maintenanceUserCondition);
        }else {
            condition.append(" and 1<>1 ");
        }
        sql = sql.replaceAll("\\$condition", condition.toString());
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, null);
        return list;
    }


    private String getVehicleStrainCondition(String vehicleStrainType, String field){
        String vehicleStrainCondition = null;
        if (StringUtils.isNotEmpty(vehicleStrainType)) {
            if("1".equals(vehicleStrainType)) {
                vehicleStrainCondition = Tools.join(VEHICLE_STRAIN1, ",", "'", "'");
            }else if("2".equals(vehicleStrainType)) {
                vehicleStrainCondition = Tools.join(VEHICLE_STRAIN2, ",", "'", "'");
            }else if("3".equals(vehicleStrainType)) {
                vehicleStrainCondition = Tools.join(VEHICLE_STRAIN3, ",", "'", "'");
            }else if("4".equals(vehicleStrainType)) {
                vehicleStrainCondition = Tools.join(VEHICLE_STRAIN4, ",", "'", "'");
            }else if("20".equals(vehicleStrainType)) {
                if(null == VEHICLE_STRAIN_OTHER){
                    List<String> list = new ArrayList<>();
                    for(int i=0; i<VEHICLE_STRAIN1.length; i++){
                        list.add(VEHICLE_STRAIN1[i]);
                    }
                    for(int i=0; i<VEHICLE_STRAIN2.length; i++){
                        list.add(VEHICLE_STRAIN2[i]);
                    }
                    for(int i=0; i<VEHICLE_STRAIN3.length; i++){
                        list.add(VEHICLE_STRAIN3[i]);
                    }
                    for(int i=0; i<VEHICLE_STRAIN4.length; i++){
                        list.add(VEHICLE_STRAIN4[i]);
                    }
                    VEHICLE_STRAIN_OTHER = list.toArray(new String[]{});
                }
                vehicleStrainCondition = Tools.join(VEHICLE_STRAIN_OTHER, ",", "'", "'");
            }else{
                throw new ServiceException(String.format("传入的条件：品系(%s)无效",vehicleStrainType));
            }
        }
        if(StringUtils.isNotEmpty(vehicleStrainCondition)){
            if("20".equals(vehicleStrainType)) {
                StringBuffer condition = new StringBuffer();
                condition.append(field).append(" not in (").append(vehicleStrainCondition).append(")");
                return condition.toString();
            }else{
                StringBuffer condition = new StringBuffer();
                condition.append(field).append(" in (").append(vehicleStrainCondition).append(")");
                return condition.toString();
            }
        }
        return null;
    }


    private String buildCountTypeCondition(String countTime, int countType) {
        if (countType == REPORT_COUNT_TYPE_EXPORT) {
            return "(DATEDIFF(" + countTime + ",real_deliver_time,GETDATE()) = 0 )";
        } else {
            return "(DATEDIFF(" + countTime + ",sign_time,GETDATE()) = 0 )"; // OR (DATEDIFF(" + countTime + ",real_deliver_time,GETDATE()) = 0 )";
        }
    }

    private String buildStationCondition(String stationId) {
        if (stationId == null) {
            return "1 = 1";
        } else {
            List<String> stationIds = Arrays.asList(stationId.split(","));
            return "a.station_id IN ('" + StringUtils.join(stationIds, "','") + "')";
        }
    }

    private String buildSalesAndGoalReportsSql(int reportType, int countType, String stationId, List<String> reportSellerIds, String startDate, String endDate) {
        switch (reportType) {
            case REPORT_TYPE_VNO:
                return "SELECT  *\n" +
                        "FROM    ( SELECT    a.station_id, --站点ID\n" +
                        "            a.station_name, --站点名称\n" +
                        "            a.vehicle_vno,--车型\n" +
                        "            a.short_name, --车型简称\n" +
                        "            a.sales_volume_month, --区间内销量\n" +
                        "            a.sales_volume_year,--年销量\n" +
                        "            a.sales_volume_day,--日销量\n" +
                        "            a.target_year,--年目标\n" +
                        "            ROUND(ISNULL(a.sales_volume_year, 0) * 1.0\n" +
                        "                  / ISNULL(NULLIF(ISNULL(b.sales_volume_year, 0), 0), 1), 2) AS contribution_rate,--年销量贡献率\n" +
                        "            1 completion_rate,--年目标完成率\n" +
                        "            DENSE_RANK() OVER ( PARTITION BY a.station_id ORDER BY a.sales_volume_year DESC ) AS ranking_year--年销售排名\n" +
                        "          FROM      ( SELECT    station_id, station_name, a.vehicle_vno,a.short_name,SUM(sales_volume_month) AS sales_volume_month,\n" +
                        "                     SUM(sales_volume_year) AS sales_volume_year, SUM(sales_volume_day) AS sales_volume_day, SUM(target_year) AS target_year\n" +
                        "                      FROM      (" +


                        "                            --月销量\n" +
                        "                                  SELECT    station_id, station_name,vehicle_vno,a.short_name,COUNT(*) AS sales_volume_month,\n" +
                        "                                              0 AS sales_volume_year, 0 AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,a.vehicle_vno,f.short_name, \n" +
                        "                                                a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                                LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                                LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                                LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                                LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                                 LEFT JOIN dbo.vw_vehicle_type f ON a.vno_id = f.vno_id\n" +
                        "                                              WHERE b.contract_status <> 3\n" +
                        "                                                    AND b.contract_status <> 4\n" +
                        "                                                    AND a.approve_status <> 30\n" +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        "                                            ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_MONTH, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,vehicle_vno,a.short_name \n" +


                        "                                  UNION ALL --年销量\n" +
                        "                                  SELECT    station_id, station_name,vehicle_vno,a.short_name,0 AS sales_volume_month,\n" +
                        "                                             COUNT(*) AS sales_volume_year, 0 AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,a.vehicle_vno,f.short_name, \n" +
                        "                                                a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                                LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                                LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                                LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                                LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                                 LEFT JOIN dbo.vw_vehicle_type f ON a.vno_id = f.vno_id\n" +
                        "                                              WHERE b.contract_status <> 3\n" +
                        "                                                    AND b.contract_status <> 4\n" +
                        "                                                    AND a.approve_status <> 30\n" +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        "                                            ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,vehicle_vno,a.short_name \n" +


                        "                                  UNION ALL --日销量\n" +
                        "                                  SELECT    station_id, station_name,vehicle_vno,a.short_name,0 AS sales_volume_month,\n" +
                        "                                                      0 AS sales_volume_year, COUNT(*) AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,a.vehicle_vno,f.short_name, \n" +
                        "                                                a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                                LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                                LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                                LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                                LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                                 LEFT JOIN dbo.vw_vehicle_type f ON a.vno_id = f.vno_id\n" +
                        "                                              WHERE b.contract_status <> 3\n" +
                        "                                                    AND b.contract_status <> 4\n" +
                        "                                                    AND a.approve_status <> 30\n" +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        "                                            ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_DAY, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,vehicle_vno,a.short_name \n" +


                        " ) a\n" +
                        "                      GROUP BY  station_id, station_name, a.vehicle_vno,a.short_name ) a\n" +
                        "            LEFT JOIN ( SELECT    a.station_id, COUNT(*) AS sales_volume_year\n" +
                        "                        FROM      ( SELECT    b.station_id, c.station_name,a.vehicle_vno AS department_name,\n" +
                        "                                      a.real_deliver_time, b.sign_time\n" +
                        "                                    FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                      LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                      LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                      LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                      LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                    WHERE b.contract_status <> 3\n" +
                        "                                          AND b.contract_status <> 4\n" +
                        "                                          AND a.approve_status <> 30\n" +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        "                                  ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                        GROUP BY  a.station_id ) b ON b.station_id = a.station_id\n" +
                        "          WHERE     a.station_id IS NOT NULL ) a\n" +
                        "WHERE  (" + this.buildStationCondition(stationId) + ") " +
                        "ORDER BY a.station_id, sales_volume_year DESC;";
            case REPORT_TYPE_COMPANY:
                return "SELECT  *\n" +
                        "FROM    ( SELECT    a.station_id, --站点ID\n" +
                        "\t\t\t\t\ta.station_name, --站点名称\n" +
                        "\t\t\t\t\ta.sales_volume_month,--月销量\n" +
                        "\t\t\t\t\ta.sales_volume_day,--日销量\n" +
                        "                    a.sales_volume_year, a.target_year,--年销量\n" +
                        "                    ROUND(ISNULL(a.sales_volume_year, 0) * 1.0 / \n" +
                        "\t\t\t\t\t\tISNULL(NULLIF(ISNULL(b.sales_volume_year, 0), 0), 1), 2) AS contribution_rate,--年销量贡献率\n" +
                        "                    1 completion_rate,--年目标完成率\n" +
                        "                    DENSE_RANK() OVER ( ORDER BY a.sales_volume_year DESC ) AS ranking_year --年销售排名\n" +
                        "          FROM      ( SELECT    station_id, station_name,SUM(sales_volume_month) AS sales_volume_month,SUM(sales_volume_day) AS sales_volume_day,\n" +
                        "\t\t\t\t\t\t\t\tSUM(sales_volume_year) AS sales_volume_year,SUM(target_year) AS target_year\n" +
                        "                      FROM      (--月销售\n" +
                        "                                  SELECT    station_id, station_name,COUNT(*) AS sales_volume_month,\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t0 AS sales_volume_year,0 AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,e.unit_name AS department_name,\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\ta.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_MONTH, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name\n" +


                        "                                  UNION ALL --年销量\n" +
                        "                                  SELECT    station_id, station_name,0 AS sales_volume_month,\n" +
                        "\t\t\t\t\t\t\t\t\t\t\tCOUNT(*) AS sales_volume_year,0 AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,e.unit_name AS department_name,\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\ta.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n  " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,department_name \n" +


                        "                                  UNION ALL --日销量\n" +
                        "                                  SELECT    station_id, station_name,0 AS sales_volume_month,\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t0 AS sales_volume_year,COUNT(*) AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,e.unit_name AS department_name,\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\ta.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n  " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_DAY, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,department_name \n" +


                        " ) a\n" +
                        "                      GROUP BY  station_id, station_name ) a\n" +
                        "          LEFT JOIN ( SELECT    a.station_id, b.sales_volume_year\n" +
                        "                      FROM      dbo.sys_stations a\n" +
                        "                      CROSS JOIN ( SELECT   COUNT(*) AS sales_volume_year\n" +
                        "                                   FROM     ( SELECT    b.station_id,c.station_name,a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "\t\t\t\t\t) b ) b ON b.station_id = a.station_id ) a\n" +
                        "WHERE  (" + this.buildStationCondition(stationId) + ") " +
                        "ORDER BY sales_volume_year DESC;";
            case REPORT_TYPE_DEPARTMENT:
                return "SELECT  *\n" +
                        "FROM    ( SELECT    a.station_id, --站点ID\n" +
                        "\t\t\t\t\ta.station_name, --站点名称\n" +
                        "\t\t\t\t\ta.department_name,--部门\n" +
                        "                    a.sales_volume_month, --月销量\n" +
                        "                    a.sales_volume_day, --日销量\n" +
                        "\t\t\t\t\ta.sales_volume_year, --年销量\n" +
                        "\t\t\t\t\ta.target_year,--年目标\n" +
                        "                    ROUND(ISNULL(a.sales_volume_year, 0) * 1.0\n" +
                        "                          / ISNULL(NULLIF(ISNULL(b.sales_volume_year, 0), 0),1), 2) AS contribution_rate,--年销量贡献率\n" +
                        "                    1 completion_rate,--年目标完成率\n" +
                        "                    DENSE_RANK() OVER ( PARTITION BY a.station_id ORDER BY a.sales_volume_year DESC ) AS ranking_year --年销售排名\n" +
                        "          FROM      ( SELECT    station_id, station_name, department_name,SUM(sales_volume_month) AS sales_volume_month,\n" +
                        "                                SUM(sales_volume_year) AS sales_volume_year,SUM(sales_volume_day) AS sales_volume_day,SUM(target_year) AS target_year\n" +
                        "                      FROM      (--月销售\n" +
                        "                                  SELECT    station_id, station_name,department_name,COUNT(*) AS sales_volume_month,\n" +
                        "                                            0 AS sales_volume_year,0 AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,e.unit_name AS department_name,\n" +
                        "                                                        a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_MONTH, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,department_name\n" +


                        "                                  UNION ALL --年销量\n" +
                        "                                  SELECT    station_id, station_name,department_name,0 AS sales_volume_month,\n" +
                        "                                            COUNT(*) AS sales_volume_year,0 AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,e.unit_name AS department_name,\n" +
                        "                                                        a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,department_name \n" +


                        "                                  UNION ALL --日销量\n" +
                        "                                  SELECT    station_id, station_name,department_name,0 AS sales_volume_month,\n" +
                        "                                            0 AS sales_volume_year,COUNT(*) AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,e.unit_name AS department_name,\n" +
                        "                                                        a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_DAY, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,department_name \n" +


                        " ) a\n" +
                        "                      GROUP BY  station_id, station_name, department_name ) a\n" +
                        "          LEFT JOIN ( SELECT    a.station_id, COUNT(*) AS sales_volume_year\n" +
                        "                      FROM      ( SELECT    b.station_id, c.station_name,e.unit_name AS department_name,\n" +
                        "                                            a.real_deliver_time, b.sign_time\n" +
                        "                                  FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                  LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                  LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                  LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                  LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                  WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                      GROUP BY  a.station_id ) b ON b.station_id = a.station_id\n" +
                        "          WHERE     a.station_id IS NOT NULL ) a\n" +
                        "WHERE  (" + this.buildStationCondition(stationId) + ") " +
                        "ORDER BY sales_volume_year DESC;";
            case REPORT_TYPE_LOCUS:
                return "SELECT  *\n" +
                        "FROM    ( SELECT    a.station_id, --站点ID\n" +
                        "\t\t\t\t\ta.station_name, --站点名称\n" +
                        "\t\t\t\t\ta.delivery_locus,--销售网点\n" +
                        "\t\t\t\t\ta.sales_volume_day, --日销量\n" +
                        "\t\t\t\t\ta.sales_volume_month, --月销量\n" +
                        "\t\t\t\t\ta.sales_volume_year,--年销量\n" +
                        "\t\t\t\t\ta.target_year,--年目标\n" +
                        "                    ROUND(ISNULL(a.sales_volume_year, 0) * 1.0\n" +
                        "                          / ISNULL(NULLIF(ISNULL(b.sales_volume_year, 0), 0), 1), 2) AS contribution_rate,--年销量贡献率\n" +
                        "                    1 completion_rate,--年目标完成率\n" +
                        "                    DENSE_RANK() OVER ( PARTITION BY a.station_id ORDER BY a.sales_volume_year DESC ) AS ranking_year--年销售排名\n" +
                        "          FROM      ( SELECT    station_id, station_name, delivery_locus,SUM(sales_volume_month) AS sales_volume_month,\n" +
                        "                                SUM(sales_volume_day) AS sales_volume_day, SUM(sales_volume_year) AS sales_volume_year,SUM(target_year) AS target_year\n" +
                        "                      FROM      (--月销售\n" +
                        "                                  SELECT    station_id, station_name,delivery_locus,COUNT(*) AS sales_volume_month,\n" +
                        "                                            0 AS sales_volume_year,0 AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,b.delivery_locus,\n" +
                        "                                                        a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE     b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_MONTH, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,delivery_locus\n" +


                        "                                  UNION ALL --年销量\n" +
                        "                                  SELECT    station_id, station_name,delivery_locus,0 AS sales_volume_month,\n" +
                        "                                            COUNT(*) AS sales_volume_year,0 AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,b.delivery_locus,\n" +
                        "                                                        a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,delivery_locus \n" +

                        "                                  UNION ALL --日销量\n" +
                        "                                  SELECT    station_id, station_name,delivery_locus,0 AS sales_volume_month,\n" +
                        "                                            0 AS sales_volume_year,COUNT(*) AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,b.delivery_locus,\n" +
                        "                                                        a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_DAY, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,delivery_locus \n" +

                        " ) a\n" +
                        "                      GROUP BY  station_id, station_name, delivery_locus ) a\n" +
                        "          LEFT JOIN ( SELECT    a.station_id, COUNT(*) AS sales_volume_year\n" +
                        "                      FROM      ( SELECT    b.station_id, c.station_name,e.unit_name AS department_name,\n" +
                        "                                            a.real_deliver_time, b.sign_time\n" +
                        "                                  FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                  LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                  LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                  LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                  LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                  WHERE     b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                      GROUP BY  a.station_id ) b ON b.station_id = a.station_id\n" +
                        "          WHERE     a.station_id IS NOT NULL ) a\n" +
                        "WHERE  (" + this.buildStationCondition(stationId) + ") " +
                        "ORDER BY sales_volume_year DESC;";
            case REPORT_TYPE_MODE:
                return "SELECT  *\n" +
                        "FROM    ( SELECT    a.station_id, --站点ID\n" +
                        "\t\t\t\t\ta.station_name, --站点名称\n" +
                        "\t\t\t\t\ta.sale_mode_meaning,--销售模式\n" +
                        "\t\t\t\t\t(a.station_name + '-' +a.sale_mode_meaning) as sale_mode_display," +
                        "      a.sales_volume_day, --日销量 \n " +
                        "      a.sales_volume_month, --月销量 \n " +
                        "\t\t\t\t\ta.sales_volume_year,--年销量\n" +
                        "\t\t\t\t\ta.target_year,--年目标\n" +
                        "                    ROUND(ISNULL(a.sales_volume_year, 0) * 1.0\n" +
                        "                          / ISNULL(NULLIF(ISNULL(b.sales_volume_year, 0), 0), 1), 2) AS contribution_rate,--年销量贡献率\n" +
                        "                    1 completion_rate,--年目标完成率\n" +
                        "                    DENSE_RANK() OVER ( PARTITION BY a.station_id ORDER BY a.sales_volume_year DESC ) AS ranking_year--年销售排名\n" +
                        "          FROM      ( SELECT    station_id, station_name, sale_mode_meaning,SUM(sales_volume_month) AS sales_volume_month,\n" +
                        "                                SUM(sales_volume_year) AS sales_volume_year,SUM(sales_volume_day) AS sales_volume_day,SUM(target_year) AS target_year\n" +
                        "                      FROM      (--月销售\n" +
                        "                                  SELECT    station_id, station_name,sale_mode_meaning,COUNT(*) AS sales_volume_month,\n" +
                        "                                            0 AS sales_volume_year,0 AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,f.meaning AS sale_mode_meaning,\n" +
                        "                                                        a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              LEFT JOIN dbo.sys_flags f ON b.sale_mode = f.code AND f.field_no = 'sale_mode'\n" +
                        "                                              WHERE     b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_MONTH, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,sale_mode_meaning\n" +


                        "                                  UNION ALL --年销量\n" +
                        "                                  SELECT    station_id, station_name,sale_mode_meaning,0 AS sales_volume_month,\n" +
                        "                                            COUNT(*) AS sales_volume_year,0 AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,f.meaning AS sale_mode_meaning,\n" +
                        "                                                        a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              LEFT JOIN dbo.sys_flags f ON b.sale_mode = f.code AND f.field_no = 'sale_mode'\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,sale_mode_meaning \n" +

                        "                                  UNION ALL --日销量\n" +
                        "                                  SELECT    station_id, station_name,sale_mode_meaning,0 AS sales_volume_month,\n" +
                        "                                            0 AS sales_volume_year,COUNT(*) AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,f.meaning AS sale_mode_meaning,\n" +
                        "                                                        a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              LEFT JOIN dbo.sys_flags f ON b.sale_mode = f.code AND f.field_no = 'sale_mode'\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_DAY, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,sale_mode_meaning \n" +


                        " ) a\n" +
                        "                      GROUP BY  station_id, station_name, sale_mode_meaning ) a\n" +
                        "          LEFT JOIN ( SELECT    a.station_id, COUNT(*) AS sales_volume_year\n" +
                        "                      FROM      ( SELECT    b.station_id, c.station_name,e.unit_name AS department_name,\n" +
                        "                                            a.real_deliver_time, b.sign_time\n" +
                        "                                  FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                  LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                  LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                  LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                  LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                  WHERE     b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                      GROUP BY  a.station_id ) b ON b.station_id = a.station_id\n" +
                        "          WHERE     a.station_id IS NOT NULL ) a\n" +
                        "WHERE  (" + this.buildStationCondition(stationId) + ") " +
                        "ORDER BY sales_volume_year DESC;";
            case REPORT_TYPE_SELLER:
                return "SELECT  *\n" +
                        "FROM    ( SELECT ISNULL(a.station_id, e.station_id) AS station_id,--站点ID\n" +
                        "                 ISNULL(a.station_name, e.station_name) AS station_name,--站点名称\n" +
                        "                 ISNULL(a.seller_id, c.user_id) AS seller_id,--销售员ID\n" +
                        "                 ISNULL(a.seller, c.user_name) AS seller,--销售员\n" +
                        "                 ISNULL(a.sales_volume_month, 0) sales_volume_month,--月销量\n" +
                        "                 ISNULL(a.sales_volume_year, 0) sales_volume_year,--年销量\n" +
                        "                 ISNULL(a.sales_volume_day, 0) sales_volume_day,--日销量\n" +
                        "                 ISNULL(a.target_year, 0) target_year, --年目标\n" +
                        "\t\t\t\t 1 completion_rate,--年目标完成率\n" +
                        "                 ISNULL(a.contribution_rate, 0) contribution_rate,--年销量贡献率\n" +
                        "                 ISNULL(a.customer_quantity, 0) customer_quantity,--管理客户数\n" +
                        "                 ISNULL(b.intention_vehicle_quantity, 0) intention_vehicle_quantity,--意向车辆数\n" +
                        "                 DENSE_RANK() OVER ( PARTITION BY a.station_id ORDER BY a.sales_volume_year DESC ) AS ranking_year--年销售排名\n" +
                        "          FROM      ( SELECT    ISNULL(a.station_id, f.station_id) AS station_id, \n" +
                        "\t\t\t\t\t\t\tISNULL(a.station_name, f.station_name) AS station_name,\n" +
                        "                                ISNULL(a.seller_id, d.user_id) AS seller_id, ISNULL(a.seller, d.user_name) AS seller,\n" +
                        "                                a.sales_volume_month, a.sales_volume_year, a.sales_volume_day, a.target_year, c.customer_quantity,\n" +
                        "\t\t\t\t\t\t\t\tROUND(ISNULL(a.sales_volume_year, 0) * 1.0\n" +
                        "                                      / ISNULL(NULLIF(ISNULL(b.sales_volume_year, 0), 0), 1), 2) AS contribution_rate\n" +
                        "                      FROM      ( SELECT    station_id, station_name, seller_id, seller, \n" +
                        "\t\t\t\t\t\t\t\t\t\t\tSUM(sales_volume_month) AS sales_volume_month,SUM(sales_volume_day) AS sales_volume_day,\n" +
                        "                                            SUM(sales_volume_year) AS sales_volume_year, SUM(target_year) AS target_year\n" +
                        "                                  FROM      (--月销售\n" +
                        "                                              SELECT\tstation_id,station_name,seller_id, seller,COUNT(*) AS sales_volume_month,\n" +
                        "                                                        0 AS sales_volume_year,0 AS sales_volume_day,0 target_year\n" +
                        "                                              FROM ( SELECT b.station_id,c.station_name,b.seller_id,\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tb.seller,a.real_deliver_time,b.sign_time\n" +
                        "                                                          FROM dbo.vehicle_sale_contract_detail a\n" +
                        "                                                          LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                                          LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                                          WHERE b.contract_status <> 3 AND b.contract_status <> 4 \n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tAND a.approve_status <> 30 ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_MONTH, countType) + ")" +
                        "                                              GROUP BY  station_id,station_name,seller_id, seller\n" +

                        "                                              UNION ALL --年销量\n" +
                        "                                              SELECT    station_id,station_name,seller_id, seller,0 AS sales_volume_month,\n" +
                        "                                                        COUNT(*) AS sales_volume_year,0 AS sales_volume_day,0 target_year\n" +
                        "                                              FROM ( SELECT b.station_id,c.station_name,b.seller_id,\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tb.seller,a.real_deliver_time,b.sign_time\n" +
                        "                                                          FROM dbo.vehicle_sale_contract_detail a\n" +
                        "                                                          LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                                          LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                                          WHERE b.contract_status <> 3 AND b.contract_status <> 4\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t AND a.approve_status <> 30 ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                                              GROUP BY  station_id,station_name,seller_id, seller \n" +

                        "                                              UNION ALL --日销量\n" +
                        "                                              SELECT    station_id,station_name,seller_id, seller,0 AS sales_volume_month,\n" +
                        "                                                        0 AS sales_volume_year,COUNT(*) AS sales_volume_day,0 target_year\n" +
                        "                                              FROM ( SELECT b.station_id,c.station_name,b.seller_id,\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tb.seller,a.real_deliver_time,b.sign_time\n" +
                        "                                                          FROM dbo.vehicle_sale_contract_detail a\n" +
                        "                                                          LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                                          LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                                          WHERE b.contract_status <> 3 AND b.contract_status <> 4\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t AND a.approve_status <> 30 ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_DAY, countType) + ")" +
                        "                                              GROUP BY  station_id,station_name,seller_id, seller ) a\n" +
                        "                                  GROUP BY  station_id, station_name,seller_id, seller \n" +


                        " ) a\n" +
                        "                      LEFT JOIN ( SELECT    a.station_id,COUNT(*) AS sales_volume_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,b.seller_id, \n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\tb.seller,a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30 ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                                  GROUP BY  a.station_id ) b ON b.station_id = a.station_id\n" +
                        "                      LEFT JOIN ( SELECT B.maintainer_id AS user_id, count(1) AS customer_quantity FROM\n" +
                        "\t\t\t\t\t\t\t\t(\n" +
                        "\t\t\t\t\t\t\t\t  SELECT [maintainer_id] = CONVERT(xml,'<root><v>' + REPLACE([maintainer_id], ',', '</v><v>') + '</v></root>') FROM base_related_objects\n" +
                        "\t\t\t\t\t\t\t\t) A OUTER APPLY\n" +
                        "\t\t\t\t\t\t\t\t(\n" +
                        "\t\t\t\t\t\t\t\t  SELECT maintainer_id = N.v.value('.', 'varchar(100)') FROM A.[maintainer_id].nodes('/root/v') N(v)\n" +
                        "\t\t\t\t\t\t\t\t) B\n" +
                        "                                  GROUP BY B.maintainer_id ) c ON a.seller_id = c.user_id\n" +
                        "                      LEFT JOIN dbo.sys_users d ON c.user_id = d.user_id\n" +
                        "                      LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                      LEFT JOIN dbo.sys_stations f ON e.default_station = f.station_id\n" +
                        "                      WHERE     ISNULL(a.station_id, f.station_id) IS NOT NULL ) a\n" +
                        "          LEFT JOIN ( SELECT    seller_id,SUM(purpose_quantity) AS intention_vehicle_quantity\n" +
                        "                      FROM      dbo.presell_visitors\n" +
                        "                      WHERE     visit_result IS NULL OR visit_result = ''\n" +
                        "                      GROUP BY  seller_id ) b ON a.seller_id = b.seller_id\n" +
                        "          LEFT JOIN dbo.sys_users c ON c.user_id = b.seller_id\n" +
                        "          LEFT JOIN dbo.sys_units d ON c.department = d.unit_id\n" +
                        "          LEFT JOIN dbo.sys_stations e ON d.default_station = e.station_id ) a\n" +
                        "WHERE  (" + this.buildStationCondition(stationId) + ") " +
                        (reportSellerIds == null ? "" : " AND a.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        "ORDER BY sales_volume_year DESC;";
            case REPORT_TYPE_STRAIN:
            default:
                return "SELECT  *\n" +
                        "FROM    ( SELECT    a.station_id, --站点ID\n" +
                        "\t\t\t\t\ta.station_name, --站点名称\n" +
                        "\t\t\t\t\ta.vehicle_strain,--品系\n" +
                        "                    a.sales_volume_month, --月销量\n" +
                        "\t\t\t\t\ta.sales_volume_year,--年销量\n" +
                        "           a.sales_volume_day, --日销量  \n" +
                        "\t\t\t\t\ta.target_year,--年目标\n" +
                        "                    ROUND(ISNULL(a.sales_volume_year, 0) * 1.0\n" +
                        "                          / ISNULL(NULLIF(ISNULL(b.sales_volume_year, 0), 0), 1), 2) AS contribution_rate,--年销量贡献率\n" +
                        "                    1 completion_rate,--年目标完成率\n" +
                        "                    DENSE_RANK() OVER ( PARTITION BY a.station_id ORDER BY a.sales_volume_year DESC ) AS ranking_year--年销售排名\n" +
                        "          FROM      ( SELECT    station_id, station_name, vehicle_strain,SUM(sales_volume_month) AS sales_volume_month,\n" +
                        "                                SUM(sales_volume_year) AS sales_volume_year, SUM(sales_volume_day) AS sales_volume_day ,SUM(target_year) AS target_year\n" +
                        "                      FROM      (--月销售\n" +
                        "                                  SELECT    station_id, station_name,vehicle_strain,COUNT(*) AS sales_volume_month,\n" +
                        "                                            0 AS sales_volume_year,0 AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id, c.station_name, a.vehicle_strain,\n" +
                        "                                                        a.real_deliver_time, b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_MONTH, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,vehicle_strain\n" +
                        "                                  UNION ALL --年销量\n" +
                        "                                  SELECT    station_id, station_name,vehicle_strain,0 AS sales_volume_month,\n" +
                        "                                            COUNT(*) AS sales_volume_year,0 AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,a.vehicle_strain,\n" +
                        "                                                        a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,vehicle_strain \n" +

                        "                                  UNION ALL --日销量\n" +
                        "                                  SELECT    station_id, station_name,vehicle_strain,0 AS sales_volume_month,\n" +
                        "                                            0 AS sales_volume_year,COUNT(*) AS sales_volume_day,0 target_year\n" +
                        "                                  FROM      ( SELECT    b.station_id,c.station_name,a.vehicle_strain,\n" +
                        "                                                        a.real_deliver_time,b.sign_time\n" +
                        "                                              FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                              LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                              LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                              LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                              LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                              WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_DAY, countType) + ")" +
                        "                                  GROUP BY  station_id, station_name,vehicle_strain ) a\n" +
                        "                      GROUP BY  station_id, station_name, vehicle_strain\n" +


                        " ) a\n" +
                        "          LEFT JOIN ( SELECT    a.station_id, COUNT(*) AS sales_volume_year\n" +
                        "                      FROM      ( SELECT    b.station_id, c.station_name,a.vehicle_strain AS department_name,\n" +
                        "                                            a.real_deliver_time, b.sign_time\n" +
                        "                                  FROM      dbo.vehicle_sale_contract_detail a\n" +
                        "                                  LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                        "                                  LEFT JOIN dbo.sys_stations c ON b.station_id = c.station_id\n" +
                        "                                  LEFT JOIN dbo.sys_users d ON b.seller_id = d.user_id\n" +
                        "                                  LEFT JOIN dbo.sys_units e ON d.department = e.unit_id\n" +
                        "                                  WHERE b.contract_status <> 3 AND b.contract_status <> 4 AND a.approve_status <> 30\n " +
                        (reportSellerIds == null ? "" : " AND b.seller_id IN ('" + StringUtils.join(reportSellerIds, "','") + "') ") +
                        " ) a\n" +
                        "                                  WHERE     ( " + this.buildCountTypeCondition(COUNT_TIME_YEAR, countType) + ")" +
                        "                      GROUP BY  a.station_id ) b ON b.station_id = a.station_id\n" +
                        "          WHERE     a.station_id IS NOT NULL ) a\n" +
                        "WHERE  (" + this.buildStationCondition(stationId) + ") " +
                        "ORDER BY sales_volume_year DESC;";
        }
    }

    public int typeStringToInt(String typeString) {
        switch (typeString) {
            case "vno":
                return SalesReportService.REPORT_TYPE_VNO;
            case "seller":
                return SalesReportService.REPORT_TYPE_SELLER;
            case "department":
                return SalesReportService.REPORT_TYPE_DEPARTMENT;
            case "locus":
                return SalesReportService.REPORT_TYPE_LOCUS;
            case "mode":
                return SalesReportService.REPORT_TYPE_MODE;
            case "strain":
                return SalesReportService.REPORT_TYPE_STRAIN;
            case "customer_name":
                return SalesReportService.REPORT_TYPE_CUSTOMER_NAME;
            case "customer_profession":
                return SalesReportService.REPORT_TYPE_CUSTOMER_PROFESSION;
            case "customer_area":
                return SalesReportService.REPORT_TYPE_CUSTOMER_AREA;
            case "company":
            default:
                return SalesReportService.REPORT_TYPE_COMPANY;
        }
    }

    public int countTypeToInt(String countTypeString) {
        switch (countTypeString) {
            case "export":
                return SalesReportService.REPORT_COUNT_TYPE_EXPORT;
            case "order":
            default:
                return SalesReportService.REPORT_COUNT_TYPE_ORDER;
        }
    }


    public List<Map<String, Object>> getSaleReport(Map<String, Object> filter, String yearMonth) {
        String sql = "---0、汇总sql\n" +
                "SELECT a.station_id, a.station_name, seller_id, seller,\n" +
                "\t   SUM (customer_quantity) AS customer_quantity,--潜在客户\n" +
                "\t   SUM (purpose_quantity) AS purpose_quantity, --新增意向\n" +
                "\t   SUM (order_quantity) AS order_quantity, --新增定车\n" +
                "\t   SUM (delivery_quantity) AS delivery_quantity, --新增交车\n" +
                "\t   SUM (defeat_quantity) AS defeat_quantity, --新增战败\n" +
                "\t   SUM (target_quantity) AS target_quantity, --目标\n" +
                "\t   CASE WHEN SUM (target_quantity) > 0 THEN SUM (delivery_quantity) * 1.0 / SUM (target_quantity) ELSE 1 END AS month_completion_rate --完成率\n" +
                "FROM (SELECT d.station_name, --站点名称\n" +
                "\t\t\t g.unit_id AS department_id, g.unit_name AS department_name, a.*\n" +
                "\t  FROM (SELECT a.station_id, a.seller_id, a.seller, a.create_time,\n" +
                "\t\t\t\t   CASE WHEN a.purpose_quantity > 1000 THEN 1 ELSE ISNULL (a.purpose_quantity, 0) END AS purpose_quantity,\n" +
                "\t\t\t\t   0 AS order_quantity, 0 AS delivery_quantity, 0 AS defeat_quantity, 0 AS target_quantity,\n" +
                "\t\t\t\t   0 AS customer_quantity\n" +
                "\t\t\tFROM dbo.presell_visitors a WITH ( NOLOCK )\n" +
                "\t\t\tLEFT JOIN dbo.vw_vehicle_type h ON a.vno_id=h.vno_id\n" +
                "\t\t\tWHERE 1=1\n" +
                "\t\t\tAND ISNULL(h.vehicle_kind,1) IN (0,1)--不包含挂车\n" +
                "\t\t\tUNION ALL\n" +
                "\t\t\tSELECT b.station_id, b.seller_id, b.seller, b.create_time,\n" +
                "\t\t\t\t   ISNULL (a.vehicle_quantity, 1) AS purpose_quantity, 0 AS order_quantity,\n" +
                "\t\t\t\t   0 AS delivery_quantity, 0 AS defeat_quantity, 0 AS target_quantity,0 AS customer_quantity\n" +
                "\t\t\tFROM dbo.vehicle_sale_contract_detail a WITH ( NOLOCK )\n" +
                "\t\t\tLEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                "\t\t\tLEFT JOIN dbo.vw_vehicle_type h ON a.vno_id=h.vno_id\n" +
                "\t\t\tWHERE a.approve_status <> 30 AND b.contract_status <> 3 AND b.contract_status <> 4\n" +
                "\t\t\t\tAND (ISNULL (a.visitor_no, '') = '' OR ISNULL (b.visitor_no, '') = '')\n" +
                "\t\t\t\tAND ISNULL(h.vehicle_kind,1) IN (0,1)--不包含挂车\n" +
                "\t\t\tUNION ALL\n" +
                "\t\t\tSELECT b.station_id, b.seller_id, b.seller, b.create_time, 0 AS purpose_quantity,\n" +
                "\t\t\t\t   ISNULL (a.vehicle_quantity, 1) AS order_quantity, 0 AS delivery_quantity,\n" +
                "\t\t\t\t   0 AS defeat_quantity, 0 AS target_quantity,0 AS customer_quantity\n" +
                "\t\t\tFROM dbo.vehicle_sale_contract_detail a WITH ( NOLOCK )\n" +
                "\t\t\tLEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                "\t\t\tLEFT JOIN dbo.vw_vehicle_type h ON a.vno_id=h.vno_id\n" +
                "\t\t\tWHERE a.approve_status <> 30 AND b.contract_status <> 3 AND b.contract_status <> 4\n" +
                "\t\t\tAND ISNULL(h.vehicle_kind,1) IN (0,1)--不包含挂车\n" +
                "\t\t\tUNION ALL\n" +
                "\t\t\tSELECT b.station_id, b.seller_id, b.seller, a.real_deliver_time, 0 AS purpose_quantity,\n" +
                "\t\t\t\t   0 AS order_quantity, ISNULL (a.vehicle_quantity, 1) AS delivery_quantity,\n" +
                "\t\t\t\t   0 AS defeat_quantity, 0 AS target_quantity,0 AS customer_quantity\n" +
                "\t\t\tFROM dbo.vehicle_sale_contract_detail a WITH ( NOLOCK )\n" +
                "\t\t\tLEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                "\t\t\tLEFT JOIN dbo.vw_vehicle_type h ON a.vno_id=h.vno_id\n" +
                "\t\t\tWHERE a.approve_status <> 30 AND b.contract_status <> 3 AND b.contract_status <> 4\n" +
                "\t\t\t\tAND a.real_deliver_time IS NOT NULL \n" +
                "\t\t\t\tAND ISNULL(h.vehicle_kind,1) IN (0,1)--不包含挂车\n" +
                "\t\t\tUNION ALL\n" +
                "\t\t\tSELECT b.station_id, b.seller_id, b.seller, b.create_time, 0 AS purpose_quantity,\n" +
                "\t\t\t\t   0 AS order_quantity, 0 AS delivery_quantity,\n" +
                "\t\t\t\t   CASE WHEN b.purpose_quantity > 1000 THEN 1 ELSE ISNULL (b.purpose_quantity, 0) END AS defeat_quantity,\n" +
                "\t\t\t\t   0 AS target_quantity,0 AS customer_quantity\n" +
                "\t\t\tFROM presell_visitors_fail AS a WITH ( NOLOCK )\n" +
                "\t\t\tINNER JOIN presell_visitors AS b ON a.visitor_no = b.visitor_no\n" +
                "\t\t\tLEFT JOIN dbo.vw_interested_customers o ON b.visitor_id=o.object_id\n" +
                "\t\t\tLEFT JOIN dbo.vw_vehicle_type h ON b.vno_id=h.vno_id\n" +
                "\t\t\tWHERE b.visit_result = 20 AND ISNULL (o.status, 0) = 1 AND ISNULL (o.maintainer_id, '') <> ''\n" +
                "\t\t\tAND ISNULL (o.customer_type, 50) <> 50 AND (o.object_type & 2 = 2 OR o.object_type = 0)\n" +
                "\t\t\tAND ISNULL(h.vehicle_kind,1) IN (0,1)--不包含挂车\n" +
                "\t\t\tUNION ALL\n" +
                "\t\t\tSELECT station_id, seller_id, seller, days, 0 AS purpose_quantity, 0 AS order_quantity,\n" +
                "\t\t\t\t   0 AS delivery_quantity, 0 AS defeat_quantity, sale_target AS target_quantity,\n" +
                "\t\t\t\t   0 AS customer_quantity\n" +
                "\t\t\tFROM vw_vehicle_sale_target_setting WITH ( NOLOCK )\n" +
                "\t\t\tUNION ALL\n" +
                "            SELECT a.create_station_id AS station_id,\n" +
                "\t\t\t\t    c.user_id AS seller_id, c.user_name AS seller,a.create_time,\n" +
                "\t\t\t\t\t0 AS purpose_quantity, 0 AS order_quantity, 0 AS delivery_quantity, 0 AS defeat_quantity,\n" +
                "\t\t\t\t\t0 AS target_quantity, 1 AS customer_quantity\n" +
                "\t\t\tFROM (SELECT dbo.fn_GetFirstMaintainer(maintainer_id) AS maintainerid,* \n" +
                "\t\t\t\t  FROM dbo.vw_stat_effective_customer WITH ( NOLOCK ) WHERE ISNULL(maintainer_id,'')<>'') a\n" +
                "\t\t\tLEFT JOIN dbo.sys_users c ON c.user_id = a.maintainerid\n" +
                "\t\t\tLEFT JOIN dbo.sys_units d ON c.department = d.unit_id\n" +
                "\t\t\tWHERE ISNULL(a.maintainerid,'')<>''\n" +
                "\t\t\t\tAND ISNULL (customer_type, 50) <> 50 AND ISNULL (a.status, 0) = 1\n" +
                "\t\t\t\tAND (a.object_type & 2 = 2 OR a.object_type = 0)\n" +
                "\t\t\t) a\n" +
                "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON a.station_id = d.station_id\n" +
                "\t  LEFT JOIN dbo.sys_users f ON a.seller_id = f.user_id\n" +
                "\t  LEFT JOIN dbo.sys_units g ON f.department = g.unit_id) a\n" +
                "WHERE 1 = 1   ";

        String groupBy = "GROUP BY a.station_id, a.station_name, seller_id, seller";
        String condition = getConditionBySaleReport(filter, yearMonth);

//        logger.debug("销售战报:sql \r\n"+String.format("%s %s %s", sql, condition, groupBy));
        List<Map<String, Object>> reportData = baseDao.getMapBySQL(String.format("%s %s %s", sql, condition, groupBy), null);

        return reportData;
    }

    private String getConditionBySaleReport(Map<String, Object> filter, String yearMonth) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String[] stations = userService.getModuleStationId(user, MODULE_ID_EFFECTIVE_CUSTOMER);
        String condition = String.format(" AND a.station_id in ('%s')", StringUtils.join(stations, "','"));
        String mapCondition = baseDao.mapToFilterString(filter, "a");
        if (StringUtils.isNotBlank(mapCondition)) {
            condition = String.format(" %s AND %s", condition, mapCondition);
        }

        if (StringUtils.isNotBlank(yearMonth)) {
            condition += " AND CONVERT(nvarchar(6),a.create_time,112) = '" + yearMonth + "'";
        }

        String maintenanceUserCondition = interestedCustomersService.getMaintenanceUserCondition("seller_id", "a",true);
        if(StringUtils.isEmpty(maintenanceUserCondition)){
            maintenanceUserCondition = " and 1<>1 ";
        }
        condition +=maintenanceUserCondition;

        return condition;
    }


    /**
     * 销售战报明细
     *
     * @param filter
     * @param saleReportType
     * @return
     */
    public List<Map<String, Object>> getSaleReportDetail(Map<String, Object> filter, short saleReportType, String yearMonth) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String sql = "";
        switch (saleReportType) {
            case 1:
                sql = "---1、意向明细\n" +
                        "SELECT *,ROW_NUMBER() OVER(ORDER BY CHARINDEX (ISNULL(a.intent_level,'空'), 'H,A,B,C,D,N,失效,成交,战败,空')) AS row_id\n" +
                        "FROM (SELECT a.station_id, --站点ID\n" +
                        "\t\t\t d.station_name, --站点名称\n" +
                        "\t\t\t b.object_name, --客户名称\n" +
                        "\t\t\t a.seller_id,--销售员ID\n" +
                        "\t\t\t a.seller, --销售员\n" +
                        "\t\t\t g.unit_id AS department_id, g.unit_name AS department_name,a.create_time,\n" +
                        "\t\t\t ISNULL (a.vehicle_strain, h.vehicleStrain) AS vehicle_strain, --品系\n" +
                        "\t\t\t ISNULL (a.vehicle_vno, a.short_name_vno) AS vehicle_vno, --意向车型\n" +
                        "\t\t\t h.vehicle_kind,\n" +
                        "\t\t\t CASE WHEN a.purpose_quantity > 1000 THEN 1 ELSE ISNULL(a.purpose_quantity,0) END AS purpose_quantity, --数量\n" +
                        "\t\t\t a.visit_time, --线索日期\n" +
                        "\t\t\t a.intent_level --意向级别\n" +
                        "\t  FROM dbo.presell_visitors a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON a.station_id = d.station_id\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_type h ON a.vno_id = h.vno_id\n" +
                        "\t  LEFT JOIN dbo.vw_interested_customers b ON a.visitor_id = b.object_id\n" +
                        "\t  LEFT JOIN dbo.sys_users f ON a.seller_id = f.user_id\n" +
                        "\t  LEFT JOIN dbo.sys_units g ON f.department = g.unit_id\n" +
                        "\t  WHERE 1=1\n" +
                        "\t  UNION ALL\n" +
                        "\t  SELECT b.station_id, d.station_name,\n" +
                        "\t\t\t b.customer_name,b.seller_id, b.seller,  g.unit_id AS department_id,\n" +
                        "\t\t\t g.unit_name AS department_name,b.create_time,a.vehicle_strain, a.vehicle_vno,h.vehicle_kind,\n" +
                        "\t\t\t ISNULL(a.vehicle_quantity,1), b.sign_time, '成交' AS intent_level\n" +
                        "\t  FROM dbo.vehicle_sale_contract_detail a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON b.station_id = d.station_id\n" +
                        "\t  LEFT JOIN dbo.sys_users f ON b.seller_id = f.user_id\n" +
                        "\t  LEFT JOIN dbo.sys_units g ON f.department = g.unit_id\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_type h ON a.vno_id=h.vno_id\n" +
                        "\t  WHERE a.approve_status <> 30 AND b.contract_status <> 3 AND b.contract_status <> 4\n" +
                        "\t\t  AND (ISNULL (a.visitor_no, '') = '' OR ISNULL (b.visitor_no, '') = '')) a\n" +
                        "WHERE 1 = 1     \n" +
                        "AND ISNULL(a.vehicle_kind,1) IN (0,1)--不包含挂车   \n";
                break;
            case 2:
                sql = "--2、定车明细\n" +
                        "SELECT *,ROW_NUMBER() OVER(ORDER BY deliver_days DESC) AS row_id\n" +
                        "FROM (SELECT b.station_id, --站点ID\n" +
                        "\t\t\t d.station_name, --站点名称\n" +
                        "\t\t\t b.customer_name, --客户名称\n" +
                        "\t\t\t b.seller_id,--销售员ID\n" +
                        "\t\t\t b.seller, --销售员\n" +
                        "\t\t\t a.vehicle_strain, --品系\n" +
                        "\t\t\t a.vehicle_vno, --车型\n" +
                        "\t\t\t  g.unit_id AS department_id, g.unit_name AS department_name,\n" +
                        "\t\t\t ISNULL (a.vehicle_quantity, 1) AS vehicle_quantity, --数量\n" +
                        "\t\t\t CASE WHEN a.real_deliver_time IS NOT NULL THEN 0\n" +
                        "\t\t\t ELSE DATEDIFF (DAY, GETDATE (), a.plan_deliver_time) END AS deliver_days, --距交车天数\n" +
                        "\t\t\t b.sign_time, --定车日期\n" +
                        "\t\t\t b.create_time, --服务端统一用create_time查询\n" +
                        "\t\t\t a.plan_deliver_time, --计划交车日期\n" +
                        "\t\t\t i.vehicle_kind,\n" +
                        "\t\t\t CASE WHEN vscd.total_deposit>0 THEN ISNULL (a.deposit, 0)\n" +
                        "\t\t\t ELSE ISNULL(b.deposit,0)*1.0/b.contract_quantity END AS deposit, --定金\n" +
                        "\t\t\t ISNULL (h.resources_quantity, 0) AS resources_quantity --可用资源\n" +
                        "\t  FROM dbo.vehicle_sale_contract_detail a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON b.station_id = d.station_id\n" +
                        "\t    LEFT JOIN (SELECT contract_no,SUM(deposit) AS total_deposit\n" +
                        "\t   FROM dbo.vehicle_sale_contract_detail GROUP BY contract_no)vscd ON vscd.contract_no = a.contract_no\n" +
                        "\t   LEFT JOIN dbo.sys_users f ON b.seller_id = f.user_id\n" +
                        "\t  LEFT JOIN dbo.sys_units g ON f.department = g.unit_id\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_type i ON a.vno_id=i.vno_id\n" +
                        "\t  LEFT JOIN (SELECT vno_id, SUM (vehicle_quantity) AS resources_quantity\n" +
                        "\t\t\t\t FROM dbo.vehicle_stocks\n" +
                        "\t\t\t\t WHERE ISNULL (status, 0) = 0 AND vehicle_vin NOT LIKE 'Del%'\n" +
                        "\t\t\t\t GROUP BY vno_id) h ON a.vno_id = h.vno_id\n" +
                        "\t  WHERE a.approve_status <> 30 AND b.contract_status <> 3 AND b.contract_status <> 4) a\n" +
                        "WHERE 1 = 1  \n" +
                        "AND ISNULL(a.vehicle_kind,1) IN (0,1)--不包含挂车  \n";
                break;
            case 3:
                sql = "--3、交车明细\n" +
                        "SELECT *, ROW_NUMBER () OVER (ORDER BY a.real_deliver_time DESC) AS row_id\n" +
                        "FROM (SELECT g.unit_id AS department_id, g.unit_name AS department_name, b.station_id, --站点ID\n" +
                        "\t\t\t d.station_name, --站点名称\n" +
                        "\t\t\t b.customer_name, --客户名称\n" +
                        "\t\t\t b.seller_id, --销售员ID\n" +
                        "\t\t\t b.seller, --销售员\n" +
                        "\t\t\t a.vehicle_strain, --品系\n" +
                        "\t\t\t a.vehicle_vno, --车型\n" +
                        "\t\t\t a.vehicle_quantity, --数量\n" +
                        "\t\t\t a.real_deliver_time, --交车日期\n" +
                        "\t\t\t i.vehicle_kind,\n" +
                        "\t\t\t a.real_deliver_time AS create_time --服务端统一用create_time查询\n" +
                        "\t  FROM dbo.vehicle_sale_contract_detail a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON b.station_id = d.station_id\n" +
                        "\t  LEFT JOIN dbo.sys_users f ON b.seller_id = f.user_id\n" +
                        "\t  LEFT JOIN dbo.sys_units g ON f.department = g.unit_id\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_type i ON a.vno_id=i.vno_id\n" +
                        "\t  WHERE a.approve_status <> 30 AND b.contract_status <> 3 AND b.contract_status <> 4\n" +
                        "\t\t  AND a.real_deliver_time IS NOT NULL) a\n" +
                        "WHERE 1 = 1  \n" +
                        "AND ISNULL(a.vehicle_kind,1) IN (0,1)--不包含挂车 \n";
                break;
            case 4:
                sql = "--4、战败明细\n" +
                        "SELECT *,ROW_NUMBER() OVER(ORDER BY a.finish_date DESC) AS row_id\n" +
                        "FROM (SELECT b.station_id, --站点ID\n" +
                        "\t\t\t d.station_name, --站点名称\n" +
                        "\t\t\t c.object_name, --客户名称\n" +
                        "\t\t\t b.seller_id,--销售员ID\n" +
                        "\t\t\t b.seller, --销售员\n" +
                        "\t\t\t  g.unit_id AS department_id, g.unit_name AS department_name,b.create_time,\n" +
                        "\t\t\t ISNULL (b.vehicle_strain, h.vehicleStrain) AS vehicle_strain, --品系\n" +
                        "\t\t\t ISNULL (b.vehicle_vno, b.short_name_vno) AS vehicle_vno, --车型\n" +
                        "\t\t\t CASE WHEN b.purpose_quantity > 1000 THEN 1 ELSE ISNULL (b.purpose_quantity, 0) END AS purpose_quantity, --数量\n" +
                        "\t\t\t ISNULL (b.finish_date, a.create_time) AS finish_date, --战败日期\n" +
                        "\t\t\t i.vehicle_kind,\n" +
                        "\t\t\t b.reason --战败原因\n" +
                        "\t  FROM presell_visitors_fail AS a WITH ( NOLOCK )\n" +
                        "\t  INNER JOIN presell_visitors AS b ON a.visitor_no = b.visitor_no\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_type i ON b.vno_id=i.vno_id\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON b.station_id = d.station_id\n" +
                        "\t  LEFT JOIN dbo.sys_users f ON b.seller_id = f.user_id\n" +
                        "\t  LEFT JOIN dbo.sys_units g ON f.department = g.unit_id\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_type h ON b.vehicle_vno = h.product_model\n" +
                        "\t  LEFT JOIN dbo.vw_interested_customers c ON b.visitor_id = c.object_id\n" +
                        "\t  WHERE b.visit_result = 20 AND ISNULL (c.status, 0) = 1 AND ISNULL (c.maintainer_id, '') <> ''\n" +
                        "\tAND ISNULL (c.customer_type, 50) <> 50 AND (c.object_type & 2 = 2 OR c.object_type = 0)) a\n" +
                        "WHERE 1 = 1 \n" +
                        "AND ISNULL(a.vehicle_kind,1) IN (0,1)--不包含挂车   \n";
                break;
            case 5:
                sql = "---潜在客户明细\n" +
                        "SELECT a.object_name, --客户\n" +
                        "\t   seller, --销售员\n" +
                        "\t   create_time --建档日期\n" +
                        "FROM (SELECT a.create_station_id AS station_id,\n" +
                        "\t\t\t\t   YEAR (a.create_time) AS year, MONTH (a.create_time) AS month, a.create_time, a.object_id,\n" +
                        "\t\t\t\t   a.object_name, c.user_id AS seller_id, c.user_name AS seller, d.unit_id AS department_id,\n" +
                        "\t\t\t\t   d.unit_name AS department_name\n" +
                        "\t\t\tFROM (SELECT dbo.fn_GetFirstMaintainer(maintainer_id) AS maintainerid,* \n" +
                        "\t\t\t\t  FROM dbo.vw_stat_effective_customer WITH ( NOLOCK ) WHERE ISNULL(maintainer_id,'')<>'') a\n" +
                        "\t\t\tLEFT JOIN dbo.sys_users c ON c.user_id = a.maintainerid\n" +
                        "\t\t\tLEFT JOIN dbo.sys_units d ON c.department = d.unit_id\n" +
                        "\t\t\tWHERE ISNULL(a.maintainerid,'')<>''\n" +
                        "\t\t\t\tAND ISNULL (customer_type, 50) <> 50 AND ISNULL (a.status, 0) = 1\n" +
                        "\t\t\t\tAND (a.object_type & 2 = 2 OR a.object_type = 0)) a\n" +
                        "WHERE 1 = 1  \n" ;
                break;
            default:
                break;
        }

        if (StringUtils.isBlank(sql)) {
            throw new ServiceException("查询销售战报明细出错，未知的明细类型：" + saleReportType);
        }


        String condition = getConditionBySaleReport(filter, yearMonth);

//        logger.debug("销售战报明细-sql："+String.format("%s %s", sql, condition));
        List<Map<String, Object>> reportData = baseDao.getMapBySQL(String.format("%s %s", sql, condition), null);

        return reportData;
    }


    public List<Map<String, Object>> getSaleYearReport(Map<String, Object> filter, String year) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String sql = baseDao.getCurrentSession().getNamedQuery("saleYearReport").getQueryString();
        //不知道对应的moduleId,则取用户部门的所辖站点
        String[] stations = userService.getModuleStationId(user, MODULE_ID_EFFECTIVE_CUSTOMER);

        //库存明细，只有站点条件
        String stockCondition = String.format(" AND a.station_id in ('%s')", StringUtils.join(stations, "','"));
        String mapCondition = baseDao.mapToFilterString(filter, "a");

        String condition = stockCondition;
        if (StringUtils.isNotBlank(mapCondition)) {
            condition = String.format(" %s AND %s", condition, mapCondition);
        }

        if (StringUtils.isBlank(year)) {
            year = "YEAR(GETDATE ())";
        }
        String maintenanceUserCondition = interestedCustomersService.getMaintenanceUserCondition("seller_id", "a",true);
        if(StringUtils.isEmpty(maintenanceUserCondition)){
            maintenanceUserCondition = " and 1<>1 ";
        }
        sql = sql.replace("[MAINTENANCE_USER_CONDITION]", maintenanceUserCondition).replace("[STOCK_CONDITION]", stockCondition).replace("[CONDITION]", condition).replace("[YEAR]", year);
//        logger.debug(String.format("销售年报-sql:\r\n%s",sql));
        List<Map<String, Object>> reportData = baseDao.getMapBySQL(sql, null);

        return reportData;
    }


    public List<Map<String, Object>> getSaleYearReportDetail(Map<String, Object> filter, short saleReportType, String year, String month) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String sql = "";
        switch (saleReportType) {
            case 1:
                sql = "  ---新增客户明细\n" +
                        "SELECT a.object_name, --客户\n" +
                        "\t   seller, --销售员\n" +
                        "\t   create_time --建档日期\n" +
                        "FROM (SELECT a.create_station_id AS station_id,\n" +
                        "\t\t\t\t   YEAR (a.create_time) AS year, MONTH (a.create_time) AS month, a.create_time, a.object_id,\n" +
                        "\t\t\t\t   a.object_name, c.user_id AS seller_id, c.user_name AS seller, d.unit_id AS department_id,\n" +
                        "\t\t\t\t   d.unit_name AS department_name\n" +
                        "\t\t\tFROM (SELECT dbo.fn_GetFirstMaintainer(maintainer_id) AS maintainerid,* \n" +
                        "\t\t\tFROM dbo.vw_stat_effective_customer WITH ( NOLOCK ) WHERE ISNULL(maintainer_id,'')<>'') a\n" +
                        "\t\t\tLEFT JOIN dbo.sys_users c ON c.user_id = a.maintainerid\n" +
                        "\t\t\tLEFT JOIN dbo.sys_units d ON c.department = d.unit_id\n" +
                        "\t\t\tWHERE ISNULL(a.maintainerid,'')<>''\n" +
                        "\t\t\t\tAND ISNULL (customer_type, 50) <> 50 AND ISNULL (a.status, 0) = 1\n" +
                        "\t\t\t\tAND (a.object_type & 2 = 2 OR a.object_type = 0)) a\n" +
                        "WHERE 1 = 1  ";
                break;
            case 2:
                sql = " --意向台数明细\n" +
                        "SELECT *,ROW_NUMBER() OVER(ORDER BY CHARINDEX (ISNULL(a.intent_level,'空'), 'H,A,B,C,D,N,失效,成交,战败,空')) AS row_id\n" +
                        "FROM (SELECT a.station_id, --站点ID\n" +
                        "\t\t\t d.station_name, --站点名称\n" +
                        "\t\t\t b.object_name, --客户名称\n" +
                        "\t\t\t a.seller_id,--销售员ID\n" +
                        "\t\t\t a.seller, --销售员\n" +
                        "\t\t\t g.unit_id AS department_id, g.unit_name AS department_name,a.create_time,\n" +
                        "\t\t\t ISNULL (a.vehicle_strain, h.vehicleStrain) AS vehicle_strain, --品系\n" +
                        "\t\t\t ISNULL (a.vehicle_vno, a.short_name_vno) AS vehicle_vno, --意向车型\n" +
                        "\t\t\t CASE WHEN a.purpose_quantity > 1000 THEN 1 ELSE ISNULL(a.purpose_quantity,0) END AS purpose_quantity, --数量\n" +
                        "\t\t\t a.visit_time, --线索日期\n" +
                        "\t\t\t a.intent_level, --意向级别\n" +
                        "\t\t\t YEAR (a.create_time) AS year, MONTH (a.create_time) AS month,h.vehicle_kind\n" +
                        "\t  FROM dbo.presell_visitors a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON a.station_id = d.station_id\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_type h ON a.vno_id = h.vno_id\n" +
                        "\t  LEFT JOIN dbo.vw_interested_customers b ON a.visitor_id = b.object_id\n" +
                        "\t  LEFT JOIN dbo.sys_users f ON a.seller_id = f.user_id\n" +
                        "\t  LEFT JOIN dbo.sys_units g ON f.department = g.unit_id\n" +
                        "\t  WHERE 1=1\n" +
                        "\t  UNION ALL\n" +
                        "\t  SELECT b.station_id, d.station_name,\n" +
                        "\t\t\t b.customer_name,b.seller_id, b.seller,  g.unit_id AS department_id,\n" +
                        "\t\t\t g.unit_name AS department_name,b.sign_time,a.vehicle_strain, a.vehicle_vno,\n" +
                        "\t\t\t ISNULL(a.vehicle_quantity,1), b.create_time, '成交' AS intent_level,\n" +
                        "\t\t\t  YEAR (b.create_time) AS year, MONTH (b.create_time) AS month,h.vehicle_kind\n" +
                        "\t  FROM dbo.vehicle_sale_contract_detail a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON b.station_id = d.station_id\n" +
                        "\t  LEFT JOIN dbo.sys_users f ON b.seller_id = f.user_id\n" +
                        "\t  LEFT JOIN dbo.sys_units g ON f.department = g.unit_id\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_type h ON a.vno_id = h.vno_id\n" +
                        "\t  WHERE a.approve_status <> 30 AND b.contract_status <> 3 AND b.contract_status <> 4\n" +
                        "\t\t  AND (ISNULL (a.visitor_no, '') = '' OR ISNULL (b.visitor_no, '') = '')) a\n" +
                        "WHERE 1 = 1 \n" +
                        "AND ISNULL(a.vehicle_kind,1) IN (0,1)--不包含挂车 \n";
                break;
            case 3:
                sql = " ---定车明细\n" +
                        "SELECT *,ROW_NUMBER() OVER(ORDER BY deliver_days DESC) AS row_id\n" +
                        "FROM (SELECT b.station_id, --站点ID\n" +
                        "\t\t\t d.station_name, --站点名称\n" +
                        "\t\t\t b.customer_name, --客户名称\n" +
                        "\t\t\t b.seller_id,--销售员ID\n" +
                        "\t\t\t b.seller, --销售员\n" +
                        "\t\t\t a.vehicle_strain, --品系\n" +
                        "\t\t\t a.vehicle_vno, --车型\n" +
                        "\t\t\t  g.unit_id AS department_id, g.unit_name AS department_name,\n" +
                        "\t\t\t ISNULL (a.vehicle_quantity, 1) AS vehicle_quantity, --数量\n" +
                        "\t\t\t CASE WHEN a.real_deliver_time IS NOT NULL THEN 0\n" +
                        "\t\t\t ELSE DATEDIFF (DAY, GETDATE (), a.plan_deliver_time) END AS deliver_days, --距交车天数\n" +
                        "\t\t\t b.sign_time, --定车日期\n" +
                        "\t\t\t a.plan_deliver_time, --计划交车日期\n" +
                        "\t\t\t i.vehicle_kind,\n" +
                        "\t\t\t  CASE WHEN vscd.total_deposit>0 THEN ISNULL (a.deposit, 0)\n" +
                        "\t\t\t ELSE ISNULL(b.deposit,0)*1.0/b.contract_quantity END AS deposit, --定金\n" +
                        "\t\t\t ISNULL (h.resources_quantity, 0) AS resources_quantity ,--可用资源\n" +
                        "\t\t\t YEAR (b.create_time) AS year, MONTH (b.create_time) AS month,\n" +
                        "\t\t\t CASE WHEN ( SELECT COUNT (*)\n" +
                        "\t\t\t\t   FROM dbo.vehicle_loan_budget_details a1\n" +
                        "\t\t\t\t   LEFT JOIN dbo.vehicle_loan_budget b1 ON b1.document_no = a1.document_no\n" +
                        "\t\t\t\t   WHERE b1.status =50\n" +
                        "\t\t\t\t\t   AND a1.sale_contract_detail_id = a.contract_detail_id ) > 0\n" +
                        "\t\t\tTHEN 1 ELSE 0 END AS is_vehicle_loan,\n" +
                        "\t\tCASE WHEN ( SELECT COUNT (*)\n" +
                        "\t\t\t\t\tFROM dbo.insurance a2\n" +
                        "\t\t\t\t\tWHERE a2.approve_status = 1\n" +
                        "\t\t\t\t\t\tAND a2.vehicle_id = a.vehicle_id\n" +
                        "\t\t\t\t\t\tAND a2.contract_no = a.contract_no ) > 0\n" +
                        "\t\t\t THEN 1 ELSE 0 END AS is_insurance\n" +
                        "\t  FROM dbo.vehicle_sale_contract_detail a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON b.station_id = d.station_id\n" +
                        "\t   LEFT JOIN dbo.sys_users f ON b.seller_id = f.user_id\n" +
                        "\t  LEFT JOIN dbo.sys_units g ON f.department = g.unit_id\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_type i ON a.vno_id = i.vno_id\n" +
                        "\t   LEFT JOIN (SELECT contract_no,SUM(deposit) AS total_deposit\t   \n" +
                        "\t   FROM dbo.vehicle_sale_contract_detail GROUP BY contract_no)vscd ON vscd.contract_no = a.contract_no\n" +
                        "\t  LEFT JOIN (SELECT vno_id, SUM (vehicle_quantity) AS resources_quantity\n" +
                        "\t\t\t\t FROM dbo.vehicle_stocks\n" +
                        "\t\t\t\t WHERE ISNULL (status, 0) = 0 AND vehicle_vin NOT LIKE 'Del%'\n" +
                        "\t\t\t\t GROUP BY vno_id) h ON a.vno_id = h.vno_id\n" +
                        "\t  WHERE a.approve_status <> 30 AND b.contract_status <> 3 AND b.contract_status <> 4) a\n" +
                        "WHERE 1 = 1 \n" +
                        "AND ISNULL(a.vehicle_kind,1) IN (0,1)--不包含挂车 \n";
                break;
            case 4:
                sql = "---交车明细\n" +
                        "SELECT *,ROW_NUMBER() OVER(ORDER BY a.real_deliver_time DESC) AS row_id\n" +
                        "FROM (SELECT g.unit_id AS department_id, g.unit_name AS department_name,\n" +
                        "\t\t\t b.station_id, --站点ID\n" +
                        "\t\t\t d.station_name, --站点名称\n" +
                        "\t\t\t b.customer_name, --客户名称\n" +
                        "\t\t\t b.seller_id,--销售员ID\n" +
                        "\t\t\t b.seller, --销售员\n" +
                        "\t\t\t a.vehicle_strain, --品系\n" +
                        "\t\t\t a.vehicle_vno, --车型\n" +
                        "\t\t\t a.vehicle_quantity, --数量\n" +
                        "\t\t\t a.real_deliver_time, --交车日期\n" +
                        "\t\t\t YEAR (a.real_deliver_time) AS year, MONTH (a.real_deliver_time) AS month,i.vehicle_kind\n" +
                        "\t  FROM dbo.vehicle_sale_contract_detail a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON b.station_id = d.station_id\n" +
                        "\t  LEFT JOIN dbo.sys_users f ON b.seller_id = f.user_id\n" +
                        "\t  LEFT JOIN dbo.sys_units g ON f.department = g.unit_id\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_type i ON a.vno_id = i.vno_id\n" +
                        "\t  WHERE a.approve_status <> 30 AND b.contract_status <> 3 AND b.contract_status <> 4\n" +
                        "\t\t  AND a.real_deliver_time IS NOT NULL) a\n" +
                        "WHERE 1=1 \n" +
                        "AND ISNULL(a.vehicle_kind,1) IN (0,1)--不包含挂车 \n";
                break;
            case 5:
                sql = " --保有客户明细\n" +
                        "SELECT a.object_name, --客户\n" +
                        "\t\tseller_id,--客户ID\n" +
                        "\t   seller, --销售员\n" +
                        "\t   create_time --建档日期\n" +
                        "FROM (SELECT  *\n" +
                        "\t  FROM (SELECT a.create_station_id AS station_id,\n" +
                        "\t\t\t\t   a.create_time, a.object_id, a.object_name, c.user_id AS seller_id, c.user_name AS seller,\n" +
                        "\t\t\t\t   d.unit_id AS department_id, d.unit_name AS department_name\n" +
                        "\t\t\tFROM (SELECT dbo.fn_GetFirstMaintainer(maintainer_id) AS maintainerid,* \n" +
                        "\t\t\t\t  FROM dbo.vw_stat_effective_customer WITH ( NOLOCK ) WHERE ISNULL(maintainer_id,'')<>'') a\n" +
                        "\t\t\tLEFT JOIN dbo.sys_users c ON c.user_id = a.maintainerid\n" +
                        "\t\t\tLEFT JOIN dbo.sys_units d ON c.department = d.unit_id\n" +
                        "\t\t\tWHERE ISNULL(a.maintainerid,'')<>''\n" +
                        "\t\t\t\tAND ISNULL (customer_type, 50) <> 50 AND ISNULL (a.status, 0) = 1\n" +
                        "\t\t\t\tAND (a.object_type & 2 = 2 OR a.object_type = 0)) a\n" +
                        "\t  WHERE 1 = 1) a\n" +
                        "WHERE 1 = 1 ";
                break;
            case 6:
                sql = " --目标销量明细\n" +
                        "SELECT *\n" +
                        "FROM (SELECT a.station_id, x.station_name,\n" +
                        "\t\t\t a.seller_id, c.user_name AS seller, d.unit_id AS department_id, d.unit_name AS department_name,\n" +
                        "\t\t\t a.years AS year, a.months AS month, ISNULL(a.sale_target,0) AS sale_target\n" +
                        "\t  FROM vw_vehicle_sale_target_setting a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN dbo.sys_users c ON c.user_id = a.seller_id\n" +
                        "\t  LEFT JOIN dbo.sys_units d ON c.department = d.unit_id\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) x ON x.station_id = a.station_id) a\n" +
                        "WHERE 1 = 1 ";
                break;
            case 7:
                sql = "---消贷台数明细\n" +
                        "SELECT *,ROW_NUMBER() OVER(ORDER BY deliver_days DESC) AS row_id\n" +
                        "FROM (SELECT b.station_id, --站点ID\n" +
                        "\t\t\t d.station_name, --站点名称\n" +
                        "\t\t\t b.customer_name, --客户名称\n" +
                        "\t\t\t b.seller_id,--销售员ID\n" +
                        "\t\t\t b.seller, --销售员\n" +
                        "\t\t\t a.vehicle_strain, --品系\n" +
                        "\t\t\t a.vehicle_vno, --车型\n" +
                        "\t\t\t  g.unit_id AS department_id, g.unit_name AS department_name,a.real_deliver_time,\n" +
                        "\t\t\t ISNULL (a.vehicle_quantity, 1) AS vehicle_quantity, --数量\n" +
                        "\t\t\t CASE WHEN a.real_deliver_time IS NOT NULL THEN 0\n" +
                        "\t\t\t ELSE DATEDIFF (DAY, GETDATE (), a.plan_deliver_time) END AS deliver_days, --距交车天数\n" +
                        "\t\t\t b.sign_time, --定车日期\n" +
                        "\t\t\t a.plan_deliver_time, --计划交车日期\n" +
                        "\t\t\tCASE WHEN vscd.total_deposit>0 THEN ISNULL (a.deposit, 0)\n" +
                        "\t\t\t ELSE ISNULL(b.deposit,0)*1.0/b.contract_quantity END AS deposit, --定金\n" +
                        "\t\t\t ISNULL (h.resources_quantity, 0) AS resources_quantity ,--可用资源\n" +
                        "\t\t\t YEAR (a.real_deliver_time) AS year, MONTH (a.real_deliver_time) AS month,i.vehicle_kind,\n" +
                        "\t\t\t CASE WHEN ( SELECT COUNT (*)\n" +
                        "\t\t\t\t   FROM dbo.vehicle_loan_budget_details a1\n" +
                        "\t\t\t\t   LEFT JOIN dbo.vehicle_loan_budget b1 ON b1.document_no = a1.document_no\n" +
                        "\t\t\t\t   WHERE b1.status =50\n" +
                        "\t\t\t\t\t   AND a1.sale_contract_detail_id = a.contract_detail_id ) > 0\n" +
                        "\t\t\tTHEN 1 ELSE 0 END AS is_vehicle_loan,\n" +
                        "\t\tCASE WHEN ( SELECT COUNT (*)\n" +
                        "\t\t\t\t\tFROM dbo.insurance a2\n" +
                        "\t\t\t\t\tWHERE a2.approve_status = 1\n" +
                        "\t\t\t\t\t\tAND a2.vehicle_id = a.vehicle_id\n" +
                        "\t\t\t\t\t\tAND a2.contract_no = a.contract_no ) > 0\n" +
                        "\t\t\t THEN 1 ELSE 0 END AS is_insurance\n" +
                        "\t  FROM dbo.vehicle_sale_contract_detail a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON b.station_id = d.station_id\n" +
                        "\t   LEFT JOIN dbo.sys_users f ON b.seller_id = f.user_id\n" +
                        "\t  LEFT JOIN dbo.sys_units g ON f.department = g.unit_id\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_type i ON a.vno_id = i.vno_id\n" +
                        "\t   LEFT JOIN (SELECT contract_no,SUM(deposit) AS total_deposit\n" +
                        "\t   FROM dbo.vehicle_sale_contract_detail GROUP BY contract_no)vscd ON vscd.contract_no = a.contract_no\n" +
                        "\t  LEFT JOIN (SELECT vno_id, SUM (vehicle_quantity) AS resources_quantity\n" +
                        "\t\t\t\t FROM dbo.vehicle_stocks\n" +
                        "\t\t\t\t WHERE ISNULL (status, 0) = 0 AND vehicle_vin NOT LIKE 'Del%'\n" +
                        "\t\t\t\t GROUP BY vno_id) h ON a.vno_id = h.vno_id\n" +
                        "\t  WHERE a.approve_status <> 30 AND b.contract_status <> 3 AND b.contract_status <> 4 AND a.real_deliver_time IS NOT NULL) a\n" +
                        "WHERE 1 = 1 \n" +
                        "AND ISNULL(a.vehicle_kind,1) IN (0,1)--不包含挂车\n" +
                        "AND a.is_vehicle_loan =1  ";
                break;
            case 8:
                sql = "---保险台数明细\n" +
                        "SELECT *, ROW_NUMBER () OVER (ORDER BY deliver_days DESC) AS row_id\n" +
                        "FROM (SELECT b.station_id, --站点ID\n" +
                        "\t\t\t d.station_name, --站点名称\n" +
                        "\t\t\t b.customer_name, --客户名称\n" +
                        "\t\t\t b.seller_id, --销售员ID\n" +
                        "\t\t\t b.seller, --销售员\n" +
                        "\t\t\t a.vehicle_strain, --品系\n" +
                        "\t\t\t a.vehicle_vno, --车型\n" +
                        "\t\t\t g.unit_id AS department_id, g.unit_name AS department_name, a.real_deliver_time,\n" +
                        "\t\t\t ISNULL (a.vehicle_quantity, 1) AS vehicle_quantity, --数量\n" +
                        "\t\t\t CASE WHEN a.real_deliver_time IS NOT NULL THEN 0\n" +
                        "\t\t\t ELSE DATEDIFF (DAY, GETDATE (), a.plan_deliver_time) END AS deliver_days, --距交车天数\n" +
                        "\t\t\t b.sign_time, --定车日期\n" +
                        "\t\t\t a.plan_deliver_time, --计划交车日期\n" +
                        "\t\t\t CASE WHEN vscd.total_deposit > 0 THEN ISNULL (a.deposit, 0)\n" +
                        "\t\t\t ELSE ISNULL (b.deposit, 0) * 1.0 / b.contract_quantity END AS deposit, --定金\n" +
                        "\t\t\t ISNULL (h.resources_quantity, 0) AS resources_quantity, --可用资源\n" +
                        "\t\t\t YEAR (a.real_deliver_time) AS year, MONTH (a.real_deliver_time) AS month,i.vehicle_kind,\n" +
                        "\t\t\t CASE WHEN (SELECT COUNT (*)\n" +
                        "\t\t\t\t\t\tFROM dbo.vehicle_loan_budget_details a1\n" +
                        "\t\t\t\t\t\tLEFT JOIN dbo.vehicle_loan_budget b1 ON b1.document_no = a1.document_no\n" +
                        "\t\t\t\t\t\tWHERE b1.status = 50 AND a1.sale_contract_detail_id = a.contract_detail_id) > 0 THEN\n" +
                        "\t\t\t\t  1 ELSE 0 END AS is_vehicle_loan,\n" +
                        "\t\t\t CASE WHEN (SELECT COUNT (*)\n" +
                        "\t\t\t\t\t\tFROM dbo.insurance a2\n" +
                        "\t\t\t\t\t\tWHERE a2.approve_status = 1 AND a2.vehicle_id = a.vehicle_id\n" +
                        "\t\t\t\t\t\t\tAND a2.contract_no = a.contract_no) > 0 THEN 1 ELSE 0 END AS is_insurance\n" +
                        "\t  FROM dbo.vehicle_sale_contract_detail a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON b.station_id = d.station_id\n" +
                        "\t  LEFT JOIN dbo.sys_users f ON b.seller_id = f.user_id\n" +
                        "\t  LEFT JOIN dbo.sys_units g ON f.department = g.unit_id\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_type i ON a.vno_id = i.vno_id\n" +
                        "\t  LEFT JOIN (SELECT contract_no, SUM (deposit) AS total_deposit FROM dbo.vehicle_sale_contract_detail GROUP BY contract_no) vscd ON vscd.contract_no = a.contract_no\n" +
                        "\t  LEFT JOIN (SELECT vno_id, SUM (vehicle_quantity) AS resources_quantity\n" +
                        "\t\t\t\t FROM dbo.vehicle_stocks\n" +
                        "\t\t\t\t WHERE ISNULL (status, 0) = 0 AND vehicle_vin NOT LIKE 'Del%'\n" +
                        "\t\t\t\t GROUP BY vno_id) h ON a.vno_id = h.vno_id\n" +
                        "\t  WHERE a.approve_status <> 30 AND b.contract_status <> 3 AND b.contract_status <> 4\n" +
                        "\t\t  AND a.real_deliver_time IS NOT NULL) a\n" +
                        "WHERE 1 = 1 AND a.is_insurance = 1  \n" +
                        "AND ISNULL(a.vehicle_kind,1) IN (0,1)--不包含挂车 \n";
                break;
            case 9:
                sql = "--库存明细\n" +
                        "SELECT *\n" +
                        "FROM (SELECT a.*,GETDATE() AS create_time,YEAR (GETDATE ()) AS year, MONTH (GETDATE ()) AS month\n" +
                        "\t  FROM (SELECT a.station_id, c.station_name, vehicle_id, vehicle_quantity,a.vehicle_vno,a.vehicle_color,a.stock_age,\n" +
                        "\t  a.warehouse_name,'在库' AS stock_status,a.underpan_no\n" +
                        "\t\t\tFROM dbo.vw_vehicle_stock a WITH ( NOLOCK )\n" +
                        "\t\t\tLEFT JOIN dbo.vw_vehicle_type f ON a.vno_id = f.self_id\n" +
                        "\t\t\tLEFT JOIN dbo.sys_stations c ON a.station_id = c.station_id\n" +
                        "\t\t\tWHERE a.status IN ( 0, 1 ) AND ISNULL (f.is_dongfeng, 0) = 1 AND ISNULL (f.vehicle_kind, 1) <> 2\n" +
                        "\t\t\tUNION ALL\n" +
                        "\t\t\tSELECT * FROM (SELECT d.station_id, st.station_name, a.self_id,\n" +
                        "\t\t\t CASE WHEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0) THEN\n" +
                        "\t\t\t\t\t\t\tISNULL (a.quantity, 0) ELSE ISNULL (g.invoice_qty, 0) END - ISNULL (f.qty, 0) > 0 THEN\n" +
                        "\t\t\t\t  CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0) THEN ISNULL (a.quantity, 0)\n" +
                        "\t\t\t\t   ELSE ISNULL (g.invoice_qty,0) END\n" +
                        "\t\t\t\t  - ISNULL (f.qty, 0) ELSE 0 END AS on_way_qty,c.vehicle_vno,c.color,0 AS stock_age,'' AS warehouse_name,'在途' AS stock_status,'' AS underpan_no\n" +
                        "\t  FROM dbo.vehicle_DF_sap_contract a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN dbo.vehicle_DF_purchase_order b ON b.purchase_order_no = a.purchase_order_no\n" +
                        "\t  LEFT JOIN dbo.vehicle_demand_apply_detail c ON b.apply_order_no = c.order_no\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t  AND b.work_state_audit = c.work_state_audit\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t  AND ISNULL (c.detail_status, '') <> 'CRM删除'\n" +
                        "\t  LEFT JOIN dbo.vehicle_demand_apply d ON c.order_no = d.order_no\n" +
                        "\t  LEFT JOIN dbo.sys_stations st ON d.station_id = st.station_id\n" +
                        "\t  LEFT JOIN (SELECT b.sap_contract_no, COUNT (*) AS qty\n" +
                        "\t\t\t\t FROM dbo.vehicle_DF_sap_delivery a\n" +
                        "\t\t\t\t INNER JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                        "\t\t\t\t WHERE (a.finish_status = 1 OR a.underpan_no LIKE 'DEL_%')\n" +
                        "\t\t\t\t GROUP BY b.sap_contract_no) f ON f.sap_contract_no = a.contract_no\n" +
                        "\t  LEFT JOIN (SELECT b.sap_contract_no, COUNT (*) AS qty\n" +
                        "\t\t\t\t FROM dbo.vehicle_DF_sap_delivery a\n" +
                        "\t\t\t\t INNER JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                        "\t\t\t\t WHERE (a.underpan_no LIKE 'DEL_%')\n" +
                        "\t\t\t\t GROUP BY b.sap_contract_no) f1 ON f1.sap_contract_no = a.contract_no\n" +
                        "\t  LEFT JOIN (SELECT a.sap_contract_no, COUNT (*) invoice_qty\n" +
                        "\t\t\t\t FROM dbo.vehicle_DF_invoice_apply a\n" +
                        "\t\t\t\t WHERE a.status IN ( '开票完成' )\n" +
                        "\t\t\t\t GROUP BY a.sap_contract_no) g ON g.sap_contract_no = a.contract_no\n" +
                        "\t  WHERE d.station_id IS NOT NULL AND a.refuse_reason = '有效'\n" +
                        "\t\t  AND (CASE WHEN ISNULL (a.quantity, 0) <= ISNULL (f1.qty, 0) THEN 0 ELSE\n" +
                        "\t\t\tCASE WHEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0) >= 0\n" +
                        "\t\t\tTHEN ISNULL (a.quantity, 0) - ISNULL (g.invoice_qty, 0) ELSE 0 END END > 0\n" +
                        "\t\t\t\t  OR CASE WHEN CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0) THEN\n" +
                        "\t\t\t\t\t\t\t\t\tISNULL (a.quantity, 0) ELSE ISNULL (g.invoice_qty, 0) END\n" +
                        "\t\t\t\t\t\t\t   - ISNULL (f.qty, 0) > 0 THEN\n" +
                        "\t\t\t\t\t\t  CASE WHEN ISNULL (g.invoice_qty, 0) > ISNULL (a.quantity, 0) THEN\n" +
                        "\t\t\t\t\t\t\t   ISNULL (a.quantity, 0) ELSE ISNULL (g.invoice_qty, 0) END - ISNULL (f.qty, 0) ELSE\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t 0 END > 0)\n" +
                        "\t\t  AND b.status NOT LIKE '%作废%' AND b.status NOT LIKE '%终止%' AND b.status NOT LIKE '%拒绝%')a WHERE a.on_way_qty>0) a\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON a.station_id = d.station_id\n" +
                        "\t  UNION ALL\n" +
                        "\t  SELECT a.station_id, d.station_name, a.vehicle_id,b.vehicle_quantity,b.vehicle_vno,b.vehicle_color,b.stock_age,\n" +
                        "\t  b.warehouse_name,'在库' AS stock_status,b.underpan_no,a.balance_month AS create_time,YEAR (balance_month) AS year, MONTH (balance_month) AS month\n" +
                        "\t  FROM vehicle_stock_month a WITH ( NOLOCK )\n" +
                        "\t  LEFT JOIN dbo.vw_vehicle_stock b ON a.vehicle_id = b.vehicle_id\n" +
                        "\t  LEFT JOIN (SELECT station_id, station_name FROM dbo.sys_stations) d ON a.station_id = d.station_id\n" +
                        "\t) a\n" +
                        "WHERE 1 = 1  ";
                break;

            default:
                break;
        }

        if (StringUtils.isBlank(sql)) {
            throw new ServiceException("查询销售年报明细出错，未知的明细类型：" + saleReportType);
        }


        String[] stations = userService.getModuleStationId(user, MODULE_ID_EFFECTIVE_CUSTOMER);
        //库存明细，只有站点条件
        String stockCondition = String.format(" AND a.station_id in ('%s')", StringUtils.join(stations, "','"));
        //如果year为空则为当前
        stockCondition += String.format(" AND year = %s", StringUtils.isBlank(year) ? "YEAR (GETDATE ())" : year);
        //如果month为空则表示不查询
        if (StringUtils.isNotBlank(month)) {
            stockCondition += " AND month = " + month;
        }


        String mapCondition = baseDao.mapToFilterString(filter, "a");
        String condition = stockCondition;
        if (StringUtils.isNotBlank(mapCondition)) {
            condition = String.format(" %s AND %s", condition, mapCondition);
        }


        if (saleReportType == 5) {
            //保有客户
            sql += String.format(" AND a.station_id in ('%s')", StringUtils.join(stations, "','"));
            if (StringUtils.isNotBlank(mapCondition)) {
                sql += String.format(" AND %s", mapCondition);
            }
            //意向客户筛选条件
            String maintenanceUserCondition = interestedCustomersService.getMaintenanceUserCondition("seller_id", "a",true);
            if(StringUtils.isEmpty(maintenanceUserCondition)){
                maintenanceUserCondition = "and 1<>1 ";
            }
            sql += maintenanceUserCondition;
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");//年
            String yearStr = StringUtils.isBlank(year) ? yearFormat.format(new Date()) : year;
            String monthStr = StringUtils.isBlank(month) ? "12" : month;

            sql += String.format(" AND DATEDIFF(MONTH,a.create_time,'%s-%s-01')>=0", yearStr, monthStr);

        } else if (saleReportType == 9) {
            //库存明细，只有站点条件
            sql = String.format("%s %s", sql, stockCondition).replace("[YEAR]", year).replace("[MONTH]", String.valueOf(month));
        } else {
            //意向客户筛选条件
            String maintenanceUserCondition = interestedCustomersService.getMaintenanceUserCondition("seller_id", "a",true);
            if(StringUtils.isEmpty(maintenanceUserCondition)){
                maintenanceUserCondition = " and 1<>1 ";
            }
            sql += maintenanceUserCondition;
            sql = String.format("%s %s", sql, condition).replace("[YEAR]", year).replace("[MONTH]", String.valueOf(month));
        }

//        logger.debug(String.format("年报明细, reportType:%d sql: \r\n %s", saleReportType, sql));
        List<Map<String, Object>> reportData = baseDao.getMapBySQL(sql, null);

        return reportData;
    }


    /**
     * 销售战报，销售年报初始化数据
     *
     * @return
     */
    public Object getSaleReportInitData() {
        SysUsers user = HttpSessionStore.getSessionUser();
        String[] stations = userService.getModuleStationId(user, MODULE_ID_EFFECTIVE_CUSTOMER);
        String sql = "select * from  sys_stations  where station_id in(:stations)";
        Map<String, Object> params = new HashMap<>(1);
        params.put("stations", stations);

        List<Map<String, Object>> stationList = baseDao.getMapBySQL(sql, params);
        Map<String, Object> rtnMap = new HashMap<>(1);
        rtnMap.put("stations", stationList);
        return rtnMap;
    }
}
