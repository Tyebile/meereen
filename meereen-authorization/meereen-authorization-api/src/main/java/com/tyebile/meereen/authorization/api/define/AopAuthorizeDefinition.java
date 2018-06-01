package com.tyebile.meereen.authorization.api.define;

import java.lang.reflect.Method;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface AopAuthorizeDefinition extends AuthorizeDefinition {
    Class getTargetClass();

    Method getTargetMethod();
}
