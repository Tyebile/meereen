package com.tyebile.meereen.system.authorization.local.service;


import com.tyebile.meereen.authorization.api.Authentication;
import com.tyebile.meereen.authorization.api.AuthenticationInitializeService;
import com.tyebile.meereen.authorization.api.AuthenticationManager;
import com.tyebile.meereen.authorization.api.AuthenticationRequest;
import com.tyebile.meereen.authorization.api.simple.PlainTextUsernamePasswordAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;

import java.util.function.Supplier;

public class SimpleAuthenticationManager implements AuthenticationManager {

    private AuthenticationInitializeService authenticationInitializeService;

//    @Autowired
//    private UserService userService;

    @Autowired(required = false)
    private CacheManager cacheManager;

    public SimpleAuthenticationManager() {
    }

    public SimpleAuthenticationManager(AuthenticationInitializeService authenticationInitializeService) {
        this.authenticationInitializeService = authenticationInitializeService;
    }

    @Autowired
    public void setAuthenticationInitializeService(AuthenticationInitializeService authenticationInitializeService) {
        this.authenticationInitializeService = authenticationInitializeService;
    }

    @Override
    public Authentication authenticate(AuthenticationRequest request) {
//        if (request instanceof PlainTextUsernamePasswordAuthenticationRequest) {
//            String username = ((PlainTextUsernamePasswordAuthenticationRequest) request).getUsername();
//            String password = ((PlainTextUsernamePasswordAuthenticationRequest) request).getPassword();
//            UserEntity userEntity = userService.selectByUserNameAndPassword(username, password);
//            if (userEntity == null) {
//                throw new ValidationException("密码错误", "password");
//            }
//            if (!DataStatus.STATUS_ENABLED.equals(userEntity.getStatus())) {
//                throw new ValidationException("用户已被禁用", "username");
//            }
//            return getByUserId(userEntity.getId());
//        }
        return null;
    }

    @Override
//    @Cacheable(value = USER_AUTH_CACHE_NAME, key = "#userId")
    public Authentication getByUserId(String userId) {
        Supplier<Authentication> supplier = () -> authenticationInitializeService.initUserAuthorization(userId);

        if (null != cacheManager) {
            Cache cache = cacheManager.getCache(USER_AUTH_CACHE_NAME);
            Cache.ValueWrapper wrapper = cache.get(userId);
            if (wrapper == null) {
                Authentication authentication = supplier.get();
                cache.put(userId, authentication);
                return authentication;
            } else {
                return (Authentication) wrapper.get();
            }
        }
        return supplier.get();
    }

}
