package com.tyebile.meereen.logging.aop;

import org.aopalliance.intercept.MethodInterceptor;
import com.tyebile.meereen.commons.utils.AopUtils;
import com.tyebile.meereen.commons.utils.WebUtil;
import com.tyebile.meereen.boost.aop.MethodInterceptorHolder;
import com.tyebile.meereen.core.id.IDGenerator;
import com.tyebile.meereen.logging.api.AccessLogger;
import com.tyebile.meereen.logging.api.AccessLoggerInfo;
import com.tyebile.meereen.logging.api.AccessLoggerListener;
import com.tyebile.meereen.logging.api.LoggerDefine;
import com.tyebile.meereen.logging.api.events.AccessLoggerAfterEvent;
import com.tyebile.meereen.logging.api.events.AccessLoggerBeforeEvent;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用AOP记录访问日志,并触发{@link AccessLoggerListener#onLogger(AccessLoggerInfo)}
 *
 * @author zhouhao
 * @since 3.0
 */
public class AopAccessLoggerSupport extends StaticMethodMatcherPointcutAdvisor {

    private final List<AccessLoggerListener> listeners = new ArrayList<>();

    private final List<AccessLoggerParser> loggerParsers = new ArrayList<>();

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    public AopAccessLoggerSupport addListener(AccessLoggerListener loggerListener) {
        listeners.add(loggerListener);
        return this;
    }

    public AopAccessLoggerSupport addParser(AccessLoggerParser parser) {
        loggerParsers.add(parser);
        return this;
    }

    public AopAccessLoggerSupport() {
        setAdvice((MethodInterceptor) methodInvocation -> {
            MethodInterceptorHolder methodInterceptorHolder = MethodInterceptorHolder.create(methodInvocation);
            AccessLoggerInfo info = createLogger(methodInterceptorHolder);
            Object response;
            try {
                eventPublisher.publishEvent(new AccessLoggerBeforeEvent(info));
                listeners.forEach(listener -> listener.onLogBefore(info));
                response = methodInvocation.proceed();
                info.setResponse(response);
            } catch (Throwable e) {
                info.setException(e);
                throw e;
            } finally {
                info.setResponseTime(System.currentTimeMillis());
                //触发监听
                eventPublisher.publishEvent(new AccessLoggerAfterEvent(info));
                listeners.forEach(listener -> listener.onLogger(info));
            }
            return response;
        });
    }

    protected AccessLoggerInfo createLogger(MethodInterceptorHolder holder) {
        AccessLoggerInfo info = new AccessLoggerInfo();
        info.setId(IDGenerator.MD5.generate());

        info.setRequestTime(System.currentTimeMillis());
        LoggerDefine define = loggerParsers.stream()
                .filter(parser -> parser.support(ClassUtils.getUserClass(holder.getTarget()), holder.getMethod()))
                .findAny()
                .map(parser -> parser.parse(holder))
                .orElse(null);

        if (define != null) {
            info.setAction(define.getAction());
            info.setDescribe(define.getDescribe());
        }
        info.setParameters(holder.getArgs());
        info.setTarget(holder.getTarget().getClass());
        info.setMethod(holder.getMethod());

        HttpServletRequest request = WebUtil.getHttpServletRequest();
        if (null != request) {
            info.setHttpHeaders(WebUtil.getHeaders(request));
            info.setIp(WebUtil.getIpAddr(request));
            info.setHttpMethod(request.getMethod());
            info.setUrl(request.getRequestURL().toString());
        }
        return info;

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        AccessLogger ann = AopUtils.findAnnotation(aClass, method, AccessLogger.class);
        if (ann != null && ann.ignore()) {
            return false;
        }
        RequestMapping mapping = AopUtils.findAnnotation(aClass, method, RequestMapping.class);
        return mapping != null;

//        //注解了并且未取消
//        return null != ann && !ann.ignore();
    }
}