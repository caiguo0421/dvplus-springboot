package cn.sf_soft.message.pusher;

import cn.sf_soft.message.MessagePusher;
import cn.sf_soft.message.MessageResult;
import com.vivo.push.sdk.notofication.Message;
import com.vivo.push.sdk.notofication.Result;
import com.vivo.push.sdk.server.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/30 15:40
 * @Description: vivo消息推送
 */
@Component
@Scope("prototype")
public class VivoMessagePusher implements MessagePusher<VivoMessageEntity> {
    private static Logger logger = LoggerFactory.getLogger("MessagePusher oppo");
    @Override
    public MessageResult push(VivoMessageEntity message) {
        logger.info("使用vivo消息推送服务");
        try {
            Sender sender = new Sender(message.getAppId(), message.getAppSecret());
            Result result = sender.getToken(Integer.parseInt(message.getAppId()), message.getAppKey());
            Sender senderMessage = new Sender(message.getAppSecret(), result.getAuthToken());
            Message singleMessage = new Message.Builder()
                    .regId(message.getToken())//该测试手机设备订阅推送后生成的regId
                    .notifyType(3)
                    .title(message.getTitle())
                    .content(message.getDescription())
                    .skipType(1)
                    .networkType(-1)
                    .requestId(UUID.randomUUID().toString())
                    .build();
            Result resultMessage = senderMessage.sendSingle(singleMessage);
            logger.info("vivo消息推送返回结果：{}", resultMessage);
            if(resultMessage.getResult() == 0){
                return new MessageResult(MessageResult.SUCCESS, resultMessage);
            }else if(resultMessage.getResult() == 10302){
                return new MessageResult(MessageResult.INVALID_TOKEN, resultMessage);
            }else{
                return new MessageResult(MessageResult.ERROR, resultMessage);
            }
        } catch (Exception e) {
            logger.error("vivo消息推送失败", e);
            return new MessageResult(MessageResult.ERROR, e.getMessage());
        }
    }
}
