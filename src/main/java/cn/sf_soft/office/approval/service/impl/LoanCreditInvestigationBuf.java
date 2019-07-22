package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.basedata.model.BaseSysAgency;
import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferCalc;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferService;
import cn.sf_soft.office.approval.dao.LoanCreditInvestigationDao;
import cn.sf_soft.office.approval.dao.PartPurchasePlanDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.VehicleLoanBudget;
import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;
import cn.sf_soft.office.approval.ui.vo.LoanCreditInvestigationView;
import cn.sf_soft.office.approval.ui.vo.PartPurchasePlanView;
import cn.sf_soft.parts.inventory.model.PartPurchaseOrder;
import cn.sf_soft.parts.inventory.model.PartPurchasePlanDetail;
import cn.sf_soft.parts.inventory.model.PartPurchasePlans;
import cn.sf_soft.parts.inventory.model.PartPurchaseUrgentOrder;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.loan.model.VehicleLoanCreditInvestigation;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 消贷征信调查
 * @author ZQH
 *
 */
@Service("loanCreditInvestigationBuf")
public class LoanCreditInvestigationBuf extends DocumentBufferService{

	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoanCreditInvestigationBuf.class);
	//审批权限id
	protected String approvalPopedomId="10181020";

	private static final String DF_PART_OBJECT_NO="DF_PART_OBJECT_NO";

	private static final String DF_PART_ORDER_TYPE="df_plan_order_type";

	private static final String DF_PURCHASE_ORDER_DEPOSIT_PROPORTION="DF_PURCHASE_ORDER_DEPOSIT_PROPORTION";

	private static final String SEND_ORDER="直送订单";

	private static final String SALE_ORDER="销售订单";

	private static final String UREA="尿素";
	private static final String CORE="滤芯";

	private static DecimalFormat df = new DecimalFormat("#.00");

	private int dfDepositProportion;

	@Autowired
	private SysCodeRulesService sysCodeService;

	@Autowired
	@Qualifier("baseDao")
	private BaseDao dao;

	@Autowired
	@Qualifier("loanCreditInvestigationBufferCacl")
	public void setDocBuffer( DocumentBufferCalc docBuffer){
		super.docBuffer = docBuffer;
	}


	public LoanCreditInvestigationBuf(){
		documentClassId = 10000;
		//docBuffer = saleContractVaryBuffer; // 缓存文旦计算
	}


	public int getDfDepositProportion() {
		return dfDepositProportion;
	}

	public void setDfDepositproportion(int dfDepositProportion) {
		this.dfDepositProportion = dfDepositProportion;
	}

	@Autowired
	private LoanCreditInvestigationDao loanCreditInvestigationDao;

	@Override
	public LoanCreditInvestigationView dealApproveDocument(VwOfficeApproveDocuments vwOfficeApproveDocuments) {
		LoanCreditInvestigationView planView= LoanCreditInvestigationView.fillDataByVwDocuments(vwOfficeApproveDocuments);
		try
		{
			StringBuffer buffer=new StringBuffer();
			buffer.append("SELECT round(loan_amount_tott,0) as loan_amount_tott from vw_vehicle_loan_credit_investigation where document_no='"+vwOfficeApproveDocuments.getDocumentNo()+"'");
			@SuppressWarnings("unchecked")
			List<Double> data1= dao.executeSQLQuery(buffer.toString());
			double loan_amount_tott=0;
			if(data1!=null&&data1.size()==1)
			{
				loan_amount_tott=data1.get(0)!=null?Double.valueOf(data1.get(0)):0;
			}
			planView.setDocumentAmount(loan_amount_tott);
			planView.setLoanAmount(loan_amount_tott);
		}
		catch(Exception ex)
		{
			logger.warn(String.format("消贷征信调查 %s,处理审批列表信息出错", vwOfficeApproveDocuments.getDocumentNo()), ex);
		}
		return planView;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveDocuments getDocumentDetail(String documentNo) {
		if(StringUtils.isEmpty(documentNo)){
			throw new ServiceException("审批单号为空");
		}
		VehicleLoanCreditInvestigation doc=dao.get(VehicleLoanCreditInvestigation.class, documentNo);
		if(doc==null){
			throw new ServiceException("根据审批单号："+documentNo+"未找到消贷征信记录");
		}
		return doc;
	}

	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument,
			ApproveStatus approveStatus) {
		if(approveDocument==null||approveDocument.getDocumentNo().isEmpty()){
			throw new ServiceException("审批失败:审批单号不能为空");
		}
		if(approveStatus==ApproveStatus.LAST_APPROVE)
		{
		  validateRecord(approveDocument);
		}
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}



	@SuppressWarnings("rawtypes")
	private void validateRecord(ApproveDocuments approveDocument) {
		List result = dao.executeSQLQuery("SELECT COUNT(*) AS c FROM vw_vehicle_loan_credit_investigation WHERE document_no='"+approveDocument.getDocumentNo()+"'");
		if(result == null || result.size() == 0 || ((Integer)result.get(0)).equals(0)){
			throw new ServiceException("审批失败:未找到消贷信用记录");
		}
//		PartPurchasePlans purchasePlan=dao.get(PartPurchasePlans.class,approveDocument.getDocumentNo());
//		if(purchasePlan==null){
//			throw new ServiceException("审批失败:未找到合同变更主表记录");
//		}
//		if(StringUtils.isEmpty(purchasePlan.getDocumentNo())){
//			throw new ServiceException("审批失败:审批单号不能为空");
//		}

//		if(purchasePlan.getPlanType()== PartPlanType.BaseDfOrder.code){
//			String  partPurchaseDefault=loanCreditInvestigationDao.getPartPurchaseDefault(purchasePlan.getStationId());
//			if(partPurchaseDefault==null||partPurchaseDefault.isEmpty()){
//				throw new ServiceException("没有设置配件采购默认站点，请前往设置！");
//			}
//		}
//		List<Map<String,Object>> list=loanCreditInvestigationDao.getSupplierListByDocumentNo(approveDocument.getDocumentNo());
//		if(list==null||list.size()==0){
//			throw new ServiceException("没有采购供应商的信息，请确认后再审批！");
//		}
//		String optionValue=dao.getOptionValue(DF_PART_OBJECT_NO);
//		List<Map<String,Object>> listObjectNo=null;
//		if(optionValue!=null&&!optionValue.isEmpty()){
//			listObjectNo=loanCreditInvestigationDao.getDfSupplierNoByObjectNo(optionValue);
//		}
//		Short planType=purchasePlan.getPlanType();
//		if(listObjectNo!=null&&listObjectNo.size()>0)
//		{
//		for(Map map:list){
//			String supplierNo=map.get("supplier_no").toString();
//			if(planType== PartPlanType.BaseDfOrder.getCode()||planType== PartPlanType.DfRushOrder.getCode()){
//				for(Map mapObjectNo:listObjectNo){
//					if(!mapObjectNo.containsValue(supplierNo)){
//						throw new ServiceException("采购计划类型为东风一般订单或东风紧急订单时，供应商必须为东风供应商！");
//					}
//				}
//			}
//		}
//	   }
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment){
		if(approveDocument==null||approveDocument.getDocumentNo().isEmpty()){
			throw new ServiceException("审批失败：未找到审批单据信息");
		}
		dealBudegetStatus(approveDocument, (short)70);
		return super.onLastApproveLevel(approveDocument, comment);
	}

	private void dealBudegetStatus(ApproveDocuments approveDocument, Short nStatus)
	{
		List result = dao.executeSQLQuery("SELECT budget_no FROM vehicle_loan_credit_investigation WHERE document_no='"+approveDocument.getDocumentNo()+"'");
		if(result == null || result.size() == 0){
			throw new ServiceException("审批失败:未找到消贷信用记录");
		}
		String budget_no = (String)result.get(0);
		VehicleLoanBudget doc=dao.get(VehicleLoanBudget.class, budget_no);
		doc.setFlowStatus(nStatus);
		dao.update(doc);
	}

	
}
