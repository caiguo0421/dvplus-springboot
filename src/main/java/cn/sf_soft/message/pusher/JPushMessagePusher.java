package cn.sf_soft.message.pusher;

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
import cn.sf_soft.message.MessagePusher;
import cn.sf_soft.message.MessageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/28 17:08
 * @Description:
 */
@Component("jPushMessagePusher")
@Scope("prototype")
public class JPushMessagePusher implements MessagePusher<JPushMessageEntity> {

    private static Logger logger = LoggerFactory.getLogger("MessagePusher jpush");

    /*@Value("${jpush.masterSecret}")
    private String appSecret;

    @Value("${jpush.appKey}")
    private String key;*/

    @Override
    public MessageResult push(JPushMessageEntity message) {
        logger.debug("开始调用JPush");
        JPushClient jpushClient = new JPushClient(
                message.getAppSecret(),
                message.getAppId(),
                null, ClientConfig.getInstance());
        String jpushId = message.getToken();

        if (jpushId == null || jpushId.length() == 0){
            logger.info("JPush调用失败：JPushId不存在");
            return new MessageResult(MessageResult.ERROR, "JPushId不存在");
        }

        PushPayload payload =  PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(jpushId))
                .setNotification(Notification.newBuilder()
                                .addPlatformNotification(
                                        IosNotification.newBuilder()
                                        .setBadge(message.getBadge())
                                        .setAlert(message.getDescription())
                                        .setSound("happy")
                                        .build()
                                ).addPlatformNotification(
                                        AndroidNotification.newBuilder()
                                        .setAlert(message.getDescription())
                                        .build()
                        ).build()
                ).setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("JPush: Got result - " + result);
            return new MessageResult(MessageResult.SUCCESS, result);
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            logger.error("JPush: Connection error, should retry later", e);
            return new MessageResult(MessageResult.ERROR, e.getMessage());
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            logger.error("JPush: Should review the error, and fix the request", e);
            logger.info("JPush: HTTP Status: " + e.getStatus());
            logger.info("JPush: Error Code: " + e.getErrorCode());
            logger.info("JPush: Error Message: " + e.getErrorMessage());
            if(e.getErrorCode() == 1003){
                return new MessageResult(MessageResult.INVALID_TOKEN, e.getErrorMessage());
            }
            return new MessageResult(MessageResult.ERROR, e.getErrorMessage());
        }
    }

}
