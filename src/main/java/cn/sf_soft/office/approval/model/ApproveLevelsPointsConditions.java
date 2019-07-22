package cn.sf_soft.office.approval.model;


public class ApproveLevelsPointsConditions implements java.io.Serializable {

	private static final long serialVersionUID = 3883820458247945188L;
	private String balpcId;
	private String levelId;
	private String balpId;
	private String conditionField;
	private String conditionValue;
	private String conditionItem;
	
	private Boolean conditionExcept;//例外
	
	public ApproveLevelsPointsConditions() {
	}

	
	// Property accessors

	public String getBalpcId() {
		return this.balpcId;
	}

	public void setBalpcId(String balpcId) {
		this.balpcId = balpcId;
	}

	public String getLevelId() {
		return this.levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getBalpId() {
		return this.balpId;
	}

	public void setBalpId(String balpId) {
		this.balpId = balpId;
	}

	public String getConditionField() {
		return this.conditionField;
	}

	public void setConditionField(String conditionField) {
		this.conditionField = conditionField;
	}

	public String getConditionValue() {
		return this.conditionValue;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public String getConditionItem() {
		return this.conditionItem;
	}

	public void setConditionItem(String conditionItem) {
		this.conditionItem = conditionItem;
	}


	public Boolean getConditionExcept() {
		return conditionExcept;
	}


	public void setConditionExcept(Boolean conditionExcept) {
		this.conditionExcept = conditionExcept;
	}
	
}
