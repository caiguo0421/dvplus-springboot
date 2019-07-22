package cn.sf_soft.message.pusher;

import cn.sf_soft.common.gson.GsonUtil;
import cn.sf_soft.message.MessagePusher;
import cn.sf_soft.message.MessageResult;
import com.google.gson.JsonObject;
import com.oppo.push.server.Notification;
import com.oppo.push.server.Result;
import com.oppo.push.server.Sender;
import com.oppo.push.server.Target;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/30 15:41
 * @Description: oppo手机消息推送
 */
@Component
@Scope("prototype")
public class OppoMessagePusher implements MessagePusher<OppoMessageEntity> {
    private static Logger logger = LoggerFactory.getLogger("MessagePusher oppo");
    @Override
    public MessageResult push(OppoMessageEntity message) {
        logger.info("使用oppo消息推送服务");
        try {
            Sender sender = new Sender(message.getAppId(), message.getAppSecret());
            Notification notification = getNotification(message);// 创建通知栏消息体
            Target target = Target.build(message.getToken()); //创建发送对象
            Result result = sender.unicastNotification(notification, target);  //发送单推消息
            logger.info("oppo消息推送返回结果：{}", result);
            return this.getResultMessage(result);
        } catch (Exception e) {
            logger.error("oppo消息推送失败", e);
            return new MessageResult(MessageResult.ERROR, "oppo消息推送失败");
        }

    }

    private MessageResult getResultMessage(Result result){
        if(null == result){
            return new MessageResult();
        }else{
            int code = result.getReturnCode().getCode();
            if(code == 0){
                return new MessageResult(MessageResult.SUCCESS, result);
            }else if(code == 11){
                return new MessageResult(MessageResult.INVALID_TOKEN, result);
            }else{
                return new MessageResult(MessageResult.ERROR, result);
            }
        }
    }

    private Notification getNotification(OppoMessageEntity messageEntity) {
        Notification notification = new Notification();
        /**
         * 以下参数必填项
         */
        notification.setTitle(messageEntity.getTitle());
        // notification.setSubTitle("sub tile");
        notification.setContent(messageEntity.getDescription());
        /**
         * 以下参数非必填项， 如果需要使用可以参考OPPO push服务端api文档进行设置
         */
        // App开发者自定义消息Id，OPPO推送平台根据此ID做去重处理，对于广播推送相同appMessageId只会保存一次，对于单推相同appMessageId只会推送一次
        notification.setAppMessageId(UUID.randomUUID().toString());
        // 点击动作类型0，启动应用；1，打开应用内页（activity的intent action）；2，打开网页；4，打开应用内页（activity）；【非必填，默认值为0】;5,Intent scheme URL
        notification.setClickActionType(0);
        // 展示类型 (0, “即时”),(1, “定时”)
        notification.setShowTimeType(0);
        /*// 定时展示开始时间（根据time_zone转换成当地时间），时间的毫秒数
        notification.setShowStartTime(System.currentTimeMillis() + 1000 * 60 * 3);
        // 定时展示结束时间（根据time_zone转换成当地时间），时间的毫秒数
        notification.setShowEndTime(System.currentTimeMillis() + 1000 * 60 * 5);*/
        // 是否进离线消息,【非必填，默认为True】
        notification.setOffLine(true);
        // 离线消息的存活时间(time_to_live) (单位：秒), 【off_line值为true时，必填，最长3天】
        notification.setOffLineTtl(24 * 3600);
        // 时区，默认值：（GMT+08:00）北京，香港，新加坡
        notification.setTimeZone("GMT+08:00");
        // 0：不限联网方式, 1：仅wifi推送
        notification.setNetworkType(0);
        return notification;

    }
}
