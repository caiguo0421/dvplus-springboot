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
import cn.sf_soft.office.approval.model.VehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractInsurance;
import cn.sf_soft.office.approval.model.VehicleSaleContractItem;
import cn.sf_soft.office.approval.model.VehicleSaleContractItemVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractPresentVary;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Field;
import cn.sf_soft.office.approval.ui.model.Label;
import cn.sf_soft.office.approval.ui.model.Property;

/**
 * 合同-改装
 * 
 * @author caigx
 *
 */
@Service("saleContractItemBufferCacl")
public class SaleContractItemBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractItemBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractItemBufferCacl.class.getSimpleName();

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
		List<VehicleSaleContractItem> itemList = (List<VehicleSaleContractItem>) dao.findByHql(
				"from VehicleSaleContractItem where contractDetailId = ? ", detail.getContractDetailId());
		if (itemList == null || itemList.size() == 0) {
			return null;
		}

		MobileBoardBuffered summaryBoard = new MobileBoardBuffered();
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(String.format("改装  收%s 支%s", Tools.toCurrencyStr(getItemIncome(detail)),
				Tools.toCurrencyStr(getItemCost(detail))));
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(false);
		createBoardTitle(summaryBoard,gson.toJson(baseBoardTitle),null,null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		//无权限的
		baseBoardTitle.setTitle(String.format("费用  收%s", Tools.toCurrencyStr(getItemIncome(detail))));
		createBoardTitle(summaryBoard,gson.toJson(baseBoardTitle),null,null,null);
		

		DocTitle docTitle = new DocTitle(String.format("改装  收%s 支%s", Tools.toCurrencyStr(getItemIncome(detail)),
				Tools.toCurrencyStr(getItemCost(detail))));
		summaryBoard.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		int n = 0;
		for (int i = 0; i < itemList.size(); i++) {
			VehicleSaleContractItem item = itemList.get(i);
			sn = createDetailField(sn, n, summaryBoard, item);
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
	public short createDetailField(short sn, int n, MobileBoardBuffered board, VehicleSaleContractItem item) {
		String title = String.format("%s", item.getItemName());
		
		String label =String.format("%d、%s", n + 1, item.getItemName());
		Integer background = Color.GRAY.getCode();
		createLable(board,sn++,true,true,true,label,null,null,background,null,null);
		
		
		// 项目编码
		Property itemNo = getItemNo(item, item.getAbortStatus());
		if (itemNo != null) {
			saveProperty(board, "itemNo",   sn++, itemNo);
		}

		Property income = getIncome(item, item.getAbortStatus());
		if (income != null) {
			saveProperty(board, "income",   sn++, income);
		}

		
		//改装成本
		sn = createCost(board,sn,item);

		Property comment = getComment(item, item.getAbortStatus());
		if (comment != null) {
			saveProperty(board, "comment",   sn++, comment);
		}

		return sn;
	}

	

	// 项目编码
	private Property getItemNo(VehicleSaleContractItem item, Short status) {
		Property p = new Property();
		p.setLabel("项目编码");

		if (StringUtils.isEmpty(item.getItemNo())) {
			return null;
		}

		p.setValue(item.getItemNo());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	// 改装价格
	private Property getIncome(VehicleSaleContractItem item, Short status) {
		Property p = new Property();
		p.setLabel("改装价格");
		if (item.getIncome() == null) {
			return null;
		}

		p.setValue(Tools.toCurrencyStr(item.getIncome()));
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	// 改装成本
//	private Property getCost(VehicleSaleContractItem item, Short status) {
//		Property p = new Property();
//		p.setLabel("改装成本");
//		if (item.getItemCost() == null) {
//			return null;
//		}
//
//		p.setValue(Tools.toCurrencyStr(item.getItemCost()));
//		p.setShownOnSlaveBoard(true);
//		p.setValueColour(Color.BLACK.getCode());
//		return p;
//
//	}
	
	
	// 改装成本
	private short createCost(MobileBoardBuffered board, short sn, VehicleSaleContractItem item) {
		String lable = "改装成本";
		String fieldCode = "cost";
		if (item.getItemCost() == null) {
			return sn;
		}

		createProperty(board, fieldCode, sn++, null, null, true, lable, Tools.toCurrencyStr(item.getItemCost()), null,
				null, null, null, null, AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}

	// 备注
	private Property getComment(VehicleSaleContractItem item, Short status) {
		Property p = new Property();
		p.setLabel("备注");

		if (StringUtils.isEmpty(item.getComment())) {
			return null;
		}

		p.setValue(item.getComment());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	// 改装价格
	@SuppressWarnings("unchecked")
	public double getItemIncome(VehicleSaleContractDetail detail) {
		double income = 0.00D;
		String hql = "select sum(isnull(income,0)) from VehicleSaleContractItem where contractDetailId = ? ";
		List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, detail.getContractDetailId());
		if (sumIncomeList != null && sumIncomeList.size() > 0) {
			income = Tools.toDouble(sumIncomeList.get(0));
		}
		return income;
	}

	// 改装成本
	@SuppressWarnings("unchecked")
	public double getItemCost(VehicleSaleContractDetail detail) {
		double cost = 0.00D;
		String hql = "select sum(isnull(itemCost,0)) from VehicleSaleContractItem where contractDetailId = ? ";
		List<Double> list = (List<Double>) dao.findByHql(hql, detail.getContractDetailId());
		if (list != null && list.size() > 0) {
			cost = Tools.toDouble(list.get(0));
		}
		return cost;
	}

}
