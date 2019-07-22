package cn.sf_soft.basedata.model;

/**
 * BaseOthers entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class BaseOthers implements java.io.Serializable {

	// Fields

	private String dataId;
	private String stationId;
	private String parentId;
	private String typeNo;
	private String data;
	private String popedoms;
	private String dataType;

	// Constructors

	/** default constructor */
	public BaseOthers() {
	}

	/** minimal constructor */
	public BaseOthers(String dataId, String data) {
		this.dataId = dataId;
		this.data = data;
	}

	public BaseOthers(String dataId, String data, String parentId) {
		this.dataId = dataId;
		this.parentId = parentId;
		this.data = data;
	}
	/** full constructor */
	public BaseOthers(String dataId, String stationId, String parentId,
			String typeNo, String data, String popedoms, String dataType) {
		this.dataId = dataId;
		this.stationId = stationId;
		this.parentId = parentId;
		this.typeNo = typeNo;
		this.data = data;
		this.popedoms = popedoms;
		this.dataType = dataType;
	}

	// Property accessors

	public String getDataId() {
		return this.dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getStationId() {
		return this.stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTypeNo() {
		return this.typeNo;
	}

	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getPopedoms() {
		return this.popedoms;
	}

	public void setPopedoms(String popedoms) {
		this.popedoms = popedoms;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return "BaseOthers [dataId=" + dataId + ", parentId=" + parentId
				+ ", data=" + data + "]";
	}

}
