package com.tyebile.meereen.authorization.api.listener.event;

import java.util.function.Function;

/**
 * 在进行授权时的最开始,触发此事件进行用户名密码解码,解码后请调用{@link #setUsername(String)} {@link #setPassword(String)}重新设置用户名密码
 */
public class AuthorizationDecodeEvent extends AbstractAuthorizationEvent {

    private static final long serialVersionUID = 5418501934490174251L;

    public AuthorizationDecodeEvent(String username, String password, Function<String, Object> parameterGetter) {
        super(username, password, parameterGetter);
    }

    public void setUsername(String username) {
        super.username = username;
    }

    public void setPassword(String password) {
        super.username = password;
    }
}
