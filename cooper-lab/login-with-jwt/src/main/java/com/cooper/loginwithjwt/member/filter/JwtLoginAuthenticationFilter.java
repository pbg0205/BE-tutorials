package com.cooper.loginwithjwt.member.filter;

import com.cooper.loginwithjwt.member.dto.MemberJwtLoginTrialDto;
import com.cooper.loginwithjwt.member.token.JwtLoginAuthenticationTrialToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JwtLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private ObjectMapper objectMapper;

    public JwtLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/auth/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String contentTypeHeader = request.getHeader(HttpHeaders.CONTENT_TYPE);
        if (!isHeaderContentType(contentTypeHeader)) {
            throw new IllegalStateException("Authentication is not supports");
        }

        MemberJwtLoginTrialDto memberJwtLoginDto
                = objectMapper.readValue(request.getReader(), MemberJwtLoginTrialDto.class);

        JwtLoginAuthenticationTrialToken jwtLoginAuthenticationTrialToken
                = new JwtLoginAuthenticationTrialToken(memberJwtLoginDto.getEmail(), memberJwtLoginDto.getPassword());

        return getAuthenticationManager().authenticate(jwtLoginAuthenticationTrialToken);
    }

    private boolean isHeaderContentType(String contentTypeHeader) {
        return !ObjectUtils.isEmpty(contentTypeHeader) && contentTypeHeader.contains(APPLICATION_JSON_VALUE);
    }

}
