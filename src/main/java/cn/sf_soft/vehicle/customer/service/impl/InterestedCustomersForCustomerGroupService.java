package cn.sf_soft.vehicle.customer.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.support.BaseService;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.vehicle.customer.model.InterestedCustomers;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 为客户分组准备的 意向客户服务；只接受UPDATE和NONE的操作
 */
@Service("interestedCustomersForCustomerGroupService")
public class InterestedCustomersForCustomerGroupService extends BaseService<InterestedCustomers> {

    @Autowired
    private InterestedCustomersService interestedCustomersService;

    private static EntityRelation entityRelation;

    static {
        entityRelation = new EntityRelation(InterestedCustomers.class, "objectId", InterestedCustomersForCustomerGroupService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }


    public void saveInterestedCustomer(JsonObject parameter) {
        List<EntityProxy<?>> interestedCustomerProxies = this.save(parameter);
    }

    @Override
    public void beforeExecute(EntityProxy<InterestedCustomers> entityProxy) {
        if (entityProxy.getOperation() == Operation.CREATE || entityProxy.getOperation() == Operation.DELETE) {
            throw new ServiceException("客户群保存接口中，意向客户（InterestedCustomers）的状态不能是CREATE或DELETE");
        }
    }

    @Override
    public void execute(EntityProxy<InterestedCustomers> entityProxy) {
        InterestedCustomers customers = entityProxy.getEntity();
        List<String> targetFields = new ArrayList<>(2);
        targetFields.add("customerGroupId");
        targetFields.add("isLeader");
        interestedCustomersService.updateRelatedObject(customers, targetFields);
    }
}
