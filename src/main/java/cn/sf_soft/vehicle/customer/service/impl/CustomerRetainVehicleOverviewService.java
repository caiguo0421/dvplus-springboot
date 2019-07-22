package cn.sf_soft.vehicle.customer.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.model.CustomerRetainVehicleOverview;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @Auther: caigx
 * @Date: 2018/10/08 11:05
 * @Description: 意向客户-保有车辆服务
 */
@Service
public class CustomerRetainVehicleOverviewService implements Command<CustomerRetainVehicleOverview> {
    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerRetainVehicleOverviewService.class);

    @Autowired
    private BaseDao baseDao;

    private static EntityRelation entityRelation;

    static {
        entityRelation = new EntityRelation(CustomerRetainVehicleOverview.class, CustomerRetainVehicleOverviewService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<CustomerRetainVehicleOverview> entityProxy) {
        SysUsers user = HttpSessionStore.getSessionUser();
        if (entityProxy.getOperation() == Operation.CREATE) {
            CustomerRetainVehicleOverview overview = entityProxy.getEntity();
            CustomerRetainVehicleOverview oriOverview = entityProxy.getOriginalEntity();

            Timestamp now = new Timestamp(System.currentTimeMillis());
            overview.setCreator(user.getUserFullName());
            overview.setCreateTime(now);
        } else if(entityProxy.getOperation() == Operation.UPDATE){
            CustomerRetainVehicleOverview overview = entityProxy.getEntity();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            overview.setModifier(user.getUserFullName());
            overview.setModifyTime(now);
        }

        validateRecord(entityProxy);
    }

    private void validateRecord(EntityProxy<CustomerRetainVehicleOverview> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE || entityProxy.getOperation() == Operation.NONE) {
            return;
        }

        //保有车辆：是否本店购买、是否东风车辆、车辆名称、数量
        CustomerRetainVehicleOverview overview = entityProxy.getEntity();
        if (overview.getIsOurPurchase() == null) {
            throw new ServiceException("保有车辆中是否本店购买不能为空");
        }

        if (overview.getIsDfVehicle() == null) {
            throw new ServiceException("保有车辆中是否东风车辆不能为空");
        }
        if (StringUtils.isBlank(overview.getVehicleName())) {
            throw new ServiceException("保有车辆中车辆名称不能为空");
        }
        if (Tools.toDouble(overview.getVehicleCount()) <= 0.00D) {
            throw new ServiceException("保有车辆中数量必须大于0");
        }
//        if(Tools.toDouble(overview.getVehicleAge())<=0.00D){
//            throw new ServiceException("保有车辆中车龄必须大于0");
//        }
    }

    @Override
    public void execute(EntityProxy<CustomerRetainVehicleOverview> entityProxy) {

    }
}
