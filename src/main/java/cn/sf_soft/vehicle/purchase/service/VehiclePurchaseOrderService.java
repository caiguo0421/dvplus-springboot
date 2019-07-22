package cn.sf_soft.vehicle.purchase.service;

import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.support.BaseService;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.purchase.model.VehiclePurchaseOrder;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆采购订单
 *
 * @author caigx
 */
@Service("vehiclePurchaseOrderService")
public class VehiclePurchaseOrderService extends BaseService<VehiclePurchaseOrder> {

    /**
     * 订单编码:生成
     */
    public static final String CODE_RULE_NO = "VPO_NO";

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private SysCodeRulesService sysCodeService;

    private static EntityRelation entityRelation;

    static {
        entityRelation = new EntityRelation(VehiclePurchaseOrder.class, "selfId", VehiclePurchaseOrderService.class);
        entityRelation.addSlave("orderId", VehiclePurchaseOrderConversionService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    /**
     * 保存采购订单
     *
     * @param parameter
     * @return
     */
    public Map<String, List<Map<String, Object>>> savePurchaseOrder(JsonObject parameter) {
        List<EntityProxy<?>> contractProxies = this.save(parameter);
        EntityProxy<VehiclePurchaseOrder> entityProxy = (EntityProxy<VehiclePurchaseOrder>) contractProxies.get(0);
        baseDao.flush();
        return convertReturnData(entityProxy.getEntity().getDocumentNo());
    }

    public Map<String, List<Map<String, Object>>> convertReturnData(String documentNo) {
        Map<String, List<Map<String, Object>>> rtnData = new HashMap<>(2);

        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("val", documentNo);
        List<Map<String, Object>> data = baseDao.getMapBySQL("SELECT * FROM vw_vehicle_purchase_order  WHERE document_no=:val", parmMap);
        rtnData.put("vehicle_purchase_order", data);

        data = baseDao.getMapBySQL("SELECT * FROM vehicle_purchase_order_conversion  WHERE document_no=:val", parmMap);
        rtnData.put("vehicle_purchase_order_conversion", data);

        return rtnData;
    }


    @Override
    public void beforeExecute(EntityProxy<VehiclePurchaseOrder> entityProxy) {
        if (entityProxy.getOperation() == Operation.CREATE) {
            //订单编码
            SysUsers user = HttpSessionStore.getSessionUser();
            VehiclePurchaseOrder order = entityProxy.getEntity();
            String documentNo = sysCodeService.createSysCodeRules(CODE_RULE_NO, user.getDefaulStationId());
            order.setDocumentNo(documentNo);

            //userId,userNo等
            order.setStationId(user.getDefaulStationId());
            order.setUserId(user.getUserId());
            order.setUserNo(user.getUserNo());
            order.setUserName(user.getUserName());
            order.setDepartmentId(user.getDepartment());
            order.setDepartmentNo(user.getDepartmentNo());
            order.setDepartmentName(user.getDepartmentName());

            order.setSubmitStationId(user.getDefaulStationId());
            SysStations submitStation = baseDao.get(SysStations.class, order.getSubmitStationId());
            if (submitStation != null) {
                order.setSubmitStationName(submitStation.getStationName());
            }
            order.setCreator(user.getUserFullName());
            order.setCreateTime(new Timestamp(System.currentTimeMillis()));
            order.setModifier(user.getUserFullName());
            order.setModifyTime(new Timestamp(System.currentTimeMillis()));
        }

        if (entityProxy.getOperation() != Operation.DELETE) {
            VehiclePurchaseOrder order = entityProxy.getEntity();
            validatePurchaseOrder(order);
        }
    }


    private void validatePurchaseOrder(VehiclePurchaseOrder purchaseOrder) {
        if (purchaseOrder.getOrderType() == null) {
            throw new ServiceException("订单类型为空");
        }
        if (StringUtils.isBlank(purchaseOrder.getVnoId())) {
            throw new ServiceException("车型为空");
        }
        if (StringUtils.isBlank(purchaseOrder.getColor())) {
            throw new ServiceException("颜色为空");
        }
        if (purchaseOrder.getExpectDate() == null) {
            throw new ServiceException("期望交期为空");
        }
        if (Tools.toInt(purchaseOrder.getVehicleQuantity()) <= 0) {
            throw new ServiceException("数量不能小于0");
        }
        if (Tools.toBigDecimal(purchaseOrder.getPurchasePrice()).compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException("开票价不能小于0");
        }
        if (OrderType.CONTRACT.getCode() == purchaseOrder.getOrderType()) {
            if (StringUtils.isBlank(purchaseOrder.getSaleContractNo())) {
                throw new ServiceException("直客订单的合同号为空");
            }

            if (StringUtils.isBlank(purchaseOrder.getSaleContractGroupId())) {
                throw new ServiceException("直客订单的销售合同分组ID为空");
            }
        }
    }

    @Override
    public void execute(EntityProxy<VehiclePurchaseOrder> entityProxy) {

    }

    public Map<String, List<Map<String, Object>>> abort(String documentNo, String modifyTime, int abort) {
        List<VehiclePurchaseOrder> orderList = (List<VehiclePurchaseOrder>) baseDao.findByHql("FROM VehiclePurchaseOrder where documentNo = ? ", documentNo);
        if (orderList == null || orderList.size() == 0) {
            throw new ServiceException(String.format("车辆采购订单(%s)无效", documentNo));
        }
        VehiclePurchaseOrder order = orderList.get(0);

        this.validateModifyTime(modifyTime, order.getModifyTime());
        if (abort <= 0) {
            throw new ServiceException("终止数量不能小于0");
        }
        Short status = order.getStatus();
        if (status != Constant.DocumentStatus.AGREED) {
            throw new ServiceException(String.format("车辆采购订单审批状态(%s)异常，不能终止", status));
        }

        Integer vehicleQuantity = order.getVehicleQuantity();   //订单数量
        Integer declareQuantity = null == order.getDeclareQuantity() ? 0 : order.getDeclareQuantity(); //申报数量
        Integer abortQuantity = null == order.getAbortQuantity() ? 0 : order.getAbortQuantity(); //已终止数量
        if (abort > vehicleQuantity - declareQuantity - abortQuantity) {
//            throw new ServiceException("终止数量超过了允许终止数量的范围");
            throw new ServiceException(String.format("终止数量【%d】超过了可终止数量【%d】", abort, vehicleQuantity - declareQuantity - abortQuantity));
        }
        //获取销售合同已配数量
        List<List<Object>> list = baseDao.findBySql("select count(1) from vehicle_stocks a \n" +
                        "join vehicle_sale_contract_detail b \n" +
                        "on a.vehicle_id = b.vehicle_id \n" +
                        "join vehicle_sale_contracts c\n" +
                        "on c.contract_no = b.contract_no\n" +
                        "where a.order_id=?\n" +
                        "and c.status<=50",
                order.getSelfId());
        int number = 0;
        if (null != list && !list.isEmpty()) {
            number = Integer.parseInt(list.get(0).get(0).toString());
        }
        if (abort > vehicleQuantity - declareQuantity - abortQuantity - number) {
            throw new ServiceException(String.format("%d台订单车辆已被销售合同配车，如要终止需先取消配车", number));
//            throw new ServiceException("终止数量超过了允许终止数量的范围");
        }
        //删除VehicleStocks中对应的数量，注意要删除没有对应合同号的记录
        List<VehicleStocks> vehicleStocksList = (List<VehicleStocks>) baseDao.findByHql("FROM VehicleStocks where orderId = ? AND ISNULL(saleContractNo,'')='' ", order.getSelfId());
        for (int i = 0; i < abort; i++) {
            VehicleStocks stocks = vehicleStocksList.get(i);
            baseDao.delete(stocks);
        }

        order.setAbortQuantity(abortQuantity + abort);
        order.setModifyTime(TimestampUitls.getTime());

        //如果 终止数量=采购数量，则将单据状态变为终止
        if (Tools.toInt(order.getAbortQuantity()) == Tools.toInt(order.getVehicleQuantity())) {
            order.setStatus((short) 80);
            order.setInvalidTime(new Timestamp(System.currentTimeMillis()));
        }

        baseDao.flush();
        return this.convertReturnData(documentNo);
    }


    //未售资源
//    private static final String UNSOLD_RESOURCES_SQL = "SELECT area,sale_status,sale_status_meaning,entity_status,entity_status_meaning,count(*)AS qty from(\n" +
//            " SELECT ISNULL(b.option_value,'未知区域') AS area,\n" +
//            " a.sale_status,a.sale_status_meaning,a.entity_status,a.entity_status_meaning\n" +
//            " FROM dbo.vw_vehicle_resources a\n" +
//            " LEFT JOIN dbo.sys_options b ON a.station_id = b.station_id AND b.option_no='DEFAULT_CITY'\n" +
//            " WHERE entity_status IN (10,20,30) --10，20，30表示订单、在途、在库\n" +
//            " AND sale_status=10 --10未售\n" +
//            " AND a.station_id =:stationId AND a.vno_id =:vnoId and a.vehicle_color=:color\n" +
//            " )tmp group by area,sale_status,sale_status_meaning,entity_status,entity_status_meaning";

    private static final String UNSOLD_RESOURCES_SQL = "SELECT a.station_id,a.station_name,--ISNULL(b.option_value,'未知区域') AS area,\n" +
            "\t\t1 can_use_qty,--可用资源\n" +
            "\t\tCASE WHEN entity_status=10 THEN 1 ELSE 0 END AS order_qty,--订单数量\n" +
            "\t\tCASE WHEN entity_status=20 THEN 1 ELSE 0 END AS on_way_qty,--在途数量\n" +
            "\t\tCASE WHEN a.entity_status=30 THEN 1 ELSE 0 END AS stock_qty,--在库数量\n" +
            "\t\t0 AS intention_qty_by,--本月意向数量\n" +
            "\t\t0 AS intention_qty_cy,--次月意向数量\n" +
            "\t\t0 AS intention_qty_ccy,--次次月意向\n" +
            "\t\t0 AS sale_qty_by,--本月销量\n" +
            "\t\t0 AS sale_qty_sy,--上月销量\n" +
            "\t\t0 AS sale_qty_ssy,--上上月销量\n" +
            "\t\t0 AS sale_qty_hby,--环比本月\n" +
            "\t\t0 AS sale_qty_hcy,--环比去年次月销量\n" +
            "\t\t0 AS sale_qty_hccy--环比去年次次月销量\n" +
            "\t\tFROM dbo.vw_vehicle_resources a\n" +
            "\n" +
            "\t\tWHERE a.vno_id=:vnoId AND a.vehicle_color=:color AND a.entity_status IN (10,20,30) AND a.sale_status=10\n" +
            "\t\tAND a.station_id IN (SELECT short_str FROM dbo.fn_GetSplit(@station_id,','))";

    /**
     * 采购订单参考数据
     *
     * @param documentNo
     * @return
     */
    public Map<String, List<Map<String, Object>>> getOrderReference(String documentNo) {
        List<VehiclePurchaseOrder> purchaseOrderList = (List<VehiclePurchaseOrder>) baseDao.findByHql("FROM VehiclePurchaseOrder where documentNo = ? ", documentNo);
        if (purchaseOrderList == null || purchaseOrderList.size() == 0) {
            throw new ServiceException(documentNo + "未找到对应的采购订单");
        }
        SysUsers user = HttpSessionStore.getSessionUser();
        VehiclePurchaseOrder purchaseOrder = purchaseOrderList.get(0);
        //参考数据
        String sql = "{call dbo.pro_statVehicleOrderRefrence(?,?,?,?)}";
        Object[] parms = new Object[]{user.getInstitution().getStationId(), purchaseOrder.getVnoId(), purchaseOrder.getColor(), purchaseOrder.getCreateTime()};
        List<Map<String, Object>> orderReference = baseDao.findProcedure(sql, parms);
//        //未售资源
//        Map<String, Object> unsoldParms = new HashMap<>(4);
//        unsoldParms.put("vnoId", purchaseOrder.getVnoId());
//        unsoldParms.put("color", purchaseOrder.getColor());
//
//        unsoldParms.put("stationId", user.getInstitution().getStationId()); //stationId
//        List<Map<String, Object>> unsoldResources = baseDao.getMapBySQL(UNSOLD_RESOURCES_SQL, unsoldParms);

        Map<String, List<Map<String, Object>>> rtnData = convertReturnData(purchaseOrder.getDocumentNo());
        rtnData.put("orderReference", orderReference);
//        rtnData.put("unsoldResources", unsoldResources);
        return rtnData;
    }

    protected VehiclePurchaseOrder newOrder(String documentNo) {
        SysUsers user = HttpSessionStore.getSessionUser();
        VehiclePurchaseOrder order = new VehiclePurchaseOrder();
        order.setSelfId(Tools.newGuid());
        if (StringUtils.isEmpty(documentNo)) {
            documentNo = sysCodeService.createSysCodeRules(CODE_RULE_NO, user.getDefaulStationId());
            order.setDocumentNo(documentNo);
        } else {
            order.setDocumentNo(documentNo);
        }
        //userId,userNo等
        order.setStationId(user.getDefaulStationId());
        order.setUserId(user.getUserId());
        order.setUserNo(user.getUserNo());
        order.setUserName(user.getUserName());
        order.setDepartmentId(user.getDepartment());
        order.setDepartmentNo(user.getDepartmentNo());
        order.setDepartmentName(user.getDepartmentName());

        order.setSubmitStationId(user.getDefaulStationId());
        SysStations submitStation = baseDao.get(SysStations.class, order.getSubmitStationId());
        if (submitStation != null) {
            order.setSubmitStationName(submitStation.getStationName());
        }
        order.setCreator(user.getUserFullName());
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setModifier(user.getUserFullName());
        order.setModifyTime(new Timestamp(System.currentTimeMillis()));
        return order;
    }
}

/**
 * 订单类型
 */
enum OrderType {

    CONTRACT((byte) 0, "直客订单"), STOCK((byte) 10, "备货订单");


    // 返回码
    private final byte code;
    // 返回消息
    private final String message;

    public String getMessage() {
        return message;
    }

    public byte getCode() {
        return code;
    }

    OrderType(byte code, String message) {
        this.code = code;
        this.message = message;
    }
}
