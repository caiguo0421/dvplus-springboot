package cn.sf_soft.vehicle.loan.service.impl;

import cn.sf_soft.basedata.dao.SysLogsDao;
import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.dao.FinanceDocumentEntriesDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleLoanBudget;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.file.service.FileService;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.support.*;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.customer.model.SysOptions;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetDetailsGroup;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("loanBudgetService")
public class LoanBudgetService extends BaseService<VehicleLoanBudget> {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoanBudgetService.class);

    private static EntityRelation entityRelation = null;

    static {
        entityRelation = new EntityRelation(VehicleLoanBudget.class, LoanBudgetService.class);
        entityRelation.addSlave("documentNo", LoanBudgetDetailsGroupService.class);
    }

    /**
     * 是否要求销售合同车辆已审批
     */
    public static final String VEHICLE_BUDGET_NEED_SVC_APPROVED = "VEHICLE_BUDGET_NEED_SVC_APPROVED";

    /**
     * 四舍五入规则
     */
    public static final String VEHICLE_LOAN_MONTH_PAY_DECIMAL_RULE = "VEHICLE_LOAN_MONTH_PAY_DECIMAL_RULE";

    /**
     * 消贷分期月供小数位数
     */
    public static final String VEHICLE_LOAN_MONTH_PAY_DECIMAL_DIGITS = "VEHICLE_LOAN_MONTH_PAY_DECIMAL_DIGITS";

    private static final String SALE_DOCUMENT_TYPE = "车辆-销售合同";
    private static final String SALE_DOCUMENT_TYPE_VARY = "车辆-销售合同变更";
    private static final String SALE_LOAN = "消贷-客户贷款";

    @Autowired
    private SysCodeRulesService sysCodeRulesService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysOptionsDao sysOptionsDao;

    @Autowired
    private SysLogsDao sysLogsDao;

    @Autowired
    private FinanceDocumentEntriesDao financeDocumentEntriesDao;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private LoanBudgetParameterConvert loanBudgetParameterConvert;

    @Override
    public ParameterConverter getDefaultConvert() {
        return loanBudgetParameterConvert;
    }


    //初始值
    public Map<String, Object> getInitData() {
        Map<String, Object> initData = new HashMap<>();
        initData.put("money_type", baseDao.findByHql("FROM SysFlags where fieldNo=?", "money_type"));

        Map<String, Object> sysOptions = new HashMap<>(2);
        initData.put("sys_options", sysOptions);

        initData.put("default_charge_catalog", getDefaultChargeCatalog());
        return initData;
    }


    //默认费用
    public List<Map<String, Object>> getDefaultChargeCatalog() {
        SysUsers user = HttpSessionStore.getSessionUser();
        String sql = "SELECT *\n" +
                "FROM (SELECT a.charge_id, a.charge_name, a.charge_type, a.account_no, a.forbid_flag, a.remark, a.object_id,\n" +
                "\t\t\t a.object_no, a.object_name, a.default_money, a.money_type, a.direction, a.accrued_type,\n" +
                "\t\t\t a.accrued_value, a.default_cost, a.station_ids, b.meaning AS charge_type_meaning,\n" +
                "\t\t\t c.meaning AS default_flag_meaning, d.meaning AS direction_meaning,\n" +
                "\t\t\t e.meaning AS money_type_meaning, f.meaning AS accrued_type_meaning,\n" +
                "\t\t\t ISNULL (g.default_flag, a.default_flag) AS default_flag,\n" +
                "\t\t\t ISNULL (g.default_condition, a.default_condition) AS default_condition\n" +
                "\t  FROM charge_catalog AS a\n" +
                "\t  LEFT JOIN (SELECT code, meaning FROM sys_flags WHERE field_no = 'charge_type') AS b ON a.charge_type = b.code\n" +
                "\t  LEFT JOIN (SELECT code, meaning FROM sys_flags WHERE field_no = 'is_yes') AS c ON a.default_flag = c.code\n" +
                "\t  LEFT JOIN (SELECT code, meaning FROM sys_flags WHERE field_no = 'direction') AS d ON a.direction = d.code\n" +
                "\t  LEFT JOIN (SELECT code, meaning FROM sys_flags WHERE field_no = 'money_type') AS e ON a.money_type = e.code\n" +
                "\t  LEFT JOIN (SELECT code, meaning FROM sys_flags WHERE field_no = 'accrued_type') AS f ON a.accrued_type = f.code\n" +
                "\t  LEFT JOIN dbo.charge_catalog_detail AS g ON a.charge_id = g.charge_id AND g.station_id = '" + user.getLoginStationId() + "'\n" +
                "\t  WHERE charge_type = 40 AND ISNULL (forbid_flag, 0) = 0) a\n" +
                "WHERE 1 = 1 AND ISNULL(default_flag,0)=1  AND (ISNULL(station_ids,'')='' OR station_ids LIKE '%" + user.getLoginStationId() + "%') ";

        return baseDao.getMapBySQL(sql, new HashMap<String, Object>());
    }


    private BigDecimal objToBigDecimal(Object val) {
        if (val == null) {
            return BigDecimal.ZERO;
        }
        if (val instanceof String) {
            return new BigDecimal((String) val);
        }

        return Tools.toBigDecimal((Number) val);
    }

    /**
     * 计算月供
     *
     * @param map
     * @return
     */
    public Map<String, Object> calculationMonthPay(Map<String, Object> map) {
        Map<String, Object> rtnMap = new HashMap<>(2);
        short nRateType = 10; //计息方式
        if (map.get("rate_type") != null) {
            BigDecimal val = objToBigDecimal(map.get("rate_type"));
            if (val.compareTo(BigDecimal.ZERO) == 0) {
                nRateType = 10;
            } else {
                nRateType = val.shortValue();
            }
        }

        BigDecimal periodNumber = BigDecimal.ZERO;
        if (map.get("period_number") != null) {
            periodNumber = objToBigDecimal(map.get("period_number"));
        }

        BigDecimal loanAmount = BigDecimal.ZERO, agent_amount = BigDecimal.ZERO, charge_loan_amount = BigDecimal.ZERO;
        if (map.get("loan_amount") != null) {
            loanAmount = objToBigDecimal(map.get("loan_amount"));
        }
        if (map.get("agent_amount") != null) {
            agent_amount = objToBigDecimal(map.get("agent_amount"));
        }
        if (map.get("charge_loan_amount") != null) {
            charge_loan_amount = objToBigDecimal(map.get("charge_loan_amount"));
        }
        BigDecimal interest_rate = BigDecimal.ZERO;
        if (map.get("interest_rate") != null) {
            interest_rate = objToBigDecimal(map.get("interest_rate"));
        }

        BigDecimal dLoanAmount = loanAmount.add(agent_amount).add(charge_loan_amount);//本金

        //计算月供和利息
        if (dLoanAmount.compareTo(BigDecimal.ZERO) > 0 && periodNumber.compareTo(BigDecimal.ZERO) > 0 && map.get("rate_type") != null) {
            //四舍五入规则和保留小数规则
            RoundingMode roundingMode = RoundingMode.HALF_UP;//默认四舍五入
            String formatType = getSysOptionValue(VEHICLE_LOAN_MONTH_PAY_DECIMAL_RULE);
            if ("向上取".equals(formatType)) {
                roundingMode = RoundingMode.CEILING;
            } else if ("向下取".equals(formatType)) {
                roundingMode = RoundingMode.FLOOR;
            } else {
                roundingMode = RoundingMode.HALF_UP;
            }
            int digit = getSysOptionDecimal(VEHICLE_LOAN_MONTH_PAY_DECIMAL_DIGITS).intValue();


            //月利率: Round(年利率*10/12,4)/1000
            BigDecimal dMothRate = interest_rate.multiply(BigDecimal.TEN).divide(new BigDecimal(12), 4, RoundingMode.HALF_UP).divide(new BigDecimal(1000));
            BigDecimal dMonthPay = BigDecimal.ZERO;
            //如果不是等额本息月供默认取第一个月的月供，不对的话用户自己调
            if (nRateType == 10) {
                //等额本息
                if (dMothRate.compareTo(new BigDecimal(0.0000000049)) < 0) {
                    dMonthPay = dLoanAmount.divide(periodNumber, digit, roundingMode);
                } else {
                    //【月供】=[贷款本金*月利率*（1+月利率）^贷款期数]÷[（1+月利率）^贷款期数－1]。X^Y表示：X的Y次方
                    dMonthPay = dLoanAmount.multiply(dMothRate).multiply(dMothRate.add(BigDecimal.ONE).pow(periodNumber.intValue())).divide(dMothRate.add(BigDecimal.ONE).pow(periodNumber.intValue()).subtract(BigDecimal.ONE), digit, roundingMode);
                }

                //刚开始剩余本金=贷款金额
                BigDecimal dSurplus = dLoanAmount;
                BigDecimal dInterestTot = BigDecimal.ZERO;
                for (int i = 1; i <= periodNumber.intValue(); i++) {
                    if (i != periodNumber.intValue()) {
                        dInterestTot = dInterestTot.add(dSurplus.multiply(dMothRate)).setScale(digit, roundingMode);//利息
                    } else {
                        //月利率为为是特殊处理
                        if (dMothRate.compareTo(BigDecimal.ZERO) != 0) {
                            dInterestTot = dInterestTot.add(dMonthPay.subtract(dSurplus));//利息
                        }
                    }
                    dSurplus = dSurplus.subtract(dMonthPay.subtract(dSurplus.multiply(dMothRate).setScale(digit, roundingMode))).setScale(2, RoundingMode.HALF_UP);
                }
                rtnMap.put("month_pay", dMonthPay);
                rtnMap.put("interest", dInterestTot);

            } else if (nRateType == 20 || nRateType == 50) {
                //等额本金计算法,50为融资租赁，公式与等额本金一样
                //每月偿还的本金
                BigDecimal dPrincipalPay = dLoanAmount.divide(periodNumber, 0, RoundingMode.CEILING);
                //刚开始剩余本金=贷款金额
                BigDecimal dSurplus = dLoanAmount;
                BigDecimal dInterestTot = BigDecimal.ZERO;
                for (int i = 1; i <= periodNumber.intValue(); i++) {
                    dInterestTot = dInterestTot.add(dSurplus.multiply(dMothRate).setScale(digit, roundingMode)); //利息
                    if (i == 1) {
                        dMonthPay = dPrincipalPay.add(dSurplus.multiply(dMothRate).setScale(digit, roundingMode));
                    }

                    dSurplus = dSurplus.subtract(dPrincipalPay);
                    if (dSurplus.compareTo(BigDecimal.ZERO) > 0) {
                        dSurplus = dSurplus.setScale(2, RoundingMode.HALF_UP);
                    } else {
                        dSurplus = BigDecimal.ZERO;
                    }
                }

                rtnMap.put("month_pay", dMonthPay);
                rtnMap.put("interest", dInterestTot);
            } else if (nRateType == 30) {
                //平息法
                //每月偿还的本金
                BigDecimal dPrincipalPay = dLoanAmount.divide(periodNumber, 0, RoundingMode.CEILING);
                //刚开始剩余本金=贷款金额
                BigDecimal dInterestTot = BigDecimal.ZERO;

                for (int i = 1; i <= periodNumber.intValue(); i++) {
                    dInterestTot = dInterestTot.add(dLoanAmount.multiply(dMothRate).setScale(digit, roundingMode)); //利息
                    if (i == 1) {
                        dMonthPay = dPrincipalPay.add(dLoanAmount.multiply(dMothRate).setScale(digit, roundingMode));
                    }
                }
                rtnMap.put("month_pay", dMonthPay);
                rtnMap.put("interest", dInterestTot);
            }
        }
        return rtnMap;
    }

    /**
     * 保存消贷预算单
     *
     * @param parameter
     * @return
     */
    public Map<String, List<Object>> saveLoanBudget(JsonObject parameter) {
        List<EntityProxy<?>> contractProxies = this.save(parameter);
        EntityProxy<VehicleLoanBudget> entityProxy = (EntityProxy<VehicleLoanBudget>) contractProxies.get(0);
        VehicleLoanBudget budget = entityProxy.getEntity();
        if (entityProxy.getOperation() != Operation.DELETE) {
            attachmentToFtp(budget);
        }
        baseDao.flush();
        this.addSysLog(entityProxy.getOperation() == Operation.CREATE ? "新建" : "修改", String.format("预算单号：%s，报文：%s", budget.getDocumentNo(), parameter.toString()));
        return convertReturnData(budget.getDocumentNo());
    }

    private void addSysLog(String logType, String description) {
        sysLogsDao.addSysLog(logType, "消贷费用预算", description);
    }

    private void attachmentToFtp(VehicleLoanBudget budget) {
        SysUsers user = HttpSessionStore.getSessionUser();
        String fileUrls = fileService.addAttachmentsToFtp(user, budget.getDocumentNo(), budget.getFileUrls());
        budget.setFileUrls(fileUrls);
    }

    public Map<String, List<Object>> convertReturnData(String documentNo) {
        Map<String, List<Object>> rtnData = new HashMap<>();
        convertReturnDataForDetail("vehicle_loan_budget", "SELECT * FROM vw_vehicle_loan_budget where document_no = :val", documentNo, rtnData);
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
            if ("vehicle_loan_budget".equals(tableName)) {
                convertReturnDataForDetail("vw_vehicle_loan_budget_details_group", "SELECT * FROM  vw_vehicle_loan_budget_details_group where document_no = :val", itemMap.get("document_no"), rtnData);
            } else if ("vw_vehicle_loan_budget_details_group".equals(tableName)) {
                convertBudgetChargeGroup(rtnData, itemMap);
//                convertReturnDataForDetail("vw_vehicle_loan_budget_charge_group", "SELECT * FROM  vw_vehicle_loan_budget_charge_group where group_id = :val", itemMap.get("group_id"), rtnData);
            }
        }
    }

    private void convertBudgetChargeGroup(Map<String, List<Object>> rtnData, Map itemMap) {
        String tableName = "vw_vehicle_loan_budget_charge_group";
        List<Object> dataList = rtnData.get(tableName);
        if (dataList == null) {
            dataList = new ArrayList<>();
            rtnData.put(tableName, dataList);
        }

        String sql = "SELECT * FROM  vw_vehicle_loan_budget_charge_group where group_id = :val AND document_no = :documentNo";
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("val", itemMap.get("group_id"));
        parmMap.put("documentNo", itemMap.get("document_no"));
        List<Object> data = baseDao.listInSql(sql, parmMap).getData();

        if (data == null || data.size() == 0) {
            return;
        }
        dataList.addAll(data);

    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleLoanBudget> entityProxy) {
        SysUsers user = HttpSessionStore.getSessionUser();
        if (entityProxy.getOperation() == Operation.CREATE) {
            VehicleLoanBudget budget = entityProxy.getEntity();
            String stationId = user.getDefaulStationId();
            String documentNo = sysCodeRulesService.createSysCodeRules("LCB_NO", stationId);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            budget.setDocumentNo(documentNo);
            budget.setStationId(stationId);
            budget.setStatus((short) 10);

            if (StringUtils.isEmpty(budget.getLoanObjectId())) {
                budget.setLoanObjectId(budget.getCustomerId());
            }
            //初始化FlowStatus
            if (budget.getFlowStatus() == null) {
                budget.setFlowStatus((short) 0);
            }
            budget.setUserId(user.getUserId());
            budget.setUserNo(user.getUserNo());
            budget.setUserName(user.getUserName());
            budget.setDepartmentId(user.getDepartment());
            budget.setSubmitStationId(stationId);

            budget.setCreatorId(user.getUserId());
            budget.setCreateTime(now);
            budget.setModifierId(user.getUserId());
            budget.setModifyTime(now);
        } else {
            VehicleLoanBudget budget = entityProxy.getEntity();
            VehicleLoanBudget oriBudget = entityProxy.getOriginalEntity();
            validateModifyTime(budget.getModifyTime(), oriBudget.getModifyTime()); //校验modifyTime

            if (Tools.toShort(oriBudget.getStatus()) == 70) {
                throw new ServiceException("已撤销的消贷预算单不能再编辑");
            }

            if (Tools.toShort(oriBudget.getStatus()) == 80) {
                throw new ServiceException("已作废的消贷预算单不能再编辑");
            }

            if (Tools.toShort(oriBudget.getStatus()) > 10 && Tools.toShort(oriBudget.getStatus()) < 50) {
//                throw new ServiceException("未审批的消贷预算单才能编辑");
                throw new ServiceException("审批中的消贷预算单不能编辑");
            }
            budget.setModifierId(user.getUserId());
            budget.setModifyTime(new Timestamp(System.currentTimeMillis()));
        }
        validateRecord(entityProxy);
    }

    //校验
    private void validateRecord(EntityProxy<VehicleLoanBudget> entityProxy) {
        Operation operation = entityProxy.getOperation();
        if (operation == Operation.DELETE) {
            return;
        }
        VehicleLoanBudget budget = entityProxy.getEntity();
        VehicleLoanBudget oriBudget = entityProxy.getOriginalEntity();
        if (budget.getLoanMode() == null) {
            throw new ServiceException("贷款模式不能为空");
        }
        if (StringUtils.isEmpty(budget.getCustomerId())) {
            throw new ServiceException("购车客户不能为空");
        }
        if (StringUtils.isEmpty(budget.getLoanObjectId())) {
            throw new ServiceException("贷款人不能为空");
        }
        if (Tools.toShort(budget.getLoanMode()) == 20 && StringUtils.isEmpty(budget.getAgentId())) {
            throw new ServiceException("贷款模式为‘代理消贷’时，代理商不能为空");
        }
    }

    @Override
    public void execute(EntityProxy<VehicleLoanBudget> entityProxy) {
        dealBillDocument(entityProxy);
        updateBuyType(entityProxy);
    }


    //处理单据分录
    private void dealBillDocument(EntityProxy<VehicleLoanBudget> entityProxy) {
        double dLoanAmountVehicle = getLoanAmountVehicle(entityProxy, "loanAmount");
        double dLoanAmountCharge = getLoanAmountCharge(entityProxy);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        VehicleLoanBudget budget = entityProxy.getEntity();
        String sContractNo = budget.getSaleContractNo();
        dealBillDocumentForContractEntry(sContractNo, dLoanAmountVehicle, dLoanAmountCharge);

        dealBillDocumentForAgent(entityProxy);
    }


    //更新销售合同付款状态
    private void updateBuyType(EntityProxy<VehicleLoanBudget> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        VehicleLoanBudget budget = entityProxy.getEntity();
        if (StringUtils.isEmpty(budget.getSaleContractNo())) {
            return;
        }

        VehicleSaleContracts contracts = baseDao.get(VehicleSaleContracts.class, budget.getSaleContractNo());
        if (contracts == null) {
            return;
        }

        //审批更新销售合同付款方式为消贷
        if (budget.getLoanType() == null) {
            //如果原销售合同付款方式是现款，更新为一证贷，否则维持原来的方式
            if (Tools.toShort(contracts.getBuyType()) == 5) {
                contracts.setBuyType((short) 30);
            }
        } else if (Tools.toShort(contracts.getBuyType()) != Tools.toShort(budget.getLoanType())) {
            contracts.setBuyType(budget.getLoanType());
        }
    }

    //处理代理贷款，对贷款人产生应付款
    private void dealBillDocumentForAgent(EntityProxy<VehicleLoanBudget> entityProxy) {
        VehicleLoanBudget budget = entityProxy.getEntity();
        //代理贷款金额（差额）
        double dAgentDiff = getLoanAmountVehicle(entityProxy, "agentAmount");
        if (dAgentDiff == 0.00D) {
            return;
        }
        List<FinanceDocumentEntries> documentEntriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries where documentType ='消贷-代理贷款' AND documentId = ?", budget.getDocumentNo());
        BaseRelatedObjects loanObject = baseDao.get(BaseRelatedObjects.class, budget.getLoanObjectId());
        if ((documentEntriesList == null || documentEntriesList.size() == 0) && dAgentDiff > 0) {
            financeDocumentEntriesDao.insertEntryEx(budget.getStationId(), (short) 19, (short) 65, "消贷-代理贷款", budget.getDocumentNo(), budget.getLoanObjectId(), loanObject.getObjectNo(), loanObject.getObjectName(), (short) 70, dAgentDiff);
            return;
        }

        FinanceDocumentEntries documentEntries = documentEntriesList.get(0);
        double dDocumentAmount = Tools.toDouble(documentEntries.getDocumentAmount()); //单据金额
        double dLeftAmount = Tools.toDouble(documentEntries.getLeftAmount()); //
        double dRequestAmount = getRequestAmount(documentEntries.getEntryId());//请款金额
        double dLeftCanUse = dDocumentAmount - dRequestAmount;
        if (dDocumentAmount + dAgentDiff == 0 && dLeftAmount + dAgentDiff == 0 && StringUtils.isEmpty(documentEntries.getAfterNo())) {
            baseDao.delete(documentEntries);
            return;
        }

        if (dLeftCanUse + dAgentDiff >= 0) {
            documentEntries.setDocumentAmount(dDocumentAmount + dAgentDiff);
            documentEntries.setLeftAmount(dLeftAmount + dAgentDiff);
        } else {
            throw new ServiceException("【消贷-代理贷款】已经请款,修改后的代理贷款金额不能小于已请款金额");
        }
    }


    /**
     * 合同分录的单据处理(反审)
     *
     * @param sContractNo        合同号
     * @param dLoanAmountVehicle 车辆贷款差额
     * @param dLoanAmountCharge  费用贷款差额
     */
    public void dealBillDocumentForContractEntryWithSab(String sContractNo, double dLoanAmountVehicle, double dLoanAmountCharge) {
        //如果为正常消贷，需处理原销售合同单据
        if (StringUtils.isNotEmpty(sContractNo)) {
            //判断是否有变更单
            List<FinanceDocumentEntries> saleEntryList = financeDocumentEntriesDao.getEntryListByDocumentNoAndType(sContractNo, SALE_DOCUMENT_TYPE);
            if (null != saleEntryList && !saleEntryList.isEmpty()) {
                FinanceDocumentEntries saleEntry = saleEntryList.get(0);
                BigDecimal dLeftAmount = Tools.toBigDecimal(saleEntry.getLeftAmount());
                BigDecimal dDocumentAmount = Tools.toBigDecimal(saleEntry.getDocumentAmount());
                BigDecimal dUsedCredit = Tools.toBigDecimal(saleEntry.getUsedCredit());//授信(担保)金额
                saleEntry.setDocumentAmount(dDocumentAmount.subtract(Tools.toBigDecimal(dLoanAmountVehicle)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                saleEntry.setLeftAmount(dLeftAmount.subtract(Tools.toBigDecimal(dLoanAmountVehicle)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                baseDao.update(saleEntry);
            } else { //如果找不到原合同单据则新增
                List<VehicleSaleContracts> vehicleSaleContracts = (List<VehicleSaleContracts>) baseDao.findByHql("from VehicleSaleContract where approveStatus in (0,1,2,20) and contractNo=?", sContractNo);
                if (null != vehicleSaleContracts && !vehicleSaleContracts.isEmpty()) {
                    VehicleSaleContracts vehicleSaleContract = vehicleSaleContracts.get(0);

                    financeDocumentEntriesDao.insertEntryEx(vehicleSaleContract.getStationId(), 19, (short) 15, SALE_DOCUMENT_TYPE, sContractNo, vehicleSaleContract.getCustomerId(), vehicleSaleContract.getCustomerNo(), vehicleSaleContract.getCustomerName(), (short) 20, -dLoanAmountVehicle, null, null, new Timestamp(System.currentTimeMillis()));
                }
            }
            //处理客户贷款
            List<FinanceDocumentEntries> loanEntryList = financeDocumentEntriesDao.getEntryListByDocumentNoAndType(sContractNo, SALE_LOAN);
            if (null != loanEntryList && !loanEntryList.isEmpty()) {
                FinanceDocumentEntries loanEntry = loanEntryList.get(0);
                BigDecimal dLeftAmountCL = Tools.toBigDecimal(loanEntry.getLeftAmount());
                BigDecimal dDocumentAmountCL = Tools.toBigDecimal(loanEntry.getDocumentAmount());
                BigDecimal dUsedCreditCL = Tools.toBigDecimal(loanEntry.getUsedCredit());
                BigDecimal dPaid = dDocumentAmountCL.subtract(dLeftAmountCL).add(dUsedCreditCL);
                if (dPaid.compareTo(dDocumentAmountCL) == 1) {
                    dPaid = dDocumentAmountCL;
                }
                //反审时dLoanAmountVehicle和dLoanAmountCharge肯定为负数
                BigDecimal loanAmountVehicle = Tools.toBigDecimal(dLoanAmountVehicle);
                BigDecimal loanAmountCharge = Tools.toBigDecimal(dLoanAmountCharge);
                BigDecimal sum = loanAmountVehicle.add(loanAmountCharge).multiply(new BigDecimal(-1));
                if (dLeftAmountCL.compareTo(sum) >= 0 && dDocumentAmountCL.compareTo(sum) == 1) {
                    BigDecimal documentAmount = dDocumentAmountCL.subtract(loanAmountVehicle.multiply(new BigDecimal(-1).subtract(loanAmountCharge)));
                    if (documentAmount.compareTo(dUsedCreditCL) == -1) {
                        throw new ServiceException("单据【消贷-客户贷款】使用了授信或担保收款，请冲红授信或担保的收款结算单后再试");
                    }
                    loanEntry.setDocumentAmount(documentAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    BigDecimal leftAmount = dLeftAmountCL.subtract(loanAmountVehicle.multiply(new BigDecimal(-1).subtract(loanAmountCharge)));
                    loanEntry.setLeftAmount(leftAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                } else if (dLeftAmountCL.compareTo(dDocumentAmountCL) == 0 && dDocumentAmountCL.compareTo(sum) == 0) {
                    if (StringUtils.isNotEmpty(loanEntry.getAfterNo())) {
                        throw new ServiceException("单据【消贷-客户贷款】财务已做了后续处理，不能删除");
                    }
                    financeDocumentEntriesDao.delete(loanEntry);
                } else {
                    throw new ServiceException("单据【消贷-客户贷款】已被处理，不能反审");
                }

                //处理费用分录

            }
        }
    }

    /**
     * 合同分录的单据处理
     *
     * @param sContractNo        合同号
     * @param dLoanAmountVehicle 车辆贷款差额
     * @param dLoanAmountCharge  费用贷款差额
     */
    public void dealBillDocumentForContractEntry(String sContractNo, double dLoanAmountVehicle, double dLoanAmountCharge) {
        //如果为正常消贷，需处理原销售合同单据，并生成挂客户的贷款
        if (StringUtils.isNotEmpty(sContractNo)) {
            //判断是否有变更单
            List<FinanceDocumentEntries> saleEntryList = financeDocumentEntriesDao.getEntryListByDocumentNoAndType(sContractNo, SALE_DOCUMENT_TYPE);
            if (saleEntryList != null && saleEntryList.size() > 0) {
                FinanceDocumentEntries saleEntry = saleEntryList.get(0);
                double dLeftAmount = Tools.toDouble(saleEntry.getLeftAmount());
                double dLeftAmountCanUse = 0;//可用剩余金额（算上授信）
                double dPaidAmount = 0;//已使用金额（包括授信担保）
                double dDocumentAmount = Tools.toDouble(saleEntry.getDocumentAmount());
                double dUsedCredit = Tools.toDouble(saleEntry.getUsedCredit());//授信(担保)金额
                String sStationId = saleEntry.getStationId();
                String sObjectId = saleEntry.getObjectId();
                String sObjectNo = saleEntry.getObjectNo();
                String sObjectName = saleEntry.getObjectName();
                dPaidAmount = dDocumentAmount - dLeftAmount + dUsedCredit;
                if (dPaidAmount > dDocumentAmount) {
                    dPaidAmount = dDocumentAmount;
                }
                dLeftAmountCanUse = dDocumentAmount - dPaidAmount;

                if (dLeftAmountCanUse >= dLoanAmountVehicle)//如果剩余金额大于当次贷款金额，直接更新原单
                {
                    if (dLoanAmountVehicle < 0)//如果贷款金额变小，说明原单据金额将增大
                    {
                        List<FinanceDocumentEntries> saleVaryEntryList = financeDocumentEntriesDao.getEntryListByDocumentNoAndType(sContractNo, SALE_DOCUMENT_TYPE_VARY);
                        if (saleVaryEntryList != null && saleVaryEntryList.size() > 0) {//存在变更单时优先处理变更单（删除或金额减小）
                            FinanceDocumentEntries saleVaryEntry = saleVaryEntryList.get(0);
                            double dLeftAmountVary = Tools.toDouble(saleVaryEntry.getLeftAmount());
                            double dDocumentAmountVary = Tools.toDouble(saleVaryEntry.getDocumentAmount());
                            double dRequestAmountVary = getRequestAmount(saleVaryEntry.getEntryId());
                            String sDocumentIdVary = saleVaryEntry.getDocumentId();
                            String sAfter_no = saleVaryEntry.getAfterNo();
                            if (StringUtils.isEmpty(sAfter_no)) {
                                if (0 >= dDocumentAmountVary + dLoanAmountVehicle)//如果变更单不足于处理
                                {
                                    baseDao.delete(saleVaryEntry);
                                    saleEntry.setDocumentAmount(dDocumentAmount - dLoanAmountVehicle - dDocumentAmountVary);
                                    saleEntry.setLeftAmount(dLeftAmount - dLoanAmountVehicle - dDocumentAmountVary);
                                    baseDao.update(saleEntry);
                                } else {
                                    saleVaryEntry.setDocumentAmount(dDocumentAmountVary + dLoanAmountVehicle);
                                    saleVaryEntry.setLeftAmount(dLeftAmountVary + dLoanAmountVehicle);
                                    baseDao.update(saleVaryEntry);
                                }
                            } else {//如果变更单有被处理过
                                if (dDocumentAmountVary - dRequestAmountVary >= -dLoanAmountVehicle)//如果变更单剩余可用金额大于贷款增减量
                                {
                                    saleVaryEntry.setDocumentAmount(dDocumentAmountVary + dLoanAmountVehicle);
                                    saleVaryEntry.setLeftAmount(dLeftAmountVary + dLoanAmountVehicle);
                                    baseDao.update(saleVaryEntry);
                                } else {//如果不足处理，更新变更单的同时要更新原单据分录
                                    saleVaryEntry.setDocumentAmount(dRequestAmountVary);
                                    saleVaryEntry.setLeftAmount(dLeftAmountVary - (dDocumentAmountVary - dRequestAmountVary));
                                    baseDao.update(saleVaryEntry);

                                    saleEntry.setDocumentAmount(dDocumentAmount - dLoanAmountVehicle - (dDocumentAmountVary - dRequestAmountVary));
                                    saleEntry.setLeftAmount(dLeftAmount - dLoanAmountVehicle - (dDocumentAmountVary - dRequestAmountVary));
                                    baseDao.update(saleEntry);
                                }
                            }
                        } else {//无变更单则直接更新
                            saleEntry.setDocumentAmount(dDocumentAmount - dLoanAmountVehicle);
                            saleEntry.setLeftAmount(dLeftAmount - dLoanAmountVehicle);
                            baseDao.update(saleEntry);
                        }

                    } else {//如果是增加贷款金额，因原单据可用剩余金额大于增减量，所以可直接更新原单
                        saleEntry.setDocumentAmount(dDocumentAmount - dLoanAmountVehicle);
                        saleEntry.setLeftAmount(dLeftAmount - dLoanAmountVehicle);
                        baseDao.update(saleEntry);
                    }
                } else {//如果可用金额不足于处理，则有可能产生变更单
                    if (dPaidAmount > 0)//更新原单据分录为的单据金额为已收金额（包括授信）
                    {
                        saleEntry.setDocumentAmount(dPaidAmount);
                        saleEntry.setLeftAmount(dPaidAmount - (dDocumentAmount - dLeftAmount));
                        baseDao.update(saleEntry);
                    }

                    //多出的金额产生预收款
                    List<FinanceDocumentEntries> saleVaryEntryList = financeDocumentEntriesDao.getEntryListByDocumentNoAndType(sContractNo, SALE_DOCUMENT_TYPE_VARY);
                    if (saleVaryEntryList != null && saleVaryEntryList.size() > 0) {
                        FinanceDocumentEntries saleVaryEntry = saleVaryEntryList.get(0);
                        double dLeftAmountVary = Tools.toDouble(saleVaryEntry.getLeftAmount());
                        double dDocumentAmountVary = Tools.toDouble(saleVaryEntry.getDocumentAmount());
                        double dRequestAmountVary = getRequestAmount(saleVaryEntry.getEntryId());
                        saleVaryEntry.setDocumentAmount(dDocumentAmountVary + (dLoanAmountVehicle - dLeftAmountCanUse));
                        saleVaryEntry.setLeftAmount(dLeftAmountVary + (dLoanAmountVehicle - dLeftAmountCanUse));
                        baseDao.update(saleVaryEntry);

                    } else {
                        if (dLoanAmountVehicle - dLeftAmountCanUse > 0) {
                            financeDocumentEntriesDao.insertEntryEx(sStationId, 19, (short) 70, SALE_DOCUMENT_TYPE_VARY, sContractNo, sObjectId, sObjectNo, sObjectName, (short) 10, dLoanAmountVehicle - dLeftAmountCanUse, null, null, new Timestamp(System.currentTimeMillis()));
                        }
                    }
                }

                //判断原来是否已经存在客户贷款，有则在原单上修改，否则新增
                List<FinanceDocumentEntries> loanEntryList = financeDocumentEntriesDao.getEntryListByDocumentNoAndType(sContractNo, SALE_LOAN);
                if (loanEntryList != null && loanEntryList.size() > 0) {
                    FinanceDocumentEntries loanEntry = loanEntryList.get(0);
                    double dLeftAmountCL = Tools.toDouble(loanEntry.getLeftAmount());
                    double dDocumentAmountCL = Tools.toDouble(loanEntry.getDocumentAmount());
                    double dUsedCreditCL = Tools.toDouble(loanEntry.getUsedCredit());
                    double dPaid = (dDocumentAmountCL - dLeftAmountCL + dUsedCreditCL) > dDocumentAmountCL ? dDocumentAmountCL : (dDocumentAmountCL - dLeftAmountCL + dUsedCreditCL);
                    double dUsableAmount = dDocumentAmountCL - dPaid;

                    if (dUsableAmount + dLoanAmountVehicle + dLoanAmountCharge < 0) {
                        throw new ServiceException("销售合同的客户贷款已被处理，客户贷款可用剩余金额不足于当次处理");
                    }
                    //判断原来的客户贷款是否足于处理
                    if (dLeftAmountCL + dLoanAmountVehicle + dLoanAmountCharge >= 0) {
                        if (dDocumentAmountCL + dLoanAmountVehicle + dLoanAmountCharge < dUsedCreditCL) {
                            throw new ServiceException("单据【消贷-客户贷款】使用了授信或担保收款，请冲红授信或担保的收款结算单后再试");

                        }
                        loanEntry.setDocumentAmount(dDocumentAmountCL + dLoanAmountVehicle + dLoanAmountCharge);
                        loanEntry.setLeftAmount(dLeftAmountCL + dLoanAmountVehicle + dLoanAmountCharge);
                        baseDao.update(loanEntry);
                    } else {
                        throw new ServiceException("销售合同的客户贷款已被处理，剩余客户贷款额不足于处理");
                    }
                } else {//原来不存在客户贷款，新增
                    financeDocumentEntriesDao.insertEntryEx(sStationId, 19, (short) 10, "消贷-客户贷款", sContractNo, sObjectId, sObjectNo, sObjectName, (short) 20, dLoanAmountVehicle + dLoanAmountCharge, sContractNo, sContractNo, new Timestamp(System.currentTimeMillis()));
                }
            } else {
                throw new ServiceException("找不到销售合同单据分录");
            }
        }
    }


    //根据entryId 查找请款金额合计
    private double getRequestAmount(String entryId) {
        double request = 0.00D;
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("SELECT a.entry_id,SUM(a.request_amount) request_amount FROM dbo.finance_payment_requests_details a").append("\r\n").append("INNER JOIN dbo.finance_payment_requests b ON b.document_no = a.document_no  WHERE  b.status=50 and a.entry_id =:entryId").append("\r\n").append("GROUP BY a.entry_id").append("\r\n");
        Map<String, Object> params = new HashMap<>();
        params.put("entryId", entryId);
        List<Map<String, Object>> resultList = baseDao.getMapBySQL(sqlBuffer.toString(), params);

        if (resultList != null && resultList.size() > 0) {
            request = Tools.toDouble((Double) resultList.get(0).get("request_amount"));
        }
        return request;
    }


    //获取车辆贷款差额
    private double getLoanAmountVehicle(EntityProxy<VehicleLoanBudget> entityProxy, String fieldName) {
        double dAmount = 0.00D;
        List<EntityProxy<VwVehicleLoanBudgetDetailsGroup>> groupProxies = entityProxy.getSlaves(VwVehicleLoanBudgetDetailsGroup.class.getSimpleName());
        if (groupProxies != null && groupProxies.size() > 0) {
            for (EntityProxy<VwVehicleLoanBudgetDetailsGroup> detailsGroupEntityProxy : groupProxies) {
                List<EntityProxy<VehicleLoanBudgetDetails>> budgetDetaillsProxies = detailsGroupEntityProxy.getSlaves(VehicleLoanBudgetDetails.class.getSimpleName());
                if (budgetDetaillsProxies != null && budgetDetaillsProxies.size() > 0) {
                    for (EntityProxy<VehicleLoanBudgetDetails> budgetDetailsEntityProxy : budgetDetaillsProxies) {
                        if (budgetDetailsEntityProxy.getOperation() == Operation.DELETE || budgetDetailsEntityProxy.getOperation() == Operation.CREATE) {
                            continue;
                        }
                        VehicleLoanBudgetDetails budgetDetails = budgetDetailsEntityProxy.getEntity();
                        VehicleLoanBudgetDetails oriBudgetDetails = budgetDetailsEntityProxy.getOriginalEntity();
                        if (Tools.toShort(budgetDetails.getStatus()) == 1) {//审批后修改贷款金额
                            if (fieldName.equals("agentAmount")) {
                                dAmount += Tools.toDouble(budgetDetails.getAgentAmount()) - Tools.toDouble(oriBudgetDetails.getAgentAmount());
                            } else {
                                dAmount += Tools.toDouble(budgetDetails.getLoanAmount()) - Tools.toDouble(oriBudgetDetails.getLoanAmount());
                            }
                        }
                    }
                }
            }
        }
        return dAmount;
    }

    //获取费用贷款差额
    private double getLoanAmountCharge(EntityProxy<VehicleLoanBudget> entityProxy) {
        double dAmount = 0.00D;
        List<EntityProxy<VwVehicleLoanBudgetDetailsGroup>> groupProxies = entityProxy.getSlaves(VwVehicleLoanBudgetDetailsGroup.class.getSimpleName());
        if (groupProxies != null && groupProxies.size() > 0) {
            for (EntityProxy<VwVehicleLoanBudgetDetailsGroup> detailsGroupEntityProxy : groupProxies) {
                List<EntityProxy<VehicleLoanBudgetDetails>> budgetDetaillsProxies = detailsGroupEntityProxy.getSlaves(VehicleLoanBudgetDetails.class.getSimpleName());
                if (budgetDetaillsProxies != null && budgetDetaillsProxies.size() > 0) {
                    for (EntityProxy<VehicleLoanBudgetDetails> budgetDetailsEntityProxy : budgetDetaillsProxies) {
                        VehicleLoanBudgetDetails budgetDetails = budgetDetailsEntityProxy.getEntity();
                        List<EntityProxy<VehicleLoanBudgetCharge>> budgetChargeProxies = budgetDetailsEntityProxy.getSlaves(VehicleLoanBudgetCharge.class.getSimpleName());
                        if (budgetDetailsEntityProxy.getOperation() == Operation.DELETE || budgetDetailsEntityProxy.getOperation() == Operation.CREATE) {
                            continue;
                        }
                        if (budgetChargeProxies != null && budgetChargeProxies.size() > 0) {
                            for (EntityProxy<VehicleLoanBudgetCharge> budgetChargeEntityProxy : budgetChargeProxies) {
                                VehicleLoanBudgetCharge budgetCharge = budgetChargeEntityProxy.getEntity();
                                VehicleLoanBudgetCharge oriBudgetCharge = budgetChargeEntityProxy.getOriginalEntity();
                                if (Tools.toShort(budgetDetails.getStatus()) == 1) {//审批后追加费用或终止费用
                                    if (Tools.toByte(budgetCharge.getStatus()) == 30 && Tools.toByte(oriBudgetCharge.getStatus()) == 30) {
                                        //本来已经终止的跳过
                                        continue;
                                    }

                                    if (budgetChargeEntityProxy.getOperation() == Operation.CREATE && Tools.toByte(budgetCharge.getStatus()) == 20) {
                                        //追加费用
                                        dAmount += Tools.toDouble(budgetCharge.getLoanAmount());
                                    } else if (budgetChargeEntityProxy.getOperation() != Operation.CREATE && Tools.toByte(budgetCharge.getStatus()) == 30) {
                                        //终止费用
                                        dAmount -= Tools.toDouble(budgetCharge.getLoanAmount());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return dAmount;
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


    public static void main(String[] args) {
        BigDecimal b = new BigDecimal("13.145");
//        BigDecimal b = BigDecimal.valueOf(13.145);
        System.out.println(b);
        System.out.println(b.setScale(2, RoundingMode.HALF_UP));
    }


}
