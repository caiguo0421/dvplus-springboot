package cn.sf_soft.office.approval.model;

/**
 * 车辆销售合同-赠品
 * 
 * @author caigx
 *
 */
public class VehicleSaleContractGifts implements java.io.Serializable {

	private static final long serialVersionUID = -6176619076855564616L;
	private String giftId;
	private String contractDetailId;
	private String detailId;
	private String itemId;
	private String itemName;
	private Short itemType;
	private Double amount = 0d;
	private Short giveFlag;
	private String remark;
	private Short abortStatus;
	private String contractNo;
	
	private String itemTypeName; //赠品项目类型 中文

	public VehicleSaleContractGifts() {
	}

	public VehicleSaleContractGifts(String giftId, String contractDetailId, String itemId, String itemName,
			Short itemType, Short giveFlag) {
		this.giftId = giftId;
		this.contractDetailId = contractDetailId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemType = itemType;
		this.giveFlag = giveFlag;
	}

	public VehicleSaleContractGifts(String giftId, String contractDetailId, String detailId, String itemId,
			String itemName, Short itemType, Double amount, Short giveFlag, String remark, Short abortStatus,
			String contractNo) {
		this.giftId = giftId;
		this.contractDetailId = contractDetailId;
		this.detailId = detailId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemType = itemType;
		this.amount = amount;
		this.giveFlag = giveFlag;
		this.remark = remark;
		this.abortStatus = abortStatus;
		this.contractNo = contractNo;
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

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

}
