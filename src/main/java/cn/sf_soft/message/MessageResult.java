package cn.sf_soft.message;

import cn.sf_soft.common.gson.GsonUtil;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/31 14:44
 * @Description:
 */
public class MessageResult {

    public static final int SUCCESS = 200;
    public static final int ERROR = 500;
    public static final int INVALID_TOKEN = 501;

    public int code = 200;
    public Object result;

    public MessageResult(){

    }

    public MessageResult(int code){
        this.code = code;
    }

    public MessageResult(int code, Object result){
        this.code = code;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String toString(){
        return String.format("code:%s;result:%s",this.code, null != this.result ? GsonUtil.GSON.toJson(result).toString() : "");
    }
}
