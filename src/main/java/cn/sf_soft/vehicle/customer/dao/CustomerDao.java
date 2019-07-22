package cn.sf_soft.vehicle.customer.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.sf_soft.basedata.model.BaseMaintenanceWorkgroups;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.model.InterestedCustomers;
import cn.sf_soft.vehicle.customer.model.CustomerVO;
import cn.sf_soft.vehicle.customer.model.ObjectOfPlace;
import cn.sf_soft.vehicle.customer.model.PresellVisitors;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsBack;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsVO;
import cn.sf_soft.vehicle.customer.model.VehicleType;

public interface CustomerDao extends BaseDao {

	/**
	 * 根据关键字查找用户所具有查看权限的客户信息
	 * @param user
	 * @param keyword 客户名称、联系人名称、手机号码
	 */
	public List<CustomerVO> getCustomerByKeyword(SysUsers user, String keyword);
	
	/***
	 * 快捷方式获取意向客户
	 * @param user
	 * @param shortcut 今日活动:today; 明日活动:tomorrow; 当周活动:week; 当月活动:month; 所有意向:allYX; H、A、O、B 
	 * @return
	 */
	public List<CustomerVO> getCustomerByShortcut(SysUsers user, String shortcut);
	
	/**
	 * 获取客户的销售线索
	 * @param user
	 * @param customerId
	 * @return
	 */
	public List<PresellVisitorsVO> getSaleClue(SysUsers user, String customerId);
	/**
	 * 获取当前部门下，某客户未完成的销售线索
	 * @param customerId
	 * @param user
	 * @return
	 */
	public List<PresellVisitors> getUnfinishedSaleClueInTheSameDepartment(String customerId, String departmentId);
	
	/**
	 * 获取某个客户所有未回访完成的销售线索
	 * @param customerId
	 * @return
	 */
	public List<PresellVisitors> getAllUnfinishedSalclue(String customerId);
	/**
	 * 获取销售线索的回访列表
	 * @param visitorNo
	 * @return
	 */
	public List<PresellVisitorsBack> getSaleClueBackVisitList(String visitorNo);
	
	/**
	 * 获取对于指定客户，当前销售员需要回访的销售回访计划
	 * @param customerId
	 * @param userId
	 * @return
	 */
	public PresellVisitorsBack getTobeBackVisitPlan(String customerId, String sellerId);
	
	/**
	 * 获取用户所在的维系小组
	 * @param user
	 * @return
	 */
	public List<BaseMaintenanceWorkgroups> getMaintenanceWorkgroupOfUser(SysUsers user);
	
	/**
	 * 获取客户是否有过消费历史
	 * @param customerId
	 * @return
	 */
	public boolean hasCustomerConsumed(String customerId);
	
	/**
	 * 获取相似的客户
	 * @param customer
	 * @return
	 */
	public List<InterestedCustomers> getSimilarCustomer(InterestedCustomers customer);
	
	/**
	 * 获取车辆型号基础数据
	 * @param stationId
	 * @param keyword 查询关键字，可为null或者车型、车名、销售代号
	 * @return
	 */
	public List<VehicleType> getVehicleType(String stationId, String keyword);
	/**
	 * 车辆品牌基础数据
	 * @return
	 */
	public List<String> getVehicleTrademark();
	
	/**
	 * 车辆品系基础数据
	 * @return
	 */
	public List<VehicleType> getVehicleStrain();
	
	/**
	 * 获取省市区基础数据
	 * @return
	 */
	public List<ObjectOfPlace> getCitys();
	
	/**
	 * 获取往来对象最大的编码
	 * @return
	 */
	public String getMaxNoOfRelatedObject();

	/**
	 * 查询用户列表
	 * @param user
	 * @param keyword
	 * @param filter
	 * @param sort
	 * @return
	 */
	PageModel getCustomers(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page);

	/**
	 * 查询线索列表
	 * @param user
	 * @param keyword
	 * @param filter
	 * @param sort
	 * @param perPage
	 * @param page
	 * @return
	 */
	PageModel getSaleClues(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page);

	/**
	 * 查询回访列表
	 * @param user
	 * @param keyword
	 * @param filter
	 * @param sort
	 * @param perPage
	 * @param page
	 * @return
	 */
    PageModel getCallbacks(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page);

	PageModel getVehicleStore(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page);

	PageModel getMyCalendar(SysUsers user, Map<String, Object> condition, int pageSize, int pageNo);


	//void giveUpCustomer(SysUsers user, String objectId);

	PageModel getPublicCustomers(String keyword, Map<String, Object> filter, String sort, int perPage, int page);

	PageModel getClueBacks(SysUsers user, Map<String, Object> postJson, int pageSize, int pageNo);

	PageModel getRelatedObjects(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page);

	List<Map<String,Object>> getMyCalendarCount(SysUsers user, Map<String, Object> condition);

	Map<String,Object> getCustomerMap(SysUsers user, String province, String city, String area, String profession, boolean onlySelf, String stationId);

    /**
     * 获取交车后指定的时间未跟进的客户
     * @param type
     * @param filter
     * @param sort
     * @param page
     * @param perPage
     * @return
     */
	PageModel getSellerCallBackWarning(String type, Map<String, Object> filter, String sort, int page, int perPage);
	PageModel getSellerCallBackWarningForTotal(String type);

    /**
     * 获取逾期未跟进客户
     * @param filter
     * @param sort
     * @param page
     * @param perPage
     * @return
     */
	PageModel getOverdueCallBack(Map<String, Object> filter, String sort, int page, int perPage);
	PageModel getOverdueCallBackForTotal();

    /**
     * 获取超过指定天数未跟进的客户
     * @param startDay
	 * @param endDay
     * @param filter
     * @param sort
     * @param page
     * @param perPage
     * @return
     */
	PageModel getCallBackWarning(int startDay, int endDay, Map<String, Object> filter, String sort, int page, int perPage);
	PageModel getCallBackWarningForTotal(int startDay, int endDay);

    /**
     * 获取待检查的客户跟进
     * @param filter
     * @param sort
     * @param page
     * @param perPage
     * @return
     */
	PageModel getPendingCheck(Map<String, Object> filter, String sort, int page, int perPage);
	PageModel getPendingCheckForTotal();

    /**
     * 获取待检查的新增客户
     * @param filter
     * @param sort
     * @param page
     * @param perPage
     * @return
     */
	PageModel getPendingCheckForNewCustomer(Map<String, Object> filter, String sort, int page, int perPage);
	PageModel getPendingCheckForNewCustomerForTotal();

	/**
	 * 获取客户动态（合同）
	 * @param customerId 客户ID
	 * @return
	 */
	List<Map<String, Object>> getCustomerInformationWithContract(String customerId);

	/**
	 * 获取客户动态（报价信息）
	 * @param customerId 客户ID
	 * @return
	 */
	List<Map<String, Object>> getCustomerInformationWithQuotation(String customerId);

	/**
	 * 获取客户动态（跟进信息）
	 * @param customerId 客户ID
	 * @return
	 */
	List<Map<String, Object>> getCustomerInformationWithCallBack(String customerId);

	/**
	 * 获取客户动态（跟进逾期）
	 * @param customerId 客户ID
	 * @return
	 */
	List<Map<String, Object>> getCustomerInformationWithOverdueCallBack(String customerId);

	/**
	 * 获取客户动态（待跟进，包含逾期跟进）
	 * @param customerId 客户ID
	 * @return
	 */
	List<Map<String, Object>> getCustomerInformationWithPendingCallBack1(String customerId);

	/**
	 * 获取客户动态（待跟进，不包含逾期跟进）
	 * @param customerId 客户ID
	 * @return
	 */
	List<Map<String, Object>> getCustomerInformationWithPendingCallBack2(String customerId);

	/**
	 * 获取指定客户最后购买汇总
	 * @param customerId 客户ID
	 * @return
	 */
	Map<String, Object> getCustomerPurchaseSummaryById(String customerId);

	/**
	 * 获取指定客户最后购买时间
	 * @param customerId 客户ID
	 * @return
	 */
	Timestamp getCustomerLastPurchaseTimeById(String customerId);

	/**
	 * 获取指定客户最后跟进时间
	 * @param customerId 客户ID
	 * @return
	 */
	Timestamp getCustomerLastVisitTimeById(String customerId);

	/**
	 * 获取指定客户最后跟进计划时间
	 * @param customerId 客户ID
	 * @return
	 */
	Timestamp getCustomerLastVisitPlanTimeById(String customerId);
}
