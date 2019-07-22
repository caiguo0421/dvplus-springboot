package cn.sf_soft.office.approval.dao.hbb;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.FinancePaymentRequestsDao;
import cn.sf_soft.office.approval.model.FinancePaymentRequests;

/**
 * 业务请款单的hibernate操作
 * 
 * @author king
 * @created 2013-01-07
 */
@Repository("financePaymentRequestsDao")
public class FinancePaymentRequestsDaoHibernate extends BaseDaoHibernateImpl
		implements FinancePaymentRequestsDao {
	/**
	 * Inquires the business documents detailed information
	 */
	@Override
	@SuppressWarnings("unchecked")
	public FinancePaymentRequests getDocumentDetail(String documentNo) {
		String hql = "from FinancePaymentRequests f left join fetch f.chargeDetail where f.documentNo=?";
		List<FinancePaymentRequests> list = (List<FinancePaymentRequests>) getHibernateTemplate()
				.find(hql, documentNo);
		if (!list.isEmpty())
			return list.get(0);
		return null;
	}

	/**
	 * Update business documents detailed information
	 */
	@Override
	public boolean updateFinancePaymentRequests(
			final FinancePaymentRequests financePaymentRequests) {
		getHibernateTemplate().update(financePaymentRequests);
		return true;
	}

	/**
	 * add by shichunshan 2015/11/27
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> findFinancePaymentRequestsDetailsByEnterId(
			String enterId) {
		String sqlString = " SELECT  m.entry_id ,"
				+ " SUM(m.request_amount) AS request_amount ,"
				+ " SUM(m.request_amount - m.paid_amount) AS amount"
				+ " FROM    finance_payment_requests_details m"
				+ "   LEFT JOIN finance_payment_requests n ON m.document_no = n.document_no"
				+ " WHERE   n.status = 50 and m.entry_id='" + enterId
				+ "' GROUP BY m.entry_id";
		return this.getMapBySQL(sqlString, null);
	}

}
