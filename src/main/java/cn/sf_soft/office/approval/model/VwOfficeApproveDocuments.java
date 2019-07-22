package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * 审批主单
 * @author caigx
 *
 */
@SuppressWarnings("rawtypes")
public class VwOfficeApproveDocuments extends ApproveDocuments {

	private static final long serialVersionUID = 2827486581370417214L;
//	private String documentId;
//	private String stationId;
//	private Short status;
//	private String moduleId;
//	private String moduleName;
//	private String documentNo;
//	private String userId;
//	private String userNo;
//	private String userName;
//	private String departmentId;
//	private String departmentNo;
//	private String departmentName;
//	private String submitStationId;
//	private String submitStationName;
//	private Timestamp submitTime;
//	private int approveLevel;
//	private String approveName;
//	private String approverId;
//	private String approverNo;
//	private String approverName;
//	private Timestamp revokeTime;
//	private Timestamp invalidTime;
	
	
	protected Double documentAmount;
	
	//用于车辆合同和车辆合同变更的字段
	private String customerName; //客户名称
	private String vehicleVin;//底盘号

	//流程中增加合同号-20190705
	private String contractCode;

	// 已审事宜中排序需要用到的字段
	private String pointApproverId;
	private Timestamp lastApproveTime;
	
	

	//不能处理的单据,当待审的审判点的所有审批人都没有审批权限时，为true
	private boolean deadForAccessCtrl = false;
	
	
	public Double getDocumentAmount() {
		return documentAmount;
	}

	public void setDocumentAmount(Double documentAmount) {
		this.documentAmount = documentAmount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getVehicleVin() {
		return vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
	}

	public String getPointApproverId() {
		return pointApproverId;
	}

	public void setPointApproverId(String pointApproverId) {
		this.pointApproverId = pointApproverId;
	}

	public Timestamp getLastApproveTime() {
		return lastApproveTime;
	}

	public void setLastApproveTime(Timestamp lastApproveTime) {
		this.lastApproveTime = lastApproveTime;
	}

	public boolean getDeadForAccessCtrl() {
		return deadForAccessCtrl;
	}

	public void setDeadForAccessCtrl(boolean deadForAccessCtrl) {
		this.deadForAccessCtrl = deadForAccessCtrl;
	}


	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
}
