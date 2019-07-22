package cn.sf_soft.vehicle.customer.service.impl;

import cn.sf_soft.basedata.dao.BaseOthersDao;
import cn.sf_soft.basedata.dao.SysIdentityDao;
import cn.sf_soft.basedata.model.BaseMaintenanceWorkgroups;
import cn.sf_soft.basedata.service.impl.BaseVehicleNameServiceImpl;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.annotation.AccessPopedom;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.util.GetChineseFirstChar;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.TimestampUitls;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.file.service.FileService;
import cn.sf_soft.finance.voucher.model.AcctItem;
import cn.sf_soft.office.approval.model.VehicleArchives;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.dao.CustomerDao;
import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import cn.sf_soft.vehicle.customer.model.*;
import cn.sf_soft.vehicle.customer.service.CustomerService;
import org.apache.commons.lang3.StringUtils;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Resource
    BaseOthersDao baseOtherDao;
    @Resource
    CustomerDao customerDao;
    @Resource
    SysIdentityDao sysIdentityDao;

    @Autowired
    private SysOptionsDao sysOptionsDao;

    Map<String, String> updateFields;

    @Autowired
    private FileService fileService;

    @Autowired
    private BaseVehicleNameServiceImpl baseVehicleNameService;

    @Autowired
    private InterestedCustomersService interestedCustomersService;

    CustomerServiceImpl() {
        updateFields = new HashMap<String, String>();
        updateFields.put("sex", "性别");
        updateFields.put("object_name", "对象名称");
        updateFields.put("certificate_no", "证件号码");
        updateFields.put("address", "通讯地址");
        updateFields.put("mobile", "移动电话");
        updateFields.put("linkman", "联系人");
        updateFields.put("education", "学历");
        updateFields.put("occupation", "职业");
        updateFields.put("certificate_type", "证件类型");
        updateFields.put("phone", "固定电话");
        updateFields.put("email", "电子邮箱");
        updateFields.put("postalcode", "邮政编码");
        updateFields.put("object_kind", "对象业务种类");
        updateFields.put("service_linkman", "维修联系人");
        updateFields.put("service_phone", "维修联系电话");
        updateFields.put("customer_type", "客户类别");
        updateFields.put("birthday", "出生日期");
        updateFields.put("fax", "传真");
        updateFields.put("icq", "QQ号");
        updateFields.put("wechat", "微信号");
        updateFields.put("remark", "备注");
        updateFields.put("area", "区域");
        updateFields.put("city", "城市");
        updateFields.put("province", "省份");
        updateFields.put("insurance_linkman", "保险联系人");
        updateFields.put("workgroup_name", "维系人");
        updateFields.put("member_type", "会员类别");
        updateFields.put("profession", "行业");
        updateFields.put("object_property", "对象属性");
        updateFields.put("member_no", "会员编号");
        updateFields.put("member_begin_time", "会员领卡日期");
        updateFields.put("member_end_time", "会员过期日期");
        updateFields.put("name_pinyin", "名称拼音");
        updateFields.put("nation", "民族");
        updateFields.put("short_name", "对象简称");
        updateFields.put("object_nature", "对象性质");
        updateFields.put("object_type", "对象类型");
        updateFields.put("introducer_name", "介绍人名称");
        updateFields.put("insurance_phone", "保险联系电话");
        updateFields.put("position", "职位");
        updateFields.put("url", "主页");
        updateFields.put("residence_address", "居住地址");
        updateFields.put("company_address", "单位地址");
        updateFields.put("country", "国家");
        updateFields.put("account_bank", "开户银行");
        updateFields.put("account_no", "银行账号");
        updateFields.put("customer_kind", "客户类别");
        updateFields.put("legal_person", "法人代表");
        updateFields.put("business_license", "营业执照");
        updateFields.put("tax_register_no", "税务登记号");
        updateFields.put("income", "收入情况");
        updateFields.put("hobby", "兴趣爱好");
        updateFields.put("top_shares", "最大股东所占股份比例");
        updateFields.put("station_id", "站点标识");
        updateFields.put("customer_type", "客户类型");
        updateFields.put("workgroup_name", "维系人员");
        updateFields.put("is_lunar_birthday", "是否农历生日");
        updateFields.put("is_parent", "是否上级组");
        updateFields.put("company_name", "单位名称");
        updateFields.put("registered_capital", "注册资金");
        updateFields.put("fixed_assets", "固定资产");
        updateFields.put("main_business", "主营业务");
        updateFields.put("business_scope", "经营范围");
        updateFields.put("staff_number", "员工人数");
        updateFields.put("shareholder_number", "股东人数");
        updateFields.put("top_shareholder", "最大股东");
        updateFields.put("vehicle_linkman", "车辆联系人");
        updateFields.put("vehicle_phone", "车辆联系电话");
        updateFields.put("part_linkman", "配件联系人");
        updateFields.put("part_phone", "配件联系电话");
        updateFields.put("introducer_id", "介绍人标识");
        updateFields.put("part_price_system", "配件价格体系");
    }

    public List<CustomerVO> getCustomerByKeyword(SysUsers user, String keyword) {
        return customerDao.getCustomerByKeyword(user, keyword);
    }

    public List<CustomerVO> getCustomerByShortCut(SysUsers user, String shortCut) {
        return customerDao.getCustomerByShortcut(user, shortCut);
    }

    public List<PresellVisitorsVO> getInentionClueListByCustomerId(
            SysUsers user, String customerId) {
        return customerDao.getSaleClue(user, customerId);
    }

    /**
     * 判断客户是否存在
     *
     * @param customer
     * @return
     */
    public String isCustomerExist(InterestedCustomers customer) {
        String msg = null;
        List<InterestedCustomers> results = customerDao.getSimilarCustomer(customer);
        if (customer.getObjectNature() == 10) {
            // 单位客户
            if (results.size() > 0) {
                for (InterestedCustomers c : results) {
                    if (customer.getObjectName().equals(c.getObjectName())) {
                        msg = "当对象性质为[单位]时，客户名称不允许重复！";
                        break;
                    }
                    if (customer.getShortName().equals(c.getShortName())) {
                        msg = "当对象性质为[单位]时，客户简称不允许重复！";
                        break;
                    }
                    if (customer.getCertificateNo().equals(c.getCertificateNo())) {
                        if (!"东贸版".equals(sysOptionsDao.getOptionForString(InterestedCustomersService.DV_VERSION)) ||
                                (!InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER.equals(customer.getCertificateNo()) && !InterestedCustomersService.DEFAULT_CER_NO_UNIT.equals(customer.getCertificateNo()))) {
                            msg = "系统中已存在证件号为'" + customer.getCertificateNo() + "'的客户， 证件号码不允许重复！";
                            break;
                        }
                    }
                }
            }
        } else {
            if (results.size() > 0) {
                for (InterestedCustomers c : results) {
                    if (!Tools.isEmpty(customer.getCertificateNo()) && customer.getCertificateNo().equals(c.getCertificateNo())) {
                        if (!"东贸版".equals(sysOptionsDao.getOptionForString(InterestedCustomersService.DV_VERSION)) ||
                                (!InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER.equals(customer.getCertificateNo()) && !InterestedCustomersService.DEFAULT_CER_NO_UNIT.equals(customer.getCertificateNo()))) {
                            msg = "系统中已存在证件号为'" + customer.getCertificateNo() + "'的客户， 证件号码不允许重复！";
                            break;
                        }
                    }
                    if (c.getObjectNature() == 10 && customer.getObjectName().equals(c.getObjectName())) {
                        msg = "系统中已存在对象性质为“单位”且对象名称为'"
                                + customer.getObjectName() + "'的客户,不能保存！";
                        break;
                    }
                    if (c.getObjectNature() == 10
                            && customer.getShortName().equals(c.getShortName())) {
                        msg = "系统中已存在对象性质为“单位”且对象简称为'"
                                + customer.getShortName() + "'的客户,不能保存！";
                        break;
                    }
                    if (c.getObjectNature() == 20 && customer.getObjectName().equals(c.getObjectName()) && customer.getShortName().equals(c.getShortName())) {
                        msg = "系统中已存在对象名称为'" + customer.getObjectName()
                                + "'、手机号码为'" + customer.getMobile()
                                + "'的客户，对象名称和手机号码不允许同时重复！";
                        break;
                    }
                }
            }
        }
        return msg;
    }

    /**
     * 编辑意向客户所需要的基础数据
     */
    public EditCustomerInitData getBaseDataOfEditCustomer() {
        String stationId = HttpSessionStore.getSessionUser()
                .getDefaulStationId();

        EditCustomerInitData data = new EditCustomerInitData();
        List<String> backVisitWay = baseOtherDao.getDataByTypeNo("VISIT_MODE",
                stationId);// 回访方式

        data.setObjectProperty(baseOtherDao.getDataByTypeNo("OBJECT_PROPERTY",
                stationId));// 客户价值
        data.setPosition(baseOtherDao.getDataByTypeNo("POSITION", stationId));// 职位
        data.setProfession(baseOtherDao
                .getDataByTypeNo("PROFESSION", stationId));// 行业
        data.setPlace(customerDao.getCitys());// 省市区
        data.setVehicleTrademark(customerDao.getVehicleTrademark());// 保有车辆品牌基础数据
        data.setVehicleStrain(customerDao.getVehicleStrain());// 保有车辆品系基础数据
        data.setObjectNature(customerDao
                .getMeanAndCodeMapBySysFlagFieldNo("object_nature"));// 对象性质
        data.setCertificateType(baseOtherDao.getDataByTypeNo(
                "CERTIFICATE_TYPE", stationId));// 证件类型
        data.setVehicleColor(baseOtherDao.getBaseOtherByTypeNoAndStationId(
                "VEHICLE_COLOR", stationId));// 意向颜色
        data.setAttentionEmphases(baseOtherDao.getDataByTypeNo(
                "ATTENTION_EMPHASES", stationId));// 关注重点
        data.setBuyType(customerDao
                .getMeanAndCodeMapBySysFlagFieldNo("vs_buy_type"));// 购车方式
        data.setVisitMode(backVisitWay);// 拜访方式
        data.setVisitorMode(baseOtherDao.getDataByTypeNo("VISITOR_MODE",
                stationId));// 来访方式
        data.setVisitorLevel(baseOtherDao.getDataByTypeNo(
                "TURNOVER_PROBABILITY", stationId));// 成交概率
        data.setKnowWay(baseOtherDao.getDataByTypeNo("KNOW_WAY", stationId));// 了解渠道
        data.setReason(baseOtherDao.getDataByTypeNo("REASON", stationId));// 成败原因
        data.setBackWay(backVisitWay);// 回访方式
        data.setPurpose(baseOtherDao.getDataByTypeNo("BACK_PURPOSE", stationId));// 回访目的
        data.setDeliveryLocus(baseOtherDao.getBaseOtherByTypeNoAndStationId(
                "DELIVERY_LOCUS", stationId));// 销售网点
        data.setPurchaseUse(baseOtherDao.getBaseOtherByTypeNoAndStationId(
                "PURCHASE_USE", stationId));// 购车用途
        data.setDistance(baseOtherDao.getDataByTypeNo("distance", stationId));// 运输距离
        data.setFactLoad(baseOtherDao.getDataByTypeNo("fact_load", stationId));// 实际载重
        data.setVisitAddr(customerDao
                .getMeanAndCodeMapBySysFlagFieldNo("visit_addr"));// 接触地点
        data.setSubjectMatter(baseOtherDao.getBaseOtherByTypeNoAndStationId(
                "SUBJECT_MATTER", stationId));// 标的物
        data.setTransportRoutes(baseOtherDao.getDataByTypeNo(
                "TRANSPORT_ROUTES", stationId));// 运输路线
        data.setWorkingCondtion(baseOtherDao.getDataByTypeNo(
                "WORKING_CONDITION", stationId));// 工况
        data.setCustomerSource(baseOtherDao.getDataByTypeNo("CUSTOMER_SOURCE", stationId)); //客户来源

        data.setBaseVehicleName(baseVehicleNameService.list(stationId)); //车辆名称
        // 跟踪结果只能选择20(失败)、50(终止)
        Map<String, Short> visitResult = customerDao
                .getMeanAndCodeMapBySysFlagFieldNo("visit_result");
        Set<String> keys = visitResult.keySet();
        List<String> tmpKeys = new ArrayList<String>();
        for (String key : keys) {
            if (visitResult.get(key) != 20 && visitResult.get(key) != 50) {
                tmpKeys.add(key);
            }
        }
        for (String key : tmpKeys) {
            visitResult.remove(key);
        }
        data.setVisitResult(visitResult);// 跟踪结果
        return data;
    }

    /**
     * 获取车辆型号基础数据
     *
     * @param stationId
     * @param keyword   关键字，可为null或者车辆型号、销售代号、车辆名称
     * @return
     */
    public List<VehicleType> getVehicleType(String stationId, String keyword) {
        return customerDao.getVehicleType(stationId, keyword);
    }

    /**
     * 是否可以新建意向线索
     */
    public String canCreateIntentionClue(String customerId, SysUsers user) {
        List<PresellVisitors> list = customerDao
                .getUnfinishedSaleClueInTheSameDepartment(customerId,
                        user.getDepartment());
        if (list != null && list.size() > 0) {
            return "当前部门下该客户存在跟进中线索，不能新建!";
        }
        list = customerDao.getAllUnfinishedSalclue(customerId);// 所有未完成的销售线索
        boolean bAllowMultiClue = "允许".equals(customerDao
                .getOptionValue("VEHICLE_ALLOW_MULTI_CLUE"));// 是否允许多线索同时跟进同一客户
        if (list != null && list.size() > 0 && !bAllowMultiClue) {
            return "客户已在跟进中,不允许多线索跟进";
        }
        return null;
    }

    /**
     * 新建意向线索
     */
    public void createIntentionClue(PresellVisitorsVO intentionClue,
                                    PresellVisitorsBack backVisitPlan, SysUsers user) {

        if (backVisitPlan.getPlanBackTime() == null) {
            throw new ServiceException("必须为意向线索制定计划回访时间");
        }

        InterestedCustomers customer = customerDao.get(InterestedCustomers.class,
                intentionClue.getVisitorId());
        if (customer == null) {
            throw new ServiceException("该客户不存在");
        }
        String msg = canCreateIntentionClue(intentionClue.getVisitorId(), user);
        if (msg != null) {
            throw new ServiceException(msg);
        }

        PresellVisitors saleClue = new PresellVisitors();
        BeanUtils.copyProperties(intentionClue, saleClue);

        Timestamp time = new Timestamp(System.currentTimeMillis());
        String intentClueNo = UUID.randomUUID().toString();
        saleClue.setVisitorNo(intentClueNo);
        saleClue.setStationId(user.getInstitution().getDefaultStation());
        saleClue.setVisitTime(new Timestamp(new Date().getTime()));
        saleClue.setVisitorCount("1");
        saleClue.setSeller(user.getUserName());
        saleClue.setCreator(user.getUserFullName());
        saleClue.setCreateTime(time);
        saleClue.setSellerId(user.getUserId());
        saleClue.setLastIsCompetitive(customer.getIsCompetitive());

        // 查找上一次跟进完毕的销售线索，将上次跟踪结果回填
        DetachedCriteria dc = DetachedCriteria.forClass(PresellVisitors.class);
        dc.add(Restrictions.eq("visitorId", saleClue.getVisitorId()));
        dc.add(Restrictions.in("visitResult", new Short[]{10, 20}));
        dc.addOrder(Order.desc("finishDate"));
        @SuppressWarnings("unchecked")
        List<PresellVisitors> lastSaleClues = customerDao.findByCriteria(dc);
        if (lastSaleClues != null && lastSaleClues.size() > 0) {
            saleClue.setLastVisitResult(lastSaleClues.get(0).getVisitResult());
        }
        backVisitPlan.setBackId(UUID.randomUUID().toString());
        backVisitPlan.setVisitorNo(intentClueNo);
        // 需要将计划回访时间回填到线索中
        saleClue.setPlanBackTime(backVisitPlan.getPlanBackTime());

        customerDao.save(saleClue);
        customerDao.save(backVisitPlan);
    }

    /**
     * 修改意向线索
     */
    public void updateIntentionClue(PresellVisitorsVO intentionClue,
                                    SysUsers user) {
        PresellVisitors saleClue = customerDao.get(PresellVisitors.class,
                intentionClue.getVisitorNo());
        if (saleClue == null) {
            throw new ServiceException("线索不存在,id:"
                    + intentionClue.getVisitorNo());
        }

        if (saleClue.getVisitResult() != null) {
            throw new ServiceException("线索已跟踪完毕,不可修改");
        }
        if (!user.getUserId().equals(saleClue.getSellerId())) {
            throw new ServiceException("您不是此线索的销售员,不能修改");
        }

        BeanUtils.copyProperties(intentionClue, saleClue, "visitorNo",
                "visitorLevel", "seller", "sellerId");

        saleClue.setModifier(user.getUserFullName());
        saleClue.setModifyTime(new Timestamp(System.currentTimeMillis()));

        customerDao.update(saleClue);
    }

    public List<BaseMaintenanceWorkgroups> getMaintainWorkgroup(SysUsers user) {
        return customerDao.getMaintenanceWorkgroupOfUser(user);
    }

    /**
     * 判断用户是否能新建意向客户
     *
     * @param user
     * @return
     */
    public String canCreateCustomer(SysUsers user) {
        if (!user.hasPopedom(AccessPopedom.SaleCustomer.CUSTOMER_FOLLOW)) {
            return "您没有权限!";
        }
        //不需要用户的角色类型必须包含 维系人员才能通过这个校验  caigx 20170123
//		boolean hasMaintainRole = false;
//		for (SysRoles role : user.getRoles()) {
//
//			if (role.getRoleType() != null && role.getRoleType().contains("35")) {
//				hasMaintainRole = true;
//				break;
//			}
//		}
//		if (!hasMaintainRole) {
//			return "您不是客户维系人员,不能新建客户";
//		}
        return null;
    }

    /**
     * 新建意向客户
     *
     * @param customerVO     客户信息
     * @param retainVehicles 客户保有车辆
     * @param saleClueVO     意向线索
     * @param backVisitPlan  回访计划
     */
    public void createIntentionCustomer(CustomerVO customerVO,
                                        List<CustomerRetainVehicle> retainVehicles,
                                        PresellVisitorsVO saleClueVO, PresellVisitorsBack backVisitPlan) {
        SysUsers user = HttpSessionStore.getSessionUser();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if (backVisitPlan.getPlanBackTime() == null) {
            throw new ServiceException("必须为意向线索制定回访计划");
        }

        // 判断是否可以新建意向客户
        String canCreateCustomer = canCreateCustomer(user);
        if (canCreateCustomer != null) {
            throw new ServiceException(canCreateCustomer);
        }

        InterestedCustomers customer = new InterestedCustomers();
        BeanUtils.copyProperties(customerVO, customer);

        PresellVisitors saleClue = new PresellVisitors();
        BeanUtils.copyProperties(saleClueVO, saleClue);

        String objectId = UUID.randomUUID().toString();// 用户ID
        customer.setObjectId(objectId);
        // 检查客户信息是否合法
        String msg = isCustomerExist(customer);
        if (msg != null) {
            throw new ServiceException(msg);
        }

        // customer.setFullId(objectId);
        customer.setObjectNo(getAutoNoOfObject());
        customer.setStationId(user.getInstitution().getDefaultStation());
        // customer.setIsParent(false);
        customer.setStatus((short) 1);
        customer.setNamePinyin(GetChineseFirstChar.getFirstLetter(customer
                .getObjectName()));
        if (customer.getObjectNature() == 20
                && ("".equals(customer.getShortName()) || customer
                .getShortName() == null)) {
            customer.setShortName(customer.getObjectName());
        }
        customer.setCustomerType((short) 30);// 新建时为有效客户
        customer.setObjectKind(1);
        customer.setObjectType(2);
        customer.setCreator(user.getUserFullName());
        customer.setCreateStationId(user.getDefaulStationId());
        customer.setCreateTime(currentTime);

        // ===========2015-01-05 修改： 删除维系小组， 添加维系人==========
        // CustomerMaintenanceWorkgroup workgroup = new
        // CustomerMaintenanceWorkgroup();
        // workgroup.setSelfId(UUID.randomUUID().toString());
        // workgroup.setObjectId(objectId);
        // workgroup.setStationId(user.getInstitution().getDefaultStation());
        // workgroup.setBmwId(workGroups.get(0).getBmwId());

//        BaseRelatedObjectMaintenace maintenace = new BaseRelatedObjectMaintenace();
//        maintenace.setObjectId(objectId);
//        maintenace.setIsManager(false);
//        maintenace.setBusiness(10);// 业务类型：车辆 ，sys_flags maintenace_business
//        maintenace.setUserId(user.getUserId());
//        maintenace.setStationId(user.getDefaulStationId());

        saleClue.setVisitorNo(UUID.randomUUID().toString());
        if (saleClue.getVisitTime() == null) {
            saleClue.setVisitTime(currentTime);
        }
        saleClue.setStationId(user.getInstitution().getDefaultStation());
        saleClue.setVisitorCount(String.valueOf(1));
        saleClue.setVisitorId(objectId);
        saleClue.setSeller(user.getUserName());
        saleClue.setCreator(user.getUserFullName());
        saleClue.setCreateTime(currentTime);
        saleClue.setSellerId(user.getUserId());
        saleClue.setLastIsCompetitive(customer.getIsCompetitive());

        backVisitPlan.setBackId(UUID.randomUUID().toString());
        backVisitPlan.setVisitorNo(saleClue.getVisitorNo());

        // 需要将计划回访时间回填到线索中
        saleClue.setPlanBackTime(backVisitPlan.getPlanBackTime());

        // 产生核算对象
        AcctItem item = new AcctItem();
        item.setTitemId(sysIdentityDao
                .getIdentityAndIncrementUpdate("acct_item"));
        item.setTitemClassId(3);
        item.setTno(customer.getObjectNo());
        item.setTparentId(0);
        item.setTlevel((short) 1);
        item.setTdetail(true);
        item.setTname(customer.getObjectName());
        item.setTused(false);
        item.setTid(customer.getObjectId());

        for (CustomerRetainVehicle retainVehicle : retainVehicles) {
            if (retainVehicle != null
                    && retainVehicle.getVehicleBrand() != null
                    && retainVehicle.getVehicleBrand().length() > 0) {
                retainVehicle.setSelfId(UUID.randomUUID().toString());
                retainVehicle.setCustomerId(objectId);
                customerDao.save(retainVehicle);
            }
        }
        customerDao.save(item);
        customerDao.save(customer);
        // customerDao.save(workgroup);
//        customerDao.save(maintenace);
        customerDao.save(saleClue);
        customerDao.save(backVisitPlan);

    }

    /**
     * 更新意向客户
     */
    public void updateCustomer(CustomerVO customerVo) {
        SysUsers user = HttpSessionStore.getSessionUser();

        InterestedCustomers customer = new InterestedCustomers();
        BeanUtils.copyProperties(customerVo, customer);

        InterestedCustomers relatedObj = customerDao.get(
                InterestedCustomers.class, customer.getObjectId());
        if (relatedObj == null) {
            throw new ServiceException("客户不存在,ID:" + customer.getObjectId());
        }
        // 检查客户信息是否合法
        String msg = isCustomerExist(customer);
        if (msg != null) {
            throw new ServiceException(msg);
        }
        boolean hasCustomerConsumed = customerDao.hasCustomerConsumed(customer
                .getObjectId());
        if (!hasCustomerConsumed
                && (relatedObj.getCreator().equals(user.getUserFullName()) || user
                .hasPopedom("10201210"))) {
            // 没有消费历史并且是当前用户新建的或者具有修改客户名称权限才能修改客户名称
            relatedObj.setObjectName(customer.getObjectName());
            relatedObj.setShortName(customer.getShortName());
            relatedObj.setNamePinyin(GetChineseFirstChar
                    .getFirstLetter(customer.getObjectName()));
        }
        relatedObj.setMobile(customer.getMobile());
        relatedObj.setLinkman(customer.getLinkman());
        relatedObj.setProvince(customer.getProvince());
        relatedObj.setCity(customer.getCity());
        relatedObj.setArea(customer.getArea());
        relatedObj.setObjectNature(customer.getObjectNature());
        relatedObj.setCertificateType(customer.getCertificateType());
        relatedObj.setCertificateNo(customer.getCertificateNo());
        // relatedObj.setPosition(customer.getPosition());
        relatedObj.setNation(customer.getNation());

        relatedObj.setModifyTime(new Timestamp(System.currentTimeMillis()));
        relatedObj.setModifier(user.getUserFullName());

        if (!Tools.isEmpty(customerVo.getRetainVehicleBrand())) {
            CustomerRetainVehicle vehicle = new CustomerRetainVehicle();
            vehicle.setVehicleBrand(customerVo.getRetainVehicleBrand());
            vehicle.setVehicleStrain(customerVo.getRetainVehicleStrain());
            vehicle.setCustomerId(customer.getObjectId());
            vehicle.setSelfId(customerVo.getRetainVehicleId());
            if (Tools.isEmpty(vehicle.getSelfId())) {
                vehicle.setSelfId(UUID.randomUUID().toString());
            }
            customerDao.update(vehicle);// save or update
        }

        customerDao.update(relatedObj);
    }

    /**
     * 获取回访信息列表
     */
    public List<PresellVisitorsBack> getBackVisitList(String presellVisitorId) {
        return customerDao.getSaleClueBackVisitList(presellVisitorId);
    }

    /**
     * 获取对于指定客户，当前销售员需要回访的销售回访计划
     *
     * @param customerId
     * @param sellerId
     * @return
     */
    public PresellVisitorsBack getToBeBackVisitPlan(String customerId,
                                                    String sellerId) {
        return customerDao.getTobeBackVisitPlan(customerId, sellerId);
    }

    /**
     * 记录回访信息
     *
     * @param backVisit
     * @param nextBackVisitPlan
     */
    public void addBackVisit(PresellVisitorsBack backVisit,
                             PresellVisitorsBack nextBackVisitPlan, SysUsers user) {
        PresellVisitors saleClue = customerDao.get(PresellVisitors.class,
                backVisit.getVisitorNo());
        if (saleClue == null) {
            throw new ServiceException("线索不存在,id:" + backVisit.getVisitorNo());
        }
        if (saleClue.getVisitResult() != null) {
            throw new ServiceException("该线索已经回访完毕，不能回访!");
        }
        Short visitResult = backVisit.getVisitResult();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        if (visitResult != null) {
            if (visitResult != 20 && visitResult != 50) {
                throw new ServiceException("跟踪结果只能为失败或终止");

            } else if (visitResult == 20) {
                // 失败，记录到战败客户
                InterestedCustomers customer = customerDao.get(
                        InterestedCustomers.class, saleClue.getVisitorId());
                PresellVisitorsFail fail = new PresellVisitorsFail();
                fail.setVisitorNo(saleClue.getVisitorNo());
                fail.setVisitorId(saleClue.getVisitorId());
                fail.setVisitorName(customer.getObjectName());
                fail.setVisitorMobile(customer.getMobile());
                fail.setEndDeal(false);
                fail.setCreateTime(time);
                fail.setCreator(user.getUserFullName());

                customerDao.save(fail);
            }

            // 跟踪结果不为空时，回填跟踪结果，以及线索成败原因及总结到线索中
            saleClue.setVisitResult(visitResult);
            saleClue.setReason(backVisit.getReason());
            saleClue.setSelfOpinion(backVisit.getSelfOpinion());
            saleClue.setFinishDate(time);

        } else {
            if (nextBackVisitPlan == null
                    || nextBackVisitPlan.getPlanBackTime() == null) {
                throw new ServiceException("必须制定下次回访计划");
            }

            nextBackVisitPlan.setBackId(UUID.randomUUID().toString());
            nextBackVisitPlan.setVisitorNo(saleClue.getVisitorNo());
            saleClue.setPlanBackTime(nextBackVisitPlan.getPlanBackTime());
            customerDao.save(nextBackVisitPlan);
        }

        saleClue.setVisitorLevel(backVisit.getBackVisitorLevel());

        backVisit.setBacker(user.getUserFullName());
        backVisit.setRealBackTime(time);

        customerDao.update(saleClue);
        customerDao.update(backVisit);
    }

    @Override
    public PageModel getCustomers(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
        return customerDao.getCustomers(user, keyword, filter, sort, perPage, page);
    }


    @Override
    public PageModel getPublicCustomers(String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
        return customerDao.getPublicCustomers(keyword, filter, sort, perPage, page);
    }

    @Override
    public void applyPublicCustomer(SysUsers user, String objectId) {
//        BaseRelatedObjectMaintenace relation = new BaseRelatedObjectMaintenace();
//        relation.setObjectMaintenaceId(UUID.randomUUID().toString());
//        relation.setObjectId(objectId);
//        relation.setStationId(user.getDefaulStationId());
//        relation.setBusiness(10);
//        relation.setUserId(user.getUserId());
//        customerDao.save(relation);
    }

	/*@Override
	public void setCustomerMaintenance(SysUsers user, String objectId, String maintenanceUserId) {
		SysUsers objectUser = customerDao.get(SysUsers.class, maintenanceUserId);
		List<BaseRelatedObjectMaintenace> history = customerDao.getEntityBySQL("SELECT * FROM base_related_object_maintenace WHERE object_id='" + objectId + "'", BaseRelatedObjectMaintenace.class, null);
		if(history != null && history.size() > 0){
			if(user.hasPopedom(AccessPopedom.SaleCustomer.EDIT_MAINTENANCE)) {
				for (BaseRelatedObjectMaintenace item : history) {
					customerDao.delete(item);
				}
			}else{
				throw new ServiceException("缺少 分配维系人 权限");
			}
		}
		BaseRelatedObjectMaintenace relation = new BaseRelatedObjectMaintenace();
		relation.setObjectMaintenaceId(UUID.randomUUID().toString());
		relation.setObjectId(objectId);
		relation.setStationId(objectUser.getDefaulStationId());
		relation.setBusiness(10);
		relation.setUserId(objectUser.getUserId());
		customerDao.save(relation);
	}*/

    @Override
    public String checkCustomerRepeat(SysUsers user, String objectName, String mobile, Integer objectNature) {
        List<VwStatEffectiveCustomer> customers;
        if (objectNature == null || objectNature == 20) {
            // 个人
            customers = customerDao.getEntityBySQL("SELECT * FROM vw_stat_effective_customer WHERE object_name = '" + objectName + "' AND mobile = '" + mobile + "'",
                    VwStatEffectiveCustomer.class, null
            );
        } else {
            // 单位
            customers = customerDao.getEntityBySQL("SELECT * FROM vw_stat_effective_customer WHERE object_name = '" + objectName + "' OR mobile = '" + mobile + "'",
                    VwStatEffectiveCustomer.class, null
            );
        }
        if (customers != null && customers.size() > 0) {
            VwStatEffectiveCustomer customer = customers.get(0);
            PresellVisitorsRepeatCheck check = new PresellVisitorsRepeatCheck();
            check.setSelfId(UUID.randomUUID().toString());
            check.setCustomerName(customer.getObjectName());
            check.setCustomerMobile(customer.getMobile());
            check.setRepeatCustomerName(objectName);
            check.setRepeatCustomerMobile(mobile);
            check.setSeller(user.getUserName());
            check.setCreator(user.getUserName() + "(" + user.getUserNo() + ")");
            check.setCreateTime(new Timestamp(System.currentTimeMillis()));
            check.setCustomerId(customer.getObjectId());
            customerDao.save(check);
            return customer.getObjectId();
        }
        return null;
    }

    @Override
    public PresellVisitors getPreSalleClue(String saleClueId) {
        PresellVisitors pv = customerDao.get(PresellVisitors.class, saleClueId);
        if (pv.getVisitorName() == null || pv.getVisitorName().length() == 0) {
            if (pv.getVisitorId() != null) {
                InterestedCustomers ic = customerDao.get(InterestedCustomers.class, pv.getVisitorId());
                if (ic != null) {
                    pv.setVisitorName(ic.getObjectName());
                    pv.setVisitorMobile(ic.getMobile());
                    pv.setVisitorPhone(ic.getPhone());
                    pv.setVisitorAddress(ic.getAddress());
                }
            }
        }
        return pv;
    }

    private static String getSaleClueBackSql = "SELECT back.back_id, back.visitor_no, back.back_purpose, back.back_way, back.plan_back_time,"
            + " back.real_back_time, back.back_schedule, back.back_content, back.back_visitor_level,"
            + " back.backer, back.checker, back.check_time, back.check_content, back.back_intent_level, back.pics,"
            + " back.remark, back.address, back.longitude, back.latitude,"
            //由于在presell_visitors_back表中‘before_back_intent_level’和‘before_back_visitor_level’存在空的情况
            + "(case when back.before_back_intent_level is null then visitor.intent_level else visitor.intent_level end) as before_back_intent_level,"
            + "(case when back.before_back_visitor_level is null then visitor.visitor_level else back.before_back_visitor_level end) as before_back_visitor_level,"
            + " customer.location_address AS visitor_location_address, customer.object_name AS visitor_name, customer.mobile AS visitor_mobile, customer.address AS visitor_address "
            + " FROM presell_visitors_back back\n"
            + " LEFT JOIN presell_visitors visitor ON visitor.visitor_no = back.visitor_no\n"
            + " LEFT JOIN vw_stat_effective_customer customer ON visitor.visitor_id = customer.object_id\n"
            + " %s ORDER BY back.plan_back_time DESC ";

    @Override
    public List<PresellVisitorsBackVO> getSaleClueBackByCustomer(String customerId) {
		/*return customerDao.getEntityBySQL("SELECT back.*, customer.location_address AS visitor_location_address, customer.object_name AS visitor_name, customer.mobile AS visitor_mobile, customer.address AS visitor_address FROM presell_visitors_back back\n" +
				"  LEFT JOIN presell_visitors visitor ON visitor.visitor_no = back.visitor_no\n" +
				"  LEFT JOIN vw_stat_effective_customer customer ON visitor.visitor_id = customer.object_id\n" +
				"WHERE back.visitor_no IN (SELECT visitor_no FROM presell_visitors WHERE visitor_id = '"+customerId+"') ORDER BY back.plan_back_time DESC ", PresellVisitorsBackVO.class, null);*/
        String sql = String.format(getSaleClueBackSql, " WHERE back.visitor_no IN (SELECT visitor_no FROM presell_visitors WHERE visitor_id = '" + customerId + "')");
        return customerDao.getEntityBySQL(sql, PresellVisitorsBackVO.class, null);
    }

    @Override
    public List<PresellVisitorsBackVO> getSaleClueBackByCule(String saleClueId) {
		/*return customerDao.getEntityBySQL("SELECT back.*, customer.location_address AS visitor_location_address, customer.object_name AS visitor_name, customer.mobile AS visitor_mobile, customer.address AS visitor_address FROM presell_visitors_back back\n" +
				"  LEFT JOIN presell_visitors visitor ON visitor.visitor_no = back.visitor_no\n" +
				"  LEFT JOIN vw_stat_effective_customer customer ON visitor.visitor_id = customer.object_id\n" +
				"WHERE visitor.visitor_no = '"+saleClueId+"' ORDER BY back.plan_back_time DESC ", PresellVisitorsBackVO.class, null);*/
        String sql = String.format(getSaleClueBackSql, " WHERE visitor.visitor_no = '" + saleClueId + "'");
        return customerDao.getEntityBySQL(sql, PresellVisitorsBackVO.class, null);
    }

    @Override
    public PageModel getClueBacks(SysUsers user, Map<String, Object> postJson, int pageSize, int pageNo) {
        return customerDao.getClueBacks(user, postJson, pageSize, pageNo);
    }

    @Override
    public CustomerRetainVehicleOverview removeVehicleOverview(SysUsers attributeFromSession, String selfId) {
        CustomerRetainVehicleOverview data = customerDao.get(CustomerRetainVehicleOverview.class, selfId);
        customerDao.delete(data);
        return data;
    }

    @Override
    public PageModel getRelatedObjects(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
        return customerDao.getRelatedObjects(user, keyword, filter, sort, perPage, page);
    }

    @Override
    public List<PresellVisitorsRepeatCheck> getCustomerRepeat(String customerId) {
        return customerDao.getEntityBySQL(
                "SELECT * FROM presell_visitors_repeat_check WHERE customer_id = '" + customerId + "'",
                PresellVisitorsRepeatCheck.class, null);
    }

    @Override
    public PresellVisitors removeSaleClue(SysUsers attributeFromSession, String saleClueId) {
        PresellVisitors data = customerDao.get(PresellVisitors.class, saleClueId);
        customerDao.delete(data);
        return data;
    }

    @Override
    public PresellVisitorsBack getVisit(SysUsers user, String selfId) {
		/*String sql = "SELECT back.*, customer.location_address AS visitor_location_address, customer.object_name AS visitor_name, customer.mobile AS visitor_mobile, customer.address AS visitor_address FROM effective_custome_call_back back\n" +
				"LEFT JOIN vw_stat_effective_customer customer ON back.customer_id = customer.object_id WHERE back_id = '" + selfId + "'";
		List<EffectiveCustomeCallBackVO> list = customerDao.getEntityBySQL(sql, EffectiveCustomeCallBackVO.class, null);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}*/

        return customerDao.get(PresellVisitorsBack.class, selfId);
    }

    @Override
    public PresellVisitors updatePreSalleClueResult(SysUsers user, String saleClueId, Short visitResult, String reason) {
        PresellVisitors clue = customerDao.get(PresellVisitors.class, saleClueId);
        clue.setVisitResult(visitResult);
        if (reason != null) {
            clue.setReason(reason);
        }
        Timestamp time = new Timestamp(System.currentTimeMillis());
        clue.setModifier(user.getUserName() + "(" + user.getUserNo() + ")");
        clue.setModifyTime(time);
        clue.setFinishDate(time);
        customerDao.update(clue);
        if (visitResult == 20) {
            // 失败
            Timestamp now = new Timestamp(System.currentTimeMillis());
            PresellVisitorsFail fail = new PresellVisitorsFail();
            fail.setVisitorNo(clue.getVisitorNo());
            fail.setVisitorId(clue.getVisitorId());
            fail.setVisitorName(clue.getVisitorName());
            fail.setVisitorMobile(clue.getVisitorMobile());
            fail.setVisitorPhone(clue.getVisitorPhone());
            fail.setEndDeal(false);
            fail.setCreateTime(now);
            fail.setCreator(user.getUserFullName());
            fail.setFailedReason(reason);
            customerDao.save(fail);
        }
        return clue;
    }

    @Override
    public Map<String, Object> getSysInitData() {
        Map<String, Object> result = new HashMap<String, Object>(8);
        result.put("visit_result", customerDao.getMapBySQL("SELECT code, meaning, sort_no FROM sys_flags WHERE field_no = 'visit_result'", null));
        result.put("buy_type", customerDao.getMapBySQL("SELECT code, meaning, sort_no FROM sys_flags WHERE field_no = 'vs_buy_type'", null));
        result.put("vehicle_color", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM base_others WHERE type_no='VEHICLE_COLOR'", null));
        result.put("intent_level", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM base_others WHERE type_no='vehicle_intent_level'", null));
        result.put("subject_matter", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM base_others WHERE type_no='subject_matter'", null));

        result.put("object_kind", customerDao.getMapBySQL("SELECT code, meaning FROM sys_flags WHERE field_no= 'object_kind'", null));

        // 客户类型
        result.put("object_type", customerDao.getMapBySQL("SELECT code, meaning FROM sys_flags WHERE field_no= 'object_type'", null));

        // 客户类别
        result.put("customer_kind", customerDao.getMapBySQL("SELECT code, meaning FROM sys_flags WHERE field_no= 'customer_kind'", null));

        // customer_type
        result.put("visitor_type", customerDao.getMapBySQL("SELECT code, meaning FROM sys_flags WHERE field_no= 'visitor_type'", null));

        // 行业
        result.put("profession", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM dbo.base_others WHERE type_no='profession'", null));
        // 客户地位
        result.put("object_property", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM dbo.base_others WHERE type_no='object_property'", null));
        // 证件类型
        result.put("certificate_type", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM dbo.base_others WHERE type_no='certificate_type'", null));

        // 购车用途
        result.put("purchase_use", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM dbo.base_others WHERE type_no='purchase_use'", null));

        // 接触地点
        result.put("visit_addr", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM dbo.base_others WHERE type_no='visit_addr'", null));

        // 标的物
        result.put("subject_matter", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM dbo.base_others WHERE type_no='subject_matter'", null));

        // 销售网点
        result.put("delivery_locus", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM dbo.base_others WHERE type_no='delivery_locus'", null));

        // 拜访目的
        result.put("back_purpose", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM dbo.base_others WHERE type_no='back_purpose'", null));

        // 拜访方式
        result.put("visit_mode", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM dbo.base_others WHERE type_no='visit_mode'", null));

        // 对象性质
        result.put("object_nature", customerDao.getMapBySQL("SELECT code, meaning FROM sys_flags WHERE field_no= 'object_nature'", null));

        // 车辆品牌
        result.put("vehicle_brand", customerDao.getMapBySQL("SELECT common_name as code, common_name as meaning FROM base_vehicle_name WHERE common_type = '车辆品牌'  AND (status = 1 OR status IS NULL)", null));

        //跟进目的
        result.put("BACK_PURPOSE", customerDao.getMapBySQL("SELECT data as code, data as meaning, sort as sort_no FROM dbo.base_others WHERE type_no='BACK_PURPOSE'", null));

        return result;
    }

    @Override
    public Map<String, Object> getCustomerInterestedCustomers(String objectId) {
        String sql = "SELECT b.customer_group_no,b.customer_group_name, h.intent_level, ISNULL(h.visit_result,0) as visit_result,\n" +
                "\tISNULL(h.visitor_count, 0) AS pending_visitor_count,g.plan_back_time,a.*\n" +
                "FROM dbo.vw_interested_customers a\n" +
                "left join dbo.base_customer_groups b\n" +
                "on  a.customer_group_id = b.customer_group_id\n" +
                "\tLEFT JOIN (SELECT *\n" +
                "\t\tFROM (SELECT ROW_NUMBER() OVER (PARTITION BY x.visitor_id ORDER BY x.create_time DESC) AS rowNumber, x.visitor_id, x.visit_result, x.intent_level, COUNT(0) AS visitor_count\n" +
                "\t\t\tFROM presell_visitors x\n" +
                "\t\t\tGROUP BY x.visitor_id, x.create_time, x.visit_result, x.intent_level) t WHERE t.rowNumber = 1) h\n" +
                "\tON a.object_id = h.visitor_id\n" +
                "\tLEFT JOIN  ( SELECT object_id, MAX (real_back_time) AS real_back_time, MAX (plan_back_time) AS plan_back_time\n" +
                "\t\t\tFROM dbo.presell_visitors_back\n" +
                "\t\t\tGROUP BY object_id ) g ON a.object_id = g.object_id\n" +
                "WHERE a.object_id =:objectId";
        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("objectId", objectId);
        List<Map<String, Object>> result = customerDao.getMapBySQL(sql, paramMap);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            Map<String, Object> map = result.get(0);
            Map<String, Object> purchaseSummary = customerDao.getCustomerPurchaseSummaryById(objectId);
            if (null != purchaseSummary) {
                map.putAll(purchaseSummary);
            }
            Timestamp lastPurchaseTime = customerDao.getCustomerLastPurchaseTimeById(objectId);
            map.put("lastPurchaseTime", lastPurchaseTime);
            Timestamp lastVisitTime = customerDao.getCustomerLastVisitTimeById(objectId);
            map.put("lastVisitTime", lastVisitTime);
            Timestamp lastVisitPlanTime = customerDao.getCustomerLastVisitPlanTimeById(objectId);
            map.put("lastVisitPlanTime", lastVisitPlanTime);
            return map;
        }
    }

    @Override
    public List<CustomerRetainVehicleOverview> getVehicleOverview(String objectId) {
        return customerDao.getEntityBySQL("SELECT * FROM customer_retain_vehicle_overview WHERE customer_id = '" + objectId + "'", CustomerRetainVehicleOverview.class, null);
    }

    @Override
    public List<CustomerOrganizationalStructure> getOrganizational(String objectId) {
        return customerDao.getEntityBySQL("SELECT * FROM customer_organizational_structure WHERE customer_id = '" + objectId + "'", CustomerOrganizationalStructure.class, null);
    }

    @Override
    public List<VehicleArchives> getVehicleArchives(String objectId) {
        return customerDao.getEntityBySQL("SELECT * FROM vehicle_archives WHERE customer_id = '" + objectId + "'", VehicleArchives.class, null);
    }

    @Override
    public List<Map<String, Object>> getConsumption(String objectId) {
        return customerDao.getMapBySQL("SELECT  a.customer_id, a.business_name, MAX(a.last_date) AS last_date,\n" +
                "                                        SUM(a.three_money) AS three_money, SUM(a.three_count) AS three_count,\n" +
                "                                        SUM(a.six_money) AS six_money, SUM(a.six_count) six_count,\n" +
                "                                        SUM(a.twelve_money) AS twelve_money, SUM(a.twelve_count) twelve_count\n" +
                "FROM    ( SELECT    '车辆' AS business_name, b.customer_id,\n" +
                "                    MAX(a.real_deliver_time) AS last_date,\n" +
                "                    SUM(a.vehicle_price) AS three_money,\n" +
                "                    COUNT(*) AS three_count, 0 AS six_money, 0 AS six_count,\n" +
                "                    0 AS twelve_money, 0 AS twelve_count\n" +
                "          FROM      dbo.vehicle_sale_contract_detail a\n" +
                "            LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                "          WHERE     a.real_deliver_time IS NOT NULL\n" +
                "                    AND b.contract_status<>4 AND b.contract_status<>4\n" +
                "                    AND a.status<>30\n" +
                "                    AND DATEDIFF(MONTH, a.real_deliver_time, GETDATE()) < 3\n" +
                "                    AND DATEDIFF(MONTH, a.real_deliver_time, GETDATE()) >= 0\n" +
                "          GROUP BY  b.customer_id\n" +
                "          UNION\n" +
                "          SELECT    '车辆' AS business_name, b.customer_id,\n" +
                "                    MAX(a.real_deliver_time) AS last_date, 0 AS three_money,\n" +
                "                    0 AS three_count, SUM(a.vehicle_price) AS six_money,\n" +
                "                    COUNT(*) AS six_count, 0 AS twelve_money,\n" +
                "                    0 AS twelve_count\n" +
                "          FROM      dbo.vehicle_sale_contract_detail a\n" +
                "            LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                "          WHERE     a.real_deliver_time IS NOT NULL\n" +
                "                    AND b.contract_status<>4 AND b.contract_status<>4\n" +
                "                    AND a.status<>30\n" +
                "                    AND DATEDIFF(MONTH, a.real_deliver_time, GETDATE()) < 6\n" +
                "                    AND DATEDIFF(MONTH, a.real_deliver_time, GETDATE()) >= 0\n" +
                "          GROUP BY  b.customer_id\n" +
                "          UNION\n" +
                "          SELECT    '车辆' AS business_name, b.customer_id,\n" +
                "                    MAX(a.real_deliver_time) AS last_date, 0 AS three_money,\n" +
                "                    0 AS three_count, 0 AS six_money, 0 AS six_count,\n" +
                "                    SUM(a.vehicle_price) AS twelve_money,\n" +
                "                    COUNT(*) AS twelve_count\n" +
                "          FROM      dbo.vehicle_sale_contract_detail a\n" +
                "            LEFT JOIN dbo.vehicle_sale_contracts b ON a.contract_no = b.contract_no\n" +
                "          WHERE     a.real_deliver_time IS NOT NULL\n" +
                "                    AND b.contract_status<>4 AND b.contract_status<>4\n" +
                "                    AND a.status<>30\n" +
                "                    AND DATEDIFF(MONTH, a.real_deliver_time, GETDATE()) < 12\n" +
                "                    AND DATEDIFF(MONTH, a.real_deliver_time, GETDATE()) >= 0\n" +
                "          GROUP BY  b.customer_id\n" +
                "          UNION\n" +
                "          SELECT    '维修' AS business_name, customer_id,\n" +
                "                    MAX(a.balance_time) AS last_date, SUM(m) AS three_money,\n" +
                "                    CASE WHEN a.task_kind = 10 THEN COUNT(DISTINCT ( vehicle_vin + '_' + CONVERT(VARCHAR(100), balance_time, 23) ))\n" +
                "                    ELSE 0 END AS three_count, 0 AS six_money, 0 AS six_count,\n" +
                "            0 AS twelve_money, 0 AS twelve_count\n" +
                "          FROM      dbo.vw_stat_taskdetail_income a\n" +
                "          WHERE     a.balance_time IS NOT NULL\n" +
                "                    AND a.task_status =60\n" +
                "                    AND DATEDIFF(MONTH, a.balance_time, GETDATE()) < 3\n" +
                "                    AND DATEDIFF(MONTH, a.balance_time, GETDATE()) >= 0\n" +
                "          GROUP BY  customer_id,a.task_kind\n" +
                "          UNION\n" +
                "          SELECT    '维修' AS business_name, customer_id,\n" +
                "                    MAX(a.balance_time) AS last_date, 0 AS three_money,\n" +
                "                    0 AS three_count, SUM(m) AS six_money,\n" +
                "                    CASE WHEN a.task_kind = 10 THEN COUNT(DISTINCT ( vehicle_vin + '_' + CONVERT(VARCHAR(100), balance_time, 23) ))\n" +
                "                    ELSE 0 END AS six_count, 0 AS twelve_money, 0 AS twelve_count\n" +
                "          FROM      dbo.vw_stat_taskdetail_income a\n" +
                "          WHERE     a.balance_time IS NOT NULL\n" +
                "                    AND a.task_status =60\n" +
                "                    AND DATEDIFF(MONTH, a.balance_time, GETDATE()) < 6\n" +
                "                    AND DATEDIFF(MONTH, a.balance_time, GETDATE()) >= 0\n" +
                "          GROUP BY  customer_id,a.task_kind\n" +
                "          UNION\n" +
                "          SELECT    '维修' AS business_name, customer_id,\n" +
                "                    MAX(a.balance_time) AS last_date, 0 AS three_money,\n" +
                "                    0 AS three_count, 0 AS six_money, 0 AS six_count,\n" +
                "                    SUM(m) AS twelve_money,\n" +
                "                    CASE WHEN a.task_kind = 10 THEN COUNT(DISTINCT ( vehicle_vin + '_' + CONVERT(VARCHAR(100), balance_time, 23) ))\n" +
                "                    ELSE 0 END AS twelve_count\n" +
                "          FROM      dbo.vw_stat_taskdetail_income a\n" +
                "          WHERE     a.balance_time IS NOT NULL\n" +
                "                    AND a.task_status =60\n" +
                "                    AND DATEDIFF(MONTH, a.balance_time, GETDATE()) < 12\n" +
                "                    AND DATEDIFF(MONTH, a.balance_time, GETDATE()) >= 0\n" +
                "          GROUP BY  customer_id,a.task_kind\n" +
                "          UNION\n" +
                "          SELECT    '配件' AS business_name, a.customer_id,\n" +
                "                    MAX(a.approve_time) AS last_date,\n" +
                "                    SUM(pos_price) AS three_money,\n" +
                "                    SUM(pos_quantity) AS three_count, 0 AS six_money,\n" +
                "                    0 AS six_count, 0 AS twelve_money, 0 AS twelve_count\n" +
                "          FROM      vw_stat_posdetail a\n" +
                "          WHERE     a.pos_type IN ( 1, 3 )\n" +
                "                    AND a.approve_status = 1\n" +
                "                    AND a.pos_type_meaning <> '维修出库'\n" +
                "                    AND DATEDIFF(MONTH, a.approve_time, GETDATE()) < 3\n" +
                "                    AND DATEDIFF(MONTH, a.approve_time, GETDATE()) >= 0\n" +
                "          GROUP BY  a.customer_id\n" +
                "          UNION\n" +
                "          SELECT    '配件' AS business_name, a.customer_id,\n" +
                "                    MAX(a.approve_time) AS last_date, 0 AS three_money,\n" +
                "                    0 AS three_count, SUM(pos_price) AS six_money,\n" +
                "                    SUM(pos_quantity) AS six_count, 0 AS twelve_money,\n" +
                "                    0 AS twelve_count\n" +
                "          FROM      vw_stat_posdetail a\n" +
                "          WHERE     a.pos_type IN ( 1, 3 )\n" +
                "                    AND a.approve_status = 1\n" +
                "                    AND a.pos_type_meaning <> '维修出库'\n" +
                "                    AND DATEDIFF(MONTH, a.approve_time, GETDATE()) < 6\n" +
                "                    AND DATEDIFF(MONTH, a.approve_time, GETDATE()) >= 0\n" +
                "          GROUP BY  a.customer_id\n" +
                "          UNION\n" +
                "          SELECT    '配件' AS business_name, a.customer_id,\n" +
                "                    MAX(a.approve_time) AS last_date, 0 AS three_money,\n" +
                "                    0 AS three_count, 0 AS six_money, 0 AS six_count,\n" +
                "                    SUM(pos_price) AS twelve_money,\n" +
                "                    SUM(pos_quantity) AS twelve_count\n" +
                "          FROM      vw_stat_posdetail a\n" +
                "          WHERE     a.pos_type IN ( 1, 3 )\n" +
                "                    AND a.approve_status = 1\n" +
                "                    AND a.pos_type_meaning <> '维修出库'\n" +
                "                    AND DATEDIFF(MONTH, a.approve_time, GETDATE()) < 12\n" +
                "                    AND DATEDIFF(MONTH, a.approve_time, GETDATE()) >= 0\n" +
                "          GROUP BY  a.customer_id\n" +
                "          UNION\n" +
                "          SELECT    '保险' AS business_name, a.customer_id,\n" +
                "                    MAX(a.approve_time) AS last_date,\n" +
                "                    SUM(a.insurance_income) AS three_money,\n" +
                "                    COUNT(*) AS three_count, 0 AS six_money, 0 AS six_count,\n" +
                "                    0 AS twelve_money, 0 AS twelve_count\n" +
                "          FROM      dbo.insurance a\n" +
                "          WHERE     a.approve_status = 1\n" +
                "                    AND DATEDIFF(MONTH, a.approve_time, GETDATE()) < 3\n" +
                "                    AND DATEDIFF(MONTH, a.approve_time, GETDATE()) >= 0\n" +
                "          GROUP BY  a.customer_id\n" +
                "          UNION\n" +
                "          SELECT    '保险' AS business_name, a.customer_id,\n" +
                "                    MAX(a.approve_time) AS last_date, 0 AS three_money,\n" +
                "                    0 AS three_count, SUM(a.insurance_income) AS six_money,\n" +
                "                    COUNT(*) AS six_count, 0 AS twelve_money,\n" +
                "                    0 AS twelve_count\n" +
                "          FROM      dbo.insurance a\n" +
                "          WHERE     a.approve_status = 1\n" +
                "                    AND DATEDIFF(MONTH, a.approve_time, GETDATE()) < 6\n" +
                "                    AND DATEDIFF(MONTH, a.approve_time, GETDATE()) >= 0\n" +
                "          GROUP BY  a.customer_id\n" +
                "          UNION\n" +
                "          SELECT    '保险' AS business_name, a.customer_id,\n" +
                "                    MAX(a.approve_time) AS last_date, 0 AS three_money,\n" +
                "                    0 AS three_count, 0 AS six_money, 0 AS six_count,\n" +
                "                    SUM(a.insurance_income) AS three_money,\n" +
                "                    COUNT(*) AS three_count\n" +
                "          FROM      dbo.insurance a\n" +
                "          WHERE     a.approve_status = 1\n" +
                "                    AND DATEDIFF(MONTH, a.approve_time, GETDATE()) < 12\n" +
                "                    AND DATEDIFF(MONTH, a.approve_time, GETDATE()) >= 0\n" +
                "          GROUP BY  a.customer_id ) a\n" +
                "WHERE   customer_id = '" + objectId + "'\n" +
                "GROUP BY a.customer_id, a.business_name\n", null);
    }

    @Override
    public PageModel getSaleClues(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
        return customerDao.getSaleClues(user, keyword, filter, sort, perPage, page);
    }

    @Override
    public Map<String, Object> getSaleClueDetail(String visitorNo) {
        List<Map<String, Object>> result = customerDao.getMapBySQL("SELECT * FROM presell_visitors a LEFT JOIN  vw_stat_effective_customer b ON a.visitor_id = b.object_id WHERE a.visitor_no = '" + visitorNo + "'", null);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public List<PresellVisitorsBack> getVisits(String customerId) {
		/*String sql = "SELECT back.*, customer.location_address AS visitor_location_address, customer.object_name AS visitor_name, customer.mobile AS visitor_mobile, customer.address AS visitor_address FROM effective_custome_call_back back\n" +
				"LEFT JOIN vw_stat_effective_customer customer ON back.customer_id = customer.object_id WHERE customer_id = '" + customerId + "'";
		List<EffectiveCustomeCallBackVO> list = customerDao.getEntityBySQL(sql, EffectiveCustomeCallBackVO.class, null);
		return list;*/
		/*String sql = "select back.* from presell_visitors_back back where back.object_id=:customerId";
		Map<String, Object> parm = new HashedMap(1);
		parm.put("customerId", customerId);
		return customerDao.getMapBySQL(sql,parm);*/
        return (List<PresellVisitorsBack>) customerDao.findByHql("from PresellVisitorsBack where objectId=?", customerId);
    }

    @Override
    public PageModel getCallbacks(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
        return customerDao.getCallbacks(user, keyword, filter, sort, perPage, page);
    }

    @Override
    public Map<String, Object> getCallbackDetail(String outStockNo) {
        List<Map<String, Object>> detailList = customerDao.getMapBySQL("SELECT b.out_stock_no, a.* FROM vehicle_out_stocks b LEFT JOIN vehicle_sale_contract_detail a ON a.contract_no = b.contract_no WHERE b.out_stock_no = '" + outStockNo + "'", null);
        Map<String, Object> contractDetail;
        if (detailList == null || detailList.size() == 0) {
            contractDetail = null;
        } else {
            contractDetail = detailList.get(0);
        }
        List<Map<String, Object>> callbackList = customerDao.getMapBySQL("SELECT * FROM seller_call_back WHERE out_stock_no = '" + outStockNo + "'", null);
        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("contract", contractDetail);
        result.put("callbackList", callbackList);
        return result;
    }

    @Override
    public PresellVisitorsBack addVisit(SysUsers user, String customerId, Map<String, Object> visitData) {
        PresellVisitorsBack callback = new PresellVisitorsBack();
        callback.setBackId(UUID.randomUUID().toString());
        callback.setObjectId(customerId);
        callback.setBacker(user.getUserFullName());
        callback.setOsType((short) 1);

        saveMapToObject(visitData, callback);
        if (StringUtils.isEmpty(callback.getObjectId())) {
            throw new ServiceException("客户跟进必须提交客户标识");
        }
//		callback.setBackPurpose(visitData.get("back_purpose").toString());
//		callback.setBackWay(visitData.get("back_way").toString());
//		callback.setPlanBackTime(Timestamp.valueOf(visitData.get("plan_back_time") + " 00:00:00"));
//		callback.setRealBackTime(Timestamp.valueOf(visitData.get("real_back_time") + " 00:00:00"));
//		callback.setBackContent(visitData.get("back_content").toString());
//		if(visitData.containsKey("address")) {
//			callback.setAddress(visitData.get("address").toString());
//		}
//		if(visitData.containsKey("pics")) {
//			callback.setPics(visitData.get("pics").toString());
//		}
        String pics = fileService.addPicsToFtp(user, callback.getBackId(), callback.getPics());
        callback.setPics(pics);
        customerDao.save(callback);
        // return customerDao.getMapBySQL("SELECT * FROM effective_custome_call_back WHERE customer_id = '" + customerId + "'", null);
        return callback;
    }

    @Override
    public void batchAddVistBySms(SysUsers user, List<String> customerIds, String content) {
        Timestamp now = TimestampUitls.getTime();
        for (String customerId : customerIds) {
            InterestedCustomers customer = customerDao.get(InterestedCustomers.class, customerId);
            if (null == customer) {
                throw new ServiceException(String.format("未找到指定的客户(%s)", customerId));
            }
            List<PresellVisitorsBack> backs = (List<PresellVisitorsBack>) customerDao.findByHql("from PresellVisitorsBack where objectId=? and realBackTime is null order by planBackTime", customerId);
            PresellVisitorsBack back = null;
            if (null != backs && !backs.isEmpty()) {
                back = backs.get(0);
            } else {
                back = new PresellVisitorsBack();
                back.setBackId(Tools.newGuid());
                back.setPlanBackTime(now);
                back.setBackSchedule("联系");
                back.setOsType((short) 1);
                back.setObjectId(customerId);
            }
            back.setBackWay("信息拜访");
            back.setBackId(user.getUserId());
            back.setBacker(user.getUserFullName());
            back.setRealBackTime(now);
            back.setBackContent(content);
            try {
                customerDao.update(back);
            } catch (Exception ex) {
                //
            }
        }
    }

    @Override
    public PresellVisitors addPreSalleClue(SysUsers user, String customerId, Map<String, Object> clue) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        PresellVisitors result = new PresellVisitors();
        saveMapToObject(clue, result);
        result.setCreator(user.getUserName() + "(" + user.getUserNo() + ")");
        result.setCreateTime(time);
        result.setVisitorNo(UUID.randomUUID().toString());
        result.setStationId(user.getDefaulStationId());
        result.setVisitorCount("1");
        // result.setVisitAddr(Short.valueOf("0"));
        result.setVisitorId(customerId);
        result.setSellerId(user.getUserId());
        result.setSeller(user.getUserName());

//		result.setVisitTime(Timestamp.valueOf(clue.get("visit_time")+ " 00:00:00"));
//		result.setVisitWay(clue.get("visit_way").toString());
//		result.setVisitorLevel(clue.get("visitor_level").toString());
//		result.setKnowWay(clue.get("know_way").toString());
//		if(clue.containsKey("back_way")) {
//			result.setBackWay(clue.get("back_way").toString());
//		}
//		if(clue.containsKey("back_place")) {
//			result.setBackPlace(clue.get("back_place").toString());
//		}
//		result.setVnoId(clue.get("vno_id").toString());
//		result.setVehicleVno(clue.get("vehicle_vno").toString());
//		result.setVehicleName(clue.get("vehicle_name").toString());
//		result.setVehicleColor(clue.get("vehicle_color").toString());
//		result.setVehicleKind(clue.get("vehicle_kind").toString());
//		result.setVehiclePrice(Double.valueOf(clue.get("vehicle_price").toString()));
//		result.setFactLoad(clue.get("fact_load").toString());
//		result.setPlanPurchaseTime(Timestamp.valueOf(clue.get("plan_purchase_time")+" 00:00:00"));
//		result.setPurchaseUse(clue.get("purchase_use").toString());

//		result.setFirstTalkComment(clue.get("first_talk_comment").toString());
//		result.setVisitResult(new Double(clue.get("visit_result").toString()).shortValue());
//		result.setEmphasis(clue.get("emphasis").toString());
//		result.setVehicleKind(clue.get("vehicle_kind").toString());
//		result.setDistance(clue.get("distance").toString());
//		result.setFactLoad(clue.get("fact_load").toString());
//		result.setSellerId(user.getUserId());
//		result.setVehicleSalesCode(clue.get("vehicle_vno").toString());
//		result.setDeliveryLocus(clue.get("delivery_locus").toString());
//		result.setBuyType((new Double(clue.get("buy_type").toString())).shortValue());
//		result.setRemark(clue.get("remark").toString());
//		result.setVnoId(clue.get("vno_id").toString());
//		result.setSubjectMatter(clue.get("subject_matter").toString());
//		result.setPurposeQuantity(new Integer(clue.get("purpose_quantity").toString()));
//		result.setIntentLevel(clue.get("intent_level").toString());
//		result.setIntentLevelBak(clue.get("intent_level_bak").toString());

        customerDao.save(result);
        return result;
    }

    @Override
    public PageModel getVehicleStore(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page) {
        return customerDao.getVehicleStore(user, keyword, filter, sort, perPage, page);
    }

    @Override
    public PresellVisitors updatePreSalleClue(SysUsers user, String saleClueId, Map<String, Object> postJson) {
        PresellVisitors clue = customerDao.get(PresellVisitors.class, saleClueId);
        saveMapToObject(postJson, clue);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        clue.setModifier(user.getUserName() + "(" + user.getUserNo() + ")");
        clue.setModifyTime(time);
        customerDao.update(clue);
        return clue;
    }

    @Override
    public PresellVisitorsBack addClueBack(SysUsers user, String saleClueId, Map<String, Object> postJson) {
        PresellVisitors visitors = customerDao.get(PresellVisitors.class, saleClueId);
        PresellVisitorsBack back = new PresellVisitorsBack();
        back.setVisitorNo(saleClueId);
        back.setBackId(UUID.randomUUID().toString());
        back.setBacker(user.getUserName() + "(" + user.getUserNo() + ")");
        back.setBeforeBackIntentLevel(visitors.getIntentLevel());
        back.setBeforeBackVisitorLevel(visitors.getVisitorLevel());
        back.setBackIntentLevel(visitors.getIntentLevel());
        back.setBackVisitorLevel(visitors.getVisitorLevel());
        back.setOsType((short) 1);
        saveMapToObject(postJson, back);
        customerDao.save(back);
        return back;
    }

    @Override
    public PresellVisitorsBack updateClueBack(SysUsers user, String backId, Map<String, Object> postJson) {
        PresellVisitorsBack back = customerDao.get(PresellVisitorsBack.class, backId);
        saveMapToObject(postJson, back);
        customerDao.update(back);
        return back;
    }

    @Override
    public PresellVisitorsBackVO getClueBack(SysUsers user, String backId) {
		/*List<PresellVisitorsBackVO> result1 = customerDao.getEntityBySQL("SELECT back.*, customer.location_address AS visitor_location_address, customer.object_name AS visitor_name, customer.mobile AS visitor_mobile, customer.address AS visitor_address FROM presell_visitors_back back\n" +
				"  LEFT JOIN presell_visitors visitor ON visitor.visitor_no = back.visitor_no\n" +
				"  LEFT JOIN vw_stat_effective_customer customer ON visitor.visitor_id = customer.object_id\n" +
				"WHERE back.back_id = '" + backId + "'", PresellVisitorsBackVO.class, null);*/
        String sql = String.format(getSaleClueBackSql, " WHERE back.back_id = '" + backId + "'");
        List<PresellVisitorsBackVO> result = customerDao.getEntityBySQL(sql, PresellVisitorsBackVO.class, null);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public PresellVisitorsBack checkClueBack(SysUsers user, String backId, String checkContent) {
        PresellVisitorsBack back = customerDao.get(PresellVisitorsBack.class, backId);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        back.setCheckContent(checkContent);
        back.setCheckTime(time);
        back.setChecker(user.getUserName() + "(" + user.getUserNo() + ")");
        return back;
    }

    @Override
    public SellerCallBack getCallback(SysUsers user, String backId) {
        return customerDao.get(SellerCallBack.class, backId);
    }

    @Override
    public SellerCallBack addCallback(SysUsers user, Map<String, Object> postJson) {
        SellerCallBack back = new SellerCallBack();
        back.setBacker(user.getUserName() + "(" + user.getUserNo() + ")");
        back.setBackId(UUID.randomUUID().toString());
        saveMapToObject(postJson, back);
        customerDao.save(back);
        return back;
    }

    @Override
    public SellerCallBack updateCallback(SysUsers user, String backId, Map<String, Object> postJson) {
        SellerCallBack back = customerDao.get(SellerCallBack.class, backId);
        saveMapToObject(postJson, back);
        customerDao.update(back);
        return back;
    }

    public BaseRelatedObjects updateBaseRelatedObjectsBase(SysUsers user, String customerId, Map<String, Object> postJson) {
        BaseRelatedObjects data = customerDao.get(BaseRelatedObjects.class, customerId);
        if (data != null) {
            saveUpdateCustomerHistory(user, data, postJson);
            saveMapToObject(postJson, data);
            customerDao.update(data);
        }
        return data;
    }

    private void saveUpdateCustomerHistory(SysUsers user, BaseRelatedObjects obj, Map<String, Object> jsonData) {
        Class objClass = obj.getClass();
        Method[] methods = objClass.getMethods();
        for (String key : jsonData.keySet()) {
            if (updateFields.containsKey(key)) {
                String methodName = "get" + underline2Camel(key, false);
                try {
                    for (Method fun : methods) {
                        if (!fun.getName().equals(methodName)) {
                            continue;
                        }
                        Object valueBefore = fun.invoke(obj);
                        Object valueAfter = jsonData.get(key);
                        if (valueBefore == null && valueAfter == null) {
                            continue;
                        }
                        if (valueBefore == null || valueAfter == null || !fun.invoke(obj).toString().equals(jsonData.get(key).toString())) {
                            BaseRelatedObjectHistory history = new BaseRelatedObjectHistory();
                            history.setHistoryId(UUID.randomUUID().toString());
                            history.setObjectId(obj.getObjectId());
                            history.setFieldName(key);
                            history.setFieldNameMeaning(updateFields.get(key));
                            history.setValueBefore(valueBefore == null ? null : valueBefore.toString());
                            history.setValueAfter(valueAfter == null ? null : valueAfter.toString());
                            history.setModifier(String.format("%s(%s)", user.getUserName(), user.getUserNo()));
                            history.setModifyTime(new Timestamp(System.currentTimeMillis()));
                            history.setStationId(user.getDefaulStationId());
                            customerDao.save(history);
                            break;
                        }
                    }
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public InterestedCustomers updateCustomerBase(SysUsers user, String customerId, Map<String, Object> postJson) {
        InterestedCustomers data = customerDao.get(InterestedCustomers.class, customerId);
        if (data != null) {
            saveMapToObject(postJson, data);
            customerDao.update(data);
        }
        updateBaseRelatedObjectsBase(user, customerId, postJson);
        String pics = fileService.addPicsToFtp(user, data.getObjectId(), data.getPics());
        data.setPics(pics);
        return data;
    }

    @Override
    public CustomerRetainVehicleOverview addVehicleOverview(SysUsers user, Map<String, Object> postJson) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        CustomerRetainVehicleOverview data = new CustomerRetainVehicleOverview();
        saveMapToObject(postJson, data);
        data.setSelfId(UUID.randomUUID().toString());
        data.setCreator(user.getUserName() + "(" + user.getUserNo() + ")");
        data.setCreateTime(time);
        customerDao.save(data);
        return data;
    }

    @Override
    public CustomerRetainVehicleOverview updateVehicleOverview(SysUsers user, String selfId, Map<String, Object> postJson) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        CustomerRetainVehicleOverview data = customerDao.get(CustomerRetainVehicleOverview.class, selfId);
        saveMapToObject(postJson, data);
        data.setModifier(user.getUserName() + "(" + user.getUserNo() + ")");
        data.setModifyTime(time);
        customerDao.save(data);
        return data;
    }

    @Override
    public CustomerOrganizationalStructure addOrganizational(SysUsers user, Map<String, Object> postJson) {
        CustomerOrganizationalStructure data = new CustomerOrganizationalStructure();
        saveMapToObject(postJson, data);
        data.setSelfId(UUID.randomUUID().toString());
        customerDao.save(data);
        return data;
    }

    @Override
    public CustomerOrganizationalStructure updateOrganizational(SysUsers user, String selfId, Map<String, Object> postJson) {
        CustomerOrganizationalStructure data = customerDao.get(CustomerOrganizationalStructure.class, selfId);
        saveMapToObject(postJson, data);
        customerDao.save(data);
        return data;
    }

    @Override
    public VehicleArchives addVehicleArchives(SysUsers user, Map<String, Object> postJson) {
        VehicleArchives data = new VehicleArchives();
        saveMapToObject(postJson, data);
        data.setVehicleId(UUID.randomUUID().toString());
        customerDao.save(data);
        return data;
    }

    @Override
    public VehicleArchives updateVehicleArchives(SysUsers user, String selfId, Map<String, Object> postJson) {
        VehicleArchives data = customerDao.get(VehicleArchives.class, selfId);
        saveMapToObject(postJson, data);
        customerDao.save(data);
        return data;
    }

//    @Override
//    public BaseRelatedObjects storeToBaseRelatedObjectsBase(SysUsers user, Map<String, Object> postJson, InterestedCustomers customers) {
//        BaseRelatedObjects data;
//
//        if (customers.getRelatedObjectId() != null) {
//            data = customerDao.get(BaseRelatedObjects.class, customers.getRelatedObjectId());
//            if (data != null) {
//                return data;
//            }
//        }
//        List<BaseRelatedObjects> result = null;
//        if (customers.getObjectNature() == 10) {
//            if (customers.getCertificateNo() != null) {
//                result = customerDao.getEntityBySQL("SELECT * FROM base_related_objects \n" +
//                        "WHERE certificate_no = '" + customers.getCertificateNo() + "'", BaseRelatedObjects.class, null);
//            }
//            if ((result == null || result.size() == 0) && customers.getObjectName() != null) {
//                result = customerDao.getEntityBySQL("SELECT * FROM base_related_objects \n" +
//                        "WHERE object_name = '" + customers.getObjectName() + "'", BaseRelatedObjects.class, null);
//            }
//        } else {
//            if (customers.getCertificateNo() != null) {
//                result = customerDao.getEntityBySQL("SELECT * FROM base_related_objects \n" +
//                        "WHERE certificate_no = '" + customers.getCertificateNo() + "'", BaseRelatedObjects.class, null);
//            }
//            if ((result == null || result.size() == 0) && (customers.getObjectName() != null && customers.getObjectName() != null)) {
//                result = customerDao.getEntityBySQL("SELECT * FROM base_related_objects \n" +
//                        "WHERE object_name = '" + customers.getObjectName() + "' AND mobile = '" + customers.getMobile() + "'", BaseRelatedObjects.class, null);
//            }
//        }
//        if (result != null && result.size() > 0) {
//            data = result.get(0);
//            customers.setRelatedObjectId(data.getObjectId());
//            customerDao.update(customers);
//            return data;
//        }
//
//        if (customers.getCertificateNo() == null || customers.getCertificateNo().length() == 0) {
//            throw new ServiceException("客户的证件号码不能为空");
//        }
//
//        if (customers.getMobile() == null || customers.getMobile().length() == 0) {
//            throw new ServiceException("客户的电话号码不能为空");
//        }
//
//        if (customers.getObjectName() == null || customers.getObjectName().length() == 0) {
//            throw new ServiceException("客户名称不能为空");
//        }
//
//        data = new BaseRelatedObjects();
//
//        saveMapToObject(customerDao.toMap(customers), data);
//        // customerDao.saveMapToObject();
//        Timestamp time = new Timestamp(System.currentTimeMillis());
//        data.setObjectId(customers.getObjectId());
//        if (data.getParentId() != null) {
//            data.setFullId(data.getParentId() + "." + data.getObjectId());
//        } else {
//            data.setFullId(data.getObjectId());
//        }
//        data.setCreator(user.getUserName() + "(" + user.getUserNo() + ")");
//        if (data.getCreateStationId() == null) {
//            data.setCreateStationId(user.getDefaulStationId());
//        }
//        data.setCreateTime(time);
//        if (data.getStationId() == null) {
//            data.setStationId(user.getDefaulStationId());
//        }
//        if (data.getIsParent() == null) {
//            data.setIsParent(false);
//        }
//        if (data.getStatus() == null) {
//            data.setStatus((short) 1);
//        }
//        // data.setObjectNo(customers.getObjectNo());
//
//        if (postJson != null) {
//            saveMapToObject(postJson, data);
//        }
//        data.setNamePinyin(GetChineseFirstChar.getFirstLetter(data.getObjectName()));
//        customerDao.save(data);
//
//        customers.setRelatedObjectId(data.getObjectId());
//        customerDao.update(customers);
//
////		BaseRelatedObjectMaintenace maintenace = new BaseRelatedObjectMaintenace();
////		maintenace.setObjectMaintenaceId(UUID.randomUUID().toString());
////		maintenace.setObjectId(data.getObjectId());
////		maintenace.setStationId(user.getDefaulStationId());
////		maintenace.setBusiness(10);
////		maintenace.setUserId(user.getUserId());
////		customerDao.save(maintenace);
//
//        return data;
//    }

    @Override
    public InterestedCustomers addCustomerBase(SysUsers user, Map<String, Object> postJson) {
        InterestedCustomers data = new InterestedCustomers();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        data.setObjectId(UUID.randomUUID().toString());
        // data.setFullId(data.getObjectId());
        data.setCreator(user.getUserName() + "(" + user.getUserNo() + ")");
        data.setCreateStationId(user.getDefaulStationId());
        data.setCreateTime(time);
        data.setStationId(user.getDefaulStationId());
        // data.setIsParent(false);
        data.setStatus((short) 1);
        data.setObjectNo(getAutoNoOfObject());
        saveMapToObject(postJson, data);
        data.setNamePinyin(GetChineseFirstChar.getFirstLetter(data.getObjectName()));
        data.setOsType((short) 1);
        customerDao.save(data);

//		BaseRelatedObjectMaintenace maintenace = new BaseRelatedObjectMaintenace();
//		maintenace.setObjectMaintenaceId(UUID.randomUUID().toString());
//		maintenace.setObjectId(data.getObjectId());
//		maintenace.setStationId(user.getDefaulStationId());
//		maintenace.setBusiness(10);
//		maintenace.setUserId(user.getUserId());
//		customerDao.save(maintenace);
        // storeToBaseRelatedObjectsBase(user, postJson, data);
        //setCustomerMaintenance(user, data.getObjectId(), user.getUserId());
        data.setMaintainerId(user.getUserId());
        data.setMaintainerName(user.getUserName());
        String pics = fileService.addPicsToFtp(user, data.getObjectId(), data.getPics());
        data.setPics(pics);
        return data;
    }

    @Override
    public PresellVisitorsBack updateVisit(SysUsers user, String backId, Map<String, Object> postJson) {
        PresellVisitorsBack data = customerDao.get(PresellVisitorsBack.class, backId);
        if (null == data) {
            throw new ServiceException(String.format("未找到客户跟进信息(%s)", backId));
        }
        saveMapToObject(postJson, data);
        if (StringUtils.isEmpty(data.getObjectId())) {
            throw new ServiceException("客户跟进必须提交客户标识");
        }
        String pics = fileService.addPicsToFtp(user, data.getBackId(), data.getPics());
        data.setPics(pics);
        customerDao.save(data);
        return data;
    }


    @Override
    public PageModel getMyCalendar(SysUsers user, Map<String, Object> condition, int pageSize, int pageNo) {
        return customerDao.getMyCalendar(user, condition, pageSize, pageNo);
    }


    @Override
    public List<Map<String, Object>> getMyCalendarCount(SysUsers user, Map<String, Object> condition) {
        return customerDao.getMyCalendarCount(user, condition);
    }

    @Override
    public Map<String, Object> getCustomerMap(SysUsers user, Map<String, Object> condition) {
        String province = condition.containsKey("province") && condition.get("province") != null ? condition.get("province").toString() : null;
        if (province != null && province.length() == 0) {
            province = null;
        }
        String city = condition.containsKey("city") && condition.get("city") != null ? condition.get("city").toString() : null;
        if (city != null && city.length() == 0) {
            city = null;
        }
        String area = condition.containsKey("area") && condition.get("area") != null ? condition.get("area").toString() : null;
        if (area != null && area.length() == 0) {
            area = null;
        }
        String profession = condition.containsKey("profession") && condition.get("profession") != null ? condition.get("profession").toString() : null;
        if (profession != null && profession.length() == 0) {
            profession = null;
        }
        String stationId = condition.containsKey("stationId") && condition.get("stationId") != null ? condition.get("stationId").toString() : null;

        boolean onlySelf = condition.containsKey("onlySelf") && condition.get("onlySelf") != null ? ((boolean) (condition.get("onlySelf"))) : true;
        return customerDao.getCustomerMap(user, province, city, area, profession, onlySelf, stationId);
    }

    @Override
    public InterestedCustomers getInterestedCustomers(String objectId) {
        return customerDao.get(InterestedCustomers.class, objectId);
    }

    @Override
    public BaseRelatedObjects getBaseRelatedObjects(String objectId) {
        return customerDao.get(BaseRelatedObjects.class, objectId);
    }


    @Override
    public PageModel<Object> listRelatedObjects(HashMap filterMap, int pageNo, int pageSize) {
        String condition = buildCondition(filterMap);
        String sql = "SELECT a.* from dbo.base_related_objects a WHERE 1=1 ";
        sql = sql + (StringUtils.isBlank(condition) ? "" : " AND  " + condition);
        return customerDao.listInSql(sql, null, pageNo, pageSize, null);
    }

    private String buildCondition(Map<String, Object> filterMap) {
        if (filterMap == null) {
            return null;
        }
        List<String> conditionArray = new ArrayList<String>(4);

        if (filterMap.get("keyword") != null && StringUtils.isNotBlank(filterMap.get("keyword").toString())) {
            conditionArray.add(MessageFormat.format("(a.object_name LIKE ''%{0}%'' OR a.short_name LIKE ''%{0}%'' OR a.mobile LIKE ''%{0}%'' OR a.phone LIKE ''%{0}%'')", filterMap.get("keyword").toString()));
        }

        return " " + StringUtils.join(conditionArray, " AND ") + " ";

    }


    /**
     * 生成往来对象的编码
     *
     * @return
     */
    private String getAutoNoOfObject() {
        String no = customerDao.getMaxNoOfRelatedObject();
        SimpleDateFormat nodf = new SimpleDateFormat("yyyy-MM-dd");
        int maxNo = 0;
        if (no != null && no.length() > 8) {
            maxNo = Integer.parseInt(no.substring(8, no.length()));
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(4);
        nf.setGroupingUsed(false);
        String value = nodf.format(new Date()).replaceAll("-", "")
                + nf.format(maxNo + 1);
        return value;
    }


    private static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index));
            } else {
                sb.append(word.substring(1));
            }
        }
        return sb.toString();
    }

    private void saveMapToObject(Map<String, Object> jsonData, Object obj) {
        Class objClass = obj.getClass();
        Method[] methods = objClass.getMethods();
        for (String key : jsonData.keySet()) {
            String methodName = "set" + underline2Camel(key, false);
            try {
                for (Method fun : methods) {
                    if (!fun.getName().equals(methodName)) {
                        continue;
                    }

                    Class[] params = fun.getParameterTypes();
                    if (params[0].equals(Double.class) || params[0].equals(double.class)) {
                        fun.invoke(obj, new Double(jsonData.get(key).toString()));
                    } else if (params[0].equals(Integer.class) || params[0].equals(int.class)) {
                        fun.invoke(obj, new Double(jsonData.get(key).toString()).intValue());
                    } else if (params[0].equals(Short.class) || params[0].equals(short.class)) {
                        fun.invoke(obj, new Double(jsonData.get(key).toString()).shortValue());
                    } else if (params[0].equals(Float.class) || params[0].equals(float.class)) {
                        fun.invoke(obj, new Double(jsonData.get(key).toString()).floatValue());
                    } else if (params[0].equals(Timestamp.class)) {
                        String value = jsonData.get(key).toString();
                        if (value.length() == 10) {
                            value += " 00:00:00";
                        }
                        fun.invoke(obj, Timestamp.valueOf(value));
                    } else if (params[0].equals(Boolean.class) || params[0].equals(boolean.class)) {
                        fun.invoke(obj, Boolean.valueOf(jsonData.get(key).toString()));
                    } else {
                        fun.invoke(obj, jsonData.get(key).toString());
                    }
                    break;
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Autowired
    private BaseDao baseDao;

    private static final String CHECK_OBJECT1 =
            "SELECT object_id as object_id,object_name as object_name,mobile,certificate_no,short_name,creator,create_time,\n" +
                    " a.maintainer_name AS workgroup_name ,\n" +
                    " a.maintainer_id AS workgroup_name_id\n" +
                    " FROM base_related_objects a WHERE (a.object_name=:objectName OR a.mobile=:mobile OR a.short_name=:objectShortName \n" +
                    "                      OR (a.certificate_no IS NOT NULL AND a.certificate_no<>'' AND a.certificate_no=:certificateNo)) AND isnull(status,0)=1";

    private static final String CHECK_OBJECT2 = "SELECT object_id,object_name,object_nature,mobile,certificate_no,short_name,creator,create_time,\n" +
            " a.maintainer_name AS workgroup_name ,\n" +
            " a.maintainer_id AS workgroup_name_id\n" +
            "                    FROM base_related_objects a WHERE (a.object_name=:objectName OR a.mobile=:mobile OR (a.certificate_no IS NOT NULL \n" +
            "                    AND a.certificate_no<>'' AND a.certificate_no=:certificateNo)) AND isnull(status,0)=1";

    @Override
    public BaseRelatedObjects createRelatedObject(String objectId, boolean force) {
        if (StringUtils.isEmpty(objectId)) {
            throw new ServiceException("objectId不能为空");
        }
        InterestedCustomers interestedCustomer = customerDao.get(InterestedCustomers.class, objectId);
        if (null == interestedCustomer) {
            throw new ServiceException(String.format("意向客户(%s)不存在", objectId));
        }
        BaseRelatedObjects relatedObject = null;
        if (StringUtils.isNotEmpty(interestedCustomer.getRelatedObjectId())) {
            relatedObject = customerDao.get(BaseRelatedObjects.class, interestedCustomer.getRelatedObjectId());
        }
        if (null != relatedObject) {
            return relatedObject;
        }

        this.validateInterestedCustomer(interestedCustomer, force, "生成客户档失败！");
        return addRelatedObject(interestedCustomer);
    }

    @Override
    public boolean existsRelatedObject(String objectId) {
        InterestedCustomers interestedCustomer = customerDao.get(InterestedCustomers.class, objectId);
        if (null == interestedCustomer) {
            throw new ServiceException(String.format("意向客户(%s)不存在", objectId));
        }
        if (StringUtils.isEmpty(interestedCustomer.getRelatedObjectId())) {
            return false;
        }
        BaseRelatedObjects relatedObject = customerDao.get(BaseRelatedObjects.class, interestedCustomer.getRelatedObjectId());
        if (null != relatedObject) {
            return true;
        } else {
            return false;
        }
    }

    private void validateInterestedCustomer(InterestedCustomers interestedCustomer, boolean force, String errMsg) {
        if (StringUtils.isEmpty(interestedCustomer.getObjectName())) {
            throw new ServiceException(String.format("%s客户名称不能为空", errMsg));
        }
        //性质
        if (null == interestedCustomer.getObjectNature()) {
            throw new ServiceException(String.format("%s客户性质不能为空", errMsg));
        } else {
            if (InterestedCustomersService.OBJECT_NATURE_UNIT == interestedCustomer.getObjectNature()) {    //客户性质为单位
                if (StringUtils.isEmpty(interestedCustomer.getShortName())) {
                    throw new ServiceException(String.format("%s客户性质为‘单位’时客户简称不能为空", errMsg));
                }
                if ("是".equals(sysOptionsDao.getOptionForString("CUSTOMER_CERTIFICATE_NO_MUST_FILL"))) {
                    if (StringUtils.isEmpty(interestedCustomer.getCertificateType())) {
                        throw new ServiceException(String.format("%s客户性质为‘单位’时客户的证件类型不能为空", errMsg));
                    }
                    if (StringUtils.isEmpty(interestedCustomer.getCertificateNo())) {
                        throw new ServiceException(String.format("%s客户性质为‘单位’时客户的证件号不能为空", errMsg));
                    }
                }
            }
        }
        if (StringUtils.isEmpty(interestedCustomer.getObjectProperty())) {
            throw new ServiceException(String.format("%s客户地位不能为空", errMsg));
        }
        if (StringUtils.isEmpty(interestedCustomer.getCustomerKind())) {
            throw new ServiceException(String.format("%s客户类别不能为空", errMsg));
        }
        if (StringUtils.isEmpty(interestedCustomer.getProfession())) {
            throw new ServiceException(String.format("%s客户行业不能为空", errMsg));
        }
        if (StringUtils.isEmpty(interestedCustomer.getMobile())) {
            throw new ServiceException(String.format("%s客户移动电话不能为空", errMsg));
        }
//        if (StringUtils.isEmpty(interestedCustomer.getLinkman())) {
//            throw new ServiceException(String.format("%s客户联系人不能为空", errMsg));
//        }
        if (StringUtils.isEmpty(interestedCustomer.getProvince())) {
            throw new ServiceException(String.format("%s客户所属的省市区不能为空", errMsg));
        }
        if (StringUtils.isEmpty(interestedCustomer.getAddress())) {
            throw new ServiceException(String.format("%s客户详细地址不能为空", errMsg));
        }
        if (StringUtils.isEmpty(interestedCustomer.getCustomerSource())) {
            throw new ServiceException(String.format("%s客户来源不能为空", errMsg));
        }
        //名称
        String objectName = interestedCustomer.getObjectName();
        //简称
        String objectShortName = interestedCustomer.getShortName();
        String mobile = interestedCustomer.getMobile();
        String certificateNo = interestedCustomer.getCertificateNo();

        short objectNature = Tools.toShort(interestedCustomer.getObjectNature(), (short) 10);
        List<Map<String, Object>> list = null;
        if (objectNature == InterestedCustomersService.OBJECT_NATURE_UNIT) {    //如果为单位，对象名称不允许重发
            Map<String, Object> parm = new HashMap<>();
            parm.put("objectName", objectName);
            parm.put("objectShortName", objectShortName);
            parm.put("mobile", mobile);
            parm.put("certificateNo", certificateNo);
            list = customerDao.getMapBySQL(CHECK_OBJECT1, parm);

        } else {
            Map<String, Object> parm = new HashMap<>();
            parm.put("objectName", objectName);
            parm.put("mobile", mobile);
            parm.put("certificateNo", certificateNo);
            list = customerDao.getMapBySQL(CHECK_OBJECT2, parm);
        }
        if (null != list && !list.isEmpty()) {
            for (Map<String, Object> map : list) {

                //校验证件号
                String mCertificate = null == map.get("certificate_no") ? null : map.get("certificate_no").toString();
                if (StringUtils.isNotEmpty(mCertificate) && StringUtils.equals(certificateNo, mCertificate)) {
                    if (!"东贸版".equals(sysOptionsDao.getOptionForString(InterestedCustomersService.DV_VERSION)) ||
                            (!InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER.equals(certificateNo) && !InterestedCustomersService.DEFAULT_CER_NO_UNIT.equals(certificateNo))) {
                        throw new ServiceException(String.format("%s 证件号码'%s'重复！", errMsg, mCertificate));
                    }
                }
                String sObjectName = null == map.get("object_name") ? null : map.get("object_name").toString();
                if (objectNature == InterestedCustomersService.OBJECT_NATURE_UNIT) { //只有单位客户才校验名称和简称
                    //校验名称
                    if (StringUtils.isNotEmpty(sObjectName) && StringUtils.equals(objectName, sObjectName)) {
                        throw new ServiceException(String.format("%s 客户名称重复！", errMsg));
                    }
                    //校验简称
                    String mShortName = null == map.get("short_name") ? null : map.get("short_name").toString();
                    if (StringUtils.isNotEmpty(mShortName) && StringUtils.equals(objectShortName, mShortName)) {
                        throw new ServiceException(String.format("%s 客户简称重复!", errMsg));
                    }
                }
                //校验手机号
                String mMobile = null == map.get("mobile") ? null : map.get("mobile").toString();
                if (StringUtils.isNotEmpty(mMobile) && StringUtils.equals(mobile, mMobile) && !force) {
                    String sInfo = "已存在相同移动电话的客户! 已存在客户名称:" + sObjectName + ", 建档人:" + map.get("creator");
                    if (null != map.get("workgroup_name") && StringUtils.isNotEmpty(map.get("workgroup_name").toString())) {
                        sInfo += ", 维系人:" + map.get("workgroup_name");
                    }
                    sInfo += "！ 您确定要生成客户档案吗？";
                    throw new ServiceException(50, sInfo);
                }
            }
        }
    }

    private BaseRelatedObjects addRelatedObject(InterestedCustomers interestedCustomer) {
        BaseRelatedObjects object = new BaseRelatedObjects();
        object.setClientType(HttpSessionStore.getSessionOs()); //记录新增时的客户端类型
        if (StringUtils.isEmpty(interestedCustomer.getShortName())) {
            //如果简称为空，默认为客户名称
            interestedCustomer.setShortName(interestedCustomer.getObjectName());
        }

        //设置默认的证件号码 ADM19010009
        interestedCustomersService.fillDefaultCerNo(interestedCustomer);

        Field[] objectFields = BaseRelatedObjects.class.getDeclaredFields();
        Field[] customerFields = interestedCustomer.getClass().getDeclaredFields();
        Map<String, Field> fieldNames = new HashMap<>();
        for (Field field : customerFields) {
            fieldNames.put(field.getName(), field);
        }
        for (Field field : objectFields) {
            if (fieldNames.containsKey(field.getName())) {
                Field field1 = fieldNames.get(field.getName());
                field1.setAccessible(true);
                Object value = null;
                try {
                    value = field1.get(interestedCustomer);
                } catch (Exception e) {
                    throw new ServiceException(String.format("获取%s.%s的值时出错", interestedCustomer.getClass().getSimpleName(), field.getName()));
                }
                try {
                    field.setAccessible(true);
                    field.set(object, value);
                } catch (Exception e) {
                    throw new ServiceException(String.format("设置%s.%s的值(%s)时出错", object.getClass().getSimpleName(), field.getName(), value));
                }
            }
        }

        object.setFullId(interestedCustomer.getObjectId());
        object.setIsParent(false);
        SysUsers user = HttpSessionStore.getSessionUser();
        object.setCreateStationId(user.getDefaulStationId());

        interestedCustomer.setRepeatCustomerName(null);
        interestedCustomer.setRepeatMobile(null);
        interestedCustomer.setRepeatCreator(null);
        interestedCustomer.setRepeatCreateTime(null);
        interestedCustomer.setRelatedObjectId(interestedCustomer.getObjectId());
        customerDao.update(interestedCustomer);
        customerDao.update(object);
        return object;
    }

    @Override
    public PageModel<Object> visitSummary(String type, Map<String, Object> filterMap, int pageNo, int pageSize) {
        if ("1".equals(type)) {    //交车3天后未跟进的客户
            return customerDao.getSellerCallBackWarning("2", filterMap, null, pageNo, pageSize);
        } else if ("2".equals(type)) {    //逾期未跟进的客户
            return customerDao.getOverdueCallBack(filterMap, null, pageNo, pageSize);
        } else if ("3".equals(type)) {    //交车3天内需要跟进的客户
            return customerDao.getSellerCallBackWarning("1", filterMap, null, pageNo, pageSize);
        } else if ("4".equals(type)) {    //超过3个月未跟进的客户
            return customerDao.getCallBackWarning(3 * 30, 6 * 30 - 1, filterMap, null, pageNo, pageSize);
        } else if ("5".equals(type)) {    //超过6个月未跟进的客户
            return customerDao.getCallBackWarning(6 * 30, 3 * 365 - 1, filterMap, null, pageNo, pageSize);
        } else if ("6".equals(type)) {    //超过3年未跟进的客户
            return customerDao.getCallBackWarning(3 * 365, 500 * 365, filterMap, null, pageNo, pageSize);
        } else if ("7".equals(type)) { //待检查的新增客户
            return customerDao.getPendingCheckForNewCustomer(filterMap, null, pageNo, pageSize);
        } else if ("8".equals(type)) { //待检查的跟进客户
            return customerDao.getPendingCheck(filterMap, null, pageNo, pageSize);
        } else if ("9".equals(type)) { //超过1个月未跟进的客户
            return customerDao.getCallBackWarning(1 * 30, 3 * 30 - 1, filterMap, null, pageNo, pageSize);
        }else {
            throw new ServiceException(String.format("无效的统计类型(%s)", type));
        }
    }

    public Map<String, Long> visitSummaryForTotal(){
        Map<String, Long> result = new HashMap<String, Long>(8);
        PageModel<Object> pageModel = customerDao.getSellerCallBackWarningForTotal("2");
        result.put("1", pageModel.getTotalSize());
        pageModel = customerDao.getOverdueCallBackForTotal();
        result.put("2", pageModel.getTotalSize());
        pageModel = customerDao.getSellerCallBackWarningForTotal("1");
        result.put("3", pageModel.getTotalSize());
        pageModel = customerDao.getCallBackWarningForTotal(3 * 30, 6 * 30 - 1);
        result.put("4", pageModel.getTotalSize());
        pageModel = customerDao.getCallBackWarningForTotal(6 * 30, 3 * 365 - 1);
        result.put("5", pageModel.getTotalSize());
        pageModel = customerDao.getCallBackWarningForTotal(3 * 365, 500 * 365);
        result.put("6", pageModel.getTotalSize());
        pageModel = customerDao.getPendingCheckForNewCustomerForTotal();
        result.put("7", pageModel.getTotalSize());
        pageModel = customerDao.getPendingCheckForTotal();
        result.put("8", pageModel.getTotalSize());
        pageModel = customerDao.getCallBackWarningForTotal(1 * 30, 3 * 30 - 1);
        result.put("9", pageModel.getTotalSize());
        return result;
    }

    @Override
    public Map<String, Object> checkVisit(String backId, String type, String content) {
        if ("0".equals(type)) {
            PresellVisitorsBack back = customerDao.get(PresellVisitorsBack.class, backId);
            if (null == back) {
                throw new ServiceException(String.format("未找到待检查的跟进信息(%s)", backId));
            }
            if (null != back.getCheckTime()) {
                throw new ServiceException("当前跟进信息已检查，请刷新后再试");
            }
            SysUsers user = HttpSessionStore.getSessionUser();
            back.setChecker(user.getUserName());
            back.setCheckTime(TimestampUitls.getTime());
            back.setCheckContent(content);
            customerDao.update(back);
            Map<String, Object> rtn = new HashMap<>(4);
            rtn.put("back_id", back.getBackId());
            rtn.put("checker", back.getBacker());
            rtn.put("check_time", back.getCheckTime());
            rtn.put("check_content", back.getCheckContent());
            return rtn;
        } else if ("1".equals(type)) {
            EffectiveCustomeCallBack back = customerDao.get(EffectiveCustomeCallBack.class, backId);
            if (null == back) {
                throw new ServiceException(String.format("未找到待检查的跟进信息(%s)", backId));
            }
            if (null != back.getCheckTime()) {
                throw new ServiceException("当前跟进信息已检查，请刷新后再试");
            }
            SysUsers user = HttpSessionStore.getSessionUser();
            back.setChecker(user.getUserName());
            back.setCheckTime(TimestampUitls.getTime());
            back.setCheckContent(content);
            customerDao.update(back);
            Map<String, Object> rtn = new HashMap<>(4);
            rtn.put("back_id", back.getBackId());
            rtn.put("checker", back.getBacker());
            rtn.put("check_time", back.getCheckTime());
            rtn.put("check_content", back.getCheckContent());
            return rtn;
        } else {
            throw new ServiceException(String.format("无效的跟进类型(%s)", type));
        }
    }

    @Override
    public List<Map<String, Object>> getCustomerInformation(String customerId, int type) {
        if (StringUtils.isEmpty(customerId)) {
            throw new ServiceException("必须提供客户标识");
        }
        List<Map<String, Object>> collection = new ArrayList<>();
        if (0 == type || 1 == type) {    //合同
            collection.addAll(customerDao.getCustomerInformationWithContract(customerId));
        }
        if (0 == type || 5 == type) {    //报价单
            collection.addAll(customerDao.getCustomerInformationWithQuotation(customerId));
        }
        if (0 == type || 10 == type) {    //跟进
            collection.addAll(customerDao.getCustomerInformationWithCallBack(customerId));
        }
        if (0 == type || 11 == type) {    //待跟进
            if (0 == type) {
                collection.addAll(customerDao.getCustomerInformationWithPendingCallBack2(customerId));
            } else {
                collection.addAll(customerDao.getCustomerInformationWithPendingCallBack1(customerId));
            }
        }
        if (0 == type || 15 == type) {    //跟进逾期
            collection.addAll(customerDao.getCustomerInformationWithOverdueCallBack(customerId));
        }
        if (!collection.isEmpty()) {
            Collections.sort(collection, new ComparatorTime("time"));
        }
        return collection;
    }

    class ComparatorTime implements Comparator<Map<String, Object>> {
        private String field;

        private ComparatorTime() {
        }

        public ComparatorTime(String field) {
            this.field = field;
        }

        @Override
        public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
            if (null != arg0) {
                String value1, value2 = null;
                value1 = null == arg0.get(field) ? null : arg0.get(field).toString();
                value2 = null == arg1 || null == arg1.get(field) ? null : arg1.get(field).toString();
                return value1.compareTo(value2) * -1;
            }
            return -1;
        }
    }
}
