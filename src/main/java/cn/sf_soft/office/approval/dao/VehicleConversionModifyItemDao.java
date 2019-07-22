package cn.sf_soft.office.approval.dao;

import java.util.List;

import cn.sf_soft.office.approval.model.VehicleConversionModifyItem;

public interface VehicleConversionModifyItemDao {

	

	/**
	 * 根据销售合同改装明细Id查找修改项
	 * @param conversionNo 改装单号
	 * @param vsccId 销售合同改装明细Id
	 * @param varyType 修改状态 10终止、20新增、30修改
	 * @return
	 */
	List<VehicleConversionModifyItem> getModifyItemsByVsccId(String conversionNo, String vsccId, short varyType);

	/**
	 * 根据改装单号查找 查找修改项
	 * @param conversionNo
	 * @return
	 */
	List<VehicleConversionModifyItem> getModifyItemsByConversionNo(String conversionNo);

	List<VehicleConversionModifyItem> getModifyItemsByVsccId(String conversionNo, String vsccId);

}
