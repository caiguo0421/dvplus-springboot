package cn.sf_soft.message.pusher;

import cn.sf_soft.message.MessageEntity;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/31 11:45
 * @Description:
 */
public class MiMessageEntity extends MessageEntity {

    private String appSecret;

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
