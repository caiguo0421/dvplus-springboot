package cn.sf_soft.parts.inventory.model;
import java.sql.Timestamp;
import java.util.List;

import cn.sf_soft.office.approval.model.ApproveDocuments;


/**
 * 配件采购计划
 * @author ZHJ
 * @date 2016-12-17
 */

public class PartPurchasePlans  extends ApproveDocuments{

	 private static final long serialVersionUID = -7973733323269625037L;
//	 private String documentNo;
     private Short planType;
     private Short planStatus;
     private String remark;
//     private String approverName;
//     private Short status;
//     private Timestamp approveTime;
     private String approvePostil;
     private Double planPrice;
//     private String stationId;
     private String creator;
     private Timestamp createTime;
     private String modifier;
//     private Timestamp modifyTime;
//     private String userId;
//     private String userNo;
//     private String userName;
//     private String departmentId;
//     private String departmentNo;
//     private String departmentName;
//     private String submitStationId;
//     private String submitStationName;
//     private String approverId;
//     private String approverNo;
//     private Timestamp submitTime;
     private Short dfPlanOrderType;
     
     private List<PartPurchasePlanDetail> partPurchasePlanDetails;
     //供应商名称，在审批界面需要显示，改字段从明细表中获取
     private String supplierName;

    // Constructors

   

	/** default constructor */
    public PartPurchasePlans() {
    }

	/** minimal constructor */
    public PartPurchasePlans(String stationId) {
        this.stationId = stationId;
    }
    
    /** full constructor */
    public PartPurchasePlans(Short planType, Short planStatus, String remark, String approverName, Short status, Timestamp approveTime, String approvePostil, Double planPrice, String stationId, String creator, Timestamp createTime, String modifier, Timestamp modifyTime, String userId, String userNo, String userName, String departmentId, String departmentNo, String departmentName, String submitStationId, String submitStationName, String approverId, String approverNo, Timestamp submitTime, Short dfPlanOrderType) {
        this.planType = planType;
        this.planStatus = planStatus;
        this.remark = remark;
        this.approverName = approverName;
        this.status = status;
        this.approveTime = approveTime;
        this.approvePostil = approvePostil;
        this.planPrice = planPrice;
        this.stationId = stationId;
        this.creator = creator;
        this.createTime = createTime;
        this.modifier = modifier;
        this.modifyTime = modifyTime;
        this.userId = userId;
        this.userNo = userNo;
        this.userName = userName;
        this.departmentId = departmentId;
        this.departmentNo = departmentNo;
        this.departmentName = departmentName;
        this.submitStationId = submitStationId;
        this.submitStationName = submitStationName;
        this.approverId = approverId;
        this.approverNo = approverNo;
        this.submitTime = submitTime;
        this.dfPlanOrderType = dfPlanOrderType;
    }

   
    // Property accessors

    /*public String getDocumentNo() {
        return this.documentNo;
    }
    
    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }*/

    public Short getPlanType() {
        return this.planType;
    }
    
    public void setPlanType(Short planType) {
        this.planType = planType;
    }

    public Short getPlanStatus() {
        return this.planStatus;
    }
    
    public void setPlanStatus(Short planStatus) {
        this.planStatus = planStatus;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApproverName() {
        return this.approverName;
    }
    
    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Short getStatus() {
        return this.status;
    }
    
    public void setStatus(Short status) {
        this.status = status;
    }

    public Timestamp getApproveTime() {
        return this.approveTime;
    }
    
    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    public String getApprovePostil() {
        return this.approvePostil;
    }
    
    public void setApprovePostil(String approvePostil) {
        this.approvePostil = approvePostil;
    }

    public Double getPlanPrice() {
        return this.planPrice;
    }
    
    public void setPlanPrice(Double planPrice) {
        this.planPrice = planPrice;
    }

    public String getStationId() {
        return this.stationId;
    }
    
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getCreator() {
        return this.creator;
    }
    
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return this.modifier;
    }
    
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Timestamp getModifyTime() {
        return this.modifyTime;
    }
    
    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNo() {
        return this.userNo;
    }
    
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }
    
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentNo() {
        return this.departmentNo;
    }
    
    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSubmitStationId() {
        return this.submitStationId;
    }
    
    public void setSubmitStationId(String submitStationId) {
        this.submitStationId = submitStationId;
    }

    public String getSubmitStationName() {
        return this.submitStationName;
    }
    
    public void setSubmitStationName(String submitStationName) {
        this.submitStationName = submitStationName;
    }

    public String getApproverId() {
        return this.approverId;
    }
    
    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getApproverNo() {
        return this.approverNo;
    }
    
    public void setApproverNo(String approverNo) {
        this.approverNo = approverNo;
    }

    public Timestamp getSubmitTime() {
        return this.submitTime;
    }
    
    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public Short getDfPlanOrderType() {
        return this.dfPlanOrderType;
    }
    
    public void setDfPlanOrderType(Short dfPlanOrderType) {
        this.dfPlanOrderType = dfPlanOrderType;
    }
    
    public List<PartPurchasePlanDetail> getPartPurchasePlanDetails() {
		return partPurchasePlanDetails;
	}

	public void setPartPurchasePlanDetails(
			List<PartPurchasePlanDetail> partPurchasePlanDetails) {
		this.partPurchasePlanDetails = partPurchasePlanDetails;
	}

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
