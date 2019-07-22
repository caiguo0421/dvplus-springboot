package cn.sf_soft.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.report.dao.InsuranceReportDao;
import cn.sf_soft.report.model.InsurancePurchaseReport;
import cn.sf_soft.report.service.InsuranceReportService;
/**
 * 保险统计
 * @author king
 * @create 2013-11-8下午3:12:37
 */
@Service("insuranceReportService")
public class InsuranceReportServiceImpl implements InsuranceReportService {
	@Autowired
	@Qualifier("insuranceReportDao")
	private InsuranceReportDao reportDao;
	public void setReportDao(InsuranceReportDao reportDao) {
		this.reportDao = reportDao;
	}
	
	public List<InsurancePurchaseReport> getReportBySupplierAndInsuType(
			String beginTime, String endTime, List<String> stationIds) {
		return reportDao.statBySupplierAndInsuType(beginTime, endTime, stationIds);
	}

	public List<InsurancePurchaseReport> getReportByInsuCategoryAndType(
			String beginTime, String endTime, List<String> stationIds) {
		return reportDao.statByInsuCategoryAndType(beginTime, endTime, stationIds);
	}

}
