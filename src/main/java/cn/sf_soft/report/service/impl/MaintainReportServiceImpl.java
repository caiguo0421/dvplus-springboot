package cn.sf_soft.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.report.dao.MaintainReportDao;
import cn.sf_soft.report.model.MainTainDailyLineReport;
import cn.sf_soft.report.model.MaintainClaimDailyReport;
import cn.sf_soft.report.model.MaintainClaimsReport;
import cn.sf_soft.report.model.MaintainReport;
import cn.sf_soft.report.model.MaintainReportofTime;
import cn.sf_soft.report.service.MaintainReportService;
@Service("maintainReportService")
public class MaintainReportServiceImpl implements MaintainReportService {
	@Autowired
	@Qualifier("maintainReportDao")
	private MaintainReportDao dao;
	
	public void setDao(MaintainReportDao dao) {
		this.dao = dao;
	}
	/**
	 * 日维修报表
	 */
	public List<MaintainReport> getMaintainDayReport(String dateTime,
			String analyseType, List<String> stationIds){
		
		return dao.getMaintainDayReport(dateTime, analyseType, stationIds);
	}
/**
 * 时间段内维修报表
 */
	public List<MaintainReportofTime> getMaintainforTimeReport(String begintime,
			String endtime, String analyseType, List<String> stationIds)
			{
		
		return dao.getMaintainforTimeReport(begintime, endtime, analyseType, stationIds);
	}

	/**
	 * 获取日进场台次/产值曲线
	 * @author liujin
	 */
	public List<MainTainDailyLineReport> getMaintainDailyLineReport(
			List<String> stationIds, String beginTime, String endTime,
			int analyseType){
		List<MainTainDailyLineReport> list = dao.getMaintainDailyLineReportData(beginTime, endTime, stationIds, analyseType);
		return list;
		//得到两个时间段之间的完整的统计数据
//		Calendar startDate = Calendar.getInstance();
//		Calendar endDate = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
//		Date start = sdf.parse(beginTime.replaceAll("-", "."));
//		Date end = sdf.parse(endTime.replaceAll("-", "."));
//		startDate.setTime(start);
//		endDate.setTime(end);
//		endDate.add(Calendar.DATE, 1);
//		List<MainTainDailyLineReport> completeList = new ArrayList<MainTainDailyLineReport>();
//		while (startDate.before(endDate)) {
//			String dateStr = sdf.format(startDate.getTime());
//			MainTainDailyLineReport mr = new MainTainDailyLineReport(dateStr, (double) 0);
//			int i = list.indexOf(mr);
//			if( i != -1){
//				completeList.add(list.get(i));
//			}else{
//				completeList.add(mr);
//			}
//			startDate.add(Calendar.DATE, 1);
//		}
//		return completeList;
	}
	
	public List<MaintainClaimDailyReport> getMaintainClaimDailyReport(
			List<String> stationIds, String dateTime){
		return dao.getMaintainClaimDailyReportData(dateTime, stationIds);
	}
	
	
	/**
	 * 获取三包索赔类别 
	 * @author liujin
	 */
	public List<MaintainClaimsReport> getMaintainClaimsReportData(
			String beginTime, String endTime, List<String> stationIds){
		return dao.getMaintainClaimsReportData(beginTime, endTime, stationIds);
	}

}
