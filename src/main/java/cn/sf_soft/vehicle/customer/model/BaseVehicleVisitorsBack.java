package cn.sf_soft.vehicle.customer.model;

import java.sql.Timestamp;

/**
 * 成交机会
 * BaseVehicleVisitorsBack entity. @author MyEclipse Persistence Tools
 */

public class BaseVehicleVisitorsBack implements java.io.Serializable {

	// Fields

	private String visitorsDaysId;
	private String visitorClass;
	private Integer visitorsNum;
	private String stationId;
	private Short status;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Timestamp modifyTime;

	// Constructors

	/** default constructor */
	public BaseVehicleVisitorsBack() {
	}

	/** minimal constructor */
	public BaseVehicleVisitorsBack(String visitorsDaysId) {
		this.visitorsDaysId = visitorsDaysId;
	}

	/** full constructor */
	public BaseVehicleVisitorsBack(String visitorsDaysId, String visitorClass,
			Integer visitorsNum, String stationId, Short status, String remark,
			String creator, Timestamp createTime, String modifier,
			Timestamp modifyTime) {
		this.visitorsDaysId = visitorsDaysId;
		this.visitorClass = visitorClass;
		this.visitorsNum = visitorsNum;
		this.stationId = stationId;
		this.status = status;
		this.remark = remark;
		this.creator = creator;
		this.createTime = createTime;
		this.modifier = modifier;
		this.modifyTime = modifyTime;
	}

	// Property accessors

	public String getVisitorsDaysId() {
		return this.visitorsDaysId;
	}

	public void setVisitorsDaysId(String visitorsDaysId) {
		this.visitorsDaysId = visitorsDaysId;
	}

	public String getVisitorClass() {
		return this.visitorClass;
	}

	public void setVisitorClass(String visitorClass) {
		this.visitorClass = visitorClass;
	}

	public Integer getVisitorsNum() {
		return this.visitorsNum;
	}

	public void setVisitorsNum(Integer visitorsNum) {
		this.visitorsNum = visitorsNum;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}


}
