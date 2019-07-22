package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import cn.sf_soft.basedata.model.BaseSysAgency;
import cn.sf_soft.basedata.service.SysCodeRulesService;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferCalc;
import cn.sf_soft.mobile.documentBuffer.DocumentBufferService;
import cn.sf_soft.office.approval.dao.PartPurchasePlanDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;
import cn.sf_soft.office.approval.ui.vo.PartPurchasePlanView;
import cn.sf_soft.parts.inventory.model.PartPurchaseOrder;
import cn.sf_soft.parts.inventory.model.PartPurchasePlanDetail;
import cn.sf_soft.parts.inventory.model.PartPurchasePlans;
import cn.sf_soft.parts.inventory.model.PartPurchaseUrgentOrder;
import cn.sf_soft.user.model.SysUsers;

/**
 * 配件采购计划
 *
 * @author ZHJ
 */
@Service("partPurchasePlanBuf")
public class PartPurchasePlanBuf extends DocumentBufferService {

    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PartPurchasePlanBuf.class);
    //审批权限id
    protected String approvalPopedomId = "15101020";

    private static final String DF_PART_OBJECT_NO = "DF_PART_OBJECT_NO";

    private static final String DF_PLAN_ORDER_TYPE = "df_plan_order_type";

    private static final String DF_MONITOR_ORDER_TYPE = "df_monitor_order_type";

    private static final String DF_PURCHASE_ORDER_DEPOSIT_PROPORTION = "DF_PURCHASE_ORDER_DEPOSIT_PROPORTION";

    private static final String SEND_ORDER = "直送订单";

    private static final String SALE_ORDER = "销售订单";

    private static final String UREA = "尿素";
    private static final String CORE = "滤芯";

    private static DecimalFormat df = new DecimalFormat("#.00");

    private int dfDepositProportion;

    @Autowired
    private SysCodeRulesService sysCodeService;

    @Autowired
    @Qualifier("baseDao")
    private BaseDao dao;

    @Autowired
    @Qualifier("partPurchasePlanBufferCacl")
    public void setDocBuffer(DocumentBufferCalc docBuffer) {
        super.docBuffer = docBuffer;
    }


    public PartPurchasePlanBuf() {
        documentClassId = 10000;
        //docBuffer = saleContractVaryBuffer; // 缓存文旦计算
    }


    public int getDfDepositProportion() {
        return dfDepositProportion;
    }

    public void setDfDepositproportion(int dfDepositProportion) {
        this.dfDepositProportion = dfDepositProportion;
    }

    @Autowired
    private PartPurchasePlanDao partPurchasePlanDao;

    @Override
    public PartPurchasePlanView dealApproveDocument(VwOfficeApproveDocuments vwOfficeApproveDocuments) {
        PartPurchasePlanView planView = PartPurchasePlanView.fillDataByVwDocuments(vwOfficeApproveDocuments);
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append("SELECT round(planPrice,0) as planPrice from PartPurchasePlans where documentNo=?");
            @SuppressWarnings("unchecked")
            List<Object> data1 = (List<Object>) dao.findByHql(buffer.toString(), vwOfficeApproveDocuments.getDocumentNo());
            double planPrice = 0;
            if (data1 != null && data1.size() == 1) {
                planPrice = data1.get(0) != null ? Tools.toDouble((Double) data1.get(0)) : 0;
            }
            planView.setPlanPrice(planPrice);
        } catch (Exception ex) {
            logger.warn(String.format("配件采购计划 %s,处理审批列表信息出错", vwOfficeApproveDocuments.getDocumentNo()), ex);

        }
        return planView;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveDocuments getDocumentDetail(String documentNo) {
        if (StringUtils.isEmpty(documentNo)) {
            throw new ServiceException("审批单号为空");
        }
        PartPurchasePlans purchasePlan = dao.get(PartPurchasePlans.class, documentNo);
        if (purchasePlan == null) {
            throw new ServiceException("根据审批单号：" + documentNo + "未找到合同变更的记录");
        }
        Query query = dao.getCurrentSession().createQuery("select supplierName from PartPurchasePlanDetail  where documentNo=?");
        query.setFirstResult(0);
        query.setMaxResults(1);
        query.setParameter(0, purchasePlan.getDocumentNo());
        List<?> list = query.list();
        if(null != list && !list.isEmpty()){
            purchasePlan.setSupplierName(list.get(0).toString());
        }
        return purchasePlan;
    }

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResultCode checkData(ApproveDocuments approveDocument,
                                       ApproveStatus approveStatus) {
        if (approveDocument == null || approveDocument.getDocumentNo().isEmpty()) {
            throw new ServiceException("审批失败:审批单号不能为空");
        }
        if (approveStatus == ApproveStatus.LAST_APPROVE) {
            validateRecord(approveDocument);
        }
        return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }


    @SuppressWarnings("rawtypes")
    private void validateRecord(ApproveDocuments approveDocument) {
        PartPurchasePlans purchasePlan = dao.get(PartPurchasePlans.class, approveDocument.getDocumentNo());
        if (purchasePlan == null) {
            throw new ServiceException("审批失败:未找到合同变更主表记录");
        }
        if (StringUtils.isEmpty(purchasePlan.getDocumentNo())) {
            throw new ServiceException("审批失败:审批单号不能为空");
        }

        if (purchasePlan.getPlanType() == PartPlanType.BaseDfOrder.code) {
            String partPurchaseDefault = partPurchasePlanDao.getPartPurchaseDefault(purchasePlan.getStationId());
            if (partPurchaseDefault == null || partPurchaseDefault.isEmpty()) {
                throw new ServiceException("没有设置配件采购默认站点，请前往设置！");
            }
        }
        List<Map<String, Object>> list = partPurchasePlanDao.getSupplierListByDocumentNo(approveDocument.getDocumentNo());
        if (list == null || list.size() == 0) {
            throw new ServiceException("没有采购供应商的信息，请确认后再审批！");
        }
        String optionValue = dao.getOptionValue(DF_PART_OBJECT_NO);
        List<Map<String, Object>> listObjectNo = null;
        if (optionValue != null && !optionValue.isEmpty()) {
            listObjectNo = partPurchasePlanDao.getDfSupplierNoByObjectNo(optionValue);
        }
        Short planType = purchasePlan.getPlanType();
        if (listObjectNo != null && listObjectNo.size() > 0) {
            for (Map map : list) {
                String supplierNo = map.get("supplier_no").toString();
                if (planType == PartPlanType.BaseDfOrder.getCode() || planType == PartPlanType.DfRushOrder.getCode()) {
                    for (Map mapObjectNo : listObjectNo) {
                        if (!mapObjectNo.containsValue(supplierNo)) {
                            throw new ServiceException("采购计划类型为东风一般订单或东风紧急订单时，供应商必须为东风供应商！");
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void CreatePartPurchaseOrders(PartPurchasePlans purchasePlan,
                                          List<Map<String, Object>> suppliers) {
//        Map<Short, Object> orderTypeMap = partPurchasePlanDao.getOrderType(DF_PART_ORDER_TYPE);
        dfDepositProportion = Integer.parseInt(dao.getOptionValue(DF_PURCHASE_ORDER_DEPOSIT_PROPORTION));
        String partPurchaseDefault = partPurchasePlanDao.getPartPurchaseDefault(purchasePlan.getStationId());
        if (partPurchaseDefault == null || partPurchaseDefault.isEmpty()) {
            throw new ServiceException("没有设置配件采购默认站点，请前往设置！");
        }

        SysUsers sysUser = HttpSessionStore.getSessionUser();
        int orderNo = 1;
        for (Map map : suppliers) {
            PartPurchaseOrder purchaseOrder = new PartPurchaseOrder();
            purchaseOrder.setPurchaseId(UUID.randomUUID().toString());
            purchaseOrder.setPurchaseNo(sysCodeService.createSysCodeRules("PPO_NO", sysUser.getDefaulStationId()));
            purchaseOrder.setSupplierId(map.get("supplier_id").toString());
            purchaseOrder.setSupplierNo(map.get("supplier_no").toString());
            purchaseOrder.setSupplierName(map.get("supplier_name").toString());
            purchaseOrder.setPurchaseStatus((short) 0);
            purchaseOrder.setApproveStatus((short) 0);
            purchaseOrder.setPlanNo(purchasePlan.getDocumentNo());
            purchaseOrder.setStationId(purchasePlan.getStationId());
            purchaseOrder.setStationIdPurchase(purchasePlan.getStationId());
            purchaseOrder.setCreator(purchasePlan.getCreator());
            purchaseOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
            purchaseOrder.setDeliveryMode("");
            double iPlanTotalQuantity = 0.0, iPlanTotalMoney = 0.0;
            if (purchasePlan.getPlanType() == PartPlanType.BaseDfOrder.getCode()) {//东风一般订单
                purchaseOrder.setStationIdPurchase(partPurchaseDefault);
                purchaseOrder.setPurchaseOrderType(30);
                purchaseOrder.setUploadStatus((short) 10);
                purchaseOrder.setOrderType(getDfOrderType(purchasePlan));

                if (purchaseOrder.getOrderType().equals(SEND_ORDER)) {
                    if (purchasePlan.getDfPlanOrderType() == (short) 50) {
                        purchaseOrder.setPredirectType(UREA);
                    } else if (purchasePlan.getDfPlanOrderType() == (short) 55) {
                        purchaseOrder.setPredirectType(CORE);
                    }
                }
                if (purchaseOrder.getOrderType().equals(SALE_ORDER)) {
                    purchaseOrder.setOrderFactory("3203");
                } else {
                    purchaseOrder.setOrderFactory("3200");
                }
                purchaseOrder.setSelfPayCarryFee("N");
                purchaseOrder.setOrderStatus("订货");
                purchaseOrder.setEarnestPercent(dfDepositProportion);
                BaseSysAgency agency = partPurchasePlanDao.getBaseSysAgencyByStationId(partPurchaseDefault);
                if (agency != null) {
                    purchaseOrder.setOrgId(agency.getOrgId());
                    purchaseOrder.setObjectIdSell(agency.getAgencyId());
                    purchaseOrder.setObjectNoSell(agency.getAgencyCode());
                    purchaseOrder.setObjectNameSell(agency.getAgencyName());
                    purchaseOrder.setObjectIdInvoice(agency.getAgencyId());
                    purchaseOrder.setObjectNoInvoice(agency.getAgencyCode());
                    purchaseOrder.setObjectNameInvoice(agency.getAgencyName());
                    purchaseOrder.setObjectIdPayment(agency.getAgencyId());
                    purchaseOrder.setObjectNoPayment(agency.getAgencyCode());
                    purchaseOrder.setObjectNamePayment(agency.getAgencyName());
                    purchaseOrder.setObjectIdReceive(agency.getAgencyId());
                    purchaseOrder.setObjectNoReceive(agency.getAgencyCode());
                    purchaseOrder.setObjectNameReceive(agency.getAgencyName());
                } else {
                    throw new ServiceException("没有设置配件采购默认站点，请前往设置！");
                }
            } else if (purchasePlan.getPlanType() == PartPlanType.DfRushOrder.code) {//东风紧急订单
                purchaseOrder.setStationIdPurchase(partPurchaseDefault);//
                purchaseOrder.setPurchaseOrderType(40);
                purchaseOrder.setOrderType("");
                purchaseOrder.setUploadStatus((short) 10);
                purchaseOrder.setObjectIdSell("");
                purchaseOrder.setObjectNoSell("");
                purchaseOrder.setObjectNameSell("");
                purchaseOrder.setObjectNoInvoice("");
                purchaseOrder.setObjectNameInvoice("");
                purchaseOrder.setObjectIdPayment("");
                purchaseOrder.setObjectNoPayment("");
                purchaseOrder.setObjectNamePayment("");
                purchaseOrder.setObjectIdReceive("");
                purchaseOrder.setObjectNoReceive("");
                purchaseOrder.setObjectNameReceive("");
                purchaseOrder.setObjectIdInvoice("");
                purchaseOrder.setOrgId("");
                PartPurchaseUrgentOrder ppuo = new PartPurchaseUrgentOrder();
                ppuo.setReceivingUnitsName("");
                ppuo.setFailureReceivingStation("");
                ppuo.setVehicleChassisNumber("");
                ppuo.setStationFax("");
                ppuo.setZipCode("");
                ppuo.setFaultDescription("");
                ppuo.setShippingAddress("");
                ppuo.setVehicleEngineNo("");
                ppuo.setFaultLocation("");
                ppuo.setShippingType("");
                ppuo.setAppraisalNo("");
                ppuo.setDrivenDistance("");
                ppuo.setUserName("");
                ppuo.setUrgentCustomerNo("");
                ppuo.setUrgentCustomerName("");
                ppuo.setUrgentOrderId(UUID.randomUUID().toString());
                ppuo.setPurchaseId(purchaseOrder.getPurchaseId());
                ppuo.setPurchaseNo(purchaseOrder.getPurchaseNo());
                ppuo.setUrgentOrderStatus("订货状态");
                ppuo.setSaleOrderType("紧急订单");
                ppuo.setStationLinkman(sysUser.getUserName());//
                if (sysUser.getPhone() != null) {
                    ppuo.setVehicleLinkmanPhone(sysUser.getPhone());//
                } else {
                    ppuo.setVehicleLinkmanPhone("");
                }
                ppuo.setStationId(purchasePlan.getStationId());//
                BaseSysAgency baseAgency = partPurchasePlanDao.getBaseSysAgencyByStationId(partPurchaseDefault);
                if (baseAgency == null) {
                    throw new ServiceException("没有设置配件采购默认站点，请前往设置");
                } else {
                    ppuo.setOrgId(baseAgency.getOrgId());
                    ppuo.setServiceCode(baseAgency.getAgencyCode());
                    ppuo.setServiceName(baseAgency.getAgencyName());
                }
                dao.save(ppuo);
            } else if (purchasePlan.getPlanType() == PartPlanType.MonitiorOrder.code) {//监控发放订单  caigx
                BaseSysAgency baseAgency = partPurchasePlanDao.getBaseSysAgencyByStationId(partPurchaseDefault);
                if (baseAgency == null) {
                    throw new ServiceException("没有设置配件采购默认站点，请前往设置");
                } else {
                    purchaseOrder.setOrgId(baseAgency.getOrgId());
                    purchaseOrder.setObjectIdSell(baseAgency.getAgencyId());
                    purchaseOrder.setObjectNoSell(baseAgency.getAgencyCode());
                    purchaseOrder.setObjectNameSell(baseAgency.getAgencyName());
                    purchaseOrder.setObjectIdInvoice(baseAgency.getAgencyId());
                    purchaseOrder.setObjectNoInvoice(baseAgency.getAgencyCode());
                    purchaseOrder.setObjectNameInvoice(baseAgency.getAgencyName());
                    purchaseOrder.setObjectIdPayment(baseAgency.getAgencyId());
                    purchaseOrder.setObjectNoPayment(baseAgency.getAgencyCode());
                    purchaseOrder.setObjectNamePayment(baseAgency.getAgencyName());
                    purchaseOrder.setObjectIdReceive(baseAgency.getAgencyId());
                    purchaseOrder.setObjectNoReceive(baseAgency.getAgencyCode());
                    purchaseOrder.setObjectNameReceive(baseAgency.getAgencyName());
                }
                purchaseOrder.setOrderType(getDfOrderType(purchasePlan));
                purchaseOrder.setDeliveryMode("代送");//默认给代送
                purchaseOrder.setPurchaseOrderType(50);
                purchaseOrder.setUploadStatus((short) 10);
                purchaseOrder.setStationIdPurchase(partPurchaseDefault);
                purchaseOrder.setSelfPayCarryFee("N");
                purchaseOrder.setOrderStatus("订货");
                purchaseOrder.setPriceSupplier(0.00D);
                purchaseOrder.setPricePis(0.00D);
                purchaseOrder.setQuantitySupplier(0.00D);
                purchaseOrder.setQuantityPis(0.00D);
                purchaseOrder.setQuantityAbort(0.00D);
                purchaseOrder.setDeposit(0.00D);
                purchaseOrder.setOrderFactory("3200");
                purchaseOrder.setCrmQuantityApprove(0.00D);
                purchaseOrder.setCrmPriceApprove(0.00D);

            } else {
                purchaseOrder.setOrderType("");
                purchaseOrder.setObjectIdSell("");
                purchaseOrder.setObjectNoSell("");
                purchaseOrder.setObjectNameSell("");
                purchaseOrder.setObjectNoInvoice("");
                purchaseOrder.setObjectNameInvoice("");
                purchaseOrder.setObjectIdPayment("");
                purchaseOrder.setObjectNoPayment("");
                purchaseOrder.setObjectNamePayment("");
                purchaseOrder.setObjectIdReceive("");
                purchaseOrder.setObjectNoReceive("");
                purchaseOrder.setObjectNameReceive("");
                purchaseOrder.setObjectIdInvoice("");
                purchaseOrder.setOrgId("");
                purchaseOrder.setPurchaseOrderType(10);
            }
            for (PartPurchasePlanDetail pppd : purchasePlan.getPartPurchasePlanDetails()) {
                if (pppd.getSupplierId().equals(map.get("supplier_id"))) {
                    pppd.setPurchaseId(purchaseOrder.getPurchaseId());
                    pppd.setPurchaseNo(purchaseOrder.getPurchaseNo());
                    pppd.setPpdetailStatus((short) 0);
                    pppd.setPisQuantity(0.00);
                    pppd.setAbortQuantity(0.00);
                    pppd.setApproveQuantity(pppd.getPlanQuantity());
                    pppd.setSupplierQuantity(0.00);
                    pppd.setSupplierPrice(0.00);
                    pppd.setCrmApproveQuantity(0.00);
                    pppd.setCrmApprovePrice(0.00);
                    if (purchaseOrder.getPurchaseOrderType() == PartPlanType.NotDfOrder.getCode() ||
                            purchaseOrder.getPurchaseOrderType() == PartPlanType.BaseDfOrder.getCode()) {
                        pppd.setSelfStatus("新增物料");
                        pppd.setOrderNumber(String.valueOf(orderNo * 10));
                        orderNo++;

                    }
                    dao.update(pppd);
                    iPlanTotalQuantity += Tools.toDouble(pppd.getPlanQuantity());
                    //订单金额=订单明细的计划数量*计划单价的汇总 --2019-02-15
                    iPlanTotalMoney += Tools.toDouble(pppd.getPlanPrice()) * Tools.toDouble(pppd.getPlanQuantity());
                }
            }
            purchaseOrder.setPricePlan(iPlanTotalMoney);
            purchaseOrder.setPriceApprove(iPlanTotalMoney);
            purchaseOrder.setPriceSupplier(0.00);
            purchaseOrder.setPricePis(0.00);
            purchaseOrder.setQuantityPlan(iPlanTotalQuantity);
            purchaseOrder.setQuantityApprove(iPlanTotalQuantity);
            purchaseOrder.setQuantitySupplier(0.00);
            purchaseOrder.setQuantityPis(0.00);
            purchaseOrder.setQuantityAbort(0.00);
            purchaseOrder.setDeposit(0.00);
            purchaseOrder.setCrmQuantityApprove(0.00);
            purchaseOrder.setCrmPriceApprove(0.00);

            dao.save(purchaseOrder);
        }
        purchasePlan.setModifier(sysUser.getUserName());
        purchasePlan.setModifyTime(new Timestamp(System.currentTimeMillis()));
        dao.update(purchasePlan);
        dao.flush();
    }

    /**
     * 获取orderType
     *
     * @param purchasePlan
     * @return
     */
    private String getDfOrderType(PartPurchasePlans purchasePlan) {
        if (purchasePlan != null) {

            short key = Tools.toShort(purchasePlan.getDfPlanOrderType());
            if (purchasePlan.getPlanType() == PartPlanType.BaseDfOrder.getCode()) {
                if (key == 50 || key == 55) {
                    return SEND_ORDER;
                } else {
                    Map<Short, Object> planOrderTypeMap = partPurchasePlanDao.getOrderType(DF_PLAN_ORDER_TYPE);
                    return planOrderTypeMap.get(purchasePlan.getDfPlanOrderType()).toString();
                }
            } else if (purchasePlan.getPlanType() == PartPlanType.MonitiorOrder.getCode()) {
                Map<Short, Object> monitorOrderTypeMap = partPurchasePlanDao.getOrderType(DF_MONITOR_ORDER_TYPE);
                return monitorOrderTypeMap.get(purchasePlan.getDfPlanOrderType()).toString();
            }
        }
        return "";
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        if (approveDocument == null || approveDocument.getDocumentNo().isEmpty()) {
            throw new ServiceException("审批失败：未找到审批单据信息");
        }
        CreatePartPurchaseOrder(approveDocument);
        return super.onLastApproveLevel(approveDocument, comment);
    }

    @SuppressWarnings("rawtypes")
    private void CreatePartPurchaseOrder(ApproveDocuments approveDocument) {
        PartPurchasePlans purchasePlan = dao.get(PartPurchasePlans.class, approveDocument.getDocumentNo());
        List<PartPurchasePlanDetail> planDetails = partPurchasePlanDao.getPartPurchasePlanDetailByDocumentNo(approveDocument.getDocumentNo());
        purchasePlan.setPartPurchasePlanDetails(planDetails);
        List<Map<String, Object>> list = partPurchasePlanDao.getSupplierListByDocumentNo(approveDocument.getDocumentNo());
        CreatePartPurchaseOrders(purchasePlan, list);
    }

    public static enum PartPlanType {
        NotDfOrder((short) 30, "非东风订单"), BaseDfOrder((short) 40, "东风一般订单"),
        DfRushOrder((short) 50, "东风紧急订单"), MonitiorOrder((short) 60, "监控发放订单");

        private final short code;
        private final String text;

        private PartPlanType(short code, String text) {
            this.code = code;
            this.text = text;
        }

        public short getCode() {
            return this.code;
        }

        public String getText() {
            return this.text;
        }

        public static PartPlanType getPartPlanType(short code) {
            switch (code) {
                case (short) 30:
                    return NotDfOrder;
                case (short) 40:
                    return BaseDfOrder;
                case (short) 50:
                    return DfRushOrder;
                case (short) 60:
                    return MonitiorOrder;
                default:
                    return BaseDfOrder;
            }
        }

        @Override
        public String toString() {
            return this.code + "(" + this.text + ")";
        }

    }


}
