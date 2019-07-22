package cn.sf_soft.office.approval.model;

// Generated 2015-4-28 15:14:05 by Hibernate Tools 3.4.0.CR1

import java.sql.Timestamp;


/** 
* @ClassName: AssetsOutStockDetail 
* @Description: 资产出库明细实体类
* @author xiongju
* @date 2015-4-29 上午11:01:17 
*  
*/
public class AssetsOutStockDetail implements java.io.Serializable
{

	private String aosdId;
	private String documentNo;
	private String assetsNo;
	private String assetsName;
	private String assetsKindName;
	private String assetsKindNo;
	private String assetsModel;
	private String assetsSpec;
	private Double posQuantity;
	private Double pisQuantity;
	private Double lossQuantity;
	private Double handleQuantity;
	private Double posPrice;
	private String custodian;
	private String custodianNo;
	private String unit;
	private String DRemark;
	private String recNo;
	private Timestamp buyDate;
	private Double marketValue;
	private Double netValue;
	private String tel;
	private String originId;
	private Double accDepr;
	private String supplierName;
	private Timestamp warrantyPeriodDate;
	private Integer errorFlag;
	private String depositPosition;
	private String assetsTypeName;
	private String assetsBrandName;
	private String shareDepts;
	private Short depreciationMode;
	private String depreciationModeName;
	private Double residualValue;
	private Double originalValue;
	private Double accDeprInit;
	private Integer depreciationPeriod;
	private Integer currentPeriod;
	private Double currentDepr;
	private Double beforeDepr;
	private String deptNoGet;
	private String deptNameGet;
	private String supplierId;
	private String supplierNo;
	private Integer hireOutType;
	private String hireOutTypeName;
	private String deptNoGetTemp;
	private String baseAssetsId;

	public AssetsOutStockDetail() {
	}

	public AssetsOutStockDetail(String aosdId) {
		this.aosdId = aosdId;
	}

	public AssetsOutStockDetail(String aosdId, String documentNo,
			String assetsNo, String assetsName, String assetsKindName,
			String assetsKindNo, String assetsModel, String assetsSpec,
			Double posQuantity, Double pisQuantity, Double lossQuantity,
			Double handleQuantity, Double posPrice, String custodian,
			String custodianNo, String unit, String DRemark, String recNo,
			Timestamp buyDate, Double marketValue, Double netValue, String tel,
			String originId, Double accDepr, String supplierName,
			Timestamp warrantyPeriodDate, Integer errorFlag,
			String depositPosition, String assetsTypeName,
			String assetsBrandName, String shareDepts, Short depreciationMode,
			String depreciationModeName, Double residualValue,
			Double originalValue, Double accDeprInit,
			Integer depreciationPeriod, Integer currentPeriod,
			Double currentDepr, Double beforeDepr, String deptNoGet,
			String deptNameGet, String supplierId, String supplierNo,
			Integer hireOutType, String hireOutTypeName, String deptNoGetTemp,
			String baseAssetsId) {
		this.aosdId = aosdId;
		this.documentNo = documentNo;
		this.assetsNo = assetsNo;
		this.assetsName = assetsName;
		this.assetsKindName = assetsKindName;
		this.assetsKindNo = assetsKindNo;
		this.assetsModel = assetsModel;
		this.assetsSpec = assetsSpec;
		this.posQuantity = posQuantity;
		this.pisQuantity = pisQuantity;
		this.lossQuantity = lossQuantity;
		this.handleQuantity = handleQuantity;
		this.posPrice = posPrice;
		this.custodian = custodian;
		this.custodianNo = custodianNo;
		this.unit = unit;
		this.DRemark = DRemark;
		this.recNo = recNo;
		this.buyDate = buyDate;
		this.marketValue = marketValue;
		this.netValue = netValue;
		this.tel = tel;
		this.originId = originId;
		this.accDepr = accDepr;
		this.supplierName = supplierName;
		this.warrantyPeriodDate = warrantyPeriodDate;
		this.errorFlag = errorFlag;
		this.depositPosition = depositPosition;
		this.assetsTypeName = assetsTypeName;
		this.assetsBrandName = assetsBrandName;
		this.shareDepts = shareDepts;
		this.depreciationMode = depreciationMode;
		this.depreciationModeName = depreciationModeName;
		this.residualValue = residualValue;
		this.originalValue = originalValue;
		this.accDeprInit = accDeprInit;
		this.depreciationPeriod = depreciationPeriod;
		this.currentPeriod = currentPeriod;
		this.currentDepr = currentDepr;
		this.beforeDepr = beforeDepr;
		this.deptNoGet = deptNoGet;
		this.deptNameGet = deptNameGet;
		this.supplierId = supplierId;
		this.supplierNo = supplierNo;
		this.hireOutType = hireOutType;
		this.hireOutTypeName = hireOutTypeName;
		this.deptNoGetTemp = deptNoGetTemp;
		this.baseAssetsId = baseAssetsId;
	}

	public String getAosdId()
	{
		return this.aosdId;
	}

	public void setAosdId(String aosdId)
	{
		this.aosdId = aosdId;
	}

	public String getDocumentNo()
	{
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo)
	{
		this.documentNo = documentNo;
	}

	public String getAssetsNo()
	{
		return this.assetsNo;
	}

	public void setAssetsNo(String assetsNo)
	{
		this.assetsNo = assetsNo;
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

	public Double getPosQuantity()
	{
		return this.posQuantity;
	}

	public void setPosQuantity(Double posQuantity)
	{
		this.posQuantity = posQuantity;
	}

	public Double getPisQuantity()
	{
		return this.pisQuantity;
	}

	public void setPisQuantity(Double pisQuantity)
	{
		this.pisQuantity = pisQuantity;
	}

	public Double getLossQuantity()
	{
		return this.lossQuantity;
	}

	public void setLossQuantity(Double lossQuantity)
	{
		this.lossQuantity = lossQuantity;
	}

	public Double getHandleQuantity()
	{
		return this.handleQuantity;
	}

	public void setHandleQuantity(Double handleQuantity)
	{
		this.handleQuantity = handleQuantity;
	}

	public Double getPosPrice()
	{
		return this.posPrice;
	}

	public void setPosPrice(Double posPrice)
	{
		this.posPrice = posPrice;
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

	public String getUnit()
	{
		return this.unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	public String getDRemark()
	{
		return this.DRemark;
	}

	public void setDRemark(String DRemark)
	{
		this.DRemark = DRemark;
	}

	public String getRecNo()
	{
		return this.recNo;
	}

	public void setRecNo(String recNo)
	{
		this.recNo = recNo;
	}

	public Timestamp getBuyDate()
	{
		return this.buyDate;
	}

	public void setBuyDate(Timestamp buyDate)
	{
		this.buyDate = buyDate;
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

	public String getTel()
	{
		return this.tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getOriginId()
	{
		return this.originId;
	}

	public void setOriginId(String originId)
	{
		this.originId = originId;
	}

	public Double getAccDepr()
	{
		return this.accDepr;
	}

	public void setAccDepr(Double accDepr)
	{
		this.accDepr = accDepr;
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

	public Integer getErrorFlag()
	{
		return this.errorFlag;
	}

	public void setErrorFlag(Integer errorFlag)
	{
		this.errorFlag = errorFlag;
	}

	public String getDepositPosition()
	{
		return this.depositPosition;
	}

	public void setDepositPosition(String depositPosition)
	{
		this.depositPosition = depositPosition;
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

	public String getShareDepts()
	{
		return this.shareDepts;
	}

	public void setShareDepts(String shareDepts)
	{
		this.shareDepts = shareDepts;
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

	public Double getResidualValue()
	{
		return this.residualValue;
	}

	public void setResidualValue(Double residualValue)
	{
		this.residualValue = residualValue;
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

	public Integer getDepreciationPeriod()
	{
		return this.depreciationPeriod;
	}

	public void setDepreciationPeriod(Integer depreciationPeriod)
	{
		this.depreciationPeriod = depreciationPeriod;
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

	public Integer getHireOutType()
	{
		return this.hireOutType;
	}

	public void setHireOutType(Integer hireOutType)
	{
		this.hireOutType = hireOutType;
	}

	public String getHireOutTypeName()
	{
		return this.hireOutTypeName;
	}

	public void setHireOutTypeName(String hireOutTypeName)
	{
		this.hireOutTypeName = hireOutTypeName;
	}

	public String getDeptNoGetTemp()
	{
		return this.deptNoGetTemp;
	}

	public void setDeptNoGetTemp(String deptNoGetTemp)
	{
		this.deptNoGetTemp = deptNoGetTemp;
	}

	public String getBaseAssetsId()
	{
		return this.baseAssetsId;
	}

	public void setBaseAssetsId(String baseAssetsId)
	{
		this.baseAssetsId = baseAssetsId;
	}

}
