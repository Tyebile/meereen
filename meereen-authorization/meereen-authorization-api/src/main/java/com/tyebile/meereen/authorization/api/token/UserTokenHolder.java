package com.tyebile.meereen.authorization.api.token;

import com.tyebile.meereen.commons.utils.ThreadLocalUtils;

/**
 * @author zhouhao
 */
public final class UserTokenHolder {

    private UserTokenHolder() {
    }

    public static UserToken currentToken() {
        return ThreadLocalUtils.get(UserToken.class.getName());
    }

    public static UserToken setCurrent(UserToken token) {
        ThreadLocalUtils.put(UserToken.class.getName(), token);
        return token;
    }

}
