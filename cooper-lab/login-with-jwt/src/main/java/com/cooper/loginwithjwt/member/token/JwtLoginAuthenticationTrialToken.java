package com.cooper.loginwithjwt.member.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtLoginAuthenticationTrialToken extends AbstractAuthenticationToken {

    private Object principal;
    private Object credentials;

    public JwtLoginAuthenticationTrialToken(
            Object principal,
            Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(false);
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

}
