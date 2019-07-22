package cn.sf_soft.office.approval.model;
/**
 * 审批单据的查询条件
 * @author king
 * @create 2013-9-27下午4:47:28
 */
public class ApproveDocumentSearchCriteria {
	private String moduleId;
	private String documentNo;
	private String submitUser;
	private String beginTime;
	private String endTime;

	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getSubmitUser() {
		return submitUser;
	}
	public void setSubmitUser(String submitUser) {
		this.submitUser = submitUser;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
}
