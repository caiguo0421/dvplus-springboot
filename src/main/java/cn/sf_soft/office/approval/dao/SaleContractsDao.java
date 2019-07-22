package cn.sf_soft.office.approval.dao;

import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetCharge;

import java.util.List;
import java.util.Map;

public interface SaleContractsDao {

	/**
	 * 根据contractNo查询车辆合同
	 * @param contractNo
	 * @return
	 */
	public VehicleSaleContracts getContractByContractNo(String contractNo);

	/**
	 * 根据documentNo查询车辆合同明细
	 * @param documentNo
	 * @return
	 */
	VehicleSaleContractDetail getDetailByDocumentNo(String documentNo);

	/**
	 * 根据contractNo查询车辆合同明细
	 * @param contractNo
	 * @return
	 */
	VehicleSaleContractDetail getDetailByContractNo(String contractNo);


	/**
	 * 根据contractNo查询车辆合同明细
	 * @param contractNo
	 * @return
	 */
	List<VehicleSaleContractDetail> getDetailByContractsNo(String contractNo);

	/**
	 * 查询保险信息
	 * @param detailId
	 * @return
	 */
	List<VehicleSaleContractInsurance> getInsurancesByDetailId(String detailId);

	/**
	 * 查询 VehicleSaleContractInsurance
	 * @param saleContractInsuranceId
	 * @return
	 */
	List<VehicleSaleContractInsurance> getInsureanceBySaleContractInsuranceId(String saleContractInsuranceId);

	/**
	 * 查询精品信息
	 * @param detailId
	 * @return
	 */
	List<VehicleSaleContractPresent> getPresentsByDetailId(String detailId);

	/**
	 * get Present By SaleContractPresentId
	 * @param saleContractPresentId
	 * @return
	 */
	VehicleSaleContractPresent getPresentBySaleContractPresentId(String saleContractPresentId);

	/**
	 * 查询改装信息
	 * @param detailId
	 * @return
	 */
	List<VehicleSaleContractItem> getItemsByDetailId(String detailId);

	/**
	 * get Item By SaleContractItemId
	 * @param saleContractItemId
	 * @return
	 */
	VehicleSaleContractItem getItemBySaleContractItemId(String saleContractItemId);

	/**
	 * 查询其他费用
	 * @param detailId
	 * @return
	 */
	List<VehicleSaleContractCharge> getChargesByDetailId(String detailId);

	/**
	 * 查询赠品信息
	 * @param detailId
	 * @return
	 */
	List<VehicleSaleContractGifts> getGiftsByDetailId(String detailId);

	/**
	 * 查询发票信息
	 * @param detailId
	 * @return
	 */
	List<VehicleInvoices> getInvoicesByDetailId(String detailId);

	/**
	 * 查询车辆消贷信息
	 * @param detailId
	 * @return
	 */
	List<VwVehicleLoanBudgetCharge> getBudgetChargesByDetailId(String detailId);

	/**
	 * 查询合同明细视图
	 * @param detailId
	 * @return
	 */
	VwVehicleSaleContractDetail getVwContractDetailByDetailId(String detailId);

	/**
	 * 查询车辆设定的最低利润和车辆最低价格
	 * @param stationId
	 * @param selfId
	 * @return
	 */
	List<Map<String, Object>> getVehiclePriceCatlog(String stationId, String selfId);

	/**
	 * 获取 凭单支付的费用明细
	 * @param detailId
	 * @return
	 */
	List<VehicleSaleContractCharge> getPaidCharges(String detailId);

	/**
	 * 获得 VehicleSaleContractDetail
	 * @param contractDetailId
	 * @return
	 */
	VehicleSaleContractDetail getContractDetailByDetailId(String contractDetailId);
}
