package cn.sf_soft.office.approval.service.impl;


import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferCalc;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferService;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;
import cn.sf_soft.office.approval.ui.vo.VehicleDemandApplyView;
import cn.sf_soft.vehicle.demand.model.VehicleDemandApply;

/**
 * 资源需求申报
 * @author caigx
 *
 */
@Service("vehicleDemandApplyBuf")
public class VehicleDemandApplyBuf extends DocumentBufferService{
	// 审批权限Id,各个审批均不相同
	protected String approvalPopedomId = "10100220";
	
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleDemandApplyBuf.class);
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ApproveDocuments getDocumentDetail(String documentNo) {
		if(StringUtils.isEmpty(documentNo)){
			throw new ServiceException("审批单号为空");
		}
		List<VehicleDemandApply> applyList = (List<VehicleDemandApply>) dao.findByHql("from VehicleDemandApply where documentNo = ?", documentNo);
		if(applyList==null || applyList.size()==0){
			throw new ServiceException("根据审批单号："+documentNo+"未找到资源需求申报的记录");
		}else if (applyList.size()>1){
			throw new ServiceException("审批单号："+documentNo+"对应了多条资源需求申报的记录");
		}
		return applyList.get(0);
	}

	@Override
	protected String getApprovalPopedomId() {
		return approvalPopedomId;
	}
	
	@Autowired
	@Qualifier("vehicleDemandApplayBufferCalc") 
	public void setDocBuffer( DocumentBufferCalc docBuffer){
		super.docBuffer = docBuffer;
	}
	
	/**
	 * 处理审批列表中的审批单据信息
	 * @param approveDocument
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public VehicleDemandApplyView dealApproveDocument(VwOfficeApproveDocuments vwOfficeApproveDocuments) {
		VehicleDemandApplyView view = new VehicleDemandApplyView(vwOfficeApproveDocuments);
		try{
			StringBuffer buf = new StringBuffer();
			buf.append("select sum(round(purchasePrice,0)*isnull(quantity,0)) as totalAmount").append("\r\n");
			buf.append(" from  VehicleDemandApplyDetail  where demandId in (select demandId from VehicleDemandApply  where documentNo=?)").append("\r\n");
			List<Object> data1 = (List<Object>) dao.findByHql(buf.toString(),vwOfficeApproveDocuments.getDocumentNo());
			double totalAmount = 0;
			if (data1 != null && data1.size() == 1) {
				//Object[] row = (Object[]) data1.get(0);
				totalAmount =data1.get(0) != null ? Tools.toDouble((Double) data1.get(0)) : 0;
			}
			view.setAmountTotal(totalAmount);
			
		}catch(Exception ex){
			logger.warn(String.format("资源需求申报 %s,处理审批列表信息出错", vwOfficeApproveDocuments.getDocumentNo()), ex);
		}
		
		return view;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
		return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
		return super.onLastApproveLevel(approveDocument, comment);
	}
	
}
