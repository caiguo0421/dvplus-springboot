package cn.sf_soft.office.approval.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.service.impl.InterestedCustomersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.common.util.CheckPopedom;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.GetChineseFirstChar;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.dao.BaseRelatedObjectsDao;
import cn.sf_soft.office.approval.dao.SaleContractsDao;
import cn.sf_soft.office.approval.dao.VehicleInvoicesDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.ConsumptionLoanFee;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleArchives;
import cn.sf_soft.office.approval.model.VehicleInvoices;
import cn.sf_soft.office.approval.model.VehicleSaleContractCharge;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractGifts;
import cn.sf_soft.office.approval.model.VehicleSaleContractInsurance;
import cn.sf_soft.office.approval.model.VehicleSaleContractItem;
import cn.sf_soft.office.approval.model.VehicleSaleContractPresent;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.office.approval.model.VwVehicleSaleContractDetail;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetCharge;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;

/**
 * 车辆销售合同
 * 
 * @author caigx
 * @date 2016.5.9
 */
@Service("afSaleContracts")
public class AfSaleContracts extends BaseApproveProcess {
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "10152220";
	
	private static final String LOWEST_PROFIT = "10152223"; // 低于最低利润审批

	java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
	@Autowired
	private SaleContractsDao saleContractsDao;

	@Autowired
	private BaseRelatedObjectsDao baseRelatedObjectsDao;

	@Autowired
	private VehicleInvoicesDao vehicleInvoicesDao;

	@Autowired
	private SysOptionsDao sysOptionsDao;

	
	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ApproveDocuments getSubmitRecordDetail(String documentNo) {
		return saleContractsDao.getDetailByDocumentNo(documentNo);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ApproveDocuments getDocumentDetail(String documentNo) {
		VehicleSaleContractDetail detail = saleContractsDao.getDetailByDocumentNo(documentNo);
		if (detail == null) {
			logger.error(documentNo + "没有找到车辆销售明细");
			throw new ServiceException("没有找到车辆销售审批明细");
		}
		VwVehicleSaleContractDetail vwDetail = saleContractsDao.getVwContractDetailByDetailId(detail
				.getContractDetailId());
		String detailId = detail.getContractDetailId();

		VehicleSaleContracts contract = dao.get(VehicleSaleContracts.class, detail.getContractNo());
		detail.setCustomerName(contract.getCustomerName());
		detail.setBuyType(contract.getBuyType());
		detail.setBuyTypeName(contract.getBuyTypeName());
		detail.setSeller(contract.getSeller());
		detail.setContractComment(contract.getContractComment());
		detail.setCreator(contract.getCreator()); // 制单人
		detail.setCreateTime(contract.getCreateTime()); // 制单时间

		// 保险
		double totalInsurance = 0.00D;
		List<VehicleSaleContractInsurance> insurances = saleContractsDao.getInsurancesByDetailId(detailId);
		if (insurances != null) {
			for (VehicleSaleContractInsurance insurance : insurances) {
				//修复Double.valueOf 改成 Tools.toDouble 修复空指针bug
				totalInsurance += Tools.toDouble(insurance.getCategoryIncome());
			}
			detail.setInsurances(insurances);
		}
		detail.setTotalInsurance(totalInsurance);

		// 精品
		double totalPresentCost = 0.00D;
		List<VehicleSaleContractPresent> presents = saleContractsDao.getPresentsByDetailId(detailId);
		if (presents != null) {
			for (VehicleSaleContractPresent present : presents) {
				totalPresentCost += Tools.toDouble(present.getPosPrice()) * Tools.toDouble(present.getPlanQuantity());
			}
			detail.setPresents(presents);
		}
		detail.setTotalPresentCost(totalPresentCost);

		// 改装
		double totalItemCost = 0.00D;
		List<VehicleSaleContractItem> items = saleContractsDao.getItemsByDetailId(detailId);
		if (items != null) {
			for (VehicleSaleContractItem item : items) {
				totalItemCost += Tools.toDouble(item.getItemCost());
			}
			detail.setItems(items);
		}
		detail.setTotalItemCost(totalItemCost);

		// 其他费用
		double totalChargePf = 0.00D;
		List<VehicleSaleContractCharge> charges = saleContractsDao.getChargesByDetailId(detailId);
		if (charges != null) {
			for (VehicleSaleContractCharge charge : charges) {
//				totalChargePf += charge.getChargePf() == null ? 0.00 : charge.getChargePf();
				totalChargePf+=Tools.toDouble(charge.getChargePf());
			}
			detail.setCharges(charges);
		}
		detail.setTotalChargePf(totalChargePf);

		// 发票
		double totalInvoiceAmount = 0.00D;
		List<VehicleInvoices> invoices = saleContractsDao.getInvoicesByDetailId(detailId);
		if (invoices != null) {
			for (VehicleInvoices invoice : invoices) {
//				totalInvoiceAmount += invoice.getInvoiceAmount() == null ? 0.00 : invoice.getInvoiceAmount();
				totalInvoiceAmount+=Tools.toDouble(invoice.getInvoiceAmount());
			}
			detail.setInvoices(invoices);
		}
		detail.setTotalInvoiceAmount(totalInvoiceAmount);

		// 消贷
		List<VwVehicleLoanBudgetCharge> budgetCharges = saleContractsDao.getBudgetChargesByDetailId(detailId);
		if (budgetCharges != null) {
			detail.setBudgetCharges(budgetCharges);
		}

		// 赠品
		double totalGiftAmount = 0.00D;
		List<VehicleSaleContractGifts> gifts = saleContractsDao.getGiftsByDetailId(detailId);
		if (gifts != null) {
			for (VehicleSaleContractGifts gift : gifts) {
				totalGiftAmount += Tools.toDouble(gift.getAmount());
			}
			detail.setTotalGiftAmount(totalGiftAmount);
			detail.setGifts(gifts);
		}

		// 是否含保费，如果为空 则为false
		if (detail.getIsContainInsuranceCost() == null) {
			detail.setIsContainInsuranceCost(false);
		}

		// 增加 消贷-车辆贷款，消贷-车辆首付
		detail.setLoanAmountLv(vwDetail.getLoanAmountLv());
		// first_pay_amount_vd=vehicle_price_total-loan_amount_lv
		detail.setFirstPayAmountVd(Tools.toDouble(vwDetail.getVehiclePrice())
				- Tools.toDouble(vwDetail.getLoanAmountLv()));

		// 消贷-车辆贷款，消贷-车辆首付加入到 loanFee 中
		if (20 == detail.getBuyType() && Tools.toDouble(vwDetail.getLoanAmountTot()) > 0) {
			// 如果是消贷
			List<ConsumptionLoanFee> loanFees = new ArrayList<ConsumptionLoanFee>();
			ConsumptionLoanFee loanFee = new ConsumptionLoanFee(detail.getContractDetailId());
			loanFee.setFirstPayAmountVd(detail.getFirstPayAmountVd());
			loanFee.setLoanAmountLv(detail.getLoanAmountLv());
			loanFees.add(loanFee);
			detail.setLoanFee(loanFees);
		}

		// 显示 预估利润、设定的最低利润、车辆销售价、车辆最低限价
		List<Map<String, Object>> catalogPrices = saleContractsDao.getVehiclePriceCatlog(detail.getStationId(),
				detail.getVnoId());
		if (catalogPrices != null && catalogPrices.size() > 0) {
			if (catalogPrices.get(0).get("profit_min") != null) {
				double minProfit = Double.parseDouble(catalogPrices.get(0).get("profit_min").toString());
				detail.setSetupProfit(minProfit); // 设定最小利润
			}

			if (catalogPrices.get(0).get("price_sale") != null) {
				double salePrice = Double.parseDouble(catalogPrices.get(0).get("price_sale").toString());
				detail.setSetupVehiclePrice(salePrice);
			}

		}
		detail.setTotalProfit(getTotalProfit(detail)); // 预估利润
		return detail;
	}

	/**
	 * 更新detail的数据 父单据，子单据的状态都是随父单据的状态
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void updateSubDocument(ApproveDocuments parentDocument, Timestamp approveTimestamp) {
		VehicleSaleContractDetail detail = saleContractsDao.getDetailByDocumentNo(parentDocument.getDocumentNo());
		detail.setStatus(parentDocument.getStatus());
		detail.setApproverId(parentDocument.getApproverId());
		detail.setApproverName(parentDocument.getApproverName());
		detail.setApproverNo(parentDocument.getApproverNo());
		detail.setApproveTime(approveTimestamp);

		dao.update(detail);
	}

	/**
	 * 重写审批不同意的方法，增加业务逻辑
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected ApproveResult approveDisagreeByPC(ApproveDocuments approveDocument, String comment) {
		VehicleSaleContractDetail detail = saleContractsDao.getDetailByDocumentNo(approveDocument.getDocumentNo());
		if (detail == null) {
			logger.error(approveDocument.getDocumentNo() + "没有找到车辆销售明细");
			throw new ServiceException("没有找到车辆销售审批明细");
		}

		detail.setApproveStatus((short) 0);
		detail.setSubmitTime(null);
		if (!dao.update(detail)) {
			return new ApproveResult(ApproveResultCode.SYSTEM_ERROR);
		}
		return super.approveDisagreeByPC(approveDocument, comment);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
		// 处理客户佣金
		VehicleSaleContractDetail detail = saleContractsDao.getDetailByDocumentNo(approveDocument.getDocumentNo());
		VehicleSaleContracts contract = dao.get(VehicleSaleContracts.class, detail.getContractNo());
		VwVehicleSaleContractDetail vwDetail = saleContractsDao.getVwContractDetailByDetailId(detail
				.getContractDetailId());
		double priceProfit = Tools.toDouble(vwDetail.getVehicleProfit());
		if (priceProfit > 0) {
			String sCId = !StringUtils.isEmpty(vwDetail.getCustomerIdProfit()) ? vwDetail.getCustomerIdProfit()
					: contract.getCustomerId();
			String sCNo = !StringUtils.isEmpty(vwDetail.getCustomerIdProfit()) ? vwDetail.getCustomerNoProfit()
					: contract.getCustomerNo();
			String sCName = !StringUtils.isEmpty(vwDetail.getCustomerIdProfit()) ? vwDetail.getCustomerNameProfit()
					: contract.getCustomerName();
			FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
			financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
			financeDocumentEntries.setStationId(vwDetail.getStationId());
			financeDocumentEntries.setEntryProperty(Constant.DocumentEntries.ENTRIES_PROPERTY_INCLUDED_IN_CURRENT);
			financeDocumentEntries.setEntryType(Constant.DocumentEntries.ENTRIES_TYPE_NEED_APPLAY);
			financeDocumentEntries.setDocumentType("车辆-客户佣金");

			financeDocumentEntries.setSubDocumentNo(vwDetail.getContractDetailId());

			financeDocumentEntries.setObjectId(sCId);
			financeDocumentEntries.setObjectNo(sCNo);
			financeDocumentEntries.setObjectName(sCName);
			financeDocumentEntries.setAmountType(AmountType.ACCOUNT_PAYABLE);// 70
			financeDocumentEntries.setDocumentAmount(priceProfit);

			financeDocumentEntries.setDocumentId(vwDetail.getContractDetailId());
			financeDocumentEntries.setDocumentNo(vwDetail.getVehicleVin() + "," + vwDetail.getContractNo());
			financeDocumentEntries.setSummary(null);
			financeDocumentEntries.setUserId(vwDetail.getUserId());
			financeDocumentEntries.setUserName(vwDetail.getUserName());
			financeDocumentEntries.setUserNo(vwDetail.getUserNo());
			financeDocumentEntries.setDepartmentId(vwDetail.getDepartmentId());
			financeDocumentEntries.setDepartmentName(vwDetail.getDepartmentName());

			financeDocumentEntries.setDepartmentNo(vwDetail.getDepartmentNo());
			financeDocumentEntries.setLeftAmount(priceProfit);

			financeDocumentEntries.setDocumentTime(new Timestamp(new Date().getTime()));
			financeDocumentEntries.setOffsetAmount(0.00);
			financeDocumentEntries.setWriteOffAmount(0.00);
			financeDocumentEntries.setInvoiceAmount(0.00);
			financeDocumentEntries.setPaidAmount(0.00);

			if (!financeDocumentEntriesDao.insertFinanceDocumentEntries(financeDocumentEntries)) {
				return new ApproveResult(ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
			}
		}
		detail.setApproveStatus((short) 1);
		updateCustomer(detail);// 更新客户信息
		updateVehicleArchive(detail);// 更新车辆档案信息
		updateChargeDocument(detail); // 处理费用单据分录

		return super.onLastApproveLevel(approveDocument, comment);
	}

	/**
	 * 处理费用单据分录
	 * 
	 * @param detail
	 * @return
	 */
	private boolean updateChargeDocument(VehicleSaleContractDetail detail) {
		VehicleSaleContracts saleContract = dao.get(VehicleSaleContracts.class, detail.getContractNo());
		List<VehicleSaleContractCharge> paidCharges = saleContractsDao.getPaidCharges(detail.getContractDetailId());

		SysUsers user = HttpSessionStore.getSessionUser();
		if (paidCharges != null && paidCharges.size() > 0) {
			for (VehicleSaleContractCharge paidCharge : paidCharges) {
				double mChargeAmount = Tools.toDouble(paidCharge.getChargePf());
				String sSummary = paidCharge.getRemark();
				if (mChargeAmount == 0)
					continue;

				FinanceDocumentEntries entry = new FinanceDocumentEntries();
				entry.setEntryId(UUID.randomUUID().toString());
				entry.setStationId(saleContract.getStationId());
				entry.setEntryProperty(19);
				entry.setEntryType((short) 15);
				entry.setDocumentType("车辆-" + paidCharge.getChargeName());
				entry.setDocumentId(paidCharge.getSaleContractChargeId());
				entry.setDocumentNo(detail.getVehicleVin() + "," + detail.getContractNo());
				entry.setSubDocumentNo(detail.getContractNo());
				entry.setObjectId(saleContract.getCustomerId());
				entry.setObjectNo(saleContract.getCustomerNo());
				entry.setObjectName(saleContract.getCustomerName());

				entry.setAmountType((short) 20);
				entry.setLeftAmount(mChargeAmount);
				entry.setDocumentAmount(mChargeAmount);
				entry.setDocumentTime(new Timestamp(new Date().getTime()));
				entry.setUserId(user.getUserId());
				entry.setUserNo(user.getUserNo());
				entry.setUserName(user.getUserName());
				entry.setDepartmentId(user.getDepartment());
				entry.setDepartmentNo(user.getDepartmentNo());
				entry.setDepartmentName(user.getDepartmentName());
				entry.setOffsetAmount(0.00);
				entry.setPaidAmount(0.00);
				entry.setWriteOffAmount(0.00);
				entry.setInvoiceAmount(0.00);
				entry.setSummary(sSummary);

				if (!financeDocumentEntriesDao.insertFinanceDocumentEntries(entry)) {
					logger.error(String.format("%s 添加费用单据分录出错,SaleContractChargeId:%s", detail.getDocumentNo(),
							paidCharge.getSaleContractChargeId()));
					throw new ServiceException("审批失败:添加费用单据分录出错！");
				}
			}
		}
		return true;
	}

	/**
	 * 获得预估利润
	 * 
	 * @param detail
	 * @return
	 */
	private double getTotalProfit(VehicleSaleContractDetail detail) {
		double decTotalIncome = 0.00D; // 实际收入
		double decTotalCostReal = 0.00D;// 实际成本统计
		double decOilCard = 0.00D; // 油卡收入
		double decTotalCost = 0.00D;// 预估成本统计

		String detailId = detail.getContractDetailId();
		VwVehicleSaleContractDetail vwDetail = saleContractsDao.getVwContractDetailByDetailId(detail
				.getContractDetailId());
		List<VehicleSaleContractGifts> gifts = saleContractsDao.getGiftsByDetailId(detailId);
		if (gifts != null) {
			for (VehicleSaleContractGifts gift : gifts) {
				// 赠品明细不为终止且赠品类型为油卡且不赠送客户时计为油卡收入
				if (!"10".equals(String.valueOf(gift.getAbortStatus()))
						&& "10".equals(String.valueOf(gift.getItemType()))
						&& "0".equals(String.valueOf(gift.getGiveFlag()))) {
					decOilCard += Tools.toDouble(gift.getAmount());
				}
			}
		}
		decTotalIncome = Tools.toDouble(vwDetail.getVehiclePriceTotal()) + decOilCard;
		// 车辆成本
		double vehicleCost = 0.00D;
		double vehicleCostReal = Tools.toDouble(vwDetail.getVehicleCost());
		if (!StringUtils.isEmpty(vwDetail.getVehicleVin())) {
			vehicleCost = Tools.toDouble(vwDetail.getVehicleCost());
		} else {
			vehicleCost = Tools.toDouble(vwDetail.getVehicleCostRef());
		}

		decTotalCost += vehicleCost;
		decTotalCostReal += vehicleCostReal;

		// 保险费用
		double insuranceCostReal = 0.00D;
		double insuranceCost = 0.00D;
		if (detail.getIsContainInsuranceCost()) {// 含保费
			insuranceCostReal = Tools.toDouble(vwDetail.getInsuranceCost());
			List<VehicleSaleContractInsurance> insurances = saleContractsDao.getInsurancesByDetailId(detailId);
			if (insurances != null) {
				for (VehicleSaleContractInsurance insurance : insurances) {
					insuranceCost += Tools.toDouble(insurance.getCategoryIncome())
							* Tools.toDouble(insurance.getCategoryScale());
				}
			}
		}
		decTotalCost += insuranceCost;
		decTotalCostReal += insuranceCostReal;
		// 精品预估成本显示
		double presentCostReal = 0.00D;
		double presentCost = 0.00D;
		List<VehicleSaleContractPresent> presents = saleContractsDao.getPresentsByDetailId(detailId);
		if (presents != null) {
			for (VehicleSaleContractPresent present : presents) {
				presentCostReal += Tools.toDouble(present.getCostRecord()) * Tools.toDouble(present.getGetQuantity());
				presentCost += (Tools.toDouble(present.getPosPrice()) + Tools.toDouble(present.getCarriageRecord()))
						* Tools.toDouble(present.getPlanQuantity());
			}
		}
		decTotalCost += presentCost;
		decTotalCostReal += presentCostReal;
		// 改装费用
		double conversionCost = Tools.toDouble(vwDetail.getConversionPf());
		double conversionCostReal = Tools.toDouble(vwDetail.getConversionCost());
		decTotalCost += conversionCost;
		decTotalCostReal += conversionCostReal;
		// 单车运费
		double carriageCost = Tools.toDouble(vwDetail.getVehicleCarriage());
		double carriageCostReal = carriageCost;
		decTotalCost += carriageCost;
		decTotalCostReal += carriageCostReal;
		// 客户佣金
		double vehicleProfitCost = Tools.toDouble(vwDetail.getVehicleProfit());
		double vehicleProfitCostReal = vehicleProfitCost;
		decTotalCost += vehicleProfitCost;
		decTotalCostReal += vehicleProfitCostReal;
		// 赠送金额
		double largessCost = Tools.toDouble(vwDetail.getLargessAmount()) + Tools.toDouble(vwDetail.getLargessPart())
				+ Tools.toDouble(vwDetail.getLargessService());
		double largessCostReal = largessCost;
		decTotalCost += largessCost;
		decTotalCostReal += largessCostReal;
		// 其他费用
		double chargeCost = 0.00D;
		double chargeCostReal = 0.00D;
		List<VehicleSaleContractCharge> charges = saleContractsDao.getChargesByDetailId(detailId);
		if (charges != null) {
			for (VehicleSaleContractCharge charge : charges) {
				if (charge.getPaidByBill() == null || charge.getPaidByBill() == false) {
					chargeCost += Tools.toDouble(charge.getChargePf());
					chargeCostReal += Tools.toDouble(charge.getChargeCost());
				}
			}
		}
		decTotalCost += chargeCost;
		decTotalCostReal += chargeCostReal;
		// 移库费用
		double moveCost = Tools.toDouble(vwDetail.getMoveStockCharge());
		double moveCostReal = moveCost;
		decTotalCost += moveCost;
		decTotalCostReal += moveCostReal;
		// 其他附加成本（如退车产生的报销费用）
		double otherCost = Tools.toDouble(vwDetail.getOtherCost());
		double otherCostReal = otherCost;
		decTotalCost += otherCost;
		decTotalCostReal += otherCostReal;
		// 厂家返利
		double returnCost = -Tools.toDouble(vwDetail.getProfitReturnPf());
		double returnCostReal = -Tools.toDouble(vwDetail.getProfitReturnReal());
		decTotalCost += returnCost;
		decTotalCostReal += returnCostReal;
		double decTotalProfit = decTotalIncome - decTotalCost;

		BigDecimal bgTotalIncome = new BigDecimal(decTotalIncome);
		double totalIncome = bgTotalIncome.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal bgTotalCost = new BigDecimal(decTotalCost);
		double totalCost = bgTotalCost.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal bgTotalProfit = new BigDecimal(decTotalProfit);
		double totalProfit = bgTotalProfit.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal bgTotalCostReal = new BigDecimal(decTotalCostReal);
		double totalCostReal = bgTotalCostReal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal bgRealTotalProfit = new BigDecimal(decTotalIncome - decTotalCostReal);
		double realTotalProfit = bgRealTotalProfit.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		logger.debug(String.format("单号 %s,底盘号 %s,收入合计:%f,预估成本:%f,预估利润:%f,实际成本:%f,实际利润:%f", detail.getDocumentNo(),
				detail.getVehicleVin(), totalIncome, totalCost, totalProfit, totalCostReal, realTotalProfit));

		return totalProfit;//
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
		VehicleSaleContractDetail detail = saleContractsDao.getDetailByDocumentNo(approveDocument.getDocumentNo());
		if (detail == null) {
			throw new ServiceException("审批失败:销售合同中没有合同明细");
		}
		validateCustomer(detail);
//		if (checkConvering(detail)) {
//			throw new ServiceException("审批失败:该车辆外委改装项目未完成");
//		}
		//checkProft(detail); //现在处理方式把【车辆单价是否低于最低限价】（是或否）作为审批条件
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}

	/**
	 * 更新车辆档案
	 * 
	 * @param detail
	 */
	private boolean updateVehicleArchive(VehicleSaleContractDetail detail) {
		if (StringUtils.isEmpty(detail.getVehicleId())) {
			return true; // 如果合同明细的 车辆Id为空，则跳过
		}
		
		VehicleArchives archives = dao.get(VehicleArchives.class, detail.getVehicleId());
		VehicleSaleContracts contract = dao.get(VehicleSaleContracts.class, detail.getContractNo());
		VwVehicleSaleContractDetail vwDetail = saleContractsDao.getVwContractDetailByDetailId(detail
				.getContractDetailId());
		
		if (archives == null) {
			archives = new VehicleArchives();
			archives.setVehicleId(detail.getVehicleId());
		}

		archives.setStationId(contract.getStationId());
		archives.setCustomerId(contract.getCustomerId());
		archives.setVehicleLinkman(contract.getLinkman());
		archives.setVehicleLinkmanPhone(contract.getLinkman());
		archives.setVehicleLinkmanMobile(contract.getCustomerMobile());
		archives.setVehicleLinkmanAddress(contract.getCustomerAddress());
		archives.setSeller(contract.getSeller());
		archives.setSellerId(contract.getSellerId());
		archives.setCreator(contract.getCreator());
		archives.setCreateTime(contract.getCreateTime());
		archives.setVnoId(StringUtils.isEmpty(vwDetail.getVnoIdNew()) ? vwDetail.getVnoId() : vwDetail.getVnoIdNew());
		archives.setVehicleVno(StringUtils.isEmpty(vwDetail.getVehicleVnoNew()) ? vwDetail.getVehicleVno() : vwDetail
				.getVehicleVnoNew());
		archives.setVehicleVin(StringUtils.isEmpty(vwDetail.getVehicleVinNew()) ? vwDetail.getVehicleVin() : vwDetail
				.getVehicleVinNew());
		archives.setVehicleSalesCode(vwDetail.getVehicleSalesCode());
		archives.setVehicleName(StringUtils.isEmpty(vwDetail.getVehicleNameNew()) ? vwDetail.getVehicleName()
				: vwDetail.getVehicleNameNew());
		archives.setVehicleStrain(vwDetail.getVehicleStrain());
		archives.setVehicleColor(vwDetail.getVehicleColor());
		archives.setVehicleEngineType(vwDetail.getVehicleEngineType());
		archives.setVehicleEngineNo(vwDetail.getVehicleEngineNo());
		archives.setVehicleEligibleNo(StringUtils.isEmpty(vwDetail.getVehicleEligibleNoNew()) ? vwDetail
				.getVehicleEligibleNo() : vwDetail.getVehicleEligibleNoNew());
		archives.setVehicleOutFactoryTime(vwDetail.getVehicleOutFactoryTime());
		archives.setVehiclePurchaseTime(vwDetail.getRealDeliverTime());
		archives.setVehicleCardNo(vwDetail.getVehicleCardNo());
		archives.setVehiclePrice(vwDetail.getVehiclePrice());
		archives.setVehiclePurchaseFlag(true);
		archives.setMaintainRemindFlag(true);
		archives.setVehicleBelongTo(false);
		archives.setStatus((short) 1);
		archives.setDriveRoomNo(vwDetail.getDriveRoomNo());
		SysUsers user = HttpSessionStore.getSessionUser();
		archives.setModifier(user.getUserFullName());
		archives.setModifyTime(new Timestamp(new Date().getTime()));

		List<String> list = getRebateObject(vwDetail);
		if (list.size() > 0) {
			archives.setBelongToSupplierId(list.get(0));
			archives.setBelongToSupplierNo(list.get(1));
			archives.setBelongToSupplierName(list.get(2));
		} else {
			archives.setBelongToSupplierId(null);
			archives.setBelongToSupplierNo(null);
			archives.setBelongToSupplierName(null);
		}
		archives.setBackAllow(1023);
		archives.setProfession(vwDetail.getProfession());
		archives.setVehicleLinkmanAddress(contract.getCustomerAddress());

		dao.update(archives); // 新增或修改
		return true;
	}

	/**
	 * 获取挂靠返利对象（发票类型为购车发票的对象）
	 * 
	 * @param vwDetail
	 *            合同车辆明细ID
	 * @return
	 */
	private List<String> getRebateObject(VwVehicleSaleContractDetail vwDetail) {
		List<String> list = new ArrayList<String>();
		List<VehicleInvoices> invoices = vehicleInvoicesDao.getVehicleInvoicesByContractId(vwDetail
				.getContractDetailId());
		if (invoices != null && invoices.size() == 1) {
			list.add(invoices.get(0).getObjectId());
			list.add(invoices.get(0).getObjectNo());
			list.add(invoices.get(0).getObjectName());
		} else {
			list.add(vwDetail.getCustomerId());
			list.add(vwDetail.getCustomerNo());
			list.add(vwDetail.getCustomerName());
		}
		return list;
	}

	/**
	 * 更新往来对象
	 */
	private boolean updateCustomer(VehicleSaleContractDetail detail) {
		VehicleSaleContracts contract = dao.get(VehicleSaleContracts.class, detail.getContractNo());
		if (contract == null) {
			throw new ServiceException("更新客户档案失败");
		}
		BaseRelatedObjects relObj = dao.get(BaseRelatedObjects.class, contract.getCustomerId());
		if (relObj == null) {
			throw new ServiceException("更新客户档案失败：客户未找到");
		} else {
			if (relObj.getStatus() == null || relObj.getStatus() <= 0) {
				throw new ServiceException("更新客户档案失败:客户已被禁用");
			}
		}
		SysUsers user = HttpSessionStore.getSessionUser();
		if (StringUtils.isEmpty(relObj.getStationId())) {
			relObj.setStationId(user.getInstitution().getDefaultStation());
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
		relObj.setObjectKind(Tools.toInt(relObj.getObjectKind()) | 7);

		relObj.setPlanBackTime(new Timestamp(new Date().getTime()));
		relObj.setBackFlag(true);
		relObj.setCustomerType((short) 30);
		relObj.setModifier(user.getUserName());
		relObj.setModifyTime(new Timestamp(new Date().getTime()));

		dao.update(relObj);
		return true;
	}

	/**
	 * 校验客户的合法性
	 * 
	 * @return
	 */
	private Boolean validateCustomer(VehicleSaleContractDetail detail) {
		VehicleSaleContracts contract = dao.get(VehicleSaleContracts.class, detail.getContractNo());
		if (contract == null) {
			throw new ServiceException("审批失败:未找到合同信息");
		}
		BaseRelatedObjects relObj = dao.get(BaseRelatedObjects.class, contract.getCustomerId());
		if (relObj == null || !"1".equals(String.valueOf(relObj.getStatus()))) {
			// 没有=1的情况
			throw new ServiceException("审批失败:往来对象中该客户已被禁用");
		}

		// 如果客户名称不一致，修改 合同中的客户名称
		if (contract.getCustomerName() != null && !contract.getCustomerName().equals(relObj.getObjectName())) {
			contract.setCustomerName(relObj.getObjectName());
		}

		Short objectNature = relObj.getObjectNature();
		List<BaseRelatedObjects> sameCertificateCustomers = baseRelatedObjectsDao.getCustomerByCertificate(
				contract.getCustomerCertificateNo(), contract.getCustomerId());
		// 如果为单位，对象名称不允许重复
		if (objectNature == null || objectNature == 10) {
			List<BaseRelatedObjects> sameNameCustomers = baseRelatedObjectsDao.getCustomerByName(
					contract.getCustomerName(), contract.getCustomerId());
			if (sameNameCustomers != null && sameNameCustomers.size() > 0) {
				logger.error(String.format("%s 存在同名客户，客户ID:%s", detail.getDocumentNo(), sameNameCustomers.get(0)
						.getObjectId()));
				throw new ServiceException("审批失败:客户名称已经存在");
			}

			if (sameCertificateCustomers != null && sameCertificateCustomers.size() > 0) {
				if(!"东贸版".equals(sysOptionsDao.getOptionForString(InterestedCustomersService.DV_VERSION)) ||
						(!InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER.equals(contract.getCustomerCertificateNo()) && !InterestedCustomersService.DEFAULT_CER_NO_UNIT.equals(contract.getCustomerCertificateNo()))) {
					logger.error(String.format("%s 存在相同证件号码的客户，客户ID %s，证件号码  %s", detail.getDocumentNo(),
							sameCertificateCustomers.get(0).getObjectId(), sameCertificateCustomers.get(0)
									.getCertificateNo()));
					throw new ServiceException("审批失败:客户的证件号码已经存在");
				}
			}
		} else
		// 对象名称和手机不能同时重复
		{
			if (!StringUtils.isEmpty(contract.getCustomerCertificateNo())) {
				if (sameCertificateCustomers != null && sameCertificateCustomers.size() > 0) {
					if(!"东贸版".equals(sysOptionsDao.getOptionForString(InterestedCustomersService.DV_VERSION)) ||
							(!InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER.equals(contract.getCustomerCertificateNo()) && !InterestedCustomersService.DEFAULT_CER_NO_UNIT.equals(contract.getCustomerCertificateNo()))) {
						logger.error(String.format("%s 存在相同证件号码的客户，客户ID %s，证件号码  %s", detail.getDocumentNo(),
								sameCertificateCustomers.get(0).getObjectId(), sameCertificateCustomers.get(0)
										.getCertificateNo()));
						throw new ServiceException("审批失败:客户的证件号码已经存在");
					}
				}
			}
			// 往来对象中已存在名称相同或移动电话相同的客户信息,因为是提示选择，所以不做
		}
		return true;
	}

	/**
	 * 检查车辆是否正处于改装中
	 * 
	 * @return
	 */
	private Boolean checkConvering(VehicleSaleContractDetail detail) {
		if (detail == null || StringUtils.isEmpty(detail.getVehicleId())) {
			return false;
		}
		VehicleStocks stocks = dao.get(VehicleStocks.class, detail.getVehicleId());
		if (stocks == null || stocks.getConversionStatus() == null || stocks.getConversionStatus() != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 检查利润,已过时，现在处理方式把【车辆单价是否低于最低限价】（是或否）作为审批条件
	 * 
	 * @param detail
	 * @return
	 */
	@Deprecated
	private Boolean checkProft(VehicleSaleContractDetail detail) {
		VwVehicleSaleContractDetail vwDetail = saleContractsDao.getVwContractDetailByDetailId(detail
				.getContractDetailId());

		String detailId = vwDetail.getContractDetailId();
		String vin = vwDetail.getVehicleVin();
		String salesCode = vwDetail.getVehicleSalesCode();

		double profitDiff = getProfitDiff(detail);
		List<Map<String, Object>> catalogPrices = saleContractsDao.getVehiclePriceCatlog(vwDetail.getStationId(),
				vwDetail.getVnoId());
		if (catalogPrices != null && catalogPrices.size() > 0) {
			double salePrice = catalogPrices.get(0).get("price_sale") == null ? 0.00 : Double.parseDouble(catalogPrices
					.get(0).get("price_sale").toString());
			double minProfit = catalogPrices.get(0).get("profit_min") == null ? 0.00 : Double.parseDouble(catalogPrices
					.get(0).get("profit_min").toString());
			HttpSession session = HttpSessionStore.getHttpSession();
			SysUsers user = (SysUsers) session.getAttribute(Attribute.SESSION_USER);
			if (minProfit > 0) {
				if (profitDiff < 0) {// 利润差小于0（真实利润 - 设定最低利润 < 0)
					if (!CheckPopedom.checkPopedom(user, LOWEST_PROFIT)) {
						throw new ServiceException("车辆预估利润小于设定的最低利润，需要授权审批人审批");
					}
				}
			}
			if (salePrice > 0) {
				if (Tools.toDouble(vwDetail.getVehiclePrice()) < salePrice) {
					if (!CheckPopedom.checkPopedom(user, LOWEST_PROFIT)) {
						throw new ServiceException("车辆售价小于设定的最低限价，需要授权审批人审批");
					}
				}
			}
		}
		return true;
	}

	/**
	 * 获得车辆的利润差
	 * 
	 * @param detail
	 * @return
	 */
	private double getProfitDiff(VehicleSaleContractDetail detail) {
		double profitDiff = 0.00; // 当前车辆的利润差
		VwVehicleSaleContractDetail vwDetail = saleContractsDao.getVwContractDetailByDetailId(detail
				.getContractDetailId());
		if (vwDetail == null) {
			return 0.00;
		}
		String detailId = vwDetail.getContractDetailId();
		double chargePF = 0.00;
		List<VehicleSaleContractCharge> charges = saleContractsDao.getChargesByDetailId(detailId);
		if (charges != null) {
			for (VehicleSaleContractCharge charge : charges) {
				if (charge.getPaidByBill() == null || charge.getPaidByBill() == false) {
//					chargePF += charge.getChargePf() == null ? 0.00 : charge.getChargePf();
					chargePF+=Tools.toDouble(charge.getChargePf());
				}
			}
		}
		double insurancePF = 0.00;
		// 不含保费
		if (!vwDetail.getIsContainInsuranceCost()) {
			insurancePF += vwDetail.getInsurancePf() == null ? 0.00 : vwDetail.getInsurancePf();
		}

		profitDiff = Tools.toDouble(vwDetail.getVehiclePriceTotal())
				+ Tools.toDouble(vwDetail.getOilCardProfit())
				+ Tools.toDouble(vwDetail.getProfitReturn())
				+ Tools.toDouble(vwDetail.getBelongToSupplierRebate())
				+ Tools.toDouble(vwDetail.getProfitPf()) // 厂家预提返利
				- (Tools.toDouble(vwDetail.getVehicleCost()) > 0 ? Tools.toDouble(vwDetail.getVehicleCost()) : Tools
						.toDouble(vwDetail.getVehicleCostRef())) - Tools.toDouble(vwDetail.getMinProfit())
				- Tools.toDouble(vwDetail.getPresentPf()) + insurancePF + Tools.toDouble(vwDetail.getConversionPf())
				+ chargePF + Tools.toDouble(vwDetail.getVehicleProfit())
				+ Tools.toDouble(vwDetail.getVehicleCarriage()) + Tools.toDouble(vwDetail.getMoveStockCharge())
				+ Tools.toDouble(vwDetail.getOtherCost());
		return profitDiff;
	}
	
	
	public static void main(String[] args){
		Double d = null;
		System.out.println(Tools.toDouble(d));
		//System.out.println(Double.valueOf(d));
	}
}
