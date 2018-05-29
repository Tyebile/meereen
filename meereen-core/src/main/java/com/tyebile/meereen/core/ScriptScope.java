package com.tyebile.meereen.core;

import java.lang.annotation.*;

/**
 * @author zhouhao
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScriptScope {

    String value() default "";
}
