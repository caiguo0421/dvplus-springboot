package cn.sf_soft.office.approval.model;

/**
 * 厂家赠品变更
 * @author caigx
 *
 */
public class VehicleSaleContractGiftsVary implements java.io.Serializable {

	private static final long serialVersionUID = 8677190745144789988L;
	private String saleContractGiftsVaryId;
	private String detailVaryId;
	private String giftId;
	private String contractDetailId;
	private String detailId;
	private String itemId;
	private String itemName;
	private Short itemType;
	private Double amount;
	private Short giveFlag;
	private String remark;
	private Short abortStatus;
	private String abortComment;
	private String contractNo;

	// Constructors

	public VehicleSaleContractGiftsVary() {
	}

	// Property accessors
	public String getSaleContractGiftsVaryId() {
		return this.saleContractGiftsVaryId;
	}

	public void setSaleContractGiftsVaryId(String saleContractGiftsVaryId) {
		this.saleContractGiftsVaryId = saleContractGiftsVaryId;
	}

	public String getDetailVaryId() {
		return this.detailVaryId;
	}

	public void setDetailVaryId(String detailVaryId) {
		this.detailVaryId = detailVaryId;
	}

	public String getGiftId() {
		return this.giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	public String getContractDetailId() {
		return this.contractDetailId;
	}

	public void setContractDetailId(String contractDetailId) {
		this.contractDetailId = contractDetailId;
	}

	public String getDetailId() {
		return this.detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Short getItemType() {
		return this.itemType;
	}

	public void setItemType(Short itemType) {
		this.itemType = itemType;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Short getGiveFlag() {
		return this.giveFlag;
	}

	public void setGiveFlag(Short giveFlag) {
		this.giveFlag = giveFlag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getAbortStatus() {
		return this.abortStatus;
	}

	public void setAbortStatus(Short abortStatus) {
		this.abortStatus = abortStatus;
	}

	public String getAbortComment() {
		return this.abortComment;
	}

	public void setAbortComment(String abortComment) {
		this.abortComment = abortComment;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

}
