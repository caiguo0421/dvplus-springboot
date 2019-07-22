package cn.sf_soft.vehicle.customer.dao;

import java.util.List;

import cn.sf_soft.vehicle.customer.model.SysOptions;

public interface SysOptionsDao {

	/**
	 * 根据optionNo查找记录
	 * @param optionNo
	 * @param stationId
	 * @return
	 */
	List<SysOptions> getOptionsByOptionNo(String optionNo, String stationId);
	
	/**
	 * 获得系统选项，并转换为boolean值
	 * @param optionNo
	 * @return true：optionValue-是; false:其他
	 */
	boolean getOptionForBoolean(String optionNo);
	
	/**
	 * 获得系统选项，并转换为boolean值
	 * @param optionNo
	 * @return true：optionValue-是; false:其他
	 */
	boolean getOptionForBoolean(String optionNo, String stationId);

	String getOptionForString(String optionNo, String stationId);

	String getOptionForString(String optionNo);

}
