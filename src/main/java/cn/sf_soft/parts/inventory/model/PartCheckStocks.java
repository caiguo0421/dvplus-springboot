package cn.sf_soft.parts.inventory.model;

import java.sql.Timestamp;
import java.util.List;

import cn.sf_soft.common.gson.GsonSerializeIgnore;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.ExpenseReimbursementsDetails;

/**
 * 配件库存盘整
 */
public class PartCheckStocks extends
		ApproveDocuments<PartCheckStocksDetail> implements
		java.io.Serializable {

	private static final long serialVersionUID = 7528871592142242490L;
	private Double pcsPrice;
	private String remark;
	private String stationName; //关联字段
	private Integer inventoryType;
	private String creator;
	private String creatorNo;
	private Timestamp createTime;
	private String modifier;
	private String creatorUnitNo;
	private String creatorUnitName;
	private String approver;
	private String approverUnitNo;
	private String approverUnitName;
	
	//盘存明细
	private List<PartCheckStocksDetail> partCheckStockDetails;

	//扩展属性
	private Integer planCheckQuantity;
	private Integer checkQuantity;
	private Short checkStatus;

	public Double getPcsPrice() {
		return pcsPrice;
	}

	public void setPcsPrice(Double pcsPrice) {
		this.pcsPrice = pcsPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(Integer inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorNo() {
		return creatorNo;
	}

	public void setCreatorNo(String creatorNo) {
		this.creatorNo = creatorNo;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getCreatorUnitNo() {
		return creatorUnitNo;
	}

	public void setCreatorUnitNo(String creatorUnitNo) {
		this.creatorUnitNo = creatorUnitNo;
	}

	public String getCreatorUnitName() {
		return creatorUnitName;
	}

	public void setCreatorUnitName(String creatorUnitName) {
		this.creatorUnitName = creatorUnitName;
	}

	public List<PartCheckStocksDetail> getPartCheckStockDetails() {
		return partCheckStockDetails;
	}

	public void setPartCheckStockDetails(List<PartCheckStocksDetail> partCheckStockDetails) {
		this.partCheckStockDetails = partCheckStockDetails;
	}

	public String getApproverUnitNo() {
		return approverUnitNo;
	}

	public void setApproverUnitNo(String approverUnitNo) {
		this.approverUnitNo = approverUnitNo;
	}

	public String getApproverUnitName() {
		return approverUnitName;
	}

	public void setApproverUnitName(String approverUnitName) {
		this.approverUnitName = approverUnitName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Integer getPlanCheckQuantity() {
		return planCheckQuantity;
	}

	public void setPlanCheckQuantity(Integer planCheckQuantity) {
		this.planCheckQuantity = planCheckQuantity;
	}

	public Integer getCheckQuantity() {
		return checkQuantity;
	}

	public void setCheckQuantity(Integer checkQuantity) {
		this.checkQuantity = checkQuantity;
	}

	public Short getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}
}
