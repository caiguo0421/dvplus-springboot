package cn.sf_soft.common.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GsonExclutionStrategy implements ExclusionStrategy{

	public boolean shouldSkipField(FieldAttributes f) {
		GsonSerializeIgnore ignore = f.getAnnotation(GsonSerializeIgnore.class);
		return ignore != null;
	}

	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
