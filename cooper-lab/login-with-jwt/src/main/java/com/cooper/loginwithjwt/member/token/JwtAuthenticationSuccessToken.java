package com.cooper.loginwithjwt.member.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtAuthenticationSuccessToken extends AbstractAuthenticationToken {

    private Object principal;
    private Object credentials;
    private List<GrantedAuthority> grantedAuthority;

    public JwtAuthenticationSuccessToken(
            Object principal,
            Object credentials,
            Collection<? extends GrantedAuthority>authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
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
