package cn.sf_soft.basedata.dao;

import java.util.List;

import cn.sf_soft.basedata.model.BaseOthers;
import cn.sf_soft.common.dao.BaseDao;

/**
 * 基础资料
 * @author king
 * @create 2013-9-25上午10:38:49
 */
public interface BaseOthersDao extends BaseDao{
	
	public List<String> getDataByTypeNo(String typeNo);
	
	public List<String> getDataByTypeNo(String typeNo, String stationId);
	
	public List<BaseOthers> getBaseOtherByTypeNo(String typeNo);
	
	public List<BaseOthers> getBaseOtherByTypeNoAndStationId(String typeNo,String stationId);
}
