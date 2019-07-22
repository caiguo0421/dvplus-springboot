package cn.sf_soft.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2019/6/1 10:47
 * @Description:
 */
public class MessageSimpleEntity {

    private MessageType messageType = MessageType.OTHER;
    private String title = "通知";
    private String content;
    private List<String> userNos;

    public MessageSimpleEntity(){

    }

    public MessageSimpleEntity(String title, String content, List<String> userNos){
        this.title = title;
        this.content = content;
        this.userNos = userNos;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getUserNos() {
        return userNos;
    }

    public void setUserNos(List<String> userNos) {
        this.userNos = userNos;
    }

    public void setUserNos(String[] userNos) {
        List<String> list = new ArrayList<String>(userNos.length);
        Collections.addAll(list, userNos);
        this.userNos = list;
    }
}
