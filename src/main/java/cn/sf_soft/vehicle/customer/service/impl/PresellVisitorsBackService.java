package cn.sf_soft.vehicle.customer.service.impl;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.file.service.FileService;
import cn.sf_soft.support.Command;
import cn.sf_soft.support.EntityProxy;
import cn.sf_soft.support.EntityRelation;
import cn.sf_soft.support.Operation;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsBack;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.*;


/**
 * @Auther: caigx
 * @Date: 2018/10/08 11:05
 * @Description: 意向客户-跟进信息服务
 */
@Service
public class PresellVisitorsBackService implements Command<PresellVisitorsBack> {
    protected static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PresellVisitorsBackService.class);

    /**
     * 计划跟进日期是否允许小于系统当前日期
     */
    private static final String PLAN_BACK_TIME_CONTROL = "PLAN_BACK_TIME_CONTROL";


    /**
     * 有效客户管理是否允许跟进日期大于当前日期
     */
    private static final String FOLLOW_UP_DATE = "FOLLOW_UP_DATE";

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private FileService fileService;

    @Autowired
    private SysOptionsDao sysOptionsDao;

    private static EntityRelation entityRelation;

    static {
        entityRelation = new EntityRelation(PresellVisitorsBack.class, PresellVisitorsBackService.class);
    }

    @Override
    public EntityRelation getEntityRelation() {
        return entityRelation;
    }

    @Override
    public void beforeExecute(EntityProxy<PresellVisitorsBack> entityProxy) {
        PresellVisitorsBack visitorsBack = entityProxy.getEntity();
        SysUsers user = HttpSessionStore.getSessionUser();
        if (entityProxy.getOperation() == Operation.CREATE) {
            visitorsBack.setBacker(user.getUserFullName());
            String stationId = user.getDefaulStationId();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            visitorsBack.setStationId(stationId);
            visitorsBack.setCreator(user.getUserFullName());
            visitorsBack.setCreateTime(now);
            visitorsBack.setModifier(user.getUserFullName());
            visitorsBack.setModifyTime(now);
        } else if (entityProxy.getOperation() == Operation.UPDATE) {
            visitorsBack.setModifier(user.getUserFullName());
            visitorsBack.setModifyTime(new Timestamp(System.currentTimeMillis()));
        }
        validateRecord(entityProxy);
    }

    private void validateRecord(EntityProxy<PresellVisitorsBack> entityProxy) {
        if (entityProxy.getOperation() == Operation.DELETE || entityProxy.getOperation() == Operation.NONE) {
            return;
        }
        PresellVisitorsBack visitorsBack = entityProxy.getEntity();
        // 跟进记录：计划跟进日期；
        // 如果填写了详情，则还需要验证：跟进日期，跟进目的、跟进方式、下一步检查人、是否继续跟进，如果继续跟进为是，下次跟进日是期不能为空
        if (visitorsBack.getPlanBackTime() == null) {
            throw new ServiceException("跟进记录中计划跟进日期不能为空");
        }

        if (StringUtils.isNotBlank(visitorsBack.getBackContent())) {
            if (visitorsBack.getRealBackTime() == null) {
                throw new ServiceException("跟进记录中跟进日期不能为空");
            }

            if (StringUtils.isBlank(visitorsBack.getBackPurpose())) {
                throw new ServiceException("跟进记录中目的不能为空");
            }

            if (StringUtils.isBlank(visitorsBack.getBackWay())) {
                throw new ServiceException("跟进记录中方式不能为空");
            }

            if (visitorsBack.getIsContinueBack() == null) {
                visitorsBack.setIsContinueBack(false); //是否继续跟进 默认“否”
            }

//            if (Tools.toBoolean(visitorsBack.getIsContinueBack())) {
//                if (visitorsBack.getNextBackTime() == null) {
//                    throw new ServiceException("跟进记录中继续跟进为是则下次跟进日期不能为空");
//                }
//            }
        }

        if (!"允许".equals(sysOptionsDao.getOptionForString(PLAN_BACK_TIME_CONTROL))) {
            Timestamp planBackTime = null;
            Timestamp nextBackTime = null;
            if (entityProxy.getOperation() == Operation.CREATE) {
                planBackTime = visitorsBack.getPlanBackTime();
                nextBackTime = visitorsBack.getNextBackTime();
            } else if (entityProxy.getOperation() == Operation.UPDATE) {
                PresellVisitorsBack oriVisitorBack = entityProxy.getOriginalEntity();
//                if (oriVisitorBack != null && visitorsBack.getPlanBackTime() != oriVisitorBack.getPlanBackTime()) {
////                    //计划跟进日期修改时才校验
////                    planBackTime = visitorsBack.getPlanBackTime();
////                }
                //计划跟进日期 新增时校验，下次跟进日期当新增时或有NULL->有值时判断
                if (oriVisitorBack != null && oriVisitorBack.getPlanBackTime() == null) {
                    nextBackTime = visitorsBack.getNextBackTime();
                }
            }

            if (nextBackTime != null && DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH).after(DateUtils.truncate(nextBackTime, Calendar.DAY_OF_MONTH))) {
                throw new ServiceException(String.format("下次跟进%tF不能小于当前日期", nextBackTime));
            }

            //按天比较
            if (planBackTime != null && DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH).after(DateUtils.truncate(planBackTime, Calendar.DAY_OF_MONTH))) {
                throw new ServiceException(String.format("计划跟进%tF不能小于当前日期", planBackTime));
            }

        }

        //ADM19010036 - 跟进记录中的跟进日期不能大于当前日期
        if ("否".equals(sysOptionsDao.getOptionForString(FOLLOW_UP_DATE))) {
            Timestamp realBackTime = visitorsBack.getRealBackTime();
            if (realBackTime != null && DateUtils.truncate(realBackTime, Calendar.DAY_OF_MONTH).after(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH))) {
                throw new ServiceException(String.format("跟进记录中的跟进日期%tF不能大于当前日期", realBackTime));
            }
        }
    }

    @Override
    public void execute(EntityProxy<PresellVisitorsBack> entityProxy) {
        PresellVisitorsBack back = entityProxy.getEntity();
        SysUsers user = HttpSessionStore.getSessionUser();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (entityProxy.getOperation() == Operation.CREATE) {
            //下一步检查的要赋默认值，默认值在【检查人设置】模块设置，数据表：base_setting_checker
//            String sql = "select * from base_setting_checker where  station_id = :stationId and bound_value like :department";
//            Map<String, Object> params = new HashMap<String, Object>(2);
//            params.put("stationId", user.getDefaulStationId());
//            params.put("department", "%" + user.getDepartment() + "%");
//
//            List<Map<String, Object>> list = baseDao.getMapBySQL(sql, params);
//            if (list != null && list.size() > 0) {
//                Map<String, Object> item = list.get(0);
//                back.setChecker((String)item.get("checker"));
//                back.setCheckerId((String)item.get("checker_id"));
//            }
        }

        if (entityProxy.getOperation() == Operation.UPDATE) {
            PresellVisitorsBack oriBack = entityProxy.getOriginalEntity();
            if (!StringUtils.equals(back.getBackContent(), oriBack.getBackContent()) && StringUtils.isNotBlank(back.getBackContent())) {
                back.setBacker(user.getUserFullName());
                back.setRealBackTime(now);
            }
        }

//        fillPlanBackTime(entityProxy);
        //保存图片
        savePics(entityProxy);
    }

    //同步InterestedCustomers的PlanBackTime
//    private void fillPlanBackTime(EntityProxy<PresellVisitorsBack> entityProxy) {
//        if (entityProxy.getOperation() == Operation.DELETE || entityProxy.getOperation() == Operation.NONE) {
//            return;
//        }
//        PresellVisitorsBack back = entityProxy.getEntity();
//        if (back == null || back.getPlanBackTime() == null) {
//            return;
//        }
//        EntityProxy<InterestedCustomers> interestedCustomersEntityProxy = entityProxy.getMaster();
//        if (interestedCustomersEntityProxy.getOperation() == Operation.DELETE) {
//            return;
//        }
//        InterestedCustomers customers = interestedCustomersEntityProxy.getEntity();
//        if(customers.getPlanBackTime() == null || customers.getPlanBackTime().getTime()<back.getPlanBackTime().getTime()){
//            customers.setPlanBackTime(back.getPlanBackTime());
//        }
//    }

    private void savePics(EntityProxy<PresellVisitorsBack> entityProxy) {
        PresellVisitorsBack back = entityProxy.getEntity();
        SysUsers user = HttpSessionStore.getSessionUser();
        if (entityProxy.getOperation() == Operation.UPDATE || entityProxy.getOperation() == Operation.CREATE) {
            String pics = fileService.addPicsToFtp(user, back.getBackId(), back.getPics());
            String imgUrls = fileService.addPicsToFtp(user, back.getBackId(), back.getImgUrls());
            back.setPics(pics);
            back.setImgUrls(imgUrls);
        }
    }


//    public static void main(String[] args) {
//
//        Timestamp planBackTime = Timestamp.valueOf("2019-05-30 12:30:30");
//        if(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH).after(DateUtils.truncate(planBackTime, Calendar.DAY_OF_MONTH))){
//            System.out.println(String.format("%tF%n  %tD%n", planBackTime, planBackTime));
//        }
//    }
}
