package org.project01.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 第一个注解表示在代码运行时可以获取
// 第二个注解表示是标识在方法上

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {
    // 这里写value()可以省略属性名
    String value() default "ROLE_USER";
}
