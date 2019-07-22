package cn.sf_soft.office.approval.model;

/**
 * OfficeAssetsReimbursementsApportionments entity. @author MyEclipse
 * Persistence Tools
 */

public class OfficeToolsReimbursementsApportionments implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5528595636717224108L;
	private String otraId;
	private String documentNo;
	private String departmentId;
	private String departmentNo;
	private String departmentName;
	private Double apportionAmount;

	// Constructors

	/** default constructor */
	public OfficeToolsReimbursementsApportionments() {
	}

	/** full constructor */
	public OfficeToolsReimbursementsApportionments(String oaraId,
			String documentNo, String departmentId, String departmentNo,
			String departmentName, Double apportionAmount) {
		this.otraId = oaraId;
		this.documentNo = documentNo;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.apportionAmount = apportionAmount;
	}

	// Property accessors

	

	public String getDocumentNo() {
		return this.documentNo;
	}

	/**
	 * @return the otraId
	 */
	public String getOtraId() {
		return otraId;
	}

	/**
	 * @param otraId the otraId to set
	 */
	public void setOtraId(String otraId) {
		this.otraId = otraId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
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

	public Double getApportionAmount() {
		return this.apportionAmount;
	}

	public void setApportionAmount(Double apportionAmount) {
		this.apportionAmount = apportionAmount;
	}

}
