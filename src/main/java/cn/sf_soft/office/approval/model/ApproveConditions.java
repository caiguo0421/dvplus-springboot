package cn.sf_soft.office.approval.model;

/**
 * SysApproveConditions entity. @author MyEclipse Persistence Tools
 */

public class ApproveConditions implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3406483202761910185L;
	private String conditionId;
	private String moduleId;
	private String sortNo;
	private String conditionField;
	private String conditionName;
	private String conditionType;
	private String expression;

	// Constructors

	/** default constructor */
	public ApproveConditions() {
	}

	/** minimal constructor */
	public ApproveConditions(String moduleId, String sortNo, String conditionField, String conditionName,
			String conditionType) {
		this.moduleId = moduleId;
		this.sortNo = sortNo;
		this.conditionField = conditionField;
		this.conditionName = conditionName;
		this.conditionType = conditionType;
	}

	/** full constructor */
	public ApproveConditions(String moduleId, String sortNo, String conditionField, String conditionName,
			String conditionType, String expression) {
		this.moduleId = moduleId;
		this.sortNo = sortNo;
		this.conditionField = conditionField;
		this.conditionName = conditionName;
		this.conditionType = conditionType;
		this.expression = expression;
	}

	// Property accessors

	public String getConditionId() {
		return this.conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getConditionField() {
		return this.conditionField;
	}

	public void setConditionField(String conditionField) {
		this.conditionField = conditionField;
	}

	public String getConditionName() {
		return this.conditionName;
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}

	public String getConditionType() {
		return this.conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

}
