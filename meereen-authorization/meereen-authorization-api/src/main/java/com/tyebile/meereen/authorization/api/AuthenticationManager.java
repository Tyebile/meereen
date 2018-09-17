package com.tyebile.meereen.authorization.api;

/**
 * 授权信息管理器,用于获取用户授权和同步授权信息
 */
public interface AuthenticationManager {
    String USER_AUTH_CACHE_NAME = "user-auth-";

    /**
     * 进行授权操作
     *
     * @param request 授权请求
     * @return 授权成功则返回用户权限信息
     */
    Authentication authenticate(AuthenticationRequest request);

    /**
     * 根据用户ID获取权限信息
     *
     * @param userId 用户ID
     * @return 权限信息
     */
    Authentication getByUserId(String userId);
}
