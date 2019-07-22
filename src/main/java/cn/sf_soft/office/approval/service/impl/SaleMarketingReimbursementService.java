package cn.sf_soft.office.approval.service.impl;


import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.*;

import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * 营销活动报销的审批
 *
 * @auther caigx
 */
@Service("saleMarketingReimbursementService")
public class SaleMarketingReimbursementService extends BaseApproveProcess {

    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "35555520";

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleMarketingReimbursementService.class);

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @Override
    public ApproveDocuments getDocumentDetail(String documentNo) {
        return dao.get(OfficeSaleMarketingReimbursements.class, documentNo);
    }

    @Override
    public Constant.ApproveResultCode checkData(ApproveDocuments approveDocument, Constant.ApproveStatus approveStatus) {
        OfficeSaleMarketingReimbursements reimbursements = dao.get(OfficeSaleMarketingReimbursements.class, approveDocument.getDocumentNo());
        if (reimbursements == null) {
            throw new ServiceException(String.format("审批失败：%s未找到营销活动报销单据", approveDocument.getDocumentNo()));
        }
        Iterator<OfficeSaleMarketingReimbursementsDetails> it = reimbursements.getChargeDetail().iterator();
        while (it.hasNext()) {
            OfficeSaleMarketingReimbursementsDetails details = it.next();
            VehicleSaleMarketingCharge marketingCharge = dao.get(VehicleSaleMarketingCharge.class, details.getVsmcId());
            if (marketingCharge == null) {
                throw new ServiceException(String.format("【%s】找不到营销活动中的费用信息", details.getChargeName()));
            }
            if (Tools.toDouble(marketingCharge.getChargePrice()) == 0.00D) {
                throw new ServiceException(String.format("营销活动中的费用【%s】的预估成本为0", details.getChargeName()));
            }

            VehicleSaleMarketingActivity activity = dao.get(VehicleSaleMarketingActivity.class, marketingCharge.getDocumentNo());
            if (Tools.toShort(activity.getStatus()) != (short) 50) {
                throw new ServiceException(String.format("对应的营销活动【%s】的状态不是已同意", marketingCharge.getDocumentNo()));
            }

            if (Tools.toDouble(marketingCharge.getChargeCost()) >= 0.005D) {
                throw new ServiceException(String.format("营销活动中的费用【%s】已报销", details.getChargeName()));
            }

            //最后一步审批
            if (approveStatus == Constant.ApproveStatus.LAST_APPROVE) {
                //回填charge_cost
                marketingCharge.setChargeCost(Tools.toDouble(details.getChargeAmount()));
            }
        }

        return Constant.ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }


    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        OfficeSaleMarketingReimbursements reimbursements = dao.get(OfficeSaleMarketingReimbursements.class, approveDocument.getDocumentNo());

        String sStationID = reimbursements.getSubmitStationId();
        String sUserID = reimbursements.getUserId();
        String sUserNo = reimbursements.getUserNo();
        String sUserName = reimbursements.getUserName();
        String sDepartmentID = reimbursements.getDepartmentId();
        String sDepartmentNo = reimbursements.getDepartmentNo();
        String sDepartmentName = reimbursements.getDepartmentName();
        String sSummary = reimbursements.getRemark();
        double mReimburseAmount = Tools.toDouble(reimbursements.getReimburseAmount());

        financeDocumentEntriesDao.insertEntry(sStationID,
                (short) 31, (short) 60, "费用-营销活动报销", reimbursements.getDocumentNo(), sUserID, sUserNo, sUserName,
                (short) 80, mReimburseAmount, null, sSummary, null, null, 1, 0.00D, null,
                sUserID, sUserNo, sUserName, sDepartmentID, sDepartmentNo, sDepartmentName);
        return super.onLastApproveLevel(approveDocument, comment);
    }
}
