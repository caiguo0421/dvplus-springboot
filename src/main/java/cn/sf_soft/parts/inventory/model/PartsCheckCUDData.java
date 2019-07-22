package cn.sf_soft.parts.inventory.model;

import java.util.List;

/**
 * 配件库存盘存增删改数据
 * @创建人 LiuJin
 * @创建时间 2014-12-16 下午4:31:55
 * @修改人 
 * @修改时间
 */
public class PartsCheckCUDData {

	private PartCheckStocks partsCheckDoc;//盘存主单
	private List<PartCheckStocksDetail> createData;//新增的盘存信息
	private List<PartCheckStocksDetail> updateData;//修改的盘存信息
	private List<String> deleteData;//删除的盘存ID
	
	public PartCheckStocks getPartsCheckDoc() {
		return partsCheckDoc;
	}

	public void setPartsCheckDoc(PartCheckStocks partsCheckDoc) {
		this.partsCheckDoc = partsCheckDoc;
	}

	public List<PartCheckStocksDetail> getCreateData() {
		return createData;
	}

	public void setCreateData(List<PartCheckStocksDetail> createData) {
		this.createData = createData;
	}

	public List<PartCheckStocksDetail> getUpdateData() {
		return updateData;
	}

	public void setUpdateData(List<PartCheckStocksDetail> updateData) {
		this.updateData = updateData;
	}

	public List<String> getDeleteData() {
		return deleteData;
	}

	public void setDeleteData(List<String> deleteData) {
		this.deleteData = deleteData;
	}

	@Override
	public String toString() {
		return "PartsCheckCUDData [partsCheckDoc=" + partsCheckDoc
				+ ", createData=" + createData + ", updateData=" + updateData
				+ ", deleteData=" + deleteData + "]";
	}
	
	
}
