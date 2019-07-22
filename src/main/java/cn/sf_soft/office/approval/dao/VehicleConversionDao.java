package cn.sf_soft.office.approval.dao;

import java.util.List;
import java.util.Map;

import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;

public interface VehicleConversionDao {

	/**
	 * 根据单号查找记录
	 * 
	 * @param documentNo
	 * @return
	 */
	List<VehicleConversion> getConversionsByDocumentNo(String documentNo);


	/**
	 * 根据单号查找 ConversionMaster
	 *
	 * @param documentNo
	 * @return
	 */
	VehicleConversionMaster getConversionMasterByDocumentNo(String documentNo);

	/**
	 * 根据拆装类型 找到拆装明细
	 * 
	 * @param conversion
	 * @param installType
	 * @return
	 */
	List<VehicleSuperstructureRemoveAndInstalls> getSuperstructureByInstallType(VehicleConversion conversion,
			String installType);

	/**
	 * 查找有相同底盘号的改装单
	 * 
	 * @param conversionNo
	 * @param vehicleVin
	 * @return
	 */
	List<Map<String, Object>> getOtherConversionsByVin(String conversionNo, String vehicleVin);

	/**
	 * 查找改装明细对应的合同改装明细
	 * 
	 * @param detail
	 * @return
	 */
	List<Map<String, Object>> getSaleContractItems(VehicleConversionDetail detail);

	/**
	 * 查找上装的加装项目
	 * 
	 * @param conversion
	 * @return
	 */
	List<VehicleSuperstructureRemoveAndInstalls> getVehicleInstallItems(VehicleConversion conversion);

	/**
	 * 查找对应的车辆销售合同明细
	 * 
	 * @param vehicleId
	 * @param isSecondHand
	 * @return
	 */
//	List<VehicleSaleContractDetail> getContractDetailByVehicleId(String vehicleId, boolean isSecondHand);

	/**
	 * 查找对应合同的车辆改装明细
	 * 
	 * @param vehicleId
	 * @param isSecondHand
	 * @return
	 */
	//List<VehicleSaleContractItem> getContractItemsByVehicleId(String vehicleId, boolean isSecondHand);

	/**
	 * 查找对应改装的改装明细
	 * 
	 * @param vehicleId
	 * @param isSecondHand
	 * @return
	 */
	List<VehicleConversionDetail> getConversionDetailsByVehicleId(String vehicleId, boolean isSecondHand,
			List<String> saleContractItemIds);

	/**
	 * 该车已经加装或拆装的成本之和
	 * 
	 * @param vehicleId
	 * @param isSecondHand
	 * @return
	 */
	List<Map<String, Object>> getVsriAmountByVehicleId(String vehicleId, boolean isSecondHand);

	/**
	 * 计算实际改装金额
	 * @param vehicleId
	 * @param isSecondHand
	 * @return
	 */
	List<Map<String, Object>> getRealCost(String vehicleId, boolean isSecondHand);

	/**
	 * 查找对应的库存车
	 * @param vehicleVin
	 * @return
	 */
	List<VehicleStocks> getVehicleStocksByVin(String vehicleVin);

	/**
	 * 查找对应的二手车库存
	 * @param vehicleVin
	 * @return
	 */
	List<SecondhandVehicleStocks> getSecondhandVehicleStocksByVin(String vehicleVin);

	
	
	
	/**
	 * 根据合同明细Id和 类型查找销售合同对应的单据分录
	 * @param vscdId 销售合同明细Id
	 * @param saleDocumentType 单据类型
	 * @return
	 */
	List<FinanceDocumentEntries> getDocumentEntriesByType(String vscdId, String saleDocumentType);
	
	/**
	 * 根据合同明细Id查找销售合同对应的单据分录
	 * @param vscdId 销售合同明细Id
	 * @return
	 */
	List<FinanceDocumentEntries> getDocumentEntries(String vscdId);

	/**
	 * 查找车辆销售合同的内容
	 * @param vscdId 销售合同明细Id
	 * @return
	 */
	List<Map<String, Object>> getContractInfo(String vscdId);

	
	/**
	 * 根据entryId 查找请款金额合计
	 * @param entryId
	 */
	List<Map<String, Object>>  getRequestAmountByEntryId(String entryId);

	/**
	 * 根据车辆Id查找合同
	 * @param vehicleId
	 * @return
	 */
	List<VehicleSaleContracts> getVscList(String vehicleId);

	/**
	 * 根据车辆Id查找合同明细
	 * @param vehicleId
	 * @return
	 */
	List<VehicleSaleContractDetail> getVscdList(String vehicleId);

	/**
	 * 根据车辆Id查找合同改装项目
	 * @param vehicleId
	 * @return
	 */
	List<VehicleSaleContractItem> getContractItemsByVehicleId(String vehicleId);

	
}
