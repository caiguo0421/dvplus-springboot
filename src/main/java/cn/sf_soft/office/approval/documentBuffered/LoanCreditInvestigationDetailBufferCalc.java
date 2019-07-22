package cn.sf_soft.office.approval.documentBuffered;

import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardTitle;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentBuffered;
import cn.sf_soft.office.approval.documentBuffered.PartPurchasePlanBufferCacl.Percent;
import cn.sf_soft.office.approval.service.impl.PartPurchasePlanBuf.PartPlanType;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.Color;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.parts.inventory.model.PartPurchasePlanDetail;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 消贷征信明细
 * 
 * @author ZQH
 *
 */
@Service("loanCreditInvestigationDetailBufferCalc")
public class LoanCreditInvestigationDetailBufferCalc extends ApprovalDocumentCalc {

	private int documentClassId = 10000;
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoanCreditInvestigationDetailBufferCalc.class);

	private static String moduleId = "101810";

	@Override
	protected void computeStaticFields(MobileDocumentBuffered doc) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void computeDynamicFields(MobileDocumentBuffered doc) {
		// TODO Auto-generated method stub

	}
	
	public MobileBoardBuffered computeDetail(Map<String, Object> doc){
		MobileBoardBuffered board = new MobileBoardBuffered();
		short sn = 1;
		String title = (String)doc.get("file_name");
		DocTitle docTitle=new DocTitle(title);
		board.setDocTitle(gson.toJson(docTitle));

		BoardTitle baseBoardTitle = new BoardTitle();
		baseBoardTitle.setTitle(title);
		baseBoardTitle.setCollapsable(false);
		baseBoardTitle.setDefaultExpanded(true);
		createBoardTitle(board, gson.toJson(baseBoardTitle), null, null, null);

		sn = createPropertyString(board, sn, doc, "file_name", "资料名称");
		sn = createPropertyString(board, sn, doc, "mode", "提供方式");
		sn = createPropertyString(board, sn, doc, "sign", "签字方式");
		sn = createPropertyString(board, sn, doc, "status_meaning", "状态");
		sn = createPropertyString(board, sn, doc, "remark", "备注");
		return board;
	}

	public void computeIssue(Map<String, Object> doc){

	}

	private short createPropertyString(MobileBoardBuffered board, short sn, Map<String, Object> doc, String field_name, String title){
		if(!doc.containsKey(field_name)){return sn;}
		createProperty(board, field_name, sn, false, true, true, title,
				(String)doc.get(field_name), null, null, null, null, null);
		return (short)(sn + 1);
	}
}
