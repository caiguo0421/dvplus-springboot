package cn.sf_soft.office.approval.dao;

import cn.sf_soft.office.approval.model.VehicleSaleMarketingActivity;

/**
 * 营销活动管理 审批
 * @author minggo
 * @created 2013-01-15
 */
public interface VehicleSaleMarketingActivityDao {
	/**
	 * According to documents, inquires the marketing activities and detail
	 * @param documentNo
	 * @return
	 */
	public VehicleSaleMarketingActivity getDocumentDetail(String documentNo);
	/**
	 * Update marketing activities documents information
	 * @param vehicleSaleMarketingActivity
	 * @return
	 */
	public boolean updateVehicleSaleMarketingActivity(VehicleSaleMarketingActivity vehicleSaleMarketingActivity);
	
	//可能有加入赞助商的操作
}
