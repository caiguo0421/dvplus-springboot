package cn.sf_soft.office.approval.dao;

import java.util.List;
import java.util.Map;

import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleInvoices;
import cn.sf_soft.office.approval.model.VehicleInvoicesVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractCharge;
import cn.sf_soft.office.approval.model.VehicleSaleContractChargeVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetailVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractGiftsVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractInsuranceVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractItemVary;
import cn.sf_soft.office.approval.model.VehicleSaleContractPresent;
import cn.sf_soft.office.approval.model.VehicleSaleContractPresentVary;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.office.approval.model.VehicleSaleContractsVary;

public interface SaleContractsVaryDao {

	/**
	 * 查找合同变更明细
	 * @param documentNo
	 * @return
	 */
	List<VehicleSaleContractDetailVary> getContractDetailVaryByDocumentNo(String documentNo);
	
	/**
	 * 查找在其他变更单中的合同号
	 * @param contractNo 合同号
	 * @param documentNo 审批单号
	 * @return
	 */
	List<VehicleSaleContractsVary> getContractVeryInOtherDocument(String contractNo,String documentNo);
	
	
	/**
	 * 查询原始单据信息（native sql）
	 * @param condition  where条件  and...
	 * @param params  参数 ，其中 合同号  contractNo
	 * @return
	 */
	List<Map<String, Object>> getOriChargeListByCondition(String condition, Map<String, Object> params);

	
	/**
	 * 变更明细查找保险明细
	 * @param detailVaryId
	 * @return
	 */
	List<VehicleSaleContractInsuranceVary> getInsuranceVaryByDetailVaryId(String detailVaryId);

	/**
	 * 查找已购保险的项目
	 * @param condition where条件  and...
	 * @param params 参数
	 * @return
	 */
	List<Map<String, Object>> getInsuranceListByCondition(String condition, Map<String, Object> params);

	/**
	 * 变更明细查找精品明细
	 * @param detailVaryId
	 * @return
	 */
	List<VehicleSaleContractPresentVary> getPresentVaryByDetailVaryId(String detailVaryId);

	/**
	 * 变更明细查找改装明细
	 * @param detailVaryId
	 * @return
	 */
	List<VehicleSaleContractItemVary> getItemVaryByDetailVaryId(String detailVaryId);

	/**
	 * 查找已改装的项目
	 * @param condition
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getConversionListByCondition(String condition, Map<String, Object> params);

	/**
	 * 变更明细查找赠品明细
	 * @param detailVaryId
	 * @return
	 */
	List<VehicleSaleContractGiftsVary> getGiftVaryByDetailVaryId(String detailVaryId);

	/**
	 * 变更明细查找费用明细
	 * @param detailVaryId
	 * @return
	 */
	List<VehicleSaleContractChargeVary> getChargeVaryByDetailVaryId(String detailVaryId);

	/**
	 * 查找符合要求的出库明细
	 * @param contractDetailId
	 * @return
	 */
	List<Map<String, Object>> getoutStocksInTime(String contractDetailId);

	/**
	 * 获取客户佣金分录
	 * @param contractDetailId
	 * @return
	 */
	List<FinanceDocumentEntries> getBrokerageEntriesByDocumentId(String contractDetailId);

	/**
	 * 查询已出库精品
	 * @param contractDetailId
	 * @return
	 */
	List<VehicleSaleContractPresent> getOutPresent(String contractDetailId);

	/**
	 * 查看已开发票
	 * @param condition2
	 * @param params2
	 * @return
	 */
	List<Map<String, Object>> getInvoiceListByCondition(String condition2, Map<String, Object> params2);

	/**
	 * 查找预算单
	 * @param condition3
	 * @param params3
	 * @return
	 */
	List<Map<String, Object>> getBudgetListByCondition(String condition3, Map<String, Object> params3);

	/**
	 * 合同明细查询变更发票
	 * @param detailVaryId
	 * @return
	 */
	List<VehicleInvoicesVary> getInvoiceVaryByContractDetailId(String contractDetailId,String detailVartId);

	/**
	 * 查找原合同明细
	 * @param contractDetailId
	 * @return
	 */
	List<VehicleInvoices> getOriInvoiceByContractDetailId(String contractDetailId);

	/**
	 * 查询购车发票
	 * @param contractDetailId
	 * @return
	 */
	List<VehicleInvoicesVary> getVehicleInvoiceVaryList(String contractDetailId);

	/**
	 * 不在变更中的发票
	 * @param contractDetailId
	 * @return
	 */
	List<VehicleInvoices> getOriInvoiceNotInVary(String contractDetailId);

	/**
	 * 查找在其他合同中的车辆
	 * @param vehicleVin
	 * @param contractNo
	 * @return
	 */
	List<Map<String, Object>> getVehicleInOtherContract(String vehicleVin, String contractNo);

	/**
	 * 根据合同号查询合同
	 * @param contractNo
	 * @return
	 */
	VehicleSaleContracts getSaleContractsByContractNo(String contractNo);

	/**
	 * 根据VIN查找原合同明细
	 * @param vehicleVin
	 * @return
	 */
	List<VehicleSaleContractDetail> getVehicleOriContractDetailList(String vehicleVin);

	/**
	 * 查找关联的合同明细
	 * @param contractDetailId
	 * @return
	 */
	List<VehicleSaleContractDetail> getOriDetailListByRelation(String contractDetailId);

	/**
	 * 查询购车发票
	 * @param contractDetailId
	 * @return
	 */
	List<VehicleInvoices> getVehicleInvoiceList(String contractDetailId);

	/**
	 * 按名称查找费用明细
	 * @param contractDetailId
	 * @param sPurchaseTaxName
	 * @return
	 */
	List<VehicleSaleContractCharge> getOriChargeListByChargeName(String contractDetailId, String sPurchaseTaxName);

	/**
	 * 按名称查找费用变更明细
	 * @param contractDetailId
	 * @param sPurchaseTaxName
	 * @return
	 */
	List<VehicleSaleContractChargeVary> getChargeVaryListByChargeName(String contractDetailId, String sPurchaseTaxName);

	/**
	 * 计算贷款总额
	 * @param contractDetailId
	 * @return
	 */
	List<Map<String, Object>> getSumLoanAmount(String contractDetailId);

	/**
	 * 获取合同已有保险金额(不含保费的保险)
	 * @param contractNo
	 * @return
	 */
	List<Map<String, Object>> getInsuranceAmountTotal(String contractNo);

	/**
	 * 找出已变更含保费的项目
	 * @param documentNo
	 * @return
	 */
	List<Map<String, Object>> getInstDocumentList(String documentNo);

	/**
	 * 获取销售合同相关单据分录
	 * @param contractNo
	 * @return
	 */
	List<Map<String, Object>> getSaleBillDocument(String contractNo);

	/**
	 * 获取销售合同变更相关单据分录
	 * @param contractNo
	 * @return
	 */
	List<Map<String, Object>> getSaleBillDocumentVary(String contractNo);

	/**
	 * 获取预算单明细
	 * @param contractDetailId
	 * @return
	 */
	List<Map<String, Object>> getBudgetDetailList(String contractDetailId);

	/**
	 * 获取预算单费用
	 * @param contractDetailId
	 * @return
	 */
	List<Map<String, Object>> getBudgetChargeList(String contractDetailId);

	/**
	 * 获取预算单的分录
	 * @param contractDetailId
	 * @return
	 */
	List<Map<String, Object>> getDocumentEntriesByBudgetCharge(String contractDetailId);

	/**
	 * 获取预算单费用 (按SelfId)
	 * @param contractDetailId
	 * @param string
	 * @return
	 */
	List<Map<String, Object>> getBudgetChargeListBySelfId(String contractDetailId, String selfId);


	/**
	 * 获取SettlementsDetail
	 * @param contractDetailId
	 * @param string
	 * @return
	 */
	List<Map<String, Object>> getSettlementsDetailList(String string);

	List<Map<String, Object>> getUserByCreator(String creator);

	/**
	 * 以开票金额
	 * @param invoicesDetailId
	 * @return
	 */
	List<Map<String, Object>> getInvoicePaid(String invoicesDetailId);
	

	/**
	 * 获得车型列表（合同 和 合同变更）
	 * @param contractNo
	 * @return
	 */
	List<Map<String, Object>> findVehicleVnoList(String contractNo);

	List<Map<String, Object>> getChargeVaryMerge(String contractDetailId, String detailVaryId);

	List<Map<String, Object>> getInsuranceVaryMerge(String contractDetailId, String detailVaryId);

	List<Map<String, Object>> getPresentVaryMerge(String contractDetailId, String detailVaryId);

	List<Map<String, Object>> getItemVaryMerge(String contractDetailId, String detailVaryId);

	/**
	 * 查询 已做了保险的明细
	 * @param contractDetailId
	 * @return
	 */
	List<Map<String, Object>>  getInsuranceByContractDetailId(String contractDetailId);

}
