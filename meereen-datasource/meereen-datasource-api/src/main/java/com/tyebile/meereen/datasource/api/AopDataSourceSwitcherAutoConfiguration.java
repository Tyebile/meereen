package com.tyebile.meereen.datasource.api;

import org.aopalliance.intercept.MethodInterceptor;
import com.tyebile.meereen.commons.utils.AopUtils;
import com.tyebile.meereen.commons.utils.ExpressionUtils;
import com.tyebile.meereen.boost.aop.MethodInterceptorHolder;
import com.tyebile.meereen.datasource.api.annotation.UseDataSource;
import com.tyebile.meereen.datasource.api.annotation.UseDefaultDataSource;
import com.tyebile.meereen.datasource.api.exception.DataSourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 通过aop方式进行对注解方式切换数据源提供支持
 *
 * @author zhouhao
 * @since 3.0
 */
@Configuration
public class AopDataSourceSwitcherAutoConfiguration {

    @Bean
    public SwitcherMethodMatcherPointcutAdvisor switcherMethodMatcherPointcutAdvisor() {
        return new SwitcherMethodMatcherPointcutAdvisor();
    }

    public static class SwitcherMethodMatcherPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {
        private static final Logger logger = LoggerFactory.getLogger(SwitcherMethodMatcherPointcutAdvisor.class);

        public SwitcherMethodMatcherPointcutAdvisor() {
            setAdvice((MethodInterceptor) methodInvocation -> {
                logger.debug("switch datasource...");
                UseDataSource useDataSource = AopUtils.findAnnotation(methodInvocation.getThis().getClass(),
                        methodInvocation.getMethod(), UseDataSource.class);
                if (useDataSource != null) {
                    String id = useDataSource.value();
                    if (id.contains("${")) {
                        MethodInterceptorHolder holder = MethodInterceptorHolder.create(methodInvocation);
                        id = ExpressionUtils.analytical(id, holder.getArgs(), "spel");
                    }
                    if (!DataSourceHolder.existing(id)) {
                        if (useDataSource.fallbackDefault()) {
                            DataSourceHolder.switcher().useDefault();
                        } else {
                            throw new DataSourceNotFoundException(id);
                        }
                    } else {
                        DataSourceHolder.switcher().use(id);
                    }
                } else {
                    UseDefaultDataSource useDefaultDataSource = AopUtils.findAnnotation(methodInvocation.getThis().getClass(),
                            methodInvocation.getMethod(), UseDefaultDataSource.class);
                    if (useDefaultDataSource == null) {
                        logger.warn("can't found  annotation: UseDefaultDataSource !");
                    }
                    DataSourceHolder.switcher().useDefault();
                }
                try {
                    return methodInvocation.proceed();
                } finally {
                    DataSourceHolder.switcher().useLast();
                }
            });
        }

        @Override
        public boolean matches(Method method, Class<?> aClass) {
            return AopUtils.findAnnotation(aClass, method, UseDataSource.class) != null ||
                    AopUtils.findAnnotation(aClass, method, UseDefaultDataSource.class) != null;
        }
    }
}
