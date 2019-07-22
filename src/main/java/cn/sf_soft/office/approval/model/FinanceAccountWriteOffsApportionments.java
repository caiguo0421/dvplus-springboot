package cn.sf_soft.office.approval.model;

/**
 * FinanceAccountWriteOffsApportionments entity. @author MyEclipse Persistence
 * Tools
 */

public class FinanceAccountWriteOffsApportionments implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -788282186856067685L;
	private String fawaId;
	private String documentNo;
	private String departmentId;
	private String departmentNo;
	private String departmentName;
	private Double apportionAmount;

	// Constructors

	/** default constructor */
	public FinanceAccountWriteOffsApportionments() {
	}

	/** full constructor */
	public FinanceAccountWriteOffsApportionments(String fawaId,
			String documentNo, String departmentId, String departmentNo,
			String departmentName, Double apportionAmount) {
		this.fawaId = fawaId;
		this.documentNo = documentNo;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.apportionAmount = apportionAmount;
	}

	// Property accessors

	public String getFawaId() {
		return this.fawaId;
	}

	public void setFawaId(String fawaId) {
		this.fawaId = fawaId;
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

	@Override
	public String toString() {
		return "FinanceAccountWriteOffsApportionments [apportionAmount="
				+ apportionAmount + ", departmentId=" + departmentId
				+ ", departmentName=" + departmentName + ", departmentNo="
				+ departmentNo + ", documentNo=" + documentNo + ", fawaId="
				+ fawaId + "]";
	}

}
