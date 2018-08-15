package com.codyy.urlannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * URL注解类
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface URL {
    String value();
}
