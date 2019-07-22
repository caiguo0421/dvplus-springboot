package cn.sf_soft.oa.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sf_soft.common.gson.GsonUtil;
import cn.sf_soft.message.MessageSimpleEntity;
import cn.sf_soft.message.MessageType;
import cn.sf_soft.message.service.MessageCenterService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Devices;
import javapns.notification.PushNotificationPayload;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.common.action.BaseAction;
import cn.sf_soft.common.annotation.Access;
import cn.sf_soft.common.model.ResponseMessage;
import cn.sf_soft.common.util.Constant.Attribute;
import cn.sf_soft.oa.message.service.impl.OaMessageService;
import cn.sf_soft.oa.notification.service.impl.OaNotificationSubscribeService;
import cn.sf_soft.oa.notification.util.IOSPushUtil;
import cn.sf_soft.user.model.SysUsers;

public class OaAction extends BaseAction{
	private static final long serialVersionUID = 6988360896688381256L;
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OaAction.class);
	
	@Autowired
	private OaNotificationSubscribeService  oaNotificationSubscribeService;
	
	@Autowired
	private  OaMessageService oaMessageService;

	@Autowired
	private MessageCenterService messageCenterService;
	
	//消息推送注册-订阅标志
	private boolean subscribed;
	//iOS推送服务需要的Token
	private String pushToken;
	
	//known接口需要的通知Id
	private Integer notificationId;
	//消息类型  10000：审批通知
	private Short msgTypeNo;

	private String brand;
	private String osVersion;
	private String registrationId;

	private String jsonData;

	public void setNotificationId(Integer notificationId){
		this.notificationId = notificationId;
	}
	
	public void setMsgTypeNo(Short msgTypeNo){
		this.msgTypeNo = msgTypeNo;
	}
	
	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	public void setSubscribed(Boolean subscribed){
		this.subscribed =subscribed==null?false:subscribed;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	/**
	 * 注册-pushToken
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Access(pass = true)
	public String register(){
		HttpSession  session = ServletActionContext.getRequest().getSession();
		if(session == null || session.getAttribute(Attribute.SESSION_USER)==null){
			throw new ServiceException("注册失败:未获取到用户信息");
		}
		
		SysUsers user = (SysUsers)session.getAttribute(Attribute.SESSION_USER);
		String deviceId = (String)session.getAttribute(Attribute.TOKEN);
		String terminalInfo = (String)session.getAttribute(Attribute.SESSION_TERMINAL_INFO);
//		String jPushId = (String)session.getAttribute(Attribute.JPUSH_ID);

		// Test Token
		// jPushId = "1114a89792954852de8";

		if(registrationId != null && registrationId.length() > 0) {
			ResponseMessage respMsg = oaNotificationSubscribeService.registerPushToken(deviceId, terminalInfo, registrationId, user, subscribed, brand, osVersion);
			ServletActionContext.getRequest().setAttribute("result", gson.toJson(respMsg));
			return SUCCESS;
		}else if(!subscribed){
			ResponseMessage respMsg = oaNotificationSubscribeService.registerPushToken(deviceId, terminalInfo, null, user, false, brand, osVersion);
			ServletActionContext.getRequest().setAttribute("result", gson.toJson(respMsg));
			return SUCCESS;
		}else {
			ServletActionContext.getRequest().setAttribute("msg", "无法注册消息推送，请检查推送权限。");
			return ERROR;
		}
	}
	
	/**
	 * 通过known接口通知服务端（该用户同类型的消息都设置成已阅读）
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Access(pass = true)
	public String known(){
		ResponseMessage respMsg = oaMessageService.known(notificationId,msgTypeNo);
		ServletActionContext.getRequest().setAttribute("result", gson.toJson(respMsg));
		return SUCCESS;
	}
	
	
	/**
	 * 推送测试
	 * @return
	 */
	private String msg;
	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	@SuppressWarnings("rawtypes")
	@Access(pass = true)
	public String testPush() throws CommunicationException, KeystoreException, JSONException{
		if(StringUtils.isEmpty(pushToken)||StringUtils.isEmpty(msg)){
			return showErrorMsg("token或信息为空");
		}
		javax.servlet.http.HttpServletRequest request = ServletActionContext.getRequest();
		String p12password = "antelop2016";
		int badge =1;
		String sound = "default";
		Map<String,String> map = new HashMap<String,String>();
		boolean production = false;
		String dir =  request.getSession().getServletContext().getRealPath("");
		String fileName = "dvplus.p12";
		String path = "/WEB-INF/classes/";
		String keystore = "";
		if(production){
			keystore = dir+path+"production."+fileName;
		}else{
			keystore = dir+path+"development."+fileName;
		}
		
		PushNotificationPayload payload = IOSPushUtil.customPayload(msg, null, sound,map);
		Push.payload(payload, keystore, p12password, production, Devices.asDevices(new String[]{pushToken}));
		
		ResponseMessage respMsg = new ResponseMessage("发送成功！");
		ServletActionContext.getRequest().setAttribute("result", gson.toJson(respMsg));
		return SUCCESS;
	}

	@Access(pass = true)
	public String sendMessage(){
		JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
		if (null == jsonObject || jsonObject.isJsonNull()) {
			throw new ServiceException("请求参数不能为空");
		}
		JsonElement userNos = jsonObject.get("userNos");
		if (null == userNos || userNos.isJsonNull()) {
			throw new ServiceException("请求参数必须提供消息接收者的用户编码");
		}
		if(!userNos.isJsonArray()){
			throw new ServiceException("请求参数提供消息接收者的用户编码格式必须为JSON Array");
		}

		JsonArray array = userNos.getAsJsonArray();
		List<String> users = new ArrayList<String>();
		for(int i=0; i<array.size(); i++){
			users.add(array.get(i).getAsString());
		}

		String title = GsonUtil.getAsString(jsonObject, "title");
		if (StringUtils.isEmpty(title)) {
			throw new ServiceException("请求参数必须提供消息标题");
		}

		String content = GsonUtil.getAsString(jsonObject, "content");
		if (StringUtils.isEmpty(content)) {
			throw new ServiceException("请求参数必须提供消息内容");
		}
		try{

			MessageSimpleEntity message = new MessageSimpleEntity();
			message.setMessageType(MessageType.OTHER);
			message.setTitle(title);
			message.setContent(content);
			message.setUserNos(users);
			messageCenterService.send(message, 1);
		}catch (Throwable e){

		}
		setResponseData(null);
		return SUCCESS;

	}
	

}
