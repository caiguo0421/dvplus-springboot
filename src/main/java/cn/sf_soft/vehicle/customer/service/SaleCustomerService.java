package cn.sf_soft.vehicle.customer.service;

import java.util.List;
import java.util.Map;

import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.model.AddCustomerInitData;
import cn.sf_soft.vehicle.customer.model.AddIntentClueInitData;
import cn.sf_soft.vehicle.customer.model.AddValidClueInitData;
import cn.sf_soft.vehicle.customer.model.CustomerVO;
import cn.sf_soft.vehicle.customer.model.BaseVehicleVisitorsBack;
import cn.sf_soft.vehicle.customer.model.InterestedCustomers;
import cn.sf_soft.vehicle.customer.model.CustomerMaintenanceWorkgroup;
import cn.sf_soft.vehicle.customer.model.CustomerRetainVehicle;
import cn.sf_soft.vehicle.customer.model.LastPresellVisitors;
import cn.sf_soft.vehicle.customer.model.OjbectNameForCheck;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsBack;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsVO;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsFail;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsForCheck;
import cn.sf_soft.vehicle.customer.model.UserMaintenanceWorkgroups;
import cn.sf_soft.vehicle.customer.model.VehicleType;
/**
 * 意向客户
 * @Title: SaleCustomerService.java
 * @date 2013-12-26 下午04:41:55 
 * @author cw
 */
public interface SaleCustomerService {
	/**
	 * 获取客户信息
	 * @param user 当前用户信息
	 * @param nameOrMobile 过滤条件    可以是名称,电话,联系人
	 * @return
	 */
	public List<CustomerVO> getSaleCustomer(SysUsers user,String nameOrMobile);
	/**
	 * 快捷栏查询客户信息
	 * @param user
	 * @param identifier 标识符   标识：today 今日活动,tomorrow 明日活动 等
	 * @return
	 */
	public List<CustomerVO> getSaleCustomerForShortCut(final SysUsers user,final String identifier);
	
	/**
	 * 获取客户信息总记录数
	 * @param user
	 * @param nameOrMobile
	 * @return
	 */
	public int getSaleCustomerTotalCount(final SysUsers user,final String nameOrMobile);
	/**
	 * 获取线索信息
	 * @param user
	 * @param visitorId 客户Id
	 * @return
	 */
	public List<PresellVisitorsVO> getSaleClue(final SysUsers user,final String visitorId);
	/**
	 * 获取某条线索回访信息
	 * @param visitorNo 线索ID
	 * @return
	 */
	public List<PresellVisitorsBack> getPresellVisitorsBack (String visitorNo);
	/**
	 * 获取某条线索需要记录的回访信息
	 * @param visitorNo 线索ID 
	 * @return
	 */
	public List<PresellVisitorsBack> getRegisterPresellVisitorsBack (String visitorNo);
	/**
	 * 获得某客户有待回访的销售线索
	 * @param visitorId 客户id
	 * @param userId  当前用户id
	 * @return
	 */
	public List<PresellVisitorsVO> getRegisterSaleClue (String visitorId,String userId);
	/**
	 * 获取成交机会
	 * @param stationName
	 * @return
	 */
	public List<BaseVehicleVisitorsBack> getVisitorLevel (String stationId);
	/**
	 * 获取回访方式
	 * @return
	 */
	public String[] getBackWay ();
	/**
	 * 获取回访目的
	 * @return
	 */
	public String[] getPurpose();
	/**
	 * 获取成败原因
	 * @return
	 */
	public String[] getRreson ();
	/**
	 * 获取成交结果
	 * @return
	 */
	public Map<String,String> getVisitResult();
	/**
	 * 获取新建客户页面的基础数据
	 * @return
	 */
	public AddCustomerInitData getAddCustomerInitData(String stationId);
	/**
	 * 获得有效线索页面的基础数据
	 * @return
	 */
	public AddValidClueInitData getAddValidClueInitData();
	/**
	 * 验证电话号码是否唯一
	 * @param mobile 电话号码
	 * @return
	 */
	public List<String> getObjectForMobile(String mobile);
	/**
	 * 添加客户信息
	 * @param object 客户基本信息
	 * @param presellVisitors 新增客户时默认新增一条有效线索信息
	 * @param presellVisitorsForIntent 新增客户的意向线索信息有则新增
	 * @param vehicle 客户保有车辆信息
	 * @param presellVisitorsBack 有效线索回访计划
	 * @param presellVisitorsBackForIntent 意向线索回访计划
	 */
	public void saveCustomer(CustomerVO object, /*PresellVisitors presellVisitors,*/PresellVisitorsVO presellVisitorsForIntent,
			CustomerRetainVehicle vehicle,/*PresellVisitorsBack presellVisitorsBack,*/PresellVisitorsBack presellVisitorsBackForIntent,CustomerMaintenanceWorkgroup workgroup);
	/**
	 * 新增保有车辆
	 * @param vehicle 车辆信息
	 */
	public void saveCustomerRetainVehicle(CustomerRetainVehicle vehicle);
	/**
	 * 删除保有车辆
	 * @param vehicle
	 */
	public void delCustomerRetainVehicle(CustomerRetainVehicle vehicle);
	/**
	 * 根据站点获取车辆型号
	 * @param stationId 
	 * @param vehicleName 车辆名称
	 * @param vehicleVno  车辆型号
	 * @return
	 */
	public List<VehicleType> getVehicleType (String stationId,String vehicleVno,String vehicleName);
	/**
	 * 验证线索是否可以修改
	 * @param visitorNo
	 * @return
	 */
	public List<PresellVisitorsForCheck> getClueForUpdate(String visitorNo);
	/**
	 * 验证计划是否有被检查
	 * @param backId
	 * @return
	 */
	public List<String> getCheckBackContext(String backId);
	/**
	 * 验证计划是否有被回访
	 * @param backId
	 * @return
	 */
	public List<String> getClueBackRealTime(String backId);
	/**
	 * @deprecated
	 * 新建线索
	 * 新建线索时会保存线索信息，保存回访计划，修改客户属性 
	 * @param presellVisitors
	 * @param presellVisitorsBack
	 * @param vaildClue 有效线索信息 现在已经取消
	 * @param object
	 */
	public void saveClue(PresellVisitorsVO presellVisitors,/*PresellVisitors vaildClue,*/PresellVisitorsBack presellVisitorsBack,CustomerVO object);
	
	
	/**
	 * 添加有效线索
	 * @param presellVisitors 有效线索
	 * @param presellVisitorsBack 有效线索回访计划
	 * @param object
	 */
	public void savePresellVisitors(PresellVisitorsVO presellVisitors,PresellVisitorsBack presellVisitorsBack,CustomerVO object);
	/**
	 * 对线索做回访
	 * @param presellVisitors 线索
	 * @param presellVisitorsBack 本次回访信息
	 * @param nextPlanCallBack 下次回访计划
	 * @param object 客户信息
	 */
	public void saveCallBackForClue(PresellVisitorsVO presellVisitors,PresellVisitorsBack presellVisitorsBack,PresellVisitorsBack nextPlanCallBack,CustomerVO object,PresellVisitorsFail presellVisitorsFail);
	/**
	 * 修改回访信息，此处用作检查回访
	 * @param presellVisitorsBack
	 */
	public void updatePresellVisitorsBack(PresellVisitorsBack presellVisitorsBack);
	/**
	 * 添加回访计划
	 * @param presellVisitorsBack
	 */
	public void savePresellVisitorsBack(PresellVisitorsBack presellVisitorsBack);
	/**
	 * 添加战败客户信息
	 * @param presellVisitorsFail 战败客户信息
	 */
	public void savePresellVisitorsFail(PresellVisitorsFail presellVisitorsFail);
	
	/**
	 * 获取新增意向线索的初始化数据
	 * @param stationId
	 * @return
	 */
	public AddIntentClueInitData getAddIntentClueInitData(String stationId);
	/**
	 * 验证前用户所在的部门是否还有未跟进完成的线索，有则不允许再次建立线索
	 * @param userId 当前用户ID
	 * @param visitorId 客户ID
	 * @return
	 */
	public List<PresellVisitorsVO> getUnfinishedClueForDepartment(String userId,String visitorId);
	/**
	 *  验证有没有未回访的有效线索 ,有则不允许再次建立线索
	 * @param visitorId
	 * @return
	 */
	public List<String> getValidClueForNoBack(String visitorId);
	/**
	 *  验证是否有未完成的回访计划 
	 * @param visitorId
	 * @return
	 */
	public List<String> getClueForNoBack(String visitorId);
	/**
	 * 修改客户信息
	 * @param object 客户信息
	 */
	public void updateObject(CustomerVO object,CustomerRetainVehicle v);
	/**
	 * 修改线索信息
	 * @param presellVisitors
	 */
	public void updatePresellVisitors(PresellVisitorsVO presellVisitors);
	/**
	 * 验证客户是否存在有效线索
	 * @param visitorId 用户ID
	 * @return
	 */
	public List<PresellVisitorsVO> checkValidClue(String visitorId);
	/**
	 * 制定回访计划
	 * @param presellVisitors 线索信息
	 * @param presellVisitorsBack 新建的回访计划
	 */
	public void savePlanCallBack(PresellVisitorsVO presellVisitors,PresellVisitorsBack presellVisitorsBack);
	/**
	 * 获取用户的保有车辆
	 * @param objectId
	 * @return
	 */
	public List<CustomerRetainVehicle> getCustomerRetainVehicle (String objectId);
	/**
	 * 查询保有车辆
	 * @param selfId
	 * @return
	 */
	public CustomerRetainVehicle getCustomerRetainVehicleById(String selfId);
	/**
	 * 修改保有车辆信息
	 * @param vehicle
	 */
	public void updateCustomerRetainVehicle(CustomerRetainVehicle vehicle);
	/**
	 * 检查客户是否有过消费 
	 * @param objectId
	 * @return
	 */
	public List<String> getFinanceDocumentEntries(String objectId);
	/**
	 * 是否允许多线索
	 * @return
	 */
	public boolean getAllowMultiClue();
	/**
	 *  查询最后一次销售线索 新增线索时需要上一次的回访结果 
	 * @param visitorId
	 * @return
	 */
	public List<LastPresellVisitors> getLastVisitResult(String visitorId);
	/**
	 *  获取 用户编码的当前编码
	 * @return
	 */
	public String getAutoNoOfObject();
	/**
	 *  查询用户是否存在维系小组
	 * @return
	 */
	public List<UserMaintenanceWorkgroups> getUserMaintenanceWorkgroups(String userId,String stationId);
	/**
	 * 添加客户维系组
	 * @param object 客户信息
	 */
	public void saveCustomerMaintenanceWorkgroup(CustomerMaintenanceWorkgroup workgroup);
	/**
	 * 
	 *  验证客户名称合法性
	 * @return
	 * @param objectName
	 * @param objectId
	 * @param certificateNo
	 * @param mobile
	 * @param shortName
	 * @param objectNature
	 * @return
	 */
	public List<OjbectNameForCheck> getObjectNameIsRight(String objectName,String objectId,String certificateNo,String mobile,String shortName,Short objectNature);
	/**
	 * 查询客户名称，
	 * @param objectId
	 * @return
	 */
	public List<CustomerVO> checkObjectNameIsUpdate(String objectId);
	
	//===========================================================================
	/***
	 * 新增或修改意向客户时，检验客户信息是否合法
	 * @param customer
	 * @return 错误信息，合法则返回null
	 */
	public String isCustomerValid(InterestedCustomers customer);
	/**
	 * 新建意向线索
	 * @param intentionClue	意向线索信息
	 * @param backVisitPlan 回访计划
	 * @author LiuJin
	 */
	public void createIntentionClue(PresellVisitorsVO intentionClue, PresellVisitorsBack backVisitPlan);
	
	/**
	 * 新建意向客户
	 * @param customer		客户信息
	 * @param retainVehicle 客户保有车辆
	 * @param saleClue		意向线索
	 * @param backVisitPlan 回访计划
	 */
	public void createIntentionCustomer(InterestedCustomers customer,
			CustomerRetainVehicle retainVehicle, PresellVisitorsVO saleClue,
			PresellVisitorsBack backVisitPlan);
}
