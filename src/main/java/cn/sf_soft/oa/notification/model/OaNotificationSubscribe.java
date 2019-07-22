package cn.sf_soft.oa.notification.model;

import static javax.persistence.GenerationType.IDENTITY;


import javax.persistence.*;

/**
 * 消息订阅
 * 
 * @author caigx
 *
 */
@Entity
@Table(name = "oa_notification_subscribe", uniqueConstraints = {@UniqueConstraint(columnNames = { "product_id","device_id" }) })
public class OaNotificationSubscribe implements java.io.Serializable {

	private static final long serialVersionUID = 3866008952373532927L;
	private Integer objId;
	//产品Id  100 Dvplus 掌上天友iPhone
	private Integer productId;
	private String deviceId;
	//设备类型 10：iPhone 
	private Short deviceTypeNo;
	private String token;
	private String userNo;
	private Boolean subscribed;
	private String brand;
	private String osVersion;
	private Long versionNo;

	// Constructors
	public OaNotificationSubscribe() {
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

	@Column(name = "product_id", nullable = false)
	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	@Column(name = "device_id", nullable = false)
	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	@Column(name = "device_type_no", nullable = false)
	public Short getDeviceTypeNo() {
		return deviceTypeNo;
	}


	public void setDeviceTypeNo(Short deviceTypeNo) {
		this.deviceTypeNo = deviceTypeNo;
	}


	@Column(name = "token")
	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	@Column(name = "user_no", nullable = false)
	public String getUserNo() {
		return userNo;
	}


	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}


	@Column(name = "subscribed", nullable = false)
	public Boolean getSubscribed() {
		return subscribed;
	}


	public void setSubscribed(Boolean subscribed) {
		this.subscribed = subscribed;
	}


	@Column(name = "version_no")
	public Long getVersionNo() {
		return versionNo;
	}


	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	@Column(name = "brand")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name = "os_version")
	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
}
