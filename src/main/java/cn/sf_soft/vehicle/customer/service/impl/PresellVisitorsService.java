package cn.sf_soft.vehicle.customer.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.model.InterestedCustomers;
import cn.sf_soft.vehicle.customer.model.PresellVisitors;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsFail;
import org.apache.commons.lang3.StringUtils;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Auther: caigx
 * @Date: 2018/10/08 11:05
 * @Description: 意向客户-线索信息服务
 */
@Service
public class PresellVisitorsService implements Command<PresellVisitors> {

    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PresellVisitorsService.class);

    @Autowired
    private BaseDao baseDao;

    private static EntityRelation entityRelation;

    static {
        entityRelation = new EntityRelation(PresellVisitors.class, PresellVisitorsService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<PresellVisitors> entityProxy) {
        SysUsers user = HttpSessionStore.getSessionUser();
        if (entityProxy.getOperation() == Operation.CREATE) {
            PresellVisitors visitors = entityProxy.getEntity();
            PresellVisitors oriVisitors = entityProxy.getOriginalEntity();

            String stationId = user.getDefaulStationId();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            visitors.setStationId(stationId);
            visitors.setCreateUnitId(user.getLoginDepartmentId());
            visitors.setVisitTime(now);
            visitors.setVisitorCount("1");
            visitors.setSeller(user.getUserName());
            visitors.setSellerId(user.getUserId());
            visitors.setCreator(user.getUserFullName());
            visitors.setCreateTime(now);
            visitors.setModifier(user.getUserFullName());
            visitors.setModifyTime(now);
            visitors.setClientType(HttpSessionStore.getSessionOs()); //记录新增时的客户端类型

            //同步intentLevelBak
            if (StringUtils.isNotEmpty(visitors.getIntentLevel())) {
                visitors.setIntentLevelBak(visitors.getIntentLevel());
            }

        } else if (entityProxy.getOperation() == Operation.UPDATE) {
            PresellVisitors visitors = entityProxy.getEntity();
            PresellVisitors oriVisitors = entityProxy.getOriginalEntity();

            //增加数据版本校验
            validateModifyTime(visitors, oriVisitors); //校验modifyTime


            Timestamp now = new Timestamp(System.currentTimeMillis());
            visitors.setModifier(user.getUserFullName());
            visitors.setModifyTime(now);

            //同步intentLevelBak
            if (StringUtils.isNotEmpty(visitors.getIntentLevel())) {
                visitors.setIntentLevelBak(visitors.getIntentLevel());
            }
        }

        validateRecord(entityProxy);
    }


    /**
     * 校验数据版本
     *
     * @param visitors
     * @param oriVisitors
     */
    protected void validateModifyTime(PresellVisitors visitors, PresellVisitors oriVisitors) {
        if (visitors == null || oriVisitors == null) {
            return;
        }
        Timestamp modifyTime = visitors.getModifyTime();
        Timestamp oriModifyTime = oriVisitors.getModifyTime();
        if (modifyTime == null || oriModifyTime == null) {
            return;
        }
        if (Math.abs(modifyTime.getTime() - oriModifyTime.getTime()) > 500) {
            throw new ServiceException(String.format("线索%s %s台已被修改，请刷新后再试", visitors.getVehicleVno(), visitors.getPurposeQuantity()));
        }
    }


    @Override
    public void execute(EntityProxy<PresellVisitors> entityProxy) {
        dealWhenFail(entityProxy);
        fillVisitorWay(entityProxy);
    }

    //ADM19040003 同步是否到店
    private void fillVisitorWay(EntityProxy<PresellVisitors> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE || entityProxy.getOperation() == Operation.NONE) {
            return;
        }
        EntityProxy<InterestedCustomers> interestedCustomersEntityProxy = entityProxy.getMaster();
        if (interestedCustomersEntityProxy.getOperation() == Operation.DELETE) {
            return;
        }

        PresellVisitors visitors = entityProxy.getEntity();
        if (entityProxy.getOperation() == Operation.UPDATE) {
            PresellVisitors oriVisitors = entityProxy.getOriginalEntity();
            //如果VisitWay未变化
            if (visitors != null && oriVisitors != null && StringUtils.equals(visitors.getVisitWay(), oriVisitors.getVisitWay())) {
                return;
            }
        }
        InterestedCustomers customer = interestedCustomersEntityProxy.getEntity();
        String oriWay = "";
        if (interestedCustomersEntityProxy.getOperation() == Operation.UPDATE) {
            InterestedCustomers oriCustomer = interestedCustomersEntityProxy.getOriginalEntity();
            oriWay = oriCustomer == null ? "" : oriCustomer.getVisitWay();
        }

        //同步是否到店
        if (StringUtils.isBlank(customer.getVisitWay()) || StringUtils.equals(customer.getVisitWay(), oriWay)) {
            if (StringUtils.isNotBlank(visitors.getVisitWay())) {
                customer.setVisitWay(visitors.getVisitWay());
            }
        }
    }

    /**
     * 处理战败线索
     *
     * @param entityProxy
     */
    private void dealWhenFail(EntityProxy<PresellVisitors> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE) {
            PresellVisitors oriVisitors = entityProxy.getOriginalEntity();
            //战败校验是否已经关联合同
            List<VehicleSaleContracts> contractsList = (List<VehicleSaleContracts>) baseDao.findByHql("FROM VehicleSaleContracts where visitorNo = ? and status<=50 ", oriVisitors.getVisitorNo());
            if (contractsList != null && contractsList.size() > 0) {
                throw new ServiceException(String.format("线索已关联合同%s，无法删除", contractsList.get(0).getContractNo()));
            }
            return;
        }

        SysUsers user = HttpSessionStore.getSessionUser();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        PresellVisitors visitors = entityProxy.getEntity();
        EntityProxy<InterestedCustomers> interestedCustomersEntityProxy = entityProxy.getMaster();
        InterestedCustomers interestedCustomers = interestedCustomersEntityProxy.getEntity();
        if (entityProxy.getOperation() == Operation.CREATE && visitors.getVisitResult() != null) {
            visitors.setFinishDate(now);
        } else if (entityProxy.getOperation() == Operation.UPDATE) {
            PresellVisitors oriVisitors = entityProxy.getOriginalEntity();
            visitors.setModifier(user.getUserFullName());
            visitors.setModifyTime(now);

            if (visitors.getVisitResult() != null && Tools.toShort(visitors.getVisitResult()) != Tools.toShort(oriVisitors.getVisitResult())) {
                if (Tools.toShort(visitors.getVisitResult()) == 20) {
                    //战败校验是否已经关联合同
                    List<VehicleSaleContracts> contractsList = (List<VehicleSaleContracts>) baseDao.findByHql("FROM VehicleSaleContracts where visitorNo = ? and status<=50 ", visitors.getVisitorNo());
                    if (contractsList != null && contractsList.size() > 0) {
                        throw new ServiceException(String.format("线索已关联合同%s，无法战败", contractsList.get(0).getContractNo()));
                    }
                }

                //如果是战败客户，除成交外其他结果，则写入战败客户表[10 成交，20 战败，40失控]
                if (Tools.toShort(visitors.getVisitResult()) == 20 || Tools.toShort(visitors.getVisitResult()) == 40) {
                    PresellVisitorsFail visitorsFail = baseDao.get(PresellVisitorsFail.class, visitors.getVisitorNo());
                    if (visitorsFail == null) {
                        visitorsFail = new PresellVisitorsFail();
                        visitorsFail.setVisitorNo(visitors.getVisitorNo());
                        visitorsFail.setVisitorId(visitors.getVisitorId());
                        visitorsFail.setVisitorName(interestedCustomers.getObjectName());
                        visitorsFail.setVisitorMobile(interestedCustomers.getMobile());
                        visitorsFail.setVisitorPhone(interestedCustomers.getPhone());

                        visitorsFail.setFailedReason(visitors.getReason());
                        visitorsFail.setEndDeal(false);
                        visitorsFail.setCreateTime(now);
                        visitorsFail.setCreator(user.getUserFullName());
                        baseDao.save(visitorsFail);
                    } else {
                        visitorsFail.setFailedReason(visitors.getReason());
                        visitorsFail.setEndDeal(false);
                        visitorsFail.setCreateTime(now);
                        visitorsFail.setCreator(user.getUserFullName());
                    }
                }
                visitors.setFinishDate(now);
            }
        }
    }

    private void validateRecord(EntityProxy<PresellVisitors> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE || entityProxy.getOperation() == Operation.NONE) {
            return;
        }
        PresellVisitors visitors = entityProxy.getEntity();
        PresellVisitors oriVisitors = entityProxy.getOriginalEntity();
        if (entityProxy.getOperation() == Operation.UPDATE && oriVisitors.getVisitResult() != null) {
            //如果之前已经结束的线索，什么都不再验证。
            return;
        }

        //线索信息：线索日期、意向级别、车辆品系、车型简称、马力段、意向颜色、意向台数、意向价格、购车方式
        if (visitors.getVisitTime() == null) {
            throw new ServiceException("线索中的线索日期不能为空");
        }

//        if (StringUtils.isEmpty(visitors.getSeller())) {
//            throw new ServiceException("线索中的销售人员不能为空");
//        }
//        if (StringUtils.isEmpty(visitors.getDeliveryLocus())) {
//            throw new ServiceException("线索中的销售网点不能为空");
//        }
        if (StringUtils.isEmpty(visitors.getIntentLevel())) {
            throw new ServiceException("线索中的意向级别不能为空");
        }
//        if (StringUtils.isEmpty(visitors.getVisitorLevel())) {
//            throw new ServiceException("线索中的成交概率不能为空");
//        }
//        if (StringUtils.isEmpty(visitors.getShortNameVno())) {
//            throw new ServiceException("线索中的意向车型不能为空");
//        }

//        if (StringUtils.isEmpty(visitors.getVehicleName())) {
//            throw new ServiceException("线索中的车辆名称不能为空");
//        }

//        if (StringUtils.isEmpty(visitors.getHorsepower())) {
//            throw new ServiceException("线索中的马力段不能为空");
//        }

        if (StringUtils.isEmpty(visitors.getVehicleStrain())) {
            throw new ServiceException("线索中的车辆品系不能为空");
        }

        if (StringUtils.isEmpty(visitors.getVehicleColor())) {
            throw new ServiceException("线索中的意向颜色不能为空");
        }

        if (visitors.getPlanPurchaseTime() == null) {
            throw new ServiceException("线索中的预购日期不能为空");
        }

        if (Tools.toInt(visitors.getPurposeQuantity()) < 0) {
            throw new ServiceException("线索中的意向台数不能小于0");
        }

        if (Tools.toInt(visitors.getPurposeQuantity()) > 1000) {
            throw new ServiceException("线索中的意向台数不能大于1000");
        }


        if (visitors.getPlanPurchaseTime() != null && visitors.getCreateTime() != null && DateUtils.truncate(visitors.getPlanPurchaseTime(), Calendar.DAY_OF_MONTH).before(DateUtils.truncate(visitors.getCreateTime(), Calendar.DAY_OF_MONTH))) {
            throw new ServiceException(String.format("线索中的预购日期%tF不能早于线索创建日期%tF", visitors.getPlanPurchaseTime(), visitors.getCreateTime()));
        }

//        if (Tools.toDouble(visitors.getVehiclePrice()) < 0.00D) {
//            throw new ServiceException("线索中的意向价格不能小于0");
//        }

        if (visitors.getBuyType() == null) {
            throw new ServiceException("线索中的购车方式不能为空");
        }
//        if (StringUtils.isBlank(visitors.getVisitWay())) {
//            throw new ServiceException("线索中，是否到店不能为空");
//        }

    }
}
