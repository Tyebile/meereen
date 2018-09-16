package com.tyebile.meereen.authorization.api.listener.event;

import java.util.function.Function;

/**
 * 授权失败时触发
 */
public class AuthorizationFailedEvent extends AbstractAuthorizationEvent {


    private static final long serialVersionUID = -101792832265740828L;
    /**
     * 失败原因
     */
    private Reason reason;

    /**
     * 异常信息
     */
    private Exception exception;

    public AuthorizationFailedEvent(String username,
                                    String password,
                                    Function<String, Object> parameterGetter,
                                    Reason reason) {
        super(username, password, parameterGetter);
        this.reason = reason;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Reason getReason() {
        return reason;
    }

    public enum Reason {
        PASSWORD_ERROR,
        USER_DISABLED,
        USER_NOT_EXISTS,
        OTHER
    }
}
