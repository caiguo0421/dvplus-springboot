package cn.sf_soft.office.approval.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.SysOptions;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import cn.sf_soft.basedata.model.BaseSettlementTypes;
import cn.sf_soft.basedata.model.SysFlags;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.office.approval.dao.FinancePaymentRequestsDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.FinancePaymentRequests;
import cn.sf_soft.office.approval.model.FinancePaymentRequestsDetails;

/**
 * 业务请款审批业务
 *
 * @author king
 * @created 2013-02-19
 */
@Service("financePaymentRequestsManager")
public class AmFinancePaymentRequests extends BaseApproveProcess {
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AmFinancePaymentRequests.class);

    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "40101020";

    /**
     * 东风接口
     */
    private static final String DFS_INTERFACE_IS_VALID = "DFS_INTERFACE_IS_VALID";

    @Resource
    private FinancePaymentRequestsDao financePaymentRequestsDao;

    @Resource
    private SysOptionsDao sysOptionsDao;

    private static final NumberFormat nf = new DecimalFormat("##.##");

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }


    @Override
    public String getConditionSql(String documentNo) {
        String sPaidField = ", ISNULL(ISNULL(f.amount, g.amount), 0) AS paid_amount";
        String sPaidSql = "LEFT JOIN (SELECT document_no, \n" + "SUM(paid_amount) AS amount FROM finance_payment_requests_details GROUP \n" + "BY document_no) f ON a.document_no = f.document_no LEFT JOIN (SELECT \n" + "m.document_id, m.document_amount - m.left_amount AS amount FROM \n" + "finance_document_entries m LEFT JOIN finance_payment_requests n ON \n" + "m.document_id = n.document_no WHERE n.status = 50) g ON a.document_no = \n" + "g.document_id ";

        if ("有效".equals(sysOptionsDao.getOptionForString(DFS_INTERFACE_IS_VALID))) {
            sPaidField = ", ISNULL(f.amount, 0) AS paid_amount";
            sPaidSql = "LEFT JOIN (SELECT document_no, settle_amount AS \n" + "amount FROM vw_finance_DFS_payment) f ON a.document_no = f.document_no";
        }

        String sql = "SELECT a.*, b.station_name, \n" + "c.meaning AS status_meaning, d.meaning AS request_type_meaning, e.type_name \n" + sPaidField + " FROM finance_payment_requests a LEFT JOIN \n" + "(SELECT station_id, station_name FROM sys_stations) b ON a.station_id = \n" + "b.station_id LEFT JOIN (SELECT code, meaning FROM sys_flags WHERE field_no = \n" + "'document_status') c ON a.status = c.code LEFT JOIN (SELECT code, meaning FROM \n" + "sys_flags WHERE field_no = 'amount_type') d ON a.request_type = d.code LEFT \n" + "JOIN (SELECT type_id, type_name FROM base_settlement_types) e ON a.detail_type \n" + "= e.type_id " + sPaidSql + " where a.document_no='" + documentNo + "'";

        logger.debug("业务请款审批业务的getConditionSql："+sql);
        return sql;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
        FinancePaymentRequests financePaymentRequests = getDocumentDetail(approveDocument.getDocumentNo());
        Set<FinancePaymentRequestsDetails> requestDetail = financePaymentRequests.getChargeDetail();
        // 若有请款明细，则判断明细所对应的单据分录是否有效。
        // 若有效，则当作废时减少其实收金额；当审批同意时增加其实收金额
        if (requestDetail.size() > 0) {
            for (FinancePaymentRequestsDetails detail : requestDetail) {
                // modify by shichunshan 数据校验改变
                List<Map<String, Object>> list = financePaymentRequestsDao.findFinancePaymentRequestsDetailsByEnterId(detail.getEntryId());

                FinanceDocumentEntries entry = financeDocumentEntriesDao.getDocumentEntriesByEntryId(detail.getEntryId());

                if (entry == null) {
                    // 找不到对应的业务单据信息
                    throw new ServiceException("审批失败：业务单据已变更，请核实");
                }
                double requestAmount = detail.getRequestAmount();
                double leftAmount = entry.getLeftAmount();
                // add by shichunshan 2015/11/27
                double leftReal = leftAmount;// 应请金额
                double paidReal = 0; // 已请金额
                if (null != list && list.size() > 0) {
                    // 解决强转会出现精度错误的情况 2016/01/04 caigx
                    leftReal = new BigDecimal(leftReal + "").subtract(new BigDecimal(list.get(0).get("amount").toString())).doubleValue();
                    paidReal = (double) list.get(0).get("request_amount");
                }

                /*
                 * if ((requestAmount > 0 && leftAmount < 0) || requestAmount <
                 * 0 && leftAmount > 0) { // 两个数正负号不一致 // return //
                 * MessageResult.APPROVE_ERROR_PAYMENT_REQUEST_AMOUNT_INVALID;
                 * throw new ServiceException("审批失败：请款金额大于应请金额"); } if
                 * (Math.abs(requestAmount) > Math.abs(leftAmount)) { //
                 * 请款金额大于应请金额 // return //
                 * MessageResult.APPROVE_ERROR_PAYMENT_REQUEST_AMOUNT_INVALID;
                 * throw new ServiceException("审批失败：请款金额大于应请金额"); }
                 */
                // modify by shichunshan 2015/11/27

//				if (requestAmount * leftReal <= -0.001 || requestAmount * (requestAmount - leftReal) > 0.001) {
//					// 请款金额大于应请金额
//					throw new ServiceException("审批失败：请款金额大于应请金额");
//				}

                //修改情况金额 大于应请金额的判断算法-20160530 caigx
                if (Math.abs(requestAmount - leftReal) > 0.001 && requestAmount - leftReal > 0) {
                    // 请款金额大于应请金额
                    logger.debug(String.format("业务请款 %s,单据 %s：请款金额 %s,可请款金额%s,已请金额%s", approveDocument.getDocumentNo(), detail.getDocumentNoEntry(), nf.format(requestAmount), nf.format(leftReal), nf.format(paidReal)));
                    throw new ServiceException(String.format("审批失败：单据(%s)请款金额%s元大于可请款金额%s元", detail.getDocumentNoEntry(), nf.format(requestAmount), nf.format(leftReal)));
                }

                if (ApproveStatus.LAST_APPROVE == approveStatus) {
                    // 最后一级审批时
                    // 修改单据分录
                    if (entry.getAfterNo() == null || entry.getAfterNo().equals("")) {// 添加后续单号
                        entry.setAfterNo(detail.getDocumentNo());
                    } else {
                        entry.setAfterNo(entry.getAfterNo() + "," + detail.getDocumentNo());
                    }

                }
            }
        }

        return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    /**
     * 获取业务请款单据和单据明细
     */
    @Override
    public FinancePaymentRequests getDocumentDetail(String documentNo) {
        FinancePaymentRequests document = dao.get(FinancePaymentRequests.class, documentNo);
        List<SysOptions> options = sysOptionsDao.getOptionsByOptionNo("CORPORATION_NAME", document.getStationId());
        if (options != null && options.size() > 0) {
            document.setPayer(options.get(0).getOptionValue());
        }
        if(StringUtils.isNotEmpty(document.getDetailType())){
            BaseSettlementTypes baseSettlementTypes = dao.getBaseSettlementTypesByTypeId(document.getDetailType());
            if(null != baseSettlementTypes) {
                document.setDetailTypeMeaning(baseSettlementTypes.getTypeName());
            }
        }
        return document;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        logger.info("onLastApproveLevel");
        FinancePaymentRequests document = getDocumentDetail(approveDocument.getDocumentNo());
        Set<FinancePaymentRequestsDetails> requestDetail = document.getChargeDetail();
        // add by shichunshan 2015/11/27
        // 若有请款明细，则不产生请款单据分录
        if (null != requestDetail && requestDetail.size() > 0) {
            return super.onLastApproveLevel(approveDocument, comment);
        }
        // 添加单据分录
        FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
        financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
        financeDocumentEntries.setStationId(approveDocument.getSubmitStationId());
        // 若款项类型为'预收款'、'销售订金'、'应付款'、收款产生的'其它应付'，
        // 则分录性质为'可以结算且可以冲抵'；
        // 若款项类型为'其它支出'且无业务明细，则分录性质为'可以结算且可收发票'；
        // 否则，分录性质为'可以结算'
        int entryProperty = 2;
        short amountType = document.getRequestType();
        if (amountType == 10 || amountType == 25 || amountType == 70 || amountType == 80 && requestDetail.size() > 0) {
            entryProperty = 18;
        }
        // add by shichunshan 2015/11/27
        else if (amountType == 90) {
            entryProperty = 6;
        }

        StringBuilder sDocumentTypeStr = new StringBuilder();
        SysFlags sysFlags = dao.getSysFlagsByFieldNoAndCode("amount_type", document.getRequestType());
        if (null != sysFlags) {
            sDocumentTypeStr.append(sysFlags.getMeaning());
        }
        // add by shichunshan 2015/11/27
        // 单据分录类型改为“请款类型-明细类型” ，请款类型从sys_flags表来，明细类型从base_settlement_types中来
        BaseSettlementTypes baseSettlementTypes = dao.getBaseSettlementTypesByTypeId(document.getDetailType());
        if (null != baseSettlementTypes) {
            sDocumentTypeStr.append("-");
            sDocumentTypeStr.append(baseSettlementTypes.getTypeName());
        }

        financeDocumentEntries.setEntryProperty(entryProperty);
        financeDocumentEntries.setEntryType(Constant.DocumentEntries.ENTRIES_TYPE_NEED_PAY);
        financeDocumentEntries.setDocumentType(sDocumentTypeStr.toString());
        financeDocumentEntries.setAmountType(amountType);

        financeDocumentEntries.setDocumentId(approveDocument.getDocumentNo());
        financeDocumentEntries.setDocumentNo(approveDocument.getDocumentNo());

        String isCounted = "";
        if (document.getIsCounted() == null || document.getIsCounted().equals("")) {
            isCounted = "0";
        } else {
            isCounted = document.getIsCounted() ? "1" : "0";
        }
        financeDocumentEntries.setSubDocumentNo(isCounted);

        financeDocumentEntries.setObjectId(document.getObjectId());
        financeDocumentEntries.setObjectNo(document.getObjectNo());
        financeDocumentEntries.setObjectName(document.getObjectName());
        financeDocumentEntries.setArapTime(document.getArapTime());
        financeDocumentEntries.setLeftAmount(document.getRequestAmount());
        financeDocumentEntries.setDocumentAmount(document.getRequestAmount());
        financeDocumentEntries.setDocumentTime(new Timestamp(new Date().getTime()));
        financeDocumentEntries.setOffsetAmount(0.00);
        financeDocumentEntries.setWriteOffAmount(0.00);
        financeDocumentEntries.setInvoiceAmount(0.00);
        financeDocumentEntries.setPaidAmount(0.00);

        financeDocumentEntries.setUserId(document.getUserId());
        financeDocumentEntries.setUserName(document.getUserName());
        financeDocumentEntries.setUserNo(document.getUserNo());
        financeDocumentEntries.setDepartmentId(document.getDepartmentId());
        financeDocumentEntries.setDepartmentName(document.getDepartmentName());
        financeDocumentEntries.setDepartmentNo(document.getDepartmentNo());
        // 2013-12-10修改，备注->摘要
        financeDocumentEntries.setSummary(document.getRemark());


        logger.info("onLastApproveLevel:插入单据分录");
        boolean success = financeDocumentEntriesDao.insertFinanceDocumentEntries(financeDocumentEntries);
        if (!success) {
            // return
            // ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL;
            return new ApproveResult(ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
        }
        return super.onLastApproveLevel(approveDocument, comment);
    }

    /**
     * @Override public boolean checkDataChanged(String modifyTime,
     *           ApproveDocuments approveDocument) { String documentNo =
     *           approveDocument.getDocumentNo(); FinancePaymentRequests
     *           financePaymentRequests = dao.get( FinancePaymentRequests.class,
     *           documentNo);
     *
     *           Timestamp lastModifyTime =
     *           financePaymentRequests.getModifyTime(); if (lastModifyTime ==
     *           null) { return false; } if (null != modifyTime &&
     *           !"".equals(modifyTime)) {
     *
     *           Timestamp timestamp = Timestamp.valueOf(modifyTime); return
     *           compareTime(timestamp, lastModifyTime); } return true; }
     **/

}
