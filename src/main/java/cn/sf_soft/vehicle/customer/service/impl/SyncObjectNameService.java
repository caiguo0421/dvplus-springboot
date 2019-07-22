package cn.sf_soft.vehicle.customer.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.dao.DefaultTransformerAdapter;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.customer.model.InterestedCustomers;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: chenbiao
 * @Date: 2019/6/4 15:49
 * @Description:
 */
@Service
public class SyncObjectNameService {

    private static Logger logger = LoggerFactory.getLogger(SyncObjectNameService.class);

    @Autowired
    private BaseDao baseDao;

    private static final Map<String, String[][]> TARGET = new HashMap<String, String[][]>(){
        {
            put("base_service_account", new String[][]{{"supplier_id", "supplier_name"}});
            put("finance_credit_contracts", new String[][]{{"object_id", "object_name"}});
            put("finance_document_entries", new String[][]{{"object_id", "object_name"}});
            put("finance_guaranties", new String[][]{{"object_id", "object_name"}});
            put("finance_invoices", new String[][]{{"object_id", "object_name"}});
            put("finance_notes", new String[][]{{"object_id", "object_name"}});
            put("finance_payment_requests", new String[][]{{"object_id", "object_name"}});
            put("finance_settlements", new String[][]{{"object_id", "object_name"}});
            put("finance_settlements_details", new String[][]{{"object_id", "object_name"}});
            put("insurance", new String[][]{{"customer_id", "customer_name"}, {"supplier_id", "supplier_name"}});
            put("insurance_profit_vary", new String[][]{{"supplier_id", "supplier_name"}});
            put("part_in_stocks", new String[][]{{"supplier_id", "supplier_name"}});
            put("part_out_stocks", new String[][]{{"customer_id", "customer_name"}, {"supplier_id", "supplier_name"}});
            put("part_pre_orders", new String[][]{{"customer_id", "customer_name"}});
            put("part_purchase_orders", new String[][]{{"supplier_id", "supplier_name"}});
            put("part_purchase_orders_track", new String[][]{{"supplier_id", "supplier_name"}});
            put("part_purchase_plan_detail", new String[][]{{"supplier_id", "supplier_name"}});
            put("part_sale_orders", new String[][]{{"customer_id", "customer_name"}});
            put("service_claims", new String[][]{{"customer_id", "customer_name"}, {"supplier_id", "supplier_name"}});
            put("service_work_list_consigns", new String[][]{{"supplier_id", "supplier_name"}});
            put("service_work_lists", new String[][]{{"customer_id", "customer_name"}, {"supplier_id", "supplier_name"}});
            put("vehicle_in_stocks", new String[][]{{"supplier_id", "supplier_name"}});
            put("vehicle_out_stocks", new String[][]{{"customer_id", "customer_name"}});
            put("vehicle_purchase_contracts", new String[][]{{"supplier_id", "supplier_name"}});
            put("vehicle_sale_contracts", new String[][]{{"customer_id", "customer_name"}});
            put("vehicle_stocks", new String[][]{{"customer_id", "customer_name"}, {"supplier_id", "supplier_name"}});
        }
    };

    public void sync(Object object){
        if(null == object) return;
        logger.debug("同步更新往来对象名称");
        String objectId = null;
        String objectName = null;
        if(object instanceof BaseRelatedObjects){
            objectId = ((BaseRelatedObjects)object).getObjectId();
            objectName = ((BaseRelatedObjects)object).getObjectName();
        }else if(object instanceof InterestedCustomers){
            objectId = ((InterestedCustomers)object).getObjectId();
            objectName = ((InterestedCustomers)object).getObjectName();
        }else{
            throw new ServiceException("无效的类型");
        }
        if(StringUtils.isEmpty(objectId)){
            throw new ServiceException("往来对象标识不能为空");
        }
        if(StringUtils.isEmpty(objectName)){
            throw new ServiceException("往来对象名称不能为空");
        }
        logger.debug("objectId:{},objectName:{}", objectId, objectName);
        Session session = baseDao.getCurrentSession();
        String tmp = "update %s set %s=? where %s=? and %s<>?";
        for(String table : TARGET.keySet()){
            String[][] values = TARGET.get(table);
            for(int i=0; i<values.length; i++){
                String[] value = values[i];
                String idColumn = value[0];
                String nameColum = value[1];
                logger.debug("table:{},idColumn:{},nameColumn:{}", new Object[]{table, idColumn, nameColum});
                try {
                    SQLQuery sqlQuery = session.createSQLQuery(String.format(tmp, table, nameColum, idColumn, nameColum));
                    sqlQuery.setParameter(0, objectName);
                    sqlQuery.setParameter(1, objectId);
                    sqlQuery.setParameter(2, objectName);
                    int count = sqlQuery.executeUpdate();
                    logger.debug("执行记录数：{}", count);
                }catch (Exception e){
                    logger.error("同步更新往来对象名称出错table:{},idColumn:{},nameColumn:{}", new Object[]{table, idColumn, nameColum});
                    throw new ServiceException("同步更新往来对象名称出错", e);
                }
            }
        }

    }
}
