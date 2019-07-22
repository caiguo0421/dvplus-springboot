package cn.sf_soft.office.approval.documentBuffered;

import java.math.BigDecimal;
import java.util.List;


import org.springframework.stereotype.Service;

import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentBuffered;
import cn.sf_soft.office.approval.documentBuffered.PartPurchasePlanBufferCacl.Percent;
import cn.sf_soft.office.approval.service.impl.PartPurchasePlanBuf.PartPlanType;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.parts.inventory.model.PartPurchasePlanDetail;

/**
 * 配件采购计划明细
 * 
 * @author ZHJ
 *
 */
@Service("partPurchasePlanDetailBufferCalc")
public class PartPurchasePlanDetailBufferCalc extends ApprovalDocumentCalc {

	private int documentClassId = 10000;
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PartPurchasePlanDetailBufferCalc.class);

	private static String moduleId = "151010";

	private PartPlanType planType;
	

	public PartPlanType getPlanType() {
		return planType;
	}

	public void setPlanType(PartPlanType planType) {
		this.planType = planType;
	}

	@Override
	protected void computeStaticFields(MobileDocumentBuffered doc) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void computeDynamicFields(MobileDocumentBuffered doc) {
		// TODO Auto-generated method stub

	}
	
	

	public MobileBoardBuffered computeFields(
			List<PartPurchasePlanDetail> listDetails, int i,
			boolean isFirstVehicleExpanded, Percent percent,
			PartPlanType planType,double planPrice){
		if(listDetails==null){
			return null;
		}
		double totalPrice=0.0;
		for(PartPurchasePlanDetail pppd:listDetails){
			totalPrice+=Tools.toDouble(pppd.getCost());
		}
		String perentValue=Tools.toCurrencyStr(totalPrice/planPrice*100);
		String tagStr = "";
		String totalMoney="";
		if(totalPrice<100){
			totalMoney=String.valueOf(totalPrice);
		}else{
			totalMoney=Tools.toCurrencyStr(totalPrice);
		}
		StringBuffer strBuffer=new StringBuffer();
		if (percent== Percent.HUNDRED_PERCENT) {
			tagStr = "";
		}
		else
		{
			tagStr=strBuffer.append("占比").append(perentValue).append("%").
					append("   ").append(totalMoney).append("元").toString();
		}
		this.planType = planType;
		if(tagStr.isEmpty()){
			return null;
		}
		MobileBoardBuffered board = new MobileBoardBuffered();
		String title = String.format("%s",tagStr);
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(title); 
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(false);
		createBoardTitle(board,gson.toJson(baseBoardTitle),null,null,null);

		DocTitle docTitle = new DocTitle(title);
		board.setDocTitle(gson.toJson(docTitle));
		short sn = 1;
		int n=1;
		for(int j=0;j<listDetails.size();j++){
			PartPurchasePlanDetail pppd=listDetails.get(j);
			sn=createDetailField(sn,n,board,pppd,this.planType);
			n++;
		}
		//createTail(board, sn++);
		dao.save(board);
		return board;

	}

	public MobileBoardBuffered createDetailFieldToSubboard(int index, PartPurchasePlanDetail detail, PartPlanType planType) {
		MobileBoardBuffered board = new MobileBoardBuffered();
		String title = String.format("配件%d: %s", index+1, detail.getPartName());
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(title);
		baseBoardTitle.setCollapsable(true);
		baseBoardTitle.setDefaultExpanded(false);
		createBoardTitle(board,gson.toJson(baseBoardTitle),null,null,null);

		DocTitle docTitle = new DocTitle(title);
		board.setDocTitle(gson.toJson(docTitle));

		short sn = 1;
		int n = 1;
		createDetailField(sn, n, board, detail, planType);
		return board;
	}

	public short createDetailField(short sn, int n, MobileBoardBuffered board,
			PartPurchasePlanDetail pppd,PartPlanType planType) {
		this.planType=planType;
		String producingArea=pppd.getProducingArea()==null?"":pppd.getProducingArea();
		String label=String.format("%d、%s",n, pppd.getPartNo()+"   "+producingArea);
		Integer background=Color.GRAY.getCode();
		createLable(board, sn++, true,true,true, label, null,null, background, null, null);
		double approveQuantity = pppd.getApproveQuantity() == null ? 0 : pppd
				.getApproveQuantity();
		double supplierQuantity = pppd.getSupplierQuantity() == null ? 0 : pppd
				.getSupplierQuantity();		
		double unSendQuantity = approveQuantity - supplierQuantity;
		double orderQuantity=pppd.getOrderQuantity()==null?0:pppd.getOrderQuantity();
		double prOrderCount=pppd.getPreOrderCount()==null?0:pppd.getPreOrderCount();
		double costRef=pppd.getCostRef()==null?0:pppd.getCostRef();
		double planPrice=pppd.getPlanPrice()==null?0:pppd.getPlanPrice();
		double stockQuantityRecord=pppd.getStockQuantityRecord()==null?0:pppd.getStockQuantityRecord();
		double quantityWay=pppd.getQuantityWay()==null?0:pppd.getQuantityWay();
		double planQuantity=pppd.getPlanQuantity()==null?0:pppd.getPlanQuantity();
		double adviceQuantity=pppd.getAdviceQuantity()==null?0:pppd.getAdviceQuantity();
		String unit=pppd.getUnit()==null?"":pppd.getUnit();
		sn=createPartPurchaseDetailsProperties(board, sn, pppd.getPartName(),unit,
				pppd.getPartNo(), pppd.getProducingArea(),
				pppd.getSupplierName(), pppd.getManagementState(),
				costRef, planPrice,adviceQuantity, planQuantity,stockQuantityRecord, 
				quantityWay,unSendQuantity,orderQuantity+prOrderCount ,
				pppd.getCost(), pppd.getRemark());
		return sn;
	}

	private short createPartPurchaseDetailsProperties(MobileBoardBuffered board, 
			short sn, String partName,String unit,String partNo, String producingArea, 
			String supplierName,String managementState, Double costRef, Double planPrice,
			Double adviceQuantity, Double planQuantity,
			Double stockQuantityRecord, Double quantityWay,
			double unSendQuantity,double orderQuantity, Double cost, String remark) {
	
//		if(managementState!=null&& managementState.equals("不库存")&&
//		(quantityWay+stockQuantityRecord+unSendQuantity+planQuantity-orderQuantity)>0)
//		{
//			createProperty(board, "partName", sn++, false, true, true, "配件",
//	           partName, null, null, null /* Color.RED.getCode() */, null, null);
//			createProperty(board, "planQuantity", sn++, false, true, true, "数量",
//					Tools.toCurrencyStr(planQuantity)+unit, null, null, null /* Color.RED.getCode() */, null, null);
//		}else{
//			createProperty(board, "partName", sn++, false, true, true, "配件",
//					partName, null, null, null, null, null);
//			createProperty(board, "planQuantity", sn++, false, true, true, "数量",
//					Tools.toCurrencyStr(planQuantity)+unit, null, null, null, null, null);
//		}

		createProperty(board, "partName", sn++, false, true, true, "配件",
				partName, null, null, null, null, null);
		createProperty(board, "partNo", sn++, false, true, true, "配件编号",
				partNo, null, null, null, null, null);

		createProperty(board, "stockQuantity", sn++, true, true, false, "库存",
				Tools.toCurrencyStr(stockQuantityRecord), null, null, null, null,
				null);
		createProperty(board, "quantityWay", sn++, true, true, false, "在途数量",
				Tools.toCurrencyStr(quantityWay), null, null, null, null, null);
		createProperty(board, "unSendQuantity", sn++, true, true, false,
				"未发货数量", Tools.toCurrencyStr(unSendQuantity), null, null, null,
				null, null);
		createProperty(board, "adviceQuantity", sn++, false, true, true, "最适",
				Tools.toCurrencyStr(adviceQuantity)+unit, null, null, null, null, null);
		createProperty(board, "planQuantity", sn++, false, true, true, "计划",
				Tools.toCurrencyStr(planQuantity)+unit, null, null, null, null, null);
		createProperty(board, "planPrice", sn++, false, true, true,
				"单价", String.valueOf(planPrice),
				null,null,Color.RED.getCode(),null,null);
		createProperty(board, "cost", sn++, false, true, true, "金额",
				String.valueOf(cost), null, null, Color.RED.getCode(), null, null);
		createProperty(board, "unSendQuantity", sn++, true, true, false,
				"备注", remark, null, null, null,
				null, null);

//		createProperty(board, "planPrice", sn++, false, true, true,
//				"参考价", String.valueOf(costRef),
//				null,null,Color.RED.getCode(),null,null);

//		String  PlanPrice = String.valueOf(planPrice);
//		String CostRef = String.valueOf(costRef);
//		if(planPrice>=100){
//			PlanPrice=Tools.toCurrencyStr(planPrice);
//		}else{
//			PlanPrice=String.valueOf(planPrice);
//		}
//		if(costRef>=100){
//			CostRef=Tools.toCurrencyStr(costRef);
//		}else{
//			CostRef=String.valueOf(costRef);
//		}
//		if (this.planType != PartPlanType.BaseDfOrder
//				&& this.planType != PartPlanType.DfRushOrder) {
//
//			if (compareTo(planPrice, costRef)) {
//
//				createProperty(board, "planPrice", sn++, false, true, true,
//						"单价", PlanPrice,
//						null,null,Color.RED.getCode(),null,null);
//				createProperty(board, "planPrice", sn++, false, true, true,
//						"参考价", CostRef,
//						null,null,Color.RED.getCode(),null,null);
//			} else {
//				createProperty(board, "planPrice", sn++, false, true, true,
//						"单价",PlanPrice,
//						null,null,Color.RED.getCode(),null,null);
//				createProperty(board, "planPrice", sn++, false, true, true,
//						"参考价",CostRef,
//						null,null,Color.RED.getCode(),null,null);
//			}
//
//		}else{
//			if(costRef==0)
//			{
//				createProperty(board, "planPrice", sn++, false, true, true,
//						"单价", PlanPrice, null,null,Color.RED.getCode(),null,null);
//			}else
//			{
//				createProperty(board, "planPrice", sn++, false, true, true,
//						"单价", PlanPrice,
//						null,null,Color.RED.getCode(),null,null);
//				createProperty(board, "planPrice", sn++, false, true, true,
//						"参考价", CostRef,
//						null,null,Color.RED.getCode(),null,null);
//			}
//
//		}
		
//		if (this.planType != PartPlanType.BaseDfOrder
//				&& this.planType != PartPlanType.DfRushOrder) {
//			createProperty(board, "supplierName", sn++, false, true, true,
//					"供应商", supplierName, null, null, null, null, null);
//		}
		
		
//		if (managementState != null && !managementState.isEmpty()) {
//			createProperty(board, "managementState", sn++, false, true, true,
//					"库存策略", managementState, null, null, null, null, null);
//		}
//		if(stockQuantityRecord!=0){
			
//		createProperty(board, "stockQuantity", sn++, true, true, false, "库存数量",
//				Tools.toCurrencyStr(stockQuantityRecord), null, null, null, null,
//				null);
//		}
//		if(quantityWay!=null&&quantityWay!=0){
//			createProperty(board, "quantityWay", sn++, true, true, false, "在途数量",
//					Tools.toCurrencyStr(quantityWay), null, null, null, null, null);
//		}
//		if(unSendQuantity!=0){
//			createProperty(board, "unSendQuantity", sn++, true, true, false,
//					"未发货数量", Tools.toCurrencyStr(unSendQuantity), null, null, null,
//					null, null);
//		}
//		if(orderQuantity!=0){
//		createProperty(board, "orderQuantity", sn++, false, true, true, "客户预定数量",
//				Tools.toCurrencyStr(orderQuantity), null, null, null, null, null);
//		}
		return sn;

	}
	
	public Boolean compareTo(Double m,Double n){
		BigDecimal a=new BigDecimal(m);
		BigDecimal b=new BigDecimal(n);
		int c= a.compareTo(b);
		if(c==1)
		{
			return false;
		}
		if(c==0||c==-1)
		{
			return true;
		}
		return null;
	}
}
