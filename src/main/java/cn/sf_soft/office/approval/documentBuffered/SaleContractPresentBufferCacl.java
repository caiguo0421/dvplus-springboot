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
import cn.sf_soft.office.approval.dao.SaleContractsVaryDao;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractItem;
import cn.sf_soft.office.approval.model.VehicleSaleContractPresent;
import cn.sf_soft.office.approval.model.VehicleSaleContractPresentVary;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Field;
import cn.sf_soft.office.approval.ui.model.Label;
import cn.sf_soft.office.approval.ui.model.Property;

/**
 * 合同-精品
 * 
 * @author caigx
 *
 */
@Service("saleContractPresentBufferCacl")
public class SaleContractPresentBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractPresentBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractPresentBufferCacl.class.getSimpleName();

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
		List<VehicleSaleContractPresent> presentList = (List<VehicleSaleContractPresent>) dao.findByHql(
				"from VehicleSaleContractPresent where contractDetailId = ?", detail.getContractDetailId());
		if (presentList == null || presentList.size() == 0) {
			return null;
		}
		MobileBoardBuffered summaryBoard = new MobileBoardBuffered();
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(String.format("精品  收%s 支%s", Tools.toCurrencyStr(getPresentIncome(detail)),
				Tools.toCurrencyStr(getPresentCost(detail))));
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(false);
		createBoardTitle(summaryBoard,gson.toJson(baseBoardTitle),null,null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		//无权限的
		baseBoardTitle.setTitle(String.format("费用  收%s", Tools.toCurrencyStr(getPresentIncome(detail))));
		createBoardTitle(summaryBoard,gson.toJson(baseBoardTitle),null,null,null);

		DocTitle docTitle = new DocTitle(String.format("精品  收%s 支%s", Tools.toCurrencyStr(getPresentIncome(detail)),
				Tools.toCurrencyStr(getPresentCost(detail))));
		summaryBoard.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		int n = 0;
		for (int i = 0; i < presentList.size(); i++) {
			VehicleSaleContractPresent present = presentList.get(i);
			sn = createDetailField(sn, n, summaryBoard, present);
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
	public short createDetailField(short sn, int n, MobileBoardBuffered board, VehicleSaleContractPresent present) {
		String title = String.format("%s", present.getPartName());
		
		String label = String.format("%d、%s", n + 1, present.getPartName());
		Integer background = Color.GRAY.getCode();
		createLable(board,sn++,true,true,true,label,null,null,background,null,null);

		// 精品编码
		Property partNo = getPartNo(present, present.getAbortStatus());
		if (partNo != null) {
			saveProperty(board, "partNo",   sn++, partNo);
		}

		// 精品售价
		Property income = getIncome(present, present.getAbortStatus());
		if (income != null) {
			saveProperty(board, "income",   sn++, income);
		}

		// 精品成本
		sn = createCostRecord(board,sn,present);

		// 备注
		Property remark = getRemark(present, present.getAbortStatus());
		if (remark != null) {
			saveProperty(board, "remark",   sn++, remark);
		}

		return sn;
	}

	

	// 精品编码
	private Property getPartNo(VehicleSaleContractPresent present, Short status) {
		Property p = new Property();
		p.setLabel("精品编码");

		if (StringUtils.isEmpty(present.getPartNo())) {
			return null;
		}

		p.setValue(present.getPartNo());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	// 精品售价
	private Property getIncome(VehicleSaleContractPresent present, Short status) {
		Property p = new Property();
		p.setLabel("精品售价");
		if (present.getIncome() == null) {
			return null;
		}

		p.setValue(Tools.toCurrencyStr(present.getIncome()));
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	// 精品成本 精品成本=精品单价*（计领数量-已领数量）+精品成本*已领数量
//	private Property getCostRecord(VehicleSaleContractPresent present, Short status) {
//		Property p = new Property();
//		p.setLabel("精品成本");
//		double outCome = 0;
//		outCome = Tools.toDouble(present.getPosPrice())
//				* (Tools.toDouble(present.getPlanQuantity()) - Tools.toDouble(present.getGetQuantity()))
//				+ Tools.toDouble(present.getCostRecord()) * Tools.toDouble(present.getGetQuantity());
//
//		p.setValue(Tools.toCurrencyStr(outCome));
//		p.setShownOnSlaveBoard(true);
//		p.setValueColour(Color.BLACK.getCode());
//		return p;
//
//	}
	
	// 精品成本 精品成本=精品单价*（计领数量-已领数量）+精品成本*已领数量
	private short createCostRecord(MobileBoardBuffered board, short sn, VehicleSaleContractPresent present) {
		String lable = "精品成本";
		String fieldCode = "costRecord";

		double outCome = 0;
		outCome = Tools.toDouble(present.getPosPrice())
				* (Tools.toDouble(present.getPlanQuantity()) - Tools.toDouble(present.getGetQuantity()))
				+ Tools.toDouble(present.getCostRecord()) * Tools.toDouble(present.getGetQuantity());

		createProperty(board, fieldCode, sn++, null, null, true, lable, Tools.toCurrencyStr(outCome), null, null, null,
				null, null, AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}

	// 备注
	private Property getRemark(VehicleSaleContractPresent present, Short status) {
		Property p = new Property();
		p.setLabel("备注");

		if (StringUtils.isEmpty(present.getRemark())) {
			return null;
		}

		p.setValue(present.getRemark());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	// 精品售价
	@SuppressWarnings("unchecked")
	public double getPresentIncome(VehicleSaleContractDetail detail) {
		double income = 0.00D;
		String hql = "select sum(isnull(income,0)) from VehicleSaleContractPresent where contractDetailId = ? ";
		List<Double> list = (List<Double>) dao.findByHql(hql, detail.getContractDetailId());
		if (list != null && list.size() > 0) {
			income = Tools.toDouble(list.get(0));
		}
		return income;
	}

	// 精品成本
	@SuppressWarnings("unchecked")
	public double getPresentCost(VehicleSaleContractDetail detail) {
		double cost = 0.00D;
		String hql = "select sum(isnull(posPrice,0)*(isnull(planQuantity,0)-isnull(getQuantity,0))+ isnull(costRecord,0)*isnull(getQuantity,0)) from VehicleSaleContractPresent where contractDetailId = ? ";
		List<Double> list = (List<Double>) dao.findByHql(hql, detail.getContractDetailId());
		if (list != null && list.size() > 0) {
			cost = Tools.toDouble(list.get(0));
		}
		return cost;
	}

}
