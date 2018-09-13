package com.tyebile.meereen.logging.aop;


import com.tyebile.meereen.boost.aop.MethodInterceptorHolder;
import com.tyebile.meereen.logging.api.LoggerDefine;

import java.lang.reflect.Method;

public interface AccessLoggerParser {

    boolean support(Class clazz, Method method);

    LoggerDefine parse(MethodInterceptorHolder holder);
}
