package cn.sf_soft.office.approval.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleInStocks;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleWriteOff;
import cn.sf_soft.office.approval.model.VehicleWriteOffDetails;
import cn.sf_soft.office.approval.model.VehicleInStockDetail;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;


public interface VehicleWriteOffDao {

	/**
	 * 根据documentNo查找车辆销账主表记录
	 * @param documentNo
	 * @return
	 */
	VehicleWriteOff getApplyByDocumentNo(String documentNo);
	
	
	/**
	 * 查询在其他销账单中的监控车
	 * @param documentNo
	 * @param vehicleId
	 * @return
	 */
	List<Map<String, Object>> getStocksInOtherApprove(String documentNo, String vehicleId);


	/**
	 * 根据documentNo分组查找SAP单号和记录数量
	 * @param documentNo
	 * @return
	 */
	List<Map<String, Object>> getDetailCountPerSapNo(String documentNo);


	/**
	 * 根据documentNo和SAP订单号查找明细
	 * @param documentNo 
	 * @param vehicleSaleDocuments SAP订单号
	 * @return
	 */
	List<Map<String, Object>> getDetailByVehicleSaleDocuments(String documentNo, String vehicleSaleDocuments);

	/**
	 * 审批同意前先同步入库单数据(金额不同步，以销账的为准)，因为可能存在入库单和销账单同时做单，然后先审入库单再审销账单的情况
	 * 根据销账单中未入库的车辆，获取已经入库的车辆
	 * 
	 * @param lstUnderpanAll 销账单中未入库的车辆底盘号
	 * @return
	 */
	List<Map<String, Object>> getVehicleInStock(String lstUnderpanAll);


	/**
	 * 根据底盘号查询销账单明细
	 * @param documentNo
	 * @param underPan
	 * @return
	 */
	List<VehicleWriteOffDetails> getVehicleWriteOffDetailsByUnderpan(String documentNo, String underPan);


	/**
	 * 更新 销账单明细
	 * @param detail
	 */
	void updateVehicleWriteOffDetails(VehicleWriteOffDetails detail);


	/**
	 * 查询在库存中的底盘号
	 * @param stockDetailIds 入库Id
	 * @return
	 */
	List<Map<String, Object>> getInStockUnderpans(Set<String> stockDetailIds);


	/**
	 * 查询在其他单据中的底盘号
	 * @param lstUnderpan
	 * @return
	 */
	List<Map<String, Object>> getUnderPansInOtherDocument(String documentNo, List<String> lstUnderpan);

	/**
	 * 查找采购入库单明细
	 * @param inStockDetailIdSet 
	 * @param sVehicleId
	 * @return
	 */
	List<VehicleInStockDetail> getStockDetailByVehicleId(Set<String> inStockDetailIdSet, String sVehicleId);
	
	/**
	 * 查找采购入库单
	 * @param inStockDetailIdSet
	 * @param sVehicleId
	 * @return
	 */
	List<VehicleInStocks> getStockByInStockNo(Set<String> inStockDetailIdSet,String sInStockNo);
	
	/**
	 * 查找合同明细
	 * @param lstVehicleId
	 * @param vehicleId
	 * @return
	 */
	List<VehicleSaleContractDetail> getContractDetailByVehicleId(List<String> lstVehicleId ,String  vehicleId);

	/**
	 * 查找库存车辆
	 * @param lstVehicleId
	 * @param sVehicleId
	 * @return
	 */
	List<VehicleStocks> getVehicleStocksByVehicleId(List<String> lstVehicleId, String sVehicleId);


	/**
	 * 查找对象
	 * @param m_sDFObjectNo
	 * @return
	 */
	List<BaseRelatedObjects> getRelatedObjectsByObjectNo(String m_sDFObjectNo);


	/**
	 * 查询vehicleSaleDocuments
	 * @param applyDetailId
	 * @return
	 */
	List<Map<String, Object>> getVehicleSaleDocumentsByDetailId(String applyDetailId);

	
	
}
