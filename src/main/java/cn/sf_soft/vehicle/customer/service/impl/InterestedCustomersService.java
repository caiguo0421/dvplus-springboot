package cn.sf_soft.vehicle.customer.service.impl;

import cn.sf_soft.basedata.dao.SysLogsDao;
import cn.sf_soft.common.BeanUtil;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.common.gson.GsonUtil;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.util.GetChineseFirstChar;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.support.BaseService;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.user.service.UserService;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.*;
import cn.sf_soft.vehicle.customer.service.SaleCustomerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author caigu
 * @Date 2018-10-08
 * @Description: 意向客户主对象服务
 */
@Service("interestedCustomersService")
public class InterestedCustomersService extends BaseService<InterestedCustomers> {
    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(InterestedCustomersService.class);

    @Autowired
    private BaseDaoHibernateImpl baseDao;

    @Autowired
    private SysLogsDao sysLogsDao;

    @Autowired
    private SysOptionsDao sysOptionsDao;

    @Autowired
    private SaleCustomerService saleCustomerService;

    @Autowired
    private CustomerHistoryService customerHistoryService;

    @Autowired
    private UserService userService;

    /**
     * 有效客户管理-部门查看
     */
    private static final String POPEDOM_DEPT = "10201030";


    /**
     * 有效客户管理-跨部门查看
     */
    private static final String POPEDOM_CROSS_DEPT = "10201040";


    /**
     * 共同维系客户共享查看
     */
    public static final String POPEDOM_SHARED_MAINTAINER = "10201080";


    //有效客户管理
    public final static String MODULE_EFFECTIVE_CUSTOMER = "102010";


    /**
     * 非东贸版
     * 东贸版
     * ADM19010009
     */
    public static final String DV_VERSION = "DV_VERSION";

    /**
     * 证件号码默认值，
     * 个人 88888 单位 99999
     * 改成： 单位默认为88888，个人默认为99999 --20190130
     */
    public static final String DEFAULT_CER_NO_UNIT = "88888";

    public static final String DEFAULT_CER_NO_CUSTOMER = "99999";

    /**
     * 证件类型,默认值
     * 个人是身份证，单位是组织机构代码证
     */
    public static final String DEFAULT_CER_TYPE_UNIT = "组织机构代码证";

    public static final String DEFAULT_CER_TYPE_CUSTOMER = "身份证";


    /**
     * 客户性质
     */
    public static final short OBJECT_NATURE_UNIT = 10;

    public static final short OBJECT_NATURE_CUSTOMER = 20;


    private String optionValue;

    /**
     * saveInterestedCustomer 新增一个参数 optionValue，当optionValue为without_maintainer时，新增的意向客户没有维系人（公海客户）
     */
    private static final String WITHOUT_MAINTAINER = "without_maintainer";


    private static EntityRelation entityRelation;

    static {
        entityRelation = new EntityRelation(InterestedCustomers.class, "objectId", InterestedCustomersService.class);
        entityRelation.addSlave("visitorId", PresellVisitorsService.class);
        entityRelation.addSlave("objectId", PresellVisitorsBackService.class);
        entityRelation.addSlave("customerId", CustomerRetainVehicleOverviewService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }


    /**
     * 设置默认的证件号码
     * ADM19010009
     */
    public void fillDefaultCerNo(InterestedCustomers customers) {
        if (StringUtils.isBlank(customers.getCertificateNo()) && "东贸版".equals(sysOptionsDao.getOptionForString(DV_VERSION))) {
            if (Tools.toShort(customers.getObjectNature(), (short) 10) == OBJECT_NATURE_UNIT) {
                customers.setCertificateType(DEFAULT_CER_TYPE_UNIT);
                customers.setCertificateNo(DEFAULT_CER_NO_UNIT);
            } else {
                customers.setCertificateType(DEFAULT_CER_TYPE_CUSTOMER);
                customers.setCertificateNo(DEFAULT_CER_NO_CUSTOMER);
            }
        }
    }


    public Map<String, Object> getMyCustomersByAllVisitResultNew(String keyword, Map<String, Object> filter, Map<String, Object> customFilter, String sort, int perPage, int page) {
        long time1 = System.currentTimeMillis();
        if (filter == null) {
            filter = new HashMap<>(1);
        }

        Map<String, Object> result = new HashMap<>(6);
        String buildSql = getMyCustomersBuildSql(keyword, filter, customFilter);
        List<Map<String, Object>> item1 = baseDao.getMapBySQL(buildSql, null);
        this.addSysLog("意向客户列表", String.format("耗时：%s ms 报文：%s", (System.currentTimeMillis() - time1), "getMyCustomersByAllVisitResultNew接口-查数据库"));
        List<Map<String, Object>> allResult = item1.stream().limit(perPage).collect(Collectors.toList());
        PageModel p = new PageModel(allResult);
        p.setPage(1);
        p.setPerPage(allResult.size());
        p.setTotalSize(item1.size());
        result.put("all", p);

        List<Map<String, Object>> allIntentResult = item1.stream().filter((item) -> "意向".equals((String) item.get("visit_result_meaning"))).collect(Collectors.toList());
        List<Map<String, Object>> intentResult = allIntentResult.stream().limit(perPage).collect(Collectors.toList());
        PageModel p2 = new PageModel(intentResult);
        p2.setPage(1);
        p2.setPerPage(intentResult.size());
        p2.setTotalSize(allIntentResult.size());
        result.put("intent", p2);

        List<Map<String, Object>> allLatentResult = item1.stream().filter((item) -> "潜在".equals((String) item.get("visit_result_meaning"))).collect(Collectors.toList());
        List<Map<String, Object>> latentResult = allLatentResult.stream().limit(perPage).collect(Collectors.toList());
        PageModel p3 = new PageModel(latentResult);
        p3.setPage(1);
        p3.setPerPage(latentResult.size());
        p3.setTotalSize(allLatentResult.size());
        result.put("latent", p3);

        List<Map<String, Object>> allDealResult = item1.stream().filter((item) -> "成交".equals((String) item.get("visit_result_meaning"))).collect(Collectors.toList());
        List<Map<String, Object>> dealResult = allDealResult.stream().limit(perPage).collect(Collectors.toList());
        PageModel p4 = new PageModel(dealResult);
        p4.setPage(1);
        p4.setPerPage(dealResult.size());
        p4.setTotalSize(allDealResult.size());
        result.put("deal", p4);

        List<Map<String, Object>> allFailResult = item1.stream().filter((item) -> "战败".equals((String) item.get("visit_result_meaning"))).collect(Collectors.toList());
        List<Map<String, Object>> failResult = allFailResult.stream().limit(perPage).collect(Collectors.toList());
        PageModel p5 = new PageModel(failResult);
        p5.setPage(1);
        p5.setPerPage(failResult.size());
        p5.setTotalSize(allFailResult.size());
        result.put("fail", p5);

        List<Map<String, Object>> allSuspectedFailResult = item1.stream().filter((item) -> "疑似战败".equals((String) item.get("visit_result_meaning"))).collect(Collectors.toList());
        List<Map<String, Object>> suspectedFailResult = allSuspectedFailResult.stream().limit(perPage).collect(Collectors.toList());
        PageModel p6 = new PageModel(suspectedFailResult);
        p6.setPage(1);
        p6.setPerPage(suspectedFailResult.size());
        p6.setTotalSize(allSuspectedFailResult.size());
        result.put("suspectedFail", p6);


        this.addSysLog("意向客户列表", String.format("耗时：%s ms %s", (System.currentTimeMillis() - time1), "getMyCustomersByAllVisitResultNew接口-完成"));

        return result;
    }

    //公海客户
    public PageModel getPublicCustomers(String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
        String sortSql;
        if (sort != null && sort.length() > 0) {
            sortSql = " ORDER BY " + sort + " ";
        } else {
            sortSql = "";//按默认row_id排序
        }

        if (page < 1) {
            page = 1;
        }

//        String baseSql = "SELECT  *\n" +
//                "FROM ( SELECT a.*,u.department, dbo.fn_GetStationNames (a.station_id) AS station_name, c.meaning AS customer_type_meaning,\n" +
//                "\t\t\t  d.meaning AS object_nature_meaning,ISNULL(f.vehicle_count,0)AS vehicle_count,              \n" +
//                "\t\t\t  ( a.province + '-' + a.city + '-' + a.area ) AS province_city_area,i.last_sign_time,\n" +
//                "\t\t\t  b.customer_group_name, e.meaning AS is_leader_meaning,h.purpose_quantity,\n" +
//                "\t\t\t  ( SELECT STUFF (( SELECT DISTINCT ',' + pv.vehicle_strain\n" +
//                "\t\t\t\t\t\t\t\tFROM dbo.presell_visitors AS pv\n" +
//                "\t\t\t\t\t\t\t\tWHERE a.object_id = pv.visitor_id\n" +
//                "\t\t\t\t\t\t\t\t\tAND ISNULL (pv.vehicle_strain, '') <> ''\n" +
//                "\t\t\t\t\t\t\t  FOR XML PATH ('')), 1, 1, '')) AS vehicle_strain\n" +
//                "\t   FROM dbo.vw_interested_customers a WITH ( NOLOCK ) \n" +
//                "\t   LEFT JOIN (select user_name+'('+user_no+')' as full_name,* from  sys_users) u ON a.creator = u.full_name\n" +
//                "\t   LEFT JOIN base_customer_groups b ON a.customer_group_id = b.customer_group_id\n" +
//                "\t   LEFT JOIN ( SELECT * FROM sys_flags WHERE field_no = 'visitor_type' ) c ON a.customer_type = c.code\n" +
//                "\t   LEFT JOIN ( SELECT * FROM sys_flags WHERE field_no = 'object_nature' ) d ON a.object_nature = d.code\n" +
//                "\t   LEFT JOIN ( SELECT * FROM sys_flags WHERE field_no = 'is_yes' ) e ON a.is_leader = e.code\n" +
//                "        LEFT JOIN (SELECT station_id,station_name FROM dbo.sys_stations) s ON a.create_station_id=s.station_id\n" +
//                "       LEFT JOIN ( SELECT customer_id, SUM (vehicle_count) AS vehicle_count\n" +
//                "\t\t\t\t   FROM dbo.customer_retain_vehicle_overview\n" +
//                "\t\t\t\t   GROUP BY customer_id ) f ON a.object_id = f.customer_id\n" +
//                "       LEFT JOIN ( SELECT object_id, MAX (real_back_time) AS real_back_time\n" +
//                "\t\t\t\t   FROM dbo.presell_visitors_back\n" +
//                "\t\t\t\t   GROUP BY object_id ) g ON a.object_id = g.object_id\n" +
//                "       LEFT JOIN ( SELECT customer_id, MAX (sign_time) AS last_sign_time\n" +
//                "\t\t\t       FROM dbo.vehicle_sale_contracts\n" +
//                "\t\t\t       WHERE contract_status <> 3 AND contract_status <> 4\n" +
//                "\t\t\t       GROUP BY customer_id ) i ON a.object_id = i.customer_id\n" +
//                "\t   LEFT JOIN ( SELECT * FROM ( SELECT ROW_NUMBER () OVER ( PARTITION BY x.visitor_id ORDER BY x.order_time DESC,x.create_time DESC ) AS rowNumber,\n" +
//                "\t\t\t\t\t\t\t\t x.visitor_id, x.visit_result, x.intent_level,CASE WHEN y.meaning IS NOT NULL THEN y.meaning ELSE\n" +
//                "\t\t\t\t\t\t\t  CASE WHEN DATEDIFF(DAY,x.plan_purchase_time,GETDATE ()) > 0 \n" +
//                "\t\t\t\t\t\t\t  THEN '疑似战败' ELSE '意向' END END AS visit_result_meaning,purpose_quantity\n" +
//                "\t\t\t\t\t\t  FROM (SELECT CASE WHEN ISNULL(visit_result,'')='' THEN GETDATE() ELSE create_time END AS order_time ,* FROM presell_visitors) x\n" +
//                "\t\t\t\t\t\t  LEFT JOIN (SELECT code, meaning FROM dbo.sys_flags WHERE field_no = 'visit_result') y ON x.visit_result = y.code\n" +
//                "                           WHERE (ISNULL(x.visit_result,'')='' OR x.visit_result=10 OR x.visit_result=20)) t\n" +
//                "\t\t\t\t   WHERE t.rowNumber = 1 ) h ON a.object_id = h.visitor_id\n" +
//                "\t   WHERE ISNULL (a.status, 0) = 1 ) base_related_objects\n" +
//                "\t   WHERE 1 = 1  AND  ISNULL(maintainer_id, '') = '' AND ISNULL(customer_type, 50) <> 50\n" +
//                "\tAND ( object_type & 2 = 2 OR object_type = 0 )  AND   ( 1 = 1 ) ";

        String baseSql = getCustomerBaseSql() + " AND  ISNULL(maintainer_id, '') = '' AND ISNULL(customer_type, 50) <> 50\n" +
                "\tAND ( object_type & 2 = 2 OR object_type = 0 )  AND   ( 1 = 1 ) ";

        if (keyword != null && keyword.length() > 0) {
            baseSql += String.format(" AND ( base_related_objects.object_name LIKE '%%%s%%' OR base_related_objects.short_name LIKE '%%%s%%' OR base_related_objects.mobile LIKE '%%%s%%' ) ", keyword, keyword, keyword);
        }

        String filterCondition = baseDao.mapToFilterString(filter, "base_related_objects");
        String buildSql = baseSql + " AND " + filterCondition;
        String countSql = getMyCustomersCountSql(buildSql);


        List<Map<String, Object>> item1 = baseDao.getMapBySQL(buildSql + sortSql, null);

        List<Map<String, Object>> intentResult = item1.stream().skip((page - 1) * perPage).limit(perPage).collect(Collectors.toList());
        PageModel p2 = new PageModel(intentResult);
        p2.setPage(page);
        p2.setPerPage(perPage);
        p2.setTotalSize(item1.size());
        return p2;

    }


    /**
     * 意向客户-我的客户查询
     *
     * @param customFilter 自定义条件
     * @return
     */
    public PageModel getMyCustomers(String keyword, Map<String, Object> filter, Map<String, Object> customFilter, String sort, int perPage, int page) {
        String sortSql;
        if (sort != null && sort.length() > 0) {
            sortSql = " ORDER BY " + sort + " ";
        } else {
            sortSql = "";//按默认row_id排序
        }

        if (page < 1) {
            page = 1;
        }

        String buildSql = getMyCustomersBuildSql(keyword, filter, customFilter);

        Long time1 = System.currentTimeMillis();
        List<Map<String, Object>> item1 = baseDao.getMapBySQL(buildSql + sortSql, null);
        this.addSysLog("意向客户列表", String.format("耗时：%s ms  %s", (System.currentTimeMillis() - time1), "getMyCustomers接口-查数据库"));

        List<Map<String, Object>> intentResult = item1.stream().skip((page - 1) * perPage).limit(perPage).collect(Collectors.toList());
        PageModel p2 = new PageModel(intentResult);
        p2.setPage(page);
        p2.setPerPage(perPage);
        p2.setTotalSize(item1.size());
        this.addSysLog("意向客户列表", String.format("耗时：%s ms  %s", (System.currentTimeMillis() - time1), "getMyCustomers接口"));
        return p2;
    }

    private String getCustomerBaseSql() {
        //增加“疑似战败”
        //增加u.department 建档部门
        //增加customer_linkman customer_mobile
        String baseSql = "SELECT a.*, g.real_back_time, ISNULL (g.real_back_time, '1970-01-01') AS real_back_time1,\n" +
                "\t   ISNULL (h.visit_result_meaning, '潜在') AS visit_result_meaning, h.intent_level,\n" +
                "\t   CASE WHEN ISNULL (h.visit_result_meaning, '潜在') = '潜在' THEN 10\n" +
                "\t   WHEN ISNULL (h.visit_result_meaning, '潜在') = '意向' THEN 20\n" +
                "\t   WHEN ISNULL (h.visit_result_meaning, '潜在') = '成交' THEN 30\n" +
                "\t   WHEN ISNULL (h.visit_result_meaning, '潜在') = '战败' THEN 40\n" +
                "\t   WHEN ISNULL (h.visit_result_meaning, '潜在') = '疑似战败' THEN 50\n" +
                "\t   ELSE 0 END AS visit_result_meaning1,\n" +
                "\t   CHARINDEX (ISNULL (h.intent_level, '空'), 'H,A,B,C,N,失效,成交,战败,空') sort,\n" +
                "\t   CHARINDEX (ISNULL (h.visit_result_meaning, '潜在'), '意向,成交,疑似战败,战败,终止,潜在') sort1, h.purpose_quantity,h.backer,\n" +
                "       ISNULL(icl.linkman,a.linkman) AS customer_linkman,ISNULL(icl.mobile,a.mobile) AS customer_mobile\n" +
                "INTO #vw_interested_customers\n" +
                "FROM dbo.vw_interested_customers a WITH ( NOLOCK )\n" +
                "LEFT JOIN ( SELECT object_id, MAX (real_back_time) AS real_back_time\n" +
                "\t\t\tFROM dbo.presell_visitors_back WITH ( NOLOCK )\n" +
                "\t\t\tGROUP BY object_id ) g ON a.object_id = g.object_id\n" +
                "LEFT JOIN ( SELECT *\n" +
                "\t\t\tFROM ( SELECT ROW_NUMBER () OVER ( PARTITION BY object_id ORDER BY maintainer_id DESC ) AS row_id, *\n" +
                "\t\t\t\t   FROM dbo.interested_customer_linkman \n" +
                "\t\t\t\t   WHERE (ISNULL(maintainer_id,'')='' OR maintainer_id ='[userId]') \n" +
                "                        AND station_id='[defaultStationId]' ) a\n" +
                "\t\t\tWHERE a.row_id = 1 ) icl ON icl.object_id = a.object_id\n" +
                "LEFT JOIN ( SELECT *\n" +
                "\t\t\tFROM ( SELECT ROW_NUMBER () OVER ( PARTITION BY x.visitor_id\n" +
                "\t\t\t\t\t\t\t\t\t\t\t   ORDER BY x.order_time DESC, x.create_time DESC ) AS rowNumber,\n" +
                "\t\t\t\t\t\t  x.visitor_id, x.visit_result, x.intent_level,x.creator AS backer,\n" +
                "\t\t\t\t\t\t  CASE WHEN y.meaning IS NOT NULL THEN y.meaning\n" +
                "\t\t\t\t\t\t  ELSE CASE WHEN DATEDIFF (DAY, x.plan_purchase_time, GETDATE ()) > 0 THEN '疑似战败'\n" +
                "\t\t\t\t\t\t\t   ELSE '意向' END END AS visit_result_meaning, purpose_quantity\n" +
                "\t\t\t\t   FROM ( SELECT CASE WHEN ISNULL (visit_result, '') = '' THEN GETDATE ()\n" +
                "\t\t\t\t\t\t\t\t ELSE create_time END AS order_time, *\n" +
                "\t\t\t\t\t\t  FROM presell_visitors  WITH ( NOLOCK )) x\n" +
                "\t\t\t\t   LEFT JOIN ( SELECT code, meaning FROM dbo.sys_flags WHERE field_no = 'visit_result' ) y ON x.visit_result = y.code\n" +
                "\t\t\t\t   WHERE ( ISNULL (x.visit_result, '') = ''\n" +
                "\t\t\t\t\t\t\t OR x.visit_result = 10\n" +
                "\t\t\t\t\t\t\t OR x.visit_result = 20 )) t\n" +
                "\t\t\tWHERE t.rowNumber = 1 ) h ON a.object_id = h.visitor_id\n" +
                "\n" +
                "SELECT 0 AS selected, ROW_NUMBER () OVER ( ORDER BY sort1 ASC, sort ASC, create_time DESC ) AS row_id, *\n" +
                "FROM ( SELECT a.*,  u.department,'' AS station_name, c.meaning AS customer_type_meaning,\n" +
                "\t\t\t  d.meaning AS object_nature_meaning, ISNULL (f.vehicle_count, 0) AS vehicle_count,\n" +
                "\t\t\t  ( a.province + '-' + a.city + '-' + a.area ) AS province_city_area, i.last_sign_time,i.contract_vehicle_count,i.contract_count,\n" +
                "\t\t\t  CASE WHEN ( SELECT TOP 1 COUNT (object_id)\n" +
                "\t\t\t\t\t\t  FROM finance_document_entries z\n" +
                "\t\t\t\t\t\t  WHERE z.object_id = a.object_id ) > 0 THEN 1\n" +
                "\t\t\t  ELSE 0 END AS is_used, s.station_name AS create_station_name, b.customer_group_name,\n" +
                "\t\t\t  e.meaning AS is_leader_meaning, su.unit_name AS create_unit_name,\n" +
                "              vos.vehicle_vno AS deliver_vehicle_vno,vos.out_stock_time AS deliver_time,\n" +
                "              DATEDIFF(DAY,vos.out_stock_time,GETDATE()) AS deliver_days\n" +
                "\t   FROM #vw_interested_customers a WITH ( NOLOCK )\n" +
                "\t   LEFT JOIN (select user_name+'('+user_no+')' as full_name,* from  sys_users) u ON a.creator = u.full_name \n" +
                "\t   LEFT JOIN base_customer_groups b ON a.customer_group_id = b.customer_group_id\n" +
                "\t   LEFT JOIN ( SELECT * FROM sys_flags WHERE field_no = 'visitor_type' ) c ON a.customer_type = c.code\n" +
                "\t   LEFT JOIN ( SELECT * FROM sys_flags WHERE field_no = 'object_nature' ) d ON a.object_nature = d.code\n" +
                "\t   LEFT JOIN ( SELECT * FROM sys_flags WHERE field_no = 'is_yes' ) e ON a.is_leader = e.code\n" +
                "\t   LEFT JOIN ( SELECT station_id, station_name FROM dbo.sys_stations ) s ON a.create_station_id = s.station_id\n" +
                "\t   LEFT JOIN ( SELECT unit_id, unit_no, unit_name FROM dbo.sys_units ) su ON a.create_unit_id = su.unit_id\n" +
                "\t   LEFT JOIN ( SELECT customer_id, SUM (vehicle_count) AS vehicle_count\n" +
                "\t\t\t\t   FROM dbo.customer_retain_vehicle_overview WITH ( NOLOCK )\n" +
                "\t\t\t\t   GROUP BY customer_id ) f ON a.object_id = f.customer_id\n" +
                "\t   LEFT JOIN ( SELECT customer_id, MAX (sign_time) AS last_sign_time,count(*) AS contract_vehicle_count,count(distinct(contracts.contract_no)) as contract_count\n" +
                "\t\t\t       FROM dbo.vehicle_sale_contracts contracts   WITH ( NOLOCK )\n" +
                "\t\t\t\t   LEFT JOIN dbo.vehicle_sale_contract_detail detail  WITH ( NOLOCK )\n" +
                "\t\t\t\t   ON detail.contract_no = contracts.contract_no\n" +
                "\t\t\t       WHERE contract_status <> 3 \n" +
                "\t\t\t       AND contract_status <> 4\n" +
                "\t\t\t       GROUP BY customer_id ) i ON a.object_id = i.customer_id\n" +
                "\t\t\t        LEFT JOIN ( SELECT *\n" +
                "\t\t\t\t   FROM ( SELECT ROW_NUMBER () OVER ( PARTITION BY b.customer_id ORDER BY b.create_time DESC ) row_id,\n" +
                "\t\t\t\t\t\t\t\t b.customer_id, a.vehicle_vno, b.out_stock_time\n" +
                "\t\t\t\t\t\t  FROM dbo.vehicle_out_stock_detail a\n" +
                "\t\t\t\t\t\t  LEFT JOIN dbo.vehicle_out_stocks b ON b.out_stock_no = a.out_stock_no\n" +
                "\t\t\t\t\t\t  WHERE b.approve_status = 1 AND b.out_stock_type=0\n" +
                "\t\t\t\t\t\t\t  AND ISNULL (a.error_flag, '0') = '0' ) a\n" +
                "\t\t\t\t   WHERE a.row_id = 1 ) vos ON a.object_id = vos.customer_id\n" +
                "\t   WHERE ISNULL (a.status, 0) = 1 ) base_related_objects\n" +
                "WHERE 1 = 1\n ";

        SysUsers user = HttpSessionStore.getSessionUser();
        baseSql = baseSql.replace("[userId]", user.getUserId()).replace("[defaultStationId]", user.getDefaulStationId());
        return baseSql;
    }

    private String getMyCustomersBuildSql(String keyword, Map<String, Object> filter, Map<String, Object> customFilter) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String baseSql = getCustomerBaseSql();
        if (keyword != null && keyword.length() > 0) {
            baseSql += String.format(" AND ( base_related_objects.object_name LIKE '%%%s%%' OR base_related_objects.short_name LIKE '%%%s%%' OR base_related_objects.mobile LIKE '%%%s%%' ) ", keyword, keyword, keyword);
        }

        String filterCondition = baseDao.mapToFilterString(filter, "base_related_objects");
        String customFilterCondition = getCustomFilterCondition(customFilter, "base_related_objects");

        String buildSql = baseSql + " AND " + filterCondition + customFilterCondition;
        String[] stationIds = userService.getModuleStationId(user, MODULE_EFFECTIVE_CUSTOMER);

        buildSql += String.format(" AND (station_id in ('%s') OR maintainer_id LIKE '%%%s%%')", StringUtils.join(stationIds, "','"), user.getUserId());

        if (!user.hasPopedom(POPEDOM_CROSS_DEPT)) {
            //优化，如果没有跨部门，加上条件
            buildSql += getMaintenanceUserCondition();
        }


        return buildSql;
    }

    private String getCustomFilterCondition(Map<String, Object> customFilter, String alias) {
        if (customFilter == null || customFilter.size() == 0) {
            return "";
        }

        String filter = "";

        //clue_create_time 线索日期
        String clueCondition = "";
        if (customFilter.get("clue_create_time_begin") != null) {
            clueCondition += " AND create_time>='" + customFilter.get("clue_create_time_begin") + "'";
        }

        if (customFilter.get("clue_create_time_end") != null) {
            clueCondition += " AND create_time<='" + customFilter.get("clue_create_time_end") + "'";
        }
        if (StringUtils.isNotEmpty(clueCondition)) {
            filter += String.format("  AND %s.object_id IN (SELECT visitor_id FROM presell_visitors where 1=1 %s) ", alias, clueCondition);
        }

        return filter;
    }


    private String getMyCustomersCountSql(String buildSql) {
        String countSql = String.format("SELECT COUNT(1) FROM (%s) tmp", buildSql);
        return countSql;
    }

    //获得维系人的条件，
    private String getMaintenanceUserCondition() {
        return getMaintenanceUserCondition("maintainer_id", "base_related_objects", true);
    }


    //includeDisableUser：包含禁用的用户
    public String getMaintenanceUserCondition(String columnName, String alias, boolean includeDisableUser) {
        String condition = "";
        List<Map<String, Object>> list = this.getMaintenanceConditionUsers(includeDisableUser);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> item = list.get(i);
                if (i == list.size() - 1) {
                    condition += String.format(" %s.%s LIKE '%s' ", alias, columnName, "%" + item.get("user_id") + "%");
                } else {
                    //多一个OR
                    condition += String.format(" %s.%s LIKE '%s' OR ", alias, columnName, "%" + item.get("user_id") + "%");

                }
            }
            logger.debug(String.format("getMaintenanceUserCondition-sql:%s", StringUtils.isBlank(condition) ? "" : String.format("AND (%s)", condition)));
            return StringUtils.isBlank(condition) ? "" : String.format("AND (%s)", condition);
        } else {
            return "AND 1=1";
        }
    }


    /**
     * 和 getAllMaintenanceUser 区别是：查询所有的客户
     *
     * @param includeDisableUser 包含禁用的用户
     * @return
     */
    public List<Map<String, Object>> getMaintenanceConditionUsers(boolean includeDisableUser) {
        SysUsers user = HttpSessionStore.getSessionUser();
        if (user.hasPopedom(POPEDOM_CROSS_DEPT)) {
            //有跨部门查看权限
            return null;

        }

        if (user.hasPopedom(POPEDOM_DEPT)) {
            //有部门查看权限没有跨部门查看权限，只能看到本部门
            String sql = "SELECT a.user_id, a.user_name, a.user_no, a.name_pinyin, b.unit_id, b.unit_name, b.unit_no,\n" +
                    "\t\t\t c.station_id, c.station_name\n" +
                    "\t  FROM sys_users a\n" +
                    "\t  LEFT JOIN sys_units b ON a.department = b.unit_id\n" +
                    "\t  LEFT JOIN dbo.sys_stations c ON b.default_station = c.station_id\n" +
                    "\t  WHERE 1=1  [STATUS_CONDITION]\n" +
                    "            [CONDITION] AND b.default_station IN (:stationIds)\n";

            String statusCondition = "";
            if (!includeDisableUser) {
                statusCondition = " AND a.status > 0 ";
            }

            String condition = "AND a.department ='" + user.getDepartment() + "'";
            Map<String, Object> params = new HashMap<>(1);
            String[] stationIds = userService.getModuleStationId(user, MODULE_EFFECTIVE_CUSTOMER);
            params.put("stationIds", stationIds);
            List<Map<String, Object>> rtnData = baseDao.getMapBySQL(sql.replace("[STATUS_CONDITION]", statusCondition).replace("[CONDITION]", condition), params);
            return rtnData;
        } else {
            //没有部门查看权限也没有跨部门查看权限，只能看自己
            List<Map<String, Object>> rtnData = new ArrayList<Map<String, Object>>();
            Map<String, Object> users = new HashMap<String, Object>();
            users.put("user_id", user.getUserId());
            rtnData.add(users);
            return rtnData;
        }
    }

    /**
     * 查找所有 维系人角色用户
     *
     * @return
     */
    public List<Map<String, Object>> getAllMaintenanceUser(boolean includeDisableUser) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String sql = "SELECT DISTINCT a.*\n" +
                "FROM (SELECT a.user_id, a.user_name, a.user_no, a.name_pinyin, b.unit_id, b.unit_name, b.unit_no,\n" +
                "\t\t\t c.station_id, c.station_name\n" +
                "\t  FROM sys_users a\n" +
                "\t  LEFT JOIN sys_units b ON a.department = b.unit_id\n" +
                "\t  LEFT JOIN dbo.sys_stations c ON b.default_station = c.station_id\n" +
                "\t  WHERE 1=1  [STATUS_CONDITION]\n" +
                "            [CONDITION] AND b.default_station IN (:stationIds)\n" +
                ") AS a\n" +
                "LEFT JOIN sys_users_roles AS b ON a.user_id = b.user_id\n" +
                "LEFT JOIN sys_roles AS c ON b.role_id = c.role_id\n" +
                " WHERE (role_type LIKE '%,35%' OR role_type LIKE ',35%'OR role_type LIKE '35,%' OR role_type LIKE '%,35,%' OR role_type = '35')\n" +
                "ORDER BY a.station_name,a.unit_name,a.user_name ";

        String statusCondition = "";
        if (!includeDisableUser) {
            statusCondition = " AND a.status > 0 ";
        }

        String condition = "";
        if (user.hasPopedom(POPEDOM_CROSS_DEPT)) {
            //有跨部门查看权限
            condition = "";

        } else if (user.hasPopedom(POPEDOM_DEPT)) {
            //有部门查看权限没有跨部门查看权限，只能看到本部门
            condition = "AND a.department ='" + user.getDepartment() + "'";
        } else {
            //没有部门查看权限也没有跨部门查看权限，只能看自己
            condition = "AND a.user_id ='" + user.getUserId() + "'";
        }

        Map<String, Object> params = new HashMap<>(1);
        String[] stationIds = userService.getModuleStationId(user, MODULE_EFFECTIVE_CUSTOMER);
        params.put("stationIds", stationIds);
        List<Map<String, Object>> rtnData = baseDao.getMapBySQL(sql.replace("[STATUS_CONDITION]", statusCondition).replace("[CONDITION]", condition), params);
//        logger.debug(String.format("getAllMaintenanceUser-sql:%s,参数:%s", sql.replace("[CONDITION]", condition), params));
        return rtnData;
    }


    /**
     * 查询 客户群
     *
     * @param filter
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageModel getCustomerGroupList(Map<String, Object> filter, int pageNo, int pageSize) {
        String filterCondition = baseDao.mapToFilterString(filter, "a");

        String sql = CUSTOMER_GROUP_SQL + " AND " + filterCondition;
        Map<String, Object> params = new HashMap<>(1);
        PageModel result = baseDao.listInSql(sql, null, pageNo, pageSize, params);
        return result;
    }


    private static final String CUSTOMER_GROUP_SQL = "SELECT CASE WHEN ( a.df_vehicle_sum * 1.0 / ISNULL (NULLIF(a.vehicle_sum, 0), 1)) > 0.5 THEN '忠诚'\n" +
            "\t   WHEN ( a.df_vehicle_sum * 1.0 / ISNULL (NULLIF(a.vehicle_sum, 0), 1)) < 0.1\n" +
            "\t   THEN '' ELSE '竞争' END AS customer_groups_type, *\n" +
            "FROM ( SELECT a.*, ISNULL (b.vehicle_sum, 0) AS vehicle_sum, ISNULL (c.df_vehicle_sum, 0) AS df_vehicle_sum,\n" +
            "\t\t\t  ISNULL (d.vehicle_sum_year, 0) AS vehicle_sum_year,\n" +
            "\t\t\t  ISNULL (e.df_vehicle_sum_year, 0) AS df_vehicle_sum_year,\n" +
            "\t\t\t  ( a.province + '-' + a.city + '-' + a.area ) AS province_city_area\n" +
            "\t   FROM base_customer_groups a\n" +
            "\t   LEFT JOIN ( SELECT b.customer_group_id, SUM (a.vehicle_count) AS vehicle_sum\n" +
            "\t\t\t\t   FROM dbo.customer_retain_vehicle_overview a\n" +
            "\t\t\t\t   LEFT JOIN dbo.vw_interested_customers b ON a.customer_id = b.object_id\n" +
            "\t\t\t\t   GROUP BY b.customer_group_id ) b ON a.customer_group_id = b.customer_group_id\n" +
            "\t   LEFT JOIN ( SELECT b.customer_group_id, SUM (a.vehicle_count) AS df_vehicle_sum\n" +
            "\t\t\t\t   FROM dbo.customer_retain_vehicle_overview a\n" +
            "\t\t\t\t   LEFT JOIN dbo.vw_interested_customers b ON a.customer_id = b.object_id\n" +
            "\t\t\t\t   WHERE ISNULL (a.is_df_vehicle, 0) = 1\n" +
            "\t\t\t\t   GROUP BY b.customer_group_id ) c ON a.customer_group_id = c.customer_group_id\n" +
            "\t   LEFT JOIN ( SELECT b.customer_group_id, SUM (a.vehicle_count) AS vehicle_sum_year\n" +
            "\t\t\t\t   FROM dbo.customer_retain_vehicle_overview a\n" +
            "\t\t\t\t   LEFT JOIN dbo.vw_interested_customers b ON a.customer_id = b.object_id\n" +
            "\t\t\t\t   WHERE DATEDIFF (YEAR, GETDATE (), a.create_time) = 0\n" +
            "\t\t\t\t   GROUP BY b.customer_group_id ) d ON a.customer_group_id = d.customer_group_id\n" +
            "\t   LEFT JOIN ( SELECT b.customer_group_id, SUM (a.vehicle_count) AS df_vehicle_sum_year\n" +
            "\t\t\t\t   FROM dbo.customer_retain_vehicle_overview a\n" +
            "\t\t\t\t   LEFT JOIN dbo.vw_interested_customers b ON a.customer_id = b.object_id\n" +
            "\t\t\t\t   WHERE DATEDIFF (YEAR, GETDATE (), a.create_time) = 0\n" +
            "\t\t\t\t\t   AND ISNULL (a.is_df_vehicle, 0) = 1\n" +
            "\t\t\t\t   GROUP BY b.customer_group_id ) e ON a.customer_group_id = e.customer_group_id ) a\n" +
            "WHERE 1 = 1";

    /**
     * 查询客户群明细
     *
     * @param customerGroupId
     * @return
     */
    public Map<String, List<Map<String, Object>>> getCustomerGroupDetail(String customerGroupId) {
        if (StringUtils.isBlank(customerGroupId)) {
            throw new ServiceException("查询客户群明细出错：客户群Id为空");
        }
        String customerGroupSql = CUSTOMER_GROUP_SQL + " AND a.customer_group_id =:val";

        String customerSql = "SELECT * FROM interested_customers where customer_group_id = :val";
        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("val", customerGroupId);


        Map<String, List<Map<String, Object>>> rtnData = new HashMap<>(2);
        rtnData.put("base_customer_groups", baseDao.getMapBySQL(customerGroupSql, paramMap));
        rtnData.put("interested_customers", baseDao.getMapBySQL(customerSql, paramMap));
        return rtnData;
    }

    /**
     * 跟进检查（批量）
     *
     * @param jsonObject
     * @return
     */
    public Map<String, List<Object>> batchCheckVisitorsBack(JsonObject jsonObject) {
        SysUsers user = HttpSessionStore.getSessionUser();
        JsonArray ja_back = jsonObject.getAsJsonArray("presell_visitors_back");
        List<PresellVisitorsBack> backList = new ArrayList<>();

        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (ja_back == null || ja_back.isJsonNull()) {
            throw new ServiceException("跟进检查出错，参数presell_visitors_back为空");
        }
        for (JsonElement element : ja_back) {
            JsonObject jo_back = element.getAsJsonObject();
            PresellVisitorsBack back = baseDao.get(PresellVisitorsBack.class, jo_back.get("back_id").getAsString());
            if (back == null) {
                throw new ServiceException(String.format("跟进ID：%s未找到跟进信息", back.getBackId()));
            }
            InterestedCustomers customer = baseDao.get(InterestedCustomers.class, back.getObjectId());
            if (!jo_back.has("check_result")) {
                throw new ServiceException(String.format("线索检查出错，%s的检查结果为空", customer.getObjectName()));
            }
            back.setCheckResult(jo_back.get("check_result").getAsString());
            back.setCheckContent(jo_back.has("check_content") ? jo_back.get("check_content").getAsString() : null);
            back.setCheckerId(user.getUserId());
            back.setChecker(user.getUserFullName());
            back.setCheckTime(now);

            backList.add(back);
        }


        Map<String, List<Object>> rtnData = new HashMap<>();
        List<Object> presell_visitors_back = new ArrayList<>();
        for (PresellVisitorsBack back : backList) {
            Map<String, Object> paramMap = new HashMap<>(1);
            paramMap.put("val", back.getBackId());
            presell_visitors_back.addAll(baseDao.getMapBySQL("SELECT * FROM presell_visitors_back where back_id = :val", paramMap));
        }
        rtnData.put("presell_visitors_back", presell_visitors_back);

        return rtnData;
    }

    /**
     * 批量无效
     *
     * @param jsonObject
     * @return
     */
    public Map<String, List<Object>> batchInvalid(JsonObject jsonObject) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String invalidReason = GsonUtil.getAsString(jsonObject, "invalid_reason");
        if (StringUtils.isBlank(invalidReason)) {
            throw new ServiceException("将客户调整为无效时必须填写原因");
        }
        JsonArray ja_customer = jsonObject.getAsJsonArray("interested_customers");
        List<InterestedCustomers> customersList = parseInterestedCustomers(ja_customer, new ICustomerPaser() {
            @Override
            public void parseCustomer(InterestedCustomers customer, JsonObject jo_customer) {
                //do nothing
            }
        });
        Map<String, List<Object>> rtnData = new HashMap<>();
        List<Object> interested_customers = new ArrayList<>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        for (InterestedCustomers customer : customersList) {
            //无效：将客户的customer_type字段设置为50，并清除维系人员
            customer.setCustomerType((short) 50);
            customer.setMaintainerId(null);
            customer.setMaintainerName(null);

            customer.setModifier(user.getUserFullName());
            customer.setModifyTime(now);
            customer.setInvalidReason(invalidReason); //设置无效原因
            updateRelatedObject(customer);//更新客户档案

            //把客户 跟进中的意向线索终止掉
            this.terminationVisitor(customer);
            this.doSyncCustomerLinkman(customer, false);
            Map<String, Object> paramMap = new HashMap<>(1);
            paramMap.put("val", customer.getObjectId());
            interested_customers.addAll(baseDao.getMapBySQL("SELECT * FROM interested_customers where object_id = :val", paramMap));
        }
        rtnData.put("interested_customers", interested_customers);
        return rtnData;
    }

    /**
     * 有效
     *
     * @param jsonObject
     * @return
     */
    public Map<String, List<Object>> batchValid(JsonObject jsonObject) {
        SysUsers user = HttpSessionStore.getSessionUser();
        JsonArray ja_customer = jsonObject.getAsJsonArray("interested_customers");
        String newMaintainerId = jsonObject.get("new_maintainer_id").getAsString();
        List<InterestedCustomers> customersList = parseInterestedCustomers(ja_customer, new ICustomerPaser() {
            @Override
            public void parseCustomer(InterestedCustomers customer, JsonObject jo_customer) {
                //do nothing
            }
        });
        Map<String, List<Object>> rtnData = new HashMap<>();
        List<Object> interested_customers = new ArrayList<>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        for (InterestedCustomers customer : customersList) {
            //将客户的customer_type字段设置为30，同时可以设置维系人员
            customer.setCustomerType((short) 30);
            customer.setMaintainerId(newMaintainerId);
            customer.setMaintainerName(getMaintainerName(newMaintainerId));

            customer.setModifier(user.getUserFullName());
            customer.setModifyTime(now);
            updateRelatedObject(customer);//更新客户档案

            this.doSyncCustomerLinkman(customer, false);
            Map<String, Object> paramMap = new HashMap<>(1);
            paramMap.put("val", customer.getObjectId());
            interested_customers.addAll(baseDao.getMapBySQL("SELECT * FROM interested_customers where object_id = :val", paramMap));
        }
        rtnData.put("interested_customers", interested_customers);
        return rtnData;
    }

    /**
     * 批量领用
     *
     * @param jsonObject
     * @return
     */
    public Map<String, List<Object>> batchReceiveInterestedCustomers(JsonObject jsonObject) {
        SysUsers user = HttpSessionStore.getSessionUser();
        JsonArray ja_customer = jsonObject.getAsJsonArray("interested_customers");
        List<InterestedCustomers> customersList = parseInterestedCustomers(ja_customer, new ICustomerPaser() {
            @Override
            public void parseCustomer(InterestedCustomers customer, JsonObject jo_customer) {
                //do nothing
            }
        });
        Map<String, List<Object>> rtnData = new HashMap<>();
        List<Object> interested_customers = new ArrayList<>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        for (InterestedCustomers customer : customersList) {
            if (StringUtils.isNotBlank(customer.getMaintainerId())) {
                throw new ServiceException(String.format("领用失败:%s不是公海客户", customer.getObjectName()));
            }
            customer.setModifier(user.getUserFullName());
            customer.setModifyTime(now);
            customer.setMaintainerId(user.getUserId());
            customer.setMaintainerName(user.getUserName());
            updateRelatedObject(customer);//更新客户档案

            this.doSyncCustomerLinkman(customer, false);

            Map<String, Object> paramMap = new HashMap<>(1);
            paramMap.put("val", customer.getObjectId());
            interested_customers.addAll(baseDao.getMapBySQL("SELECT * FROM interested_customers where object_id = :val", paramMap));
        }
        rtnData.put("interested_customers", interested_customers);
        return rtnData;
    }


    /**
     * 批量更新维系人
     *
     * @param jsonObject
     * @return
     */
    public Map<String, List<Object>> batchUpdateMaintainer(JsonObject jsonObject) {
        SysUsers user = HttpSessionStore.getSessionUser();
        JsonArray ja_customer = jsonObject.getAsJsonArray("interested_customers");
        String newMaintainerId = jsonObject.get("new_maintainer_id").getAsString();
        List<InterestedCustomers> customersList = parseInterestedCustomers(ja_customer, new ICustomerPaser() {
            @Override
            public void parseCustomer(InterestedCustomers customer, JsonObject jo_customer) {
                //do nothing
            }
        });

        Map<String, List<Object>> rtnData = new HashMap<>();
        List<Object> interested_customers = new ArrayList<>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        for (InterestedCustomers customer : customersList) {
            customer.setModifier(user.getUserFullName());
            customer.setModifyTime(now);
            customer.setMaintainerId(newMaintainerId);
            customer.setMaintainerName(getMaintainerName(newMaintainerId));
            updateRelatedObject(customer);//更新客户档案
            if (StringUtils.isEmpty(customer.getMaintainerId())) {
                //如果维系人为空了,需要把客户跟进中的意向线索终止掉
                this.terminationVisitor(customer);
            }

            this.doSyncCustomerLinkman(customer, false);
            Map<String, Object> paramMap = new HashMap<>(1);
            paramMap.put("val", customer.getObjectId());
            interested_customers.addAll(baseDao.getMapBySQL("SELECT * FROM interested_customers where object_id = :val", paramMap));
        }
        rtnData.put("interested_customers", interested_customers);

        return rtnData;
    }

    public Map<String, List<Object>> abandonMaintenance(JsonObject jObject) {
        String abandonReason = GsonUtil.getAsString(jObject, "abandon_reason");
        if (StringUtils.isBlank(abandonReason)) {
            throw new ServiceException("放弃原因不能为空");
        }
        JsonArray jsonArray = jObject.getAsJsonArray("interested_customers");
        SysUsers user = HttpSessionStore.getSessionUser();
        Map<String, List<Object>> result = new HashMap<String, List<Object>>();
        List<Object> valueList = new ArrayList<Object>();
        Timestamp now = TimestampUitls.getTime();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            String customerId = GsonUtil.getAsString(jsonObject, "object_id");
            if (StringUtils.isEmpty(customerId)) {
                throw new ServiceException("必须提供客户标识");
            }
            InterestedCustomers interestedCustomer = baseDao.get(InterestedCustomers.class, customerId);
            if (null == interestedCustomer) {
                throw new ServiceException(String.format("未找到意向客户(%s)", customerId));
            }
            BaseRelatedObjects baseRelatedObjects = null;
            if (StringUtils.isNotEmpty(interestedCustomer.getRelatedObjectId())) {
                baseRelatedObjects = baseDao.get(BaseRelatedObjects.class, interestedCustomer.getRelatedObjectId());
            }
            //维系人的取值以客户档案优先，视图：vw_interested_customers
            String maintainerIds = null;
            String maintainerNames = null;
            if (null != baseRelatedObjects) {
                maintainerIds = baseRelatedObjects.getMaintainerId();
                maintainerNames = baseRelatedObjects.getMaintainerName();
            } else {
                maintainerIds = interestedCustomer.getMaintainerId();
                maintainerNames = interestedCustomer.getMaintainerName();
            }

            Timestamp oriModifyTime = null;
            String oriModifyTimeStr = GsonUtil.getAsString(jsonObject, "modify_time");
            if (StringUtils.isNotEmpty(oriModifyTimeStr)) {
                oriModifyTime = Timestamp.valueOf(oriModifyTimeStr);
            }
            validateModifyTime(interestedCustomer.getModifyTime(), oriModifyTime);

            if (StringUtils.isEmpty(maintainerIds)) {
                continue;
            }
            String[] idArray = maintainerIds.split(",");
            String[] nameArray = maintainerNames.split(",");
            boolean syncAll = false;    //是否通过维系人ID同步更新维系人名称
            List<String> idList = new ArrayList<>(idArray.length);
            Collections.addAll(idList, idArray);
            if (idArray.length == nameArray.length) {
                if (idList.contains(user.getUserId())) {
                    int index = idList.indexOf(user.getUserId());
                    List<String> nameList = new ArrayList<>(nameArray.length);
                    Collections.addAll(nameList, nameArray);
                    String userName = nameList.get(index);
                    if (StringUtils.equals(user.getUserName(), userName)) {
                        nameList.remove(index);

                    } else {
                        syncAll = true;
                    }
                    idList.remove(index);
                    interestedCustomer.setMaintainerId(StringUtils.join(idList, ","));
                    if (!syncAll) {
                        interestedCustomer.setMaintainerName(StringUtils.join(nameList, ","));
                    }
                }
            } else {
                syncAll = true;
            }
            if (syncAll) {
                idList.remove("");
                if (idList.contains(user.getUserId())) {
                    idList.remove(user.getUserId());
                }
                List<String> list = new ArrayList<>(idList.size());
                for (String id : idList) {
                    SysUsers u = baseDao.get(SysUsers.class, id);
                    if (null != u) {
                        list.add(u.getUserName());
                    }
                }
                interestedCustomer.setMaintainerId(StringUtils.join(idList, ","));
                interestedCustomer.setMaintainerName(StringUtils.join(list, ","));
            }
            interestedCustomer.setModifyTime(now);
            interestedCustomer.setAbandonTime(now); //放弃维系，记录放弃维系日期
            interestedCustomer.setAbandonReason(abandonReason); //放弃维系，记录放弃维系原因
            //同步更新客户档案的维系人
            /*String objectId = interestedCustomer.getRelatedObjectId();
            BaseRelatedObjects object = null;
            if (StringUtils.isNotBlank(objectId)) {
                object = baseDao.get(BaseRelatedObjects.class, objectId);
            }
            if (null != object) {
                object.setMaintainerId(interestedCustomer.getMaintainerId());
                object.setMaintainerName(interestedCustomer.getMaintainerName());
                object.setModifyTime(now);
                customerHistoryService.WriteCustomerChangeHistory(object); //增加历史 caigx
            }*/
            if (null != baseRelatedObjects) {
                baseRelatedObjects.setMaintainerId(interestedCustomer.getMaintainerId());
                baseRelatedObjects.setMaintainerName(interestedCustomer.getMaintainerName());
                baseRelatedObjects.setModifyTime(now);
                customerHistoryService.WriteCustomerChangeHistory(baseRelatedObjects); //增加历史 caigx
            }
            //如果放弃维系后，客户维系人为空（公海客户），把线索变成战败状态
            if (StringUtils.isEmpty(interestedCustomer.getMaintainerId())) {
                this.terminationVisitor(interestedCustomer);
            }
            customerHistoryService.WriteCustomerChangeHistory(interestedCustomer);
            this.doSyncCustomerLinkman(interestedCustomer, false);
            valueList.add(BeanUtil.toNativeOfMap(interestedCustomer));
        }
        result.put("interested_customers", valueList);
        return result;
    }

    /**
     * 跟进中的意向线索终止掉
     *
     * @param interestedCustomer
     */
    private void terminationVisitor(InterestedCustomers interestedCustomer) {
        if (interestedCustomer == null) {
            return;
        }

        //ZYH需求：终止线索时，把跟进状态设置为战败（20），instead of 跟进状态设置为终止（50）--20190409
        List<PresellVisitors> visitors = (List<PresellVisitors>) baseDao.findByHql("FROM PresellVisitors WHERE visitorId = ? AND visitResult IS NULL", interestedCustomer.getObjectId());
        if (visitors != null && visitors.size() > 0) {
            for (PresellVisitors visitor : visitors) {
                visitor.setVisitResult((short) 20);
            }
        }
    }


    /**
     * 批量客户检查
     *
     * @param jsonObject
     * @return
     */
    public Map<String, List<Object>> batchCheckInterestedCustomers(JsonObject jsonObject) {
        SysUsers user = HttpSessionStore.getSessionUser();
        JsonArray ja_customer = jsonObject.getAsJsonArray("interested_customers");
        List<InterestedCustomers> customersList = parseInterestedCustomers(ja_customer, new ICustomerPaser() {
            @Override
            public void parseCustomer(InterestedCustomers customer, JsonObject jo_customer) {
                if (customer == null || jo_customer.isJsonNull()) {
                    return;
                }

                if (!jo_customer.has("check_result")) {
                    throw new ServiceException(String.format("客户检查出错，%s的检查结果为空", customer.getObjectName()));
                }
                customer.setCheckResult(jo_customer.get("check_result").getAsString());
                customer.setCheckContent(jo_customer.has("check_content") ? jo_customer.get("check_content").getAsString() : null);
            }
        });

        Map<String, List<Object>> rtnData = new HashMap<>();
        List<Object> interested_customers = new ArrayList<>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        for (InterestedCustomers customer : customersList) {
            customer.setModifier(user.getUserFullName());
            customer.setModifyTime(now);
            customer.setChecker(user.getUserFullName());
            customer.setCheckTime(now);
//            customer.setCheckResult("正常"); //检查结果客户端传入

            Map<String, Object> paramMap = new HashMap<>(1);
            paramMap.put("val", customer.getObjectId());
            interested_customers.addAll(baseDao.getMapBySQL("SELECT * FROM interested_customers where object_id = :val", paramMap));
        }
        rtnData.put("interested_customers", interested_customers);

        return rtnData;
    }


    private List<InterestedCustomers> parseInterestedCustomers(JsonArray ja_customer, ICustomerPaser paser) {
        List<InterestedCustomers> customersList = new ArrayList<>();
        if (ja_customer == null || ja_customer.size() == 0) {
            return customersList;
        }
        for (JsonElement element : ja_customer) {
            JsonObject jo_customer = element.getAsJsonObject();
            InterestedCustomers customer = baseDao.get(InterestedCustomers.class, jo_customer.get("object_id").getAsString());
            if (customer == null) {
                throw new ServiceException(String.format("根据客户ID：%s未找到数据", jo_customer.get("object_id").getAsString()));
            }
            Timestamp oriModifyTime = null;

            String oriModifyTimeStr = GsonUtil.getAsString(jo_customer, "modify_time");
            if (StringUtils.isNotBlank(oriModifyTimeStr)) {
                oriModifyTime = Timestamp.valueOf(oriModifyTimeStr);
            }
            validateModifyTime(customer.getModifyTime(), oriModifyTime);
            customersList.add(customer);

            paser.parseCustomer(customer, jo_customer);
        }
        return customersList;
    }

    /**
     * 获取maintainerName
     *
     * @param maintainerId
     * @return
     */
    private String getMaintainerName(String maintainerId) {
        if (StringUtils.isBlank(maintainerId)) {
            return null;
        }

        List<String> maintainerNameList = new ArrayList<>();
        String[] maintainerIdArray = StringUtils.split(maintainerId, ",");
        if (maintainerIdArray == null || maintainerIdArray.length == 0) {
            return null;
        }
        for (String item : maintainerIdArray) {
            SysUsers user = baseDao.get(SysUsers.class, item);
            if (user != null) {
                maintainerNameList.add(user.getUserName());
            }
        }

        return StringUtils.join(maintainerNameList, ",");
    }


    /**
     * 保存意向客户
     *
     * @param parameter
     * @return
     */
    public Map<String, List<Object>> saveInterestedCustomer(JsonObject parameter, String optionValue) {
        long time1 = System.currentTimeMillis();
        this.optionValue = optionValue; //saveInterestedCustomer 新增一个参数 optionValue，当optionValue为without_maintainer时，新增的意向客户没有维系人（公海客户）
        parameter = parseParameterBeforeSave(parameter);
        logger.debug("parseParameterBeforeSave(parameter) 完成");
        List<EntityProxy<?>> contractProxies = this.save(parameter);
        logger.debug("this.save(parameter) 完成");
        EntityProxy<InterestedCustomers> entityProxy = (EntityProxy<InterestedCustomers>) contractProxies.get(0);
        InterestedCustomers customers = entityProxy.getEntity();

        baseDao.flush();
        Map<String, List<Object>> result = convertReturnData(customers.getObjectId());
        long time2 = System.currentTimeMillis();
        this.addSysLog(entityProxy.getOperation() == Operation.CREATE ? "新建" : "修改", String.format("意向客户ID：%s，意向客户编码：%s,耗时：%s ms 报文：%s", customers.getObjectId(), customers.getObjectNo(), (time2 - time1), parameter.toString()));
        return result;
    }

    /**
     * 意向客户明细
     *
     * @param objectId
     * @return
     */
    public Map<String, List<Object>> getInterestedCustomerDetail(String objectId) {
        return convertReturnData(objectId);
    }


    /**
     * 保存意向客户前对它进行处理
     *
     * @param parameter
     * @return
     */
    private JsonObject parseParameterBeforeSave(JsonObject parameter) {
        //presell_visitors_back中 can_edit =0的我就把它删掉
        if (parameter == null || !parameter.has("presell_visitors_back") || parameter.get("presell_visitors_back").isJsonNull()) {
            return parameter;
        }
        JsonArray ja_back = parameter.getAsJsonArray("presell_visitors_back");
        if (ja_back.isJsonNull() || ja_back.size() == 0) {
            return parameter;
        }
        for (int i = ja_back.size() - 1; i >= 0; i--) {
            JsonElement element = ja_back.get(i);
            if (element.isJsonNull()) {
                continue;
            }
            JsonObject jo_back = element.getAsJsonObject();

            String canEditStr = GsonUtil.getAsString(jo_back, "can_edit");
            if ("0".equals(canEditStr)) {
                ja_back.remove(i);
            }
        }
        return parameter;
    }

    private void addSysLog(String logType, String description) {
        sysLogsDao.addSysLog(logType, "意向客户", description);
    }

    public Map<String, List<Object>> convertReturnData(String objectId) {
        logger.debug("convertReturnData 开始");
        Map<String, List<Object>> rtnData = new HashMap<>();
        String sql = getCustomerBaseSql() + " AND base_related_objects.object_id =:val";
//        String sql = "SELECT ROW_NUMBER() OVER(ORDER BY sort1 ASC,sort ASC,create_time DESC) AS row_id, *\n" +
//                "FROM ( SELECT a.*,u.department, dbo.fn_GetStationNames (a.station_id) AS station_name, c.meaning AS customer_type_meaning,\n" +
//                "\t\t\t  d.meaning AS object_nature_meaning,ISNULL(f.vehicle_count,0)AS vehicle_count,              \n" +
//                "\t\t\t  ( a.province + '-' + a.city + '-' + a.area ) AS province_city_area,i.last_sign_time,i.contract_vehicle_count,i.contract_count,\n" +
//                "\t\t\t  CASE WHEN ( SELECT TOP 1 COUNT (object_id)\n" +
//                "\t\t\t\t\t\t  FROM finance_document_entries z\n" +
//                "\t\t\t\t\t\t  WHERE z.object_id = a.object_id ) > 0\n" +
//                "\t\t\t\t   THEN 1 ELSE 0 END AS is_used,g.real_back_time, \n" +
//                "              ISNULL (h.visit_result_meaning, '潜在') AS visit_result_meaning, h.intent_level,\n" +
//                "              CASE WHEN ISNULL (h.visit_result_meaning, '潜在')='潜在' THEN 10\n" +
//                "\t\t\t  WHEN ISNULL (h.visit_result_meaning, '潜在')='意向' THEN 20\n" +
//                "\t\t\t  WHEN ISNULL (h.visit_result_meaning, '潜在')='成交' THEN 30\n" +
//                "\t\t\t  WHEN ISNULL (h.visit_result_meaning, '潜在')='战败' THEN 40\n" +
//                "\t\t\t  WHEN ISNULL (h.visit_result_meaning, '潜在')='疑似战败' THEN 50\n" +
//                "\t\t\t  ELSE 0 END AS visit_result_meaning1, s.station_name AS create_station_name,\n" +
//                "\t\t\t  CHARINDEX (ISNULL(h.intent_level,'空'), 'H,A,B,C,N,失效,成交,战败,空') sort,\n" +
//                "\t\t\t  CHARINDEX (ISNULL (h.visit_result_meaning, '潜在'), '意向,成交,疑似战败,战败,终止,潜在') sort1,\n" +
//                "\t\t\t  b.customer_group_name, e.meaning AS is_leader_meaning,h.purpose_quantity,\n" +
//                "\t\t\t  ( SELECT STUFF (( SELECT DISTINCT ',' + pv.vehicle_strain\n" +
//                "\t\t\t\t\t\t\t\tFROM dbo.presell_visitors AS pv\n" +
//                "\t\t\t\t\t\t\t\tWHERE a.object_id = pv.visitor_id\n" +
//                "\t\t\t\t\t\t\t\t\tAND ISNULL (pv.vehicle_strain, '') <> ''\n" +
//                "\t\t\t\t\t\t\t  FOR XML PATH ('')), 1, 1, '')) AS vehicle_strain\n" +
//                "\t   FROM dbo.vw_interested_customers a  WITH ( NOLOCK ) \n\n" +
//                "\t   LEFT JOIN (select user_name+'('+user_no+')' as full_name,* from  sys_users) u ON a.creator = u.full_name\n" +
//                "\t   LEFT JOIN base_customer_groups b ON a.customer_group_id = b.customer_group_id\n" +
//                "\t   LEFT JOIN ( SELECT * FROM sys_flags WHERE field_no = 'visitor_type' ) c ON a.customer_type = c.code\n" +
//                "\t   LEFT JOIN ( SELECT * FROM sys_flags WHERE field_no = 'object_nature' ) d ON a.object_nature = d.code\n" +
//                "\t   LEFT JOIN ( SELECT * FROM sys_flags WHERE field_no = 'is_yes' ) e ON a.is_leader = e.code\n" +
//                "        LEFT JOIN (SELECT station_id,station_name FROM dbo.sys_stations) s ON a.create_station_id=s.station_id\n" +
//                "       LEFT JOIN ( SELECT customer_id, SUM (vehicle_count) AS vehicle_count\n" +
//                "\t\t\t\t   FROM dbo.customer_retain_vehicle_overview\n" +
//                "\t\t\t\t   GROUP BY customer_id ) f ON a.object_id = f.customer_id\n" +
//                "       LEFT JOIN ( SELECT object_id, MAX (real_back_time) AS real_back_time\n" +
//                "\t\t\t\t   FROM dbo.presell_visitors_back\n" +
//                "\t\t\t\t   GROUP BY object_id ) g ON a.object_id = g.object_id\n" +
//                "       LEFT JOIN ( SELECT customer_id, MAX (sign_time) AS last_sign_time,count(*) AS contract_vehicle_count,count(distinct(contracts.contract_no)) as contract_count\n" +
//                "\t\t\t       FROM dbo.vehicle_sale_contracts contracts \n" +
//                "\t\t\t\t   LEFT JOIN dbo.vehicle_sale_contract_detail detail\n" +
//                "\t\t\t\t   ON detail.contract_no = contracts.contract_no\n" +
//                "\t\t\t       WHERE contract_status <> 3 AND contract_status <> 4\n" +
//                "\t\t\t       GROUP BY customer_id ) i ON a.object_id = i.customer_id\n" +
//                "\t   LEFT JOIN ( SELECT * FROM ( SELECT ROW_NUMBER () OVER ( PARTITION BY x.visitor_id ORDER BY x.order_time DESC,x.create_time DESC ) AS rowNumber,\n" +
//                "\t\t\t\t\t\t\t\t x.visitor_id, x.visit_result, x.intent_level,CASE WHEN y.meaning IS NOT NULL THEN y.meaning ELSE\n" +
//                "\t\t\t\t\t\t\t  CASE WHEN DATEDIFF(DAY,x.plan_purchase_time,GETDATE ()) > 0 \n" +
//                "\t\t\t\t\t\t\t  THEN '疑似战败' ELSE '意向' END END AS visit_result_meaning,purpose_quantity\n" +
//                "\t\t\t\t\t\t  FROM (SELECT CASE WHEN ISNULL(visit_result,'')='' THEN GETDATE() ELSE create_time END AS order_time ,* FROM presell_visitors) x\n" +
//                "\t\t\t\t\t\t  LEFT JOIN (SELECT code, meaning FROM dbo.sys_flags WHERE field_no = 'visit_result') y ON x.visit_result = y.code\n" +
//                "                           WHERE (ISNULL(x.visit_result,'')='' OR x.visit_result=10 OR x.visit_result=20)) t\n" +
//                "\t\t\t\t   WHERE t.rowNumber = 1 ) h ON a.object_id = h.visitor_id\n" +
//                "\t   WHERE ISNULL (a.status, 0) = 1 ) base_related_objects \n" +
//                "\t   WHERE base_related_objects.object_id =:val";
        convertReturnDataForDetail("interested_customers", sql, objectId, rtnData);
        rtnData.put("interested_customer_linkman", getInterestedCustomerLinkman(objectId));
        rtnData.put("presell_visitors", getPresellVisitors(objectId));
        rtnData.put("presell_visitors_back", getPresellVisitorsBack(objectId));
        logger.debug("convertReturnData 结束");
        return rtnData;
    }

    private List getPresellVisitors(String objectId) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String presell_visitors_sql = "SELECT *\n" +
                "FROM ( SELECT a.*, c.meaning AS buy_type_meaning, \n" +
                "              CASE WHEN d.meaning IS NOT NULL THEN d.meaning\n" +
                "\t\t\t  ELSE CASE WHEN DATEDIFF(DAY,a.plan_purchase_time,GETDATE())>0 \n" +
                "\t\t\t  THEN '疑似战败' ELSE '跟进中'END END AS visit_result_meaning,\n" +
                "\t\t\t  ISNULL (( SELECT COUNT (*)\n" +
                "\t\t\t\t\t\tFROM presell_visitors_back x\n" +
                "\t\t\t\t\t\tWHERE x.visitor_no = a.visitor_no\n" +
                "\t\t\t\t\t\t\tAND real_back_time IS NULL ), 0) no_back_count,\n" +
                "\t\t\t  CASE WHEN a.ways_point = NULL OR a.ways_point = ''\n" +
                "\t\t\t\t   THEN a.start_point + ',' + a.end_point\n" +
                "\t\t\t  ELSE a.start_point + ',' + a.ways_point + ',' + a.end_point\n" +
                "\t\t\t  END AS transportRoutes, e.station_name, b.maintainer_name, b.object_name,su.unit_name AS create_unit_name,\n" +
                "\t\t\t  f.buy_quantity,f.jiao_quantity,f.sign_time,f.real_deliver_time, g.meaning as vehicle_kind_meaning,\n" +
                "              (SELECT TOP 1 contract_no FROM dbo.vehicle_sale_contracts \n" +
                "               WHERE visitor_no LIKE '%'+a.visitor_no+'%' ORDER BY create_time DESC) AS contract_no,\n" +
                "              (SELECT TOP 1 contract_code FROM dbo.vehicle_sale_contracts \n" +
                "               WHERE visitor_no LIKE '%'+a.visitor_no+'%' ORDER BY create_time DESC) AS contract_code\n" +
                "\t   FROM presell_visitors a\n" +
                "\t   LEFT JOIN ( SELECT object_id, object_name, maintainer_name FROM dbo.vw_interested_customers ) b ON a.visitor_id = b.object_id\n" +
                "\t   LEFT JOIN ( SELECT code, meaning FROM sys_flags WHERE field_no = 'vs_buy_type' ) c ON a.buy_type = c.code\n" +
                "\t   LEFT JOIN ( SELECT code, meaning FROM sys_flags WHERE field_no = 'visit_result' ) d ON a.visit_result = d.code\n" +
                "\t   LEFT JOIN ( SELECT station_id, station_name FROM dbo.sys_stations ) e ON e.station_id = a.station_id\n" +
                "       LEFT JOIN (SELECT code, meaning FROM sys_flags WHERE field_no= 'vehicle_kind') g ON a.vehicle_kind = g.code  \n" +
                "       LEFT JOIN (SELECT unit_id,unit_no,unit_name FROM dbo.sys_units) su ON a.create_unit_id=su.unit_id\n" +
                "\t   LEFT JOIN ( SELECT a.visitor_no, SUM (vehicle_quantity) AS buy_quantity,\n" +
                "\t\t\t\t\t\t  MAX (b.sign_time) AS sign_time, MAX (a.real_deliver_time) AS real_deliver_time,\n" +
                "\t\t\t\t\t\t  SUM (CASE WHEN real_deliver_time IS NOT NULL THEN vehicle_quantity ELSE 0 END) AS jiao_quantity\n" +
                "\t\t\t\t   FROM dbo.vehicle_sale_contract_detail a\n" +
                "\t\t\t\t   LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                "                   WHERE a.approve_status<>30 AND b.contract_status<>3 AND b.contract_status<>4\n" +
                "\t\t\t\t   GROUP BY a.visitor_no ) f ON a.visitor_no = f.visitor_no ) a  \n" +
                "WHERE 1=1 AND a.visitor_id = :objectId  [CONDITION] ";
        String condition = "";
        //没有共同维系客户共享查看权限，只能看建单人是自己或销售员是自已和线索
        if (!user.hasPopedom(POPEDOM_SHARED_MAINTAINER)) {
            condition = " AND (a.creator = :userFullName OR a.seller_id=:userId )";
        } else {
            condition = " AND (a.station_id IN (:stationIds)  OR a.creator = :userFullName  OR a.seller_id=:userId)";
        }


        Map<String, Object> params = new HashMap<>(4);
        String[] stationIds = userService.getModuleStationId(user, MODULE_EFFECTIVE_CUSTOMER);
        params.put("stationIds", stationIds);
        params.put("userId", user.getUserId());
        params.put("userFullName", user.getUserFullName());
        params.put("objectId", objectId);

        List<Map<String, Object>> rtnData = baseDao.getMapBySQL(presell_visitors_sql.replace("[CONDITION]", condition), params);
        return rtnData;
    }

    private List getPresellVisitorsBack(String objectId) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String presell_visitors_back_sql = "SELECT 1 AS can_edit, a.*, b.meaning AS is_continue_back_meaning,--继续跟进\n" +
                "\t   CASE WHEN a.real_back_time IS NULL THEN '待跟进' ELSE '已跟进' END AS back_status,--跟进状态\n" +
                "\t   CASE WHEN ISNULL (check_result, '') = '' THEN '待检查' ELSE '已检查' END AS check_status --检查状态\n" +
                "FROM dbo.presell_visitors_back a  WITH ( NOLOCK )\n\n" +
                "LEFT JOIN ( SELECT * FROM dbo.sys_flags WHERE field_no = 'is_yes' ) b ON a.is_continue_back = b.code\n" +
                "where a.object_id =:objectId [CONDITION]";

        String condition = "";
        //没有共同维系客户共享查看权限，只能看建单人是自己或销售员是自已和线索
        if (!user.hasPopedom(POPEDOM_SHARED_MAINTAINER)) {
            condition = " AND (a.creator = :userFullName OR a.backer=:userFullName )";
        } else {
            condition = " AND (a.station_id IN (:stationIds)  OR a.creator = :userFullName  OR a.backer=:userFullName)";
        }


        Map<String, Object> params = new HashMap<>(4);
        String[] stationIds = userService.getModuleStationId(user, MODULE_EFFECTIVE_CUSTOMER);
        params.put("stationIds", stationIds);
        params.put("userId", user.getUserId());
        params.put("userFullName", user.getUserFullName());
        params.put("objectId", objectId);

        List<Map<String, Object>> rtnData = baseDao.getMapBySQL(presell_visitors_back_sql.replace("[CONDITION]", condition), params);
        return rtnData;
    }


    private void convertReturnDataForDetail(String tableName, String sql, Object referenceVal, Map<String, List<Object>> rtnData) {
        List<Object> dataList = rtnData.get(tableName);
        if (dataList == null) {
            dataList = new ArrayList<>();
            rtnData.put(tableName, dataList);
        }
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("val", referenceVal);
        List<Object> data = baseDao.listInSql(sql, parmMap).getData();

        if (data == null || data.size() == 0) {
            return;
        }
        dataList.addAll(data);

        for (Object item : data) {
            Map itemMap = (Map) item;
            if ("interested_customers".equals(tableName)) {


                String customer_retain_vehicle_overview_sql = "SELECT a.*, b.meaning AS is_our_purchase_meaning, c.meaning AS is_df_vehicle_meaning,\n" +
                        "                YEAR(GETDATE())-a.vehicle_purchase_year+1 AS real_age\n" +
                        "            FROM customer_retain_vehicle_overview a  WITH ( NOLOCK )\n\n" +
                        "            LEFT JOIN ( SELECT * FROM dbo.sys_flags WHERE field_no = 'is_yes' ) b ON a.is_our_purchase = b.code\n" +
                        "            LEFT JOIN ( SELECT * FROM dbo.sys_flags WHERE field_no = 'is_yes' ) c ON a.is_df_vehicle = c.code\n" +
                        "WHERE a.customer_id =:val";


//                convertReturnDataForDetail("presell_visitors", presell_visitors_sql, itemMap.get("object_id"), rtnData);
//                convertReturnDataForDetail("presell_visitors_back", presell_visitors_back_sql, itemMap.get("object_id"), rtnData);
                convertReturnDataForDetail("customer_retain_vehicle_overview", customer_retain_vehicle_overview_sql, itemMap.get("object_id"), rtnData);
            }
        }
    }


    public List getInterestedCustomerLinkman(String objectId) {
        SysUsers user = HttpSessionStore.getSessionUser();

        String sql = "SELECT a.*,b.user_name AS maintainer_name,c.station_name FROM interested_customer_linkman a \n" +
                "LEFT JOIN dbo.sys_users b ON a.maintainer_id=b.user_id\n" +
                "LEFT JOIN sys_units un ON b.department = un.unit_id\n" +
                "LEFT JOIN dbo.sys_stations c ON c.station_id = a.station_id\n" +
                "WHERE 1=1 AND object_id = :objectId  [CONDITION]  AND (a.station_id IN (:stationIds)  OR  a.maintainer_id = :userId ) ";

        String condition = "";
        if (user.hasPopedom(POPEDOM_CROSS_DEPT)) {
            //有跨部门查看权限
            condition = "";

        } else if (user.hasPopedom(POPEDOM_DEPT)) {
            //有部门查看权限没有跨部门查看权限，只能看到本部门
            condition = "AND b.department ='" + user.getDepartment() + "'";
        } else {
            //没有部门查看权限也没有跨部门查看权限，只能看自己
            condition = "AND a.maintainer_id ='" + user.getUserId() + "'";
        }

        Map<String, Object> params = new HashMap<>(3);
        String[] stationIds = userService.getModuleStationId(user, MODULE_EFFECTIVE_CUSTOMER);
        params.put("stationIds", stationIds);
        params.put("userId", user.getUserId());
        params.put("objectId", objectId);
        List<Map<String, Object>> rtnData = baseDao.getMapBySQL(sql.replace("[CONDITION]", condition), params);


        return rtnData;
    }


    @Override
    public void beforeExecute(EntityProxy<InterestedCustomers> entityProxy) {
        SysUsers user = HttpSessionStore.getSessionUser();
        if (entityProxy.getOperation() == Operation.CREATE) {
            InterestedCustomers customers = entityProxy.getEntity();
            InterestedCustomers oriCustomers = entityProxy.getOriginalEntity();

            String stationId = user.getDefaulStationId();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            customers.setStationId(stationId);
            customers.setCreateUnitId(user.getLoginDepartmentId());
            customers.setCreator(user.getUserFullName());
            customers.setCreateTime(now);
            customers.setCreateStationId(stationId);
            customers.setModifier(user.getUserFullName());
            customers.setModifyTime(now);
            //为防止生成的用户编码重复，将生成objectNo的代码下移
            //customers.setObjectNo(saleCustomerService.getAutoNoOfObject()); //新增时强制设置用户编码
            if (customers.getStatus() == null) {
                customers.setStatus((short) 1);
            }
            customers.setClientType(HttpSessionStore.getSessionOs()); //记录新增时的客户端类型
            //设置默认维系人
            setDefaultMaintenace(customers);
        } else {
            InterestedCustomers customers = entityProxy.getEntity();
            InterestedCustomers oriCustomers = entityProxy.getOriginalEntity();
            customers.setRelatedObjectId(oriCustomers.getRelatedObjectId());
            validateModifyTime(customers.getModifyTime(), oriCustomers.getModifyTime()); //校验modifyTime

            customers.setModifier(user.getUserFullName());
            customers.setModifyTime(new Timestamp(System.currentTimeMillis()));
        }
        logger.debug("validateRecord开始");
        validateRecord(entityProxy);
        logger.debug("validateRecord结束");
    }


    @Override
    public void execute(EntityProxy<InterestedCustomers> entityProxy) {

        InterestedCustomers customers = entityProxy.getEntity();
        if (StringUtils.isNotBlank(customers.getObjectName())) {
            customers.setNamePinyin(GetChineseFirstChar.getFirstLetter(customers.getObjectName()));
        }

        if ((Tools.toInt(customers.getObjectType()) & 2) != 2) {
            customers.setObjectType(Tools.toInt(customers.getObjectType()) + 2);
        }
        if (entityProxy.getOperation() != Operation.DELETE) {
            if (entityProxy.getOperation() == Operation.CREATE) {

                if (StringUtils.equals("PC", HttpSessionStore.getSessionOs()) && StringUtils.isNotBlank(customers.getObjectNo())) {
                    //donothing
                    //按熊桔的要求：兼容往来对象生成意向客户的情况,客户编码存在，则不重新生成客户编码
                } else {
                    //为防止生成的用户编码重复，将生成objectNo的代码下移
                    customers.setObjectNo(saleCustomerService.getAutoNoOfObject()); //新增时强制设置用户编码
                }
            }
            //同步最近更近日期
            this.fillPlanBackTime(entityProxy);
            updateRelatedObject(customers);
        }

        this.syncCustomerLinkman(entityProxy);
    }

    //同步 联系人列表
    private void syncCustomerLinkman(EntityProxy<InterestedCustomers> entityProxy) {
        //如果是NONE的，跳过
        if (entityProxy.getOperation() == Operation.NONE) {
            return;
        }
        InterestedCustomers customers = entityProxy.getEntity();
        if (entityProxy.getOperation() == Operation.DELETE) {
            deleteCustomerLinkmaByObjectId(customers.getObjectId());
            return;
        }

        this.doSyncCustomerLinkman(customers, true);
    }


    //updateSelfMobile 如果是本人修改的话，更新本人的联系人信息
    private void doSyncCustomerLinkman(InterestedCustomers customers, boolean updateSelfLinkman) {
        String maintainerId = customers.getMaintainerId();
        String maintainerName = customers.getMaintainerName();

        //解决前面有“,”的情况
        if (StringUtils.isNotEmpty(maintainerId) && maintainerId.startsWith(",")) {
            maintainerId = StringUtils.removeStart(maintainerId, ",");
            customers.setMaintainerId(maintainerId);
        }

        if (StringUtils.isNotEmpty(maintainerName) && maintainerName.startsWith(",")) {
            maintainerName = StringUtils.removeStart(maintainerName, ",");
            customers.setMaintainerName(maintainerName);
        }

        if (StringUtils.isBlank(maintainerId)) {
            deleteCustomerLinkmaByObjectId(customers.getObjectId());
            return;
        }

        SysUsers user = HttpSessionStore.getSessionUser();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String[] maintainerIdArray = StringUtils.split(maintainerId, ",");
        List<InterestedCustomerLinkman> linkmanList = (List<InterestedCustomerLinkman>) baseDao.findByHql("FROM InterestedCustomerLinkman WHERE objectId = ?", customers.getObjectId());
        for (String item : maintainerIdArray) {
            List<InterestedCustomerLinkman> filterLinkmanList = linkmanList.stream().filter(linkman -> item.equals(linkman.getMaintainerId())).collect(Collectors.toList());
            if (filterLinkmanList == null || filterLinkmanList.size() == 0) {
                //新增
                SysUsers maintainerUser = baseDao.get(SysUsers.class, item);

                InterestedCustomerLinkman linkman = new InterestedCustomerLinkman();
                linkman.setSelfId(UUID.randomUUID().toString());
                linkman.setObjectId(customers.getObjectId());
                linkman.setStationId(maintainerUser == null ? null : maintainerUser.getDefaulStationId());
                linkman.setMaintainerId(item);
                linkman.setLinkman(customers.getLinkman());
                linkman.setMobile(customers.getMobile());
                linkman.setCreator(user.getUserFullName());
                linkman.setCreateTime(now);
                linkman.setModifyTime(now);

                baseDao.save(linkman);
            } else {
                //如果是本人修改
                if (updateSelfLinkman && item.equals(user.getUserId())) {
                    InterestedCustomerLinkman linkman = filterLinkmanList.get(0);
                    //如果联系人或电话更新了，同步更新InterestedCustomerLinkman
                    if (!(StringUtils.equals(linkman.getLinkman(), customers.getLinkman()) && StringUtils.equals(linkman.getMobile(), customers.getMobile()))) {
                        linkman.setLinkman(customers.getLinkman());
                        linkman.setMobile(customers.getMobile());
                        linkman.setModifyTime(now);

                        baseDao.update(linkman);
                    }
                }
            }
        }

        //删除linkmanList中多出来的数据
        for (InterestedCustomerLinkman linkman : linkmanList) {
            List<String> maintainerIdList = Arrays.stream(maintainerIdArray).filter(item -> item.equals(linkman.getMaintainerId())).collect(Collectors.toList());
            if (maintainerIdList == null || maintainerIdList.size() == 0) {
                baseDao.delete(linkman);
            }
        }

        //其他非维系人修改客户的联系人和电话信息：修改维系人为空的联系人信息
        if (updateSelfLinkman) {
            if (!ArrayUtils.contains(maintainerIdArray, user.getUserId())) {
                List<InterestedCustomerLinkman> specLinkmanList = (List<InterestedCustomerLinkman>) baseDao.findByHql("FROM InterestedCustomerLinkman WHERE objectId = ? AND ISNULL(maintainerId,'') = '' AND stationId = ?", customers.getObjectId(), user.getDefaulStationId());
                InterestedCustomerLinkman specLinkman = null;
                if (specLinkmanList != null && specLinkmanList.size() > 0) {
                    specLinkman = specLinkmanList.get(0);
                }
                if (specLinkman == null) {
                    specLinkman = new InterestedCustomerLinkman();
                    specLinkman.setSelfId(UUID.randomUUID().toString());
                    specLinkman.setCreator(user.getUserFullName());
                    specLinkman.setCreateTime(now);
                }
                specLinkman.setStationId(user.getDefaulStationId());
                specLinkman.setObjectId(customers.getObjectId());
                specLinkman.setLinkman(customers.getLinkman());
                specLinkman.setMobile(customers.getMobile());
                specLinkman.setMaintainerId(null);
                specLinkman.setModifyTime(now);
                baseDao.update(specLinkman);
            }
        }
    }

    private void deleteCustomerLinkmaByObjectId(String objectId) {
        Query query = baseDao.getCurrentSession().createQuery("DELETE InterestedCustomerLinkman WHERE objectId = ?");
        query.setParameter(0, objectId);
        query.executeUpdate();
    }

    //同步最近跟进近日期
    private void fillPlanBackTime(EntityProxy<InterestedCustomers> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        InterestedCustomers customers = entityProxy.getEntity();
        //同步最近跟进近日期规则得改下，先取待跟进（实际跟进日期为空）的最大计划跟进日期，如果没有待跟进的记录，就取最大计划跟进日期
        Timestamp planBackTime = null;
        Timestamp planBackTime1 = null; //待跟进（实际跟进日期为空）的最大计划跟进日期
        List<EntityProxy<PresellVisitorsBack>> backProxyList = entityProxy.getSlaves(PresellVisitorsBack.class.getSimpleName());
        if (backProxyList != null && backProxyList.size() > 0) {
            for (EntityProxy<PresellVisitorsBack> backProxy : backProxyList) {
                if (backProxy.getOperation() == Operation.DELETE) {
                    return;
                }
                PresellVisitorsBack back = backProxy.getEntity();
                if (customers != null && back != null && back.getPlanBackTime() != null) {
                    if (planBackTime == null || planBackTime.getTime() < back.getPlanBackTime().getTime()) {
                        planBackTime = back.getPlanBackTime();
                    }

                    if (back.getRealBackTime() == null) {
                        if (planBackTime1 == null || planBackTime1.getTime() < back.getPlanBackTime().getTime()) {
                            planBackTime1 = back.getPlanBackTime();
                        }
                    }
                }
            }
        }

        customers.setPlanBackTime(planBackTime1 != null ? planBackTime1 : planBackTime);
    }

    public void updateRelatedObject(InterestedCustomers customers) {
        updateRelatedObject(customers, null);
    }

    public void updateRelatedObject(InterestedCustomers customers, List<String> targetFields) {
        if (StringUtils.isBlank(customers.getRelatedObjectId())) {
            //客户没有生成客户档案时，也记录下客户修改历史 20181109
            if (StringUtils.isNotEmpty(customers.getObjectId())) { //新增的意向客户不记录修改历史
                customerHistoryService.WriteCustomerChangeHistory(customers);
            }
            return;
        }
        BaseRelatedObjects relatedObjects = baseDao.get(BaseRelatedObjects.class, customers.getRelatedObjectId());
        if (relatedObjects == null) {
            return;
        }
        relatedObjects = copyInterestedCustomerIntoRelatedObject(customers, relatedObjects, targetFields);

        //写入历史
        customerHistoryService.WriteCustomerChangeHistory(relatedObjects);
    }


    private BaseRelatedObjects copyInterestedCustomerIntoRelatedObject(InterestedCustomers interestedCustomer, BaseRelatedObjects relatedObjects, List<String> targetFields) {
        if (interestedCustomer == null || relatedObjects == null) {
            return relatedObjects;
        }

        if (StringUtils.isEmpty(interestedCustomer.getShortName())) {
            //如果简称为空，默认为客户名称
            interestedCustomer.setShortName(interestedCustomer.getObjectName());
        }

        Field[] objectFields = BaseRelatedObjects.class.getDeclaredFields();
        Field[] customerFields = interestedCustomer.getClass().getDeclaredFields();
        Map<String, Field> fieldNames = new HashMap<>();
        for (Field field : customerFields) {
            fieldNames.put(field.getName(), field);
        }
        for (Field field : objectFields) {
            if (targetFields != null && !targetFields.isEmpty()) {
                if (!targetFields.contains(field.getName()))
                    continue;
            }
            if (fieldNames.containsKey(field.getName())) {
                Field field1 = fieldNames.get(field.getName());
                field1.setAccessible(true);
                Object value = null;
                try {
                    value = field1.get(interestedCustomer);

                } catch (Exception e) {
                    throw new ServiceException(String.format("同步客户档案出错： 获取%s.%s的值时出错", interestedCustomer.getClass().getSimpleName(), field.getName()));
                }
                try {
                    field.setAccessible(true);
                    field.set(relatedObjects, value);
                } catch (Exception e) {
                    throw new ServiceException(String.format("同步客户档案出错：设置%s.%s的值(%s)时出错", relatedObjects.getClass().getSimpleName(), field.getName(), value));
                }
            }
        }

        return relatedObjects;
    }

    /**
     * 添加重复客户检核
     *
     * @param entityProxy
     */
    private void addRepeatCheck(EntityProxy<InterestedCustomers> entityProxy, Map<String, Object> result) {
        logger.debug("addRepeatCheck开始");
        SysUsers user = HttpSessionStore.getSessionUser();
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        InterestedCustomers customer = entityProxy.getEntity();

        if (result == null || result.size() == 0) {
            return;
        }

        Session session = null;
        Transaction tx = null;
        try {
            session = baseDao.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query query = session.createSQLQuery("select * from presell_visitors_repeat_check  where customer_id = ? AND repeat_customer_name = ? AND customer_name = ?");
            query.setString(0, customer.getObjectId());
            query.setString(1, (String) result.get("object_name"));
            query.setString(2, customer.getObjectName());
            List checkList = query.list();

            if (checkList == null || checkList.size() == 0) {
                PresellVisitorsRepeatCheck check = new PresellVisitorsRepeatCheck();
                check.setSelfId(UUID.randomUUID().toString());
                check.setCustomerName(customer.getObjectName());
                check.setCustomerMobile(customer.getMobile());
                check.setRepeatCustomerName((String) result.get("object_name"));
                check.setRepeatCustomerMobile((String) result.get("mobile"));
                check.setCreator(user.getUserFullName());
                check.setCreateTime(new Timestamp(System.currentTimeMillis()));
                check.setCustomerId(customer.getObjectId());
                //单独事务执行
                session.save(check);

            }
            tx.commit();
        } catch (Exception e1) {
            logger.warn("addRepeatCheck保存出错：", e1);
            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception e2) {
                    logger.warn("addRepeatCheck中rollback出错：", e2);
                    // do nothing
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }

            logger.debug("addRepeatCheck结束");
        }
    }


    /**
     * 设置默认联系人
     *
     * @param customers
     */
    private void setDefaultMaintenace(InterestedCustomers customers) {
        //saveInterestedCustomer 新增一个参数 optionValue，当optionValue为without_maintainer时，新增的意向客户没有维系人（公海客户）
        if (WITHOUT_MAINTAINER.equals(this.optionValue)) {
            customers.setMaintainerId(null);
            customers.setMaintainerName(null);
            return;
        }

        SysUsers user = HttpSessionStore.getSessionUser();
        String sql = "SELECT  a.user_name FROM sys_users a\n" +
                "                LEFT JOIN sys_users_roles AS b ON a.user_id = b.user_id\n" +
                "                LEFT JOIN sys_roles AS c ON b.role_id = c.role_id\n" +
                "                WHERE  role_type LIKE '%35%' AND a.user_id=:userId";
        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("userId", user.getUserId());
        List<Map<String, Object>> result = baseDao.getMapBySQL(sql, paramMap);
        if (result != null || result.size() > 0) {
            //如果当前用户有维系人角色，就将新建客户的维系人设为当前用户，如果没有，就将维系人设为空
            customers.setMaintainerId(user.getUserId());
            customers.setMaintainerName(user.getUserName());
        } else {
            customers.setMaintainerId(null);
            customers.setMaintainerName(null);
        }
    }

    private void validateRecord(EntityProxy<InterestedCustomers> entityProxy) {
        Operation operation = entityProxy.getOperation();
        if (entityProxy.getOperation() == Operation.DELETE || entityProxy.getOperation() == Operation.NONE) {
            return;
        }
        InterestedCustomers customers = entityProxy.getEntity();
        InterestedCustomers oriCustomers = entityProxy.getOriginalEntity();

        if (StringUtils.isEmpty(customers.getObjectName())) {
            throw new ServiceException("客户名称不能为空");
        }

        if (StringUtils.isBlank(customers.getShortName())) {
            customers.setShortName(customers.getObjectName());
        }

        //简版需求：客户类别CustomerKind要求必填，ObjectNature根据客户类别自动生成,customerKind存的是name
        if (StringUtils.isEmpty(customers.getCustomerKind())) {
            throw new ServiceException("客户类别不能为空");
        }
//        if ("个人客户".equals(customers.getCustomerKind())) {
//            customers.setObjectNature(OBJECT_NATURE_CUSTOMER);
//        } else {
//            customers.setObjectNature(OBJECT_NATURE_UNIT);
//        }

        //客户性质可以多选
        if (StringUtils.isNotEmpty(customers.getCustomerKind()) && customers.getCustomerKind().contains("个人客户")) {
            customers.setObjectNature(OBJECT_NATURE_CUSTOMER);
        } else {
            customers.setObjectNature(OBJECT_NATURE_UNIT);
        }

//        if (customers.getObjectNature() == null) {
//            throw new ServiceException("对象性质不能为空");
//        }

        //设置默认证件号码 ADM19010009
        fillDefaultCerNo(customers);

        if (StringUtils.isBlank(customers.getMobile())) {
            throw new ServiceException("移动电话不能为空");
        }

//        if (StringUtils.isBlank(customers.getVisitWay())) {
//            throw new ServiceException("客户信息中，是否到店不能为空");
//        }


        if (StringUtils.isNotBlank(customers.getCustomerKind())) {
            if (customers.getCustomerKind().contains("车辆二级") && customers.getCustomerKind().contains("车辆终端")) {
                throw new ServiceException("'车辆二级'和'车辆终端'不能同时选择");
            }

            if (customers.getCustomerKind().contains("车辆二级") && customers.getCustomerKind().contains("车辆转供")) {
                throw new ServiceException("'车辆二级'和'车辆转供'不能同时选择");
            }

            if (customers.getCustomerKind().contains("车辆二级") && customers.getCustomerKind().contains("挂靠公司")) {
                throw new ServiceException("'车辆二级'和'挂靠公司'不能同时选择");
            }

            if (customers.getCustomerKind().contains("车辆终端") && customers.getCustomerKind().contains("车辆转供")) {
                throw new ServiceException("'车辆终端'和'车辆转供'不能同时选择");
            }

            if (customers.getCustomerKind().contains("车辆终端") && customers.getCustomerKind().contains("挂靠公司")) {
                throw new ServiceException("'车辆终端'和'挂靠公司'不能同时选择");
            }

            if (customers.getCustomerKind().contains("车辆转供") && customers.getCustomerKind().contains("挂靠公司")) {
                throw new ServiceException("'车辆转供'和'挂靠公司'不能同时选择");
            }

            if (customers.getCustomerKind().contains("配件二级") && customers.getCustomerKind().contains("配件终端")) {
                throw new ServiceException("'配件二级'和'配件终端'不能同时选择");
            }
        }

        validateCustomer(entityProxy);
    }


    /**
     * 验证客户合法性
     *
     * @param entityProxy
     */
    private void validateCustomerOld(EntityProxy<InterestedCustomers> entityProxy) {
        Operation operation = entityProxy.getOperation();
        if (operation == Operation.DELETE) {
            return;
        }
        InterestedCustomers customers = entityProxy.getEntity();
        InterestedCustomers oriCustomers = entityProxy.getOriginalEntity();

        short nObjectNature = Tools.toShort(customers.getObjectNature(), (short) 10);
        if (operation == Operation.UPDATE) {
            //如果对象名称、手机号码、证件号码、单位性质、简称都没变化时，不再重复性检查，避免用户补录其他信息时老是提示。
            if (StringUtils.equals(customers.getObjectName(), oriCustomers.getObjectName()) && StringUtils.equals(customers.getMobile(), oriCustomers.getMobile())
                    && nObjectNature == oriCustomers.getObjectNature() && StringUtils.equals(customers.getShortName(), oriCustomers.getShortName())) {
                return;
            }
        }

        //如果为单位，对象名称不允许重复
        if (nObjectNature == OBJECT_NATURE_UNIT) {
            //单位客户。客户名称不允许重复，电话号码可以重复
            String sql = "SELECT object_id,object_name,mobile,certificate_no,short_name,creator,\n" +
                    "a.maintainer_name AS workgroup_name\n" +
                    "FROM vw_stat_effective_customer a WHERE (a.object_name=:objectName \n" +
                    " OR (a.certificate_no IS NOT NULL AND a.certificate_no<>'' AND a.certificate_no<>:DEFAULT_CER_NO_CUSTOMER AND a.certificate_no<>:DEFAULT_CER_NO_UNIT  AND a.certificate_no=:certificateNo))\n" +
                    " AND (a.object_id<>:objectId) AND isnull(a.status,0)=1  ORDER BY create_time desc";

            Map<String, Object> paramMap = new HashMap<>(5);
            paramMap.put("objectId", customers.getObjectId());
            paramMap.put("objectName", customers.getObjectName());
            paramMap.put("shortName", customers.getShortName());
            paramMap.put("certificateNo", customers.getCertificateNo() == null ? "" : customers.getCertificateNo());
            paramMap.put("DEFAULT_CER_NO_CUSTOMER", InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER);
            paramMap.put("DEFAULT_CER_NO_UNIT", InterestedCustomersService.DEFAULT_CER_NO_UNIT);
            paramMap.put("mobile", customers.getMobile());
            List<Map<String, Object>> result = baseDao.getMapBySQL(sql, paramMap);


            if (result != null && result.size() > 0) {
                String reason = "";
                Map<String, Object> reasonItem = null;
                for (Map<String, Object> item : result) {
                    if (StringUtils.equals(customers.getObjectName(), (String) item.get("object_name"))) {
                        reasonItem = item;
                        reason = "客户名称重复";
                        break;
                    }

                    if (StringUtils.isNotBlank(customers.getCertificateNo()) && StringUtils.equals(customers.getCertificateNo(), (String) item.get("certificate_no"))) {
                        if ("东贸版".equals(sysOptionsDao.getOptionForString(DV_VERSION))
                                && (DEFAULT_CER_NO_CUSTOMER.equals(customers.getCertificateNo()) || DEFAULT_CER_NO_UNIT.equals(customers.getCertificateNo()))) {
                            //do nothing
                        } else {
                            reasonItem = item;
                            reason = "证件号码重复";
                            break;
                        }
                    }
                }


                if (StringUtils.isNotEmpty(reason)) {
                    //添加重复客户检核
                    addRepeatCheck(entityProxy, reasonItem);
                    String errMsg = String.format("客户已存在(%s) 已存在客户名称:%s, 建档人:%s", reason, (String) reasonItem.get("object_name"), (String) reasonItem.get("creator"))
                            + (StringUtils.isEmpty((String) reasonItem.get("workgroup_name")) ? "" : String.format(", 维系人:%s", reasonItem.get("workgroup_name")));
                    throw new ServiceException(errMsg);
                }
            }
        } else {
            //个人客户。客户名称允许重复，电话号码不允许重复
            String sql = "SELECT object_id,object_name,object_nature,mobile,certificate_no,short_name,creator,\n" +
                    "                        a.maintainer_name AS workgroup_name \n" +
                    "                    FROM vw_stat_effective_customer a WHERE (a.object_name=:objectName OR a.mobile=:mobile OR (a.certificate_no IS NOT NULL \n" +
                    "                   AND a.certificate_no<>:DEFAULT_CER_NO_CUSTOMER AND a.certificate_no<>:DEFAULT_CER_NO_UNIT AND a.certificate_no<>'' AND a.certificate_no=:certificateNo)) AND (a.object_id<>:objectId) AND isnull(a.status,0)=1 ORDER BY create_time desc";
            Map<String, Object> paramMap = new HashMap<>(5);
            paramMap.put("objectId", customers.getObjectId());
            paramMap.put("objectName", customers.getObjectName());
            paramMap.put("shortName", customers.getShortName());
            paramMap.put("certificateNo", customers.getCertificateNo() == null ? "" : customers.getCertificateNo());
            paramMap.put("DEFAULT_CER_NO_CUSTOMER", InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER);
            paramMap.put("DEFAULT_CER_NO_UNIT", InterestedCustomersService.DEFAULT_CER_NO_UNIT);
            paramMap.put("mobile", customers.getMobile());
            List<Map<String, Object>> result = baseDao.getMapBySQL(sql, paramMap);


            if (result != null && result.size() > 0) {
                String reason = "";
                Map<String, Object> reasonItem = null;
                for (Map<String, Object> item : result) {
                    if (StringUtils.isNotBlank(customers.getCertificateNo()) && StringUtils.equals(customers.getCertificateNo(), (String) item.get("certificate_no"))) {
                        if ("东贸版".equals(sysOptionsDao.getOptionForString(DV_VERSION))
                                && (DEFAULT_CER_NO_CUSTOMER.equals(customers.getCertificateNo()) || DEFAULT_CER_NO_UNIT.equals(customers.getCertificateNo()))) {
                            //do nothing
                        } else {
                            reasonItem = item;
                            reason = "证件号码重复";
                            break;
                        }
                    }

                    short objectNature = Tools.toShort((Number) item.get("object_nature"), (short) 10);
                    //个人客户和单位客户的名称不能重复（但是个人客户和个人客户的名称可以重复）
                    if (objectNature == 10 && StringUtils.equals(customers.getObjectName(), (String) item.get("object_name"))) {
                        reasonItem = item;
                        reason = "客户名称重复";
                        break;
                    }

                    //个人客户之间的移动电话不能重复
                    if (objectNature == 20 && StringUtils.equals(customers.getMobile(), (String) item.get("mobile"))) {
                        reasonItem = item;
                        reason = "移动电话重复";
                        break;
                    }
                }

                if (StringUtils.isNotEmpty(reason)) {
                    //添加重复客户检核
                    addRepeatCheck(entityProxy, reasonItem);
                    String errMsg = String.format("客户已存在(%s) 已存在客户名称:%s, 建档人:%s", reason, (String) reasonItem.get("object_name"), (String) reasonItem.get("creator"))
                            + (StringUtils.isEmpty((String) reasonItem.get("workgroup_name")) ? "" : String.format(", 维系人:%s", reasonItem.get("workgroup_name")));
                    throw new ServiceException(errMsg);
                }
            }
        }
    }

    private void validateCustomer(EntityProxy<InterestedCustomers> entityProxy) {
        Operation operation = entityProxy.getOperation();
        if (operation == Operation.DELETE) {
            return;
        }
        InterestedCustomers customers = entityProxy.getEntity();
        InterestedCustomers oriCustomers = entityProxy.getOriginalEntity();

        short nObjectNature = Tools.toShort(customers.getObjectNature(), (short) 10);
        if (operation == Operation.UPDATE) {
            //如果对象名称、手机号码、证件号码、单位性质、简称都没变化时，不再重复性检查，避免用户补录其他信息时老是提示。
            if (StringUtils.equals(customers.getObjectName(), oriCustomers.getObjectName()) && StringUtils.equals(customers.getMobile(), oriCustomers.getMobile())
                    && nObjectNature == oriCustomers.getObjectNature() && StringUtils.equals(customers.getShortName(), oriCustomers.getShortName())) {
                return;
            }
        }


        CustomerRepeatEntry repeatEntry = doValidateCustomerRepeat(customers.getObjectNature(), customers.getObjectId(), customers.getObjectName(), customers.getShortName(), customers.getMobile(), customers.getCertificateNo());
        String msg = repeatEntry.getMsg(true);
        if (StringUtils.isNotEmpty(msg)) {
            throw new ServiceException(msg);
        }
    }

    public Map<String, Object> validateCustomerRepeat(Map<String, Object> jsonData) {
        Short objectNature = Short.valueOf(jsonData.get("object_nature").toString());
        String objectId = (String) jsonData.get("object_id");
        String objectName = (String) jsonData.get("object_name");
        String shortName = (String) jsonData.get("short_name");
        String mobile = (String) jsonData.get("mobile");
        String certificateNo = (String) jsonData.get("certificate_no");

        CustomerRepeatEntry repeatEntry = doValidateCustomerRepeat(objectNature, objectId, objectName, shortName, mobile, certificateNo);
        if (repeatEntry.isEmpty()) {
            return null;
        }

        Map<String, Object> result = new HashMap<>(5);
        result.put("code", repeatEntry.getCode());
        result.put("needLoadCustomer", repeatEntry.getNeedLoadCustomer());
        result.put("objectId", repeatEntry.getObjectId());
        result.put("msg", repeatEntry.getMsg(false));

        if (StringUtils.isNotEmpty(repeatEntry.getObjectId())) {
            result.put("data", this.convertReturnData(repeatEntry.getObjectId()));
        }

        return result;
    }


    /**
     * 重复性校验
     */
    public CustomerRepeatEntry doValidateCustomerRepeat(Short objectNature, String objectId, String objectName, String shortName, String mobile, String certificateNo) {
        short nObjectNature = Tools.toShort(objectNature, (short) 10);

        CustomerRepeatEntry repeatEntry = new CustomerRepeatEntry();
        //如果为单位，对象名称不允许重复
        if (nObjectNature == OBJECT_NATURE_UNIT) {
            //单位客户。客户名称不允许重复，电话号码可以重复
            String sql = "SELECT object_id,object_name,mobile,certificate_no,short_name,creator,\n" +
                    "a.maintainer_name \n" +
                    "FROM vw_stat_effective_customer a WHERE (a.object_name=:objectName \n" +
                    " OR (a.short_name =:shortName)\n" +
                    " OR (a.certificate_no IS NOT NULL AND a.certificate_no<>'' AND a.certificate_no<>:DEFAULT_CER_NO_CUSTOMER AND a.certificate_no<>:DEFAULT_CER_NO_UNIT  AND a.certificate_no=:certificateNo))\n" +
                    " AND (a.object_id<>:objectId) AND isnull(a.status,0)=1  ORDER BY create_time desc";

            Map<String, Object> paramMap = new HashMap<>(5);
            paramMap.put("objectId", objectId);
            paramMap.put("objectName", objectName);
            paramMap.put("shortName", shortName);
            paramMap.put("certificateNo", StringUtils.isEmpty(certificateNo) ? "" : certificateNo);
            paramMap.put("DEFAULT_CER_NO_CUSTOMER", InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER);
            paramMap.put("DEFAULT_CER_NO_UNIT", InterestedCustomersService.DEFAULT_CER_NO_UNIT);
            paramMap.put("mobile", mobile);
            List<Map<String, Object>> result = baseDao.getMapBySQL(sql, paramMap);

            if (result != null && result.size() > 0) {
                for (Map<String, Object> item : result) {
                    if (StringUtils.equals(objectName, (String) item.get("object_name"))) {
                        repeatEntry.setCode(CustomerRepeatEntry.CODE_OBJECT_NAME);
                        repeatEntry.setReason("客户名称重复");
                        repeatEntry.setReasonItem(item);
                        break;
                    }

                    if (StringUtils.equals(shortName, (String) item.get("short_name"))) {
                        repeatEntry.setCode(CustomerRepeatEntry.CODE_SHORT_NAME);
                        repeatEntry.setReason("简称重复");
                        repeatEntry.setReasonItem(item);
                        break;
                    }

                    if (StringUtils.isNotBlank(certificateNo) && StringUtils.equals(certificateNo, (String) item.get("certificate_no"))) {
                        if ("东贸版".equals(sysOptionsDao.getOptionForString(DV_VERSION)) && (DEFAULT_CER_NO_CUSTOMER.equals(certificateNo) || DEFAULT_CER_NO_UNIT.equals(certificateNo))) {
                            //do nothing
                        } else {
                            repeatEntry.setCode(CustomerRepeatEntry.CODE_CER_NO);
                            repeatEntry.setReason("证件号码重复");
                            repeatEntry.setReasonItem(item);
                            break;
                        }
                    }
                }
            }
            if (null != repeatEntry.getCode()) {
                return repeatEntry;
            }

            //电话重复
            sql = "SELECT * FROM (SELECT object_id, object_name, object_nature, mobile, certificate_no, short_name, customer_type,\n" +
                    "\t   maintainer_name, maintainer_id, creator,status,create_time\n" +
                    "FROM vw_stat_effective_customer\n" +
                    "UNION ALL\n" +
                    "SELECT a.object_id, b.object_name, b.object_nature, a.mobile, b.certificate_no, b.short_name,\n" +
                    "\t   b.customer_type, b.maintainer_name, b.maintainer_id, b.creator,b.status,b.create_time\n" +
                    "FROM dbo.interested_customer_linkman a\n" +
                    "LEFT JOIN vw_stat_effective_customer b ON a.object_id = b.object_id) a  where a.mobile=:mobile AND (a.object_id<>:objectId) AND isnull(a.status,0)=1 ORDER BY create_time desc";
            result = baseDao.getMapBySQL(sql, paramMap);
            if (result != null && result.size() > 0) {
                repeatEntry.setCode(CustomerRepeatEntry.CODE_MOBILE);
                repeatEntry.setReason("移动电话重复");
                repeatEntry.setNeedLoadCustomer(false);
                repeatEntry.setReasonItem(result.get(0));
            }
        } else {
            //个人客户。客户名称允许重复，电话号码不允许重复
            String sql = "SELECT object_id,object_name,object_nature,mobile,certificate_no,short_name,creator,\n" +
                    "                        a.maintainer_name  \n" +
                    "                    FROM vw_stat_effective_customer a WHERE (a.object_name=:objectName OR (a.short_name =:shortName) OR a.mobile=:mobile OR (a.certificate_no IS NOT NULL \n" +
                    "                   AND a.certificate_no<>:DEFAULT_CER_NO_CUSTOMER AND a.certificate_no<>:DEFAULT_CER_NO_UNIT AND a.certificate_no<>'' AND a.certificate_no=:certificateNo)) AND (a.object_id<>:objectId) AND isnull(a.status,0)=1 ORDER BY create_time desc";
            Map<String, Object> paramMap = new HashMap<>(5);
            paramMap.put("objectId", objectId);
            paramMap.put("objectName", objectName);
            paramMap.put("shortName", shortName);
            paramMap.put("certificateNo", StringUtils.isEmpty(certificateNo) ? "" : certificateNo);
            paramMap.put("DEFAULT_CER_NO_CUSTOMER", InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER);
            paramMap.put("DEFAULT_CER_NO_UNIT", InterestedCustomersService.DEFAULT_CER_NO_UNIT);
            paramMap.put("mobile", mobile);
            List<Map<String, Object>> result = baseDao.getMapBySQL(sql, paramMap);


            if (result != null && result.size() > 0) {
                for (Map<String, Object> item : result) {
                    if (StringUtils.isNotBlank(certificateNo) && StringUtils.equals(certificateNo, (String) item.get("certificate_no"))) {
                        if ("东贸版".equals(sysOptionsDao.getOptionForString(DV_VERSION)) && (DEFAULT_CER_NO_CUSTOMER.equals(certificateNo) || DEFAULT_CER_NO_UNIT.equals(certificateNo))) {
                            //do nothing
                        } else {
                            repeatEntry.setReason("证件号码重复");
                            repeatEntry.setCode(CustomerRepeatEntry.CODE_CER_NO);
                            repeatEntry.setReasonItem(item);
                            break;
                        }
                    }

                    short itemObjectNature = Tools.toShort((Number) item.get("object_nature"), (short) 10);
                    //个人客户和单位客户的名称不能重复（但是个人客户和个人客户的名称可以重复）
                    if (itemObjectNature == 10 && StringUtils.equals(objectName, (String) item.get("object_name"))) {
                        repeatEntry.setCode(CustomerRepeatEntry.CODE_OBJECT_NAME);
                        repeatEntry.setReason("客户名称重复");
                        repeatEntry.setReasonItem(item);
                        break;
                    }

                    if (itemObjectNature == 10 && StringUtils.equals(shortName, (String) item.get("short_name"))) {
                        repeatEntry.setCode(CustomerRepeatEntry.CODE_SHORT_NAME);
                        repeatEntry.setReason("简称重复");
                        repeatEntry.setReasonItem(item);
                        break;
                    }
                }
            }
            if (null != repeatEntry.getCode()) {
                return repeatEntry;
            }
            //个人客户之间的移动电话不能重复
            sql = "SELECT * FROM (SELECT object_id, object_name, object_nature, mobile, certificate_no, short_name, customer_type,\n" +
                    "\t   maintainer_name, maintainer_id, creator,status,create_time\n" +
                    "FROM vw_stat_effective_customer\n" +
                    "UNION ALL\n" +
                    "SELECT a.object_id, b.object_name, b.object_nature, a.mobile, b.certificate_no, b.short_name,\n" +
                    "\t   b.customer_type, b.maintainer_name, b.maintainer_id, b.creator,b.status,b.create_time\n" +
                    "FROM dbo.interested_customer_linkman a\n" +
                    "LEFT JOIN vw_stat_effective_customer b ON a.object_id = b.object_id) a  where a.mobile=:mobile AND a.object_nature = 20 AND (a.object_id<>:objectId) AND isnull(a.status,0)=1 ORDER BY create_time desc";
            result = baseDao.getMapBySQL(sql, paramMap);
            if (result != null && result.size() > 0) {
                repeatEntry.setCode(CustomerRepeatEntry.CODE_MOBILE);
                repeatEntry.setReason("移动电话重复");
                repeatEntry.setReasonItem(result.get(0));
            }
        }

        return repeatEntry;
    }


    /**
     * 查找检查人
     *
     * @return
     */
    public Object getChecker() {
        SysUsers user = HttpSessionStore.getSessionUser();
        Map<String, Object> rtnMap = new HashMap<>(2);

        String sql = "SELECT a.user_id, a.department AS parent_id, a.user_no, a.user_name, b.unit_id AS department_id,\n" +
                "\t   b.unit_no AS department_no, b.unit_name AS department_name, c.unit_id, c.unit_no, c.unit_name,\n" +
                "\t   c.default_station, d.station_name\n" +
                "FROM sys_users a\n" +
                "LEFT JOIN sys_units b ON a.department = b.unit_id\n" +
                "LEFT JOIN sys_units c ON a.institution = c.unit_id\n" +
                "LEFT JOIN sys_stations d ON c.default_station = d.station_id\n" +
                "LEFT JOIN dbo.sys_users_roles e ON e.user_id = a.user_id\n" +
                "LEFT JOIN dbo.sys_roles_popedoms f ON e.role_id=f.role_id\n" +
                "WHERE a.status > 0 AND f.popedom_id='10151920'  \n" +
                "     AND c.default_station IN (:stationIds) [CONDITION]";

        //检查人增加权限控制
        //若无客户管理-部门查看权限和客户管理-跨部门查看权限，可选择范围是自己部门且有“跟进记录检查”中“检查”权限的用户；
        //若有客户管理-部门查看权限，没有客户管理-跨部门查看权限，可选择范围是同站点其他部门且有“跟进记录检查”中“检查”权限的用户；
        //若有客户管理-部门查看权限，有客户管理-跨部门查看权限，可选择范围是其他站点其他部门且有“跟进记录检查”中“检查”权限的用户；
        String condition = "";
        //getChecker中跨权限的筛选不对，暂时取消
//        if (user.hasPopedom(POPEDOM_CROSS_DEPT)) {
//            condition = "";
//        } else if (user.hasPopedom(POPEDOM_DEPT)) {
//            //若有客户管理-部门查看权限，没有客户管理-跨部门查看权限，可选择范围是同站点其他部门且有“跟进记录检查”中“检查”权限的用户；
//            condition = "AND c.default_station ='" + user.getDefaulStationId() + "'";
//        } else {
//            //若无客户管理-部门查看权限和客户管理-跨部门查看权限，可选择范围是自己部门且有“跟进记录检查”中“检查”权限的用户；
//            condition = "AND b.unit_id ='" + user.getDepartment() + "'";
//        }

        Map<String, Object> params = new HashMap<>(1);
        params.put("stationIds", userService.getModuleStationId(user, MODULE_EFFECTIVE_CUSTOMER));
        rtnMap.put("checker", baseDao.getMapBySQL(sql.replace("[CONDITION]", condition), params));


        //下一步检查的要赋默认值，默认值在【检查人设置】模块设置，数据表：base_setting_checker
        String sql2 = "SELECT a.user_id, a.department AS parent_id, a.user_no, a.user_name, b.unit_id AS department_id\n" +
                "\t, b.unit_no AS department_no, b.unit_name AS department_name, c.unit_id, c.unit_no, c.unit_name\n" +
                "\t, c.default_station, d.station_name\n" +
                "FROM sys_users a\n" +
                "\tLEFT JOIN sys_units b ON a.department = b.unit_id\n" +
                "\tLEFT JOIN sys_units c ON a.institution = c.unit_id\n" +
                "\tLEFT JOIN sys_stations d ON c.default_station = d.station_id\n" +
                "WHERE a.user_id IN (\n" +
                "\tSELECT checker_id\n" +
                "\tFROM base_setting_checker\n" +
                "\tWHERE station_id = :stationId\n" +
                "\t\tAND (ISNULL(bound_value, '') = ''\n" +
                "\t\t\tOR bound_value LIKE :department)\n" +
                ")";
        Map<String, Object> params2 = new HashMap<String, Object>(2);
        params2.put("stationId", user.getDefaulStationId());
        params2.put("department", "%" + user.getDepartment() + "%");

        rtnMap.put("defaultChecker", baseDao.getMapBySQL(sql2, params2));

        return rtnMap;
    }

    /**
     * 查询待跟进的线索
     *
     * @param objectId
     * @return
     */
    public Object getPendingVisitorsBack(String objectId) {
        String sql = "select * from presell_visitors_back  WHERE  object_id =:objectId AND real_back_time is null";
        Map<String, Object> params = new HashMap<>(1);
        params.put("objectId", objectId);
        List<Map<String, Object>> result = baseDao.getMapBySQL(sql, params);
        return result;
    }


//    private List<Map<String, Object>> getRepeatCustomers(InterestedCustomers customers) {
//        if (customers == null) {
//            return null;
//        }
//        String sql = "SELECT object_id,object_name,mobile,certificate_no,short_name,creator,\n" +
//                "                            (SELECT STUFF((SELECT ',' + bmw.user_name FROM base_related_object_maintenace AS cmw \n" +
//                "                             LEFT JOIN dbo.sys_users bmw ON cmw.user_id = bmw.user_id WHERE a.object_id = cmw.object_id \n" +
//                "                             AND cmw.user_id IS NOT NULL FOR XML PATH('')), 1, 1, '')) AS workgroup_name \n" +
//                "                      FROM vw_stat_effective_customer a WHERE (a.object_name=:objectName OR a.short_name=:shortName OR a.mobile=:mobile \n" +
//                "                      OR (a.certificate_no IS NOT NULL AND a.certificate_no<>'' AND a.certificate_no=:certificateNo))  \n" +
//                "                      AND (a.object_id<>:objectId) AND isnull(a.status,0)=1";
//        Map<String, Object> paramMap = new HashMap<>(5);
//        paramMap.put("objectId", customers.getObjectId());
//        paramMap.put("objectName", customers.getObjectName());
//        paramMap.put("shortName", customers.getShortName());
//        paramMap.put("certificateNo", customers.getCertificateNo() == null ? "" : customers.getCertificateNo());
//        paramMap.put("mobile", customers.getMobile());
//
//        return baseDao.getMapBySQL(sql, paramMap);
//    }


}
