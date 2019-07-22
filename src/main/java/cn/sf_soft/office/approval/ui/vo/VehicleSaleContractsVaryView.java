package cn.sf_soft.office.approval.ui.vo;

import java.sql.Timestamp;
import java.util.Set;

import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;

/**
 * 合同变更扩展信息
 * @author caigx
 *
 */
public class VehicleSaleContractsVaryView  extends VwOfficeApproveDocuments{

	private static final long serialVersionUID = 6527429031886765312L;

	private String contractNo;//合同号
	
	//private String customerName;//客户名称 VwOfficeApproveDocuments中有
	
	private String  remark;//备注
	
	private String salesman;//销售人员
	
	//private Double priceRequest;//需请款金额
	
	
	public VehicleSaleContractsVaryView(){
		
	}
	
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	
	
	/**
	 * 根据VwOfficeApproveDocuments 内容来填充数据
	 * @param vwOfficeApproveDocuments
	 */
	public static VehicleSaleContractsVaryView fillDataByVwDocuments(VwOfficeApproveDocuments vwOfficeApproveDocuments){
		VehicleSaleContractsVaryView  varyApproval = new VehicleSaleContractsVaryView();
		
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

//	public Double getPriceRequest() {
//		return priceRequest;
//	}
//
//	public void setPriceRequest(Double priceRequest) {
//		this.priceRequest = priceRequest;
//	}
	
}
