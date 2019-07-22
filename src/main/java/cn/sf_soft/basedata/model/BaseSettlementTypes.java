package cn.sf_soft.basedata.model;

/**
 * BaseSettlementTypes entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class BaseSettlementTypes implements java.io.Serializable {

	// Fields

	private String typeId;
	private String parentId;
	private String fullId;
	private Short status;
	private String typeNo;
	private String typeName;
	private Short settleType;
	private String fullNo;
	private Integer relatedAccount;
	private Integer relatedCashFlowClass;

	// Constructors

	/** default constructor */
	public BaseSettlementTypes() {
	}

	/** minimal constructor */
	public BaseSettlementTypes(String typeId, String fullId, Short status,
			String typeNo, String typeName, Short settleType, String fullNo) {
		this.typeId = typeId;
		this.fullId = fullId;
		this.status = status;
		this.typeNo = typeNo;
		this.typeName = typeName;
		this.settleType = settleType;
		this.fullNo = fullNo;
	}

	/** full constructor */
	public BaseSettlementTypes(String typeId, String parentId, String fullId,
			Short status, String typeNo, String typeName, Short settleType,
			String fullNo, Integer relatedAccount, Integer relatedCashFlowClass) {
		this.typeId = typeId;
		this.parentId = parentId;
		this.fullId = fullId;
		this.status = status;
		this.typeNo = typeNo;
		this.typeName = typeName;
		this.settleType = settleType;
		this.fullNo = fullNo;
		this.relatedAccount = relatedAccount;
		this.relatedCashFlowClass = relatedCashFlowClass;
	}

	// Property accessors

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
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

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getTypeNo() {
		return this.typeNo;
	}

	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Short getSettleType() {
		return this.settleType;
	}

	public void setSettleType(Short settleType) {
		this.settleType = settleType;
	}

	public String getFullNo() {
		return this.fullNo;
	}

	public void setFullNo(String fullNo) {
		this.fullNo = fullNo;
	}

	public Integer getRelatedAccount() {
		return this.relatedAccount;
	}

	public void setRelatedAccount(Integer relatedAccount) {
		this.relatedAccount = relatedAccount;
	}

	public Integer getRelatedCashFlowClass() {
		return this.relatedCashFlowClass;
	}

	public void setRelatedCashFlowClass(Integer relatedCashFlowClass) {
		this.relatedCashFlowClass = relatedCashFlowClass;
	}

}
