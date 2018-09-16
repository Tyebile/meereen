package com.tyebile.meereen.authorization.basic.web;

import com.tyebile.meereen.authorization.api.Authentication;
import com.tyebile.meereen.authorization.api.AuthenticationManager;
import com.tyebile.meereen.authorization.api.listener.event.AuthorizationBeforeEvent;
import com.tyebile.meereen.authorization.api.listener.event.AuthorizationDecodeEvent;
import com.tyebile.meereen.authorization.api.listener.event.AuthorizationFailedEvent;
import com.tyebile.meereen.authorization.api.listener.event.AuthorizationSuccessEvent;
import com.tyebile.meereen.authorization.api.simple.PlainTextUsernamePasswordAuthenticationRequest;
import com.tyebile.meereen.commons.controller.message.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.tyebile.meereen.commons.controller.message.ResponseMessage.ok;

@RestController
@Api(tags = "权限-用户授权", value = "授权")
public class AuthorizationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("用户名密码登录,json方式")
    public ResponseMessage<Map<String, Object>> authorize(@ApiParam(example = "{\"username\":\"admin\",\"password\":\"admin\"}")
                                                          @RequestBody Map<String, String> parameter) {

        return doLogin(Objects.requireNonNull(parameter.get("username"), "用户名不能为空")
                , Objects.requireNonNull(parameter.get("password"), "密码不能为空")
                , parameter);
    }

    protected ResponseMessage<Map<String, Object>> doLogin(String username, String password, Map<String, ?> parameter) {
        AuthorizationFailedEvent.Reason reason = AuthorizationFailedEvent.Reason.OTHER;
        Function<String, Object> parameterGetter = parameter::get;

        try {
            AuthorizationDecodeEvent decodeEvent = new AuthorizationDecodeEvent(username, password, parameterGetter);
            eventPublisher.publishEvent(decodeEvent);
            username = decodeEvent.getUsername();
            password = decodeEvent.getPassword();
            AuthorizationBeforeEvent beforeEvent = new AuthorizationBeforeEvent(username, password, parameterGetter);
            eventPublisher.publishEvent(beforeEvent);
            // 验证通过
            Authentication authentication = authenticationManager.authenticate(new PlainTextUsernamePasswordAuthenticationRequest(username, password));
            //触发授权成功事件
            AuthorizationSuccessEvent event = new AuthorizationSuccessEvent(authentication, parameterGetter);
            event.getResult().put("userId", authentication.getUser().getId());
            eventPublisher.publishEvent(event);
            return ok(event.getResult());
        } catch (Exception e){

        }


        return null;
    }
}
