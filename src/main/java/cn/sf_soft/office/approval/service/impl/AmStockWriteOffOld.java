package cn.sf_soft.office.approval.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.office.approval.dao.VehicleDfWriteOffApplyDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleDfWriteOffApply;
import cn.sf_soft.office.approval.model.VehicleDfWriteOffApplyDetail;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;

/**
 * 监控车销账(老)
 * 
 * @author caigx
 * @date 2016-05-04
 */
@Service("amStockWriteOffOld")
public class AmStockWriteOffOld extends BaseApproveProcess {

	@Autowired
	private VehicleDfWriteOffApplyDao vehicleDfWriteOffApplyDao;

	@Override
	protected String getApprovalPopedomId() {
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ApproveDocuments getSubmitRecordDetail(String documentNo) {
		return vehicleDfWriteOffApplyDao.getApplyByDocumentNo(documentNo);
	}
	
	@Override
	public VehicleDfWriteOffApply getDocumentDetail(String documentNo) {
		double totalWriteOffAmount = 0.00; // 款项合计
		int totalWriteOffCount = 0; // 总台数
		VehicleDfWriteOffApply apply = vehicleDfWriteOffApplyDao.getApplyByDocumentNo(documentNo);
		Set<VehicleDfWriteOffApplyDetail> details = apply.getChargeDetail();
		Iterator<VehicleDfWriteOffApplyDetail> it = details.iterator();
		StringBuffer logBuffer = new StringBuffer();
		while (it.hasNext()) {
			VehicleDfWriteOffApplyDetail detail = it.next();
			logBuffer.append("[底盘号:" + detail.getUnderpanNo() + ",金额:" + detail.getWriteOffAmount() + "]");
			totalWriteOffAmount += detail.getWriteOffAmount() == null ? 0.00 : detail.getWriteOffAmount();
		}
		totalWriteOffCount = details.size();
		apply.setTotalWriteOffAmount(totalWriteOffAmount);
		apply.setTotalWriteOffCount(totalWriteOffCount);
		logger.debug(String.format("监控车销账：%s,销账明细：%s", documentNo, logBuffer.toString()));
		return apply;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
		if (approveDocument == null || StringUtils.isEmpty(approveDocument.getDocumentNo())) {
			throw new ServiceException("审批失败:系统单号不能为空");
		}
		VehicleDfWriteOffApply writeOffApply = getDocumentDetail(approveDocument.getDocumentNo());
		if (writeOffApply == null || StringUtils.isEmpty(writeOffApply.getDepartmentId())) {
			throw new ServiceException("审批失败:销账部门不能为空");
		}
		if (StringUtils.isEmpty(writeOffApply.getOrderName())) {
			throw new ServiceException("审批失败:销账单名称不能为空");
		}

		Set<VehicleDfWriteOffApplyDetail> applyDetails = writeOffApply.getChargeDetail();
		if (applyDetails == null || applyDetails.size() == 0) {
			throw new ServiceException("审批失败:没有销账明细");
		}

		// 判断这些车辆中是否已存在于其他单据上
		Iterator<VehicleDfWriteOffApplyDetail> it = applyDetails.iterator();
		while (it.hasNext()) {
			VehicleDfWriteOffApplyDetail detail = it.next();
			List<Map<String, Object>> stocks = vehicleDfWriteOffApplyDao.getStocksInOtherApprove(
					approveDocument.getDocumentNo(), detail.getVehicleId());
			if (stocks != null && stocks.size() > 0) {
				logger.error(String.format("监控车销账:%s审批失败：车辆【%s】已在单据%s（状态码:%s）中，", approveDocument.getDocumentNo(),
						stocks.get(0).get("vehicle_vin"), stocks.get(0).get("document_no"), stocks.get(0).get("status")));
				throw new ServiceException(String.format("%s已存在于其他销账中，且已提交审批或已确认", stocks.get(0).get("vehicle_vin")));
			}

			if (ApproveStatus.LAST_APPROVE == approveStatus) {
				// 最后一步审批，判断销账单明细车辆数与实际库存数是否对应
				VehicleStocks vehicle = dao.get(VehicleStocks.class, detail.getVehicleId());
				if (vehicle == null) {
					logger.error(String.format("监控车销账:%s末级审批失败：车辆【%s】不在库", approveDocument.getDocumentNo(),
							detail.getUnderpanNo()));
					throw new ServiceException("审批失败:明细车辆数与实际库存数不对应");
				}
			}
		}
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
		VehicleDfWriteOffApply writeOffApply = getDocumentDetail(approveDocument.getDocumentNo());
		Set<VehicleDfWriteOffApplyDetail> applyDetails = writeOffApply.getChargeDetail();
		Iterator<VehicleDfWriteOffApplyDetail> it = applyDetails.iterator();
		while (it.hasNext()) {
			VehicleDfWriteOffApplyDetail detail = it.next();
			List<FinanceDocumentEntries> entries = financeDocumentEntriesDao.getDocumentEntriesByDocumentId(detail
					.getInStockDetailId());
			for (FinanceDocumentEntries entry : entries) {
				Integer entryProperty = entry.getEntryProperty();
				String entryDocNo = entry.getDocumentNo();
				if (!entryDocNo.contains(approveDocument.getDocumentNo())) {
					// 如果分录的documentNo不包含此documentNo，则添加进去以“，”分割
					entryDocNo += "," + approveDocument.getDocumentNo();
					entry.setDocumentNo(entryDocNo);
				}
				if ((entryProperty & 2) != 2) {
					// 如果原单据不可结算,修改为可结算
					entryProperty += 2;
					entry.setEntryProperty(entryProperty);
				}
				entry.setDocumentType("车辆-采购入库");
				// 保存修改
				if (!financeDocumentEntriesDao.updateFinanceDocumentEntries(entry)) {
					return new ApproveResult(ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
				}

			}
		}
		// Dvplus没有机制凭证
		return super.onLastApproveLevel(approveDocument, comment);
	}

}
