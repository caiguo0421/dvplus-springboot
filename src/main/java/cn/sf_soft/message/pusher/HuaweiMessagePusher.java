package cn.sf_soft.message.pusher;

import cn.sf_soft.common.gson.GsonUtil;
import cn.sf_soft.message.MessagePusher;
import cn.sf_soft.message.MessageResult;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: chenbiao
 * @Date: 2019/5/28 15:22
 * @Description: 华为消息推送
 */
@Component
@Scope("prototype")
public class HuaweiMessagePusher implements MessagePusher<HuaweiMessageEntity> {

    private static Logger logger = LoggerFactory.getLogger("MessagePusher huawei");

    /*@Value("${huawei.message.push.appSecret}")
    private String appSecret;
    @Value("${huawei.message.push.appId}")
    private String appId;
    @Value("${huawei.message.push.tokenUrl}")
    private String tokenUrl;
    @Value("${huawei.message.push.apiUrl}")
    private String apiUrl;
    @Value("${app.packageName}")
    private String packageName;*/
    private static Map<String, String> accessToken = new ConcurrentHashMap<String, String>();//下发通知消息的认证Token
    private static Map<String, Long> tokenExpiredTime = new ConcurrentHashMap<String, Long>();  //accessToken的过期时间

    //获取下发通知消息的认证Token
    private  void refreshToken(HuaweiMessageEntity message) throws IOException
    {
        String msgBody = MessageFormat.format(
                "grant_type=client_credentials&client_secret={0}&client_id={1}",
                URLEncoder.encode(message.getAppSecret(), "UTF-8"), message.getAppId());
        String response = httpPost(message.getTokenUrl(), msgBody, 5000, 5000);
        JsonObject jsonObject = GsonUtil.GSON.fromJson(response, JsonObject.class);
        String token = GsonUtil.getAsString(jsonObject, "access_token");
        accessToken.put(message.getAppId(), token);

        long expiresIn = jsonObject.get("expires_in").getAsLong();
        long expiredTime = System.currentTimeMillis() + expiresIn - 5*60*1000;
        tokenExpiredTime.put(message.getAppId(), expiredTime);
    }

    @Override
    public MessageResult push(HuaweiMessageEntity message) {
        logger.info("使用华为消息推送");
        if (null == tokenExpiredTime.get(message.getAppId()) || tokenExpiredTime.get(message.getAppId()) <= System.currentTimeMillis())
        {
            try {
                refreshToken(message);
            } catch (IOException e) {
                logger.error("获取下发通知消息的认证Token出错", e);
                return new MessageResult(MessageResult.ERROR, "获取下发通知消息的认证Token出错");
            }
        }
        /*PushManager.requestToken为客户端申请token的方法，可以调用多次以防止申请token失败*/
        /*PushToken不支持手动编写，需使用客户端的onToken方法获取*/
        JSONArray deviceTokens = new JSONArray();//目标设备Token
        deviceTokens.add(message.getToken());

        JSONObject body = new JSONObject();//仅通知栏消息需要设置标题和内容，透传消息key和value为用户自定义
        body.put("title", message.getTitle());//消息标题
        body.put("content", message.getDescription());//消息内容体

        JSONObject param = new JSONObject();
        param.put("appPkgName", message.getPackageName());//定义需要打开的appPkgName

        JSONObject action = new JSONObject();
        action.put("type", 3);//类型3为打开APP，其他行为请参考接口文档设置
        action.put("param", param);//消息点击动作参数

        JSONObject msg = new JSONObject();
        msg.put("type", 3);//3: 通知栏消息，异步透传消息请根据接口文档设置
        msg.put("action", action);//消息点击动作
        msg.put("body", body);//通知栏消息body内容

        JSONObject ext = new JSONObject();//扩展信息，含BI消息统计，特定展示风格，消息折叠。
        ext.put("badgeAddNum", message.getBadge() + "");
        ext.put("badgeClass", message.getPackageName() + ".MainActivity");
        ext.put("biTag", "Trump");//设置消息标签，如果带了这个标签，会在回执中推送给CP用于检测某种类型消息的到达率和状态

        JSONObject hps = new JSONObject();//华为PUSH消息总结构体
        hps.put("msg", msg);
        hps.put("ext", ext);

        JSONObject payload = new JSONObject();
        payload.put("hps", hps);

        String postBody = null;
        try {
            postBody = MessageFormat.format(
                    "access_token={0}&nsp_svc={1}&nsp_ts={2}&device_token_list={3}&payload={4}",
                    URLEncoder.encode(accessToken.get(message.getAppId()),"UTF-8"),
                    URLEncoder.encode("openpush.message.api.send","UTF-8"),
                    URLEncoder.encode(String.valueOf(System.currentTimeMillis() / 1000),"UTF-8"),
                    URLEncoder.encode(deviceTokens.toString(),"UTF-8"),
                    URLEncoder.encode(payload.toString(),"UTF-8"));
            String postUrl = message.getApiUrl() + "?nsp_ctx=" + URLEncoder.encode("{\"ver\":\"1\", \"appId\":\"" + message.getAppId() + "\"}", "UTF-8");
            String result = httpPost(postUrl, postBody, 5000, 5000);
            logger.info("huawei消息推送返回结果：{}", result);
            return getResultMessage(result);
        } catch (UnsupportedEncodingException e) {
            logger.error("huawei消息编码错误", e);
            return new MessageResult(MessageResult.ERROR, "huawei消息编码错误");
        } catch (IOException e){
            logger.error("huawei消息发送失败", e);
            return new MessageResult(MessageResult.ERROR, "huawei消息发送失败");
        }



    }

    private MessageResult getResultMessage(String result){
        if(StringUtils.isEmpty(result)){
            return new MessageResult(MessageResult.ERROR);
        }else{
            JsonObject jsonObject = GsonUtil.GSON.fromJson(result, JsonObject.class);
            String code = GsonUtil.getAsString(jsonObject, "code");
            if("80000000".equals(code)){
                return new MessageResult(MessageResult.SUCCESS, jsonObject);
            }else if("80100002".equals(code)){
                return new MessageResult(MessageResult.INVALID_TOKEN, jsonObject);
            }else{
                return new MessageResult(MessageResult.ERROR, jsonObject);
            }
        }
    }

    protected String httpPost(String httpUrl, String data, int connectTimeout, int readTimeout) throws IOException
    {
        OutputStream outPut = null;
        HttpURLConnection urlConnection = null;
        InputStream in = null;

        try
        {
            URL url = new URL(httpUrl);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            urlConnection.setConnectTimeout(connectTimeout);
            urlConnection.setReadTimeout(readTimeout);
            urlConnection.connect();

            // POST data
            outPut = urlConnection.getOutputStream();
            outPut.write(data.getBytes("UTF-8"));
            outPut.flush();

            // read response
            if (urlConnection.getResponseCode() < 400)
            {
                in = urlConnection.getInputStream();
            }
            else
            {
                in = urlConnection.getErrorStream();
            }

            List<String> lines = IOUtils.readLines(in, urlConnection.getContentEncoding());
            StringBuffer strBuf = new StringBuffer();
            for (String line : lines)
            {
                strBuf.append(line);
            }
            return strBuf.toString();
        }
        finally
        {
            IOUtils.closeQuietly(outPut);
            IOUtils.closeQuietly(in);
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }
    }
}
