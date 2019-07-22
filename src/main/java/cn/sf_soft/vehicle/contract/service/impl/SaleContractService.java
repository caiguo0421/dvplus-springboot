package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.basedata.dao.SysLogsDao;
import cn.sf_soft.basedata.model.SysFlags;
import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.basedata.service.SysBaseDataService;
import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.FinanceDocumentEntriesDao;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.common.gson.GsonExclutionStrategy;
import cn.sf_soft.common.gson.HibernateProxyTypeAdapter;
import cn.sf_soft.common.gson.TimestampTypeAdapter;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.util.*;
import cn.sf_soft.file.service.FileService;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.office.approval.service.ApprovalService;
import cn.sf_soft.support.*;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractDetailGroups;
import cn.sf_soft.vehicle.contract.model.VehicleSaleQuotation;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.*;
import cn.sf_soft.vehicle.customer.service.impl.CustomerHistoryService;
import cn.sf_soft.vehicle.customer.service.impl.InterestedCustomersService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: chenbiao
 * @Date: 2018/6/30 10:29
 * @Description: 销售合同主对象服务
 */
@Service("saleContractService1")
public class SaleContractService extends BaseService<VehicleSaleContracts> {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractService.class);

    private static final String MODULE_ID = "102020";

    public final String CODE_RULE_NO = "VSC_NO";

    /**
     * 销售合同-车辆保险录入部门(销售部 俱乐部)
     */
    public static final String INSURANCE_INPUT_UNIT = "INSURANCE_INPUT_UNIT";

    /**
     * 精品加价比例
     */
    public static final String VEHICLE_PRESENT_PRICE_MARKUP_RATE = "VEHICLE_PRESENT_PRICE_MARKUP_RATE";

    /**
     * 精品价格选择
     */
    public static final String VEHICLE_PRESENT_PRICE_STRUCTURE = "VEHICLE_PRESENT_PRICE_STRUCTURE";

    @Autowired
    private SysCodeRulesService sysCodeService;

    @Autowired
    private SysBaseDataService sysBaseDataService;

    /**
     * 是否需要创建保险预收款
     */
    private static final String vsc_insurance_advances_received = "vsc_insurance_advances_received";

    /**
     * 销售合同必须有意向线索的销售模式
     */
//    public static final String SALE_CONTRACT_MUST_VISITOR = "SALE_CONTRACT_MUST_VISITOR";
    /**
     * 审批条件
     */
    public static final String VEHICLE_CONTRACT_FLOW_RETURN_CONDITION = "VEHICLE_CONTRACT_FLOW_RETURN_CONDITION";

    /**
     * 销售订金的录入方式，合同录入、车辆录入
     */
    public static final String VEHICLE_DEPOSIT_INPUT_TYPE = "VEHICLE_DEPOSIT_INPUT_TYPE";

    /**
     * 选择车型的品牌范围
     */
    public static final String VEHICLE_CHOOSE_VNO_RANGE = "VEHICLE_CHOOSE_VNO_RANGE";


    /**
     * 跨站点选车
     */
    public static final String VEHICEL_OVER_STATION = "VEHICEL_OVER_STATION";

    /**
     * 车型未维护最低限价销售合同能否选择
     */
    public static final String VEHICLE_MIN_SALE_PRICE_CONTROL = "VEHICLE_MIN_SALE_PRICE_CONTROL";

    /**
     * DFS接口是否有效
     */
    public static final String DFS_INTERFACE_IS_VALID = "DFS_INTERFACE_IS_VALID";


    /**
     * 车辆销售合同费用是否单独产生单据分录
     */
    public static final String VEHICLE_CHARGE_FDE_SEPARATE = "VEHICLE_CHARGE_FDE_SEPARATE";


    /**
     * 销售合同修改签约客户是否需要控制选择范围
     */
    public static final String SELECT_CUSTOMER_CONTROL = "SELECT_CUSTOMER_CONTROL";

    private static EntityRelation entityRelation;

    @Autowired
    private BaseDaoHibernateImpl baseDao;

    @Autowired
    private SysOptionsDao sysOptionsDao;

    @Autowired
    private SysLogsDao sysLogsDao;

    @Autowired
    protected FinanceDocumentEntriesDao financeDocumentEntriesDao;// 单据分录

    @Autowired
    private CustomerHistoryService customerHistoryService;

    @Resource
    private ApprovalService approvalService;

    @Autowired
    private FileService fileService;

    @Autowired
    private VehicleSaleContractDetailService vehicleSaleContractDetailService;

    //变更的摘要
    private CompareEntity compareEntity = null;

    protected Gson gson = new GsonBuilder()
            .registerTypeAdapter(Boolean.class, new BooleanTypeAdapter())
            .registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
            .registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
            .setExclusionStrategies(new GsonExclutionStrategy()).create();


    static {
        entityRelation = new EntityRelation(VehicleSaleContracts.class, "contractNo", SaleContractService.class);
        entityRelation.addSlave("contractNo", VehicleSaleContractDetailGroupsService.class);
    }


    private static ThreadLocal<Map<String, List<Map<String, Object>>>> threadLocalVehicleMinPrice = new ThreadLocal<>();


    /**
     * 获取价格体系中的最低限价设定
     *
     * @param paramMap
     * @return
     */
    public List<Map<String, Object>> getVehicleMinPrice(Map<String, Object> paramMap) {
        String stationId = HttpSessionStore.getSessionUser().getDefaulStationId(); //默认为当前用户站点
        String signTime = new Timestamp(System.currentTimeMillis()).toString(); //默认为当前时间
        String vnoId = "";
        if (paramMap.containsKey("vnoId") && paramMap.get("vnoId") != null && StringUtils.isNotEmpty(paramMap.get("vnoId").toString())) {
            vnoId = paramMap.get("vnoId").toString();
        }

        if (paramMap.containsKey("stationId") && paramMap.get("stationId") != null && StringUtils.isNotEmpty(paramMap.get("stationId").toString())) {
            stationId = paramMap.get("stationId").toString();
        }

        if (paramMap.containsKey("signTime") && paramMap.get("signTime") != null && StringUtils.isNotEmpty(paramMap.get("signTime").toString())) {
            signTime = paramMap.get("signTime").toString();
        }

        if (StringUtils.isEmpty(vnoId)) {
            throw new ServiceException("获取最低限价出错：vnoId为空");
        }

        //增加threadLocalVehicleMinPrice缓存
        String key = String.format("%s-%s-%s", stationId, signTime, vnoId);
        if (threadLocalVehicleMinPrice.get() == null) {
            threadLocalVehicleMinPrice.set(new HashMap<String, List<Map<String, Object>>>());
        }

        if (!threadLocalVehicleMinPrice.get().containsKey(key)) {
            String sql = "{call dbo.pro_GetVehicleMinPrice(?,?,?)}";
            List<Map<String, Object>> result = baseDao.findProcedure(sql, new Object[]{stationId, vnoId, signTime});

            threadLocalVehicleMinPrice.get().put(key, result);
        }

        return threadLocalVehicleMinPrice.get().get(key);
    }


    private static ThreadLocal<Map<String, List<Map<String, Object>>>> threadLocalVehicleReferenceCost = new ThreadLocal<>();


    /**
     * 获取参考成本
     *
     * @param paramMap
     * @return
     */
    public List<Map<String, Object>> getVehicleReferenceCost(Map<String, Object> paramMap) {
        String stationId = HttpSessionStore.getSessionUser().getDefaulStationId(); //默认为当前用户站点
        String signTime = new Timestamp(System.currentTimeMillis()).toString(); //默认为当前时间
        String vnoId = "";
        if (paramMap.containsKey("vnoId") && paramMap.get("vnoId") != null && StringUtils.isNotEmpty(paramMap.get("vnoId").toString())) {
            vnoId = paramMap.get("vnoId").toString();
        }

        if (paramMap.containsKey("stationId") && paramMap.get("stationId") != null && StringUtils.isNotEmpty(paramMap.get("stationId").toString())) {
            stationId = paramMap.get("stationId").toString();
        }

        if (paramMap.containsKey("signTime") && paramMap.get("signTime") != null && StringUtils.isNotEmpty(paramMap.get("signTime").toString())) {
            signTime = paramMap.get("signTime").toString();
        }


        if (StringUtils.isEmpty(vnoId)) {
            throw new ServiceException("获取参考成本出错：vnoId为空");
        }
        //增加threadLocalVehicleReferenceCost缓存
        String key = String.format("%s-%s-%s", stationId, signTime, vnoId);
        if (threadLocalVehicleReferenceCost.get() == null) {
            threadLocalVehicleReferenceCost.set(new HashMap<String, List<Map<String, Object>>>());
        }

        if (!threadLocalVehicleReferenceCost.get().containsKey(key)) {
            String sql = "{call dbo.pro_GetVehicleReferenceCost(?,?,?)}";
            List<Map<String, Object>> result = baseDao.findProcedure(sql, new Object[]{stationId, vnoId, signTime});

            threadLocalVehicleReferenceCost.get().put(key, result);
        }

        return threadLocalVehicleReferenceCost.get().get(key);
    }


    //是否能切换VIN
    public void canChangeVIN(String contractDetailId) {
        if (StringUtils.isEmpty(contractDetailId)) {
            return;
        }
        VehicleSaleContractDetail detail = baseDao.get(VehicleSaleContractDetail.class, contractDetailId);
        vehicleSaleContractDetailService.canChangeVIN(detail);
    }


    public String canChangeVinForCharge(String contractDetailId) {
        if (StringUtils.isEmpty(contractDetailId)) {
            return "";
        }
        VehicleSaleContractDetail detail = baseDao.get(VehicleSaleContractDetail.class, contractDetailId);
        return vehicleSaleContractDetailService.canChangeVinForCharge(detail);
    }

    public String canChangeVinForConversion(String contractDetailId) {
        if (StringUtils.isEmpty(contractDetailId)) {
            return "";
        }
        VehicleSaleContractDetail detail = baseDao.get(VehicleSaleContractDetail.class, contractDetailId);
        return vehicleSaleContractDetailService.canChangeVinForConversion(detail);
    }


    //合同列表接口
    public PageModel getContractList(Map<String, Object> filter, String orderBy, String vehicleVin, Boolean chooseVin, Boolean match, String keyword, int pageNo, int pageSize) {
        long currentTs = System.currentTimeMillis();
        SysUsers user = HttpSessionStore.getSessionUser();
        String filterCondition = getFilterCondition(filter, vehicleVin, chooseVin, match);

        //客户和单号
        if (StringUtils.isNotBlank(keyword)) {
            filterCondition += " AND (a.contract_no LIKE '%" + keyword + "%' OR a.customer_name LIKE '%" + keyword + "%')";
        }

        if (orderBy == null || orderBy.length() == 0) {
            orderBy = "createTime DESC";
        }

        orderBy = orderBy.replaceAll(" DESC", " desc").replaceAll(" ASC", " asc");
        orderBy = baseDao.camel2Underline(orderBy);

        String sql = "SELECT a.*\n" +
                "FROM (\n" +
                "\tSELECT contracts.*\n" +
                "\t\t, ISNULL(contract_money, 0) + ISNULL(charge_money_tot, 0) - ISNULL(receive_money, 0) - ISNULL(receive_money_loan_charge, 0) AS remain_money\n" +
                "\t\t\n" +
                "\tFROM vw_vehicle_sale_contracts_mini contracts\n" +
                ") a ";

        //是否开启东风接口
        boolean m_bDfsValid = "有效".equals(sysOptionsDao.getOptionForString(DFS_INTERFACE_IS_VALID, user.getDefaulStationId()));

        sql += " WHERE " + filterCondition + " ORDER BY " + orderBy;

        PageModel<Map<String, Object>> pageModel = baseDao.findBySql(sql, VehicleSaleContracts.class, pageNo, pageSize);
        List<Map<String, Object>> listData = pageModel.getData();
//        logger.debug(String.format("getContractList的sql：%s", sql));
//        logger.debug(String.format("getContractList的查询消耗 %s ms", (System.currentTimeMillis() - currentTs)));
//        currentTs = System.currentTimeMillis();
//        增加计算字段
        if (listData != null && listData.size() > 0) {
            for (Map<String, Object> item : listData) {
                computeContractExtraDataForList(item, m_bDfsValid);

            }
        }
//        logger.debug(String.format("getContractList的computeContractExtraDataForList %s ms", (System.currentTimeMillis() - currentTs)));
        String description = String.format("查询合同列表：%s", (System.currentTimeMillis() - currentTs));
        this.addSysLog("操作耗时", description);
        return pageModel;
    }


    //查询合计
    public Map<String, Object> getContractTotal(Map<String, Object> filter, String vehicleVin, Boolean chooseVin, Boolean match) {
        long currentTs = System.currentTimeMillis();
        String filterCondition = getFilterCondition(filter, vehicleVin, chooseVin, match);

        String sql = "SELECT SUM(contract_quantity) AS contract_quantity,\n" + " SUM(arrived_quantity) AS arrived_quantity,\n" + " SUM(allotted_quantity) AS allotted_quantity,\n" + " SUM(contract_quantity - arrived_quantity) AS available_quantity,\n" + " SUM(ISNULL(contract_money, 0) + ISNULL(charge_money_tot, 0)) AS contract_money,\n" + " SUM(ISNULL(contract_money, 0) + ISNULL(charge_money_tot, 0) - ISNULL(loan_amount_totv,0) + ISNULL(first_pay_totc,0)) AS receivable_money,\n" + " SUM(ISNULL(receive_money, 0) + ISNULL(receive_money_loan_charge, 0)) AS receive_money\n" + " FROM ( SELECT contracts.*,\n" + "  (STUFF(( SELECT ',' + vehicle_vin FROM  vehicle_sale_contract_detail WHERE contract_no = contracts.contract_no FOR XML PATH('')), 1, 1, '')) AS vins,\n" + "  (STUFF(( SELECT ',' + vehicle_vno FROM  vehicle_sale_contract_detail WHERE contract_no = contracts.contract_no FOR XML PATH('')), 1, 1, '')) AS vnos,\n" + "  (ISNULL(contract_money, 0) + ISNULL(charge_money_tot, 0) - ISNULL(receive_money, 0) - ISNULL(receive_money_loan_charge, 0) ) AS remain_money,\n" + "  (SELECT ISNULL(COUNT(1),0) FROM vehicle_sale_contract_detail detail WHERE vehicle_vin IS NOT NULL AND detail.contract_no = contracts.contract_no) AS allotted_quantity\n" + " FROM vw_vehicle_sale_contracts contracts ) a WHERE " + filterCondition;
        List<Map<String, Object>> result = baseDao.getMapBySQL(sql, null);
        if (result == null || result.size() == 0) {
            return null;
        }
        logger.debug(String.format("getContractTotal %s ms", (System.currentTimeMillis() - currentTs)));
        return result.get(0);
    }


    /**
     * 计算合同的附加数据数据,已作废，为了节省时间，直接sql中查询
     *
     * @param itemMap
     */
    private void computeContractExtraDataForList(Map itemMap, boolean m_bDfsValid) {
        if (itemMap == null) {
            return;
        }

        SysUsers user = HttpSessionStore.getSessionUser();
        //是否开启东风接口
        double contract_money_total = 0.00D; //合同总额（包括应收预算费用）
        double receive_money_total = 0.00D; //合同已收款（包括应收预算费用）
        double due_receive_money = 0.00D; //应收金额
        double rest_due_receive_money = 0.00D;//剩余应收
//        double can_use_money = 0.00D; //可用金额
        if (m_bDfsValid) {
            contract_money_total = getDoubleValByKey(itemMap, "contractMoney");
            receive_money_total = getDoubleValByKey(itemMap, "receiveMoney");
            due_receive_money = contract_money_total - getDoubleValByKey(itemMap, "loanAmountTotv") + getDoubleValByKey(itemMap, "firstPayTotc");
        } else {
            contract_money_total = getDoubleValByKey(itemMap, "contractMoney") + getDoubleValByKey(itemMap, "chargeMoneyTot");
            receive_money_total = getDoubleValByKey(itemMap, "receiveMoney") + getDoubleValByKey(itemMap, "receiveMoneyLoanCharge");
            due_receive_money = contract_money_total - getDoubleValByKey(itemMap, "loanAmountTot");
        }
        rest_due_receive_money = (due_receive_money - receive_money_total < 0.00D ? 0.00D : (due_receive_money - receive_money_total));
//        if (due_receive_money - receive_money_total < 0.00D) {
//            can_use_money = due_receive_money - getDoubleValByKey(itemMap, "outMoney");
//        } else {
//            can_use_money = receive_money_total - getDoubleValByKey(itemMap, "outMoney");
//        }
        itemMap.put("contractMoneyTotal", contract_money_total);
        itemMap.put("receiveMoneyTotal", receive_money_total);
        itemMap.put("dueReceiveMoney", due_receive_money);
        itemMap.put("restDueReceiveMoney", rest_due_receive_money);
//        itemMap.put("canUseMoney", can_use_money);

    }


    private String getFilterCondition(Map<String, Object> filter, String vehicleVin, Boolean chooseVin, Boolean match) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String filterCondition = " 1 = 1 ";
        if (user.getPopedomIds() != null && user.getPopedomIds().contains(PopedomConst.VehicleContracts.CROSS_DEPT_VIEW)) {
            //跨部门查看权限，能查看所有数据
            filterCondition = " 1 = 1 ";

        } else if (user.getPopedomIds() != null && user.getPopedomIds().contains(PopedomConst.VehicleContracts.WITHIN_DEPT_VIEW)) {
            //部门查看权限,仅能查看本单位数据
            filterCondition = String.format(" a.department_id = '%s'", user.getDepartment());

        } else {
            //都没有就只能查看自己创建、审批过的合同
            filterCondition = " (a.user_id = '" + user.getUserId() + "' OR a.approver_id LIKE '%" + user.getUserId() + "%'  OR a.document_no IN (SELECT DISTINCT m.document_no FROM  office_approve_documents m LEFT JOIN office_approve_documents_points n ON m.document_id = n.document_id WHERE m.module_id = '102020' AND n.approver_id LIKE '%" + user.getUserId() + "%'))";
        }

        //站点权限
        String stationIds = user.getModuleStations().get(MODULE_ID);
        if (StringUtils.isNotEmpty(stationIds)) {
            String[] stationIdArray = stationIds.split(",");
            filterCondition += " AND " + "a.station_id in ('" + StringUtils.join(stationIdArray, "','") + "')";
        }

        //条件vehicleVin
        if (StringUtils.isNotBlank(vehicleVin)) {
            filterCondition += " AND a.contract_no IN (SELECT contract_no FROM vehicle_sale_contract_detail WHERE vehicle_vin LIKE '%" + vehicleVin + "%')";
        }


        //添加 未选择VIN ，已选择VIN
        if (chooseVin != null) {
            if (chooseVin) {
                filterCondition += " AND a.contract_no IN (SELECT contract_no FROM vehicle_sale_contract_detail WHERE vehicle_vin IS NOT NULL AND vehicle_vin <> '')";
            } else {
                filterCondition += " AND a.contract_no IN (SELECT contract_no FROM vehicle_sale_contract_detail WHERE (vehicle_vin IS NULL OR vehicle_vin =''))";
            }
        }

        //配车状态，已配车，未配车
        if (match != null) {
            if (match) {
                filterCondition += " AND a.match_quantity>0 ";
            } else {
                filterCondition += " AND a.match_quantity=0 ";
            }
        }

        if (filter != null && filter.size() > 0) {
            Map<String, Object> underLineFilter = new HashMap<String, Object>(filter.size());
            for (String key : filter.keySet()) {
                underLineFilter.put(baseDao.camel2Underline(key), filter.get(key));
            }
            String mapCondition = baseDao.mapToFilterString(underLineFilter, "a");
            if (StringUtils.isNotEmpty(mapCondition)) {
                filterCondition = String.format(" %s AND %s", filterCondition, mapCondition);
            }
        }

        return filterCondition;
    }


    //合同提交接口
    public Map<String, List<Object>> submitContract(String contractNo) {
        long time1 = System.currentTimeMillis();
        SysUsers user = HttpSessionStore.getSessionUser();
        Timestamp now = new Timestamp(new Date().getTime());
        VehicleSaleContracts contract = baseDao.get(VehicleSaleContracts.class, contractNo);
        if (contract == null) {
            throw new ServiceException("未找到该合同");
        }
        contract.setModifyTime(new Timestamp(new Date().getTime()));
        contract.setModifier(user.getUserFullName());
//        contract.setSubmitStationId(user.getDefaulStationId());
//        contract.setSubmitStationName(user.getStationName());
        SysStations stations = baseDao.get(SysStations.class, contract.getSubmitStationId());
        //增加stationName
        if (stations != null) {
            contract.setSubmitStationName(stations.getStationName());
        }
        contract.setSubmitTime(now);

        //修改ApproveStatus
        List<VehicleSaleContractDetailGroups> detailGroupsList = (List<VehicleSaleContractDetailGroups>) baseDao.findByHql("FROM VehicleSaleContractDetailGroups where contractNo = ?", contractNo);
        if (detailGroupsList != null && detailGroupsList.size() > 0) {
            for (VehicleSaleContractDetailGroups detailGroups : detailGroupsList) {
                List<VehicleSaleContractDetail> detailList = (List<VehicleSaleContractDetail>) baseDao.findByHql("FROM VehicleSaleContractDetail where groupId = ?", detailGroups.getGroupId());
                if (detailList != null && detailList.size() > 0) {
                    for (VehicleSaleContractDetail detail : detailList) {
                        if (Tools.toShort(detail.getApproveStatus()) == 0) {
                            detail.setApproveStatus((short) 20); //待审批	20
                            detail.setStatus((short) 20);
                        }
                    }
                }
            }
        }
        //调用提交接口
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Constant.OSType osType = Constant.OSType.MOBILE;
        if ("PC".equals(HttpSessionStore.getSessionOs())) {
            osType = Constant.OSType.PC;
        }
        ApproveResult approveResult = approvalService.submitRecord(user, contract.getDocumentNo(), "102020", true, "", sdf.format(now), osType);

        baseDao.flush();//flush数据，保证接口返回值是最新的
        Map<String, List<Object>> result = new HashMap<>(1);
        if ("PC".equals(HttpSessionStore.getSessionOs())) {
            //pc端的话，只返回主表数据
            List item = baseDao.getMapBySQL("SELECT * FROM vehicle_sale_contracts where contract_no = '" + contract.getContractNo() + "'", null);
            result.put("vehicle_sale_contracts", item);
        } else {
            result = convertReturnData(contract.getContractNo());
        }

        long time2 = System.currentTimeMillis();
        String description = String.format("%s:%d 合同号：%s", "提交", (time2 - time1), contract.getContractNo());
        this.addSysLog("操作耗时", description);

        return result;
    }

    //初始值
    public Map<String, Object> getInitData() {
        Map<String, Object> initData = new HashMap<>();
        initData.put("sale_mode", baseDao.findByHql("FROM SysFlags where fieldNo=?", "sale_mode"));
        initData.put("loan_mode", baseDao.findByHql("FROM SysFlags where fieldNo=?", "loan_mode"));
        //slc_loan_type 消贷预算单 - 贷款方式
        initData.put("slc_loan_type", baseDao.findByHql("FROM SysFlags where fieldNo=?", new Object[]{"slc_loan_type"}));
        initData.put("loan_type", baseDao.findByHql("FROM SysFlags where fieldNo=?", new Object[]{"loan_type"}));
        initData.put("pay_type", baseDao.findByHql("FROM SysFlags where fieldNo=?", new Object[]{"pay_type"}));
        initData.put("budget_status", baseDao.findByHql("FROM SysFlags where fieldNo=?", new Object[]{"budget_status"}));
        initData.put("slc_rate_type", baseDao.findByHql("FROM SysFlags where fieldNo=?", new Object[]{"slc_rate_type"}));
        initData.put("confirm_status", baseDao.findByHql("FROM SysFlags where fieldNo=?", new Object[]{"confirm_status"}));
        //增加pay_mode
        initData.put("pay_mode", baseDao.findByHql("FROM SysFlags where fieldNo=?", new Object[]{"vs_pay_mode"}));
        //实物状态
        initData.put("entity_status", baseDao.findByHql("FROM SysFlags where fieldNo=?", new Object[]{"entity_status"}));
        //销售状态
        initData.put("sale_status", baseDao.findByHql("FROM SysFlags where fieldNo=?", new Object[]{"sale_status"}));
        //合同状态
        initData.put("vc_contract_status", baseDao.findByHql("FROM SysFlags where fieldNo=?", new Object[]{"vc_contract_status"}));

        Map<String, Object> sysOptions = new HashMap<>(3);
        String val = getSysOptionValue(VEHICLE_MIN_SALE_PRICE_CONTROL);
        sysOptions.put(VEHICLE_MIN_SALE_PRICE_CONTROL, val);
        sysOptions.put(INSURANCE_INPUT_UNIT, getSysOptionValue(INSURANCE_INPUT_UNIT));
        sysOptions.put(DFS_INTERFACE_IS_VALID, getSysOptionValue(DFS_INTERFACE_IS_VALID));
        sysOptions.put(VEHICLE_DEPOSIT_INPUT_TYPE, getSysOptionValue(VEHICLE_DEPOSIT_INPUT_TYPE));
//        sysOptions.put(SALE_CONTRACT_MUST_VISITOR, getSysOptionValue(SALE_CONTRACT_MUST_VISITOR));

        initData.put("sys_options", sysOptions);

        SysUsers user = HttpSessionStore.getSessionUser();
        Map<String, Object> baseOhters = new HashMap<>(4);
        baseOhters.put("card_type", sysBaseDataService.getBaseOthers("card_type", user.getDefaulStationId()));
        baseOhters.put("use_property", sysBaseDataService.getBaseOthers("use_property", user.getDefaulStationId()));
        baseOhters.put("oil_notice", sysBaseDataService.getBaseOthers("oil_notice", user.getDefaulStationId()));
        baseOhters.put("ep_notice", sysBaseDataService.getBaseOthers("ep_notice", user.getDefaulStationId()));
        //交车地点
        baseOhters.put("DELIVER_ADDRESS", sysBaseDataService.getBaseOthers("DELIVER_ADDRESS", user.getDefaulStationId()));
        //保险代购(年)
        baseOhters.put("vehicle_insurance_year", sysBaseDataService.getBaseOthers("vehicle_insurance_year", user.getDefaulStationId()));

        initData.put("base_others", baseOhters);

        return initData;
    }

    //签约客户
    public PageModel getSignedCustomers(String keyword, Map<String, Object> filter, int pageNo, int pageSize) {
        SysUsers user = HttpSessionStore.getSessionUser();

        if (filter == null) {
            filter = new HashMap<>(1);
        }

//        String sql = "SELECT * FROM    (SELECT  a.*, b.meaning AS object_nature_meaning, d.*, cms.manager_id,\n" + "                e.meaning AS customer_type_meaning, h.user_no AS manager_no, h.user_name AS manager_name, i.user_no AS maintenance_no,\n" + "                i.user_name AS maintenance_name, ISNULL(j.meaning, '待认领') AS maintenance_status_meaning, c.real_amount,\n" + "                c.valid_amount, ( SELECT STUFF(( SELECT  ',' + cmw.user_id FROM base_related_object_maintenace AS cmw\n" + "                 WHERE   a.object_id = cmw.object_id AND cmw.user_id IS NOT NULL FOR XML PATH('') ), 1, 1, '') ) AS maintenace_id,\n" + "                (SELECT STUFF((SELECT   ',' + bmw.user_name FROM base_related_object_maintenace AS cmw \n" + "                LEFT JOIN dbo.sys_users bmw ON cmw.user_id = bmw.user_id WHERE a.object_id = cmw.object_id AND \n" + "                cmw.user_id IS NOT NULL FOR XML PATH('')), 1, 1, '')) AS workgroup_name,l.meaning AS part_sale_type_meaning,\n" + "                ( a.province + '-' + a.city + '-' + a.area ) AS province_city_area\n" + "            FROM    base_related_objects AS a LEFT JOIN dbo.customer_extension_info d ON a.object_id = d.customer_id\n" + "            LEFT JOIN dbo.customers_manager_setting_details cmsd ON cmsd.maintenance_id = d.maintenance_id\n" + "            LEFT JOIN dbo.customers_manager_settings cms ON cms.cms_id = cmsd.cms_id\n" + "            LEFT JOIN ( SELECT  code , meaning FROM sys_flags WHERE field_no = 'visitor_type' ) e ON a.customer_type = e.code\n" + "            LEFT JOIN ( SELECT  user_id , user_name , user_no FROM dbo.sys_users ) h ON h.user_id = cms.manager_id\n" + "            LEFT JOIN ( SELECT  user_id , user_name , user_no FROM dbo.sys_users ) i ON i.user_id = d.maintenance_id\n" + "            LEFT JOIN ( SELECT  code , meaning FROM sys_flags WHERE field_no = 'maintenance_status' ) j ON d.maintenance_status = j.code\n" + "            LEFT JOIN ( SELECT  code , meaning FROM sys_flags WHERE field_no = 'object_nature' ) AS b ON a.object_nature = b.code\n" + "            LEFT JOIN ( SELECT  code , meaning FROM sys_flags WHERE field_no = 'sale_type' ) AS l ON a.part_sale_type = l.code\n" + "            LEFT JOIN ( SELECT  object_id, SUM(real_amount) AS real_amount, SUM(valid_amount) AS valid_amount FROM vw_finance_credit_contracts\n" + "            WHERE   status IN ( 40, 85 ) GROUP BY object_id HAVING  SUM(real_amount) + SUM(valid_amount) <> 0 ) AS c ON a.object_id = c.object_id\n" + "            WHERE   status = 1) a WHERE   1 = 1 ";
        String sql = "SELECT * FROM    (SELECT  a.*, b.meaning AS object_nature_meaning, d.*, cms.manager_id,\n" +
                "                e.meaning AS customer_type_meaning, h.user_no AS manager_no, h.user_name AS manager_name, i.user_no AS maintenance_no,\n" +
                "                i.user_name AS maintenance_name, ISNULL(j.meaning, '待认领') AS maintenance_status_meaning, c.real_amount,\n" +
                "                c.valid_amount, a.maintainer_id AS maintenace_id,\n" +
                "                a.maintainer_name AS workgroup_name,l.meaning AS part_sale_type_meaning,\n" +
                "                ( a.province + '-' + a.city + '-' + a.area ) AS province_city_area\n" +
                "            FROM    base_related_objects AS a LEFT JOIN dbo.customer_extension_info d ON a.object_id = d.customer_id\n" +
                "            LEFT JOIN dbo.customers_manager_setting_details cmsd ON cmsd.maintenance_id = d.maintenance_id\n" +
                "            LEFT JOIN dbo.customers_manager_settings cms ON cms.cms_id = cmsd.cms_id\n" +
                "            LEFT JOIN ( SELECT  code , meaning FROM sys_flags WHERE field_no = 'visitor_type' ) e ON a.customer_type = e.code\n" +
                "            LEFT JOIN ( SELECT  user_id , user_name , user_no FROM dbo.sys_users ) h ON h.user_id = cms.manager_id\n" +
                "            LEFT JOIN ( SELECT  user_id , user_name , user_no FROM dbo.sys_users ) i ON i.user_id = d.maintenance_id\n" +
                "            LEFT JOIN ( SELECT  code , meaning FROM sys_flags WHERE field_no = 'maintenance_status' ) j ON d.maintenance_status = j.code\n" +
                "            LEFT JOIN ( SELECT  code , meaning FROM sys_flags WHERE field_no = 'object_nature' ) AS b ON a.object_nature = b.code\n" +
                "            LEFT JOIN ( SELECT  code , meaning FROM sys_flags WHERE field_no = 'sale_type' ) AS l ON a.part_sale_type = l.code\n" +
                "            LEFT JOIN ( SELECT  object_id, SUM(real_amount) AS real_amount, SUM(valid_amount) AS valid_amount FROM vw_finance_credit_contracts\n" +
                "            WHERE   status IN ( 40, 85 ) GROUP BY object_id HAVING  SUM(real_amount) + SUM(valid_amount) <> 0 ) AS c ON a.object_id = c.object_id\n" +
                "            WHERE   status = 1) a WHERE   1 = 1 ";
        Map<String, Object> params = new HashMap<>(4);

//        String stationId = user.getDefaulStationId();
//        if (filter.get("stationId") != null && StringUtils.isNotBlank((String) filter.get("stationId"))) {
//            stationId = (String) filter.get("stationId");
//        }
//        sql += " AND a.station_id LIKE :stationId";
//        params.put("stationId", "%" + stationId + "%");
        List<String> stationIds = user.getRuledStationIds();
        String stationSql = " AND ( a.station_id  IS NULL";
        for (String stationId : stationIds) {
            stationSql += " OR a.station_id LIKE '%" + stationId + "%' ";
        }
        stationSql += ") ";
        sql = sql + stationSql;

        if (StringUtils.isNotBlank(keyword)) {
            sql += " AND (a.object_name LIKE :keyword OR a.mobile LIKE :keyword)";
            params.put("keyword", "%" + keyword + "%");
        } else {
            sql += " AND a.object_kind &1=1 AND a.object_type &2=2 ";
        }

        if ("是".equals(getSysOptionValue(SELECT_CUSTOMER_CONTROL, user.getDefaulStationId()))) {
            sql += " AND ( a.maintenace_id LIKE :sellerId OR a.maintenace_id LIKE :creatorId OR a.maintenace_id IS NULL)";
            String sellerId = filter.get("sellId") + "";
            //为如果销售合同的销售员ID为空则默认当前用户ID，如果不为空则取销售员ID
            if (StringUtils.isBlank(sellerId)) {
                sellerId = user.getUserId();
            }
            String creatorId = user.getUserId();

            //如果创建人ID为空则默认当前用户，否则取销售员ID
            String creator = filter.get("creator") + "";
            String creatorSql = "SELECT a.user_id FROM (SELECT *,user_name+'('+user_no+')' AS user_full_name FROM dbo.sys_users ) a WHERE a.user_full_name = :creator";
            Map<String, Object> creatorMap = new HashMap<>(1);
            creatorMap.put("creator", creator);
            List<Map<String, Object>> res = baseDao.getMapBySQL(creatorSql, creatorMap);
            if (res != null && res.size() > 0) {
                String userId = (String) res.get(0).get("user_id");
                if (StringUtils.isNotBlank(userId)) {
                    creatorId = userId;
                }
            }
            params.put("sellerId", "%" + sellerId + "%");
            params.put("creatorId", "%" + creatorId + "%");
        }

        PageModel result = baseDao.listInSql(sql, null, pageNo, pageSize, params);
        return result;
    }

    //线索客户
    public PageModel getClueCustomers(String keyword, Boolean queryDefault, int pageNo, int pageSize) {
//        String sql = "SELECT a.intent_level,a.visitor_count,a.purpose_quantity,a.visitor_no, a.visitor_id, a.vno_id, a.visit_time,\n" +
//                "\t\t a.visit_addr, a.visit_way, a.visitor_level,\n" +
//                "        c.object_id, c.object_no, c.object_name,c.short_name, c.sex, e.education,a.short_name_vno,\n" +
//                "        e.occupation, c.province, c.city, c.area, c.birthday,a.vehicle_strain,\n" +
//                "        c.certificate_type, c.certificate_no, a.visitor_income, c.mobile,\n" +
//                "        c.phone, c.linkman, c.address, c.postalcode, c.email, c.object_nature,\n" +
//                "        a.know_way, a.introducer, a.work_company, a.back_way, a.back_place,\n" +
//                "        a.back_time, a.vehicle_vno, a.vehicle_name, a.vehicle_color,\n" +
//                "        a.vehicle_price, a.visitor_qq, a.plan_purchase_time,\n" +
//                "        a.attention_emphases, a.purchase_use, a.try_drive_flag, a.seller,\n" +
//                "        a.comment, a.first_talk_comment, a.plan_back_time, a.final_result,\n" +
//                "        a.reason, a.self_opinion, a.other_opinion, a.unit_no, a.unit_name,\n" +
//                "        a.creator, a.create_time, a.modifier, a.modify_time, a.visit_result,\n" +
//                "        c.profession, a.visitor_msn, c.introducer_id, c.introducer_name,\n" +
//                "        a.introducer_address, a.emphasis, a.vehicle_kind, a.distance,\n" +
//                "        a.fact_load, a.tonnage, a.seller_id, a.vehicle_sales_code,\n" +
//                "        a.delivery_locus, a.buy_type, a.used_flag, a.subject_matter,\n" +
//                "        a.start_point, a.ways_point, a.end_point, a.transport_routes,\n" +
//                "        b.meaning AS buy_type_meaning, c.related_object_id,\n" +
//                "        d.meaning AS vehicle_kind_meaning\n" +
//                "FROM    presell_visitors a\n" +
//                "LEFT JOIN ( SELECT  code, meaning FROM sys_flags WHERE field_no = 'vs_buy_type' ) b ON a.buy_type = b.code\n" +
//                "LEFT JOIN dbo.vw_interested_customers c ON a.visitor_id = c.object_id\n" +
//                "LEFT JOIN dbo.base_related_objects e ON c.related_object_id = e.object_id\n" +
//                "LEFT JOIN sys_flags AS d ON d.field_no = 'vehicle_kind'  AND a.vehicle_kind = d.code\n" +
//                "WHERE   visit_result IS NULL \n" +
//                "        AND ISNULL(a.used_flag, 0) = 0\n" +
//                "        AND ISNULL(c.status, 0) = 1 ";
        //修改sql返回vw_interested_customers的所有值
        String sql = "SELECT  a.intent_level,a.visitor_count,a.purpose_quantity,a.visitor_no, a.visitor_id, a.vno_id, a.visit_time,\n" +
                "\t\ta.visit_addr, a.visit_way, a.visitor_level,\n" +
                "\t\ta.know_way, a.introducer, a.work_company, a.back_way, a.back_place,\n" +
                "        a.back_time, a.vehicle_vno, a.vehicle_name, a.vehicle_color,\n" +
                "        a.vehicle_price, a.visitor_qq, a.plan_purchase_time,\n" +
                "        a.attention_emphases, a.purchase_use, a.try_drive_flag, a.seller,\n" +
                "        a.comment, a.first_talk_comment, a.plan_back_time, a.final_result,\n" +
                "        a.reason, a.self_opinion, a.other_opinion, a.unit_no, a.unit_name,\n" +
                "        a.creator, a.create_time, a.modifier, a.modify_time, a.visit_result,\n" +
                "\t\ta.introducer_address, a.emphasis, a.vehicle_kind, a.distance,\n" +
                "        a.fact_load, a.tonnage, a.seller_id, a.vehicle_sales_code,\n" +
                "        a.delivery_locus, a.buy_type, a.used_flag, a.subject_matter,\n" +
                "        a.start_point, a.ways_point, a.end_point, a.transport_routes,\n" +
                "\t\tc.*,\n" +
                "\t\te.occupation,e.education,\n" +
                "        b.meaning AS buy_type_meaning, c.related_object_id,\n" +
                "        d.meaning AS vehicle_kind_meaning\n" +
                "FROM    presell_visitors a\n" +
                "LEFT JOIN ( SELECT  code, meaning FROM sys_flags WHERE field_no = 'vs_buy_type' ) b ON a.buy_type = b.code\n" +
                "LEFT JOIN dbo.vw_interested_customers c ON a.visitor_id = c.object_id\n" +
                "LEFT JOIN dbo.base_related_objects e ON c.related_object_id = e.object_id\n" +
                "LEFT JOIN sys_flags AS d ON d.field_no = 'vehicle_kind'  AND a.vehicle_kind = d.code\n" +
                "WHERE   visit_result IS NULL \n" +
                "        AND ISNULL(a.used_flag, 0) = 0\n" +
                "        AND ISNULL(c.status, 0) = 1 ";
        String countSql = "SELECT count(*)\n" +
                "FROM    presell_visitors a\n" +
                "LEFT JOIN ( SELECT  code, meaning FROM sys_flags WHERE field_no = 'vs_buy_type' ) b ON a.buy_type = b.code\n" +
                "LEFT JOIN dbo.vw_interested_customers c ON a.visitor_id = c.object_id\n" +
                "LEFT JOIN dbo.base_related_objects e ON c.related_object_id = e.object_id\n" +
                "LEFT JOIN sys_flags AS d ON d.field_no = 'vehicle_kind'  AND a.vehicle_kind = d.code\n" +
                "WHERE   visit_result IS NULL \n" +
                "        AND ISNULL(a.used_flag, 0) = 0\n" +
                "        AND ISNULL(c.status, 0) = 1";
        Map<String, Object> params = new HashMap<>(4);

        String condition = "";
        if (StringUtils.isNotBlank(keyword)) {
            condition += " AND (c.object_name LIKE :keyword OR c.mobile LIKE :keyword)";
            params.put("keyword", "%" + keyword + "%");
        }
        SysUsers user = HttpSessionStore.getSessionUser();
        //如果无跨级查看权限(权限ID为00101010)的
        if (!user.hasPopedom("00101010")) {
            condition += " AND (a.creator = :creator OR a.seller_id=:sellerId)";
            params.put("creator", user.getUserFullName());
            params.put("sellerId", user.getUserId());
        }
        if (null != queryDefault && queryDefault) {
            condition += " AND Datediff(DAY, isnull(a.modify_time,a.create_time), GETDATE()) < 30";
        }
        PageModel result = baseDao.listInSql(sql + condition, countSql + condition, pageNo, pageSize, params);
        return result;
    }

    //车型选择
    public PageModel getVehicleModel(String keyword, Map<String, Object> filter, String sort, int pageNo, int pageSize) {

        if (sort == null || sort.length() == 0) {
            sort = " ORDER BY vehicle_vno ASC";
        } else {
            sort = " ORDER BY " + sort + " ";
        }

        String filterCondition = baseDao.mapToFilterString(filter, "base_tmp");
        String keywordCondition;
        Map<String, Object> params = new HashMap<>(2);
        if (keyword != null && keyword.length() > 0) {
//            keywordCondition = " (vehicle_name LIKE :keyword OR vehicle_vno LIKE :keyword OR vehicle_brand LIKE :keyword )";
            //只匹配车型
            keywordCondition = " (vehicle_vno LIKE :keyword)";
            params.put("keyword", "%" + keyword + "%");
        } else {
            keywordCondition = " ( 1 = 1 ) ";
        }

        String baseSql = "SELECT * FROM (\n" +
                "SELECT a.*,a.product_model AS vehicle_vno, b.common_name AS vehicle_name, b.common_no, c.common_name AS vehicleStrain,\n" +
                "d.common_name AS vehicle_brand, e.meaning AS vehicle_kind_meaning,\n" +
                "ISNULL(f.price_purchase, a.price_purchase) AS price_purchase_station,\n" +
                "ISNULL(f.purchase_discount_amount, a.purchase_discount_amount) AS purchase_discount_amount_station,\n" +
                "ISNULL(f.purchase_other_amount, a.purchase_other_amount) AS purchase_other_amount_station,\n" +
                "ISNULL(f.profit_min, a.profit_min) AS profit_min_station,\n" +
                "ISNULL(f.price_sale, a.price_sale) AS price_sale_station,\n" +
                "ISNULL(f.price_sale_big_customer,a.price_sale_big_customer) AS price_sale_big_customer_station,\n" +
                "ISNULL(f.price_sale_terminal,a.price_sale_terminal) AS price_sale_terminal_station,\n" +
                "ISNULL(f.price_sale_sec_lvl,a.price_sale_sec_lvl) AS price_sale_sec_lvl_station,\n" +
                "d.common_name + '_' + c.common_name + '_' + b.common_name + '_' + a.product_model AS full_name, \n" +
                "a.product_no AS full_no, f.station_id AS station_id_station\n" +
                "FROM base_vehicle_model_catalog AS a\n" +
                "LEFT JOIN base_vehicle_name AS b ON a.vehicle_name_id = b.self_id AND b.common_type = '车辆名称'\n" +
                "LEFT JOIN base_vehicle_name AS c ON b.parent_id = c.self_id AND c.common_type = '车辆品系'\n" +
                "LEFT JOIN base_vehicle_name AS d ON c.parent_id = d.self_id AND d.common_type = '车辆品牌'\n" +
                "LEFT JOIN sys_flags AS e ON e.field_no = 'vehicle_kind' AND a.vehicle_kind = e.code\n" +
                "LEFT JOIN base_vehicle_model_catalog_price AS f ON a.self_id = f.parent_id AND\n" +
                "f.station_id = :stationId) AS base_tmp \n" +
                "WHERE status = '有效' AND ISNULL(vehicle_name,'')<>'' AND (is_sellable = 1)";  //选择车型时，过滤掉车辆名称为空的数据 -20190304
        //可选车型，需要受车型目录中的“本公司可售”条件控制
        //"WHERE status = '有效' AND (is_sellable = 1 OR (base_tmp.self_id IN (SELECT vno_id FROM dbo.vehicle_stocks WHERE status=0)))";
        SysUsers user = HttpSessionStore.getSessionUser();
        params.put("stationId", user.getDefaulStationId());
        String chooseVnoRange = getSysOptionValue(VEHICLE_CHOOSE_VNO_RANGE);
        if (StringUtils.isNotBlank(chooseVnoRange)) {
            String[] vnoRanges = StringUtils.split(",");
            if (vnoRanges != null && vnoRanges.length > 0) {
                baseSql += " AND vehicle_brand IN (:vnoRanges)";
                params.put("vnoRanges", vnoRanges);
            }
        }

        String sql = baseSql + " AND " + keywordCondition + " AND " + filterCondition;
        //countSql中不能有orderBy
        String countSql = String.format("SELECT COUNT(*) AS _row_conunt_ FROM (%s) AS _count_sql_", sql);
        sql += sort;

        logger.debug("getVehicleModel的sql:" + sql);
        logger.debug("getVehicleModel的countSql:" + countSql);

        PageModel result = baseDao.listInSql(sql, countSql, pageNo, pageSize, params);
        return result;
    }

    public PageModel getOptionalVehicles(Map<String, Object> filterMap, String keyword, List<String> stationIds, int pageNo, int pageSize) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String sql = "SELECT * FROM (SELECT a.*, b.warehouse_type,ISNULL(e.price_purchase,d.price_purchase) \n" +
                "AS price_purchase,ISNULL(e.purchase_discount_ratio,d.purchase_discount_ratio) AS purchase_discount_ratio,\n" +
                "ISNULL(e.purchase_discount_amount,d.purchase_discount_amount) AS purchase_discount_amount,\n" +
                "ISNULL(e.purchase_other_amount,d.purchase_other_amount) AS purchase_other_amount,\n" +
                "ISNULL(e.price_sale,d.price_sale) AS price_sale,ISNULL(e.profit_min,d.profit_min) AS profit_min,\n" +
                "f.contract_detail_id AS sale_contract_detail_id,g.vehicle_brand,dbo.fn_GetVehicleEntityStatus(a.vehicle_id) entity_status,\n" +
                "h.meaning AS entity_status_meaning,dbo.fn_GetVehicleSaleStatus(a.vehicle_id) sale_status,i.meaning AS sale_status_meaning\n" +
                "FROM vw_vehicle_stock a \n" +
                "LEFT JOIN (SELECT warehouse_id,warehouse_type FROM base_vehicle_warehouse WHERE warehouse_type != 2) b \n" +
                "    ON a.warehouse_id = b.warehouse_id LEFT JOIN base_vehicle_model_catalog d ON a.vno_id=d.self_id\n" +
                "LEFT JOIN base_vehicle_model_catalog_price e ON a.vno_id=e.parent_id AND a.station_id=e.station_id\n" +
                "LEFT JOIN dbo.vehicle_sale_contract_detail f ON a.vehicle_id=f.vehicle_id AND a.sale_contract_no=f.contract_no \n" +
                "AND f.approve_status IN (0,1,2,20) LEFT JOIN dbo.vw_vehicle_type g ON a.vno_id=g.vno_id \n" +
                "LEFT JOIN dbo.sys_flags h ON h.code=dbo.fn_GetVehicleEntityStatus(a.vehicle_id) AND h.field_no='entity_status'\n" +
                "LEFT JOIN dbo.sys_flags i ON i.code=dbo.fn_GetVehicleSaleStatus(a.vehicle_id) AND i.field_no='sale_status'\n" +
                "WHERE ISNULL(a.conversion_status,0) IN (0,2) \n" +
                "AND a.vehicle_id NOT IN (SELECT vc.vehicle_id FROM dbo.vehicle_conversion vc \n" +
                "                         LEFT JOIN dbo.vehicle_conversion_master vcm ON vc.master_no=vcm.document_no\n" +
                "\t\t\t\t\t\t WHERE vcm.status<50)\n" +
                ") a WHERE 1=1 AND a.vehicle_vin NOT LIKE 'DEL_%' AND a.entity_status<>40 ";  //a.entity_status 40:已出库

        if (!"允许".equals(getSysOptionValue(VEHICEL_OVER_STATION))) {
            sql += String.format(" AND ((a.vat_no IS NULL AND a.station_id ='%s') OR (vat_no IS NOT NULL AND vat_station_id='%s'))\n", user.getDefaulStationId(), user.getDefaulStationId());
        }

        String condition = "";
        Map<String, Object> paramMap = new HashMap<>();

        if (StringUtils.isNotBlank(keyword)) {
            condition += " AND (a.vehicle_vin LIKE :keyword OR a.station_name LIKE  :keyword  " +
                    "OR a.vehicle_color LIKE :keyword " +
                    "OR a.vehicle_strain LIKE :keyword)";
            paramMap.put("keyword", "%" + keyword + "%");
        }

        if (stationIds != null && stationIds.size() > 0) {
            condition += " AND a.station_id IN (:stationIds)";
            paramMap.put("stationIds", stationIds);
        }

        String filterCondition = baseDao.mapToFilterString(filterMap, "a");
        if (StringUtils.isNotBlank(filterCondition)) {
            condition += " AND " + filterCondition;
        }

        sql = sql + condition;
        logger.debug("sql=" + sql);
        return baseDao.listInSql(sql, null, pageNo, pageSize, paramMap);
    }


    public PageModel getOptionalVehiclesConversion(String vehicleIds) {
        if (StringUtils.isBlank(vehicleIds)) {
            return null;
        }
        String[] vehicleId = StringUtils.split(vehicleIds, ",");
        if (vehicleId == null || vehicleId.length == 0) {
            return null;
        }
        String sql = "SELECT a.vehicle_id,a.item_id,ISNULL(a.item_cost,0) item_cost,ISNULL(a.conversion_type,0) conversion_type,\n" + "ISNULL(a.supplier_name,'') AS supplier_name\n" + " FROM dbo.vehicle_conversion_detail a\n" + "WHERE ISNULL(a.sale_contract_item_id,'')='' AND a.status=2   AND a.vehicle_id IN (:vehicleId)";

        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("vehicleId", vehicleId);
        return baseDao.listInSql(sql, paramMap);
    }


    public Object getInvoiceAgency(String vehicleIds) {
        if (StringUtils.isBlank(vehicleIds)) {
            return null;
        }
        String[] vehicleId = StringUtils.split(vehicleIds, ",");
        if (vehicleId == null || vehicleId.length == 0) {
            return null;
        }
        String sql = "select vehicle_id,invoice_agency_code from vw_vehicle_invoice_agency_code WITH(NOLOCK) where vehicle_id in (:vehicleId)";

        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("vehicleId", vehicleId);
        return baseDao.getMapBySQL(sql, paramMap);
    }

    /**
     * 保存方法入口
     *
     * @param parameter
     */
    public Map<String, List<Object>> saveSaleContract(JsonObject parameter) {
        long time1 = System.currentTimeMillis();

        List<EntityProxy<?>> contractProxies = this.save(parameter);


        EntityProxy<VehicleSaleContracts> entityProxy = (EntityProxy<VehicleSaleContracts>) contractProxies.get(0);

        StopWatch watch0 = ContractStopWatch.startWatch(SaleContractService.class, "saveSaleContract", "log");
        this.log(entityProxy);
        ContractStopWatch.stop(watch0);

        VehicleSaleContracts contract = entityProxy.getEntity();
        if (entityProxy.getOperation() != Operation.DELETE) {
            StopWatch watch1 = ContractStopWatch.startWatch(SaleContractService.class, "saveSaleContract", "attachmentToFtp");
            attachmentToFtp(contract);
            ContractStopWatch.stop(watch1);
        }

        StopWatch watch3 = ContractStopWatch.startWatch(SaleContractService.class, "saveSaleContract", "updateQuotationWithSaveContract");
        updateQuotationWithSaveContract(entityProxy);
        ContractStopWatch.stop(watch3);

        StopWatch watch4 = ContractStopWatch.startWatch(SaleContractService.class, "saveSaleContract", "flush");
        baseDao.flush();
        ContractStopWatch.stop(watch4);

        StopWatch watch5 = ContractStopWatch.startWatch(SaleContractService.class, "saveSaleContract", "updateVehicleGroupInfo");
        this.updateVehicleGroupInfo(contract);
        ContractStopWatch.stop(watch5);


        //执行存储过程，要放到flush之后
        StopWatch watch6 = ContractStopWatch.startWatch(SaleContractService.class, "saveSaleContract", "executeProVehicleDataVary");
        this.executeProVehicleDataVary(contract.getContractNo());
        ContractStopWatch.stop(watch6);

        StopWatch watch7 = ContractStopWatch.startWatch(SaleContractService.class, "saveSaleContract", "convertReturnData");
        Map<String, List<Object>> result = new HashMap<>(1);
        if ("PC".equals(HttpSessionStore.getSessionOs())) {
            //pc端的话，只返回主表数据
            List item = baseDao.getMapBySQL("SELECT * FROM vehicle_sale_contracts where contract_no = '" + contract.getContractNo() + "'", null);
            result.put("vehicle_sale_contracts", item);
        } else {
            result = convertReturnData(contract.getContractNo());
        }
        ContractStopWatch.stop(watch7);

        long time2 = System.currentTimeMillis();
        String description = String.format("%s:%d 合同号：%s，变更摘要：%s", entityProxy.getOperation() == Operation.CREATE ? "新建" : "修改", (time2 - time1), contract.getContractNo(), compareEntity == null ? null : gson.toJson(compareEntity));
        this.addSysLog("操作耗时", description);

        String watchLog = ContractStopWatch.log();
        if (StringUtils.isNotEmpty(watchLog)) {
            this.addSysLog("服务端耗时", String.format("合同号：%s,报文：%s, %s", contract.getContractNo(), parameter, watchLog));
        }

        return result;
    }


    /**
     * 执行存储过程,要放到flush之后
     *
     * @param contractNo
     */
    public void executeProVehicleDataVary(String contractNo) {
        String sql = "{call dbo.pro_vehicle_data_vary(?)}";
        baseDao.findProcedureWithoutReturn(sql, new Object[]{contractNo});
    }


    /**
     * 配车详情 -手机客户端列表要显示
     *
     * @param contract
     */
    private void updateVehicleGroupInfo(VehicleSaleContracts contract) {
        //配车详情 vehicleGroupInfo
        List<VehicleSaleContractDetailGroups> detailGroupsList = (List<VehicleSaleContractDetailGroups>) baseDao.findByHql("FROM VehicleSaleContractDetailGroups where contractNo = ?", contract.getContractNo());
        String vehicleGroupInfo = "";
        if (detailGroupsList != null && detailGroupsList.size() > 0) {
            for (VehicleSaleContractDetailGroups detailGroup : detailGroupsList) {
                vehicleGroupInfo += String.format("%s %s %d台；",
                        StringUtils.isBlank(detailGroup.getShortNameVno()) ? detailGroup.getVehicleVno() : detailGroup.getShortNameVno(),
                        detailGroup.getVehicleColor(), detailGroup.getVehicleQuantity());
            }
        }
        contract.setVehicleGroupInfo(vehicleGroupInfo);

    }


    private void addSysLog(String logType, String descrption) {
        sysLogsDao.addSysLog(logType, "销售合同", descrption);
    }

    /**
     * 合同保存后回填报价单
     *
     * @param entityProxy
     */
    public void updateQuotationWithSaveContract(EntityProxy<VehicleSaleContracts> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        JsonObject jo_contract = entityProxy.getJsonObject();
        if (jo_contract == null || jo_contract.isJsonNull() || !jo_contract.has("quotation_id")) {
            return;
        }
        String quotationId = jo_contract.get("quotation_id").getAsString();
        if (StringUtils.isNotEmpty(quotationId)) {
            VehicleSaleQuotation quotation = baseDao.get(VehicleSaleQuotation.class, quotationId);
            if (quotation != null) {
                quotation.setContractNo(entityProxy.getEntity().getContractNo());
            }
        }
    }


    /**
     * 图片附件上传
     *
     * @param contract
     */
    private void attachmentToFtp(VehicleSaleContracts contract) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String pics = fileService.addPicsToFtp(user, contract.getContractNo(), contract.getPics());
        String fileUrls = fileService.addAttachmentsToFtp(user, contract.getContractNo(), contract.getFileUrls());

        contract.setPics(pics);
        contract.setFileUrls(fileUrls);
    }

    private void log(EntityProxy<VehicleSaleContracts> entityProxy) {
        try {

            VehicleSaleContractHistoryHandler logHandler = new VehicleSaleContractHistoryHandler();
            logHandler.logHistory(entityProxy);

        } catch (Exception ex) {
            if (entityProxy.getOperation() == Operation.UPDATE) {
                throw new ServiceException(String.format("记录车辆销售合同（%s）更改历史出错", entityProxy.getEntity().getContractNo()), ex);
            } else {
                throw new ServiceException("记录车辆销售合同更改历史出错", ex);
            }
        }
    }


    public Map<String, List<Object>> convertReturnData(String contractNo) {
        StopWatch wc = new StopWatch();
        Map<String, List<Object>> rtnData = new HashMap<>(10);

        wc.start("vehicle_sale_contracts");
        String contracts_sql = "SELECT * FROM vw_vehicle_sale_contracts where contract_no = :val";
        Map<String, Object> paramsMap = new HashMap<>(2);
        paramsMap.put("val", contractNo);
        List contractList = baseDao.getMapBySQL(contracts_sql, paramsMap);
        if (contractList != null && contractList.size() > 0) {
            for (Object item : contractList) {
                Map itemMap = (Map) item;
                //计算合同中的一些金额
                computeContractExtraData(itemMap);
            }
        }
        rtnData.put("vehicle_sale_contracts", contractList);
        wc.stop();


        wc.start("vehicle_sale_contract_detail_groups");
        String detailGroup_sql = "SELECT * FROM  vw_vehicle_sale_contract_detail_groups where contract_no = :val";
        List detailGroupList = baseDao.getMapBySQL(detailGroup_sql, paramsMap);
        rtnData.put("vehicle_sale_contract_detail_groups", detailGroupList);
        wc.stop();


        wc.start("vehicle_sale_contract_detail");
        String detail_sql = "SELECT * FROM  vehicle_sale_contract_detail where contract_no = :val";
        List detailList = baseDao.getMapBySQL(detail_sql, paramsMap);
        rtnData.put("vehicle_sale_contract_detail", detailList);
        wc.stop();

        wc.start("vehicle_sale_contract_insurance_groups");
        String insurance_groups_sql = "SELECT * FROM  vehicle_sale_contract_insurance_groups where contract_no = :val";
        List insuranceGroupList = baseDao.getMapBySQL(insurance_groups_sql, paramsMap);
        rtnData.put("vehicle_sale_contract_insurance_groups", insuranceGroupList);
        wc.stop();

        wc.start("vehicle_sale_contract_present_groups");
        String present_groups_sql = "SELECT * FROM  vehicle_sale_contract_present_groups where contract_no = :val";
        List presentGroupList = baseDao.getMapBySQL(present_groups_sql, paramsMap);
        rtnData.put("vehicle_sale_contract_present_groups", presentGroupList);
        wc.stop();


        wc.start("vehicle_sale_contract_item_groups");
        String item_groups_sql = "SELECT * FROM  vehicle_sale_contract_item_groups where contract_no = :val";
        List itemGroupList = baseDao.getMapBySQL(item_groups_sql, paramsMap);
        rtnData.put("vehicle_sale_contract_item_groups", itemGroupList);
        wc.stop();

        wc.start("vehicle_sale_contract_charge_groups");
        String charge_groups_sql = "SELECT * FROM  vehicle_sale_contract_charge_groups where contract_no = :val";
        List chargeGroupList = baseDao.getMapBySQL(charge_groups_sql, paramsMap);
        if (chargeGroupList != null && chargeGroupList.size() > 0) {
            for (Object item : chargeGroupList) {
                Map itemMap = (Map) item;
                //判断分组的费用是否已经报销
                checkIsReimbursed(itemMap);
            }
        }
        rtnData.put("vehicle_sale_contract_charge_groups", chargeGroupList);
        wc.stop();


        wc.start("vehicle_sale_contract_gifts_groups");
        String gifts_groups_sql = "SELECT * FROM  vehicle_sale_contract_gifts_groups where contract_no = :val";
        List giftsGroupList = baseDao.getMapBySQL(gifts_groups_sql, paramsMap);
        rtnData.put("vehicle_sale_contract_gifts_groups", giftsGroupList);
        wc.stop();

        wc.start("vehicle_invoices_groups");
        String invoices_groups_sql = "SELECT * FROM  vehicle_invoices_groups where contract_no = :val";
        List invoicesGroupList = baseDao.getMapBySQL(invoices_groups_sql, paramsMap);
        rtnData.put("vehicle_invoices_groups", invoicesGroupList);
        wc.stop();


        //获得合同分组ID
        List<String> groupIds = new ArrayList<>();
        if (detailGroupList != null && detailGroupList.size() > 0) {
            for (Object o : detailGroupList) {
                Map<String, Object> item = (Map<String, Object>) o;
                if (item != null && item.containsKey("group_id") && StringUtils.isNotEmpty((String) item.get("group_id"))) {
                    groupIds.add((String) item.get("group_id"));
                }
            }
        }
        paramsMap.put("groupIds", groupIds);

        wc.start("vw_vehicle_loan_budget_details_group");
        String budget_details_group_sql = "SELECT * FROM (\n" + "SELECT a.*,b.contract_no FROM vw_vehicle_loan_budget_details_group a WITH ( NOLOCK )\n"
                + "LEFT JOIN  ( SELECT DISTINCT group_id,contract_no FROM dbo.vehicle_sale_contract_detail WITH ( NOLOCK ) ) b ON a.group_id=b.group_id\n"
                + "LEFT JOIN dbo.vw_vehicle_loan_budget c  WITH ( NOLOCK ) ON a.document_no=c.document_no\n"
                + "WHERE a.status IN (0,1) AND c.status<=50\n" + ") a where a.group_id IN (:groupIds)";
        List budgetDetailsGroupList = baseDao.getMapBySQL(budget_details_group_sql, paramsMap);
        rtnData.put("vw_vehicle_loan_budget_details_group", budgetDetailsGroupList);
        wc.stop();


        wc.start("vw_vehicle_loan_budget_charge_group");
        String budget_charge_group_sql = "SELECT * FROM ( SELECT a.*,d.sale_contract_no as contract_no,c.status as c_staus FROM vw_vehicle_loan_budget_charge_group a WITH ( NOLOCK )\n" +
                "LEFT JOIN dbo.vw_vehicle_loan_budget_details_group c WITH ( NOLOCK ) ON a.group_id=c.group_id  and a.document_no = c.document_no\n" +
                "LEFT JOIN dbo.vehicle_loan_budget d WITH ( NOLOCK )  ON c.document_no=d.document_no\n" +
                "WHERE a.status IN (10,20) AND c.status IN (0,1) AND d.status<=50) a\n" +
                "WHERE a.group_id  IN (:groupIds)";
        List budgetChargeGroupList = baseDao.getMapBySQL(budget_charge_group_sql, paramsMap);

        rtnData.put("vw_vehicle_loan_budget_charge_group", budgetChargeGroupList);
        wc.stop();


        //实际改装
        wc.start("vehicle_conversion_detail");
        String conversion_sql = "SELECT DISTINCT item_no,a.item_name,a.item_cost,a.supplier_no,a.supplier_name,a.conversion_type,\n"
                + "b.meaning AS conversion_type_meaning FROM dbo.vehicle_conversion_detail a WITH ( NOLOCK ) \n"
                + "LEFT JOIN dbo.sys_flags b  WITH ( NOLOCK ) ON a.conversion_type=b.code AND b.field_no='conversion_type'\n"
                + "WHERE a.status=2  AND a.vehicle_id IN(select vehicle_id from vehicle_sale_contract_detail where group_id IN (:groupIds))";
        List conversionList = baseDao.getMapBySQL(conversion_sql, paramsMap);
        rtnData.put("vehicle_conversion_detail", conversionList);
        wc.stop();


        //vw_vehicle_sale_item_details
        wc.start("vw_vehicle_sale_item_details");
        String item_details_sql = "SELECT * FROM vw_vehicle_sale_item_details WITH ( NOLOCK ) where group_id IN (:groupIds)";
        List itemDetailList = baseDao.getMapBySQL(item_details_sql, paramsMap);
        rtnData.put("vw_vehicle_sale_item_details", itemDetailList);
        wc.stop();


        wc.start("getOverviewInfoNew");
        rtnData.putAll(getOverviewInfoNew(detailGroupList, contractNo));
        wc.stop();

        logger.debug(String.format("查询合同明细，%s %s %s", wc.getTotalTimeMillis(), contractNo, wc.prettyPrint()));
        this.addSysLog("合同明细耗时", String.format("%s, %s %s", wc.getTotalTimeMillis(), contractNo, wc.prettyPrint()));

        return rtnData;
    }

    private Map<String, List<Object>> getOverviewInfoNew(List detailGroupList, String contractNo) {
        VehicleSaleContracts contracts = baseDao.get(VehicleSaleContracts.class, contractNo);

        List<Object> overViewList = new ArrayList<>();
        for (Object obj : detailGroupList) {
            Map<String, Object> groupMap = (Map<String, Object>) obj;

            double deposit = Tools.toDouble((Number) groupMap.get("deposit"));
            if (!"车辆录入".equals(getSysOptionValue(VEHICLE_DEPOSIT_INPUT_TYPE))) {
                //单车订金是合同录入时
                if (contracts != null) {
                    deposit = Tools.toDouble(contracts.getDeposit()) / contracts.getContractQuantity();
                }
            }

            Map<String, Object> overViewMap = new HashMap();
            overViewMap.put("group_id", groupMap.get("group_id"));
            overViewMap.put("合同号", groupMap.get("contract_no"));
            overViewMap.put("单车订金", deposit);
            overViewMap.put("裸车限价", groupMap.get("price_sale_base"));
            overViewMap.put("裸车参考成本", groupMap.get("reference_cost"));
            overViewMap.put("合同收入", groupMap.get("income_tot"));
            overViewMap.put("合同成本", groupMap.get("cost_tot"));
            overViewMap.put("合同利润", Tools.toBigDecimal((Number) groupMap.get("income_tot")).subtract(Tools.toBigDecimal((Number) groupMap.get("cost_tot"))));
            overViewMap.put("消贷收益", groupMap.get("loan_charge_profit"));
            overViewMap.put("可用资源", getCanUseQty(contractNo, groupMap));
            //可用资源需要优化，暂时都给0
//            overViewMap.put("可用资源", 0);

            overViewList.add(overViewMap);
        }


        Map<String, List<Object>> rtnData = new HashMap<>(1);
        rtnData.put("group_overview_info", overViewList);
        return rtnData;
    }

    /**
     * 获得合同信息
     *
     * @param contractNo
     * @return
     */
    public Map<String, List<Object>> convertReturnDataOld(String contractNo) {
        Map<String, List<Object>> rtnData = new HashMap<>();
        convertReturnDataForDetail("vehicle_sale_contracts", "SELECT * FROM vw_vehicle_sale_contracts where contract_no = :val", contractNo, rtnData);
        rtnData.putAll(getOverviewInfo(contractNo));

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
            if ("vehicle_sale_contracts".equals(tableName)) {
                //计算合同中的一些金额
                computeContractExtraData(itemMap);
                convertReturnDataForDetail("vehicle_sale_contract_detail_groups", "SELECT * FROM  vw_vehicle_sale_contract_detail_groups where contract_no = :val", itemMap.get("contract_no"), rtnData);
            } else if ("vehicle_sale_contract_detail_groups".equals(tableName)) {
                convertReturnDataForDetail("vehicle_sale_contract_detail", "SELECT * FROM  vehicle_sale_contract_detail where group_id = :val", itemMap.get("group_id"), rtnData);

                convertReturnDataForDetail("vehicle_sale_contract_insurance_groups", "SELECT * FROM  vehicle_sale_contract_insurance_groups where group_id = :val", itemMap.get("group_id"), rtnData);

                convertReturnDataForDetail("vehicle_sale_contract_present_groups", "SELECT * FROM  vehicle_sale_contract_present_groups where group_id = :val", itemMap.get("group_id"), rtnData);

                convertReturnDataForDetail("vehicle_sale_contract_item_groups", "SELECT * FROM  vehicle_sale_contract_item_groups where group_id = :val", itemMap.get("group_id"), rtnData);

                convertReturnDataForDetail("vehicle_sale_contract_charge_groups", "SELECT * FROM  vehicle_sale_contract_charge_groups where group_id = :val", itemMap.get("group_id"), rtnData);

                convertReturnDataForDetail("vehicle_sale_contract_gifts_groups", "SELECT * FROM  vehicle_sale_contract_gifts_groups where group_id = :val", itemMap.get("group_id"), rtnData);

                convertReturnDataForDetail("vehicle_invoices_groups", "SELECT * FROM  vehicle_invoices_groups where group_id = :val", itemMap.get("group_id"), rtnData);

                convertReturnDataForDetail("vw_vehicle_loan_budget_details_group", "SELECT * FROM (\n" + "SELECT a.*,b.contract_no FROM vw_vehicle_loan_budget_details_group a\n" + "LEFT JOIN  ( SELECT DISTINCT group_id,contract_no FROM dbo.vehicle_sale_contract_detail) b ON a.group_id=b.group_id\n" + "LEFT JOIN dbo.vw_vehicle_loan_budget c ON a.document_no=c.document_no\n" + "WHERE a.status IN (0,1) AND c.status<=50\n" + ") a where a.group_id =:val", itemMap.get("group_id"), rtnData);

                //游工说很慢，优化sql
                convertReturnDataForDetail("vw_vehicle_loan_budget_charge_group", "SELECT * FROM ( SELECT a.*,d.sale_contract_no as contract_no,c.status as c_staus FROM vw_vehicle_loan_budget_charge_group a \n" +
                        "LEFT JOIN dbo.vw_vehicle_loan_budget_details_group c ON a.group_id=c.group_id  and a.document_no = c.document_no\n" +
                        "LEFT JOIN dbo.vehicle_loan_budget d ON c.document_no=d.document_no\n" +
                        "WHERE a.status IN (10,20) AND c.status IN (0,1) AND d.status<=50) a\n" +
                        "WHERE a.group_id = :val", itemMap.get("group_id"), rtnData);

                getRealConversionDetail(itemMap, rtnData);

            } else if ("vehicle_sale_contract_detail".equals(tableName)) {

            } else if ("vehicle_sale_contract_charge_groups".equals(tableName)) {
                checkIsReimbursed(itemMap);
            }
        }
    }

    /**
     * 实际改装
     *
     * @param itemMap
     * @param rtnData
     */
    private void getRealConversionDetail(Map itemMap, Map<String, List<Object>> rtnData) {
        String tableName = "vehicle_conversion_detail";
        String sql = "SELECT DISTINCT item_no,a.item_name,a.item_cost,a.supplier_no,a.supplier_name,a.conversion_type,\n" + "b.meaning AS conversion_type_meaning FROM dbo.vehicle_conversion_detail a\n" + "LEFT JOIN dbo.sys_flags b ON a.conversion_type=b.code AND b.field_no='conversion_type'\n" + "WHERE a.status=2  AND a.vehicle_id IN(select vehicle_id from vehicle_sale_contract_detail where group_id=:val)";
        List<Object> dataList = rtnData.get(tableName);
        if (dataList == null) {
            dataList = new ArrayList<>();
            rtnData.put(tableName, dataList);
        }
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("val", itemMap.get("group_id"));

        List<Map<String, Object>> data = baseDao.getMapBySQL(sql, parmMap);

        if (data != null && data.size() > 0) {
            for (Map<String, Object> item : data) {
                item.put("group_id", itemMap.get("group_id"));
            }
        }
        dataList.addAll(data);
    }


    //判断分组的费用是否已经报销
    private void checkIsReimbursed(Map itemMap) {
        if (itemMap == null) {
            return;
        }

        itemMap.put("is_reimbursed", false);
        String sql = "SELECT distinct charge_group_id,charge_id,charge_name,cost_status \n" + "FROM dbo.vehicle_sale_contract_charge WHERE charge_group_id=:chargeGroupId AND isnull(cost_status,0)=1";
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("chargeGroupId", itemMap.get("charge_group_id"));
        List<Object> data = baseDao.listInSql(sql, parmMap).getData();
        if (data != null && data.size() > 0) {
            //如果对应的vehicle_sale_contract_charge有报销的，is_reimbursed = true
            itemMap.put("is_reimbursed", true);
        }
    }

    // 计算合同的附加数据数据
    private void computeContractExtraData(Map itemMap) {
        if (itemMap == null) {
            return;
        }

        SysUsers user = HttpSessionStore.getSessionUser();
        //是否开启东风接口
        boolean m_bDfsValid = "有效".equals(sysOptionsDao.getOptionForString(DFS_INTERFACE_IS_VALID, user.getDefaulStationId()));
        double contract_money_total = 0.00D; //合同总额（包括应收预算费用）
        double receive_money_total = 0.00D; //合同已收款（包括应收预算费用）
        double due_receive_money = 0.00D; //应收金额
        double rest_due_receive_money = 0.00D;//剩余应收
        double can_use_money = 0.00D; //可用金额
        if (m_bDfsValid) {
            contract_money_total = getDoubleValByKey(itemMap, "contract_money");
            receive_money_total = getDoubleValByKey(itemMap, "receive_money");
            due_receive_money = contract_money_total - getDoubleValByKey(itemMap, "loan_amount_totv") + getDoubleValByKey(itemMap, "first_pay_totc");
        } else {
            contract_money_total = getDoubleValByKey(itemMap, "contract_money") + getDoubleValByKey(itemMap, "charge_money_tot");
            receive_money_total = getDoubleValByKey(itemMap, "receive_money") + getDoubleValByKey(itemMap, "receive_money_loan_charge");
            due_receive_money = contract_money_total - getDoubleValByKey(itemMap, "loan_amount_tot");
        }
        rest_due_receive_money = (due_receive_money - receive_money_total < 0.00D ? 0.00D : (due_receive_money - receive_money_total));
        if (due_receive_money - receive_money_total < 0.00D) {
            can_use_money = due_receive_money - getDoubleValByKey(itemMap, "out_money");
        } else {
            can_use_money = receive_money_total - getDoubleValByKey(itemMap, "out_money");
        }
        itemMap.put("contract_money_total", contract_money_total);
        itemMap.put("receive_money_total", receive_money_total);
        itemMap.put("due_receive_money", due_receive_money);
        itemMap.put("rest_due_receive_money", rest_due_receive_money);
        itemMap.put("can_use_money", can_use_money);
    }

    private double getDoubleValByKey(Map itemMap, String key) {
        if (!itemMap.containsKey(key) || itemMap.get(key) == null) {
            return 0.00D;
        }
        return Tools.toDouble((Number) itemMap.get(key));
    }

    public Map<String, List<Object>> getOverviewInfo(String contractNo) {
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("val", contractNo);
        List<Object> data = baseDao.listInSql("SELECT * FROM  vw_vehicle_sale_contract_detail_groups where contract_no = :val", parmMap).getData();
        VehicleSaleContracts contracts = baseDao.get(VehicleSaleContracts.class, contractNo);

        List<Object> overViewList = new ArrayList<>();
        List<Object> itemDetailList = new ArrayList<>();
        for (Object obj : data) {
            Map<String, Object> groupMap = (Map<String, Object>) obj;
            double deposit = Tools.toDouble((Number) groupMap.get("deposit"));
            if (!"车辆录入".equals(getSysOptionValue(VEHICLE_DEPOSIT_INPUT_TYPE))) {
                //单车订金是合同录入时
                if (contracts != null) {
                    deposit = Tools.toDouble(contracts.getDeposit()) / contracts.getContractQuantity();
                }
            }

            Map<String, Object> overViewMap = new HashMap();
            overViewMap.put("group_id", groupMap.get("group_id"));
            overViewMap.put("合同号", groupMap.get("contract_no"));
            overViewMap.put("单车订金", deposit);
            overViewMap.put("裸车限价", groupMap.get("price_sale_base"));
            overViewMap.put("裸车参考成本", groupMap.get("reference_cost"));
            overViewMap.put("合同收入", groupMap.get("income_tot"));
            overViewMap.put("合同成本", groupMap.get("cost_tot"));
            overViewMap.put("合同利润", Tools.toBigDecimal((Number) groupMap.get("income_tot")).subtract(Tools.toBigDecimal((Number) groupMap.get("cost_tot"))));
            overViewMap.put("消贷收益", groupMap.get("loan_charge_profit"));
            overViewMap.put("可用资源", getCanUseQty(contractNo, groupMap));


            overViewList.add(overViewMap);
            parmMap.put("val", groupMap.get("group_id"));
            itemDetailList.addAll(baseDao.listInSql(" SELECT * FROM vw_vehicle_sale_item_details  where group_id =:val", parmMap).getData());
        }

        Map<String, List<Object>> rtnData = new HashMap<>(2);
        rtnData.put("group_overview_info", overViewList);
        rtnData.put("vw_vehicle_sale_item_details", itemDetailList);
        return rtnData;
    }

    //查看车辆的可用资源
    private double getCanUseQty(String contractNo, Map<String, Object> groupMap) {
        double canUseQty = 0.00D;
        if (groupMap == null) {
            return canUseQty;
        }
//        String sql = " select  vehicle_vno, case when ISNULL(sum(can_use_qty),0)<0 THEN 0 ELSE ISNULL(sum(can_use_qty),0) END as can_use_qty\n" + "  FROM vw_stat_vehicle_stock_analysis WHERE vehicle_vno=:vehicleVno  GROUP BY vehicle_vno";


//        String sql = "SELECT vehicle_vno, COUNT(*) as can_use_qty  FROM dbo.vw_vehicle_resources WITH ( NOLOCK ) WHERE entity_status IN (10,20,30) AND sale_status IN (10)\n" +
//                "AND vehicle_vno = :vehicleVno AND vehicle_color = :vehicleColor GROUP BY vehicle_vno";

        String sql = "SELECT vehicle_vno,COUNT(*) qty FROM dbo.vehicle_stocks a WITH ( NOLOCK ) \n" +
                "INNER JOIN vw_vehicle_resource_status b WITH ( NOLOCK ) ON b.vehicle_id = a.vehicle_id\n" +
                "WHERE b.entity_status IN (10,20,30) AND b.sale_status IN (10)\n" +
                "AND a.vehicle_vno = :vehicleVno AND a.vehicle_color = :vehicleColor\n" +
                "GROUP BY vehicle_vno";
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("vehicleVno", groupMap.get("vehicle_vno"));
        parmMap.put("vehicleColor", groupMap.get("vehicle_color"));
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, parmMap);
        if (list != null && list.size() > 0) {
            canUseQty = Tools.toDouble((Number) (list.get(0).get("can_use_qty")));
        }

        sql = "select contract_detail_id from vehicle_sale_contract_detail where ISNULL(vehicle_id,'')<>'' AND vehicle_vno=:vehicleVno";
        list = baseDao.getMapBySQL(sql, parmMap);

        canUseQty += (list == null ? 0 : list.size());
        return canUseQty;
    }

    public Object getCanUseQty(String vehicleVno) {

        String sql = "SELECT vehicle_vno, COUNT(*) as can_use_qty  FROM dbo.vw_vehicle_resources WHERE entity_status IN (10,20,30) AND sale_status IN (10)\n" +
                "AND vehicle_vno =:vehicleVno GROUP BY vehicle_vno";

        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("vehicleVno", vehicleVno);
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, parmMap);
        return list;
    }


    @Override
    public EntityRelation getEntityRelation() {

        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleSaleContracts> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(SaleContractService.class, "beforeExecute");

        //合同号
        SysUsers user = HttpSessionStore.getSessionUser();
        //生成变更的摘要
        StopWatch watch0 = ContractStopWatch.startWatch(EntityProxyUtil.class, "beforeExecute", "compareEntityProxy");
        compareEntity = EntityProxyUtil.compareEntityProxy(entityProxy);
        ContractStopWatch.stop(watch0);

        VehicleSaleContracts contracts = entityProxy.getEntity();
        if (entityProxy.getOperation() == Operation.CREATE) {

            if (StringUtils.isBlank(contracts.getStationId())) {
                throw new ServiceException("车辆合同的站点为空");
            } else if (contracts.getStationId().contains(",")) {
                throw new ServiceException("车辆合同站点错误：" + contracts.getStationId());
            }
            if (StringUtils.isBlank(contracts.getDepartmentId())) {
                throw new ServiceException("车辆合同部门为空（departmentId）");
            }
            if (StringUtils.isBlank(contracts.getDepartmentNo())) {
                throw new ServiceException("车辆合同部门为空（departmentNo）");
            }
            if (StringUtils.isBlank(contracts.getDepartmentName())) {
                throw new ServiceException("车辆合同部门为空（departmentName）");
            }
            if (null != contracts.getPlanFinishTime() && TimestampUitls.compareWithDate(contracts.getPlanFinishTime(), new Timestamp(System.currentTimeMillis())) == -1) {
                throw new ServiceException("车辆合同的计划交付日期不能早于当前时间");
            }
            String contractNo = sysCodeService.createSysCodeRules(CODE_RULE_NO, contracts.getStationId());
            contracts.setContractNo(contractNo);
            // document_no 和 contract_no 应该是同一个值
            contracts.setDocumentNo(contractNo);
            if (StringUtils.isEmpty(contracts.getContractCode())) {
                contracts.setContractCode(contractNo);
            }

            contracts.setUserId(user.getUserId());
            contracts.setUserNo(user.getUserNo());
            contracts.setUserName(user.getUserName());

            contracts.setSubmitStationId(contracts.getStationId());
            SysStations submitStation = baseDao.get(SysStations.class, contracts.getSubmitStationId());
            if (submitStation != null) {
                contracts.setSubmitStationName(submitStation.getStationName());
            }
            contracts.setCreator(user.getUserFullName());
            contracts.setCreateTime(new Timestamp(System.currentTimeMillis()));
            contracts.setModifier(user.getUserFullName());
            contracts.setModifyTime(new Timestamp(System.currentTimeMillis()));
            contracts.setClientType(HttpSessionStore.getSessionOs());//记录新增时的客户端类型
        } else {
            VehicleSaleContracts oriContracts = entityProxy.getOriginalEntity();
            validateModifyTime(contracts.getModifyTime(), oriContracts.getModifyTime());

            if (Tools.toShort(oriContracts.getContractStatus()) == (short) 4 || Tools.toShort(oriContracts.getContractStatus()) == (short) 3) {
                throw new ServiceException("已作废或已终止的合同不允许再编辑");
            }
        }

        StopWatch watch1 = ContractStopWatch.startWatch(SaleContractService.class, "beforeExecute", "validateRecord");
        validateRecord(entityProxy);
        ContractStopWatch.stop(watch1);

        StopWatch watch2 = ContractStopWatch.startWatch(SaleContractService.class, "beforeExecute", "initExtProperty");
        initExtProperty(entityProxy);
        ContractStopWatch.stop(watch2);

        ContractStopWatch.stop(watch);
    }


    /**
     * 校验
     *
     * @param entityProxy
     */
    private void validateRecord(EntityProxy<VehicleSaleContracts> entityProxy) {

        Operation operation = entityProxy.getOperation();
        if (operation == Operation.DELETE) {
            return;
        }
        VehicleSaleContracts contracts = entityProxy.getEntity();
        VehicleSaleContracts oriContracts = entityProxy.getOriginalEntity();

        if (operation == Operation.CREATE || operation == Operation.UPDATE) {

            if (contracts.getAppointedTime() == null) {
                throw new ServiceException("约定回款不能为空");
            }

            String contractNo = contracts.getContractNo();
            String contractCode = contracts.getContractCode();
            /*if (!StringUtils.equals(contractNo, contractCode)) {
                List list = baseDao.findBySql("select 1 from vehicle_sale_contracts WHERE contract_status IN (0,1,2,4) and contract_no<>? and contract_code=?", contractNo, contractCode);
                if (null != list && !list.isEmpty()) {
                    throw new ServiceException(String.format("合同号%s已存在，请修改后再试", contractCode));
                }
            }*/
            this.validateContractCode(contracts);

            if (contracts.getBuyType() == null) {
                throw new ServiceException("购车方式不能为空");
            }

            if (operation == Operation.UPDATE) {
                if (Tools.toShort(contracts.getBuyType()) != Tools.toShort(oriContracts.getBuyType())) {

                    StopWatch watch0 = ContractStopWatch.startWatch(SaleContractService.class, "beforeExecute", "validateRecord", "查询VehicleLoanBudget");
//                    List<VehicleLoanBudget> budgetList = (List<VehicleLoanBudget>) baseDao.findByHql("FROM VehicleLoanBudget where status = 50 AND saleContractNo = ? ", contracts.getContractNo());
                    //使用WITH ( NOLOCK )减少锁表
                    String sql = "select * from vehicle_loan_budget WITH ( NOLOCK ) where status = 50 AND sale_contract_no=:saleContractNo";
                    Map<String, Object> params = new HashMap<>(1);
                    params.put("saleContractNo", contracts.getContractNo());
                    List budgetList = baseDao.getMapBySQL(sql, params);
                    ContractStopWatch.stop(watch0);

                    if (budgetList != null && budgetList.size() > 0) {
                        throw new ServiceException("已经做了消贷预算，付款方式不能修改");
                    }
                }
            }

            if (contracts.getSaleMode() == null) {
                throw new ServiceException("合同销售模式不能为空");
            }

//            List<SysFlags> saleModeList = (List<SysFlags>) baseDao.findByHql("FROM SysFlags Where fieldNo = ? and code = ?", "sale_mode", contracts.getSaleMode());
//            if (saleModeList != null && saleModeList.size() > 0) {
//                String optVal = getSysOptionValue(SALE_CONTRACT_MUST_VISITOR);
//                if (StringUtils.isNotEmpty(optVal) && optVal.contains(saleModeList.get(0).getMeaning()) && StringUtils.isEmpty(contracts.getVisitorId())) {
//                    throw new ServiceException(String.format("销售模式【%s】必须要有意向线索，请选择线索客户", saleModeList.get(0).getMeaning()));
//                }
//            }


            //2019-04-01后创建的合同必须要有意向线索
            if (contracts != null && contracts.getCreateTime() != null && contracts.getCreateTime().getTime() > Timestamp.valueOf("2019-04-01 00:00:00").getTime()
                    && StringUtils.isEmpty(contracts.getVisitorId())) {
                throw new ServiceException("2019-04-01后创建的合同必须要有意向线索，请选择线索客户");
            }
            if (contracts != null && contracts.getCreateTime() != null && contracts.getCreateTime().getTime() > Timestamp.valueOf("2019-04-01 00:00:00").getTime()
                    && StringUtils.isEmpty(contracts.getVisitorNo())) {
                throw new ServiceException("2019-04-01后创建的合同必须要有意向线索ID(visitorNo)");
            }

            //合同对应线索(%s)的状态不是跟进中
            if (operation == Operation.CREATE) {
                if (StringUtils.isNotEmpty(contracts.getVisitorNo())) {
                    PresellVisitors visitor = baseDao.get(PresellVisitors.class, contracts.getVisitorNo());
                    if (visitor != null && visitor.getVisitResult() != null) {
                        throw new ServiceException(String.format("合同对应线索(%s)的状态不正确", contracts.getVisitorNo()));
                    }
                }
            }


            if (StringUtils.isEmpty(contracts.getSeller())) {
                throw new ServiceException("销售员不能为空");
            }

            if (Tools.toShort(contracts.getSaleMode()) == 10 || Tools.toShort(contracts.getSaleMode()) == 20) {
                if (StringUtils.isEmpty(contracts.getCommissionMerchantId())) {
                    throw new ServiceException("销售模式为二级网点或合作单位时分销商不能为空");
                }
            }

            if (contracts.getSignTime() == null) {
                throw new ServiceException("合同签订日期为空");
            }

            if (contracts.getPlanFinishTime() == null) {
                throw new ServiceException("合同计划完成时间不能为空");
            }

            if (TimestampUitls.getElapsedMinutes(contracts.getPlanFinishTime(), contracts.getSignTime(), 1) < 0) {
                throw new ServiceException("计划完成日期不能小于合同签订日期");
            }

            if (StringUtils.isEmpty(contracts.getDeliveryLocus())) {
                throw new ServiceException("合同销售网点不能为空");
            }

            if (!"车辆录入".equals(getSysOptionValue(VEHICLE_DEPOSIT_INPUT_TYPE)) && Tools.toDouble(contracts.getDeposit()) < 0.00D) {
                throw new ServiceException("预付订金不能为负数");
            }

            if ("是".equals(getSysOptionValue("SALE_CONTRACT_MUST_VISITOR")) && StringUtils.isEmpty(contracts.getVisitorName())) {
                throw new ServiceException("销售合同必须要有意向线索，线索客户不能为空");
            }

            if (StringUtils.isEmpty(contracts.getCustomerId())) {
                throw new ServiceException("签约客户不能为空");
            }
            if (StringUtils.isEmpty(contracts.getCustomerProvince())) {
                throw new ServiceException("客户所属省份不能为空");
            }

            if (StringUtils.isEmpty(contracts.getCustomerCity())) {
                throw new ServiceException("客户所属市区不能为空");
            }
            if (StringUtils.isEmpty(contracts.getCustomerArea())) {
                throw new ServiceException("客户所属区域不能为空");
            }

            if (StringUtils.isEmpty(contracts.getCustomerMobile())) {
                throw new ServiceException("客户移动号码不能为空");
            }

            if (StringUtils.isEmpty(contracts.getCustomerProfession())) {
                throw new ServiceException("客户行业不能为空");
            }
            if (StringUtils.isEmpty(contracts.getLinkman())) {
                throw new ServiceException("客户联系人不能为空");
            }

            if (operation == Operation.UPDATE) {
                //线索客户变更，不再校验消贷预算单等信息
//                if (!StringUtils.equals(contracts.getVisitorId(), oriContracts.getVisitorId())) {
//                    if (checkBudget(contracts.getContractNo())) {
//                        throw new ServiceException("该合同已经做了消贷预算单,不能变更客户信息");
//                    }
//
//                    if (checkInsurance(contracts.getContractNo())) {
//                        throw new ServiceException("该合同已建立了保单,不能变更客户信息");
//                    }
//
//                    if (checkPart(contracts.getContractNo())) {
//                        throw new ServiceException("该合同已精品出库,不能变更客户信息");
//                    }
//
//                    if (checkVehicleVin(entityProxy)) {
//                        throw new ServiceException("该合同已配车,不能变更线索客户");
//                    }
//                }

                //客户变更
                if (!StringUtils.equals(contracts.getCustomerId(), oriContracts.getCustomerId())) {
                    if (checkBudget(contracts.getContractNo())) {
                        throw new ServiceException("该合同已经做了消贷预算单,不能变更客户信息");
                    }

                    if (checkInsurance(contracts.getContractNo())) {
                        throw new ServiceException("该合同已建立了保单,不能变更客户信息");
                    }

                    if (checkPart(contracts.getContractNo())) {
                        throw new ServiceException("该合同已精品出库,不能变更客户信息");
                    }
                    if (checkVehicleVin(entityProxy)) {
                        throw new ServiceException("该合同已配车,不能变更线索客户");
                    }
                }

            }
        }


    }

    /**
     * 校验合同号是否重复
     *
     * @param contracts
     */
    private void validateContractCode(VehicleSaleContracts contracts) {
        String optionValue = "站点唯一";
        List<SysOptions> optionsList = (List<SysOptions>) baseDao.findByHql("from SysOptions where option_no=? and station_id=?",
                "VEHICLE_SALE_CONTRACT_CODE_RULE", contracts.getStationId());
        if (null != optionsList && !optionsList.isEmpty()) {
            optionValue = optionsList.get(0).getOptionValue();
        }
        if (StringUtils.isNotEmpty(contracts.getContractCode()) && !"不限制".equals(optionValue)) {
            String sql = "";
            if ("系统唯一".equals(optionValue) || StringUtils.isEmpty(optionValue)) {
                sql += "SELECT 1 FROM dbo.vehicle_sale_contracts WHERE contract_status IN (0,1,2) AND contract_no <>'" + contracts.getContractNo() + "' AND contract_code='" + contracts.getContractCode() + "'";
            } else if ("站点唯一".equals(optionValue)) {
                sql += "SELECT 1 FROM dbo.vehicle_sale_contracts WHERE contract_status IN (0,1,2) AND contract_no <>'" +
                        contracts.getContractNo() + "' AND contract_code='" + contracts.getContractCode() + "' AND station_id='" + contracts.getStationId() + "'";
            }
            List list = baseDao.findBySql(sql, null);
            if (null != list && !list.isEmpty()) {
                throw new ServiceException(String.format("合同号%s已存在，请修改后再试", contracts.getContractCode()));
            }


        }
    }

    //判断是否已配车
    private boolean checkVehicleVin(EntityProxy<VehicleSaleContracts> entityProxy) {
        List<EntityProxy<VehicleSaleContractDetailGroups>> groupProxies = entityProxy.getSlaves(VehicleSaleContractDetailGroups.class.getSimpleName());
        for (EntityProxy groupProxy : groupProxies) {
            if (groupProxy.getOperation() == Operation.DELETE) {
                continue;
            }
            List<EntityProxy<VehicleSaleContractDetail>> detailProxies = groupProxy.getSlaves(VehicleSaleContractDetail.class.getSimpleName());
            for (EntityProxy<VehicleSaleContractDetail> detailProxy : detailProxies) {
                if (detailProxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                VehicleSaleContractDetail detail = detailProxy.getEntity();
                if (StringUtils.isNotEmpty(detail.getVehicleVin())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkBudget(String contractNo) {
        List<VehicleLoanBudget> budgetList = (List<VehicleLoanBudget>) baseDao.findByHql("FROM VehicleLoanBudget where flowStatus<>2 AND flowStatus<>4 AND saleContractNo = ?", contractNo);
        if (budgetList != null && budgetList.size() > 0) {
            return true;
        }
        return false;
    }

    private boolean checkInsurance(String contractNo) {
        String sql = "SELECT insurance_no FROM dbo.insurance WHERE approve_status IN (0,1) AND contract_no=:contractNo";
        Map<String, Object> params = new HashMap<>(1);
        params.put("contractNo", contractNo);
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    private boolean checkPart(String contractNo) {
        //因为合同中.vehicle_sale_contract_present不会修改get_quantity，可以这样查询
        String sql = "SELECT  sale_contract_present_id FROM dbo.vehicle_sale_contract_present WHERE ISNULL(get_quantity,0)>0 AND contract_no=:contractNo";
        Map<String, Object> params = new HashMap<>(1);
        params.put("contractNo", contractNo);
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }


    private void initExtProperty(EntityProxy<VehicleSaleContracts> entityProxy) {
        if (entityProxy.getOperation() == Operation.NONE) {
            return;
        }
        VehicleSaleContracts contract = entityProxy.getEntity();
        if (null != contract.getSaleMode()) {
            SysFlags flag = baseDao.getSysFlagsByFieldNoAndCode("sale_mode", contract.getSaleMode());
            if (null != flag) {
                contract.setSaleModeMeaning(flag.getMeaning());
            }
        }
        VehicleSaleContracts oriContract = entityProxy.getOriginalEntity();
        if (null != oriContract && null != oriContract.getSaleMode()) {
            SysFlags flag = baseDao.getSysFlagsByFieldNoAndCode("sale_mode", oriContract.getSaleMode());
            if (null != flag) {
                oriContract.setSaleModeMeaning(flag.getMeaning());
            }
        }

        if (null != contract.getVisitorId()) {
            BaseRelatedObjects baseRelatedObjects = baseDao.get(BaseRelatedObjects.class, contract.getVisitorId());
            if (null != baseRelatedObjects) {
                contract.setVisitorName(baseRelatedObjects.getObjectName());
            }
        }
        if (null != oriContract && null != oriContract.getVisitorId()) {
            BaseRelatedObjects baseRelatedObjects = baseDao.get(BaseRelatedObjects.class, oriContract.getVisitorId());
            if (null != baseRelatedObjects) {
                oriContract.setVisitorName(baseRelatedObjects.getObjectName());
            }
        }
    }

    @Override
    public void execute(EntityProxy<VehicleSaleContracts> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(SaleContractService.class, "execute");


        StopWatch watch0 = ContractStopWatch.startWatch(SaleContractService.class, "execute", "dealDataBeforeSave");
        this.dealDataBeforeSave(entityProxy);
        ContractStopWatch.stop(watch0);

        StopWatch watch1 = ContractStopWatch.startWatch(SaleContractService.class, "execute", "updateCustomer");
        this.updateCustomer(entityProxy);
        ContractStopWatch.stop(watch1);

        StopWatch watch2 = ContractStopWatch.startWatch(SaleContractService.class, "execute", "dealInstDocument");
        this.dealInstDocument(entityProxy);
        ContractStopWatch.stop(watch2);

        StopWatch watch3 = ContractStopWatch.startWatch(SaleContractService.class, "execute", "dealBillDocument");
        this.dealBillDocument(entityProxy);
        ContractStopWatch.stop(watch3);

//        this.exportCIDToVisitor(entityProxy);
        StopWatch watch4 = ContractStopWatch.startWatch(SaleContractService.class, "execute", "updateInterestedCustomers");
        this.updateInterestedCustomers(entityProxy);
        ContractStopWatch.stop(watch4);

        StopWatch watch5 = ContractStopWatch.startWatch(SaleContractService.class, "execute", "updateContractVistior");
        this.updateContractVistior(entityProxy);
        ContractStopWatch.stop(watch5);


        if (StringUtils.equals("任何修改", getSysOptionValue(VEHICLE_CONTRACT_FLOW_RETURN_CONDITION))) {
            StopWatch watch6 = ContractStopWatch.startWatch(SaleContractService.class, "execute", "returnApproveFlowByAnyUpdate");
            this.returnApproveFlowByAnyUpdate(entityProxy);
            ContractStopWatch.stop(watch6);
        } else if (StringUtils.equals("利润或限价", getSysOptionValue(VEHICLE_CONTRACT_FLOW_RETURN_CONDITION))) {
            StopWatch watch7 = ContractStopWatch.startWatch(SaleContractService.class, "execute", "returnApproveFlowByOnlyProfitDiff");
            this.returnApproveFlowByOnlyProfitDiff(entityProxy); //注意：最低限价时要flush数据
            ContractStopWatch.stop(watch7);
        } else {
            //ADM19020030 - 任何修改均不回退
            //do nothing
        }

        ContractStopWatch.stop(watch);
    }


    /**
     * 更新意向客户
     *
     * @param entityProxy
     */
    private void updateInterestedCustomers(EntityProxy<VehicleSaleContracts> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        SysUsers user = HttpSessionStore.getSessionUser();
//        VehicleSaleContracts oriContracts = entityProxy.getOriginalEntity();
        VehicleSaleContracts contract = entityProxy.getEntity();
        //如果对应的意向客户还没有检查，则需要自动检查 20181205
        List<InterestedCustomers> interestedCustomerList = (List<InterestedCustomers>) baseDao.findByHql("FROM InterestedCustomers where relatedObjectId = ?", contract.getCustomerId());
        if (interestedCustomerList != null && interestedCustomerList.size() > 0) {
            InterestedCustomers interestedCustomer = interestedCustomerList.get(0);
            if (interestedCustomer.getCheckTime() == null) {
                interestedCustomer.setCheckTime(new Timestamp(System.currentTimeMillis()));
                interestedCustomer.setChecker(user.getUserFullName());
                interestedCustomer.setCheckResult("正常");
                interestedCustomer.setCheckContent("客户已成交，系统自动检查");
            }
        }

    }


    /**
     * 处理Vistior
     *
     * @param entityProxy
     */
    private void updateContractVistior(EntityProxy<VehicleSaleContracts> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        VehicleSaleContracts oriContracts = entityProxy.getOriginalEntity();
        VehicleSaleContracts contract = entityProxy.getEntity();
        if (entityProxy.getOperation() == Operation.CREATE) {
            if (StringUtils.isNotEmpty(contract.getVisitorNo())) {
                this.fillVisitor(contract);

            }
        } else if (entityProxy.getOperation() == Operation.UPDATE) {
            if (!StringUtils.equals(contract.getVisitorNo(), oriContracts.getVisitorNo())) {
                //如果是更换意向客户
                this.clearVisitor(oriContracts.getVisitorNo());
                this.fillVisitor(contract);
            }

        }
    }


//    private void WriteCustomerChangeHistory(EntityProxy<VehicleSaleContracts> entityProxy) {
//        if (entityProxy.getOperation() == Operation.CREATE || entityProxy.getOperation() == Operation.UPDATE) {
//            logger.debug(String.format("执行客户更改历史，%s", entityProxy));
//            VehicleSaleContracts contract = entityProxy.getEntity();
//
//            BaseRelatedObjects object = new BaseRelatedObjects();
//            object.setObjectId(contract.getCustomerId());
//            object.setObjectName(contract.getCustomerName());
//            object.setNamePinyin(GetChineseFirstChar.getFirstLetter(contract.getCustomerName()));
//            object.setObjectName(contract.getCustomerName());
//            object.setNamePinyin(GetChineseFirstChar.getFirstLetter(contract.getCustomerName()));
//            object.setSex(contract.getCusotmerSex());
//            object.setCertificateType(contract.getCustomerCertificateType());
//            object.setCertificateNo(contract.getCustomerCertificateNo());
//            object.setLinkman(contract.getLinkman());
//            object.setPhone(contract.getCustomerPhone());
//            object.setMobile(contract.getCustomerMobile());
//            object.setEmail(contract.getCustomerEmail());
//            object.setAddress(contract.getCustomerAddress());
//            object.setPostalcode(contract.getCustomerPostcode());
//            object.setEducation(contract.getCustomerEducation());
//            object.setOccupation(contract.getCustomerOccupation());
//            object.setProvince(contract.getCustomerProvince());
//            object.setCity(contract.getCustomerCity());
//            object.setArea(contract.getCustomerArea());
//            customerHistoryService.WriteCustomerChangeHistory(object);
//        }
//    }

    private void dealDataBeforeSave(EntityProxy<VehicleSaleContracts> entityProxy) {
        SysUsers user = HttpSessionStore.getSessionUser();
        VehicleSaleContracts oriContracts = entityProxy.getOriginalEntity();
        VehicleSaleContracts contracts = entityProxy.getEntity();
        if (entityProxy.getOperation() == Operation.UPDATE) {
            List<FinanceDocumentEntries> entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? AND documentType = '车辆-销售合同'", contracts.getContractNo());
            if (entriesList != null && entriesList.size() > 0) {
                double receiveMoney = 0.00D;
                FinanceDocumentEntries entries = entriesList.get(0);
                if (Tools.toDouble(entries.getDocumentAmount()) - Tools.toDouble(entries.getLeftAmount()) + Tools.toDouble(entries.getUsedCredit()) > Tools.toDouble(entries.getDocumentAmount())) {
                    receiveMoney = Tools.toDouble(entries.getDocumentAmount());
                } else {
                    receiveMoney = Tools.toDouble(entries.getDocumentAmount()) - Tools.toDouble(entries.getLeftAmount()) + Tools.toDouble(entries.getUsedCredit());
                }

                if (!StringUtils.equals(oriContracts.getCustomerId(), contracts.getCustomerId()) && receiveMoney > 0.00D) {
                    throw new ServiceException("该销售合同款项已处理，不能再变更客户");
                }
                entries.setObjectId(contracts.getCustomerId());
                entries.setObjectNo(contracts.getCustomerNo());
                entries.setObjectName(contracts.getCustomerName());
            }
        }

        if (entityProxy.getOperation() == Operation.CREATE) {
            contracts.setCreator(user.getUserFullName());
            contracts.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }

        if (entityProxy.getOperation() != Operation.DELETE) {
            contracts.setModifier(user.getUserFullName());
            contracts.setModifyTime(new Timestamp(System.currentTimeMillis()));
        }
        int contractQ = getContractQuantity(entityProxy);
        contracts.setContractQuantity(contractQ);
        short iContractStatus = 1;
        if (contractQ == Tools.toInt(contracts.getArrivedQuantity())) {
            iContractStatus = 2;
        } else if (Tools.toInt(contracts.getArrivedQuantity()) == 0) {
            iContractStatus = 0;
        }
        contracts.setContractStatus(iContractStatus);

        //算佣金 和车款合计
        double mDeposit = 0D, vehicle_price_total = 0D;
        List<EntityProxy<VehicleSaleContractDetailGroups>> groupProxies = entityProxy.getSlaves(VehicleSaleContractDetailGroups.class.getSimpleName());
        for (EntityProxy groupProxy : groupProxies) {
            if (groupProxy.getOperation() == Operation.DELETE) {
                continue;
            }
            List<EntityProxy<?>> detailProxies = groupProxy.getSlaves(VehicleSaleContractDetail.class.getSimpleName());
            for (EntityProxy detailProxy : detailProxies) {
                if (detailProxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                VehicleSaleContractDetail detail = (VehicleSaleContractDetail) detailProxy.getEntity();
                mDeposit += Tools.toDouble(detail.getDeposit());
                if (detail.getApproveStatus() != 30) {
                    vehicle_price_total += Tools.toDouble(detail.getVehiclePriceTotal());
                }

            }
        }

        if ("车辆录入".equals(getSysOptionValue(VEHICLE_DEPOSIT_INPUT_TYPE))) {
            contracts.setDeposit(mDeposit);
        }

        contracts.setContractMoney(vehicle_price_total);
    }

    private int getContractQuantity(EntityProxy<VehicleSaleContracts> entityProxy) {
        int contractQ = 0;
        if (entityProxy.getOperation() == Operation.DELETE) {
            return contractQ;
        }
        List<EntityProxy<VehicleSaleContractDetailGroups>> groupProxies = entityProxy.getSlaves(VehicleSaleContractDetailGroups.class.getSimpleName());
        for (EntityProxy groupProxy : groupProxies) {
            if (groupProxy.getOperation() == Operation.DELETE) {
                continue;
            }
            List<EntityProxy<?>> detailProxies = groupProxy.getSlaves(VehicleSaleContractDetail.class.getSimpleName());
            for (EntityProxy detailProxy : detailProxies) {
                if (detailProxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                contractQ++;
            }
        }

        return contractQ;
    }


    private void updateCustomer(EntityProxy<VehicleSaleContracts> entityProxy) {

        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        VehicleSaleContracts oriContracts = entityProxy.getOriginalEntity();
        VehicleSaleContracts contract = entityProxy.getEntity();

        if (StringUtils.isEmpty(contract.getCustomerId())) {
            throw new ServiceException("导出客户档案失败,档案中的客户ID不存在");
        }

        BaseRelatedObjects relObj = baseDao.get(BaseRelatedObjects.class, contract.getCustomerId());
        if (relObj == null || Tools.toShort(relObj.getStatus()) <= 0) {
            throw new ServiceException("导出客户档案失败,档案中的客户不存在或者客户已被禁用");
        }

        SysUsers user = HttpSessionStore.getSessionUser();
        // 更新客户档案
        if (StringUtils.isEmpty(relObj.getStationId())) {
            relObj.setStationId(user.getDefaulStationId());
        }
        relObj.setObjectName(contract.getCustomerName());
        relObj.setNamePinyin(GetChineseFirstChar.getFirstLetter(contract.getCustomerName()));
        relObj.setSex(contract.getCusotmerSex());
        relObj.setCertificateType(contract.getCustomerCertificateType());
        relObj.setCertificateNo(contract.getCustomerCertificateNo());
        relObj.setLinkman(contract.getLinkman());
        relObj.setPhone(contract.getCustomerPhone());
        relObj.setMobile(contract.getCustomerMobile());
        relObj.setEmail(contract.getCustomerEmail());
        relObj.setAddress(contract.getCustomerAddress());
        relObj.setPostalcode(contract.getCustomerPostcode());
        relObj.setEducation(contract.getCustomerEducation());
        relObj.setOccupation(contract.getCustomerOccupation());
        relObj.setProvince(contract.getCustomerProvince());
        relObj.setCity(contract.getCustomerCity());
        relObj.setArea(contract.getCustomerArea());
        relObj.setObjectType(Tools.toInt(relObj.getObjectType()) | 2);
        relObj.setObjectKind(Tools.toInt(relObj.getObjectKind()) | 5);
        //不同步plan_back_time
//        relObj.setPlanBackTime(new Timestamp(System.currentTimeMillis()));
        relObj.setBackFlag(true);
        relObj.setCustomerType((short) 30);
        relObj.setModifier(user.getUserName());
        relObj.setModifyTime(new Timestamp(System.currentTimeMillis()));
        //增加校验 validateCustomer
        validateCustomer(relObj);

        //写入往来对象的历史记录， WriteCustomerChangeHistory(EntityProxy<VehicleSaleContracts> entityProxy)方法废弃
        customerHistoryService.WriteCustomerChangeHistory(relObj);
    }


    /**
     * 验证客户合法性
     */
    private void validateCustomer(BaseRelatedObjects customers) {
        short nObjectNature = Tools.toShort(customers.getObjectNature(), (short) 10);

        //如果为单位，对象名称不允许重复
        if (nObjectNature == InterestedCustomersService.OBJECT_NATURE_UNIT) {
            //单位客户。客户名称不允许重复，电话号码可以重复
            //合同只验证 base_related_objects
            String sql = "SELECT object_id,object_name,mobile,certificate_no,short_name,creator,\n" +
                    "a.maintainer_name AS workgroup_name\n" +
                    "FROM base_related_objects a WHERE  (status is null or status = 1) AND (a.object_name=:objectName \n" +
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
                        if ("东贸版".equals(sysOptionsDao.getOptionForString(InterestedCustomersService.DV_VERSION))
                                && (InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER.equals(customers.getCertificateNo()) || InterestedCustomersService.DEFAULT_CER_NO_UNIT.equals(customers.getCertificateNo()))) {
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
                    addRepeatCheck(customers, reasonItem);
                    String errMsg = String.format("客户已存在(%s) 已存在客户名称:%s, 建档人:%s", reason, (String) reasonItem.get("object_name"), (String) reasonItem.get("creator"))
                            + (StringUtils.isEmpty((String) reasonItem.get("workgroup_name")) ? "" : String.format(", 维系人:%s", reasonItem.get("workgroup_name")));
                    throw new ServiceException(errMsg);
                }
            }
        } else {
            //个人客户。客户名称允许重复，电话号码不允许重复
            String sql = "SELECT object_id,object_name,object_nature,mobile,certificate_no,short_name,creator,\n" +
                    "                        a.maintainer_name AS workgroup_name \n" +
                    "                    FROM base_related_objects a WHERE (status is null or status = 1) AND (a.object_name=:objectName OR a.mobile=:mobile OR (a.certificate_no IS NOT NULL \n" +
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
                        if ("东贸版".equals(sysOptionsDao.getOptionForString(InterestedCustomersService.DV_VERSION))
                                && (InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER.equals(customers.getCertificateNo()) || InterestedCustomersService.DEFAULT_CER_NO_UNIT.equals(customers.getCertificateNo()))) {
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
                    addRepeatCheck(customers, reasonItem);
                    String errMsg = String.format("客户已存在(%s) 已存在客户名称:%s, 建档人:%s", reason, (String) reasonItem.get("object_name"), (String) reasonItem.get("creator"))
                            + (StringUtils.isEmpty((String) reasonItem.get("workgroup_name")) ? "" : String.format(", 维系人:%s", reasonItem.get("workgroup_name")));
                    throw new ServiceException(errMsg);
                }
            }
        }
    }


    /**
     * 添加重复客户检核
     */
    private void addRepeatCheck(BaseRelatedObjects customer, Map<String, Object> result) {
        logger.debug("addRepeatCheck开始");
        SysUsers user = HttpSessionStore.getSessionUser();

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
     * 处理单据分录信息
     *
     * @param entityProxy
     */
    private void dealBillDocument(EntityProxy<VehicleSaleContracts> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }

        VehicleSaleContracts oriContracts = entityProxy.getOriginalEntity();
        VehicleSaleContracts contract = entityProxy.getEntity();

        double dContractAmount = Tools.toDouble(contract.getContractMoney());//当前合同金额

        double dChargeIncome = 0.00D;
        //begin 如果VEHICLE_CHARGE_FDE_SEPARATE为是，车辆销售合同费用 单独产生单据分录 -20190108
        if ("是".equals(getSysOptionValue(VEHICLE_CHARGE_FDE_SEPARATE))) {
            dChargeIncome = getChargeIncome(entityProxy, false); //当前费用收入
        }
        dContractAmount -= dChargeIncome;
        //end

        Timestamp dtArTime = new Timestamp(System.currentTimeMillis());
        List<FinanceDocumentEntries> entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? AND  documentType IN ('车辆-销售合同','车辆-销售合同变更')", contract.getContractNo());

        double dContractAmountOri = oriContracts == null ? 0.00D : Tools.toDouble(oriContracts.getContractMoney()); //未修改前合同金额

        double dChargeIncomeOri = 0.00D;
        //begin 如果VEHICLE_CHARGE_FDE_SEPARATE为是，车辆销售合同费用 单独产生单据分录 -20190108
        if ("是".equals(getSysOptionValue(VEHICLE_CHARGE_FDE_SEPARATE))) {
            dChargeIncomeOri = getChargeIncome(entityProxy, true);
        }
        dContractAmountOri -= dChargeIncomeOri;
        //end

        double dLoanAmount = getLoanAmount(entityProxy);//当前合同已做预算的贷款金额
        double dFristAmount = dContractAmount - dLoanAmount;//此份合同应该产生单据分录的应收金额

        if (dFristAmount > 0 && (entriesList == null || entriesList.size() == 0)) {
            financeDocumentEntriesDao.insertEntryEx(contract.getStationId(), (short) 19, (short) 15, "车辆-销售合同", contract.getContractNo(), contract.getCustomerId(), contract.getCustomerNo(), contract.getCustomerName(), (short) 20, dFristAmount, null, null, dtArTime);
            return;
        }

        double dDocumentAmount = 0, dLeftAmount = 0, dLeftAmountCanUse = 0, dUsedCredit = 0, dPaidAmount = 0, dDocumentAmountVary = 0, dLeftAmountVary = 0, dUsedCreditVary = 0, dRequestAmount = 0;
        String sAfterNo = "", sAfterNoVary = "", sDocumentId = "", sDocumentIdVary = "";

        List<FinanceDocumentEntries> contractEntriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? AND  documentType ='车辆-销售合同'", contract.getContractNo());
        if (contractEntriesList != null && contractEntriesList.size() > 0) {
            FinanceDocumentEntries entries = contractEntriesList.get(0);
            dDocumentAmount = Tools.toDouble(entries.getDocumentAmount());
            dLeftAmount = Tools.toDouble(entries.getLeftAmount());
            dUsedCredit = Tools.toDouble(entries.getUsedCredit());
            dPaidAmount = dDocumentAmount - dLeftAmount + dUsedCredit;//实际收款(含授信)
            if (dPaidAmount > dDocumentAmount) {
                dPaidAmount = dDocumentAmount;
            }
            dLeftAmountCanUse = dDocumentAmount - dPaidAmount;//合同金额变小要时，判断是否足于处理变化量时需用此金额
            sAfterNo = entries.getAfterNo();
            sDocumentId = entries.getDocumentId();
        }

        List<FinanceDocumentEntries> varyEntriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? AND  documentType ='车辆-销售合同变更'", contract.getContractNo());
        if (varyEntriesList != null && varyEntriesList.size() > 0) {
            FinanceDocumentEntries entries = varyEntriesList.get(0);

            dDocumentAmountVary = Tools.toDouble(entries.getDocumentAmount());
            dLeftAmountVary = Tools.toDouble(entries.getLeftAmount());
            dUsedCreditVary = Tools.toDouble(entries.getUsedCredit());
            sAfterNoVary = entries.getAfterNo();
            sDocumentIdVary = entries.getDocumentId();
            String sql = "SELECT SUM(a.request_amount) request_amount FROM dbo.finance_payment_requests_details a\n" + "INNER JOIN dbo.finance_payment_requests b ON b.document_no = a.document_no AND b.status=50\n" + "WHERE a.entry_id ='" + entries.getEntryId() + "'";
            List<Object> list = baseDao.executeSQLQuery(sql);
            if (list != null && list.size() > 0) {
                Object obj = list.get(0);
                dRequestAmount = Tools.toDouble((Double) obj);
            }
        }

        //变更了客户
        if (contract != null && oriContracts != null && (!StringUtils.equals(contract.getCustomerId(), oriContracts.getCustomerId()) || !StringUtils.equals(contract.getCustomerName(), oriContracts.getCustomerName()))) {
            if (StringUtils.isNotEmpty(sAfterNo) || StringUtils.isNotEmpty(sAfterNoVary)) {
                throw new ServiceException("合同单据，财务已做后续处理，不能变更签约客户");
            }

            //如果已经做了预算不能变更客户
            String sql = "SELECT document_no FROM dbo.vehicle_loan_budget WHERE sale_contract_no='" + contract.getContractNo() + "' AND flow_status<>2 AND flow_status<>4";
            List<Object> list = baseDao.executeSQLQuery(sql);
            if (list != null && list.size() > 0) {
                throw new ServiceException("该合同已经建立预算单，不能变更签约客户");
            }
            //能变更客户，则所有单据都肯定没后续操作，有变更单则直接删除变更单，在“车辆-销售合同”上修改
            if (varyEntriesList != null && varyEntriesList.size() > 0) {
                FinanceDocumentEntries entries = varyEntriesList.get(0);
                baseDao.delete(entries);
            }

            if (dFristAmount > 0) {
                FinanceDocumentEntries entries = contractEntriesList.get(0);
                entries.setDocumentAmount(dFristAmount);
                entries.setLeftAmount(dFristAmount);
                entries.setObjectId(contract.getCustomerId());
                entries.setObjectNo(contract.getCustomerNo());
                entries.setObjectName(contract.getCustomerName());
                entries.setArapTime(dtArTime);
            } else//如果全额贷款
            {
                FinanceDocumentEntries entries = contractEntriesList.get(0);
                baseDao.delete(entries);
            }

            return;
        }

        //进入以下代码肯定没修改客户
        //如果金额变大或不变,直接修改原单
        double dContractAmountVary = dContractAmount - dContractAmountOri;//合同金额增量
        if (dContractAmountVary >= 0) {
            //无变更单
            if (varyEntriesList == null || varyEntriesList.size() == 0) {
                if (contractEntriesList != null && contractEntriesList.size() > 0) {
                    FinanceDocumentEntries entries = contractEntriesList.get(0);
                    entries.setDocumentAmount(dDocumentAmount + dContractAmountVary);
                    entries.setLeftAmount(dLeftAmount + dContractAmountVary);
                    entries.setArapTime(dtArTime);
                }
            } else {
                if (dDocumentAmountVary - dRequestAmount - dContractAmountVary > 0)//优先处理变更单
                {
                    FinanceDocumentEntries entries = varyEntriesList.get(0);
                    entries.setDocumentAmount(dDocumentAmountVary - dContractAmountVary);
                    entries.setLeftAmount(dLeftAmountVary - dContractAmountVary);
                    entries.setArapTime(dtArTime);
                } else {
                    if (StringUtils.isEmpty(sAfterNoVary))//有变更单但未使用（包括请款）
                    {
                        FinanceDocumentEntries varyEntries = varyEntriesList.get(0);
                        baseDao.delete(varyEntries);

                        FinanceDocumentEntries entries = contractEntriesList.get(0);
                        entries.setDocumentAmount(dDocumentAmount + dContractAmountVary - dLeftAmountVary);
                        entries.setLeftAmount(dLeftAmount + dContractAmountVary - dLeftAmountVary);
                        entries.setArapTime(dtArTime);
                    } else {
                        //单据从document_amount变成dRequestAmount，说明单据减少了(dDocumentAmountVary-dRequestAmount)
                        FinanceDocumentEntries varyEntries = varyEntriesList.get(0);
                        varyEntries.setDocumentAmount(dRequestAmount);
                        varyEntries.setLeftAmount(dLeftAmountVary - (dDocumentAmountVary - dRequestAmount));
                        varyEntries.setArapTime(dtArTime);

                        //【车辆-销售合同】单据增加合同变更单未处理完的那部分金额
                        FinanceDocumentEntries entries = contractEntriesList.get(0);
                        entries.setDocumentAmount(dDocumentAmount + dContractAmountVary - (dDocumentAmountVary - dRequestAmount));
                        entries.setLeftAmount(dLeftAmount + dContractAmountVary - (dDocumentAmountVary - dRequestAmount));
                        entries.setArapTime(dtArTime);
                    }
                }

            }
        } else {//如果合同金额变小，则需要考虑是否产生预收款
            //判断【车辆-销售合同】是否足于处理，不足于处理则在【车辆-销售合同变更】单中处理
            if (dLeftAmountCanUse + dContractAmountVary >= 0)//原单剩余可用金额足于处理
            {
                //全额贷款时删除
                if (dLeftAmount + dContractAmountVary == 0 && dDocumentAmount + dContractAmountVary == 0 && StringUtils.isEmpty(sAfterNo)) {
                    FinanceDocumentEntries entries = contractEntriesList.get(0);
                    baseDao.delete(entries);
                    return;
                }

                FinanceDocumentEntries entries = contractEntriesList.get(0);
                entries.setDocumentAmount(dDocumentAmount + dContractAmountVary);
                entries.setLeftAmount(dLeftAmount + dContractAmountVary);
                entries.setArapTime(dtArTime);

                return;
            } else {//如果已多收，需要产生变更单
                //dPaidAmount - (dDocumentAmount - dLeftAmount) 表示未恢复的授信，授信也要当成已收款算，但实际仍可收款
                FinanceDocumentEntries entries = contractEntriesList.get(0);
                entries.setDocumentAmount(dPaidAmount);
                entries.setLeftAmount(dPaidAmount - (dDocumentAmount - dLeftAmount));
                entries.setArapTime(dtArTime);

                if (StringUtils.isEmpty(sDocumentIdVary))//如果原来无变更单，则生成
                {
                    financeDocumentEntriesDao.insertEntryEx(contract.getStationId(), (short) 17, (short) 70, "车辆-销售合同变更", contract.getContractNo(), contract.getCustomerId(), contract.getCustomerNo(), contract.getCustomerName(), (short) 10, dPaidAmount - dFristAmount, null, null, dtArTime);

                } else {//如果原来已经有变更单，则更新
                    FinanceDocumentEntries varyEntries = varyEntriesList.get(0);
                    varyEntries.setDocumentAmount(dPaidAmount - dFristAmount);
                    varyEntries.setLeftAmount((dPaidAmount - dFristAmount) - (dDocumentAmountVary - dLeftAmountVary));
                    varyEntries.setArapTime(dtArTime);
                }
            }

        }
    }

    //统计合同的费用金额
    private double getChargeIncome(EntityProxy<VehicleSaleContracts> entityProxy, boolean ori) {
        BigDecimal amount = BigDecimal.ZERO;
        String slaveNamePath = String.format("%s.%s.%s", VehicleSaleContractDetailGroups.class.getSimpleName(), VehicleSaleContractDetail.class.getSimpleName(), VehicleSaleContractCharge.class.getSimpleName());
        List<EntityProxy<?>> chargeProxyList = EntityProxyUtil.getDescendants(entityProxy, slaveNamePath);
        if (chargeProxyList != null && chargeProxyList.size() > 0) {
            for (EntityProxy<?> chargeProxy : chargeProxyList) {
                if (ori) {
                    //如果取原来的值，不要新增的，其他取原值
                    if (chargeProxy.getOperation() == Operation.CREATE) {
                        continue;
                    }
                    VehicleSaleContractCharge oriCharge = (VehicleSaleContractCharge) chargeProxy.getOriginalEntity();
                    amount = amount.add(Tools.toBigDecimal(oriCharge.getIncome()));
                } else {
                    //如果取现在的值，不要删除的，其他取现在值
                    if (chargeProxy.getOperation() == Operation.DELETE) {
                        continue;
                    }
                    VehicleSaleContractCharge charge = (VehicleSaleContractCharge) chargeProxy.getEntity();
                    amount = amount.add(Tools.toBigDecimal(charge.getIncome()));
                }
            }
        }
        return amount.doubleValue();
    }


    //
    private double getLoanAmount(EntityProxy<VehicleSaleContracts> entityProxy) {
        VehicleSaleContracts contract = entityProxy.getEntity();
        double val = 0.00D;
        String sql = "SELECT SUM(a.loan_amount) loan_amount FROM dbo.vehicle_loan_budget_details a\n" + "LEFT JOIN dbo.vehicle_loan_budget b ON b.document_no = a.document_no\n" + "WHERE b.flow_status IN (1,30,40,45,50,60,70) AND a.sale_contract_detail_id IN\n" + "(select contract_detail_id from vehicle_sale_contract_detail  where contract_no='" + contract.getContractNo() + "' AND approve_status<>'30')";

        List<Object> list = baseDao.executeSQLQuery(sql);
        if (list != null && list.size() > 0) {
            Object obj = list.get(0);
            val = Tools.toDouble((Double) obj);
        }
        return val;
    }


    /**
     * 处理保险分录
     *
     * @param entityProxy
     */
    private void dealInstDocument(EntityProxy<VehicleSaleContracts> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }

        VehicleSaleContracts oriContracts = entityProxy.getOriginalEntity();
        VehicleSaleContracts contract = entityProxy.getEntity();
        //如果不需要创建保险预收款
        if (!isInsuranceAdvancesReceived(contract.getStationId())) {
            List<FinanceDocumentEntries> entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? AND  documentType='车辆-保险预收' AND afterNo IS NULL", contract.getContractNo());
            if (entriesList != null && entriesList.size() > 0) {
                baseDao.delete(entriesList.get(0));
            }
            return;
        }

        Timestamp dt = contract.getPlanFinishTime() == null ? new Timestamp(System.currentTimeMillis()) : contract.getPlanFinishTime();
        double dAmountOri = getInsuranceAmountTotal(contract.getContractNo());
        double dAmount = getCurrentInsuranceAmountTotal(entityProxy);
        if (entityProxy.getOperation() == Operation.CREATE) {
            if (dAmount > 0) {
                financeDocumentEntriesDao.insertEntryEx(contract.getStationId(), (short) 18, (short) 15, "车辆-保险预收", contract.getContractNo(), contract.getCustomerId(), contract.getCustomerNo(), contract.getCustomerName(), (short) 10, dAmount, null, null, dt);
            }
        } else {
            List<FinanceDocumentEntries> entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? AND  documentType='车辆-保险预收' ", contract.getContractNo());
            //如果之前没有保险，编辑时增加保险
            if ((entriesList == null || entriesList.size() == 0) && dAmount > 0) {
                financeDocumentEntriesDao.insertEntryEx(contract.getStationId(), (short) 18, (short) 15, "车辆-保险预收", contract.getContractNo(), contract.getCustomerId(), contract.getCustomerNo(), contract.getCustomerName(), (short) 10, dAmount, null, null, dt);
                return;
            }

            //如果原金额与现金额相同时更新对象
            if (dAmount == dAmountOri) {
                for (FinanceDocumentEntries entries : entriesList) {
                    //注意：原方法中更新了主键，我这边没更新
                    entries.setObjectId(contract.getCustomerId());
                    entries.setObjectNo(contract.getCustomerNo());
                    entries.setObjectName(contract.getCustomerName());
                }
                return;
            }

            FinanceDocumentEntries entries = entriesList.get(0);
            double dPriceReduce = dAmount - dAmountOri;//减少的金额
            double dPricePaid = Tools.toDouble(entries.getDocumentAmount()) - Tools.toDouble(entries.getLeftAmount()); //实际预收金额;
            String sObjectName = entries.getObjectName();

            if (dAmount >= dPricePaid) {
                entries.setObjectId(contract.getCustomerId());
                entries.setObjectNo(contract.getCustomerNo());
                entries.setObjectName(contract.getCustomerName());
                entries.setDocumentAmount(dAmount);
                entries.setLeftAmount(dAmount - dPricePaid);
            } else {
                //实际预收比准备预收还多，分录变成实际预收金额；就算多收也可通过请预收款进行退钱，所以不需要另外产生变更单
                entries.setObjectId(contract.getCustomerId());
                entries.setObjectNo(contract.getCustomerNo());
                entries.setObjectName(contract.getCustomerName());
                entries.setDocumentAmount(dPricePaid);
                entries.setLeftAmount(0D);
            }
        }
    }


//    /**
//     * 导出客户ID至售前客户表
//     *
//     * @param entityProxy
//     */
//    private void exportCIDToVisitor(EntityProxy<VehicleSaleContracts> entityProxy) {
//        if (entityProxy.getOperation() == Operation.DELETE) {
//            return;
//        }
//
//        VehicleSaleContracts oriContracts = entityProxy.getOriginalEntity();
//        VehicleSaleContracts contract = entityProxy.getEntity();
//        if (StringUtils.isEmpty(contract.getVisitorNo()) || (entityProxy.getOperation() == Operation.UPDATE && StringUtils.equals(contract.getCustomerId(), oriContracts.getCustomerId()))) {
//            return;
//        }
//
//        PresellVisitors visitor = baseDao.get(PresellVisitors.class, contract.getVisitorNo());
//        if (visitor != null) {
//            visitor.setVisitorId(contract.getCustomerId());
//        }
//    }


    //清除跟进信息
    public void clearVisitor(String _sVisitorNo) {
        if (StringUtils.isEmpty(_sVisitorNo)) {
            return;
        }

        String[] arrVisitor = _sVisitorNo.split(",");
        for (String strVisitor : arrVisitor) {
            PresellVisitors visitor = baseDao.get(PresellVisitors.class, strVisitor);
            if (visitor == null) {
                continue;
            }

            visitor.setVisitResult(null);
            visitor.setReason(null);
            visitor.setSelfOpinion(null);
            visitor.setUsedFlag(null);
            visitor.setFinishDate(null);
//            visitor.setSellerId(null);
//            visitor.setSeller(null);
            visitor.setModifyTime(new Timestamp(System.currentTimeMillis()));
            visitor.setIntentLevel(visitor.getIntentLevelBak());
        }
    }

    //回填跟进信息
    public void fillVisitor(VehicleSaleContracts contracts) {
        if (contracts == null || StringUtils.isEmpty(contracts.getVisitorNo())) {
            return;
        }

        String _sVisitorNo = contracts.getVisitorNo();
        String[] arrVisitor = _sVisitorNo.split(",");
        for (String strVisitor : arrVisitor) {
            PresellVisitors visitor = baseDao.get(PresellVisitors.class, strVisitor);
            if (visitor == null) {
                continue;
            }

            visitor.setVisitResult((short) 10);
            visitor.setReason("服务到位");
            visitor.setSelfOpinion("系统自动回填");
            visitor.setIntentLevel("成交");
            visitor.setUsedFlag(true);
            visitor.setSellerId(contracts.getSellerId());
            visitor.setSeller(contracts.getSeller());
            visitor.setModifyTime(new Timestamp(System.currentTimeMillis()));
            if (visitor.getFinishDate() == null) {
                visitor.setFinishDate(new Timestamp(System.currentTimeMillis()));
            }

            this.updateVisitorBack(visitor, contracts);

        }
    }


    /**
     * 有效客户管理中，当线索成交后，待跟进的跟进计划自动回填跟进信息：合同成交。跟进状态自动变为已跟进。不继续跟进。
     *
     * @param visitor
     * @param contracts
     */
    private void updateVisitorBack(PresellVisitors visitor, VehicleSaleContracts contracts) {
        if (visitor == null) {
            return;
        }

        SysUsers seller = baseDao.get(SysUsers.class, contracts.getSellerId());
        if (seller == null) {
            return;
        }

        //查找对应的销售员的未跟进的线索
        List<PresellVisitorsBack> backList = (List<PresellVisitorsBack>) baseDao.findByHql("FROM PresellVisitorsBack where objectId = ? AND creator = ? AND realBackTime is null", visitor.getVisitorId(), seller.getUserFullName());
        if (backList != null && backList.size() > 0) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            for (PresellVisitorsBack back : backList) {
                back.setRealBackTime(now);
                back.setBacker(seller.getUserFullName());
                back.setBackContent("合同成交");
                back.setModifier(seller.getUserFullName());
                back.setModifyTime(now);
                back.setIsContinueBack(false);


                //跟进目的
                if (StringUtils.isEmpty(back.getBackPurposeTmp())) {
                    back.setBackPurposeTmp("促成成交");
                }

                //跟进方式
                if (StringUtils.isEmpty(back.getBackWay())) {
                    back.setBackWay("电话");
                }


            }
        }
    }


//    /**
//     * 处理意向客户跟踪结果和跟踪明细信息
//     *
//     * @param _bIsFinish  为True时表示完成回访，为False时表示恢复待回访
//     * @param _sVisitorNo
//     */
//    public void dealVisitor(boolean _bIsFinish, String _sVisitorNo) {
//        if (StringUtils.isEmpty(_sVisitorNo)) {
//            return;
//        }
//
//        String[] arrVisitor = _sVisitorNo.split(",");
//        for (String strVisitor : arrVisitor) {
//            PresellVisitors visitor = baseDao.get(PresellVisitors.class, strVisitor);
//            if (visitor == null) {
//                continue;
//            }
//
//            if (_bIsFinish) {
//                visitor.setVisitResult((short) 10);
//                visitor.setReason("服务到位");
//                visitor.setSelfOpinion("系统自动回填");
//                visitor.setIntentLevel("成交");
//                visitor.setUsedFlag(true);
//                visitor.setFinishDate(new Timestamp(System.currentTimeMillis()));
//            } else {
//                visitor.setVisitResult(null);
//                visitor.setReason(null);
//                visitor.setSelfOpinion(null);
//                visitor.setUsedFlag(null);
//                visitor.setFinishDate(null);
//                visitor.setIntentLevel(visitor.getIntentLevelBak());
//            }

    // presell_visitors 表的处还要，presell_visitors_back的可以不需要了 20181205
    //处理回访信息
//            if (_bIsFinish) {
//                List<PresellVisitorsBack> backList = (List<PresellVisitorsBack>) baseDao.findByHql("FROM PresellVisitorsBack WHERE visitorNo = ? AND realBackTime IS NULL", strVisitor);
//                if (backList != null && backList.size() > 0) {
//                    PresellVisitorsBack back = backList.get(0);
//                    back.setRealBackTime(new Timestamp(System.currentTimeMillis()));
//                    back.setBackSchedule("联系");
//                    back.setBackContent("系统自动回填");
//                    back.setBackWay("信息拜访");
//                    back.setBackVisitorLevel("80%以上");
//                    back.setBackIntentLevel("成交");
//                }
//            } else {
//                List<PresellVisitorsBack> backList = (List<PresellVisitorsBack>) baseDao.findByHql("FROM PresellVisitorsBack WHERE visitorNo = ? AND backContent='系统自动回填'", strVisitor);
//                if (backList != null && backList.size() > 0) {
//                    PresellVisitorsBack back = backList.get(0);
//                    back.setRealBackTime(null);
//                    back.setBackContent(null);
//                    back.setBackVisitorLevel(null);
//                    back.setBackIntentLevel(null);
//                }
//            }
//        }
//    }


//    /**
//     * 设置默认联系人
//     *
//     * @param entityProxy
//     */
//    private void setDefaultMaintenace(EntityProxy<VehicleSaleContracts> entityProxy) {
//        if (entityProxy.getOperation() == Operation.DELETE) {
//            return;
//        }
//
//        VehicleSaleContracts oriContracts = entityProxy.getOriginalEntity();
//        VehicleSaleContracts contract = entityProxy.getEntity();
//
//        List<BaseRelatedObjectMaintenace> maintenacelist = (List<BaseRelatedObjectMaintenace>) baseDao.findByHql("FROM BaseRelatedObjectMaintenace WHERE objectId = ? AND userId = ?", contract.getCustomerId(), contract.getSellerId());
//        if (maintenacelist == null || maintenacelist.size() == 0) {
//            String sql = "SELECT a.user_id,a.user_name,d.default_station FROM sys_users a\n" + "LEFT JOIN sys_users_roles AS b ON a.user_id = b.user_id\n" + "LEFT JOIN sys_roles AS c ON b.role_id = c.role_id\n" + "LEFT JOIN dbo.sys_units d ON a.department=d.unit_id\n" + "WHERE role_type LIKE '%35%' AND a.user_id=:userId";
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("userId", contract.getSellerId());
//            List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
//            if (list != null && list.size() > 0) {
//                Map<String, Object> result = list.get(0);
//                BaseRelatedObjectMaintenace maintenace = new BaseRelatedObjectMaintenace();
//                maintenace.setObjectMaintenaceId(UUID.randomUUID().toString());
//                maintenace.setObjectId(contract.getCustomerId());
//                maintenace.setStationId((String) result.get("default_station"));
//                maintenace.setUserId(contract.getSellerId());
//                maintenace.setBusiness(10);
//                maintenace.setIsManager(false);
//                baseDao.save(maintenace);
//            }
//
//        }
//    }

    //需要忽略的字段
    private static final String[] Vehicle_Sale_Contracts_ignore_property = new String[]{"modifier"};
    private static final String[] Vehicle_Sale_Contract_Detail_Groups_ignore_property = new String[]{"vehiclePriceTotal", "vehicleCostRef", "minProfit", "minSalePrice"};

    private static final String[] Vehicle_Sale_Contract_Detail_ignore_property = new String[]{"warehouseId", "warehouseName", "vehicleId", "vehicleColor", "vehicleVin", "vehicleCost", "vehicleCostRef",
            "vehicleVinNew", "vehicleNameNew", "minSalePrice", "minProfit", "vehicleEngineType", "vehicleEngineNo", "conversionCost",
            "planDeliverTime", "realDeliverTime", "vehiclePriceTotal"};

    /**
     * 回退审批--任何修改
     *
     * @param entityProxy
     */
    private void returnApproveFlowByAnyUpdate(EntityProxy<VehicleSaleContracts> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }

        if (!StringUtils.equals("任何修改", getSysOptionValue(VEHICLE_CONTRACT_FLOW_RETURN_CONDITION))) {
            return;
        }

        //ADM19010054 销售合同状态回退条件条件：“任何修改”条件下，需要排除 1、配车 2、编辑后未做修改就保存
        //这两种情况是不需要回退的。
        boolean isChanged = false;
        if (this.compareEntity != null) {
            isChanged = EntityProxyUtil.compareEntityIsChanged(this.compareEntity, new CompareEntity.Compare() {
                @Override
                public boolean compareProperty(CompareEntity entity, CompareEntity.CompareProperty property) {
                    if (VehicleSaleContracts.class.getSimpleName().equals(entity.getEntityClassName())) {
                        if (Arrays.asList(Vehicle_Sale_Contracts_ignore_property).contains(property.getPropertyName())) {
                            return false;
                        }
                    } else if (VehicleSaleContractDetailGroups.class.getSimpleName().equals(entity.getEntityClassName())) {
                        if (Arrays.asList(Vehicle_Sale_Contract_Detail_Groups_ignore_property).contains(property.getPropertyName())) {
                            return false;
                        }
                    } else if (VehicleSaleContractDetail.class.getSimpleName().equals(entity.getEntityClassName())) {
                        //配车or换车需要排除，
                        //planDeliverTime 解决手机的问题，planDeliverTime客户端修改不算 vehiclePriceTotal 客户端修改不算
                        if (Arrays.asList(Vehicle_Sale_Contract_Detail_ignore_property).contains(property.getPropertyName())) {
                            return false;
                        }
                    }
                    return true;
                }
            });
        }

        //ADM19010054 销售合同状态回退条件条件：“任何修改”条件下，需要排除 1、配车 2、编辑后未做修改就保存
        //这两种情况是不需要回退的。
        if (isChanged) {
            int nCount = 0;
            VehicleSaleContracts contract = entityProxy.getEntity();
            List<EntityProxy<VehicleSaleContractDetailGroups>> groupProxies = entityProxy.getSlaves(VehicleSaleContractDetailGroups.class.getSimpleName());
            for (EntityProxy groupProxy : groupProxies) {
                if (groupProxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                List<EntityProxy<?>> detailProxies = groupProxy.getSlaves(VehicleSaleContractDetail.class.getSimpleName());
                for (EntityProxy detailProxy : detailProxies) {
                    if (detailProxy.getOperation() == Operation.DELETE) {
                        continue;
                    }
                    VehicleSaleContractDetail detail = (VehicleSaleContractDetail) detailProxy.getEntity();
                    if (detail.getRealDeliverTime() == null) {
                        nCount++;
                    }
                }
            }
            if (nCount > 0) {
                doReturnApproveFlow(entityProxy);
            }
        }
    }


    /**
     * 回退审批
     *
     * @param entityProxy
     */
    private void doReturnApproveFlow(EntityProxy<VehicleSaleContracts> entityProxy) {
        VehicleSaleContracts contract = entityProxy.getEntity();
        if (contract == null) {
            return;
        }

        List<EntityProxy<VehicleSaleContractDetailGroups>> groupProxies = entityProxy.getSlaves(VehicleSaleContractDetailGroups.class.getSimpleName());
        for (EntityProxy groupProxy : groupProxies) {
            if (groupProxy.getOperation() == Operation.DELETE) {
                continue;
            }
            List<EntityProxy<?>> detailProxies = groupProxy.getSlaves(VehicleSaleContractDetail.class.getSimpleName());
            for (EntityProxy detailProxy : detailProxies) {
                if (detailProxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                VehicleSaleContractDetail detail = (VehicleSaleContractDetail) detailProxy.getEntity();
                if (detail.getRealDeliverTime() == null) {
                    detail.setApproveStatus((short) 0);
                    detail.setStatus((short) 10);
                }
            }
        }


        contract.setStatus((short) 10);

        //ApproveDocuments的状态也变成10
        List<ApproveDocuments> approveDocumentsList = (List<ApproveDocuments>) baseDao.findByHql("FROM ApproveDocuments  where documentNo = ? AND moduleId = ?", contract.getDocumentNo(), "102020");
        if (approveDocumentsList != null && approveDocumentsList.size() > 0) {
            ApproveDocuments approveDocuments = approveDocumentsList.get(0);
            approveDocuments.setStatus((short) 10);
        }

        contract.setIsChanging(true);
        deleteApprovingPoint(contract.getContractNo());
    }


    /**
     * 回退审批--利润或最低限价
     *
     * @param entityProxy
     */
    private void returnApproveFlowByOnlyProfitDiff(EntityProxy<VehicleSaleContracts> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        if (StringUtils.equals("任何修改", getSysOptionValue(VEHICLE_CONTRACT_FLOW_RETURN_CONDITION))) {
            return;
        }

        VehicleSaleContracts contract = entityProxy.getEntity();

        //如果是新增合同，直接处理
        if (entityProxy.getOperation() == Operation.CREATE) {
            doReturnApproveFlow(entityProxy);
            return;
        }


        StopWatch watch0 = ContractStopWatch.startWatch(SaleContractService.class, "execute", "returnApproveFlowByOnlyProfitDiff", "查询vw_vehicle_sale_contract_detail_groups");
        String sql = "select group_id,profit_diff,income_tot-cost_tot as profit_tot, income_tot,cost_tot,income_tot,cost_tot,price_sale,vehicle_price from vw_vehicle_sale_contract_detail_groups  WITH ( NOLOCK ) where contract_no=:contractNo ";
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("contractNo", contract.getContractNo());
        List<Map<String, Object>> oriList = baseDao.getMapBySQL(sql, params); //数据的原始值
        ContractStopWatch.stop(watch0);

        StopWatch watch1 = ContractStopWatch.startWatch(SaleContractService.class, "execute", "returnApproveFlowByOnlyProfitDiff", "flush");
        baseDao.flush(); //数据flush到数据库中，为了取得现在的值来进行比较
        ContractStopWatch.stop(watch1);

        //从view查出来的是现在的值
        sql = "select * from vw_vehicle_sale_contract_detail_groups  WITH ( NOLOCK ) WHERE contract_no=:contractNo ";
        StopWatch watch2 = ContractStopWatch.startWatch(SaleContractService.class, "execute", "returnApproveFlowByOnlyProfitDiff", "查询vw_vehicle_sale_contract_detail_groups-2");
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        ContractStopWatch.stop(watch2);


        boolean bNeedReturn = false;//是否需要回退
        int nCount = 0;
        List<EntityProxy<VehicleSaleContractDetailGroups>> groupProxies = entityProxy.getSlaves(VehicleSaleContractDetailGroups.class.getSimpleName());
        for (EntityProxy groupProxy : groupProxies) {
            if (groupProxy.getOperation() == Operation.DELETE) {//删除分组不需判断
                continue;
            }
            VehicleSaleContractDetailGroups group = (VehicleSaleContractDetailGroups) groupProxy.getEntity();

            boolean bMustCheck = groupProxy.getOperation() == Operation.CREATE ? true : false;
            boolean b1 = false;
            boolean b2 = false;
            double profitDiff = 0.00D, profitDiffOri = 0.00D;
            double profitTot = 0.00D, profitTotOri = 0.00D;
            double incomeTot = 0.00D, costTot = 0.00D;
            double vehiclePrice = 0.00D, vehiclePriceOri = 0.00D;
            double priceSale = 0.00D;

            if (list != null && list.size() > 0) {
                for (Map<String, Object> result : oriList) {
                    if (StringUtils.equals(result.get("group_id").toString(), group.getGroupId())) {
                        profitDiff = Tools.toDouble((Double) result.get("profit_diff"));
                        incomeTot = Tools.toDouble((Double) result.get("income_tot"));
                        costTot = Tools.toDouble((Double) result.get("cost_tot"));
                        vehiclePrice = Tools.toDouble((Double) result.get("vehicle_price"));
                        priceSale = Tools.toDouble((Double) result.get("price_sale"));
                        profitTot = incomeTot - costTot;
                    }
                }
            }

            if (oriList != null && oriList.size() > 0) {
                for (Map<String, Object> oriResult : oriList) {
                    if (StringUtils.equals(oriResult.get("group_id").toString(), group.getGroupId())) {
                        profitDiffOri = Tools.toDouble((Double) oriResult.get("profit_diff"));
                        profitTotOri = Tools.toDouble((Double) oriResult.get("profit_tot"));
                        vehiclePriceOri = Tools.toDouble((Double) oriResult.get("vehicle_price"));
                    }
                }
            }


            //如果其中有组预估利润小于设定或利润小于0的，需要回退，再次提交审批（利润差额和总利润没发生变化时，不回退）。
            if (bMustCheck) {
                b1 = true;
            } else if (profitDiff == profitDiffOri && profitTot == profitTotOri) {
                b1 = false;
            } else if (profitDiff < 0 || incomeTot - costTot < 0) {
                b1 = true;
            } else {
                b1 = false;
            }

            if (bMustCheck) {
                b2 = true;
            } else if (vehiclePrice == vehiclePriceOri) {
                b2 = false;
            } else if (priceSale != 0 && vehiclePrice < priceSale) {
                b2 = true;
            } else {
                b2 = false;
            }

            if (b1 || b2) {
                List<EntityProxy<?>> detailProxies = groupProxy.getSlaves(VehicleSaleContractDetail.class.getSimpleName());
                for (EntityProxy detailProxy : detailProxies) {
                    if (detailProxy.getOperation() == Operation.DELETE) {
                        continue;
                    }
                    VehicleSaleContractDetail detail = (VehicleSaleContractDetail) detailProxy.getEntity();
                    VehicleSaleContractDetail oriDetail = (VehicleSaleContractDetail) detailProxy.getOriginalEntity();
                    if (detail.getRealDeliverTime() == null) {//未出库的才回退状态
                        detail.setApproveStatus((short) 0);
                        detail.setStatus((short) 10);
                        nCount++;
                    }
                    bNeedReturn = true;

                }
            }

            int nStatus = Tools.toShort(contract.getStatus(), (short) 10);
            if (bMustCheck && nStatus > 10 && nStatus <= 50) {
                bNeedReturn = true;
            }

            if (bNeedReturn && nCount > 0) {
                break;
            }
        }

        if (bNeedReturn && nCount > 0) {
            doReturnApproveFlow(entityProxy);
        } else {
            //如果合同是已同意，且不需要回退，则把合同明细的状态变成已审批
            if (Tools.toShort(contract.getStatus()) == 50) {
                logger.debug(contract.getContractNo() + "已同意且不需要回退，把合同明细的状态变成已审批");
                for (EntityProxy groupProxy : groupProxies) {
                    if (groupProxy.getOperation() == Operation.DELETE) {//删除分组不需判断
                        continue;
                    }

                    List<EntityProxy<?>> detailProxies = groupProxy.getSlaves(VehicleSaleContractDetail.class.getSimpleName());
                    for (EntityProxy detailProxy : detailProxies) {
                        if (detailProxy.getOperation() == Operation.DELETE) {
                            continue;
                        }
                        VehicleSaleContractDetail detail = (VehicleSaleContractDetail) detailProxy.getEntity();

                        //审批中的合同明细
                        if (detail.getRealDeliverTime() == null && Tools.toShort(detail.getStatus()) == 10) {
                            logger.debug(detail.getContractDetailId() + "合同明细状态变成已审批");
                            detail.setApproveStatus((short) 1);
                            detail.setStatus((short) 50); //已同意
                        }
                    }
                }
            }
        }

    }


    //主单状态回退为制单中时，删除正在审批的审批点
    private void deleteApprovingPoint(String contractNo) {
        List<ApproveDocuments> documentsList = (List<ApproveDocuments>) baseDao.findByHql("FROM ApproveDocuments WHERE documentNo = ?", contractNo);
        if (documentsList == null || documentsList.size() == 0) {
            return;
        }
        List<ApproveDocumentsPoints> pointsList = (List<ApproveDocumentsPoints>) baseDao.findByHql("FROM ApproveDocumentsPoints WHERE documentId = ? AND status = 30", documentsList.get(0).getDocumentId());

        for (ApproveDocumentsPoints point : pointsList) {
            baseDao.delete(point);
        }

        SysUsers user = HttpSessionStore.getSessionUser();
        //回退时，需要往审批点表增加一条数据，避免客户误解为什么审批流上显示已审了
        ApproveDocumentsPoints rtnPonit = new ApproveDocumentsPoints();
        rtnPonit.setOadpId(UUID.randomUUID().toString());
        rtnPonit.setDocumentId(documentsList.get(0).getDocumentId());
        rtnPonit.setStatus((short) 70);
        rtnPonit.setApproveLevel(-1);
        rtnPonit.setApproveName("审批完成前撤销");
        rtnPonit.setApproverId(user.getUserId());
        rtnPonit.setApproverNo(user.getUserNo());
        rtnPonit.setApproverName(user.getUserName());
        rtnPonit.setApproveTime(new Timestamp(System.currentTimeMillis()));
        rtnPonit.setRemark("编辑自动回退");

        baseDao.save(rtnPonit);
    }


    private double getCurrentInsuranceAmountTotal(EntityProxy<VehicleSaleContracts> entityProxy) {
        double val = 0.00D;
        List<EntityProxy<VehicleSaleContractDetailGroups>> groupProxies = entityProxy.getSlaves(VehicleSaleContractDetailGroups.class.getSimpleName());
        for (EntityProxy group : groupProxies) {
            if (group.getOperation() == Operation.DELETE) {
                continue;
            }
            List<EntityProxy<?>> detailProxies = group.getSlaves(VehicleSaleContractDetail.class.getSimpleName());
            for (EntityProxy detailProxy : detailProxies) {
                if (detailProxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                VehicleSaleContractDetail detail = (VehicleSaleContractDetail) detailProxy.getEntity();
                //含保费的去掉
                if (detail.getIsContainInsuranceCost()) {
                    continue;
                }

                List<EntityProxy<?>> insurancesProxies = detailProxy.getSlaves(VehicleSaleContractInsurance.class.getSimpleName());
                if (insurancesProxies != null && insurancesProxies.size() > 0) {
                    for (EntityProxy insurancesProxy : insurancesProxies) {
                        if (insurancesProxy.getOperation() == Operation.DELETE) {
                            continue;
                        }
                        VehicleSaleContractInsurance insurance = (VehicleSaleContractInsurance) insurancesProxy.getEntity();
                        if (Tools.toShort(insurance.getAbortStatus(), (short) 20) == 10) {
                            continue;
                        }
                        val += Tools.toDouble(insurance.getCategoryIncome());
                    }
                }
            }
        }

        return val;
    }

    //原保险金额
    private double getInsuranceAmountTotal(String contractNo) {
        double val = 0.00D;
        String str = "SELECT SUM(ISNULL(category_income,0)) category_income FROM vehicle_sale_contract_insurance " + "WHERE contract_detail_id IN (" + "SELECT ISNULL(a.contract_detail_id,'') FROM vehicle_sale_contract_detail a INNER JOIN vehicle_sale_contracts b ON a.contract_no=b.contract_no WHERE ISNULL(a.abort_status,20)<>10 AND b.contract_status<>3 AND b.contract_status<>4 AND b.contract_no='" + contractNo + "' AND ISNULL(a.is_contain_insurance_cost,0)=0 ) " + "AND ISNULL(abort_status,20)<>10  ";
        List<Object> list = baseDao.executeSQLQuery(str);
        if (list != null && list.size() > 0) {
            Object obj = list.get(0);
            val = Tools.toDouble((Double) obj);
        }

        return val;
    }


    private boolean isInsuranceAdvancesReceived(String stationId) {
        boolean val = false;
        List<SysOptions> options = sysOptionsDao.getOptionsByOptionNo(vsc_insurance_advances_received, stationId);
        if (options != null && options.size() > 0) {
            if ("是".equals(options.get(0).getOptionValue())) {
                val = true;
            }
        }
        return val;
    }


    public String getSysOptionValue(String optionNo) {
        return getSysOptionValue(optionNo, null);
    }


    public String getSysOptionValue(String optionNo, String stationId) {
        SysUsers user = HttpSessionStore.getSessionUser();
        if (StringUtils.isEmpty(stationId)) {
            stationId = user.getDefaulStationId();
        }

        String val = null;
        List<SysOptions> options = sysOptionsDao.getOptionsByOptionNo(optionNo, stationId);
        if (options != null && options.size() > 0) {
            val = options.get(0).getOptionValue();
        }
        return val;

    }


    /**
     * 查找常用选项-bigDecimal
     *
     * @return
     */
    public BigDecimal getSysOptionDecimal(String optionNo) {
        BigDecimal val = BigDecimal.ZERO;

        SysUsers user = HttpSessionStore.getSessionUser();
        List<SysOptions> options = sysOptionsDao.getOptionsByOptionNo(optionNo, user.getDefaulStationId());
        if (options != null && options.size() > 0) {
            val = new BigDecimal(options.get(0).getOptionValue());
        }

        return val;

    }


}
