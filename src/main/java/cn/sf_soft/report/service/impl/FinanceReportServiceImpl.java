package cn.sf_soft.report.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import cn.sf_soft.report.dao.FinanceReportDao;
import cn.sf_soft.report.model.FinanaceGatheringReport;
import cn.sf_soft.report.model.FinanceReportOfPayMode;
import cn.sf_soft.report.model.FinanceType;
@Service("financeReportService")
public class FinanceReportServiceImpl implements
		cn.sf_soft.report.service.FinanceReportService {
	@Autowired
	@Qualifier("financeReportDao")
	private FinanceReportDao dao;
	
	public void setDao(FinanceReportDao dao) {
		this.dao = dao;
	}

	/**
	 * 初始化收款类型数据
	 * @return
	 */
	public Map<String, String> getGatheringInitData() {
		Map<String, String> map=new LinkedHashMap<String, String>();
		List<FinanceType> list = dao.getGatheringInitData();
		for(FinanceType f:list){
			map.put(f.getMeaning(),f.getCode());
		}
		return map;
	}

	public List<FinanaceGatheringReport> getGatheringReportData(
			String beginTime, String endTime, List<String> stationIds,
			String financeType) {
		return dao.getGatheringReportData(beginTime, endTime, stationIds, financeType);
	}

	public Map<String, String> getPayMentInitData() {
		Map<String, String> map=new LinkedHashMap<String, String>();
		List<FinanceType> list = dao.getPayMentInitData();
		for(FinanceType f:list){
			map.put(f.getMeaning(),f.getCode());
		}
		return map;
	}

	public List<FinanaceGatheringReport> getPayMentReportData(String beginTime,
			String endTime, List<String> stationIds, String financeType) {
		return dao.getPayMentReportData(beginTime, endTime, stationIds, financeType);
	}

	public List<FinanceReportOfPayMode> getGatheringReportForPayModeData(
			String beginTime, String endTime, List<String> stationIds,
			String financeType) {
		return dao.getGatheringReportForPayModeData(beginTime, endTime, stationIds, financeType);
	}

	public List<FinanceReportOfPayMode> getPayMentReportForPayModeData(
			String beginTime, String endTime, List<String> stationIds,
			String financeType) {
		return dao.getPayMentReportForPayModeData(beginTime, endTime, stationIds, financeType);
	}

}
