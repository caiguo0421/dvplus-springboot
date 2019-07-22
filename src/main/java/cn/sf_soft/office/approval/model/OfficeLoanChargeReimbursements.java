package cn.sf_soft.office.approval.model;



import java.sql.Timestamp;

/**
 * 消贷费用报销
 */

public class OfficeLoanChargeReimbursements extends
ApproveDocuments<OfficeLoanChargeReimbursementsDetails> implements java.io.Serializable {
	// Fields
	private static final long serialVersionUID = 1L;
	private Double reimburseAmount;
	private Timestamp reimburseTime;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String modifier;
	private Double paidAmount;
	private Timestamp paidTime;
	
	
	private String vehicleBrand; //新增 vehicleBrand 20170608
	private String fileUrls;

	// Constructors

	/** default constructor */
	public OfficeLoanChargeReimbursements() {
	}

	
	// Property accessors

	public Double getReimburseAmount() {
		return this.reimburseAmount;
	}

	public void setReimburseAmount(Double reimburseAmount) {
		this.reimburseAmount = reimburseAmount;
	}

	public Timestamp getReimburseTime() {
		return this.reimburseTime;
	}

	public void setReimburseTime(Timestamp reimburseTime) {
		this.reimburseTime = reimburseTime;
	}
	
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}


	public Double getPaidAmount() {
		return paidAmount;
	}


	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}


	public Timestamp getPaidTime() {
		return paidTime;
	}


	public void setPaidTime(Timestamp paidTime) {
		this.paidTime = paidTime;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}


	public String getFileUrls() {
		return fileUrls;
	}

	public void setFileUrls(String fileUrls) {
		this.fileUrls = fileUrls;
	}
}
