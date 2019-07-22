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
import cn.sf_soft.office.approval.model.VehicleSaleContractInsuranceVary;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Field;
import cn.sf_soft.office.approval.ui.model.Label;
import cn.sf_soft.office.approval.ui.model.Property;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;

/**
 * 合同-保险明细
 * 
 * @author caigx
 *
 */
@Service("saleContractInsuranceBufferCacl")
public class SaleContractInsuranceBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractInsuranceBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractInsuranceBufferCacl.class.getSimpleName();

	private static String moduleId = "102025";

	// 10 终止 20 新增 30 修改
	private static final short STATUS_NEW = 20;
	private static final short STATUS_MODIFY = 30;
	private static final short STATUS_ABORT = 10;

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

	@SuppressWarnings("unchecked")
	public MobileBoardBuffered computeFields(VehicleSaleContractDetail detail, String vehicleTitle) {
		List<VehicleSaleContractInsurance> insuranceList = (List<VehicleSaleContractInsurance>) dao.findByHql(
				"from VehicleSaleContractInsurance where contractDetailId = ?", detail.getContractDetailId());
		if (insuranceList == null || insuranceList.size() == 0) {
			return null;
		}

		MobileBoardBuffered summaryBoard = new MobileBoardBuffered();
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(String.format("保险  收%s 支%s", Tools.toCurrencyStr(getInsuranceIncome(detail)),Tools.toCurrencyStr(getInsuranceOutcome(detail))));
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(false);
		createBoardTitle(summaryBoard,gson.toJson(baseBoardTitle),null,null,AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		//无权限的
		baseBoardTitle.setTitle(String.format("费用  收%s", Tools.toCurrencyStr(getInsuranceIncome(detail))));
		createBoardTitle(summaryBoard,gson.toJson(baseBoardTitle),null,null,null);

		DocTitle docTitle = new DocTitle(String.format("保险  收%s 支%s",
				Tools.toCurrencyStr(getInsuranceIncome(detail)), Tools.toCurrencyStr(getInsuranceOutcome(detail))));
		summaryBoard.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		int n = 0;
		for (int i = 0; i < insuranceList.size(); i++) {
			VehicleSaleContractInsurance insurance = insuranceList.get(i);
			sn = createDetailField(sn, n, summaryBoard, insurance);
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
	public short createDetailField(short sn, int n, MobileBoardBuffered board, VehicleSaleContractInsurance insurance) {
		// label
		String title = String.format("%s", insurance.getCategoryName());
		
		String label = String.format("%d、%s", n + 1, insurance.getCategoryName());
		Integer background = Color.GRAY.getCode();
		createLable(board,sn++,true,true,true,label,null,null,background,null,null);

		// property
		// 保险公司
		Property supplierName = getSupplierName(insurance, insurance.getAbortStatus());
		if (supplierName != null) {
			saveProperty(board, "supplierName",   sn++, supplierName);
		}

		// 险种目录
		Property categoryName = getCategoryName(insurance, insurance.getAbortStatus());
		if (categoryName != null) {
			saveProperty(board, "categoryName",   sn++, categoryName);
		}

		// 保险收款
		Property categoryIncome = getCategoryIncome(insurance, insurance.getAbortStatus());
		if (categoryIncome != null) {
			saveProperty(board, "categoryIncome",   sn++, categoryIncome);
		}

		// 保险支出
		sn = createCategoryOutcome(board,sn,insurance);

		// 备注
		Property remark = getRemark(insurance, insurance.getAbortStatus());
		if (remark != null) {
			saveProperty(board, "remark",   sn++, remark);
		}

		return sn;
	}

	// 保险公司
	private Property getSupplierName(VehicleSaleContractInsurance insurance, Short status) {
		Property p = new Property();
		p.setLabel("保险公司");

		if (StringUtils.isEmpty(insurance.getSupplierId())) {
			return null;
		}
		BaseRelatedObjects relObj = dao.get(BaseRelatedObjects.class, insurance.getSupplierId());
		if (relObj == null) {
			return null;
		}
		p.setValue(relObj.getObjectName());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	// 险种目录
	private Property getCategoryName(VehicleSaleContractInsurance insurance, Short status) {
		Property p = new Property();
		p.setLabel("险种目录");

		if (StringUtils.isEmpty(insurance.getCategoryName())) {
			return null;
		}

		p.setValue(insurance.getCategoryName());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	// 保险收款
	private Property getCategoryIncome(VehicleSaleContractInsurance insurance, Short status) {
		Property p = new Property();
		p.setLabel("保险收款");
		if (insurance.getCategoryIncome() == null) {
			return null;
		}

		p.setValue(Tools.toCurrencyStr(insurance.getCategoryIncome()));
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	// 保险支出
//	private Property getCategoryOutcome(VehicleSaleContractInsurance insurance, Short status) {
//		Property p = new Property();
//		p.setLabel("保险支出");
//		if (insurance.getCategoryIncome() == null) {
//			return null;
//		}
//		double outcome = Tools.toDouble(insurance.getCategoryIncome()) * Tools.toDouble(insurance.getCategoryScale());
//
//		p.setValue(Tools.toCurrencyStr(outcome));
//		p.setShownOnSlaveBoard(true);
//		p.setValueColour(Color.BLACK.getCode());
//		return p;
//
//	}
	// 保险支出
	private short createCategoryOutcome(MobileBoardBuffered board,short sn, VehicleSaleContractInsurance insurance){
		String lable = "保险支出";
		String fieldCode = "categoryOutcome";
		if(insurance.getCategoryIncome() == null){
			return sn;
		}
		
		double outcome = Tools.toDouble(insurance.getCategoryIncome()) * Tools.toDouble(insurance.getCategoryScale());
		createProperty(board, fieldCode, sn++, null, null, true, lable, Tools.toCurrencyStr(outcome), null, null,
				null, null, null, AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}

	// 备注
	private Property getRemark(VehicleSaleContractInsurance insurance, Short status) {
		Property p = new Property();
		p.setLabel("备注");
		if (StringUtils.isEmpty(insurance.getRemark())) {
			return null;
		}
		p.setValue(insurance.getRemark());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	@SuppressWarnings("unchecked")
	public double getInsuranceIncome(VehicleSaleContractDetail detail) {
		double sumCategoryIncome = 0.00D;// 保险收款合计
		String hql = "select  sum(isnull(categoryIncome,0)) from VehicleSaleContractInsurance  where contractDetailId = ?";
		List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, detail.getContractDetailId());
		if (sumIncomeList != null && sumIncomeList.size() > 0) {
			sumCategoryIncome = Tools.toDouble(sumIncomeList.get(0));
		}
		return sumCategoryIncome;
	}

	@SuppressWarnings("unchecked")
	public double getInsuranceOutcome(VehicleSaleContractDetail detail) {
		double sumCategoryOutcome = 0.00D;// 保险支出
		String hql = "select  sum(isnull(categoryIncome,0)*isnull(categoryScale,0)) from VehicleSaleContractInsurance  where contractDetailId = ?";
		List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, detail.getContractDetailId());
		if (sumIncomeList != null && sumIncomeList.size() > 0) {
			sumCategoryOutcome = Tools.toDouble(sumIncomeList.get(0));
		}
		return sumCategoryOutcome;
	}

}
