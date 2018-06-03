package com.tyebile.meereen.datasource.api.annotation;

import java.lang.annotation.*;

/**
 * @author zhouhao
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UseDefaultDataSource {
}
