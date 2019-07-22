package cn.sf_soft.message.service;

import cn.sf_soft.common.dao.BaseDao;
import cn.sf_soft.message.*;
import cn.sf_soft.message.pusher.*;
import cn.sf_soft.oa.notification.model.OaNotificationA4sApproval;
import cn.sf_soft.oa.notification.model.OaNotificationSubscribe;
import cn.sf_soft.oa.notification.model.VwApproveDocumentsNotification;
import cn.sf_soft.office.approval.dao.ApproveDocumentDao;
import cn.sf_soft.office.approval.dao.hbb.ApproveDocumentDaoHibernate;
import cn.sf_soft.office.approval.model.ApproveDocumentsPoints;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/29 09:01
 * @Description:
 */
@Component
public class MessageCenterService {

    private static Logger logger = LoggerFactory.getLogger("MessagePusher center");

    //催促通知的间隔时间
    protected static final int URGE_HOUR = 4;

    protected static final Short NOTIFICATION_TYPE_NO = 10000;
    protected static final Short MOBILE_TYPE_NO = 10;

    protected static final int IOS_PRODUCT_ID = 100;
    protected static final int ANDROID_PRODUCT_ID = 101;
    protected static final int OTHER_PRODUCT_ID = 109;

    @Prop("app.packageName")
    private String packageName;

    @Prop("huawei.message.push.on")
    private boolean huawei_on;
    @Prop("huawei.message.push.appSecret")
    private String huawei_appSecret;
    @Prop("huawei.message.push.appId")
    private String huawei_appId;
    @Prop("huawei.message.push.tokenUrl")
    private String huawei_tokenUrl;
    @Prop("huawei.message.push.apiUrl")
    private String huawei_apiUrl;

    @Prop("jpush.message.push.masterSecret")
    private String jpush_appSecret;
    @Prop("jpush.message.push.appKey")
    private String jpush_appId;

    @Prop("mi.message.push.on")
    private boolean mi_on;
    @Prop("mi.message.push.appSecret")
    private String mi_appSecret;

    @Prop("vivo.message.push.on")
    private boolean vivo_on;
    @Prop("vivo.message.push.appId")
    private String vivo_appId;
    @Prop("vivo.message.push.appKey")
    private String vivo_appKey;
    @Prop("vivo.message.push.appSecret")
    private String vivo_appSecret;

    @Prop("oppo.message.push.on")
    private boolean oppo_on;
    @Prop("oppo.message.push.appKey")
    private String oppo_appKey;
    @Prop("oppo.message.push.appSecret")
    private String oppo_appSecret;

    @Autowired
    BaseDao baseDao;


    @Autowired
    protected ApproveDocumentDao approveDocumentDao;

    @Autowired
    private HuaweiMessagePusher huaweiMessagePusher;

    @Autowired
    private IOSMessagePusher iosMessagePusher;

    @Autowired
    private JPushMessagePusher jPushMessagePusher;

    @Autowired
    private MiMessagePusher miMessagePusher;

    @Autowired
    private VivoMessagePusher vivoMessagePusher;

    @Autowired
    private OppoMessagePusher oppoMessagePusher;

    private boolean initFailed = false;

    public MessageCenterService(){
        initFailed = false;
        try{
            MessagePusherConfig.initPropField(this);
        }catch (Exception e){
            logger.error("init message pusher config", e);
            initFailed = true;
        }
    }

    public void send(MessageType messageType, String title, String content, List<String> users) {
        if(initFailed){
            logger.error("消息推送配置有误");
            return;
        }
        if(null == users || users.isEmpty()) return;
        List<OaNotificationSubscribe> allSubscribeList = this.getAllSubscribe();
        for(OaNotificationSubscribe subscribe : allSubscribeList){
            String userNo = subscribe.getUserNo();
            if(users.contains(userNo)){
                MobileBrand mobileBrand = this.getMobileBrand(subscribe.getBrand(), subscribe.getOsVersion());
                MessagePusher messagePusher = this.getMessagePusher(mobileBrand);
                MessageEntity messageEntity = this.getMessageEntity(mobileBrand, messageType, title, subscribe.getToken(), userNo, 1, content);
                logger.info(messageEntity.toString());
                messagePusher.push(messageEntity);
            }
        }
    }

    public void send(MessageSimpleEntity messageSimpleEntity, int count) {
        if(initFailed){
            logger.error("消息推送配置有误");
            return;
        }
        if(null == messageSimpleEntity) return;
        List<String> users = messageSimpleEntity.getUserNos();
        if(null == users || users.isEmpty()) return;
        List<OaNotificationSubscribe> allSubscribeList = this.getAllSubscribe();
        for(OaNotificationSubscribe subscribe : allSubscribeList){
            String userNo = subscribe.getUserNo();
            if(users.contains(userNo)){
                MobileBrand mobileBrand = this.getMobileBrand(subscribe.getBrand(), subscribe.getOsVersion());
                MessagePusher messagePusher = this.getMessagePusher(mobileBrand);
                MessageEntity messageEntity = this.getMessageEntity(mobileBrand, messageSimpleEntity.getMessageType(), messageSimpleEntity.getTitle(),
                                                                        subscribe.getToken(), userNo, count, messageSimpleEntity.getContent());
                logger.info(messageEntity.toString());
                messagePusher.push(messageEntity);
            }
        }
    }

    /**
     * 推送单据审批消息
     */
    public void pushApproveMessage(){
        Map<String, List<OaNotificationA4sApproval>> approvalNotification = this.getNotApproved();
        List<OaNotificationSubscribe> allSubscribeList = this.getAllSubscribe();
        if(null == approvalNotification || approvalNotification.isEmpty()){
            this.pushUrgeApproveMessage(allSubscribeList);
            return;
        }

        Set<String> userNos = approvalNotification.keySet();
        List<Map<String, Object>> approvalList =  (List<Map<String, Object>>)baseDao.findByHql("select new map(userNo as userNo, count(*) as num) " +
                "from OaNotificationA4sApproval where effective = true and refusedNotification = false group by userNo");
        for(Map<String, Object> approval : approvalList) {
            String userNo = approval.get("userNo").toString();
            if(userNos.contains(userNo)){
                int count = Integer.parseInt(approval.get("num").toString());
                List<OaNotificationSubscribe> subscribes = this.getSubscribeListByUserNo(allSubscribeList, userNo);
                if (subscribes.isEmpty()) {
                    continue;
                }
                for (OaNotificationSubscribe subscribe : subscribes) {
                    MobileBrand mobileBrand = this.getMobileBrand(subscribe.getBrand(), subscribe.getOsVersion());
                    MessagePusher messagePusher = this.getMessagePusher(mobileBrand);
                    MessageEntity messageEntity = this.getMessageEntity(mobileBrand, MessageType.APPROVE, "待审事宜", subscribe.getToken(), userNo, count, null);
                    logger.info(messageEntity.toString());
                    MessageResult messageResult = messagePusher.push(messageEntity);
                    logger.info("返回的消息：{}", messageResult);
                }
            }
        }
        this.pushUrgeApproveMessage(allSubscribeList);
    }

    protected void pushUrgeApproveMessage(List<OaNotificationSubscribe> allSubscribeList){
        List<OaNotificationA4sApproval> approvalList = (List<OaNotificationA4sApproval>)baseDao.findByHql("from OaNotificationA4sApproval where  effective = true  AND DATEDIFF(HOUR, lastNotificationTime,getdate())>=? " +
                "AND userNo in (select userNo from OaNotificationSubscribe where subscribed = true)", URGE_HOUR);

        Map<String, List<OaNotificationA4sApproval>> map = new HashMap<String, List<OaNotificationA4sApproval>>();
        for(OaNotificationA4sApproval approval : approvalList){
            String userNo = approval.getUserNo();
            List<OaNotificationA4sApproval> userApproval = map.get(userNo);
            if(null == userApproval){
                userApproval = new ArrayList<OaNotificationA4sApproval>();
                map.put(userNo, userApproval);
            }
            userApproval.add(approval);
        }

        if(null != approvalList && !approvalList.isEmpty()){
            for(String userNo : map.keySet()){
                List<OaNotificationSubscribe> subscribes = this.getSubscribeListByUserNo(allSubscribeList, userNo);
                if (subscribes.isEmpty()) {
                    continue;
                }
                int count = map.get(userNo).size();
                for (OaNotificationSubscribe subscribe : subscribes) {
                    MobileBrand mobileBrand = this.getMobileBrand(subscribe.getBrand(), subscribe.getOsVersion());
                    MessagePusher messagePusher = this.getMessagePusher(mobileBrand);
                    MessageEntity messageEntity = this.getMessageEntity(mobileBrand, MessageType.APPROVE, "待审事宜", subscribe.getToken(), userNo, count, null);
                    messageEntity.setDescription( String.format("您有%d条很久未审批的待审事宜", count));
                    logger.debug(messageEntity.toString());
                    messagePusher.push(messageEntity);

                }

                List<OaNotificationA4sApproval> a4sApprovals = map.get(userNo);
                for(OaNotificationA4sApproval approval: a4sApprovals){
                    approval.setLastNotificationTime(new Timestamp(System.currentTimeMillis()));
                    approval.setNotificationTimes((short) (approval.getNotificationTimes()+1));
                    baseDao.update(approval);
                }
            }
        }
    }

    public MessageEntity getMessageEntity(MobileBrand mobileBrand, MessageType messageType,
                                             String title, String token, String userNo, int count, String customMessage){
        MessageEntity msgEntity = null;
        if(this.huawei_on && mobileBrand == MobileBrand.HUAWEI){
            HuaweiMessageEntity messageEntity = new HuaweiMessageEntity();
            messageEntity.setAppSecret(this.huawei_appSecret);
            messageEntity.setAppId(this.huawei_appId);
            messageEntity.setTokenUrl(this.huawei_tokenUrl);
            messageEntity.setApiUrl(this.huawei_apiUrl);
            msgEntity = messageEntity;
        }else if(this.mi_on && mobileBrand == MobileBrand.MI){
            MiMessageEntity messageEntity = new MiMessageEntity();
            messageEntity.setAppSecret(this.mi_appSecret);
            msgEntity = messageEntity;
        }else if(this.oppo_on && mobileBrand == MobileBrand.OPPO){
            OppoMessageEntity messageEntity = new OppoMessageEntity();
            messageEntity.setAppId(this.oppo_appKey);
            messageEntity.setAppSecret(this.oppo_appSecret);
            msgEntity = messageEntity;
        }else if(this.vivo_on && mobileBrand == MobileBrand.VIVO){
            VivoMessageEntity messageEntity = new VivoMessageEntity();
            messageEntity.setAppId(this.vivo_appId);
            messageEntity.setAppKey(this.vivo_appKey);
            messageEntity.setAppSecret(this.vivo_appSecret);
            msgEntity = messageEntity;
        }else{
            JPushMessageEntity messageEntity = new JPushMessageEntity();
            messageEntity.setAppSecret(this.jpush_appSecret);
            messageEntity.setAppId(this.jpush_appId);
            msgEntity = messageEntity;
        }
        msgEntity.setTitle(title);
        msgEntity.setToken(token);
        msgEntity.setBadge(count == 0 ? 1 : count);
//        msgEntity.setBadge(1);
        msgEntity.setPackageName(this.packageName);
        if(messageType == MessageType.APPROVE){
            msgEntity.setDescription(this.getApproveMessage(userNo, count));
        }else{
            msgEntity.setDescription(customMessage);
        }
        return msgEntity;
    }

    protected String getApproveMessage(String userNo, int count){
        return String.format("您有%d条待审事宜未审批", count);
    }


    public MobileBrand getMobileBrand(String brand, String osVersion){
        return MobileBrand.codeOf(brand);
    }

    public MessagePusher getMessagePusher(MobileBrand mobileBrand){
        if(this.huawei_on && mobileBrand == MobileBrand.HUAWEI){
            return this.huaweiMessagePusher;
        }else if(mobileBrand == MobileBrand.IOS){
            return this.iosMessagePusher;
        }else if(this.mi_on && mobileBrand == MobileBrand.MI){
            return this.miMessagePusher;
        }else if(this.vivo_on && mobileBrand == MobileBrand.VIVO){
            return this.vivoMessagePusher;
        }else if(this.oppo_on && mobileBrand == MobileBrand.OPPO){
            return this.oppoMessagePusher;
        }else {
            return this.jPushMessagePusher;
        }
    }



    protected List<OaNotificationSubscribe> getAllSubscribe(){
        return (List<OaNotificationSubscribe>)
                baseDao.findByHql("from OaNotificationSubscribe where token is not null AND token <> '' AND  subscribed = ? AND deviceTypeNo = ? AND productId IN (?, ?)",
                        true, MOBILE_TYPE_NO, IOS_PRODUCT_ID, ANDROID_PRODUCT_ID);
    }

    protected void invalidApprovalNofitication(Session session){

        session.createSQLQuery("update Oa_Notification_A4s_Approval set effective=0 \n" +
                "WHERE obj_id IN (\n" +
                "\t\tSELECT a.obj_id\n" +
                "\t\tFROM Oa_Notification_A4s_Approval a\n" +
                "\t\tWHERE NOT EXISTS (\n" +
                "\t\t\t\tSELECT 1\n" +
                "\t\t\t\tFROM Vw_Approve_Documents_Notification b\n" +
                "\t\t\t\tWHERE b.oadp_Id = a.approval_Point_Id and b.module_id in "+ApproveDocumentDaoHibernate.MODULE_IDS+"\n" +
                "\t\t\t)\n" +
                "\t\t\tAND a.effective = 1\n" +
                "\t)").executeUpdate();

    }

    /**
     * 获取所有需要审批的消息
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, List<OaNotificationA4sApproval>> getNotApproved() {
        Session session = baseDao.getCurrentSession().getSessionFactory().openSession();
        session.setFlushMode(FlushMode.COMMIT);
        Transaction tx = session.beginTransaction();
        tx.begin();
        try {
            // 修改OaNotificationA4sApproval中已审批的节点
            this.invalidApprovalNofitication(session);
            session.flush();
            session.clear();
            // 将未加入notification的记录加入
            List<VwApproveDocumentsNotification> documentList = this.getAllApprovingMatters(session);
            Map<String, List<OaNotificationA4sApproval>> resultMap = new HashMap<String, List<OaNotificationA4sApproval>>();
            for (int i = documentList.size() - 1; i >= 0; i--) {
                VwApproveDocumentsNotification documemnt = documentList.get(i);
                String approverNo = documemnt.getApproverNo();
                if (StringUtils.isNotEmpty(approverNo)) {
                    String[] arrNos = approverNo.split(",");
                    for (String userNo : arrNos) {
                        OaNotificationA4sApproval newNotification = org.springframework.beans.BeanUtils.instantiate(OaNotificationA4sApproval.class);
                        newNotification.setApprovalPointId(documemnt.getOadpId());
                        newNotification.setApprovalPoint((ApproveDocumentsPoints)session.get(ApproveDocumentsPoints.class, documemnt.getOadpId()));
                        newNotification.setEffective(true);
                        newNotification.setRefusedNotification(false);
                        newNotification.setUserNo(userNo);
                        newNotification.setLastNotificationTime(new Timestamp(System.currentTimeMillis()));
                        newNotification.setNotificationTimes((short) 1);
                        session.save(newNotification);

                        List<OaNotificationA4sApproval> list = resultMap.get(userNo);
                        if (list == null) {
                            list = new ArrayList<OaNotificationA4sApproval>();
                        }
                        list.add(newNotification);
                        resultMap.put(userNo, list);
                    }
                }
            }
            tx.commit();
            return resultMap;
        }catch (Exception ex){
            logger.error("增加未审批消息出错", ex);
            tx.rollback();
        }finally {
            if(null != session){
                session.clear();
                session.close();
            }
        }
        return null;
    }

    protected List<VwApproveDocumentsNotification> getAllApprovingMatters(Session session){
        Query query = session.createQuery("from VwApproveDocumentsNotification d  where not exists (select 1 from OaNotificationA4sApproval b  where  b.approvalPointId = d.oadpId) AND d.moduleId IN "+ ApproveDocumentDaoHibernate.MODULE_IDS);
        return query.list();
    }

    private List<OaNotificationSubscribe> getSubscribeListByUserNo(
            List<OaNotificationSubscribe> allSubscribeList, String userNo) {
        List<OaNotificationSubscribe> subscribeList = new ArrayList<OaNotificationSubscribe>();
        if(StringUtils.isEmpty(userNo))
            return subscribeList;
        for(OaNotificationSubscribe subscribe :allSubscribeList){
            if(userNo.equals(subscribe.getUserNo()))
                subscribeList.add(subscribe);
        }

        return subscribeList;
    }
}