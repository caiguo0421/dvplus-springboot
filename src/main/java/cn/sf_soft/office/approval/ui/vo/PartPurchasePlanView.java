package cn.sf_soft.office.approval.ui.vo;

import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;

public class PartPurchasePlanView extends VwOfficeApproveDocuments{

	private static final long serialVersionUID = 4049934305103010654L;

	private Double planPrice;
	
	
	
	public PartPurchasePlanView(){
		
	}
	
	public Double getPlanPrice() {
		return planPrice;
	}


	public void setPlanPrice(Double planPrice) {
		this.planPrice = planPrice;
	}

	
	public static PartPurchasePlanView fillDataByVwDocuments(VwOfficeApproveDocuments vwOfficeApproveDocuments){
		PartPurchasePlanView varyApproval=new PartPurchasePlanView();
		varyApproval.setDocumentId(vwOfficeApproveDocuments.getDocumentId());
		varyApproval.setStationId(vwOfficeApproveDocuments.getStationId());
		varyApproval.setStatus(vwOfficeApproveDocuments.getStatus());
		varyApproval.setModuleId(vwOfficeApproveDocuments.getModuleId());
		varyApproval.setModuleName(vwOfficeApproveDocuments.getModuleName());
		varyApproval.setDocumentNo(vwOfficeApproveDocuments.getDocumentNo());
		varyApproval.setUserId(vwOfficeApproveDocuments.getUserId());
		varyApproval.setUserNo(vwOfficeApproveDocuments.getUserNo());
		varyApproval.setUserName(vwOfficeApproveDocuments.getUserName());
		varyApproval.setDepartmentId(vwOfficeApproveDocuments.getDepartmentId());
		varyApproval.setDepartmentNo(vwOfficeApproveDocuments.getDepartmentNo());
		varyApproval.setDepartmentName(vwOfficeApproveDocuments.getDepartmentName());
		varyApproval.setSubmitStationId(vwOfficeApproveDocuments.getSubmitStationId());
		varyApproval.setSubmitStationName(vwOfficeApproveDocuments.getSubmitStationName());
		varyApproval.setSubmitTime(vwOfficeApproveDocuments.getSubmitTime());
		varyApproval.setApproveLevel(vwOfficeApproveDocuments.getApproveLevel());
		varyApproval.setApproveName(vwOfficeApproveDocuments.getApproveName());
		varyApproval.setApproverId(vwOfficeApproveDocuments.getApproverId());
		varyApproval.setApproverNo(vwOfficeApproveDocuments.getApproverNo());
		varyApproval.setApproverName(vwOfficeApproveDocuments.getApproverName());
		varyApproval.setRevokeTime(vwOfficeApproveDocuments.getRevokeTime());
		varyApproval.setInvalidTime(vwOfficeApproveDocuments.getInvalidTime());
		varyApproval.setDocumentAmount(vwOfficeApproveDocuments.getDocumentAmount());
		return varyApproval;
	}


}
