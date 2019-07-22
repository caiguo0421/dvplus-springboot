package cn.sf_soft.office.approval.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.OfficeVehicleMoveChargeReimbursements;
import cn.sf_soft.office.approval.model.OfficeVehicleMoveChargeReimbursementsDetails;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;

/**
 * 车辆移调费用报销
 *
 * @author king
 * @create 2013-6-21下午5:48:37
 */
@Service("vehicleMoveChargeReimbursementManager")
public class AmOfficeVehicleMoveChargeReimburse extends BaseApproveProcess {
    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "35552520";

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @SuppressWarnings({"rawtypes"})
    @Override
    public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
        return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {

        OfficeVehicleMoveChargeReimbursements document = getDocumentDetail(approveDocument.getDocumentNo());
        try {
            this.syncVehicleStock(document);
        } catch (Exception ex) {
            throw new ServiceException("同步车辆库存信息出错", ex);
        }
        FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
        financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
        financeDocumentEntries.setStationId(approveDocument.getSubmitStationId());

        financeDocumentEntries.setEntryProperty(Constant.DocumentEntries.ENTRIES_PROPERTY_EXPENSE_REIMBURSEMENT);
        financeDocumentEntries.setEntryType(Constant.DocumentEntries.ENTRIES_TYPE_NEED_PAY);
        financeDocumentEntries.setDocumentType("费用-车辆移调报销");
        // modify by shichunshan
        financeDocumentEntries.setAmountType(AmountType.NEED_PAY_OTHERS);
        // financeDocumentEntries.setAmountType(AmountType.OTHERS_PAYMENT);

        financeDocumentEntries.setDocumentId(approveDocument.getDocumentNo());
        financeDocumentEntries.setDocumentNo(approveDocument.getDocumentNo());
        financeDocumentEntries.setSubDocumentNo(approveDocument.getDocumentNo());
        financeDocumentEntries.setObjectId(document.getUserId());
        financeDocumentEntries.setObjectNo(document.getUserNo());
        financeDocumentEntries.setObjectName(document.getUserName());

        financeDocumentEntries.setLeftAmount(document.getReimburseAmount());
        financeDocumentEntries.setDocumentAmount(document.getReimburseAmount());
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
        // 2013-12-10 备注->摘要 by liujin
        financeDocumentEntries.setSummary(document.getRemark());
        boolean success = financeDocumentEntriesDao.insertFinanceDocumentEntries(financeDocumentEntries);
        if (!success) {
            // return
            // ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL;
            return new ApproveResult(ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
        }
        // add by shichunshan 增加生成机制凭证
        if (!createVoucher(approveDocument.getDocumentNo())) {
            throw new ServiceException("审批失败:生成凭证模板出错");
        }
        return super.onLastApproveLevel(approveDocument, comment);
    }

    /**
     * 审批通过后将费用同步到车辆库存中
     *
     * @param document
     */
    private void syncVehicleStock(OfficeVehicleMoveChargeReimbursements document) {
        // 审批完成后如果是移库出库则更新车辆的移库费用
        //处理移库出库(2)、调拨出库(1)、移库入库(2)、调拨入库(1)
        Set<OfficeVehicleMoveChargeReimbursementsDetails> chargeDetails = document.getChargeDetail();
        Map<String, BigDecimal> moveCharge = new HashMap<String, BigDecimal>();
        for (OfficeVehicleMoveChargeReimbursementsDetails chargeDetail : chargeDetails) {
            List list = dao.executeSQLQuery("SELECT ISNULL (c.in_stock_type, d.out_stock_type) AS out_stock_type\n" + "\t   FROM dbo.office_vehicle_move_charge_reimbursements_details a\n" + "\t   LEFT JOIN dbo.vehicle_in_stocks c ON a.out_stock_no = c.in_stock_no\n" + "\t   LEFT JOIN dbo.vehicle_out_stocks d ON a.out_stock_no = d.out_stock_no where a.out_stock_no='" + chargeDetail.getOutStockNo() + "'");
            if (null == list || null == list.get(0)) continue;
            Byte outStockType = (Byte) list.get(0);
            if (outStockType == 1 || outStockType == 2) {
                // 将费用明细中的费用按车辆ID累加
                String vehicleId = chargeDetail.getVehicleId();
                BigDecimal chargeAmount = chargeDetail.getChargeAmount() == null ? BigDecimal.ZERO : new BigDecimal(chargeDetail.getChargeAmount());
                if (!moveCharge.containsKey(vehicleId)) {
                    moveCharge.put(vehicleId, chargeAmount);
                } else {
                    moveCharge.put(vehicleId, moveCharge.get(vehicleId).add(chargeAmount));
                }
            }
        }

        if (!moveCharge.isEmpty()) {
            for (String vehicleId : moveCharge.keySet()) {
                VehicleStocks vs = dao.get(VehicleStocks.class, vehicleId);
                if (null != vs) {
                    // 将车辆的移库费用累加
                    BigDecimal msCharge = vs.getMoveStockCharge() == null ? BigDecimal.ZERO : new BigDecimal(vs.getMoveStockCharge());
                    vs.setMoveStockCharge(msCharge.add(moveCharge.get(vehicleId)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    logger.debug(String.format("车辆移调费用报销:%s,车辆：%s,移库费用：%s+%s=%s", document.getDocumentNo(), vs.getVehicleVin(), msCharge, moveCharge.get(vehicleId), vs.getMoveStockCharge()));
                    dao.update(vs);
                }
            }
        }
    }

    /**
     * @param documentNo
     * @param @return
     * @throws
     * @Description: 生成机制凭证
     */
    private boolean createVoucher(String documentNo) {

        // return voucherAuto.generateVoucher(sql, "35551000", false,
        // HttpSessionStore.getSessionUser());
        String sql = dao.getQueryStringByName("vehicleMoveChargeReimbursementDS", null, null);
        return voucherAuto.generateVoucherByProc(sql, "35552500", false, HttpSessionStore.getSessionUser().getUserId(), documentNo);
    }

    @Override
    public OfficeVehicleMoveChargeReimbursements getDocumentDetail(String documentNo) {
        return dao.get(OfficeVehicleMoveChargeReimbursements.class, documentNo);
    }

    /**
     @Override public boolean checkDataChanged(String modifyTime,
     ApproveDocuments approveDocument) {
     String documentNo = approveDocument.getDocumentNo();
     OfficeVehicleMoveChargeReimbursements officeVehicleMoveChargeReimbursements = dao
     .get(OfficeVehicleMoveChargeReimbursements.class, documentNo);

     Timestamp lastModifyTime = officeVehicleMoveChargeReimbursements
     .getModifyTime();
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
