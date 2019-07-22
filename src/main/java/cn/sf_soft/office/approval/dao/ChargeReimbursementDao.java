package cn.sf_soft.office.approval.dao;

import cn.sf_soft.office.approval.model.ChargeReimbursements;
import cn.sf_soft.office.approval.model.ChargeReimbursementsDetails;
import cn.sf_soft.office.approval.model.VehicleSaleContractCharge;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;

/**
 * 车辆费用报销单据DAO
 * @author king
 * @create 2012-12-10下午05:22:59
 */
public interface ChargeReimbursementDao{

	/**
	 * 根据单据编号得到车辆费用报销单据的详细信息以及该单据的费用明细
	 * @param documentNo
	 * @return
	 */
	public ChargeReimbursements getDocumentDetail(String documentNo);
	
	/**
	 * 修改车辆费用报销单据的信息
	 * @param chargeReimbursements
	 * @return
	 */
	public ChargeReimbursements updateChargeReimbursements(ChargeReimbursements chargeReimbursements);
	
	/**
	 * 根据车辆报销单的费用明细中的合同单号得到车辆销售合同车辆表中对应的记录
	 * @param vscvId	
	 * @return	车辆销售合同车辆表中的记录
	 */
	public VehicleSaleContractDetail getVehicleSaleContractDetail(String vscvId);
	
	/**
	 * 修改车辆销售合同车辆表
	 * @param vehicleSaleContractDetail
	 * @return
	 */
	public boolean updateVehicleSaleContractDetail(VehicleSaleContractDetail vehicleSaleContractDetail);
	
	/**
	 * 根据车辆报销单的费用明细中的合同单号得到车辆销售合同费用表中对应的记录
	 * @param vsccId
	 * @return
	 */
	public VehicleSaleContractCharge getVehicleSaleContractCharge(String vsccId);
	
	/**
	 * 修改车辆销售合同费用表
	 * @param vehicleSaleContractCharge
	 * @return
	 */
	public void updateVehicleSaleContractCharge(VehicleSaleContractCharge vehicleSaleContractCharge);
	
	/**
	 * 根据车辆报销单的费用明细中的合同单号得到车辆销售合同表中对应的记录
	 * @param contractNo
	 * @return
	 */
	public VehicleSaleContracts getVehicleSaleContracts(String contractNo);

	/**
	 * 新增  车辆销售合同费用表
	 * @param vehicleSaleContractCharge
	 * @return
	 */
	VehicleSaleContractCharge saveVehicleSaleContractCharge(VehicleSaleContractCharge vehicleSaleContractCharge);

	/**
	 * 修改合同明细
	 * @param chargeReimburseDetail
	 */
	public ChargeReimbursementsDetails updateChargeReimbursementsDetails(ChargeReimbursementsDetails chargeReimburseDetail);
}
