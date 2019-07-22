package cn.sf_soft.user.model;

import cn.sf_soft.finance.voucher.model.AcctCompanyInfo;

/**
 * SysUnits entity. @author MyEclipse Persistence Tools
 */

public class SysUnits implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5515024282153984842L;
	private String unitId;
	private String parentId;
	private String fullId;
	private Short unitType;
	private String unitNo;
	private String unitName;
	private String principal;
	private String stationId;
	private String defaultStation;
	private Short unitRelation;

	private AcctCompanyInfo companyInfo;//帳套信息
	
	// Constructors

	/** default constructor */
	public SysUnits() {
	}

	/** minimal constructor */
	public SysUnits(String unitId, String fullId, Short unitType,
			String unitNo, String unitName) {
		this.unitId = unitId;
		this.fullId = fullId;
		this.unitType = unitType;
		this.unitNo = unitNo;
		this.unitName = unitName;
	}

	// Property accessors

	public String getUnitId() {
		return this.unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getFullId() {
		return this.fullId;
	}

	public void setFullId(String fullId) {
		this.fullId = fullId;
	}

	public Short getUnitType() {
		return this.unitType;
	}

	public void setUnitType(Short unitType) {
		this.unitType = unitType;
	}

	public String getUnitNo() {
		return this.unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getDefaultStation() {
		return this.defaultStation;
	}

	public void setDefaultStation(String defaultStation) {
		this.defaultStation = defaultStation;
	}

	public Short getUnitRelation() {
		return this.unitRelation;
	}

	public void setUnitRelation(Short unitRelation) {
		this.unitRelation = unitRelation;
	}


	public AcctCompanyInfo getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(AcctCompanyInfo companyInfo) {
		this.companyInfo = companyInfo;
	}

	@Override
	public String toString() {
		return "SysUnits [unitId=" + unitId + ", parentId=" + parentId
				+ ", fullId=" + fullId + ", unitType=" + unitType + ", unitNo="
				+ unitNo + ", unitName=" + unitName + ", principal="
				+ principal + ", stationId=" + stationId + ", defaultStation="
				+ defaultStation + ", unitRelation=" + unitRelation + "]";
	}


}
