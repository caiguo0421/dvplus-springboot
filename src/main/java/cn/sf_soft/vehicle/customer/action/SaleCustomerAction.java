package cn.sf_soft.vehicle.customer.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.JsonSyntaxException;
import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.annotation.AccessPopedom.SaleCustomer;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.common.util.CheckPopedom;
import cn.sf_soft.common.util.GetChineseFirstChar;
import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.common.util.Tools;
import cn.sf_soft.user.model.SysUsers;
import cn.sf_soft.vehicle.customer.model.AddCustomerInitData;
import cn.sf_soft.vehicle.customer.model.AddIntentClueInitData;
import cn.sf_soft.vehicle.customer.model.AddValidClueInitData;
import cn.sf_soft.vehicle.customer.model.CustomerVO;
import cn.sf_soft.vehicle.customer.model.BaseVehicleVisitorsBack;
import cn.sf_soft.vehicle.customer.model.CallBackInitData;
import cn.sf_soft.vehicle.customer.model.InterestedCustomers;
import cn.sf_soft.vehicle.customer.model.CustomerMaintenanceWorkgroup;
import cn.sf_soft.vehicle.customer.model.CustomerRetainVehicle;
import cn.sf_soft.vehicle.customer.model.LastPresellVisitors;
import cn.sf_soft.vehicle.customer.model.OjbectNameForCheck;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsBack;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsVO;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsBack;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsFail;
import cn.sf_soft.vehicle.customer.model.PresellVisitorsForCheck;
import cn.sf_soft.vehicle.customer.model.UserMaintenanceWorkgroups;
import cn.sf_soft.vehicle.customer.model.VehicleType;
import cn.sf_soft.vehicle.customer.service.SaleCustomerService;
/**
 * 
 * @Title: 意向客户
 * @date 2013-12-11 下午02:07:37
 * @author cw
 */
public class SaleCustomerAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 636779097701275204L;

	private SaleCustomerService service;
	private String nameOrMobile;
	private String visitorId;
	private String visitorNo;
	private String identifier;// 快捷查询 标识 today今日活动, tomorrow明日活动

	private CustomerVO object;// 待添加的客户信息
	private CustomerRetainVehicle vehicle;// 客户的保有车辆信息
	private PresellVisitorsVO presellVisitors;// 客户有效线索信息
	private PresellVisitorsBack validClueBack;// 有效线索回访计划// 记录回访信息时此对象当做下次回访计划使用
	
	private PresellVisitorsVO presellVisitorsForIntent;//客户的意向线索信息 
	// 意向线索回访计划//记录回访信息时此对象当做本次回访信息使用 //检查回访时当被检查的回访计划使用使用
	private PresellVisitorsBack intentClueBack;
	
	private String vehicleVno;//车辆型号 用于模糊匹配
	private String vehicleName;//车辆名称 用于过滤车辆型号
	
	static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaleCustomerAction.class);

	
	public void setVehicleVno(String vehicleVno) {
		this.vehicleVno = vehicleVno;
	}


	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	
	public void setValidClueBack(String validClueBack) {
		try {
			this.validClueBack = gson.fromJson(validClueBack,
					PresellVisitorsBack.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			addActionError("参数错误");
		}
	}
	
	
	public void setIntentClueBack(String intentClueBack) {
		try {
			this.intentClueBack = gson.fromJson(intentClueBack,
					PresellVisitorsBack.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			addActionError("参数错误");
		}
	}


	public void setPresellVisitorsForIntent(String presellVisitorsForIntent) {
		try {
			this.presellVisitorsForIntent = gson.fromJson(presellVisitorsForIntent,
					PresellVisitorsVO.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			addActionError("参数错误");
		}
	}


	public void setPresellVisitors(String presellVisitors) {
		try {
			this.presellVisitors = gson.fromJson(presellVisitors,
					PresellVisitorsVO.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			addActionError("参数错误");
		}
	}

	public void setVehicle(String vehicle) {
		try {
			this.vehicle = gson.fromJson(vehicle, CustomerRetainVehicle.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			addActionError("参数错误");
		}
	}

	public void setObject(String object) {
		try {
			this.object = gson.fromJson(object, CustomerVO.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			addActionError("参数错误");
		}
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setVisitorNo(String visitorNo) {
		this.visitorNo = visitorNo;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public void setNameOrMobile(String nameOrMobile) {
		if(nameOrMobile != null){
			nameOrMobile = nameOrMobile.replaceAll("'", "").replaceAll("%", "").replaceAll("\\*", "");
		}
		if("null".equals(nameOrMobile)){
			nameOrMobile = "";
		}
		this.nameOrMobile = nameOrMobile;
	}

	public void setService(SaleCustomerService service) {
		this.service = service;
	}

	/**
	 * 获得用户信息
	 * 
	 * @return
	 */
	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW,SaleCustomer.CUSTOMER_PLAN_TIME,SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP,
			SaleCustomer.CUSTOMER_RECTIFY,SaleCustomer.CUSTOMER_SELECT,SaleCustomer.CUSTOMER_SERVICE_BACK,SaleCustomer.EXPORT,SaleCustomer.MESSAGE})
	public String getSaleCustomer() {
		if(nameOrMobile != null && nameOrMobile.matches("[0-9]+")){
			//是数字类型，不能小于3个字符，否则查询结果太大
			if(nameOrMobile.length() > 0 && nameOrMobile.length() < 4){
				throw new ServiceException("电话号码查询关键字不能少于4个字符");
			}
		}
		
		List<CustomerVO> list = service.getSaleCustomer(
				(SysUsers) getAttributeFromSession(Attribute.SESSION_USER),
				nameOrMobile);
		logger.debug("查询的客户数量:"+list.size());
		setResponseData(list);
		return SUCCESS;
	}

	/**
	 * 快捷查询用户信息 今日活动，明日活动等
	 * 
	 * @return
	 */
	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW,SaleCustomer.CUSTOMER_PLAN_TIME,SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP,
			SaleCustomer.CUSTOMER_RECTIFY,SaleCustomer.CUSTOMER_SELECT,SaleCustomer.CUSTOMER_SERVICE_BACK,SaleCustomer.EXPORT,SaleCustomer.MESSAGE})
	public String getSaleCustomerForShortCut() {
		List<CustomerVO> list = service.getSaleCustomerForShortCut(
				(SysUsers) getAttributeFromSession(Attribute.SESSION_USER),
				identifier);
		logger.debug("查询的客户数量:"+list.size());
		setResponseData(list);
		return SUCCESS;
	}

	/**
	 * 获得销售线索
	 * 
	 * @return
	 */
	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW,SaleCustomer.CUSTOMER_PLAN_TIME,SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP,
			SaleCustomer.CUSTOMER_RECTIFY,SaleCustomer.CUSTOMER_SELECT,SaleCustomer.CUSTOMER_SERVICE_BACK,SaleCustomer.EXPORT,SaleCustomer.MESSAGE})
	public String getSaleClue() {
		List<PresellVisitorsVO> list = service.getSaleClue(
				(SysUsers) getAttributeFromSession(Attribute.SESSION_USER),
				visitorId);
		setResponseData(list);
		return SUCCESS;
	}

	/**
	 * 获取某条线索所有回访信息
	 * 
	 * @param visitorNo
	 *            线索ID
	 * @return
	 */
	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW,SaleCustomer.CUSTOMER_PLAN_TIME,SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP,
			SaleCustomer.CUSTOMER_RECTIFY,SaleCustomer.CUSTOMER_SELECT,SaleCustomer.CUSTOMER_SERVICE_BACK,SaleCustomer.EXPORT,SaleCustomer.MESSAGE})
	public String getPresellVisitorsBack() {
		List<PresellVisitorsBack> list = service
				.getPresellVisitorsBack(visitorNo);
		setResponseData(list);
		return SUCCESS;
	}

	/**
	 * 获取某条线索需要记录的回访信息
	 * 
	 * @param visitorNo
	 *            线索ID
	 * @return
	 */
	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW,SaleCustomer.CUSTOMER_PLAN_TIME,SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP,
			SaleCustomer.CUSTOMER_RECTIFY,SaleCustomer.CUSTOMER_SELECT,SaleCustomer.CUSTOMER_SERVICE_BACK,SaleCustomer.EXPORT,SaleCustomer.MESSAGE})
	public String getRegisterPresellVisitorsBack() {
		List<PresellVisitorsBack> list = service
				.getRegisterPresellVisitorsBack(visitorNo);
		setResponseData(list);
		return SUCCESS;
	}

	/**
	 * 获得某客户有待回访的销售线索
	 * 
	 * @param visitorId
	 *            客户id
	 * @param userId
	 *            当前用户id
	 * @return
	 */
	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW,SaleCustomer.CUSTOMER_PLAN_TIME,SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP,
			SaleCustomer.CUSTOMER_RECTIFY,SaleCustomer.CUSTOMER_SELECT,SaleCustomer.CUSTOMER_SERVICE_BACK,SaleCustomer.EXPORT,SaleCustomer.MESSAGE})
	public String getRegisterSaleClue() {
		SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
		List<PresellVisitorsVO> list = service.getRegisterSaleClue(visitorId,
				user.getUserId());
		setResponseData(list);
		return SUCCESS;
	}

	/**
	 * 获得记录回访页面 的基础数据
	 * 
	 * @return
	 */
	@Access(pass = true)
	public String CallBackInitData() {
		//SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
		List<BaseVehicleVisitorsBack> list = service.getVisitorLevel(getDefaultStationId());
		String[] backWay = service.getBackWay();
		String[] purpose = service.getPurpose();
		String[] reason = service.getRreson();
		Map<String, String> map = service.getVisitResult();
		CallBackInitData initData = new CallBackInitData();
		initData.setBackWay(backWay);
		initData.setPurpose(purpose);
		initData.setVisitorLevel(list);
		initData.setReason(reason);
		initData.setMap(map);
		setResponseData(initData);
		return SUCCESS;
	}

	/**
	 * 获取新建客户页面的基础数据
	 * 此处包含有效线索+意向线索的基础数据
	 * @return
	 */
	@Access(pass = true)
	public String getAddCustomerInitData() {
		AddCustomerInitData init = service.getAddCustomerInitData(getDefaultStationId());
		setResponseData(init);
		return SUCCESS;
	}

	/**
	 * 获取新建有效线索页面的基础数据
	 * 
	 * @return
	 */
	@Access(pass = true)
	public String getAddValidClueInitData() {
		AddValidClueInitData init = service.getAddValidClueInitData();
		setResponseData(init);
		return SUCCESS;
	}
	/**
	 * 验证客户名等有效性
	 * @param object
	 * @param objectId
	 * @return
	 */
//	public String checkObjectName(CustomerVO object,String objectId){
//		   //如果为单位，对象名称不允许重复
//		String msg = "";
//		if(object.getObjectNature() == 10){
//			List<OjbectNameForCheck> checkObjectName = service.getObjectNameIsRight(object.getObjectName(), objectId,object.getCertificateNo(),null,object.getShortName(),object.getObjectNature());
//			if(checkObjectName.size() > 0){
//				for(OjbectNameForCheck c:checkObjectName){
//					if(object.getObjectName().equals(c.getObjectName())){
//						msg += "当对象性质为“单位”时，客户名称不允许重复！";
//						break;
//					}
//					if(object.getShortName().equals(c.getShortName())){
//						msg += "当对象性质为“单位”时，客户简称不允许重复！";
//						break;
//					}if(object.getCertificateNo().equals(c.getCertificateNo())){
//						msg += "系统中已存在证件号为'"+object.getCertificateNo()+"'的客户， 证件号码不允许重复！";
//						break;
//					}
//				}
//			}
//		}else{
//			//个人客户
//			List<OjbectNameForCheck> checkObjectName = service.getObjectNameIsRight(object.getObjectName(), objectId,object.getCertificateNo(),object.getMobile(),object.getShortName(),object.getObjectNature());
//			if(checkObjectName.size() > 0){
//				for(OjbectNameForCheck c:checkObjectName){
//					if(!Tools.isEmpty(object.getCertificateNo()) && object.getCertificateNo().equals(c.getCertificateNo())){
//						msg += "系统中已存在证件号为'"+object.getCertificateNo()+"'的客户， 证件号码不允许重复！";
//						break;
//					}
//					if(c.getObjectNature() == 10 && object.getObjectName().equals(c.getObjectName())){
//						msg += "系统中已存在对象性质为“单位”且对象名称为'"+object.getObjectName()+"'的客户,不能保存！";
//						break;
//					}
//					if(c.getObjectNature() == 10 && object.getShortName().equals(c.getShortName())){
//						msg += "系统中已存在对象性质为“单位”且对象简称为'"+object.getShortName()+"'的客户,不能保存！";
//						break;
//					}
//					if(c.getObjectNature() == 20 && object.getObjectName().equals(c.getObjectName()) && object.getShortName().equals(c.getShortName())){
//						msg += "系统中已存在对象名称为'"+object.getObjectName()+"'、手机号码为'"+object.getMobile()+"'的客户，对象名称和手机号码不允许同时重复！";
//						break;
//					}
//				}
//			}
//		}
//		return msg;
//	}
	/**
	 * 查询用户是否存在维系组中
	 * 
	 * @return
	 */
	@Access(pass = true)
	public String getUserMaintenanceWorkgroups() {
		SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
		List<UserMaintenanceWorkgroups> list = service.getUserMaintenanceWorkgroups(user.getUserId(),getDefaultStationId());
		ResponseMessage<String> result = new ResponseMessage<String>();
		if(list.size() == 0){
			result.setRet(ResponseMessage.RET_PARAM_ERR);
			result.setMsg("暂未加入任何维系组!");
			setResponseMessageData(result);
			return SUCCESS;
		}else{
			result.setData(list.get(0).getBmwId());
		}
		setResponseMessageData(result);
		return SUCCESS;
	}
//	/**
//	 * 添加客户信息
//	 *
//	 * @return
//	 */
//	@Access(needPopedom = SaleCustomer.CUSTOMER_FOLLOW)
//	public String saveCustomer() {
//		String objectId = UUID.randomUUID().toString();// 用户ID
//		ResponseMessage<String> result = new ResponseMessage<String>();
//		SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
//
//		String msg = checkObjectName(object,objectId);
//		if(!"".equals(msg)){
//			result.setRet(ResponseMessage.RET_PARAM_ERR);
//			result.setMsg(msg);
//			setResponseMessageData(result);
//			return SUCCESS;
//		}else{
//			object.setObjectId(objectId);
//			object.setFullId(objectId);
//			object.setObjectNo(service.getAutoNoOfObject());
//			object.setStationId(getDefaultStationId());
//			object.setIsParent(false);
//			object.setStatus((short) 1);
////			object.setNamePinyin(GetChineseFirstChar.getFirstLetter(object
////					.getObjectName()));
//			if(object.getObjectNature() == 20 && ("".equals(object.getShortName()) || object.getShortName() ==null)){
//				object.setShortName(object.getObjectName());
//			}
//			object.setCustomerType((short) 30);// 新建时为有效客户
//			object.setObjectKind(1);
//			object.setObjectType(2);
//			object.setCreator(user.getUserName() + "(" + user.getUserNo() + ")");
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			object.setCreateTime(Timestamp.valueOf(df.format(new Date())));
//
//			List<UserMaintenanceWorkgroups> list = service.getUserMaintenanceWorkgroups(user.getUserId(), user.getDefaulStationId());
//
//			CustomerMaintenanceWorkgroup workgroup = new CustomerMaintenanceWorkgroup();
//			workgroup.setSelfId(UUID.randomUUID().toString());
//			workgroup.setObjectId(objectId);
//			workgroup.setStationId(getDefaultStationId());
//			workgroup.setBmwId(list.get(0).getBmwId());
//			/**
//			 * 成交机会为必填项
//			 * 若成交机会不为空 则说明用户已制定意向线索
//			 */
//			if (vehicle.getVehicleBrand().length() > 0) {
//				vehicle.setSelfId(UUID.randomUUID().toString());
//				vehicle.setCustomerId(objectId);
//			}
//			String intentClueNo;
//			if(presellVisitorsForIntent.getVisitorLevel().length() > 0){
//			    intentClueNo = UUID.randomUUID().toString();
//				presellVisitorsForIntent.setVisitorNo(intentClueNo);
//				if(presellVisitorsForIntent.getVisitTime() == null){
//					presellVisitorsForIntent.setVisitTime(Timestamp.valueOf(df.format(new Date())));
//				}
//				presellVisitorsForIntent.setStationId(getDefaultStationId());
//				presellVisitorsForIntent.setVisitorCount(String.valueOf(1));
//				presellVisitorsForIntent.setVisitorId(objectId);
//				presellVisitorsForIntent.setSeller(user.getUserName());
//				presellVisitorsForIntent.setCreator(user.getUserName() + "("
//						+ user.getUserNo() + ")");
//				presellVisitorsForIntent.setCreateTime(Timestamp.valueOf(df
//						.format(new Date())));
//				presellVisitorsForIntent.setSellerId(user.getUserId());
//				//presellVisitorsForIntent.setClueType((short) 10);
////				presellVisitorsForIntent.setLastIsCompetitive(object.getIsCompetitive());
//				if(presellVisitorsForIntent.getVisitResult() == null){
//					//此时新建了意向线索，但没有跟踪结果,需要将客户类型转为意向客户
//					//object.setCustomerType((short) 20); //不更改客户类型
//					intentClueBack.setBackId(UUID.randomUUID().toString());
//					intentClueBack.setVisitorNo(intentClueNo);
//					if(intentClueBack.getPlanBackTime() == null){
//						intentClueBack.setPlanBackTime(Timestamp.valueOf(df.format(new Date())));
//					}
//					//需要将计划回访时间回填到线索中
//					presellVisitorsForIntent.setPlanBackTime(intentClueBack.getPlanBackTime());
//				}/*else {
//					//新建线索时，跟踪结果只能为成交和空 在此将客户信息改为有效客户
//					object.setCustomerType((short) 30);
//				}*/
//				//若跟踪结果为失败，失控，无效，则将信息记录到战败客户表
//				if( presellVisitorsForIntent.getVisitResult() != null && presellVisitorsForIntent.getVisitResult() != 10){
//					//object.setCustomerType((short)50);
//					PresellVisitorsFail fail = new PresellVisitorsFail();
//					fail.setVisitorNo(UUID.randomUUID().toString());
//					fail.setVisitorId(objectId);
//					fail.setVisitorName(object.getObjectName());
//					fail.setVisitorMobile(object.getMobile());
//					fail.setEndDeal(false);
//					fail.setCreateTime(Timestamp.valueOf(df.format(new Date())));
//					fail.setCreator(user.getUserName() + "("+ user.getUserNo() + ")");
//					service.savePresellVisitorsFail(fail);
//				}
//			}
//			service.saveCustomer(object, /*presellVisitors,*/presellVisitorsForIntent, vehicle,/*validClueBack,*/intentClueBack,workgroup);
//			result.setMsg("已登记客户信息！");
//		}
//		setResponseMessageData(result);
//		return SUCCESS;
//	}
	/**
	 * 根据站点获取车辆型号
	 * @param stationId 
	 * @param vehicleName 车辆名称
	 * @param vehicleVno  车辆型号
	 * @return
	 */
	@Access(pass = true)
	public String getVehicleType(){
		SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
		String stationId;
		stationId = getDefaultStationId();
		List<VehicleType> result = service.getVehicleType(stationId,vehicleVno, vehicleName);
		setResponseData(result);
		return SUCCESS;
	}
	/**
	 * 获取新增意向线索的初始化数据
	 * @return
	 */
	@Access(pass = true)
	public String getAddIntentClueInitData(){
		AddIntentClueInitData init = service.getAddIntentClueInitData(getDefaultStationId());
		setResponseData(init);
		return SUCCESS;
	}
	/**
	 * 检查是否有未回访的有效线索和同一部门正在跟进的线索
	 * @return
	 */
	@Access(pass = true)
	public String CheckClueForNoBack(){
		SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
		List<PresellVisitorsVO> list = service.getUnfinishedClueForDepartment(user.getUserId(), visitorId);
		//List<String> validCluelist = service.getValidClueForNoBack(visitorId);
		List<String> noBack = service.getClueForNoBack(visitorId);
		Boolean flag = service.getAllowMultiClue();
		ResponseMessage<String> result = new ResponseMessage<String>();
		if(flag){
			if (list.size() > 0) {
				result.setRet(ResponseMessage.RET_PARAM_ERR);
				result.setMsg("同一部门只能建立一条正在跟进的线索！");
			}
		}else{
			if(noBack.size() > 0){
				result.setRet(ResponseMessage.RET_PARAM_ERR);
				result.setMsg("不允许多线索跟进！");
			}
		}
		
		setResponseMessageData(result);
		return SUCCESS;
	}
	/**
	 *  验证某客户是否有未回访的计划  
	 * @param visitorId
	 * @return
	 */
	@Access(pass = true)
	public String getClueForNoBack(){
		List<String> list = service.getClueForNoBack(visitorId);
		ResponseMessage<String> result = new ResponseMessage<String>();
		if (list.size() > 0) {
			result.setMsg("该客户正在被跟进,如需跟进请新建线索！");
			result.setRet(ResponseMessage.RET_PARAM_ERR);
		}
		setResponseMessageData(result);
		return SUCCESS;
	}
	/**
	 * 针对已存在的客户新增意向线索
	 * @return
	 */
	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW,SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP,SaleCustomer.CUSTOMER_SERVICE_BACK})
	public String saveClue(){
		
		SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
		List<PresellVisitorsVO> list = service.getUnfinishedClueForDepartment(user.getUserId(), object.getObjectId());
		//List<String> validCluelist = service.getValidClueForNoBack(object.getObjectId());
		//上次销售线索
		List<LastPresellVisitors> lastPresellVisitors = service.getLastVisitResult(object.getObjectId());
		//检查用户是否有有效线索，若没有则新建一条
		//List<PresellVisitors> hasValidClue = service.checkValidClue(object.getObjectId());
		ResponseMessage<String> result = new ResponseMessage<String>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 //判断当前用户所在的部门是否还有未跟进完成的线索，有则不允许再次建立线索
		if (list.size() > 0) {
			result.setRet(ResponseMessage.RET_PARAM_ERR);
			result.setMsg("同一部门只能建立一条正在跟进的线索！");
		}
		else {
			String intentClueNo;
		    intentClueNo = UUID.randomUUID().toString();
			presellVisitorsForIntent.setVisitorNo(intentClueNo);
			presellVisitorsForIntent.setStationId(getDefaultStationId());
			if(presellVisitorsForIntent.getVisitTime() == null){
				presellVisitorsForIntent.setVisitTime(Timestamp.valueOf(df.format(new Date())));
			}
			presellVisitorsForIntent.setVisitorCount(String.valueOf(1));
			presellVisitorsForIntent.setVisitorId(object.getObjectId());
			presellVisitorsForIntent.setSeller(user.getUserName());
			presellVisitorsForIntent.setCreator(user.getUserName() + "("
					+ user.getUserNo() + ")");
			presellVisitorsForIntent.setCreateTime(Timestamp.valueOf(df
					.format(new Date())));
			presellVisitorsForIntent.setSellerId(user.getUserId());
			//presellVisitorsForIntent.setClueType((short) 10);
//			presellVisitorsForIntent.setLastIsCompetitive(object.getIsCompetitive());
			//判断上次销售线索是否存在，若存在且为意向线索 则将上次回访结果 填入本次线索中
			if(lastPresellVisitors.size() > 0){
				presellVisitorsForIntent.setLastVisitResult(lastPresellVisitors.get(0).getVisitResult());
				/*if(lastPresellVisitors.get(0).getClueType() == 10){
					
				}*/
			}
			if(presellVisitorsForIntent.getVisitResult() == null){
				//此时新建了意向线索，但没有跟踪结果,需要将客户类型转为意向客户,并新增回访计划
				//object.setCustomerType((short) 20);
				intentClueBack.setBackId(UUID.randomUUID().toString());
				intentClueBack.setVisitorNo(intentClueNo);
				if(intentClueBack.getPlanBackTime() == null){
					intentClueBack.setPlanBackTime(Timestamp.valueOf(df.format(new Date())));
				}
				//需要将计划回访时间回填到线索中
				presellVisitorsForIntent.setPlanBackTime(intentClueBack.getPlanBackTime());
			}/*else if (presellVisitorsForIntent.getVisitResult() == 10 || presellVisitorsForIntent.getVisitResult() == 20 ||presellVisitorsForIntent.getVisitResult() == 40){
				//若成交结果为成交，失败，失控 则将客户类型转为有效
				object.setCustomerType((short) 30);
			}else if(presellVisitorsForIntent.getVisitResult() == 30){
				//若成交结果为无效 则将客户类型转为无效
				object.setCustomerType((short) 50);
			}*/
			//若跟踪结果为失败，失控，无效，则将信息记录到战败客户表
			if( presellVisitorsForIntent.getVisitResult() != null && (presellVisitorsForIntent.getVisitResult() == 20 || presellVisitorsForIntent.getVisitResult() == 40)){
				PresellVisitorsFail fail = new PresellVisitorsFail();
				fail.setVisitorNo(presellVisitorsForIntent.getVisitorNo());
				fail.setVisitorId(object.getObjectId());
				fail.setVisitorName(object.getObjectName());
				fail.setVisitorMobile(object.getMobile());
				fail.setEndDeal(false);
				fail.setCreateTime(Timestamp.valueOf(df.format(new Date())));
				fail.setCreator(user.getUserName() + "("+ user.getUserNo() + ")");
				service.savePresellVisitorsFail(fail);
			}
			service.saveClue(presellVisitorsForIntent, intentClueBack, object);
			result.setMsg("已添加线索！");
		}
		setResponseMessageData(result);
		return SUCCESS;
	}
	/**
	 * 对客户做回访
	 * @return
	 */
	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW,SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP,SaleCustomer.CUSTOMER_SERVICE_BACK})
	public String saveCallBackForClue(){
		ResponseMessage<String> result = new ResponseMessage<String>();
		SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
		List<String> list = service.getClueBackRealTime(intentClueBack.getBackId());
		if("".equals(list.get(0)) || list.get(0)==null){
			intentClueBack.setBacker(user.getUserFullName());
			intentClueBack.setRealBackTime(new Timestamp(new Date().getTime()));//实际回访时间
			presellVisitorsForIntent.setVisitorLevel(intentClueBack.getBackVisitorLevel());
			if(intentClueBack.getPlanBackTime() == null){
				intentClueBack.setPlanBackTime(new Timestamp(new Date().getTime()));
			}
			if(presellVisitorsForIntent.getVisitResult() == null /*&& presellVisitorsForIntent.getClueType() == 10*/){
				//此时为意向线索且没有跟踪结果,新增回访计划
				//object.setCustomerType((short) 20);
				validClueBack.setBackId(UUID.randomUUID().toString());
				validClueBack.setVisitorNo(presellVisitorsForIntent.getVisitorNo());
				if(validClueBack.getPlanBackTime() == null){
					throw new ServiceException("需要制定下次计划回访时间");
				}
				//需要将计划回访时间回填到线索中
				presellVisitorsForIntent.setPlanBackTime(validClueBack.getPlanBackTime());
			}/*if(presellVisitorsForIntent.getVisitResult() != null){
				if (presellVisitorsForIntent.getVisitResult() == 10 || presellVisitorsForIntent.getVisitResult() == 20 ||presellVisitorsForIntent.getVisitResult() == 40){
					//若成交结果为成交，失败，失控 则将客户类型转为有效
					object.setCustomerType((short) 30);
				}else if(presellVisitorsForIntent.getVisitResult() == 30){
					//若成交结果为无效 则将客户类型转为无效
					object.setCustomerType((short) 50);
				}
			}*/
			//若跟踪结果为失败，失控，无效，则将信息记录到战败客户表
			PresellVisitorsFail fail = null;
			if( presellVisitorsForIntent.getVisitResult() != null && (presellVisitorsForIntent.getVisitResult() == 20 || presellVisitorsForIntent.getVisitResult() == 40)){
				fail = new PresellVisitorsFail();
				fail.setVisitorNo(presellVisitorsForIntent.getVisitorNo());
				fail.setVisitorId(object.getObjectId());
				fail.setVisitorName(object.getObjectName());
				fail.setVisitorMobile(object.getMobile());
				fail.setEndDeal(false);
				fail.setCreateTime(new Timestamp(new Date().getTime()));
				fail.setCreator(user.getUserName() + "("+ user.getUserNo() + ")");
				//service.savePresellVisitorsFail(fail);
			}
			service.saveCallBackForClue(presellVisitorsForIntent, intentClueBack, validClueBack,object,fail);
			result.setMsg("回访成功！");
		}else{
			result.setRet(ResponseMessage.RET_PARAM_ERR);
			result.setMsg("该计划已被回访！");
		}
		setResponseMessageData(result);
		return SUCCESS;
	}
	/**
	 * 检查回访
	 * @return
	 */
	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP})
	public String checkClueBack(){
		ResponseMessage<String> result = new ResponseMessage<String>();
		SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<String> list = service.getCheckBackContext(intentClueBack.getBackId());
		if("".equals(list.get(0)) || list.get(0) == null){
			intentClueBack.setChecker(user.getUserName() + "("+ user.getUserNo() + ")");
			intentClueBack.setCheckTime(Timestamp.valueOf(df.format(new Date())));
			service.updatePresellVisitorsBack(intentClueBack);
			result.setMsg("检查成功！");
		}else{
			result.setRet(ResponseMessage.RET_PARAM_ERR);
			result.setMsg("该计划已被检查！");
		}
		setResponseMessageData(result);
		return SUCCESS;
	}
	/**
	 * 制定回访计划
	 * @return
	 */
	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW,SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP,SaleCustomer.CUSTOMER_SERVICE_BACK})
	public String savePlanCallBack(){
		ResponseMessage<String> result = new ResponseMessage<String>();
		List<PresellVisitorsForCheck> p =service.getClueForUpdate(presellVisitorsForIntent.getVisitorNo());
		if(p.get(0).getVisitResult() != null){
			result.setRet(ResponseMessage.RET_PARAM_ERR);
			result.setMsg("该线索已跟踪完！");
		}else{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			intentClueBack.setBackId(UUID.randomUUID().toString());
			intentClueBack.setVisitorNo(presellVisitorsForIntent.getVisitorNo());
			if(intentClueBack.getPlanBackTime() == null){
				intentClueBack.setPlanBackTime(Timestamp.valueOf(df.format(new Date())));
			}
			//将回访时间回填到线索中
			presellVisitorsForIntent.setPlanBackTime(intentClueBack.getPlanBackTime());
			service.savePlanCallBack(presellVisitorsForIntent,intentClueBack);
			result.setMsg("已制定回访计划，请注意按时回访！");
		}
		setResponseMessageData(result);
		return SUCCESS;
	}
	/**
	 * 检查线索是否可以修改--- 修改线索时有效线索若无回访计划不能修改
	 * @return
	 */
	@Access(pass = true)
	public String checkClueForUpdate(){
		SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
		//List<String> validCluelist = service.getValidClueForNoBack(object.getObjectId());
		ResponseMessage<String> result = new ResponseMessage<String>();
		if(presellVisitorsForIntent.getVisitResult() != null){
			result.setRet(ResponseMessage.RET_PARAM_ERR);
			result.setMsg("已回访完，不可编辑！");
		}else if(!presellVisitorsForIntent.getSellerId().equals(user.getUserId())/* && presellVisitorsForIntent.getClueType() == 10*/){
			result.setRet(ResponseMessage.RET_PARAM_ERR);
			result.setMsg("只有该线索的销售员才可编辑！");
		}
		setResponseMessageData(result);
		return SUCCESS;
	}
	/**
	 * 修改线索信息
	 * @return
	 */
	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW,SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP,SaleCustomer.CUSTOMER_SERVICE_BACK})
	public String updatePresellVisitors(){
		SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ResponseMessage<String> result = new ResponseMessage<String>();
		List<PresellVisitorsForCheck> p =service.getClueForUpdate(presellVisitorsForIntent.getVisitorNo());
		//List<String> validCluelist = service.getValidClueForNoBack(object.getObjectId());
		if(p.get(0).getVisitResult() != null){
			result.setRet(ResponseMessage.RET_PARAM_ERR);
			result.setMsg("已回访完，不可编辑！");
		}else if(!p.get(0).getSellerId().equals(user.getUserId()) /*&& p.get(0).getClueType() == "10"*/){
			result.setRet(ResponseMessage.RET_PARAM_ERR);
			result.setMsg("只有该线索的销售员才可编辑！");
		}else{
			presellVisitorsForIntent.setModifier(user.getUserName() + "("+ user.getUserNo() + ")");
			presellVisitorsForIntent.setModifyTime(Timestamp.valueOf(df.format(new Date())));
			service.updatePresellVisitors(presellVisitorsForIntent);
			result.setMsg("修改成功！");
		}
		
		setResponseMessageData(result);
		return SUCCESS;
	}
	/**
	 * 检查客户是否有过消费 
	 * @param objectId
	 * @return
	 */
	@Access(pass = true)
	public String checkCustomerForUpdate(){
		List<String> detail  = service.getFinanceDocumentEntries(visitorId);
		setResponseData(detail);
		return SUCCESS;
	}
	/**
	 * 获得用户的保有车辆
	 * @param objectId
	 * @return
	 */
	@Access(pass = true)
	public String getCustomerRetainVehicle(){
		List<CustomerRetainVehicle> list  = service.getCustomerRetainVehicle(visitorId);
		setResponseData(list);
		return SUCCESS;
	}
	/**
	 * 修改客户信息
	 * @return
	 */
//	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW,SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP,SaleCustomer.CUSTOMER_SERVICE_BACK})
//	public String updateCustomerAndCustomerRetainVehicle(){
//		ResponseMessage<String> result = new ResponseMessage<String>();
//		SysUsers user = (SysUsers) getAttributeFromSession(Attribute.SESSION_USER);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String msg = checkObjectName(object,object.getObjectId());
//
//		List<CustomerVO> objectNames = service.checkObjectNameIsUpdate(object.getObjectId());
//
//		if(!"".equals(msg) && msg != null){
//			result.setRet(ResponseMessage.RET_PARAM_ERR);
//			result.setMsg(msg);
//			setResponseMessageData(result);
//			return SUCCESS;
//		}else if(!object.getCreator().equals(user.getUserName() + "("+ user.getUserNo() + ")") && CheckPopedom.checkPopedom(user,"10201210") == false){
//			result.setRet(ResponseMessage.RET_PARAM_ERR);
//			result.setMsg("无法修改客户名称");
//			setResponseMessageData(result);
//			return SUCCESS;
//		}else if(object.getIsConsumption() == true && (!object.getObjectName().equals(objectNames.get(0).getObjectName()) ||
//					!object.getShortName().equals(objectNames.get(0).getShortName()))){
//			result.setRet(ResponseMessage.RET_PARAM_ERR);
//			result.setMsg("客户有消费历史，无法修改客户名称");
//			setResponseMessageData(result);
//			return SUCCESS;
//		}else{
//			//用户有保有车辆，修改保有车辆信息
//			CustomerRetainVehicle v = service.getCustomerRetainVehicleById(object.getRetainVehicleId());
//
//			if("".equals(object.getObjectNo()) || object.getObjectNo() == null){
//				object.setObjectNo(service.getAutoNoOfObject());
//			}
//			object.setModifier(user.getUserName() + "("+ user.getUserNo() + ")");
//			object.setModifyTime(Timestamp.valueOf(df.format(new Date())));
//			service.updateObject(object,v);
//			result.setMsg("修改成功！");
//		}
//		setResponseMessageData(result);
//		return SUCCESS;
//	}
	
	//=======================================================================
	private InterestedCustomers intentionCustomer;	//意向客户
	private CustomerRetainVehicle retainVehicle;
	private PresellVisitorsVO intentionClue;//意向线索
	private PresellVisitorsBack backVisitInfo;//回访信息
	private PresellVisitorsBack nextBackVisitPlan;//下次回访计划
	
	public void setIntentionCustomer(String intentionCustomerJson) {
		try {
			this.intentionCustomer = gson.fromJson(intentionCustomerJson, InterestedCustomers.class);
		} catch (JsonSyntaxException e) {
			logger.error("参数错误" + intentionCustomerJson, e);
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
	 * 新建意向线索
	 */
	@Access(needOneOfPopedoms = {SaleCustomer.CUSTOMER_FOLLOW,SaleCustomer.CUSTOMER_CHECK,SaleCustomer.CUSTOMER_CHECKDEP,SaleCustomer.CUSTOMER_SERVICE_BACK})
	public String createIntentionClue(){
		if(intentionClue == null){
			return showErrorMsg("参数错误");
		}else if(nextBackVisitPlan == null || nextBackVisitPlan.getPlanBackTime() == null){
			return showErrorMsg("需要制定下次回访计划");
		}
		service.createIntentionClue(intentionClue, nextBackVisitPlan);
		setResponseData("新建意向线索成功!");
		return SUCCESS;
	}

	/***
	 * 新建意向客户
	 * @return
	 */
	@Access(needPopedom = SaleCustomer.CUSTOMER_FOLLOW)
	public String createIntentionCustomer(){
		if(intentionCustomer == null || intentionClue == null){
			return showErrorMsg("参数错误:客户信息不能为空");
			
		}else if(nextBackVisitPlan == null || nextBackVisitPlan.getPlanBackTime() == null){
			return showErrorMsg("需要制定下次回访计划");
		}
		
		service.createIntentionCustomer(intentionCustomer, retainVehicle, intentionClue, nextBackVisitPlan);
		setResponseData("新建客户成功!");
		return SUCCESS;
	}

}
