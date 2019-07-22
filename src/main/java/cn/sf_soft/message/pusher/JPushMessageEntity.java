package cn.sf_soft.message.pusher;

import cn.sf_soft.message.MessageEntity;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/31 11:46
 * @Description:
 */
public class JPushMessageEntity extends MessageEntity {

    private String appSecret;

    private String appId;

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
