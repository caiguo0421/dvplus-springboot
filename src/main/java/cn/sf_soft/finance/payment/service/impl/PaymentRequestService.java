package cn.sf_soft.finance.payment.service.impl;

import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.basedata.service.SysBaseDataService;
import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.file.service.FileService;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinancePaymentRequests;
import cn.sf_soft.office.approval.model.FinancePaymentRequestsDetails;
import cn.sf_soft.office.approval.service.ApprovalService;
import cn.sf_soft.user.model.SysUnits;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.customer.model.SysOptions;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("paymentRequestService")
public class PaymentRequestService {
    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PaymentRequestService.class);

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private static final String MODULE_ID = "401010";

    @Autowired
    private SysOptionsDao sysOptionsDao;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private SysCodeRulesService sysCodeService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysBaseDataService sysBaseDataService;

    /**
     * 获取初始数据
     *
     * @return
     */
    public Map<String, Object> getInitData() {
        SysUsers user = HttpSessionStore.getSessionUser();
        List<SysOptions> options = sysOptionsDao.getOptionsByOptionNo("CORPORATION_NAME", user.getLoginStationId());
        Map<String, Object> map = new HashMap<String, Object>(2);

        if (options == null || options.size() == 0) {
            throw new ServiceException("获取公司名称失败(optionNo:CORPORATION_NAME)");
        }
        map.put("payer", options.get(0).getOptionValue());

        String sql = "SELECT code, meaning FROM sys_flags WHERE field_no = 'amount_type' AND code NOT IN (20, 30, 35, 38, 40, 88) order by sort_no ";
        List<Object> requestTypes = baseDao.listInSql(sql, null).getData();
        map.put("requestTypes", requestTypes);

        String detailTypeSql = "SELECT type_id, parent_id, \n"
                + "type_name, settle_type FROM base_settlement_types WHERE type_id IN \n"
                + "(SELECT a.type_id FROM base_settlement_types a RIGHT JOIN (SELECT full_id \n"
                + "FROM base_settlement_types WHERE type_id NOT IN (SELECT parent_id FROM \n"
                + "base_settlement_types WHERE parent_id IS NOT NULL) AND status = 1 ) b ON b.full_id LIKE a.full_id + '%')";
        List<Object> detailTypes = baseDao.listInSql(detailTypeSql, null).getData();
        map.put("detailTypes", detailTypes);

        Map<String, Object> baseOhters = new HashMap<>(4);
        baseOhters.put("PAYMENT_MODE", sysBaseDataService.getBaseOthers("PAYMENT_MODE", user.getDefaulStationId()));
        map.put("base_others", baseOhters);

        return map;
    }


    /**
     * 业务单据
     *
     * @return
     */
    public PageModel<Object> listDocumentEntry(Map<String, Object> filterMap, int pageNo, int pageSize) {
        String condition = buildCondition(filterMap);
        String sql = "SELECT a.*, b.station_name, c.meaning AS amount_type_meaning, d.account_bank, d.account_no\n" + "FROM ( SELECT i.*, CASE  WHEN j.amount IS NULL THEN i.left_amount WHEN i.left_amount * (j.amount - i.left_amount) >= 0 THEN 0 ELSE i.left_amount - j.amount END AS left_real\n" + "FROM finance_document_entries i\n" + "LEFT JOIN (SELECT m.entry_id, SUM(m.request_amount - m.paid_amount) AS amount FROM finance_payment_requests_details m LEFT JOIN finance_payment_requests n ON m.document_no = n.document_no\n" + "WHERE n.status < 70 GROUP BY m.entry_id) j ON i.entry_id = j.entry_id) a\n" + "LEFT JOIN (SELECT station_id, station_name FROM sys_stations ) b ON a.station_id = b.station_id\n" + "LEFT JOIN ( SELECT code, meaning FROM sys_flags WHERE field_no = 'amount_type' ) c\n" + "ON a.amount_type = c.code LEFT JOIN base_related_objects d ON a.object_id = d.object_id\n" + "WHERE (a.entry_type = 65 AND a.left_real <> 0 OR a.entry_property & 16 = 16 AND a.entry_type = 70 AND a.amount_type IN (10, 25) AND a.left_real > 0)";

        sql = sql + (StringUtils.isBlank(condition) ? "" : " AND  " + condition);

        logger.debug("sql=" + sql);
        return baseDao.listInSql(sql, null, pageNo, pageSize, null);
    }


    private String buildCondition(Map<String, Object> filterMap) {
        if (filterMap == null) {
            return null;
        }
        List<String> conditionArray = new ArrayList<String>(4);
        if (filterMap.get("objectId") != null && StringUtils.isNotBlank(filterMap.get("objectId").toString())) {
            conditionArray.add(MessageFormat.format("a.object_id = ''{0}''", filterMap.get("objectId").toString()));
        }

        if (filterMap.get("stationId") != null && StringUtils.isNotBlank(filterMap.get("stationId").toString())) {
            conditionArray.add(MessageFormat.format("a.station_id = ''{0}''", filterMap.get("stationId").toString()));
        }

        if (filterMap.get("amountType") != null && StringUtils.isNotBlank(filterMap.get("amountType").toString())) {
            conditionArray.add(MessageFormat.format("a.amount_type = {0}", filterMap.get("amountType").toString()));
        }

        if (filterMap.get("keyword") != null && StringUtils.isNotBlank(filterMap.get("keyword").toString())) {
            conditionArray.add(MessageFormat.format("(a.document_no LIKE ''%{0}%'' OR  a.document_type LIKE ''%{0}%'')", filterMap.get("keyword").toString()));
        }

        return " " + StringUtils.join(conditionArray, " AND ") + " ";

    }

    /**
     * 提交
     *
     * @param postJson
     */
    public List<ApproveDocuments> submitRequest(Map<String, Object> postJson) throws ParseException {
        SysUsers user = HttpSessionStore.getSessionUser();
        //保存
        FinancePaymentRequests requests = saveRequest(postJson);
        //提交
        ApproveResult approveResult = approvalService.submitRecord(user, requests.getDocumentNo(), MODULE_ID, true, "", requests.getModifyTime().toString(), Constant.OSType.MOBILE);
        if (approveResult.getApproveResultCode() != Constant.ApproveResultCode.APPROVE_SUCCESS) {
            throw new ServiceException("提交失败：" + approveResult.getErrMsg());
        }
        //处理附件
        String fileUrls = fileService.addAttachmentsToFtp(user, requests.getDocumentNo(), requests.getFileUrls());
        requests.setFileUrls(fileUrls);
        baseDao.update(requests);

        //返回ApproveDocuments 是为了提交后能直接跳转到审批查看界面
        return (List<ApproveDocuments>) baseDao.findByHql("from ApproveDocuments d where d.moduleId =? AND d.documentNo = ?", MODULE_ID, requests.getDocumentNo());
    }


    private FinancePaymentRequests saveRequest(Map<String, Object> postJson) throws ParseException {
        SysUsers user = HttpSessionStore.getSessionUser();
        FinancePaymentRequests requests = new FinancePaymentRequests();
        String documentNo = sysCodeService.createSysCodeRules("FPR_NO", user.getDefaulStationId());
        requests.setDocumentNo(documentNo);
        requests.setStationId(user.getDefaulStationId());
        requests.setStatus((short) 10);//制单中
        requests.setDetailType(null);
        //收款方
        if (postJson.get("objectId") == null) {
            throw new ServiceException("提交出错：收款方为空");
        }
        String objectId = postJson.get("objectId").toString();
        BaseRelatedObjects relObj = baseDao.get(BaseRelatedObjects.class, objectId);
        if (relObj == null) {
            throw new ServiceException("提交出错：未查到收款方信息，objectId=" + objectId);
        }

        requests.setObjectId(relObj.getObjectId());
        requests.setObjectNo(relObj.getObjectNo());
        requests.setObjectName(relObj.getObjectName());

        requests.setRequestAmount((Double) postJson.get("requestAmount"));
        requests.setRequestTime(new Timestamp(System.currentTimeMillis()));
        requests.setArapTime(new Timestamp(df.parse(postJson.get("arapTime").toString()).getTime()));

        requests.setAccountBank(postJson.get("accountBank") == null ? null : postJson.get("accountBank").toString());
        requests.setAccountNo(postJson.get("accountNo") == null ? null : postJson.get("accountNo").toString());

        requests.setUserId(user.getUserId());
        requests.setUserNo(user.getUserNo());
        requests.setUserName(user.getUserName());

        SysUnits depart = baseDao.get(SysUnits.class,user.getDepartment());
        requests.setDepartmentId(depart.getUnitId());
        requests.setDepartmentNo(depart.getUnitNo());
        requests.setDepartmentName(depart.getUnitName());

        requests.setIsCounted(true);
        requests.setRemark(postJson.get("remark") == null ? null : postJson.get("remark").toString());

        requests.setCreator(String.format("%s(%s)", user.getUserName(), user.getUserNo()));
        requests.setCreateTime(new Timestamp(System.currentTimeMillis()));
        requests.setModifier(String.format("%s(%s)", user.getUserName(), user.getUserNo()));
        requests.setModifyTime(new Timestamp(System.currentTimeMillis()));
        requests.setFileUrls(postJson.get("fileUrls") == null ? null : postJson.get("fileUrls").toString());
        saveRequestDetail(requests, postJson.get("chargeDetail"));

        //如果没有明细数据
        if(requests.getChargeDetail()==null ||requests.getChargeDetail().size()==0){
            SysStations stations = baseDao.get(SysStations.class, user.getDefaulStationId());
            requests.setSubmitStationId(user.getDefaulStationId());
            requests.setSubmitStationName(stations.getStationName());

            Short requestType = Short.valueOf((String)postJson.get("requestType"));
            requests.setRequestType(requestType.shortValue());
            String detailType = (String)postJson.get("detailType");
            requests.setDetailType(detailType);
        }

        baseDao.save(requests);

        return requests;
    }


    private void saveRequestDetail(FinancePaymentRequests requests, Object detail) {
        Set<FinancePaymentRequestsDetails> chargeDetailSet = requests.getChargeDetail();
        if (chargeDetailSet == null) {
            chargeDetailSet = new HashSet<FinancePaymentRequestsDetails>();
            requests.setChargeDetail(chargeDetailSet);
        }

        if (detail == null || ((ArrayList) detail).size() == 0) {
            return;
        }

        List<Map<String, Object>> chargeDetailList = (ArrayList) detail;
        for (Map<String, Object> chargeDetail : chargeDetailList) {
            FinancePaymentRequestsDetails requestsDetails = new FinancePaymentRequestsDetails();
            requestsDetails.setFprdId(UUID.randomUUID().toString());
            requestsDetails.setDocumentNo(requests.getDocumentNo());
            requestsDetails.setEntryId(chargeDetail.get("entry_id").toString());
            requestsDetails.setDocumentType(chargeDetail.get("document_type").toString());
            requestsDetails.setDocumentId(chargeDetail.get("document_id").toString());
            requestsDetails.setDocumentNoEntry(chargeDetail.get("document_no") == null ? null : chargeDetail.get("document_no").toString());

            if (chargeDetail.get("left_real") == null || Tools.objToDouble(chargeDetail.get("left_real")) == 0.00D) {
                throw new ServiceException("请款明细:" + requestsDetails.getDocumentNoEntry() + "的金额不能为0");
            }
            requestsDetails.setRequestAmount(Tools.objToDouble(chargeDetail.get("left_real")));
            requestsDetails.setPaidAmount(0.00D);

            if (requests.getRequestType() == null) {
                Double amountType = (Double) chargeDetail.get("amount_type");
                requests.setRequestType(amountType.shortValue());
            } else {
                Double amountType = (Double) chargeDetail.get("amount_type");
                if (requests.getRequestType() != amountType.shortValue()) {
                    throw new ServiceException("提交失败：明细的请款类型不一致");
                }
            }

            //请款站点
            if (requests.getSubmitStationId() == null) {
                String submitStationId = chargeDetail.get("station_id").toString();
                SysStations stations = baseDao.get(SysStations.class, submitStationId);
                requests.setSubmitStationId(submitStationId);
                requests.setSubmitStationName(stations.getStationName());
            } else {
                if (!requests.getSubmitStationId().equals(chargeDetail.get("station_id"))) {
                    throw new ServiceException("提交失败：明细分录的站点不一致");
                }
            }

            chargeDetailSet.add(requestsDetails);
            baseDao.save(requestsDetails);
        }


    }


}
