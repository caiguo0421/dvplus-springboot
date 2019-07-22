package cn.sf_soft.basedata.service.impl;

import cn.sf_soft.basedata.model.BasePartType;
import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.basedata.service.SysBaseDataService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysBaseDataService")
public class SysBaseDataServiceImpl implements SysBaseDataService {
	@Autowired
	@Qualifier("baseDao")
	private BaseDao baseDao;
	
	@SuppressWarnings("unchecked")
	public List<SysStations> getSysStations() {
		return baseDao.findByCriteria(DetachedCriteria.forClass(SysStations.class));
	}

	@SuppressWarnings("unchecked")
	public List<BasePartType> getBasePartsType(){
		DetachedCriteria dc = DetachedCriteria.forClass(BasePartType.class);
		dc.add(Restrictions.eq("status", (short)1));//状态为"正常中"的配件类型
		dc.addOrder(Order.asc("orderNo"));
		dc.addOrder(Order.asc("partTypeName"));
		return baseDao.findByCriteria(dc);
	}

	@Override
	public List<Map<String, Object>> getDepartments() {
		String sql = "SELECT   \n" +
				"a.unit_id,--机构ID\n" +
				"a.parent_id,--父级ID \n" +
				"a.full_id,--全ID\n" +
				"a.status, --状态值\n" +
				"b.meaning AS status_meaning, --状态中文意思\n" +
				"a.unit_type,--机构类型\n" +
				"c.meaning AS unit_type_meaning,--机构类型意思\n" +
				"a.unit_no,--机构编码\n" +
				"a.unit_name,--机构名称\n" +
				"a.principal,--负责人ID\n" +
				"d.user_name,--负责人名称\n" +
				"a.station_id,--管辖站点ID,有可能多个站点ID\n" +
				"a.default_station,--默认站点ID\n" +
				"e.station_name, --默认站点名称\n" +
				"a.phone,--固定电话\n" +
				"a.mobile,--移动电话\n" +
				"a.fax,--传真\n" +
				"a.url,--公司主页\n" +
				"a.email,--邮箱\n" +
				"a.address,--联系地址\n" +
				"a.postalcode,--邮编\n" +
				"a.remark,--备注\n" +
				"a.creator,--创建人\n" +
				"a.create_time,--创建时间\n" +
				"a.modifier,--更新人\n" +
				"a.modify_time,--更新时间\n" +
				"f.meaning AS unit_relation_meaning--机构关联\n" +
				"FROM sys_units a \n" +
				"LEFT JOIN \n" +
				"(SELECT code, meaning FROM sys_flags WHERE field_no = 'base.status') b \n" +
				"ON a.status = b.code \n" +
				"LEFT JOIN \n" +
				"(SELECT code, meaning FROM sys_flags WHERE field_no = 'unit_type') c \n" +
				"ON a.unit_type = c.code \n" +
				"LEFT JOIN \n" +
				"(SELECT user_id, user_name FROM sys_users) d \n" +
				"ON a.principal = d.user_id \n" +
				"LEFT JOIN (SELECT station_id, station_name FROM sys_stations) e \n" +
				"ON a.default_station = e.station_id\n" +
				" LEFT JOIN \n" +
				" (SELECT code,meaning FROM sys_flags WHERE field_no = 'unit_relation') f \n" +
				" ON a.unit_relation= f.code";
		return baseDao.getMapBySQL(sql, null);
	}

	@Override
	public List<Map<String, Object>> getBaseOthers(String typeNo, String stationId) {
		if(StringUtils.isEmpty(typeNo)){
			throw new ServiceException("选项类型不能为空");
		}
		Map<String, Object> parm = new HashMap<String, Object>(2);
		parm.put("typeCode", typeNo);
		parm.put("stationId", stationId);
		return baseDao.getMapBySQL("SELECT data as code, data as meaning, data_id as id, parent_id as parentId FROM dbo.base_others WHERE type_no=:typeCode and (station_id is null or station_id=:stationId) ORDER BY sort", parm);
	}
}
