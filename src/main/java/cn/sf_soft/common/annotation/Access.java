package cn.sf_soft.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 访问控制注解，标记访问方法所需要的权限<br>
 * 该注解保留到运行时，可通过反射读取<br>
 * 注解针对方法而使用
 * @author king
 * @create 2013-7-19下午3:38:37
 * @modify 2013-9-25下午15:46 增加pass(),对于有些方法可不验证权限
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Access {
	/**
	 * 所需要的权限
	 * @return
	 */
	String needPopedom() default "";
	/**
	 * 满足数组中其中一个权限即可
	 * @return
	 */
	String[] needOneOfPopedoms() default {};
	/**
	 * 需要满足数组中所有权限
	 * @return
	 */
	String[] needAllOfPopedoms() default {};
	/**
	 * 是否不验证权限
	 * @return
	 */
	boolean pass() default false;
}
