package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.Constant.DocumentEntries;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.AssetsExpensesDetail;
import cn.sf_soft.office.approval.model.AssetsOutStockDetail;
import cn.sf_soft.office.approval.model.AssetsOutStocks;
import cn.sf_soft.office.approval.model.AssetsStocks;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;

/**
 * @ClassName: AmAssetsScrapOutStock
 * @Description: 资产报废出库
 * @author xiongju
 * @date 2015-4-29 上午10:56:04
 * 
 */
@Service("assetsScrapOutStock")
public class AmAssetsScrapOutStock extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "35153020";
	
	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	@Override
	public AssetsOutStocks getDocumentDetail(String documentNo) {
		return dao.get(AssetsOutStocks.class, documentNo);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		AssetsOutStocks document = getDocumentDetail(approveDocument
				.getDocumentNo());
		Set<AssetsOutStockDetail> details = document.getChargeDetail();
		Set<String> objNos = new HashSet<String>();
		for (AssetsOutStockDetail detail : details) {
			objNos.add(detail.getAssetsNo());
		}
		// modify by shichunshan fix bug 站点未配置 2016/01/06
		// String stations = HttpSessionStore.getSessionUser().getSysStations()
		// .get(0).getStationGroup();
		// String[] stationArray = stations.split(",");
		Short statuShort = 1;
		DetachedCriteria dc = DetachedCriteria.forClass(AssetsStocks.class);
		dc.add(Restrictions.in("assetsNo", objNos));
		dc.add(Restrictions.eq("status", statuShort));
		// modify by shichunshan fix bug 站点未配置 2016/01/06
		// dc.add(Restrictions.in("stationId", stationArray));
		List<AssetsStocks> stocks = dao.findByCriteria(dc);

		for (AssetsOutStockDetail detail : details) {
			boolean flag = false;
			for (AssetsStocks stock : stocks) {
				if (stock.getAssetsNo().equals(detail.getAssetsNo())) {
					double quantity = stock.getQuantity() == null ? 0 : stock
							.getQuantity();
					double rec_quantity = stock.getRecQuantity() == null ? 0
							: stock.getRecQuantity();
					if (quantity < 1 && rec_quantity < 1) // 库存数<出库数，提示错误
					{
						throw new ServiceException("审批失败,["
								+ stock.getAssetsName() + "],出库数大于可报废数！");
					}
					double dcStockDepr = stock.getAccDepr() == null ? 0 : stock
							.getAccDepr();
					double dcDetailDepr = detail.getAccDepr() == null ? 0
							: detail.getAccDepr();
					if (Math.abs(dcStockDepr - dcDetailDepr) > 0.05) {
						throw new ServiceException("审批失败,["
								+ detail.getAssetsName()
								+ "]，在报废出库单据保存后做了折旧计提！请重新建报废出库单据。");
					}
					flag = true;
					break;
				}
			}
			if (!flag) {
				// 找不到资产维修单或资产维修单已作废
				throw new ServiceException("审批失败,[" + detail.getAssetsName()
						+ "]，库存中不存在该资产！");
			}
		}

		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument,
			String comment) {
		AssetsOutStocks document = getDocumentDetail(approveDocument
				.getDocumentNo());
		Set<AssetsOutStockDetail> details = document.getChargeDetail();
		Set<String> objNos = new HashSet<String>();
		for (AssetsOutStockDetail detail : details) {
			objNos.add(detail.getAssetsNo());
		}
		// modify by shichunshan fix bug 站点未配置 2016/01/06
		// String stations = HttpSessionStore.getSessionUser().getSysStations()
		// .get(0).getStationGroup();
		// String[] stationArray = stations.split(",");
		Short statuShort = 1;

		DetachedCriteria dcStock = DetachedCriteria
				.forClass(AssetsStocks.class);
		dcStock.add(Restrictions.in("assetsNo", objNos));
		dcStock.add(Restrictions.eq("status", statuShort));
		// modify by shichunshan fix bug 站点未配置 2016/01/06
		// dcStock.add(Restrictions.in("stationId", stationArray));
		List<AssetsStocks> stocks = dao.findByCriteria(dcStock);
		// 更新库存数量。
		for (AssetsOutStockDetail detail : details) {
			for (AssetsStocks stock : stocks) {
				if (stock.getAssetsNo().equals(detail.getAssetsNo())) {
					stock.setQuantity(0.0);
					stock.setRecQuantity(0.0);
					stock.setLossQuantity(1.0);
					dao.update(stock);
				}
			}
		}

		Short objType = 0;
		DetachedCriteria dc = DetachedCriteria
				.forClass(AssetsExpensesDetail.class);
		dc.add(Restrictions.eq("documentId", document.getDocumentNo()));
		dc.add(Restrictions.eq("objType", objType));
		List<AssetsExpensesDetail> expenses = dao.findByCriteria(dc);
		int i = 0;
		for (AssetsExpensesDetail expense : expenses) {
			double dPrice = expense.getAmount() == null ? 0 : expense
					.getAmount();

			FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
			financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
			financeDocumentEntries.setStationId(document.getStationId());
			financeDocumentEntries
					.setEntryProperty(DocumentEntries.ENTRIES_PROPERTY_INCLUDED_IN_CURRENT
							+ DocumentEntries.ENTRIES_PROPERTY_SETTLE_ACCOUNTS);
			financeDocumentEntries
					.setEntryType(DocumentEntries.ENTRIES_TYPE_NEED_SILVER);
			financeDocumentEntries.setDocumentType("资产-报废出库");
			financeDocumentEntries.setDocumentId(document.getDocumentNo() + "_"
					+ i);
			financeDocumentEntries.setDocumentNo(document.getDocumentNo() + "_"
					+ i);
			financeDocumentEntries.setSubDocumentNo(document.getDocumentNo()
					+ "_" + i);
			financeDocumentEntries.setObjectId(expense.getObjId());
			financeDocumentEntries.setObjectNo(expense.getObjNo());
			financeDocumentEntries.setObjectName(expense.getObjName());

			financeDocumentEntries.setAmountType(AmountType.ACCOUNT_RECEIVABLE);

			financeDocumentEntries.setLeftAmount(dPrice);
			financeDocumentEntries.setDocumentAmount(dPrice);
			financeDocumentEntries.setDocumentTime(new Timestamp(new Date()
					.getTime()));
			financeDocumentEntries.setOffsetAmount(0.00);
			financeDocumentEntries.setWriteOffAmount(0.00);
			financeDocumentEntries.setInvoiceAmount(0.00);
			financeDocumentEntries.setPaidAmount(0.00);

			financeDocumentEntries.setUserId(HttpSessionStore.getSessionUser()
					.getUserId());
			financeDocumentEntries.setUserName(HttpSessionStore
					.getSessionUser().getUserName());
			financeDocumentEntries.setUserNo(HttpSessionStore.getSessionUser()
					.getUserNo());
			financeDocumentEntries.setDepartmentId(HttpSessionStore
					.getSessionUser().getDepartment());
			financeDocumentEntries.setDepartmentName(HttpSessionStore
					.getSessionUser().getDepartmentName());
			financeDocumentEntries.setDepartmentNo(HttpSessionStore
					.getSessionUser().getDepartmentNo());
			// 2013-12-10 by 备注->摘要liujin
			// financeDocumentEntries.setSummary(document.getRemark());
			i++;
			if (!financeDocumentEntriesDao
					.insertFinanceDocumentEntries(financeDocumentEntries)) {
				throw new ServiceException("审批失败:插入单据分录出错");
			}
		}

		if (!createVoucher(approveDocument.getDocumentNo())) {
			throw new ServiceException("审批失败:生成凭证模板出错");
		}
		return super.onLastApproveLevel(approveDocument, comment);
	}

	private boolean createVoucher(String documentNo) {
		String sql = dao.getQueryStringByName("assetsScrapOutStockVoucherDS",
				null, null);
		return voucherAuto.generateVoucherByProc(sql, "35153000", false,
				HttpSessionStore.getSessionUser().getUserId(), documentNo);
		// String sql = dao.getQueryStringByName("assetsScrapOutStockVoucherDS",
		// new String[]{"documentNo"}, new String[]{"'" + documentNo + "'"});
		// return voucherAuto.generateVoucher(sql, "35153000", false,
		// HttpSessionStore.getSessionUser());
	}

	/**
	@SuppressWarnings("rawtypes")
	@Override
	public boolean checkDataChanged(String modifyTime,
			ApproveDocuments approveDocument) {
		String documentNo = approveDocument.getDocumentNo();
		AssetsOutStocks assetsOutStocks = dao.get(AssetsOutStocks.class,
				documentNo);
		Timestamp lastModifyTime = assetsOutStocks.getModifyTime();
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
