package cn.sf_soft.common.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Gson序列化时忽略的属性
 * @创建人 LiuJin
 * @创建时间 2014-12-16 下午3:14:34
 * @修改人 
 * @修改时间
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GsonSerializeIgnore {

}
