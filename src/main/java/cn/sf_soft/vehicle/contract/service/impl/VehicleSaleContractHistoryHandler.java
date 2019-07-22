package cn.sf_soft.vehicle.contract.service.impl;

import cn.sf_soft.common.BeanUtil;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.util.Constant;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.VehicleSaleContractDetail;
import cn.sf_soft.office.approval.model.VehicleSaleContractHistory;
import cn.sf_soft.office.approval.model.VehicleSaleContracts;
import cn.sf_soft.support.*;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.contract.model.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/6 11:15
 * @Description: 记录合同更改日志
 */
public class VehicleSaleContractHistoryHandler {

    private static final String NEW_SUFFIX = "New";
    private static final String ORI_SUFFIX = "Ori";
    private static final String CREATE = "新增";
    private static final String UPDATE = "修改";
    private static final String DELETE = "删除";
    private static final String SPLIT_CREATE = "拆分-新增";
    private static final String SPLIT_KEEP = "拆分-保留";
    private static final String INSURANCE = "保险信息";
    private static final String PRESENT = "精品信息";
    private static final String ITEM = "改装信息";
    private static final String CHARGE = "费用信息";
    private static final String INVOICE = "发票信息";

    private static Map<String, List<Field>> properties;
    public static final String JOIN_STRING = "→";
    private static Pattern pattern = Pattern.compile(JOIN_STRING);

    private VehicleSaleContractHistory history;
    private VehicleSaleContractHistoryGroups historyGroup;
    private EntityProxy<VehicleSaleContracts> vehicleSaleContractProxy;

    public VehicleSaleContractHistoryHandler() {
        init();
    }

    public static void init() {
        if (null == properties) {
            properties = new HashMap<String, List<Field>>();
            properties.put(VehicleSaleContracts.class.getSimpleName(), getProperties(VehicleSaleContracts.class));
            properties.put(VehicleSaleContractDetailGroups.class.getSimpleName(), getProperties(VehicleSaleContractDetailGroups.class));
            properties.put(VehicleInvoicesGroups.class.getSimpleName(), getProperties(VehicleInvoicesGroups.class));


        }
    }

    private static List<Field> getProperties(Class<?> clazz) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields = BeanUtil.getAllField(clazz);
        if (null != fields && fields.length > 0) {
            for (Field field : fields) {
                LogProperty logProperty = field.getAnnotation(LogProperty.class);
                if (null != logProperty) {
                    list.add(field);
                }
            }
        }
        return list;
    }

    private BaseDao getBaseDao() {
        return ApplicationUtil.getBean("baseDao");
    }

    /**
     * 记录销售合同更改记录
     *
     * @param entityProxy
     */
    public void logHistory(EntityProxy<VehicleSaleContracts> entityProxy) {
        if (entityProxy.getOperation() == Operation.CREATE) {
            return;
        }
        this.vehicleSaleContractProxy = entityProxy;
        VehicleSaleContracts entity = entityProxy.getEntity();
        VehicleSaleContracts oriEntity = entityProxy.getOriginalEntity();
        //只有合同的原始状态是已同意的才记录
        if(null != oriEntity.getStatus() && oriEntity.getStatus().shortValue() != Constant.DocumentStatus.AGREED){
            return;
        }
        String contractNo = oriEntity.getContractNo();
        this.history = getHistory(contractNo);
        boolean isExists = false;
        if (StringUtils.isNotEmpty(history.getHistoryId())) {
            isExists = true;
        } else {
            this.history.setHistoryId(Tools.newGuid());
        }
        if (null != entity.getStatus() && entity.getStatus().shortValue() == (short) 50) {
            this.history.setStatus((byte) 1);
        } else {
            this.history.setStatus((byte) 0);
        }
        List<Field> fields = properties.get(VehicleSaleContracts.class.getSimpleName());
        this.invoke(this.history, isExists, fields, entityProxy, true);
        this.getBaseDao().update(this.history);
        List<EntityProxy<VehicleSaleContractDetailGroups>> groups = entityProxy.getSlaves(VehicleSaleContractDetailGroups.class.getSimpleName());
        if (null != groups) {
            for (EntityProxy group : groups) {
                try {
                    this.logGroup(group);
                } catch (ServiceException ex) {
                    throw ex;
                } catch (Exception ex) {
                    throw new ServiceException(String.format("记录车辆明细分组更改记录出错（group:%s）", group), ex);
                }
            }
        }
        this.removeNoJoinStringLog();
    }

    /**
     * 根据合同号获取合同更改历史对象，如果不存在则创建
     *
     * @param contractNo 销售合同号
     * @return
     */
    private VehicleSaleContractHistory getHistory(String contractNo) {
        /*BaseDao baseDao = getBaseDao();
        List<VehicleSaleContractHistory> histories = (List<VehicleSaleContractHistory>) baseDao.findByHql("from VehicleSaleContractHistory where contractNo=?", contractNo);
        if (null == histories || histories.isEmpty()) {
            history = new VehicleSaleContractHistory();
            history.setSort(1);
            Timestamp now = TimestampUitls.getTime();
            history.setVaryTime(now);
            history.setContractNo(contractNo);
            SysUsers user = HttpSessionStore.getSessionUser();
            history.setCreator(user.getUserFullName());
            String nowOfString = TimestampUitls.getTimeOfString();
            history.setCreateTime(nowOfString);
            return history;
        } else {
            history = histories.get(0);
            history.setVaryTime(TimestampUitls.getTime());
            return history;
        }*/
        //主表每次都新增
        history = new VehicleSaleContractHistory();
        SQLQuery query = this.getBaseDao().getCurrentSession().createSQLQuery("select count(1) from vehicle_sale_contract_history where contract_no=?");
        query.setParameter(0, contractNo);
        List<Integer> list = query.list();
        history.setSort(list.get(0) + 1);
        Timestamp now = TimestampUitls.getTime();
        history.setVaryTime(now);
        history.setContractNo(contractNo);
        SysUsers user = HttpSessionStore.getSessionUser();
        history.setCreator(user.getUserFullName());
        String nowOfString = TimestampUitls.getTimeOfString();
        history.setCreateTime(nowOfString);
        history.setStatus((byte) 0);
        return history;
    }

    /**
     * 删除没有连接符且没有item的记录
     */
    private void removeNoJoinStringLog() {
        List<VehicleSaleContractHistoryGroups> groups = this.history.getGroups();
        if (null != groups && groups.isEmpty()) {
            for (int i = 0; i < groups.size(); i++) {
                VehicleSaleContractHistoryGroups group = groups.get(i);
                List<VehicleSaleContractHistoryItems> items = group.getItems();
                if (null == items || items.isEmpty()) {
                    if (!hasJoinString(group)) {
                        this.getBaseDao().delete(group);
                        groups.remove(group);
                        i--;
                    }
                }
            }
        }
        if (null == groups || groups.isEmpty()) {
            if (!hasJoinString(this.history)) {
                this.getBaseDao().delete(this.history);
            }
        }

    }


    /**
     * 通过传入指定的列与相关参数反射设置历史对象的属性值
     *
     * @param logEntity   历史对象
     * @param isExists    历史对象存在为true，否则为false(表示是新建)
     * @param fields      合同相关对象需要写入历史对象中的所有属性列表
     * @param entityProxy 合同相关对象
     * @param enableJoin  是否启用连接符
     */
    private void invoke(Object logEntity, boolean isExists, List<Field> fields, EntityProxy entityProxy, boolean enableJoin) {
        Operation operation = entityProxy.getOperation();
        Object entity = entityProxy.getEntity();
        Object oriEntity = entityProxy.getOriginalEntity();
        Field currentField = null;
        String propertyName, newPropertyName, oriPropertyName;
        try {
            for (Field field : fields) {
                currentField = field;
                LogProperty logProperty = field.getAnnotation(LogProperty.class);
                //设置属性名
                if (StringUtils.isEmpty(logProperty.name())) {
                    propertyName = field.getName();
                } else {
                    propertyName = logProperty.name();
                }
                newPropertyName = propertyName + NEW_SUFFIX;
                oriPropertyName = propertyName + ORI_SUFFIX;
                if (operation == Operation.DELETE || operation == Operation.CREATE) {
                    if (enableJoin && logProperty.join()) {
                        Object val = getValueForLog(entity, field.getName(), logProperty);
                        BeanUtil.setValueOfProperty(logEntity, oriPropertyName, val);
                        BeanUtil.setValueOfProperty(logEntity, newPropertyName, val);
                        Object val1 = this.convertByLogProperty(logProperty.nullDefault(), logProperty);
                        if (equals(val, val1, logProperty)) {
                            BeanUtil.setValueOfProperty(logEntity, propertyName, this.format(val1, logProperty.type()));
                        } else {
                            if (operation == Operation.CREATE) {
                                BeanUtil.setValueOfProperty(logEntity, propertyName, this.getJoinValue(val1, val, logProperty.type()));
                            } else if (operation == Operation.DELETE) {
                                BeanUtil.setValueOfProperty(logEntity, propertyName, this.getJoinValue(val, val1, logProperty.type()));
                            }
                        }
                    } else {
                        Object val = getValueForLog(entity, field.getName(), logProperty);
                        BeanUtil.setValueOfProperty(logEntity, oriPropertyName, val);
                        BeanUtil.setValueOfProperty(logEntity, newPropertyName, val);
                        BeanUtil.setValueOfProperty(logEntity, propertyName, this.format(val, logProperty.type()));
                    }
                } else {
                    /*//属性的最新值
                    Object value = BeanUtil.getValueOfProperty(entity, field.getName());
                    //将当前属性经过属性处理器处理
                    Object curValue = value;
                    if(null != propertyHandler){
                        curValue = propertyHandler.convert(value, field, logProperty);
                    }*/
                    //历史记录中的原始值
                    Object hisOriValue = BeanUtil.getValueOfProperty(logEntity, oriPropertyName);
                    //当前最新值,经过转换后数据类型与历史记录中的属性类型一致
                    Object currentValue = this.getValueForLog(entity, field.getName(), logProperty);
                    //将当前最新值记录到newPropertyName中
                    BeanUtil.setValueOfProperty(logEntity, newPropertyName, currentValue);
                    if (!isExists && null != oriEntity) {
                        Object oriValue = this.getValueForLog(oriEntity, field.getName(), logProperty);
                        BeanUtil.setValueOfProperty(logEntity, oriPropertyName, oriValue);
                        if (enableJoin && !equals(oriValue, currentValue, logProperty)) {
                            BeanUtil.setValueOfProperty(logEntity, propertyName, getJoinValue(oriValue, currentValue, logProperty.type()));
                        } else {
                            BeanUtil.setValueOfProperty(logEntity, propertyName, this.format(currentValue, logProperty.type()));
                        }
                    } else {
                        if (enableJoin && !equals(hisOriValue, currentValue, logProperty)) {
                            BeanUtil.setValueOfProperty(logEntity, propertyName, getJoinValue(hisOriValue, currentValue, logProperty.type()));
                        } else {
                            BeanUtil.setValueOfProperty(logEntity, propertyName, this.format(currentValue, logProperty.type()));
                        }
                    }

                }
            }
        } catch (Exception ex) {
            throw new ServiceException(String.format("记录合同更改日志%s.%s时出错", logEntity.getClass().getSimpleName(), currentField.getName()), ex);
        }
    }


    private VehicleSaleContractHistoryGroups getHistoryDetailGroup(EntityProxy<VehicleSaleContractDetailGroups> detailGroup) {


        List<VehicleSaleContractHistoryGroups> hisGroups = this.history.getGroups();
        if (null == hisGroups) {
            hisGroups = new ArrayList<>();
            List<VehicleSaleContractHistoryGroups> sHisGroups = (List<VehicleSaleContractHistoryGroups>) getBaseDao()
                    .findByHql("from VehicleSaleContractHistoryGroups where historyId=?", this.history.getHistoryId());
            if (null != sHisGroups && !sHisGroups.isEmpty()) {
                for (VehicleSaleContractHistoryGroups group : sHisGroups) {
                    hisGroups.add(group);
                }
            }
            history.setGroups(hisGroups);
        }

        String sGroupId = null;
        String sGroupNo = null;
        if (detailGroup.getOperation() != Operation.CREATE) {
            VehicleSaleContractDetailGroups oriGroup = detailGroup.getOriginalEntity();
            sGroupId = oriGroup.getGroupId();
            for (VehicleSaleContractHistoryGroups group : hisGroups) {
                if (sGroupId.equals(group.getGroupId())) {
                    return group;
                }
            }
        }

        sGroupId = detailGroup.getEntity().getGroupId();
        sGroupNo = detailGroup.getEntity().getGroupNo();

        VehicleSaleContractHistoryGroups hisDetailGroup = new VehicleSaleContractHistoryGroups();
        hisDetailGroup.setHistoryId(this.history.getHistoryId());
        hisDetailGroup.setGroupId(sGroupId);
        hisDetailGroup.setGroupNo(sGroupNo);
        hisDetailGroup.setContractNo(this.history.getContractNo());
        hisDetailGroup.setCreateTime(TimestampUitls.format(this.history.getVaryTime(), "yyyy-MM-dd HH:mm:ss"));
        SysUsers user = HttpSessionStore.getSessionUser();
        hisDetailGroup.setCreator(user.getUserFullName());
        hisGroups.add(hisDetailGroup);
        return hisDetailGroup;

    }

    /**
     * 记录明细
     *
     * @param detailGroup
     */
    private void logGroup(EntityProxy<VehicleSaleContractDetailGroups> detailGroup) {
        Operation operation = detailGroup.getOperation();
        VehicleSaleContractDetailGroups group = detailGroup.getEntity();
        VehicleSaleContractDetailGroups oriGroup = detailGroup.getOriginalEntity();
        String curGroupId = group.getGroupId();
        String sGroupId = null;
        String sGroupNo = null;
        boolean isContainInsuranceCost = false;
        List<Field> fields = properties.get(VehicleSaleContractDetailGroups.class.getSimpleName());
        VehicleSaleContractHistoryGroups hisGroup = this.getHistoryDetailGroup(detailGroup);
        if (operation == Operation.DELETE) {
            isContainInsuranceCost = null == oriGroup.getIsContainInsuranceCost() ? false : oriGroup.getIsContainInsuranceCost();
            hisGroup.setVaryType(DELETE);
            hisGroup.setModifyTime(this.history.getVaryTime());
            if (StringUtils.isEmpty(hisGroup.getHistoryGroupId())) {
                hisGroup.setHistoryGroupId(Tools.newGuid());
            } else {
                this.invoke(hisGroup, true, fields, detailGroup, true);
                String vin = this.getVins(oriGroup.getGroupId(), true, false);
                hisGroup.setVinsOri(vin);
                hisGroup.setVinsNew(vin);
                hisGroup.setVins(vin);
            }
            sGroupId = oriGroup.getGroupId();
            sGroupNo = oriGroup.getGroupNo();
        } else if (operation == Operation.CREATE) {
            sGroupId = group.getGroupId();
            sGroupNo = group.getGroupNo();
            isContainInsuranceCost = group.getIsContainInsuranceCost();
            if (StringUtils.isEmpty(hisGroup.getHistoryGroupId())) {
                hisGroup.setHistoryGroupId(Tools.newGuid());
            }
            if (StringUtils.isNotEmpty(group.getGroupNoRef())) {
                hisGroup.setVaryType(SPLIT_CREATE);
            } else {
                hisGroup.setVaryType(CREATE);
            }
            this.invoke(hisGroup, false, fields, detailGroup, true);
            String vin = this.getVins(curGroupId, false, false);
            hisGroup.setVinsOri(vin);
            hisGroup.setVinsNew(vin);
            hisGroup.setVins(vin);
        } else if (operation == Operation.UPDATE || operation == Operation.NONE) {
            sGroupId = group.getGroupId();
            sGroupNo = group.getGroupNo();
            isContainInsuranceCost = group.getIsContainInsuranceCost();
            boolean isExists = true;
            if (StringUtils.isEmpty(hisGroup.getHistoryGroupId())) {
                hisGroup.setHistoryGroupId(Tools.newGuid());
                isExists = false;
            }
            String vin = this.getVins(curGroupId, false, false);
            if (isExists) {
                if (StringUtils.isNotEmpty(hisGroup.getVaryType()) && hisGroup.getVaryType().indexOf(CREATE) >= 0) {
                    hisGroup.setVehicleVnoOri(group.getVehicleVno());
                    hisGroup.setVehicleColorOri(group.getVehicleColor());
                    hisGroup.setQuantityOri(group.getVehicleQuantity());
                    hisGroup.setIncomeOri(null == group.getIncomeTot() ? BigDecimal.ZERO : group.getIncomeTot());
                    hisGroup.setCostOri(null == group.getCostTot() ? BigDecimal.ZERO : group.getCostTot());
                    hisGroup.setProfitOri(null == group.getProfitTot() ? BigDecimal.ZERO : group.getProfitTot());
                    hisGroup.setVehicleOwnerNameOri(group.getVehicleOwnerName());
                    hisGroup.setVehiclePriceOri(null == group.getVehiclePrice() ? BigDecimal.ZERO : group.getVehiclePrice());
                    hisGroup.setVehiclePriceTotalOri(null == group.getVehiclePriceTotal() ? BigDecimal.ZERO : group.getVehiclePriceTotal());
                    hisGroup.setIsContainInsuranceCostOri(null == group.getIsContainInsuranceCost() ? false : group.getIsContainInsuranceCost());
                    hisGroup.setDiscountAmountOri(null == group.getDiscountAmount() ? BigDecimal.ZERO : group.getDiscountAmount());
                    hisGroup.setLargessAmountOri(null == group.getLargessAmount() ? BigDecimal.ZERO : group.getLargessAmount());
                    hisGroup.setCustomerNameDetailOri(group.getCustomerNameProfit());
                    hisGroup.setVehicleProfitOri(null == group.getVehicleProfit() ? BigDecimal.ZERO : group.getVehicleProfit());
                    hisGroup.setDepositOri(null == group.getDeposit() ? BigDecimal.ZERO : group.getDeposit());
                    hisGroup.setSubjectMatter(group.getSubjectMatter());
                    hisGroup.setVehicleCardModelOri(group.getVehicleVnoNew());
                    hisGroup.setTransportRoutesOri(group.getTransportRoutes());
                    hisGroup.setVinsOri(vin);
                }
                this.invoke(hisGroup, isExists, fields, detailGroup, true);
            } else {
                String refGroupNo = group.getGroupNoRef();
                hisGroup.setVaryType(UPDATE);
                if (StringUtils.isEmpty(refGroupNo)) {
                    EntityProxy<VehicleSaleContractDetailGroups> refGroup = getRefGroup(detailGroup);
                    if (null != refGroup) {
                        if (Operation.CREATE == refGroup.getOperation() && StringUtils.isNotEmpty(refGroup.getEntity().getGroupNoRef())) {
                            hisGroup.setVaryType(SPLIT_KEEP);
                        }
                    }
                }
                String oriVin = this.getVins(curGroupId, false, true);
                hisGroup.setVinsOri(oriVin);
                hisGroup.setVinsNew(vin);
                this.invoke(hisGroup, isExists, fields, detailGroup, true);
            }
            if(operation == Operation.UPDATE){
                if(StringUtils.equals(hisGroup.getVinsOri(), hisGroup.getVinsNew())){
                    hisGroup.setVins(hisGroup.getVinsNew());
                }else{
                    hisGroup.setVins(getJoinValue(hisGroup.getVinsOri(), hisGroup.getVinsNew(), LogPropertyType.STRING));
                }
            }
        }
        if (StringUtils.isEmpty(hisGroup.getHistoryGroupId())) {
            hisGroup.setHistoryGroupId(Tools.newGuid());
        }

        this.historyGroup = hisGroup;
        //boolean isContainInsuranceCost = (null == oriGroup || null == oriGroup.getIsContainInsuranceCost()) ? false : oriGroup.getIsContainInsuranceCost().booleanValue();
        int result = this.logItems(detailGroup, hisGroup.getHistoryGroupId(), sGroupId, sGroupNo, isContainInsuranceCost);
        if (result > 0) {
            this.getBaseDao().update(hisGroup);
        } else if (operation != Operation.NONE) {
            this.getBaseDao().update(hisGroup);
        }
    }


    /**
     * 获取vins
     *
     * @param groupId
     * @param isContainDelete
     * @param isOri
     * @return
     */
    private String getVins(String groupId, boolean isContainDelete, boolean isOri) {
        List<EntityProxy<?>> entityProxies = EntityProxyUtil.getDescendants(this.vehicleSaleContractProxy, VehicleSaleContractDetailGroups.class.getSimpleName() + "." + VehicleSaleContractDetail.class.getSimpleName());
        if (null != entityProxies && !entityProxies.isEmpty()) {
            List<String> list = new ArrayList<String>();
            for (EntityProxy<?> entityProxy : entityProxies) {
                Operation operation = entityProxy.getOperation();
                VehicleSaleContractDetail contractDetail = (VehicleSaleContractDetail)entityProxy.getEntity();
                VehicleSaleContractDetail oriContractDetail = (VehicleSaleContractDetail)entityProxy.getOriginalEntity();
                if (operation == Operation.DELETE) {
                    if (!isContainDelete) {
                        continue;
                    }
                    String oriGroupId = oriContractDetail.getGroupId();
                    String oriVehicleVin = oriContractDetail.getVehicleVin();
                    if (!StringUtils.equals(oriGroupId, groupId) || StringUtils.isEmpty(oriVehicleVin)) {
                        continue;
                    }
                    list.add(oriVehicleVin);
                } else {
                    String curGroupId = contractDetail.getGroupId();
                    String curVehicleVin = contractDetail.getVehicleVin();
                    if (!isOri) {
                        if (!StringUtils.equals(curGroupId, groupId) || StringUtils.isEmpty(curVehicleVin)) {
                            continue;
                        }
                        list.add(curVehicleVin);
                    } else {
                        if (!StringUtils.equals(curGroupId, groupId)) {
                            continue;
                        }
                        if (operation == Operation.CREATE) {
                            continue;
                        }else if(StringUtils.isEmpty(oriContractDetail.getVehicleVin())){
                            continue;
                        }
                        list.add(oriContractDetail.getVehicleVin());
                    }
                }
            }
            return StringUtils.join(list, ",");
        }
        return null;
    }

    private EntityProxy<VehicleSaleContractDetailGroups> getRefGroup(EntityProxy<VehicleSaleContractDetailGroups> detailGroup) {
        EntityProxy<?> master = detailGroup.getMaster();
        if (null != master) {
            List<EntityProxy<VehicleSaleContractDetailGroups>> slaves = master.getSlaves(VehicleSaleContractDetailGroups.class.getSimpleName());
            String groupNo = detailGroup.getEntity().getGroupNo();
            if (null != slaves && !slaves.isEmpty()) {
                for (EntityProxy<VehicleSaleContractDetailGroups> entityProxy : slaves) {
                    VehicleSaleContractDetailGroups entity = entityProxy.getEntity();
                    String refGroupNo = entity.getGroupNoRef();
                    if (StringUtils.isNotEmpty(refGroupNo) && refGroupNo.equals(groupNo)) {
                        return entityProxy;
                    }
                }
            }
        }
        return null;
    }


    private int logItems(EntityProxy<VehicleSaleContractDetailGroups> detailGroupsEntityProxy,
                         String sHistoryGroupId, String sGroupId, String sGroupNo, boolean isContainInsuranceCost) {
        int result = 0;
        try {
            List<EntityProxy<VehicleSaleContractInsuranceGroups>> insuranceGroupEntityProxies = detailGroupsEntityProxy.getSlaves(VehicleSaleContractInsuranceGroups.class.getSimpleName());
            if (null != insuranceGroupEntityProxies && !insuranceGroupEntityProxies.isEmpty()) {
                for (EntityProxy entityProxy : insuranceGroupEntityProxies) {
                    if (this.logItem(entityProxy, sHistoryGroupId, sGroupId, sGroupNo, INSURANCE,
                            "categoryName", "insuranceGroupId", "categoryIncome", "costPf", null, isContainInsuranceCost))
                        result++;
                }
            }
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(String.format("记录保险更改记录出错（groupId:%s；groupNo:%s）", sGroupId, sGroupNo), ex);
        }

        try {
            List<EntityProxy<VehicleSaleContractPresentGroups>> presentGroupEntityProxies = detailGroupsEntityProxy.getSlaves(VehicleSaleContractPresentGroups.class.getSimpleName());
            if (null != presentGroupEntityProxies && !presentGroupEntityProxies.isEmpty()) {
                for (EntityProxy entityProxy : presentGroupEntityProxies) {
                    if (this.logItem(entityProxy, sHistoryGroupId, sGroupId, sGroupNo, PRESENT,
                            "partName", "presentGroupId", "income", "costPf", "planQuantity", isContainInsuranceCost))
                        result++;
                }
            }
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(String.format("记录精品更改记录出错（groupId:%s；groupNo:%s）", sGroupId, sGroupNo));
        }

        try {
            List<EntityProxy<VehicleSaleContractItemGroups>> itemGroupEntityProxies = detailGroupsEntityProxy.getSlaves(VehicleSaleContractItemGroups.class.getSimpleName());
            if (null != itemGroupEntityProxies && !itemGroupEntityProxies.isEmpty()) {
                for (EntityProxy entityProxy : itemGroupEntityProxies) {
                    if (this.logItem(entityProxy, sHistoryGroupId, sGroupId, sGroupNo, ITEM,
                            "itemName", "itemGroupId", "income", "itemCost", null, isContainInsuranceCost))
                        result++;
                }
            }
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(String.format("记录车辆改装更改记录出错（groupId:%s；groupNo:%s）", sGroupId, sGroupNo), ex);
        }

        try {
            List<EntityProxy<VehicleSaleContractChargeGroups>> chargeGroupEntityProxies = detailGroupsEntityProxy.getSlaves(VehicleSaleContractChargeGroups.class.getSimpleName());
            if (null != chargeGroupEntityProxies && !chargeGroupEntityProxies.isEmpty()) {
                for (EntityProxy entityProxy : chargeGroupEntityProxies) {
                    if (this.logItem(entityProxy, sHistoryGroupId, sGroupId, sGroupNo, CHARGE,
                            "chargeName", "chargeGroupId", "income", "chargePf", null, isContainInsuranceCost))
                        result++;
                }
            }
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(String.format("记录费用更改记录出错（groupId:%s；groupNo:%s）", sGroupId, sGroupNo), ex);
        }
        try {
            List<EntityProxy<VehicleInvoicesGroups>> invoiceGroupEntityProxies = detailGroupsEntityProxy.getSlaves(VehicleInvoicesGroups.class.getSimpleName());
            if (null != invoiceGroupEntityProxies && !invoiceGroupEntityProxies.isEmpty()) {
                for (EntityProxy entityProxy : invoiceGroupEntityProxies) {
                    if (this.logInvoice(entityProxy, sHistoryGroupId, sGroupId, sGroupNo))
                        result++;
                }
            }
        } catch (ServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(String.format("记录发票更改记录出错（groupId:%s；groupNo:%s）", sGroupId, sGroupNo), ex);
        }
        return result;
    }


    /**
     * 记录发票
     *
     * @param entityProxy
     * @param sHistoryId
     * @param sGroupId
     * @param sGroupNo
     */
    private boolean logInvoice(EntityProxy<VehicleInvoicesGroups> entityProxy, String sHistoryId, String sGroupId, String sGroupNo) {
        List<Field> fields = properties.get(VehicleInvoicesGroups.class.getSimpleName());
        Operation operation = entityProxy.getOperation();
        String sItemType = INVOICE;
        String sItemId = null;
        String sItemName = null;
        VehicleSaleContractHistoryItems item = null;
        VehicleInvoicesGroups entity = entityProxy.getEntity();
        VehicleInvoicesGroups oriEntity = entityProxy.getOriginalEntity();
        if (operation == Operation.DELETE) {
            String gid = entityProxy.getOriginalEntity().getGroupId();
            if (!StringUtils.equals(gid, sGroupId)) {
                return false;
            }
            sItemId = entityProxy.getOriginalEntity().getInvoiceGroupId();
            sItemName = entityProxy.getOriginalEntity().getInvoiceType();
            item = this.getHistoryItem(sHistoryId, sGroupId, sGroupNo, sItemType, sItemId, sItemName);
            if (StringUtils.isNotEmpty(item.getSelfId())) {
                item.setVaryType(DELETE);
            } else {
                item.setSelfId(Tools.newGuid());
                String sRemark = String.format("发票种类:%s,金额：%s,开票对象：%s",
                        oriEntity.getInvoiceType(),
                        oriEntity.getInvoiceAmount(),
                        oriEntity.getObjectName());
                item.setRemark(sRemark);
                this.invoke(item, false, fields, entityProxy, false);
            }
        } else if (operation == Operation.CREATE || operation == Operation.UPDATE) {
            String gid = entityProxy.getEntity().getGroupId();
            if (!StringUtils.equals(gid, sGroupId)) {
                return false;
            }
            sItemId = entity.getInvoiceGroupId();
            sItemName = entity.getInvoiceType();
            item = this.getHistoryItem(sHistoryId, sGroupId, sGroupNo, sItemType, sItemId, sItemName);
            boolean isExists = true;
            if (StringUtils.isEmpty(item.getSelfId())) {
                item.setSelfId(Tools.newGuid());
                isExists = false;
            }

            String sRemark = String.format("发票种类:%s,金额：%s,开票对象：%s",
                    entity.getInvoiceType(),
                    entity.getInvoiceAmount(),
                    entity.getObjectName());
            item.setRemark(sRemark);

            if (operation == Operation.UPDATE) {
                if (isExists && CREATE.equals(item.getVaryType())) {
                    item.setInvoiceTypeOri(entity.getInvoiceType());
                    item.setInvoiceAmountOri(entity.getInvoiceAmount());
                    item.setInvoiceObjectOri(entity.getObjectName());
                }
                item.setVaryType(UPDATE);
                this.invoke(item, isExists, fields, entityProxy, true);
            } else if (operation == Operation.CREATE) {
                item.setVaryType(CREATE);
                this.invoke(item, isExists, fields, entityProxy, false);
            }

        } else {
            return false;
        }
        this.getBaseDao().update(item);
        return true;
    }


    private boolean logItem(EntityProxy<?> entityProxy, String sHistoryGroupId, String sGroupId, String sGroupNo, String sItemType, String itemNameField,
                            String itemIdField, String sIncomeField, String sCostField, String sQuantityField, boolean isContainInsuranceCost) throws Exception {
        Operation operation = entityProxy.getOperation();
        if (operation == Operation.NONE) {
            return false;
        }
        Object entity = entityProxy.getEntity();
        Object oriEntity = entityProxy.getOriginalEntity();
        VehicleSaleContractHistoryItems item = null;
        BigDecimal mIncome = BigDecimal.ZERO;
        BigDecimal mCost = BigDecimal.ZERO;
        BigDecimal mIncomeOri = null;
        BigDecimal mCostOri = null;
        BigDecimal mQuantity = BigDecimal.ZERO;
        BigDecimal mQuantityOri = null;
        boolean flag = true;
        Operation op = Operation.UPDATE;
        if (operation == Operation.DELETE) {
            op = Operation.DELETE;
            String groupId = BeanUtil.getValueOfProperty(oriEntity, "groupId").toString();
            if (!StringUtils.equals(groupId, sGroupId)) {
                return false;
            }
            String sItemId = BeanUtil.getValueOfProperty(oriEntity, itemIdField).toString();
            String itemName = BeanUtil.getValueOfProperty(oriEntity, itemNameField).toString();
            item = this.getHistoryItem(sHistoryGroupId, sGroupId, sGroupNo, sItemType, sItemId, itemName);
            if (StringUtils.isNotEmpty(item.getSelfId())) {
                flag = false;
            } else {
                item.setSelfId(Tools.newGuid());
                if (INSURANCE.equals(sItemType)) {
                    if (!isContainInsuranceCost) {
                        mIncome = getValueWithBigDecimal(oriEntity, sIncomeField, BigDecimal.ZERO);
                    }
                } else {
                    mIncome = getValueWithBigDecimal(oriEntity, sIncomeField, BigDecimal.ZERO);
                }
                BigDecimal oriCost = this.getValueWithBigDecimal(oriEntity, sCostField, BigDecimal.ZERO);
                if (PRESENT.equals(sItemType)) {
                    mCost = this.getQuantity(entityProxy, sQuantityField, true).multiply(oriCost);
                } else {
                    mCost = oriCost;
                }
            }
            item.setVaryType(DELETE);

            mIncomeOri = this.getValueWithBigDecimal(oriEntity, sIncomeField, BigDecimal.ZERO);
            mCostOri = this.getValueWithBigDecimal(oriEntity, sCostField, BigDecimal.ZERO);
            mQuantity = this.getQuantity(entityProxy, sQuantityField, false);
            mQuantityOri = this.getQuantity(entityProxy, sQuantityField, true);
            //this.setHistoryItemValue(item, Operation.DELETE, null, mIncome, null, mCost, mQuantity, mQuantityOri);

        } else if (operation == Operation.CREATE || operation == Operation.UPDATE) {
            String groupId = BeanUtil.getValueOfProperty(entity, "groupId").toString();
            if (!StringUtils.equals(groupId, sGroupId)) {
                return false;
            }
            String sItemId = BeanUtil.getValueOfProperty(entity, itemIdField).toString();
            String itemName = BeanUtil.getValueOfProperty(entity, itemNameField).toString();
            item = this.getHistoryItem(sHistoryGroupId, sGroupId, sGroupNo, sItemType, sItemId, itemName);
            if (StringUtils.isEmpty(item.getSelfId())) {
                item.setSelfId(Tools.newGuid());
            }

            if (INSURANCE.equals(sItemType)) {
                if (!isContainInsuranceCost) {
                    mIncome = getValueWithBigDecimal(entity, sIncomeField, BigDecimal.ZERO);
                }
            } else {
                mIncome = getValueWithBigDecimal(entity, sIncomeField, BigDecimal.ZERO);
            }
            BigDecimal curCost = this.getValueWithBigDecimal(entity, sCostField, BigDecimal.ZERO);
            if (PRESENT.equals(sItemType)) {
                mCost = this.getQuantity(entityProxy, sQuantityField, true).multiply(curCost);
            } else {
                mCost = curCost;
            }
            mQuantity = this.getQuantity(entityProxy, sQuantityField, false);
            mQuantityOri = this.getQuantity(entityProxy, sQuantityField, true);
            if (operation == Operation.UPDATE) {
                if (INSURANCE.equals(sItemType)) {
                    if (!isContainInsuranceCost) {
                        mIncomeOri = this.getValueWithBigDecimal(oriEntity, sIncomeField, BigDecimal.ZERO);
                    }
                } else {
                    mIncomeOri = this.getValueWithBigDecimal(oriEntity, sIncomeField, BigDecimal.ZERO);
                }
                mCostOri = this.getValueWithBigDecimal(oriEntity, sCostField, BigDecimal.ZERO);
                item.setVaryType(UPDATE);
                op = Operation.UPDATE;
                //this.setHistoryItemValue(item, Operation.UPDATE, mIncome, mIncomeOri, mCost, mCostOri, mQuantity, mQuantityOri);
            } else {
                op = Operation.CREATE;
                item.setVaryType(CREATE);
                //this.setHistoryItemValue(item, Operation.CREATE, mIncome, null, mCost, null, mQuantity, mQuantityOri);
            }
        }
        if (flag) {
            this.setHistoryItemValue(item, op, mIncome, mIncomeOri, mCost, mCostOri, mQuantity, mQuantityOri);
        }
        this.getBaseDao().update(item);
        return true;
    }

    private void setHistoryItemValue(VehicleSaleContractHistoryItems item, Operation operation,
                                     BigDecimal _sIncome, BigDecimal _sIncomeOri,
                                     BigDecimal _sCost, BigDecimal _sCostOri, BigDecimal _sQuantity, BigDecimal _sQuantityOri) {
        BigDecimal mIncomeOri = BigDecimal.ZERO;
        BigDecimal mCostOri = BigDecimal.ZERO;
        BigDecimal nQuantityOri = BigDecimal.ZERO;
        String _sVaryType = item.getVaryType();
        if (UPDATE.equals(_sVaryType) || operation == Operation.CREATE) {
            mIncomeOri = _sIncomeOri == null ? _sIncome : _sIncomeOri;
            mCostOri = _sCostOri == null ? _sCost : _sCostOri;
            nQuantityOri = _sQuantityOri == null ? _sQuantity : _sQuantityOri;
            item.setIncomeOri(mIncomeOri);
            item.setCostOri(mCostOri);
            item.setProfitOri(mIncomeOri.subtract(mCostOri));
            if (_sQuantity != null) {
                item.setQuantityOri(nQuantityOri.intValue());
            }
        } else {
            mIncomeOri = _sIncomeOri == null ? BigDecimal.ZERO : _sIncomeOri;
            mCostOri = _sCostOri == null ? BigDecimal.ZERO : _sCostOri;
            nQuantityOri = _sQuantityOri == null ? BigDecimal.ZERO : _sQuantityOri;
            item.setIncomeOri(mIncomeOri);
            item.setCostOri(mCostOri);
            item.setProfitOri(mIncomeOri.subtract(mCostOri));
            if (_sQuantity != null) {
                item.setQuantityOri(nQuantityOri.intValue());
            }
        }
        item.setIncomeNew(_sIncome);
        item.setCostNew(_sCost);
        BigDecimal profitNew = BigDecimal.ZERO;
        if(operation == Operation.DELETE){
            profitNew = BigDecimal.ZERO;
            nQuantityOri = BigDecimal.ZERO;
        }else{
            profitNew = _sIncome.subtract(_sCost);
        }
        item.setProfitNew(profitNew);

        if (_sQuantity != null) {
            item.setQuantityNew(_sQuantity.intValue());
        }
//        BigDecimal incomeOri = null == item.getIncomeOri() ? BigDecimal.ZERO : item.getIncomeOri();
        if (mIncomeOri.compareTo(_sIncome) != 0) {
            item.setIncome(this.getJoinValue(mIncomeOri, _sIncome, LogPropertyType.BIG_DECIMAL));
        } else if (operation == Operation.CREATE) {
            item.setIncome(this.format(_sIncome, LogPropertyType.BIG_DECIMAL));
        } else if (StringUtils.isEmpty(item.getIncome())) {
            item.setIncome(this.format(_sIncome, LogPropertyType.BIG_DECIMAL));
        }
//        BigDecimal costOri = null == item.getCostOri() ? BigDecimal.ZERO : item.getCostOri();
        if (mCostOri.compareTo(_sCost) != 0) {
            item.setCost(this.getJoinValue(mCostOri, _sCost, LogPropertyType.BIG_DECIMAL));
        } else if (operation == Operation.CREATE) {
            item.setCost(this.format(_sCost, LogPropertyType.BIG_DECIMAL));
        } else if (StringUtils.isEmpty(item.getCost())) {
            item.setCost(this.format(_sCost, LogPropertyType.BIG_DECIMAL));
        }
//        BigDecimal profitOri = null == item.getProfitOri() ? BigDecimal.ZERO : item.getProfitOri();
        if (nQuantityOri.compareTo(profitNew) != 0) {
            item.setProfit(this.getJoinValue(nQuantityOri, profitNew, LogPropertyType.BIG_DECIMAL));
        } else if (operation == Operation.CREATE) {
            item.setProfit(this.format(profitNew, LogPropertyType.BIG_DECIMAL));
        } else if (StringUtils.isEmpty(item.getProfit())) {
            item.setProfit(this.format(profitNew, LogPropertyType.BIG_DECIMAL));
        }

        if (_sQuantity != null) {
//            BigDecimal quantityOri = null == item.getQuantityOri() ? BigDecimal.ZERO : new BigDecimal(item.getQuantityOri());
            if (_sQuantityOri.compareTo(_sQuantity) != 0) {
                item.setQuantity(this.getJoinValue(_sQuantityOri, _sQuantity, LogPropertyType.BIG_DECIMAL));
            } else if (operation == Operation.CREATE) {
                item.setQuantity(_sQuantity.intValue() + "");
            } else if (StringUtils.isEmpty(item.getQuantity())) {
                item.setQuantity(_sQuantity.intValue() + "");
            }
        }
    }


    private BigDecimal getQuantity(EntityProxy<?> entityProxy, String _sField, boolean isOri) {
        if (StringUtils.isEmpty(_sField)) {
            return BigDecimal.ZERO;
        } else {
            Operation operation = entityProxy.getOperation();
            if (operation == Operation.DELETE || (operation == Operation.CREATE && isOri)) {
                return BigDecimal.ZERO;
            }
            if (isOri) {
                return this.getValueWithBigDecimal(entityProxy.getOriginalEntity(), _sField, BigDecimal.ZERO);
            } else {
                return this.getValueWithBigDecimal(entityProxy.getEntity(), _sField, BigDecimal.ZERO);
            }
        }
    }


    private VehicleSaleContractHistoryItems getHistoryItem(String sHistoryGroupId, String sGroupId, String sGroupNo, String sItemType, String sItemId, String itemName) {
        List<VehicleSaleContractHistoryItems> items = this.historyGroup.getItems();
        if (null == items) {
            items = new ArrayList<VehicleSaleContractHistoryItems>();
            List<VehicleSaleContractHistoryItems> list = (List<VehicleSaleContractHistoryItems>) this.getBaseDao()
                    .findByHql("from VehicleSaleContractHistoryItems where historyId=? and historyGroupId=?",
                            this.history.getHistoryId(), this.historyGroup.getHistoryGroupId());
            if (null != list && !list.isEmpty()) {
                for (VehicleSaleContractHistoryItems item : list) {
                    items.add(item);
                }
            }
            this.historyGroup.setItems(items);
        }
        VehicleSaleContractHistoryItems vehicleSaleContractHistoryItems = null;
        for (VehicleSaleContractHistoryItems item : items) {
            if (sGroupId.equals(item.getGroupId()) && sItemId.equals(item.getItemId())) {
                vehicleSaleContractHistoryItems = item;
                break;
            }
        }
        if (null == vehicleSaleContractHistoryItems) {
            vehicleSaleContractHistoryItems = new VehicleSaleContractHistoryItems();
            vehicleSaleContractHistoryItems.setHistoryGroupId(sHistoryGroupId);
            vehicleSaleContractHistoryItems.setHistoryId(this.history.getHistoryId());
            vehicleSaleContractHistoryItems.setGroupId(sGroupId);
            vehicleSaleContractHistoryItems.setGroupNo(sGroupNo);
            vehicleSaleContractHistoryItems.setContractNo(this.history.getContractNo());
            vehicleSaleContractHistoryItems.setItemType(sItemType);
            vehicleSaleContractHistoryItems.setItemId(sItemId);
            vehicleSaleContractHistoryItems.setItemName(itemName);
            SysUsers user = HttpSessionStore.getSessionUser();
            vehicleSaleContractHistoryItems.setCreator(user.getUserFullName());
            vehicleSaleContractHistoryItems.setCreateTime(TimestampUitls.format(this.history.getVaryTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        vehicleSaleContractHistoryItems.setModifyTime(this.history.getVaryTime());
        return vehicleSaleContractHistoryItems;
    }

    /**
     * 通过指定对象和对象属性及其属性描述，返回历史对象属性中能匹配的值
     *
     * @param object       合同及其合同相关对象
     * @param propertyName 属性名称
     * @param logProperty  属性描述
     * @return
     * @throws Exception
     * @see LogProperty
     */
    private Object getValueForLog(Object object, String propertyName, LogProperty logProperty) throws Exception {
        Object val = BeanUtil.getValueOfProperty(object, propertyName);
        if (null == val) {
            return this.convertByLogProperty(logProperty.nullDefault(), logProperty);
        }
        return this.convertByLogProperty(val, logProperty);
    }

    /**
     * 将传入的值对象根据属性描述转换成指定的类型
     *
     * @param value       值对象
     * @param logProperty 属性描述
     * @return
     * @throws Exception
     * @see LogProperty
     */
    private Object convertByLogProperty(Object value, LogProperty logProperty) throws Exception {
        try {
            if (logProperty.type() == LogPropertyType.STRING) {
                if (null != value) {
                    if (StringUtils.isNotEmpty(logProperty.dateFormat())) {
                        if (value instanceof Date) {
                            return new SimpleDateFormat(logProperty.dateFormat()).format(value);
                        }
                    }
                    return value.toString();
                } else {
                    return "";
                }
            } else if (logProperty.type() == LogPropertyType.INTEGER) {
                if (null != value && StringUtils.isNotEmpty(value.toString())) {
                    return Integer.valueOf(value.toString());
                } else {
                    return 0;
                }
            } else if (logProperty.type() == LogPropertyType.SHORT) {
                if (null != value && StringUtils.isNotEmpty(value.toString())) {
                    return Short.valueOf(value.toString());
                } else {
                    return (short) 0;
                }
            } else if (logProperty.type() == LogPropertyType.BIG_DECIMAL) {
                if (null != value && StringUtils.isNotEmpty(value.toString())) {
                    return new BigDecimal(value.toString());
                } else {
                    return BigDecimal.ZERO;
                }
            } else if (logProperty.type() == LogPropertyType.BOOLEAN) {
                if (null != value && StringUtils.isNotEmpty(value.toString())) {
                    return Boolean.valueOf(value.toString());
                } else {
                    return false;
                }
            } else {
                if (null != value) {
                    return value.toString();
                }
            }
            return null;
        } catch (Exception ex) {
            throw new Exception(String.format("根据类型(%s)转换值%s时出错", logProperty.type().toString(), value), ex);
        }
    }

    /**
     * 格式化参数并连接返回
     *
     * @param hisOriValue     值对象
     * @param currentValue    值对象
     * @param logPropertyType 属性描述
     * @return
     */
    private String getJoinValue(Object hisOriValue, Object currentValue, LogPropertyType logPropertyType) {
        return String.format("%s%s%s", format(hisOriValue, logPropertyType), JOIN_STRING, format(currentValue, logPropertyType));
    }

    /**
     * 根据属性描述格式化值对象并返回该对象的字符串
     *
     * @param object          值对象
     * @param logPropertyType 属性描述
     * @return
     */
    private String format(Object object, LogPropertyType logPropertyType) {
        if (logPropertyType == LogPropertyType.BIG_DECIMAL) {
            if (null == object) {
                return "0.00";
            } else if (object instanceof BigDecimal) {
                return ((BigDecimal) object).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            } else {
                return new BigDecimal(object.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
        } else {
            if (null == object) {
                return "";
            } else {
                return object.toString();
            }
        }
    }

    /**
     * 根据属性描述配置比较两个值对象是否相等
     *
     * @param object1     值对象
     * @param object2     值对象
     * @param logProperty 属性描述
     * @return
     */
    private static boolean equals(Object object1, Object object2, LogProperty logProperty) {
        if (null == object1 && null == object2) {
            return true;
        }
        if ((null == object1 && null != object2) || (null == object2 && null != object1)) {
            return false;
        }
        if (logProperty.type() == LogPropertyType.BIG_DECIMAL) {
            BigDecimal b1, b2 = null;
            if (object1 instanceof BigDecimal) {
                b1 = (BigDecimal) object1;
            } else {
                b1 = new BigDecimal(object1.toString());
            }
            if (object2 instanceof BigDecimal) {
                b2 = (BigDecimal) object2;
            } else {
                b2 = new BigDecimal(object2.toString());
            }
            if (b1.compareTo(b2) == 0) {
                return true;
            }
        } else if (logProperty.type() == LogPropertyType.BOOLEAN) {
            Boolean b1, b2 = false;
            if (object1 instanceof Boolean) {
                b1 = (Boolean) object1;
            } else {
                b1 = Boolean.valueOf(object1.toString());
            }
            if (object2 instanceof Boolean) {
                b2 = (Boolean) object2;
            } else {
                b2 = Boolean.valueOf(object2.toString());
            }
            if (b1.compareTo(b2) == 0) {
                return true;
            }
        } else if (logProperty.type() == LogPropertyType.SHORT) {
            Short b1, b2 = null;
            if (object1 instanceof Short) {
                b1 = (Short) object1;
            } else {
                b1 = Short.valueOf(object1.toString());
            }
            if (object2 instanceof Short) {
                b2 = (Short) object2;
            } else {
                b2 = Short.valueOf(object2.toString());
            }
            if (b1.compareTo(b2) == 0) {
                return true;
            }
        } else if (logProperty.type() == LogPropertyType.INTEGER) {
            Integer b1, b2 = null;
            if (object1 instanceof Integer) {
                b1 = (Integer) object1;
            } else {
                b1 = Integer.valueOf(object1.toString());
            }
            if (object2 instanceof Integer) {
                b2 = (Integer) object2;
            } else {
                b2 = Integer.valueOf(object2.toString());
            }
            if (b1.compareTo(b2) == 0) {
                return true;
            }
        } else if (StringUtils.equals(object1.toString(), object2.toString())) {
            return true;
        }
        return false;
    }

    /**
     * 判断指定对象中所有为java.lang.String的属性是否包含JOIN_STRING
     *
     * @param object 记录历史的对象
     * @return true：包含；false：不包含
     */
    private boolean hasJoinString(Object object) {
        List<Field> fields = this.getProperties(object.getClass());
        if (null != fields && !fields.isEmpty()) {
            for (Field field : fields) {
                if (field.getType().toString().equals(String.class.toString())) {
                    field.setAccessible(true);
                    try {
                        Object obj = field.get(object);
                        if (null != obj) {
                            if (pattern.matcher(obj.toString()).find()) {
                                return true;
                            }
                        }
                    } catch (Exception e) {
                        throw new ServiceException(String.format("获取%s.%s的值时出错", object.getClass().getSimpleName(), field.getName()));
                    }
                }
            }
        }
        return false;
    }

    private BigDecimal getValueWithBigDecimal(Object object, String propertyName, BigDecimal nullDefault) {
        try {
            Object value = BeanUtil.getValueOfProperty(object, propertyName);
            if (null != value) {
                if (value instanceof BigDecimal) {
                    return (BigDecimal) value;
                } else {
                    return new BigDecimal(value.toString());
                }
            } else {
                return nullDefault;
            }
        } catch (Exception e) {
            throw new ServiceException(String.format("%s.%s取值出错", object.getClass().getSimpleName(), propertyName));
        }
    }

}
