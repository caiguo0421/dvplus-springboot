package cn.sf_soft.basedata.model;


/**
 * 基础资料-配件类型
 * BasePartType entity. @author MyEclipse Persistence Tools
 */

public class BasePartType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8735189035297675072L;
	private String basePartTypeId;
	private String parentId;
	private Boolean isParent;
	private Short status;
	private String partTypeName;
	private Integer orderNo;
	
//	A4S7 数据库才有的字段
//	private String partTypeAcctCode;
//	private String partTypeShortCode;
//	private String partTypeShortName;
	
//	private String fullId;
//	private String remark;
//	private String creator;
//	private Timestamp createTime;
//	private Timestamp lastUpdateTime;
//	private String lastUpdateUser;

	// Constructors

	/** default constructor */
	public BasePartType() {
	}

	/** minimal constructor */
	public BasePartType(String basePartTypeId) {
		this.basePartTypeId = basePartTypeId;
	}


	// Property accessors

	public String getBasePartTypeId() {
		return this.basePartTypeId;
	}

	public void setBasePartTypeId(String basePartTypeId) {
		this.basePartTypeId = basePartTypeId;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Boolean getIsParent() {
		return this.isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getPartTypeName() {
		return this.partTypeName;
	}

	public void setPartTypeName(String partTypeName) {
		this.partTypeName = partTypeName;
	}


	public Integer getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
/*
	public String getPartTypeAcctCode() {
		return this.partTypeAcctCode;
	}

	public void setPartTypeAcctCode(String partTypeAcctCode) {
		this.partTypeAcctCode = partTypeAcctCode;
	}

	public String getPartTypeShortCode() {
		return this.partTypeShortCode;
	}

	public void setPartTypeShortCode(String partTypeShortCode) {
		this.partTypeShortCode = partTypeShortCode;
	}

	public String getPartTypeShortName() {
		return this.partTypeShortName;
	}

	public void setPartTypeShortName(String partTypeShortName) {
		this.partTypeShortName = partTypeShortName;
	}
*/
}
