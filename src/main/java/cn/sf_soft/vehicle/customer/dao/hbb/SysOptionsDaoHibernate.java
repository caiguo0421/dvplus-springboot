package cn.sf_soft.vehicle.customer.dao.hbb;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.sf_soft.common.dao.hbb.BaseDaoHibernateImpl;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.SysOptions;

@Repository("sysOptionsDao")
public class SysOptionsDaoHibernate extends BaseDaoHibernateImpl implements SysOptionsDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<SysOptions>  getOptionsByOptionNo(String optionNo,String stationId){
		//stationId为null表示适用所有站点
		String hql = "from SysOptions  where optionNo = ? and (stationId = ?  OR  stationId is NULL)";
		List<SysOptions> list = (List<SysOptions>) getHibernateTemplate().find(hql,
				new Object[] { optionNo, stationId});
		return list;
	}

	@Override
	public boolean getOptionForBoolean(String optionNo) {
		SysUsers user = HttpSessionStore.getSessionUser();
		return getOptionForBoolean(optionNo, user.getDefaulStationId());
	}

	@Override
	public boolean getOptionForBoolean(String optionNo, String stationId) {
		boolean result = false;
		List<SysOptions> options = getOptionsByOptionNo(optionNo, stationId);
		if (options != null && options.size() > 0) {
			if ("是".equals(options.get(0).getOptionValue())) {
				result = true;
			}
		}
		return result;
	}


	@Override
	public String getOptionForString(String optionNo) {
		SysUsers user = HttpSessionStore.getSessionUser();
		return getOptionForString(optionNo, user.getDefaulStationId());
	}

	@Override
	public String getOptionForString(String optionNo, String stationId) {
		String result = "";
		List<SysOptions> options = getOptionsByOptionNo(optionNo, stationId);
		if (options != null && options.size() > 0) {
			result = options.get(0).getOptionValue();
		}
		return result;
	}


}
