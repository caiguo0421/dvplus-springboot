package cn.sf_soft.basedata.service;


import cn.sf_soft.basedata.model.SysCodeRules;

public interface SysCodeRulesService {
	/**
	 * 获取编码
	 * @param ruleNo
	 * @param stationId
	 * @return
	 */
	public String createSysCodeRules(final String ruleNo,final String stationId) ;
	/**
	 * 更新编码
	 * @param code
	 */
	public void updateSysCodeRules(SysCodeRules code);
}
