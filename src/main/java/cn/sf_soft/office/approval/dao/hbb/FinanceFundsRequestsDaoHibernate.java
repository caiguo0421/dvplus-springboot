package cn.sf_soft.office.approval.dao.hbb;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.FinanceFundsRequestsDao;
import cn.sf_soft.office.approval.model.FinanceFundsRequests;
/**
 * 资金调拨申请DAO Hibernate实现
 */
@Repository("financeFundsRequestsDao")
public class FinanceFundsRequestsDaoHibernate extends BaseDaoHibernateImpl
		implements FinanceFundsRequestsDao {

	/**
	 * 根据单据编号得到资金调拨申请的详细信息
	 */
	@SuppressWarnings("unchecked")
	public FinanceFundsRequests getDocumentDetail(String documentNo) {
		List<FinanceFundsRequests> result = (List<FinanceFundsRequests>) getHibernateTemplate().find("from FinanceFundsRequests f left join fetch f.chargeDetail where f.documentNo=?",documentNo);
		if(result != null && result.size() > 0)
			return result.get(0);
		return null;
	}

	/**
	 * 修改资金调拨申请单据
	 */
	public boolean updateFinanceFundsRequests(
			FinanceFundsRequests financeFundsRequests) {
		try {
			getHibernateTemplate().update(financeFundsRequests);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 根据单据编号得到已经审批同意的资金调入申请单
	 */
	@SuppressWarnings("unchecked")
	public FinanceFundsRequests getAgreedRequestIn(String documentNo) {
		List<FinanceFundsRequests> result = (List<FinanceFundsRequests>) getHibernateTemplate().find("from FinanceFundsRequests f where f.status=50 and f.requestNo is NULL and f.documentNo=?", documentNo);
		if(result != null && result.size() > 0)
			return result.get(0);
		return null;
	}

}
