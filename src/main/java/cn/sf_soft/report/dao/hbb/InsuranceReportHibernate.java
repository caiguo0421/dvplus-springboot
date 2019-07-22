package cn.sf_soft.report.dao.hbb;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.report.dao.InsuranceReportDao;
import cn.sf_soft.report.model.InsurancePurchaseReport;
/**
 * 保险统计
 * @author king
 * @create 2013-11-11上午10:08:08
 */
@Repository("insuranceReportDao")
public class InsuranceReportHibernate extends BaseDaoHibernateImpl implements
		InsuranceReportDao {

	@SuppressWarnings("unchecked")
	public List<InsurancePurchaseReport> statBySupplierAndInsuType(
			String beginTime, String endTime, List<String> stationIds) {
		return (List<InsurancePurchaseReport>) getHibernateTemplate().findByNamedQueryAndNamedParam("insuranceReportBySupplierAndInsuType", new String[]{"beginTime", "endTime", "stationIds"}, new Object[]{beginTime, endTime, stationIds});
	}
	@SuppressWarnings("unchecked")
	public List<InsurancePurchaseReport> statByInsuCategoryAndType(
			String beginTime, String endTime, List<String> stationIds) {
		return (List<InsurancePurchaseReport>) getHibernateTemplate().findByNamedQueryAndNamedParam("insuranceReportByCategoryTypeAndInsuType", new String[]{"beginTime", "endTime", "stationIds"}, new Object[]{beginTime, endTime, stationIds});
	}

}
