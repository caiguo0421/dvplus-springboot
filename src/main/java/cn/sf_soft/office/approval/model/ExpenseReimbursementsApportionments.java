package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;

/**
 * OfficeExpenseReimbursementsApportionments entity. @author MyEclipse
 * Persistence Tools
 */

public class ExpenseReimbursementsApportionments implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1287812196177097690L;
	// Fields

	private String oeraId;
	private String documentNo;
	private String departmentId;
	private String departmentNo;
	private String departmentName;
	private Double apportionAmount;
	private Timestamp apportionTime;
	
	private String unitRelation;//所关联的部门
	private String stationId;//站点ID
	
	// Constructors

	//2013-12-13 修改为每个费用明细对应多个分摊部门  by liujin
	private String oerdId;//对应的费用明细ID
	private String remark;

	/** default constructor */
	public ExpenseReimbursementsApportionments() {
	}
	
	/** minimal constructor */
	public ExpenseReimbursementsApportionments(String oeraId,
			String documentNo, String departmentId, String departmentNo,
			String departmentName, Double apportionAmount) {
		this.oeraId = oeraId;
		this.documentNo = documentNo;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.apportionAmount = apportionAmount;
	}

	/** full constructor */
	public ExpenseReimbursementsApportionments(String oeraId,
			String documentNo, String departmentId, String departmentNo,
			String departmentName, Double apportionAmount,
			Timestamp apportionTime) {
		this.oeraId = oeraId;
		this.documentNo = documentNo;
		this.departmentId = departmentId;
		this.departmentNo = departmentNo;
		this.departmentName = departmentName;
		this.apportionAmount = apportionAmount;
		this.apportionTime = apportionTime;
	}

	public String getOeraId() {
		return oeraId;
	}

	public void setOeraId(String oeraId) {
		this.oeraId = oeraId;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentNo() {
		return departmentNo;
	}

	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Double getApportionAmount() {
		return apportionAmount;
	}

	public void setApportionAmount(Double apportionAmount) {
		this.apportionAmount = apportionAmount;
	}

	public Timestamp getApportionTime() {
		return apportionTime;
	}

	public void setApportionTime(Timestamp apportionTime) {
		this.apportionTime = apportionTime;
	}

	public String getUnitRelation() {
		return unitRelation;
	}

	public void setUnitRelation(String unitRelation) {
		this.unitRelation = unitRelation;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getOerdId() {
		return oerdId;
	}

	public void setOerdId(String oerdId) {
		this.oerdId = oerdId;
	}

	@Override
	public String toString() {
		return "ExpenseReimbursementsApportionments [oeraId=" + oeraId
				+ ", documentNo=" + documentNo + ", departmentId="
				+ departmentId + ", departmentNo=" + departmentNo
				+ ", departmentName=" + departmentName + ", apportionAmount="
				+ apportionAmount + ", apportionTime=" + apportionTime
				+ ", unitRelation=" + unitRelation + ", stationId=" + stationId
				+ ", oerdId=" + oerdId + "]";
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
