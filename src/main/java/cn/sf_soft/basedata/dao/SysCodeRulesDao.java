package cn.sf_soft.basedata.dao;

import java.util.List;

import cn.sf_soft.basedata.model.SysCodeRules;
import cn.sf_soft.common.dao.BaseDao;



public interface SysCodeRulesDao extends BaseDao{

//	public SysCodeRules getSysCodeRules
	/**
	 * 获取编码
	 * @param ruleNo
	 * @param stationId
	 * @return
	 */
	public List<SysCodeRules> getSysCodeRules(final String ruleNo,final String stationId);
	/**
	 * 更新编码
	 * @param code
	 */
	public  void updateSysCodeRules(SysCodeRules code) ;
	
}
