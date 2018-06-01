package com.tyebile.meereen.commons.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author zhouhao
 */
@Component
public class ApplicationContextHolder {
    private static ApplicationContext context;

    public static ApplicationContext get() {
        if (null == context) {
            throw new UnsupportedOperationException("ApplicationContext not ready!");
        }
        return context;
    }

    @Autowired
    public void setContext(ApplicationContext context) {
        if (null == ApplicationContextHolder.context) {
            ApplicationContextHolder.context = context;
        }
    }
}
