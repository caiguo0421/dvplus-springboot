package cn.sf_soft.basedata.service;

import cn.sf_soft.basedata.model.BasePartType;
import cn.sf_soft.basedata.model.SysStations;

import java.util.List;
import java.util.Map;

/**
 * 系统基础数据
 * @创建人 LiuJin
 * @创建时间 2014-8-26 下午4:12:12
 * @修改人 
 * @修改时间
 */
public interface SysBaseDataService {

	/**
	 * 获取系统站点信息
	 * @return
	 */
	public List<SysStations> getSysStations();
	
	/**
	 * 获取系统配件类型基础资料
	 * @return
	 */
	public List<BasePartType> getBasePartsType();

	public List<Map<String, Object>> getDepartments();

	public List<Map<String, Object>> getBaseOthers(String typeCode, String stationId);
}
