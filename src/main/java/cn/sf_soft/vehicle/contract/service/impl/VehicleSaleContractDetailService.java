package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.dao.FinanceDocumentEntriesDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.support.*;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.contract.model.VehicleLoanContracts;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractDetailGroups;
import cn.sf_soft.vehicle.loan.model.VehicleLoanCreditInvestigation;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: chenbiao
 * @Date: 2018/6/30 11:11
 * @Description:
 */
@Service
public class VehicleSaleContractDetailService implements Command<VehicleSaleContractDetail> {

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleSaleContractDetailGroupsService.class);

    @Autowired
    private BaseDao baseDao;

    @Autowired
    protected FinanceDocumentEntriesDao financeDocumentEntriesDao;// 单据分录'

    @Autowired
    private VehicleSaleContractDetailHandler vehicleSaleContractDetailHandler;

    private static EntityRelation entityRelation;


    static {
        entityRelation = EntityRelation.newInstance(VehicleSaleContractDetailService.class);
//        entityRelation.addSlave("contractDetailId", VehicleSaleContractInsuranceService.class);
//        entityRelation.addSlave("contractDetailId", VehicleSaleContractPresentService.class);
//        entityRelation.addSlave("contractDetailId", VehicleSaleContractItemService.class);
//        entityRelation.addSlave("contractDetailId", VehicleSaleContractChargeService.class);
//        entityRelation.addSlave("contractDetailId", VehicleSaleContractGiftService.class);
//        entityRelation.addSlave("contractDetailId", VehicleInvoiceService.class);
    }


    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleSaleContractDetail> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "beforeExecute");

        //合同号
        VehicleSaleContractDetail detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContractDetailGroups> masterProxy = entityProxy.getMaster();
        EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster().getMaster();
        VehicleSaleContracts contracts = contractsProxy.getEntity();

        VehicleSaleContractDetailGroups master = masterProxy.getEntity();
        detail.setContractNo(master.getContractNo());
        if (entityProxy.getOperation() == Operation.CREATE) {
            if(null != detail.getPlanDeliverTime() && TimestampUitls.compareWithDate(detail.getPlanDeliverTime(), new Timestamp(System.currentTimeMillis()))  == -1){
                throw new ServiceException("车辆合同明细中车辆的计划交付日期不能早于当前时间");
            }
            detail.setApproveStatus((short) 0);
            detail.setStatus((short) 10);
            detail.setDocumentNo(UUID.randomUUID().toString()); //documentNo 已无用

        }

        //是否含保费
        detail.setIsContainInsuranceCost(master.getIsContainInsuranceCost());
        detail.setVisitorNo(master.getVisitorNo());  //同步VisitorNo
        //强制同步车型信息
        detail.setVnoId(master.getVnoId());
        detail.setVehicleVno(master.getVehicleVno());
        detail.setVehicleSalesCode(master.getVehicleVno()); //VehicleSalesCode = VehicleVno
        detail.setVehicleName(master.getVehicleName());
        detail.setVehicleStrain(master.getVehicleStrain());
        detail.setVehicleQuantity(1); //强制刷新VehicleQuantity =1
//        detail.setVehicleColor(master.getVehicleColor()); //不强制更新明细的车辆颜色

        //同步佣金客户
        detail.setCustomerIdProfit(master.getCustomerIdProfit());
        detail.setCustomerNoProfit(master.getCustomerNoProfit());
        detail.setCustomerNameProfit(master.getCustomerNameProfit());
        detail.setVehicleProfit(Tools.toDouble(master.getVehicleProfit()));

        detail.setVehiclePrice(Tools.toDouble(master.getVehiclePrice()));
        detail.setDiscountAmount(Tools.toDouble(master.getDiscountAmount()));//单车优惠
        detail.setDeposit(Tools.toDouble(master.getDeposit())); //单车订金
        detail.setLargessAmount(Tools.toDouble(master.getLargessAmount())); //公司赠券
        detail.setBelongToSupplierId(master.getBelongToSupplierId());
        detail.setBelongToSupplierNo(master.getBelongToSupplierNo());
        detail.setBelongToSupplierName(master.getBelongToSupplierName());
        detail.setVehicleOwnerId(master.getVehicleOwnerId());
        detail.setVehicleName(master.getVehicleName());
        detail.setInsuranceYear(master.getInsuranceYear());
        detail.setInsuranceAppointUnit(master.getInsuranceAppointUnit());
        //如果明细中的计划交付日期有值，合同中的计划完成日期 就不同步到明细
        if (detail.getPlanDeliverTime() == null) {
            detail.setPlanDeliverTime(contracts.getPlanFinishTime());
        } else {
            //如果合同中计划完成日期修改了，明细中和计划完成日期一致的，也要改成新的计划完成日期
            if (contractsProxy.getOperation() == Operation.UPDATE) {
                VehicleSaleContracts oriContracts = contractsProxy.getOriginalEntity();
                if (oriContracts != null && oriContracts.getPlanFinishTime() != null
                        && Math.abs(oriContracts.getPlanFinishTime().getTime() - detail.getPlanDeliverTime().getTime()) < 1000 * 3600 * 24) {
                    detail.setPlanDeliverTime(contracts.getPlanFinishTime());
                }
            }
        }

        //强制同步字段“vehicle_cost_ref”，“min_sale_price”和“min_profit”，分别为“参考成本”、“最低限价”和“最低利润”
        detail.setVehicleCostRef(Tools.toDouble(master.getVehicleCostRef()));
        detail.setMinSalePrice(Tools.toDouble(master.getMinSalePrice()));
        detail.setMinProfit(Tools.toDouble(master.getMinProfit()));

        StopWatch watch0 = ContractStopWatch.startWatch(VehicleSaleContractDetailHandler.class, "beforeExecute", "handle");
        vehicleSaleContractDetailHandler.handle(entityProxy);
        ContractStopWatch.stop(watch0);

        StopWatch watch1 = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "beforeExecute", "validateRecord");
        validateRecord(entityProxy);
        ContractStopWatch.stop(watch1);

        ContractStopWatch.stop(watch);
    }

    private void validateRecord(EntityProxy<VehicleSaleContractDetail> entityProxy) {
        //校验正在改装中
        if (entityProxy.getOperation() == Operation.DELETE) {
            VehicleSaleContractDetail oriDetail = entityProxy.getOriginalEntity();
            String sql = "SELECT a.sale_contract_detail_id ,a.loan_amount,a.vehicle_price_total,a.vno_id FROM dbo.vehicle_loan_budget_details a\n" + "LEFT JOIN dbo.vehicle_loan_budget b ON b.document_no = a.document_no\n" + "WHERE b.flow_status IN (0,1,30,40,45,50,60,70) AND a.sale_contract_detail_id=:contractDetailId";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("contractDetailId", oriDetail.getContractDetailId());
            List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
            if (list != null && list.size() > 0) {
                throw new ServiceException(String.format("VIN码为【%s】已经建立消贷费用预算，不能删除", oriDetail.getVehicleVin()));
            }

            if (StringUtils.isNotEmpty(oriDetail.getVehicleVin())) {
                if (checkLoanReimbursement(oriDetail.getContractDetailId())) {
                    throw new ServiceException(String.format("车辆【%s】已做了消贷费用报销，不能删除VIN", oriDetail.getVehicleVin()));
                }
                canChangeVIN(oriDetail);
            }
            return;
        }

        VehicleSaleContractDetail detail = entityProxy.getEntity();
        VehicleSaleContractDetail oriDetail = entityProxy.getOriginalEntity();
        if (detail.getPlanDeliverTime() == null) {
            throw new ServiceException("计划交车时间不能为空");
        }

        if (Tools.toShort(detail.getApproveStatus()) == 1 || Tools.toShort(detail.getApproveStatus()) == 2) {
            if (Tools.toDouble(detail.getVehicleProfit()) > 0 && !StringUtils.equals(detail.getCustomerIdProfit(), oriDetail.getCustomerIdProfit())) {
                if (vehicleProfitPaid(detail.getContractDetailId())) {
                    throw new ServiceException(String.format("VIN码为【%s】的车辆佣金已被处理，不能更改佣金客户", detail.getVehicleVin()));
                }
            }
        }


        String vehicleId = "";
        if (entityProxy.getOperation() == Operation.CREATE && StringUtils.isNotEmpty(detail.getVehicleId())) {
            vehicleId = detail.getVehicleId();
        } else if (entityProxy.getOperation() == Operation.UPDATE && !StringUtils.equals(detail.getVehicleId(), oriDetail.getVehicleId())) {
            vehicleId = detail.getVehicleId();
        }
        if (StringUtils.isNotEmpty(vehicleId)) {
            VehicleStocks stocks = baseDao.get(VehicleStocks.class, vehicleId);
            if (stocks != null && Tools.toShort(stocks.getConversionStatus()) == 1) {
                throw new ServiceException("车辆" + stocks.getVehicleVin() + "正在改装中，不能对该车进行配车");
            }
        }

        //新增和修改都需要校验 VIN码为【%s】的车辆已被合同%s配车，不能再配车到本合同上
        if (StringUtils.isNotEmpty(detail.getVehicleId())) {
            String sql = "SELECT vc.vehicle_id,vc.vehicle_vin FROM dbo.vehicle_conversion vc \n"
                    + "LEFT JOIN dbo.vehicle_conversion_master vcm ON vc.master_no=vcm.document_no\n"
                    + "WHERE vcm.status<50 AND ISNULL(vcm.is_exists,0)=0 AND vc.vehicle_id=:vehicleId";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("vehicleId", detail.getVehicleId());
            List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
            if (list != null && list.size() > 0) {
                throw new ServiceException(String.format("VIN码为【%s】还存在未审批的库存改装单", detail.getVehicleVin()));
            }

            sql = "select a.vehicle_vin,b.contract_no from  vehicle_sale_contract_detail a LEFT JOIN vehicle_sale_contracts b on a.contract_no= b.contract_no  where  a.approve_status<>30 AND b.status<=50\n"
                    + "and a.vehicle_id =:vehicleId and b.contract_no<>:contractNo";
            params.put("contractNo", detail.getContractNo());
            list = baseDao.getMapBySQL(sql, params);
            if (list != null && list.size() > 0) {

                throw new ServiceException(String.format("VIN码为【%s】的车辆已被合同%s配车，不能再配车到本合同上", detail.getVehicleVin(), list.get(0).get("contract_no")));
            }
        }

        if (entityProxy.getOperation() == Operation.UPDATE && !StringUtils.equals(detail.getVehicleId(), oriDetail.getVehicleId())) {
            //ADM19010007 车辆销售合同模块应该允许消贷费用报销了且没配车的进行配车，取消限制
            if (!StringUtils.isEmpty(oriDetail.getVehicleId())) {
                if (checkLoanReimbursement(detail.getContractDetailId())) {
                    throw new ServiceException(String.format("车辆【%s】已经做了消贷费用报销，不能变更VIN", detail.getVehicleVin()));
                }
            }

            if (checkLoan(detail.getContractDetailId())) {
                throw new ServiceException(String.format("车辆【%s】已经做了消贷合同，不能变更VIN", detail.getVehicleVin()));
            }

            if (checkItem(detail.getContractDetailId())) {
                throw new ServiceException(String.format(" 车辆【%s】存在未完成的改装申请单，不能将VIN变成【%s】，请作废或确认完改装申请单再试", oriDetail.getVehicleVin(), detail.getVehicleVin()));

            }

            //合同状态0,1,2 才判断
            if (Tools.toShort(detail.getApproveStatus()) == 0 || Tools.toShort(detail.getApproveStatus()) == 1 || Tools.toShort(detail.getApproveStatus()) == 2) {
                //已报销的预算单费用

                //ADM19010007 车辆销售合同模块应该允许消贷费用报销了且没配车的进行配车，取消限制
                if (!StringUtils.isEmpty(oriDetail.getVehicleId())) {
                    String sql = "SELECT a.self_id,a.budget_detail_id,a.charge_id,c.charge_name,b.vno_id,b.vehicle_vin,b.sale_contract_detail_id FROM dbo.vehicle_loan_budget_charge a\n"
                            + "LEFT JOIN dbo.vehicle_loan_budget_details b ON a.budget_detail_id=b.self_id\n"
                            + "LEFT JOIN dbo.charge_catalog c ON a.charge_id=c.charge_id\n"
                            + "LEFT JOIN dbo.vehicle_loan_budget d ON d.document_no = b.document_no\n"
                            + "WHERE d.status<>2 AND d.status<>4 AND a.is_reimbursed=1 AND b.sale_contract_detail_id=:contractDetailId";
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("contractDetailId", detail.getContractDetailId());
                    List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
                    if (list != null && list.size() > 0) {
                        throw new ServiceException(String.format("VIN码为【%s】已经建立消贷费用预算并已报销，不能变更VIN", oriDetail.getVehicleVin()));
                    }
                }

                //已建立贷款合同
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("contractDetailId", detail.getContractDetailId());
                String sql = "SELECT a.sale_contract_detail_id,b.slc_no FROM vw_vehicle_loan_contracts_vehicles a \n"
                        + " LEFT JOIN dbo.vehicle_loan_contracts b ON b.slc_no = a.slc_no\n"
                        + " WHERE b.status IN (10,20,30,35) AND a.sale_contract_detail_id=:contractDetailId";
                List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
                if (list != null && list.size() > 0) {
                    throw new ServiceException(String.format("VIN码为【%s】已经建立贷款合同，不能变更VIN", oriDetail.getVehicleVin()));
                }

            }


        }
    }

    public String canChangeVinForCharge(VehicleSaleContractDetail detail) {
        if (detail == null || StringUtils.isEmpty(detail.getVehicleId())) {
            return "";
        }

        String sql = "SELECT * FROM dbo.vw_vehicle_sale_contract_detail_ex WHERE  contract_detail_id=:contractDetailId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("contractDetailId", detail.getContractDetailId());
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            Map<String, Object> result = list.get(0);

            int nCount = Tools.toInt((Integer) result.get("charge_count")); //Global.ToInt32(drv["charge_count"], 0);//费用数量
            int nRCount = Tools.toInt((Integer) result.get("charge_left_count"));//Global.ToInt32(drv["charge_left_count"], 0);//剩余未完成数量

            if (nCount > 0 && nCount != nRCount) {
                return String.format("车辆【%s】的相关费用已报销，是否仍取消配车，如果取消配车，则已报销的车辆费用自动计入车辆的其他成本中", detail.getVehicleVin());
            }
        }

        return "";
    }

    public String canChangeVinForConversion(VehicleSaleContractDetail detail) {
        if (detail == null || StringUtils.isEmpty(detail.getVehicleId())) {
            return "";
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("contractDetailId", detail.getContractDetailId());

        String sql = "SELECT a.sale_contract_item_id,b.status\n"
                + "FROM dbo.vehicle_conversion_detail a\n"
                + "INNER JOIN vehicle_conversion b ON b.conversion_no = a.conversion_no\n"
                + "LEFT JOIN vehicle_sale_contract_item c ON c.sale_contract_item_id = a.sale_contract_item_id\n"
                + "WHERE c.contract_detail_id =:contractDetailId AND ISNULL(b.is_exists,0)=1 AND a.status IN (0,1,2,30,40,50) AND b.status=60";
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            return String.format("车辆【%s】存在已完成的改装，是否仍取消配车，如果取消配车，则已做的合同改装单自动变为库存车改装", detail.getVehicleVin());
        }

        return "";
    }

    public void canChangeVIN(VehicleSaleContractDetail detail) {
        if (detail == null || StringUtils.isEmpty(detail.getVehicleId())) {
            return;
        }

        if (checkLoan(detail.getContractDetailId())) {
            throw new ServiceException(String.format("车辆【%s】已建立了贷款合同，不能删除或变更VIN", detail.getVehicleVin()));
        }


        String hql = "from  FinanceDocumentEntries   WHERE documentType='车辆-客户佣金' AND documentId =?";
        List<FinanceDocumentEntries> results = (List<FinanceDocumentEntries>) baseDao.findByHql(hql, new Object[]{detail.getContractDetailId()});
        if (results != null && results.size() > 0) {
            for (FinanceDocumentEntries entries : results) {
                if (StringUtils.isNotEmpty(entries.getAfterNo())) {
                    throw new ServiceException(String.format("车辆【%s】已做了佣金处理，不能删除或变更VIN", detail.getVehicleVin()));
                }
            }
        }

        String sql = "SELECT * FROM dbo.vw_vehicle_sale_contract_detail_ex WHERE  contract_detail_id=:contractDetailId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("contractDetailId", detail.getContractDetailId());
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            Map<String, Object> result = list.get(0);
            if (result.get("real_deliver_time") != null) {
                throw new ServiceException(String.format("车辆【%s】已做出库处理，不能删除或变更VIN", detail.getVehicleVin()));
            }

            int nCount = Tools.toInt((Integer) result.get("inst_count"));//险种数量
            int nRCount = Tools.toInt((Integer) result.get("inst_left_count"));//剩余未完成数据
            if (nCount > 0 && nCount != nRCount) {
                throw new ServiceException(String.format("车辆【%s】已购买保险，不能删除或变更VIN", detail.getVehicleVin()));
            }

            nCount = Tools.toInt((Integer) result.get("present_count"));//Global.ToInt32(drv["present_count"], 0);//精品数量
            nRCount = Tools.toInt((Integer) result.get("present_left_count"));// Global.ToInt32(drv["present_left_count"], 0);//剩余未完成数量

            if (nCount > 0 && nCount != nRCount) {
                throw new ServiceException(String.format("车辆【%s】赠送的精品已出库，不能删除或变更VIN", detail.getVehicleVin()));
            }

//            nCount = Tools.toInt((Integer) result.get("charge_count")); //Global.ToInt32(drv["charge_count"], 0);//费用数量
//            nRCount = Tools.toInt((Integer) result.get("charge_left_count"));//Global.ToInt32(drv["charge_left_count"], 0);//剩余未完成数量
//
//            if (nCount > 0 && nCount != nRCount) {
//                throw new ServiceException(String.format("车辆【%s】的相关费用已报销，不能删除或变更VIN", detail.getVehicleVin()));
//            }

            if (Tools.toDouble((Double) result.get("ticket_out_stock_amount")) > 0) {
                throw new ServiceException(String.format("车辆【%s】的公司赠券已发放，不能删除或变更VIN", detail.getVehicleVin()));
            }
        }

        sql = "SELECT a.sale_contract_item_id,b.status\n"
                + "FROM dbo.vehicle_conversion_detail a\n"
                + "INNER JOIN vehicle_conversion b ON b.conversion_no = a.conversion_no\n"
                + "LEFT JOIN vehicle_sale_contract_item c ON c.sale_contract_item_id = a.sale_contract_item_id\n"
                + "WHERE c.contract_detail_id =:contractDetailId AND ISNULL(b.is_exists,0)=1 AND a.status IN (0,1,2,30,40,50) AND b.status<60";
        list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            throw new ServiceException(String.format("车辆【%s】存在未完成的改装申请单，不能删除或变更VIN，请作废或确认完改装申请单再试", detail.getVehicleVin()));
        }

        sql = "SELECT a.sale_contract_insurance_id FROM dbo.insurance_detail a\n" + "LEFT JOIN dbo.insurance b ON a.insurance_no=b.insurance_no\n" + "WHERE b.approve_status IN (0,1) AND b.contract_detail_id=:contractDetailId";

        list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            throw new ServiceException(String.format("车辆【%s】已有明细建立保单了，不能删除或变更VIN", detail.getVehicleVin()));
        }

        sql = "select * from finance_document_entries where document_id in (select sale_contract_charge_id from vehicle_sale_contract_charge  where contract_detail_id = :contractDetailId) AND ISNULL(after_no,'')<>''";
        list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            Map<String, Object> map = list.get(0);
            throw new ServiceException(String.format("车辆【%s】中费用单据分录【%s】 财务已处理，不能删除或变更VIN", detail.getVehicleVin(), map.get("document_type")));
        }
    }

    private boolean checkItem(String contractDetailId) {
        String sql = "SELECT a.sale_contract_item_id\n"
                + "FROM dbo.vehicle_conversion_detail a\n"
                + "LEFT JOIN vehicle_conversion b ON b.conversion_no = a.conversion_no\n"
                + "LEFT JOIN vehicle_sale_contract_item c ON c.sale_contract_item_id = a.sale_contract_item_id\n"
                + "WHERE c.contract_detail_id =:contractDetailId AND ISNULL(b.is_exists,0)=1 AND a.status IN (0,1,2,30,40,50) AND b.status<60";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("contractDetailId", contractDetailId);
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            return true;
        }

        return false;
    }

    private boolean checkLoan(String contractDetailId) {
        String sql = "SELECT b.slc_no FROM dbo.vw_vehicle_loan_contracts_vehicles a\n" + "LEFT JOIN dbo.vehicle_loan_contracts b ON b.slc_no = a.slc_no\n" + " WHERE b.status<>40 AND b.status<>50 AND sale_contract_detail_id =:contractDetailId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("contractDetailId", contractDetailId);
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            return true;
        }

        return false;
    }

    private boolean checkLoanReimbursement(String contractDetailId) {
        String sql = "SELECT a.self_id FROM dbo.vehicle_loan_budget_charge a\n"
                + "LEFT JOIN dbo.vehicle_loan_budget_details b ON a.budget_detail_id=b.self_id\n"
                + "LEFT JOIN dbo.vehicle_loan_budget c ON c.document_no = b.document_no\n"
                + "WHERE a.is_reimbursed=1 AND c.flow_status IN (1,30,40,45,50,60,70) AND b.sale_contract_detail_id =:contractDetailId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("contractDetailId", contractDetailId);
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            return true;
        }


        return false;
    }

    private boolean vehicleProfitPaid(String contractDetailId) {
        List<FinanceDocumentEntries> finaceList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? and documentType = ?", contractDetailId, "车辆-客户佣金");
        if (finaceList != null && finaceList.size() > 0) {
            for (FinanceDocumentEntries e : finaceList) {
                if (StringUtils.isNotEmpty(e.getAfterNo())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void execute(EntityProxy<VehicleSaleContractDetail> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "execute");

        StopWatch watch0 = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "execute", "setLastDeliverTime");
        this.setLastDeliverTime(entityProxy); //设置上次交付日期，数据中心报表需要
        ContractStopWatch.stop(watch0);

        StopWatch watch1 = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "execute", "computationVehiclePriceTotal");
        this.computationVehiclePriceTotal(entityProxy);
        ContractStopWatch.stop(watch1);

        StopWatch watch2 = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "execute", "dealDataBeforeSave");
        this.dealDataBeforeSave(entityProxy);
        ContractStopWatch.stop(watch2);

        StopWatch watch3 = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "execute", "updateAllVS");
        this.updateAllVS(entityProxy);
        ContractStopWatch.stop(watch3);

        StopWatch watch4 = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "execute", "dealCustomerCommission");
        this.dealCustomerCommission(entityProxy);
        ContractStopWatch.stop(watch4);

        StopWatch watch5 = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "execute", "updateVehicleArchive");
        this.updateVehicleArchive(entityProxy);
        ContractStopWatch.stop(watch5);

        StopWatch watch6 = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "execute", "updateBudget");
        this.updateBudget(entityProxy);
        ContractStopWatch.stop(watch6);

        StopWatch watch7 = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "execute", "dealForChargeAndConversion");
        this.dealForChargeAndConversion(entityProxy);
        ContractStopWatch.stop(watch7);

        ContractStopWatch.stop(watch);
    }

    //ADM19030076- 允许已完成的合同改装、车辆费用报销的车辆取消配车
    private void dealForChargeAndConversion(EntityProxy<VehicleSaleContractDetail> entityProxy) {
        String newVehicleId = "";
        VehicleSaleContractDetail detail = entityProxy.getEntity();
        //取消配车或换车
        if (entityProxy.getOperation() == Operation.DELETE) {
            if (StringUtils.isEmpty(detail.getVehicleId())) {
                return;
            }
        } else if (entityProxy.getOperation() == Operation.UPDATE) {
            VehicleSaleContractDetail oriDetail = entityProxy.getOriginalEntity();
            //如果原来没有配车，或者没有换车，跳过
            if (StringUtils.isEmpty(oriDetail.getVehicleId()) || StringUtils.equals(detail.getVehicleId(), oriDetail.getVehicleId())) {
                return;
            }
            newVehicleId = detail.getVehicleId();
        } else {
            //CREATE 或 NONE
            return;
        }

//        1、取消配车或更换车时，被取消或被更换的车，要判断下是否做过车辆费用报销（对合同的），
// 如果有，保存后将这部分值累计入vehicle_stocks表的other_cost字段中。同时更新vehicle_sale_contract_detail表的字段charge_cost为０，
// 更新vehicle_sale_contract_charge表字段charge_cost为０，cost_status为０。
// 对应的车辆费用报销明细office_charge_reimbursements_details表的字段contract_no、vscv_id、reimbursement_difference和vscc_id更新为NULL）
        String sql = "SELECT a.* FROM dbo.office_charge_reimbursements_details a\n" +
                "INNER JOIN dbo.office_charge_reimbursements b ON a.document_no=b.document_no\n" +
                "INNER JOIN dbo.vehicle_sale_contract_detail c ON a.vscv_id=c.contract_detail_id\n" +
                "WHERE b.status=50 AND c.contract_detail_id = :contractDetailId";
        Map<String, Object> params = new HashMap<>(1);
        params.put("contractDetailId", detail.getContractDetailId());
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> item : list) {
                List<VehicleStocks> stocks = (List<VehicleStocks>) baseDao.findByHql("FROM VehicleStocks where vehicleVin = ? ", (String) item.get("vehicle_vin"));
                if (stocks != null && stocks.size() > 0) {
                    VehicleStocks stock = stocks.get(0);
                    stock.setOtherCost(Tools.toDouble(stock.getOtherCost()) + Tools.toDouble((Double) item.get("charge_amount")));
                }

                VehicleSaleContractCharge contractCharge = baseDao.get(VehicleSaleContractCharge.class, (String) item.get("vscc_id"));
                if (contractCharge != null) {
                    contractCharge.setChargeCost(BigDecimal.ZERO);
                    contractCharge.setCostStatus((short) 0);
                }

                ChargeReimbursementsDetails reimbursementsDetails = baseDao.get(ChargeReimbursementsDetails.class, (String) item.get("ocrd_id"));
                if (reimbursementsDetails != null) {
                    reimbursementsDetails.setContractNo(null);
                    reimbursementsDetails.setVscvId(null);
                    reimbursementsDetails.setReimbursementDifference(0.00D);
                    reimbursementsDetails.setVsccId(null);
                }
            }
            detail.setChargeCost(0.00D);
        }

//        2、取消配车或更换车时，被取消或被更换的车，要判断下是否做过合同改申请单，如果有则，保存后如下处理：
//        a)更新vehicle_conversion表的字段is_exists为0，vscd_id字段为NULL
//        b)更新vehicle_conversion_detail字段sale_contract_item_id为NULL
//        c)如果是取消配车：更新 vehicle_sale_contract_detail表的字段conversion_cost为0
//        如果是换车：更新vehicle_sale_contract_detail表的字段conversion_cost的值为新换车的改装金额（取值 SELECT modified_fee FROM dbo.vehicle_stocks WHERE vehicle_id='XXX'）
        sql = "SELECT a.conversion_detail_id,a.sale_contract_item_id,b.status\n"
                + "FROM dbo.vehicle_conversion_detail a\n"
                + "INNER JOIN vehicle_conversion b ON b.conversion_no = a.conversion_no\n"
                + "LEFT JOIN vehicle_sale_contract_item c ON c.sale_contract_item_id = a.sale_contract_item_id\n"
                + "WHERE c.contract_detail_id =:contractDetailId AND ISNULL(b.is_exists,0)=1 AND a.status IN (0,1,2,30,40,50) AND b.status=60";
        list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> item : list) {
                VehicleConversionDetail conversionDetail = baseDao.get(VehicleConversionDetail.class, (String) item.get("conversion_detail_id"));
                if (conversionDetail != null) {
                    conversionDetail.setSaleContractItemId(null);
                    VehicleConversion conversion = baseDao.get(VehicleConversion.class, conversionDetail.getConversionNo());
                    if (conversion != null) {
                        conversion.setIsExists(false);
                        conversion.setVscdId(null);
                    }
                }
            }
        }

        if (StringUtils.isEmpty(newVehicleId)) {
            //如果是取消配车：更新 vehicle_sale_contract_detail表的字段conversion_cost为0
            detail.setConversionCost(0.00D);
        } else {
            //如果是换车：更新vehicle_sale_contract_detail表的字段conversion_cost的值为新换车的改装金额（取值 SELECT modified_fee FROM dbo.vehicle_stocks WHERE vehicle_id='XXX'）
            VehicleStocks stocks = baseDao.get(VehicleStocks.class, newVehicleId);
            if (stocks != null) {
                detail.setConversionCost(stocks.getModifiedFee());
            }
        }
    }


    /**
     * 设置上次交付日期，数据中心报表需要
     *
     * @param entityProxy
     */
    private void setLastDeliverTime(EntityProxy<VehicleSaleContractDetail> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE || entityProxy.getOperation() == Operation.CREATE) {
            return;
        }
        EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster().getMaster();
        VehicleSaleContracts contracts = contractsProxy.getEntity();
        //last_deliver_time 这个日期只在合同审批之后修改的才记录，审批之前修改的不记录
        if (Tools.toShort(contracts.getStatus()) == (short) 50) {
            VehicleSaleContractDetail contractDetail = entityProxy.getEntity();
            VehicleSaleContractDetail oriDetail = entityProxy.getOriginalEntity();
            long t1 = contractDetail.getPlanDeliverTime() == null ? 0L : contractDetail.getPlanDeliverTime().getTime();
            long t2 = (oriDetail == null || oriDetail.getPlanDeliverTime() == null) ? 0L : oriDetail.getPlanDeliverTime().getTime();
            if (t1 > t2) {
                //计划交付日期延后了
                if (oriDetail != null && oriDetail.getPlanDeliverTime() != null) {
                    contractDetail.setLastDeliverTime(oriDetail.getPlanDeliverTime());
                }
            }
        }
    }


    /**
     * 计算车辆费用合计
     *
     * @param entityProxy
     */
    private void computationVehiclePriceTotal(EntityProxy<VehicleSaleContractDetail> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        VehicleSaleContractDetail contractDetail = entityProxy.getEntity();

        BigDecimal dVehiclePrice = Tools.toBigDecimal(contractDetail.getVehiclePrice());
        BigDecimal dDiscount_amount = Tools.toBigDecimal(contractDetail.getDiscountAmount());
        BigDecimal dInsuranceIncomeTotal = BigDecimal.ZERO;
        BigDecimal dPresentIncomeTotal = null;
        BigDecimal dItemIncomeTotal = null;
        BigDecimal dChargeIncomeTotal = null;
        BigDecimal dVehiclePriceTotal = null;
        BigDecimal dEnergyConservationSubsidy = Tools.toBigDecimal(contractDetail.getEnergyConservationSubsidy());

        dPresentIncomeTotal = EntityProxyUtil.sum(entityProxy, VehicleSaleContractPresent.class.getSimpleName(), "income");
        dItemIncomeTotal = EntityProxyUtil.sum(entityProxy, VehicleSaleContractItem.class.getSimpleName(), "income");
        dChargeIncomeTotal = EntityProxyUtil.sum(entityProxy, VehicleSaleContractCharge.class.getSimpleName(), "income");

        dVehiclePriceTotal = dVehiclePrice.subtract(dDiscount_amount).add(dInsuranceIncomeTotal).add(dPresentIncomeTotal).add(dItemIncomeTotal).add(dChargeIncomeTotal).subtract(dEnergyConservationSubsidy);

        if (Tools.toBigDecimal(contractDetail.getVehiclePriceTotal()).compareTo(dVehiclePriceTotal) != 0) {
            contractDetail.setVehiclePriceTotal(dVehiclePriceTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        BigDecimal dTotInsurance = EntityProxyUtil.sum(entityProxy, VehicleSaleContractInsurance.class.getSimpleName(), "categoryIncome");
        if (Tools.toBigDecimal(contractDetail.getInsuranceIncome()).compareTo(dTotInsurance) != 0) {
            contractDetail.setInsuranceIncome(dTotInsurance.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        BigDecimal dPresentIncome = EntityProxyUtil.sum(entityProxy, VehicleSaleContractPresent.class.getSimpleName(), "income");
        if (Tools.toBigDecimal(contractDetail.getPresentIncome()).compareTo(dPresentIncome) != 0) {
            contractDetail.setPresentIncome(dPresentIncome.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        BigDecimal dItemIncome = EntityProxyUtil.sum(entityProxy, VehicleSaleContractItem.class.getSimpleName(), "income");
        if (Tools.toBigDecimal(contractDetail.getConversionIncome()).compareTo(dItemIncome) != 0) {
            contractDetail.setConversionIncome(dItemIncome.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }


    }


    private void dealDataBeforeSave(EntityProxy<VehicleSaleContractDetail> entityProxy) {
        if (entityProxy.getOperation() != Operation.DELETE) {
            VehicleSaleContractDetail contractDetail = entityProxy.getEntity();
            if (StringUtils.isNotEmpty(contractDetail.getVehicleId())) {
                List<VehicleStocks> stocksList = (List<VehicleStocks>) baseDao.findByHql("FROM VehicleStocks WHERE vehicleId = ? ", contractDetail.getVehicleId());
                if (stocksList != null && stocksList.size() > 0) {
                    contractDetail.setWarehouseId(stocksList.get(0).getWarehouseId());
                    contractDetail.setWarehouseName(stocksList.get(0).getWarehouseName());
                }
            }
        }

    }


    /**
     * 更新车辆库存信息
     *
     * @param entityProxy
     */
    private void updateAllVS(EntityProxy<VehicleSaleContractDetail> entityProxy) {
        EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster().getMaster();
        VehicleSaleContracts contracts = contractsProxy.getEntity();

        if (entityProxy.getOperation() == Operation.DELETE) {
            VehicleSaleContractDetail contractDetail = entityProxy.getEntity();
            clearVehicleStocks(contractDetail.getVehicleId());
        } else {

            VehicleSaleContractDetail oriContractDetail = entityProxy.getOriginalEntity();
            VehicleSaleContractDetail contractDetail = entityProxy.getEntity();
            /*//已终止或已出库的不更新
            if (contractDetail.getApproveStatus() == (short) 30 || contractDetail.getRealDeliverTime() != null) {
                return;
            }*/
            //已终止不更新
            if (Tools.toShort(contractDetail.getApproveStatus()) == (short) 30 || contractDetail.getRealDeliverTime() != null) {
                return;
            }
            //已出库需更新合同号
            if (contractDetail.getRealDeliverTime() != null) {
                if (StringUtils.isNotEmpty(contractDetail.getVehicleId())) {
                    List<VehicleStocks> stocksList = (List<VehicleStocks>) baseDao.findByHql("FROM VehicleStocks WHERE isnull(status,0) IN (0,1) AND vehicleId =?", contractDetail.getVehicleId());
                    if (stocksList != null && stocksList.size() > 0) {
                        VehicleStocks stocks = stocksList.get(0);
                        stocks.setSaleContractNo(contracts.getContractNo());
                        stocks.setSaleContractCode(contracts.getContractCode());
                    }
                }
                return;
            } else {
                if (StringUtils.isNotEmpty(contractDetail.getVehicleId())) {
                    List<VehicleStocks> stocksList = (List<VehicleStocks>) baseDao.findByHql("FROM VehicleStocks WHERE isnull(status,0) IN (0,1) AND vehicleId =?", contractDetail.getVehicleId());
                    if (stocksList != null && stocksList.size() > 0) {
                        VehicleStocks stocks = stocksList.get(0);
                        stocks.setStatus((short) 1);
                        stocks.setSaleContractNo(contracts.getContractNo());
                        stocks.setSaleContractCode(contracts.getContractCode());
                        stocks.setCustomerId(contracts.getCustomerId());
                        stocks.setCustomerName(contracts.getCustomerName());
                        stocks.setSeller(contracts.getSeller());
                        stocks.setSellerId(contracts.getSellerId());
                    }
                }
            }

            //换车
            if (entityProxy.getOperation() == Operation.UPDATE && !StringUtils.equals(oriContractDetail.getVehicleId(), contractDetail.getVehicleId())) {
                clearVehicleStocks(oriContractDetail.getVehicleId());
            }
        }
    }


    public void clearVehicleStocks(String vehicleId) {
        if (StringUtils.isEmpty(vehicleId)) {
            return;
        }
        List<VehicleStocks> stocksList = (List<VehicleStocks>) baseDao.findByHql("FROM VehicleStocks WHERE isnull(status,0) IN (0,1) AND vehicleId =?", vehicleId);
        if (stocksList != null && stocksList.size() > 0) {
            VehicleStocks stocks = stocksList.get(0);
            stocks.setStatus((short) 0);
            stocks.setSaleContractNo(null);
            stocks.setSaleContractCode(null);
            stocks.setCustomerId(null);
            stocks.setCustomerName(null);
            stocks.setSeller(null);
            stocks.setSellerId(null);
        }
    }


    /**
     * 更新车辆档案
     *
     * @param entityProxy
     */
    private void updateVehicleArchive(EntityProxy<VehicleSaleContractDetail> entityProxy) {
        VehicleSaleContractDetail oriContractDetail = entityProxy.getOriginalEntity();
        VehicleSaleContractDetail contractDetail = entityProxy.getEntity();

//        vc_approve_status2	0	未提交
//        vc_approve_status2	1	已审批
//        vc_approve_status2	2	已审核
//        vc_approve_status2	20	待审批
//        vc_approve_status2	30	已终止
//        vc_approve_status2	40	已退车
        //修改生成档案的逻辑：明细未提交（approve_status = 0）时就生成车辆档案，不要等到审批后，作废的时候会清除车辆档案的关系
        if (Tools.toShort(contractDetail.getApproveStatus()) != 0 && Tools.toShort(contractDetail.getApproveStatus()) != 1 && Tools.toShort(contractDetail.getApproveStatus()) != 2) {
            return;
        }
        EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster().getMaster();
        VehicleSaleContracts contracts = contractsProxy.getEntity();

        if (entityProxy.getOperation() == Operation.DELETE) {
            clearVehicleArchiveRelation(contractDetail);
        } else if (entityProxy.getOperation() == Operation.CREATE) {
            addVehicleArchiveRelation(contractDetail, contracts);
        } else if (entityProxy.getOperation() == Operation.UPDATE) {
            //可能存在换车的情况，因此先清除，在新增
            clearVehicleArchiveRelation(oriContractDetail);
            addVehicleArchiveRelation(contractDetail, contracts);
        } else {
            //do nothing
        }
    }


    //查询佣金分录 WITH (NOLOCK)
    private List<Map<String, Object>> getCommissionEntryWithNoLock(VehicleSaleContractDetail detail) {
        StopWatch watch0 = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "execute", "dealCustomerCommission", "查询finance_document_entries WITH (NOLOCK)");
        String sql = "select * from finance_document_entries WITH (NOLOCK) where document_id=:documentId AND document_type=:documentType";
        Map<String, Object> params = new HashMap<>(2);
        params.put("documentId", detail.getContractDetailId());
        params.put("documentType", "车辆-客户佣金");
        List<Map<String, Object>> result = baseDao.getMapBySQL(sql, params);
        return result;
    }

    /**
     * 处理佣金
     *
     * @param entityProxy
     */
    private void dealCustomerCommission(EntityProxy<VehicleSaleContractDetail> entityProxy) {
        VehicleSaleContractDetail oriContractDetail = entityProxy.getOriginalEntity();
        VehicleSaleContractDetail detail = entityProxy.getEntity();

        if (entityProxy.getOperation() == Operation.DELETE || Tools.toDouble(detail.getVehicleProfit()) == 0.00D) {
            List<Map<String, Object>> result = getCommissionEntryWithNoLock(detail);
            //佣金为0，删除佣金分录
            if (result.size() > 0) {
                if (StringUtils.isEmpty((String) result.get(0).get("after_no"))) {
                    baseDao.getCurrentSession().createSQLQuery("DELETE FROM finance_document_entries WHERE entry_id =:entryId").setParameter("entryId", (String) result.get(0).get("entry_id")).executeUpdate();
                } else {
                    throw new ServiceException(String.format("佣金单据分录%s,已经请了款，不能删除车辆或将佣金修改为0", (String) result.get(0).get("document_no")));
                }
            }
        } else {
            EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster().getMaster();
            VehicleSaleContracts contract = contractsProxy.getEntity();

            String sCId = StringUtils.isEmpty(detail.getCustomerIdProfit()) ? contract.getCustomerId() : detail.getCustomerIdProfit();
            String sCNo = StringUtils.isEmpty(detail.getCustomerIdProfit()) ? contract.getCustomerNo() : detail.getCustomerNoProfit();
            String sCName = StringUtils.isEmpty(detail.getCustomerIdProfit()) ? contract.getCustomerName() : detail.getCustomerNameProfit();
            String documentNo = (StringUtils.isEmpty(detail.getVehicleVin()) ? "" : detail.getVehicleVin()) + "," + contract.getContractNo();
            double dPriceProfit = Tools.toDouble(detail.getVehicleProfit());

            List<Map<String, Object>> result = getCommissionEntryWithNoLock(detail);
            if (result == null || result.size() == 0) {
                //没有的新增分录
                financeDocumentEntriesDao.insertEntry(contract.getStationId(), 1, (short) 65, "车辆-客户佣金", detail.getContractDetailId(), sCId, sCNo, sCName, (short) 70, dPriceProfit, documentNo, null);
            } else {
                StopWatch watch0 = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "execute", "dealCustomerCommission", "查询FinanceDocumentEntries");
                List<FinanceDocumentEntries> finaceList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? and documentType = ?", detail.getContractDetailId(), "车辆-客户佣金");
                ContractStopWatch.stop(watch0);

                FinanceDocumentEntries financeDocumentEntries = finaceList.get(0);
                if (StringUtils.isEmpty(financeDocumentEntries.getAfterNo())) {
                    //没有后续操作，直接修改分录
                    financeDocumentEntries.setLeftAmount(dPriceProfit);
                    financeDocumentEntries.setDocumentAmount(dPriceProfit);
                    financeDocumentEntries.setObjectId(sCId);
                    financeDocumentEntries.setObjectNo(sCNo);
                    financeDocumentEntries.setObjectName(sCName);
                    financeDocumentEntries.setDocumentNo(documentNo);
                } else {
                    //配车时，佣金金额和佣金客户没变的情况下应该允许保存 -20190214
                    if (Tools.toDouble(financeDocumentEntries.getDocumentAmount()) != dPriceProfit || !sCId.equals(financeDocumentEntries.getObjectId())) {
                        throw new ServiceException(String.format("佣金单据分录%s,已经请了款，不能更改佣金金额或佣金客户或VIN", financeDocumentEntries.getDocumentNo()));
                    }

                    //那如果换车呢，是否允许保存，如果允许的话，那应该还得更新之前所生成的单据分录和请款单上的单据编号（单据编号上有VIN） -20190214
                    if (!documentNo.equals(financeDocumentEntries.getDocumentNo())) {
                        financeDocumentEntries.setDocumentNo(documentNo);

                        //修改请款单上的单据编号
                        StopWatch watch1 = ContractStopWatch.startWatch(VehicleSaleContractDetailService.class, "execute", "dealCustomerCommission", "FinancePaymentRequestsDetails");
                        List<FinancePaymentRequestsDetails> requestsDetailsList = (List<FinancePaymentRequestsDetails>) baseDao.findByHql("FROM FinancePaymentRequestsDetails where entryId = ?", financeDocumentEntries.getEntryId());
                        ContractStopWatch.stop(watch1);

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

    /**
     * 更新预算单
     *
     * @param entityProxy
     */
    private void updateBudget(EntityProxy<VehicleSaleContractDetail> entityProxy) {
        if (entityProxy.getOperation() == Operation.CREATE) {
            return;
        }
        VehicleSaleContractDetail oriContractDetail = entityProxy.getOriginalEntity();
        VehicleSaleContractDetail detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster().getMaster();
        VehicleSaleContracts contracts = contractsProxy.getEntity();


        String sql = "SELECT a.* FROM dbo.vehicle_loan_budget_details a WITH ( NOLOCK )\n"
                + "LEFT JOIN dbo.vehicle_loan_budget b  WITH ( NOLOCK ) " +
                "ON b.document_no = a.document_no WHERE b.flow_status IN (0,1,30,40,45,50,60,70) AND a.sale_contract_detail_id =:saleContractDetailId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("saleContractDetailId", detail.getContractDetailId());
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> result : list) {
                VehicleLoanBudgetDetails budgetDetail = baseDao.get(VehicleLoanBudgetDetails.class, (String) result.get("self_id"));

                //如果更新了VIN
                if (!StringUtils.equals(detail.getVehicleVin(), (String) result.get("vehicle_vin"))) {
                    List<VehicleLoanBudgetCharge> chargeList = (List<VehicleLoanBudgetCharge>) baseDao.findByHql("FROM VehicleLoanBudgetCharge WHERE budgetDetailId = ? ", result.get("self_id"));
                    for (VehicleLoanBudgetCharge charge : chargeList) {
                        //ADM19010007 车辆销售合同模块应该允许消贷费用报销了且没配车的进行配车，取消限制
                        if (Tools.toBoolean(charge.getIsReimbursed()) && StringUtils.isNotEmpty(oriContractDetail.getVehicleVin())) {
                            throw new ServiceException(String.format("车辆%s对应的预算费用，已经做了报销处理，不能变更VIN", detail.getVehicleVin()));
                        }
                        //筛选当前预算单明细的费用单据分录
                        List<FinanceDocumentEntries> finaceList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? OR documentId = ?", charge.getSelfId(), charge.getSelfId() + "_1");
                        for (FinanceDocumentEntries entry : finaceList) {
                            //如果单据分录做了后续处理，则要判断原来VIN是否是空，如果原来是空，现在VIN变了(配车)也不提示，否则提示不能更换VIN
                            if (StringUtils.isNotEmpty(entry.getAfterNo())) {
                                if (StringUtils.isNotEmpty(oriContractDetail.getVehicleVin())) {
                                    throw new ServiceException(String.format("车辆%s对应的预算费用财务已经做了后续处理，不能变更VIN", detail.getVehicleVin()));
                                }
                            }

                            String sBudGetNo = budgetDetail.getDocumentNo();
                            String sVscNo = contracts.getDocumentNo();
                            String sObjName = contracts.getCustomerName();
                            String sVin = StringUtils.isEmpty(detail.getVehicleVin()) ? "" : ("," + detail.getVehicleVin());

                            if (Tools.toShort(entry.getEntryType(), (short) 10) == 10) {
                                entry.setDocumentNo(sBudGetNo + "," + sVscNo + sVin);

                            } else {
                                entry.setDocumentNo(sBudGetNo + "," + sVscNo + "," + sObjName + sVin);
                            }
                        }
                    }
                    budgetDetail.setVehicleVin(detail.getVehicleVin());
                }

                //如果车型、车款合计、公告型号变了
                if (!StringUtils.equals(detail.getVnoId(), budgetDetail.getVnoId()) || !StringUtils.equals(detail.getVnoIdNew(), budgetDetail.getBulletinId()) || !StringUtils.equals(detail.getVehicleVnoNew(), budgetDetail.getBulletinNo()) || Tools.toDouble(detail.getVehiclePriceTotal()) != Tools.toDouble(budgetDetail.getVehiclePriceTotal())) {
                    if (Tools.toDouble(detail.getVehiclePriceTotal()) < Tools.toDouble(budgetDetail.getLoanAmount())) {
                        throw new ServiceException(String.format("车款合计%s不能小于已建立贷款预算的车辆贷款%s", Tools.toDouble(detail.getVehiclePriceTotal()), Tools.toDouble(budgetDetail.getLoanAmount())));
                    }
                    budgetDetail.setVehiclePriceTotal(detail.getVehiclePriceTotal());
                    budgetDetail.setVnoId(detail.getVnoId());
                    budgetDetail.setBulletinId(detail.getVnoIdNew());
                    budgetDetail.setBulletinNo(detail.getVehicleVnoNew());

                }

                //如果挂靠单位变更
                if (!StringUtils.equals(detail.getBelongToSupplierId(), budgetDetail.getAffiliatedCompanyId())) {
                    budgetDetail.setAffiliatedCompanyId(detail.getBelongToSupplierId());
                    //如果贷款合同已审批，则可能已经做了征信调查或贷款合同，如果做了则一并更新
                    if (Tools.toShort(detail.getAbortStatus()) == 1) {
                        sql = "SELECT a.*,b.document_no as budno FROM dbo.vehicle_loan_credit_investigation a\n" + "LEFT JOIN dbo.vehicle_loan_budget b ON a.budget_no=b.document_no\n" + "WHERE a.status<=60 AND b.status =50 AND b.document_no IN (\n" + "SELECT document_no FROM dbo.vehicle_loan_budget_details WHERE sale_contract_detail_id =:saleContractDetailId)\n" + "UNION\n" + "SELECT a.*,c.document_no as budno FROM dbo.vehicle_loan_credit_investigation a\n" + "LEFT JOIN dbo.vehicle_loan_investigation_arrange b ON a.arrange_no=b.arrange_no\n" + "LEFT JOIN dbo.vehicle_loan_budget c ON b.budget_no=c.document_no\n" + "WHERE a.status<=60 AND b.status=20 AND c.status=50 AND c.document_no IN (\n" + "SELECT document_no FROM dbo.vehicle_loan_budget_details WHERE sale_contract_detail_id =:saleContractDetailId)";
                        Map<String, Object> paramsMap = new HashMap<String, Object>();
                        paramsMap.put("saleContractDetailId", detail.getContractDetailId());
                        List<Map<String, Object>> resultList = baseDao.getMapBySQL(sql, paramsMap);
                        for (Map<String, Object> res : resultList) {
                            VehicleLoanCreditInvestigation investigation = baseDao.get(VehicleLoanCreditInvestigation.class, (String) res.get("document_no"));
                            investigation.setAffiliatedCompanyId(budgetDetail.getAffiliatedCompanyId());
                        }

                        //同时更新消贷合同
                        sql = "SELECT a.* FROM dbo.vehicle_loan_contracts a\n" + "WHERE a.status IN (10,20,30,35) AND a.slc_no IN (SELECT slc_no FROM dbo.vehicle_loan_contracts_vehicles WHERE budget_detail_id\n" + "IN (SELECT self_id FROM dbo.vehicle_loan_budget_details WHERE sale_contract_detail_id =:saleContractDetailId)";
                        Map<String, Object> paramsMap2 = new HashMap<String, Object>();
                        paramsMap2.put("saleContractDetailId", detail.getContractDetailId());
                        List<Map<String, Object>> resultList2 = baseDao.getMapBySQL(sql, paramsMap2);
                        for (Map<String, Object> res : resultList2) {
                            VehicleLoanContracts loanContracts = baseDao.get(VehicleLoanContracts.class, (String) res.get("slc_no"));
                            loanContracts.setAffiliatedCompanyId(detail.getBelongToSupplierId());
                        }
                    }
                }
            }
        }

    }


    private void addVehicleArchiveRelation(VehicleSaleContractDetail detail, VehicleSaleContracts contract) {
        if (StringUtils.isEmpty(detail.getVehicleId()) && StringUtils.isEmpty(detail.getVehicleVin())) {
            return;
        }

        //ADM18100073
        String underpanNo = "";
        if (detail.getVehicleVin() != null && detail.getVehicleVin().length() >= 8) {
            underpanNo = detail.getVehicleVin().substring(detail.getVehicleVin().length() - 8);
        } else {
            underpanNo = detail.getVehicleVin();
        }
        List<VehicleStocks> stocksList = (List<VehicleStocks>) baseDao.findByHql("FROM VehicleStocks WHERE  underpanNo=? AND vnoId =? AND  ISNULL(inStockNo,'')<>'' ORDER BY inStockTime desc", underpanNo, detail.getVnoId());
//        List<VehicleStocks> stocksList = (List<VehicleStocks>) baseDao.findByHql("FROM VehicleStocks WHERE isnull(status,0) IN (0,1) AND vehicleId =?", detail.getVehicleId());
        if (stocksList == null || stocksList.size() == 0) {
            return;
        }

        //ADM18100073
        //底盘为销售合同VIN后8位，如果能找到相同车型相同底盘的，如果有多个，且当前销售合同的vehicle_id为入库时间最晚的一那台时，更新档案，否则不更新。
        if (!StringUtils.equals(stocksList.get(0).getVehicleId(), detail.getVehicleId())) {
            return;
        }

        SysUsers user = HttpSessionStore.getSessionUser();
        List<VehicleArchives> vehicleArchiveList = (List<VehicleArchives>) baseDao.findByHql("from VehicleArchives where vehicleId = ? OR vehicleVin = ? ", detail.getVehicleId(), detail.getVehicleVin());

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
        vehicleArchive.setVehicleVin(detail.getVehicleVin());
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
        vehicleArchive.setVehicleComment(String.format("新VIN码：%s,合同号：%s", detail.getVehicleVinNew(), detail.getContractNo()));


        logger.debug(String.format("更新车辆档案VehicleArchives：vehicleId:%s,vehicleVin:%s", vehicleArchive.getVehicleId(), vehicleArchive.getVehicleVin()));
        baseDao.update(vehicleArchive);
    }

    public void clearVehicleArchiveRelation(VehicleSaleContractDetail contractDetail) {
        if (StringUtils.isEmpty(contractDetail.getVehicleId())) {
            return;
        }
        List<VehicleArchives> vehicleArchiveList = (List<VehicleArchives>) baseDao.findByHql("from VehicleArchives where vehicleId = ? OR vehicleVin = ? ", contractDetail.getVehicleId(), contractDetail.getVehicleVin());
        if (vehicleArchiveList != null && vehicleArchiveList.size() > 0) {
            VehicleArchives vehicleArchive = vehicleArchiveList.get(0);
            vehicleArchive.setCustomerId(null);
            vehicleArchive.setBelongToSupplierId(null);
            vehicleArchive.setBelongToSupplierNo(null);
            vehicleArchive.setBelongToSupplierName(null);
            vehicleArchive.setVehiclePurchaseFlag(null);

            vehicleArchive.setSellerId(null);
            vehicleArchive.setSeller(null);
        }

    }

}
