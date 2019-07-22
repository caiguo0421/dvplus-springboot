package cn.sf_soft.office.approval.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Constant.AmountType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.Constant.DocumentEntries;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.dao.ChargeReimbursementDao;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractChargeGroups;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.df.model.VehicleOnwayCharge;
import cn.sf_soft.vehicle.purchase.service.ConfirmStatus;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * 车辆费用报销审批单业务处理
 *
 * @author king
 * @create 2012-12-17上午09:48:50
 */
@Service("chargeRiemburseManager")
public class AmChargeReimbursement extends BaseApproveProcess {
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AmChargeReimbursement.class);

    // 审批权限Id,各个审批均不相同
    protected String approvalPopedomId = "35552020";

    @Autowired
    private SysOptionsDao sysOptionsDao;

    /**
     * 库存车费用报销是否允许车辆订购后还能审批通过
     * 设置选项为‘允许’，如果车辆费用在审批过程中车辆状态变为已订购，则仍允许审批通过；
     * 设置选项为‘不允许’，如果车辆费用在审批过程中车辆状态变为已订购，则不允许审批通过；
     * --ADM18110048
     */
    private static final String VEHICLE_CHARGE_ALLOW_APPROVE_AFTER_ORDER = "VEHICLE_CHARGE_ALLOW_APPROVE_AFTER_ORDER";

    @Override
    protected String getApprovalPopedomId() {
        return approvalPopedomId;
    }


    @Autowired
    @Qualifier("chargeReimburseDaoHibernate")
    private ChargeReimbursementDao chargeReimbursementDao;

    public void setChargeReimbursementDao(ChargeReimbursementDao chargeReimbursementDao) {
        this.chargeReimbursementDao = chargeReimbursementDao;
    }

    @Override
    public ChargeReimbursements getDocumentDetail(String documentNo) {
        ChargeReimbursements chargeReimbursements = dao.get(ChargeReimbursements.class, documentNo);
        return chargeReimbursements;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus) {
        ChargeReimbursements chargeReimbursement = getDocumentDetail(approveDocument.getDocumentNo());
        Set<ChargeReimbursementsDetails> chargeDetail = chargeReimbursement.getChargeDetail();
        if (chargeDetail == null || chargeDetail.size() == 0) {
            throw new ServiceException("审批失败:该费用报销单中没有费用明细");
        }
        for (ChargeReimbursementsDetails chargeReimburseDetail : chargeDetail) {
            if (Tools.toShort(chargeReimbursement.getReimbursementMode()) == (short) 20) {
                //审批时校验是否为“新增费用”类型的单据（office_charge_reimbursements表reimbursement_mode字段为20表示的是”新增费用“），
                // 如果是，检查是对应的销售合同的组的数量是否大于1，大于1时，不能审批通过。提示：审批失败，报销方式为”新增费用“时，所关联销售合同的车辆数量必须为1。
                String sql = "SELECT d.group_id,d.vehicle_quantity FROM office_charge_reimbursements_details a\n" +
                        "LEFT JOIN dbo.office_charge_reimbursements b ON b.document_no = a.document_no\n" +
                        "LEFT JOIN dbo.vehicle_sale_contract_detail c ON a.vscv_id=c.contract_detail_id\n" +
                        "LEFT JOIN dbo.vehicle_sale_contract_detail_groups d ON c.group_id=d.group_id\n" +
                        "WHERE b.status<50 AND b.reimbursement_mode=20 AND c.contract_detail_id=:contractDetailId";
                Map<String, Object> paramMap = new HashMap(2);
                paramMap.put("contractDetailId", chargeReimburseDetail.getVscvId());
                List<Map<String, Object>> resultList = dao.getMapBySQL(sql, paramMap);
                if (resultList != null && resultList.size() > 0) {
                    Map<String, Object> item = resultList.get(0);
                    if (item.get("vehicle_quantity") != null && Tools.toInt((Integer) item.get("vehicle_quantity")) > 1) {
                        throw new ServiceException("审批失败，报销方式为”新增费用“时，所关联销售合同分组的车辆数量必须为1");
                    }
                }

            } else if ((short) 30 == Tools.toShort(chargeReimbursement.getReimbursementMode())) {
                //库存车费用
                if (StringUtils.isEmpty(chargeReimburseDetail.getVehicleVin())) {
                    throw new ServiceException(String.format("审批失败：费用：%s对应的VIN码为空", chargeReimburseDetail.getChargeName()));
                }

                List<VehicleStocks> stockList = (List<VehicleStocks>) dao.findByHql("from  VehicleStocks  where vehicleId = ? ", chargeReimburseDetail.getVehicleId());
                if (stockList == null || stockList.size() == 0) {
                    throw new ServiceException(String.format("审批失败：VIN：%s在车辆库存中找不到", chargeReimburseDetail.getVehicleVin()));
                }

                VehicleStocks stocks = stockList.get(0);
                if ("不允许".equals(sysOptionsDao.getOptionForString(VEHICLE_CHARGE_ALLOW_APPROVE_AFTER_ORDER))) {
                    if (Tools.toShort(stocks.getStatus()) != 0) {
                        throw new ServiceException(String.format("审批失败：VIN：%s的库存状态不正确，非库存车不能报销", chargeReimburseDetail.getVehicleVin()));
                    }
                }

            } else if ((short) 40 != Tools.toShort(chargeReimbursement.getReimbursementMode())) {
                if (StringUtils.isNotEmpty(chargeReimburseDetail.getVsccId())) {
                    VehicleSaleContractCharge vehicleCharge = chargeReimbursementDao.getVehicleSaleContractCharge(chargeReimburseDetail.getVsccId());
                    if (vehicleCharge == null) {
                        throw new ServiceException("审批失败:找不到车辆销售合同中的费用信息或费用的预估成本为0");
                    }
                    // 如果cost_status字段值不为1说明该费用已报销过
                    if (vehicleCharge.getCostStatus() != 0) {
                        throw new ServiceException("审批失败:车辆销售合同中的费用已报销");
                    } else {
                        VehicleSaleContractDetail vehicleDetail = chargeReimbursementDao.getVehicleSaleContractDetail(chargeReimburseDetail.getVscvId());
                        if (vehicleDetail == null) {
                            throw new ServiceException("审批失败:找不到车辆销售合同中的车辆信息，或者车辆未审批，或者没有车辆识别码。");
                        }
                        // 车辆的审批状态必须是已审批或已审核才能报销(对应sys_flags表中approve_status)
                        if (vehicleDetail.getApproveStatus() != 1 && vehicleDetail.getApproveStatus() != 2) {
                            throw new ServiceException("车辆所对应的销售合同中的车辆未经过审批或审核!");
                        }

                    }
                }
            }
        }
        return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        ChargeReimbursements chargeReimbursement = getDocumentDetail(approveDocument.getDocumentNo());
        short reimbursementMode = 10; // 10是合同费用、20为新增费用
        if (chargeReimbursement.getReimbursementMode() != null) {
            reimbursementMode = chargeReimbursement.getReimbursementMode();
        }

        if (reimbursementMode == 10) {
            // 合同费用
            doReimburseDetailWithContract(chargeReimbursement);
        } else if (reimbursementMode == 20) {
            // 新增费用
            doReimburseDetailWithNew(chargeReimbursement);
        } else if (reimbursementMode == 30) {
            // 库存车费用
            doReimburseDetailWithStock(chargeReimbursement);
        } else if (reimbursementMode == 40) {
            //在途车报销
            doReimburseDetailWithOnWay(chargeReimbursement);
        } else {
            //do nothing
        }

        //产生对报销人的应付单据分录
        FinanceDocumentEntries financeDocumentEntries = new FinanceDocumentEntries();
        financeDocumentEntries.setEntryId(UUID.randomUUID().toString());
        financeDocumentEntries.setStationId(chargeReimbursement.getStationId());
        financeDocumentEntries.setEntryProperty(DocumentEntries.ENTRIES_PROPERTY_EXPENSE_REIMBURSEMENT);
        financeDocumentEntries.setEntryType(DocumentEntries.ENTRIES_TYPE_NEED_PAY);
        financeDocumentEntries.setDocumentType("费用-车辆报销");
        financeDocumentEntries.setDocumentId(chargeReimbursement.getDocumentNo());
        financeDocumentEntries.setDocumentNo(chargeReimbursement.getDocumentNo());
        financeDocumentEntries.setSubDocumentNo(chargeReimbursement.getDocumentNo());
        financeDocumentEntries.setObjectId(chargeReimbursement.getUserId());
        financeDocumentEntries.setObjectNo(chargeReimbursement.getUserNo());
        financeDocumentEntries.setObjectName(chargeReimbursement.getUserName());

        // modify by shichunshan
        financeDocumentEntries.setAmountType(AmountType.NEED_PAY_OTHERS);
        // financeDocumentEntries.setAmountType(AmountType.OTHERS_PAYMENT);

        financeDocumentEntries.setLeftAmount(chargeReimbursement.getReimburseAmount());
        financeDocumentEntries.setDocumentAmount(chargeReimbursement.getReimburseAmount());
        financeDocumentEntries.setDocumentTime(new Timestamp(new Date().getTime()));
        financeDocumentEntries.setOffsetAmount(0.00);
        financeDocumentEntries.setWriteOffAmount(0.00);
        financeDocumentEntries.setInvoiceAmount(0.00);
        financeDocumentEntries.setPaidAmount(0.00);

        financeDocumentEntries.setUserId(chargeReimbursement.getUserId());
        financeDocumentEntries.setUserName(chargeReimbursement.getUserName());
        financeDocumentEntries.setUserNo(chargeReimbursement.getUserNo());
        financeDocumentEntries.setDepartmentId(chargeReimbursement.getDepartmentId());
        financeDocumentEntries.setDepartmentName(chargeReimbursement.getDepartmentName());
        financeDocumentEntries.setDepartmentNo(chargeReimbursement.getDepartmentNo());
        financeDocumentEntries.setSummary(chargeReimbursement.getRemark());
        if (!financeDocumentEntriesDao.insertFinanceDocumentEntries(financeDocumentEntries)) {
            throw new ServiceException("审批失败:插入单据分录出错");
        }


        // 机制凭证处理
        if (!createVoucher(approveDocument.getDocumentNo())) {
            throw new ServiceException("审批失败:生成机制凭证出错");
        }
        @SuppressWarnings("unchecked")
        List<String> list = financeDocumentEntriesDao
                .executeSQLQuery("SELECT a.ocrd_id FROM office_charge_reimbursements_details a LEFT JOIN vehicle_stocks b ON a.vehicle_vin = b.vehicle_vin WHERE a.document_no = '"
                        + approveDocument.getDocumentNo() + "' AND b.status = 3");
        // 对于销售合同中已出库的车辆费用产生冲暂估凭证
        if (list != null && list.size() > 0) {
            if (!createOffsetVoucher(approveDocument.getDocumentNo())) {
                throw new ServiceException("审批失败:生成机制凭证出错");
            }
        }

        return super.onLastApproveLevel(approveDocument, comment);
    }

    /**
     * 库存车费用
     *
     * @param chargeReimbursement
     */
    @SuppressWarnings("unchecked")
    private void doReimburseDetailWithStock(ChargeReimbursements chargeReimbursement) {
        Set<ChargeReimbursementsDetails> chargeDetail = chargeReimbursement.getChargeDetail();
        for (ChargeReimbursementsDetails chargeReimburseDetail : chargeDetail) {
            List<VehicleStocks> stockList = (List<VehicleStocks>) dao.findByHql("from  VehicleStocks  where vehicleId = ? ", chargeReimburseDetail.getVehicleId());
            if (stockList == null || stockList.size() == 0) {
                throw new ServiceException(String.format("审批失败：VIN：%s在车辆库存中找不到", chargeReimburseDetail.getVehicleVin()));
            }
            VehicleStocks stocks = stockList.get(0);
            if ("不允许".equals(sysOptionsDao.getOptionForString(VEHICLE_CHARGE_ALLOW_APPROVE_AFTER_ORDER))) {
                if (Tools.toShort(stocks.getStatus()) != 0) {
                    throw new ServiceException(String.format("审批失败：VIN：%s的库存状态不正确，非库存车不能报销", chargeReimburseDetail.getVehicleVin()));
                }
            }

            //如果车辆状态为已订购，则审批同意后还需要同步更新销售合同中车辆的其他成本(other_cost) --ADM18110048
            if (Tools.toShort(stocks.getStatus()) == 1) {
                String sql = "SELECT a.*\n" +
                        "FROM vehicle_sale_contract_detail a\n" +
                        "\tLEFT JOIN vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                        "WHERE b.status <= 50 AND a.vehicle_id =:vehicleId";
                Map<String, Object> paramMap = new HashMap<>(1);
                paramMap.put("vehicleId", stocks.getVehicleId());
                List<Map<String, Object>> list = dao.getMapBySQL(sql, paramMap);
                if (list != null && list.size() > 0) {
                    VehicleSaleContractDetail contractDetail = dao.get(VehicleSaleContractDetail.class, (String) list.get(0).get("contract_detail_id"));
                    if (contractDetail != null) {
                        contractDetail.setOtherCost(Tools.toDouble(contractDetail.getOtherCost()) + Tools.toDouble(chargeReimburseDetail.getChargeAmount()));
                    }
                }
            }

            //更新车辆库存表的other_cost字段，将该报销单该车的报销金额累加到库存表的other_cost中
            stocks.setOtherCost(Tools.toDouble(stocks.getOtherCost()) + Tools.toDouble(chargeReimburseDetail.getChargeAmount()));
            dao.update(stocks);
        }
    }


    /**
     * 在途车报销
     *
     * @param chargeReimbursement
     */
    private void doReimburseDetailWithOnWay(ChargeReimbursements chargeReimbursement) {

        Set<ChargeReimbursementsDetails> chargeDetail = chargeReimbursement.getChargeDetail();
        Session session = dao.getCurrentSession();
        for (ChargeReimbursementsDetails chargeReimburseDetail : chargeDetail) {
            //因为可能业务滞后，在途确认的费用，在入库后了才报销。所以在途的不管状态，只要vehicle_stocks表中有记录就行。
            List<VehicleStocks> stockList = (List<VehicleStocks>) dao.findByHql("from  VehicleStocks  where vehicleId = ? ", chargeReimburseDetail.getVehicleId());
            if (stockList == null || stockList.size() == 0) {
                throw new ServiceException(String.format("审批失败：底盘号：%s在车辆库存中找不到", chargeReimburseDetail.getVehicleVin()));
            }
            //更新车辆库存表的other_cost字段，将该报销单该车的报销金额累加到库存表的other_cost中
            VehicleStocks stock = stockList.get(0);
            stock.setOtherCost(Tools.toDouble(stock.getOtherCost()) + Tools.toDouble(chargeReimburseDetail.getChargeAmount()));

            //如果合同中有配资源车的，同时更新合同明细的vehicle_sale_contract_detail表的other_cost
            Query query = session.createQuery("update VehicleSaleContractDetail set otherCost=? where vehicleId=?");
            query.setParameter(0, stock.getOtherCost());
            query.setParameter(1, stock.getVehicleId());
            query.executeUpdate();

            SQLQuery sqlQuery = session.createSQLQuery("select charge.* \n" +
                    "from vehicle_onway_charge charge \n" +
                    "left join vehicle_df_sap_delivery delivery ON charge.vocd_id=delivery.self_id\n" +
                    "left join vehicle_stocks stocks on delivery.vehicle_id=stocks.vehicle_id\n" +
                    "where stocks.vehicle_id is not null and charge.charge_id='" + chargeReimburseDetail.getChargeId() + "'\n" +
                    "and stocks.underpan_no='" + chargeReimburseDetail.getVehicleVin() + "'");
            sqlQuery.addEntity(VehicleOnwayCharge.class);
            List<VehicleOnwayCharge> charges = sqlQuery.list();
            if (null == charges || charges.isEmpty()) {
                throw new ServiceException(String.format("审批失败：未找到车辆底盘号为%s的在途改装费用(%s)",
                        chargeReimburseDetail.getVehicleVin(), chargeReimburseDetail.getChargeName()));
            } else if (charges.size() > 1) {
                throw new ServiceException(String.format("审批失败：车辆底盘号为%s的在途改装费用(%s)存在%s条记录",
                        chargeReimburseDetail.getVehicleVin(), chargeReimburseDetail.getChargeName(), charges.size()));
            }
            Byte status = charges.get(0).getStatus();
            if (null == status || status != ConfirmStatus.CONFIRMED.getCode()) {
                throw new ServiceException(String.format("审批失败：车辆底盘号为%s存在未确认的在途改装费用", chargeReimburseDetail.getVehicleVin()));
            }

            //回填vehicle_onway_charge表的charge_cost字段和cost_status字段
            VehicleOnwayCharge onwayCharge = charges.get(0);
            //因为使用sql查询的对象，这里使用hql更新
            query = session.createQuery("update VehicleOnwayCharge set chargeCost=?, costStatus=? where selfId=?");
            query.setParameter(0, Tools.toBigDecimal(chargeReimburseDetail.getChargeAmount()));
            query.setParameter(1, true);
            query.setParameter(2, onwayCharge.getSelfId());
            query.executeUpdate();
        }
    }

    /**
     * 报销方式为合同费用 的处理
     *
     * @author caigx
     */
    private void doReimburseDetailWithContract(ChargeReimbursements chargeReimbursement) {
        Set<ChargeReimbursementsDetails> chargeDetail = chargeReimbursement.getChargeDetail();
        for (ChargeReimbursementsDetails chargeReimburseDetail : chargeDetail) {
            VehicleSaleContractCharge vehicleCharge = chargeReimbursementDao.getVehicleSaleContractCharge(chargeReimburseDetail.getVsccId());
            VehicleSaleContractDetail vehicleDetail = chargeReimbursementDao.getVehicleSaleContractDetail(chargeReimburseDetail.getVscvId());
            if (!StringUtils.equals(chargeReimburseDetail.getVehicleVin(), vehicleDetail.getVehicleVin())) {
                throw new ServiceException(String.format("费用明细中VIN%s已经变更为%s，销售单号%s", chargeReimburseDetail.getVehicleVin(), vehicleDetail.getVehicleVin(), vehicleDetail.getContractNo()));
            }

            boolean isPaidByBill = vehicleCharge.getPaidByBill() == null ? false : vehicleCharge.getPaidByBill();
            if (isPaidByBill) {
                // 若费用为凭单收付，则产生该费用对车辆销售合同上客户的应收款
                VehicleSaleContracts saleContract = chargeReimbursementDao.getVehicleSaleContracts(chargeReimburseDetail.getContractNo());

                // 查找对应分录信息
                List<FinanceDocumentEntries> entries = financeDocumentEntriesDao.getDocumentEntriesByDocumentId(vehicleCharge.getSaleContractChargeId());
                if (entries != null && entries.size() > 0) {

                    FinanceDocumentEntries entry = entries.get(0); // 只能有1个
                    // 判断是否已收款
                    if (!StringUtils.isEmpty(entry.getAfterNo())) {// 如果after_no不为空值，说明已收款
                        double paid = Tools.toDouble(entry.getDocumentAmount()) - Tools.toDouble(entry.getLeftAmount());// 已收金额

                        double chargeAmount = Tools.toDouble(chargeReimburseDetail.getChargeAmount());

                        double realLeft = chargeAmount - paid - Tools.toDouble(entry.getLeftAmount()); // 报销金额-已收金额-原剩余应收
                        if (realLeft != 0) {
                            // 新增一条分录
                            FinanceDocumentEntries newEntry = new FinanceDocumentEntries();
                            newEntry.setEntryId(UUID.randomUUID().toString());
                            newEntry.setStationId(chargeReimbursement.getSubmitStationId());
                            newEntry.setEntryProperty(19);
                            newEntry.setEntryType((short) 15);
                            newEntry.setDocumentType("车辆-" + vehicleCharge.getChargeName());
                            newEntry.setDocumentId(chargeReimburseDetail.getOcrdId());
                            newEntry.setDocumentNo(chargeReimburseDetail.getVehicleVin());
                            newEntry.setSubDocumentNo(chargeReimbursement.getDocumentNo());
                            newEntry.setObjectId(saleContract.getCustomerId());
                            newEntry.setObjectNo(saleContract.getCustomerNo());
                            newEntry.setObjectName(saleContract.getCustomerName());

                            newEntry.setAmountType((short) 20);
                            newEntry.setLeftAmount(realLeft);
                            newEntry.setDocumentAmount(realLeft);
                            newEntry.setDocumentTime(new Timestamp(new Date().getTime()));
                            newEntry.setUserId(chargeReimbursement.getUserId());
                            newEntry.setUserNo(chargeReimbursement.getUserNo());
                            newEntry.setUserName(chargeReimbursement.getUserName());
                            newEntry.setDepartmentId(chargeReimbursement.getDepartmentId());
                            newEntry.setDepartmentNo(chargeReimbursement.getDepartmentNo());
                            newEntry.setDepartmentName(chargeReimbursement.getDepartmentName());
                            newEntry.setOffsetAmount(0.00);
                            newEntry.setPaidAmount(0.00);
                            newEntry.setWriteOffAmount(0.00);
                            newEntry.setInvoiceAmount(0.00);
                            newEntry.setSummary(chargeReimbursement.getRemark());

                            if (!financeDocumentEntriesDao.insertFinanceDocumentEntries(newEntry)) {// 新增新的分录
                                throw new ServiceException("审批失败:添加单据分录出错！");
                            }
                        }
                    } else { // 未付款
                        entry.setDocumentAmount(chargeReimburseDetail.getChargeAmount());
                        entry.setLeftAmount(chargeReimburseDetail.getChargeAmount());
                        if (!financeDocumentEntriesDao.updateFinanceDocumentEntries(entry)) { // 修改
                            throw new ServiceException("审批失败:添加单据分录出错！");
                        }
                    }
                } else {
                    FinanceDocumentEntries entry = new FinanceDocumentEntries();
                    entry.setEntryId(UUID.randomUUID().toString());
                    entry.setStationId(chargeReimbursement.getSubmitStationId());
                    entry.setEntryProperty(19);
                    entry.setEntryType((short) 15);
                    entry.setDocumentType("车辆-" + vehicleCharge.getChargeName());
                    entry.setDocumentId(chargeReimburseDetail.getOcrdId());
                    entry.setDocumentNo(chargeReimburseDetail.getVehicleVin());
                    entry.setSubDocumentNo(chargeReimbursement.getDocumentNo());
                    entry.setObjectId(saleContract.getCustomerId());
                    entry.setObjectNo(saleContract.getCustomerNo());
                    entry.setObjectName(saleContract.getCustomerName());
                    // entry.setAccountId(null);
                    entry.setAmountType((short) 20);
                    entry.setLeftAmount(chargeReimburseDetail.getChargeAmount());
                    entry.setDocumentAmount(chargeReimburseDetail.getChargeAmount());
                    entry.setDocumentTime(new Timestamp(new Date().getTime()));
                    entry.setUserId(chargeReimbursement.getUserId());
                    entry.setUserNo(chargeReimbursement.getUserNo());
                    entry.setUserName(chargeReimbursement.getUserName());
                    entry.setDepartmentId(chargeReimbursement.getDepartmentId());
                    entry.setDepartmentNo(chargeReimbursement.getDepartmentNo());
                    entry.setDepartmentName(chargeReimbursement.getDepartmentName());
                    entry.setOffsetAmount(0.00);
                    entry.setPaidAmount(0.00);
                    entry.setWriteOffAmount(0.00);
                    entry.setInvoiceAmount(0.00);
                    // 2013-12-10 备注->摘要 by liujin
                    entry.setSummary(chargeReimbursement.getRemark());
                    if (!financeDocumentEntriesDao.insertFinanceDocumentEntries(entry)) {
                        throw new ServiceException("审批失败:添加单据分录出错！");
                    }
                }
                vehicleCharge.setIncome(Tools.toBigDecimal(chargeReimburseDetail.getChargeAmount())); // 回填报销金额至收入字段
            }

            vehicleCharge.setChargeCost(vehicleCharge.getChargeCost().add(Tools.toBigDecimal(chargeReimburseDetail.getChargeAmount())));
            short statu = 1;
            vehicleCharge.setCostStatus(statu);
            vehicleDetail.setChargeCost(Tools.toDouble(vehicleDetail.getChargeCost()) + Tools.toDouble(chargeReimburseDetail.getChargeAmount()));
            chargeReimbursementDao.updateVehicleSaleContractCharge(vehicleCharge);
            chargeReimbursementDao.updateVehicleSaleContractDetail(vehicleDetail);

        }

    }

    /**
     * 报销方式为新增费用的处理
     *
     * @author caigx
     */
    private void doReimburseDetailWithNew(ChargeReimbursements chargeReimbursement) {
        Set<ChargeReimbursementsDetails> chargeDetail = chargeReimbursement.getChargeDetail();
        for (ChargeReimbursementsDetails chargeReimburseDetail : chargeDetail) {

            if (!StringUtils.isEmpty(chargeReimburseDetail.getVsccId())) {
                throw new ServiceException("审批失败： 生成合同费用出错");
            }

            VehicleSaleContractDetail vehicleDetail = chargeReimbursementDao.getVehicleSaleContractDetail(chargeReimburseDetail.getVscvId());
            String saleContractChargeId = UUID.randomUUID().toString();
            VehicleSaleContractCharge vehicleCharge = new VehicleSaleContractCharge();
            vehicleCharge.setSaleContractChargeId(saleContractChargeId);
            vehicleCharge.setContractDetailId(vehicleDetail.getContractDetailId());

            vehicleCharge.setChargeId(chargeReimburseDetail.getChargeId());
            vehicleCharge.setChargeName(chargeReimburseDetail.getChargeName());
            vehicleCharge.setChargePf(Tools.toBigDecimal(chargeReimburseDetail.getChargeAmount()));
            vehicleCharge.setChargeCost(Tools.toBigDecimal(chargeReimburseDetail.getChargeAmount()));
            vehicleCharge.setCostStatus((short) 1);
            vehicleCharge.setIncome(BigDecimal.ZERO);
            vehicleCharge.setPaidByBill(false);
            vehicleCharge.setContractNo(chargeReimburseDetail.getContractNo());
            // 计算累计值到表vehicle_sale_contract_detail的字段charge_pf,charge_cost中
            if (vehicleDetail.getChargePf() != null) {
                vehicleDetail.setChargePf(vehicleDetail.getChargePf() + chargeReimburseDetail.getChargeAmount());
            }
            if (vehicleDetail.getChargeCost() != null) {
                vehicleDetail.setChargeCost(vehicleDetail.getChargeCost() + chargeReimburseDetail.getChargeAmount());
            }
            // 更新chargeReimburseDetail的vsccId
            chargeReimburseDetail.setVsccId(saleContractChargeId);
            //当报销类型为“新增费用”时，审批那的原逻辑中有处是插入表数据vehicle_sale_contract_charge的一段逻辑，
            //增加一条相同的数据到组中（即同时也插入vehicle_sale_contract_charge_groups表）
            VehicleSaleContractChargeGroups chargeGroups = new VehicleSaleContractChargeGroups();
            chargeGroups.setChargeGroupId(UUID.randomUUID().toString());
            chargeGroups.setGroupId(vehicleDetail.getGroupId());
            chargeGroups.setChargeId(vehicleCharge.getChargeId());
            chargeGroups.setChargeName(vehicleCharge.getChargeName());
            chargeGroups.setIncome(vehicleCharge.getIncome());
            chargeGroups.setChargePf(vehicleCharge.getChargePf());
            chargeGroups.setChargeComment(vehicleCharge.getChargeComment());
            chargeGroups.setRemark(vehicleCharge.getRemark());
            chargeGroups.setPaidByBill(vehicleCharge.getPaidByBill());
            chargeGroups.setContractNo(vehicleDetail.getContractNo());

            //设置ChargeGroupId
            vehicleCharge.setChargeGroupId(chargeGroups.getChargeGroupId());

            dao.save(chargeGroups);
            chargeReimbursementDao.saveVehicleSaleContractCharge(vehicleCharge);
            chargeReimbursementDao.updateVehicleSaleContractDetail(vehicleDetail);
            chargeReimbursementDao.updateChargeReimbursementsDetails(chargeReimburseDetail);
        }
    }

    private boolean createVoucher(String documentNo) {
        String sql = dao.getQueryStringByName("chargeReimbursementVoucherDS", null, null);
        return voucherAuto.generateVoucherByProc(sql, "35552000", false, HttpSessionStore.getSessionUser().getUserId(),
                documentNo);
    }

    private boolean createOffsetVoucher(String documentNo) {
        String sql = dao.getQueryStringByName("chargeReimbursementOffsetVoucherDS", null, null);
        return voucherAuto.generateVoucherByProc(sql, "35552000", false, HttpSessionStore.getSessionUser().getUserId(),
                documentNo);
    }

}
