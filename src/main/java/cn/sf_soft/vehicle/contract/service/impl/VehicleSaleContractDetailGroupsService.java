package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.office.approval.model.VehicleConversionDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.vehicle.contract.model.VehicleSaleContractDetailGroups;
import cn.sf_soft.vehicle.contract.model.*;
import cn.sf_soft.vehicle.customer.model.BaseRelatedObjects;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2018/6/30 11:05
 * @Description: 销售合同明细Group服务
 */
@Service
public class VehicleSaleContractDetailGroupsService implements Command<VehicleSaleContractDetailGroups> {

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VehicleSaleContractDetailGroupsService.class);

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private SaleContractService saleContractService1;

    private static final String MSG = "车辆分组（分组号：%s，车辆型号：%s）下没有分配车辆";

    private static EntityRelation entityRelation;

    static {
        entityRelation = new EntityRelation(VehicleSaleContractDetailGroups.class, VehicleSaleContractDetailGroupsService.class);
        entityRelation.addSlave("groupId", VehicleSaleContractDetailService.class);
        entityRelation.addSlave("groupId", VehicleSaleContractInsuranceGroupService.class);
        entityRelation.addSlave("groupId", VehicleSaleContractPresentGroupService.class);
        entityRelation.addSlave("groupId", VehicleSaleContractItemGroupService.class);
        entityRelation.addSlave("groupId", VehicleSaleContractChargeGroupService.class);
        entityRelation.addSlave("groupId", VehicleInvoicesGroupService.class);

    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<VehicleSaleContractDetailGroups> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractDetailGroupsService.class, "beforeExecute");
        //合同号
        VehicleSaleContractDetailGroups detailGroups = entityProxy.getEntity();
        EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster();
        VehicleSaleContracts contracts = contractsProxy.getEntity();
        detailGroups.setContractNo(contracts.getContractNo());
        if (StringUtils.isBlank(detailGroups.getVisitorNo())) {
            detailGroups.setVisitorNo(contracts.getVisitorNo());  //同步VisitorNo
        }
        StopWatch watch0 = ContractStopWatch.startWatch(VehicleSaleContractDetailGroupsService.class, "beforeExecute", "validateRecord");
        validateRecord(entityProxy);
        ContractStopWatch.stop(watch0);

        StopWatch watch1 = ContractStopWatch.startWatch(VehicleSaleContractDetailGroupsService.class, "beforeExecute", "initExtProperty");
        initExtProperty(entityProxy);
        ContractStopWatch.stop(watch1);

        StopWatch watch2 = ContractStopWatch.startWatch(VehicleSaleContractDetailGroupsService.class, "beforeExecute", "setSalePriceMin");
        this.setSalePriceMin(entityProxy);
        ContractStopWatch.stop(watch2);


        ContractStopWatch.stop(watch);
    }

    private void validateRecord(EntityProxy<VehicleSaleContractDetailGroups> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        VehicleSaleContractDetailGroups detailGroups = entityProxy.getEntity();
        EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster();
        VehicleSaleContracts contracts = contractsProxy.getEntity();

        if (detailGroups.getIsContainInsuranceCost() == null) {
            detailGroups.setIsContainInsuranceCost(false);
        }

        if (StringUtils.isEmpty(detailGroups.getVnoId()) || StringUtils.isEmpty(detailGroups.getVehicleVno())) {
            throw new ServiceException("车型型号不能为空");
        }

        if (Tools.toInt(detailGroups.getVehicleQuantity()) <= 0) {
            throw new ServiceException(String.format("%s的数量必须大于0", detailGroups.getVehicleVno()));
        }

        //增加校验： 的分组数量%s不等于车辆明细数量%s
        int detailCount = getVehicleContractDetailCount(entityProxy);
        if (Tools.toInt(detailGroups.getVehicleQuantity()) != detailCount) {
            throw new ServiceException(String.format("%s的分组数量%s不等于车辆明细数量%s", detailGroups.getVehicleVno(), Tools.toInt(detailGroups.getVehicleQuantity()), detailCount));
        }

        if (detailGroups.getDeposit() == null) {
            detailGroups.setDeposit(BigDecimal.ZERO);
        }
        if (detailGroups.getDeposit().compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException(String.format("%s的预付订金不能为负数", detailGroups.getVehicleVno()));
        }
        if (StringUtils.isEmpty(detailGroups.getVehicleColor())) {
            throw new ServiceException(String.format("%s的车辆颜色不能为空", detailGroups.getVehicleVno()));
        }

        if (StringUtils.isEmpty(detailGroups.getBelongToSupplierName())) {
            throw new ServiceException(String.format("%s的挂靠单位不能为空", detailGroups.getVehicleVno()));
        }

        if (StringUtils.isEmpty(detailGroups.getVehicleOwnerName())) {
            throw new ServiceException(String.format("%s的车主不能为空", detailGroups.getVehicleVno()));
        }

        if (Tools.toBigDecimal(detailGroups.getVehiclePrice()).compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException(String.format("%s的车辆单价必须大于0", detailGroups.getVehicleVno()));
        }
        //TODO 车款合计不能小于已建立预算单的车辆贷款金额

        if (detailGroups.getDiscountAmount() == null) {
            detailGroups.setDiscountAmount(BigDecimal.ZERO);
        }
        if (detailGroups.getDiscountAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException(String.format("%s的优惠金额不能小于0", detailGroups.getVehicleVno()));
        }

        if (detailGroups.getDiscountAmount().compareTo(detailGroups.getVehiclePrice()) > 0) {
            throw new ServiceException(String.format("%s的优惠金额不能大于车辆单价", detailGroups.getVehicleVno()));
        }

        if (detailGroups.getVehicleProfit() == null) {
            detailGroups.setVehicleProfit(BigDecimal.ZERO);
        }
        if (detailGroups.getVehicleProfit().compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException(String.format("%s的客户佣金不能小于0", detailGroups.getVehicleVno()));
        }

        //如果佣金不为0且佣金客户为空时，默认成销售客户
        if (detailGroups.getVehicleProfit().compareTo(BigDecimal.ZERO) > 0 && StringUtils.isEmpty(detailGroups.getCustomerIdProfit())) {

            detailGroups.setCustomerIdProfit(contracts.getCustomerId());
            detailGroups.setCustomerNoProfit(contracts.getCustomerNo());
            detailGroups.setCustomerNameProfit(contracts.getCustomerName());
        }

        //如果佣金为0，清除掉佣金客户
        if (detailGroups.getVehicleProfit().compareTo(BigDecimal.ZERO) == 0 && StringUtils.isNotEmpty(detailGroups.getCustomerIdProfit())) {

            detailGroups.setCustomerIdProfit(null);
            detailGroups.setCustomerNoProfit(null);
            detailGroups.setCustomerNameProfit(null);
        }

        if (detailGroups.getLargessAmount() == null) {
            detailGroups.setLargessAmount(BigDecimal.ZERO);
        }
        if (detailGroups.getLargessAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException(String.format("%s的公司赠券不能小于0", detailGroups.getVehicleVno()));
        }

        if (StringUtils.isEmpty(detailGroups.getSubjectMatter())) {
            throw new ServiceException(String.format("%s的运输标的物不能为空", detailGroups.getVehicleVno()));
        }

        if (!"允许".equals(saleContractService1.getSysOptionValue(SaleContractService.VEHICLE_MIN_SALE_PRICE_CONTROL))) {
            Map<String, Object> paramMap = new HashMap<>(3);
            paramMap.put("stationId", contracts.getStationId());
            paramMap.put("vnoId", detailGroups.getVnoId());
            paramMap.put("signTime", contracts.getSignTime() == null ? null : contracts.getSignTime().toString());
            List<Map<String, Object>> list = saleContractService1.getVehicleMinPrice(paramMap);
            if (list == null || list.size() == 0) {
                throw new ServiceException(String.format("车型%s在基础资料中未维护最低限价", detailGroups.getVehicleVno()));
            }

//            String sql = "SELECT a.self_id,ISNULL(NULLIF(b.price_sale,0),ISNULL(a.price_sale,0)) AS price_sale FROM dbo.base_vehicle_model_catalog a\n" + "LEFT JOIN dbo.base_vehicle_model_catalog_price b ON a.self_id=b.parent_id AND b.station_id=:stationId\n" + " WHERE a.self_id =:vnoId AND ISNULL(NULLIF(b.price_sale,0),ISNULL(a.price_sale,0))=0";
//            Map<String, Object> paramMap = new HashMap<>(2);
//            paramMap.put("stationId", contracts.getStationId());
//            paramMap.put("vnoId", detailGroups.getVnoId());
//            List<Map<String, Object>> list = baseDao.getMapBySQL(sql, paramMap);
//            if (list != null && list.size() > 0) {
//                throw new ServiceException(String.format("车型%s在基础资料中未维护最低限价", detailGroups.getVehicleVno()));
//            }
        }

        List<EntityProxy<VehicleInvoicesGroups>> invoiceGroupProxyList = entityProxy.getSlaves(VehicleInvoicesGroups.class.getSimpleName());
        int n = 0;
        if (invoiceGroupProxyList != null && invoiceGroupProxyList.size() > 0) {
            for (EntityProxy<VehicleInvoicesGroups> invoiceGroupProxy : invoiceGroupProxyList) {
                if (invoiceGroupProxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                VehicleInvoicesGroups invoicesGroups = invoiceGroupProxy.getEntity();
                if (invoicesGroups.getInvoiceAmount() == null) {
                    invoicesGroups.setInvoiceAmount(BigDecimal.ZERO);
                }

                if (invoicesGroups.getInvoiceAmount().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new ServiceException(String.format("%s的车辆发票金额必须大于0", detailGroups.getVehicleVno()));
                }

                if (StringUtils.isEmpty(invoicesGroups.getInvoiceType())) {
                    throw new ServiceException(String.format("%s的车辆发票种类不能为空", detailGroups.getVehicleVno()));
                }

                if ("购车发票".equals(invoicesGroups.getInvoiceType())) {
                    n++;
                }
            }
        }

        if (n > 1) {
            throw new ServiceException(String.format("%s不能有多张购车发票", detailGroups.getVehicleVno()));
        }

        //主要是应对之后的将东贸版本升级到其他dvplus版上，兼容现有业务。
        //【车辆销售合同】模块保存时，服务端那需要加个判断，当vehicle_sale_contract_detail_groups的vehicle_quantity字段更改时，
        // 如果数量大于1时，要检查下是否存在未报销的报销方式为“新增费用”的报销单，如果有，则不允许保存 -20181130
        if (entityProxy.getOperation() == Operation.UPDATE) {
            VehicleSaleContractDetailGroups oriDetailGroups = entityProxy.getOriginalEntity();
            if (Tools.toInt(detailGroups.getVehicleQuantity()) != Tools.toInt(oriDetailGroups.getVehicleQuantity())
                    && Tools.toInt(detailGroups.getVehicleQuantity()) > 1) {
                //如果能查到值，则group_id对应的表数据的vehicle_quantity不能修改为大于1的值。提示：车型X销售数量不能大于1，该组存在报销方式为”新增费用“的未审报销单！
                String sql = "SELECT d.group_id FROM office_charge_reimbursements_details a\n" +
                        "LEFT JOIN dbo.office_charge_reimbursements b ON b.document_no = a.document_no\n" +
                        "LEFT JOIN dbo.vehicle_sale_contract_detail c ON a.vscv_id=c.contract_detail_id\n" +
                        "LEFT JOIN dbo.vehicle_sale_contract_detail_groups d ON c.group_id=d.group_id\n" +
                        "WHERE b.status<50 AND b.reimbursement_mode=20 AND d.group_id=:groupId";
                Map<String, Object> paramMap = new HashMap<>(2);
                paramMap.put("groupId", detailGroups.getGroupId());
                List<Map<String, Object>> resultList = baseDao.getMapBySQL(sql, paramMap);
                if (resultList != null && resultList.size() > 0) {
                    throw new ServiceException(String.format("车型%s销售数量不能大于1，该组存在报销方式为“新增费用”的未审报销单", detailGroups.getVehicleVno()));
                }
            }

        }

    }

    private void initExtProperty(EntityProxy<VehicleSaleContractDetailGroups> entityProxy) {
        //日志记录需要
        /*CASE WHEN a.ways_point IS NULL OR
        a.ways_point = '' THEN a.start_point + ',' + a.end_point ELSE a.start_point + ',' + a.ways_point
                + ',' + a.end_point END AS transportRoutes,*/
        VehicleSaleContractDetailGroups entity = entityProxy.getEntity();
        VehicleSaleContractDetailGroups oriEntity = entityProxy.getOriginalEntity();
        VehicleSaleContractDetailGroups[] entities = new VehicleSaleContractDetailGroups[]{entity, oriEntity};
        for (VehicleSaleContractDetailGroups entityGroup : entities) {
            if (null != entityGroup) {
                if (StringUtils.isEmpty(entityGroup.getWaysPoint())) {
                    if (StringUtils.isNotEmpty(entityGroup.getStartPoint()) && StringUtils.isNotEmpty(entityGroup.getEndPoint())) {
                        entityGroup.setTransportRoutes(String.format("%s,%s", entityGroup.getStartPoint(), entityGroup.getEndPoint()));
                    } else if (StringUtils.isNotEmpty(entityGroup.getStartPoint()) && StringUtils.isEmpty(entityGroup.getEndPoint())) {
                        entityGroup.setTransportRoutes(entityGroup.getStartPoint());
                    } else if (StringUtils.isNotEmpty(entityGroup.getEndPoint()) && StringUtils.isEmpty(entityGroup.getStartPoint())) {
                        entityGroup.setTransportRoutes(entityGroup.getEndPoint());
                    }
                } else {
                    if (StringUtils.isNotEmpty(entityGroup.getEndPoint())) {
                        entityGroup.setTransportRoutes(String.format("%s,%s", entityGroup.getWaysPoint(), entityGroup.getEndPoint()));
                    } else {
                        entityGroup.setTransportRoutes(entityGroup.getWaysPoint());
                    }

                }
            }
        }
        if (null != oriEntity) {
            Map<String, Object> param = new HashMap<>(1);
            param.put("groupId", oriEntity.getGroupId());
            List<Map<String, Object>> list = this.baseDao.getMapBySQL("select income_tot,cost_tot from vw_vehicle_sale_contract_detail_groups where group_id=:groupId", param);
            if (null != list && !list.isEmpty()) {
                Map<String, Object> map = list.get(0);
                Object incomeTot = map.get("income_tot");
                if (null != incomeTot) {
                    oriEntity.setIncomeTot(new BigDecimal(incomeTot.toString()));
                }
                Object costTot = map.get("cost_tot");
                if (null != costTot) {
                    oriEntity.setCostTot(new BigDecimal(costTot.toString()));
                }
                oriEntity.setProfitTot(Tools.toBigDecimal(oriEntity.getIncomeTot()).subtract(Tools.toBigDecimal(oriEntity.getCostTot())).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
    }

    @Override
    public void execute(EntityProxy<VehicleSaleContractDetailGroups> entityProxy) {
        StopWatch watch = ContractStopWatch.startWatch(VehicleSaleContractDetailGroupsService.class, "execute");

        StopWatch watch0 = ContractStopWatch.startWatch(VehicleSaleContractDetailGroupsService.class, "execute", "computationVehiclePriceTotal");
        this.computationVehiclePriceTotal(entityProxy);
        ContractStopWatch.stop(watch0);

        StopWatch watch1 = ContractStopWatch.startWatch(VehicleSaleContractDetailGroupsService.class, "execute", "dealDataBeforeSave");
        this.dealDataBeforeSave(entityProxy);
        ContractStopWatch.stop(watch1);

        ContractStopWatch.stop(watch);
    }

    public int getVehicleContractDetailCount(EntityProxy<VehicleSaleContractDetailGroups> entityProxy) {
        int count = 0;
        if (entityProxy.getOperation() == Operation.DELETE) {
            return count;
        }
        List<EntityProxy<VehicleSaleContractDetail>> detailProxies = entityProxy.getSlaves(VehicleSaleContractDetail.class.getSimpleName());
        if (null == detailProxies || detailProxies.isEmpty()) {
            return count;
        }
        VehicleSaleContractDetail detail = null;
        for (EntityProxy<?> slaveEntityProxy : detailProxies) {
            if (slaveEntityProxy.getOperation() != Operation.DELETE) {
                count++;
            }
        }

        return count;
    }


    // 设置最低限价
    private void setSalePriceMin(EntityProxy<VehicleSaleContractDetailGroups> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        VehicleSaleContractDetailGroups detailGroup = entityProxy.getEntity();
        EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster();
        VehicleSaleContracts contracts = contractsProxy.getEntity();

        Map<String, Object> paramMap = new HashMap<>(3);
        paramMap.put("stationId", contracts.getStationId());
        paramMap.put("signTime", contracts.getSignTime() == null ? null : contracts.getSignTime().toString());
        paramMap.put("vnoId", detailGroup.getVnoId());

        StopWatch watch0 = ContractStopWatch.startWatch(VehicleSaleContractDetailGroupsService.class, "beforeExecute", "setSalePriceMin", "getVehicleMinPrice");
        List<Map<String, Object>> _dtPrice = saleContractService1.getVehicleMinPrice(paramMap);
        ContractStopWatch.stop(watch0);

        StopWatch watch1 = ContractStopWatch.startWatch(VehicleSaleContractDetailGroupsService.class, "beforeExecute", "setSalePriceMin", "getVehicleReferenceCost");
        List<Map<String, Object>> dtMC = saleContractService1.getVehicleReferenceCost(paramMap);
        ContractStopWatch.stop(watch1);

        double mMinPriceSys = 0.00D;//价格体系设置的最低限价
        double mMinProfitSys = 0.00D;//价格体系设置的最低利润
        double mCostSys = 0.00D;//价格体系设置的参考成本
        double mBig = 0.00D;       //大客户限价
        double mTer = 0.00D;       //终端限价
        double mLvl = 0.00D;       //二网限价
        double mCostSysOri = 0; //旧价格体系的参考成本

        Map<String, Object> paramMap2 = new HashMap<>(2);
        paramMap2.put("stationId", contracts.getStationId());
        paramMap2.put("vnoId", detailGroup.getVnoId());


        StopWatch watch2 = ContractStopWatch.startWatch(VehicleSaleContractDetailGroupsService.class, "beforeExecute", "setSalePriceMin", "找出目录最低限价和参考成本");
        //如果价格体系没维护则取旧的车型目录的数据，兼容升级后客户没来得及维护的情况
        if (_dtPrice == null || _dtPrice.size() == 0) {
            //找出目录最低限价和参考成本
            String sql = "SELECT a.self_id AS vno_id,ISNULL(NULLIF(f.reference_cost,0),a.reference_cost) as reference_cost,\n" +
                    "ISNULL(NULLIF(f.profit_min,0), a.profit_min) AS profit_min_calc,\n" +
                    "ISNULL(NULLIF(f.price_sale,0), a.price_sale) AS price_sale_station,\n" +
                    "f.station_id AS station_id_station,\n" +
                    "ISNULL(NULLIF(ISNULL(NULLIF(f.price_sale_big_customer,0),a.price_sale_big_customer),0),ISNULL(NULLIF(f.price_sale,0), a.price_sale)) AS price_sale_big_customer,\n" +
                    "ISNULL(NULLIF(ISNULL(NULLIF(f.price_sale_terminal,0),a.price_sale_terminal),0),ISNULL(NULLIF(f.price_sale,0), a.price_sale)) AS price_sale_terminal,\n" +
                    "ISNULL( NULLIF(ISNULL(NULLIF(f.price_sale_sec_lvl,0),a.price_sale_sec_lvl),0),ISNULL(NULLIF(f.price_sale,0), a.price_sale)) AS price_sale_sec_lvl\n" +
                    "FROM base_vehicle_model_catalog AS a\n" +
                    "LEFT JOIN base_vehicle_model_catalog_price AS f ON a.self_id = f.parent_id AND f.station_id=:stationId \n" +
                    "WHERE a.status='有效' AND a.self_id=:vnoId";
            _dtPrice = baseDao.getMapBySQL(sql, paramMap2);
        }
        ContractStopWatch.stop(watch2);


        if (_dtPrice != null && _dtPrice.size() > 0) {
            mBig = Tools.toDouble((Number) _dtPrice.get(0).get("price_sale_big_customer"));
            mTer = Tools.toDouble((Number) _dtPrice.get(0).get("price_sale_terminal"));
            mLvl = Tools.toDouble((Number) _dtPrice.get(0).get("price_sale_sec_lvl"));
            mMinProfitSys = Tools.toDouble((Number) _dtPrice.get(0).get("profit_min_calc"));
            mCostSysOri = Tools.toDouble((Number) _dtPrice.get(0).get("reference_cost"));
        }

        if (dtMC != null && dtMC.size() > 0) {
            mCostSys = Tools.toDouble((Number) dtMC.get(0).get("vehicle_cost"));
        } else {
            //如果取不到新价格体系，则取旧价格体系
            mCostSys = mCostSysOri;
        }

        BaseRelatedObjects relatedObjects = baseDao.get(BaseRelatedObjects.class, contracts.getCustomerId());
        if (relatedObjects == null) {
            throw new ServiceException("未找到客户信息");
        }
        String _sObjectProperty = relatedObjects.getObjectProperty();
        short btySaleMode = Tools.toShort(contracts.getSaleMode(), (short) 30);

        if (btySaleMode == 10 || btySaleMode == 20)//二级网点和合作单位取二网价
        {
            mMinPriceSys = mLvl > 0 ? mLvl : mTer;
        } else if ("大客户".equals(_sObjectProperty)) {
            mMinPriceSys = mBig > 0 ? mBig : mTer;
        } else if ("一般客户".equals(_sObjectProperty)) {
            mMinPriceSys = mTer;
        } else {
            mMinPriceSys = mTer;
        }

        detailGroup.setMinSalePrice(Tools.toBigDecimal(mMinPriceSys));//存价格体系中获取的
        detailGroup.setMinProfit(Tools.toBigDecimal(mMinProfitSys));//存价格体系中获取的
        detailGroup.setVehicleCostRef(Tools.toBigDecimal(mCostSys));//记录价格体系的参考成本
    }


    private void dealDataBeforeSave(EntityProxy<VehicleSaleContractDetailGroups> entityProxy) {
        //合计分组信息
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        VehicleSaleContractDetailGroups detailGroup = entityProxy.getEntity();
        EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster();
        VehicleSaleContracts contracts = contractsProxy.getEntity();

        //1.合计保险费用
        BigDecimal category_income = BigDecimal.ZERO;
        BigDecimal insurance_cost_pf = BigDecimal.ZERO;
        List<EntityProxy<VehicleSaleContractInsuranceGroups>> insuranceGroupProxies = entityProxy.getSlaves(VehicleSaleContractInsuranceGroups.class.getSimpleName());
        if (insuranceGroupProxies != null && !insuranceGroupProxies.isEmpty()) {
            for (EntityProxy insuranceGroupProxy : insuranceGroupProxies) {
                if (insuranceGroupProxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                VehicleSaleContractInsuranceGroups insuranceGroup = (VehicleSaleContractInsuranceGroups) insuranceGroupProxy.getEntity();
                category_income = category_income.add(Tools.toBigDecimal(insuranceGroup.getCategoryIncome()));
//                insurance_cost_pf = insurance_cost_pf.add(Tools.toBigDecimal(insuranceGroup.getCategoryIncome()).multiply(Tools.toBigDecimal(insuranceGroup.getCategoryScale())));
                BigDecimal insurance_pf = Tools.toBigDecimal(insuranceGroup.getCategoryIncome()).subtract(Tools.toBigDecimal(insuranceGroup.getCategoryIncome()).multiply(Tools.toBigDecimal(insuranceGroup.getRebateRatio())));
                insurance_cost_pf = insurance_cost_pf.add(insurance_pf);
            }
        }
        category_income = category_income.setScale(2, BigDecimal.ROUND_HALF_UP);
        insurance_cost_pf = insurance_cost_pf.setScale(2, BigDecimal.ROUND_HALF_UP);
        detailGroup.setInsuranceIncome(category_income);
        detailGroup.setInsurancePf(insurance_cost_pf);

        //2.合计精品费用
        BigDecimal present_pf = BigDecimal.ZERO;
        BigDecimal present_income = BigDecimal.ZERO;
        List<EntityProxy<VehicleSaleContractPresentGroups>> presentGroupProxies = entityProxy.getSlaves(VehicleSaleContractPresentGroups.class.getSimpleName());
        if (presentGroupProxies != null && !presentGroupProxies.isEmpty()) {
            for (EntityProxy proxy : presentGroupProxies) {
                if (proxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                VehicleSaleContractPresentGroups group = (VehicleSaleContractPresentGroups) proxy.getEntity();
                present_pf = present_pf.add(Tools.toBigDecimal(group.getPosPrice()).multiply(Tools.toBigDecimal(group.getPlanQuantity())));
                present_income = present_income.add(Tools.toBigDecimal(group.getIncome()));
            }
        }
        present_pf = present_pf.setScale(2, BigDecimal.ROUND_HALF_UP);
        present_income = present_income.setScale(2, BigDecimal.ROUND_HALF_UP);
        detailGroup.setPresentPf(present_pf);
        detailGroup.setPresentIncome(present_income);

        //3.合计费用
        BigDecimal charge_pf = BigDecimal.ZERO;
        BigDecimal charge_income = BigDecimal.ZERO;
        List<EntityProxy<VehicleSaleContractChargeGroups>> chargeGroupProxies = entityProxy.getSlaves(VehicleSaleContractChargeGroups.class.getSimpleName());
        if (chargeGroupProxies != null && !chargeGroupProxies.isEmpty()) {
            for (EntityProxy proxy : chargeGroupProxies) {
                if (proxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                VehicleSaleContractChargeGroups group = (VehicleSaleContractChargeGroups) proxy.getEntity();
                if (Tools.toBoolean(group.getPaidByBill())) {//凭单支付
                    continue;
                }
                charge_pf = charge_pf.add(Tools.toBigDecimal(group.getChargePf()));
                charge_income = charge_income.add(Tools.toBigDecimal(group.getIncome()));
            }
        }
        charge_pf = charge_pf.setScale(2, BigDecimal.ROUND_HALF_UP);
        charge_income = charge_income.setScale(2, BigDecimal.ROUND_HALF_UP);
        detailGroup.setChargePf(charge_pf);
        detailGroup.setChargeIncome(charge_income);

        //4.合计改装
        BigDecimal conversion_pf = BigDecimal.ZERO;
        BigDecimal conversion_income = BigDecimal.ZERO;
        List<EntityProxy<VehicleSaleContractItemGroups>> itemGroupProxies = entityProxy.getSlaves(VehicleSaleContractItemGroups.class.getSimpleName());
        if (itemGroupProxies != null && !itemGroupProxies.isEmpty()) {
            for (EntityProxy proxy : itemGroupProxies) {
                if (proxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                VehicleSaleContractItemGroups group = (VehicleSaleContractItemGroups) proxy.getEntity();

                conversion_pf = conversion_pf.add(Tools.toBigDecimal(group.getItemCost()));
                conversion_income = conversion_income.add(Tools.toBigDecimal(group.getIncome()));
            }
        }
        conversion_pf = conversion_pf.setScale(2, BigDecimal.ROUND_HALF_UP);
        conversion_income = conversion_income.setScale(2, BigDecimal.ROUND_HALF_UP);
        detailGroup.setConversionPf(conversion_pf);
        detailGroup.setConversionIncome(conversion_income);


        //车辆明细
        BigDecimal dItemCostOri = BigDecimal.ZERO;//自带改装，不算自动关联

        List<EntityProxy<VehicleSaleContractDetail>> detailProxies = entityProxy.getSlaves(VehicleSaleContractDetail.class.getSimpleName());
        if (detailProxies != null && !detailProxies.isEmpty()) {
            for (EntityProxy proxy : detailProxies) {
                if (proxy.getOperation() == Operation.DELETE) {
                    continue;
                }
                VehicleSaleContractDetail detail = (VehicleSaleContractDetail) proxy.getEntity();

                detail.setInsuranceIncome(detailGroup.getInsuranceIncome().doubleValue());
                detail.setInsurancePf(detailGroup.getInsurancePf().doubleValue());
                detail.setPresentPf(detailGroup.getPresentPf().doubleValue());
                detail.setPresentIncome(detailGroup.getPresentIncome().doubleValue());
                detail.setChargePf(detailGroup.getChargePf().doubleValue());
                detail.setChargeIncome(detailGroup.getChargeIncome().doubleValue());
                detail.setConversionIncome(detailGroup.getConversionIncome().doubleValue());

                detail.setCardType(detailGroup.getCardType());
                detail.setContainerSize(detailGroup.getContainerSize());
                detail.setVehicleSize(detailGroup.getVehicleSize());
                detail.setVehicleWeight(detailGroup.getVehicleWeight());
                detail.setCurbWeight(detailGroup.getCurbWeight());
                detail.setRegistrationAddress(detailGroup.getRegistrationAddress());
                detail.setRegistrationTonnage(detailGroup.getRegistrationTonnage());
                detail.setUseProperty(detailGroup.getUseProperty());
                detail.setPeopleNumber(detailGroup.getPeopleNumber());
                detail.setOilNotice(detailGroup.getOilNotice());
                detail.setEpNotice(detailGroup.getEpNotice());
                detail.setTractiveTonnage(detailGroup.getTractiveTonnage());
                detail.setContainerInsideSize(detailGroup.getContainerInsideSize());

                //实际改装成本
                BigDecimal conversion_cost = BigDecimal.ZERO;
                if (StringUtils.isNotEmpty(detail.getVehicleId())) {
                    List<VehicleConversionDetail> conversionDetailList = (List<VehicleConversionDetail>) baseDao.findByHql("FROM VehicleConversionDetail WHERE vehicleId = ?", detail.getVehicleId());
                    if (conversionDetailList == null || conversionDetailList.size() == 0) {
                        break;
                    }
                    //新方式后将改装项目明细ID存入了改装单明细的备注中，用于计算改装预估成本时统计
                    for (VehicleConversionDetail conversionDetail : conversionDetailList) {
                        if (StringUtils.isEmpty(conversionDetail.getSaleContractItemId())) {
                            conversion_pf = conversion_pf.add(Tools.toBigDecimal(conversionDetail.getItemCost()));
                        }
                        //已确认
                        if (conversionDetail.getStatus() == 2) {
                            conversion_cost = conversion_cost.add(Tools.toBigDecimal(conversionDetail.getItemCost()));
                            if (!Tools.toBoolean(conversionDetail.getIsAutoRelation())) {//不算自动关联
                                dItemCostOri = dItemCostOri.add(Tools.toBigDecimal(conversionDetail.getItemCost()));
                            }
                        }

                    }

                    double dVsriAmount = 0;//该车已经加装或拆装的成本之和
                    String sql = "SELECT vehicle_id,SUM(vsri_amount) AS vsri_amount FROM (\n" + "                        SELECT b.vehicle_id, CASE WHEN a.install_type=10 THEN ISNULL(a.item_cost,0)*ISNULL(a.quantity,0) ELSE\n" + "                ISNULL(a.item_cost,0)*ISNULL(a.quantity,0)*-1 END AS vsri_amount\n" + "                FROM vehicle_superstructure_remove_and_installs a\n" + "                INNER JOIN dbo.vehicle_conversion b ON b.conversion_no = a.conversion_no\n" + "                WHERE a.status IN (10) AND b.status=60 AND isnull(b.is_secondhand,0)=0 AND b.vehicle_id =:vehicleId) a GROUP BY vehicle_id";
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("vehicleId", detail.getVehicleId());
                    List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
                    if (list != null && list.size() > 0) {
                        Map<String, Object> result = list.get(0);
                        dVsriAmount = Tools.toDouble((Double) result.get("vsri_amount"));
                    }


                    double dCostTransfer = 0;//该车的成本转移金额
                    sql = "SELECT * FROM dbo.vehicle_cost_transfer WHERE approve_status =1 AND (vehicle_id_out =:vehicleId OR vehicle_id_in =:vehicleId)";
                    List<Map<String, Object>> list2 = baseDao.getMapBySQL(sql, params);
                    if (list2 != null && list2.size() > 0) {
                        for (Map<String, Object> result : list2) {
                            if (StringUtils.equals(detail.getVehicleId(), (String) result.get("vehicle_id_out"))) {
                                dCostTransfer -= Tools.toDouble((Double) result.get("cost_transfer"));
                            } else if (StringUtils.equals(detail.getVehicleId(), (String) result.get("vehicle_id_in"))) {
                                dCostTransfer += Tools.toDouble((Double) result.get("cost_transfer"));
                            }
                        }
                    }

                    detail.setConversionPf(conversion_pf.doubleValue() + dVsriAmount + dCostTransfer);
                    detail.setConversionCost(conversion_cost.doubleValue() + dVsriAmount + dCostTransfer);
                }

                //销售订金
                if ("车辆录入".equals(saleContractService1.getSysOptionValue(SaleContractService.VEHICLE_DEPOSIT_INPUT_TYPE))) {
                } else//合同录入时，车辆订金为0
                {
                    detail.setDeposit(0.00D);
                }
            }
        }

        //更新车辆收入、成本和利润，不然写日志仍然记录的是旧的，因这3个字段是sql算出来的
        BigDecimal mReturn = BigDecimal.ZERO;//返利
        String sql = "SELECT a.vno_id,SUM(a.profit_return_amount) AS profit_return_amount FROM dbo.vehicle_profit_return_template_detail a\n" + "\t\t   WHERE a.approve_status=1 AND a.vno_id =:vnoId AND a.vpt_no IN (\n" + "\t\t   SELECT a.vpt_no FROM (SELECT vpt_no,ROW_NUMBER() OVER (PARTITION BY vpt_no ORDER BY end_month DESC) AS rowNum\n" + "\t\t   FROM dbo.vehicle_profit_return_template WHERE approve_status=1) a WHERE a.rowNum=1) GROUP BY a.vno_id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vnoId", detailGroup.getVnoId());
        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
        if (list != null && list.size() > 0) {
            Map<String, Object> result = list.get(0);
            mReturn = Tools.toBigDecimal((Double) result.get("profit_return_amount"));
        }

        //如果是否含保费选择是 ，收入=单台合计 ；选择否： 收入=单台合计+保险
        if (Tools.toBoolean(detailGroup.getIsContainInsuranceCost())) {
            detailGroup.setIncomeTot(Tools.toBigDecimal(detailGroup.getVehiclePriceTotal())
                    .setScale(2, BigDecimal.ROUND_HALF_UP));
        } else {
            detailGroup.setIncomeTot(Tools.toBigDecimal(detailGroup.getVehiclePriceTotal())
                    .add(Tools.toBigDecimal(detailGroup.getInsuranceIncome()))
                    .setScale(2, BigDecimal.ROUND_HALF_UP));
        }


        BigDecimal mTotCost = getPriceSaleBase(entityProxy).add(dItemCostOri)
                .add(Tools.toBigDecimal(detailGroup.getInsurancePf()))
                .add(Tools.toBigDecimal(detailGroup.getPresentPf()))
                .add(Tools.toBigDecimal(detailGroup.getConversionPf()))
                .add(Tools.toBigDecimal(detailGroup.getChargePf()))
                .add(Tools.toBigDecimal(detailGroup.getVehicleProfit()))
                .add(Tools.toBigDecimal(detailGroup.getLargessAmount()));
        detailGroup.setCostTot(mTotCost.setScale(2, BigDecimal.ROUND_HALF_UP));
        detailGroup.setProfitTot(Tools.toBigDecimal(detailGroup.getIncomeTot())
                .subtract(Tools.toBigDecimal(detailGroup.getCostTot()))
                .setScale(2, BigDecimal.ROUND_HALF_UP));
    }


    private BigDecimal getPriceSaleBase(EntityProxy<VehicleSaleContractDetailGroups> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return BigDecimal.ZERO;
        }
        VehicleSaleContractDetailGroups detailGroup = entityProxy.getEntity();
        EntityProxy<VehicleSaleContracts> contractsProxy = entityProxy.getMaster();
        VehicleSaleContracts contracts = contractsProxy.getEntity();


        double mStockMinPrice = 0.00D;//库存所设置的最低限价
        double mStockMinProfit = 0.00D;//库存所设置的最低利润

        String sql = "SELECT a.group_id,MAX(b.min_sale_price) AS stock_min_price,MAX(b.vehicle_lowest_profit) AS stock_min_profit\n" +
                "\t\t\tFROM dbo.vehicle_sale_contract_detail a\n" +
                "\t\t\tINNER JOIN vehicle_stocks b ON a.vehicle_id=b.vehicle_id\n" +
                "\t\t\tWHERE ISNULL(a.vehicle_id,'')<>'' AND a.group_id=:groupId GROUP BY a.group_id";
        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("groupId", detailGroup.getGroupId());

        List<Map<String, Object>> list = baseDao.getMapBySQL(sql, paramMap);
        if (list != null && list.size() > 0) {
            mStockMinPrice = Tools.toDouble((Number) list.get(0).get("stock_min_price"));
            mStockMinProfit = Tools.toDouble((Number) list.get(0).get("stock_min_profit"));
        }

        byte btyVehicleKind = (byte) 0;//车型类型
        sql = "select vehicle_kind from vw_vehicle_sale_contract_detail_groups where group_id =:groupId";
        list = baseDao.getMapBySQL(sql, paramMap);
        if (list != null && list.size() > 0) {
            btyVehicleKind = Tools.toByte((Number) list.get(0).get("vehicle_kind"));
        }

        BigDecimal mMinPrice = btyVehicleKind == 2 ? Tools.toBigDecimal(detailGroup.getVehiclePrice()) :
                (mStockMinPrice > 0 ? Tools.toBigDecimal(mStockMinPrice) : Tools.toBigDecimal(detailGroup.getMinSalePrice())); //detailGroup.getMinSalePrice()在SetSalePriceMin中计算的


        //drVehicle["price_sale_base"] = mMinPrice; //车辆成本
        return mMinPrice;

    }

    /**
     * 车辆单价合计
     *
     * @param entityProxy
     */
    private void computationVehiclePriceTotal(EntityProxy<VehicleSaleContractDetailGroups> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            return;
        }
        VehicleSaleContractDetailGroups group = entityProxy.getEntity();
        List<EntityProxy<VehicleSaleContractDetail>> contractDetails = entityProxy.getSlaves(VehicleSaleContractDetail.class.getSimpleName());
        if (null == contractDetails || contractDetails.isEmpty()) {
            throw new ServiceException(String.format(MSG, group.getGroupId(), group.getVehicleVno()));
        }
        VehicleSaleContractDetail detail = null;
        for (EntityProxy<?> slaveEntityProxy : contractDetails) {
            if (slaveEntityProxy.getOperation() != Operation.DELETE) {
                detail = (VehicleSaleContractDetail) slaveEntityProxy.getEntity();
            }
        }
        if (null == detail) {
            throw new ServiceException(String.format(MSG, group.getGroupId(), group.getVehicleVno()));
        }
        group.setVehiclePriceTotal(Tools.toBigDecimal(detail.getVehiclePriceTotal()));
    }


}
