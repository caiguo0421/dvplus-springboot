package cn.sf_soft.message.pusher;

import cn.sf_soft.message.MessageEntity;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/31 11:43
 * @Description:
 */
public class HuaweiMessageEntity extends MessageEntity {

    private String appSecret;

    private String appId;

    private String tokenUrl;

    private String apiUrl;

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

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
