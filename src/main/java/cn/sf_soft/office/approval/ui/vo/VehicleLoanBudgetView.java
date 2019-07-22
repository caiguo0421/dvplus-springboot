package cn.sf_soft.office.approval.ui.vo;

import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;

/**
 * 消贷费用预算-扩展信息
 * @author caigx
 *
 */
public class VehicleLoanBudgetView extends VwOfficeApproveDocuments{
	private static final long serialVersionUID = 8486965816750216398L;
	//贷款人
	private String loanObjectName;
	
	//贷款总金额
	private double amountTotal;
	
	
	public VehicleLoanBudgetView(){
		
	}
	
	public VehicleLoanBudgetView(VwOfficeApproveDocuments vwOfficeApproveDocuments){
		this.setDocumentId(vwOfficeApproveDocuments.getDocumentId());
		this.setStationId(vwOfficeApproveDocuments.getStationId());
		this.setStatus(vwOfficeApproveDocuments.getStatus());
		this.setModuleId(vwOfficeApproveDocuments.getModuleId());
		this.setModuleName(vwOfficeApproveDocuments.getModuleName());
		this.setDocumentNo(vwOfficeApproveDocuments.getDocumentNo());
		this.setUserId(vwOfficeApproveDocuments.getUserId());
		this.setUserNo(vwOfficeApproveDocuments.getUserNo());
		this.setUserName(vwOfficeApproveDocuments.getUserName());
		this.setDepartmentId(vwOfficeApproveDocuments.getDepartmentId());
		this.setDepartmentNo(vwOfficeApproveDocuments.getDepartmentNo());
		this.setDepartmentName(vwOfficeApproveDocuments.getDepartmentName());
		this.setSubmitStationId(vwOfficeApproveDocuments.getSubmitStationId());
		this.setSubmitStationName(vwOfficeApproveDocuments.getSubmitStationName());
		this.setSubmitTime(vwOfficeApproveDocuments.getSubmitTime());
		this.setApproveLevel(vwOfficeApproveDocuments.getApproveLevel());
		this.setApproveName(vwOfficeApproveDocuments.getApproveName());
		this.setApproverId(vwOfficeApproveDocuments.getApproverId());
		this.setApproverNo(vwOfficeApproveDocuments.getApproverNo());
		this.setApproverName(vwOfficeApproveDocuments.getApproverName());
		this.setRevokeTime(vwOfficeApproveDocuments.getRevokeTime());
		this.setInvalidTime(vwOfficeApproveDocuments.getInvalidTime());
		this.setDocumentAmount(vwOfficeApproveDocuments.getDocumentAmount());
	}
	
	public String getLoanObjectName() {
		return loanObjectName;
	}
	public void setLoanObjectName(String loanObjectName) {
		this.loanObjectName = loanObjectName;
	}
	public double getAmountTotal() {
		return amountTotal;
	}
	public void setAmountTotal(double amountTotal) {
		this.amountTotal = amountTotal;
	}
	
	
	
	

}
