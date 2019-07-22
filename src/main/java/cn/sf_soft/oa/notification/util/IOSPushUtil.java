package cn.sf_soft.oa.notification.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

public class IOSPushUtil {
	
	private static final int PAYLOAD_MAX_SIZE = 10*1024;

	/**
	 * 获得 pushManager
	 * 
	 * @param keystore
	 *            p12文件路径
	 * @param password
	 *            p12文件密码
	 * @param production
	 *            true：产品发布推送服务 false：产品测试推送服务
	 * @return
	 * @throws CommunicationException
	 * @throws KeystoreException
	 */
	public static PushNotificationManager getPushManager(Object keystore,
			String password, boolean production) throws CommunicationException,
			KeystoreException {
		PushNotificationManager pushManager = new PushNotificationManager();
		pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
				keystore, password, production));
		return pushManager;
	}

	/**
	public static boolean sentDocumentNotification(
			PushNotificationManager pushManager,
			VwApproveDocumentsNotification document,
			MobileNotificationSubscribe subscribe) throws JSONException,
			CommunicationException, KeystoreException {
		String message = String.format("%s %s需要您来审批", document.getModuleName(),document.getDocumentNo());
		
		JsonObject jo = new JsonObject();
		JsonParser jParser = new JsonParser();
		jo.add("userNo", jParser.parse(subscribe.getUserNo()));
		//jo.add("documentId", jParser.parse(document.getDocumentId()));
		jo.add("documentNo", jParser.parse(document.getDocumentNo()));
		jo.add("moduleId", jParser.parse(document.getModuleId()));
		jo.add("notificationTypeNo", jParser.parse("10000"));
		Map<String,String> map = new HashMap<String,String>();
		map.put("data", jo.toString());
		PushNotificationPayload payload = customPayload(message, null, null,map);
		
		Device device = new BasicDevice();
		device.setToken(subscribe.getToken());
		PushedNotification notification = pushManager.sendNotification(device,
				payload, false);
		return notification.isSuccessful();
	}

	
	public static boolean sentUrgeDocumentNotification(
			PushNotificationManager pushManager,
			VwApproveDocumentsNotification document,
			MobileNotification notification,
			MobileNotificationSubscribe subscribe) throws JSONException,
			CommunicationException {
		String message = String.format("%s %s未处理需要您来审批", document.getModuleName(),document.getDocumentNo());
		
		JsonObject jo = new JsonObject();
		JsonParser jParser = new JsonParser();
		jo.add("userNo", jParser.parse(subscribe.getUserNo()));
		//jo.add("documentId", jParser.parse(document.getDocumentId()));
		jo.add("documentNo", jParser.parse(document.getDocumentNo()));
		jo.add("moduleId", jParser.parse(document.getModuleId()));
		jo.add("notificationTypeNo", jParser.parse("10000"));
		Map<String,String> map = new HashMap<String,String>();
		map.put("data", jo.toString());
		PushNotificationPayload payload = customPayload(message, null, null,map);

		Device device = new BasicDevice();
		device.setToken(subscribe.getToken());
		PushedNotification pushedNotification = pushManager.sendNotification(
				device, payload, false);
		return pushedNotification.isSuccessful();
	}**/
	
	

	/**
	 * 自定义负载
	 * 
	 * @param msg
	 *            信息
	 * @param badge
	 *            图标小红圈的数值
	 * @param sound
	 *            声音
	 * @param map
	 *            自定义字典
	 * @return
	 * @throws JSONException
	 */
	public static PushNotificationPayload customPayload(String msg,
			Integer badge, String sound, Map<String, String> map)
			throws JSONException {
		PushNotificationPayload payload = PushNotificationPayload.complex();
		if (StringUtils.isNotEmpty(msg)) {
			payload.addAlert(msg);
		}
		if (badge != null) {
			payload.addBadge(badge);
		}
		payload.addSound(StringUtils.defaultIfEmpty(sound, "default"));
		if (map != null && !map.isEmpty()) {
			Object[] keys = map.keySet().toArray();
			Object[] vals = map.values().toArray();
			if (keys != null && vals != null && keys.length == vals.length) {
				for (int i = 0; i < map.size(); i++) {
					payload.addCustomDictionary(String.valueOf(keys[i]),
							String.valueOf(vals[i]));
				}
			}
		}
		return payload;
	}
}
