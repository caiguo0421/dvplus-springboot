package cn.sf_soft.vehicle.demand.dao.hbb;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.vehicle.demand.dao.IVehicleDemandApplyDao;
import cn.sf_soft.vehicle.demand.model.VehicleDemandApply;
import cn.sf_soft.vehicle.demand.model.VehicleDemandApplyDetail;

@Repository("vehicleDemandApplyDao")
public class VehicleDemandApplyDao extends BaseDaoHibernateImpl implements IVehicleDemandApplyDao{

	@SuppressWarnings("unchecked")
	@Override
	public VehicleDemandApply getDemandApplayByDocumentNo(String documentNo){
		String hql = "from  VehicleDemandApply  where documentNo = ?";
		List<VehicleDemandApply> applyList = (List<VehicleDemandApply>) getHibernateTemplate().find(hql, new Object[] { documentNo });
		if(applyList==null || applyList.size()==0){
			throw new ServiceException("根据审批单号："+documentNo+"未找到资源需求申报的记录");
		}else if (applyList.size()>1){
			throw new ServiceException("审批单号："+documentNo+"对应了多条资源需求申报的记录");
		}
		return applyList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleDemandApplyDetail> getDetailByDemandId(String demandId) {
		String hql = "from VehicleDemandApplyDetail  where demandId = ?";
		List<VehicleDemandApplyDetail> list = (List<VehicleDemandApplyDetail>) getHibernateTemplate().find(hql, new Object[] { demandId });
		return list;
	}
}
