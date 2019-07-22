package cn.sf_soft.office.approval.documentBuffered;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.sf_soft.office.approval.model.VehicleInvoices;
import cn.sf_soft.office.approval.model.VehicleSaleContractChargeVary;
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
 * 合同变更-保险明细
 * 
 * @author caigx
 *
 */
@Service("saleContractInsuranceVaryBufferCacl")
public class SaleContractInsuranceVaryBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractInsuranceVaryBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractInsuranceVaryBufferCacl.class.getSimpleName();

	private static String moduleId = "102025";

	// 10 终止 20 新增 30 修改
	private static final short STATUS_NEW = 20;
	private static final short STATUS_MODIFY = 30;
	private static final short STATUS_ABORT = 10;

	// 合同变更车辆状态
	private short vehicleAbortStatus;

	@Autowired
	private SaleContractsVaryDao saleContractsVaryDao;

	@Autowired
	private SaleContractInsuranceBufferCacl saleContractInsuranceBufferCacl;

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
	private List<VehicleSaleContractInsurance> getNoChangeList(VehicleSaleContractDetailVary contractDetailVary) {
		String hql = "from VehicleSaleContractInsurance as a  where not exists(select 1 from VehicleSaleContractInsuranceVary as b where a.saleContractInsuranceId = b.saleContractInsuranceId and b.contractDetailId = ?  and  b.detailVaryId = ?) and  a.contractDetailId = ?";
		List<VehicleSaleContractInsurance> list = (List<VehicleSaleContractInsurance>) dao.findByHql(hql,
				contractDetailVary.getContractDetailId(),contractDetailVary.getDetailVaryId(), contractDetailVary.getContractDetailId());
		return list;
	}

	/**
	 * 
	 * @param contractDetailVary
	 * @param vehicleTitle
	 *            车辆标题
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MobileBoardBuffered computeFields(VehicleSaleContractDetailVary contractDetailVary, String vehicleTitle) {
		List<VehicleSaleContractInsuranceVary> insuranceVaryList = saleContractsVaryDao
				.getInsuranceVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
		List<VehicleSaleContractInsurance> nochangeList = getNoChangeList(contractDetailVary);
		if ((insuranceVaryList == null || insuranceVaryList.size() == 0)
				&& (nochangeList == null || nochangeList.size() == 0)) {
			return null;
		}

		// 车辆状态
		this.setVehicleAbortStatus(contractDetailVary.getAbortStatus());

		MobileBoardBuffered summaryBoard = new MobileBoardBuffered();
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(getTitle(contractDetailVary,true));
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(false);
		createBoardTitle(summaryBoard, gson.toJson(baseBoardTitle), null, null, AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		// 无权限的
		baseBoardTitle.setTitle(getTitle(contractDetailVary, false));
		createBoardTitle(summaryBoard, gson.toJson(baseBoardTitle), null, null, null);

		DocTitle docTitle = new DocTitle(getTitle(contractDetailVary,true));
		summaryBoard.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		int n = 0;
		for (int i = 0; i < insuranceVaryList.size(); i++) {
			VehicleSaleContractInsuranceVary insurance = insuranceVaryList.get(i);
			sn = createDetailField(sn, n, summaryBoard, insurance);
			n++;
		}

		// 未变更
		for (int i = 0; i < nochangeList.size(); i++) {
			VehicleSaleContractInsurance insurance = nochangeList.get(i);
			sn = saleContractInsuranceBufferCacl.createDetailField(sn, n, summaryBoard, insurance);
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
	public short createDetailField(short sn, int n, MobileBoardBuffered board,
			VehicleSaleContractInsuranceVary insurance) {
		String abortStatusStr = "";
		if (STATUS_NEW == insurance.getAbortStatus()) {
			abortStatusStr = "新增";
		} else if (STATUS_MODIFY == insurance.getAbortStatus()) {
			abortStatusStr = "修改";
		} else if (STATUS_ABORT == insurance.getAbortStatus()) {
			abortStatusStr = "终止";
		}
		String title = String.format("%s %s", abortStatusStr, insurance.getCategoryName());
		if (vehicleAbortStatus == STATUS_NEW) {
			title = String.format("%s", insurance.getCategoryName());// 新增不用显示状态
		}
		
		String label = String.format(String.format("%d、%s", n + 1, title));
		Integer background = Color.GRAY.getCode();
		createLable(board,sn++,true,true,true,label,null,null,background,null,null);
		

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
		// 终止原因
		Property abortComment = getAbortComment(insurance, insurance.getAbortStatus());
		if (abortComment != null) {
			saveProperty(board, "abortComment",   sn++, abortComment);
		}

		return sn;
	}

	private String getTitle(VehicleSaleContractDetailVary contractDetailVary,boolean hasPopedom) {
		Map<String,Double> result =  getInsuranceSummary(contractDetailVary);
		double income = result.get("income");
		double outcome = result.get("outcome");
		double oriIncome = result.get("oriIncome");;
		double oriOutcome = result.get("oriOutcome");
		
		String recStr ="";
		String payStr = "";
		if(STATUS_NEW ==contractDetailVary.getAbortStatus()){
			recStr = String.format("%s",Tools.toCurrencyStr(income));
			payStr = String.format("%s",Tools.toCurrencyStr(outcome));
		}else if(STATUS_ABORT == contractDetailVary.getAbortStatus()){
			recStr = String.format("%s",Tools.toCurrencyStr(oriIncome));
			payStr = String.format("%s",Tools.toCurrencyStr(oriOutcome));
		}else if(STATUS_MODIFY == contractDetailVary.getAbortStatus()){
			if(income==oriIncome){
				recStr = String.format("%s",Tools.toCurrencyStr(income));
			}else{
				recStr = String.format("%s【%s】",Tools.toCurrencyStr(income),Tools.toCurrencyStr(oriIncome));
			}
			
			if(outcome==oriOutcome){
				payStr = String.format("%s",Tools.toCurrencyStr(outcome));
			}else{
				payStr = String.format("%s【%s】",Tools.toCurrencyStr(outcome),Tools.toCurrencyStr(oriOutcome));
			}
//			if(income==oriIncome&&outcome==oriOutcome){
//				recStr = String.format("%s",Tools.toCurrencyStr(income));
//				payStr = String.format("%s",Tools.toCurrencyStr(outcome));
//			}else{
//				recStr = String.format("%s【%s】",Tools.toCurrencyStr(income),Tools.toCurrencyStr(oriIncome));
//				payStr = String.format("%s【%s】",Tools.toCurrencyStr(outcome),Tools.toCurrencyStr(oriOutcome));
//			}
		}
		
		String titleStr ="保险";
		if(hasPopedom){
			return String.format("%s 收%s 支%s", titleStr,recStr,payStr);
		}else{
			return String.format("%s 收%s", titleStr,recStr);
		}
		
	}

//	private MobileBoardBuffered getInsuranceVaryDetail(VehicleSaleContractInsuranceVary insurance, String vehicleTitle,
//			String title) {
//		MobileBoardBuffered lableBoard = new MobileBoardBuffered();
//
//		BoardTitle baseBoardTitle = new BoardTitle();
//		baseBoardTitle.setTitle(String.format("%s %s", vehicleTitle, title));
//		baseBoardTitle.setCollapsable(true);
//		baseBoardTitle.setDefaultExpanded(true);
//		createBoardTitle(lableBoard, gson.toJson(baseBoardTitle), null, null, null);
//		
//
//		DocTitle docTitle = new DocTitle(String.format("%s", insurance.getCategoryName()));
//		lableBoard.setDocTitle(gson.toJson(docTitle));
//
//		short sn = 1;
//		// 保险公司
//		Property supplierName = getSupplierName(insurance, insurance.getAbortStatus());
//		if (supplierName != null) {
//			saveProperty(lableBoard, "supplierName",   sn++, supplierName);
//		}
//		// 险种目录
//		Property categoryName = getCategoryName(insurance, insurance.getAbortStatus());
//		if (categoryName != null) {
//			saveProperty(lableBoard, "categoryName",   sn++, categoryName);
//		}
//		// 保险收款
//		Property categoryIncome = getCategoryIncome(insurance, insurance.getAbortStatus());
//		if (categoryIncome != null) {
//			saveProperty(lableBoard, "categoryIncome",   sn++, categoryIncome);
//		}
//		// 保险支出
//		Property categoryOutcome = getCategoryOutcome(insurance, insurance.getAbortStatus());
//		if (categoryOutcome != null) {
//			saveProperty(lableBoard, "categoryOutcome",   sn++, categoryOutcome);
//		}
//		// 备注
//		Property remark = getRemark(insurance, insurance.getAbortStatus());
//		if (remark != null) {
//			saveProperty(lableBoard, "remark",   sn++, remark);
//		}
//		// 终止原因
//		Property abortComment = getAbortComment(insurance, insurance.getAbortStatus());
//		if (abortComment != null) {
//			saveProperty(lableBoard, "abortComment",   sn++, abortComment);
//		}
//
//		return lableBoard;
//	}

	// 保险公司
	private Property getSupplierName(VehicleSaleContractInsuranceVary insurance, short status) {
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
	private Property getCategoryName(VehicleSaleContractInsuranceVary insurance, short status) {
		Property p = new Property();
		p.setLabel("险种目录");
		if (status == STATUS_NEW) {
			if (StringUtils.isEmpty(insurance.getCategoryName())) {
				return null;
			}

			p.setValue(insurance.getCategoryName());
			p.setShownOnSlaveBoard(true);
			p.setValueColour(Color.BLACK.getCode());
			return p;
		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
			if (StringUtils.isEmpty(insurance.getCategoryName())) {
				return null;
			}
			p.setValue(insurance.getCategoryName());
			p.setShownOnSlaveBoard(true);
			p.setValueColour(Color.BLACK.getCode());

			return p;
		}
		return null;
	}

	// 保险收款
	private Property getCategoryIncome(VehicleSaleContractInsuranceVary insurance, short status) {
		Property p = new Property();
		p.setLabel("保险收款");
		if (status == STATUS_NEW) {
			if (insurance.getCategoryIncome() == null) {
				return null;
			}

			p.setValue(Tools.toCurrencyStr(insurance.getCategoryIncome()));
			p.setShownOnSlaveBoard(true);
			p.setValueColour(Color.BLACK.getCode());
			return p;
		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
			if (insurance.getCategoryIncome() == null) {
				return null;
			}
			if (compareEqual(insurance.getCategoryIncome(), insurance.getOriCategoryIncome())) {
				p.setValue(Tools.toCurrencyStr(insurance.getCategoryIncome()));
				p.setValueColour(Color.BLACK.getCode());
			} else {
				p.setValue(formatDiff(insurance.getCategoryIncome(), insurance.getOriCategoryIncome()));
				p.setValueColour(Color.RED.getCode());
			}

			p.setShownOnSlaveBoard(true);
			return p;
		}
		return null;
	}

	// 保险支出
//	private Property getCategoryOutcome(VehicleSaleContractInsuranceVary insurance, short status) {
//		Property p = new Property();
//		p.setLabel("保险支出");
//		if (status == STATUS_NEW) {
//			if (insurance.getCategoryIncome() == null) {
//				return null;
//			}
//			double outcome = Tools.toDouble(insurance.getCategoryIncome())
//					* Tools.toDouble(insurance.getCategoryScale());
//
//			p.setValue(Tools.toCurrencyStr(outcome));
//			p.setShownOnSlaveBoard(true);
//			p.setValueColour(Color.BLACK.getCode());
//			return p;
//		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
//			if (insurance.getCategoryIncome() == null) {
//				return null;
//			}
//			double outcome = Tools.toDouble(insurance.getCategoryIncome())
//					* Tools.toDouble(insurance.getCategoryScale());
//			double oriOutcome = Tools.toDouble(insurance.getOriCategoryIncome())
//					* Tools.toDouble(insurance.getCategoryScale());
//
//			if(compareEqual(outcome, oriOutcome)){
//				p.setValue(Tools.toCurrencyStr(outcome));
//				p.setValueColour(Color.BLACK.getCode());
//			} else {
//				p.setValue(formatDiff(outcome, oriOutcome));
//				p.setValueColour(Color.RED.getCode());
//			}
//
//			p.setShownOnSlaveBoard(true);
//			return p;
//		}
//		return null;
//	}
	
	// 保险支出
	private short createCategoryOutcome(MobileBoardBuffered board, short sn, VehicleSaleContractInsuranceVary insurance) {
		String lable = "保险支出";
		String fieldCode = "categoryOutcome";
		String value = null;
		Integer valueColour = null;
		short status = Tools.toShort(insurance.getAbortStatus());

		double outcome = Tools.toDouble(insurance.getCategoryIncome()) * Tools.toDouble(insurance.getCategoryScale());
		double oriOutcome = Tools.toDouble(insurance.getOriCategoryIncome())
				* Tools.toDouble(insurance.getCategoryScale());

		if (status == STATUS_NEW) {
			if (insurance.getCategoryIncome() == null) {
				return sn;
			}
			value = Tools.toCurrencyStr(outcome);
		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
			if (insurance.getCategoryIncome() == null) {
				return sn;
			}
			if (compareEqual(outcome, oriOutcome)) {
				value = Tools.toCurrencyStr(outcome);
			} else {
				value = formatDiff(outcome, oriOutcome);
				valueColour = Color.RED.getCode();
			}

		}

		createProperty(board, fieldCode, sn++, null, null, true, lable, value, null, null, valueColour, null, null,
				AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}

	// 备注
	private Property getRemark(VehicleSaleContractInsuranceVary insurance, short status) {
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

	// 终止原因
	private Property getAbortComment(VehicleSaleContractInsuranceVary insurance, short status) {
		Property p = new Property();
		p.setLabel("终止原因");
		if (StringUtils.isEmpty(insurance.getAbortComment())) {
			return null;
		}
		p.setValue(insurance.getAbortComment());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;
	}

	
	@SuppressWarnings("unchecked")
	public Map<String,Double> getInsuranceSummary(VehicleSaleContractDetailVary contractDetailVary){
//		StringBuffer buf = new StringBuffer();
//		buf.append("select sum(case when abortStatus<>10 then categoryIncome end) as  income,").append("\r\n");
//		buf.append("sum(case when abortStatus<>10 then outCome end) as  outcome,").append("\r\n");
//		buf.append("sum(case when detailVaryId is null then categoryIncome else oriCategoryIncome end) as oriIncome,").append("\r\n");
//		buf.append("sum(case when detailVaryId is null then outCome else oriOutCome end) as oriOutcome,").append("\r\n");
//		buf.append("sum(realCost) as realCost").append("\r\n");
//		buf.append("from VwVehicleSaleContractInsuranceVaryMarge where contractDetailId = ?");
//		List<Object> data1 = (List<Object>) dao.findByHql(buf.toString(),contractDetailVary.getContractDetailId());
		
		List<Map<String,Object>>  data1=  saleContractsVaryDao.getInsuranceVaryMerge(contractDetailVary.getContractDetailId(),contractDetailVary.getDetailVaryId());
		double income=0, outcome=0,oriIncome=0,oriOutcome = 0,realCost = 0;
		if(data1!=null && data1.size()==1){
			Map<String,Object> row = data1.get(0);
			income = row.get("income")!=null?(double)row.get("income"):0;
			outcome = row.get("outcome")!=null?(double)row.get("outcome"):0;
			oriIncome = row.get("oriIncome")!=null?(double)row.get("oriIncome"):0;
			oriOutcome = row.get("oriOutcome")!=null?(double)row.get("oriOutcome"):0;
			realCost = row.get("realCost")!=null?(double)row.get("realCost"):0;
		}
		
		
		Map<String,Double> result = new HashMap<String,Double>();
		result.put("income", income);
		result.put("outcome", outcome);
		result.put("oriIncome", oriIncome);
		result.put("oriOutcome", oriOutcome);
		result.put("realCost", realCost);
		return result;
	}
	
	/**
	@SuppressWarnings("unchecked")
	public double getInsuranceIncome(VehicleSaleContractDetailVary contractDetailVary) {
		double sumCategoryIncome = 0.00D;// 保险收款合计
		String hql = "select  sum(isnull(categoryIncome,0)) from VehicleSaleContractInsuranceVary  where detailVaryId = ? and abortStatus != ?";
		List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId(),
				STATUS_ABORT);
		if (sumIncomeList != null && sumIncomeList.size() > 0) {
			sumCategoryIncome = Tools.toDouble(sumIncomeList.get(0));
		}
		return sumCategoryIncome;
	}

	@SuppressWarnings("unchecked")
	public double getInsuranceOutcome(VehicleSaleContractDetailVary contractDetailVary) {
		double sumCategoryOutcome = 0.00D;// 保险支出
		String hql = "select  sum(isnull(categoryIncome,0)*isnull(categoryScale,0)) from VehicleSaleContractInsuranceVary  where detailVaryId = ? and abortStatus != ?";
		List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId(),
				STATUS_ABORT);
		if (sumIncomeList != null && sumIncomeList.size() > 0) {
			sumCategoryOutcome = Tools.toDouble(sumIncomeList.get(0));
		}
		return sumCategoryOutcome;
	}

	@SuppressWarnings("unchecked")
	public double getOriInsuranceIncome(VehicleSaleContractDetailVary contractDetailVary) {
		double sumCategoryIncome = 0.00D;
		String hql = "select  sum(isnull(categoryIncome,0)) from VehicleSaleContractInsurance  where contractDetailId = ? and abortStatus != ?";
		List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, contractDetailVary.getContractDetailId(),
				STATUS_ABORT);

		if (sumIncomeList != null && sumIncomeList.size() > 0) {
			sumCategoryIncome = Tools.toDouble(sumIncomeList.get(0));
		}
		return sumCategoryIncome;
	}

	@SuppressWarnings("unchecked")
	public double getOriInsuranceOutcome(VehicleSaleContractDetailVary contractDetailVary) {
		double sumCategoryOutcome = 0.00D;// 保险支出
		String hql = "select  sum(isnull(categoryIncome,0)*isnull(categoryScale,0)) from VehicleSaleContractInsurance  where contractDetailId = ? and abortStatus != ?";
		List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, contractDetailVary.getContractDetailId(),
				STATUS_ABORT);
		if (sumIncomeList != null && sumIncomeList.size() > 0) {
			sumCategoryOutcome = Tools.toDouble(sumIncomeList.get(0));
		}
		return sumCategoryOutcome;
	}**/

	public short getVehicleAbortStatus() {
		return vehicleAbortStatus;
	}

	
	public void setVehicleAbortStatus(Short vehicleAbortStatus) {
		if (vehicleAbortStatus == null) {
			vehicleAbortStatus = STATUS_MODIFY; // 如果为空按修改处理
		}
		this.vehicleAbortStatus = vehicleAbortStatus;
	}

}
