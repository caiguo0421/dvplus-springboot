package cn.sf_soft.basedata.dao;

import cn.sf_soft.common.dao.BaseDao;


public interface SysIdentityDao extends BaseDao {

	/**
	 * 根据表名获取表的自增长ID，并将其ID增长之后更新至表中
	 * @param tableName
	 * @return
	 */
	public int getIdentityAndIncrementUpdate(String tableName);
}
