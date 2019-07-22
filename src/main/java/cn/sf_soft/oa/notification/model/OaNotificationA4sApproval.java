package cn.sf_soft.oa.notification.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

import cn.sf_soft.office.approval.model.ApproveDocumentsPoints;

/**
 * 待审事宜通知
 * @author caiguoxin
 *
 */
@Entity
@Table(name = "oa_notification_a4s_approval")
public class OaNotificationA4sApproval implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 3463451899823628534L;
	private Integer objId;
	private String approvalPointId;
	private ApproveDocumentsPoints approvalPoint;
	private Boolean effective;
	private Boolean refusedNotification;
	private String userNo;
	private Timestamp lastNotificationTime;
	private Short notificationTimes;
	private Long versionNo;

	// Constructors
	public OaNotificationA4sApproval() {
	}

	public OaNotificationA4sApproval(String approvalPointId, Boolean effective,
			Boolean refusedNotification, String userNo,
			Timestamp lastNotificationTime, Short notificationTimes) {
		this.approvalPointId = approvalPointId;
		this.effective = effective;
		this.refusedNotification = refusedNotification;
		this.userNo = userNo;
		this.lastNotificationTime = lastNotificationTime;
		this.notificationTimes = notificationTimes;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "obj_id", unique = true, nullable = false)
	public Integer getObjId() {
		return this.objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	@Column(name = "approval_point_id", nullable = false)
	public String getApprovalPointId() {
		return this.approvalPointId;
	}

	public void setApprovalPointId(String approvalPointId) {
		this.approvalPointId = approvalPointId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approval_point_id", insertable = false,updatable = false)
	public ApproveDocumentsPoints getApprovalPoint() {
		return approvalPoint;
	}

	public void setApprovalPoint(ApproveDocumentsPoints approvalPoint) {
		this.approvalPoint = approvalPoint;
	}

	@Column(name = "effective", nullable = false)
	public Boolean getEffective() {
		return this.effective;
	}

	public void setEffective(Boolean effective) {
		this.effective = effective;
	}

	@Column(name = "refused_notification", nullable = false)
	public Boolean getRefusedNotification() {
		return this.refusedNotification;
	}

	public void setRefusedNotification(Boolean refusedNotification) {
		this.refusedNotification = refusedNotification;
	}

	@Column(name = "user_no", nullable = false)
	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Column(name = "last_notification_time", nullable = false, length = 23)
	public Timestamp getLastNotificationTime() {
		return this.lastNotificationTime;
	}

	public void setLastNotificationTime(Timestamp lastNotificationTime) {
		this.lastNotificationTime = lastNotificationTime;
	}

	@Column(name = "notification_times", nullable = false)
	public Short getNotificationTimes() {
		return this.notificationTimes;
	}

	public void setNotificationTimes(Short notificationTimes) {
		this.notificationTimes = notificationTimes;
	}

	@Column(name = "version_no")
	public Long getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	

}