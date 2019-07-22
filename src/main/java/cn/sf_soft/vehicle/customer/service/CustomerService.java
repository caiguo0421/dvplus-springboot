package cn.sf_soft.vehicle.customer.service;

import cn.sf_soft.basedata.model.BaseMaintenanceWorkgroups;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.office.approval.model.VehicleArchives;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.model.*;
import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 意向客户
 * @创建人 LiuJin
 * @创建时间 2014-9-26 下午5:15:57
 * @修改人 
 * @修改时间
 */
public interface CustomerService {

	/**
	 * 根据关键字获取用户可以看到的客户列表
	 * @param user
	 * @param keyword 客户名、联系人名称或手机号码，可选
	 * @return
	 */
	public List<CustomerVO> getCustomerByKeyword(SysUsers user, String keyword);
	
	/**
	 * 快捷查询
	 * @param user
	 * @param shorCut
	 * @return
	 */
	public List<CustomerVO> getCustomerByShortCut(SysUsers user, String shorCut);
	
	/**
	 * 获取某个客户的意向线索列表
	 * @param customerId
	 * @return
	 */
	public List<PresellVisitorsVO> getInentionClueListByCustomerId(SysUsers user, String customerId);
	
	/**
	 * 判断客户是否存在
	 * @param customer
	 * @return
	 */
	public String isCustomerExist(InterestedCustomers customer);
	
	
	/**
	 * 获取修改、新建意向客户时需要的基础数据
	 */
	public EditCustomerInitData getBaseDataOfEditCustomer();
	
	/**
	 * 获取车辆型号基础数据
	 * @param stationId
	 * @param keyword 关键字，可为null或者车辆型号、销售代号、车辆名称
	 * @return
	 */
	public List<VehicleType> getVehicleType(String stationId, String keyword);
	
	/**
	 * 用户是否可以为客户新建意向线索(是否有未完成线索或者是否允许多线索跟进等)
	 * @param customerId 客户ID
	 * @param user	当前用户
	 * @return 可以返回null，否则返回提示信息
	 */
	public String canCreateIntentionClue(String customerId, SysUsers user);
	
	/**
	 * 新建意向线索
	 * @param intentionClue	意向线索信息
	 * @param backVisitPlan 回访计划
	 * @author LiuJin
	 */
	public void createIntentionClue(PresellVisitorsVO intentionClue, PresellVisitorsBack backVisitPlan, SysUsers user);
	
	/**
	 * 修改意向线索
	 * @param intentionClue
	 */
	public void updateIntentionClue(PresellVisitorsVO intentionClue,  SysUsers user);
	
	/***
	 * 获取用户所在的维系小组列表
	 * @param user
	 * @return
	 */
	public List<BaseMaintenanceWorkgroups> getMaintainWorkgroup(SysUsers user);
	/**
	 * 判断用户是否能新建意向客户
	 * @param user
	 * @return
	 */
	public String canCreateCustomer(SysUsers user);
	/**
	 * 新建意向客户
	 * @param customer		客户信息
	 * @param retainVehicle 客户保有车辆
	 * @param saleClue		意向线索
	 * @param backVisitPlan 回访计划
	 */
	public void createIntentionCustomer(CustomerVO customer,
			List<CustomerRetainVehicle> retainVehicles, PresellVisitorsVO saleClue,
			PresellVisitorsBack backVisitPlan);
	
	/**
	 * 修改意向客户
	 * @param customer
	 */
	public void updateCustomer(CustomerVO customer);
	
	/**
	 * 根据线索ID获取该线索的销售回访列表
	 * @param presellVisitorId
	 * @return
	 */
	public List<PresellVisitorsBack> getBackVisitList(String presellVisitorId);
	
	/**
	 * 获取对于指定客户，当前销售员需要回访的销售回访计划
	 * @param customerId
	 * @param sellerId
	 * @return
	 */
	public PresellVisitorsBack getToBeBackVisitPlan(String customerId, String sellerId);
	/**
	 * 记录回访信息
	 * @param backVisit
	 * @param nextBackVisitPlan
	 */
	public void addBackVisit(PresellVisitorsBack backVisit, PresellVisitorsBack nextBackVisitPlan, SysUsers user);


	/**
	 * 查询用户列表
	 * @param user
	 * @param keyword
	 * @param filter
	 * @param sort
	 * @return
	 */
	public PageModel getCustomers(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page);

	/**
	 * 获得系统初始化数据
	 * @return
	 */
    Map<String,Object> getSysInitData();

	/**
	 * 获得某个用户的基本信息
	 * @param objectId
	 * @return
	 */
	Map<String,Object> getCustomerInterestedCustomers(String objectId);

	/**
	 * 获得车辆概述
	 * @param objectId
	 * @return
	 */
	List<CustomerRetainVehicleOverview> getVehicleOverview(String objectId);

	/**
	 * 获得联系人信息
	 * @param objectId
	 * @return
	 */
	List<CustomerOrganizationalStructure> getOrganizational(String objectId);

	/**
	 * 车辆明细
	 * @param objectId
	 * @return
	 */
	List<VehicleArchives> getVehicleArchives(String objectId);

	/**
	 * 消费汇总
	 * @param objectId
	 * @return
	 */
	List<Map<String,Object>> getConsumption(String objectId);

	/**
	 * 获取线索列表
	 * @param attributeFromSession
	 * @param keyword
	 * @param filter
	 * @param sort
	 * @param perPage
	 * @param page
	 * @return
	 */
	PageModel getSaleClues(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page);

	/**
	 * 获得线索详情
	 * @param visitorNo
	 * @return
	 */
	Map<String,Object> getSaleClueDetail(String visitorNo);

	/**
	 * 获得回访记录
	 * @param customerId
	 * @return
	 */
	List<PresellVisitorsBack> getVisits(String customerId);

	PageModel getCallbacks(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page);

	Map<String,Object> getCallbackDetail(String outStockNo);

	PresellVisitorsBack addVisit(SysUsers user, String customerId, Map<String, Object> visitData);

	void batchAddVistBySms(SysUsers user, List<String> customerIds, String content);

	PresellVisitors addPreSalleClue(SysUsers users, String customerId, Map<String, Object> clue);

	PageModel getVehicleStore(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page);

	PresellVisitors updatePreSalleClue(SysUsers user, String saleClueId, Map<String, Object> postJson);

	PresellVisitorsBack addClueBack(SysUsers user, String saleClueId, Map<String, Object> postJson);

	PresellVisitorsBack updateClueBack(SysUsers user, String backId, Map<String, Object> postJson);

	PresellVisitorsBackVO getClueBack(SysUsers user, String backId);

	PresellVisitorsBack checkClueBack(SysUsers user, String backId, String checkContent);

	SellerCallBack getCallback(SysUsers user, String backId);

	SellerCallBack addCallback(SysUsers user, Map<String, Object> postJson);

	SellerCallBack updateCallback(SysUsers user, String backId, Map<String, Object> postJson);

	InterestedCustomers updateCustomerBase(SysUsers user, String customerId, Map<String, Object> postJson);

	CustomerRetainVehicleOverview addVehicleOverview(SysUsers user, Map<String, Object> postJson);

	CustomerRetainVehicleOverview updateVehicleOverview(SysUsers user, String selfId, Map<String, Object> postJson);

	CustomerOrganizationalStructure addOrganizational(SysUsers user, Map<String, Object> postJson);

	CustomerOrganizationalStructure updateOrganizational(SysUsers user, String selfId, Map<String, Object> postJson);

	VehicleArchives addVehicleArchives(SysUsers user, Map<String, Object> postJson);

	VehicleArchives updateVehicleArchives(SysUsers user, String selfId, Map<String, Object> postJson);

//	BaseRelatedObjects storeToBaseRelatedObjectsBase(SysUsers user, Map<String, Object> postJson, InterestedCustomers customers);

	InterestedCustomers addCustomerBase(SysUsers user, Map<String, Object> postJson);

	PresellVisitorsBack updateVisit(SysUsers user, String backId, Map<String, Object> postJson);

	PageModel getMyCalendar(SysUsers user, Map<String, Object> condition, int pageSize, int pageNo);

//	PageModel getMyCustomers(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page);

	//void giveUpCustomer(SysUsers user, String objectId);

	PageModel getPublicCustomers(String keyword, Map<String, Object> filter, String sort, int pageSize, int pageNo);

	void applyPublicCustomer(SysUsers user, String objectId);

//	void setCustomerMaintenance(SysUsers user, String objectId, String maintenanceUserId);

	/**
	 *
	 * @param objectName
	 * @param mobile
	 * @param objectNature
	 * @return repeactUserId
	 */
	String checkCustomerRepeat(SysUsers user, String objectName, String mobile, Integer objectNature);

	PresellVisitors getPreSalleClue(String saleClueId);

	List<PresellVisitorsBackVO> getSaleClueBackByCustomer(String customerId);

	List<PresellVisitorsBackVO> getSaleClueBackByCule(String saleClueId);

	PageModel getClueBacks(SysUsers user, Map<String, Object> postJson, int pageSize, int pageNo);

    CustomerRetainVehicleOverview removeVehicleOverview(SysUsers attributeFromSession, String selfId);

	PageModel getRelatedObjects(SysUsers user, String keyword, Map<String, Object> filter, String sort, int perPage, int page);

	List<PresellVisitorsRepeatCheck> getCustomerRepeat(String customerId);

	PresellVisitors removeSaleClue(SysUsers attributeFromSession, String saleClueId);

	PresellVisitorsBack getVisit(SysUsers user, String selfId);

	PresellVisitors updatePreSalleClueResult(SysUsers user, String saleClueId, Short visitResult, String reason);

	List<Map<String,Object>> getMyCalendarCount(SysUsers attributeFromSession, Map<String, Object> postJson);

	Map<String, Object> getCustomerMap(SysUsers user, Map<String, Object> postJson);

    PageModel<Object> listRelatedObjects(HashMap filter_map, int pageNo, int pageSize);

	InterestedCustomers getInterestedCustomers(String objectId);

	BaseRelatedObjects getBaseRelatedObjects(String objectId);

	/**
	 * 创建客户档案
	 * @param objectId 意向客户ID
	 * @param force 强制创建档案，不返回确认信息
	 */
	BaseRelatedObjects createRelatedObject(String objectId, boolean force);

	/**
	 * 检查意向客户信息是否已生成客户档案
	 * @param objectId
	 */
	boolean existsRelatedObject(String objectId);

	/**
	 * 客户跟进查询统计
	 * @param filterMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageModel<Object> visitSummary(String type, Map<String, Object> filterMap, int pageNo, int pageSize);

	Map<String, Long> visitSummaryForTotal();

	/**
	 * 跟进检查
	 * @param backId 跟进ID
	 * @param type 跟进的类型（0：意向客户跟进；1：有效客户跟进）
	 * @return
	 */
	Map<String, Object> checkVisit(String backId, String type, String content);

	/**
	 * 获取指定客户的动态
	 * @param customerId 客户ID
	 * @param type 动态类型（1：合同；5：报价单；10：跟进；15：跟进逾期；null/0：所有）
	 * @return
	 */
	List<Map<String, Object>> getCustomerInformation(String customerId, int type);

}
