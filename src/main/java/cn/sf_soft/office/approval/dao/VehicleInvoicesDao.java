package cn.sf_soft.office.approval.dao;

import java.util.List;

import cn.sf_soft.office.approval.model.VehicleInvoices;

public interface VehicleInvoicesDao {

	/**
	 * 根据合同明细查找购车发票
	 * @param contractDetailId
	 * @return
	 */
	List<VehicleInvoices> getVehicleInvoicesByContractId(String contractDetailId);

}
