package cn.sf_soft.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模块级别(Struts Action)的访问控制注解，只能用于注解在Struts Action中，用于指定模块的ID和访问模块所需要的权限ID,
 * 如果设为pass，则不检验方法中注解的权限
 * @创建人 LiuJin
 * @创建时间 2014-8-26 上午11:37:07
 * @修改人 
 * @修改时间
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleAccess {
	
	/**
	 * 模块ID
	 * @return
	 */
	String moduleId();
	
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
