package cn.sf_soft.office.approval.model;

/**
 * VwFinanceGuarantors entity. @author MyEclipse Persistence Tools
 */

public class VwFinanceGuarantors implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7571100908439133136L;
	private VwFinanceGuarantorsId id;

	// Constructors

	/** default constructor */
	public VwFinanceGuarantors() {
	}

	/** full constructor */
	public VwFinanceGuarantors(VwFinanceGuarantorsId id) {
		this.id = id;
	}

	// Property accessors

	public VwFinanceGuarantorsId getId() {
		return this.id;
	}

	public void setId(VwFinanceGuarantorsId id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "VwFinanceGuarantors [id=" + id + "]";
	}

}
