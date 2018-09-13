package com.tyebile.meereen.logging.aop;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.*;

/**
 * 启用访问日志
 *
 * @see AopAccessLoggerSupportAutoConfiguration
 * @see com.tyebile.meereen.logging.api.AccessLoggerListener
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration(AopAccessLoggerSupportAutoConfiguration.class)
public @interface EnableAccessLogger {
}
