package cn.sf_soft.office.approval.model;

import java.util.Set;

/**
 * 审批点
 * 
 * @author caigx
 *
 */
public class ApproveLevelsPoints implements java.io.Serializable {

	private static final long serialVersionUID = 9001566876632601470L;
	private String balpId;
	private String levelId;
	private String approveName;
	private Short approveMode;
	//审批人Id
	private String approverId;
	//审批人编号（多个）
	private String approverNo;
	//审批人姓名（多个）
	private String approverName;
	
	private String boundValue;
	private String boundItem;
	private Boolean boundExcept;
	private String remark;

	private Set<ApproveLevelsPointsConditions> levelPointCondition;

	// Constructors

	public ApproveLevelsPoints() {
	}

	// Property accessors

	public String getBalpId() {
		return this.balpId;
	}

	public void setBalpId(String balpId) {
		this.balpId = balpId;
	}

	public String getLevelId() {
		return this.levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getApproveName() {
		return this.approveName;
	}

	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}

	public Short getApproveMode() {
		return this.approveMode;
	}

	public void setApproveMode(Short approveMode) {
		this.approveMode = approveMode;
	}


	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
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

	public String getBoundValue() {
		return this.boundValue;
	}

	public void setBoundValue(String boundValue) {
		this.boundValue = boundValue;
	}

	public String getBoundItem() {
		return this.boundItem;
	}

	public void setBoundItem(String boundItem) {
		this.boundItem = boundItem;
	}

	public Boolean getBoundExcept() {
		return this.boundExcept;
	}

	public void setBoundExcept(Boolean boundExcept) {
		this.boundExcept = boundExcept;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setLevelPointCondition(Set<ApproveLevelsPointsConditions> levelPointCondition) {
		this.levelPointCondition = levelPointCondition;
	}

	public Set<ApproveLevelsPointsConditions> getLevelPointCondition() {
		return levelPointCondition;
	}

}
