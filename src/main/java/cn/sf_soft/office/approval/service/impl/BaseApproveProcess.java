package cn.sf_soft.office.approval.service.impl;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.message.MessageSimpleEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.sf_soft.basedata.dao.SysLogsDao;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.dao.FinanceDocumentEntriesDao;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Constant.ApproveConditionType;
import cn.sf_soft.common.util.Constant.ApproveResultCode;
import cn.sf_soft.common.util.Constant.ApproveStatus;
import cn.sf_soft.common.util.Constant.OSType;
import cn.sf_soft.common.util.Constant.ResultCode;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.finance.voucher.service.VoucherAuto;
import cn.sf_soft.office.approval.dao.ApproveAgentDao;
import cn.sf_soft.office.approval.dao.ApproveConditionDao;
import cn.sf_soft.office.approval.dao.ApproveDocumentDao;
import cn.sf_soft.office.approval.dao.ApproveDocumentPointsDao;
import cn.sf_soft.office.approval.dao.ApproveLevelDao;
import cn.sf_soft.office.approval.dao.PrincipalDao;
import cn.sf_soft.office.approval.dto.ApproveInfo;
import cn.sf_soft.office.approval.dto.ApproveResult;
import cn.sf_soft.office.approval.model.ApproveAgent;
import cn.sf_soft.office.approval.model.ApproveConditions;
import cn.sf_soft.office.approval.model.ApproveDocuments;
import cn.sf_soft.office.approval.model.ApproveDocumentsPoints;
import cn.sf_soft.office.approval.model.ApproveLevels;
import cn.sf_soft.office.approval.model.ApproveLevelsPoints;
import cn.sf_soft.office.approval.model.ApproveLevelsPointsConditions;
import cn.sf_soft.office.approval.model.VwOfficeApproveDocuments;
import cn.sf_soft.user.dao.UserDao;
import cn.sf_soft.user.model.SysUsers;

/**
 * (新)审批Service的基类-为了适应新的审批流程修改
 *
 * @author caigx
 */
@Service("baseApproveProcess")
public abstract class BaseApproveProcess {

    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BaseApproveProcess.class);
    @Autowired
    protected ApproveLevelDao approveLevelDao;// 审批级别
    @Autowired
    protected ApproveConditionDao approveConditionDao;// 审批条件
    @Autowired
    protected ApproveDocumentDao approveDocumentDao;
    @Autowired
    protected PrincipalDao principalDao;// 部门负责人
    @Autowired
    protected ApproveAgentDao approveAgenDao;// 审批代理
    @Autowired
    protected UserDao userDao;
    @Autowired
    protected ApproveDocumentPointsDao documentPointDao;// 审批历史
    @Autowired
    protected FinanceDocumentEntriesDao financeDocumentEntriesDao;// 单据分录
    @Autowired
    protected SysLogsDao sysLogDao;// 系统日志
    @Autowired
    @Qualifier("baseDao")
    protected BaseDao dao;
    @Autowired
    protected VoucherAuto voucherAuto;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    // private Timestamp approveTimestamp; // 审批的时间戳

    public void setSysLogDao(SysLogsDao sysLogDao) {
        this.sysLogDao = sysLogDao;
    }

    public void setDocumentPointDao(ApproveDocumentPointsDao documentPointDao) {
        this.documentPointDao = documentPointDao;
    }

    public void setApproveLevelDao(ApproveLevelDao approveLevelDao) {
        this.approveLevelDao = approveLevelDao;
    }

    public void setApproveConditionDao(ApproveConditionDao approveConditionDao) {
        this.approveConditionDao = approveConditionDao;
    }

    public void setApproveDocumentDao(ApproveDocumentDao approveDocumentDao) {
        this.approveDocumentDao = approveDocumentDao;
    }

    public void setPrincipalDao(PrincipalDao principalDao) {
        this.principalDao = principalDao;
    }

    public void setApproveAgenDao(ApproveAgentDao approveAgenDao) {
        this.approveAgenDao = approveAgenDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setFinanceDocumentEntriesDao(FinanceDocumentEntriesDao financeDocumentEntriesDao) {
        this.financeDocumentEntriesDao = financeDocumentEntriesDao;
    }

    public void setDao(BaseDao dao) {
        this.dao = dao;
    }

    public void setVoucherAuto(VoucherAuto voucherAuto) {
        this.voucherAuto = voucherAuto;
    }

    /**
     * 处理审批列表中的审批单据信息
     *
     * @param vwOfficeApproveDocuments
     * @return
     */
    public VwOfficeApproveDocuments dealApproveDocument(VwOfficeApproveDocuments vwOfficeApproveDocuments) {
        return vwOfficeApproveDocuments;
    }

    /**
     * 是否有审批权限
     *
     * @return
     */
    public boolean hasApprovalPopedom() {
        if (StringUtils.isBlank(getApprovalPopedomId())) {
            // 子类中没有approvalPopedomId，默认通过
            return true;
        }
        SysUsers user = HttpSessionStore.getSessionUser();
        return user.getPopedomIds().contains(getApprovalPopedomId());
    }

    /**
     * 是否是死流程，当待审的审判点的所有审批人都没有审批权限时，为true
     *
     * @param vwOfficeApproveDocument
     * @return
     */
    public boolean isDeadApprove(VwOfficeApproveDocuments vwOfficeApproveDocument) {
        if (StringUtils.isBlank(getApprovalPopedomId())) {
            // 子类中没有重写approvalPopedomId，默认通过
            return false;
        }
        List<ApproveDocumentsPoints> list = documentPointDao.getApproveDocumentsPoints(vwOfficeApproveDocument.getDocumentId());
        for (ApproveDocumentsPoints point : list) {
            if (null != point.getApproveTime()) {
                continue;
            }
            if (StringUtils.isBlank(point.getApproverId())) {
                return true;
            }
            String[] arrIDs = point.getApproverId().split(",");
            for (String userId : arrIDs) {
                if (userDao.hasPopedom(userId, getApprovalPopedomId())) {
                    break;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是死节点
     *
     * @param point
     * @return
     */
    public boolean isDeadPoint(ApproveDocumentsPoints point) {
        if (StringUtils.isBlank(getApprovalPopedomId())) {
            // 子类中没有重写approvalPopedomId，默认通过
            return false;
        }
        if (Tools.toShort(point.getStatus()) != (short) 30) {
            return false;
        }
        if (StringUtils.isBlank(point.getApproverId())) {
            return true;
        }
        String[] arrIDs = point.getApproverId().split(",");
        for (String userId : arrIDs) {
            if (userDao.hasPopedom(userId, getApprovalPopedomId())) {
                break;
            }
            return true;
        }

        return false;
    }

    /**
     * 显示审批明细
     *
     * @param documentNo
     * @return
     */
    @SuppressWarnings("rawtypes")
    public abstract ApproveDocuments getDocumentDetail(String documentNo);

    /**
     * 获得审批权限Id
     *
     * @return
     */
    protected abstract String getApprovalPopedomId();

    /**
     * 获得提交单据的明细
     *
     * @param documentNo
     * @return
     */
    @SuppressWarnings("rawtypes")
    public ApproveDocuments getSubmitRecordDetail(String documentNo) {
        return getDocumentDetail(documentNo);
    }

    /**
     * 合并PC端和手机端的审批代码
     *
     * @param agree
     * @param approveDocument
     * @param comment
     * @param modifyTime
     * @param osType
     * @return
     * @author caigx
     * @create 2016-04-20
     */
    @SuppressWarnings("rawtypes")
    public ApproveResult approve(boolean agree, ApproveDocuments approveDocument, String comment, String modifyTime, OSType osType) {
//        if (osType == OSType.MOBILE) {//只对手机校验有没有审批权限
//            SysUsers user = HttpSessionStore.getSessionUser();
//            if (!userDao.hasPopedom(user.getUserId(), getApprovalPopedomId())) {
//                throw new ServiceException("审批失败：没有此单据的审批权限，请检查权限设置");
//            }
//        }
        if (checkDataChanged(modifyTime, approveDocument)) {
            throw new ServiceException("审批失败：数据发生更改，请重新刷新");
        }
        String osString = osType.getMessage(); // 手机,PC
        if (agree) {
            ApproveResult approveResult = approveAgreeByPC(approveDocument, (comment == null ? "" : comment) + "(" + osString + ")");
            return approveResult;
        } else {
            return approveDisagreeByPC(approveDocument, (comment == null ? "" : comment) + "(" + osString + ")");
        }
    }


    /**
     * 审批单据的提交接口
     *
     * @param agree
     * @param documentNo
     * @param comment
     * @param modifyTime
     * @param osType
     * @return
     */
    public ApproveResult submitRecord(boolean agree, String documentNo, String moduleId, String comment, String modifyTime, OSType osType) {
//		if (osType == OSType.MOBILE) {
//			throw new ServiceException("提交失败:手机端暂不支持单据提交操作");
//		}
        return doSubmitRecord(agree, documentNo, moduleId, comment, modifyTime, osType);
    }


    /**
     * 提交操作
     *
     * @param agree
     * @param documentNo
     * @param moduleId
     * @param comment
     * @param modifyTime
     * @param osType
     * @return
     */
    private ApproveResult doSubmitRecord(boolean agree, String documentNo, String moduleId, String comment, String modifyTime, OSType osType) {
        comment = StringUtils.isEmpty(comment) ? "" : comment;
        String osString = osType.getMessage(); // 手机,PC
        Timestamp submitTime = new Timestamp(new Date().getTime()); // 提交时间，
        ApproveDocuments recordDetailDocument = getSubmitRecordDetail(documentNo);
        String departmentId = recordDetailDocument.getDepartmentId();
        String submitStationId = recordDetailDocument.getSubmitStationId();
        if (recordDetailDocument == null || recordDetailDocument.getStatus() >= 20) {
            throw new ServiceException("提交失败:单据状态错误");
        }

        ApproveDocuments approveDocument = null;
        // 提交时创建或更新业务单据相应的审批单据
        approveDocument = approveDocumentDao.getApproveDocumentsByNo(documentNo);
        if (approveDocument == null) {
            approveDocument = new ApproveDocuments();
            approveDocument.setDocumentId(UUID.randomUUID().toString());
            approveDocument.setModuleId(moduleId);
            approveDocument.setStationId(recordDetailDocument.getStationId());
            String moduleName = approveDocumentDao.getModuleNameByModuleId(moduleId);
            approveDocument.setModuleName(moduleName);
            approveDocument.setDocumentNo(documentNo);
        }
        approveDocument.setUserId(recordDetailDocument.getUserId());
        approveDocument.setUserNo(recordDetailDocument.getUserNo());
        approveDocument.setUserName(recordDetailDocument.getUserName());
        approveDocument.setDepartmentId(recordDetailDocument.getDepartmentId());
        approveDocument.setDepartmentNo(recordDetailDocument.getDepartmentNo());
        approveDocument.setDepartmentName(recordDetailDocument.getDepartmentName());
        approveDocument.setSubmitStationId(recordDetailDocument.getSubmitStationId());
        approveDocument.setSubmitStationName(recordDetailDocument.getSubmitStationName());
        approveDocument.setSubmitTime(submitTime);

        // 创建业务单据相应的审批单据提交点
        ApproveDocumentsPoints submitPoint = new ApproveDocumentsPoints();
        submitPoint.setOadpId(UUID.randomUUID().toString());
        submitPoint.setDocumentId(approveDocument.getDocumentId());
        submitPoint.setStatus((short) 20);
        submitPoint.setApproveLevel(0);
        submitPoint.setApproveName("发起申请");
        submitPoint.setApproverId(approveDocument.getUserId());
        submitPoint.setApproverNo(approveDocument.getUserNo());
        submitPoint.setApproverName(approveDocument.getUserName());
        submitPoint.setApproveTime(submitTime);
        submitPoint.setRemark(String.format("%s(%s)", comment, osString));
        dao.save(submitPoint);

        // 是否在范围内
        boolean bBound = true;
        // 是否有效审批
        boolean bValidApprove = false;
        // 是否本人审批
        boolean bSelfApprove = false;

        ApproveInfo validApproveInfo = new ApproveInfo(); // 第一个有效的审批信息
        ApproveInfo selfApproveInfo = new ApproveInfo();// 自审的审批信息，当没有有效的审批信息时，需要自审的审批信息

        List<ApproveLevels> approveLevels = approveLevelDao.getApproveLevels(moduleId, submitStationId);
        if (approveLevels == null || approveLevels.size() == 0) {
            // 需求：ADM16070015 审批流程设置为空时，默认提交人即审批人，单据提交后自动审批
            logger.info("单据:" + documentNo + "审批流程为空，默认提交人即审批人");
            bSelfApprove = true;
            selfApproveInfo.setApproveLevel(1);
            selfApproveInfo.setApproveName("申请人自审");
            selfApproveInfo.setApproverID(approveDocument.getUserId());
            selfApproveInfo.setApproverNo(approveDocument.getUserNo());
            selfApproveInfo.setApproverName(approveDocument.getUserName());

        } else {
            // 遍历审批流程，创建业务单据相应的审批单据审批点
            for (ApproveLevels level : approveLevels) {
                String sLevelID = level.getLevelId();
                int nApproveLevel = level.getApproveLevel();
                // 注意，由于Set是无序的，如果直接通过level.getApprovelPoint()得到的审批点的顺序是不确定的，导致每次审批的审批人都可能不一致
                List<ApproveLevelsPoints> levelPoints = approveLevelDao.getApproveLevelsPoints(sLevelID);
                // 循环遍历审批点
                for (ApproveLevelsPoints point : levelPoints) {
                    String sApproveName = point.getApproveName();
                    short bytApproveMode = point.getApproveMode();
                    String sApproverID = point.getApproverId();
                    String sApproverNo = point.getApproverNo();
                    String sApproverName = point.getApproverName();
                    String sBound = point.getBoundValue();
                    boolean bBoundExcept = Tools.toBoolean(point.getBoundExcept());

                    // 如果无范围，则即为在范围内；否则判断提交部门是否在范围内
                    if (StringUtils.isEmpty(sBound)) {
                        bBound = true;
                    } else {
                        if (sBound.contains(approveDocument.getDepartmentId()) && !bBoundExcept || !sBound.contains(approveDocument.getDepartmentId()) && bBoundExcept) {
                            bBound = true;
                        } else {
                            bBound = false;
                        }
                    }
                    // 如果不在范围内，则转到下一个审批点
                    if (!bBound) {
                        continue;
                    }
                    // 循环遍历审批条件，是否满足条件
                    boolean condionIsValid = checkPonitConditions(point, documentNo, moduleId);

                    // 循环遍历完 条件后
                    // 如果不满足条件，则转到下一个审批点
                    if (!condionIsValid) {
                        continue;
                    }

                    // 如果审批方式为部门负责人，则审批人为提交部门的负责人
                    if (bytApproveMode == 10) {
                        SysUsers principal = principalDao.getPrincipal(departmentId);
                        if (principal == null) {
                            throw new ServiceException("提交失败:提交部门没有设置部门负责人");
                        }
                        sApproverID = principal.getUserId();
                        sApproverNo = principal.getUserNo();
                        sApproverName = principal.getUserName();
                    }

                    // 如果存在审批代理的情况，则审批人为代理审批人
                    // 提交时产生审批流程，如果存在审批代理情况，并且是多个审批人，需逐个将审批人替换为代理人 -20161206
                    String[] arrIDs = sApproverID.split(",");
                    String[] arrNos = sApproverNo.split(",");
                    String[] arrNames = sApproverName.split(",");

                    for (int i = 0; i < arrIDs.length; i++) {
                        ApproveAgent agent = approveAgenDao.getAgentByMoudleId(Constant.SysFlags.APPROVE_AGENT_CONFIRMED, arrIDs[i], moduleId, approveDocument.getSubmitStationId());
                        if (agent != null) {
                            SysUsers agentUser = userDao.getUserById(agent.getAgent());
                            arrIDs[i] = agent.getAgent();
                            arrNos[i] = agentUser.getUserNo();
                            arrNames[i] = agentUser.getUserName();
                        }
                    }
                    sApproverID = StringUtils.join(arrIDs, ",");
                    sApproverNo = StringUtils.join(arrNos, ",");
                    sApproverName = StringUtils.join(arrNames, ",");

                    // 如果审批人为提交人本人，则转到下一个审批级别，并记录当前审批信息
                    if (sApproverID != null && sApproverID.contains(approveDocument.getUserId())) {
                        bSelfApprove = true;
                        selfApproveInfo.setApproveLevel(nApproveLevel);
                        selfApproveInfo.setApproveName(sApproveName);
                        // selfApproveInfo.setApproverID(sApproverID);
                        // selfApproveInfo.setApproverNo(sApproverNo);
                        // selfApproveInfo.setApproverName(sApproverName);
                        selfApproveInfo.setApproverID(approveDocument.getUserId());
                        selfApproveInfo.setApproverNo(approveDocument.getUserNo());
                        selfApproveInfo.setApproverName(approveDocument.getUserName());
                        break;
                    }

                    // 如果还没有有效审批，则当前为首个有效审批点，并记录当前审批信息
                    if (!bValidApprove) {
                        bValidApprove = true;
                        validApproveInfo.setApproveLevel(nApproveLevel);
                        validApproveInfo.setApproveName(sApproveName);
                        validApproveInfo.setApproverID(sApproverID);
                        validApproveInfo.setApproverNo(sApproverNo);
                        validApproveInfo.setApproverName(sApproverName);
                    }

                    // 创建业务单据相应的审批单据审批点
                    ApproveDocumentsPoints newpoint = new ApproveDocumentsPoints();
                    newpoint.setOadpId(UUID.randomUUID().toString());
                    newpoint.setDocumentId(approveDocument.getDocumentId());
                    newpoint.setStatus((short) 30);
                    newpoint.setApproveLevel(nApproveLevel);
                    newpoint.setApproveName(sApproveName);
                    newpoint.setApproverId(sApproverID);
                    newpoint.setApproverNo(sApproverNo);
                    newpoint.setApproverName(sApproverName);
                    dao.save(newpoint);
                    break; //如果一个审批级别中有一个审批点符合条件，就直接跳出到下一个审批级别  --2019-02-21 蔡国新
                }
            }
        }

        // 如果遍历完审批流程仍没有有效审批
        if (!bValidApprove) {
            //需求 ADM17010011 提交审批时，如果已设置审批流程没有满足条件的审批点时，处理方式同没有设置审批条件的处理方式一样
            if (!bSelfApprove) {
                logger.info("单据:" + documentNo + "没有设置有效的审批点，默认提交人即审批人");
                selfApproveInfo.setApproveLevel(1);
                selfApproveInfo.setApproveName("申请人自审");
                selfApproveInfo.setApproverID(approveDocument.getUserId());
                selfApproveInfo.setApproverNo(approveDocument.getUserNo());
                selfApproveInfo.setApproverName(approveDocument.getUserName());
            }

            ApproveDocumentsPoints newpoint = new ApproveDocumentsPoints();
            newpoint.setOadpId(UUID.randomUUID().toString());
            newpoint.setDocumentId(approveDocument.getDocumentId());
            newpoint.setStatus((short) 30);
            newpoint.setApproveLevel(selfApproveInfo.getApproveLevel());
            newpoint.setApproveName(selfApproveInfo.getApproveName());
            newpoint.setApproverId(selfApproveInfo.getApproverID());
            newpoint.setApproverNo(selfApproveInfo.getApproverNo());
            newpoint.setApproverName(selfApproveInfo.getApproverName());
            newpoint.setApproveTime(submitTime);
            dao.save(newpoint);

            // 更新 approveDocument
            approveDocument.setStatus((short) 30);
            approveDocument.setApproveLevel(selfApproveInfo.getApproveLevel());
            approveDocument.setApproveName(selfApproveInfo.getApproveName());
            approveDocument.setApproverId(selfApproveInfo.getApproverID());
            approveDocument.setApproverNo(selfApproveInfo.getApproverNo());
            approveDocument.setApproverName(selfApproveInfo.getApproverName());
            dao.update(approveDocument);

            // 更新recordDetailDocument
            recordDetailDocument.setStatus((short) 30);
            recordDetailDocument.setSubmitTime(submitTime);
            recordDetailDocument.setApproverId(selfApproveInfo.getApproverID());
            recordDetailDocument.setApproverNo(selfApproveInfo.getApproverNo());
            recordDetailDocument.setApproverName(selfApproveInfo.getApproverName());
            dao.update(recordDetailDocument);

            return approveAgreeByPC(approveDocument, String.format("本人自审(特殊审批)(%s)", osString), submitTime);// 正常情况下，只能走最后一级审批

        } else {
            // 更新 approveDocument
            approveDocument.setStatus((short) 20);
            approveDocument.setApproveLevel(validApproveInfo.getApproveLevel());
            approveDocument.setApproveName(validApproveInfo.getApproveName());
            approveDocument.setApproverId(validApproveInfo.getApproverID());
            approveDocument.setApproverNo(validApproveInfo.getApproverNo());
            approveDocument.setApproverName(validApproveInfo.getApproverName());
            dao.update(approveDocument);

            // 更新recordDetailDocument
            recordDetailDocument.setStatus((short) 20);
            recordDetailDocument.setSubmitTime(submitTime);
            recordDetailDocument.setApproverId(validApproveInfo.getApproverID());
            recordDetailDocument.setApproverNo(validApproveInfo.getApproverNo());
            recordDetailDocument.setApproverName(validApproveInfo.getApproverName());
            dao.update(recordDetailDocument);

            ApproveResult approveResult = new ApproveResult(ApproveResultCode.APPROVE_SUCCESS);
            approveResult.setRtnCode(ResultCode.RET_SUCC.getCode());
            approveResult.setErrMsg(ResultCode.RET_SUCC.getMessage());
            approveResult.setStatusNo((short) 20);
            approveResult.setStatusName("已提交");
            approveResult.setApprovedTime(submitTime);
            approveResult.setNextUserID(validApproveInfo.getApproverID());
            approveResult.setNextUserName(validApproveInfo.getApproverName());
            approveResult.setNextUserNo(validApproveInfo.getApproverNo());
            return approveResult;
        }
    }


    /**
     * 通过conditionSql查找条件值
     *
     * @param documentNo
     * @return
     */
    public String getConditionSql(String documentNo) {
        return null;
    }


    private Object getConditionFieldValue(String conditionField, String documentNo) {
        Object objectValue = null;
        String conditionSql = getConditionSql(documentNo);
        if (StringUtils.isBlank(conditionSql)) { //兼容原处理方式，按实体类通过反射获得fieldValue
            StringBuilder sb = new StringBuilder("get");
            try {
                String[] temp = conditionField.split("_");
                for (String t : temp) {
                    sb.append(Character.toUpperCase(t.charAt(0))).append(t.substring(1));
                }
                // 使用反射机制调用获取字段值的方法
                @SuppressWarnings("rawtypes") ApproveDocuments model = getDocumentDetail(documentNo);
                Method m = model.getClass().getMethod(sb.toString());
                objectValue = m.invoke(model);
            } catch (Exception e) {
                logger.error("审批点条件设置有误,方法" + sb.toString() + "调用失败", e);
                throw new ServiceException("提交失败:审批点条件设置有误");
            }
        } else {
            //新增的方式，直接通过sql查询出结果集
            List<Object> resultList = dao.listInSql(conditionSql, null).getData();
            if (resultList == null || resultList.size() == 0) {
                logger.error("conditionSql:" + conditionSql + "未查到数据");
                throw new ServiceException("提交失败:" + documentNo + "未查到数据");
            }
            Map<String, Object> resultMap = (Map<String, Object>) resultList.get(0);
            objectValue = resultMap.get(conditionField);
        }

        return objectValue;
    }

    /**
     * 检查审批点的所有审批条件
     *
     * @param point
     * @param documentNo
     * @param moduleId
     * @return true 全部审批条件都通过
     */
    private boolean checkPonitConditions(ApproveLevelsPoints point, String documentNo, String moduleId) {
        // 是否满足条件
        boolean condionIsValid = true;
        // 如果无条件，则即为满足条件；否则判断是否满足条件，只要有一个条件不满足，就为不满足条件
        Set<ApproveLevelsPointsConditions> levelPointConditions = point.getLevelPointCondition();

        for (ApproveLevelsPointsConditions alpc : levelPointConditions) {
            // 循环遍历审批点下的审批条件，只有每个条件都符合才是下级审批点
            String conditionValue = alpc.getConditionValue();
            String conditionItem = alpc.getConditionItem();
            boolean except = Tools.toBoolean(alpc.getConditionExcept());// 除外
            // 如果设置了审批条件但是审批条件的值没有设定的话则返回错误提示信息
            if (StringUtils.isEmpty(alpc.getConditionValue()) && StringUtils.isEmpty(alpc.getConditionItem())) {
                logger.error(String.format("审批点条件没有设置条件值(%s)", alpc.getBalpcId()));
                throw new ServiceException("提交失败:审批点条件没有设置条件值");
            }
            ApproveConditions approveCondition = approveConditionDao.getApproveCondition(moduleId, alpc.getConditionField());

            String conditionField = approveCondition.getConditionField();
            String conditionType = approveCondition.getConditionType();// 条件类型

            Object objectValue = getConditionFieldValue(conditionField, documentNo);
            if (ApproveConditionType.DATETIME.equals(conditionType)) {
                // 时间类型
                Date beforeDate = null;
                Date afterDate = null;

                if (StringUtils.isNotBlank(conditionItem)) {
                    try {
                        afterDate = sdf.parse(conditionItem.substring(0, 10));
                    } catch (Exception e) {
                        throw new ServiceException("提交失败:审批点条件设置有误：" + conditionItem + "转换成日期类型出错");
                    }
                }

                if (StringUtils.isNotBlank(conditionValue)) {
                    try {
                        beforeDate = sdf.parse(conditionValue.substring(0, 10));
                    } catch (Exception e) {
                        throw new ServiceException("提交失败:审批点条件设置有误：" + conditionValue + "转换成日期类型出错");
                    }
                }

                try {
                    Timestamp targetTime = (Timestamp) objectValue;
                    // 按天比较
                    if ((afterDate != null && targetTime.getTime() >= DateUtils.addDays(afterDate, 1).getTime()) || (beforeDate != null && targetTime.getTime() < beforeDate.getTime())) {
                        condionIsValid = false;
                        break;
                    }
                } catch (Exception e) {
                    throw new ServiceException("提交失败:审批点条件设置有误");
                }

            } else if (Constant.ApproveConditionType.DECIMAL.equals(conditionType)) {
                // 条件值是十进制数
                Double min = null;
                Double max = null;
                if (StringUtils.isNotBlank(conditionValue)) {
                    try {
                        min = Double.parseDouble(conditionValue);
                    } catch (Exception e) {
                        throw new ServiceException("提交失败:审批点条件设置有误," + conditionValue + "不是数字");
                    }

                }

                if (StringUtils.isNotBlank(conditionItem)) {
                    try {
                        max = Double.parseDouble(conditionItem);
                    } catch (Exception e) {
                        throw new ServiceException("提交失败:审批点条件设置有误," + conditionItem + "不是数字");
                    }
                }
                // 比较
                try {
                    double mAmount = (Double) objectValue;
                    if (min != null && mAmount - min <= -0.005D || max != null && mAmount - max >= 0.005D) {
                        condionIsValid = false;
                        break;
                    }

                } catch (Exception ex) {
                    // 审批点条件无效的错误
                    throw new ServiceException("提交失败:审批点条件设置有误");
                }


            } else if (Constant.ApproveConditionType.SELECTION.equals(conditionType)) {
                if (conditionValue == null || conditionValue.length() < 1) {
                    logger.error("条件为空");
                    throw new ServiceException("提交失败:审批点条件设置有误");
                } else {
                    // 注意toString和String.valueOf的区别
                    String[] objValueStrs = String.valueOf(objectValue).split(",");
                    condionIsValid = false;
                    if (null != objValueStrs && objValueStrs.length > 0) {
                        for (String str : objValueStrs) {
                            if (conditionValue.contains(str)) {
                                condionIsValid = true;
                                break;
                            }
                        }
                    }

                    //解决bug -20180926
                    if (except) {
                        condionIsValid = !condionIsValid;
                    }

                    if (!condionIsValid) {
                        break;
                    }
                }
            } else if (ApproveConditionType.STRING.equals(conditionType)) {
                // 字符串类型
                if (conditionValue == null || conditionValue.length() < 1) {
                    logger.error("条件为空");
                    throw new ServiceException("提交失败:审批点条件设置有误");
                }
                if (except) {
                    if (conditionValue.equals(String.valueOf(objectValue))) {
                        condionIsValid = false;
                        break;
                    }
                } else {
                    if (!conditionValue.equals(String.valueOf(objectValue))) {
                        condionIsValid = false;
                        break;
                    }
                }
            } else {
                logger.error("不支持此条件类型:" + conditionType);
                throw new ServiceException("提交失败:服务端还未加入审批点的审批条件值类型的支持");
            }
        }
        return condionIsValid;
    }

    /**
     * 最后一句审批处理--子类需要重写此方法
     *
     * @param approveDocument
     * @param comment
     * @return
     */
    @SuppressWarnings("rawtypes")
    public ApproveResult onLastApproveLevel(ApproveDocuments approveDocument, String comment) {
        ApproveResult approveResult = new ApproveResult(ApproveResultCode.APPROVE_SUCCESS);
        approveResult.setErrMsg(null);
        approveResult.setStatusNo(Constant.DocumentStatus.AGREED);
        approveResult.setStatusName("已同意");
        ApproveDocuments detailDocument = getDocumentDetail(approveDocument.getDocumentNo());
        // 回填的数据必须一致
        approveResult.setApprovedTime(detailDocument.getApproveTime());

        // 最后一级审批，也将审批人填入返回结果中 --caigx 20160064
        approveResult.setNextUserID(approveDocument.getApproverId());
        approveResult.setNextUserName(approveDocument.getApproverName());
        approveResult.setNextUserNo(approveDocument.getApproverNo());

        return approveResult;
    }


    public List<ApproveDocumentsPoints> getDocumentHistory(String documentId) {
        List<ApproveDocumentsPoints> list = documentPointDao.getDocumentHistory(documentId);
        int i;
        Timestamp lastApproveTime = null;
        boolean flag = true;
        for (i = 0; i < list.size(); i++) {
            ApproveDocumentsPoints point = list.get(i);
            Timestamp approveTime = point.getApproveTime();

            if (null != approveTime) {
                lastApproveTime = approveTime;
                continue;
            } else {
                if (flag) {
                    approveTime = new Timestamp(System.currentTimeMillis());
                    point.setHoldingTime(TimestampUitls.getHoldingTime(lastApproveTime, approveTime));
                    flag = false;
                }
            }
            lastApproveTime = approveTime;
            if (isDeadPoint(point)) {
                point.setDeadForAccessCtrl(true);
                break;
            }
        }
        return list;
    }


    /**
     * 在每一次点审批同意时，检查数据的合法性
     *
     * @param approveDocument
     * @param approveStatus   审批中或者最后一级审批
     * @return
     */
    @SuppressWarnings("rawtypes")
    public abstract ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus);

    public ApproveResultCode checkData(ApproveDocuments approveDocument, ApproveStatus approveStatus, Boolean useException) {
        return ApproveResultCode.APPROVE_DATA_CHECKED_PASS;
    }

    ;

    /**
     * 在审批之前，检验数据是否已更改
     *
     * @param modifyTime 单据最后更改时间
     * @return
     */
    public boolean checkDataChanged(String modifyTime, ApproveDocuments approveDocument) {
        Timestamp lastModifyTime = approveDocument.getModifyTime();
        if (lastModifyTime == null) {
            return false;
        }
        if (null != modifyTime && !"".equals(modifyTime)) {
            Timestamp timestamp = Timestamp.valueOf(modifyTime);
            return compareTime(timestamp, lastModifyTime);
        }
        return true;
    }

    ;

    /**
     * 比较表单的时间戳是否一致 ，时间误差在2s内认为一致 返回true 否则返回false
     *
     * @param Timestamp1
     * @param Timestamp2
     * @return true：时间相同
     */
    public boolean compareTime(Timestamp Timestamp1, Timestamp Timestamp2) {
        // 比较规则2s内认为是一致的
        if (Math.abs(Timestamp1.getTime() - Timestamp2.getTime()) < 2000) {
            logger.info(String.format("客户端发来的时间:%s,服务端查询的时间:%s,版本一致！", Timestamp1, Timestamp2));
            return false;
        } else {
            // 数据版本不一致
            logger.info(String.format("客户端发来的时间:%s,服务端查询的时间:%s,版本不一致！", Timestamp1, Timestamp2));
            return true;
        }
    }

    /**
     * 新的审批不同意
     *
     * @param approveDocument
     * @param comment
     * @return
     */
    ApproveResult approveDisagreeByPC(ApproveDocuments approveDocument, String comment) {
        List<ApproveDocumentsPoints> approvingPoints = documentPointDao.getApproveDocumentsPoints(approveDocument.getDocumentId());// 得到“审批中”的审批点
        if (approvingPoints == null || approvingPoints.size() == 0) {
            throw new ServiceException("审批失败:找不到单据的待审审批点");
        }
        ApproveResult approveResult = afterApproveDisagree(approveDocument, approvingPoints, new Timestamp(new Date().getTime()), comment);
        return approveResult;
    }

    /**
     * 审批不同意后处理
     *
     * @param approveDocument
     * @param approvingPoints
     * @param approveTimestamp
     * @param comment
     * @return
     */
    private ApproveResult afterApproveDisagree(ApproveDocuments approveDocument, List<ApproveDocumentsPoints> approvingPoints, Timestamp approveTimestamp, String comment) {
        // 修改当前审批点信息
        ApproveDocumentsPoints currentPoint = approvingPoints.get(0);
        currentPoint.setStatus((short) 5);
        currentPoint.setApproveTime(approveTimestamp);
        currentPoint.setRemark(comment);
        SysUsers user = HttpSessionStore.getSessionUser();
        currentPoint.setApproverId(user.getUserId());
        currentPoint.setApproverNo(user.getUserNo());
        currentPoint.setApproverName(user.getUserName());
        dao.update(currentPoint);
        // 后面 还没有审批的审批点全部删除
        if (approvingPoints.size() > 1) {
            for (int i = 1; i < approvingPoints.size(); i++) {
                ApproveDocumentsPoints afterPoint = approvingPoints.get(i);
                dao.delete(afterPoint);
            }
        }
        approveDocument.setStatus(Constant.DocumentStatus.DISAGREE);
        ApproveResult approveResult = new ApproveResult(ApproveResultCode.APPROVE_DISGREE);
        approveResult.setRtnCode(ResultCode.RET_SUCC.getCode());
        approveResult.setErrMsg(ResultCode.RET_SUCC.getMessage());
        approveResult.setStatusNo(Constant.DocumentStatus.DISAGREE);
        approveResult.setStatusName("不同意");
        approveResult.setApprovedTime(approveTimestamp);

        updateSubDocument(approveDocument, approveTimestamp);

        return approveResult;
    }

    /**
     * 新的审批同意 (重载)
     *
     * @param approveDocument
     * @param comment
     * @return
     */
    @SuppressWarnings("rawtypes")
    public ApproveResult approveAgreeByPC(ApproveDocuments approveDocument, String comment) {
        return approveAgreeByPC(approveDocument, comment, null);
    }

    /**
     * 新的审批同意
     *
     * @param approveDocument
     * @param comment
     * @return
     */
    @SuppressWarnings("rawtypes")
    public ApproveResult approveAgreeByPC(ApproveDocuments approveDocument, String comment, Timestamp approveTimestamp) {
        if (null == approveTimestamp) {
            approveTimestamp = new Timestamp(System.currentTimeMillis());
        }

        List<ApproveDocumentsPoints> approvingPoints = documentPointDao.getApproveDocumentsPoints(approveDocument.getDocumentId());// 得到“审批中”的审批点
        if (approvingPoints == null || approvingPoints.size() == 0) {
            throw new ServiceException("审批失败:找不到单据的待审批点");
        }
        // 如果同意，且仅有一个审批单据审批点，即最后一个审批点，则审批同意；
        // 如果同意，且不止一个审批单据审批点，则继续审批中，并赋值为下一个审批点的审批信息；
        if (approvingPoints.size() == 1) {
            ApproveResultCode messageResult = checkData(approveDocument, ApproveStatus.LAST_APPROVE);
            if (messageResult == null) {
                // 子类没有重写checkData方法时，提示错误信息
                throw new ServiceException("审批失败:服务端该模块审批不支持检查数据的合法性");
            }
            switch (messageResult) {
                case APPROVE_DATA_CHECKED_PASS:
                    // 数据检查通过时，继续往下执行
                    ApproveResult approveResult = afterApproveAgree(approveDocument, approvingPoints, approveTimestamp, comment, ApproveStatus.LAST_APPROVE);
                    return onLastApproveLevel(approveDocument, comment);
                default:
                    // 数据检查不同过时，返回数据检查不通过的原因
                    return new ApproveResult(messageResult);
            }
        } else {
            // 下一级不是特殊审批，完成查找，修改单据，添加审批历史
            // 检查数据
            ApproveResultCode messageResult = checkData(approveDocument, ApproveStatus.APPROVING);
            if (messageResult == null) {
                // 子类没有重写checkData方法时，提示错误信息
                throw new ServiceException("审批失败:服务端该模块审批不支持检查数据的合法性");
            }
            switch (messageResult) {
                case APPROVE_DATA_CHECKED_PASS:
                    // 数据检查通过时，跳出switch，继续往下执行
                    break;
                default:
                    // 数据检查不同过时，返回数据检查不通过的原因
                    return new ApproveResult(messageResult);
            }
            ApproveResult approveResult = afterApproveAgree(approveDocument, approvingPoints, approveTimestamp, comment, ApproveStatus.APPROVING);
            return approveResult;
        }
    }

    /**
     * 审批同意后处理
     *
     * @param approveDocument
     * @param approvingPoints
     * @return
     */
    @SuppressWarnings("rawtypes")
    private ApproveResult afterApproveAgree(ApproveDocuments approveDocument, List<ApproveDocumentsPoints> approvingPoints, Timestamp approveTimestamp, String comment, ApproveStatus approveStatus) {
        ApproveResult approveResult = null;
        if (approveStatus == ApproveStatus.APPROVING) {
            approveDocument.setStatus(Constant.DocumentStatus.APPROVEING);
            approveDocument.setApproveLevel(approvingPoints.get(1).getApproveLevel());
            approveDocument.setApproveName(approvingPoints.get(1).getApproveName());
            approveDocument.setApproverId(approvingPoints.get(1).getApproverId());
            approveDocument.setApproverNo(approvingPoints.get(1).getApproverNo());
            approveDocument.setApproverName(approvingPoints.get(1).getApproverName());

            // 返回结果
            approveResult = new ApproveResult(ApproveResultCode.APPROVE_SUCCESS);
            approveResult.setRtnCode(ResultCode.RET_SUCC.getCode());
            approveResult.setErrMsg(ResultCode.RET_SUCC.getMessage());
            approveResult.setStatusNo(Constant.DocumentStatus.APPROVEING);
            approveResult.setStatusName("审批中");
            approveResult.setApprovedTime(approveTimestamp);
            approveResult.setNextUserID(approvingPoints.get(1).getApproverId());
            approveResult.setNextUserName(approvingPoints.get(1).getApproverName());
            approveResult.setNextUserNo(approvingPoints.get(1).getApproverName());
        }

        if (approveStatus == ApproveStatus.LAST_APPROVE) {
            approveDocument.setStatus(Constant.DocumentStatus.AGREED);
        }

        updateSubDocument(approveDocument, approveTimestamp);

        SysUsers user = HttpSessionStore.getSessionUser();
        ApproveDocumentsPoints currentPoint = approvingPoints.get(0);// 修改当前审批点信息
        currentPoint.setStatus((short) 50);
        currentPoint.setApproveTime(approveTimestamp);
        // 审批同意后，将审批点的approverId，approverNo，approverName 设置为当前用户 20161206
        currentPoint.setApproverId(user.getUserId());
        currentPoint.setApproverNo(user.getUserNo());
        currentPoint.setApproverName(user.getUserName());
        currentPoint.setRemark(comment);

        return approveResult;
    }

    /**
     * update the table of document detail
     *
     * @param parentDocument 父单据，子单据的状态都是随父单据的状态
     */
    @SuppressWarnings("rawtypes")
    protected void updateSubDocument(ApproveDocuments parentDocument, Timestamp approveTimestamp) {
        ApproveDocuments subDocument = getDocumentDetail(parentDocument.getDocumentNo());
        subDocument.setStatus(parentDocument.getStatus());
        subDocument.setApproverId(parentDocument.getApproverId());
        subDocument.setApproverName(parentDocument.getApproverName());
        subDocument.setApproverNo(parentDocument.getApproverNo());
        subDocument.setApproveTime(approveTimestamp);
        dao.update(subDocument);
    }


    public void revokingRecord(String documentNo, String modifyTime) {
    }

    public Object getReturnDataWithRevoke(String documentNo) {
        return null;
    }

    public void forbiddingRecord(String documentNo, String modifyTime) {
    }

    public Object getReturnDataWithForbid(String documentNo) {
        return null;
    }

    /**
     * 校验数据版本
     *
     * @param modifyTime
     * @param oriModifyTime
     */
    protected void validateModifyTime(Timestamp modifyTime, Timestamp oriModifyTime) {
        if (modifyTime == null || oriModifyTime == null) {
            return;
        }
        boolean throwError = false;
        if (null != modifyTime && null != oriModifyTime) {
            if (Math.abs(modifyTime.getTime() - oriModifyTime.getTime()) > 500) {
                throwError = true;
            }
        } else if (null == modifyTime || null == oriModifyTime) {
            throwError = true;
        }
        if (throwError) {
            throw new ServiceException("数据已被他人修改，请刷新后再试");
        }
    }

    protected void validateModifyTime(String modifyTime, Timestamp oriModifyTime) {
        if (StringUtils.isEmpty(modifyTime) && oriModifyTime == null) {
            return;
        }
        Timestamp curModifyTime = null;
        if (StringUtils.isNotEmpty(modifyTime)) {
            try {
                curModifyTime = new Timestamp(TimestampUitls.formatDate(modifyTime).getTime());
            } catch (ParseException e) {
                throw new ServiceException(String.format("modifyTime(%s)格式错误", modifyTime));
            }
        }
        validateModifyTime(curModifyTime, oriModifyTime);
    }
}
