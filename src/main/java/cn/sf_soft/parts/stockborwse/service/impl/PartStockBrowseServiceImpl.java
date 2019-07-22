package cn.sf_soft.parts.stockborwse.service.impl;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.parts.stockborwse.dao.PartStockBrowseDao;
import cn.sf_soft.parts.stockborwse.model.*;
import cn.sf_soft.parts.stockborwse.service.PartStockBrowseService;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
@Service("partStockBrowseService")
public class PartStockBrowseServiceImpl implements PartStockBrowseService {


	@Autowired
	private BaseDao baseDao;

	@Autowired
	/* @Qualifier("baseDao") */
	private PartStockBrowseDao dao;
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PartStockBrowseServiceImpl.class);

	/* public void setDao(PartStockBrowseDao dao) {
		this.dao = dao;
	} */

	/**
	 * @author Henry
	 */
	@SuppressWarnings("unchecked")
	
	/**
	 * 查询配件信息
	 */
	public List<VwPartStocks> getPartStockByCriteria(
			PartStockSearchCriteria criteria, int pageNo, int pageSize){
		DetachedCriteria dc = createCriteria(criteria);
		dc.addOrder(Order.asc("partNo"));
		dc.addOrder(Order.asc("stationId"));
		dc.addOrder(Order.asc("warehouseName"));
		dc.addOrder(Order.desc("quantity"));
		return dao.findByCriteria(dc, pageNo, pageSize);
	}

	private String buildConditionString(PartStockSearchCriteria condition){
		List<String> conditionArray = new ArrayList<String>();
		conditionArray.add("1 = 1");
		if(condition != null){
			List<String> stationIds = condition.getStationIds();
			String partName = condition.getPartName();
			String warehouseId = condition.getWarehouseId();

			if (stationIds != null && stationIds.size() > 0){
				conditionArray.add("a.station_id IN ('" + StringUtils.join(stationIds, "','") + "')");
			}

			if (partName != null && partName.length() > 0){
				conditionArray.add("(a.part_no LIKE '%" + partName + "%' OR a.name_pinyin LIKE '%" + partName + "%' OR a.part_name LIKE '%" + partName + "%')");
			}

			if (warehouseId != null && warehouseId.length() > 0){
				conditionArray.add("(a.warehouse_id = '" + warehouseId + "')");
			}
		}

		return " " + StringUtils.join(conditionArray, " AND ") + " ";
	}



	@Override
	public List<Map<String, Object>> getPartStock(PartStockSearchCriteria condition, int pageNo, int pageSize, String orderBy) {
		String conditionString = buildConditionString(condition);
		if(orderBy != null && orderBy.length()>0){
			orderBy = " ORDER BY a." + orderBy + " ";
		}else{
			orderBy = " ORDER BY a.stock_id DESC";
		}
		String sql =  "SELECT \n" +
				"a.stock_id,--库存ID\n" +
				"a.unit,--单位\n" +
				"a.station_id,--站点ID\n" +
				"a.station_name,--站点名称\n" +
				"a.warehouse_id,--仓库ID\n" +
				"a.warehouse_name,--仓库名称\n" +
				"a.part_no,--配件编码\n" +
				"a.producing_area,--配件产地(批次)\n" +
				"a.part_name,--配件名称\n" +
				"a.deposit_position,--库位\n" +
				"a.applicable_model,--适用车型\n" +
				"a.spec_model,--规格型号\n" +
				"a.part_type,--配件类型\n" +
				"a.quantity,--库存数量\n" +
				"a.quantity_way,--在途数量\n" +
				"isnull(a.order_quantity,0)+isnull(a.pre_order_count,0) AS pre_order_counts,--预定数量\n" +
				"a.cost,--配件库存成本\n" +
				"a.cost_ref,--配件参考成本\n" +
				"a.price_retail_use,--销售价\n" +
				"a.price_trade_use,--批发价\n" +
				"a.price_inner_use,--内部价\n" +
				"a.price_counterclaim_use,--保修价\n" +
				"a.price_insurance_use,--保险价\n" +
				"a.last_pis_time,--最近入库日期\n" +
				"a.last_pos_time,--最近出库日期\n" +
				"a.stocks_level,--配件库存级别\n" +
				"c.max_quantity_manual_use,--最适库存（最高库存） \n" +
				"a.stocks_mini_ip,--库存MINI-IP\n" +
				"c.min_quantity_use,--最低库存\n" +
				"c.safe_days_manual_use , --安全周期（合理库存天数）\n" +
				"c.stocks_days,--库龄（最长库龄）\n" +
				"CASE WHEN isnull(c.stocks_days,0)>isnull(c.safe_days_manual_use,0) THEN a.quantity  ELSE 0 END quantity_overdue, --超期数量\n" +
				"CASE WHEN isnull(a.quantity,0)>isnull(c.max_quantity_manual_use,0) \n" +
				"THEN isnull(a.quantity,0)-isnull(c.max_quantity_manual_use,0)  ELSE 0 END quantity_over_max, --超高数量\n" +
				"CASE WHEN isnull(c.min_quantity_use,0)>isnull(a.quantity,0) \n" +
				"THEN isnull(c.min_quantity_use,0)-isnull(a.quantity,0)  ELSE 0 END quantity_under_mini, --超低数量\n" +
				"a.default_supplier_id,--默认供应商ID\n" +
				"a.default_supplier_no,--默认供应商编码\n" +
				"a.default_supplier_name,--默认供应商名称\n" +
				"--如果东风配件ID不为空，则是东风配件\n" +
				"CASE WHEN isnull(b.part_id_df,'') <> '' THEN '是' ELSE '否' END AS is_df_part --是否是东风配件\n" +
				"FROM \n" +
				"vw_part_stocks a --配件库存查询视图\n" +
				"LEFT JOIN base_part_catalogs b --配件目录表\n" +
				"ON a.part_id=b.part_id\n" +
				"LEFT JOIN\n" +
				"(\n" +
				"  SELECT stock_id,\n" +
				"  CASE WHEN ISNULL(a.max_quantity_manual,0)>0 THEN ISNULL(a.max_quantity_manual,0) \n" +
				"  ELSE ISNULL(a.max_quantity_compute,0) END max_quantity_manual_use,--最高库存（最适库存）\n" +
				"  CEILING(CASE WHEN ISNULL(a.max_quantity_manual,0)>0 THEN ISNULL(a.max_quantity_manual,0)*ISNULL(a.stocks_mini_ip,1) \n" +
				"  ELSE ISNULL(a.max_quantity_compute,0)*ISNULL(a.stocks_mini_ip,1) END) min_quantity_use ,--最低库存\n" +
				"  CASE WHEN ISNULL(a.safe_days_manual,0)>0 THEN ISNULL(a.safe_days_manual,0) \n" +
				"  ELSE ISNULL(a.safe_days_compute,0) END safe_days_manual_use,  --安全周期（合理库存天数）\n" +
				"  CASE WHEN isnull(a.quantity,0)=0 THEN 0 ELSE DATEDIFF(day,ISNULL(last_pos_time, ISNULL(last_pis_time, '2017-01-01')),GETDATE()) END  stocks_days --库龄\n" +
				"  FROM vw_part_stocks  a\n" +
				"\n" +
				")c --最高库存、最低库存子查询\n" +
				"ON a.stock_id=c.stock_id\n" +
				"WHERE " + conditionString + orderBy + " OFFSET " + (pageNo-1) + " ROW FETCH NEXT " + pageSize + " ROW ONLY";

		return baseDao.getMapBySQL(sql, null);
	}

	@Override
	public long getPartStockCount(PartStockSearchCriteria condition) {
		String conditionString = buildConditionString(condition);
		String sql = "SELECT \n" +
				"COUNT(*) as ct \n" +
				"FROM \n" +
				"vw_part_stocks a --配件库存查询视图\n" +
				"LEFT JOIN base_part_catalogs b --配件目录表\n" +
				"ON a.part_id=b.part_id\n" +
				"LEFT JOIN\n" +
				"(\n" +
				"  SELECT stock_id,\n" +
				"  CASE WHEN ISNULL(a.max_quantity_manual,0)>0 THEN ISNULL(a.max_quantity_manual,0) \n" +
				"  ELSE ISNULL(a.max_quantity_compute,0) END max_quantity_manual_use,--最高库存（最适库存）\n" +
				"  CEILING(CASE WHEN ISNULL(a.max_quantity_manual,0)>0 THEN ISNULL(a.max_quantity_manual,0)*ISNULL(a.stocks_mini_ip,1) \n" +
				"  ELSE ISNULL(a.max_quantity_compute,0)*ISNULL(a.stocks_mini_ip,1) END) min_quantity_use ,--最低库存\n" +
				"  CASE WHEN ISNULL(a.safe_days_manual,0)>0 THEN ISNULL(a.safe_days_manual,0) \n" +
				"  ELSE ISNULL(a.safe_days_compute,0) END safe_days_manual_use,  --安全周期（合理库存天数）\n" +
				"  CASE WHEN isnull(a.quantity,0)=0 THEN 0 ELSE DATEDIFF(day,ISNULL(last_pos_time, ISNULL(last_pis_time, '2017-01-01')),GETDATE()) END  stocks_days --库龄\n" +
				"  FROM vw_part_stocks  a\n" +
				"\n" +
				")c --最高库存、最低库存子查询\n" +
				"ON a.stock_id=c.stock_id\n" +
				"WHERE " + conditionString;
		List<Map<String, Object>> result = baseDao.getMapBySQL(sql, null);
		return Long.parseLong(result.get(0).get("ct").toString());
	}

	/**
	 * 得到配件的总记录数
	 */
	@SuppressWarnings("rawtypes")
	public long getResultSize(PartStockSearchCriteria criterial){
		DetachedCriteria dc = createCriteria(criterial);
		dc.setProjection(Projections.rowCount());
		List count = dao.findByCriteria(dc);
		return (Long) count.get(0);
	}
	/**
	 * 查询配件出入库历史信息
	 */
	@SuppressWarnings("unchecked")
	public List<VwPartIomList> getPartStockHistoryByCriteria(
			PartStockHistorySearchCriteria criteria, int pageNo, int pageSize){
		DetachedCriteria dc = createCriteriahistory(criteria);
		dc.addOrder(Order.desc("createTime"));
		return dao.findByCriteria(dc, pageNo, pageSize);
	}
	/**
	 * 得到配件历史的总记录数
	 */
	@SuppressWarnings("rawtypes")
	public long getHistoryResultSize(PartStockHistorySearchCriteria criterial){
		DetachedCriteria dc = createCriteriahistory(criterial);
		dc.setProjection(Projections.rowCount());
		List count = dao.findByCriteria(dc);
		return (Long) count.get(0);
}
	
	private DetachedCriteria createCriteriahistory(PartStockHistorySearchCriteria criterial) {
		DetachedCriteria dc = DetachedCriteria.forClass(VwPartIomList.class);
		if(criterial != null){
			String stockId = criterial.getStockId();
			String partTo  = criterial.getPartTo();
			
			Date startTime = null;
			Date endTime   = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c= Calendar.getInstance();
			try{
				if("".equals(criterial.getStartTime()) || criterial.getStartTime()==null)
				{
					 startTime=null;
				}else
					 startTime = sdf.parse(criterial.getStartTime());
				if("".equals(criterial.getEndTime()) || criterial.getEndTime()==null){
					 endTime=null;
				}else
					c.setTime(sdf.parse(criterial.getEndTime()));
					c.add(Calendar.DATE, 1);
					endTime = c.getTime();
			}catch(Exception e){
				e.printStackTrace();
			}
			Integer outType = criterial.getOutType();
			dc.add(Restrictions.eq("stockId", stockId));
			dc.add(Restrictions.eq("approveStatus", 1));
			if(partTo != null && partTo.length() > 0){
				dc.add(Restrictions.like("partTo", "%"+partTo+"%"));
			}
			if(startTime != null && startTime.toString().length() > 0){
				dc.add(Restrictions.ge("approveTime", startTime));
			}
			if(endTime != null && endTime.toString().length() > 0){
				dc.add(Restrictions.lt("approveTime", endTime));
			}
			if(outType != null && !"".equals(outType)){
				dc.add(Restrictions.eq("outType", outType));
			}
		}
		
		return dc;
	}

	private DetachedCriteria createCriteria(PartStockSearchCriteria criterial) {
		DetachedCriteria dc = DetachedCriteria.forClass(VwPartStocks.class);
		if(criterial != null){
			String partNo = criterial.getPartNo();
			String partType = criterial.getPartType();
			List<String> stationId=criterial.getStationIds();
			String partWarehouseId = criterial.getWarehouseId();
			String applicableModel=criterial.getApplicableModel();
			String partName=criterial.getPartName();
			String specModel=criterial.getSpecModel();
			
			if(partNo != null && partNo.length() > 0){
				dc.add(Restrictions.or(Restrictions.like("partMnemonic", "%"+partNo+"%"), Restrictions.like("partNo", "%"+partNo+"%")));
			}
			if(partType != null && partType.length() > 0){
				dc.add(Restrictions.eq("partType", partType));
			}
			/*if(stationId != null || stationId.size() > 0){
				dc.add(Restrictions.eq("stationId", stationId));
			}*/
			
			/**
			 * 如果仓库不等于空，则不用管站点
			 */
			if(partWarehouseId != null && partWarehouseId.length() > 0){
				dc.add(Restrictions.eq("warehouseId", partWarehouseId));
			}else{
				dc.add(Restrictions.in("stationId", stationId));
			}
			if(applicableModel != null && applicableModel.length() > 0){
				dc.add(Restrictions.eq("applicableModel", applicableModel));
			}
			if(partName != null && partName.length() > 0){
				dc.add(Restrictions.like("partName", "%"+partName+"%"));
			}
			if(specModel != null && specModel.length() > 0){
				dc.add(Restrictions.eq("specModel", specModel));
			}
		}
		
		return dc;
	}

	/**
	 * 得到配件仓库数据
	 * @param stationIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PartWarehouse> getPartWarehouse(List<String> stationIds){
		List<PartWarehouse> partWarehouses = dao.findByNamedQueryAndNamedParam("partWarehouse", new String[]{"stationIds"}, new Object[]{stationIds});
		return partWarehouses;
	}

	public Map<String, List<Map<String, String>>> getPartWarehouseMap(
			List<String> stationIds) {
		List<PartWarehouse> partWarehouses = getPartWarehouse(stationIds);
		Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String,String>>>();
		for(PartWarehouse warehouse:partWarehouses){
			//某站点的仓库列表
			List<Map<String, String>> stationWarehouses = map.get(warehouse.getStationName());
			if(stationWarehouses == null){
				stationWarehouses = new ArrayList<Map<String,String>>();
			}
			Map<String, String> whMap = new HashMap<String, String>();
			whMap.put(warehouse.getWarehouseName(), warehouse.getWarehouseId());
			stationWarehouses.add(whMap);
			map.put(warehouse.getStationName(), stationWarehouses);
		}
		return map;
	}

	//获得总数，总金额
	public PartStockStatistical getPartStockStatistical(PartStockSearchCriteria criterial) {
		return dao.getPartStockStatistical(criterial);
	}


}
