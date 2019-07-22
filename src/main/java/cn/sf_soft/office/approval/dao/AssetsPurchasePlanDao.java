package cn.sf_soft.office.approval.dao;

import cn.sf_soft.office.approval.model.AssetsPurchasePlan;

/**
 * 资产采购计划hibernate接口
 * @author minggo
 * @created 2013-01-15
 */
public interface AssetsPurchasePlanDao {
	/**
	 * According to the documents documentNo inquires the check asset purchase plan details
	 * @param documentNo
	 * @return
	 */
	public AssetsPurchasePlan getDocumentDetail(String documentNo);
	/**
	 * Update asset purchase plan documents
	 * @param assetsPurchasePlan
	 * @return
	 */
	public boolean updateAssetsPurchasePlan(AssetsPurchasePlan assetsPurchasePlan);
}
