package cn.sf_soft.parts.stockborwse.dao;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.parts.stockborwse.model.PartStockSearchCriteria;
import cn.sf_soft.parts.stockborwse.model.PartStockStatistical;

/**
 * 配件库存浏览
 * @创建人 Henry
 * @创建时间 2017-4-28 16:36:06
 * @修改人 
 * @修改时间
 */
public interface PartStockBrowseDao extends BaseDao {
	/**
	 * 根据查询条件获取配件库存统计信息
	 * @param condition
	 * @return
	 */
	public PartStockStatistical getPartStockStatistical(PartStockSearchCriteria condition);
}
