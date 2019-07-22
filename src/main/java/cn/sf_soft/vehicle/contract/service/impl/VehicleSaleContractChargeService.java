package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.dao.FinanceDocumentEntriesDao;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleSaleContractCharge;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.vehicle.contract.model.ChargeCatalog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/3 17:23
 * @Description: 销售合同费用明细
 */
@Service
public class VehicleSaleContractChargeService implements Command<VehicleSaleContractCharge> {

    private static Logger logger = LoggerFactory.getLogger(VehicleSaleContractChargeService.class);

    private static String CHARGE_EXISTS_SQL = "SELECT count(1) \n" + "FROM dbo.vehicle_sale_contract_charge WHERE sale_contract_charge_id=? AND isnull(cost_status,0)=1";

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private FinanceDocumentEntriesDao financeDocumentEntriesDao;

    @Autowired
    private SaleContractService saleContractService1;


    private static EntityRelation entityRelation;

    static {
        entityRelation = EntityRelation.newInstance(VehicleSaleContractChargeService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleSaleContractCharge> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractChargeService.class, "beforeExecute");

        //合同号
        VehicleSaleContractCharge detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContractDetail> masterProxy = entityProxy.getMaster();
        VehicleSaleContractDetail master = masterProxy.getEntity();
        detail.setContractNo(master.getContractNo());

        Operation operation = entityProxy.getOperation();
        if (operation == Operation.CREATE || operation == Operation.UPDATE) {
            VehicleSaleContractCharge entity = entityProxy.getEntity();
            if (null == entity.getIncome()) {
                entity.setIncome(BigDecimal.ZERO);
            }
            if (null == entity.getChargePf()) {
                entity.setChargePf(BigDecimal.ZERO);
            }
        }
        ContractStopWatch.stop(watch);
    }

    @Override
    public void execute(EntityProxy<VehicleSaleContractCharge> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractChargeService.class, "execute");

        this.validate(entityProxy);
        //如果VEHICLE_CHARGE_FDE_SEPARATE为是，车辆销售合同费用 单独产生单据分录 -20190108
        if ("是".equals(saleContractService1.getSysOptionValue(SaleContractService.VEHICLE_CHARGE_FDE_SEPARATE))) {
            this.dealChargeDocument(entityProxy);
        }

        ContractStopWatch.stop(watch);
    }


    private void validate(EntityProxy<VehicleSaleContractCharge> entityProxy) {
        logger.debug("校验销售合同费用{}", entityProxy);
        Operation operation = entityProxy.getOperation();
        if (operation == Operation.CREATE || operation == Operation.UPDATE) {
            VehicleSaleContractCharge entity = entityProxy.getEntity();
            if (entity.getIncome().compareTo(BigDecimal.ZERO) == 0 && entity.getChargePf().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ServiceException(String.format("费用为【%s】的收入与成本必须有一项大于0，请修改后再尝试保存！", entity.getChargeName()));
            }

            if (operation == Operation.UPDATE) {
                //分录可能是预收款，因此不能修改费用名称-20180918
                if (!StringUtils.equals(entity.getChargeId(), entityProxy.getOriginalEntity().getChargeId())) {
                    throw new ServiceException(String.format("不允许修改费用名称：【%s】改成【%s】", entityProxy.getOriginalEntity().getChargeName(), entity.getChargeName()));
                }

                //优化程序逻辑，减少查询语句消耗时间
                if (entity.getIncome().compareTo(entityProxy.getOriginalEntity().getIncome()) != 0 || entity.getChargePf().compareTo(entityProxy.getOriginalEntity().getChargePf()) != 0) {
                    Integer result = baseDao.findUniqueBySql(CHARGE_EXISTS_SQL, entity.getSaleContractChargeId());
                    if (null != result && result > 0) {
                        throw new ServiceException(String.format("费用为【%s】的收入与成本当已报销后不能更改！", entity.getChargeName()));
                    }
                }
            } else if (operation == Operation.DELETE) {
                VehicleSaleContractCharge oriCharge = entityProxy.getOriginalEntity();
                Integer result = baseDao.findUniqueBySql(CHARGE_EXISTS_SQL, oriCharge.getSaleContractChargeId());
                if (null != result && result > 0) {
                    throw new ServiceException(String.format("费用【%s】已报销后不能删除", oriCharge.getChargeName()));
                }
            }
        }
    }


    /**
     * 处理费用分录--ADM18070048
     *
     * @param entityProxy
     */
    private void dealChargeDocument(EntityProxy<VehicleSaleContractCharge> entityProxy) {
        EntityProxy<VehicleSaleContractDetail> detailProxy = entityProxy.getMaster();
        EntityProxy<VehicleSaleContracts> contractsEntityProxy = entityProxy.getMaster().getMaster().getMaster();
        VehicleSaleContractDetail oriContractDetail = detailProxy.getOriginalEntity();
        VehicleSaleContractDetail contractDetail = detailProxy.getEntity();
        VehicleSaleContractCharge contractCharge = entityProxy.getEntity();
        VehicleSaleContractCharge oriContractCharge = entityProxy.getOriginalEntity();
        VehicleSaleContracts contracts = contractsEntityProxy.getEntity();


        String sDocumentNo = contractDetail.getContractNo();
        String sVehicleVin = "";
        if (StringUtils.isNotEmpty(contractDetail.getVehicleVin())) {
            sVehicleVin = contractDetail.getVehicleVin();
            sDocumentNo = String.format("%s,%s", contractDetail.getContractNo(), contractDetail.getVehicleVin());
        }

        short bytAmountType = 20;//应收款
        if (entityProxy.getOperation() != Operation.DELETE) {
            ChargeCatalog chargeCatalog = baseDao.get(ChargeCatalog.class, contractCharge.getChargeId());
            if (Tools.toShort(chargeCatalog.getMoneyType(), (short) 10) == 60) {//60为“预收款”类型
                bytAmountType = 10;//预收款类型
            }
        }

        if (entityProxy.getOperation() == Operation.CREATE) {
            if (Tools.toBigDecimal(contractCharge.getIncome()).compareTo(BigDecimal.ZERO) <= 0) {
                return;
            }

            financeDocumentEntriesDao.insertEntryEx(contracts.getStationId(), (short) 19, (short) 10, "车辆-" + contractCharge.getChargeName(), contractCharge.getSaleContractChargeId(), contracts.getCustomerId(), contracts.getCustomerNo(), contracts.getCustomerName(), bytAmountType, Tools.toDouble(contractCharge.getIncome()), sDocumentNo, sVehicleVin, new Timestamp(System.currentTimeMillis()));
        } else if (entityProxy.getOperation() == Operation.DELETE) {
            List<FinanceDocumentEntries> entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries where documentId = ? AND documentType LIKE '车辆-%'", oriContractCharge.getSaleContractChargeId());
            deleteFinanceDocumentEntries(entriesList);
        } else {
            //优化程序逻辑，如果vin没变而且费用金额也没变，则跳过
            if (oriContractDetail != null && StringUtils.equals(oriContractDetail.getVehicleVin(), contractDetail.getVehicleVin()) && contractCharge.getIncome().compareTo(oriContractCharge.getIncome()) == 0) {
                return;
            }

            List<FinanceDocumentEntries> entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries where documentId = ? AND documentType LIKE '车辆-%'", contractCharge.getSaleContractChargeId());
            if (Tools.toBigDecimal(contractCharge.getIncome()).compareTo(BigDecimal.ZERO) <= 0) {
                deleteFinanceDocumentEntries(entriesList);//如果收入修改为0，检查有没单据分录，有则删除
            } else {
                //收入大于0
                if (entriesList != null && entriesList.size() > 0) {
                    FinanceDocumentEntries documentEntries = entriesList.get(0);
                    double mPaid = Tools.toDouble(documentEntries.getDocumentAmount()) - Tools.toDouble(documentEntries.getLeftAmount());
                    if (Tools.toDouble(contractCharge.getIncome()) < mPaid) {
                        throw new ServiceException(String.format("费用【%s】的收入金额(%s) 不能小于已收款金额(%s)", documentEntries.getDocumentType(), Tools.toDouble(contractCharge.getIncome()), mPaid));
                    }

                    documentEntries.setDocumentAmount(Tools.toDouble(contractCharge.getIncome()));
                    documentEntries.setLeftAmount(Tools.toDouble(contractCharge.getIncome()) - mPaid);
                    documentEntries.setDocumentNo(sDocumentNo);
                    documentEntries.setSubDocumentNo(sVehicleVin);
                } else {
                    financeDocumentEntriesDao.insertEntryEx(contracts.getStationId(), (short) 19, (short) 10, "车辆-" + contractCharge.getChargeName(), contractCharge.getSaleContractChargeId(), contracts.getCustomerId(), contracts.getCustomerNo(), contracts.getCustomerName(), bytAmountType, Tools.toDouble(contractCharge.getIncome()), sDocumentNo, sVehicleVin, new Timestamp(System.currentTimeMillis()));
                }
            }
        }
    }

    private void deleteFinanceDocumentEntries(List<FinanceDocumentEntries> entriesList) {
        if (entriesList == null || entriesList.size() == 0) {
            return;
        }
        FinanceDocumentEntries documentEntries = entriesList.get(0);
        if (StringUtils.isEmpty(documentEntries.getAfterNo())) {
            baseDao.delete(documentEntries);
        } else {
            throw new ServiceException(String.format("单据分录【%s】，财务已处理，不能删除", documentEntries.getDocumentType()));
        }
    }
}
