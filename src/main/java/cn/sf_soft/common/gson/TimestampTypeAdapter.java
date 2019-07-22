package cn.sf_soft.common.gson;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @Description: Timestamp转换适配器
 * @author ShiChunshan
 * @date 2015-4-10 上午9:33:28
 * @version V1.0
 */
public class TimestampTypeAdapter implements JsonSerializer<Timestamp>,
		JsonDeserializer<Timestamp> {
	private final DateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");
	// 针对"Apr 20, 2016 12:00:00 AM"格式的转换 caigx 20160420
	private final SimpleDateFormat enLocalFormat = new SimpleDateFormat(
			"MMM d, yyyy K:m:s a", Locale.ENGLISH);

	@Override
	public JsonElement serialize(Timestamp src, Type arg1,
			JsonSerializationContext arg2) {
		String dateFormatAsString = format.format(new Date(src.getTime()));
		return new JsonPrimitive(dateFormatAsString);
	}

	@Override
	public Timestamp deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		}

		try {
			// 首先用 yyyy-MM-dd HH:mm:ss.SSS 格式转换
			Date date = format.parse(json.getAsString());
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			// 如果不成功，针对"Apr 20, 2016 12:00:00 AM"格式转换
			try {
				Date date1 = enLocalFormat.parse(json.getAsString());
				return new Timestamp(date1.getTime());
			} catch (Exception ex) {
				throw new JsonParseException(e);
			}
		}
	}

}
