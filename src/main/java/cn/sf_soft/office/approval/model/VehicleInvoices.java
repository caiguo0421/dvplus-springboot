package cn.sf_soft.office.approval.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 车辆销售合同-发票
 * 
 * @author caigx
 *
 */
public class VehicleInvoices implements java.io.Serializable {
	
	private static final long serialVersionUID = 6440484578582747293L;
	private String invoicesDetailId;
	private String contractDetailId;
	private String invoiceType;
	private BigDecimal invoiceAmount = BigDecimal.ZERO;
	private String objectId;
	private String objectNo;
	private String objectName;
	private String remark;
	private String creator;
	private Timestamp createTime;
	private String contractNo;
	private String invoiceGroupId;

	public VehicleInvoices() {
	}


	public String getInvoicesDetailId() {
		return this.invoicesDetailId;
	}

	public void setInvoicesDetailId(String invoicesDetailId) {
		this.invoicesDetailId = invoicesDetailId;
	}

	public String getContractDetailId() {
		return this.contractDetailId;
	}

	public void setContractDetailId(String contractDetailId) {
		this.contractDetailId = contractDetailId;
	}

	public String getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public BigDecimal getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectNo() {
		return this.objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
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

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getInvoiceGroupId() {
		return invoiceGroupId;
	}

	public void setInvoiceGroupId(String invoiceGroupId) {
		this.invoiceGroupId = invoiceGroupId;
	}
}
