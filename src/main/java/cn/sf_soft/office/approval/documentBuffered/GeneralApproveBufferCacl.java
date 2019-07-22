package cn.sf_soft.office.approval.documentBuffered;


import cn.sf_soft.common.Config;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.model.*;
import cn.sf_soft.office.approval.dao.LoanCreditInvestigationDao;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.DocTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 通用审批
 * @author ZQH
 *
 */
@Service("generalApproveBufferCacl")
public class GeneralApproveBufferCacl extends ApprovalDocumentCalc {

	private int documentClassId=10000;
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GeneralApproveBufferCacl.class);
	private static String bufferCalcVersion="2017113001.1";
	private static final String DF_PART_ORDER_TYPE="df_plan_order_type";
	private Map<Short,Object> planOrderType=new HashMap<Short,Object>();

	@Autowired
	protected Config config;

	private static String moduleId="359030";

//	@Autowired
//	private LoanCreditInvestigationDao partPurchasePlanDao;

//	@Autowired
//	private LoanCreditInvestigationDetailBufferCalc detailBufferCalc;


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

	protected short createDownloadFile(MobileBoardBuffered board, short sn, String filename, String filepath){
		createAttachment(board, "download_file", sn, false, true, true, filename,
				filepath, null, null, null, null, null);
		return (short)(sn + 1);
	}

	private short createPropertyString(MobileBoardBuffered board, short sn, Map<String, Object> doc, String field_name, String title){
		if(!doc.containsKey(field_name)){return sn;}
		createProperty(board, field_name, sn, false, true, true, title,
				(String)doc.get(field_name), null, null, null, null, null);
		return (short)(sn + 1);
	}

	private short createPropertyTimeStamp(MobileBoardBuffered board, short sn, Map<String, Object> doc, String field_name, String title){
		if(!doc.containsKey(field_name)){return sn;}
		Timestamp value = (Timestamp)doc.get(field_name);
		if(value == null){
			return sn;
		}
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		createProperty(board, field_name, sn, false, true, true, title,
				sdf.format(value), null, null, null, null, null);
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
			throw new ServiceException(document.getDocumentNo()+"此单据非通用审批单");
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

		// 通用审批
		MobileBoardBuffered board=doc.getBoard();
		if(null==board){
			board=org.springframework.beans.BeanUtils.instantiate(MobileBoardBuffered.class);
			doc.setBoard(board);
			board.setDocument(doc);
		}
		String documentNo=document.getDocumentNo();
		// double planPrice=Tools.toDouble(purchasePlan.getPlanPrice());

		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(documentNo);
		baseBoardTitle.setCollapsable(false);
		baseBoardTitle.setDefaultExpanded(true);
		createBoardTitle(board, gson.toJson(baseBoardTitle), null, null, null);

		DocTitle docTitle=new DocTitle("通用审批");
		board.setDocTitle(gson.toJson(docTitle));

		List<MobileBoardField> fields=board.getFields();
		if(null==fields){
			fields=new ArrayList<MobileBoardField>();
			board.setFields(fields);
		}

		// double planPrice=Tools.toDouble(purchasePlan.getPlanPrice());
		List<Map<String, Object>> docs = dao.getMapBySQL("SELECT * FROM office_general_approve WHERE document_no = '"+documentNo+"'", null);
		if(docs.size() == 0){
			throw new ServiceException("未找到审批单号对应通用审批单：" + document.getDocumentNo());
		}
		Map<String, Object> generalDoc = docs.get(0);
		//office_general_approve

		short sn = 1;
		sn = createPropertyString(board, sn, generalDoc, "approve_theme", "申请主题");
		sn = createPropertyString(board, sn, generalDoc, "approve_type", "审批类型");
		sn = createPropertyTimeStamp(board, sn, generalDoc, "approve_time", "申请日期");
		sn = createPropertyString(board, sn, generalDoc, "user_name", "申请人");
		sn = createPropertyString(board, sn, generalDoc, "remark", "申请事由");
//		sn = createPropertyString(board, sn, generalDoc, "department_name", "申请部门");
//		sn = createPropertyString(board, sn, generalDoc, "submit_station_name", "申请站点");
//		sn = createPropertyString(board, sn, generalDoc, "creator", "制单人");
//		sn = createPropertyTimeStamp(board, sn, generalDoc, "create_time", "制单时间");
//		sn = createPropertyString(board, sn, generalDoc, "modifier", "修改人");
//		sn = createPropertyString(board, sn, generalDoc, "remark", "申请事由");
//		sn = createPropertyTimeStamp(board, sn, generalDoc, "modify_time", "修改时间");
//		sn = createPropertyTimeStamp(board, sn, generalDoc, "submit_time", "提交时间");

//		List <Map<String, Object>> attachmentList = dao.getMapBySQL("SELECT * FROM office_general_approve_attachments WHERE document_no = '" + documentNo + "'", null);
//		if(attachmentList.size()>0){
//			createSubObjectField(board, "attachments", AppBoardFieldType.Subobject, sn++, buildAttachments(attachmentList));
//		}
		if(generalDoc.get("file_urls") != null){
			createSubObjectField(board, "attachments", AppBoardFieldType.Subobject, sn++, buildAttachments(documentNo, ((String)(generalDoc.get("file_urls"))).split(";")));
		}

		System.out.println(board2Json(board));
		dao.flush();
		dao.update(doc);

	}

	private MobileBoardBuffered buildAttachments(String documentNo, String[] attachmentList){
		String title = "附件";
		MobileBoardBuffered board = new MobileBoardBuffered();
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(title);
		baseBoardTitle.setCollapsable(false);
		baseBoardTitle.setDefaultExpanded(true);
		createBoardTitle(board, gson.toJson(baseBoardTitle), null, null, null);

		DocTitle docTitle=new DocTitle(title);
		board.setDocTitle(gson.toJson(docTitle));
		short sn = 1;
//		sn = createPropertyString(board, sn, doc, "upload_file", "上传文件");
//		sn = createPropertyString(board, sn, doc, "attachment_name", "附件命名");

		String uploadPrefix =  this.config.getApplicationConfig("attachment.prefix");
		if(uploadPrefix == null){
			uploadPrefix = "Uploads/";
		}

		for(int i = 0;i<attachmentList.length;i++){
			String path = attachmentList[i];
			if(path == null || path.length() == 0){
				continue;
			}
			if(Pattern.matches("^\\d{17}_", path)){
				sn = createDownloadFile(board, sn, path.substring(18), uploadPrefix + path);
			}else{
				sn = createDownloadFile(board, sn, path, uploadPrefix + documentNo + path);
			}
		}
		return board;
	}

	private MobileBoardBuffered buildAttachments(List <Map<String, Object>> attachmentList){
		String title = "附件";
		MobileBoardBuffered board = new MobileBoardBuffered();
		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(title);
		baseBoardTitle.setCollapsable(false);
		baseBoardTitle.setDefaultExpanded(true);
		createBoardTitle(board, gson.toJson(baseBoardTitle), null, null, null);

		DocTitle docTitle=new DocTitle(title);
		board.setDocTitle(gson.toJson(docTitle));
		short sn = 1;
//		sn = createPropertyString(board, sn, doc, "upload_file", "上传文件");
//		sn = createPropertyString(board, sn, doc, "attachment_name", "附件命名");
		for(int i = 0;i<attachmentList.size();i++){
			Map<String, Object> doc = attachmentList.get(i);
			sn = createDownloadFile(board, sn, doc.get("upload_file").toString(), doc.get("save_path").toString());
		}
		return board;
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
