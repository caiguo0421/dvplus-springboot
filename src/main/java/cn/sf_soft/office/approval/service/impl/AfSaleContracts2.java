package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.*;
import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.office.approval.dao.BaseRelatedObjectsDao;
import cn.sf_soft.office.approval.dao.SaleContractsDao;
import cn.sf_soft.office.approval.dao.VehicleInvoicesDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractDetailGroups;
import cn.sf_soft.vehicle.contract.service.impl.SaleContractService;
import cn.sf_soft.vehicle.contract.service.impl.VehicleSaleContractDetailService;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import cn.sf_soft.vehicle.customer.service.impl.InterestedCustomersService;
import cn.sf_soft.vehicle.loan.model.VwVehicleLoanBudgetCharge;
import cn.sf_soft.vehicle.out.model.VehicleOutStocks;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * 车辆销售合同
 *
 * @author caigx
 * @date 2016.5.9
 */
@Service("afSaleContracts2")
public class AfSaleContracts2 extends BaseApproveProcess {
    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "10202020";

    private static final String LOWEST_PROFIT = "10152223"; // 低于最低利润审批

    java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");

    @Autowired
    private SaleContractService saleContractService1;

    @Autowired
    private VehicleSaleContractDetailService vehicleSaleContractDetailService;

    @Autowired
    private SaleContractsDao saleContractsDao;

    @Autowired
    private BaseRelatedObjectsDao baseRelatedObjectsDao;

    @Autowired
    private VehicleInvoicesDao vehicleInvoicesDao;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private SysOptionsDao sysOptionsDao;


    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveDocuments getSubmitRecordDetail(String documentNo) {
        return saleContractsDao.getContractByContractNo(documentNo);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveDocuments getDocumentDetail(String documentNo) {
        VehicleSaleContracts contract = saleContractsDao.getContractByContractNo(documentNo);
        //手机端合同详情用的是合同里面的明细接口，因此此接口只返回主表数据即可，提高系统的效率
//        List<VwVehicleSaleContractDetail> vwVehicleSaleContractDetails = (List<VwVehicleSaleContractDetail>) baseDao.findByHql("FROM VwVehicleSaleContractDetail where contractNo = ?", contract.getContractNo());
//        if (vwVehicleSaleContractDetails == null) {
//            logger.error(documentNo + "没有找到车辆销售明细");
//            throw new ServiceException("没有找到车辆销售审批明细");
//        }
//        List<VehicleSaleContractDetail> details = new ArrayList<>();
//        for (VwVehicleSaleContractDetail vwDetail : vwVehicleSaleContractDetails) {
//            VehicleSaleContractDetail detail = baseDao.get(VehicleSaleContractDetail.class, vwDetail.getContractDetailId());
//            getDocumentDetailOne(contract, detail, vwDetail);
//            details.add(detail);
//        }
//        contract.setDetails(details);
        return contract;
    }


    /**
     * 提交时审判点条件判断按sql判断，需重写此方法
     *
     * @param documentNo
     * @return
     */
    @Override
    public String getConditionSql(String documentNo) {
        return "SELECT * FROM vw_vehicle_sale_contracts  WHERE contract_no ='" + documentNo + "'";
    }


    @Override
    public void revokingRecord(String documentNo, String modifyTime) {
        VehicleSaleContracts contracts = baseDao.get(VehicleSaleContracts.class, documentNo);
        if (contracts == null) {
            throw new ServiceException("未找到合同信息");
        }

        if (contracts.getStatus() >= 40) {
            throw new ServiceException("撤销失败:合同处于不可撤销状态");
        }

        //审批中的撤销只修改审批状态，其他情况下撤销相当于作废
        if (Tools.toShort(contracts.getStatus(), (short) 10) > 10 && Tools.toShort(contracts.getStatus(), (short) 10) < 50) {
            List<VehicleSaleContractDetail> detailList = (List<VehicleSaleContractDetail>) baseDao.findByHql("FROM VehicleSaleContractDetail WHERE contractNo = ? ", documentNo);
            for (VehicleSaleContractDetail detail : detailList) {
                //不对已审明细撤销
                if (detail.getApproveStatus() != 1) {
                    detail.setApproveStatus((short) 0);
                    detail.setStatus((short) 10);
                }
            }
        } else {
            //作废操作
            doForbiddingRecord(contracts, "撤销");
        }


        //撤销的状态处理
        // 如果单据在'不同意'或'制单中'状态时撤销，则置为'已撤销'状态；
        // 否则在'已提交'或'审批中'状态时撤销，则打回'制单中'状态
        short bytStatus = Tools.toShort(contracts.getStatus());
        contracts.setStatus(bytStatus < 20 ? (short) 70 : (short) 10);

        //车辆销售合同保存，审批同意（包括提交自审）、作废、和撤销 成功后调用下存储过程 pro_vehicle_data_vary
        baseDao.flush();
        saleContractService1.executeProVehicleDataVary(contracts.getContractNo());
    }


    @Override
    public void forbiddingRecord(String documentNo, String modifyTime) {
        VehicleSaleContracts contracts = baseDao.get(VehicleSaleContracts.class, documentNo);

        doForbiddingRecord(contracts, "作废");
        //作废的状态处理
        contracts.setStatus((short) 80);

        //车辆销售合同保存，审批同意（包括提交自审）、作废、和撤销 成功后调用下存储过程 pro_vehicle_data_vary
        baseDao.flush();
        saleContractService1.executeProVehicleDataVary(contracts.getContractNo());
    }

    @Override
    public Object getReturnDataWithRevoke(String documentNo) {
        baseDao.flush();
        return saleContractService1.convertReturnData(documentNo);
    }

    @Override
    public Object getReturnDataWithForbid(String documentNo) {
        baseDao.flush();
        return saleContractService1.convertReturnData(documentNo);
    }

    private String action = "";

    private void doForbiddingRecord(VehicleSaleContracts contracts, String action) {
        if (contracts == null) {
            throw new ServiceException("未找到合同信息");
        }
        this.action = action;
        List<VehicleSaleContractDetailGroups> detailGroupsList = (List<VehicleSaleContractDetailGroups>) baseDao.findByHql("FROM VehicleSaleContractDetailGroups WHERE contractNo = ? ", contracts.getContractNo());
        for (VehicleSaleContractDetailGroups detailGroup : detailGroupsList) {
            checkDetailGroup(detailGroup);
        }
        //处理意向客户跟踪结果和跟踪明细信息
        if (StringUtils.isNotEmpty(contracts.getVisitorNo())) {
//            saleContractService1.dealVisitor(false, contracts.getVisitorNo());
            saleContractService1.clearVisitor(contracts.getVisitorNo());
        }
        //处理单据分录
        List<FinanceDocumentEntries> entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId LIKE ? AND documentType = '车辆-销售合同'", contracts.getContractNo() + "%");
        if (entriesList != null && entriesList.size() > 0) {
            FinanceDocumentEntries entries = entriesList.get(0);
            if (StringUtils.isNotEmpty(entries.getAfterNo())) {
                //如果存在已收款，产生预收款
                double dLeftAmount = Tools.toDouble(entries.getLeftAmount());
                double dDocumentAmount = Tools.toDouble(entries.getDocumentAmount());
                double dUsedCredit = Tools.toDouble(entries.getUsedCredit());
                double dPaid = dDocumentAmount - dLeftAmount + dUsedCredit;
                if (dPaid > dDocumentAmount) {
                    dPaid = dDocumentAmount;
                }
                entries.setLeftAmount(dPaid - (dDocumentAmount - dLeftAmount));
                entries.setDocumentAmount(dPaid);

                financeDocumentEntriesDao.insertEntryEx(contracts.getStationId(), (short) 17, (short) 70, "车辆-销售合同作废", contracts.getContractNo(), contracts.getCustomerId(), contracts.getCustomerNo(), contracts.getCustomerName(), (short) 10, dPaid, null, null, new Timestamp(System.currentTimeMillis()));
            } else {
                baseDao.delete(entries);
            }
        }

        List<VehicleSaleContractDetail> detailList = (List<VehicleSaleContractDetail>) baseDao.findByHql("FROM VehicleSaleContractDetail WHERE contractNo = ? ", contracts.getContractNo());
        for (VehicleSaleContractDetail detail : detailList) {
            //处理发票
            List<VehicleInvoices> invoicesList = (List<VehicleInvoices>) baseDao.findByHql("FROM VehicleInvoices WHERE  contractDetailId = ? ", detail.getContractDetailId());
            if (invoicesList != null && invoicesList.size() > 0) {
                for (VehicleInvoices invoices : invoicesList) {
                    entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? ", invoices.getInvoicesDetailId());
                    if (entriesList != null && entriesList.size() > 0) {
                        FinanceDocumentEntries entries = entriesList.get(0);
                        if (StringUtils.isNotEmpty(entries.getAfterNo())) {
                            throw new ServiceException("存在已开票的车辆明细，不能" + action);
                        } else {
                            baseDao.delete(entries);
                        }
                    }
                }
            }
            //处理佣金
            entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? AND documentType = '车辆-客户佣金'", detail.getContractDetailId());
            if (entriesList != null && entriesList.size() > 0) {
                for (FinanceDocumentEntries entries : entriesList) {
                    baseDao.delete(entries);
                }
            }
            //更新车辆库存
            if ((Tools.toShort(detail.getApproveStatus()) == 0 || Tools.toShort(detail.getApproveStatus()) == 1 || Tools.toShort(detail.getApproveStatus()) == 20) && StringUtils.isNotEmpty(detail.getVehicleId())) {
                vehicleSaleContractDetailService.clearVehicleStocks(detail.getVehicleId());
            }
            //删除费用的单据分录
            deleteChargeDocument(contracts, action);
            //清理车辆档案
            vehicleSaleContractDetailService.clearVehicleArchiveRelation(detail);


            //终止明细
            if (Tools.toShort(detail.getApproveStatus()) == 1) {
                detail.setApproveStatus((short) 30);
                detail.setStatus((short) 80);
            } else {
                detail.setApproveStatus((short) 0);
                detail.setStatus((short) 10);
            }
        }

        //判断是否有产生保险预收款，如果有则处理
        entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentNo = ? AND documentType = '车辆-保险预收'", contracts.getContractNo());
        if (entriesList != null && entriesList.size() > 0) {
            FinanceDocumentEntries entries = entriesList.get(0);
            if (StringUtils.isNotEmpty(entries.getAfterNo())) {
                double dLeftAmount = Tools.toDouble(entries.getLeftAmount());
                double dDocumentAmount = Tools.toDouble(entries.getDocumentAmount());
                double dUsedCredit = Tools.toDouble(entries.getUsedCredit());
                double dPaid = dDocumentAmount - dLeftAmount + dUsedCredit;
                if (dPaid > dDocumentAmount) {
                    dPaid = dDocumentAmount;
                }
                entries.setLeftAmount(dPaid - (dDocumentAmount - dLeftAmount));
                entries.setDocumentAmount(dPaid);

            } else {
                baseDao.delete(entries);
            }
        }
        //ADM19040013-当销售合同作废时，合同对应的出库单（未审批）自动作废
        this.cancelOutStock(contracts);

        //处理contract_status
        SysUsers user = HttpSessionStore.getSessionUser();
        short status = ("作废".equals(action) ? (short) 3 : (short) 4);
        contracts.setContractStatus(status);
        contracts.setModifier(user.getUserFullName());
        contracts.setModifyTime(new Timestamp(System.currentTimeMillis()));
    }


    //ADM19040013-当销售合同作废时，合同对应的出库单（未审批）自动作废
    private void cancelOutStock(VehicleSaleContracts contracts) {
        if (contracts == null) {
            return;
        }
        List<VehicleOutStocks> outStocksList = (List<VehicleOutStocks>) baseDao.findByHql("FROM VehicleOutStocks where approveStatus = 0 AND contractNo = ?", contracts.getContractNo());
        if (outStocksList == null || outStocksList.size() == 0) {
            return;
        }
        for (VehicleOutStocks outStocks : outStocksList) {
            outStocks.setApproveStatus((byte) 2);
            outStocks.setComment(String.format("%s %s", StringUtils.isEmpty(outStocks.getComment()) ? "" : outStocks.getComment(), "销售合同作废，自动作废出库单"));
        }
    }

    //删除费用分录
    private void deleteChargeDocument(VehicleSaleContracts contracts, String action) {
        List<VehicleSaleContractCharge> contractChargeList = (List<VehicleSaleContractCharge>) baseDao.findByHql("FROM VehicleSaleContractCharge where contractNo = ?", contracts.getContractNo());
        if (contractChargeList != null && contractChargeList.size() > 0) {
            for (VehicleSaleContractCharge contractCharge : contractChargeList) {
                List<FinanceDocumentEntries> entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries where documentId = ? AND documentType LIKE '车辆-%'", contractCharge.getSaleContractChargeId());
                if (entriesList == null || entriesList.size() == 0) {
                    continue;
                }
                FinanceDocumentEntries documentEntries = entriesList.get(0);
                if (StringUtils.isEmpty(documentEntries.getAfterNo())) {
                    baseDao.delete(documentEntries);
                } else {
                    throw new ServiceException(String.format("单据分录【%s】，财务已处理，不能" + action, documentEntries.getDocumentType()));
                }

            }

        }

    }

    private void checkDetailGroup(VehicleSaleContractDetailGroups detailGroup) {
        List<VehicleSaleContractDetail> detailList = (List<VehicleSaleContractDetail>) baseDao.findByHql("FROM VehicleSaleContractDetail WHERE groupId = ?  AND approveStatus IN (0,1,20)", detailGroup.getGroupId());
        for (VehicleSaleContractDetail detail : detailList) {
            String sql = "SELECT a.sale_contract_detail_id FROM dbo.vehicle_loan_budget_details a\n" + "LEFT JOIN dbo.vehicle_loan_budget b ON b.document_no = a.document_no\n" + "WHERE a.status IN (0,1) AND b.status<=50 AND a.sale_contract_detail_id =:saleContractDetailId";
            Map<String, Object> params = new HashMap<>(1);
            params.put("saleContractDetailId", detail.getContractDetailId());
            List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
            if (list != null && list.size() > 0) {
                throw new ServiceException("已有明细做了消贷费用预算，不能" + this.action);
            }

            //是否做了精品出库
            List<VehicleSaleContractPresent> persentList = (List<VehicleSaleContractPresent>) baseDao.findByHql("FROM  VehicleSaleContractPresent WHERE contractDetailId = ? AND ISNULL(getQuantity,0)>0", detail.getContractDetailId());
            if (persentList != null && persentList.size() > 0) {
                throw new ServiceException("已有明细做了精品出库，不能" + this.action);
            }

            //是否做了保单
            sql = "SELECT a.sale_contract_insurance_id FROM dbo.insurance_detail a\n" + "LEFT JOIN dbo.insurance b ON a.insurance_no=b.insurance_no\n" + "WHERE b.approve_status IN (0,1) AND b.contract_detail_id  =:saleContractDetailId";
            params.clear();
            params.put("saleContractDetailId", detail.getContractDetailId());
            list = baseDao.getMapBySQL(sql, params);
            if (list != null && list.size() > 0) {
                throw new ServiceException("已有明细建立了保单，不能" + this.action);
            }
            //是否做了改装
            sql = "SELECT sale_contract_item_id FROM dbo.vehicle_conversion_detail \n" + "WHERE sale_contract_item_id IN (SELECT sale_contract_item_id \n" + "FROM dbo.vehicle_sale_contract_item WHERE contract_detail_id =:saleContractDetailId)\n" + "AND status IN (0,1,2,30,40,50)\n";
            params.clear();
            params.put("saleContractDetailId", detail.getContractDetailId());
            list = baseDao.getMapBySQL(sql, params);
            if (list != null && list.size() > 0) {
                throw new ServiceException("已有明细建立了改装申请单，不能" + this.action);
            }

            //是否做了费用报销
            List<VehicleSaleContractCharge> chargeList = (List<VehicleSaleContractCharge>) baseDao.findByHql("FROM  VehicleSaleContractCharge WHERE contractDetailId = ? AND ISNULL(costStatus,0)=1", detail.getContractDetailId());
            if (chargeList != null && chargeList.size() > 0) {
                throw new ServiceException("已有明细做了费用报销，不能" + this.action);
            }

            //佣金是否已使用
            List<FinanceDocumentEntries> entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentType='车辆-客户佣金' AND documentId = ?", detail.getContractDetailId());
            for (FinanceDocumentEntries entries : entriesList) {
                if (StringUtils.isNotEmpty(entries.getAfterNo())) {
                    throw new ServiceException("已有明细做了佣金处理，不能" + this.action);
                }
            }

            //公司赠券是否已使用
            if (Tools.toDouble(detail.getTicketOutStockAmount()) > 0) {
                throw new ServiceException("已有明细做了公司赠券处理，不能" + this.action);
            }


            //验证当前车辆是否已经出库
            if (StringUtils.isNotEmpty(detail.getVehicleId())) {
                List<VehicleStocks> stockList = (List<VehicleStocks>) baseDao.findByHql("FROM VehicleStocks WHERE vehicleId = ? AND status = 3", detail.getVehicleId());
                if (stockList != null && stockList.size() > 0) {
                    throw new ServiceException("已有明细车辆已出库，不能" + this.action);
                }
            }
        }
    }


    public ApproveDocuments getDocumentDetailOne(VehicleSaleContracts contract, VehicleSaleContractDetail detail, VwVehicleSaleContractDetail vwDetail) {
        String detailId = detail.getContractDetailId();
        detail.setCustomerName(contract.getCustomerName());
        detail.setBuyType(contract.getBuyType());
        detail.setBuyTypeName(contract.getBuyTypeName());
        detail.setSeller(contract.getSeller());
        detail.setContractComment(contract.getContractComment());
        detail.setCreator(contract.getCreator()); // 制单人
        detail.setCreateTime(contract.getCreateTime()); // 制单时间

        // 保险
        double totalInsurance = 0.00D;
        List<VehicleSaleContractInsurance> insurances = saleContractsDao.getInsurancesByDetailId(detailId);
        if (insurances != null) {
            for (VehicleSaleContractInsurance insurance : insurances) {
                //修复Double.valueOf 改成 Tools.toDouble 修复空指针bug
                totalInsurance += Tools.toDouble(insurance.getCategoryIncome());
            }
            detail.setInsurances(insurances);
        }
        detail.setTotalInsurance(totalInsurance);

        // 精品
        double totalPresentCost = 0.00D;
        List<VehicleSaleContractPresent> presents = saleContractsDao.getPresentsByDetailId(detailId);
        if (presents != null) {
            for (VehicleSaleContractPresent present : presents) {
                totalPresentCost += Tools.toDouble(present.getPosPrice()) * Tools.toDouble(present.getPlanQuantity());
            }
            detail.setPresents(presents);
        }
        detail.setTotalPresentCost(totalPresentCost);

        // 改装
        double totalItemCost = 0.00D;
        List<VehicleSaleContractItem> items = saleContractsDao.getItemsByDetailId(detailId);
        if (items != null) {
            for (VehicleSaleContractItem item : items) {
                totalItemCost += Tools.toDouble(item.getItemCost());
            }
            detail.setItems(items);
        }
        detail.setTotalItemCost(totalItemCost);

        // 其他费用
        double totalChargePf = 0.00D;
        List<VehicleSaleContractCharge> charges = saleContractsDao.getChargesByDetailId(detailId);
        if (charges != null) {
            for (VehicleSaleContractCharge charge : charges) {
                totalChargePf += Tools.toDouble(charge.getChargePf());
            }
            detail.setCharges(charges);
        }
        detail.setTotalChargePf(totalChargePf);

        // 发票
        double totalInvoiceAmount = 0.00D;
        List<VehicleInvoices> invoices = saleContractsDao.getInvoicesByDetailId(detailId);
        if (invoices != null) {
            for (VehicleInvoices invoice : invoices) {
                totalInvoiceAmount += Tools.toDouble(invoice.getInvoiceAmount());
            }
            detail.setInvoices(invoices);
        }
        detail.setTotalInvoiceAmount(totalInvoiceAmount);

        // 消贷
        List<VwVehicleLoanBudgetCharge> budgetCharges = saleContractsDao.getBudgetChargesByDetailId(detailId);
        if (budgetCharges != null) {
            detail.setBudgetCharges(budgetCharges);
        }

        // 赠品
        double totalGiftAmount = 0.00D;
        List<VehicleSaleContractGifts> gifts = saleContractsDao.getGiftsByDetailId(detailId);
        if (gifts != null) {
            for (VehicleSaleContractGifts gift : gifts) {
                totalGiftAmount += Tools.toDouble(gift.getAmount());
            }
            detail.setTotalGiftAmount(totalGiftAmount);
            detail.setGifts(gifts);
        }

        // 是否含保费，如果为空 则为false
        if (detail.getIsContainInsuranceCost() == null) {
            detail.setIsContainInsuranceCost(false);
        }

        // 增加 消贷-车辆贷款，消贷-车辆首付
        detail.setLoanAmountLv(vwDetail.getLoanAmountLv());
        // first_pay_amount_vd=vehicle_price_total-loan_amount_lv
        detail.setFirstPayAmountVd(Tools.toDouble(vwDetail.getVehiclePrice()) - Tools.toDouble(vwDetail.getLoanAmountLv()));

        // 消贷-车辆贷款，消贷-车辆首付加入到 loanFee 中
        if (20 == detail.getBuyType() && Tools.toDouble(vwDetail.getLoanAmountTot()) > 0) {
            // 如果是消贷
            List<ConsumptionLoanFee> loanFees = new ArrayList<ConsumptionLoanFee>();
            ConsumptionLoanFee loanFee = new ConsumptionLoanFee(detail.getContractDetailId());
            loanFee.setFirstPayAmountVd(detail.getFirstPayAmountVd());
            loanFee.setLoanAmountLv(detail.getLoanAmountLv());
            loanFees.add(loanFee);
            detail.setLoanFee(loanFees);
        }

        // 显示 预估利润、设定的最低利润、车辆销售价、车辆最低限价
        List<Map<String, Object>> catalogPrices = saleContractsDao.getVehiclePriceCatlog(detail.getStationId(), detail.getVnoId());
        if (catalogPrices != null && catalogPrices.size() > 0) {
            if (catalogPrices.get(0).get("profit_min") != null) {
                double minProfit = Double.parseDouble(catalogPrices.get(0).get("profit_min").toString());
                detail.setSetupProfit(minProfit); // 设定最小利润
            }

            if (catalogPrices.get(0).get("price_sale") != null) {
                double salePrice = Double.parseDouble(catalogPrices.get(0).get("price_sale").toString());
                detail.setSetupVehiclePrice(salePrice);
            }

        }

        List<VwVehicleSaleContractDetailGroups> groups = dao.getEntityBySQL("SELECT * FROM vw_vehicle_sale_contract_detail_groups WHERE group_id = '" + detail.getGroupId() + "'", VwVehicleSaleContractDetailGroups.class, null);
        if (groups != null && groups.size() > 0) {
            double incomeTot = 0;
            double costTot = 0;
            if (groups.get(0).getIncomeTot() != null) {
                incomeTot = groups.get(0).getIncomeTot().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            if (groups.get(0).getCostTot() != null) {
                costTot = groups.get(0).getCostTot().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }

            detail.setTotalProfit((incomeTot - costTot)); // 预估利润
        }


//        List<Map<String, Object>> result = dao.getMapBySQL("SELECT price_sale_min_nofd FROM dbo.vw_vehicle_sale_contract_detail_groups " + " WHERE contract_no = '" + detail.getContractNo() + "' " + " AND vehicle_vno = '" + detail.getVehicleVno() + "'", null);
//        if (result != null && result.size() > 0) {
//            if (result.get(0).containsKey("price_sale_min_nofd")) {
//                Object v = result.get(0).get("price_sale_min_nofd");
//                if (v != null) {
//                    detail.setMinSalePrice(Double.valueOf(v.toString()));
//                }
//            }
//        }
        return detail;
    }

    /**
     * 更新detail的数据 父单据，子单据的状态都是随父单据的状态
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected void updateSubDocument(ApproveDocuments parentDocument, Timestamp approveTimestamp) {
        VehicleSaleContracts contract = saleContractsDao.getContractByContractNo(parentDocument.getDocumentNo());
        contract.setStatus(parentDocument.getStatus());
        contract.setApproverId(parentDocument.getApproverId());
        contract.setApproverName(parentDocument.getApproverName());
        contract.setApproverNo(parentDocument.getApproverNo());
        contract.setApproveTime(approveTimestamp);

        //合同审批修改修改时间 -20190112
        SysUsers user = HttpSessionStore.getSessionUser();
        contract.setModifyTime(approveTimestamp);
        contract.setModifier(user.getUserFullName());
        dao.update(contract);
        List<VehicleSaleContractDetail> details = saleContractsDao.getDetailByContractsNo(parentDocument.getDocumentNo());
        if (details != null && details.size() > 0) {
            for (VehicleSaleContractDetail detail : details) {
                updateSubDocumentOne(detail, parentDocument, approveTimestamp);
            }
        }
    }

    /**
     * 更新detail的数据 父单据，子单据的状态都是随父单据的状态
     */
    private void updateSubDocumentOne(VehicleSaleContractDetail detail, ApproveDocuments parentDocument, Timestamp approveTimestamp) {
        detail.setStatus(parentDocument.getStatus());
        detail.setApproverId(parentDocument.getApproverId());
        detail.setApproverName(parentDocument.getApproverName());
        detail.setApproverNo(parentDocument.getApproverNo());
        detail.setApproveTime(approveTimestamp);
        dao.update(detail);
    }

    /**
     * 重写审批不同意的方法，增加业务逻辑
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected ApproveResult approveDisagreeByPC(ApproveDocuments approveDocument, String comment) {
        VehicleSaleContracts contract = saleContractsDao.getContractByContractNo(approveDocument.getDocumentNo());
        if (contract == null) {
            logger.error(approveDocument.getDocumentNo() + "没有找到车辆销售明细");
            throw new ServiceException("没有找到车辆销售审批明细");
        }
        // contract.setApproveStatus((short) 0);
        contract.setSubmitTime(null);
        if (!dao.update(contract)) {
            return new ApproveResult(ApproveResultCode.SYSTEM_ERROR);
        }

        List<VehicleSaleContractDetail> details = saleContractsDao.getDetailByContractsNo(approveDocument.getDocumentNo());
        for (VehicleSaleContractDetail detail : details) {
            detail.setApproveStatus((short) 0);
            detail.setSubmitTime(null);
            if (!dao.update(detail)) {
                return new ApproveResult(ApproveResultCode.SYSTEM_ERROR);
            }
        }

        return super.approveDisagreeByPC(approveDocument, comment);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        VehicleSaleContracts contract = saleContractsDao.getContractByContractNo(approveDocument.getDocumentNo());

        SysUsers user = HttpSessionStore.getSessionUser();

        // 更新客户信息
        updateCustomer(contract);
        List<VehicleSaleContractDetail> details = saleContractsDao.getDetailByContractsNo(contract.getContractNo());
        for (VehicleSaleContractDetail detail : details) {
            // 未审批或者未提交的时候都要变成已审批
            if (detail.getApproveStatus() == (short) 20 || detail.getApproveStatus() == (short) 0) {
                detail.setApproveTime(new Timestamp(System.currentTimeMillis()));
                detail.setApproverName(user.getUserName());
                detail.setApproveStatus((short) 1);
                detail.setModifier(user.getUserFullName());
                detail.setModifyTime(new Timestamp(System.currentTimeMillis()));
            }
            updateVehicleArchive(contract, detail);
            dealCustomerCommission(contract, detail);
        }

        contract.setIsChanging(false);
        updateHistory(contract);

        //车辆销售合同保存，审批同意（包括提交自审）、作废、和撤销 成功后调用下存储过程 pro_vehicle_data_vary
        baseDao.flush();
        saleContractService1.executeProVehicleDataVary(contract.getContractNo());
        return super.onLastApproveLevel(approveDocument, comment);
    }

    /**
     * 更新客户信息
     */
    private void updateCustomer(VehicleSaleContracts contract) {
        if (StringUtils.isEmpty(contract.getCustomerId())) {
            throw new ServiceException("导出客户档案失败:合同上的客户ID不能为空");
        }
        BaseRelatedObjects relObj = dao.get(BaseRelatedObjects.class, contract.getCustomerId());
        if (relObj == null) {
            throw new ServiceException("导出客户档案失败:档案中的客户ID不存在");
        } else {
            if (Tools.toShort(relObj.getStatus()) <= 0) {
                throw new ServiceException("导出客户档案失败:档案中的客户已被禁用");
            }
        }
        SysUsers user = HttpSessionStore.getSessionUser();
        // 更新客户档案
        if (StringUtils.isEmpty(relObj.getStationId())) {
            relObj.setStationId(user.getDefaulStationId());
        }
        relObj.setObjectName(contract.getCustomerName());
        relObj.setNamePinyin(GetChineseFirstChar.getFirstLetter(contract.getCustomerName()));
        relObj.setSex(contract.getCusotmerSex());
        relObj.setCertificateType(contract.getCustomerCertificateType());
        relObj.setCertificateNo(contract.getCustomerCertificateNo());
        relObj.setLinkman(contract.getLinkman());
        relObj.setPhone(contract.getCustomerPhone());
        relObj.setMobile(contract.getCustomerMobile());
        relObj.setEmail(contract.getCustomerEmail());
        relObj.setAddress(contract.getCustomerAddress());
        relObj.setPostalcode(contract.getCustomerPostcode());
        relObj.setEducation(contract.getCustomerEducation());
        relObj.setOccupation(contract.getCustomerOccupation());
        relObj.setProvince(contract.getCustomerProvince());
        relObj.setCity(contract.getCustomerCity());
        relObj.setArea(contract.getCustomerArea());
        relObj.setObjectType(Tools.toInt(relObj.getObjectType()) | 2);
        relObj.setObjectKind(Tools.toInt(relObj.getObjectKind()) | 5);
        relObj.setPlanBackTime(new Timestamp(System.currentTimeMillis()));
        relObj.setBackFlag(true);
        relObj.setCustomerType((short) 30);
        relObj.setModifier(user.getUserName());
        relObj.setModifyTime(new Timestamp(System.currentTimeMillis()));
        dao.update(relObj);
    }

    /**
     * 更新车辆档案
     */
    private void updateVehicleArchive(VehicleSaleContracts contract, VehicleSaleContractDetail detail) {
        if (StringUtils.isEmpty(detail.getVehicleId())) {
            return;
        }
        SysUsers user = HttpSessionStore.getSessionUser();

        List<VehicleArchives> vehicleArchiveList = (List<VehicleArchives>) dao.findByHql("from VehicleArchives where vehicleId = ? OR vehicleVin = ? ", detail.getVehicleId(), detail.getVehicleVin());
        VehicleArchives vehicleArchive = null;
        if (vehicleArchiveList != null && vehicleArchiveList.size() > 0) {
            vehicleArchive = vehicleArchiveList.get(0);
        } else {
            vehicleArchive = new VehicleArchives();
            vehicleArchive.setVehicleId(detail.getVehicleId());
        }

        vehicleArchive.setStationId(contract.getStationId());
//        vehicleArchive.setCustomerId(contract.getCustomerId());
        vehicleArchive.setCustomerId(StringUtils.isBlank(detail.getVehicleOwnerId()) ? contract.getCustomerId() : detail.getVehicleOwnerId());
        vehicleArchive.setVehicleLinkman(contract.getLinkman());
        vehicleArchive.setVehicleLinkmanPhone(contract.getCustomerPhone());
        vehicleArchive.setVehicleLinkmanMobile(contract.getCustomerMobile());
        vehicleArchive.setVehicleLinkmanAddress(contract.getCustomerAddress());
        vehicleArchive.setSeller(contract.getSeller());
        vehicleArchive.setCreator(contract.getCreator());
        vehicleArchive.setCreateTime(contract.getCreateTime());
        vehicleArchive.setVnoId(detail.getVnoId());
        vehicleArchive.setVehicleVno(detail.getVehicleVno());
        vehicleArchive.setVehicleName(detail.getVehicleName());
        vehicleArchive.setVehicleCardModel(detail.getVehicleVnoNew());
        vehicleArchive.setVehicleVin(StringUtils.isEmpty(detail.getVehicleVinNew()) ? detail.getVehicleVin() : detail.getVehicleVinNew());
        vehicleArchive.setVehicleSalesCode(detail.getVehicleSalesCode());
        vehicleArchive.setVehicleStrain(detail.getVehicleStrain());
        vehicleArchive.setVehicleColor(detail.getVehicleColor());
        vehicleArchive.setVehicleEngineType(detail.getVehicleEngineType());
        vehicleArchive.setVehicleEngineNo(detail.getVehicleEngineNo());
        vehicleArchive.setVehicleEligibleNo(StringUtils.isEmpty(detail.getVehicleEligibleNoNew()) ? detail.getVehicleEligibleNo() : detail.getVehicleEligibleNoNew());

        vehicleArchive.setVehicleOutFactoryTime(detail.getVehicleOutFactoryTime());
        vehicleArchive.setVehiclePurchaseTime(detail.getRealDeliverTime());
        vehicleArchive.setVehicleCardNo(detail.getVehicleCardNo());
        vehicleArchive.setVehiclePrice(detail.getVehiclePrice());

        vehicleArchive.setVehiclePurchaseFlag(true);
        vehicleArchive.setMaintainRemindFlag(true);
        vehicleArchive.setVehicleBelongTo(false);
        vehicleArchive.setStatus((short) 1);
        vehicleArchive.setDriveRoomNo(detail.getDriveRoomNo());
        vehicleArchive.setModifier(user.getUserFullName());
        vehicleArchive.setModifyTime(new Timestamp(System.currentTimeMillis()));

        vehicleArchive.setBelongToSupplierId(detail.getBelongToSupplierId());
        vehicleArchive.setBelongToSupplierNo(detail.getBelongToSupplierNo());
        vehicleArchive.setBelongToSupplierName(detail.getBelongToSupplierName());
        vehicleArchive.setBackAllow(1023);
        vehicleArchive.setProfession(contract.getCustomerProfession());
        vehicleArchive.setVehicleLinkmanAddress(contract.getCustomerAddress());

        dao.update(vehicleArchive);
    }


    /**
     * 处理佣金
     */
    private void dealCustomerCommission(VehicleSaleContracts contract, VehicleSaleContractDetail detail) {
        List<FinanceDocumentEntries> finaceList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? and documentType = ?", detail.getContractDetailId(), "车辆-客户佣金");
        if (Tools.toDouble(detail.getVehicleProfit()) == 0.00D) {
            //佣金为0，删除佣金分录
            if (finaceList.size() > 0) {
                FinanceDocumentEntries financeDocumentEntries = finaceList.get(0);
                if (StringUtils.isEmpty(financeDocumentEntries.getAfterNo())) {
                    baseDao.delete(financeDocumentEntries);
                } else {
                    throw new ServiceException(String.format("佣金单据分录%s,已经请了款，不能删除车辆或将佣金修改为0", financeDocumentEntries.getDocumentNo()));
                }
            }
        } else {
            String sCId = StringUtils.isEmpty(detail.getCustomerIdProfit()) ? contract.getCustomerId() : detail.getCustomerIdProfit();
            String sCNo = StringUtils.isEmpty(detail.getCustomerIdProfit()) ? contract.getCustomerNo() : detail.getCustomerNoProfit();
            String sCName = StringUtils.isEmpty(detail.getCustomerIdProfit()) ? contract.getCustomerName() : detail.getCustomerNameProfit();
            String documentNo = (StringUtils.isEmpty(detail.getVehicleVin()) ? "" : detail.getVehicleVin()) + "," + contract.getContractNo();
            double dPriceProfit = Tools.toDouble(detail.getVehicleProfit());
            if (finaceList == null || finaceList.size() == 0) {
                //没有的新增分录
                financeDocumentEntriesDao.insertEntry(contract.getStationId(), 1, (short) 65, "车辆-客户佣金", detail.getContractDetailId(), sCId, sCNo, sCName, (short) 70, dPriceProfit, documentNo, null);
            } else {

                FinanceDocumentEntries financeDocumentEntries = finaceList.get(0);
                if (StringUtils.isEmpty(financeDocumentEntries.getAfterNo())) {
                    //没有后续操作，直接修改分录
                    financeDocumentEntries.setLeftAmount(dPriceProfit);
                    financeDocumentEntries.setDocumentAmount(dPriceProfit);
                    financeDocumentEntries.setObjectId(sCId);
                    financeDocumentEntries.setObjectNo(sCNo);
                    financeDocumentEntries.setObjectName(sCName);
                    financeDocumentEntries.setDocumentNo(documentNo);
                    dao.update(financeDocumentEntries);
                } else {
                    //配车时，佣金金额和佣金客户没变的情况下应该允许保存 -20190214
                    if (Tools.toDouble(financeDocumentEntries.getDocumentAmount()) != dPriceProfit || !sCId.equals(financeDocumentEntries.getObjectId())) {
                        throw new ServiceException(String.format("佣金单据分录%s,已经请了款，不能更改佣金金额或佣金客户或VIN", financeDocumentEntries.getDocumentNo()));
                    }

                    //那如果换车呢，是否允许保存，如果允许的话，那应该还得更新之前所生成的单据分录和请款单上的单据编号（单据编号上有VIN） -20190214
                    if (!documentNo.equals(financeDocumentEntries.getDocumentNo())) {
                        financeDocumentEntries.setDocumentNo(documentNo);

                        //修改请款单上的单据编号
                        List<FinancePaymentRequestsDetails> requestsDetailsList = (List<FinancePaymentRequestsDetails>) baseDao.findByHql("FROM FinancePaymentRequestsDetails where entryId = ?", financeDocumentEntries.getEntryId());
                        if (requestsDetailsList != null && requestsDetailsList.size() > 0) {
                            for (FinancePaymentRequestsDetails requestsDetail : requestsDetailsList) {
                                requestsDetail.setDocumentNoEntry(documentNo);
                            }
                        }
                    }
                }
            }
        }
    }


    private void updateHistory(VehicleSaleContracts contract) {
        List<VehicleSaleContractHistory> list = (List<VehicleSaleContractHistory>) dao.findByHql("FROM VehicleSaleContractHistory WHERE ISNULL(status,0)=0 AND contractNo = ?", contract.getContractNo());
        if (list != null && list.size() > 0) {
            Timestamp varyTime = new Timestamp(new Date().getTime());
            for (VehicleSaleContractHistory history : list) {
                history.setStatus((byte) 1);
                history.setVaryTime(varyTime);
                dao.update(history);
            }
        }
    }

    @SuppressWarnings("rawtypes")
//    public void onLastApproveLevelOne(VehicleSaleContractDetail detail, String comment) {
//        // 处理客户佣金
//        VehicleSaleContracts contract = dao.get(VehicleSaleContracts.class, detail.getContractNo());
//        VwVehicleSaleContractDetail vwDetail = saleContractsDao.getVwContractDetailByDetailId(detail.getContractDetailId());
//        double priceProfit = Tools.toDouble(vwDetail.getVehicleProfit());
//        if (priceProfit > 0) {
//            String sCId = !StringUtils.isEmpty(vwDetail.getCustomerIdProfit()) ? vwDetail.getCustomerIdProfit() : contract.getCustomerId();
//            String sCNo = !StringUtils.isEmpty(vwDetail.getCustomerIdProfit()) ? vwDetail.getCustomerNoProfit() : contract.getCustomerNo();
//            String sCName = !StringUtils.isEmpty(vwDetail.getCustomerIdProfit()) ? vwDetail.getCustomerNameProfit() : contract.getCustomerName();
//            FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
//            financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
//            financeDocumentEntries.setStationId(contract.getStationId());
//            financeDocumentEntries.setEntryProperty(Constant.DocumentEntries.ENTRIES_PROPERTY_INCLUDED_IN_CURRENT);
//            financeDocumentEntries.setEntryType(Constant.DocumentEntries.ENTRIES_TYPE_NEED_APPLAY);
//            financeDocumentEntries.setDocumentType("车辆-客户佣金");
//
//            financeDocumentEntries.setSubDocumentNo(vwDetail.getContractDetailId());
//
//            financeDocumentEntries.setObjectId(sCId);
//            financeDocumentEntries.setObjectNo(sCNo);
//            financeDocumentEntries.setObjectName(sCName);
//            financeDocumentEntries.setAmountType(AmountType.ACCOUNT_PAYABLE);// 70
//            financeDocumentEntries.setDocumentAmount(priceProfit);
//
//            financeDocumentEntries.setDocumentId(vwDetail.getContractDetailId());
//            financeDocumentEntries.setDocumentNo(vwDetail.getVehicleVin() + "," + vwDetail.getContractNo());
//            financeDocumentEntries.setSummary(null);
//            financeDocumentEntries.setUserId(vwDetail.getUserId());
//            financeDocumentEntries.setUserName(vwDetail.getUserName());
//            financeDocumentEntries.setUserNo(vwDetail.getUserNo());
//            financeDocumentEntries.setDepartmentId(vwDetail.getDepartmentId());
//            financeDocumentEntries.setDepartmentName(vwDetail.getDepartmentName());
//
//            financeDocumentEntries.setDepartmentNo(vwDetail.getDepartmentNo());
//            financeDocumentEntries.setLeftAmount(priceProfit);
//
//            financeDocumentEntries.setDocumentTime(new Timestamp(new Date().getTime()));
//            financeDocumentEntries.setOffsetAmount(0.00);
//            financeDocumentEntries.setWriteOffAmount(0.00);
//            financeDocumentEntries.setInvoiceAmount(0.00);
//            financeDocumentEntries.setPaidAmount(0.00);
//
//            if (!financeDocumentEntriesDao.insertFinanceDocumentEntries(financeDocumentEntries)) {
//                throw new ServiceException("审批保存失败，请稍后重试");
//                // return new ApproveResult(ApproveResultCode.APPROVE_ERROR_DOCUMENT_ENTRY_INSERT_FAIL);
//            }
//        }
//        detail.setApproveStatus((short) 1);
//
//        SysUsers user = (SysUsers) ServletActionContext.getRequest().getSession().getAttribute(Attribute.SESSION_USER);
//        Timestamp approveTime = new Timestamp(new Date().getTime());
//        detail.setApproveTime(approveTime);
//        detail.setApproveName(user.getUserName());
//        detail.setModifier(user.getUserFullName());
//        detail.setModifyTime(approveTime);
//
//        updateCustomer(detail);// 更新客户信息
//        updateVehicleArchive(detail);// 更新车辆档案信息
//        updateChargeDocument(detail); // 处理费用单据分录
//        dao.update(detail);
//        // return super.onLastApproveLevel(detail, comment);
//    }

    /**
     * 处理费用单据分录
     *
     * @param detail
     * @return
     */
    private boolean updateChargeDocument(VehicleSaleContractDetail detail) {
        VehicleSaleContracts saleContract = dao.get(VehicleSaleContracts.class, detail.getContractNo());
        List<VehicleSaleContractCharge> paidCharges = saleContractsDao.getPaidCharges(detail.getContractDetailId());

        SysUsers user = HttpSessionStore.getSessionUser();
        if (paidCharges != null && paidCharges.size() > 0) {
            for (VehicleSaleContractCharge paidCharge : paidCharges) {
                double mChargeAmount = Tools.toDouble(paidCharge.getChargePf());
                String sSummary = paidCharge.getRemark();
                if (mChargeAmount == 0) continue;

                FinanceDocumentEntries entry = new FinanceDocumentEntries();
                entry.setEntryId(UUID.randomUUID().toString());
                entry.setStationId(saleContract.getStationId());
                entry.setEntryProperty(19);
                entry.setEntryType((short) 15);
                entry.setDocumentType("车辆-" + paidCharge.getChargeName());
                entry.setDocumentId(paidCharge.getSaleContractChargeId());
                entry.setDocumentNo(detail.getVehicleVin() + "," + detail.getContractNo());
                entry.setSubDocumentNo(detail.getContractNo());
                entry.setObjectId(saleContract.getCustomerId());
                entry.setObjectNo(saleContract.getCustomerNo());
                entry.setObjectName(saleContract.getCustomerName());

                entry.setAmountType((short) 20);
                entry.setLeftAmount(mChargeAmount);
                entry.setDocumentAmount(mChargeAmount);
                entry.setDocumentTime(new Timestamp(new Date().getTime()));
                entry.setUserId(user.getUserId());
                entry.setUserNo(user.getUserNo());
                entry.setUserName(user.getUserName());
                entry.setDepartmentId(user.getDepartment());
                entry.setDepartmentNo(user.getDepartmentNo());
                entry.setDepartmentName(user.getDepartmentName());
                entry.setOffsetAmount(0.00);
                entry.setPaidAmount(0.00);
                entry.setWriteOffAmount(0.00);
                entry.setInvoiceAmount(0.00);
                entry.setSummary(sSummary);

                if (!financeDocumentEntriesDao.insertFinanceDocumentEntries(entry)) {
                    logger.error(String.format("%s 添加费用单据分录出错,SaleContractChargeId:%s", detail.getDocumentNo(), paidCharge.getSaleContractChargeId()));
                    throw new ServiceException("审批失败:添加费用单据分录出错！");
                }
            }
        }
        return true;
    }


    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
        List<VehicleSaleContractDetail> details = saleContractsDao.getDetailByContractsNo(approveDocument.getDocumentNo());
        if (details == null || details.size() == 0) {
            throw new ServiceException("审批失败:销售合同中没有合同明细");
        }
        for (VehicleSaleContractDetail detail : details) {
            validateCustomer(detail);
            //东贸-只有新选的车辆才需判断是否在改装中，因此只需在保存时校验
//            if (checkConvering(detail)) {
//                throw new ServiceException("审批失败:该车辆外委改装项目未完成");
//            }
            //checkProft(detail); //现在处理方式把【车辆单价是否低于最低限价】（是或否）作为审批条件
        }
        return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    /**
     * 更新车辆档案
     *
     * @param detail
     */
//    private boolean updateVehicleArchive(VehicleSaleContractDetail detail) {
//        if (StringUtils.isEmpty(detail.getVehicleId())) {
//            return true; // 如果合同明细的 车辆Id为空，则跳过
//        }
//
//        VehicleArchives archives = dao.get(VehicleArchives.class, detail.getVehicleId());
//        VehicleSaleContracts contract = dao.get(VehicleSaleContracts.class, detail.getContractNo());
//        VwVehicleSaleContractDetail vwDetail = saleContractsDao.getVwContractDetailByDetailId(detail.getContractDetailId());
//
//        if (archives == null) {
//            archives = new VehicleArchives();
//            archives.setVehicleId(detail.getVehicleId());
//        }
//
//        archives.setStationId(contract.getStationId());
//        archives.setCustomerId(contract.getCustomerId());
//        archives.setVehicleLinkman(contract.getLinkman());
//        archives.setVehicleLinkmanPhone(contract.getLinkman());
//        archives.setVehicleLinkmanMobile(contract.getCustomerMobile());
//        archives.setVehicleLinkmanAddress(contract.getCustomerAddress());
//        archives.setSeller(contract.getSeller());
//        archives.setSellerId(contract.getSellerId());
//        archives.setCreator(contract.getCreator());
//        archives.setCreateTime(contract.getCreateTime());
//        archives.setVnoId(StringUtils.isEmpty(vwDetail.getVnoIdNew()) ? vwDetail.getVnoId() : vwDetail.getVnoIdNew());
//        archives.setVehicleVno(StringUtils.isEmpty(vwDetail.getVehicleVnoNew()) ? vwDetail.getVehicleVno() : vwDetail.getVehicleVnoNew());
//        archives.setVehicleVin(StringUtils.isEmpty(vwDetail.getVehicleVinNew()) ? vwDetail.getVehicleVin() : vwDetail.getVehicleVinNew());
//        archives.setVehicleSalesCode(vwDetail.getVehicleSalesCode());
//        archives.setVehicleName(StringUtils.isEmpty(vwDetail.getVehicleNameNew()) ? vwDetail.getVehicleName() : vwDetail.getVehicleNameNew());
//        archives.setVehicleStrain(vwDetail.getVehicleStrain());
//        archives.setVehicleColor(vwDetail.getVehicleColor());
//        archives.setVehicleEngineType(vwDetail.getVehicleEngineType());
//        archives.setVehicleEngineNo(vwDetail.getVehicleEngineNo());
//        archives.setVehicleEligibleNo(StringUtils.isEmpty(vwDetail.getVehicleEligibleNoNew()) ? vwDetail.getVehicleEligibleNo() : vwDetail.getVehicleEligibleNoNew());
//        archives.setVehicleOutFactoryTime(vwDetail.getVehicleOutFactoryTime());
//        archives.setVehiclePurchaseTime(vwDetail.getRealDeliverTime());
//        archives.setVehicleCardNo(vwDetail.getVehicleCardNo());
//        archives.setVehiclePrice(vwDetail.getVehiclePrice());
//        archives.setVehiclePurchaseFlag(true);
//        archives.setMaintainRemindFlag(true);
//        archives.setVehicleBelongTo(false);
//        archives.setStatus((short) 1);
//        archives.setDriveRoomNo(vwDetail.getDriveRoomNo());
//        SysUsers user = HttpSessionStore.getSessionUser();
//        archives.setModifier(user.getUserFullName());
//        archives.setModifyTime(new Timestamp(new Date().getTime()));
//
//        List<String> list = getRebateObject(vwDetail);
//        if (list.size() > 0) {
//            archives.setBelongToSupplierId(list.get(0));
//            archives.setBelongToSupplierNo(list.get(1));
//            archives.setBelongToSupplierName(list.get(2));
//        } else {
//            archives.setBelongToSupplierId(null);
//            archives.setBelongToSupplierNo(null);
//            archives.setBelongToSupplierName(null);
//        }
//        archives.setBackAllow(1023);
//        archives.setProfession(vwDetail.getProfession());
//        archives.setVehicleLinkmanAddress(contract.getCustomerAddress());
//
//        dao.update(archives); // 新增或修改
//        return true;
//    }

//    /**
//     * 获取挂靠返利对象（发票类型为购车发票的对象）
//     *
//     * @param vwDetail 合同车辆明细ID
//     * @return
//     */
//    private List<String> getRebateObject(VwVehicleSaleContractDetail vwDetail) {
//        List<String> list = new ArrayList<String>();
//        List<VehicleInvoices> invoices = vehicleInvoicesDao.getVehicleInvoicesByContractId(vwDetail.getContractDetailId());
//        if (invoices != null && invoices.size() == 1) {
//            list.add(invoices.get(0).getObjectId());
//            list.add(invoices.get(0).getObjectNo());
//            list.add(invoices.get(0).getObjectName());
//        } else {
//            list.add(vwDetail.getCustomerId());
//            list.add(vwDetail.getCustomerNo());
//            list.add(vwDetail.getCustomerName());
//        }
//        return list;
//    }

    /**
     * 更新往来对象
     */
    private boolean updateCustomer(VehicleSaleContractDetail detail) {
        VehicleSaleContracts contract = dao.get(VehicleSaleContracts.class, detail.getContractNo());
        if (contract == null) {
            throw new ServiceException("更新客户档案失败");
        }
        BaseRelatedObjects relObj = dao.get(BaseRelatedObjects.class, contract.getCustomerId());
        if (relObj == null) {
            throw new ServiceException("更新客户档案失败：客户未找到");
        } else {
            if (relObj.getStatus() == null || relObj.getStatus() <= 0) {
                throw new ServiceException("更新客户档案失败:客户已被禁用");
            }
        }
        SysUsers user = HttpSessionStore.getSessionUser();
        if (StringUtils.isEmpty(relObj.getStationId())) {
            relObj.setStationId(user.getInstitution().getDefaultStation());
        }

        relObj.setObjectName(contract.getCustomerName());
        relObj.setNamePinyin(GetChineseFirstChar.getFirstLetter(contract.getCustomerName()));
        relObj.setSex(contract.getCusotmerSex());
        relObj.setCertificateType(contract.getCustomerCertificateType());
        relObj.setCertificateNo(contract.getCustomerCertificateNo());
        relObj.setLinkman(contract.getLinkman());
        relObj.setPhone(contract.getCustomerPhone());
        relObj.setMobile(contract.getCustomerMobile());
        relObj.setEmail(contract.getCustomerEmail());
        relObj.setAddress(contract.getCustomerAddress());
        relObj.setPostalcode(contract.getCustomerPostcode());
        relObj.setEducation(contract.getCustomerEducation());
        relObj.setOccupation(contract.getCustomerOccupation());
        relObj.setProvince(contract.getCustomerProvince());
        relObj.setCity(contract.getCustomerCity());
        relObj.setArea(contract.getCustomerArea());
        relObj.setObjectKind(Tools.toInt(relObj.getObjectKind()) | 7);

        relObj.setPlanBackTime(new Timestamp(new Date().getTime()));
        relObj.setBackFlag(true);
        relObj.setCustomerType((short) 30);
        relObj.setModifier(user.getUserName());
        relObj.setModifyTime(new Timestamp(new Date().getTime()));

        dao.update(relObj);
        return true;
    }

    /**
     * 校验客户的合法性
     *
     * @return
     */
    private Boolean validateCustomer(VehicleSaleContractDetail detail) {
        VehicleSaleContracts contract = dao.get(VehicleSaleContracts.class, detail.getContractNo());
        if (contract == null) {
            throw new ServiceException("审批失败:未找到合同信息");
        }
        BaseRelatedObjects relObj = dao.get(BaseRelatedObjects.class, contract.getCustomerId());
        if (relObj == null || !"1".equals(String.valueOf(relObj.getStatus()))) {
            // 没有=1的情况
            throw new ServiceException("审批失败:往来对象中该客户已被禁用");
        }

        // 如果客户名称不一致，修改 合同中的客户名称
        if (contract.getCustomerName() != null && !contract.getCustomerName().equals(relObj.getObjectName())) {
            contract.setCustomerName(relObj.getObjectName());
        }

        Short objectNature = relObj.getObjectNature();
        List<BaseRelatedObjects> sameCertificateCustomers = baseRelatedObjectsDao.getCustomerByCertificate(contract.getCustomerCertificateNo(), contract.getCustomerId());
        // 如果为单位，对象名称不允许重复
        if (objectNature == null || objectNature == 10) {
            List<BaseRelatedObjects> sameNameCustomers = baseRelatedObjectsDao.getCustomerByName(contract.getCustomerName(), contract.getCustomerId());
            if (sameNameCustomers != null && sameNameCustomers.size() > 0) {
                logger.error(String.format("%s 存在同名客户，客户ID:%s", detail.getDocumentNo(), sameNameCustomers.get(0).getObjectId()));
                throw new ServiceException("审批失败:客户名称已经存在");
            }

            if (sameCertificateCustomers != null && sameCertificateCustomers.size() > 0) {
                if (!"东贸版".equals(sysOptionsDao.getOptionForString(InterestedCustomersService.DV_VERSION)) ||
                        (!InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER.equals(contract.getCustomerCertificateNo()) && !InterestedCustomersService.DEFAULT_CER_NO_UNIT.equals(contract.getCustomerCertificateNo()))) {
                    logger.error(String.format("%s 存在相同证件号码的客户，客户ID %s，证件号码  %s", detail.getDocumentNo(), sameCertificateCustomers.get(0).getObjectId(), sameCertificateCustomers.get(0).getCertificateNo()));
                    throw new ServiceException("审批失败:客户的证件号码已经存在");
                }
            }
        } else
        // 对象名称和手机不能同时重复
        {
            if (!StringUtils.isEmpty(contract.getCustomerCertificateNo())) {
                if (sameCertificateCustomers != null && sameCertificateCustomers.size() > 0) {
                    if (!"东贸版".equals(sysOptionsDao.getOptionForString(InterestedCustomersService.DV_VERSION)) ||
                            (!InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER.equals(contract.getCustomerCertificateNo()) && !InterestedCustomersService.DEFAULT_CER_NO_UNIT.equals(contract.getCustomerCertificateNo()))) {
                        logger.error(String.format("%s 存在相同证件号码的客户，客户ID %s，证件号码  %s", detail.getDocumentNo(), sameCertificateCustomers.get(0).getObjectId(), sameCertificateCustomers.get(0).getCertificateNo()));
                        throw new ServiceException("审批失败:客户的证件号码已经存在");
                    }
                }
            }
            // 往来对象中已存在名称相同或移动电话相同的客户信息,因为是提示选择，所以不做
        }
        return true;
    }

    /**
     * 检查车辆是否正处于改装中
     *
     * @return
     */
    private Boolean checkConvering(VehicleSaleContractDetail detail) {
        if (detail == null || StringUtils.isEmpty(detail.getVehicleId())) {
            return false;
        }
        VehicleStocks stocks = dao.get(VehicleStocks.class, detail.getVehicleId());
        if (stocks == null || stocks.getConversionStatus() == null || stocks.getConversionStatus() != 1) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        Double d = null;
        System.out.println(Tools.toDouble(d));
        //System.out.println(Double.valueOf(d));
    }
}
