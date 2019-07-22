package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.office.approval.ui.vo.PurchaseOrderView;
import cn.sf_soft.vehicle.contract.model.BaseVehicleModelCatalog;
import cn.sf_soft.vehicle.contract.model.VwVehicleType;
import cn.sf_soft.vehicle.purchase.model.*;
import cn.sf_soft.vehicle.purchase.service.ConfirmStatus;
import cn.sf_soft.vehicle.purchase.service.VehiclePurchaseOrderService;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 车辆采购订单-审批服务
 *
 * @author caigx
 */
@Service("vehiclePurchaseOrderApproveService")
public class VehiclePurchaseOrderApproveService extends BaseApproveProcess {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehiclePurchaseOrderApproveService.class);

    @Autowired
    private VehiclePurchaseOrderService vehiclePurchaseOrderService;

    @Autowired
    private BaseDao baseDao;

    /**
     * 提交时审判点条件判断按sql判断，需重写此方法
     *
     * @param documentNo
     * @return
     */
    @Override
    public String getConditionSql(String documentNo) {
        //审批点条件增加车辆品系 vehicle_strain
        return "SELECT a.*,b.vehicleStrain AS vehicle_strain FROM dbo.vehicle_purchase_order a\n" +
                "LEFT JOIN dbo.vw_vehicle_type b ON a.vno_id=b.vno_id\n" +
                "WHERE a.document_no='" + documentNo + "'";
    }


    //审批中的个性化字段
    @Override
    public PurchaseOrderView dealApproveDocument(VwOfficeApproveDocuments vwOfficeApproveDocuments) {
        PurchaseOrderView view = PurchaseOrderView.fillDataByVwDocuments(vwOfficeApproveDocuments);
        try {
            List<VehiclePurchaseOrder> purchaseOrderList = (List<VehiclePurchaseOrder>) baseDao.findByHql("FROM VehiclePurchaseOrder where documentNo = ? ", vwOfficeApproveDocuments.getDocumentNo());
            VehiclePurchaseOrder purchaseOrder = purchaseOrderList.get(0);
            view.setDocumentAmount(Tools.toBigDecimal(purchaseOrder.getVehicleQuantity()).multiply(Tools.toBigDecimal(purchaseOrder.getPurchasePrice())).doubleValue());
        } catch (Exception ex) {
            logger.warn(String.format("车辆采购订单 %s,处理审批列表信息出错", vwOfficeApproveDocuments.getDocumentNo()), ex);
        }

        return view;
    }


    @Override
    public ApproveDocuments getDocumentDetail(String documentNo) {
        List<VehiclePurchaseOrder> purchaseOrderList = (List<VehiclePurchaseOrder>) baseDao.findByHql("FROM VehiclePurchaseOrder where documentNo = ? ", documentNo);
        if (purchaseOrderList == null || purchaseOrderList.size() == 0) {
            throw new ServiceException(documentNo + "未找到对应的采购订单");
        }
        VehiclePurchaseOrder purchaseOrder = purchaseOrderList.get(0);
        List<VehiclePurchaseOrderConversion> conversionList = (List<VehiclePurchaseOrderConversion>) baseDao.findByHql("FROM VehiclePurchaseOrderConversion where documentNo = ? ", documentNo);
        if (conversionList != null && conversionList.size() > 0) {
            purchaseOrder.setChargeDetail(new HashSet<VehiclePurchaseOrderConversion>(conversionList));
        }

        return purchaseOrder;
    }

    @Override
    protected String getApprovalPopedomId() {
        return null;
    }

    @Override
    public Constant.ApproveResultCode checkData(ApproveDocuments approveDocument, Constant.ApproveStatus approveStatus) {
        return Constant.ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        List<VehiclePurchaseOrder> purchaseOrderList = (List<VehiclePurchaseOrder>) baseDao.findByHql("FROM VehiclePurchaseOrder where documentNo = ? ", approveDocument.getDocumentNo());
        VehiclePurchaseOrder purchaseOrder = purchaseOrderList.get(0);
        //生成库存记录
        List<VehicleStocks> stocksList = genVehicleStocks(purchaseOrder);
        autoUpdateContracts(purchaseOrder, stocksList);
        return super.onLastApproveLevel(approveDocument, comment);
    }

    /**
     * 自动装配合同车辆
     *
     * @param purchaseOrder
     * @param stocksList
     */
    private void autoUpdateContracts(VehiclePurchaseOrder purchaseOrder, List<VehicleStocks> stocksList) {
        if (StringUtils.isEmpty(purchaseOrder.getSaleContractGroupId())) {
            return;
        }

        if (stocksList == null || stocksList.size() == 0) {
            return;
        }

        String sql = "SELECT a.*,b.customer_id,b.contract_code,b.customer_name,b.seller,b.seller_id FROM dbo.vehicle_sale_contract_detail a\n" +
                "INNER JOIN dbo.vehicle_sale_contracts b ON a.contract_no=b.contract_no\n" +
                "WHERE a.approve_status<>30 AND b.status<=50 AND ISNULL(a.vehicle_id,'')='' AND a.group_id =:groupId";
        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("groupId", purchaseOrder.getSaleContractGroupId());
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, paramMap);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i > stocksList.size() - 1) {
                    continue;
                }
                Map<String, Object> item = list.get(i);
                VehicleSaleContractDetail contractDetail = baseDao.get(VehicleSaleContractDetail.class, (String) item.get("contract_detail_id"));
                VehicleStocks stocks = stocksList.get(i);
                if (!StringUtils.equals(contractDetail.getVnoId(), stocks.getVnoId())) {
                    //只配未配车且车型相同的销售合同明细,如果合同的车型变了，则跳过
                    continue;
                }

                //自动配车
                contractDetail.setVehicleCostRef(stocks.getVehicleCost());
                contractDetail.setVehicleCost(stocks.getVehicleCost());
                contractDetail.setVehicleCarriage(stocks.getVehicleCarriage());
                contractDetail.setVehicleVin(stocks.getVehicleVin());
                contractDetail.setVehicleId(stocks.getVehicleId());
                contractDetail.setVnoId(stocks.getVnoId());
                contractDetail.setVehicleSalesCode(stocks.getVehicleSalesCode());
                contractDetail.setVehicleVno(stocks.getVehicleVno());
                contractDetail.setVehicleVinNew(stocks.getVehicleVin());
                contractDetail.setDriveRoomNo(stocks.getDriveRoomNo());
                contractDetail.setVehicleNameNew(stocks.getVehicleName());
                contractDetail.setVehicleName(stocks.getVehicleName());
                contractDetail.setVehicleStrain(stocks.getVehicleStrain());
                contractDetail.setVehicleEngineType(stocks.getVehicleEngineType());
                contractDetail.setVehicleEngineNo(stocks.getVehicleEngineNo());
                contractDetail.setVehicleEligibleNo(stocks.getVehicleEligibleNo());
                contractDetail.setVehicleColor(stocks.getVehicleColor());
                contractDetail.setWarehouseId(stocks.getWarehouseId());
                contractDetail.setWarehouseName(stocks.getWarehouseName());
                contractDetail.setVehicleOutFactoryTime(stocks.getVehicleOutFactoryTime());
                contractDetail.setConversionCost(stocks.getModifiedFee());
                contractDetail.setMaintainFee(stocks.getMaintainFee());
                contractDetail.setOtherCost(stocks.getOtherCost());
                baseDao.save(contractDetail);

                updateLoanBudget(contractDetail);

                //更新库存
                stocks.setSaleContractNo(contractDetail.getContractNo());
                stocks.setSaleContractCode((String) item.get("contract_code"));
                stocks.setSellerId((String) item.get("seller_id"));
                stocks.setSeller((String) item.get("seller"));
                stocks.setCustomerId((String) item.get("customer_id"));
                stocks.setCustomerName((String) item.get("customer_name"));
                stocks.setStatus((short) 1);
                baseDao.save(stocks);
            }
        }
    }


    //同步消贷预算单
    private void updateLoanBudget(VehicleSaleContractDetail detail) {
        String sql = "SELECT a.* FROM dbo.vehicle_loan_budget_details a WITH ( NOLOCK )\n"
                + "LEFT JOIN dbo.vehicle_loan_budget b  WITH ( NOLOCK ) " +
                "ON b.document_no = a.document_no WHERE b.flow_status IN (0,1,30,40,45,50,60,70) AND a.sale_contract_detail_id =:saleContractDetailId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("saleContractDetailId", detail.getContractDetailId());
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> result : list) {
                VehicleLoanBudgetDetails budgetDetail = baseDao.get(VehicleLoanBudgetDetails.class, (String) result.get("self_id"));

                //如果车型、车款合计、公告型号变了
                if (!StringUtils.equals(detail.getVehicleVin(), budgetDetail.getVehicleVin()) || !StringUtils.equals(detail.getVnoId(), budgetDetail.getVnoId()) || !StringUtils.equals(detail.getVnoIdNew(), budgetDetail.getBulletinId()) || !StringUtils.equals(detail.getVehicleVnoNew(), budgetDetail.getBulletinNo()) || Tools.toDouble(detail.getVehiclePriceTotal()) != Tools.toDouble(budgetDetail.getVehiclePriceTotal())) {
//                    if (Tools.toDouble(detail.getVehiclePriceTotal()) < Tools.toDouble(budgetDetail.getLoanAmount())) {
//                        throw new ServiceException(String.format("车款合计%s不能小于已建立贷款预算的车辆贷款%s", Tools.toDouble(detail.getVehiclePriceTotal()), Tools.toDouble(budgetDetail.getLoanAmount())));
//                    }
                    budgetDetail.setVehicleVin(detail.getVehicleVin());
                    budgetDetail.setVehiclePriceTotal(detail.getVehiclePriceTotal());
                    budgetDetail.setVnoId(detail.getVnoId());
                    budgetDetail.setBulletinId(detail.getVnoIdNew());
                    budgetDetail.setBulletinNo(detail.getVehicleVnoNew());

                }
            }
        }
    }

    /**
     * 生成库存记录，不生成资源单（vehicleResource），直接生成库存记录（）
     *
     * @param purchaseOrder
     */
    public List<VehicleStocks> genVehicleStocks(VehiclePurchaseOrder purchaseOrder) {

        List<VehicleStocks> stocksList = new ArrayList<>();
        for (int i = 0; i < purchaseOrder.getVehicleQuantity(); i++) {
            VehicleStocks stocks = null;
            String vehicleVin = String.format("$%s_%s", purchaseOrder.getDocumentNo(), (i + 1));
            //如果存在此VIN则修改库存
            List<VehicleStocks> vehicleStocksList = (List<VehicleStocks>) baseDao.findByHql("FROM VehicleStocks where vehicleVin = ?", vehicleVin);
            if (vehicleStocksList == null || vehicleStocksList.size() == 0) {
                stocks = new VehicleStocks();
                stocks.setVehicleId(UUID.randomUUID().toString());
            } else {
                stocks = vehicleStocksList.get(0);
            }

            //生成VIN时赋值$前缀+订单编码+流水（如"$IVPO18090002_1）
            stocks.setVehicleVin(vehicleVin);
            stocks.setResourcesNo(stocks.getVehicleVin());
            stocks.setOrderId(purchaseOrder.getSelfId());//采购订单的ID
            stocks.setStationId(purchaseOrder.getStationId());
            stocks.setSaleContractCode(null);
            stocks.setSaleContractCode(null); //配车后回填

            stocks.setVnoId(purchaseOrder.getVnoId());
            VwVehicleType vehicleModel = dao.get(VwVehicleType.class, purchaseOrder.getVnoId());
            if (vehicleModel != null) {
                stocks.setVehicleSalesCode(vehicleModel.getProductModel());
                stocks.setVehicleVno(vehicleModel.getProductModel());
                stocks.setVehicleName(vehicleModel.getVehicleName());
                stocks.setVehicleStrain(vehicleModel.getVehicleStrain());
            }

            stocks.setVehicleColor(purchaseOrder.getColor());
            stocks.setVehicleQuantity(1); //数量默认为1
            dao.save(stocks);
            stocksList.add(stocks);
        }

        return stocksList;
    }


    //作废
    @Override
    public void forbiddingRecord(String documentNo, String modifyTime) {
        List<VehiclePurchaseOrder> orderList = (List<VehiclePurchaseOrder>) baseDao.findByHql("FROM VehiclePurchaseOrder where documentNo = ? ", documentNo);
        if (orderList == null || orderList.size() == 0) {
            throw new ServiceException(String.format("车辆采购订单(%s)无效", documentNo));
        }

        VehiclePurchaseOrder order = orderList.get(0);

        this.validateModifyTime(modifyTime, order.getModifyTime());
        List<VehiclePurchaseContractDetail> vehiclePurchaseContractDetails = (List<VehiclePurchaseContractDetail>) baseDao.findByHql("from VehiclePurchaseContractDetail where orderId=?", order.getSelfId());
        if (null != vehiclePurchaseContractDetails && !vehiclePurchaseContractDetails.isEmpty()) {
            for (VehiclePurchaseContractDetail detail : vehiclePurchaseContractDetails) {
                VehiclePurchaseContract purchaseContract = baseDao.get(VehiclePurchaseContract.class, detail.getContractId());
                if (purchaseContract != null && Tools.toByte(purchaseContract.getStatus()) != ConfirmStatus.FORBID.getCode()) { //采购合同非作废
                    throw new ServiceException(String.format("车辆采购订单(%s)已被车辆采购合同(%s)关联，不允许作废", documentNo, vehiclePurchaseContractDetails.get(0).getContractNo()));
                }
            }
        }

//        Map<String, Object> paramMap = new HashMap<>(1);
//        paramMap.put("orderId", order.getSelfId());
//        List<Map<String, Object>> list = baseDao.getMapBySQL("select a.vehicle_vin, c.contract_no from vehicle_stocks a \n" +
//                "join vehicle_sale_contract_detail b \n" +
//                "on a.vehicle_id = b.vehicle_id \n" +
//                "join vehicle_sale_contracts c\n" +
//                "on c.contract_no = b.contract_no\n" +
//                "where a.order_id=:orderId\n" +
//                "and c.status<=50", paramMap);
        List<Map<String, Object>> list = getOrderVehicleInUse(order.getSelfId());
        if (null != list && !list.isEmpty()) {
            Map<String, Object> item = list.get(0);
            throw new ServiceException(String.format("车辆采购订单（%s）中车辆（%s）已被车辆销售合同（%s）配车，不允许作废", documentNo, item.get("vehicle_vin"), item.get("contract_no")));
        }
        //删除车辆资源
        Query query = baseDao.getCurrentSession().createQuery("delete VehicleStocks where orderId='" + order.getSelfId() + "'");
        order.setStatus((short) 80); //终止
        query.executeUpdate();
    }


    //查询订单中已配入销售合同的车辆
    public List<Map<String, Object>> getOrderVehicleInUse(String orderId) {
        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("orderId", orderId);
        List<Map<String, Object>> list = baseDao.getMapBySQL("select a.vehicle_vin, c.contract_no from vehicle_stocks a \n" +
                "join vehicle_sale_contract_detail b \n" +
                "on a.vehicle_id = b.vehicle_id \n" +
                "join vehicle_sale_contracts c\n" +
                "on c.contract_no = b.contract_no\n" +
                "where a.order_id=:orderId\n" +
                "and c.status<=50", paramMap);
        return list;
    }

    @Override
    public Object getReturnDataWithForbid(String documentNo) {
        return vehiclePurchaseOrderService.convertReturnData(documentNo);
    }

    //撤销
    @Override
    public void revokingRecord(String documentNo, String modifyTime) {
        List<VehiclePurchaseOrder> orderList = (List<VehiclePurchaseOrder>) baseDao.findByHql("FROM VehiclePurchaseOrder where documentNo = ? ", documentNo);
        if (orderList == null || orderList.size() == 0) {
            throw new ServiceException(String.format("车辆采购订单(%s)无效", documentNo));
        }
        VehiclePurchaseOrder order = orderList.get(0);
        Short bytStatus = order.getStatus();
        //在各自的审批中校验状态
        if (Tools.toShort(bytStatus) >= 40) {
            throw new ServiceException("撤销失败:单据处于不可撤销状态");
        }
        this.validateModifyTime(modifyTime, order.getModifyTime());

        // 如果单据在'不同意'或'制单中'状态时撤销，则置为'已撤销'状态；
        order.setStatus(bytStatus < 20 ? (short) 70 : (short) 10);
    }

    @Override
    public Object getReturnDataWithRevoke(String documentNo) {
        return vehiclePurchaseOrderService.convertReturnData(documentNo);
    }
}
