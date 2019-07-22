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
import cn.sf_soft.office.approval.model.VehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractInsurance;
import cn.sf_soft.office.approval.model.VehicleSaleContractInsuranceVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractPresent;
import cn.sf_soft.office.approval.model.VehicleSaleContractPresentVary;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Field;
import cn.sf_soft.office.approval.ui.model.Label;
import cn.sf_soft.office.approval.ui.model.Property;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;

/**
 * 合同变更-精品
 * 
 * @author caigx
 *
 */
@Service("saleContractPresentVaryBufferCacl")
public class SaleContractPresentVaryBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractPresentVaryBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractPresentVaryBufferCacl.class.getSimpleName();

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
	private SaleContractPresentBufferCacl saleContractPresentBufferCacl;

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
	private List<VehicleSaleContractPresent> getNoChangeList(VehicleSaleContractDetailVary contractDetailVary) {
		String hql = "from VehicleSaleContractPresent as a where not exists (select 1 from VehicleSaleContractPresentVary as b  where a.saleContractPresentId = b.saleContractPresentId and b.contractDetailId = ? and  b.detailVaryId = ?) and a.contractDetailId = ?";
		List<VehicleSaleContractPresent> list = (List<VehicleSaleContractPresent>) dao.findByHql(hql,
				contractDetailVary.getContractDetailId(), contractDetailVary.getDetailVaryId(),contractDetailVary.getContractDetailId());
		return list;
	}

	/**
	 * 计算字段
	 * 
	 * @param contractDetailVary
	 * @return
	 */
	public MobileBoardBuffered computeFields(VehicleSaleContractDetailVary contractDetailVary, String vehicleTitle) {
		List<VehicleSaleContractPresentVary> presentVaryList = saleContractsVaryDao
				.getPresentVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
		List<VehicleSaleContractPresent> noChangeList = getNoChangeList(contractDetailVary);

		if ((presentVaryList == null || presentVaryList.size() == 0)
				&& (noChangeList == null || noChangeList.size() == 0)) {
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
		for (int i = 0; i < presentVaryList.size(); i++) {
			VehicleSaleContractPresentVary present = presentVaryList.get(i);
			sn = createDetailField(sn, n, summaryBoard, present);
			n++;
		}

		// 未变更
		for (int i = 0; i < noChangeList.size(); i++) {
			VehicleSaleContractPresent present = noChangeList.get(i);
			sn = saleContractPresentBufferCacl.createDetailField(sn, n, summaryBoard, present);
			n++;
		}
		createTail(summaryBoard,sn++);//创建Tail
		dao.save(summaryBoard);
		return summaryBoard;
	}
	
	private String getTitle(VehicleSaleContractDetailVary contractDetailVary,boolean hasPopedom) {
		Map<String,Double> result =  getPresentSummary(contractDetailVary);
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
		
		String titleStr ="精品";
		if(hasPopedom){
			return String.format("%s 收%s 支%s", titleStr,recStr,payStr);
		}else{
			return String.format("%s 收%s", titleStr,recStr);
		}
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
	public short createDetailField(short sn, int n, MobileBoardBuffered board, VehicleSaleContractPresentVary present) {
		String abortStatusStr = "";
		if (STATUS_NEW == present.getAbortStatus()) {
			abortStatusStr = "新增";
		} else if (STATUS_MODIFY == present.getAbortStatus()) {
			abortStatusStr = "修改";
		} else if (STATUS_ABORT == present.getAbortStatus()) {
			abortStatusStr = "终止";
		}
		String title = String.format("%s %s", abortStatusStr, present.getPartName());

		if (vehicleAbortStatus == STATUS_NEW) {
			title = String.format("%s", present.getPartName());// 新增不用显示状态
		}
		
		String label = String.format("%d、%s", n + 1, title);
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
		// 终止原因
		Property abortComment = getAbortComment(present, present.getAbortStatus());
		if (abortComment != null) {
			saveProperty(board, "abortComment",   sn++, abortComment);
		}

		return sn;
	}

	// 精品编码
	private Property getPartNo(VehicleSaleContractPresentVary present, short status) {
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
	private Property getIncome(VehicleSaleContractPresentVary present, short status) {
		Property p = new Property();
		p.setLabel("精品售价");
		if (status == STATUS_NEW) {
			if (present.getIncome() == null) {
				return null;
			}

			p.setValue(Tools.toCurrencyStr(present.getIncome()));
			p.setShownOnSlaveBoard(true);
			p.setValueColour(Color.BLACK.getCode());
			return p;
		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
			if (present.getIncome() == null) {
				return null;
			}
			if (compareEqual(present.getIncome(), present.getOriIncome())) {
				p.setValue(Tools.toCurrencyStr(present.getIncome()));
				p.setValueColour(Color.BLACK.getCode());
			} else {
				p.setValue(formatDiff(present.getIncome(), present.getOriIncome()));
				p.setValueColour(Color.RED.getCode());
			}

			p.setShownOnSlaveBoard(true);
			return p;
		}
		return null;
	}

	// 精品成本 精品成本=精品单价*（计领数量-已领数量）+精品成本*已领数量
//	private Property getCostRecord(VehicleSaleContractPresentVary present, short status) {
//		Property p = new Property();
//		p.setLabel("精品成本");
//		double outCome = 0, oriOutCome = 0;
//
//		outCome = Tools.toDouble(present.getPosPrice())
//				* (Tools.toDouble(present.getPlanQuantity()) - Tools.toDouble(present.getGetQuantity()))
//				+ Tools.toDouble(present.getCostRecord()) * Tools.toDouble(present.getGetQuantity());
//		oriOutCome = Tools.toDouble(present.getPosPrice())
//				* (Tools.toDouble(present.getOriPlanQuantity()) - Tools.toDouble(present.getGetQuantity()))
//				+ Tools.toDouble(present.getCostRecord()) * Tools.toDouble(present.getGetQuantity());
//		if (status == STATUS_NEW) {
//			p.setValue(Tools.toCurrencyStr(outCome));
//			p.setShownOnSlaveBoard(true);
//			p.setValueColour(Color.BLACK.getCode());
//			return p;
//		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
//			if (compareEqual(outCome, oriOutCome)) {
//				p.setValue(Tools.toCurrencyStr(outCome));
//				p.setValueColour(Color.BLACK.getCode());
//			} else {
//				p.setValue(formatDiff(outCome, oriOutCome));
//				p.setValueColour(Color.RED.getCode());
//			}
//			p.setShownOnSlaveBoard(true);
//			return p;
//		}
//		return null;
//	}
	
	// 精品成本 精品成本=精品单价*（计领数量-已领数量）+精品成本*已领数量
	private short createCostRecord(MobileBoardBuffered board, short sn, VehicleSaleContractPresentVary present) {
		String lable = "精品成本";
		String fieldCode = "costRecord";
		String value = null;
		Integer valueColour = null;
		short status = Tools.toShort(present.getAbortStatus());
		
		double outCome = 0, oriOutCome = 0;

		outCome = Tools.toDouble(present.getPosPrice())
				* (Tools.toDouble(present.getPlanQuantity()) - Tools.toDouble(present.getGetQuantity()))
				+ Tools.toDouble(present.getCostRecord()) * Tools.toDouble(present.getGetQuantity());
		oriOutCome = Tools.toDouble(present.getPosPrice())
				* (Tools.toDouble(present.getOriPlanQuantity()) - Tools.toDouble(present.getGetQuantity()))
				+ Tools.toDouble(present.getCostRecord()) * Tools.toDouble(present.getGetQuantity());
		if (status == STATUS_NEW) {
			value = Tools.toCurrencyStr(outCome);
		}else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
			if (compareEqual(outCome, oriOutCome)) {
				value = Tools.toCurrencyStr(outCome);
			}else{
				value = formatDiff(outCome, oriOutCome);
				valueColour = Color.RED.getCode();
			}
		}
		createProperty(board, fieldCode, sn++, null, null, true, lable, value, null,
				null, valueColour, null, null, AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}

	// 备注
	private Property getRemark(VehicleSaleContractPresentVary present, short status) {
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

	// 终止原因
	private Property getAbortComment(VehicleSaleContractPresentVary present, short status) {
		Property p = new Property();
		p.setLabel("终止原因");

		if (StringUtils.isEmpty(present.getAbortComment())) {
			return null;
		}

		p.setValue(present.getAbortComment());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Double> getPresentSummary(VehicleSaleContractDetailVary contractDetailVary){
//		StringBuffer buf = new StringBuffer();
//		buf.append("select sum(case when abortStatus<>10 then income end) as  income,").append("\r\n");
//		buf.append("sum(case when abortStatus<>10 then outCome end) as  outcome,").append("\r\n");
//		buf.append("sum(case when detailVaryId is null then income else oriIncome end) as oriIncome,").append("\r\n");
//		buf.append("sum(case when detailVaryId is null then outCome else oriOutCome end) as oriOutcome,").append("\r\n");
//		buf.append("sum(realCost) as realCost").append("\r\n");
//		buf.append("from VwVehicleSaleContractPresentVaryMerge where contractDetailId = ?");
//		List<Object> data1 = (List<Object>) dao.findByHql(buf.toString(),contractDetailVary.getContractDetailId());
//		double income=0, outcome=0,oriIncome=0,oriOutcome = 0,realCost = 0;
//		if(data1!=null && data1.size()==1){
//			Object[] row = (Object[])data1.get(0);
//			income = row[0]!=null?(double)row[0]:0;
//			outcome = row[1]!=null?(double)row[1]:0;
//			oriIncome = row[2]!=null?(double)row[2]:0;
//			oriOutcome = row[3]!=null?(double)row[3]:0;
//			realCost= row[4]!=null?(double)row[4]:0;
//		}
		
		List<Map<String,Object>>  data1=  saleContractsVaryDao.getPresentVaryMerge(contractDetailVary.getContractDetailId(),contractDetailVary.getDetailVaryId());
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
	// 精品售价
	@SuppressWarnings("unchecked")
	private double getPresentIncome(VehicleSaleContractDetailVary contractDetailVary) {
		double income = 0.00D;
		String hql = "select sum(isnull(income,0)) from VehicleSaleContractPresentVary where detailVaryId = ? ";
		List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
		if (list != null && list.size() > 0) {
			income = Tools.toDouble(list.get(0));
		}
		return income;
	}

	// 精品成本
	@SuppressWarnings("unchecked")
	private double getPresentCost(VehicleSaleContractDetailVary contractDetailVary) {
		double cost = 0.00D;
		String hql = "select sum(isnull(costRecord,0)) from VehicleSaleContractPresentVary where detailVaryId = ? ";
		List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
		if (list != null && list.size() > 0) {
			cost = Tools.toDouble(list.get(0));
		}
		return cost;
	}
	**/

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
