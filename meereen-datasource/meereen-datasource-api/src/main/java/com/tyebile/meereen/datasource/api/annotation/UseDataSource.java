package com.tyebile.meereen.datasource.api.annotation;

import com.tyebile.meereen.datasource.api.DataSourceHolder;
import com.tyebile.meereen.datasource.api.DynamicDataSource;

import java.lang.annotation.*;

/**
 * @author zhouhao
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UseDataSource {
    /**
     * @return 数据源ID ,支持表达式如 : ${#param.id}
     * @see DynamicDataSource#getId()
     */
    String value();

    /**
     * @return 数据源不存在时, 是否使用默认数据源.
     * 如果为{@code false},当数据源不存在的时候,
     * 将抛出 {@link com.tyebile.meereen.datasource.api.exception.DataSourceNotFoundException}
     * @see DataSourceHolder#currentExisting()
     */
    boolean fallbackDefault() default true;
}
