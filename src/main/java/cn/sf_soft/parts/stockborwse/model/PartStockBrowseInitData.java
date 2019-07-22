package cn.sf_soft.parts.stockborwse.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 配件库存浏览需要的初始化条件
 * @author king
 * @creation 2013-9-11
 */
public class PartStockBrowseInitData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 88314201897095544L;
	private List<PartWarehouse> partWarehouse;

	public List<PartWarehouse> getPartWarehouse() {
		return partWarehouse;
	}

	public void setPartWarehouse(List<PartWarehouse> partWarehouse) {
		this.partWarehouse = partWarehouse;
	}

	@Override
	public String toString() {
		return "PartStockBrowseInitData [partWarehouse=" + partWarehouse + "]";
	}

}
