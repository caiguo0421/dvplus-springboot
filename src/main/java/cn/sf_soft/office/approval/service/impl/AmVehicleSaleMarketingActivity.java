package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import cn.sf_soft.common.util.Tools;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.Constant.DocumentStatus;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleSaleMarketingActivity;
import cn.sf_soft.office.approval.model.VehicleSaleMarketingActivitySupporters;

/**
 * 营销活动管理的审批实现类
 *
 * @author king
 * @created 2013-02-21
 */
@Service("vehicleSaleMarketingActivityManager")
public class AmVehicleSaleMarketingActivity extends BaseApproveProcess {
    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "10206520";

    final String DOCUMENT_TYPE = "车辆-营销活动";


    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @SuppressWarnings({"rawtypes"})
    @Override
    public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
        VehicleSaleMarketingActivity saleActivity = getDocumentDetail(approveDocument.getDocumentNo());
        short status = saleActivity.getStatus();
        if (status >= DocumentStatus.APPROVED && status <= DocumentStatus.HAS_RED) {
            // 营销活动已处理
            // return MessageResult.APPROVE_ERROR_SALE_ACTIVITY_HAS_HANDLED;
            throw new ServiceException("审批失败:营销活动已被处理");
        }
        return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    @Override
    public VehicleSaleMarketingActivity getDocumentDetail(String documentNo) {
        return dao.get(VehicleSaleMarketingActivity.class, documentNo);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        // 添加单据分录
        VehicleSaleMarketingActivity document = getDocumentDetail(approveDocument.getDocumentNo());
        // 如果有预支费用则产生业务请款的单据分录
        if (Tools.toDouble(document.getAdvanceMoney()) > 0.00D) {
            FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
            financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
            financeDocumentEntries.setStationId(document.getSubmitStationId());
            financeDocumentEntries.setEntryProperty(18);
            financeDocumentEntries.setEntryType(Constant.DocumentEntries.ENTRIES_TYPE_NEED_APPLAY);
            financeDocumentEntries.setDocumentType(DOCUMENT_TYPE);
            financeDocumentEntries.setAmountType(AmountType.ADVANCE_PAYMENT);

            financeDocumentEntries.setDocumentId(document.getDocumentNo());
            financeDocumentEntries.setDocumentNo(document.getDocumentNo());
            financeDocumentEntries.setSubDocumentNo(document.getDocumentNo());

            financeDocumentEntries.setObjectId(document.getCreditGiverId());
            financeDocumentEntries.setObjectName(document.getCreditGiver());
            financeDocumentEntries.setLeftAmount(document.getAdvanceMoney());
            financeDocumentEntries.setDocumentAmount(document.getAdvanceMoney());
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
            boolean success = financeDocumentEntriesDao.insertFinanceDocumentEntries(financeDocumentEntries);
            if (!success) {
                return new ApproveResult(ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
            }
        }
        Set<VehicleSaleMarketingActivitySupporters> activitySupporters = document.getActivitySupporters();
        // 如果有赞助商，则产生对赞助商应收的单据分录
        if (activitySupporters != null && !activitySupporters.isEmpty()) {
            for (VehicleSaleMarketingActivitySupporters supporter : activitySupporters) {
                FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
                financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
                financeDocumentEntries.setStationId(document.getSubmitStationId());
                financeDocumentEntries.setEntryProperty(3);
                financeDocumentEntries.setEntryType(Constant.DocumentEntries.ENTRIES_TYPE_NEED_INCOME);// 应收
                financeDocumentEntries.setDocumentType(DOCUMENT_TYPE);
                financeDocumentEntries.setAmountType(AmountType.OTHER_RECEIVABLE);// 其他应收

                financeDocumentEntries.setDocumentId(supporter.getSelfId());
                financeDocumentEntries.setDocumentNo(document.getDocumentNo());
                financeDocumentEntries.setSubDocumentNo(document.getDocumentNo());

                financeDocumentEntries.setObjectId(supporter.getSupporterId());
                financeDocumentEntries.setObjectNo(supporter.getSupporterNo());
                financeDocumentEntries.setObjectName(supporter.getSupporterName());
                financeDocumentEntries.setLeftAmount(supporter.getSupportMoney());
                financeDocumentEntries.setDocumentAmount(supporter.getSupportMoney());
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
                boolean success = financeDocumentEntriesDao.insertFinanceDocumentEntries(financeDocumentEntries);
                if (!success) {
                    return new ApproveResult(ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
                }
            }
        }

        return super.onLastApproveLevel(approveDocument, comment);
    }


    /**
     @Override public boolean checkDataChanged(String modifyTime,
     ApproveDocuments approveDocument) {
     String documentNo = approveDocument.getDocumentNo();
     VehicleSaleMarketingActivity vehicleSaleMarketingActivity = dao.get(
     VehicleSaleMarketingActivity.class, documentNo);

     Timestamp lastModifyTime = vehicleSaleMarketingActivity.getModifyTime();
     // 未修改过
     if (lastModifyTime == null) {
     return false;
     }
     if (null != modifyTime && !"".equals(modifyTime)) {
     Timestamp timestamp = Timestamp.valueOf(modifyTime);
     return compareTime(timestamp, lastModifyTime);
     }

     return true;
     }**/

}
