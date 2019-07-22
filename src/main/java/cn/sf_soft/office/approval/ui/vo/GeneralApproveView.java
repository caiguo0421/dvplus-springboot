package cn.sf_soft.office.approval.ui.vo;

import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;

public class GeneralApproveView extends VwOfficeApproveDocuments{

	private static final long serialVersionUID = 4049934305103010654L;

	private Double loanAmount;



	public GeneralApproveView(){
		
	}

	
	public static GeneralApproveView fillDataByVwDocuments(VwOfficeApproveDocuments vwOfficeApproveDocuments){
		GeneralApproveView varyApproval=new GeneralApproveView();
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


	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
}
