package cn.sf_soft.office.approval.dao.hbb;


import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.VehicleDfWriteOffApplyDao;
import cn.sf_soft.office.approval.model.VehicleDfWriteOffApply;
import cn.sf_soft.office.approval.model.VehicleDfWriteOffApplyDetail;

/**
 * 
 * @author caigx
 *
 */
@Repository("vehicleDfWriteOffApplyDao")
public class VehicleDfWriteOffApplyDaoHibernate extends BaseDaoHibernateImpl implements VehicleDfWriteOffApplyDao {

	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleDfWriteOffApplyDaoHibernate.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleDfWriteOffApplyDetail> getApplyDetail(VehicleDfWriteOffApply writeOffApply) {
		String hql = "from VehicleDfWriteOffApplyDetail where writeOffNo = ?";
		List<VehicleDfWriteOffApplyDetail> applyDetails = (List<VehicleDfWriteOffApplyDetail>) getHibernateTemplate()
				.find(hql, new Object[] { writeOffApply.getWriteOffNo() });
		return applyDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public VehicleDfWriteOffApply getApplyByDocumentNo(String documentNo) {
		String hql = "from VehicleDfWriteOffApply where documentNo = ?";
		List<VehicleDfWriteOffApply> applys = (List<VehicleDfWriteOffApply>) getHibernateTemplate().find(hql,
				new Object[] { documentNo });
		if (applys == null || applys.size() == 0) {
			return null;
		} else if (applys.size() == 1) {
			return applys.get(0);
		}
		logger.error(String.format("监控车销账 %s 对应了多个VehicleDfWriteOffApply记录", documentNo));
		throw new ServiceException("服务器内部异常");

	}

	@Override
	public List<Map<String, Object>> getStocksInOtherApprove(String documentNo, String vehicleId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.vehicle_vin,g.status,a.document_no FROM vehicle_DF_write_off_apply_detail a").append("\r\n")
				.append("LEFT JOIN vehicle_DF_write_off_apply g ON a.document_no=g.document_no").append("\r\n")
				.append("WHERE a.document_no <>:documentNo").append("\r\n")
				.append("AND g.status IN (20,30,50,60) AND a.vehicle_id = :vehicleId");
		Map<String, Object> params = new HashMap<>();
		params.put("documentNo", documentNo);
		params.put("vehicleId", vehicleId);
		List<Map<String, Object>> Stocks = getMapBySQL(sqlBuffer.toString(), params);
		return Stocks;
	}

}
