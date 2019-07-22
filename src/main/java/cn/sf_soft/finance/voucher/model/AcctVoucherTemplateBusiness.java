package cn.sf_soft.finance.voucher.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AcctVoucherTemplateBusiness entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "acct_voucher_template_business")
public class AcctVoucherTemplateBusiness implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5601999448708956307L;
	private Long id;
	private Integer tvoucherId;
	private String businessNo;
	private String tno;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public AcctVoucherTemplateBusiness() {
	}

	/** full constructor */
	public AcctVoucherTemplateBusiness(Integer tvoucherId, String businessNo,
			String tno, Timestamp createTime) {
		this.tvoucherId = tvoucherId;
		this.businessNo = businessNo;
		this.tno = tno;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "tvoucher_id")
	public Integer getTvoucherId() {
		return this.tvoucherId;
	}

	public void setTvoucherId(Integer tvoucherId) {
		this.tvoucherId = tvoucherId;
	}

	@Column(name = "business_no", length = 40)
	public String getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	@Column(name = "tno", length = 40)
	public String getTno() {
		return this.tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	@Column(name = "create_time", length = 23)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("AcctVoucherTemplateBusiness [ tvoucherId=");
		sb.append(tvoucherId);
		sb.append(", businessNo=");
		sb.append(businessNo);
		sb.append(", tno=");
		sb.append(tno);
		sb.append(", createTime=");
		sb.append(createTime);
		sb.append(" ]");
		return sb.toString();
	}
}
