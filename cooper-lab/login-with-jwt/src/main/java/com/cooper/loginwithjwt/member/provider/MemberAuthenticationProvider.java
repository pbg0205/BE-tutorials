package com.cooper.loginwithjwt.member.provider;

import com.cooper.loginwithjwt.member.token.JwtAuthenticationSuccessToken;
import com.cooper.loginwithjwt.member.token.JwtLoginAuthenticationTrialToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String inputEmail = authentication.getName();
        String inputPassword = (String) authentication.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(inputEmail);
        String userEmail = userDetails.getUsername();

        if (!passwordEncoder.matches(inputPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("invalid inputPassword");
        }

        return new JwtAuthenticationSuccessToken(userEmail, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JwtLoginAuthenticationTrialToken.class);
    }

}
