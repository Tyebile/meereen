package com.tyebile.meereen.authorization.api.token.event;

import com.tyebile.meereen.authorization.api.listener.event.AuthorizationEvent;
import com.tyebile.meereen.authorization.api.token.UserToken;
import org.springframework.context.ApplicationEvent;

public class UserTokenRemovedEvent extends ApplicationEvent implements AuthorizationEvent {

    private static final long serialVersionUID = -6662943150068863177L;

    public UserTokenRemovedEvent(UserToken token) {
        super(token);
    }

    public UserToken getDetail() {
        return ((UserToken) getSource());
    }
}
