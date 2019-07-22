package cn.sf_soft.message;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/28 15:16
 * @Description:
 */
public interface MessagePusher<T extends MessageEntity> {

    MessageResult push(T message);
}
