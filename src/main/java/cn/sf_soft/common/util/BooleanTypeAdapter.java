package cn.sf_soft.common.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Boolean json 反序列化Adapter，增加 0,1转换成Boolean
 * @创建人 LiuJin
 * @创建时间 2014-9-18 上午10:46:57
 * @修改人 
 * @修改时间
 */
public class BooleanTypeAdapter implements JsonDeserializer<Boolean> {

	public Boolean deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		try {
			int code = json.getAsInt();
			return code == 0 ? false : code == 1 ? true : null;
		} catch (Exception e) {
			return json.getAsBoolean();
		}
	}
}
