package com.tyebile.meereen.authorization.basic.web;

/**
 * 令牌解析结果
 */
public interface ParsedToken {
    /**
     * @return 令牌
     */
    String getToken();

    /**
     * @return 令牌类型
     */
    String getType();
}
