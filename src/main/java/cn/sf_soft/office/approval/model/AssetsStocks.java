package cn.sf_soft.office.approval.model;

// Generated 2015-4-28 15:14:05 by Hibernate Tools 3.4.0.CR1

import java.sql.Timestamp;


/** 
* @ClassName: AssetsStocks 
* @Description: 资产库存实体类
* @author xiongju
* @date 2015-4-29 上午10:58:48 
*  
*/
public class AssetsStocks implements java.io.Serializable
{
	private String assetsNo;
	private String stationId;
	private String assetsName;
	private String assetsKindName;
	private String assetsKindNo;
	private String assetsModel;
	private String assetsSpec;
	private Double quantity;
	private Double buyQuantity;
	private Double recQuantity;
	private Timestamp buyDate;
	private Double price;
	private Double accDepr;
	private Double marketValue;
	private Double netValue;
	private String supplierName;
	private Timestamp warrantyPeriodDate;
	private String depositPosition;
	private String tel;
	private String unit;
	private Short status;
	private String assetsTypeName;
	private String assetsBrandName;
	private String custodian;
	private String custodianNo;
	private Integer depreciationPeriod;
	private Double residualValue;
	private Short depreciationMode;
	private String depreciationModeName;
	private Double originalValue;
	private Double accDeprInit;
	private Double impairmentValue;
	private String shareDepts;
	private String deptNoGet;
	private String deptNameGet;
	private Double lossQuantity;
	private Short originPisType;
	private Integer currentPeriod;
	private Double currentDepr;
	private Double beforeDepr;
	private Double handleQuantity;
	private Short isChildren;
	private String parentAssetsNo;
	private String supplierId;
	private String supplierNo;
	private String remark;
	private String baseAssetsId;
	private String acctAccountTno;
	private String acctAccountTname;
	private String acctAccountTfullname;

	public AssetsStocks() {
	}

	public AssetsStocks(String assetsNo, String stationId) {
		this.assetsNo = assetsNo;
		this.stationId = stationId;
	}

	public AssetsStocks(String assetsNo, String stationId, String assetsName,
			String assetsKindName, String assetsKindNo, String assetsModel,
			String assetsSpec, Double quantity, Double buyQuantity,
			Double recQuantity, Timestamp buyDate, Double price,
			Double accDepr, Double marketValue, Double netValue,
			String supplierName, Timestamp warrantyPeriodDate,
			String depositPosition, String tel, String unit, Short status,
			String assetsTypeName, String assetsBrandName, String custodian,
			String custodianNo, Integer depreciationPeriod,
			Double residualValue, Short depreciationMode,
			String depreciationModeName, Double originalValue,
			Double accDeprInit, Double impairmentValue, String shareDepts,
			String deptNoGet, String deptNameGet, Double lossQuantity,
			Short originPisType, Integer currentPeriod, Double currentDepr,
			Double beforeDepr, Double handleQuantity, Short isChildren,
			String parentAssetsNo, String supplierId, String supplierNo,
			String remark, String baseAssetsId, String acctAccountTno,
			String acctAccountTname, String acctAccountTfullname) {
		this.assetsNo = assetsNo;
		this.stationId = stationId;
		this.assetsName = assetsName;
		this.assetsKindName = assetsKindName;
		this.assetsKindNo = assetsKindNo;
		this.assetsModel = assetsModel;
		this.assetsSpec = assetsSpec;
		this.quantity = quantity;
		this.buyQuantity = buyQuantity;
		this.recQuantity = recQuantity;
		this.buyDate = buyDate;
		this.price = price;
		this.accDepr = accDepr;
		this.marketValue = marketValue;
		this.netValue = netValue;
		this.supplierName = supplierName;
		this.warrantyPeriodDate = warrantyPeriodDate;
		this.depositPosition = depositPosition;
		this.tel = tel;
		this.unit = unit;
		this.status = status;
		this.assetsTypeName = assetsTypeName;
		this.assetsBrandName = assetsBrandName;
		this.custodian = custodian;
		this.custodianNo = custodianNo;
		this.depreciationPeriod = depreciationPeriod;
		this.residualValue = residualValue;
		this.depreciationMode = depreciationMode;
		this.depreciationModeName = depreciationModeName;
		this.originalValue = originalValue;
		this.accDeprInit = accDeprInit;
		this.impairmentValue = impairmentValue;
		this.shareDepts = shareDepts;
		this.deptNoGet = deptNoGet;
		this.deptNameGet = deptNameGet;
		this.lossQuantity = lossQuantity;
		this.originPisType = originPisType;
		this.currentPeriod = currentPeriod;
		this.currentDepr = currentDepr;
		this.beforeDepr = beforeDepr;
		this.handleQuantity = handleQuantity;
		this.isChildren = isChildren;
		this.parentAssetsNo = parentAssetsNo;
		this.supplierId = supplierId;
		this.supplierNo = supplierNo;
		this.remark = remark;
		this.baseAssetsId = baseAssetsId;
		this.acctAccountTno = acctAccountTno;
		this.acctAccountTname = acctAccountTname;
		this.acctAccountTfullname = acctAccountTfullname;
	}

	public String getAssetsNo()
	{
		return this.assetsNo;
	}

	public void setAssetsNo(String assetsNo)
	{
		this.assetsNo = assetsNo;
	}

	public String getStationId()
	{
		return this.stationId;
	}

	public void setStationId(String stationId)
	{
		this.stationId = stationId;
	}

	public String getAssetsName()
	{
		return this.assetsName;
	}

	public void setAssetsName(String assetsName)
	{
		this.assetsName = assetsName;
	}

	public String getAssetsKindName()
	{
		return this.assetsKindName;
	}

	public void setAssetsKindName(String assetsKindName)
	{
		this.assetsKindName = assetsKindName;
	}

	public String getAssetsKindNo()
	{
		return this.assetsKindNo;
	}

	public void setAssetsKindNo(String assetsKindNo)
	{
		this.assetsKindNo = assetsKindNo;
	}

	public String getAssetsModel()
	{
		return this.assetsModel;
	}

	public void setAssetsModel(String assetsModel)
	{
		this.assetsModel = assetsModel;
	}

	public String getAssetsSpec()
	{
		return this.assetsSpec;
	}

	public void setAssetsSpec(String assetsSpec)
	{
		this.assetsSpec = assetsSpec;
	}

	public Double getQuantity()
	{
		return this.quantity;
	}

	public void setQuantity(Double quantity)
	{
		this.quantity = quantity;
	}

	public Double getBuyQuantity()
	{
		return this.buyQuantity;
	}

	public void setBuyQuantity(Double buyQuantity)
	{
		this.buyQuantity = buyQuantity;
	}

	public Double getRecQuantity()
	{
		return this.recQuantity;
	}

	public void setRecQuantity(Double recQuantity)
	{
		this.recQuantity = recQuantity;
	}

	public Timestamp getBuyDate()
	{
		return this.buyDate;
	}

	public void setBuyDate(Timestamp buyDate)
	{
		this.buyDate = buyDate;
	}

	public Double getPrice()
	{
		return this.price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	public Double getAccDepr()
	{
		return this.accDepr;
	}

	public void setAccDepr(Double accDepr)
	{
		this.accDepr = accDepr;
	}

	public Double getMarketValue()
	{
		return this.marketValue;
	}

	public void setMarketValue(Double marketValue)
	{
		this.marketValue = marketValue;
	}

	public Double getNetValue()
	{
		return this.netValue;
	}

	public void setNetValue(Double netValue)
	{
		this.netValue = netValue;
	}

	public String getSupplierName()
	{
		return this.supplierName;
	}

	public void setSupplierName(String supplierName)
	{
		this.supplierName = supplierName;
	}

	public Timestamp getWarrantyPeriodDate()
	{
		return this.warrantyPeriodDate;
	}

	public void setWarrantyPeriodDate(Timestamp warrantyPeriodDate)
	{
		this.warrantyPeriodDate = warrantyPeriodDate;
	}

	public String getDepositPosition()
	{
		return this.depositPosition;
	}

	public void setDepositPosition(String depositPosition)
	{
		this.depositPosition = depositPosition;
	}

	public String getTel()
	{
		return this.tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getUnit()
	{
		return this.unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	public Short getStatus()
	{
		return this.status;
	}

	public void setStatus(Short status)
	{
		this.status = status;
	}

	public String getAssetsTypeName()
	{
		return this.assetsTypeName;
	}

	public void setAssetsTypeName(String assetsTypeName)
	{
		this.assetsTypeName = assetsTypeName;
	}

	public String getAssetsBrandName()
	{
		return this.assetsBrandName;
	}

	public void setAssetsBrandName(String assetsBrandName)
	{
		this.assetsBrandName = assetsBrandName;
	}

	public String getCustodian()
	{
		return this.custodian;
	}

	public void setCustodian(String custodian)
	{
		this.custodian = custodian;
	}

	public String getCustodianNo()
	{
		return this.custodianNo;
	}

	public void setCustodianNo(String custodianNo)
	{
		this.custodianNo = custodianNo;
	}

	public Integer getDepreciationPeriod()
	{
		return this.depreciationPeriod;
	}

	public void setDepreciationPeriod(Integer depreciationPeriod)
	{
		this.depreciationPeriod = depreciationPeriod;
	}

	public Double getResidualValue()
	{
		return this.residualValue;
	}

	public void setResidualValue(Double residualValue)
	{
		this.residualValue = residualValue;
	}

	public Short getDepreciationMode()
	{
		return this.depreciationMode;
	}

	public void setDepreciationMode(Short depreciationMode)
	{
		this.depreciationMode = depreciationMode;
	}

	public String getDepreciationModeName()
	{
		return this.depreciationModeName;
	}

	public void setDepreciationModeName(String depreciationModeName)
	{
		this.depreciationModeName = depreciationModeName;
	}

	public Double getOriginalValue()
	{
		return this.originalValue;
	}

	public void setOriginalValue(Double originalValue)
	{
		this.originalValue = originalValue;
	}

	public Double getAccDeprInit()
	{
		return this.accDeprInit;
	}

	public void setAccDeprInit(Double accDeprInit)
	{
		this.accDeprInit = accDeprInit;
	}

	public Double getImpairmentValue()
	{
		return this.impairmentValue;
	}

	public void setImpairmentValue(Double impairmentValue)
	{
		this.impairmentValue = impairmentValue;
	}

	public String getShareDepts()
	{
		return this.shareDepts;
	}

	public void setShareDepts(String shareDepts)
	{
		this.shareDepts = shareDepts;
	}

	public String getDeptNoGet()
	{
		return this.deptNoGet;
	}

	public void setDeptNoGet(String deptNoGet)
	{
		this.deptNoGet = deptNoGet;
	}

	public String getDeptNameGet()
	{
		return this.deptNameGet;
	}

	public void setDeptNameGet(String deptNameGet)
	{
		this.deptNameGet = deptNameGet;
	}

	public Double getLossQuantity()
	{
		return this.lossQuantity;
	}

	public void setLossQuantity(Double lossQuantity)
	{
		this.lossQuantity = lossQuantity;
	}

	public Short getOriginPisType()
	{
		return this.originPisType;
	}

	public void setOriginPisType(Short originPisType)
	{
		this.originPisType = originPisType;
	}

	public Integer getCurrentPeriod()
	{
		return this.currentPeriod;
	}

	public void setCurrentPeriod(Integer currentPeriod)
	{
		this.currentPeriod = currentPeriod;
	}

	public Double getCurrentDepr()
	{
		return this.currentDepr;
	}

	public void setCurrentDepr(Double currentDepr)
	{
		this.currentDepr = currentDepr;
	}

	public Double getBeforeDepr()
	{
		return this.beforeDepr;
	}

	public void setBeforeDepr(Double beforeDepr)
	{
		this.beforeDepr = beforeDepr;
	}

	public Double getHandleQuantity()
	{
		return this.handleQuantity;
	}

	public void setHandleQuantity(Double handleQuantity)
	{
		this.handleQuantity = handleQuantity;
	}

	public Short getIsChildren()
	{
		return this.isChildren;
	}

	public void setIsChildren(Short isChildren)
	{
		this.isChildren = isChildren;
	}

	public String getParentAssetsNo()
	{
		return this.parentAssetsNo;
	}

	public void setParentAssetsNo(String parentAssetsNo)
	{
		this.parentAssetsNo = parentAssetsNo;
	}

	public String getSupplierId()
	{
		return this.supplierId;
	}

	public void setSupplierId(String supplierId)
	{
		this.supplierId = supplierId;
	}

	public String getSupplierNo()
	{
		return this.supplierNo;
	}

	public void setSupplierNo(String supplierNo)
	{
		this.supplierNo = supplierNo;
	}

	public String getRemark()
	{
		return this.remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getBaseAssetsId()
	{
		return this.baseAssetsId;
	}

	public void setBaseAssetsId(String baseAssetsId)
	{
		this.baseAssetsId = baseAssetsId;
	}

	public String getAcctAccountTno()
	{
		return this.acctAccountTno;
	}

	public void setAcctAccountTno(String acctAccountTno)
	{
		this.acctAccountTno = acctAccountTno;
	}

	public String getAcctAccountTname()
	{
		return this.acctAccountTname;
	}

	public void setAcctAccountTname(String acctAccountTname)
	{
		this.acctAccountTname = acctAccountTname;
	}

	public String getAcctAccountTfullname()
	{
		return this.acctAccountTfullname;
	}

	public void setAcctAccountTfullname(String acctAccountTfullname)
	{
		this.acctAccountTfullname = acctAccountTfullname;
	}

}
