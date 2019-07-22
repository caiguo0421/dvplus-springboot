package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.dao.VehicleWriteOffDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleDfWriteOffApply;
import cn.sf_soft.office.approval.model.VehicleDfWriteOffApplyDetail;
import cn.sf_soft.office.approval.model.VehicleInStockDetail;
import cn.sf_soft.office.approval.model.VehicleInStocks;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleWriteOff;
import cn.sf_soft.office.approval.model.VehicleWriteOffDetails;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.customer.model.SysOptions;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;

/**
 * 新车辆销账申请，有原来监控车销账修改
 * 
 * @author caigx
 * @date 2016-08-03
 */
@Service("amStockWriteOff")
public class AmStockWriteOff extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "10701720";
	
	@Autowired
	private VehicleWriteOffDao vehicleWriteOffDao;

	@Autowired
	private SysOptionsDao sysOptionsDao;

	@Autowired
	private SysCodeRulesService sysCodeService; // 获得系统编码

	private static final String DF_VEHICLE_OBJECT_NO = "DF_VEHICLE_OBJECT_NO";

	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ApproveDocuments getSubmitRecordDetail(String documentNo) {
		return vehicleWriteOffDao.getApplyByDocumentNo(documentNo);
	}

	@Override
	public VehicleWriteOff getDocumentDetail(String documentNo) {
		double totalWriteOffAmount = 0.00; // 款项合计
		int totalWriteOffCount = 0; // 总台数
		VehicleWriteOff apply = vehicleWriteOffDao.getApplyByDocumentNo(documentNo);
		Set<VehicleWriteOffDetails> details = apply.getChargeDetail();
		Iterator<VehicleWriteOffDetails> it = details.iterator();
		StringBuffer logBuffer = new StringBuffer();
		while (it.hasNext()) {
			VehicleWriteOffDetails detail = it.next();
			logBuffer.append("[底盘号:" + detail.getUnderpanNo() + ",金额:" + detail.getWriteOffAmount() + "]");
			totalWriteOffAmount += detail.getWriteOffAmount() == null ? 0.00 : detail.getWriteOffAmount();
		}
		totalWriteOffCount = details.size();
		apply.setTotalWriteOffAmount(totalWriteOffAmount);
		apply.setTotalWriteOffCount(totalWriteOffCount);
		logger.debug(String.format("车辆销账申请：%s,销账明细：%s", documentNo, logBuffer.toString()));
		return apply;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
		if (approveDocument == null || StringUtils.isEmpty(approveDocument.getDocumentNo())) {
			throw new ServiceException("审批失败:系统单号不能为空");
		}
		VehicleWriteOff writeOffApply = getDocumentDetail(approveDocument.getDocumentNo());
		if (writeOffApply == null || StringUtils.isEmpty(writeOffApply.getDepartmentId())) {
			throw new ServiceException("审批失败:销账部门不能为空");
		}
		if (StringUtils.isEmpty(writeOffApply.getOrderName())) {
			throw new ServiceException("审批失败:销账单名称不能为空");
		}

		Set<VehicleWriteOffDetails> applyDetails = writeOffApply.getChargeDetail();
		if (applyDetails == null || applyDetails.size() == 0) {
			throw new ServiceException("审批失败:没有销账明细");
		}

		Map<String, String> dicDetail = new HashMap<String, String>();// 先入库后销账的明细ID
		List<String> lstVehicleId = new ArrayList<String>();// 先入库后销账的车辆ID
		List<String> lstUnderpan = new ArrayList<String>();// 未入库先销账的底盘
		List<String> lstUnderpanAll = new ArrayList<String>();// 当前单据的所有底盘号

		Iterator<VehicleWriteOffDetails> it = applyDetails.iterator();
		while (it.hasNext()) {
			VehicleWriteOffDetails detail = it.next();
			if (StringUtils.isNotEmpty(detail.getVehicleId())) {
				dicDetail.put(detail.getInStockDetailId(), detail.getUnderpanNo());
				lstVehicleId.add(detail.getVehicleId());
			} else {
				lstUnderpan.add(detail.getUnderpanNo());
			}
			lstUnderpanAll.add(detail.getUnderpanNo());
		}

		for (String underpanStr : lstUnderpan) {
			List<Map<String, Object>> vehicleInStocks = vehicleWriteOffDao.getVehicleInStock(underpanStr);
			if (vehicleInStocks != null && vehicleInStocks.size() > 0) {
				VehicleWriteOffDetails detail = getVehicleWriteOffDetailsByUnderpan(applyDetails, underpanStr);
				if (detail != null) {
					detail.setInStockDetailId(vehicleInStocks.get(0).get("in_stock_detail_id").toString());
					detail.setVehicleId(vehicleInStocks.get(0).get("vehicle_id").toString());
					detail.setVehicleVin(vehicleInStocks.get(0).get("vehicle_vin").toString());
					detail.setVehicleVno(vehicleInStocks.get(0).get("vehicle_vno").toString());
					detail.setVehicleOutFactoryTime((Timestamp) vehicleInStocks.get(0).get("vehicle_out_factory_time"));
					detail.setDetailStatus("DMS制单");

					vehicleWriteOffDao.updateVehicleWriteOffDetails(detail);// 更新

					dicDetail.put(vehicleInStocks.get(0).get("in_stock_detail_id").toString(), vehicleInStocks.get(0)
							.get("underpan_no").toString());
					lstVehicleId.add(vehicleInStocks.get(0).get("vehicle_id").toString());
					lstUnderpan.remove(vehicleInStocks.get(0).get("underpan_no").toString());
				}
			}
		}

		// 检查底盘号是否在入库单中
		List<Map<String, Object>> underpans = vehicleWriteOffDao.getInStockUnderpans(dicDetail.keySet());
		Iterator<VehicleWriteOffDetails> it2 = applyDetails.iterator();
		while (it2.hasNext()) {
			VehicleWriteOffDetails detail = it2.next();
			// 先入库后做销账的需要此验证，未入库先销账的，销账单不存在vehicle_id
			if (StringUtils.isNotEmpty(detail.getVehicleId())) {
				if (!underpanInList(detail.getUnderpanNo(), underpans)) {
					throw new ServiceException(String.format("底盘号为 %s的车辆不在采购入库单中或已被冲红", detail.getUnderpanNo()));
				}
			}
		}

		// 检查是否在其他入库单中
		List<Map<String, Object>> underPansInOtherDocument = vehicleWriteOffDao.getUnderPansInOtherDocument(
				approveDocument.getDocumentNo(), lstUnderpanAll);
		if (underPansInOtherDocument != null && underPansInOtherDocument.size() > 0) {
			throw new ServiceException(String.format("底盘为%s的车辆已存在于其他销账中",
					underPansInOtherDocument.get(0).get("underpan_no")));
		}

		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	/**
	 * 底盘号在结果集中
	 * 
	 * @param underpan
	 * @param underpans
	 * @return
	 */
	private boolean underpanInList(String underpan, List<Map<String, Object>> underpans) {
		for (Map<String, Object> undepanMap : underpans) {
			if (underpan.equals(undepanMap.get("underpan_no").toString())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 根据底盘号获得销账单明细
	 * 
	 * @param applyDetails
	 * @param underpanStr
	 * @return
	 */
	private VehicleWriteOffDetails getVehicleWriteOffDetailsByUnderpan(Set<VehicleWriteOffDetails> applyDetails,
			String underpanStr) {
		Iterator<VehicleWriteOffDetails> it = applyDetails.iterator();
		while (it.hasNext()) {
			VehicleWriteOffDetails detail = it.next();
			if (underpanStr.equals(detail.getUnderpanNo())) {
				return detail;
			}
		}
		return null;
	}

	/**
	 * 根据vehicleId 获得销账单明细
	 * 
	 * @param applyDetails
	 * @param vehicleId
	 * @return
	 */
	private VehicleWriteOffDetails getVehicleWriteOffDetailsByVehicleId(Set<VehicleWriteOffDetails> applyDetails,
			String vehicleId) {
		Iterator<VehicleWriteOffDetails> it = applyDetails.iterator();
		while (it.hasNext()) {
			VehicleWriteOffDetails detail = it.next();
			if (vehicleId.equals(detail.getVehicleId())) {
				return detail;
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
		VehicleWriteOff writeOffApply = getDocumentDetail(approveDocument.getDocumentNo());
		Set<VehicleWriteOffDetails> applyDetails = writeOffApply.getChargeDetail();

		Map<String, String> dicDetail = new HashMap<String, String>();// 先入库后销账的明细ID
		List<String> lstVehicleId = new ArrayList<String>();// 先入库后销账的车辆ID
		List<String> lstUnderpan = new ArrayList<String>();// 未入库先销账的底盘
		List<String> lstUnderpanAll = new ArrayList<String>();// 当前单据的所有底盘号

		Iterator<VehicleWriteOffDetails> it = applyDetails.iterator();
		while (it.hasNext()) {
			VehicleWriteOffDetails detail = it.next();
			if (StringUtils.isNotEmpty(detail.getVehicleId())) {
				dicDetail.put(detail.getInStockDetailId(), detail.getUnderpanNo());
				lstVehicleId.add(detail.getVehicleId());
			} else {
				lstUnderpan.add(detail.getUnderpanNo());
			}
			lstUnderpanAll.add(detail.getUnderpanNo());
		}

		for (String underpanStr : lstUnderpan) {
			List<Map<String, Object>> vehicleInStocks = vehicleWriteOffDao.getVehicleInStock(underpanStr);
			if (vehicleInStocks != null && vehicleInStocks.size() > 0) {
				VehicleWriteOffDetails detail = getVehicleWriteOffDetailsByUnderpan(applyDetails, underpanStr);
				if (detail != null) {
					detail.setInStockDetailId(vehicleInStocks.get(0).get("in_stock_detail_id").toString());
					detail.setVehicleId(vehicleInStocks.get(0).get("vehicle_id").toString());
					detail.setVehicleVin(vehicleInStocks.get(0).get("vehicle_vin").toString());
					detail.setVehicleVno(vehicleInStocks.get(0).get("vehicle_vno").toString());
					detail.setVehicleOutFactoryTime((Timestamp) vehicleInStocks.get(0).get("vehicle_out_factory_time"));
					detail.setDetailStatus("DMS制单");

					vehicleWriteOffDao.updateVehicleWriteOffDetails(detail);// 更新

					dicDetail.put(vehicleInStocks.get(0).get("in_stock_detail_id").toString(), vehicleInStocks.get(0)
							.get("underpan_no").toString());
					lstVehicleId.add(vehicleInStocks.get(0).get("vehicle_id").toString());
					lstUnderpan.remove(vehicleInStocks.get(0).get("underpan_no").toString());
				}
			}
		}

		
		SysUsers user = HttpSessionStore.getSessionUser();
		
		double dWriteOffAmount = 0;// 销账金额
		double dInStockAmount = 0;// 入库单金额
		double dDiffAmount = 0;// 销账金额与入库金额的差额
		double mPrice;
		Timestamp dtFuturePayDate = new Timestamp(System.currentTimeMillis());// 入库单预计付款日期

		// 循环处理先入库后销账的车辆
		for (String sVehicleId : lstVehicleId) {
			dWriteOffAmount = 0;
			dInStockAmount = 0;
			dDiffAmount = 0;
			String sInStockDetailId = "";// 入库单明细主键
			String sInStockNo = "";// 入库单号
			String sVehicleSaleDocuments = "";// sap订单号(sap_order_no)
			String sVehicleVin = "";

			VehicleWriteOffDetails detail = getVehicleWriteOffDetailsByVehicleId(applyDetails, sVehicleId);
			if (detail != null) {
				dWriteOffAmount = Tools.toDouble(detail.getWriteOffAmount());
				sInStockDetailId = detail.getInStockDetailId();
			}

			List<VehicleInStockDetail> drs = vehicleWriteOffDao.getStockDetailByVehicleId(dicDetail.keySet(),
					sVehicleId);
			if (drs != null && drs.size() > 0) {
				VehicleInStockDetail vehicleInStockDetail = drs.get(0);
				dInStockAmount = Tools.toDouble(vehicleInStockDetail.getVehicleCost());
				sInStockNo = vehicleInStockDetail.getInStockNo();
				sVehicleSaleDocuments = vehicleInStockDetail.getVehicleSaleDocuments();
				sVehicleVin = vehicleInStockDetail.getVehicleVin();
				if (vehicleInStockDetail.getFuturePayDate() != null) {
					dtFuturePayDate = vehicleInStockDetail.getFuturePayDate();
				}
				dDiffAmount = dWriteOffAmount - dInStockAmount;

				// 如果有差额,更新原采购入库单
				if (dDiffAmount != 0) {
					mPrice = dWriteOffAmount;

					vehicleInStockDetail.setVehicleCost(mPrice);
					vehicleInStockDetail.setVehiclePrice(mPrice);// 合同价
					vehicleInStockDetail.setNoTaxPrice(Math.rint(mPrice / 1.17D * 100) / 100);
					vehicleInStockDetail.setVehicleTax(vehicleInStockDetail.getVehicleCost()
							- vehicleInStockDetail.getNoTaxPrice());
					List<VehicleInStocks> vehicleInStocks = vehicleWriteOffDao.getStockByInStockNo(dicDetail.keySet(),
							sInStockNo);
					if (vehicleInStocks != null && vehicleInStocks.size() > 0) {
						VehicleInStocks inStock = vehicleInStocks.get(0);
						inStock.setInStockMoney(Tools.toDouble(inStock.getInStockMoney()) + dDiffAmount);
					}
				}

				// 更新销售合同
				List<VehicleSaleContractDetail> contractDetails = vehicleWriteOffDao.getContractDetailByVehicleId(
						lstVehicleId, sVehicleId);
				if (contractDetails != null && contractDetails.size() > 0) {
					VehicleSaleContractDetail contractDetail = contractDetails.get(0);
					if (Tools.toDouble(contractDetail.getVehicleCost()) != dWriteOffAmount) {
						contractDetail.setVehicleCost(dWriteOffAmount);
						contractDetail.setVehicleCostRef(dWriteOffAmount);
					}
				}

				// 更新车辆库存
				List<VehicleStocks> vehicleStocks = vehicleWriteOffDao.getVehicleStocksByVehicleId(lstVehicleId,
						sVehicleId);
				if (vehicleStocks != null && vehicleStocks.size() > 0) {
					VehicleStocks vehicleStock = vehicleStocks.get(0);
					if (Tools.toDouble(vehicleStock.getVehicleCost()) != dWriteOffAmount) {
						vehicleStock.setVehicleCost(dWriteOffAmount);
					}
				}

				// 更新分录
				List<FinanceDocumentEntries> entries = financeDocumentEntriesDao
						.getDocumentEntriesByDocumentId(sInStockDetailId);
				if (entries != null && entries.size() > 0) {
					FinanceDocumentEntries entry = entries.get(0);
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
					entry.setDocumentAmount(dWriteOffAmount);
					entry.setLeftAmount(dWriteOffAmount);
					// 保存修改
					if (!financeDocumentEntriesDao.updateFinanceDocumentEntries(entry)) {
						return new ApproveResult(ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
					}
				} else {
					// 如果没有单据分录则生成，不般不会进入此分支，只有历史数据即以前允许入库价为0入库，入库价为0入库的以前不产生单据分录，后来控制了入库价不能为0.
					List<VehicleInStocks> inStocks = vehicleWriteOffDao.getStockByInStockNo(dicDetail.keySet(),
							sInStockNo);

					VehicleInStocks inStock = inStocks.get(0);

					FinanceDocumentEntries entry1 = new FinanceDocumentEntries();
					entry1.setEntryId(UUID.randomUUID().toString());
					entry1.setStationId(writeOffApply.getStationId());
					entry1.setEntryProperty(7);
					entry1.setEntryType((short) 60);
					entry1.setDocumentType("车辆-采购入库");

					entry1.setDocumentId(sInStockDetailId);
					entry1.setDocumentNo(sVehicleSaleDocuments + "," + sVehicleVin);
					entry1.setSubDocumentNo(sInStockNo);

					entry1.setObjectId(inStock.getSupplierId());
					entry1.setObjectNo(inStock.getSupplierNo());
					entry1.setObjectName(inStock.getSupplierName());

					entry1.setUserId(user.getUserId());
					entry1.setUserNo(user.getUserNo());
					entry1.setUserName(user.getUserName());
					entry1.setDepartmentId(user.getDepartment());
					entry1.setDepartmentNo(user.getDepartmentNo());
					entry1.setDepartmentName(user.getDepartmentName());

					entry1.setAmountType((short) 70);
					entry1.setLeftAmount(dWriteOffAmount);
					entry1.setDocumentAmount(dWriteOffAmount);
					entry1.setDocumentTime(dtFuturePayDate);
					entry1.setArapTime(dtFuturePayDate);

					entry1.setOffsetAmount(0.00);
					entry1.setPaidAmount(0.00);
					entry1.setWriteOffAmount(0.00);
					entry1.setInvoiceAmount(0.00);

					// 保存修改
					if (!financeDocumentEntriesDao.insertFinanceDocumentEntries(entry1)) {
						return new ApproveResult(ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
					}
				}
			}
		}
			// 处理未入库先销账的车辆
			String m_sDFObjectNo = "";
			String m_sDFObjectId = "";
			String m_sDFObjectName = "";
			
			List<SysOptions> options = sysOptionsDao.getOptionsByOptionNo(DF_VEHICLE_OBJECT_NO,user.getDefaulStationId());
			if (options != null && options.size() > 0) {
				m_sDFObjectNo = options.get(0).getOptionValue();
			}
			List<BaseRelatedObjects> relatedObjects = vehicleWriteOffDao.getRelatedObjectsByObjectNo(m_sDFObjectNo);
			if (relatedObjects != null && relatedObjects.size() == 1) {
				BaseRelatedObjects relatedObject = relatedObjects.get(0);
				m_sDFObjectId = relatedObject.getObjectId();
				m_sDFObjectName = relatedObject.getObjectName();
			}

			for (String sUpderpan : lstUnderpan) {
				VehicleWriteOffDetails detail1 = getVehicleWriteOffDetailsByUnderpan(applyDetails, sUpderpan);
				if (detail1 != null) {
					// 生成的单据分录不需要请款
					dWriteOffAmount = Tools.toDouble(detail1.getWriteOffAmount());
					String vehicle_sale_documents = "";
					List<Map<String, Object>>resultList=vehicleWriteOffDao.getVehicleSaleDocumentsByDetailId(detail1.getApplyDetailId());
					if(resultList!=null && resultList.size()>0){
						vehicle_sale_documents = resultList.get(0).get("vehicle_sale_documents").toString();
					}
					
					FinanceDocumentEntries entry2 = new FinanceDocumentEntries();
					entry2.setEntryId(UUID.randomUUID().toString());
					entry2.setStationId(writeOffApply.getStationId());
					entry2.setEntryProperty(7);
					entry2.setEntryType((short) 60);
					entry2.setDocumentType("车辆-采购入库");

					entry2.setDocumentId(detail1.getInStockDetailId());
					entry2.setDocumentNo(vehicle_sale_documents + "," + sUpderpan);
					entry2.setSubDocumentNo(detail1.getDocumentNo());

					entry2.setObjectId(m_sDFObjectId);
					entry2.setObjectNo(m_sDFObjectNo);
					entry2.setObjectName(m_sDFObjectName);

					entry2.setUserId(user.getUserId());
					entry2.setUserNo(user.getUserNo());
					entry2.setUserName(user.getUserName());
					entry2.setDepartmentId(user.getDepartment());
					entry2.setDepartmentNo(user.getDepartmentNo());
					entry2.setDepartmentName(user.getDepartmentName());

					entry2.setAmountType((short) 70);
					entry2.setLeftAmount(dWriteOffAmount);
					entry2.setDocumentAmount(dWriteOffAmount);
					entry2.setDocumentTime(new Timestamp(System.currentTimeMillis()));
					entry2.setArapTime(new Timestamp(System.currentTimeMillis()));

					entry2.setOffsetAmount(0.00);
					entry2.setPaidAmount(0.00);
					entry2.setWriteOffAmount(0.00);
					entry2.setInvoiceAmount(0.00);

					// 保存修改
					if (!financeDocumentEntriesDao.insertFinanceDocumentEntries(entry2)) {
						return new ApproveResult(ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
					}
				}
			}
		

		// 自动按SAP单号拆分多个订单
		splitApply(approveDocument);
		// Dvplus没有机制凭证
		return super.onLastApproveLevel(approveDocument, comment);
	}

	public ApproveResultCode checkDataOld(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
		if (approveDocument == null || StringUtils.isEmpty(approveDocument.getDocumentNo())) {
			throw new ServiceException("审批失败:系统单号不能为空");
		}
		VehicleWriteOff writeOffApply = getDocumentDetail(approveDocument.getDocumentNo());
		if (writeOffApply == null || StringUtils.isEmpty(writeOffApply.getDepartmentId())) {
			throw new ServiceException("审批失败:销账部门不能为空");
		}
		if (StringUtils.isEmpty(writeOffApply.getOrderName())) {
			throw new ServiceException("审批失败:销账单名称不能为空");
		}

		Set<VehicleWriteOffDetails> applyDetails = writeOffApply.getChargeDetail();
		if (applyDetails == null || applyDetails.size() == 0) {
			throw new ServiceException("审批失败:没有销账明细");
		}

		// 判断这些车辆中是否已存在于其他单据上
		Iterator<VehicleWriteOffDetails> it = applyDetails.iterator();
		while (it.hasNext()) {
			VehicleWriteOffDetails detail = it.next();
			List<Map<String, Object>> stocks = vehicleWriteOffDao.getStocksInOtherApprove(
					approveDocument.getDocumentNo(), detail.getVehicleId());
			if (stocks != null && stocks.size() > 0) {
				logger.error(String.format("车辆销账申请:%s审批失败：车辆【%s】已在单据%s（状态码:%s）中，", approveDocument.getDocumentNo(),
						stocks.get(0).get("vehicle_vin"), stocks.get(0).get("document_no"), stocks.get(0).get("status")));
				throw new ServiceException(String.format("%s已存在于其他销账中，且已提交审批或已确认", stocks.get(0).get("vehicle_vin")));
			}

			if (ApproveStatus.LAST_APPROVE == approveStatus) {
				// 最后一步审批，判断销账单明细车辆数与实际库存数是否对应
				VehicleStocks vehicle = dao.get(VehicleStocks.class, detail.getVehicleId());
				if (vehicle == null) {
					logger.error(String.format("车辆销账申请:%s末级审批失败：车辆【%s】不在库", approveDocument.getDocumentNo(),
							detail.getUnderpanNo()));
					throw new ServiceException("审批失败:销账单明细车辆数与实际库存数不对应");
				}
			}
		}
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	public ApproveResult onLastApproveLevelOld(ApproveDocuments approveDocument, String comment) {
		VehicleWriteOff writeOffApply = getDocumentDetail(approveDocument.getDocumentNo());
		Set<VehicleWriteOffDetails> applyDetails = writeOffApply.getChargeDetail();
		Iterator<VehicleWriteOffDetails> it = applyDetails.iterator();
		while (it.hasNext()) {
			VehicleWriteOffDetails detail = it.next();
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
		// 自动按SAP单号拆分多个订单
		splitApply(approveDocument);
		// Dvplus没有机制凭证
		return super.onLastApproveLevel(approveDocument, comment);
	}

	/**
	 * 自动按SAP单号拆分多个订单
	 */
	@SuppressWarnings("rawtypes")
	private void splitApply(ApproveDocuments approveDocument) {
		VehicleWriteOff writeOff = getDocumentDetail(approveDocument.getDocumentNo());
		List<Map<String, Object>> resultList = vehicleWriteOffDao.getDetailCountPerSapNo(approveDocument.getDocumentNo());
		if (resultList == null || resultList.size() == 0) {
			return;
		}
		SysUsers user = HttpSessionStore.getSessionUser();
		for (Map<String, Object> resultMap : resultList) {
			// 获得VWO_NO的编码
			String code = sysCodeService.createSysCodeRules("VWO_NO", user.getDefaulStationId());

			String vehicleSaleDocuments = resultMap.get("vehicle_sale_documents").toString();
			// 生成销账订单主单
			VehicleDfWriteOffApply newOrder = new VehicleDfWriteOffApply();
			String sAgencyCode = writeOff.getDealerCode();
			newOrder.setWriteOffNo(sAgencyCode + "_" + code);
			newOrder.setApplyQuantity((Integer) resultMap.get("detail_count"));// 数量取
			newOrder.setDealerCode(sAgencyCode);
			newOrder.setApplyDealerCode(sAgencyCode);
			newOrder.setApplyType(writeOff.getApplyType());
			newOrder.setRemark(writeOff.getRemark());
			newOrder.setExtendOrderNo(vehicleSaleDocuments);
			newOrder.setFinance("现款");
			newOrder.setInvoiceProcess("新建");
			newOrder.setOrderName(writeOff.getOrderName());
			newOrder.setCreator(writeOff.getCreator());
			newOrder.setCreateTime(writeOff.getCreateTime());
			newOrder.setUploadStatus((short) 10);
			newOrder.setDocumentNo(code);
			newOrder.setStationId(writeOff.getStationId());
			newOrder.setStatus((short) 50);
			newOrder.setUserId(writeOff.getUserId());
			newOrder.setUserNo(writeOff.getUserNo());
			newOrder.setUserName(writeOff.getUserName());
			newOrder.setDepartmentId(writeOff.getDepartmentId());
			newOrder.setDepartmentNo(writeOff.getDepartmentNo());
			newOrder.setDepartmentName(writeOff.getDepartmentName());
			newOrder.setSubmitStationId(writeOff.getSubmitStationId());
			newOrder.setSubmitStationName(writeOff.getSubmitStationName());
			newOrder.setSubmitTime(writeOff.getSubmitTime());
			newOrder.setApproverId(user.getUserId());
			newOrder.setApproverNo(user.getUserNo());
			newOrder.setApproverName(user.getUserName());

			Timestamp approveTime = new Timestamp(System.currentTimeMillis());
			newOrder.setApproveTime(approveTime);
			newOrder.setModifyTime(approveTime);
			newOrder.setModifier(writeOff.getModifier());
			newOrder.setVwaNo(writeOff.getDocumentNo());

			dao.save(newOrder);

			List<Map<String, Object>> detailResultList = vehicleWriteOffDao.getDetailByVehicleSaleDocuments(
					approveDocument.getDocumentNo(), vehicleSaleDocuments);
			if (detailResultList == null || detailResultList.size() == 0) {
				logger.error(String.format("审批失败：根据SAP单号 %s未找到明细数据", vehicleSaleDocuments));
				throw new ServiceException("审批失败：拆分订单失败");
			}

			for (Map<String, Object> detailResult : detailResultList) {
				VehicleWriteOffDetails writeOffDetails = dao.get(VehicleWriteOffDetails.class,
						detailResult.get("apply_detail_id").toString());
				// 生成销账订单明细
				VehicleDfWriteOffApplyDetail newOrderDetail = new VehicleDfWriteOffApplyDetail();
				newOrderDetail.setWriteOffNo(newOrder.getWriteOffNo());
				newOrderDetail.setWriteOffDetailId(UUID.randomUUID().toString());
				newOrderDetail.setOffsetSequence(writeOffDetails.getOffsetSequence());
				newOrderDetail.setVehicleVno(writeOffDetails.getVehicleVno());
				newOrderDetail.setUnderpanNo(writeOffDetails.getUnderpanNo());
				newOrderDetail.setOffsetComment(writeOffDetails.getOffsetComment());
				newOrderDetail.setVehicleId(writeOffDetails.getVehicleId());
				newOrderDetail.setVehicleVin(writeOffDetails.getVehicleVin());
				newOrderDetail.setWriteOffAmount(writeOffDetails.getWriteOffAmount());
				newOrderDetail.setWriteOffAmountDfs(writeOffDetails.getWriteOffAmountDfs());
				newOrderDetail.setDocumentNo(newOrder.getDocumentNo());
				newOrderDetail.setInStockDetailId(writeOffDetails.getInStockDetailId());
				newOrderDetail.setInvoiceNum(writeOffDetails.getInvoiceNum());
				newOrderDetail.setDfObjId(writeOffDetails.getDfObjId());
				newOrderDetail.setCrmWriteOffId(writeOffDetails.getCrmWriteOffId());
				newOrderDetail.setCrmWriteOffDetailId(writeOffDetails.getCrmWriteOffDetailId());
				newOrderDetail.setDetailStatus(writeOffDetails.getDetailStatus());

				dao.save(newOrderDetail);
			}
		}

	}

}
