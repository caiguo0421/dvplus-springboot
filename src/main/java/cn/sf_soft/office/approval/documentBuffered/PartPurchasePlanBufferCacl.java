package cn.sf_soft.office.approval.documentBuffered;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.sf_soft.basedata.model.SysStations;
import cn.sf_soft.office.approval.ui.model.Color;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.mobile.documentBuffer.model.AppBoardFieldType;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardBuffered;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardField;
import cn.sf_soft.mobile.documentBuffer.model.MobileBoardTitle;
import cn.sf_soft.mobile.documentBuffer.model.MobileDocumentBuffered;
import cn.sf_soft.office.approval.dao.PartPurchasePlanDao;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.service.impl.PartPurchasePlanBuf.PartPlanType;
import cn.sf_soft.office.approval.ui.model.BoardTitle;
import cn.sf_soft.office.approval.ui.model.DocTitle;
import cn.sf_soft.parts.inventory.model.PartPurchasePlanDetail;
import cn.sf_soft.parts.inventory.model.PartPurchasePlans;

/**
 * 配件采购计划
 *
 * @author ZHJ
 */
@Service("partPurchasePlanBufferCacl")
public class PartPurchasePlanBufferCacl extends ApprovalDocumentCalc {

    private int documentClassId = 10000;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PartPurchasePlanBufferCacl.class);
    private static String bufferCalcVersion = "20181019.1";
    private static final String DF_PART_ORDER_TYPE = "df_plan_order_type";
    private Map<Short, Object> planOrderType = new HashMap<Short, Object>();


    private static String moduleId = "151010";

    @Autowired
    private PartPurchasePlanDao partPurchasePlanDao;

    @Autowired
    private PartPurchasePlanDetailBufferCalc detailBufferCalc;


    @Override
    public MobileDocumentBuffered compute(boolean onlyStatic, String moduleId, String documentNo) {
        MobileDocumentBuffered bufferedDoc = loadDocumentBuffered(moduleId, documentNo);

        // 设置业务单据：车辆销售合同变更单
        ApproveDocuments<?> approveDoc = (ApproveDocuments<?>) bufferedDoc.getBusiBill();

        PartPurchasePlans purchasePlan = dao.get(PartPurchasePlans.class, approveDoc.getDocumentNo());
        if (purchasePlan == null) {
            throw new ServiceException("未找到审批单号对应的配件采购计划：" + approveDoc.getDocumentNo());
        }
        bufferedDoc.setRelatedObjects(new Object[]{purchasePlan});
        if (this.isNeedToCompute(bufferedDoc)) {
            computeStaticFields(bufferedDoc);
        }
        if (!onlyStatic) {
            computeDynamicFields(bufferedDoc);
        }
        return bufferedDoc;
    }

    /**
     * 从缓存中获取审批单据
     *
     * @param bufferedDoc
     * @return
     */
    private PartPurchasePlans getPartPurchasePlan(
            MobileDocumentBuffered bufferedDoc) {
        PartPurchasePlans bill;
        if (bufferedDoc.getRelatedObjects() == null || bufferedDoc.getRelatedObjects().length < 1)
            throw new ServiceException("未找销售合同变更单，单号：" + bufferedDoc.getBusiBillId());
        try {
            bill = (PartPurchasePlans) (bufferedDoc.getRelatedObjects()[0]);
        } catch (Exception e) {
            throw new ServiceException("转成审批流程对象失败，单号：" + bufferedDoc.getBusiBillId());
        }
        return bill;
    }


    @Override
    public void computeStaticFields(MobileDocumentBuffered doc) {
        if (null == doc) {
            throw new ServiceException("无法计算空文档");
        }
        ApproveDocuments<?> document = (ApproveDocuments<?>) doc.getBusiBill();
        if (document == null) {
            throw new ServiceException("无审批单据，无法计算");
        }
        if (!moduleId.equals(document.getModuleId())) {
            throw new ServiceException(document.getDocumentNo() + "此单据非配件采购计划单");
        }
        PartPurchasePlans purchasePlan = getPartPurchasePlan(doc);
        if (purchasePlan == null) {
            throw new ServiceException("未找到审批单号对应的配件采购计划单：" + document.getDocumentNo());
        }
        List<PartPurchasePlanDetail> details = partPurchasePlanDao.getPartPurchasePlanDetailByDocumentNo(document.getDocumentNo());
        if (details == null) {
            throw new ServiceException("未找到采购计划单对应配件明细信息：" + document.getDocumentNo());
        }
        purchasePlan.setPartPurchasePlanDetails(details);
        planOrderType = getPlanOrderType();
        /**
         * buffer_version 如果buffer_version 和 buffered_doc_version 一致,直接返回
         */
        if (!this.isNeedToCompute(doc)) {
            return;
        }

        if (null != doc.getBoard() && null != doc.getBoard().getFields() && doc.getBoard().getFields().size() > 0) {
            List<MobileBoardField> fields = doc.getBoard().getFields();
            for (int i = fields.size() - 1; i >= 0; i--) {
                dao.delete(fields.get(i));
                fields.remove(i);
            }
            dao.flush();
        }

        if (null != doc.getBoard() && null != doc.getBoard().getTitles() && doc.getBoard().getTitles().size() > 0) {
            List<MobileBoardTitle> titles = doc.getBoard().getTitles();
            for (int i = titles.size() - 1; i >= 0; i--) {
                dao.delete(titles.get(i));
                titles.remove(i);
            }
            dao.flush();
        }
        doc.setBufferCalcVersion(bufferCalcVersion);
        doc.setBusiBillId(document.getDocumentId());
        doc.setBufferCalcVersion(this.getBusiBillVersion(document));
        doc.setComputeTime(new Timestamp(System.currentTimeMillis()));

        //配件采购计划审批
        MobileBoardBuffered board = doc.getBoard();
        if (null == board) {
            board = org.springframework.beans.BeanUtils.instantiate(MobileBoardBuffered.class);
            doc.setBoard(board);
            board.setDocument(doc);
        }
        String documentNo = document.getDocumentNo();
        double planPrice = Tools.toDouble(purchasePlan.getPlanPrice());
        BoardTitle baseBoardTitle = new BoardTitle();
        baseBoardTitle.setTitle(documentNo + "   " + Tools.toCurrencyStr(planPrice) + "元");
        baseBoardTitle.setCollapsable(false);
        baseBoardTitle.setDefaultExpanded(true);
        createBoardTitle(board, gson.toJson(baseBoardTitle), null, null, null);


        DocTitle docTitle = new DocTitle("配件采购计划审批");
        board.setDocTitle(gson.toJson(docTitle));

        List<MobileBoardField> fields = board.getFields();
        if (null == fields) {
            fields = new ArrayList<MobileBoardField>();
            board.setFields(fields);
        }
        short sn = 1;

        //计划单号
        double totalQuantity = 0.0;
        if (purchasePlan.getPartPurchasePlanDetails() != null) {
            for (PartPurchasePlanDetail pppd : purchasePlan.getPartPurchasePlanDetails()) {
                totalQuantity += pppd.getPlanQuantity();
            }
        }


        short PlanType = purchasePlan.getPlanType();
        Short dfPlanOrderType = purchasePlan.getDfPlanOrderType();
        String remark = purchasePlan.getRemark();

        int size = purchasePlan.getPartPurchasePlanDetails().size();

        SysStations station;
        try {
            station = dao.get(SysStations.class, purchasePlan.getStationId());
        } catch (Exception e) {
            station = null;
        }
        String stationName = "";
        if (station != null) {
            stationName = station.getStationName();
        }
        sn = createPurchasePlanProperties(board, null, sn, documentNo, PlanType, dfPlanOrderType,
                remark, planPrice, size, totalQuantity, stationName, purchasePlan.getCreator(), document.getSubmitTime());


        /**
         * 计算明细
         */

        int n = 1;
        Map<Percent, List<PartPurchasePlanDetail>> map = getPercentDetails(purchasePlan);
        MobileBoardBuffered subobjectBoard = null;
        if (map != null) {
            if (map.containsKey(Percent.HUNDRED_PERCENT)) {
                for (int i = 0; i < map.get(Percent.HUNDRED_PERCENT).size(); i++) {
                    // PartPurchasePlanDetail pppd=map.get(Percent.HUNDRED_PERCENT).get(i);
                    // sn=detailBufferCalc.createDetailField(sn, i+1, board, pppd,PartPlanType.getPartPlanType(purchasePlan.getPlanType()));
                    subobjectBoard = detailBufferCalc.createDetailFieldToSubboard(i, map.get(Percent.HUNDRED_PERCENT).get(i), PartPlanType.getPartPlanType(purchasePlan.getPlanType()));
                    createSubObjectField(board, "PartPurchasePlanDetailList", AppBoardFieldType.Subobject, sn++, subobjectBoard);
                    n++;
                }
            } else {
                List<PartPurchasePlanDetail> listDetails;
                if (map.containsKey(Percent.SIXTY_PERCENT)) {
                    listDetails = map.get(Percent.SIXTY_PERCENT);
                    subobjectBoard = detailBufferCalc.computeFields(listDetails, n, true, Percent.SIXTY_PERCENT, PartPlanType.getPartPlanType(purchasePlan.getPlanType()), planPrice);
                    createSubObjectField(board, "PartPurchasePlanDetailList", AppBoardFieldType.Subobject, sn++, subobjectBoard);
                    n++;
                }
                if (map.containsKey(Percent.FORTY_PERCENT)) {
                    listDetails = map.get(Percent.FORTY_PERCENT);
                    subobjectBoard = detailBufferCalc.computeFields(listDetails, n, true, Percent.FORTY_PERCENT, PartPlanType.getPartPlanType(purchasePlan.getPlanType()), planPrice);
                    createSubObjectField(board, "PartPurchasePlanDetailList", AppBoardFieldType.Subobject, sn++, subobjectBoard);
                    n++;
                }
                if (map.containsKey(Percent.FIFTY_PERCENT)) {
                    listDetails = map.get(Percent.FIFTY_PERCENT);
                    subobjectBoard = detailBufferCalc.computeFields(listDetails, n, true, Percent.FIFTY_PERCENT, PartPlanType.getPartPlanType(purchasePlan.getPlanType()), planPrice);
                    createSubObjectField(board, "PartPurchasePlanDetailList", AppBoardFieldType.Subobject, sn++, subobjectBoard);
                    n++;
                }
                if (map.containsKey(Percent.THIRTY_PERCENT)) {
                    listDetails = map.get(Percent.THIRTY_PERCENT);
                    subobjectBoard = detailBufferCalc.computeFields(listDetails, n, true, Percent.THIRTY_PERCENT, PartPlanType.getPartPlanType(purchasePlan.getPlanType()), planPrice);
                    createSubObjectField(board, "PartPurchasePlanDetailList", AppBoardFieldType.Subobject, sn++, subobjectBoard);
                    n++;
                }
                if (map.containsKey(Percent.TWENTY_PERCENT)) {
                    listDetails = map.get(Percent.TWENTY_PERCENT);
                    subobjectBoard = detailBufferCalc.computeFields(listDetails, n, true, Percent.TWENTY_PERCENT, PartPlanType.getPartPlanType(purchasePlan.getPlanType()), planPrice);
                    createSubObjectField(board, "PartPurchasePlanDetailList", AppBoardFieldType.Subobject, sn++, subobjectBoard);
                    n++;
                }
                //dao.save(board);
            }
        } else {
            // 无配件采购明细也
            // throw new ServiceException("无配件采购明细信息");
        }
        //logger.debug("计算完成:"+board2Json(board));
        System.out.println(board2Json(board));
        dao.flush();
        dao.update(doc);

    }

    private Map<Short, Object> getPlanOrderType() {
        Map<Short, Object> orderTypeMap = partPurchasePlanDao
                .getOrderType(DF_PART_ORDER_TYPE);
        if (orderTypeMap == null) {
            throw new ServiceException("获取订单类型数据异常！");
        }
        return orderTypeMap;
    }


    private Short createPurchasePlanProperties(MobileBoardBuffered board, Integer background, short sn,
                                               String documentNo, Short planType, Short dfPlanOrderType, String remark,
                                               Double planPrice, Integer breedNumber, Double totalQuantity, String stationName, String approverName, Timestamp submitTime) {

        if (stationName != null && stationName.length() > 0) {
            createProperty(board, "stationName", sn++, true, true, true, "采购站点",
                    stationName, null, null, null,
                    null, background);
        }

        createProperty(board, "planType", sn++, true, true, true, "计划类型",
                PartPlanType.getPartPlanType(planType).getText(), null, null,
                null, null, background);
        if (null != dfPlanOrderType) {
//			createProperty(board, "dfOrderType", sn++, true, true, true, "订单类型",
//					planOrderType.get(dfOrderType).toString(), null, null, null,
//					null, background);
            createProperty(board, "dfOrderType", sn++, true, true, true, "订单类型",
                    getDfOrderType(dfPlanOrderType, planType), null, null, null,
                    null, background);
        }

        if (planPrice >= 100) {
            createProperty(board, "planPrice", sn++, true, true, true, "采购金额",
                    Tools.toCurrencyStr(planPrice), null, null, Color.RED.getCode(), null,
                    background);
        } else {
            createProperty(board, "planPrice", sn++, true, true, true, "采购金额",
                    String.valueOf(planPrice), null, null, Color.RED.getCode(), null,
                    background);
        }

        createProperty(board, "breedNumber", sn++, true, true, true, "采购品种数",
                String.valueOf(breedNumber), null, null, null, null, background);

        createProperty(board, "approverName", sn++, true, true, true, "制单人",
                String.valueOf(approverName), null, null, null, null, background);

        createProperty(board, "submitTime", sn++, true, true, true, "制单时间",
                String.valueOf(submitTime), null, null, null, null, background);


        if (null != remark && StringUtils.isNotEmpty(remark)) {
            createProperty(board, "remark", sn++, true, true, true, "备注", remark,
                    null, null, null, null, background);
        }

        return sn;
    }

    private static final String DF_PLAN_ORDER_TYPE = "df_plan_order_type";

    private static final String DF_MONITOR_ORDER_TYPE = "df_monitor_order_type";


    private String getDfOrderType(Short dfPlanOrderType, Short planType) {
        dfPlanOrderType = Tools.toShort(dfPlanOrderType);
        planType = Tools.toShort(planType);
        if (planType == PartPlanType.BaseDfOrder.getCode()) {
            Map<Short, Object> planOrderTypeMap = partPurchasePlanDao.getOrderType(DF_PLAN_ORDER_TYPE);
            return planOrderTypeMap.get(dfPlanOrderType).toString();
        } else if (planType == PartPlanType.DfRushOrder.getCode()) {
            return "紧急订单";
        } else if (planType == PartPlanType.MonitiorOrder.getCode()) {
            Map<Short, Object> monitorOrderTypeMap = partPurchasePlanDao.getOrderType(DF_MONITOR_ORDER_TYPE);
            return monitorOrderTypeMap.get(dfPlanOrderType).toString();
        }
        return "";
    }


    public Map<Percent, List<PartPurchasePlanDetail>> getPercentDetails(
            PartPurchasePlans purchasePlan) {
        Map<Percent, List<PartPurchasePlanDetail>> map = new HashMap<Percent, List<PartPurchasePlanDetail>>();

        if (purchasePlan.getPlanPrice() == null || purchasePlan.getPlanPrice() == 0) {
            return null;
        }
        List<PartPurchasePlanDetail> list = new ArrayList<PartPurchasePlanDetail>();
        list.addAll(purchasePlan.getPartPurchasePlanDetails());//复制明细信息
        Map<Percent, List<PartPurchasePlanDetail>> subList = null;
        double TotalPrice = purchasePlan.getPlanPrice();//总金额
        //配件数量小于等于10
        if (list.size() <= 10) {
            map.put(Percent.HUNDRED_PERCENT, list);
            return map;
        } else if (list.size() > 10 && list.size() <= 40) {
            subList = getSixtyAndFortyPercentDetails(TotalPrice, list, Percent.SIXTY_PERCENT);
            map.put(Percent.FORTY_PERCENT, subList.get(Percent.FORTY_PERCENT));
            map.put(Percent.SIXTY_PERCENT, subList.get(Percent.SIXTY_PERCENT));
        } else {
            subList = getFiftyAndThirtyAndTwentyPercentDetails(TotalPrice, list, Percent.FIFTY_PERCENT);
            map.put(Percent.FIFTY_PERCENT, subList.get(Percent.FIFTY_PERCENT));
            if (null != subList.get(Percent.SUBLIST)) {
                subList = getFiftyAndThirtyAndTwentyPercentDetails(TotalPrice, subList.get(Percent.SUBLIST), Percent.THIRTY_PERCENT);
                if (subList.get(Percent.SUBLIST) == null) {
                    map.put(Percent.TWENTY_PERCENT, subList.get(Percent.LISTDETAL));
                } else {
                    map.put(Percent.THIRTY_PERCENT, subList.get(Percent.THIRTY_PERCENT));
                    map.put(Percent.TWENTY_PERCENT,
                            list.subList(map.get(Percent.FIFTY_PERCENT).size() + map.get(Percent.THIRTY_PERCENT).size(),
                                    list.size()));
                }
            }
        }
        return map;
    }

    /*配件个数大于40*/
    private Map<Percent, List<PartPurchasePlanDetail>> getFiftyAndThirtyAndTwentyPercentDetails(
            double totalPrice, List<PartPurchasePlanDetail> subList,
            Percent percent) {
        if (subList == null || subList.size() == 0) {
            return null;
        }
        List<PartPurchasePlanDetail> listDetail = new ArrayList<PartPurchasePlanDetail>();
        List<PartPurchasePlanDetail> subDetails = null;
        Map<Percent, List<PartPurchasePlanDetail>> map = new HashMap<Percent, List<PartPurchasePlanDetail>>();
        double totalCost = 0.0;
        int n = 0;
        if (percent == Percent.FIFTY_PERCENT) {
            n = 45;
        } else if (percent == Percent.THIRTY_PERCENT) {
            n = 25;
        }
        for (int i = 0; i < subList.size(); i++) {
            double cost = subList.get(i).getCost() == null ? 0 : subList.get(i).getCost();
            totalCost += cost;
            listDetail.add(subList.get(i));
            if (Tools.toDouble(totalCost / totalPrice * 100) >= n) {
                subDetails = subList.subList(i + 1, subList.size());
                break;
            }
        }
        map.put(percent, listDetail);
        map.put(Percent.SUBLIST, subDetails);
        return map;
    }

    /*大于10,小于40个配件*/
    public Map<Percent, List<PartPurchasePlanDetail>> getSixtyAndFortyPercentDetails(
            double totalPrice, List<PartPurchasePlanDetail> subList, Percent percent) {
        if (subList == null || subList.size() == 0) {
            return null;
        }
        List<PartPurchasePlanDetail> listDetail = new ArrayList<PartPurchasePlanDetail>();
        List<PartPurchasePlanDetail> subDetails = null;
        Map<Percent, List<PartPurchasePlanDetail>> map = new HashMap<Percent, List<PartPurchasePlanDetail>>();
        double totalCost = 0.0;
        for (int i = 0; i < subList.size(); i++) {
            double cost = subList.get(i).getCost() == null ? 0 : subList.get(i).getCost();
            totalCost += cost;
            listDetail.add(subList.get(i));
            if (percent == Percent.SIXTY_PERCENT && Tools.toDouble(totalCost / totalPrice * 100) >= 55) {
                map.put(Percent.SIXTY_PERCENT, listDetail);
                subDetails = subList.subList(i + 1, subList.size());
                map.put(Percent.FORTY_PERCENT, subDetails);
                return map;
            }
        }
        return null;
    }


    @Override
    protected void computeDynamicFields(MobileDocumentBuffered doc) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getBufferCalcVersion() {
        return bufferCalcVersion;
    }

    @Override
    public String getBusiBillVersion(MobileDocumentBuffered doc) {
        return getBusiBillVersion(this.getApproveDocument(doc));
    }

    @Override
    public String getBusiBillVersion(ApproveDocuments<?> approveDoc) {
        if (null == approveDoc || null == approveDoc.getSubmitTime()) {
            throw new ServiceException("审批流程对象是空，不可以获得版本号。");
        } else {
            return sdf.format(approveDoc.getSubmitTime());
        }
    }


    @Override
    public ApproveDocuments<?> getApproveDocument(MobileDocumentBuffered doc) {
        ApproveDocuments<?> approveDoc;
        if (doc.getBusiBill() == null)
            throw new ServiceException("未找审批单，单号：" + doc.getBusiBillId());
        try {
            approveDoc = (ApproveDocuments<?>) (doc.getBusiBill());
        } catch (Exception e) {
            throw new ServiceException("转成审批流程对象失败，单号：" + doc.getBusiBillId());
        }
        return approveDoc;
    }

    public static enum Percent {
        HUNDRED_PERCENT,
        SIXTY_PERCENT,
        FIFTY_PERCENT,
        FORTY_PERCENT,
        THIRTY_PERCENT,
        TWENTY_PERCENT,
        LISTDETAL,
        SUBLIST,
        PERCENT_VALUE
    }

}
