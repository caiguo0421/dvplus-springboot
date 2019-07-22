package cn.sf_soft.basedata.model;

/**
 * 
 */

public class BaseSysAgency implements java.io.Serializable {

	// Fields

	private String agencyId;
	private String agencyCode;
	private String agencyName;
	private String divisionName;
	private String identifiedCode;
	private String state;
	private String operAddress;
	private String servicePhone;
	private Boolean isSelf;
	private Boolean status;
	private String productLine;
	private String areaType;
	private String orgId;

	// Constructors

	/** default constructor */
	public BaseSysAgency() {
	}

	/** minimal constructor */
	public BaseSysAgency(String agencyName, String divisionName, String state,
			String operAddress) {
		this.agencyName = agencyName;
		this.divisionName = divisionName;
		this.state = state;
		this.operAddress = operAddress;
	}

	/** full constructor */
	public BaseSysAgency(String agencyCode, String agencyName,
			String divisionName, String identifiedCode, String state,
			String operAddress, String servicePhone, Boolean isSelf,
			Boolean status, String productLine, String areaType, String orgId) {
		this.agencyCode = agencyCode;
		this.agencyName = agencyName;
		this.divisionName = divisionName;
		this.identifiedCode = identifiedCode;
		this.state = state;
		this.operAddress = operAddress;
		this.servicePhone = servicePhone;
		this.isSelf = isSelf;
		this.status = status;
		this.productLine = productLine;
		this.areaType = areaType;
		this.orgId = orgId;
	}

	// Property accessors

	public String getAgencyId() {
		return this.agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyCode() {
		return this.agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getAgencyName() {
		return this.agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getDivisionName() {
		return this.divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getIdentifiedCode() {
		return this.identifiedCode;
	}

	public void setIdentifiedCode(String identifiedCode) {
		this.identifiedCode = identifiedCode;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOperAddress() {
		return this.operAddress;
	}

	public void setOperAddress(String operAddress) {
		this.operAddress = operAddress;
	}

	public String getServicePhone() {
		return this.servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public Boolean getIsSelf() {
		return this.isSelf;
	}

	public void setIsSelf(Boolean isSelf) {
		this.isSelf = isSelf;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getProductLine() {
		return this.productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public String getAreaType() {
		return this.areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
