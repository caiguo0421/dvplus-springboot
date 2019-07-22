package cn.sf_soft.office.approval.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;

import cn.sf_soft.office.approval.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.dao.VehicleConversionDao;
import cn.sf_soft.office.approval.dao.VehicleConversionModifyItemDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.SysOptions;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;

/**
 * 车辆改装
 *
 * @author caigx
 */
@Service("vehicleConversionService")
public class VehicleConversionService extends BaseApproveProcess {
    /**
     * 金额的标准输出格式
     */
    private static DecimalFormat currencyFormat = new DecimalFormat(",###.00");

    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "10703020";

    @Autowired
    private VehicleConversionDao vehicleConversionDao;

    @Autowired
    private VehicleConversionModifyItemDao conversionModifyItemDao;

    @Autowired
    private SysOptionsDao sysOptionsDao;

    private static final String VEHICLE_CONVERSION_PRICE_CONFIRM = "VEHICLE_CONVERSION_PRICE_CONFIRM";

    private static final String SALE_DOCUMENT_TYPE = "车辆-销售合同";
    private static final String SALE_DOCUMENT_TYPE_VARY = "车辆-销售合同变更";

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveDocuments getSubmitRecordDetail(String documentNo) {
        List<VehicleConversion> list = vehicleConversionDao.getConversionsByDocumentNo(documentNo);
        if (list == null) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        return vehicleConversionDao.getConversionMasterByDocumentNo(documentNo);
    }

    private ApproveDocuments getDocumentDetailOne(VehicleConversion conversion) {

        // 改装性质
        conversion.setIsExistsMeaning(Tools.toBoolean(conversion.getIsExists()) ? "合同改装" : "库存改装");

//		//remark中增加状态
//		Iterator<VehicleConversionDetail> it = conversion.getChargeDetail().iterator();
//		while(it.hasNext()){
//			VehicleConversionDetail detail = it.next();
//			String statusMeaning = "";
//			Short status = detail.getStatus();
////			0	未审批
////			1	已审批
////			2	已确认
////			3	已终止
////			4	已作废
////			30	待确认
////			40	待审批
////			50	待改装
//			if(status ==(short)0){
//				statusMeaning = "未审批";
//			}else if(status ==(short)1){
//				statusMeaning = "已审批";
//			}else if(status ==(short)2){
//				statusMeaning = "已审批";
//			}else if(status ==(short)3){
//				statusMeaning = "已终止";
//			}else if(status ==(short)4){
//				statusMeaning = "已作废";
//			}else if(status ==(short)30){
//				statusMeaning = "待确认";
//			}else if(status ==(short)40){
//				statusMeaning = "待审批";
//			}else if(status ==(short)50){
//				statusMeaning = "待改装";
//			}else{
//
//			}
//
//			if(StringUtils.isNotEmpty(statusMeaning)){
//				if(StringUtils.isEmpty(detail.getRemark())){
//					detail.setRemark("【"+statusMeaning+"】");
//				}else{
//					detail.setRemark("【"+statusMeaning+"】"+detail.getRemark());
//				}
//			}
//		}

        // 加装
        List<VehicleSuperstructureRemoveAndInstalls> installItems = vehicleConversionDao
                .getSuperstructureByInstallType(conversion, "10");// 10--加装
        double totalInstallAmount = 0.00D;
        if (installItems != null && installItems.size() > 0) {
            for (VehicleSuperstructureRemoveAndInstalls installItem : installItems) {
                totalInstallAmount += Tools.toDouble(installItem.getItemCost())
                        * (installItem.getQuantity() == null ? 0 : installItem.getQuantity());
            }
            conversion.setInstallItems(installItems);
        }
        conversion.setTotalInstallAmount(totalInstallAmount);

        // 拆装
        List<VehicleSuperstructureRemoveAndInstalls> removeItems = vehicleConversionDao.getSuperstructureByInstallType(
                conversion, "20");// 20--拆装
        double totalRemoveAmount = 0.00D;
        if (removeItems != null && removeItems.size() > 0) {
            for (VehicleSuperstructureRemoveAndInstalls removeItem : removeItems) {
                totalRemoveAmount += Tools.toDouble(removeItem.getItemCost())
                        * (removeItem.getQuantity() == null ? 0 : removeItem.getQuantity());
            }
            conversion.setRemoveItems(removeItems);
        }
        conversion.setTotalRemoveAmount(totalRemoveAmount);
        return conversion;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveDocuments getDocumentDetail(String documentNo) {
        List<VehicleConversion> conversions = vehicleConversionDao.getConversionsByDocumentNo(documentNo);
        if (conversions == null) {
            logger.error(documentNo + "没有找到车辆改装明细");
            throw new ServiceException("没有找到车辆改装明细");
        }
        /*if (conversions.size() == 1) {
            return getDocumentDetailOne(conversions.get(0));
        }*/
        for (VehicleConversion conversion : conversions) {
            getDocumentDetailOne(conversion);
        }
        VehicleConversionMaster master = vehicleConversionDao.getConversionMasterByDocumentNo(documentNo);
        master.setConversions(conversions);
        return master;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
        List<VehicleConversion> conversions = vehicleConversionDao.getConversionsByDocumentNo(approveDocument.getDocumentNo());
        if (conversions == null || conversions.size() == 0) {
            logger.error(approveDocument.getDocumentNo() + "没有找到车辆改装明细");
            throw new ServiceException("没有找到车辆改装明细");
        }
        ApproveResultCode result = null;
        for (VehicleConversion conversion : conversions) {
            result = checkDataOne(conversion, approveStatus);
            if (result != ApproveResultCode.APPROVE_DATA_CHECKED_PASS) {
                break;
            }
        }
        return result;
    }

    public ApproveResultCode checkDataOne(VehicleConversion conversion, ApproveStatus approveStatus) {
        List<Map<String, Object>> otherConversions = vehicleConversionDao.getOtherConversionsByVin(
                conversion.getConversionNo(), conversion.getVehicleVin());
        if (otherConversions != null && otherConversions.size() > 0) {
            throw new ServiceException("该车的改装项目已在其它改装单上 (" + otherConversions.get(0).get("conversion_no") + ") 已审批或已确认");
        }

//		Iterator<VehicleConversionDetail> it = conversion.getChargeDetail().iterator();
//		while (it.hasNext()) {
//			VehicleConversionDetail detail = it.next();
//			if (!StringUtils.isEmpty(detail.getInDetailId())) {
//				List<Map<String, Object>> contractItems = vehicleConversionDao.getSaleContractItems(detail);
//				if (contractItems == null || contractItems.size() == 0) {
//					List<VehicleConversionModifyItem> modifyItems = conversionModifyItemDao.getModifyItemsByVsccId(conversion.getConversionNo(), detail.getInDetailId(), (short) 20);// 新增的记录
//					if (modifyItems == null || modifyItems.size() == 0) {
//						throw new ServiceException("销售合同中找不到改装项目" + detail.getItemName());
//					}
//				}
//			}
//		}

        if (Tools.toBoolean(conversion.getIsExists())) {//合同改装下才做以下校验
            List<VehicleSaleContractItem> oriItems = vehicleConversionDao.getContractItemsByVehicleId(conversion.getVehicleId());
            List<VehicleConversionModifyItem> modifyItems = conversionModifyItemDao.getModifyItemsByConversionNo(conversion.getConversionNo());

            for (VehicleConversionModifyItem modifyItem : modifyItems) {
                if (modifyItem.getVaryType() != (short) 20) {//非新增都要验证
                    if (findContractItemByVsccId(oriItems, modifyItem.getVsccId()) == null) {
                        throw new ServiceException("销售合同中找不到改装项目" + modifyItem.getItemName());
                    }
                }
            }

            //校验改装单明细
            Iterator<VehicleConversionDetail> it = conversion.getChargeDetail().iterator();
            while (it.hasNext()) {
                VehicleConversionDetail detail = it.next();
                if (findContractItemByVsccId(oriItems, detail.getSaleContractItemId()) == null) {//如果原销售合同找不到当前项目
                    List<VehicleConversionModifyItem> modifyItemsByVsccId = conversionModifyItemDao.getModifyItemsByVsccId(conversion.getConversionNo(), detail.getSaleContractItemId());
                    if (modifyItemsByVsccId != null && modifyItemsByVsccId.size() > 0) {
                        if (modifyItemsByVsccId.get(0).getVaryType() == (short) 20) {//新增则视为存在
                            continue;
                        } else {
                            throw new ServiceException("销售合同中找不到改装项目" + detail.getItemName());
                        }
                    } else {
                        throw new ServiceException("销售合同中找不到改装项目" + detail.getItemName());
                    }
                }
            }
        }

        List<VehicleSuperstructureRemoveAndInstalls> vehicleInstallItems = vehicleConversionDao
                .getVehicleInstallItems(conversion);
        if (vehicleInstallItems != null && vehicleInstallItems.size() > 0) {
            for (VehicleSuperstructureRemoveAndInstalls vehicleInstallItem : vehicleInstallItems) {
                VehicleSuperstructureStocks superstructureStock = dao.get(VehicleSuperstructureStocks.class,
                        vehicleInstallItem.getItemId());
                if (superstructureStock == null) {
                    throw new ServiceException("库存中找不到加装的上装项目" + vehicleInstallItem.getItemName());
                } else {
                    if (!"10".equals(String.valueOf(superstructureStock.getStatus()))) {
                        throw new ServiceException("上装 " + superstructureStock.getSuperstructureNo() + "库存状态不正确");
                    }

                    if (!"10".equals(String.valueOf(superstructureStock.getInstallStatus()))) {
                        throw new ServiceException("上装 " + superstructureStock.getSuperstructureNo() + "处于待加装或已加装状态");
                    }
                }
            }
        }
        return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    // 最后一步审批
    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        List<VehicleConversion> conversions = vehicleConversionDao.getConversionsByDocumentNo(approveDocument.getDocumentNo());
        if (conversions == null || conversions.size() == 0) {
            logger.error(approveDocument.getDocumentNo() + "没有找到车辆改装明细");
            throw new ServiceException("没有找到车辆改装明细");
        }

        for (VehicleConversion conversion : conversions) {
            onLastApproveLevelOne(conversion, comment);
        }

        VehicleConversionMaster master = vehicleConversionDao.getConversionMasterByDocumentNo(approveDocument.getDocumentNo());
        //自动关联销售合同改装项目
        this.autoBuildItemRelation(master);

        return super.onLastApproveLevel(master, comment);
    }

    public void onLastApproveLevelOne(VehicleConversion conversion, String comment) {
        SysUsers user = HttpSessionStore.getSessionUser();

        // 加装
        List<VehicleSuperstructureRemoveAndInstalls> installItems = vehicleConversionDao
                .getSuperstructureByInstallType(conversion, "10");// 10--加装
        if (installItems != null && installItems.size() > 0) {
            for (VehicleSuperstructureRemoveAndInstalls installItem : installItems) {
                installItem.setStatus((short) 1);
                dao.update(installItem);// 保存加装的状态
            }
        }
        // 拆装
        List<VehicleSuperstructureRemoveAndInstalls> removeItems = vehicleConversionDao.getSuperstructureByInstallType(
                conversion, "20");// 20--拆装
        if (removeItems != null && removeItems.size() > 0) {
            for (VehicleSuperstructureRemoveAndInstalls removeItem : removeItems) {
                removeItem.setStatus((short) 1);
                dao.update(removeItem);// 保存加装的状态
            }
        }

        // 上装的加装项目
        List<VehicleSuperstructureRemoveAndInstalls> vehicleInstallItems = vehicleConversionDao
                .getVehicleInstallItems(conversion);
        if (vehicleInstallItems != null && vehicleInstallItems.size() > 0) {
            for (VehicleSuperstructureRemoveAndInstalls vehicleInstallItem : vehicleInstallItems) {
                VehicleSuperstructureStocks superstructureStock = dao.get(VehicleSuperstructureStocks.class,
                        vehicleInstallItem.getItemId()); // 上装项目的库存
                if (superstructureStock != null) {
                    superstructureStock.setInstallStatus((short) 20); // 修改状态
                    dao.update(superstructureStock);
                }
            }
        }

        double incomeDiff = getIncomeDiff(conversion); // 同步vehicle_conversion_modify_item，获得收入差额
        updateVSD(conversion, incomeDiff);// 更新销售合同明细的改装成本

        boolean isSecondhand = Tools.toBoolean(conversion.getIsSecondhand());
        if (isSecondhand) { // 二手车
            List<SecondhandVehicleStocks> secondStocks = vehicleConversionDao.getSecondhandVehicleStocksByVin(conversion.getVehicleVin());
            if (secondStocks != null && secondStocks.size() > 0) {
                SecondhandVehicleStocks stock = secondStocks.get(0);
                if ("50".equals(stock.getStatus())) {
                    stock.setConversionStatus((short) 1);
                    dao.update(stock);
                }
            }
        } else {
            List<VehicleStocks> stocks = vehicleConversionDao.getVehicleStocksByVin(conversion.getVehicleVin());
            if (stocks != null && stocks.size() > 0) {
                VehicleStocks stock = stocks.get(0);
                stock.setConversionStatus((short) 1);
                dao.update(stock);
            }
        }


        // 单据分录处理
        dealBillDocment(conversion);

        if (!isConfirmPrice()) {
            Iterator<VehicleConversionDetail> it2 = conversion.getChargeDetail().iterator();
            while (it2.hasNext()) {
                VehicleConversionDetail detail = it2.next();
                if ("00".equals(detail.getSupplierId())) {
                    detail.setConfirmerPrice(user.getUnitName());
                    detail.setConfirmPriceDate(new Timestamp(new Date().getTime()));
                    detail.setStatus((short) 50);

                    dao.update(detail);
                }
            }
        }


        // 处理销售合同分录
        dealVSCBillDocment(conversion, incomeDiff);
    }

    //自动关联销售合同改装项目
    private void autoBuildItemRelation(VehicleConversionMaster master) {
        //非库存车改装跳过
        if (master == null || StringUtils.isNotEmpty(master.getSaleContractNo())) {
            return;
        }

        List<VehicleConversion> conversions = vehicleConversionDao.getConversionsByDocumentNo(master.getDocumentNo());

        if (conversions == null || conversions.size() == 0) {
            return;
        }

        for (VehicleConversion conversion : conversions) {

            List<VehicleConversionDetail> conversionDetailList = (List<VehicleConversionDetail>) dao.findByHql("FROM VehicleConversionDetail WHERE conversionNo = ?", conversion.getConversionNo());
            if (conversionDetailList == null || conversionDetailList.size() == 0) {
                continue;
            }
            for (VehicleConversionDetail conversionDetail : conversionDetailList) {
                if (StringUtils.isEmpty(conversionDetail.getVehicleId())) {
                    continue;
                }

                String sql = "SELECT a.sale_contract_item_id,b.contract_detail_id,b.vehicle_id,a.item_id\n" +
                        "FROM dbo.vehicle_sale_contract_item a\n" +
                        "INNER JOIN dbo.vehicle_sale_contract_detail b ON b.contract_detail_id = a.contract_detail_id\n" +
                        "INNER JOIN dbo.vehicle_sale_contracts c ON b.contract_no=b.contract_no\n" +
                        "WHERE b.approve_status<>30 AND c.status<=50 AND b.vehicle_id = :vehicleId AND a.item_id = :itemId";
                Map<String, Object> params = new HashMap<>(1);
                params.put("vehicleId", conversionDetail.getVehicleId());
                params.put("itemId", conversionDetail.getItemId());
                List<Map<String, Object>> results = dao.getMapBySQL(sql, params);

                if (results != null && results.size() > 0) {
                    conversionDetail.setSaleContractItemId((String) results.get(0).get("sale_contract_item_id"));
                    conversionDetail.setIsAutoRelation(true);
                }
            }
        }

    }


    /**
     * 更新detail的数据 父单据，子单据的状态都是随父单据的状态
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected void updateSubDocument(ApproveDocuments parentDocument, Timestamp approveTimestamp) {
        VehicleConversionMaster master = vehicleConversionDao.getConversionMasterByDocumentNo(parentDocument.getDocumentNo());
        master.setStatus(parentDocument.getStatus());
        master.setApproverId(parentDocument.getApproverId());
        master.setApproverName(parentDocument.getApproverName());
        master.setApproverNo(parentDocument.getApproverNo());
        master.setApproveTime(approveTimestamp);
        dao.update(master);
        List<VehicleConversion> conversions = vehicleConversionDao.getConversionsByDocumentNo(parentDocument.getDocumentNo());
        if (conversions != null && conversions.size() > 0) {
            for (VehicleConversion conversion : conversions) {
                updateSubDocumentOne(conversion, parentDocument, approveTimestamp);
            }
        }
    }

    /**
     * 更新detail的数据 父单据，子单据的状态都是随父单据的状态
     */
    private void updateSubDocumentOne(VehicleConversion conversion, ApproveDocuments parentDocument, Timestamp approveTimestamp) {
        conversion.setApproverId(parentDocument.getApproverId());
        conversion.setApproverName(parentDocument.getApproverName());
        conversion.setApproverNo(parentDocument.getApproverNo());
        conversion.setApproveTime(approveTimestamp);
        conversion.setStatus(parentDocument.getStatus());
        dao.update(conversion);
    }


    // 是否 VEHICLE_CONVERSION_PRICE_CONFIRM
    private boolean isConfirmPrice() {
        boolean isConfirmPrice = false;
        SysUsers user = HttpSessionStore.getSessionUser();
        List<SysOptions> options = sysOptionsDao.getOptionsByOptionNo(VEHICLE_CONVERSION_PRICE_CONFIRM,
                user.getDefaulStationId());
        if (options != null && options.size() > 0) {
            if ("是".equals(options.get(0).getOptionValue())) {
                isConfirmPrice = true;
            }
        }
        return isConfirmPrice;
    }

    /**
     * 处理改装单的分录
     *
     * @param conversion
     */
    private void dealBillDocment(VehicleConversion conversion) {
        // 审批时产生应付单据
        String documentType = Tools.toBoolean(conversion.getIsSecondhand()) ? "二手车-车辆改装" : "车辆-车辆改装";
        Iterator<VehicleConversionDetail> it = conversion.getChargeDetail().iterator();
        while (it.hasNext()) {
            VehicleConversionDetail detail = it.next();
            if ("00".equals(detail.getSupplierId())) {// 00 表示站内改装
                detail.setStatus((short) 1);
            } else {
                detail.setStatus((short) 30);
                // 插入分录
                financeDocumentEntriesDao.insertEntryEx(conversion.getStationId(), 5, (short) 65, documentType,
                        detail.getConversionDetailId(), detail.getSupplierId(), detail.getSupplierNo(),
                        detail.getSupplierName(), (short) 70, Tools.toDouble(detail.getItemCost()),
                        detail.getVehicleVin() + "," + detail.getConversionNo() + "," + conversion.getMasterNo(), null, detail.getFuturePayDate(),
                        detail.getItemName()
                );
            }
            dao.update(detail);// 保存明细状态
        }
    }

    // 更新销售合同明细的改装成本
    @SuppressWarnings("unchecked")
    private void updateVSD(VehicleConversion conversion, double incomeDiff) {
        boolean isSecondHand = Tools.toBoolean(conversion.getIsSecondhand());
        //注意： 有先做 改装单申请（库存改装），然后在做销售合同的情况并且销售合同先审批的情况，
        //因此。不能根据 conversion的车辆合同明细ID 来查找
        List<VehicleSaleContracts> vscList = vehicleConversionDao.getVscList(conversion.getVehicleId());
        List<VehicleSaleContractDetail> vscdList = vehicleConversionDao.getVscdList(conversion.getVehicleId());
        List<VehicleSaleContractItem> oriItems = vehicleConversionDao.getContractItemsByVehicleId(conversion.getVehicleId());

        if (incomeDiff != 0.00D) {// 更新合同金额和佣金合计
            VehicleSaleContracts saleContracts = vscList.get(0);
            saleContracts.setContractMoney(Tools.toDouble(saleContracts.getContractMoney()) + incomeDiff);
            saleContracts.setCommissionAmt(Tools.toDouble(saleContracts.getCommissionAmt()) + incomeDiff);
            dao.update(saleContracts);
            //审批同意时，vehicle_sale_contract_detail表的字段conversion_income也要同步。不然会间接引起销售合同变更及统计的数据有问题
            //2016/11/2
            for (VehicleSaleContractDetail contraceDetail : vscdList) {
                contraceDetail.setConversionIncome(Tools.toDouble(contraceDetail.getConversionIncome()) + incomeDiff);
                dao.update(contraceDetail);
            }
        }

        // 2 修改合同明细的改装预估(conversionPf)
        double dVsriAmount = 0.00D;// 该车已经加装或拆装的成本之和
        if (vscdList != null && vscdList.size() > 0) {
            List<Map<String, Object>> vsriList = vehicleConversionDao.getVsriAmountByVehicleId(
                    conversion.getVehicleId(), isSecondHand);
            if (vsriList != null && vsriList.size() > 0) {
                dVsriAmount = (double) vsriList.get(0).get("vsri_amount");
            }

            for (VehicleSaleContractDetail vscd : vscdList) {
                double conversionPf = 0;
                List<String> saleContractItemIds = new ArrayList<String>();
                for (VehicleSaleContractItem itemOri : oriItems) {
                    if (itemOri.getContractDetailId() != null && itemOri.getContractDetailId().equals(vscd.getContractDetailId())) {
                        conversionPf += Tools.toDouble(itemOri.getItemCost());// 销售合同记录的改装明细之和
                        saleContractItemIds.add(itemOri.getSaleContractItemId());
                    }
                }
                //logger.debug("在合同中的改装明细合计成本："+conversionPf);

                // 不在该合同记录的其他改装之和
                double conversion_pf2 = 0.00D;
                if (saleContractItemIds.size() > 0) {
                    List<VehicleConversionDetail> conversionDetails = vehicleConversionDao.getConversionDetailsByVehicleId(conversion.getVehicleId(), isSecondHand,
                            saleContractItemIds);
                    if (conversionDetails != null) {
                        for (VehicleConversionDetail conversionDetail : conversionDetails) {
                            conversion_pf2 += Tools.toDouble(conversionDetail.getItemCost());
                        }
                    }
                }

                //logger.debug("不在合同中的改装明细合计成本："+conversion_pf2);
                conversionPf += conversion_pf2;

                Iterator<VehicleConversionDetail> it = conversion.getChargeDetail().iterator();
                while (it.hasNext()) {
                    VehicleConversionDetail detail = it.next();

                    //如果当前改装单明细在当前销售合同中存在，改装预估不处理
                    if (saleContractItemIds.contains(detail.getSaleContractItemId())) {
                        continue;
                    }
                    // 原C#代码中，有很多判断，审批的只用考虑 状态由0-->1 和 0-->30的
                    conversionPf += Tools.toDouble(detail.getItemCostOri());
                }
                //logger.debug("修改合同明细Id "+vscd.getContractDetailId()+"的预估合计：conversionPf "+conversionPf+"dVsriAmount "+dVsriAmount);
                vscd.setConversionPf(conversionPf + dVsriAmount);// 之前未审批，现在是审批的加
                vscd.setVehiclePriceTotal(Tools.toDouble(vscd.getVehiclePriceTotal()) + incomeDiff);
                SysUsers user = HttpSessionStore.getSessionUser();
                vscd.setModifier(user.getUserFullName());
                vscd.setModifyTime(new Timestamp(System.currentTimeMillis()));
                dao.update(vscd);

                //新增-消贷预算校验-2016/12/12
                List<VehicleLoanBudgetDetails> budgetDetailList = (List<VehicleLoanBudgetDetails>) dao.findByHql("from VehicleLoanBudgetDetails as a where status in (0,1) and saleContractDetailId = ?", vscd.getContractDetailId());
                for (VehicleLoanBudgetDetails budgetDetail : budgetDetailList) {
                    if (Tools.toDouble(vscd.getVehiclePriceTotal()) < Tools.toDouble(budgetDetail.getLoanAmount())) {
                        throw new ServiceException(String.format("车辆对应的合同明细的车款合计%s小于消贷预算明细的贷款金额%s", currencyFormat.format(Tools.toDouble(vscd.getVehiclePriceTotal())),
                                currencyFormat.format(Tools.toDouble(budgetDetail.getLoanAmount()))));
                    }

                    if (Tools.toDouble(vscd.getVehiclePriceTotal()) != Tools.toDouble(budgetDetail.getVehiclePriceTotal())) {
                        budgetDetail.setVehiclePriceTotal(Tools.toDouble(vscd.getVehiclePriceTotal()));
                        dao.update(budgetDetail);
                    }

                }

            }

            // 计算实际改装金额--begin
            List<Map<String, Object>> realCostList = vehicleConversionDao.getRealCost(conversion.getVehicleId(),
                    isSecondHand);
            // 当前数据库中的实际改装金额
            double dConversionCost = 0;
            if (realCostList != null && realCostList.size() > 0) {
                if (null != realCostList.get(0).get("item_cost")) {
                    dConversionCost = (double) realCostList.get(0).get("item_cost");
                }
            }
            for (VehicleSaleContractDetail saleContractDetail : vscdList) {
                saleContractDetail.setConversionCost(dConversionCost + dVsriAmount);
                dao.update(saleContractDetail);
            }
            // 计算实际改装金额--end
        }

    }

    /**
     * 根据 销售合同改装明细ID 筛选
     *
     * @param oriItems
     * @param vsccId   销售合同改装明细ID
     * @return
     */
    private VehicleSaleContractItem findContractItemByVsccId(List<VehicleSaleContractItem> oriItems, String vsccId) {
        for (VehicleSaleContractItem oriItem : oriItems) {
            if (vsccId.equals(oriItem.getSaleContractItemId())) {
                return oriItem;
            }
        }
        return null;
    }

    /**
     * 处理改装单的收入差额
     *
     * @param conversion
     * @return
     */
    private double getIncomeDiff(VehicleConversion conversion) {
        double incomeDiff = 0.00D;// 收入差值
        if (StringUtils.isEmpty(conversion.getVscdId())) {
            return incomeDiff;
        }

        List<VehicleSaleContractItem> oriItems = vehicleConversionDao.getContractItemsByVehicleId(conversion.getVehicleId());
        VehicleSaleContractDetail contractDetail = dao.get(VehicleSaleContractDetail.class, conversion.getVscdId());
        if (contractDetail == null) {
            throw new ServiceException("更新销售合同明细的改装成本出错：根据Id " + conversion.getVscdId() + "未找到销售合同明细");
        }

        // 判断当前改装单是否有修改销售合同预定项目，如果有需要同步更新
        List<VehicleConversionModifyItem> modifyItems = conversionModifyItemDao.getModifyItemsByConversionNo(conversion
                .getConversionNo());
        for (VehicleConversionModifyItem modifyItem : modifyItems) {
            short nVaryType = Tools.toShort(modifyItem.getVaryType(), (short) 20);// 修改状态,为空表示新增
            VehicleSaleContractItem contractItem = findContractItemByVsccId(oriItems, modifyItem.getVsccId());
            if (contractItem != null) {
                if (nVaryType == 10) {// 终止
                    incomeDiff = incomeDiff - Tools.toDouble(modifyItem.getIncome());
                    dao.delete(contractItem);// 终止的情况删除对应的 车辆合同外委改装
                }
                if (nVaryType == 30) {// 修改
                    incomeDiff += (Tools.toDouble(modifyItem.getIncome()) - Tools.toDouble(contractItem.getIncome()));
                    contractItem.setItemCost(Tools.toBigDecimal(modifyItem.getItemCost()));
                    contractItem.setIncome(Tools.toBigDecimal(modifyItem.getIncome()));
                    contractItem.setComment(modifyItem.getComment());
                    dao.update(contractItem);
                }
            }
            if (nVaryType == 20) {// 新增
                incomeDiff += Tools.toDouble(modifyItem.getIncome());

                VehicleSaleContractItem newContractItem = new VehicleSaleContractItem();
                newContractItem.setSaleContractItemId(modifyItem.getVsccId());
                newContractItem.setContractDetailId(modifyItem.getVscdId());
                newContractItem.setItemId(modifyItem.getItemId());
                newContractItem.setItemNo(modifyItem.getItemNo());
                newContractItem.setItemName(modifyItem.getItemName());
                newContractItem.setItemCost(Tools.toBigDecimal(modifyItem.getItemCost()));
                newContractItem.setComment(modifyItem.getComment());
                newContractItem.setIncome(Tools.toBigDecimal(modifyItem.getIncome()));
                newContractItem.setContractNo(contractDetail.getContractNo());

                dao.save(newContractItem);
            }

            modifyItem.setStatus((short) 1);
            dao.update(modifyItem);
        }
        return incomeDiff;
    }

    /**
     * 处理销售合同的单据分录
     *
     * @param conversion
     * @param incomeDiff
     */
    private void dealVSCBillDocment(VehicleConversion conversion, double incomeDiff) {
        if (StringUtils.isEmpty(conversion.getVscdId())) {// 非合同改装直接跳过不处理
            return;
        }
        if (incomeDiff == 0.00D) {
            return;
        }
        List<Map<String, Object>> contractInfoList = vehicleConversionDao.getContractInfo(conversion.getVscdId());
        if (contractInfoList == null || contractInfoList.size() == 0) {
            throw new ServiceException("处理销售合同分录出错，根据Id " + conversion.getVscdId() + "未找到销售合同明细");
        }
        String contractNo = contractInfoList.get(0).get("contract_no").toString();
        String stationId = contractInfoList.get(0).get("station_id").toString();
        String customerId = contractInfoList.get(0).get("customer_id").toString();
        String customerNo = contractInfoList.get(0).get("customer_no").toString();
        String customerName = contractInfoList.get(0).get("customer_name").toString();
        //double firstAmount = (double) contractInfoList.get(0).get("frist_amount") + incomeDiff;
        //注意： 在328行中，setContractMoney已经增加了incomeDiff，这里不用再重复增加
        double firstAmount = (double) contractInfoList.get(0).get("frist_amount");
        logger.debug(String.format("车辆改装：%s,firstAmount:%f,incomeDiff:%f", conversion.getDocumentNo(), firstAmount, incomeDiff));
        Timestamp arTime = (Timestamp) contractInfoList.get(0).get("plan_deliver_time");
        if (arTime == null) {
            arTime = new Timestamp(System.currentTimeMillis());
        }
        FinanceDocumentEntries contractEntry = null; // 车辆-销售合同分录
        FinanceDocumentEntries contractEntryVary = null; // 车辆-销售合同变更分录

        List<FinanceDocumentEntries> contractEntries = vehicleConversionDao.getDocumentEntriesByType(
                conversion.getVscdId(), SALE_DOCUMENT_TYPE);
        List<FinanceDocumentEntries> contractEntriesVary = vehicleConversionDao.getDocumentEntriesByType(
                conversion.getVscdId(), SALE_DOCUMENT_TYPE_VARY);
        List<FinanceDocumentEntries> contractEntriesAll = vehicleConversionDao.getDocumentEntries(conversion
                .getVscdId());

        if (contractEntriesAll == null || contractEntriesAll.size() == 0) {
            financeDocumentEntriesDao.insertEntryEx(stationId, 19, (short) 15, SALE_DOCUMENT_TYPE, contractNo,
                    customerId, customerNo, customerName, (short) 20, incomeDiff, null, null, arTime);
            return;
        }

        double dDocumentAmount = 0, dLeftAmount = 0, dLeftAmountCanUse = 0, dUsedCredit = 0, dPaidAmount = 0, dDocumentAmountVary = 0, dLeftAmountVary = 0, dUsedCreditVary = 0, dRequestAmount = 0;
        String sAfterNo = "", sAfterNoVary = "", sDocumentId = "", sDocumentIdVary = "";
        if (contractEntries != null && contractEntries.size() > 0) {
            contractEntry = contractEntries.get(0);
            dDocumentAmount = Tools.toDouble(contractEntry.getDocumentAmount());
            dLeftAmount = Tools.toDouble(contractEntry.getLeftAmount());
            dUsedCredit = Tools.toDouble(contractEntry.getUsedCredit());
            dPaidAmount = dDocumentAmount - dLeftAmount + dUsedCredit;// 实际收款(含授信)
            logger.debug(String.format("车辆改装：%s,dPaidAmount(before):%f", conversion.getDocumentNo(), dPaidAmount));
            if (dPaidAmount > dDocumentAmount) {
                dPaidAmount = dDocumentAmount;
            }
            dLeftAmountCanUse = dDocumentAmount - dPaidAmount;// 合同金额变小要时，判断是否足于处理变化量时需用此金额
            logger.debug(String.format("车辆改装：%s,dPaidAmount(after):%f,dLeftAmountCanUse:%f", conversion.getDocumentNo(), dPaidAmount, dLeftAmountCanUse));
            sAfterNo = contractEntry.getAfterNo() + "";
            sDocumentId = contractEntry.getDocumentId() + "";
            logger.debug(String.format("车辆改装：%s,关联销售合同分录：%s", conversion.getDocumentNo(), contractEntry.toString()));

        }

        if (contractEntriesVary != null && contractEntriesVary.size() > 0) {
            contractEntryVary = contractEntriesVary.get(0);
            dDocumentAmountVary = Tools.toDouble(contractEntryVary.getDocumentAmount());
            dLeftAmountVary = Tools.toDouble(contractEntryVary.getLeftAmount());
            dUsedCreditVary = Tools.toDouble(contractEntryVary.getUsedCredit());
            sAfterNoVary = contractEntryVary.getAccountId() + "";
            sDocumentIdVary = contractEntryVary.getDocumentId() + "";

            List<Map<String, Object>> resultList = vehicleConversionDao.getRequestAmountByEntryId(contractEntryVary
                    .getEntryId());
            if (resultList != null && resultList.size() > 0) {
                if (StringUtils.isNotEmpty(resultList.get(0).get("request_amount") + "")) {
                    dRequestAmount = Tools.toDouble((double) resultList.get(0).get("request_amount"));// 请款金额
                }
            }
            logger.debug(String.format("车辆改装：%s,关联销售合同-变更分录：%s", conversion.getDocumentNo(), contractEntriesVary.toString()));
            logger.debug(String.format("车辆改装：%s,dRequestAmount：%f", conversion.getDocumentNo(), dRequestAmount));
        }

        // 如果金额变大或不变,直接修改原单
        if (incomeDiff >= 0) {
            if (StringUtils.isEmpty(sDocumentIdVary)) {// 无变更单
                logger.debug("修改销售合同分录(前)：" + contractEntry);
                contractEntry.setDocumentAmount(dDocumentAmount + incomeDiff);
                contractEntry.setLeftAmount(dLeftAmount + incomeDiff);
                contractEntry.setArapTime(arTime);
                financeDocumentEntriesDao.update(contractEntry);
                logger.debug("修改销售合同分录(后)：" + contractEntry);

            } else {// 有变更单
                if (dDocumentAmountVary - dRequestAmount - incomeDiff > 0) {// 优先处理变更单
                    logger.debug("修改销售合同分录(前)：" + contractEntryVary);

                    contractEntryVary.setDocumentAmount(dDocumentAmountVary - incomeDiff);
                    contractEntryVary.setLeftAmount(dLeftAmountVary - incomeDiff);
                    contractEntryVary.setArapTime(arTime);
                    financeDocumentEntriesDao.update(contractEntryVary);

                    logger.debug("修改销售合同分录(后)：" + contractEntryVary);
                } else {
                    if (StringUtils.isEmpty(sAfterNoVary)) {// 有变更单但未使用（包括请款）
                        logger.debug("删除销售合同分录：" + contractEntryVary);
                        financeDocumentEntriesDao.delete(contractEntryVary);// 先删除变更的分录

                        logger.debug("修改销售合同分录(前)：" + contractEntry);
                        contractEntry.setDocumentAmount(dDocumentAmount + incomeDiff - dLeftAmountVary);
                        contractEntry.setLeftAmount(dLeftAmount + incomeDiff - dLeftAmountVary);
                        contractEntry.setArapTime(arTime);
                        financeDocumentEntriesDao.update(contractEntry);
                        logger.debug("修改销售合同分录(后)：" + contractEntry);
                    } else {
                        // 单据从document_amount变成dRequestAmount，说明单据减少了(dDocumentAmountVary-dRequestAmount)
                        logger.debug("修改销售合同分录(前)：" + contractEntryVary);
                        contractEntryVary.setDocumentAmount(dRequestAmount);
                        contractEntryVary.setLeftAmount(dLeftAmountVary - (dDocumentAmountVary - dRequestAmount));
                        contractEntryVary.setArapTime(arTime);
                        financeDocumentEntriesDao.update(contractEntryVary);
                        logger.debug("修改销售合同分录(后)：" + contractEntryVary);

                        // 【车辆-销售合同】单据增加合同变更单未处理完的那部分金额
                        logger.debug("修改销售合同分录(前)：" + contractEntry);
                        contractEntry.setDocumentAmount(dDocumentAmount + incomeDiff - (dDocumentAmountVary - dRequestAmount));
                        contractEntry.setLeftAmount(dLeftAmount + incomeDiff - (dDocumentAmountVary - dRequestAmount));
                        contractEntry.setArapTime(arTime);
                        financeDocumentEntriesDao.update(contractEntry);
                        logger.debug("修改销售合同分录(后)：" + contractEntry);
                    }
                }
            }

        } else {// 如果合同金额变小，则需要考虑是否产生预收款
            // 判断【车辆-销售合同】是否足于处理，不足于处理则在【车辆-销售合同变更】单中处理
            if (dLeftAmountCanUse + incomeDiff >= 0) {// 原单剩余可用金额足于处理
                // 全额贷款时删除
                if (dLeftAmount + incomeDiff == 0 && dDocumentAmount + incomeDiff == 0 && StringUtils.isEmpty(sAfterNo)) {

                    logger.debug("删除销售合同分录：" + contractEntry);
                    financeDocumentEntriesDao.delete(contractEntry);
                } else {

                    logger.debug("修改销售合同分录(前)：" + contractEntry);
                    contractEntry.setDocumentAmount(dDocumentAmount + incomeDiff);
                    contractEntry.setLeftAmount(dLeftAmount + incomeDiff);
                    contractEntry.setArapTime(arTime);
                    financeDocumentEntriesDao.update(contractEntry);
                    logger.debug("修改销售合同分录(后)：" + contractEntry);
                }
                return;
            } else {// 如果已多收，需要产生变更单
                //dPaidAmount - (dDocumentAmount - dLeftAmount) 表示未恢复的授信，授信也要当成已收款算，但实际仍可收款

                logger.debug("修改销售合同分录(前)：" + contractEntry);
                contractEntry.setDocumentAmount(dPaidAmount);
                contractEntry.setLeftAmount(dPaidAmount - (dDocumentAmount - dLeftAmount));
                contractEntry.setArapTime(arTime);
                financeDocumentEntriesDao.update(contractEntry);
                logger.debug("修改销售合同分录(后)：" + contractEntry);

                if (StringUtils.isEmpty(sDocumentIdVary)) {// 如果原来无变更单，则生成
                    financeDocumentEntriesDao.insertEntryEx(stationId, 17, (short) 70, SALE_DOCUMENT_TYPE_VARY, contractNo,
                            customerId, customerNo, customerName, (short) 10, dPaidAmount - firstAmount, null, null, arTime);
                    //logger.debug("新增销售合同分录："+resultEntry);

                } else {// 如果原来已经有变更单，则更新
                    logger.debug("修改销售合同分录(前)：" + contractEntryVary);
                    contractEntryVary.setDocumentAmount(dPaidAmount - firstAmount);
                    contractEntryVary.setLeftAmount((dPaidAmount - firstAmount) - (dDocumentAmountVary - dLeftAmountVary));
                    contractEntryVary.setArapTime(arTime);
                    financeDocumentEntriesDao.update(contractEntryVary);
                    logger.debug("修改销售合同分录(后)：" + contractEntryVary);
                }
            }
        }

    }

}
