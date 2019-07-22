package cn.sf_soft.vehicle.customer.model;

import java.sql.Timestamp;

/**
 * 战败客户信息 
 * 若跟踪结果为失败，失控，无效，则将信息记录到战败客户表
 * PresellVisitorsFail entity. @author MyEclipse Persistence Tools
 */

public class PresellVisitorsFail implements java.io.Serializable {

	// Fields

	private String visitorNo;
	private String visitorId;
	private String visitorName;
	private String visitorMobile;
	private String visitorPhone;
	private Boolean endDeal;
	private Timestamp createTime;
	private String creator;
	private Timestamp modifyTime;
	private String modifier;
	private String failedReason;
	private Timestamp failedDealTime;

	// Constructors

	/** default constructor */
	public PresellVisitorsFail() {
	}

	/** minimal constructor */
	public PresellVisitorsFail(String visitorNo) {
		this.visitorNo = visitorNo;
	}

	/** full constructor */
	public PresellVisitorsFail(String visitorNo, String visitorId,
			String visitorName, String visitorMobile, String visitorPhone,
			Boolean endDeal, Timestamp createTime, String creator,
			Timestamp modifyTime, String modifier, String failedReason, Timestamp failedDealTime) {
		this.visitorNo = visitorNo;
		this.visitorId = visitorId;
		this.visitorName = visitorName;
		this.visitorMobile = visitorMobile;
		this.visitorPhone = visitorPhone;
		this.endDeal = endDeal;
		this.createTime = createTime;
		this.creator = creator;
		this.modifyTime = modifyTime;
		this.modifier = modifier;
		this.failedReason = failedReason;
		this.failedDealTime = failedDealTime;
	}

	// Property accessors

	public String getVisitorNo() {
		return this.visitorNo;
	}

	public void setVisitorNo(String visitorNo) {
		this.visitorNo = visitorNo;
	}

	public String getVisitorId() {
		return this.visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getVisitorName() {
		return this.visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getVisitorMobile() {
		return this.visitorMobile;
	}

	public void setVisitorMobile(String visitorMobile) {
		this.visitorMobile = visitorMobile;
	}

	public String getVisitorPhone() {
		return this.visitorPhone;
	}

	public void setVisitorPhone(String visitorPhone) {
		this.visitorPhone = visitorPhone;
	}

	public Boolean getEndDeal() {
		return this.endDeal;
	}

	public void setEndDeal(Boolean endDeal) {
		this.endDeal = endDeal;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public Timestamp getFailedDealTime() {
		return failedDealTime;
	}

	public void setFailedDealTime(Timestamp failedDealTime) {
		this.failedDealTime = failedDealTime;
	}
}
