package cn.sf_soft.support;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * @Auther: chenbiao
 * @Date: 2018/6/30 09:14
 * @Description: 将传入的json数组转换成对象集合
 */
public interface ParameterConverter {

    public static final String OP_CODE = "opCode";

    <T> List<T> convert(JsonObject parameter, Command command);
}
