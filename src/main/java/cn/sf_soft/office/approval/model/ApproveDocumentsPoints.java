package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * OfficeApproveDocumentsPoints entity. @author MyEclipse Persistence Tools
 */

public class ApproveDocumentsPoints implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -1175294719131951999L;
	private String oadpId;
	private String documentId;
	private Short status;
	private Integer approveLevel;
	private String approveName;
	private String approverId;
	private String approverNo;
	private String approverName;
	private Timestamp approveTime;
	private String remark;
	
	//不能处理的单据,审判点的所有审批人都没有审批权限时，为true
	private boolean deadForAccessCtrl = false;
	//等待时间
	private String holdingTime;

	/**
	 * 图片
	 */
	private String pics;

	/**
	 * 附件
	 */
	private String fileUrls;

	// Constructors

	/** default constructor */
	public ApproveDocumentsPoints() {
	}

	/** minimal constructor */
	public ApproveDocumentsPoints(String documentId, Short status,
			Integer approveLevel, String approveName, String approverId,
			String approverNo, String approverName, Timestamp approveTime) {
		this.documentId = documentId;
		this.status = status;
		this.approveLevel = approveLevel;
		this.approveName = approveName;
		this.approverId = approverId;
		this.approverNo = approverNo;
		this.approverName = approverName;
		this.approveTime = approveTime;
	}

	/** full constructor */
	public ApproveDocumentsPoints(String documentId, Short status,
			Integer approveLevel, String approveName, String approverId,
			String approverNo, String approverName, Timestamp approveTime,
			String remark) {
		this.documentId = documentId;
		this.status = status;
		this.approveLevel = approveLevel;
		this.approveName = approveName;
		this.approverId = approverId;
		this.approverNo = approverNo;
		this.approverName = approverName;
		this.approveTime = approveTime;
		this.remark = remark;
	}

	// Property accessors

	public String getOadpId() {
		return this.oadpId;
	}

	public void setOadpId(String oadpId) {
		this.oadpId = oadpId;
	}

	public String getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Integer getApproveLevel() {
		return this.approveLevel;
	}

	public void setApproveLevel(Integer approveLevel) {
		this.approveLevel = approveLevel;
	}

	public String getApproveName() {
		return this.approveName;
	}

	public void setApproveName(String approveName) {
		this.approveName = approveName;
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

	public String getApproverName() {
		return this.approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public boolean getDeadForAccessCtrl() {
		return deadForAccessCtrl;
	}

	public void setDeadForAccessCtrl(boolean deadForAccessCtrl) {
		this.deadForAccessCtrl = deadForAccessCtrl;
	}

	public String getHoldingTime() {
		return holdingTime;
	}

	public void setHoldingTime(String holdingTime) {
		this.holdingTime = holdingTime;
	}

	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public String getFileUrls() {
		return fileUrls;
	}

	public void setFileUrls(String fileUrls) {
		this.fileUrls = fileUrls;
	}

	@Override
	public String toString() {
		return "ApproveDocumentsPoints [approveLevel=" + approveLevel
				+ ", approveName=" + approveName + ", approveTime="
				+ approveTime + ", approverId=" + approverId
				+ ", approverName=" + approverName + ", approverNo="
				+ approverNo + ", documentId=" + documentId + ", oadpId="
				+ oadpId + ", remark=" + remark + ", status=" + status + "]";
	}

}
