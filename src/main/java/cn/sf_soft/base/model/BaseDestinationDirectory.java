package cn.sf_soft.base.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 送达地字典表
 * 
 * @author caigx
 *
 */
@Entity
@Table(name = "base_destination_directory")
public class BaseDestinationDirectory implements java.io.Serializable {
	private static final long serialVersionUID = 4104532818440684685L;

	// Fields
	private String destinationId;
	private String strains;
	private String areaCode;
	private String areaName;
	private String pactCode;
	private String dealerCode;
	private Boolean deleted;
	
	// Constructors
	public BaseDestinationDirectory(){
		
	}

	public BaseDestinationDirectory(String destinationId, String strains,
			String areaCode, String areaName, String pactCode,
			String dealerCode, Boolean deleted) {
		super();
		this.destinationId = destinationId;
		this.strains = strains;
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.pactCode = pactCode;
		this.dealerCode = dealerCode;
		this.deleted = deleted;
	}

	// Property accessors
	@Id
	@Column(name = "destination_id", unique = true, nullable = false, length = 40)
	public String getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}

	@Column(name = "strains")
	public String getStrains() {
		return strains;
	}

	public void setStrains(String strains) {
		this.strains = strains;
	}

	@Column(name = "area_code")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "area_name")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "pact_code")
	public String getPactCode() {
		return pactCode;
	}

	public void setPactCode(String pactCode) {
		this.pactCode = pactCode;
	}

	@Column(name = "dealer_code")
	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	@Column(name = "deleted")
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}