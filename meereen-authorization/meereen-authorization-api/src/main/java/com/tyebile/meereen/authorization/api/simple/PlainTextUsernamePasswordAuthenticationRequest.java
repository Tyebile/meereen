package com.tyebile.meereen.authorization.api.simple;

import com.tyebile.meereen.authorization.api.AuthenticationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlainTextUsernamePasswordAuthenticationRequest implements AuthenticationRequest {

    private String username;

    private String password;
}
