package cn.sf_soft.message;

import java.lang.annotation.*;

/**
 * @Auther: chenbiao
 * @Date: 2019/6/13 09:43
 * @Description:
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Prop {
    String value();
}
