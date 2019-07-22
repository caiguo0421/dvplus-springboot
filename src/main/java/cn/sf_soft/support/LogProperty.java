package cn.sf_soft.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Auther: chenbiao
 * @Date: 2018/7/6 09:20
 * @Description:
 */
@Retention(RUNTIME)
@Target({ ElementType.FIELD })
public @interface LogProperty {

    /**
     * 日志对象中的属性名称
     * @return
     */
    String name() default "";

    /**
     * 日志对象中的属性类型
     * @return
     */
    LogPropertyType type() default LogPropertyType.STRING;

    String nullDefault() default "";
    /**
     * 格式化，有属性是日期类型，但是在日志对象中使用的yyyy-MM-dd格式的字符串
     * @return
     */
    String dateFormat() default "";

    /**
     * 是否将原始值和最新值连接并存储
     * @return
     */
    boolean join() default true;


}
