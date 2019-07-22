package cn.sf_soft.vehicle.customer.dao;

import java.util.List;


import cn.sf_soft.basedata.model.BaseMaintenanceWorkgroups;
import cn.sf_soft.basedata.model.SysFlags;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.model.CustomerVO;
import cn.sf_soft.vehicle.customer.model.BaseVehicleVisitorsBack;
import cn.sf_soft.vehicle.customer.model.CustomerMaintenanceWorkgroup;
import cn.sf_soft.vehicle.customer.model.CustomerRetainVehicle;
import cn.sf_soft.vehicle.customer.model.LastPresellVisitors;
import cn.sf_soft.vehicle.customer.model.ObjectOfPlace;
import cn.sf_soft.vehicle.customer.model.OjbectNameForCheck;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsBack;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsVO;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsFail;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsForCheck;
import cn.sf_soft.vehicle.customer.model.UserMaintenanceWorkgroups;
import cn.sf_soft.vehicle.customer.model.VehicleType;


/**
 * 
 * @Title: 意向客户
 * @date 2013-12-11 上午10:07:37 
 * @author cw
 */
public interface SaleCustomerDao extends BaseDao{
	
	/**
	 * 获取用户关联的部门
	 * @param userId
	 * @return
	 */
	public List<String> getCurrentUserUnitRelation (String userId);
	/**
	 * 是否允许多线索
	 * @return
	 */
	public List<String> getAllowMultiClue();
	/**
	 * 是否是销售组长
	 * @param userId
	 * @return
	 */
	public List<String> getIsGroupLeader (String userId);
	/**
	 * 获取客户信息
	 * @param user
	 * @param nameOrMobile
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
	 * 获取线索信息
	 * @param user
	 * @param visitorId 客户Id
	 * @return
	 */
	public List<PresellVisitorsVO> getSaleClue(final SysUsers user,final String visitorId);
	/**
	 * 获取某条线索所有回访信息
	 * @param visitorNo
	 * @return
	 */
	public List<PresellVisitorsBack> getPresellVisitorsBack (String visitorNo);
	/**
	 * 获取某条线索需要记录的回访信息
	 * @param visitorNo
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
	 * 获取客户信息总记录数
	 * @param user
	 * @param nameOrMobile
	 * @return
	 */
	public List<Integer> getSaleCustomerTotalCount(final SysUsers user,final String nameOrMobile);
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
	public List<String> getBackWay ();
	/**
	 * 获取回访目的
	 * @return
	 */
	public List<String> getPurpose ();
	/**
	 * 获取成败原因
	 * @return
	 */
	public List<String> getRreson ();
	/**
	 * 获取成交结果
	 * @return
	 */
	public List<SysFlags> getVisitResult();
	/**
	 * 获取客户价值
	 * @return
	 */
	public List<String> getObjectProperty();
	/**
	 * 获取民族
	 * @return
	 */
	public List<String> getNation();
	/**
	 * 获取行业
	 * @return
	 */
	public List<String> getProfession();
	/**
	 * 获取职位
	 * @return
	 */
	public List<String> getPosition();
	/**
	 * 获取区域
	 * @return
	 */
	public List<String> getArea();
	/**
	 * 获取保有车辆_品牌
	 * @return
	 */
	public List<String> getVehicleTrademark();
	/**
	 * 获取保有车辆_品系
	 * @return
	 */
	public List<VehicleType> getVehicleStrain();
	/**
	 * 获取购车用途
	 * @return
	 */
	public List<String> getPurchaseUse();
	/**
	 * 获取运输距离
	 * @return
	 */
	public List<String> getDistance();
	/**
	 * 获取上牌吨位
	 * @return
	 */
	public List<String> getTonnage();
	/**
	 * 获取实际载重
	 * @return
	 */
	public List<String> getFactLoad();
	/**
	 * 获取接触地点
	 * @return
	 */
	public List<SysFlags> getVisitAddr();
	/**
	 * 验证电话号码是否唯一
	 * @param mobile 电话号码
	 * @return
	 */
	public List<String> getObjectForMobile(String mobile);
	/**
	 * 添加客户信息
	 * @param object
	 */
	public void saveCustomer(CustomerVO object);
	/**
	 * 获取用户的保有车辆
	 * @param objectId
	 * @return
	 */
	public List<CustomerRetainVehicle> getCustomerRetainVehicle (String objectId);
	/**
	 * 添加客户保有车辆信息
	 * @param vehicle
	 */
	public void saveCustomerRetainVehicle(CustomerRetainVehicle vehicle);
	/**
	 * 删除保有车辆
	 * @param vehicle
	 */
	public void delCustomerRetainVehicle(CustomerRetainVehicle vehicle);
	/**
	 * 添加客户线索信息
	 * @param presellVisitors 线索信息
	 */
	public void savePresellVisitors(PresellVisitorsVO presellVisitors);
	/**
	 * 添加线索回访计划信息
	 * @param presellVisitorsBack 回访信息
	 */
	public void savePresellVisitorsBack(PresellVisitorsBack presellVisitorsBack);
	/**
	 * 添加战败客户信息
	 * @param presellVisitorsFail 战败客户信息
	 */
	public void savePresellVisitorsFail(PresellVisitorsFail presellVisitorsFail);
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
	 * 意向颜色
	 * @param stationId
	 * @return
	 */
	public List<String> getVehicleColor(String stationId);
	/**
	 * 意向车名
	 * @param stationId
	 * @return
	 */
	public List<VehicleType> getVehicleName();
	/**
	 * 关注重点
	 * @return
	 */
	public List<String> getAttentionEmphases();
	/**
	 * 购车方式
	 * @return
	 */
	public List<SysFlags> getBuyType();
	/**
	 * 来访方式
	 * @return
	 */
	public List<String> getVisitorMode();
	/**
	 * 拜访方式
	 * @return
	 */
	public List<String> getVisitMode();
	/**
	 * 了解渠道
	 * @return
	 */
	public List<String> getKnowWay();
	/**
	 * 销售网点
	 * @return
	 */
	public List<String> getDeliveryLocus();
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
	public void updateObject(CustomerVO object);
	/**
	 * 验证客户是否存在有效线索
	 * @param visitorId 用户ID
	 * @return
	 */
	public List<PresellVisitorsVO> checkValidClue(String visitorId);
	/**
	 * 修改销售线索信息
	 * @param presellVisitors
	 */
	public void updatePresellVisitors(PresellVisitorsVO presellVisitors);
	/**
	 * 修改回访信息,也就是对线索做回访
	 * @param presellVisitors
	 */
	public void updatePresellVisitorsBack(PresellVisitorsBack presellVisitorsBack);
	/**
	 * 查询保有车辆
	 * @param selfId
	 * @return
	 */
	public List<CustomerRetainVehicle> getCustomerRetainVehicleById(String selfId);
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
	 *  查询最后一次销售线索 新增线索时需要上一次的回访结果 
	 * @param visitorId
	 * @return
	 */
	public List<LastPresellVisitors> getLastVisitResult(String visitorId);

	/**
	 *  客户性质
	 * @return
	 */
	public List<SysFlags> getObjectNature();
	/**
	 * 获取证件类型
	 * @return
	 */
	public List<String> getCertificateType();
	/**
	 * 
	 *  验证客户名称合法性
	 * @return
	 *
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
	 *  获得省市区的基础数据
	 * @return
	 */
	public List<ObjectOfPlace> getObjectOfPlace();
	/**
	 *  获得维系小组的基础数据
	 * @return
	 */
	public List<BaseMaintenanceWorkgroups> getMaintenanceWorkgroups(String stationId);
	/**
	 * 查询客户名称，
	 * @param objectId
	 * @return
	 */
	public List<CustomerVO> checkObjectNameIsUpdate(String objectId);
}
