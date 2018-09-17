package com.tyebile.meereen.authorization.basic.web;

public interface UserTokenForTypeParser extends UserTokenParser {
    String getTokenType();
}
