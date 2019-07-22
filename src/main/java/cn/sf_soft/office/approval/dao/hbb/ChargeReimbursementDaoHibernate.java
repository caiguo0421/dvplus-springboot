package cn.sf_soft.office.approval.dao.hbb;

import java.util.List;

import org.hibernate.LockMode;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.ChargeReimbursementDao;
import cn.sf_soft.office.approval.model.ChargeReimbursements;
import cn.sf_soft.office.approval.model.ChargeReimbursementsDetails;
import cn.sf_soft.office.approval.model.VehicleSaleContractCharge;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;

/**
 * 车辆费用报销
 * 
 * @author liujin
 *
 */
@Repository("chargeReimburseDaoHibernate")
public class ChargeReimbursementDaoHibernate extends BaseDaoHibernateImpl implements ChargeReimbursementDao {

	/**
	 * 根据单据编号得到车辆费用报销单据的详细信息以及该单据的费用明细
	 */
	@SuppressWarnings("unchecked")
	public ChargeReimbursements getDocumentDetail(String documentNo) {
		List<ChargeReimbursements> result = (List<ChargeReimbursements>) getHibernateTemplate().find(
				"from ChargeReimbursements c left join fetch c.chargeDetail where c.documentNo=?", documentNo);
		if (result != null && result.size() > 0)
			return result.get(0);
		return null;
	}

	/**
	 * 修改车辆费用报销单据的信息
	 */
	public ChargeReimbursements updateChargeReimbursements(ChargeReimbursements chargeReimbursements) {
		
		getHibernateTemplate().update(chargeReimbursements);
		
		return chargeReimbursements;
	}

	/**
	 * 根据车辆报销单的费用明细中的费用ID得到车辆销售合同车辆表中对应的记录
	 */
	public VehicleSaleContractDetail getVehicleSaleContractDetail(String vscvId) {
		return (VehicleSaleContractDetail) getHibernateTemplate().get(VehicleSaleContractDetail.class, vscvId);
	}

	/**
	 * 修改车辆销售合同车辆表
	 */
	public boolean updateVehicleSaleContractDetail(VehicleSaleContractDetail vehicleSaleContractDetail) {
		try {
			getHibernateTemplate().update(vehicleSaleContractDetail);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 根据车辆报销单的费用明细中的费用ID得到车辆销售合同费用表中对应的记录
	 */
	public VehicleSaleContractCharge getVehicleSaleContractCharge(String vsccId) {
		VehicleSaleContractCharge v = (VehicleSaleContractCharge) getHibernateTemplate().get(
				VehicleSaleContractCharge.class, vsccId, LockMode.UPGRADE);
		return v;
	}

	/**
	 * 根据车辆报销单的费用明细中的费用ID得到车辆销售合同表中对应的记录
	 */
	public VehicleSaleContracts getVehicleSaleContracts(String contractNo) {
		return (VehicleSaleContracts) getHibernateTemplate().get(VehicleSaleContracts.class, contractNo);
	}

	/**
	 * 修改车辆销售合同费用表
	 */
	public void updateVehicleSaleContractCharge(VehicleSaleContractCharge vehicleSaleContractCharge) {
		getHibernateTemplate().update(vehicleSaleContractCharge);
	}
	
	/**
	 * 新增车辆销售合同费用表
	 * @param vehicleSaleContractCharge
	 * @return
	 */
	@Override
	public VehicleSaleContractCharge saveVehicleSaleContractCharge(VehicleSaleContractCharge vehicleSaleContractCharge){
		getHibernateTemplate().save(vehicleSaleContractCharge);
		return vehicleSaleContractCharge;
	}

	/**
	 * 修改车辆报销单明细
	 */
	@Override
	public ChargeReimbursementsDetails updateChargeReimbursementsDetails(ChargeReimbursementsDetails chargeReimburseDetail) {
		getHibernateTemplate().update(chargeReimburseDetail);
		return  chargeReimburseDetail;
	}

}
