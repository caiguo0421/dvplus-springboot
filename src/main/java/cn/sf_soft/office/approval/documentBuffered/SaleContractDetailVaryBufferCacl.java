package cn.sf_soft.office.approval.documentBuffered;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferCalc;
import cn.sf_soft.mobile.documentBuffer.model.AppBoardFieldType;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentBuffered;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.model.VwVehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Field;
import cn.sf_soft.office.approval.ui.model.Property;

/**
 * 合同变更-车辆明细
 * 
 * @author caigx
 *
 */
@Service("saleContractDetailVaryBufferCacl")
public class SaleContractDetailVaryBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractDetailVaryBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractDetailVaryBufferCacl.class.getSimpleName();

	private static String moduleId = "102025";

	// 10 终止 20 新增 30 修改
	private static final short STATUS_NEW = 20;
	private static final short STATUS_MODIFY = 30;
	private static final short STATUS_ABORT = 10;

	@Autowired
	private SaleContractInsuranceVaryBufferCacl saleContractInsuranceVaryBufferCacl;

	@Autowired
	private SaleContractPresentVaryBufferCacl saleContractPresentVaryBufferCacl;

	@Autowired
	private SaleContractItemVaryBufferCacl saleContractItemVaryBufferCacl;

	@Autowired
	private SaleContractChargeVaryBufferCacl saleContractChargeVaryBufferCacl;

	@Autowired
	private SaleContractGiftVaryBufferCacl saleContractGiftVaryBufferCacl;

	@Autowired
	private SaleContractInvoiceVaryBufferCacl saleContractInvoiceVaryBufferCacl;

	@Override
	public String getBufferCalcVersion() {
		return bufferVersion;
	}

	@Override
	public void computeStaticFields(MobileDocumentBuffered doc) {

	}

	@Override
	public void computeDynamicFields(MobileDocumentBuffered doc) {

	}

	public MobileBoardBuffered computeFields(VehicleSaleContractDetailVary contractDetailVary, int i,
			boolean isFirstVehicleExpanded) {
		String abortStatusStr = "";
		if (STATUS_NEW == contractDetailVary.getAbortStatus()) {
			abortStatusStr = "新增";
		} else if (STATUS_MODIFY == contractDetailVary.getAbortStatus()) {
			abortStatusStr = "修改";
		} else if (STATUS_ABORT == contractDetailVary.getAbortStatus()) {
			abortStatusStr = "终止";
		}

		MobileBoardBuffered board = new MobileBoardBuffered();
		// board.setClassCode(VehicleSaleContractDetailVary.class.getSimpleName());
		// board.setObjectId(contractDetailVary.getDetailVaryId());
		String title = String.format("%d、%s %s", i, abortStatusStr, contractDetailVary.getVehicleVno());// 1、新增DFL-KD6J-0987

		BoardTitle salveBoardTitle = new BoardTitle();
		salveBoardTitle.setTitle(title);
		salveBoardTitle.setCollapsable(true);
		salveBoardTitle.setDefaultExpanded(isFirstVehicleExpanded);

		BoardTitle detailBoardTitle = new BoardTitle();
		detailBoardTitle.setTitle(title);
		detailBoardTitle.setCollapsable(true);
		detailBoardTitle.setDefaultExpanded(true);
		
		createBoardTitle(board, null, gson.toJson(detailBoardTitle), gson.toJson(salveBoardTitle), null);

		DocTitle docTitle = new DocTitle("车辆信息");
		board.setDocTitle(gson.toJson(docTitle));

		// dao.save(board);

		short sn = 1;
		if (contractDetailVary.getAbortStatus() == STATUS_NEW || contractDetailVary.getAbortStatus() == STATUS_MODIFY) {
			// 车型
			Property vnoField = getVno(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vnoField != null) {
				saveProperty(board, "vno", sn++, vnoField);
			}
			// 颜色
			Property vehicleColorField = getVehicleColor(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleColorField != null) {
				saveProperty(board, "vehicleColor", sn++, vehicleColorField);
			}
			// VIN
			Property vehicleVinField = getVehicleVin(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleVinField != null) {
				saveProperty(board, "vehicleVin", sn++, vehicleVinField);
			}
			// 计划交车时间
			Property planDeliverTime = getPlanDeliverTime(contractDetailVary, contractDetailVary.getAbortStatus());
			if (planDeliverTime != null) {
				saveProperty(board, "planDeliverTime", sn++, planDeliverTime);
			}
			// 是否含保费
			Property isContainInsuranceCost = getisContainInsuranceCost(contractDetailVary,
					contractDetailVary.getAbortStatus());
			if (isContainInsuranceCost != null) {
				saveProperty(board, "isContainInsuranceCost", sn++, isContainInsuranceCost);
			}
			// 车辆总价
			Property vehiclePriceTotal = getVehiclePriceTotal(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehiclePriceTotal != null) {
				saveProperty(board, "vehiclePriceTotal", sn++, vehiclePriceTotal);
			}
			// 车辆单价
			Property vehiclePrice = getVehiclePrice(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehiclePrice != null) {
				saveProperty(board, "vehiclePrice", sn++, vehiclePrice);
			}

			// 优惠金额
			Property discountAmount = getDiscountAmount(contractDetailVary, contractDetailVary.getAbortStatus());
			if (discountAmount != null) {
				saveProperty(board, "discountAmount", sn++, discountAmount);
			}

			// 保险统计
			sn = createInsuranceSummary(board, sn, contractDetailVary);

			// 精品统计
			sn = createPresentSummary(board, sn, contractDetailVary);

			// 改装统计
			sn = createItemSummary(board, sn, contractDetailVary);

			// 费用统计
			sn = createChargeSummary(board, sn, contractDetailVary);

			// 单车利润
			sn = createProfitPf(board, sn,contractDetailVary);

			// 佣金
			Property vehicleProfit = getVehicleProfit(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleProfit != null) {
				saveProperty(board, "vehicleProfit", sn++, vehicleProfit);
			}
			// 佣金客户
			Property customerProfit = getCustomerProfit(contractDetailVary, contractDetailVary.getAbortStatus());
			if (customerProfit != null) {
				saveProperty(board, "customerProfit", sn++, customerProfit);
			}

			// 公司赠券
			Property largessAmount = getLargessAmount(contractDetailVary, contractDetailVary.getAbortStatus());
			if (largessAmount != null) {
				saveProperty(board, "largessAmount", sn++, largessAmount);
			}

			// 交车地点
			Property deliverAddress = getDeliverAddress(contractDetailVary, contractDetailVary.getAbortStatus());
			if (deliverAddress != null) {
				saveProperty(board, "deliverAddress", sn++, deliverAddress);
			}
			// 标的物
			Property subjectMatter = getSubjectMatter(contractDetailVary, contractDetailVary.getAbortStatus());
			if (subjectMatter != null) {
				saveProperty(board, "subjectMatter", sn++, subjectMatter);
			}
			// 车牌号码
			Property vehicleCardNo = getVehicleCardNo(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleCardNo != null) {
				saveProperty(board, "vehicleCardNo", sn++, vehicleCardNo);
			}
			// 上牌型号
			Property vehicleVnoNew = getVehicleVnoNew(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleVnoNew != null) {
				saveProperty(board, "vehicleVnoNew", sn++, vehicleVnoNew);
			}
			// 运输路线
			Property transportRoutes = getTransportRoutes(contractDetailVary, contractDetailVary.getAbortStatus());
			if (transportRoutes != null) {
				saveProperty(board, "transportRoutes", sn++, transportRoutes);
			}
			// 关联车辆
			Property relationVehicleVin = getRelationVehicleVin(contractDetailVary, contractDetailVary.getAbortStatus());
			if (relationVehicleVin != null) {
				saveProperty(board, "relationVehicleVin", sn++, relationVehicleVin);
			}
			// 新VIN码
			Property vehicleVinNew = getVehicleVinNew(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleVinNew != null) {
				saveProperty(board, "vehicleVinNew", sn++, vehicleVinNew);
			}
			// 新合格证号
			Property vehicleEligibleNoNew = getVehicleEligibleNoNew(contractDetailVary,
					contractDetailVary.getAbortStatus());
			if (vehicleEligibleNoNew != null) {
				saveProperty(board, "vehicleEligibleNoNew", sn++, vehicleEligibleNoNew);
			}
			// 备注
			Property vehicleComment = getVehicleComment(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleComment != null) {
				saveProperty(board, "vehicleComment", sn++, vehicleComment);
			}
		} else if (contractDetailVary.getAbortStatus() == STATUS_ABORT) {
			// VIN
			Property vehicleVinField = getVehicleVin(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleVinField != null) {
				saveProperty(board, "vehicleVin", sn++, vehicleVinField);
			}

			// 终止原因
			Property abortComment = getAbortComment(contractDetailVary, contractDetailVary.getAbortStatus());
			if (abortComment != null) {
				saveProperty(board, "abortComment", sn++, abortComment);
			}

			// 已购买保险
			Property abortInsurance = getAbortInsurance(contractDetailVary, contractDetailVary.getAbortStatus());
			if (abortInsurance != null) {
				saveProperty(board, "abortInsurance", sn++, abortInsurance);
			}

			// 已出库精品
			Property abortPresent = getAbortPresent(contractDetailVary, contractDetailVary.getAbortStatus());
			if (abortPresent != null) {
				saveProperty(board, "abortPresent", sn++, abortPresent);
			}

			// 已改装项目
			Property abortItem = getAbortItem(contractDetailVary, contractDetailVary.getAbortStatus());
			if (abortItem != null) {
				saveProperty(board, "abortItem", sn++, abortItem);
			}

			// 已报销费用
			Property abortCharge = getAbortCharge(contractDetailVary, contractDetailVary.getAbortStatus());
			if (abortCharge != null) {
				saveProperty(board, "abortCharge", sn++, abortCharge);
			}

			// 车型
			Property vnoField = getVno(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vnoField != null) {
				saveProperty(board, "vno", sn++, vnoField);
			}
			// 颜色
			Property vehicleColorField = getVehicleColor(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleColorField != null) {
				saveProperty(board, "vehicleColor", sn++, vehicleColorField);
			}

			// 计划交车时间
			Property planDeliverTime = getPlanDeliverTime(contractDetailVary, contractDetailVary.getAbortStatus());
			if (planDeliverTime != null) {
				saveProperty(board, "planDeliverTime", sn++, planDeliverTime);
			}
			// 是否含保费
			Property isContainInsuranceCost = getisContainInsuranceCost(contractDetailVary,
					contractDetailVary.getAbortStatus());
			if (isContainInsuranceCost != null) {
				saveProperty(board, "isContainInsuranceCost", sn++, isContainInsuranceCost);
			}
			// 车辆总价
			Property vehiclePriceTotal = getVehiclePriceTotal(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehiclePriceTotal != null) {
				saveProperty(board, "vehiclePriceTotal", sn++, vehiclePriceTotal);
			}
			// 车辆单价
			Property vehiclePrice = getVehiclePrice(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehiclePrice != null) {
				saveProperty(board, "vehiclePrice", sn++, vehiclePrice);
			}

			// 优惠金额
			Property discountAmount = getDiscountAmount(contractDetailVary, contractDetailVary.getAbortStatus());
			if (discountAmount != null) {
				saveProperty(board, "discountAmount", sn++, discountAmount);
			}

			// 保险统计
			sn = createInsuranceSummary(board, sn, contractDetailVary);

			// 精品统计
			sn = createPresentSummary(board, sn, contractDetailVary);

			// 改装统计
			sn = createItemSummary(board, sn, contractDetailVary);

			// 费用统计
			sn = createChargeSummary(board, sn, contractDetailVary);

			
			// 单车利润
			sn = createProfitPf(board, sn,contractDetailVary);

			// 佣金
			Property vehicleProfit = getVehicleProfit(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleProfit != null) {
				saveProperty(board, "vehicleProfit", sn++, vehicleProfit);
			}
			// 佣金客户
			Property customerProfit = getCustomerProfit(contractDetailVary, contractDetailVary.getAbortStatus());
			if (customerProfit != null) {
				saveProperty(board, "customerProfit", sn++, customerProfit);
			}

			// 公司赠券
			Property largessAmount = getLargessAmount(contractDetailVary, contractDetailVary.getAbortStatus());
			if (largessAmount != null) {
				saveProperty(board, "largessAmount", sn++, largessAmount);
			}

			// 交车地点
			Property deliverAddress = getDeliverAddress(contractDetailVary, contractDetailVary.getAbortStatus());
			if (deliverAddress != null) {
				saveProperty(board, "deliverAddress", sn++, deliverAddress);
			}
			// 标的物
			Property subjectMatter = getSubjectMatter(contractDetailVary, contractDetailVary.getAbortStatus());
			if (subjectMatter != null) {
				saveProperty(board, "subjectMatter", sn++, subjectMatter);
			}
			// 车牌号码
			Property vehicleCardNo = getVehicleCardNo(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleCardNo != null) {
				saveProperty(board, "vehicleCardNo", sn++, vehicleCardNo);
			}
			// 上牌型号
			Property vehicleVnoNew = getVehicleVnoNew(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleVnoNew != null) {
				saveProperty(board, "vehicleVnoNew", sn++, vehicleVnoNew);
			}
			// 运输路线
			Property transportRoutes = getTransportRoutes(contractDetailVary, contractDetailVary.getAbortStatus());
			if (transportRoutes != null) {
				saveProperty(board, "transportRoutes", sn++, transportRoutes);
			}
			// 关联车辆
			Property relationVehicleVin = getRelationVehicleVin(contractDetailVary, contractDetailVary.getAbortStatus());
			if (relationVehicleVin != null) {
				saveProperty(board, "relationVehicleVin", sn++, relationVehicleVin);
			}
			// 新VIN码
			Property vehicleVinNew = getVehicleVinNew(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleVinNew != null) {
				saveProperty(board, "vehicleVinNew", sn++, vehicleVinNew);
			}
			// 新合格证号
			Property vehicleEligibleNoNew = getVehicleEligibleNoNew(contractDetailVary,
					contractDetailVary.getAbortStatus());
			if (vehicleEligibleNoNew != null) {
				saveProperty(board, "vehicleEligibleNoNew", sn++, vehicleEligibleNoNew);
			}
			// 备注
			Property vehicleComment = getVehicleComment(contractDetailVary, contractDetailVary.getAbortStatus());
			if (vehicleComment != null) {
				saveProperty(board, "vehicleComment", sn++, vehicleComment);
			}
		}

		createTail(board, sn++, false, true);
		// 保险
		MobileBoardBuffered insuranceBoard = saleContractInsuranceVaryBufferCacl.computeFields(contractDetailVary,
				title);
		if (insuranceBoard != null)
			createSubObjectField(board, "VehicleSaleContractInsuranceVarySubobject", AppBoardFieldType.Subobject, sn++,
					insuranceBoard);

		// 精品
		MobileBoardBuffered presentBoard = saleContractPresentVaryBufferCacl.computeFields(contractDetailVary, title);
		if (presentBoard != null)
			createSubObjectField(board, "VehicleSaleContractPresentVarySubobject", AppBoardFieldType.Subobject, sn++,
					presentBoard);

		// 改装
		MobileBoardBuffered itemBoard = saleContractItemVaryBufferCacl.computeFields(contractDetailVary, title);
		if (itemBoard != null)
			createSubObjectField(board, "VehicleSaleContractItemVarySubobject", AppBoardFieldType.Subobject, sn++,
					itemBoard);

		// 费用
		MobileBoardBuffered chargeBoard = saleContractChargeVaryBufferCacl.computeFields(contractDetailVary, title);
		if (chargeBoard != null)
			createSubObjectField(board, "VehicleSaleContractChargeVarySubobject", AppBoardFieldType.Subobject, sn++,
					chargeBoard);

		// 厂家赠品
		MobileBoardBuffered giftBoard = saleContractGiftVaryBufferCacl.computeFields(contractDetailVary, title);
		if (giftBoard != null)
			createSubObjectField(board, "VehicleSaleContractGiftsVarySubobject", AppBoardFieldType.Subobject, sn++,
					giftBoard);

		// 发票
		MobileBoardBuffered invoiceBoard = saleContractInvoiceVaryBufferCacl.computeFields(contractDetailVary, title);
		if (invoiceBoard != null)
			createSubObjectField(board, "VehicleInvoicesVaryListSubobject", AppBoardFieldType.Subobject, sn++,
					invoiceBoard);

		return board;
	}

	

	// 保险合计统计
	private short createInsuranceSummary(MobileBoardBuffered board, short sn,
			VehicleSaleContractDetailVary contractDetailVary) {
		Map<String, Double> summaryMap = saleContractInsuranceVaryBufferCacl.getInsuranceSummary(contractDetailVary);
		double income = summaryMap.get("income");
		double outcome = summaryMap.get("outcome");
		double oriIncome = summaryMap.get("oriIncome");
		double oriOutcome = summaryMap.get("oriOutcome");
		if (STATUS_NEW == contractDetailVary.getAbortStatus()) {
			if (income == 0.00D && outcome == 0.00D) {
				return sn;
			}
			// 保险收款
			createProperty(board, "insuranceIncome", sn++, true, true, true, "保险收款", Tools.toCurrencyStr(income), null,
					null, null, null, null);
			// 保险支出
			createProperty(board, "insuranceOutcome", sn++, null, true, null, "保险支出", Tools.toCurrencyStr(outcome),
					null, null, null, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);

		} else if (STATUS_ABORT == contractDetailVary.getAbortStatus()) {
			if (oriIncome != 0.00D || oriOutcome != 0.00D) {
				// 保险收款
				createProperty(board, "insuranceIncome", sn++, null, true, null, "保险收款",
						Tools.toCurrencyStr(oriIncome), null, null, null, null, null);
				// 保险支出
				createProperty(board, "insuranceOutcome", sn++, null, true, null, "保险支出",
						Tools.toCurrencyStr(oriOutcome), null, null, null, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
			}

			double realCost = summaryMap.get("realCost");
			if (realCost > 0) {
				// 已购买保险
				createProperty(board, "insuranceCost", sn++, true, true, true, "已购买保险", Tools.toCurrencyStr(realCost),
						null, null, Color.RED.getCode(), null, null);
			}
		} else if (STATUS_MODIFY == contractDetailVary.getAbortStatus()) {
			if (income == 0.00D && outcome == 0.00D && oriIncome == 0.00D && oriOutcome == 0.00D) {
				return sn;
			}
			Integer incomeColor = null;
			if (!compareEqual(income, oriIncome)) {
				incomeColor = Color.RED.getCode();
			}
			// 保险收款
			createProperty(board, "insuranceIncome", sn++, true, true, true, "保险收款", formatDiff(income, oriIncome),
					null, null, incomeColor, null, null);
			Integer outcomeColor = null;
			if (!compareEqual(outcome, oriOutcome)) {
				outcomeColor = Color.RED.getCode();
			}

			// 保险支出
			createProperty(board, "insuranceOutcome", sn++, null, true, null, "保险支出", formatDiff(outcome, oriOutcome),
					null, null, outcomeColor, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		}
		return sn;
	}

	// 改装统计
	private short createItemSummary(MobileBoardBuffered board, short sn,
			VehicleSaleContractDetailVary contractDetailVary) {
		Map<String, Double> summaryMap = saleContractItemVaryBufferCacl.getItemSummary(contractDetailVary);
		double income = summaryMap.get("income");
		double outcome = summaryMap.get("outcome");
		double oriIncome = summaryMap.get("oriIncome");
		double oriOutcome = summaryMap.get("oriOutcome");

		if (STATUS_NEW == contractDetailVary.getAbortStatus()) {
			if (income == 0.00D && outcome == 0.00D) {
				return sn;
			}
			// 改装价格
			createProperty(board, "itemIncome", sn++, true, true, true, "改装价格", Tools.toCurrencyStr(income), null,
					null, null, null, null);
			// 改装成本
			createProperty(board, "itemOutcome", sn++, null, true, null, "改装成本", Tools.toCurrencyStr(outcome), null,
					null, null, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);

		} else if (STATUS_ABORT == contractDetailVary.getAbortStatus()) {
			if (oriIncome != 0.00D || oriOutcome != 0.00D) {
				// 改装价格
				createProperty(board, "itemIncome", sn++, null, true, null, "改装价格", Tools.toCurrencyStr(oriIncome),
						null, null, null, null, null);
				// 改装成本
				createProperty(board, "itemOutcome", sn++, null, true, null, "改装成本", Tools.toCurrencyStr(oriOutcome),
						null, null, null, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
			}
			double realCost = summaryMap.get("realCost");
			if (realCost > 0) {
				// 已改装项目
				createProperty(board, "itemCost", sn++, true, true, true, "已改装项目", Tools.toCurrencyStr(realCost), null,
						null, Color.RED.getCode(), null, null);
			}

		} else if (STATUS_MODIFY == contractDetailVary.getAbortStatus()) {
			if (income == 0.00D && outcome == 0.00D && oriIncome == 0.00D && oriOutcome == 0.00D) {
				return sn;
			}
			Integer incomeColor = null;
			if (!compareEqual(income, oriIncome)) {
				incomeColor = Color.RED.getCode();
			}
			// 改装价格
			createProperty(board, "itemIncome", sn++, true, true, true, "改装价格", formatDiff(income, oriIncome), null,
					null, incomeColor, null, null);

			Integer outcomeColor = null;
			if (!compareEqual(outcome, oriOutcome)) {
				outcomeColor = Color.RED.getCode();
			}

			// 改装成本
			createProperty(board, "itemOutcome", sn++, null, true, null, "改装成本", formatDiff(outcome, oriOutcome), null,
					null, outcomeColor, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);

		}
		return sn;
	}

	// 精品统计
	private short createPresentSummary(MobileBoardBuffered board, short sn,
			VehicleSaleContractDetailVary contractDetailVary) {
		Map<String, Double> summaryMap = saleContractPresentVaryBufferCacl.getPresentSummary(contractDetailVary);
		double income = summaryMap.get("income");
		double outcome = summaryMap.get("outcome");
		double oriIncome = summaryMap.get("oriIncome");
		double oriOutcome = summaryMap.get("oriOutcome");

		if (STATUS_NEW == contractDetailVary.getAbortStatus()) {
			if (income == 0.00D && outcome == 0.00D) {
				return sn;
			}
			// 精品售价
			createProperty(board, "presentIncome", sn++, true, true, true, "精品售价", Tools.toCurrencyStr(income), null,
					null, null, null, null);
			// 精品成本
			createProperty(board, "presentOutcome", sn++, null, true, null, "精品成本", Tools.toCurrencyStr(outcome), null,
					null, null, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);

		} else if (STATUS_ABORT == contractDetailVary.getAbortStatus()) {

			if (oriIncome != 0.00D || oriOutcome != 0.00D) {

				// 精品售价
				createProperty(board, "presentIncome", sn++, null, true, null, "精品售价", Tools.toCurrencyStr(oriIncome),
						null, null, null, null, null);
				// 精品成本
				createProperty(board, "presentOutcome", sn++, null, true, null, "精品成本",
						Tools.toCurrencyStr(oriOutcome), null, null, null, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
			}

			double realCost = summaryMap.get("realCost");
			if (realCost > 0) {
				// 已安装精品
				createProperty(board, "presentCost", sn++, true, true, true, "已安装精品", Tools.toCurrencyStr(realCost),
						null, null, Color.RED.getCode(), null, null);
			}
		} else if (STATUS_MODIFY == contractDetailVary.getAbortStatus()) {
			if (income == 0.00D && outcome == 0.00D && oriIncome == 0.00D && oriOutcome == 0.00D) {
				return sn;
			}
			Integer incomeColor = null;
			if (!compareEqual(income, oriIncome)) {
				incomeColor = Color.RED.getCode();
			}

			// 精品售价
			createProperty(board, "presentIncome", sn++, true, true, true, "精品售价", formatDiff(income, oriIncome), null,
					null, incomeColor, null, null);

			Integer outcomeColor = null;
			if (!compareEqual(outcome, oriOutcome)) {
				outcomeColor = Color.RED.getCode();
			}
			// 精品成本
			createProperty(board, "presentOutcome", sn++, null, true, null, "精品成本", formatDiff(outcome, oriOutcome),
					null, null, outcomeColor, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);

		}
		return sn;
	}

	// 费用统计
	private short createChargeSummary(MobileBoardBuffered board, short sn,
			VehicleSaleContractDetailVary contractDetailVary) {
		Map<String, Double> summaryMap = saleContractChargeVaryBufferCacl.getChargeSummary(contractDetailVary);
		double income = summaryMap.get("income");
		double outcome = summaryMap.get("outcome");
		double oriIncome = summaryMap.get("oriIncome");
		double oriOutcome = summaryMap.get("oriOutcome");

		if (STATUS_NEW == contractDetailVary.getAbortStatus()) {
			if (income == 0.00D && outcome == 0.00D) {
				return sn;
			}
			// 费用收款
			createProperty(board, "chargeIncome", sn++, true, true, true, "费用收款", Tools.toCurrencyStr(income), null,
					null, null, null, null);
			// 费用支出
			createProperty(board, "chargeOutcome", sn++, null, true, null, "费用支出", Tools.toCurrencyStr(outcome), null,
					null, null, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);

		} else if (STATUS_ABORT == contractDetailVary.getAbortStatus()) {

			if (oriIncome != 0.00D || oriOutcome != 0.00D) {
				// 费用收款
				createProperty(board, "chargeIncome", sn++, null, true, null, "费用收款", Tools.toCurrencyStr(oriIncome),
						null, null, null, null, null);
				// 费用支出
				createProperty(board, "chargeOutcome", sn++, null, true, null, "费用支出", Tools.toCurrencyStr(oriOutcome),
						null, null, null, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
			}

			double realCost = summaryMap.get("realCost");
			if (realCost > 0) {
				// 已报销费用
				createProperty(board, "chargeCost", sn++, true, true, true, "已报销费用", Tools.toCurrencyStr(realCost),
						null, null, Color.RED.getCode(), null, null);
			}
		} else if (STATUS_MODIFY == contractDetailVary.getAbortStatus()) {
			if (income == 0.00D && outcome == 0.00D && oriIncome == 0.00D && oriOutcome == 0.00D) {
				return sn;
			}
			Integer incomeColor = null;
			if (!compareEqual(income, oriIncome)) {
				incomeColor = Color.RED.getCode();
			}

			// 费用收款
			createProperty(board, "chargeIncome", sn++, true, true, true, "费用收款", formatDiff(income, oriIncome), null,
					null, incomeColor, null, null);

			Integer outcomeColor = null;
			if (!compareEqual(outcome, oriOutcome)) {
				outcomeColor = Color.RED.getCode();
			}
			// 费用支出
			createProperty(board, "chargeOutcome", sn++, null, true, null, "费用支出", formatDiff(outcome, oriOutcome),
					null, null, outcomeColor, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);

		}
		return sn;
	}

	private String formatDiffString(String newValue, String oldValue) {
		if (StringUtils.isBlank(newValue)) {
			newValue = "无";
		}
		if (StringUtils.isBlank(oldValue)) {
			oldValue = "无";
		}
		if (StringUtils.equals(newValue, oldValue)) {
			return newValue;
		}
		return String.format("%s【%s】", newValue, oldValue);
	}

	// 车型
	private Property getVno(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property vno = new Property();
		vno.setLabel("车型");
		if (status == STATUS_NEW) {
			return null;
		} else if (status == STATUS_MODIFY) {
			// 未变更
			if (StringUtils.equals(contractDetailVary.getVehicleVno(), contractDetailVary.getOriVehicleVno())) {
				return null;
			}
			vno.setValue(formatDiffString(contractDetailVary.getVehicleVno(), contractDetailVary.getOriVehicleVno()));
			vno.setShownOnSlaveBoard(true);
			vno.setShownOnDetailBoard(true);
			vno.setValueColour(Color.RED.getCode());
			return vno;
		}
		return null;
	}

	// 颜色
	private Property getVehicleColor(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property vehicleColor = new Property();
		vehicleColor.setLabel("颜色");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			vehicleColor.setValue(contractDetailVary.getVehicleColor());
			vehicleColor.setValueColour(Color.BLACK.getCode());
			if (status == STATUS_NEW) {
				vehicleColor.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				vehicleColor.setShownOnDetailBoard(true);
			}
			return vehicleColor;
		} else if (status == STATUS_MODIFY) {
			boolean shownOnDetailBoard = false;
			boolean shownOnSlaveBoard = false;
			Integer valueColor = null;
			if (StringUtils.equals(contractDetailVary.getVehicleColor(), contractDetailVary.getOriVehicleColor())) {
				shownOnDetailBoard = true;
			} else {
				shownOnDetailBoard = true;
				shownOnSlaveBoard = true;
				valueColor = Color.RED.getCode();
			}
			vehicleColor.setValue(formatDiffString(contractDetailVary.getVehicleColor(),
					contractDetailVary.getOriVehicleColor()));
			vehicleColor.setShownOnDetailBoard(shownOnDetailBoard);
			vehicleColor.setShownOnSlaveBoard(shownOnSlaveBoard);
			vehicleColor.setValueColour(valueColor);
			return vehicleColor;
		}
		return null;
	}

	// VIN
	private Property getVehicleVin(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("VIN码");
		if (status == STATUS_NEW) {
			p.setValue(contractDetailVary.getVehicleVin());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			p.setValueColour(Color.BLACK.getCode());
			return p;
		} else if (status == STATUS_MODIFY) {
			Integer valueColor = null;
			if (!StringUtils.equals(contractDetailVary.getVehicleVin(), contractDetailVary.getOriVehicleVin())) {

				valueColor = Color.RED.getCode();
			}
			p.setValue(formatDiffString(contractDetailVary.getVehicleVin(), contractDetailVary.getOriVehicleVin()));
			p.setValueColour(valueColor);
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;
		} else if (status == STATUS_ABORT) {
			p.setValue(contractDetailVary.getVehicleVin());
			p.setShownOnSlaveBoard(true);
			p.setValueColour(Color.BLACK.getCode());
			return p;
		}
		return null;
	}

	// 计划交车时间
	private Property getPlanDeliverTime(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("计划交车日期");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			p.setValue(Tools.formatDate(contractDetailVary.getPlanDeliverTime()));
			p.setValueColour(Color.BLACK.getCode());
			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			// 时间通过字符串判断
			if (StringUtils.equals(Tools.formatDate(contractDetailVary.getPlanDeliverTime()),
					Tools.formatDate(contractDetailVary.getOriPlanDeliverTime()))) {
				p.setValue(Tools.formatDate(contractDetailVary.getPlanDeliverTime()));
				p.setValueColour(Color.BLACK.getCode());
			} else {
				p.setValue(String.format("%s【%s】", Tools.formatDate(contractDetailVary.getPlanDeliverTime()),
						Tools.formatDate(contractDetailVary.getOriPlanDeliverTime())));
				p.setValueColour(Color.RED.getCode());
			}
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;
		}
		return null;
	}

	// 是否含保费
	private Property getisContainInsuranceCost(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("车价含保费");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			p.setValue(Tools.toBooleanStr(contractDetailVary.getIsContainInsuranceCost()));

			p.setValueColour(Color.BLACK.getCode());
			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (Tools.toBoolean(contractDetailVary.getIsContainInsuranceCost()) == Tools.toBoolean(contractDetailVary
					.getOriIsContainInsuranceCost())) {
				p.setValue(Tools.toBooleanStr(contractDetailVary.getIsContainInsuranceCost()));
				p.setValueColour(Color.BLACK.getCode());
			} else {
				p.setValue(String.format("%s【%s】", Tools.toBooleanStr(contractDetailVary.getIsContainInsuranceCost()),
						Tools.toBooleanStr(contractDetailVary.getOriIsContainInsuranceCost())));
				p.setValueColour(Color.RED.getCode());
			}
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;
		}
		return null;
	}

	// 车辆总价
	private Property getVehiclePriceTotal(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("车辆总价");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			p.setValue(Tools.toCurrencyStr(contractDetailVary.getVehiclePriceTotal()));
			p.setValueColour(Color.BLACK.getCode());
			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (compareEqual(contractDetailVary.getVehiclePriceTotal(), contractDetailVary.getOriVehiclePriceTotal())) {
				p.setValue(Tools.toCurrencyStr(contractDetailVary.getVehiclePriceTotal()));
				p.setValueColour(Color.BLACK.getCode());
			} else {
				p.setValue(formatDiff(contractDetailVary.getVehiclePriceTotal(),
						contractDetailVary.getOriVehiclePriceTotal()));
				p.setValueColour(Color.RED.getCode());
			}

			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;

		}
		return null;
	}

	// 车辆单价
	private Property getVehiclePrice(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("车辆单价");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			p.setValue(Tools.toCurrencyStr(contractDetailVary.getVehiclePrice()));
			p.setValueColour(Color.BLACK.getCode());

			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (compareEqual(contractDetailVary.getVehiclePrice(), contractDetailVary.getOriVehiclePrice())) {
				p.setValue(Tools.toCurrencyStr(contractDetailVary.getVehiclePrice()));
				p.setValueColour(Color.BLACK.getCode());
			} else {
				p.setValue(formatDiff(contractDetailVary.getVehiclePrice(), contractDetailVary.getOriVehiclePrice()));
				p.setValueColour(Color.RED.getCode());
			}
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;
		}
		return null;
	}

	// 优惠金额
	private Property getDiscountAmount(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("优惠金额");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (Tools.toDouble(contractDetailVary.getDiscountAmount()) == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(contractDetailVary.getDiscountAmount()));
			p.setValueColour(Color.BLACK.getCode());

			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (compareEqual(contractDetailVary.getDiscountAmount(), contractDetailVary.getOriDiscountAmount())) {
				if (Tools.toDouble(contractDetailVary.getDiscountAmount()) == 0.00D) {
					return null;
				}
				p.setValue(Tools.toCurrencyStr(contractDetailVary.getDiscountAmount()));
				p.setValueColour(Color.BLACK.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else {
				p.setValue(formatDiff(contractDetailVary.getDiscountAmount(), contractDetailVary.getOriDiscountAmount()));
				p.setValueColour(Color.RED.getCode());

				// if (Tools.toDouble(contractDetailVary.getDiscountAmount()) ==
				// 0.00D) {
				// p.setShownOnDetailBoard(true);
				// } else {
				// p.setShownOnSlaveBoard(true);
				// p.setShownOnDetailBoard(true);
				// }

				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			}

			return p;

		}
		return null;
	}

	// 佣金
	private Property getVehicleProfit(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("佣金");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (Tools.toDouble(contractDetailVary.getVehicleProfit()) == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(contractDetailVary.getVehicleProfit()));
			p.setValueColour(Color.BLACK.getCode());

			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}

			return p;
		} else if (status == STATUS_MODIFY) {
			if (compareEqual(contractDetailVary.getVehicleProfit(), contractDetailVary.getOriVehicleProfit())) {
				if (Tools.toDouble(contractDetailVary.getVehicleProfit()) == 0.00D) {
					return null;
				}
				p.setValue(Tools.toCurrencyStr(contractDetailVary.getVehicleProfit()));
				p.setValueColour(Color.BLACK.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else {
				p.setValue(formatDiff(contractDetailVary.getVehicleProfit(), contractDetailVary.getOriVehicleProfit()));
				// p.setValue(String.format("%s【%s】",
				// contractDetailVary.getVehicleProfit(),
				// contractDetailVary.getOriVehicleProfit()));
				p.setValueColour(Color.RED.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			}
			return p;
		}

		return null;
	}

	// 佣金客户
	private Property getCustomerProfit(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("佣金客户");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (StringUtils.isEmpty(contractDetailVary.getCustomerIdProfit())) {
				return null;
			}
			p.setValue(contractDetailVary.getCustomerNameProfit());
			p.setValueColour(Color.BLACK.getCode());
			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (StringUtils.equals(contractDetailVary.getCustomerIdProfit(),
					contractDetailVary.getOriCustomerIdProfit())) {
				if (StringUtils.isEmpty(contractDetailVary.getCustomerIdProfit())) {
					return null;
				}
				p.setValue(contractDetailVary.getCustomerNameProfit());
				p.setValueColour(Color.BLACK.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else {
				if (StringUtils.isEmpty(contractDetailVary.getCustomerIdProfit())) {
					return null;
				} else {
					if (StringUtils.isEmpty(contractDetailVary.getOriCustomerIdProfit())) {
						p.setValue(String.format("%s", contractDetailVary.getCustomerNameProfit()));
					} else {
						p.setValue(String.format("%s【%s】", contractDetailVary.getCustomerNameProfit(),
								contractDetailVary.getOriCustomerNameProfit()));
					}
				}

				p.setValueColour(Color.RED.getCode());

				if (StringUtils.isEmpty(contractDetailVary.getCustomerIdProfit())) {
					p.setShownOnSlaveBoard(true);
				} else {
					p.setShownOnSlaveBoard(true);
					p.setShownOnDetailBoard(true);
				}
			}
			return p;

		}
		return null;
	}

	// 公司赠券
	private Property getLargessAmount(VehicleSaleContractDetailVary contractDetailVary, Short status) {
		Property p = new Property();
		p.setLabel("公司赠券");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (Tools.toDouble(contractDetailVary.getLargessAmount()) == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(contractDetailVary.getLargessAmount()));
			p.setValueColour(Color.BLACK.getCode());

			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (compareEqual(contractDetailVary.getLargessAmount(), contractDetailVary.getOriLargessAmount())) {
				if (Tools.toDouble(contractDetailVary.getLargessAmount()) == 0.00D) {
					return null;
				}
				p.setValue(Tools.toCurrencyStr(contractDetailVary.getLargessAmount()));
				p.setValueColour(Color.BLACK.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else {
				p.setValue(formatDiff(contractDetailVary.getLargessAmount(), contractDetailVary.getOriLargessAmount()));
				p.setValueColour(Color.RED.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			}
			return p;
		}

		return null;

	}

	// 单车利润
//	private Property getProfitPf(VehicleSaleContractDetailVary contractDetailVary, short status) {
//		VwVehicleSaleContractDetailVary vwontractDetailVary = dao.get(VwVehicleSaleContractDetailVary.class,
//				contractDetailVary.getDetailVaryId());
//		if (vwontractDetailVary == null) {
//			return null;
//		}
//		Property p = new Property();
//		p.setLabel("单车利润");
//		if (status == STATUS_NEW || status == STATUS_ABORT) {
//			p.setValue(Tools.toCurrencyStr(vwontractDetailVary.getProfitPf()));
//			p.setValueColour(Color.BLACK.getCode());
//
//			if (status == STATUS_NEW) {
//				p.setShownOnSlaveBoard(true);
//				p.setShownOnDetailBoard(true);
//			} else if (status == STATUS_ABORT) {
//				p.setShownOnDetailBoard(true);
//			}
//			return p;
//		} else if (status == STATUS_MODIFY) {
//			p.setValue(Tools.toCurrencyStr(vwontractDetailVary.getProfitPf()));
//			p.setValueColour(Color.BLACK.getCode());
//			p.setShownOnSlaveBoard(true);
//			p.setShownOnDetailBoard(true);
//			return p;
//		}
//		return null;
//	}
	
	// 单车利润
	private short createProfitPf(MobileBoardBuffered board, short sn, VehicleSaleContractDetailVary contractDetailVary) {
		String lable = "单车利润";
		String fieldCode ="profitPf";
		Boolean shownOnSlaveBoard = null;
		Boolean shownOnDetailBoard = null;
		Integer valueColour = null;
		String value = null;
		short status = Tools.toShort(contractDetailVary.getAbortStatus());
		
		VwVehicleSaleContractDetailVary vwontractDetailVary = dao.get(VwVehicleSaleContractDetailVary.class,contractDetailVary.getDetailVaryId());
		if (vwontractDetailVary == null) {
			return sn;
		}
		
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			value = Tools.toCurrencyStr(vwontractDetailVary.getProfitPf());
			if(status == STATUS_NEW){
				shownOnSlaveBoard = true;
				shownOnDetailBoard = true;
			}else if (status == STATUS_ABORT){
				shownOnDetailBoard = true;
			}
		}else if(status == STATUS_MODIFY){
			value = Tools.toCurrencyStr(vwontractDetailVary.getProfitPf());
			shownOnSlaveBoard = true;
			shownOnDetailBoard = true;
		}
		createProperty(board, fieldCode, sn++, null, shownOnDetailBoard, shownOnSlaveBoard, 
				lable, value,
				null, null, valueColour,  null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}

	// 交车地点
	private Property getDeliverAddress(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("交车地点");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (StringUtils.isEmpty(contractDetailVary.getDeliverAddress())) {
				return null;
			}
			p.setValue(contractDetailVary.getDeliverAddress());
			p.setValueColour(Color.BLACK.getCode());

			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (StringUtils.equals(contractDetailVary.getDeliverAddress(), contractDetailVary.getOriDeliverAddress())) {
				return null;
			} else {
				p.setValue(String.format("%s【%s】", contractDetailVary.getDeliverAddress(),
						contractDetailVary.getOriDeliverAddress()));
				p.setValueColour(Color.RED.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
				return p;
			}
		}
		return null;
	}

	// 标的物
	private Property getSubjectMatter(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("标的物");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (StringUtils.isEmpty(contractDetailVary.getSubjectMatter())) {
				return null;
			}
			p.setValue(contractDetailVary.getSubjectMatter());
			p.setValueColour(Color.BLACK.getCode());

			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (StringUtils.equals(contractDetailVary.getSubjectMatter(), contractDetailVary.getOriSubjectMatter())) {
				return null;
			} else {

				p.setValue(formatDiffString(contractDetailVary.getSubjectMatter(),
						contractDetailVary.getOriSubjectMatter()));
				p.setValueColour(Color.RED.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
				return p;
			}
		}
		return null;
	}

	// 车牌号码
	private Property getVehicleCardNo(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("车牌号码");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (StringUtils.isEmpty(contractDetailVary.getVehicleCardNo())) {
				return null;
			}
			p.setValue(contractDetailVary.getVehicleCardNo());
			p.setValueColour(Color.BLACK.getCode());

			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (StringUtils.equals(contractDetailVary.getVehicleCardNo(), contractDetailVary.getOriVehicleCardNo())) {
				return null;
			} else {
				p.setValue(formatDiffString(contractDetailVary.getVehicleCardNo(),
						contractDetailVary.getOriVehicleCardNo()));
				p.setValueColour(Color.RED.getCode());
				p.setShownOnDetailBoard(true);
				return p;
			}
		}
		return null;
	}

	// 上牌型号
	private Property getVehicleVnoNew(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("上牌型号");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (StringUtils.isEmpty(contractDetailVary.getVehicleVnoNew())) {
				return null;
			}
			p.setValue(contractDetailVary.getVehicleVnoNew());
			p.setValueColour(Color.BLACK.getCode());

			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (StringUtils.equals(contractDetailVary.getVehicleVnoNew(), contractDetailVary.getOriVehicleVnoNew())) {
				return null;
			} else {
				p.setValue(formatDiffString(contractDetailVary.getVehicleVnoNew(),
						contractDetailVary.getOriVehicleVnoNew()));
				p.setValueColour(Color.RED.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
				return p;
			}
		}
		return null;
	}

	// 运输路线
	private Property getTransportRoutes(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("运输路线");
		String transportRoutes = transportRoutes(contractDetailVary);
		String oriTransportRoutes = oriTransportRoutes(contractDetailVary);
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (StringUtils.isEmpty(transportRoutes)) {
				return null;
			}
			p.setValue(transportRoutes);
			p.setValueColour(Color.BLACK.getCode());
			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (StringUtils.equals(transportRoutes, oriTransportRoutes)) {
				return null;
			} else {
				p.setValue(formatDiffString(transportRoutes, oriTransportRoutes));
				p.setValueColour(Color.RED.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
				return p;
			}
		}
		return null;
	}

	private String transportRoutes(VehicleSaleContractDetailVary contractDetailVary) {
		String transportRoutes = "";
		String waysPoint = StringUtils.isEmpty(contractDetailVary.getWaysPoint()) ? "" : contractDetailVary
				.getWaysPoint();
		String startPoint = StringUtils.isEmpty(contractDetailVary.getStartPoint()) ? "" : contractDetailVary
				.getStartPoint();
		String endPoint = StringUtils.isEmpty(contractDetailVary.getEndPoint()) ? "" : contractDetailVary.getEndPoint();
		if (StringUtils.isEmpty(waysPoint) && StringUtils.isEmpty(startPoint) && StringUtils.isEmpty(endPoint)) {
			return null;
		}
		if (StringUtils.isEmpty(waysPoint)) {
			transportRoutes = startPoint + "," + endPoint;
		} else {
			transportRoutes = startPoint + "," + waysPoint + "," + endPoint;
		}
		return transportRoutes;
	}

	private String oriTransportRoutes(VehicleSaleContractDetailVary contractDetailVary) {
		String transportRoutes = "";
		String waysPoint = StringUtils.isEmpty(contractDetailVary.getOriWaysPoint()) ? "" : contractDetailVary
				.getOriWaysPoint();
		String startPoint = StringUtils.isEmpty(contractDetailVary.getOriStartPoint()) ? "" : contractDetailVary
				.getOriStartPoint();
		String endPoint = StringUtils.isEmpty(contractDetailVary.getOriEndPoint()) ? "" : contractDetailVary
				.getOriEndPoint();
		if (StringUtils.isEmpty(waysPoint) && StringUtils.isEmpty(startPoint) && StringUtils.isEmpty(endPoint)) {
			return null;
		}
		if (StringUtils.isEmpty(waysPoint)) {
			transportRoutes = startPoint + "," + endPoint;
		} else {
			transportRoutes = startPoint + "," + waysPoint + "," + endPoint;
		}
		return transportRoutes;
	}

	// 关联车辆
	private Property getRelationVehicleVin(VehicleSaleContractDetailVary contractDetailVary, Short status) {
		Property p = new Property();
		p.setLabel("关联车辆");
		VwVehicleSaleContractDetailVary vwContractDetailVary = dao.get(VwVehicleSaleContractDetailVary.class,
				contractDetailVary.getDetailVaryId());
		if (vwContractDetailVary == null) {
			return null;
		}

		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (StringUtils.isEmpty(vwContractDetailVary.getRelationVehicleVin())) {
				return null;
			}
			p.setValue(vwContractDetailVary.getRelationVehicleVin());
			p.setValueColour(Color.BLACK.getCode());

			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (StringUtils.equals(vwContractDetailVary.getRelationVehicleVin(),
					vwContractDetailVary.getOriRelationVehicleVin())) {
				return null;
			} else {
				p.setValue(formatDiffString(vwContractDetailVary.getRelationVehicleVin(),
						vwContractDetailVary.getOriRelationVehicleVin()));
				p.setValueColour(Color.RED.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
				return p;
			}
		}
		return null;
	}

	// 新VIN码
	private Property getVehicleVinNew(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("新VIN码");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (StringUtils.isEmpty(contractDetailVary.getVehicleVinNew())) {
				return null;
			}
			p.setValue(contractDetailVary.getVehicleVinNew());
			p.setValueColour(Color.BLACK.getCode());
			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (StringUtils.equals(contractDetailVary.getVehicleVinNew(), contractDetailVary.getOriVehicleVinNew())) {
				return null;
			} else {
				p.setValue(formatDiffString(contractDetailVary.getVehicleVinNew(),
						contractDetailVary.getOriVehicleVinNew()));
				p.setValueColour(Color.RED.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
				return p;
			}
		}
		return null;
	}

	// 新合格证号
	private Property getVehicleEligibleNoNew(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("新合格证号");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (StringUtils.isEmpty(contractDetailVary.getVehicleEligibleNoNew())) {
				return null;
			}
			p.setValue(contractDetailVary.getVehicleEligibleNoNew());
			p.setValueColour(Color.BLACK.getCode());
			if (status == STATUS_NEW) {
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (StringUtils.equals(contractDetailVary.getVehicleEligibleNoNew(),
					contractDetailVary.getOriVehicleEligibleNoNew())) {
				return null;
			} else {
				p.setValue(formatDiffString(contractDetailVary.getVehicleEligibleNoNew(),
						contractDetailVary.getOriVehicleEligibleNoNew()));
				p.setValueColour(Color.RED.getCode());
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
				return p;
			}
		}
		return null;
	}

	// 备注
	private Property getVehicleComment(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("备注");
		if (status == STATUS_NEW || status == STATUS_ABORT) {
			if (StringUtils.isEmpty(contractDetailVary.getVehicleComment())) {
				return null;
			}
			p.setValue(contractDetailVary.getVehicleComment());
			p.setValueColour(Color.BLACK.getCode());
			if (status == STATUS_NEW) {
				p.setShownOnSlaveBoard(true);
				p.setShownOnDetailBoard(true);
			} else if (status == STATUS_ABORT) {
				p.setShownOnDetailBoard(true);
			}
			return p;
		} else if (status == STATUS_MODIFY) {
			if (StringUtils.isEmpty(contractDetailVary.getVehicleComment())) {
				return null;
			}
			p.setValue(contractDetailVary.getVehicleComment());
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;
		}
		return null;

	}

	// 保险收款
	@SuppressWarnings("unchecked")
	private Property getInsuranceIncome(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("保险收款");
		if (status == STATUS_NEW) {
			double sumCategoryIncome = 0.00D;// 保险收款合计
			String hql = "select  sum(isnull(categoryIncome,0)) from VehicleSaleContractInsuranceVary  where detailVaryId = ?";
			List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (sumIncomeList != null && sumIncomeList.size() > 0) {
				sumCategoryIncome = Tools.toDouble(sumIncomeList.get(0));
			}

			p.setValue(Tools.toCurrencyStr(sumCategoryIncome));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;
		} else if (status == STATUS_MODIFY) {
			double sumCategoryIncome = 0.00D;// 保险收款合计
			String hql = "select  sum(isnull(categoryIncome,0)) from VehicleSaleContractInsuranceVary  where detailVaryId = ?";
			List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (sumIncomeList != null && sumIncomeList.size() > 0) {
				sumCategoryIncome = Tools.toDouble(sumIncomeList.get(0));
			}

			p.setValue(Tools.toCurrencyStr(sumCategoryIncome));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;
		}

		return null;
	}

	// 保险支出
	@SuppressWarnings("unchecked")
	private Property getInsuranceOutcome(VehicleSaleContractDetailVary contractDetailVary, short status) {

		Property p = new Property();
		p.setLabel("保险支出");
		if (status == STATUS_NEW) {
			double sumCategoryOutcome = 0.00D;// 保险支出
			String hql = "select  sum(isnull(categoryIncome,0)*isnull(categoryScale,0)) from VehicleSaleContractInsuranceVary  where detailVaryId = ?";
			List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (sumIncomeList != null && sumIncomeList.size() > 0) {
				sumCategoryOutcome = Tools.toDouble(sumIncomeList.get(0));
			}
			if (sumCategoryOutcome == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(sumCategoryOutcome));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;
		} else if (status == STATUS_MODIFY) {
			double sumCategoryOutcome = 0.00D;// 保险支出
			String hql = "select  sum(isnull(categoryIncome,0)*isnull(categoryScale,0)) from VehicleSaleContractInsuranceVary  where detailVaryId = ?";
			List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (sumIncomeList != null && sumIncomeList.size() > 0) {
				sumCategoryOutcome = Tools.toDouble(sumIncomeList.get(0));
			}
			if (sumCategoryOutcome == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(sumCategoryOutcome));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			;
			return p;
		}
		return null;
	}

	// 改装价格
	@SuppressWarnings("unchecked")
	private Property getItemIncome(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("改装价格");
		if (status == STATUS_NEW) {
			double income = 0.00D;
			String hql = "select sum(isnull(income,0)) from VehicleSaleContractItemVary where detailVaryId = ? ";
			List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (sumIncomeList != null && sumIncomeList.size() > 0) {
				income = Tools.toDouble(sumIncomeList.get(0));
			}
			if (income == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(income));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;

		} else if (status == STATUS_MODIFY) {
			double income = 0.00D;
			String hql = "select sum(isnull(income,0)) from VehicleSaleContractItemVary where detailVaryId = ? ";
			List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (sumIncomeList != null && sumIncomeList.size() > 0) {
				income = Tools.toDouble(sumIncomeList.get(0));
			}
			if (income == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(income));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;
		}
		return null;
	}

	// 改装成本
	@SuppressWarnings("unchecked")
	private Property getItemCost(VehicleSaleContractDetailVary contractDetailVary, short status) {

		Property p = new Property();
		p.setLabel("改装成本");
		if (status == STATUS_NEW) {
			double cost = 0.00D;
			String hql = "select sum(isnull(itemCost,0)) from VehicleSaleContractItemVary where detailVaryId = ? ";
			List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (list != null && list.size() > 0) {
				cost = Tools.toDouble(list.get(0));
			}
			if (cost == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(cost));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;

		} else if (status == STATUS_MODIFY) {
			double cost = 0.00D;
			String hql = "select sum(isnull(itemCost,0)) from VehicleSaleContractItemVary where detailVaryId = ? ";
			List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (list != null && list.size() > 0) {
				cost = Tools.toDouble(list.get(0));
			}
			if (cost == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(cost));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;

		}
		return null;
	}

	// 精品售价
	@SuppressWarnings("unchecked")
	private Property getPresentIncome(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("精品售价");
		if (status == STATUS_NEW) {
			double income = 0.00D;
			String hql = "select sum(isnull(income,0)) from VehicleSaleContractPresentVary where detailVaryId = ? ";
			List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (list != null && list.size() > 0) {
				income = Tools.toDouble(list.get(0));
			}
			if (income == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(income));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;

		} else if (status == STATUS_MODIFY) {
			double income = 0.00D;
			String hql = "select sum(isnull(income,0)) from VehicleSaleContractPresentVary where detailVaryId = ? ";
			List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (list != null && list.size() > 0) {
				income = Tools.toDouble(list.get(0));
			}
			if (income == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(income));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;
		}

		return null;
	}

	// 精品成本
	@SuppressWarnings("unchecked")
	private Property getPresentCost(VehicleSaleContractDetailVary contractDetailVary, short status) {

		Property p = new Property();
		p.setLabel("精品成本");
		if (status == STATUS_NEW) {
			double cost = 0.00D;
			String hql = "select sum(isnull(costRecord,0)) from VehicleSaleContractPresentVary where detailVaryId = ? ";
			List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (list != null && list.size() > 0) {
				cost = Tools.toDouble(list.get(0));
			}
			if (cost == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(cost));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;

		} else if (status == STATUS_MODIFY) {

			double cost = 0.00D;
			String hql = "select sum(isnull(costRecord,0)) from VehicleSaleContractPresentVary where detailVaryId = ? ";
			List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (list != null && list.size() > 0) {
				cost = Tools.toDouble(list.get(0));
			}
			if (cost == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(cost));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;
		}
		return null;
	}

	// 费用收款
	private Property getChargeIncome(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("费用收款");
		if (status == STATUS_NEW || status == STATUS_MODIFY) {
			double income = 0.00D;
			String hql = "select sum(isnull(income,0)) from VehicleSaleContractChargeVary where detailVaryId = ? ";
			List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (list != null && list.size() > 0) {
				income = Tools.toDouble(list.get(0));
			}
			if (income == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(income));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;

		}

		return null;
	}

	// 费用支出
	private Property getChargeCost(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("费用支出");
		if (status == STATUS_NEW || status == STATUS_MODIFY) {
			double cost = 0.00D;
			String hql = "select sum(isnull(chargeCost,0)) from VehicleSaleContractChargeVary where detailVaryId = ? ";
			List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (list != null && list.size() > 0) {
				cost = Tools.toDouble(list.get(0));
			}
			if (cost == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(cost));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setShownOnDetailBoard(true);
			return p;

		}
		return null;
	}

	// 终止原因
	private Property getAbortComment(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("终止原因");
		if (status == STATUS_ABORT) {
			p.setValue(contractDetailVary.getAbortComment());
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			return p;
		}
		return null;
	}

	// 已购买保险
	@SuppressWarnings("unchecked")
	private Property getAbortInsurance(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("已购买保险");
		if (status == STATUS_ABORT) {
			double sumCategoryOutcome = 0.00D;// 保险支出
			String hql = "select  sum(isnull(categoryIncome,0)*isnull(categoryScale,0)) from VehicleSaleContractInsuranceVary  where detailVaryId = ?";
			List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (sumIncomeList != null && sumIncomeList.size() > 0) {
				sumCategoryOutcome = Tools.toDouble(sumIncomeList.get(0));
			}
			if (sumCategoryOutcome == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(sumCategoryOutcome));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);

			p.setLabelBackground(Color.RED.getCode());// 前景红色
			return p;
		}
		return null;

	}

	// 已出库精品
	@SuppressWarnings("unchecked")
	private Property getAbortPresent(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("已出库精品");
		if (status == STATUS_ABORT) {
			double cost = 0.00D;
			String hql = "select sum(isnull(costRecord,0)) from VehicleSaleContractPresentVary where detailVaryId = ? ";
			List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (list != null && list.size() > 0) {
				cost = Tools.toDouble(list.get(0));
			}
			if (cost == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(cost));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setLabelBackground(Color.RED.getCode());// 前景红色
			return p;
		}
		return null;
	}

	// 已改装项目
	@SuppressWarnings("unchecked")
	private Property getAbortItem(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("已改装项目");
		if (status == STATUS_ABORT) {
			double cost = 0.00D;
			String hql = "select sum(isnull(itemCost,0)) from VehicleSaleContractItemVary where detailVaryId = ? ";
			List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (list != null && list.size() > 0) {
				cost = Tools.toDouble(list.get(0));
			}
			if (cost == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(cost));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setLabelBackground(Color.RED.getCode());// 前景红色
			return p;

		}
		return null;
	}

	// 已报销费用
	@SuppressWarnings("unchecked")
	private Property getAbortCharge(VehicleSaleContractDetailVary contractDetailVary, short status) {
		Property p = new Property();
		p.setLabel("已报销费用");
		if (status == STATUS_ABORT) {
			double income = 0.00D;
			String hql = "select sum(isnull(income,0)) from VehicleSaleContractChargeVary where detailVaryId = ? ";
			List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
			if (list != null && list.size() > 0) {
				income = Tools.toDouble(list.get(0));
			}
			if (income == 0.00D) {
				return null;
			}
			p.setValue(Tools.toCurrencyStr(income));
			p.setValueColour(Color.BLACK.getCode());
			p.setShownOnSlaveBoard(true);
			p.setLabelBackground(Color.RED.getCode());// 前景红色
			return p;
		}
		return null;
	}

}
