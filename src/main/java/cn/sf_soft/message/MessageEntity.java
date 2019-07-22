package cn.sf_soft.message;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/28 15:18
 * @Description:
 */
public class MessageEntity {

    /**
     * 消息标题
     */
    String title;
    /**
     * 消息描述
     */
    String description;

    int badge;

    String token;

    private String packageName;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return String.format("title:%s;description:%s;badge:%s;token:%s", title, description, badge, token);
    }
}
