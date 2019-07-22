package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Set;


/**
 * 审批单，所有类型的审批单的基类
 * 
 * @author king
 * @create 2012-12-8下午03:30:06
 * @param <Detail>
 *            审批单的明细
 */
public class ApproveDocuments<Detail> implements java.io.Serializable {


	private static final long serialVersionUID = -680798807496615651L;
	private String documentId;
	private String moduleId;
	private String moduleName;

	// this is the commons fields
	protected String documentNo;
	protected String stationId;
	protected Short status;
	protected String userId;
	protected String userNo;
	protected String userName;
	protected String departmentId;
	protected String departmentNo;
	protected String departmentName;
	protected String submitStationId;
	protected String submitStationName;
	protected Timestamp submitTime;
	protected String approverId;
	protected String approverNo;
	protected String approverName;
	protected Set<Detail> chargeDetail;// 单据的费用明细

	// modify 2013-5-15， 此属性在父表中没有，但是所有子表中都有
	protected Timestamp approveTime;

	private int approveLevel;
	private String approveName;
	private Timestamp revokeTime;
	private Timestamp invalidTime;
	// modify by shichunshan modifyTime从子类改到基类
	protected Timestamp modifyTime;
	
	// Constructors

	/** default constructor */
	public ApproveDocuments() {
	}

	/** minimal constructor */
	public ApproveDocuments(String stationId, Short status, String moduleId,
			String moduleName, String documentNo, String userId, String userNo,
			String userName, String departmentId, String departmentNo,
			String departmentName, String submitStationId,
			String submitStationName, Timestamp submitTime, int approveLevel,
			String approveName, String approverId, String approverNo,
			String approverName, Timestamp modifyTime) {
		this.stationId = stationId;
		this.status = status;
		this.moduleId = moduleId;
		this.moduleName = moduleName;
		this.documentNo = documentNo;
		this.userId = userId;
		this.userNo = userNo;
		this.userName = userName;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.submitStationId = submitStationId;
		this.submitStationName = submitStationName;
		this.submitTime = submitTime;
		this.approveLevel = approveLevel;
		this.approveName = approveName;
		this.approverId = approverId;
		this.approverNo = approverNo;
		this.approverName = approverName;
		this.modifyTime = modifyTime;
	}

	/** full constructor */
	public ApproveDocuments(String stationId, Short status, String moduleId,
			String moduleName, String documentNo, String userId, String userNo,
			String userName, String departmentId, String departmentNo,
			String departmentName, String submitStationId,
			String submitStationName, Timestamp submitTime, int approveLevel,
			String approveName, String approverId, String approverNo,
			String approverName, Timestamp revokeTime, Timestamp invalidTime,
			Timestamp modifyTime) {
		this.stationId = stationId;
		this.status = status;
		this.moduleId = moduleId;
		this.moduleName = moduleName;
		this.documentNo = documentNo;
		this.userId = userId;
		this.userNo = userNo;
		this.userName = userName;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.submitStationId = submitStationId;
		this.submitStationName = submitStationName;
		this.submitTime = submitTime;
		this.approveLevel = approveLevel;
		this.approveName = approveName;
		this.approverId = approverId;
		this.approverNo = approverNo;
		this.approverName = approverName;
		this.revokeTime = revokeTime;
		this.invalidTime = invalidTime;
		this.modifyTime = modifyTime;
	}

	// Property accessors

	public String getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
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

	public Timestamp getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	public int getApproveLevel() {
		return this.approveLevel;
	}

	public void setApproveLevel(int approveLevel) {
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

	public Timestamp getRevokeTime() {
		return this.revokeTime;
	}

	public void setRevokeTime(Timestamp revokeTime) {
		this.revokeTime = revokeTime;
	}

	public Timestamp getInvalidTime() {
		return this.invalidTime;
	}

	public void setInvalidTime(Timestamp invalidTime) {
		this.invalidTime = invalidTime;
	}

	public Set<Detail> getChargeDetail() {
		return chargeDetail;
	}

	public void setChargeDetail(Set<Detail> chargeDetail) {
		this.chargeDetail = chargeDetail;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (chargeDetail != null) {
			Iterator<Detail> it = chargeDetail.iterator();
			while (it.hasNext()) {
				sb.append("\n\t" + it.next().toString());
			}
			return "ApproveDocuments [approveLevel=" + approveLevel
					+ ", approveName=" + approveName + ", approverId="
					+ approverId + ", approverName=" + approverName
					+ ", approverNo=" + approverNo + ", departmentId="
					+ departmentId + ", departmentName=" + departmentName
					+ ", departmentNo=" + departmentNo + ", documentId="
					+ documentId + ", documentNo=" + documentNo
					+ ", invalidTime=" + invalidTime + ", moduleId=" + moduleId
					+ ", moduleName=" + moduleName + ", revokeTime="
					+ revokeTime + ", stationId=" + stationId + ", status="
					+ status + ", submitStationId=" + submitStationId
					+ ", submitStationName=" + submitStationName
					+ ", submitTime=" + submitTime + ", userId=" + userId
					+ ", userName=" + userName + ", userNo=" + userNo + "]"
					+ sb.toString();
		} else {
			return "ApproveDocuments [approveLevel=" + approveLevel
					+ ", approveName=" + approveName + ", approverId="
					+ approverId + ", approverName=" + approverName
					+ ", approverNo=" + approverNo + ", departmentId="
					+ departmentId + ", departmentName=" + departmentName
					+ ", departmentNo=" + departmentNo + ", documentId="
					+ documentId + ", documentNo=" + documentNo
					+ ", invalidTime=" + invalidTime + ", moduleId=" + moduleId
					+ ", moduleName=" + moduleName + ", revokeTime="
					+ revokeTime + ", stationId=" + stationId + ", status="
					+ status + ", submitStationId=" + submitStationId
					+ ", submitStationName=" + submitStationName
					+ ", submitTime=" + submitTime + ", userId=" + userId
					+ ", userName=" + userName + ", userNo=" + userNo + "]";
		}
	}

	public Timestamp getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

}
