package com.tyebile.meereen.authorization.api.token.event;

import com.tyebile.meereen.authorization.api.listener.event.AuthorizationEvent;
import com.tyebile.meereen.authorization.api.token.UserToken;
import org.springframework.context.ApplicationEvent;

public class UserTokenChangedEvent extends ApplicationEvent implements AuthorizationEvent {
    private UserToken before, after;

    public UserTokenChangedEvent(UserToken before, UserToken after) {
        super(after);
        this.before = before;
        this.after = after;
    }

    public UserToken getBefore() {
        return before;
    }

    public UserToken getAfter() {
        return after;
    }
}
