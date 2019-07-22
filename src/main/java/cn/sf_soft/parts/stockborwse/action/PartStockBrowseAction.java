package cn.sf_soft.parts.stockborwse.action;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.common.annotation.ModuleAccess;
import cn.sf_soft.common.annotation.Modules;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.parts.stockborwse.model.*;
import cn.sf_soft.parts.stockborwse.service.PartStockBrowseService;
import com.google.gson.JsonSyntaxException;


import java.util.List;
import java.util.Map;

/**
 * 配件库存浏览
 * @author king
 * @create 2013-9-5上午10:18:32
 */
//@ModuleAccess(moduleId = Modules.Parts.STOCK_BROWSE)
public class PartStockBrowseAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -308608828496023534L;
	private PartStockHistorySearchCriteria historySearchCriteria;
	private PartStockBrowseService partStockBrowseService;
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PartStockBrowseAction.class);
	private String orderBy;

	public void setHistorySearchCriteria(String historySearchCriteria) {
		
		this.historySearchCriteria = gson.fromJson(historySearchCriteria, PartStockHistorySearchCriteria.class);
		if(this.historySearchCriteria == null){
			this.historySearchCriteria = new PartStockHistorySearchCriteria();
		}
	}
	public void setPartStockBrowseService(PartStockBrowseService partStockBrowseService) {
		this.partStockBrowseService = partStockBrowseService;
	}
	
	/**
	 * 获取配件库存信息
	 * @return
	 */
//	@Access(needPopedom=AccessPopedom.StockBrowse.PART_STOCK_BROWSE)
	@Access(pass = true)
	public String getPartStock(){
		List<VwPartStocks> list = partStockBrowseService.getPartStockByCriteria(initCriteria(), pageNo, pageSize);
		ResponseMessage<List<VwPartStocks>> result = new ResponseMessage<List<VwPartStocks>>(list);
		result.setPageNo(pageNo);
		result.setPageSize(pageSize);
		result.setTotalSize(partStockBrowseService.getResultSize(initCriteria()));
		logger.debug("配件数量：" + result.getTotalSize());
		setResponseMessageData(result);
		return SUCCESS;
	 }


	/**
	 * 获取配件库存信息
	 * @return
	 */
//	@Access(needPopedom=AccessPopedom.StockBrowse.PART_STOCK_BROWSE)
	@Access(pass = true)
	public String getPartStockV2(){
		List<Map<String, Object>> list = partStockBrowseService.getPartStock(initCriteria(), pageNo, pageSize, orderBy);
		ResponseMessage<List<Map<String, Object>>> result = new ResponseMessage<List<Map<String, Object>>>(list);
		result.setPageNo(pageNo);
		result.setPageSize(pageSize);
		result.setTotalSize(partStockBrowseService.getPartStockCount(initCriteria()));
//		logger.debug("配件数量：" + result.getTotalSize());
		setResponseMessageData(result);
		return SUCCESS;
	}

	/**
	 * 查询零件总数  总成本
	 */
//	@Access(needPopedom=AccessPopedom.StockBrowse.PART_STOCK_BROWSE)
	@Access(pass = true)
	public String getListCountOfPart(){
		PartStockStatistical  list = partStockBrowseService.getPartStockStatistical(initCriteria());
		setResponseData(list);
		return SUCCESS;
	}
	
	/**
	 * 获取配件出入库历史
	 * @return
	  */
//	@Access(needPopedom=AccessPopedom.StockBrowse.PART_STOCK_BROWSE)
	@Access(pass = true)
	public String getPartStockHistory(){
		List<VwPartIomList> list = partStockBrowseService.getPartStockHistoryByCriteria(historySearchCriteria, pageNo, pageSize);
		ResponseMessage<List<VwPartIomList>> result = new ResponseMessage<List<VwPartIomList>>(list);
		result.setPageNo(pageNo);
		result.setPageSize(pageSize);
		result.setTotalSize(partStockBrowseService.getHistoryResultSize(historySearchCriteria));
		setResponseMessageData(result);
		return SUCCESS;
	}
	
	/**
	 * 得到配件仓库数据
	 * @return
	 */
//	@Access(needPopedom=AccessPopedom.StockBrowse.PART_STOCK_BROWSE)
	@Access(pass = true)
	public String getInitData(){
		List<PartWarehouse> warehouses = partStockBrowseService.getPartWarehouse(stationIds);
		PartStockBrowseInitData initData = new PartStockBrowseInitData();
		initData.setPartWarehouse(warehouses);
		setResponseData(initData);
		return SUCCESS;
	}
	
	private PartStockSearchCriteria initCriteria(){
		PartStockSearchCriteria criteria = null;
		try {
			criteria = gson.fromJson(searchCriteria, PartStockSearchCriteria.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw new ServiceException("参数不合法");
		}
		if(criteria == null){
			criteria = new PartStockSearchCriteria();
		}
		if(criteria.getStationIds() == null || criteria.getStationIds().size() == 0) {
			criteria.setStationIds(stationIds);
		}
		return criteria;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
