package cn.sf_soft.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.report.dao.CustomerConnectionDao;
import cn.sf_soft.report.model.SaleCallBackReport;
import cn.sf_soft.report.service.CustomerConnectionService;

/**
 * 
 * @Title: 客户关系报表
 * @date 2013-11-9 上午10:16:41 
 * @author cw
 */
@Service("customerReportService")
public class CustomerConnectionServiceImpl implements CustomerConnectionService {
	@Autowired
	@Qualifier("customerConnectionReportDao")
	private CustomerConnectionDao dao;
	
	public void setDao(CustomerConnectionDao dao) {
		this.dao = dao;
	}

	public List<SaleCallBackReport> getSaleCallBackData(
			String beginTime, String endTime, List<String> stationIds) {
		return dao.getSaleCallBackData(beginTime, endTime, stationIds);
	}

	public List<SaleCallBackReport> getServiceCallBackData(String beginTime,
			String endTime, List<String> stationIds) {
		return dao.getServiceCallBackData(beginTime, endTime, stationIds);
	}

}
