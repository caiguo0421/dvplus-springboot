package cn.sf_soft.parts.inventory.dao.hbb;

import java.text.ParseException;
import java.util.*;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.parts.inventory.model.PartCheckStocksDetail;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.parts.inventory.model.PartCheckStocks;
import org.springframework.stereotype.Service;

@Repository("partCheckStocksDao")
public class PartCheckStocksDao extends BaseDaoHibernateImpl {

	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PartCheckStocksDao.class);

//	/**
//	 * sql中checkStatus(盘点状态)更改相应的getPartCheckStockById()也要做调整，
//	 * 因为客户端要将checkStatus作为筛选条件
//	 */
//	static String SQL = "SELECT * FROM (SELECT a.*,\n"+
//			"(SELECT\n"+
//			"   (CASE WHEN a.status is null or a.status<"+Constant.DocumentStatus.SUBMITED+" THEN\n"+
//			"   (CASE WHEN a.checkQuantity<=0 THEN "+CheckStatus.WAITING_CHECK.code+"\n"+
//			"       \t WHEN a.checkQuantity<=a.planCheckQuantity THEN "+CheckStatus.CHECKING.code+"\n"+
//			"        ELSE "+CheckStatus.UNKNOWN.code+" end\n"+
//			"        )\n"+
//			"        WHEN a.status="+Constant.DocumentStatus.SUBMITED+" OR a.status="+Constant.DocumentStatus.APPROVEING+" THEN "+CheckStatus.WAITING_APPROVE.code+"\n"+
//			"        WHEN a.status="+Constant.DocumentStatus.REVOKED+" THEN "+CheckStatus.REVOKED.code+"\n"+
//			"        WHEN a.status="+Constant.DocumentStatus.AGREED+" THEN "+CheckStatus.AGREED.code+" ELSE "+CheckStatus.UNKNOWN.code+" end)) AS checkStatus\n"+
//			"FROM (\n"+
//			"\tselect\n"+
//			"\t        p.document_no as documentNo,\n"+
//			"\t        p.pcs_price as pcsPrice,\n"+
//			"\t        p.remark as remark,\n"+
//			"\t        p.station_id as stationId,\n"+
//			"\t        p.approver as approver,\n"+
//			"\t        p.inventory_type as inventoryType,\n"+
//			"\t        p.creator as creator,\n"+
//			"\t        p.creator_no as creatorNo,\n"+
//			"\t        p.create_time as createTime,\n"+
//			"\t        p.modifier as modifier,\n"+
//			"\t        p.modify_time as modifyTime,\n"+
//			"\t        p.creator_unit_no as creatorUnitNo,\n"+
//			"\t        p.creator_unit_name as creatorUnitName,\n"+
//			"\t        p.status as status,\n"+
//			"\t        p.department_id as departmentId,\n"+
//			"\t        p.department_no as departmentNo,\n"+
//			"\t        p.department_name as departmentName,\n"+
//			"\t        p.approver_id as approverId,\n"+
//			"\t        p.approver_no as approverNo,\n"+
//			"\t        p.approver_name as approverName,\n"+
//			"\t        p.approve_time as approveTime,\n"+
//			"\t        p.approver_unit_no as approverUnitNo,\n"+
//			"\t        p.approver_unit_name as approverUnitName,\n"+
//			"\t        p.user_id as userId,\n"+
//			"\t        p.user_no as userNo,\n"+
//			"\t        p.user_name as userName,\n"+
//			"\t        p.submit_time as submitTime,\n"+
//			"\t        p.submit_station_id as submitStationId,\n"+
//			"\t        p.submit_station_name as submitStationName,\n"+
//			"\t        (SELECT count(1) FROM part_check_stocks_detail b WHERE b.document_no=p.document_no) as planCheckQuantity,\n"+
//			"\t        (SELECT count(1) FROM part_check_stocks_detail b WHERE b.document_no=p.document_no and check_status=1) as checkQuantity\n"+
//			"\t    from\n"+
//			"\t        dbo.part_check_stocks p \n"+
//			"\t    LEFT JOIN sys_stations s ON p.station_id=s.station_id\n"+
//			"    ) a) b\n"+
//			"\n";

	@SuppressWarnings("unchecked")
	public PageModel<PartCheckStocks> getPartCheckStock(List<String> stationIds, JsonObject condition, String orderBy, int pageNo, int pageSize) {
		Map.Entry<String, Object[]> entry = buildConditionString(condition, null);
		StringBuffer buff = new StringBuffer("from PartCheckStocks");
		buff.append(" where ").append("stationId in ('" + StringUtils.join(stationIds, "','") + "') ");
		if(null != entry && StringUtils.isNotEmpty(entry.getKey())){
			buff.append(" and ").append(entry.getKey());
		}
		if(StringUtils.isNotEmpty(orderBy)){
			buff.append(" order by ").append(orderBy);
		}
		if(logger.isDebugEnabled()){
			logger.debug("hql:"+buff);
		}
		return this.listInHql(buff.toString(), pageNo, pageSize, null != entry ? entry.getValue() : null);
	}

	public PartCheckStocks getPartCheckStockById(String documentNo){
		return this.get(PartCheckStocks.class, documentNo);
	}

	public List<PartCheckStocksDetail> getPartCheckStockDetail(String documentNo, JsonObject condition, String orderBy) {
		String sql = "from PartCheckStocksDetail where documentNo=?";
		Map.Entry<String,Object[]> entry = buildPartCheckStockDetailCondition(condition, "");
		StringBuffer buff = new StringBuffer(sql);
		List<Object> params = new ArrayList<Object>();
		params.add(documentNo);
		if(null != entry){
			buff.append(" and ").append(entry.getKey());
			for(int i=0; i<entry.getValue().length; i++){
				params.add(entry.getValue()[i]);
			}
		}

		if(StringUtils.isNotEmpty(orderBy)){
			buff.append(" order by ").append(orderBy);
		}
		return (List<PartCheckStocksDetail>)this.findByHql(buff.toString(),params.toArray());
	}

	private Map.Entry<String, Object[]> buildPartCheckStockDetailCondition(JsonObject condition, String alias){
		if(null != condition && !condition.isJsonNull()){
			if(StringUtils.isNotEmpty(alias)){
				alias += ".";
			}else{
				alias = StringUtils.EMPTY;
			}
			List<String> where = new ArrayList<String>();
			List<Object> parms = new ArrayList<Object>();
			if(condition.has("part")){
				String val = condition.get("part").getAsString();
				if(StringUtils.isNotEmpty(val)){
					StringBuffer buff = new StringBuffer();
					buff.append("(");

					buff.append(alias).append("partNo like ? or ")
							.append(alias).append("partName like ? ");
					buff.append(")");
					where.add(buff.toString());
					parms.add("%"+val+"%");
					parms.add("%"+val+"%");
				}

			}
			if(condition.has("position")){
				String val = condition.get("position").getAsString();
				if(StringUtils.isNotEmpty(val)){
					where.add(alias+"depositPosition like ?");
					parms.add("%"+val+"%");
				}
			}

			if(condition.has("profitLossStatus")){
				int val = condition.get("profitLossStatus").getAsInt();
				if(10 == val){ //只看盘盈
					where.add(alias+"quantityFact > "+alias+"quantityStock");
				}else if(20 == val){
					where.add(alias+"quantityFact < "+alias+"quantityStock");
				}
			}

			if(condition.has("checkStatus")){
				JsonElement element = condition.get("checkStatus");
				if(null != element && !element.isJsonNull()){
					int val = condition.get("checkStatus").getAsInt();
					if(0 == val){    //未盘存
						where.add(alias+".checkStatus <> ?");
					}else if(1 == val){  //已盘存
						where.add(alias+".checkStatus = ?");
					}
					parms.add((short)1);
				}
			}

			if(where.size() > 0){
				return new AbstractMap.SimpleEntry(StringUtils.join(where, " and "), parms.toArray());
			}
		}
		return null;
	}

	private Map.Entry<String, Object[]> buildConditionString(JsonObject condition, String alias){
		if(null != condition && !condition.isJsonNull()){
			if(StringUtils.isNotEmpty(alias)){
				alias += ".";
			}else{
				alias = StringUtils.EMPTY;
			}
			List<String> where = new ArrayList<String>();
			List<Object> parms = new ArrayList<Object>();
			if(condition.has("searchString")){
				String val = condition.get("searchString").getAsString();
				StringBuffer buff = new StringBuffer();
				buff.append("(");

				buff.append(alias).append("documentNo like ? or ")
						.append(alias).append("creator like ? or ")
						.append(alias).append("remark like ?");
				buff.append(")");
				where.add(buff.toString());
				String value = "%"+val+"%";
				parms.add(value);
				parms.add(value);
				parms.add(value);
			}
			if(condition.has("status")){
				where.add(alias+"status=?");
				parms.add(condition.get("status").getAsShort());
			}
			if(condition.has("startCreateTime")){
				where.add(alias+"createTime>=?");
				String startCreateTime = condition.get("startCreateTime").getAsString() + " 00:00:00.000";
				try {
					parms.add(TimestampUitls.formatDate(startCreateTime));
				} catch (ParseException e) {
					throw new ServiceException(String.format("开始时间参数%s格式不合法", startCreateTime));
				}
			}
			if(condition.has("endCreateTime")){
				where.add(alias+"createTime<=?");
				String endCreateTime = condition.get("endCreateTime").getAsString() + " 23:59:59.999";
				try {
					parms.add(TimestampUitls.formatDate(endCreateTime));
				} catch (ParseException e) {
					throw new ServiceException(String.format("结束时间参数%s格式不合法", endCreateTime));
				}
			}
			if(where.size() > 0) {
				return new AbstractMap.SimpleEntry(StringUtils.join(where, " and "), parms.toArray());
			}
		}
		return null;
	}

	public Double getNowPrice(String stockId, List<String> stationIds){
		String sql = "SELECT \n" +
				"\tTOP(1) b.pis_price as pis_price \n" +
				"FROM  part_in_stocks a \n" +
				"LEFT JOIN part_in_stock_detail b ON a.pis_no=b.pis_no\n" +
				"WHERE \n" +
				"\t(a.pis_type=1 OR a.pis_type=3) AND a.approve_status=1 and b.stock_id='"+stockId+"' %s\n" +
				"ORDER BY a.approve_time DESC";
		StringBuffer stationCondition = new StringBuffer();
		stationCondition.append(" and ").append("a.station_id in ('" + StringUtils.join(stationIds, "','") + "') ");
		sql = String.format(sql, stationCondition);
		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Object> list = query.list();
		if(null == list || list.isEmpty()){
			return null;
		}else{
			return Tools.objToDouble(list.get(0));
		}
	}

	private <E> PageModel<E> listInHql (String hql, int pageNo, int pageSize, Object... values){
		PageModel pageModel = null;
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		if (values != null) {
			for(int i = 0; i < values.length; ++i) {
				query.setParameter(i, values[i]);
			}
		}
		//判断分页
		if(pageNo>0 && pageSize>0){
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		List<Object> list = query.list();
		pageModel = new PageModel(list);

		//分页
		if(pageNo>0 && pageSize>0){
			pageModel.setPage(pageNo);
			pageModel.setPerPage(pageSize);
		}
		return pageModel;
	}
}
