package cn.sf_soft.office.approval.dao.hbb;

import cn.sf_soft.basedata.model.BaseSysAgency;
import cn.sf_soft.basedata.model.SysFlags;
import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.office.approval.dao.LoanCreditInvestigationDao;
import cn.sf_soft.parts.inventory.model.PartPurchasePlanDetail;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("generalApproveDaoHibernate")
public class GeneralApproveDaoHibernate extends BaseDaoHibernateImpl implements LoanCreditInvestigationDao{

	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GeneralApproveDaoHibernate.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PartPurchasePlanDetail> getPartPurchasePlanDetailByDocumentNo(
			String documentNo) {
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append("select a.*,Convert(decimal(18,2),(a.plan_price * a.plan_quantity)) as cost,").append("\r\n").
		append("b.management_state,b.order_quantity,b.pre_order_count from part_purchase_plan_detail a").append("\r\n").
		append("left join vw_part_stocks b ON a.stock_id=b.stock_id").append("\r\n").
		append(" where a.document_no=:documentNo ORDER by cost desc");
		SQLQuery query=getSessionFactory().getCurrentSession().createSQLQuery(sqlBuffer.toString()).addEntity(PartPurchasePlanDetail.class);
		query.setParameter("documentNo", documentNo);
		List<PartPurchasePlanDetail> details=query.list();
		
		return details;
	}

	@Override
	public List<Map<String, Object>> getSupplierListByDocumentNo(
			String documentNo) {
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append("SELECT DISTINCT a.supplier_id,b.object_no AS supplier_no,")
		.append("\r\n").
		append("b.object_name as supplier_name FROM part_purchase_plan_detail AS a").
		append("\r\n").
		append("LEFT JOIN base_related_objects AS b ON a.supplier_id=b.object_id").
		append("\r\n").
		append("WHERE a.document_no=:documentNo");
		Map<String,Object> params=new HashMap<String,Object>();
	    params.put("documentNo", documentNo);
		List<Map<String,Object>>  list=getMapBySQL(sqlBuffer.toString(),params);
		return list;
	}

	@Override
	public List<Map<String,Object>> getDfSupplierNoByObjectNo(String objectNo) {
		String sql="SELECT object_no from base_related_objects where object_no=:objectNo and status=1";
		Map<String,Object> params=new HashMap<String,Object>(); 
		params.put("objectNo", objectNo);
		List<Map<String,Object>>  list=getMapBySQL(sql,params);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getPartPurchaseDefault(String stationId) {
		String hql="FROM SysStations where stationId=?";
		List<SysStations> list=(List<SysStations>) getHibernateTemplate().find(hql,new Object[]{stationId});
		if(list!=null&&list.size()>0){
			return list.get(0).getPartPurchaseDefault();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Short, Object> getOrderType(String fieldNo) {
		String hql="FROM SysFlags where fieldNo=?";
		List<SysFlags> list=(List<SysFlags>) getHibernateTemplate().find(hql,new Object[]{fieldNo});
		Map<Short,Object> map=new HashMap<Short,Object>();
		if(list!=null&&list.size()>0){
			for(SysFlags sf:list){
				map.put(sf.getCode(),sf.getMeaning());
			}
		}
		return map;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public BaseSysAgency getBaseSysAgencyByStationId(String stationId) {
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append("SELECT * FROM base_sys_agency where product_line='20配件' AND").append("\r\n").
		append("agency_code=(select service_code from sys_stations where station_id=:stationId)");
		SQLQuery query=getSessionFactory().getCurrentSession().createSQLQuery(sqlBuffer.toString()).addEntity(BaseSysAgency.class);
		query.setParameter("stationId", stationId);
		List<BaseSysAgency> list=query.list();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
