package cn.sf_soft.oa.message.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 消息
 * @author caiguoxin
 *
 */
@Entity
@Table(name = "oa_message")
public class OaMessage implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 3782214442578052420L;
	private Integer objId;
	private Timestamp createTime;
	private String userNo;
	private Short msgTypeNo;
	private String message;
	private Boolean known;
	private Long versionNo;
	private List<OaMessageNotification> oaMessageNotifications = new ArrayList<OaMessageNotification>(0);

	// Constructors
	public OaMessage() {
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

	@Column(name = "create_time", length = 23)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "user_no")
	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Column(name = "msg_type_no")
	public Short getMsgTypeNo() {
		return this.msgTypeNo;
	}

	public void setMsgTypeNo(Short msgTypeNo) {
		this.msgTypeNo = msgTypeNo;
	}

	@Column(name = "message")
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "known")
	public Boolean getKnown() {
		return this.known;
	}

	public void setKnown(Boolean known) {
		this.known = known;
	}

	@Column(name = "version_no")
	public Long getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "msg")
	public List<OaMessageNotification> getOaMessageNotifications() {
		return this.oaMessageNotifications;
	}

	public void setOaMessageNotifications(List<OaMessageNotification> oaMessageNotifications) {
		this.oaMessageNotifications = oaMessageNotifications;
	}

}