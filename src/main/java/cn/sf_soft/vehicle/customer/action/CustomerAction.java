package cn.sf_soft.vehicle.customer.action;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom.SaleCustomer;
import cn.sf_soft.common.gson.GsonUtil;
import cn.sf_soft.common.model.PageModel;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.common.util.HttpSessionStore;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.office.approval.model.VehicleArchives;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.model.*;
import cn.sf_soft.vehicle.customer.service.CustomerService;
import cn.sf_soft.vehicle.customer.service.impl.BaseCustomerGroupsService;
import cn.sf_soft.vehicle.customer.service.impl.InterestedCustomersService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

public class CustomerAction extends BaseAction {

    /**
     *
     */
    private static final long serialVersionUID = -8093664833818726438L;
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerAction.class);
    @Resource
    private CustomerService customerService;

    @Autowired
    private InterestedCustomersService interestedCustomersService;

    @Autowired
    private BaseCustomerGroupsService baseCustomerGroupsService;

    private String keyword;
    private String customerId;
    private String shortcut;
    private String saleClueId;
    private CustomerVO customer;
    private CustomerRetainVehicle retainVehicle;
    private PresellVisitorsVO intentionClue;//意向线索
    private PresellVisitorsBack backVisitInfo;//回访信息
    private PresellVisitorsBack nextBackVisitPlan;//下次回访计划
    private int perPage;
    private int page;
    private String filter;
    private String sort;
    private String objectId; // 客户ID
    private String visitorNo;
    private String contractNo;
    private String jsonData;
    private String backId;
    private String checkContent;
    private String outStockNo;
    private String selfId;
    private String maintenanceUserId;
    private String objectName;
    private Integer objectNature;
    private String mobile;
    private Short visitResult;
    private String reason;
    /**
     * 客户群Id
     */
    private String customerGroupId;


    private String commentContent;


    private String commentId;

    /**
     * 自定义的
     */
    private String customFilter;

    public void setCustomFilter(String customFilter) {
        this.customFilter = customFilter;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public void setOutStockNo(String outStockNo) {
        this.outStockNo = outStockNo;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public void setSaleClueId(String saleClueId) {
        this.saleClueId = saleClueId;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setVisitorNo(String visitorNo) {
        this.visitorNo = visitorNo;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }


    public void setBackId(String backId) {
        this.backId = backId;
    }

    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public void setCustomer(String customerJson) {
        try {
            this.customer = gson.fromJson(customerJson, CustomerVO.class);
        } catch (JsonSyntaxException e) {
            logger.error("参数错误" + customerJson, e);
            addFieldError("intentionCustomer", "参数格式错误");
        }
    }

    public void setRetainVehicle(String retainVehicleJson) {
        try {
            this.retainVehicle = gson.fromJson(retainVehicleJson, CustomerRetainVehicle.class);
        } catch (JsonSyntaxException e) {
            logger.error("参数错误" + retainVehicleJson, e);
            addFieldError("retainVehicle", "参数格式错误");
        }
    }

    public void setIntentionClue(String intentionClueJson) {
        try {
            this.intentionClue = gson.fromJson(intentionClueJson, PresellVisitorsVO.class);
        } catch (JsonSyntaxException e) {
            logger.error("参数错误" + intentionClueJson, e);
            addFieldError("intentionClue", "参数格式错误");
        }
    }

    public void setBackVisitInfo(String backVisitInfoJson) {
        try {
            this.backVisitInfo = gson.fromJson(backVisitInfoJson, PresellVisitorsBack.class);
        } catch (JsonSyntaxException e) {
            logger.error("参数错误" + backVisitInfoJson, e);
            addFieldError("backVisitInfo", "参数格式错误");
        }
    }

    public void setNextBackVisitPlan(String nextBackVisitPlanJson) {
        try {
            this.nextBackVisitPlan = gson.fromJson(nextBackVisitPlanJson, PresellVisitorsBack.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            logger.error("参数错误", e);
            addFieldError("nextBackVisitPlan", "参数格式错误");
        }
    }

    /**
     * 根据关键字获取当前用户可以看到的意向客户信息
     *
     * @return
     */
    @Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW, SaleCustomer.CUSTOMER_PLAN_TIME, SaleCustomer.CUSTOMER_CHECK, SaleCustomer.CUSTOMER_CHECKDEP,
            SaleCustomer.CUSTOMER_RECTIFY, SaleCustomer.CUSTOMER_SELECT, SaleCustomer.CUSTOMER_SERVICE_BACK, SaleCustomer.EXPORT, SaleCustomer.MESSAGE})
    public String getCustomerByKeyWord() {
        List<CustomerVO> results = customerService.getCustomerByKeyword((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), keyword);
        setResponseData(results);
        return SUCCESS;
    }

    /**
     * 根据快捷方式获取当前用户可见的意向客户信息
     *
     * @return
     */
    @Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW, SaleCustomer.CUSTOMER_PLAN_TIME, SaleCustomer.CUSTOMER_CHECK, SaleCustomer.CUSTOMER_CHECKDEP,
            SaleCustomer.CUSTOMER_RECTIFY, SaleCustomer.CUSTOMER_SELECT, SaleCustomer.CUSTOMER_SERVICE_BACK, SaleCustomer.EXPORT, SaleCustomer.MESSAGE})
    public String getCustomerByShortcut() {
        List<CustomerVO> data = customerService.getCustomerByShortCut((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), shortcut);
        setResponseData(data);
        return SUCCESS;
    }

    /**
     * 获取某个客户的销售线索
     *
     * @return
     */
    @Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW, SaleCustomer.CUSTOMER_PLAN_TIME, SaleCustomer.CUSTOMER_CHECK, SaleCustomer.CUSTOMER_CHECKDEP,
            SaleCustomer.CUSTOMER_RECTIFY, SaleCustomer.CUSTOMER_SELECT, SaleCustomer.CUSTOMER_SERVICE_BACK, SaleCustomer.EXPORT, SaleCustomer.MESSAGE})
    public String getSaleClue() {
        List<PresellVisitorsVO> data = customerService.getInentionClueListByCustomerId((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), customerId);
        setResponseData(data);
        return SUCCESS;
    }

    /**
     * 获取某条销售线索的回访记录
     *
     * @return
     */
    @Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW, SaleCustomer.CUSTOMER_PLAN_TIME, SaleCustomer.CUSTOMER_CHECK, SaleCustomer.CUSTOMER_CHECKDEP,
            SaleCustomer.CUSTOMER_RECTIFY, SaleCustomer.CUSTOMER_SELECT, SaleCustomer.CUSTOMER_SERVICE_BACK, SaleCustomer.EXPORT, SaleCustomer.MESSAGE})
    public String getBackVisitList() {
        if (saleClueId != null) {
            setResponseData(customerService.getBackVisitList(saleClueId));
        } else if (customerId != null) {
            List<PresellVisitorsVO> data = customerService.getInentionClueListByCustomerId((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), customerId);
            List<PresellVisitorsBack> result = new ArrayList<PresellVisitorsBack>();
            for (PresellVisitorsVO v : data) {
                List<PresellVisitorsBack> split = customerService.getBackVisitList(v.getVisitorNo());
                result.addAll(split);
            }
            setResponseData(result);
        } else {
            setResponseData(null);
        }
        return SUCCESS;
    }

    /**
     * 是否可以新建意向客户
     *
     * @return
     */
    @Access(pass = true)
    public String canCreateCustomer() {
        SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
        String msg = customerService.canCreateCustomer(user);
        if (msg != null) {
            return showErrorMsg(msg);
        }
        setResponseData("OK");
        return SUCCESS;
    }

    /**
     * 更新意向客户信息
     *
     * @return
     */
    @Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW, SaleCustomer.CUSTOMER_CHECK, SaleCustomer.CUSTOMER_CHECKDEP, SaleCustomer.CUSTOMER_SERVICE_BACK})
    public String updateCustomer() {
        if (customer == null) {
            return showErrorMsg("参数错误:客户信息不能为空");
        }
        customerService.updateCustomer(customer);
        setResponseData("OK");
        return SUCCESS;
    }

    /**
     * 获取编辑意向客户及销售线索时所需要的基础数据
     *
     * @return
     */
    @Access(pass = true)
    public String getBaseDataOfEditCustomer() {
        setResponseData(customerService.getBaseDataOfEditCustomer());
        return SUCCESS;
    }

    /**
     * 新建意向客户
     *
     * @return
     */
    @Access(needPopedom = SaleCustomer.CUSTOMER_FOLLOW)
    public String createCustomer() {
        if (customer == null) {
            return showErrorMsg("参数错误:客户信息不能为空!");
        }
        if (intentionClue == null) {
            return showErrorMsg("参数错误:意向线索不能为空!");
        }
        if (nextBackVisitPlan == null) {
            return showErrorMsg("参数错误:必须制定回访计划!");
        }
        List<CustomerRetainVehicle> retainVehicles = new ArrayList<CustomerRetainVehicle>(1);
        retainVehicles.add(retainVehicle);
        customerService.createIntentionCustomer(customer, retainVehicles, intentionClue, nextBackVisitPlan);
        setResponseData("新建成功!");
        return SUCCESS;
    }

    /**
     * 判断是否可以新建意向线索
     *
     * @return
     */
    @Access(pass = true)
    public String canCreateSaleClue() {
        String msg = customerService.canCreateIntentionClue(customerId, (SysUsers) getAttributeFromSession(Attribute.SESSION_USER));
        if (msg != null) {
            return showErrorMsg(msg);
        }
        setResponseData("OK");
        return SUCCESS;
    }

    /**
     * 更新意向线索
     *
     * @return
     */
    @Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW, SaleCustomer.CUSTOMER_CHECK, SaleCustomer.CUSTOMER_CHECKDEP, SaleCustomer.CUSTOMER_SERVICE_BACK})
    public String updateSaleClue() {
        if (intentionClue == null) {
            return showErrorMsg("参数错误:线索不能为空");
        }
        customerService.updateIntentionClue(intentionClue, (SysUsers) getAttributeFromSession(Attribute.SESSION_USER));
        setResponseData("修改成功");
        return SUCCESS;
    }

    /**
     * 新建销售线索
     *
     * @return
     */
    @Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW, SaleCustomer.CUSTOMER_CHECK, SaleCustomer.CUSTOMER_CHECKDEP, SaleCustomer.CUSTOMER_SERVICE_BACK})
    public String createSaleClue() {
        if (intentionClue == null) {
            return showErrorMsg("参数错误:线索不能为空");
        } else if (Tools.isEmpty(intentionClue.getVisitorId())) {
            return showErrorMsg("参数错误:未指定客户ID");
        }
        if (nextBackVisitPlan == null) {
            return showErrorMsg("参数错误:必须制定下次回访计划");
        }
        customerService.createIntentionClue(intentionClue, nextBackVisitPlan, (SysUsers) getAttributeFromSession(Attribute.SESSION_USER));
        setResponseData("新建成功!");
        return SUCCESS;
    }

    /**
     * 获取某个客户，当前销售员有待回访的线索
     *
     * @return
     */
    @Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW})
    public String getTobeBackVisitPlan() {
        SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
        PresellVisitorsBack backVisitPlan = customerService.getToBeBackVisitPlan(customerId, user.getUserId());
        if (backVisitPlan == null) {
            return showErrorMsg("没有需要您回访的计划");
        }
        setResponseData(backVisitPlan);
        return SUCCESS;
    }

    /**
     * 记录回访信息
     *
     * @return
     */
    @Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW, SaleCustomer.CUSTOMER_CHECK, SaleCustomer.CUSTOMER_CHECKDEP, SaleCustomer.CUSTOMER_SERVICE_BACK})
    public String recordBackVisit() {
        if (backVisitInfo == null) {
            return showErrorMsg("参数错误:回访信息不能为空");
        }
        customerService.addBackVisit(backVisitInfo, nextBackVisitPlan, (SysUsers) getAttributeFromSession(Attribute.SESSION_USER));
        setResponseData("操作成功");
        return SUCCESS;
    }

    /**
     * 获取车辆型号基础数据
     *
     * @return
     */
    @Access(pass = true)
    public String getVehicleType() {
        SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
        setResponseData(customerService.getVehicleType(user.getDefaulStationId(), keyword));
        return SUCCESS;
    }

    private Map<String, Object> getFilter() {
        try {
            HashMap filter_map = gson.fromJson(filter, HashMap.class);
            return filter_map;
        } catch (Exception e) {
            throw new ServiceException("查询条件不合法");
        }
    }

    //自定义查询条件
    private Map<String, Object> getCustomFilter() {
        try {
            HashMap filter_map = gson.fromJson(customFilter, HashMap.class);
            return filter_map;
        } catch (Exception e) {
            throw new ServiceException("查询条件不合法");
        }
    }

    private Map<String, Object> getPostJson() {
        try {
            HashMap json_map = gson.fromJson(jsonData, HashMap.class);
            return json_map;
        } catch (Exception e) {
            throw new ServiceException("提交数据不合法");
        }
    }

    /**
     * 查找所有 维系人角色用户
     *
     * @return
     */
    @Access(pass = true)
    public String getAllMaintenanceUser() {
        Object results = interestedCustomersService.getAllMaintenanceUser(false);
        setResponseCommonData(results);
        return SUCCESS;
    }

    /**
     * 查找检查人
     *
     * @return
     */
    @Access(pass = true)
    public String getChecker() {
        Object results = interestedCustomersService.getChecker();
        setResponseCommonData(results);
        return SUCCESS;
    }

    /**
     * 意向客户列表
     *
     * @return
     */
    @Access(pass = true)
    public String getCustomers() {
        PageModel results = customerService.getCustomers((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), keyword, getFilter(), sort, perPage, page);
        setResponseCommonData(results);
        return SUCCESS;
    }


    /**
     * 意向客户，返回所有状态的意向客户
     *
     * @return
     */
    @Access(pass = true)
    public String getMyCustomersByAllVisitResult() {
        Map<String, Object> results = interestedCustomersService.getMyCustomersByAllVisitResultNew(keyword, getFilter(), getCustomFilter(), sort, pageSize, pageNo);
        setResponseCommonData(results);
        return SUCCESS;
    }


    /**
     * 获取我的客户信息列表
     *
     * @return
     */
    @Access(pass = true)
    public String getMyCustomers() {
        PageModel results = interestedCustomersService.getMyCustomers(keyword, getFilter(), getCustomFilter(), sort, pageSize, pageNo);
        setResponseCommonData(results);
        return SUCCESS;
    }

    /**
     * 查询公海客户
     *
     * @return
     */
    @Access(pass = true)
    public String getPublicCustomers() {
        PageModel results = interestedCustomersService.getPublicCustomers(keyword, getFilter(), sort, pageSize, pageNo);
        setResponseCommonData(results);
        return SUCCESS;
    }


    /**
     * 获取待跟进的线索
     *
     * @return
     */
    @Access(pass = true)
    public String getPendingVisitorsBack() {
        Object results = interestedCustomersService.getPendingVisitorsBack(objectId);
        setResponseCommonData(results);
        return SUCCESS;
    }

    /**
     * 获取Vehicle列表
     *
     * @return
     */
    @Access(pass = true)
    public String getVehicleStore() {
        PageModel results = customerService.getVehicleStore((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), keyword, getFilter(), sort, perPage, page);
        setResponseCommonData(results);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getRelatedObjects() {
        PageModel results = customerService.getRelatedObjects((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), keyword, getFilter(), sort, pageSize, pageNo);
        setResponseCommonData(results);
        return SUCCESS;
    }

    /**
     * 获取系统基础数据
     *
     * @return
     */
    @Access(pass = true)
    public String getSysInitData() {
        Map<String, Object> visit_result = customerService.getSysInitData();
        setResponseData(visit_result);
        return SUCCESS;
    }

    /**
     * 获取客户信息
     *
     * @return
     */
    @Access(pass = true)
    public String getCustomer() {
        Map<String, Object> customer = customerService.getCustomerInterestedCustomers(objectId);
        setResponseData(customer);
        return SUCCESS;
    }

	/*@Access(pass=true)
	public String giveUpCustomer(){
		customerService.giveUpCustomer((SysUsers)getAttributeFromSession(Attribute.SESSION_USER), objectId);
		return SUCCESS;
	}*/


    /**
     * 获取车辆概述
     *
     * @return
     */
    @Access(pass = true)
    public String getVehicleOverview() {
        List<CustomerRetainVehicleOverview> vehicleOverview = customerService.getVehicleOverview(objectId);
        setResponseData(vehicleOverview);
        return SUCCESS;
    }

    @Access(pass = true)
    public String addVehicleOverview() {
        CustomerRetainVehicleOverview result = customerService.addVehicleOverview((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), getPostJson());
        setResponseData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateVehicleOverview() {
        CustomerRetainVehicleOverview result = customerService.updateVehicleOverview((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), selfId, getPostJson());
        setResponseData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeVehicleOverview() {
        customerService.removeVehicleOverview((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), selfId);
        return SUCCESS;
    }

    /**
     * 获取联系人
     *
     * @return
     */
    @Access(pass = true)
    public String getOrganizational() {
        List<CustomerOrganizationalStructure> organizational = customerService.getOrganizational(objectId);
        setResponseData(organizational);
        return SUCCESS;
    }


    @Access(pass = true)
    public String addOrganizational() {
        CustomerOrganizationalStructure result = customerService.addOrganizational((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), getPostJson());
        setResponseData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateOrganizational() {
        CustomerOrganizationalStructure result = customerService.updateOrganizational((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), selfId, getPostJson());
        setResponseData(result);
        return SUCCESS;
    }

    /**
     * 获取车辆保有详情
     *
     * @return
     */
    @Access(pass = true)
    public String getVehicleArchives() {
        List<VehicleArchives> vehicleArchives = customerService.getVehicleArchives(objectId);
        setResponseData(vehicleArchives);
        return SUCCESS;
    }

    @Access(pass = true)
    public String addVehicleArchives() {
        VehicleArchives vehicleArchives = customerService.addVehicleArchives((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), getPostJson());
        setResponseData(vehicleArchives);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateVehicleArchives() {
        VehicleArchives vehicleArchives = customerService.updateVehicleArchives((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), selfId, getPostJson());
        setResponseData(vehicleArchives);
        return SUCCESS;
    }

    /**
     * 获取消费统计
     *
     * @return
     */
    @Access(pass = true)
    public String getConsumption() {
        List<Map<String, Object>> consumption = customerService.getConsumption(objectId);
        setResponseData(consumption);
        return SUCCESS;
    }


    /**
     * 获取销售线索
     *
     * @return
     */
    @Access(pass = true)
    public String getSaleClues() {
        PageModel results = customerService.getSaleClues((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), keyword, getFilter(), sort, perPage, page);
        setResponseCommonData(results);
        return SUCCESS;
    }

    @Access(pass = true)
    public String removeSaleClue() {
        customerService.removeSaleClue((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), saleClueId);
        return SUCCESS;
    }

    /**
     * 获取销售线索详情
     *
     * @return
     */
    @Access(pass = true)
    public String getSaleClueDetail() {
        Map<String, Object> saleClue = customerService.getSaleClueDetail(visitorNo);
        setResponseData(saleClue);
        return SUCCESS;
    }

    /**
     * 获取维系列表
     *
     * @return
     */
    @Access(pass = true)
    public String getVisits() {
        List<PresellVisitorsBack> visits = customerService.getVisits(customerId);
        setResponseData(visits);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getSaleClueBackByCustomer() {
        List<PresellVisitorsBackVO> list = customerService.getSaleClueBackByCustomer(customerId);
        setResponseData(list);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getSaleClueBackByCule() {
        List<PresellVisitorsBackVO> list = customerService.getSaleClueBackByCule(saleClueId);
        setResponseData(list);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getVisit() {
        PresellVisitorsBack result = customerService.getVisit((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), selfId);
        setResponseData(result);
        return SUCCESS;
    }

    /**
     * 增加跟进
     *
     * @return
     */
    @Access(pass = true)
    public String addVisit() {
        PresellVisitorsBack result = customerService.addVisit((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), customerId, getPostJson());
        this.selfId = result.getBackId();
        return this.getVisit();
    }

    /**
     * 批量增加跟进
     *
     * @return
     */
    public String batchAddVistBySms() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        if (null == jsonObject || jsonObject.isJsonNull()) {
            throw new ServiceException("请求参数不能为空");
        }
        JsonElement element = jsonObject.get("customers");
        if (null == element || element.isJsonNull()) {
            throw new ServiceException("请求参数必须提供客户标识");
        }
        List<String> customerIds = gson.fromJson(element, List.class);
        String content = GsonUtil.getAsString(jsonObject, "content");
        customerService.batchAddVistBySms(HttpSessionStore.getSessionUser(), customerIds, content);
        setResponseData(null);
        return SUCCESS;
    }

    /**
     * 修改跟进
     *
     * @return
     */
    @Access(pass = true)
    public String updateVisit() {
        PresellVisitorsBack result = customerService.updateVisit((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), backId, getPostJson());
        this.selfId = result.getBackId();
        return this.getVisit();
    }

    @Access(pass = true)
    public String getPreSalleClue() {
        PresellVisitors result = customerService.getPreSalleClue(saleClueId);
        setResponseData(result);
        return SUCCESS;
    }

    /**
     * 增加线索
     *
     * @return
     */
    @Access(pass = true)
    public String addPreSalleClue() {
        PresellVisitors result = customerService.addPreSalleClue((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), customerId, getPostJson());
        setResponseData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updatePreSalleClue() {
        PresellVisitors result = customerService.updatePreSalleClue((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), saleClueId, getPostJson());
        setResponseData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updatePreSalleClueResult() {
        PresellVisitors result = customerService.updatePreSalleClueResult((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), saleClueId, visitResult, reason);
        setResponseData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getCallbacks() {
        PageModel results = customerService.getCallbacks((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), keyword, getFilter(), sort, perPage, page);
        setResponseCommonData(results);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getCallbackDetail() {
        Map<String, Object> detail = customerService.getCallbackDetail(outStockNo);
        setResponseData(detail);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getUploadToken() {
//		String accessKey = "JwNkqQ73f7wKrMiDu-YbkEC-eEpOjNvi4VpK-m4X";
//		String secretKey = "GThGCGBitVJe0Sl3PVrPlGZWcFDBUC4ayvGfrEf_";
//		String bucket = "dvplus-mobile";
        String accessKey = this.config.getApplicationConfig("qiniu.accessKey");
        String secretKey = this.config.getApplicationConfig("qiniu.secretKey");
        String bucket = this.config.getApplicationConfig("qiniu.bucket");
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("key", "crm/upload/" + UUID.randomUUID().toString());
        putPolicy.put("save_key", true);
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        Map<String, String> response = new HashMap<String, String>(1);
        response.put("uptoken", upToken);
        setResponseCommonData(response);
        return SUCCESS;
    }

    @Access(pass = true)
    public String addClueBack() {
        PresellVisitorsBack result = customerService.addClueBack((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), saleClueId, getPostJson());
        // setResponseData(result);
        backId = result.getBackId();
        return this.getClueBack();
    }


    @Access(pass = true)
    public String updateClueBack() {
        PresellVisitorsBack result = customerService.updateClueBack((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), backId, getPostJson());
        return getClueBack();
    }

    @Access(pass = true)
    public String getClueBack() {
        PresellVisitorsBackVO result = customerService.getClueBack((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), backId);
        setResponseData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getClueBacks() {
        PageModel result = customerService.getClueBacks((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), getPostJson(), pageSize, pageNo);
        setResponseCommonData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String checkClueBack() {
        PresellVisitorsBack result = customerService.checkClueBack((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), backId, checkContent);
        setResponseData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getCallback() {
        SellerCallBack results = customerService.getCallback((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), backId);
        setResponseData(results);
        return SUCCESS;
    }

    @Access(pass = true)
    public String addCallback() {
        SellerCallBack results = customerService.addCallback((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), getPostJson());
        setResponseData(results);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateCallback() {
        SellerCallBack results = customerService.updateCallback((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), backId, getPostJson());
        setResponseData(results);
        return SUCCESS;
    }

    @Access(pass = true)
    public String updateCustomerBase() {
        InterestedCustomers result = customerService.updateCustomerBase((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), customerId, getPostJson());
        setResponseData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String addCustomerBase() {
        InterestedCustomers result = customerService.addCustomerBase((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), getPostJson());
        setResponseData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String getMyCalendar() {
        PageModel result = customerService.getMyCalendar(
                (SysUsers) getAttributeFromSession(Attribute.SESSION_USER),
                getPostJson(), pageSize, pageNo);
        setResponseCommonData(result);
        return SUCCESS;
    }


    @Access(pass = true)
    public String getMyCalendarCount() {
        List<Map<String, Object>> result = customerService.getMyCalendarCount((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), getPostJson());
        setResponseData(result);
        return SUCCESS;
    }

    @Access(pass = true)
    public String applyPublicCustomer() {
        customerService.applyPublicCustomer((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), objectId);
        return SUCCESS;
    }

    // @Access(needPopedom = SaleCustomer.EDIT_MAINTENANCE)
	/*@Access(pass=true)
	public String setCustomerMaintenance(){
		customerService.setCustomerMaintenance((SysUsers)getAttributeFromSession(Attribute.SESSION_USER), objectId, maintenanceUserId);
		return SUCCESS;
	}*/

    public void setMaintenanceUserId(String maintenanceUserId) {
        this.maintenanceUserId = maintenanceUserId;
    }

    @Access(pass = true)
    public String checkCustomerRepeat() {
        String customerId = customerService.checkCustomerRepeat((SysUsers) getAttributeFromSession(Attribute.SESSION_USER), objectName, mobile, objectNature);
        if (customerId != null) {
            this.objectId = customerId;
            this.getCustomer();
        } else {
            setResponseData(null);
        }
        return SUCCESS;
    }

    @Access(pass = true)
    public String getCustomerMap() {
        setResponseData(customerService.getCustomerMap(
                (SysUsers) getAttributeFromSession(Attribute.SESSION_USER),
                getPostJson()
        ));
        return SUCCESS;
    }


    @Access(pass = true)
    public String getCustomerRepeat() {
        List<PresellVisitorsRepeatCheck> list = customerService.getCustomerRepeat(customerId);
        setResponseData(list);
        return SUCCESS;
    }


    @Access(pass = true)
    public String listRelatedObjects() {
        HashMap filter_map = gson.fromJson(filter, HashMap.class);
        PageModel<Object> pageModel = customerService.listRelatedObjects(filter_map, pageNo, pageSize);
        ResponseMessage<Object> respMsg = new ResponseMessage();
        respMsg.setPageNo(pageModel.getPage());
        respMsg.setPageSize(pageModel.getPerPage());
        respMsg.setTotalSize(pageModel.getTotalSize());
        respMsg.setData(pageModel.getData());
        setResponseMessageData(respMsg);
        return SUCCESS;
    }

    /**
     * 接收报文：{jsonData:{"objectId:"意向客户ID/String/必填", "force": "是否强制更新/boolean/可空/缺省：false"}}<p>
     * 返回报文：
     * <ul>
     * <li>
     * 当force=false时，errcode=50，msg是确认信息
     * </li>
     * <li>
     * 当force=true时，强制保存不返回确认信息
     * </li>
     * </ul>
     *
     * @return
     */
    @Access(pass = true)
    public String createRelatedObject() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        if (null == jsonObject || jsonObject.isJsonNull()) {
            throw new ServiceException("请求参数不能为空");
        }
        JsonElement element = jsonObject.get("objectId");
        if (null == element || element.isJsonNull()) {
            throw new ServiceException("请求参数objectId不能为空");
        }
        String objectId = element.getAsString();
        element = jsonObject.get("force");
        boolean force = false;
        if (null != element && !element.isJsonNull()) {
            force = element.getAsBoolean();
        }
        try {
            BaseRelatedObjects relatedObject = customerService.createRelatedObject(objectId, force);
            setResponseData(relatedObject);
        } catch (ServiceException e) {
            if (e.getCode() != 0) {
                ResponseMessage message = new ResponseMessage();
                message.setErrcode(e.getCode());
                message.setMsg(e.getMessage());
                setResponseMessageData(message);
            } else {
                throw e;
            }
        }
        return SUCCESS;
    }

    /**
     * 客户是否已建立客户档案
     *
     * @return
     */
    @Access(pass = true)
    public String existsRelatedObject() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        if (null == jsonObject || jsonObject.isJsonNull()) {
            throw new ServiceException("请求参数不能为空");
        }
        JsonElement element = jsonObject.get("objectId");
        if (null == element || element.isJsonNull() || org.apache.commons.lang3.StringUtils.isEmpty(element.getAsString())) {
            throw new ServiceException("请求参数objectId不能为空");
        }
        String objectId = element.getAsString();
        boolean exists = customerService.existsRelatedObject(objectId);
        Map<String, Object> result = new HashMap<>(1);
        result.put("exists", exists);
        setResponseData(result);
        return SUCCESS;
    }

    /**
     * 统计及其预警
     *
     * @return
     */
    @Access(pass = true)
    public String visitSummary() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        if (null != jsonObject && !jsonObject.isJsonNull()) {
            if (!jsonObject.has("type") || jsonObject.get("type").isJsonNull()) {
                throw new ServiceException("统计类型不能为空");
            }
            String type = jsonObject.get("type").getAsString();
            Map<String, Object> condition = (Map<String, Object>) this.gson.fromJson(jsonObject.get("condition"), Map.class);
            PageModel<Object> pageModel = this.customerService.visitSummary(type, condition, pageNo, pageSize);
            ResponseMessage<Object> respMsg = new ResponseMessage();
            respMsg.setPageNo(pageModel.getPage());
            respMsg.setPageSize(pageModel.getPerPage());
            respMsg.setTotalSize(pageModel.getTotalSize());
            respMsg.setData(pageModel.getData());
            setResponseMessageData(respMsg);
        }

        return SUCCESS;
    }

    @Access(pass = true)
    public String visitSummaryForTotal() {
        setResponseData(customerService.visitSummaryForTotal());
        return SUCCESS;
    }

    /**
     * 跟进检查
     *
     * @return
     */
    @Access(pass = true)
    public String checkVisit() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        if (null != jsonObject && !jsonObject.isJsonNull()) {
            if (!jsonObject.has("backId") || jsonObject.get("backId").isJsonNull()) {
                throw new ServiceException("跟进标识不能为空");
            }
            if (!jsonObject.has("type") || jsonObject.get("type").isJsonNull()) {
                throw new ServiceException("跟进类型不能为空");
            }
            String backId = jsonObject.get("backId").getAsString();
            String type = jsonObject.get("type").getAsString();
            String content = (!jsonObject.has("content") || jsonObject.get("content").isJsonNull()) ? null : jsonObject.get("content").getAsString();
            setResponseData(this.customerService.checkVisit(backId, type, content));
        } else {
            throw new ServiceException("请求参数不合法");
        }
        return SUCCESS;
    }

    /**
     * 批量放弃维系人
     *
     * @return
     */
    @Access(pass = true)
    public String batchAbandonMaintainer() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        if (!jsonObject.isJsonNull()) {
            Map<String, List<Object>> result = this.interestedCustomersService.abandonMaintenance(jsonObject);
            setResponseData(result);
        } else {
            throw new ServiceException("请求参数不合法");
        }
        return SUCCESS;
    }

    /**
     * 客户动态
     *
     * @return
     */
    @Access(pass = true)
    public String customerInformation() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        if (null != jsonObject && !jsonObject.isJsonNull()) {
            if (!jsonObject.has("customerId") || jsonObject.get("customerId").isJsonNull()) {
                throw new ServiceException("请求参数不合法，必须提供客户标识");
            }
            String customerId = jsonObject.get("customerId").getAsString();
            int type = 0;
            if (jsonObject.has("type") && !jsonObject.get("type").isJsonNull()) {
                type = jsonObject.get("type").getAsInt();
            }
            setResponseData(this.customerService.getCustomerInformation(customerId, type));
        } else {
            throw new ServiceException("请求参数不合法");
        }
        return SUCCESS;
    }


    /**
     * 保存客户群
     *
     * @return
     */
    @Access(pass = true)
    public String saveCustomerGroup() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Object>> rtnData = baseCustomerGroupsService.saveCustomerGroup(jsonObject);
        setResponseData(rtnData);
        return SUCCESS;
    }

    private String optionValue;

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    /**
     * 保存意向客户
     *
     * @return
     */
    @Access(pass = true)
    public String saveInterestedCustomer() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Object>> rtnData = interestedCustomersService.saveInterestedCustomer(jsonObject, optionValue);
        setResponseData(rtnData);
        return SUCCESS;
    }


    /**
     * 意向客户明细
     *
     * @return
     */
    @Access(pass = true)
    public String getInterestedCustomerDetail() {
        Map<String, List<Object>> rtnData = interestedCustomersService.getInterestedCustomerDetail(objectId);
        setResponseData(rtnData);
        return SUCCESS;
    }


    /**
     * 批量更新维系人
     *
     * @return
     */
    @Access(pass = true)
    public String batchUpdateMaintainer() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Object>> rtnData = interestedCustomersService.batchUpdateMaintainer(jsonObject);
        setResponseData(rtnData);
        return SUCCESS;
    }

    /**
     * 批量领用
     *
     * @return
     */
    @Access(pass = true)
    public String batchReceiveInterestedCustomers() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Object>> rtnData = interestedCustomersService.batchReceiveInterestedCustomers(jsonObject);
        setResponseData(rtnData);
        return SUCCESS;
    }


    /**
     * 批量无效
     *
     * @return
     */
    @Access(pass = true)
    public String batchInvalid() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Object>> rtnData = interestedCustomersService.batchInvalid(jsonObject);
        setResponseData(rtnData);
        return SUCCESS;
    }

    /**
     * 批量有效
     *
     * @return
     */
    @Access(pass = true)
    public String batchValid() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Object>> rtnData = interestedCustomersService.batchValid(jsonObject);
        setResponseData(rtnData);
        return SUCCESS;
    }


    /**
     * 新增客户检查（批量）
     *
     * @return
     */
    @Access(pass = true)
    public String batchCheckInterestedCustomers() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Object>> rtnData = interestedCustomersService.batchCheckInterestedCustomers(jsonObject);
        setResponseData(rtnData);
        return SUCCESS;
    }


    /**
     * 检查客户重复
     * @return
     */
    @Access(pass = true)
    public String validateCustomerRepeat(){
        HashMap json_map = gson.fromJson(jsonData, HashMap.class);
        Map<String, Object> rtnData = interestedCustomersService.validateCustomerRepeat(json_map);
        setResponseData(rtnData);
        return SUCCESS;
    }


    /**
     * 跟进检查(批量)
     *
     * @return
     */
    @Access(pass = true)
    public String batchCheckVisitorsBack() {
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
        Map<String, List<Object>> rtnData = interestedCustomersService.batchCheckVisitorsBack(jsonObject);
        setResponseData(rtnData);
        return SUCCESS;
    }


    /**
     * 查找客户群
     *
     * @return
     */
    @Access(pass = true)
    public String getCustomerGroupList() {
        PageModel rtnData = interestedCustomersService.getCustomerGroupList(getFilter(), pageNo, pageSize);
//        setResponseData(rtnData);
        setResponseCommonData(rtnData);
        return SUCCESS;
    }


    /**
     * 查找客户群明细
     *
     * @return
     */
    @Access(pass = true)
    public String getCustomerGroupDetail() {
        Map<String, List<Map<String, Object>>> rtnData = interestedCustomersService.getCustomerGroupDetail(customerGroupId);
        setResponseData(rtnData);
        return SUCCESS;
    }


    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setObjectNature(Integer objectNature) {
        this.objectNature = objectNature;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setVisitResult(Short visitResult) {
        this.visitResult = visitResult;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
