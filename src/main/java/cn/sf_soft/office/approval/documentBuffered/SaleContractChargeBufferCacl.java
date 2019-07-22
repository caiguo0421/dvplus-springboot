package cn.sf_soft.office.approval.documentBuffered;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferCalc;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentBuffered;
import cn.sf_soft.office.approval.dao.SaleContractsVaryDao;
import cn.sf_soft.office.approval.model.VehicleSaleContractCharge;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VwVehicleSaleContractDetail;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Property;

/**
 * 合同-费用明细
 * 
 * @author caigx
 *
 */
@Service("saleContractChargeBufferCacl")
public class SaleContractChargeBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractChargeBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractChargeBufferCacl.class.getSimpleName();

	private static String moduleId = "102025";

	// 10 终止 20 新增 30 修改
	private static final Short status_NEW = 20;
	private static final Short status_MODIFY = 30;
	private static final Short status_ABORT = 10;

	@Autowired
	private SaleContractsVaryDao saleContractsVaryDao;

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

	/**
	 * 计算字段
	 * 
	 * @param contractDetailVary
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MobileBoardBuffered computeFields(VehicleSaleContractDetail detail, String vehicleTitle) {
		List<VehicleSaleContractCharge> chargeList = (List<VehicleSaleContractCharge>) dao.findByHql(
				"from VehicleSaleContractCharge where contractDetailId = ? ", detail.getContractDetailId());
		if (chargeList == null || chargeList.size() == 0) {
			return null;
		}
		MobileBoardBuffered summaryBoard = new MobileBoardBuffered();
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(String.format("费用  收%s 支%s", Tools.toCurrencyStr(getChargeIncome(detail)),Tools.toCurrencyStr(getChargeCost(detail))));
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(false);
		createBoardTitle(summaryBoard,gson.toJson(baseBoardTitle),null,null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		//无权限的
		baseBoardTitle.setTitle(String.format("费用  收%s", Tools.toCurrencyStr(getChargeIncome(detail))));
		createBoardTitle(summaryBoard,gson.toJson(baseBoardTitle),null,null,null);

		

		DocTitle docTitle = new DocTitle(String.format("费用  收%s 支%s", Tools.toCurrencyStr(getChargeIncome(detail)),
				Tools.toCurrencyStr(getChargeCost(detail))));
		summaryBoard.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		int n = 0;
		for (int i = 0; i < chargeList.size(); i++) {
			VehicleSaleContractCharge charge = chargeList.get(i);
			sn = createDetailField(sn, n, summaryBoard, charge);
			n++;
		}
		createTail(summaryBoard,sn++);//创建Tail
		dao.save(summaryBoard);
		return summaryBoard;
	}

	/**
	 * 生成明细的Field
	 * 
	 * @param sn
	 *            保存的序号
	 * @param n
	 *            显示的序号
	 * @param board
	 * @param insurance
	 * @return 返回的最后序号
	 */
	public short createDetailField(short sn, int n, MobileBoardBuffered board, VehicleSaleContractCharge charge) {
		String title = String.format("%s", charge.getChargeName());
		String label = String.format("%d、%s", n + 1, charge.getChargeName());
		Integer background = Color.GRAY.getCode();
		createLable(board,sn++,true,true,true,label,null,null,background,null,null);

		// 费用收款
		Property income = getIncome(charge, charge.getAbortStatus());
		if (income != null) {
			saveProperty(board, "income",   sn++, income);
		}

		
		sn = createChargePf(board,sn,charge);

		Property remark = getRemark(charge, charge.getAbortStatus());
		if (remark != null) {
			saveProperty(board, "remark",   sn++, remark);
		}
		return sn;
	}

	// 费用收款
	private Property getIncome(VehicleSaleContractCharge charge, Short status) {
		Property p = new Property();
		p.setLabel("费用收款");
		if (charge.getIncome() == null) {
			return null;
		}

		p.setValue(Tools.toCurrencyStr(charge.getIncome()));
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

//	// 费用支出 单项费用支出取值要比较预估金额和实际金额，哪个大取哪个
//	private Property getChargePf(VehicleSaleContractCharge charge, Short status) {
//		Property p = new Property();
//		p.setLabel("费用支出");
//		double chargePf = Tools.toDouble(charge.getChargePf()) > Tools.toDouble(charge.getChargeCost()) ? Tools
//				.toDouble(charge.getChargePf()) : Tools.toDouble(charge.getChargeCost());
//
//		p.setValue(Tools.toCurrencyStr(chargePf));
//		p.setShownOnSlaveBoard(true);
//		p.setValueColour(Color.BLACK.getCode());
//		return p;
//	}

	// 费用支出 单项费用支出取值要比较预估金额和实际金额，哪个大取哪个
	private short createChargePf(MobileBoardBuffered board, short sn, VehicleSaleContractCharge charge) {
		String lable = "费用支出";
		String fieldCode = "chargePf";

		double chargePf = Tools.toDouble(charge.getChargePf()) > Tools.toDouble(charge.getChargeCost()) ? Tools
				.toDouble(charge.getChargePf()) : Tools.toDouble(charge.getChargeCost());
		createProperty(board, fieldCode, sn++, null, null, true, lable, Tools.toCurrencyStr(chargePf), null, null,
				null, null, null, AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}

	// 备注
	private Property getRemark(VehicleSaleContractCharge charge, Short status) {
		Property p = new Property();
		p.setLabel("备注");

		if (StringUtils.isEmpty(charge.getRemark())) {
			return null;
		}

		p.setValue(charge.getRemark());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	// 费用收款
	public double getChargeIncome(VehicleSaleContractDetail detail) {

		double income = 0.00D;
		String hql = "select sum(isnull(income,0)) from VehicleSaleContractCharge where contractDetailId = ? ";
		List<Double> list = (List<Double>) dao.findByHql(hql, detail.getContractDetailId());
		if (list != null && list.size() > 0) {
			income = Tools.toDouble(list.get(0));
		}
		return income;
	}

	// 费用支出
	public double getChargeCost(VehicleSaleContractDetail detail) {
		double cost = 0.00D;
		String hql = "select (case when isnull(chargePf,0)>isnull(chargeCost,0) then isnull(chargePf,0) else isnull(chargeCost,0) end) from VehicleSaleContractCharge where contractDetailId = ? ";
		List<Double> list = (List<Double>) dao.findByHql(hql, detail.getContractDetailId());
		if (list != null && list.size() > 0) {
			cost = Tools.toDouble(list.get(0));
		}
		return cost;
	}

}
