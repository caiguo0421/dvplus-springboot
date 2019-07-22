package cn.sf_soft.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.report.dao.VehicleReportDao;
import cn.sf_soft.report.model.VehicleMonthlyReport;
import cn.sf_soft.report.model.VehiclePeriodOfTimeReport;
import cn.sf_soft.report.service.VehicleReportService;
@Service("vehicleReportService")
public class VehicleReportServiceImpl implements VehicleReportService {
	@Autowired
	@Qualifier("vehicleReportDao")
	private VehicleReportDao dao;
	public void setDao(VehicleReportDao dao) {
		this.dao = dao;
	}
	

	public List<VehicleMonthlyReport>  getMonthlySaleReportData(String dateTime, String analyseType, List<String> stationIds,String datetype, String statContent){
		
		return dao.getMonthlySaleReportData(dateTime, analyseType, stationIds,datetype, statContent);
	}
	public List<VehiclePeriodOfTimeReport> getPeriodOfTimeSaleReportData(
			 String beginTime, String endTime, String analyseType,
			 List<String> stationIds,String datetype){
		return dao.getPeriodOfTimeSaleReportData(beginTime, endTime, analyseType, stationIds,datetype);
	}

	
	

}
