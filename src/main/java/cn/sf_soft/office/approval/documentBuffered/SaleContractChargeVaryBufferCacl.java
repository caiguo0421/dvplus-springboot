package cn.sf_soft.office.approval.documentBuffered;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.sf_soft.office.approval.model.VehicleSaleContractChargeVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.office.approval.ui.model.Property;

/**
 * 合同变更-费用明细
 * 
 * @author caigx
 *
 */
@Service("saleContractChargeVaryBufferCacl")
public class SaleContractChargeVaryBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractChargeVaryBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractChargeVaryBufferCacl.class.getSimpleName();

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
	private SaleContractChargeBufferCacl saleContractChargeBufferCacl;

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
	private List<VehicleSaleContractCharge> getNoChangeList(VehicleSaleContractDetailVary contractDetailVary) {
		String hql = "from VehicleSaleContractCharge as a where not exists (select 1 from VehicleSaleContractChargeVary as b  where a.saleContractChargeId = b.saleContractChargeId and b.contractDetailId = ? and  b.detailVaryId = ?) and a.contractDetailId = ?";
		List<VehicleSaleContractCharge> list = (List<VehicleSaleContractCharge>) dao.findByHql(hql,
				contractDetailVary.getContractDetailId(),contractDetailVary.getDetailVaryId(), contractDetailVary.getContractDetailId());
		return list;
	}

	/**
	 * 计算字段
	 * 
	 * @param contractDetailVary
	 * @return
	 */
	public MobileBoardBuffered computeFields(VehicleSaleContractDetailVary contractDetailVary, String vehicleTitle) {
		List<VehicleSaleContractChargeVary> chargeVaryList = saleContractsVaryDao
				.getChargeVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
		List<VehicleSaleContractCharge> noChangeList = getNoChangeList(contractDetailVary);
		if ((chargeVaryList == null || chargeVaryList.size() == 0)
				&& (noChangeList == null || noChangeList.size() == 0)) {
			return null;
		}
		// 车辆状态
		this.setVehicleAbortStatus(contractDetailVary.getAbortStatus());

		MobileBoardBuffered summaryBoard = new MobileBoardBuffered();
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(getTitle(contractDetailVary, true));
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(false);
		createBoardTitle(summaryBoard, gson.toJson(baseBoardTitle), null, null, AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		// 无权限的
		baseBoardTitle.setTitle(getTitle(contractDetailVary, false));
		createBoardTitle(summaryBoard, gson.toJson(baseBoardTitle), null, null, null);

		DocTitle docTitle = new DocTitle(getTitle(contractDetailVary, true));
		summaryBoard.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		int n = 0;
		for (int i = 0; i < chargeVaryList.size(); i++) {
			VehicleSaleContractChargeVary charge = chargeVaryList.get(i);
			sn = createDetailField(sn, n, summaryBoard, charge);
			n++;
		}

		for (int i = 0; i < noChangeList.size(); i++) {
			VehicleSaleContractCharge charge = noChangeList.get(i);
			sn = saleContractChargeBufferCacl.createDetailField(sn, n, summaryBoard, charge);
			n++;
		}
		createTail(summaryBoard,sn++);//创建Tail
		dao.save(summaryBoard);
		return summaryBoard;
	}
	
	private String getTitle(VehicleSaleContractDetailVary contractDetailVary,boolean hasPopedom) {
		Map<String,Double> result =  getChargeSummary(contractDetailVary);
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
		
		String titleStr ="费用";
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
	public short createDetailField(short sn, int n, MobileBoardBuffered board, VehicleSaleContractChargeVary charge) {
		String abortStatusStr = "";
		if (STATUS_NEW == charge.getAbortStatus()) {
			abortStatusStr = "新增";
		} else if (STATUS_MODIFY == charge.getAbortStatus()) {
			abortStatusStr = "修改";
		} else if (STATUS_ABORT == charge.getAbortStatus()) {
			abortStatusStr = "终止";
		}
		String title = String.format("%s %s", abortStatusStr, charge.getChargeName());
		if (vehicleAbortStatus == STATUS_NEW) {
			title = String.format("%s", charge.getChargeName());// 新增不用显示状态
		}

		String label = String.format("%d、%s", n + 1, title);
		Integer background = Color.GRAY.getCode();
		createLable(board,sn++,true,true,true,label,null,null,background,null,null);

		// 费用收款
		Property income = getIncome(charge, charge.getAbortStatus());
		if (income != null) {
			saveProperty(board, "income",   sn++, income);
		}

		//费用支出
		sn = createChargePf(board,sn,charge);

		Property remark = getRemark(charge, charge.getAbortStatus());
		if (remark != null) {
			saveProperty(board, "remark",   sn++, remark);
		}

		Property abortComment = getAbortComment(charge, charge.getAbortStatus());
		if (abortComment != null) {
			saveProperty(board, "abortComment",   sn++, abortComment);
		}

		return sn;
	}

	// 费用收款
	private Property getIncome(VehicleSaleContractChargeVary charge, short status) {
		Property p = new Property();
		p.setLabel("费用收款");
		if (status == STATUS_NEW) {
			if (charge.getIncome() == null) {
				return null;
			}

			p.setValue(Tools.toCurrencyStr(charge.getIncome()));
			p.setShownOnSlaveBoard(true);
			p.setValueColour(Color.BLACK.getCode());
			return p;
		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
			if (charge.getIncome() == null) {
				return null;
			}
			if(compareEqual(charge.getIncome(), charge.getOriIncome())){
				p.setValueColour(Color.BLACK.getCode());
			} else {
				p.setValueColour(Color.RED.getCode());
			}
			p.setValue(formatDiff(charge.getIncome(), charge.getOriIncome()));
			p.setShownOnSlaveBoard(true);
			return p;
		}
		return null;
	}

//	// 费用支出 单项费用支出取值要比较预估金额和实际金额，哪个大取哪个
//	private Property getChargePf(VehicleSaleContractChargeVary charge, short status) {
//		Property p = new Property();
//		p.setLabel("费用支出");
//		double chargePf = Tools.toDouble(charge.getChargePf())>Tools.toDouble(charge.getChargeCost())?Tools.toDouble(charge.getChargePf()):Tools.toDouble(charge.getChargeCost());
//		double oriChargePf = Tools.toDouble(charge.getOriChargePf())>Tools.toDouble(charge.getOriChargeCost())?Tools.toDouble(charge.getOriChargePf()):Tools.toDouble(charge.getOriChargeCost());
//		if (status == STATUS_NEW) {
//			p.setValue(Tools.toCurrencyStr(chargePf));
//			p.setShownOnSlaveBoard(true);
//			p.setValueColour(Color.BLACK.getCode());
//			return p;
//		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
//			if (compareEqual(chargePf, oriChargePf)) {
//				p.setValueColour(Color.BLACK.getCode());
//			} else {
//				
//				p.setValueColour(Color.RED.getCode());
//			}
//			p.setValue(formatDiff(chargePf, oriChargePf));
//			p.setShownOnSlaveBoard(true);
//			return p;
//		}
//		return null;
//	}
	
	// 费用支出 单项费用支出取值要比较预估金额和实际金额，哪个大取哪个
	private short createChargePf(MobileBoardBuffered board, short sn, VehicleSaleContractChargeVary charge) {
		String lable = "费用支出";
		String fieldCode = "chargePf";
		String value = null;
		Integer valueColour = null;
		short status = Tools.toShort(charge.getAbortStatus());

		double chargePf = Tools.toDouble(charge.getChargePf()) > Tools.toDouble(charge.getChargeCost()) ? Tools
				.toDouble(charge.getChargePf()) : Tools.toDouble(charge.getChargeCost());
		double oriChargePf = Tools.toDouble(charge.getOriChargePf()) > Tools.toDouble(charge.getOriChargeCost()) ? Tools
				.toDouble(charge.getOriChargePf()) : Tools.toDouble(charge.getOriChargeCost());

		if (status == STATUS_NEW) {
			value = Tools.toCurrencyStr(chargePf);
		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
			if (!compareEqual(chargePf, oriChargePf)) {
				valueColour = Color.RED.getCode();
			}
			value = formatDiff(chargePf, oriChargePf);
		}

		createProperty(board, fieldCode, sn++, null, null, true, lable, value, null, null, valueColour, null, null,
				AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}

	// 备注
	private Property getRemark(VehicleSaleContractChargeVary charge, short status) {
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

	// 终止原因
	private Property getAbortComment(VehicleSaleContractChargeVary charge, short status) {
		Property p = new Property();
		p.setLabel("终止原因");

		if (StringUtils.isEmpty(charge.getAbortComment())) {
			return null;
		}

		p.setValue(charge.getAbortComment());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}

	
	@SuppressWarnings("unchecked")
	public Map<String,Double> getChargeSummary(VehicleSaleContractDetailVary contractDetailVary){
//		StringBuffer buf = new StringBuffer();
//		buf.append("select sum(case when abortStatus<>10 then income end) as  income,").append("\r\n");
//		buf.append("sum(case when abortStatus<>10 then outCome end) as  outcome,").append("\r\n");
//		buf.append("sum(case when detailVaryId is null then income else oriIncome end) as oriIncome,").append("\r\n");
//		buf.append("sum(case when detailVaryId is null then outCome else oriOutCome end) as oriOutcome,").append("\r\n");
//		buf.append("sum(realCost) as realCost").append("\r\n");
//		buf.append("from VwVehicleSaleContractChargeVaryMerge where contractDetailId = ?");
//		List<Object> data1 = (List<Object>) dao.findByHql(buf.toString(),contractDetailVary.getContractDetailId());
		
		List<Map<String,Object>>  data1=  saleContractsVaryDao.getChargeVaryMerge(contractDetailVary.getContractDetailId(),contractDetailVary.getDetailVaryId());
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
	// 费用收款
	private double getChargeIncome(VehicleSaleContractDetailVary contractDetailVary) {

		double income = 0.00D;
		String hql = "select sum(isnull(income,0)) from VehicleSaleContractChargeVary where detailVaryId = ? ";
		List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
		if (list != null && list.size() > 0) {
			income = Tools.toDouble(list.get(0));
		}
		return income;
	}

	// 费用支出
	private double getChargeCost(VehicleSaleContractDetailVary contractDetailVary) {

		double cost = 0.00D;
		String hql = "select sum(isnull(chargeCost,0)) from VehicleSaleContractChargeVary where detailVaryId = ? ";
		List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
		if (list != null && list.size() > 0) {
			cost = Tools.toDouble(list.get(0));
		}
		return cost;
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
