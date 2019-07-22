package cn.sf_soft.vehicle.customer.service.impl;

import cn.sf_soft.basedata.dao.SysLogsDao;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.support.BaseService;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.model.BaseCustomerGroups;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户群主服务
 *
 * @author caigu
 */
@Service("baseCustomerGroupsService")
public class BaseCustomerGroupsService extends BaseService<BaseCustomerGroups> {

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BaseCustomerGroupsService.class);

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private SysLogsDao sysLogsDao;

    @Autowired
    private InterestedCustomersForCustomerGroupService interestedCustomersForCustomerGroupService;

    private static EntityRelation entityRelation;

    static {
        entityRelation = new EntityRelation(BaseCustomerGroups.class, "customerGroupId", BaseCustomerGroupsService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    /**
     * 保存客户群
     *
     * @param parameter
     * @return
     */
    public Map<String, List<Object>> saveCustomerGroup(JsonObject parameter) {
        List<EntityProxy<?>> contractProxies = this.save(parameter);
        EntityProxy<BaseCustomerGroups> entityProxy = (EntityProxy<BaseCustomerGroups>) contractProxies.get(0);
        BaseCustomerGroups groups = entityProxy.getEntity();

        //客户群保存接口（Customer/Customer_saveCustomerGroup.action）要改下：客户群模块要允许增加/删除成员
        JsonElement je = parameter.get("interested_customers");
        if (je != null && je.isJsonArray()) {
            JsonArray ja_interested_customers = je.getAsJsonArray();
            for (int i = 0; i < ja_interested_customers.size(); i++) {
                JsonArray ja_customer = new JsonArray();
                ja_customer.add(ja_interested_customers.get(i));
                JsonObject jo_interested_customers = new JsonObject();
                jo_interested_customers.add("interested_customers", ja_customer);
                interestedCustomersForCustomerGroupService.saveInterestedCustomer(jo_interested_customers);
            }
        }

        baseDao.flush();
        this.addSysLog(entityProxy.getOperation() == Operation.CREATE ? "新建" : "修改", String.format("客户群ID：%s，客户群名称：%s, 报文：%s", groups.getCustomerGroupId(), groups.getCustomerGroupName(), parameter.toString()));
        return convertReturnData(groups.getCustomerGroupId());
    }

    private void addSysLog(String logType, String description) {
        sysLogsDao.addSysLog(logType, "客户群", description);
    }

    public Map<String, List<Object>> convertReturnData(String customerGroupId) {
        Map<String, List<Object>> rtnData = new HashMap<>();
        convertReturnDataForDetail("base_customer_groups", "SELECT * FROM base_customer_groups where customer_group_id = :val", customerGroupId, rtnData);
        return rtnData;
    }

    private void convertReturnDataForDetail(String tableName, String sql, Object referenceVal, Map<String, List<Object>> rtnData) {
        List<Object> dataList = rtnData.get(tableName);
        if (dataList == null) {
            dataList = new ArrayList<>();
            rtnData.put(tableName, dataList);
        }
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("val", referenceVal);
        List<Object> data = baseDao.listInSql(sql, parmMap).getData();

        if (data == null || data.size() == 0) {
            return;
        }
        dataList.addAll(data);

//        for (Object item : data) {
//            Map itemMap = (Map) item;
//            if ("interested_customers".equals(tableName)) {
//                convertReturnDataForDetail("presell_visitors", "SELECT * FROM  presell_visitors where visitor_id = :val", itemMap.get("object_id"), rtnData);
//                convertReturnDataForDetail("presell_visitors_back", "SELECT * FROM  presell_visitors_back where object_id = :val", itemMap.get("object_id"), rtnData);
//                convertReturnDataForDetail("customer_retain_vehicle_overview", "SELECT * FROM  customer_retain_vehicle_overview where customer_id = :val", itemMap.get("object_id"), rtnData);
//            }
//        }
    }

    @Override
    public void beforeExecute(EntityProxy<BaseCustomerGroups> entityProxy) {
        SysUsers user = HttpSessionStore.getSessionUser();
        if (entityProxy.getOperation() == Operation.CREATE) {
            BaseCustomerGroups groups = entityProxy.getEntity();
            BaseCustomerGroups oriGroups = entityProxy.getOriginalEntity();

            String stationId = user.getDefaulStationId();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            groups.setStationId(stationId);
            groups.setCreator(user.getUserFullName());
            groups.setCreateTime(now);
            groups.setModifier(user.getUserFullName());
            groups.setModifyTime(now);

        } else {
            BaseCustomerGroups groups = entityProxy.getEntity();
            BaseCustomerGroups oriGroups = entityProxy.getOriginalEntity();
            validateModifyTime(groups.getModifyTime(), oriGroups.getModifyTime()); //校验modifyTime

            groups.setModifier(user.getUserFullName());
            groups.setModifyTime(new Timestamp(System.currentTimeMillis()));
        }
        validateRecord(entityProxy);
    }

    private void validateRecord(EntityProxy<BaseCustomerGroups> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        BaseCustomerGroups groups = entityProxy.getEntity();
        if (StringUtils.isBlank(groups.getCustomerGroupName())) {
            throw new ServiceException("客户群名称为空");
        }
        if (StringUtils.isBlank(groups.getCustomerGroupNo())) {
            throw new ServiceException("客户群编码为空");
        }
        List<BaseCustomerGroups> sameNameGroups = (List<BaseCustomerGroups>) baseDao.findByHql("FROM BaseCustomerGroups where customerGroupId<>? AND customerGroupName = ?", groups.getCustomerGroupId(), groups.getCustomerGroupName());
        if (sameNameGroups != null && sameNameGroups.size() > 0) {
            throw new ServiceException(String.format("%s的客户群名称重复", groups.getCustomerGroupName()));
        }
        List<BaseCustomerGroups> sameNoGroups = (List<BaseCustomerGroups>) baseDao.findByHql("FROM BaseCustomerGroups where customerGroupId<>? AND customerGroupNo = ?", groups.getCustomerGroupId(), groups.getCustomerGroupNo());
        if (sameNoGroups != null && sameNoGroups.size() > 0) {
            throw new ServiceException(String.format("%s的客户群编码重复", groups.getCustomerGroupName()));
        }
    }

    @Override
    public void execute(EntityProxy<BaseCustomerGroups> entityProxy) {

    }
}
