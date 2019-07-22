package cn.sf_soft.oa.notification.service.impl;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.sf_soft.common.Config;
import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.oa.message.model.OaMessage;
import cn.sf_soft.oa.message.model.OaMessageNotification;
import cn.sf_soft.oa.notification.model.OaNotificationA4sApproval;
import cn.sf_soft.oa.notification.model.OaNotificationSubscribe;
import cn.sf_soft.oa.notification.model.VwApproveDocumentsNotification;
import cn.sf_soft.oa.notification.util.IOSPushUtil;
import cn.sf_soft.office.approval.dao.ApproveDocumentDao;
import cn.sf_soft.office.approval.model.ApproveDocumentsPoints;
import cn.sf_soft.user.dao.UserDao;
import cn.sf_soft.user.model.SysUsers;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import org.apache.commons.lang3.StringUtils;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

public class OaNotificationA4sApprovalService extends ApplicationObjectSupport {
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OaNotificationA4sApprovalService.class);

    @Autowired
    private Config config;

    @Autowired
    private ApproveDocumentDao approveDocumentDao;

    @Autowired
    @Qualifier("baseDao")
    protected BaseDao dao;

    @Autowired
    private UserDao userDao;

    private static final Short NOTIFICATION_TYPE_NO = 10000;
    private static final Short MOBILE_TYPE_NO = 10;

    private static final int IOS_PRODUCT_ID = 100;
    private static final int ANDROID_PRODUCT_ID = 101;
    private static final int OTHER_PRODUCT_ID = 109;

    //催促通知的间隔时间
    private static final int URGE_HOUR = 4;


    //p12文件密码
    private String password = "antelop2016";

    //生产环境
    private boolean production = true;

    //p12文件名
    private String p12Name = "dvplus.p12";

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isProduction() {
        return production;
    }


    public void setProduction(boolean production) {
        this.production = production;
    }


    public String getP12Name() {
        return p12Name;
    }


    public void setP12Name(String p12Name) {
        this.p12Name = p12Name;
    }


    /**
     * 新增待审通知
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, List<OaNotificationA4sApproval>> addApprovalNoficication() {
        // 将OaNotificationA4sApproval中已审批的节点改为false
        List<OaNotificationA4sApproval> notificationList = (List<OaNotificationA4sApproval>) dao
                .findByHql(
                        "from OaNotificationA4sApproval a where not exists (select 1 from VwApproveDocumentsNotification b  where b.oadpId = a.approvalPointId) and a.effective = ?",
                        true);
        for (OaNotificationA4sApproval notification : notificationList) {
            notification.setEffective(false);
            dao.update(notification);
        }
        dao.flush();
        // 将未加入notification的记录加入
        List<VwApproveDocumentsNotification> documentList = approveDocumentDao.getAllApprovingMatters();
        Map<String, List<OaNotificationA4sApproval>> resultMap = new HashMap<String, List<OaNotificationA4sApproval>>();
        for (int i = documentList.size() - 1; i >= 0; i--) {
            VwApproveDocumentsNotification documemnt = documentList.get(i);
            String approverNo = documemnt.getApproverNo();
            if (StringUtils.isNotEmpty(approverNo)) {
                String[] arrNos = approverNo.split(",");
                for (String userNo : arrNos) {
                    OaNotificationA4sApproval newNotification = org.springframework.beans.BeanUtils.instantiate(OaNotificationA4sApproval.class);
                    newNotification.setApprovalPointId(documemnt.getOadpId());
                    newNotification.setApprovalPoint(dao.get(ApproveDocumentsPoints.class, documemnt.getOadpId()));
                    newNotification.setEffective(true);
                    newNotification.setRefusedNotification(false);
                    newNotification.setUserNo(userNo);
                    newNotification.setLastNotificationTime(new Timestamp(System.currentTimeMillis()));
                    newNotification.setNotificationTimes((short) 1);
                    dao.save(newNotification);

                    List<OaNotificationA4sApproval> list = resultMap.get(userNo);
                    if (list == null) {
                        list = new ArrayList<OaNotificationA4sApproval>();
                    }
                    list.add(newNotification);
                    resultMap.put(userNo, list);
                }
            }
        }

        dao.flush();
        return resultMap;
    }


    /**
     * 发送IOS通知
     */
    @SuppressWarnings("unchecked")
    public void pushMobileNofication() {
//		PushNotificationManager pushManager = null;
        try {
            Map<String, List<OaNotificationA4sApproval>> resultMap = addApprovalNoficication();

            List<OaNotificationSubscribe> allSubscribeList = (List<OaNotificationSubscribe>) dao.findByHql("from OaNotificationSubscribe where  subscribed = ? AND deviceTypeNo = ? AND productId IN (?, ?)", true, MOBILE_TYPE_NO, IOS_PRODUCT_ID, ANDROID_PRODUCT_ID);
            Iterator<String> it = resultMap.keySet().iterator();
            while (it.hasNext()) {
                String userNo = it.next();
                // 查询该用户已订阅设备
                List<OaNotificationSubscribe> subscribeList = getSubscribeListByUserNo(allSubscribeList, userNo);
                List<Long> approvalCountList = (List<Long>) dao.findByHql("select count(*) from OaNotificationA4sApproval  where userNo = ? and effective = true and refusedNotification = false", userNo);
                // jpush(userNo,String.format("新增%d条待审事宜，共%d条", resultMap.get(userNo).size(), approvalCountList.get(0)));
                for (OaNotificationSubscribe subscribe : subscribeList) {
//					if (StringUtils.isEmpty(subscribe.getToken())|| subscribe.getToken().length() != 64) {
//						logger.warn(String.format("OaNotificationSubscribe中用户%s的Token %s为空或长度不为64位",subscribe.getUserNo(), subscribe.getToken()));
//						continue;
//					}
                    List<OaNotificationA4sApproval> list = resultMap.get(userNo);
                    jpushBySubscribe(subscribe, list, approvalCountList.get(0), true);
                    // push(pushManager,subscribe,list,approvalCountList.get(0),false);

                }
            }
        } catch (Exception ex) {
            logger.error("发送推送出错", ex);
        }
    }


    private List<OaNotificationSubscribe> getSubscribeListByUserNo(
            List<OaNotificationSubscribe> allSubscribeList, String userNo) {
        List<OaNotificationSubscribe> subscribeList = new ArrayList<OaNotificationSubscribe>();
        if (StringUtils.isEmpty(userNo))
            return subscribeList;
        for (OaNotificationSubscribe subscribe : allSubscribeList) {
            if (userNo.equals(subscribe.getUserNo()))
                subscribeList.add(subscribe);
        }

        return subscribeList;
    }


    /**
     * 发送IOS通知
     *
     * @param pushManager
     * @param subscribe
     * @param list
     * @param approvalCount
     * @param urge          true:催促通知
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private boolean push(PushNotificationManager pushManager, OaNotificationSubscribe subscribe, List<OaNotificationA4sApproval> list, Long approvalCount, boolean urge) throws Exception {
        List<Long> unReadMsg = (List<Long>) dao.findByHql("select count(*) from OaMessageNotification  where deviceTypeNo = ? AND msg.userNo = ? and  msg.known = false and msg.msgTypeNo = ?", MOBILE_TYPE_NO, subscribe.getUserNo(), NOTIFICATION_TYPE_NO);
        String message = "";
        if (urge) {
            message = String.format("您有%d条待审事宜未审批", approvalCount);
        } else {
            message = String.format("新增%d条待审事宜，共%d条", list.size(), approvalCount);
        }
        // jpush(subscribe.getUserNo(), message);
        OaMessage oaMsg = org.springframework.beans.BeanUtils.instantiate(OaMessage.class);
        oaMsg.setCreateTime(new Timestamp(System.currentTimeMillis()));
        oaMsg.setUserNo(subscribe.getUserNo());
        oaMsg.setMessage(message);
        oaMsg.setMsgTypeNo(NOTIFICATION_TYPE_NO);
        oaMsg.setKnown(false);

        OaMessageNotification msgNotification = org.springframework.beans.BeanUtils.instantiate(OaMessageNotification.class);
        msgNotification.setMsg(oaMsg);
        msgNotification.setDeviceTypeNo(MOBILE_TYPE_NO);
        msgNotification.setNotificationTime(new Timestamp(System.currentTimeMillis()));

        oaMsg.getOaMessageNotifications().add(msgNotification);
        dao.save(oaMsg);

        JsonObject jo = new JsonObject();
        JsonParser jParser = new JsonParser();
        jo.add("userNo", jParser.parse(subscribe.getUserNo()));
        jo.add("msgTypeNo", jParser.parse("10000"));
        jo.add("notificationId", jParser.parse(msgNotification.getObjId() + ""));
        if (list != null && list.size() == 1) {
            jo.add("documentId", jParser.parse(list.get(0).getApprovalPoint().getDocumentId()));
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("data", jo.toString());
        PushNotificationPayload payload = IOSPushUtil.customPayload(message, (int) (unReadMsg.get(0) + 1), null, map);
        Device device = new BasicDevice();
        device.setToken(subscribe.getToken());
        PushedNotification notification = pushManager.sendNotification(device, payload, false);
        return notification.isSuccessful();
    }

    private static PushPayload buildPushObject_all_all_alert(Collection<String> jpushId, String message) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(jpushId))
                //.setNotification(Notification.alert(message))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(message)
                                .setSound("happy")
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(message)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    private static PushPayload buildPushObject_all_all_alert(String jpushId, String message, int badge) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(jpushId))
                //.setNotification(Notification.alert(message))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setBadge(badge)
                                .setAlert(message)
                                .setSound("happy")
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(message)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    private boolean jpushBySubscribe(OaNotificationSubscribe subscribe, List<OaNotificationA4sApproval> list, Long approvalCount, boolean urge) throws Exception {
        List<Long> unReadMsg = (List<Long>) dao.findByHql("select count(*) from OaMessageNotification  where deviceTypeNo = ? AND msg.userNo = ? and  msg.known = false and msg.msgTypeNo = ?", MOBILE_TYPE_NO, subscribe.getUserNo(), NOTIFICATION_TYPE_NO);
        String message = "";
        if (urge) {
            message = String.format("您有%d条待审事宜未审批", approvalCount);
        } else {
            message = String.format("新增%d条待审事宜，共%d条", list.size(), approvalCount);
        }
        // jpush(subscribe.getUserNo(), message);
        OaMessage oaMsg = org.springframework.beans.BeanUtils.instantiate(OaMessage.class);
        oaMsg.setCreateTime(new Timestamp(System.currentTimeMillis()));
        oaMsg.setUserNo(subscribe.getUserNo());
        oaMsg.setMessage(message);
        oaMsg.setMsgTypeNo(NOTIFICATION_TYPE_NO);
        oaMsg.setKnown(false);

        OaMessageNotification msgNotification = org.springframework.beans.BeanUtils.instantiate(OaMessageNotification.class);
        msgNotification.setMsg(oaMsg);
        msgNotification.setDeviceTypeNo(MOBILE_TYPE_NO);
        msgNotification.setNotificationTime(new Timestamp(System.currentTimeMillis()));

        oaMsg.getOaMessageNotifications().add(msgNotification);
        dao.save(oaMsg);

        logger.debug("开始调用JPush");
        JPushClient jpushClient = new JPushClient(
                config.getApplicationConfig("jpush.masterSecret"),
                config.getApplicationConfig("jpush.appKey"),
                null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.

        String jpushId = subscribe.getToken();

        if (jpushId == null || jpushId.length() == 0) {
            logger.info("JPush调用失败：JPushId不存在");
            return false;
        }

        PushPayload payload = buildPushObject_all_all_alert(jpushId, message, approvalCount == null ? 0 : Integer.valueOf(approvalCount.toString()));

        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("JPush: Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            logger.error("JPush: Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            logger.error("JPush: Should review the error, and fix the request", e);
            logger.info("JPush: HTTP Status: " + e.getStatus());
            logger.info("JPush: Error Code: " + e.getErrorCode());
            logger.info("JPush: Error Message: " + e.getErrorMessage());
        }
        return true;
    }

    private boolean jpush(String userNo, String message, int badge) {
        logger.debug("开始调用JPush");
        JPushClient jpushClient = new JPushClient(
                config.getApplicationConfig("jpush.masterSecret"),
                config.getApplicationConfig("jpush.appKey"),
                null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        SysUsers user = userDao.getUserByUserNo(userNo);
        if (user == null) {
            logger.error("JPush调用失败：userNo不存在");
            return false;
        }

        String jpushId = user.getJpushId();

        if (jpushId == null || jpushId.length() == 0) {
            logger.info("JPush调用失败：JPushId不存在");
            return false;
        }

        PushPayload payload = buildPushObject_all_all_alert(jpushId, message, badge);

        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("JPush: Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            logger.error("JPush: Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            logger.error("JPush: Should review the error, and fix the request", e);
            logger.info("JPush: HTTP Status: " + e.getStatus());
            logger.info("JPush: Error Code: " + e.getErrorCode());
            logger.info("JPush: Error Message: " + e.getErrorMessage());
        }
        return true;
    }


    private File getKeyStore() throws IOException {
        String p12Prefix = "production";
        if (!production) {
            p12Prefix = "development";
        }
        //classpath:development.dvplus.p12
        Resource resource = this.getApplicationContext().getResource("classpath:" + p12Prefix + "." + p12Name);
        return resource.getFile();
    }


    /**
     * 发送催促通知，超过4h
     *
     * @throws IOException
     * @throws KeystoreException
     * @throws CommunicationException
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
    public void pushUrgeNotification() {
        // 将OaNotificationA4sApproval中已审批的节点改为false
        List<OaNotificationA4sApproval> notificationList = (List<OaNotificationA4sApproval>) dao
                .findByHql(
                        "from OaNotificationA4sApproval a where not exists (select 1 from VwApproveDocumentsNotification b  where b.oadpId = a.approvalPointId) and a.effective = ?",
                        true);
        for (OaNotificationA4sApproval notification : notificationList) {
            notification.setEffective(false);
            dao.update(notification);
        }

        List<OaNotificationSubscribe> allSubscribeList = (List<OaNotificationSubscribe>) dao.findByHql("from OaNotificationSubscribe where  subscribed = ? AND deviceTypeNo = ? AND productId IN (?, ?) ", true, MOBILE_TYPE_NO, IOS_PRODUCT_ID, ANDROID_PRODUCT_ID);

        // 查找超过4h的记录
        try {
            List<Object> resultList = (List<Object>) dao.findByHql("select userNo,count(*) from OaNotificationA4sApproval where  effective = true  AND DATEDIFF(HOUR, lastNotificationTime,getdate())>=? AND userNo in (select userNo from OaNotificationSubscribe where subscribed = true) group by userNo", URGE_HOUR);

            for (Object result : resultList) {
                Object[] row = (Object[]) result;
                String userNo = row[0] + "";
                Long approvalCount = row[1] == null ? 0 : (Long) row[1];
                // jpush(userNo, String.format("您有%d条待审事宜未审批", approvalCount));
                List<OaNotificationA4sApproval> userApprovalList = (List<OaNotificationA4sApproval>) dao.findByHql("from OaNotificationA4sApproval  where  userNo = ?  AND effective = true", userNo);
                List<OaNotificationA4sApproval> approvalList = getUrgeNotificationList(userApprovalList);//(List<OaNotificationA4sApproval>) dao.findByHql("from OaNotificationA4sApproval  where  userNo = ? and  DATEDIFF(HOUR, lastNotificationTime,getdate())>=? AND effective = true", userNo,4);
                // 查询该用户已订阅设备
                List<OaNotificationSubscribe> subscribeList = getSubscribeListByUserNo(allSubscribeList, userNo);
                int n = 0;
                for (OaNotificationSubscribe subscribe : subscribeList) {
//					if (StringUtils.isEmpty(subscribe.getToken())|| subscribe.getToken().length() != 64) {
//						logger.warn(String.format("OaNotificationSubscribe中用户%s的Token %s为空或长度不为64位",subscribe.getUserNo(), subscribe.getToken()));
//						continue;
//					}
                    jpushBySubscribe(subscribe, approvalList, approvalCount, true);
                    // push(pushManager,  subscribe, approvalList, approvalCount,true);
                    n++;
                }

                if (n > 0) {
                    //有催促通知，将此人的所有有效待审事宜通知都刷一遍
                    //更新 通知次数次数和最后通知时间
                    for (OaNotificationA4sApproval approval : userApprovalList) {
                        approval.setLastNotificationTime(new Timestamp(System.currentTimeMillis()));
                        approval.setNotificationTimes((short) (approval.getNotificationTimes() + 1));
                        dao.update(approval);
                    }
                }
            }
            dao.flush();
        } catch (Exception ex) {
            logger.error("发送推送出错", ex);
        }
    }


    /**
     * 催促通知的时间
     *
     * @param userApprovalList
     * @return
     */
    private List<OaNotificationA4sApproval> getUrgeNotificationList(List<OaNotificationA4sApproval> userApprovalList) {
        long currentTime = System.currentTimeMillis();
        List<OaNotificationA4sApproval> urgeNotificationList = new ArrayList<OaNotificationA4sApproval>();
        for (OaNotificationA4sApproval notification : userApprovalList) {
            if (notification.getLastNotificationTime() != null) {
                long t = (currentTime - notification.getLastNotificationTime().getTime()) / 1000 / 60 / 60;
                if (t >= URGE_HOUR)
                    urgeNotificationList.add(notification);
            }

        }
        return urgeNotificationList;
    }
}
