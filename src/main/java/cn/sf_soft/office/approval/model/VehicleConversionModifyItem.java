package cn.sf_soft.office.approval.model;

/**
 * 车辆改装修改销售合同预定项目
 * @author caigx
 *
 */
public class VehicleConversionModifyItem implements java.io.Serializable {

	private static final long serialVersionUID = -6673899719394642836L;
	private String modifyId;
	private String conversionNo;
	private String vscdId;
	private String vsccId;
	private String itemId;
	private String itemNo;
	private String itemName;
	private Double itemCost;
	private Double income;
	private Double oriItemCost;
	private Double oriIncome;
	private Short status;
	private Short varyType;
	private String comment;
	private String oriItemId;
	private String oriItemNo;
	private String oriItemName;

	/** default constructor */
	public VehicleConversionModifyItem() {
	}

	public String getModifyId() {
		return this.modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public String getConversionNo() {
		return this.conversionNo;
	}

	public void setConversionNo(String conversionNo) {
		this.conversionNo = conversionNo;
	}

	public String getVscdId() {
		return this.vscdId;
	}

	public void setVscdId(String vscdId) {
		this.vscdId = vscdId;
	}

	public String getVsccId() {
		return this.vsccId;
	}

	public void setVsccId(String vsccId) {
		this.vsccId = vsccId;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getItemCost() {
		return this.itemCost;
	}

	public void setItemCost(Double itemCost) {
		this.itemCost = itemCost;
	}

	public Double getIncome() {
		return this.income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getOriItemCost() {
		return this.oriItemCost;
	}

	public void setOriItemCost(Double oriItemCost) {
		this.oriItemCost = oriItemCost;
	}

	public Double getOriIncome() {
		return this.oriIncome;
	}

	public void setOriIncome(Double oriIncome) {
		this.oriIncome = oriIncome;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getVaryType() {
		return this.varyType;
	}

	public void setVaryType(Short varyType) {
		this.varyType = varyType;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOriItemId() {
		return this.oriItemId;
	}

	public void setOriItemId(String oriItemId) {
		this.oriItemId = oriItemId;
	}

	public String getOriItemNo() {
		return this.oriItemNo;
	}

	public void setOriItemNo(String oriItemNo) {
		this.oriItemNo = oriItemNo;
	}

	public String getOriItemName() {
		return this.oriItemName;
	}

	public void setOriItemName(String oriItemName) {
		this.oriItemName = oriItemName;
	}

}
