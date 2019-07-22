package cn.sf_soft.parts.stockborwse.service;

import cn.sf_soft.parts.stockborwse.model.*;

import java.util.List;
import java.util.Map;

/**
 * 配件库存浏览
 * @author king
 * @create 2013-9-5上午10:29:45
 */
public interface PartStockBrowseService {

	/**
	 * 根据条件分页查询配件库存信息
	 * @param criteria	查询条件
	 * @param pageNo	查询第几页数据
	 * @param pageSize	每页数据条数
	 * @return
	 * @throws Exception
	 */
	public List<VwPartStocks> getPartStockByCriteria(PartStockSearchCriteria criteria, int pageNo, int pageSize);


	/**
	 * 根据条件分页查询配件库存信息
	 * @param criteria	查询条件
	 * @param pageNo	查询第几页数据
	 * @param pageSize	每页数据条数
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getPartStock(PartStockSearchCriteria criteria, int pageNo, int pageSize, String orderBy);

	/**
	 * 根据条件分页查询配件库存信息数量
	 * @param criteria	查询条件
	 * @return
	 * @throws Exception
	 */
	public long getPartStockCount(PartStockSearchCriteria criteria);

	/**
	 * 根据条件分页查询配件出入库历史
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<VwPartIomList> getPartStockHistoryByCriteria(PartStockHistorySearchCriteria criteria, int pageNo, int pageSize);
	

	/**
	 * 根据搜索条件得到配件记录的总条数
	 * @param criterial
	 * @return
	 * @throws Exception
	 */
	public long getResultSize(PartStockSearchCriteria criterial);
	
	/**
	 * 根据搜索条件得到配件历史记录的总条数
	 * @param criterial
	 * @return
	 * @throws Exception
	 */
	public long getHistoryResultSize(PartStockHistorySearchCriteria criterial);
	
	/**
	 * 得到配件仓库
	 * @return
	 */
	public List<PartWarehouse> getPartWarehouse(List<String> stationIds);
	
	public Map<String, List<Map<String, String>>> getPartWarehouseMap(List<String> stationIds);


	/**
	 *
	 * 得到总数 总成本
	 * @return
	 * @throws Exception
	 */
	public  PartStockStatistical getPartStockStatistical(PartStockSearchCriteria criterial);
	
}
