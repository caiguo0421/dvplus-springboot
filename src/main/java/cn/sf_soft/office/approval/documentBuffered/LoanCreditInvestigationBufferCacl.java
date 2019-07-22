package cn.sf_soft.office.approval.documentBuffered;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.sf_soft.office.approval.dao.LoanChargeReimbursementsDao;
import cn.sf_soft.office.approval.dao.LoanCreditInvestigationDao;
import cn.sf_soft.office.approval.ui.model.Color;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.model.AppBoardFieldType;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardField;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardTitle;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentBuffered;
import cn.sf_soft.office.approval.dao.PartPurchasePlanDao;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.service.impl.PartPurchasePlanBuf.PartPlanType;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.parts.inventory.model.PartPurchasePlanDetail;
import cn.sf_soft.parts.inventory.model.PartPurchasePlans;
/**
 * 消贷征信
 * @author ZQH
 *
 */
@Service("loanCreditInvestigationBufferCacl")
public class LoanCreditInvestigationBufferCacl extends ApprovalDocumentCalc {

	private int documentClassId=10000;
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoanCreditInvestigationBufferCacl.class);
	private static String bufferCalcVersion="2017113001.2";
	private static final String DF_PART_ORDER_TYPE="df_plan_order_type";
	private Map<Short,Object> planOrderType=new HashMap<Short,Object>();


	private static String moduleId="101810";

//	@Autowired
//	private LoanCreditInvestigationDao partPurchasePlanDao;

	@Autowired
	private LoanCreditInvestigationDetailBufferCalc detailBufferCalc;


	@Override
	public MobileDocumentBuffered compute(boolean onlyStatic, String moduleId, String documentNo){
		MobileDocumentBuffered bufferedDoc = loadDocumentBuffered(moduleId, documentNo);

		// 设置业务单据：车辆销售合同变更单
//		ApproveDocuments<?> approveDoc = (ApproveDocuments<?>)bufferedDoc.getBusiBill();

//		PartPurchasePlans purchasePlan=dao.get(PartPurchasePlans.class,approveDoc.getDocumentNo());
//		if (purchasePlan == null) {
//			throw new ServiceException("未找到审批单号对应的配件采购计划：" + approveDoc.getDocumentNo());
//		}
//		bufferedDoc.setRelatedObjects(new Object[]{purchasePlan});
		if (this.isNeedToCompute(bufferedDoc)) {
			computeStaticFields(bufferedDoc);
		}
		if(!onlyStatic){
			computeDynamicFields(bufferedDoc);
		}
		return bufferedDoc;
	}


	private short createPropertyString(MobileBoardBuffered board, short sn, Map<String, Object> doc, String field_name, String title){
		if(!doc.containsKey(field_name)){return sn;}
		createProperty(board, field_name, sn, false, true, true, title,
				(String)doc.get(field_name), null, null, null, null, null);
		return (short)(sn + 1);
	}

	private short createPropertyDouble(MobileBoardBuffered board, short sn, Map<String, Object> doc, String field_name, String title){
		if(!doc.containsKey(field_name)){return sn;}
		createProperty(board, field_name, sn, false, true, true, title,
				((Double)doc.get(field_name)).toString(), null, null, null, null, null);
		return (short)(sn + 1);
	}

	private short createPropertyInt(MobileBoardBuffered board, short sn, Map<String, Object> doc, String field_name, String title){
		if(!doc.containsKey(field_name)){return sn;}
		createProperty(board, field_name, sn, false, true, true, title,
				((Integer)doc.get(field_name)).toString(), null, null, null, null, null);
		return (short)(sn + 1);
	}

	@Override
	public void computeStaticFields(MobileDocumentBuffered doc) {
		if(null==doc){
			throw new ServiceException("无法计算空文档");
		}
		ApproveDocuments<?> document=(ApproveDocuments<?>)doc.getBusiBill();
		if(document==null){
			throw new ServiceException("无审批单据，无法计算");
		}
		if(!moduleId.equals(document.getModuleId())){
			throw new ServiceException(document.getDocumentNo()+"此单据非消贷征信单");
		}

		/**
		 * buffer_version 如果buffer_version 和 buffered_doc_version 一致,直接返回
		 */
		if(!this.isNeedToCompute(doc)){
			return;
		}

		if(null!=doc.getBoard()&&null!=doc.getBoard().getFields()&&doc.getBoard().getFields().size()>0){
			List<MobileBoardField> fields=doc.getBoard().getFields();
			for(int i=fields.size()-1;i>=0;i--){
				dao.delete(fields.get(i));
				fields.remove(i);
			}
			dao.flush();
		}

		if(null != doc.getBoard()&& null!=doc.getBoard().getTitles() && doc.getBoard().getTitles().size()>0){
			List<MobileBoardTitle> titles = doc.getBoard().getTitles();
			for(int i =titles.size()-1;i>=0;i--){
				dao.delete(titles.get(i));
				titles.remove(i);
			}
			dao.flush();
		}
		doc.setBufferCalcVersion(bufferCalcVersion);
		doc.setBusiBillId(document.getDocumentId());
		doc.setBufferCalcVersion(this.getBusiBillVersion(document));
		doc.setComputeTime(new Timestamp(System.currentTimeMillis()));

		// 消贷征信审批
		MobileBoardBuffered board=doc.getBoard();
		if(null==board){
			board=org.springframework.beans.BeanUtils.instantiate(MobileBoardBuffered.class);
			doc.setBoard(board);
			board.setDocument(doc);
		}
		String documentNo=document.getDocumentNo();
		// double planPrice=Tools.toDouble(purchasePlan.getPlanPrice());
		List<Map<String, Object>> docs = dao.getMapBySQL("SELECT * FROM vw_vehicle_loan_credit_investigation WHERE document_no = '"+documentNo+"'", null);
		if(docs.size() == 0){
			throw new ServiceException("未找到审批单号对应的消贷征信调查单：" + document.getDocumentNo());
		}
		Map<String, Object> loanCreditDoc = docs.get(0);

		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(documentNo+"   "+Tools.toCurrencyStr((Double)loanCreditDoc.get("loan_amount_tott"))+"元");
		baseBoardTitle.setCollapsable(false);
		baseBoardTitle.setDefaultExpanded(true);
		createBoardTitle(board, gson.toJson(baseBoardTitle), null, null, null);


		DocTitle docTitle=new DocTitle("消贷征信审批");
		board.setDocTitle(gson.toJson(docTitle));

		List<MobileBoardField> fields=board.getFields();
		if(null==fields){
			fields=new ArrayList<MobileBoardField>();
			board.setFields(fields);
		}
		short sn = 1;
		sn = createPropertyString(board, sn, loanCreditDoc, "budget_no", "消贷预算单");
		sn = createPropertyString(board, sn, loanCreditDoc, "loan_object_name", "客户名称");
		sn = createPropertyString(board, sn, loanCreditDoc, "loan_object_name", "贷款人");
//		sn = createPropertyString(board, sn, loanCreditDoc, "hr_province", "户口所在省");
//		sn = createPropertyString(board, sn, loanCreditDoc, "hr_city", "户口所在市");
//		sn = createPropertyString(board, sn, loanCreditDoc, "hr_area", "户口所在区");
//		sn = createPropertyString(board, sn, loanCreditDoc, "hr_address", "户口所在村镇");
//		sn = createPropertyString(board, sn, loanCreditDoc, "live_province", "现居所在省");
//		sn = createPropertyString(board, sn, loanCreditDoc, "live_city", "现居所在市");
//		sn = createPropertyString(board, sn, loanCreditDoc, "live_area", "现居所在区");
//		sn = createPropertyString(board, sn, loanCreditDoc, "live_address", "现居所在村镇");
		sn = createPropertyInt(board, sn, loanCreditDoc, "vehicle_quantity", "购车台数");
		sn = createPropertyDouble(board, sn, loanCreditDoc, "loan_amount_tot", "贷款金额");
//		sn = createPropertyString(board, sn, loanCreditDoc, "warranter_name", "担保人");
//		sn = createPropertyString(board, sn, loanCreditDoc, "warranter2_name", "担保人2");
		sn = createPropertyString(board, sn, loanCreditDoc, "affiliated_company_name", "挂靠单位");
		sn = createPropertyString(board, sn, loanCreditDoc, "investigate_man", "随同考察人");
		sn = createPropertyString(board, sn, loanCreditDoc, "investigate_suggestion", "调查人意见");


		List<Map<String, Object>> detailList = dao.getMapBySQL("SELECT a.*, b.meaning as status_meaning FROM vehicle_loan_credit_investigation_details a LEFT JOIN sys_flags b ON b.field_no = 'document_status' AND a.status = b.code WHERE a.document_no = '" + documentNo + "'", null);

		for(int i=0;i<detailList.size();i++){
			createSubObjectField(board, "detail", AppBoardFieldType.Subobject, sn++, detailBufferCalc.computeDetail(detailList.get(i)));
		}
//
//		detailList = dao.getMapBySQL("SELECT * FROM vehicle_loan_credit_investigation_details WHERE document_no = '" + documentNo + "'", null);
//
//		for(int i=0;i<detailList.size();i++){
//			createSubObjectField(board, "detail", AppBoardFieldType.Subobject, sn++, detailBufferCalc.computeDetail(detailList.get(i)));
//		}

		//logger.debug("计算完成:"+board2Json(board));
		System.out.println(board2Json(board));
		dao.flush();
		dao.update(doc);

	}


	@Override
	protected void computeDynamicFields(MobileDocumentBuffered doc) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getBufferCalcVersion() {
		return bufferCalcVersion;
	}

	@Override
	public String getBusiBillVersion(MobileDocumentBuffered doc) {
		return getBusiBillVersion(this.getApproveDocument(doc));
	}

	@Override
	public String getBusiBillVersion(ApproveDocuments<?> approveDoc) {
		if (null==approveDoc || null==approveDoc.getSubmitTime()){
			throw new ServiceException("审批流程对象是空，不可以获得版本号。");
		}else{
			return sdf.format(approveDoc.getSubmitTime());
		}
	}


	@Override
	public ApproveDocuments<?> getApproveDocument(MobileDocumentBuffered doc) {
		ApproveDocuments<?> approveDoc;
		if(doc.getBusiBill()==null)
			throw new ServiceException("未找审批单，单号：" + doc.getBusiBillId());
		try{
			approveDoc = (ApproveDocuments<?>)(doc.getBusiBill());
		}catch(Exception e){
			throw new ServiceException("转成审批流程对象失败，单号：" + doc.getBusiBillId());
		}
		return approveDoc;
	}
}
