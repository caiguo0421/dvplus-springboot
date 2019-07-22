package cn.sf_soft.basedata.service.impl;

import cn.sf_soft.basedata.model.BasePartType;
import cn.sf_soft.basedata.model.SysStations;
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

/**
 * @Auther: chenbiao
 * @Date: 2018/10/25 15:14
 * @Description:
 */
@Service("baseVehicleNameService")
public class BaseVehicleNameServiceImpl {

    @Autowired
    @Qualifier("baseDao")
    private BaseDao baseDao;


    public List<Map<String, Object>> list(String stationId) {
        return baseDao.getMapBySQL("SELECT self_id as id, parent_id as parentId, common_name as commonName, common_type as commonType FROM dbo.base_vehicle_name WHERE (status is null or status=1) and (station_id is null or station_id like '%"+stationId+"%') ORDER BY common_type", null);
    }
}
