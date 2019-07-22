package cn.sf_soft.oa.message.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 通知
 * @author caiguoxin
 *
 */
@Entity
@Table(name = "oa_message_notification")
public class OaMessageNotification implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 3170457943238791745L;
	private Integer objId;
	private Integer msgId;
	private OaMessage msg;
	private Short deviceTypeNo;
	private Timestamp notificationTime;
	private Long versionNo;

	// Constructors

	public OaMessageNotification() {
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "obj_id", unique = true, nullable = false)
	public Integer getObjId() {
		return this.objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	@Column(name = "msg_id", insertable = false,updatable = false)
	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "msg_id", nullable = false)
	public OaMessage getMsg() {
		return msg;
	}

	public void setMsg(OaMessage msg) {
		this.msg = msg;
	}
	

	@Column(name = "device_type_no", nullable = false)
	public Short getDeviceTypeNo() {
		return this.deviceTypeNo;
	}

	public void setDeviceTypeNo(Short deviceTypeNo) {
		this.deviceTypeNo = deviceTypeNo;
	}

	@Column(name = "notification_time", nullable = false, length = 23)
	public Timestamp getNotificationTime() {
		return this.notificationTime;
	}

	public void setNotificationTime(Timestamp notificationTime) {
		this.notificationTime = notificationTime;
	}

	@Column(name = "version_no")
	public Long getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	

}