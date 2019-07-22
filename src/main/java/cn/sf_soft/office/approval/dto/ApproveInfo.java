package cn.sf_soft.office.approval.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * 审批级别相关的数据，提交时使用
 * @author caigx
 *
 */
public class ApproveInfo {
	
	private int approveLevel = 0;
	private String approveName ;
	private String approverID ;
	private String approverNo ;
	private String approverName ;
	
	public ApproveInfo(){
		approveLevel = 0;
	}
	
	public boolean isEmpty(){
		return StringUtils.isEmpty(approverID); //已approverID是否为空判断
	}
	
	public int getApproveLevel() {
		return approveLevel;
	}
	public void setApproveLevel(int approveLevel) {
		this.approveLevel = approveLevel;
	}
	public String getApproveName() {
		return approveName;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	public String getApproverID() {
		return approverID;
	}
	public void setApproverID(String approverID) {
		this.approverID = approverID;
	}
	public String getApproverNo() {
		return approverNo;
	}
	public void setApproverNo(String approverNo) {
		this.approverNo = approverNo;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

}
