package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.*;
import cn.sf_soft.parts.stockborwse.model.VwPartStocks;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityProxyUtil;
import cn.sf_soft.support.Operation;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.contract.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.BindException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/13 10:43
 * @Description:
 */
@Component
public class VehicleSaleContractDetailHandler {

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleSaleContractDetailHandler.class);
    @Autowired
    private BaseDao baseDao;

    @Autowired
    private SaleContractService saleContractService1;

    public void handle(EntityProxy<VehicleSaleContractDetail> entityProxy) {
        EntityProxy<?> masterEntityProxy = entityProxy.getMaster();
        if (null != masterEntityProxy && masterEntityProxy.getEntityClass().getSimpleName().equals(VehicleSaleContractDetailGroups.class.getSimpleName())) {
            String[] slaveNames = masterEntityProxy.getSlaveNames();
            for (String slaveName : slaveNames) {
                List<EntityProxy<Object>> slaves = masterEntityProxy.getSlaves(slaveName);
                //处理改装
                if (slaveName.equals(VehicleSaleContractItemGroups.class.getSimpleName())) {
                    for (EntityProxy<?> slave : slaves) {
                        item(entityProxy, (EntityProxy<VehicleSaleContractItemGroups>) slave);
                    }
                }

                //处理保险
                if (slaveName.equals(VehicleSaleContractInsuranceGroups.class.getSimpleName())) {
                    for (EntityProxy<?> slave : slaves) {
                        insurance(entityProxy, (EntityProxy<VehicleSaleContractInsuranceGroups>) slave);
                    }
                }

                //处理费用
                if (slaveName.equals(VehicleSaleContractChargeGroups.class.getSimpleName())) {
                    for (EntityProxy<?> slave : slaves) {
                        charge(entityProxy, (EntityProxy<VehicleSaleContractChargeGroups>) slave);
                    }
                }

                //精品
                if (slaveName.equals(VehicleSaleContractPresentGroups.class.getSimpleName())) {
                    for (EntityProxy<?> slave : slaves) {
                        present(entityProxy, (EntityProxy<VehicleSaleContractPresentGroups>) slave);
                    }
                }

                //发票
                if (slaveName.equals(VehicleInvoicesGroups.class.getSimpleName())) {
                    for (EntityProxy<?> slave : slaves) {
                        invoice(entityProxy, (EntityProxy<VehicleInvoicesGroups>) slave);
                    }
                }
            }
        }

        VehicleSaleContractDetail detail = entityProxy.getEntity();
        logger.debug(String.format("%s的handle完成，id：%s",entityProxy.getEntityClass().getSimpleName(),detail.getContractDetailId()));
    }




    private void item(EntityProxy<VehicleSaleContractDetail> entityProxy, EntityProxy<VehicleSaleContractItemGroups> groupEntityProxy) {
        Operation operation = entityProxy.getOperation();
        Operation opGroup = groupEntityProxy.getOperation();
        VehicleSaleContractDetail detail = entityProxy.getEntity();
        VehicleSaleContractItemGroups group = groupEntityProxy.getEntity();
        List<VehicleSaleContractItem> itemList = (List<VehicleSaleContractItem>) baseDao.findByHql("FROM VehicleSaleContractItem WHERE contractDetailId = ? AND itemGroupId = ?", detail.getContractDetailId(), group.getItemGroupId());

        VehicleSaleContractItem item = null;
        if (operation == Operation.DELETE || opGroup == Operation.DELETE) {
            //删除
            if(itemList == null || itemList.size()==0){
                return;
            }
            item = itemList.get(0);
//            //检查已经建立了改装申请单
//            List<VehicleConversionDetail> conversionDetailList = (List<VehicleConversionDetail>)baseDao.findByHql("FROM VehicleConversionDetail WHERE saleContractItemId = ? AND status IN (0,1,2,30,40,50)",item.getSaleContractItemId());
//            if(conversionDetailList!=null && conversionDetailList.size()>0){
//                throw new ServiceException("车辆改装："+item.getItemName()+"已经建立了改装申请单，不能删除");
//            }
            EntityProxy proxy = EntityProxyUtil.newEntityProxy(Operation.DELETE, item, VehicleSaleContractItemService.class);
            EntityProxyUtil.addSlave(entityProxy, proxy);
        } else if (operation == Operation.CREATE || opGroup == Operation.CREATE) {
            item = new VehicleSaleContractItem();
            item.setSaleContractItemId(UUID.randomUUID().toString());
            item.setContractDetailId(detail.getContractDetailId());
            item.setItemGroupId(group.getItemGroupId());


            item.setSupplierId(group.getSupplierId());
            item.setSupplierNo(group.getSupplierNo());
            item.setSupplierName(group.getSupplierName());
            item.setItemId(group.getItemId());
            item.setItemNo(group.getItemNo());
            item.setItemName(group.getItemName());
            item.setItemCost(group.getItemCost());
            item.setIncome(group.getIncome());
            item.setComment(group.getComment());

            EntityProxy proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, item, VehicleSaleContractItemService.class);
            EntityProxyUtil.addSlave(entityProxy, proxy);
        } else {
            //查询
            EntityProxy proxy = null;
            if (itemList == null || itemList.size()==0) {
                //没查到就算新增
                item = new VehicleSaleContractItem();
                item.setSaleContractItemId(UUID.randomUUID().toString());
                item.setContractDetailId(detail.getContractDetailId());
                item.setItemGroupId(group.getItemGroupId());

                proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, item, VehicleSaleContractItemService.class);
            } else {
                //查到就算修改
                item = itemList.get(0);
                proxy = EntityProxyUtil.newEntityProxy(Operation.UPDATE, item, VehicleSaleContractItemService.class);
            }

            item.setSupplierId(group.getSupplierId());
            item.setSupplierNo(group.getSupplierNo());
            item.setSupplierName(group.getSupplierName());
            item.setItemId(group.getItemId());
            item.setItemNo(group.getItemNo());
            item.setItemName(group.getItemName());
            item.setItemCost(group.getItemCost());
            item.setIncome(group.getIncome());
            item.setComment(group.getComment());

            EntityProxyUtil.addSlave(entityProxy, proxy);
        }
    }


    private void insurance(EntityProxy<VehicleSaleContractDetail> entityProxy, EntityProxy<VehicleSaleContractInsuranceGroups> groupEntityProxy) {
        Operation operation = entityProxy.getOperation();
        Operation opGroup = groupEntityProxy.getOperation();
        VehicleSaleContractDetail detail = entityProxy.getEntity();
        VehicleSaleContractInsuranceGroups group = groupEntityProxy.getEntity();
        EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster().getMaster();
        VehicleSaleContracts contract = contractsProxy.getEntity();

        List<VehicleSaleContractInsurance> insuranceList = (List<VehicleSaleContractInsurance>) baseDao.findByHql("FROM VehicleSaleContractInsurance WHERE contractDetailId = ? AND insuranceGroupId = ?", detail.getContractDetailId(), group.getInsuranceGroupId());

        VehicleSaleContractInsurance insurance = null;
        if (operation == Operation.DELETE || opGroup == Operation.DELETE) {
            //删除
            if(insuranceList == null || insuranceList.size()==0){
                return;
            }
            insurance = insuranceList.get(0);
            //检查已经建立了改装申请单
            String sql = "SELECT a.sale_contract_insurance_id FROM dbo.insurance_detail a\n" + "LEFT JOIN dbo.insurance b ON a.insurance_no=b.insurance_no\n" + "WHERE b.approve_status IN (0,1) AND a.sale_contract_insurance_id =:saleContractInsuranceId";
            Map<String,Object> params = new HashMap<>(1);
            params.put("saleContractInsuranceId",insurance.getSaleContractInsuranceId());
            List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
            if(list!=null && list.size()>0){
                throw new ServiceException("险种："+insurance.getCategoryName()+"已经建立保单了，不能删除");
            }
            EntityProxy proxy = EntityProxyUtil.newEntityProxy(Operation.DELETE, insurance, VehicleSaleContractInsuranceService.class);
            EntityProxyUtil.addSlave(entityProxy, proxy);
        } else if (operation == Operation.CREATE || opGroup == Operation.CREATE) {

            if("俱乐部".equals(saleContractService1.getSysOptionValue(SaleContractService.INSURANCE_INPUT_UNIT,contract.getStationId()))){
                throw new ServiceException("销售合同-车辆保险录入部门为俱乐部，不能在合同中录入保险信息");
            }

            //tax_rate 和 rebate_ration
            List<InsuranceCompanyCatalog>  catalogList = (List<InsuranceCompanyCatalog>)baseDao.findByHql("FROM InsuranceCompanyCatalog WHERE categoryId = ? AND supplierId= ?",group.getCategoryId(), group.getSupplierId());
            if(catalogList!=null && catalogList.size()>0){
                group.setTaxRate(catalogList.get(0).getTaxRate());
                group.setRebateRatio(catalogList.get(0).getRebateRatio());
            }

            insurance = new VehicleSaleContractInsurance();
            insurance.setSaleContractInsuranceId(UUID.randomUUID().toString());
            insurance.setContractDetailId(detail.getContractDetailId());
            insurance.setInsuranceGroupId(group.getInsuranceGroupId());
            insurance.setCategoryCost(BigDecimal.ZERO);
            insurance.setCostStatus((short)0);

            insurance.setInsuranceYear(group.getInsuranceYear());
            insurance.setCategoryId(group.getCategoryId());
            insurance.setCategoryType(group.getCategoryType());
            insurance.setCategoryName(group.getCategoryName());
            insurance.setCategoryIncome(group.getCategoryIncome());
            insurance.setCategoryScale(group.getCategoryScale());
            insurance.setRemark(group.getRemark());
            insurance.setSupplierId(group.getSupplierId());
            insurance.setIsFree(Tools.toBoolean(detail.getIsContainInsuranceCost()));
            insurance.setTaxRate(group.getTaxRate());
            insurance.setRebateRatio(group.getRebateRatio());


            EntityProxy proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, insurance, VehicleSaleContractInsuranceService.class);
            EntityProxyUtil.addSlave(entityProxy, proxy);
        } else {
            //查询
            EntityProxy proxy = null;
            if (insuranceList == null || insuranceList.size()==0) {
                //没查到就算新增
                insurance = new VehicleSaleContractInsurance();

                insurance.setSaleContractInsuranceId(UUID.randomUUID().toString());
                insurance.setContractDetailId(detail.getContractDetailId());
                insurance.setInsuranceGroupId(group.getInsuranceGroupId());
                insurance.setCategoryCost(BigDecimal.ZERO);
                insurance.setCostStatus((short)0);

                proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, insurance, VehicleSaleContractInsuranceService.class);
            } else {
                //查到就算修改
                insurance = insuranceList.get(0);
                proxy = EntityProxyUtil.newEntityProxy(Operation.UPDATE, insurance, VehicleSaleContractInsuranceService.class);
            }

            insurance.setInsuranceYear(group.getInsuranceYear());
            insurance.setCategoryId(group.getCategoryId());
            insurance.setCategoryType(group.getCategoryType());
            insurance.setCategoryName(group.getCategoryName());
            insurance.setCategoryIncome(group.getCategoryIncome());
            insurance.setCategoryScale(group.getCategoryScale());
            insurance.setRemark(group.getRemark());
            insurance.setSupplierId(group.getSupplierId());
            insurance.setIsFree(Tools.toBoolean(detail.getIsContainInsuranceCost()));
            insurance.setTaxRate(group.getTaxRate());
            insurance.setRebateRatio(group.getRebateRatio());

            EntityProxyUtil.addSlave(entityProxy, proxy);
        }
    }


    private void charge(EntityProxy<VehicleSaleContractDetail> entityProxy, EntityProxy<VehicleSaleContractChargeGroups> groupEntityProxy) {
        Operation operation = entityProxy.getOperation();
        Operation opGroup = groupEntityProxy.getOperation();
        VehicleSaleContractDetail detail = entityProxy.getEntity();
        VehicleSaleContractChargeGroups group = groupEntityProxy.getEntity();
        List<VehicleSaleContractCharge> chargeList = (List<VehicleSaleContractCharge>) baseDao.findByHql("FROM VehicleSaleContractCharge WHERE contractDetailId = ? AND chargeGroupId = ?", detail.getContractDetailId(), group.getChargeGroupId());

        VehicleSaleContractCharge charge = null;
        if (operation == Operation.DELETE || opGroup == Operation.DELETE) {
            //删除
            if (chargeList == null || chargeList.size() == 0) {
                return;
            }
             charge = chargeList.get(0);
            if (Tools.toShort(charge.getCostStatus()) == 1) {
                throw new ServiceException("费用：" + charge.getChargeName() + "已报销，不能删除");
            }

            EntityProxy proxy = EntityProxyUtil.newEntityProxy(Operation.DELETE, charge, VehicleSaleContractChargeService.class);
            EntityProxyUtil.addSlave(entityProxy, proxy);
        } else if (operation == Operation.CREATE || opGroup == Operation.CREATE) {
            //新建
            charge = new VehicleSaleContractCharge();
            charge.setSaleContractChargeId(UUID.randomUUID().toString());
            charge.setContractDetailId(detail.getContractDetailId());
            charge.setChargeGroupId(group.getChargeGroupId());

            charge.setChargeId(group.getChargeId());
            charge.setChargeName(group.getChargeName());
            charge.setChargePf(group.getChargePf());
            charge.setChargeCost(BigDecimal.ZERO);
            charge.setIncome(group.getIncome());
            charge.setCostStatus((short) 0);
            charge.setPaidByBill(false);
            charge.setRemark(group.getRemark());

            EntityProxy proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, charge, VehicleSaleContractChargeService.class);
            EntityProxyUtil.addSlave(entityProxy, proxy);
        } else {
            //查询
            EntityProxy proxy = null;
            if (chargeList == null || chargeList.size() == 0) {
                //没查到就算新增
                charge = new VehicleSaleContractCharge();
                charge.setSaleContractChargeId(UUID.randomUUID().toString());
                charge.setContractDetailId(detail.getContractDetailId());
                charge.setChargeGroupId(group.getChargeGroupId());
                charge.setChargeId(group.getChargeId());

                charge.setChargeCost(BigDecimal.ZERO);
                charge.setCostStatus((short) 0);

                proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, charge, VehicleSaleContractChargeService.class);
            } else {
                //查到就算修改
                charge = chargeList.get(0);
                proxy = EntityProxyUtil.newEntityProxy(Operation.UPDATE, charge, VehicleSaleContractChargeService.class);
            }
            charge.setChargeId(group.getChargeId());
            charge.setChargeName(group.getChargeName());
            charge.setChargePf(group.getChargePf());
            charge.setIncome(group.getIncome());
            charge.setPaidByBill(false);
            charge.setRemark(group.getRemark());

            EntityProxyUtil.addSlave(entityProxy, proxy);
        }
    }


    private void present(EntityProxy<VehicleSaleContractDetail> entityProxy, EntityProxy<VehicleSaleContractPresentGroups> groupEntityProxy) {
        Operation operation = entityProxy.getOperation();
        Operation opGroup = groupEntityProxy.getOperation();
        VehicleSaleContractDetail detail = entityProxy.getEntity();
        VehicleSaleContractPresentGroups group = groupEntityProxy.getEntity();

        if(opGroup !=Operation.DELETE){
            //计算 cost等
            VwPartStocks partStock = baseDao.get(VwPartStocks.class,group.getStockId());

            group.setPartName(partStock.getPartName());
            group.setPartNo(partStock.getPartNo());
            group.setPartMnemonic(partStock.getPartMnemonic());
            group.setProducingArea(partStock.getProducingArea());
            group.setPartType(partStock.getPartType());
            group.setSpecModel(partStock.getSpecModel());
            group.setApplicableModel(partStock.getApplicableModel());
            group.setUnit(partStock.getUnit());
            group.setWarehouseName(partStock.getWarehouseName());

            double dPrice =  Tools.toDouble(partStock.getCost()); //库存成本
            String m_sPresentPriceStructure = saleContractService1.getSysOptionValue(SaleContractService.VEHICLE_PRESENT_PRICE_STRUCTURE);
            if("参考成本".equals(m_sPresentPriceStructure)){
                dPrice = Tools.toDouble(partStock.getCostRef());
            } else if ("零售价".equals(m_sPresentPriceStructure)){
                dPrice = Tools.toDouble(partStock.getPriceRetailUse());
            }else if("内部价".equals(m_sPresentPriceStructure)){
                dPrice = Tools.toDouble(partStock.getPriceInnerUse());
            }else if( "批发价".equals(m_sPresentPriceStructure)){
                dPrice = Tools.toDouble(partStock.getPriceTradeUse());
            }

            //精品加价比例
            double m_dPriceMarkupRate = saleContractService1.getSysOptionDecimal(SaleContractService.VEHICLE_PRESENT_PRICE_MARKUP_RATE).doubleValue();
            double posPrice = dPrice*(1+m_dPriceMarkupRate)+(Tools.toDouble(partStock.getQuantity(),1)==0.00D?0.00D:(Tools.toDouble(partStock.getCarriage())/Tools.toDouble(partStock.getQuantity(),1)) ); //成本=原成本+运费
            group.setPosPrice(Tools.toBigDecimal(posPrice));
        }

        List<VehicleSaleContractPresent> presentList = (List<VehicleSaleContractPresent>)baseDao.findByHql("FROM VehicleSaleContractPresent WHERE contractDetailId = ? AND presentGroupId = ? ",detail.getContractDetailId(), group.getPresentGroupId());
        VehicleSaleContractPresent present = null;
        if (operation == Operation.DELETE || opGroup == Operation.DELETE) {
            //删除
            if (presentList == null || presentList.size() == 0) {
                return;
            }
            present = presentList.get(0);
            if(Tools.toDouble(present.getGetQuantity())>0){
                throw new ServiceException("精品："+present.getPartName()+"已经出库了，不能删除");
            }

            EntityProxy proxy = EntityProxyUtil.newEntityProxy(Operation.DELETE, present, VehicleSaleContractPresentService.class);
            EntityProxyUtil.addSlave(entityProxy, proxy);
        }else if (operation == Operation.CREATE || opGroup == Operation.CREATE) {
            //新建
            present = new VehicleSaleContractPresent();
            present.setSaleContractPresentId(UUID.randomUUID().toString());
            present.setContractDetailId(detail.getContractDetailId());
            present.setPresentGroupId(group.getPresentGroupId());
            present.setGetQuantity(BigDecimal.ZERO);


            present.setPlanQuantity(group.getPlanQuantity());
            present.setPosPrice(group.getPosPrice());
            present.setPosAgio(group.getPosAgio());
            present.setStockId(group.getStockId());
            present.setDepositPosition(group.getDepositPosition());
            present.setPartName(group.getPartName());
            present.setPartNo(group.getPartNo());
            present.setPartMnemonic(group.getPartMnemonic());
            present.setProducingArea(group.getProducingArea());
            present.setPartType(group.getPartType());
            present.setSpecModel(group.getSpecModel());
            present.setApplicableModel(group.getApplicableModel());
            present.setUnit(group.getUnit());
            present.setWarehouseName(group.getWarehouseName());
            present.setCostRecord(group.getCostRecord());
            present.setCarriageRecord(group.getCarriageRecord());
            present.setIncome(group.getIncome());
            present.setRemark(group.getRemark());

            EntityProxy proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, present, VehicleSaleContractPresentService.class);
            EntityProxyUtil.addSlave(entityProxy, proxy);
        }else{

            //查询
            EntityProxy proxy = null;
            if (presentList == null || presentList.size() == 0) {
                //没查到就算新增
                present = new VehicleSaleContractPresent();
                present.setSaleContractPresentId(UUID.randomUUID().toString());
                present.setContractDetailId(detail.getContractDetailId());
                present.setPresentGroupId(group.getPresentGroupId());
                present.setGetQuantity(BigDecimal.ZERO);

                proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, present, VehicleSaleContractPresentService.class);
            } else {
                //查到就算修改
                present = presentList.get(0);
                proxy = EntityProxyUtil.newEntityProxy(Operation.UPDATE, present, VehicleSaleContractPresentService.class);
            }
            present.setPlanQuantity(group.getPlanQuantity());
            present.setPosPrice(group.getPosPrice());
            present.setPosAgio(group.getPosAgio());
            present.setStockId(group.getStockId());
            present.setDepositPosition(group.getDepositPosition());
            present.setPartName(group.getPartName());
            present.setPartNo(group.getPartNo());
            present.setPartMnemonic(group.getPartMnemonic());
            present.setProducingArea(group.getProducingArea());
            present.setPartType(group.getPartType());
            present.setSpecModel(group.getSpecModel());
            present.setApplicableModel(group.getApplicableModel());
            present.setUnit(group.getUnit());
            present.setWarehouseName(group.getWarehouseName());
            present.setCostRecord(group.getCostRecord());
            present.setCarriageRecord(group.getCarriageRecord());
            present.setIncome(group.getIncome());
            present.setRemark(group.getRemark());

            EntityProxyUtil.addSlave(entityProxy, proxy);
        }

    }


    private void invoice(EntityProxy<VehicleSaleContractDetail> entityProxy, EntityProxy<VehicleInvoicesGroups> groupEntityProxy) {
        Operation operation = entityProxy.getOperation();
        Operation opGroup = groupEntityProxy.getOperation();
        VehicleSaleContractDetail detail = entityProxy.getEntity();
        VehicleInvoicesGroups group = groupEntityProxy.getEntity();
        SysUsers user = HttpSessionStore.getSessionUser();

        List<VehicleInvoices> invoiceList = (List<VehicleInvoices>) baseDao.findByHql("FROM VehicleInvoices WHERE contractDetailId = ? AND invoiceGroupId = ?", detail.getContractDetailId(), group.getInvoiceGroupId());
        VehicleInvoices invoice = null;
        if (operation == Operation.DELETE || opGroup == Operation.DELETE) {
            //删除
            if (invoiceList == null || invoiceList.size() == 0) {
                return;
            }
            invoice = invoiceList.get(0);
            EntityProxy proxy = EntityProxyUtil.newEntityProxy(Operation.DELETE, invoice, VehicleInvoiceService.class);
            EntityProxyUtil.addSlave(entityProxy, proxy);
        }else if (operation == Operation.CREATE || opGroup == Operation.CREATE) {
            //新建
            invoice = new VehicleInvoices();
            if(StringUtils.isEmpty(group.getObjectId())) {
                group.setObjectId(detail.getBelongToSupplierId());
                group.setObjectNo(detail.getBelongToSupplierNo());
                group.setObjectName(detail.getBelongToSupplierName());
            }

            invoice.setInvoicesDetailId(UUID.randomUUID().toString());
            invoice.setContractDetailId(detail.getContractDetailId());
            invoice.setInvoiceGroupId(group.getInvoiceGroupId());
            invoice.setCreator(user.getUserFullName());
            invoice.setCreateTime(new Timestamp(System.currentTimeMillis()));

            invoice.setInvoiceType(group.getInvoiceType());
            invoice.setInvoiceAmount(group.getInvoiceAmount());
            invoice.setObjectId(group.getObjectId());
            invoice.setObjectNo(group.getObjectNo());
            invoice.setObjectName(group.getObjectName());
            invoice.setRemark(group.getRemark());

            EntityProxy proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, invoice, VehicleInvoiceService.class);
            EntityProxyUtil.addSlave(entityProxy, proxy);
        }else{
            //查询
            EntityProxy proxy = null;
            if (invoiceList == null || invoiceList.size() == 0) {
                //没查到就算新增
                invoice = new VehicleInvoices();
                invoice.setInvoicesDetailId(UUID.randomUUID().toString());
                invoice.setContractDetailId(detail.getContractDetailId());
                invoice.setInvoiceGroupId(group.getInvoiceGroupId());
                invoice.setCreator(user.getUserFullName());
                invoice.setCreateTime(new Timestamp(System.currentTimeMillis()));

                proxy = EntityProxyUtil.newEntityProxy(Operation.CREATE, invoice, VehicleInvoiceService.class);
            } else {
                //查到就算修改
                invoice = invoiceList.get(0);
                proxy = EntityProxyUtil.newEntityProxy(Operation.UPDATE, invoice, VehicleInvoiceService.class);
            }
            invoice.setInvoiceType(group.getInvoiceType());
            invoice.setInvoiceAmount(group.getInvoiceAmount());
            invoice.setObjectId(group.getObjectId());
            invoice.setObjectNo(group.getObjectNo());
            invoice.setObjectName(group.getObjectName());
            invoice.setRemark(group.getRemark());

            EntityProxyUtil.addSlave(entityProxy, proxy);
        }
    }


}
