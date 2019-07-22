package cn.sf_soft.message.pusher;
import cn.sf_soft.message.MessagePusher;

import cn.sf_soft.message.MessageResult;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/28 15:17
 * @Description: 小米消息推送
 */
@Component
@Scope("prototype")
public class MiMessagePusher implements MessagePusher<MiMessageEntity> {

    private static Logger logger = LoggerFactory.getLogger("MessagePusher mi");

    /*@Value("${mi.message.push.appSecret}")
    private String appSecret;

    @Value("${app.packageName}")
    private String packageName;*/

    @Override
    public MessageResult push(MiMessageEntity messageEntity) {
        logger.info("使用mi推送");
        Constants.useOfficial();
        Sender sender = new Sender(messageEntity.getAppSecret());

        Message message = new Message.Builder()
                .title(messageEntity.getTitle())
                .description(messageEntity.getDescription())
                .restrictedPackageName(messageEntity.getPackageName())
                .payload(messageEntity.getDescription())
                .notifyType(1)
                .build();

        try {
            Result result = sender.send(message, messageEntity.getToken(), 3);
            logger.info("Mi消息推送返回结果：{}", result);
            if(result.getErrorCode().getValue() == 0){
                return new MessageResult(MessageResult.SUCCESS, result);
            }else if(result.getErrorCode().getValue() == 66007){
                return new MessageResult(MessageResult.INVALID_TOKEN, result);
            }else{
                return new MessageResult(MessageResult.ERROR, result);
            }
        } catch (IOException e) {
            logger.error("mi发送消息出错", e);
            return new MessageResult(MessageResult.ERROR, e.getMessage());
        } catch (ParseException e) {
            logger.error("mi发送消息时解析出错", e);
            return new MessageResult(MessageResult.ERROR, e.getMessage());
        }
    }
}
