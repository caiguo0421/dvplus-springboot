package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.VehicleConversionDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractItem;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VehicleSaleContractItemService implements Command<VehicleSaleContractItem> {

    protected static Logger logger = LoggerFactory.getLogger(VehicleSaleContractItemService.class);

    @Autowired
    private BaseDao baseDao;

    private static String ITEM_EXISTS_SQL = "SELECT count(1) FROM dbo.vehicle_conversion_detail \n"
            + "WHERE sale_contract_item_id=? AND status IN (0,1,2,30,40,50)";

    private static EntityRelation entityRelation;

    static {
        entityRelation = EntityRelation.newInstance(VehicleSaleContractItemService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleSaleContractItem> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractItemService.class, "beforeExecute");

        //合同号
        VehicleSaleContractItem detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContractDetail> masterProxy = entityProxy.getMaster();
        VehicleSaleContractDetail master = masterProxy.getEntity();
        detail.setContractNo(master.getContractNo());

        ContractStopWatch.stop(watch);
    }

    @Override
    public void execute(EntityProxy<VehicleSaleContractItem> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractItemService.class, "execute");

        this.validate(entityProxy);
        this.autoBuildItemRelation(entityProxy);

        ContractStopWatch.stop(watch);
    }

    private void validate(EntityProxy<VehicleSaleContractItem> entityProxy) {
        logger.debug("校验合同改装信息{}", entityProxy);
        Operation operation = entityProxy.getOperation();
        if (operation == Operation.NONE) {
            return;
        }

        if (operation == Operation.UPDATE) {
            VehicleSaleContractItem item = entityProxy.getEntity();
            VehicleSaleContractItem oriItem = entityProxy.getOriginalEntity();
            if (null == item.getItemCost()) {
                item.setItemCost(BigDecimal.ZERO);
            }
            double oriItemCost = Tools.toDouble(oriItem.getItemCost());
            //当金额和改装项目相同时，跳过sql执行，用来减少反应时间
            if (oriItemCost == item.getItemCost().doubleValue() && StringUtils.equals(item.getItemId(), oriItem.getItemId())) {
                return;
            }

            Integer result = baseDao.findUniqueBySql(ITEM_EXISTS_SQL, item.getSaleContractItemId());
            if (null != result && result > 0) {
                if (oriItemCost != item.getItemCost().doubleValue()) {
                    throw new ServiceException(String.format("改装项目【%s】已建立改装申请单，不能变更项目金额", item.getItemName()));
                }

                if (!StringUtils.equals(item.getItemId(), oriItem.getItemId())) {
                    throw new ServiceException(String.format("已建立改装申请单，不能将改装项目【%s】改成【%s】", oriItem.getItemName(), item.getItemName()));
                }
            }

        } else if (operation == Operation.DELETE) {
            //ADM19040012
            VehicleSaleContractItem oriItem = entityProxy.getOriginalEntity();
            String sql = "SELECT b.item_group_id FROM dbo.vehicle_conversion_detail a\n" +
                    "LEFT JOIN dbo.vehicle_sale_contract_item b ON a.sale_contract_item_id=b.sale_contract_item_id\n" +
                    "WHERE a.sale_contract_item_id =:saleContractItemId AND a.status IN (0,1,2,30,40,50) AND isnull(a.is_auto_relation,0)=0";
            Map<String, Object> params = new HashMap<>(1);
            params.put("saleContractItemId", oriItem.getSaleContractItemId());
            List<Map<String, Object>> result = baseDao.getMapBySQL(sql, params);
            if (result != null && result.size() > 0) {
                throw new ServiceException(String.format("改装项目【%s】已建立改装申请单，不能删除", oriItem.getItemName()));
            }

//            Integer result = baseDao.findUniqueBySql(ITEM_EXISTS_SQL, oriItem.getSaleContractItemId());
//            if (null != result && result > 0) {
//                throw new ServiceException(String.format("改装项目【%s】已建立改装申请单，不能删除", oriItem.getItemName()));
//            }
        }
    }


    private void autoBuildItemRelation(EntityProxy<VehicleSaleContractItem> entityProxy) {
        VehicleSaleContractItem item = entityProxy.getEntity();

        EntityProxy<VehicleSaleContractDetail> detailProxy = entityProxy.getMaster();
        VehicleSaleContractDetail oriContractDetail = detailProxy.getOriginalEntity();
        VehicleSaleContractDetail contractDetail = detailProxy.getEntity();

        if (entityProxy.getOperation() == Operation.DELETE || detailProxy.getOperation() == Operation.DELETE) { //删除改装明细,则取消关联
            removeRelation(item);
        } else if (entityProxy.getOperation() == Operation.CREATE) {
            if (StringUtils.isNotEmpty(contractDetail.getVehicleId())) {
                //新增车辆则创建关联
                addItemRelation(item.getItemId(), contractDetail.getVehicleId(), item.getSaleContractItemId());
            }
        } else {
            //修改前后都没选车跳过
            if (oriContractDetail != null && StringUtils.isEmpty(contractDetail.getVehicleId()) && StringUtils.isEmpty(oriContractDetail.getVehicleId())) {
                return;
            }

            dealRelation(item, contractDetail.getVehicleId());
        }
    }


    //修改关联
    private void dealRelation(VehicleSaleContractItem item, String vehicleId) {
        List<VehicleConversionDetail> list = (List<VehicleConversionDetail>) baseDao.findByHql("from VehicleConversionDetail WHERE status IN (1,2,30,50) "
                + "AND ((vehicleId = ? AND itemId =?) OR saleContractItemId =?)  ORDER BY conversionType ASC", vehicleId, item.getItemId(), item.getSaleContractItemId());
        //先处理删除的情况
        for (VehicleConversionDetail conversionDetail : list) {
            if (StringUtils.equals(conversionDetail.getSaleContractItemId(), item.getSaleContractItemId())) {
                conversionDetail.setIsAutoRelation(null);
                conversionDetail.setSaleContractItemId(null);
            }
        }

        if (!StringUtils.isEmpty(vehicleId)) {
            //按改装类型排序只关联第一条，其他的如果有关联自动清了
            boolean done = false;
            for (VehicleConversionDetail conversionDetail : list) {
                if (StringUtils.equals(conversionDetail.getVehicleId(), vehicleId) && StringUtils.equals(conversionDetail.getItemId(), item.getItemId())) {
                    if (!done) {
                        conversionDetail.setSaleContractItemId(item.getSaleContractItemId());
                        conversionDetail.setIsAutoRelation(true);
                        done = true;
                    } else {
                        conversionDetail.setIsAutoRelation(null);
                        conversionDetail.setSaleContractItemId(null);
                    }
                }
            }
        }
    }

    private void removeRelation(VehicleSaleContractItem item) {
        List<VehicleConversionDetail> list = (List<VehicleConversionDetail>) baseDao.findByHql("from VehicleConversionDetail WHERE status IN (1,2,30,50) " + "AND saleContractItemId =?", item.getSaleContractItemId());
        for (VehicleConversionDetail conversionDetail : list) {
            conversionDetail.setIsAutoRelation(null);
            conversionDetail.setSaleContractItemId(null);
        }
    }

    private void addItemRelation(String itemId, String vehicleId, String saleContractItemId) {
        if (StringUtils.isEmpty(vehicleId)) {
            return;
        }

        List<VehicleConversionDetail> list = (List<VehicleConversionDetail>) baseDao.findByHql("from VehicleConversionDetail WHERE status IN (1,2,30,50) " + "AND vehicleId = ? AND itemId =? ORDER BY conversionType ASC", vehicleId, itemId);
        for (int i = 0; i < list.size(); i++) {
            VehicleConversionDetail conversionDetail = list.get(i);
            if (i == 0) { //按改装类型排序只关联第一条，其他的如果有关联自动清了
                conversionDetail.setSaleContractItemId(saleContractItemId);
                conversionDetail.setIsAutoRelation(true);
            } else {
                conversionDetail.setIsAutoRelation(null);
                conversionDetail.setSaleContractItemId(null);
            }
        }
    }


}
