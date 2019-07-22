package cn.sf_soft.basedata.model;

/**
 * SysFlags entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class SysFlags implements java.io.Serializable {

	// Fields

	private String flagId;
	private String fieldNo;
	private Short code;
	private String meaning;

	// Constructors

	/** default constructor */
	public SysFlags() {
	}

	/** full constructor */
	public SysFlags(String fieldNo, Short code, String meaning) {
		this.fieldNo = fieldNo;
		this.code = code;
		this.meaning = meaning;
	}

	// Property accessors

	public String getFlagId() {
		return this.flagId;
	}

	public void setFlagId(String flagId) {
		this.flagId = flagId;
	}

	public String getFieldNo() {
		return this.fieldNo;
	}

	public void setFieldNo(String fieldNo) {
		this.fieldNo = fieldNo;
	}

	public Short getCode() {
		return this.code;
	}

	public void setCode(Short code) {
		this.code = code;
	}

	public String getMeaning() {
		return this.meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	@Override
	public String toString() {
		return "SysFlags [flagId=" + flagId + ", fieldNo=" + fieldNo
				+ ", code=" + code + ", meaning=" + meaning + "]";
	}
	

}
