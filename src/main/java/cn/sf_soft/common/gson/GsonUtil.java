package cn.sf_soft.common.gson;

import cn.sf_soft.common.util.BooleanTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.Timestamp;

/**
 * @Auther: chenbiao
 * @Date: 2018/8/23 17:14
 * @Description:
 */
public class GsonUtil {

    public static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Boolean.class, new BooleanTypeAdapter())
            .registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
            .registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
            .setExclusionStrategies(new GsonExclutionStrategy()).serializeNulls().create();

    public static String getAsString(JsonObject jsonObject, String memberName){
        JsonElement element = jsonObject.get(memberName);
        if(null == element || element.isJsonNull()){
            return null;
        }
        return element.getAsString();
    }

    public static Number getAsNumber(JsonObject jsonObject, String memberName){
        JsonElement element = jsonObject.get(memberName);
        if(null == element || element.isJsonNull()){
            return null;
        }
        return element.getAsNumber();
    }

}
