package cn.sf_soft.office.approval.model;

// Generated 2015-4-28 15:14:05 by Hibernate Tools 3.4.0.CR1


/** 
* @ClassName: AssetsExpensesDetail 
* @Description: 资产分摊部门明细实体类
* @author xiongju
* @date 2015-4-29 上午11:01:45 
*  
*/
public class AssetsExpensesDetail implements java.io.Serializable
{

	private String aedId;
	private String documentId;
	private Short documentType;
	private Short objType;
	private String objNo;
	private String objId;
	private String objName;
	private Double amount;

	public AssetsExpensesDetail() {
	}

	public AssetsExpensesDetail(String aedId, String documentId,
			Short documentType, Short objType, String objNo, String objId,
			String objName, Double amount) {
		this.aedId = aedId;
		this.documentId = documentId;
		this.documentType = documentType;
		this.objType = objType;
		this.objNo = objNo;
		this.objId = objId;
		this.objName = objName;
		this.amount = amount;
	}

	public String getAedId()
	{
		return this.aedId;
	}

	public void setAedId(String aedId)
	{
		this.aedId = aedId;
	}

	public String getDocumentId()
	{
		return this.documentId;
	}

	public void setDocumentId(String documentId)
	{
		this.documentId = documentId;
	}

	public Short getDocumentType()
	{
		return this.documentType;
	}

	public void setDocumentType(Short documentType)
	{
		this.documentType = documentType;
	}

	public Short getObjType()
	{
		return this.objType;
	}

	public void setObjType(Short objType)
	{
		this.objType = objType;
	}

	public String getObjNo()
	{
		return this.objNo;
	}

	public void setObjNo(String objNo)
	{
		this.objNo = objNo;
	}

	public String getObjId()
	{
		return this.objId;
	}

	public void setObjId(String objId)
	{
		this.objId = objId;
	}

	public String getObjName()
	{
		return this.objName;
	}

	public void setObjName(String objName)
	{
		this.objName = objName;
	}

	public Double getAmount()
	{
		return this.amount;
	}

	public void setAmount(Double amount)
	{
		this.amount = amount;
	}
}
