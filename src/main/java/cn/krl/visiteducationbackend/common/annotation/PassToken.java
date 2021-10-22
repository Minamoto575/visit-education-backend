package cn.krl.visiteducationbackend.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

/**
 * 跳过token验证
 * @author kuang
 */
public @interface PassToken {
    boolean required() default true;
}
