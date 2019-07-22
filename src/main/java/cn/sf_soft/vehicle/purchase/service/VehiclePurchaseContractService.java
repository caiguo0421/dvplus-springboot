package cn.sf_soft.vehicle.purchase.service;

import cn.sf_soft.basedata.model.SysFlags;
import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.VehicleConversionDetail;
import cn.sf_soft.office.approval.service.impl.VehiclePurchaseOrderApproveService;
import cn.sf_soft.support.BaseService;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.contract.model.VwVehicleType;
import cn.sf_soft.vehicle.df.model.VehicleDfSapDelivery;
import cn.sf_soft.vehicle.purchase.model.VehiclePurchaseContract;
import cn.sf_soft.vehicle.purchase.model.VehiclePurchaseContractDetail;
import cn.sf_soft.vehicle.purchase.model.VehiclePurchaseOrder;
import cn.sf_soft.vehicle.purchase.model.VehiclePurchaseOrderConversion;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/28 10:52
 * @Description: 车辆采购合同管理
 */
@Service
public class VehiclePurchaseContractService extends BaseService<VehiclePurchaseContract> {

    public static final String CODE_RULE_NO = "VPC_NO";

    @Autowired
    private SysCodeRulesService sysCodeService;

    @Autowired
    private VehiclePurchaseOrderService vehiclePurchaseOrderService;

    @Autowired
    private VehiclePurchaseOrderApproveService vehiclePurchaseOrderApproveService;

    @Autowired
    private BaseDao baseDao;

    private static EntityRelation entityRelation;

    static {
        entityRelation = new EntityRelation(VehiclePurchaseContract.class, "contractId", VehiclePurchaseContractService.class);
        entityRelation.addSlave("contractId", VehiclePurchaseContractDetailService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehiclePurchaseContract> entityProxy) {
        if(entityProxy.getOperation() == Operation.DELETE){
            throw new ServiceException("车辆采购合同暂不支持删除操作");
        }
        this.validate(entityProxy);
        if (entityProxy.getOperation() == Operation.CREATE) {
            //合同号
            SysUsers user = HttpSessionStore.getSessionUser();
            VehiclePurchaseContract contract = entityProxy.getEntity();
            String contractNo = null;
            try {
                contractNo = sysCodeService.createSysCodeRules(CODE_RULE_NO, user.getDefaulStationId());
            }catch (Exception ex){
                throw new ServiceException("车辆采购合同号生成失败", ex);
            }
            contract.setContractNo(contractNo);
            //设置创建人和创建时间
            contract.setCreator(user.getUserFullName());
            contract.setCreateTime(TimestampUitls.getTime());
            //未确认
            contract.setStatus((byte)0);
            contract.setModifyTime(TimestampUitls.getTime());
        } else if(entityProxy.getOperation() == Operation.UPDATE){
            Byte status = entityProxy.getEntity().getStatus();
            if(null != status && status == 1){
                throw new ServiceException(String.format("车辆采购合同(%s)已确认，不能修改",entityProxy.getEntity().getContractNo()));
            }
            SysUsers user = HttpSessionStore.getSessionUser();
            entityProxy.getEntity().setModifier(user.getUserFullName());
            entityProxy.getEntity().setModifyTime(TimestampUitls.getTime());
        }
    }

    @Override
    public void execute(EntityProxy<VehiclePurchaseContract> entityProxy) {

    }

    /**
     * 保存车辆采购合同
     * @param parameter
     * @return
     */
    public Map<String, List<Object>> savePurchaseContract(final JsonObject parameter) {
        List<EntityProxy<?>> contractProxies = super.save(parameter);
        baseDao.flush();
        VehiclePurchaseContract contract = (VehiclePurchaseContract)contractProxies.get(0).getEntity();
        return convertReturnData(contract.getContractNo());
    }

    /**
     * 确认
     * @param contractId 车辆采购合同ID
     * @param modifyTime 操作时间
     * @return
     */
    public Map<String, List<Object>> confirm(String contractId, String modifyTime){
        VehiclePurchaseContract contract = baseDao.get(VehiclePurchaseContract.class, contractId);
        if(null == contract){
            throw new ServiceException(String.format("未找到车辆采购合同(%s)", contractId));
        }
        this.validateModifyTime(modifyTime, contract);
        Byte status = contract.getStatus();
        if(status != ConfirmStatus.UNCONFIRMED.getCode()){
            throw new ServiceException(String.format("车辆采购合同(%s)的当前状态(%s)不允许确认", contract.getContractNo(), status));
        }
        contract.setStatus(ConfirmStatus.CONFIRMED.getCode());
        contract.setAffirmedQuantity(contract.getDeclareQuantity());
        SysUsers user = HttpSessionStore.getSessionUser();
        contract.setConfirmPerson(user.getUserFullName());
        contract.setConfirmTime(TimestampUitls.getTime());
        contract.setModifier(user.getUserFullName());
        contract.setModifyTime(contract.getConfirmTime());
        VehiclePurchaseOrder order = this.autoCreateOrder(contract);
        this.syncVehicleInfo(contract, Op.CONFIRM);
        /*if(null != order){
            //东风订单，对于自动生成的采购订单申报数量与车辆数量相等
            order.setDeclareQuantity(order.getVehicleQuantity());
        }*/
        baseDao.flush();
        return convertReturnData(contractId);
    }

    /**
     * 反确认
     * @param contractId 车辆采购合同ID
     * @param modifyTime 操作时间
     * @return
     */
    public Map<String, List<Object>> unConfirm(String contractId, String modifyTime){
        VehiclePurchaseContract contract = baseDao.get(VehiclePurchaseContract.class, contractId);
        if(null == contract){
            throw new ServiceException(String.format("未找到车辆采购合同(%s)", contractId));
        }
        this.validateModifyTime(modifyTime, contract);
        Byte status = contract.getStatus();
        if(status != ConfirmStatus.CONFIRMED.getCode()){
            throw new ServiceException(String.format("车辆采购合同(%s)的当前状态(%s)不允许反确认", contract.getContractNo(), status));
        }
        contract.setStatus(ConfirmStatus.UNCONFIRMED.getCode());
        contract.setAffirmedQuantity(0);
        SysUsers user = HttpSessionStore.getSessionUser();
        contract.setConfirmPerson(null);
        contract.setConfirmTime(null);
        contract.setModifier(user.getUserFullName());
        contract.setModifyTime(TimestampUitls.getTime());
        this.validate(contract, false);
        this.syncVehicleInfo(contract, Op.UN_CONFIRM);
        return convertReturnData(contractId);
    }

    /**
     * 作废
     * @param contractId 合同ID
     * @param modifyTime 修改时间
     * @return
     */
    public Map<String, List<Object>> forbid(String contractId, String modifyTime){
        VehiclePurchaseContract contract = baseDao.get(VehiclePurchaseContract.class, contractId);
        if(null == contract){
            throw new ServiceException(String.format("未找到车辆采购合同(%s)", contractId));
        }
        this.validateModifyTime(modifyTime, contract);
        Byte status = contract.getStatus();
        ConfirmStatus confirmStatus = ConfirmStatus.valueOf(status);
        if(confirmStatus == ConfirmStatus.CONFIRMED){
            throw new ServiceException(String.format("车辆采购合同(%s)已确认，不允许作废",contract.getContractNo()));
        }
        if(confirmStatus == ConfirmStatus.FORBID){
            throw new ServiceException(String.format("车辆采购合同(%)已作废，不能重复操作",contract.getContractNo()));
        }
        contract.setStatus(ConfirmStatus.FORBID.getCode());
        contract.setModifier(HttpSessionStore.getSessionUser().getUserFullName());
        contract.setModifyTime(TimestampUitls.getTime());
        this.validate(contract, false);
        this.syncVehicleInfo(contract, Op.FORBID);
        baseDao.flush();
        return convertReturnData(contractId);
    }

    private Map<String, List<Object>> convertReturnData(String contractId) {
        Map<String, List<Object>> rtnData = new HashMap<>(2);
        Map<String, Object> parmMap = new HashMap<>();
        parmMap.put("val", contractId);
        List<Object> data = baseDao.listInSql("SELECT * FROM vehicle_purchase_contract WHERE contract_id=:val", parmMap).getData();
        rtnData.put("vehicle_purchase_contract", data);
        data = baseDao.listInSql("SELECT * FROM vehicle_purchase_contract_detail WHERE contract_id=:val", parmMap).getData();
        rtnData.put("vehicle_purchase_contract_detail", data);

        return rtnData;
    }

    private void validate(EntityProxy<VehiclePurchaseContract> entityProxy){
        if(entityProxy.getOperation() == Operation.UPDATE || entityProxy.getOperation() == Operation.UPDATE){
            this.validateModifyTime(entityProxy.getEntity().getModifyTime(), entityProxy.getOriginalEntity().getModifyTime());
        }
        if(entityProxy.getOperation() != Operation.DELETE){
            VehiclePurchaseContract entity = entityProxy.getEntity();
            if(null == entity.getStatus()){
                throw new ServiceException("状态不能为空");
            }
            if(StringUtils.isEmpty(entity.getSupplierId())){
                throw new ServiceException("供应商不能为空");
            }
            if(StringUtils.isEmpty(entity.getSupplierNo())){
                throw new ServiceException("供应商编码不能为空");
            }
            if(StringUtils.isEmpty(entity.getSupplierName())){
                throw new ServiceException("供应商名称不能为空");
            }
            if(StringUtils.isEmpty(entity.getVnoId())){
                throw new ServiceException("车型不能为空");
            }
            if(StringUtils.isEmpty(entity.getColor())){
                throw new ServiceException("车辆颜色不能为空");
            }
            if(null == entity.getPurchasePrice()){
                throw new ServiceException("开票价不能为空");
            }
            if(null == entity.getContractAmount()){
                throw new ServiceException("合同金额不能为空");
            }
            if(null == entity.getDeclareQuantity()){
                throw new ServiceException("申报数量不能为空");
            }
            if(null == entity.getStatus()){
                throw new ServiceException("付款类型");
            }
            if(StringUtils.isEmpty(entity.getApplicant())){
                throw new ServiceException("申请人不能为空");
            }
            if(null == entity.getApplyTime()){
                throw new ServiceException("申请时间不能为空");
            }
        }else if(entityProxy.getOperation() == Operation.UPDATE){
            VehiclePurchaseContract entity = entityProxy.getEntity();
            Byte status = entity.getStatus();
            if(null == status){
                throw new ServiceException("车辆采购合同状态不能为空");
            }
            if(ConfirmStatus.UNCONFIRMED.getCode() != status && ConfirmStatus.CONFIRMED.getCode() != status){
                throw new ServiceException(String.format("车辆采购合同状态(%s)无效", status));
            }
            if(status == ConfirmStatus.CONFIRMED.getCode()){
                throw new ServiceException("车辆采购合同已确认，不允许修改");
            }
        }

    }

    private void validateModifyTime(String modifyTime, VehiclePurchaseContract contract){
        Timestamp curModifyTime = null;
        try {
            curModifyTime = new Timestamp(TimestampUitls.formatDate(modifyTime).getTime());
        }catch (Exception ex){
            throw new ServiceException(String.format("modifyTime(%s)格式错误", modifyTime));
        }
        if(null != curModifyTime){
            this.validateModifyTime(contract.getModifyTime(), curModifyTime);
        }
    }

    /**
     * 确认时发现vehicle_purchase_contract_detail为空
     * 或vehicle_purchase_contract_detail的declare_quantity总数小于主单上的declare_quantity
     * （因为采购合同主单可能对应多个订单，可能有部分系统已自动匹配）时，
     * 自动生成一个已审批同意的采购订单并与之关联起来。
     */
    private VehiclePurchaseOrder autoCreateOrder(VehiclePurchaseContract contract){
        List<?> list = baseDao.findByHql("select sum(declareQuantity) as declareQuantity from VehiclePurchaseContractDetail where contractId=?", contract.getContractId());
        int declareQuantity = null == list || list.isEmpty() || null ==list.get(0) ? 0 : Integer.parseInt(list.get(0).toString());
        if(contract.getDeclareQuantity() > declareQuantity){
            int quantity = contract.getDeclareQuantity() - declareQuantity;
            Map<String, Object> parm = new HashMap<>(1);
            parm.put("purchaseOrderNo", contract.getDfPurchaseOrderNo());
            List<Map<String, Object>> vehicleModelList = baseDao.getMapBySQL("SELECT b.self_id AS vno_id,\n" +
                    "\tISNULL(NULLIF(a.color,''),a.color_name) AS color,a.expect_date as expect_date,\t\n" +
                    "\tb.price_purchase as price_purchase, b.engine as engine\t\n" +
                    "\tFROM dbo.vehicle_DF_purchase_order a \n" +
                    "\tLEFT JOIN dbo.base_vehicle_model_catalog b ON a.vehicle_vno=b.product_model\n" +
                    "WHERE a.purchase_order_no=:purchaseOrderNo", parm);

            if(null == vehicleModelList || vehicleModelList.isEmpty()){
                throw new ServiceException(String.format("车辆采购合同(%s)存在未维护的车型", contract.getContractNo()));
            }
            VehiclePurchaseOrder order = vehiclePurchaseOrderService.newOrder(contract.getDfPurchaseOrderNo());
            order.setStationId(contract.getStationId());
            order.setDeclareQuantity(0);
            order.setVehicleQuantity(quantity);
            order.setOrderType((byte)10);
            SysUsers user = HttpSessionStore.getSessionUser();
            order.setSubmitStationId(user.getDefaulStationId());
            order.setSubmitStationName(user.getStationName());
            order.setSubmitTime(order.getCreateTime());
            order.setApproverNo(user.getUserNo());
            order.setApproverName(user.getUserName());
            order.setApproveTime(order.getCreateTime());
            order.setStatus(Constant.DocumentStatus.AGREED);


            String vnoId = (String)vehicleModelList.get(0).get("vno_id");
            String color = null;
            if(null != vehicleModelList.get(0).get("color")){
                color = (String)vehicleModelList.get(0).get("color");
            }
            Timestamp expectDate = null;
            if(null != vehicleModelList.get(0).get("expect_date")){
                expectDate = (Timestamp)vehicleModelList.get(0).get("expect_date");
            }
            Double pricePurchase = 0d;
            if(null != vehicleModelList.get(0).get("price_purchase")){
                pricePurchase = (Double)vehicleModelList.get(0).get("price_purchase");
            }
            String engine = null;
            if(null != vehicleModelList.get(0).get("engine")){
                engine = (String)vehicleModelList.get(0).get("engine");
            }

            order.setVnoId(vnoId);
            order.setColor(color);
            order.setExpectDate(expectDate);
            order.setPurchasePrice(Tools.toBigDecimal(pricePurchase));
            order.setEngine(engine);
            baseDao.save(order);

            //创建采购合同明细
            VehiclePurchaseContractDetail detail = new VehiclePurchaseContractDetail();
            detail.setSelfId(Tools.newGuid());
            detail.setContractId(contract.getContractId());
            detail.setContractNo(contract.getContractNo());
            detail.setDeclareQuantity(quantity);
            detail.setOrderId(order.getSelfId());
            baseDao.save(detail);

            List<VehicleDfSapDelivery> sapDeliveries = this.findDeliveryByDfPurchaseOrderNo(contract.getDfPurchaseOrderNo());
            if(null != sapDeliveries && !sapDeliveries.isEmpty()){
                int index = 1;
                for(VehicleDfSapDelivery vehicleDfSapDelivery : sapDeliveries){
                    VehicleStocks vehicleStocks = null;
                    //如果提货单的vehicleId不为空说明是历史数据，历史数据不用生成资源，只做关联
                    if(StringUtils.isEmpty(vehicleDfSapDelivery.getVehicleId())){
                        //生成资源
                        vehicleStocks = generateVehicleStock(order, vehicleDfSapDelivery);
                    }else{
                        vehicleStocks = baseDao.get(VehicleStocks.class, vehicleDfSapDelivery.getVehicleId());
                        if(null == vehicleStocks) {
                            throw new ServiceException(String.format("未找到指定的车辆(%s)", vehicleDfSapDelivery.getVehicleId()));
                        }else{
                            vehicleStocks.setOrderId(order.getSelfId());
                        }
                        vehicleDfSapDelivery.setContractDetailId(detail.getSelfId());
                    }
                    String resourcesNo = this.getResourcesNo(order.getDocumentNo(), index);
                    index++;
                    vehicleStocks.setResourcesNo(resourcesNo);
                }
            }
            baseDao.flush();
            return order;
        }
        return null;
    }

    /*private int getMaxResourcesNoOfIndex(String dfPurchaseOrderNo){
        List<List<Object>> maxResourcesNo = baseDao.findBySql("select top 1 resources_no from vehicle_stocks where resources_no like '$"+dfPurchaseOrderNo+"_%' order by resources_no desc",null);
        if(null == maxResourcesNo || maxResourcesNo.isEmpty() || null == maxResourcesNo.get(0).get(0)){
            return 0;
        }
        String resourcesNo = maxResourcesNo.get(0).get(0).toString();
        if(StringUtils.isBlank(resourcesNo)){
            return 0;
        }
        String[] array = resourcesNo.split("_");
        if(array.length == 2){
            String index = array[1];
            return Integer.parseInt(index);
        }
        return 0;
    }*/

    private VehicleStocks generateVehicleStock(VehiclePurchaseOrder purchaseOrder, VehicleDfSapDelivery vehicleDfSapDelivery){
        VehicleStocks stocks = new VehicleStocks();
        stocks.setVehicleId(UUID.randomUUID().toString());
        //生成VIN时赋值$前缀+订单编码+流水（如"$IVPO18090002_1）
        //stocks.setVehicleVin(String.format("$%s_%s", purchaseOrder.getDocumentNo(), (i + 1)));
        stocks.setVehicleVin(vehicleDfSapDelivery.getUnderpanNo());
        stocks.setOrderId(purchaseOrder.getSelfId());//采购订单的ID
        stocks.setStationId(purchaseOrder.getStationId());
        stocks.setSaleContractCode(null);
        stocks.setSaleContractCode(null); //配车后回填

        stocks.setVnoId(purchaseOrder.getVnoId());
        VwVehicleType vehicleModel = baseDao.get(VwVehicleType.class, purchaseOrder.getVnoId());
        if (vehicleModel != null) {
            stocks.setVehicleSalesCode(vehicleModel.getProductModel());
            stocks.setVehicleVno(vehicleModel.getProductModel());
            stocks.setVehicleName(vehicleModel.getVehicleName());
            stocks.setVehicleStrain(vehicleModel.getVehicleStrain());
        }

        stocks.setVehicleColor(purchaseOrder.getColor());
        stocks.setVehicleQuantity(1); //数量默认为1
        baseDao.save(stocks);
        return stocks;
    }


    private String getResourcesNo(String dfPurchaseOrderNo, int index){
        return "$" + dfPurchaseOrderNo + "_" + index;
    }

    private List<VehicleDfSapDelivery> findDeliveryByDfPurchaseOrderNo(String dfPurchaseOrderNo){
        List<List<Object>> deliveryArray = baseDao.findBySql("SELECT a.self_id \n" +
                "\t\tFROM dbo.vehicle_DF_sap_delivery a\n" +
                "\t\tINNER JOIN dbo.vehicle_DF_sap_order b ON b.sap_order_no = a.sap_order_no\n" +
                "\t\tINNER JOIN dbo.vehicle_DF_sap_contract c ON b.sap_contract_no=c.contract_no\n" +
                "\t\tINNER JOIN dbo.vehicle_DF_purchase_order d ON d.purchase_order_no = c.purchase_order_no\n" +
                "\t\tWHERE a.underpan_no NOT LIKE 'DEL_%' and c.refuse_reason='有效'\n" +
                "\t\tAND d.purchase_order_no=?", dfPurchaseOrderNo);
        List<VehicleDfSapDelivery> sapDeliveries = new ArrayList<>();
        if (null != deliveryArray && !deliveryArray.isEmpty()) {
            for (List<Object> delivery : deliveryArray) {
                VehicleDfSapDelivery vehicleDfSapDelivery = baseDao.get(VehicleDfSapDelivery.class, delivery.get(0).toString());
                sapDeliveries.add(vehicleDfSapDelivery);
            }
        }
        return sapDeliveries;
    }

    /**
     * 同步车辆信息
     * @param contract 车辆采购合同
     * @param op 操作
     */
    private void syncVehicleInfo(VehiclePurchaseContract contract, Op op){
        //采购订单编码
        String dfPurchaseOrderNo = contract.getDfPurchaseOrderNo();
        List<VehiclePurchaseContractDetail> details = (List<VehiclePurchaseContractDetail>)baseDao.findByHql("from VehiclePurchaseContractDetail where contractId=?", contract.getContractId());
        if(StringUtils.isNotEmpty(dfPurchaseOrderNo)) {  //该采购合同属于东风订单
            String sapOrderNo = contract.getSapOrderNo();   //SAP订单号
            //获取东风提货单信息
            List<VehicleDfSapDelivery> sapDeliveries = this.findDeliveryByDfPurchaseOrderNo(dfPurchaseOrderNo);
            for (VehiclePurchaseContractDetail detail : details) {
                String orderId = detail.getOrderId();
                VehiclePurchaseOrder order = baseDao.get(VehiclePurchaseOrder.class, orderId);
                if (null == order) {
                    throw new ServiceException(String.format("车辆采购订单(标识：%s)不存在", orderId));
                }
                if(null != sapDeliveries && !sapDeliveries.isEmpty()) {
                    logger.debug(String.format("没有找到提货单(%s)数据", dfPurchaseOrderNo));
                    //获取采购订单所有车辆
                    List<List<Object>> allList = baseDao.findBySql("select a.vehicle_id as vehicle_id from vehicle_stocks a where a.order_id=?", order.getSelfId());
                    List<String> allVehicle = new ArrayList<>();
                    if (null != allList && !allList.isEmpty()) {
                        for (List<Object> vehicle : allList) {
                            allVehicle.add(vehicle.get(0).toString());
                        }
                    }
                    //获取采购订单被销售合同已配车辆
                    List<List<Object>> list = baseDao.findBySql("select a.vehicle_id as vehicle_id from vehicle_stocks a \n" +
                                    "join vehicle_sale_contract_detail b \n" +
                                    "on a.vehicle_id = b.vehicle_id \n" +
                                    "join vehicle_sale_contracts c\n" +
                                    "on c.contract_no = b.contract_no\n" +
                                    "where a.order_id=?\n" +
                                    "and c.status<=50",
                            order.getSelfId());
                    List<String> allocatedVehicle = new ArrayList<>();
                    if (null != list && !list.isEmpty()) {
                        for (List<Object> vehicle : list) {
                            allocatedVehicle.add(vehicle.get(0).toString());
                        }
                    }
                    List<String> cacheIdList = new ArrayList<String>();
                    List<String> ignoreVehicleIds = new ArrayList<>();
                    if (op == Op.CONFIRM) {
                        //确认时，优先匹配采购订单下的资源被销售合同配车
                        syncDfSapDeliverties(op, sapDeliveries, contract, detail, allocatedVehicle, ignoreVehicleIds);
                        //再同步其他
                        syncDfSapDeliverties(op, sapDeliveries, contract, detail, allVehicle, ignoreVehicleIds);

                    } else {
                        //反确认，先同步未配车的
                        List<String> untreatedVehicle = new ArrayList<>();
                        for (String vehicleId : allVehicle) {
                            if (!allocatedVehicle.contains(vehicleId)) {
                                untreatedVehicle.add(vehicleId);
                            }
                        }

                        syncDfSapDeliverties(op, sapDeliveries, contract, detail, untreatedVehicle, ignoreVehicleIds);
                        //同步采购订单下的资源被销售合同配车
                        syncDfSapDeliverties(op, sapDeliveries, contract, detail, allocatedVehicle, ignoreVehicleIds);

                    }
                }
                syncVehicleOrder(contract, detail, op, true);
            }
        }else{  //非东风订单，创建提货单
            if(op == Op.CONFIRM) {
                for (VehiclePurchaseContractDetail detail : details) {
                    String orderId = detail.getOrderId();    //订单ID
                    VehiclePurchaseOrder order = baseDao.get(VehiclePurchaseOrder.class, orderId);
                    Short status = order.getStatus();
                    if(null == status || Constant.DocumentStatus.AGREED != status){
                        throw new ServiceException(String.format("操作失败，车辆采购订单(%s)状态(%s)异常",order.getDocumentNo(), status));
                    }
                    List<List<Object>> vehicleIds = baseDao.findBySql("SELECT stock.vehicle_id as vehicleId\n" +
                            "FROM vehicle_stocks stock\n" +
                            "WHERE stock.order_id = ?\n" +
                            "\tAND NOT EXISTS (\n" +
                            "\t\tSELECT 1\n" +
                            "\t\tFROM vehicle_DF_sap_delivery\n" +
                            "\t\tWHERE vehicle_id = stock.vehicle_id\n" +
                            "\t)", orderId);
                    if (null == vehicleIds || vehicleIds.isEmpty() || vehicleIds.get(0).isEmpty()) {
                        throw new ServiceException("操作失败，库存中未找到可同步的车辆");
                    }
                    int quantity = vehicleIds.size();
                    if (detail.getDeclareQuantity() > quantity) {
                        throw new ServiceException(String.format("操作失败，同步车辆信息时，库存中可同步的车辆数量(%s)小于当前确认数量", quantity, detail.getDeclareQuantity()));
                    }

                    for (int i = 0; i < detail.getDeclareQuantity(); i++) {
                        VehicleDfSapDelivery sapDelivery = new VehicleDfSapDelivery();
                        sapDelivery.setSelfId(Tools.newGuid());
                        sapDelivery.setVehicleId(vehicleIds.get(i).get(0).toString());
                        sapDelivery.setQuantity(1);
                        sapDelivery.setCreateDate(TimestampUitls.getTime());
                        sapDelivery.setComfirmStatus(ConfirmStatus.UNCONFIRMED.getCode());  //设置为未确认
                        sapDelivery.setContractDetailId(detail.getSelfId());
                        //设置开票价
                        sapDelivery.setInvoiceAmount(contract.getPurchasePrice());
                        baseDao.save(sapDelivery);
                        List<VehiclePurchaseOrderConversion> conversions = (List<VehiclePurchaseOrderConversion>) baseDao.findByHql("from VehiclePurchaseOrderConversion where orderId=?", detail.getOrderId());
                        if (null != conversions && !conversions.isEmpty()) {
                            for (VehiclePurchaseOrderConversion conversion : conversions) {
                                VehicleConversionDetail item = new VehicleConversionDetail();
                                item.setConversionDetailId(Tools.newGuid());
                                item.setVocdId(sapDelivery.getSelfId());
                                item.setItemId(conversion.getItemId());
                                item.setItemNo(conversion.getItemNo());
                                item.setItemName(conversion.getItemName());
                                item.setItemCost(conversion.getItemCost());
                                item.setItemCostOri(conversion.getItemCost());
                                item.setSupplierId(conversion.getSupplierId());
                                item.setSupplierNo(conversion.getSupplierNo());
                                item.setSupplierName(conversion.getSupplierName());
                                item.setStatus(ConversionStatus.UNCONFIRMED.getCode());
                                item.setConversionType((short)0); //入库前
                                item.setVehicleId(sapDelivery.getVehicleId());
                                VehicleStocks vehicleStocks = baseDao.get(VehicleStocks.class, sapDelivery.getVehicleId());
                                item.setVehicleVin(vehicleStocks.getUnderpanNo());  //入库前，VIN使用底盘号
                                item.setPaymentStatus((short)0);
                                item.setVaryFlag(false);
                                baseDao.save(item);
                            }
                        }
                    }

                    syncVehicleOrder(contract, detail, op, false);
                }
            }else{
                for (VehiclePurchaseContractDetail detail : details) {
                    List<VehicleDfSapDelivery> vehicleDfSapDeliverys = (List<VehicleDfSapDelivery>)baseDao.findByHql("from VehicleDfSapDelivery where contractDetailId=?", detail.getSelfId());
                    if(null != vehicleDfSapDeliverys && !vehicleDfSapDeliverys.isEmpty()){
                        for(VehicleDfSapDelivery vehicleDfSapDelivery : vehicleDfSapDeliverys){
                            Byte status = vehicleDfSapDelivery.getComfirmStatus();
                            ConfirmStatus confirmStatus = ConfirmStatus.valueOf(status);
                            if(confirmStatus == ConfirmStatus.CONFIRMED){
                                throw new ServiceException(String.format("操作失败，非东风订单的提货单(SAP订单号：%s)已确认", vehicleDfSapDelivery.getSapOrderNo()));
                            }
                            baseDao.delete(vehicleDfSapDelivery);
                            baseDao.getCurrentSession().createQuery("delete from cn.sf_soft.office.approval.model.VehicleConversionDetail where vocdId='"+vehicleDfSapDelivery.getSelfId()+"'").executeUpdate();
                        }
                    }
                    syncVehicleOrder(contract, detail, op, false);
                }
            }

        }
    }

    private void syncDfSapDeliverties(Op op, List<VehicleDfSapDelivery> sapDeliveries, VehiclePurchaseContract contract, VehiclePurchaseContractDetail detail, List<String> vehicleIds, List<String> ignoreVehicleIds){
        if(op == Op.CONFIRM) {
            List<String> cacheIds = new ArrayList<>();
            for (String vehicleId : vehicleIds) {
                if(!ignoreVehicleIds.contains(vehicleId)) {
                    for (VehicleDfSapDelivery sapDelivery : sapDeliveries) {
                        if (StringUtils.isEmpty(sapDelivery.getContractDetailId()) &&
                                StringUtils.isEmpty(sapDelivery.getVehicleId())) {
                            ignoreVehicleIds.add(vehicleId);
                            syncDfSapDelivery(op, sapDelivery, contract, vehicleId, detail.getSelfId());
                            break;
                        }
                    }
                }
            }
        }else{
            for (String vehicleId : vehicleIds) {
                if(!ignoreVehicleIds.contains(vehicleId)) {
                    for (VehicleDfSapDelivery sapDelivery : sapDeliveries) {
                        if (StringUtils.isNotEmpty(sapDelivery.getContractDetailId()) &&
                                StringUtils.isNotEmpty(sapDelivery.getVehicleId()) &&
                                StringUtils.equals(vehicleId, sapDelivery.getVehicleId())) {
                            Byte status = sapDelivery.getComfirmStatus();
                            ConfirmStatus confirmStatus = ConfirmStatus.valueOf(status);
                            if (confirmStatus == ConfirmStatus.CONFIRMED) {
                                throw new ServiceException(String.format("操作失败，东风订单的提货单(SAP订单号：%s)已确认", sapDelivery.getSapOrderNo()));
                            }
                            ignoreVehicleIds.add(vehicleId);
                            syncDfSapDelivery(op, sapDelivery, contract, vehicleId, detail.getSelfId());
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param op
     * @param sapDelivery
     * @param contract
     * @param vehicleId
     * @param detailId
     */
    private void syncDfSapDelivery(Op op, VehicleDfSapDelivery sapDelivery, VehiclePurchaseContract contract, String vehicleId, String detailId){
        if(op == Op.CONFIRM){
            sapDelivery.setVehicleId(vehicleId);
            if (null == sapDelivery.getInvoiceAmount() || sapDelivery.getInvoiceAmount().compareTo(BigDecimal.ZERO) == 0) {
                sapDelivery.setInvoiceAmount(contract.getPurchasePrice()); //设置开票价格
            }
            if (StringUtils.isEmpty(sapDelivery.getInvoiceType())) {
                SysFlags sysFlags = baseDao.getSysFlagsByFieldNoAndCode("vi_pay_type", Short.parseShort(contract.getPayType().toString()));
                if (null != sysFlags) {
                    sapDelivery.setInvoiceType(sysFlags.getMeaning());
                }
            }
            sapDelivery.setContractDetailId(detailId);
            //同步底盘号
            Query query = baseDao.getCurrentSession().createQuery("update VehicleStocks set underpanNo=? where vehicleId=?");
            query.setParameter(0, sapDelivery.getUnderpanNo());
            query.setParameter(1, vehicleId);
            query.executeUpdate();
        }else{
            sapDelivery.setVehicleId(null);
            sapDelivery.setInvoiceAmount(null);
            sapDelivery.setInvoiceType(null);
            sapDelivery.setContractDetailId(null);
            //同步底盘号
            Query query = baseDao.getCurrentSession().createQuery("update VehicleStocks set underpanNo=? where vehicleId=?");
            query.setParameter(0, null);
            query.setParameter(1, vehicleId);
            query.executeUpdate();
        }

    }

    /*private void syncVehicleInfo1(VehiclePurchaseContract contract, boolean isConfirm){
        //采购订单编码
        String dfPurchaseOrderNo = contract.getDfPurchaseOrderNo();
        List<VehiclePurchaseContractDetail> details = (List<VehiclePurchaseContractDetail>)baseDao.findByHql("from VehiclePurchaseContractDetail where contractId=?", contract.getContractId());
        if(StringUtils.isNotEmpty(dfPurchaseOrderNo)){  //该采购合同属于东风订单
            String sapOrderNo = contract.getSapOrderNo();   //SAP订单号
            if(StringUtils.isNotEmpty(sapOrderNo)){
                //获取东风提货单信息
                List<VehicleDfSapDelivery> sapDeliveries =
                        (List<VehicleDfSapDelivery>)baseDao.findByHql("from VehicleDfSapDelivery where sapOrderNo=?", sapOrderNo);
                if(null != sapDeliveries && !sapDeliveries.isEmpty()) { //已有东风提货单信息
                    //优先匹配采购订单下的资源被销售合同配车，同时匹配更新车辆资源表
                    for(VehiclePurchaseContractDetail detail : details) {
                        String orderId = detail.getOrderId();
                        VehiclePurchaseOrder order = baseDao.get(VehiclePurchaseOrder.class,orderId);
                        if(null == order){
                            throw new ServiceException(String.format("车辆采购订单(标识：%s)不存在", orderId));
                        }
                        //申报数量
                        int declareQuantity = detail.getDeclareQuantity();
                        int caseSaleQuantity = 0;   //销售合同匹配更新数量
                        int caseStockQuantity = 0;  //库存匹配数量
                        //根据订单号获取车辆资源信息列表


                        String saleContractNo = order.getSaleContractNo();
                        String saleContractGroupId = order.getSaleContractGroupId();
                        //如果订单已被销售合同配车则更新销售合同
                        if (StringUtils.isNotEmpty(saleContractNo) && StringUtils.isNotEmpty(saleContractGroupId)) {
                            List<VehicleSaleContractDetail> contractDetails =
                                    (List<VehicleSaleContractDetail>) baseDao.findByHql(
                                            "from VehicleSaleContractDetail where contractNo=? and groupId=? and vehicleId is not null and vehicleId<>''",
                                            saleContractNo, saleContractGroupId);
                            if (null != contractDetails && !contractDetails.isEmpty()) {
                                List<String> cacheIdList = new ArrayList<String>();
                                for (VehicleDfSapDelivery sapDelivery : sapDeliveries) {
                                    String contractDetailId = sapDelivery.getContractDetailId();
                                    if (StringUtils.isEmpty(contractDetailId)) {
                                        for (VehicleSaleContractDetail contractDetail : contractDetails) {
                                            if(!cacheIdList.contains(contractDetail.getContractDetailId())){
                                                cacheIdList.add(contractDetail.getContractDetailId());
                                                if(isConfirm){
                                                    sapDelivery.setContractDetailId(contractDetail.getContractDetailId());
                                                }else{
                                                    sapDelivery.setContractDetailId(null);
                                                }
                                                caseSaleQuantity++;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        //将提货单信息同步到车辆库存
                        List<String> cacheIdList = new ArrayList<String>();
                        List<VehicleStocks> vehicleStocks =
                                (List<VehicleStocks>) baseDao.findByHql("from VehicleStocks where orderId=?", order.getSelfId());
                        for (VehicleDfSapDelivery sapDelivery : sapDeliveries) {
                            for (VehicleStocks vehicleStock : vehicleStocks) {
                                //提货单中的底盘号为空或以‘DEL’开头的不同步
                                if(StringUtils.isNotEmpty(vehicleStock.getUnderpanNo()) &&
                                        (StringUtils.isEmpty(sapDelivery.getUnderpanNo()) || sapDelivery.getUnderpanNo().startsWith("DEL"))){
                                    continue;
                                }
                                String selfId = sapDelivery.getSelfId();
                                if(!cacheIdList.contains(selfId)){
                                    cacheIdList.add(selfId);
                                    if(isConfirm){
                                        //同步底盘号
                                        vehicleStock.setUnderpanNo(sapDelivery.getUnderpanNo());
                                    }else{
                                        vehicleStock.setUnderpanNo(null);
                                    }
                                    caseStockQuantity++;
                                }

                            }
                        }

                        if(declareQuantity != caseSaleQuantity + caseStockQuantity){
                            throw new ServiceException(String.format("操作失败，原因为：申报数量为：%s，销售合同同步更新数量：%s，车辆库存同步更新数量：%s",declareQuantity, caseSaleQuantity, caseStockQuantity));
                        }
                        syncVehicleOrder(detail, isConfirm);
                    }

                }
            }
        }else{  //非东风订单，创建提货单
            if(isConfirm) {
                for (VehiclePurchaseContractDetail detail : details) {
                    String orderId = detail.getOrderId();    //订单ID
                    List<List<Object>> vehicleIds = baseDao.findBySql("SELECT stock.vehicle_id as vehicleId\n" +
                            "FROM vehicle_stocks stock\n" +
                            "WHERE stock.order_id = ?\n" +
                            "\tAND NOT EXISTS (\n" +
                            "\t\tSELECT 1\n" +
                            "\t\tFROM vehicle_DF_sap_delivery\n" +
                            "\t\tWHERE vehicle_id = stock.vehicle_id\n" +
                            "\t)", orderId);
                    if (null == vehicleIds || vehicleIds.isEmpty() || vehicleIds.get(0).isEmpty()) {
                        throw new ServiceException("操作失败，库存中未找到可同步的车辆");
                    }
                    int quantity = vehicleIds.size();
                    if (detail.getDeclareQuantity() > quantity) {
                        throw new ServiceException(String.format("操作失败，同步车辆信息时，库存中可同步的车辆数量(%s)小于当前确认数量", quantity, detail.getDeclareQuantity()));
                    }

                    for (int i = 0; i < detail.getDeclareQuantity(); i++) {
                        VehicleDfSapDelivery sapDelivery = new VehicleDfSapDelivery();
                        sapDelivery.setSelfId(Tools.newGuid());
                        sapDelivery.setVehicleId(vehicleIds.get(i).get(0).toString());
                        sapDelivery.setQuantity(1);
                        sapDelivery.setCreateDate(TimestampUitls.getTime());
                        sapDelivery.setComfirmStatus(ConfirmStatus.UNCONFIRMED.getCode());  //设置为未确认
                        sapDelivery.setContractDetailId(detail.getSelfId());
                        baseDao.save(sapDelivery);
                        List<VehiclePurchaseOrderConversion> conversions = (List<VehiclePurchaseOrderConversion>) baseDao.findByHql("from VehiclePurchaseOrderConversion where orderId=?", detail.getOrderId());
                        if (null != conversions && !conversions.isEmpty()) {
                            for (VehiclePurchaseOrderConversion conversion : conversions) {
                                VehicleConversionDetail item = new VehicleConversionDetail();
                                item.setConversionDetailId(Tools.newGuid());
                                item.setVocdId(sapDelivery.getSelfId());
                                item.setItemId(conversion.getItemId());
                                item.setItemNo(conversion.getItemNo());
                                item.setItemName(conversion.getItemName());
                                item.setItemCost(conversion.getItemCost());
                                item.setItemCostOri(conversion.getItemCost());
                                item.setSupplierId(conversion.getSupplierId());
                                item.setSupplierNo(conversion.getSupplierNo());
                                item.setSupplierName(conversion.getSupplierName());
                                item.setStatus(ConversionStatus.UNCONFIRMED.getCode());
                                item.setConversionType((short)0); //入库前
                                item.setVehicleId(sapDelivery.getVehicleId());
                                VehicleStocks vehicleStocks = baseDao.get(VehicleStocks.class, sapDelivery.getVehicleId());
                                item.setVehicleVin(vehicleStocks.getUnderpanNo());  //入库前，VIN使用底盘号
                                item.setPaymentStatus((short)0);
                                item.setVaryFlag(false);
                                baseDao.save(item);
                            }
                        }
                    }

                    syncVehicleOrder(detail, isConfirm);
                }
            }else{
                for (VehiclePurchaseContractDetail detail : details) {
                    List<VehicleDfSapDelivery> vehicleDfSapDeliverys = (List<VehicleDfSapDelivery>)baseDao.findByHql("from VehicleDfSapDelivery where contractDetailId=?", detail.getSelfId());
                    if(null != vehicleDfSapDeliverys && !vehicleDfSapDeliverys.isEmpty()){
                        for(VehicleDfSapDelivery vehicleDfSapDelivery : vehicleDfSapDeliverys){
                            Byte status = vehicleDfSapDelivery.getComfirmStatus();
                            ConfirmStatus confirmStatus = ConfirmStatus.valueOf(status);
                            if(confirmStatus == ConfirmStatus.CONFIRMED){
                                throw new ServiceException(String.format("非东风订单的提货单(SAP订单号：%s)已确认，不允许反确认车辆采购合同", vehicleDfSapDelivery.getSapOrderNo()));
                            }
                            baseDao.delete(vehicleDfSapDelivery);
                            baseDao.getCurrentSession().createQuery("delete from cn.sf_soft.office.approval.model.VehicleConversionDetail where vocdId='"+vehicleDfSapDelivery.getSelfId()+"'").executeUpdate();
                        }
                    }
                    syncVehicleOrder(detail, isConfirm);
                }
            }

        }
    }*/

    private void validate(VehiclePurchaseContract contract, boolean isConfirm){
        if(isConfirm) return;
        List<List<Object>> list = baseDao.findBySql("SELECT deliver.* FROM vehicle_df_sap_delivery deliver\n" +
                "                WHERE \n" +
                "                EXISTS(SELECT 1 FROM vehicle_purchase_contract_detail detail \n" +
                "                WHERE detail.contract_id=? and \n" +
                "                deliver.contract_detail_id=detail.self_id AND \n" +
                "                (deliver.finish_status=1 OR deliver.comfirm_status=1))", contract.getContractId());
        if(null != list && !list.isEmpty()){
            throw new ServiceException("操作失败，车辆采购合同明细中存在已在途确认或已入库的车辆");
        }
    }

    private void syncVehicleOrder(VehiclePurchaseContract contract, VehiclePurchaseContractDetail detail, Op op, boolean isDfOrder) {
        logger.debug("同步更新采购订单");
        String orderId = detail.getOrderId();
        VehiclePurchaseOrder order = baseDao.get(VehiclePurchaseOrder.class, orderId);
        if (null == order) {
            throw new ServiceException(String.format("车辆采购订单(标识：%s)不存在", orderId));
        }

        //申报数量
        int declareQuantity = detail.getDeclareQuantity();
        //设置采购订单的申报数量
        if (op == Op.CONFIRM) {
            //订单车辆台数
            int orderQuantity = null == order.getVehicleQuantity() ? 0 : order.getVehicleQuantity();
            //订单终止车辆数量
            int orderAbortQuantity = null == order.getAbortQuantity() ? 0 : order.getAbortQuantity();
            //订单申报数量
            int orderDeclareQuantity = null == order.getDeclareQuantity() ? 0 : order.getDeclareQuantity();
            int quantity = orderQuantity - orderAbortQuantity - orderDeclareQuantity;
            if (!isDfOrder && declareQuantity > quantity) {
                throw new ServiceException(String.format("操作失败，车辆采购订单(%s)可申报的车辆数量(%s)小于采购订单可确认车辆数量(%s)"
                        , order.getDocumentNo(), quantity, declareQuantity));
            }
            order.setDeclareQuantity(orderDeclareQuantity + declareQuantity);
            logger.debug("确认后采购订单的申报数量：{}", order.getDeclareQuantity());
        } else {
            //如果采购订单已作废或者合同未确认不做校验
            if (op == Op.FORBID) {
                Short status = order.getStatus();
                if (null == status || status == Constant.DocumentStatus.OBSOLETED || null == contract.getConfirmTime()) {
                    return;
                }
            }
            String opTitle = "反确认";
            if (op == Op.FORBID) {
                opTitle = "作废";
            }
            //订单申报数量
            int orderDeclareQuantity = null == order.getDeclareQuantity() ? 0 : order.getDeclareQuantity();
            if (!isDfOrder && orderDeclareQuantity < declareQuantity) {
                throw new ServiceException(String.format("操作失败，车辆采购订单(%s)已申报的车辆数量(%s)小于采购订单可%s的车辆数量(%s)", order.getDocumentNo(), orderDeclareQuantity, opTitle, declareQuantity));
            }
            order.setDeclareQuantity(order.getDeclareQuantity() - declareQuantity);
            logger.debug("{}后采购订单的申报数量：{}", opTitle, order.getDeclareQuantity());
        }
    }

    enum Op{
        CONFIRM,//确认
        UN_CONFIRM,//反确认
        FORBID //作废
    }

}
