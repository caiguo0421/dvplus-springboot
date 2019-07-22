package cn.sf_soft.report.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.basedata.model.BaseOthers;
import cn.sf_soft.basedata.model.SysFlags;
import cn.sf_soft.report.dao.OfficeReportDao;
import cn.sf_soft.report.model.OfficeAssetReport;
import cn.sf_soft.report.model.SuppliesPosition;
import cn.sf_soft.report.service.OfficeReportService;
@Service("officeReportService")
public class OfficeReportServiceImpl implements OfficeReportService {
	@Autowired
	@Qualifier("officeReportDao")
	private OfficeReportDao dao;
	public void setDao(OfficeReportDao dao) {
		this.dao = dao;
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> getTypeInitData(String Type) {
		Map<String, String> map = new LinkedMap();
		List<SysFlags>	list = dao.getTypeInitData(Type);
		for(SysFlags f:list){
			map.put(f.getMeaning(),f.getCode().toString());
		}
		return map;
	}

	public String[] getPositionInitData() {
		List<BaseOthers> list = dao.getPositionInitData();
		String[] posstionList = new String[list.size()];
		for(int i=0;i<list.size();i++){
			posstionList[i] = list.get(i).getData();
		}
		return posstionList;
	}

	public List<OfficeAssetReport> getAssetOutReoprtData(String beginTime,
			String endTime, List<String> stationIds, String posType,
			String depositPosition) {
		return dao.getAssetOutReoprtData(beginTime, endTime, stationIds, posType, depositPosition);
	}

	public List<OfficeAssetReport> getAssetInReoprtData(String beginTime,
			String endTime, List<String> stationIds, String pis_type,
			String depositPosition) {
		return dao.getAssetInReoprtData(beginTime, endTime, stationIds, pis_type, depositPosition);
	}
	@SuppressWarnings("unchecked")
	public Map<String, String> getSuppliesPositionInitData(List<String> stationIds){
		 List<SuppliesPosition>  list = dao.getSuppliesPositionInitData(stationIds);
		Map<String, String> map = new LinkedMap();
		for(SuppliesPosition f:list){
			map.put(f.getPositionName(),f.getPositionCode().toString());
		}
		return map;
	}
	public List<OfficeAssetReport> getThingsInReoprtData(String beginTime,
			String endTime, List<String> stationIds, String pis_type,
			String positionCode) {
		return dao.getThingsInReoprtData(beginTime, endTime, stationIds, pis_type, positionCode);
	}
	public List<OfficeAssetReport> getThingsOutReoprtData(final String beginTime,final String endTime,
			final List<String> stationIds,final String pos_type,final String positionCode){
		return dao.getThingsOutReoprtData(beginTime, endTime, stationIds, pos_type, positionCode);
	}
}
