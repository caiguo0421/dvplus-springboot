package cn.sf_soft.message.service;

import cn.sf_soft.common.ServiceException;
import cn.sf_soft.message.Prop;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Auther: chenbiao
 * @Date: 2019/6/13 09:10
 * @Description:
 */
public class MessagePusherConfig {

    private static Map<String, Object> config;
    private static boolean faild = false;

    static{
        init();
    }

    private static void init(){
        Properties prop = new Properties();
        InputStream in = null;
        config = new HashMap<String, Object>();
        faild = false;
        try {
            in = MessagePusherConfig.class.getClassLoader().getResourceAsStream("messagePusherConfig.properties");
            prop.load(in);
            for (Object o : prop.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                config.put(entry.getKey().toString(), entry.getValue());
            }
            in.close();
        } catch (IOException e) {
            faild = true;
        }finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e1) {}
            }
        }
    }

    public static Object getProp(String name){
        if(faild){
            throw new ServiceException("初始化消息推送配置出错");
        }
        return config.get(name);
    }

    public static void initPropField(Object obj) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        if(fields.length > 0){
            for (Field field : fields) {
                Prop value = field.getAnnotation(Prop.class);
                if (null != value) {
                    field.setAccessible(true);
                    String valueField = value.value();
                    Object v = MessagePusherConfig.getProp(valueField);
                    Object v1 = null;
                    if("boolean".equals(field.getType().getSimpleName())){
                        if(null != v){
                            String sVal = v.toString().toLowerCase();
                            if (sVal.equals("y") || sVal.equals("yes") || sVal.equals("t") || sVal.equals("true") || sVal.equals("1")) {
                                v1 = true;
                            } else {
                                v1 = false;
                            }
                        }else{
                            v1 = false;
                        }
                    }else{
                        v1 = v.toString();
                    }
                    field.set(obj, v1);
                }
            }
        }
    }
}
