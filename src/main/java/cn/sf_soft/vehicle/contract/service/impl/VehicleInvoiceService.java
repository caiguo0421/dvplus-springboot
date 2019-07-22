package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.dao.FinanceDocumentEntriesDao;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.FinanceDocumentEntries;
import cn.sf_soft.office.approval.model.VehicleInvoices;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.vehicle.stockbrowse.model.VehicleStocks;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.UUID;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/3 17:25
 * @Description: 发票明细明细表
 */
@Service
public class VehicleInvoiceService implements Command<VehicleInvoices> {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleInvoiceService.class);

    @Autowired
    private BaseDao baseDao;

    @Autowired
    protected FinanceDocumentEntriesDao financeDocumentEntriesDao;// 单据分录


    private static EntityRelation entityRelation;

    static {
        entityRelation = EntityRelation.newInstance(VehicleInvoiceService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleInvoices> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleInvoiceService.class, "beforeExecute");
        //合同号
        VehicleInvoices detail = entityProxy.getEntity();
        EntityProxy<VehicleSaleContractDetail> masterProxy = entityProxy.getMaster();
        VehicleSaleContractDetail master = masterProxy.getEntity();
        detail.setContractNo(master.getContractNo());
        validateRecord(entityProxy);

        ContractStopWatch.stop(watch);
    }

    private void validateRecord(EntityProxy<VehicleInvoices> entityProxy) {
        VehicleInvoices oriInVoice = entityProxy.getOriginalEntity();
        VehicleInvoices invoice = entityProxy.getEntity();

        List<FinanceDocumentEntries> entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? ", invoice.getInvoicesDetailId());
        if (entityProxy.getOperation() == Operation.UPDATE) {
            if (entriesList != null && entriesList.size() > 0) {
                FinanceDocumentEntries entries = entriesList.get(0);
                if (StringUtils.isNotEmpty(entries.getAfterNo())) {
                    if (!StringUtils.equals(invoice.getInvoiceType(), oriInVoice.getInvoiceType())) {
                        throw new ServiceException(String.format("发票已经开票，不能从【%s】变成【%s】", oriInVoice.getInvoiceType(), invoice.getInvoiceType()));
                    }

                    if (!StringUtils.equals(invoice.getObjectId(), oriInVoice.getObjectId())) {
                        throw new ServiceException(String.format("发票【%s】已经开票，不能更换开票对象", invoice.getInvoiceType()));
                    }

                    if (Tools.toBigDecimal(invoice.getInvoiceAmount()).compareTo(Tools.toBigDecimal(entries.getInvoiceAmount())) < 0) {
                        throw new ServiceException(String.format("发票【%s】的金额不能小于已开金额%s", invoice.getInvoiceType(), Tools.toDouble(entries.getInvoiceAmount())));
                    }
                }
            }
        } else if (entityProxy.getOperation() == Operation.DELETE) {
            if (entriesList != null && entriesList.size() > 0) {
                FinanceDocumentEntries entries = entriesList.get(0);
                if (StringUtils.isNotEmpty(entries.getAfterNo())) {
                    throw new ServiceException(String.format("发票【%s】已经开票，不能删除", oriInVoice.getInvoiceType()));
                }
            }
        }
    }

    @Override
    public void execute(EntityProxy<VehicleInvoices> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleInvoiceService.class, "execute");
        this.dealInvoice(entityProxy);
        ContractStopWatch.stop(watch);
    }


    /**
     * 处理发票分录
     *
     * @param entityProxy
     */
    private void dealInvoice(EntityProxy<VehicleInvoices> entityProxy) {

        VehicleInvoices oriInVoice = entityProxy.getOriginalEntity();
        VehicleInvoices invoice = entityProxy.getEntity();

        EntityProxy<VehicleSaleContractDetail> contractDetailProxy = entityProxy.getMaster();
        VehicleSaleContractDetail detail = contractDetailProxy.getEntity();
        VehicleSaleContractDetail oriDetail = contractDetailProxy.getOriginalEntity();

        if (entityProxy.getOperation() == Operation.DELETE) {
            deleteInvoice(entityProxy);
        } else if (entityProxy.getOperation() == Operation.CREATE) {
            createInvoice(entityProxy, detail);
        } else {
            //如果车辆VIN为空时，删除开票单据分录
            if (detail != null && StringUtils.isEmpty(detail.getVehicleVin())) {
                deleteInvoice(entityProxy);
            } else {
                //如果原发票为空，则新增发票
                List<FinanceDocumentEntries> entriesList = getInvoiceEntries(entityProxy);
                if (entriesList == null || entriesList.size() == 0) {
                    createInvoice(entityProxy, detail);
                } else {
                    FinanceDocumentEntries entries = entriesList.get(0);
                    if (!StringUtils.equals(detail.getVehicleVin(), oriDetail.getVehicleVin())) {
                        //换车
                        if (StringUtils.isNotEmpty(entries.getAfterNo())) {
                            throw new ServiceException(String.format("车辆%s已经开票，不能更换VIN码", detail.getVehicleVin()));
                        }
                    }
                    //注意：更新主键的目的，是和孙总的交互存在这种问题。孙总那建立了开票单，但没审，此时合同这又修改了。正常孙总那应该判断是否存在差异，存在就不允许审了。
                    // 但又由于孙总那是个通用模块，不可能一个个业务去判断。当时孙总就说，直接更新主键，他那找不到原单了，就审不过去了。-by 谢工
                    entries.setDocumentType("车辆-" + invoice.getInvoiceType());
                    entries.setDocumentAmount(Tools.toDouble(invoice.getInvoiceAmount()));
                    entries.setDocumentNo(detail.getVehicleVin() + "," + detail.getContractNo());
                    entries.setObjectId(invoice.getObjectId());
                    entries.setObjectNo(invoice.getObjectNo());
                    entries.setObjectName(invoice.getObjectName());
                    entries.setLeftAmount(Tools.toDouble(invoice.getInvoiceAmount()));
//                    baseDao.save(cloneEntry);

                }
            }
        }
    }


    private void createInvoice(EntityProxy<VehicleInvoices> entityProxy, VehicleSaleContractDetail detail) {
        if (StringUtils.isEmpty(detail.getVehicleVin())) {
            return;
        }
        VehicleInvoices invoice = entityProxy.getEntity();
        if (StringUtils.isEmpty(invoice.getObjectId())) {
            throw new ServiceException("发票明细对象不能为空");
        }
        EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster().getMaster().getMaster();
        VehicleSaleContracts contracts = contractsProxy.getEntity();

        //车辆销售合同录入发票信息产生待开发票信息的逻辑要注意一下：如果只是关联了订单，此时不要生成待开发票信息！
        VehicleStocks stocks = baseDao.get(VehicleStocks.class, detail.getVehicleId());
        //要判断vehicle_stocks表的in_stock_no入库单号字段是否有值，有值说明是入库了。没值说明还是订单车。
        if (stocks != null && StringUtils.isNotBlank(stocks.getInStockNo())) {
            financeDocumentEntriesDao.insertEntry(contracts.getStationId(), (short) 4, (short) 10, "车辆-" + invoice.getInvoiceType(), invoice.getInvoicesDetailId(), invoice.getObjectId(), invoice.getObjectNo(), invoice.getObjectName(), (short) 20, Tools.toDouble(invoice.getInvoiceAmount()), detail.getVehicleVin() + "," + detail.getContractNo(), null);
        }
    }


    private void deleteInvoice(EntityProxy<VehicleInvoices> entityProxy) {
        List<FinanceDocumentEntries> entriesList = getInvoiceEntries(entityProxy);
        if (entriesList == null || entriesList.size() == 0) {
            return;
        }
        FinanceDocumentEntries entries = entriesList.get(0);
        if (StringUtils.isEmpty(entries.getAfterNo())) {
            baseDao.delete(entries);
        } else {
            throw new ServiceException("有发票明细已经开票，不能删除发票明细");
        }
    }


    //获取发票分录
    private List<FinanceDocumentEntries> getInvoiceEntries(EntityProxy<VehicleInvoices> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleInvoiceService.class, "execute", "getInvoiceEntries");

        VehicleInvoices oriInVoice = entityProxy.getOriginalEntity();
        VehicleInvoices invoice = entityProxy.getEntity();
        List<FinanceDocumentEntries> entriesList = (List<FinanceDocumentEntries>) baseDao.findByHql("FROM FinanceDocumentEntries WHERE documentId = ? ", invoice.getInvoicesDetailId());

        ContractStopWatch.stop(watch);
        return entriesList;
    }

    private FinanceDocumentEntries cloneNewEntry(FinanceDocumentEntries entry) {
        FinanceDocumentEntries newEntry = new FinanceDocumentEntries();
        newEntry.setEntryId(UUID.randomUUID().toString());


        newEntry.setSummary(entry.getSummary());
        newEntry.setPaidTime(entry.getPaidTime());
        newEntry.setWriteOffTime(entry.getWriteOffTime());
        newEntry.setLeftAmount(entry.getLeftAmount());
        newEntry.setDepartmentId(entry.getDepartmentId());
        newEntry.setInvoiceTime(entry.getInvoiceTime());
        newEntry.setDocumentType(entry.getDocumentType());
        newEntry.setAfterNo(entry.getAfterNo());
        newEntry.setOffsetAmount(entry.getOffsetAmount());
        newEntry.setEntryProperty(entry.getEntryProperty());
        newEntry.setWriteOffAmount(entry.getWriteOffAmount());
        newEntry.setDocumentTime(entry.getDocumentTime());
        newEntry.setSubDocumentNo(entry.getSubDocumentNo());
        newEntry.setUserId(entry.getUserId());
        newEntry.setStationId(entry.getStationId());
        newEntry.setPaidAmount(entry.getPaidAmount());
        newEntry.setDepartmentName(entry.getDepartmentName());
        newEntry.setOffsetTime(entry.getOffsetTime());
        newEntry.setDocumentNo(entry.getDocumentNo());
        newEntry.setAccountId(entry.getAccountId());
        newEntry.setDocumentId(entry.getDocumentId());
        newEntry.setUsedCredit(entry.getUsedCredit());
        newEntry.setArapTime(entry.getArapTime());
        newEntry.setObjectId(entry.getObjectId());
        newEntry.setEntryType(entry.getEntryType());
        newEntry.setUserNo(entry.getUserNo());
        newEntry.setInvoiceAmount(entry.getInvoiceAmount());
        newEntry.setDepartmentNo(entry.getDepartmentNo());
        newEntry.setDocumentAmount(entry.getDocumentAmount());
        newEntry.setUserName(entry.getUserName());
        newEntry.setObjectNo(entry.getObjectNo());
        newEntry.setAmountType(entry.getAmountType());
        newEntry.setObjectName(entry.getObjectName());
        return newEntry;

    }

}
