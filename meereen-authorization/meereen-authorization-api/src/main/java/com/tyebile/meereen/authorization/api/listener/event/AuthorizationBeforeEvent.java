package com.tyebile.meereen.authorization.api.listener.event;

import java.util.function.Function;

/**
 * 授权前事件
 */
public class AuthorizationBeforeEvent extends AbstractAuthorizationEvent {

    private static final long serialVersionUID = 5948747533500518524L;

    public AuthorizationBeforeEvent(String username, String password, Function<String, Object> parameterGetter) {
        super(username, password, parameterGetter);
    }
}
