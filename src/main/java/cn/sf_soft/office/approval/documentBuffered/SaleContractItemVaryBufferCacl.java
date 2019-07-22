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
 * 合同变更-改装
 * 
 * @author caigx
 *
 */
@Service("saleContractItemVaryBufferCacl")
public class SaleContractItemVaryBufferCacl extends DocumentBufferCalc {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleContractItemVaryBufferCacl.class);
	private static String bufferVersion = "20161128.0";
	private static String bufferClass = SaleContractItemVaryBufferCacl.class.getSimpleName();

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
	private SaleContractItemBufferCacl saleContractItemBufferCacl;

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

	private List<VehicleSaleContractItem> getNochangeList(VehicleSaleContractDetailVary contractDetailVary) {
		String hql = "from VehicleSaleContractItem as a  where not exists (select 1 from VehicleSaleContractItemVary as b  where  a.saleContractItemId = b.saleContractItemId and b.contractDetailId = ? and  b.detailVaryId = ?) and a.contractDetailId = ?";
		List<VehicleSaleContractItem> list = (List<VehicleSaleContractItem>) dao.findByHql(hql,
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
		List<VehicleSaleContractItemVary> itemVaryList = saleContractsVaryDao
				.getItemVaryByDetailVaryId(contractDetailVary.getDetailVaryId());
		List<VehicleSaleContractItem> nochangeList = getNochangeList(contractDetailVary);
		if ((itemVaryList == null || itemVaryList.size() == 0) && (nochangeList == null || nochangeList.size() == 0)) {
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
		for (int i = 0; i < itemVaryList.size(); i++) {
			VehicleSaleContractItemVary item = itemVaryList.get(i);
			sn = createDetailField(sn, n, summaryBoard, item);
			n++;
		}

		for (int i = 0; i < nochangeList.size(); i++) {
			VehicleSaleContractItem item = nochangeList.get(i);
			sn = saleContractItemBufferCacl.createDetailField(sn, n, summaryBoard, item);
			n++;
		}
		createTail(summaryBoard,sn++);//创建Tail
		dao.save(summaryBoard);
		return summaryBoard;
	}
	
	
	private String getTitle(VehicleSaleContractDetailVary contractDetailVary,boolean hasPopedom) {
		Map<String,Double> result =  getItemSummary(contractDetailVary);
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
		
		String titleStr ="改装";
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
	public short createDetailField(short sn, int n, MobileBoardBuffered board, VehicleSaleContractItemVary item) {
		String abortStatusStr = "";
		if (STATUS_NEW == item.getAbortStatus()) {
			abortStatusStr = "新增";
		} else if (STATUS_MODIFY == item.getAbortStatus()) {
			abortStatusStr = "修改";
		} else if (STATUS_ABORT == item.getAbortStatus()) {
			abortStatusStr = "终止";
		}
		String title = String.format("%s %s", abortStatusStr, item.getItemName());
		if (vehicleAbortStatus == STATUS_NEW) {
			title = String.format("%s", item.getItemName());// 新增不用显示状态
		}

		
		String label = String.format("%d、%s", n + 1, title);
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

		Property abortComment = getAbortComment(item, item.getAbortStatus());
		if (abortComment != null) {
			saveProperty(board, "abortComment",   sn++, abortComment);
		}

		return sn;
	}

	// 项目编码
	private Property getItemNo(VehicleSaleContractItemVary item, short status) {
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
	private Property getIncome(VehicleSaleContractItemVary item, short status) {
		Property p = new Property();
		p.setLabel("改装价格");
		if (status == STATUS_NEW) {
			if (item.getIncome() == null) {
				return null;
			}

			p.setValue(Tools.toCurrencyStr(item.getIncome()));
			p.setShownOnSlaveBoard(true);
			p.setValueColour(Color.BLACK.getCode());
			return p;
		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
			if (item.getIncome() == null) {
				return null;
			}
			if (compareEqual(item.getIncome(), item.getOriIncome())) {
				p.setValue(Tools.toCurrencyStr(item.getIncome()));
				p.setValueColour(Color.BLACK.getCode());
			} else {
				p.setValue(formatDiff(item.getIncome(), item.getOriIncome()));
				// p.setValue(String.format("%s【%s】",
				// Tools.toCurrencyStr(item.getIncome()),
				// Tools.toCurrencyStr(item.getOriIncome())));
				p.setValueColour(Color.RED.getCode());
			}

			p.setShownOnSlaveBoard(true);
			return p;
		}
		return null;
	}

	// 改装成本
//	private Property getCost(VehicleSaleContractItemVary item, short status) {
//		Property p = new Property();
//		p.setLabel("改装成本");
//		if (status == STATUS_NEW) {
//			if (item.getItemCost() == null) {
//				return null;
//			}
//
//			p.setValue(Tools.toCurrencyStr(item.getItemCost()));
//			p.setShownOnSlaveBoard(true);
//			p.setValueColour(Color.BLACK.getCode());
//			return p;
//		} else if (status == STATUS_MODIFY || status == STATUS_ABORT) {
//			if (item.getItemCost() == null) {
//				return null;
//			}
//			if (compareEqual(item.getItemCost(), item.getOriItemCost())) {
//				p.setValue(Tools.toCurrencyStr(item.getItemCost()));
//				p.setValueColour(Color.BLACK.getCode());
//			} else {
//				p.setValue(formatDiff(item.getItemCost(), item.getOriItemCost()));
//				// p.setValue(String.format("%s【%s】",
//				// Tools.toCurrencyStr(item.getItemCost()),
//				// Tools.toCurrencyStr(item.getOriItemCost())));
//				p.setValueColour(Color.RED.getCode());
//			}
//
//			p.setShownOnSlaveBoard(true);
//			return p;
//		}
//		return null;
//	}
	
	
	// 改装成本
	private short createCost(MobileBoardBuffered board, short sn, VehicleSaleContractItemVary item) {
		String lable = "改装成本";
		String fieldCode = "cost";
		String value = null;
		Integer valueColour = null;
		short status = Tools.toShort(item.getAbortStatus());
		if (item.getItemCost() == null) {
			return sn;
		}
		
		if (status == STATUS_NEW) {
			value = Tools.toCurrencyStr(item.getItemCost());
		} else if (status == STATUS_MODIFY || status == STATUS_ABORT){
			if (compareEqual(item.getItemCost(), item.getOriItemCost())){
				value = Tools.toCurrencyStr(item.getItemCost());
			}else{
				value =formatDiff(item.getItemCost(), item.getOriItemCost());
				valueColour = Color.RED.getCode();
			}
			
		}

		createProperty(board, fieldCode, sn++, null, null, true, lable, value, null,
				null, valueColour, null, null, AccessPopedom.GeneralVehicle.CHECK_VEHICLE_COST);
		return sn;
	}

	// 备注
	private Property getComment(VehicleSaleContractItemVary item, short status) {
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

	// 终止原因
	private Property getAbortComment(VehicleSaleContractItemVary item, short status) {
		Property p = new Property();
		p.setLabel("终止原因");

		if (StringUtils.isEmpty(item.getAbortComment())) {
			return null;
		}

		p.setValue(item.getAbortComment());
		p.setShownOnSlaveBoard(true);
		p.setValueColour(Color.BLACK.getCode());
		return p;

	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String,Double> getItemSummary(VehicleSaleContractDetailVary contractDetailVary){
//		StringBuffer buf = new StringBuffer();
//		buf.append("select sum(case when abortStatus<>10 then income end) as  income,").append("\r\n");
//		buf.append("sum(case when abortStatus<>10 then outCome end) as  outcome,").append("\r\n");
//		buf.append("sum(case when detailVaryId is null then income else oriIncome end) as oriIncome,").append("\r\n");
//		buf.append("sum(case when detailVaryId is null then outCome else oriOutCome end) as oriOutcome").append("\r\n");
//		buf.append("from VwVehicleSaleContractItemVaryMerge where contractDetailId = ?");
//		List<Object> data1 = (List<Object>) dao.findByHql(buf.toString(),contractDetailVary.getContractDetailId());
//		double income=0, outcome=0,oriIncome=0,oriOutcome = 0,realCost = 0;
//		if(data1!=null && data1.size()==1){
//			Object[] row = (Object[])data1.get(0);
//			income = row[0]!=null?(double)row[0]:0;
//			outcome = row[1]!=null?(double)row[1]:0;
//			oriIncome = row[2]!=null?(double)row[2]:0;
//			oriOutcome = row[3]!=null?(double)row[3]:0;
//		}
		
		List<Map<String,Object>>  data1=  saleContractsVaryDao.getItemVaryMerge(contractDetailVary.getContractDetailId(),contractDetailVary.getDetailVaryId());
		double income=0, outcome=0,oriIncome=0,oriOutcome = 0,realCost = 0;
		if(data1!=null && data1.size()==1){
			Map<String,Object> row = data1.get(0);
			income = row.get("income")!=null?(double)row.get("income"):0;
			outcome = row.get("outcome")!=null?(double)row.get("outcome"):0;
			oriIncome = row.get("oriIncome")!=null?(double)row.get("oriIncome"):0;
			oriOutcome = row.get("oriOutcome")!=null?(double)row.get("oriOutcome"):0;
//			realCost = row.get("realCost")!=null?(double)row.get("realCost"):0;
		}
		
		//实际改装
		if(StringUtils.isNotEmpty(contractDetailVary.getVehicleId())){
			String hql = "select sum(itemCost) as realCost from VehicleConversionDetail where vehicleId = ? and status IN (1,2,30,50)";
			List<Double> list = (List<Double>) dao.findByHql(hql, contractDetailVary.getVehicleId());
			if(list!=null && list.size()>0){
				realCost = Tools.toDouble(list.get(0));
			}
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
	// 改装价格
	@SuppressWarnings("unchecked")
	private double getItemIncome(VehicleSaleContractDetailVary contractDetailVary) {
		double income = 0.00D;
		String hql = "select sum(isnull(income,0)) from VehicleSaleContractItemVary where detailVaryId = ? ";
		List<Double> sumIncomeList = (List<Double>) dao.findByHql(hql, contractDetailVary.getDetailVaryId());
		if (sumIncomeList != null && sumIncomeList.size() > 0) {
			income = Tools.toDouble(sumIncomeList.get(0));
		}
		return income;
	}

	// 改装成本
	@SuppressWarnings("unchecked")
	private double getItemCost(VehicleSaleContractDetailVary contractDetailVary) {

		double cost = 0.00D;
		String hql = "select sum(isnull(itemCost,0)) from VehicleSaleContractItemVary where detailVaryId = ? ";
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
