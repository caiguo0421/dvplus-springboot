package cn.sf_soft.office.approval.model;

/**
 * OfficeAssetsReimbursementsApportionments entity. @author MyEclipse
 * Persistence Tools
 */

public class OfficeAssetsReimbursementsApportionments implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5528595636717224108L;
	private String oaraId;
	private String documentNo;
	private String departmentId;
	private String departmentNo;
	private String departmentName;
	private Double apportionAmount;

	// Constructors

	/** default constructor */
	public OfficeAssetsReimbursementsApportionments() {
	}

	/** full constructor */
	public OfficeAssetsReimbursementsApportionments(String oaraId,
			String documentNo, String departmentId, String departmentNo,
			String departmentName, Double apportionAmount) {
		this.oaraId = oaraId;
		this.documentNo = documentNo;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.apportionAmount = apportionAmount;
	}

	// Property accessors

	public String getOaraId() {
		return this.oaraId;
	}

	public void setOaraId(String oaraId) {
		this.oaraId = oaraId;
	}

	public String getDocumentNo() {
		return this.documentNo;
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
