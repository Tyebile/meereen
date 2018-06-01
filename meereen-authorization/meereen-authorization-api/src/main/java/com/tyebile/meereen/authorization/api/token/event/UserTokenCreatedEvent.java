package com.tyebile.meereen.authorization.api.token.event;

import com.tyebile.meereen.authorization.api.listener.event.AuthorizationEvent;
import com.tyebile.meereen.authorization.api.token.UserToken;
import org.springframework.context.ApplicationEvent;

public class UserTokenCreatedEvent extends ApplicationEvent implements AuthorizationEvent {
    private UserToken detail;

    public UserTokenCreatedEvent(UserToken detail) {
        super(detail);
        this.detail = detail;
    }

    public UserToken getDetail() {
        return detail;
    }
}
