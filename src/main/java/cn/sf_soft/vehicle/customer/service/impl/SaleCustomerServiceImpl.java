package cn.sf_soft.vehicle.customer.service.impl;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.sf_soft.vehicle.customer.dao.SysOptionsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sf_soft.basedata.dao.BaseOthersDao;
import cn.sf_soft.basedata.dao.SysIdentityDao;
import cn.sf_soft.basedata.model.BaseOthers;
import cn.sf_soft.basedata.model.SysFlags;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.util.GetChineseFirstChar;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.finance.voucher.dao.AcctItemDao;
import cn.sf_soft.finance.voucher.model.AcctItem;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.dao.SaleCustomerDao;
import cn.sf_soft.vehicle.customer.model.AddCustomerInitData;
import cn.sf_soft.vehicle.customer.model.AddIntentClueInitData;
import cn.sf_soft.vehicle.customer.model.AddValidClueInitData;
import cn.sf_soft.vehicle.customer.model.CustomerVO;
import cn.sf_soft.vehicle.customer.model.BaseVehicleVisitorsBack;
import cn.sf_soft.vehicle.customer.model.InterestedCustomers;
import cn.sf_soft.vehicle.customer.model.CustomerMaintenanceWorkgroup;
import cn.sf_soft.vehicle.customer.model.CustomerRetainVehicle;
import cn.sf_soft.vehicle.customer.model.LastPresellVisitors;
import cn.sf_soft.vehicle.customer.model.ObjectOfPlace;
import cn.sf_soft.vehicle.customer.model.OjbectNameForCheck;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsVO;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsBack;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsFail;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsForCheck;
import cn.sf_soft.vehicle.customer.model.UserMaintenanceWorkgroups;
import cn.sf_soft.vehicle.customer.model.VehicleType;
import cn.sf_soft.vehicle.customer.service.SaleCustomerService;

/**
 * 意向客户service
 *
 * @author cw
 * @Title: SaleCustomerServiceImpl.java
 * @date 2013-12-26 下午04:41:39
 */
@Service("saleCustomerService")
public class SaleCustomerServiceImpl implements SaleCustomerService {
    @Autowired
    private SaleCustomerDao dao;
    @Autowired
    private BaseOthersDao baseOthersDao;
    @Autowired
    private SysIdentityDao sysIdentityDao;
    @Autowired
    private AcctItemDao acctItemDao;

    @Autowired
    private SysOptionsDao sysOptionsDao;

    public List<CustomerVO> getSaleCustomer(SysUsers user, String nameOrMobile) {
        return dao.getSaleCustomer(user, nameOrMobile);
    }

    public int getSaleCustomerTotalCount(SysUsers user,
                                         String nameOrMobile) {
        List<Integer> list = dao.getSaleCustomerTotalCount(user, nameOrMobile);
        if (list.size() > 0) {
            return Integer.parseInt(list.get(0).toString());
        } else {
            return 0;
        }
    }

    public List<PresellVisitorsVO> getSaleClue(SysUsers user, String visitorId) {
        return dao.getSaleClue(user, visitorId);
    }

    public List<PresellVisitorsBack> getPresellVisitorsBack(String visitorNo) {
        return dao.getPresellVisitorsBack(visitorNo);
    }

    public List<BaseVehicleVisitorsBack> getVisitorLevel(String stationId) {
        return dao.getVisitorLevel(stationId);
    }

    public List<PresellVisitorsVO> getRegisterSaleClue(String visitorId, String userId) {
        return dao.getRegisterSaleClue(visitorId, userId);
    }

    public String[] getBackWay() {
        List<String> list = dao.getBackWay();
        String[] backWay = new String[list.size()];
        return list.toArray(backWay);
    }

    public String[] getPurpose() {
        List<String> list = dao.getPurpose();
        String[] purpose = new String[list.size()];
        return list.toArray(purpose);
    }

    public List<PresellVisitorsBack> getRegisterPresellVisitorsBack(
            String visitorNo) {
        return dao.getRegisterPresellVisitorsBack(visitorNo);
    }

    public String[] getRreson() {
        List<String> list = dao.getRreson();
        String[] reason = new String[list.size()];
        return list.toArray(reason);
    }

    public Map<String, String> getVisitResult() {
        List<SysFlags> list = dao.getVisitResult();
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (SysFlags s : list) {
            map.put(s.getMeaning(), s.getCode().toString());
        }
        return map;
    }

    public List<CustomerVO> getSaleCustomerForShortCut(SysUsers user,
                                                       String identifier) {
        return dao.getSaleCustomerForShortCut(user, identifier);
    }

    public AddCustomerInitData getAddCustomerInitData(String stationId) {
        List<String> property = dao.getObjectProperty();
        List<String> nation = dao.getNation();
        List<String> position = dao.getPosition();
        List<String> profession = dao.getProfession();
        List<String> vehicleTrademark = dao.getVehicleTrademark();//baseOthersDao.getBaseOtherByTypeNo("VEHICLE_TRADEMARK");
        List<VehicleType> vehicleStrain = dao.getVehicleStrain();//baseOthersDao.getBaseOtherByTypeNo("VEHICLE_STRAIN");//
        List<SysFlags> list = dao.getObjectNature();
        Map<String, String> map = new LinkedHashMap<String, String>();
        List<String> certificateType = dao.getCertificateType();
        for (SysFlags s : list) {
            map.put(s.getMeaning(), s.getCode().toString());
        }
        List<ObjectOfPlace> place = dao.getObjectOfPlace();

        AddCustomerInitData init = new AddCustomerInitData();
        init.setObjectNature(map);
        init.setCertificateType(certificateType);
        init.setObjectProperty(property);
        init.setNation(nation);
        init.setPosition(position);
        init.setProfession(profession);
        init.setPlace(place);
        init.setVehicleTrademark(vehicleTrademark);
        init.setVehicleStrain(vehicleStrain);
//		init.setAddValidClueInitData(getAddValidClueInitData());
        init.setAddIntentClueInitData(getAddIntentClueInitData(stationId));
        return init;
    }

    public AddValidClueInitData getAddValidClueInitData() {
        List<BaseOthers> purchaseUse = baseOthersDao.getBaseOtherByTypeNo("PURCHASE_USE");//dao.getPurchaseUse();
        List<String> distance = dao.getDistance();
        List<String> tonnage = dao.getTonnage();
        List<String> factLoad = dao.getFactLoad();
        List<SysFlags> visitAddr = dao.getVisitAddr();


        //String[] purchaseUses = new String[purchaseUse.size()];
        String[] distances = new String[distance.size()];
        String[] tonnages = new String[tonnage.size()];
        String[] factLoads = new String[factLoad.size()];
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (SysFlags s : visitAddr) {
            map.put(s.getMeaning(), s.getCode().toString());
        }

        AddValidClueInitData init = new AddValidClueInitData();
        init.setPurchaseUse(purchaseUse);
        init.setDistance(distance.toArray(distances));
        init.setFactLoad(factLoad.toArray(factLoads));
        init.setTonnage(tonnage.toArray(tonnages));
        init.setVisitAddr(map);
        init.setBackWay(getBackWay());
        init.setPurpose(getPurpose());
        return init;
    }

    public List<String> getObjectForMobile(String mobile) {
        return dao.getObjectForMobile(mobile);
    }

    /**
     * 新建客户
     */
    public void saveCustomer(CustomerVO object, /*PresellVisitors presellVisitors,*/PresellVisitorsVO presellVisitorsForIntent,
                             CustomerRetainVehicle vehicle,/*PresellVisitorsBack presellVisitorsBack,*/PresellVisitorsBack presellVisitorsBackForIntent, CustomerMaintenanceWorkgroup workgroup) {
        dao.saveCustomer(object);
        dao.saveCustomerMaintenanceWorkgroup(workgroup);
        //产生核算对象
        AcctItem item = new AcctItem();
        item.setTitemId(sysIdentityDao.getIdentityAndIncrementUpdate("acct_item"));
        item.setTitemClassId(3);
        item.setTno(object.getObjectNo());
        item.setTparentId(0);
        item.setTlevel((short) 1);
        item.setTdetail(true);
        item.setTname(object.getObjectName());
        item.setTused(false);
        item.setTid(object.getObjectId());
        acctItemDao.save(item);


        //dao.savePresellVisitors(presellVisitors);
        if (vehicle.getVehicleBrand().length() > 0) {
            dao.saveCustomerRetainVehicle(vehicle);
        }
		/*// 有效线索回访计划 新建客户时如果填写了有效线索信息 则一定要制定回访计划
		if (presellVisitorsBack.getBackId() != null) {
			dao.savePresellVisitorsBack(presellVisitorsBack);
		}*/
        //判断用户是否填写了意向线索,若有意向线索则一并保存
        if (presellVisitorsForIntent.getVisitorNo() != null) {
            dao.savePresellVisitors(presellVisitorsForIntent);
            if (presellVisitorsBackForIntent.getBackId() != null) {
                dao.savePresellVisitorsBack(presellVisitorsBackForIntent);
            }
        }
    }

    /**
     * 新增保有车辆
     *
     * @param vehicle
     */
    public void saveCustomerRetainVehicle(CustomerRetainVehicle vehicle) {
        dao.saveCustomerRetainVehicle(vehicle);
    }

    /**
     * 删除保有车辆
     *
     * @param vehicle
     */
    public void delCustomerRetainVehicle(CustomerRetainVehicle vehicle) {
        dao.delCustomerRetainVehicle(vehicle);
    }

    public List<VehicleType> getVehicleType(String stationId, String vehicleVno, String vehicleName) {
        return dao.getVehicleType(stationId, vehicleVno, vehicleName);
    }

    /**
     * @param presellVisitors
     * @param presellVisitorsBack
     * @param object
     * @deprecated 新建意向线索
     * 新建线索时会保存线索信息，保存回访计划，修改客户属性
     * 并判断是否存在有效线索若没有则新建一条
     */
    public void saveClue(PresellVisitorsVO presellVisitors,/*PresellVisitors vaildClue,*/PresellVisitorsBack presellVisitorsBack, CustomerVO object) {
        dao.savePresellVisitors(presellVisitors);
        if (presellVisitorsBack.getBackId() != null) {
            dao.savePresellVisitorsBack(presellVisitorsBack);
        }
		/*if(vaildClue.getVisitorNo() != null){
			dao.savePresellVisitors(vaildClue);
		}*/
        dao.updateObject(object);
    }


    /**
     * 添加有效线索
     *
     * @param presellVisitors     有效线索
     * @param presellVisitorsBack 有效线索回访计划
     * @param object
     */
    public void savePresellVisitors(PresellVisitorsVO presellVisitors, PresellVisitorsBack presellVisitorsBack, CustomerVO object) {
        if (presellVisitors.getVisitorNo() != null) {
            dao.savePresellVisitors(presellVisitors);
        }
        if (presellVisitorsBack.getBackId() != null) {
            dao.savePresellVisitorsBack(presellVisitorsBack);
        }
        dao.updateObject(object);
    }

    /**
     * 对线索做回访
     *
     * @param presellVisitors     线索
     * @param presellVisitorsBack 回访信息
     * @param object              客户信息
     */
    public void saveCallBackForClue(PresellVisitorsVO presellVisitors, PresellVisitorsBack presellVisitorsBack, PresellVisitorsBack nextPlanCallBack, CustomerVO object, PresellVisitorsFail presellVisitorsFail) {
        dao.updatePresellVisitorsBack(presellVisitorsBack);
        dao.updatePresellVisitors(presellVisitors);
        dao.updateObject(object);
        if (nextPlanCallBack != null && nextPlanCallBack.getBackId() != null) {
            dao.savePresellVisitorsBack(nextPlanCallBack);
        }
        if (presellVisitorsFail != null) {
            dao.savePresellVisitorsFail(presellVisitorsFail);
        }
    }

    /**
     * 修改回访信息，此处用作检查回访
     *
     * @param presellVisitorsBack
     */
    public void updatePresellVisitorsBack(PresellVisitorsBack presellVisitorsBack) {
        dao.updatePresellVisitorsBack(presellVisitorsBack);
    }

    /**
     * 添加回访计划
     *
     * @param presellVisitorsBack
     */
    public void savePresellVisitorsBack(PresellVisitorsBack presellVisitorsBack) {
        dao.savePresellVisitorsBack(presellVisitorsBack);
    }

    /**
     * 添加战败客户信息
     *
     * @param presellVisitorsFail 战败客户信息
     */
    public void savePresellVisitorsFail(PresellVisitorsFail presellVisitorsFail) {
        dao.savePresellVisitorsFail(presellVisitorsFail);
    }

    /**
     * 获取新增意向线索的初始化数据
     */
    public AddIntentClueInitData getAddIntentClueInitData(String stationId) {
        AddIntentClueInitData init = new AddIntentClueInitData();
        List<BaseOthers> vehicleColor = baseOthersDao.getBaseOtherByTypeNoAndStationId("VEHICLE_COLOR", stationId);//dao.getVehicleColor(stationId);

        List<VehicleType> vehicleName = dao.getVehicleName();
        List<String> attentionEmphases = dao.getAttentionEmphases();
        List<SysFlags> buyType = dao.getBuyType();
        List<String> visitMode = dao.getVisitMode();
        List<String> knowWay = dao.getKnowWay();
        List<String> visitorMode = dao.getVisitorMode();
        List<SysFlags> visitResult = dao.getVisitResult();
        List<String> reason = dao.getRreson();
        List<String> backWay = dao.getBackWay();
        List<String> purpose = dao.getPurpose();
        List<BaseOthers> deliveryLocus = baseOthersDao.getBaseOtherByTypeNo("DELIVERY_LOCUS");


        //String[] deliveryLocuss = new String[deliveryLocus.size()];
        Map<String, Short> visitResultMap = new LinkedHashMap<String, Short>();
        Map<String, Short> map = new LinkedHashMap<String, Short>();
        for (SysFlags s : buyType) {
            map.put(s.getMeaning(), s.getCode());
        }
        for (SysFlags s : visitResult) {
            visitResultMap.put(s.getMeaning(), s.getCode());
        }
        init.setBackWay(backWay);
        init.setPurpose(purpose);
        init.setVehicleColor(vehicleColor);
        init.setVehicleName(vehicleName);
        init.setAttentionEmphases(attentionEmphases);
        init.setVisitMode(visitMode);
        init.setKnowWay(knowWay);
        init.setVisitorMode(visitorMode);
        init.setBuyType(map);
        init.setVisitorLevel(getVisitorLevel(stationId));
        init.setVisitResult(visitResultMap);
        init.setReason(reason);
        init.setDeliveryLocus(deliveryLocus);


        List<BaseOthers> purchaseUse = baseOthersDao.getBaseOtherByTypeNo("PURCHASE_USE");//dao.getPurchaseUse();
        List<String> distance = dao.getDistance();
        List<String> tonnage = dao.getTonnage();
        List<String> factLoad = dao.getFactLoad();
        List<SysFlags> visitAddr = dao.getVisitAddr();

        Map<String, String> visitAddrMap = new LinkedHashMap<String, String>();
        for (SysFlags s : visitAddr) {
            visitAddrMap.put(s.getMeaning(), s.getCode().toString());
        }

        init.setPurchaseUse(purchaseUse);
        init.setDistance(distance);
        init.setFactLoad(factLoad);
        init.setTonnage(tonnage);
        init.setVisitAddr(visitAddrMap);

        return init;
    }

    /**
     * 验证前用户所在的部门是否还有未跟进完成的线索，有则不允许再次建立线索
     *
     * @param userId    当前用户ID
     * @param visitorId 客户ID
     * @return
     */
    public List<PresellVisitorsVO> getUnfinishedClueForDepartment(String userId, String visitorId) {
        return dao.getUnfinishedClueForDepartment(userId, visitorId);
    }

    /**
     * 验证有没有未回访的有效线索 ,有则不允许再次建立线索
     *
     * @param visitorId
     * @return
     */
    public List<String> getValidClueForNoBack(String visitorId) {
        return dao.getValidClueForNoBack(visitorId);
    }

    /**
     * 修改客户信息
     *
     * @param object
     */
    public void updateObject(CustomerVO object, CustomerRetainVehicle v) {
        if (v != null) {
            if (!Tools.isEmpty(object.getRetainVehicleBrand())) {
                //CustomerRetainVehicle v = list.get(0);
                v.setVehicleBrand(object.getRetainVehicleBrand());
                v.setVehicleStrain(object.getRetainVehicleStrain());
                this.updateCustomerRetainVehicle(v);
            }
            //删除保有车辆
            if (Tools.isEmpty(object.getRetainVehicleBrand())) {
                this.delCustomerRetainVehicle(v);
            }
        }
        //新增保有车辆信息
        if (v == null && !Tools.isEmpty(object.getRetainVehicleBrand())) {
            CustomerRetainVehicle retainVehicle = new CustomerRetainVehicle();
            retainVehicle.setSelfId(UUID.randomUUID().toString());
            retainVehicle.setCustomerId(object.getObjectId());
            retainVehicle.setVehicleBrand(object.getRetainVehicleBrand());
            retainVehicle.setVehicleStrain(object.getRetainVehicleStrain());
            this.saveCustomerRetainVehicle(retainVehicle);
        }
        dao.updateObject(object);
    }

    /**
     * 修改线索信息
     *
     * @param presellVisitors
     */
    public void updatePresellVisitors(PresellVisitorsVO presellVisitors) {
        dao.updatePresellVisitors(presellVisitors);
    }

    /**
     * 验证客户是否存在有效线索
     *
     * @param visitorId 用户ID
     * @return
     */
    public List<PresellVisitorsVO> checkValidClue(String visitorId) {
        return dao.checkValidClue(visitorId);
    }

    /**
     * 验证是否有未完成的回访计划
     *
     * @param visitorId
     * @return
     */
    public List<String> getClueForNoBack(String visitorId) {
        return dao.getClueForNoBack(visitorId);
    }

    /**
     * 制定回访计划
     *
     * @param presellVisitors     线索信息
     * @param presellVisitorsBack 新建的回访计划
     */
    public void savePlanCallBack(PresellVisitorsVO presellVisitors, PresellVisitorsBack presellVisitorsBack) {
        dao.savePresellVisitorsBack(presellVisitorsBack);
        dao.updatePresellVisitors(presellVisitors);
    }

    /**
     * 查询保有车辆
     *
     * @param selfId
     * @return
     */
    public CustomerRetainVehicle getCustomerRetainVehicleById(String selfId) {
        List<CustomerRetainVehicle> list = dao.getCustomerRetainVehicleById(selfId);
        CustomerRetainVehicle v = null;
        if (list.size() > 0) {
            v = list.get(0);
        }
        return v;
    }

    /**
     * 修改保有车辆信息
     *
     * @param vehicle
     */
    public void updateCustomerRetainVehicle(CustomerRetainVehicle vehicle) {
        dao.updateCustomerRetainVehicle(vehicle);
    }

    /**
     * 检查客户是否有过消费
     *
     * @param objectId
     * @return
     */
    public List<String> getFinanceDocumentEntries(String objectId) {
        return dao.getFinanceDocumentEntries(objectId);
    }

    /**
     * 是否允许多线索
     *
     * @return
     */
    public boolean getAllowMultiClue() {
        List<String> list = dao.getAllowMultiClue();
        boolean allowMultiClue = list.get(0).equals("允许");
        return allowMultiClue;
    }

    /**
     * 查询最后一次销售线索 新增线索时需要上一次的回访结果
     *
     * @param visitorId
     * @return
     */
    public List<LastPresellVisitors> getLastVisitResult(String visitorId) {
        return dao.getLastVisitResult(visitorId);
    }

    /**
     * 获取用户的保有车辆
     */
    public List<CustomerRetainVehicle> getCustomerRetainVehicle(String objectId) {
        return dao.getCustomerRetainVehicle(objectId);
    }

    public List<String> getCheckBackContext(String backId) {
        return dao.getCheckBackContext(backId);
    }

    public List<String> getClueBackRealTime(String backId) {
        return dao.getClueBackRealTime(backId);
    }

    public List<PresellVisitorsForCheck> getClueForUpdate(String visitorNo) {
        return dao.getClueForUpdate(visitorNo);
    }

    public String getAutoNoOfObject() {
        SimpleDateFormat nodf = new SimpleDateFormat("yyyyMMdd");
        String sql = "SELECT MAX(SUBSTRING(object_no, 9,100)) AS max_no FROM (SELECT object_no \n" +
                "                    FROM dbo.base_related_objects UNION SELECT object_no FROM dbo.interested_customers) a \n" +
                "                    WHERE a.object_no LIKE :objectNo";
        Map<String, Object> param = new HashMap<>(1);
        param.put("objectNo", nodf.format(new Date(System.currentTimeMillis())) + "%");
        List<Map<String, Object>> result = dao.getMapBySQL(sql, param);
        int maxNo = 0;
        if (result != null && result.size() > 0) {
            String maxNoStr = (String) result.get(0).get("max_no");
            if (StringUtils.isNotEmpty(maxNoStr)) {
                maxNo = Integer.parseInt(maxNoStr);
            }
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(4);
        nf.setGroupingUsed(false);
        String value = nodf.format(new Date()) + nf.format(maxNo + 1);

        return value;
    }

    /**
     * 验证客户名称合法性
     *
     * @param objectName
     * @param objectId
     * @param certificateNo
     * @param mobile
     * @param shortName
     * @param objectNature
     * @return
     */
    public List<OjbectNameForCheck> getObjectNameIsRight(String objectName, String objectId, String certificateNo, String mobile, String shortName, Short objectNature) {
        return dao.getObjectNameIsRight(objectName, objectId, certificateNo, mobile, shortName, objectNature);
    }

    public List<UserMaintenanceWorkgroups> getUserMaintenanceWorkgroups(
            String userId, String stationId) {
        return dao.getUserMaintenanceWorkgroups(userId, stationId);
    }

    public void saveCustomerMaintenanceWorkgroup(
            CustomerMaintenanceWorkgroup workgroup) {
        dao.saveCustomerMaintenanceWorkgroup(workgroup);

    }

    public List<CustomerVO> checkObjectNameIsUpdate(String objectId) {
        return dao.checkObjectNameIsUpdate(objectId);
    }


    //==========================================================================

    /***
     * 新增或修改意向客户时，检验客户信息是否合法
     * @param customer
     * @return 错误信息，合法则返回null
     */
    public String isCustomerValid(InterestedCustomers customer) {
        String msg = null;
        if (customer.getObjectNature() == 10) {
            List<OjbectNameForCheck> checkObjectName = getObjectNameIsRight(customer.getObjectName(), customer.getObjectId(), customer.getCertificateNo(), null, customer.getShortName(), customer.getObjectNature());
            if (checkObjectName.size() > 0) {
                for (OjbectNameForCheck c : checkObjectName) {
                    if (customer.getObjectName().equals(c.getObjectName())) {
                        msg += "当对象性质为“单位”时，客户名称不允许重复！";
                        break;
                    }
                    if (customer.getShortName().equals(c.getShortName())) {
                        msg += "当对象性质为“单位”时，客户简称不允许重复！";
                        break;
                    }
                    if (customer.getCertificateNo().equals(c.getCertificateNo())) {
                        if (!"东贸版".equals(sysOptionsDao.getOptionForString(InterestedCustomersService.DV_VERSION)) ||
                                (!InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER.equals(customer.getCertificateNo()) && !InterestedCustomersService.DEFAULT_CER_NO_UNIT.equals(customer.getCertificateNo()))) {
                            msg += "系统中已存在证件号为'" + customer.getCertificateNo() + "'的客户， 证件号码不允许重复！";
                            break;
                        }
                    }
                }
            }
        } else {
            List<OjbectNameForCheck> checkObjectName = getObjectNameIsRight(customer.getObjectName(), customer.getObjectId(), customer.getCertificateNo(), customer.getMobile(), customer.getShortName(), customer.getObjectNature());
            if (checkObjectName.size() > 0) {
                for (OjbectNameForCheck c : checkObjectName) {
                    if (!Tools.isEmpty(customer.getCertificateNo()) && customer.getCertificateNo().equals(c.getCertificateNo())) {
                        if(!"东贸版".equals(sysOptionsDao.getOptionForString(InterestedCustomersService.DV_VERSION)) ||
                                (!InterestedCustomersService.DEFAULT_CER_NO_CUSTOMER.equals(customer.getCertificateNo()) && !InterestedCustomersService.DEFAULT_CER_NO_UNIT.equals(customer.getCertificateNo()))) {
                            msg += "系统中已存在证件号为'" + customer.getCertificateNo() + "'的客户， 证件号码不允许重复！";
                            break;
                        }
                    }
                    if (c.getObjectNature() == 10 && customer.getObjectName().equals(c.getObjectName())) {
                        msg += "系统中已存在对象性质为“单位”且对象名称为'" + customer.getObjectName() + "'的客户,不能保存！";
                        break;
                    }
                    if (c.getObjectNature() == 10 && customer.getShortName().equals(c.getShortName())) {
                        msg += "系统中已存在对象性质为“单位”且对象简称为'" + customer.getShortName() + "'的客户,不能保存！";
                        break;
                    }
                    if (c.getObjectNature() == 20 && customer.getObjectName().equals(c.getObjectName()) && customer.getShortName().equals(c.getShortName())) {
                        msg += "系统中已存在对象名称为'" + customer.getObjectName() + "'、手机号码为'" + customer.getMobile() + "'的客户，对象名称和手机号码不允许同时重复！";
                        break;
                    }
                }
            }
        }
        return msg;
    }

    /**
     * 新建意向线索
     *
     * @param intentionClue 意向线索信息
     * @param backVisitPlan 回访计划
     * @author LiuJin
     */
    public void createIntentionClue(PresellVisitorsVO intentionClue, PresellVisitorsBack backVisitPlan) {
        SysUsers user = HttpSessionStore.getSessionUser();
        InterestedCustomers customer = dao.get(InterestedCustomers.class, intentionClue.getVisitorId());
        if (customer == null) {
            throw new ServiceException("该客户不存在");
        }

        List<PresellVisitorsVO> list = getUnfinishedClueForDepartment(user.getUserId(), intentionClue.getVisitorId());
        //上次销售线索
        List<LastPresellVisitors> lastPresellVisitors = getLastVisitResult(intentionClue.getVisitorId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //判断当前用户所在的部门是否还有未跟进完成的线索，有则不允许再次建立线索
        if (list.size() > 0) {
            throw new ServiceException("同一部门只能建立一条正在跟进的线索！");
        } else {
            String intentClueNo;
            intentClueNo = UUID.randomUUID().toString();
            intentionClue.setVisitorNo(intentClueNo);
            intentionClue.setStationId(user.getInstitution().getDefaultStation());
            intentionClue.setVisitTime(new Timestamp(new Date().getTime()));
            intentionClue.setVisitorCount("1");
            intentionClue.setVisitorId(intentionClue.getVisitorId());
            intentionClue.setSeller(user.getUserName());
            intentionClue.setCreator(user.getUserName() + "("
                    + user.getUserNo() + ")");
            intentionClue.setCreateTime(Timestamp.valueOf(df
                    .format(new Date())));
            intentionClue.setSellerId(user.getUserId());
            intentionClue.setLastIsCompetitive(customer.getIsCompetitive());
            //判断上次销售线索是否存在，若存在且为意向线索 则将上次回访结果 填入本次线索中
            if (lastPresellVisitors.size() > 0) {
                intentionClue.setLastVisitResult(lastPresellVisitors.get(0).getVisitResult());
            }
            if (intentionClue.getVisitResult() == null) {
                backVisitPlan.setBackId(UUID.randomUUID().toString());
                backVisitPlan.setVisitorNo(intentClueNo);
                if (backVisitPlan.getPlanBackTime() == null) {
                    throw new ServiceException("必须为意向线索制定回访计划");
                }
                //需要将计划回访时间回填到线索中
                intentionClue.setPlanBackTime(backVisitPlan.getPlanBackTime());
            }
            dao.save(intentionClue);
            dao.save(backVisitPlan);
        }
    }

    /**
     * 新建意向客户
     *
     * @param customer      客户信息
     * @param retainVehicle 客户保有车辆
     * @param saleClue      意向线索
     * @param backVisitPlan 回访计划
     */
    public void createIntentionCustomer(InterestedCustomers customer,
                                        CustomerRetainVehicle retainVehicle, PresellVisitorsVO saleClue,
                                        PresellVisitorsBack backVisitPlan) {
        SysUsers user = HttpSessionStore.getSessionUser();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        List<UserMaintenanceWorkgroups> workGroups = getUserMaintenanceWorkgroups(
                user.getUserId(), user.getInstitution().getDefaultStation());
        if (workGroups == null || workGroups.size() == 0) {
            throw new ServiceException("您暂未加入任何维系小组，不能新建客户");
        }

        String objectId = UUID.randomUUID().toString();// 用户ID
        customer.setObjectId(objectId);
        //检查客户信息是否合法
        String msg = isCustomerValid(customer);
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
        customer.setCreateTime(currentTime);

        CustomerMaintenanceWorkgroup workgroup = new CustomerMaintenanceWorkgroup();
        workgroup.setSelfId(UUID.randomUUID().toString());
        workgroup.setObjectId(objectId);
        workgroup.setStationId(user.getInstitution().getDefaultStation());
        workgroup.setBmwId(workGroups.get(0).getBmwId());

        if (retainVehicle != null && retainVehicle.getVehicleBrand() != null && retainVehicle.getVehicleBrand().length() > 0) {
            retainVehicle.setSelfId(UUID.randomUUID().toString());
            retainVehicle.setCustomerId(objectId);
        } else {
            retainVehicle = null;
        }

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
        if (backVisitPlan.getPlanBackTime() == null) {
            throw new ServiceException("必须为意向线索制定回访计划");
        }
        //需要将计划回访时间回填到线索中
        saleClue.setPlanBackTime(backVisitPlan.getPlanBackTime());

        //产生核算对象
        AcctItem item = new AcctItem();
        item.setTitemId(sysIdentityDao.getIdentityAndIncrementUpdate("acct_item"));
        item.setTitemClassId(3);
        item.setTno(customer.getObjectNo());
        item.setTparentId(0);
        item.setTlevel((short) 1);
        item.setTdetail(true);
        item.setTname(customer.getObjectName());
        item.setTused(false);
        item.setTid(customer.getObjectId());

        dao.save(item);
        dao.save(customer);
        dao.save(workgroup);
        if (retainVehicle != null) {
            dao.save(retainVehicle);
        }
        dao.save(saleClue);
        dao.save(backVisitPlan);
    }
}
