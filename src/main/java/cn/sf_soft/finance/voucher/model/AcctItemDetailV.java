package cn.sf_soft.finance.voucher.model;

/**
 * AcctItemDetailV entity. @author MyEclipse Persistence Tools
 */

public class AcctItemDetailV implements java.io.Serializable {

	// Fields

	/**
		 * 
		 */
	private static final long serialVersionUID = -6836763995540505937L;
	private Integer tdetailId;
	private Integer titemClassId;
	private Integer titemId;

	// Constructors

	/** default constructor */
	public AcctItemDetailV() {
	}

	/** full constructor */
	public AcctItemDetailV(Integer tdetailId, Integer titemClassId,
			Integer titemId) {
		this.tdetailId = tdetailId;
		this.titemClassId = titemClassId;
		this.titemId = titemId;
	}

	// Property accessors

	public Integer getTdetailId() {
		return this.tdetailId;
	}

	public void setTdetailId(Integer tdetailId) {
		this.tdetailId = tdetailId;
	}

	public Integer getTitemClassId() {
		return this.titemClassId;
	}

	public void setTitemClassId(Integer titemClassId) {
		this.titemClassId = titemClassId;
	}

	public Integer getTitemId() {
		return this.titemId;
	}

	public void setTitemId(Integer titemId) {
		this.titemId = titemId;
	}

	@Override
	public String toString() {
		return "AcctItemDetailV [tdetailId=" + tdetailId + ", titemClassId="
				+ titemClassId + ", titemId=" + titemId + "]";
	}

}
