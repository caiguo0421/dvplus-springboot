package cn.sf_soft.user.model;

/**
 * SysPopedoms entity. @author MyEclipse Persistence Tools
 */

public class SysPopedoms implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 764603879265271970L;
	private String popedomId;
	private String popedomType;
	private String module;
	private String popedomName;
	private String moduleId;

	// Constructors

	/** default constructor */
	public SysPopedoms() {
	}

	/** minimal constructor */
	public SysPopedoms(String popedomId, String popedomType, String module,
			String popedomName) {
		this.popedomId = popedomId;
		this.popedomType = popedomType;
		this.module = module;
		this.popedomName = popedomName;
	}

	/** full constructor */
	public SysPopedoms(String popedomId, String popedomType, String module,
			String popedomName, String moduleId) {
		this.popedomId = popedomId;
		this.popedomType = popedomType;
		this.module = module;
		this.popedomName = popedomName;
		this.moduleId = moduleId;
	}

	// Property accessors

	public String getPopedomId() {
		return this.popedomId;
	}

	public void setPopedomId(String popedomId) {
		this.popedomId = popedomId;
	}

	public String getPopedomType() {
		return this.popedomType;
	}

	public void setPopedomType(String popedomType) {
		this.popedomType = popedomType;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getPopedomName() {
		return this.popedomName;
	}

	public void setPopedomName(String popedomName) {
		this.popedomName = popedomName;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

}
