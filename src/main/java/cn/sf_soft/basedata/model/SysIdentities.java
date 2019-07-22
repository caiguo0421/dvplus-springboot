package cn.sf_soft.basedata.model;

/**
 * 记录一些表的自增长ID，
 * SysIdentities entity. @author MyEclipse Persistence Tools
 */

public class SysIdentities implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8680873629382205571L;
	private String tableName;
	private Integer nextNumber;
	private Short increment;

	// Constructors

	/** default constructor */
	public SysIdentities() {
	}

	/** full constructor */
	public SysIdentities(String tableName, Integer nextNumber, Short increment) {
		this.tableName = tableName;
		this.nextNumber = nextNumber;
		this.increment = increment;
	}

	// Property accessors

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getNextNumber() {
		return this.nextNumber;
	}

	public void setNextNumber(Integer nextNumber) {
		this.nextNumber = nextNumber;
	}

	public Short getIncrement() {
		return this.increment;
	}

	public void setIncrement(Short increment) {
		this.increment = increment;
	}

	@Override
	public String toString() {
		return "SysIdentities [tableName=" + tableName + ", nextNumber="
				+ nextNumber + ", increment=" + increment + "]";
	}

}
