package cn.sf_soft.office.approval.model;

import java.sql.Timestamp;
import java.util.Iterator;

/**
 * VehicleLoanStandingBooks entity. @author MyEclipse Persistence Tools
 */

public class VehicleLoanStandingBooks implements java.io.Serializable {

	// Fields

	private String slcBookId;
	//private String slcDetailId;
	private String slcNo;
	private Integer periodNumber;
	//private Double surplusPrincipal;
	/*private Double splitAmount;
	private Double splitPrincipal;
	private Double splitInterest;*/
	private Timestamp arDate;
	private Double arAmount;
	private Double arPrincipal;
	private Double arInterest;
	private Timestamp payDate;
	private Double payAmount;
	private Double payPrincipal;
	private Double payInterest;
	private Boolean isAdvance;
	//private Boolean isSubstitute;
	private Short status;
	private Boolean isAheadPay;
	//private Boolean isValid;
	private String remark;
	//private String creator;
	private Timestamp createTime;
	private Double arPrincipalDue;
	private Double arInterestDue;
	private Double arAmountDue;
	//private Short planType;
	//private String objectId;
	//private String objectName;
	//private String bankId;
	//private String bankName;
	private Short bookType;
	//private AccountsReceivableInstalment instalMent;
	// Constructors

	
	

	/** default constructor */
	public VehicleLoanStandingBooks() {
	}

	/** minimal constructor */
	public VehicleLoanStandingBooks(String slcBookId, String slcNo) {
		this.slcBookId = slcBookId;
		this.slcNo = slcNo;
	}

	/** full constructor */
	public VehicleLoanStandingBooks(String slcBookId, String slcDetailId,
			String slcNo, Integer periodNumber, Double surplusPrincipal,
			Timestamp arDate, Double arAmount, Double arPrincipal,
			Double arInterest, Timestamp payDate, Double payAmount,
			Double payPrincipal, Double payInterest, Boolean isAdvance,
			Boolean isSubstitute, Short status, Boolean isAheadPay,
			Boolean isValid, String remark, String creator,
			Timestamp createTime, Double arPrincipalDue, Double arInterestDue,
			Double arAmountDue, Short planType, String objectId,
			String objectName, String bankId, String bankName, Short bookType) {
		this.slcBookId = slcBookId;
		//this.slcDetailId = slcDetailId;
		this.slcNo = slcNo;
		this.periodNumber = periodNumber;
		//this.surplusPrincipal = surplusPrincipal;
		this.arDate = arDate;
		this.arAmount = arAmount;
		this.arPrincipal = arPrincipal;
		this.arInterest = arInterest;
		this.payDate = payDate;
		this.payAmount = payAmount;
		this.payPrincipal = payPrincipal;
		this.payInterest = payInterest;
		this.isAdvance = isAdvance;
		//this.isSubstitute = isSubstitute;
		this.status = status;
		this.isAheadPay = isAheadPay;
		//this.isValid = isValid;
		this.remark = remark;
		//this.creator = creator;
		this.createTime = createTime;
		this.arPrincipalDue = arPrincipalDue;
		this.arInterestDue = arInterestDue;
		this.arAmountDue = arAmountDue;
		/*this.planType = planType;
		this.objectId = objectId;
		this.objectName = objectName;
		this.bankId = bankId;
		this.bankName = bankName;*/
		this.bookType = bookType;
	}

	// Property accessors

	public String getSlcBookId() {
		return this.slcBookId;
	}

	public void setSlcBookId(String slcBookId) {
		this.slcBookId = slcBookId;
	}

	/*public String getSlcDetailId() {
		return this.slcDetailId;
	}

	public void setSlcDetailId(String slcDetailId) {
		this.slcDetailId = slcDetailId;
	}*/

	public String getSlcNo() {
		return this.slcNo;
	}

	public void setSlcNo(String slcNo) {
		this.slcNo = slcNo;
	}

	public Integer getPeriodNumber() {
		return this.periodNumber;
	}

	public void setPeriodNumber(Integer periodNumber) {
		this.periodNumber = periodNumber;
	}

	/*public Double getSurplusPrincipal() {
		return this.surplusPrincipal;
	}

	public void setSurplusPrincipal(Double surplusPrincipal) {
		this.surplusPrincipal = surplusPrincipal;
	}*/

	public Timestamp getArDate() {
		return this.arDate;
	}

	public void setArDate(Timestamp arDate) {
		this.arDate = arDate;
	}

	public Double getArAmount() {
		return this.arAmount;
	}

	public void setArAmount(Double arAmount) {
		this.arAmount = arAmount;
	}

	public Double getArPrincipal() {
		return this.arPrincipal;
	}

	public void setArPrincipal(Double arPrincipal) {
		this.arPrincipal = arPrincipal;
	}

	public Double getArInterest() {
		return this.arInterest;
	}

	public void setArInterest(Double arInterest) {
		this.arInterest = arInterest;
	}

	public Timestamp getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Timestamp payDate) {
		this.payDate = payDate;
	}

	public Double getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getPayPrincipal() {
		return this.payPrincipal;
	}

	public void setPayPrincipal(Double payPrincipal) {
		this.payPrincipal = payPrincipal;
	}

	public Double getPayInterest() {
		return this.payInterest;
	}

	public void setPayInterest(Double payInterest) {
		this.payInterest = payInterest;
	}

	public Boolean getIsAdvance() {
		return this.isAdvance;
	}

	public void setIsAdvance(Boolean isAdvance) {
		this.isAdvance = isAdvance;
	}

	/*public Boolean getIsSubstitute() {
		return this.isSubstitute;
	}

	public void setIsSubstitute(Boolean isSubstitute) {
		this.isSubstitute = isSubstitute;
	}*/

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Boolean getIsAheadPay() {
		return this.isAheadPay;
	}

	public void setIsAheadPay(Boolean isAheadPay) {
		this.isAheadPay = isAheadPay;
	}

	/*public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}*/

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/*public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}*/

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Double getArPrincipalDue() {
		return this.arPrincipalDue;
	}

	public void setArPrincipalDue(Double arPrincipalDue) {
		this.arPrincipalDue = arPrincipalDue;
	}

	public Double getArInterestDue() {
		return this.arInterestDue;
	}

	public void setArInterestDue(Double arInterestDue) {
		this.arInterestDue = arInterestDue;
	}

	public Double getArAmountDue() {
		return this.arAmountDue;
	}

	public void setArAmountDue(Double arAmountDue) {
		this.arAmountDue = arAmountDue;
	}

	/*public Short getPlanType() {
		return this.planType;
	}

	public void setPlanType(Short planType) {
		this.planType = planType;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getBankId() {
		return this.bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
*/
	public Short getBookType() {
		return this.bookType;
	}

	public void setBookType(Short bookType) {
		this.bookType = bookType;
	}
public String toString() {
		
		return "VehicleLoanStandingBooks [slcBookId=" + slcBookId
				+ ", slcNo="
				+ slcNo + ", periodNumber=" + periodNumber
				+ ", arDate="
				+ arDate + ", arAmount="
				+ arAmount + ", arPrincipal=" + arPrincipal + ", arInterest="
				+ arInterest + ", payDate=" + payDate
				+ ", payAmount=" + payAmount + ", payPrincipal="
				+ payPrincipal + ", payInterest=" + payInterest + ", isAdvance=" + isAdvance
				+", status=" + status+", isAheadPay=" + isAheadPay
				+", remark=" + remark+", createTime=" + createTime+", arPrincipalDue=" + arPrincipalDue+", arInterestDue=" + arInterestDue+
				", arAmountDue=" + arAmountDue+
				", bookType=" + bookType
				;
				
	}
	
}
