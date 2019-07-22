package cn.sf_soft.office.approval.dao;

import cn.sf_soft.basedata.model.BaseSysAgency;
import cn.sf_soft.parts.inventory.model.PartPurchasePlanDetail;

import java.util.List;
import java.util.Map;

public interface GeneralApproveDao {
	
	/**
	 * 鏌ヨ閰嶄欢閲囪喘璁″垝鏄庣粏
	 * @param documentNo
	 * @return
	 */
	List<PartPurchasePlanDetail> getPartPurchasePlanDetailByDocumentNo(String documentNo);

	/**
	 * 鏌ヨ閲囪喘渚涘簲鍟�
	 * @param documentNo
	 * @return
	 */
	List<Map<String,Object>> getSupplierListByDocumentNo(String documentNo);
	
	/**
	 * 鏌ヨ涓滈渚涘簲鍟嗙紪鐮�
	 * @param objectNo
	 * @return
	 */
	List<Map<String,Object>> getDfSupplierNoByObjectNo(String objectNo);
	
	/**
	 * 鑾峰彇閰嶄欢閲囪喘榛樿绔欑偣
	 * @param stationId
	 * @return
	 */
	String getPartPurchaseDefault(String stationId);
	
	/**
	 * 鑾峰彇璁㈠崟绫诲瀷
	 * @param fieldNo
	 * @return
	 */
	Map<Short,Object> getOrderType(String fieldNo);
	/**
	 * 鑾峰彇榛樿绔欑偣
	 * @param stationId
	 * @return
	 */
	BaseSysAgency getBaseSysAgencyByStationId(String stationId);
}
