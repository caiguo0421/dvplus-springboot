package cn.sf_soft.office.approval.documentBuffered;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.gson.GsonExclutionStrategy;
import cn.sf_soft.common.gson.TimestampTypeAdapter;
import cn.sf_soft.common.util.BooleanTypeAdapter;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferCalc;
import cn.sf_soft.mobile.documentBuffer.model.AppBoardFieldType;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardField;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentBuffered;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.model.VwVehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VwVehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Field;
import cn.sf_soft.office.approval.ui.model.Label;
import cn.sf_soft.office.approval.ui.model.Property;

/**
 * 车辆合同-车辆明细
 * 
 * @author caigx
 *
 */
@Service("saleContractDetailBufferCacl")
public class SaleContractDetailBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractDetailBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractDetailBufferCacl.class.getSimpleName();

	private static String moduleId = "102025";

	// 10 终止 20 新增 30 修改
	private static final Short status_NEW = 20;
	private static final Short status_MODIFY = 30;
	private static final Short status_ABORT = 10;

	@Autowired
	private SaleContractInsuranceBufferCacl saleContractInsuranceBufferCacl;

	@Autowired
	private SaleContractPresentBufferCacl saleContractPresentBufferCacl;

	@Autowired
	private SaleContractItemBufferCacl saleContractItemBufferCacl;

	@Autowired
	private SaleContractChargeBufferCacl saleContractChargeBufferCacl;

	@Autowired
	private SaleContractGiftBufferCacl saleContractGiftBufferCacl;

	@Autowired
	private SaleContractInvoiceBufferCacl saleContractInvoiceBufferCacl;

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

	public MobileBoardBuffered computeFields(List<VehicleSaleContractDetail> contractDetails, int n) {
		MobileBoardBuffered board = new MobileBoardBuffered();

		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle("未变更车辆");
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(false);

		BoardTitle salveBoardTitle = new BoardTitle();
		salveBoardTitle.setTitle("未变更车辆");
		salveBoardTitle.setCollapsable(true);
		salveBoardTitle.setDefaultExpanded(true);
		
		createBoardTitle(board, gson.toJson(baseBoardTitle), null, gson.toJson(salveBoardTitle), null);

		DocTitle docTitle = new DocTitle("未变更车辆");
		board.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		for (int i = 0; i < contractDetails.size(); i++) {
			VehicleSaleContractDetail detail = contractDetails.get(i);
			String vehicleTitle = String.format("%d、%s", n++, detail.getVehicleVno());
			MobileBoardBuffered lableBoard = getContractDetail(detail, vehicleTitle);

			String label = vehicleTitle;
			createLable(board, sn++, true, true, true, label, null, null, null, null, lableBoard);
		}

		dao.save(board);
		return board;
	}

	/**
	 * 显示明细
	 * 
	 * @param detail
	 * @return
	 */
	private MobileBoardBuffered getContractDetail(VehicleSaleContractDetail detail, String vehicleTitle) {
		MobileBoardBuffered board = new MobileBoardBuffered();

		DocTitle docTitle = new DocTitle("车辆信息");
		board.setDocTitle(gson.toJson(docTitle));

		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(vehicleTitle);
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(true);

		BoardTitle salveBoardTitle = new BoardTitle();
		salveBoardTitle.setTitle(vehicleTitle);
		salveBoardTitle.setCollapsable(true);
		salveBoardTitle.setDefaultExpanded(true);

		createBoardTitle(board, gson.toJson(baseBoardTitle), null, gson.toJson(salveBoardTitle), null);

		short sn = 1;
		// 车型
		Property vnoField = getVno(detail, detail.getAbortStatus());
		if (vnoField != null) {
			saveProperty(board, "vno", sn++, vnoField);
		}
		// 颜色
		Property vehicleColorField = getVehicleColor(detail, detail.getAbortStatus());
		if (vehicleColorField != null) {
			saveProperty(board, "vehicleColor", sn++, vehicleColorField);
		}

		// VIN
		Property vehicleVinField = getVehicleVin(detail, detail.getAbortStatus());
		if (vehicleVinField != null) {
			saveProperty(board, "vehicleVin", sn++, vehicleVinField);
		}
		// 计划交车时间
		Property planDeliverTime = getPlanDeliverTime(detail, detail.getAbortStatus());
		if (planDeliverTime != null) {
			saveProperty(board, "planDeliverTime", sn++, planDeliverTime);
		}
		// 是否含保费
		Property isContainInsuranceCost = getisContainInsuranceCost(detail, detail.getAbortStatus());
		if (isContainInsuranceCost != null) {
			saveProperty(board, "isContainInsuranceCost", sn++, isContainInsuranceCost);
		}
		// 车辆总价
		Property vehiclePriceTotal = getVehiclePriceTotal(detail, detail.getAbortStatus());
		if (vehiclePriceTotal != null) {
			saveProperty(board, "vehiclePriceTotal", sn++, vehiclePriceTotal);
		}
		// 车辆单价
		Property vehiclePrice = getVehiclePrice(detail, detail.getAbortStatus());
		if (vehiclePrice != null) {
			saveProperty(board, "vehiclePrice", sn++, vehiclePrice);
		}

		// 优惠金额
		Property discountAmount = getDiscountAmount(detail, detail.getAbortStatus());
		if (discountAmount != null) {
			saveProperty(board, "discountAmount", sn++, discountAmount);
		}

		// 佣金
		Property vehicleProfit = getVehicleProfit(detail, detail.getAbortStatus());
		if (vehicleProfit != null) {
			saveProperty(board, "vehicleProfit", sn++, vehicleProfit);
		}
		// 佣金客户
		Property customerProfit = getCustomerProfit(detail, detail.getAbortStatus());
		if (customerProfit != null) {
			saveProperty(board, "customerProfit", sn++, customerProfit);
		}

		// 公司赠券
		Property largessAmount = getLargessAmount(detail, detail.getAbortStatus());
		if (largessAmount != null) {
			saveProperty(board, "largessAmount", sn++, largessAmount);
		}
		
		// 保险统计
		sn = createInsuranceSummary(board, sn, detail);
		// 精品统计
		sn = createPresentSummary(board, sn, detail);

		// 改装统计
		sn = createItemSummary(board, sn, detail);

		// 费用统计
		sn = createChargeSummary(board, sn, detail);

		// 单车利润
		sn = createProfitPf(board, sn,detail);

		// 交车地点
		Property deliverAddress = getDeliverAddress(detail, detail.getAbortStatus());
		if (deliverAddress != null) {
			saveProperty(board, "deliverAddress", sn++, deliverAddress);
		}
		// 标的物
		Property subjectMatter = getSubjectMatter(detail, detail.getAbortStatus());
		if (subjectMatter != null) {
			saveProperty(board, "subjectMatter", sn++, subjectMatter);
		}

		// 车牌号码
		Property vehicleCardNo = getVehicleCardNo(detail, detail.getAbortStatus());
		if (vehicleCardNo != null) {
			saveProperty(board, "vehicleCardNo", sn++, vehicleCardNo);
		}
		// 上牌型号
		Property vehicleVnoNew = getVehicleVnoNew(detail, detail.getAbortStatus());
		if (vehicleVnoNew != null) {
			saveProperty(board, "vehicleVnoNew", sn++, vehicleVnoNew);
		}
		// 运输路线
		Property transportRoutes = getTransportRoutes(detail, detail.getAbortStatus());
		if (transportRoutes != null) {
			saveProperty(board, "transportRoutes", sn++, transportRoutes);
		}

		// 关联车辆
		Property relationVehicleVin = getRelationVehicleVin(detail, detail.getAbortStatus());
		if (relationVehicleVin != null) {
			saveProperty(board, "relationVehicleVin", sn++, relationVehicleVin);
		}

		// 新VIN码
		Property vehicleVinNew = getVehicleVinNew(detail, detail.getAbortStatus());
		if (vehicleVinNew != null) {
			saveProperty(board, "vehicleVinNew", sn++, vehicleVinNew);
		}
		// 新合格证号
		Property vehicleEligibleNoNew = getVehicleEligibleNoNew(detail, detail.getAbortStatus());
		if (vehicleEligibleNoNew != null) {
			saveProperty(board, "vehicleEligibleNoNew", sn++, vehicleEligibleNoNew);
		}

		// 备注
		Property vehicleComment = getVehicleComment(detail, detail.getAbortStatus());
		if (vehicleComment != null) {
			saveProperty(board, "vehicleComment", sn++, vehicleComment);
		}
		createTail(board, sn++, false, true);

		// 保险
		MobileBoardBuffered insuranceBoard = saleContractInsuranceBufferCacl.computeFields(detail, vehicleTitle);
		if (insuranceBoard != null) {
			createSubObjectField(board, "VehicleSaleContractInsuranceSubobject", AppBoardFieldType.Subobject, sn++,
					insuranceBoard);
		}

		// 精品
		MobileBoardBuffered presentBoard = saleContractPresentBufferCacl.computeFields(detail, vehicleTitle);
		if (presentBoard != null) {
			createSubObjectField(board, "VehicleSaleContractPresentSubobject", AppBoardFieldType.Subobject, sn++,
					presentBoard);
		}

		// 改装
		MobileBoardBuffered itemBoard = saleContractItemBufferCacl.computeFields(detail, vehicleTitle);
		if (itemBoard != null) {
			createSubObjectField(board, "VehicleSaleContractItemSubobject", AppBoardFieldType.Subobject, sn++,
					itemBoard);
		}

		// 费用
		MobileBoardBuffered chargeBoard = saleContractChargeBufferCacl.computeFields(detail, vehicleTitle);
		if (chargeBoard != null) {
			createSubObjectField(board, "VehicleSaleContractChargeSubobject", AppBoardFieldType.Subobject, sn++,
					chargeBoard);
		}

		// 厂家赠品
		MobileBoardBuffered giftBoard = saleContractGiftBufferCacl.computeFields(detail, vehicleTitle);
		if (giftBoard != null) {
			createSubObjectField(board, "VehicleSaleContractGiftsSubobject", AppBoardFieldType.Subobject, sn++,
					giftBoard);
		}

		// 发票
		MobileBoardBuffered invoiceBoard = saleContractInvoiceBufferCacl.computeFields(detail, vehicleTitle);
		if (invoiceBoard != null) {
			createSubObjectField(board, "VehicleInvoicesVaryListSubobject", AppBoardFieldType.Subobject, sn++,
					invoiceBoard);
		}

		return board;
	}

	// 车牌号码
	private Property getVehicleCardNo(VehicleSaleContractDetail detail, Short abortStatus) {
		Property p = new Property();
		p.setLabel("车牌号码");
		if (StringUtils.isEmpty(detail.getVehicleCardNo())) {
			return null;
		}
		p.setValue(detail.getVehicleCardNo());
		// p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	// 上牌型号
	private Property getVehicleVnoNew(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("上牌型号");

		if (StringUtils.isEmpty(detail.getVehicleVnoNew())) {
			return null;
		}
		p.setValue(detail.getVehicleVnoNew());
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnSlaveBoard(true);
		p.setShownOnDetailBoard(true);
		return p;

	}

	// 关联车辆
	private Property getRelationVehicleVin(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("关联车辆");
		VwVehicleSaleContractDetail vwDetail = dao.get(VwVehicleSaleContractDetail.class, detail.getContractDetailId());
		if (vwDetail == null || StringUtils.isEmpty(vwDetail.getRelationVehicleVin())) {
			return null;
		}
		p.setValue(vwDetail.getRelationVehicleVin());
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnSlaveBoard(true);
		p.setShownOnDetailBoard(true);
		return p;
	}

	// 运输路线
	private Property getTransportRoutes(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("运输路线");

		String transportRoutes = transportRoutes(detail);
		if (StringUtils.isEmpty(transportRoutes)) {
			return null;
		}
		p.setValue(transportRoutes);
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnSlaveBoard(true);
		p.setShownOnDetailBoard(true);
		return p;

	}

	private String transportRoutes(VehicleSaleContractDetail contractDetailVary) {
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

	// 新VIN码
	private Property getVehicleVinNew(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("新VIN码");

		if (StringUtils.isEmpty(detail.getVehicleVinNew())) {
			return null;
		}
		p.setValue(detail.getVehicleVinNew());
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnSlaveBoard(true);
		p.setShownOnDetailBoard(true);
		return p;

	}

	// 新合格证号
	private Property getVehicleEligibleNoNew(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("新合格证号");

		if (StringUtils.isEmpty(detail.getVehicleEligibleNoNew())) {
			return null;
		}
		p.setValue(detail.getVehicleEligibleNoNew());
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnDetailBoard(true);
		return p;
	}

	private short createChargeSummary(MobileBoardBuffered board, short sn, VehicleSaleContractDetail detail) {
		double income = saleContractChargeBufferCacl.getChargeIncome(detail);
		double outcome = saleContractChargeBufferCacl.getChargeCost(detail);
		if (income == 0.00D && outcome == 0.00D) {
			return sn;
		}
		// 费用收款
		createProperty(board, "chargeIncome", sn++, true, true, true, "费用收款", Tools.toCurrencyStr(income), null, null,
				null, null, null);
		// 费用支出
		createProperty(board, "chargeOutcome", sn++, true, true, true, "费用支出", Tools.toCurrencyStr(outcome), null,
				null, null, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}

	private short createPresentSummary(MobileBoardBuffered board, short sn, VehicleSaleContractDetail detail) {
		double income = saleContractPresentBufferCacl.getPresentIncome(detail);
		double outcome = saleContractPresentBufferCacl.getPresentCost(detail);
		if (income == 0.00D && outcome == 0.00D) {
			return sn;
		}
		// 精品售价
		createProperty(board, "presentIncome", sn++, true, true, true, "精品售价", Tools.toCurrencyStr(income), null, null,
				null, null, null);
		// 精品成本
		createProperty(board, "presentOutcome", sn++, true, true, true, "精品成本", Tools.toCurrencyStr(outcome), null,
				null, null, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);

		return sn;
	}

	private short createItemSummary(MobileBoardBuffered board, short sn, VehicleSaleContractDetail detail) {
		double income = saleContractItemBufferCacl.getItemIncome(detail);
		double outcome = saleContractItemBufferCacl.getItemCost(detail);
		if (income == 0.00D && outcome == 0.00D) {
			return sn;
		}
		// 改装价格
		createProperty(board, "itemIncome", sn++, true, true, true, "改装价格", Tools.toCurrencyStr(income), null, null,
				null, null, null);
		// 改装成本
		createProperty(board, "itemOutcome", sn++, true, true, true, "改装成本", Tools.toCurrencyStr(outcome), null, null,
				null, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}

	private short createInsuranceSummary(MobileBoardBuffered board, short sn, VehicleSaleContractDetail detail) {
		double income = saleContractInsuranceBufferCacl.getInsuranceIncome(detail);
		double outcome = saleContractInsuranceBufferCacl.getInsuranceOutcome(detail);
		if (income == 0.00D && outcome == 0.00D) {
			return sn;
		}
		// 保险收款
		createProperty(board, "insuranceIncome", sn++, true, true, true, "保险收款", Tools.toCurrencyStr(income), null,
				null, null, null, null);
		// 保险支出
		createProperty(board, "insuranceOutcome", sn++, true, true, true, "保险支出", Tools.toCurrencyStr(outcome), null,
				null, null, null, null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}

	// 车型
	private Property getVno(VehicleSaleContractDetail detail, Short status) {
		Property vno = new Property();
		vno.setLabel("车型");
		if (StringUtils.isEmpty(detail.getVehicleVno())) {
			return null;
		}
		vno.setValue(detail.getVehicleVno());
		vno.setShownOnBaseBoard(true);
		vno.setShownOnDetailBoard(true);
		vno.setValueColour(Color.BLACK.getCode());
		return vno;
	}

	// 颜色
	private Property getVehicleColor(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("颜色");
		if (StringUtils.isEmpty(detail.getVehicleColor())) {
			return null;
		}
		p.setValue(detail.getVehicleColor());
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	// VIN
	private Property getVehicleVin(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("VIN码");
		if (StringUtils.isEmpty(detail.getVehicleVin())) {
			return null;
		}
		p.setValue(detail.getVehicleVin());
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	// 计划交车时间
	private Property getPlanDeliverTime(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("计划交车日期");
		if (detail.getPlanDeliverTime() == null) {
			return null;
		}
		p.setValue(Tools.formatDate(detail.getPlanDeliverTime()));
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	// 是否含保费
	private Property getisContainInsuranceCost(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("车价含保费");
		p.setValue(Tools.toBooleanStr(detail.getIsContainInsuranceCost()));
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	// 车辆总价
	private Property getVehiclePriceTotal(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("车辆总价");
		p.setValue(Tools.toCurrencyStr(detail.getVehiclePriceTotal()));
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		return p;
	}

	// 车辆单价
	private Property getVehiclePrice(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("车辆单价");
		p.setValue(Tools.toCurrencyStr(detail.getVehiclePrice()));
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		return p;
	}

	// 优惠金额
	private Property getDiscountAmount(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("优惠金额");
		if (Tools.toDouble(detail.getDiscountAmount()) == 0.00D) {
			return null;
		}
		p.setValue(Tools.toCurrencyStr(detail.getDiscountAmount()));
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		return p;

	}

	// 佣金
	private Property getVehicleProfit(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("佣金");

		if (Tools.toDouble(detail.getVehicleProfit()) == 0.00D) {
			return null;
		}
		p.setValue(Tools.toCurrencyStr(detail.getVehicleProfit()));
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		return p;
	}

	// 佣金客户
	private Property getCustomerProfit(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("佣金客户");

		if (StringUtils.isEmpty(detail.getCustomerIdProfit())) {
			return null;
		}
		p.setValue(detail.getCustomerNameProfit());
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		return p;
	}

	// 公司赠券
	private Property getLargessAmount(VehicleSaleContractDetail detail, Short abortStatus) {
		Property p = new Property();
		p.setLabel("公司赠券");
		if (detail.getLargessAmount() == null) {
			return null;
		}
		p.setValue(Tools.toCurrencyStr(detail.getLargessAmount()));
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		return p;
	}

//	// 单车利润
//	private Property getProfitPf(VehicleSaleContractDetail detail, Short status) {
//		VwVehicleSaleContractDetail vwVehicleSaleContractDetail = dao.get(VwVehicleSaleContractDetail.class,
//				detail.getContractDetailId());
//		if (vwVehicleSaleContractDetail == null) {
//			return null;
//		}
//		Property p = new Property();
//		p.setLabel("单车利润");
//
//		p.setValue(Tools.toCurrencyStr(vwVehicleSaleContractDetail.getProfitPf()));
//		p.setValueColour(Color.BLACK.getCode());
//		p.setShownOnBaseBoard(true);
//		p.setShownOnDetailBoard(true);
//		return p;
//
//	}
	
	
	// 单车利润
	private short createProfitPf(MobileBoardBuffered board, short sn, VehicleSaleContractDetail detail) {
		String lable = "单车利润";
		String fieldCode = "profitPf";

		VwVehicleSaleContractDetail vwVehicleSaleContractDetail = dao.get(VwVehicleSaleContractDetail.class, detail.getContractDetailId());
		if (vwVehicleSaleContractDetail == null) {
			return sn;
		}

		createProperty(board, fieldCode, sn++, true, true, null, lable,
				Tools.toCurrencyStr(vwVehicleSaleContractDetail.getProfitPf()), null, null, null, null, null,
				AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}
	

	// 交车地点
	private Property getDeliverAddress(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("交车地点");
		if (StringUtils.isEmpty(detail.getDeliverAddress())) {
			return null;
		}
		p.setValue(detail.getDeliverAddress());
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		return p;
	}

	// 标的物
	private Property getSubjectMatter(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("标的物");

		if (StringUtils.isEmpty(detail.getSubjectMatter())) {
			return null;
		}
		p.setValue(detail.getSubjectMatter());
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		return p;
	}

	// 备注
	private Property getVehicleComment(VehicleSaleContractDetail detail, Short status) {
		Property p = new Property();
		p.setLabel("备注");
		if (StringUtils.isEmpty(detail.getVehicleComment())) {
			return null;
		}
		p.setValue(detail.getVehicleComment());
		p.setValueColour(Color.BLACK.getCode());
		p.setShownOnBaseBoard(true);
		p.setShownOnDetailBoard(true);
		return p;
	}

}
