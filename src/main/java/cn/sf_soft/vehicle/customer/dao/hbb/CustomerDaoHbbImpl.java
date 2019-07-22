package cn.sf_soft.vehicle.customer.dao.hbb;

import cn.sf_soft.basedata.model.BaseMaintenanceWorkgroups;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.user.service.UserService;
import cn.sf_soft.vehicle.customer.dao.CustomerDao;
import cn.sf_soft.vehicle.customer.model.*;
import cn.sf_soft.vehicle.customer.service.impl.InterestedCustomersService;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.sf_soft.vehicle.customer.service.impl.InterestedCustomersService.MODULE_EFFECTIVE_CUSTOMER;
import static cn.sf_soft.vehicle.customer.service.impl.InterestedCustomersService.POPEDOM_SHARED_MAINTAINER;

@Repository("customerDao")
public class CustomerDaoHbbImpl extends BaseDaoHibernateImpl implements
        CustomerDao {

    @Autowired
    private UserService userService;

    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerDaoHbbImpl.class);

    private String[] initSql(SysUsers user) {
        boolean m_bPCheck = user.hasPopedom("10201220"); // 跨部门检查
        boolean m_bPCheckDep = user.hasPopedom("10201210"); // 部门检查

        // boolean m_bPAdjust = user.hasPopedom("10201025");//调整
        // boolean m_bPBackCS = user.hasPopedom("10201030");//客服回访
        // boolean m_bPNote = user.hasPopedom("10201040"); //短信
        // boolean m_bModiCustomer = user.hasPopedom("00401010");//能否修改客户名称
        // boolean m_bPAdjustPlanTime = user.hasPopedom("10201027");//有没调整计划的权限
        // boolean m_bView = user.hasPopedom("10201005");
        // boolean m_bHasGroup = false;
        // boolean m_bAllowMultiClue =
        // "允许".equals(getOptionValue("VEHICLE_ALLOW_MULTI_CLUE"));//是否允许多线索同时跟进同一客户

        String m_sSqlMaster = getSessionFactory().getCurrentSession()
                .getNamedQuery("sqlMaster").getQueryString();
        String m_sSqlVisitors = getSessionFactory().getCurrentSession()
                .getNamedQuery("sqlVisitor").getQueryString();
        String m_sSqlSaleClueNo = "SELECT visitor_no FROM presell_visitors WHERE 1=1";

        // 拥有跨部门权限时，拥有所有查看权限
        if (m_bPCheck) {
            m_sSqlMaster = m_sSqlMaster.replace(":saleClueNoCondition", "");
            return new String[]{m_sSqlMaster, m_sSqlVisitors};
        }

        // 销售小组长
        List<String> lstSaleGroup = getWorkGroupIdLeadByUser(user.getUserId());
        boolean bIsSaleGroupLeader = lstSaleGroup != null
                && lstSaleGroup.size() > 0;

        // //维系小组长
        // List<String> lstMWGroup =
        // getMaintenanceWorkgroupIdLeadByUser(user.getUserId());
        // boolean bIsMWGroupLeader = lstMWGroup != null && lstMWGroup.size() >
        // 0;

        // 只能看到自己的线索
        String sWhereD = String.format(" AND (seller_id='%s') [VALUE]",
                user.getUserId());

        // 只能看到自己所在维系组的客户
        // String sWhereM =
        // String.format(" AND (object_id IN (SELECT object_id FROM dbo.customer_maintenance_workgroup a "
        // +
        // "INNER JOIN dbo.base_maintenance_workgroup_details b ON b.bmw_id = a.bmw_id "
        // +
        // "WHERE user_id='%s') [VALUE])", user.getUserId());

		/*String sWhereM = String
				.format(" AND (object_id IN (SELECT object_id FROM dbo.base_related_object_maintenace WHERE user_id='%s') [VALUE])",
						user.getUserId());*/
        String sWhereM = String
                .format(" AND maintenace_id like '%%%s%%' [VALUE])",
                        user.getUserId());

        // 没有跨部门检查，不是销售组长，不是维系组长，没有客服回访权限，没有部门检查权限,即是所有权限都没有
        if (!(m_bPCheck || bIsSaleGroupLeader /* || bIsMWGroupLeader */ || m_bPCheckDep)) {
            m_sSqlVisitors += sWhereD.replace("[VALUE]", "");
            m_sSqlMaster += sWhereM.replace("[VALUE]", "");

            m_sSqlSaleClueNo += sWhereD.replace("[VALUE]", "");
            m_sSqlMaster = m_sSqlMaster.replace(":saleClueNoCondition",
                    "AND visitor_no IN(" + m_sSqlSaleClueNo + ")");

            return new String[]{m_sSqlMaster, m_sSqlVisitors};
        }

        // 有销售组长权限时
        if (bIsSaleGroupLeader) {
            sWhereD = sWhereD
                    .replace(
                            "[VALUE]",
                            String.format(
                                    " OR (seller_id IN (SELECT user_id FROM base_sale_workgroup_detail WHERE sw_id IN ('%s'))) [VALUE] ",
                                    StringUtils.join(lstSaleGroup, "', '")));
            sWhereM = sWhereM
                    .replace(
                            "[VALUE]",
                            String.format(
                                    " OR object_id IN (SELECT visitor_id FROM dbo.presell_visitors WHERE seller_id IN (SELECT user_id FROM base_sale_workgroup_detail WHERE sw_id IN ('%s'))) [VALUE]",
                                    StringUtils.join(lstSaleGroup, "', '")));
        }

        // //有维系组长权限时
        // if (bIsMWGroupLeader)
        // {
        // sWhereD = sWhereD.replace("[VALUE]",
        // String.format(" OR (seller_id IN (SELECT user_id FROM base_maintenance_workgroup_details WHERE bmw_id IN ('%s'))) [VALUE] ",
        // StringUtils.join(lstMWGroup, "', '")));
        // sWhereM = sWhereM.replace("[VALUE]",
        // String.format(" OR object_id IN (SELECT visitor_id FROM dbo.presell_visitors WHERE seller_id IN (SELECT user_id FROM base_maintenance_workgroup_details WHERE bmw_id IN ('%s'))) [VALUE]",
        // StringUtils.join(lstMWGroup, "', '")));
        // }

        // 有部门检查权限时
        if (m_bPCheckDep) {
            // 找出当前部门下的销售员
            List<String> lstSellerId = getSellerIds(user.getUserId());
            sWhereD = sWhereD.replace("[VALUE]", String.format(
                    " OR (seller_id IN ('%s')) [VALUE]",
                    StringUtils.join(lstSellerId, "', '")));
            sWhereM = sWhereM
                    .replace(
                            "[VALUE]",
                            String.format(
                                    " OR object_id IN (SELECT visitor_id FROM dbo.presell_visitors WHERE seller_id IN ('%s'))",
                                    StringUtils.join(lstSellerId, "', '")));
        }

        m_sSqlVisitors += sWhereD.replace("[VALUE]", "");
        m_sSqlSaleClueNo += sWhereD.replace("[VALUE]", "");
        m_sSqlMaster += sWhereM.replace("[VALUE]", "");
        m_sSqlMaster = m_sSqlMaster.replace(":saleClueNoCondition",
                "AND visitor_no IN(" + m_sSqlSaleClueNo + ")");
        return new String[]{m_sSqlMaster, m_sSqlVisitors};
    }

    /**
     * 得到用户是组长的销售小组的ID
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<String> getWorkGroupIdLeadByUser(String userId) {
        return executeSQLQuery(String
                .format("SELECT sw_id FROM base_sale_workgroup_detail WHERE is_group_leader = 1 AND user_id = '%s'",
                        userId));
    }

    /***
     * 得到用户是组长的维系小组的ID
     *
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<String> getMaintenanceWorkgroupIdLeadByUser(String userId) {
        return executeSQLQuery(String
                .format("SELECT bmw_id FROM base_maintenance_workgroup_details WHERE is_group_leader = 1 AND user_id ='%s'",
                        userId));
    }

    /***
     * 获取用户部门下的所有人员的ID
     *
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<String> getSellerIds(String userId) {
        String sql = String
                .format("SELECT user_id FROM sys_users WHERE department IN (SELECT department FROM sys_users WHERE user_id='%s')",
                        userId);
        return executeSQLQuery(sql);
    }

    /**
     * 根据关键字查找用户所具有查看权限的客户信息
     *
     * @param user
     * @param keyword 客户名称、联系人名称、手机号码
     */
    public List<CustomerVO> getCustomerByKeyword(SysUsers user, String keyword) {
        String[] sqlMasterVisitors = initSql(user);
        String m_sSqlMaster = sqlMasterVisitors[0];
        String m_sSqlVisitors = sqlMasterVisitors[1];

        // 显示计划回访日期为当天或以前有未回访的(有效线索除外)
        String sWhere2 = " AND object_id IN ( "
                + "SELECT visitor_id FROM presell_visitors WHERE visit_result IS NULL AND visitor_no IN ("
                + "SELECT visitor_no FROM presell_visitors_back WHERE real_back_time is NULL AND DATEDIFF(DAY, plan_back_time, GETDATE()) >= 0 )) "
                + "AND object_id IN (SELECT visitor_id FROM (" + m_sSqlVisitors
                + ") t WHERE visit_result IS NULL)";

        String sql = m_sSqlMaster;

        Map<String, Object> params = null;
        if (keyword != null && keyword.trim().length() > 0) {
            sql += " AND (object_name LIKE :keyword OR mobile LIKE :keyword OR linkman LIKE :keyword)";
            params = new HashMap<String, Object>();
            params.put("keyword", keyword);

        } else {
            // 如果未指定搜索关键字，则默认加载计划回访日期为当天或以前有未回访的(有效线索除外)的意向客户
            sql += (" AND last_seller is not null " + sWhere2);
        }

        List<CustomerVO> results = getEntityBySQL(sql, CustomerVO.class, params);

        return results;
    }

    /***
     * 快捷方式获取意向客户
     *
     * @param user
     * @param shortcut
     *            今日活动:today; 明日活动:tomorrow; 当周活动:week; 当月活动:month; 所有意向:allYX;
     *            H、A、O、B
     * @return
     */
    public List<CustomerVO> getCustomerByShortcut(SysUsers user, String shortcut) {
        String[] sqlMasterVisitors = initSql(user);
        String m_sSqlMaster = sqlMasterVisitors[0];
        String m_sSqlVisitors = sqlMasterVisitors[1];
        String m_sCondition = " AND last_seller is not null AND object_id IN (SELECT visitor_id FROM ("
                + m_sSqlVisitors + ") t WHERE 1=1) ";

        String sql = "", sStr = "";
        // H、A、O、B
        if ("H".equalsIgnoreCase(shortcut) || "A".equalsIgnoreCase(shortcut)
                || "O".equalsIgnoreCase(shortcut)
                || "B".equalsIgnoreCase(shortcut)) {
            sStr = " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_level='"
                    + shortcut + "')";

            // 当日活动
        } else if ("today".equalsIgnoreCase(shortcut)) {
            sStr = " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())=0))";

            // 明日活动
        } else if ("tomorrow".equalsIgnoreCase(shortcut)) {
            sStr = " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(day,plan_back_time,getdate())=-1))";

            // 当周活动
        } else if ("week".equalsIgnoreCase(shortcut)) {
            sStr = " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(WEEK, GETDATE(), plan_back_time) = 0))";

            // 当月活动
        } else if ("month".equalsIgnoreCase(shortcut)) {
            sStr = " AND object_id IN ( SELECT visitor_id FROM presell_visitors WHERE visitor_no IN (SELECT visitor_no FROM presell_visitors_back WHERE DATEDIFF(MONTH, GETDATE(), plan_back_time) = 0))";

            // 所有意向
        } else if ("allYX".equalsIgnoreCase(shortcut)) {
            sStr = " AND object_id IN (SELECT visitor_id FROM dbo.presell_visitors WHERE visit_result IS NULL) ";

        } else {
            logger.warn(String.format("获取意向客户的快捷标识无效:%s", shortcut));
            return null;
        }

        sql = m_sSqlMaster + m_sCondition + sStr;

        return getEntityBySQL(sql, CustomerVO.class, null);
    }

    /**
     * 获取客户的销售线索
     *
     * @param user
     * @param customerId
     * @return
     */
    public List<PresellVisitorsVO> getSaleClue(SysUsers user, String customerId) {
        String[] sqlMasterVisitors = initSql(user);
        String m_sSqlVisitors = sqlMasterVisitors[1];

        String sql = m_sSqlVisitors
                + " AND visitor_id=:visitorId  ORDER BY presell_visitors.create_time DESC";
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put("visitorId", customerId);

        return getEntityBySQL(sql, PresellVisitorsVO.class, params);
    }

    /**
     * 获取销售线索的回访列表
     *
     * @param visitorNo
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<PresellVisitorsBack> getSaleClueBackVisitList(String visitorNo) {
        return (List<PresellVisitorsBack>) getHibernateTemplate().find(
                "FROM PresellVisitorsBack b WHERE b.visitorNo=?", visitorNo);
    }

    /**
     * 获取对于指定客户，当前销售员需要回访的销售回访计划
     *
     * @param customerId
     * @param sellerId
     * @return
     */
    public PresellVisitorsBack getTobeBackVisitPlan(String customerId,
                                                    String sellerId) {
        String sql = "SELECT * FROM presell_visitors_back b WHERE b.visitor_no=(SELECT TOP 1 v.visitor_no FROM  presell_visitors v WHERE v.visitor_id=:visitorId AND v.visit_result IS NULL AND v.seller_id=:sellerId) AND b.real_back_time IS NULL";
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("visitorId", customerId);
        params.put("sellerId", sellerId);
        List<PresellVisitorsBack> results = getEntityBySQL(sql,
                PresellVisitorsBack.class, params);
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    /**
     * 获取用户所在的维系小组
     *
     * @param user
     * @return
     */
    public List<BaseMaintenanceWorkgroups> getMaintenanceWorkgroupOfUser(
            SysUsers user) {
        String sql = "SELECT * FROM base_maintenance_workgroups g WHERE g.bmw_id IN(SELECT d.bmw_id FROM dbo.base_maintenance_workgroup_details d WHERE user_id=:userId AND station_id =:stationId)";
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("userId", user.getUserId());
        params.put("stationId", user.getDefaulStationId());
        return getEntityBySQL(sql, BaseMaintenanceWorkgroups.class, params);
    }

    /**
     * 获取车辆型号基础数据
     *
     * @param stationId
     * @param keyword   查询关键字，可为null或者车型、车名、销售代号
     * @return
     */
    public List<VehicleType> getVehicleType(String stationId, String keyword) {
        String sql = getSessionFactory().getCurrentSession()
                .getNamedQuery("selectVehicleType").getQueryString();
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("stationId", "%" + stationId + "%");
        if (keyword != null && keyword.trim().length() > 0) {
            sql += " AND (vehicle_no LIKE :keyeord OR vehicle_name LIKE :keyword OR vehicle_sales_code LIKE :keyword)";
            params.put("keyword", "%" + keyword + "%");
        }
        return getEntityBySQL(sql, VehicleType.class, params);
    }

    /**
     * 车辆品牌基础数据
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> getVehicleTrademark() {
        return executeSQLQuery("SELECT common_name FROM base_vehicle_name WHERE common_type='车辆品牌'");
        //return executeSQLQuery("SELECT vehicle_brand FROM vw_vehicle_type WHERE catalogs_type=10");
    }

    /**
     * 车辆品系基础数据
     *
     * @return
     */
    public List<VehicleType> getVehicleStrain() {
        return getByNamedQuery("selectVehicleStrain");
    }

    /**
     * 获取省市区基础数据
     *
     * @return
     */
    public List<ObjectOfPlace> getCitys() {
        return getByNamedQuery("selectCitys");
    }

    /**
     * 获取往来对象最大的编码
     *
     * @return
     */
    public String getMaxNoOfRelatedObject() {
        @SuppressWarnings("unchecked")
        Long max_a = 0L;
        Long max_b = 0L;

        SimpleDateFormat nodf = new SimpleDateFormat("yyyy-MM-dd");
        String strHead = nodf.format(new Date()).replaceAll("-", "");

        List<String> list = executeSQLQuery("SELECT  MAX(object_no) AS max_no FROM interested_customers WHERE object_no LIKE '" + strHead + "%'");
        if (list != null && list.size() > 0 && list.get(0) != null) {
            max_a = Long.valueOf(list.get(0));
        }
        list = executeSQLQuery("SELECT  MAX(object_no) AS max_no FROM  base_related_objects WHERE object_no LIKE '" + strHead + "%'");
        if (list != null && list.size() > 0 && list.get(0) != null) {
            max_b = Long.valueOf(list.get(0));
        }
        if (max_a == 0 && max_b == 0) {
            return null;
        }
        if (max_a > max_b) {
            return max_a.toString();
        } else {
            return max_b.toString();
        }
    }

	/*@Override
	public PageModel getCustomers(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
		if(sort == null || sort.length() == 0){
			sort = " ORDER BY last_visit_result DESC";
		}else{
			sort = " ORDER BY " + sort + " ";
		}
		if(page < 1){
            page = 1;
        }
        if(perPage < 1){
		    perPage = 100;
        }

		String[] sqls = initSql(user);
		String customerPoolCondition = " (base_tmp.object_id IN (SELECT customer_pool.object_id FROM ("+sqls[0]+") customer_pool )) ";
		String filterCondition = mapToFilterString(filter, "base_tmp");
        String keywordCondition;
		if( keyword != null && keyword.length() > 0) {
            keywordCondition = String.format(" ( object_name LIKE '%%%s%%' OR (object_id IN (select customer_id from customer_organizational_structure WHERE name LIKE '%%%s%%')) OR mobile LIKE '%%%s%%'  ) ", keyword, keyword, keyword);
        }else{
            keywordCondition = " ( 1 = 1 ) ";
        }
		String pageSql = String.format("  OFFSET %d ROWS FETCH NEXT %d ROWS ONLY ", (page-1)*perPage, perPage);

		String baseSql = "SELECT a.*, b.last_visit_result, b.visit_time,  b.visitor_level, b.purpose_quantity, b.intent_level, b.vehicle_name, b.vehicle_vno, \n" +
                "                 ISNULL(c.vehicle_count, 0) as vehicle_archives_count, ISNULL(c.vehicle_count, 0) as vehicle_contracts_count,\n" +
                "                 d.last_contract_time, b.presell_plan_back_time, e.seller_back_flag, ISNULL(f.clue_following_count,0) as clue_following_count, " +
				"				  a.maintainer_name as maintainers \n" +
                "               FROM vw_stat_effective_customer a\n" +
                "                 LEFT JOIN (\n" +
                "                             SELECT  vehicle_name, vehicle_vno, visitor_level, intent_level, visitor_id, last_visit_result, visit_time, plan_back_time as presell_plan_back_time, purpose_quantity,  ROW_NUMBER() OVER (PARTITION BY visitor_id ORDER BY create_time DESC) rn FROM presell_visitors\n" +
                "                           ) b ON a.object_id = b.visitor_id AND b.rn = 1\n" +
                "                 LEFT JOIN (\n" +
                "                             SELECT customer_id, COUNT(*) as vehicle_count FROM vehicle_archives GROUP BY customer_id\n" +
                "                           ) c ON a.object_id = c.customer_id\n" +
                "                 LEFT JOIN (\n" +
                "                             SELECT customer_id, SUM(contract_quantity) AS vehicle_count, MAX(create_time) AS last_contract_time FROM vehicle_sale_contracts GROUP BY customer_id\n" +
                "                           ) d ON a.object_id = d.customer_id\n" +
                "                 LEFT JOIN (\n" +
                "                             select customer_id, back_flag as seller_back_flag, ROW_NUMBER() OVER (PARTITION BY  customer_id ORDER BY plan_back_time DESC) rn from seller_call_back\n" +
                "                           ) e ON a.object_id = e.customer_id AND e.rn = 1" +
				"				  LEFT JOIN (\n" +
				" 						   SELECT visitor_id, COUNT(*) as clue_following_count FROM presell_visitors WHERE last_visit_result IS NULL OR last_visit_result = 0 GROUP BY visitor_id\n" +
				" 				   ) f ON a.object_id = f.visitor_id ";

		String buildSql = "SELECT base_tmp.* FROM (" + baseSql + ") base_tmp WHERE " + customerPoolCondition + "AND" + keywordCondition + "AND" + filterCondition + sort + pageSql;
        String countSql = "SELECT COUNT(*) FROM (" + baseSql + ") base_tmp WHERE " + customerPoolCondition + "AND" + keywordCondition + "AND" + filterCondition;
        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>(getMapBySQL(buildSql, null));
        result.setPage(page);
        result.setPerPage(perPage);
        List list = executeSQLQuery(countSql);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
	    return result;
	}*/

    @Override
    public PageModel getCustomers(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
        if (sort == null || sort.length() == 0) {
            sort = " ORDER BY visit_result DESC";
        } else {
            sort = " ORDER BY " + sort + " ";
        }
        if (page < 1) {
            page = 1;
        }
        if (perPage < 1) {
            perPage = 100;
        }

        //String[] sqls = initSql(user);
        //String customerPoolCondition = " (base_tmp.object_id IN (SELECT customer_pool.object_id FROM ("+sqls[0]+") customer_pool )) ";
        String filterCondition = mapToFilterString(filter, "base_tmp");
        String keywordCondition;
        if (keyword != null && keyword.length() > 0) {
            keywordCondition = String.format(" ( object_name LIKE '%%%s%%' OR (object_id IN (select customer_id from customer_organizational_structure WHERE name LIKE '%%%s%%')) OR mobile LIKE '%%%s%%'  ) ", keyword, keyword, keyword);
        } else {
            keywordCondition = " ( 1 = 1 ) ";
        }
        String pageSql = String.format("  OFFSET %d ROWS FETCH NEXT %d ROWS ONLY ", (page - 1) * perPage, perPage);

        String baseSql = "SELECT a.object_id, a.object_name, h.visit_result, a.customer_kind\n" +
                "\t, a.object_property, a.profession, a.phone, a.mobile, a.linkman, a.maintainer_id\n" +
                "\t, a.maintainer_name, ISNULL(h.visitor_count, 0) AS pending_visitor_count\n" +
                "FROM dbo.vw_interested_customers a\n" +
                "\tLEFT JOIN (\n" +
                "\t\tSELECT *\n" +
                "\t\tFROM (\n" +
                "\t\t\tSELECT ROW_NUMBER() OVER (PARTITION BY x.visitor_id ORDER BY x.create_time DESC) AS rowNumber, x.visitor_id, ISNULL(x.visit_result,0) visit_result, x.intent_level\n" +
                "\t\t\t\t, COUNT(0) AS visitor_count\n" +
                "\t\t\tFROM presell_visitors x\n" +
                "\t\t\tGROUP BY x.visitor_id, x.create_time, x.visit_result, x.intent_level\n" +
                "\t\t) t\n" +
                "\t\tWHERE t.rowNumber = 1\n" +
                "\t) h\n" +
                "\tON a.object_id = h.visitor_id\n";

        String buildSql = "SELECT base_tmp.* FROM (" + baseSql + ") base_tmp WHERE 1=1 AND" + keywordCondition + "AND" + filterCondition + sort + pageSql;
        String countSql = "SELECT COUNT(*) FROM (" + baseSql + ") base_tmp WHERE 1=1 AND" + keywordCondition + "AND" + filterCondition;
        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>(getMapBySQL(buildSql, null));
        result.setPage(page);
        result.setPerPage(perPage);
        List list = executeSQLQuery(countSql);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
        return result;
    }

	/*@Override
	public PageModel getMyCustomers(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
		String baseSql = "SELECT  ROW_NUMBER() OVER(PARTITION BY obj.object_id ORDER BY obj.object_id DESC) _id, obj.*, ISNULL(visitor_statistic.visitor_count, 0) AS visitor_count, CONVERT(varchar, visitor.plan_back_time, 23) AS plan_back_date, ISNULL(pending_visitor.visitor_count, 0) AS pending_visitor_count, visitor.intent_level, ISNULL(visitor.visit_result, 0) AS visit_result, structure.hierarchy, structure.name as structure_name, CONVERT(varchar, visitor_back.real_back_time, 23) AS real_back_date, sign.sign_date ,interCustomer.pics AS pics FROM vw_stat_effective_customer obj\n" +
				"  LEFT JOIN interested_customers interCustomer on obj.object_id = interCustomer.object_id \n"+
				"  LEFT JOIN customer_organizational_structure structure  ON obj.object_id = structure.customer_id\n" +
				"  LEFT JOIN (SELECT * FROM presell_visitors WHERE create_time IN (SELECT MAX(create_time) as create_time FROM presell_visitors GROUP BY visitor_id)) visitor ON obj.object_id = visitor.visitor_id\n" +
				"  LEFT JOIN (SELECT visitor_id, COUNT(0) AS visitor_count FROM presell_visitors GROUP BY visitor_id) visitor_statistic ON visitor_statistic.visitor_id = obj.object_id\n" +
				"  LEFT JOIN (SELECT visitor_id, COUNT(0) AS visitor_count FROM presell_visitors WHERE visit_result IS NULL GROUP BY visitor_id) pending_visitor ON pending_visitor.visitor_id = obj.object_id\n" +
				"  LEFT JOIN (SELECT a.visitor_id, MAX(b.real_back_time) AS real_back_time FROM presell_visitors a LEFT JOIN presell_visitors_back b ON a.visitor_no = b.visitor_no WHERE b.real_back_time IS NOT NULL GROUP BY a.visitor_id) visitor_back ON visitor_back.visitor_id = visitor.visitor_id\n" +
				"  LEFT JOIN (SELECT customer_id, CONVERT(VARCHAR, MAX(sign_time), 23) AS sign_date FROM vehicle_sale_contracts WHERE contract_status <> 3 AND contract_status <> 4 GROUP BY customer_id) sign ON obj.object_id = sign.customer_id\n" +
				"  WHERE ISNULL (obj.customer_type, 50) <> 50 AND ( obj.object_type & 2 = 2 OR obj.object_type = 0 ) AND obj.status = 1 ";
		if(user.hasPopedom(AccessPopedom.SaleCustomer.ALL_CUSTOMER)) {
			baseSql += "  AND obj.status = 1 AND obj.maintainer_id IS NOT NULL ";
		}else if(user.hasPopedom(AccessPopedom.SaleCustomer.DEPARTMENT_CUSTOMER)){
			baseSql += "  AND obj.status = 1 AND u.department = '" + user.getDepartment() + "'";
		}else{
			baseSql += "  AND obj.status = 1 AND obj.maintainer_id like '%" + user.getUserId() + "%'";
		}
		if(keyword != null && keyword.length() > 0){
			baseSql += String.format(" AND ( obj.object_name LIKE '%%%s%%' OR structure.name LIKE '%%%s%%' OR obj.mobile LIKE '%%%s%%') ", keyword, keyword, keyword);
		}

		String filterCondition = mapToFilterString(filter, "base_tmp");
		//---modify by chenbiao
		String stationIds = user.getModuleStations().get("102008");	//获取有效客户的站点范围
		if(StringUtils.isNotEmpty(stationIds)){
			String[] stationArray = stationIds.split(",");
			String stationCondition = getStationCondition(stationArray);
			filterCondition += " AND "+stationCondition;
		}
		//-----
		String sortSql;
		if(sort != null && sort.length() > 0){
			sortSql = " ORDER BY " + sort + " ";
		}else {
			sortSql = " ORDER BY create_time DESC ";
		}
		if(page<1){
			page = 1;
		}
		String pageSql = " OFFSET " + ((page-1)*perPage) + " ROW FETCH NEXT " + perPage + " ROWS ONLY ";

		String buildSql = "SELECT base_tmp.* FROM (" + baseSql + ") base_tmp WHERE 1 = 1 AND " + filterCondition + sortSql + pageSql;
		String countSql = "SELECT COUNT(DISTINCT base_tmp.object_id) FROM (" + baseSql + ") base_tmp WHERE 1 = 1 AND " + filterCondition;
		PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>(getMapBySQL(buildSql, null));
		result.setPage(page);
		result.setPerPage(perPage);
		List list = executeSQLQuery(countSql);
		result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
		return result;
	}*/

	/*@Override
	public PageModel getPublicCustomers(String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
		String baseSql = "SELECT obj.*, ISNULL(visitor_statistic.visitor_count, 0) AS visitor_count, visitor.intent_level, ISNULL(visitor.visit_result, 0) AS visit_result, structure.hierarchy, structure.name as structure_name, CONVERT(varchar, visitor_back.real_back_time, 23) AS real_back_date, sign.sign_date ,intCustomer.pics AS pics, intCustomer.modify_time as modify_time FROM vw_stat_effective_customer obj\n" +
				"  LEFT JOIN dbo.interested_customers intCustomer ON intCustomer.object_id = obj.object_id\n" +
				"  LEFT JOIN customer_organizational_structure structure  ON obj.object_id = structure.customer_id\n" +
				"  LEFT JOIN (SELECT * FROM presell_visitors WHERE create_time IN (SELECT MAX(create_time) as create_time FROM presell_visitors GROUP BY visitor_id)) visitor ON obj.object_id = visitor.visitor_id\n" +
				"  LEFT JOIN (SELECT visitor_id, COUNT(0) AS visitor_count FROM presell_visitors GROUP BY visitor_id) visitor_statistic ON visitor_statistic.visitor_id = obj.object_id\n" +
				"  LEFT JOIN (SELECT a.visitor_id, MAX(b.real_back_time) AS real_back_time FROM presell_visitors a LEFT JOIN presell_visitors_back b ON a.visitor_no = b.visitor_no WHERE b.real_back_time IS NOT NULL GROUP BY a.visitor_id) visitor_back ON visitor_back.visitor_id = visitor.visitor_id\n" +
				"  LEFT JOIN (SELECT customer_id, CONVERT(VARCHAR, MAX(sign_time), 23) AS sign_date FROM vehicle_sale_contracts WHERE contract_status <> 3 AND contract_status <> 4 GROUP BY customer_id) sign ON obj.object_id = sign.customer_id\n" +
				"  WHERE obj.status = 1 AND (obj.object_type & 2 = 2 OR obj.object_type = 0) AND obj.customer_type = 30 AND obj.maintainer_id IS NULL";
		if(keyword != null && keyword.length() > 0){
			baseSql += String.format(" AND ( obj.object_name LIKE '%%%s%%' OR structure.name LIKE '%%%s%%' OR obj.mobile LIKE '%%%s%%') ", keyword, keyword, keyword);
		}

		String filterCondition = mapToFilterString(filter, "base_tmp");
		String sortSql;
		if(sort != null && sort.length() > 0){
			sortSql = " ORDER BY " + sort + " ";
		}else {
			sortSql = " ORDER BY create_time DESC ";
		}
		String pageSql = " OFFSET " + ((page-1)*perPage) + " ROW FETCH NEXT " + perPage + " ROWS ONLY ";

		String buildSql = "SELECT base_tmp.* FROM (" + baseSql + ") base_tmp WHERE " + filterCondition + sortSql + pageSql;
		String countSql = "SELECT COUNT(*) FROM (" + baseSql + ") base_tmp WHERE " + filterCondition;
		PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>(getMapBySQL(buildSql, null));
		result.setPage(page);
		result.setPerPage(perPage);
		List list = executeSQLQuery(countSql);
		result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
		return result;
	}*/

    @Override
    public PageModel getPublicCustomers(String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
//        String baseSql = "SELECT obj.*, ISNULL(visitor_statistic.visitor_count, 0) AS visitor_count, visitor.intent_level, ISNULL(visitor.visit_result, 0) AS visit_result, structure.hierarchy, structure.name as structure_name, CONVERT(varchar, visitor_back.real_back_time, 23) AS real_back_date, sign.sign_date ,intCustomer.pics AS pics, intCustomer.modify_time as modify_time FROM vw_stat_effective_customer obj\n" +
//                "  LEFT JOIN dbo.interested_customers intCustomer ON intCustomer.object_id = obj.object_id\n" +
//                "  LEFT JOIN customer_organizational_structure structure  ON obj.object_id = structure.customer_id\n" +
//                "  LEFT JOIN (SELECT * FROM presell_visitors WHERE create_time IN (SELECT MAX(create_time) as create_time FROM presell_visitors GROUP BY visitor_id)) visitor ON obj.object_id = visitor.visitor_id\n" +
//                "  LEFT JOIN (SELECT visitor_id, COUNT(0) AS visitor_count FROM presell_visitors GROUP BY visitor_id) visitor_statistic ON visitor_statistic.visitor_id = obj.object_id\n" +
//                "  LEFT JOIN (SELECT a.visitor_id, MAX(b.real_back_time) AS real_back_time FROM presell_visitors a LEFT JOIN presell_visitors_back b ON a.visitor_no = b.visitor_no WHERE b.real_back_time IS NOT NULL GROUP BY a.visitor_id) visitor_back ON visitor_back.visitor_id = visitor.visitor_id\n" +
//                "  LEFT JOIN (SELECT customer_id, CONVERT(VARCHAR, MAX(sign_time), 23) AS sign_date FROM vehicle_sale_contracts WHERE contract_status <> 3 AND contract_status <> 4 GROUP BY customer_id) sign ON obj.object_id = sign.customer_id\n" +
//                "  WHERE obj.status = 1 AND (obj.object_type & 2 = 2 OR obj.object_type = 0) AND obj.customer_type = 30 AND obj.maintainer_id IS NULL";
        String baseSql = "SELECT a.object_id, a.object_name, a.create_time, h.intent_level, h.visit_result, a.customer_kind\n" +
                "\t, a.object_property, a.profession, a.phone, a.mobile, a.linkman, a.maintainer_id,a.area,a.city,a.province\n" +
                "\t, a.maintainer_name, ISNULL(h.visitor_count, 0) AS pending_visitor_count\n" +
                "FROM dbo.vw_interested_customers a\n" +
                "\tLEFT JOIN (\n" +
                "\t\tSELECT *\n" +
                "\t\tFROM (\n" +
                "\t\t\tSELECT ROW_NUMBER() OVER (PARTITION BY x.visitor_id ORDER BY x.create_time DESC) AS rowNumber, x.visitor_id, isnull(x.visit_result,0) visit_result, x.intent_level\n" +
                "\t\t\t\t, COUNT(0) AS visitor_count\n" +
                "\t\t\tFROM presell_visitors x\n" +
                "\t\t\tGROUP BY x.visitor_id, x.create_time, x.visit_result, x.intent_level\n" +
                "\t\t) t\n" +
                "\t\tWHERE t.rowNumber = 1\n" +
                "\t) h\n" +
                "\tON a.object_id = h.visitor_id\n" +
                "  LEFT JOIN customer_organizational_structure structure  ON a.object_id = structure.customer_id\n" +
                "WHERE ISNULL(a.status, 0) = 1 and ( a.object_type & 2 = 2 OR a.object_type = 0 ) and a.customer_type = 30 AND a.maintainer_id IS NULL";
        if (keyword != null && keyword.length() > 0) {
            baseSql += String.format(" AND ( a.object_name LIKE '%%%s%%' OR structure.name LIKE '%%%s%%' OR a.mobile LIKE '%%%s%%') ", keyword, keyword, keyword);
        }

        String filterCondition = mapToFilterString(filter, "base_tmp");
        String sortSql;
        if (sort != null && sort.length() > 0) {
            sortSql = " ORDER BY " + sort + " ";
        } else {
            sortSql = " ORDER BY create_time DESC ";
        }
        String pageSql = " OFFSET " + ((page - 1) * perPage) + " ROW FETCH NEXT " + perPage + " ROWS ONLY ";

        String buildSql = "SELECT base_tmp.* FROM (" + baseSql + ") base_tmp WHERE " + filterCondition + sortSql + pageSql;
        String countSql = "SELECT COUNT(*) FROM (" + baseSql + ") base_tmp WHERE " + filterCondition;
        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>(getMapBySQL(buildSql, null));
        result.setPage(page);
        result.setPerPage(perPage);
        List list = executeSQLQuery(countSql);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
        return result;
    }

    @Override
    public PageModel getClueBacks(SysUsers user, Map<String, Object> filter, int pageSize, int pageNo) {
        String baseSql = "SELECT back.*, customer.location_address AS visitor_location_address, customer.object_name AS visitor_name, customer.mobile AS visitor_mobile, customer.linkman, customer.address AS visitor_address FROM presell_visitors_back back\n" +
                "  LEFT JOIN presell_visitors visitor ON visitor.visitor_no = back.visitor_no\n" +
                "  LEFT JOIN vw_interested_customers customer ON visitor.visitor_id = customer.object_id\n" +
                "WHERE visitor.visitor_no IN(\n" +
                "  SELECT visitor.visitor_no FROM vw_stat_effective_customer obj\n" +
                "    INNER JOIN presell_visitors visitor ON obj.object_id = visitor.visitor_id\n" +
                "  WHERE obj.maintainer_id like '%" + user.getUserId() + "%'\n" +
                ")";
        String filterCondition = mapToFilterString(filter, "base_tmp");

        String pageSql = " OFFSET " + ((pageNo - 1) * pageSize) + " ROW FETCH NEXT " + pageSize + " ROWS ONLY ";

        String sortSql = " ORDER BY base_tmp.back_id DESC ";

        String buildSql = "SELECT base_tmp.* FROM (" + baseSql + ") base_tmp WHERE " + filterCondition + sortSql + pageSql;
        String countSql = "SELECT COUNT(*) FROM (" + baseSql + ") base_tmp WHERE " + filterCondition;
        PageModel<PresellVisitorsBackVO> result = new PageModel<PresellVisitorsBackVO>(getEntityBySQL(buildSql, PresellVisitorsBackVO.class, null));
        result.setPage(pageNo);
        result.setPerPage(pageSize);
        List list = executeSQLQuery(countSql);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
        return result;
    }

    @Override
    public PageModel getRelatedObjects(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
        if (sort == null || sort.length() == 0) {
            sort = " ORDER BY object_no DESC";
        } else {
            sort = " ORDER BY " + sort + " ";
        }
        if (page < 1) {
            page = 1;
        }
        if (perPage < 1) {
            perPage = 100;
        }

        String filterCondition = mapToFilterString(filter, "base_tmp");
        String keywordCondition;
        if (keyword != null && keyword.length() > 0) {
            keywordCondition = String.format(" ( object_name LIKE '%%%s%%' OR phone LIKE '%%%s%%' OR mobile LIKE '%%%s%%' ) ", keyword, keyword, keyword);
        } else {
            keywordCondition = " ( 1 = 1 ) ";
        }
        String pageSql = String.format("  OFFSET %d ROWS FETCH NEXT %d ROWS ONLY ", (page - 1) * perPage, perPage);

        String baseSql = "SELECT * FROM base_related_objects WHERE status = 1";

        String buildSql = "SELECT * FROM(" + baseSql + ") base_tmp WHERE " + keywordCondition + "AND" + filterCondition + sort + pageSql;
        String countSql = "SELECT COUNT(*) FROM(" + baseSql + ") base_tmp WHERE " + keywordCondition + "AND" + filterCondition;
        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>(getMapBySQL(buildSql, null));
        result.setPage(page);
        result.setPerPage(perPage);
        List list = executeSQLQuery(countSql);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
        return result;
    }

	/*@Override
	public void giveUpCustomer(SysUsers user, String objectId) {
		List<BaseRelatedObjectMaintenace> maintenceList = getEntityBySQL("SELECT * FROM base_related_object_maintenace" +
				" WHERE object_id = '" + objectId + "' AND user_id = '" + user.getUserId() + "'", BaseRelatedObjectMaintenace.class, null);
		if(maintenceList != null && maintenceList.size() > 0){
			for(int i = 0;i<maintenceList.size();i++){
				delete(maintenceList.get(i));
			}
		}
	}*/

    @Override
    public PageModel getSaleClues(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
        String baseSql = "SELECT obj.object_id, obj.object_name, obj.object_property, back.real_back_date, visitor.*, structure.hierarchy, structure.name as structure_name FROM vw_interested_customers obj\n" +
                "  LEFT JOIN customer_organizational_structure structure  ON obj.object_id = structure.customer_id\n" +
                "  INNER JOIN presell_visitors visitor ON obj.object_id = visitor.visitor_id\n" +
                "  LEFT JOIN (SELECT visitor_no, CONVERT(varchar, MAX(real_back_time), 23) AS real_back_date FROM presell_visitors_back GROUP BY visitor_no) back ON back.visitor_no = visitor.visitor_no\n";
        if (user.hasPopedom(AccessPopedom.SaleCustomer.ALL_CUSTOMER)) {
            baseSql += "  WHERE obj.status = 1 ";
        } else {
            baseSql += "  WHERE obj.status = 1 AND obj.maintainer_id like '%" + user.getUserId() + "%' ";
        }
        if (keyword != null && keyword.length() > 0) {
            baseSql += String.format(" AND ( obj.object_name LIKE '%%%s%%' OR structure.name LIKE '%%%s%%' OR obj.mobile LIKE '%%%s%%') ", keyword, keyword, keyword);
        }

        if (sort == null || sort.length() == 0) {
            sort = " ORDER BY base_tmp.visitor_level DESC";
        } else {
            sort = " ORDER BY base_tmp." + sort + " ";
        }
        if (page < 1) {
            page = 1;
        }
        if (perPage < 1) {
            perPage = 100;
        }

        String filterCondition = mapToFilterString(filter, "base_tmp");
        //---modify by chenbiao
        String stationIds = user.getModuleStations().get("102012");    //获取线索跟踪检查的站点范围
        if (StringUtils.isNotEmpty(stationIds)) {
            String[] stationArray = stationIds.split(",");
            String stationCondition = getStationCondition(stationArray);
            filterCondition += " AND " + stationCondition;
        }
        //-----
        String pageSql = String.format("  OFFSET %d ROWS FETCH NEXT %d ROWS ONLY ", (page - 1) * perPage, perPage);

        String buildSql = "SELECT base_tmp.* FROM (" + baseSql + ") base_tmp " + " WHERE " + filterCondition + sort + pageSql;
        String countSql = "SELECT COUNT(1) FROM (" + baseSql + ") base_tmp " + " WHERE " + filterCondition;
        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>(getMapBySQL(buildSql, null));
        result.setPage(page);
        result.setPerPage(perPage);
        List list = executeSQLQuery(countSql);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
        return result;
    }

    @Override
    public PageModel getCallbacks(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
        if (sort == null || sort.length() == 0) {
            sort = " ORDER BY out_stock_time DESC";
        } else {
            sort = " ORDER BY " + sort + " ";
        }
        if (page < 1) {
            page = 1;
        }
        if (perPage < 1) {
            perPage = 100;
        }

        String[] sqls = initSql(user);
        String customerPoolCondition = " (base_tmp.object_id IN (SELECT customer_pool.object_id FROM (" + sqls[0] + ") customer_pool )) ";
        String filterCondition = mapToFilterString(filter, "base_tmp");
        String keywordCondition;
        if (keyword != null && keyword.length() > 0) {
            keywordCondition = String.format(" ( object_name LIKE '%%%s%%' OR (object_id IN (select customer_id from customer_organizational_structure WHERE name LIKE '%%%s%%')) OR mobile LIKE '%%%s%%'  ) ", keyword, keyword, keyword);
        } else {
            keywordCondition = " ( 1 = 1 ) ";
        }
        String pageSql = String.format("  OFFSET %d ROWS FETCH NEXT %d ROWS ONLY ", (page - 1) * perPage, perPage);

        String baseSql = " SELECT a.*, b.out_stock_time, b.contract_no, c.vehicle_vno, c.vehicle_color, d.service_linkman, d.service_phone, d.object_name, d.object_id\n" +
                "                FROM seller_call_back a\n" +
                "                  LEFT JOIN vehicle_out_stocks b ON a.out_stock_no = b.out_stock_no\n" +
                "                  LEFT JOIN vehicle_sale_contract_detail c ON b.contract_no = c.contract_no\n" +
                "                  LEFT JOIN vw_stat_effective_customer d ON a.customer_id = d.object_id ";

        String buildSql = "SELECT base_tmp.* FROM (" + baseSql + ") base_tmp WHERE " + customerPoolCondition + "AND" + keywordCondition + "AND" + filterCondition + sort + pageSql;
        String countSql = "SELECT COUNT(*) FROM (" + baseSql + ") base_tmp WHERE " + customerPoolCondition + "AND" + keywordCondition + "AND" + filterCondition;
        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>(getMapBySQL(buildSql, null));
        result.setPage(page);
        result.setPerPage(perPage);
        List list = executeSQLQuery(countSql);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
        return result;
    }

    @Override
    public PageModel getVehicleStore(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
        if (sort == null || sort.length() == 0) {
            sort = " ORDER BY vehicle_vno ASC";
        } else {
            sort = " ORDER BY " + sort + " ";
        }
        if (page < 1) {
            page = 1;
        }
        if (perPage < 1) {
            perPage = 100;
        }

        String filterCondition = mapToFilterString(filter, "base_tmp");
        String keywordCondition;
        if (keyword != null && keyword.length() > 0) {
            keywordCondition = String.format(" ( vehicle_name LIKE '%%%s%%' OR vehicle_vno LIKE '%%%s%%' OR vehicle_brand LIKE '%%%s%%' OR vehicleStrain LIKE '%%%s%%') ", keyword, keyword, keyword, keyword);
        } else {
            keywordCondition = " ( 1 = 1 ) ";
        }
//		String pageSql = String.format("  OFFSET %d ROWS FETCH NEXT %d ROWS ONLY ", (page-1)*perPage, perPage);

        String baseSql = "SELECT vno_id, vno_id as self_id, short_name, product_model as vehicle_vno, vehicle_name, vehicleStrain, vehicle_kind, vehicle_brand " +
                " FROM vw_vehicle_type WHERE short_name IS NOT NULL";
        String buildSql = "SELECT * FROM(" + baseSql + ") base_tmp WHERE " + keywordCondition + "AND" + filterCondition + sort;
        String countSql = "SELECT COUNT(*) FROM(" + baseSql + ") base_tmp WHERE " + keywordCondition + "AND" + filterCondition;
//		PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>(getMapBySQL(buildSql, null));
//		result.setPage(page);
//		result.setPerPage(perPage);
//		List list = executeSQLQuery(countSql);
//		result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));

        PageModel<Object> result = listInSql(buildSql, countSql, page, perPage, null);

        return result;
    }

    @Override
    public PageModel getMyCalendar(SysUsers user, Map<String, Object> condition, int pageSize, int pageNo) {
        return this.getMyCalendar(false, user, condition, pageSize, pageNo);

    }

    private PageModel getMyCalendar(boolean onlyQueryCount, SysUsers user, Map<String, Object> condition, int pageSize, int pageNo) {
        String filterCondition = mapToFilterString(condition, "base_tmp");
        String baseSql = "SELECT h.visit_result, h.intent_level, h.purpose_quantity,h.visitor_level,obj.object_id,obj.station_id as station_id, back.back_id, 'ClueBack' AS type,back.remark,back.before_back_intent_level, back.before_back_visitor_level, back.back_intent_level, back.back_visitor_level, back.back_content, back.plan_back_time, back.real_back_time, back.backer, back.creator, obj.object_name, obj.address, obj.mobile, obj.phone,obj.maintainer_id \n" +
                "FROM presell_visitors_back back WITH ( NOLOCK ) \n" +
                "LEFT JOIN vw_interested_customers obj WITH ( NOLOCK ) ON back.object_id = obj.object_id \n" +
                "LEFT JOIN (\n" +
                "\t\t\tSELECT *\n" +
                "\t\t\tFROM (\n" +
                "\t\t\t\tSELECT ROW_NUMBER() OVER (PARTITION BY x.visitor_id ORDER BY x.order_time DESC, x.create_time DESC) AS rowNumber, x.visitor_id, x.visit_result, x.intent_level, purpose_quantity,visitor_level\n" +
                "\t\t\t\tFROM (\n" +
                "\t\t\t\t\tSELECT CASE \n" +
                "\t\t\t\t\t\t\tWHEN ISNULL(visit_result, '') = '' THEN GETDATE()\n" +
                "\t\t\t\t\t\t\tELSE create_time\n" +
                "\t\t\t\t\t\tEND AS order_time, *\n" +
                "\t\t\t\t\tFROM presell_visitors WITH ( NOLOCK ) \n" +
                "\t\t\t\t) x\n" +
                "\t\t\t\tWHERE ISNULL(x.visit_result, '') = ''\n" +
                "\t\t\t\t\tOR x.visit_result = 10\n" +
                "\t\t\t\t\tOR x.visit_result = 20\n" +
                "\t\t\t) t\n" +
                "\t\t\tWHERE t.rowNumber = 1\n" +
                "\t\t) h\n" +
                "\t\tON obj.object_id = h.visitor_id";
        String conditionStr = " WHERE base_tmp.maintainer_id like '%" + user.getUserId() + "%' ";

        //没有共同维系客户共享查看权限，只能看建单人是自己或销售员是自已和线索
        Map<String, Object> param = new HashMap<String, Object>();
        if (!user.hasPopedom(POPEDOM_SHARED_MAINTAINER)) {
            conditionStr += " AND (base_tmp.creator = :userFullName OR base_tmp.backer=:userFullName ) ";
            param.put("userFullName", user.getUserFullName());
        } else {
            conditionStr += " AND (base_tmp.station_id IN (:stationIds)  OR base_tmp.creator = :userFullName  OR base_tmp.backer=:userFullName) ";
            param.put("stationIds", userService.getModuleStationId(user, MODULE_EFFECTIVE_CUSTOMER));
            param.put("userFullName", user.getUserFullName());
        }
        String sql = null, totalSql = null;
        String countSql = null;
        if(onlyQueryCount){
            countSql = "SELECT CONVERT(CHAR(10),base_tmp.plan_back_time,120) as date, COUNT(1) AS count FROM(" + baseSql + ") base_tmp ";
            countSql += conditionStr + " AND " + filterCondition;
            countSql += " GROUP BY CONVERT(CHAR(10),base_tmp.plan_back_time,120) ORDER BY date DESC";
            return new PageModel<Map<String, Object>>(getMapBySQL(countSql, param));
        }else{
            sql = "SELECT * FROM(" + baseSql + ") base_tmp ";
            if (pageNo < 1) {
                pageNo = 1;
            }
            String pageSql = String.format("  OFFSET %d ROWS FETCH NEXT %d ROWS ONLY ", (pageNo - 1) * pageSize, pageSize);
            sql += conditionStr + " AND " + filterCondition + " ORDER BY base_tmp.plan_back_time DESC " + pageSql;
            totalSql = "SELECT COUNT(1) as count FROM(" + baseSql + ") base_tmp ";
            totalSql += conditionStr + " AND " + filterCondition;
            PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>(getMapBySQL(sql, param));
            result.setPage(pageNo);
            result.setPerPage(pageSize);
            List<Map<String, Object>> list = getMapBySQL(totalSql, param);
            int totalSize = (list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).get("count").toString());
            result.setTotalSize(totalSize);
            return result;
        }
    }


    @Override
    public List<Map<String, Object>> getMyCalendarCount(SysUsers user, Map<String, Object> condition) {
        return this.getMyCalendar(true, user, condition, 0, 0).getData();
    }

    private String getStationCondition(String[] stationArray) {
        return getStationCondition(null, "station_id", stationArray);
    }

    private String getStationCondition(String alias, String stationIdField, String[] stationArray) {
        if (StringUtils.isEmpty(alias)) {
            alias = "base_tmp";
        }
        List<String> list = new ArrayList<>(stationArray.length);
        for (String stationId : stationArray) {
            list.add(alias + "."+stationIdField+" like '%" + stationId + "%'");
        }
        String stationCondition = " (" + StringUtils.join(list, " OR ") + ")";
        return stationCondition;
    }

    private String getLocationGroupKey(String province, String city, Map<String, Object> user) {
        if (city != null) {
            if (!user.containsKey("area") || user.get("area") == null) {
                return null;
            }
            return user.get("area").toString();
        }
        if (province != null) {
            if (!user.containsKey("city") || user.get("city") == null) {
                return null;
            }
            return user.get("city").toString();
        }
        if (!user.containsKey("province") || user.get("province") == null) {
            return null;
        }
        return user.get("province").toString();
    }

    @Override
    public Map<String, Object> getCustomerMap(SysUsers user, String province, String city, String area, String profession, boolean onlySelf, String stationId) {
        Map<String, Integer> total = new HashMap<String, Integer>(6);
        Map<String, Integer> customer_total = new HashMap<String, Integer>(6);
        Map<String, Integer> intention_customer_total = new HashMap<String, Integer>(6);
        Map<String, Integer> success_customer_total = new HashMap<String, Integer>(6);
        Map<String, Integer> failure_customer_total = new HashMap<String, Integer>(6);
        Map<String, Integer> seller_total = new HashMap<String, Integer>(6);

        List<String> customerCondition = new ArrayList<String>();
        customerCondition.add("1=1");
        if (province != null && province.length() > 0) {
            customerCondition.add("province = '" + province + "'");
        }
        if (city != null && city.length() > 0) {
            customerCondition.add("city = '" + city + "'");
        }
        if (area != null && area.length() > 0) {
            customerCondition.add("area = '" + area + "'");
        }
        if (profession != null && profession.length() > 0) {
            customerCondition.add("profession = '" + profession + "'");
        }
        if (stationId != null && stationId.length() > 0) {
            customerCondition.add("station_id LIKE '%" + stationId + "%'");
        }

//		String baseSql = "SELECT DISTINCT obj.*, ROW_NUMBER() OVER ( PARTITION BY obj.object_id  ORDER BY obj.object_id DESC ) AS _id, ISNULL(visitor_statistic.visitor_count, 0) AS visitor_count," +
//				"  CONVERT(varchar, visitor.plan_back_time, 23) AS plan_back_date, ISNULL(pending_visitor.visitor_count, 0) AS pending_visitor_count, visitor.intent_level, " +
//				"  ISNULL(success_visitor.visitor_count, 0) AS success_visitor_count, ISNULL(fail_visitor.visitor_count, 0) AS fail_visitor_count, " +
//				"  ISNULL(visitor.visit_result, 0) AS visit_result, structure.hierarchy, structure.name as structure_name, " +
//				"  CONVERT(varchar, visitor_back.real_back_time, 23) AS real_back_date, sign.sign_date FROM vw_stat_effective_customer obj\n" +
//				"  LEFT JOIN base_related_object_maintenace maintenace ON obj.object_id = maintenace.object_id AND maintenace.user_id IS NOT NULL \n" +
//				"  LEFT JOIN customer_organizational_structure structure  ON obj.object_id = structure.customer_id\n" +
//				"  LEFT JOIN sys_users u ON maintenace.user_id = u.user_id\n" +
//				"  LEFT JOIN (SELECT * FROM presell_visitors WHERE create_time IN (SELECT MAX(create_time) as create_time FROM presell_visitors GROUP BY visitor_id)) visitor ON obj.object_id = visitor.visitor_id\n" +
//				"  LEFT JOIN (SELECT visitor_id, COUNT(0) AS visitor_count FROM presell_visitors GROUP BY visitor_id) visitor_statistic ON visitor_statistic.visitor_id = obj.object_id\n" +
//				"  LEFT JOIN (SELECT visitor_id, COUNT(0) AS visitor_count FROM presell_visitors WHERE visit_result IS NULL GROUP BY visitor_id) pending_visitor ON pending_visitor.visitor_id = obj.object_id\n" +
//				"  LEFT JOIN (SELECT visitor_id, COUNT(0) AS visitor_count FROM presell_visitors WHERE visit_result = 10 GROUP BY visitor_id) success_visitor ON success_visitor.visitor_id = obj.object_id\n" +
//				"  LEFT JOIN (SELECT visitor_id, COUNT(0) AS visitor_count FROM presell_visitors WHERE visit_result = 20 GROUP BY visitor_id) fail_visitor ON fail_visitor.visitor_id = obj.object_id\n" +
//				"  LEFT JOIN (SELECT a.visitor_id, MAX(b.real_back_time) AS real_back_time FROM presell_visitors a LEFT JOIN presell_visitors_back b ON a.visitor_no = b.visitor_no WHERE b.real_back_time IS NOT NULL GROUP BY a.visitor_id) visitor_back ON visitor_back.visitor_id = visitor.visitor_id\n" +
//				"  LEFT JOIN (SELECT customer_id, CONVERT(VARCHAR, MAX(sign_time), 23) AS sign_date FROM vehicle_sale_contracts WHERE contract_status <> 3 AND contract_status <> 4 GROUP BY customer_id) sign ON obj.object_id = sign.customer_id\n " +
//				"  WHERE ISNULL (customer_type, 50) <> 50 AND ( object_type & 2 = 2 OR object_type = 0 ) AND obj.status = 1 AND maintenace.user_id IS NOT NULL ";
		/*String baseSql = "SELECT obj.*, ROW_NUMBER() OVER ( PARTITION BY obj.object_id  ORDER BY obj.object_id DESC ) AS _id\n" +
				"  ,ISNULL(visitor_statistic.visitor_count, 0) AS visitor_count\n" +
				"  ,CONVERT(varchar, visitor.plan_back_time, 23) AS plan_back_date\n" +
				"  ,ISNULL(visitor_statistic.pending_visitor_count, 0) AS pending_visitor_count\n" +
				"  ,visitor.intent_level\n" +
				"  ,ISNULL(visitor_statistic.success_visitor_count, 0) AS success_visitor_count\n" +
				"  ,ISNULL(visitor_statistic.fail_visitor_count, 0) AS fail_visitor_count\n" +
				"  ,ISNULL(visitor.visit_result, 0) AS visit_result\n" +
				"  ,structure.hierarchy, structure.name as structure_name\n" +
				"  ,visitor_back.real_back_date\n" +
				"  ,sign.sign_date\n" +
				"  FROM vw_stat_effective_customer obj\n" +
				"  LEFT JOIN base_related_object_maintenace maintenace ON obj.object_id = maintenace.object_id AND maintenace.user_id IS NOT NULL\n" +
				"  LEFT JOIN customer_organizational_structure structure  ON obj.object_id = structure.customer_id\n" +
				"  LEFT JOIN sys_users u ON maintenace.user_id = u.user_id\n" +
				"  LEFT JOIN (SELECT * FROM presell_visitors WHERE create_time IN (SELECT MAX(create_time) as create_time FROM presell_visitors GROUP BY visitor_id)) visitor ON obj.object_id = visitor.visitor_id\n" +
				"  LEFT JOIN (SELECT visitor_id\n" +
				"       ,COUNT(0) AS visitor_count\n" +
				"       ,SUM(CASE WHEN visit_result IS NULL THEN 1 ELSE 0 END) AS pending_visitor_count\n" +
				"       ,SUM(CASE WHEN visit_result = 10 THEN 1 ELSE 0 END) AS success_visitor_count\n" +
				"       ,SUM(CASE WHEN visit_result = 20 THEN 1 ELSE 0 END) AS fail_visitor_count\n" +
				"     FROM presell_visitors GROUP BY visitor_id\n" +
				"   ) visitor_statistic ON visitor_statistic.visitor_id = obj.object_id\n" +
				"  LEFT JOIN (SELECT a.visitor_id, CONVERT(varchar, MAX(b.real_back_time), 23) AS real_back_date FROM presell_visitors a LEFT JOIN presell_visitors_back b ON a.visitor_no = b.visitor_no WHERE b.real_back_time IS NOT NULL GROUP BY a.visitor_id) visitor_back ON visitor_back.visitor_id = visitor.visitor_id\n" +
				"  LEFT JOIN (SELECT customer_id, CONVERT(VARCHAR, MAX(sign_time), 23) AS sign_date FROM vehicle_sale_contracts WHERE contract_status <> 3 AND contract_status <> 4 GROUP BY customer_id) sign ON obj.object_id = sign.customer_id \n" +
				"  WHERE ISNULL (customer_type, 50) <> 50 AND ( object_type & 2 = 2 OR object_type = 0 ) AND obj.status = 1 AND maintenace.user_id IS NOT NULL ";*/
        String baseSql = "SELECT obj.*, ROW_NUMBER() OVER ( PARTITION BY obj.object_id  ORDER BY obj.object_id DESC ) AS _id\n" +
                "  ,ISNULL(visitor_statistic.visitor_count, 0) AS visitor_count\n" +
                "  ,CONVERT(varchar, visitor.plan_back_time, 23) AS plan_back_date\n" +
                "  ,ISNULL(visitor_statistic.pending_visitor_count, 0) AS pending_visitor_count\n" +
                "  ,visitor.intent_level\n" +
                "  ,ISNULL(visitor_statistic.success_visitor_count, 0) AS success_visitor_count\n" +
                "  ,ISNULL(visitor_statistic.fail_visitor_count, 0) AS fail_visitor_count\n" +
                "  ,ISNULL(visitor.visit_result, 0) AS visit_result\n" +
                "  ,structure.hierarchy, structure.name as structure_name\n" +
                "  ,visitor_back.real_back_date\n" +
                "  ,sign.sign_date\n" +
                "  FROM vw_stat_effective_customer obj\n" +
                "  LEFT JOIN customer_organizational_structure structure  ON obj.object_id = structure.customer_id\n" +
                "  LEFT JOIN (SELECT * FROM presell_visitors WHERE create_time IN (SELECT MAX(create_time) as create_time FROM presell_visitors GROUP BY visitor_id)) visitor ON obj.object_id = visitor.visitor_id\n" +
                "  LEFT JOIN (SELECT visitor_id\n" +
                "       ,COUNT(0) AS visitor_count\n" +
                "       ,SUM(CASE WHEN visit_result IS NULL THEN 1 ELSE 0 END) AS pending_visitor_count\n" +
                "       ,SUM(CASE WHEN visit_result = 10 THEN 1 ELSE 0 END) AS success_visitor_count\n" +
                "       ,SUM(CASE WHEN visit_result = 20 THEN 1 ELSE 0 END) AS fail_visitor_count\n" +
                "     FROM presell_visitors GROUP BY visitor_id\n" +
                "   ) visitor_statistic ON visitor_statistic.visitor_id = obj.object_id\n" +
                "  LEFT JOIN (SELECT a.visitor_id, CONVERT(varchar, MAX(b.real_back_time), 23) AS real_back_date FROM presell_visitors a LEFT JOIN presell_visitors_back b ON a.visitor_no = b.visitor_no WHERE b.real_back_time IS NOT NULL GROUP BY a.visitor_id) visitor_back ON visitor_back.visitor_id = visitor.visitor_id\n" +
                "  LEFT JOIN (SELECT customer_id, CONVERT(VARCHAR, MAX(sign_time), 23) AS sign_date FROM vehicle_sale_contracts WHERE contract_status <> 3 AND contract_status <> 4 GROUP BY customer_id) sign ON obj.object_id = sign.customer_id \n" +
                "  WHERE ISNULL (customer_type, 50) <> 50 AND ( object_type & 2 = 2 OR object_type = 0 ) AND obj.status = 1 AND obj.maintainer_id IS NOT NULL ";
        if (!user.hasPopedom(AccessPopedom.SaleCustomer.ALL_CUSTOMER) || onlySelf) {
            baseSql += "  AND obj.maintainer_id like '%" + user.getUserId() + "%' ";
        }

        String filterCondition = StringUtils.join(customerCondition, " AND ");

//		String countSql = "SELECT COUNT(DISTINCT base_tmp.object_id) FROM (" + baseSql + ") base_tmp WHERE base_tmp._id = 1 AND " + filterCondition;
//		List list = executeSQLQuery(countSql);
//		total.put("total_customer", (list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
//		list = executeSQLQuery(countSql + " AND pending_visitor_count > 0 AND visit_result = 0 ");
//		total.put("intention_customer", (list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));

        String keyFieldName = "province";
        if (province != null && province.length() > 0) {
            keyFieldName = "city";
        }
        if (city != null && city.length() > 0) {
            keyFieldName = "area";
        }

        String groupKeySql = "SELECT ISNULL(" + keyFieldName + ", '其他') AS group_key, COUNT(0) AS customer_total,\n" +
                "  SUM(CASE WHEN pending_visitor_count > 0 AND visit_result = 0 THEN 1 ELSE 0 END) AS intention_customer_total,\n" +
                "  SUM(CASE WHEN visit_result = 10 THEN 1 ELSE 0 END) AS success_customer_total,\n" +
                "  SUM(CASE WHEN visit_result = 20 THEN 1 ELSE 0 END) AS failure_customer_total  FROM (" + baseSql + ") base_tmp WHERE base_tmp._id = 1 AND " + filterCondition +
                "  GROUP BY " + keyFieldName;
        int intention_customer = 0;
        int total_customer = 0;

        for (Map<String, Object> row : getMapBySQL(groupKeySql, null)) {
            String group_key = row.get("group_key").toString();
            customer_total.put(group_key, Integer.valueOf(row.get("customer_total").toString()));
            intention_customer_total.put(group_key, Integer.valueOf(row.get("intention_customer_total").toString()));
            success_customer_total.put(group_key, Integer.valueOf(row.get("success_customer_total").toString()));
            failure_customer_total.put(group_key, Integer.valueOf(row.get("failure_customer_total").toString()));

            intention_customer += Integer.valueOf(row.get("intention_customer_total").toString());
            total_customer += Integer.valueOf(row.get("customer_total").toString());
        }
        total.put("total_customer", total_customer);
        total.put("intention_customer", intention_customer);

        // List<String> customerIds = new ArrayList<>(customerList.size());
//		for(Map<String, Object> row : customerList){
//			// customerIds.add(row.get("object_id").toString());
//
//			String group_key = getLocationGroupKey(province, city, row);
//			if(group_key == null){
//				group_key = "其他";
//			}
//			if(customer_total.containsKey(group_key)){
//				customer_total.put(group_key, customer_total.get(group_key) + 1);
//			}else{
//				customer_total.put(group_key, 1);
//			}
//
//			Object pending_visitor_count = row.get("pending_visitor_count");
//			Object visit_result = row.get("visit_result");
//			if(pending_visitor_count != null && Integer.valueOf(pending_visitor_count.toString())>0 && (visit_result != null && visit_result.toString().equals("0"))){
//				if(intention_customer_total.containsKey(group_key)){
//					intention_customer_total.put(group_key, intention_customer_total.get(group_key) + 1);
//				}else{
//					intention_customer_total.put(group_key, 1);
//				}
//			}
//			if(visit_result != null && visit_result.toString().equals("10")){
//				if(success_customer_total.containsKey(group_key)){
//					success_customer_total.put(group_key, success_customer_total.get(group_key) + 1);
//				}else{
//					success_customer_total.put(group_key, 1);
//				}
//			}
//			if(visit_result != null && visit_result.toString().equals("20")){
//				if(failure_customer_total.containsKey(group_key)){
//					failure_customer_total.put(group_key, failure_customer_total.get(group_key) + 1);
//				}else{
//					failure_customer_total.put(group_key, 1);
//				}
//			}
//		}
        // String customerIdsStr = "('" + StringUtils.join(customerIds, "','") + "')";

//		countSql = "SELECT COUNT (*)\n" +  // 订车数
//				"FROM dbo.vehicle_sale_contract_detail a\n" +
//				"LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
//				"WHERE b.contract_status <> 3\n" +
//				"\tAND b.contract_status <> 4\n" +
//				"\tAND a.approve_status <> 30 AND b.customer_id IN " + customerIdsStr;
//		list = executeSQLQuery(countSql + " AND b.create_time > DATEADD(DAY,-365,GETDATE())");
//		total.put("order_vehicle_count", (list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
//		list = executeSQLQuery(countSql + " AND a.real_deliver_time > DATEADD(DAY,-365,GETDATE())");
//		total.put("deliver_vehicle_count", (list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
//
//		countSql = "SELECT COUNT (*) FROM dbo.presell_visitors WHERE finish_date > DATEADD(DAY,-365,GETDATE()) AND visitor_id IN " + customerIdsStr;
//		list = executeSQLQuery(countSql + " AND visit_result = 10");
//		total.put("visit_success", (list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));
//		list = executeSQLQuery(countSql + " AND visit_result = 20");
//		total.put("visit_failure", (list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).toString()));

        List<String> total_customer_condition = new ArrayList<String>(7);
        List<String> total_other_condition = new ArrayList<String>(7);
        total_customer_condition.add("1 = 1");
        total_other_condition.add("1 = 1");

        if (province != null && province.length() > 0) {
            total_customer_condition.add("b.customer_province = '" + province + "'");
            total_other_condition.add("b.province = '" + province + "'");
        }
        if (city != null && city.length() > 0) {
            total_customer_condition.add("b.customer_city = '" + city + "'");
            total_other_condition.add("b.city = '" + city + "'");
        }
        if (area != null && area.length() > 0) {
            total_customer_condition.add("b.customer_area = '" + area + "'");
            total_other_condition.add("b.area = '" + area + "'");
        }
        if (profession != null && profession.length() > 0) {
            total_customer_condition.add("b.customer_profession = '" + profession + "'");
            total_other_condition.add("b.profession = '" + profession + "'");
        }
        if (stationId != null && stationId.length() > 0) {
            total_customer_condition.add("b.station_id = '" + stationId + "'");
            total_other_condition.add("a.station_id = '" + stationId + "'");
        }
        if (onlySelf) {
            total_customer_condition.add("b.seller_id = '" + user.getUserId() + "'");
            total_other_condition.add("a.seller_id = '" + user.getUserId() + "'");
        }

        String countSql = this.getQueryStringByName("customerMapTotal",
                new String[]{"customer_condition", "condition"},
                new String[]{" AND " + StringUtils.join(total_customer_condition, " AND "), " AND " + StringUtils.join(total_other_condition, " AND ")});

        List<Map<String, Object>> total_result = getMapBySQL(countSql, null);
        if (total_result != null && total_result.size() > 0) {
            total.put("order_vehicle_count", Integer.valueOf(total_result.get(0).get("order_vehicle_count").toString()));
            total.put("deliver_vehicle_count", Integer.valueOf(total_result.get(0).get("deliver_vehicle_count").toString()));
            total.put("visit_success", Integer.valueOf(total_result.get(0).get("visit_success").toString()));
            total.put("visit_failure", Integer.valueOf(total_result.get(0).get("visit_failure").toString()));
        } else {
            total.put("order_vehicle_count", 0);
            total.put("deliver_vehicle_count", 0);
            total.put("visit_success", 0);
            total.put("visit_failure", 0);
        }

        String groupByKey = "customer_area";
        if (city == null) {
            groupByKey = "customer_city";
        }
        if (province == null) {
            groupByKey = "customer_province";
        }

        countSql = "SELECT " + groupByKey + " AS group_key, COUNT(1) AS vehicle_sale_count\n" +
                "FROM dbo.vehicle_sale_contract_detail a\n" +
                "  LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
                "      WHERE \n" +
                "      a.approve_status IN ( 0, 1, 20 )\n" +
                "      AND b.contract_status <> 3\n" +
                "      AND b.contract_status <> 4\n" +
                "      AND a.real_deliver_time IS NULL\n" +
                "      AND DATEDIFF (YEAR, b.sign_time, GETDATE ()) = 0\n" +
                "      AND " + StringUtils.join(total_customer_condition, " AND ") +
                "      GROUP BY b." + groupByKey;

//		countSql = "SELECT " + groupByKey + " as group_key, COUNT (*) AS vehicle_sale_count\n" +  // 订车数
//				"FROM dbo.vehicle_sale_contract_detail a\n" +
//				"LEFT JOIN dbo.vehicle_sale_contracts b ON b.contract_no = a.contract_no\n" +
//				"LEFT JOIN vw_interested_customers c ON c.object_id = b.customer_id\n" +
//				"WHERE b.contract_status <> 3\n" +
//				"\tAND b.contract_status <> 4\n" +
//				"\tAND b.create_time > DATEADD(DAY,-365,GETDATE())\n"+
//				"\tAND a.approve_status <> 30 AND b.customer_id IN " + customerIdsStr + "\n"+
//				"\tGROUP BY c." + groupByKey;
        List<Map<String, Object>> seller_count_list = getMapBySQL(countSql, null);
        for (Map<String, Object> row : seller_count_list) {
            if (row != null && row.containsKey("group_key")) {
                Object group_key = row.get("group_key");
                if (group_key != null) {
                    seller_total.put(row.get("group_key").toString(), Integer.valueOf(row.get("vehicle_sale_count").toString()));
                }
            }
        }

        Map<String, Object> result = new HashMap<String, Object>(3);
        if (area != null) {
            String buildSql = "SELECT base_tmp.* FROM (" + baseSql + ") base_tmp WHERE base_tmp._id = 1 AND " + filterCondition;
            List<Map<String, Object>> customerList = getMapBySQL(buildSql, null);
            result.put("customers", customerList);
        }
        result.put("total", total);
        Map<String, Object> group = new HashMap<String, Object>(5);
        group.put("customer_total", customer_total);
        group.put("intention_customer_total", intention_customer_total);
        group.put("success_customer_total", success_customer_total);
        group.put("failure_customer_total", failure_customer_total);
        group.put("seller_total", seller_total);
        result.put("group", group);
        return result;
    }

    /**
     * 获取客户是否有过消费历史
     *
     * @param customerId
     * @return
     */
    public boolean hasCustomerConsumed(String customerId) {
        @SuppressWarnings("unchecked")
        List<Integer> list = executeSQLQuery("SELECT COUNT(*) FROM  finance_document_entries J WHERE J.object_id = '"
                + customerId + "'");
        return list != null && list.size() > 0 && list.get(0) > 0;
    }

    /**
     * 获取相似的客户
     *
     * @param customer
     * @return
     */
    public List<InterestedCustomers> getSimilarCustomer(
            InterestedCustomers customer) {
        Map<String, Object> params = new HashMap<String, Object>(4);

        params.put("objectName", customer.getObjectName());
        params.put("shortName", customer.getShortName());
        params.put("mobile", customer.getMobile());

        String sql = "SELECT * FROM vw_stat_effective_customer WHERE (object_name=:objectName OR short_name =:shortName OR mobile=:mobile ";
        if (!Tools.isEmpty(customer.getCertificateNo())) {
            sql += " OR certificate_no=:certificateNo";
            params.put("certificateNo", customer.getCertificateNo());
        }
        sql += " ) ";
        if (!Tools.isEmpty(customer.getObjectId())) {
            sql += " AND object_id <> :objectId";
            params.put("objectId", customer.getObjectId());
        }
        sql += " AND ISNULL(status, 0) = 1";
        return getEntityBySQL(sql, InterestedCustomers.class, params);
    }

    /**
     * 获取当前部门下，某客户未完成的销售线索
     *
     * @param customerId
     * @param departmentId
     * @return
     */

    public List<PresellVisitors> getUnfinishedSaleClueInTheSameDepartment(
            String customerId, String departmentId) {
        String sql = " SELECT v.* FROM presell_visitors v LEFT JOIN sys_users u ON v.seller_id = u.user_id WHERE visitor_id =:customerId AND v.visit_result IS NULL AND u.department=:departmentId";
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("customerId", customerId);
        params.put("departmentId", departmentId);
        return getEntityBySQL(sql, PresellVisitors.class, params);
    }

    /**
     * 获取某个客户所有未回访完成的销售线索
     *
     * @param customerId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<PresellVisitors> getAllUnfinishedSalclue(String customerId) {
        return (List<PresellVisitors>) getHibernateTemplate()
                .find("FROM PresellVisitors v  WHERE v.visitorId =? AND v.visitResult IS NULL",
                        customerId);
        // String sql =
        // "FROM PresellVisitors v  WHERE v.visitorId =? AND v.visitResult IS NULL";
        // Map<String, Object> params = new HashMap<String, Object>(2);
        // params.put("customerId", customerId);
        // return getEntityBySQL(sql, PresellVisitors.class, params);
    }

    /**
     * 外置命名查询
     *
     * @param queryName
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> getByNamedQuery(String queryName) {
        return getSessionFactory().getCurrentSession().getNamedQuery(queryName)
                .list();
    }

    @Autowired
    private InterestedCustomersService interestedCustomersService;

    //获得维系人的条件，
    private String getMaintenanceUserCondition() {
        String condition = "";
        condition = interestedCustomersService.getMaintenanceUserCondition("maintainer_id","base_tmp",true);
//        List<Map<String, Object>> list = interestedCustomersService.getAllMaintenanceUser(true);
//        if (list != null && list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                Map<String, Object> item = list.get(i);
//                if (i == list.size() - 1) {
//                    condition += " base_tmp.maintainer_id LIKE '%" + item.get("user_id") + "%' ";
//                } else {
//                    //多一个OR
//                    condition += " base_tmp.maintainer_id LIKE '%" + item.get("user_id") + "%' OR ";
//                }
//            }
//        }

        return StringUtils.isBlank(condition) ? "" : String.format(" %s", condition);
    }


    public PageModel getSellerCallBackWarning(String type, Map<String, Object> filter, String sort, int page, int perPage) {
        return this.getSellerCallBackWarning(type, filter, sort, page, perPage, false);
    }

    public PageModel getSellerCallBackWarningForTotal(String type){
        return this.getSellerCallBackWarning(type, null, null, 1, 1, true);
    }
    /**
     * 获取已交车未做回访的客户（预警统计列表）
     *
     * @param type    1：3天内；2：3天后
     * @param filter
     * @param sort
     * @param page
     * @param perPage
     * @return
     */
    private PageModel getSellerCallBackWarning(String type, Map<String, Object> filter, String sort, int page, int perPage, boolean onlyQueryTotal) {
        String baseSql = getSqlByNamedQuery("sellerCallBackWarning");
        SysUsers user = HttpSessionStore.getSessionUser();
        Map<String, Object> parm = new HashMap<String, Object>();
        String condition;
        //没有共同维系客户共享查看权限，只能看建单人是自己或销售员是自已和线索
        if (!user.hasPopedom(POPEDOM_SHARED_MAINTAINER)) {
            condition = " (base_tmp.creator = :userFullName OR base_tmp.backer=:userId )";
            parm.put("userFullName", user.getUserFullName());
            parm.put("userId", user.getUserId());
        } else {
            condition = " (base_tmp.station_id IN (:stationIds)  OR base_tmp.creator = :userFullName  OR base_tmp.backer=:userId)";
            String[] stationIds = userService.getModuleStationId(user, MODULE_EFFECTIVE_CUSTOMER);
            parm.put("stationIds", stationIds);
            parm.put("userId", user.getUserId());
            parm.put("userFullName", user.getUserFullName());
        }

        if ("1".equals(type)) {
            condition += " AND base_tmp.out_stock_day<=3";
        } else if ("2".equals(type)) {
            condition += " AND base_tmp.out_stock_day>3";
        } else {
            condition += " AND base_tmp.out_stock_day<=3";
        }
        String filterCondition = mapToFilterString(filter, "base_tmp");
        filterCondition += " AND " + condition;
        filterCondition += this.getMaintenanceUserCondition();

        String sortSql;
        if (sort != null && sort.length() > 0) {
            sortSql = " ORDER BY " + sort + " ";
        } else {
            sortSql = " ORDER BY out_stock_time DESC ";
        }
        if (page < 1) {
            page = 1;
        }

        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>();
        String countSql = "SELECT COUNT(base_tmp.object_id) as count FROM (" + baseSql + ") base_tmp WHERE 1 = 1 AND " + filterCondition;
        List<Map<String, Object>> list = getMapBySQL(countSql, parm);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).get("count").toString()));
        if(onlyQueryTotal){
            return result;
        }
        String pageSql = " OFFSET " + ((page - 1) * perPage) + " ROW FETCH NEXT " + perPage + " ROWS ONLY ";
        String buildSql = "SELECT base_tmp.* FROM (" + baseSql + ") base_tmp WHERE 1 = 1 AND " + filterCondition + sortSql + pageSql;
        result.setPage(page);
        result.setPerPage(perPage);
        result.setData(getMapBySQL(buildSql, parm));
        return result;
    }


    public PageModel getOverdueCallBackForTotal() {
        return getOverdueCallBack(null, null, 1, 1, true);
    }

    public PageModel getOverdueCallBack(Map<String, Object> filter, String sort, int page, int perPage) {
        return getOverdueCallBack(filter, sort, page, perPage, false);
    }

    private PageModel getOverdueCallBack(Map<String, Object> filter, String sort, int page, int perPage, boolean onlyQueryTotal) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String baseSql = getSqlByNamedQuery("overdueCallBack");
        String condition;
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("maintainerId", "%" + user.getUserId() + "%");
        //没有共同维系客户共享查看权限，只能看建单人是自己或销售员是自已和线索
        if (!user.hasPopedom(POPEDOM_SHARED_MAINTAINER)) {
            condition = " (base_tmp.creator = :userFullName OR base_tmp.backer=:userId )";
            parm.put("userFullName", user.getUserFullName());
            parm.put("userId", user.getUserId());
        } else {
            condition = " (base_tmp.station_id IN (:stationIds)  OR base_tmp.creator = :userFullName  OR base_tmp.backer=:userId)";
            String[] stationIds = userService.getModuleStationId(user, MODULE_EFFECTIVE_CUSTOMER);
            parm.put("stationIds", stationIds);
            parm.put("userId", user.getUserId());
            parm.put("userFullName", user.getUserFullName());
        }
        String filterCondition = mapToFilterString(filter, "base_tmp");
        filterCondition += " AND " + condition;

        String sortSql;
        if (sort != null && sort.length() > 0) {
            sortSql = " ORDER BY " + sort + " ";
        } else {
            sortSql = " ORDER BY plan_back_time DESC ";
        }
        if (page < 1) {
            page = 1;
        }

        filterCondition += this.getMaintenanceUserCondition();

        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>();
        String countSql = "SELECT COUNT(base_tmp.object_id) as count FROM (" + baseSql + ") base_tmp";
        countSql += " where 1=1 AND " + filterCondition;
        List<Map<String, Object>> list = getMapBySQL(countSql, parm);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).get("count").toString()));
        if(onlyQueryTotal){
            return result;
        }
        String pageSql = " OFFSET " + ((page - 1) * perPage) + " ROW FETCH NEXT " + perPage + " ROWS ONLY ";
        String buildSql = baseSql + " WHERE 1=1 AND " + filterCondition + sortSql + pageSql;
        result.setPage(page);
        result.setPerPage(perPage);
        result.setData(getMapBySQL(buildSql, parm));
        return result;
    }
    public PageModel getCallBackWarning(int startDay, int endDay, Map<String, Object> filter, String sort, int page, int perPage) {
        return getCallBackWarning(startDay, endDay, filter, sort, page, perPage, false);
    }
    public PageModel getCallBackWarningForTotal(int startDay, int endDay) {
        return getCallBackWarning(startDay, endDay, null, null, 1, 1, true);
    }
    /**
     * 查询超过指定天数未跟进的客户
     *
     * @param startDay     天数
     * @param endDay     天数
     * @param filter
     * @param sort
     * @param page
     * @param perPage
     * @return
     */
    private PageModel getCallBackWarning(int startDay, int endDay, Map<String, Object> filter, String sort, int page, int perPage, boolean onlyQueryTotal) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String stationId = user.getDefaulStationId();
        String baseSql = getSqlByNamedQuery("callBackWarning");
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("startDay", startDay);
        parm.put("endDay", endDay);
        String condition;
        //没有共同维系客户共享查看权限，只能看建单人是自己或销售员是自已和线索
        if (!user.hasPopedom(POPEDOM_SHARED_MAINTAINER)) {
            condition = " (base_tmp.creator = :userFullName OR base_tmp.backer=:userId )";
            parm.put("userFullName", user.getUserFullName());
            parm.put("userId", user.getUserId());
        } else {
            condition = " (base_tmp.station_id IN (:stationIds)  OR base_tmp.creator = :userFullName  OR base_tmp.backer=:userId)";
            String[] stationIds = userService.getModuleStationId(user, MODULE_EFFECTIVE_CUSTOMER);
            parm.put("stationIds", stationIds);
            parm.put("userId", user.getUserId());
            parm.put("userFullName", user.getUserFullName());
        }
        String filterCondition = mapToFilterString(filter, "base_tmp");
        filterCondition += " AND "+ condition;
        filterCondition += this.getMaintenanceUserCondition();
        String sortSql;
        if (sort != null && sort.length() > 0) {
            sortSql = " ORDER BY " + sort + " ";
        } else {
            sortSql = " ORDER BY object_name ";
        }
        if (page < 1) {
            page = 1;
        }
        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>();
        String countSql = "SELECT COUNT(base_tmp.object_id) as count FROM (" + baseSql + ") base_tmp WHERE 1 = 1 AND " + filterCondition;
        List<Map<String, Object>> list = getMapBySQL(countSql, parm);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).get("count").toString()));
        if(onlyQueryTotal){
            return result;
        }
        result.setPage(page);
        result.setPerPage(perPage);
        String pageSql = " OFFSET " + ((page - 1) * perPage) + " ROW FETCH NEXT " + perPage + " ROWS ONLY ";
        String buildSql = baseSql + " WHERE 1=1 AND " + filterCondition + sortSql + pageSql;
        logger.debug(String.format("超过指定天数未跟进的客户sql:%s", buildSql));
        result.setData(getMapBySQL(buildSql, parm));
        return result;
    }
    public PageModel getPendingCheckForNewCustomer(Map<String, Object> filter, String sort, int page, int perPage) {
        return getPendingCheckForNewCustomer(filter, sort, page, perPage, false);
    }
    public PageModel getPendingCheckForNewCustomerForTotal() {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("check_time__isnull", true);
        return getPendingCheckForNewCustomer(condition, null, 1, 1, true);
    }
    /**
     * 查询待检查新增客户，只需要判断是否拥有检查权限（10151720）
     * @param filter
     * @param sort
     * @param page
     * @param perPage
     * @return
     */
    private PageModel getPendingCheckForNewCustomer(Map<String, Object> filter, String sort, int page, int perPage, boolean onlyQueryTotal) {
        SysUsers user = HttpSessionStore.getSessionUser();
        if(!user.hasPopedom("10151720")){   //检查权限
            return new PageModel<Map<String, Object>>(new ArrayList());
        }
        String baseSql = getSqlByNamedQuery("pendingCheckForNewCustomer");
        String[] stationIds = userService.getModuleStationId(HttpSessionStore.getSessionUser(), MODULE_EFFECTIVE_CUSTOMER);
        String condition = null;
        Map<String, Object> parm = new HashMap<String, Object>();
        if (null != stationIds && stationIds.length > 0) {
            condition = "base_tmp.create_station_id IN (:stationIds)";
            parm.put("stationIds", stationIds);
        }

        String filterCondition = mapToFilterString(filter, "base_tmp");
        filterCondition += " AND " + condition;
        filterCondition += this.getMaintenanceUserCondition();
        String sortSql;
        if (sort != null && sort.length() > 0) {
            sortSql = " ORDER BY " + sort + " ";
        } else {
            sortSql = " ORDER BY create_time DESC ";
        }
        if (page < 1) {
            page = 1;
        }
        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>();
        String countSql = "SELECT COUNT(base_tmp.object_id) as count FROM (" + baseSql + ") base_tmp WHERE 1 = 1 AND " + filterCondition;
        List<Map<String, Object>> list = getMapBySQL(countSql, parm);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).get("count").toString()));
        if(onlyQueryTotal){
            return result;
        }
        String pageSql = " OFFSET " + ((page - 1) * perPage) + " ROW FETCH NEXT " + perPage + " ROWS ONLY ";
        String buildSql = baseSql + " WHERE 1=1 AND " + filterCondition + sortSql + pageSql;
        logger.debug(String.format("待检查的新增客户sql:%s", buildSql));
        result.setPage(page);
        result.setPerPage(perPage);
        result.setData(getMapBySQL(buildSql, parm));
        return result;
    }

    public PageModel getPendingCheck(Map<String, Object> filter, String sort, int page, int perPage) {
        return getPendingCheck(filter, sort, page, perPage, false);
    }
    public PageModel getPendingCheckForTotal() {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("check_time__isnull", true);
        return getPendingCheck(condition, null, 1, 1, true);
    }
    /**
     * 查询待检查的客户及其跟进信息
     *
     * @param filter
     * @param sort
     * @param page
     * @param perPage
     * @return
     */
    public PageModel getPendingCheck(Map<String, Object> filter, String sort, int page, int perPage, boolean onlyQueryTotal) {
        SysUsers user = HttpSessionStore.getSessionUser();
        if(!user.hasPopedom("10151920")){   //检查权限
            return new PageModel<Map<String, Object>>(new ArrayList());
        }
        String baseSql = getSqlByNamedQuery("pendingCheck");
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("checkerId", "%" + user.getUserId() + "%");
        String[] stationIds = userService.getModuleStationId(HttpSessionStore.getSessionUser(), MODULE_EFFECTIVE_CUSTOMER);
        String condition = null;
        if (null != stationIds && stationIds.length > 0) {
            condition = "base_tmp.station_id in (:stationIds)";
            parm.put("stationIds", stationIds);
        }

        String filterCondition = mapToFilterString(filter, "base_tmp");
        filterCondition += " AND " + condition;
        String sortSql;
        if (sort != null && sort.length() > 0) {
            sortSql = " ORDER BY " + sort + " ";
        } else {
            sortSql = " ORDER BY object_name ";
        }
        if (page < 1) {
            page = 1;
        }
        PageModel<Map<String, Object>> result = new PageModel<Map<String, Object>>();
        String countSql = "SELECT COUNT(base_tmp.object_id) as count FROM (" + baseSql + ") base_tmp WHERE 1 = 1 AND " + filterCondition;
        List<Map<String, Object>> list = getMapBySQL(countSql, parm);
        result.setTotalSize((list == null || list.size() == 0) ? 0 : Integer.valueOf(list.get(0).get("count").toString()));
        if(onlyQueryTotal){
            return result;
        }
        String pageSql = " OFFSET " + ((page - 1) * perPage) + " ROW FETCH NEXT " + perPage + " ROWS ONLY ";
        String buildSql = baseSql + " WHERE 1=1 AND " + filterCondition + sortSql + pageSql;
        logger.debug(String.format("待检查的跟进信息sql:%s", buildSql));
        result.setPage(page);
        result.setPerPage(perPage);
        result.setData(getMapBySQL(buildSql, parm));
        return result;
    }

    @Override
    public List<Map<String, Object>> getCustomerInformationWithContract(String customerId) {
        return getCustomerInformation("customerInformationWithContract", customerId);
    }

    @Override
    public List<Map<String, Object>> getCustomerInformationWithQuotation(String customerId) {
        return getCustomerInformation("customerInformationWithQuotation", customerId);
    }

    @Override
    public List<Map<String, Object>> getCustomerInformationWithCallBack(String customerId) {
        return getCustomerInformation("customerInformationWithCallBack", customerId, true);
    }

    @Override
    public List<Map<String, Object>> getCustomerInformationWithPendingCallBack1(String customerId) {
        return getCustomerInformation("customerInformationWithPendingCallBack1", customerId, true);
    }
    @Override
    public List<Map<String, Object>> getCustomerInformationWithPendingCallBack2(String customerId) {
        return getCustomerInformation("customerInformationWithPendingCallBack2", customerId, true);
    }

    @Override
    public List<Map<String, Object>> getCustomerInformationWithOverdueCallBack(String customerId) {
        return getCustomerInformation("customerInformationWithOverdueCallBack", customerId, true);
    }

    private List<Map<String, Object>> getCustomerInformation(String queryName, String customerId) {
        return getCustomerInformation(queryName, customerId, false);
    }
    private List<Map<String, Object>> getCustomerInformation(String queryName, String customerId, boolean addPopedomSharedMaintainer) {
        Query query = getSessionFactory().getCurrentSession().getNamedQuery(queryName);
        if(addPopedomSharedMaintainer){
            String baseSql = query.getQueryString();
            SysUsers user = HttpSessionStore.getSessionUser();
            Map<String, Object> parm = new HashMap<String, Object>();
            String condition;
            //没有共同维系客户共享查看权限，只能看建单人是自己或销售员是自已和线索
            if (!user.hasPopedom(POPEDOM_SHARED_MAINTAINER)) {
                condition = " (back.creator = :userFullName OR back.backer=:userId )";
                parm.put("userFullName", user.getUserFullName());
                parm.put("userId", user.getUserId());
            } else {
                condition = " (back.station_id IN (:stationIds)  OR back.creator = :userFullName  OR back.backer=:userId)";
                String[] stationIds = userService.getModuleStationId(user, MODULE_EFFECTIVE_CUSTOMER);
                parm.put("stationIds", stationIds);
                parm.put("userId", user.getUserId());
                parm.put("userFullName", user.getUserFullName());
            }
            parm.put("customerId", customerId);
            baseSql += " AND " + condition;
            return this.getMapBySQL(baseSql, parm);
        }else{
            query.setParameter("customerId", customerId);
            query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
            return query.list();
        }
    }

    @Override
    public Map<String, Object> getCustomerPurchaseSummaryById(String customerId) {
        List<Map<String, Object>> list = getCustomerInformation("customerPurchaseSummaryById", customerId);
        if (null == list || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Timestamp getCustomerLastPurchaseTimeById(String customerId) {
        List<Map<String, Object>> list = getCustomerInformation("customerLastPurchaseTimeById", customerId);
        if (null == list || list.isEmpty()) {
            return null;
        }
        Object value = list.get(0).get("lastPurchaseTime");
        if (null != value) {
            return (Timestamp) value;
        }
        return null;
    }

    @Override
    public Timestamp getCustomerLastVisitTimeById(String customerId) {
        List<Map<String, Object>> list = getCustomerInformation("customerLastVisitTimeById", customerId, true);
        if (null == list || list.isEmpty()) {
            return null;
        }
        Object value = list.get(0).get("realBackTime");
        if (null != value) {
            return (Timestamp) value;
        }
        return null;
    }

    @Override
    public Timestamp getCustomerLastVisitPlanTimeById(String customerId) {
        List<Map<String, Object>> list = getCustomerInformation("customerLastVisitPlanTimeById", customerId, true);
        if (null == list || list.isEmpty()) {
            return null;
        }
        Object value = list.get(0).get("planBackTime");
        if (null != value) {
            return (Timestamp) value;
        }
        return null;
    }

    private String getSqlByNamedQuery(String name) {
        return getCurrentSession().getNamedQuery(name).getQueryString();
    }

}
