package com.tyebile.meereen.logging.aop;


import com.tyebile.meereen.logging.api.AccessLoggerListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AOP 访问日志记录自动配置
 *
 * @author Tyebile
 * @see com.tyebile.meereen.logging.api.AccessLogger
 * @see AopAccessLoggerSupport
 */
@ConditionalOnClass(AccessLoggerListener.class)
@Configuration
public class AopAccessLoggerSupportAutoConfiguration {

    @Bean
    public AopAccessLoggerSupport aopAccessLoggerSupport() {
        return new AopAccessLoggerSupport();
    }

    @Bean
    public DefaultAccessLoggerParser defaultAccessLoggerParser(){
        return new DefaultAccessLoggerParser();
    }

    @Bean
    @ConditionalOnClass(name = "io.swagger.annotations.Api")
    public SwaggerAccessLoggerParser swaggerAccessLoggerParser(){
        return new SwaggerAccessLoggerParser();
    }

}

